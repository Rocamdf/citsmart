package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class ExecucaoFluxoDTO implements IDto {
	
	private Integer idExecucao;
	private Integer idFluxo;
	private Integer idInstanciaFluxo;
	
	public Integer getIdExecucao() {
		return idExecucao;
	}
	public void setIdExecucao(Integer idExecucao) {
		this.idExecucao = idExecucao;
	}
	public Integer getIdFluxo() {
		return idFluxo;
	}
	public void setIdFluxo(Integer idFluxo) {
		this.idFluxo = idFluxo;
	}
	public Integer getIdInstanciaFluxo() {
		return idInstanciaFluxo;
	}
	public void setIdInstanciaFluxo(Integer idInstanciaFluxo) {
		this.idInstanciaFluxo = idInstanciaFluxo;
	}
	
}
