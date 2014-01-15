package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ImportConfigCamposDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ImportConfigCamposServiceEjb extends CrudServicePojoImpl implements ImportConfigCamposService {
	protected CrudDAO getDao() throws ServiceException {
		return new ImportConfigCamposDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdImportConfig(Integer parm) throws Exception{
		ImportConfigCamposDao dao = new ImportConfigCamposDao();
		try{
			return dao.findByIdImportConfig(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdImportConfig(Integer parm) throws Exception{
		ImportConfigCamposDao dao = new ImportConfigCamposDao();
		try{
			dao.deleteByIdImportConfig(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
