package br.com.centralit.citsmart.rest.bean;

import br.com.citframework.dto.IDto;

public class RestOperationDTO implements IDto {
	private Integer idRestOperation;
	private Integer idBatchProcessing;
	private String name;
	private String description;
	private String operationType;
	private String classType;
	private String javaClass;
	private String javaScript;
	private String status;

	public Integer getIdRestOperation(){
		return this.idRestOperation;
	}
	public void setIdRestOperation(Integer parm){
		this.idRestOperation = parm;
	}

	public Integer getIdBatchProcessing(){
		return this.idBatchProcessing;
	}
	public void setIdBatchProcessing(Integer parm){
		this.idBatchProcessing = parm;
	}

	public String getName(){
		return this.name;
	}
	public void setName(String parm){
		this.name = parm;
	}

	public String getDescription(){
		return this.description;
	}
	public void setDescription(String parm){
		this.description = parm;
	}

	public String getOperationType(){
		return this.operationType;
	}
	public void setOperationType(String parm){
		this.operationType = parm;
	}

	public String getClassType(){
		return this.classType;
	}
	public void setClassType(String parm){
		this.classType = parm;
	}

	public String getJavaClass(){
		return this.javaClass;
	}
	public void setJavaClass(String parm){
		this.javaClass = parm;
	}

	public String getJavaScript(){
		return this.javaScript;
	}
	public void setJavaScript(String parm){
		this.javaScript = parm;
	}

	public String getStatus(){
		return this.status;
	}
	public void setStatus(String parm){
		this.status = parm;
	}

}
