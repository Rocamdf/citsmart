package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestOperationDao extends CrudDaoDefaultImpl {
	public RestOperationDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idRestOperation" ,"idRestOperation", true, true, false, false));
		listFields.add(new Field("idBatchProcessing" ,"idBatchProcessing", false, false, false, false));
		listFields.add(new Field("name" ,"name", false, false, false, false));
		listFields.add(new Field("description" ,"description", false, false, false, false));
		listFields.add(new Field("operationType" ,"operationType", false, false, false, false));
		listFields.add(new Field("classType" ,"classType", false, false, false, false));
		listFields.add(new Field("javaClass" ,"javaClass", false, false, false, false));
		listFields.add(new Field("javaScript" ,"javaScript", false, false, false, false));
		listFields.add(new Field("status" ,"status", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Rest_Operation";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RestOperationDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdBatchProcessing(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idBatchProcessing", "=", parm)); 
		ordenacao.add(new Order("idRestOperation"));
		return super.findByCondition(condicao, ordenacao);
	}
	public RestOperationDTO findByName(String name) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("name", "=", name)); 
		List<RestOperationDTO> result = (List<RestOperationDTO>) super.findByCondition(condicao, ordenacao);
		if (result != null && !result.isEmpty())
			return result.get(0);
		else
			return null;
	}	
	public void deleteByIdBatchProcessing(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idBatchProcessing", "=", parm));
		super.deleteByCondition(condicao);
	}
}
