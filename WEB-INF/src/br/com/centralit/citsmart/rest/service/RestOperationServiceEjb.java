package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.dao.RestOperationDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RestOperationServiceEjb extends CrudServicePojoImpl implements RestOperationService {
	protected CrudDAO getDao() throws ServiceException {
		return new RestOperationDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdBatchProcessing(Integer parm) throws Exception{
		RestOperationDao dao = new RestOperationDao();
		try{
			return dao.findByIdBatchProcessing(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdBatchProcessing(Integer parm) throws Exception{
		RestOperationDao dao = new RestOperationDao();
		try{
			dao.deleteByIdBatchProcessing(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public RestOperationDTO findByName(String name) {
		RestOperationDao dao = new RestOperationDao();
		try {
			return dao.findByName(name);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
