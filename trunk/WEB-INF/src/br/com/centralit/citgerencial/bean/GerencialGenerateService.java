package br.com.centralit.citgerencial.bean;

import java.text.ParseException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public abstract class GerencialGenerateService {
	/**
	 * O retorno deste metodo deve ser uma Lista onde cada linha da lista é uma array de Objetos.
	 * 		Exemplo: Object[] row
	 * @param parametersValues
	 * @param paramtersDefinition
	 * @return
	 * @throws ParseException 
	 */
	public abstract List execute(HashMap parametersValues, Collection paramtersDefinition) throws ParseException;
}
