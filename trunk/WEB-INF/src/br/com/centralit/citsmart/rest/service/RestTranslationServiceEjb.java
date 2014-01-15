package br.com.centralit.citsmart.rest.service;
import br.com.centralit.citsmart.rest.dao.RestTranslationDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RestTranslationServiceEjb extends CrudServicePojoImpl implements RestTranslationService {
	protected CrudDAO getDao() throws ServiceException {
		return new RestTranslationDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

}
