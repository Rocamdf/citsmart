package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.PesquisaItemConfiguracaoDTO;
import br.com.centralit.citcorpore.integracao.ItemConfiguracaoDao;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class PesquisaItemConfiguracaoServiceEjb extends CrudServicePojoImpl
		implements PesquisaItemConfiguracaoService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ItemConfiguracaoDao();
	}

	@Override
	protected void validaCreate(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaDelete(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaFind(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	protected void validaUpdate(Object arg0) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public Collection<ItemConfiguracaoDTO> listByIdItemconfiguracao(
			PesquisaItemConfiguracaoDTO pesquisa) throws Exception {

		try {
			return this.getItemConfiguracaoDao().listByIdentificacao(pesquisa);
		} catch (LogicException e) {
			throw e;
		}
	}

	public ItemConfiguracaoDao getItemConfiguracaoDao() throws ServiceException {
		return (ItemConfiguracaoDao) getDao();
	}

}
