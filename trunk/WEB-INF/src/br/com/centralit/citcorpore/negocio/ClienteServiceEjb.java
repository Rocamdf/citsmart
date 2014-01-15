package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import br.com.centralit.citcorpore.integracao.ClienteDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ClienteServiceEjb extends CrudServicePojoImpl implements ClienteService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new ClienteDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}
	
	public boolean existeDuplicacao(HashMap mapFields) throws LogicException, RemoteException, ServiceException {
		String nomeRazaoSocial = (String) mapFields.get("NOMERAZAOSOCIAL");
		boolean retorno = false;
		ClienteDao clienteDAO = new ClienteDao();
		try {
			retorno = clienteDAO.existeDuplicacao(nomeRazaoSocial);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}
}