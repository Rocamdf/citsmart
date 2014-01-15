package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.CriterioCotacaoCategoriaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class CriterioCotacaoCategoriaServiceEjb extends CrudServicePojoImpl implements CriterioCotacaoCategoriaService {
	protected CrudDAO getDao() throws ServiceException {
		return new CriterioCotacaoCategoriaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdCategoria(Integer parm) throws Exception{
		CriterioCotacaoCategoriaDao dao = new CriterioCotacaoCategoriaDao();
		try{
			return dao.findByIdCategoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCategoria(Integer parm) throws Exception{
		CriterioCotacaoCategoriaDao dao = new CriterioCotacaoCategoriaDao();
		try{
			dao.deleteByIdCategoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
