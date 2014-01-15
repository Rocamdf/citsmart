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
public interface LogEstruturaServiceHome extends EJBHome {
	
	public LogEstruturaServiceRemote create() throws CreateException, RemoteException;

}
