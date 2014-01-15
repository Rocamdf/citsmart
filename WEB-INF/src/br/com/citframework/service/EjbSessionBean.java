package br.com.citframework.service;

import java.rmi.RemoteException;

import javax.ejb.CreateException;
import javax.ejb.EJBException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

public class EjbSessionBean implements SessionBean{
	
	
	//private SessionContext ctx;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2749437615175738365L;

	public void ejbCreate() throws CreateException {
		
		
	}

	public void setSessionContext(SessionContext ctx) throws EJBException, RemoteException {
		
		//this.ctx = ctx;
		
		
	}

	public void ejbRemove() throws EJBException, RemoteException {
		
		
	}

	public void ejbActivate() throws EJBException, RemoteException {
		
		
	}

	public void ejbPassivate() throws EJBException, RemoteException {
		
		
	}

}
