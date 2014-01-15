package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.integracao.PortalDao;
import br.com.centralit.citcorpore.integracao.PostDAO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Flávio.santana
 *
 */
public class PostServiceEjb extends CrudServicePojoImpl implements PostService {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new PostDAO();
    }

    protected void validaCreate(Object arg0) throws Exception {
    }

    protected void validaDelete(Object arg0) throws Exception {
    }

    protected void validaUpdate(Object arg0) throws Exception {
    }

    protected void validaFind(Object obj) throws Exception {
    }

    @SuppressWarnings("rawtypes")
	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
	return null;
    }

    @SuppressWarnings("rawtypes")
	public Collection list(String ordenacao) throws LogicException, RemoteException,
	    ServiceException {
	return null;
    }

	@Override
	public Collection listNotNull() throws Exception {
		return this.getPostDao().listNotNull();
	}
	
	 public PostDAO getPostDao() throws ServiceException {
		 return (PostDAO) getDao();
    }
	 


}
