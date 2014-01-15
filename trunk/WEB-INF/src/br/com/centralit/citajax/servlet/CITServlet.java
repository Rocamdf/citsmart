package br.com.centralit.citajax.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.centralit.citajax.config.CitAjaxConfig;
import br.com.centralit.citajax.config.RedirectItem;
import br.com.centralit.citajax.framework.CITAutoCompleteProcess;
import br.com.centralit.citajax.framework.CITFacadeProcess;
import br.com.centralit.citajax.framework.CITObjectProcess;
import br.com.centralit.citajax.html.ScriptExecute;
import br.com.centralit.citajax.util.CitAjaxWebUtil;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.LookupDTO;
import br.com.citframework.excecao.DuplicateUniqueException;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.LookupProcessService;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilStrings;

public class CITServlet extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(CITServlet.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = 7373420780718728389L;

	/**
	 * Processa as requisicoes.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pathInfo = request.getRequestURI();
		String ext = "";

		/*
		 * if (br.com.citframework.util.Constantes.SERVER_ADDRESS == nul nl || br.com.citframework.util.Constantes.SERVER_ADDRESS.equalsIgnoreCase("")) { String url = request.getRequestURL().toString();
		 * int index = -1; if (request.getContextPath() != null && !request.getContextPath().equalsIgnoreCase("") && !request.getContextPath().equalsIgnoreCase("/")) { index =
		 * url.indexOf(request.getContextPath()); } if (index > -1) { url = url.substring(0, index); } else { String urlInicial = ""; index = url.indexOf("http://"); if (index > -1) { url =
		 * url.substring(6, index); urlInicial = "http://"; } else { index = url.indexOf("https://"); if (index > -1) { url = url.substring(7, index); urlInicial = "https://"; } } for (int i = 0; i <
		 * url.length(); i++) { if (url.charAt(i) == '/') { break; } else { urlInicial += url.charAt(i); } } url = urlInicial; } br.com.citframework.util.Constantes.SERVER_ADDRESS = url; }
		 */
		String serverAdd = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS");
		if (serverAdd == null) {
			serverAdd = "";
		}
		br.com.citframework.util.Constantes.SERVER_ADDRESS = serverAdd;
		br.com.centralit.citajax.util.Constantes.SERVER_ADDRESS = br.com.citframework.util.Constantes.SERVER_ADDRESS;
		// String servletPath = request.getServletPath();

		try {
			request.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			LOGGER.error("ERRO AO ATRIBUIR UTF-8 no request! --> " + e.getMessage());
		}
		try {
			response.setCharacterEncoding("UTF-8");
		} catch (Exception e) {
			LOGGER.error("ERRO AO ATRIBUIR UTF-8 no response! --> " + e.getMessage());
		}

		Boolean isIE = (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE")>-1);
		if (isIE != null && isIE) {
			// Quando for IE coloca na sessao que eh IE.
			request.getSession(true).setAttribute("isIE", "true");
		}

		try {
			if (pathInfo != null) {
				// System.out.println(">>>>> CITAJAX: >>>> pathInfo: " + pathInfo);
				// Executa um acao
				ext = getObjectExt(pathInfo);
				ext = ext.replaceAll("#", ""); // Evita problemas com href="#"

				// Converte objetos Java em JavaScript.
				if (pathInfo.indexOf("objects/") > -1 || "JSX".equalsIgnoreCase(ext)) {
					CITObjectProcess citProcess = new CITObjectProcess();
					String strResult;
					try {
						strResult = citProcess.process(pathInfo, this.getServletContext());
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}
					if (strResult != null) {
						response.setContentType("text/javascript; charset=UTF-8");
						PrintWriter out = response.getWriter();
						// OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
						out.write(strResult);
					}
					return;
				}
				if (pathInfo.indexOf("ajaxFacade/") > -1) {
					CITObjectProcess citProcess = new CITObjectProcess();
					String strResult;
					try {
						strResult = citProcess.process(pathInfo, this.getServletContext());
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}
					if (strResult != null) {
						response.setContentType("text/javascript; charset=UTF-8");
						// OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
						PrintWriter out = response.getWriter();
						out.write(strResult);
					}
					return;
				}

				// Operacoes de CRUD - Manipulacao de dados
				if ("save".equalsIgnoreCase(ext) || "restore".equalsIgnoreCase(ext) || "event".equalsIgnoreCase(ext)) {
					CITFacadeProcess citFacadeProcess = new CITFacadeProcess();
					String strResult;
					try {
						strResult = citFacadeProcess.process(pathInfo, this.getServletContext(), request, response);
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}
					if (strResult != null) {
						response.setContentType("text/javascript; charset=UTF-8");
						PrintWriter out = null;
						try {
							out = response.getWriter();
						} catch (Exception e) {
						}
						// OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
						if (out != null) {
							out.write(strResult);
						}
					} else {
						response.setContentType("text/javascript; charset=UTF-8");
						PrintWriter out = null;
						try {
							out = response.getWriter();
						} catch (Exception e) {
						}
						if (out != null) {
							out.write("alert('Retorno vazio de chamada Ajax')");
						}
					}
					return;
				}

				// Operacoes de Carregamento de paginas
				if ("load".equalsIgnoreCase(ext)) {
					
					/**
					 * Verifica se o parametro de instalação está em sessão
					 */
					ServletContext context = request.getSession().getServletContext();
					if (context.getAttribute("instalacao") != null) {
						pathInfo = "/citsmart/pages/start/start.load";
					}
					
					CITFacadeProcess citFacadeProcess = new CITFacadeProcess();
					String strResult;
					try {
						strResult = citFacadeProcess.process(pathInfo, this.getServletContext(), request, response);
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}
					/*
					 * if (response.getContentType() != null && !response.getContentType().equalsIgnoreCase("")){ return; }
					 */
					String strForm = getObjectName(pathInfo);

					request.getSession().setAttribute("retornoLoad" + strForm, strResult);
					String url = "";

					CitAjaxConfig config = CitAjaxConfig.getInstance();
					RedirectItem redirectItem = config.getPathInConfig(pathInfo, request);
					if (redirectItem != null) {
						url = redirectItem.getPathOut();
					} else {
						if (Constantes.getValue("CAMINHO_PAGES") != null)
							url = Constantes.getValue("CAMINHO_PAGES") + "/pages/" + strForm + "/" + strForm + ".jsp";
						else
							url = "/pages/" + strForm + "/" + strForm + ".jsp";
					}
					LOGGER.debug("URL ENCAMINHAMENTO>>>: " + url);
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
					return;
				}

				// Auto complete
				if ("complete".equalsIgnoreCase(ext)) {
					CITAutoCompleteProcess citFacadeAutoComplete = new CITAutoCompleteProcess();
					String strResult;
					try {
						strResult = citFacadeAutoComplete.process(pathInfo, this.getServletContext(), request, response);
					} catch (Exception e) {
						e.printStackTrace();
						throw new ServletException(e);
					}

					response.setContentType("text/html; charset=ISO-8859-1");
					PrintWriter out = response.getWriter();
					// OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
					out.write(strResult);
					return;
				}

				// Pega retorno da acao processada anteriormente
				if ("get".equalsIgnoreCase(ext)) {
					String strForm = getObjectName(pathInfo);
					String strResult = (String) request.getSession().getAttribute("retornoLoad" + strForm);
					if (strResult != null) {
						response.setContentType("text/javascript; charset=UTF-8");
						PrintWriter out = response.getWriter();
						out.write(strResult);
					} else {
						response.setContentType("text/javascript; charset=UTF-8");
						PrintWriter out = null;
						try {
							out = response.getWriter();
						} catch (Exception e) {
						}
						if (out != null) {
							out.write("alert('Retorno vazio de chamada Ajax')");
						}
					}
				}

				// Operacoes de Busca de Informacoes
				if ("find".equalsIgnoreCase(ext)) {
					// System.out.println("Solicitacao: " + ext + "   " + pathInfo);
					String urlErro = "/pages/lookup/erro.jsp";
					try {
						IDto user = (IDto) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
						LookupProcessService lookupService = (LookupProcessService) ServiceLocator.getInstance().getService(LookupProcessService.class, null);
						LookupDTO lookup = new LookupDTO();
						lookup.setAcao(UtilStrings.decodeCaracteresEspeciais(request.getParameter("acao")));
						lookup.setNomeLookup(UtilStrings.decodeCaracteresEspeciais(request.getParameter("nomeLookup")));
						lookup.setParm1(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm1")));
						lookup.setParm2(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm2")));
						lookup.setParm3(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm3")));
						lookup.setParm4(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm4")));
						lookup.setParm5(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm5")));
						lookup.setParm6(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm6")));
						lookup.setParm7(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm7")));
						lookup.setParm8(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm8")));
						lookup.setParm9(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm9")));
						lookup.setParm10(UtilStrings.decodeCaracteresEspeciais(request.getParameter("parm10")));
						lookup.setCheckbox(UtilStrings.decodeCaracteresEspeciais(request.getParameter("checkbox")));
						String paginacao = "";
						if (request.getParameter("paginacao") != null) {
							paginacao = UtilStrings.decodeCaracteresEspeciais(request.getParameter("paginacao"));
						}
						lookup.setPaginacao(paginacao);
						lookup.setParmCount(Integer.parseInt("0" + UtilStrings.nullToVazio(request.getParameter("parmCount"))));
						if (user != null) {
							lookup.setUser(user);
						}

						Collection colRetorno = null;
						colRetorno = lookupService.process(lookup,request);
						request.setAttribute("retorno", colRetorno);

						String urlRedirecionarLookup = "/pages/lookup/retornoAjax.jsp";
						if (Constantes.getValue("CAMINHO_PAGES") == null) {
							System.out.println("############################################# CITAJAX ##############################################");
							System.out.println("####################################################################################################");
							System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
							System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
							System.out.println("##### Redirecionamento do lookup: " + Constantes.getValue("CAMINHO_PAGES") + urlRedirecionarLookup);
							System.out.println("####################################################################################################");
						}

						String url = Constantes.getValue("CAMINHO_PAGES") + urlRedirecionarLookup;

						RequestDispatcher dispatcher = request.getRequestDispatcher(url);
						dispatcher.forward(request, response);
					} catch (LogicException e) {
						e.printStackTrace();
						request.setAttribute("mensagem", UtilI18N.internacionaliza(request, e.getMessage().replaceAll("br.com.citframework.excecao.LogicException: ", "")));
						if (Constantes.getValue("CAMINHO_PAGES") == null) {
							System.out.println("############################################# CITAJAX ##############################################");
							System.out.println("####################################################################################################");
							System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
							System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
							System.out.println("##### Redirecionamento para erro: " + Constantes.getValue("CAMINHO_PAGES") + urlErro);
							System.out.println("####################################################################################################");
						}
						String url = Constantes.getValue("CAMINHO_PAGES") + urlErro;
						RequestDispatcher dispatcher = request.getRequestDispatcher(url);
						dispatcher.forward(request, response);
					} catch (ServiceException e) {
						e.printStackTrace();
						if (Constantes.getValue("CAMINHO_PAGES") == null) {
							System.out.println("############################################# CITAJAX ##############################################");
							System.out.println("####################################################################################################");
							System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
							System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
							System.out.println("##### Redirecionamento para erro: " + Constantes.getValue("CAMINHO_PAGES") + urlErro);
							System.out.println("####################################################################################################");
						}
						String url = Constantes.getValue("CAMINHO_PAGES") + urlErro;
						RequestDispatcher dispatcher = request.getRequestDispatcher(url);
						dispatcher.forward(request, response);
					} catch (Exception e) {
						e.printStackTrace();
						if (Constantes.getValue("CAMINHO_PAGES") == null) {
							System.out.println("############################################# CITAJAX ##############################################");
							System.out.println("####################################################################################################");
							System.out.println("##### ATENCAO: a configuracao da chave 'CAMINHO_PAGES' esta nula no arquivo de Constantes.properties");
							System.out.println("##### Isto fara que o Lookup nao funcione corretamente!");
							System.out.println("##### Redirecionamento para erro: " + Constantes.getValue("CAMINHO_PAGES") + urlErro);
							System.out.println("####################################################################################################");
						}
						String url = Constantes.getValue("CAMINHO_PAGES") + urlErro;
						RequestDispatcher dispatcher = request.getRequestDispatcher(url);
						dispatcher.forward(request, response);
					}

				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			String mensagemErro = e.getMessage();
			Throwable ex = e;
			while (ex.getCause() != null) {
				if (ex.getCause() != null) {
					ex = ex.getCause();
				}
			}
			if (ex != null) {
				mensagemErro = ex.getMessage();
			}
			if (mensagemErro == null) {
				mensagemErro = "";
			}

			mensagemErro = mensagemErro.replaceAll("br\\.com\\.citframework\\.excecao\\.FKReferenceException\\:", "");
			mensagemErro = mensagemErro.replaceAll("br\\.com\\.citframework\\.excecao\\.LogicException\\:", "");
            mensagemErro = mensagemErro.replaceAll("br\\.com\\.centralit\\.citcorpore\\.exception\\.LogicException\\:", "");
            mensagemErro = mensagemErro.replaceAll("br\\.com\\.centralit\\.citajax\\.exception\\.LogicException\\:", "");
            mensagemErro = mensagemErro.replaceAll("br\\.com\\.citframework\\.excecao\\.DuplicateUniqueException\\:", "");
            
			// String exec = ExceptionHandler.handleException(e);
			String exec = "";
			String strForm = getObjectName(pathInfo);
			if (!LogicException.class.isInstance(ex)) {
				exec = "Ocorreu um problema na execução: " + mensagemErro + ", contate a equipe de suporte do CITSMart";
			} else if(DuplicateUniqueException.class.isInstance(ex)) {
				exec = "Já existe um campo com este :" + mensagemErro;
			}else{
				exec = mensagemErro;
			}
			if (mensagemErro.indexOf("ORA-") > -1) {
				exec = "Problemas no Banco de dados: " + mensagemErro + ", contate a equipe de suporte do CITSMart";
			}
			if (mensagemErro.indexOf("connection") > -1) {
				exec = "Problemas na Conexão com o Banco de dados: " + mensagemErro + ", contate a equipe de suporte do CITSMart";
			}
			exec = exec.replaceAll("'", "\"");
			if ("load".equalsIgnoreCase(ext)) {
				Collection colRetorno = new ArrayList();
				ScriptExecute script = new ScriptExecute();

				script.setScript("try{JANELA_AGUARDE_MENU.hide();}catch(ex){}");
				colRetorno.add(script);
				script = new ScriptExecute();
				script.setScript("alert('" + CitAjaxWebUtil.codificaEnterByChar(exec, "") + "')");
				colRetorno.add(script);

				String strResult = "";
				try {
					strResult = CitAjaxWebUtil.serializeObjects(colRetorno, true);
				} catch (Exception e1) {
					strResult = "";
				}

				request.getSession().setAttribute("retornoLoad" + strForm, strResult);
				String url = "";

				CitAjaxConfig config = null;
				try {
					config = CitAjaxConfig.getInstance();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (config != null) {
					RedirectItem redirectItem = config.getPathInConfig(pathInfo, request);
					if (redirectItem != null) {
						url = redirectItem.getPathOut();
					} else {
						if (Constantes.getValue("CAMINHO_PAGES") != null)
							url = Constantes.getValue("CAMINHO_PAGES") + "/pages/" + strForm + "/" + strForm + ".jsp";
						else
							url = "/pages/" + strForm + "/" + strForm + ".jsp";
					}
					LOGGER.debug("URL ENCAMINHAMENTO>>>: " + url);
					RequestDispatcher dispatcher = request.getRequestDispatcher(url);
					dispatcher.forward(request, response);
				}
				return;
			} else {
				Collection colRetorno = new ArrayList();
				ScriptExecute script = new ScriptExecute();

				/*
				 * if (e.getMessage()==null){ if (e.getStackTrace()!=null){ exec = e.toString(); }else{ exec = "Erro não identificado"; } }else{ exec = e.getMessage(); }
				 * script.setScript("alert('Excecao: " + CitAjaxWebUtil.codificaEnterByChar(exec, "") + "')");
				 */
				script.setScript("try{JANELA_AGUARDE_MENU.hide();}catch(ex){}");
				colRetorno.add(script);
				exec = exec.replaceAll("'", "\"");
				script = new ScriptExecute();
				script.setScript("alert('" + CitAjaxWebUtil.codificaEnterByChar(exec, "") + "')");
				colRetorno.add(script);
				PrintWriter out = null;
				try {
					out = response.getWriter();
				} catch (Exception eX) {
				}
				// OutputStreamWriter out = new OutputStreamWriter(response.getOutputStream());
				try {
					if (out != null) {
						out.write(CitAjaxWebUtil.serializeObjects(colRetorno, true));
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				response.setContentType("text/javascript; charset=UTF-8");
			}
		}
	}

	/**
	 * Metodo doGet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Metodo doPost
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);
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

	public String getObjectExt(String path) {
		String strResult = "";
		for (int i = path.length() - 1; i >= 0; i--) {
			if (path.charAt(i) == '.') {
				return strResult;
			} else {
				strResult = path.charAt(i) + strResult;
			}
		}
		return strResult;
	}
}
