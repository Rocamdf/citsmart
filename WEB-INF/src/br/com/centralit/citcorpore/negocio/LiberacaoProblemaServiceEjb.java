package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.LiberacaoMudancaDao;
import br.com.centralit.citcorpore.integracao.LiberacaoProblemaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class LiberacaoProblemaServiceEjb extends CrudServicePojoImpl implements LiberacaoProblemaService {
	protected CrudDAO getDao() throws ServiceException {
		return new LiberacaoMudancaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdLiberacao(Integer parm) throws Exception{
		LiberacaoProblemaDao dao = new LiberacaoProblemaDao();
		try{
			return dao.findByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdLiberacao(Integer parm) throws Exception{
		LiberacaoProblemaDao dao = new LiberacaoProblemaDao();
		try{
			dao.deleteByIdLiberacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
