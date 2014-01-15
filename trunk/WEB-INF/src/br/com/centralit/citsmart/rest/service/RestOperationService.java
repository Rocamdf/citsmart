package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestOperationDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface RestOperationService extends CrudServiceEjb2 {
	public Collection findByIdBatchProcessing(Integer parm) throws Exception;
	public void deleteByIdBatchProcessing(Integer parm) throws Exception;
	public RestOperationDTO findByName(String name);
}
