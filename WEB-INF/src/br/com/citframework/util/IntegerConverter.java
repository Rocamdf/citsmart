package br.com.citframework.util;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;



public class IntegerConverter implements Converter{
	
	
	public Object convert(Class classe, Object value)throws ConversionException {
		
		if(value ==null || value.toString().trim().length()==0){
			return null;
		}
		String valor = value.toString();
		if(ConverterUtils.possuiMascara(valor)){
			valor = ConverterUtils.retiraMascara(valor);
		}
		if(value ==null || value.toString().trim().length()==0){
			return null;
		}
		
		return new org.apache.commons.beanutils.converters.IntegerConverter().convert(classe, valor);
		
		
		
	}

	
	
}
