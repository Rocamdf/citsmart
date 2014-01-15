package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ReaberturaSolicitacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ReaberturaSolicitacaoServiceEjb extends CrudServicePojoImpl implements ReaberturaSolicitacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ReaberturaSolicitacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception{
		ReaberturaSolicitacaoDao dao = new ReaberturaSolicitacaoDao();
		try{
			return dao.findByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
