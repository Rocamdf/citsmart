package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author ronnie.lopes
 *
 */
public interface ItemPrestacaoContasViagemService extends CrudServiceEjb2 {
	
	public Collection<ItemPrestacaoContasViagemDTO> recuperaItensPrestacao(PrestacaoContasViagemDTO prestacaoContasViagemDto) throws Exception;
	
}
