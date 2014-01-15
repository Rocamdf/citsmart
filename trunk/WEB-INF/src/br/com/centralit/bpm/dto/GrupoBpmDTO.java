package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class GrupoBpmDTO implements IDto {
	
	private Integer idGrupo;
	private String sigla;
	private String[] emails;
	
	public Integer getIdGrupo() {
		return idGrupo;
	}
	public void setIdGrupo(Integer idGrupo) {
		this.idGrupo = idGrupo;
	}
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
    public String[] getEmails() {
        return emails;
    }
    public void setEmails(String[] emails) {
        this.emails = emails;
    }
	
}
