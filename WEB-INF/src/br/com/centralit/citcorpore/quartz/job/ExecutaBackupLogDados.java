package br.com.centralit.citcorpore.quartz.job;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.util.DataBaseMetaDadosUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.JdbcEngine;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilTratamentoArquivos;

public class ExecutaBackupLogDados implements Job {

	String keysProcessed = "";

	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		String ORIGEM_SISTEMA;
		try {
			String INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS, "");
			ORIGEM_SISTEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_SISTEMA,"");
			if (ORIGEM_SISTEMA == null || ORIGEM_SISTEMA.trim().equalsIgnoreCase("")){
				System.out.println("Origem do sistema vazio");
				return;
			}
			if (INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS == null || INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS.trim().equalsIgnoreCase("")){
				INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS = CITCorporeUtil.caminho_real_app + "/exportXML";
			}
			ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
			ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
			objetoNegocioDTO = objetoNegocioService.findByNomeObjetoNegocio("LOGDADOS");
			String sqlDelete = "";
			String nomeTabela = "";
			String excluirAoExportar = "S";	
			StringBuffer strAux = null;
			
			strAux = geraRecursiveExportObjetoNegocio(objetoNegocioDTO.getIdObjetoNegocio(), sqlDelete, nomeTabela, "");
			
			nomeTabela = "LOGDADOS";
			sqlDelete = "DELETE FROM LOGDADOS";
			
			String str = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n<tables origem='" + ORIGEM_SISTEMA + "'>\n" + strAux.toString();
			str = "" + str + "\n</tables>";
			
			String strDateTime = (new java.util.Date()).toString();
			strDateTime = strDateTime.replaceAll(" ", "_");
			strDateTime = strDateTime.replaceAll(":", "_");
			UtilTratamentoArquivos.geraFileTxtFromString(INFORMAR_CAMINHO_EXECUCAO_BACKUP_LOGDADOS + "/export_data_" + strDateTime + ".smart", str);
			
			JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
			
			if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")){
				sqlDelete = sqlDelete + ";";
				String[] strDel = sqlDelete.split(";");
				if (strDel != null){
					for (int i = 0; i < strDel.length; i++){
							try{
								System.out.println("Executando sql... " + strDel[i]);
								jdbcEngine.execUpdate(strDel[i], null);
							}catch (Exception e){
								e.printStackTrace();						}
						}
					}
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public StringBuffer geraRecursiveExportObjetoNegocio(Integer idObjetoNegocio, String sqlDelete, String nomeTabela, String filterAditional) throws ServiceException, Exception{
		StringBuffer strAux = geraExportObjetoNegocio(idObjetoNegocio, sqlDelete, nomeTabela, filterAditional);
		return strAux;
	}
	
	public StringBuffer geraExportObjetoNegocio(Integer idObjetoNegocio, String sqlDelete, String nomeTabela, String filterAditional) throws ServiceException, Exception {
		CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
		objetoNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
		objetoNegocioDTO = (ObjetoNegocioDTO) objetoNegocioService.restore(objetoNegocioDTO);
		Collection col = camposObjetoNegocioService.findByIdObjetoNegocio(idObjetoNegocio);
		String sqlCondicao = "";
		String sqlCampos = "";
		
		String excluirAoExportar = "S";
		
		// Antes de fazer a exportacao, faz o sincronismo com o DB, pois pode estar desatualizado!
		DataBaseMetaDadosUtil dataBaseMetaDadosUtil = new DataBaseMetaDadosUtil();
		dataBaseMetaDadosUtil.sincronizaObjNegDB(objetoNegocioDTO.getNomeTabelaDB(), false);
		System.out.println("Sincronizando tabela: " + objetoNegocioDTO.getNomeTabelaDB());
		Thread.sleep(1000); // Da uma sleep pra liberar cursores presos e dar um tempo pro GC passar.
		System.gc();
		
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
				if (!sqlCampos.trim().equalsIgnoreCase("")) {
					sqlCampos += ",";
				}
				sqlCampos = sqlCampos + camposObjetoNegocioDto.getNomeDB();

			}
		}
		String sqlFinal = "SELECT " + sqlCampos + " FROM " + objetoNegocioDTO.getNomeTabelaDB();
		sqlDelete = "DELETE FROM " + objetoNegocioDTO.getNomeTabelaDB();
		if (!sqlCondicao.trim().equalsIgnoreCase("")) {
			if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
				sqlFinal = sqlFinal + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
				sqlDelete = sqlDelete + " WHERE " + sqlCondicao + " AND (" + filterAditional + ")";
			} else {
				sqlFinal = sqlFinal + " WHERE " + sqlCondicao;
				sqlDelete = sqlDelete + " WHERE " + sqlCondicao;
			}
		} else {
			if (filterAditional != null && !filterAditional.trim().equalsIgnoreCase("")) {
				sqlFinal = sqlFinal + " WHERE (" + filterAditional + ")";
				sqlDelete = sqlDelete + " WHERE (" + filterAditional + ")";
			}
		}
		String sqlDeleteAux = sqlDelete;
		String sqlExportAux = sqlFinal;
		
		JdbcEngine jdbcEngine = new JdbcEngine(Constantes.getValue("DATABASE_ALIAS"), null);
		List lst = null;
		try {
			lst = jdbcEngine.execSQL(sqlFinal, null, 0);
		} catch (Exception e) {
			e.printStackTrace();
			return new StringBuffer("OCORREU ERRO NA GERACAO DOS DADOS!" + e.getMessage());
		}
		StringBuffer strXML = new StringBuffer();
		strXML.append("<table name='" + objetoNegocioDTO.getNomeTabelaDB() + "'>\n");
		strXML.append("<command><![CDATA[" + sqlFinal + "]]></command>\n");
		if (excluirAoExportar != null && excluirAoExportar.equalsIgnoreCase("S")) {
			strXML.append("<commandDelete><![CDATA[" + sqlDelete + "]]></commandDelete>\n");
		} else {
			strXML.append("<commandDelete>NONE</commandDelete>\n");
		}
		if (lst != null) {
			int j = 0;
			for (Iterator itDados = lst.iterator(); itDados.hasNext();) {
				Object[] obj = (Object[]) itDados.next();
				int i = 0;
				j++;
				strXML.append("<record number='" + j + "'>\n");
				for (Iterator it = col.iterator(); it.hasNext();) {
					CamposObjetoNegocioDTO camposObjetoNegocioDto = (CamposObjetoNegocioDTO) it.next();
					String key = "n";
					boolean isStringType = MetaUtil.isStringType(camposObjetoNegocioDto.getTipoDB());
					if (camposObjetoNegocioDto.getPk() != null && camposObjetoNegocioDto.getPk().equalsIgnoreCase("S")) {
						key = "y";
						if (!keysProcessed.trim().equalsIgnoreCase("")) {
							keysProcessed = keysProcessed + ",";
						}
						if (isStringType) {
							keysProcessed = keysProcessed + "'" + obj[i] + "'";
						} else {
							keysProcessed = keysProcessed + "" + obj[i] + "";
						}
					}
					String sequence = "n";
					if (camposObjetoNegocioDto.getSequence() != null && camposObjetoNegocioDto.getSequence().equalsIgnoreCase("S")) {
						sequence = "y";
					}
					strXML.append("<field name='" + camposObjetoNegocioDto.getNomeDB() + "' key='" + key + "' sequence='" + sequence + "' type='" + camposObjetoNegocioDto.getTipoDB().trim() + "'>");
					if (isStringType) {
						strXML.append("<![CDATA[");
					}
					strXML.append(obj[i]);
					if (isStringType) {
						strXML.append("]]>");
					}
					strXML.append("</field>\n");
					i++;
				}
				strXML.append("</record>\n");
			}
		}
		strXML.append("</table>\n");
		
		return strXML;
	}

}
