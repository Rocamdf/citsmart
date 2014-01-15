package br.com.citframework.excecao;

public class InvalidTransactionControler extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3690400574717330647L;

	public InvalidTransactionControler(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionControler(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public InvalidTransactionControler(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	
	

}
