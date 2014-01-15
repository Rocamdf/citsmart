package br.com.citframework.excecao;

public class FKReferenceException extends PersistenceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8924754433488426943L;

	public FKReferenceException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public FKReferenceException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public FKReferenceException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
