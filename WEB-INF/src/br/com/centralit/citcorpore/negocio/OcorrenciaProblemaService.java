package br.com.centralit.citcorpore.negocio;

import java.util.Collection;
import br.com.citframework.service.CrudServicePojo;

/**
 * 
 * @author geber.costa
 *
 */
@SuppressWarnings("rawtypes")
public interface OcorrenciaProblemaService extends CrudServicePojo {
	
	public Collection findByIdProblema(Integer idProblema
			) throws Exception;
}


