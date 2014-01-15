package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoItemConfigRelDTO;
import br.com.centralit.citcorpore.integracao.EventoItemConfigRelDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class EventoItemConfigRelServiceEjb extends CrudServicePojoImpl
		implements EventoItemConfigRelService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new EventoItemConfigRelDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {

	}

	@Override
	protected void validaFind(Object obj) throws Exception {

	}

	@Override
	public Collection<EventoItemConfigRelDTO> listByEvento(Integer idEvento)
			throws Exception {
		try {
			EventoItemConfigRelDao dao = (EventoItemConfigRelDao) getDao();
		    return dao.listByEvento(idEvento);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

}
