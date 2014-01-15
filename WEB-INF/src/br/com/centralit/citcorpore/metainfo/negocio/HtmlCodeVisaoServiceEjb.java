package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.HtmlCodeVisaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class HtmlCodeVisaoServiceEjb extends CrudServicePojoImpl implements HtmlCodeVisaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new HtmlCodeVisaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdVisao(Integer parm) throws Exception{
		HtmlCodeVisaoDao dao = new HtmlCodeVisaoDao();
		try{
			return dao.findByIdVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdVisao(Integer parm) throws Exception{
		HtmlCodeVisaoDao dao = new HtmlCodeVisaoDao();
		try{
			dao.deleteByIdVisao(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
