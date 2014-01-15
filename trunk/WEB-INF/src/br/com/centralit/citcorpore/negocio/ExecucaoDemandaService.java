package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface ExecucaoDemandaService extends CrudServiceEjb2 {
	public Collection getAtividadesByGrupoAndPessoa(Integer idEmpregado, String[] grupo) throws LogicException, RemoteException, ServiceException;
	public void updateAtribuir(IDto bean) throws LogicException, RemoteException, ServiceException;
	public void updateStatus(IDto bean) throws LogicException, RemoteException, ServiceException;
	public void updateFinalizar(IDto bean) throws LogicException, RemoteException, ServiceException;
	public boolean temAtividadeNaSequencia(IDto bean) throws LogicException, RemoteException, ServiceException;
}
