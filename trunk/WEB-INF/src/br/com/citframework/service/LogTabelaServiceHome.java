/**
 * 
 */
package br.com.citframework.service;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * @author karem.ricarte
 *
 */
public interface LogTabelaServiceHome extends EJBHome {
	
	public LogTabelaServiceRemote create() throws CreateException, RemoteException;

}
