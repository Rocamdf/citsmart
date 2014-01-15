package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

public interface AplicacaoQuestionarioService extends CrudServicePojo {
	public Collection listByIdQuestionarioAndAplicacao(Integer idQuestionario, String aplicacao) throws Exception;
}
