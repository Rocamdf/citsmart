/*
 * Created on 12/01/2006
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.util;

import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.Converter;





/**
 * @author ney
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DoubleConverter implements Converter {

	/* (non-Javadoc)
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class, java.lang.Object)
	 */
	public Object convert(Class classe, Object value)throws ConversionException {
		
		return ConverterUtils.strFormatToDouble(value.toString());
		
/*		if(value==null || value.toString().length()==0){
			return null;
		}
		if(ConverterUtils.possuiMascara(value.toString())){
		    String tmp = ConverterUtils.retiraMarcara(value.toString());
		    Double result = new Double(tmp);
		    return result;
		    
		}
		
		
		if(value.toString().indexOf(",")>-1){
			StringBuffer str = new StringBuffer(value.toString());
			
			for(int i=0;i<str.length();i++){
				if(str.charAt(i)=='.')
				{
					str = str.deleteCharAt(i);
				}
			}
			
			String tmp = str.toString().replace(',','.');
			
			Double result;
			try {
				result = new Double(tmp);
			} catch (NumberFormatException e) {
				
				throw new ConversionException("Formato numérico inválido. "+value);
			}
			
			return result;
		}else{
			return new Double(value.toString());
		}
*/		
		

		
	}


}
