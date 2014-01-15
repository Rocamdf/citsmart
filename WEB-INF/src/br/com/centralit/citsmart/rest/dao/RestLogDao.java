package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestLogDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestLogDao extends CrudDaoDefaultImpl {
	public RestLogDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idRestLog" ,"idRestLog", true, true, false, false));
		listFields.add(new Field("idRestExecution" ,"idRestExecution", false, false, false, false));
		listFields.add(new Field("dateTime" ,"dateTime", false, false, false, false));
		listFields.add(new Field("status" ,"status", false, false, false, false));
		listFields.add(new Field("resultData" ,"resultData", false, false, false, false));
		listFields.add(new Field("resultClass" ,"resultClass", false, false, false, false));		
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Rest_Log";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RestLogDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdRestExecution(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idRestExecution", "=", parm)); 
		ordenacao.add(new Order("idRestLog"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdRestExecution(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRestExecution", "=", parm));
		super.deleteByCondition(condicao);
	}
}
