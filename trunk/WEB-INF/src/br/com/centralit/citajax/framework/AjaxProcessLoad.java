package br.com.centralit.citajax.framework;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.centralit.citajax.util.Constantes;

public class AjaxProcessLoad extends AjaxFacade {
	public Collection execute(String name) throws Exception{
		Class classe = null;
		boolean bTentarLocalizarForm = true;
		int iCodigoTentativa = 1;
		while(bTentarLocalizarForm){
			try {
				//System.out.println("Form action: " + Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
				if (iCodigoTentativa == 1){
					classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
				}else{
					if (Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) == null || Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa).trim().equalsIgnoreCase("")){
						classe = null;
						bTentarLocalizarForm = false;
						break;
					}
					classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
				}
				if (classe != null){
					bTentarLocalizarForm = true;
					break;
				}				
			} catch (ClassNotFoundException e) {
				iCodigoTentativa++;
				//throw new Exception("Form não encontrado: " + Util.convertePrimeiraLetra(name, "U"));
			}
		}
		if (classe == null){
			System.out.println("Form não encontrado: " + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
			return null;
		}
		Object objeto = classe.newInstance();
		
		//Identifica os metodos a serem tratados, existentes no form.
		Collection col1 = CitAjaxReflexao.findMethodByPalavra("onclick", objeto);
		Collection col2 = CitAjaxReflexao.findMethodByPalavra("onClick", objeto);
		Collection col3 = CitAjaxReflexao.findMethodByPalavra("onchange", objeto);
		Collection col4 = CitAjaxReflexao.findMethodByPalavra("onChange", objeto);
		Collection col5 = CitAjaxReflexao.findMethodByPalavra("onblur", objeto);
		Collection col6 = CitAjaxReflexao.findMethodByPalavra("onBlur", objeto);
		
		Collection colMetodosTratar = new ArrayList();
		colMetodosTratar.addAll(col1);
		colMetodosTratar.addAll(col2);
		colMetodosTratar.addAll(col3);
		colMetodosTratar.addAll(col4);
		colMetodosTratar.addAll(col5);
		colMetodosTratar.addAll(col6);
		
		DocumentHTML document = new DocumentHTML();
		document.setMetodosTratamentoByMethods(colMetodosTratar);
		
		//Verifica se existe o metodo load, caso exista entao executa.
		Method mtd = CitAjaxReflexao.findMethod("load", objeto);
		
		//Passa os valores do request para o bean
		//Pega a classe Bean associada ao FormAction
		Method mtdGetBeanClass = CitAjaxReflexao.findMethod("getBeanClass", objeto);
		Object objClassBean = mtdGetBeanClass.invoke(objeto, null );
		//Instancia o Bean
		Object objBean = ((Class)objClassBean).newInstance();			
		ParserRequest parser = new ParserRequest();
		HashMap hashValores = parser.getFormFields(request);
		parser.converteValoresRequestToBean(hashValores, objBean);			
		
		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();
		document.setBean(objBean);
		document.setValuesForm(hashValores);		
		
		document.setIgnoreNextMethod(false);
		/*
		CITScriptProcess citScriptProcess = new CITScriptProcess(); 
		citScriptProcess.processScript("beforeLoad", objeto, objBean, document, request, response);	
		*/
		//if (!document.isIgnoreNextMethod()){
			if (mtd != null){ //Pode ser que o form nao tenha o metodo load.
				mtd.invoke(objeto, new Object[] {document, request, response} );
			//}else{
			//	citScriptProcess.processScript("load", objeto, objBean, document, request, response);
			}
		//}
		/*
		citScriptProcess.processScript("afterLoad", objeto, objBean, document, request, response);
		*/
		return document.getAllScripts();
	}
}
