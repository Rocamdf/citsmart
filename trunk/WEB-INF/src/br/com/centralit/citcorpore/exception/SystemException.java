/*
 * Created on 20/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;

import java.rmi.RemoteException;

/**
 * @author tellus SA
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SystemException extends GeneralException {

	/**
	 * 
	 */
	public SystemException() {
		super();

	}
	
	public SystemException( Throwable cause ) {
		super(cause);
		// 
	}
	
	public SystemException( RuntimeException cause ) {
		super(cause) ;
		code="sis004" ;
		 
	}
	public SystemException( RemoteException cause ) {
		super(cause) ;
		code="sis005" ;
		 
	}

	/**
	 * @param code
	 */
	public SystemException(String code) {
		super(code);

	}

	/**
	 * @param code
	 * @param arg1
	 */
	public SystemException(String code, String arg1) {
		super(code, arg1);

	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 */
	public SystemException(String code, String arg1, String arg2) {
		super(code, arg1, arg2);

	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public SystemException(
		String code,
		String arg1,
		String arg2,
		String arg3) {
		super(code, arg1, arg2, arg3);

	}

	/**
	 * @param code
	 * @param cause
	 */
	public SystemException(String code, Throwable cause) {
		super(code, cause);

	}

	/**
	 * @param code
	 * @param arg1
	 * @param cause
	 */
	public SystemException(String code, String arg1, Throwable cause) {
		super(code, arg1, cause);

	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param cause
	 */
	public SystemException(
		String code,
		String arg1,
		String arg2,
		Throwable cause) {
		super(code, arg1, arg2, cause);
	
	}

	/**
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param cause
	 */
	public SystemException(
		String code,
		String arg1,
		String arg2,
		String arg3,
		Throwable cause) {
		super(code, arg1, arg2, arg3, cause);

	}

}
