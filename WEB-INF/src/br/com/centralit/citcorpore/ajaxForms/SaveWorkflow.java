package br.com.centralit.citcorpore.ajaxForms;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.bean.SaveWorkflowDTO;

public class SaveWorkflow extends AjaxFormAction{
	private static boolean DEBUG = true;
	
	@Override
	public Class getBeanClass() {
		return SaveWorkflowDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ParserRequest parser = new ParserRequest();
		HashMap hashValores = parser.getFormFields(request);		
		if (DEBUG){
			debugValuesFromRequest(hashValores);
		}		
	}
	
	public void debugValuesFromRequest(HashMap hashValores){
		Set set = hashValores.entrySet(); 
		Iterator i = set.iterator(); 
		
		System.out.print("------- VALORES DO REQUEST: -------"); 
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]"); 
		}		
	}
}
