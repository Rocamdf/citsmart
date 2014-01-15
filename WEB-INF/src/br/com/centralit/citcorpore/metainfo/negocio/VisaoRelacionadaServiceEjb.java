package br.com.centralit.citcorpore.metainfo.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.metainfo.integracao.VisaoRelacionadaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class VisaoRelacionadaServiceEjb extends CrudServicePojoImpl implements VisaoRelacionadaService {
	protected CrudDAO getDao() throws ServiceException {
		return new VisaoRelacionadaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	public Collection findByIdVisaoPaiAtivos(Integer idVisaoPai) throws Exception {
		VisaoRelacionadaDao visaoRelacionadaDao = new VisaoRelacionadaDao();
		return visaoRelacionadaDao.findByIdVisaoPaiAtivos(idVisaoPai);
	}
}
