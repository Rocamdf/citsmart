package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.integracao.ImportanciaNegocioDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings("rawtypes")
public class ImportanciaNegocioServiceEjb extends CrudServicePojoImpl implements
		ImportanciaNegocioService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new ImportanciaNegocioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	
	public Collection list(List ordenacao) throws LogicException,
			RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException,
			RemoteException, ServiceException {
		return null;
	}

	public boolean existeRegistro(String nome){
		
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomeImportanciaNegocio", "=", nome));
		condicoes.add(new Condition("situacao", "=", "A"));
		Collection retorno = null;
		try {
			retorno = ((ImportanciaNegocioDao) getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno == null ? false : true;
		
	}
}
