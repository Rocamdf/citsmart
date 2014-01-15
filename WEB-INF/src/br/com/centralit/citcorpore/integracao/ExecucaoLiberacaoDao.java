package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExecucaoLiberacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExecucaoLiberacaoDao extends CrudDaoDefaultImpl {
	public ExecucaoLiberacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idExecucao" ,"idExecucao", true, true, false, false));
		listFields.add(new Field("idLiberacao" ,"idRequisicaoLiberacao", false, false, false, false));
		listFields.add(new Field("idFluxo" ,"idFluxo", false, false, false, false));
		listFields.add(new Field("idInstanciaFluxo" ,"idInstanciaFluxo", false, false, false, false));
		listFields.add(new Field("seqReabertura" ,"seqReabertura", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ExecucaoLiberacao";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ExecucaoLiberacaoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public ExecucaoLiberacaoDTO findByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception {
		
		List condicao = new ArrayList();
		//System.out.println("\n --> " + idInstanciaFluxo + " \n");
		condicao.add(new Condition("idInstanciaFluxo", "=", idInstanciaFluxo));
		
		Collection col = super.findByCondition(condicao, null);
		if (col == null || col.size() == 0) return null;
		return (ExecucaoLiberacaoDTO) ((List) col).get(0);
	}		
	public Collection<ExecucaoLiberacaoDTO> listByIdRequisicaoLiberacao(Integer idRequisicaoLiberacao) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idRequisicaoLiberacao", "=", idRequisicaoLiberacao));
		ordenacao.add(new Order("idExecucao", Order.DESC));
		return super.findByCondition(condicao, ordenacao);
	}		
}
