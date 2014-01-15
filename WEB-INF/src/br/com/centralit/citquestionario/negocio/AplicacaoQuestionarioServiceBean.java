package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.integracao.AplicacaoQuestionarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class AplicacaoQuestionarioServiceBean extends CrudServicePojoImpl implements AplicacaoQuestionarioService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new AplicacaoQuestionarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	public Collection listByIdQuestionarioAndAplicacao(Integer idQuestionario, String aplicacao) throws Exception{
		AplicacaoQuestionarioDao aplicDao = new AplicacaoQuestionarioDao();
		return aplicDao.listByIdQuestionarioAndAplicacao(idQuestionario, aplicacao);
	}
}
