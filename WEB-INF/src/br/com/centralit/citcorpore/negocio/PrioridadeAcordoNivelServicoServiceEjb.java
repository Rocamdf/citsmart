package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.PrioridadeAcordoNivelServicoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class PrioridadeAcordoNivelServicoServiceEjb extends CrudServicePojoImpl implements PrioridadeAcordoNivelServicoService {
	protected CrudDAO getDao() throws ServiceException {
		return new PrioridadeAcordoNivelServicoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdUnidade(Integer parm) throws Exception{
		PrioridadeAcordoNivelServicoDao dao = new PrioridadeAcordoNivelServicoDao();
		try{
			return dao.findByIdUnidade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdUnidade(Integer parm) throws Exception{
		PrioridadeAcordoNivelServicoDao dao = new PrioridadeAcordoNivelServicoDao();
		try{
			dao.deleteByIdUnidade(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByIdAcordoNivelServico(Integer parm) throws Exception{
		PrioridadeAcordoNivelServicoDao dao = new PrioridadeAcordoNivelServicoDao();
		try{
			return dao.findByIdAcordoNivelServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdAcordoNivelServico(Integer parm) throws Exception{
		PrioridadeAcordoNivelServicoDao dao = new PrioridadeAcordoNivelServicoDao();
		try{
			dao.deleteByIdAcordoNivelServico(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
