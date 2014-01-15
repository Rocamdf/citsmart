package br.com.centralit.citcorpore.batch;

import java.io.IOException;
import java.util.ArrayList;

import br.com.centralit.asterisk.Asterisk;
import br.com.centralit.asterisk.ChamadaDTO;
import br.com.centralit.citajax.framework.AjaxReverse;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;

/**
 * @author euler.ramos
 * 
 */
public class MonitoraAsterisk extends Thread {
	final String nomeDaFuncaoJavaScript = "exibirNotificacaoAsterisk";
	static ArrayList<ChamadaDTO> ultimaLista = new ArrayList<ChamadaDTO>();
	static Asterisk asterisk;
	StringBuilder listatelefones = null;
	ArrayList<ChamadaDTO> listChamadaDto = new ArrayList<ChamadaDTO>();
	//int teste = 1;
	
	@Override
	public void run() {
		while (true) {
			String asteriskAtivo = ParametroUtil
					.getValorParametroCitSmartHashMap(
							ParametroSistema.SERVASTERISKATIVAR, "N");
			if (asteriskAtivo.equals("S")) {
				try {
					if (this.asterisk == null) {
						this.asterisk = new Asterisk();
					}
					listChamadaDto = asterisk.telefonesChamando();
					listatelefones = new StringBuilder();
					// Controle para evitar abrir popup mesmo quando o usuário
					// já visualizou a informação e fechou a tela, o sistema só
					// mostra se houve alguma mudança nas chamadas!
					for (ChamadaDTO chamadaDTO : listChamadaDto) {
						if (ultimaLista != null) {
							if (!ultimaLista.contains(chamadaDTO)) {
								listatelefones.append(chamadaDTO
										.getNumeroOrigem()
										+ ","
										+ chamadaDTO.getNumeroDestino() + "#");
							}
						}
					}
					ultimaLista = listChamadaDto;
//Código de teste simulando chamadas no asterisk
//Lembre-se que este teste é uma sobrecarga de ligações já que o sleep está configurado para 1 segundo.
//O código real não gera lista para os telefones já listados anteriormente.
/*					if (teste==1){
						AjaxReverse.executarAjaxReverseWithAllSessions(nomeDaFuncaoJavaScript,"6201,6222#6233248280,6250#6200,6201#6201,6202#6202,6203#6203,6204#6204,6205#6205,6206#6206,6207#6207,6208#6208,6209#6209,6210#");
						teste=0;
					} else {
						AjaxReverse.executarAjaxReverseWithAllSessions(nomeDaFuncaoJavaScript,"6201,1111#6233248280,2222#");
						teste=1;
					}
*/

					if (listatelefones.length() > 0) {
						// Enviando lista de novas chamadas ativas para os
						// computadores clientes.
						AjaxReverse.executarAjaxReverseWithAllSessions(nomeDaFuncaoJavaScript,listatelefones.toString());
					}
				} catch (IOException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}