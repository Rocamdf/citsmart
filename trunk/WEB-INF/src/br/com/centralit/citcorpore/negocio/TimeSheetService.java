package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.sql.Date;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

public interface TimeSheetService extends CrudServiceEjb2 {
	public Collection findByPessoaAndPeriodo(Integer idEmpregado, Date dataInicio, Date dataFim) throws LogicException, RemoteException, ServiceException;
	public Collection findByDemanda(Integer idDemanda) throws LogicException, RemoteException, ServiceException;
}
