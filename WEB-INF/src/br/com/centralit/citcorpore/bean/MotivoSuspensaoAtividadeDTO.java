package br.com.centralit.citcorpore.bean;

import java.sql.Date;

import br.com.citframework.dto.IDto;

public class MotivoSuspensaoAtividadeDTO implements IDto {
	private Integer idMotivo;
	private String descricao;
	private Date dataFim;

	public Integer getIdMotivo(){
		return this.idMotivo;
	}
	public void setIdMotivo(Integer parm){
		this.idMotivo = parm;
	}

	public String getDescricao(){
		return this.descricao;
	}
	public void setDescricao(String parm){
		this.descricao = parm;
	}
	public Date getDataFim() {
		return dataFim;
	}
	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
