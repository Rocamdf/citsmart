package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.PedidoPortalDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

@SuppressWarnings("rawtypes")
public class PedidoPortalServiceEjb implements PedidoPortalService {
	
	private PedidoPortalDAO pedidoPortalDAO = null;

	
	private static final long serialVersionUID = 1L;

	@Override
	public IDto create(IDto model) throws LogicException, RemoteException, ServiceException {
		pedidoPortalDAO = new PedidoPortalDAO();
		try {
			pedidoPortalDAO.create(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IDto restore(IDto model) throws LogicException, RemoteException, ServiceException {
		pedidoPortalDAO = new PedidoPortalDAO();
		try {
			pedidoPortalDAO.restore(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(IDto model) throws LogicException, RemoteException, ServiceException {
		pedidoPortalDAO = new PedidoPortalDAO();
		try {
			pedidoPortalDAO.update(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(IDto model) throws LogicException, RemoteException, ServiceException {
		pedidoPortalDAO = new PedidoPortalDAO();
		try {
			pedidoPortalDAO.delete(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Collection find(IDto obj) throws LogicException, RemoteException, ServiceException {
		pedidoPortalDAO = new PedidoPortalDAO();
		try {
			pedidoPortalDAO.find(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection list() throws LogicException, RemoteException, ServiceException {
		pedidoPortalDAO = new PedidoPortalDAO();
			try {
				return pedidoPortalDAO.list();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}

	@Override
	public void setUsuario(Usuario usr) throws RemoteException,
			ServiceException {
		// TODO Auto-generated method stub
		
	}


}
