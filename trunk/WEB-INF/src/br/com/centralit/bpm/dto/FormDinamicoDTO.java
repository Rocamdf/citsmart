package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class FormDinamicoDTO implements IDto {
	private Integer idVisao;
	private String descricao;
	private String tipoVisao;
	private String situacao;
	private String classeName;
	private String identificador;

	public Integer getIdVisao() {
		return idVisao;
	}
	public void setIdVisao(Integer idVisao) {
		this.idVisao = idVisao;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getTipoVisao() {
		return tipoVisao;
	}
	public void setTipoVisao(String tipoVisao) {
		this.tipoVisao = tipoVisao;
	}
	public String getSituacao() {
		return situacao;
	}
	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}
	public String getClasseName() {
		return classeName;
	}
	public void setClasseName(String classeName) {
		this.classeName = classeName;
	}
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

}
