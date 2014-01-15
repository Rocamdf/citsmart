package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author rosana.godinho
 * 
 */
@SuppressWarnings("serial")
public class InformacaoItemConfiguracaoServiceEjb extends CrudServicePojoImpl
		implements InformacaoItemConfiguracaoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {

		return new ItemConfiguracaoDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		// TODO Auto-generated method stub

	}

	public ItemConfiguracaoDao getItemConfiguracaoDao() throws ServiceException {
		return (ItemConfiguracaoDao) getDao();
	}

	@Override
	public InformacaoItemConfiguracaoDTO listByInformacao(
			ItemConfiguracaoDTO itemConfiguracao) throws Exception {
		try {
			return this.getItemConfiguracaoDao().listByInformacao(
					itemConfiguracao);
		} catch (LogicException e) {
			throw e;
		}
	}

}
