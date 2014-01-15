package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.FaixaValoresRecursoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class FaixaValoresRecursoServiceEjb extends CrudServicePojoImpl implements FaixaValoresRecursoService {
	protected CrudDAO getDao() throws ServiceException {
		return new FaixaValoresRecursoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdRecurso(Integer parm) throws Exception{
		FaixaValoresRecursoDao dao = new FaixaValoresRecursoDao();
		try{
			return dao.findByIdRecurso(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdRecurso(Integer parm) throws Exception{
		FaixaValoresRecursoDao dao = new FaixaValoresRecursoDao();
		try{
			dao.deleteByIdRecurso(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
