package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class ControleFinanceiroViagemDTO implements IDto {

	private static final long serialVersionUID = 1L;

	private Integer idControleFinanceiroViagem;
	private Integer idResponsavel;
	private Integer idMoeda;
	private Timestamp dataHora;
	private String situacao;
	private String observacoes;

	private Integer idContrato;
	private Integer idSolicitacaoServico;
	private String nomeMoeda;
	
	private Integer idResponsavelCompras;
	

	// Para ControleFinanceiroViagemImprevisto

	private Integer idSolicitacao;
	private Date dataInicio;

	private Integer idItemControleFinanceiroViagem;
	private Integer idFormaPagamento;
	private Integer idAdiantamentoViagem;
	private Integer idFornecedor;
	private Integer idJustificativa;
	private Integer idEmpregado;
	private Integer idTipoMovimFinanceiraViagem;
	private String complementoJustificativa;
	private Double quantidade;
	private Double valorUnitario;
	private Double valorAdiantamento;
	private String tipoPassagem;
	private String localizador;
	private String assento;
	private String nomeFornecedor;
	private String nomeTipoMovimFinanceira;
	private Double adiantamento;
	private String classificacao;
	private Date prazoCotacao;
	private Integer idTipoMovimenta;
	private Integer unitario;
	private String itemSerialiados;
	private Collection<ItemControleFinanceiroViagemDTO> colItens;
	
	private Integer paginaSelecionada;

	private String classifica;

	private Date dataFim;

	private RequisicaoViagemDTO requisicaoViagemDto;
	
	private Integer idIntegrante;
	
	private String confirma;

	public Integer getIdSolicitacao() {
		return idSolicitacao;
	}

	public void setIdSolicitacao(Integer idSolicitacao) {
		this.idSolicitacao = idSolicitacao;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public Integer getIdControleFinanceiroViagem() {
		return idControleFinanceiroViagem;
	}

	public void setIdControleFinanceiroViagem(Integer idControleFinanceiroViagem) {
		this.idControleFinanceiroViagem = idControleFinanceiroViagem;
	}

	public Integer getIdResponsavel() {
		return idResponsavel;
	}

	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}

	public Integer getIdMoeda() {
		return idMoeda;
	}

	public void setIdMoeda(Integer idMoeda) {
		this.idMoeda = idMoeda;
	}

	public Timestamp getDataHora() {
		return dataHora;
	}

	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public RequisicaoViagemDTO getRequisicaoViagemDto() {
		return requisicaoViagemDto;
	}

	public void setRequisicaoViagemDto(RequisicaoViagemDTO requisicaoViagemDto) {
		this.requisicaoViagemDto = requisicaoViagemDto;
	}

	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}

	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}

	public Integer getIdContrato() {
		return idContrato;
	}

	public void setIdContrato(Integer idContrato) {
		this.idContrato = idContrato;
	}

	/**
	 * @return the nomeMoeda
	 */
	public String getNomeMoeda() {
		return nomeMoeda;
	}

	/**
	 * @param nomeMoeda
	 *            the nomeMoeda to set
	 */
	public void setNomeMoeda(String nomeMoeda) {
		this.nomeMoeda = nomeMoeda;
	}

	public Integer getIdItemControleFinanceiroViagem() {
		return idItemControleFinanceiroViagem;
	}

	public void setIdItemControleFinanceiroViagem(
			Integer idItemControleFinanceiroViagem) {
		this.idItemControleFinanceiroViagem = idItemControleFinanceiroViagem;
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

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
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


	public Integer getIdTipoMovimFinanceiraViagem() {
		return idTipoMovimFinanceiraViagem;
	}

	public void setIdTipoMovimFinanceiraViagem(
			Integer idTipoMovimFinanceiraViagem) {
		this.idTipoMovimFinanceiraViagem = idTipoMovimFinanceiraViagem;
	}

	public Integer getIdTipoMovimenta() {
		return idTipoMovimenta;
	}

	public void setIdTipoMovimenta(Integer idTipoMovimenta) {
		this.idTipoMovimenta = idTipoMovimenta;
	}

	public Integer getUnitario() {
		return unitario;
	}

	public void setUnitario(Integer unitario) {
		this.unitario = unitario;
	}

	public String getClassifica() {
		return classifica;
	}

	public void setClassifica(String classifica) {
		this.classifica = classifica;
	}

	public Integer getPaginaSelecionada() {
		return paginaSelecionada;
	}

	public void setPaginaSelecionada(Integer paginaSelecionada) {
		this.paginaSelecionada = paginaSelecionada;
	}

	public String getItemSerialiados() {
		return itemSerialiados;
	}

	public void setItemSerialiados(String itemSerialiados) {
		this.itemSerialiados = itemSerialiados;
	}

	public Collection<ItemControleFinanceiroViagemDTO> getColItens() {
		return colItens;
	}

	public void setColItens(Collection<ItemControleFinanceiroViagemDTO> colItens) {
		this.colItens = colItens;
	}

	public Integer getIdIntegrante() {
		return idIntegrante;
	}

	public void setIdIntegrante(Integer idIntegrante) {
		this.idIntegrante = idIntegrante;
	}

	public String getConfirma() {
		return confirma;
	}

	public void setConfirma(String confirma) {
		this.confirma = confirma;
	}
	public Integer getIdResponsavelCompras() {
		return idResponsavelCompras;
	}

	public void setIdResponsavelCompras(Integer idResponsavelCompras) {
		this.idResponsavelCompras = idResponsavelCompras;
	}
}
