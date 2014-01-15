package br.com.centralit.citcorpore.integracao;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.centralit.citcorpore.bean.HistoricoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.RelatorioCargaHorariaDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HistoricoSolicitacaoServicoDao extends CrudDaoDefaultImpl {

	public HistoricoSolicitacaoServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
		// TODO Auto-generated constructor stub
	}

	/**
     * 
     */
	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {

		return null;
	}

	@Override
	public Collection getFields() {

		Collection listFields = new ArrayList();

		listFields.add(new Field("idHistoricoSolicitacao", "idHistoricoSolicitacao", true, true, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idResponsavelAtual", "idResponsavelAtual", false, false, false, false));
		listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));
		listFields.add(new Field("idOcorrencia", "idOcorrencia", false, false, false, false));
		listFields.add(new Field("dataCriacao", "dataCriacao", false, false, false, false));
		listFields.add(new Field("dataFinal", "dataFinal", false, false, false, false));
		listFields.add(new Field("horasTrabalhadas", "horasTrabalhadas", false, false, false, false));
		listFields.add(new Field("idServicoContrato", "idServicoContrato", false, false, false, false));
		listFields.add(new Field("idCalendario", "idCalendario", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		return listFields;
	}

	@Override
	public String getTableName() {
		return "HistoricoSolicitacaoServico";
	}


	@Override
	public Class getBean() {
		return HistoricoSolicitacaoServicoDTO.class;
	}

	@Override
	public Collection list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean findHistoricoSolicitacao(Integer idSolicitacaoServico) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		
		List list = new ArrayList();
		sql.append("SELECT idHistoricoSolicitacao ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("WHERE idSolicitacaoServico = ? ");
		sql.append("AND datafinal is null ");
		
		parametro.add(idSolicitacaoServico);
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idServicoContratoContrato");

		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result == null || result.size() == 0) {
			return true;
		} else {
			return false;
		}
	}
	public Collection<HistoricoSolicitacaoServicoDTO> restoreHistoricoServico(Integer idSolicitacaoServico)throws Exception {
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		
		List list = new ArrayList();
		sql.append("SELECT idHistoricoSolicitacao,idSolicitacaoServico,idResponsavelAtual,idGrupo,idOcorrencia,dataCriacao,dataFinal,horasTrabalhadas,idServicoContrato,idCalendario,status ");
		sql.append("FROM " + getTableName() + " ");
		sql.append("WHERE idSolicitacaoServico = ? ");
		sql.append("AND datafinal is null ");
		
		parametro.add(idSolicitacaoServico);
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idHistoricoSolicitacao");
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("idResponsavelAtual");
		listRetorno.add("idGrupo");
		listRetorno.add("idOcorrencia");
		listRetorno.add("dataCriacao");
		listRetorno.add("datafinal");
		listRetorno.add("horasTrabalhadas");
		listRetorno.add("idServicoContrato");
		listRetorno.add("idCalendario");
		listRetorno.add("status");
		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result != null && !result.isEmpty()) {
			return result;
		}else{
			return null;
		}
	}
	
	public Collection<HistoricoSolicitacaoServicoDTO> findResponsavelAtual(Integer idSolicitacaoServico)throws Exception {
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		
		List list = new ArrayList();
		
		sql.append("SELECT bpm_itemtrabalhofluxo.idresponsavelatual ");
		sql.append("FROM solicitacaoservico solicitacaoservico ");
		sql.append("INNER JOIN execucaosolicitacao execucaosolicitacao ");
		sql.append("ON execucaosolicitacao.idsolicitacaoservico = solicitacaoservico.idsolicitacaoservico ");
		sql.append("INNER JOIN bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo ");
		sql.append("ON bpm_itemtrabalhofluxo.idinstancia = execucaosolicitacao.idinstanciafluxo ");
		sql.append("WHERE solicitacaoservico.idsolicitacaoservico = ? ");
		sql.append("AND bpm_itemtrabalhofluxo.datahorafinalizacao  is null  ");
		sql.append("AND bpm_itemtrabalhofluxo.idresponsavelatual  is not null  ");
		
		parametro.add(idSolicitacaoServico);
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idResponsavelAtual");
		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		if (result != null && !result.isEmpty()) {
			return result;
		}else{
			return null;
		}
	}
	
	public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaUsuario(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		
			List list = new ArrayList();
		
		
			
			sql.append("SELECT usuario.nome as nomeUsuario, sum(historicosolicitacaoservico.horastrabalhadas) AS soma ");
			sql.append("FROM historicosolicitacaoservico ");
			sql.append("INNER JOIN usuario ");
			sql.append("ON usuario.idusuario = historicosolicitacaoservico.idresponsavelatual ");
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("WHERE to_char(historicosolicitacaoservico.datacriacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		}else{
			sql.append("WHERE historicosolicitacaoservico.datacriacao BETWEEN ? AND ? ");
		}
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(solicitacaoServicoDTO.getDataInicio()));
			parametro.add(formatter.format(solicitacaoServicoDTO.getDataFim()));
		} else {
			parametro.add(solicitacaoServicoDTO.getDataInicio());
			parametro.add(transformaHoraFinal(solicitacaoServicoDTO.getDataFim()));
		}
		
		if (solicitacaoServicoDTO.getIdGrupoAtual()!= null) {
			sql.append("AND historicosolicitacaoservico.idgrupo = ? ");
			parametro.add(solicitacaoServicoDTO.getIdGrupoAtual());
		}
		sql.append("GROUP BY historicosolicitacaoservico.idresponsavelatual,usuario.nome ");
		sql.append("ORDER BY usuario.nome ");
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("nomeUsuario");
		listRetorno.add("soma");
		List result = this.engine.listConvertion(RelatorioCargaHorariaDTO.class, list, listRetorno);
		if (result != null && !result.isEmpty()) {
			return result;
		}else{
			return null;
		}
	}
	
	public Collection<RelatorioCargaHorariaDTO> imprimirCargaHorariaGrupo(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		
		List list = new ArrayList();
		
			sql.append("SELECT grupo.nome AS nomeGrupo,Sum(historicosolicitacaoservico.horastrabalhadas) AS soma ");
			sql.append("FROM   historicosolicitacaoservico ");
			sql.append("      INNER JOIN grupo ");
			sql.append("               ON grupo.idgrupo = historicosolicitacaoservico.idgrupo ");
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("WHERE to_char(historicosolicitacaoservico.datacriacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		}else{
			sql.append("WHERE historicosolicitacaoservico.datacriacao BETWEEN ? AND ? ");
		}
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(solicitacaoServicoDTO.getDataInicio()));
			parametro.add(formatter.format(solicitacaoServicoDTO.getDataFim()));
		} else {
			parametro.add(solicitacaoServicoDTO.getDataInicio());
			parametro.add(transformaHoraFinal(solicitacaoServicoDTO.getDataFim()));
		}
		
		if (solicitacaoServicoDTO.getIdGrupoAtual()!= null) {
			sql.append("AND historicosolicitacaoservico.idgrupo = ? ");
			parametro.add(solicitacaoServicoDTO.getIdGrupoAtual());
		}
		
		sql.append("GROUP  BY grupo.idgrupo, grupo.nome ");
		sql.append("ORDER BY grupo.nome,grupo.idgrupo ");
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("nomeGrupo");
		listRetorno.add("soma");
		List result = this.engine.listConvertion(RelatorioCargaHorariaDTO.class, list, listRetorno);
		if (result != null && !result.isEmpty()) {
			return result;
		}else{
			return null;
		}
	}
	
	
	private Timestamp transformaHoraFinal(Date data) throws ParseException {
		String dataHora = data + " 23:59:59";
		String pattern = "yyyy-MM-dd hh:mm:ss";
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		java.util.Date d = sdf.parse(dataHora);
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(d.getTime());
		return sqlDate;
	}
	

	public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhada(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		
		List list = new ArrayList();
			
		sql.append("SELECT distinct execucaosolicitacao.idsolicitacaoservico,UPPER(servico.nomeservico),UPPER(solicitacaoservico.situacao) ");
		sql.append("FROM bpm_atribuicaofluxo bpm_atribuicaofluxo  ");
		sql.append("INNER JOIN bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo  ");
		sql.append("ON bpm_itemtrabalhofluxo.iditemtrabalho = bpm_atribuicaofluxo.iditemtrabalho  ");
		sql.append("INNER JOIN bpm_instanciafluxo bpm_instanciafluxo  ");
		sql.append("ON bpm_instanciafluxo.idinstancia = bpm_itemtrabalhofluxo.idinstancia  ");
		sql.append("INNER JOIN execucaosolicitacao execucaosolicitacao  ");
		sql.append("on execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia  ");
		sql.append("INNER JOIN usuario usuario  ");
		sql.append("ON usuario.idusuario = bpm_itemtrabalhofluxo.idresponsavelatual	  ");
		sql.append("INNER JOIN grupo grupo  ");
		sql.append("ON grupo.idgrupo = bpm_atribuicaofluxo.idgrupo  ");
		sql.append("INNER JOIN ocorrenciasolicitacao ocorrenciasolicitacao  ");
		sql.append("ON ocorrenciasolicitacao.iditemtrabalho = bpm_itemtrabalhofluxo.iditemtrabalho  ");
		sql.append("INNER JOIN bpm_elementofluxo bpm_elementofluxo 	     ");
		sql.append("ON bpm_elementofluxo.idelemento = bpm_itemtrabalhofluxo.idelemento    ");
		sql.append("INNER JOIN solicitacaoservico solicitacaoservico    ");
		sql.append("ON solicitacaoservico.idsolicitacaoservico = ocorrenciasolicitacao.idsolicitacaoservico  ");
		sql.append("INNER JOIN servicocontrato servicocontrato ");
		sql.append("ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato  ");
		sql.append("INNER JOIN servico servico ");
		sql.append("ON servico.idservico = servicocontrato.idservico   ");
		
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			sql.append("WHERE to_char(solicitacaoservico.datahorasolicitacao, 'YYYY-MM-DD') BETWEEN ? AND ? ");
		}else{
			sql.append("WHERE solicitacaoservico.datahorasolicitacao BETWEEN ? AND ? ");
		}
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			parametro.add(formatter.format(solicitacaoServicoDTO.getDataInicio()));
			parametro.add(formatter.format(solicitacaoServicoDTO.getDataFim()));
		} else {
			parametro.add(solicitacaoServicoDTO.getDataInicio());
			parametro.add(transformaHoraFinal(solicitacaoServicoDTO.getDataFim()));
		}
		if (solicitacaoServicoDTO.getIdGrupoAtual()!= null) {
			sql.append("AND bpm_atribuicaofluxo.idgrupo = ? ");
			parametro.add(solicitacaoServicoDTO.getIdGrupoAtual());
		}
		if (solicitacaoServicoDTO.getIdSolicitacaoServico() != null) {
			sql.append("AND execucaosolicitacao.idsolicitacaoservico = ? ");
			parametro.add(solicitacaoServicoDTO.getIdSolicitacaoServico());
		}
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			sql.append("AND servicocontrato.idcontrato = ? ");
			parametro.add(solicitacaoServicoDTO.getIdContrato());
		}
		if (solicitacaoServicoDTO.getIdResponsavel() != null) {
			sql.append("AND bpm_itemtrabalhofluxo.idresponsavelatual = ? ");
			parametro.add(solicitacaoServicoDTO.getIdResponsavel());
		}
		sql.append("AND bpm_itemtrabalhofluxo.idresponsavelatual is not null ");
		sql.append("AND bpm_itemtrabalhofluxo.idinstancia is not null ");
