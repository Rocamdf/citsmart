package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.FaturaOSDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class FaturaOSServiceEjb extends CrudServicePojoImpl implements FaturaOSService {
	protected CrudDAO getDao() throws ServiceException {
		return new FaturaOSDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdFatura(Integer parm) throws Exception{
		FaturaOSDao dao = new FaturaOSDao();
		try{
			return dao.findByIdFatura(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdFatura(Integer parm) throws Exception{
		FaturaOSDao dao = new FaturaOSDao();
		try{
			dao.deleteByIdFatura(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdOs(Integer parm) throws Exception{
		FaturaOSDao dao = new FaturaOSDao();
		try{
			return dao.findByIdOs(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdOs(Integer parm) throws Exception{
		FaturaOSDao dao = new FaturaOSDao();
		try{
			dao.deleteByIdOs(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
