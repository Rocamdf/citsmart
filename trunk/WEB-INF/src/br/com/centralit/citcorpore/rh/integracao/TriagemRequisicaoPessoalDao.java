package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.TriagemRequisicaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({"unchecked", "rawtypes"})
public class TriagemRequisicaoPessoalDao extends CrudDaoDefaultImpl {
	private static final long serialVersionUID = 1L;
	
	public TriagemRequisicaoPessoalDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idTriagem" ,"idTriagem", true, true, false, false));
		listFields.add(new Field("idSolicitacaoServico" ,"idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idCurriculo" ,"idCurriculo", false, false, false, false));
		listFields.add(new Field("idItemTrabalhoEntrevistaRH" ,"idItemTrabalhoEntrevistaRH", false, false, false, false));
		listFields.add(new Field("idItemTrabalhoEntrevistaGestor" ,"idItemTrabalhoEntrevistaGestor", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RH_TriagemRequisicaoPessoal";
	}
	public Collection list() throws Exception {
        List list = new ArrayList();
        list.add(new Order("idTriagem"));
        return super.list(list);
	}

	public Class getBean() {
		return TriagemRequisicaoPessoalDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
		ordenacao.add(new Order("idTriagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findDisponiveisEntrevistaRH(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
		condicao.add(new Condition("idItemTrabalhoEntrevistaRH", "is", null)); 
		ordenacao.add(new Order("idTriagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findDisponiveisEntrevistaGestor(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
		condicao.add(new Condition("idItemTrabalhoEntrevistaGestor", "is", null)); 
		ordenacao.add(new Order("idTriagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdItemTrabalhoRH(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idItemTrabalhoEntrevistaRH", "=", parm)); 
		ordenacao.add(new Order("idTriagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdItemTrabalhoGestor(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idItemTrabalhoEntrevistaGestor", "=", parm)); 
		ordenacao.add(new Order("idTriagem"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", parm));
		super.deleteByCondition(condicao);
	}
}
