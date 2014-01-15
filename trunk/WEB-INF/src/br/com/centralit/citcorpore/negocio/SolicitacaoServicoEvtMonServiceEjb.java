package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.SolicitacaoServicoEvtMonDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class SolicitacaoServicoEvtMonServiceEjb extends CrudServicePojoImpl implements SolicitacaoServicoEvtMonService {
	protected CrudDAO getDao() throws ServiceException {
		return new SolicitacaoServicoEvtMonDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	public Collection findByIdRecursoAndSolicitacaoAberta(Integer idRecurso) throws Exception {
		SolicitacaoServicoEvtMonDao solicitacaoServicoEvtMonDao = new SolicitacaoServicoEvtMonDao();
		return solicitacaoServicoEvtMonDao.findByIdRecursoAndSolicitacaoAberta(idRecurso);
	}
	public Collection findByIdSolicitacao(Integer idSolicitacaoServico) throws Exception {
		SolicitacaoServicoEvtMonDao solicitacaoServicoEvtMonDao = new SolicitacaoServicoEvtMonDao();
		return solicitacaoServicoEvtMonDao.findByIdSolicitacao(idSolicitacaoServico);		
	}
}
