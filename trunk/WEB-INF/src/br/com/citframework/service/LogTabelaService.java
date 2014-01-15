/**
 * 
 */
package br.com.citframework.service;

import java.rmi.RemoteException;

import br.com.citframework.dto.LogTabela;
import br.com.citframework.excecao.ServiceException;


/**
 * @author karem.ricarte
 *
 */
public interface LogTabelaService extends CrudServiceEjb2 {
	
	
	public LogTabela getLogByTabela(String nomeTabela)throws RemoteException,ServiceException;

}
