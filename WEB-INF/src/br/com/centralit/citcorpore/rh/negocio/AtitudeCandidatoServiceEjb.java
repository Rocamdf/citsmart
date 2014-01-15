package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.AtitudeCandidatoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class AtitudeCandidatoServiceEjb extends CrudServicePojoImpl implements AtitudeCandidatoService {
	protected CrudDAO getDao() throws ServiceException {
		return new AtitudeCandidatoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdEntrevista(Integer parm) throws Exception{
		AtitudeCandidatoDao dao = new AtitudeCandidatoDao();
		try{
			return dao.findByIdEntrevista(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEntrevista(Integer parm) throws Exception{
		AtitudeCandidatoDao dao = new AtitudeCandidatoDao();
		try{
			dao.deleteByIdEntrevista(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
