package br.com.centralit.citcorpore.ajaxForms;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.centralit.citcorpore.negocio.ComandoService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.service.ServiceLocator;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings("rawtypes")
public class Comando extends AjaxFormAction {

    public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

    }

  
	public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	ComandoDTO comandoDTO = (ComandoDTO) document.getBean();
    	ComandoService comandoService = (ComandoService) ServiceLocator.getInstance().getService(ComandoService.class, null);
    	
    	Collection comandoJaCadastrado = comandoService.find(comandoDTO);
    	if (comandoJaCadastrado != null && !comandoJaCadastrado.equals("") ) {
			document.alert(UtilI18N.internacionaliza(request, "MSE01") );
			return;
		} 
    	
    		if (comandoDTO.getId() == null || comandoDTO.getId().intValue() == 0) {
    		
    			if (comandoJaCadastrado != null && !comandoJaCadastrado.isEmpty() ) {
    				document.alert(UtilI18N.internacionaliza(request, "MSE01") );
    			} else {  
    				comandoService.create(comandoDTO);
            		document.alert(UtilI18N.internacionaliza(request, "MSG05") );
    			}        		
        	} else { 
        		
        		comandoService.update(comandoDTO);
        		document.alert(UtilI18N.internacionaliza(request, "MSG06") );
        	}

        	HTMLForm form = document.getForm("form");
        	form.clear();
    	  	
    }

    public void restore(DocumentHTML document, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
	ComandoDTO comando = (ComandoDTO) document.getBean();
	ComandoService comandoService = (ComandoService) ServiceLocator.getInstance().getService(
		ComandoService.class, null);

	comando = (ComandoDTO) comandoService.restore(comando);

	HTMLForm form = document.getForm("form");
	form.clear();
	form.setValues(comando);
    }

    public Class getBeanClass() {
	return ComandoDTO.class;
    }
}
