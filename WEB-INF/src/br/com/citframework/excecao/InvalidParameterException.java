package br.com.citframework.excecao;

public class InvalidParameterException extends PersistenceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4748754751954771620L;

	public InvalidParameterException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidParameterException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidParameterException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
