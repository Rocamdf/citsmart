/*
 * Created on 11/10/2005
 *

 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package br.com.centralit.citajax.reflexao;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citajax.exception.LogicException;
import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.util.UtilDatas;

/**
 * @author ney
 */
public class CitAjaxReflexao {

	public static void executeAll(Object obj) {

		Iterator it = findSets(obj).iterator();
		while (it.hasNext()) {

			findExecuteGet(it.next().toString(), obj);
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

	public static Object getPropertyValue(Object obj, String propName) throws Exception {

		Method met = findMethod("get" + propName, obj);
		if (met == null) {
			throw new Exception("Propriedade " + propName + " não encontrada na classe " + obj.getClass().getName());
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
				if(parameterClass == BigInteger.class){
					value = new BigInteger(value.toString());
				}
				if(parameterClass == Timestamp.class){
					try{
						value = Timestamp.valueOf(value.toString());
					}catch (Exception e) {
						value = UtilDatas.strToTimestamp(value.toString());
					}
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
				}
				setter.invoke(bean, new Object[]{value});
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
					setter.invoke(bean, new Object[]{valueOfCurrent});
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
					if (!((String) value).equalsIgnoreCase("")){
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
					if (!((String) value).equalsIgnoreCase("")){					
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
					if (!((String) value).equalsIgnoreCase("")){
						value = new Integer(Integer.parseInt((String) value));
					}else{
						value = null;
					}
				}
			} else if (parameterClass == Long.class) {
				value = new Long(Long.parseLong((String) value));				
			} else if (parameterClass == java.sql.Date.class) {
				value = CitAjaxUtil.strToSQLDate((String)value);
			}
			setter.invoke(bean, new Object[]{value});
		}
	}
	
	
	public static Method getGetter(Object bean, String attributeName) throws SecurityException, NoSuchMethodException {
		Class clazz = bean.getClass();
		String getterName = "get" + attributeName.substring(0,1).toUpperCase() + attributeName.substring(1);
		return clazz.getMethod(getterName, null);
	}
	public static Method getSetter(Object bean, String attributeName) throws SecurityException, NoSuchMethodException {
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
				if (((String) value).trim().length() >0){
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
				if (((String) value).trim().length() >0){					
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
				if (((String) value).trim().length() >0){
					valueRetorno = new Integer(Integer.parseInt((String) value));
				}else{
					valueRetorno = null;
				}
			}
		} else if (parameterClass == Long.class) {
			if (((String) value).trim().length() >0){
			   valueRetorno = new Long(Long.parseLong((String) value));	
			}else{
				valueRetorno = null;
			}
		} else if (parameterClass == int.class) {
			if (value != null){
				if (((String) value).trim().length() >0){
					valueRetorno = new Integer(Integer.parseInt((String) value));
				}else{
					valueRetorno = null;
				}
			}							
		} else if (parameterClass == java.sql.Date.class) {
			try {
				valueRetorno = CitAjaxUtil.strToSQLDate((String)value);
			} catch (LogicException e) {
				valueRetorno = null;
			}
		} else {
			valueRetorno = value;
		}
		
		return valueRetorno;
	}
	
}
