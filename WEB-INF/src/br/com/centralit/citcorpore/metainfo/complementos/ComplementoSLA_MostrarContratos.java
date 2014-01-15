package br.com.centralit.citcorpore.metainfo.complementos;

import java.io.PrintWriter;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citcorpore.bean.ClienteDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.FornecedorDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.negocio.ClienteService;
import br.com.centralit.citcorpore.negocio.ContratoService;
import br.com.centralit.citcorpore.negocio.FornecedorService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilHTML;
import br.com.citframework.util.UtilStrings;

public class ComplementoSLA_MostrarContratos {
	public void execute(HttpServletRequest request, HttpServletResponse response, String tipoContrato){
		HashMap map = getValuesFromRequest(request);
		debugValuesFromRequest(map);		
		PrintWriter out = null;
		try{
			out = response.getWriter();
			
			String IDACORDONIVELSERVICO_STR = request.getParameter("IDACORDONIVELSERVICO");
			int IDACORDONIVELSERVICO = 0;
			if (IDACORDONIVELSERVICO_STR != null){
				try{
					IDACORDONIVELSERVICO = Integer.parseInt(IDACORDONIVELSERVICO_STR);					
				}catch(Exception e){
				}
			}
			
			String strTable = "<table width='100%'>";
			strTable += "<tr>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "&nbsp;";
			strTable += "</td>";			
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.contrato.numero")) + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilI18N.internacionaliza(request, "sla.contrato.data") + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilI18N.internacionaliza(request, "sla.contrato.cliente") + "</b>";
			strTable += "</td>";
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilI18N.internacionaliza(request, "sla.contrato.fornecedor") + "</b>";
			strTable += "</td>";	
			strTable += "<td style='border:1px solid black'>";
				strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.contrato.situacao")) + "</b>";
			strTable += "</td>";				
			strTable += "</tr>";			
			if (IDACORDONIVELSERVICO > 0){
				ContratoService contratoService = (ContratoService) ServiceLocator.getInstance().getService(ContratoService.class, null);
				ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
				ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
				ClienteService clienteService = (ClienteService) ServiceLocator.getInstance().getService(ClienteService.class, null);
				FornecedorService fornecedorService = (FornecedorService) ServiceLocator.getInstance().getService(FornecedorService.class, null);
				List lstContratosCliente = (List) contratoService.listByIdAcordoNivelServicoAndTipo(IDACORDONIVELSERVICO, tipoContrato);
				if (lstContratosCliente != null){
					for (Iterator it = lstContratosCliente.iterator(); it.hasNext();){
						ContratoDTO contratoDTO = (ContratoDTO)it.next();
						String nomeCliente = "";
						String nomeFornecedor = "";
						
						ClienteDTO clientDto = new ClienteDTO();
						clientDto.setIdCliente(contratoDTO.getIdCliente());
						clientDto = (ClienteDTO) clienteService.restore(clientDto);
						if (clientDto != null){
							nomeCliente = clientDto.getNomeRazaoSocial();
						}
						
						FornecedorDTO fornecedorDto = new FornecedorDTO();
						fornecedorDto.setIdFornecedor(contratoDTO.getIdFornecedor());
						fornecedorDto = (FornecedorDTO) fornecedorService.restore(fornecedorDto);
						if (fornecedorDto != null){
							nomeFornecedor = fornecedorDto.getRazaoSocial();
						}
						
						String situacao = contratoDTO.getSituacao();
						if (situacao.equalsIgnoreCase("A")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.ativo");
						}else if (situacao.equalsIgnoreCase("F")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.finalizado");
						}else if (situacao.equalsIgnoreCase("C")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.cancelado");
						}else if (situacao.equalsIgnoreCase("P")){
							situacao = UtilI18N.internacionaliza(request, "sla.contrato.paralisado");
						}
						
						strTable += "<tr>";
							strTable += "<td style='border:1px solid black'>";
								strTable += "<img id='img_tr_" + contratoDTO.getIdContrato() + "_" + tipoContrato + "' src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/mais.jpg' border='0' onclick=\"abreFechaMaisMenos(this, 'tr_" + contratoDTO.getIdContrato() + "_" + tipoContrato + "')\"/>";
							strTable += "</td>";						
							strTable += "<td style='border:1px solid black'>";
								strTable += contratoDTO.getNumero();
							strTable += "</td>";
							strTable += "<td style='border:1px solid black'>";
								strTable += UtilDatas.dateToSTR(contratoDTO.getDataContrato());
							strTable += "</td>";		
							strTable += "<td style='border:1px solid black'>";
								strTable += UtilHTML.encodeHTML(nomeCliente);
							strTable += "</td>";	
							strTable += "<td style='border:1px solid black'>";
								strTable += UtilHTML.encodeHTML(nomeFornecedor);
							strTable += "</td>";	
							strTable += "<td style='border:1px solid black'>";
								strTable += situacao;
							strTable += "</td>";								
						strTable += "</tr>";
						
						Collection colServicos = servicoContratoService.findByIdContrato(contratoDTO.getIdContrato());
						if (colServicos != null && colServicos.size() > 0){
							strTable += "<tr>";
								strTable += "<td colspan='6' style='border:1px solid black'>";
									strTable += "<div id='tr_" + contratoDTO.getIdContrato() + "_" + tipoContrato + "' style='display:none'>";
										strTable += "<table width='100%'>";
										strTable += "<tr>";
										strTable += "<td colspan='2'>";
											strTable += "<b>" + UtilHTML.encodeHTML(UtilI18N.internacionaliza(request, "sla.servicosdocontrato")) + "</b>";
										strTable += "</td>";
										strTable += "</tr>";										
										for (Iterator itServ = colServicos.iterator(); itServ.hasNext();){
											ServicoContratoDTO servContratoDto = (ServicoContratoDTO)itServ.next();
											if ((servContratoDto.getDataFim() == null || UtilDatas.getDataAtual().before(servContratoDto.getDataFim())) 
													&& (servContratoDto.getDeleted() == null || servContratoDto.getDeleted().equalsIgnoreCase("n"))){
												ServicoDTO servicoDto = new ServicoDTO();
												servicoDto.setIdServico(servContratoDto.getIdServico());
												servicoDto = (ServicoDTO) servicoService.restore(servicoDto);
												if (servicoDto != null){
													strTable += "<tr>";
														strTable += "<td>";
															strTable += "<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/imagens/seta_link1.gif' border='0'/>";
														strTable += "</td>";
														strTable += "<td>";
															strTable += UtilHTML.encodeHTML(servicoDto.getNomeServico());
														strTable += "</td>";
													strTable += "</tr>";
												}
											}
										}
										strTable += "</table>";
									strTable += "</div>";
								strTable += "</td>";								
							strTable += "</tr>";
						}
					}
				}
			}
			strTable += "</table>";
			
			out.write(strTable);
		}catch (Exception eX) {
		}        	
    	
		response.setContentType("text/html; charset=UTF-8");
	}
	public HashMap getValuesFromRequest(HttpServletRequest req){
		Enumeration en = req.getParameterNames();
		String[] strValores;
		HashMap formFields = new HashMap();
		while(en.hasMoreElements()) {
			String nomeCampo  = (String)en.nextElement();
			strValores = req.getParameterValues(nomeCampo);
			if (strValores.length == 0){
				formFields.put(nomeCampo.toUpperCase(),UtilStrings.decodeCaracteresEspeciais(req.getParameter(nomeCampo)));
			} else {
				if (strValores.length == 1){
					formFields.put(nomeCampo.toUpperCase(),UtilStrings.decodeCaracteresEspeciais(strValores[0]));
				}else{
					formFields.put(nomeCampo.toUpperCase(),strValores);
				}
			}
		}
		return formFields;
	}
	public void debugValuesFromRequest(HashMap hashValores){
		Set set = hashValores.entrySet(); 
		Iterator i = set.iterator(); 
		
		System.out.print("------- ComplementoSLA_MostrarContratos ------ VALORES DO REQUEST: -------"); 
		while(i.hasNext()) { 
			Map.Entry me = (Map.Entry)i.next(); 
			System.out.print("ComplementoSLA_MostrarContratos -------------> [" + me.getKey() + "]: [" + me.getValue() + "]"); 
		}		
	}	
}
