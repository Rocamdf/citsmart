package br.com.centralit.citcorpore.metainfo.util;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import br.com.centralit.citcorpore.bean.DataManagerFKRelatedDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.integracao.VisaoDao;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.SQLConfig;
@SuppressWarnings({"rawtypes","unchecked","unused"})
public class DataBaseMetaDadosUtil {
	
	public String sincronizaObjNegDB(String nomeTabela, boolean messages) throws ServiceException, Exception{
		VisaoDao visaoDao = new VisaoDao();
		Connection con = (Connection) visaoDao.getTransactionControler().getTransactionObject();
		String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA,"");
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
			DB_SCHEMA = null;
		} else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")){
		    DB_SCHEMA = "citsmart";
		}
		Collection colObsNegocio = this.readTables(con, DB_SCHEMA, DB_SCHEMA, nomeTabela, messages);		
		con.close();
		con = null;
		
		String carregados = "";
		ObjetoNegocioService objetoNegocioService = (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		for (Iterator it = colObsNegocio.iterator(); it.hasNext();){
			ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
			if (messages){
				System.out.println("-----: Objeto de Negocio: " + objetoNegocioDTO.getNomeTabelaDB());
			}
			carregados += objetoNegocioDTO.getNomeTabelaDB() + ",";
			Collection colObjs = objetoNegocioService.findByNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
			if (colObjs == null || colObjs.size() == 0){
				if (messages){
					System.out.println("----------: Criando....  " + objetoNegocioDTO.getNomeTabelaDB());
				}
				objetoNegocioService.create(objetoNegocioDTO); 
			}else{
				ObjetoNegocioDTO objetoNegocioAux = (ObjetoNegocioDTO)((List)colObjs).get(0);
				objetoNegocioDTO.setIdObjetoNegocio(objetoNegocioAux.getIdObjetoNegocio());
				if (messages){
					System.out.println("----------: Atualizando....  " + objetoNegocioDTO.getNomeTabelaDB() + "    Id Interno: " + objetoNegocioAux.getIdObjetoNegocio());
				}
				objetoNegocioService.update(objetoNegocioDTO);
			}
		}
		
		return carregados;
	}
	public Collection readTables(Connection con, String catalogo, String esquema, String tableName, boolean messages) throws SQLException{
		DatabaseMetaData dm = con.getMetaData();
		String[] types = {"TABLE"};
		Map listaFKs = new HashMap();
		ResultSet rsTables = dm.getTables(catalogo,esquema,null,types);	
		
		Collection colObjetosNegocio = new ArrayList();
		while (rsTables.next()) {
			ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
			String cat = rsTables.getString("TABLE_CAT");
			String schema = rsTables.getString("TABLE_SCHEM");
			String nomeTabela = rsTables.getString("TABLE_NAME");
			
			String nomeTabelaAux = nomeTabela.toUpperCase();
			objetoNegocioDTO.setNomeTabelaDB(nomeTabelaAux);
			objetoNegocioDTO.setNomeObjetoNegocio(nomeTabelaAux);
			objetoNegocioDTO.setSituacao("A");
			
			if(tableName != null){
				if (tableName != null && !tableName.trim().equalsIgnoreCase("")){
					if (!tableName.toUpperCase().equalsIgnoreCase(nomeTabelaAux)){
						continue;
					}
				}
			}
			
			if (messages){
				System.out.println(" ------::::::::::::::::> TABELA: " + nomeTabelaAux);
			}
			
			ResultSet rsPKs = dm.getPrimaryKeys(cat,schema,nomeTabela);
			ArrayList listaPKs = new ArrayList();
			while (rsPKs.next()) {
				String nomeColuna  = rsPKs.getString("COLUMN_NAME");
				listaPKs.add(nomeColuna);
			}
			rsPKs.close();
			rsPKs = null;
			ResultSet rsFKs = dm.getImportedKeys(cat,schema,nomeTabela);
			while (rsFKs.next()) {
				/*
				RelacionamentoTabela relTabela = new RelacionamentoTabela();
				*/
				String nomeTabelaPK  = rsFKs.getString("PKTABLE_NAME");
				String nomeColunaPK  = rsFKs.getString("PKCOLUMN_NAME");
				String nomeTabelaFK  = rsFKs.getString("FKTABLE_NAME");
				String nomeColunaFK  = rsFKs.getString("FKCOLUMN_NAME");
				String campoFK = schema + "." + nomeTabelaFK + "." + nomeColunaFK;
				String campoPK = schema + "." + nomeTabelaPK + "." + nomeColunaPK;
				String join = "(and " + campoFK + " = " + campoPK + ")";
				/*
				relTabela.setIdTabela1(aliasTabela);
				relTabela.setNomeTabela1(nomeTabelaFK);
				relTabela.setNomeTabela2(nomeTabelaPK);
				relTabela.setNomeColunaTabela1(nomeColunaFK);
				relTabela.setNomeColunaTabela2(nomeColunaPK);
				*/
				//listaFKs.put(campoFK,relTabela);
				if (messages){
					System.out.println(" join = " + join);
				}
			}
			rsFKs.close();	
			rsFKs = null;
			
			ResultSet rsColunas = null;
			try{
				rsColunas = dm.getColumns(cat,schema,nomeTabela,null);
			}catch(Exception e){
				System.out.println("Problemas ao ler campos da tabela: " + nomeTabela + " --> " + e.getMessage());
				//e.printStackTrace();
			}
			if (rsColunas == null){
				continue;
			}
			Collection colCampos = new ArrayList();
			while (rsColunas.next()) {
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
				String nomeColunaBanco = rsColunas.getString("COLUMN_NAME");
				
				String campoFK = schema + "." + nomeTabela + "." + nomeColunaBanco;
				
				String nomeTipo = rsColunas.getString("TYPE_NAME");
				int precisionDB = rsColunas.getInt("DECIMAL_DIGITS");
				int tamanho = rsColunas.getInt("COLUMN_SIZE");
				String isNullable = rsColunas.getString("IS_NULLABLE");	
				String seColunaPK = ((listaPKs.contains(nomeColunaBanco))?"S":"N"); 
				String seColunaFK = ((listaFKs.containsKey(campoFK))?"S":"N");				
				String seObrigatorio = null;
				if ((isNullable!= null)&&(isNullable.trim().length() > 0)) {
					seObrigatorio = ((isNullable.trim().indexOf("YES") > -1)?"N":"S");
				}
				
				if (messages){
					System.out.println(" ------:::::::::::::::::::::::::::::::::::> COLUNA: " + nomeColunaBanco 
						+ " Tipo: " + nomeTipo
						+ " PK: " + seColunaPK 
						+ " OBR: " + seObrigatorio);
				}
				
				if (nomeTipo == null){
					nomeTipo = "";
				}
				nomeTipo = nomeTipo.toUpperCase();
				nomeColunaBanco = nomeColunaBanco.toUpperCase();
				
				camposObjetoNegocioDTO.setNomeDB(nomeColunaBanco);
				camposObjetoNegocioDTO.setNome(nomeColunaBanco);
				camposObjetoNegocioDTO.setPk(seColunaPK);
				camposObjetoNegocioDTO.setSequence(seColunaPK);
				camposObjetoNegocioDTO.setUnico(seColunaPK);
				camposObjetoNegocioDTO.setObrigatorio(seObrigatorio);
				camposObjetoNegocioDTO.setTipoDB(nomeTipo);
				camposObjetoNegocioDTO.setPrecisionDB(precisionDB);
				camposObjetoNegocioDTO.setSituacao("A");
				
				colCampos.add(camposObjetoNegocioDTO);
			}	
			rsColunas.close();
			rsColunas = null;
			objetoNegocioDTO.setColCampos(colCampos);
			colObjetosNegocio.add(objetoNegocioDTO);
		}
		rsTables.close();
		rsTables = null;
		dm = null;
		return colObjetosNegocio;
	}
	
	public String getTabelaPaiByTableAndField(String tableName, String field, boolean messages) throws Exception{
		VisaoDao visaoDao = new VisaoDao();
		Connection con = (Connection) visaoDao.getTransactionControler().getTransactionObject();
		String DB_SCHEMA = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DB_SCHEMA,"");
		if(CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
			DB_SCHEMA = null;
		} else if (DB_SCHEMA == null || DB_SCHEMA.trim().equalsIgnoreCase("")){
		    DB_SCHEMA = "citsmart";
		}
		
		DatabaseMetaData dm = con.getMetaData();
		String[] types = {"TABLE"};
		Map listaFKs = new HashMap();
		String retorno = "";
		ResultSet rsTables = dm.getTables(DB_SCHEMA,DB_SCHEMA,null,types);	
		
		Collection colObjetosNegocio = new ArrayList();
		while (rsTables.next()) {
			ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
			String cat = rsTables.getString("TABLE_CAT");
			String schema = rsTables.getString("TABLE_SCHEM");
			String nomeTabela = rsTables.getString("TABLE_NAME");
			
			String nomeTabelaAux = nomeTabela.toUpperCase();
			objetoNegocioDTO.setNomeTabelaDB(nomeTabelaAux);
			objetoNegocioDTO.setNomeObjetoNegocio(nomeTabelaAux);
			objetoNegocioDTO.setSituacao("A");
			
			if (tableName != null && !tableName.trim().equalsIgnoreCase("")){
				if (!tableName.toUpperCase().equalsIgnoreCase(nomeTabelaAux)){
					continue;
				}
			}		
			
			if (messages){
				//System.out.println(" ------::::::::::::::::> TABELA: " + nomeTabelaAux);
			}
			
			ResultSet rsFKs = dm.getImportedKeys(cat,schema,nomeTabela);
			while (rsFKs.next()) {
				String nomeTabelaPK  = rsFKs.getString("PKTABLE_NAME");
				String nomeColunaPK  = rsFKs.getString("PKCOLUMN_NAME");
				String nomeTabelaFK  = rsFKs.getString("FKTABLE_NAME");
				String nomeColunaFK  = rsFKs.getString("FKCOLUMN_NAME");
				String campoFK = schema + "." + nomeTabelaFK + "." + nomeColunaFK;
				String campoPK = schema + "." + nomeTabelaPK + "." + nomeColunaPK;
				String join = "(and " + campoFK + " = " + campoPK + ")";
				
				if (field.equalsIgnoreCase(nomeColunaPK)){
					retorno = nomeTabelaPK;
					break;
				}
			}
			rsFKs.close();	
			rsFKs = null;
			
			break;
		}
		rsTables.close();
		rsTables = null;
		dm = null;
		
		con.close();
		con = null;
		
		return retorno;
	}	
	
	public Collection readFK(Connection con, String catalogo, String esquema, String tableName) throws SQLException{
		DatabaseMetaData dm = con.getMetaData();
		String[] types = {"TABLE"};
		Map listaFKs = new HashMap();
		ResultSet rsTables = dm.getTables(catalogo,esquema,null,types);	
		
		Collection colRetorno = new ArrayList();
		while (rsTables.next()) {
			ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
			String cat = rsTables.getString("TABLE_CAT");
			String schema = rsTables.getString("TABLE_SCHEM");
			String nomeTabela = rsTables.getString("TABLE_NAME");
			
			String nomeTabelaAux = nomeTabela.toUpperCase();
			objetoNegocioDTO.setNomeTabelaDB(nomeTabelaAux);
			objetoNegocioDTO.setNomeObjetoNegocio(nomeTabelaAux);
			objetoNegocioDTO.setSituacao("A");
			
			if (tableName != null && !tableName.trim().equalsIgnoreCase("")){
				if (!tableName.toUpperCase().equalsIgnoreCase(nomeTabelaAux)){
					continue;
				}
			}		
			
			System.out.println(" ------::::::::::::::::> TABELA: " + nomeTabelaAux);
			
			ResultSet rsPKs = dm.getPrimaryKeys(cat,schema,nomeTabela);
			ArrayList listaPKs = new ArrayList();
			while (rsPKs.next()) {
				String nomeColuna  = rsPKs.getString("COLUMN_NAME");
				listaPKs.add(nomeColuna);
			}
			rsPKs.close();
			rsPKs = null;
			ResultSet rsFKs = dm.getExportedKeys(cat,schema,nomeTabela);
			while (rsFKs.next()) {
				DataManagerFKRelatedDTO dataManagerFKRelatedDTO = new DataManagerFKRelatedDTO();
				/*
				RelacionamentoTabela relTabela = new RelacionamentoTabela();
				*/
				String nomeTabelaPK  = rsFKs.getString("PKTABLE_NAME");
				String nomeColunaPK  = rsFKs.getString("PKCOLUMN_NAME");
				String nomeTabelaFK  = rsFKs.getString("FKTABLE_NAME");
				String nomeColunaFK  = rsFKs.getString("FKCOLUMN_NAME");
				String campoFK = nomeTabelaFK + "." + nomeColunaFK;
				String campoPK = nomeTabelaPK + "." + nomeColunaPK;
				String join = " " + campoFK + " = " + campoPK + " ";
				
				dataManagerFKRelatedDTO.setJoin(join);
				dataManagerFKRelatedDTO.setNomeTabelaRelacionada(nomeTabelaFK);
				dataManagerFKRelatedDTO.setPartChild(campoFK);
				dataManagerFKRelatedDTO.setPartParent(campoPK);
				/*
				relTabela.setIdTabela1(aliasTabela);
				relTabela.setNomeTabela1(nomeTabelaFK);
				relTabela.setNomeTabela2(nomeTabelaPK);
				relTabela.setNomeColunaTabela1(nomeColunaFK);
				relTabela.setNomeColunaTabela2(nomeColunaPK);
				*/
				//listaFKs.put(campoFK,relTabela);
				System.out.println(" join = " + join);
				colRetorno.add(dataManagerFKRelatedDTO);
			}
			rsFKs.close();	
			rsFKs = null;
		}
		rsTables.close();
		rsTables = null;
		dm = null;
		return colRetorno;
	}	
}
