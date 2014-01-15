package br.com.centralit.citsmart.rest.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class RestExecutionDTO implements IDto {
	private Integer idRestExecution;
	private Integer idRestOperation;
	private Timestamp inputDateTime;
	private Integer idUser;
	private String inputClass;
	private String inputData;
	private String status;
	
	private RestOperationDTO restOperationDto;
	private Object input;

	public Integer getIdRestExecution(){
		return this.idRestExecution;
	}
	public void setIdRestExecution(Integer parm){
		this.idRestExecution = parm;
	}

	public Integer getIdRestOperation(){
		return this.idRestOperation;
	}
	public void setIdRestOperation(Integer parm){
		this.idRestOperation = parm;
	}

	public Timestamp getInputDateTime(){
		return this.inputDateTime;
	}
	public void setInputDateTime(Timestamp parm){
		this.inputDateTime = parm;
	}

	public String getInputData(){
		return this.inputData;
	}
	public void setInputData(String parm){
		this.inputData = parm;
	}

	public String getStatus(){
		return this.status;
	}
	public void setStatus(String parm){
		this.status = parm;
	}
	public RestOperationDTO getRestOperationDto() {
		return restOperationDto;
	}
	public void setRestOperationDto(RestOperationDTO restOperationDto) {
		this.restOperationDto = restOperationDto;
	}
	public String getInputClass() {
		return inputClass;
	}
	public void setInputClass(String inputClass) {
		this.inputClass = inputClass;
	}
	public Object getInput() {
		return input;
	}
	public void setInput(Object input) {
		this.input = input;
	}
	public Integer getIdUser() {
		return idUser;
	}
	public void setIdUser(Integer idUser) {
		this.idUser = idUser;
	}

}
