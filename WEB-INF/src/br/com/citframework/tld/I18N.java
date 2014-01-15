/*
 * Created on 21/03/2012
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.tld;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.centralit.citcorpore.negocio.DicionarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Mensagens;

/**
 * @author CLEISON FERREIRA DE MELO
 * 
 * 
 *         Window - Preferences - Java - Code Style - Code Templates
 */
public class I18N extends BodyTagSupport {
	private String key;
	private String locale = "";
	private static final long serialVersionUID = 1L;
	private static HashMap<String, String> mapDicPort = null;
	private static HashMap<String, String> mapDicIng = null;
	private static HashMap<String, String> mapDicEsp = null;
	private static InputStream inputStreamSettedInLoad = null;
	private static Properties props = null;

	@SuppressWarnings("unchecked")
	public int doStartTag() throws JspException {
		try {		
			Boolean chavaEncontrada = false; 			
			StringBuffer strBuff = new StringBuffer();	
			String pathInfo = getRequestedPath((HttpServletRequest) pageContext.getRequest());
			DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
			String fileName = "Mensagens";
			String sessaoLocale = "";
			String value = "";
			List<DicionarioDTO> lisResult = null;
			if (pageContext.getSession().getAttribute("locale") != null) {
				sessaoLocale = pageContext.getSession().getAttribute("locale").toString();	
			}else if (getLocale() != null){
				sessaoLocale = getLocale().toLowerCase();
			}
			if ( ( mapDicPort == null || props == null || mapDicIng == null || mapDicEsp == null) || (pathInfo.endsWith("dicionario.jsp")) || (pathInfo.endsWith("dicionario.load"))) {
				mapDicPort = new HashMap<String, String>();
				mapDicIng = new HashMap<String, String>();
				mapDicEsp = new HashMap<String, String>();
				props = new Properties();
				if(!sessaoLocale.trim().equalsIgnoreCase("")){
					fileName = fileName + "_" + sessaoLocale.toLowerCase().trim() + ".properties";
				}else{
					fileName = fileName + ".properties";
				}
				carregaMaps();					
				ClassLoader load = Mensagens.class.getClassLoader();
				InputStream is = load.getResourceAsStream(fileName);
				if (is == null) {
					is = ClassLoader.getSystemResourceAsStream(fileName);
				}
				if (is == null) {
					is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
				}
				if (is == null) {
					is = inputStreamSettedInLoad;
				}
				if (is != null) {
					props.load(is);
				}	
				
				if(!sessaoLocale.trim().equalsIgnoreCase("")){
					if (sessaoLocale.trim().equalsIgnoreCase("en") && mapDicIng.size() > 0){
						if(mapDicIng.containsKey(getKey())){
			            	value = mapDicIng.get(getKey());
			            	chavaEncontrada = true;
			            }else{
			            	value = getKey();
			            }					
					} else {
						if (sessaoLocale.trim().equalsIgnoreCase("es") && mapDicEsp.size() > 0){
							if(mapDicEsp.containsKey(getKey())){
				            	value = mapDicEsp.get(getKey());
				            	chavaEncontrada = true;
				            }else{
				            	value = getKey();
				            }					
						} else{
				            if(mapDicPort.containsKey(getKey())){
				            	value = mapDicPort.get(getKey());
				            	chavaEncontrada = true;
				            }else{
				            	value = getKey();
				            }
						}
					}
				}
	            if(!chavaEncontrada){
		            if(props.containsKey(getKey())){
		            	value = props.getProperty(getKey());
		            	chavaEncontrada = true;
		            }else{
		            	value = getKey();
		            }	
	            }				
			}else{
				if(!sessaoLocale.trim().equalsIgnoreCase("")){
					if (sessaoLocale.trim().equalsIgnoreCase("en")){
						if(mapDicIng.containsKey(getKey())){
			            	value = mapDicIng.get(getKey());
			            	chavaEncontrada = true;
			            }else{
			            	value = getKey();
			            }					
					} else {
						if (sessaoLocale.trim().equalsIgnoreCase("es")){
							if(mapDicEsp.containsKey(getKey())){
				            	value = mapDicEsp.get(getKey());
				            	chavaEncontrada = true;
				            }else{
				            	value = getKey();
				            }					
						} else{
				            if(mapDicPort.containsKey(getKey())){
				            	value = mapDicPort.get(getKey());
				            	chavaEncontrada = true;
				            }else{
				            	value = getKey();
				            }
						}
					}
				}
				
	            if(!chavaEncontrada){
					props = new Properties();
					if(!sessaoLocale.trim().equalsIgnoreCase("")){
						fileName = fileName + "_" + sessaoLocale.toLowerCase().trim() + ".properties";
					}else{
						fileName = fileName + ".properties";
					}
					ClassLoader load = Mensagens.class.getClassLoader();
					InputStream is = load.getResourceAsStream(fileName);
					if (is == null) {
						is = ClassLoader.getSystemResourceAsStream(fileName);
					}
					if (is == null) {
						is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
					}
					if (is == null) {
						is = inputStreamSettedInLoad;
					}
					if (is != null) {
						props.load(is);
					}	            	
		            if(props.containsKey(getKey())){
		            	value = props.getProperty(getKey());
		            	chavaEncontrada = true;
		            }else{
		            	value = getKey();
		            }
	            }	            
			}
			strBuff.append(value);
			pageContext.getOut().print(strBuff.toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Erro nas taglibs" + e);
		}
		return SKIP_BODY;		
	}
	
	public void carregaMaps() throws ServiceException, Exception{
		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
		List<DicionarioDTO> lisResult = null;
		lisResult = (List<DicionarioDTO>) dicionarioService.list();				
		if (lisResult != null) {
			for (DicionarioDTO dicionario : lisResult) {
				if(dicionario.getSigla().equalsIgnoreCase("en")){
					mapDicIng.put(dicionario.getNome().trim(), dicionario.getValor());
				}else{
					if(dicionario.getSigla().equalsIgnoreCase("es")){
						mapDicEsp.put(dicionario.getNome().trim(), dicionario.getValor());
					}else{
						mapDicPort.put(dicionario.getNome().trim(), dicionario.getValor());
					}					
				}
			}
		}		
	}
	
	private String getRequestedPath(HttpServletRequest request) {
		String path = request.getRequestURI();
		path = path.substring(request.getContextPath().length());
		int index = path.indexOf("?");
		if (index != -1)
			path = path.substring(0, index);
		return path;
	}	

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
