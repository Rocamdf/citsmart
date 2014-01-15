package br.com.centralit.citgerencial.bean;

import br.com.citframework.dto.IDto;

public class GerencialFieldDTO implements IDto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2517333768182961561L;
	private String name;
	private String width;
	private String type;
	private String title;
	private boolean totals;
	private boolean count;
	private Integer decimals;
	private String mask;
	private Class classField;
	public boolean isCount() {
		return count;
	}
	public void setCount(boolean count) {
		this.count = count;
	}
	public Integer getDecimals() {
		return decimals;
	}
	public void setDecimals(Integer decimals) {
		this.decimals = decimals;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isTotals() {
		return totals;
	}
	public void setTotals(boolean totals) {
		this.totals = totals;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getWidth() {
		return width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	public Class getClassField() {
		return classField;
	}
	public void setClassField(Class classField) {
		this.classField = classField;
	}
}
