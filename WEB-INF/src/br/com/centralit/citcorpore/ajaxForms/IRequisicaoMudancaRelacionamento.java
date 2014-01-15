package br.com.centralit.citcorpore.ajaxForms;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;

/**
 * @author breno.guimaraes
 *
 */
public interface IRequisicaoMudancaRelacionamento {
	@SuppressWarnings("rawtypes")
	public void gravarRelacionamentos(Integer idRequisicaoMudanca, Collection listaDeserializada) throws RemoteException, LogicException, ServiceException, Exception;
	@SuppressWarnings("rawtypes")
	public ArrayList listItensRelacionadosRequisicaoMudanca(RequisicaoMudancaDTO requisicaoMudancaDTO) throws ServiceException, Exception;
}
