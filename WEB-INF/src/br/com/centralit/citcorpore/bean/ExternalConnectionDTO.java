package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ExternalConnectionDTO implements IDto {
	private Integer idExternalConnection;
	private String nome;
	private String tipo;
	private String urlJdbc;
	private String jdbcDbName;
	private String jdbcDriver;
	private String jdbcUser;
	private String jdbcPassword;
	private String fileName;
	private String schemadb;

	public Integer getIdExternalConnection(){
		return this.idExternalConnection;
	}
	public void setIdExternalConnection(Integer parm){
		this.idExternalConnection = parm;
	}

	public String getNome(){
		return this.nome;
	}
	public void setNome(String parm){
		this.nome = parm;
	}

	public String getTipo(){
		return this.tipo;
	}
	public void setTipo(String parm){
		this.tipo = parm;
	}

	public String getUrlJdbc(){
		return this.urlJdbc;
	}
	public void setUrlJdbc(String parm){
		this.urlJdbc = parm;
	}

	public String getJdbcDbName(){
		return this.jdbcDbName;
	}
	public void setJdbcDbName(String parm){
		this.jdbcDbName = parm;
	}

	public String getJdbcDriver(){
		return this.jdbcDriver;
	}
	public void setJdbcDriver(String parm){
		this.jdbcDriver = parm;
	}

	public String getJdbcUser(){
		return this.jdbcUser;
	}
	public void setJdbcUser(String parm){
		this.jdbcUser = parm;
	}

	public String getJdbcPassword(){
		return this.jdbcPassword;
	}
	public void setJdbcPassword(String parm){
		this.jdbcPassword = parm;
	}

	public String getFileName(){
		return this.fileName;
	}
	public void setFileName(String parm){
		this.fileName = parm;
	}
	public String getSchemadb() {
		return schemadb;
	}
	public void setSchemadb(String schemadb) {
		this.schemadb = schemadb;
	}

}
