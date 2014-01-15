package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.PrioridadeServicoUnidadeDTO;
import br.com.centralit.citcorpore.integracao.PrioridadeServicoUnidadeDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class PrioridadeServicoUnidadeServiceEjb extends CrudServicePojoImpl implements PrioridadeServicoUnidadeService {
	protected CrudDAO getDao() throws ServiceException {
		return new PrioridadeServicoUnidadeDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	public PrioridadeServicoUnidadeDTO restore(Integer idServicoContrato, Integer idUnidade) throws Exception{
	    PrioridadeServicoUnidadeDao prioridadeServicoUnidadeDao = new PrioridadeServicoUnidadeDao();
	    return prioridadeServicoUnidadeDao.restore(idServicoContrato, idUnidade);
	}
}
