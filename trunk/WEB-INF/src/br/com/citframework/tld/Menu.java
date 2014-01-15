package br.com.citframework.tld;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import br.com.citframework.dto.Usuario;
import br.com.citframework.menu.IRenderMenu;
import br.com.citframework.menu.MenuConfig;
import br.com.citframework.menu.MenuItem;
import br.com.citframework.security.Access;
import br.com.citframework.security.AccessConfig;
import br.com.citframework.util.Constantes;

public class Menu extends BodyTagSupport{
	private static final String FORWARD_SLASH = "/";
	private static final long serialVersionUID = -3103179786470352866L;
	private static final Logger LOGGER = Logger.getLogger(Menu.class);

	public int doStartTag() throws JspException {
		MenuConfig menuConfig = null;
		try {
			menuConfig = MenuConfig.getInstance(pageContext.getServletContext());
		} catch (Exception e) {
			e.printStackTrace();
			throw new JspException(e);
		}
		if (menuConfig == null){
			throw new JspException("MenuConfig Esta Nulo !!!!!!!!!! Verificar!!!!!!!!!");
		}
		
		Collection colMenus = menuConfig.getMenuItens();
		Usuario usuario = (Usuario) pageContext.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO"));
		LOGGER.debug("Usuario visualizando o menu\r\n" + usuario);
		if (colMenus != null){
			LOGGER.debug("Quantidade de itens na raiz do menu antes da filtragem: " + colMenus.size());
		}
		//colMenus = filtrarMenuUsuario(colMenus, usuario);
		if (colMenus != null){
			LOGGER.debug("Quantidade de itens na raiz do menu depois da filtragem: " + colMenus.size());
		}
		
		if (colMenus != null){
			try {
				String str = StringUtils.EMPTY;
				try {
					//pageContext.getResponse();
					String classeMenu = Constantes.getValue("CLASSE_MENU");
					if (classeMenu == null){
						LOGGER.error("CLASSE_MENU: NAO DEFINIDO NO ARQUIVO DE CONSTANTES.PROPERTIES!");
						System.out.println("CLASSE_MENU: NAO DEFINIDO NO ARQUIVO DE CONSTANTES.PROPERTIES!");
					}else{					
						Class classMenu = Class.forName(classeMenu);
						if (classMenu == null){
							LOGGER.error("CLASSE_MENU: CLASSE INEXISTENTE!");
							System.out.println("CLASSE_MENU: CLASSE INEXISTENTE!");							
						}else{
							Object objMenuObj = classMenu.newInstance();
							IRenderMenu objRenderMenu = (IRenderMenu)objMenuObj;
							str = objRenderMenu.render(colMenus, 
									((HttpServletRequest) pageContext.getRequest()).getContextPath(), 
									(HttpServletRequest) pageContext.getRequest(), 
									(HttpServletResponse) pageContext.getResponse());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					throw new JspException(e);
				}
				pageContext.getOut().print(str);
			} catch (IOException e) {
				e.printStackTrace();
				throw new JspException(e);
			}
		}
		return SKIP_BODY;
	}
	
	public List filtrarMenuUsuario(Collection/*<MenuItem>*/ menuItemCollection, Usuario usuario) {
		if (menuItemCollection == null || menuItemCollection.size() == 0)
			return Collections.EMPTY_LIST;
		
		if (usuario == null)
			return Collections.EMPTY_LIST;
		
		ArrayList itemList = new ArrayList();
		
		for (Iterator i = menuItemCollection.iterator(); i.hasNext();) {
			MenuItem item = (MenuItem) i.next();
			String path = item.getPath();
			//if 'path' is blank, item is top level for some itens 
			if (StringUtils.isBlank(path)) {
				Collection menuItens = item.getMenuItens();
				if (menuItens != null && menuItens.size() != 0) {
					menuItens = filtrarMenuUsuario(menuItens, usuario);
					if (menuItens != null && menuItens.size() != 0) {
						item.setMenuItens(menuItens);
						itemList.add(item);
					}
				}
			} else {
				path = FORWARD_SLASH + path;
				Access access = AccessConfig.getAccess(path);
				if (access == null) {
					LOGGER.debug("NO mapping for path: " + path);
				} else {
					String acessosUsuario = usuario.getAcessos();
					if (access.hasAccess(acessosUsuario)) {
						LOGGER.debug("User '" + usuario.getIdUsuario() + "' has access to: " + path);
						itemList.add(item);
					} else {
						LOGGER.debug("User '" + usuario.getIdUsuario() + "' has NO access to: " + path);
					}
				}
			}
		}
		
		return itemList;
	}
}