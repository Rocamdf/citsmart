package br.com.centralit.citcorpore.negocio;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.centralit.citcorpore.bean.ItemRequisicaoProdutoDTO;
import br.com.centralit.citcorpore.bean.ProdutoDTO;
import br.com.centralit.citcorpore.bean.UnidadeMedidaDTO;
import br.com.centralit.citcorpore.integracao.ItemPedidoCompraDao;
import br.com.centralit.citcorpore.integracao.ProdutoDao;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.integracao.CrudDAO;
import br.com.citframework.service.CrudServicePojoImpl;
public class ItemPedidoCompraServiceEjb extends CrudServicePojoImpl implements ItemPedidoCompraService {
	protected CrudDAO getDao() throws ServiceException {
		return new ItemPedidoCompraDao();
	}

	protected void validaCreate(Object arg0) throws Exception {}
	protected void validaDelete(Object arg0) throws Exception {}
	protected void validaFind(Object arg0) throws Exception {}
	protected void validaUpdate(Object arg0) throws Exception {}

    public void recuperaRelacionamentos(Collection<ItemPedidoCompraDTO> colItens) throws Exception{
        if (colItens != null) {
            ProdutoDao produtoDao = new ProdutoDao();
            for (ItemPedidoCompraDTO itemDto : colItens) {
                UnidadeMedidaDTO unidDto = new UnidadeMedidaDTO();
                if (itemDto.getIdProduto() != null) {
                    ProdutoDTO produtoDto = new ProdutoDTO();
                    produtoDto.setIdProduto(itemDto.getIdProduto());
                    produtoDto = (ProdutoDTO) produtoDao.restore(produtoDto);
                    if (produtoDto != null) {
                        itemDto.setCodigoProduto(produtoDto.getCodigoProduto());
                        itemDto.setDescricaoItem(produtoDto.getNomeProduto());
                    }
                }
            }               
        }
    }	
    
	public Collection findByIdPedido(Integer parm) throws Exception{
		ItemPedidoCompraDao dao = new ItemPedidoCompraDao();
		try{
		    Collection<ItemPedidoCompraDTO> colItens = dao.findByIdPedido(parm);
		    recuperaRelacionamentos(colItens);
		    return colItens;
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public void deleteByIdPedido(Integer parm) throws Exception{
		ItemPedidoCompraDao dao = new ItemPedidoCompraDao();
		try{
			dao.deleteByIdPedido(parm);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	public double obtemQtdePedidoColetaPreco(Integer idColetaPreco) throws Exception {
        double qtde = 0;
        ItemPedidoCompraDao dao = new ItemPedidoCompraDao();
        try{
            Collection<ItemPedidoCompraDTO> colItens = dao.findByIdColetaPreco(idColetaPreco);
            if (colItens != null) {
                for (ItemPedidoCompraDTO itemPedidoDto : colItens) {
                    qtde += itemPedidoDto.getQuantidade().doubleValue();
                }
            }
        } catch (Exception e) {
            throw new ServiceException(e);
        }
        return qtde;
	}
}
