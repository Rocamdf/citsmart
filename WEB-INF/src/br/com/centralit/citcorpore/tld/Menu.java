package br.com.centralit.citcorpore.tld;

import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.MenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.MenuDao;
import br.com.centralit.citcorpore.negocio.DicionarioService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioServiceEjb;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.UsuarioServiceEjb;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Mensagens;

/**
 * @author breno.guimaraes
 * 
 */
@SuppressWarnings({ "unused", "serial", "unchecked" })
public class Menu extends BodyTagSupport {

	private String rapido;
	private static final String INTERROGACAO = "?";
	private final String CAMINHO_PAGINAS = Constantes.getValue("CONTEXTO_APLICACAO") + "/pages";
	private final String CAMINHO_IMAGENS = "/citsmart/template_new/images/icons/small/grey/";
	private final String CAMINHO_IMAGENS_LARGE = "/citsmart/template_new/images/icons/large/grey/";
	protected Boolean btnGrava;
	protected Boolean btnPesquisa;
	protected Boolean btnDeleta;
	private Integer qtdSub = 0;
	private static HashMap<String, String> mapDicIng = null;
	private static HashMap<String, String> mapDicEsp = null;
	private static HashMap<String, String> mapDicPort = null;
	private static String locale = "";
	private static Properties props = null;
	private static InputStream inputStreamSettedInLoad = null;
	private static String fileName = "";

