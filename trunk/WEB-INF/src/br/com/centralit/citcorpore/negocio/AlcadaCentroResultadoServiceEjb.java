package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO;
import br.com.centralit.citcorpore.integracao.AlcadaCentroResultadoDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

public class AlcadaCentroResultadoServiceEjb extends CrudServicePojoImpl
		implements AlcadaCentroResultadoService {

	private static final long serialVersionUID = 2418571763655973208L;

	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new AlcadaCentroResultadoDAO();
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
	public boolean verificarVinculoCentroResultado(Integer obj)
			throws Exception {
		AlcadaCentroResultadoDAO alcadaCentroResultadoDAO = new AlcadaCentroResultadoDAO();
		return alcadaCentroResultadoDAO.verificarVinculoCentroResultado(obj);
	}

	@Override
	public Collection<AlcadaCentroResultadoDTO> findByIdCentroResultado(Integer idCentroResultado) throws Exception {
	    return new AlcadaCentroResultadoDAO().findByIdCentroResultado(idCentroResultado);
	}
}