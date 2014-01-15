package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.RequisicaoIdiomaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RequisicaoIdiomaDao extends CrudDaoDefaultImpl {
	public RequisicaoIdiomaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("idIdioma" ,"idIdioma", true, false, false, false));
		listFields.add(new Field("obrigatorio" ,"obrigatorio", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RH_RequisicaoIdioma";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RequisicaoIdiomaDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
		ordenacao.add(new Order("idIdioma"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", parm));
		super.deleteByCondition(condicao);
	}
}
