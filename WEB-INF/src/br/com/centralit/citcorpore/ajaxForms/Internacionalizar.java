package br.com.centralit.citcorpore.ajaxForms;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.InternacionalizarDTO;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.util.XmlReadLookup;

@SuppressWarnings("rawtypes")
public class Internacionalizar extends AjaxFormAction {

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

	}
	
	public void internacionaliza(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		InternacionalizarDTO bean = (InternacionalizarDTO) document.getBean();
		
		String IDIOMAPADRAO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.IDIOMAPADRAO," ");
		
		if(IDIOMAPADRAO == null){
			IDIOMAPADRAO= "";
		}
		
		request.getSession(true).setAttribute("menu", null);
		request.getSession(true).setAttribute("menuPadrao", null);
		UtilI18N.resetar();		
		
		if(bean != null){
			
			if(bean.getLocale() != null){
				WebUtil.setLocale(bean.getLocale().trim(), request);
				XmlReadLookup.getInstance(new Locale(bean.getLocale().trim()));
			}else{
				WebUtil.setLocale(IDIOMAPADRAO, request);
				XmlReadLookup.getInstance(new Locale(IDIOMAPADRAO));
			}
		}else{
			WebUtil.setLocale(IDIOMAPADRAO, request);
			XmlReadLookup.getInstance(new Locale(IDIOMAPADRAO));
		}
		document.executeScript("window.location.reload()");
	}

	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return InternacionalizarDTO.class;
	}
}
