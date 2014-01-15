package br.com.citframework.excecao;

public class TransactionOperationException extends PersistenceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5324157786326109801L;

	public TransactionOperationException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public TransactionOperationException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public TransactionOperationException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
