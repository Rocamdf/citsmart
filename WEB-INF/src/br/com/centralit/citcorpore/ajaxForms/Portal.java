package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ContratoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.CatalogoServicoService;
import br.com.centralit.citcorpore.negocio.ComentariosService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.InfoCatalogoServicoService;
import br.com.centralit.citcorpore.negocio.PortalService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.UtilDatas;

public class Portal extends AjaxFormAction {

	@Override
	public void load(DocumentHTML document, HttpServletRequest request,HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
		
		if(usrDto == null ){
    		document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "/pages/login/login.load'");
			return;
    	}
    	
		if (request.getParameter("logout") != null && "yes".equalsIgnoreCase(request.getParameter("logout"))) {
			request.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE", null);
			request.getSession().setAttribute("acessosUsuario", null);
		}
		
		/*Classe de Serviço da Base de conhecimento*/
    	BaseConhecimentoService baseServicoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
    	/*Classe de Serviço dos Comentarios*/
    	ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
    	/*Classe de Serviço do Portal*/
    	PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);
    	
    	Collection<BaseConhecimentoDTO> listBase = baseServicoService.list();
		Collection<BaseConhecimentoDTO> listBaseFaq = baseServicoService.listarBaseConhecimentoFAQ();
		 
    	contentCatalogoServico(document, request, response);
    	Integer idEmpregado = new Integer(usrDto.getIdEmpregado());
    	Collection<PortalDTO> portais = (Collection<PortalDTO>) portalService.findByCondition(idEmpregado);
    	HTMLForm form = document.getForm("formPortal");
    	
    	
		if(portais == null || portais.isEmpty()) {	
			form.getDocument().getElementById("column4").setInnerHTML("");
			
			
					
		} else {
			StringBuffer dados = new StringBuffer();
			dados.append("");
		
			for (PortalDTO portalDTO : portais) {
				/********************************************************************** BASE DE CONHECIMENTO **********************************************************************************/
			
				StringBuffer strBase = new StringBuffer();
				StringBuffer colBase = new StringBuffer();
				strBase.append("<table>" +
						" <tr>" +
							" <form onsubmit='javascript:;' action='javascript:;'>" +
						" </tr></table>");
				strBase.append("<div id ='tableBc'><table class='table table-bordered table-striped' id='tblBaseConhecimento'>");
				strBase.append("<thead><tr>" +
						"<th>Titulo</th><th>Nota</th></tr></thead><tbody>");
				if (listBase != null){
						for (BaseConhecimentoDTO baseDTO : listBase) {
							if(baseDTO.getDataFim() == null){
								Double nota = comentariosService.calcularNota(baseDTO.getIdBaseConhecimento());
								String nt = null;
								if(nota == null)
									 nt = "S/N";
								else
									nt = nt.valueOf(nota);            						
								strBase.append(        								 
										"<tr>" +
											"<td  style='text-align: left;'>" +
											"	<a href='javascript:;' class='passar_mouse' onclick='executaModal(" + baseDTO.getIdBaseConhecimento() + ")' >" + baseDTO.getTitulo() + "</a></td>" +
											"<td  style='text-align: left;'>" 
											+ nt + 
											"</td>" +
										"</tr>" 	
										);	
							}					
						}
				}
				strBase.append("</tbody></table>");
				colBase.append(	"	<li class='span12'>" +
										"<div id='portlet-content-03' class='portlet-content'>"+strBase+"</div>" +
									"</li>");
			
				HTMLElement divBase = document.getElementById("column4");
				divBase.setInnerHTML(colBase.toString());
		/********************************************************************** FAQ ****************************************************************************/
				StringBuffer strFaq = new StringBuffer();
				if (listBaseFaq != null){
					HTMLElement divFaq = document.getElementById("faqs");//Encontrando o elemento no html
					for (BaseConhecimentoDTO baseDTO : listBaseFaq) {
						if(baseDTO.getDataFim() == null){
							
							strFaq.append("<div class='accordion-heading'>");//Inicio div Cabeçalho
    						strFaq.append("<a class='accordion-toggle glyphicons circle_question_mark' data-toggle='collapse' data-parent='#accordion' href='#collapse-"+baseDTO.getIdBaseConhecimento()+"'>"); 
    						strFaq.append("<i></i>"+ baseDTO.getTitulo());//Titulo   								
    						strFaq.append("</a>");	
    						strFaq.append("</div>");// Fim div cabeçalho
    						
    						strFaq.append("<div id='collapse-"+baseDTO.getIdBaseConhecimento()+"' class='accordion-body collapse'>");//Inicio div Corpo
    						
    						strFaq.append("<div class='accordion-inner' sytle='text-align:center;'>"+baseDTO.getConteudo()+"<br></div>");//Corpo do conteudo
    						
    						strFaq.append("</div>");//Fim div corpo
    						
						}//Fim do if					
					}//Fim do for
					divFaq.setInnerHTML(strFaq.toString());//Adicionando o elemento no html
				}//Fim do if
			}
		}
		
	}
	
	@Override
	public Class getBeanClass() {
		// TODO Auto-generated method stub
		return PortalDTO.class;
	}
	
	public void contentCatalogoServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
		Integer idEmpregado = new Integer(usrDto.getIdEmpregado());
			
			/*Classe d Servico de grupo*/
	    	GrupoEmpregadoService grupoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
	    	/*Classe d Servico de Contratos*/
	    	ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
	    	
			// Busca Grupo do solicitante
			@SuppressWarnings("unchecked")
			Collection<GrupoEmpregadoDTO> grupo = grupoService.findByIdEmpregado(idEmpregado);
	
			Set<ContratoDTO> listContratosDTO = new HashSet<ContratoDTO>();
			
			if (grupo!=null) {
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : grupo) {
					List<ContratosGruposDTO> temp = null;
					temp = (List<ContratosGruposDTO>) contratosGruposService.findByIdGrupo(grupoEmpregadoDTO.getIdGrupo());
					if (temp != null)
					{
					for (ContratosGruposDTO contratosGruposDTO : temp) 
						{
							ContratoDTO contratoDTO = new ContratoDTO();
							contratoDTO.setIdContrato(contratosGruposDTO.getIdContrato());
							listContratosDTO.add(contratoDTO);
						}
					}
						
				}	
			}
			
			StringBuffer sbCatSer = new StringBuffer();
			CatalogoServicoService catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, null);
			Collection<CatalogoServicoDTO> listCatalogos = new ArrayList<CatalogoServicoDTO>();
			
			if (listContratosDTO!=null) {
				for (ContratoDTO contratoDTO : listContratosDTO) {
					List<CatalogoServicoDTO> temp = null;
					temp = (List<CatalogoServicoDTO>) catalogoServicoService.listByIdContrato(contratoDTO.getIdContrato());
					if (temp != null)
					{
						for (CatalogoServicoDTO catalogoServicoDTO : temp) 
						{
							listCatalogos.add(catalogoServicoDTO);
						}
					}
				}
			}
				
		sbCatSer.append("	<ul class='row-fluid'>");
		for (CatalogoServicoDTO catalogoServicoDTO : listCatalogos) {
			//Criando lista de Serviços
			sbCatSer.append("	<li class='span4'>");
			sbCatSer.append("		<a href='javascript:carregarServicos("+catalogoServicoDTO.getIdCatalogoServico()+","+catalogoServicoDTO.getIdContrato()+");' >");
			sbCatSer.append("			<span class='pull-right glyphicons shopping_cart'><i></i></span>");
			sbCatSer.append("			<h5>");
			sbCatSer.append(			StringEscapeUtils.escapeHtml(catalogoServicoDTO.getTituloCatalogoServico()));
			sbCatSer.append("			</h5>");
			sbCatSer.append("			<span class='price'>");
			sbCatSer.append("				");
			sbCatSer.append("			</span>");
			sbCatSer.append("			<span class='clearfix'></span>");
			sbCatSer.append("		</a>");
			sbCatSer.append("	</li>");
		}
		sbCatSer.append("	</ul>");
		
		//atribuindo informações da lista de Serviços
		HTMLElement divPrincipal = document.getElementById("listaServicos");
		divPrincipal.setInnerHTML(sbCatSer.toString());
	
	}
	
	public void contentServicos (DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		InfoCatalogoServicoService infoCatalogoServicoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, null);
		StringBuffer sbDesc = new StringBuffer();
		//Criando tabela Descrição
		sbDesc.append("<h5>"+UtilI18N.internacionaliza(request, "portal.carrinho.listagem")+"</h5>");
		sbDesc.append("<table class='table table-bordered uniformjs' id='tblDescricao'>");
		sbDesc.append("		<button type='button' class='close' data-dismiss='modal' aria-hidden='true'>×</button>");                                                                
		sbDesc.append("		<tr>");
		sbDesc.append("			<th class='span1'>");
		sbDesc.append("				<input class='checkbox' checked='checked' type='checkbox' id='checkboxCheckAll' name='checkboxCheckAll' onclick='marcarTodos(this.checked);' />");
		sbDesc.append("			</th>");
		sbDesc.append("			<th class='span2'>");
		sbDesc.append("				"+UtilI18N.internacionaliza(request, "portal.carrinho.nomeAmigavel.servico")+"");
		sbDesc.append("			</th>");
		sbDesc.append("			<th class='span2'>");
		sbDesc.append("				"+UtilI18N.internacionaliza(request, "portal.carrinho.nomeTecnico.servico")+"");
		sbDesc.append("			</th>");
		sbDesc.append("			<th class='span4'>");
		sbDesc.append("				"+UtilI18N.internacionaliza(request, "portal.carrinho.descricao")+"");
		sbDesc.append("			</th>");
		sbDesc.append("			<th class='span3'>");
		sbDesc.append("				"+UtilI18N.internacionaliza(request, "portal.carrinho.observacao")+"");
		sbDesc.append("			</th>");
		sbDesc.append("		</tr>");
		PortalDTO portalDTO = (PortalDTO) document.getBean();
		Collection<InfoCatalogoServicoDTO> listInfoCatalogoServico = infoCatalogoServicoService.findByCatalogoServico(portalDTO.getIdCatalogoServico());
		if (listInfoCatalogoServico != null && !listInfoCatalogoServico.isEmpty()) {
			int i = 1;
			for (InfoCatalogoServicoDTO info : listInfoCatalogoServico) {
				if (info.getIdServicoCatalogo() != null){
					sbDesc.append("<tr>");
					sbDesc.append("<td>");
					sbDesc.append("		<input type='checkbox' checked='checked' class='perm checkbox' id='idServicoCatalogo"+i+"' name='idServicoCatalogo' value='"+info.getIdServicoCatalogo()+"' />");
					sbDesc.append("		<input type='hidden'id='idContrato"+i+"' name='idContrato' value='"+portalDTO.getIdContratoUsuario()+"' />");
					sbDesc.append("		<input type='hidden'id='descInfoCatalogoServico"+i+"' name='descInfoCatalogoServico' value='" + StringEscapeUtils.escapeJavaScript(info.getDescInfoCatalogoServico()) + "' /></td>");
					sbDesc.append("		<input type='hidden'id='observacaoInfoCatalogoServico"+i+"' name='observacaoInfoCatalogoServico' value='' /></td>");
					sbDesc.append("<td>" + StringEscapeUtils.escapeJavaScript(info.getNomeInfoCatalogoServico()) + "</td>");
					sbDesc.append("<td>" + StringEscapeUtils.escapeJavaScript(info.getNomeServicoContrato()) + "</td>");
					sbDesc.append("<td>" + StringEscapeUtils.escapeJavaScript(info.getDescInfoCatalogoServico()) + "</td>");
					sbDesc.append("<td>");
					sbDesc.append("		<textarea onkeyup='javascript:limita(this.id);'   id='observacaoPortal"+i+"' name='observacaoPortal' class='' style='width: 96%;' rows='2' ></textarea>");
					sbDesc.append("</td>");
					i++;
				}
			}
			/* Desenvolvedor: Thiago Matias - Data: 07/11/2013 - Horário: 14:50 - ID Citsmart: 123357 - 
			* Motivo/Comentário: uma forma de verificar se no catálogo de Negócio o campo Serviço foi preenchido, pois pode haver dados antigos no banco que não tenha essa vinculação do Cat. Negócio com o Serviço
			* */
			if(i == 1) {
				document.alert(UtilI18N.internacionaliza(request, "portal.naohaserviconocatalogo"));
				return;
			}
		} else {
			/* Desenvolvedor: Thiago Matias - Data: 07/11/2013 - Horário: 14:50 - ID Citsmart: 123357 - 
			* Motivo/Comentário: Exibir um alert se no catálogo de Negócio não houver nenhum serviço vinculados
			* */
			document.alert(UtilI18N.internacionaliza(request, "portal.naohaserviconocatalogo"));
			return;
		}
		sbDesc.append("</tr>");
		sbDesc.append("</table>");
		document.executeScript("setarValoresTabela(\"" + sbDesc.toString()+ "\")");
	}
	@SuppressWarnings("unchecked")
	public void adicionaItensCarrinhoDeCompra(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception{
		List<ServicoContratoDTO> listaServicosContrato = new ArrayList<ServicoContratoDTO>();
		ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
		List<InfoCatalogoServicoDTO> infoCatalogoServicoDTOs = (ArrayList<InfoCatalogoServicoDTO>) 
				br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(InfoCatalogoServicoDTO.class, "servicosEscolhidos", request);
		
		if (infoCatalogoServicoDTOs != null) {
			for (InfoCatalogoServicoDTO infoCatalogoServicoDTO : infoCatalogoServicoDTOs) {
				ServicoContratoDTO servicoContratoDto = servicoContratoService.findByIdServicoContrato(infoCatalogoServicoDTO.getIdServicoCatalogo(),infoCatalogoServicoDTO.getIdContrato());
				if(servicoContratoDto != null) {
					servicoContratoDto.setIdServico(infoCatalogoServicoDTO.getIdServicoCatalogo());
					servicoContratoDto.setIdContrato(infoCatalogoServicoDTO.getIdContrato());
					servicoContratoDto.setDescricao(StringEscapeUtils.escapeJavaScript(infoCatalogoServicoDTO.getDescInfoCatalogoServico()));
					servicoContratoDto.setObservacaoPortal(StringEscapeUtils.escapeJavaScript(infoCatalogoServicoDTO.getObservacaoPortal()));
					listaServicosContrato.add(servicoContratoDto);
				}
			}
			if (listaServicosContrato != null && listaServicosContrato.size() > 0) {
				document.executeScript("adicionarColecaoItensItens(\""+br.com.citframework.util.WebUtil.serializeObjects(listaServicosContrato)+"\")");
			}
		}		
	}
	
	@SuppressWarnings("unchecked")
	public void finalizarCarrinho(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
		UsuarioDTO usuario = WebUtil.getUsuario(request);
		if (usuario == null) {
			document.alert(UtilI18N.internacionaliza(request, "citcorpore.comum.sessaoExpirada"));
			document.executeScript("window.location = '" + Constantes.getValue("SERVER_ADDRESS") + request.getContextPath() + "'");
			return;
		}
		PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);
		List<ServicoContratoDTO> servicoDTOs = (ArrayList<ServicoContratoDTO>) br.com.citframework.util.WebUtil.deserializeCollectionFromRequest(ServicoContratoDTO.class, "servicosLancados", request);
		if(servicoDTOs != null) {
			/*Pedido*/
			PedidoPortalDTO pedidoPortalDTO = new PedidoPortalDTO();
			pedidoPortalDTO.setIdUsuario(usuario.getIdEmpregado());
			pedidoPortalDTO.setDataPedido(UtilDatas.getDataAtual());
			pedidoPortalDTO.setListaServicoContrato(servicoDTOs);
			try {
				pedidoPortalDTO = portalService.criarPedidoSolicitacao(pedidoPortalDTO, usuario);
				String mensagem = "";
				mensagem += "<h4>" + UtilI18N.internacionaliza(request, "MSG05") + "</h4>";
				for (SolicitacaoServicoDTO solicitacaoServicoDTO : pedidoPortalDTO.getListaSolicitacoes()) {
						mensagem += "<br/>";
						mensagem += UtilI18N.internacionaliza(request, "gerenciaservico.numerosolicitacao") + " <b><u>" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "</u></b> "
							+ UtilI18N.internacionaliza(request, "citcorpore.comum.crida") + ".<br>" + 
							UtilI18N.internacionaliza(request, "prioridade.prioridade") + ": " 	+ solicitacaoServicoDTO.getIdPrioridade();
					if (solicitacaoServicoDTO.getPrazoHH() > 0 || solicitacaoServicoDTO.getPrazoMM() > 0) {
						mensagem +=" - SLA: " + solicitacaoServicoDTO.getSLAStr() + "<br/>";
					}
				}
				document.executeScript("mostrarMensagemSolicitacoes(\"" + mensagem + "\")");
			} catch(Exception e) {
				e.printStackTrace();
				document.alert(UtilI18N.internacionaliza(request, "portal.pendenciaFinalizaCarrinhoCompras"));
			}
		}
	}
}
