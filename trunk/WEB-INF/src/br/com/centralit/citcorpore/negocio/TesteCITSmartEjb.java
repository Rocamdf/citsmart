package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.TesteCITSmartDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class TesteCITSmartEjb extends CrudServicePojoImpl implements
	TesteCITSMartService {
	
    protected CrudDAO getDao() throws ServiceException {
	return new TesteCITSmartDao();
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

