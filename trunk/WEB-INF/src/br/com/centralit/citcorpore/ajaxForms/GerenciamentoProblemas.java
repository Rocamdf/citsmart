package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.bpm.dto.TarefaFluxoDTO;
import br.com.centralit.bpm.util.Enumerados;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.GerenciamentoProblemasDTO;
import br.com.centralit.citcorpore.bean.ProblemaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.integracao.ProblemaDAO;
import br.com.centralit.citcorpore.negocio.CategoriaProblemaService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.ExecucaoProblemaService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.negocio.ProblemaItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.ProblemaService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;

@SuppressWarnings({ "rawtypes", "unchecked","unused" })
public class GerenciamentoProblemas extends AjaxFormAction {
	private ProblemaService problemaService;
	private EmpregadoService empregadoService;
	private CategoriaProblemaService categoriaProblemaService;
	private ProblemaItemConfiguracaoService problemaItemConfiguracaoService;
	private ItemConfiguracaoService itemConfiguracaoService;
	private PastaService pastaService;
	private UsuarioDTO usuario;

	@Override
	public Class getBeanClass() {
		return GerenciamentoProblemasDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*usuario = WebUtil.getUsuario(request);
		
		if(usuario!=null){
			
			Problema problema = new Problema();
			
			problema.notificarPrazoSolucionarProblemaExpirou(document, request, response, usuario);
			
		}*/
		
		exibeTarefas(document, request, response);
		
		
	}

	
	public void exibeTarefas(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		ProblemaDTO problemaDto = new ProblemaDTO();
		
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sessao expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoProblemasDTO gerenciamentoBean = (GerenciamentoProblemasDTO) document.getBean();

		ExecucaoProblemaService execucaoProblemaService = (ExecucaoProblemaService) ServiceLocator.getInstance().getService(ExecucaoProblemaService.class, null);
		List<TarefaFluxoDTO> colTarefas = execucaoProblemaService.recuperaTarefas(usuario.getLogin());
		if (colTarefas == null)
			return;

		boolean bFiltroPorSolicitacao = false;
		if(gerenciamentoBean != null){
			bFiltroPorSolicitacao = gerenciamentoBean.getIdProblemaSel() != null && gerenciamentoBean.getIdProblemaSel().length() > 0;
		}
		List<TarefaFluxoDTO> colTarefasFiltradas = new ArrayList();
		if (!bFiltroPorSolicitacao)
			colTarefasFiltradas.addAll(colTarefas);
		else {
			for (TarefaFluxoDTO tarefaDto : colTarefas) {
				boolean bAdicionar = false;
				String idProblema = "" + ((ProblemaDTO) tarefaDto.getProblemaDto()).getIdProblema();
				bAdicionar = idProblema.indexOf(gerenciamentoBean.getIdProblemaSel()) >= 0;
				if (bAdicionar)
					colTarefasFiltradas.add(tarefaDto);
			}
		}
		List colTarefasFiltradasFinal = new ArrayList();
		HashMap mapAtr = new HashMap();
		mapAtr.put("-- Sem Atribuição --", "-- Sem Atribuição --");
		for (TarefaFluxoDTO tarefaDto : colTarefasFiltradas) {
			problemaDto = (ProblemaDTO) tarefaDto.getProblemaDto();
			problemaDto.setDataHoraInicioSLAStr("");
			problemaDto.setDescricao("");
			int prazoHH = 0;
			int prazoMM = 0;
			if (problemaDto.getPrazoHH() != null) {
				prazoHH = problemaDto.getPrazoHH();
			}
			if (problemaDto.getPrazoMM() != null) {
				prazoMM = problemaDto.getPrazoMM();
			}

			if (problemaDto.getResponsavel() != null) {
				if (!mapAtr.containsKey(problemaDto.getResponsavel())) {
					mapAtr.put(problemaDto.getResponsavel(), problemaDto.getResponsavel());
				}
			}

			if (gerenciamentoBean.getAtribuidaCompartilhada() == null || gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase("")) {
				colTarefasFiltradasFinal.add(tarefaDto);
			} else {
				if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(UtilI18N.internacionaliza(request, "citcorpore.comum.sematribuicao"))) {
					if (tarefaDto.getResponsavelAtual() == null || tarefaDto.getResponsavelAtual().trim().equalsIgnoreCase("")) {
						colTarefasFiltradasFinal.add(tarefaDto);
					}
				} else {
					if (gerenciamentoBean.getAtribuidaCompartilhada().trim().equalsIgnoreCase(tarefaDto.getResponsavelAtual())) {
						colTarefasFiltradasFinal.add(tarefaDto);
					}
				}
			}
		}
		
			if (gerenciamentoBean != null && (gerenciamentoBean.getIdProblemaSel() != null && !gerenciamentoBean.getIdProblemaSel().trim().equalsIgnoreCase(""))){
				problemaDto.setIdProblema(new Integer(gerenciamentoBean.getIdProblemaSel()));
			}
			
			String tarefasStr = serializaTarefas(colTarefasFiltradasFinal);
			
