package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestExecutionDao extends CrudDaoDefaultImpl {
	public RestExecutionDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idRestExecution" ,"idRestExecution", true, true, false, false));
		listFields.add(new Field("idRestOperation" ,"idRestOperation", false, false, false, false));
		listFields.add(new Field("idUser" ,"idUser", false, false, false, false));
		listFields.add(new Field("inputDateTime" ,"inputDateTime", false, false, false, false));
		listFields.add(new Field("inputClass" ,"inputClass", false, false, false, false));
		listFields.add(new Field("inputData" ,"inputData", false, false, false, false));
		listFields.add(new Field("status" ,"status", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Rest_Execution";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RestExecutionDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdRestOperation(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idRestOperation", "=", parm)); 
		ordenacao.add(new Order("idRestExecution"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdRestOperation(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRestOperation", "=", parm));
		super.deleteByCondition(condicao);
	}
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}
}
