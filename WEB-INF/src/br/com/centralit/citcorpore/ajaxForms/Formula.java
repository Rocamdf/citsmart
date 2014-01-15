package br.com.centralit.citcorpore.ajaxForms;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.FormulaService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
@SuppressWarnings("rawtypes")
public class Formula extends AjaxFormAction {

    @Override
    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario == null) {
		document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
		return;
	}

	document.focusInFirstActivateField(null);	
    }

	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    FormulaDTO formulaDTO = (FormulaDTO) document.getBean();
		FormulaService formulaService = (FormulaService) ServiceLocator.getInstance().getService(FormulaService.class, null);

		
		if (!formulaService.verificarSeNomeExiste(formulaDTO)) {
			if (formulaDTO.getIdFormula() == null || formulaDTO.getIdFormula().intValue() == 0) {
				if(formulaService.findByIdentificador(formulaDTO.getIdentificador())!=null){
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.identificadorRegistroJaCadastrado"));
					return;
				}
			    formulaService.create(formulaDTO);
			    document.alert(UtilI18N.internacionaliza(request, "MSG05"));
			} else {
				//verifica se o identificar existe somente se for um novo registro
				if(!formulaService.existeRegistro(formulaDTO.getNome()) && formulaService.verificarSeIdentificadorExiste(formulaDTO)){
					document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.identificadorRegistroJaCadastrado"));
					return;
				}
			    formulaService.update(formulaDTO);
			    document.alert(UtilI18N.internacionaliza(request, "MSG06"));
			}
		} else {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.registroJaCadastrado"));
		}
		HTMLForm form = document.getForm("form");
		form.clear();
	}
	
	public void restore(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    FormulaDTO formulaDTO = (FormulaDTO) document.getBean();
	    FormulaService formulaService = (FormulaService) ServiceLocator.getInstance().getService(FormulaService.class, null);
		formulaDTO = (FormulaDTO) formulaService.restore(formulaDTO);
		HTMLForm form = document.getForm("form");
		form.clear();
		form.setValues(formulaDTO);
	}

	public void delete(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	    FormulaDTO formulaDTO = (FormulaDTO) document.getBean();
	    FormulaService formulaService = (FormulaService) ServiceLocator.getInstance().getService(FormulaService.class, null);

	    formulaService.delete(formulaDTO);
		HTMLForm form = document.getForm("form");
		form.clear();

		document.alert(UtilI18N.internacionaliza(request, "MSG07"));
	}	
	
   
	@Override
    public Class getBeanClass() {
	return FormulaDTO.class;
    }

}
