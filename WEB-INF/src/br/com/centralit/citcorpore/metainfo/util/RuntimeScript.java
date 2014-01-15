package br.com.centralit.citcorpore.metainfo.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import br.com.centralit.citajax.reflexao.CitAjaxReflexao;

public class RuntimeScript {
	public Object executeClass(String classToExecute, Object parms[]) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class classe = MetaUtil.class.forName(classToExecute);
		Object objeto = classe.newInstance();
		
		Method mtd = CitAjaxReflexao.findMethod("execute", objeto);
		Object parmReals[] = new Object[3];
		
		Object retorno = mtd.invoke(objeto, parms);
		return retorno;
	}
}
