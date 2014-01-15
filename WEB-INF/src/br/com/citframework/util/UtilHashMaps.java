package br.com.citframework.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import br.com.citframework.excecao.LogicException;

public class UtilHashMaps {
	public static String generateString(HashMap map) throws LogicException {
		String retorno = "";
		
		Set set = map.entrySet(); 
		Iterator i = set.iterator(); 
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			retorno = retorno + me.getKey() + ": " + me.getValue() + "\n"; 
		} 
		
		return retorno;
	}
}
