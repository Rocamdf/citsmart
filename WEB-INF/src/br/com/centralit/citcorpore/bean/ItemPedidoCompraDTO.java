package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class ItemPedidoCompraDTO implements IDto {
	private Integer idItemPedido;
	private Integer idPedido;
	private Integer idColetaPreco;
	private Integer idProduto;
	private Double quantidade;
	private Double valorTotal;
	private Double valorDesconto;
	private Double valorAcrescimo;
	private Double baseCalculoIcms;
	private Double aliquotaIcms;
	private Double aliquotaIpi;
	private Double valorFrete;
	
	private String descricaoItem;
	private String codigoProduto;

	public Integer getIdItemPedido(){
		return this.idItemPedido;
	}
	public void setIdItemPedido(Integer parm){
		this.idItemPedido = parm;
	}

	public Integer getIdPedido(){
		return this.idPedido;
	}
	public void setIdPedido(Integer parm){
		this.idPedido = parm;
	}

	public Integer getIdProduto(){
		return this.idProduto;
	}
	public void setIdProduto(Integer parm){
		this.idProduto = parm;
	}

	public Double getQuantidade(){
		return this.quantidade;
	}
	public void setQuantidade(Double parm){
		this.quantidade = parm;
	}

	public Double getValorTotal(){
		return this.valorTotal;
	}
	public void setValorTotal(Double parm){
		this.valorTotal = parm;
	}

	public Double getValorDesconto(){
		return this.valorDesconto;
	}
	public void setValorDesconto(Double parm){
		this.valorDesconto = parm;
	}

	public Double getValorAcrescimo(){
		return this.valorAcrescimo;
	}
	public void setValorAcrescimo(Double parm){
		this.valorAcrescimo = parm;
	}

	public Double getBaseCalculoIcms(){
		return this.baseCalculoIcms;
	}
	public void setBaseCalculoIcms(Double parm){
		this.baseCalculoIcms = parm;
	}

	public Double getAliquotaIcms(){
		return this.aliquotaIcms;
	}
	public void setAliquotaIcms(Double parm){
		this.aliquotaIcms = parm;
	}

	public Double getAliquotaIpi(){
		return this.aliquotaIpi;
	}
	public void setAliquotaIpi(Double parm){
		this.aliquotaIpi = parm;
	}
    public Integer getIdColetaPreco() {
        return idColetaPreco;
    }
    public void setIdColetaPreco(Integer idColetaPreco) {
        this.idColetaPreco = idColetaPreco;
    }
    public String getDescricaoItem() {
        return descricaoItem;
    }
    public void setDescricaoItem(String descricaoItem) {
        this.descricaoItem = descricaoItem;
    }
    public String getCodigoProduto() {
        return codigoProduto;
    }
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    public Double getValorFrete() {
        return valorFrete;
    }
    public void setValorFrete(Double valorFrete) {
        this.valorFrete = valorFrete;
    }

}
