package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class PrestacaoContasViagemDTO implements IDto {
	
	public static final String APROVADA = "Aprovada";
	public static final String NAO_APROVADA = "Não Aprovada";
	public static final String AGUARDANDO_CONFERENCIA = "Aguardando Conferência";
	public static final String EM_CONFERENCIA = "Em Conferência";
	public static final String EM_CORRECAO = "Em Correção";
	
	
	private Integer idPrestacaoContasViagem;
	private Integer idResponsavel;
	private Integer idAprovacao;
	private Integer idSolicitacaoServico;
	private Integer idEmpregado;
	private Timestamp dataHora;
	private String situacao;
	
	private String listItens;
	private String itensPrestacaoContasViagemSerialize;
	
	private String complemJustificativaAutorizacao;
	private Integer idJustificativaAutorizacao;
	private String aprovado;
	
	private Integer idItemTrabalho;
	private Integer idTarefa;
	
	private String corrigir;
	
	Collection<ItemPrestacaoContasViagemDTO> listaItemPrestacaoContasViagemDTO;
	
	public Collection<ItemPrestacaoContasViagemDTO> getListaItemPrestacaoContasViagemDTO() {
		return listaItemPrestacaoContasViagemDTO;
	}
	public void setListaItemPrestacaoContasViagemDTO(
			Collection<ItemPrestacaoContasViagemDTO> listaItemPrestacaoContasViagemDTO) {
		this.listaItemPrestacaoContasViagemDTO = listaItemPrestacaoContasViagemDTO;
	}
	private static final long serialVersionUID = 1L;

	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public Integer getIdAprovacao() {
		return idAprovacao;
	}
	public void setIdAprovacao(Integer idAprovacao) {
		this.idAprovacao = idAprovacao;
	}
	public Integer getIdEmpregado() {
		return idEmpregado;
	}
	public void setIdEmpregado(Integer idEmpregado) {
		this.idEmpregado = idEmpregado;
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
	public String getListItens() {
		return listItens;
	}
	public void setListItens(String listItens) {
		this.listItens = listItens;
	}
	public Integer getIdPrestacaoContasViagem() {
		return idPrestacaoContasViagem;
	}
	public void setIdPrestacaoContasViagem(Integer idPrestacaoContasViagem) {
		this.idPrestacaoContasViagem = idPrestacaoContasViagem;
	}
	public Integer getIdSolicitacaoServico() {
		return idSolicitacaoServico;
	}
	public void setIdSolicitacaoServico(Integer idSolicitacaoServico) {
		this.idSolicitacaoServico = idSolicitacaoServico;
	}
	public String getItensPrestacaoContasViagemSerialize() {
		return itensPrestacaoContasViagemSerialize;
	}
	public void setItensPrestacaoContasViagemSerialize(
			String itensPrestacaoContasViagemSerialize) {
		this.itensPrestacaoContasViagemSerialize = itensPrestacaoContasViagemSerialize;
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
	public String getAprovado() {
		return aprovado;
	}
	public void setAprovado(String aprovado) {
		this.aprovado = aprovado;
	}
	public Integer getIdItemTrabalho() {
		return idItemTrabalho;
	}
	public void setIdItemTrabalho(Integer idItemTrabalho) {
		this.idItemTrabalho = idItemTrabalho;
	}
	public Integer getIdTarefa() {
		return idTarefa;
	}
	public void setIdTarefa(Integer idTarefa) {
		this.idTarefa = idTarefa;
	}
	public String getCorrigir() {
		return corrigir;
	}
	public void setCorrigir(String corrigir) {
		this.corrigir = corrigir;
	}

}
