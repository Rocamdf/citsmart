package br.com.centralit.citajax.framework;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

public class ParserRequest {
	/**
	 * Processa todos os elementos do request e gera um HashMap
	 * @param req
	 * @return
	 */
	public HashMap getFormFields(HttpServletRequest req){
		try {
			req.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("PROBLEMA COM CODIFICACAO DE CARACTERES!!! [AjaxProcessEvent.getFormFields()]");
			e.printStackTrace();
		}
		HashMap formFields = new HashMap();
		Enumeration en = req.getParameterNames();
		String[] strValores;
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
	/**
	 * Passa os valores do Hash para o Bean.
	 * @param valores
	 * @param bean
	 * @throws Exception
	 */
	public void converteValoresRequestToBean(HashMap valores, Object bean) throws Exception{
		List metodos = CitAjaxReflexao.findSets(bean);
		Object valorAtributo = null;
		Class[] classParametro;
		Method mtd;
		Object retorno;
		for (int i = 0; i < metodos.size(); i++){
			valorAtributo = (Object)valores.get(((String)metodos.get(i)).toUpperCase());
			if (valorAtributo instanceof String) {
				mtd = CitAjaxReflexao.getSetter(bean, (String)metodos.get(i));
				classParametro = mtd.getParameterTypes();	
				
				try {
					if (Integer[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE INTEIROS
						try{
							retorno = new Integer[] { new Integer((String)valorAtributo) };
						}catch (Exception e) {
							retorno = new Integer[] { null };
						}
					}else if (Long[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE LONGOS
						try{
							retorno = new Long[] { new Long((String)valorAtributo) };
						}catch (Exception e) {
							retorno = new Long[] { null };
						}
					}else if (String[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE STRINGS
						try{
							retorno = new String[] { new String( UtilStrings.decodeCaracteresEspeciais((String)valorAtributo)) };
						}catch (Exception e) {
							retorno = new String[] { null };
						}							
					}else if (Short[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE SHORTS
						try{
							retorno = new Short[] { new Short((String)valorAtributo) };
						}catch (Exception e) {
							retorno = new Short[] { null };
						}							
					}else if (Double[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE DOUBLES
						String aux = (String)valorAtributo;
						aux = aux.replaceAll("\\.", "");
						aux = aux.replaceAll("\\,", "\\.");
						
						Double duplo = null;
						try{
							duplo = new Double( Double.parseDouble(aux) );
							retorno = new Double[] { new Double(duplo.doubleValue()) };
						}catch (Exception e) {
							//e.printStackTrace();
							duplo = null;
							retorno = new Double[] { null };
						}						

					}else if (BigDecimal[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE BIGDECIMAL
						String aux = (String)valorAtributo;
						aux = aux.replaceAll("\\.", "");
						aux = aux.replaceAll("\\,", "\\.");
						
						BigDecimal big = null;
						try{
							big = new BigDecimal( Double.parseDouble(aux) );
							retorno = new BigDecimal[] { new BigDecimal(big.doubleValue()) };						
						}catch (Exception e) {
							retorno = new BigDecimal[] { null };
						}

					}else if (Date[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE DATES
						Date data = null;
						
						if (valorAtributo == null || ((String)valorAtributo).equalsIgnoreCase("")){
							data = null;
							retorno = new Date[] { null };
						}else{
							data = new Date( UtilDatas.strToSQLDate(((String)valorAtributo)).getTime());
							retorno = new Date[] { new Date(data.getTime()) };						
						}
					}else if (Timestamp[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE DATES
						Timestamp data = null;
						
						if (valorAtributo == null || ((String)valorAtributo).equalsIgnoreCase("")){
							data = null;
							retorno = new Timestamp[] { null };
						}else{
							data = Timestamp.valueOf((String)valorAtributo);
							retorno = new Timestamp[] { new Timestamp(data.getTime()) };						
						}						
					}else if (Boolean[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE BOOLEANS
						retorno = new Boolean[] { new Boolean((String)valorAtributo) };
					}else{ //NAO EH ARRAY
						retorno = CitAjaxReflexao.converteTipo((String)valorAtributo, classParametro[0]);
					}
				} catch (Exception e) {

					e.printStackTrace();
					throw new Exception("Erro ao converter o valor '"+valorAtributo+"' do atributo "+(String)metodos.get(i)+" para "+classParametro[0]);
				}
				try {
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), retorno);
				} catch (Exception e) {
					e.printStackTrace();
					throw new Exception("Erro ao setar o valor '"+valorAtributo+"' no atributo "+(String)metodos.get(i)+" do tipo "+classParametro[0]);

				}
			//ELSE IF PARA ARRAY - O QUE VEIO NO REQUEST EH ARRAY!
			}else if (valorAtributo instanceof String[]) {
				mtd = CitAjaxReflexao.getSetter(bean, (String)metodos.get(i));
				classParametro = mtd.getParameterTypes();	
				
				if (Integer[].class.isAssignableFrom(classParametro[0])){
					Integer[] arrayInteiros = new Integer[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						Integer inteiro = null;
						try{
							inteiro = new Integer( ((String[])valorAtributo)[j] );
						}catch (Exception e) {
							//e.printStackTrace();
							inteiro = null;
						}
						arrayInteiros[j] = inteiro;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayInteiros);
				}
				if (Long[].class.isAssignableFrom(classParametro[0])){
					Long[] arrayLongos = new Long[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						Long longo = null;
						try{
							longo = new Long( ((String[])valorAtributo)[j] );
						}catch (Exception e) {
							//e.printStackTrace();
							longo = null;
						}
						arrayLongos[j] = longo;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayLongos);					
				}
				if (String[].class.isAssignableFrom(classParametro[0])){
					String[] strAux = (String[])valorAtributo;
					if (strAux != null){
						for(int iAux = 0; iAux < strAux.length; iAux++){
							strAux[iAux] = UtilStrings.decodeCaracteresEspeciais(strAux[iAux]);
						}
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), strAux);										
				}
				if (Short[].class.isAssignableFrom(classParametro[0])){
					Short[] arrayShorts = new Short[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						Short curto;
						curto = new Short( ((String[])valorAtributo)[j] );
						arrayShorts[j] = curto;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayShorts);					
				}
				if (Double[].class.isAssignableFrom(classParametro[0])){
					Double[] arrayDuplo = new Double[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						Double duplo;
						
						String aux = ((String[])valorAtributo)[j];
						aux = aux.replaceAll("\\.", "");
						aux = aux.replaceAll("\\,", "\\.");
						
						if (aux == null || aux.equalsIgnoreCase("")){
							duplo = null;
						}else{
							try{
								duplo = new Double( Double.parseDouble(aux) );
							}catch (Exception e) {
								//e.printStackTrace();
								duplo = null;
							}
						}
						arrayDuplo[j] = duplo;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayDuplo);					
				}				
				if (BigDecimal[].class.isAssignableFrom(classParametro[0])){
					BigDecimal[] arrayBigDec = new BigDecimal[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						BigDecimal bigDec = null;
						
						String aux = ((String[])valorAtributo)[j];
						aux = aux.replaceAll("\\.", "");
						aux = aux.replaceAll("\\,", "\\.");
						
						try{
							bigDec = new BigDecimal( Double.parseDouble(aux) );
						}catch (Exception e) {
							//e.printStackTrace();
							bigDec = null;
						}
						arrayBigDec[j] = bigDec;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBigDec);					
				}
				if (Date[].class.isAssignableFrom(classParametro[0])){
					Date[] arrayData = new Date[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						Date data = null;
						try{
							data = new Date( UtilDatas.strToSQLDate(((String[])valorAtributo)[j]).getTime() );
						}catch (Exception e) {
							//e.printStackTrace();
							data = null;
						}
						arrayData[j] = data;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayData);					
				}
				if (Timestamp[].class.isAssignableFrom(classParametro[0])){
					Timestamp[] arrayData = new Timestamp[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						Timestamp data = null;
						try{
							data = new Timestamp( UtilDatas.strToSQLDate(((String[])valorAtributo)[j]).getTime() );
						}catch (Exception e) {
							//e.printStackTrace();
							data = null;
						}
						arrayData[j] = data;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayData);					
				}				
				if (Boolean[].class.isAssignableFrom(classParametro[0])){
					Boolean[] arrayBool = new Boolean[((String[])valorAtributo).length];
					for(int j = 0; j < ((String[])valorAtributo).length; j++){
						Boolean bool;
						bool = new Boolean( ((String[])valorAtributo)[j] );
						arrayBool[j] = bool;
					}
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBool);					
				}	
			//ELSE
			}else{
				mtd = CitAjaxReflexao.getSetter(bean, (String)metodos.get(i));
				classParametro = mtd.getParameterTypes();
				if (classParametro[0].isInstance(valorAtributo)){
					CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), valorAtributo);
				}
			}
		}
	}
	public Collection converteValoresRequestToInternalBean(HashMap valores, Class classe, String nomeInternalBean) throws Exception{
		Object beanGetMethodList = null;
		try {
			beanGetMethodList = classe.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		if (beanGetMethodList == null){
			return null;
		}		
		List metodos = CitAjaxReflexao.findSets(beanGetMethodList);
		Object valorAtributo = null;
		Class[] classParametro;
		Method mtd;
		Object retorno;
		
		Collection col = new ArrayList();
		
		String str = (String)valores.get(nomeInternalBean.toUpperCase() + "_ULTIMO_SEQUENCIAL_HIDDEN");
		int iQtde = 0;
		if (str != null && !str.equalsIgnoreCase("")){
			iQtde = Integer.parseInt(str);
		}
		for (int x = 0; x <= iQtde; x++){
			Object bean = null;
			try {
				bean = classe.newInstance();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
			if (bean == null){
				continue;
			}	
			boolean somenteNulos = true;
			for (int i = 0; i < metodos.size(); i++){
				valorAtributo = (Object)valores.get(nomeInternalBean.toUpperCase() + "." + ((String)metodos.get(i)).toUpperCase() + "." + x);
				if (valorAtributo == null){
					continue;
				}
				somenteNulos = false;
				if (valorAtributo instanceof String) {
					mtd = CitAjaxReflexao.getSetter(bean, (String)metodos.get(i));
					classParametro = mtd.getParameterTypes();	
					
					try {
						if (Integer[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE INTEIROS
							try{
								retorno = new Integer[] { new Integer((String)valorAtributo) };
							}catch (Exception e) {
								retorno = new Integer[] { null };
							}
						}else if (Long[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE LONGOS
							try{
								retorno = new Long[] { new Long((String)valorAtributo) };
							}catch (Exception e) {
								retorno = new Long[] { null };
							}
						}else if (String[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE STRINGS
							try{
								retorno = new String[] { new String( UtilStrings.decodeCaracteresEspeciais((String)valorAtributo)) };
							}catch (Exception e) {
								retorno = new String[] { null };
							}							
						}else if (Short[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE SHORTS
							try{
								retorno = new Short[] { new Short((String)valorAtributo) };
							}catch (Exception e) {
								retorno = new Short[] { null };
							}							
						}else if (Double[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE DOUBLES
							String aux = (String)valorAtributo;
							aux = aux.replaceAll("\\.", "");
							aux = aux.replaceAll("\\,", "\\.");
							
							Double duplo = null;
							try{
								duplo = new Double( Double.parseDouble(aux) );
								retorno = new Double[] { new Double(duplo.doubleValue()) };
							}catch (Exception e) {
								//e.printStackTrace();
								duplo = null;
								retorno = new Double[] { null };
							}						
	
						}else if (BigDecimal[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE BIGDECIMAL
							String aux = (String)valorAtributo;
							aux = aux.replaceAll("\\.", "");
							aux = aux.replaceAll("\\,", "\\.");
							
							BigDecimal big = null;
							try{
								big = new BigDecimal( Double.parseDouble(aux) );
								retorno = new BigDecimal[] { new BigDecimal(big.doubleValue()) };						
							}catch (Exception e) {
								retorno = new BigDecimal[] { null };
							}
	
						}else if (Date[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE DATES
							Date data = null;
							
							if (valorAtributo == null || ((String)valorAtributo).equalsIgnoreCase("")){
								data = null;
								retorno = new Date[] { null };
							}else{
								data = new Date( UtilDatas.strToSQLDate(((String)valorAtributo)).getTime());
								retorno = new Date[] { new Date(data.getTime()) };						
							}
						}else if (Boolean[].class.isAssignableFrom(classParametro[0])){ //ARRAY DE BOOLEANS
							retorno = new Boolean[] { new Boolean((String)valorAtributo) };
						}else{ //NAO EH ARRAY
							retorno = CitAjaxReflexao.converteTipo((String)valorAtributo, classParametro[0]);
						}
					} catch (Exception e) {
	
						e.printStackTrace();
						throw new Exception("Erro ao converter o valor '"+valorAtributo+"' do atributo "+(String)metodos.get(i)+" para "+classParametro[0]);
					}
					try {
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), retorno);
					} catch (Exception e) {
						e.printStackTrace();
						throw new Exception("Erro ao setar o valor '"+valorAtributo+"' no atributo "+(String)metodos.get(i)+" do tipo "+classParametro[0]);
	
					}
				//ELSE IF PARA ARRAY - O QUE VEIO NO REQUEST EH ARRAY!
				}else if (valorAtributo instanceof String[]) {
					mtd = CitAjaxReflexao.getSetter(bean, (String)metodos.get(i));
					classParametro = mtd.getParameterTypes();	
					
					if (Integer[].class.isAssignableFrom(classParametro[0])){
						Integer[] arrayInteiros = new Integer[((String[])valorAtributo).length];
						for(int j = 0; j < ((String[])valorAtributo).length; j++){
							Integer inteiro = null;
							try{
								inteiro = new Integer( ((String[])valorAtributo)[j] );
							}catch (Exception e) {
								//e.printStackTrace();
								inteiro = null;
							}
							arrayInteiros[j] = inteiro;
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayInteiros);
					}
					if (Long[].class.isAssignableFrom(classParametro[0])){
						Long[] arrayLongos = new Long[((String[])valorAtributo).length];
						for(int j = 0; j < ((String[])valorAtributo).length; j++){
							Long longo = null;
							try{
								longo = new Long( ((String[])valorAtributo)[j] );
							}catch (Exception e) {
								//e.printStackTrace();
								longo = null;
							}
							arrayLongos[j] = longo;
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayLongos);					
					}
					if (String[].class.isAssignableFrom(classParametro[0])){
						String[] strAux = (String[])valorAtributo;
						if (strAux != null){
							for(int iAux = 0; iAux < strAux.length; iAux++){
								strAux[iAux] = UtilStrings.decodeCaracteresEspeciais(strAux[iAux]);
							}
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), strAux);										
					}
					if (Short[].class.isAssignableFrom(classParametro[0])){
						Short[] arrayShorts = new Short[((String[])valorAtributo).length];
						for(int j = 0; j < ((String[])valorAtributo).length; j++){
							Short curto;
							curto = new Short( ((String[])valorAtributo)[j] );
							arrayShorts[j] = curto;
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayShorts);					
					}
					if (Double[].class.isAssignableFrom(classParametro[0])){
						Double[] arrayDuplo = new Double[((String[])valorAtributo).length];
						for(int j = 0; j < ((String[])valorAtributo).length; j++){
							Double duplo;
							
							String aux = ((String[])valorAtributo)[j];
							aux = aux.replaceAll("\\.", "");
							aux = aux.replaceAll("\\,", "\\.");
							
							if (aux == null || aux.equalsIgnoreCase("")){
								duplo = null;
							}else{
								try{
									duplo = new Double( Double.parseDouble(aux) );
								}catch (Exception e) {
									//e.printStackTrace();
									duplo = null;
								}
							}
							arrayDuplo[j] = duplo;
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayDuplo);					
					}				
					if (BigDecimal[].class.isAssignableFrom(classParametro[0])){
						BigDecimal[] arrayBigDec = new BigDecimal[((String[])valorAtributo).length];
						for(int j = 0; j < ((String[])valorAtributo).length; j++){
							BigDecimal bigDec = null;
							
							String aux = ((String[])valorAtributo)[j];
							aux = aux.replaceAll("\\.", "");
							aux = aux.replaceAll("\\,", "\\.");
							
							try{
								bigDec = new BigDecimal( Double.parseDouble(aux) );
							}catch (Exception e) {
								//e.printStackTrace();
								bigDec = null;
							}
							arrayBigDec[j] = bigDec;
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBigDec);					
					}
					if (Date[].class.isAssignableFrom(classParametro[0])){
						Date[] arrayData = new Date[((String[])valorAtributo).length];
						for(int j = 0; j < ((String[])valorAtributo).length; j++){
							Date data = null;
							try{
								data = new Date( UtilDatas.strToSQLDate(((String[])valorAtributo)[j]).getTime() );
							}catch (Exception e) {
								//e.printStackTrace();
								data = null;
							}
							arrayData[j] = data;
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayData);					
					}
					if (Boolean[].class.isAssignableFrom(classParametro[0])){
						Boolean[] arrayBool = new Boolean[((String[])valorAtributo).length];
						for(int j = 0; j < ((String[])valorAtributo).length; j++){
							Boolean bool;
							bool = new Boolean( ((String[])valorAtributo)[j] );
							arrayBool[j] = bool;
						}
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), arrayBool);					
					}	
				//ELSE
				}else{
					mtd = CitAjaxReflexao.getSetter(bean, (String)metodos.get(i));
					classParametro = mtd.getParameterTypes();
					if (classParametro[0].isInstance(valorAtributo)){
						CitAjaxReflexao.setPropertyValue(bean, mtd.getName().substring(3), valorAtributo);
					}
				}
			}
			if (!somenteNulos){
				col.add(bean);
			}
		}
		return col;
	}
}
