package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.integracao.OpcaoRespostaQuestionarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class OpcaoRespostaQuestionarioServiceBean extends CrudServicePojoImpl implements OpcaoRespostaQuestionarioService{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		
		return new OpcaoRespostaQuestionarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
		
	}

	protected void validaDelete(Object arg0) throws Exception {
		
	}

	protected void validaFind(Object arg0) throws Exception {
		
	}

	protected void validaUpdate(Object arg0) throws Exception {
		
	}
	
	public Collection listByIdQuestaoQuestionario(Integer idQuestaoQuestionario) throws Exception{
		OpcaoRespostaQuestionarioDao opcRespDao = new OpcaoRespostaQuestionarioDao();
		return opcRespDao.listByIdQuestaoQuestionario(idQuestaoQuestionario);
	}

}
