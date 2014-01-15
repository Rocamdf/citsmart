package br.com.centralit.citcorpore.ajaxForms;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.htmlparser.jericho.Source;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.html.HTMLSelect;
import br.com.centralit.citajax.html.HTMLTable;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.batch.MonitoraIncidentes;
import br.com.centralit.citcorpore.bean.AcordoNivelServicoDTO;
import br.com.centralit.citcorpore.bean.AcordoServicoContratoDTO;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.ContatoSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.EmailSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO;
import br.com.centralit.citcorpore.bean.EventoMonitoramentoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.ImpactoDTO;
import br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO;
import br.com.centralit.citcorpore.bean.ItemCfgSolicitacaoServDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.bean.OcorrenciaSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.OrigemOcorrenciaDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoEvtMonDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoQuestionarioDTO;
import br.com.centralit.citcorpore.bean.TemplateSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.bean.UrgenciaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.AcordoNivelServicoDao;
import br.com.centralit.citcorpore.integracao.CategoriaOcorrenciaDAO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.centralit.citcorpore.integracao.OrigemOcorrenciaDAO;
import br.com.centralit.citcorpore.negocio.AcordoNivelServicoService;
import br.com.centralit.citcorpore.negocio.AcordoServicoContratoService;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.CategoriaServicoService;
import br.com.centralit.citcorpore.negocio.CategoriaSolucaoService;
import br.com.centralit.citcorpore.negocio.CausaIncidenteService;
import br.com.centralit.citcorpore.negocio.ConhecimentoSolicitacaoService;
import br.com.centralit.citcorpore.negocio.ContatoSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.EmailSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.EventoMonitConhecimentoService;
import br.com.centralit.citcorpore.negocio.EventoMonitoramentoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.ImportanciaConhecimentoGrupoService;
import br.com.centralit.citcorpore.negocio.ItemCfgSolicitacaoServService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.JustificativaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.LocalidadeService;
import br.com.centralit.citcorpore.negocio.LocalidadeUnidadeService;
import br.com.centralit.citcorpore.negocio.OcorrenciaSolicitacaoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PrioridadeSolicitacoesService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoEvtMonService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TemplateSolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.TipoItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UnidadesAccServicosService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.negocio.ValorService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class SolicitacaoServico_OLD extends AjaxFormAction {

	private PrioridadeSolicitacoesService prioridadeSolicitacoesService;
	private String calcularDinamicamente;

	@Override
	public Class getBeanClass() {
		return SolicitacaoServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoexpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		document.executeScript("JANELA_AGUARDE_MENU.show();");
		GrupoService grupoService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		// OrigemAtendimentoService origemService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);

		/*
		 * Verifica se mostra ou não o campo Categoria de Serviço na tela de cadastro de incidentes.
		 */
		String mostraCampoCategoria = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.MOSTRAR_CATEGORIA_SERVICO_EM_INCIDENTE, "S");

		if (mostraCampoCategoria.trim().equals("S")) {
			// document.getElementById("divCategServico").setVisible(true);
		} else {
			// document.getElementById("divCategServico").setVisible(false);
		}

		HTMLSelect urgencia = (HTMLSelect) document.getSelectById("urgencia");
		urgencia.removeAllOptions();
		HTMLSelect impacto = (HTMLSelect) document.getSelectById("impacto");
		impacto.removeAllOptions();

		if (!getCalcularDinamicamente().trim().equalsIgnoreCase("S")) {
			urgencia.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
			urgencia.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
			urgencia.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));

			impacto.addOption("B", UtilI18N.internacionaliza(request, "citcorpore.comum.baixa"));
			impacto.addOption("M", UtilI18N.internacionaliza(request, "citcorpore.comum.media"));
			impacto.addOption("A", UtilI18N.internacionaliza(request, "citcorpore.comum.alta"));
		} else {
			Collection<UrgenciaDTO> urgenciaDTO = getPrioridadeSolicitacoesService().consultaUrgencia();
			for (UrgenciaDTO urgenciaTemp : urgenciaDTO) {
				urgencia.addOption(urgenciaTemp.getSiglaUrgencia().toString(), StringEscapeUtils.escapeJavaScript(urgenciaTemp.getNivelUrgencia()));
			}
			Collection<ImpactoDTO> impactoDTO = getPrioridadeSolicitacoesService().consultaImpacto();
			for (ImpactoDTO impactoTemp : impactoDTO) {
				impacto.addOption(impactoTemp.getSiglaImpacto().toString(), StringEscapeUtils.escapeJavaScript(impactoTemp.getNivelImpacto()));
			}
		}

		Collection<GrupoDTO> lstGrupos = grupoService.getGruposByEmpregado(usuario.getIdEmpregado());

		if (lstGrupos != null) {
			for (GrupoDTO g : lstGrupos) {
				if (g.getAbertura() != null && g.getAbertura().trim().equals("S"))
					document.getElementById("enviaEmailCriacao").setDisabled(true);
				if (g.getEncerramento() != null && g.getEncerramento().trim().equals("S"))
					document.getElementById("enviaEmailFinalizacao").setDisabled(true);
				if (g.getAndamento() != null && g.getAndamento().trim().equals("S"))
					document.getElementById("enviaEmailAcoes").setDisabled(true);
			}
		}

		// request.getSession(true).setAttribute("colUploadsGED", null);
		request.getSession(true).setAttribute("dados_solicit_quest", null);

		this.preencherComboOrigem(document, request, response);

		TipoDemandaServicoService tipoDemandaService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");
		idTipoDemandaServico.removeAllOptions();
		idTipoDemandaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection col = tipoDemandaService.listSolicitacoes();
		if (col != null)
			idTipoDemandaServico.addOptions(col, "idTipoDemandaServico", "nomeTipoDemandaServico", null);

		HTMLSelect idGrupoAtual = (HTMLSelect) document.getSelectById("idGrupoAtual");
		idGrupoAtual.removeAllOptions();
		idGrupoAtual.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupos = grupoSegurancaService.listGruposServiceDesk();
		if (colGrupos != null)
			idGrupoAtual.addOptions(colGrupos, "idGrupo", "nome", null);

		// this.criarComboCategoriaServico(document, request);

		((HTMLSelect) document.getSelectById("idServico")).removeAllOptions();
		((HTMLSelect) document.getSelectById("idContrato")).removeAllOptions();

		CausaIncidenteService causaIncidenteService = (CausaIncidenteService) ServiceLocator.getInstance().getService(CausaIncidenteService.class, null);
		Collection colCausas = causaIncidenteService.listHierarquia();
		HTMLSelect idCausa = (HTMLSelect) document.getSelectById("idCausaIncidente");
		idCausa.removeAllOptions();
		idCausa.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colCausas != null) {
			idCausa.addOptions(colCausas, "idCausaIncidente", "descricaoCausaNivel", null);
		}

		CategoriaSolucaoService categoriaSolucaoService = (CategoriaSolucaoService) ServiceLocator.getInstance().getService(CategoriaSolucaoService.class, null);
		Collection colCategSolucao = categoriaSolucaoService.listHierarquia();
		HTMLSelect idCategoriaSolucao = (HTMLSelect) document.getSelectById("idCategoriaSolucao");
		idCategoriaSolucao.removeAllOptions();
		idCategoriaSolucao.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (colCategSolucao != null) {
			idCategoriaSolucao.addOptions(colCategSolucao, "idCategoriaSolucao", "descricaoCategoriaNivel", null);
		}

		this.preencherComboUnidade(document, request, response);

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		String tarefaAssociada = "N";
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdTarefa() != null) {
			tarefaAssociada = "S";
		}
		request.setAttribute("tarefaAssociada", tarefaAssociada);

		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
			document.getElementById("novaSolicitacao").setValue("false");
			restore(document, request, response);
		} else {
			preparaTelaInclusao(document, request, response);
			document.getElementById("novaSolicitacao").setValue("true");
		}
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar() == null) {
			solicitacaoServicoDto.setEditar("S");
		}
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
			document.getElementById("divBotoes").setVisible(false);
			document.getElementById("divBotaoAddRegExecucao").setVisible(false);
			document.getElementById("botaoSolicitante").setVisible(false);
			document.getForm("form").lockForm();
			document.getElementById("btAnexos").setVisible(true);
			document.getElementById("btnGravarTelaAnexos").setVisible(true);
			document.getElementById("msgGravarDados").setVisible(false);

			/**
			 * Verifica o uso da versão free
			 */
			if (!br.com.citframework.util.Util.isVersionFree(request)) {
				document.executeScript("document.getElementById('imagenIC').style.display = 'none'");
			}

			document.getElementById("btIncidentesRelacionados").setVisible(false);
			document.getElementById("divMessage")
					.setInnerHTML("<font color='red'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.alteracaosolicitacao") + ".</font>");
		} else {
			document.getElementById("msgGravarDados").setVisible(true);
		}

		if (request.getParameter("idSolicitacaoRelacionada") != null && !request.getParameter("idSolicitacaoRelacionada").equalsIgnoreCase("")) {
			Integer idSolicitacaoRelacionada = Integer.parseInt(request.getParameter("idSolicitacaoRelacionada"));
			Integer idContrato = null;
			SolicitacaoServicoDTO solicitacaoServico = new SolicitacaoServicoDTO();
			SolicitacaoServicoDTO solicitacaoServicoInformacoesContato = new SolicitacaoServicoDTO();
			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
			if (request.getParameter("idContrato") != null && !request.getParameter("idContrato").equalsIgnoreCase("")) {
				idContrato = Integer.parseInt(request.getParameter("idContrato"));

				if (idContrato != null) {
					solicitacaoServico.setIdContrato(idContrato);
				}
			}
			// onchange="setaValorLookup(this);">
			solicitacaoServico.setIdSolicitacaoRelacionada(idSolicitacaoRelacionada);
			SolicitacaoServicoMultiContratos sol = new SolicitacaoServicoMultiContratos();
			this.verificaGrupoExecutor(document, request, response);
			this.verificaImpactoUrgencia(document, request, response);
			//sol.carregaServicosMulti(document, request, response);
			sol.carregaUnidade(document, request, response);
			if (idContrato != null) {
				document.executeScript("adicionaValoLookup(" + idContrato + ")");
			}

			solicitacaoServico = (SolicitacaoServicoDTO) solicitacaoServicoService.restoreAll(idSolicitacaoRelacionada);
			if (solicitacaoServico != null) {
				solicitacaoServicoInformacoesContato.setIdSolicitante(solicitacaoServico.getIdSolicitante());
				solicitacaoServicoInformacoesContato.setSolicitante(solicitacaoServico.getSolicitante());
				solicitacaoServicoInformacoesContato.setNomecontato(solicitacaoServico.getNomecontato());
				solicitacaoServicoInformacoesContato.setTelefonecontato(solicitacaoServico.getTelefonecontato());
				solicitacaoServicoInformacoesContato.setEmailcontato(solicitacaoServico.getEmailcontato());
				solicitacaoServicoInformacoesContato.setRamal(solicitacaoServico.getRamal());
				solicitacaoServicoInformacoesContato.setObservacao(solicitacaoServico.getObservacao());
				solicitacaoServicoInformacoesContato.setIdUnidade(solicitacaoServico.getIdUnidade());
				solicitacaoServicoInformacoesContato.setIdSolicitacaoRelacionada(idSolicitacaoRelacionada);

				this.preencherComboLocalidade(document, request, response);
			}

			document.getForm("form").setValues(solicitacaoServicoInformacoesContato);
		}
		document.executeScript("JANELA_AGUARDE_MENU.hide();");

		solicitacaoServicoDto = null;
	}

	/**
	 * Preenche a combo Unidade.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preencherComboUnidade(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");

		if (PAGE_CADADTRO_SOLICITACAOSERVICO.equalsIgnoreCase("pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load")) {

			SolicitacaoServicoMultiContratos solicitacaoServicoMultiContratos = new SolicitacaoServicoMultiContratos();

			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

			if (solicitacaoServicoDto.getIdSolicitacaoServico() != null && solicitacaoServicoDto.getIdSolicitacaoServico().intValue() > 0) {

				solicitacaoServicoMultiContratos.carregaUnidade(document, request, response);
			}

			solicitacaoServicoDto = null;

		} else {
			UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
			HTMLSelect comboUnidade = (HTMLSelect) document.getSelectById("idUnidade");
			ArrayList<UnidadeDTO> unidades = (ArrayList) unidadeService.listHierarquia();

			inicializarCombo(comboUnidade, request);
			if (unidades != null) {
				for (UnidadeDTO unidade : unidades)
					if (unidade.getDataFim() == null)
						comboUnidade.addOption(unidade.getIdUnidade().toString(), StringEscapeUtils.escapeJavaScript(unidade.getNomeNivel()));
			}
		}
	}

	public void preencherComboOrigem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		HTMLSelect selectOrigem = (HTMLSelect) document.getSelectById("idOrigem");
		selectOrigem.removeAllOptions();
		ArrayList<OrigemAtendimentoDTO> todasOrigens = (ArrayList) origemAtendimentoService.list();
		ArrayList<OrigemAtendimentoDTO> origensNaoExcluidas = new ArrayList<OrigemAtendimentoDTO>();
		String origemPadrao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.ORIGEM_PADRAO_SOLICITACAO, "");

		selectOrigem.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		if (todasOrigens != null) {
			for (OrigemAtendimentoDTO origemAtendimento : todasOrigens) {
				if (origemAtendimento.getDataFim() == null) {
					origensNaoExcluidas.add(origemAtendimento);
				}
			}
			selectOrigem.addOptions(origensNaoExcluidas, "idOrigem", "descricao", origemPadrao);
		}
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
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		LocalidadeUnidadeService localidadeUnidadeService = (LocalidadeUnidadeService) ServiceLocator.getInstance().getService(LocalidadeUnidadeService.class, null);

		LocalidadeService localidadeService = (LocalidadeService) ServiceLocator.getInstance().getService(LocalidadeService.class, null);

		LocalidadeDTO localidadeDto = new LocalidadeDTO();

		Collection<LocalidadeUnidadeDTO> listaIdlocalidadePorUnidade = null;

		Collection<LocalidadeDTO> listaIdlocalidade = null;

		String TIRAR_VINCULO_LOCALIDADE_UNIDADE = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.TIRAR_VINCULO_LOCALIDADE_UNIDADE, "N");

		HTMLSelect comboLocalidade = (HTMLSelect) document.getSelectById("idLocalidade");
		comboLocalidade.removeAllOptions();
		if (TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("N") || TIRAR_VINCULO_LOCALIDADE_UNIDADE.trim().equalsIgnoreCase("")) {
			if (solicitacaoServicoDto.getIdUnidade() != null) {
				listaIdlocalidadePorUnidade = (ArrayList) localidadeUnidadeService.listaIdLocalidades(solicitacaoServicoDto.getIdUnidade());
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
		solicitacaoServicoDto = null;
	}

	/**
	 * Cria Combo de Categoria Serviço Ativas.
	 * 
	 * @param document
	 * @throws ServiceException
	 * @throws Exception
	 * @throws LogicException
	 * @throws RemoteException
	 */
	@SuppressWarnings("unused")
	private void criarComboCategoriaServico(DocumentHTML document, HttpServletRequest request) throws ServiceException, Exception {
		CategoriaServicoService categoriaService = (CategoriaServicoService) ServiceLocator.getInstance().getService(CategoriaServicoService.class, null);
		HTMLSelect idCategoriaServico = (HTMLSelect) document.getSelectById("idCategoriaServico");
		idCategoriaServico.removeAllOptions();
		idCategoriaServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		Collection listaDeCategoriasAtivas = categoriaService.listHierarquia();
		if (listaDeCategoriasAtivas != null) {
			idCategoriaServico.addOptions(listaDeCategoriasAtivas, "idCategoriaServico", "nomeCategoriaServicoNivel", null);
		}
	}

	private void preparaTelaInclusao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// document.getElementById("btOcorrencias").setVisible(false);
		// document.getElementById("btIncidentesRelacionados").setVisible(false);
	}

	@SuppressWarnings("unused")
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		// request.getSession(true).setAttribute("colUploadsGED", null);

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		ContatoSolicitacaoServicoService contatoSolicitacaoServicoService = (ContatoSolicitacaoServicoService) ServiceLocator.getInstance().getService(ContatoSolicitacaoServicoService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		SolicitacaoServicoEvtMonService solicitacaoServicoEvtMonService = (SolicitacaoServicoEvtMonService) ServiceLocator.getInstance().getService(SolicitacaoServicoEvtMonService.class,
				WebUtil.getUsuarioSistema(request));
		EventoMonitoramentoService eventoMonitoramentoService = (EventoMonitoramentoService) ServiceLocator.getInstance().getService(EventoMonitoramentoService.class,
				WebUtil.getUsuarioSistema(request));
		EventoMonitConhecimentoService eventoMonitConhecimentoService = (EventoMonitConhecimentoService) ServiceLocator.getInstance().getService(EventoMonitConhecimentoService.class,
				WebUtil.getUsuarioSistema(request));
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, WebUtil.getUsuarioSistema(request));

		Integer idTarefa = solicitacaoServicoDto.getIdTarefa();
		String acaoFluxo = solicitacaoServicoDto.getAcaoFluxo();
		String reclassificar = solicitacaoServicoDto.getReclassificar();
		String escalar = solicitacaoServicoDto.getEscalar();
		String alterarSituacao = solicitacaoServicoDto.getAlterarSituacao();
		String validaBaseConhecimento = solicitacaoServicoDto.getValidaBaseConhecimento();

		solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (solicitacaoServicoDto == null) {
			document.alert(UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.registronaoencotrado"));
			return;
		}
		solicitacaoServicoDto.setIdTarefa(idTarefa);
		solicitacaoServicoDto.setAcaoFluxo(acaoFluxo);
		solicitacaoServicoDto.setReclassificar(reclassificar);
		solicitacaoServicoDto.setEscalar(escalar);
		solicitacaoServicoDto.setAlterarSituacao(alterarSituacao);
		solicitacaoServicoDto.setValidaBaseConhecimento(validaBaseConhecimento);

		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		if (solicitacaoServicoDto.getIdSolicitante() != null) {
			empregadoDTO.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
			empregadoDTO = (EmpregadoDTO) empregadoService.restore(empregadoDTO);
		}
		ServicoContratoDTO servicoContratoDTO = new ServicoContratoDTO();
		servicoContratoDTO.setIdServicoContrato(solicitacaoServicoDto.getIdServicoContrato());
		if (solicitacaoServicoDto.getIdServicoContrato() != null) {
			servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.restore(servicoContratoDTO);
		} else {
			servicoContratoDTO = null;
		}
		if (servicoContratoDTO != null) {
			solicitacaoServicoDto.setIdServico(servicoContratoDTO.getIdServico());
			solicitacaoServicoDto.setIdContrato(servicoContratoDTO.getIdContrato());
			ServicoDTO servicoDto = new ServicoDTO();
			servicoDto.setIdServico(servicoContratoDTO.getIdServico());
			servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
			if (servicoDto != null) {
				solicitacaoServicoDto.setIdCategoriaServico(servicoDto.getIdCategoriaServico());
				document.setBean(solicitacaoServicoDto);
				carregaServicos(document, request, response);
				carregaContratos(document, request, response);
				carregaBaseConhecimentoAssoc(document, request, response);
			}
		}

		ContatoSolicitacaoServicoDTO contatoSolicitacaoServicoDTO = null;
		if (solicitacaoServicoDto.getIdContatoSolicitacaoServico() != null) {
			contatoSolicitacaoServicoDTO = new ContatoSolicitacaoServicoDTO();
			contatoSolicitacaoServicoDTO.setIdcontatosolicitacaoservico(solicitacaoServicoDto.getIdContatoSolicitacaoServico());
			contatoSolicitacaoServicoDTO = (ContatoSolicitacaoServicoDTO) contatoSolicitacaoServicoService.restore(contatoSolicitacaoServicoDTO);
		}

		if (contatoSolicitacaoServicoDTO != null) {
			solicitacaoServicoDto.setNomecontato(contatoSolicitacaoServicoDTO.getNomecontato());
			solicitacaoServicoDto.setEmailcontato(contatoSolicitacaoServicoDTO.getEmailcontato());
			solicitacaoServicoDto.setTelefonecontato(contatoSolicitacaoServicoDTO.getTelefonecontato());
			solicitacaoServicoDto.setRamal(contatoSolicitacaoServicoDTO.getRamal());
			solicitacaoServicoDto.setObservacao(contatoSolicitacaoServicoDTO.getObservacao());
			solicitacaoServicoDto.setIdLocalidade(contatoSolicitacaoServicoDTO.getIdLocalidade());
			this.preencherComboLocalidade(document, request, response);
		}

		ItemConfiguracaoDTO itemConfiguracaoDTO = null;
		ItemConfiguracaoDTO itemConfiguracaoFilhoDTO = null;
		String tagItemCfg = "";
		if (solicitacaoServicoDto.getIdItemConfiguracao() != null) {
			ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			itemConfiguracaoDTO = new ItemConfiguracaoDTO();
			itemConfiguracaoDTO.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
			itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);

			if (solicitacaoServicoDto.getIdItemConfiguracaoFilho() != null) {
				itemConfiguracaoFilhoDTO = new ItemConfiguracaoDTO();
				itemConfiguracaoFilhoDTO.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracaoFilho());
				itemConfiguracaoFilhoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoFilhoDTO);
				if (itemConfiguracaoFilhoDTO != null && itemConfiguracaoFilhoDTO.getIdTipoItemConfiguracao() != null) {
					TipoItemConfiguracaoService tipoItemConfiguracaoService = (TipoItemConfiguracaoService) ServiceLocator.getInstance().getService(TipoItemConfiguracaoService.class, null);
					TipoItemConfiguracaoDTO tipoItemConfiguracaoDTO = new TipoItemConfiguracaoDTO();
					tipoItemConfiguracaoDTO.setId(itemConfiguracaoFilhoDTO.getIdTipoItemConfiguracao());
					tipoItemConfiguracaoDTO = (TipoItemConfiguracaoDTO) tipoItemConfiguracaoService.restore(tipoItemConfiguracaoDTO);
					if (tipoItemConfiguracaoDTO != null) {
						tagItemCfg = tipoItemConfiguracaoDTO.getTag();
					}
				}
			}
		}

		HTMLForm form = document.getForm("form");
		form.clear();

		if (itemConfiguracaoDTO != null) {
			document.getTextBoxById("itemConfiguracao").setValue(itemConfiguracaoDTO.getIdentificacao());
			/* document.executeScript("exibeCampos()"); */
			document.setBean(solicitacaoServicoDto);
			if (tagItemCfg != null && tagItemCfg.equalsIgnoreCase("SOFTWARES")) {
				document.executeScript("document.form.caracteristica[0].checked = true");
				preecherComboSoftware(document, request, response);
			} else {
				document.executeScript("document.form.caracteristica[1].checked = true");
				preecherComboHardware(document, request, response);
			}
		}

		form.setValues(solicitacaoServicoDto);

		if (solicitacaoServicoDto.getEditar() == null) {
			solicitacaoServicoDto.setEditar("S");
		}

		if (empregadoDTO != null) {
			document.getTextBoxById("solicitante").setValue(empregadoDTO.getNome());
		}

		document.executeScript("setValueToDataEditor()");
		document.getElementById("divMails").setVisible(false);

		if (solicitacaoServicoDto.getReclassificar() != null && solicitacaoServicoDto.getReclassificar().equalsIgnoreCase("S")) {
			document.getElementById("divMessage").setInnerHTML("<font color='red'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.reclassificacao") + ".</font>");
			document.getSelectById("btnLimpar").setVisible(false);
		} else {
			document.getSelectById("idCategoriaServico").setDisabled(true);
			document.getSelectById("idContrato").setDisabled(true);
			document.getSelectById("idServico").setDisabled(true);
			document.getSelectById("idTipoDemandaServico").setDisabled(true);
			document.getSelectById("urgencia").setDisabled(true);
			document.getSelectById("impacto").setDisabled(true);
			if (solicitacaoServicoDto.getEditar() == null) {
				solicitacaoServicoDto.setEditar("S");
			}
			if (solicitacaoServicoDto.getEditar().equalsIgnoreCase("N")) {
				document.getElementById("divMessage").setInnerHTML(
						"<font color='red'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.aleracaosolicitacao") + ".</font>");
			} else {
				document.getElementById("divMessage").setInnerHTML(
						"<font color='red'>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacaoServico.msg.aleracaoclassificacao") + ".</font>");
			}
		}

		boolean bEscalar = solicitacaoServicoDto.getEscalar() != null && solicitacaoServicoDto.getEscalar().equalsIgnoreCase("S");
		boolean bAlterarSituacao = solicitacaoServicoDto.getAlterarSituacao() != null && solicitacaoServicoDto.getAlterarSituacao().equalsIgnoreCase("S");
		if (!bAlterarSituacao) {
			document.executeScript("document.form.situacao[0].disabled = true;");
			document.executeScript("document.form.situacao[1].disabled = true;");
			document.executeScript("document.form.situacao[2].disabled = true;");
		}

		if (!bEscalar) {
			document.getSelectById("idGrupoAtual").setDisabled(true);
		}

		document.getElementById("divMessage").setVisible(true);

		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, solicitacaoServicoDto.getIdSolicitacaoServico());
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);

		OcorrenciaSolicitacaoService ocorrenciaSolicitacaoService = (OcorrenciaSolicitacaoService) ServiceLocator.getInstance().getService(OcorrenciaSolicitacaoService.class, null);
		Collection colOcorrencias = ocorrenciaSolicitacaoService.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (colOcorrencias != null && colOcorrencias.size() > 0) {
			String str = listInfoRegExecucaoSolicitacao(colOcorrencias, request);
			request.setAttribute("strRegistrosExecucao", str);
		}

		carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

		/**
		 * Verifica o uso da versão free
		 */
		if (!br.com.citframework.util.Util.isVersionFree(request)) {

			HTMLTable tblProblema = document.getTableById("tblProblema");
			tblProblema.deleteAllRows();

			if (solicitacaoServicoDto != null) {
				ProblemaDTO problemadto = new ProblemaDTO();
				problemadto.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				Collection col = this.getProblemaService().findByIdSolictacaoServico(problemadto.getIdSolicitacaoServico());
				if (col != null) {
					tblProblema.addRowsByCollection(col, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idProblema" }, "Problema já cadastrado!!",
							new String[] { "exibeIconesProblema" }, "buscaProblema", null);
					document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
				}
			}

			HTMLTable tblMudanca = document.getTableById("tblMudanca");
			tblMudanca.deleteAllRows();

			if (solicitacaoServicoDto != null) {
				RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();
				requisicaoMudancaDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
				Collection col = this.getRequisicaoMudancaService().findBySolictacaoServico(requisicaoMudancaDTO);
				if (col != null) {
					tblMudanca.addRowsByCollection(col, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idRequisicaoMudanca" }, "Mudança já cadastrado!!",
							new String[] { "exibeIconesMudanca" }, "abreMudanca", null);
					document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");
				}
			}

			HTMLTable tblBaseConhecimento = document.getTableById("tblBaseConhecimento");
			tblBaseConhecimento.deleteAllRows();
			ConhecimentoSolicitacaoDTO conhecimentoSolicitacaoDTO = new ConhecimentoSolicitacaoDTO();
			conhecimentoSolicitacaoDTO.setIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());
			Collection colConhecimentoSolicitacao = this.getConhecimentoSolicitacaoService().findBySolictacaoServico(conhecimentoSolicitacaoDTO);

			if (colConhecimentoSolicitacao != null) {
				tblBaseConhecimento.addRowsByCollection(colConhecimentoSolicitacao, new String[] { "", "", "idBaseConhecimento", "titulo" }, new String[] {},
						UtilI18N.internacionalizaString("baseConhecimento.baseConhecimentoJaCadastrada", request), new String[] { "exibeIconesBaseConhecimento" }, null, null);
				document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblBaseConhecimento', 'tblBaseConhecimento');");
			}

			HTMLTable tblIC = document.getTableById("tblIC");
			tblIC.deleteAllRows();

			if (solicitacaoServicoDto != null) {
				ItemCfgSolicitacaoServDTO itemCfgSolicitacaoServDTO = new ItemCfgSolicitacaoServDTO();
				ItemCfgSolicitacaoServService serviceItem = (ItemCfgSolicitacaoServService) ServiceLocator.getInstance().getService(ItemCfgSolicitacaoServService.class, null);
				ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
				Collection col = serviceItem.findByIdSolicitacaoServico(solicitacaoServicoDto.getIdSolicitacaoServico());

				if (col != null) {
					for (Iterator it = col.iterator(); it.hasNext();) {
						ItemCfgSolicitacaoServDTO itemCfgSolicitacaoServAux = (ItemCfgSolicitacaoServDTO) it.next();
						ItemConfiguracaoDTO itemConfiguracaoAux = new ItemConfiguracaoDTO();
						itemConfiguracaoAux.setIdItemConfiguracao(itemCfgSolicitacaoServAux.getIdItemConfiguracao());
						itemConfiguracaoAux = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoAux);
						if (itemConfiguracaoAux != null) {

							String impactoUrgencia = "";
							String impacto = "";
							String urgencia = "";

							if (itemConfiguracaoAux.getImpacto() != null) {

								if ("B".equalsIgnoreCase(itemConfiguracaoAux.getImpacto())) {
									impacto = UtilI18N.internacionaliza(request, "liberacao.riscoBaixo");
								}
								if ("M".equalsIgnoreCase(itemConfiguracaoAux.getImpacto())) {
									impacto = UtilI18N.internacionaliza(request, "liberacao.riscoMedio");
								}
								if ("A".equalsIgnoreCase(itemConfiguracaoAux.getImpacto()) || "A".equalsIgnoreCase(itemConfiguracaoAux.getImpacto())) {
									impacto = UtilI18N.internacionaliza(request, "liberacao.riscoAlto");
								}

								impactoUrgencia += "(" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.impacto") + ": " + impacto + ")";
							}
							if (itemConfiguracaoAux.getUrgencia() != null) {

								if ("B".equalsIgnoreCase(itemConfiguracaoAux.getUrgencia())) {
									urgencia = UtilI18N.internacionaliza(request, "liberacao.riscoBaixo");
								}
								if ("M".equalsIgnoreCase(itemConfiguracaoAux.getUrgencia())) {
									urgencia = UtilI18N.internacionaliza(request, "liberacao.riscoMedio");
								}
								if ("A".equalsIgnoreCase(itemConfiguracaoAux.getUrgencia()) || "H".equalsIgnoreCase(itemConfiguracaoAux.getUrgencia())) {
									urgencia = UtilI18N.internacionaliza(request, "liberacao.riscoAlto");
								}

								impactoUrgencia += "(" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.urgencia") + ": " + urgencia + ")";
							}

							itemConfiguracaoAux.setImpactoUrgencia(impactoUrgencia);

							itemCfgSolicitacaoServAux.setIdentificacaoStatus(itemConfiguracaoAux.getIdentificacaoStatus());
						}
					}
				}

				if (col != null) {
					tblIC.addRowsByCollection(col, new String[] { "", "idItemConfiguracao", "identificacaoStatus", "" }, new String[] { "idItemConfiguracao" }, "Item Configuração já cadastrado!!",
							new String[] { "exibeIconesIC" }, null, null);

					document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");
				}
			}
		}

		this.abrirListaDeSubSolicitacoes(document, request, response);

		StringBuilder strEventos = new StringBuilder();
		Collection colEventsSolic = solicitacaoServicoEvtMonService.findByIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());
		if (colEventsSolic != null && colEventsSolic.size() > 0) {
			strEventos.append("<table border='1' width='100%'>");
			for (Iterator it = colEventsSolic.iterator(); it.hasNext();) {
				SolicitacaoServicoEvtMonDTO solicitacaoServicoEvtMonDTO = (SolicitacaoServicoEvtMonDTO) it.next();
				EventoMonitoramentoDTO eventoMonitoramentoDto = new EventoMonitoramentoDTO();
				eventoMonitoramentoDto.setIdEventoMonitoramento(solicitacaoServicoEvtMonDTO.getIdEventoMonitoramento());
				eventoMonitoramentoDto = (EventoMonitoramentoDTO) eventoMonitoramentoService.restore(eventoMonitoramentoDto);
				if (eventoMonitoramentoDto != null) {
					Collection<EventoMonitConhecimentoDTO> colEventos = eventoMonitConhecimentoService.listByIdEventoMonitoramento(eventoMonitoramentoDto.getIdEventoMonitoramento());
					Integer[] ids = null;
					if (colEventos != null && colEventos.size() > 0) {
						ids = new Integer[colEventos.size()];
						int x = 0;
						for (Iterator itEvtBC = colEventos.iterator(); itEvtBC.hasNext();) {
							EventoMonitConhecimentoDTO eventoMonitConhecimentoDTO = (EventoMonitConhecimentoDTO) itEvtBC.next();
							ids[x] = eventoMonitConhecimentoDTO.getIdBaseConhecimento();
							x++;
						}
					}

					Collection colBasesConhec = baseConhecimentoService.listarBaseConhecimentoByIds(ids);

					StringBuilder strBC = new StringBuilder();

					if (colBasesConhec != null && colBasesConhec.size() > 0) {
						strBC.append("<table width='100%'>");
						for (Iterator itBC = colBasesConhec.iterator(); itBC.hasNext();) {
							BaseConhecimentoDTO baseConhecimentoDto = (BaseConhecimentoDTO) itBC.next();
							String onclickStr = "onclick='abreConhecimento(\"" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
									+ br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/pages\", \"idBaseConhecimento=" + baseConhecimentoDto.getIdBaseConhecimento() + "\")'";
							strBC.append("<tr>");
							strBC.append("<td>");
							strBC.append("<img style='cursor:pointer' src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")
									+ br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/script.png' border='0' " + onclickStr + "/>");
							strBC.append("</td>");
							strBC.append("<td style='cursor:pointer' " + onclickStr + ">");
							strBC.append("" + UtilStrings.retiraAspasApostrofe(baseConhecimentoDto.getTitulo()));
							strBC.append("</td>");
							strBC.append("</tr>");
						}
						strBC.append("</table>");
					}

					strEventos.append("<tr>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append("<img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
							+ "/imagens/relampago.png' border='0'/>");
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(UtilStrings.retiraAspasApostrofe(eventoMonitoramentoDto.getNomeEvento()));
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(UtilStrings.retiraAspasApostrofe(UtilStrings.nullToVazio(solicitacaoServicoEvtMonDTO.getNomeHost())));
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(UtilStrings.retiraAspasApostrofe(UtilStrings.nullToVazio(solicitacaoServicoEvtMonDTO.getNomeService())));
					strEventos.append("</td>");
					strEventos.append("<td style='background-color:yellow; border:1px solid red'>");
					strEventos.append(strBC.toString());
					strEventos.append("</td>");
					strEventos.append("</tr>");
				}
			}
			strEventos.append("</table>");

			document.getElementById("divEvtMonitoramento").setInnerHTML(strEventos.toString());
			document.getElementById("divEvtMonitoramento").setVisible(true);
		}

		document.executeScript("informaNumeroSolicitacao('" + solicitacaoServicoDto.getIdSolicitacaoServico() + "')");

		if (solicitacaoServicoDto.getIdTarefa() != null) {
			ItemTrabalho itemTrabalho = solicitacaoServicoService.getItemTrabalho(solicitacaoServicoDto.getIdTarefa());
			if (itemTrabalho != null) {
				String strTexto = "";
				strTexto = UtilI18N.internacionaliza(request, "solicitacaoServico.tarefaatual.desc") + ": ";
				strTexto += "<u>" + itemTrabalho.getElementoFluxoDto().getNome() + "</u>";
				if (itemTrabalho.getItemTrabalhoDto() != null) {
					if (itemTrabalho.getItemTrabalhoDto().getIdResponsavelAtual() != null) {
						UsuarioDTO usuarioDto = new UsuarioDTO();
						usuarioDto.setIdUsuario(itemTrabalho.getItemTrabalhoDto().getIdResponsavelAtual());
						usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
						if (usuarioDto != null) {
							EmpregadoDTO empDto = new EmpregadoDTO();
							empDto.setIdEmpregado(usuarioDto.getIdEmpregado());
							empDto = (EmpregadoDTO) empregadoService.restore(empDto);
							if (empDto != null) {
								strTexto = strTexto + "<br>" + UtilI18N.internacionaliza(request, "solicitacaoServico.responsavelatual.desc") + ": <u><span style='background-color:yellow'>"
										+ empDto.getNome() + "</span></u>";
							}
						}
					}
				}
				strTexto = strTexto.replaceAll("\n", "");
				strTexto = strTexto.replaceAll("\r", "");
				strTexto = UtilStrings.retiraAspasApostrofe(strTexto);
				strTexto = StringEscapeUtils.escapeJavaScript(strTexto);
				document.executeScript("informaNomeTarefa('" + strTexto + "')");
			}
		}

		if(solicitacaoServicoDto.getTituloBaseConhecimento() != null){
			document.getElementById("Inpt_Title_BaseConhecimento").setValue(solicitacaoServicoDto.getTituloBaseConhecimento());
		}
		
		document.setBean(solicitacaoServicoDto); // Isto permite que nas classes herdadas, seja colocado o Bean no document.

		solicitacaoServicoDto = null;
		servicoContratoDTO = null;
	}

	public String listInfoRegExecucaoSolicitacao(Collection col, HttpServletRequest request) throws ServiceException, Exception {
		JustificativaSolicitacaoService justificativaSolicitacaoService = (JustificativaSolicitacaoService) ServiceLocator.getInstance().getService(JustificativaSolicitacaoService.class, null);
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		CategoriaOcorrenciaDAO categoriaOcorrenciaDAO = new CategoriaOcorrenciaDAO();
		OrigemOcorrenciaDAO origemOcorrenciaDAO = new OrigemOcorrenciaDAO();

		CategoriaOcorrenciaDTO categoriaOcorrenciaDTO = new CategoriaOcorrenciaDTO();
		OrigemOcorrenciaDTO origemOcorrenciaDTO = new OrigemOcorrenciaDTO();

		StringBuilder strBuffer = new StringBuilder();
		strBuffer.append("<table width='100%' border='1'>");
		strBuffer.append("<tr>");
		strBuffer.append("<td class='linhaSubtituloGridOcorr'>");
		strBuffer.append(UtilI18N.internacionaliza(request, "citcorpore.comum.datahora"));
		strBuffer.append("</td>");
		strBuffer.append("<td class='linhaSubtituloGridOcorr'>");
		strBuffer.append(UtilI18N.internacionaliza(request, "solicitacaoServico.informacaoexecucao"));
		strBuffer.append("</td>");
		strBuffer.append("</tr>");

		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				OcorrenciaSolicitacaoDTO ocorrenciaSolicitacaoAux = (OcorrenciaSolicitacaoDTO) it.next();

				if (ocorrenciaSolicitacaoAux.getOcorrencia() != null) {
					Source source = new Source(ocorrenciaSolicitacaoAux.getOcorrencia());
					ocorrenciaSolicitacaoAux.setOcorrencia(source.getTextExtractor().toString());
				}

				if (categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != null && categoriaOcorrenciaDTO.getIdCategoriaOcorrencia() != 0) {
					categoriaOcorrenciaDTO.setIdCategoriaOcorrencia(ocorrenciaSolicitacaoAux.getIdCategoriaOcorrencia());
					categoriaOcorrenciaDTO = (CategoriaOcorrenciaDTO) categoriaOcorrenciaDAO.restore(categoriaOcorrenciaDTO);
				}

				if (origemOcorrenciaDTO.getIdOrigemOcorrencia() != null && origemOcorrenciaDTO.getIdOrigemOcorrencia() != 0) {
					origemOcorrenciaDTO.setIdOrigemOcorrencia(ocorrenciaSolicitacaoAux.getIdOrigemOcorrencia());
					origemOcorrenciaDAO.restore(origemOcorrenciaDTO);
				}

				String ocorrencia = UtilStrings.nullToVazio(StringEscapeUtils.unescapeJavaScript(ocorrenciaSolicitacaoAux.getOcorrencia()));
				String descricao = UtilStrings.nullToVazio(StringEscapeUtils.unescapeJavaScript(ocorrenciaSolicitacaoAux.getDescricao()));
				String informacoesContato = UtilStrings.nullToVazio(StringEscapeUtils.unescapeJavaScript(ocorrenciaSolicitacaoAux.getInformacoesContato()));
				ocorrencia = ocorrencia.replaceAll("\"", "");
				descricao = descricao.replaceAll("\"", "");
				informacoesContato = informacoesContato.replaceAll("\"", "");
				ocorrencia = ocorrencia.replaceAll("\n", "<br>");
				descricao = descricao.replaceAll("\n", "<br>");
				informacoesContato = informacoesContato.replaceAll("\n", "<br>");
				ocorrencia = UtilHTML.encodeHTML(ocorrencia.replaceAll("\'", ""));
				descricao = UtilHTML.encodeHTML(descricao.replaceAll("\'", ""));
				informacoesContato = UtilHTML.encodeHTML(informacoesContato.replaceAll("\'", ""));
				strBuffer.append("<tr>");
				strBuffer.append("<td style='border:1px solid black'>");
				strBuffer.append("<b>" + UtilDatas.dateToSTR(ocorrenciaSolicitacaoAux.getDataregistro()) + " - " + ocorrenciaSolicitacaoAux.getHoraregistro());

				String strRegPor = UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getRegistradopor());
				try {
					if (ocorrenciaSolicitacaoAux.getRegistradopor() != null && !ocorrenciaSolicitacaoAux.getRegistradopor().trim().equalsIgnoreCase("Automático")) {
						UsuarioDTO usuarioDto = usuarioService.restoreByLogin(ocorrenciaSolicitacaoAux.getRegistradopor());
						if (usuarioDto != null) {
							EmpregadoDTO empregadoDto = empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
							strRegPor = strRegPor + " - " + empregadoDto.getNome();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				strBuffer.append(" - </b>" + UtilI18N.internacionaliza(request, "ocorrenciaSolicitacao.registradopor") + ": <b>" + strRegPor + "</b>");
				strBuffer.append("</td>");
				strBuffer.append("<td style='border:1px solid black'>");
				strBuffer.append("<b>" + ocorrenciaSolicitacaoAux.getDescricao() + "<br><br></b>");
				strBuffer.append("<b>" + ocorrencia + "<br><br></b>");

				/*
				 * if (ocorrenciaSolicitacaoAux.getCategoria().equalsIgnoreCase( Enumerados.CategoriaOcorrencia.Suspensao.toString() ) || ocorrenciaSolicitacaoAux
				 * .getCategoria().equalsIgnoreCase(Enumerados .CategoriaOcorrencia.MudancaSLA.toString() ) ) { JustificativaSolicitacaoDTO justificativaSolicitacaoDTO = new
				 * JustificativaSolicitacaoDTO(); if (ocorrenciaSolicitacaoAux.getIdJustificativa() != null) { justificativaSolicitacaoDTO .setIdJustificativa(ocorrenciaSolicitacaoAux
				 * .getIdJustificativa() ); justificativaSolicitacaoDTO = (JustificativaSolicitacaoDTO) justificativaSolicitacaoService. restore(justificativaSolicitacaoDTO); if
				 * (justificativaSolicitacaoDTO != null) { strBuffer += UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" +
				 * justificativaSolicitacaoDTO.getDescricaoJustificativa() + "<br><br></b>"; } } if (!UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux
				 * .getComplementoJustificativa()).trim().equalsIgnoreCase("") ) { strBuffer += "<b>" + UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux .getComplementoJustificativa()) +
				 * "<br><br></b>"; } }
				 */
				if (ocorrenciaSolicitacaoAux.getCategoria() != null && !ocorrenciaSolicitacaoAux.getCategoria().equals("")) {
					if (ocorrenciaSolicitacaoAux.getCategoria().trim().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.Suspensao.toString())
							|| ocorrenciaSolicitacaoAux.getCategoria().trim().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.MudancaSLA.toString())
							|| ocorrenciaSolicitacaoAux.getCategoria().trim().equalsIgnoreCase(Enumerados.CategoriaOcorrencia.SuspensaoSLA.toString())) {
						JustificativaSolicitacaoDTO justificativaSolicitacaoDTO = new JustificativaSolicitacaoDTO();
						if (ocorrenciaSolicitacaoAux.getIdJustificativa() != null) {
							justificativaSolicitacaoDTO.setIdJustificativa(ocorrenciaSolicitacaoAux.getIdJustificativa());
							justificativaSolicitacaoDTO = (JustificativaSolicitacaoDTO) justificativaSolicitacaoService.restore(justificativaSolicitacaoDTO);
							if (justificativaSolicitacaoDTO != null) {
								strBuffer.append(UtilI18N.internacionaliza(request, "citcorpore.comum.justificativa") + ": <b>" + justificativaSolicitacaoDTO.getDescricaoJustificativa()
										+ "<br><br></b>");
							}
						}
						if (!UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getComplementoJustificativa()).trim().equalsIgnoreCase("")) {
							strBuffer.append("<b>" + UtilStrings.nullToVazio(ocorrenciaSolicitacaoAux.getComplementoJustificativa()) + "<br><br></b>");
						}
					}
				}
				strBuffer.append("</td>");
				strBuffer.append("</tr>");
			}
		}
		strBuffer.append("</table>");

		categoriaOcorrenciaDTO = null;
		origemOcorrenciaDTO = null;

		return strBuffer.toString();
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		boolean bExisteSolicitacao = false;
		SolicitacaoServicoQuestionarioDTO solicitacaoServicoQuestionarioDto = null;
		try {
			UsuarioDTO usuario = WebUtil.getUsuario(request);
			String CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO, "N");

			if (usuario == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
				document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
				return;
			}

			SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
			bExisteSolicitacao = solicitacaoServicoDto.getIdSolicitacaoServico() != null;

			SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class,
					WebUtil.getUsuarioSistema(request));
			TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class,
					WebUtil.getUsuarioSistema(request));
			TemplateSolicitacaoServicoDTO templateDto = templateService.recuperaTemplateServico(solicitacaoServicoDto);
			if (templateDto != null && templateDto.isQuestionario()) {
				Timestamp ts1 = UtilDatas.getDataHoraAtual();
				double tempo = 0;
				solicitacaoServicoQuestionarioDto = (SolicitacaoServicoQuestionarioDTO) request.getSession().getAttribute("dados_solicit_quest");
				while (solicitacaoServicoQuestionarioDto == null && tempo <= 10000) {
					solicitacaoServicoQuestionarioDto = (SolicitacaoServicoQuestionarioDTO) request.getSession().getAttribute("dados_solicit_quest");
					Timestamp ts2 = UtilDatas.getDataHoraAtual();
					tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
				}
				if (solicitacaoServicoQuestionarioDto == null) {
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.informacoesComplementares"));
					carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
					return;
				}
			}
			solicitacaoServicoService.deserializaInformacoesComplementares(solicitacaoServicoDto, solicitacaoServicoQuestionarioDto);

			// Criado por Bruno.Aquino
			// Cria objeto BaseConhecimento e insere dentro setBaseConhecimento

			BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();
			UsuarioDTO usuarioDto = WebUtil.getUsuario(request);

			String str = request.getParameter("Inpt_Title_BaseConhecimento");
			if (str != null) { //ronnie.lopes - Tratamento dos caracteres especiais
				str = UtilStrings.decodeCaracteresEspeciais(str);
				str = str.trim();
			}
			
			baseConhecimento.setTitulo(str);// pega da tela
			baseConhecimento.setConteudo("Descrição: " + solicitacaoServicoDto.getDescricaoSemFormatacao() + "<br><br>" + "Solução/Resposta: " + solicitacaoServicoDto.getResposta());
			baseConhecimento.setOrigem("5");// Serviço
			baseConhecimento.setDataExpiracao(UtilDatas.getSqlDate(UtilDatas.geraUmAnoSeguinte(UtilDatas.getDataAtual())));
			baseConhecimento.setStatus("N");
			baseConhecimento.setErroConhecido("S");
			baseConhecimento.setSituacao("EAV");
			baseConhecimento.setPrivacidade("C");
			baseConhecimento.setDataInicio(UtilDatas.getDataAtual());
			baseConhecimento.setArquivado("N");
			baseConhecimento.setVersao("1.0");
			baseConhecimento.setIdUsuarioAutor(usuarioDto.getIdUsuario());
			solicitacaoServicoDto.setBeanBaseConhecimento(baseConhecimento);

			if (solicitacaoServicoDto.getIdUnidade() == null) {
				document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.unidadecontato"));
				this.verificaImpactoUrgencia(document, request, response);
				if (solicitacaoServicoQuestionarioDto != null)
					carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
				return;

			}

			if (solicitacaoServicoDto.getEscalar() != null && solicitacaoServicoDto.getEscalar().equalsIgnoreCase("S")) {
				if (solicitacaoServicoDto.getIdGrupoAtual() == null) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.grupoatendimento"));
					this.verificaImpactoUrgencia(document, request, response);
					if (solicitacaoServicoQuestionarioDto != null)
						carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
					return;
				}
			}
			ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
			ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

			if (servicoContratoDto != null) {
				if (solicitacaoServicoDto.getIdGrupoNivel1() == null || solicitacaoServicoDto.getIdGrupoNivel1().intValue() <= 0) {
					Integer idGrupoNivel1 = null;
					if (servicoContratoDto.getIdGrupoNivel1() != null && servicoContratoDto.getIdGrupoNivel1().intValue() > 0) {
						idGrupoNivel1 = servicoContratoDto.getIdGrupoNivel1();
					} else {
						String idGrupoN1 = ParametroUtil.getValor(ParametroSistema.ID_GRUPO_PADRAO_NIVEL1, null, null);
						if (idGrupoN1 != null && !idGrupoN1.trim().equalsIgnoreCase("")) {
							try {
								idGrupoNivel1 = new Integer(idGrupoN1);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					if (idGrupoNivel1 == null || idGrupoNivel1.intValue() <= 0) {
						document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.grupoatendnivel"));
						if (solicitacaoServicoQuestionarioDto != null)
							carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
						return;
					}

				}
			}

			List<ConhecimentoSolicitacaoDTO> colConhecimentoSolicitacao = (List<ConhecimentoSolicitacaoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(
					ConhecimentoSolicitacaoDTO.class, "colConhecimentoSolicitacao_Serialize", request);
			solicitacaoServicoDto.setColConhecimentoSolicitacaoSerialize(colConhecimentoSolicitacao);

			if (solicitacaoServicoDto.getSituacao() != null && solicitacaoServicoDto.getSituacao().equalsIgnoreCase(Enumerados.SituacaoSolicitacaoServico.Resolvida.name())) {
				if (solicitacaoServicoDto.getResposta().trim().equalsIgnoreCase("")) {
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.solucaoresposta"));
					this.verificaImpactoUrgencia(document, request, response);
					if (solicitacaoServicoQuestionarioDto != null)
						carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
					return;
				}
				TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class,
						WebUtil.getUsuarioSistema(request));
				TipoDemandaServicoDTO tipoDemandaServicoDTO = new TipoDemandaServicoDTO();
				tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoDto.getIdTipoDemandaServico());
				if (tipoDemandaServicoDTO.getIdTipoDemandaServico() == null) {
					SolicitacaoServicoDTO solicitacaoServicoAux = solicitacaoServicoService.restoreAll(solicitacaoServicoDto.getIdSolicitacaoServico());
					tipoDemandaServicoDTO.setIdTipoDemandaServico(solicitacaoServicoAux.getIdTipoDemandaServico());
				}
				tipoDemandaServicoDTO = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDTO);
				if (tipoDemandaServicoDTO != null) {
					if (tipoDemandaServicoDTO.getClassificacao().equalsIgnoreCase("I")) {
						if (CAMPOS_OBRIGATORIO_SOLICITACAOSERVICO.trim().equalsIgnoreCase("S")) {
							if (solicitacaoServicoDto.getIdCausaIncidente() == null) {
								document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.classifiqueincidente"));
								this.verificaImpactoUrgencia(document, request, response);
								if (solicitacaoServicoQuestionarioDto != null)
									carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
								return;
							}
							if (solicitacaoServicoDto.getIdCategoriaSolucao() == null) {
								document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.classifiquesolucao"));
								this.verificaImpactoUrgencia(document, request, response);
								if (solicitacaoServicoQuestionarioDto != null)
									carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
								return;
							}
						}
					}
				}
				boolean bvalidaBaseConhecimento = solicitacaoServicoDto.getValidaBaseConhecimento() != null && solicitacaoServicoDto.getValidaBaseConhecimento().equalsIgnoreCase("S");
				if (bvalidaBaseConhecimento) {
					boolean informouBaseConhecimento = solicitacaoServicoDto.getColConhecimentoSolicitacaoSerialize() != null
							&& solicitacaoServicoDto.getColConhecimentoSolicitacaoSerialize().size() > 0;
					if (!informouBaseConhecimento) {
						document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.baseconhecimento"));
						this.verificaImpactoUrgencia(document, request, response);
						if (solicitacaoServicoQuestionarioDto != null)
							carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
						return;
					}
				}
			}

			Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
			solicitacaoServicoDto.setColArquivosUpload(arquivosUpados);

			Collection colItensProblema = br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ProblemaDTO.class, "colItensProblema_Serialize", request);
			solicitacaoServicoDto.setColItensProblema(colItensProblema);

			List<RequisicaoMudancaDTO> colItensMudanca = (List<RequisicaoMudancaDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(RequisicaoMudancaDTO.class,
					"colItensMudanca_Serialize", request);
			solicitacaoServicoDto.setColItensMudanca(colItensMudanca);

			List<ItemConfiguracaoDTO> colItensIC = (List<ItemConfiguracaoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ItemConfiguracaoDTO.class, "colItensIC_Serialize",
					request);
			solicitacaoServicoDto.setColItensICSerialize(colItensIC);

			EmailSolicitacaoServicoService emailSolicitacaoServicoService = (EmailSolicitacaoServicoService) ServiceLocator.getInstance().getService(EmailSolicitacaoServicoService.class,
					WebUtil.getUsuarioSistema(request));
			if (solicitacaoServicoDto.getMessageId() != null && solicitacaoServicoDto.getMessageId().trim().length() > 0) {
				EmailSolicitacaoServicoDTO emailDto = new EmailSolicitacaoServicoDTO();
				emailDto.setIdEmailSolicitacaoServico(Integer.parseInt(solicitacaoServicoDto.getMessageId()));
				emailDto = (EmailSolicitacaoServicoDTO) emailSolicitacaoServicoService.restore(emailDto);
				emailDto.setSituacao("Resolvido");
				emailSolicitacaoServicoService.update(emailDto);
			}
			solicitacaoServicoDto.setUsuarioDto(usuario);
			solicitacaoServicoDto.setRegistradoPor(usuario.getNomeUsuario());

			try {
				/* Escapa os caracteres especiais */
				solicitacaoServicoDto.setObservacao(UtilStrings.getParameter(solicitacaoServicoDto.getObservacao()));
				solicitacaoServicoDto.setDescricao(UtilStrings.getParameter(solicitacaoServicoDto.getDescricao()));
				solicitacaoServicoDto.setDetalhamentoCausa(UtilStrings.getParameter(solicitacaoServicoDto.getDetalhamentoCausa()));
				solicitacaoServicoDto.setRegistroexecucao(UtilStrings.getParameter(solicitacaoServicoDto.getRegistroexecucao()));
				if (solicitacaoServicoDto != null && solicitacaoServicoDto.getResposta() != null) {
					solicitacaoServicoDto.setResposta(UtilStrings.getParameter(solicitacaoServicoDto.getResposta()));
				}

				// solicitacaoServicoDto.setObservacao(UtilStrings.getParameter(solicitacaoServicoDto.getObservacao()));
				// solicitacaoServicoDto.setDetalhamentoCausa(UtilStrings.getParameter(solicitacaoServicoDto.getDetalhamentoCausa()));
				// solicitacaoServicoDto.setRegistroexecucao(UtilStrings.getParameter(solicitacaoServicoDto.getRegistroexecucao()));
				// solicitacaoServicoDto.setResposta(UtilStrings.getParameter(solicitacaoServicoDto.getResposta()));

				if (solicitacaoServicoDto.getIdSolicitacaoServico() == null || solicitacaoServicoDto.getIdSolicitacaoServico().intValue() == 0) {
					solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoServicoService.create(solicitacaoServicoDto);
					
					if(solicitacaoServicoDto.getCriouProblemaAutomatico() != null && solicitacaoServicoDto.getCriouProblemaAutomatico().equalsIgnoreCase("S")) { //Grava Problema caso Criar Problema Seja 'S'
						MonitoraIncidentes monitoraIncidentes = new MonitoraIncidentes();
						monitoraIncidentes.criaProblema(solicitacaoServicoDto);
					}
					
					// document.executeScript("document.getElementById('divInformacoesComplementares').style.display = 'none';");
					// document.alert("Registro efetuado com sucesso. Solicitação N.o: "
					// + solicitacaoServicoDto.getIdSolicitacaoServico() +
					// " criada.");
					String comando = "mostraMensagemInsercao('" + UtilI18N.internacionaliza(request, "MSG05") + ".<br>" + UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao")
							+ " <b><u>" + solicitacaoServicoDto.getIdSolicitacaoServico() + "</u></b> " + UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br><br>"
							+ UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": " + solicitacaoServicoDto.getIdPrioridade();
					if (solicitacaoServicoDto.getPrazoHH() > 0 || solicitacaoServicoDto.getPrazoMM() > 0) {
						comando = comando + " - SLA: " + solicitacaoServicoDto.getSLAStr() + "";
					}
					comando = comando + "')";
					document.executeScript(comando);
					return;
				} else {
					solicitacaoServicoService.updateInfo(solicitacaoServicoDto);

					// document.executeScript("document.getElementById('divInformacoesComplementares').style.display = 'none';");
					document.alert(UtilI18N.internacionaliza(request, "MSG06"));
				}

			} catch (Exception e) {
				e.printStackTrace();
				if (!bExisteSolicitacao)
					solicitacaoServicoDto.setIdSolicitacaoServico(null);
				if (solicitacaoServicoQuestionarioDto != null)
					carregaInformacoesComplementares(document, request, solicitacaoServicoDto);
				String msgErro = (e.getLocalizedMessage() == null ? "" : e.getLocalizedMessage());
				msgErro = msgErro.replaceAll("java.lang.Exception:", "");
				msgErro = msgErro.replaceAll("br.com.citframework.excecao.ServiceException:", "");
				msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
				msgErro = msgErro.replaceAll("br.com.citframework.excecao.LogicException:", "");
				msgErro = msgErro.replaceAll("br.com.centralit.citcorpore.exception.LogicException:", "");
				msgErro = msgErro.replaceAll("br.com.centralit.citajax.exception.LogicException:", "");
				msgErro = msgErro.replaceAll("Wrapped", "");
				msgErro = msgErro.replaceAll("params.get\\(\"execucaoFluxo\"\\).recuperaGrupoAprovador\\(\\);", "");
				msgErro = msgErro.replaceAll("\\(script#1\\)", "");
				document.alert("" + msgErro);
				this.verificaImpactoUrgencia(document, request, response);
				return;
			}
			document.executeScript("fechar();");
			solicitacaoServicoDto = null;
		} finally {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
		}
	}

	public void restoreSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

		UsuarioDTO usuarioDto = new UsuarioDTO();
		usuarioDto.setIdUsuario(solicitacaoServicoDto.getIdSolicitante());
		usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);

		if (usuarioDto != null) {

			EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
			EmpregadoDTO empregadoBean = empregadoService.restoreByIdEmpregado(usuarioDto.getIdEmpregado());
			if (empregadoBean == null) {
				empregadoBean = new EmpregadoDTO();
				empregadoBean.setNome("");
			}
			String login = usuarioDto.getLogin();

			SolicitacaoServicoDTO listaIdentificacaoLogin = solicitacaoServicoService.retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);
			solicitacaoServicoDto.setSolicitante(empregadoBean.getNome());
			String nome = solicitacaoServicoDto.getSolicitante();
			SolicitacaoServicoDTO listaInformacaoContato = solicitacaoServicoService.listInformacaoContato(nome);
			if (listaIdentificacaoLogin != null) {
				solicitacaoServicoDto.setIdItemConfiguracao(listaIdentificacaoLogin.getIdItemConfiguracao());
				solicitacaoServicoDto.setItemConfiguracao(listaIdentificacaoLogin.getItemConfiguracao());
				/* document.executeScript("exibeCampos()"); */
			}

			if (listaInformacaoContato != null) {
				// CITCorporeUtil.limparFormulario(document);
				solicitacaoServicoDto.setNomecontato(listaInformacaoContato.getNomecontato());
				solicitacaoServicoDto.setTelefonecontato(listaInformacaoContato.getTelefonecontato());
				solicitacaoServicoDto.setEmailcontato(listaInformacaoContato.getEmailcontato());

			}

			HTMLForm form = document.getForm("form");
			form.setValues(solicitacaoServicoDto);
			document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");
		}
		document.executeScript("setDataEditor()");

		// verifica se tem historico para mostrar botao de historico
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null);
		if (resumo == null || resumo.size() <= 0) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'inline')");
		}

		resumo = solicitacaoService.findSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null, null);
		if (resumo == null || resumo.size() <= 0) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'inline')");
		}

		solicitacaoServicoDto = null;
	}

	/**
	 * Restaura Colaborador selecionado como Solicitante, obtendo e atribuindo informaï¿½ï¿½es de Contato, Item de Configuraï¿½ï¿½o e Histï¿½rico de Solicitaï¿½ï¿½es.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restoreColaboradorSolicitante(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));

		EmpregadoDTO empregadoDto = new EmpregadoDTO();
		empregadoDto.setIdEmpregado(solicitacaoServicoDto.getIdSolicitante());
		empregadoDto = (EmpregadoDTO) empregadoService.restore(empregadoDto);

		// CITCorporeUtil.limparFormulario(document);
		if (empregadoDto != null) {
			solicitacaoServicoDto.setSolicitante(empregadoDto.getNome());
			solicitacaoServicoDto.setNomecontato(empregadoDto.getNome());
			solicitacaoServicoDto.setTelefonecontato(empregadoDto.getTelefone());
			solicitacaoServicoDto.setRamal(empregadoDto.getRamal());
			solicitacaoServicoDto.setEmailcontato(empregadoDto.getEmail().trim());
			solicitacaoServicoDto.setIdUnidade(empregadoDto.getIdUnidade());
			solicitacaoServicoDto.setRamal(empregadoDto.getRamal());

			this.preencherComboLocalidade(document, request, response);
		}

		UsuarioDTO usuarioDto = new UsuarioDTO();

		if (empregadoDto != null && empregadoDto.getIdEmpregado() != null) {
			usuarioDto = (UsuarioDTO) usuarioService.restoreByIdEmpregado(empregadoDto.getIdEmpregado());
		}

		if (usuarioDto != null) {
			String login = usuarioDto.getLogin();

			SolicitacaoServicoDTO solicitacaoServicoComItemConfiguracaoDoSolicitante = solicitacaoServicoService.retornaSolicitacaoServicoComItemConfiguracaoDoSolicitante(login);

			if (solicitacaoServicoComItemConfiguracaoDoSolicitante != null) {
				solicitacaoServicoDto.setIdItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getIdItemConfiguracao());
				solicitacaoServicoDto.setItemConfiguracao(solicitacaoServicoComItemConfiguracaoDoSolicitante.getItemConfiguracao());
				/* document.executeScript("exibeCampos()"); */
			}
		}

		HTMLForm form = document.getForm("form");

		// Removido pois apresenta erro quando o método é chamado a partir da Chamada do Asterisk
		// document.executeScript("setDataEditor()");

		form.setValues(solicitacaoServicoDto);

		document.executeScript("fecharPopup(\"#POPUP_SOLICITANTE\")");

		boolean hasSol = solicitacaoService.hasSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), null);
		if (!hasSol) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitante', 'inline')");
		}

		boolean hashist = solicitacaoService.hasSolicitacoesServicosUsuario(solicitacaoServicoDto.getIdSolicitante(), "EmAndamento");
		if (!hashist) {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'none')");
		} else {
			document.executeScript("escondeMostraDiv('btHistoricoSolicitanteEmAndamento', 'inline')");
		}

		String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");

		if (!UtilStrings.isNotVazio(controleAccUnidade)) {
			controleAccUnidade = "N";
		}

		if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
			carregaServicos(document, request, response);
		}

		solicitacaoServicoDto = null;
	}

	/*
	 * public void restoreItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception { SolicitacaoServicoDTO solicitacaoServicoDto =
	 * (SolicitacaoServicoDTO) document.getBean();
	 * 
	 * Integer idItemConfiguracao = solicitacaoServicoDto.getIdItemConfiguracao(); SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService)
	 * ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request)); SolicitacaoServicoDTO identificacao =
	 * solicitacaoServicoService.listIdentificacao(idItemConfiguracao); solicitacaoServicoDto .setItemConfiguracao(identificacao.getItemConfiguracao()); HTMLForm form = document.getForm("form");
	 * document.executeScript("fecharPopupPesquisaItemCfg()"); document.executeScript("exibeCampos()"); form.setValues(solicitacaoServicoDto);
	 * 
	 * // verifica se tem histórico para mostrar botï¿½o de histórico // divBtHistoricoSolicitante SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService)
	 * ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request)); ArrayList<SolicitacaoServicoDTO> resumo =
	 * solicitacaoService.findSolicitacoesServicosUsuario(null, solicitacaoServicoDto.getIdItemConfiguracao()); if (resumo == null || resumo.size() <= 0) {
	 * document.executeScript("escondeMostraDiv('btHistoricoIc', 'none')"); } else { document.executeScript("escondeMostraDiv('btHistoricoIc', 'inline')"); }
	 * 
	 * document.setBean(solicitacaoServicoDto); atualizaGridIC(document, request, response);
	 * 
	 * 
	 * document.getElementById("itemConfiguracao").setValue( "NOME QUE COLOQUEI NO JAVA"); document.executeScript("fecharPopupPesquisaItemCfg()");
	 * 
	 * }
	 */

	public void restoreItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();
		ItemConfiguracaoService itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();

		itemConfiguracaoDTO.setIdItemConfiguracao(bean.getIdItemConfiguracao());
		itemConfiguracaoDTO = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO);

		HTMLTable tblIC = document.getTableById("tblIC");
		String impactoUrgencia = "";
		String impacto = "";
		String urgencia = "";

		if (itemConfiguracaoDTO.getImpacto() != null) {

			if ("B".equalsIgnoreCase(itemConfiguracaoDTO.getImpacto())) {
				impacto = UtilI18N.internacionaliza(request, "liberacao.riscoBaixo");
			}
			if ("M".equalsIgnoreCase(itemConfiguracaoDTO.getImpacto())) {
				impacto = UtilI18N.internacionaliza(request, "liberacao.riscoMedio");
			}
			if ("A".equalsIgnoreCase(itemConfiguracaoDTO.getImpacto()) || "A".equalsIgnoreCase(itemConfiguracaoDTO.getImpacto())) {
				impacto = UtilI18N.internacionaliza(request, "liberacao.riscoAlto");
			}

			impactoUrgencia += "(" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.impacto") + ": " + impacto + ")";
		}
		if (itemConfiguracaoDTO.getUrgencia() != null) {

			if ("B".equalsIgnoreCase(itemConfiguracaoDTO.getUrgencia())) {
				urgencia = UtilI18N.internacionaliza(request, "liberacao.riscoBaixo");
			}
			if ("M".equalsIgnoreCase(itemConfiguracaoDTO.getUrgencia())) {
				urgencia = UtilI18N.internacionaliza(request, "liberacao.riscoMedio");
			}
			if ("A".equalsIgnoreCase(itemConfiguracaoDTO.getUrgencia()) || "H".equalsIgnoreCase(itemConfiguracaoDTO.getUrgencia())) {
				urgencia = UtilI18N.internacionaliza(request, "liberacao.riscoAlto");
			}

			impactoUrgencia += "(" + UtilI18N.internacionaliza(request, "itemConfiguracaoTree.urgencia") + ": " + urgencia + ")";
		}
		if (verificaItemConfiguracaoSeJaEstaRelacionado(document, request, response, bean.getIdItemConfiguracao())) {

			if (itemConfiguracaoDTO.getSequenciaIC() == null) {
				tblIC.addRow(itemConfiguracaoDTO, new String[] { "", "idItemConfiguracao", "identificacaoStatus", "" }, new String[] { "idItemConfiguracao" }, "Item Configuração já cadastrado!!",
						new String[] { "exibeIconesICComAlerta" }, null, null);
			} else {
				tblIC.updateRow(itemConfiguracaoDTO, new String[] { "", "idItemConfiguracao", "identificacaoStatus", "" }, new String[] { "idItemConfiguracao" }, "Item Configuração já cadastrado!!",
						new String[] { "exibeIconesICComAlerta" }, null, null, itemConfiguracaoDTO.getSequenciaIC());
			}

		} else {

			if (itemConfiguracaoDTO.getSequenciaIC() == null) {
				tblIC.addRow(itemConfiguracaoDTO, new String[] { "", "idItemConfiguracao", "identificacaoStatus", "" }, new String[] { "idItemConfiguracao" }, "Item Configuração já cadastrado!!",
						new String[] { "exibeIconesIC" }, null, null);
			} else {
				tblIC.updateRow(itemConfiguracaoDTO, new String[] { "", "idItemConfiguracao", "identificacaoStatus", "" }, new String[] { "idItemConfiguracao" }, "Item Configuração já cadastrado!!",
						new String[] { "exibeIconesIC" }, null, null, itemConfiguracaoDTO.getSequenciaIC());
			}

		}

		itemConfiguracaoDTO.setImpactoUrgencia(impactoUrgencia);

		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblIC', 'tblIC');");

		document.executeScript("fecharPopupPesquisaItemCfg()");

		/* document.executeScript("fecharM();"); */

		// metodo para setar urgencia e impacto de acordo com o item de configuração
		int prioridade = 0;
		int prioridadeObj = 0;
		List<ItemConfiguracaoDTO> colItensIC = (List<ItemConfiguracaoDTO>) br.com.citframework.util.WebUtil
				.deserializeCollectionFromRequest(ItemConfiguracaoDTO.class, "colItensIC_Serialize", request);
		if (colItensIC == null) {
			colItensIC = new ArrayList<ItemConfiguracaoDTO>();
		}
		colItensIC.add(itemConfiguracaoDTO);
		if (colItensIC != null) {
			for (ItemConfiguracaoDTO itemConfiguracaoDTO2 : colItensIC) {
				itemConfiguracaoDTO2 = (ItemConfiguracaoDTO) itemConfiguracaoService.restore(itemConfiguracaoDTO2);
				if (itemConfiguracaoDTO2.getUrgencia() != null && itemConfiguracaoDTO2.getImpacto() != null) {
					if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
						prioridadeObj = 1;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
						prioridadeObj = 2;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("B") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
						prioridadeObj = 3;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
						prioridadeObj = 2;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
						prioridadeObj = 3;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("M") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
						prioridadeObj = 4;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("B")) {
						prioridadeObj = 3;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("M")) {
						prioridadeObj = 4;
					} else if (itemConfiguracaoDTO2.getUrgencia().equalsIgnoreCase("A") && itemConfiguracaoDTO2.getImpacto().equalsIgnoreCase("A")) {
						prioridadeObj = 5;
					}
					if (prioridadeObj > prioridade) {
						prioridade = prioridadeObj;
						document.getSelectById("urgencia").setValue(itemConfiguracaoDTO2.getUrgencia());
						document.getSelectById("impacto").setValue(itemConfiguracaoDTO2.getImpacto());
					}
				}
			}
		}
		// fim metodo urgencia e impacto

		bean = null;
	}

	// Criado por Bruno.Aquino
	// Verifica se ja existe solicitações com o mesmo item de configuração, se existe mostra a imagem de alerta na lista
	public boolean verificaItemConfiguracaoSeJaEstaRelacionado(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, int idItemConfiguracao) throws ServiceException,
			Exception {

		// SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		String idSolicitanteStr = request.getParameter("idSolicitante");
		Integer idSolicitante = null;
		if (idSolicitanteStr != null && !idSolicitanteStr.equals("")) {
			idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
		}

		if (idSolicitante == null) {
			idSolicitante = 0;
		}
		System.out.println(idItemConfiguracao);
		if (idItemConfiguracao == 0) {

			return false;
		}

		ItemConfiguracaoDao dao = new ItemConfiguracaoDao();
		Collection<SolicitacaoServicoDTO> lista = dao.listItemConfiguracaoPorSolicitacaoServicoEmAndamento(idSolicitante, "EmAndamento", "");
		if (lista != null) {
			for (SolicitacaoServicoDTO i : lista) {
				if (idItemConfiguracao == i.getIdItemConfiguracao()) {
					return true;
				}
			}
		}

		return false;

	}

	// Bruno.aquino
	// Renderiza a popup de solicitações referentes ao metodo verificaItemConfiguracaoSeJaEstaRelacionado()
	public void renderizaHistoricoItemConfiguracao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idSolicitante = null;
		if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
			idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
		}
		String situacao = request.getParameter("situacaoFiltroSolicitante");
		String campoBusca = request.getParameter("buscaFiltroSolicitante");

		if (situacao == null || situacao.isEmpty()) {
			situacao = "EmAndamento";
		} else {
			situacao = situacao.trim();
		}
		if (campoBusca != null) {
			campoBusca = UtilStrings.decodeCaracteresEspeciais(campoBusca);
			campoBusca = campoBusca.trim();
		}

		if (campoBusca != null && campoBusca.equalsIgnoreCase("")) {
			campoBusca = "";
		}
		StringBuffer script = new StringBuffer();
		StringBuffer filtro = new StringBuffer();

		filtro.append("<div class='clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all' style='padding-top: 10px; margin-bottom: 5px;'>");
		filtro.append("   <div style='float: left;  width: 75%;' class='space'>");
		filtro.append("      <table cellspacing='3' cellpadding='0' style='float: left; height:100%;'>");
		filtro.append("         <tbody>");
		filtro.append("            <tr>");
		filtro.append("               <td style='vertical-align: middle; padding-left: 10px;'>");
		filtro.append("                  " + UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") + ":");
		filtro.append("               </td>");
		filtro.append("               <td style='vertical-align: middle; padding-left: 10px;' colspan='2'>");
		filtro.append("                  " + UtilI18N.internacionaliza(request, "citcorpore.comum.busca") + ":");
		filtro.append("               </td>");
		filtro.append("            </tr>");
		filtro.append("            <tr>");
		filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>");
		filtro.append("			 		<select name='situacaoTblResumo3' id='situacaoTblResumo3'>");
		filtro.append("			 			<option value='EmAndamento' " + ("EmAndamento".equals(situacao) ? "selected" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento")
				+ "</option>");
		filtro.append("			 			<option value='Fechada' " + ("Fechada".equals(situacao) ? "selected" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.comum.fechada") + "</option>");
		filtro.append("			   		</select>");
		filtro.append("                </td>");
		filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>");
		filtro.append("                  <input type='text' style='border:1px solid #B3B3B3;height:32px;' maxlength='256' size='25' id='campoBuscaTblResumo3' name='campoBuscaTblResumo3' class='text' ");
		filtro.append((campoBusca != null ? "value='" + campoBusca + "'" : "") + ">");
		filtro.append("               </td> ");
		filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>");
		filtro.append("		 			 <button id='' type='button' class='light img_icon has_text' onclick='");
		filtro.append("inicializarTemporizador();");
		filtro.append("document.form.situacaoFiltroSolicitante.value = document.getElementById(\"situacaoTblResumo3\").value;");
		filtro.append("document.form.buscaFiltroSolicitante.value = document.getElementById(\"campoBuscaTblResumo3\").value;");
		filtro.append("document.form.fireEvent(\"renderizaHistoricoItemConfiguracao\");'>");
		filtro.append("		 				<img src='/citsmart/template_new/images/icons/small/grey/magnifying_glass.png'>");
		filtro.append("		 				<span>" + UtilI18N.internacionaliza(request, "citcorpore.comum.pesquisar") + "</span>");
		filtro.append("					 </button>");
		filtro.append("               </td>");
		filtro.append("            </tr>");
		filtro.append("         </tbody>");
		filtro.append("      </table>");
		filtro.append("   </div>");
		filtro.append("</div>");

		ItemConfiguracaoDao dao = new ItemConfiguracaoDao();
		Collection<SolicitacaoServicoDTO> resumo = dao.listItemConfiguracaoPorSolicitacaoServicoEmAndamento(idSolicitante, situacao, campoBusca);
		ArrayList<SolicitacaoServicoDTO> solicitacoesRetorno = new ArrayList<SolicitacaoServicoDTO>();

		for (SolicitacaoServicoDTO solicitacao : resumo) {
			solicitacoesRetorno.add(solicitacaoService.restoreAll(solicitacao.getIdSolicitacaoServico()));
		}

		String corpoHTML = montaHTMLResumoSolicitacoes(solicitacoesRetorno, script, request);

		filtro.append(corpoHTML);

		document.getElementById("tblResumo3").setInnerHTML(filtro.toString());

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo3\").dialog(\"open\");");
	}

	public void carregaBaseConhecimentoAssoc(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		document.getElementById("divScript").setInnerHTML(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.scriptservico"));
		if (solicitacaoServicoDto.getIdServico() == null)
			return;
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		ServicoDTO servicoDto = new ServicoDTO();
		servicoDto.setIdServico(solicitacaoServicoDto.getIdServico());
		servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
		if (servicoDto != null) {
			if (servicoDto.getIdBaseconhecimento() != null) {
				BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();
				baseConhecimentoDTO.setIdBaseConhecimento(servicoDto.getIdBaseconhecimento());
				baseConhecimentoDTO = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDTO);
				if (baseConhecimentoDTO != null) {
					document.getElementById("divScript").setInnerHTML(baseConhecimentoDTO.getConteudo());
					document.executeScript("destaqueScript()");
				}
			}
		}

		solicitacaoServicoDto = null;
	}

	public void carregaContratos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		HTMLSelect idContrato = (HTMLSelect) document.getSelectById("idContrato");
		idContrato.removeAllOptions();

		if (solicitacaoServicoDto.getIdServico() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		Collection<ServicoContratoDTO> colContratos = servicoContratoService.findByIdServico(solicitacaoServicoDto.getIdServico());
		if (colContratos != null) {
			FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
			if (colContratos.size() > 1)
				idContrato.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			else {
				ServicoContratoDTO servicoContratoDto = (ServicoContratoDTO) ((List) colContratos).get(0);
				solicitacaoServicoDto.setIdContrato(servicoContratoDto.getIdContrato());
				verificaGrupoExecutorInterno(document, solicitacaoServicoDto);
				verificaImpactoUrgencia(document, request, response);
			}
			ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
			for (ServicoContratoDTO servicoContratoDto : colContratos) {
				if (servicoContratoDto.getDeleted() == null || servicoContratoDto.getDeleted().equalsIgnoreCase("N")) {
					ContratoDTO contratoDto = new ContratoDTO();
					contratoDto.setIdContrato(servicoContratoDto.getIdContrato());
					contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
					if (contratoDto != null) {
						if (contratoDto.getDeleted() == null || contratoDto.getDeleted().equalsIgnoreCase("N")) {
							String id = contratoDto.getNumero();
							FornecedorDTO fornecedorDto = new FornecedorDTO();
							fornecedorDto.setIdFornecedor(contratoDto.getIdFornecedor());
							fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
							if (fornecedorDto != null)
								id += " - " + fornecedorDto.getRazaoSocial();
							idContrato.addOptionIfNotExists("" + contratoDto.getIdContrato(), id);
						}
					}
				}
			}
		}

		solicitacaoServicoDto = null;
	}

	public void verificaGrupoExecutor(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		verificaGrupoExecutorInterno(document, solicitacaoServicoDto);

		solicitacaoServicoDto = null;
	}

	public void verificaImpactoUrgencia(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		document.getSelectById("impacto").setDisabled(false);
		document.getSelectById("urgencia").setDisabled(false);
		if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());

		if (servicoContratoDto != null) {
			AcordoNivelServicoService acordoNivelServicoService = (AcordoNivelServicoService) ServiceLocator.getInstance().getService(AcordoNivelServicoService.class, null);
			AcordoNivelServicoDTO acordoNivelServicoDto = acordoNivelServicoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
			if (acordoNivelServicoDto == null) {
				// Se nao houver acordo especifico, ou seja, associado direto ao
				// servicocontrato, entao busca um acordo geral que esteja
				// vinculado ao servicocontrato.
				AcordoServicoContratoService acordoServicoContratoService = (AcordoServicoContratoService) ServiceLocator.getInstance().getService(AcordoServicoContratoService.class, null);
				AcordoServicoContratoDTO acordoServicoContratoDTO = acordoServicoContratoService.findAtivoByIdServicoContrato(servicoContratoDto.getIdServicoContrato(), "T");
				if (acordoServicoContratoDTO == null) {
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
					return;
				}
				// Apos achar a vinculacao do acordo com o servicocontrato,
				// entao faz um restore do acordo de nivel de servico.
				acordoNivelServicoDto = new AcordoNivelServicoDTO();
				acordoNivelServicoDto.setIdAcordoNivelServico(acordoServicoContratoDTO.getIdAcordoNivelServico());
				acordoNivelServicoDto = (AcordoNivelServicoDTO) new AcordoNivelServicoDao().restore(acordoNivelServicoDto);
				if (acordoNivelServicoDto == null) {
					// Se nao houver acordo especifico, ou seja, associado
					// direto ao servicocontrato
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.tempoacordo"));
					return;
				}
			}
			if (acordoNivelServicoDto.getImpacto() != null) {
				document.getSelectById("impacto").setValue("" + acordoNivelServicoDto.getImpacto());
				if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
					document.getSelectById("impacto").setDisabled(true);
				}
			} else {
				document.getSelectById("impacto").setValue("B");
			}
			if (acordoNivelServicoDto.getUrgencia() != null) {
				document.getSelectById("urgencia").setValue("" + acordoNivelServicoDto.getUrgencia());
				if (acordoNivelServicoDto.getPermiteMudarImpUrg() != null && acordoNivelServicoDto.getPermiteMudarImpUrg().equalsIgnoreCase("N")) {
					document.getSelectById("urgencia").setDisabled(true);
				}
			} else {
				document.getSelectById("urgencia").setValue("B");
			}
		} else {
			document.getSelectById("impacto").setValue("B");
			document.getSelectById("urgencia").setValue("B");
		}

		servicoContratoDto = null;
		solicitacaoServicoDto = null;
	}

	public void verificaGrupoExecutorInterno(DocumentHTML document, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		if (solicitacaoServicoDto.getIdServico() == null || solicitacaoServicoDto.getIdContrato() == null)
			return;

		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdContratoAndIdServico(solicitacaoServicoDto.getIdContrato(), solicitacaoServicoDto.getIdServico());
		if (servicoContratoDto != null && servicoContratoDto.getIdGrupoExecutor() != null)
			document.getElementById("idGrupoAtual").setValue("" + servicoContratoDto.getIdGrupoExecutor());
		else
			document.getElementById("idGrupoAtual").setValue("");
	}

	public void carregaServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		document.getElementById("divScript").setInnerHTML(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.scriptservico"));
		HTMLSelect idServico = (HTMLSelect) document.getSelectById("idServico");
		idServico.removeAllOptions();

		HTMLSelect idTipoDemandaServico = (HTMLSelect) document.getSelectById("idTipoDemandaServico");

		((HTMLSelect) document.getSelectById("idContrato")).removeAllOptions();

		if (solicitacaoServicoDto.getIdTipoDemandaServico() == null)
			return;

		String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");

		if (!UtilStrings.isNotVazio(controleAccUnidade)) {
			controleAccUnidade = "N";
		}

		if (controleAccUnidade.trim().equalsIgnoreCase("S")) {
			if (solicitacaoServicoDto.getSolicitante() == null || solicitacaoServicoDto.getSolicitante().isEmpty()) {
				document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.solicitacao"));
				idTipoDemandaServico.setSelectedIndex(0);
				return;
			} else {
				UnidadesAccServicosService unidadeAccService = (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);
				idServico.removeAllOptions();

				// Busca Unidade do solicitante
				Integer idUnidade = getEmpregadoService().consultaUnidadeDoEmpregado(solicitacaoServicoDto.getIdSolicitante());

				Collection<UnidadesAccServicosDTO> col = unidadeAccService.findByIdUnidade(idUnidade);

				if (col == null || col.isEmpty()) {
					document.alert(UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.solicitanteunidade"));
					idTipoDemandaServico.setSelectedIndex(0);
					return;
				}

				ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
				Collection colServicos = new ArrayList();

				for (UnidadesAccServicosDTO unidadeAccServicoDTO : col) {
					List<UnidadesAccServicosDTO> temp = null;
					temp = (List<UnidadesAccServicosDTO>) servicoService.findByIdServicoAndIdTipoDemandaAndIdCategoria(unidadeAccServicoDTO.getIdServico(),
							solicitacaoServicoDto.getIdTipoDemandaServico(), solicitacaoServicoDto.getIdCategoriaServico());
					if (temp != null)
						colServicos.add(temp.get(0));
				}

				int cont = 0;

				Integer idServicoCasoApenas1 = null;

				if (colServicos != null) {
					if (colServicos.size() > 1)
						idServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
					for (Iterator it = colServicos.iterator(); it.hasNext();) {
						ServicoDTO servicoDTO = (ServicoDTO) it.next();
						if (servicoDTO.getDeleted() == null || servicoDTO.getDeleted().equalsIgnoreCase("N")) {
							if (servicoDTO.getIdSituacaoServico().intValue() == 1) { // ATIVO
								idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
								idServicoCasoApenas1 = servicoDTO.getIdServico();
								cont++;
							}
						}
					}
					// --- RETITRADO POR EMAURI EM 16/07 - TRATAMENTO DE DELETED
					// --> idServico.addOptions(col, "idServico", "nomeServico",
					// null);
				}
				if (colServicos.isEmpty()) {
					idServico.addOption("", "--- " + UtilI18N.internacionaliza(request, "solicitacaoservico.validacao.servicosolicitante") + " ---");
				}

				if (cont == 1) { // Se for apenas um servico encontrado, ja
									// executa o carrega contratos.
					solicitacaoServicoDto.setIdServico(idServicoCasoApenas1);
					carregaContratos(document, request, response);
					carregaBaseConhecimentoAssoc(document, request, response);
				}

			}

		} else {
			ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
			idServico.removeAllOptions();
			Collection col = servicoService.findByIdTipoDemandaAndIdCategoria(solicitacaoServicoDto.getIdTipoDemandaServico(), solicitacaoServicoDto.getIdCategoriaServico());
			int cont = 0;
			Integer idServicoCasoApenas1 = null;
			if (col != null) {
				if (col.size() > 1)
					idServico.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
				for (Iterator it = col.iterator(); it.hasNext();) {
					ServicoDTO servicoDTO = (ServicoDTO) it.next();
					if (servicoDTO.getDeleted() == null || servicoDTO.getDeleted().equalsIgnoreCase("N")) {
						if (servicoDTO.getIdSituacaoServico().intValue() == 1) { // ATIVO
							idServico.addOptionIfNotExists("" + servicoDTO.getIdServico(), servicoDTO.getNomeServico());
							idServicoCasoApenas1 = servicoDTO.getIdServico();
							cont++;
						}
					}
				}
				// --- RETITRADO POR EMAURI EM 16/07 - TRATAMENTO DE DELETED -->
				// idServico.addOptions(col, "idServico", "nomeServico", null);
			}
			if (cont == 1) { // Se for apenas um servico encontrado, ja executa
								// o carrega contratos.
				solicitacaoServicoDto.setIdServico(idServicoCasoApenas1);
				carregaContratos(document, request, response);
				carregaBaseConhecimentoAssoc(document, request, response);
			}
		}
		carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

		solicitacaoServicoDto = null;
	}

	/**
	 * Preenche a combo Hardware.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preecherComboHardware(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		ItemConfiguracaoDTO valor = new ItemConfiguracaoDTO();
		HTMLSelect comboHardware = (HTMLSelect) document.getSelectById("idItemConfiguracaoFilho");
		Integer idItemAnterior = -9999;
		valor.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
		inicializarCombo(comboHardware, request);
		for (ValorDTO valores : this.getListaCaracteristica(valor, "HARDWARE")) {
			if (idItemAnterior.intValue() != valores.getIdItemConfiguracao().intValue()) {
				comboHardware.addOption(valores.getIdItemConfiguracao().toString(), valores.getTagtipoitemconfiguracao() + " - Id: " + valores.getIdItemConfiguracao());
			}
			idItemAnterior = valores.getIdItemConfiguracao();
			comboHardware.addOption(valores.getIdItemConfiguracao().toString(),
					StringEscapeUtils.escapeJavaScript(valores.getNomeCaracteristica()) + " - " + StringEscapeUtils.escapeJavaScript(valores.getValorStr()));
		}
		document.executeScript("addCorCombo();");

		solicitacaoServicoDto = null;

	}

	/**
	 * Preenche a combo Software.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author thays.araujo
	 */
	public void preecherComboSoftware(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		ItemConfiguracaoDTO valor = new ItemConfiguracaoDTO();
		HTMLSelect comboSoftware = (HTMLSelect) document.getSelectById("idItemConfiguracaoPai");
		inicializarCombo(comboSoftware, request);
		valor.setIdItemConfiguracao(solicitacaoServicoDto.getIdItemConfiguracao());
		for (ValorDTO valores : this.getListaCaracteristicaSoftware(valor, "SOFTWARES")) {
			comboSoftware.addOption(valores.getIdItemConfiguracao().toString(), StringEscapeUtils.escapeJavaScript(valores.getValorStr()));
		}

		solicitacaoServicoDto = null;
	}

	/**
	 * Iniciliza combo.
	 * 
	 * @param componenteCombo
	 * @author thays.araujo
	 */
	private void inicializarCombo(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	private void inicializarComboLocalidade(HTMLSelect componenteCombo, HttpServletRequest request) {
		componenteCombo.removeAllOptions();
		componenteCombo.addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
	}

	/**
	 * Retorna lista de caracterï¿½sticas.
	 * 
	 * @param idItemConfiguracao
	 * @param tagTipoItemConfiguracao
	 * @return listaCaracteristica
	 * @throws ServiceException
	 * @throws Exception
	 * @author rosana.godinho
	 */
	public Collection<ValorDTO> getListaCaracteristica(ItemConfiguracaoDTO itemConfiguracao, String tagTipoItemConfiguracao) throws ServiceException, Exception {
		TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
		tipoItemConfiguracao.setTag(tagTipoItemConfiguracao);
		return this.getValorService().findByItemAndTipoItemConfiguracao(itemConfiguracao, tipoItemConfiguracao);

	}

	public Collection<ValorDTO> getListaCaracteristicaSoftware(ItemConfiguracaoDTO itemConfiguracao, String tagTipoItemConfiguracao) throws ServiceException, Exception {
		TipoItemConfiguracaoDTO tipoItemConfiguracao = new TipoItemConfiguracaoDTO();
		tipoItemConfiguracao.setTag(tagTipoItemConfiguracao);
		return this.getValorService().findByItemAndTipoItemConfiguracaoSofware(itemConfiguracao, tipoItemConfiguracao);

	}

	/**
	 * Retorna Service de Valor.
	 * 
	 * @return ValorService
	 * @throws Exception
	 * @author rosana.godinho
	 */
	public ValorService getValorService() throws Exception {
		return (ValorService) ServiceLocator.getInstance().getService(ValorService.class, null);
	}

	/**
	 * @author breno.guimaraes
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void renderizaHistoricoSolicitacoesIncidente(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idIc = null;
		if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) {
			idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
		}

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(null, idIc);
		StringBuffer script = new StringBuffer();
		document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request));

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
	}

	/**
	 * @author breno.guimaraes
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 */
	public void renderizaHistoricoSolicitacoesIC(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idIc = null;
		if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) {
			idIc = Integer.parseInt(request.getParameter("idItemConfiguracao"));
		}

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(null, idIc);
		StringBuffer script = new StringBuffer();
		document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request));

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void renderizaHistoricoSolicitacoesUsuario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idSolicitante = null;
		if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
			idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
		}

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(idSolicitante, null);
		StringBuffer script = new StringBuffer();
		document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request));

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo\").dialog(\"open\");");
	}

	/**
	 * @param document
	 * @param request
	 * @param response
	 * @throws ServiceException
	 * @throws Exception
	 * @author breno.guimaraes
	 */
	public void renderizaHistoricoSolicitacoesEmAndamentoUsuario(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Integer idSolicitante = null;
		if (request.getParameter("idSolicitante") != null && !request.getParameter("idSolicitante").equals("")) {
			idSolicitante = Integer.parseInt(request.getParameter("idSolicitante"));
		}
		String situacao = request.getParameter("situacaoFiltroSolicitante");
		String campoBusca = request.getParameter("buscaFiltroSolicitante");

		if (situacao == null || situacao.isEmpty()) {
			situacao = "EmAndamento";
		} else {
			situacao = situacao.trim();
		}
		if (campoBusca != null) {
			campoBusca = UtilStrings.decodeCaracteresEspeciais(campoBusca);
			campoBusca = campoBusca.trim();
		}

		StringBuffer script = new StringBuffer();
		StringBuffer filtro = new StringBuffer();

		filtro.append("<div class='clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all' style='padding-top: 10px; margin-bottom: 5px;'>");
		filtro.append("   <div style='float: left;  width: 75%;' class='space'>");
		filtro.append("      <table cellspacing='3' cellpadding='0' style='float: left; height:100%;'>");
		filtro.append("         <tbody>");
		filtro.append("            <tr>");
		filtro.append("               <td style='vertical-align: middle; padding-left: 10px;'>");
		filtro.append("                  " + UtilI18N.internacionaliza(request, "citcorpore.comum.situacao") + ":");
		filtro.append("               </td>");
		filtro.append("               <td style='vertical-align: middle; padding-left: 10px;' colspan='2'>");
		filtro.append("                  " + UtilI18N.internacionaliza(request, "citcorpore.comum.busca") + ":");
		filtro.append("               </td>");
		filtro.append("            </tr>");
		filtro.append("            <tr>");
		filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>");
		filtro.append("			 		<select name='situacaoTblResumo2' id='situacaoTblResumo2'>");
		filtro.append("			 			<option value='EmAndamento' " + ("EmAndamento".equals(situacao) ? "selected" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.comum.emandamento")
				+ "</option>");
		filtro.append("			 			<option value='Fechada' " + ("Fechada".equals(situacao) ? "selected" : "") + ">" + UtilI18N.internacionaliza(request, "citcorpore.comum.fechada") + "</option>");
		filtro.append("			   		</select>");
		filtro.append("                </td>");
		filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>");
		filtro.append("                  <input type='text' style='border:1px solid #B3B3B3;height:32px;' maxlength='256' size='25' id='campoBuscaTblResumo2' name='campoBuscaTblResumo2' class='text' ");
		filtro.append((campoBusca != null ? "value='" + campoBusca + "'" : "") + ">");
		filtro.append("               </td> ");
		filtro.append("               <td style='vertical-align: top; padding-left: 10px;'>");
		filtro.append("		 			 <button id='' type='button' class='light img_icon has_text' onclick='");
		filtro.append("inicializarTemporizador();");
		filtro.append("document.form.situacaoFiltroSolicitante.value = document.getElementById(\"situacaoTblResumo2\").value;");
		filtro.append("document.form.buscaFiltroSolicitante.value = document.getElementById(\"campoBuscaTblResumo2\").value;");
		filtro.append("document.form.fireEvent(\"renderizaHistoricoSolicitacoesEmAndamentoUsuario\");'>");
		filtro.append("		 				<img src='/citsmart/template_new/images/icons/small/grey/magnifying_glass.png'>");
		filtro.append("		 				<span>" + UtilI18N.internacionaliza(request, "citcorpore.comum.pesquisar") + "</span>");
		filtro.append("					 </button>");
		filtro.append("               </td>");
		filtro.append("            </tr>");
		filtro.append("         </tbody>");
		filtro.append("      </table>");
		filtro.append("   </div>");
		filtro.append("</div>");

		ArrayList<SolicitacaoServicoDTO> resumo = solicitacaoService.findSolicitacoesServicosUsuario(idSolicitante, situacao, campoBusca);
		String corpoHTML = montaHTMLResumoSolicitacoes(resumo, script, request);

		filtro.append(corpoHTML);

		document.getElementById("tblResumo2").setInnerHTML(filtro.toString());

		document.executeScript(script.toString());
		document.executeScript("temporizador.init();");
		document.executeScript("$(\"#tblResumo2\").dialog(\"open\");");
	}

	/**
	 * @param resumo
	 *            Lista de solicitaÃ§Ãµes que serÃ¡ montada.
	 * @param script
	 *            A string que serÃ¡ alimentada por referÃªncia para ser executada posteriormente.
	 * @return
	 * @author breno.guimaraes
	 */
	private String montaHTMLResumoSolicitacoes(Collection<SolicitacaoServicoDTO> resumo, StringBuffer script, HttpServletRequest request) {
		StringBuffer html = new StringBuffer();

		html.append("<div style='overflow:auto'>");
		html.append("<table class='table' width='100%'");
		html.append("<tr>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "pesquisa.resposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.horalimite") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante"));
		html.append("<img width='20' height='20'");
		html.append("alt='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativaotemporizador") + "' id='imgAtivaTimer' style='opacity:0.5; cursor:pointer;' ");
		html.append("title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativadestemporizador") + "'");
		html.append("src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/cronometro.png'/>");
		html.append("</th>");
		html.append("</tr>");
		for (SolicitacaoServicoDTO r : resumo) {
			html.append("<tr onclick=\"detalheSolicitacao('" + r.getContrato() + "#" + r.getNomecontato() + "#" + r.getEmailcontato() + "#" + r.getTelefonecontato() + "#" + r.getDemanda() + "#"
					+ r.getServico() + "#" + r.getSituacao()
					+ "')\" style=\"cursor:default\" onMouseOver=\"javascript:this.style.backgroundColor='#CFCFCF'\" onMouseOut=\"javascript:this.style.backgroundColor=''\" >");
			html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
			html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");
			html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
			html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(r.getDataHoraSolicitacao()) + "</td>");
			html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
			html.append("<td>" + StringEscapeUtils.unescapeJavaScript(r.getDescricao()) + "</td>");
			html.append("<td>" + (StringEscapeUtils.unescapeJavaScript(r.getResposta()) != null ? StringEscapeUtils.unescapeJavaScript(r.getResposta()) : "-") + "</td>");
			html.append("<td>" + r.getSituacao() + "</td>");
			if (r.getDataHoraLimite() != null) {
				html.append("<td>" + UtilDatas.formatTimestamp(r.getDataHoraLimite()) + "</td>");
				if (r.getSituacao().equals("EmAndamento")) {
					script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante" + r.getIdSolicitacaoServico() + "', " + "'barraProgresso" + r.getIdSolicitacaoServico() + "', " + "'"
							+ r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");
				}
				html.append("<td><label id='tempoRestante" + r.getIdSolicitacaoServico() + "'></label>");
				html.append("<div id='barraProgresso" + r.getIdSolicitacaoServico() + "'></div></td>");
			} else
				html.append("<td>&nbsp;</td>");
			html.append("</tr>");
		}
		html.append("</table>");
		html.append("</div>");

		return html.toString();
	}

	public void listHistorico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		Collection col = solicitacaoService.getHistoricoByIdSolicitacao(solicitacaoServicoDto.getIdSolicitacaoServico());

		StringBuilder strBuilder = new StringBuilder();

		strBuilder.append("<table width='100%'>");
		strBuilder.append("<tr>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.datahora"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.seqreabertura"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "citcorpore.comum.responsavel"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.acao"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "tarefa.tarefa"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.atribuidogrupo"));
		strBuilder.append("</td>");
		strBuilder.append("<td class='linhaSubtituloGridOcorr'>");
		strBuilder.append(UtilI18N.internacionaliza(request, "solicitacaoServico.atribuidousuario"));
		strBuilder.append("</td>");
		strBuilder.append("</tr>");
		if (col != null) {
			for (Iterator it = col.iterator(); it.hasNext();) {
				SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) it.next();
				strBuilder.append("<tr>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilDatas.dateToSTR(solicitacaoServicoAux.getDataHora()) + " " + UtilDatas.formatHoraFormatadaHHMMSSStr(solicitacaoServicoAux.getDataHora()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				if (solicitacaoServicoAux.getSeqReabertura() == null) {
					strBuilder.append("--");
				} else {
					strBuilder.append(solicitacaoServicoAux.getSeqReabertura());
				}
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getResponsavel()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getAcaoFluxo()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getTarefa()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getSiglaGrupo()));
				strBuilder.append("</td>");
				strBuilder.append("<td style='border:1px solid black'>");
				strBuilder.append(UtilStrings.nullToVazio(solicitacaoServicoAux.getNomeUsuario()));
				strBuilder.append("</td>");
				strBuilder.append("</tr>");
			}
		}
		strBuilder.append("</table>");
		document.getElementById("divResultHistorico").setInnerHTML(strBuilder.toString());

		solicitacaoServicoDto = null;
	}

	public void gravarAnexo(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");
		solicitacaoServicoDto.setColArquivosUpload(arquivosUpados);
		// Rotina para gravar no banco
		if (solicitacaoServicoDto.getColArquivosUpload() != null && solicitacaoServicoDto.getColArquivosUpload().size() > 0) {
			Integer idEmpresa = WebUtil.getIdEmpresa(request);
			if (idEmpresa == null)
				idEmpresa = 1;
			solicitacaoServicoService.gravaInformacoesGED(solicitacaoServicoDto.getColArquivosUpload(), idEmpresa, solicitacaoServicoDto, null);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			document.executeScript("('#POPUP_menuAnexos').dialog('close');");
		}

		solicitacaoServicoDto = null;
	}

	/**
	 * Retorna o service de empregado para buscar a unidade do solicitante
	 * 
	 * @author rodrigo.oliveira
	 */
	public EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		return (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
	}

	private ProblemaService problemaService;

	private ProblemaService getProblemaService() throws ServiceException, Exception {
		if (problemaService == null) {
			problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		}
		return problemaService;
	}

	private RequisicaoMudancaService requisicaoMudancaService;

	private RequisicaoMudancaService getRequisicaoMudancaService() throws ServiceException, Exception {
		if (requisicaoMudancaService == null) {
			requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		}
		return requisicaoMudancaService;
	}

	private ConhecimentoSolicitacaoService conhecimentoSolicitacaoService;

	private ConhecimentoSolicitacaoService getConhecimentoSolicitacaoService() throws ServiceException, Exception {
		if (conhecimentoSolicitacaoService == null) {
			conhecimentoSolicitacaoService = (ConhecimentoSolicitacaoService) ServiceLocator.getInstance().getService(ConhecimentoSolicitacaoService.class, null);
		}
		return conhecimentoSolicitacaoService;
	}

	public void preenchePorEmail(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		EmpregadoDTO empregadoDTO = new EmpregadoDTO();
		empregadoDTO = empregadoService.listEmpregadoContrato(solicitacaoServicoDto.getEmailcontato());
		if (empregadoDTO != null) {
			document.getElementById("idSolicitante").setValue(empregadoDTO.getIdEmpregado().toString());
			document.getElementById("nomecontato").setValue(StringEscapeUtils.escapeJavaScript(empregadoDTO.getNome()));
			document.getElementById("telefonecontato").setValue(StringEscapeUtils.escapeJavaScript(empregadoDTO.getTelefone()));
			document.getElementById("idUnidade").setValue(empregadoDTO.getIdUnidade().toString());
			document.getElementById("solicitante").setValue(StringEscapeUtils.escapeJavaScript(empregadoDTO.getNome()));
			document.getElementById("idOrigem").setValue("3");
		}

		solicitacaoServicoDto = null;
	}

	public void abrirListaDeSubSolicitacoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		if (solicitacaoServicoDto != null && solicitacaoServicoDto.getIdSolicitacaoServico() != null) {
			SolicitacaoServicoDTO solicitacaoServicoAux = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoDto);
			Collection colFinal = new ArrayList();
			if (solicitacaoServicoAux != null) {
				if (solicitacaoServicoAux.getIdSolicitacaoRelacionada() != null) {
					solicitacaoServicoAux.setIdSolicitacaoServico(solicitacaoServicoAux.getIdSolicitacaoRelacionada());
					SolicitacaoServicoDTO solicitacaoServicoAux2 = (SolicitacaoServicoDTO) solicitacaoServicoService.restore(solicitacaoServicoAux);
					if (solicitacaoServicoAux2 != null) {
						colFinal.add(solicitacaoServicoAux2);
					}
				}
			}

			Collection<SolicitacaoServicoDTO> solicitacoesRelacionadas = solicitacaoServicoService.listSolicitacaoServicoRelacionadaPai(solicitacaoServicoDto.getIdSolicitacaoServico());
			if (solicitacoesRelacionadas != null) {
				colFinal.addAll(solicitacoesRelacionadas);
			}

			StringBuffer script = new StringBuffer();

			String html = this.gerarHtmlComListaSubSolicitacoes(colFinal, script, request);

			document.getElementById("solicitacaoRelacionada").setInnerHTML(html);
		}

		solicitacaoServicoDto = null;
	}

	private String gerarHtmlComListaSubSolicitacoes(Collection<SolicitacaoServicoDTO> listSolicitacaoServicoRelacionada, StringBuffer script, HttpServletRequest request) {
		StringBuffer html = new StringBuffer();

		html.append("<table class='table' width='100%'");
		html.append("<tr>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.numerosolicitacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.dataabertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.prazo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.resposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.horalimite") + "</th>");
		html.append("</tr>");

		if (listSolicitacaoServicoRelacionada != null && !listSolicitacaoServicoRelacionada.isEmpty()) {

			for (SolicitacaoServicoDTO solicitacaoServicoRelacionada : listSolicitacaoServicoRelacionada) {
				html.append("<tr>");
				html.append("<hidden id='idSolicitante' value='" + solicitacaoServicoRelacionada.getIdSolicitante() + "'/>");
				html.append("<hidden id='idResponsavel' value='" + solicitacaoServicoRelacionada.getIdResponsavel() + "'/>");
				html.append("<td style='text-align: center;'>" + solicitacaoServicoRelacionada.getIdSolicitacaoServico() + "</td>");
				html.append("<td id='dataHoraSolicitacao'>" + solicitacaoServicoRelacionada.getDataHoraSolicitacao() + "</td>");
				html.append("<td>" + solicitacaoServicoRelacionada.getPrazoHH() + ":" + solicitacaoServicoRelacionada.getPrazoMM() + "</td>");
				html.append("<td>" + StringEscapeUtils.unescapeJavaScript(solicitacaoServicoRelacionada.getDescricao()) + "</td>");
				html.append("<td>"
						+ (StringEscapeUtils.unescapeJavaScript(solicitacaoServicoRelacionada.getResposta()) != null ? StringEscapeUtils.unescapeJavaScript(solicitacaoServicoRelacionada.getResposta())
								: "-") + "</td>");
				html.append("<td>" + solicitacaoServicoRelacionada.getSituacao() + "</td>");
				if (solicitacaoServicoRelacionada.getDataHoraLimite() != null)
					html.append("<td>" + solicitacaoServicoRelacionada.getDataHoraLimite() + "</td>");
				else
					html.append("<td>&nbsp;</td>");

				html.append("</tr>");
			}
		}
		html.append("</table>");
		return html.toString();
	}

	public void carregaInformacoesComplementares(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDto = (SolicitacaoServicoDTO) document.getBean();
		carregaInformacoesComplementares(document, request, solicitacaoServicoDto);

		solicitacaoServicoDto = null;
	}

	private void carregaInformacoesComplementares(DocumentHTML document, HttpServletRequest request, SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		document.executeScript("document.getElementById('flagGrupo').value = 1;");
		document.executeScript("document.getElementById('divGrupoAtual').style.display = 'block';");
		document.executeScript("document.getElementById('divSituacao').style.display = 'block';");
		document.executeScript("document.getElementById('solucao').style.display = 'block';");
		document.executeScript("document.getElementById('divUrgencia').style.display = 'block';");
		document.executeScript("document.getElementById('divImpacto').style.display = 'block';");
		document.executeScript("document.getElementById('divNotificacaoEmail').style.display = 'block';");
		document.executeScript("document.getElementById('divProblema').style.display = 'block';");
		document.executeScript("document.getElementById('divMudanca').style.display = 'block';");
		document.executeScript("document.getElementById('divItemConfiguracao').style.display = 'block';");
		if (solicitacaoServicoDto.getIdSolicitacaoServico() != null)
			document.executeScript("document.getElementById('divSolicitacaoRelacionada').style.display = 'block';");
		if (solicitacaoServicoDto.getIdTarefa() != null)
			document.executeScript("document.getElementById('btnGravarEContinuar').style.display = 'block';");
		 document.executeScript("document.getElementById('divInformacoesComplementares').style.height = '350px';");

		SolicitacaoServicoService solicitacaoServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		TemplateSolicitacaoServicoService templateService = (TemplateSolicitacaoServicoService) ServiceLocator.getInstance().getService(TemplateSolicitacaoServicoService.class,
				WebUtil.getUsuarioSistema(request));
		document.executeScript("exibirInformacoesComplementares(\"" + solicitacaoServicoService.getUrlInformacoesComplementares(solicitacaoServicoDto) + "\");");
		TemplateSolicitacaoServicoDTO templateDto = templateService.recuperaTemplateServico(solicitacaoServicoDto);
		if (templateDto != null) {
			if (templateDto.getScriptAposRecuperacao() != null && !StringUtils.isBlank(templateDto.getScriptAposRecuperacao()))
				document.executeScript(templateDto.getScriptAposRecuperacao());
			if (!templateDto.getHabilitaDirecionamento().equalsIgnoreCase("S")) {
				document.executeScript("document.getElementById('flagGrupo').value = 0;");
				document.executeScript("document.getElementById('divGrupoAtual').style.display = 'none';");
			}
			if (!templateDto.getHabilitaSituacao().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divSituacao').style.display = 'none';");
			if (!templateDto.getHabilitaSolucao().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('solucao').style.display = 'none';");
			if (!templateDto.getHabilitaUrgenciaImpacto().equalsIgnoreCase("S")) {
				document.executeScript("document.getElementById('divUrgencia').style.display = 'none';");
				document.executeScript("document.getElementById('divImpacto').style.display = 'none';");
			}
			if (!templateDto.getHabilitaNotificacaoEmail().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divNotificacaoEmail').style.display = 'none';");
			if (!templateDto.getHabilitaProblema().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divProblema').style.display = 'none';");
			if (!templateDto.getHabilitaMudanca().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divMudanca').style.display = 'none';");
			if (!templateDto.getHabilitaItemConfiguracao().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divItemConfiguracao').style.display = 'none';");
			if (!templateDto.getHabilitaSolicitacaoRelacionada().equalsIgnoreCase("S"))
				document.executeScript("document.getElementById('divSolicitacaoRelacionada').style.display = 'none';");
			if (!templateDto.getHabilitaGravarEContinuar().equalsIgnoreCase("S") && solicitacaoServicoDto.getIdTarefa() != null)
				document.executeScript("document.getElementById('btnGravarEContinuar').style.display = 'none';");
			// if (templateDto.getAlturaDiv() != null)
			 document.executeScript("document.getElementById('divInformacoesComplementares').style.height = '" + templateDto.getAlturaDiv().intValue() + "px';");
		}
	}

	public void atualizaGridProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();
		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ProblemaDTO problemaDTO = new ProblemaDTO();

		problemaDTO.setIdProblema(bean.getIdProblema());

		problemaDTO = (ProblemaDTO) problemaService.restore(problemaDTO);
		if (problemaDTO == null) {
			return;
		}
		HTMLTable tblProblema = document.getTableById("tblProblema");

		if (problemaDTO.getSequenciaProblema() == null) {
			tblProblema.addRow(problemaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idProblema" }, "Problema já cadastrado!!", new String[] { "exibeIconesProblema" },
					"buscaProblema", null);
		} else {
			tblProblema.updateRow(problemaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idProblema" }, "Problema já cadastrado!!", new String[] { "exibeIconesProblema" },
					"buscaProblema", null, problemaDTO.getSequenciaProblema());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblProblema', 'tblProblema');");
		document.executeScript("fecharProblema();");

		bean = null;
	}

	public void atualizaGridMudanca(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO bean = (SolicitacaoServicoDTO) document.getBean();

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		RequisicaoMudancaDTO requisicaoMudancaDTO = new RequisicaoMudancaDTO();

		requisicaoMudancaDTO.setIdRequisicaoMudanca(bean.getIdRequisicaoMudanca());

		requisicaoMudancaDTO = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoMudancaDTO);

		HTMLTable tblMudanca = document.getTableById("tblMudanca");

		if (requisicaoMudancaDTO.getSequenciaMudanca() == null) {
			tblMudanca.addRow(requisicaoMudancaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idRequisicaoMudanca" }, "Mudança já cadastrado!!",
					new String[] { "exibeIconesMudanca" }, "abreMudanca", null);
		} else {
			tblMudanca.updateRow(requisicaoMudancaDTO, new String[] { "", "", "numberAndTitulo", "status" }, new String[] { "idRequisicaoMudanca" }, "Mudança já cadastrado!!",
					new String[] { "exibeIconesMudanca" }, "abreMudanca", null, requisicaoMudancaDTO.getSequenciaMudanca());
		}
		document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblMudanca', 'tblMudanca');");
		document.executeScript("fecharMudanca();");

		bean = null;
	}

	public void atualizaGridBaseConhecimento(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		SolicitacaoServicoDTO solicitacaoServicoDTO = (SolicitacaoServicoDTO) document.getBean();

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		BaseConhecimentoDTO baseConhecimentoDTO = new BaseConhecimentoDTO();

		if (solicitacaoServicoDTO.getIdItemBaseConhecimento() != null) {
			baseConhecimentoDTO.setIdBaseConhecimento(solicitacaoServicoDTO.getIdItemBaseConhecimento());
			baseConhecimentoDTO = (BaseConhecimentoDTO) baseConhecimentoService.restore(baseConhecimentoDTO);

			HTMLTable tblBaseConhecimento = document.getTableById("tblBaseConhecimento");

			if (baseConhecimentoDTO.getSequenciaBaseConhecimento() == null) {
				tblBaseConhecimento.addRow(baseConhecimentoDTO, new String[] { "", "", "idBaseConhecimento", "titulo" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null);
			} else {
				tblBaseConhecimento.updateRow(baseConhecimentoDTO, new String[] { "", "", "idBaseConhecimento", "titulo" }, new String[] { "idBaseConhecimento" },
						UtilI18N.internacionaliza(request, "baseConhecimento.baseConhecimentoJaCadastrada"), new String[] { "exibeIconesBaseConhecimento" }, null, null,
						baseConhecimentoDTO.getSequenciaBaseConhecimento());
			}
			document.executeScript("HTMLUtils.applyStyleClassInAllCells('tblBaseConhecimento', 'tblBaseConhecimento');");
			document.executeScript("fecharBaseConhecimento();");
		}
	}

	/**
	 * @return the prioridadeSolicitacoesService
	 * @throws Exception
	 * @throws ServiceException
	 */
	public PrioridadeSolicitacoesService getPrioridadeSolicitacoesService() throws ServiceException, Exception {
		if (prioridadeSolicitacoesService == null) {
			prioridadeSolicitacoesService = (PrioridadeSolicitacoesService) ServiceLocator.getInstance().getService(PrioridadeSolicitacoesService.class, null);
		}
		return prioridadeSolicitacoesService;
	}

	/**
	 * @return the calcularDinamicamente
	 * @throws Exception
	 */
	public String getCalcularDinamicamente() throws Exception {
		calcularDinamicamente = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CALCULAR_PRIORIDADE_SOLICITACAO_DINAMICAMENTE, "N");
		return calcularDinamicamente.trim();
	}

	public Integer carregarProblema(Integer indice, Integer id) throws ServiceException, br.com.citframework.excecao.LogicException, Exception {
		ProblemaDTO problemadto = new ProblemaDTO();
		problemadto.setIdSolicitacaoServico(id);
		if (id != null) {
			Collection col = this.getProblemaService().findBySolictacaoServico(problemadto);
			if (col == null) {
				return null;
			}
			problemadto = (ProblemaDTO) ((List) col).get(indice);
			if (problemadto == null) {
				return null;
			}
		}
		return problemadto.getIdProblema();
	}

	/**
	 * Retorna Grau de Importância.
	 * 
	 * @param request
	 * @param importancia
	 * @return String
	 * @author Vadoilo Damasceno
	 */
	public String getGrauImportancia(HttpServletRequest request, Integer importancia) {
		if (importancia != null) {
			if (importancia == 1) {
				return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.baixo");
			} else {
				if (importancia == 2) {
					return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.medio");
				} else {
					if (importancia == 3) {
						return " - " + UtilI18N.internacionaliza(request, "baseconhecimento.importancia") + ": " + UtilI18N.internacionaliza(request, "baseconhecimento.grauimportancia.alto");
					}
				}
			}
		}
		return "";
	}

	public Integer obterGrauDeImportanciaParaUsuario(BaseConhecimentoDTO baseConhecimentoDto, UsuarioDTO usuarioDto) throws Exception {

		ImportanciaConhecimentoGrupoService importanciaConhecimentoGrupoService = (ImportanciaConhecimentoGrupoService) ServiceLocator.getInstance().getService(
				ImportanciaConhecimentoGrupoService.class, null);
		GrupoEmpregadoService grupoEmpregadoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);

		Collection<GrupoEmpregadoDTO> listGrupoEmpregadoDto = grupoEmpregadoService.findByIdEmpregado(usuarioDto.getIdEmpregado());

		ImportanciaConhecimentoGrupoDTO importanciaConhecimento = importanciaConhecimentoGrupoService.obterGrauDeImportancia(baseConhecimentoDto, listGrupoEmpregadoDto, usuarioDto);

		if (importanciaConhecimento != null) {
			return Integer.parseInt(importanciaConhecimento.getGrauImportancia());
		} else {
			return 0;
		}
	}

}