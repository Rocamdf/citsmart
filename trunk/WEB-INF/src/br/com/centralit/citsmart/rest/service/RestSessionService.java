package br.com.centralit.citsmart.rest.service;
import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtLogin;
import br.com.citframework.service.CrudServiceEjb2;
public interface RestSessionService extends CrudServiceEjb2 {
	public RestSessionDTO newSession(HttpServletRequest httpRequest, CtLogin login) throws Exception;
	public RestSessionDTO getSession(String sessionID);
	
}
