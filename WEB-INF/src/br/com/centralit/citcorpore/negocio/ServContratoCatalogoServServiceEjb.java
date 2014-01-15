package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.ServContratoCatalogoServDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ServContratoCatalogoServServiceEjb extends CrudServicePojoImpl implements ServContratoCatalogoServService {
	
	private static final long serialVersionUID = -2253183314661440900L;
    
	protected CrudDAO getDao() throws ServiceException {
		return new ServContratoCatalogoServDao();
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
