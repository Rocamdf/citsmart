package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import br.com.centralit.citcorpore.bean.ConhecimentoSolicitacaoDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.TransactionControler;
import br.com.citframework.service.CrudServiceEjb2;

public interface ConhecimentoSolicitacaoService extends CrudServiceEjb2 {
	public void rollbackTransaction(TransactionControler tc, Exception ex) throws ServiceException, LogicException;

	public ConhecimentoSolicitacaoDTO restoreAll(Integer idSolicitacaoServico) throws Exception;

	public Collection findBySolictacaoServico(ConhecimentoSolicitacaoDTO bean) throws ServiceException, LogicException;
}