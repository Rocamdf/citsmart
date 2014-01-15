package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.AtividadesServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.AtividadesServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

/**
 * 
 * @author Cledson.junior
 *
 */
public class AtividadesServicoContrato extends AjaxFormAction {
	
	/**
	 * Inicializa os dados ao carregar a tela.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
	}
	
	/**
	 * Inclui registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
		AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		if (atividadesServicoContrato.getIdAtividadeServicoContrato() == null || atividadesServicoContrato.getIdAtividadeServicoContrato().intValue() == 0) {
		if (atividadesServicoContrato.getTipoCusto().equals("F") && !atividadesServicoContrato.getPeriodo().equals("4") && !atividadesServicoContrato.getPeriodo().equals("5") ) {
			atividadesServicoContrato.setCustoAtividade(atividadeServicoService.calculaFormula(atividadesServicoContrato));
		}
			atividadeServicoService.create(atividadesServicoContrato);
			document.alert(UtilI18N.internacionaliza(request, "MSG05"));
		} else {
			if (atividadesServicoContrato.getTipoCusto().equals("F") && !atividadesServicoContrato.getPeriodo().equals("4") && !atividadesServicoContrato.getPeriodo().equals("5") ) {
				atividadesServicoContrato.setCustoAtividade(atividadeServicoService.calculaFormula(atividadesServicoContrato));
			}
			atividadeServicoService.update(atividadesServicoContrato);
			document.alert(UtilI18N.internacionaliza(request, "MSG06"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
		document.executeScript("closePopup(" + atividadesServicoContrato.getIdServicoContrato() + ");");
	}
	/**
	 * Restaura os dados ao clicar em um registro.
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
		AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		atividadesServicoContrato = (AtividadesServicoContratoDTO) atividadeServicoService.restore(atividadesServicoContrato);
		
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(atividadesServicoContrato);
	}
	
	public void restoreAtividadeServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
		
		ServicoDTO servicoBean = new ServicoDTO();
		ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);

		servicoBean.setIdServico(atividadesServicoContrato.getIdServicoContratoContabil());
		servicoBean = (ServicoDTO) servicoService.restore(servicoBean);
		atividadesServicoContrato.setNomeServico(servicoBean.getNomeServico());
		atividadesServicoContrato.setIdServicoContratoContabil(servicoBean.getIdServico());
		HTMLForm form = document.getForm("form");
		//form.clear();
		form.setValues(atividadesServicoContrato);
		document.executeScript("fecharPopup()");

	}
	
	/**
	 * recupera os dados ao carregar página
	 * 
	 * @param document
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void recupera(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
		AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class, WebUtil.getUsuarioSistema(request));
		HTMLForm form = document.getForm("form");
		// form.clear();
		if(atividadesServicoContrato.getIdAtividadeServicoContrato() != null) {
			atividadesServicoContrato = (AtividadesServicoContratoDTO) atividadeServicoService.restore(atividadesServicoContrato);
			if (atividadesServicoContrato.getIdServicoContratoContabil() != null) {
				ServicoDTO servicoBean = new ServicoDTO();
				ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
				servicoBean.setIdServico(atividadesServicoContrato.getIdServicoContratoContabil());
				servicoBean = (ServicoDTO) servicoService.restore(servicoBean);
				atividadesServicoContrato.setNomeServico(servicoBean.getNomeServico());
			}
			
			form.setValues(atividadesServicoContrato);
			
			if (atividadesServicoContrato.getFormula() == null || atividadesServicoContrato.getFormula().isEmpty()) {
				document.executeScript("document.getElementById('TIPOCUSTO').value = 'C'");
				document.executeScript("document.getElementById('divByCustoFormula').style.display = 'none'");
				document.executeScript("document.getElementById('divByCustoFormula').style.display = 'none'");
				document.executeScript("document.getElementById('divByCustoTotal').style.display = 'block'");
				document.executeScript("document.getElementById('divByCustoTotal2').style.display = 'block'");
			}
			if (atividadesServicoContrato.getTipoCusto() != null && atividadesServicoContrato.getTipoCusto().equalsIgnoreCase("F")) {
				document.executeScript("document.getElementById('TIPOCUSTO').value = 'F'");
				document.executeScript("document.getElementById('divByCustoFormula').style.display = 'block'");
				document.executeScript("document.getElementById('divByCustoFormula').style.display = 'block'");
				document.executeScript("document.getElementById('divByCustoTotal').style.display = 'none'");
				document.executeScript("document.getElementById('divByCustoTotal2').style.display = 'none'");
				document.executeScript("geraFormula()");
			}
			if (atividadesServicoContrato.getNomeServico() != null && atividadesServicoContrato.getNomeServico() != "") {
				document.executeScript("document.getElementById('CONTABILIZAR').value = 'S'");
				document.executeScript("document.getElementById('divComboServicoContrato').style.display = 'block'");
				document.executeScript("document.getElementById('addServicoContrato').value = "+atividadesServicoContrato.getNomeServico());
			}
			
		}
	}
	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			AtividadesServicoContratoDTO atividadesServicoContrato = (AtividadesServicoContratoDTO) document.getBean();
			AtividadesServicoContratoService atividadeServicoService = (AtividadesServicoContratoService) ServiceLocator.getInstance().getService(AtividadesServicoContratoService.class, WebUtil.getUsuarioSistema(request));
			if (atividadesServicoContrato.getIdAtividadeServicoContrato() != null || atividadesServicoContrato.getIdAtividadeServicoContrato().intValue() != 0) {
				atividadesServicoContrato.setDeleted("y");
				atividadeServicoService.update(atividadesServicoContrato);
				document.alert(UtilI18N.internacionaliza(request, "MSG07"));
			}
			HTMLForm form = document.getForm("formInterno");
			form.clear();
			document.executeScript("closePopup(" + atividadesServicoContrato.getIdServicoContrato() + ");");
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	
	public Class<AtividadesServicoContratoDTO> getBeanClass() {
		return AtividadesServicoContratoDTO.class;
	}
	

}
