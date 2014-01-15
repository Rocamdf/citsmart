package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ObjetivoMonitoramentoDTO;
import br.com.centralit.citcorpore.integracao.ObjetivoMonitoramentoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
@SuppressWarnings({"serial","rawtypes"})
public class ObjetivoMonitoramentoServiceEjb extends CrudServicePojoImpl implements ObjetivoMonitoramentoService {
	protected CrudDAO getDao() throws ServiceException {
		return new ObjetivoMonitoramentoDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdObjetivoPlanoMelhoria(Integer parm) throws Exception{
		ObjetivoMonitoramentoDao dao = new ObjetivoMonitoramentoDao();
		try{
			return dao.findByIdObjetivoPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdObjetivoPlanoMelhoria(Integer parm) throws Exception{
		ObjetivoMonitoramentoDao dao = new ObjetivoMonitoramentoDao();
		try{
			dao.deleteByIdObjetivoPlanoMelhoria(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public Collection<ObjetivoMonitoramentoDTO> listObjetivosMonitoramento(ObjetivoMonitoramentoDTO obj) throws Exception {
		ObjetivoMonitoramentoDao dao = new ObjetivoMonitoramentoDao();
		try{
			return dao.listObjetivosMonitoramento(obj);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
