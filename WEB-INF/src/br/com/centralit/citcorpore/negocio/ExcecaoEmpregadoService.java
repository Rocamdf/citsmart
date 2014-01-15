package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExcecaoEmpregadoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface ExcecaoEmpregadoService extends CrudServiceEjb2 {

	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdGrupo(Integer idEvento, Integer idGrupo) throws ServiceException, RemoteException;
	public Collection<ExcecaoEmpregadoDTO> listByIdEventoIdUnidade(Integer idEvento, Integer idUnidade) throws ServiceException, RemoteException;
}
