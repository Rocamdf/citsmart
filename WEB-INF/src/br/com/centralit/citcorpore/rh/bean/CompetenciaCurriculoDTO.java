package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;


public class CompetenciaCurriculoDTO implements IDto {
	private Integer idCompetencia;
	private String descricaoCompetencia;
	private Integer idCurriculo;
	
	private String imagemEmailprincipal;
	
	
	public String getImagemEmailprincipal() {
		return imagemEmailprincipal;
	}
	public void setImagemEmailprincipal(String imagemEmailprincipal) {
		this.imagemEmailprincipal = imagemEmailprincipal;
	}
	public Integer getIdCurriculo() {
		return idCurriculo;
	}
	public void setIdCurriculo(Integer idCurriculo) {
		this.idCurriculo = idCurriculo;
	}
	public Integer getIdCompetencia() {
		return idCompetencia;
	}
	public void setIdCompetencia(Integer idCompetencia) {
		this.idCompetencia = idCompetencia;
	}
	public String getDescricaoCompetencia() {
		return descricaoCompetencia;
	}
	public void setDescricaoCompetencia(String descricaoCompetencia) {
		this.descricaoCompetencia = descricaoCompetencia;
	}
	
}