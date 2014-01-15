package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExcecaoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.ExcecaoEmpregadoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ExcecaoEmpregadoServiceEjb extends CrudServicePojoImpl implements ExcecaoEmpregadoService {

	protected CrudDAO getDao() throws ServiceException {
		return new ExcecaoEmpregadoDao();
	}

	protected void validaCreate(Object obj) throws Exception {
	}

	protected void validaDelete(Object obj) throws Exception {
	}

	protected void validaUpdate(Object obj) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}
	
	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdGrupo(Integer idEvento, Integer idGrupo) throws ServiceException, RemoteException {
		try {
		    ExcecaoEmpregadoDao dao = (ExcecaoEmpregadoDao) getDao();
		    return dao.listByIdEventoIdGrupo(idEvento, idGrupo);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdUnidade(Integer idEvento, Integer idUnidade) throws ServiceException, RemoteException {
		try {
		    ExcecaoEmpregadoDao dao = (ExcecaoEmpregadoDao) getDao();
		    return dao.listByIdEventoIdUnidade(idEvento, idUnidade);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
