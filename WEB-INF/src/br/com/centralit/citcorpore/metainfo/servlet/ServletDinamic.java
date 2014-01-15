package br.com.centralit.citcorpore.metainfo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.citframework.util.UtilStrings;

public class ServletDinamic extends HttpServlet {
	private static final Logger LOGGER = Logger.getLogger(ServletDinamic.class);

	/**
	 * Processa as requisicoes.
	 * 
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
													 throws ServletException, IOException{
        String pathInfo = request.getRequestURI();
        String ext = "";
        
        try{
	        if (pathInfo!=null){
	        	//Executa um acao
	        	ext = getObjectExt(pathInfo);
	        	ext = ext.replaceAll("#", ""); //Evita problemas com href="#"
	        	
	        	//Operacoes de CRUD - Manipulacao de dados
	        	if ("extern".equalsIgnoreCase(ext)){
	        		String className = request.getParameter("className");
	        		
	        		//String jarPath = (String)CITCorporeUtil.hsmExternalClasses.get(className);
	        		
	        		//ClassLoader classLoader = new URLClassLoader(new URL[] {new URL("file:" + jarPath)}, null);  
	        		
	        		className = className.replaceAll(".class", "");
	        		
	        		Class classe = this.getClass().forName(className) ; //, true, this.getClass().getClassLoader());
	        		//Class classe = this.getClass().forName(className, true, classLoader);
	        		//Class classe = classLoader.loadClass(className);
	        		Object objeto = classe.newInstance();
	        		
	        		Method mtd = CitAjaxReflexao.findMethod("execute", objeto);
	        		Object parmReals[] = new Object[2];
	        		
	        		parmReals[0] = request;
	        		parmReals[1] = response;
	        		
	        		HashMap map = getValuesFromRequest(request);
	        		debugValuesFromRequest(map);
	        		
	        		Object retorno = mtd.invoke(objeto, parmReals);
	        		return;
	        	}
	        }
        }catch (Exception e) {
			PrintWriter out = null;
			try{
				out = response.getWriter();
			}catch (Exception eX) {
			}        	
        	e.printStackTrace(out);
        	
        	LOGGER.error(e);
        	e.printStackTrace();
        	
			//response.setContentType("text/html");
			response.setContentType("text/html; charset=UTF-8");	
		}
	}
	/**
	 * Metodo doGet
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
											throws ServletException, IOException{
  		processRequest(request, response);
	}

	/**
	 * Metodo doPost
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
											throws ServletException, IOException {
		processRequest(request, response);
	}
	public String getObjectName(String path){
		String strResult = "";
		boolean b = false;
		for(int i = path.length() - 1; i >= 0; i--){
			if (b){
				if (path.charAt(i) == '/'){
					return strResult;
				}else{
					strResult = path.charAt(i) + strResult; 
				}
			}else{
				if (path.charAt(i) == '.'){
					b = true;
				}
			}
		}
		return strResult;
	}	
	public String getObjectExt(String path){
		String strResult = "";
		for(int i = path.length() - 1; i >= 0; i--){
			if (path.charAt(i) == '.'){
				return strResult;
			}else{
				strResult = path.charAt(i) + strResult; 
			}
		}
		return strResult;
	}
	public HashMap getValuesFromRequest(HttpServletRequest req){
		Enumeration en = req.getParameterNames();
		String[] strValores;
		HashMap formFields = new HashMap();
		while(en.hasMoreElements()) {
			String nomeCampo  = (String)en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			if (strValores.length == 0){
				formFields.put(nomeCampo.toUpperCase(),UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
			} else {
				if (strValores.length == 1){
					formFields.put(nomeCampo.toUpperCase(),UtilStrings.decodeCaracteresEspeciais(strValores[0]));
				}else{
					formFields.put(nomeCampo.toUpperCase(),strValores);
				}
			}
		}
		return formFields;
	}
	public void debugValuesFromRequest(HashMap hashValores){
		Set set = hashValores.entrySet(); 
		Iterator i = set.iterator(); 
		
		System.out.print("------- ServletDinamic ------ VALORES DO REQUEST: -------"); 
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print("-------------> [" + me.getKey() + "]: [" + me.getValue() + "]"); 
		}		
	}	
}
