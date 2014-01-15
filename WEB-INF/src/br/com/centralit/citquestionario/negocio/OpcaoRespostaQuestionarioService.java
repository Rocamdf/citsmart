package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

public interface OpcaoRespostaQuestionarioService extends CrudServicePojo {
	public Collection listByIdQuestaoQuestionario(Integer idQuestaoQuestionario) throws Exception;
}
