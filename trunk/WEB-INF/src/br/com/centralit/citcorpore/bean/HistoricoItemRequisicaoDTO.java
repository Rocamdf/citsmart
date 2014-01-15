package br.com.centralit.citcorpore.bean;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class HistoricoItemRequisicaoDTO implements IDto {
	private Integer idHistorico;
	private Integer idItemRequisicao;
	private Integer idResponsavel;
	private Timestamp dataHora;
	private String acao;
	private String situacao;
    private String complemento;
    
    public Integer getIdHistorico() {
        return idHistorico;
    }
    public void setIdHistorico(Integer idHistorico) {
        this.idHistorico = idHistorico;
    }
    public Integer getIdItemRequisicao() {
        return idItemRequisicao;
    }
    public void setIdItemRequisicao(Integer idItemRequisicao) {
        this.idItemRequisicao = idItemRequisicao;
    }
    public Integer getIdResponsavel() {
        return idResponsavel;
    }
    public void setIdResponsavel(Integer idResponsavel) {
        this.idResponsavel = idResponsavel;
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
    public String getComplemento() {
        return complemento;
    }
    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }
	public String getAcao() {
		return acao;
	}
	public void setAcao(String acao) {
		this.acao = acao;
	}
    
}
