/*
 * Created on 12/01/2006
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
package br.com.citframework.util;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConversionException;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimeConverter;



/**
 * @author ney
 *

 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ConverterUtils {

     public static void copyFromStringObject(Object strObject, Object dest) throws Exception{
     	if(strObject==null || dest ==null){
     		return;
     	}
     	
     	  HashMap map = mapProperties(strObject,dest);
		  ConvertUtils.register(new SqlDateConverter(),Date.class);
		  ConvertUtils.register(new SqlTimeConverter(),Time.class);
		  ConvertUtils.register(new DoubleConverter(),Double.class); 
		  ConvertUtils.register(new IntegerConverter(), Integer.class);
		  ConvertUtils.register(new TimeConverter(), Time.class);
		  ConvertUtils.register(new Big_DecimalConverter(), BigDecimal.class);
		  ConvertUtils.register(new LongConverter(), Long.class);
     	  BeanUtils.populate(dest,map);
     	    
	
     }

     public static void copyToStringObject(Object strObject, Object src) throws Exception{
     	if(strObject==null || src ==null){
     		return;
     	}

     	
     	List lista = Reflexao.getCommonPropertyNames(strObject,src);
		Iterator it = lista.iterator();
		
		while(it.hasNext()){
			String atribName = it.next().toString();
			Object obj = Reflexao.getPropertyValue(src,atribName);
			if(obj!=null){
				Reflexao.setPropertyValue(strObject,atribName,obj.toString());
			}
		}

     	    
	
     }
     
     
     
     
     
     private static HashMap mapProperties(Object strObject, Object dest) throws Exception{
			
     	    List lista = Reflexao.getCommonPropertyNames(strObject,dest);
			Iterator it = lista.iterator();
			HashMap map = new HashMap();
			while(it.hasNext()){
				String atribName = it.next().toString();
				Object obj = Reflexao.getPropertyValue(strObject,atribName);
				if(obj!=null){
					map.put(atribName,obj.toString());
					//System.out.println("-------- "+atribName+" = "+obj);
				}
			}
			return map;
     }
     
     //aplica a mascara com # em String com valores numéricos
     public static String aplicaMascara(String numero, String mascara){
         
         
         if(possuiMascara(numero)){
              return numero;
          }
          numero = ajustaValor(numero,mascara,'#');
	    	  String result ="";
	    	  int j=0;
	    	  for(int i=0;i<numero.length();i++){
	    	      if(mascara.charAt(j) == '#'){
	    	          result+=numero.charAt(i);
	    	          j++;
	    	          
	    	      }else{
	    	          char tmp = mascara.charAt(j);
	    	          while(tmp!='#'){
	    	              result+=mascara.charAt(j);
	    	              j++;
	    	              tmp = mascara.charAt(j);
	    	          }
	    	          result+=numero.charAt(i);
	    	          j++;
	    	      }   
	    	  }
	    	  return result;
     }
     
     //Retira mascara de String com valores numéricos
     public static String retiraMascara(String valor){
          if(valor==null || valor.length()==0){
              return valor;
          }
	    	  String resp="";
	    	  for(int i=0;i<valor.length();i++){
	    	      char tmp  = valor.charAt(i);
	    	      if((tmp=='0')||(tmp=='1')||(tmp=='2')
							||(tmp=='3')||(tmp=='4')||(tmp=='5')
							||(tmp=='6')||(tmp=='7')||(tmp=='8')
							||(tmp=='9')){
	    	          resp+=tmp;
	    	      }      
	    	  }
	    	  return resp;
     }
     
     //Verifica se String com valores numericos possui mascara
     public static boolean possuiMascara(String valor){
         if(valor==null || valor.length()==0){
             return false;
         }
         
	    	  for(int i=0;i<valor.length();i++){
	    	      char tmp  = valor.charAt(i);
	    	      if((tmp!='0')&&(tmp!='1')&&(tmp!='2')
							&&(tmp!='3')&&(tmp!='4')&&(tmp!='5')
							&&(tmp!='6')&&(tmp!='7')&&(tmp!='8')
							&&(tmp!='9')&&(tmp!='.')&&(tmp!=',')&&(tmp!='E')){
	    	          return true;
	    	      }      
	    	  }
	    	  return false;
         
     }
     
   	//Transforma uma String Com mascara em double
     public static final Double strFormatToDouble(String value){
    	    System.out.println("###########################Converssor");
   			if(value==null || value.toString().length()==0){
   			 		return null;
   				}
   				if(ConverterUtils.possuiMascara(value.toString())){
   					System.out.println("-----------Possui Mascara");
   				    String tmp = ConverterUtils.retiraMascara(value.toString());
   				    System.out.println("-----------Valor sem mascara"+tmp);
   				    if(tmp==null || tmp.trim().length()==0){
   				    	return null;
   				    }
   				    Double result = new Double(tmp);
   				    System.out.println("-----------Valor convertido"+result);
   				    return result;
   				    
   				}
   				System.out.println("-----------Não possui mascara");
   				
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

   		}
     
     
    	//Transforma uma String Com mascara em BigDecimal
     public static final Long strFormatToLong(String value){
    	    System.out.println("###########################Converssor");
   			if(value==null || value.toString().length()==0){
   			 		return null;
   				}
   				if(ConverterUtils.possuiMascara(value.toString())){
   					System.out.println("-----------Possui Mascara");
   				    String tmp = ConverterUtils.retiraMascara(value.toString());
   				    System.out.println("-----------Valor sem mascara"+tmp);
   				    if(tmp==null || tmp.trim().length()==0){
   				    	return null;
   				    }
   				    Long result = new Long(tmp);
   				    System.out.println("-----------Valor convertido"+result);
   				    return result;
   				    
   				}
   				System.out.println("-----------Não possui mascara");
   				
   				if(value.toString().indexOf(",")>-1){
   					StringBuffer str = new StringBuffer(value.toString());
   					
   					for(int i=0;i<str.length();i++){
   						if(str.charAt(i)=='.')
   						{
   							str = str.deleteCharAt(i);
   						}
   					}
   					
   					String tmp = str.toString().replace(',','.');
   					
   					Long result;
   					try {
   						result = new Long(tmp);
   					} catch (NumberFormatException e) {
   						
   						throw new ConversionException("Formato numérico inválido. "+value);
   					}
   					
   					return result;
   				}else{
   					return new Long(value.toString());
   				}

   		}
     
     
 	//Transforma uma String Com mascara em BigDecimal
     public static final BigDecimal strFormatToBigDecimal(String value){
    	    System.out.println("###########################Converssor");
   			if(value==null || value.toString().length()==0){
   			 		return null;
   				}
   				if(ConverterUtils.possuiMascara(value.toString())){
   					System.out.println("-----------Possui Mascara");
   				    String tmp = ConverterUtils.retiraMascara(value.toString());
   				    System.out.println("-----------Valor sem mascara"+tmp);
   				    if(tmp==null || tmp.trim().length()==0){
   				    	return null;
   				    }
   				    BigDecimal result = new BigDecimal(tmp);
   				    System.out.println("-----------Valor convertido"+result);
   				    return result;
   				    
   				}
   				System.out.println("-----------Não possui mascara");
   				
   				if(value.toString().indexOf(",")>-1){
   					StringBuffer str = new StringBuffer(value.toString());
   					
   					for(int i=0;i<str.length();i++){
   						if(str.charAt(i)=='.')
   						{
   							str = str.deleteCharAt(i);
   						}
   					}
   					
   					String tmp = str.toString().replace(',','.');
   					
   					BigDecimal result;
   					try {
   						result = new BigDecimal(tmp);
   					} catch (NumberFormatException e) {
   						
   						throw new ConversionException("Formato numérico inválido. "+value);
   					}
   					
   					return result;
   				}else{
   					return new BigDecimal(value.toString());
   				}

   		}
     
     
   		
   	  //Retorna a quantidade de marcadores existentes na mascara
   		private static int contaMarcador(String mascara, char marcador){
   		    
   		    int result = 0;
   		    for(int i=0;i<mascara.length();i++){
   		        if(mascara.charAt(i)==marcador){
   		            result++;
   		        }
   		    }
   		    return result;
   		    
   		}
   		
   		
   		//Insere 0 a direita do valor a ser mascarado
   		private static String ajustaValor(String numero, String mascara, char marcador){
   		    
   		    if(contaMarcador(mascara,marcador)>numero.length()){
   		        int sobra = contaMarcador(mascara,marcador) - numero.length(); 
   		        String result = numero;
   		        for(int i=0;i<sobra;i++){
   		            result="0"+result;
   		        }
   		        return result;
   		    }
   		    return numero;
   		    
   		}
   		
   		
   		
     
     
     
}
