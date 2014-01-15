package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import br.com.centralit.citcorpore.util.Enumerados.AcaoItemRequisicaoProduto;
import br.com.centralit.citcorpore.util.Enumerados.SituacaoItemRequisicaoProduto;
import br.com.citframework.dto.IDto;

public class ItemRequisicaoProdutoDTO implements IDto {
	private Integer idItemRequisicaoProduto;
	private Integer idSolicitacaoServico;
	private Integer idProduto;
    private Integer idMarca;  
	private Integer idUnidadeMedida;
	private String descricaoItem;
	private String especificacoes;
	private Double quantidade;
	private String marcaPreferencial;
	private Double precoAproximado;
	private String situacao;
	private Double percVariacaoPreco;
	private Integer idParecerValidacao;
    private Integer idParecerAutorizacao;
    private Integer idItemCotacao;
    private String tipoAtendimento;
    private String descrTipoAtendimento;
    private String tipoIdentificacao;
    private Double qtdeCotada;
    private String aprovaCotacao;
    private Double valorAprovado;
    
    private Integer idCategoriaProduto;
    private String codigoProduto;
    private String nomeProduto;
    
	private String validado;
	private Integer idJustificativaValidacao;
	private String complemJustificativaValidacao;
	
    private String autorizado;
    private Integer idJustificativaAutorizacao;
    private String complemJustificativaAutorizacao;

    private String siglaUnidadeMedida;
    private String descrSituacao;
    private Double qtdeAprovada;
    
    private Timestamp dataHoraSolicitacao;
    private Timestamp dataHoraLimite;    
    private String dataHoraLimiteStr;
    
    private Integer idProjeto;
    private Integer idCentroCusto;
    private String nomeCentroCusto;
    private String nomeProjeto;
    private String nomeCategoria;
    
    private Integer idEnderecoEntrega;
    private String enderecoStr;
    
    private UsuarioDTO usuarioDto;

    public Integer getIdItemRequisicaoProduto(){
		return this.idItemRequisicaoProduto;
	}
	public void setIdItemRequisicaoProduto(Integer parm){
		this.idItemRequisicaoProduto = parm;
	}

