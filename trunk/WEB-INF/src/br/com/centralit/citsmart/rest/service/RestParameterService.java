package br.com.centralit.citsmart.rest.service;
import java.util.HashMap;

import br.com.centralit.citsmart.rest.bean.RestDomainDTO;
import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.centralit.citsmart.rest.bean.RestParameterDTO;
import br.com.centralit.citsmart.rest.bean.RestSessionDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface RestParameterService extends CrudServiceEjb2 {
	public RestParameterDTO findByIdentifier(Integer parm) throws Exception;
	public HashMap<String, RestDomainDTO> findParameters(RestSessionDTO restSessionDto, RestOperationDTO restOperationDto);
	public String getParamValue(HashMap<String, RestDomainDTO> mapParam, String key);
	
}
