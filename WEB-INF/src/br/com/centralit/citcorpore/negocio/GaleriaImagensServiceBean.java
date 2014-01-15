package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.GaleriaImagensDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class GaleriaImagensServiceBean extends CrudServicePojoImpl implements GaleriaImagensService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new GaleriaImagensDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	
	public Collection findByCategoria(Integer idCategoria) throws Exception {
		GaleriaImagensDao galeriaImagensDao = new GaleriaImagensDao();
		return galeriaImagensDao.findByCategoria(idCategoria);
	}
	
	public Collection listOrderByCategoria() throws Exception {
		GaleriaImagensDao galeriaImagensDao = new GaleriaImagensDao();
		return galeriaImagensDao.listOrderByCategoria();		
	}

}