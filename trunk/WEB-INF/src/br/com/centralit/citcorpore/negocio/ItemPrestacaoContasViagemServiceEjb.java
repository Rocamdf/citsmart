package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.integracao.ItemPrestacaoContasViagemDao;
import br.com.centralit.citcorpore.integracao.PrestacaoContasViagemDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings("rawtypes")
public class ItemPrestacaoContasViagemServiceEjb extends CrudServicePojoImpl implements ItemPrestacaoContasViagemService {

    private static final long serialVersionUID = -2253183314661440900L;

    protected CrudDAO getDao() throws ServiceException {
    	return new PrestacaoContasViagemDao();
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
    
    public Collection<ItemPrestacaoContasViagemDTO> recuperaItensPrestacao(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception{
    	ItemPrestacaoContasViagemDao dao = new ItemPrestacaoContasViagemDao();
    	return dao.listByPrestacaoContas(prestacaoContasViagemDto.getIdPrestacaoContasViagem());
    }
}

