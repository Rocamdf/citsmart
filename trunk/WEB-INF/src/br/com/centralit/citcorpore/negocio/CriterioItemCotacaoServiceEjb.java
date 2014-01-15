package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.CriterioItemCotacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class CriterioItemCotacaoServiceEjb extends CrudServicePojoImpl implements CriterioItemCotacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new CriterioItemCotacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdItemCotacao(Integer parm) throws Exception{
	    CriterioItemCotacaoDao dao = new CriterioItemCotacaoDao();
		try{
			return dao.findByIdItemCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdItemCotacao(Integer parm) throws Exception{
	    CriterioItemCotacaoDao dao = new CriterioItemCotacaoDao();
		try{
			dao.deleteByIdItemCotacao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
