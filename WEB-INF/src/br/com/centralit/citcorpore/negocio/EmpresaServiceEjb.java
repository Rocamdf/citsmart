package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.EmpresaDTO;
import br.com.centralit.citcorpore.integracao.EmpresaDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author rosana.godinho
 * 
 */
@SuppressWarnings("rawtypes")
public class EmpresaServiceEjb extends CrudServicePojoImpl implements EmpresaService {

    private static final long serialVersionUID = -2253183314661440900L;

    protected CrudDAO getDao() throws ServiceException {
	return new EmpresaDao();
    }

    protected void validaCreate(Object obj) throws Exception {
    	if(jaExisteRegistroComMesmoNome((EmpresaDTO)obj)){
    		throw new LogicException(i18n_Message("citcorpore.comum.registroJaCadastrado"));
    	}
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

    public Collection list(String ordenacao) throws LogicException, RemoteException,
	    ServiceException {
	return null;
    }

	public boolean jaExisteRegistroComMesmoNome(EmpresaDTO empresa) {
		try {
			return ((EmpresaDao) getDao()).jaExisteRegistroComMesmoNome(empresa);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
    
}

