package br.com.centralit.citcorpore.bean;

import br.com.citframework.dto.IDto;

public class FaturaOSDTO implements IDto {
	private Integer idFatura;
	private Integer idOs;

	public Integer getIdFatura(){
		return this.idFatura;
	}
	public void setIdFatura(Integer parm){
		this.idFatura = parm;
	}

	public Integer getIdOs(){
		return this.idOs;
	}
	public void setIdOs(Integer parm){
		this.idOs = parm;
	}

}
