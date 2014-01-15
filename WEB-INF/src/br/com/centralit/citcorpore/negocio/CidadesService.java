package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.CidadesDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface CidadesService  extends CrudServiceEjb2  {
	
	public Collection<CidadesDTO> listByIdCidades(CidadesDTO obj)
			throws Exception ;

	
}
