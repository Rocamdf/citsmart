package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.BICategoriasDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class BICategoriasServiceEjb extends CrudServicePojoImpl implements BICategoriasService {
	protected CrudDAO getDao() throws ServiceException {
		return new BICategoriasDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	public Collection findByIdCategoriaPai(Integer parm) throws Exception{
		BICategoriasDao biCategoriasDao = new BICategoriasDao();
		return biCategoriasDao.findByIdCategoriaPai(parm);
	}
	public Collection findSemPai() throws Exception{
		BICategoriasDao biCategoriasDao = new BICategoriasDao();
		return biCategoriasDao.findSemPai();
	}
}
