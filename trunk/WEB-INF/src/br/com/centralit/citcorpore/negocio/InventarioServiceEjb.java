package br.com.centralit.citcorpore.negocio;

import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;


/**
 * @author Maycon.Fernandes
 *
 */
public class InventarioServiceEjb extends CrudServicePojoImpl implements InventarioService {

    /* (non-Javadoc)
     * @see br.com.citframework.service.CrudServicePojoImpl#getDao()
     */
    @Override
    protected CrudDAO getDao() throws ServiceException {
	// TODO Auto-generated method stub
	return null;
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
