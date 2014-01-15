package br.com.citframework.excecao;

public class InvalidTransactionObjectException extends PersistenceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1749546277700450972L;

	public InvalidTransactionObjectException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionObjectException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionObjectException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
