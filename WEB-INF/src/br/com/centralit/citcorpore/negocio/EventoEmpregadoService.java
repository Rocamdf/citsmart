package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.EventoEmpregadoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface EventoEmpregadoService extends CrudServiceEjb2 {

    public Collection<EventoEmpregadoDTO> listByIdEvento(Integer idEvento) throws ServiceException, RemoteException;

    public Collection<EventoEmpregadoDTO> listByIdEventoGrupo(Integer idEvento) throws ServiceException, RemoteException;

    public Collection<EventoEmpregadoDTO> listByIdEventoUnidade(Integer idEvento) throws ServiceException, RemoteException;

    public Collection<EventoEmpregadoDTO> listByIdEventoEmpregado(Integer idEvento) throws ServiceException, RemoteException;
}
