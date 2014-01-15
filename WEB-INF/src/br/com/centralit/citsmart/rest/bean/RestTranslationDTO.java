package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestTranslationDTO implements IDto {
	private Integer idRestTranslation;
	private Integer idRestOperation;
	private Integer idBusinessObject;
	private String fromValue;
	private String toValue;

	public Integer getIdRestTranslation() {
		return idRestTranslation;
	}
	public void setIdRestTranslation(Integer idRestTranslation) {
		this.idRestTranslation = idRestTranslation;
	}
	public Integer getIdRestOperation(){
		return this.idRestOperation;
	}
	public void setIdRestOperation(Integer parm){
		this.idRestOperation = parm;
	}

	public Integer getIdBusinessObject(){
		return this.idBusinessObject;
	}
	public void setIdBusinessObject(Integer parm){
		this.idBusinessObject = parm;
	}

	public String getFromValue(){
		return this.fromValue;
	}
	public void setFromValue(String parm){
		this.fromValue = parm;
	}

	public String getToValue(){
		return this.toValue;
	}
	public void setToValue(String parm){
		this.toValue = parm;
	}

}
