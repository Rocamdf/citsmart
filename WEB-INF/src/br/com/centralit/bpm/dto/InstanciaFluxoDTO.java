package br.com.centralit.bpm.dto;

import java.sql.Timestamp;
import java.util.List;

import br.com.citframework.dto.IDto;

public class InstanciaFluxoDTO implements IDto {
	private Integer idInstancia;
	private Integer idFluxo;
	private Timestamp dataHoraCriacao;
	private String situacao;
	private Timestamp dataHoraFinalizacao;
	
	private List<ObjetoInstanciaFluxoDTO> colObjetos;
	private FluxoDTO fluxoDto;

	public Integer getIdInstancia() {
		return idInstancia;
	}
	public void setIdInstancia(Integer idInstancia) {
		this.idInstancia = idInstancia;
	}
	public Integer getIdFluxo(){
		return this.idFluxo;
	}
	public void setIdFluxo(Integer parm){
		this.idFluxo = parm;
	}

	public Timestamp getDataHoraCriacao(){
		return this.dataHoraCriacao;
	}
	public void setDataHoraCriacao(Timestamp parm){
		this.dataHoraCriacao = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}

	public Timestamp getDataHoraFinalizacao(){
		return this.dataHoraFinalizacao;
	}
	public void setDataHoraFinalizacao(Timestamp parm){
		this.dataHoraFinalizacao = parm;
	}
	public FluxoDTO getFluxoDto() {
		return fluxoDto;
	}
	public void setFluxoDto(FluxoDTO fluxoDto) {
		this.fluxoDto = fluxoDto;
	}
	public List<ObjetoInstanciaFluxoDTO> getColObjetos() {
		return colObjetos;
	}
	public void setColObjetos(List<ObjetoInstanciaFluxoDTO> colObjetos) {
		this.colObjetos = colObjetos;
	}
}
