package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.TimeSheetDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServiceEjb2Impl;
import br.com.citframework.service.CrudServicePojoImpl;

public class TimeSheetServiceEjb extends CrudServicePojoImpl implements TimeSheetService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new TimeSheetDao();
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
	public Collection findByPessoaAndPeriodo(Integer idEmpregado, Date dataInicio, Date dataFim) throws LogicException, RemoteException, ServiceException{
		TimeSheetDao timeSheetDao = (TimeSheetDao)getDao();
		try {
			return timeSheetDao.findByPessoaAndPeriodo(idEmpregado, dataInicio, dataFim);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
	public Collection findByDemanda(Integer idDemanda) throws LogicException, RemoteException, ServiceException{
		TimeSheetDao timeSheetDao = (TimeSheetDao)getDao();
		try {
			return timeSheetDao.findByDemanda(idDemanda);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceException(e);
		}
	}
	
}
