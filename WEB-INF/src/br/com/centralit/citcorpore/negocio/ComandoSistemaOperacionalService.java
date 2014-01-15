package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;

import br.com.centralit.citcorpore.bean.ComandoSistemaOperacionalDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author ygor.magalhaes
 * 
 */
public interface ComandoSistemaOperacionalService extends CrudServiceEjb2 {

    public ComandoSistemaOperacionalDTO pegarComandoSO(String so, String tipoExecucao) throws ServiceException, RemoteException;
    
    public boolean pesquisarExistenciaComandoSO(ComandoSistemaOperacionalDTO comandoSODTO) throws ServiceException, RemoteException;
}
