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
public interface LogDadosServiceHome extends EJBHome {
	
	public LogDadosServiceRemote create() throws CreateException, RemoteException;

}
