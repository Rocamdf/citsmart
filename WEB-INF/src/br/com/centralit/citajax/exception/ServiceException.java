package br.com.centralit.citajax.exception;

public class ServiceException extends Exception{



	/**
	 * 
	 */
	private static final long serialVersionUID = 5524978861966390783L;

	public ServiceException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	public ServiceException(String arg0) {
		super(arg0);
		
	}

	public ServiceException(Throwable arg0) {
		super(arg0);
		
	}
	
	


}
