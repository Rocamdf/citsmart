package br.com.citframework.log;

@SuppressWarnings("rawtypes")
public interface Log {
	public static String DEBUG = "DEBUG";
	public static String ERROR = "ERROR";
	public static String FATAL = "FATAL";
	public static String INFO = "INFO";
	public static String WARN = "WARN";
	public static String SEPARADOR = "#|#";
	
	public void registraLog(String mensagem, Class classe, String tipoMensagem) throws Exception;
	public void registraLog(Exception e, Class classe, String tipoMensagem) throws Exception;

}
