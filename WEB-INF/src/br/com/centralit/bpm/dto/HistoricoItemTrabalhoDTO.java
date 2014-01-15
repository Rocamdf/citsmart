package br.com.centralit.bpm.dto;

import java.sql.Timestamp;

import br.com.citframework.dto.IDto;

public class HistoricoItemTrabalhoDTO implements IDto {
	private Integer idHistoricoItemTrabalho;
	private Integer idItemTrabalho;
	private Integer idResponsavel;
	private Integer idUsuario;
	private Integer idGrupo;
	private Timestamp dataHora;
	private String acao;

	public Integer getIdHistoricoItemTrabalho(){
		return this.idHistoricoItemTrabalho;
	}
	public void setIdHistoricoItemTrabalho(Integer parm){
		this.idHistoricoItemTrabalho = parm;
	}

	public Integer getIdItemTrabalho(){
		return this.idItemTrabalho;
	}
	public void setIdItemTrabalho(Integer parm){
		this.idItemTrabalho = parm;
	}

	public Integer getIdUsuario(){
		return this.idUsuario;
	}
	public void setIdUsuario(Integer parm){
		this.idUsuario = parm;
	}

	public Timestamp getDataHora(){
		return this.dataHora;
	}
	public void setDataHora(Timestamp parm){
		this.dataHora = parm;
	}

	public String getAcao(){
		return this.acao;
	}
	public void setAcao(String parm){
		this.acao = parm;
	}
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}

}
