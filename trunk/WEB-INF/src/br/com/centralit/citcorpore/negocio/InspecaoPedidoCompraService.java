package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;
public interface InspecaoPedidoCompraService extends CrudServiceEjb2 {
	public Collection findByIdPedido(Integer parm) throws Exception;
	public void deleteByIdPedido(Integer parm) throws Exception;
}
