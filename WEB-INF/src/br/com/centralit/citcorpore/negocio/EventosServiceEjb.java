package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.EventosDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class EventosServiceEjb extends CrudServicePojoImpl implements EventosService {

	protected CrudDAO getDao() throws ServiceException {
		return new EventosDao();
	}

	protected void validaCreate(Object obj) throws Exception {
	}

	protected void validaDelete(Object obj) throws Exception {
	}

	protected void validaUpdate(Object obj) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}
	
}
