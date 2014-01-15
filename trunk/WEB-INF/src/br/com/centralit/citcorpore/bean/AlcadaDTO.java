package br.com.centralit.citcorpore.bean;

import java.util.ArrayList;
import java.util.Collection;

import br.com.citframework.dto.IDto;

public class AlcadaDTO implements IDto {
	private Integer idAlcada;
	private String nomeAlcada;
	private String tipoAlcada;
	private String situacao;
	private ArrayList<LimiteAlcadaDTO> listaDeLimites;
	private String listLimites;
	
    private Collection<LimiteAlcadaDTO> colLimites;
	
	private Collection<EmpregadoDTO> colResponsaveis;

	public Integer getIdAlcada(){
		return this.idAlcada;
	}
	public void setIdAlcada(Integer parm){
		this.idAlcada = parm;
	}

	public String getNomeAlcada(){
		return this.nomeAlcada;
	}
	public void setNomeAlcada(String parm){
		this.nomeAlcada = parm;
	}

	public String getSituacao(){
		return this.situacao;
	}
	public void setSituacao(String parm){
		this.situacao = parm;
	}
    public String getTipoAlcada() {
        return tipoAlcada;
    }
    public void setTipoAlcada(String tipoAlcada) {
        this.tipoAlcada = tipoAlcada;
    }
    public Collection<EmpregadoDTO> getColResponsaveis() {
        return colResponsaveis;
    }
    public Collection<LimiteAlcadaDTO> getColLimites() {
        return colLimites;
    }
    public void setColLimites(Collection<LimiteAlcadaDTO> colLimites) {
        this.colLimites = colLimites;
    }
    public void setColResponsaveis(Collection<EmpregadoDTO> colResponsaveis) {
        this.colResponsaveis = colResponsaveis;
    }
	public ArrayList<LimiteAlcadaDTO> getListaDeLimites() {
		return listaDeLimites;
	}
	public void setListaDeLimites(ArrayList<LimiteAlcadaDTO> listaDeLimites) {
		this.listaDeLimites = listaDeLimites;
	}
	public String getListLimites() {
		return listLimites;
	}
	public void setListLimites(String listLimites) {
		this.listLimites = listLimites;
	}

}