			document.executeScript("exibirTarefas('" + tarefasStr + "');");
			
	}
		
	private String serializaTarefas(List<TarefaFluxoDTO> colTarefas) throws Exception {
		if (colTarefas == null)
			return null;
		for (TarefaFluxoDTO tarefaDto : colTarefas) {
			String elementoFluxo_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getElementoFluxoDto()));
			String problema_serialize = StringEscapeUtils.escapeJavaScript(br.com.citframework.util.WebUtil.serializeObject(tarefaDto.getProblemaDto()));
			
			tarefaDto.setElementoFluxo_serialize(elementoFluxo_serialize);
			tarefaDto.setProblema_serialize(problema_serialize);
		}
		return br.com.citframework.util.WebUtil.serializeObjects(colTarefas);
	}

	public void preparaExecucaoTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoProblemasDTO gerenciamentoBean = (GerenciamentoProblemasDTO) document.getBean();
		if (gerenciamentoBean.getIdTarefa() == null)
			return;
		
		if(gerenciamentoBean.getIdProblema() == null)
			return;

		ExecucaoProblemaService execucaoProblemaService = (ExecucaoProblemaService) ServiceLocator.getInstance().getService(ExecucaoProblemaService.class, null);
		TarefaFluxoDTO tarefaDto = execucaoProblemaService.recuperaTarefa(usuario.getLogin(), gerenciamentoBean.getIdTarefa());
		if (tarefaDto == null || tarefaDto.getElementoFluxoDto() == null || !tarefaDto.getExecutar().equals("S") || tarefaDto.getElementoFluxoDto().getTipoInteracao() == null)
			return;

		if (tarefaDto.getElementoFluxoDto().getTipoInteracao().equals(Enumerados.INTERACAO_VISAO)) {
			if (tarefaDto.getIdVisao() != null) {
				document.executeScript("exibirVisao('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getIdVisao() + "','"
						+ tarefaDto.getElementoFluxoDto().getIdFluxo() + "','" + tarefaDto.getIdItemTrabalho() + "','" + gerenciamentoBean.getAcaoFluxo() + "');");
			} else {
				document.alert("Visão para tarefa \"" + tarefaDto.getElementoFluxoDto().getDocumentacao() + "\" não encontrada");
			}
		} else {
			String caracterParmURL = "?";
			if (tarefaDto.getElementoFluxoDto().getUrl().indexOf("?") > -1) { // Se na URL ja tiver ?, entao colocar &
				caracterParmURL = "&";
			}
			document.executeScript("exibirUrl('Executar tarefa " + tarefaDto.getElementoFluxoDto().getDocumentacao() + "','" + tarefaDto.getElementoFluxoDto().getUrl()
					+ caracterParmURL + "idProblema=" + gerenciamentoBean.getIdProblema() + "&idTarefa="
					+ tarefaDto.getIdItemTrabalho() + "&acaoFluxo=" + gerenciamentoBean.getAcaoFluxo() + "');");
		}
	}

	public void reativaProblema(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert("Sessão expirada! Favor efetuar logon novamente!");
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoProblemasDTO gerenciamentoBean = (GerenciamentoProblemasDTO) document.getBean();
		if (gerenciamentoBean.getIdProblema() == null)
			return;

		ProblemaService problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		ProblemaDTO problemaDto = problemaService.restoreAll(gerenciamentoBean.getIdProblema());
		problemaService.reativa(usuario, problemaDto);
		exibeTarefas(document, request, response);
	}
	
	public void capturaTarefa(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}

		GerenciamentoProblemasDTO problemaBean = (GerenciamentoProblemasDTO) document.getBean();

		if (problemaBean.getIdTarefa() == null) {
			return;
		}

		if (problemaBean.getIdProblema() == null){
			return;
		}
		
		ProblemaDTO problemaDto = new ProblemaDTO();
		ProblemaDAO problemaDao = new ProblemaDAO();
		problemaDto.setIdProblema(problemaBean.getIdProblema());
		problemaDto.setIdProprietario(usuario.getIdUsuario());
		problemaDao.updateNotNull(problemaDto);
		
		
		
		ExecucaoProblemaService execucaoProblemaService = (ExecucaoProblemaService) ServiceLocator.getInstance().getService(ExecucaoProblemaService.class, null);
		execucaoProblemaService.executa(usuario, problemaBean.getIdTarefa(), Enumerados.ACAO_INICIAR);
		exibeTarefas(document, request, response);

		problemaBean = null;
	}
	
	
	private ProblemaService getProblemaService() throws ServiceException, Exception {
		if (problemaService == null) {
			problemaService = (ProblemaService) ServiceLocator.getInstance().getService(ProblemaService.class, null);
		}
		return problemaService;
	}

	private EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		if (empregadoService == null) {
			empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		}
		return empregadoService;
	}

	private CategoriaProblemaService getCategoriaProblemaService() throws ServiceException, Exception {
		if (categoriaProblemaService == null) {
			categoriaProblemaService = (CategoriaProblemaService) ServiceLocator.getInstance().getService(CategoriaProblemaService.class, null);
		}
		return categoriaProblemaService;
	}

	private ProblemaItemConfiguracaoService getProblemaItemConfiguracaoService() throws ServiceException, Exception {
		if (problemaItemConfiguracaoService == null) {
			problemaItemConfiguracaoService = (ProblemaItemConfiguracaoService) ServiceLocator.getInstance().getService(ProblemaItemConfiguracaoService.class, null);
		}
		return problemaItemConfiguracaoService;
	}

	private ItemConfiguracaoService getItemConfiguracaoService() throws ServiceException, Exception {
		if (itemConfiguracaoService == null) {
			itemConfiguracaoService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
		}
		return itemConfiguracaoService;
	}

	
	private PastaService getPastaService() throws ServiceException, Exception {
		if (pastaService == null) {
			pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		}
		return pastaService;
	}
}