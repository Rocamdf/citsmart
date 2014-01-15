/**
 *
 */
package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRAbstractLRUVirtualizer;
import net.sf.jasperreports.engine.fill.JRGzipVirtualizer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.bpm.dto.FluxoDTO;
import br.com.centralit.bpm.dto.PermissoesFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FaseServicoDTO;
import br.com.centralit.citcorpore.bean.GrupoDTO;
import br.com.centralit.citcorpore.bean.OrigemAtendimentoDTO;
import br.com.centralit.citcorpore.bean.PesquisaSolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.FaseServicoService;
import br.com.centralit.citcorpore.negocio.GrupoService;
import br.com.centralit.citcorpore.negocio.OrigemAtendimentoService;
import br.com.centralit.citcorpore.negocio.PermissoesFluxoService;
import br.com.centralit.citcorpore.negocio.PrioridadeService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.TipoDemandaServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.LogoRel;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.UtilRelatorio;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citged.bean.ControleGEDDTO;
import br.com.centralit.citged.negocio.ControleGEDService;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class PesquisaSolicitacoesServicos extends AjaxFormAction {

	private static Date dataInicioEstatico;
	private static Date dataFimEstatico;
	
	UsuarioDTO usuario;
	private String localeSession = null;

	@Override
	public Class getBeanClass() {
		return PesquisaSolicitacaoServicoDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		document.getSelectById("idContrato").removeAllOptions();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		Collection colContrato = contratoService.list();
		document.getSelectById("idContrato").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idContrato").addOptions(colContrato, "idContrato", "numero", null);

		document.getSelectById("idPrioridade").removeAllOptions();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		Collection col = prioridadeService.list();
		document.getSelectById("idPrioridade").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idPrioridade").addOptions(col, "idPrioridade", "nomePrioridade", null);

		document.getSelectById("idGrupoAtual").removeAllOptions();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		Collection colGrupos = grupoSegurancaService.findGruposAtivos();
		document.getSelectById("idGrupoAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idGrupoAtual").addOptions(colGrupos, "idGrupo", "nome", null);

		document.getSelectById("idFaseAtual").removeAllOptions();
		FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
		Collection colFases = faseServicoService.list();
		document.getSelectById("idFaseAtual").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idFaseAtual").addOptions(colFases, "idFase", "nomeFase", null);

		document.getSelectById("idOrigem").removeAllOptions();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		Collection colOrigem = origemAtendimentoService.list();
		document.getSelectById("idOrigem").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idOrigem").addOptions(colOrigem, "idOrigem", "descricao", null);

		document.getSelectById("idTipoDemandaServico").removeAllOptions();
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		Collection colTiposDemanda = tipoDemandaServicoService.list();
		document.getSelectById("idTipoDemandaServico").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		document.getSelectById("idTipoDemandaServico").addOptions(colTiposDemanda, "idTipoDemandaServico", "nomeTipoDemandaServico", null);
		
		document.getElementById("paginaAtual").setInnerHTML("1");
		document.getElementById("paginaTotal").setInnerHTML("0");
		
	}

	/*
	 * public void preencheSolicitacoesRelacionadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception { usuario =
	 * WebUtil.getUsuario(request); if (usuario == null) { document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada")); document.executeScript("window.location = '" +
	 * Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'"); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide(); return; } SolicitacaoServicoDTO solicitacaoServicoDto =
	 * (SolicitacaoServicoDTO) document.getBean(); SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class,
	 * null);
	 * 
	 * if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) { if (solicitacaoServicoDto.getDataInicio() == null) { document.alert(UtilI18N.internacionaliza(request,
	 * "citcorpore.comum.validacao.datainicio")); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide(); return; } if (solicitacaoServicoDto.getDataFim() == null) {
	 * document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.datafim")); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide(); return; }
	 * 
	 * } Integer idIc = -1; if (request.getParameter("idItemConfiguracao") != null && !request.getParameter("idItemConfiguracao").equals("")) { idIc =
	 * Integer.parseInt(request.getParameter("idItemConfiguracao")); } Integer idSolicitante = -1; if (request.getParameter("idSolicitante") != null &&
	 * !request.getParameter("idSolicitante").equals("")) { idSolicitante = Integer.parseInt(request.getParameter("idSolicitante")); }
	 * 
	 * Integer idResponsavel = -1; if (request.getParameter("idResponsavel") != null && !request.getParameter("idResponsavel").equals("")) { idResponsavel =
	 * Integer.parseInt(request.getParameter("idResponsavel")); } Integer idUnidade = -1; if (request.getParameter("idUnidade") != null && !request.getParameter("idUnidade").equals("")) { idUnidade =
	 * Integer.parseInt(request.getParameter("idUnidade")); } Integer idServico1 = -1; if (request.getParameter("idServico") != null && !request.getParameter("idServico").equals("")) { idServico1 =
	 * Integer.parseInt(request.getParameter("idServico")); }
	 * 
	 * if (solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() == null) { solicitacaoServicoDto.setIdSolicitacaoServicoPesquisa(-1); } if (solicitacaoServicoDto.getDataInicio() == null) {
	 * solicitacaoServicoDto.setDataInicio (UtilDatas.strToSQLDate("01/01/1970")); } if (solicitacaoServicoDto.getDataFim() == null) { solicitacaoServicoDto.setDataFim(new
	 * java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime())); }
	 * 
	 * if (solicitacaoServicoDto.getDataInicioFechamento() == null) { solicitacaoServicoDto .setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970")); } if
	 * (solicitacaoServicoDto.getDataFimFechamento() == null) { solicitacaoServicoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365,
	 * Calendar.DAY_OF_YEAR).getTime())); }
	 * 
	 * Collection colCriterios = new ArrayList(); colCriterios.add(new Condition("idSolicitacaoServico", "", solicitacaoServicoDto.getIdSolicitacaoServicoPesquisa())); colCriterios.add(new
	 * Condition("idsolicitante", "", idSolicitante)); colCriterios.add(new Condition("iditemconfiguracao", "", idIc)); colCriterios.add(new Condition("situacao", "",
	 * solicitacaoServicoDto.getSituacao())); colCriterios.add(new Condition("dataInicial", "", solicitacaoServicoDto.getDataInicio())); colCriterios.add(new Condition("dataFinal", "",
	 * UtilDatas.strToTimestamp(UtilDatas .dateToSTR(solicitacaoServicoDto.getDataFim()) + " 23:59:59"))); colCriterios.add(new Condition("idPrioridade", "", solicitacaoServicoDto.getIdPrioridade()));
	 * colCriterios.add(new Condition("idOrigem", "", solicitacaoServicoDto.getIdOrigem())); colCriterios.add(new Condition("idUnidade", "", idUnidade)); colCriterios.add(new Condition("idFaseAtual",
	 * "", solicitacaoServicoDto.getIdFaseAtual())); colCriterios.add(new Condition("idGrupoAtual", "", solicitacaoServicoDto.getIdGrupoAtual())); colCriterios.add(new Condition("idServico", "", new
	 * Integer(-1))); colCriterios.add(new Condition("classificacao", "", new String("*"))); colCriterios.add(new Condition("idTipoDemandaServico", "",
	 * solicitacaoServicoDto.getIdTipoDemandaServico())); colCriterios.add(new Condition("idContrato", "", solicitacaoServicoDto.getIdContrato())); colCriterios.add(new Condition("ordenacao", "",
	 * solicitacaoServicoDto.getOrdenacao())); colCriterios.add(new Condition("idResponsavel", "", idResponsavel)); colCriterios.add(new Condition("idServico1", "", idServico1)); colCriterios.add(new
	 * Condition("palavraChave", "", solicitacaoServicoDto.getPalavraChave())); colCriterios.add(new Condition("dataInicioFechamento", "", solicitacaoServicoDto.getDataInicioFechamento()));
	 * colCriterios.add(new Condition("dataFinalFechamento", "", UtilDatas.strToTimestamp(UtilDatas.dateToSTR (solicitacaoServicoDto.getDataFimFechamento()) + " 23:59:59")));
	 * 
	 * ArrayList<SolicitacaoServicoDTO> resumo = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService.listSolicitacaoServicoByCriterios(colCriterios);
	 * 
	 * StringBuffer script = new StringBuffer(); if (resumo != null) { document.getElementById ("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(resumo, script, request, response)); } else {
	 * document.getElementById("tblResumo").setInnerHTML (UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.criterioinformado")); } document.executeScript(script.toString());
	 * document.executeScript("temporizador.init()"); document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
	 * 
	 * }
	 */

	public void preencheSolicitacoesRelacionadas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		
		String pathInfo = request.getRequestURI();
		String ext = "";
		String paginacao = "0";
		Integer totalPag = 1;
		Integer pagAtual = 0;
		Integer pagAtualAux = 0;
		Integer idContrato;
		String campoPesquisa="";
	
		PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();

		if (request.getParameter("paginacao") != null && !request.getParameter("paginacao").equals("")) {
			paginacao = UtilStrings.decodeCaracteresEspeciais(request.getParameter("paginacao"));
		}
		if (request.getParameter("paginacao") == null || request.getParameter("paginacao") == "") {
			paginacao = "0";
		}
		if (request.getParameter("paginacao") == null) {
			paginacao = "0";
		}

		Integer quantidadePaginator = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.QUANT_RETORNO_PESQUISA, "5"));
		if(paginacao.equalsIgnoreCase(quantidadePaginator.toString())){
			pagAtual = quantidadePaginator;
		}else if(new Integer(paginacao) == 1){
			pagAtual = (pesquisaSolicitacaoServicoDto.getPagAtual() + quantidadePaginator);
			pagAtualAux = (pesquisaSolicitacaoServicoDto.getPagAtualAux()+1);
			if(pagAtual >= pesquisaSolicitacaoServicoDto.getTotalItens()){
				pagAtual = pesquisaSolicitacaoServicoDto.getPagAtual();
			}
			if(pagAtualAux >= pesquisaSolicitacaoServicoDto.getTotalPagina()){
				pagAtualAux = pesquisaSolicitacaoServicoDto.getTotalPagina();
			}			
		}else if(new Integer(paginacao) < 0){
			pagAtual = (pesquisaSolicitacaoServicoDto.getPagAtual()- quantidadePaginator);
			pagAtualAux = pesquisaSolicitacaoServicoDto.getPagAtualAux() - 1;
			if(pagAtual < 1){
				pagAtual = 0;
				pagAtualAux = 1;
			}			
		}else if(new Integer(paginacao) == 0){
			pagAtual = 0;
			pagAtualAux = 1;
			
		} 
		
		else{
			pagAtualAux = pesquisaSolicitacaoServicoDto.getTotalPagina() + 1;
			Integer modulo = (pesquisaSolicitacaoServicoDto.getTotalItens() % quantidadePaginator);
			if(modulo.intValue() == quantidadePaginator.intValue() || modulo.intValue() == 0){
				pagAtual = new Integer(paginacao) - quantidadePaginator;
			}else{
				pagAtual = new Integer(paginacao) - modulo;
			}			
			if(pagAtualAux > pesquisaSolicitacaoServicoDto.getTotalPagina()){
				pagAtualAux = pesquisaSolicitacaoServicoDto.getTotalPagina();
			}			
		}

		usuario = WebUtil.getUsuario(request);

		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (pesquisaSolicitacaoServicoDto.getDataInicio() == null) {
			pesquisaSolicitacaoServicoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
		}

		if (pesquisaSolicitacaoServicoDto.getDataFim() == null) {
			pesquisaSolicitacaoServicoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		}

		if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() == null) {
			pesquisaSolicitacaoServicoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
		}

		if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() == null) {
			pesquisaSolicitacaoServicoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
		}

		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		
		if (pesquisaSolicitacaoServicoDto.getFlag().equalsIgnoreCase("semPag")) {
			pagAtual = 0;
			pagAtualAux = 1;
		}
		
		ArrayList<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService.listaSolicitacaoServicoPorCriteriosPaginado(pesquisaSolicitacaoServicoDto, paginacao, pagAtual, pagAtualAux, totalPag, quantidadePaginator, campoPesquisa);
		
		StringBuffer script = new StringBuffer();
		if (listaSolicitacaoServicoPorCriterios != null) {
			document.getElementById("tblResumo").setInnerHTML(montaHTMLResumoSolicitacoes(listaSolicitacaoServicoPorCriterios, script, request, response));
		} else {
			document.getElementById("tblResumo").setInnerHTML(UtilI18N.internacionaliza(request, "citcorpore.comum.validacao.criterioinformado"));
		}
		
		document.getElementById("paginaAtual").setInnerHTML(pagAtualAux.toString());
		document.getElementById("paginaTotal").setInnerHTML(pesquisaSolicitacaoServicoDto.getTotalPagina().toString());
		document.getElementById("totalItens").setValue(pesquisaSolicitacaoServicoDto.getTotalItens().toString());
		document.getElementById("totalPagina").setValue(pesquisaSolicitacaoServicoDto.getTotalPagina().toString());
		document.getElementById("pagAtual").setValue(pagAtual.toString());
		document.getElementById("pagAtualAux").setValue(pagAtualAux.toString());
		
		dataFimEstatico = pesquisaSolicitacaoServicoDto.getDataFim();
		dataInicioEstatico = pesquisaSolicitacaoServicoDto.getDataInicio();
		
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	private String montaHTMLResumoSolicitacoes(ArrayList<SolicitacaoServicoDTO> resumo, StringBuffer script, HttpServletRequest request, HttpServletResponse response) throws ServiceException,
			Exception {
		usuario = WebUtil.getUsuario(request);
		StringBuffer html = new StringBuffer();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, WebUtil.getUsuarioSistema(request));
		PermissoesFluxoService permissoesFluxoService = (PermissoesFluxoService) ServiceLocator.getInstance().getService(PermissoesFluxoService.class, null);
		/*	Foi necessário diminuir a fonte e porcetagem da largura da table para adequear ao modal */
		html.append("<table class='dynamicTable table  table-bordered table-condensed dataTable' id='tbRetorno' width='98%' style = 'font-size: 9px' >");
		html.append("<tr>");
		html.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>");
		html.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>");
		html.append("<th>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitacao") + "/<br>" + UtilI18N.internacionaliza(request, "solicitacaoServico.incidente") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solicitante") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.tipo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraabertura") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "gerenciaservico.sla") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.descricao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.solucaoResposta") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "servico.servico") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.situacao") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoralimite") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "unidade.grupo") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.datahoraencerramento") + "</th>");
		html.append("<th>" + UtilI18N.internacionaliza(request, "solicitacaoServico.temporestante"));
		html.append("<img width='20' height='20'");
		html.append("alt='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativaotemporizador") + "' id='imgAtivaTimer' style='opacity:0.5' ");
		html.append("title='" + UtilI18N.internacionaliza(request, "citcorpore.comum.ativadestemporizador") + "'");
		html.append("src='" + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/cronometro.png'/>");
		html.append("</th>");
		html.append("</tr>");
		HashMap<String, PermissoesFluxoDTO> mapPermissoes = new HashMap();
		for (SolicitacaoServicoDTO r : resumo) {
			SolicitacaoServicoDTO solDto = new SolicitacaoServicoDTO();
			solDto.setIdSolicitacaoServico(r.getIdSolicitacaoServico());

			FluxoDTO fluxoDto = solicitacaoService.recuperaFluxo(solDto);
			if (fluxoDto == null)
				continue;
			PermissoesFluxoDTO permFluxoDto = mapPermissoes.get("" + fluxoDto.getIdFluxo());
			html.append("<tr>");
			html.append("<hidden id='idSolicitante' value='" + r.getIdSolicitante() + "'/>");
			html.append("<hidden id='idResponsavel' value='" + r.getIdResponsavel() + "'/>");
			if (permFluxoDto == null) {
				permFluxoDto = permissoesFluxoService.findByUsuarioAndFluxo(usuario, fluxoDto);
				if (permFluxoDto != null)
					mapPermissoes.put("" + fluxoDto.getIdFluxo(), permFluxoDto);
			}

			html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
					+ "/imagens/search.png' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.consultasolicitacaoincidente") + "' onclick='consultarOcorrencias(\""
					+ r.getIdSolicitacaoServico() + "\")' style='cursor:pointer'/></td>");
			if (permFluxoDto != null && permFluxoDto.getReabrir() != null && permFluxoDto.getReabrir().equalsIgnoreCase("S")) {
				if (r.encerrada()) {
					html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
							+ "/imagens/reabrir.jpg' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.reabrirsol") + "' onclick='reabrir(\"" + r.getIdSolicitacaoServico()
							+ "\")' style='cursor:pointer'/></td>");
				} else {
					html.append("<td>&nbsp;</td>");
				}
			} else {
				html.append("<td>&nbsp;</td>");
			}
			ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
			Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, r.getIdSolicitacaoServico());

			if (colAnexos != null && !colAnexos.isEmpty()) {
				html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
						+ "/imagens/Paperclip4-black-32.png' width='16' height='16' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos")
						+ "' id='btAnexos' onclick='anexos(\"" + r.getIdSolicitacaoServico() + "\")' style='cursor:pointer'/></td>");
			} else {
				html.append("<td><img src='" + br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")
						+ "/imagens/file.png' width='16' height='16' border='0' title='" + UtilI18N.internacionaliza(request, "pesquisasolicitacao.visualizaranexos")
						+ "' id='btAnexos' onclick='anexos(\"" + r.getIdSolicitacaoServico() + "\")' style='cursor:pointer'/></td>");
			}
			html.append("<td>" + r.getIdSolicitacaoServico() + "</td>");
			html.append("<td>" + r.getNomeSolicitante() + "</td>");
			html.append("<td>" + r.getResponsavel() + "</td>");
			html.append("<td>" + r.getNomeTipoDemandaServico() + "</td>");
			if (r.getSeqReabertura() == null || r.getSeqReabertura().intValue() == 0) {
				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(r.getDataHoraSolicitacao()) + "</td>");
			} else {
				html.append("<td id='dataHoraSolicitacao'>" + UtilDatas.formatTimestamp(r.getDataHoraSolicitacao()) + "<br><br>"
						+ UtilI18N.internacionaliza(request, "solicitacaoServico.seqreabertura") + ": <span style='color:red'><b>" + r.getSeqReabertura() + "</b></span></td>");
			}

			boolean slaACombinar = (r.getPrazoHH() == null || r.getPrazoHH() == 0) && (r.getPrazoMM() == null || r.getPrazoMM() == 0);

			if (slaACombinar) {
				html.append("<td>" + UtilI18N.internacionaliza(request, "citcorpore.comum.aCombinar") + "</td>");
			} else {
				html.append("<td>" + r.getPrazoHH() + ":" + r.getPrazoMM() + "</td>");
			}

			html.append("<td>" + UtilStrings.nullToVazio(StringEscapeUtils.unescapeJavaScript(r.getDescricaoSemFormatacao())) + "</td>");
			html.append("<td>" + UtilStrings.nullToVazio(StringEscapeUtils.unescapeJavaScript(r.getResposta())) + "</td>");
			html.append("<td>" + r.getNomeServico().replace(".", ". ") + "</td>");
			
			/*
			 * -- Para internacionalizar corretamente --
			 * A query possui um case when para colocar "Em Andamento" se a situação for "EmAndamento"
			 * Essa query é utilizada em outros programas
			 * Por esse motivo, o mais simples e menos arriscado é colocar esse teste em vez de 
			 * mudar a query e arriscar bagunçar em outro lugar
			 * Quando a query for melhorada retirando o case when, esse if não será mais utilizado
			 * Uelen Paulo - 26/09/2013
			 */
			if (r.getSituacao().equalsIgnoreCase("Em Andamento")) {
				
				r.setSituacao("EmAndamento");
			}
			
			html.append("<td>" + r.obterSituacaoInternacionalizada(request) + "</td>");
			
			if (r.getDataHoraLimite() != null && !slaACombinar)
				html.append("<td>" + UtilDatas.formatTimestamp(r.getDataHoraLimite()) + "</td>");
			else
				html.append("<td>&nbsp;</td>");
			html.append("<td>" + UtilStrings.nullToVazio(r.getSiglaGrupo()) + "</td>");
			String d = "";
			if (r.getDataHoraFim() != null) {
				d = UtilDatas.formatTimestamp(r.getDataHoraFim());
			}
			html.append("<td id='dataHoraFimSolicitacao'>" + d + "</td>");
			if (r.getSituacao().equals("EmAndamento")) {
				script.append("temporizador.addOuvinte(new Solicitacao('tempoRestante" + r.getIdSolicitacaoServico() + "', " + "'barraProgresso" + r.getIdSolicitacaoServico() + "', " + "'"
						+ r.getDataHoraSolicitacao() + "', '" + r.getDataHoraLimite() + "'));");
			}
			html.append("<td><label id='tempoRestante" + r.getIdSolicitacaoServico() + "'></label>");
			html.append("<div id='barraProgresso" + r.getIdSolicitacaoServico() + "'></div></td>");
			html.append("</tr>");
		}
		html.append("</table>");
		return html.toString();
	}

	public void reabre(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoDTO solicitacaoServicoDto = new SolicitacaoServicoDTO();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico() == null) {
			document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.informeReabrir"));
			return;
		} else {
			solicitacaoServicoDto.setIdSolicitacaoServico(pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico());
			solicitacaoServicoDto = (SolicitacaoServicoDTO) solicitacaoService.restore(solicitacaoServicoDto);
			
			int numDias;
			int numDiasParametro;
			boolean permiteReabrir = false;
			
			try {

				numDias = UtilDatas.dataDiff(solicitacaoServicoDto.getDataHoraFim(),UtilDatas.getDataAtual());
				numDiasParametro = Integer.parseInt(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.DIAS_LIMITE_REABERTURA_INCIDENTE_REQUISICAO, null));
				
				if (numDias <= numDiasParametro){
					permiteReabrir = true;
				}
			} catch (NumberFormatException ne) {
				document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.prazoReaberturaNaoConfigurado"));
				return;
			}
			
			if (!permiteReabrir){
				document.alert(UtilI18N.internacionaliza(request, "pesquisasolicitacao.prazoReaberturaExcedido"));
				return;
			}else{
				solicitacaoService.reabre(usuario, solicitacaoServicoDto);
			}
		}

		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.reaberta"));
		document.executeScript("filtrar()");
	}

	public void imprimirRelatorio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		FaseServicoDTO faseDto = new FaseServicoDTO();
		FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		UsuarioDTO usuarioDto = new UsuarioDTO();
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		UnidadeDTO unidadeDto = new UnidadeDTO();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (pesquisaSolicitacaoServicoDto.getDataInicio() == null) {
			pesquisaSolicitacaoServicoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
		}

		if (pesquisaSolicitacaoServicoDto.getDataFim() == null) {
			pesquisaSolicitacaoServicoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		}

		if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() == null) {
			pesquisaSolicitacaoServicoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
		}

		if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() == null) {
			pesquisaSolicitacaoServicoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		} else {
			document.executeScript("$('#situacao').attr('disabled', 'true')");
			pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
		}

		ArrayList<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService.listaSolicitacaoServicoPorCriterios(pesquisaSolicitacaoServicoDto);

		if (listaSolicitacaoServicoPorCriterios == null || listaSolicitacaoServicoPorCriterios.size() == 0) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			return;
		}

		for (SolicitacaoServicoDTO solicitacaoServico : listaSolicitacaoServicoPorCriterios) {
			solicitacaoServico.setResposta(StringEscapeUtils.unescapeJavaScript(solicitacaoServico.getResposta()));
			if ((solicitacaoServico.getPrazoHH() == null || solicitacaoServico.getPrazoHH() == 0) && (solicitacaoServico.getPrazoMM() == null || solicitacaoServico.getPrazoMM() == 0)) {
				solicitacaoServico.setSlaACombinar("S");
			} else {
				solicitacaoServico.setSlaACombinar("N");
			}
			solicitacaoServico.setDescricaoSemFormatacao(StringEscapeUtils.unescapeJavaScript(solicitacaoServico.getDescricaoSemFormatacao()));
			solicitacaoServico.setDescricao(StringEscapeUtils.unescapeJavaScript(solicitacaoServico.getDescricao()));
			
			/*
			 * Internacionaliza a situação
			 */
			solicitacaoServico.setSituacao(solicitacaoServico.obterSituacaoInternacionalizada(request));
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.caminho_real_app + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaSolicitacaoServico.jasper";
		String diretorioReceita = CITCorporeUtil.caminho_real_app + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "citcorporeRelatorio.pesquisaSolicitacoesServicos"));
		parametros.put("CIDADE", "Brasília,");
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", pesquisaSolicitacaoServicoDto.getDataInicio());
		parametros.put("dataFim", pesquisaSolicitacaoServicoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("exibirCampoDescricao", StringEscapeUtils.unescapeJavaScript(pesquisaSolicitacaoServicoDto.getExibirCampoDescricao()));
		parametros.put("quantidade", listaSolicitacaoServicoPorCriterios.size());
		parametros.put("criado_por", UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + ":");
		
		if (pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao() != null && !pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao().equalsIgnoreCase("")) {
			parametros.put("nomeItemConfiguracao", pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao());
		} else {
			parametros.put("nomeItemConfiguracao", null);
		}
		if (pesquisaSolicitacaoServicoDto.getNomeSolicitante() != null && !pesquisaSolicitacaoServicoDto.getNomeSolicitante().equalsIgnoreCase("")) {
			parametros.put("nomeSolicitante", pesquisaSolicitacaoServicoDto.getNomeSolicitante());
		} else {
			parametros.put("nomeSolicitante", null);
		}

		if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
			tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
			tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
			pesquisaSolicitacaoServicoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
			parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
		} else {
			parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
		}
		if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
			parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
		} else {
			parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
		}
		if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equals("")) {
			parametros.put("situacao", pesquisaSolicitacaoServicoDto.getSituacaoInternacionalizada(request));
		} else {
			parametros.put("situacao", null);
		}

		if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
			grupoDto.setIdGrupo(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			pesquisaSolicitacaoServicoDto.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
		}
		if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
			origemDto.setIdOrigem(pesquisaSolicitacaoServicoDto.getIdOrigem());
			origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
			pesquisaSolicitacaoServicoDto.setOrigem(StringEscapeUtils.unescapeJavaScript(origemDto.getDescricao()));
			parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
		} else {
			parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
		}
		if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
			faseDto.setIdFase(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
			faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
			pesquisaSolicitacaoServicoDto.setFaseAtual(faseDto.getNomeFase());
			parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
		} else {
			parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
		}
		if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
			prioridadeDto.setIdPrioridade(pesquisaSolicitacaoServicoDto.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			pesquisaSolicitacaoServicoDto.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
		} else {
			parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
		}

		if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(pesquisaSolicitacaoServicoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}

		if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
			usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdResponsavel());
			usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
			parametros.put("responsavel", usuarioDto.getNomeUsuario());
		} else {
			parametros.put("responsavel", null);
		}
		if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
			unidadeDto.setIdUnidade(pesquisaSolicitacaoServicoDto.getIdUnidade());
			unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
			parametros.put("unidade", unidadeDto.getNome());
		} else {
			parametros.put("unidade", null);
		}
		
		try {
			
			
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);

			// Instancia o arquivo de swap, informando:
			// Diretorio,
			// Tamanho de cada bloco (4kb)
			// Numero mínimo de blocos que o swap será aumentado sempre que
			// estiver cheio
			// JRSwapFile arquivoSwap = new JRSwapFile(diretorioReceita, 4096, 100);

			// Instancia o virtualizador
			// JRAbstractLRUVirtualizer virtualizer = new
			// JRSwapFileVirtualizer(25, arquivoSwap, true);

			JRAbstractLRUVirtualizer virtualizer = new JRGzipVirtualizer(50);

			// Seta o parametro REPORT_VIRTUALIZER com a instância da
			// virtualização
			parametros.put(JRParameter.REPORT_VIRTUALIZER, virtualizer);

			// Preenche o relatório e exibe numa GUI
			Timestamp ts1 = UtilDatas.getDataHoraAtual();
			JasperPrint jp = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource);
			Timestamp ts2 = UtilDatas.getDataHoraAtual();
			double tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(ts2, ts1);
			System.out.println("########## Tempo fillReport: " + tempo);

			JasperExportManager.exportReportToPdfFile(jp, diretorioReceita + "/RelatorioSolicitacaoServico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf");
			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioSolicitacaoServico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
			// JasperViewer.viewReport(jp,false);
			virtualizer = null;
			dataSource = null;
			listaSolicitacaoServicoPorCriterios = null;
			System.gc();
		} catch (OutOfMemoryError e) {
			/*
			 * Desenvolvedor: Thiago Matias - Data: 30/10/2013 - Horário: 15h35min - ID Citsmart: 122665 - Motivo/Comentário: alterando o a chave de citcorpore.erro.memoria para
			 * citsmart.erro.memoria
			 */
			document.alert(UtilI18N.internacionaliza(request, "citsmart.erro.memoria"));
		}
		/*
		 * JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);
		 * 
		 * JasperPrint print = JasperFillManager.fillReport(caminhoJasper, parametros, dataSource); JasperExportManager.exportReportToPdfFile(print, diretorioReceita + "/RelatorioSolicitacaoServico" +
		 * strCompl + "_" + usuario.getIdUsuario() + ".pdf");
		 * 
		 * document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS +
		 * "/RelatorioSolicitacaoServico" + strCompl + "_" + usuario.getIdUsuario() + ".pdf')");
		 */
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void imprimirRelatorioXls(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = ((HttpServletRequest) request).getSession();
		PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();
		SolicitacaoServicoService solicitacaoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
		TipoDemandaServicoDTO tipoDemandaServicoDto = new TipoDemandaServicoDTO();
		TipoDemandaServicoService tipoDemandaServicoService = (TipoDemandaServicoService) ServiceLocator.getInstance().getService(TipoDemandaServicoService.class, null);
		GrupoDTO grupoDto = new GrupoDTO();
		GrupoService grupoSegurancaService = (GrupoService) ServiceLocator.getInstance().getService(GrupoService.class, null);
		OrigemAtendimentoDTO origemDto = new OrigemAtendimentoDTO();
		OrigemAtendimentoService origemAtendimentoService = (OrigemAtendimentoService) ServiceLocator.getInstance().getService(OrigemAtendimentoService.class, null);
		FaseServicoDTO faseDto = new FaseServicoDTO();
		FaseServicoService faseServicoService = (FaseServicoService) ServiceLocator.getInstance().getService(FaseServicoService.class, null);
		PrioridadeDTO prioridadeDto = new PrioridadeDTO();
		PrioridadeService prioridadeService = (PrioridadeService) ServiceLocator.getInstance().getService(PrioridadeService.class, null);
		ContratoDTO contratoDto = new ContratoDTO();
		ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
		UsuarioDTO usuarioDto = new UsuarioDTO();
		UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
		UnidadeDTO unidadeDto = new UnidadeDTO();
		UnidadeService unidadeService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);

		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			return;
		}

		if (pesquisaSolicitacaoServicoDto.getDataInicio() == null) {
			pesquisaSolicitacaoServicoDto.setDataInicio(UtilDatas.strToSQLDate("01/01/1970"));
		}

		if (pesquisaSolicitacaoServicoDto.getDataFim() == null) {
			pesquisaSolicitacaoServicoDto.setDataFim(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		}

		if (pesquisaSolicitacaoServicoDto.getDataInicioFechamento() == null) {
			pesquisaSolicitacaoServicoDto.setDataInicioFechamento(UtilDatas.strToSQLDate("01/01/1970"));
		} else {
			pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
		}

		if (pesquisaSolicitacaoServicoDto.getDataFimFechamento() == null) {
			pesquisaSolicitacaoServicoDto.setDataFimFechamento(new java.sql.Date(UtilDatas.alteraData(UtilDatas.getDataAtual(), 365, Calendar.DAY_OF_YEAR).getTime()));
		} else {
			pesquisaSolicitacaoServicoDto.setSituacao("Fechada");
		}

		ArrayList<SolicitacaoServicoDTO> listaSolicitacaoServicoPorCriterios = (ArrayList<SolicitacaoServicoDTO>) solicitacaoService.listaSolicitacaoServicoPorCriterios(pesquisaSolicitacaoServicoDto);

		if (listaSolicitacaoServicoPorCriterios == null || listaSolicitacaoServicoPorCriterios.size() == 0) {
			document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.relatorioVazio"));
			return;
		}

		for (SolicitacaoServicoDTO solicitacaoServico : listaSolicitacaoServicoPorCriterios) {
			solicitacaoServico.setResposta(StringEscapeUtils.unescapeJavaScript(solicitacaoServico.getResposta()));
			if ((solicitacaoServico.getPrazoHH() == null || solicitacaoServico.getPrazoHH() == 0) && (solicitacaoServico.getPrazoMM() == null || solicitacaoServico.getPrazoMM() == 0)) {
				solicitacaoServico.setSlaACombinar("S");
			} else {
				solicitacaoServico.setSlaACombinar("N");
			}
			solicitacaoServico.setDescricaoSemFormatacao(StringEscapeUtils.unescapeJavaScript(solicitacaoServico.getDescricaoSemFormatacao()));
			solicitacaoServico.setDescricao(StringEscapeUtils.unescapeJavaScript(solicitacaoServico.getDescricao()));
			
			/*
			 * Internacionaliza a situação
			 */
			solicitacaoServico.setSituacao(solicitacaoServico.obterSituacaoInternacionalizada(request));
		}

		Date dt = new Date();
		String strCompl = "" + dt.getTime();
		String caminhoJasper = CITCorporeUtil.caminho_real_app + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaSolicitacaoServico.jasper";
		String diretorioReceita = CITCorporeUtil.caminho_real_app + "/tempFiles";
		String diretorioRelativoOS = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/tempFiles";

		Map<String, Object> parametros = new HashMap<String, Object>();
		parametros = UtilRelatorio.trataInternacionalizacaoLocale(session, parametros);
		
		parametros.put("TITULO_RELATORIO", UtilI18N.internacionaliza(request, "citcorporeRelatorio.pesquisaSolicitacoesServicos"));
		parametros.put("CIDADE", "Brasília,");
		parametros.put("DATA_HORA", UtilDatas.getDataHoraAtual());
		parametros.put("NOME_USUARIO", usuario.getNomeUsuario());
		parametros.put("dataInicio", pesquisaSolicitacaoServicoDto.getDataInicio());
		parametros.put("dataFim", pesquisaSolicitacaoServicoDto.getDataFim());
		parametros.put("Logo", LogoRel.getFile());
		parametros.put("exibirCampoDescricao", pesquisaSolicitacaoServicoDto.getExibirCampoDescricao());
		parametros.put("quantidade", listaSolicitacaoServicoPorCriterios.size());
		parametros.put("criado_por", UtilI18N.internacionaliza(request, "citcorpore.comum.criadopor") + ":");
		
		if (pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao() != null && !pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao().equalsIgnoreCase("")) {
			parametros.put("nomeItemConfiguracao", pesquisaSolicitacaoServicoDto.getNomeItemConfiguracao());
		} else {
			parametros.put("nomeItemConfiguracao", null);
		}
		if (pesquisaSolicitacaoServicoDto.getNomeSolicitante() != null && !pesquisaSolicitacaoServicoDto.getNomeSolicitante().equalsIgnoreCase("")) {
			parametros.put("nomeSolicitante", pesquisaSolicitacaoServicoDto.getNomeSolicitante());
		} else {

			parametros.put("nomeSolicitante", null);
		}

		if (pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico() != null) {
			tipoDemandaServicoDto.setIdTipoDemandaServico(pesquisaSolicitacaoServicoDto.getIdTipoDemandaServico());
			tipoDemandaServicoDto = (TipoDemandaServicoDTO) tipoDemandaServicoService.restore(tipoDemandaServicoDto);
			pesquisaSolicitacaoServicoDto.setNomeTipoDemandaServico(tipoDemandaServicoDto.getNomeTipoDemandaServico());
			parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
		} else {
			parametros.put("tipo", pesquisaSolicitacaoServicoDto.getNomeTipoDemandaServico());
		}
		if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa() != null) {
			parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
		} else {
			parametros.put("numero", pesquisaSolicitacaoServicoDto.getIdSolicitacaoServicoPesquisa());
		}
		if (pesquisaSolicitacaoServicoDto.getSituacao() != null && !pesquisaSolicitacaoServicoDto.getSituacao().equals("")) {
			parametros.put("situacao", pesquisaSolicitacaoServicoDto.getSituacaoInternacionalizada(request));
		} else {
			parametros.put("situacao", null);
		}

		if (pesquisaSolicitacaoServicoDto.getIdGrupoAtual() != null) {
			grupoDto.setIdGrupo(pesquisaSolicitacaoServicoDto.getIdGrupoAtual());
			grupoDto = (GrupoDTO) grupoSegurancaService.restore(grupoDto);
			pesquisaSolicitacaoServicoDto.setGrupoAtual(grupoDto.getSigla());
			parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
		} else {
			parametros.put("grupoSolucionador", pesquisaSolicitacaoServicoDto.getGrupoAtual());
		}
		if (pesquisaSolicitacaoServicoDto.getIdOrigem() != null) {
			origemDto.setIdOrigem(pesquisaSolicitacaoServicoDto.getIdOrigem());
			origemDto = (OrigemAtendimentoDTO) origemAtendimentoService.restore(origemDto);
			pesquisaSolicitacaoServicoDto.setOrigem(StringEscapeUtils.unescapeJavaScript(origemDto.getDescricao()));
			parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
		} else {
			parametros.put("origem", pesquisaSolicitacaoServicoDto.getOrigem());
		}
		if (pesquisaSolicitacaoServicoDto.getIdFaseAtual() != null) {
			faseDto.setIdFase(pesquisaSolicitacaoServicoDto.getIdFaseAtual());
			faseDto = (FaseServicoDTO) faseServicoService.restore(faseDto);
			pesquisaSolicitacaoServicoDto.setFaseAtual(faseDto.getNomeFase());
			parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
		} else {
			parametros.put("fase", pesquisaSolicitacaoServicoDto.getFaseAtual());
		}
		if (pesquisaSolicitacaoServicoDto.getIdPrioridade() != null) {
			prioridadeDto.setIdPrioridade(pesquisaSolicitacaoServicoDto.getIdPrioridade());
			prioridadeDto = (PrioridadeDTO) prioridadeService.restore(prioridadeDto);
			pesquisaSolicitacaoServicoDto.setPrioridade(prioridadeDto.getNomePrioridade());
			parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
		} else {
			parametros.put("prioridade", pesquisaSolicitacaoServicoDto.getPrioridade());
		}

		if (pesquisaSolicitacaoServicoDto.getIdContrato() != null) {
			contratoDto.setIdContrato(pesquisaSolicitacaoServicoDto.getIdContrato());
			contratoDto = (ContratoDTO) contratoService.restore(contratoDto);
			parametros.put("contrato", contratoDto.getNumero());
		} else {
			parametros.put("contrato", contratoDto.getNumero());
		}

		if (pesquisaSolicitacaoServicoDto.getIdResponsavel() != null) {
			usuarioDto.setIdUsuario(pesquisaSolicitacaoServicoDto.getIdResponsavel());
			usuarioDto = (UsuarioDTO) usuarioService.restore(usuarioDto);
			parametros.put("responsavel", usuarioDto.getNomeUsuario());
		} else {
			parametros.put("responsavel", null);
		}
		if (pesquisaSolicitacaoServicoDto.getIdUnidade() != null) {
			unidadeDto.setIdUnidade(pesquisaSolicitacaoServicoDto.getIdUnidade());
			unidadeDto = (UnidadeDTO) unidadeService.restore(unidadeDto);
			parametros.put("unidade", unidadeDto.getNome());
		} else {
			parametros.put("unidade", null);
		}

		try {
			JRDataSource dataSource = new JRBeanCollectionDataSource(listaSolicitacaoServicoPorCriterios);

			JasperDesign desenho = JRXmlLoader.load(CITCorporeUtil.caminho_real_app + Constantes.getValue("CAMINHO_RELATORIOS") + "RelatorioPesquisaSolicitacaoServicoXls.jrxml");

			JasperReport relatorio = JasperCompileManager.compileReport(desenho);

			JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, dataSource);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, impressao);
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, diretorioReceita + "/RelatorioPesquisaSolicitacaoServicoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls");

			exporter.exportReport();

			document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/printPDF/printPDF.jsp?url=" + diretorioRelativoOS
					+ "/RelatorioPesquisaSolicitacaoServicoXls" + strCompl + "_" + usuario.getIdUsuario() + ".xls')");
		} catch (OutOfMemoryError e) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.erro.erroServidor"));
		}
		document.getJanelaPopupById("JANELA_AGUARDE_MENU").hide();

	}

	public void restoreUpload(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {

		request.getSession(true).setAttribute("colUploadsGED", null);
		/* Realida o refresh do iframe */
		document.executeScript("document.getElementById('fraUpload_uploadAnexos').contentWindow.location.reload(true)");

		usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		PesquisaSolicitacaoServicoDTO pesquisaSolicitacaoServicoDto = (PesquisaSolicitacaoServicoDTO) document.getBean();

		if (pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico() == null) {
			return;
		}
		ControleGEDService controleGedService = (ControleGEDService) ServiceLocator.getInstance().getService(ControleGEDService.class, null);
		Collection colAnexos = controleGedService.listByIdTabelaAndID(ControleGEDDTO.TABELA_SOLICITACAOSERVICO, pesquisaSolicitacaoServicoDto.getIdSolicitacaoServico());
		Collection colAnexosUploadDTO = controleGedService.convertListControleGEDToUploadDTO(colAnexos);

		request.getSession(true).setAttribute("colUploadsGED", colAnexosUploadDTO);
		document.executeScript("$('#POPUP_menuAnexos').dialog('open');");
	}

}