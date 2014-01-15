package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.TemplateImpressaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class TemplateImpressaoServiceEjb extends CrudServicePojoImpl implements TemplateImpressaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new TemplateImpressaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdTipoTemplateImp(Integer parm) throws Exception{
		TemplateImpressaoDao dao = new TemplateImpressaoDao();
		try{
			return dao.findByIdTipoTemplateImp(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdTipoTemplateImp(Integer parm) throws Exception{
		TemplateImpressaoDao dao = new TemplateImpressaoDao();
		try{
			dao.deleteByIdTipoTemplateImp(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
