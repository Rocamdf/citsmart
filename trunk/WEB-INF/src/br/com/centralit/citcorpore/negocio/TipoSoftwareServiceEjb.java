package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.TipoSoftwareDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class TipoSoftwareServiceEjb extends CrudServicePojoImpl implements TipoSoftwareService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7181458797548518305L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new TipoSoftwareDAO();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		
	}

}
