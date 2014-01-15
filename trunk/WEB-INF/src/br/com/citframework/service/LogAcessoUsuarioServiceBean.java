/**
 * 
 */
package br.com.citframework.service;

import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.LogAcessoUsuarioDao;


/**
 * @author karem.ricarte
 *
 */
public class LogAcessoUsuarioServiceBean extends CrudServiceEjb2Impl implements LogAcessoUsuarioService {

	
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new LogAcessoUsuarioDao(usuario);
	}

	protected void validaCreate(Object arg0) throws Exception {
		
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
		
	}

}
