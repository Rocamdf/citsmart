package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.integracao.PlanoMelhoriaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class PlanoMelhoriaServiceEjb extends CrudServicePojoImpl implements PlanoMelhoriaService {
	protected CrudDAO getDao() throws ServiceException {
		return new PlanoMelhoriaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

}
