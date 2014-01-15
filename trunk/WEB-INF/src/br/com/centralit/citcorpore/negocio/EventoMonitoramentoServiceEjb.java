/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.integracao.EventoMonitoramentoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author Vadoilo Damasceno
 * 
 */
public class EventoMonitoramentoServiceEjb extends CrudServicePojoImpl implements EventoMonitoramentoService {

	private static final long serialVersionUID = -4755868266488086524L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new EventoMonitoramentoDAO();
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
