package br.com.centralit.citcorpore.ajaxForms;

import java.io.File;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.htmlparser.jericho.Source;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRSwapFileVirtualizer;
import net.sf.jasperreports.engine.util.JRSwapFile;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.derby.iapi.services.io.ArrayUtil;
import org.openqa.selenium.internal.seleniumemulation.GetAttribute;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.AprovacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.AprovacaoPropostaDTO;
import br.com.centralit.citcorpore.bean.AtividadePeriodicaDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContatoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoAtvPeriodicaDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.GrupoRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.HistoricoMudancaDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaRequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LiberacaoMudancaDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaMudancaDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.bean.ProgramacaoAtividadeDTO;
import br.com.centralit.citcorpore.bean.RequisicaoLiberacaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaResponsavelDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaRiscoDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoMudancaDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoMudancaDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.AprovacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.GrupoRequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.HistoricoMudancaDao;
import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaResponsavelDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaRiscoDao;
import br.com.centralit.citcorpore.integracao.RequisicaoMudancaServicoDao;
import br.com.centralit.citcorpore.integracao.ServicoDao;
import br.com.centralit.citcorpore.integracao.SolicitacaoServicoMudancaDao;
import br.com.centralit.citcorpore.negocio.AprovacaoMudancaService;
import br.com.centralit.citcorpore.negocio.AprovacaoPropostaService;
import br.com.centralit.citcorpore.negocio.AtividadePeriodicaService;
import br.com.centralit.citcorpore.negocio.CategoriaMudancaService;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContatoRequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoAtvPeriodicaService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoRequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.HistoricoMudancaService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.JustificativaRequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.LiberacaoMudancaService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.OcorrenciaMudancaService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ProblemaMudancaService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoLiberacaoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaResponsavelService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaRiscoService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaServiceEjb;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoMudancaService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.integracao.ControleGEDDao;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.integracao.TransactionControlerImpl;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

/**
 * @author breno.guimaraes
 * 
 */
@SuppressWarnings({ "rawtypes", "unused", "unchecked" })
public class RequisicaoMudanca extends AjaxFormAction {

	private RequisicaoMudancaService requisicaoMudancaService;
	private EmpregadoService empregadoService;

	private RequisicaoMudancaServiceEjb reqMudancaICAction;
	private RequisicaoMudancaServico reqMudancaServicoAction;
	private CategoriaMudancaService categoriaMudancaService;

	private RequisicaoMudancaDTO requisicaoMudancaDto;
	private UsuarioDTO usuario;
	
	private  String localeSession = null;
	
	private RequisicaoLiberacaoDTO liberacaoDto;
	

	@Override
	public Class getBeanClass() {
		return RequisicaoMudancaDTO.class;
	}

	ContratoDTO contratoDtoAux = new ContratoDTO();

	private Boolean acao = false;
	private static HttpServletRequest requestGlobal;
	private static DocumentHTML documentGlobal;
	
	/*Thiago Fernandes - 23/10/2013 - Sol. 121468 - Realização das correções dos testes feitos nas telas do sistema. Branch 3.0.3. Assim que aver apenas um contrato deve ser seleciona-lo como default.
	 * 
	 * 
	 */
	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

		document.executeScript("$('#abas').show()");
		document.executeScript("$('#requisicaMudancaStatus').show()");
		document.executeScript("$('#statusCancelado').hide()");
		
		/**
		 * Adicionado para fazer limpeza do upload que está na sessão
		 * Modificado para quando for solicitação serviço
		 * @author maycon.fernandes
		 * @author mario.junior
		 * @since 28/10/2013 08:21
		 * @author thiago.oliveira
		 * @since 29/10/2013 08:21
		 */
		request.getSession(true).setAttribute("colUploadPlanoDeReversaoGED", null);
		request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", null);
		String flagGerenciamento = (String)request.getSession(true).getAttribute("flagGerenciamento");
		if(flagGerenciamento!= null && flagGerenciamento.equalsIgnoreCase("S")){
			request.getSession(true).setAttribute("flagGerenciamento", null);	
		}
		/*document.executeScript("document.form.status[0].disabled = true;");
		document.executeScript("document.form.status[1].disabled = true;");
		document.executeScript("document.form.status[2].disabled = true;");
		document.executeScript("document.form.status[3].disabled = true;");*/			
		
		// INICIO_LOAD
		if(requisicaoMudancaDto == null || requisicaoMudancaDto.getIdRequisicaoMudanca() == null){
			document.getElementById("btOcorrencias").setVisible(false);
			//document.executeScript("bloqueiaMenusDeConsulta();");
		}
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<GrupoDTO> lstGrupos = grupoService.getGruposByEmpregado(usuario.getIdEmpregado());

