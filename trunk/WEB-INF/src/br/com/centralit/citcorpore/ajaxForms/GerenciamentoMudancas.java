package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.GerenciamentoMudancasDTO;
import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoMudancaService;
import br.com.centralit.citcorpore.negocio.RequisicaoMudancaService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
public class GerenciamentoMudancas extends AjaxFormAction {

	@Override
	public Class getBeanClass() {
		return GerenciamentoMudancasDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		exibeTarefas(document, request, response);
	}

	public void exibeTarefas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sessao expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoMudancasDTO gerenciamentoBean = (GerenciamentoMudancasDTO) document.getBean();

		ExecucaoMudancaService execucaoMudancaService = (ExecucaoMudancaService) ServiceLocator.getInstance().getService(ExecucaoMudancaService.class, null);
		List<TarefaFluxoDTO> colTarefas = execucaoMudancaService.recuperaTarefas(usuario.getLogin());
		if (colTarefas == null)
			return;

		boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdRequisicaoSel() != null && gerenciamentoBean.getIdRequisicaoSel().length() > 0;
		List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
		if (!bFiltroPorSolicitacao)
			colTarefasFiltradas.addAll(colTarefas);
		else {
			for (TarefaFluxoDTO tarefaDto : colTarefas) {
				boolean bAdicionar = false;
				String idRequisicao = "" + ((RequisicaoMudancaDTO) tarefaDto.getRequisicaoMudancaDto()).getIdRequisicaoMudanca();
				bAdicionar = idRequisicao.indexOf(gerenciamentoBean.getIdRequisicaoSel()) >= 0;
				if (bAdicionar)
					colTarefasFiltradas.add(tarefaDto);
			}
		}
		List colTarefasFiltradasFinal = new ArrayList();
		HashMap mapAtr = new HashMap();
		mapAtr.put("-- Sem AtribuiÁ„o --", "-- Sem AtribuiÁ„o --");
		for (TarefaFluxoDTO tarefaDto : colTarefasFiltradas) {
			RequisicaoMudancaDTO requisicaoMudancaDto = (RequisicaoMudancaDTO) tarefaDto.getRequisicaoMudancaDto();
			requisicaoMudancaDto.setDataHoraLimiteToString(""); // Apenas forca atualizacao
			requisicaoMudancaDto.setDataHoraInicioToString(""); // Apenas forca atualizacao
			requisicaoMudancaDto.setDescricao("");
			int prazoHH = 0;
			int prazoMM = 0;
			if (requisicaoMudancaDto.getPrazoHH() != null) {
				prazoHH = requisicaoMudancaDto.getPrazoHH();
			}
			if (requisicaoMudancaDto.getPrazoMM() != null) {
				prazoMM = requisicaoMudancaDto.getPrazoMM();
			}

			// tarefaDto.setSolicitacaoDto(null);

			if (requisicaoMudancaDto.getResponsavelAtual() != null) {
				if (!mapAtr.containsKey(requisicaoMudancaDto.getResponsavelAtual())) {
					mapAtr.put(requisicaoMudancaDto.getResponsavelAtual(), requisicaoMudancaDto.getResponsavelAtual());
				}
			}

			if (gerenciamentoBean.getAtribuidaCompartilhada() == null || gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase("")) {
				colTarefasFiltradasFinal.add(tarefaDto);
			} else {
				if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"))) {
					if (requisicaoMudancaDto.getResponsavelAtual() == null || requisicaoMudancaDto.getResponsavelAtual().trim().equalsIgnoreCase("")) {
						colTarefasFiltradasFinal.add(tarefaDto);
					}
				} else {
					if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(requisicaoMudancaDto.getResponsavelAtual())) {
						colTarefasFiltradasFinal.add(tarefaDto);
					}
				}
			}
		}
		String tarefasStr = serializaTarefas(colTarefasFiltradasFinal);

		/*
		 * Collections.sort(colTarefasFiltradasFinal, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC)); String tarefasStr = new Gson().toJson(colTarefasFiltradasFinal);
		 * tarefasStr = tarefasStr.replaceAll("\n", " "); tarefasStr = tarefasStr.replaceAll("\r", " "); tarefasStr = tarefasStr.replaceAll("\\\\n", " ");
		 */

		document.executeScript("exibirTarefas('" + tarefasStr + "');");

		document.getSelectById("atribuidaCompartilhada").removeAllOptions();
		document.getSelectById("atribuidaCompartilhada").addOption("", "-- " + UtilI18N.internacionaliza(request, "citcorpore.comum.todos") + " --");
		for (Iterator it = mapAtr.values().iterator(); it.hasNext();) {
			String str = (String) it.next();
			document.getSelectById("atribuidaCompartilhada").addOption(str, str);
		}
		document.getSelectById("atribuidaCompartilhada").setValue(gerenciamentoBean.getAtribuidaCompartilhada());
	}

	public void preparaExecucaoTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sess„o expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoMudancasDTO gerenciamentoBean = (GerenciamentoMudancasDTO) document.getBean();
		if (gerenciamentoBean.getIdTarefa() == null)
			return;

		ExecucaoMudancaService execucaoMudancaService = (ExecucaoMudancaService) ServiceLocator.getInstance().getService(ExecucaoMudancaService.class, null);
		TarefaFluxoDTO tarefaDto = execucaoMudancaService.recuperaTarefa(usuario.getLogin(), gerenciamentoBean.getIdTarefa());
		if (tarefaDto == null || tarefaDto.getElementoFluxoDto() == null || !tarefaDto.getExecutar().equals("S") || tarefaDto.getElementoFluxoDto().getTipoInteracao() == null)
			return;

		if (tarefaDto.getElementoFluxoDto().getTipoInteracao().equals(Enumerados.INTERACAO_VISAO)) {
			if (tarefaDto.getIdVisao() != null) {
				document.executeScript("exibirVisao('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getIdVisao() + "','"
						+ tarefaDto.getElementoFluxoDto().getIdFluxo() + "','" + tarefaDto.getIdItemTrabalho() + "','" + gerenciamentoBean.getAcaoFluxo() + "');");
			} else {
				document.alert("Vis„o para tarefa \"" + tarefaDto.getElementoFluxoDto().getDocumentacao() + "\" n„o encontrada");
			}
		} else {
			String caracterParmURL = "?";
			if (tarefaDto.getElementoFluxoDto().getUrl().indexOf("?") > -1) { // Se na URL j√° conter ?, entao colocar &
				caracterParmURL = "&";
			}
			document.executeScript("exibirUrl('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getElementoFluxoDto().getUrl() + caracterParmURL
					+ "idRequisicaoMudanca=" + ((RequisicaoMudancaDTO) tarefaDto.getRequisicaoMudancaDto()).getIdRequisicaoMudanca() + "&idTarefa=" + tarefaDto.getIdItemTrabalho() + "&acaoFluxo="
					+ gerenciamentoBean.getAcaoFluxo() + "');");
		}
	}

	public void reativaRequisicao(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sess„o expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoMudancasDTO gerenciamentoBean = (GerenciamentoMudancasDTO) document.getBean();
		if (gerenciamentoBean.getIdRequisicao() == null)
			return;

		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);
		RequisicaoMudancaDTO requisicaoMudancaDto = requisicaoMudancaService.restoreAll(gerenciamentoBean.getIdRequisicao());
		requisicaoMudancaService.reativa(usuario, requisicaoMudancaDto);
		exibeTarefas(document, request, response);
	}

	private String serializaTarefas(List<TarefaFluxoDTO> colTarefas) throws Exception {
		if (colTarefas == null)
			return null;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			String elementoFluxo_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto()));
			String requisicaoMudanca_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getRequisicaoMudancaDto()));
			
			tarefaDto.setElementoFluxo_serialize(elementoFluxo_serialize);
			tarefaDto.setSolicitacao_serialize(requisicaoMudanca_serialize);
		}
		return br.com.citframework.util.WebUtil.serializeObjects(colTarefas);
	}

	public void capturaTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoMudancasDTO requisicaoBean = (GerenciamentoMudancasDTO) document.getBean();

		if (requisicaoBean.getIdTarefa() == null) {
			return;
		}
		
		RequisicaoMudancaService requisicaoMudancaService = (RequisicaoMudancaService) ServiceLocator.getInstance().getService(RequisicaoMudancaService.class, null);

		RequisicaoMudancaDTO requisicaoDto = new RequisicaoMudancaDTO();
		requisicaoDto.setIdRequisicaoMudanca(requisicaoBean.getIdRequisicao());
		requisicaoDto = (RequisicaoMudancaDTO) requisicaoMudancaService.restore(requisicaoDto);
		
		requisicaoDto.setIdProprietario(usuario.getIdUsuario());
		requisicaoMudancaService.update(requisicaoDto);
		
		ExecucaoMudancaService execucaoMudancaService = (ExecucaoMudancaService) ServiceLocator.getInstance().getService(ExecucaoMudancaService.class, null);
		execucaoMudancaService.executa(usuario, requisicaoBean.getIdTarefa(), Enumerados.ACAO_INICIAR);
		exibeTarefas(document, request, response);

		requisicaoBean = null;
	}

}
