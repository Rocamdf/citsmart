package br.com.centralit.citsmart.rest.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class RestLogDTO implements IDto {
	private Integer idRestLog;
	private Integer idRestExecution;
	private Timestamp dateTime;
	private String status;
	private String resultClass;	
	private String resultData;

	public Integer getIdRestLog(){
		return this.idRestLog;
	}
	public void setIdRestLog(Integer parm){
		this.idRestLog = parm;
	}

	public Integer getIdRestExecution(){
		return this.idRestExecution;
	}
	public void setIdRestExecution(Integer parm){
		this.idRestExecution = parm;
	}
	
	public Timestamp getDateTime() {
		return dateTime;
	}
	public void setDateTime(Timestamp dateTime) {
		this.dateTime = dateTime;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getResultData(){
		return this.resultData;
	}
	public void setResultData(String parm){
		this.resultData = parm;
	}
	public String getResultClass() {
		return resultClass;
	}
	public void setResultClass(String resultClass) {
		this.resultClass = resultClass;
	}

}
