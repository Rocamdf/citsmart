package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.SlaRequisitoSLADao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * 
 * @author rodrigo.oliveira
 *
 */
public class SlaRequisitoSlaServiceEjb extends CrudServicePojoImpl implements SlaRequisitoSlaService {
	
	protected CrudDAO getDao() throws ServiceException {
		return new SlaRequisitoSLADao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	@Override
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception {
		SlaRequisitoSLADao dao = new SlaRequisitoSLADao();
		try{
			return dao.findByIdAcordoNivelServico(idAcordoNivelServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception {
		SlaRequisitoSLADao dao = new SlaRequisitoSLADao();
		try{
			dao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
