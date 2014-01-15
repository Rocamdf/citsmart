package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

public class RelEscalonamentoMudancaServeEjb implements RelEscalonamentoMudancaService{

	@Override
	public IDto create(IDto model) throws LogicException, RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IDto restore(IDto model) throws LogicException, RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(IDto model) throws LogicException, RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(IDto model) throws LogicException, RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection find(IDto obj) throws LogicException, RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection list() throws LogicException, RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setUsuario(Usuario usr) throws RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		
	}

}
