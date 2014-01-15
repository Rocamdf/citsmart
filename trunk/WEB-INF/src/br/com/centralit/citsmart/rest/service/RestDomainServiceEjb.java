package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.centralit.citsmart.rest.dao.RestDomainDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RestDomainServiceEjb extends CrudServicePojoImpl implements RestDomainService {
	protected CrudDAO getDao() throws ServiceException {
		return new RestDomainDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdRestOperation(Integer parm) throws Exception{
		RestDomainDao dao = new RestDomainDao();
		try{
			return dao.findByIdRestOperation(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
	public void deleteByIdRestOperation(Integer parm) throws Exception{
		RestDomainDao dao = new RestDomainDao();
		try{
			dao.deleteByIdRestOperation(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}			
	}
}
