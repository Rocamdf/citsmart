package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.OcorrenciaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface OcorrenciaService extends CrudServiceEjb2 {
	public Collection findByDemanda(Integer idDemanda) throws LogicException, RemoteException, ServiceException;
	public void updateResposta(IDto bean) throws LogicException, RemoteException, ServiceException;
	public Collection<OcorrenciaDTO> findByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;
	public OcorrenciaDTO findSiglaGrupoExecutorByIdSolicitacao(Integer idSolicitacaoServico) throws Exception;
}
