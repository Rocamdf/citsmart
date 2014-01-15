package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citsmart.rest.bean.RestPermissionDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RestPermissionDao extends CrudDaoDefaultImpl {
	public RestPermissionDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idRestOperation" ,"idRestOperation", true, false, false, false));
		listFields.add(new Field("idGroup" ,"idGroup", true, false, false, false));
		listFields.add(new Field("status" ,"status", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Rest_Permission";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RestPermissionDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		RestPermissionDTO permissionDto = (RestPermissionDTO) arg0;
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		if (permissionDto.getIdRestOperation() != null)
			condicao.add(new Condition("idRestOperation", "=", permissionDto.getIdRestOperation())); 
		if (permissionDto.getIdGroup() != null)
			condicao.add(new Condition("idGroup", "=", permissionDto.getIdGroup())); 
		ordenacao.add(new Order("idRestOperation"));
		return super.findByCondition(condicao, ordenacao);
	}
	public Collection findByIdOperation(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idRestOperation", "=", parm)); 
		ordenacao.add(new Order("idGroup"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdOperation(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRestOperation", "=", parm));
		super.deleteByCondition(condicao);
	}
	public Collection findByIdGroup(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idGroup", "=", parm)); 
		ordenacao.add(new Order("idRestOperation"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdGroup(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idRestUser", "=", parm));
		super.deleteByCondition(condicao);
	}	
}
