package br.com.centralit.citcorpore.comm.server;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NetMapService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

/**
 * @author Maycon.Fernandes
 * 
 */
public class NetDiscover implements Runnable {

	NetMapDTO netMapDto = new NetMapDTO();
	private List<NetMapDTO> listNetMap = new ArrayList<NetMapDTO>();

	public static void main(String[] args) throws IOException {
		new Thread(new NetDiscover()).start();
	}

	public List<NetMapDTO> listaIpsAtivos() throws ServiceException, Exception, LogicException {
		List<NetMapDTO> lstIpsAtivos = getListNetMap();
		lstIpsAtivos = this.gravarDados(lstIpsAtivos);
		return lstIpsAtivos;
	}

	/**
	 * @return valor do atributo listNetMapAux.
	 * @throws Exception
	 * @throws ServiceException
	 */
	public List<NetMapDTO> getListNetMap() throws ServiceException, LogicException,  Exception {
		Integer i = 0;
		List<NetMapDTO> listaRetorno = new ArrayList<NetMapDTO>();
		List<String> listaVmapFiles = new ArrayList<String>();
		File arqv;// = new File(this.getParametroCaminhoArquivoNmap() + "nmapFile" + i);
		//if (arqv.exists())
		//	arqv.delete();	
		try {
	
			Process exec = null;
			String caminhoNetMap = (this.getParametroCaminhoArquivoNmap() == null ? "" : this.getParametroCaminhoArquivoNmap().trim()); 
			if (new File(caminhoNetMap).isDirectory()) {
				caminhoNetMap += File.separator;
				String [] arrayFaixaIp = {}; 
				arrayFaixaIp = this.getParametroFaixaIPNmap();
				for(String faixaIp : arrayFaixaIp){
					arqv = new File(caminhoNetMap + "nmapFile" + i);
					if (arqv.exists())
						arqv.delete();
					try {
						System.out.println("Realizando port scan..." + faixaIp.trim());
						exec = Runtime.getRuntime().exec(new String[] { this.getParametroCaminhoNmap(), "-sP", faixaIp.trim(), "-oN", caminhoNetMap + "nmapFile" + i });
						new PrintStream( exec.getInputStream() ).start();
						exec.waitFor();					
						listaVmapFiles.add(caminhoNetMap + "nmapFile" + i);
						i++;
					}catch (IOException er) {
						throw new LogicException("Parâmentro de Configuração do Caminho nmap inválido");
					}
				}				
				// Pega Arquivo Gerado pelo nmap
				for(String nmapFile : listaVmapFiles){
					arqv = new File(nmapFile);
					if (arqv.exists()) {
						boolean flag = true;
						arqv = new File(nmapFile);
						Scanner arq = new Scanner(arqv);
						while (flag) {
							String linha = arq.nextLine();
							// Flag para identificar fim do arquivo ser encontrar
							// retorna false
							if (linha.substring(0, 11).equalsIgnoreCase("# Nmap done"))
								flag = false;
							if (!arq.hasNext()) {
								arq.close();
								arq = null;
								Thread.sleep(1000);
								arqv = new File(nmapFile);
								arq = new Scanner(arqv);
							}
						}
						// Chama metodo que irá fazer leitura de arquivo nmap
						// Pegar lista Preenchida pelo metodo netDiscover
						listaRetorno.addAll(readFile(arqv));
					}				
				}

			} else {
				throw new LogicException("Parâmentro de Configuração de Diretório Arquivo NetMap não Encontrado");
			}	
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return listaRetorno;
	}
	
	class PrintStream extends Thread {
		java.io.InputStream __is = null;

		public PrintStream(java.io.InputStream is) {
			__is = is;
		}

		public void run() {
			try {
				while (this != null) {
					int _ch = __is.read();
					if (_ch != -1){
						//System.out.print((char) _ch);
					}
					else
						break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}	

	private ParametroCorporeService getServiceParametro() throws ServiceException, Exception {
		ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
		return parametroService;
	}

	/**
	 * metodo para percorre arquivo e preencher obj netmap
	 * 
	 * @param file
	 *            diretorio do arquivo
	 */
	public List<NetMapDTO> readFile(File file) {
		try {
			listNetMap = new ArrayList<NetMapDTO>();
			Scanner arq = new Scanner(file);
			while (arq.hasNext()) {
				String linha = arq.nextLine();
				if (linha.length() > 0) {
					// Identifica se é o final do arquivo
					if (!linha.substring(0, 11).equalsIgnoreCase("# Nmap done")) {
						// Verifica se é ip
						if (linha.substring(0, 9).equalsIgnoreCase("Nmap scan")) {

								getNetMapDto();

								if (linha.length() > 38) {
									String texto = linha.toString();
									Pattern p = Pattern.compile("\\(.*\\)$");
									Matcher m = p.matcher(texto);
									String ip;
									if (m.find()) {
										ip = m.group();
										ip = ip.replace('(', ' ').replace(')', ' ').replaceAll(" ", "");
										ip = ip.trim();
										this.netMapDto.setIp(ip);
									}

								} else {
									this.netMapDto.setIp(linha.substring(21, linha.length()).replaceAll("[^0-9.]",""));
								}
								this.netMapDto.setNome(linha.substring(21, linha.length()));
								//this.netMapDto.setIcNovo("true");
								listNetMap.add(this.netMapDto);
							// Veririca se mac

						} else if (linha.substring(0, 11).equalsIgnoreCase("MAC Address")) {
							this.netMapDto.setMac(linha.substring(13, 30));
//							A linha abaixo foi comentada por estar duplicando os IPs no inventario
//							listNetMap.add(this.netMapDto);
							getNetMapDto();
						}
					}
				}
			}
			arq.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		file.getFreeSpace();
		return listNetMap;
	}


	/**
	 * Grava a lista de dados obtidos do NMap.
	 * Se os dados ja estiverem gravados eles serao atualizados.
	 * 
	 * @param lst Lista de dados NMap
	 */
	private List<NetMapDTO> gravarDados(List<NetMapDTO> lst) {
		List<NetMapDTO> lstRetorno = new ArrayList<NetMapDTO>();
		try {
			ItemConfiguracaoService itemConfService = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			if (lst != null) {
				for (NetMapDTO netMapDTO : lst) {

					NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
					ItemConfiguracaoDTO itemConfiguracaoDTO = new ItemConfiguracaoDTO();
					List<NetMapDTO> lstNetMap = netMapService.verificarExistenciaIp(netMapDTO);
					netMapDTO.setDate(Util.getSqlDataAtual());
					// Verifica existencia, se existir faz update se nao grava
					if (lstNetMap != null && !lstNetMap.isEmpty()) {
						NetMapDTO mapDTO = new NetMapDTO();
						mapDTO = lstNetMap.get(0);
						mapDTO.setDate(Util.getSqlDataAtual());
						mapDTO.setMac(netMapDTO.getMac());
						netMapDTO.setIcNovo("false");
						netMapService.update(mapDTO);
					} else {
						netMapDTO.setIcNovo("true");
						netMapService.create(netMapDTO);
						itemConfiguracaoDTO.setIdentificacao(netMapDTO.getIp());
						itemConfiguracaoDTO.setDataInicio(netMapDTO.getDate());						
					    Collection result = itemConfService.findByIdItemConfiguracaoPai(itemConfiguracaoDTO);					 
					    if(result == null || result.size() == 0){
					    	itemConfService.create(itemConfiguracaoDTO);
					    }
					}
					lstRetorno.add(netMapDTO);

				}
			} else {
				System.out.println("Não foi encontrado IP na rede!");
			}
		} catch (ServiceException e) {
			System.out.println("Problema ao gravar dados NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Problema ao gravar dados NetMap: " + e.getMessage());
			e.printStackTrace();
		}
		return lstRetorno;
	}


	public NetMapDTO getNetMapDto() {
		return this.netMapDto = new NetMapDTO();
	}

	public void run() {
		try {
			NetDiscover netDiscover = new NetDiscover();
			List<NetMapDTO> lst = netDiscover.getListNetMap();
			// Metodo Gravar dados
			this.gravarDados(lst);

		} catch (IOException e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (ServiceException e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("Problema ao obter a lista NetMap: " + e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private String[] getParametroFaixaIPNmap() throws ServiceException, LogicException, Exception {
		String faixaIp = "";
		String [] arrayIp = {""};
		if (this.getParametroFaixaIp() != null && !this.getParametroFaixaIp().equalsIgnoreCase("")) {
			faixaIp = this.getParametroFaixaIp();
			
			if(faixaIp.trim().indexOf(";") != -1){
				arrayIp = faixaIp.split(";");
			}else {
				throw new LogicException("Parâmentro de Configuração de Faixa de IP inválido");
			}
			return arrayIp;
		} else {
			System.out.println("Caminho Nmap não definida em Parâmetro de Sistema 'Faixa Ip'!");
		}
		return null;
	}
	
	private String getParametroFaixaIp()throws Exception {
		String faixaIp = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.FaixaIp, " ");
		return faixaIp;
	}
	
	private String getParametroCaminhoArquivoNmap() throws Exception {
		String caminhoArquivoNetMap = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CaminhoArquivoNetMap, " ");
		return caminhoArquivoNetMap;
	}

	private String getParametroCaminhoNmap() throws Exception {
		String caminhoNmap = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CaminhoNmap, " ");	
		return caminhoNmap;
	}
}
