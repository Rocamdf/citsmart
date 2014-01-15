package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.TipoItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.ValorDTO;
import br.com.centralit.citcorpore.integracao.ValorDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("rawtypes")
public class ValorServiceEjb extends CrudServicePojoImpl implements
		ValorService {

	private static final long serialVersionUID = -5472933017982074780L;

	public Collection findByIdItemConfiguracao(Integer idItemConfiguracao)
			throws Exception {
		ValorDao dao = new ValorDao();
		try {
			return dao.findByIdItemConfiguracao(idItemConfiguracao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdItemConfiguracao(Integer idItemConfiguracao)
			throws Exception {
		ValorDao dao = new ValorDao();
		try {
			dao.deleteByIdItemConfiguracao(idItemConfiguracao);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public Collection findByIdCaracteristica(Integer idCaracteristica)
			throws Exception {
		ValorDao dao = new ValorDao();
		try {
			return dao.findByIdCaracteristica(idCaracteristica);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public void deleteByIdCaracteristica(Integer idCaracteristica)
			throws Exception {
		ValorDao dao = new ValorDao();
		try {
			dao.deleteByIdCaracteristica(idCaracteristica);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public ValorDTO restore(Integer idBaseItemConfiguracao,
			Integer idCaracteristica) throws Exception {
		return this.getValorDao().restore(false, idBaseItemConfiguracao,
				idCaracteristica);
	}

	@Override
	public Collection<ValorDTO> findByItemAndTipoItemConfiguracao(
			ItemConfiguracaoDTO itemConfiguracao,
			TipoItemConfiguracaoDTO tipoItemConfiguracao)
			throws ServiceException, Exception {
		return this.getValorDao().findByItemAndTipoItemConfiguracao(
				itemConfiguracao, tipoItemConfiguracao);
	}

	@Override
	public Collection<ValorDTO> findByItemAndTipoItemConfiguracaoSofware(
			ItemConfiguracaoDTO itemConfiguracao,
			TipoItemConfiguracaoDTO tipoItemConfiguracao) throws Exception {
		// TODO Auto-generated method stub
		return this.getValorDao().findByItemAndTipoItemConfiguracaoSofware(
				itemConfiguracao, tipoItemConfiguracao);

	}

	/**
	 * Retorna DAO de Valor.
	 * 
	 * @return ValorDao
	 * @throws ServiceException
	 * @author valdoilo.damasceno
	 */
	public ValorDao getValorDao() throws ServiceException {
		return (ValorDao) this.getDao();
	}

	protected CrudDAO getDao() throws ServiceException {
		return new ValorDao();
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
	public ValorDTO restoreItemConfiguracao(Integer idItemConfiguracao,
			Integer idCaracteristica) throws Exception {
		return this.getValorDao().restoreItemConfiguracao(idItemConfiguracao,
				idCaracteristica);
	}
	public Collection listByItemConfiguracaoAndTagCaracteristica(Integer idItemConfiguracao, String tag) throws Exception {
		return this.getValorDao().listByItemConfiguracaoAndTagCaracteristica(idItemConfiguracao, tag);		
	}
	public Collection listUniqueValuesByTagCaracteristica(String tag) throws Exception {
		return this.getValorDao().listUniqueValuesByTagCaracteristica(tag);
	}
}
