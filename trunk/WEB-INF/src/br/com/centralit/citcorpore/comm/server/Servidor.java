
package br.com.centralit.citcorpore.comm.server;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import br.com.centralit.citcorpore.bean.InventarioXMLDTO;
import br.com.centralit.citcorpore.bean.ItemConfiguracaoDTO;
import br.com.centralit.citcorpore.bean.NetMapDTO;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.centralit.citcorpore.negocio.InventarioXMLService;
import br.com.centralit.citcorpore.negocio.ItemConfiguracaoService;
import br.com.centralit.citcorpore.negocio.NetMapService;
import br.com.centralit.citcorpore.negocio.ParametroCorporeService;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.Util;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;
import br.com.citsmart.cliente.CriptoSignedUtil;
import br.com.citsmart.cliente.SignedInfo;

public class Servidor implements Runnable, ClientServer {
	static List<NetMapDTO> listnetMap = null;
	static ParametroCorporeDTO paramentroDTO = null;
	private String diretorioXmlAgente = "";
	private NetMapDTO netMapDTO;
	static List<NetMapDTO> listNetMapErro = new ArrayList<NetMapDTO>();

	public Servidor() {
	}

	public Servidor(NetMapDTO netMapDTO) {
		ParametroCorporeService parametroService;
		this.netMapDTO = netMapDTO;
		try {
			
			this.diretorioXmlAgente = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DiretorioXmlAgente, " ");
			
//			if(this.diretorioXmlAgente != null && this.diretorioXmlAgente.trim().equals("")){
//				parametroService = serviceParametroCorpore();
//				List<ParametroCorporeDTO> listDiretorioXmlAgente = parametroService.pesquisarParamentro(Enumerados.ParametroSistema.DiretorioXmlAgente.id(), Enumerados.ParametroSistema.DiretorioXmlAgente.getCampoParametroInternacionalizado((HttpServletRequest) pageContext.getRequest()));
//				
//				if ((paramentroDTO = (ParametroCorporeDTO) listDiretorioXmlAgente.get(0)).getValor() != null) {
//					this.diretorioXmlAgente = (paramentroDTO = (ParametroCorporeDTO) listDiretorioXmlAgente.get(0)).getValor();
//				}
//			}
//
//		} catch (ServiceException e) {
//			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<NetMapDTO> carregarListaIP(List<NetMapDTO> listNetMapDTO) throws InterruptedException {
		listnetMap = listNetMapDTO;
		listNetMapErro = new ArrayList<NetMapDTO>();
		for (NetMapDTO netMapDTO : listNetMapDTO) {
			Thread server =  new Thread(new Servidor(netMapDTO));
			server.start();
			server.join();
		}
		return listNetMapErro;
	}

	public List<NetMapDTO> carregarIP(NetMapDTO listNetMapDTO) throws InterruptedException {
		listnetMap = new ArrayList<NetMapDTO>();
		listNetMapErro = new ArrayList<NetMapDTO>();
		Thread server =  new Thread(new Servidor(netMapDTO));
		server.start();
		server.join();
		return listNetMapErro;
	}

	
	public static void executarPesquisaIPGerarInvenario() throws ServiceException, Exception {
		NetMapService netMapService = serviceNetMapService();
		Integer dias = 0;
		String diasInventario  = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.DiasInventario, " ");
//		if(diasInventario != null && diasInventario.trim().equalsIgnoreCase("")){
//			ParametroCorporeService parametroService = serviceParametroCorpore();
//			List<ParametroCorporeDTO> listDiasInventario = parametroService.pesquisarParamentro(Enumerados.ParametroSistema.DiasInventario.id(), Enumerados.ParametroSistema.DiasInventario.getCampoParametroInternacionalizado(request));
//			if ((paramentroDTO = (ParametroCorporeDTO) listDiasInventario.get(0)).getValor() != null) {
//				diasInventario = (paramentroDTO = (ParametroCorporeDTO) listDiasInventario.get(0)).getValor();
//			}
//		}
		
		try {  
			dias = Integer.parseInt(diasInventario);  
		} catch (NumberFormatException ex) {  
		    System.err.println(ex);   
		}  
		
