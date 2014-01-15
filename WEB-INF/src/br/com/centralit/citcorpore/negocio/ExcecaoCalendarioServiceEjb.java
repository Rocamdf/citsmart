package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ExcecaoCalendarioDTO;
import br.com.centralit.citcorpore.integracao.ExcecaoCalendarioDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ExcecaoCalendarioServiceEjb extends CrudServicePojoImpl implements ExcecaoCalendarioService {
	protected CrudDAO getDao() throws ServiceException {
		return new ExcecaoCalendarioDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdCalendario(Integer parm) throws Exception{
		ExcecaoCalendarioDao dao = new ExcecaoCalendarioDao();
		try{
			return dao.findByIdCalendario(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdCalendario(Integer parm) throws Exception{
		ExcecaoCalendarioDao dao = new ExcecaoCalendarioDao();
		try{
			dao.deleteByIdCalendario(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ExcecaoCalendarioDTO findByIdCalendarioAndData(Integer idCalendario, Date data) throws Exception {
		ExcecaoCalendarioDao dao = new ExcecaoCalendarioDao();
		try{
			return dao.findByIdCalendarioAndData(idCalendario, data);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
