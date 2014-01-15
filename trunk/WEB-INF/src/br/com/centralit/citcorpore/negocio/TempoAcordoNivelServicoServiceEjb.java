package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.TempoAcordoNivelServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class TempoAcordoNivelServicoServiceEjb extends CrudServicePojoImpl implements TempoAcordoNivelServicoService {
	protected CrudDAO getDao() throws ServiceException {
		return new TempoAcordoNivelServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdAcordoAndIdPrioridade(Integer idAcordoNivelServico, Integer idPrioridade) throws Exception{
		TempoAcordoNivelServicoDao dao = new TempoAcordoNivelServicoDao();
		try{
			return dao.findByIdAcordoAndIdPrioridade(idAcordoNivelServico, idPrioridade);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection findByIdAcordoAndFaseAndIdPrioridade(Integer idAcordoNivelServico, Integer idFase, Integer idPrioridade) throws Exception {
		TempoAcordoNivelServicoDao dao = new TempoAcordoNivelServicoDao();
		try{
			return dao.findByIdAcordoAndFaseAndIdPrioridade(idAcordoNivelServico, idFase, idPrioridade);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
