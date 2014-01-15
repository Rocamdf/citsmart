package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.HistoricoExecucaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceEjb2Impl;
import br.com.citframework.service.CrudServicePojoImpl;

public class HistoricoExecucaoServiceEjb extends CrudServicePojoImpl implements HistoricoExecucaoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new HistoricoExecucaoDao();
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
	public Collection findByDemanda(Integer idDemanda) throws LogicException, RemoteException, ServiceException{
		HistoricoExecucaoDao historicoExecucaoDao = (HistoricoExecucaoDao)getDao();
		try {
			return historicoExecucaoDao.findByDemanda(idDemanda);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}	
}
