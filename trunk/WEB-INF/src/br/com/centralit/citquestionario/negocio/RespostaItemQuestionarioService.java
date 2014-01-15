package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServicePojo;

public interface RespostaItemQuestionarioService extends CrudServicePojo {
	public void deleteByIdQuestaoAndIdentificadorResposta(Integer idQuestaoQuestionario, Integer idIdentificadorResposta) throws Exception;
	public Collection listByIdIdentificadorAndIdQuestao(Integer idIdentificadorResposta, Integer idQuestaoQuestionario) throws Exception;
	public Collection getRespostasOpcoesByIdRespostaItemQuestionario(Integer idRespostaItemQuestionario) throws Exception;
	public void deleteByIdIdentificadorResposta(
	        final RespostaItemQuestionarioDTO resposta) 
	throws ServiceException, LogicException;
	public void deleteByIdIdentificadorResposta(
            final RespostaItemQuestionarioDTO resposta, 
            final TransactionControler tc) throws Exception;
}