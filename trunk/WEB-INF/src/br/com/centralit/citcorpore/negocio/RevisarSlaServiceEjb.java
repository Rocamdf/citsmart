package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.integracao.RevisarSlaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;

/**
 * @since 14/06/2013
 * @author rodrigo.oliveira
 *
 */
public class RevisarSlaServiceEjb extends CrudServicePojoImpl implements RevisarSlaService {
	protected CrudDAO getDao() throws ServiceException {
		return new RevisarSlaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	@Override
	public Collection findByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception {
		RevisarSlaDao dao = new RevisarSlaDao();
		try{
			return dao.findByIdAcordoNivelServico(idAcordoNivelServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public void deleteByIdAcordoNivelServico(Integer idAcordoNivelServico) throws Exception {
		RevisarSlaDao dao = new RevisarSlaDao();
		try{
			dao.deleteByIdAcordoNivelServico(idAcordoNivelServico);
		} catch (Exception e) {
			throw new ServiceException(e);
		}		
	}
}
