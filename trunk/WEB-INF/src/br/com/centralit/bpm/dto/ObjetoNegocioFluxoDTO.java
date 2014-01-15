package br.com.centralit.bpm.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.citframework.dto.IDto;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ObjetoNegocio") 
public class ObjetoNegocioFluxoDTO implements IDto {
    @XmlElement(name = "IdResponsavel")
	private Integer idResponsavel;
    
    @XmlElement(name = "IdGrupoAtual")
	private Integer idGrupoAtual;
    
    @XmlElement(name = "IdGrupoNivel1")
	private Integer idGrupoNivel1;
	
	public Integer getIdResponsavel() {
		return idResponsavel;
	}
	public void setIdResponsavel(Integer idResponsavel) {
		this.idResponsavel = idResponsavel;
	}
	public Integer getIdGrupoAtual() {
		return idGrupoAtual;
	}
	public void setIdGrupoAtual(Integer idGrupoAtual) {
		this.idGrupoAtual = idGrupoAtual;
	}
	public Integer getIdGrupoNivel1() {
		return idGrupoNivel1;
	}
	public void setIdGrupoNivel1(Integer idGrupoNivel1) {
		this.idGrupoNivel1 = idGrupoNivel1;
	}
	
}