	@Override
	public int doStartTag() throws JspException {
		UsuarioDTO usrSession = WebUtil.getUsuario((HttpServletRequest) pageContext.getRequest());
		StringBuffer menu = new StringBuffer();
		String menuSessao = "";
		String menuRapido = "";
		String permissaoBotao = "";

		try {
			// if (usrSession != null) {
			// usrSession = getAtualizacoesUsuario(usrSession.getLogin(), usrSession);
			// }
			if (usrSession != null && usrSession.getStatus().equals("I")) {
				pageContext.getOut().println("<p style='color:#990000'>Usuário não cadastrado. Contate o administrador.</p>");
				return SKIP_BODY;
			}
			if (usrSession != null && usrSession.getIdPerfilAcessoUsuario() == null) {
				pageContext.getOut().println("<p style='color:#990000'>Usuário não cadastrado. Contate o administrador.</p>");
				return SKIP_BODY;
			}
			if (getRapido() == null) {
				setRapido("N");
			}
			if (getRapido().equalsIgnoreCase("S")) {

				menuRapido = (String) ((HttpServletRequest) pageContext.getRequest()).getSession(true).getAttribute("menuRapido");

				if (menuRapido != null && !StringUtils.isBlank(menuRapido)) {

					menu.append(menuRapido);

				} else {

					menuRapido = this.getMenuRapido(usrSession);

					((HttpServletRequest) pageContext.getRequest()).getSession(true).setAttribute("menuRapido", menuRapido);

					menu.append(menuRapido);
				}

				//Removido pois a validação de Permissões de Botão não estavam sendo realizadas.
//				permissaoBotao = (String) ((HttpServletRequest) pageContext.getRequest()).getSession(true).getAttribute("permissaoBotao");
				
//				if (permissaoBotao != null && StringUtils.isNotBlank(permissaoBotao)) {
//					menu.append(permissaoBotao);
//				} else {
					
					permissaoBotao = validarPermissaoDeBotao((HttpServletRequest) pageContext.getRequest(), usrSession);
					
//					((HttpServletRequest) pageContext.getRequest()).getSession(true).setAttribute("permissaoBotao", permissaoBotao);
					
					menu.append(permissaoBotao);
//				}
				
			} else {

				menuSessao = (String) ((HttpServletRequest) pageContext.getRequest()).getSession(true).getAttribute("menu");

				if (menuSessao != null && !StringUtils.isBlank(menuSessao)) {
					menu.append(menuSessao);
				} else {

					menuSessao = this.getMenu(usrSession);

					((HttpServletRequest) pageContext.getRequest()).getSession(true).setAttribute("menu", menuSessao);

					menu.append(menuSessao);
				}

			}

			pageContext.getOut().println(menu);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}

	private String getMenu(UsuarioDTO usrSession) throws Exception {
		MenuDao menuDao = new MenuDao();
		StringBuffer html = new StringBuffer();
		ArrayList<MenuDTO> menusPai = (ArrayList<MenuDTO>) menuDao.listarMenusPorPerfil(usrSession, null, false);
		if (menusPai != null) {
			html.append("<script>function chamaItemMenu(url){window.location = url;}</script>");
			html.append("<div id='tst' style='background: #D5DBDF; width:100%;'>");
			for (MenuDTO menPai : menusPai) {
				if (menPai.getNome().trim().equalsIgnoreCase("$menu.nome.recursosHumanos")) {
					String mostrarGerenciaRecursosHumanos = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOSTRAR_GERENCIA_RECURSOS_HUMANOS, "N");
					if (!mostrarGerenciaRecursosHumanos.trim().equalsIgnoreCase("S") || menPai.getMostrar() == null || !Boolean.parseBoolean(menPai.getMostrar())) {
						continue;
					}
				} else if (menPai.getNome().trim().equalsIgnoreCase("$menu.nome.compras")) {
					String mostrarCompras = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOSTRAR_COMPRAS, "N");
					if (!mostrarCompras.trim().equalsIgnoreCase("S") || menPai.getMostrar() == null || !Boolean.parseBoolean(menPai.getMostrar())) {
						continue;
					}
				}
				String iconMenu = "";
				html.append("<a href=\"javascript:void(0)\" id='itemMM" + menPai.getIdMenu() + "' style='background:url(" + CAMINHO_IMAGENS + menPai.getImagem() + ") no-repeat;' "
						+ "class=\"easyui-menubutton m-btn l-btn l-btn-plain\" data-options=\"menu:'#mm" + menPai.getIdMenu() + "'" + iconMenu + "\">"
						+ "<span class=\"l-btn-left\"><span class=\"l-btn-text\">" + internacionalizar(menPai.getNome()) + "</span><span class=\"m-btn-downarrow\">&nbsp;</span></span>" + "</a>\n");

			}
			html.append("</div>");
			gerarMenus(html, menusPai, usrSession, 0);
		}
		return html.toString();
	}

	private void gerarMenus(StringBuffer sb, Collection<MenuDTO> listaDeMenus, UsuarioDTO usuario, int indice) {
		String link;
		try {
			MenuDao menuDao = new MenuDao();
			for (MenuDTO submenu : listaDeMenus) {
				ArrayList<MenuDTO> menusFilho = (ArrayList<MenuDTO>) menuDao.listarMenusPorPerfil(usuario, submenu.getIdMenu(), false);
				if (menusFilho != null && !menusFilho.isEmpty()) {
					this.qtdSub++;
					String compl = "";
					if (indice > 0) {
						compl = "SUB";
					}
					String iconMenu = "";
					if (submenu.getImagem() != null && !"".equalsIgnoreCase(submenu.getImagem().trim())) {
						iconMenu = "iconCls:'icon-menu" + submenu.getIdMenu() + "'";
					}
					String s;
					s = (compl.equals("SUB") ? "" : "display:none;");
					if (submenu.getNome().equals("$menu.esconder")) {
						sb.append("<div style=\"display:none;\" id=\"mm" + compl + submenu.getIdMenu() + "\" style=\"width:250px;" + s + "\" data-options=\"" + iconMenu + "\">\n");
					}else{
						sb.append("<div id=\"mm" + compl + submenu.getIdMenu() + "\" style=\"width:250px;" + s + "\" data-options=\"" + iconMenu + "\">\n");
					}
					if (indice > 0) {
						sb.append("<span>" + internacionalizar(submenu.getNome()) + "</span>");
						sb.append("<div style=\"width:250px;\">\n");
					}
					this.gerarMenus(sb, menusFilho, usuario, indice + 1);
					if (indice > 0) {
						sb.append("</div>\n");
					}
					sb.append("</div>\n");
				} else {
					link = submenu.getLink() == null || submenu.getLink().trim().equals("") ? "#" : CAMINHO_PAGINAS + submenu.getLink();
					String iconMenu = "";
					if (submenu.getImagem() != null && !"".equalsIgnoreCase(submenu.getImagem().trim())) {
						iconMenu = "iconCls:'icon-menu" + submenu.getIdMenu() + "'";
					}
					if (indice > 0) {
						if (submenu.getNome().equals("$menu.esconder")) {
							sb.append("		<div style=\"display:none;\" id=\"mmSUB" + submenu.getIdMenu() + "\" data-options=\"" + iconMenu + "\" onclick=\"chamaItemMenu('" + link + "')\">" + internacionalizar(submenu.getNome())+ "</div>\n");

						}else{
							sb.append("		<div id=\"mmSUB" + submenu.getIdMenu() + "\" data-options=\"" + iconMenu + "\" onclick=\"chamaItemMenu('" + link + "')\">" + internacionalizar(submenu.getNome())+ "</div>\n");
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private boolean permiteAdicionarMenu(){
		boolean resultado = true;
		String asteriskAtivo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKATIVAR, "N");
		if (!asteriskAtivo.equals("S")) {
			resultado = false;
		}
		return resultado;
	}
	
	private String getMenuRapido(UsuarioDTO usrSession) throws Exception {
		MenuDao menuDao = new MenuDao();
		StringBuffer html = new StringBuffer();
		ArrayList<MenuDTO> menusRapido = (ArrayList<MenuDTO>) menuDao.listarMenusPorPerfil(usrSession, null, true);
		if (permiteAdicionarMenu()){
			html.append("<li class=\"li_menu tooltip_bottom\" title=\"Ramal onde se encontra o usuário\">");
			html.append("<img onclick=\"abreRamalTelefone();\" src=\"" + CAMINHO_IMAGENS_LARGE + "phone.png\">");
			html.append("</li>");
		}
		if (menusRapido != null) {
			for (MenuDTO menRapido : menusRapido) {
				html.append("<a href=\"" + CAMINHO_PAGINAS + menRapido.getLink() + "\"> ");
				html.append("<li class=\"li_menu tooltip_bottom\" title=\"" + menRapido.getDescricao() + "\">");
				html.append("<img src=\"" + CAMINHO_IMAGENS_LARGE + menRapido.getImagem() + "\">");
				html.append("</li>");
				html.append("</a>");
			}
			html.append("<a href=\"/cithelp/index.html\" target=\"blank\">");
			html.append("<li class=\"li_menu tooltip_bottom\" title=\" Help CITSmart\">");
			html.append("<img src=\"" + CAMINHO_IMAGENS_LARGE + "help.png\">");
			html.append("</li>");
			html.append("</a>");
			html.append("</ul>");
		}
		return html.toString();
	}

	private UsuarioDTO getAtualizacoesUsuario(String login, UsuarioDTO usrSession) {
		UsuarioDTO retorno = null;
		UsuarioServiceEjb usuarioService = new UsuarioServiceEjb();
		PerfilAcessoUsuarioServiceEjb perfilAcessoUsuario = new PerfilAcessoUsuarioServiceEjb();

		PerfilAcessoUsuarioDTO perfilAcessoDTO = new PerfilAcessoUsuarioDTO();
		perfilAcessoDTO.setIdUsuario(usrSession.getIdUsuario());

		try {
			retorno = usuarioService.restoreByLogin(login);
			perfilAcessoDTO = perfilAcessoUsuario.listByIdUsuario(perfilAcessoDTO);
			retorno.setIdPerfilAcessoUsuario(perfilAcessoDTO.getIdPerfilAcesso());
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (LogicException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retorno;
	}

	@Override
	public int doEndTag() throws JspException {
		return super.doEndTag();
	}

	public String validarPermissaoDeBotao(HttpServletRequest request, UsuarioDTO usuario) throws Exception {
		String validarPermissoesDeBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.VALIDAR_BOTOES, "N");
		StringBuffer html = new StringBuffer();
		if (usuario != null && usuario.getLogin() != null && (usuario.getLogin().equalsIgnoreCase("admin") || usuario.getLogin().equalsIgnoreCase("consultor"))) { // Permissao total
			return "";
		}
		if (validarPermissoesDeBotoes.trim().equalsIgnoreCase("S")) {
			MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
			PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
			PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
			PerfilAcessoMenuDTO perfilAcessoMenudto = new PerfilAcessoMenuDTO();
			PerfilAcessoGrupoDTO perfilAcessoGrupo = new PerfilAcessoGrupoDTO();
			String pathInfo = getRequestedPath(request);
			String[] auxDinamic = {};
			auxDinamic = pathInfo.split(".jsp");
			String strForm = getObjectName(pathInfo);
			String url = "/" + strForm + "/" + strForm + ".load";
			try {
				if (!auxDinamic[1].equals("null")) {
					url += "?" + auxDinamic[1];
				}
			} catch (Exception x) {
				x.printStackTrace();
			}
			Integer idMenu = menuService.buscarIdMenu(url);
			html.append("<script> addEvent(window, \"load\", carregaPermissao, false); ");
			html.append("function carregaPermissao(){");
			if (idMenu != null) {
				if (usuario.getIdPerfilAcessoUsuario() != null) {
					perfilAcessoMenudto.setIdPerfilAcesso(usuario.getIdPerfilAcessoUsuario());
					perfilAcessoMenudto.setIdMenu(idMenu);
					Collection<PerfilAcessoMenuDTO> listaAcessoMenus = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);
					usuario = (UsuarioDTO) usuarioService.restore(usuario);
					Integer idEmpregado = usuario.getIdEmpregado();
					Collection<GrupoEmpregadoDTO> listaDeGrupoEmpregado = grupoEmpregadoService.findByIdEmpregado(idEmpregado);
					if (listaDeGrupoEmpregado != null) {
						for (GrupoEmpregadoDTO grupoEmpregado : listaDeGrupoEmpregado) {
							perfilAcessoGrupo.setIdGrupo(grupoEmpregado.getIdGrupo());
							perfilAcessoGrupo = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupo);

							if (perfilAcessoGrupo != null) {
								perfilAcessoMenudto.setIdPerfilAcesso(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
								perfilAcessoMenudto.setIdMenu(idMenu);
								Collection<PerfilAcessoMenuDTO> listaAcessoMenusGrupo = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);
								if (listaAcessoMenusGrupo != null) {
									for (PerfilAcessoMenuDTO perfilAcessoMenu : listaAcessoMenusGrupo) {
										PerfilAcessoMenuDTO perfil = new PerfilAcessoMenuDTO();
										perfil.setGrava(perfilAcessoMenu.getGrava());
										perfil.setPesquisa(perfilAcessoMenu.getPesquisa());
										perfil.setDeleta(perfilAcessoMenu.getDeleta());
										listaAcessoMenus.add(perfil);
									}
								}
							}

						}
					}
					if (listaAcessoMenus != null) {
						setBtnGrava(true);
						html.append("$('#btnGravar').attr('class','light img_icon has_text disabledButtons'); ");
						setBtnPesquisa(true);
						html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only disabledButtons'); ");
						html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only disabledButtons'); ");
						setBtnDeleta(true);
						html.append("$('#btnUpDate').attr('class','light img_icon has_text disabledButtons'); ");
						html.append("$('#btnExcluir').attr('class','light img_icon has_text disabledButtons'); ");
						for (PerfilAcessoMenuDTO perfilAcesso : listaAcessoMenus) {
							if (perfilAcesso.getGrava().equalsIgnoreCase("S")) {
								html.append("$('#btnGravar').attr('class','light img_icon has_text'); ");
								setBtnGrava(false);

								html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
								html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
								setBtnPesquisa(false);
							}
							if (perfilAcesso.getPesquisa().equalsIgnoreCase("S")) {
								html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
								html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
								setBtnPesquisa(false);
							}
							if (perfilAcesso.getDeleta().equalsIgnoreCase("S")) {
								html.append("$('#btnUpDate').attr('class','light img_icon has_text'); ");
								html.append("$('#btnExcluir').attr('class','light img_icon has_text'); ");
								setBtnDeleta(false);

								html.append("$('#btnPesquisar').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
								html.append("$('#btnTodos').attr('class','ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only'); ");
								setBtnPesquisa(false);
							}
						}
						html.append("$('#btnGravar').attr('disabled', " + getBtnGrava() + "); ");
						html.append("$('#btnPesquisar').attr('disabled', " + getBtnPesquisa() + "); ");
						html.append("$('#btnTodos').attr('disabled', " + getBtnPesquisa() + "); ");
						html.append("$('#btnUpDate').attr('disabled', " + getBtnDeleta() + "); ");
						html.append("$('#btnExcluir').attr('disabled', " + getBtnDeleta() + "); ");
					}
				}
			}
			html.append("}");
			html.append("</script>");
		}
		return html.toString();
	}

	private String getRequestedPath(HttpServletRequest request) {

		String path = request.getRequestURI() + request.getQueryString();
		path = path.substring(request.getContextPath().length());
		int index = path.indexOf(INTERROGACAO);
		if (index != -1)
			path = path.substring(0, index);
		return path;
	}

	private String getRequestedPathInternacional(HttpServletRequest request) {

		String path = request.getRequestURI();
		path = path.substring(request.getContextPath().length());
		int index = path.indexOf(INTERROGACAO);
		if (index != -1)
			path = path.substring(0, index);
		return path;
	}

	public String getObjectName(String path) {
		String strResult = "";
		boolean b = false;
		for (int i = path.length() - 1; i >= 0; i--) {
			if (b) {
				if (path.charAt(i) == '/') {
					return strResult;
				} else {
					strResult = path.charAt(i) + strResult;
				}
			} else {
				if (path.charAt(i) == '.') {
					b = true;
				}
			}
		}
		return strResult;
	}

	public String getRapido() {
		return rapido;
	}

	public void setRapido(String rapido) {
		this.rapido = rapido;
	}

	public Boolean getBtnGrava() {
		return btnGrava;
	}

	public void setBtnGrava(Boolean btnGrava) {
		this.btnGrava = btnGrava;
	}

	public Boolean getBtnPesquisa() {
		return btnPesquisa;
	}

	public void setBtnPesquisa(Boolean btnPesquisa) {
		this.btnPesquisa = btnPesquisa;
	}

	public Boolean getBtnDeleta() {
		return btnDeleta;
	}

	public void setBtnDeleta(Boolean btnDeleta) {
		this.btnDeleta = btnDeleta;
	}

	public String internacionalizar(String valor) throws Exception {
		String valorRetorno = "";
		try {
			valor = valor.replaceAll("\\$", "");
			Boolean chavaEncontrada = false;
			//StringBuffer strBuff = new StringBuffer();
			String pathInfo = getRequestedPathInternacional((HttpServletRequest) pageContext.getRequest());
			DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
			String fileName = "Mensagens";
			String sessaoLocale = "";
			String value = "";
			List<DicionarioDTO> lisResult = null;
			if (pageContext.getSession().getAttribute("locale") != null) {
				sessaoLocale = pageContext.getSession().getAttribute("locale").toString();
			} else if (locale != null) {
				sessaoLocale = locale.toLowerCase();
			}
			if ((mapDicPort == null || props == null || mapDicIng == null || mapDicEsp == null) || (pathInfo.endsWith("menu.jsp")) || (pathInfo.endsWith("menu.load")) || (pathInfo.endsWith("dicionario.jsp"))
					|| (pathInfo.endsWith("dicionario.load"))) {
				mapDicPort = new HashMap<String, String>();
				mapDicIng = new HashMap<String, String>();
				mapDicEsp = new HashMap<String, String>();
				props = new Properties();
				if (!sessaoLocale.trim().equalsIgnoreCase("")) {
					fileName = fileName + "_" + sessaoLocale.toLowerCase().trim() + ".properties";
				} else {
					fileName = fileName + ".properties";
				}
				carregaMaps();
				ClassLoader load = Mensagens.class.getClassLoader();
				InputStream is = load.getResourceAsStream(fileName);
				if (is == null) {
					is = ClassLoader.getSystemResourceAsStream(fileName);
				}
				if (is == null) {
					is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
				}
				if (is == null) {
					is = inputStreamSettedInLoad;
				}
				if (is != null) {
					props.load(is);
				}

				if (!sessaoLocale.trim().equalsIgnoreCase("")) {
					if (sessaoLocale.trim().equalsIgnoreCase("en")) {
						if (mapDicIng.containsKey(valor)) {
							value = mapDicIng.get(valor);
							chavaEncontrada = true;
						} else {
							value = valor;
						}
					} else {
						if (sessaoLocale.trim().equalsIgnoreCase("es")) {
							if (mapDicEsp.containsKey(valor)) {
								value = mapDicEsp.get(valor);
								chavaEncontrada = true;
							} else {
								value = valor;
							}
						} else {
							if (mapDicPort.containsKey(valor)) {
								value = mapDicPort.get(valor);
								chavaEncontrada = true;
							} else {
								value = valor;
							}
						}
					}
				} else {
					if (mapDicPort.containsKey(valor)) {
						value = mapDicPort.get(valor);
						chavaEncontrada = true;
					} else {
						value = valor;
					}
				}
				if (!chavaEncontrada) {
					if (props.containsKey(valor)) {
						value = props.getProperty(valor);
						chavaEncontrada = true;
					} else {
						value = valor;
					}
				}
			} else {
				
				if (!sessaoLocale.trim().equalsIgnoreCase("")) {
					if (sessaoLocale.trim().equalsIgnoreCase("en")) {
						if (mapDicIng.containsKey(valor)) {
							value = mapDicIng.get(valor);
							chavaEncontrada = true;
						} else {
							value = valor;
						}
					} else {
						if (sessaoLocale.trim().equalsIgnoreCase("es")) {
							if (mapDicEsp.containsKey(valor)) {
								value = mapDicEsp.get(valor);
								chavaEncontrada = true;
							} else {
								value = valor;
							}
						} else {
							if (mapDicPort.containsKey(valor)) {
								value = mapDicPort.get(valor);
								chavaEncontrada = true;
							} else {
								value = valor;
							}
						}
					}
				} else {
					if (mapDicPort.containsKey(valor)) {
						value = mapDicPort.get(valor);
						chavaEncontrada = true;
					} else {
						value = valor;
					}
				}
				
				if (!chavaEncontrada) {
					props = new Properties();
					if (!sessaoLocale.trim().equalsIgnoreCase("")) {
						fileName = fileName + "_" + sessaoLocale.toLowerCase().trim() + ".properties";
					} else {
						fileName = fileName + ".properties";
					}
					ClassLoader load = Mensagens.class.getClassLoader();
					InputStream is = load.getResourceAsStream(fileName);
					if (is == null) {
						is = ClassLoader.getSystemResourceAsStream(fileName);
					}
					if (is == null) {
						is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
					}
					if (is == null) {
						is = inputStreamSettedInLoad;
					}
					if (is != null) {
						props.load(is);
					}
					if (props.containsKey(valor)) {
						value = props.getProperty(valor);
						chavaEncontrada = true;
					} else {
						value = valor;
					}
				}
			}
			valorRetorno = value;
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro nas taglibs" + e);
		}
		return valorRetorno;
	}

	public void carregaMaps() throws ServiceException, Exception {
		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
		List<DicionarioDTO> lisResult = null;
		lisResult = (List<DicionarioDTO>) dicionarioService.list();
		if (lisResult != null) {
			for (DicionarioDTO dicionario : lisResult) {
				if (dicionario.getSigla().equalsIgnoreCase("en")) {
					mapDicIng.put(dicionario.getNome(), dicionario.getValor());
				} else {
					if (dicionario.getSigla().equalsIgnoreCase("es")) {
						mapDicEsp.put(dicionario.getNome(), dicionario.getValor());
					} else {
						mapDicPort.put(dicionario.getNome(), dicionario.getValor());
					}
				}
			}
		}
	}
}