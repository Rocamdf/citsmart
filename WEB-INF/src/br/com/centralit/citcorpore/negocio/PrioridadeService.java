package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PrioridadeDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author leandro.viana
 * 
 */
public interface PrioridadeService extends CrudServiceEjb2 {
	public Collection<PrioridadeDTO> prioridadesAtivasPorNome(String nome);
}
