package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class CertificacaoRequisicaoPessoalDTO implements IDto {
	private Integer idCertificacao;
	private String descricaoCertificacao;
	private String versaoCertificacao;
	private Integer validadeCertificacao;
	
	private Integer idCurriculo;
	
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}

	public Integer getIdCertificacao() {
		return idCertificacao;
	}
	public void setIdCertificacao(Integer idCertificacao) {
		this.idCertificacao = idCertificacao;
	}
	public String getDescricaoCertificacao() {
		return descricaoCertificacao;
	}
	public void setDescricaoCertificacao(String descricaoCertificacao) {
		this.descricaoCertificacao = descricaoCertificacao;
	}
	public String getVersaoCertificacao() {
		return versaoCertificacao;
	}
	public void setVersaoCertificacao(String versaoCertificacao) {
		this.versaoCertificacao = versaoCertificacao;
	}
	public Integer getValidadeCertificacao() {
		return validadeCertificacao;
	}
	public void setValidadeCertificacao(Integer validadeCertificacao) {
		this.validadeCertificacao = validadeCertificacao;
	}


	
	
	
}