package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface LimiteAlcadaService extends CrudServiceEjb2 {
	public void removerPorIdAlcada(Integer idAlcada) throws Exception;
	public Collection findByIdAlcada(Integer idAlcada) throws Exception;
}
