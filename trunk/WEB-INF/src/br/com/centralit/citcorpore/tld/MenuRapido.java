package br.com.centralit.citcorpore.tld;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.Constantes;
/**
 * Layout do menu de acesso rápido
 * @author flavio.santana
 *@since 28/10/2013
 */
public class MenuRapido extends BodyTagSupport {

	private final String CAMINHO_PAGINAS = Constantes.getValue("CONTEXTO_APLICACAO") + "/pages";
	private static boolean flagMenuPrincipal = true;
	private static boolean temMenu;
	private static final long serialVersionUID = 1L;

	@Override
	public int doStartTag() throws JspException {
		UsuarioDTO usrSession = WebUtil.getUsuario((HttpServletRequest) pageContext.getRequest());
		StringBuffer sbMenu = new StringBuffer();
		String menuSessao = "";

		try {
			menuSessao = this.gerarMenuPrincipal(usrSession);
			sbMenu.append(menuSessao);
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
		StringBuilder html = new StringBuilder();
		boolean flagFinalLinha = false, flagInicioLinha = false;
		temMenu = false;
		html.append("<div class='widget'>");
		html.append("	<div class='widget-head'>");
		html.append("		<h4 class='heading'>"+UtilI18N.internacionaliza((HttpServletRequest) pageContext.getRequest(), "citcorpore.comum.acessoRapido")+"</h4>");
		html.append("	</div>");
		html.append("	<div class='widget-body'>");
		
		html.append("		<div class='row-fluid'>");
		ArrayList<MenuDTO> listaMenusPai = (ArrayList<MenuDTO>) menuDao.listarMenusPorPerfil(usuario, null, false);
		if (listaMenusPai != null) {
			int i = 1;
			double num = listaMenusPai.size();
			for (MenuDTO menu : listaMenusPai) {
				StringBuilder htmlMenu = new StringBuilder();
				flagMenuPrincipal = false;
				
				if(flagInicioLinha) {
					html.append("<div class='row-fluid'>");
					flagInicioLinha = false;
				}
				htmlMenu.append("<div class='span4'>");
				htmlMenu.append("	<div class='innerAll'>");
				htmlMenu.append("		<div class='glyphicons glyphicon-large "+menu.getImagem()+"'>");
				htmlMenu.append("			<i></i>");
				htmlMenu.append("			<h4>" + UtilI18N.internacionaliza(((HttpServletRequest) pageContext.getRequest()), menu.getNome()) + "</h4>");
				this.gerarSubMenu(htmlMenu, menu.getIdMenu(), usuario);
				htmlMenu.append("		</div>");
				htmlMenu.append("	</div>");
				htmlMenu.append("</div>");
				
				if(flagMenuPrincipal) {
					html.append(htmlMenu);
				}else {
					i--;
				}
				if(i%3==0 || i == num) 
					flagFinalLinha = flagInicioLinha = true;					
				
				if(flagFinalLinha) {
					html.append("</div>");
					flagFinalLinha = false;
				}
				i++;
			}
		}
		html.append("		</div>");	
		html.append("	</div>");		
		html.append("</div>");
		
		if(!temMenu)
			html = new StringBuilder();
		
		return html.toString();
	}
	/**
	 * Gera o HTML dos subMenus
	 * @param html
	 * @param idMenu
	 * @param usuario
	 */
	private void gerarSubMenu(StringBuilder html, Integer idMenu, UsuarioDTO usuario) {
		try {
			MenuDao menuDao = new MenuDao();
			ArrayList<MenuDTO> listaSubMenus = (ArrayList<MenuDTO>) menuDao.listarMenusPorPerfil(usuario, idMenu, true);
			if(listaSubMenus != null && !listaSubMenus.isEmpty()) {
				for (MenuDTO submenu : listaSubMenus) {
					if(submenu.getLink()!=null && !submenu.getLink().equals("")) {
						temMenu = true;
						flagMenuPrincipal = true;
						html.append("<p>");
						html.append("	<a href='"+((submenu.getLink() == null || submenu.getLink().equals("")) ? "javascript:;"  : CAMINHO_PAGINAS + submenu.getLink()) + "'>"
								+ ""+UtilI18N.internacionaliza(((HttpServletRequest) pageContext.getRequest()),submenu.getNome())+"</a>");
						html.append("</p>");
					}
					this.gerarSubMenu(html, submenu.getIdMenu(), usuario);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
