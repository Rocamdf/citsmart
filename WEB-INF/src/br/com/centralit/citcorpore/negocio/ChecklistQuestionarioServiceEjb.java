package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ChecklistQuestionarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ChecklistQuestionarioServiceEjb extends CrudServicePojoImpl implements ChecklistQuestionarioService  {

	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ChecklistQuestionarioDao();
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


}
