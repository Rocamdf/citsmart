package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class OcorrenciaDao extends CrudDaoDefaultImpl {
	
	private static final String SQL_OCORRENCIA_DEMANDA = "SELECT O.ocorrencia, O.tipoOcorrencia, O.respostaOcorrencia, O.data, E.nome, O.idOcorrencia, O.idDemanda " +
		"FROM OCORRENCIAS O " +
		" INNER JOIN EMPREGADOS E on E.idEmpregado = O.idEmpregado " +
		"where idDemanda = ? order by data";
	
	public OcorrenciaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2983316142102074344L;

	public Class getBean() {
		return OcorrenciaDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idOcorrencia", "idOcorrencia", true, true, false, false));
		listFields.add(new Field("idDemanda", "idDemanda", false, false, false, false));
		listFields.add(new Field("ocorrencia", "ocorrencia", false, false, false, false));
		listFields.add(new Field("tipoOcorrencia", "tipoOcorrencia", false, false, false, false));
		listFields.add(new Field("respostaOcorrencia", "respostaOcorrencia", false, false, false, false));
		listFields.add(new Field("data", "data", false, false, false, false));
		listFields.add(new Field("idEmpregado", "idEmpregado", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "OCORRENCIAS";
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Collection findByDemanda(Integer idDemanda) throws Exception {
		Object[] objs = new Object[] {idDemanda};
		
		String sql = SQL_OCORRENCIA_DEMANDA;
		
		List lista = this.execSQL(sql, objs);
		
		List listRetorno = new ArrayList();
		listRetorno.add("ocorrencia");
		listRetorno.add("tipoOcorrencia");
		listRetorno.add("respostaOcorrencia");
		listRetorno.add("data");
		listRetorno.add("nomeEmpregado");
		listRetorno.add("idOcorrencia");
		listRetorno.add("idDemanda");

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0) return null;
		return result;
	}
	
	public void updateResposta(IDto obj) throws Exception{
		OcorrenciaDTO ocorrencia = (OcorrenciaDTO)obj;
		OcorrenciaDTO ocorrenciaUpdate = new OcorrenciaDTO();
		
		ocorrenciaUpdate.setIdOcorrencia(ocorrencia.getIdOcorrencia());
		ocorrenciaUpdate.setRespostaOcorrencia(ocorrencia.getRespostaOcorrencia());
				
		super.updateNotNull(ocorrenciaUpdate);
	}	
	
	public Collection<OcorrenciaDTO> findByIdSolicitacao(Integer idSolicitacao) throws Exception{
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select ocorrencia from ocorrenciasolicitacao where idsolicitacaoservico = ?");
		parametro.add(idSolicitacao);

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("ocorrencia");


		if (list != null && !list.isEmpty()) {
			return (Collection<OcorrenciaDTO>) this.listConvertion(getBean(), list, listRetorno);
		} else {
			return null;
		}
	}
	
	public OcorrenciaDTO findSiglaGrupoExecutorByIdSolicitacao(Integer idSolicitacao) throws Exception{
		List parametro = new ArrayList();
		List listRetorno = new ArrayList();
		List list = new ArrayList();
		
		StringBuffer sql = new StringBuffer();
		sql.append("select  sigla from ocorrenciasolicitacao o "
				+ "inner join solicitacaoservico ss on o.idsolicitacaoservico = ss.idsolicitacaoservico "
				+ "inner join servicocontrato sc on sc.idservicocontrato = ss.idservicocontrato "
				+ "inner join grupo g on sc.idgrupoexecutor = g.idgrupo "
				+ "where o.idsolicitacaoservico = ?");
		
		parametro.add(idSolicitacao);

		list = this.execSQL(sql.toString(), parametro.toArray());

		listRetorno.add("sigla");


		if (list != null && !list.isEmpty()) {
			return (OcorrenciaDTO) (this.listConvertion(getBean(), list, listRetorno)).get(0);
		} else {
			return null;
		}
	}
	
}
