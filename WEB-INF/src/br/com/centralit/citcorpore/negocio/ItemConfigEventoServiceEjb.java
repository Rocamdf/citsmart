package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.centralit.citcorpore.integracao.ItemConfigEventoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ItemConfigEventoServiceEjb extends CrudServicePojoImpl implements ItemConfigEventoService {

    private static final long serialVersionUID = 1L;

    protected CrudDAO getDao() throws ServiceException {
	return new ItemConfigEventoDao();
    }

    protected void validaCreate(Object obj) throws Exception {
    }

    protected void validaDelete(Object obj) throws Exception {
    }

    protected void validaUpdate(Object obj) throws Exception {
    }

    protected void validaFind(Object obj) throws Exception {
    }

    public Collection<ItemConfigEventoDTO> listByIdEvento(Integer idEvento) throws ServiceException, RemoteException {
	try {
	    ItemConfigEventoDao dao = (ItemConfigEventoDao) getDao();
	    return dao.listByIdEvento(idEvento);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public Collection<ItemConfigEventoDTO> verificarDataHoraEvento() throws ServiceException, RemoteException {
	try {
	    ItemConfigEventoDao dao = (ItemConfigEventoDao) getDao();
	    return dao.verificaDataHoraEvento();
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

}
