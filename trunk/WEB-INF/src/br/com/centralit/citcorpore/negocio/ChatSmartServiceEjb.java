package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ChatSmartDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author CentralIT
 * 
 */
/**
 * @author Centralit
 * 
 */
@SuppressWarnings("rawtypes")
public class ChatSmartServiceEjb extends CrudServicePojoImpl implements ChatSmartService {

	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ChatSmartDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

}
