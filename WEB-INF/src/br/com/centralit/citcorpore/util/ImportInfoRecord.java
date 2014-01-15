package br.com.centralit.citcorpore.util;

import java.util.Collection;

public class ImportInfoRecord {
	private String tableName;
	private String origem;
	private Collection colFields;
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Collection getColFields() {
		return colFields;
	}
	public void setColFields(Collection colFields) {
		this.colFields = colFields;
	}
	public String getOrigem() {
		return origem;
	}
	public void setOrigem(String origem) {
		this.origem = origem;
	}
}
