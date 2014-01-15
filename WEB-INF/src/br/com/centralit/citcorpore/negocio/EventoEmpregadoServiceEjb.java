package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoEmpregadoDTO;
import br.com.centralit.citcorpore.integracao.EventoEmpregadoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class EventoEmpregadoServiceEjb extends CrudServicePojoImpl implements EventoEmpregadoService {

    private static final long serialVersionUID = 1L;

    protected CrudDAO getDao() throws ServiceException {
	return new EventoEmpregadoDao();
    }

    protected void validaCreate(Object obj) throws Exception {
    }

    protected void validaDelete(Object obj) throws Exception {
    }

    protected void validaUpdate(Object obj) throws Exception {
    }

    protected void validaFind(Object obj) throws Exception {
    }

    public Collection<EventoEmpregadoDTO> listByIdEvento(Integer idEvento) throws ServiceException, RemoteException {
	try {
	    EventoEmpregadoDao dao = (EventoEmpregadoDao) getDao();
	    return dao.listByIdEvento(idEvento);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public Collection<EventoEmpregadoDTO> listByIdEventoGrupo(Integer idEvento) throws ServiceException, RemoteException {
	try {
	    EventoEmpregadoDao dao = (EventoEmpregadoDao) getDao();
	    return dao.listByIdEventoGrupo(idEvento);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public Collection<EventoEmpregadoDTO> listByIdEventoUnidade(Integer idEvento) throws ServiceException, RemoteException {
	try {
	    EventoEmpregadoDao dao = (EventoEmpregadoDao) getDao();
	    return dao.listByIdEventoUnidade(idEvento);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public Collection<EventoEmpregadoDTO> listByIdEventoEmpregado(Integer idEvento) throws ServiceException, RemoteException {
	try {
	    EventoEmpregadoDao dao = (EventoEmpregadoDao) getDao();
	    return dao.listByIdEventoEmpregado(idEvento);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

}