		Date dataInventario = UtilDatas.getSqlDate(UtilDatas.incrementaDiasEmData(Util.getDataAtual(), -(new Integer(dias))));
		// Pesquisa ips para geracao de iventario, passa data como
		// parametro, para identificar apartir de que data será iventariado.
		listnetMap = (List<NetMapDTO>) netMapService.listIpByDataInventario(dataInventario);
		for (NetMapDTO netMapDTO : listnetMap) {
			Thread server =  new Thread(new Servidor(netMapDTO));
			server.start();
			server.join();
		}
	}

	/**
	 * @return
	 * @throws ServiceException
	 * @throws Exception
	 */
	private static ParametroCorporeService serviceParametroCorpore() throws ServiceException, Exception {
		ParametroCorporeService parametroService = (ParametroCorporeService) ServiceLocator.getInstance().getService(ParametroCorporeService.class, null);
		return parametroService;
	}

	private static NetMapService serviceNetMapService() throws ServiceException, Exception {
		NetMapService netMapService = (NetMapService) ServiceLocator.getInstance().getService(NetMapService.class, null);
		return netMapService;
	}

	public void run() {
		try {
			// Declaro o socket cliente
			Socket s = null;

			// Declaro a Stream de saida de dados
			ObjectOutputStream outObjects = null;
			try {
				// Cria o socket com o recurso desejado
				System.out.println("Conectando a " + netMapDTO.getIp() + "...	");
				s = new Socket(netMapDTO.getIp(), SERVER_PORT);
				s.setSoTimeout(90000);
				// Cria a Stream de saida de dados
				outObjects = new ObjectOutputStream(s.getOutputStream());

				List<String> parametrosEvento = new ArrayList<String>();
				parametrosEvento.add("INVENTORY");

				// Imprime uma linha para a stream de saída de dados
				SignedInfo signedInfo = CriptoSignedUtil.generateStringToSend(CITCorporeUtil.caminho_real_app + "/keysSec/citsmart.jks", CITCorporeUtil.caminho_real_app
						+ "/keysSec/citsmartcripto.jks", parametrosEvento.toString());

				outObjects.writeObject(signedInfo);
				outObjects.flush();
				boolean running = true;
				while (running) {
					String dadoRecebido = "";
					try {
						System.out.println("Recebendo dados do Socket..");
						ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
						signedInfo = (SignedInfo) ois.readObject();
					} catch (IOException e) {
						System.out.println("Problema ao receber dados pelo socket: " + e);
						e.printStackTrace();
						listNetMapErro.add(netMapDTO);
						running = false;
						break;
					}
					String dadoRecebidoAux = "";
					try {
						dadoRecebidoAux = CriptoSignedUtil.translateStringReceive(CITCorporeUtil.caminho_real_app + "/keysSec/citsmart.jks", CITCorporeUtil.caminho_real_app + "/keysSec/citsmartcripto.jks", signedInfo.getStrCripto(), signedInfo.getStrSigned());
					} catch (Exception e) {
						System.out.println("Problema ao descriptografar o texto de inventario: " + e);
						e.printStackTrace();
					}
					if (dadoRecebidoAux.equals("") || dadoRecebidoAux.equals("false")) {
						dadoRecebidoAux = null;
					}
					dadoRecebido = (dadoRecebidoAux != null ? new String(dadoRecebidoAux.getBytes()) : null);
					if (dadoRecebido != null) {
						try {
							getGravarItemConfiguracao(dadoRecebido);
							getGravarInventarioXml(dadoRecebido);
						} catch (Exception e) {
							System.out.println("Problema ao gravar o inventario: " + e);
							e.printStackTrace();
							listNetMapErro.add(netMapDTO);
							BufferedWriter br = new BufferedWriter(new FileWriter(new File(diretorioXmlAgente + this.getNomeArquivo() + ".xml")));
							br.write(dadoRecebido);
							br.close();
							running = false;
						}
						running = false;
					} else {
						running = false;
						listNetMapErro.add(netMapDTO);
						System.out.println("Mensagem não pode ser lida..");
					}
				}
			} catch (ConnectException e) {
				System.out.println("Conexão recusada..");
				listNetMapErro.add(netMapDTO);
			} finally {
				try {
					// Encerra o ServerSocket
					if(s!=null && !s.isClosed()){
						s.close();
					}
				} catch (IOException e) {
					System.out.println("Problema ao encerrar o socket: " + e);
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println("Problema ao gerar inventario manualmente: " + e);
			e.printStackTrace();
		}
	}

	private void getGravarInventarioXml(String dadoRecebido) {
		try {
			InventarioXMLDTO inventarioXMLDTO = new InventarioXMLDTO();
			InventarioXMLService inventarioXmlService = (InventarioXMLService) ServiceLocator.getInstance().getService(InventarioXMLService.class, null);
			inventarioXMLDTO.setIdNetMap(netMapDTO.getIdNetMap());
			inventarioXMLDTO.setConteudo(dadoRecebido);
			inventarioXMLDTO.setDatainicial(UtilDatas.getDataAtual());
			inventarioXMLDTO.setNome(this.getNomeArquivo());
			// Grava Xml Completo.
			inventarioXMLDTO = (InventarioXMLDTO) inventarioXmlService.create(inventarioXMLDTO);
		} catch (Exception e) {
			System.out.println("Problema ao gravar o inventario: " + e);
			e.printStackTrace();
		}
	}

	public void getGravarItemConfiguracao(String dadoRecebido) throws ServiceException, LogicException, Exception {
			XmlReadDtdAgente xmlReadDtdAgente = new XmlReadDtdAgente();
			List<ItemConfiguracaoDTO> list = xmlReadDtdAgente.XmlReadDtdAgent(dadoRecebido);
			ItemConfiguracaoService serviceItem = (ItemConfiguracaoService) ServiceLocator.getInstance().getService(ItemConfiguracaoService.class, null);
			serviceItem.create(list, null);
	}

	private String getNomeArquivo() {
		String nomeArquivo = netMapDTO.getIp() + "__" + netMapDTO.getMac() + "__" + UtilDatas.getDataAtual() + "__" + UtilDatas.getHoraAtual();
		nomeArquivo = nomeArquivo.replace(".", "-");
		nomeArquivo = nomeArquivo.replace(":", "-");
		nomeArquivo = nomeArquivo.replace("-", "-");
		return nomeArquivo;
	}
}