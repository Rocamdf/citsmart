package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.ValorAjusteGlosaDAO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @author rodrigo.oliveira
 *
 */
public class ValorAjusteGlosaServiceEjb extends CrudServicePojoImpl implements ValorAjusteGlosaService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2220101771511713722L;
	
	@Override
	protected CrudDAO getDao() throws ServiceException {
		return new ValorAjusteGlosaDAO();
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
	
	public Collection findByIdServicoContrato(Integer parm) throws Exception {
		ValorAjusteGlosaDAO dao = new ValorAjusteGlosaDAO();
		try {
			return dao.findByIdServicoContrato(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
}
