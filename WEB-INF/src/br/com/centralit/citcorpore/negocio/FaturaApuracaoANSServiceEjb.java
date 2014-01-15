package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.FaturaApuracaoANSDTO;
import br.com.centralit.citcorpore.integracao.FaturaApuracaoANSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class FaturaApuracaoANSServiceEjb extends CrudServicePojoImpl implements FaturaApuracaoANSService {

    private static final long serialVersionUID = -9026864278973916006L;

    protected CrudDAO getDao() throws ServiceException {
	return new FaturaApuracaoANSDao();
    }

    protected void validaCreate(Object arg0) throws Exception {
    }

    protected void validaDelete(Object arg0) throws Exception {
    }

    protected void validaFind(Object arg0) throws Exception {
    }

    protected void validaUpdate(Object arg0) throws Exception {
    }

    public Collection findByIdFatura(Integer parm) throws Exception {
	FaturaApuracaoANSDao dao = new FaturaApuracaoANSDao();
	try {
	    return dao.findByIdFatura(parm);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public void deleteByIdFatura(Integer parm) throws Exception {
	FaturaApuracaoANSDao dao = new FaturaApuracaoANSDao();
	try {
	    dao.deleteByIdFatura(parm);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public FaturaApuracaoANSDTO findByIdAcordoNivelServicoContrato(Integer idAcordoNivelServicoContrato) throws Exception {
	FaturaApuracaoANSDao dao = new FaturaApuracaoANSDao();
	try {
	    return dao.findByIdAcordoNivelServicoContrato(idAcordoNivelServicoContrato);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }

    public void deleteByIdAcordoNivelServicoContrato(Integer parm) throws Exception {
	FaturaApuracaoANSDao dao = new FaturaApuracaoANSDao();
	try {
	    dao.deleteByIdAcordoNivelServicoContrato(parm);
	} catch (Exception e) {
	    throw new ServiceException(e);
	}
    }
}
