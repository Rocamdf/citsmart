package br.com.centralit.citcorpore.metainfo.util;

import java.util.HashMap;

public class HashMapUtil {
	public static HashMap map;
	public static String getFieldInHash(String name){
		if (map == null){
			return null;
		}
		if (name == null){
			return null;
		}		
		return (String) map.get(name.toUpperCase());
	}	
}
