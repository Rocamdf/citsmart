package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ProblemaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ProblemaMudancaDTO;
import br.com.centralit.citcorpore.integracao.ProblemaItemConfiguracaoDAO;
import br.com.centralit.citcorpore.integracao.ProblemaMudancaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class ProblemaMudancaServiceEjb extends CrudServicePojoImpl implements ProblemaMudancaService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new ProblemaMudancaDAO();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	public Collection findByIdProblemaMudanca(Integer parm) throws Exception {
		ProblemaMudancaDAO dao = new ProblemaMudancaDAO();
		try {
			return dao.findByIdProblemaMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblemaMudanca(Integer parm) throws Exception {
		ProblemaMudancaDAO dao = new ProblemaMudancaDAO();
		try {
			dao.deleteByIdProblemaMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdProblema(Integer parm) throws Exception {
		ProblemaMudancaDAO dao = new ProblemaMudancaDAO();
		try {
			return dao.findByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdProblema(Integer parm) throws Exception {
		ProblemaMudancaDAO dao = new ProblemaMudancaDAO();
		try {
			dao.deleteByIdProblema(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdRequisicaoMudanca(Integer parm) throws Exception {
		ProblemaMudancaDAO dao = new ProblemaMudancaDAO();
		try {
			return dao.findByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdRequisicaoMudanca(Integer parm) throws Exception {
		ProblemaMudancaDAO dao = new ProblemaMudancaDAO();
		try {
			dao.deleteByIdRequisicaoMudanca(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public ProblemaMudancaDTO restoreByIdProblema(Integer idProblema) throws Exception {
		try {
			ProblemaMudancaDAO dao = new ProblemaMudancaDAO();
			return dao.restoreByIdProblema(idProblema);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
