/*
 * Created on 11/10/2005
 *

 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package br.com.citframework.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.math.NumberUtils;

import br.com.citframework.dto.ReflexaoCopyProperty;
import br.com.citframework.excecao.LogicException;

/**
 * @author ney
 */
public class Reflexao {

	public static void executeAll(Object obj) {

		Iterator it = findSets(obj).iterator();
		while (it.hasNext()) {

			findExecuteGet(it.next().toString(), obj);
		}

	}
	
	
	
	/**
	 * Limpa todas as propriedades de um objeto.
	 * 
	 * 	Exemplo:	
	 * 		Usuario usuario = new Usuario();
	 *      usuario.setNomeUsuario("EMAURI");
	 *	    usuario.setIdEmpresa(new Integer(1));
	 *
	 * 		Reflexao.clearAllProperties(usuario);
	 * 
	 *      Apos a execucao do metodo anterior, o objeto usuario estará limpo (todas as propriedades nulas).
	 * 
	 * @param obj
	 * @throws Exception
	 */
	public static void clearAllProperties(Object obj)throws Exception{
		
		  List lista = getAllProperties(obj);
		  Iterator it = lista.iterator();
		  while(it.hasNext()){
			  setPropertyValue(obj, it.next().toString(), null);
			  
		  }
		
		
	}

