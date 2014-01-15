package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.RecursoProjetoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class RecursoProjetoDao extends CrudDaoDefaultImpl {
	public RecursoProjetoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idProjeto" ,"idProjeto", true, false, false, false));
		listFields.add(new Field("idEmpregado" ,"idEmpregado", true, false, false, false));
		listFields.add(new Field("custoHora" ,"custoHora", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RecursoProjeto";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RecursoProjetoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdProjeto(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idProjeto", "=", parm)); 
		ordenacao.add(new Order("idEmpregado"));
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
		condicao.add(new Condition("idEmpregado", "=", parm)); 
		ordenacao.add(new Order("idProjeto"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEmpregado", "=", parm));
		super.deleteByCondition(condicao);
	}
}
