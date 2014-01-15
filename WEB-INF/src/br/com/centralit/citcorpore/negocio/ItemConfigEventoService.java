package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfigEventoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface ItemConfigEventoService extends CrudServiceEjb2 {

    /**
     * Lista os itens de configuração dos eventos cadastrados.
     * 
     * @param idEvento
     * @return Collection
     * @throws ServiceException
     * @throws RemoteException
     * @author daniel.queiroz
     */
    public Collection<ItemConfigEventoDTO> listByIdEvento(Integer idEvento) throws ServiceException, RemoteException;

    /**
     * Verifica a data e hora que foi cadastrado os eventos.
     * 
     * @return Collection
     * @throws ServiceException
     * @throws RemoteException
     * @author daniel.queiroz
     */
    public Collection<ItemConfigEventoDTO> verificarDataHoraEvento() throws ServiceException, RemoteException;
}
