package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import microsoft.exchange.webservices.data.EmailMessage;
import microsoft.exchange.webservices.data.Item;
import microsoft.exchange.webservices.data.ItemId;
import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ClienteEmailCentralServicoDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.ClienteEmailCentralServicoService;
import br.com.centralit.citcorpore.negocio.ClienteEmailCentralServicoServiceEjb;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.RenderableMessage;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;

/**
 * @author breno.guimaraes Controla as transações para manipulação de Emails.
 * 
 */
@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
public class ClienteEmailCentralServico extends AjaxFormAction {

	ClienteEmailCentralServicoService clienteEmailService;

	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		carregarEmails(document, request, response);
	}

	public void carregarEmails(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {

		ClienteEmailCentralServicoDTO clienteEmailCentralServicoDTO = (ClienteEmailCentralServicoDTO) document.getBean();

		EmpregadoService empregadoService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
		Collection<EmpregadoDTO> colEmpregadoDTO = new ArrayList();
		StringBuffer html = new StringBuffer();
		RenderableMessage renderable = null;
		ArrayList<Item> mensagens = null;
		String parametroDias = request.getParameter("ultimosXDias");

		int ultimosXDias = 1;
		if (parametroDias != null && !parametroDias.equals("")) {
			ultimosXDias = Integer.parseInt(parametroDias);
		}

		try {
			System.out.println("Buscando emails dos últimos " + ultimosXDias + " dias.");
			/*
			 * mensagens = getSerice().getMails(new ClienteEmailCentralServicoDTO("imap.gmail.com", "incidentes.centralit@gmail.com", "incidentesc3ntr0lit", "imaps", 993), ultimosXDias);
			 */

			mensagens = getService().getMails();

			if (mensagens == null || mensagens.size() <= 0) {
				document.alert(UtilI18N.internacionaliza(request, "MSG04"));
				// return;
			}

			html.append("<table class='table' width='100%'>");
			html.append("<tr>");
			html.append("<th>" + UtilI18N.internacionaliza(request, "eventoItemConfiguracao.data ") + "</th>");
			html.append("<th>" + UtilI18N.internacionaliza(request, "citcorpore.comum.de") + "</th>");
			html.append("<th>" + UtilI18N.internacionaliza(request, "requisitosla.assunto") + "</th>");
			html.append("</tr>");

			/*
			 * Realiza a validação de Contratos Se multicontratos - Lista todos os emails daquele contratos em especifico Senão Lista todos os emails
			 */
			String solicitacao = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
			if (solicitacao == null) {
				solicitacao = "";
			}
			if (solicitacao.trim().equalsIgnoreCase("")) {
				colEmpregadoDTO = empregadoService.listEmailContrato();
			} else {
				colEmpregadoDTO = empregadoService.listEmailContrato(clienteEmailCentralServicoDTO.getIdContrato());
			}

			// HTMLTable tabela = document.getTableById("")
			int i = 0;

			if (mensagens != null) {

				for (Item item : mensagens) {
					i++;
					ItemId itemId = item.getId();
					EmailMessage email = EmailMessage.bind(item.getService(), itemId);
					SolicitacaoServicoDTO solicitacaoServicoDTO = new SolicitacaoServicoDTO();
					// tabela.addRowsByCollection(mensagens,
					// new String[]{"sentDate", "from", "subject"},
					// propertyNamesVerifDuplAddTable, mensagemDuplicacaoCasoOcorra, arFuncoesExec, funcaoClick, funcaoVerificacao)

					for (EmpregadoDTO empregadoDTO : colEmpregadoDTO) {
						if (empregadoDTO.getEmail().trim().equals(email.getSender().toString().trim())) {
							html.append("<tr class='hover' bgcolor='#cccccc' onclick=\"toggleDiv('email" + i + "');\">");
							html.append("<td>");
							html.append(email.getDateTimeReceived());
							html.append("</td>");

							html.append("<td>");
							html.append("<label>");
							html.append("<input type='hidden' value='" + email.getSender().toString() + "' name='idSenderEmail" + i + "' id = 'idSenderEmail" + i + "'/>");
							html.append(email.getSender().toString());
							html.append("</label>");
							html.append("</td>");

							html.append("<td>");
							html.append(email.getSubject());
							html.append("</td>");

							html.append("</tr>");
							html.append("<tr>");
							html.append("<td colspan='6'>");
							html.append("<div id='email" + i + "' style='display:none'>");
							// html.append("content: " + mensagens.get(i).getContent());
							html.append("<label id = 'idGetBody" + i + "' >");
							html.append(email.getBody());
							html.append("</label>");
							html.append("<button type='button' id='copiaEmail" + i + "'  class='light' onclick='copiaEmail(" + i + ")'><img src='"
									+ br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/template_new/images/icons/small/grey/archive.png'>"
									+ UtilI18N.internacionaliza(request, "solicitacaoServico.copiarMsg") + "</button>");
							html.append("</div>");
							html.append("</td>");
							html.append("</tr>");
							html.append("</div>");
							// System.out.println("subject: " + mensagens[i].getSubject());
							break;
						}
					}
				}
			}
			html.append("</table>");
			document.getElementById("emails").setInnerHTML(html.toString());
		} catch (Exception e) {
			System.out.println("" + UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmails") + "");
			e.printStackTrace();
			document.alert("" + UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmails") + "");
			document.getElementById("emails").setInnerHTML("" + UtilI18N.internacionaliza(request, "clienteEmailCentralServico.problemasRealizarleituraEmails") + "");
		}
	}

	private ArrayList<Message> vetorParaArray(Message[] vetor) {
		ArrayList<Message> mensagens = new ArrayList<Message>();
		if (!(vetor instanceof Message[])) {
			return null;
		}
		for (int i = 0; i < vetor.length; i++) {
			mensagens.add(vetor[i]);
		}
		return mensagens;
	}

	private void classificarPorData(ArrayList<Message> mensagens) {
		Collections.sort(mensagens, new Comparator<Message>() {
			public int compare(Message msg1, Message msg2) {
				try {
					return msg1.getSentDate().compareTo(msg2.getSentDate());
				} catch (MessagingException e) {
					e.printStackTrace();
				}
				return 0;
			}

		});

	}

	public Class getBeanClass() {
		return ClienteEmailCentralServicoDTO.class;
	}

	private ClienteEmailCentralServicoService getService() {
		if (clienteEmailService == null) {
			clienteEmailService = new ClienteEmailCentralServicoServiceEjb();
		}
		// return (ClienteEmailCentralServicoService) ServiceLocator.getInstance().getService(ClienteEmailCentralServicoService.class, null);
		return clienteEmailService;
	}
}
