package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface RelacionamentoProdutoService extends CrudServiceEjb2 {
	public Collection findByIdTipoProduto(Integer parm) throws Exception;
	public void deleteByIdTipoProduto(Integer parm) throws Exception;
}
