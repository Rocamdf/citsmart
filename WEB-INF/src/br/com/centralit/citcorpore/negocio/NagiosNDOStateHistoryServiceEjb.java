package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.NagiosNDOStateHistoryDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class NagiosNDOStateHistoryServiceEjb extends CrudServicePojoImpl implements NagiosNDOStateHistoryService {
	private String jndiName;
	protected CrudDAO getDao() throws ServiceException {
		return new NagiosNDOStateHistoryDao(jndiName);
	}

	public void setJndiName(String jndiNameParm){
		this.jndiName = jndiNameParm;
	}
	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByObject_id(Integer parm) throws Exception{
		NagiosNDOStateHistoryDao dao = new NagiosNDOStateHistoryDao(jndiName);
		try{
			return dao.findByObject_id(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByObject_id(Integer parm) throws Exception{
		NagiosNDOStateHistoryDao dao = new NagiosNDOStateHistoryDao(jndiName);
		try{
			dao.deleteByObject_id(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public Collection findByHostServiceStatus(String jndiNameParm, String hostName, String serviceName, String status, Date dataInicial, Date dataFinal) throws Exception {
		NagiosNDOStateHistoryDao nagiosNDOStateHistoryDao = new NagiosNDOStateHistoryDao(jndiNameParm);
		return nagiosNDOStateHistoryDao.findByHostServiceStatus(hostName, serviceName, status, dataInicial, dataFinal);
	}
	
	public Collection findByHostServiceStatusAndServiceNull(String jndiNameParm, String hostName, String status, Date dataInicial, Date dataFinal) throws Exception {
		NagiosNDOStateHistoryDao nagiosNDOStateHistoryDao = new NagiosNDOStateHistoryDao(jndiNameParm);
		return nagiosNDOStateHistoryDao.findByHostServiceStatusAndServiceNull(hostName, status, dataInicial, dataFinal);		
	}	
}
