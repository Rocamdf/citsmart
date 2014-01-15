package br.com.centralit.citcorpore.rh.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.rh.integracao.RequisicaoCursoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class RequisicaoCursoServiceEjb extends CrudServicePojoImpl implements RequisicaoCursoService {
	protected CrudDAO getDao() throws ServiceException {
		return new RequisicaoCursoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception{
		RequisicaoCursoDao dao = new RequisicaoCursoDao();
		try{
			return dao.findByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdSolicitacaoServico(Integer parm) throws Exception{
		RequisicaoCursoDao dao = new RequisicaoCursoDao();
		try{
			dao.deleteByIdSolicitacaoServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
