package br.com.centralit.citcorpore.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;

import br.com.centralit.citcorpore.bean.DicionarioDTO;
import br.com.centralit.citcorpore.negocio.DicionarioService;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Mensagens;

/**
 * @author CentralIT
 */

@SuppressWarnings( "unused")
public class UtilI18N implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Properties props = null;
	private static HashMap<String, String> mapDicPort = null;
	private static HashMap<String, String> mapDicIng = null;
	private static HashMap<String, String> mapDicEsp = null; 
	private static InputStream inputStreamSettedInLoad = null;
	private static StringBuffer strBuff = new StringBuffer();
	private static String fileName = "";
	private static String locale = null;

	/**
	 * @param request
	 *            É necessario para idenficar o locale na sessão
	 * @param key
	 *            Parametro para identificar arquivo no arquivo properties
	 * @return String da mensagem.
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public static String internacionaliza(HttpServletRequest request, String keyParm) {

		String str = "";
		if (keyParm == null){
			return null;
		}
		String key = new String(keyParm);
		key = key.replaceAll("\\$", "");
		Boolean chaveEncontrada = false;

		try {
			HttpSession session = ((HttpServletRequest) request).getSession(true);
	    	if(session.getAttribute("locale") != null)
	    		locale = session.getAttribute("locale").toString();
	    	else
	    		locale = "";
	    	
	    	getProperties(new Locale(locale));
	    	
	    	if ( mapDicPort == null || props == null || mapDicIng == null || mapDicEsp == null ) {
	    		mapDicPort = new HashMap<String, String>();
				mapDicIng = new HashMap<String, String>();
				mapDicEsp = new HashMap<String, String>();
				
				getProperties(new Locale(locale));
				getMapas(new Locale(locale));
				
				if(!locale.trim().equalsIgnoreCase("")) {
					if (locale.trim().equalsIgnoreCase("en")){
						if(mapDicIng.containsKey(key)){
			            	str =   mapDicIng.get(key);
			            	chaveEncontrada = true;
			            } else {
		            		str =  key;
			            }
					} else {
						if (locale.trim().equalsIgnoreCase("es")){
							if(mapDicEsp.containsKey(key)){
				            	str =   mapDicEsp.get(key);
				            	chaveEncontrada = true;
				            } else {
			            		str =  key;
				            }
						} else {
				            if(mapDicPort.containsKey(key)){
				            	str =  mapDicPort.get(key);
				            	chaveEncontrada = true;
				            } else 
				            	str =  key;
						}
					}
				}
				if(!chaveEncontrada){
		            if(props.containsKey(key)){
		            	str = props.getProperty(key);
		            	chaveEncontrada = true;
		            }else
		            	str = key;
	            }		
			}else {
				/**
				 * Comentado pois quando resetado os maps o mesmo usava o prop estático da ultima sessão
				 * Usando 
				 * Autor: flavio.santana
				 * Data/Hora: 26/11/2013
				 */
				//if(!locale.equals("")) {
					if (locale.trim().equalsIgnoreCase("en")){
						if(mapDicIng.containsKey(key)){
			            	str =   mapDicIng.get(key);
			            	chaveEncontrada = true;
			            } else {
		            		str =  key;
			            }
					} else {
						if (locale.trim().equalsIgnoreCase("es")){
							if(mapDicEsp.containsKey(key)){
				            	str =   mapDicEsp.get(key);
				            	chaveEncontrada = true;
				            } else {
			            		str =  key;
				            }
						} else {
				            if(mapDicPort.containsKey(key)){
				            	str =  mapDicPort.get(key);
				            	chaveEncontrada = true;
				            } else 
				            	str =  key;
						}
					}
				//}
			}

	    	if(!chaveEncontrada){
	    		str = props.getProperty(key.trim());
            }		
			if (str != null) {
				str = str.replaceAll("\n", "\\n\\r");
			}

		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		if (str == null || str.trim().equalsIgnoreCase("")){
			str = keyParm; //Caso nao encontre a chave nem no dicionario quanto no .properties, entao mostra a key para que a pessoa possa ir na tela de dicionario e adicionar. Emauri em 28/11/2012.
		}
		return str;
	}

	/**
	 * @param locale
	 *            identificador do pais ex: en (Inglês)
	 * @param Key
	 *            parametro de busca arquivo properties
	 * @return String da mensagem.
	 * @throws Exception 
	 * @throws ServiceException 
	 */
	public static String internacionaliza(String locale_, String key)   {
		String value = null;		
		try {
			if ( mapDicPort == null || props == null || mapDicIng == null || mapDicEsp == null ) {
				mapDicPort = new HashMap<String, String>();
				mapDicIng = new HashMap<String, String>();
				mapDicEsp = new HashMap<String, String>();
				
				getProperties(new Locale(locale_));
				getMapas(new Locale(locale_));
			}
			Boolean chaveEncontrada = false;	

			if(!locale_.trim().equalsIgnoreCase("")) {
				if (locale.trim().equalsIgnoreCase("en")){
					if(mapDicIng.containsKey(key)){
						value =   mapDicIng.get(key);
		            	chaveEncontrada = true;
		            } else {
		            	value =  key;
		            }
				} else {
					if (locale_.trim().equalsIgnoreCase("es")){
						if(mapDicEsp.containsKey(key)){
							value =   mapDicEsp.get(key);
			            	chaveEncontrada = true;
			            } else {
			            	value =  key;
			            }
					} else {
			            if(mapDicPort.containsKey(key)){
			            	value =  mapDicPort.get(key);
			            	chaveEncontrada = true;
			            } else 
			            	value =  key;
					}
				}
			}
			
			if(!chaveEncontrada){
				value = props.getProperty(key.trim());
            }		
			if (value != null) {
				value = value.replaceAll("\n", "");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return value;
	}

	public static String getJsonMensagens(HttpServletRequest request) {
		try {
			HttpSession session = ((HttpServletRequest) request).getSession();
	    	if(session.getAttribute("locale") != null) {
	    		locale = (String) session.getAttribute("locale");
    			getProperties(new Locale(locale));
	    	} else
	    		locale = "";
	    	
	    	if ( mapDicPort == null || props == null || mapDicIng == null || mapDicEsp == null ) {
	    		mapDicPort = new HashMap<String, String>();
				mapDicIng = new HashMap<String, String>();
				mapDicEsp = new HashMap<String, String>();
				
				getProperties(new Locale(locale));
				getMapas(new Locale(locale));
	    	}

		} catch (SecurityException e1) {
			e1.printStackTrace();
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String msg = "";
		boolean isTrue = false;
		StringBuilder json = new StringBuilder();
		json.append(" key : {");
		if (locale.trim().equalsIgnoreCase("en") && mapDicIng.size() > 0)
		{
			for (String key : new HashSet<String>(mapDicIng.keySet())) {
				 boolean flag = false;
					if (mapDicIng.get(key.trim()) != null && !StringUtils.isEmpty(mapDicIng.get(key.trim()))) {
						
						if (StringUtils.contains(mapDicIng.get(key.trim()), "\"")) {
							msg = StringUtils.replace(mapDicIng.get(key.trim()), "\"", "\\\"");
							flag = true;
						} else {
							msg = mapDicIng.get(key.trim());
						}					
						
						if (StringUtils.contains(msg, "'")) {
							msg = StringUtils.replace(msg, "'", "");
							flag = true;
						}
						
						if(!flag) {
							if (StringUtils.contains(mapDicIng.get(key.trim()), "\\")) {
								msg = StringUtils.replace(mapDicIng.get(key.trim()), "\\", "");
							}
						}
					} else {
						msg = mapDicIng.get(key.trim());
					}
	            
					if(StringUtils.contains(msg, "\n")) {
						msg = StringUtils.replace(msg, "\n", "\\n\\r");
					}
					
				json.append("'" + key + "' : '" + msg + "',");
			}
			isTrue = true;
		} else  if (locale.trim().equalsIgnoreCase("es") && mapDicEsp.size() > 0)
		{
			for (String key : new HashSet<String>(mapDicEsp.keySet())) {
				 boolean flag = false;
					if (mapDicEsp.get(key.trim()) != null && !StringUtils.isEmpty(mapDicEsp.get(key.trim()))) {
						
						if (StringUtils.contains(mapDicEsp.get(key.trim()), "\"")) {
							msg = StringUtils.replace(mapDicEsp.get(key.trim()), "\"", "\\\"");
							flag = true;
						} else {
							msg = mapDicEsp.get(key.trim());
						}					
						
						if (StringUtils.contains(msg, "'")) {
							msg = StringUtils.replace(msg, "'", "");
							flag = true;
						}
						
						if(!flag) {
							if (StringUtils.contains(mapDicEsp.get(key.trim()), "\\")) {
								msg = StringUtils.replace(mapDicEsp.get(key.trim()), "\\", "");
							}
						}
					} else {
						msg = mapDicEsp.get(key.trim());
					}
	            
					if(StringUtils.contains(msg, "\n")) {
						msg = StringUtils.replace(msg, "\n", "\\n\\r");
					}
					
				json.append("'" + key + "' : '" + msg + "',");
			}
			isTrue = true;
		} else if ((locale.trim().equals("") || locale.trim().equalsIgnoreCase("pt_br")) && mapDicPort.size() > 0)
		{
			Set<String> keys =  mapDicPort.keySet();
			for (String key : keys) {
	            boolean flag = false;
				if (mapDicPort.get(key.trim()) != null && !StringUtils.isEmpty(mapDicPort.get(key.trim()))) {
					
					if (StringUtils.contains(mapDicPort.get(key.trim()), "\"")) {
						msg = StringUtils.replace(mapDicPort.get(key.trim()), "\"", "\\\"");
						flag = true;
					} else {
						msg = mapDicPort.get(key.trim());
					}					
					
					if (StringUtils.contains(msg, "'")) {
						msg = StringUtils.replace(msg, "'", "");
						flag = true;
					}
					
					if(!flag) {
						if (StringUtils.contains(mapDicPort.get(key.trim()), "\\")) {
							msg = StringUtils.replace(mapDicPort.get(key.trim()), "\\", "");
						}
					}
				} else {
					msg = mapDicPort.get(key.trim());
				}
				
				if(StringUtils.contains(msg, "\n")) {
					msg = StringUtils.replace(msg, "\n", "\\n\\r");
				}
	          
				json.append("'" + key + "' : '" + msg + "',");
			}
			isTrue = true;
		} 
		if(!isTrue) {
			Set<String> keys =  props.stringPropertyNames();
	
			for (String key : keys) {
				boolean flag = false;
				if (props.getProperty(key.trim()) != null && !StringUtils.isEmpty(props.getProperty(key.trim()))) {

					if (StringUtils.contains(props.getProperty(key.trim()), "\"")) {
						msg = StringUtils.replace(props.getProperty(key.trim()), "\"", "\\\"");
						flag = true;
					} else {
						msg = props.getProperty(key.trim());
					}
	
					if (StringUtils.contains(msg, "'")) {
						msg = StringUtils.replace(msg, "'", "");
						flag = true;
					}
					
					if(!flag) {
						if (StringUtils.contains(mapDicPort.get(key.trim()), "\\")) {
							msg = StringUtils.replace(mapDicPort.get(key.trim()), "\\", "");
						}
					}
					
				} else {
					msg = props.getProperty(key.trim());
				}
				
				if(StringUtils.contains(msg, "\n")) {
					msg = StringUtils.replace(msg, "\n", "\\n\\r");
				}
	
				json.append("'" + key + "' : '" + msg + "',");
			}
		}

		int i = json.lastIndexOf(",");
		json.replace(i, i + 1, "");

		json.append("}");
		return StringEscapeUtils.escapeJava(json.toString());
	}
	public static String internacionalizaString(String strParm, HttpServletRequest request)  {
		if (strParm == null){
			return "";
		}
		String retorno = "";
		String strTrata = new String(strParm);
		boolean continua = true;
		while (continua){
			int indice = strTrata.indexOf("$");
			if (strTrata.trim().equals("")){
				continua = false;
			}
			if (indice > -1 && !strTrata.trim().equals("")){
				if (indice > 0){
					retorno = retorno + strTrata.substring(0, indice);
				}
				String palavra = pegaAteFinalToken(strTrata.substring(indice));
				retorno = retorno + UtilI18N.internacionaliza(request, palavra) + "";
				strTrata = strTrata.substring(indice + palavra.length());
			}else{
				continua = false;
				retorno = retorno + strTrata;
			}
		}
		return retorno.trim();
	}
	public static String pegaAteFinalToken(String strParm){
		if (strParm == null){
			return "";
		}
		String palavraFormada = "";
		for (int i = 0; i < strParm.length(); i++){
			if (strParm.charAt(i) != ' ' && strParm.charAt(i) != ':' && strParm.charAt(i) != '<' && strParm.charAt(i) != '\n' 
					&& strParm.charAt(i) != '\r' && strParm.charAt(i) != '\t' && strParm.charAt(i) != '\"' && strParm.charAt(i) != '\''
					&& strParm.charAt(i) != '[' && strParm.charAt(i) != ']'){
				palavraFormada += strParm.charAt(i);
			}else{
				break;
			}
		}
		return palavraFormada;
	}
	
	private static Properties getProperties(Locale locale) {
		try {
			if (locale != null && ! locale.toString().trim().equals("")) 
				fileName = "Mensagens_" + locale.toString() + ".properties";
			 else 
				fileName = "Mensagens.properties";

			props = new Properties();
			ClassLoader load = Mensagens.class.getClassLoader();
			InputStream is = load.getResourceAsStream(fileName);
			if (is == null) 
				is = ClassLoader.getSystemResourceAsStream(fileName);
			if (is == null) 
				is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
			
			try {
				if (is != null) {
					props.load(is);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return props;
	}
	
	@SuppressWarnings("unchecked")
	private static void  getMapas(Locale locale) throws ServiceException, Exception {
		DicionarioService dicionarioService = (DicionarioService) ServiceLocator.getInstance().getService(DicionarioService.class, null);
		List<DicionarioDTO> lisResult = null;
		lisResult = (List<DicionarioDTO>) dicionarioService.list();				
		if (lisResult != null) {
			for (DicionarioDTO dicionario : lisResult) {
				if(dicionario.getSigla().equalsIgnoreCase("en")){
					mapDicIng.put(dicionario.getNome().trim(), dicionario.getValor());
				}else if(dicionario.getSigla().equalsIgnoreCase("es")){
					mapDicEsp.put(dicionario.getNome().trim(), dicionario.getValor());
				}else if (dicionario.getSigla().equalsIgnoreCase("pt")){
					mapDicPort.put(dicionario.getNome().trim(), dicionario.getValor());
				}
			}
		}		
	}
	/**
	 * Reseta da Memória o Dicionário de Dados 
	 * 
	 */
	public static void resetar() {
		 mapDicPort = null; props = null; mapDicIng = null; mapDicEsp = null;
	}

}