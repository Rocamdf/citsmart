package br.com.centralit.asterisk;

import java.io.IOException;
import java.util.ArrayList;
import org.asteriskjava.live.AsteriskChannel;
import org.asteriskjava.live.AsteriskServer;
import org.asteriskjava.live.DefaultAsteriskServer;
import org.asteriskjava.manager.ManagerConnectionState;

import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;

public class Asterisk {
	String AsteriskIP;
	String AsteriskLogin;
	String AsteriskSenha;
	AsteriskServer asteriskServer;

	public Asterisk() throws IOException {
		this.AsteriskIP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKIP, "0.0.0.0");
		this.AsteriskLogin = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKLOGIN, "admin");
		this.AsteriskSenha = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKSENHA, "0");
		this.asteriskServer = new DefaultAsteriskServer(this.AsteriskIP, this.AsteriskLogin, this.AsteriskSenha);
	}
	
	@SuppressWarnings("finally")
	public ArrayList<ChamadaDTO> telefonesChamando() throws IOException {
		ArrayList<ChamadaDTO> resultado = new ArrayList<ChamadaDTO>();
		ChamadaDTO chamadaDTO;
		try {
			for (AsteriskChannel asteriskChannel : asteriskServer.getChannels())
			{
				//System.out.println(asteriskChannel);
				//Ramal Tocando!
				if (asteriskChannel.getState().toString().equals("RINGING")){
					chamadaDTO = new ChamadaDTO();
					chamadaDTO.setNumeroOrigem(asteriskChannel.getDialingChannel().getCallerId().getNumber().toString());
					chamadaDTO.setNumeroDestino(asteriskChannel.getCallerId().getNumber().toString());
					resultado.add(chamadaDTO);
				}
			}
		}catch(Exception e){
			e.getStackTrace();
		}finally {
			return resultado;
		}
	}
	
	public void close(){
		this.asteriskServer.getManagerConnection().getState();
		this.asteriskServer.getManagerConnection().getState();
		if (this.asteriskServer.getManagerConnection().getState().equals(ManagerConnectionState.CONNECTED) ||
			this.asteriskServer.getManagerConnection().getState().equals(ManagerConnectionState.RECONNECTING))
		{
			this.asteriskServer.getManagerConnection().logoff();
		}
		//this.asteriskServer.getManagerConnection().setSocketTimeout(0); Parece que para de vez mas não ativa o timeout novamente.
		//this.asteriskServer.shutdown();
		this.asteriskServer = null;
		System.gc();
	}
	
}