	public Integer getIdSolicitacaoServico() {
        return idSolicitacaoServico;
    }
    public String getNomeProduto() {
        return nomeProduto;
    }
    public void setNomeProduto(String nomeProduto) {
        this.nomeProduto = nomeProduto;
    }
    public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
        this.idSolicitacaoServico = idSolicitacaoServico;
    }
    public Integer getIdUnidadeMedida(){
		return this.idUnidadeMedida;
	}
	public void setIdUnidadeMedida(Integer parm){
		this.idUnidadeMedida = parm;
	}

	public String getDescricaoItem(){
		return this.descricaoItem;
	}
	public void setDescricaoItem(String parm){
		this.descricaoItem = parm;
	}

	public String getEspecificacoes(){
		return this.especificacoes;
	}
	public void setEspecificacoes(String parm){
		this.especificacoes = parm;
	}

	public Double getQuantidade(){
		return this.quantidade;
	}
	public void setQuantidade(Double parm){
		this.quantidade = parm;
	}

	public String getMarcaPreferencial(){
		return this.marcaPreferencial;
	}
	public void setMarcaPreferencial(String parm){
		this.marcaPreferencial = parm;
	}

	public Double getPrecoAproximado(){
		return this.precoAproximado;
	}
	public void setPrecoAproximado(Double parm){
		this.precoAproximado = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
        this.descrSituacao = "";
        this.situacao = parm;
        try {
            if (this.situacao != null)
                this.descrSituacao = SituacaoItemRequisicaoProduto.valueOf(this.situacao.trim()).getDescricao();
        } catch (Exception e) {
            this.descrSituacao = this.situacao;
        }		
	}

	public Double getPercVariacaoPreco(){
		return this.percVariacaoPreco;
	}
	public void setPercVariacaoPreco(Double parm){
		this.percVariacaoPreco = parm;
	}
    public String getSiglaUnidadeMedida() {
        return siglaUnidadeMedida;
    }
    public void setSiglaUnidadeMedida(String siglaUnidadeMedida) {
        this.siglaUnidadeMedida = siglaUnidadeMedida;
    }
    public String getCodigoProduto() {
        return codigoProduto;
    }
    public void setCodigoProduto(String codigoProduto) {
        this.codigoProduto = codigoProduto;
    }
    public Integer getIdParecerValidacao() {
        return idParecerValidacao;
    }
    public void setIdParecerValidacao(Integer idParecerValidacao) {
        this.idParecerValidacao = idParecerValidacao;
    }
    public Integer getIdParecerAutorizacao() {
        return idParecerAutorizacao;
    }
    public void setIdParecerAutorizacao(Integer idParecerAutorizacao) {
        this.idParecerAutorizacao = idParecerAutorizacao;
    }
    public String getValidado() {
        return validado;
    }
    public void setValidado(String validado) {
        this.validado = validado;
    }
    public Integer getIdJustificativaValidacao() {
        return idJustificativaValidacao;
    }
    public void setIdJustificativaValidacao(Integer idJustificativaValidacao) {
        this.idJustificativaValidacao = idJustificativaValidacao;
    }
    public String getComplemJustificativaValidacao() {
        return complemJustificativaValidacao;
    }
    public void setComplemJustificativaValidacao(
            String complemJustificativaValidacao) {
        this.complemJustificativaValidacao = complemJustificativaValidacao;
    }
    public String getAutorizado() {
        return autorizado;
    }
    public void setAutorizado(String autorizado) {
        this.autorizado = autorizado;
    }
    public String getComplemJustificativaAutorizacao() {
        return complemJustificativaAutorizacao;
    }
    public void setComplemJustificativaAutorizacao(
            String complemJustificativaAutorizacao) {
        this.complemJustificativaAutorizacao = complemJustificativaAutorizacao;
    }
    public Integer getIdJustificativaAutorizacao() {
        return idJustificativaAutorizacao;
    }
    public void setIdJustificativaAutorizacao(Integer idJustificativaAutorizacao) {
        this.idJustificativaAutorizacao = idJustificativaAutorizacao;
    }
    public String getDescrSituacao() {
        return descrSituacao;
    }
    public void setDescrSituacao(String descrSituacao) {
        this.descrSituacao = descrSituacao;
    }
    public Double getQtdeAprovada() {
        return qtdeAprovada;
    }
    public void setQtdeAprovada(Double qtdeAprovada) {
        this.qtdeAprovada = qtdeAprovada;
    }
    public Timestamp getDataHoraSolicitacao() {
        return dataHoraSolicitacao;
    }
    public void setDataHoraSolicitacao(Timestamp dataHoraSolicitacao) {
        this.dataHoraSolicitacao = dataHoraSolicitacao;
    }
    public Integer getIdProjeto() {
        return idProjeto;
    }
    public void setIdProjeto(Integer idProjeto) {
        this.idProjeto = idProjeto;
    }
    public Integer getIdCentroCusto() {
        return idCentroCusto;
    }
    public void setIdCentroCusto(Integer idCentroCusto) {
        this.idCentroCusto = idCentroCusto;
    }
    public String getNomeCentroCusto() {
        return nomeCentroCusto;
    }
    public void setNomeCentroCusto(String nomeCentroCusto) {
        this.nomeCentroCusto = nomeCentroCusto;
    }
    public String getNomeProjeto() {
        return nomeProjeto;
    }
    public void setNomeProjeto(String nomeProjeto) {
        this.nomeProjeto = nomeProjeto;
    }
    public Integer getIdItemCotacao() {
        return idItemCotacao;
    }
    public void setIdItemCotacao(Integer idItemCotacao) {
        this.idItemCotacao = idItemCotacao;
    }
    public Date getDataSolicitacao() {
        if (dataHoraSolicitacao == null) return null;
        return new Date(dataHoraSolicitacao.getTime());
    }
    public String getTipoAtendimento() {
        return tipoAtendimento;
    }
    public void setTipoAtendimento(String tipoAtendimento) {
        this.tipoAtendimento = tipoAtendimento;
        this.descrTipoAtendimento = "";
        if (this.tipoAtendimento == null)
            return;
        if (this.tipoAtendimento.equalsIgnoreCase("C"))
            this.descrTipoAtendimento = "Compra";
        else if (this.tipoAtendimento.equalsIgnoreCase("E"))
            this.descrTipoAtendimento = "Estoque";
    }
    public String getDescrTipoAtendimento() {
        return descrTipoAtendimento;
    }
    public void setDescrTipoAtendimento(String descrTipoAtendimento) {
        this.descrTipoAtendimento = descrTipoAtendimento;
    }

    public void setIdCategoriaProduto(Integer idCategoriaProduto) {
        this.idCategoriaProduto = idCategoriaProduto;
    }
    public Integer getIdMarca() {
        return idMarca;
    }
    public void setIdMarca(Integer idMarca) {
        this.idMarca = idMarca;
    }
    public Integer getIdCategoriaProduto() {
        return idCategoriaProduto;
    }
    public String getNomeCategoria() {
        return nomeCategoria;
    }
    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }
    public Integer getIdProduto() {
        return idProduto;
    }
    public void setIdProduto(Integer idProduto) {
        this.idProduto = idProduto;
    }
    public String getTipoIdentificacao() {
        return tipoIdentificacao;
    }
    public void setTipoIdentificacao(String tipoIdentificacao) {
        this.tipoIdentificacao = tipoIdentificacao;
    }
    public Timestamp getDataHoraLimite() {
        return dataHoraLimite;
    }

    public void setDataHoraLimite(Timestamp dataHoraLimite) {
        this.dataHoraLimite = dataHoraLimite;
        if (dataHoraLimite != null) {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            this.dataHoraLimiteStr = format.format(dataHoraLimite);
        }
    }

    public String getDataHoraLimiteStr() {
        if (dataHoraLimite != null) {
            return dataHoraLimiteStr;
        } else {
            return "--";
        }
    }
    public String getAprovaCotacao() {
        return aprovaCotacao;
    }
    public void setAprovaCotacao(String aprovaCotacao) {
        this.aprovaCotacao = aprovaCotacao;
    }
    public void setDataHoraLimiteStr(String dataHoraLimiteStr) {
        this.dataHoraLimiteStr = dataHoraLimiteStr;
    }
    public Double getQtdeCotada() {
        return qtdeCotada;
    }
    public void setQtdeCotada(Double qtdeCotada) {
        this.qtdeCotada = qtdeCotada;
    }
    public Double getValorAprovado() {
        return valorAprovado;
    }
    public void setValorAprovado(Double valorAprovado) {
        this.valorAprovado = valorAprovado;
    }
    public UsuarioDTO getUsuarioDto() {
        return usuarioDto;
    }
    public void setUsuarioDto(UsuarioDTO usuarioDto) {
        this.usuarioDto = usuarioDto;
    }
	public Integer getIdEnderecoEntrega() {
		return idEnderecoEntrega;
	}
	public void setIdEnderecoEntrega(Integer idEnderecoEntrega) {
		this.idEnderecoEntrega = idEnderecoEntrega;
	}
	public String getEnderecoStr() {
		return enderecoStr;
	}
	public void setEnderecoStr(String enderecoStr) {
		this.enderecoStr = enderecoStr;
	}
	
	public boolean cotacaoIniciada() {
		if (this.situacao == null)
			return false;
		if (this.situacao.equals(SituacaoItemRequisicaoProduto.AguardandoCotacao.name()) && this.idItemCotacao != null)
			return true;
		return this.situacao.equals(SituacaoItemRequisicaoProduto.AguardandoAprovacaoCotacao.name())
				|| this.situacao.equals(SituacaoItemRequisicaoProduto.CotacaoNaoAprovada.name())
				|| this.situacao.equals(SituacaoItemRequisicaoProduto.AguardandoPedido.name())
				|| this.situacao.equals(SituacaoItemRequisicaoProduto.AguardandoEntrega.name())
				|| this.situacao.equals(SituacaoItemRequisicaoProduto.AguardandoInspecao.name())
				|| this.situacao.equals(SituacaoItemRequisicaoProduto.AguardandoInspecaoGarantia.name())
				|| this.situacao.equals(SituacaoItemRequisicaoProduto.InspecaoRejeitada.name());
	}
}
