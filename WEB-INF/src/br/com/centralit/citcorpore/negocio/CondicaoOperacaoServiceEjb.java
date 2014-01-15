package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.integracao.CondicaoOperacaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author ygor.magalhaes
 *
 */
@SuppressWarnings({"serial","rawtypes"})
public class CondicaoOperacaoServiceEjb extends CrudServicePojoImpl implements CondicaoOperacaoService {

	protected CrudDAO getDao() throws ServiceException {
		return new CondicaoOperacaoDao();
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
	
	public boolean jaExisteCondicaoComMesmoNome(String nome) {
		ArrayList<Condition> condicoes = new ArrayList<Condition>();
		condicoes.add(new Condition("nomeCondicaoOperacao", "=", nome));
		condicoes.add(new Condition("dataFim", "is", null));
		Collection retorno = null;
		try {
			retorno = ((CondicaoOperacaoDao) getDao()).findByCondition(condicoes, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno == null ? false : true;
	}
	
}
