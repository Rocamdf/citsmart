package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.CentreonLogDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class CentreonLogServiceEjb extends CrudServicePojoImpl implements CentreonLogService {
	private String jndiName;
	protected CrudDAO getDao() throws ServiceException {
		return new CentreonLogDao(jndiName);
	}

	public void setJndiName(String jndiNameParm){
		this.jndiName = jndiNameParm;
	}
	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}
	
	public Collection findByHostServiceStatus(String jndiNameParm, String hostName, String serviceName, String status, Date dataInicial, Date dataFinal) throws Exception {
		CentreonLogDao centreonLogDao = new CentreonLogDao(jndiNameParm);
		return centreonLogDao.findByHostServiceStatus(hostName, serviceName, status, dataInicial, dataFinal);
	}
	
	public Collection findByHostServiceStatusAndServiceNull(String jndiNameParm, String hostName, String status, Date dataInicial, Date dataFinal) throws Exception {
		CentreonLogDao centreonLogDao = new CentreonLogDao(jndiNameParm);
		return centreonLogDao.findByHostServiceStatusAndServiceNull(hostName, status, dataInicial, dataFinal);		
	}

}
