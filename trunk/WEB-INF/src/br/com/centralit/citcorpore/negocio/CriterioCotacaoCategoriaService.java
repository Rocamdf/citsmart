package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface CriterioCotacaoCategoriaService extends CrudServiceEjb2 {
	public Collection findByIdCategoria(Integer parm) throws Exception;
	public void deleteByIdCategoria(Integer parm) throws Exception;
}
