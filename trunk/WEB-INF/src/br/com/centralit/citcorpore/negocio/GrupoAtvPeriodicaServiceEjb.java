package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.GrupoAtvPeriodicaDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class GrupoAtvPeriodicaServiceEjb extends CrudServicePojoImpl implements GrupoAtvPeriodicaService {
	protected CrudDAO getDao() throws ServiceException {
		return new GrupoAtvPeriodicaDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByDescGrupoAtvPeriodica(String parm) throws Exception{
		GrupoAtvPeriodicaDao dao = new GrupoAtvPeriodicaDao();
		try{
			return dao.findByDescGrupoAtvPeriodica(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByDescGrupoAtvPeriodica(String parm) throws Exception{
		GrupoAtvPeriodicaDao dao = new GrupoAtvPeriodicaDao();
		try{
			dao.deleteByDescGrupoAtvPeriodica(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection listGrupoAtividadePeriodicaAtiva() throws Exception {
		GrupoAtvPeriodicaDao dao = new GrupoAtvPeriodicaDao();
		try{
			return dao.listGrupoAtividadePeriodicaAtiva();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
