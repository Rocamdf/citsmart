
package br.com.citframework.service;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

/**
 * @author karem.ricarte
 *
 */
public interface LogAcessoUsuarioServiceHome extends EJBHome {
	
	public LogAcessoUsuarioServiceRemote create() throws CreateException, RemoteException;

}
