package br.com.citframework.service;

import java.rmi.RemoteException;

import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.ServiceException;

public interface IService{
	
	
	public void setUsuario(Usuario usr) throws RemoteException, ServiceException;
	
	

}
