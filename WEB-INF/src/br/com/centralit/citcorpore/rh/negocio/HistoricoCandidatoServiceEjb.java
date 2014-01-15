package br.com.centralit.citcorpore.rh.negocio;
import br.com.centralit.citcorpore.rh.integracao.HistoricoCandidatoDao;
import br.com.centralit.citcorpore.rh.integracao.IdiomaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class HistoricoCandidatoServiceEjb extends CrudServicePojoImpl implements HistoricoCandidatoService {
	protected CrudDAO getDao() throws ServiceException{ 
		return new HistoricoCandidatoDao(); 
	}
	
	protected void validaCreate(Object arg0) throws Exception{}
	
	protected void validaDelete(Object arg0) throws Exception{}
	
	protected void validaFind(Object arg0) throws Exception{}
	
	protected void validaUpdate(Object arg0) throws Exception{}
	
}
