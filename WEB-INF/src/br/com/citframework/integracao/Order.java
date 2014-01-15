package br.com.citframework.integracao;

import java.io.Serializable;

public class Order implements Serializable{

	/**
	 * 
	 */
	
	
	
	private static final long serialVersionUID = -3708708164549646409L;
	public static final String ASC ="ASC";
	public static final String DESC ="DESC";
	
	
	private String field;
	private String typeOrder = ASC;
	
	
	
	public Order(String field, String typeOrder) {
		super();
		this.field = field;
		this.typeOrder = typeOrder;
	}

	
	public Order(String field) {
		super();
		this.field = field;

	}
	
	
	public String getField() {
		return field;
	}
	public void setField(String campo) {
		this.field = campo;
	}
	public String getTypeOrder() {
		return typeOrder;
	}
	public void setTypeOrder(String tipoOrdenacao) {
		this.typeOrder = tipoOrdenacao;
	}
	
	
	
	
	
	
	
	


}
