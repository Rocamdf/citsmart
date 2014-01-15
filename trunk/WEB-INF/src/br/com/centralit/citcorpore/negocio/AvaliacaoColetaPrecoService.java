package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface AvaliacaoColetaPrecoService extends CrudServiceEjb2 {
	public Collection findByIdColetaPreco(Integer idColetaPreco) throws Exception;
	public void deleteByIdColetaPreco(Integer idColetaPreco) throws Exception;
}
