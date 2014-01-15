package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ADUserDTO;
import br.com.centralit.citcorpore.bean.BaseDTO;
import br.com.centralit.citcorpore.bean.EmailDTO;
import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.centralit.citcorpore.bean.GedDTO;
import br.com.centralit.citcorpore.bean.GeralDTO;
import br.com.centralit.citcorpore.bean.ICDTO;
import br.com.centralit.citcorpore.bean.InstalacaoDTO;
import br.com.centralit.citcorpore.bean.LdapDTO;
import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.bean.LogDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.SmtpDTO;
import br.com.centralit.citcorpore.bean.StartDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.integracao.LinguaService;
import br.com.centralit.citcorpore.integracao.ad.LDAPUtils;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoService;
import br.com.centralit.citcorpore.negocio.DataBaseMetaDadosService;
import br.com.centralit.citcorpore.negocio.DicionarioService;
import br.com.centralit.citcorpore.negocio.EmpresaService;
import br.com.centralit.citcorpore.negocio.InstalacaoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.ScriptsService;
import br.com.centralit.citcorpore.negocio.VersaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.FiltroSegurancaCITSmart;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.ScriptRunner;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * Controller da página de instalação do sistema
 * 
 * @author flavio.santana
 * 
 */
public class Start extends AjaxFormAction {

