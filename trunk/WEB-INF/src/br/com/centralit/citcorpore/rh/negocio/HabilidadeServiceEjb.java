package br.com.centralit.citcorpore.rh.negocio;
import br.com.centralit.citcorpore.rh.integracao.HabilidadeDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class HabilidadeServiceEjb extends CrudServicePojoImpl implements HabilidadeService {
	protected CrudDAO getDao() throws ServiceException{ 
		return new HabilidadeDao(); 
	}
	
	protected void validaCreate(Object arg0) throws Exception{}
	
	protected void validaDelete(Object arg0) throws Exception{}
	
	protected void validaFind(Object arg0) throws Exception{}
	
	protected void validaUpdate(Object arg0) throws Exception{}
	
}
