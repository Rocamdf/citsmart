package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ItemPedidoCompraDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ItemPedidoCompraDao extends CrudDaoDefaultImpl {
	public ItemPedidoCompraDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idItemPedido" ,"idItemPedido", true, true, false, false));
		listFields.add(new Field("idPedido" ,"idPedido", false, false, false, false));
	    listFields.add(new Field("idColetaPreco" ,"idColetaPreco", false, false, false, false));
		listFields.add(new Field("idProduto" ,"idProduto", false, false, false, false));
		listFields.add(new Field("quantidade" ,"quantidade", false, false, false, false));
		listFields.add(new Field("valorTotal" ,"valorTotal", false, false, false, false));
		listFields.add(new Field("valorDesconto" ,"valorDesconto", false, false, false, false));
		listFields.add(new Field("valorAcrescimo" ,"valorAcrescimo", false, false, false, false));
		listFields.add(new Field("baseCalculoIcms" ,"baseCalculoIcms", false, false, false, false));
		listFields.add(new Field("aliquotaIcms" ,"aliquotaIcms", false, false, false, false));
		listFields.add(new Field("aliquotaIpi" ,"aliquotaIpi", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ItemPedidoCompra";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ItemPedidoCompraDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdPedido(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idPedido", "=", parm)); 
		ordenacao.add(new Order("idItemPedido"));
		return super.findByCondition(condicao, ordenacao);
	}
    public Collection findByIdColetaPreco(Integer parm) throws Exception {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("idColetaPreco", "=", parm)); 
        ordenacao.add(new Order("idItemPedido"));
        return super.findByCondition(condicao, ordenacao);
    }
	public void deleteByIdPedido(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idPedido", "=", parm));
		super.deleteByCondition(condicao);
	}
    public void deleteByIdColetaPreco(Integer parm) throws Exception {
        List condicao = new ArrayList();
        condicao.add(new Condition("idColetaPreco", "=", parm));
        super.deleteByCondition(condicao);
    }
}
