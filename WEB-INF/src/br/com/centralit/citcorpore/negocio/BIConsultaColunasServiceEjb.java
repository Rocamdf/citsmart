package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.BIConsultaColunasDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class BIConsultaColunasServiceEjb extends CrudServicePojoImpl implements BIConsultaColunasService {
	protected CrudDAO getDao() throws ServiceException {
		return new BIConsultaColunasDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdConsulta(Integer parm) throws Exception{
		BIConsultaColunasDao dao = new BIConsultaColunasDao();
		try{
			return dao.findByIdConsulta(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdConsulta(Integer parm) throws Exception{
		BIConsultaColunasDao dao = new BIConsultaColunasDao();
		try{
			dao.deleteByIdConsulta(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByOrdem(Integer parm) throws Exception{
		BIConsultaColunasDao dao = new BIConsultaColunasDao();
		try{
			return dao.findByOrdem(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByOrdem(Integer parm) throws Exception{
		BIConsultaColunasDao dao = new BIConsultaColunasDao();
		try{
			dao.deleteByOrdem(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
