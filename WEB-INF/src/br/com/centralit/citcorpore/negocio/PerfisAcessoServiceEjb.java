package br.com.centralit.citcorpore.negocio;

import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
import br.com.centralit.citcorpore.negocio.PerfisAcessoService;

class PerfisAcessoServiceEjb extends CrudServicePojoImpl implements PerfisAcessoService {

    @Override
    protected CrudDAO getDao() throws ServiceException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    protected void validaCreate(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void validaDelete(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void validaFind(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

    @Override
    protected void validaUpdate(Object arg0) throws Exception {
	// TODO Auto-generated method stub
	
    }

}
