package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.LocalidadeItemConfiguracaoDTO;

import br.com.centralit.citcorpore.integracao.LocalidadeItemConfiguracaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class LocalidadeItemConfiguracaoServiceEjb  extends CrudServicePojoImpl implements LocalidadeItemConfiguracaoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new LocalidadeItemConfiguracaoDao();
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
	public LocalidadeItemConfiguracaoDTO listByIdRegiao(
			LocalidadeItemConfiguracaoDTO obj) throws Exception {
		try {
		    LocalidadeItemConfiguracaoDao dao = (LocalidadeItemConfiguracaoDao) getDao();
		    return dao.listByIdRegiao(obj);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

	@Override
	public LocalidadeItemConfiguracaoDTO listByIdUf(
			LocalidadeItemConfiguracaoDTO obj) throws Exception {
		try {
		    LocalidadeItemConfiguracaoDao dao = (LocalidadeItemConfiguracaoDao) getDao();
		    return dao.listByIdUf(obj);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}

	

}
