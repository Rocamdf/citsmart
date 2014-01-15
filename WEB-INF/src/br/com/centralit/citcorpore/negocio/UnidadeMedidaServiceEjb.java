package br.com.centralit.citcorpore.negocio;
import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.integracao.UnidadeMedidaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class UnidadeMedidaServiceEjb extends CrudServicePojoImpl implements UnidadeMedidaService {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4256139435347692985L;

	protected CrudDAO getDao() throws ServiceException {
		return new UnidadeMedidaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	@Override
	public boolean consultarUnidadesMedidas(UnidadeMedidaDTO unidadeMedidaDTO) throws Exception {
		UnidadeMedidaDao unidadeMedidaDao = new UnidadeMedidaDao();
		
		return unidadeMedidaDao.consultarUnidadesMedidas(unidadeMedidaDTO);
	}

}
