package br.com.centralit.citcorpore.negocio;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.RiscoDTO;
import br.com.centralit.citcorpore.integracao.EmpresaDao;
import br.com.centralit.citcorpore.integracao.RiscoDAO;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class RiscoServiceEjb extends CrudServicePojoImpl implements RiscoService{

	private static final long serialVersionUID = -2253183314661440900L;

    protected CrudDAO getDao() throws ServiceException {
    	return new RiscoDAO();
    }

    protected void validaCreate(Object obj) throws Exception {
    	//if(jaExisteRegistroComMesmoNome((RiscoDTO)obj)){
    	if(existeNoBanco((RiscoDTO)obj)){
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

	public boolean jaExisteRegistroComMesmoNome(RiscoDTO risco) {
		try {
			return ((RiscoDAO) getDao()).jaExisteRegistroComMesmoNome(risco);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean existeNoBanco(RiscoDTO risco) {
		try {
			return ((RiscoDAO) getDao()).existeNoBanco(risco);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public ArrayList<RiscoDTO> riscoAtivo(){
		try {
		return ((RiscoDAO) getDao()).riscoAtivo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
