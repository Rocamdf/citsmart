package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface CriterioItemCotacaoService extends CrudServiceEjb2 {
	public Collection findByIdItemCotacao(Integer parm) throws Exception;
	public void deleteByIdItemCotacao(Integer parm) throws Exception;
}
