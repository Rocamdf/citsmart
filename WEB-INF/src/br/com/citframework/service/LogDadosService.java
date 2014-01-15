/**
 * 
 */
package br.com.citframework.service;

import java.util.Collection;

import br.com.citframework.dto.LogDados;


/**
 * @author karem.ricarte
 *
 */
public interface LogDadosService extends CrudServiceEjb2{
	
	public Collection<LogDados> listAllLogs() throws Exception;
	
	public Collection<LogDados> listLogs(LogDados log) throws Exception;
	
	public Collection<LogDados> listNomeTabela() throws Exception;
}
