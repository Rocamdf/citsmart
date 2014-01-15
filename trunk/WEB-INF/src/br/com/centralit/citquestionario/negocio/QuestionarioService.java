package br.com.centralit.citquestionario.negocio;

import java.util.Collection;

import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;

public interface QuestionarioService extends CrudServicePojo {
	public Collection listByIdEmpresa(Integer idEmpresa) throws Exception;
    public Collection listByIdEmpresaAndAplicacao(Integer idEmpresa, String aplicacao) throws Exception;	
	public void copyGroup(IDto model) throws ServiceException, LogicException;
	public QuestionarioDTO restoreByIdOrigem(Integer idQuestionarioOrigem) throws Exception;
	public void updateOrdemGrupos(IDto model) throws ServiceException, LogicException;
	public void updateNomeGrupo(IDto model) throws ServiceException, LogicException;
	public Collection listOpcoesRespostaItemQuestionarioOpcoes(Integer idRespostaItemQuestionario) throws Exception;
}