	private String DEFAULT = "";

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String sessao = (String) request.getSession().getAttribute("passoInstalacao");
		if (sessao != null) {
			document.executeScript("setNext('" + sessao + "')");

			carregaEmail(document, request, response);
			carregaEmpresa(document, request, response);
			carregaParametrosLDAP(document, request, response);
			carregaGED(document, request, response);
			carregaLog(document, request, response);
			carregaSMTP(document, request, response);
			/**
			 * Se versão free não carrega parametros de IC
			 */
			if (!br.com.citframework.util.Util.isVersionFree(request)) {
				carregaParametrosIC(document, request, response);
			}
			carregaParametrosBase(document, request, response);
			carregaParametrosGerais(document, request, response);
		} else {
			reload(document, request, null);
		}

	}

	/**
	 * Gera carga inicial do banco
	 * 
	 * @throws Exception
	 * @throws ServiceException
	 */
	public void gerarCargaInicial(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO start = (StartDTO) document.getBean();
		String sessao = (String) request.getSession().getAttribute("passoInstalacao");
		try {
			if (verificaParametrosConexao(document, request, response)) {
				Connection conn = getConnection();
				if(conn != null){
					conn.setAutoCommit(false);
				}
				if (conn != null) {
					if (sessao == null) {
						setSession(request, start.getCurrent());

						ScriptRunner runner = new ScriptRunner(conn, true, true);
						runner.setDelimiter("(;(\r)?\n)|(--\n)", false);
						try {
							runner.runScript(new File(CITCorporeUtil.caminho_real_app + "/scripts_deploy/" + conn.getMetaData().getDatabaseProductName().replaceAll(" ", "_") + ".sql"));
						} catch (Exception er) {
						}
						/**
						 * Mata da sessão o parametro de instalação
						 */
						ServletContext context = request.getSession().getServletContext();
						context.setAttribute("instalacao", null);
						reload(document, request, null);
					} else {
						carregaEmpresa(document, request, response);
						document.executeScript("setNext('" + start.getCurrent() + "')");
					}
					conn.close();
					conn = null;
				}
			} else {
				document.alert(UtilI18N.internacionaliza(request, "start.instalacao.validaConexao"));
				document.executeScript("habilitaInstall();");
			}
		} catch (Exception e) {
			setSession(request, null);
		}
	}

	/**
	 * Verifica o status da conexão
	 * 
	 * @throws SQLException
	 */
	public boolean verificaParametrosConexao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws SQLException {
		StartDTO conexao = (StartDTO) document.getBean();

		Connection conn = getConnection();
		if (conn != null) {
			if (conn.getMetaData().getDatabaseProductName().equalsIgnoreCase(conexao.getDriverConexao())) {
				conn.close();
				return true;
			}
		}
		return false;
	}

	/**
	 * Retorna a conexão existente
	 * 
	 * @throws SQLException
	 */
	private Connection getConnection() {
		Connection conn = null;
		try {
			conn = ConnectionProvider.getConnection(Constantes.getValue("DATABASE_ALIAS"));

		} catch (Exception e) {
			e.printStackTrace();
		}
		return conn;
	}

	/**
	 * Infomações iniciais de configuração da Empresa
	 * 
	 * @throws Exception
	 * @throws ServiceException
	 */
	public void cadastraEmpresa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO start = (StartDTO) document.getBean();

		EmpresaDTO empresaDTO = new EmpresaDTO();
		EmpresaService empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, null);
		if ("".equals(start.getNomeEmpresa()) || "Default".equals(start.getNomeEmpresa().trim())) {
			document.alert(UtilI18N.internacionaliza(request, "start.instalacao.validaEmpresa"));
			document.executeScript("document.frmEmpresa.nomeEmpresa.focus()");
		} else {
			empresaDTO.setIdEmpresa(1); // Definindo o id padrão da carga
			empresaDTO.setNomeEmpresa(start.getNomeEmpresa());
			empresaDTO.setDetalhamento(start.getDetalhamento());
			empresaDTO.setDataInicio(UtilDatas.getDataAtual());
			empresaService.update(empresaDTO);
			setSession(request, start.getCurrent());
			document.executeScript("setNext('" + start.getCurrent() + "')");
			carregaParametrosLDAP(document, request, response);
		}
	}

	/**
	 * Carrega as informações de empresa
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaEmpresa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		EmpresaService empresaService = (EmpresaService) ServiceLocator.getInstance().getService(EmpresaService.class, null);
		EmpresaDTO empresaDTO = new EmpresaDTO();
		empresaDTO.setIdEmpresa(1);
		empresaDTO = (EmpresaDTO) empresaService.restore(empresaDTO);
		HTMLForm form = document.getForm("frmEmpresa");

		if (empresaDTO != null) {
			form.setValues(empresaDTO);
		}

	}

	/**
	 * Carrega os paramatros do LDAP
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaParametrosLDAP(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		document.executeScript("deleteAllRowsTabelaAtributosLdap()");

		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_URL.id() + ",'" + escape(Enumerados.ParametroSistema.LDAP_URL.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.LDAP_URL) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.DOMINIO_AD.id() + ",'" + escape(Enumerados.ParametroSistema.DOMINIO_AD.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.DOMINIO_AD) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO.id() + ",'" + escape(Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.LDAD_SUFIXO_DOMINIO) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LOGIN_AD.id() + ",'" + escape(Enumerados.ParametroSistema.LOGIN_AD.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.LOGIN_AD) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.SENHA_AD.id() + ",'" + escape(Enumerados.ParametroSistema.SENHA_AD.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.SENHA_AD) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_FILTRO.id() + ",'" + escape(Enumerados.ParametroSistema.LDAP_FILTRO.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.LDAP_FILTRO) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_ATRIBUTO.id() + ",'" + escape(Enumerados.ParametroSistema.LDAP_ATRIBUTO.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.LDAP_ATRIBUTO) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_SN_LAST_NAME.id() + ",'" + escape(Enumerados.ParametroSistema.LDAP_SN_LAST_NAME.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.LDAP_SN_LAST_NAME) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT.id() + ",'" + escape(Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT.getCampoParametroInternacionalizado(request))
				+ "','" + escapeValor(Enumerados.ParametroSistema.ID_PERFIL_ACESSO_DEFAULT) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP.id() + ",'" + escape(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP.getCampoParametroInternacionalizado(request)) + "','"
				+ escapeValor(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_LDAP) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD.id() + ",'"
				+ escape(Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD.getCampoParametroInternacionalizado(request)) + "','" + escapeValor(Enumerados.ParametroSistema.NUMERO_COLABORADORES_CONSULTA_AD) + " ')");
		document.executeScript("addLinhaTabelaAtributosLdap(" + Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO.id() + ",'"
				+ StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO.getCampoParametroInternacionalizado(request)) + "','" + escapeValor(Enumerados.ParametroSistema.LDAP_MOSTRA_BOTAO, "N") + " ')");
	}

	/**
	 * Grava os parametros do LDAP informados pelo usuário
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void configuraLDAP(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO ldapDto = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		/* Atualiza o metodo de autencicação de pasta 1 Proprio / 2LDAP */
		ParametroCorporeDTO parametroCorpore = new ParametroCorporeDTO();
		parametroCorpore.setId(Enumerados.ParametroSistema.METODO_AUTENTICACAO_Pasta.id());
		parametroCorpore.setValor(ldapDto.getMetodoAutenticacao());
		parametroCorporeService.atualizarParametros(parametroCorpore);

		if (ldapDto.getMetodoAutenticacao().equals("2")) {
		//	ADUserDTO adUserDto = LDAPUtils.testarConexao();
			Collection<ADUserDTO> listaAdUserDto = LDAPUtils.testarConexao();

			for (ADUserDTO adUserDto : listaAdUserDto) {
				
			
			if (adUserDto != null) {
				ldapDto.setListLdapDTO((Collection<LdapDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LdapDTO.class, "listAtributoLdapSerializado", request));
				if (ldapDto.getListLdapDTO() != null && !ldapDto.getListLdapDTO().isEmpty()) {
					for (LdapDTO parametroLdap : ldapDto.getListLdapDTO()) {
						ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
						parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoLdap().trim()));
						parametroCorporeDto.setValor(parametroLdap.getValorAtributoLdap().trim());
						parametroCorporeService.atualizarParametros(parametroCorporeDto);
					}
				}
				document.executeScript("setNext('" + ldapDto.getCurrent() + "')");
			} else {
				document.alert(UtilI18N.internacionaliza(request, "instalacao.parametrosConexaoInvalidos"));
			}
			}
		} else {
			document.executeScript("setNext('" + ldapDto.getCurrent() + "')");
		}
		setSession(request, ldapDto.getCurrent());
		carregaEmail(document, request, response);
	}

	/**
	 * Testa os parametros de Conexão LDAP
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void testaLDAP(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		// ADUserDTO adUserDto = LDAPUtils.testarConexao();
		Collection<ADUserDTO> listaAdUserDto = LDAPUtils.testarConexao();

		for (ADUserDTO adUserDto : listaAdUserDto) {

			if (adUserDto != null) {
				StringBuilder stringBuilder = new StringBuilder();
				// stringBuilder.append(UtilI18N.internacionaliza(request, "ldap.conexaosucesso")).append("\n\n");
				stringBuilder.append("sAMAccountName: " + getDisponivel(request, adUserDto.getsAMAccountName()));
				stringBuilder.append("\nE-mail: " + getDisponivel(request, adUserDto.getMail()));
				stringBuilder.append("\nCN: " + getDisponivel(request, adUserDto.getCN()));
				stringBuilder.append("\nSN: " + getDisponivel(request, adUserDto.getSN()));
				stringBuilder.append("\nDN: " + getDisponivel(request, adUserDto.getDN()));
				stringBuilder.append("\nDisplay Name: " + getDisponivel(request, adUserDto.getDisplayName()));

				document.alert(stringBuilder.toString());
			} else {
				document.alert(UtilI18N.internacionaliza(request, "ldap.conexaofalhou"));
			}
		}
	}

	/**
	 * 
	 * @param request
	 * @param atributoLdap
	 * @return
	 */
	private String getDisponivel(HttpServletRequest request, String atributoLdap) {
		if (atributoLdap != null) {
			if (StringUtils.isBlank(atributoLdap)) {
				return UtilI18N.internacionaliza(request, "ldap.naodiponivel");
			}
			return atributoLdap;
		}
		return UtilI18N.internacionaliza(request, "ldap.naodiponivel");
	}

	@SuppressWarnings("unchecked")
	public void configuraEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO emailDto = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		emailDto.setListEmailDTO((Collection<EmailDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(EmailDTO.class, "listAtributoEmailSerializado", request));
		if (emailDto.getListEmailDTO() != null && !emailDto.getListEmailDTO().isEmpty()) {
			for (EmailDTO parametroLdap : emailDto.getListEmailDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoEmail().trim()));
				parametroCorporeDto.setValor(parametroLdap.getValorAtributoEmail().trim());
				parametroCorporeService.atualizarParametros(parametroCorporeDto);
			}
		}
		setSession(request, emailDto.getCurrent());
		document.executeScript("setNext('" + emailDto.getCurrent() + "')");
		carregaLog(document, request, response);
	}

	public void carregaEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		document.executeScript("deleteAllRowsTabelaAtributosEmail()");

		document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.RemetenteNotificacoesSolicitacao.id() + "," + "'"
				+ escape(Enumerados.ParametroSistema.RemetenteNotificacoesSolicitacao.getCampoParametroInternacionalizado(request)) + "'," + "'" + escapeValor(Enumerados.ParametroSistema.RemetenteNotificacoesSolicitacao) + "'," + "'N')");
		document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.EmailUsuario.id() + "," + "'" + escape(Enumerados.ParametroSistema.EmailUsuario.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.EmailUsuario) + "'," + "'N')");
		document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.EmailSenha.id() + "," + "'" + escape(Enumerados.ParametroSistema.EmailSenha.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.EmailSenha) + "'," + "'N')");
		document.executeScript("addLinhaEmail(" + Enumerados.ParametroSistema.EmailAutenticacao.id() + "," + "'" + escape(Enumerados.ParametroSistema.EmailAutenticacao.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.EmailAutenticacao) + "'," + "'S')");
		document.executeScript("changeCheck()");
	}

	/**
	 * Carrega as informações dos parametros do sistema para log
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaLog(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		document.executeScript("deleteAllRowsTabelaAtributosLog()");

		document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.USE_LOG.id() + "," + "'" + StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.USE_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.USE_LOG) + "'," + "'S','','','')");
		document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.FILE_LOG.id() + "," + "'" + escape(Enumerados.ParametroSistema.FILE_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.FILE_LOG) + "'," + "'N', '', '', '')");
		document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.PATH_LOG.id() + "," + "'" + escape(Enumerados.ParametroSistema.PATH_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.PATH_LOG) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);')");
		document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.TIPO_LOG.id() + "," + "'" + escape(Enumerados.ParametroSistema.TIPO_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escape(escapeValor(Enumerados.ParametroSistema.TIPO_LOG)) + "'," + "'N','true','','')");
		document.executeScript("addLinhaLog(" + Enumerados.ParametroSistema.EXT_LOG.id() + "," + "'" + escape(Enumerados.ParametroSistema.EXT_LOG.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.EXT_LOG) + "'," + "'N','','','')");
		document.executeScript("changeCheck()");
	}

	/**
	 * Grava as informações de log do sistema
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void configuraLog(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO log = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		log.setListLogDTO((Collection<LogDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LogDTO.class, "listAtributoLogSerializado", request));
		if (log.getListLogDTO() != null && !log.getListLogDTO().isEmpty()) {
			for (LogDTO parametroLdap : log.getListLogDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoLog().trim()));
				parametroCorporeDto.setValor(parametroLdap.getValorAtributoLog().trim());
				parametroCorporeService.atualizarParametros(parametroCorporeDto);
			}
		}
		setSession(request, log.getCurrent());
		document.executeScript("setNext('" + log.getCurrent() + "')");
		carregaGED(document, request, response);
	}

	/**
	 * Carrega as informações do gerenciamento eletronico de documentos
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaGED(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		document.executeScript("deleteAllRowsTabelaAtributosGed()");
		document.executeScript("addLinhaGed(" + Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH.id() + "," + "'" + escape(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH.getCampoParametroInternacionalizado(request))
				+ "'," + "'" + escapeValor(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);')");

		document.executeScript("addLinhaGed(" + Enumerados.ParametroSistema.GedDiretorio.id() + "," + "'" + escape(Enumerados.ParametroSistema.GedDiretorio.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.GedDiretorio) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);')");
		document.executeScript("changeCheck()");
	}

	/**
	 * Carrega as informações do gerenciamento eletronico de documentos
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaSMTP(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		document.executeScript("deleteAllRowsTabelaAtributosSMTP()");

		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.EmailSMTP.id() + "," + "'" + escape(Enumerados.ParametroSistema.EmailSMTP.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.EmailSMTP) + "'," + "'N', '')");
		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Servidor.id() + "," + "'" + escape(Enumerados.ParametroSistema.SMTP_LEITURA_Servidor.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Servidor) + "'," + "'N', '')");
		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Caixa.id() + "," + "'" + escape(Enumerados.ParametroSistema.SMTP_LEITURA_Caixa.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Caixa) + "'," + "'N', '')");
		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Senha.id() + "," + "'" + escape(Enumerados.ParametroSistema.SMTP_LEITURA_Senha.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Senha) + "'," + "'N', '')");
		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Provider.id() + "," + "'" + escape(Enumerados.ParametroSistema.SMTP_LEITURA_Provider.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Provider) + "'," + "'N', '')");
		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Porta.id() + "," + "'" + escape(Enumerados.ParametroSistema.SMTP_LEITURA_Porta.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Porta) + "'," + "'N', 'Format[Numero]')");
		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_Pasta.id() + "," + "'" + escape(Enumerados.ParametroSistema.SMTP_LEITURA_Pasta.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_Pasta) + "'," + "'N', '')");
		document.executeScript("addLinhaSMTP(" + Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_.id() + "," + "'" + escape(Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.SMTP_LEITURA_LIMITE_) + "'," + "'N', 'Format[Numero]')");
		document.executeScript("changeCheck()");
	}

	/**
	 * Grava as informações de Ged do sistema
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void configuraGed(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO ged = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		/* Atualiza ged interno */
		ParametroCorporeDTO parametroCorpore1 = new ParametroCorporeDTO();
		parametroCorpore1.setId(Enumerados.ParametroSistema.GedInterno.id());
		parametroCorpore1.setValor("S");
		parametroCorporeService.atualizarParametros(parametroCorpore1);

		/* Atualiza a ged interno bd */
		ParametroCorporeDTO parametroCorpore2 = new ParametroCorporeDTO();
		parametroCorpore2.setId(Enumerados.ParametroSistema.GedInternoBD.id());
		parametroCorpore2.setValor("N");
		parametroCorporeService.atualizarParametros(parametroCorpore2);

		ged.setListGedDTO((Collection<GedDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GedDTO.class, "listAtributoGedSerializado", request));
		if (ged.getListGedDTO() != null && !ged.getListGedDTO().isEmpty()) {
			for (GedDTO parametroLdap : ged.getListGedDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoGed().trim()));
				parametroCorporeDto.setValor(parametroLdap.getValorAtributoGed().trim());
				parametroCorporeService.atualizarParametros(parametroCorporeDto);
			}
		}
		setSession(request, ged.getCurrent());
		document.executeScript("setNext('" + ged.getCurrent() + "')");
		carregaSMTP(document, request, response);
	}

	/**
	 * Grava as informações de SMTP do sistema
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void configuraSMTP(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO smtp = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		smtp.setListSmtpDTO((Collection<SmtpDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(SmtpDTO.class, "listAtributoSMTPSerializado", request));
		if (smtp.getListSmtpDTO() != null && !smtp.getListSmtpDTO().isEmpty()) {
			for (SmtpDTO parametroLdap : smtp.getListSmtpDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoSMTP().trim()));
				parametroCorporeDto.setValor(parametroLdap.getValorAtributoSMTP().trim());
				parametroCorporeService.atualizarParametros(parametroCorporeDto);
			}
		}
		setSession(request, smtp.getCurrent());
		document.executeScript("setNext('" + smtp.getCurrent() + "')");
		carregaParametrosIC(document, request, response);
	}

	/**
	 * Carrega as informações do gerenciamento eletronico de documentos
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaParametrosIC(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		document.executeScript("deleteAllRowsTabelaAtributosIC()");

		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO.id() + "," + "'"
				+ escape(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO.getCampoParametroInternacionalizado(request)) + "'," + "'" + escapeValor(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_DESENVOLVIMENTO) + "',"
				+ "'N', '', '', '', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO.id() + "," + "'" + escape(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO.getCampoParametroInternacionalizado(request))
				+ "'," + "'" + escapeValor(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_PRODUCAO) + "'," + "'N', '', '', '', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO.id() + "," + "'"
				+ escape(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO.getCampoParametroInternacionalizado(request)) + "'," + "'" + escapeValor(Enumerados.ParametroSistema.CICLO_DE_VIDA_IC_HOMOLOGACAO) + "',"
				+ "'N', '', '', '', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.NOME_INVENTARIO.id() + "," + "'" + escape(Enumerados.ParametroSistema.NOME_INVENTARIO.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.NOME_INVENTARIO) + "'," + "'N', '', '', '', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA.id() + "," + "'"
				+ StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA.getCampoParametroInternacionalizado(request)) + "'," + "'" + escapeValor(Enumerados.ParametroSistema.ITEM_CONFIGURACAO_MUDANCA)
				+ "'," + "'S', '', '', '', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.ENVIO_PADRAO_EMAIL_IC.id() + "," + "'" + escape(Enumerados.ParametroSistema.ENVIO_PADRAO_EMAIL_IC.getCampoParametroInternacionalizado(request)) + "',"
				+ "'" + escapeValor(Enumerados.ParametroSistema.ENVIO_PADRAO_EMAIL_IC) + "'," + "'N', '', '', '', 'Format[Numero]')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_LICENCA.id() + "," + "'"
				+ escape(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_LICENCA.getCampoParametroInternacionalizado(request)) + "'," + "'" + escapeValor(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_LICENCA) + "',"
				+ "'N', '', '', '', 'Format[Numero]')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.ENVIAR_EMAIL_DATAEXPIRACAO.id() + "," + "'"
				+ escape(Enumerados.ParametroSistema.ENVIAR_EMAIL_DATAEXPIRACAO.getCampoParametroInternacionalizado(request)) + "'," + "'" + escapeValor(Enumerados.ParametroSistema.ENVIAR_EMAIL_DATAEXPIRACAO) + "',"
				+ "'N', '', '', '', 'Format[Numero]')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CaminhoArquivoNetMap.id() + "," + "'" + escape(Enumerados.ParametroSistema.CaminhoArquivoNetMap.getCampoParametroInternacionalizado(request)) + "',"
				+ "'" + escapeValor(Enumerados.ParametroSistema.CaminhoArquivoNetMap) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.FaixaIp.id() + "," + "'" + escape(Enumerados.ParametroSistema.FaixaIp.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.FaixaIp) + "'," + "'N', '', '', '', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.DiretorioXmlAgente.id() + "," + "'" + escape(Enumerados.ParametroSistema.DiretorioXmlAgente.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.DiretorioXmlAgente) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.CaminhoNmap.id() + "," + "'" + escape(Enumerados.ParametroSistema.CaminhoNmap.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.CaminhoNmap) + "'," + "'N', '', '', '', '')");
		document.executeScript("addLinhaParametrosIC(" + Enumerados.ParametroSistema.DiasInventario.id() + "," + "'" + escape(Enumerados.ParametroSistema.DiasInventario.getCampoParametroInternacionalizado(request)) + "'," + "'"
				+ escapeValor(Enumerados.ParametroSistema.DiasInventario) + "'," + "'N', '', '', '' , 'Format[Numero]')");
		document.executeScript("changeCheck()");
	}

	/**
	 * Grava as informações de parametros para item de configuração do sistema
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void configuraParametrosIC(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO paramIC = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		paramIC.setListIcDTO((Collection<ICDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ICDTO.class, "listAtributoICSerializado", request));
		if (paramIC.getListIcDTO() != null && !paramIC.getListIcDTO().isEmpty()) {
			for (ICDTO parametroLdap : paramIC.getListIcDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoIC().trim()));
				parametroCorporeDto.setValor(parametroLdap.getValorAtributoIC().trim());
				parametroCorporeService.atualizarParametros(parametroCorporeDto);
			}
		}
		setSession(request, paramIC.getCurrent());
		document.executeScript("setNext('" + paramIC.getCurrent() + "')");
		carregaParametrosBase(document, request, response);
	}

	/**
	 * Carrega as informações de base de conhecimento
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaParametrosBase(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		document.executeScript("deleteAllRowsTabelaAtributosBase()");
		
		document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.LUCENE_DIR_BASECONHECIMENTO.id() + "," + "'" + escape(Enumerados.ParametroSistema.LUCENE_DIR_BASECONHECIMENTO.getCampoParametroInternacionalizado(request))
				+ "'," + "'" + escapeValor(Enumerados.ParametroSistema.LUCENE_DIR_BASECONHECIMENTO) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
		document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.LUCENE_DIR_PALAVRAGEMEA.id() + "," + "'" + escape(Enumerados.ParametroSistema.LUCENE_DIR_PALAVRAGEMEA.getCampoParametroInternacionalizado(request))
				+ "'," + "'" + escapeValor(Enumerados.ParametroSistema.LUCENE_DIR_PALAVRAGEMEA) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
		document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.LUCENE_DIR_ANEXOBASECONHECIMENTO.id() + "," + "'" + escape(Enumerados.ParametroSistema.LUCENE_DIR_ANEXOBASECONHECIMENTO.getCampoParametroInternacionalizado(request))
				+ "'," + "'" + escapeValor(Enumerados.ParametroSistema.LUCENE_DIR_ANEXOBASECONHECIMENTO) + "'," + "'N', 'true', 'onblur', 'validaDiretorio(this);', '')");
		
		document.executeScript("addLinhaParametrosBase(" + Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_BASECONHECIMENTO.id() + "," + "'"
				+ escape(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_BASECONHECIMENTO.getCampoParametroInternacionalizado(request)) + "'," + "'" + escapeValor(Enumerados.ParametroSistema.AVISAR_DATAEXPIRACAO_BASECONHECIMENTO)
				+ "'," + "'N', '', '', '', 'Format[Numero]')");
		document.executeScript("changeCheck()");
	}

	/**
	 * Grava as informações de parametros para base de conhecimento do sistema
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void configuraParametrosBase(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO paramBase = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);

		paramBase.setListBaseDTO((Collection<BaseDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(BaseDTO.class, "listAtributoBaseSerializado", request));
		if (paramBase.getListBaseDTO() != null && !paramBase.getListBaseDTO().isEmpty()) {
			for (BaseDTO parametroLdap : paramBase.getListBaseDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoBase().trim()));
				parametroCorporeDto.setValor(parametroLdap.getValorAtributoBase().trim());
				parametroCorporeService.atualizarParametros(parametroCorporeDto);
			}
		}
		setSession(request, paramBase.getCurrent());
		document.executeScript("setNext('" + paramBase.getCurrent() + "')");
		carregaParametrosGerais(document, request, response);
	}

	/**
	 * Carrega as informações de base de conhecimento
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaParametrosGerais(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		document.executeScript("deleteAllRowsTabelaAtributosGerais()");
		
		document.executeScript("addLinhaParametrosGerais(" + Enumerados.ParametroSistema.URL_Sistema.id() + "," +
				"'" + StringEscapeUtils.escapeJavaScript(Enumerados.ParametroSistema.URL_Sistema.getCampoParametroInternacionalizado(request)) + "'," +
						"'" + escapeValor(Enumerados.ParametroSistema.URL_Sistema) + "'," +
								"'N')");
		
		/*
		 * Rodrigo Pecci Acorse - 09/12/2013 14h25 - #126457
		 * Adiciona o input que solicita o nome do schema somente se o banco de dados for diferente de Sql Server e Oracle
		 */
		if(!CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER) && !CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			document.executeScript("addLinhaParametrosGerais(" + Enumerados.ParametroSistema.DB_SCHEMA.id() + "," +
				"'" + escape(Enumerados.ParametroSistema.DB_SCHEMA.getCampoParametroInternacionalizado(request)) + "'," +
						"'"	+ escapeValor(Enumerados.ParametroSistema.DB_SCHEMA)  +"'," +
								"'N')");
		}
		document.executeScript("changeCheck()");
	}

	@SuppressWarnings("unchecked")
	public void configuraParametrosGerais(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO paramBase = (StartDTO) document.getBean();
		ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
		InstalacaoService instalacaoService = (InstalacaoService) ServiceLocator.getInstance().getService(InstalacaoService.class, null);
		MenuService menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
		
		/*
		 * Rodrigo Pecci Acorse - 06/12/2013 15h30 - #126457
		 * Seta o schema nos parametros do sistema se o banco for Oracle.
		 */
		if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
			String userName = null;
			
			DataSource ds = (DataSource) new InitialContext().lookup(Constantes.getValue("CONTEXTO_CONEXAO") + Constantes.getValue("DATABASE_ALIAS"));
			if (ds != null)	{
				userName = ds.getConnection().getMetaData().getUserName();
				if (userName != null && !userName.equals("")) {
					ParametroCorporeDTO parametroCorporeSchema = new ParametroCorporeDTO();
					parametroCorporeSchema.setId(Enumerados.ParametroSistema.DB_SCHEMA.id());
					parametroCorporeSchema.setValor(userName);
					parametroCorporeService.atualizarParametros(parametroCorporeSchema);
				}
			}
		}

		ParametroCorporeDTO parametroCorpore1 = new ParametroCorporeDTO();
		parametroCorpore1.setId(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO.id());
		parametroCorpore1.setValor("/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load");
		parametroCorporeService.atualizarParametros(parametroCorpore1);

		ParametroCorporeDTO parametroCorpore2 = new ParametroCorporeDTO();
		parametroCorpore2.setId(Enumerados.ParametroSistema.NomeFluxoPadraoServicos.id());
		parametroCorpore2.setValor("SolicitacaoServico");
		parametroCorporeService.atualizarParametros(parametroCorpore2);

		ParametroCorporeDTO parametroCorpore3 = new ParametroCorporeDTO();
		parametroCorpore3.setId(Enumerados.ParametroSistema.ID_GRUPO_PADRAO_NIVEL1.id());
		parametroCorpore3.setValor("2");
		parametroCorporeService.atualizarParametros(parametroCorpore3);

		ParametroCorporeDTO parametroCorpore4 = new ParametroCorporeDTO();
		parametroCorpore4.setId(Enumerados.ParametroSistema.IDFaseExecucaoServicos.id());
		parametroCorpore4.setValor("2");
		parametroCorporeService.atualizarParametros(parametroCorpore4);

		ParametroCorporeDTO parametroCorpore5 = new ParametroCorporeDTO();
		parametroCorpore5.setId(Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS.id());
		parametroCorpore5.setValor("S");
		parametroCorporeService.atualizarParametros(parametroCorpore5);

		// ParametroCorporeDTO parametroCorpore6 = new ParametroCorporeDTO();
		// parametroCorpore6.setId(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS.id());
		// parametroCorpore6.setValor("S");
		// parametroCorporeService.atualizarParametros(parametroCorpore6);

		paramBase.setListGeralDTO((Collection<GeralDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GeralDTO.class, "listAtributoGeraisSerializado", request));
		if (paramBase.getListGeralDTO() != null && !paramBase.getListGeralDTO().isEmpty()) {
			for (GeralDTO parametroLdap : paramBase.getListGeralDTO()) {
				ParametroCorporeDTO parametroCorporeDto = new ParametroCorporeDTO();
				parametroCorporeDto.setId(Integer.parseInt(parametroLdap.getIdAtributoGeral().trim()));
				parametroCorporeDto.setValor(parametroLdap.getValorAtributoGeral().trim());
				parametroCorporeService.atualizarParametros(parametroCorporeDto);
			}
		}

		// Carrega script de Banco
		try {
			carregaScript();
			// Verificação de instalação
		} catch (Exception ex) {
			document.alert(ex.getMessage());
		}
		
		// Carrega dicionario
		try {
			Collection<LinguaDTO> linguaDTOs = new ArrayList();
			LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
			linguaDTOs = linguaService.listarAtivos();
			if (linguaDTOs != null) {
				for (LinguaDTO linguadDto : linguaDTOs) {
					criarMensagensNovos(linguadDto);
				}
			} else {
				linguaDTOs = new ArrayList();
				LinguaDTO linguaDTO = new LinguaDTO();
				linguaDTO.setNome("Português");
				linguaDTO.setSigla("pt");
				linguaDTO.setDataInicio(UtilDatas.getDataAtual());
				linguaService.create(linguaDTO);
				linguaDTOs.add(linguaDTO);
				linguaDTO.setNome("English");
				linguaDTO.setSigla("EN");
				linguaDTO.setDataInicio(UtilDatas.getDataAtual());
				linguaService.create(linguaDTO);
				linguaDTOs.add(linguaDTO);
				linguaDTO.setNome("Español");
				linguaDTO.setSigla("ES");
				linguaDTO.setDataInicio(UtilDatas.getDataAtual());
				linguaService.create(linguaDTO);
				linguaDTOs.add(linguaDTO);
				for (LinguaDTO linguadDto : linguaDTOs) {
					criarMensagensNovos(linguadDto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		// Carrega os menus
		String separator = System.getProperty("file.separator");
		String diretorioReceita = CITCorporeUtil.caminho_real_app + "XMLs" + separator;
		File file = new File(diretorioReceita + "menu.xml");
		menuService.gerarCarga(file);

		/* Carregando as dinamic views */
		try {
			carregaVisoes(request);
			carregaVisoes(request);
			
			request.getSession().setAttribute("passoInstalacao", null);

			ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, null);
			if (scriptsService.haScriptDeVersaoComErro()) {
				scriptsService.marcaErrosScriptsComoCorrigidos();
			}
			VersaoService versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);
			versaoService.validaVersoes(WebUtil.getUsuario(request));

			reload(document, request, UtilI18N.internacionaliza(request, "citcorpore.comum.citsmartInstaladoComSucesso"));

		} catch (Exception ex) {
			document.alert(ex.getMessage());
			document.executeScript("habilita();");
		}
		
		/**
		 * Define a instalação com sucesso
		 */
		InstalacaoDTO instalacaoDTO = new InstalacaoDTO();
		instalacaoDTO.setSucesso("S");
		instalacaoService.create(instalacaoDTO);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getBeanClass() {
		return StartDTO.class;
	}

	/**
	 * Defini o valor da sessão para os passos da instalação
	 * 
	 * @param request
	 * @param sessao
	 */
	public void setSession(HttpServletRequest request, String sessao) {
		String s = (String) request.getSession().getAttribute("passoInstalacao");
		if (s != null) {
			try {
				if (Integer.valueOf(UtilStrings.apenasNumeros(sessao)) > Integer.valueOf(UtilStrings.apenasNumeros(s))) {
					request.getSession().setAttribute("passoInstalacao", sessao);
				}
			} catch (Exception e) {
			}
		} else {
			request.getSession().setAttribute("passoInstalacao", sessao);
		}
	}

	/**
	 * Dá um location para a página inicial
	 * 
	 * @param document
	 * @param request
	 */
	public void reload(DocumentHTML document, HttpServletRequest request, String mensagem) {
		String comando = "window.location = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/index/index.load";
		if (mensagem != null && !mensagem.trim().isEmpty()) {
			comando += "?mensagem=" + mensagem;
		}
		comando += "';";
		document.executeScript(comando);
	}

	/**
	 * Dá um location para a página inicial
	 * 
	 * @param document
	 * @param request
	 */
	public void reloadPaginaScript(DocumentHTML document, HttpServletRequest request) {
		document.executeScript("window.location = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/scripts/scripts.load?upgrade=sim';");
		// document.executeScript("window.location = '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/pages/scripts/scripts.load?upgrade=sim'';");
	}

	/**
	 * Valida se Existe o diretório
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void validaDiretorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		StartDTO diretorio = (StartDTO) document.getBean();
		String campo = diretorio.getCampoDiretorio();
		if (!diretorio.getDiretorio().equals("")) {
			/* document.alert(UtilI18N.internacionaliza(request, "start.instalacao.informeDiretorio")); */
			/* document.executeScript("$('#"+campo+"').focus();"); */
			if (!new File(diretorio.getDiretorio()).isDirectory()) {
				document.alert(UtilI18N.internacionaliza(request, "start.instalacao.diretorioNaoEncontrado"));
				if (campo != null) {
					document.executeScript("$('#" + campo + "').val('');");
					document.executeScript("$('#" + campo + "').focus();");
				}
			}
		}
	}

	/**
	 * Realiza a leitura de um arquivo e incrementa em uma lista de uploads
	 * 
	 * @param dir
	 * @param lista
	 * @return
	 */
	public static java.util.List<UploadDTO> listDirectoryAppend(File dir, java.util.List<UploadDTO> lista) {
		if (dir.isDirectory()) {
			String[] filhos = dir.list();
			for (int i = 0; i < filhos.length; i++) {
				File nome = new File(dir, filhos[i]);
				if (nome.isFile()) {
					if (nome.getName().endsWith(".citVision")) {
						lista.add(new UploadDTO(nome.getName(), nome.getPath()));
					}
				} else if (nome.isDirectory()) {
					listDirectoryAppend(nome, lista);
				}
			}
		} else {
			lista.add(new UploadDTO(dir.getName(), dir.getPath()));
		}
		return lista;
	}

	/**
	 * Carrega as Visoes da Dinamic Views para instalação do sistema
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public void carregaVisoes(HttpServletRequest request) throws ServiceException, Exception {

		DataBaseMetaDadosService dataBaseMetaDadosService = (DataBaseMetaDadosService) ServiceLocator.getInstance().getService(DataBaseMetaDadosService.class, null);

		Collection colection = dataBaseMetaDadosService.getDataBaseMetaDadosUtil();
		if (colection != null && !colection.isEmpty()) {
			/* Carregando metaDados */
			dataBaseMetaDadosService.carregaTodosMetaDados(colection);

			List<UploadDTO> lista = new ArrayList<UploadDTO>();
			listDirectoryAppend(new File(CITCorporeUtil.caminho_real_app + "/visoesExportadas"), lista);

			importaVisoes(lista);

		} else {
			throw new Exception(UtilI18N.internacionaliza(request, "start.metaDadosException"));
		}
	}

	/**
	 * Carrega rotina de script
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void carregaScript() throws ServiceException, Exception {
		try {
			ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, null);
			System.out.println("CITSMART - Executando rotina de scripts... iniciando.");
			String erro = scriptsService.executaRotinaScripts();
			if (erro != null && !erro.isEmpty()) {
				System.out.println("CITSMART - Problema ao executar rotina. Detalhes:\n" + erro);
			} else {
				System.out.println("CITSMART - Executando rotina de scripts... pronto.");
			}
			VersaoService versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);
			FiltroSegurancaCITSmart.setHaVersoesSemValidacao(versaoService.haVersoesSemValidacao());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param arquivosUpados
	 * @throws Exception
	 * @throws ServiceException
	 */
	public void importaVisoes(List<UploadDTO> arquivosUpados) throws ServiceException, Exception {
		VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
		FileReader reader = null;
		VisaoDTO visaoAtual = null;
		VisaoDTO visaoDtoXML = new VisaoDTO();

		int countImport = 0;
		int countAtualiza = 0;

		if (arquivosUpados != null && arquivosUpados.size() > 0) {

			for (UploadDTO uploadDTO : arquivosUpados) {
				String path = uploadDTO.getPath();
				if (path != null && !path.isEmpty()) {
					try {
						reader = new FileReader(path);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					XStream x = new XStream(new DomDriver("ISO-8859-1"));
					visaoDtoXML = (VisaoDTO) x.fromXML(reader);
					visaoAtual = visaoService.visaoExistente(visaoDtoXML.getIdentificador());
					// Determina o tipo da importação
					if (visaoAtual == null) {
						try {
							visaoService.importar(visaoDtoXML);
							countImport++;
						} catch (Exception e) {
							System.out.println("Erro ao importar visão: " + visaoDtoXML.getIdentificador());
							e.printStackTrace();
						}
					} else {
						try {
							visaoService.atualizarVisao(visaoAtual, visaoDtoXML);
							countAtualiza++;
						} catch (Exception e) {
							System.out.println("Erro ao atualizar visão: " + visaoDtoXML.getIdentificador());
							e.printStackTrace();
						}
					}
				}
			}
			System.out.println("Visões importadas com sucesso: " + countImport + "\n" + "Visões atualizadas com sucesso: " + countAtualiza);

		} else {
			System.out.println("Nenhum arquivo foi selecionado!");
		}

	}

	private String escape(String valor) {
		return StringEscapeUtils.escapeJavaScript(valor);
	}

	private String escapeValor(ParametroSistema parametro, String df) throws Exception {
		return ParametroUtil.getValorParametroCitSmartHashMap(parametro, df);
	}

	private String escapeValor(ParametroSistema parametro) throws Exception {
		return ParametroUtil.getValorParametroCitSmartHashMap(parametro, DEFAULT);
	}
	
	private void criarMensagensNovos(LinguaDTO lingua) throws Exception {

		if ((lingua != null) && (lingua.getIdLingua() == null || lingua.getIdLingua().intValue() == 0)) {
			return;
		}
		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
		if (lingua != null && lingua.getIdLingua() != null) {
			LinguaService linguaService = (LinguaService) ServiceLocator.getInstance().getService(LinguaService.class, null);
			LinguaDTO linguaDto = new LinguaDTO();
			linguaDto = lingua;
			if (linguaDto != null) {
				/* auxNome e' usado para se trabalhar com nome da lingua. */ 
				String auxNome = "";
				if (linguaDto.getNome() != null) {
					auxNome = linguaDto.getNome().toLowerCase();
				}

				/*
				 * Acrescentada a verificacao se o nome e' similiar a "portug" para obter o arquivo correto.
				 */
				if (linguaDto.getSigla() == null || auxNome.contains("portug")) {
					linguaDto.setSigla("");
				}
				dicionarioService.criarMensagensNovos(null, linguaDto.getSigla(), linguaDto.getIdLingua());
			}
		}

	}

}
