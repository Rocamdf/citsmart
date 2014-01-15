package br.com.centralit.citcorpore.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import br.com.centralit.citcorpore.bean.FormulaDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.comm.server.XmlReadDtdAgente;
import br.com.centralit.citcorpore.metainfo.script.ScriptRhinoJSExecute;
import br.com.centralit.citcorpore.metainfo.util.RuntimeScript;
import br.com.centralit.citcorpore.negocio.FormulaService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.snmp.SNMPManager;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilTratamentoArquivos;

public class ThreadProcessaInventario extends Thread {
	//public static Semaphore performanceDataSemaphoreInventario = new Semaphore(1);
	private NetMapDTO netMapDTO;
	private boolean finish = false;
	private boolean breakThread = false;
	//---
	Socket echoSocket = null;
	PrintWriter out = null;
	BufferedReader in = null;	
	public void run() {
		if (netMapDTO == null){
			finish = true;
			return;
		}
		netMapDTO.setNovoIC(false);
		netMapDTO.setForce(false);
		SimpleDateFormat spd = new SimpleDateFormat("yyyyMMdd");
		Date dataAtual = UtilDatas.getDataAtual();
		String dataAtualInv = spd.format(dataAtual).trim();
		//---
		boolean agenteLocalDOTNET = false;
		boolean agenteServerWindows = false;
		boolean agenteSNMP = false;
		String nameOrIp = "";
		if (netMapDTO.getNome() != null) {
			nameOrIp = netMapDTO.getNome();
		} else {
			nameOrIp = netMapDTO.getIp();
		}
		FormulaService formulaService = null;
		try {
			formulaService = (FormulaService) ServiceLocator
					.getInstance().getService(
							FormulaService.class, null);
		} catch (ServiceException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}			
		MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.verificandoOAtivo"+"#" + nameOrIp;
		File fXX = new File(MonitoraAtivosDiscovery.DIRETORIO_ARQUIVOS_INVENTARIO + "CITSMART_INVENTORY_" + nameOrIp + "_" + dataAtualInv + ".BKP");
		if (!fXX.exists()){
			String ipServidorInv = null;
			try {
				ipServidorInv = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.IP_SERVIDOR_INVENTARIO, null);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			/*
			if (ipServidorInv == null){
				MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO_COMPL = "";
				MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "IP DO SERVIDOR DE INVENTARIO NÃO CONFIGURADO! ENTRE EM PARAMETROS E CONFIGURE!";
				finish = true;
				return;
			}	
			*/		
			boolean ok = false;
			MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.capturandoInventario"+"#" + nameOrIp;
			try {
				if (formulaService != null){
					Collection col = formulaService.findByIdentificador(FormulaDTO.FORMULA_INVENTORY_PROCESS_BEFORE_CAPTURE);
					if (col != null && col.size() > 0){
						FormulaDTO formulaDTO = (FormulaDTO) col.iterator().next();
						if (formulaDTO != null){
	        		   		String strScript = formulaDTO.getConteudo();
	        		   		if (strScript != null && !strScript.trim().equalsIgnoreCase("")){
	        		   			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
	        		   			RuntimeScript runtimeScript = new RuntimeScript();
	        		   			Context cx = Context.enter();
	        		   			Scriptable scope = cx.initStandardObjects();	
	        		   			scope.put("listaInfo", scope, null);
	        		   			scope.put("fileName", scope, MonitoraAtivosDiscovery.DIRETORIO_ARQUIVOS_INVENTARIO + "CITSMART_INVENTORY_" + nameOrIp + "_" + dataAtualInv + ".XML");
	        		   			scope.put("netMapDTO", scope, netMapDTO);
	        		   			scope.put("thread", scope, this);
	        		   			scope.put("out", scope, System.out);
	        		   			scope.put("dataAtualInv", scope, dataAtualInv);
	        		   			scope.put("RuntimeScript", scope, runtimeScript);
	        		   			Object retorno = scriptExecute.processScript(cx, scope, strScript, ThreadProcessaInventario.class.getName() + "_" + FormulaDTO.FORMULA_INVENTORY_PROCESS_SAVE);
	        		   		}												
						}
					}
				}
				if (finish){
					return;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
			fXX = new File(MonitoraAtivosDiscovery.DIRETORIO_ARQUIVOS_INVENTARIO + "CITSMART_INVENTORY_" + nameOrIp + "_" + dataAtualInv + ".XML");
			if (fXX.exists()){
				ok = true;
			}else{
				agenteLocalDOTNET = geraInventarioAtivoAgenteLocal(nameOrIp, dataAtualInv);
				if (!agenteLocalDOTNET) { // Se nao tem AGENTE LOCAL .NET, tenta AGENTE SERVER
					if (ipServidorInv != null && !ipServidorInv.trim().equalsIgnoreCase("")){
						agenteServerWindows = geraInventarioAtivo(ipServidorInv, nameOrIp, nameOrIp, dataAtualInv);
					}
					if (!agenteServerWindows) { // Se nao tem AGENTE SERVER .NET, tenta SNMP
						agenteSNMP = geraInventarioAtivoSNMP(nameOrIp, dataAtualInv);
						if (agenteSNMP){
							MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.inventarioDoAtivo"+"#" + nameOrIp + "#"+"mostrarStatusInventario.caturadoComSucesso";
							ok = true;
						}else{
							MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.inventarioDoAtivo"+"#" + nameOrIp + "#"+"mostrarStatusInventario.problemaAoCapturarInformações";
						}
					} else {
						// Deu certo, entao grava na lista para processamento
						MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.inventarioDoAtivo"+"#" + nameOrIp + "#"+"mostrarStatusInventario.caturadoComSucesso";
						ok = true;
					}
				} else {
					// Deu certo, entao grava na lista para processamento
					MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.inventarioDoAtivo"+"#" + nameOrIp + "#"+"mostrarStatusInventario.caturadoComSucesso";
					ok = true;
				}
			}
			if (ok){
				boolean erroAux = false;
				MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.processamentoOInventarioDoAtivo"+"#" + nameOrIp;

				String fileName = MonitoraAtivosDiscovery.DIRETORIO_ARQUIVOS_INVENTARIO	+ "CITSMART_INVENTORY_" + nameOrIp + "_" + dataAtualInv + ".XML";
				File f = new File(fileName);
				if (!f.exists()){
					MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.arquivoDeInvetarioNaoExiste"+"#" + nameOrIp;
					finish = true;
					return;
				}
				f = null;
				StringBuffer strBuffer = null;
				try {
					strBuffer = UtilTratamentoArquivos.lerFileTXTGenerateStringBuffer(fileName);
				} catch (Exception e) {
					e.printStackTrace();
					erroAux = true;
				}
				if (!erroAux) {
					XmlReadDtdAgente xmlReadDtdAgente = new XmlReadDtdAgente();
					List<ItemConfiguracaoDTO> list = null;
					try {
						if (strBuffer != null) {
							String aux = strBuffer.toString();
							if (aux.indexOf("<") > -1) {
								aux = aux.substring(aux.indexOf("<") );
								list = xmlReadDtdAgente.XmlReadDtdAgent(aux);
							}
						}
					} catch (ServiceException e) {
						System.out.println("CITSMART --> PROBLEMAS NO PROCESSAMENTO DO ARQUIVO: " + fileName);
						e.printStackTrace();
						erroAux = true;
					} catch (LogicException e) {
						System.out.println("CITSMART --> PROBLEMAS NO PROCESSAMENTO DO ARQUIVO: " + fileName);
						e.printStackTrace();
						erroAux = true;
					} catch (Exception e) {
						System.out.println("CITSMART --> PROBLEMAS NO PROCESSAMENTO DO ARQUIVO: " + fileName);
						e.printStackTrace();
						erroAux = true;
					}
					ItemConfiguracaoService serviceItem = null;
					try {
						serviceItem = (ItemConfiguracaoService) ServiceLocator
								.getInstance().getService(
										ItemConfiguracaoService.class, null);
					} catch (ServiceException e) {
						e.printStackTrace();
						erroAux = true;
					} catch (Exception e) {
						e.printStackTrace();
						erroAux = true;
					}
					if (!erroAux) {
						try {
							if (list != null) {
								if (!breakThread){
									try {
										if (formulaService != null){
											Collection col = formulaService.findByIdentificador(FormulaDTO.FORMULA_INVENTORY_PROCESS_SAVE);
											if (col != null && col.size() > 0){
												FormulaDTO formulaDTO = (FormulaDTO) col.iterator().next();
												if (formulaDTO != null){
							        		   		String strScript = formulaDTO.getConteudo();
							        		   		if (strScript != null && !strScript.trim().equalsIgnoreCase("")){
							        		   			ScriptRhinoJSExecute scriptExecute = new ScriptRhinoJSExecute();
							        		   			RuntimeScript runtimeScript = new RuntimeScript();
							        		   			Context cx = Context.enter();
							        		   			Scriptable scope = cx.initStandardObjects();	
							        		   			scope.put("listaInfo", scope, list);
							        		   			scope.put("fileName", scope, fileName);
							        		   			scope.put("netMapDTO", scope, netMapDTO);
							        		   			scope.put("thread", scope, this);
							        		   			scope.put("out", scope, System.out);
							        		   			scope.put("dataAtualInv", scope, dataAtualInv);
							        		   			scope.put("RuntimeScript", scope, runtimeScript);
							        		   			Object retorno = scriptExecute.processScript(cx, scope, strScript, ThreadProcessaInventario.class.getName() + "_" + FormulaDTO.FORMULA_INVENTORY_PROCESS_SAVE);
							        		   		}												
												}
											}
										}
										if (finish){
											return;
										}
									} catch (Exception e) {
										e.printStackTrace();
									}
									/*
									 * Alteracao - Emauri - 27/11/2013
									 */									
									//performanceDataSemaphoreInventario.acquireUninterruptibly();
									try{
										serviceItem.create(list, null);
									}catch(Exception e){
										e.printStackTrace();
									}finally{
										//performanceDataSemaphoreInventario.release();
									}
									/*
									 * Fim - Alteracao - Emauri - 27/11/2013
									 */										
									String fileNameBkp = fileName.replaceAll(".XML", ".BKP");
									UtilTratamentoArquivos.copyFile(fileName, fileNameBkp);
									File fAux = new File(fileName);
									fAux.delete();
									fAux = null;
									netMapDTO.setForce(false);
									netMapDTO.setDataInventario(new java.sql.Date(UtilDatas.getDataHoraAtual().getTime()));
								}else{
									netMapDTO.setForce(true);
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
						}finally{
							serviceItem = null;
						}
					}
					serviceItem = null;
				}							
			}
		}else{
			MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO = "mostrarStatusInventario.oAtivo"+"#<b>" + nameOrIp + "</b>#"+"mostrarStatusInventario.jaTemInventarioCapturadoParaHoje";
			netMapDTO.setForce(false);
		}
		fXX = null;
		finish = true;
	}
	private boolean geraInventarioAtivoSNMP(String nomeHostIP, String dataAtualInv) {
		try {
			String xml = SNMPManager.getSNMPXML(nomeHostIP, MonitoraAtivosDiscovery.PORTA_SNMP, null);
			if (xml != null && !xml.trim().equalsIgnoreCase("")){
				UtilTratamentoArquivos.geraFileTxtFromString(MonitoraAtivosDiscovery.DIRETORIO_ARQUIVOS_INVENTARIO + "CITSMART_INVENTORY_" + nomeHostIP + "_" + dataAtualInv + ".XML", xml);
				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean geraInventarioAtivoAgenteLocal(String nomeHostIPServer, String dataAtualInv) {
		return geraInventarioAtivo(nomeHostIPServer, "localhost", nomeHostIPServer, dataAtualInv);
	}

	public boolean geraInventarioAtivo(String nomeHostIPServer,
			String nomeHostIPInventario, String nomeGerarFile,
			String dataAtualInv) {
		boolean retorno = false;

		try {
			echoSocket = new Socket(nomeHostIPServer, MonitoraAtivosDiscovery.PORTA_AGENTE_DOTNET);
			out = new PrintWriter(echoSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(
					echoSocket.getInputStream()));
		} catch (UnknownHostException e) {
			//System.err.println("Don't know about host: localhost.");
			try {
				if (out != null){
					out.close();
				}
			}catch(Exception eX1){}	
			try {
				if (in != null){
					in.close();
				}
			}catch(Exception eX1){}				
			try {
				// Encerra o echoSocket
				if(echoSocket!=null && !echoSocket.isClosed()){
					echoSocket.close();
				}
			} catch (IOException eZ) {
				System.out.println("Problema ao encerrar o socket: " + eZ);
				e.printStackTrace();
			}	
			echoSocket = null;
			out = null;
			in = null;
			return false;
		} catch (IOException e) {
			//System.err.println("Couldn't get I/O for the connection to: localhost.");
			try {
				if (out != null){
					out.close();
				}
			}catch(Exception eX1){}	
			try {
				if (in != null){
					in.close();
				}
			}catch(Exception eX1){}				
			try {
				// Encerra o echoSocket
				if(echoSocket!=null && !echoSocket.isClosed()){
					echoSocket.close();
				}
			} catch (IOException eZ) {
				System.out.println("Problema ao encerrar o socket: " + eZ);
				e.printStackTrace();
			}	
			echoSocket = null;
			out = null;
			in = null;			
			return false;
		}

		try{
			out.println("GETINVENT:" + nomeHostIPInventario + "\n");
		}catch(Exception e){
			try {
				// Encerra o echoSocket
				if(echoSocket!=null && !echoSocket.isClosed()){
					echoSocket.close();
				}
			} catch (IOException eZ) {
				System.out.println("Problema ao encerrar o socket: " + eZ);
				e.printStackTrace();
			}	
		}
		// out.println("INVENTORY:" + nomeHostIPInventario + "\n");
		String answer = "";
		List lst = new ArrayList();
		try {
			answer = in.readLine();
			if (answer.trim().equalsIgnoreCase("NOK")) {
				out.println("INVENTORY:" + nomeHostIPInventario + "\n");
			} else {
				lst.add(answer);
			}
		} catch (Exception e) {
			try {
				if (out != null){
					out.close();
				}
			}catch(Exception eX1){}	
			try {
				if (in != null){
					in.close();
				}
			}catch(Exception eX1){}			
			try {
				// Encerra o echoSocket
				if(echoSocket!=null && !echoSocket.isClosed()){
					echoSocket.close();
				}
			} catch (IOException eX) {
				System.out.println("Problema ao encerrar o socket: " + e);
				eX.printStackTrace();
			}
			echoSocket = null;
			out = null;
			in = null;			
			return false;
		}
		while (true) {
			try {
				if (finish){
					break;
				}				
				answer = in.readLine();
				if (answer == null){
					break;
				}
				if (answer.trim().equalsIgnoreCase("NOK")) {
					break;
				}
				lst.add(answer);
				// System.out.println(answer);
				if (answer.trim().equalsIgnoreCase("</CONTENT>")) {
					lst.add("</REQUEST>");
					break;
				}
			} catch (Exception e) {
				break;
			}
		}
		if (!finish){
			try {
				if (lst != null && lst.size() > 0) {
					UtilTratamentoArquivos.geraFileTXT(
							MonitoraAtivosDiscovery.DIRETORIO_ARQUIVOS_INVENTARIO + "CITSMART_INVENTORY_"
									+ nomeGerarFile + "_" + dataAtualInv + ".XML",
							lst);
					retorno = true;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try{
			out.println("<EOF>\n");
		}catch(Exception e){
		}
		try {
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			echoSocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		out = null;
		in = null;
		echoSocket = null;

		return retorno;
	}
	
	public NetMapDTO getNetMapDTO() {
		return netMapDTO;
	}
	public void setNetMapDTO(NetMapDTO netMapDTO) {
		this.netMapDTO = netMapDTO;
	}
	public boolean isFinish() {
		return finish;
	}
	public void setFinish(boolean finish) {
		this.finish = finish;
	}
	public boolean isBreakThread() {
		return breakThread;
	}
	public void setBreakThread(boolean breakThread) {
		this.breakThread = breakThread;
	}
	@Override
	public void interrupt() {
		finish = true;
		try {
			if (out != null){
				out.close();
			}
		}catch(Exception eX1){}	
		try {
			if (in != null){
				in.close();
			}
		}catch(Exception eX1){}				
		try {
			// Encerra o echoSocket
			if(echoSocket!=null && !echoSocket.isClosed()){
				echoSocket.close();
			}
		} catch (IOException eZ) {
		}	
		echoSocket = null;
		out = null;
		in = null;		
		super.interrupt();
	}
}
