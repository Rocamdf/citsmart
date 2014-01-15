package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class CtrlAsteriskDTO implements IDto {
	private String ramalTelefone;
	private String listaChamadas;
	
	public String getRamalTelefone() {
		return ramalTelefone;
	}
	public void setRamalTelefone(String ramalTelefone) {
		this.ramalTelefone = ramalTelefone;
	}
	public String getListaChamadas() {
		return listaChamadas;
	}
	public void setListaChamadas(String listaChamadas) {
		this.listaChamadas = listaChamadas;
	}
}
