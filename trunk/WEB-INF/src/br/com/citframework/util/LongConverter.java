package br.com.citframework.util;

import org.apache.commons.beanutils.Converter;

public class LongConverter implements Converter {


		public Object convert(Class classe, Object value) {
			
			if(value==null){
				return null;
			}
			try{
				return ConverterUtils.strFormatToLong(value.toString());
			}catch(Exception e){
				System.out.println("Valor Long :"+value);
				e.printStackTrace();
				return null;
			}
		}




}
