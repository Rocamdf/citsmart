package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.GlosaOSDTO;
import br.com.centralit.citcorpore.integracao.AtividadesOSDao;
import br.com.centralit.citcorpore.integracao.GlosaOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class GlosaOSServiceEjb extends CrudServicePojoImpl implements GlosaOSService {
	protected CrudDAO getDao() throws ServiceException {
		return new GlosaOSDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdOs(Integer parm) throws Exception{
		GlosaOSDao dao = new GlosaOSDao();
		try{
			return dao.findByIdOs(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdOs(Integer parm) throws Exception{
		GlosaOSDao dao = new GlosaOSDao();
		try{
			dao.deleteByIdOs(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	@Override
	    public Double retornarCustoGlosaOSByIdOs(Integer idOs) throws Exception {
		GlosaOSDao dao = new GlosaOSDao();
		try {
		    return dao.retornarCustoGlosaOSByIdOs(idOs);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}

	    }

	@Override
	public Collection<GlosaOSDTO> listaDeGlosas(Integer idOs) throws Exception {
		GlosaOSDao dao = new GlosaOSDao();
		try {
		    return dao.listaDeGlosas(idOs);
		} catch (Exception e) {
		    throw new ServiceException(e);
		}

	}
	
	
}


