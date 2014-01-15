package br.com.centralit.citsmart.rest.bean;

import java.sql.Timestamp;

import javax.servlet.http.HttpSession;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.util.UtilDatas;

public class RestSessionDTO implements IDto {
	private HttpSession httpSession;
	private Timestamp creation;
	private long maxTime;
	
	public HttpSession getHttpSession() {
		return httpSession;
	}
	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}
	
	public Timestamp getCreation() {
		return creation;
	}
	public void setCreation(Timestamp creation) {
		this.creation = creation;
	}
	public long getMaxTime() {
		return maxTime;
	}
	public void setMaxTime(long maxTime) {
		this.maxTime = maxTime;
	}
	public String getSessionID() {
		if (httpSession != null)
			return httpSession.getId();
		else
			return null;
	}
	public boolean isValid() {
		if (this.httpSession == null)
			return false;
		//return true;
		long tempo;
		try {
			tempo = UtilDatas.calculaDiferencaTempoEmMilisegundos(UtilDatas.getDataHoraAtual(), this.creation) / 1000;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return tempo <= maxTime;
	}
	public UsuarioDTO getUser() {
		if (!this.isValid())
			return null;
		return (UsuarioDTO) this.getHttpSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE");
    }
	public Integer getUserId() {
		UsuarioDTO userDto = getUser();
		if (userDto == null)
			return null;
		return userDto.getIdUsuario();
	}
	public Integer getDptoId() {
		UsuarioDTO userDto = getUser();
		if (userDto == null)
			return null;
		return userDto.getIdUnidade();
	}	
}
