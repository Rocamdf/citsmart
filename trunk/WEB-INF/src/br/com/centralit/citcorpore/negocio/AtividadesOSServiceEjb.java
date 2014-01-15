package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class AtividadesOSServiceEjb extends CrudServicePojoImpl implements AtividadesOSService {

    private static final long serialVersionUID = 6834270035301728580L;

    protected CrudDAO getDao() throws ServiceException {
	return new AtividadesOSDao();
    }

    protected void validaCreate(Object arg0) throws Exception {
    }

    protected void validaDelete(Object arg0) throws Exception {
    }

    protected void validaFind(Object arg0) throws Exception {
    }

    protected void validaUpdate(Object arg0) throws Exception {
    }

    public Collection findByIdOS(Integer parm) throws Exception {
	AtividadesOSDao dao = new AtividadesOSDao();
	try {
	    return dao.findByIdOS(parm);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public void deleteByIdOS(Integer parm) throws Exception {
	AtividadesOSDao dao = new AtividadesOSDao();
	try {
	    dao.deleteByIdOS(parm);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    @Override
    public Double retornarCustoAtividadeOSByIdOs(Integer idOs) throws Exception {
	AtividadesOSDao dao = new AtividadesOSDao();
	try {
	    return dao.retornarCustoAtividadeOSByIdOs(idOs);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}

    }
    
    @Override
    public Double retornarGlosaAtividadeOSByIdOs(Integer idOs) throws Exception {
	AtividadesOSDao dao = new AtividadesOSDao();
	try {
	    return dao.retornarGlosaAtividadeOSByIdOs(idOs);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}

    }

	@Override
	public Double retornarQtdExecucao(Integer idOs) throws Exception {
		AtividadesOSDao dao = new AtividadesOSDao();
		try {
		    return dao.retornarQtdExecucao(idOs);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}

	}

	@Override
	public Collection findByIdOsServicoContratoContabil(Integer idOs, Integer servicoContratoContabil) throws Exception {
		AtividadesOSDao dao = new AtividadesOSDao();
		try {
		    return dao.findByIdOsServicoContratoContabil(idOs, servicoContratoContabil);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}
	}
	
}
