package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.centralit.citcorpore.integracao.PrioridadeDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author leandro.viana
 * 
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class PrioridadeServiceEjb extends CrudServicePojoImpl implements PrioridadeService {

	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new PrioridadeDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}

	
	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}

	public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
	}
	
	public Collection<PrioridadeDTO> prioridadesAtivasPorNome(String nome){
		List condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomePrioridade", "=", nome));
		condicoes.add(new Condition("situacao", "!=", "I"));
		try {
			return ((PrioridadeDao)getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ArrayList<PrioridadeDTO>();
	}
}
