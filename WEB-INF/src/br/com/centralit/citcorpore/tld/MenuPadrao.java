package br.com.centralit.citcorpore.tld;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
/**
 * Layout do novo menu
 * @author flavio.santana
 *@since 30/07/2013
 */
public class MenuPadrao extends BodyTagSupport {

	private final String CAMINHO_PAGINAS = Constantes.getValue("CONTEXTO_APLICACAO") + "/pages";
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		UsuarioDTO usrSession = WebUtil.getUsuario((HttpServletRequest) pageContext.getRequest());
		StringBuffer sbMenu = new StringBuffer();
		String menuSessao = "";

		try {
			menuSessao = (String) ((HttpServletRequest) pageContext.getRequest()).getSession(true).getAttribute("menuPadrao");
			if (menuSessao != null && !StringUtils.isBlank(menuSessao)) {
				sbMenu.append(menuSessao);
			} else {
				menuSessao = this.gerarMenuPrincipal(usrSession);

				((HttpServletRequest) pageContext.getRequest()).getSession(true).setAttribute("menuPadrao", menuSessao);

				sbMenu.append(menuSessao);
			}		

			pageContext.getOut().println(sbMenu);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
	/***
	 * Gera o HTML do menu principal
	 * @param usuario
	 * @return
	 * @throws Exception
	 */
	private String gerarMenuPrincipal(UsuarioDTO usuario) throws Exception {
		MenuDao menuDao = new MenuDao();
		StringBuffer html = new StringBuffer();
		html.append("<ul class='g-unit g-section g-tpl-nest'  id='nav' >");
		ArrayList<MenuDTO> listaMenusPai = (ArrayList<MenuDTO>) menuDao.listarMenusPorPerfil(usuario, null, false);
		if (listaMenusPai != null) {
			for (MenuDTO menu : listaMenusPai) {
				html.append("<li class='g-unit'>");
				html.append("<a  href='javascript:;'>");
				html.append("<span>" + UtilI18N.internacionaliza(((HttpServletRequest) pageContext.getRequest()), menu.getNome()) + "</span>");
				html.append("<i></i>");
				html.append("</a>");
				this.gerarSubMenu(html, menu.getIdMenu(), usuario);
			}
		}
		html.append("</ul");
		return html.toString();
	}
	/**
	 * Gera o HTML dos subMenus
	 * @param html
	 * @param idMenu
	 * @param usuario
	 */
	private void gerarSubMenu(StringBuffer html, Integer idMenu, UsuarioDTO usuario) {
		try {
			MenuDao menuDao = new MenuDao();
			ArrayList<MenuDTO> listaSubMenus = (ArrayList<MenuDTO>) menuDao.listarMenusPorPerfil(usuario, idMenu, false);
			if(listaSubMenus != null && !listaSubMenus.isEmpty()) {
				html.append("<i></i>");
				html.append("<ul>");
				for (MenuDTO submenu : listaSubMenus) {
					html.append("<li>");
					html.append("	<a href='"+((submenu.getLink() == null || submenu.getLink().equals("")) ? "javascript:;"  : CAMINHO_PAGINAS + submenu.getLink()) + "'>"
							+ ""+UtilI18N.internacionaliza(((HttpServletRequest) pageContext.getRequest()),submenu.getNome())+"</a>");
					this.gerarSubMenu(html, submenu.getIdMenu(), usuario);
					html.append("</li>");
				}
				html.append("</ul>");
			}
			html.append("</li>");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
