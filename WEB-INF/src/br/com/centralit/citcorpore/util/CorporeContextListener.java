package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.naming.InitialContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletException;
import javax.sql.DataSource;

import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesUtil;

import org.apache.log4j.Logger;

import br.com.centralit.bpm.batch.ThreadVerificaEventos;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.centralit.citcorpore.batch.MonitoraAsterisk;
import br.com.centralit.citcorpore.batch.MonitoraAtivosDiscovery;
import br.com.centralit.citcorpore.batch.MonitoraIncidentes;
import br.com.centralit.citcorpore.batch.ThreadCarregaMenuXML;
import br.com.centralit.citcorpore.batch.ThreadCarregaXmlProcessamentoBatch;
import br.com.centralit.citcorpore.batch.ThreadIniciaGaleriaImagens;
import br.com.centralit.citcorpore.batch.ThreadIniciaProcessamentosBatch;
import br.com.centralit.citcorpore.bean.AnexoBaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.LinguaDTO;
import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.bean.PerfilAcessoUsuarioDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.comm.server.ServidorSocket;
import br.com.centralit.citcorpore.integracao.LinguaService;
import br.com.centralit.citcorpore.metainfo.bean.BibliotecasExternasDTO;
import br.com.centralit.citcorpore.metainfo.bean.ExternalClassDTO;
import br.com.centralit.citcorpore.metainfo.negocio.BibliotecasExternasService;
import br.com.centralit.citcorpore.negocio.AnexoBaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.ContadorAcessoService;
import br.com.centralit.citcorpore.negocio.DicionarioService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.MenuService;
import br.com.centralit.citcorpore.negocio.PalavraGemeaService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoMenuService;
import br.com.centralit.citcorpore.negocio.PerfilAcessoUsuarioService;
import br.com.centralit.citcorpore.negocio.ScriptsService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.VersaoService;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.centralit.lucene.Lucene;
import br.com.centralit.nagios.MonitoraNagios;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTratamentoArquivos;
import br.com.citframework.util.XmlReadLookup;

public class CorporeContextListener implements ServletContextListener {

	private static final Logger LOGGER = Logger.getLogger(CorporeContextListener.class);

	public void contextDestroyed(ServletContextEvent event) {
	}

	/**
	 * Faz a inicializacao de elementos importantes do sistema.
	 */
	@SuppressWarnings("unchecked")
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("CITSMART --->>>> INICIANDO... ");
		Properties props = System.getProperties();
		String dir = event.getServletContext().getRealPath("/");
		String strFinal = "";
		for (int i = 0; i < dir.length(); i++) {
			if (dir.charAt(i) == '\\') {
				strFinal = strFinal + "/";
			} else {
				strFinal = strFinal + dir.charAt(i);
			}
		}

		strFinal = strFinal.replaceAll("/./", "/");
		if (!strFinal.equalsIgnoreCase("") && strFinal.length() > 0) {
			if (strFinal.charAt(strFinal.length() - 1) != '\\' && strFinal.charAt(strFinal.length() - 1) != '/') {
				strFinal = strFinal + "/";
			}
		}

		dir = strFinal;
		props.setProperty("user.dir", dir);
		CitAjaxUtil.caminho_real_app = dir;
		CITCorporeUtil.caminho_real_app = dir;

