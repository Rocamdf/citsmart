package br.com.centralit.citcorpore.metainfo.negocio;
import br.com.centralit.citcorpore.metainfo.integracao.BibliotecasExternasDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class BibliotecasExternasServiceEjb extends CrudServicePojoImpl implements BibliotecasExternasService {
	protected CrudDAO getDao() throws ServiceException {
		return new BibliotecasExternasDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

}
