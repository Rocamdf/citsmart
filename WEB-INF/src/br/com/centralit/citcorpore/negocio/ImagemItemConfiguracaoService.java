package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;



/**
 *
 * @author breno.guimaraes
 *
 */

public interface ImagemItemConfiguracaoService extends CrudServiceEjb2{
    Collection findByIdServico(int idServico) throws LogicException, RemoteException, ServiceException;
    Collection findByIdImagemItemConfiguracaoPai(int id) throws LogicException, RemoteException, ServiceException;
    public Collection findItensRelacionadosHierarquia(Integer idItemCfg) throws Exception;
    public Collection findServicosRelacionadosHierarquia(Integer idItemCfg) throws Exception;
}
