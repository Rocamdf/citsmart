package br.com.centralit.citcorpore.negocio;
import java.sql.Date;
import java.util.Collection;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServicePojo;
public interface CentreonLogService extends CrudServicePojo {
	public void setJndiName(String jndiNameParm);
	public Collection findByHostServiceStatus(String jndiNameParm, String hostName, String serviceName, String status, Date dataInicial, Date dataFinal) throws Exception;
	public Collection findByHostServiceStatusAndServiceNull(String jndiNameParm, String hostName, String status, Date dataInicial, Date dataFinal) throws Exception;
}
