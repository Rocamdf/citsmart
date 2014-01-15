package br.com.citframework.excecao;

public class ConnectionException extends PersistenceException{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8065909272818855345L;

	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ConnectionException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ConnectionException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
