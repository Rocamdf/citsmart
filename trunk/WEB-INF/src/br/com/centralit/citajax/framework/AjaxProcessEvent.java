package br.com.centralit.citajax.framework;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.centralit.citajax.util.Constantes;

public class AjaxProcessEvent extends AjaxFacade {
	public Collection execute(String formNameParm, String name, String evento) throws Exception{
		Class classe = null;
		boolean bTentarLocalizarForm = true;
		String formName = formNameParm;
		if (formName == null){
			formName = "";
		}
		formName = formName.replaceAll("\\.load", "");
		int iCodigoTentativa = 1;
		while(bTentarLocalizarForm){
			try {
				//System.out.println("Form action: " + Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(name, "U"));
				if (iCodigoTentativa == 1){
					classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM") + "." + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
				}else{
					if (Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) == null || Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa).trim().equalsIgnoreCase("")){
						classe = null;
						bTentarLocalizarForm = false;
						break;
					}
					classe = Class.forName(Constantes.getValue("BEAN_LOCATION_FORM" + iCodigoTentativa) + "." + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));					
				}
				if (classe != null){
					bTentarLocalizarForm = true;
					break;
				}				
			} catch (ClassNotFoundException e) {
				iCodigoTentativa++;
				//throw new Exception("Form não encontrado: " + Util.convertePrimeiraLetra(name, "U"));
			} catch (StringIndexOutOfBoundsException e) {
				iCodigoTentativa++;
			}
		}
		if (classe == null){
			System.out.println("Form não encontrado: " + CitAjaxUtil.convertePrimeiraLetra(formName, "U"));
			return null;
		}
		Object objeto = classe.newInstance();

		HttpServletRequest request = this.getRequest();
		HttpServletResponse response = this.getResponse();		
		
		//Pega a classe Bean associada ao FormAction
		Method mtdGetBeanClass = CitAjaxReflexao.findMethod("getBeanClass", objeto);
		Object objClassBean = mtdGetBeanClass.invoke(objeto, null );
		//Instancia o Bean
		Object objBean = ((Class)objClassBean).newInstance();
		//Passa os valores do request para o bean
		ParserRequest parser = new ParserRequest();
		HashMap hashValores = parser.getFormFields(request);
		parser.converteValoresRequestToBean(hashValores, objBean);
		
		//Cria o document e associa o bean.
		DocumentHTML document = new DocumentHTML();
		document.setBean(objBean);
		document.setValuesForm(hashValores);
		
		document.setIgnoreNextMethod(false);
		
		String eventoOrig = evento;
		Method mtd = null;
		mtd = CitAjaxReflexao.findMethod(evento, objeto);
		if (mtd == null){
			evento = evento.toLowerCase();
			mtd = CitAjaxReflexao.findMethod(name + "_on" + evento, objeto);
		}
		
		//CITScriptProcess citScriptProcess = new CITScriptProcess();
		eventoOrig = CitAjaxUtil.convertePrimeiraLetra(eventoOrig, "U");
		//citScriptProcess.processScript("before" + eventoOrig, objeto, objBean, document, request, response);			
		
		//if (!document.isIgnoreNextMethod()){
			if (mtd != null){			
				mtd.invoke(objeto, new Object[] {document, request, response} );
				return document.getAllScripts();
			}else{
				mtd = CitAjaxReflexao.findMethod(name + "_on" + CitAjaxUtil.convertePrimeiraLetra(evento,"U"), objeto);
				if (mtd != null){
					mtd.invoke(objeto, new Object[] {document, request, response} );
					return document.getAllScripts();				
				}else{
					//citScriptProcess.processScript(CitAjaxUtil.convertePrimeiraLetra(eventoOrig,"L"), objeto, objBean, document, request, response);
				}
			}
		//}
		
		//citScriptProcess.processScript("after" + eventoOrig, objeto, objBean, document, request, response);
		
		return null;
	}

}
