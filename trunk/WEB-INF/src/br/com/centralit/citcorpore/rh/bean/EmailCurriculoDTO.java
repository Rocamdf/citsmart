package br.com.centralit.citcorpore.rh.bean;

import br.com.citframework.dto.IDto;
import br.com.citframework.util.Constantes;


public class EmailCurriculoDTO implements IDto {
	private Integer idEmail;
	private String descricaoEmail;
	private String principal;
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
	public Integer getIdEmail() {
		return idEmail;
	}
	public void setIdEmail(Integer idEmail) {
		this.idEmail = idEmail;
	}
    public String getDescricaoEmail() {
        return descricaoEmail;
    }
    public void setDescricaoEmail(String descricaoEmail) {
        this.descricaoEmail = descricaoEmail;
    }
    public String getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal;
		if (principal != null){
			if (principal.equalsIgnoreCase("S")){
				this.setImagemEmailprincipal("<img src=\"" + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/ok.png\" border=\"0\"/>");
			}else{
				this.setImagemEmailprincipal("");
			}
		}else{
			this.setImagemEmailprincipal("");
		}
    }
}