//		sql.append("AND bpm_atribuicaofluxo.tipo<>'Acompanhamento' ");
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("nomeServico");
		listRetorno.add("situacao");
		List result = this.engine.listConvertion(SolicitacaoServicoDTO.class, list, listRetorno);
		if (result != null && !result.isEmpty()) {
			return result;
		}else{
			return Collections.emptyList();
		}
	}
	
	public Collection<SolicitacaoServicoDTO> imprimirSolicitacaoEncaminhadaFilhas(SolicitacaoServicoDTO solicitacaoServicoDTO)throws Exception {
		StringBuilder sql = new StringBuilder();
		List parametro = new ArrayList();
		
		List list = new ArrayList();
			
		sql.append("SELECT execucaosolicitacao.idsolicitacaoservico,UPPER(servico.nomeservico), UPPER(grupo.nome) as grupoAtual," +
				" UPPER(usuario.nome) as nomeSolicitante," +	" UPPER(ocorrenciasolicitacao.descricao) as nomeElementoFluxo,UPPER (solicitacaoservico.situacao),ocorrenciasolicitacao.dataregistro,ocorrenciasolicitacao.horaregistro,ocorrenciasolicitacao.dadossolicitacao,ocorrenciasolicitacao.idocorrencia,ocorrenciasolicitacao.descricao ");
		sql.append("FROM bpm_atribuicaofluxo bpm_atribuicaofluxo  ");
		sql.append("INNER JOIN bpm_itemtrabalhofluxo bpm_itemtrabalhofluxo  ");
		sql.append("ON bpm_itemtrabalhofluxo.iditemtrabalho = bpm_atribuicaofluxo.iditemtrabalho  ");
		sql.append("INNER JOIN bpm_instanciafluxo bpm_instanciafluxo  ");
		sql.append("ON bpm_instanciafluxo.idinstancia = bpm_itemtrabalhofluxo.idinstancia  ");
		sql.append("INNER JOIN execucaosolicitacao execucaosolicitacao  ");
		sql.append("on execucaosolicitacao.idinstanciafluxo = bpm_itemtrabalhofluxo.idinstancia  ");
		sql.append("INNER JOIN usuario usuario  ");
		sql.append("ON usuario.idusuario = bpm_itemtrabalhofluxo.idresponsavelatual	  ");
		sql.append("INNER JOIN grupo grupo  ");
		sql.append("ON grupo.idgrupo = bpm_atribuicaofluxo.idgrupo  ");
		sql.append("INNER JOIN ocorrenciasolicitacao ocorrenciasolicitacao  ");
		sql.append("ON ocorrenciasolicitacao.iditemtrabalho = bpm_itemtrabalhofluxo.iditemtrabalho  ");
		sql.append("INNER JOIN bpm_elementofluxo bpm_elementofluxo 	     ");
		sql.append("ON bpm_elementofluxo.idelemento = bpm_itemtrabalhofluxo.idelemento    ");
		sql.append("INNER JOIN solicitacaoservico solicitacaoservico    ");
		sql.append("ON solicitacaoservico.idsolicitacaoservico = ocorrenciasolicitacao.idsolicitacaoservico  ");
		sql.append("INNER JOIN servicocontrato servicocontrato ");
		sql.append("ON servicocontrato.idservicocontrato = solicitacaoservico.idservicocontrato  ");
		sql.append("INNER JOIN servico servico ");
		sql.append("ON servico.idservico = servicocontrato.idservico   ");
		sql.append("WHERE execucaosolicitacao.idsolicitacaoservico is not null  ");
		
		if (solicitacaoServicoDTO.getIdGrupoAtual()!= null) {
			sql.append("AND bpm_atribuicaofluxo.idgrupo = ? ");
			parametro.add(solicitacaoServicoDTO.getIdGrupoAtual());
		}
		if (solicitacaoServicoDTO.getIdSolicitacaoServico() != null) {
			sql.append("AND execucaosolicitacao.idsolicitacaoservico = ? ");
			parametro.add(solicitacaoServicoDTO.getIdSolicitacaoServico());
		}
		if (solicitacaoServicoDTO.getIdContrato() != null) {
			sql.append("AND servicocontrato.idcontrato = ? ");
			parametro.add(solicitacaoServicoDTO.getIdContrato());
		}
		if (solicitacaoServicoDTO.getIdResponsavel() != null) {
			sql.append("AND bpm_itemtrabalhofluxo.idresponsavelatual = ? ");
			parametro.add(solicitacaoServicoDTO.getIdResponsavel());
		}
		sql.append("AND bpm_itemtrabalhofluxo.idresponsavelatual is not null ");
		sql.append("AND bpm_itemtrabalhofluxo.idinstancia is not null ");
//		sql.append("AND bpm_atribuicaofluxo.tipo<>'Acompanhamento' ");
		list = this.execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idSolicitacaoServico");
		listRetorno.add("nomeServico");
		listRetorno.add("grupoAtual");
		listRetorno.add("nomeSolicitante");
		listRetorno.add("nomeElementoFluxo");
		listRetorno.add("situacao");
		listRetorno.add("dataRegistro");
		listRetorno.add("horaRegistro");
		listRetorno.add("dadosSolicitacao");
		listRetorno.add("idOcorrencia");
		listRetorno.add("descricao");
		List result = this.engine.listConvertion(SolicitacaoServicoDTO.class, list, listRetorno);
		if (result != null && !result.isEmpty()) {
			return result;
		}else{
			return Collections.emptyList();
		}
	}
	
}
