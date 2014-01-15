/*
 * Created on 20/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;

/**
 * @author Tellus SA
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BusinessException extends GeneralException {

	/**
	 * 
	 */
	public BusinessException() {
		super();
		// 
	}
	
	public BusinessException(Throwable cause) {
		super(cause);
		// 
	}

	/**
	 * @param code
	 */
	public BusinessException(String code) {
		super(code);
		// 
	}

	/**
	 * @param code
	 * @param arg1
	 */
	public BusinessException(String code, String arg1) {
		super(code, arg1);
		// 
	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 */
	public BusinessException(String code, String arg1, String arg2) {
		super(code, arg1, arg2);
		// 
	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public BusinessException(
		String code,
		String arg1,
		String arg2,
		String arg3) {
		super(code, arg1, arg2, arg3);
		// 
	}

	/**
	 * @param code
	 * @param cause
	 */
	public BusinessException(String code, Throwable cause) {
		super(code, cause);
		// 
	}

	/**
	 * @param code
	 * @param arg1
	 * @param cause
	 */
	public BusinessException(String code, String arg1, Throwable cause) {
		super(code, arg1, cause);
		// 
	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param cause
	 */
	public BusinessException(
		String code,
		String arg1,
		String arg2,
		Throwable cause) {
		super(code, arg1, arg2, cause);
		// 
	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param cause
	 */
	public BusinessException(
		String code,
		String arg1,
		String arg2,
		String arg3,
		Throwable cause) {
		super(code, arg1, arg2, arg3, cause);
		// 
	}

}
