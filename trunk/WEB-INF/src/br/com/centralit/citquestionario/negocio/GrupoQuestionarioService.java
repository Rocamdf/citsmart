package br.com.centralit.citquestionario.negocio;

import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.citframework.service.CrudServicePojo;

public interface GrupoQuestionarioService extends CrudServicePojo {
	public Collection listByIdQuestionario(Integer idQuestionario) throws Exception;
	public Collection geraImpressao(Collection colQuestoes);
	public Collection geraImpressaoFormatadaHTML(Collection colQuestoes,
			Date dataQuestionario, 
			Integer idSolicitacaoServico, 
			Integer idProfissional);
	public Collection geraImpressaoFormatadaHTMLQuestao(QuestaoQuestionarioDTO questaoDto);
}
