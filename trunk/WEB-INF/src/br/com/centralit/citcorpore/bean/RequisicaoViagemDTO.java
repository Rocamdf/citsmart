package br.com.centralit.citcorpore.bean;

import java.sql.Date;
import java.util.Collection;

public class RequisicaoViagemDTO extends SolicitacaoServicoDTO {
	
	 public static final String AGUARDANDO_PLANEJAMENTO = "Aguardando Planejamento";
	 public static final String REJEITADA_PLANEJAMENTO = "Rejeitada Planejamento";
	 public static final String AGUARDANDO_APROVACAO = "Aguardando Aprovacao";
	 public static final String NAO_APROVADA = "Não Aprovada";
	 public static final String AGUARDANDO_FINANCEIRO = "Aguardando Financeiro";
	 public static final String AGUARDANDO_PRESTACAOCONTAS = "Aguardando Prestação de Contas"; 
	 public static final String AGUARDANDO_CONFERENCIA = "Aguardando Conferência";
	 public static final String AGUARDANDO_CORRECAO = "Aguardando Correção";  
	 public static final String FINALIZADA = "Finalizada";  
	
	private static final long serialVersionUID = 1L;

	private Integer idCidadeOrigem;
	
	private Integer idCidadeDestino;
	
	private Integer idProjeto;
	
	private Integer idCentroCusto;
	
	private Integer idMotivoViagem;
	
	private Integer idAprovacao;
	
	private String descricaoMotivo;
	
	private Date dataInicioViagem;
	
	private Date dataFimViagem;
	
	private String rejeitada;
	
	private Integer qtdeDias;
	
	private Double valorTotalSolicitado;
	
	private String estado;
	private String tarefaIniciada;
	
	private Collection<IntegranteViagemDTO> integranteViagem;
	
	private String integranteViagemSerialize;
	
	//Tarefa Autorização
	private String autorizado;
	
    private Integer idJustificativaAutorizacao;
    
    private String complemJustificativaAutorizacao;
    
	private String nomeCidadeOrigem;
	
	private String nomeCidadeDestino;
	
	//Para ControleFinanceiroViagem
	
	private String observacoes;
	private Integer idControleFinanceiroViagem;
	private Integer idMoeda;
	
	private Integer idEmpregado;
	
	private String nomeMoeda;
	
	/**
	 * @return the nomeCidadeOrigem
	 */
	public String getNomeCidadeOrigem() {
		return nomeCidadeOrigem;
	}

	/**
	 * @param nomeCidadeOrigem the nomeCidadeOrigem to set
	 */
	public void setNomeCidadeOrigem(String nomeCidadeOrigem) {
		this.nomeCidadeOrigem = nomeCidadeOrigem;
	}

	/**
	 * @return the nomeCidadeDestino
	 */
	public String getNomeCidadeDestino() {
		return nomeCidadeDestino;
	}

	/**
	 * @param nomeCidadeDestino the nomeCidadeDestino to set
	 */
	public void setNomeCidadeDestino(String nomeCidadeDestino) {
		this.nomeCidadeDestino = nomeCidadeDestino;
	}

	/**
	 * @return the autorizado
	 */
	public String getAutorizado() {
		return autorizado;
	}

	/**
	 * @param autorizado the autorizado to set
	 */
	public void setAutorizado(String autorizado) {
		this.autorizado = autorizado;
	}

	/**
	 * @return the idJustificativaAutorizacao
	 */
	public Integer getIdJustificativaAutorizacao() {
		return idJustificativaAutorizacao;
	}

	/**
	 * @param idJustificativaAutorizacao the idJustificativaAutorizacao to set
	 */
	public void setIdJustificativaAutorizacao(Integer idJustificativaAutorizacao) {
		this.idJustificativaAutorizacao = idJustificativaAutorizacao;
	}

	/**
	 * @return the complemJustificativaAutorizacao
	 */
	public String getComplemJustificativaAutorizacao() {
		return complemJustificativaAutorizacao;
	}

	/**
	 * @param complemJustificativaAutorizacao the complemJustificativaAutorizacao to set
	 */
	public void setComplemJustificativaAutorizacao(String complemJustificativaAutorizacao) {
		this.complemJustificativaAutorizacao = complemJustificativaAutorizacao;
	}

	/**
	 * @return the idCidadeOrigem
	 */
	public Integer getIdCidadeOrigem() {
		return idCidadeOrigem;
	}

	/**
	 * @param idCidadeOrigem the idCidadeOrigem to set
	 */
	public void setIdCidadeOrigem(Integer idCidadeOrigem) {
		this.idCidadeOrigem = idCidadeOrigem;
	}

	/**
	 * @return the idCidadeDestino
	 */
	public Integer getIdCidadeDestino() {
		return idCidadeDestino;
	}

	/**
	 * @param idCidadeDestino the idCidadeDestino to set
	 */
	public void setIdCidadeDestino(Integer idCidadeDestino) {
		this.idCidadeDestino = idCidadeDestino;
	}

	/**
	 * @return the idProjeto
	 */
	public Integer getIdProjeto() {
		return idProjeto;
	}