	public static boolean findExecuteGet(String name, Object obj) {

		Method[] mtd = obj.getClass().getMethods();

		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().equalsIgnoreCase("get" + name)) {

				try {
					mtd[i].invoke(obj, null);
				} catch (Exception e) {
					return false;
				}
				return true;
			}

		}

		return false;
	}

	public static boolean findGet(String name, Object obj) {

		Method[] mtd = obj.getClass().getMethods();

		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().equalsIgnoreCase("get" + name)) {
				return true;
			}

		}

		return false;
	}

	public static Method findMethod(String name, Object obj) {

		Method[] mtd = obj.getClass().getMethods();

		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().equalsIgnoreCase(name)) {
				return mtd[i];
			}

		}

		return null;
	}
	
	public static Collection findMethodByPalavra(String palavra, Object obj) {
		Collection colMethods = new ArrayList();
		Method[] mtd = obj.getClass().getMethods();

		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().indexOf(palavra) > -1) {
				colMethods.add(mtd[i]);
			}

		}

		return colMethods;
	}	

	public static Method findMethod(String name, Object obj, int index) {

		Method[] mtd = obj.getClass().getMethods();
		int param = 0;
		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().equalsIgnoreCase(name)) {
				if (param == index) {
					return mtd[i];
				}
				param++;
			}

		}

		return null;
	}

	public static List findSets(Object obj) {

		/*
		 * if(obj==null){ return new ArrayList(); }
		 */

		List result = new ArrayList();
		Method[] mtd = obj.getClass().getMethods();

		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().startsWith("set")) {
				result.add(mtd[i].getName().substring(3));
			}
		}
		return result;
	}

	public static List findGets(Object obj) {

		List result = new ArrayList();
		Method[] mtd = obj.getClass().getMethods();

		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().startsWith("get")) {
				result.add(mtd[i].getName().substring(3));
			}
		}
		return result;
	}

	public static Class getReturnType(Object obj, String propName) {

		Method[] mtd = obj.getClass().getMethods();

		for (int i = 0; i < mtd.length; i++) {
			if (mtd[i].getName().equalsIgnoreCase("get" + propName)) {
				return mtd[i].getReturnType();
			}
		}
		return null;
	}

	public static List getAllProperties(Object obj) {

		List lista = new ArrayList();
		Iterator it = findSets(obj).iterator();
		while (it.hasNext()) {

			String prop = it.next().toString();
			if (findGet(prop, obj)) {
				lista.add(prop.substring(0, 1).toLowerCase() + prop.substring(1));
			}
		}

		return lista;

	}

	public static List getCommonProperties(Object obj1, Object obj2) {

		List lista1 = getAllProperties(obj1);
		List lista2 = getAllProperties(obj2);
		List result = new ArrayList();
		for (int i = 0; i < lista1.size(); i++) {
			String prop = (String) lista1.get(i);
			int ind = lista2.indexOf(prop);
			if (ind >= 0) {
				if (getReturnType(obj1, prop).getName().equals(getReturnType(obj2, prop).getName())) {
					result.add(prop);
				}
			}
		}
		return result;
	}

	public static List getCommonPropertyNames(Object obj1, Object obj2) {

		List lista1 = getAllProperties(obj1);
		List lista2 = getAllProperties(obj2);
		List result = new ArrayList();
		for (int i = 0; i < lista1.size(); i++) {
			String prop = (String) lista1.get(i);
			int ind = lista2.indexOf(prop);
			if (ind >= 0) {
				result.add(prop);
			}
		}
		return result;
	}
	
	/**
	 * Copia todas propriedades de um HashMap que sao comuns em outro objeto.
	 *    As propriedades que serao copiadas devem ter o mesmo nome (Independente de maiusculas e minusculas).
	 * 
	 * @param source - HashMap origem (de onde serao copiadas as propriedades)
	 * @param dest - Objeto destino (para onde serao copiadas as propriedades)
	 * @throws Exception
	 */
	public static void copyPropertyValuesFromMap(HashMap source, Object dest) throws Exception {
		if (source == null || dest == null) {
			throw new Exception("Source and Destination Object can not be null.");
		}

		Set setHM = source.entrySet();
		Iterator i = setHM.iterator(); 
		List lista2 = getAllProperties(dest);
		while(i.hasNext()) {
			Map.Entry me = (Map.Entry)i.next(); 
			String propName1 = me.getKey().toString().trim().toUpperCase();
			System.out.println("copyPropertyValuesFromMap: " + propName1 + "---> " + me.getValue());
			for (int iX = 0; iX < lista2.size(); iX++){
				String propName = (String) lista2.get(iX);
				if (propName.trim().toUpperCase().equalsIgnoreCase(propName1)){
					System.out.println("copyPropertyValuesFromMap: " + propName1 + "---> Encontrou... ");
					Object value = me.getValue();
					if (value != null) {
						Object[] param = new Object[1];
						param[0] = value;
						
						setPropertyValue(dest, propName, value);
					}
					break;
				}
			}
		} 
		System.out.println(dest);
	}
	
	/**
	 * Copia todas propriedades de um objeto que sao comuns em outro objeto.
	 *    As propriedades que serao copiadas devem ter o mesmo nome (Inclusive letras maiusculas e minusculas).
	 * 
	 * @param source - Objeto origem (de onde serao copiadas as propriedades)
	 * @param dest - Objeto destino (para onde serao copiadas as propriedades)
	 * @throws Exception
	 */
	public static void copyPropertyValues(Object source, Object dest) throws Exception {
		if (source == null || dest == null) {
			throw new Exception("Source and Destination Object can not be null.");
		}

		List tmp = getCommonProperties(source, dest);
		Iterator it = tmp.iterator();
		while (it.hasNext()) {
			String prop = (String) it.next();

			Method get = findMethod("get" + prop, source);

			Object value = null;
			try {
				value = get.invoke(source, null);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
			if (value != null) {
				Object[] param = new Object[1];
				param[0] = value;

				Method set = findMethod("set" + prop, dest);
				try {
					set.invoke(dest, param);
				} catch (IllegalArgumentException e) {
					System.out.println("Erro de Conversao....1");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					System.out.println("Erro de Conversao....2");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					System.out.println("Erro de Conversao....3");
					e.printStackTrace();
				}

			}

		}

	}
	
	/**
	 * Copia todas propriedades de um objeto que estao relacionadas no array passado como parametro.
	 * 	As propriedades podem ter nomes diferentes (a origem do destino).
	 * 
	 * Exemplo:
	 	Usuario usuario = new Usuario();
		LogAcessoUsuario logAcessoUsuario = new LogAcessoUsuario(); 
		
		usuario.setNomeUsuario("EMAURI");
		usuario.setIdEmpresa(new Integer(1));
		
		Reflexao.copyPropertyValues(usuario, logAcessoUsuario, new ReflexaoCopyProperty[] {
				new ReflexaoCopyProperty("NomeUsuario","login"),
				new ReflexaoCopyProperty("idEmpresa","HistAtualUsuario_idUsuario")});
	 * 
	 * @param source - Objeto origem (de onde serao copiadas as propriedades)
	 * @param dest - Objeto destino (para onde serao copiadas as propriedades)
	 * @param ReflexaoCopyProperty[] - Este parametro define as propriedades que devem ser copiadas (origem e destino)
	 * @throws Exception
	 */	
	public static void copyPropertyValues(Object source, Object dest, ReflexaoCopyProperty[] propertiesCopy) throws Exception {
		if (propertiesCopy == null){
			return;
		}
		for(int i = 0; i < propertiesCopy.length; i++){
			Method get = findMethod("get" + propertiesCopy[i].getNamePropertySource(), source);

			Object value = null;
			try {
				value = get.invoke(source, null);
			} catch (IllegalArgumentException e1) {
				e1.printStackTrace();
			} catch (IllegalAccessException e1) {
				e1.printStackTrace();
			} catch (InvocationTargetException e1) {
				e1.printStackTrace();
			}
			
			if (value != null) {
				Object[] param = new Object[1];
				param[0] = value;

				Method set = findMethod("set" + propertiesCopy[i].getNamePropertyDest(), dest);
				try {
					set.invoke(dest, param);
				} catch (IllegalArgumentException e) {
					System.out.println("Erro de Conversao....1");
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					System.out.println("Erro de Conversao....2");
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					System.out.println("Erro de Conversao....3");
					e.printStackTrace();
				}
			}			
		}
	}
	
	public static Collection convertList(Collection source, Class classeDest, ReflexaoCopyProperty[] propertiesCopy) throws Exception {
		if (source == null){
			return null;
		}
		Collection dest = new ArrayList();
		for(Iterator it = source.iterator(); it.hasNext();){
			Object objSource = it.next();
			Object objDest = classeDest.newInstance();
			copyPropertyValues(objSource, objDest, propertiesCopy);
			dest.add(objDest);
		}
		return dest;
	}	

	public static Object getPropertyValue(Object obj, String propName) throws Exception {

		Method met = findMethod("get" + propName, obj);
		if (met == null) {
			throw new Exception("Propriedade [" + propName + "] nao encontrada na classe " + obj.getClass().getName());
		}
		return met.invoke(obj, null);

	}

	public static void setPropertyValueAsString(Object obj, String propName, String propValue) throws Exception, InstantiationException, IllegalAccessException, InvocationTargetException {

		Method set = findMethod("set" + propName, obj);

		Class retorno = getReturnType(obj, propName);
		Object valorConvertido = null;
		Constructor[] cons = retorno.getConstructors();

		for (int i = 0; i < cons.length; i++) {
			if (cons[i].toString().indexOf("(java.lang.String)") > -1)
				valorConvertido = cons[i].newInstance(new Object[] { propValue });
		}
		set.invoke(obj, new Object[] { valorConvertido });
	}

	public static void setPropertyValue(Object obj, String propName, Object value) throws Exception {
		setNested(obj, value, propName);
	}
	
	private static void setNested(Object bean, Object value, String attributeName) throws Exception {
		int dotIndex = attributeName.indexOf('.');
		if (dotIndex == -1) {
			Method setter = getSetter(bean, attributeName);
			if (setter != null){
				Class parameterClass = setter.getParameterTypes()[0];
				if ((value instanceof Long) && (parameterClass == BigInteger.class)) {
					value = new BigInteger(value.toString());
				}
				if ((value instanceof Long) && (parameterClass == Integer.class)) {
					value = new Integer(value.toString());
				}
				if ((value instanceof Short) && (parameterClass == Integer.class)) {
					value = new Integer(value.toString());
				}				
				if ((value instanceof Short) && (parameterClass == Long.class)) {
					value = new Long(value.toString());
				}				
				if ((value instanceof Integer) && (parameterClass == Long.class)) {
					value = new Long(value.toString());
				}				
				if ((value instanceof String) && (parameterClass == Integer.class)) {
					try{
						value = new Integer(value.toString());
					}catch(Exception e){
						value = null;
					}
				}	
				if ((value instanceof String) && (parameterClass == BigInteger.class)) {
					value = new BigInteger(value.toString());
				}				
				if ((value instanceof Long) && (parameterClass == Double.class)) {
					value = new Double(value.toString());
				}
				if ((value instanceof Integer) && (parameterClass == Double.class)) {
					value = new Double(value.toString());
				}
				if ((value instanceof String) && (parameterClass == Double.class)) {
					value = UtilNumbersAndDecimals.strFormatToDouble(value.toString());
				}				
				if ((value instanceof BigDecimal) && (parameterClass == Integer.class)) {
					value = new Integer(value.toString());
				}
				if ((value instanceof BigDecimal) && (parameterClass == String.class)) {
					value = new String(value.toString());
				}
				
				/*
				 * Rodrigo Pecci Acorse - 14/11/2013 - #124212
				 * A reflexão não previa (e não fazia a conversão) caso o valor a ser setado fosse do tipo Long e o tipo no DTO fosse String. 
				 */
				if ((value instanceof Long) && (parameterClass == String.class)) {
					value = new String(value.toString());
				}
				
				if ((value instanceof BigInteger) && (parameterClass == Long.class)) {
					value = new Long(((BigInteger)value).longValue());
				}
				if ((value instanceof Long) && (parameterClass == BigDecimal.class)) {
					value = new BigDecimal(((Long) value).longValue());
				} else if ((value instanceof BigDecimal) && (parameterClass == Long.class)) {
					value = new Long(((BigDecimal) value).longValue());
				}
				if ((value instanceof Integer) && (parameterClass == BigDecimal.class)) {
					value = new BigDecimal(((Integer) value).intValue());
				} else if ((value instanceof BigDecimal) && (parameterClass == Integer.class)) {
					value = new Integer(((BigDecimal) value).intValue());
				} else if ((value instanceof BigDecimal) && (parameterClass == Double.class)) {
					value = new Double(((BigDecimal) value).doubleValue());
				} else if ((value instanceof Float) && (parameterClass == Double.class)) {
					value = new Double(((Float) value).doubleValue());					
				}
				if ((value instanceof Timestamp) && (parameterClass == Date.class)) {
					value = new Date(((Timestamp)value).getTime());
				}
				if ((value instanceof String) && (parameterClass == Date.class)) {
					value = UtilDatas.strToSQLDate(value.toString());
				}				
				if ((value instanceof Date) && (parameterClass == Timestamp.class)) {
					value = new Timestamp(((Date)value).getTime());
				}
				if ((value instanceof String) && (parameterClass == Timestamp.class)) {
					value = UtilDatas.strToTimestamp(value.toString());
				}				
				if ((value instanceof Integer) && (parameterClass == Short.class)) {
					value = new Short(((Integer) value).shortValue());
				}else if (value != null && value.getClass().getName().indexOf("com.ibm.db2.jcc") != -1) { //Tipo do DB2 para campo memo que merece tratamento.
					if (value.getClass().getName().indexOf("com.ibm.db2.jcc.am.ie") == -1){
						/*
						Method lengthMethod = value.getClass().getMethod("length", null);
						Integer length = null;
						if (lengthMethod.getReturnType() == Integer.class){
							length = (Integer) lengthMethod.invoke(value, null);
						}else if (lengthMethod.getReturnType() == Long.class){
							Long lengthLong = (Long) lengthMethod.invoke(value, null);
							length = new Integer(lengthLong.intValue());
						}else if (lengthMethod.getReturnType() == long.class){
							Long lengthLong = (Long) lengthMethod.invoke(value, null);
							length = new Integer(lengthLong.intValue());
						}else if (lengthMethod.getReturnType() == int.class){
							length = (Integer) lengthMethod.invoke(value, null);
						}else{
							length = (Integer) lengthMethod.invoke(value, null);
						}
						if (length == null) length = new Integer(0);
						if (length.intValue() != 0) {
							Method substringMethod = value.getClass().getMethod("substring", new Class[]{int.class});
							value = substringMethod.invoke(value, new Object[]{NumberUtils.INTEGER_ZERO});
						} else {
							value = null;
						}
						*/
						Method lengthMethod = value.getClass().getMethod("length", null);
						Integer length = (Integer) lengthMethod.invoke(value, null);
						if (length.intValue() != 0) {
							Method substringMethod = value.getClass().getMethod("substring", new Class[]{int.class});
							value = substringMethod.invoke(value, new Object[]{NumberUtils.INTEGER_ZERO});
						} else {
							value = null;
						}						
					}
				}else if (value != null && value.getClass().getName().indexOf("oracle.sql.TIMESTAMP") != -1) { 
					Method timeValueMethod = value.getClass().getMethod("timestampValue", null);
					Timestamp timeValue = null;
					if (timeValueMethod.getReturnType() == Timestamp.class){
						timeValue = (Timestamp) timeValueMethod.invoke(value, null);
					}
					if (timeValue != null){
						value = new Date(((Timestamp)timeValue).getTime());
					}else{
						value = null;
					}
				}				
				
				try {

						setter.invoke(bean, new Object[]{value});
					
				} catch (Exception e) {
					if(value !=null){
						throw new Exception("tipo de dado incompatível com o banco de dados "+value.getClass().getName()+" :"+bean.getClass().getName()+" "+attributeName, e);	
					}
					
					throw e;
					
				}
			}
		} else {
			String currentAttributeName = attributeName.substring(0, dotIndex);
			Method getter = getGetter(bean, currentAttributeName);
			if (getter != null){
				Object valueOfCurrent = getter.invoke(bean, null);
				if (valueOfCurrent == null) {
					Method setter = getSetter(bean, currentAttributeName);
					if (setter == null)
						throw new RuntimeException("Não foi encontrado setter para o atributo '" + currentAttributeName + "' em '" + bean.getClass() + "'.");
					Class[] params = setter.getParameterTypes();
					if (params.length != 1)
						throw new RuntimeException("Mais de um setter para o atributo '" + currentAttributeName + "' da classe '" + bean.getClass() + "'.");
					valueOfCurrent = params[0].newInstance();
					try {
						setter.invoke(bean, new Object[]{valueOfCurrent});
					} catch (Exception e) {
						throw new Exception("tipo de dado incompatível com o banco de dados "+value.getClass().getName()+" :"+bean.getClass().getName()+" "+attributeName);
					}
				}
				setNested(valueOfCurrent, value, attributeName.substring(dotIndex + 1));
			}
		}
	}
	  
	public static void setPropertyValueFromString(Object bean, Object value, String attributeName) throws Exception {
		Method setter = getSetter(bean, attributeName);
		if (setter != null){
			Class parameterClass = setter.getParameterTypes()[0];
			if (parameterClass == BigDecimal.class) {
				if (value != null){
					if (!((String) value).trim().equalsIgnoreCase("")){
						String aux = (String)value;
						aux = aux.replaceAll("\\.", "");
						aux = aux.replaceAll("\\,", "\\.");
						value = new BigDecimal(Double.parseDouble(aux));
					}else{
						value = null;
					}
				}
			}else if (parameterClass == Double.class) {
				if (value != null){
					if (!((String) value).trim().equalsIgnoreCase("")){					
						String aux = (String)value;
						aux = aux.replaceAll("\\.", "");
						aux = aux.replaceAll("\\,", "\\.");
						value = new Double(Double.parseDouble(aux));
					}else{
						value = null;
					}
				}
			} else if (parameterClass == Integer.class) {
				if (value != null){
					if (!((String) value).trim().equalsIgnoreCase("")){
						value = new Integer(Integer.parseInt((String) value));
					}else{
						value = null;
					}
				}
			} else if (parameterClass == Long.class) {
				if (!((String) value).trim().equalsIgnoreCase("")){
					value = new Long(Long.parseLong((String) value));
				}else{
					value = null;
				}
								
			} else if (parameterClass == java.sql.Timestamp.class){
				if (!((String) value).trim().equalsIgnoreCase("")){
					value = UtilDatas.strToTimestamp((String)value);
				}else{
					value = null;
				}
				
		    } else if (parameterClass == java.sql.Date.class) {
		    	if (!((String) value).trim().equalsIgnoreCase("")){
		    		value = UtilDatas.strToSQLDate((String)value);
		    	}else{
					value = null;
				}
				
			}
			if(value!=null)
				setter.invoke(bean, new Object[]{value});
		}
	}
	
	
	private static Method getGetter(Object bean, String attributeName) throws SecurityException, NoSuchMethodException {
		Class clazz = bean.getClass();
		String getterName = "get" + attributeName.substring(0,1).toUpperCase() + attributeName.substring(1);
		return clazz.getMethod(getterName, null);
	}
	private static Method getSetter(Object bean, String attributeName) throws SecurityException, NoSuchMethodException {
		Class clazz = bean.getClass();
		String setterName = "set" + attributeName.substring(0,1).toUpperCase() + attributeName.substring(1);
		Method[] methods = clazz.getMethods();
		for (int i = 0; i < methods.length; i++)
			if (methods[i].getName().equals(setterName))
				return methods[i];
		return null;
	}
	/**
	 * Converte um valor (em string) para o parametro correto da classe.
	 * @param value
	 * @param parameterClass
	 * @return
	 */
	public static Object converteTipo(String value, Class parameterClass){
		Object valueRetorno = null;
		if (parameterClass == BigDecimal.class) {
			if (value != null){
				if (!((String) value).equalsIgnoreCase("")){
					String aux = (String)value;
					aux = aux.replaceAll("\\.", "");
					aux = aux.replaceAll("\\,", "\\.");
					valueRetorno = new BigDecimal(Double.parseDouble(aux));
				}else{
					valueRetorno = null;
				}
			}
		}else if (parameterClass == Double.class) {
			if (value != null){
				if (!((String) value).equalsIgnoreCase("")){					
					String aux = (String)value;
					aux = aux.replaceAll("\\.", "");
					aux = aux.replaceAll("\\,", "\\.");
					valueRetorno = new Double(Double.parseDouble(aux));
				}else{
					valueRetorno = null;
				}
			}
		} else if (parameterClass == Integer.class) {
			if (value != null){
				if (!((String) value).equalsIgnoreCase("")){
					valueRetorno = new Integer(Integer.parseInt((String) value));
				}else{
					valueRetorno = null;
				}
			}
		} else if (parameterClass == Long.class) {
			valueRetorno = new Long(Long.parseLong((String) value));	
		} else if (parameterClass == int.class) {
			if (value != null){
				if (!((String) value).equalsIgnoreCase("")){
					valueRetorno = new Integer(Integer.parseInt((String) value));
				}else{
					valueRetorno = null;
				}
			}							
		} else if (parameterClass == java.sql.Date.class) {
			try {
				valueRetorno = UtilDatas.strToSQLDate((String)value);
			} catch (LogicException e) {
				valueRetorno = null;
			}
		} else {
			valueRetorno = value;
		}
		
		return valueRetorno;
	}	
}
