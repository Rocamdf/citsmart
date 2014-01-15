package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LocalidadeDTO;
import br.com.centralit.citcorpore.integracao.LocalidadeDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

@SuppressWarnings("serial")
public class LocalidadeServiceEjb extends CrudServicePojoImpl implements LocalidadeService {

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new LocalidadeDAO();
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

	@Override
	public boolean verificarLocalidadeAtiva(LocalidadeDTO obj) throws Exception {
		LocalidadeDAO localidadedao = new LocalidadeDAO();
		return localidadedao.verificarLocalidadeAtiva(obj);
	}

	@Override
	public Collection<LocalidadeDTO> listLocalidade() throws Exception {
		LocalidadeDAO localidadedao = new LocalidadeDAO();
		return localidadedao.listLocalidade();
	}

}
