package br.com.centralit.bpm.dto;

import br.com.citframework.dto.IDto;

public class UsuarioBpmDTO implements IDto {
	
	private Integer idUsuario;
	private String login;
    private String[] emails;
    private String nome;
		
	public Integer getIdUsuario() {
		return idUsuario;
	}
	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
    public String[] getEmails() {
        return emails;
    }
    public void setEmails(String[] emails) {
        this.emails = emails;
    }
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
    
	
}
