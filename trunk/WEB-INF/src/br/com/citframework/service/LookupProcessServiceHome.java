package br.com.citframework.service;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;

public interface LookupProcessServiceHome extends EJBHome {
	public LookupProcessServiceRemote create() throws RemoteException, CreateException;
}
