package br.com.citframework.excecao;

public class DuplicateUniqueException extends PersistenceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4115099327420847198L;

	public DuplicateUniqueException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DuplicateUniqueException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DuplicateUniqueException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
