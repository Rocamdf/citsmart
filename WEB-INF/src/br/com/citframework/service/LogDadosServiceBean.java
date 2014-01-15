
package br.com.citframework.service;

import java.util.Collection;

import br.com.citframework.dto.LogDados;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.integracao.LogDadosDao;

/**
 * @author karem.ricarte
 *
 */
public class LogDadosServiceBean extends CrudServiceEjb2Impl implements LogDadosService{

	
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new LogDadosDao(usuario);
	}

	protected void validaCreate(Object arg0) throws Exception {
		
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
		
	}

	@Override
	public Collection<LogDados> listAllLogs() throws Exception {
		LogDadosDao dao = new LogDadosDao(usuario);
		return dao.listAllLogs();
	}

	@Override
	public Collection<LogDados> listLogs(LogDados log) throws Exception {
		LogDadosDao dao = new LogDadosDao(usuario);
		return dao.listLogs(log);
	}
	
	@Override
	public Collection<LogDados> listNomeTabela() throws Exception {
		LogDadosDao dao = new LogDadosDao(usuario);
		return dao.listNomeTabela();
	}

}
