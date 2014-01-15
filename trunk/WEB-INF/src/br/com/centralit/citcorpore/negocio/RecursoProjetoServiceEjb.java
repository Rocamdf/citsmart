package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RecursoProjetoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RecursoProjetoServiceEjb extends CrudServicePojoImpl implements RecursoProjetoService {
	protected CrudDAO getDao() throws ServiceException {
		return new RecursoProjetoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdProjeto(Integer parm) throws Exception{
		RecursoProjetoDao dao = new RecursoProjetoDao();
		try{
			return dao.findByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdProjeto(Integer parm) throws Exception{
		RecursoProjetoDao dao = new RecursoProjetoDao();
		try{
			dao.deleteByIdProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdEmpregado(Integer parm) throws Exception{
		RecursoProjetoDao dao = new RecursoProjetoDao();
		try{
			return dao.findByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception{
		RecursoProjetoDao dao = new RecursoProjetoDao();
		try{
			dao.deleteByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
