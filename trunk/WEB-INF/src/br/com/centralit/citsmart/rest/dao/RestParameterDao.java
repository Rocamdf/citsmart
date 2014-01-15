package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestParameterDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestParameterDao extends CrudDaoDefaultImpl {
	public RestParameterDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idRestParameter" ,"idRestParameter", true, true, false, false));
		listFields.add(new Field("identifier" ,"identifier", false, false, false, false));
		listFields.add(new Field("description" ,"description", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Rest_Parameter";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RestParameterDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public RestParameterDTO findByIdentifier(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("identifier", "=", parm)); 
		ordenacao.add(new Order("idRestParameter"));
		List<RestParameterDTO>  list = (List<RestParameterDTO>) super.findByCondition(condicao, ordenacao);
		if (list != null && !list.isEmpty())
			return list.get(0);
		else
			return null;
	}
}
