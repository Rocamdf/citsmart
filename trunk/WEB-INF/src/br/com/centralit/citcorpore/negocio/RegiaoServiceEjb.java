package br.com.centralit.citcorpore.negocio;


import br.com.centralit.citcorpore.bean.RegiaoDTO;

import br.com.centralit.citcorpore.integracao.RegiaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class RegiaoServiceEjb extends CrudServicePojoImpl implements RegiaoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		
		return new RegiaoDao();
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
	public RegiaoDTO listByIdRegiao(RegiaoDTO obj) throws Exception {
		try {
			RegiaoDao dao = new RegiaoDao();   
		    return dao.listByIdRegiao(obj);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

}
