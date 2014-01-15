package br.com.centralit.bpm.dto;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class AtribuicaoFluxoDTO implements IDto {
	private Integer idAtribuicao;
	private Integer idItemTrabalho;
	private String tipo;
	private Integer idUsuario;
	private Integer idGrupo;
	private Timestamp dataHora;

	public Integer getIdAtribuicao() {
		return idAtribuicao;
	}
	public void setIdAtribuicao(Integer idAtribuicao) {
		this.idAtribuicao = idAtribuicao;
	}
	public Integer getIdItemTrabalho() {
		return idItemTrabalho;
	}
	public void setIdItemTrabalho(Integer idItemTrabalho) {
		this.idItemTrabalho = idItemTrabalho;
	}
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public Timestamp getDataHora() {
		return dataHora;
	}
	public void setDataHora(Timestamp dataHora) {
		this.dataHora = dataHora;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
}
