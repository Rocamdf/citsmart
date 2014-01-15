package br.com.centralit.bpm.servico;
import java.util.Collection;

import br.com.centralit.bpm.integracao.HistoricoItemTrabalhoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class HistoricoItemTrabalhoServiceEjb extends CrudServicePojoImpl implements HistoricoItemTrabalhoService {
	protected CrudDAO getDao() throws ServiceException {
		return new HistoricoItemTrabalhoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdItemTrabalho(Integer parm) throws Exception{
		HistoricoItemTrabalhoDao dao = new HistoricoItemTrabalhoDao();
		try{
			return dao.findByIdItemTrabalho(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdUsuario(Integer parm) throws Exception{
		HistoricoItemTrabalhoDao dao = new HistoricoItemTrabalhoDao();
		try{
			return dao.findByIdUsuario(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
