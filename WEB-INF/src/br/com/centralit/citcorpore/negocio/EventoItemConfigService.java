package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.CaracteristicaDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface EventoItemConfigService extends CrudServiceEjb2 {

    public ValorDTO pegarCaminhoItemConfig(String nomeBaseItemConfig) throws ServiceException, RemoteException;
    
    /**
     * Traz os dados da Network do item de configuração
     * 
     * @param idItemConfiguracao
     * @return
     * @throws ServiceException
     * @throws RemoteException
     */
    public Collection<CaracteristicaDTO> pegarNetworksItemConfiguracao(Integer idItemConfiguracao) throws ServiceException, RemoteException;
    
    /**
     * Traz o nome do Sistema Operacional instalado no item de configuração
     * 
     * @param idItemConfiguracao
     * @return String nome do SO
     * @throws ServiceException
     * @throws RemoteException
     */
    public String pegarSistemaOperacionalItemConfiguracao(Integer idItemConfiguracao) throws ServiceException, RemoteException;

}
