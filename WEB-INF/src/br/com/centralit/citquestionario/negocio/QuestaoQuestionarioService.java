package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.citframework.service.CrudServicePojo;

public interface QuestaoQuestionarioService extends CrudServicePojo {
	public Collection listByIdGrupoQuestionario(Integer idGrupoQuestionario) throws Exception;
	public Collection listByIdGrupoQuestionarioComAgrupadoras(Integer idGrupoQuestionario) throws Exception;
	public QuestaoQuestionarioDTO findBySiglaAndIdQuestionario(String sigla, Integer idQuestionario) throws Exception;
	public Collection listByTipoQuestaoAndIdQuestionario(String tipoQuestao, Integer idQuestionario) throws Exception;
	public Collection listByTipoAndIdQuestionario(String tipo, Integer idQuestionario) throws Exception;
	public Collection listByIdQuestaoAndContrato(Integer idQuestao, Integer idContrato) throws Exception;
	public Collection listByIdQuestaoAndContratoOrderDataASC(Integer idQuestao, Integer idContrato) throws Exception;
	public Collection listByIdQuestaoAgrupadora(Integer idQuestaoAgrupadora) throws Exception;
}
