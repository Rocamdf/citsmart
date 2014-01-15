package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestParameterDTO implements IDto {
	private Integer idRestParameter;
	private String identifier;
	private String description;
	
	public Integer getIdRestParameter() {
		return idRestParameter;
	}
	public void setIdRestParameter(Integer idRestParameter) {
		this.idRestParameter = idRestParameter;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

}