	/**
	 * @param idProjeto the idProjeto to set
	 */
	public void setIdProjeto(Integer idProjeto) {
		this.idProjeto = idProjeto;
	}

	/**
	 * @return the idCentroCusto
	 */
	public Integer getIdCentroCusto() {
		return idCentroCusto;
	}

	/**
	 * @param idCentroCusto the idCentroCusto to set
	 */
	public void setIdCentroCusto(Integer idCentroCusto) {
		this.idCentroCusto = idCentroCusto;
	}

	/**
	 * @return the idMotivoViagem
	 */
	public Integer getIdMotivoViagem() {
		return idMotivoViagem;
	}

	/**
	 * @param idMotivoViagem the idMotivoViagem to set
	 */
	public void setIdMotivoViagem(Integer idMotivoViagem) {
		this.idMotivoViagem = idMotivoViagem;
	}

	/**
	 * @return the idAprovacao
	 */
	public Integer getIdAprovacao() {
		return idAprovacao;
	}

	/**
	 * @param idAprovacao the idAprovacao to set
	 */
	public void setIdAprovacao(Integer idAprovacao) {
		this.idAprovacao = idAprovacao;
	}

	/**
	 * @return the descricaoMotivo
	 */
	public String getDescricaoMotivo() {
		return descricaoMotivo;
	}

	/**
	 * @param descricaoMotivo the descricaoMotivo to set
	 */
	public void setDescricaoMotivo(String descricaoMotivo) {
		this.descricaoMotivo = descricaoMotivo;
	}

	/**
	 * @return the rejeitada
	 */
	public String getRejeitada() {
		return rejeitada;
	}

	/**
	 * @param rejeitada the rejeitada to set
	 */
	public void setRejeitada(String rejeitada) {
		this.rejeitada = rejeitada;
	}

	/**
	 * @return the qtdeDias
	 */
	public Integer getQtdeDias() {
		return qtdeDias;
	}

	/**
	 * @param qtdeDias the qtdeDias to set
	 */
	public void setQtdeDias(Integer qtdeDias) {
		this.qtdeDias = qtdeDias;
	}

	/**
	 * @return the valorTotalSolicitado
	 */
	public Double getValorTotalSolicitado() {
		return valorTotalSolicitado;
	}

	/**
	 * @param valorTotalSolicitado the valorTotalSolicitado to set
	 */
	public void setValorTotalSolicitado(Double valorTotalSolicitado) {
		this.valorTotalSolicitado = valorTotalSolicitado;
	}

	/**
	 * @return the estado
	 */
	public String getEstado() {
		return estado;
	}

	/**
	 * @param estado the estado to set
	 */
	public void setEstado(String estado) {
		this.estado = estado;
	}

	/**
	 * @return the integranteViagem
	 */
	public Collection<IntegranteViagemDTO> getIntegranteViagem() {
		return integranteViagem;
	}

	/**
	 * @param integranteViagem the integranteViagem to set
	 */
	public void setIntegranteViagem(Collection<IntegranteViagemDTO> integranteViagem) {
		this.integranteViagem = integranteViagem;
	}

	/**
	 * @return the integranteViagemSerialize
	 */
	public String getIntegranteViagemSerialize() {
		return integranteViagemSerialize;
	}

	/**
	 * @param integranteViagemSerialize the integranteViagemSerialize to set
	 */
	public void setIntegranteViagemSerialize(String integranteViagemSerialize) {
		this.integranteViagemSerialize = integranteViagemSerialize;
	}

	/**
	 * @return the dataInicioViagem
	 */
	public Date getDataInicioViagem() {
		return dataInicioViagem;
	}

	/**
	 * @param dataInicioViagem the dataInicioViagem to set
	 */
	public void setDataInicioViagem(Date dataInicioViagem) {
		this.dataInicioViagem = dataInicioViagem;
	}

	/**
	 * @return the dataFimViagem
	 */
	public Date getDataFimViagem() {
		return dataFimViagem;
	}

	/**
	 * @param dataFimViagem the dataFimViagem to set
	 */
	public void setDataFimViagem(Date dataFimViagem) {
		this.dataFimViagem = dataFimViagem;
	}

	public String getObservacoes() {
		return observacoes;
	}

	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public Integer getIdControleFinanceiroViagem() {
		return idControleFinanceiroViagem;
	}

	public void setIdControleFinanceiroViagem(Integer idControleFinanceiroViagem) {
		this.idControleFinanceiroViagem = idControleFinanceiroViagem;
	}

	public Integer getIdMoeda() {
		return idMoeda;
	}

	public void setIdMoeda(Integer idMoeda) {
		this.idMoeda = idMoeda;
	}

	public String getNomeMoeda() {
		return nomeMoeda;
	}

	public void setNomeMoeda(String nomeMoeda) {
		this.nomeMoeda = nomeMoeda;
	}

	public Integer getIdEmpregado() {
		return idEmpregado;
	}

	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
	}

	public String getTarefaIniciada() {
		return tarefaIniciada;
	}

	public void setTarefaIniciada(String tarefaIniciada) {
		this.tarefaIniciada = tarefaIniciada;
	}
	


}
