package br.com.citframework.menu;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IRenderMenu {
	public String render(Collection colMenus, String contextName, HttpServletRequest request, HttpServletResponse response) throws Exception;
}
