package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface HistoricoExecucaoService extends CrudServiceEjb2 {
	public Collection findByDemanda(Integer idDemanda) throws LogicException, RemoteException, ServiceException;
}
