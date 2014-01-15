package br.com.centralit.citajax.framework;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxFacade {
	protected HttpServletRequest request;
	protected HttpServletResponse response;
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	/*
	public Usuario getUsuario(){
		if (this.request == null) return null;
		Usuario user = CitAjaxWebUtil.getUsuario(this.request);
		return user;
	}	
	*/
}
