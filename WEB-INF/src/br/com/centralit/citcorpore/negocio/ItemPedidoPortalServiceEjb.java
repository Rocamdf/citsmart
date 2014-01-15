package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ItemPedidoPortalDAO;
import br.com.citframework.dto.IDto;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

@SuppressWarnings("rawtypes")
public class ItemPedidoPortalServiceEjb implements ItemPedidoPortalService {
	
	private ItemPedidoPortalDAO itemPedidoPortalDAO = null;

	
	private static final long serialVersionUID = 1L;

	@Override
	public IDto create(IDto model) throws LogicException, RemoteException, ServiceException {
		itemPedidoPortalDAO = new ItemPedidoPortalDAO();
		try {
			itemPedidoPortalDAO.create(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public IDto restore(IDto model) throws LogicException, RemoteException, ServiceException {
		itemPedidoPortalDAO = new ItemPedidoPortalDAO();
		try {
			itemPedidoPortalDAO.restore(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void update(IDto model) throws LogicException, RemoteException, ServiceException {
		itemPedidoPortalDAO = new ItemPedidoPortalDAO();
		try {
			itemPedidoPortalDAO.update(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void delete(IDto model) throws LogicException, RemoteException, ServiceException {
		itemPedidoPortalDAO = new ItemPedidoPortalDAO();
		try {
			itemPedidoPortalDAO.delete(model);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Collection find(IDto obj) throws LogicException, RemoteException, ServiceException {
		itemPedidoPortalDAO = new ItemPedidoPortalDAO();
		try {
			itemPedidoPortalDAO.find(obj);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Collection list() throws LogicException, RemoteException, ServiceException {
		itemPedidoPortalDAO = new ItemPedidoPortalDAO();
			try {
				return itemPedidoPortalDAO.list();
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
