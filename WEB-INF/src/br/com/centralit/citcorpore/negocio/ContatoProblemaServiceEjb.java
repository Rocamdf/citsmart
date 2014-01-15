package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ContatoProblemaDTO;
import br.com.centralit.citcorpore.integracao.ContatoProblemaDAO;
import br.com.centralit.citcorpore.integracao.ContatoRequisicaoMudancaDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class ContatoProblemaServiceEjb extends CrudServicePojoImpl implements ContatoProblemaService {
	/**
	 * @author geber.costa
	 */
	private static final long serialVersionUID = -8348097277497967610L;

	protected CrudDAO getDao() throws ServiceException {
		return new ContatoProblemaDAO();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	@Override
	public synchronized IDto create(IDto model) throws ServiceException, LogicException {
		return super.create(model);
	}

	@Override
	public ContatoProblemaDTO restoreContatosById(Integer idContatoProblema) throws Exception {
		ContatoProblemaDAO ContatoDao = new ContatoProblemaDAO();
		return ContatoDao.restoreById(idContatoProblema);
	}
	public ContatoProblemaDTO restoreContatosById(ContatoProblemaDTO obj) throws Exception {
		ContatoProblemaDAO ContatoDao = new ContatoProblemaDAO();
		return ContatoDao.restoreById(obj);
	}
}