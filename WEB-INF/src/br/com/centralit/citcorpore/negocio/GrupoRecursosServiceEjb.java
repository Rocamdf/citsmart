package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.integracao.GrupoRecursosDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class GrupoRecursosServiceEjb extends CrudServicePojoImpl implements GrupoRecursosService {
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoRecursosDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

}