		if (lstGrupos != null) {
			for (GrupoDTO g : lstGrupos) {
				if (g.getAbertura() != null && g.getAbertura().trim().equals("S"))
					document.getElementById("enviaEmailCriacao").setDisabled(true);
					document.getElementById("enviaEmailGrupoComite").setDisabled(true);
				if (g.getEncerramento() != null && g.getEncerramento().trim().equals("S"))
					document.getElementById("enviaEmailFinalizacao").setDisabled(true);
				if (g.getAndamento() != null && g.getAndamento().trim().equals("S"))
					document.getElementById("enviaEmailAcoes").setDisabled(true);
			}
		}

		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
		FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
		ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
		Collection colContratos = contratoService.list();

		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		Collection colContratosColab = null;
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			colContratosColab = contratosGruposService.findByIdEmpregado(usuario.getIdEmpregado());
		}
		
		Collection<ContratoDTO> listaContratos = new ArrayList<ContratoDTO>();
		((HTMLSelect) document.getSelectById("idContrato")).removeAllOptions();
		if (colContratos != null) {
			if (colContratos.size() > 1) {
				//((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			} else {
				acao = true;
			}

			for (Iterator it = colContratos.iterator(); it.hasNext();) {
				ContratoDTO contratoDto = (ContratoDTO) it.next();
				if (contratoDto.getDeleted() == null || !contratoDto.getDeleted().equalsIgnoreCase("y")) {
					if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) { // Se
																				// parametro
																				// de
																				// colaboradores
																				// por
																				// contrato
																				// ativo,
																				// entao
																				// filtra.
						if (colContratosColab == null) {
							continue;
						}
						if (!isContratoInList(contratoDto.getIdContrato(), colContratosColab)) {
							continue;
						}
					}

					String nomeCliente = "";
					String nomeForn = "";
					ClienteDTO clienteDto = new ClienteDTO();
					clienteDto.setIdCliente(contratoDto.getIdCliente());
					clienteDto = (ClienteDTO) clienteService.restore(clienteDto);
					if (clienteDto != null) {
						nomeCliente = clienteDto.getNomeRazaoSocial();
					}
					FornecedorDTO fornecedorDto = new FornecedorDTO();
					fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
					fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
					if (fornecedorDto != null) {
						nomeForn = fornecedorDto.getRazaoSocial();
					}
					contratoDtoAux.setIdContrato(contratoDto.getIdContrato());
					if (contratoDto.getSituacao().equalsIgnoreCase("A")) {
						String nomeContrato = "" + contratoDto.getNumero() + " de " + UtilDatas.dateToSTR(contratoDto.getDataContrato()) + " (" + nomeCliente + " - " + nomeForn + ")";
						contratoDto.setNome(nomeContrato);
						listaContratos.add(contratoDto);
					}
				}
			}
		}
		
		/*Thiago Fernandes - 28/10/2013 - 1410 - Sol. 121468 - Assim que aver apenas um contrato deve ser preenchido a combo unidade de acordo com o contrato.*/
		if (listaContratos != null) {
			if (listaContratos.size() == 1) {
				for(ContratoDTO contratoDto: listaContratos) {
					((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());
					requisicaoMudancaDto.setIdContrato(contratoDto.getIdContrato());
					carregaUnidade(document, request, response);
				}
			}
			if (listaContratos.size() > 1) {
				((HTMLSelect) document.getSelectById("idContrato")).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
				for(ContratoDTO contratoDto: listaContratos) {
					((HTMLSelect) document.getSelectById("idContrato")).addOption("" + contratoDto.getIdContrato(), contratoDto.getNome());
				}
			}
		}

		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdContrato() != null) {
			document.getElementById("idContrato").setValue("" + requisicaoMudancaDto.getIdContrato());
		}

		if (request.getParameter("idContrato") != null && !request.getParameter("idContrato").equalsIgnoreCase("")) {
			Integer idContrato = 0;
			idContrato = Integer.parseInt(request.getParameter("idContrato"));

			if (idContrato != null) {
				document.getElementById("idContrato").setValue("" + idContrato);
			}
		}

		if(requisicaoMudancaDto != null){
			requisicaoMudancaDto.getIdContrato();
		}
		String tarefaAssociada = "N";
		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdTarefa() != null)
			tarefaAssociada = "S";
			request.setAttribute("tarefaAssociada", tarefaAssociada);

		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdContrato() != null) {
			verificaGrupoExecutor(document, request, response);
			document.getElementById("idGrupoAtual").setValue("" + requisicaoMudancaDto.getIdGrupoAtual());
			// document.getSelectById("idGrupoAtual").setValue("" + requisicaoMudancaDto.getIdGrupoAtual());
		}
		if (acao) {
			if (requisicaoMudancaDto.getIdRequisicaoMudanca() == null || requisicaoMudancaDto.getIdRequisicaoMudanca().intValue() == 0) {
				this.verificaGrupoExecutor(document, request, response);
				this.carregaUnidade(document, request, response);
			}

		}
		
		//limpa os formularios e dos anexos.
		this.limpar(document, request, response);

		document.executeScript("$('#loading_overlay').hide();");

		this.preencherComboComite(document, request, response);
		this.preencherComboGrupoExecutor(document, request, response);
		this.preencherComboCategoriaSolucao(document, request, response);
		this.preencherComboTipoMudanca(document, request, response);

		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			this.restore(document, request, response);

		}else{
			// this.restaurarAnexos(request, requisicaoMudancaDto);
		}
		
		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			request.setAttribute("idRequisicaoMudanca", requisicaoMudancaDto.getIdRequisicaoMudanca());
		}
		
		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null && requisicaoMudancaDto.getIdGrupoComite() != null) {
			document.getElementById("idGrupoComite").setDisabled(true);
		}
		
		//if(requisicaoMudancaDto.getFase() != null && !requisicaoMudancaDto.getFase().equalsIgnoreCase("Proposta")){
			document.executeScript("$('#div_ehProposta').hide();");
		//}
		if(requisicaoMudancaDto != null && requisicaoMudancaDto.getIdTarefa() != null ){
			carregaInformacoesComplementares(document, request, requisicaoMudancaDto);
		}
		
		//carregar o grupo de atividade periódica para agendamento
		HTMLForm form = document.getForm("form");
		//form.clear();
		
		if(requisicaoMudancaDto != null && requisicaoMudancaDto.getIdGrupoAtvPeriodica() != null){
			((HTMLSelect) document.getSelectById("idGrupoAtvPeriodica")).removeAllOptions();
			GrupoAtvPeriodicaService grupoService2 = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
	
			ArrayList<GrupoAtvPeriodicaDTO> grupos = (ArrayList) grupoService2.list();
			if (grupos != null) {
				for (GrupoAtvPeriodicaDTO grupo : grupos) {		
					if(requisicaoMudancaDto.getIdGrupoAtvPeriodica().equals(grupo.getIdGrupoAtvPeriodica()))
						((HTMLSelect) document.getSelectById("idGrupoAtvPeriodica")).addOption(grupo.getIdGrupoAtvPeriodica().toString(), grupo.getNomeGrupoAtvPeriodica());
				}
			}
		} else {
		
			AtividadePeriodicaDTO atividadePeriodicaDTO = new AtividadePeriodicaDTO();
			if(requisicaoMudancaDto != null){
				atividadePeriodicaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			}
			HTMLSelect idGrupoAtvPeriodica = (HTMLSelect) document.getSelectById("idGrupoAtvPeriodica");
			idGrupoAtvPeriodica.removeAllOptions();
			GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
			Collection colGrupos = grupoAtvPeriodicaService.listGrupoAtividadePeriodicaAtiva();
			idGrupoAtvPeriodica.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			idGrupoAtvPeriodica.addOptions(colGrupos, "idGrupoAtvPeriodica", "nomeGrupoAtvPeriodica", null);	
		
			form.setValues(atividadePeriodicaDTO);
		}
		
		requisicaoMudancaDto = null;
		// FIM_LOAD
	}

	private boolean isContratoInList(Integer idContrato, Collection colContratosColab) {
		if (colContratosColab != null) {
			for (Iterator it = colContratosColab.iterator(); it.hasNext();) {
				ContratosGruposDTO contratosGruposDTO = (ContratosGruposDTO) it.next();
				if (contratosGruposDTO.getIdContrato().intValue() == idContrato.intValue()) {
					return true;
				}
			}
		}
		return false;
	}

	public void verificaGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
		if (requisicaoMudancaDto.getIdContrato() == null || requisicaoMudancaDto.getIdContrato().intValue() == 0) {
			requisicaoMudancaDto.setIdContrato(contratoDtoAux.getIdContrato());
		}
		String COLABORADORES_VINC_CONTRATOS = ParametroUtil.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");
		if (COLABORADORES_VINC_CONTRATOS == null) {
			COLABORADORES_VINC_CONTRATOS = "N";
		}
		if (COLABORADORES_VINC_CONTRATOS.equalsIgnoreCase("S")) {
			HTMLSelect idGrupoAtual = (HTMLSelect) document.getSelectById("idGrupoAtual");
			idGrupoAtual.removeAllOptions();
			idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
			Collection colGrupos = grupoSegurancaService.listGruposServiceDeskByIdContrato(requisicaoMudancaDto.getIdContrato());
			if (colGrupos != null)
				idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);
		}

		verificaGrupoExecutorInterno(document, requisicaoMudancaDto);

		requisicaoMudancaDto = null;
	}

	public void verificaGrupoExecutorInterno(DocumentHTML document, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() == null || requisicaoMudancaDto.getIdContrato() == null)
			return;

		RequisicaoMudancaService servicoContratoService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(requisicaoMudancaDto.getIdContrato(), requisicaoMudancaDto.getIdRequisicaoMudanca());
		if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null)
			document.getElementById("idGrupoAtual").setValue("" + servicoContratoDto.getIdGrupoExecutor());
		else
			document.getElementById("idGrupoAtual").setValue("");
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		this.setRequisicaoMudancaDto((RequisicaoMudancaDTO) document.getBean());
		
		document.executeScript("$('#abas').show()");
		document.executeScript("$('#requisicaMudancaStatus').show()");

		Integer idTarefa = this.getRequisicaoMudancaDto().getIdTarefa();
		String acaoFluxo = this.getRequisicaoMudancaDto().getAcaoFluxo();
		String escalar = this.getRequisicaoMudancaDto().getEscalar();
		String alterarSituacao = this.getRequisicaoMudancaDto().getAlterarSituacao();
		String fase = this.getRequisicaoMudancaDto().getFase();
		String editar = requisicaoMudancaDto.getEditar();
		
		this.setRequisicaoMudancaDto(this.getRequisicaoMudancaService(request).restoreAll(requisicaoMudancaDto.getIdRequisicaoMudanca()));
		
		if(fase == null){
			fase = this.getRequisicaoMudancaDto().getFase();
		}
		
		//if(this.getRequisicaoMudancaDto().getEhPropostaAux().equalsIgnoreCase("S")){
			document.getElementById("ehPropostaAux").setDisabled(true);
		//}
		
		if (fase != null) {
			if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
				if (fase.equalsIgnoreCase("Proposta")) {
					document.getElementById("abaRelacionarAprovacoesMudanca")
							.setVisible(false);
				}

				if (fase.equalsIgnoreCase("Proposta")) {
					document.getElementById("abaRelacionarAprovacoesProposta").setVisible(true);
					document.getElementById("relacionarAprovacoesProposta").setVisible(true);
				} else{
					document.getElementById("abaRelacionarAprovacoesProposta").setVisible(false);
					document.getElementById("relacionarAprovacoesProposta").setVisible(false);
				}

				if (!fase.equalsIgnoreCase("Proposta")) {
					document.getElementById("labelEhProposta")
							.setVisible(false);
				}
			}
		}
		atribuirNomeProprietarioESolicitanteParaRequisicaoDto(this.getRequisicaoMudancaDto());
		atualizaInformacoesRelacionamentos(document,request,response);

		this.restoreInformacoesContato(this.getRequisicaoMudancaDto(), document, request, response);
		this.restoreComboUnidade(this.getRequisicaoMudancaDto(), document, request, response);
		
		//this.restoreGrupoAtvPeriodica(this.getRequisicaoMudancaDto(), document, request, response);
		
		this.restoreComboLocalidade(this.getRequisicaoMudancaDto(), document, request, response);

		this.preencherComboTipoMudanca(document, request, response);

		this.getRequisicaoMudancaDto().setIdTarefa(idTarefa);
		this.getRequisicaoMudancaDto().setAcaoFluxo(acaoFluxo);
		this.getRequisicaoMudancaDto().setEscalar(escalar);
		this.getRequisicaoMudancaDto().setAlterarSituacao(alterarSituacao);
		this.getRequisicaoMudancaDto().setFase(fase);
		
		HTMLForm form = document.getForm("form");
		form.clear();
		if(this.getRequisicaoMudancaDto().getIdContrato() !=null){
			document.getSelectById("idContrato").setDisabled(true);
		}
		
		if(this.getRequisicaoMudancaDto().getIdSolicitante() !=null){
			document.getSelectById("addSolicitante").setDisabled(true);
		}
		
		if(this.getRequisicaoMudancaDto().getIdTipoMudanca() !=null){
			document.getSelectById("idTipoMudanca").setDisabled(true);
		}
		
		if(this.getRequisicaoMudancaDto().getIdGrupoAtual() !=null){
			document.getSelectById("idGrupoAtual").setDisabled(true);
		}
		
		if(this.getRequisicaoMudancaDto().getIdContatoRequisicaoMudanca() !=null){
			document.getSelectById("contato").setDisabled(true);
		}
		if(this.getRequisicaoMudancaDto().getIdGrupoComite()!=null){
			document.getSelectById("idGrupoComite").setDisabled(true);
		}
		
		this.restaurarAnexos(request, requisicaoMudancaDto);
		this.restaurarAnexosPlanoDeReversao(request, requisicaoMudancaDto);
		
		if (this.listInfoRegExecucaoRequisicaoMudanca(this.getRequisicaoMudancaDto(), request) != null) {
			document.getElementById("tblOcorrencias").setInnerHTML(listInfoRegExecucaoRequisicaoMudanca(requisicaoMudancaDto, request));
		}

		this.montarTabelaAprovacoesProposta(document, request, response, this.getRequisicaoMudancaDto());
		this.montarTabelaAprovacoesMudanca(document, request, response, this.getRequisicaoMudancaDto());
		this.quantidadeAprovacaoMudancaPorVotoAprovada(document, request, response, requisicaoMudancaDto);
		this.quantidadeAprovacaoMudancaPorVotoRejeitada(document, request, response, requisicaoMudancaDto);
		this.quantidadeAprovacaoPropostaPorVotoAprovada(document, request, response, requisicaoMudancaDto);
		this.quantidadeAprovacaoPropostaPorVotoRejeitada(document, request, response, requisicaoMudancaDto);

		boolean bAlterarSituacao = requisicaoMudancaDto.getAlterarSituacao() != null && requisicaoMudancaDto.getAlterarSituacao().equalsIgnoreCase("S");
		/*if (!bAlterarSituacao) {
			document.executeScript("document.form.status[0].disabled = true;");
			document.executeScript("document.form.status[1].disabled = true;");
			document.executeScript("document.form.status[2].disabled = true;");
			document.executeScript("document.form.status[3].disabled = true;");
		}*/
		//restaurar-anexos
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		//Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
		request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", colAnexosUploadDTO);
		//fim-restaurar-anexos

		//Responsável
		HTMLTable tblResponsavel = document.getTableById("tblResponsavel");
		tblResponsavel.deleteAllRows();

		RequisicaoMudancaResponsavelService mudancaResponsavelService = (RequisicaoMudancaResponsavelService) ServiceLocator.getInstance().getService(RequisicaoMudancaResponsavelService.class, WebUtil.getUsuarioSistema(request));
		Collection<RequisicaoMudancaResponsavelDTO> responsavel = mudancaResponsavelService.findByIdMudancaEDataFim(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca());
		if(editar != null && editar.equalsIgnoreCase("N")){
			tblResponsavel.addRowsByCollection(responsavel, new String[] { "", "idResponsavel", "nomeResponsavel", "nomeCargo", "telResponsavel", "emailResponsavel", "papelResponsavel"}, null, "", null, null, null);
		} else{
			tblResponsavel.addRowsByCollection(responsavel, new String[] { "", "idResponsavel", "nomeResponsavel", "nomeCargo", "telResponsavel", "emailResponsavel", "papelResponsavel"}, null, "", new String[] { "gerarImgDelResponsavel" }, null, null);
		}

		form.setValues(this.getRequisicaoMudancaDto());
		form.setValueText("dataHoraInicioAgendada", null, this.getRequisicaoMudancaDto().getDataInicioStr());
		form.setValueText("dataHoraTerminoAgendada", null, this.getRequisicaoMudancaDto().getDataTerminoStr());
		form.setValueText("dataHoraConclusao", null, this.getRequisicaoMudancaDto().getDataConclusaoStr());
		if(this.getRequisicaoMudancaDto().getNomeCategoriaMudanca() != null && !this.getRequisicaoMudancaDto().getNomeCategoriaMudanca().equalsIgnoreCase("")){
			document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + false + ");");
			document.executeScript("$('#div_categoria').show();");	
		}
		String statusSetado = "<input type='radio' id='status' name='status' value='"+this.getRequisicaoMudancaDto().getStatus()+"' checked='checked' />"+this.getRequisicaoMudancaDto().getStatus()+"";
		document.getElementById("statusSetado").setInnerHTML(statusSetado);
		document.executeScript("restaurar()");
		
		if (editar == null || editar.equalsIgnoreCase("")) {	
			this.getRequisicaoMudancaDto().setEditar("S");
		}else if (editar.equalsIgnoreCase("N")) {
			document.executeScript("$('#divBarraFerramentas').hide()");	
			document.executeScript("$('#divBotoes').hide()");	
			document.getForm("form").lockForm();		
		}
		
		//Criada para mostrar a fase da requisição.
		String faseMudancaRequisicao = "";
		String sePropostaAprovada = "";
		String seRequisicaoAprovada = "";
		if (requisicaoMudancaDto != null && requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			RequisicaoMudancaDao requisicaoMudancaDao = new RequisicaoMudancaDao();
			TransactionControler tc = new TransactionControlerImpl(requisicaoMudancaDao.getAliasDB());
			RequisicaoMudancaService servicoContratoService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
			 if(requisicaoMudancaDto.getFase() != null){
				 if (requisicaoMudancaDto.getFase().equals("Proposta")) {
					sePropostaAprovada = servicoContratoService.verificaAprovacaoProposta(requisicaoMudancaDto,tc);
					if(sePropostaAprovada.equals("reprovado")){
						faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPropostaReprovada");						
					} else if(sePropostaAprovada.equals("aprovado")){
						faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPropostaAprovada");
					} else{
						faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPropostaAguardando");
					}
				 }else if(requisicaoMudancaDto.getFase().equals("Aprovacao")){
					seRequisicaoAprovada = servicoContratoService.verificaAprovacaoMudanca(requisicaoMudancaDto,tc);
					if(seRequisicaoAprovada.equals("reprovado")){
						faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoReprovada");						
					} else if(seRequisicaoAprovada.equals("aprovado")){
						faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoAprovada");
					} else{
						faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoAguardando");
					}
				 }else if(requisicaoMudancaDto.getFase().equals("Planejamento")){
					faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoPlanejamento");
				 }else if(requisicaoMudancaDto.getFase().equals("Execucao")){
					faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoExecucao");
				 }else if(requisicaoMudancaDto.getFase().equals("Avaliacao")){
					faseMudancaRequisicao = UtilI18N.internacionaliza(request, "requisicaoMudanca.requisicaoExecucao");
				 }
				
				 if (faseMudancaRequisicao != null && faseMudancaRequisicao != "") {
					request.setAttribute("faseMudancaRequisicao", faseMudancaRequisicao);
				 }
			 }
		}
		
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		Collection<GrupoDTO> lstGrupos = grupoService.getGruposByEmpregado(usuario.getIdEmpregado());

		mostraHistoricoMudanca(document, request, response, requisicaoMudancaDto);
		
		if (lstGrupos != null) {
			for (GrupoDTO g : lstGrupos) {
				if(this.getRequisicaoMudancaService(request).verificaPermissaoGrupoCancelar(this.getRequisicaoMudancaDto().getIdTipoMudanca(), g.getIdGrupo())){
					document.executeScript("$('#statusCancelado').show()");
					break;
				}
			}
		}

		
	}

	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	private void inicializarComboLocalidade(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	/**
	 * Preenche a combo Localidade.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboLocalidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

		LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);

		LocalidadeDTO localidadeDto = new LocalidadeDTO();

		Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;

		Collection<LocalidadeDTO> listaIdlocalidade = null;

		String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

		HTMLSelect comboLocalidade = (HTMLSelect) document.getSelectById("idLocalidade");
		comboLocalidade.removeAllOptions();
		if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
			if (requisicaoMudancaDto.getIdUnidade() != null) {
				listaIdlocalidadePorUnidade = (ArrayList) localidadeUnidadeService.listaIdLocalidades(requisicaoMudancaDto.getIdUnidade());
			}
			if (listaIdlocalidadePorUnidade != null) {
				inicializarComboLocalidade(comboLocalidade, request);
				for (LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
					localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
					localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
					comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade().toString()));
				}

			}
		} else {
			listaIdlocalidade = (ArrayList) localidadeService.listLocalidade();
			if (listaIdlocalidade != null) {
				inicializarComboLocalidade(comboLocalidade, request);
				for (LocalidadeDTO localidadeDTO : listaIdlocalidade) {
					localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
					localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
					comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade().toString()));
				}
			}

		}
		// requisicaoMudancaDto = null;
	}	

	/**
	 * CarregarColaborador
	 */
	public void restoreColaboradorSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		RequisicaoMudancaService requisicaoService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		if (requisicaoMudancaDto.getIdSolicitante() != null) {
			empregadoDto.setIdEmpregado(requisicaoMudancaDto.getIdSolicitante());
			empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

			requisicaoMudancaDto.setNomeSolicitante(empregadoDto.getNome());
			requisicaoMudancaDto.setNomeContato(empregadoDto.getNome());
			requisicaoMudancaDto.setTelefoneContato(empregadoDto.getTelefone());
			requisicaoMudancaDto.setRamal(empregadoDto.getRamal());
			requisicaoMudancaDto.setEmailSolicitante(empregadoDto.getEmail().trim());
			requisicaoMudancaDto.setObservacao(empregadoDto.getObservacoes());
			requisicaoMudancaDto.setIdUnidade(empregadoDto.getIdUnidade());
			this.preencherComboLocalidade(document, request, response);
		}

		document.executeScript("$('#POPUP_SOLICITANTE').dialog('close')");

		HTMLForm form = document.getForm("form");
		// form.clear();
		form.setValues(requisicaoMudancaDto);
		document.executeScript("fecharPopup(\"#POPUP_EMPREGADO\")");

		requisicaoMudancaDto = null;
	}

	/**
	 * CarregarContatoRequisicao
	 */
	public void restoreInformacoesContato(RequisicaoMudancaDTO requisicaoMudancaDto, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ContatoRequisicaoMudancaService contatoRequisicaoMudancaService = (ContatoRequisicaoMudancaService) ServiceLocator.getInstance().getService(ContatoRequisicaoMudancaService.class, null);

		ContatoRequisicaoMudancaDTO contatoRequisicaoMudancaDto = new ContatoRequisicaoMudancaDTO();
		if (requisicaoMudancaDto.getIdContatoRequisicaoMudanca() != null) {
			contatoRequisicaoMudancaDto.setIdContatoRequisicaoMudanca(requisicaoMudancaDto.getIdContatoRequisicaoMudanca());
			contatoRequisicaoMudancaDto = (ContatoRequisicaoMudancaDTO) contatoRequisicaoMudancaService.restore(contatoRequisicaoMudancaDto);
		}
		if (contatoRequisicaoMudancaDto != null) {
			requisicaoMudancaDto.setNomeContato(contatoRequisicaoMudancaDto.getNomecontato());
			requisicaoMudancaDto.setTelefoneContato(contatoRequisicaoMudancaDto.getTelefonecontato());
			requisicaoMudancaDto.setRamal(contatoRequisicaoMudancaDto.getRamal());
			requisicaoMudancaDto.setEmailSolicitante(contatoRequisicaoMudancaDto.getEmailcontato().trim());
			requisicaoMudancaDto.setObservacao(contatoRequisicaoMudancaDto.getObservacao());
			requisicaoMudancaDto.setIdLocalidade(contatoRequisicaoMudancaDto.getIdLocalidade());
		}
	}

	public void restoreComboUnidade(RequisicaoMudancaDTO requisicaoMudancaDto, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null && requisicaoMudancaDto.getIdRequisicaoMudanca().intValue() > 0) {
			String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");

			requisicaoMudancaDto = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDto);

			if (requisicaoMudancaDto.getIdContrato() == null || requisicaoMudancaDto.getIdContrato().intValue() == 0) {
				requisicaoMudancaDto.setIdContrato(contratoDtoAux.getIdContrato());
			}
			if (requisicaoMudancaDto.getIdUnidade() != null) {
				UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
				HTMLSelect comboUnidade = (HTMLSelect) document.getSelectById("idUnidade");
				inicializarCombo(comboUnidade, request);
				if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
					Integer idContrato = requisicaoMudancaDto.getIdContrato();
					ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
					if (unidades != null) {
						for (UnidadeDTO unidade : unidades) {
							if (unidade.getDataFim() == null) {
								comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
							}

						}
					}
				} else {
					ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
					if (unidades != null) {
						for (UnidadeDTO unidade : unidades) {
							if (unidade.getDataFim() == null) {
								comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
							}
						}
					}
				}
			}
		}
	}
	
	public void restoreComboLocalidade(RequisicaoMudancaDTO requisicaoMudancaDto, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null && requisicaoMudancaDto.getIdRequisicaoMudanca().intValue() > 0) {

			String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

			if (requisicaoMudancaDto.getIdContrato() == null || requisicaoMudancaDto.getIdContrato().intValue() == 0) {
				requisicaoMudancaDto.setIdContrato(contratoDtoAux.getIdContrato());
			}

			if (requisicaoMudancaDto.getIdLocalidade() != null) {

				LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);
				LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);
				LocalidadeDTO localidadeDto = new LocalidadeDTO();
				Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;
				Collection<LocalidadeDTO> listaIdlocalidade = null;

				HTMLSelect comboLocalidade = (HTMLSelect) document.getSelectById("idLocalidade");
				comboLocalidade.removeAllOptions();
				if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
					if (requisicaoMudancaDto.getIdUnidade() != null) {
						listaIdlocalidadePorUnidade = (ArrayList) localidadeUnidadeService.listaIdLocalidades(requisicaoMudancaDto.getIdUnidade());
					}
					if (listaIdlocalidadePorUnidade != null) {
						inicializarComboLocalidade(comboLocalidade, request);
						for (LocalidadeUnidadeDTO localidadeUnidadeDto : listaIdlocalidadePorUnidade) {
							localidadeDto.setIdLocalidade(localidadeUnidadeDto.getIdLocalidade());
							localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
							comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade()));
						}

					}
				} else {
					listaIdlocalidade = (ArrayList) localidadeService.listLocalidade();
					if (listaIdlocalidade != null) {
						inicializarComboLocalidade(comboLocalidade, request);
						for (LocalidadeDTO localidadeDTO : listaIdlocalidade) {
							localidadeDto.setIdLocalidade(localidadeDTO.getIdLocalidade());
							localidadeDto = (LocalidadeDTO) localidadeService.restore(localidadeDto);
							comboLocalidade.addOption(localidadeDto.getIdLocalidade().toString(), StringEscapeUtils.escapeJavaScript(localidadeDto.getNomeLocalidade()));
						}
					}
				}
			}
		}
	}

	public void carregaUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String validarComboUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.UNIDADE_VINC_CONTRATOS, "N");
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null && requisicaoMudancaDto.getIdRequisicaoMudanca().intValue() > 0) {

			RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

			ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
			contratoDtoAux.setIdContrato(requisicaoMudancaDto.getIdContrato());
			
			requisicaoMudancaDto = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDto);

			ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
			servicoContratoDTO.setIdServicoContrato(requisicaoMudancaDto.getIdContrato());
			if (requisicaoMudancaDto.getIdContrato() != null) {
				servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
			} else {
				servicoContratoDTO = null;
			}
			if (servicoContratoDTO != null) {
				requisicaoMudancaDto.setIdRequisicaoMudanca(servicoContratoDTO.getIdServico());
				requisicaoMudancaDto.setIdContrato(servicoContratoDTO.getIdContrato());
			}

		}

		if (requisicaoMudancaDto.getIdContrato() == null || requisicaoMudancaDto.getIdContrato().intValue() ==0) {
			requisicaoMudancaDto.setIdContrato(contratoDtoAux.getIdContrato());
		}

		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
		HTMLSelect comboUnidade = (HTMLSelect) document.getSelectById("idUnidade");
		inicializarCombo(comboUnidade, request);
		if (validarComboUnidade.trim().equalsIgnoreCase("S")) {
			Integer idContrato = requisicaoMudancaDto.getIdContrato();
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquiaMultiContratos(idContrato);
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
					}

				}
			}
		} else {
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades) {
					if (unidade.getDataFim() == null) {
						comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel().toString()));
					}
				}
			}
		}

		requisicaoMudancaDto = null;
	}

	/**
	 * Popula combo Urgencia.
	 * 
	 * @param document
	 * @throws Exception
	 */

	private void carregarComboUrgencia(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect urgencia = (HTMLSelect) document.getSelectById("nivelUrgencia");
		urgencia.removeAllOptions();
		urgencia.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
		urgencia.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
		urgencia.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
	}

	/**
	 * Popula combo Impacto.
	 * 
	 * @param document
	 * @throws Exception
	 */
	private void carregarComboImpacto(DocumentHTML document, HttpServletRequest request) throws Exception {
		HTMLSelect impacto = (HTMLSelect) document.getSelectById("nivelImpacto");
		impacto.removeAllOptions();
		impacto.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
		impacto.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
		impacto.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
	}

	private void inicializaCombo(HTMLSelect componenteCombo) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", "-- Selecione --");
	}

	/**
	 * Centraliza atualização de informações dos objetos que se relacionam com a mudança.
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void atualizaInformacoesRelacionamentos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		// informações dos ics relacionados
		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listaICsRelacionados = getReqMudancaICAction().listItensRelacionadosRequisicaoMudanca(requisicaoMudancaDto);
		if(listaICsRelacionados!=null && listaICsRelacionados.size()>0){
			requisicaoMudancaDto.setItensConfiguracaoRelacionadosSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaICsRelacionados));
		}
	
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		// informações dos servicos relacionados
		ArrayList<RequisicaoMudancaServicoDTO> listaServicosRelacionados = getReqMudancaServicoAction().listItensRelacionadosRequisicaoMudanca(requisicaoMudancaDto);
		if(listaServicosRelacionados!=null &&  listaServicosRelacionados.size() > 0 ){
			requisicaoMudancaDto.setServicosRelacionadosSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaServicosRelacionados));
		}
		
		//informações dos servicos relacionados
		LiberacaoMudancaService liberacaoMudancaService = (LiberacaoMudancaService) ServiceLocator.getInstance().getService(LiberacaoMudancaService.class, null);
		//ArrayList<RequisicaoMudancaLiberacaoDTO> liberacaoMudanca = (ArrayList<RequisicaoMudancaLiberacaoDTO>) liberacaoMudancaService.findByIdRequisicaoMudanca(this.requisicaoMudancaDto.getIdRequisicaoMudanca());
		ArrayList<LiberacaoMudancaDTO> liberacaoMudanca = (ArrayList<LiberacaoMudancaDTO>) liberacaoMudancaService.findByIdRequisicaoMudanca(this.requisicaoMudancaDto.getIdLiberacao(),this.requisicaoMudancaDto.getIdRequisicaoMudanca());
		if(liberacaoMudanca!=null &&  liberacaoMudanca.size() > 0 ){	
			requisicaoMudancaDto.setLiberacoesRelacionadosSerializado(br.com.citframework.util.WebUtil.serializeObjects(liberacaoMudanca));
		}
		
		//geber.costa
		//this.criaTabelaLiberacao(document,request,response);

		ArrayList<RequisicaoMudancaDTO> listaSolicitacaoServico = (ArrayList<RequisicaoMudancaDTO>) requisicaoMudancaService.listMudancaByIdSolicitacao(requisicaoMudancaDto);
		if (listaSolicitacaoServico != null && listaSolicitacaoServico.size() > 0) {
			requisicaoMudancaDto.setSolicitacaoServicoSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaSolicitacaoServico));
		}
		
		ProblemaMudancaService problemaMudancaService = (ProblemaMudancaService) ServiceLocator.getInstance().getService(ProblemaMudancaService.class, null);
		ArrayList<ProblemaMudancaDTO> listaProblemaMudanca = (ArrayList<ProblemaMudancaDTO>) problemaMudancaService.findByIdRequisicaoMudanca(this.requisicaoMudancaDto.getIdRequisicaoMudanca());
		if (listaProblemaMudanca != null && listaProblemaMudanca.size() > 0) {
			requisicaoMudancaDto.setProblemaSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaProblemaMudanca));
		}	
		
		RequisicaoMudancaRiscoService requisicaoMudancaRiscoService= (RequisicaoMudancaRiscoService) ServiceLocator.getInstance().getService(RequisicaoMudancaRiscoService.class, null);
		ArrayList<RequisicaoMudancaRiscoDTO> listaRiscos = (ArrayList<RequisicaoMudancaRiscoDTO>) requisicaoMudancaRiscoService.findByIdRequisicaoMudanca(this.requisicaoMudancaDto.getIdRequisicaoMudanca());
		if (listaRiscos != null && listaRiscos.size() > 0) {
			requisicaoMudancaDto.setRiscoSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaRiscos));
		}
		
		GrupoRequisicaoMudancaService grupoRequisicaoMudancaService = (GrupoRequisicaoMudancaService) ServiceLocator.getInstance().getService(GrupoRequisicaoMudancaService.class, null);
		ArrayList<GrupoRequisicaoMudancaDTO> listaGrupoRequisicaoMudanca = (ArrayList<GrupoRequisicaoMudancaDTO>) grupoRequisicaoMudancaService.findByIdRequisicaoMudanca(this.requisicaoMudancaDto.getIdRequisicaoMudanca());
		if (listaGrupoRequisicaoMudanca != null && listaGrupoRequisicaoMudanca.size() > 0) {
			requisicaoMudancaDto.setGrupoMudancaSerializado(br.com.citframework.util.WebUtil.serializeObjects(listaGrupoRequisicaoMudanca));
		}
		
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.executeScript("exibeJanelaAguarde()");
		
		UsuarioDTO usuario = (UsuarioDTO) request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		requestGlobal = request;
		documentGlobal = document;
		
		this.setRequisicaoMudancaDto((RequisicaoMudancaDTO) document.getBean());
		this.getRequisicaoMudancaDto().setUsuarioDto(usuario);
		this.getRequisicaoMudancaDto().setDataHoraTermino(this.getRequisicaoMudancaDto().getDataHoraTerminoAgendada());
		this.getRequisicaoMudancaDto().setEnviaEmailCriacao("S");
		this.getRequisicaoMudancaDto().setEnviaEmailAcoes("S");
		this.getRequisicaoMudancaDto().setEnviaEmailFinalizacao("S");
		this.getRequisicaoMudancaDto().setEnviaEmailGrupoComite("S");
		
		this.getRequisicaoMudancaDto().setTitulo(this.getRequisicaoMudancaDto().getTitulo());
		this.getRequisicaoMudancaDto().setDescricao(this.getRequisicaoMudancaDto().getDescricao());
		this.getRequisicaoMudancaDto().setObservacao(this.getRequisicaoMudancaDto().getObservacao());
		this.getRequisicaoMudancaDto().setTipo(this.getRequisicaoMudancaDto().getTipo());
		
		//geber.costa
		this.getRequisicaoMudancaDto().setIdRequisicaoMudanca(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca());
		this.getRequisicaoMudancaDto().setStatus(this.getRequisicaoMudancaDto().getStatus());
		this.getRequisicaoMudancaDto().setSituacaoLiberacao(this.getRequisicaoMudancaDto().getSituacaoLiberacao());
		
		this.getRequisicaoMudancaDto().setIdGrupoAtvPeriodica(this.getRequisicaoMudancaDto().getIdGrupoAtvPeriodica());

		try {
			/* Inicio Deserialização */
			ArrayList<SolicitacaoServicoDTO> listIdSolicitacaoServico = (ArrayList<SolicitacaoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(SolicitacaoServicoDTO.class, "solicitacaoServicoSerializado", request);
			this.getRequisicaoMudancaDto().setListIdSolicitacaoServico(listIdSolicitacaoServico);

			ArrayList<AprovacaoMudancaDTO> listAprovacoMudanca = (ArrayList<AprovacaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(AprovacaoMudancaDTO.class,
					"aprovacaoMudancaServicoSerializado", request);
			
			ArrayList<AprovacaoPropostaDTO> listAprovacoProposta;
			
			if (this.getRequisicaoMudancaDto().getFase().equalsIgnoreCase("Proposta")) {
				listAprovacoProposta = (ArrayList<AprovacaoPropostaDTO>) br.com.citframework.util.WebUtil
						.deserializeCollectionFromRequest(
								AprovacaoPropostaDTO.class,
								"aprovacaoPropostaServicoSerializado", request);
				if (listAprovacoProposta != null) {
					setaDataHoraVotacoesProposta(listAprovacoProposta, usuario, request);
				}
				
				this.getRequisicaoMudancaDto().setListAprovacaoPropostaDTO(listAprovacoProposta);
			}
			
			if(listAprovacoMudanca != null){
				setaDataHoraVotacoesMudanca(listAprovacoMudanca, usuario, request);
			}
			
			this.getRequisicaoMudancaDto().setListAprovacaoMudancaDTO(listAprovacoMudanca);

			ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listRequisicaoMudancaItemConfiguracaoDTO = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoMudancaItemConfiguracaoDTO.class, "itensConfiguracaoRelacionadosSerializado", request);
			this.getRequisicaoMudancaDto().setListRequisicaoMudancaItemConfiguracaoDTO(listRequisicaoMudancaItemConfiguracaoDTO);

			ArrayList<ProblemaMudancaDTO> listProblemaMudancaDTO = (ArrayList<ProblemaMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProblemaMudancaDTO.class, "problemaSerializado", request);
			this.getRequisicaoMudancaDto().setListProblemaMudancaDTO(listProblemaMudancaDTO);
			
			ArrayList<GrupoRequisicaoMudancaDTO> listGrupoRequisicaoMudancaDTO = (ArrayList<GrupoRequisicaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(GrupoRequisicaoMudancaDTO.class, "grupoMudancaSerializado", request);
			this.getRequisicaoMudancaDto().setListGrupoRequisicaoMudancaDTO(listGrupoRequisicaoMudancaDTO);	
			
			ArrayList<RequisicaoMudancaServicoDTO> listRequisicaoMudancaServicoDTO = (ArrayList<RequisicaoMudancaServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
					RequisicaoMudancaServicoDTO.class, "servicosRelacionadosSerializado", request);
			this.getRequisicaoMudancaDto().setListRequisicaoMudancaServicoDTO(listRequisicaoMudancaServicoDTO);
			
			ArrayList<RequisicaoMudancaRiscoDTO> listRiscosDTO = (ArrayList<RequisicaoMudancaRiscoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoMudancaRiscoDTO.class, "riscoSerializado", request);
			this.getRequisicaoMudancaDto().setListRequisicaoMudancaRiscoDTO(listRiscosDTO);
			
			Collection<RequisicaoMudancaResponsavelDTO> colResponsavel = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoMudancaResponsavelDTO.class, "responsavel_serialize", request);
			this.getRequisicaoMudancaDto().setColResponsaveis(colResponsavel);
			
			//geber.costa
			
			ArrayList<LiberacaoMudancaDTO> listLiberacoesDTO = (ArrayList<LiberacaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(LiberacaoMudancaDTO.class, "liberacoesRelacionadosSerializado", request);
			this.getRequisicaoMudancaDto().setListLiberacaoMudancaDTO(listLiberacoesDTO);        

			if (this.getRequisicaoMudancaDto().getIdRequisicaoMudanca() == null || this.getRequisicaoMudancaDto().getIdRequisicaoMudanca() == 0) {
				
				if(this.getRequisicaoMudancaDto().getEhPropostaAux() != null){
					if(this.getRequisicaoMudancaDto().getEhPropostaAux().equalsIgnoreCase("S")){
						this.getRequisicaoMudancaDto().setEhProposta(true);
						if(this.getRequisicaoMudancaDto().getFase() == null || this.getRequisicaoMudancaDto().getFase().equals("") || UtilStrings.stringVazia(this.getRequisicaoMudancaDto().getFase())){
							this.getRequisicaoMudancaDto().setFase("Proposta");
						}
					}
				} else {
					this.getRequisicaoMudancaDto().setEhPropostaAux("N");
					this.getRequisicaoMudancaDto().setEhProposta(false);
				}

				gravarAnexoMudanca(document, request, response, this.getRequisicaoMudancaDto());
				gravarAnexosPlanosDeReversao(document, request, response, this.getRequisicaoMudancaDto());
				
				RequisicaoMudancaService r = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
				this.setRequisicaoMudancaDto((RequisicaoMudancaDTO) r.create(this.getRequisicaoMudancaDto()));
				
//				Boolean continua = false;
//				if(requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null)
//					continua = salvaGrupoAtvPeriodicaEAgenda(requisicaoMudancaDto, document, request);
//				
//				if(requisicaoMudancaDto.getDataHoraInicioAgendada() == null)
//					continua = true;
//				
//				if(continua == false)
//					throw new LogicException(UtilI18N.internacionaliza(request,"gerenciaservico.agendaratividade.informacoesGrupoAtividade"));
				
				TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
				Collection collTipoMudanca = tipoMudancaService.findByIdTipoMudanca(this.requisicaoMudancaDto.getIdTipoMudanca());
				TipoMudancaDTO tipoMudanca = (TipoMudancaDTO) ((List) collTipoMudanca).get(0);
				
				document.executeScript("escondeJanelaAguarde()");
				
				String comando = "mostraMensagemInsercao('" + UtilI18N.internacionaliza(request, "MSG05") + ".<br>" + UtilI18N.internacionaliza(request, "gerenciamentoMudanca.numerorequisicao") + " <b><u>"
						+ this.getRequisicaoMudancaDto().getIdRequisicaoMudanca() + "</u></b> " + UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>"
						+ UtilI18N.internacionaliza(request, "contrato.tipo")+":" + StringEscapeUtils.escapeJavaScript(tipoMudanca.getNomeTipoMudanca().toString()) + ".<br>";
				comando = comando + "')";
				document.executeScript(comando);
				return;
				
				//document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				requisicaoMudancaDto.setColArquivosUpload(gravarAnexoMudanca(document, request, response, this.getRequisicaoMudancaDto()));
				requisicaoMudancaDto.setColUploadPlanoDeReversaoGED(gravarAnexosPlanosDeReversao(document, request, response, this.getRequisicaoMudancaDto()));
				RequisicaoMudancaDTO requisicaoMudancaAuxDto =  new RequisicaoMudancaDTO();
				if(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca()!=null){
					requisicaoMudancaAuxDto.setIdRequisicaoMudanca(this.getRequisicaoMudancaDto().getIdRequisicaoMudanca());
					requisicaoMudancaAuxDto = (RequisicaoMudancaDTO) this.getRequisicaoMudancaService(request).restore(requisicaoMudancaAuxDto);
					if(requisicaoMudancaAuxDto.getIdContrato()!=null){
						this.getRequisicaoMudancaDto().setIdContrato(requisicaoMudancaAuxDto.getIdContrato());
						this.getRequisicaoMudancaDto().setEhPropostaAux((requisicaoMudancaAuxDto.getEhPropostaAux()));
					}
					if(requisicaoMudancaAuxDto.getIdGrupoComite()!=null){
						this.getRequisicaoMudancaDto().setIdGrupoComite(requisicaoMudancaAuxDto.getIdGrupoComite());
					}
				}
				
				RequisicaoMudancaService r = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
				boolean planoDeReversaoInformado = r.planoDeReversaoInformado(this.getRequisicaoMudancaDto(), request);
				if (planoDeReversaoInformado == true) {
					r.update(this.getRequisicaoMudancaDto(), request);
				}else{
					document.executeScript("abrirAbaPlanoDeReversao();");
					return;
				}
				
//				Boolean continua = false;
//				if(requisicaoMudancaDto.getDataHoraInicioAgendada() != null && requisicaoMudancaDto.getDataHoraTerminoAgendada() != null)
//					continua = salvaGrupoAtvPeriodicaEAgenda(requisicaoMudancaDto, document, request);
//				
//				if(requisicaoMudancaDto.getDataHoraInicioAgendada() == null)
//					continua = true;
//				
//				if(continua == false)
//					throw new LogicException(UtilI18N.internacionaliza(request,"gerenciaservico.agendaratividade.informacoesGrupoAtividade"));
				
				document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}

		} catch (Exception e) {
			System.out.println("Falha na transação.");
			throw new ServiceException(e);
		}
		
		
		document.executeScript("escondeJanelaAguarde()");
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("fechar();");
		
	}

	public void saveBaseline(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
		HistoricoMudancaService historicoMudancaService = (HistoricoMudancaService) ServiceLocator.getInstance().getService(HistoricoMudancaService.class, null);
		HistoricoMudancaDao historicoMudancaDao = new HistoricoMudancaDao();
		List<HistoricoMudancaDTO> set = (ArrayList<HistoricoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(HistoricoMudancaDTO.class, "baselinesSerializadas" , request);
		if(set!=null) {
			for (HistoricoMudancaDTO historicoMudancaDTO : set) {
				HistoricoMudancaDTO novo = new HistoricoMudancaDTO();
				novo.setBaseLine("SIM");
				if(historicoMudancaDTO.getIdHistoricoMudanca() != null) {
					novo.setIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
					//novo = (HistoricoMudancaDTO) historicoMudancaService.restore(novo);
					novo = (HistoricoMudancaDTO) historicoMudancaDao.restore(novo);
					novo.setBaseLine("SIM");
					historicoMudancaDao.update(novo);
				}
			}
			document.alert(UtilI18N.internacionaliza(request, "itemConfiguracaoTree.baselineGravadasSucesso"));
			load(document, request, response);
		}		
	}

	public static Boolean salvaGrupoAtvPeriodicaEAgenda(RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException, Exception{
		UsuarioDTO usuario = WebUtil.getUsuario(requestGlobal);
		if (usuario == null){
			documentGlobal.alert(UtilI18N.internacionaliza(requestGlobal, "citcorpore.comum.sessaoExpirada"));
			documentGlobal.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + requestGlobal.getContextPath() + "'");
			return false;
		}
		
		AtividadePeriodicaDTO atividadePeriodicaDTO = new AtividadePeriodicaDTO();
		atividadePeriodicaDTO.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		
		atividadePeriodicaDTO.setDuracaoEstimada((int) calculaDuracaoEstimada(requisicaoMudancaDto));
		atividadePeriodicaDTO.setIdGrupoAtvPeriodica(requisicaoMudancaDto.getIdGrupoAtvPeriodica());
		atividadePeriodicaDTO.setDataInicio(transformaDataStringEmDate(requisicaoMudancaDto.getDataInicioStr()));
		atividadePeriodicaDTO.setHoraInicio(requisicaoMudancaDto.getHoraAgendamentoInicial());
		
		if (atividadePeriodicaDTO.getDuracaoEstimada() == null || atividadePeriodicaDTO.getDuracaoEstimada().intValue() == 0){
		    return false;
		} else if (requisicaoMudancaDto.getIdGrupoAtvPeriodica() == null) {
		    return false;
		}
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(requestGlobal));
		GrupoAtvPeriodicaService grupoAtvPeriodicaService = (GrupoAtvPeriodicaService) ServiceLocator.getInstance().getService(GrupoAtvPeriodicaService.class, null);
		//RequisicaoMudancaDTO requisicaoMudancaDto = requisicaoMudancaService.restoreAll(atividadePeriodicaDTO.getIdRequisicaoMudanca());
		String orient = "";
		String ocorr = "";
		if (!ocorr.equalsIgnoreCase("")) ocorr += "\n";
		ocorr += UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.dataagendamento") +" " + UtilDatas.dateToSTR(atividadePeriodicaDTO.getDataInicio());
		if (!ocorr.equalsIgnoreCase("")) ocorr += "\n";
		ocorr += UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.horaagendamento") + " " + atividadePeriodicaDTO.getHoraInicio();
		if (!ocorr.equalsIgnoreCase("")) ocorr += "\n";
		ocorr += UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.duracaoestimada") +" " + atividadePeriodicaDTO.getDuracaoEstimada();
		GrupoAtvPeriodicaDTO grupoAtvPeriodicaDTO = new GrupoAtvPeriodicaDTO();
		grupoAtvPeriodicaDTO.setIdGrupoAtvPeriodica(atividadePeriodicaDTO.getIdGrupoAtvPeriodica());
		grupoAtvPeriodicaDTO = (GrupoAtvPeriodicaDTO) grupoAtvPeriodicaService.restore(grupoAtvPeriodicaDTO);
		if (grupoAtvPeriodicaDTO != null){
		    ocorr += "\n"+UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.grupo") + ": " + grupoAtvPeriodicaDTO.getNomeGrupoAtvPeriodica();
		}
		if (atividadePeriodicaDTO.getOrientacaoTecnica() != null){
		    orient = atividadePeriodicaDTO.getOrientacaoTecnica();
		    ocorr += "\n"+ UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.orientacaotecnica") +": \n" + atividadePeriodicaDTO.getOrientacaoTecnica();
		}
		orient += "\n\n"+ UtilI18N.internacionaliza(requestGlobal, "requisicaoMudanca.requisicaoMudanca") +": \n" + requisicaoMudancaDto.getDescricao();
		
		atividadePeriodicaDTO.setTituloAtividade(UtilI18N.internacionaliza(requestGlobal, "gerenciaservico.agendaratividade.requisicaoMudanca") +" " + atividadePeriodicaDTO.getIdRequisicaoMudanca());
		atividadePeriodicaDTO.setDescricao(requisicaoMudancaDto.getDescricao());
		atividadePeriodicaDTO.setDataCriacao(UtilDatas.getDataAtual());
		atividadePeriodicaDTO.setCriadoPor(usuario.getNomeUsuario());
		atividadePeriodicaDTO.setIdContrato(requisicaoMudancaDto.getIdContrato());
		atividadePeriodicaDTO.setOrientacaoTecnica(orient);
		
		Collection colItens = new ArrayList();
		ProgramacaoAtividadeDTO programacaoAtividadeDTO = new ProgramacaoAtividadeDTO();
		programacaoAtividadeDTO.setTipoAgendamento("U");
		programacaoAtividadeDTO.setDataInicio(atividadePeriodicaDTO.getDataInicio());
		programacaoAtividadeDTO.setHoraInicio(atividadePeriodicaDTO.getHoraInicio());
		programacaoAtividadeDTO.setHoraFim("00:00");
		programacaoAtividadeDTO.setDuracaoEstimada(atividadePeriodicaDTO.getDuracaoEstimada());
		programacaoAtividadeDTO.setRepeticao("N");
		colItens.add(programacaoAtividadeDTO);
		
		AtividadePeriodicaService atividadePeriodicaService = (AtividadePeriodicaService) ServiceLocator.getInstance().getService(AtividadePeriodicaService.class, null);
		atividadePeriodicaDTO.setColItens(colItens);
		//atividadePeriodicaService.create(atividadePeriodicaDTO);
		
		//verifica se já não houve agendamento para essa requisição
		Collection<AtividadePeriodicaDTO> listAtividade = atividadePeriodicaService.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		if(listAtividade != null) {
			//só é possível fazer um agendamento para a mudança, logo a lista deverá vir só com um registro
			//ao atualizar na tela, esse registro único deve ser atualizado
			int idAtvPeriodica = 0;
			idAtvPeriodica = listAtividade.iterator().next().getIdAtividadePeriodica();
			atividadePeriodicaDTO.setIdAtividadePeriodica(idAtvPeriodica);
			atividadePeriodicaService.update(atividadePeriodicaDTO);
		} else {
			atividadePeriodicaService.create(atividadePeriodicaDTO);	
		}
		
		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
		OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoDTO = new OcorrenciaSolicitacaoDTO();
		ocorrenciaSolicitacaoDTO.setIdSolicitacaoServico(atividadePeriodicaDTO.getIdSolicitacaoServico());
		ocorrenciaSolicitacaoDTO.setDataregistro(UtilDatas.getDataAtual());
		ocorrenciaSolicitacaoDTO.setHoraregistro(UtilDatas.formatHoraFormatadaStr(UtilDatas.getHoraAtual()));
		ocorrenciaSolicitacaoDTO.setTempoGasto(0);
		ocorrenciaSolicitacaoDTO.setDescricao(Enumerados.CategoriaOcorrencia.Agendamento.getDescricao());
		ocorrenciaSolicitacaoDTO.setDataInicio(UtilDatas.getDataAtual());
		ocorrenciaSolicitacaoDTO.setDataFim(UtilDatas.getDataAtual());
		ocorrenciaSolicitacaoDTO.setInformacoesContato(UtilI18N.internacionaliza(requestGlobal, "MSG013"));
		ocorrenciaSolicitacaoDTO.setRegistradopor(usuario.getNomeUsuario());
		ocorrenciaSolicitacaoDTO.setOcorrencia(ocorr);
		ocorrenciaSolicitacaoDTO.setOrigem(Enumerados.OrigemOcorrencia.OUTROS.getSigla().toString());
		ocorrenciaSolicitacaoDTO.setCategoria(Enumerados.CategoriaOcorrencia.Agendamento.getSigla());
		ocorrenciaSolicitacaoDTO.setIdItemTrabalho(requisicaoMudancaDto.getIdTarefa());
		ocorrenciaSolicitacaoService.create(ocorrenciaSolicitacaoDTO);
		
		return true;
	}
	
	public static long calculaDuracaoEstimada(RequisicaoMudancaDTO requisicaoMudancaDto){
		long duracao = requisicaoMudancaDto.getDataHoraTerminoAgendada().getTime() - requisicaoMudancaDto.getDataHoraInicioAgendada().getTime();
		long minutos = duracao/(1000*60);
		return minutos;
	}
	
	public static java.sql.Date transformaDataStringEmDate(String dataSemFormatacao) throws ParseException{
		DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");  
		java.sql.Date data = new java.sql.Date(fmt.parse(dataSemFormatacao).getTime()); 
		return data;
		
	}
	
	/**
	 * 
	 * @author Bruno.franco
	 */
	public void setaDataHoraVotacoesMudanca(ArrayList<AprovacaoMudancaDTO> listAprovacoMudanca, UsuarioDTO usuario, HttpServletRequest request){
		
		for (AprovacaoMudancaDTO aprovacaoMudancaDTO : listAprovacoMudanca) {		
			if(UtilStrings.isNotVazio(aprovacaoMudancaDTO.getVoto())){
					if(usuario.getNomeUsuario().equals(aprovacaoMudancaDTO.getNomeEmpregado())){
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());  
					String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());  
					aprovacaoMudancaDTO.setDataHoraVotacao(date);
					}
			} else {
				aprovacaoMudancaDTO.setDataHoraVotacao(UtilI18N.internacionaliza(request, "requisicaoMudanca.aindaNaoVotou"));
			}
		}
	}
	
	public void setaDataHoraVotacoesProposta(ArrayList<AprovacaoPropostaDTO> listAprovacoProposta, UsuarioDTO usuario, HttpServletRequest request){

		for (AprovacaoPropostaDTO aprovacaoPropostaDTO : listAprovacoProposta) {
			if(UtilStrings.isNotVazio(aprovacaoPropostaDTO.getVoto())){
				if(usuario.getNomeUsuario().equals(aprovacaoPropostaDTO.getNomeEmpregado())){
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());  
					String date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(timestamp.getTime());  
					aprovacaoPropostaDTO.setDataHoraVotacao(date);
				}
			} else {
				aprovacaoPropostaDTO.setDataHoraVotacao(UtilI18N.internacionaliza(request, "requisicaoMudanca.aindaNaoVotou"));
			}
		}
	}
	
	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

		/*
		 * if(requisicaoMudancaDto.getIdRequisicaoMudanca() != null && !requisicaoMudancaDto.getIdRequisicaoMudanca().equals("")){
		 * getRequisicaoMudancaService(request).deleteWithTransaction(requisicaoMudancaDto);
		 * 
		 * limparFormularioConsiderandoFCKEditores(document, "form"); document.alert(UtilI18N.internacionaliza(request, "MSG07")); }
		 */
	}

	/**
	 * preencher combo de tipo fluxo
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author geber.costa
	 */

	public void preencherComboTipoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
		HTMLSelect comboTipoMudanca = (HTMLSelect) document.getSelectById("idTipoMudanca");
		ArrayList<TipoMudancaDTO> listTipoMudanca = (ArrayList) tipoMudancaService.getAtivos();

		comboTipoMudanca.removeAllOptions();
		comboTipoMudanca.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (listTipoMudanca != null) {
			for (TipoMudancaDTO tipoMudancaDTO : listTipoMudanca) {
				if (tipoMudancaDTO.getIdTipoMudanca() != null || tipoMudancaDTO.getIdTipoMudanca() > 0) {
					comboTipoMudanca.addOption(tipoMudancaDTO.getIdTipoMudanca().toString(), StringEscapeUtils.escapeJavaScript(tipoMudancaDTO.getNomeTipoMudanca().toString()));
				}
			}
		}
	}
	
	public void tratarCaracterItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) {
		requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
		String descricaoItemConfiguracao = "";
		String descricaoTratada = "";
		try {
			if (requisicaoMudancaDto != null && requisicaoMudancaDto.getHiddenDescricaoItemConfiguracao() != null) {
				descricaoItemConfiguracao = requisicaoMudancaDto.getHiddenDescricaoItemConfiguracao();
				descricaoItemConfiguracao = descricaoItemConfiguracao.replaceAll("\"", "");
				descricaoItemConfiguracao = descricaoItemConfiguracao.replaceAll("\'", "");
				descricaoTratada = StringEscapeUtils.escapeJavaScript(descricaoItemConfiguracao.toString().trim());   
				
				ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
				if (requisicaoMudancaDto != null && requisicaoMudancaDto.getHiddenIdItemConfiguracao() != null && requisicaoMudancaDto.getHiddenIdItemConfiguracao().SIZE > 0) {
					itemConfiguracaoDTO.setIdItemConfiguracao(requisicaoMudancaDto.getHiddenIdItemConfiguracao());
					itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);
					document.getElementById("hiddenIdItemConfiguracao").setValue(itemConfiguracaoDTO.getIdItemConfiguracao().toString());
				}
				
				document.executeScript("atualizarTabela('"+descricaoTratada+"','" + itemConfiguracaoDTO.getIdentificacao() + "')");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	/**
	 * Atualiza as informações de nome de proprietario e nome de solicitante em uma requisicaoMudancaDto, caso haja.
	 * 
	 * @param requisicaoMudancaDto
	 * @throws ServiceException
	 * @throws Exception
	 */
	private void atribuirNomeProprietarioESolicitanteParaRequisicaoDto(RequisicaoMudancaDTO requisicaoMudancaDto) throws ServiceException, Exception {
		if (requisicaoMudancaDto == null) {
			return;
		}

		Integer idProprietario = requisicaoMudancaDto.getIdProprietario();
		Integer idSolicitante = requisicaoMudancaDto.getIdSolicitante();

		if (idProprietario != null && idSolicitante != null) {
			requisicaoMudancaDto.setNomeProprietario(getEmpregadoService().restoreByIdEmpregado(idProprietario).getNome());
			requisicaoMudancaDto.setNomeSolicitante(getEmpregadoService().restoreByIdEmpregado(idSolicitante).getNome());
		}
	}

	/**
	 * @return RequisicaoMudancaService
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	private RequisicaoMudancaService getRequisicaoMudancaService(HttpServletRequest request) throws ServiceException, Exception {
		if (requisicaoMudancaService == null) {
			requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class,
					br.com.centralit.citcorpore.util.WebUtil.getUsuarioSistema(request));
		}
		return requisicaoMudancaService;
	}

	/**
	 * @return EmpregadoService
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	private EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		if (empregadoService == null) {
			empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		}
		return empregadoService;
	}

	private CategoriaMudancaService getCategoriaMudancaService() throws ServiceException, Exception {
		if (categoriaMudancaService == null) {
			categoriaMudancaService = (CategoriaMudancaService) ServiceLocator.getInstance().getService(CategoriaMudancaService.class, null);
		}

		return categoriaMudancaService;
	}

	/*
	 * Get actions de relacionamento
	 */

	private RequisicaoMudancaServiceEjb getReqMudancaICAction() {
		if (reqMudancaICAction == null) {
			reqMudancaICAction = new RequisicaoMudancaServiceEjb();
		}

		return reqMudancaICAction;
	}

	private RequisicaoMudancaServico getReqMudancaServicoAction() {
		if (reqMudancaServicoAction == null) {
			reqMudancaServicoAction = new RequisicaoMudancaServico();
		}

		return reqMudancaServicoAction;
	}

	public void preencherComboComite(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		document.getSelectById("idGrupoComite").removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<GrupoDTO> listGrupo = grupoService.listGruposComite();

		document.getSelectById("idGrupoComite").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		document.getSelectById("idGrupoComite").addOptions(listGrupo, "idGrupo", "nome", null);
	}

	/**
	 * Preenche a combo com os grupos que nao fazem parte do CCM para gerar definir como Grupo executor
	 * 
	 * @author Riubbe Oliveira
	 * 
	 */
	public void preencherComboGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		document.getSelectById("idGrupoAtual").removeAllOptions();

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		Collection<GrupoDTO> listGrupo = grupoService.listGruposNaoComite();

		document.getSelectById("idGrupoAtual").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));

		document.getSelectById("idGrupoAtual").addOptions(listGrupo, "idGrupo", "nome", null);

	}

	/**
	 * Metodo para montar grid de aprovação da requisição mudanca
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @param requisicaoMudancaDto
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void montarTabelaAprovacoesMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		GrupoDTO grupoDto = new GrupoDTO();

		EmpregadoDTO empregadoDto = new EmpregadoDTO();

		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);

		AprovacaoMudancaDTO aprovacaoMudanca = new AprovacaoMudancaDTO();

		Collection<GrupoEmpregadoDTO> listaGrupoEmpregados = null;

		Collection<AprovacaoMudancaDTO> listaAprovacaoMudanca = new ArrayList<AprovacaoMudancaDTO>();
		
		Set<AprovacaoMudancaDTO> setListaAprovacaoMudanca = new HashSet<AprovacaoMudancaDTO>();
		
		

		String comentario = "";

		String dataHora = "";

		boolean validacao;
		
		boolean aux = false;		

		document.executeScript("deleteAllRowsMudanca()");
		document.executeScript("zerarContadores()");
		
		Collection<AprovacaoMudancaDTO> listaFinal = null;
		
		

		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {

			listaAprovacaoMudanca =  aprovacaoMudancaService.listaAprovacaoMudancaPorIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca(), requisicaoMudancaDto.getIdGrupoComite(),usuarioDto.getIdEmpregado());

		}

		if (requisicaoMudancaDto.getIdGrupoComite() != null) {

			listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudancaDto.getIdGrupoComite());

		} else {

			listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudancaDto.getIdGrupoAtual());

		}
		
		if(listaAprovacaoMudanca != null){
			for (AprovacaoMudancaDTO aprovacaoMudancaDto : listaAprovacaoMudanca) {
				setListaAprovacaoMudanca.add(aprovacaoMudancaDto);
			}
		}
			if (listaGrupoEmpregados != null) {
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : listaGrupoEmpregados) {
					AprovacaoMudancaDTO aprovacao = new AprovacaoMudancaDTO();
					if(grupoEmpregadoDTO.getIdEmpregado()!=null){
						empregadoDto.setIdEmpregado(grupoEmpregadoDTO.getIdEmpregado());
						empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
						aprovacao.setIdEmpregado(empregadoDto.getIdEmpregado());
						aprovacao.setNomeEmpregado(empregadoDto.getNome());
						
						setListaAprovacaoMudanca.add(aprovacao);
					}
					

				}
				
		}
			
		if (requisicaoMudancaDto.getIdProprietario() != null) {
				AprovacaoMudancaDTO aprovacaoDto = new AprovacaoMudancaDTO();
				if (usuarioDto.getIdUsuario().intValue() == requisicaoMudancaDto.getIdProprietario().intValue()) {
					empregadoDto.setIdEmpregado(usuarioDto.getIdEmpregado());
					empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
					aprovacaoDto.setIdEmpregado(empregadoDto.getIdEmpregado());
					aprovacaoDto.setNomeEmpregado(empregadoDto.getNome());
					setListaAprovacaoMudanca.add(aprovacaoDto);
					
				}
		}
		
		if (setListaAprovacaoMudanca != null) {
				for (AprovacaoMudancaDTO aprovacaoMudancaDto : setListaAprovacaoMudanca) {
					if (aprovacaoMudancaDto.getIdEmpregado() != null) {
						if (aprovacaoMudancaDto.getComentario() == null) {
							aprovacaoMudancaDto.setComentario("");
						}
						if (aprovacaoMudancaDto.getDataHoraInicio() == null) {
							aprovacaoMudancaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
						}
						if (usuarioDto.getIdEmpregado().intValue() == aprovacaoMudancaDto.getIdEmpregado().intValue()) {
							validacao = false;
						} else {
							validacao = true;
						}
						if(aprovacaoMudancaDto.getDataHoraVotacao() == null){
							aprovacaoMudancaDto.setDataHoraVotacao(UtilI18N.internacionaliza(request, "requisicaoMudanca.aindaNaoVotou"));
						}
						document.executeScript("addLinhaTabelaAprovacaoMudanca('" + aprovacaoMudancaDto.getIdEmpregado() + "','" + aprovacaoMudancaDto.getNomeEmpregado() + "','" + aprovacaoMudancaDto.getComentario() + "','" + aprovacaoMudancaDto.getDataHoraVotacao() + "','" + validacao + "','"+true+"')");
						document.executeScript("atribuirCheckedVotoMudanca('" + aprovacaoMudancaDto.getVoto() + "')");
					}
				}
				
			}
	

	}

	public void montarTabelaAprovacoesProposta(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);

		GrupoDTO grupoDto = new GrupoDTO();

		EmpregadoDTO empregadoDto = new EmpregadoDTO();

		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		AprovacaoPropostaService aprovacaoPropostaService = (AprovacaoPropostaService) ServiceLocator.getInstance().getService(AprovacaoPropostaService.class, null);

		AprovacaoPropostaDTO aprovacaoProposta = new AprovacaoPropostaDTO();

		Collection<GrupoEmpregadoDTO> listaGrupoEmpregados = null;

		Collection<AprovacaoPropostaDTO> listaAprovacaoProposta = new ArrayList<AprovacaoPropostaDTO>();
		
		Set<AprovacaoPropostaDTO> setListaAprovacaoProposta= new HashSet<AprovacaoPropostaDTO>();
		
		

		String comentario = "";

		String dataHora = "";

		boolean validacao;
		
		boolean aux = false;		

		document.executeScript("deleteAllRowsProposta()");
		document.executeScript("zerarContadores()");
		
		Collection<AprovacaoPropostaDTO> listaFinal = null;
		
		

		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {

			listaAprovacaoProposta =  aprovacaoPropostaService.listaAprovacaoPropostaPorIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca(), requisicaoMudancaDto.getIdGrupoComite(),usuarioDto.getIdEmpregado());

		}

		if (requisicaoMudancaDto.getIdGrupoComite() != null) {

			listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudancaDto.getIdGrupoComite());

		} else {

			listaGrupoEmpregados = grupoEmpregadoService.findByIdGrupo(requisicaoMudancaDto.getIdGrupoAtual());

		}
		
		if(listaAprovacaoProposta != null){
			for (AprovacaoPropostaDTO aprovacaoPropostaDto : listaAprovacaoProposta) {
				setListaAprovacaoProposta.add(aprovacaoPropostaDto);
			}
		}
			if (listaGrupoEmpregados != null) {
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : listaGrupoEmpregados) {
					AprovacaoPropostaDTO aprovacao = new AprovacaoPropostaDTO();
					if(grupoEmpregadoDTO.getIdEmpregado()!=null){
						empregadoDto.setIdEmpregado(grupoEmpregadoDTO.getIdEmpregado());
						empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
						aprovacao.setIdEmpregado(empregadoDto.getIdEmpregado());
						aprovacao.setNomeEmpregado(empregadoDto.getNome());
						
						setListaAprovacaoProposta.add(aprovacao);
					}
					

				}
				
		}
			
		if (requisicaoMudancaDto.getIdProprietario() != null) {
			AprovacaoPropostaDTO aprovacaoDto = new AprovacaoPropostaDTO();
				if (usuarioDto.getIdUsuario().intValue() == requisicaoMudancaDto.getIdProprietario().intValue()) {
					empregadoDto.setIdEmpregado(usuarioDto.getIdEmpregado());
					empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);
					aprovacaoDto.setIdEmpregado(empregadoDto.getIdEmpregado());
					aprovacaoDto.setNomeEmpregado(empregadoDto.getNome());
					setListaAprovacaoProposta.add(aprovacaoDto);
					
				}
		}
		
		if (setListaAprovacaoProposta != null) {
				for (AprovacaoPropostaDTO aprovacaoPropostaDto : setListaAprovacaoProposta) {
					if (aprovacaoPropostaDto.getIdEmpregado() != null) {
						if (aprovacaoPropostaDto.getComentario() == null) {
							aprovacaoPropostaDto.setComentario("");
						}
						if (aprovacaoPropostaDto.getDataHoraInicio() == null) {
							aprovacaoPropostaDto.setDataHoraInicio(UtilDatas.getDataHoraAtual());
						}
						if (usuarioDto.getIdEmpregado().intValue() == aprovacaoPropostaDto.getIdEmpregado().intValue()) {
							validacao = false;
						} else {
							validacao = true;
						}
						if(aprovacaoPropostaDto.getDataHoraVotacao() == null){
							aprovacaoPropostaDto.setDataHoraVotacao(UtilI18N.internacionaliza(request, "requisicaoMudanca.aindaNaoVotou"));
						}
						document.executeScript("addLinhaTabelaAprovacaoProposta('" + aprovacaoPropostaDto.getIdEmpregado() + "','" + aprovacaoPropostaDto.getNomeEmpregado() + "','" + aprovacaoPropostaDto.getComentario() + "','" + aprovacaoPropostaDto.getDataHoraVotacao() + "','" + validacao + "','"+true+"')");
						document.executeScript("atribuirCheckedVotoProposta('" + aprovacaoPropostaDto.getVoto() + "')");
					}
				}
				
			}
	

	}
	

	public void validacaoAvancaFluxo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception{
		
		Set<AprovacaoMudancaDTO> setListaAprovacaoMudanca = new HashSet<AprovacaoMudancaDTO>();
		
		AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
		AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("A");
			aprovacaoMudancaDto.setQuantidadeVotoAprovada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("R");
			aprovacaoMudancaDto.setQuantidadeVotoRejeitada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			aprovacaoMudancaDto.setQuantidadeAprovacaoMudanca(aprovacaoMudancaService.quantidadeAprovacaoMudanca(aprovacaoMudancaDto, requisicaoMudancaDto.getIdGrupoComite()));
			
		}
		if(aprovacaoMudancaDto.getQuantidadeVotoAprovada() > 0){
			
			if(aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca().intValue() == aprovacaoMudancaDto.getQuantidadeVotoAprovada()){
				document.executeScript("$('#btnGravarEFinalizar').show()");
			}
			else{
				if(aprovacaoMudancaDto.getQuantidadeVotoAprovada() >= ((aprovacaoMudancaDto.getQuantidadeAprovacaoMudanca()/2) + 1)){
					document.executeScript("$('#btnGravarEFinalizar').show()");
				}
			}
		}
		
	}
	
	public void quantidadeAprovacaoMudancaPorVotoAprovada(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		StringBuffer hmtl = new StringBuffer();
		AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
		AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("A");
			aprovacaoMudancaDto.setQuantidadeVotoAprovada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoAprovada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			if (aprovacaoMudancaDto.getQuantidadeVotoAprovada() != null) {
				hmtl.append("<div>" + UtilI18N.internacionaliza(request, "itemRequisicaoProduto.qtdeAprovada") + ": " + aprovacaoMudancaDto.getQuantidadeVotoAprovada() + "</div>");
				document.getElementById("quantidadePorVotoAprovadaMudanca").setInnerHTML(hmtl.toString());
			}
		}

	}
	
	public void quantidadeAprovacaoPropostaPorVotoAprovada(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		StringBuffer hmtl = new StringBuffer();
		AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
		AprovacaoPropostaService aprovacaoPropostaService = (AprovacaoPropostaService) ServiceLocator.getInstance().getService(AprovacaoPropostaService.class, null);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoPropostaDto.setVoto("A");
			aprovacaoPropostaDto.setQuantidadeVotoAprovada(aprovacaoPropostaService.quantidadeAprovacaoPropostaPorVotoAprovada(aprovacaoPropostaDto,requisicaoMudancaDto.getIdGrupoComite()));
			if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() != null) {
				hmtl.append("<div>" + UtilI18N.internacionaliza(request, "itemRequisicaoProduto.qtdeAprovada") + ": " + aprovacaoPropostaDto.getQuantidadeVotoAprovada() + "</div>");
				document.getElementById("quantidadePorVotoAprovadaProposta").setInnerHTML(hmtl.toString());
			}
		}

	}

	public void quantidadeAprovacaoMudancaPorVotoRejeitada(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		StringBuffer hmtl = new StringBuffer();
		AprovacaoMudancaDTO aprovacaoMudancaDto = new AprovacaoMudancaDTO();
		AprovacaoMudancaService aprovacaoMudancaService = (AprovacaoMudancaService) ServiceLocator.getInstance().getService(AprovacaoMudancaService.class, null);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoMudancaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoMudancaDto.setVoto("R");
			aprovacaoMudancaDto.setQuantidadeVotoRejeitada(aprovacaoMudancaService.quantidadeAprovacaoMudancaPorVotoRejeitada(aprovacaoMudancaDto,requisicaoMudancaDto.getIdGrupoComite()));
			if (aprovacaoMudancaDto.getQuantidadeVotoRejeitada() != null) {
				hmtl.append("<div>" + UtilI18N.internacionaliza(request, "requisicaoMudanca.quantidadeAprovacaoMudancaRejeitda") + ": " + aprovacaoMudancaDto.getQuantidadeVotoRejeitada() + "</div>");
				document.getElementById("quantidadePorVotoRejeitadaMudanca").setInnerHTML(hmtl.toString());
			}
		}

	}
	
	public void quantidadeAprovacaoPropostaPorVotoRejeitada(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		StringBuffer hmtl = new StringBuffer();
		AprovacaoPropostaDTO aprovacaoPropostaDto = new AprovacaoPropostaDTO();
		AprovacaoPropostaService aprovacaoPropostaService = (AprovacaoPropostaService) ServiceLocator.getInstance().getService(AprovacaoPropostaService.class, null);
		if (requisicaoMudancaDto.getIdRequisicaoMudanca() != null) {
			aprovacaoPropostaDto.setIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
			aprovacaoPropostaDto.setVoto("R");
			aprovacaoPropostaDto.setQuantidadeVotoAprovada(aprovacaoPropostaService.quantidadeAprovacaoPropostaPorVotoRejeitada(aprovacaoPropostaDto,requisicaoMudancaDto.getIdGrupoComite()));
			if (aprovacaoPropostaDto.getQuantidadeVotoAprovada() != null) {
				hmtl.append("<div>" + UtilI18N.internacionaliza(request, "requisicaoMudanca.quantidadeAprovacaoMudancaRejeitda") + ": " + aprovacaoPropostaDto.getQuantidadeVotoAprovada() + "</div>");
				document.getElementById("quantidadePorVotoRejeitadaProposta").setInnerHTML(hmtl.toString());
			}
		}

	}

	/**
	 * Retorna uma lista de informações da entidade ocorrencia
	 * 
	 * @param requisicaoMudancaDto
	 * @param request
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 * @author geber.costa
	 */
	public String listInfoRegExecucaoRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDto, HttpServletRequest request) throws ServiceException, Exception {
		String ocorrenciaAux = "";
		String tamanhoAux = "";
		JustificativaRequisicaoMudancaService justificativaRequisicaoMudancaService = (JustificativaRequisicaoMudancaService) ServiceLocator.getInstance().getService(JustificativaRequisicaoMudancaService.class, null);

		OcorrenciaMudancaService ocorrenciaMudancaService = (OcorrenciaMudancaService) ServiceLocator.getInstance().getService(OcorrenciaMudancaService.class, null);

		Collection<OcorrenciaMudancaDTO> col = ocorrenciaMudancaService.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());

		CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
		OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();

		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
		OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();

		String strBuffer = "<table class='dynamicTable table table-striped table-bordered table-condensed dataTable' style='table-layout: fixed;'>";
		strBuffer += "<tr>";
		strBuffer += "<th style='width:20%;'>";
		strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.datahora");
		strBuffer += "</th>";
		strBuffer += "<th>";
		strBuffer += UtilI18N.internacionaliza(request, "solicitacaoServico.informacaoexecucao");
		strBuffer += "</th>";
		strBuffer += "</tr>";

		if (col != null) {

			for (OcorrenciaMudancaDTO ocorrenciaMudancaDto : col) {
		
				if (ocorrenciaMudancaDto.getOcorrencia() != null) {
					Source source = new Source(ocorrenciaMudancaDto.getOcorrencia());
					ocorrenciaMudancaDto.setOcorrencia(source.getTextExtractor().toString());
				}		 
				
				/*
				 * if (categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != null && categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != 0) {
				 * categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaMudancaDto.getIdCategoriaOcorrencia()); categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO); }
				 * 
				 * if (origemOcorrenciaDTO.getIdOrigemOcorrencia() != null && origemOcorrenciaDTO.getIdOrigemOcorrencia() != 0) {
				 * origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaMudancaDto.getIdOrigemOcorrencia()); origemOcorrenciaDAO.restore(origemOcorrenciaDTO); }
				 */

				String ocorrencia = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getOcorrencia());
				if (ocorrencia != null) {
					int tamanhoString = 0;
					int tamanhoAQuebrar = 0;
					int x = 200;
					int p = 0;
					float divisao = 0;
					int i = 0;
					int iAux = 0;
					tamanhoString = ocorrencia.length();
					tamanhoAQuebrar = (tamanhoString/x);
					//tamanhoAQuebrar = (int) divisao;
					
					for (int y = 0; y < tamanhoAQuebrar; y++) {
						System.out.println(tamanhoAux.length());
						ocorrenciaAux += ocorrencia.substring(tamanhoAux.length(), x) + "\n";
						tamanhoAux += ocorrencia.substring(tamanhoAux.length(), x);
						x= x + 200;
					}
					if (tamanhoAQuebrar > 0) {
						System.out.println(tamanhoAux.length());
						ocorrenciaAux += ocorrencia.substring(tamanhoAux.length(), tamanhoString);
					}
					
					/*for (i = 0; i < ocorrencia.length(); i++) {
						if (i == 180 || i == 360 || i == 540 || i == 720 || i == 900 || i == 1080 || i == 1260 || i == 1440 || i == 1620 || i == 1800 || i == 1980 || i == 2160) {
							iAux = i;
							System.out.println(tamanhoAux.length());
							ocorrenciaAux += ocorrencia.substring(tamanhoAux.length(), i) + "\n";
							tamanhoAux += ocorrencia.substring(tamanhoAux.length(), i);
						}
						
					}*/
					//System.out.println(i);
				}
				String descricao = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getDescricao());
				String informacoesContato = UtilStrings.nullToVazio(ocorrenciaMudancaDto.getInformacoesContato());
				if (ocorrenciaAux != null && ocorrenciaAux.length() > 0) {
					ocorrencia = ocorrenciaAux;
				}
				ocorrencia = ocorrencia.replaceAll("\"", "");
				descricao = descricao.replaceAll("\"", "");
				informacoesContato = informacoesContato.replaceAll("\"", "");
				ocorrencia = ocorrencia.replaceAll("\n", "<br>");
				descricao = descricao.replaceAll("\n", "<br>");
				informacoesContato = informacoesContato.replaceAll("\n", "<br>");
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", ""));
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", ""));
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", ""));
				strBuffer += "<tr>";
				strBuffer += "<td>";
				strBuffer += "<b>" + UtilDatas.dateToSTR(ocorrenciaMudancaDto.getDataregistro()) + " - " + ocorrenciaMudancaDto.getHoraregistro();
				strBuffer += " - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <b>" + ocorrenciaMudancaDto.getRegistradopor() + "</b>";
				strBuffer += "</td>";
				strBuffer += "<td style='word-wrap: break-word;overflow:hidden;'>";
				strBuffer += "<b>" + ocorrenciaMudancaDto.getDescricao() + "<br><br></b>";
				strBuffer += "<b>" + ocorrencia + "<br><br></b>";

				if (ocorrenciaMudancaDto.getCategoria() != null){
					if (ocorrenciaMudancaDto.getCategoria().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
							|| ocorrenciaMudancaDto.getCategoria().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())) {
						JustificativaRequisicaoMudancaDTO justificativaSolicitacaoDTO = new JustificativaRequisicaoMudancaDTO();
						if (ocorrenciaMudancaDto.getIdJustificativa() != null) {
							justificativaSolicitacaoDTO.setIdJustificativaMudanca(ocorrenciaMudancaDto.getIdJustificativa());
							justificativaSolicitacaoDTO = (JustificativaRequisicaoMudancaDTO) justificativaRequisicaoMudancaService.restore(justificativaSolicitacaoDTO);
							if (justificativaSolicitacaoDTO != null) {
								strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" + justificativaSolicitacaoDTO.getDescricaoJustificativa() + "<br><br></b>";
							}
						}
						if (!UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
							strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()) + "<br><br></b>";
						}
					}
				}
			

				if (ocorrenciaMudancaDto.getOcorrencia() != null){
					if (categoriaOcorrenciaDTO.getNome() != null && !categoriaOcorrenciaDTO.getNome().equals("")) {
						if (categoriaOcorrenciaDTO.getNome().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
								|| categoriaOcorrenciaDTO.getNome().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())) {
							JustificativaRequisicaoMudancaDTO justificativaSolicitacaoDTO = new JustificativaRequisicaoMudancaDTO();
							if (ocorrenciaMudancaDto.getIdJustificativa() != null) {
								justificativaSolicitacaoDTO.setIdJustificativaMudanca(ocorrenciaMudancaDto.getIdJustificativa());
								justificativaSolicitacaoDTO = (JustificativaRequisicaoMudancaDTO) justificativaRequisicaoMudancaService.restore(justificativaSolicitacaoDTO);
								if (justificativaSolicitacaoDTO != null) {
									strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" + justificativaSolicitacaoDTO.getDescricaoJustificativa() + "<br><br></b>";
								}
							}
							if (!UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
								strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaMudancaDto.getComplementoJustificativa()) + "<br><br></b>";
							}
						}
					}
				}
				ocorrenciaAux = "";
				tamanhoAux = "";
				strBuffer += "</td>";
				strBuffer += "</tr>";
			}
		}
		strBuffer += "</table>";

		categoriaOcorrenciaDTO = null;
		origemOcorrenciaDTO = null;
		ocorrenciaAux = "";
		tamanhoAux = "";

		return strBuffer;
	}
	
	public RequisicaoMudancaDTO getRequisicaoMudancaDto() {	
		return requisicaoMudancaDto;
	}

	public void setRequisicaoMudancaDto(RequisicaoMudancaDTO requisicaoMudancaDto) {
		this.requisicaoMudancaDto = requisicaoMudancaDto;
	}

	public void validacaoCategoriaMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		RequisicaoMudancaDTO RequisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();

		TipoMudancaService tipoMudancaService = (TipoMudancaService) ServiceLocator.getInstance().getService(TipoMudancaService.class, null);

		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();

		if (RequisicaoMudancaDto.getIdTipoMudanca() != null) {
			tipoMudancaDto.setIdTipoMudanca(RequisicaoMudancaDto.getIdTipoMudanca());

			tipoMudancaDto = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDto);
			
			document.getElementById("idGrupoAtual").setValue("" + tipoMudancaDto.getIdGrupoExecutor());

			if (tipoMudancaDto.getNomeTipoMudanca() != null && tipoMudancaDto.getNomeTipoMudanca().equalsIgnoreCase("Normal")) {
				document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + false + ");");
				document.executeScript("$('#div_categoria').show();");
				
				document.executeScript("$('#div_ehProposta').show();");
			} else {
				document.executeScript("$('#div_categoria').hide();");
				document.executeScript("$('#nomeCategoriaMudanca').attr('disabled', " + true + ");");
				
				document.executeScript("$('#div_ehProposta').hide();");
			}
		}

	}
	
	protected void restaurarAnexos(HttpServletRequest request, RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception {
		/*ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection<UploadDTO> colAnexosUploadDTO = null;
		if (requisicaoMudancaDTO != null && requisicaoMudancaDTO.getIdRequisicaoMudanca() != null) {
			Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDTO.getIdRequisicaoMudanca());
			colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
			if (colAnexosUploadDTO != null) {
				for (UploadDTO uploadDTO : colAnexosUploadDTO) {
					if (uploadDTO.getDescricao() == null) {
						uploadDTO.setDescricao("");
					}
				}
			}
		}
		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);*/
		Collection<UploadDTO> colAnexosUploadDTO = null;
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndIdLiberacaoAndLigacao(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDTO.getIdRequisicaoMudanca());
		colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		/**
		 * =================================
		 * Restaura anexo(s) principal.
		 * =================================
		 * **/
		if (colAnexosUploadDTO != null) {
			for (UploadDTO uploadDTO : colAnexosUploadDTO) {
				if (uploadDTO.getDescricao() == null) {
					uploadDTO.setDescricao("");
				}
			}
		}
		
	
		//Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
		request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", colAnexosUploadDTO);
	}	
	protected void restaurarAnexosPlanoDeReversao(HttpServletRequest request, RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception {
		/*ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection<UploadDTO> colAnexosUploadDTO = null;
		if (requisicaoMudancaDTO != null && requisicaoMudancaDTO.getIdRequisicaoMudanca() != null) {
			Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDTO.getIdRequisicaoMudanca());
			colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
			if (colAnexosUploadDTO != null) {
				for (UploadDTO uploadDTO : colAnexosUploadDTO) {
					if (uploadDTO.getDescricao() == null) {
						uploadDTO.setDescricao("");
					}
				}
			}
		}
		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);*/
		Collection<UploadDTO> colAnexosUploadDTO = null;
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndIdLiberacaoAndLigacao(ControleGEDDTO.TABELA_PLANO_REVERSAO_MUDANCA, requisicaoMudancaDTO.getIdRequisicaoMudanca());
		colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		
		/**
		 * =================================
		 * Restaura anexo(s) principal.
		 * =================================
		 * **/
		if (colAnexosUploadDTO != null) {
			for (UploadDTO uploadDTO : colAnexosUploadDTO) {
				if (uploadDTO.getDescricao() == null) {
					uploadDTO.setDescricao("");
				}
				if(!UtilStrings.isNotVazio(uploadDTO.getVersao())){
					uploadDTO.setVersao(" ");
				}
			}
		}
		
		
		
		request.getSession(true).setAttribute("colUploadPlanoDeReversaoGED", colAnexosUploadDTO);
	}	
	
	public Collection gravarAnexoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		//deleta os anexos referentes a essa requisicao de mudança para poder regravá-los
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection<UploadDTO> colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_REQUISICAOMUDANCA, requisicaoMudancaDto.getIdRequisicaoMudanca());
		Collection<UploadDTO> colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);
		
		if(colAnexosUploadDTO!= null){
			for (Iterator iterator = colAnexosUploadDTO.iterator(); iterator.hasNext();) {
				UploadDTO object = (UploadDTO) iterator.next();
				controleGedService.delete(object);
			}
		}
		
		Integer idEmpresa = null;
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
		//Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
		Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadRequisicaoMudancaGED");
		requisicaoMudancaDto.setColArquivosUpload(arquivosUpados);
		// Rotina para gravar no banco
		if (requisicaoMudancaDto.getColArquivosUpload() != null && requisicaoMudancaDto.getColArquivosUpload().size() > 0) {
			idEmpresa = WebUtil.getIdEmpresa(request);
			if (idEmpresa == null)
				idEmpresa = 1;
			/*requisicaoMudancaService.gravaInformacoesGED(requisicaoMudancaDto.getColArquivosUpload(), idEmpresa, requisicaoMudancaDto, null);*/
			//document.alert(UtilI18N.internacionaliza(request, "MSG06"));
//			document.executeScript("('#POPUP_menuAnexos').dialog('close');");
		}
		requisicaoMudancaDto.setIdEmpresa(idEmpresa);

		return requisicaoMudancaDto.getColArquivosUpload();
	}
	
	public Collection gravarAnexosPlanosDeReversao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDto) throws Exception {
		Integer idEmpresa = null;
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
		Collection<UploadDTO> arquivosReversaoUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadPlanoDeReversaoGED");
		requisicaoMudancaDto.setColUploadPlanoDeReversaoGED(arquivosReversaoUpados);
		// Rotina para gravar no banco
		if (requisicaoMudancaDto.getColUploadPlanoDeReversaoGED() != null && requisicaoMudancaDto.getColUploadPlanoDeReversaoGED().size() > 0) {
			idEmpresa = WebUtil.getIdEmpresa(request);
			if (idEmpresa == null)
				idEmpresa = 1;
			/*requisicaoMudancaService.gravaInformacoesGED(requisicaoMudancaDto.getColArquivosUpload(), idEmpresa, requisicaoMudancaDto, null);*/
			//document.alert(UtilI18N.internacionaliza(request, "MSG06"));
//			document.executeScript("('#POPUP_menuAnexos').dialog('close');");
		}
		requisicaoMudancaDto.setIdEmpresa(idEmpresa);
		
		return requisicaoMudancaDto.getColUploadPlanoDeReversaoGED();
	}
	
	public void imprimirRelatorioReqMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		OcorrenciaMudancaService ocorrenciaMudancaService = (OcorrenciaMudancaService) ServiceLocator.getInstance().getService(OcorrenciaMudancaService.class, null);
		
		Collection<OcorrenciaMudancaDTO> col = ocorrenciaMudancaService.findByIdRequisicaoMudanca(requisicaoMudancaDto.getIdRequisicaoMudanca());
		
		JRDataSource dataSource = null;
		
		HttpSession session = ((HttpServletRequest) request).getSession();
		usuario = WebUtil.getUsuario(request);


		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "relatorioRequisicaoMudanca.relatorioRequisicaoMudanca"));
		parametros.put("CIDADE", UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioCidade"));
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		

		if (col.size() == 0) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		} 
		
		try
		{
			dataSource = new JRBeanCollectionDataSource(col);
		
			Date dt = new Date();
			String strCompl = "" + dt.getTime();
			String caminhoJasper = CITCorporeUtil.caminho_real_app + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioRequisicaoMudanca.jasper";
			String diretorioReceita = CITCorporeUtil.caminho_real_app + "/tempFiles";
			String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";
			
			JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 25);
			 
			// Instancia o virtualizador
			JRAbstractLRUVirtualizer virtualizer = new JRSwapFileVirtualizer(25, arquivoSwap, true);
			 
			//Seta o parametro REPORT_VIRTUALIZER com a instância da virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);
			 
			//Preenche o relatório e exibe numa GUI
			JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			//JasperViewer.viewReport(print,false);
			
			JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioRequisicaoMudanca" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
	
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS + "/RelatorioRequisicaoMudanca"
					+ strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		
		} catch(OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}
	
	public UsuarioDTO getUsuario() {
		return usuario;
	}

	public void setUsuario(UsuarioDTO usuario) {
		this.usuario = usuario;
	}
	
	public void verificarParametroAnexos(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		
		String DISKFILEUPLOAD_REPOSITORYPATH = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DISKFILEUPLOAD_REPOSITORYPATH, "");
		if(DISKFILEUPLOAD_REPOSITORYPATH == null){
			DISKFILEUPLOAD_REPOSITORYPATH = "";
		}
		if(DISKFILEUPLOAD_REPOSITORYPATH.equals("")){
			throw new LogicException(UtilI18N.internacionaliza(request,"citcorpore.comum.anexosUploadSemParametro"));
		}
		File pasta = new File(DISKFILEUPLOAD_REPOSITORYPATH);
		if(!pasta.exists()){
			throw new LogicException(UtilI18N.internacionaliza(request,"citcorpore.comum.pastaIndicadaNaoExiste"));
		}
	}

	private void carregaInformacoesComplementares(DocumentHTML document, HttpServletRequest request, RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception{
			
			TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class, br.com.centralit.citcorpore.util.WebUtil.getUsuarioSistema(request));
			
//			document.executeScript("exibirInformacoesAprovacao(\"" + getProblemaService(request).getUrlInformacoesComplementares(problemaDto) + "\");");
			document.executeScript("exibirInformacoesComplementares('" + getRequisicaoMudancaService(request).getUrlInformacoesComplementares(requisicaoMudancaDTO) + "');");
//			document.executeScript("exibirInformacoesComplementares(localhost:8080/citsmart/pages/\"" + getProblemaService(request).getUrlInformacoesComplementares(problemaDto) + "\");");
			TemplateSolicitacaoServicoDTO templateDto = templateService.recuperaTemplateRequisicaoMudanca(requisicaoMudancaDTO);
			
			if (templateDto != null) {
				if (templateDto.getAlturaDiv() != null){
					document.executeScript("document.getElementById('divInformacoesComplementares').style.height = '" + templateDto.getAlturaDiv().intValue() + "px';");
					
				}
					
			}
			document.executeScript("escondeJanelaAguarde()");
			
		}
		
	public void criaTabelaLiberacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws Exception{
		LiberacaoMudancaService liberacaoMudancaService = (LiberacaoMudancaService) ServiceLocator.getInstance().getService(LiberacaoMudancaService.class, null);
		ArrayList<LiberacaoMudancaDTO> liberacaoMudanca = (ArrayList<LiberacaoMudancaDTO>) liberacaoMudancaService.findByIdRequisicaoMudanca(this.requisicaoMudancaDto.getIdLiberacao(),this.requisicaoMudancaDto.getIdRequisicaoMudanca());
		if(liberacaoMudanca!= null){
			HTMLTable table;
			table = document.getTableById("tblLiberacao");
			table.deleteAllRows();
			table.addRowsByCollection(liberacaoMudanca, new String[] {"idLiberacao","titulo","descricao","status","situacaoLiberacao"}, null, null, new String[] {"gerarButtonDeleteVersao"}, null, null);
		}
	}
	
	public void restoreImpactoUrgenciaPorTipoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws Exception{
		
		RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) document.getBean();
		
		TipoMudancaDTO tipoMudancaDto = new TipoMudancaDTO();
		
		TipoMudancaService tipoMudancaService = (TipoMudancaService)ServiceLocator.getInstance().getService(TipoMudancaService.class, null);
		
		if(requisicaoMudancaDto.getIdTipoMudanca()!=null){
			tipoMudancaDto.setIdTipoMudanca(requisicaoMudancaDto.getIdTipoMudanca());
			tipoMudancaDto = (TipoMudancaDTO) tipoMudancaService.restore(tipoMudancaDto);
		}
		
		if(tipoMudancaDto!=null){
			requisicaoMudancaDto.setNivelImpacto(tipoMudancaDto.getImpacto());
			requisicaoMudancaDto.setNivelUrgencia(tipoMudancaDto.getUrgencia());
			
		}
		
		HTMLForm form = document.getForm("form");
		form.setValues(requisicaoMudancaDto);
		document.executeScript("atualizaPrioridade()");
	}
	
	/**
	 * @author geber.costa
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * 
	 * Método que pega do jsp o id da liberação , faz a validação , restaura e executa o método '' devolvendo o objeto com os valores preenchidos
	 */
	public void inserirRequisicaoLiberacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String idLib = (String)request.getParameter("liberacao#idRequisicaoLiberacao");		
		RequisicaoLiberacaoDTO requisicaoLiberacaoDto = new RequisicaoLiberacaoDTO();
		Integer idLiberacao = (new Integer(idLib));
		if(requisicaoLiberacaoDto != null){
			requisicaoLiberacaoDto.setIdRequisicaoLiberacao(idLiberacao);
		
		  RequisicaoLiberacaoService requisicaoLiberacaoService = (RequisicaoLiberacaoService) ServiceLocator.getInstance().getService(RequisicaoLiberacaoService.class, WebUtil.getUsuarioSistema(request));
		  requisicaoLiberacaoDto = (RequisicaoLiberacaoDTO) requisicaoLiberacaoService.restore(requisicaoLiberacaoDto);
		  
		  //Se o valor dele tiver igual ao valor do primeiro caractere do enumerado correspondente ele irá setar e executar o método "adicionaLiberacaoMudanca", esse método é uma função javascript
//		  for(FaseRequisicaoLiberacao situacaoLiberacao : Enumerados.FaseRequisicaoLiberacao.values()){
//			  Character verifica = situacaoLiberacao.getSituacao().toString().toUpperCase().trim().charAt(0);
//
//			  if(requisicaoLiberacaoDto.getSituacao().toUpperCase().toString().equals(verifica.toString().toUpperCase())){
//				  requisicaoLiberacaoDto.setSituacaoLiberacao(situacaoLiberacao.getSituacao().toString());
//				  document.executeScript("adicionaLiberacaoMudanca('"+requisicaoLiberacaoDto.getIdRequisicaoLiberacao()+"','"+requisicaoLiberacaoDto.getTitulo()+"','"
//						  +requisicaoLiberacaoDto.getDescricao()+"','"+requisicaoLiberacaoDto.getStatus()+"','"+requisicaoLiberacaoDto.getSituacaoLiberacao()+"')");
//				  return;
//			  }
//		  }
		  //caso contrário ele seta o valor vazio e executa a função javascript
		  if(requisicaoLiberacaoDto.getSituacao().toUpperCase().toString().equalsIgnoreCase("E")){
			  
			  requisicaoLiberacaoDto.setSituacaoLiberacao(UtilI18N.internacionaliza(request, "liberacao.emExecucao"));
		  }
		  else if(requisicaoLiberacaoDto.getSituacao().toUpperCase().toString().equalsIgnoreCase("R")){
			  
			  requisicaoLiberacaoDto.setSituacaoLiberacao(UtilI18N.internacionaliza(request, "citcorpore.comum.resolvida"));
			  
		  }else if(requisicaoLiberacaoDto.getSituacao().toUpperCase().toString().equalsIgnoreCase("N")){
			  
			  requisicaoLiberacaoDto.setSituacaoLiberacao(UtilI18N.internacionaliza(request, "requisicaoLiberacao.naoResolvida"));
		  }
		  else{
			  requisicaoLiberacaoDto.setSituacaoLiberacao("");
		  }
		  
		  document.executeScript("adicionaLiberacaoMudanca('"+requisicaoLiberacaoDto.getIdRequisicaoLiberacao()+"','"+requisicaoLiberacaoDto.getTitulo()+"','"
		  +requisicaoLiberacaoDto.getDescricao()+"','"+requisicaoLiberacaoDto.getStatus()+"')");
	  
		}
	}
	
	public void limpar(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//Thiago Fernandes - 29/10/2013 - 18:49 - Sol. 121468 - Criação de Upload para requisição mudança para evitar conflitos com outras telas do sistema que usão upload.
		request.getSession(true).setAttribute("colUploadRequisicaoMudancaGED", null);
		request.getSession(true).setAttribute("colUploadPlanoDeReversaoGED", null);
	}
	
	public void mostraHistoricoMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, RequisicaoMudancaDTO requisicaoMudancaDTO) throws Exception {
		//HistoricoLiberacaoService liberacaoService = (HistoricoLiberacaoService) ServiceLocator.getInstance().getService(HistoricoLiberacaoService.class, null);
		HistoricoMudancaService mudancaService = (HistoricoMudancaService) ServiceLocator.getInstance().getService(HistoricoMudancaService.class, null);
		/*RequisicaoLiberacaoDTO requisicaoLiberacaoDTO = (RequisicaoLiberacaoDTO) document.getBean();*/
		Collection<String> colbaselines = new ArrayList();
		
		HTMLElement divPrincipal = document.getElementById("contentBaseline");
		StringBuffer subDiv = new StringBuffer();
		subDiv.append("" +
				"<div class='formBody'> " +
				"	<table id='tblBaselines' class='tableLess'> 	" +
				"		<thead>" +
				"			<tr>" +
				"				<th>"+UtilI18N.internacionaliza(request, "liberacao.baseline")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "liberacao.versaoHistorico")+"</th>	" + 
				"				<th>"+UtilI18N.internacionaliza(request, "liberacao.executorModificacao")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "colaborador.colaborador")+"</th>	" +
				"				<th>"+UtilI18N.internacionaliza(request, "liberacao.idRequisicaoMudancaVinculada")+"</th>	" + 
				"				<th>"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historicoRestaurar")+"</th>	" +
				"			</tr>" +
				"		</thead>");
		List<HistoricoMudancaDTO> listHistoricoMudancas = new ArrayList<HistoricoMudancaDTO>();
		List<HistoricoMudancaDTO> listHistoricoMudancasAux = new ArrayList<HistoricoMudancaDTO>();
		listHistoricoMudancas = mudancaService.listHistoricoMudancaByIdRequisicaoMudanca(requisicaoMudancaDTO.getIdRequisicaoMudanca());

		if(listHistoricoMudancas!=null) {
			int count = 0;
			boolean flag = false;		
			document.executeScript("document.form.idHistoricoMudanca.value = " + requisicaoMudancaDTO.getIdRequisicaoMudanca());			
			document.executeScript("countHistorico = 0");
			for (HistoricoMudancaDTO historicoMudancaDTO : listHistoricoMudancas) {
				flag = (historicoMudancaDTO.getBaseLine()!= null && historicoMudancaDTO.getBaseLine().equals("SIM")) ? true: false;
				String disabled = "";
				count++;
				DecimalFormat df = new DecimalFormat("0.##");
				String versao = df.format(historicoMudancaDTO.getHistoricoVersao());
				versao = versao.replace(",",".");
				document.executeScript("seqBaseline = " + count);
				if(flag){
					 disabled = "disabled='disabled'";
					 colbaselines.add("idHistoricoMudanca" + count);
					}
				subDiv.append(
				"<tbody>"+
				"	<tr>"+
				"		<td width='5%'>" + "<input type='checkbox' "+ disabled + " id='idHistoricoMudanca" + count + "'" +
				" name='idHistoricoMudanca" + count + "' value='0"+historicoMudancaDTO.getIdHistoricoMudanca().toString()+ "'/></td>" +
				"		<td>" + versao + " - " + UtilDatas.formatTimestamp(historicoMudancaDTO.getDataHoraModificacao())+ "</td>" +
				"		<td width='15%'>" + (historicoMudancaDTO.getNomeExecutorModificacao() == null ? "" : historicoMudancaDTO.getNomeExecutorModificacao()) + "</td>" +
				"		<td>" + (historicoMudancaDTO.getNomeProprietario() == null ? "" : historicoMudancaDTO.getNomeProprietario())+ "</td>" +
				"		<td>" + (historicoMudancaDTO.getIdRequisicaoMudanca() == null ? "" : historicoMudancaDTO.getIdRequisicaoMudanca()) + "</td>" +
				"		<td>" +
				"			<a href='javascript:;' class='even' id='even-" + historicoMudancaDTO.getIdHistoricoMudanca() + "'>" +
						"		<img src='../../template_new/images/icons/small/grey/documents.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historico")+"' " +
								"title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.historico")+"' /></a>");
				if(flag) {
					subDiv.append(
					"		<a href='javascript:;' onclick='restaurarHistorico(\"" + historicoMudancaDTO.getIdHistoricoMudanca() + "\")'>" +
					"			<img src='../../template_new/images/icons/small/grey/refresh_3.png' alt='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.restaurar")+"' " +
							"title='"+UtilI18N.internacionaliza(request, "itemConfiguracaoTree.restaurar")+"' /></a>");
				}
				
				subDiv.append("		</td>" +
				"	</tr>" +
				"	<tr class='sel' id='sel-" + historicoMudancaDTO.getIdHistoricoMudanca() + "'>" + 
				"	</tr>" +
				"</tbody>");
			}
		}
		subDiv.append(
			"	</table>" +
			"</div>");
		divPrincipal.setInnerHTML(subDiv.toString());
		document.executeScript("marcarCheckbox("+ colbaselines + ")");
		for (String str : colbaselines) {
			document.getCheckboxById(str).setChecked(true);
		}
	}
	
	public void preencherComboCategoriaSolucao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CategoriaSolucaoService categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);
		Collection colCategSolucao = categoriaSolucaoService.listHierarquia();
		HTMLSelect idCategoriaSolucao = (HTMLSelect) document.getSelectById("idCategoriaSolucao");
		idCategoriaSolucao.removeAllOptions();
		idCategoriaSolucao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colCategSolucao != null) {
			idCategoriaSolucao.addOptions(colCategSolucao, "idCategoriaSolucao", "descricaoCategoriaNivel", null);
		}
	}
		
	public void restaurarBaseline(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) br.com.centralit.citcorpore.util.WebUtil.getUsuario(request);
		HistoricoMudancaDao historicoMudancaDao =  new HistoricoMudancaDao();
		if(usrDto == null){
			return;
		}
		RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) document.getBean();
		RequisicaoMudancaDTO requisicaoMudancaDTOAux = new RequisicaoMudancaDTO();
		HistoricoMudancaDTO historicoMudancaDTO = new HistoricoMudancaDTO();
		historicoMudancaDTO.setIdHistoricoMudanca(Integer.parseInt(request.getParameter("idHistoricoMudanca")));
		
		HistoricoMudancaService historicoMudancaService = (HistoricoMudancaService) ServiceLocator.getInstance().getService(HistoricoMudancaService.class, null);
		RequisicaoMudancaService requisicaoLiberacaoService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		historicoMudancaDTO = (HistoricoMudancaDTO) historicoMudancaDao.restore(historicoMudancaDTO);


		//Realizando a Reflexão de Item de Configuração
		Reflexao.copyPropertyValues(historicoMudancaDTO, requisicaoMudancaDTOAux);
			
		List<RequisicaoMudancaItemConfiguracaoDTO> colItemconfiguracao = new ArrayList<RequisicaoMudancaItemConfiguracaoDTO>();
		RequisicaoMudancaItemConfiguracaoDao itemConfiguracaoDao = new RequisicaoMudancaItemConfiguracaoDao();
		colItemconfiguracao = (List<RequisicaoMudancaItemConfiguracaoDTO>) itemConfiguracaoDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setListRequisicaoMudancaItemConfiguracaoDTO(colItemconfiguracao);
		
		List<ProblemaMudancaDTO> colProblemas = new ArrayList<ProblemaMudancaDTO>();
		ProblemaMudancaDAO problemaMudancaDAO = new ProblemaMudancaDAO();
		colProblemas =  (List<ProblemaMudancaDTO>) problemaMudancaDAO.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setListProblemaMudancaDTO(colProblemas);
		
		List<GrupoRequisicaoMudancaDTO> colGrupoRequisicaoMudanca = new ArrayList<GrupoRequisicaoMudancaDTO>();
		GrupoRequisicaoMudancaDao grupoRequisicaoMudancaDAO = new GrupoRequisicaoMudancaDao();
		colGrupoRequisicaoMudanca =  (List<GrupoRequisicaoMudancaDTO>) grupoRequisicaoMudancaDAO.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setListGrupoRequisicaoMudancaDTO(colGrupoRequisicaoMudanca);
		

		List<RequisicaoMudancaRiscoDTO> colMudancaRiscoDTOs = new ArrayList<RequisicaoMudancaRiscoDTO>();
		RequisicaoMudancaRiscoDao requisicaoMudancaRiscoDao = new RequisicaoMudancaRiscoDao();
		colMudancaRiscoDTOs =  (List<RequisicaoMudancaRiscoDTO>) requisicaoMudancaRiscoDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setListRequisicaoMudancaRiscoDTO(colMudancaRiscoDTOs);
		
		List<AprovacaoMudancaDTO> aprovacaoMudancaDTOs = new ArrayList<AprovacaoMudancaDTO>();
		AprovacaoMudancaDao aprovacaoMudancaDao = new AprovacaoMudancaDao();
		aprovacaoMudancaDTOs =   (List<AprovacaoMudancaDTO>) aprovacaoMudancaDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setListAprovacaoMudancaDTO(aprovacaoMudancaDTOs) ;
		
		List<LiberacaoMudancaDTO> liberacaoMudancaDTOs = new ArrayList<LiberacaoMudancaDTO>();
		LiberacaoMudancaDao liberacaoMudancaDao = new LiberacaoMudancaDao();
		liberacaoMudancaDTOs = (List<LiberacaoMudancaDTO>) liberacaoMudancaDao.listByIdHistoricoMudanca2(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setListLiberacaoMudancaDTO(liberacaoMudancaDTOs);
		
		List<RequisicaoMudancaServicoDTO> requisicaoMudancaServicoDTOs = new ArrayList<RequisicaoMudancaServicoDTO>();
		RequisicaoMudancaServicoDao requisicaoMudancaServicoDao = new RequisicaoMudancaServicoDao();
		requisicaoMudancaServicoDTOs =  (List<RequisicaoMudancaServicoDTO>) requisicaoMudancaServicoDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setListRequisicaoMudancaServicoDTO(requisicaoMudancaServicoDTOs);
		
		List<SolicitacaoServicoMudancaDTO> solicitacaoServicoMudancaDTOs = new ArrayList<SolicitacaoServicoMudancaDTO>();
		List<SolicitacaoServicoDTO> solicitacaoServicos = new ArrayList<SolicitacaoServicoDTO>();
		SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();
		SolicitacaoServicoMudancaDao solicitacaoServicoMudancaDao = new SolicitacaoServicoMudancaDao();
		solicitacaoServicoMudancaDTOs =  (List<SolicitacaoServicoMudancaDTO>) solicitacaoServicoMudancaDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		for (SolicitacaoServicoMudancaDTO solicitacaoServicoMudancaDTO : solicitacaoServicoMudancaDTOs) {
			solicitacaoServicoDTO.setIdRequisicaoMudanca(solicitacaoServicoMudancaDTO.getIdRequisicaoMudanca());
			solicitacaoServicoDTO.setIdSolicitacaoServico(solicitacaoServicoMudancaDTO.getIdSolicitacaoServico());
			solicitacaoServicos.add(solicitacaoServicoDTO);
			solicitacaoServicoDTO = new SolicitacaoServicoDTO();
		}
		requisicaoMudancaDTOAux.setListIdSolicitacaoServico(solicitacaoServicos);
		
		
		List<RequisicaoMudancaResponsavelDTO> respMudancaDTOs = new ArrayList<RequisicaoMudancaResponsavelDTO>();
		RequisicaoMudancaResponsavelDao respMudancaDao = new RequisicaoMudancaResponsavelDao();
		respMudancaDTOs =   (List<RequisicaoMudancaResponsavelDTO>) respMudancaDao.listByIdHistoricoMudanca(historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setColResponsaveis(respMudancaDTOs);
		
		
		// esse bloco preenche os anexos do historico
		Collection<UploadDTO> listuploadDTO = new ArrayList<UploadDTO>();
		ControleGEDDao controleGEDDao = new ControleGEDDao();
		listuploadDTO = controleGEDDao.listByIdTabelaAndIdHistorico(ControleGEDDTO.TABELA_REQUISICAOLIBERACAO, historicoMudancaDTO.getIdHistoricoMudanca());
		requisicaoMudancaDTOAux.setColArquivosUpload(listuploadDTO);

		
		

		UsuarioDTO usr = new UsuarioDTO();
		usr.setIdUsuario(historicoMudancaDTO.getIdProprietario());
		requisicaoMudancaDTOAux.setUsuarioDto(usr);
		requisicaoLiberacaoService.update(requisicaoMudancaDTOAux, request);
		
		document.setBean(requisicaoMudancaDTOAux);
		
			
		document.executeScript("uploadAnexos.refresh()");
		this.load(document, request, response);
		
		String comando = "mostraMensagemRestaurarBaseline('" + UtilI18N.internacionaliza(request, "MSG15") + ".<br>" + UtilI18N.internacionaliza(request, "requisicaoLiberacao.requisicaoLiberacao") + " <b><u>"
				+ requisicaoMudancaDTO.getIdRequisicaoMudanca() + "</u></b> " + UtilI18N.internacionaliza(request, "citcorpore.comum.restaurada") + ".<br><br>"
				+ "Versão: " + UtilStrings.nullToVazio(requisicaoMudancaDTOAux.getIdRequisicaoMudanca().toString()) + "<br>";
		comando = comando + "')";
		
		document.executeScript(comando);
		

	}
	
	public void verificarItensRelacionados(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		RequisicaoMudancaDTO RequisicaoMudancaDTO = (RequisicaoMudancaDTO) document.getBean();	
		RequisicaoMudancaService requisicaoMudancaService  = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, WebUtil.getUsuarioSistema(request));
		
		ArrayList<SolicitacaoServicoDTO> listIdSolicitacaoServico = (ArrayList<SolicitacaoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(SolicitacaoServicoDTO.class, "solicitacaoServicoSerializado", request);
		ArrayList<RequisicaoMudancaItemConfiguracaoDTO> listRequisicaoMudancaItemConfiguracaoDTO = (ArrayList<RequisicaoMudancaItemConfiguracaoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoMudancaItemConfiguracaoDTO.class, "itensConfiguracaoRelacionadosSerializado", request);
		ArrayList<ProblemaMudancaDTO> listProblemaMudancaDTO = (ArrayList<ProblemaMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProblemaMudancaDTO.class, "problemaSerializado", request);
		
		boolean existeItensRelaiconados = false;
		
		if(listIdSolicitacaoServico != null && listIdSolicitacaoServico.size() > 0){
			existeItensRelaiconados = true;
		}else if(listRequisicaoMudancaItemConfiguracaoDTO != null && listRequisicaoMudancaItemConfiguracaoDTO.size() > 0){
			existeItensRelaiconados = true;
		}else if(listProblemaMudancaDTO != null && listProblemaMudancaDTO.size() > 0){
			existeItensRelaiconados = true;
		}/*else{
			existeItensRelaiconados  = requisicaoMudancaService.verificarItensRelacionados(RequisicaoMudancaDTO);
		}*/
		
		if(existeItensRelaiconados){
			document.executeScript("verificarItensRelacionados(false)");
		}else{
			document.executeScript("gravar()");
			
		}
	}
	
	public void adicionaTabelaLOOKUP_PROBLEMA(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)throws Exception{
		ProblemaDAO problemaDao = new ProblemaDAO();
		RequisicaoMudancaDTO requisicaoMudancaDTO = (RequisicaoMudancaDTO) document.getBean();	
		ProblemaService problemaService  = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, WebUtil.getUsuarioSistema(request));
		
		requisicaoMudancaDTO.setColAllLOOKUP_PROBLEMA(requisicaoMudancaDTO.getColAllLOOKUP_PROBLEMA());
		requisicaoMudancaDTO.setColProblemaCheckado(requisicaoMudancaDTO.getColAllLOOKUP_PROBLEMA());
		//Declarando arrays de strings
		ArrayList<String> listaValoresGrid = new ArrayList<String>();
		ArrayList<String> listaValoresCheckados = new ArrayList<String>();
		
		ArrayList<ProblemaMudancaDTO> listProblemaMudancaDTO = (ArrayList<ProblemaMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProblemaMudancaDTO.class, "problemaSerializado", request);
		
		if (listProblemaMudancaDTO != null){
			for (ProblemaMudancaDTO problemaMudanca : listProblemaMudancaDTO){
				listaValoresGrid.add(problemaMudanca.getIdProblema().toString());
			}
		}
		for (String problemaCheckado : requisicaoMudancaDTO.getColProblemaCheckado()){
			listaValoresCheckados.add(problemaCheckado);
		}
		
		//Neste trecho do codigo adiciona a uma Collection os valores que NAO estão se repetindo entre os dois Arrays
		Set<String> valoresUnicosGrid = new HashSet<String>();
		valoresUnicosGrid.addAll(listaValoresGrid);
		valoresUnicosGrid.addAll(listaValoresCheckados);
		
		//chamando pelo DAO o objeto referente aos ids checkados e setando na collection problemas retorno
		Set<ProblemaDTO> problemasRetorno = new HashSet<ProblemaDTO>();
		ProblemaDTO problema = new ProblemaDTO();
		ProblemaDTO problemaDTO = new ProblemaDTO();
		document.executeScript("tabelaProblema.limpaLista();");
		for (String idProblemaCheckado : valoresUnicosGrid){
			if (idProblemaCheckado != null && !idProblemaCheckado.equals("")){
				Integer idProblemasCheckados = Integer.parseInt(idProblemaCheckado);
				problemaDTO = new ProblemaDTO();
				problemaDTO.setIdProblema(idProblemasCheckados);
				problema = (ProblemaDTO) problemaDao.restore(problemaDTO);
				document.executeScript("addLinhaTabelaProblema('"+problema.getIdProblema()+"', '"+problema.getIdProblema()+"-"+problema.getTitulo()+"-"+problema.getStatus()+"',false)");
			}else {
				continue;
			}
		}
	}
	
}
