package br.com.centralit.citquestionario.negocio;

import br.com.centralit.citquestionario.integracao.ListagemDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ListagemServiceBean extends CrudServicePojoImpl implements ListagemService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	protected CrudDAO getDao() throws ServiceException {
		
		return new ListagemDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
}
