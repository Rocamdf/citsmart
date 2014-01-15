package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.integracao.VisaoPersonalizadaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class VisaoPersonalizadaServiceEjb extends CrudServicePojoImpl implements VisaoPersonalizadaService {
	protected CrudDAO getDao() throws ServiceException {
		return new VisaoPersonalizadaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	public void deleteAll() throws Exception{
	    VisaoPersonalizadaDao dao = new VisaoPersonalizadaDao();
	    dao.deleteAll();
	}
}
