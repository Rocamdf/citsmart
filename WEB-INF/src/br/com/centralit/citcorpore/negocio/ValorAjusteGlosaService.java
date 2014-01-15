/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServicePojo;

/**
 * @author rodrigo.oliveira
 *
 */
public interface ValorAjusteGlosaService extends CrudServicePojo {
	
	public Collection findByIdServicoContrato(Integer parm) throws Exception;
	
}
