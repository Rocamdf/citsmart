package br.com.citframework.log;

import org.apache.log4j.Logger;

public class Log4jLog implements Log{

	public void registraLog(String mensagem, Class classe, String tipoMensagem) throws Exception {
		Logger log = Logger.getLogger(classe);
		if(tipoMensagem.equals(DEBUG))
		  log.debug(mensagem);
		else if(tipoMensagem.equals(ERROR))
			log.error(mensagem);
		else if(tipoMensagem.equals(FATAL))
			log.fatal(mensagem);
		else if(tipoMensagem.equals(INFO))
			log.info(mensagem);
		else if(tipoMensagem.equals(WARN))
			log.warn(mensagem);
	}

	public void registraLog(Exception e, Class classe, String tipoMensagem)
			throws Exception {
		Logger log = Logger.getLogger(classe);
		if(tipoMensagem.equals(DEBUG))
			  log.debug(e);
		else if(tipoMensagem.equals(ERROR))
			log.error(e);
		else if(tipoMensagem.equals(FATAL))
			log.fatal(e);
		else if(tipoMensagem.equals(INFO))
			log.info(e);
		else if(tipoMensagem.equals(WARN))
			log.warn(e);
	}

}
