package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.centralit.citcorpore.integracao.CidadesDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class CidadesServiceEjb  extends CrudServicePojoImpl implements CidadesService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new CidadesDao();
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

	@Override
	public Collection<CidadesDTO> listByIdCidades(CidadesDTO obj)
			throws Exception {
		try {
			CidadesDao dao = new CidadesDao();   
		    return (Collection<CidadesDTO>) dao.listByIdCidades(obj);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

}
