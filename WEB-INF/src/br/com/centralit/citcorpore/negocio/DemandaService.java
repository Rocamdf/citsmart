package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;

public interface DemandaService extends CrudServiceEjb2 {
	public Collection findByIdOS(Integer parm) throws Exception;
}
