package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class ItemControleFinanceiroViagemDTO implements IDto {

	private static final long serialVersionUID = 1L;
	
	private Integer idItemControleFinanceiroViagem;
	private Integer idControleFinanceiroViagem;
	private Integer idFormaPagamento;
	private Integer idAdiantamentoViagem;
	private Integer idFornecedor;
	private Integer idJustificativa;
	private Integer idSolicitacaoServico;
	private Integer idEmpregado;
	private Integer idTipoMovimFinanceiraViagem;
	private String complementoJustificativa;
	private Double quantidade;
	private Double valorUnitario;
	private Double valorAdiantamento;
	private String tipoPassagem;
	private String localizador;
	private String assento;
	private String situacao;
	private Date dataFim;
	private String observacao;
	private String origemCompras;
	
	private String nomeFornecedor;
	private String nomeTipoMovimFinanceira;
	
	private Double adiantamento;
	
	private String classificacao;
	
	private Date prazoCotacao;
	
	private String dataCotacao;
	private String horaCotacao;
	
	private Timestamp dataHoraPrazoCotacao;
	
	//Data em que o item foi comprado ou adiantado para o Integrante da viagem
	private Timestamp dataExecucao;
	
	//Campo para auxiliar na exclusão de itens
	private Integer auxiliarIdentificador;

	
	
	public Integer getIdItemControleFinanceiroViagem() {
		return idItemControleFinanceiroViagem;
	}

	public void setIdItemControleFinanceiroViagem(
			Integer idItemControleFinanceiroViagem) {
		this.idItemControleFinanceiroViagem = idItemControleFinanceiroViagem;
	}

	public Integer getIdControleFinanceiroViagem() {
		return idControleFinanceiroViagem;
	}

	public void setIdControleFinanceiroViagem(Integer idControleFinanceiroViagem) {
		this.idControleFinanceiroViagem = idControleFinanceiroViagem;
	}

	public Integer getIdFormaPagamento() {
		return idFormaPagamento;
	}

	public void setIdFormaPagamento(Integer idFormaPagamento) {
		this.idFormaPagamento = idFormaPagamento;
	}

	public Integer getIdAdiantamentoViagem() {
		return idAdiantamentoViagem;
	}

	public void setIdAdiantamentoViagem(Integer idAdiantamentoViagem) {
		this.idAdiantamentoViagem = idAdiantamentoViagem;
	}

	public Integer getIdFornecedor() {
		return idFornecedor;
	}

	public void setIdFornecedor(Integer idFornecedor) {
		this.idFornecedor = idFornecedor;
	}

	public Integer getIdJustificativa() {
		return idJustificativa;
	}

	public void setIdJustificativa(Integer idJustificativa) {
		this.idJustificativa = idJustificativa;
	}

	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}

	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}

	public Integer getIdTipoMovimFinanceiraViagem() {
		return idTipoMovimFinanceiraViagem;
	}

	public void setIdTipoMovimFinanceiraViagem(Integer idTipoMovimFinanceiraViagem) {
		this.idTipoMovimFinanceiraViagem = idTipoMovimFinanceiraViagem;
	}

	public String getComplementoJustificativa() {
		return complementoJustificativa;
	}

	public void setComplementoJustificativa(String complementoJustificativa) {
		this.complementoJustificativa = complementoJustificativa;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getValorUnitario() {
		return valorUnitario;
	}

	public void setValorUnitario(Double valorUnitario) {
		this.valorUnitario = valorUnitario;
	}

	public Double getValorAdiantamento() {
		return valorAdiantamento;
	}

	public void setValorAdiantamento(Double valorAdiantamento) {
		this.valorAdiantamento = valorAdiantamento;
	}

	public String getTipoPassagem() {
		return tipoPassagem;
	}

	public void setTipoPassagem(String tipoPassagem) {
		this.tipoPassagem = tipoPassagem;
	}

	public String getLocalizador() {
		return localizador;
	}

	public void setLocalizador(String localizador) {
		this.localizador = localizador;
	}

	public String getAssento() {
		return assento;
	}

	public void setAssento(String assento) {
		this.assento = assento;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getOrigemCompras() {
		return origemCompras;
	}

	public void setOrigemCompras(String origemCompras) {
		this.origemCompras = origemCompras;
	}

	public String getNomeFornecedor() {
		return nomeFornecedor;
	}

	public void setNomeFornecedor(String nomeFornecedor) {
		this.nomeFornecedor = nomeFornecedor;
	}

	public String getNomeTipoMovimFinanceira() {
		return nomeTipoMovimFinanceira;
	}

	public void setNomeTipoMovimFinanceira(String nomeTipoMovimFinanceira) {
		this.nomeTipoMovimFinanceira = nomeTipoMovimFinanceira;
	}

	public Double getAdiantamento() {
		return adiantamento;
	}

	public void setAdiantamento(Double adiantamento) {
		this.adiantamento = adiantamento;
	}

	public String getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(String classificacao) {
		this.classificacao = classificacao;
	}

	public Date getPrazoCotacao() {
		return prazoCotacao;
	}

	public void setPrazoCotacao(Date prazoCotacao) {
		this.prazoCotacao = prazoCotacao;
	}

	public String getDataCotacao() {
		return dataCotacao;
	}

	public void setDataCotacao(String dataCotacao) {
		this.dataCotacao = dataCotacao;
	}

	public String getHoraCotacao() {
		return horaCotacao;
	}

	public void setHoraCotacao(String horaCotacao) {
		this.horaCotacao = horaCotacao;
	}

	public Timestamp getDataHoraPrazoCotacao() {
		return dataHoraPrazoCotacao;
	}

	public void setDataHoraPrazoCotacao(Timestamp dataHoraPrazoCotacao) {
		this.dataHoraPrazoCotacao = dataHoraPrazoCotacao;
	}

	public Timestamp getDataExecucao() {
		return dataExecucao;
	}

	public void setDataExecucao(Timestamp dataExecucao) {
		this.dataExecucao = dataExecucao;
	}

	public Integer getAuxiliarIdentificador() {
		return auxiliarIdentificador;
	}

	public void setAuxiliarIdentificador(Integer auxiliarIdentificador) {
		this.auxiliarIdentificador = auxiliarIdentificador;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}