package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.GrupoEmailDTO;
import br.com.centralit.citcorpore.integracao.GrupoEmailDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class GrupoEmailServiceEjb extends CrudServicePojoImpl implements GrupoEmailService {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5871725506020377855L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoEmailDao();
	}

	@Override
	protected void validaCreate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaUpdate(Object obj) throws Exception {
		
	}

	@Override
	protected void validaDelete(Object obj) throws Exception {
		
	}

	@Override
	protected void validaFind(Object obj) throws Exception {
		
	}

	@Override
	public Collection<GrupoEmailDTO> findByIdGrupo(Integer idGrupo) throws Exception {
		return new GrupoEmailDao().findByIdGrupo(idGrupo);
	}

}
