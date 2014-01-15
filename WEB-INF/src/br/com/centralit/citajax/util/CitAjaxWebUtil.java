package br.com.centralit.citajax.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilFormatacao;

public class CitAjaxWebUtil {
	/**
	 * Deserializa um objeto obtendo os valores do request
	 * @param classe
	 * @param name
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static Collection deserializeCollectionFromRequest(Class classe, String name, HttpServletRequest req) throws Exception{
		String strParser = (String)req.getParameter(name);
		if (strParser == null) return null;
		
		return deserializeCollectionFromString(classe, strParser);
	}
	/**
	 * Deserializa uma colecao de objetos atraves do valor passado como parametro.
	 * @param classe
	 * @param valor
	 * @return
	 * @throws Exception
	 */
	public static Collection deserializeCollectionFromString(Class classe, String valor) throws Exception{
		Collection col = new ArrayList();
		String[] strArray = separaObjetos(valor, '\3'); //Esta string representa a colecao de objetos serializados
		if (strArray == null) return null;
		for(int j = 0; j < strArray.length; j++){
			Object obj = deserializeObject(classe, strArray[j]);
			if (obj != null){
				col.add(obj);
			}
		}
		return col;		
	}
	/**
	 * Recebe a classe que deve ser deserializada e a string contendo o objeto serializado
	 * 			Exemplo: deserializeObject(Lotacao.class, "idFuncao\47\6idCargo\49\6....");
	 * 				Onde isso representa: idFuncao=7;idCargo=9;
	 * @param classe
	 * @param value
	 * @return
	 * @throws Exception
	 */
	public static Object deserializeObject(Class classe, String value) throws Exception{
		if (value == null) return null;
		String[] str = separaObjetos(value, '\6'); //Quebra os atributos
		Object obj = null;
		try {
			obj = classe.newInstance();
		} catch (InstantiationException e1) {
			throw new Exception("Erro ao instanciar a classe (1)!");
		} catch (IllegalAccessException e1) {
			throw new Exception("Erro ao instanciar a classe (2)!");
		}			
		//Faz o tratamento dos pares propriedade=valor
		String[] propriedadesValores;
		String aux;
		if (str != null){
			for(int i = 0; i < str.length; i++){
				propriedadesValores = separaByToken(str[i], '\4'); 
				try {
					aux = decodificaAspasApostrofe(decodificaEnter(propriedadesValores[1]));
					CitAjaxReflexao.setPropertyValueFromString(obj, aux, propriedadesValores[0]);
				} catch (Exception e) {
					e.printStackTrace();
					//Deixa passar... fica sem setar este valor
				}
			}
		}
		return obj;
	}
	public static String[] separaObjetos(String str, char token){
		Collection col = new ArrayList();
		String obj = null;
		boolean bIniciou = false;
		int qtdeChaveAberta = 0;
		for(int i = 0; i < str.length(); i++){
			if (str.charAt(i)==token && qtdeChaveAberta == 0){
				if (obj != null){
					col.add(obj);
				}
				obj = new String("");
			}else{
				if (str.charAt(i)=='\5'){
					qtdeChaveAberta--;
				}				
				if (bIniciou){
					if (obj != null && qtdeChaveAberta > 0){
						obj += str.charAt(i);
					}
				}				
				if (str.charAt(i)=='\2'){
					bIniciou = true;
					qtdeChaveAberta++;
				}					
			}
		}	
		if (obj != null){
			col.add(obj);
		}
		String[] ret = null;
		if (col.size()>0){
			ret = new String[col.size()];
		}
		int i = 0;
		for(Iterator it = col.iterator(); it.hasNext();i++){
			ret[i] = (String)it.next();
		}
		return ret;		
	}
	/**
	 * Esta funcao quebra os tokens de objetos.
	 * 	Ele deve ser usada no lugar do Split pois podem existir objetos dentro de objetos.
	 * @param str
	 * @param token
	 * @return
	 */
	public static String[] separaByToken(String str, char token){
		String propriedade = "";
		String valor = "";
		boolean bProp = true;
		boolean bIniciou = false;
		int qtdeChaveAberta = 0;
		for(int i = 0; i < str.length(); i++){
			if (str.charAt(i)==token){
				bProp = false;
			}
			if (bProp){
				propriedade += str.charAt(i);
			}else{
				if (str.charAt(i)=='\5'){
					qtdeChaveAberta--;
					if (qtdeChaveAberta == 0){
						break;
					}
				}				
				if (bIniciou && qtdeChaveAberta > 0){
					valor += str.charAt(i);
				}				
				if (str.charAt(i)=='\2'){
					bIniciou = true;
					qtdeChaveAberta++;
				}
			}
		}
		String[] strRetorno = new String[] {propriedade, valor};
		return strRetorno;
	}
	/**
	 * Este metodo foi mantido para suportar utilizacoes anteriores.
	 * 	Recebe valores separados por =  e propriedades separadas por ;
	 * @param classe
	 * @param name
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public static Collection getValuesCollectionRequest(Class classe, String name, HttpServletRequest req) throws Exception{
		Collection col = new ArrayList();
		String[] strParser = (String[])req.getParameterValues(name);
		
		for(int j = 0; j < strParser.length; j++){
			String[] str = strParser[j].split(";");
			String[] propriedadesValores;
			
			Object obj;
			try {
				obj = classe.newInstance();
			} catch (InstantiationException e1) {
				throw new Exception("Erro ao instanciar a classe (1)!");
			} catch (IllegalAccessException e1) {
				throw new Exception("Erro ao instanciar a classe (2)!");
			}			
			//Faz o tratamento dos pares propriedade=valor
			for(int i = 0; i < str.length; i++){
				propriedadesValores = str[i].split("=");
				try {
					CitAjaxReflexao.setPropertyValueFromString(obj, propriedadesValores[1], propriedadesValores[0]);
				} catch (Exception e) {
					e.printStackTrace();
					//Deixa passar... fica sem setar este valor
				}
			}
			col.add(obj);
		}
		return col;
	}
	/**
	 * Serializa um objeto.
	 * @throws Exception 
	 */
	public static String serializeObjects(Collection col, boolean naoCodificaApostrofeAspas) throws Exception{
		if (col == null) return "";
		String result = "";
		Object obj;
		for(Iterator it = col.iterator(); it.hasNext();){
			obj = it.next();
			result = result + "\3\2";
			result = result + serializeObject(obj, naoCodificaApostrofeAspas);
			result = result + "\5";			
		}
		return result;
	}
	/*
	 * Serializa um objeto em string para envio ao servidor.
	 */
	public static String serializeObject(Object objeto, boolean naoCodificaApostrofeAspas) throws Exception{
	  if (objeto == null) return "";
	  String strResult = "";
	  String propriedade;
	  Object value;
	  String valueStr;
	  List lstGets = CitAjaxReflexao.findGets(objeto);
	  for (int i = 0; i < lstGets.size(); i++) {
		  propriedade = (String) lstGets.get(i);
		  if (!propriedade.equalsIgnoreCase("class")){
			  value = Reflexao.getPropertyValue(objeto, propriedade);
			  if (value != null){
				  valueStr = "";
				  if (Date.class.isInstance(value)) {
					  valueStr = UtilDatas.dateToSTR((Date) value);
				  }else if (java.util.Date.class.isInstance(value)) {
					  valueStr = UtilDatas.dateToSTR((java.util.Date) value);
				  }else if (Double.class.isInstance(value)) {
					  Integer qtdeCasasDec = new Integer(2);
					  Method m = Reflexao.findMethod(propriedade + "_casasDecimais", objeto);
					  if (m != null){
						  qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
					  }
					  valueStr = UtilFormatacao.formatDouble((Double) value, qtdeCasasDec.intValue());
				  }else if (BigDecimal.class.isInstance(value)) {
					  Integer qtdeCasasDec = new Integer(2);
					  Method m = Reflexao.findMethod(propriedade + "_casasDecimais", objeto);
					  if (m != null){
						  qtdeCasasDec = (Integer) Reflexao.getPropertyValue(objeto, propriedade + "_casasDecimais");
					  }
					  valueStr = UtilFormatacao.formatBigDecimal((BigDecimal) value, qtdeCasasDec.intValue());
				  }else{
					  valueStr = value.toString();
				  }				  
			      strResult = strResult + "\6\2";
			      if (naoCodificaApostrofeAspas){
			    	  strResult = strResult + CitAjaxUtil.convertePrimeiraLetra(propriedade, "L") + "\4\2" + codificaEnter(valueStr) + "\5";
			      }else{
			    	  //strResult = strResult + CitAjaxUtil.convertePrimeiraLetra(propriedade, "L") + "\4\2" + JavaScriptUtil.escapeJavaScript(codificaAspasApostrofe(codificaEnter(valueStr))) + "\5";
			    	  strResult = strResult + CitAjaxUtil.convertePrimeiraLetra(propriedade, "L") + "\4\2" + JavaScriptUtil.escapeJavaScript(codificaEnter(valueStr)) + "\5";
			      }
			      strResult = strResult + "\5";
			  }else{
			      strResult = strResult + "\6\2";
			      strResult = strResult + CitAjaxUtil.convertePrimeiraLetra(propriedade, "L") + "\4\2" + "\5";
			      strResult = strResult + "\5";				  
			  }
		  }
	  }
	  return strResult;
	}
	public static String codificaEnter(String str){
		if (str == null) return "";
		String x = str.replaceAll("\r","#10#");
		return x.replaceAll("\n","#13#");
	};
	public static String codificaAspasApostrofe(String str){
		if (str == null) return "";
		String x = str.replaceAll("\"","#32#");
		return x.replaceAll("\'", "#33#");
	}
	public static String decodificaEnter(String str){
		if (str == null) return "";
		String x = str.replaceAll("#10#","\r");
		return x.replaceAll("#13#","\n");
	};
	public static String decodificaAspasApostrofe(String str){
		if (str == null) return "";
		String x = str.replaceAll("#32#","\"");
		return x.replaceAll("#33#", "\'");
	}	
	public static String codificaEnterByChar(String str, String charCode){
		if (str == null) return "";
		String x = str.replaceAll("\r",charCode);
		return x.replaceAll("\n",charCode);
	};
		
}
