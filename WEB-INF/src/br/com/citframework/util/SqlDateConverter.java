/*
 * Created on 12/01/2006
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.util;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;

/**
 * @author ney
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SqlDateConverter implements Converter{

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	public Object convert(Class classe, Object value) throws ConversionException{
		
		if(value==null || value.toString().length()==0){
			return null;
		}
		
		
		String valor = value.toString();
		if(valor.length()==7){
			valor ="01/"+valor; 
		}
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		
		try {
			return new Date(sdf.parse(valor).getTime());
		} catch (ParseException e) {
			
			throw new ConversionException("Formato de data inválida. "+valor); 
			
		}
	}


}
