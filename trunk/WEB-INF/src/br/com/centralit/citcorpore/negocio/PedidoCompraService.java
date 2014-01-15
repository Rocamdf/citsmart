package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.PedidoCompraDTO;
import br.com.citframework.service.CrudServiceEjb2;
public interface PedidoCompraService extends CrudServiceEjb2 {
	public Collection findByIdCotacao(Integer parm) throws Exception;
	public Collection findEntreguesByIdCotacao(Integer parm) throws Exception;
	public void deleteByIdCotacao(Integer parm) throws Exception;
    public void atualizaEntrega(PedidoCompraDTO pedidoCompraDto) throws Exception;
}
