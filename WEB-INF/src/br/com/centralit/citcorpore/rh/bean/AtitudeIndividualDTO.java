package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;

public class AtitudeIndividualDTO implements IDto {
	private Integer idAtitudeIndividual;
	private String descricao;
	private String detalhe;
	
	public String getDetalhe() {
		return detalhe;
	}
	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}
	

	public Integer getIdAtitudeIndividual() {
		return idAtitudeIndividual;
	}
	public void setIdAtitudeIndividual(Integer idAtitudeIndividual) {
		this.idAtitudeIndividual = idAtitudeIndividual;
	}

	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	
}