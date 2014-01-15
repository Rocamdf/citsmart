package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface SolicitacaoServicoEvtMonService extends CrudServicePojo {
	public Collection findByIdRecursoAndSolicitacaoAberta(Integer idRecurso) throws Exception;
	public Collection findByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;
}
