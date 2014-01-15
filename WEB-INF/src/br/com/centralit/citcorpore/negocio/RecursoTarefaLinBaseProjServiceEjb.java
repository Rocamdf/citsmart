package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RecursoTarefaLinBaseProjDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RecursoTarefaLinBaseProjServiceEjb extends CrudServicePojoImpl implements RecursoTarefaLinBaseProjService {
	protected CrudDAO getDao() throws ServiceException {
		return new RecursoTarefaLinBaseProjDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception{
		RecursoTarefaLinBaseProjDao dao = new RecursoTarefaLinBaseProjDao();
		try{
			return dao.findByIdTarefaLinhaBaseProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdTarefaLinhaBaseProjeto(Integer parm) throws Exception{
		RecursoTarefaLinBaseProjDao dao = new RecursoTarefaLinBaseProjDao();
		try{
			dao.deleteByIdTarefaLinhaBaseProjeto(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdEmpregado(Integer parm) throws Exception{
		RecursoTarefaLinBaseProjDao dao = new RecursoTarefaLinBaseProjDao();
		try{
			return dao.findByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception{
		RecursoTarefaLinBaseProjDao dao = new RecursoTarefaLinBaseProjDao();
		try{
			dao.deleteByIdEmpregado(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
