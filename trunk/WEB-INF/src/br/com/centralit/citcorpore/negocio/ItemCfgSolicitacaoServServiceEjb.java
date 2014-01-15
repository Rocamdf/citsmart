package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ItemCfgSolicitacaoServDAO;
import br.com.centralit.citcorpore.integracao.ProblemaItemConfiguracaoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class ItemCfgSolicitacaoServServiceEjb extends CrudServicePojoImpl implements ItemCfgSolicitacaoServService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected CrudDAO getDao() throws ServiceException {
		return new ProblemaItemConfiguracaoDAO();
	}

	protected void validaCreate(Object arg0) throws Exception {
	}

	protected void validaDelete(Object arg0) throws Exception {
	}

	protected void validaFind(Object arg0) throws Exception {
	}

	protected void validaUpdate(Object arg0) throws Exception {
	}

	@Override
	public Collection findByIdItemConfiguracao(Integer parm) throws Exception {
		ItemCfgSolicitacaoServDAO dao = new ItemCfgSolicitacaoServDAO();
		return dao.findByIdItemConfiguracao(parm);
	}

	@Override
	public void deleteByIdItemConfiguracao(Integer parm) throws Exception {
		ItemCfgSolicitacaoServDAO dao = new ItemCfgSolicitacaoServDAO();
		dao.deleteByIdItemConfiguracao(parm);
		
	}
	
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception {
		ItemCfgSolicitacaoServDAO itemCfgSolicitacaoServDAO = new ItemCfgSolicitacaoServDAO();
		return itemCfgSolicitacaoServDAO.findByIdSolicitacaoServico(parm);
		
	}

	
}
