package br.com.centralit.citcorpore.metainfo.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class JSONUtil {
	public static HashMap<String, Object> convertJsonToMap (String strData, boolean convKeyToUpperCase){
		if (strData == null){
			return null;
		}
		JsonParser parserJson = new JsonParser();
	    JsonElement element = (JsonElement) parserJson.parse(strData);
	    if (JsonObject.class.isInstance(element)){
	    	JsonObject object = (JsonObject)element;
		    if (object != null){
			    Set<Map.Entry<String, JsonElement>> set = object.entrySet();
			    Iterator<Map.Entry<String, JsonElement>> iterator = set.iterator();
			    HashMap<String, Object> map = new HashMap<String, Object>();
			    while (iterator.hasNext()) {
			        Map.Entry<String, JsonElement> entry = iterator.next();
			        String key = entry.getKey();
			        JsonElement value = entry.getValue();
		        	if (convKeyToUpperCase){
		        		if (key != null){
		        			key = key.toUpperCase();
		        		}
		        	}			        
			        if (!value.isJsonPrimitive()) {
			        	if (value.isJsonArray()){
			        		map.put(key, JSONUtil.convertJsonArrayToCollection((JsonArray)value, convKeyToUpperCase));
			        	}else{
			        		map.put(key, JSONUtil.convertJsonToMap(value.toString(), convKeyToUpperCase));
			        	}
			        } else {
			            map.put(key, value.getAsString());
			        }
			    }
			    return map;
		    }	    	
	    }
	    if (JsonArray.class.isInstance(element)){
	    	JsonArray array = (JsonArray)element;
	    	HashMap<String, Object> map = new HashMap<String, Object>();
	    	map.put("ARRAY", JSONUtil.convertJsonArrayToCollection(array, convKeyToUpperCase));
	    	return map;
	    }
	    return null;
	}
	public static Collection convertJsonArrayToCollection (JsonArray array, boolean convUpperCase){
    	if (array != null){
		    Iterator iterator = array.iterator();
		    Collection col = new ArrayList();
		    while (iterator.hasNext()) {
		    	Object obj = (Object)iterator.next();
		    	col.add(JSONUtil.convertJsonToMap(obj.toString(), convUpperCase));
		    }
		    return col;
    	}
    	return null;
	}
}
