package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestLogDTO;
import br.com.centralit.citsmart.rest.schema.CtError;
import br.com.centralit.citsmart.rest.util.RestEnum.ExecutionStatus;
import br.com.citframework.service.CrudServiceEjb2;
public interface RestLogService extends CrudServiceEjb2 {
	public Collection findByIdRestExecution(Integer parm) throws Exception;
	public void deleteByIdRestExecution(Integer parm) throws Exception;
	public CtError create(RestExecutionDTO restExecutionDto, String code, String description);
	public CtError create(RestExecutionDTO restExecutionDto, Exception e);
	public RestLogDTO create(RestExecutionDTO restExecutionDto, Object result, ExecutionStatus status);
	
}
