package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.integracao.ContatoSolicitacaoServicoDao;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ContatoSolicitacaoServicoServiceEjb extends CrudServicePojoImpl implements ContatoSolicitacaoServicoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ContatoSolicitacaoServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	@Override
	public synchronized IDto create(IDto model) throws ServiceException, LogicException {
		return super.create(model);
	}

}
