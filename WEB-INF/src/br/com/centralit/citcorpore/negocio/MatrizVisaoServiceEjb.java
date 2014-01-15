package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.MatrizVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class MatrizVisaoServiceEjb extends CrudServicePojoImpl implements MatrizVisaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new MatrizVisaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdVisao(Integer parm) throws Exception{
		MatrizVisaoDao dao = new MatrizVisaoDao();
		try{
			return dao.findByIdVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdVisao(Integer parm) throws Exception{
		MatrizVisaoDao dao = new MatrizVisaoDao();
		try{
			dao.deleteByIdVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
