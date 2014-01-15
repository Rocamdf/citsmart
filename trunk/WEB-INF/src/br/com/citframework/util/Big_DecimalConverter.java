package br.com.citframework.util;

import org.apache.commons.beanutils.Converter;

public class Big_DecimalConverter implements Converter{

	public Object convert(Class classe, Object value) {
		
		if(value==null){
			return null;
		}
		try{
			return ConverterUtils.strFormatToBigDecimal(value.toString());
		}catch(Exception e){
			System.out.println("Valor Bigdecimal :"+value);
			e.printStackTrace();
			return null;
		}
		
	}
	
	
	
	



}
