package br.com.centralit.citcorpore.rh.negocio;

import java.rmi.RemoteException;

import br.com.centralit.citcorpore.rh.bean.AdmissaoEmpregadoDTO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;


public interface AdmissaoEmpregadoService extends CrudServicePojo {
	
	public AdmissaoEmpregadoDTO calcularCustos(AdmissaoEmpregadoDTO admissaoEmpregado) throws ServiceException, RemoteException, LogicException;
}
