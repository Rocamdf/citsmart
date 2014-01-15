package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestExecutionDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.centralit.citsmart.rest.schema.CtMessageResp;
import br.com.citframework.service.CrudServiceEjb2;
public interface RestExecutionService extends CrudServiceEjb2 {
	public Collection findByIdRestOperation(Integer parm) throws Exception;
	public void deleteByIdRestOperation(Integer parm) throws Exception;
	public RestExecutionDTO start(RestSessionDTO restSessionDto, RestOperationDTO restOperationDto, Object input) throws Exception;
	public RestExecutionDTO start(RestOperationDTO restOperationDto, String input) throws Exception;
	public void end(RestExecutionDTO restExecutionDto, CtMessageResp output) throws Exception;
	
}
