/**
 * CentralIT - CITSMart
 */
package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.PalavraGemeaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class PalavraGemeaServiceEjb extends CrudServicePojoImpl implements PalavraGemeaService {

	private static final long serialVersionUID = -1464314714584083462L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new PalavraGemeaDAO();
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
