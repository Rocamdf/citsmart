package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.InspecaoEntregaItemDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class InspecaoEntregaItemServiceEjb extends CrudServicePojoImpl implements InspecaoEntregaItemService {
	protected CrudDAO getDao() throws ServiceException {
		return new InspecaoEntregaItemDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdEntrega(Integer parm) throws Exception{
		InspecaoEntregaItemDao dao = new InspecaoEntregaItemDao();
		try{
			return dao.findByIdEntrega(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdEntrega(Integer parm) throws Exception{
		InspecaoEntregaItemDao dao = new InspecaoEntregaItemDao();
		try{
			dao.deleteByIdEntrega(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
