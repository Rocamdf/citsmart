package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoGrupoDTO;
import br.com.centralit.citcorpore.integracao.EventoGrupoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class EventoGrupoServiceEjb extends CrudServicePojoImpl implements
		EventoGrupoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new EventoGrupoDao();
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
	public Collection<EventoGrupoDTO> listByEvento(Integer idEvento)
			throws Exception {
		try {
			EventoGrupoDao dao = (EventoGrupoDao) getDao();
			return dao.listByEvento(idEvento);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

}
