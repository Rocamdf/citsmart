package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoGrupoDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoMenuDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ColumnsDTO;
import br.com.centralit.citcorpore.metainfo.bean.DinamicViewsDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO;
import br.com.centralit.citcorpore.metainfo.negocio.BotaoAcaoVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.DinamicViewsService;
import br.com.centralit.citcorpore.metainfo.negocio.GrupoVisaoCamposNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.GrupoVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.HtmlCodeVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.ScriptsVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.ValorVisaoCamposNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoRelacionadaService;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoService;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.JSONUtil;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoGrupoService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.negocio.ServicoContratoServiceEjb;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;
import br.com.citframework.util.UtilStrings;

import com.google.gson.Gson;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class DinamicViews extends AjaxFormAction {
	private static boolean DEBUG = false;

	@Override
	public Class getBeanClass() {
		return DinamicViewsDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

		if (dinamicViewsDTO.getIdVisao() == null) {
			if (dinamicViewsDTO.getIdentificacao() != null && !dinamicViewsDTO.getIdentificacao().trim().equalsIgnoreCase("")) {
				VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
				VisaoDTO visaoDto = visaoService.findByIdentificador(dinamicViewsDTO.getIdentificacao());
				if (visaoDto == null) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.visao_nao_encontrada"));
					return;
				}
				dinamicViewsDTO.setIdVisao(visaoDto.getIdVisao());
			} else {
				document.alert(UtilI18N.internacionaliza(request, "dinamicview.visaonaoencontrada"));
				return;
			}
		}

		VisaoDTO visaoDto = recuperaVisao(dinamicViewsDTO.getIdVisao(), true);
		if (visaoDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "dinamicview.visaonaoencontrada"));
			return;
		}

		//DinamicViewsDTO dinamicViewsDto = (DinamicViewsDTO) document.getBean();
		//DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, WebUtil.getUsuarioSistema(request));
		HashMap hashValores = getFormFields(request);
		if (DEBUG) {
			debugValuesFromRequest(hashValores);
		}

		HashMap<String, Object> map = new HashMap<String, Object>();

		map.put("SESSION.DINAMICVIEWS_SAVEINFO", request.getSession().getAttribute("DinamicViews_SaveInfo"));
		map.put("SESSION.NUMERO_CONTRATO_EDICAO", request.getSession().getAttribute("NUMERO_CONTRATO_EDICAO"));

		request.setAttribute("visao", visaoDto);

		request.getSession().setAttribute("DinamicViews_SaveInfo", "");
		request.getSession().setAttribute("DinamicViews_SaveInfo", dinamicViewsDTO.getSaveInfo());

		BotaoAcaoVisaoService botaoAcaoVisaoService = (BotaoAcaoVisaoService) ServiceLocator.getInstance().getService(BotaoAcaoVisaoService.class, null);
		Collection colBotoes = botaoAcaoVisaoService.findByIdVisao(dinamicViewsDTO.getIdVisao());
		request.setAttribute("botoes", colBotoes);

		String strScript = (String) visaoDto.getMapScripts().get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_LOAD.getName());
		if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
			Context cx = Context.enter();
			Scriptable scope = cx.initStandardObjects();
			scope.put("usuarioDto", scope, usuarioDto);
			scope.put("document", scope, document);
			scope.put("mapFields", scope, map);
			scope.put("visaoDto", scope, visaoDto);
			scope.put("request", scope, request);
			scope.put("response", scope, response);
			Object retorno = scriptExecute.processScript(cx, scope, strScript, DinamicViews.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_LOAD.getName());
		}

		setValuesFromMap(document, map, "document.form");

		if (dinamicViewsDTO.getId() != null && !dinamicViewsDTO.getId().trim().equalsIgnoreCase("")) {
			VisaoDTO visaoPesquisaDto = recuperaVisao(visaoDto.getIdVisao(), false);
			Collection colFilter = new ArrayList();
			Collection colGrupos = visaoPesquisaDto.getColGrupos();
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
				if (grupoVisaoDTO.getColCamposVisao() != null) {
					for (Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

						if (grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getPk().equalsIgnoreCase("S")) {
							grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().setValue(dinamicViewsDTO.getId());
							colFilter.add(grupoVisaoCamposNegocioDTO);
						}
					}
				}
			}
			String metodoOrigem = "load";
			restoreVisao(document, request, response, visaoDto.getIdVisao(), colFilter, metodoOrigem);
		}

		// ---
		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
		PerfilAcessoGrupoService perfilAcessoGrupoService = (PerfilAcessoGrupoService) ServiceLocator.getInstance().getService(PerfilAcessoGrupoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		String pathInfo = getRequestedPath(request);
		//String strForm = getObjectName(pathInfo);
		PerfilAcessoMenuDTO perfilAcessoMenudto = new PerfilAcessoMenuDTO();
		PerfilAcessoGrupoDTO perfilAcessoGrupo = new PerfilAcessoGrupoDTO();
		String url = pathInfo.replaceAll("/pages", "");
		Integer idMenu = menuService.buscarIdMenu(url);
		String acessoGravar = "N";
		String acessoDeletar = "N";
		String acessoPesquisar = "N";
		if (idMenu != null) {
			PerfilAcessoUsuarioService perfilAcessoUsuarioService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);

			PerfilAcessoUsuarioDTO perfilAcessoUsuarioDto = new PerfilAcessoUsuarioDTO();

			perfilAcessoUsuarioDto = perfilAcessoUsuarioService.obterPerfilAcessoUsuario(usuarioDto);

			if (perfilAcessoUsuarioDto != null && perfilAcessoUsuarioDto.getIdPerfilAcesso() != null) {

				perfilAcessoMenudto.setIdPerfilAcesso(perfilAcessoUsuarioDto.getIdPerfilAcesso());
				perfilAcessoMenudto.setIdMenu(idMenu);

				Collection<PerfilAcessoMenuDTO> listaPerfilAcessoMenu = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);

				String validarPermissoesDeBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.VALIDAR_BOTOES, "N");

				if (validarPermissoesDeBotoes != null && StringUtils.isNotBlank(validarPermissoesDeBotoes) && validarPermissoesDeBotoes.trim().equalsIgnoreCase("S")) {

					if (listaPerfilAcessoMenu != null) {

						for (PerfilAcessoMenuDTO perfilAcessoMenu : listaPerfilAcessoMenu) {

							if (acessoGravar.equals("N") && acessoDeletar.equals("N") && acessoPesquisar.equals("N")) {

								if (perfilAcessoMenu.getGrava() != null && perfilAcessoMenu.getGrava().equalsIgnoreCase("S")) {
									acessoGravar = "S";
								}
								if (perfilAcessoMenu.getDeleta() != null && perfilAcessoMenu.getDeleta().equalsIgnoreCase("S")) {
									acessoDeletar = "S";
								}
								if (perfilAcessoMenu.getPesquisa() != null && perfilAcessoMenu.getPesquisa().equalsIgnoreCase("S")) {
									acessoPesquisar = "S";
								}
							}
						}
					}

				} else {
					acessoGravar = "S";
					acessoDeletar = "S";
					acessoPesquisar = "S";
				}

				UsuarioDTO usuario = (UsuarioDTO) usuarioService.restore(usuarioDto);
				Integer idEmpregado = usuario.getIdEmpregado();

				Collection<GrupoEmpregadoDTO> listaDeGrupoEmpregado = grupoEmpregadoService.findByIdEmpregado(idEmpregado);

				if (listaDeGrupoEmpregado != null) {
					for (GrupoEmpregadoDTO grupoEmpregado : listaDeGrupoEmpregado) {

						perfilAcessoGrupo.setIdGrupo(grupoEmpregado.getIdGrupo());

						perfilAcessoGrupo = perfilAcessoGrupoService.listByIdGrupo(perfilAcessoGrupo);

						perfilAcessoMenudto.setIdPerfilAcesso(perfilAcessoGrupo.getIdPerfilAcessoGrupo());
						perfilAcessoMenudto.setIdMenu(idMenu);

						Collection<PerfilAcessoMenuDTO> listaAcessoMenusGrupo = perfilAcessoMenuService.restoreMenusAcesso(perfilAcessoMenudto);

						if (listaAcessoMenusGrupo != null) {
							for (PerfilAcessoMenuDTO perfilAcessoMenu : listaAcessoMenusGrupo) {
								if (perfilAcessoMenu.getGrava() != null && perfilAcessoMenu.getGrava().equalsIgnoreCase("S")) {
									acessoGravar = "S";
								}
								if (perfilAcessoMenu.getDeleta() != null && perfilAcessoMenu.getDeleta().equalsIgnoreCase("S")) {
									acessoDeletar = "S";
								}
								if (perfilAcessoMenu.getPesquisa() != null && perfilAcessoMenu.getPesquisa().equalsIgnoreCase("S")) {
									acessoPesquisar = "S";
								}
							}
						}
					}
				}
			}
		} else {
			acessoGravar = "S";
			acessoDeletar = "S";
			acessoPesquisar = "S";
		}
		if (acessoGravar.equalsIgnoreCase("N")) {
			document.executeScript("try{document.getElementById('btnGravar').style.display='none';}catch(e){}");
		}
		if (acessoDeletar.equalsIgnoreCase("N")) {
			document.executeScript("try{document.getElementById('btnExcluir').style.display='none';}catch(e){}");
		}
		if (acessoPesquisar.equalsIgnoreCase("S")) {
			document.executeScript("try{acaoPesquisar = 'S';}catch(e){}");
		}
	}

	private String getRequestedPath(HttpServletRequest request) {
		String path = request.getRequestURI() + "?" + request.getQueryString();
		path = path.substring(request.getContextPath().length());
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

	public void setDadosTemporarios(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();
		request.getSession(true).setAttribute("tempData_" + usuarioDto.getIdUsuario() + "_" + dinamicViewsDTO.getKeyControl(), dinamicViewsDTO.getDinamicViewsJson_tempData());
	}

	public void recuperaVisaoFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

		if (dinamicViewsDTO.getIdTarefa() != null) {
			Collection<GrupoVisaoCamposNegocioDTO> colFilter = findCamposTarefa(dinamicViewsDTO.getIdTarefa());
			if (colFilter != null) {
				String metodoOrigem = "recuperaVisaoFluxo";
				restoreVisao(document, request, response, dinamicViewsDTO.getDinamicViewsIdVisao(), colFilter, metodoOrigem);
			}
		}
	}

	public VisaoDTO recuperaVisao(Integer idVisao, boolean comFilhos) throws ServiceException, Exception {
		VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
		GrupoVisaoService grupoVisaoService = (GrupoVisaoService) ServiceLocator.getInstance().getService(GrupoVisaoService.class, null);
		GrupoVisaoCamposNegocioService grupoVisaoCamposNegocioService = (GrupoVisaoCamposNegocioService) ServiceLocator.getInstance().getService(GrupoVisaoCamposNegocioService.class, null);
		CamposObjetoNegocioService camposObjetoNegocioService = (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		ValorVisaoCamposNegocioService valorVisaoCamposNegocioService = (ValorVisaoCamposNegocioService) ServiceLocator.getInstance().getService(ValorVisaoCamposNegocioService.class, null);
		VisaoRelacionadaService visaoRelacionadaService = (VisaoRelacionadaService) ServiceLocator.getInstance().getService(VisaoRelacionadaService.class, null);
		ScriptsVisaoService scriptsVisaoService = (ScriptsVisaoService) ServiceLocator.getInstance().getService(ScriptsVisaoService.class, null);
		HtmlCodeVisaoService htmlCodeVisaoService = (HtmlCodeVisaoService) ServiceLocator.getInstance().getService(HtmlCodeVisaoService.class, null);

		VisaoDTO visaoDto = new VisaoDTO();
		visaoDto.setIdVisao(idVisao);
		visaoDto = (VisaoDTO) visaoService.restore(visaoDto);
		if (visaoDto == null) {
			return null;
		}

		Collection colGrupos = grupoVisaoService.findByIdVisaoAtivos(idVisao);
		if (colGrupos != null) {
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
				grupoVisaoDTO.setColCamposVisao(grupoVisaoCamposNegocioService.findByIdGrupoVisaoAtivos(grupoVisaoDTO.getIdGrupoVisao()));

				if (grupoVisaoDTO.getColCamposVisao() != null) {
					for (Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

						CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) camposObjetoNegocioService.restore(camposObjetoNegocioDTO);

						grupoVisaoCamposNegocioDTO.setCamposObjetoNegocioDto(camposObjetoNegocioDTO);

						Collection colValores = valorVisaoCamposNegocioService.findByIdGrupoVisaoAndIdCampoObjetoNegocio(grupoVisaoDTO.getIdGrupoVisao(),
								grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
						grupoVisaoCamposNegocioDTO.setColValores(colValores);
					}
				}
			}
		}
		visaoDto.setColGrupos(colGrupos);

		if (comFilhos) {
			Collection colVisoesRelacionadas = visaoRelacionadaService.findByIdVisaoPaiAtivos(idVisao);
			visaoDto.setColVisoesRelacionadas(colVisoesRelacionadas);
			if (colVisoesRelacionadas != null) {
				for (Iterator it = colVisoesRelacionadas.iterator(); it.hasNext();) {
					VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO) it.next();
					if (visaoRelacionadaDto.getIdVisaoFilha() != null) {
						VisaoDTO visaoFilhaDTO = recuperaVisao(visaoRelacionadaDto.getIdVisaoFilha(), false);
						if (visaoFilhaDTO != null) {
							visaoFilhaDTO.setFilha(true);
							visaoFilhaDTO.setAcaoVisaoFilhaPesqRelacionada(visaoRelacionadaDto.getAcaoEmSelecaoPesquisa());
							visaoRelacionadaDto.setVisaoFilhaDto(visaoFilhaDTO);
						}
					}
				}
			}

			Collection colScripts = scriptsVisaoService.findByIdVisao(idVisao);
			visaoDto.setColScripts(colScripts);
			HashMap map = new HashMap();
			if (colScripts != null) {
				for (Iterator it = colScripts.iterator(); it.hasNext();) {
					ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
					map.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
				}
			}
			visaoDto.setMapScripts(map);

			Collection colHtmlCode = htmlCodeVisaoService.findByIdVisao(idVisao);
			visaoDto.setColHtmlCode(colHtmlCode);
			map = new HashMap();
			if (colHtmlCode != null) {
				for (Iterator it = colHtmlCode.iterator(); it.hasNext();) {
					HtmlCodeVisaoDTO htmlCodeVisaoDTO = (HtmlCodeVisaoDTO) it.next();
					map.put(htmlCodeVisaoDTO.getHtmlCodeType().trim(), htmlCodeVisaoDTO.getHtmlCode());
				}
			}
			visaoDto.setMapHtmlCodes(map);
		}

		if (visaoDto.getMapScripts() == null) {
			visaoDto.setMapScripts(new HashMap());
		}
		if (visaoDto.getMapHtmlCodes() == null) {
			visaoDto.setMapHtmlCodes(new HashMap());
		}

		return visaoDto;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		try {
			DinamicViewsDTO dinamicViewsDto = (DinamicViewsDTO) document.getBean();
			DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, WebUtil.getUsuarioSistema(request));
			HashMap hashValores = getFormFields(request);
			if (DEBUG) {
				debugValuesFromRequest(hashValores);
			}

			HashMap<String, Object> map = null;
			try {
				map = JSONUtil.convertJsonToMap(dinamicViewsDto.getDinamicViewsDadosAdicionais(), true);
			} catch (Exception e) {
				System.out.println("dinamicViewsDto.getDinamicViewsDadosAdicionais(): " + dinamicViewsDto.getDinamicViewsDadosAdicionais());
				e.printStackTrace();
				document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
				throw e;
			}

			dinamicViewsDto.setDinamicViewsMapDadosAdicional(map);
			if (map != null) {
				hashValores.putAll(map);
			}

			
			hashValores.put("SESSION.DINAMICVIEWS_SAVEINFO", request.getSession().getAttribute("DinamicViews_SaveInfo"));
			hashValores.put("SESSION.NUMERO_CONTRATO_EDICAO", request.getSession().getAttribute("NUMERO_CONTRATO_EDICAO"));

			if (dinamicViewsDto.getJsonMatriz() != null && !dinamicViewsDto.getJsonMatriz().trim().equalsIgnoreCase("")) {
				dinamicViewsService.saveMatriz(usuarioDto, dinamicViewsDto, hashValores);
			} else {
				dinamicViewsService.save(usuarioDto, dinamicViewsDto, hashValores);
			}
			if (dinamicViewsDto.getMsgRetorno() == null || dinamicViewsDto.getMsgRetorno().trim().equalsIgnoreCase("")) {
				document.alert(UtilI18N.internacionaliza(request, "dinamicview.gravadocomsucesso"));
				if (dinamicViewsDto.getIdFluxo() == null)
					document.executeScript("limpar()");
				else
					document.executeScript("cancelar()");
				document.executeScript("fecharSePOPUP()");
			} else {
				document.alert(dinamicViewsDto.getMsgRetorno());
			}
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		} catch (Exception e) {
			String msgErro = e.getMessage();
			msgErro = msgErro.replaceAll("java.lang.Exception:", "");
			document.alert("" + msgErro);
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}

	private HashMap getFormFields(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("ISO-8859-1");
		} catch (UnsupportedEncodingException e) {
			System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
			e.printStackTrace();
		}
		HashMap formFields = new HashMap();
		Enumeration en = req.getParameterNames();
		String[] strValores;
		while (en.hasMoreElements()) {
			String nomeCampo = (String) en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			if (strValores.length == 0) {
				formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
			} else {
				if (strValores.length == 1) {
					formFields.put(nomeCampo.toUpperCase(), UtilStrings.decodeCaracteresEspeciais(Util.tratarAspasSimples(strValores[0])));
				} else {
					formFields.put(nomeCampo.toUpperCase(), strValores);
				}
			}
		}
		return formFields;
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ScriptsVisaoService scriptsVisaoService = (ScriptsVisaoService) ServiceLocator.getInstance().getService(ScriptsVisaoService.class, null);
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		DinamicViewsDTO dinamicViewsDto = (DinamicViewsDTO) document.getBean();
		DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, WebUtil.getUsuarioSistema(request));
		ParserRequest parser = new ParserRequest();
		HashMap hashValores = parser.getFormFields(request);
		if (DEBUG) {
			debugValuesFromRequest(hashValores);
		}

		HashMap<String, Object> map = JSONUtil.convertJsonToMap(dinamicViewsDto.getDinamicViewsDadosAdicionais(), true);

		dinamicViewsDto.setDinamicViewsMapDadosAdicional(map);
		if (map != null) {
			hashValores.putAll(map);
		}
		
		//tratamento de deleção para a visão de contratos
		
		if(hashValores.containsKey("IDCONTRATO") && hashValores.containsKey("DATAFIMCONTRATO") && hashValores.containsKey("COTACAOMOEDA")){
			boolean defineExclusao = new ServicoContratoServiceEjb().pesquisaServicosVinculados(document, hashValores, request);
			
			if(!defineExclusao){
				return;
			}
		}
		
		Collection colScripts = scriptsVisaoService.findByIdVisao(dinamicViewsDto.getDinamicViewsIdVisao());
		HashMap mapScritps = new HashMap();
		if (colScripts != null) {
			for (Iterator it = colScripts.iterator(); it.hasNext();) {
				ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
				mapScritps.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
			}
		}
		String strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONDELETE.getName());
		
		if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
			Context cx = Context.enter();
			Scriptable scope = cx.initStandardObjects();
			scope.put("usuarioDto", scope, usuarioDto);
			scope.put("document", scope, document);
			scope.put("mapFields", scope, map);
			scope.put("request", scope, request);
			scope.put("response", scope, response);
			Object retorno = scriptExecute.processScript(cx, scope, strScript, DinamicViews.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONDELETE.getName());
		}

		hashValores.put("REMOVED", "true");
		
		/*Thiago Fernandes Oliveira - 28/10/2013 - Sol. 121468 - Só sera excluido algum registro caso ele tenha sido restaurado, evitando o cadastro de algum registro ao clicar em excluir.
		 * Modulos, Liberação, Mudança e Problema.
		 * 
		 */
		if (hashValores.containsKey("IDJUSTIFICATIVAMUDANCA") || hashValores.containsKey("IDJUSTIFICATIVALIBERACAO") || hashValores.containsKey("IDJUSTIFICATIVAPROBLEMA")) {
			if(hashValores.get("IDJUSTIFICATIVAMUDANCA") != null && !hashValores.get("IDJUSTIFICATIVAMUDANCA").equals("") 
			   || hashValores.get("IDJUSTIFICATIVALIBERACAO") != null && !hashValores.get("IDJUSTIFICATIVALIBERACAO").equals("")
			   || hashValores.get("IDJUSTIFICATIVAPROBLEMA") != null && !hashValores.get("IDJUSTIFICATIVAPROBLEMA").equals("")) {
				
				dinamicViewsService.save(usuarioDto, dinamicViewsDto, hashValores); // A exclusão é sempre lógica.
				document.alert(UtilI18N.internacionaliza(request, "MSG07") + "!");
				if (dinamicViewsDto.getIdFluxo() == null) {
					document.executeScript("limpar()");
					document.executeScript("location.reload()");
				} else {
					document.executeScript("cancelar()");
				}
				document.executeScript("fecharSePOPUP()");
				return;
			}else{
				document.alert(UtilI18N.internacionaliza(request, "jornadaTrabalho.necessarioSelecionarRegistro"));
				return;
			}
		} 
		
		dinamicViewsService.save(usuarioDto, dinamicViewsDto, hashValores); // A exclusão é sempre lógica.
		
		/*
		 * Rodrigo Pecci Acorse - 29/11/2013 14h05 - #125019
		 * Esconde a janela de aguarde.
		 */
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		
		document.alert(UtilI18N.internacionaliza(request, "MSG07") + "!");
		
		if (dinamicViewsDto.getIdFluxo() == null) {
			document.executeScript("limpar()");
			document.executeScript("location.reload()");
		} else {
			document.executeScript("cancelar()");
		}
		
		document.executeScript("fecharSePOPUP()");
	}

	public void restoreVisao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer idVisao, Collection colFilter, String metodoOrigem) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		if (usuarioDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, null);
		ScriptsVisaoService scriptsVisaoService = (ScriptsVisaoService) ServiceLocator.getInstance().getService(ScriptsVisaoService.class, null);
		Collection col = dinamicViewsService.restoreVisao(idVisao, colFilter);
		HashMap map = new HashMap();
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();

				if (camposObjetoNegocioDTO.getTipoDB().startsWith("NUMBER") && camposObjetoNegocioDTO.getPrecisionDB() == 0) {
					if (camposObjetoNegocioDTO.getValue() instanceof BigDecimal) {
						BigDecimal aux = (BigDecimal) camposObjetoNegocioDTO.getValue();
						camposObjetoNegocioDTO.setValue(new Integer(aux.intValue()));
					}
				}

				map.put(camposObjetoNegocioDTO.getNomeDB(), camposObjetoNegocioDTO.getValue());

				if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
					if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE)) {
						if (camposObjetoNegocioDTO.getReturnLookupDTO() != null) {
							map.put(camposObjetoNegocioDTO.getNomeDB() + "_label", camposObjetoNegocioDTO.getReturnLookupDTO().getLabel());
						}
					}
				}
			}
			Collection colScripts = scriptsVisaoService.findByIdVisao(idVisao);
			HashMap mapScritps = new HashMap();
			if (colScripts != null) {
				for (Iterator it = colScripts.iterator(); it.hasNext();) {
					ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
					mapScritps.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
				}
			}
			String strScript = (String) mapScritps.get(ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + ScriptsVisaoDTO.SCRIPT_ONRESTORE.getName());
			if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
				ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
				Context cx = Context.enter();
				Scriptable scope = cx.initStandardObjects();
				scope.put("usuarioDto", scope, usuarioDto);
				scope.put("document", scope, document);
				scope.put("mapFields", scope, map);
				scope.put("request", scope, request);
				scope.put("response", scope, response);
				Object retorno = scriptExecute.processScript(cx, scope, strScript, DinamicViews.class.getName() + "_" + ScriptsVisaoDTO.SCRIPT_ONRESTORE.getName());
			}
			document.executeScript("try{limpar();}catch(e){}");
			setValuesFromMap(document, map, "document.form");
			document.getElementById("dinamicViewsIdVisao").setValue("" + idVisao);
			document.executeScript("try{$( '#tabs' ).tabs('select', 0);}catch(e){}");
			document.executeScript("carregaVinculacoes()");

			String modoExibicao = (String) request.getParameter("modoExibicao");
			if (modoExibicao == null || modoExibicao.trim().length() == 0)
				modoExibicao = "N";

			if (!modoExibicao.equals("J")) {
				if (metodoOrigem == null || !metodoOrigem.equalsIgnoreCase("load")) {
					document.executeScript("restore_scripts()");
				}
			} else {
				document.executeScript("restore_scripts()");
			}
			// document.alert("Registro recuperado com sucesso!");
		} else {
			document.alert(UtilI18N.internacionaliza(request, "dinamicview.naofoipossivelrecuperar"));
		}
	}

	public void setValuesFromMap(DocumentHTML document, HashMap map, String formName) {
		for (Iterator it = map.entrySet().iterator(); it.hasNext();) {
			Map.Entry me = (Map.Entry) it.next();
			Object valor = me.getValue();

			// String property = UtilStrings.convertePrimeiraLetra((String)me.getKey(), "L");
			String property = (String) me.getKey();
			property = property.trim();

			String valorTransf = null;
			if ((valor instanceof BigInteger)) {
				valorTransf = UtilFormatacao.formatInt(((BigInteger) valor).intValue(), "################");
			}

			if ((valor instanceof Long)) {
				valorTransf = UtilFormatacao.formatInt(((Long) valor).intValue(), "################");
			}
			if ((valor instanceof Integer)) {
				valorTransf = UtilFormatacao.formatInt(((Integer) valor).intValue(), "################");
			}

			if ((valor instanceof Float)) {
				valorTransf = valor.toString();
			}

			if ((valor instanceof Double)) {
				// valorTransf = UtilFormatacao.formatDecimal(((Double)valor).doubleValue(), "###############0,00");
				valorTransf = UtilFormatacao.formatBigDecimal(new BigDecimal(((Double) valor).doubleValue()), 2);
			}
			if ((valor instanceof BigDecimal)) {
				if (property.startsWith("ID")) {
					valorTransf = UtilFormatacao.formatBigDecimal(((BigDecimal) valor), 2);
					valorTransf = valorTransf.replaceAll(",00", "");
				} else {
					valorTransf = UtilFormatacao.formatBigDecimal(((BigDecimal) valor), 2);
				}
			}
			if ((valor instanceof String)) {
				valorTransf = (String) valor;
			}
			if ((valor instanceof Date)) {
				valorTransf = UtilDatas.dateToSTR((Date) valor);
			}
			if ((valor instanceof Timestamp)) {
				valorTransf = UtilDatas.dateToSTR(new Date(((Timestamp) valor).getTime()));
			}

			if (valorTransf != null) {
				document.executeScript("try{$('#" + property + "').combogrid('setValue', ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('"
						+ StringEscapeUtils.escapeJavaScript(valorTransf) + "')));}catch(e){}");
				document.executeScript("HTMLUtils.setValue('" + property + "', ObjectUtils.decodificaAspasApostrofe(ObjectUtils.decodificaEnter('" + StringEscapeUtils.escapeJavaScript(valorTransf)
						+ "')), " + formName + ")");
			}
		}
	}

	public void tableSearchClick(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

		//Gson gson = new Gson();
		// ColumnsDTO columnsDTO = gson.fromJson(dinamicViewsDTO.getDinamicViewsJson_data(), ColumnsDTO.class);

		HashMap<String, Object> map = null;
		try {
			map = JSONUtil.convertJsonToMap(dinamicViewsDTO.getDinamicViewsJson_data(), true);
		} catch (Exception e) {
			System.out.println("dinamicViewsDTO.getDinamicViewsJson_data(): " + dinamicViewsDTO.getDinamicViewsJson_data());
			e.printStackTrace();
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			throw e;
		}

		VisaoDTO visaoPesquisaDto = recuperaVisao(dinamicViewsDTO.getDinamicViewsIdVisaoPesquisaSelecionada(), false);

		Collection colFilter = new ArrayList();
		if (dinamicViewsDTO.getDinamicViewsAcaoPesquisaSelecionada() != null && dinamicViewsDTO.getDinamicViewsAcaoPesquisaSelecionada().equalsIgnoreCase(VisaoRelacionadaDTO.ACAO_RECUPERAR_PRINCIPAL)) {
			Collection colGrupos = visaoPesquisaDto.getColGrupos();
			int i = 0;
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
				if (grupoVisaoDTO.getColCamposVisao() != null) {
					for (Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

						if (map != null) {
							if (grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getPk().equalsIgnoreCase("S")) {
								// grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().setValue(columnsDTO.getColuna()[i]);
								grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().setValue(map.get(grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getNomeDB()));
								colFilter.add(grupoVisaoCamposNegocioDTO);
							}
						}
						i++;
					}
				}
			}
			String metodoOrigem = "tableSearchClick";
			restoreVisao(document, request, response, dinamicViewsDTO.getDinamicViewsIdVisao(), colFilter, metodoOrigem);
		}
	}

	public void tableEditClick(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DinamicViewsDTO dinamicViewsDTO = (DinamicViewsDTO) document.getBean();

		Gson gson = new Gson();
		ColumnsDTO columnsDTO = gson.fromJson(dinamicViewsDTO.getJsonDataEdit(), ColumnsDTO.class);

		VisaoDTO visaoPesquisaDto = recuperaVisao(dinamicViewsDTO.getIdVisaoEdit(), false);

		Collection colFilter = new ArrayList();
		Collection colGrupos = visaoPesquisaDto.getColGrupos();
		int i = 1; // A primeira coluna (indice 0) é de controle do sistema.
		for (Iterator it = colGrupos.iterator(); it.hasNext();) {
			GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
			if (grupoVisaoDTO.getColCamposVisao() != null) {
				for (Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
					GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

					if (columnsDTO.getColuna() != null && columnsDTO.getColuna().length > i) {
						if (grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().getPk().equalsIgnoreCase("S")) {
							if (columnsDTO.getColuna()[i].equalsIgnoreCase("")) {
								document.alert("Por favor, salve o serviço antes de restaurar o fluxo.");
								return;

							}
							grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto().setValue(columnsDTO.getColuna()[i]);
							colFilter.add(grupoVisaoCamposNegocioDTO);
						}
					}
					i++;
				}
			}
		}
		restoreVisaoEdit(document, request, response, dinamicViewsDTO.getIdVisaoEdit(), colFilter);
	}

	public void restoreVisaoEdit(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer idVisao, Collection colFilter) throws Exception {
		DinamicViewsService dinamicViewsService = (DinamicViewsService) ServiceLocator.getInstance().getService(DinamicViewsService.class, null);
		Collection col = dinamicViewsService.restoreVisao(idVisao, colFilter);
		HashMap map = new HashMap();
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = grupoVisaoCamposNegocioDTO.getCamposObjetoNegocioDto();
				map.put(camposObjetoNegocioDTO.getNomeDB(), camposObjetoNegocioDTO.getValue());

				if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
					if (grupoVisaoCamposNegocioDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioDTO.RELATION_SIMPLE)) {
						if (camposObjetoNegocioDTO.getReturnLookupDTO() != null) {
							map.put(camposObjetoNegocioDTO.getNomeDB() + "_label", camposObjetoNegocioDTO.getReturnLookupDTO().getLabel());
						}
					}
				}
			}
			document.executeScript("limparForm(document.formEdit" + idVisao + ")");
			setValuesFromMap(document, map, "document.formEdit" + idVisao);
			document.executeScript("$( '#TABLE_EDIT_" + idVisao + "' ).dialog( 'open' );");
			// document.alert("Registro recuperado com sucesso!");
		} else {
			document.alert(UtilI18N.internacionaliza(request, "dinamicview.naofoipossivelrecuperar"));
		}
	}

	public void debugValuesFromRequest(HashMap hashValores) {
		Set set = hashValores.entrySet();
		Iterator i = set.iterator();

		System.out.print("------- VALORES DO REQUEST: -------");
		while (i.hasNext()) {
			Map.Entry me = (Map.Entry) i.next();
			System.out.print("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]");
		}
	}

	public Collection<GrupoVisaoCamposNegocioDTO> findCamposTarefa(Integer idTarefa) throws Exception {
		ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		return execucaoSolicitacaoService.findCamposTarefa(idTarefa);
	}
}
