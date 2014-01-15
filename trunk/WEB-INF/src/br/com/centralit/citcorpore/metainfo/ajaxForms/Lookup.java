package br.com.centralit.citcorpore.metainfo.ajaxForms;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.framework.ParserRequest;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.centralit.citcorpore.metainfo.negocio.LookupService;
import br.com.citframework.service.ServiceLocator;

import com.google.gson.Gson;

public class Lookup extends AjaxFormAction {
	private static boolean DEBUG = true;
	
	@Override
	public Class getBeanClass() {
		return LookupDTO.class;
	}

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ParserRequest parser = new ParserRequest();
		HashMap hashValores = parser.getFormFields(request);
		if (DEBUG){
			debugValuesFromRequest(hashValores);
		}
		
		LookupService lookupService = (LookupService) ServiceLocator.getInstance().getService(LookupService.class, null);
		
		LookupDTO lookupDto = (LookupDTO)document.getBean();
		lookupDto.setTermoPesquisa(lookupDto.getQ());
		String retorno = lookupService.findSimpleString(lookupDto);
		//lookupDto.setValues(colRetorno);
		
		/*
		LookupDTO lookupDto = new LookupDTO();
		ReturnLookupDTO returnLookupDTO = new ReturnLookupDTO();
		returnLookupDTO.setLabel("EMAURI");
		returnLookupDTO.setValue("1");
		lookupDto.getValues().add(returnLookupDTO);
		
		returnLookupDTO = new ReturnLookupDTO();
		returnLookupDTO.setLabel("EMAURI NETO");
		returnLookupDTO.setValue("2");
		lookupDto.getValues().add(returnLookupDTO);
		
		returnLookupDTO = new ReturnLookupDTO();
		returnLookupDTO.setLabel("YASMIN");
		returnLookupDTO.setValue("3");		
		lookupDto.getValues().add(returnLookupDTO);

		returnLookupDTO = new ReturnLookupDTO();
		returnLookupDTO.setLabel("MARINEIDE");
		returnLookupDTO.setValue("4");	
		lookupDto.getValues().add(returnLookupDTO);
		*/

		//Gson gson = new Gson();
		
		//String json = gson.toJson(lookupDto);
		
		request.setAttribute("json_response", "[" + retorno + "]");		
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
