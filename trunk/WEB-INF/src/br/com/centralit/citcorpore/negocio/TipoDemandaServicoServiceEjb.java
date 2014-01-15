package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.TipoDemandaServicoDTO;
import br.com.centralit.citcorpore.integracao.TipoDemandaServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class TipoDemandaServicoServiceEjb extends CrudServicePojoImpl implements TipoDemandaServicoService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101073129183404628L;

	protected CrudDAO getDao() throws ServiceException {
		return new TipoDemandaServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	protected void validaFind(Object obj) throws Exception {
	}
	
	
	public Collection<TipoDemandaServicoDTO> listSolicitacoes() throws Exception {
		return new TipoDemandaServicoDao().listSolicitacoes();
	}

}
