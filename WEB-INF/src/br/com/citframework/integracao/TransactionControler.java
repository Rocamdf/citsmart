package br.com.citframework.integracao;


public interface TransactionControler {


	public boolean isStarted();

	public void setStarted(boolean started);

	public String getDataBaseAlias();

	public void setDataBaseAlias(String dataBaseAlias);

	public Object getTransactionObject() throws Exception;

	public void setTransactionObject(Object transactionObject) throws Exception;

	public void start() throws Exception;

	public void commit() throws Exception;

	public void rollback() throws Exception;

	public void close() throws Exception;

}