package br.com.citframework.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Constantes {
	
	private static Properties props = null;
	private static final String fileName = "Constantes.properties";
	
	public static String SERVER_ADDRESS = "";
	
	public static InputStream inputStreamSettedInLoad = null;
	
	static{
		
		props = new Properties();
		ClassLoader load = Constantes.class.getClassLoader();

		InputStream is = load.getResourceAsStream(fileName);		
		if (is == null){
			is = ClassLoader.getSystemResourceAsStream(fileName);
		}
		if (is == null){
			is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
		}	
		
		//InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
		try {
			if (is == null){
				is = inputStreamSettedInLoad;
			}
			if (is == null){
				throw new Exception("Arquivo de recursos nao encontrado: " + fileName);
			}
			props.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getValue(String value) {
		if (value.equalsIgnoreCase("SERVER_ADDRESS")){
			if (SERVER_ADDRESS != null && !SERVER_ADDRESS.equalsIgnoreCase("")){
				return SERVER_ADDRESS;
			}
		}
//		if (props == null){
//			InputStream is = inputStreamSettedInLoad;
//			if (is != null){
//				try {
//					props.load(is);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}	
//			if (props == null){ //Se ainda continuar nulo.
//				return null;
//			}
//		}		
		
		if(props == null){
			return null;
		}
		return props.getProperty(value);
	}
	
	public static String getValueFromProperties(String value) {
//		if (props == null){
//			InputStream is = inputStreamSettedInLoad;
//			if (is != null){
//				try {
//					props.load(is);
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}	
//			if (props == null){ //Se ainda continuar nulo.
//				return null;
//			}
//		}		
		
		if(props == null){
			return null;
		}
		return props.getProperty(value);
	}
	
	
	

}
