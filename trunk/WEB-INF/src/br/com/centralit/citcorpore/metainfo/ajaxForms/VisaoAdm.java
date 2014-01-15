package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.MatrizVisaoDTO;
import br.com.centralit.citcorpore.bean.UploadDTO;
import br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.CamposObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioLigacaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.ValorVisaoCamposNegocioDTO;
import br.com.centralit.citcorpore.metainfo.bean.VinculoVisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO;
import br.com.centralit.citcorpore.metainfo.negocio.BotaoAcaoVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.CamposObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.GrupoVisaoCamposNegocioLigacaoService;
import br.com.centralit.citcorpore.metainfo.negocio.GrupoVisaoCamposNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.GrupoVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.HtmlCodeVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.ObjetoNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.ScriptsVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.ValorVisaoCamposNegocioService;
import br.com.centralit.citcorpore.metainfo.negocio.VinculoVisaoService;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoRelacionadaService;
import br.com.centralit.citcorpore.metainfo.negocio.VisaoService;
import br.com.centralit.citcorpore.metainfo.util.MetaUtil;
import br.com.centralit.citcorpore.negocio.DataBaseMetaDadosService;
import br.com.centralit.citcorpore.negocio.MatrizVisaoService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;
import br.com.citframework.util.UtilZip;
import br.com.citframework.util.WebUtil;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class VisaoAdm extends AjaxFormAction {

	private GrupoVisaoCamposNegocioService grupoVisaoCamposNegocioService;
	private CamposObjetoNegocioService camposObjetoNegocioService;
	private ObjetoNegocioService objetoNegocioService;
	private VinculoVisaoService vinculoVisaoService;
	private VisaoService visaoService;
	private GrupoVisaoService grupoVisaoService;
	private ValorVisaoCamposNegocioService valorVisaoCamposNegocioService;
	private GrupoVisaoCamposNegocioLigacaoService grupoVisaoCamposNegocioLigacaoService;
	private ScriptsVisaoService scriptsVisaoService;
	private HtmlCodeVisaoService htmlCodeVisaoService;
	private BotaoAcaoVisaoService botaoAcaoVisaoService;
	private MatrizVisaoService matrizVisaoService;
	private VisaoRelacionadaService visaoRelacionadaService;

	@Override
	public Class getBeanClass() {
		return GrupoVisaoCamposNegocioDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.getSession().setAttribute("visao", null);
		request.getSession().setAttribute("scripts", null);
		request.getSession().setAttribute("htmlCodes", null);
		request.getSession().setAttribute("botoes", null);

		request.getSession(true).setAttribute("colUploadsGED", null);

		document.executeScript("uploadAnexos.refresh()");

		Collection col = getObjetoNegocioService().listAtivos();

		if (col != null) {
			document.getSelectById("idObjetoNegocio").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idObjetoNegocioLigacao").addOption("", UtilI18N.internacionaliza(request,"citcorpore.comum.selecione"));

			document.getSelectById("idObjetoNegocio").addOptions(col, "idObjetoNegocio", "nomeObjetoNegocio", null);
			document.getSelectById("idObjetoNegocioLigacao").addOptions(col, "idObjetoNegocio", "nomeObjetoNegocio", null);
		}

		String strTemplate = "<li><a href='#{href}'>#{label}</a> <span class='ui-icon ui-icon-close'>"+ UtilI18N.internacionaliza(request, "visaoAdm.removeTab")+"</span></li>";
		request.setAttribute("templateTab", strTemplate);

		Collection colVisoes = getVisaoService().listAtivos();
		request.setAttribute("relacaoVisoes", colVisoes);

		request.setAttribute("objetosNegocio", col);

		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		if (grupoVisaoCamposNegocioDTO.getIdVisao() != null) {
			restore(document, request, response);
		}
		
		document.executeScript("$('#loading_overlay').hide();");
	}

	public void addItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (col == null) {
			col = new ArrayList();
		}
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		col.add(grupoVisaoCamposNegocioDTO);

		request.getSession().setAttribute("visao", col);

		draw(document, request, response);
		document.executeScript("$( '#POPUP_OBJ' ).dialog( 'close' );");
	}

	public void atualizaItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (col == null) {
			col = new ArrayList();
		}
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		int i = 0;
		Collection colFinal = new ArrayList();
		for (Iterator it = col.iterator(); it.hasNext();) {
			i++;
			GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it.next();
			if (i == grupoVisaoCamposNegocioDTO.getNumeroEdicao().intValue()) {
				colFinal.add(grupoVisaoCamposNegocioDTO);
			} else {
				colFinal.add(grupoVisaoCamposNegocioAux);
			}
		}

		request.getSession().setAttribute("visao", colFinal);

		draw(document, request, response);
		document.executeScript("$( '#POPUP_OBJ' ).dialog( 'close' );");
	}

	public void deleteItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (col == null) {
			col = new ArrayList();
		}
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		int i = 0;
		Collection colFinal = new ArrayList();
		for (Iterator it = col.iterator(); it.hasNext();) {
			i++;
			GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it.next();
			if (i != grupoVisaoCamposNegocioDTO.getNumeroEdicao().intValue()) {
				colFinal.add(grupoVisaoCamposNegocioAux);
			}
		}

		request.getSession().setAttribute("visao", colFinal);

		draw(document, request, response);
		document.executeScript("$( '#POPUP_OBJ' ).dialog( 'close' );");
	}

	public void deleteVisao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		request.getSession(true).setAttribute("colUploadsGED", null);

		document.executeScript("uploadAnexos.refresh()");

		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();

		VisaoDTO visaoDto = new VisaoDTO();
		visaoDto.setIdVisao(grupoVisaoCamposNegocioDTO.getIdVisao());
		visaoDto.setTipoVisao(grupoVisaoCamposNegocioDTO.getTipoVisao());
		try {
			getVisaoService().deleteVisao(visaoDto);
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.visaoExcluidaSucesso"));

			// Limpa tela
			document.executeScript("limparTela()");

		} catch (Exception e) {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.naoFoiPossivelExcluirVisao"));
			e.printStackTrace();
		}

	}

	public void atualizaScript(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		HashMap mapScripts = (HashMap) request.getSession().getAttribute("scripts");
		if (mapScripts == null) {
			mapScripts = new HashMap();
		}
		if (grupoVisaoCamposNegocioDTO.getScryptType() == null) {
			grupoVisaoCamposNegocioDTO.setScryptType("");
		}
		mapScripts.put(grupoVisaoCamposNegocioDTO.getScryptType(), grupoVisaoCamposNegocioDTO.getScript());
		request.getSession().setAttribute("scripts", mapScripts);
		document.executeScript("showMessageScript('<i18n:message key='visaoAdm.scriptAtualizadoSucesso.NecessarioGravarNoBanco'/>')");
	}

	public void atualizaHTMLCode(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		HashMap mapHtmlCode = (HashMap) request.getSession().getAttribute("htmlCodes");
		if (mapHtmlCode == null) {
			mapHtmlCode = new HashMap();
		}
		if (grupoVisaoCamposNegocioDTO.getHtmlCodeType() == null) {
			grupoVisaoCamposNegocioDTO.setHtmlCodeType("");
		}
		mapHtmlCode.put(grupoVisaoCamposNegocioDTO.getHtmlCodeType(), grupoVisaoCamposNegocioDTO.getHtmlCode());
		request.getSession().setAttribute("htmlCodes", mapHtmlCode);		
		document.executeScript("showMessageHtmlCode('<i18n:message key='visaoAdm.htmlCodeAtualizadoSucesso.NecessarioGravarNoBanco'/>')");
	}

	public void recuperaScript(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		HashMap mapScripts = (HashMap) request.getSession().getAttribute("scripts");
		if (mapScripts == null) {
			mapScripts = new HashMap();
		}
		if (grupoVisaoCamposNegocioDTO.getScryptType() == null) {
			grupoVisaoCamposNegocioDTO.setScryptType("");
		}
		String script = (String) mapScripts.get(grupoVisaoCamposNegocioDTO.getScryptType());
		document.getTextAreaById("script").setValue(script);
		document.executeScript("showMessageScript('<i18n:message key='visaoAdm.scriptRecuperado'/>')");
	}

	public void recuperaHtmlCode(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		HashMap mapHtmlCode = (HashMap) request.getSession().getAttribute("htmlCodes");
		if (mapHtmlCode == null) {
			mapHtmlCode = new HashMap();
		}
		if (grupoVisaoCamposNegocioDTO.getHtmlCodeType() == null) {
			grupoVisaoCamposNegocioDTO.setHtmlCodeType("");
		}
		String htmlCode = (String) mapHtmlCode.get(grupoVisaoCamposNegocioDTO.getHtmlCodeType());
		document.getTextAreaById("htmlCodePopupHtmlCode").setValue(htmlCode);
		document.executeScript("showMessageHtmlCode('<i18n:message key='visaoAdm.htmlCodeRecuperado'/>')");
	}

	public void atualizaBotao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection colBotoes = (Collection) request.getSession().getAttribute("botoes");
		if (colBotoes == null) {
			colBotoes = new ArrayList();
		}
		int indice = -1;
		int i = 0;
		BotaoAcaoVisaoDTO botaoAcaoVisaoDTO = null;
		for (Iterator it = colBotoes.iterator(); it.hasNext();) {
			BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
			if (botaoAcaoVisaoAux.getTexto().equalsIgnoreCase(grupoVisaoCamposNegocioDTO.getTexto())) {
				indice = i;
				botaoAcaoVisaoDTO = botaoAcaoVisaoAux;
				break;
			}
			i++;
		}
		if (botaoAcaoVisaoDTO == null) {
			botaoAcaoVisaoDTO = new BotaoAcaoVisaoDTO();
		}
		botaoAcaoVisaoDTO.setTexto(grupoVisaoCamposNegocioDTO.getTexto());
		botaoAcaoVisaoDTO.setAcao(grupoVisaoCamposNegocioDTO.getAcao());
		botaoAcaoVisaoDTO.setScript(grupoVisaoCamposNegocioDTO.getScript());
		botaoAcaoVisaoDTO.setHint(grupoVisaoCamposNegocioDTO.getHint());
		botaoAcaoVisaoDTO.setIcone(grupoVisaoCamposNegocioDTO.getIcone());
		if (botaoAcaoVisaoDTO.getTexto() == null || botaoAcaoVisaoDTO.getTexto().trim().equalsIgnoreCase("")) {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.campoObrigatorioTexto"));
			return;
		}
		if (botaoAcaoVisaoDTO.getAcao() == null || botaoAcaoVisaoDTO.getAcao().trim().equalsIgnoreCase("")) {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.campoObrigatorioAcao"));
			return;
		}
		if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_SCRIPT)) {
			if (botaoAcaoVisaoDTO.getScript() == null || botaoAcaoVisaoDTO.getScript().trim().equalsIgnoreCase("")) {
				document.alert(UtilI18N.internacionaliza(request, "visaoAdm.campoObrigatorioScript"));
				return;
			}
		}
		i = 0;
		Collection colBotoesFinal = new ArrayList();
		if (indice > -1) {
			for (Iterator it = colBotoes.iterator(); it.hasNext();) {
				BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
				if (i == indice) {
					colBotoesFinal.add(botaoAcaoVisaoDTO);
				} else {
					colBotoesFinal.add(botaoAcaoVisaoAux);
				}
				i++;
			}
		} else {
			if (colBotoes != null && colBotoes.size() > 0) {
				colBotoesFinal.addAll(colBotoes);
			}
			colBotoesFinal.add(botaoAcaoVisaoDTO);
		}

		request.getSession().setAttribute("botoes", colBotoesFinal);

		document.getSelectById("botaoCadastrado").removeAllOptions();
		for (Iterator it = colBotoesFinal.iterator(); it.hasNext();) {
			BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
			document.getSelectById("botaoCadastrado").addOption(botaoAcaoVisaoAux.getTexto(), botaoAcaoVisaoAux.getTexto());
		}
		document.executeScript("showMessageHtmlCode('<i18n:message key='visaoAdm.botaoAtualizadoSucesso.gravarVisao'/>')");
	}

	public void excluirBotao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection colBotoes = (Collection) request.getSession().getAttribute("botoes");
		if (colBotoes == null) {
			colBotoes = new ArrayList();
		}
		int indice = -1;
		int i = 0;
		BotaoAcaoVisaoDTO botaoAcaoVisaoDTO = null;
		for (Iterator it = colBotoes.iterator(); it.hasNext();) {
			BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
			if (botaoAcaoVisaoAux.getTexto().equalsIgnoreCase(grupoVisaoCamposNegocioDTO.getTexto())) {
				indice = i;
				botaoAcaoVisaoDTO = botaoAcaoVisaoAux;
				break;
			}
			i++;
		}
		if (botaoAcaoVisaoDTO == null) {
			botaoAcaoVisaoDTO = new BotaoAcaoVisaoDTO();
		}
		botaoAcaoVisaoDTO.setTexto(grupoVisaoCamposNegocioDTO.getTexto());
		botaoAcaoVisaoDTO.setAcao(grupoVisaoCamposNegocioDTO.getAcao());
		botaoAcaoVisaoDTO.setScript(grupoVisaoCamposNegocioDTO.getScript());
		botaoAcaoVisaoDTO.setHint(grupoVisaoCamposNegocioDTO.getHint());
		botaoAcaoVisaoDTO.setIcone(grupoVisaoCamposNegocioDTO.getIcone());

		i = 0;
		Collection colBotoesFinal = new ArrayList();
		if (indice > -1) {
			for (Iterator it = colBotoes.iterator(); it.hasNext();) {
				BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
				if (i != indice) {
					colBotoesFinal.add(botaoAcaoVisaoAux);
				}
				i++;
			}
		}

		request.getSession().setAttribute("botoes", colBotoesFinal);

		document.getSelectById("botaoCadastrado").removeAllOptions();
		for (Iterator it = colBotoesFinal.iterator(); it.hasNext();) {
			BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
			document.getSelectById("botaoCadastrado").addOption(botaoAcaoVisaoAux.getTexto(), botaoAcaoVisaoAux.getTexto());
		}
		document.getForm("formBotoes").clear();
		document.executeScript("showMessageHtmlCode('<i18n:message key='visaoAdm.botaoExcluidoSucesso.gravarVisao'/>')");
	}

	public void setaOrdemBotoes(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection colBotoes = (Collection) request.getSession().getAttribute("botoes");
		if (colBotoes == null) {
			colBotoes = new ArrayList();
		}

		if (grupoVisaoCamposNegocioDTO.getOrdemBotoes() == null || grupoVisaoCamposNegocioDTO.getOrdemBotoes().trim().equalsIgnoreCase("")) {
			return;
		}

		String[] strSeqBotoes = grupoVisaoCamposNegocioDTO.getOrdemBotoes().split(",");
		if (strSeqBotoes == null) {
			return;
		}

		Collection colBotoesFinal = new ArrayList();
		for (int i = 0; i < strSeqBotoes.length; i++) {
			BotaoAcaoVisaoDTO botaoAcaoVisaoDTO = null;
			for (Iterator it = colBotoes.iterator(); it.hasNext();) {
				BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
				if (botaoAcaoVisaoAux.getTexto().equalsIgnoreCase(strSeqBotoes[i])) {
					colBotoesFinal.add(botaoAcaoVisaoAux);
					break;
				}
			}
		}

		request.getSession().setAttribute("botoes", colBotoesFinal);
		document.executeScript("showMessageHtmlCode('<i18n:message key='visaoAdm.botaoOrdenadoSucesso.gravarVisao'/>')");
		document.getSelectById("botaoCadastrado").setFocus();
	}

	public void recuperaBotao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection colBotoes = (Collection) request.getSession().getAttribute("botoes");
		if (colBotoes == null) {
			colBotoes = new ArrayList();
		}
		int indice = -1;
		int i = 0;
		if (grupoVisaoCamposNegocioDTO.getTexto() == null) {
			grupoVisaoCamposNegocioDTO.setTexto("");
		}
		BotaoAcaoVisaoDTO botaoAcaoVisaoDTO = null;
		for (Iterator it = colBotoes.iterator(); it.hasNext();) {
			BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
			if (botaoAcaoVisaoAux.getTexto().equalsIgnoreCase(grupoVisaoCamposNegocioDTO.getTexto())) {
				indice = i;
				botaoAcaoVisaoDTO = botaoAcaoVisaoAux;
				break;
			}
			i++;
		}
		document.executeScript("guardaInfoBotao()");
		document.getForm("formBotoes").clear();
		document.executeScript("setInfoBotao()");
		if (botaoAcaoVisaoDTO != null) {
			if (botaoAcaoVisaoDTO.getAcao() == null) {
				botaoAcaoVisaoDTO.setAcao("");
			}
			document.getSelectById("acao").setValue(botaoAcaoVisaoDTO.getAcao());
			document.getTextBoxById("texto").setValue(botaoAcaoVisaoDTO.getTexto());
			document.getTextAreaById("scriptBotao").setValue(botaoAcaoVisaoDTO.getScript());
			document.getTextBoxById("hint").setValue(botaoAcaoVisaoDTO.getHint());
		}
		document.executeScript("showMessageBotao('<i18n:message key='visaoAdm.botaoAcaoRecuperado'/>')");
	}

	public void draw(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (col == null) {
			col = new ArrayList();
		}
		String strDrawWorkflow = "";
		int i = 0;
		boolean bMostrarSetaFim = false;
		for (Iterator it = col.iterator(); it.hasNext();) {
			i++;
			GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it.next();
			grupoVisaoCamposNegocioDTO.setOrdem(new Integer(i));
			String nome = grupoVisaoCamposNegocioDTO.getDescricaoNegocio();
			nome = UtilStrings.nullToVazio(nome);

			bMostrarSetaFim = true;

			if (grupoVisaoCamposNegocioDTO.getTipoNegocio() == null) {
				grupoVisaoCamposNegocioDTO.setTipoNegocio("");
			}
			strDrawWorkflow += "<li id=\"item_" + i + "\" class='ui-state-default' ondblclick='editar(" + i + ")'><span class='ui-icon ui-icon-arrowthick-2-n-s'></span>";
			if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.TEXT)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/form_input_text.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.TEXTAREA)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/text-area-icon.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.SELECT)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/form_input_select_single.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.DATE)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/stock_form_date_field.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RADIO)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/form_input_radio.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/table_relationship.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.HIDDEN)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/control_panel_3.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.NUMBER)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/form_input_num.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.DECIMAL)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/form_input_dec.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.HTML)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/stock_form_image_html.png' border='0'/> " + nome;
			} else if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.CLASS_AND_METHOD)) {
				strDrawWorkflow += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/forms/stock_form_autopilots.png' border='0'/> " + nome;
			}
			strDrawWorkflow += "</li>";
		}

		document.getElementById("sortable").setInnerHTML(strDrawWorkflow);
	}

	private Collection ordenaCamposVisao(Collection col, GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO) {
		Collection colRetorno = new ArrayList();
		if (col != null) {
			String ordemCampos[] = null;
			if (grupoVisaoCamposNegocioDTO.getOrdemCampos() != null) {
				ordemCampos = grupoVisaoCamposNegocioDTO.getOrdemCampos().split(",");
			}
			if (ordemCampos != null && ordemCampos.length > 0) {
				for (int ind = 0; ind < ordemCampos.length; ind++) {
					int ordem = -1;
					try {
						ordemCampos[ind] = ordemCampos[ind].trim();
						ordem = new Integer(ordemCampos[ind]);
					} catch (Exception e) {
					}
					int i = 0;
					for (Iterator it = col.iterator(); it.hasNext();) {
						i++;
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it.next();
						if (i == ordem) {
							colRetorno.add(grupoVisaoCamposNegocioAux);
						}
					}
				}
			}
		}
		if (colRetorno == null || colRetorno.size() == 0) {
			return col;
		}
		return colRetorno;
	}

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (!grupoVisaoCamposNegocioDTO.getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
			if (col == null) {
				document.alert(UtilI18N.internacionaliza(request, "visaoAdm.naoHaCamposRelacionados"));
				return;
			}
			col = ordenaCamposVisao(col, grupoVisaoCamposNegocioDTO);
		}
		VisaoDTO visaoDto = new VisaoDTO();
		visaoDto.setIdVisao(grupoVisaoCamposNegocioDTO.getIdVisao());
		visaoDto.setDescricao(grupoVisaoCamposNegocioDTO.getDescricao());
		visaoDto.setSituacao(grupoVisaoCamposNegocioDTO.getSituacaoVisao());
		visaoDto.setTipoVisao(grupoVisaoCamposNegocioDTO.getTipoVisao());
		visaoDto.setIdentificador(grupoVisaoCamposNegocioDTO.getIdentificador());
		visaoDto.setClasseName(grupoVisaoCamposNegocioDTO.getClasseName());

		MatrizVisaoDTO matrizVisaoDTO = new MatrizVisaoDTO();
		if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
			matrizVisaoDTO.setIdObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdObjetoNegocioMatriz());
			matrizVisaoDTO.setIdCamposObjetoNegocio1(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio1());
			matrizVisaoDTO.setIdCamposObjetoNegocio2(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio2());
			matrizVisaoDTO.setIdCamposObjetoNegocio3(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio3());
			matrizVisaoDTO.setDescricaoCampo1(grupoVisaoCamposNegocioDTO.getDescricaoCampo1());
			matrizVisaoDTO.setDescricaoCampo2(grupoVisaoCamposNegocioDTO.getDescricaoCampo2());
			matrizVisaoDTO.setDescricaoCampo3(grupoVisaoCamposNegocioDTO.getDescricaoCampo3());
		}
		visaoDto.setMatrizVisaoDTO(matrizVisaoDTO);
		Collection colGrupos = new ArrayList();
		GrupoVisaoDTO grupoVisaoDTO = new GrupoVisaoDTO();
		grupoVisaoDTO.setDescricaoGrupoVisao(grupoVisaoCamposNegocioDTO.getDescricao());
		grupoVisaoDTO.setOrdem(new Integer(0));
		grupoVisaoDTO.setSituacao(visaoDto.getSituacao());
		grupoVisaoDTO.setForma("1");
		grupoVisaoDTO.setColCamposVisao(col);

		colGrupos.add(grupoVisaoDTO);
		visaoDto.setColGrupos(colGrupos);

		Collection colScripts = new ArrayList();
		HashMap mapScripts = (HashMap) request.getSession().getAttribute("scripts");
		if (mapScripts == null) {
			mapScripts = new HashMap();
		}
		Set set = mapScripts.entrySet();
		Iterator itMap = set.iterator();
		while (itMap.hasNext()) {
			Map.Entry me = (Map.Entry) itMap.next();
			ScriptsVisaoDTO scriptsVisaoDTO = new ScriptsVisaoDTO();
			scriptsVisaoDTO.setScript((String) me.getValue());
			scriptsVisaoDTO.setScriptLanguage(ScriptsVisaoDTO.JAVASCRIPT);
			if (me.getKey() != null) {
				String typeExecute = ((String) me.getKey()).substring(0, 1);
				String scriptType = ((String) me.getKey()).substring(2);
				scriptsVisaoDTO.setScryptType(scriptType);
				scriptsVisaoDTO.setTypeExecute(typeExecute);

				colScripts.add(scriptsVisaoDTO);
			}
		}

		Collection colHtmlCode = new ArrayList();
		HashMap mapHtmlCode = (HashMap) request.getSession().getAttribute("htmlCodes");
		if (mapHtmlCode == null) {
			mapHtmlCode = new HashMap();
		}
		set = mapHtmlCode.entrySet();
		itMap = set.iterator();
		while (itMap.hasNext()) {
			Map.Entry me = (Map.Entry) itMap.next();
			HtmlCodeVisaoDTO htmlCodeVisaoDTO = new HtmlCodeVisaoDTO();
			htmlCodeVisaoDTO.setHtmlCode((String) me.getValue());
			if (me.getKey() != null) {
				String htmlCodeType = (String) me.getKey();
				htmlCodeVisaoDTO.setHtmlCodeType(htmlCodeType);

				colHtmlCode.add(htmlCodeVisaoDTO);
			}
		}

		Collection colVisaoRelacionada = new ArrayList();
		if (grupoVisaoCamposNegocioDTO.getNumTabs() != null) {
			String[] str = grupoVisaoCamposNegocioDTO.getNumTabs().split(",");
			if (str != null) {
				int ordem = 0;
				for (int i = 0; i < str.length; i++) {
					if (str[i] != null && !str[i].trim().equalsIgnoreCase("")) {
						VisaoRelacionadaDTO visaoRelacionadaDTO = new VisaoRelacionadaDTO();
						int numTab = Integer.parseInt(str[i]);
						String idVisaoRel = request.getParameter("divVisaoRelacionada_" + numTab);
						int idVisaoRelInt = 0;
						try {
							idVisaoRelInt = Integer.parseInt(idVisaoRel);
						} catch (Exception e) {
						}
						if (idVisaoRelInt > 0) {
							String titulo = UtilStrings.decodeCaracteresEspeciais(request.getParameter("titulo_" + numTab));
							String tipoVinculacao = request.getParameter("tipoVinculacao_" + numTab);
							String acaoEmSelecaoPesquisa = request.getParameter("acaoEmSelecaoPesquisa_" + numTab);
							String situacaoVinc = request.getParameter("situacaoVisaoVinculada_" + numTab);
							String idObjetoNegocioNNStr = request.getParameter("idObjetoNegocioNN_" + numTab);
							if (situacaoVinc == null || situacaoVinc.trim().equalsIgnoreCase("")) {
								situacaoVinc = "A";
							}
							visaoRelacionadaDTO.setIdVisaoFilha(idVisaoRelInt);
							visaoRelacionadaDTO.setTitulo(titulo);
							visaoRelacionadaDTO.setTipoVinculacao(tipoVinculacao);
							visaoRelacionadaDTO.setAcaoEmSelecaoPesquisa(acaoEmSelecaoPesquisa);
							visaoRelacionadaDTO.setSituacao(situacaoVinc);
							visaoRelacionadaDTO.setOrdem(ordem);
							try {
								visaoRelacionadaDTO.setIdObjetoNegocioNN(new Integer(idObjetoNegocioNNStr));
							} catch (Exception e) {
							}

							String tipoVinculo = request.getParameter("tipoVinculo_" + numTab);

							Collection colVinculosVisao = new ArrayList();
							String nomeCampo = "vinculosVisaoPaiNN_" + numTab;
							String[] strValores = geraArrayRequest(request, nomeCampo);
							if (strValores != null && strValores.length > 0) {
								for (int x = 0; x < strValores.length; x++) {
									String[] strAux = strValores[x].split("#");
									VinculoVisaoDTO vinculoVisaoDTO = new VinculoVisaoDTO();
									vinculoVisaoDTO.setSeq(x);
									vinculoVisaoDTO.setTipoVinculo(tipoVinculo);
									vinculoVisaoDTO.setIdCamposObjetoNegocioPai(new Integer(strAux[0]));
									vinculoVisaoDTO.setIdCamposObjetoNegocioPaiNN(new Integer(strAux[1]));
									vinculoVisaoDTO.setControle("P");

									colVinculosVisao.add(vinculoVisaoDTO);
								}
							}
							nomeCampo = "vinculosVisaoFilhoNN_" + numTab;
							strValores = geraArrayRequest(request, nomeCampo);
							if (strValores != null && strValores.length > 0) {
								for (int x = 0; x < strValores.length; x++) {
									String[] strAux = strValores[x].split("#");
									VinculoVisaoDTO vinculoVisaoDTO = new VinculoVisaoDTO();
									vinculoVisaoDTO.setSeq(x);
									vinculoVisaoDTO.setTipoVinculo(tipoVinculo);
									vinculoVisaoDTO.setIdCamposObjetoNegocioFilho(new Integer(strAux[0]));
									vinculoVisaoDTO.setIdCamposObjetoNegocioFilhoNN(new Integer(strAux[1]));
									vinculoVisaoDTO.setControle("F");

									colVinculosVisao.add(vinculoVisaoDTO);
								}
							}

							visaoRelacionadaDTO.setColVinculosVisao(colVinculosVisao);
							colVisaoRelacionada.add(visaoRelacionadaDTO);

							ordem++;
						}
					}
				}
			}
		}

		Collection colBotoes = (Collection) request.getSession().getAttribute("botoes");
		if (colBotoes == null) {
			colBotoes = new ArrayList();
		}

		visaoDto.setColBotoes(colBotoes);
		visaoDto.setColVisoesRelacionadas(colVisaoRelacionada);
		visaoDto.setColScripts(colScripts);
		visaoDto.setColHtmlCode(colHtmlCode);

		if (visaoDto.getIdVisao() != null && visaoDto.getIdVisao().intValue() != 0) {
			visaoService.update(visaoDto);
		} else {
			visaoDto = (VisaoDTO) visaoService.create(visaoDto);
			document.getElementById("idVisao").setValue("" + visaoDto.getIdVisao());
		}
		document.alert(UtilI18N.internacionaliza(request, "visaoAdm.visaoGravadaSucesso"));
	}

	public void listaVisoesTb(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HTMLElement divPrincipal = document.getElementById("listaVisoesTb");
		StringBuffer subDiv = new StringBuffer();
		subDiv.append("" + "<div class='formBody'> " + "	<table id='tbVisoes' class='tableLess'> 	" + "		<thead>" + "			<tr>" + "				<th>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.MarcarTodos")
				+ "<input type='checkbox' id='marcarTodos' name='marcarTodos' onclick='marcarTodosCheckbox()'/></th>	" + "				<th>"+ UtilI18N.internacionaliza(request, "citcorpore.comum.descricao")+"</th>	" + "				<th>"+ UtilI18N.internacionaliza(request, "citSmart.comum.identificador")+"</th>	" + "				<th>"+ UtilI18N.internacionaliza(request, "visaoAdm.situacao")+"</th>	" + "			</tr>"
				+ "		</thead><tbody>");
		List<VisaoDTO> list = (List<VisaoDTO>) getVisaoService().listAtivos();
		if (list != null) {
			int count = 0;
			boolean flag = false;
			document.executeScript("countVisao = 0");
			for (VisaoDTO visaoDTO : list) {
				subDiv.append("	<tr onclick='marcarCheck(" + count + ")'>" + "		<td width='5%' style='text-align:center;'>" + "<input type='checkbox' id='idVisaoCheck" + count + "'"
						+ " 		name='idVisaoCheck' onclick='marcarCheck(" + count + ")' />" + "<input type='hidden' name='idVisao" + count + "' id='idVisao" + count + "' value='"
						+ (visaoDTO.getIdVisao() == null ? "" : visaoDTO.getIdVisao()) + "'/></td>" + "       <td width='45%'>" + (visaoDTO.getDescricao() == null ? "" : visaoDTO.getDescricao()) + "</td>"
						+ "       <td width='45%'>" + (visaoDTO.getIdentificador() == null ? "" : visaoDTO.getIdentificador()) + "</td>" + "       <td width='5%'>"
						+ (visaoDTO.getSituacao() == null ? "" : visaoDTO.getSituacao()) + "</td>" + "	</tr>");
				count++;
			}
			subDiv.append("</tbody>	</table>" + "</div>");
			divPrincipal.setInnerHTML(subDiv.toString());

		}

	}

	public void exportarVisoesXML(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		int count = 0;

		String diretorioExport = request.getSession().getServletContext().getRealPath("/");
		String diretorioRelativoExport = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/";
		diretorioExport = diretorioExport.replaceAll("\\\\", "/");
		diretorioExport = diretorioExport + "exportXML";
		diretorioRelativoExport = diretorioRelativoExport + "exportXML";

		XStream x = new XStream(new DomDriver("ISO-8859-1"));

		byte[] dados = new byte[32768];
		BufferedInputStream origem = null;
		FileInputStream streamDeEntrada = null;
		FileOutputStream destino = null;
		ZipOutputStream saida = null;
		ZipEntry entry = null;

		// Recebe os ID's das visões para serem exportadas
		List<VisaoDTO> listVisao = (List) WebUtil.deserializeCollectionFromRequest(VisaoDTO.class, "visoesSerializadas", request);
		if (listVisao != null) {
			if (listVisao.size() > 1) {
				File f1 = new File(diretorioExport + "/" + "visoesExportadas");
				if (!f1.exists()) {
					f1.mkdirs();
				} else {
					for (File arquivos : f1.listFiles()) {
						arquivos.delete();
					}
				}

				for (VisaoDTO visaoDTO : listVisao) {
					if (visaoDTO != null && visaoDTO.getIdVisao() != null) {
						try {
							visaoDTO.setIdentificador(visaoDTO.getIdentificador().trim());
							visaoDTO = montaVisaoParaExportar(visaoDTO);
							String name = UtilStrings.generateNomeBusca(visaoDTO.getDescricao());
							name = name + ".citVision";
							FileOutputStream fOut = new FileOutputStream(f1.getAbsolutePath() + "/" + name);
							x.toXML(visaoDTO, fOut);
							fOut.close();
							count++;
						} catch (Exception e) {
							document.alert(UtilI18N.internacionaliza(request, "visaoAdm.erroExportarVisao") + visaoDTO.getIdentificador());
							e.printStackTrace();
						}
					}
				}
				// Compacta os arquivos exportados
				UtilZip.zipFileOrDirectory(f1.getAbsolutePath() + ".zip", f1.getAbsolutePath());
				document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/getFile.getFile?file=" + diretorioExport + "/visoesExportadas.zip"
						+ "&fileName=visoesExportadas.zip')");
			} else {
				for (VisaoDTO visaoDTO : listVisao) {
					if (visaoDTO != null && visaoDTO.getIdVisao() != null) {
						try {
							visaoDTO.setIdentificador(visaoDTO.getIdentificador().trim());
							visaoDTO = montaVisaoParaExportar(visaoDTO);
							String name = UtilStrings.generateNomeBusca(visaoDTO.getDescricao());
							name = name + ".citVision";
							FileOutputStream fOut = new FileOutputStream(diretorioExport + "/" + name);
							x.toXML(visaoDTO, fOut);
							fOut.close();
							document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/getFile.getFile?file=" + diretorioExport + "/" + name
									+ "&fileName=" + name + "')");
							count++;
						} catch (Exception e) {
							document.alert(UtilI18N.internacionaliza(request, "visaoAdm.erroExportarVisao") + visaoDTO.getIdentificador());
							e.printStackTrace();
						}
					}
				}
			}

		}
		document.alert(UtilI18N.internacionaliza(request, "visaoAdm.visoesExportadasSucesso") + count);
		document.executeScript("('#POPUP_EXPORTARVISOES').dialog('close');");

	}

	private VisaoDTO montaVisaoParaExportar(VisaoDTO visaoDTO) throws LogicException, ServiceException, Exception {

		GrupoVisaoDTO grupoVisaoDto = new GrupoVisaoDTO();

		// Restaura visão
		if (visaoDTO != null) {
			visaoDTO = (VisaoDTO) getVisaoService().restore(visaoDTO);
		}

		// Restaura GrupoVisão
		List<GrupoVisaoDTO> listGrupoVisao = new ArrayList<GrupoVisaoDTO>();
		if(visaoDTO != null){
			listGrupoVisao = (List<GrupoVisaoDTO>) getGrupoVisaoService().findByIdVisao(visaoDTO.getIdVisao());
			grupoVisaoDto.setDescricaoGrupoVisao(visaoDTO.getDescricao());
			grupoVisaoDto.setSituacao(visaoDTO.getSituacao());
		}
		
		grupoVisaoDto.setOrdem(new Integer(0));
		grupoVisaoDto.setForma("1");

		if(listGrupoVisao != null){
			listGrupoVisao.add(grupoVisaoDto);
		}
		if(visaoDTO != null){
			visaoDTO.setColGrupos(listGrupoVisao);
		}
		
		if(listGrupoVisao != null){
			for (GrupoVisaoDTO grupoVisao : listGrupoVisao) {
	
				Collection colGrpVisCpsNeg = getGrupoVisaoCamposNegocioService().findByIdGrupoVisao(grupoVisao.getIdGrupoVisao());
				if (colGrpVisCpsNeg != null) {
					for (Iterator it2 = colGrpVisCpsNeg.iterator(); it2.hasNext();) {
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it2.next();
						// Restaura Campos do Objeto de Negócio
						CamposObjetoNegocioDTO camposObjetoNegocioValid = new CamposObjetoNegocioDTO();
						camposObjetoNegocioValid.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
						camposObjetoNegocioValid = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioValid);
						if (camposObjetoNegocioValid != null) {
							grupoVisaoCamposNegocioAux.setNomeDB(camposObjetoNegocioValid.getNomeDB());
	
							ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
							objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioValid.getIdObjetoNegocio());
							objetoNegocioDTO = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objetoNegocioDTO);
							if (objetoNegocioDTO != null) {
								grupoVisaoCamposNegocioAux.setNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
							}
						}
						// Restaura Valores
						Collection colValores = getValorVisaoCamposNegocioService().findByIdGrupoVisaoAndIdCampoObjetoNegocio(grupoVisao.getIdGrupoVisao(), grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
						String[] valoresOpcoes = null;
						if (colValores != null && colValores.size() > 0) {
							valoresOpcoes = new String[colValores.size()];
							int x = 0;
							for (Iterator it3 = colValores.iterator(); it3.hasNext();) {
								ValorVisaoCamposNegocioDTO valorVisaoCamposNegocioDTO = (ValorVisaoCamposNegocioDTO) it3.next();
								valorVisaoCamposNegocioDTO.setValor(valorVisaoCamposNegocioDTO.getValorDescricao());
								valoresOpcoes[x] = valorVisaoCamposNegocioDTO.getValor();
								x++;
							}
						}
						grupoVisaoCamposNegocioAux.setValoresOpcoes(valoresOpcoes);
						grupoVisaoCamposNegocioAux.setColValores(colValores);
	
						// Restaura ligação dos objeto de negócio
						Collection colLigacao = getGrupoVisaoCamposNegocioLigacaoService().findByIdGrupoVisaoAndIdCamposObjetoNegocio(grupoVisao.getIdGrupoVisao(), grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
						if (colLigacao != null) {
							for (Iterator itLigacao = colLigacao.iterator(); itLigacao.hasNext();) {
								GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTO = (GrupoVisaoCamposNegocioLigacaoDTO) itLigacao.next();
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.VALUE)) {
									grupoVisaoCamposNegocioAux.setIdCamposObjetoNegocioLigacaoVinc(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									CamposObjetoNegocioDTO camposObjetoNegocioAux2 = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux2.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocioLigacaoVinc());
									camposObjetoNegocioAux2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux2);
									if (camposObjetoNegocioAux2 != null) {
										grupoVisaoCamposNegocioAux.setNomeDBLigacaoVinc(camposObjetoNegocioAux2.getNomeDB());
									}
								}
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.ORDER)) {
									grupoVisaoCamposNegocioAux.setIdCamposObjetoNegocioLigacaoOrder(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									CamposObjetoNegocioDTO camposObjetoNegocioAux2 = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux2.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocioLigacaoOrder());
									camposObjetoNegocioAux2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux2);
									if (camposObjetoNegocioAux2 != null) {
										grupoVisaoCamposNegocioAux.setNomeDBLigacaoOrder(camposObjetoNegocioAux2.getNomeDB());
									}
								}
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.PRESENTATION)) {
									grupoVisaoCamposNegocioAux.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									CamposObjetoNegocioDTO camposObjetoNegocioAux2 = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux2.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocioLigacao());
									camposObjetoNegocioAux2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux2);
									if (camposObjetoNegocioAux2 != null) {
										grupoVisaoCamposNegocioAux.setNomeDBLigacao(camposObjetoNegocioAux2.getNomeDB());
									}
	
									grupoVisaoCamposNegocioAux.setDescricaoRelacionamento(grupoVisaoCamposNegocioLigacaoDTO.getDescricao());
	
									CamposObjetoNegocioDTO camposObjetoNegocioAux = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									camposObjetoNegocioAux = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux);
									if (camposObjetoNegocioAux != null) {
										grupoVisaoCamposNegocioAux.setIdObjetoNegocioLigacao(camposObjetoNegocioAux.getIdObjetoNegocio());
										ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
										objetoNegocioDTO.setIdObjetoNegocio(grupoVisaoCamposNegocioAux.getIdObjetoNegocioLigacao());
										objetoNegocioDTO = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objetoNegocioDTO);
										if (objetoNegocioDTO != null) {
											grupoVisaoCamposNegocioAux.setNomeTabelaDBLigacao(objetoNegocioDTO.getNomeTabelaDB());
										}
									}
								}
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.FILTER)) {
									grupoVisaoCamposNegocioAux.setFiltro(grupoVisaoCamposNegocioLigacaoDTO.getFiltro());
								}
							}
						}
					}
				}
				if (colGrpVisCpsNeg != null) {
					grupoVisao.setColCamposVisao(colGrpVisCpsNeg);
				}
	
				//listGrupoVisao.clear();
				//listGrupoVisao.add(grupoVisaoDto);
	
			}
		}
		
		List lstFinal = new ArrayList();
		if(listGrupoVisao != null){
			for (GrupoVisaoDTO grupoVisao : listGrupoVisao) {
				if (grupoVisao.getColCamposVisao() != null){
					lstFinal.add(grupoVisao);
				}
			}
		}
		if(visaoDTO != null){
			visaoDTO.setColGrupos(lstFinal);
		}
		// Restaura MatrizVisao
		List<MatrizVisaoDTO> listMatrizVisao = new ArrayList<MatrizVisaoDTO>();
		if(visaoDTO != null){
			listMatrizVisao = (List<MatrizVisaoDTO>) getMatrizVisaoService().findByIdVisao(visaoDTO.getIdVisao());
		}
		if (listMatrizVisao != null) {
			for (MatrizVisaoDTO matrizVisaoDTO : listMatrizVisao) {
				// Restaurando Objeto Negócio
				ObjetoNegocioDTO obNeg = new ObjetoNegocioDTO();
				obNeg.setIdObjetoNegocio(matrizVisaoDTO.getIdObjetoNegocio());
				obNeg = (ObjetoNegocioDTO) getObjetoNegocioService().restore(obNeg);
				matrizVisaoDTO.setNomeTabelaDB(obNeg.getNomeTabelaDB());

				// Restaurando NomeDB1
				CamposObjetoNegocioDTO campNeg1 = new CamposObjetoNegocioDTO();
				if (matrizVisaoDTO.getIdCamposObjetoNegocio1() != null) {
					campNeg1.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio1());
					campNeg1 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campNeg1);
					matrizVisaoDTO.setNomeDB1(campNeg1.getNomeDB());
				}

				// Restaurando NomeDB2
				CamposObjetoNegocioDTO campNeg2 = new CamposObjetoNegocioDTO();
				if (matrizVisaoDTO.getIdCamposObjetoNegocio2() != null) {
					campNeg2.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio2());
					campNeg2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campNeg2);
					matrizVisaoDTO.setNomeDB2(campNeg2.getNomeDB());
				}

				// Restaurando NomeDB3
				CamposObjetoNegocioDTO campNeg3 = new CamposObjetoNegocioDTO();
				if (matrizVisaoDTO.getIdCamposObjetoNegocio3() != null) {
					campNeg3.setIdCamposObjetoNegocio(matrizVisaoDTO.getIdCamposObjetoNegocio3());
					campNeg3 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campNeg3);
					matrizVisaoDTO.setNomeDB3(campNeg3.getNomeDB());
				}

				// Restaurando Matriz
				visaoDTO.setMatrizVisaoDTO(matrizVisaoDTO);

			}
		}

		// Restaura Scripts
		List<ScriptsVisaoDTO> listScriptVisao = new ArrayList<ScriptsVisaoDTO>();
		if(visaoDTO != null){
			listScriptVisao = (List<ScriptsVisaoDTO>) getScriptsVisaoService().findByIdVisao(visaoDTO.getIdVisao());
			visaoDTO.setColScripts(listScriptVisao);
		}

		// Restaura HTMLCode
		List<HtmlCodeVisaoDTO> listHtmlCodeVisao = new ArrayList<HtmlCodeVisaoDTO>();
		if(visaoDTO != null){
			listHtmlCodeVisao = (List<HtmlCodeVisaoDTO>) getHtmlCodeVisaoService().findByIdVisao(visaoDTO.getIdVisao());
			visaoDTO.setColHtmlCode(listHtmlCodeVisao);
		}

		// Restaura Botões
		List<BotaoAcaoVisaoDTO> listBotoesVisao = new ArrayList<BotaoAcaoVisaoDTO>();
		if(visaoDTO != null){
			listBotoesVisao = (List<BotaoAcaoVisaoDTO>) getBotaoAcaoVisaoService().findByIdVisao(visaoDTO.getIdVisao());
			visaoDTO.setColBotoes(listBotoesVisao);
		}
		// Restaura Vínculo Visão
		Collection colVisRel = null;
		if(visaoDTO != null){
			colVisRel = getVisaoRelacionadaService().findByIdVisaoPaiAtivos(visaoDTO.getIdVisao());
		}
		if (colVisRel != null) {
			int i = 1;
			for (Iterator it = colVisRel.iterator(); it.hasNext();) {
				if (i > 30) {
					break;
				}
				VisaoRelacionadaDTO visaoRelacionadaDTO = (VisaoRelacionadaDTO) it.next();
				VisaoDTO visaoAux = new VisaoDTO();
				visaoAux.setIdVisao(visaoRelacionadaDTO.getIdVisaoFilha());
				visaoAux = (VisaoDTO) getVisaoService().restore(visaoAux);
				if (visaoAux != null) {
					visaoRelacionadaDTO.setIdentificacaoVisaoFilha(visaoAux.getIdentificador());
				}

				Collection colVincs = getVinculoVisaoService().findByIdVisaoRelacionada(visaoRelacionadaDTO.getIdVisaoRelacionada());
				if (colVincs != null) {
					int z = 0;
					for (Iterator itVinc = colVincs.iterator(); itVinc.hasNext();) {
						if (z > 20) {
							break;
						}
						VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO) itVinc.next();

						if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
							CamposObjetoNegocioDTO campoObj1 = new CamposObjetoNegocioDTO();
							campoObj1.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPai());
							campoObj1 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj1);

							vinculoVisaoDTO.setNomeDBPai(campoObj1.getNomeDB());

							ObjetoNegocioDTO objNegTemp = new ObjetoNegocioDTO();
							objNegTemp.setIdObjetoNegocio(campoObj1.getIdObjetoNegocio());
							if (objNegTemp != null) {
								objNegTemp = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objNegTemp);
								if (objNegTemp != null) {
									vinculoVisaoDTO.setNomeTabelaPai(objNegTemp.getNomeTabelaDB());
								}
							}

							CamposObjetoNegocioDTO campoObj2 = new CamposObjetoNegocioDTO();
							campoObj2.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN());
							campoObj2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj2);

							if(campoObj2 != null)
								vinculoVisaoDTO.setNomeDBPaiNN(campoObj2.getNomeDB());

							if(campoObj2 != null){
								objNegTemp.setIdObjetoNegocio(campoObj2.getIdObjetoNegocio());
							}
							if (objNegTemp != null) {
								objNegTemp = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objNegTemp);
								if (objNegTemp != null) {
									vinculoVisaoDTO.setNomeTabelaPaiNN(objNegTemp.getNomeTabelaDB());
								}
							}

							vinculoVisaoDTO.setControle("P");
						}
						if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
							CamposObjetoNegocioDTO campoObj1 = new CamposObjetoNegocioDTO();
							campoObj1.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilho());
							campoObj1 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj1);

							vinculoVisaoDTO.setNomeDBPaiFilho(campoObj1.getNomeDB());

							ObjetoNegocioDTO objNegTemp = new ObjetoNegocioDTO();
							objNegTemp.setIdObjetoNegocio(campoObj1.getIdObjetoNegocio());
							if (objNegTemp != null) {
								objNegTemp = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objNegTemp);
								if (objNegTemp != null) {
									vinculoVisaoDTO.setNomeTabelaFilho(objNegTemp.getNomeTabelaDB());
								}
							}

							CamposObjetoNegocioDTO campoObj2 = new CamposObjetoNegocioDTO();
							campoObj2.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN());
							campoObj2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj2);

							vinculoVisaoDTO.setNomeDBPaiFilhoNN(campoObj2.getNomeDB());

							objNegTemp = new ObjetoNegocioDTO();
							objNegTemp.setIdObjetoNegocio(campoObj1.getIdObjetoNegocio());
							if (objNegTemp != null) {
								objNegTemp = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objNegTemp);
								if (objNegTemp != null) {
									vinculoVisaoDTO.setNomeTabelaFilhoNN(objNegTemp.getNomeTabelaDB());
								}
							}

							vinculoVisaoDTO.setControle("F");
						}
						z++;
					}
					visaoRelacionadaDTO.setColVinculosVisao(colVincs);
				}
				i++;
			}
		}

		if(visaoDTO != null){
			visaoDTO.setColVisoesRelacionadas(colVisRel);
		}
		
		return visaoDTO;

	}

	public void exportXML(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (!grupoVisaoCamposNegocioDTO.getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
			if (col == null) {
				document.alert(UtilI18N.internacionaliza(request, "visaoAdm.naoHaCamposRelacionados"));
				return;
			}
			col = ordenaCamposVisao(col, grupoVisaoCamposNegocioDTO);
		}
		VisaoDTO visaoDto = new VisaoDTO();
		visaoDto.setIdVisao(grupoVisaoCamposNegocioDTO.getIdVisao());
		visaoDto.setDescricao(grupoVisaoCamposNegocioDTO.getDescricao());
		visaoDto.setSituacao(grupoVisaoCamposNegocioDTO.getSituacaoVisao());
		visaoDto.setTipoVisao(grupoVisaoCamposNegocioDTO.getTipoVisao());
		visaoDto.setIdentificador(grupoVisaoCamposNegocioDTO.getIdentificador());
		visaoDto.setClasseName(grupoVisaoCamposNegocioDTO.getClasseName());

		if (visaoDto.getIdVisao() == null) {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.necessarioSalvarAntesDeExportar"));
			return;
		}

		Collection colGrupos = new ArrayList();
		GrupoVisaoDTO grupoVisaoDTO = new GrupoVisaoDTO();
		grupoVisaoDTO.setDescricaoGrupoVisao(grupoVisaoCamposNegocioDTO.getDescricao());
		grupoVisaoDTO.setOrdem(new Integer(0));
		grupoVisaoDTO.setSituacao(visaoDto.getSituacao());
		grupoVisaoDTO.setForma("1");
		grupoVisaoDTO.setColCamposVisao(col);

		colGrupos.add(grupoVisaoDTO);
		visaoDto.setColGrupos(colGrupos);

		Collection colScripts = new ArrayList();
		HashMap mapScripts = (HashMap) request.getSession().getAttribute("scripts");
		if (mapScripts == null) {
			mapScripts = new HashMap();
		}
		Set set = mapScripts.entrySet();
		Iterator itMap = set.iterator();
		while (itMap.hasNext()) {
			Map.Entry me = (Map.Entry) itMap.next();
			ScriptsVisaoDTO scriptsVisaoDTO = new ScriptsVisaoDTO();
			scriptsVisaoDTO.setScript((String) me.getValue());
			scriptsVisaoDTO.setScriptLanguage(ScriptsVisaoDTO.JAVASCRIPT);
			if (me.getKey() != null) {
				String typeExecute = ((String) me.getKey()).substring(0, 1);
				String scriptType = ((String) me.getKey()).substring(2);
				scriptsVisaoDTO.setScryptType(scriptType);
				scriptsVisaoDTO.setTypeExecute(typeExecute);

				colScripts.add(scriptsVisaoDTO);
			}
		}

		Collection colHtmlCode = new ArrayList();
		HashMap mapHtmlCode = (HashMap) request.getSession().getAttribute("htmlCodes");
		if (mapHtmlCode == null) {
			mapHtmlCode = new HashMap();
		}
		set = mapHtmlCode.entrySet();
		itMap = set.iterator();
		while (itMap.hasNext()) {
			Map.Entry me = (Map.Entry) itMap.next();
			HtmlCodeVisaoDTO htmlCodeVisaoDTO = new HtmlCodeVisaoDTO();
			htmlCodeVisaoDTO.setHtmlCode((String) me.getValue());
			if (me.getKey() != null) {
				String htmlCodeType = (String) me.getKey();
				htmlCodeVisaoDTO.setHtmlCodeType(htmlCodeType);

				colHtmlCode.add(htmlCodeVisaoDTO);
			}
		}

		Collection colVisaoRelacionada = new ArrayList();
		if (grupoVisaoCamposNegocioDTO.getNumTabs() != null) {
			String[] str = grupoVisaoCamposNegocioDTO.getNumTabs().split(",");
			if (str != null) {
				int ordem = 0;
				for (int i = 0; i < str.length; i++) {
					if (str[i] != null && !str[i].trim().equalsIgnoreCase("")) {
						VisaoRelacionadaDTO visaoRelacionadaDTO = new VisaoRelacionadaDTO();
						int numTab = Integer.parseInt(str[i]);
						String idVisaoRel = request.getParameter("divVisaoRelacionada_" + numTab);
						int idVisaoRelInt = 0;
						try {
							idVisaoRelInt = Integer.parseInt(idVisaoRel);
						} catch (Exception e) {
						}
						if (idVisaoRelInt > 0) {
							String titulo = UtilStrings.decodeCaracteresEspeciais(request.getParameter("titulo_" + numTab));
							String tipoVinculacao = request.getParameter("tipoVinculacao_" + numTab);
							String acaoEmSelecaoPesquisa = request.getParameter("acaoEmSelecaoPesquisa_" + numTab);
							String situacaoVinc = request.getParameter("situacaoVisaoVinculada_" + numTab);
							String idObjetoNegocioNNStr = request.getParameter("idObjetoNegocioNN_" + numTab);
							if (situacaoVinc == null || situacaoVinc.trim().equalsIgnoreCase("")) {
								situacaoVinc = "A";
							}
							visaoRelacionadaDTO.setIdVisaoFilha(idVisaoRelInt);
							VisaoDTO visaoAux = new VisaoDTO();
							visaoAux.setIdVisao(visaoRelacionadaDTO.getIdVisaoFilha());
							visaoAux = (VisaoDTO) getVisaoService().restore(visaoAux);
							if (visaoAux != null) {
								visaoRelacionadaDTO.setIdentificacaoVisaoFilha(visaoAux.getIdentificador());
							}
							visaoRelacionadaDTO.setTitulo(titulo);
							visaoRelacionadaDTO.setTipoVinculacao(tipoVinculacao);
							visaoRelacionadaDTO.setAcaoEmSelecaoPesquisa(acaoEmSelecaoPesquisa);
							visaoRelacionadaDTO.setSituacao(situacaoVinc);
							visaoRelacionadaDTO.setOrdem(ordem);
							try {
								visaoRelacionadaDTO.setIdObjetoNegocioNN(new Integer(idObjetoNegocioNNStr));
							} catch (Exception e) {
							}
							if (visaoRelacionadaDTO.getIdObjetoNegocioNN() != null) {
								ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
								objetoNegocioDTO.setIdObjetoNegocio(visaoRelacionadaDTO.getIdObjetoNegocioNN());
								objetoNegocioDTO = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objetoNegocioDTO);
								if (objetoNegocioDTO != null) {
									visaoRelacionadaDTO.setNomeDBNegocioNN(objetoNegocioDTO.getNomeTabelaDB());
								}
							}

							String tipoVinculo = request.getParameter("tipoVinculo_" + numTab);

							Collection colVinculosVisao = new ArrayList();
							String nomeCampo = "vinculosVisaoPaiNN_" + numTab;
							String[] strValores = geraArrayRequest(request, nomeCampo);
							if (strValores != null && strValores.length > 0) {
								for (int x = 0; x < strValores.length; x++) {
									String[] strAux = strValores[x].split("#");
									VinculoVisaoDTO vinculoVisaoDTO = new VinculoVisaoDTO();
									vinculoVisaoDTO.setSeq(x);
									vinculoVisaoDTO.setTipoVinculo(tipoVinculo);
									vinculoVisaoDTO.setIdCamposObjetoNegocioPai(new Integer(strAux[0]));
									vinculoVisaoDTO.setIdCamposObjetoNegocioPaiNN(new Integer(strAux[1]));

									if (vinculoVisaoDTO.getIdCamposObjetoNegocioPai() != null) {
										CamposObjetoNegocioDTO campoObj1 = new CamposObjetoNegocioDTO();
										campoObj1.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPai());
										campoObj1 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj1);
										vinculoVisaoDTO.setNomeDBPai(campoObj1.getNomeDB());
									}
									if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
										CamposObjetoNegocioDTO campoObj2 = new CamposObjetoNegocioDTO();
										campoObj2.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN());
										campoObj2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj2);
										vinculoVisaoDTO.setNomeDBPaiNN(campoObj2.getNomeDB());
									}
									vinculoVisaoDTO.setControle("P");

									colVinculosVisao.add(vinculoVisaoDTO);
								}
							}
							nomeCampo = "vinculosVisaoFilhoNN_" + numTab;
							strValores = geraArrayRequest(request, nomeCampo);
							if (strValores != null && strValores.length > 0) {
								for (int x = 0; x < strValores.length; x++) {
									String[] strAux = strValores[x].split("#");
									VinculoVisaoDTO vinculoVisaoDTO = new VinculoVisaoDTO();
									vinculoVisaoDTO.setSeq(x);
									vinculoVisaoDTO.setTipoVinculo(tipoVinculo);
									vinculoVisaoDTO.setIdCamposObjetoNegocioFilho(new Integer(strAux[0]));
									if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilho() != null) {
										CamposObjetoNegocioDTO campoObj1 = new CamposObjetoNegocioDTO();
										campoObj1.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilho());
										campoObj1 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj1);
										vinculoVisaoDTO.setNomeDBPaiFilho(campoObj1.getNomeDB());
									}
									vinculoVisaoDTO.setIdCamposObjetoNegocioFilhoNN(new Integer(strAux[1]));
									if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
										CamposObjetoNegocioDTO campoObj2 = new CamposObjetoNegocioDTO();
										campoObj2.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN());
										campoObj2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj2);
										vinculoVisaoDTO.setNomeDBPaiFilhoNN(campoObj2.getNomeDB());
									}
									vinculoVisaoDTO.setControle("F");

									colVinculosVisao.add(vinculoVisaoDTO);
								}
							}

							visaoRelacionadaDTO.setColVinculosVisao(colVinculosVisao);
							colVisaoRelacionada.add(visaoRelacionadaDTO);

							ordem++;
						}
					}
				}
			}
		}

		Collection colBotoes = (Collection) request.getSession().getAttribute("botoes");
		if (colBotoes == null) {
			colBotoes = new ArrayList();
		}

		visaoDto.setColBotoes(colBotoes);
		visaoDto.setColVisoesRelacionadas(colVisaoRelacionada);
		visaoDto.setColScripts(colScripts);
		visaoDto.setColHtmlCode(colHtmlCode);

		String diretorioExport = request.getSession().getServletContext().getRealPath("/");
		String diretorioRelativoExport = Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/";

		diretorioExport = diretorioExport.replaceAll("\\\\", "/");

		diretorioExport = diretorioExport + "exportXML";
		diretorioRelativoExport = diretorioRelativoExport + "exportXML";
		File f = new File(diretorioExport);
		if (!f.exists()) {
			f.mkdirs();
		}

		visaoDto.setIdVisao(new Integer(0));
		visaoDto.setMapScripts(mapScripts);
		visaoDto.setIdentificador(visaoDto.getIdentificador().trim());
		String name = UtilStrings.generateNomeBusca(visaoDto.getDescricao());
		name = name + ".citVision";
		FileOutputStream fOut = new FileOutputStream(diretorioExport + "/VisoesExportadas/" + name);

		XStream x = new XStream(new DomDriver("ISO-8859-1"));
		x.toXML(visaoDto, fOut);

		fOut.close();

		document.executeScript("window.open('" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/getFile.getFile?file=" + diretorioExport + "/" + name + "&fileName=" + name + "')");
	}
	
	/**
	 * 
	 * @param arquivosUpados
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	private String importaVisoes(List<UploadDTO> arquivosUpados,  HttpServletRequest request, HttpServletResponse response) throws ServiceException, Exception {
		VisaoService visaoService = (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class,null);
		FileReader reader = null;
		VisaoDTO visaoAtual = null;
		VisaoDTO visaoDtoXML = new VisaoDTO();

		int countImport = 0;
		int countAtualiza = 0;
		StringBuffer str = new StringBuffer();

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
							System.out.println("<i18n:message key='visaoAdm.erroImportarVisao'/>" + visaoDtoXML.getIdentificador());
							e.printStackTrace();
						}
					} else {
						try {
							visaoService.atualizarVisao(visaoAtual, visaoDtoXML);
							countAtualiza++;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
			str.append(UtilI18N.internacionaliza(request, "visaoAdm.visoesImportadasSucesso") + countImport + "<br/>" + UtilI18N.internacionaliza(request, "visaoAdm.visoesAtualizadasSucesso") + countAtualiza);

		} else {
			str.append(UtilI18N.internacionaliza(request, "visaoAdm.nenhumArquivoSelecionado"));
		}
		return str.toString();

	}

	public void importarVisoesXML(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Collection<UploadDTO> arquivosUpados = (Collection<UploadDTO>) request.getSession(true).getAttribute("colUploadsGED");

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
					visaoAtual = visaoExistente(visaoDtoXML.getIdentificador());
					// Determina o tipo da importação
					if (visaoAtual == null) {
						try {
							importar(visaoDtoXML, document, request, response);
							countImport++;
							request.getSession(true).setAttribute("colUploadsGED", null);
							document.executeScript("uploadAnexos.refresh()");
						} catch (Exception e) {
							document.alert(UtilI18N.internacionaliza(request, "visaoAdm.erroImportarVisao ") + visaoDtoXML.getIdentificador());
							e.printStackTrace();
						}
					} else {
						try {
							atualizarVisao(visaoAtual, visaoDtoXML, document, request, response);
							countAtualiza++;
							request.getSession(true).setAttribute("colUploadsGED", null);
							document.executeScript("uploadAnexos.refresh()");
						} catch (Exception e) {
							document.alert(UtilI18N.internacionaliza(request, "visaoAdm.erroAtualizarVisao ") + visaoDtoXML.getIdentificador());
							e.printStackTrace();
						}
					}
				}
			}
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.visoesImportadasSucesso") + countImport + "\n" + UtilI18N.internacionaliza(request, "visaoAdm.visoesAtualizadasSucesso ") + countAtualiza);
			document.executeScript("('#POPUP_IMPORTARVISOES').dialog('close');");

			HTMLForm formUpload = document.getForm("formUpload");
			formUpload.clear();

		} else {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.nenhumArquivoSelecionado "));
		}

	}

	private VisaoDTO visaoExistente(String identificadorVisao) throws ServiceException, Exception {
		VisaoDTO visaoDto = null;
		visaoDto = getVisaoService().findByIdentificador(identificadorVisao);
		if (visaoDto != null) {
			return visaoDto;
		} else {
			return null;
		}
	}

	private void importar(VisaoDTO visaoXML, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		List<GrupoVisaoCamposNegocioDTO> colCampos = new ArrayList();

		if (visaoXML != null && visaoXML.getColGrupos() != null && visaoXML.getColGrupos().size() > 0) {
			GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
			colCampos = (List<GrupoVisaoCamposNegocioDTO>) grupoVisaoDTO.getColCamposVisao();
			if (colCampos != null) {
				for (GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO : colCampos) {
					if (grupoVisaoCamposNegocioDTO.getNomeTabelaDB() != null) {// Restaura id do Grupo Visão
						List<GrupoVisaoDTO> listGrupoVisaoTemp = (List<GrupoVisaoDTO>) getGrupoVisaoService().findByIdVisao(visaoXML.getIdVisao());
						if (listGrupoVisaoTemp != null) {
							for (GrupoVisaoDTO grupoVisaoDTO2 : listGrupoVisaoTemp) {
								grupoVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO2.getIdGrupoVisao());
							}
						}
						// Restaura id dos Campos Objeto Negócio
						Integer idObjetoNegocio = null;
						List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(grupoVisaoCamposNegocioDTO.getNomeTabelaDB());
						if (colObjNegocio != null) {
							for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
								idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
							}
							if (idObjetoNegocio != null) {
								grupoVisaoCamposNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
							}
						}
						if (idObjetoNegocio != null) {
							List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
									grupoVisaoCamposNegocioDTO.getNomeDB());
							if (colCamposObjNeg != null) {
								for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
									grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());

								}
							}
						}
						// Restaura visões de ligação caso exista
						if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
							// Restaura id do objeto negócio ligação
							List<ObjetoNegocioDTO> listObjNegTemp = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(grupoVisaoCamposNegocioDTO.getNomeTabelaDBLigacao());
							if (listObjNegTemp != null) {
								for (ObjetoNegocioDTO objetoNegocioDTO : listObjNegTemp) {
									grupoVisaoCamposNegocioDTO.setIdObjetoNegocioLigacao(objetoNegocioDTO.getIdObjetoNegocio());
								}
							}
							// Restaura id do campo objeto negócio ligação
							List<CamposObjetoNegocioDTO> listCamposObjNegTemp2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
									grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacao());
							if (listCamposObjNegTemp2 != null) {
								for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp2) {
									grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacao(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
								}
							}
							// Restaura id do campo objeto negócio ligação vinculado
							List<CamposObjetoNegocioDTO> listCamposObjNegTemp3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
									grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoVinc());
							if (listCamposObjNegTemp3 != null) {
								for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp3) {
									grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoVinc(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
								}
							}
							// Restaura id do campo objeto negócio ligação order
							List<CamposObjetoNegocioDTO> listCamposObjNegTemp4 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
									grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoOrder());
							if (listCamposObjNegTemp4 != null) {
								for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp4) {
									grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoOrder(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
								}
							}
						}
					}
				}
			}

			if (visaoXML.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
				// Restaura ObjetoNegocio
				ObjetoNegocioDTO obNeg = new ObjetoNegocioDTO();
				Integer idObjetoNegocio = null;
				List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(visaoXML.getMatrizVisaoDTO().getNomeTabelaDB());
				if (colObjNegocio != null) {
					for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
						idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdObjetoNegocio(idObjetoNegocio);
				}
				// Restaura CampoObjetoNegocio1
				Integer idCampNeg1 = null;
				List<CamposObjetoNegocioDTO> colCampNeg1 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(),
						visaoXML.getMatrizVisaoDTO().getNomeDB1());
				if (colCampNeg1 != null) {
					for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg1) {
						idCampNeg1 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio1(idCampNeg1);
				}
				// Restaura CampoObjetoNegocio2
				Integer idCampNeg2 = null;
				List<CamposObjetoNegocioDTO> colCampNeg2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(),
						visaoXML.getMatrizVisaoDTO().getNomeDB2());
				if (colCampNeg2 != null) {
					for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg2) {
						idCampNeg2 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio2(idCampNeg2);
				}
				// Restaura CampoObjetoNegocio3
				Integer idCampNeg3 = null;
				List<CamposObjetoNegocioDTO> colCampNeg3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(),
						visaoXML.getMatrizVisaoDTO().getNomeDB3());
				if (colCampNeg3 != null) {
					for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg3) {
						idCampNeg3 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio3(idCampNeg3);
				}
			}
		}

		if (visaoXML != null && visaoXML.getColVisoesRelacionadas() != null && visaoXML.getColVisoesRelacionadas().size() > 0) {
			List<VisaoRelacionadaDTO> listVisaoRelacionada = (List<VisaoRelacionadaDTO>) visaoXML.getColVisoesRelacionadas();
			for (VisaoRelacionadaDTO visaoRelacionadaDTO : listVisaoRelacionada) {
				// Restaura ID da Visão Relacionada Filha
				VisaoDTO visaoDTOFilha = getVisaoService().findByIdentificador(visaoRelacionadaDTO.getIdentificacaoVisaoFilha());
				if (visaoDTOFilha != null) {
					visaoRelacionadaDTO.setIdVisaoFilha(visaoDTOFilha.getIdVisao());
				}

				// Preenche campos do vínculo
				List<VinculoVisaoDTO> listVinculoVisao = (List<VinculoVisaoDTO>) visaoRelacionadaDTO.getColVinculosVisao();
				if (listVinculoVisao != null) {
					GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
					for (VinculoVisaoDTO vinculoVisaoDTO : listVinculoVisao) {
						if (vinculoVisaoDTO != null) {
							vinculoVisaoDTO.setIdGrupoVisaoPai(grupoVisaoDTO.getIdGrupoVisao());
							if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
								// Recuperando CampoObjetoNegocio do Pai
								Integer idObjetoNegocio = null;
								List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPai());
								if (colObjNegocio != null) {
									for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
										idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
									}
									List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
											vinculoVisaoDTO.getNomeDBPai());
									if (colCamposObjNeg != null) {
										for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
											vinculoVisaoDTO.setIdCamposObjetoNegocioPai(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
										}
									}
								}
								// Recuperando CampoObjetoNegocio do PaiNN
								colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPaiNN());
								if (colObjNegocio != null) {
									for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
										idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
									}
									List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
											vinculoVisaoDTO.getNomeDBPaiNN());
									if (colCamposObjNeg != null) {
										for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
											vinculoVisaoDTO.setIdCamposObjetoNegocioPaiNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
										}
									}
								}
							}

							if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
								// Recuperando CampoObjetoNegocio do Filho
								Integer idObjetoNegocio = null;
								List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilho());
								if (colObjNegocio != null) {
									for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
										idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
									}
									List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
											vinculoVisaoDTO.getNomeDBPaiFilho());
									if (colCamposObjNeg != null) {
										for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
											vinculoVisaoDTO.setIdCamposObjetoNegocioFilho(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
										}
									}
								}
								// Recuperando CampoObjetoNegocio do FilhoNN
								colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilhoNN());
								if (colObjNegocio != null) {
									for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
										idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
									}
									List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
											vinculoVisaoDTO.getNomeDBPaiFilhoNN());
									if (colCamposObjNeg != null) {
										for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
											vinculoVisaoDTO.setIdCamposObjetoNegocioFilhoNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
										}
									}
								}
							}
						}
					}
				}
			}
		}

		if (visaoXML != null) {
			getVisaoService().create(visaoXML);
		}

	}

	private void atualizarVisao(VisaoDTO visaoAtual, VisaoDTO visaoXML, DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		// Primeiro obtem visão atual a ser atualizada
		if (visaoAtual != null && visaoXML != null &&  visaoAtual.getIdVisao() != null) {
			visaoXML.setIdVisao(visaoAtual.getIdVisao());
			// Segundo obtem visão vinda do XML
			List<GrupoVisaoCamposNegocioDTO> colCamposVisaoXML = new ArrayList();

			if (visaoXML.getColGrupos() != null && visaoXML.getColGrupos().size() > 0) {
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
				colCamposVisaoXML = (List<GrupoVisaoCamposNegocioDTO>) grupoVisaoDTO.getColCamposVisao();
				if (colCamposVisaoXML != null) {
					for (GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO : colCamposVisaoXML) {
						if (grupoVisaoCamposNegocioDTO.getNomeTabelaDB() != null) {
							// Restaura id do Grupo Visão
							List<GrupoVisaoDTO> listGrupoVisaoTemp = (List<GrupoVisaoDTO>) getGrupoVisaoService().findByIdVisao(visaoXML.getIdVisao());
							if (listGrupoVisaoTemp != null) {
								for (GrupoVisaoDTO grupoVisaoDTO2 : listGrupoVisaoTemp) {
									grupoVisaoCamposNegocioDTO.setIdGrupoVisao(grupoVisaoDTO2.getIdGrupoVisao());
								}
							}
							// Restaura id dos Campos Objeto Negócio
							Integer idObjetoNegocio = null;
							List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(grupoVisaoCamposNegocioDTO.getNomeTabelaDB());
							if (colObjNegocio != null) {
								for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
									idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
								}
								if (idObjetoNegocio != null) {
									grupoVisaoCamposNegocioDTO.setIdObjetoNegocio(idObjetoNegocio);
								}
							}
							if (idObjetoNegocio != null) {
								List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
										grupoVisaoCamposNegocioDTO.getNomeDB());
								if (colCamposObjNeg != null) {
									for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
										grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocio(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
									}
								}
							}
							// Restaura visões de ligação caso exista
							if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
								// Restaura id do objeto negócio ligação
								List<ObjetoNegocioDTO> listObjNegTemp = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(grupoVisaoCamposNegocioDTO.getNomeTabelaDBLigacao());
								if (listObjNegTemp != null) {
									for (ObjetoNegocioDTO objetoNegocioDTO : listObjNegTemp) {
										grupoVisaoCamposNegocioDTO.setIdObjetoNegocioLigacao(objetoNegocioDTO.getIdObjetoNegocio());
									}
								}
								// Restaura id do campo objeto negócio ligação
								List<CamposObjetoNegocioDTO> listCamposObjNegTemp2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
										grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacao());
								if (listCamposObjNegTemp2 != null) {
									for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp2) {
										grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacao(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
									}
								}
								// Restaura id do campo objeto negócio ligação vinculado
								List<CamposObjetoNegocioDTO> listCamposObjNegTemp3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
										grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoVinc());
								if (listCamposObjNegTemp3 != null) {
									for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp3) {
										grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoVinc(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
									}
								}
								// Restaura id do campo objeto negócio ligação order
								List<CamposObjetoNegocioDTO> listCamposObjNegTemp4 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(
										grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao(), grupoVisaoCamposNegocioDTO.getNomeDBLigacaoOrder());
								if (listCamposObjNegTemp4 != null) {
									for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : listCamposObjNegTemp4) {
										grupoVisaoCamposNegocioDTO.setIdCamposObjetoNegocioLigacaoOrder(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
									}
								}
							}
						}
					}
				}
			}

			if (visaoXML.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
				// Restaura ObjetoNegocio
				ObjetoNegocioDTO obNeg = new ObjetoNegocioDTO();
				Integer idObjetoNegocio = null;
				List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(visaoXML.getMatrizVisaoDTO().getNomeTabelaDB());
				if (colObjNegocio != null) {
					for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
						idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdObjetoNegocio(idObjetoNegocio);
				}
				// Restaura CampoObjetoNegocio1
				Integer idCampNeg1 = null;
				List<CamposObjetoNegocioDTO> colCampNeg1 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(),
						visaoXML.getMatrizVisaoDTO().getNomeDB1());
				if (colCampNeg1 != null) {
					for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg1) {
						idCampNeg1 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio1(idCampNeg1);
				}
				// Restaura CampoObjetoNegocio2
				Integer idCampNeg2 = null;
				List<CamposObjetoNegocioDTO> colCampNeg2 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(),
						visaoXML.getMatrizVisaoDTO().getNomeDB2());
				if (colCampNeg2 != null) {
					for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg2) {
						idCampNeg2 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio2(idCampNeg2);
				}
				// Restaura CampoObjetoNegocio3
				Integer idCampNeg3 = null;
				List<CamposObjetoNegocioDTO> colCampNeg3 = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(visaoXML.getMatrizVisaoDTO().getIdObjetoNegocio(),
						visaoXML.getMatrizVisaoDTO().getNomeDB3());
				if (colCampNeg3 != null) {
					for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCampNeg3) {
						idCampNeg3 = camposObjetoNegocioDTO.getIdCamposObjetoNegocio();
					}
					visaoXML.getMatrizVisaoDTO().setIdCamposObjetoNegocio3(idCampNeg3);
				}
			}

			if (visaoXML.getColVisoesRelacionadas() != null && visaoXML.getColVisoesRelacionadas().size() > 0) {
				List<VisaoRelacionadaDTO> listVisaoRelacionada = (List<VisaoRelacionadaDTO>) visaoXML.getColVisoesRelacionadas();
				for (VisaoRelacionadaDTO visaoRelacionadaDTO : listVisaoRelacionada) {
					// Restaura ID da Visão Relacionada Filha
					VisaoDTO visaoDTOFilha = getVisaoService().findByIdentificador(visaoRelacionadaDTO.getIdentificacaoVisaoFilha());
					if (visaoDTOFilha != null) {
						visaoRelacionadaDTO.setIdVisaoFilha(visaoDTOFilha.getIdVisao());
					}

					// Preenche campos do vínculo
					List<VinculoVisaoDTO> listVinculoVisao = (List<VinculoVisaoDTO>) visaoRelacionadaDTO.getColVinculosVisao();
					if (listVinculoVisao != null) {
						GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) ((List) (visaoXML.getColGrupos())).get(0);
						for (VinculoVisaoDTO vinculoVisaoDTO : listVinculoVisao) {
							if (vinculoVisaoDTO != null) {
								vinculoVisaoDTO.setIdGrupoVisaoPai(grupoVisaoDTO.getIdGrupoVisao());
								if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
									// Recuperando CampoObjetoNegocio do Pai
									Integer idObjetoNegocio = null;
									List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPai());
									if (colObjNegocio != null) {
										for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
											idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
										}
										List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
												vinculoVisaoDTO.getNomeDBPai());
										if (colCamposObjNeg != null) {
											for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
												vinculoVisaoDTO.setIdCamposObjetoNegocioPai(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
											}
										}
									}
									// Recuperando CampoObjetoNegocio do PaiNN
									colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaPaiNN());
									if (colObjNegocio != null) {
										for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
											idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
										}
										List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
												vinculoVisaoDTO.getNomeDBPaiNN());
										if (colCamposObjNeg != null) {
											for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
												vinculoVisaoDTO.setIdCamposObjetoNegocioPaiNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
											}
										}
									}
								}

								if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
									// Recuperando CampoObjetoNegocio do Filho
									Integer idObjetoNegocio = null;
									List<ObjetoNegocioDTO> colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilho());
									if (colObjNegocio != null) {
										for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
											idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
										}
										List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
												vinculoVisaoDTO.getNomeDBPaiFilho());
										if (colCamposObjNeg != null) {
											for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
												vinculoVisaoDTO.setIdCamposObjetoNegocioFilho(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
											}
										}
									}
									// Recuperando CampoObjetoNegocio do FilhoNN
									colObjNegocio = (List<ObjetoNegocioDTO>) getObjetoNegocioService().findByNomeTabelaDB(vinculoVisaoDTO.getNomeTabelaFilhoNN());
									if (colObjNegocio != null) {
										for (ObjetoNegocioDTO objetoNegocioDTO : colObjNegocio) {
											idObjetoNegocio = objetoNegocioDTO.getIdObjetoNegocio();
										}
										List<CamposObjetoNegocioDTO> colCamposObjNeg = (List<CamposObjetoNegocioDTO>) getCamposObjetoNegocioService().findByIdObjetoNegocioAndNomeDB(idObjetoNegocio,
												vinculoVisaoDTO.getNomeDBPaiFilhoNN());
										if (colCamposObjNeg != null) {
											for (CamposObjetoNegocioDTO camposObjetoNegocioDTO : colCamposObjNeg) {
												vinculoVisaoDTO.setIdCamposObjetoNegocioFilhoNN(camposObjetoNegocioDTO.getIdCamposObjetoNegocio());
											}
										}
									}
								}
							}
						}
					}
				}

			}

			// Salva alterações
			if (visaoXML != null) {
				getVisaoService().update(visaoXML);
			}

		} else {
			throw new Exception();
		}

	}

	public String[] geraArrayRequest(HttpServletRequest request, String nomeCampo) {
		String[] strValores = request.getParameterValues(nomeCampo);
		if (strValores == null) {
			String str = request.getParameter(nomeCampo);
			if (str == null) {
				return null;
			}
			strValores = new String[1];
			strValores[0] = UtilStrings.decodeCaracteresEspeciais(str);
			return strValores;
		}
		if (strValores.length == 0) {
			strValores = new String[1];
			strValores[0] = UtilStrings.decodeCaracteresEspeciais(request.getParameter(nomeCampo));
		} else {
			if (strValores.length == 1) {
				strValores[0] = UtilStrings.decodeCaracteresEspeciais(strValores[0]);
			} else {
				for (int x = 0; x < strValores.length; x++) {
					strValores[x] = UtilStrings.decodeCaracteresEspeciais(strValores[x]);
				}
			}
		}
		return strValores;
	}

	public void listarCamposObjNegocio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		listarCamposObjNegocioParm(document, request, response, "idCamposObjetoNegocio");
	}

	public void listarCamposObjNegocioNN(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		listarCamposObjNegocioParm(document, request, response, grupoVisaoCamposNegocioDTO.getNomeCombo());
	}

	public void listarCamposObjNegocioParm(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, String nomeCombo) throws Exception {

		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection colCampos = null;
		if (grupoVisaoCamposNegocioDTO.getIdObjetoNegocio() != null) {
			colCampos = getCamposObjetoNegocioService().findByIdObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdObjetoNegocio());
		}
		if (grupoVisaoCamposNegocioDTO.getIdObjetoNegocio() != null) {
			document.getSelectById(nomeCombo).removeAllOptions();
		}
		if (colCampos != null) {
			document.getSelectById(nomeCombo).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById(nomeCombo).addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);
		}
	}

	public void listarCamposObjNegocioLigacao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection colCampos = getCamposObjetoNegocioService().findByIdObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao());

		document.getSelectById("idCamposObjetoNegocioLigacao").removeAllOptions();
		document.getSelectById("idCamposObjetoNegocioLigacaoVinc").removeAllOptions();
		document.getSelectById("idCamposObjetoNegocioLigacaoOrder").removeAllOptions();
		if (colCampos != null) {
			document.getSelectById("idCamposObjetoNegocioLigacao").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idCamposObjetoNegocioLigacao").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);

			document.getSelectById("idCamposObjetoNegocioLigacaoVinc").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idCamposObjetoNegocioLigacaoVinc").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);

			document.getSelectById("idCamposObjetoNegocioLigacaoOrder").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idCamposObjetoNegocioLigacaoOrder").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);

			Collection col = (Collection) request.getSession().getAttribute("visao");
			if (col == null) {
				col = new ArrayList();
			}
			int i = 0;
			for (Iterator it = col.iterator(); it.hasNext();) {
				GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it.next();
				i++;
				if (grupoVisaoCamposNegocioDTO.getNumeroEdicao() != null) {
					if (i == grupoVisaoCamposNegocioDTO.getNumeroEdicao().intValue()) {
						grupoVisaoCamposNegocioAux.setIdObjetoNegocioLigacao(grupoVisaoCamposNegocioDTO.getIdObjetoNegocioLigacao());
						document.getForm("formItem").setValues(grupoVisaoCamposNegocioAux);
					}
				}
			}
		}
	}

	public void listarCamposObjNegocioMatriz(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		Collection colCampos = getCamposObjetoNegocioService().findByIdObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdObjetoNegocioMatriz());
		document.getSelectById("idCamposObjetoNegocio1").removeAllOptions();
		document.getSelectById("idCamposObjetoNegocio2").removeAllOptions();
		document.getSelectById("idCamposObjetoNegocio3").removeAllOptions();
		if (colCampos != null) {
			document.getSelectById("idCamposObjetoNegocio1").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idCamposObjetoNegocio1").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);

			document.getSelectById("idCamposObjetoNegocio2").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idCamposObjetoNegocio2").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);

			document.getSelectById("idCamposObjetoNegocio3").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
			document.getSelectById("idCamposObjetoNegocio3").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);
		}
	}

	public void aplicaCfgCampoObjNegocioVisao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		aplicaCfgCampoObjNegocio(document, request, response, grupoVisaoCamposNegocioDTO);
	}

	public void aplicaCfgCampoObjNegocio(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO) throws Exception {
		escondeDivs(document);
		if (grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio() != null) {
			CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
			camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
			camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioDTO);
			if (camposObjetoNegocioDTO != null) {
				if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RADIO) || grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.SELECT)) {
					document.getElementById("divCampoRadioSelect").setVisible(true);
				}
				if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.DECIMAL)) {
					document.getElementById("divCampoNumeroDecimais").setVisible(true);
				}
				if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.RELATION)) {
					document.getElementById("divCampoRelacao").setVisible(true);
					document.executeScript("selecionaObjNegocioLigacao(document.getElementById('idObjetoNegocioLigacao'))");
				}
				if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.HTML)) {
					document.getElementById("divCampoHTMLCode").setVisible(true);
				}
				if (grupoVisaoCamposNegocioDTO.getTipoNegocio().equalsIgnoreCase(MetaUtil.CLASS_AND_METHOD)) {
					document.getElementById("divCampoClass").setVisible(true);
				}
			}
		}
	}

	public void escondeDivs(DocumentHTML document) throws Exception {
		document.getElementById("divCampoRadioSelect").setVisible(false);
		document.getElementById("divCampoNumeroDecimais").setVisible(false);
		document.getElementById("divCampoRelacao").setVisible(false);
		document.getElementById("divCampoHTMLCode").setVisible(false);
		document.getElementById("divCampoClass").setVisible(false);
	}

	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();

		VisaoDTO visaoDto = new VisaoDTO();
		visaoDto.setIdVisao(grupoVisaoCamposNegocioDTO.getIdVisao());
		if (visaoDto.getIdVisao() == null) {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.nenhumaVisaoSelecionada "));
			return;
		}

		visaoDto = (VisaoDTO) getVisaoService().restore(visaoDto);

		document.getElementById("sortable").setInnerHTML("");
		document.getForm("form").clear();

		if (visaoDto.getIdentificador() != null) {
			visaoDto.setIdentificador(visaoDto.getIdentificador().trim());
		}
		if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
			Collection colMatriz = getMatrizVisaoService().findByIdVisao(visaoDto.getIdVisao());
			if (colMatriz != null && colMatriz.size() > 0) {
				MatrizVisaoDTO matrizVisaoDTO = (MatrizVisaoDTO) colMatriz.iterator().next();
				visaoDto.setMatrizVisaoDTO(matrizVisaoDTO);
				visaoDto.setIdObjetoNegocioMatriz(matrizVisaoDTO.getIdObjetoNegocio());
				visaoDto.setIdCamposObjetoNegocio1(matrizVisaoDTO.getIdCamposObjetoNegocio1());
				visaoDto.setIdCamposObjetoNegocio2(matrizVisaoDTO.getIdCamposObjetoNegocio2());
				visaoDto.setIdCamposObjetoNegocio3(matrizVisaoDTO.getIdCamposObjetoNegocio3());
				visaoDto.setDescricaoCampo1(matrizVisaoDTO.getDescricaoCampo1());
				visaoDto.setDescricaoCampo2(matrizVisaoDTO.getDescricaoCampo2());
				visaoDto.setDescricaoCampo3(matrizVisaoDTO.getDescricaoCampo3());

				Collection colCampos = getCamposObjetoNegocioService().findByIdObjetoNegocio(visaoDto.getIdObjetoNegocioMatriz());
				document.getSelectById("idCamposObjetoNegocio1").removeAllOptions();
				document.getSelectById("idCamposObjetoNegocio2").removeAllOptions();
				document.getSelectById("idCamposObjetoNegocio3").removeAllOptions();
				if (colCampos != null) {
					document.getSelectById("idCamposObjetoNegocio1").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
					document.getSelectById("idCamposObjetoNegocio1").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);

					document.getSelectById("idCamposObjetoNegocio2").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
					document.getSelectById("idCamposObjetoNegocio2").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);

					document.getSelectById("idCamposObjetoNegocio3").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
					document.getSelectById("idCamposObjetoNegocio3").addOptions(colCampos, "idCamposObjetoNegocio", "nome", null);
				}
			}
		}
		document.getForm("form").setValues(visaoDto);
		document.executeScript("verificaTipoVisao(document.form.tipoVisao)");

		document.getRadioById("situacaoVisao").setValue("I");
		if (visaoDto.getSituacaoVisao() != null && visaoDto.getSituacaoVisao().equalsIgnoreCase("A")) {
			document.getRadioById("situacaoVisao").setValue("A");
		}

		Collection colFinal = new ArrayList();
		Collection colGrupos = getGrupoVisaoService().findByIdVisao(visaoDto.getIdVisao());
		if (colGrupos != null) {
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
				Collection colGrpVisCpsNeg = getGrupoVisaoCamposNegocioService().findByIdGrupoVisao(grupoVisaoDTO.getIdGrupoVisao());
				if (colGrpVisCpsNeg != null) {
					for (Iterator it2 = colGrpVisCpsNeg.iterator(); it2.hasNext();) {
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it2.next();
						// --
						CamposObjetoNegocioDTO camposObjetoNegocioValid = new CamposObjetoNegocioDTO();
						camposObjetoNegocioValid.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
						camposObjetoNegocioValid = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioValid);
						if (camposObjetoNegocioValid != null) {
							grupoVisaoCamposNegocioAux.setNomeDB(camposObjetoNegocioValid.getNomeDB());

							ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
							objetoNegocioDTO.setIdObjetoNegocio(camposObjetoNegocioValid.getIdObjetoNegocio());
							objetoNegocioDTO = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objetoNegocioDTO);
							if (objetoNegocioDTO != null) {
								grupoVisaoCamposNegocioAux.setNomeTabelaDB(objetoNegocioDTO.getNomeTabelaDB());
							}
						}
						// --
						Collection colValores = getValorVisaoCamposNegocioService().findByIdGrupoVisaoAndIdCampoObjetoNegocio(grupoVisaoDTO.getIdGrupoVisao(), grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
						String[] valoresOpcoes = null;
						if (colValores != null && colValores.size() > 0) {
							valoresOpcoes = new String[colValores.size()];
							int x = 0;
							for (Iterator it3 = colValores.iterator(); it3.hasNext();) {
								ValorVisaoCamposNegocioDTO valorVisaoCamposNegocioDTO = (ValorVisaoCamposNegocioDTO) it3.next();
								valorVisaoCamposNegocioDTO.setValor(valorVisaoCamposNegocioDTO.getValorDescricao());
								valoresOpcoes[x] = valorVisaoCamposNegocioDTO.getValor();
								x++;
							}
						}
						grupoVisaoCamposNegocioAux.setValoresOpcoes(valoresOpcoes);
						grupoVisaoCamposNegocioAux.setColValores(colValores);

						Collection colLigacao = getGrupoVisaoCamposNegocioLigacaoService().findByIdGrupoVisaoAndIdCamposObjetoNegocio(grupoVisaoDTO.getIdGrupoVisao(),
								grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
						if (colLigacao != null) {
							for (Iterator itLigacao = colLigacao.iterator(); itLigacao.hasNext();) {
								GrupoVisaoCamposNegocioLigacaoDTO grupoVisaoCamposNegocioLigacaoDTO = (GrupoVisaoCamposNegocioLigacaoDTO) itLigacao.next();
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.VALUE)) {
									grupoVisaoCamposNegocioAux.setIdCamposObjetoNegocioLigacaoVinc(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									CamposObjetoNegocioDTO camposObjetoNegocioAux2 = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux2.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocioLigacaoVinc());
									try {
										camposObjetoNegocioAux2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux2);
									} catch (Exception e) {
										camposObjetoNegocioAux2 = null;
									}
									if (camposObjetoNegocioAux2 != null) {
										grupoVisaoCamposNegocioAux.setNomeDBLigacaoVinc(camposObjetoNegocioAux2.getNomeDB());
									}
								}
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.ORDER)) {
									grupoVisaoCamposNegocioAux.setIdCamposObjetoNegocioLigacaoOrder(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									CamposObjetoNegocioDTO camposObjetoNegocioAux2 = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux2.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocioLigacaoOrder());
									camposObjetoNegocioAux2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux2);
									if (camposObjetoNegocioAux2 != null) {
										grupoVisaoCamposNegocioAux.setNomeDBLigacaoOrder(camposObjetoNegocioAux2.getNomeDB());
									}
								}
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.PRESENTATION)) {
									grupoVisaoCamposNegocioAux.setIdCamposObjetoNegocioLigacao(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									CamposObjetoNegocioDTO camposObjetoNegocioAux2 = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux2.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocioLigacao());
									try {
										camposObjetoNegocioAux2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux2);
									} catch (Exception e) {
										camposObjetoNegocioAux2 = null;
									}
									if (camposObjetoNegocioAux2 != null) {
										grupoVisaoCamposNegocioAux.setNomeDBLigacao(camposObjetoNegocioAux2.getNomeDB());
									}

									grupoVisaoCamposNegocioAux.setDescricaoRelacionamento(grupoVisaoCamposNegocioLigacaoDTO.getDescricao());

									CamposObjetoNegocioDTO camposObjetoNegocioAux = new CamposObjetoNegocioDTO();
									camposObjetoNegocioAux.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioLigacaoDTO.getIdCamposObjetoNegocioLigacao());
									try {
										camposObjetoNegocioAux = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioAux);
									} catch (Exception e) {
										camposObjetoNegocioAux = null;
									}

									if (camposObjetoNegocioAux != null) {
										grupoVisaoCamposNegocioAux.setIdObjetoNegocioLigacao(camposObjetoNegocioAux.getIdObjetoNegocio());
										ObjetoNegocioDTO objetoNegocioDTO = new ObjetoNegocioDTO();
										objetoNegocioDTO.setIdObjetoNegocio(grupoVisaoCamposNegocioAux.getIdObjetoNegocioLigacao());
										objetoNegocioDTO = (ObjetoNegocioDTO) getObjetoNegocioService().restore(objetoNegocioDTO);
										if (objetoNegocioDTO != null) {
											grupoVisaoCamposNegocioAux.setNomeTabelaDBLigacao(objetoNegocioDTO.getNomeTabelaDB());
										}
									}
								}
								if (grupoVisaoCamposNegocioLigacaoDTO.getTipoLigacao().equalsIgnoreCase(GrupoVisaoCamposNegocioLigacaoDTO.FILTER)) {
									grupoVisaoCamposNegocioAux.setFiltro(grupoVisaoCamposNegocioLigacaoDTO.getFiltro());
								}
							}
						}
					}
				}
				if (colGrpVisCpsNeg != null) {
					colFinal.addAll(colGrpVisCpsNeg);
				}
			}
		}
		request.getSession().setAttribute("visao", null);
		request.getSession().setAttribute("visao", colFinal);
		request.getSession().setAttribute("scripts", null);
		request.getSession().setAttribute("htmlCodes", null);
		request.getSession().setAttribute("botoes", null);
		draw(document, request, response);

		Collection colVisRel = getVisaoRelacionadaService().findByIdVisaoPaiAtivos(visaoDto.getIdVisao());

		if (colVisRel != null) {
			int i = 1;
			for (Iterator it = colVisRel.iterator(); it.hasNext();) {
				if (i > 30) {
					break;
				}
				VisaoRelacionadaDTO visaoRelacionadaDTO = (VisaoRelacionadaDTO) it.next();
				VisaoDTO visaoAux = new VisaoDTO();
				visaoAux.setIdVisao(visaoRelacionadaDTO.getIdVisaoFilha());
				visaoAux = (VisaoDTO) getVisaoService().restore(visaoAux);
				if (visaoAux != null) {
					visaoRelacionadaDTO.setIdentificacaoVisaoFilha(visaoAux.getIdentificador());
				}
				document.executeScript("addTab()");
				document.getSelectById("divVisaoRelacionada_" + i).setValue("" + visaoRelacionadaDTO.getIdVisaoFilha());
				document.getSelectById("tipoVinculacao_" + i).setValue("" + visaoRelacionadaDTO.getTipoVinculacao());
				document.getSelectById("acaoEmSelecaoPesquisa_" + i).setValue("" + visaoRelacionadaDTO.getAcaoEmSelecaoPesquisa());
				document.getSelectById("idObjetoNegocioNN_" + i).setValue("" + visaoRelacionadaDTO.getIdObjetoNegocioNN());
				document.getTextBoxById("titulo_" + i).setValue("" + visaoRelacionadaDTO.getTitulo());
				document.getRadioById("situacaoVisaoVinculada_" + i).setValue("" + visaoRelacionadaDTO.getSituacao());

				document.executeScript("window.setTimeout('mudaCampoObjNN(null, " + i + ")', 1000)");

				Collection colVincs = getVinculoVisaoService().findByIdVisaoRelacionada(visaoRelacionadaDTO.getIdVisaoRelacionada());
				if (colVincs != null) {
					int z = 0;
					for (Iterator itVinc = colVincs.iterator(); itVinc.hasNext();) {
						if (z > 20) {
							break;
						}
						VinculoVisaoDTO vinculoVisaoDTO = (VinculoVisaoDTO) itVinc.next();
						document.getRadioById("tipoVinculo_" + i).setValue("" + vinculoVisaoDTO.getTipoVinculo());
						document.executeScript("mudaTipoVinculo(document.getElementById('tipoVinculo_" + i + "'), '" + i + "', false)");
						listaCamposVisaoImpl(document, request, response, i);
						getCamposFromVisaoRelImpl(document, request, response, i, visaoRelacionadaDTO.getIdVisaoFilha(), "idCamposObjetoNegocioFilho_" + i);
						if (vinculoVisaoDTO.getTipoVinculo() != null && vinculoVisaoDTO.getTipoVinculo().equalsIgnoreCase(VinculoVisaoDTO.VINCULO_1_TO_N)) {
							getCamposFromVisaoRelImpl(document, request, response, i, visaoRelacionadaDTO.getIdVisaoFilha(), "idCamposObjetoNegocioObjNN1_" + i);
						}
						if (vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN() != null) {
							CamposObjetoNegocioDTO campoObj1 = new CamposObjetoNegocioDTO();
							campoObj1.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPai());
							campoObj1 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj1);

							vinculoVisaoDTO.setNomeDBPai(campoObj1.getNomeDB());

							CamposObjetoNegocioDTO campoObj2 = new CamposObjetoNegocioDTO();
							campoObj2.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN());
							campoObj2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj2);

							vinculoVisaoDTO.setNomeDBPaiNN(campoObj2.getNomeDB());

							document.getSelectById("vinculosVisaoPaiNN_" + i).addOptionIfNotExists(vinculoVisaoDTO.getIdCamposObjetoNegocioPai() + "#" + vinculoVisaoDTO.getIdCamposObjetoNegocioPaiNN(),
									campoObj1.getNome() + " - " + campoObj2.getNome());
						}
						if (vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN() != null) {
							CamposObjetoNegocioDTO campoObj1 = new CamposObjetoNegocioDTO();
							campoObj1.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilho());
							campoObj1 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj1);

							vinculoVisaoDTO.setNomeDBPaiFilho(campoObj1.getNomeDB());

							CamposObjetoNegocioDTO campoObj2 = new CamposObjetoNegocioDTO();
							campoObj2.setIdCamposObjetoNegocio(vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN());
							campoObj2 = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(campoObj2);

							vinculoVisaoDTO.setNomeDBPaiFilhoNN(campoObj2.getNomeDB());

							document.getSelectById("vinculosVisaoFilhoNN_" + i).addOptionIfNotExists(vinculoVisaoDTO.getIdCamposObjetoNegocioFilho() + "#" + vinculoVisaoDTO.getIdCamposObjetoNegocioFilhoNN(),
									campoObj1.getNome() + " - " + campoObj2.getNome());
						}
						z++;
					}
				}
				i++;
			}
		}

		Collection colScripts = getScriptsVisaoService().findByIdVisao(grupoVisaoCamposNegocioDTO.getIdVisao());
		visaoDto.setColScripts(colScripts);
		HashMap map = new HashMap();
		if (colScripts != null) {
			for (Iterator it = colScripts.iterator(); it.hasNext();) {
				ScriptsVisaoDTO scriptsVisaoDTO = (ScriptsVisaoDTO) it.next();
				map.put(scriptsVisaoDTO.getTypeExecute() + "#" + scriptsVisaoDTO.getScryptType().trim(), scriptsVisaoDTO.getScript());
			}
		}
		visaoDto.setMapScripts(map);
		request.getSession().setAttribute("scripts", map);

		Collection colHtmlCodes = getHtmlCodeVisaoService().findByIdVisao(grupoVisaoCamposNegocioDTO.getIdVisao());
		visaoDto.setColHtmlCode(colHtmlCodes);
		map = new HashMap();
		if (colHtmlCodes != null) {
			for (Iterator it = colHtmlCodes.iterator(); it.hasNext();) {
				HtmlCodeVisaoDTO htmlCodeVisaoDTO = (HtmlCodeVisaoDTO) it.next();
				map.put(htmlCodeVisaoDTO.getHtmlCodeType().trim(), htmlCodeVisaoDTO.getHtmlCode());
			}
		}
		request.getSession().setAttribute("htmlCodes", map);

		Collection colBotoes = getBotaoAcaoVisaoService().findByIdVisao(grupoVisaoCamposNegocioDTO.getIdVisao());
		request.getSession().setAttribute("botoes", colBotoes);

		document.getSelectById("botaoCadastrado").removeAllOptions();
		if (colBotoes != null) {
			for (Iterator it = colBotoes.iterator(); it.hasNext();) {
				BotaoAcaoVisaoDTO botaoAcaoVisaoAux = (BotaoAcaoVisaoDTO) it.next();
				document.getSelectById("botaoCadastrado").addOption(botaoAcaoVisaoAux.getTexto(), botaoAcaoVisaoAux.getTexto());
			}
		}
		document.executeScript("$( '#tabs' ).tabs('select', 0);");
	}

	public void recuperaItem(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) document.getBean();
		escondeDivs(document);
		document.executeScript("limparItem()");
		if (grupoVisaoCamposNegocioDTO.getNumeroEdicao() == null) {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.informeCampoParaEdicao"));
			return;
		}
		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (col == null) {
			col = new ArrayList();
		}
		int i = 0;
		boolean bEncontrou = false;
		document.getSelectById("valoresOpcoes").removeAllOptions();
		for (Iterator it = col.iterator(); it.hasNext();) {
			GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it.next();
			i++;
			if (i == grupoVisaoCamposNegocioDTO.getNumeroEdicao().intValue()) {
				CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
				camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
				camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioDTO);
				if (camposObjetoNegocioDTO != null) {
					grupoVisaoCamposNegocioAux.setIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				}
				String htmlCode = grupoVisaoCamposNegocioAux.getHtmlCode();
				grupoVisaoCamposNegocioAux.setHtmlCode(""); // Engana o framwork CITAjax
				document.getForm("formItem").setValues(grupoVisaoCamposNegocioAux);
				document.getTextAreaById("htmlCode").setValue(htmlCode);
				grupoVisaoCamposNegocioAux.setHtmlCode(htmlCode);
				Collection colCampos = null;
				if (camposObjetoNegocioDTO != null){
					colCampos = getCamposObjetoNegocioService().findByIdObjetoNegocio(camposObjetoNegocioDTO.getIdObjetoNegocio());
				}
				if (colCampos != null) {
					document.getSelectById("idCamposObjetoNegocio").addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
					document.getSelectById("idCamposObjetoNegocio").addOptions(colCampos, "idCamposObjetoNegocio", "nome", "" + grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());

					aplicaCfgCampoObjNegocio(document, request, response, grupoVisaoCamposNegocioAux);
				}
				Collection colValores = getValorVisaoCamposNegocioService().findByIdGrupoVisaoAndIdCampoObjetoNegocio(grupoVisaoCamposNegocioAux.getIdGrupoVisao(), grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
				if (colValores != null) {
					document.getSelectById("valoresOpcoes").addOptions(colValores, "valorDescricao", "valorDescricaoMostrar", null);
					document.getElementById("divCampoRadioSelect").setVisible(true);
				}
				bEncontrou = true;
				break;
			}
		}
		if (!bEncontrou) {
			document.alert(UtilI18N.internacionaliza(request, "visaoAdm.nenhumCampoEncontrado"));
			return;
		}
		document.executeScript("$( '#POPUP_OBJ' ).dialog( 'open' );");
	}

	public void getCamposFromVisaoRel(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioBean = (GrupoVisaoCamposNegocioDTO) document.getBean();
		getCamposFromVisaoRelImpl(document, request, response, grupoVisaoCamposNegocioBean.getSeq(), grupoVisaoCamposNegocioBean.getIdVisaoRel(), "idCamposObjetoNegocioFilho_" + grupoVisaoCamposNegocioBean.getSeq());
	}

	public void getCamposFromVisaoRelImpl(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer seq, Integer idVisaoRel, String nameCombo) throws Exception {

		Collection colGrupos = getGrupoVisaoService().findByIdVisaoAtivos(idVisaoRel);

		Collection colCamposTodos = new ArrayList();
		if (colGrupos != null) {
			for (Iterator it = colGrupos.iterator(); it.hasNext();) {
				GrupoVisaoDTO grupoVisaoDTO = (GrupoVisaoDTO) it.next();
				grupoVisaoDTO.setColCamposVisao(getGrupoVisaoCamposNegocioService().findByIdGrupoVisaoAtivos(grupoVisaoDTO.getIdGrupoVisao()));

				if (grupoVisaoDTO.getColCamposVisao() != null) {
					for (Iterator it2 = grupoVisaoDTO.getColCamposVisao().iterator(); it2.hasNext();) {
						GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioDTO = (GrupoVisaoCamposNegocioDTO) it2.next();

						CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
						camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioDTO.getIdCamposObjetoNegocio());
						camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioDTO);

						if (camposObjetoNegocioDTO != null) {
							colCamposTodos.add(camposObjetoNegocioDTO);
						}
					}
				}
			}
		}
		document.getSelectById(nameCombo).removeAllOptions();
		document.getSelectById(nameCombo).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		document.getSelectById(nameCombo).addOptions(colCamposTodos, "idCamposObjetoNegocio", "nome", null);
	}

	public void getCampos1ToN(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioBean = (GrupoVisaoCamposNegocioDTO) document.getBean();
		getCamposFromVisaoRelImpl(document, request, response, grupoVisaoCamposNegocioBean.getSeq(), grupoVisaoCamposNegocioBean.getIdVisaoRel(), "idCamposObjetoNegocioObjNN1_" + grupoVisaoCamposNegocioBean.getSeq());
	}

	public void listaCamposVisao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioBean = (GrupoVisaoCamposNegocioDTO) document.getBean();
		listaCamposVisaoImpl(document, request, response, grupoVisaoCamposNegocioBean.getSeq());
	}

	public void listaCamposVisaoImpl(DocumentHTML document, HttpServletRequest request, HttpServletResponse response, Integer seq) throws Exception {
		Collection col = (Collection) request.getSession().getAttribute("visao");
		if (col == null) {
			col = new ArrayList();
		}
		Collection colCamposTodos = new ArrayList();
		for (Iterator it = col.iterator(); it.hasNext();) {
			GrupoVisaoCamposNegocioDTO grupoVisaoCamposNegocioAux = (GrupoVisaoCamposNegocioDTO) it.next();
			CamposObjetoNegocioDTO camposObjetoNegocioDTO = new CamposObjetoNegocioDTO();
			camposObjetoNegocioDTO.setIdCamposObjetoNegocio(grupoVisaoCamposNegocioAux.getIdCamposObjetoNegocio());
			camposObjetoNegocioDTO = (CamposObjetoNegocioDTO) getCamposObjetoNegocioService().restore(camposObjetoNegocioDTO);

			if (camposObjetoNegocioDTO != null) {
				colCamposTodos.add(camposObjetoNegocioDTO);
			}
		}
		document.getSelectById("idCamposObjetoNegocioPai_" + seq).removeAllOptions();
		document.getSelectById("idCamposObjetoNegocioPai_" + seq).addOption("", UtilI18N.internacionaliza(request, "citcorpore.comum.selecione"));
		document.getSelectById("idCamposObjetoNegocioPai_" + seq).addOptions(colCamposTodos, "idCamposObjetoNegocio", "nome", null);
	}
	
	/**
	 * Realiza a leitura de um arquivo e incrementa em uma lista de uploads
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

	public void importarTodasVisoesXML(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DataBaseMetaDadosService dataBaseMetaDadosService = (DataBaseMetaDadosService) ServiceLocator.getInstance().getService(DataBaseMetaDadosService.class, null);
		/*Carregando metaDados*/
		
		String d, s = "";
		Collection colection = dataBaseMetaDadosService.getDataBaseMetaDadosUtil();
		if(colection!=null && !colection.isEmpty()) {
			d = dataBaseMetaDadosService.carregaTodosMetaDados(colection);			

			List<UploadDTO> lista = new ArrayList<UploadDTO>();
			listDirectoryAppend(new File(CITCorporeUtil.caminho_real_app +"/visoesExportadas"), lista);
			
			s = importaVisoes(lista, request, response);
			
		} else {
			
			d = UtilI18N.internacionaliza(request, "visaoAdm.naoFoiPossivelCarregarMetadadosSchema");
		}
		
		document.getElementById("listaMetadadosTb").setInnerHTML(d);
		document.getElementById("listaTodasVisoesTb").setInnerHTML(s);
		document.executeScript("habilita();");
	}
	
	private GrupoVisaoCamposNegocioService getGrupoVisaoCamposNegocioService() throws ServiceException, Exception {
		if (grupoVisaoCamposNegocioService == null) {
			return (GrupoVisaoCamposNegocioService) ServiceLocator.getInstance().getService(GrupoVisaoCamposNegocioService.class, null);
		} else {
			return grupoVisaoCamposNegocioService;
		}
	}

	private CamposObjetoNegocioService getCamposObjetoNegocioService() throws ServiceException, Exception {
		if (camposObjetoNegocioService == null) {
			return (CamposObjetoNegocioService) ServiceLocator.getInstance().getService(CamposObjetoNegocioService.class, null);
		} else {
			return camposObjetoNegocioService;
		}
	}

	private ObjetoNegocioService getObjetoNegocioService() throws ServiceException, Exception {
		if (objetoNegocioService == null) {
			return (ObjetoNegocioService) ServiceLocator.getInstance().getService(ObjetoNegocioService.class, null);
		} else {
			return objetoNegocioService;
		}
	}

	private VinculoVisaoService getVinculoVisaoService() throws ServiceException, Exception {
		if (vinculoVisaoService == null) {
			return (VinculoVisaoService) ServiceLocator.getInstance().getService(VinculoVisaoService.class, null);
		} else {
			return vinculoVisaoService;
		}
	}

	private VisaoService getVisaoService() throws ServiceException, Exception {
		if (visaoService == null) {
			return (VisaoService) ServiceLocator.getInstance().getService(VisaoService.class, null);
		} else {
			return visaoService;
		}
	}

	private GrupoVisaoService getGrupoVisaoService() throws ServiceException, Exception {
		if (grupoVisaoService == null) {
			return (GrupoVisaoService) ServiceLocator.getInstance().getService(GrupoVisaoService.class, null);
		} else {
			return grupoVisaoService;
		}
	}

	private ValorVisaoCamposNegocioService getValorVisaoCamposNegocioService() throws ServiceException, Exception {
		if (valorVisaoCamposNegocioService == null) {
			return (ValorVisaoCamposNegocioService) ServiceLocator.getInstance().getService(ValorVisaoCamposNegocioService.class, null);
		} else {
			return valorVisaoCamposNegocioService;
		}
	}

	private GrupoVisaoCamposNegocioLigacaoService getGrupoVisaoCamposNegocioLigacaoService() throws ServiceException, Exception {
		if (grupoVisaoCamposNegocioLigacaoService == null) {
			return (GrupoVisaoCamposNegocioLigacaoService) ServiceLocator.getInstance().getService(GrupoVisaoCamposNegocioLigacaoService.class, null);
		} else {
			return grupoVisaoCamposNegocioLigacaoService;
		}
	}

	private ScriptsVisaoService getScriptsVisaoService() throws ServiceException, Exception {
		if (scriptsVisaoService == null) {
			return (ScriptsVisaoService) ServiceLocator.getInstance().getService(ScriptsVisaoService.class, null);
		} else {
			return scriptsVisaoService;
		}
	}

	private HtmlCodeVisaoService getHtmlCodeVisaoService() throws ServiceException, Exception {
		if (htmlCodeVisaoService == null) {
			return (HtmlCodeVisaoService) ServiceLocator.getInstance().getService(HtmlCodeVisaoService.class, null);
		} else {
			return htmlCodeVisaoService;
		}
	}

	private BotaoAcaoVisaoService getBotaoAcaoVisaoService() throws ServiceException, Exception {
		if (botaoAcaoVisaoService == null) {
			return (BotaoAcaoVisaoService) ServiceLocator.getInstance().getService(BotaoAcaoVisaoService.class, null);
		} else {
			return botaoAcaoVisaoService;
		}
	}

	private MatrizVisaoService getMatrizVisaoService() throws ServiceException, Exception {
		if (matrizVisaoService == null) {
			return (MatrizVisaoService) ServiceLocator.getInstance().getService(MatrizVisaoService.class, null);
		} else {
			return matrizVisaoService;
		}
	}

	private VisaoRelacionadaService getVisaoRelacionadaService() throws ServiceException, Exception {
		if (visaoRelacionadaService == null) {
			return (VisaoRelacionadaService) ServiceLocator.getInstance().getService(VisaoRelacionadaService.class, null);
		} else {
			return visaoRelacionadaService;
		}
	}

}
