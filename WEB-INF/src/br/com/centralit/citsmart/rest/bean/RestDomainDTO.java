package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestDomainDTO implements IDto {
	private Integer idRestOperation;
	private Integer idRestParameter;
	private String value;
	
	public Integer getIdRestOperation() {
		return idRestOperation;
	}
	public void setIdRestOperation(Integer idRestOperation) {
		this.idRestOperation = idRestOperation;
	}
	public Integer getIdRestParameter() {
		return idRestParameter;
	}
	public void setIdRestParameter(Integer idRestParameter) {
		this.idRestParameter = idRestParameter;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
}
