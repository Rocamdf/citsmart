package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.FluxoServicoDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

/**
 * 
 * @author Cledson.junior
 * 
 */
public class ServicoContratoMulti extends ServicoContrato {

	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ServicoContratoDTO servicoContratoDTO = (ServicoContratoDTO) document.getBean();
		
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		
		List<FluxoServicoDTO> listFluxoServicoDTO = (ArrayList<FluxoServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(FluxoServicoDTO.class, "fluxosSerializados", request);
		List<ServicoDTO> listServicoDto = (ArrayList<ServicoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ServicoDTO.class, "servicosSerializados", request);

		if (listServicoDto == null || listServicoDto.isEmpty()) {
			document.alert(UtilI18N.internacionaliza(request, "servicoContrato.selecioneServicos"));
			document.executeScript("JANELA_AGUARDE_MENU.hide()");
			return;
		}

		servicoContratoDTO.setListaFluxo((listFluxoServicoDTO == null ? new ArrayList<FluxoServicoDTO>() : listFluxoServicoDTO));
		servicoContratoDTO.setListaServico((listServicoDto == null ? new ArrayList<ServicoDTO>() : listServicoDto));
		
		try {
			if (servicoContratoDTO.getIdServicoContrato() == null || servicoContratoDTO.getIdServicoContrato().intValue() == 0) {
				for (ServicoDTO servicoDto : servicoContratoDTO.getListaServico()) {
					servicoContratoDTO.setIdServico(servicoDto.getIdServico());
					servicoContratoDTO = (ServicoContratoDTO) servicoContratoService.create(servicoContratoDTO);
				}
			}
			
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		document.executeScript("closePopup();");
		document.executeScript("JANELA_AGUARDE_MENU.show()");
	}

	/**
	 * Restaura a lista de todos os serviços para a tela de Adicionar Vários Serviços ao Contrato.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 * @author valdoilo.damasceno
	 */
	public void adicionarTodosOsServicos(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

		Collection<ServicoDTO> listServicoAtivo = servicoService.listAtivos();

		if (listServicoAtivo != null && !listServicoAtivo.isEmpty()) {

			for (ServicoDTO servicoDto : listServicoAtivo) {

				document.executeScript("addLinhaTabelaServico(" + servicoDto.getIdServico() + ",'" + servicoDto.getNomeServico() + "')");
			}
			
		}else{
			
		}
		
		document.executeScript("JANELA_AGUARDE_MENU.hide()");

	}

	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */

	public Class<ServicoContratoDTO> getBeanClass() {
		return ServicoContratoDTO.class;
	}

}
