package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO;
import br.com.centralit.citcorpore.integracao.LocalidadeUnidadeDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class LocalidadeUnidadeServiceEjb extends CrudServicePojoImpl implements LocalidadeUnidadeService {

	private static final long serialVersionUID = 1L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		// TODO Auto-generated method stub
		return new LocalidadeUnidadeDAO();
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
	public Collection<LocalidadeUnidadeDTO> listaIdLocalidades(Integer idUnidade) throws Exception {
		LocalidadeUnidadeDAO localidadeUnidadeDao = new LocalidadeUnidadeDAO();
		return localidadeUnidadeDao.listaIdLocalidades(idUnidade);
	}

	@Override
	public boolean verificarExistenciaDeLocalidadeEmUnidade(Integer idLocalidade) throws Exception {
		LocalidadeUnidadeDAO localidadeUnidadeDao = new LocalidadeUnidadeDAO();
		return localidadeUnidadeDao.verificarExistenciaDeLocalidadeEmUnidade(idLocalidade);
	}
	
	public void deleteByIdUnidade(Integer idUnidade) throws Exception {
		
		LocalidadeUnidadeDAO localidadeUnidadeDAO = (LocalidadeUnidadeDAO) getDao();
		
		localidadeUnidadeDAO.deleteByIdUnidade(idUnidade);
	}	
}
