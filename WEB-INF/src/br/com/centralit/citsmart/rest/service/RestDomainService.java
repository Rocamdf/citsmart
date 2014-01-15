package br.com.centralit.citsmart.rest.service;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface RestDomainService extends CrudServiceEjb2 {
	public Collection findByIdRestOperation(Integer parm) throws Exception;
	public void deleteByIdRestOperation(Integer parm) throws Exception;
}
