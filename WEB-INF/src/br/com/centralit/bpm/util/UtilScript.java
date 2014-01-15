package br.com.centralit.bpm.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilStrings;

public class UtilScript {

	public static Object executaScript(String nome, String script, Map<String, Object> objetos) throws Exception {
		Object object = null;
		HashMap<String, Object> params = getParams(objetos);
		if (script != null) {
			script = substituiParametros(script, params);
			Context cx = Context.enter();
			Scriptable scope = cx.initStandardObjects();
			scope.put("params", scope, params);
			try {
				object = cx.evaluateString(scope, script, "script", 1, null);
			} catch (Exception e) {
				String msg = e.getLocalizedMessage() + "\n" + script;
				throw new Exception(msg);
			}
		}
		return object;
	}
	
	public static HashMap<String, Object> getParams(Map<String, Object> objetos) throws Exception {
		HashMap<String, Object> params = new HashMap();
		for(String key: objetos.keySet()) {
			Object objeto = objetos.get(key);
			params.put(key, objeto);
			try{
				List lstGets = Reflexao.findGets(objeto);
				for (int i = 0; i < lstGets.size(); i++) {
					String propriedade = UtilStrings.convertePrimeiraLetra((String) lstGets.get(i), "L");
					try{
						Object value = Reflexao.getPropertyValue(objeto, propriedade);
						if (value != null) {
							String id = key+"."+UtilStrings.convertePrimeiraLetra(propriedade, "L");
							params.put(id, value);
						}
					}catch (Exception e) {
						//System.out.println("######### Erro na recuperação da propriedade "+propriedade);
					}
				}
			}catch (Exception e) {
			}
		}
		return params;
	}
	
	public static String substituiParametros(String str, HashMap<String, Object> params) throws Exception {
		if (str != null) {
			for (String key : params.keySet())
				str = str.replaceAll("\\#\\{" + key + "\\}", "params.get(\"" + key + "\")");
		}
		return str;
	}
}
