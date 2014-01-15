package br.com.citframework.service;

import java.rmi.RemoteException;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

public interface LookupProcessService extends IService {
	public Collection process(Object obj, HttpServletRequest request) throws RemoteException,LogicException,ServiceException;
}
