package br.com.citframework.excecao;

public class ServiceNotFoundException extends ServiceException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7789096066055617011L;

	public ServiceNotFoundException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public ServiceNotFoundException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public ServiceNotFoundException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}
	
	

}
