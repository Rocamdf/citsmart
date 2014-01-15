package br.com.centralit.citcorpore.negocio;



import java.util.Collection;

import br.com.centralit.citcorpore.bean.UfDTO;
import br.com.centralit.citcorpore.integracao.UfDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class UfServiceEjb extends CrudServicePojoImpl implements UfService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new UfDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	@Override
	public Collection<UfDTO> listByIdRegioes(UfDTO obj) throws Exception {
		try {
			UfDao dao = new UfDao();   
		    return (Collection<UfDTO>) dao.listByIdRegioes(obj);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

	@Override
	public Collection<UfDTO> listByIdPais(UfDTO obj) throws Exception {
		try {
			UfDao dao = new UfDao();   
		    return (Collection<UfDTO>) dao.listByIdPais(obj);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

	

}
