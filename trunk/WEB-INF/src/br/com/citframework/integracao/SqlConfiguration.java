package br.com.citframework.integracao;

import java.io.Serializable;
import java.util.List;

public class SqlConfiguration implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String sql;
	private Object[] paramentros;
	private List camposRetorno;
	
	
	
	
	
	public SqlConfiguration(String sql, Object[] paramentros, List camposRetorno) {
		super();
		this.sql = sql;
		this.paramentros = paramentros;
		this.camposRetorno = camposRetorno;
	}

	
	public SqlConfiguration(String sql, Object[] paramentros) {
		super();
		this.sql = sql;
		this.paramentros = paramentros;
	}
	
	public List getCamposRetorno() {
		return camposRetorno;
	}
	public void setCamposRetorno(List camposRetorno) {
		this.camposRetorno = camposRetorno;
	}
	public Object[] getParamentros() {
		return paramentros;
	}
	public void setParamentros(Object[] paramentros) {
		this.paramentros = paramentros;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	
	
	
	
	
	

}
