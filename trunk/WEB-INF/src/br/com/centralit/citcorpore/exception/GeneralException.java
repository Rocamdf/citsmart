/*
 * Created on 18/03/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.exception;

import java.io.Serializable;
import java.util.StringTokenizer;


/**
 * @author Tellus SA
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public abstract class GeneralException extends Exception implements Serializable {
	
	protected String code ;
	protected String arg1 ;
	protected String arg2 ;
	protected String arg3 ;
	protected Throwable causa ;

	protected GeneralException() {}
	
	
	protected GeneralException( Throwable cause ) {
		this.causa = cause ;
	}

	/**
	 * 
	 * @param code ( String ) :: Código da exceção. Deve estar definido no arquivo resources.properties
	 */
	public GeneralException(String code) {
		super(code);
		this.code=code;
		this.arg2 = null ;
		this.arg3 = null ;
		this.arg1 = null ;
	
	}
	
	/**
	 * 
	 * @param code
	 * @param arg1
	 */
	public GeneralException(String code, String arg1) {
		super(code);
		this.code=code;
		this.arg1 = arg1 ;
		this.arg2 = null ;
		this.arg3 = null ;
	
	}

	/**
	 * 
	 * @param code
	 * @param arg1
	 * @param arg2
	 */
	public GeneralException(String code, String arg1, String arg2) {
		super(code);
		this.code=code;
		this.arg1 = arg1 ;
		this.arg2 = arg2 ;
		this.arg3 = null ;
	
	}

	/**
	 * 
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 */
	public GeneralException(String code, String arg1, String arg2, String arg3) {
		super(code);
		this.code=code;
		this.arg1 = arg1 ;
		this.arg2 = arg2 ;
		this.arg3 = arg3 ;
	}

	/**
	 * 
	 * @param code
	 * @param cause
	 */
	public GeneralException(String code, Throwable cause) {
		this.causa = cause ;
		this.code = code ;
		this.arg2 = null ;
		this.arg3 = null ;
		this.arg1 = null ;
	}
	
	/**
	 * 
	 * @param code
	 * @param arg1
	 * @param cause
	 */
	public GeneralException(String code, String arg1, Throwable cause) {
		this.causa = cause ;
		this.code = code ;
		this.arg1 = arg1 ;
		this.arg2 = null ;
		this.arg3 = null ;

	}

	/**
	 * 
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param cause
	 */
	public GeneralException(String code, String arg1, String arg2, Throwable cause) {
		this.causa = cause ;
		this.code = code ;
		this.arg1 = arg1 ;
		this.arg2 = arg2 ;
		this.arg3 = null ;

	}
	
	/**
	 * 
	 * @param code
	 * @param arg1
	 * @param arg2
	 * @param arg3
	 * @param cause
	 */
	public GeneralException(String code, String arg1, String arg2, String arg3, Throwable cause) {
		this.causa = cause ;
		this.code = code ;
		this.arg1 = arg1 ;
		this.arg2 = arg2 ;
		this.arg3 = arg3 ;

	}
	
	public String toHTML() {
		String html = "<li><b>" + toMessage() + "</b></li><br>" ;
		return html ;
		
	}
	public String getMensagem() {
		return toMessage() ;
	}

	public String getMessage() {
		return toMessage() ;
	}
	
	/**
	 * Texto HTML das mensagens de erro
	 * @return	String
	 */
	public String toMessage() {
		TradutorExcecao tradutor;
		String html = null ;
		String message = "" ;
		try {
			if (code == null){
				return "Codigo de erro nao informado (nulo)!";
			}
			tradutor = TradutorExcecao.getInstance();
			String resource = tradutor.getMensagem(code) ;
			if(resource == null ) message = "Recurso não encontrado: " + code ;
			StringTokenizer st = new StringTokenizer(resource,"|") ;
			if (!st.hasMoreElements()){
				message = resource;
			}
			while( st.hasMoreElements() ) {
				String arg = null;
				String partMessage = st.nextToken();
				if( isArg( partMessage ) ) {
					arg = getArg( partMessage ) ;
					if(arg==null) arg = "" ;
					message = message + arg + " ";
				} else {
					message = message + partMessage + " ";
				}
			}
		} catch( Exception e ) {
			e.printStackTrace() ;
		}
		
		return message ;
	}
	
	
	/**
	 * 
	 * @param partMessage
	 * @return
	 */
	private boolean isArg(String partMessage) {
		if( partMessage.equals("1") || partMessage.equals("2") || partMessage.equals("3"))
			return true ;
		return false ;
	}
	
	/**
	 * 
	 * @param number
	 * @return
	 */
	private String getArg(String number) {
		String arg = null ;
		if("1".equals(number) ) arg = this.arg1 ;
		if("2".equals(number) ) arg = this.arg2 ;
		if("3".equals(number) ) arg = this.arg3 ;
		return arg;
	}
	
	
	public String toStackTrace() {
		
		Writer writer = new Writer();
		String mensagem = "" ;
		
		if( getCause() != null ) {
			getCause().printStackTrace( writer ) ;
			mensagem = "Causa =======================================================\n" ;
			mensagem = mensagem + writer.getBuffer().toString() + "\n" ;
		}
		
		writer = new Writer();
		this.printStackTrace( writer ) ;
		mensagem = mensagem + "Excecao =====================================================\n" ;
		mensagem = mensagem + writer.getBuffer().toString() + "\n" ;
		
		return mensagem ;
	}
		
	/**
	 * @return
	 */
	public Throwable getCause() {
		return causa;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
	}

}
