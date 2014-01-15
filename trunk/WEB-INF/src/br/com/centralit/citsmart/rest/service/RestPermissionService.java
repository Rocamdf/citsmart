package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface RestPermissionService extends CrudServiceEjb2 {
	public Collection findByIdOperation(Integer parm) throws Exception;
	public void deleteByIdOperation(Integer parm) throws Exception;
	public Collection findByIdGroup(Integer parm) throws Exception;
	public void deleteByIdGroup(Integer parm) throws Exception;
	public boolean allowedAccess(RestSessionDTO restSessionDto, RestOperationDTO restOperationDto);
}
