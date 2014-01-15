package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestPermissionDTO implements IDto {
	private Integer idRestOperation;
	private Integer idGroup;
	private String status;
	
	public Integer getIdRestOperation() {
		return idRestOperation;
	}
	public void setIdRestOperation(Integer idRestOperation) {
		this.idRestOperation = idRestOperation;
	}
	public Integer getIdGroup() {
		return idGroup;
	}
	public void setIdGroup(Integer idGroup) {
		this.idGroup = idGroup;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	

}