		System.out.println("CITSMART - CAMINHO DA APP: " + dir);
		/*
		 * Inserido por Emauri - 23/11/2013
		 */
		String serverConfUrl = System.getProperty("jboss.server.config.url");
		URL url = null;
		URI uri = null;
		try {
			url = new URL(serverConfUrl + "citsmart.cfg");
			uri = url.toURI();
		} catch (MalformedURLException e2) {
			url = null;
			uri = null;
		} catch (URISyntaxException e) {
			url = null;
			uri = null;
		} 
		try{
			if(url != null && uri != null && new File(uri).exists()) {
				File fConf = new File(uri);
				CITCorporeUtil.caminho_real_config_file = fConf.getAbsolutePath();
			}else{
				String searchFind = "server";
				int indexSearchWord = dir.indexOf(searchFind);
				indexSearchWord = dir.indexOf("/", indexSearchWord + (searchFind.length() + 1));
				String pathConfigStartMode = dir.substring(0,indexSearchWord); //Determina o diretorio de config, que pode ser /default/conf ou /all/conf ou /minimal/conf ou outro /<dir name>/conf
				pathConfigStartMode += "/conf/citsmart.cfg";
				CITCorporeUtil.caminho_real_config_file = pathConfigStartMode;
			}
		}catch(Exception e){
			String searchFind = "server";
			int indexSearchWord = dir.indexOf(searchFind);
			indexSearchWord = dir.indexOf("/", indexSearchWord + (searchFind.length() + 1));
			String pathConfigStartMode = dir.substring(0,indexSearchWord); //Determina o diretorio de config, que pode ser /default/conf ou /all/conf ou /minimal/conf ou outro /<dir name>/conf
			pathConfigStartMode += "/conf/citsmart.cfg";
			CITCorporeUtil.caminho_real_config_file = pathConfigStartMode;			
		}
		CITCorporeUtil.fazLeituraArquivoConfiguracao();
		/*
		 * Fim - Inserido por Emauri - 23/11/2013
		 */
		
		CITCorporeUtil.lstExternalClasses = new ArrayList();
		CITCorporeUtil.hsmExternalClasses = new HashMap();

		System.out.println("CAMINHO SERVER: " + CITCorporeUtil.CAMINHO_SERVIDOR);

		AdaptacaoBD.getBancoUtilizado(); // este metodo atualizada o valor de CITCorporeUtil.SGBD_PRINCIPAL
		System.out.println("CITSMART --->>>> SGBD DA CONEXAO PRINCIPAL: " + CITCorporeUtil.SGBD_PRINCIPAL);

