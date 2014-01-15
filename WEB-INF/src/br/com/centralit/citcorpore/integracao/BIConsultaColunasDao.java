package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.BIConsultaColunasDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class BIConsultaColunasDao extends CrudDaoDefaultImpl {
	public BIConsultaColunasDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idConsultaColuna" ,"idConsultaColuna", true, true, false, false));
		listFields.add(new Field("idConsulta" ,"idConsulta", false, false, false, false));
		listFields.add(new Field("nomeColuna" ,"nomeColuna", false, false, false, false));
		listFields.add(new Field("tipoFiltro" ,"tipoFiltro", false, false, false, false));
		listFields.add(new Field("ordem" ,"ordem", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "BI_ConsultaColunas";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return BIConsultaColunasDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdConsulta(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idConsulta", "=", parm)); 
		ordenacao.add(new Order("ordem"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdConsulta(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idConsulta", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByOrdem(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("ordem", "=", parm)); 
		ordenacao.add(new Order(""));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByOrdem(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("ordem", "=", parm));
		super.deleteByCondition(condicao);
	}
}
