package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.NagiosNDOObjectDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class NagiosNDOObjectServiceEjb extends CrudServicePojoImpl implements NagiosNDOObjectService {
	protected CrudDAO getDao() throws ServiceException {
		return new NagiosNDOObjectDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByName1(String parm) throws Exception{
		NagiosNDOObjectDao dao = new NagiosNDOObjectDao();
		try{
			return dao.findByName1(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByName1(String parm) throws Exception{
		NagiosNDOObjectDao dao = new NagiosNDOObjectDao();
		try{
			dao.deleteByName1(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByName2(String parm) throws Exception{
		NagiosNDOObjectDao dao = new NagiosNDOObjectDao();
		try{
			return dao.findByName2(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByName2(String parm) throws Exception{
		NagiosNDOObjectDao dao = new NagiosNDOObjectDao();
		try{
			dao.deleteByName2(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