		String packageName = "br.com.centralit.citcorpore.metainfo.complementos";
		ClassLoader classLoader = br.com.centralit.citcorpore.metainfo.complementos.ComplementoSLA.class.getClassLoader();
		String path = packageName.replace('.', '/');
		try {
			Enumeration<URL> resources = classLoader.getResources(path);
			List<File> dirs = new ArrayList<File>();
			while (resources.hasMoreElements()) {
				URL resource = resources.nextElement();
				dirs.add(new File(resource.getFile()));
			}
			ArrayList<ExternalClassDTO> classes = new ArrayList<ExternalClassDTO>();
			for (File directory : dirs) {
				try {
					String temp = directory.getAbsolutePath();
					temp = temp.replaceAll("%20", " ");
					File fileNovo = new File(temp);
					classes.addAll(findClasses(fileNovo, packageName));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		/**
		 * Tratando a existencia de tabelas
		 * Adiciona na sessão o parametro de instalação
		 * @author flavio.santana
		 */
		ServletContext context = event.getServletContext();
		Connection conn = getConnection();
		if (conn != null) {
			try {
				DatabaseMetaData db_md = conn.getMetaData();
				ResultSet res = null;
				if (CITCorporeUtil.SGBD_PRINCIPAL.toUpperCase().equals(SQLConfig.ORACLE)) {
					/*
					 * Rodrigo Pecci Acorse - 06/12/2013 15h30 - #126457
					 * Adiciona o usuário utilizado na conexão para garantir que o oracle não irá olhar para outras tabelas.
					 */
					String userName = null;
					
					DataSource ds = (DataSource) new InitialContext().lookup(Constantes.getValue("CONTEXTO_CONEXAO") + Constantes.getValue("DATABASE_ALIAS"));
					if (ds != null)	{
						userName = ds.getConnection().getMetaData().getUserName();
						if (userName.equals("")) userName = null;
					}
					
					res = db_md.getTables(null, userName, "PARAMETROCORPORE", new String[] { "TABLE" });
				} else {
					res = db_md.getTables(null, null, "parametrocorpore", new String[] { "TABLE" });
				}
				
				if (!res.next()) {
					context.setAttribute("instalacao", new ServletException("CITSMart Instalacao"));
				}
				HashMap<Integer, String> dadosSGBD = new HashMap<Integer, String>();
				dadosSGBD.put(1, CITCorporeUtil.SGBD_PRINCIPAL);
				dadosSGBD.put(2, conn.getMetaData().getURL());
				dadosSGBD.put(3, conn.getMetaData().getSchemaTerm());
				context.setAttribute("dadosSGBD", dadosSGBD);				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if(conn!=null && !conn.isClosed())
						conn.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}				
			}
		}

		try {
			ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
			System.out.println("CITSMART - Criando parametros novos... iniciando.");
			parametroService.criarParametrosNovos();
			System.out.println("CITSMART - Criando parametros novos... pronto.");
		} catch (Exception e) {
		}

		try {
			ScriptsService scriptsService = (ScriptsService) ServiceLocator.getInstance().getService(ScriptsService.class, null);
			VersaoService versaoService = (VersaoService) ServiceLocator.getInstance().getService(VersaoService.class, null);
			System.out.println("CITSMART - Executando rotina de scripts... iniciando.");
			String erro = scriptsService.executaRotinaScripts();
			if (erro != null && !erro.isEmpty()) {
				System.out.println("CITSMART - Problema ao executar rotina. Detalhes:\n" + erro);
			} else {
				System.out.println("CITSMART - Executando rotina de scripts... pronto.");
			}
			FiltroSegurancaCITSmart.setHaVersoesSemValidacao(versaoService.haVersoesSemValidacao());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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

		System.out.println("CITSMART - Carregando bibliotecas externas...");
		try {
			BibliotecasExternasService bibliotecasExternasService = (BibliotecasExternasService) ServiceLocator.getInstance().getService(BibliotecasExternasService.class, null);
			Collection colLibs = bibliotecasExternasService.list();
			if (colLibs != null) {
				int i = 1;
				for (Iterator it = colLibs.iterator(); it.hasNext();) {
					BibliotecasExternasDTO bibliotecasExternasDTO = (BibliotecasExternasDTO) it.next();
					try {
						JarFile jarFile = new JarFile(bibliotecasExternasDTO.getCaminho());
						generateClassesFromLib(jarFile, bibliotecasExternasDTO.getCaminho(), bibliotecasExternasDTO.getIdBibliotecasExterna());
					} catch (Exception e) {
						System.out.println("CITSMART - Problema ao carregar biblioteca externa... " + bibliotecasExternasDTO.getCaminho());
						e.printStackTrace();
					}
					i++;
				}
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			UsuarioDTO usuarioDTO = new UsuarioDTO();
			UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
			usuarioDTO.setLogin("consultor");
			usuarioDTO = usuarioService.listLogin(usuarioDTO);
			EmpregadoDTO empregadoDTO = new EmpregadoDTO();
			PerfilAcessoUsuarioDTO perfilAcessoUsuarioDTO = new PerfilAcessoUsuarioDTO();
			PerfilAcessoUsuarioService perfilAcessoUsuarioService = (PerfilAcessoUsuarioService) ServiceLocator.getInstance().getService(PerfilAcessoUsuarioService.class, null);
			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			if (usuarioDTO == null) {
				usuarioDTO = new UsuarioDTO();
				empregadoDTO.setNome("Consultor");
				empregadoDTO.setDataCadastro(UtilDatas.getDataAtual());
				empregadoDTO.setNomeProcura("Consultor");
				empregadoDTO.setTelefone("não disponivel");
				empregadoDTO.setIdSituacaoFuncional(1);
				empregadoDTO.setEmail("consultoria@centralit.com.br");
				empregadoDTO = (EmpregadoDTO) empregadoService.create(empregadoDTO);
				String algoritmo = br.com.citframework.util.Constantes.getValue("ALGORITMO_CRIPTOGRAFIA_SENHA");
				if (algoritmo == null || !algoritmo.trim().equalsIgnoreCase("")) {

					algoritmo = "SHA-1";
				}
				usuarioDTO.setLogin("consultor");
				usuarioDTO.setNomeUsuario("Consultor");
				usuarioDTO.setSenha(CriptoUtils.generateHash("admgoiania516", algoritmo));
				usuarioDTO.setDataInicio(UtilDatas.getDataAtual());
				usuarioDTO.setIdEmpregado(empregadoDTO.getIdEmpregado());
				usuarioDTO.setIdEmpresa(1);
				usuarioDTO.setStatus("A");
				usuarioService.create(usuarioDTO);
				perfilAcessoUsuarioDTO.setDataInicio(UtilDatas.getDataAtual());
				perfilAcessoUsuarioDTO.setIdPerfilAcesso(1);
				perfilAcessoUsuarioDTO.setIdUsuario(usuarioDTO.getIdUsuario());
				perfilAcessoUsuarioService.create(perfilAcessoUsuarioDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		XmlReadLookup.getInstance();

		ThreadCarregaXmlProcessamentoBatch threadCarregaXmlProcessamentoBatch = new ThreadCarregaXmlProcessamentoBatch();
		threadCarregaXmlProcessamentoBatch.start();

		/*
		 * Inicia os processamentos batch.
		 */
		ThreadIniciaProcessamentosBatch thread = new ThreadIniciaProcessamentosBatch();
		thread.start();

		/**
		 * Inicia a galeria de imagens.
		 */
		ThreadIniciaGaleriaImagens threadIniciaGaleriaImagens = new ThreadIniciaGaleriaImagens();
		threadIniciaGaleriaImagens.start();

		/**
		 * Inicia o socket que recebe solicitação do agente.
		 */
		ServidorSocket servidor = new ServidorSocket();
		servidor.start();

		/**
		 * Rodrigo Pecci Acorse - 09/12/2013 16h10 - #126343
		 * Caso parâmetro ativado, carrega menu pelo arquivo padrão XML.
		 * A thread que carrega o menu foi comentada e o código adicionado diretamente abaixo para corrigir o problema em que o menu não aparecia corretamente em alguns casos.
		 */
		
		//ThreadCarregaMenuXML threadCarregaMenuXML = new ThreadCarregaMenuXML();
		//threadCarregaMenuXML.start();
		
		try {
			String mostraBotoes = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.LER_ARQUIVO_PADRAO_XML_MENUS, "S");
			if(mostraBotoes.trim().equalsIgnoreCase("S")){
				System.out.println("CITSMART - INICIANDO CARGA DO MENU PELO ARQUIVO XML PADRÃO.");
				
				String diretorio = "";
				try {
					MenuService menuService = null;
					try {
						menuService = (MenuService) ServiceLocator.getInstance().getService(MenuService.class, null);
					} catch (ServiceException e1) {
						e1.printStackTrace();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
					
					String separator = System.getProperty("file.separator");
					String diretorioReceita = CITCorporeUtil.caminho_real_app + "XMLs"  + separator;
					
					File file = new File(diretorioReceita + "menu.xml");
					
					if(menuService != null){
						menuService.gerarCarga(file);
						menuService.deletaMenusSemReferencia();
					
						menuService.gerarCarga(file);
						menuService.deletaMenusSemReferencia();
					}
					
					PerfilAcessoMenuService perfilAcessoMenuService = (PerfilAcessoMenuService) ServiceLocator.getInstance().getService(PerfilAcessoMenuService.class, null);
					perfilAcessoMenuService.atualizaPerfis();
					
					//menuService.gerarCargaNova(file);
					System.out.println("CITSMART - MENUS CARREGADOS COM SUCESSO A PARTIR DO ARQUIVO XML PADRÃO.");
					
				} catch (Exception e) {
					System.out.println("CITSMART - ERRO AO CARREGAR O ARQUIVO XML PADRÃO DE MENU DO DIRETÓRIO " + diretorio);
					e.printStackTrace();
				}
				
			}
		} catch (Exception e) {
			System.out.println("CITSMART - ERRO AO LER PARÂMETRO ID:" + Enumerados.ParametroSistema.LER_ARQUIVO_PADRAO_XML_MENUS + " DO SISTEMA.");
			e.printStackTrace();
		}
		
		MonitoraNagios monitoraNagios = new MonitoraNagios();
		monitoraNagios.start();
		
		MonitoraAtivosDiscovery monitoraAtivosDiscovery = new MonitoraAtivosDiscovery();
		monitoraAtivosDiscovery.start();			

		MonitoraIncidentes monitoraIncidentes = new MonitoraIncidentes();
		monitoraIncidentes.start();

		/**
		 * Inicia a execução dos eventos bpm.
		 */
		ThreadVerificaEventos threadEventos = new ThreadVerificaEventos();
		threadEventos.start();

		/*
		 * Define parametros do JasperReport
		 * Caso não haja fonte no servidor para imprimir relatório, não vai dar exception
		 */
		String fontePadrao = "SansSerif";
		DefaultJasperReportsContext contexto = DefaultJasperReportsContext.getInstance();
		JRPropertiesUtil.getInstance(contexto).setProperty("net.sf.jasperreports.awt.ignore.missing.font", "true");
		JRPropertiesUtil.getInstance(contexto).setProperty("net.sf.jasperreports.default.font.name", fontePadrao);
		
		String asteriskAtivo = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKATIVAR, "N");
        if (asteriskAtivo.equals("S")) {
              MonitoraAsterisk monitoraAsterisk = new MonitoraAsterisk();
              monitoraAsterisk.start();
        }
		
		/**
		 * Cria ou refaz todos os índices da pesquisa Lucene; será executado apenas uma vez ou quando o usuário setar "S" SIM para o parâmetro: LUCENE_REFAZER_INDICES
		 * 
		 * @author euler.ramos;
		 */
		try {
			String refazerIndicesLucene = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LUCENE_REFAZER_INDICES, "S");
			if (refazerIndicesLucene != null) {
				if (refazerIndicesLucene.isEmpty() || refazerIndicesLucene.equals("S")) {
					System.out.println("REALIZANDO REINDEXACAO LUCENE AUTOMATICA");
					Lucene lucene = new Lucene();
					Util.deleteDiretorioAndSubdiretorios(lucene.getDirBaseConhecimento()); // Destruindo índices corrompidos, quando o usuário solicita uma reindexação.
					Util.deleteDiretorioAndSubdiretorios(lucene.getDirAnexos());
					Util.createDiretorio(lucene.getDirBaseConhecimento());
					Util.createDiretorio(lucene.getDirAnexos());
					System.out.println("LUCENE - Base Conhecimento e Anexos ...");
					BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
					AnexoBaseConhecimentoService anexoBaseConhecimentoService = (AnexoBaseConhecimentoService) ServiceLocator.getInstance().getService(AnexoBaseConhecimentoService.class, null);
					ControleGEDService controleGEDService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
					ArrayList<BaseConhecimentoDTO> listaBaseConhecimento = (ArrayList<BaseConhecimentoDTO>) baseConhecimentoService.listarBasesConhecimentoPublicadas();
					ArrayList<AnexoBaseConhecimentoDTO> listaAnexosBaseConhecimento;
					// Tive que criar este "for" porque o Service não traz o DTO
					// alimentado completamente em todos os campos
					for (BaseConhecimentoDTO baseConhecimentoDTO : listaBaseConhecimento) {
						// Avaliação - Média da nota dada pelos usuários
						Double media = baseConhecimentoService.calcularNota(baseConhecimentoDTO.getIdBaseConhecimento());
						if (media != null)
							baseConhecimentoDTO.setMedia(media.toString());
						else
							baseConhecimentoDTO.setMedia(null);
						ContadorAcessoService contadorAcessoService = (ContadorAcessoService) ServiceLocator.getInstance().getService(ContadorAcessoService.class, null);

						// Qtde de cliques
						Integer quantidadeDeCliques = contadorAcessoService.quantidadesDeAcessoPorBaseConhecimnto(baseConhecimentoDTO);
						if (quantidadeDeCliques != null)
							baseConhecimentoDTO.setContadorCliques(quantidadeDeCliques);
						else
							baseConhecimentoDTO.setContadorCliques(0);
						
						listaAnexosBaseConhecimento = (ArrayList<AnexoBaseConhecimentoDTO>) anexoBaseConhecimentoService.consultarAnexosDaBaseConhecimento(baseConhecimentoDTO);
						
						if (listaAnexosBaseConhecimento!=null){
							//Obtendo o conteúdo do Anexo diretamente do arquivo
							for (AnexoBaseConhecimentoDTO anexoBaseConhecimento : listaAnexosBaseConhecimento){
								ControleGEDDTO controleGEDDTO = controleGEDService.getControleGED(anexoBaseConhecimento);
								if ((controleGEDDTO!=null)&&(controleGEDDTO.getIdControleGED()!=null)&&(controleGEDDTO.getIdControleGED()>0)&&
									(controleGEDDTO.getNomeArquivo()!=null)&&(controleGEDDTO.getPasta()!=null)&&(controleGEDDTO.getExtensaoArquivo()!=null)){
									Arquivo arquivo = new Arquivo(controleGEDDTO);
									anexoBaseConhecimento.setTextoDocumento(arquivo.getConteudo());
								}
							}
						}
						lucene.indexarBaseConhecimento(baseConhecimentoDTO,listaAnexosBaseConhecimento);
					}

					System.out.println("LUCENE - Palavras gemeas...");
					PalavraGemeaService palavraGemeaService = (PalavraGemeaService) ServiceLocator.getInstance().getService(PalavraGemeaService.class, null);
					ArrayList<PalavraGemeaDTO> listaPalavrasGemeas = (ArrayList<PalavraGemeaDTO>) palavraGemeaService.list();
					lucene = new Lucene();
					Util.deleteDiretorioAndSubdiretorios(lucene.getDirGemeas()); // Destruindo índices corrompidos, quando o usuário solicita uma reindexação.
					Util.createDiretorio(lucene.getDirGemeas());
					lucene.indexarListaPalavrasGemeas(listaPalavrasGemeas);

					ParametroCorporeService parametroCorporeService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
					ParametroCorporeDTO parametroCorporeDTO = parametroCorporeService.getParamentroAtivo(ParametroSistema.LUCENE_REFAZER_INDICES.id());
					parametroCorporeDTO.setValor("N");
					parametroCorporeService.atualizarParametros(parametroCorporeDTO);
					System.out.println("REINDEXACAO LUCENE CONCLUIDA");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("PROBLEMA NA INDEXAÇÃO LUCENE");
		}
		
		System.out.println("CITSMART --->>>> INICIADO... OK");
	}

	private void generateClassesFromLib(JarFile jar, String pathJar, Integer control) {
		Enumeration e = jar.entries();
		while (e.hasMoreElements()) {
			JarEntry jarFile = (JarEntry) e.nextElement();
			if (jarFile.getName().indexOf(".class") != -1) {
				ExternalClassDTO externalClassDTO = new ExternalClassDTO();

				String newNameJar = CITCorporeUtil.caminho_real_app + "WEB-INF/lib/LIBEXTCITCORPORE_" + control + ".jar";
				UtilTratamentoArquivos.copyFile(pathJar, newNameJar);

				externalClassDTO.setNameJar(newNameJar);
				externalClassDTO.setNameJarOriginal(pathJar);
				externalClassDTO.setNameClass(jarFile.getName());

				if (externalClassDTO.getNameClass() != null) {
					externalClassDTO.setNameClass(externalClassDTO.getNameClass().replaceAll("/", "."));
					externalClassDTO.setNameClass(externalClassDTO.getNameClass().replaceAll(".class", ""));
				}

				CITCorporeUtil.lstExternalClasses.add(externalClassDTO);

				CITCorporeUtil.hsmExternalClasses.put(externalClassDTO.getNameClass(), newNameJar);
			}
		}
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
	
	private static List<ExternalClassDTO> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<ExternalClassDTO> classes = new ArrayList<ExternalClassDTO>();
		if (!directory.exists()) {
			return classes;
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				ExternalClassDTO externalClassDTO = new ExternalClassDTO();
				externalClassDTO.setNameClass(packageName + '.' + file.getName());
				externalClassDTO.setNameClass(externalClassDTO.getNameClass().replaceAll("/", "."));
				externalClassDTO.setNameJar("CLASSES");
				externalClassDTO.setNameJarOriginal("CLASSES");

				CITCorporeUtil.lstExternalClasses.add(externalClassDTO);

				CITCorporeUtil.hsmExternalClasses.put(externalClassDTO.getNameClass(), "CLASSES");
			}
		}
		return classes;
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
}
