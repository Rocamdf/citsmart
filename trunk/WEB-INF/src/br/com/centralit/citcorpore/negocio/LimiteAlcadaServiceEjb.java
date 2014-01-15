package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LimiteAlcadaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class LimiteAlcadaServiceEjb extends CrudServicePojoImpl implements LimiteAlcadaService {
	protected CrudDAO getDao() throws ServiceException {
		return new LimiteAlcadaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public void removerPorIdAlcada(Integer idAlcada) throws Exception {
		LimiteAlcadaDao limiteDAO = new LimiteAlcadaDao();
		limiteDAO.removerPorIdAlcada(idAlcada);
	}
	
	public Collection findByIdAlcada(Integer idAlcada) throws Exception {
		return new LimiteAlcadaDao().findByIdAlcada(idAlcada);
	}
}
