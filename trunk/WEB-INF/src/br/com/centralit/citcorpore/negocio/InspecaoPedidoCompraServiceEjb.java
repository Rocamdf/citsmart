package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.integracao.InspecaoPedidoCompraDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class InspecaoPedidoCompraServiceEjb extends CrudServicePojoImpl implements InspecaoPedidoCompraService {
	protected CrudDAO getDao() throws ServiceException {
		return new InspecaoPedidoCompraDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

	public Collection findByIdPedido(Integer parm) throws Exception{
	    InspecaoPedidoCompraDao dao = new InspecaoPedidoCompraDao();
		try{
			return dao.findByIdPedido(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdPedido(Integer parm) throws Exception{
	    InspecaoPedidoCompraDao dao = new InspecaoPedidoCompraDao();
		try{
			dao.deleteByIdPedido(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
}
