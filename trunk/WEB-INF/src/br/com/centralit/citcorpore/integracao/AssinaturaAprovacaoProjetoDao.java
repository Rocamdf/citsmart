package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AssinaturaAprovacaoProjetoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AssinaturaAprovacaoProjetoDao extends CrudDaoDefaultImpl{
	
	public AssinaturaAprovacaoProjetoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idAssinaturaAprovacaoProjeto" ,"idAssinaturaAprovacaoProjeto", true, true, false, false));
		listFields.add(new Field("idProjeto" ,"idProjeto", true, false, false, false));
		listFields.add(new Field("idEmpregado" ,"idEmpregadoAssinatura", true, false, false, false));
		listFields.add(new Field("papel" ,"papel", false, false, false, false));
		listFields.add(new Field("ordem" ,"ordem", false, false, false, false));
		return listFields;
	}
	
	public String getTableName() {
		return this.getOwner() + "AssinaturaAprovacaoProjeto";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return AssinaturaAprovacaoProjetoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdProjeto(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProjeto", "=", parm)); 
		ordenacao.add(new Order("idEmpregadoAssinatura"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdProjeto(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idProjeto", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdEmpregado(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idEmpregadoAssinatura", "=", parm)); 
		ordenacao.add(new Order("idProjeto"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEmpregadoAssinatura", "=", parm));
		super.deleteByCondition(condicao);
	}

}
