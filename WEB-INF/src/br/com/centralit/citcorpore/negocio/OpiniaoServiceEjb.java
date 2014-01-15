package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.OpiniaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class OpiniaoServiceEjb extends CrudServicePojoImpl implements OpiniaoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new OpiniaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	@SuppressWarnings("rawtypes")
	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	@SuppressWarnings("rawtypes")
	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}
	
	public Collection findByTipoAndPeriodo(String tipo, Integer idContrato, Date dataInicial, Date dataFinal) throws Exception {
		OpiniaoDao opiniaoDao= new OpiniaoDao();
		return opiniaoDao.findByTipoAndPeriodo(tipo, idContrato, dataInicial, dataFinal);
	}

}
