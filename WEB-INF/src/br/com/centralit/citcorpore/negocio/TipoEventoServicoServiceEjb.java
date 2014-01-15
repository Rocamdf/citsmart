package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.integracao.TipoEventoServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class TipoEventoServicoServiceEjb extends CrudServicePojoImpl implements TipoEventoServicoService {
	protected CrudDAO getDao() throws ServiceException {
		return new TipoEventoServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

}
