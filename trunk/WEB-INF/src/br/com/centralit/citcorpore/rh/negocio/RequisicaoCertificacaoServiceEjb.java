package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.RequisicaoCertificacaoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RequisicaoCertificacaoServiceEjb extends CrudServicePojoImpl implements RequisicaoCertificacaoService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoCertificacaoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception{
		RequisicaoCertificacaoDao dao = new RequisicaoCertificacaoDao();
		try{
			return dao.findByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception{
		RequisicaoCertificacaoDao dao = new RequisicaoCertificacaoDao();
		try{
			dao.deleteByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
