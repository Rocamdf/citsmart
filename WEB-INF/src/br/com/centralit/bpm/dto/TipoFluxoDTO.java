package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class TipoFluxoDTO implements IDto {
	private Integer idTipoFluxo;
	private String nomeFluxo;
	private String descricao;
	private String nomeClasseFluxo;

	public Integer getIdTipoFluxo(){
		return this.idTipoFluxo;
	}
	public void setIdTipoFluxo(Integer parm){
		this.idTipoFluxo = parm;
	}

	public String getNomeFluxo(){
		return this.nomeFluxo;
	}
	public void setNomeFluxo(String parm){
		this.nomeFluxo = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}
    public String getNomeClasseFluxo() {
        return nomeClasseFluxo;
    }
    public void setNomeClasseFluxo(String nomeClasseFluxo) {
        this.nomeClasseFluxo = nomeClasseFluxo;
    }

}
