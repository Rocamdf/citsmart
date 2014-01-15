package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.centralit.citcorpore.integracao.TipoMovimFinanceiraViagemDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings("rawtypes")
public class TipoMovimFinanceiraViagemServiceEjb extends CrudServicePojoImpl implements TipoMovimFinanceiraViagemService {

    private static final long serialVersionUID = -2253183314661440900L;

    protected CrudDAO getDao() throws ServiceException {
	return new TipoMovimFinanceiraViagemDao();
    }

    protected void validaCreate(Object obj) throws Exception {
    }

    protected void validaDelete(Object obj) throws Exception {
    }

    protected void validaUpdate(Object obj) throws Exception {
    }

    protected void validaFind(Object obj) throws Exception {
    }

    
	public Collection list(List ordenacao) throws LogicException, RemoteException, ServiceException {
		return null;
    }

    public Collection list(String ordenacao) throws LogicException, RemoteException, ServiceException {
    	return null;
    }

	@Override
	public List<TipoMovimFinanceiraViagemDTO> listByClassificacao(
			String classificacao) throws Exception {
		TipoMovimFinanceiraViagemDao tipoDao = new TipoMovimFinanceiraViagemDao();
		return tipoDao.listByClassificacao(classificacao);
	}

	@Override
	public List<TipoMovimFinanceiraViagemDTO> findByMovimentacao(
			Long idtipoMovimFinanceiraViagem) throws Exception {
		TipoMovimFinanceiraViagemDao tipoDao = new TipoMovimFinanceiraViagemDao();
		return tipoDao.findByMovimentacao(idtipoMovimFinanceiraViagem);
	}
	
	public List<TipoMovimFinanceiraViagemDTO> recuperaTipoAtivos() throws Exception {
		TipoMovimFinanceiraViagemDao dao = new TipoMovimFinanceiraViagemDao();
		return dao.recuperaTiposAtivos();
	}
	
}

