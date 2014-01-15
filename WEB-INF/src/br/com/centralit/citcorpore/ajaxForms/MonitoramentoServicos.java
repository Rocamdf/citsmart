package br.com.centralit.citcorpore.ajaxForms;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.GerenciamentoServicosDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ExecucaoSolicitacaoService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({"rawtypes","unchecked"})
public class MonitoramentoServicos extends AjaxFormAction {

	
	@Override
	public Class getBeanClass() {
		return GerenciamentoServicosDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		exibeTarefas(document,request,response);
	}

	public void exibeTarefas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null){
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}	
		
		GerenciamentoServicosDTO gerenciamentoBean = (GerenciamentoServicosDTO)document.getBean();	
		
		ExecucaoSolicitacaoService execucaoSolicitacaoService = (ExecucaoSolicitacaoService) ServiceLocator.getInstance().getService(ExecucaoSolicitacaoService.class, null);
		List<TarefaFluxoDTO> colTarefas = execucaoSolicitacaoService.recuperaTarefas(usuario.getLogin());
		if (colTarefas == null)
			return;
		
		boolean bFiltroPorContrato = gerenciamentoBean.getNumeroContratoSel() != null && gerenciamentoBean.getNumeroContratoSel().length() > 0;
		boolean bFiltroPorSolicitacao = gerenciamentoBean.getIdSolicitacaoSel() != null && gerenciamentoBean.getIdSolicitacaoSel().length() > 0;
		List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
		if (!bFiltroPorContrato && !bFiltroPorSolicitacao) 
			colTarefasFiltradas.addAll(colTarefas);
		else{
			for (TarefaFluxoDTO tarefaDto : colTarefas) {
				boolean bAdicionar = false;
				String contrato = UtilStrings.nullToVazio(((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getContrato());
				String idSolicitacao = ""+((SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto()).getIdSolicitacaoServico();
				if (bFiltroPorContrato && bFiltroPorSolicitacao)
					bAdicionar = contrato.indexOf(gerenciamentoBean.getNumeroContratoSel()) >= 0 && idSolicitacao.indexOf(gerenciamentoBean.getIdSolicitacaoSel()) >= 0; 
				else if (bFiltroPorContrato)
					bAdicionar = contrato.indexOf(gerenciamentoBean.getNumeroContratoSel()) >= 0;
				else
					bAdicionar = idSolicitacao.indexOf(gerenciamentoBean.getIdSolicitacaoSel()) >= 0;
				if (bAdicionar)
					colTarefasFiltradas.add(tarefaDto);
			}
		}
		for(TarefaFluxoDTO tarefaDto : colTarefasFiltradas){
			SolicitacaoServicoDTO dtoSol = (SolicitacaoServicoDTO) tarefaDto.getSolicitacaoDto();
			dtoSol.setDataHoraLimiteToString(""); //Apenas forca atualizacao
			dtoSol.setDataHoraSolicitacaoToString(""); //Apenas forca atualizacao
			dtoSol.setDescricao("");
			dtoSol.setDescricaoSemFormatacao("");
			dtoSol.setResposta("");
			dtoSol.setDetalhamentoCausa("");
			dtoSol.setCaracteristica("");
			dtoSol.setColArquivosUpload(null);
			dtoSol.setColItensICSerialize(null);
			dtoSol.setColItensMudanca(null);
			dtoSol.setColItensProblema(null);
			dtoSol.setComplementoJustificativa("");
			dtoSol.setDemanda("");
			dtoSol.setDetalhamentoCausa("");
			dtoSol.setEditar("");
			dtoSol.setEmailcontato("");
			dtoSol.setEnviaEmailAcoes("");
			dtoSol.setEnviaEmailCriacao("");
			dtoSol.setEnviaEmailFinalizacao("");
			dtoSol.setEscalar("");
			dtoSol.setExibirCampoDescricao("");
			dtoSol.setObservacao("");
			dtoSol.setPalavraChave("");
			if (dtoSol.getSlaACombinar() == null){
			    dtoSol.setSlaACombinar("N");
			}			
			int prazoHH = 0;
			int prazoMM = 0;
			if (dtoSol.getPrazoHH() != null){
			    prazoHH = dtoSol.getPrazoHH();
			}
			if (dtoSol.getPrazoMM() != null){
			    prazoMM = dtoSol.getPrazoMM();
			}	
			if (prazoHH == 0 && prazoMM == 0){
			    dtoSol.setSlaACombinar("S");
			    dtoSol.setAtrasoSLA(0);
			    dtoSol.setAtrasoSLAStr("");
			    dtoSol.setDataHoraLimiteStr("");
			}	
			if (dtoSol.getSlaACombinar().equalsIgnoreCase("S")){
			    dtoSol.setDataHoraLimite(null);
			    tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 10, Calendar.YEAR).getTime()));
			}
			if (dtoSol.getSituacao().equalsIgnoreCase(br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico.Suspensa.name())){
			    dtoSol.setDataHoraLimite(null);
			    tarefaDto.setDataHoraLimite(new Timestamp(UtilDatas.alteraData(UtilDatas.getDataAtual(), 10, Calendar.YEAR).getTime()));
			}			
			if (dtoSol.getSolicitanteUnidade() != null && !dtoSol.getSolicitanteUnidade().trim().equalsIgnoreCase("")){
			    dtoSol.setSolicitante(""); //pra nao enviar no JSON
			}else{
			    dtoSol.setSolicitanteUnidade(dtoSol.getSolicitante());
			}
		}
		//Collections.sort(colTarefasFiltradas, new ObjectSimpleComparator("getDataHoraLimite", ObjectSimpleComparator.ASC));
//		String tarefasStr = new Gson().toJson(colTarefasFiltradas);
//		tarefasStr = tarefasStr.replaceAll("\n", " ");
//		tarefasStr = tarefasStr.replaceAll("\r", " ");
//		tarefasStr = tarefasStr.replaceAll("\\\\n", " ");
		

		String tarefasStr = serializaTarefas(colTarefasFiltradas);

		document.executeScript("exibirTarefas('" + tarefasStr + "');");
		
	}

	private String serializaTarefas(List<TarefaFluxoDTO> colTarefas) throws Exception {
		if (colTarefas == null)
			return null;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			String elementoFluxo_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto()));
			String requisicaoSolicitacao_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getSolicitacaoDto()));
			
			tarefaDto.setElementoFluxo_serialize(elementoFluxo_serialize);
			tarefaDto.setSolicitacao_serialize(requisicaoSolicitacao_serialize);
		}
		return br.com.citframework.util.WebUtil.serializeObjects(colTarefas);
	}


}
