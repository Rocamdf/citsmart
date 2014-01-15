package br.com.centralit.citcorpore.ajaxForms;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.centralit.citajax.html.AjaxFormAction;
import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLElement;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.bean.BaseConhecimentoDTO;
import br.com.centralit.citcorpore.bean.CatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.ContratosGruposDTO;
import br.com.centralit.citcorpore.bean.GrupoEmpregadoDTO;
import br.com.centralit.citcorpore.bean.InfoCatalogoServicoDTO;
import br.com.centralit.citcorpore.bean.MeuCatalogoDTO;
import br.com.centralit.citcorpore.bean.PastaDTO;
import br.com.centralit.citcorpore.bean.PortalDTO;
import br.com.centralit.citcorpore.bean.PostDTO;
import br.com.centralit.citcorpore.bean.ServicoContratoDTO;
import br.com.centralit.citcorpore.bean.ServicoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO;
import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.negocio.BaseConhecimentoService;
import br.com.centralit.citcorpore.negocio.CatalogoServicoService;
import br.com.centralit.citcorpore.negocio.ComentariosService;
import br.com.centralit.citcorpore.negocio.ContratosGruposService;
import br.com.centralit.citcorpore.negocio.EmpregadoService;
import br.com.centralit.citcorpore.negocio.GrupoEmpregadoService;
import br.com.centralit.citcorpore.negocio.InfoCatalogoServicoService;
import br.com.centralit.citcorpore.negocio.MeuCatalogoService;
import br.com.centralit.citcorpore.negocio.PastaService;
import br.com.centralit.citcorpore.negocio.PortalService;
import br.com.centralit.citcorpore.negocio.PostService;
import br.com.centralit.citcorpore.negocio.ServicoContratoService;
import br.com.centralit.citcorpore.negocio.ServicoService;
import br.com.centralit.citcorpore.negocio.SolicitacaoServicoService;
import br.com.centralit.citcorpore.negocio.UnidadeService;
import br.com.centralit.citcorpore.negocio.UnidadesAccServicosService;
import br.com.centralit.citcorpore.negocio.UsuarioService;
import br.com.centralit.citcorpore.util.CitCorporeConstantes;
import br.com.centralit.citcorpore.util.CriptoUtils;
import br.com.centralit.citcorpore.util.Enumerados;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.centralit.citcorpore.util.WebUtil;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.ServiceLocator;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilDatas;
import br.com.citframework.util.UtilStrings;

/**
 * @author Flávio
 *
 */
public class Portal_OLD extends AjaxFormAction {

	@SuppressWarnings({ "static-access", "unchecked", "rawtypes" })
	

	
	public void load(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	
		
		ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL, "");
		
		if (request.getParameter("logout") != null && "yes".equalsIgnoreCase(request.getParameter("logout"))) {
			request.getSession().setAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE", null);
			request.getSession().setAttribute("acessosUsuario", null);
		}
		
		
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		
      		if(request.getParameter("idPost") != null)	{
    			contentPost(document, request, response);
    		} else
    		{
    			posts(document, request, response);
    		}
    		return;
    	}
    	/*Adicionado o conteudo da aba FAQ*/
    	contentFAQ(document, request, response);
    	
    	/*Adicionado o conteudo da catálogo de negócio FAQ*/
    	contentCatalogoServico(document, request, response);
    	
    	/*Classe de Serviço do portal*/
    	UsuarioService userService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
    	EmpregadoService empService = (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
    	UnidadeService undService = (UnidadeService) ServiceLocator.getInstance().getService(UnidadeService.class, null);
    	Integer unidade = empService.consultaUnidadeDoEmpregado(usrDto.getIdEmpregado());
    	
    	Collection<UnidadeDTO> unidades = new ArrayList<UnidadeDTO>();
    	unidades = (Collection<UnidadeDTO>) undService.findById(unidade);
    	StringBuffer uni = null;
    	if(!unidades.isEmpty())
    	{
    		uni = new StringBuffer();
	    	for (UnidadeDTO i : unidades) {
	    		uni.append(i.getNome());
			}
    	}
    	
    	HTMLElement divUser = document.getElementById("usuario");
    	divUser.setInnerHTML("<font style='font-weight: bold'>Usuário:</font> " + usrDto.getNomeUsuario());
    	
    	HTMLElement divUnid = document.getElementById("unidade");
	    if(uni != null)
	    {
	    	divUnid.setInnerHTML("<font  style='font-weight: bold'>Unidade:</font> " + uni);
	    }
	  
	    
		Integer idEmpregado = new Integer(usrDto.getIdEmpregado());
		
		HTMLForm form = document.getForm("formPortal");	
		//form.getDocument().getElementById("column0").setInnerHTML("");
		//form.getDocument().getElementById("column1").setInnerHTML("");
		form.getDocument().getElementById("column2").setInnerHTML("");
		form.getDocument().getElementById("column3").setInnerHTML("");
		form.getDocument().getElementById("column4").setInnerHTML("");
		
		/*Classe de Serviço do portal*/
    	PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);
    	/*Classe de Serviço da Solicitação de serviço*/
    	SolicitacaoServicoService solServicoService = (SolicitacaoServicoService) ServiceLocator.getInstance().getService(SolicitacaoServicoService.class, null);
    	/*Classe de Serviço da Solicitação de serviço*/
    	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
    	/*Classe de Serviço da Base de conhecimento*/
    	BaseConhecimentoService baseServicoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
    	/*Classe de Serviço dos Comentarios*/
    	ComentariosService comentariosService = (ComentariosService) ServiceLocator.getInstance().getService(ComentariosService.class, null);
    	/*Classe d Servico de grupo*/
    	GrupoEmpregadoService grupoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
    	/*Classe d Servico de Contratos*/
    	ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
    	/*Classe d Servico de Contratos*/
    	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
    	//Meu Catalogo de Servicos
    	MeuCatalogoService meuCatalogoService = (MeuCatalogoService) ServiceLocator.getInstance().getService(MeuCatalogoService.class, null);
    			
  
    	
    	Collection<ServicoDTO> listService 		= new ArrayList<ServicoDTO>();
    	Collection<SolicitacaoServicoDTO> listIncidentes 	= solServicoService.listAllIncidentes(idEmpregado);
    	Collection<ContratosGruposDTO> listContratosGrupos 	= new ArrayList<ContratosGruposDTO>();
    	Collection<ServicoContratoDTO> listContratosServico = new ArrayList<ServicoContratoDTO>();
    	Collection<MeuCatalogoDTO> listCatalogo = new ArrayList<MeuCatalogoDTO>();
    	Set listSet = new HashSet<ServicoDTO>();
    	
    	
    	/*Controle unidades - Se houver mudança no parametro alterar metodo listNomeService*/
		String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");
		if (!UtilStrings.isNotVazio(controleAccUnidade)){
			controleAccUnidade = "N";
		}
				
		if (controleAccUnidade.trim().equalsIgnoreCase("S"))
		{
			// Busca Unidade do solicitante
			UnidadesAccServicosService unidadeAccService = (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);
			Integer idUnidade = getEmpregadoService().consultaUnidadeDoEmpregado(idEmpregado);
			Collection<UnidadesAccServicosDTO> col = unidadeAccService.findByIdUnidade(idUnidade);
			if (col!=null) {
				for (UnidadesAccServicosDTO unidadeAccServicoDTO : col) {
					List<ServicoDTO> temp = null;
					temp = (List<ServicoDTO>) servicoService.findByServico(unidadeAccServicoDTO.getIdServico());
					if (temp != null)
						listSet.add(temp.get(0));
					
				}	
			}
		}
		/*Controle Grupos*/
		String controleAccGrupo = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");		
		if (!UtilStrings.isNotVazio(controleAccGrupo.trim())){
			controleAccGrupo = "N";
		}
		if (controleAccGrupo.trim().equalsIgnoreCase("S"))
		{
			// Busca Grupo do solicitante
			Collection<GrupoEmpregadoDTO> grupo = grupoService.findByIdEmpregado(idEmpregado);	

			if (grupo!=null) {
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : grupo) {
					List<ContratosGruposDTO> temp = null;
					temp = (List<ContratosGruposDTO>) contratosGruposService.findByIdGrupo(grupoEmpregadoDTO.getIdGrupo());
					if (temp != null)
					{
						for (ContratosGruposDTO contratosGruposDTO : temp) 
						{
							listContratosGrupos.add(contratosGruposDTO);
						}
					}
						
				}	
			}
			Set listSetServico = new HashSet<ServicoContratoDTO>();
			if (listContratosGrupos!=null) {
				for (ContratosGruposDTO contratosGruposDTO : listContratosGrupos) {
					List<ServicoContratoDTO> temp = null;
					temp = (List<ServicoContratoDTO>) servicoContratoService.findByIdContratoDistinct(contratosGruposDTO.getIdContrato());
					if (temp != null)
					{
						for (ServicoContratoDTO servicoContratoDTO : temp) 
						{
							listSetServico.add(servicoContratoDTO);	
						}
					}
				}	
			}
			listContratosServico = listSetServico;
			if(!listContratosServico.isEmpty())
			{
				for (ServicoContratoDTO servicoContratoDTO : listContratosServico) {
					List<ServicoDTO> temp = null;
					temp = (List<ServicoDTO>) servicoService.findByServico(servicoContratoDTO.getIdServico());
					if (temp != null)
						listSet.add(temp.get(0));
				}
			}
		}
		//List Service Recebendo o List Set
		listService = listSet;
		
		if(controleAccUnidade.trim().equalsIgnoreCase("N") && controleAccGrupo.trim().equalsIgnoreCase("N") )
		{
			listService = servicoService.list();
		}
		//Limpando o formulário
		//form.clear();
		Collection<BaseConhecimentoDTO> listBase = baseServicoService.list();
		Collection<BaseConhecimentoDTO> listBaseFaq = baseServicoService.listarBaseConhecimentoFAQ();
		int x = 0;
    	   
	/*	String p1 = "<li class='panel ' id='portlet-01'>" +
				"	<div id='portlet-title-01' class='panel_titulo' ><img  src='/citsmart/template_new/images/icons/small/grey/box_incoming.png'><br>Solicitações / Incidentes em Aberto </div>" +
				"	<div id='portlet-content-01' class='portlet-content'>Informa as Solicitações/Incidentes em Aberto</div>" +
				"</li>";
		String p2 = "<li class='panel' id='portlet-02'>" +
				"	<div id='portlet-title-02' class='panel_titulo'><img  src='/citsmart/template_new/images/icons/small/grey/book_large.png'><br>Catálogo de Serviços</div>" +
				"	<div id='portlet-content-02' class='portlet-content'>Informa os Serviços</div>" +
				"</li>";
		String p3 = "<li class='panel ' id='portlet-03'>" +
				"	<div id='portlet-title-03' class='panel_titulo'><img  src='/citsmart/template_new/images/icons/small/grey/documents.png'><br>Documentos</div>" +
				"	<div id='portlet-content-03' class='portlet-content'>Base de Conhecimento</div>" +
				"</li>";
		String p4 = "<li class='panel ' id='portlet-04'>" +
				"	<div id='portlet-title-04' class='panel_titulo'><img  src='/citsmart/template_new/images/icons/small/grey/box_outgoing.png'><br>Solicitações / Incidentes Fechados </div>" +
				"	<div id='portlet-content-04' class='portlet-content'>Informa as Solicitações/Incidentes Fechados</div>" +
				"</li>";
		String p5 = "<li class='panel ' id='portlet-05'>" +
				"	<div id='portlet-title-05' class='panel_titulo'><img  src='/citsmart/template_new/images/icons/small/grey/book.png'><br> Meu Catálogo </div>" +
				"	<div id='portlet-content-05' class='portlet-content'>Meus Serviços</div>" +
				"</li>";
		String p6 = "<li class='panel ' id='portlet-06'>" +
				"	<div id='portlet-title-06' class='panel_titulo'><img  src='/citsmart/template_new/images/icons/small/grey/book.png'><br> FAQ </div>" +
				"	<div id='portlet-content-06' class='portlet-content'>FAQ</div>" +
				"</li>";*/
	
		//Adiciona os portlets na lateral
		UsuarioDTO usuario = userService.restoreByIdEmpregado(usrDto.getIdEmpregado());
		PortalDTO pDto = new PortalDTO();
		
		/**
		 * @author Pedro
		 * Verifica se existe algum usuario configurado no portal, ou seja, todo usuário que acessa a primeira vez, seu id é salvo para que as posições dos portlets sejam salvos também.
		 * 
		 * Obs.: Ao subir o portal pela primeira vez, a tabela 'portal' deve ser limpa.
		 */
		pDto.setIdUsuario(usuario.getIdEmpregado());
		Collection<PortalDTO> colUsuarioExistente = portalService.listByUsuario(usuario.getIdEmpregado()); 
		if(colUsuarioExistente == null || colUsuarioExistente.size() == 0) {
			
			Collection<PortalDTO> col = null;
			usuario.setUltimoAcessoPortal(UtilDatas.getDataHoraAtual());
			userService.updateNotNull(usuario);
			
			col = portalService.findByCondition(usrDto.getIdEmpregado(), new Integer(1));
			
			if (col == null || col.isEmpty()) {
				//Caso Não exista cria o portal padrão de Solicitações em abertp
				PortalDTO portal02 = new PortalDTO();
				portal02.setIdItem(new Integer(1));
				portal02.setIdUsuario(usrDto.getIdEmpregado());
				portal02.setLargura(new Double(900));
				portal02.setAltura(new Double(225));
				portal02.setData(UtilDatas.getDataAtual());
				portal02.setColuna(new Integer(3));		
				portalService.create(portal02);
			}
			
			col = portalService.findByCondition(usrDto.getIdEmpregado(), new Integer(2));
			if (col == null || col.isEmpty()) {
				//Caso Não exista cria o portal padrão de Catalogo de servico
				PortalDTO portal01 = new PortalDTO();
				portal01.setIdItem(new Integer(2));
				portal01.setIdUsuario(usrDto.getIdEmpregado());
				portal01.setLargura(new Double(900));
				portal01.setAltura(new Double(225));
				portal01.setData(UtilDatas.getDataAtual());
				portal01.setColuna(new Integer(2));
				portalService.create(portal01);
			}
			
			col = portalService.findByCondition(usrDto.getIdEmpregado(), new Integer(3));
			
			if (col == null || col.isEmpty()) {
				//Caso Não exista cria o portal padrão de Base conhecimento
				PortalDTO portal03 = new PortalDTO();
				portal03.setIdItem(new Integer(3));
				portal03.setIdUsuario(usrDto.getIdEmpregado());
				portal03.setLargura(new Double(900));
				portal03.setAltura(new Double(225));
				portal03.setData(UtilDatas.getDataAtual());
				portal03.setColuna(new Integer(4));			
				portalService.create(portal03);
			}
			
			col = portalService.findByCondition(usrDto.getIdEmpregado(), new Integer(4));
			
			if (col == null || col.isEmpty()) {
				//Caso Não exista cria o portal padrão de Base conhecimento
				PortalDTO portal03 = new PortalDTO();
				portal03.setIdItem(new Integer(4));
				portal03.setIdUsuario(usrDto.getIdEmpregado());
				portal03.setLargura(new Double(900));
				portal03.setAltura(new Double(225));
				portal03.setData(UtilDatas.getDataAtual());
				portal03.setColuna(new Integer(2));			
				portalService.create(portal03);
			}
			
			col = portalService.findByCondition(usrDto.getIdEmpregado(), new Integer(5));
			
			if (col == null || col.isEmpty()) {
				//Caso Não exista cria o portal meu catalogo
				PortalDTO portal03 = new PortalDTO();
				portal03.setIdItem(new Integer(5));
				portal03.setIdUsuario(usrDto.getIdEmpregado());
				portal03.setLargura(new Double(900));
				portal03.setAltura(new Double(225));
				portal03.setData(UtilDatas.getDataAtual());
				portal03.setColuna(new Integer(2));			
				portalService.create(portal03);
			}
			
			col = portalService.findByCondition(usrDto.getIdEmpregado(), new Integer(6));
			if (col == null || col.isEmpty()) {
				//Caso Não exista cria o portal padrão de FAQ
				PortalDTO portal06 = new PortalDTO();
				portal06.setIdItem(new Integer(6));
				portal06.setIdUsuario(usrDto.getIdEmpregado());
				portal06.setLargura(new Double(900));
				portal06.setAltura(new Double(225));
				portal06.setData(UtilDatas.getDataAtual());
				portal06.setColuna(new Integer(5));			
				portalService.create(portal06);
			}
			
			col = portalService.findByCondition(usrDto.getIdEmpregado(), new Integer(7));
			if (col == null || col.isEmpty()) {
				//Caso Não exista cria o portal padrão de Catalogo negocio
				PortalDTO portal06 = new PortalDTO();
				portal06.setIdItem(new Integer(7));
				portal06.setIdUsuario(usrDto.getIdEmpregado());
				portal06.setLargura(new Double(900));
				portal06.setAltura(new Double(225));
				portal06.setData(UtilDatas.getDataAtual());
				portal06.setColuna(new Integer(5));			
				portalService.create(portal06);
			}

		}
		
		Collection<PortalDTO> portais = (Collection<PortalDTO>) portalService.findByCondition(idEmpregado);
		
		if(portais == null || portais.isEmpty()) {	
			/*Configuração Padrão de Portlets*/			
		//	form.getDocument().getElementById("column0").setInnerHTML("");
						
			form.getDocument().getElementById("column2").setInnerHTML("");
			
			form.getDocument().getElementById("column3").setInnerHTML("");
			
			form.getDocument().getElementById("column4").setInnerHTML("");
			
			form.getDocument().getElementById("column5").setInnerHTML("");
			
					
		} else {
			StringBuffer dados = new StringBuffer();
			dados.append("");
		
			/*if(portais.size() == 5) 
				document.executeScript("hideLeft();");
			else 
				document.executeScript("showLeft();");	*/
			
			/*Variaveis booleanas para verificação dos itens * */
			boolean b1 = false, b2 = false, b3 = false, b4 = false, b5 = false, b6 = false;
			
			for (PortalDTO portalDTO : portais) {
				/*Se variaveis == false então Column 1*/
				/*if(!b1){ 
					if(portalDTO.getIdItem()==01) {	
						b1 = true; 
					}	
				}
				if(!b2)	{ 
					if(portalDTO.getIdItem()==02) {	
						b2 = true; }	
					}		
				if(!b3)	{ 
					if(portalDTO.getIdItem()==03) {	
						b3 = true; 
						}	
					}
				if(!b4)	{ 
					if(portalDTO.getIdItem()==04) {	
						b4 = true; 
						}	
					}
				if(!b5)	{ 
					if(portalDTO.getIdItem()==05) {	
						b5 = true; 
						}	
					}
				if(!b6)	{ 
					if(portalDTO.getIdItem()==06) {	
						b6 = true; 
						}	
					}*/
				/*Portlet padrão twitter é incrementado ao numero de itens a ser mostrado; logo + 1*/
			/*	if(portais.size() == 1){					
					if(portalDTO.getIdItem()==01)
						dados.append(p2 + p3 + p4 + p5);
					else if(portalDTO.getIdItem()==02)
						dados.append(p1 + p3 + p4 + p5);
					else if(portalDTO.getIdItem()==03)
						dados.append(p1 + p2 + p4 + p5);
					else if(portalDTO.getIdItem()==04)
						dados.append(p1 + p2 + p3 + p5);
					else if(portalDTO.getIdItem()==05)
						dados.append(p1 + p2 + p3 + p4);
				}
				else if(portais.size() == 2)
				{
					if(!b1 && !b2 && !b3 && b4 && b5)					
						dados.append(p1 + p2 + p3);					
					else if(!b1 && !b2 && b3 && !b4 && b5)					
						dados.append(p1 + p2 + p4);							
					else if(!b1 && !b2 && b3 && b4 && !b5)					
						dados.append(p1 + p2 + p5);					
					else if(!b1 && b2 && !b3 && !b4 && b5)					
						dados.append(p1 + p3 + p4);					
					else if(!b1 && b2 && !b3 && b4 && !b5)					
						dados.append(p1 + p3 + p5);					
					else if(!b1 && b2 && b3 && !b4 && !b5)					
						dados.append(p1 + p4 + p5);					
					else if(b1 && !b2 && !b3 && !b4 && b5)					
						dados.append(p2 + p3 + p4);					
					else if(b1 && !b2 && !b3 && b4 && !b5)					
						dados.append(p2 + p3 + p5);					
					else if(b1 && !b2 && b3 && !b4 && !b5)					
						dados.append(p2 + p4 + p5);
					else if(b1 && b2 && !b3 && !b4 && !b5)					
						dados.append(p3 + p4 + p5);					
				}
				else if(portais.size() == 3)
				{
					if(!b1 && !b2 && b3 && b4 && b5)					
						dados.append(p1 + p2);					
					else if(!b1 && b2 && !b3 && b4 && b5)					
						dados.append(p1 + p3);							
					else if(!b1 && b2 && b3 && !b4 && b5)					
						dados.append(p1 + p4);					
					else if(!b1 && b2 && b3 && b4 && !b5)					
						dados.append(p1 + p5);					
					else if(b1 && !b2 && !b3 && b4 && b5)					
						dados.append(p2 + p3);					
					else if(b1 && !b2 && b3 && !b4 && b5)					
						dados.append(p2 + p4);					
					else if(b1 && !b2 && b3 && b4 && !b5)					
						dados.append(p2 + p5);					
					else if(b1 && b2 && !b3 && !b4 && b5)					
						dados.append(p3 + p4);					
					else if(b1 && b2 && !b3 && b4 && !b5)					
						dados.append(p3 + p5);					
					else if(b1 && b2 && b3 && !b4 && !b5)					
						dados.append(p4 + p5);
				}
				else if(portais.size() == 4)
				{
					if(b1 && b2 && !b3 && b4 && b5)
						dados.append(p3);					
					else if(!b1 && b2 && b3 && b4 && b5)					
						dados.append(p1);							
					else if(b1 && !b2 && b3 && b4 && b5)					
						dados.append(p2);					
					else if(b1 && b2 && b3 && !b4 && b5)					
						dados.append(p4);					
					else if(b1 && b2 && b3 && b4 && !b5)					
						dados.append(p5);
					
				}*/
			
						/****************************************************** SOLICITAÇÕES / INCIDENTES EM ABERTO **************************************************************/
				String categoria = "";
				if(portalDTO.getIdItem() == 1 || portalDTO.getIdItem() == 4){
					
					if(portalDTO.getIdItem() == 1){
						StringBuffer incAberto = new StringBuffer();
						StringBuffer colIncAberto = new StringBuffer();
					incAberto.append(" <table style='border-bottom: 0px !important; border-right: 0px !important'> " +
									"<tr> " +
										" <td> " +
											" <input type='button' onclick='popupNovaSolicitacao()' id='novaSolicitacao' class='botao ui-widget ui-state-default lFloat' value='Nova Solicitação' /></td> "+ 
									" </tr> " +
									" <tr> " +
										" <td style='text-align: left;width:130px; border-bottom: 0px !important; border-right: 0px !important' colspan='1'>Filtro:</td>" +
										" <td style='text-align: left; border-bottom: 0px !important; border-right: 0px !important' colspan='1'>" +
											" <form onsubmit='javascript:;' action='javascript:;'>" +
												" <input type='text' id='filtroBaseConhecimento' onkeyup='filtroTableJs(this, \"tblIncidentesAbertos\")' name='filtroBaseConhecimento'/></form>" +
										" </td> " +
									" </tr>	" +
									" </table>");	
					incAberto.append("<div id='table'><table class='tabela_portal' cellpadding='0' cellspacing='0' style='width:100%' id='tblIncidentesAbertos'>");
					incAberto.append("<thead><tr>" +
							"<th>Numero</th><th>Data Solicitação</th><th>Data Limite</th><th>Prioridade</th><th>Situação</th><th>Opinião</th><th>Pesquisa Satisfação</th></tr></thead><tbody>");
					
					if (listIncidentes != null){
    					for (SolicitacaoServicoDTO solicitacaoServicoDTO : listIncidentes) {
    						if(solicitacaoServicoDTO.getSituacao().equals("EmAndamento"))
    						{
    							//Criando o hash de validação
    							String idHashValidacao = CriptoUtils.generateHash("CODED" + solicitacaoServicoDTO.getIdSolicitacaoServico(), "MD5");
    							incAberto.append(
								"<tr>" +
								"<td  style='text-align: left;'><a href='javascript:;' class='passar_mouse' onclick='executaModalIncidentes(" + solicitacaoServicoDTO.getIdSolicitacaoServico() + ")' >" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "</a></td>" +
								"<td  style='text-align: left;'>" + UtilDatas.dateToSTR( solicitacaoServicoDTO.getDataHoraSolicitacao() )+ "</td>" +
								"<td  style='text-align: left;'>" + UtilDatas.dateToSTR( solicitacaoServicoDTO.getDataHoraLimite())+ "</td>" +
								"<td  style='text-align: left;'>" + solicitacaoServicoDTO.getPrioridade()+ "</td>" 	+
								"<td  style='text-align: left;'>" + solicitacaoServicoDTO.getSituacao()+ "</td>" 	+
								"<td  style='text-align: left;'><a href='javascript:;' class='passar_mouse' title='Registrar elogio ou queixa' onclick='executaModalOpiniao(" + solicitacaoServicoDTO.getIdSolicitacaoServico() + ")' >Registrar Opinião</a></td>" 	+
								"<td  style='text-align: left;'><a href='javascript:;' class='passar_mouse' title='Pesquisa de Satisfação do usuário' onclick='executaModalPesquisa(" + solicitacaoServicoDTO.getIdSolicitacaoServico() + " , \"" + idHashValidacao + "\")' >Nível de Satifação</a></td>" 	+
								"</tr>" 	
								);
    						}        										
    					}
					}
					
					incAberto.append("</tbody></div></table>");
					colIncAberto.append("<li class='portlet ' id='portlet-01' style='width: "+portalDTO.getLargura()+"px ; height: "+portalDTO.getAltura()+"px; top: 10%; left: 1%;  position: absolute; '>" +
							"	<div id='portlet-title-01' class='portlet-header'></span>01 - Solicitações / Incidentes em Aberto </div>" +
							"	<div id='portlet-content-01' class='portlet-content'>"+incAberto+"</div>" +
							"</li>");
					HTMLElement divSif = document.getElementById("column3");
					divSif.setInnerHTML(colIncAberto.toString());
					}
					/******************************************************************* SOLICITACOES / INCIDENTES FECHADOS *************************************************************************/
					if(portalDTO.getIdItem() == 4){
					StringBuffer colSif = new StringBuffer();
					StringBuffer strSif = new StringBuffer();
					strSif.append("<table  cellpadding='0' cellspacing='0' style='width:100%'>" +
							" <tr>" +
								" <td style='text-align: left;width:130px;' colspan='1'>Filtro:</td>" +
								" <td style='text-align: left;' colspan='1'>" +
								" <form onsubmit='javascript:;' action='javascript:;'>" +
								" <input type='text' id='filtroIncidentesFechados' onkeyup='filtroTableJs(this, \"tblIncidentesFechados\")' name='filtroIncidentesFechados'/></form></td> " +
							" </tr></table>");
					strSif.append("<table  cellpadding='0' cellspacing='0' style='width:100%' id='tblIncidentesFechados'>");
					strSif.append("<thead><tr>" +
							"<th>Numero</th><th>Data Solicitação</th><th>Data Limite</th><th>Prioridade</th><th>Situação</th><th>Opinião</th><th>Pesquisa Satisfação</th></tr></thead><tbody>");
					if (listIncidentes != null){
    					for (SolicitacaoServicoDTO solicitacaoServicoDTO : listIncidentes) {
    						if(solicitacaoServicoDTO.getSituacao().equals("Fechada"))
    						{
    							//Criando o hash de validação
    							String idHashValidacao = CriptoUtils.generateHash("CODED" + solicitacaoServicoDTO.getIdSolicitacaoServico(), "MD5");
	    						strSif.append(
	    								"<tr>" +
	    								"<td  style='text-align: left;'><a href='javascript:;' class='passar_mouse' onclick='executaModalIncidentes(" + solicitacaoServicoDTO.getIdSolicitacaoServico() + ")' >" + solicitacaoServicoDTO.getIdSolicitacaoServico() + "</a></td>" +
	    								"<td  style='text-align: left;'>" + UtilDatas.dateToSTR( solicitacaoServicoDTO.getDataHoraSolicitacao() )+ "</td>" +
	    								"<td  style='text-align: left;'>" + UtilDatas.dateToSTR( solicitacaoServicoDTO.getDataHoraLimite())+ "</td>" +
	    								"<td  style='text-align: left;'>" + solicitacaoServicoDTO.getPrioridade()+ "</td>" 	+
	    								"<td  style='text-align: left;'>" + solicitacaoServicoDTO.getSituacao()+ "</td>" 	+
	    								"<td  style='text-align: left;'><a href='javascript:;' class='passar_mouse' title='Registrar elogio ou queixa' onclick='executaModalOpiniao(" + solicitacaoServicoDTO.getIdSolicitacaoServico() + ")' >Registrar Opinião</a></td>" 	+
	    								"<td  style='text-align: left;'><a href='javascript:;' class='passar_mouse' title='Pesquisa de Satisfação do usuário' onclick='executaModalPesquisa(" + solicitacaoServicoDTO.getIdSolicitacaoServico() + " , \"" + idHashValidacao + "\")' >Nível de Satifação</a></td>" 	+
	    								"</tr>" 	
	    								);
    						}
    					}
					}
					strSif.append("</tbody></table>");
					colSif.append(			
							"<li class='portlet ' id='portlet-04' style='width: "+portalDTO.getLargura()+"px ; height: "+portalDTO.getAltura()+"px; top: 55%; left: 1%;  position: absolute;'>" +
							"	<div id='portlet-title-04' class='portlet-header'></span>04 - Solicitações / Incidentes Fechados </div>" +
							"	<div id='portlet-content-04' class='portlet-content'>"+strSif+"</div>" +
							"</li>");
					//concatena incidentes abertos e incidentes fechados e seta na DIV
					HTMLElement divSif1 = document.getElementById("column3_1");
					divSif1.setInnerHTML(colSif.toString());
					}
					
					/********************************************************************* CATALOGO DE SERVICOS ********************************************************************/
				}else if(portalDTO.getIdItem() == 2){
					
					StringBuffer strCatServ = new StringBuffer();
					StringBuffer colCatServ = new StringBuffer();
					
					strCatServ.append("<table class='form' cellpadding='0' cellspacing='0' style='width:100%'>" +
									" <tr>" +
									"	<td style='text-align: left;width:130px;' colspan='1'>Filtro:</td>" +
									"	<td style='text-align: left;' colspan='1'>" +
									"			<input type='text' id='camponomeServico' onkeyup='filtroTableJs(this, \"tblCatServico\");'  name='camponomeServico' />" +
									"	</td>" +
									"</tr>" +
								"</table>");
					strCatServ.append("<div id ='table' class='' style='border: 0px !important'><table id='tblCatServico' cellpadding='0' cellspacing='0' style='width:100%'>");
					strCatServ.append("<thead><tr><th>Categoria</th><th>Tipo de Serviço</th><th>Serviço</th></tr></thead><tbody>");
					for (ServicoDTO servicoDTO : listService) {					
						if(UtilStrings.nullToVazio(categoria).equals("")){	
							categoria = servicoDTO.getNomeCategoriaServico();
							strCatServ.append("<tr><td  align='midlle'>" + categoria + "</td>" +
									"<td  style='text-align: left;'>" + servicoDTO.getNomeTipoServico() + "</td>" +
									"<td  style='text-align: left;'>");
							strCatServ.append("<div id='servico-" + servicoDTO.getIdServico() + "' class='sort1'> "+ servicoDTO.getNomeServico()+" " +
									"<span class='btclick ui-icon ui-icon-circle-plus'  onclick='addServicoMeuCatalogo(" + servicoDTO.getIdServico() + ")' ></span></div>");
							
						}else if(!UtilStrings.nullToVazio(categoria).equalsIgnoreCase(servicoDTO.getNomeCategoriaServico())){
							categoria = servicoDTO.getNomeCategoriaServico();
							strCatServ.append("</td></tr><tr><td  >" + UtilStrings.nullToVazio(categoria) + "</td>" +
									"<td style='text-align: left;'>" + servicoDTO.getNomeTipoServico() + "</td>" +
											"<td  style='text-align: left;'>");
							strCatServ.append("<div id='" + servicoDTO.getIdServico() + "' class='sort1'>"+ servicoDTO.getNomeServico()+" <span class='btclick ui-icon ui-icon-circle-plus' onclick='addServicoMeuCatalogo(" + servicoDTO.getIdServico() + ")' ></span></div>");	
						}else{
							strCatServ.append("<div id='" + servicoDTO.getIdServico() + "' class='sort1'>"+ servicoDTO.getNomeServico()+" <span class='btclick ui-icon ui-icon-circle-plus'  onclick='addServicoMeuCatalogo(" +  servicoDTO.getIdServico() + ")' ></span></div>");
						}
					}
					strCatServ.append("</tbody></table></div>");
					
					colCatServ.append("<li class='portlet ' id='portlet-02' style='width: 700px ; height: 270px;  top: 18%; left: 1%;  position: absolute;'>" +
							"	<div id='portlet-title-02' class='portlet-header'></span>02 - Catálogo de Serviço</div>" +
						"	<div id='portlet-content-02' class='portlet-content' style='height: 200px !important' >"+strCatServ+"</div>" +
						"</li>");
					HTMLElement divCatServ = document.getElementById("tabCatalogoNegocio");
					divCatServ.setInnerHTML(colCatServ.toString());
																																																											
					/********************************************************************** BASE DE CONHECIMENTO **********************************************************************************/
				}else if(portalDTO.getIdItem() == 3){
					StringBuffer strBase = new StringBuffer();
					StringBuffer colBase = new StringBuffer();
					strBase.append("<table  cellpadding='0' cellspacing='0' style='width:100%'>" +
							" <tr>" +
								" <td style='text-align: left;width:130px;' colspan='1'>Filtro:</td>" +
								" <td style='text-align: left;' colspan='1'>" +
								" <form onsubmit='javascript:;' action='javascript:;'>" +
								" <input type='text' id='filtroBaseConhecimento' onkeyup='filtroTableJs(this, \"tblBaseConhecimento\")' name='filtroBaseConhecimento'/></form></td> " +
							" </tr></table>");
					strBase.append("<div id ='tableBc'><table id='tblBaseConhecimento' cellpadding='0' cellspacing='0' style='width:100%'>");
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
					colBase.append(				
							"<li class='portlet ' id='portlet-03' style='width: "+portalDTO.getLargura()+"px ; height: "+portalDTO.getAltura()+"px; top: 10%; left: 1%;  position: absolute;'>" +
							"	<div id='portlet-title-03' class='portlet-header'></span>03 - Base Conhecimento</div>" +
							"	<div id='portlet-content-03' class='portlet-content'>"+strBase+"</div>" +
							"</li>");
				
					HTMLElement divBase = document.getElementById("column4");
					divBase.setInnerHTML(colBase.toString());
					
				}else if(portalDTO.getIdItem() == 5 || portalDTO.getIdItem() == 7){
					
					/*********************************************************** MEU CATALOGO ***************************************************************************/
					
					if(!listService.isEmpty())
					{
						listCatalogo = new ArrayList<MeuCatalogoDTO>(); ;
						for(ServicoDTO sol : listService)
						{
							List<MeuCatalogoDTO> temp = null;
							temp = (List<MeuCatalogoDTO>) meuCatalogoService.findByIdServicoAndIdUsuario(sol.getIdServico(),idEmpregado);
							if (temp != null)
							{
								listCatalogo.add(temp.get(0));
							}
						}
						if(listCatalogo != null)
						{
							listService.clear();
							for (MeuCatalogoDTO meuCatalogoDTO : listCatalogo) {
								List<ServicoDTO> temp = null;
								temp = (List<ServicoDTO>) servicoService.findByServico(meuCatalogoDTO.getIdServico());
								if (temp != null)
									listService.add(temp.get(0));
							}
						}
					}
					
					
					if(portalDTO.getIdItem() == 5){
					StringBuffer strMeuCatalogo = new StringBuffer(); //monta a table
					StringBuffer col2 = new StringBuffer(); //reuni a table com o style da tabela
					strMeuCatalogo.append("<table  cellpadding='0' cellspacing='0' style='width:100%'>" +
							" <tr>" +
								" <td style='text-align: left;width:130px;' colspan='1'>Filtro:</td>" +
								" <td style='text-align: left;' colspan='1'>" +
								" <form onsubmit='javascript:;' action='javascript:;'>" +
								" <input type='text' id='filtroMeuCatalogo' onkeyup='filtroTableJs(this, \"tblMeuCatalogo\")' name='filtroMeuCatalogo'/></form></td> " +
							" </tr></table>");
					strMeuCatalogo.append("<div><table  cellpadding='0' cellspacing='0' style='width:100%' id='tblMeuCatalogo'>");
					strMeuCatalogo.append("<thead ><tr><th>Serviço</th></tr></thead><tbody>");
					for (ServicoDTO servicoDTO : listService) {					
						if(categoria.equals("")){	
							categoria = servicoDTO.getNomeCategoriaServico().trim();
							strMeuCatalogo.append("<tr>" +
									"<td  style='text-align: left;'>");
							strMeuCatalogo.append("<div id='meu-servico-" + servicoDTO.getIdServico() + "' class='sort2'><a href='javascript:;' title='Abrir Solicitação' class='passar_mouse meuCatalogo' " +
									"id='meuCatalogo-" + servicoDTO.getIdServico() + "' servico='"+ servicoDTO.getNomeServico()+"'>"+ servicoDTO.getNomeServico()+"</a>" +
													"<span title='Remove o Serviço do Catálogo' class='btclick ui-icon ui-icon-circle-minus' onclick='deleteServicoMeuCatalogo(" +  servicoDTO.getIdServico() + ")'></span></div>");
						}else if(!categoria.equalsIgnoreCase(servicoDTO.getNomeCategoriaServico())){
							categoria = servicoDTO.getNomeCategoriaServico().trim();
							strMeuCatalogo.append("</td></tr><tr>" +
											"<td  style='text-align: left;'>");
							strMeuCatalogo.append("<div id='meu-servico-" + servicoDTO.getIdServico() + "' class='sort2'><a href='javascript:;' title='Abrir Solicitação' class='passar_mouse meuCatalogo' " +
									"id='meuCatalogo-" + servicoDTO.getIdServico() + "' servico='"+ servicoDTO.getNomeServico()+"'>"+ servicoDTO.getNomeServico()+"</a>" +
													"<span title='Remove o Serviço do Catálogo' class='btclick ui-icon ui-icon-circle-minus' onclick='deleteServicoMeuCatalogo(" +  servicoDTO.getIdServico() + ")'></span></div>");	
						}else{
							strMeuCatalogo.append("<div id='meu-servico-" + servicoDTO.getIdServico() + "' class='sort2'><a href='javascript:;' title='Abrir Solicitação' class='passar_mouse meuCatalogo' " +
									"id='meuCatalogo-" + servicoDTO.getIdServico() + "' servico='"+ servicoDTO.getNomeServico()+"'>"+ servicoDTO.getNomeServico()+"</a>" +
													"<span title='Remove o Serviço do Catálogo' class='btclick ui-icon ui-icon-circle-minus' onclick='deleteServicoMeuCatalogo(" +  servicoDTO.getIdServico() + ")'></span></div>");
						}

					}
					strMeuCatalogo.append("</tbody>" +
							"</table>" );
					
					
					col2.append("<li class='portlet ' id='portlet-05' style='width: "+portalDTO.getLargura()+"px ; height: "+portalDTO.getAltura()+"px; top: 55%; left: 1%;  position: absolute;'>" +
							"	<div id='portlet-title-05' class='portlet-header'></span>05 - Meu Catálogo </div>" +
							"	<div id='portlet-content-05' class='portlet-content'>" +strMeuCatalogo+ "</div>" +
							"</li>");
					HTMLElement divMeuCatalogo = document.getElementById("column2");
					//concatena Catalogo de negocios e Meu Catalogo e seta na DIV
					divMeuCatalogo.setInnerHTML(col2.toString());
					}
					/******************************************************************** CATALOGO DE NEGOCIOS ****************************************************************************/
					if(portalDTO.getIdItem() == 7){
					StringBuffer strCatNegocio = new StringBuffer(); //monta a table
					StringBuffer colCatNegocio = new StringBuffer(); //reuni a table com o style da tabela
					
					CatalogoServicoService catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, null);
					InfoCatalogoServicoService infoCatalogoServicoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, null);
					Collection<CatalogoServicoDTO> listCatalogos = new ArrayList<CatalogoServicoDTO>();
					listCatalogos = catalogoServicoService.listAllCatalogos();
					
					strCatNegocio.append("<table  cellpadding='0' cellspacing='0' style='width:100%'>" +
							" <tr>" +
								" <td style='text-align: left;width:130px;' colspan='1'>Filtro:</td>" +
								" <td style='text-align: left;' colspan='1'>" +
								" <form onsubmit='javascript:;' action='javascript:;'>" +
								" <input type='text' id='filtroCatNegocio' onkeyup='filtroTableJs(this, \"tblCatNegocio\")' name='filtroCatNegocio'/></form></td> " +
							" </tr></table>");
					strCatNegocio.append("<div><table  cellpadding='0' cellspacing='0' style='width:100%' id='tblCatNegocio'>");
					strCatNegocio.append("<thead ><tr><th>Titulo / Informações</th></tr></thead><tbody>");
					for (CatalogoServicoDTO catalogoServicoDTO : listCatalogos) {
						strCatNegocio.append("<tr><td>");
						strCatNegocio.append("<span style='font-weight: bold'>");
						strCatNegocio.append(" " + catalogoServicoDTO.getTituloCatalogoServico() + " ");
						strCatNegocio.append("</span>");
						strCatNegocio.append("</td></tr>");
						Collection<InfoCatalogoServicoDTO> listInfoCatalogoServico = infoCatalogoServicoService.findByCatalogoServico(catalogoServicoDTO.getIdCatalogoServico());
						if (listInfoCatalogoServico != null && !listInfoCatalogoServico.isEmpty()) {
							
							for (InfoCatalogoServicoDTO info : listInfoCatalogoServico) {
								strCatNegocio.append("<tr>");
								strCatNegocio.append("<td>");
								strCatNegocio.append("<span >");
								strCatNegocio.append("<a  href='#' servico='"+catalogoServicoDTO.getTituloCatalogoServico()+"' class='catalogo'  onclick='eventCatalogo("+info.getIdInfoCatalogoServico()+")' id='catalogo-" + info.getIdInfoCatalogoServico() + "' >");
								strCatNegocio.append("  - " + info.getNomeInfoCatalogoServico() + " ");
								strCatNegocio.append("</a>");
								strCatNegocio.append("</span>");
								strCatNegocio.append("</td>");
								strCatNegocio.append("</tr>");
							}
							
						}
					}
			
					strCatNegocio.append("</tbody>" +
							"</table>" );
					
					
					colCatNegocio.append("<li class='portlet ' id='portlet-07' style='width: "+portalDTO.getLargura()+"px ; height: "+portalDTO.getAltura()+"px; top: 10%; left: 1%;  position: absolute;'>" +
							"	<div id='portlet-title-07' class='portlet-header'></span>07 - Catálogo de Negócios </div>" +
							"	<div id='portlet-content-07' class='portlet-content'>" +strCatNegocio+ "</div>" +
							"</li>");
					HTMLElement divMeuCatalogo = document.getElementById("column2_1");
					//concatena Catalogo de negocios e Meu Catalogo e seta na DIV
					divMeuCatalogo.setInnerHTML(colCatNegocio.toString());
					}
					
					/********************************************************************** FAQ ****************************************************************************/
				}else if(portalDTO.getIdItem() == 6){
					StringBuffer strFaq = new StringBuffer();
					StringBuffer colFaq = new StringBuffer();
					strFaq.append("<table  cellpadding='0' cellspacing='0' style='width:100%'>" +
									" <tr>" +
										" <td style='text-align: left;width:130px;' colspan='1'>Filtro:</td>" +
										" <td style='text-align: left;' colspan='1'>" +
										" <input type='text' id='filtroFaq' onkeyup='filtroTableJs(this, \"tblFaq\")' name='filtroFaq'/></td> " +
									" </tr></table>");
							strFaq.append("<div ><table id='tblFaq' cellpadding='0' cellspacing='0' style='width:100%'>");
							strFaq.append("<thead><tr>" +
									"<th>Titulo</th></tr></thead><tbody>");
							if (listBaseFaq != null){
		        					for (BaseConhecimentoDTO baseDTO : listBaseFaq) {
		        						if(baseDTO.getDataFim() == null){
		            						strFaq.append(        								 
		            								"<tr>" +
		            								"<td  style='text-align: left;'>" +
		            								"	<a href='javascript:;' class='passar_mouse' onclick='executaModalFaq(" + baseDTO.getIdBaseConhecimento() + ")' >" + baseDTO.getTitulo() + "</a></td>" +
		            								
		            								"</tr>" 	
		            								);	
		        						}					
		        					}
							}
							strFaq.append("</tbody>" +
									"</table>");
							colFaq.append("<li class='portlet ' id='portlet-06' style='width: "+portalDTO.getLargura()+"px ; height: "+portalDTO.getAltura()+"px; top: 10%; left: 1%;  position: absolute;'>" +
							"	<div id='portlet-title-06' class='portlet-header'></span>06 - FAQ </div>" +
							"	<div id='portlet-content-06' class='portlet-content' style='height: 160px'>"+strFaq+"</div>" +
							"</li>");
							
							HTMLElement divFaq = document.getElementById("column5");
							divFaq.setInnerHTML(colFaq.toString());																								
				}
			}
			//	form.getDocument().getElementById("column0").setInnerHTML(dados + "");
				document.executeScript("redimensionamento();");	
				form.getDocument().getForm("formPortal").clear();
			}
		document.executeScript("event();eventMeuCatalogo();");	
		document.executeScript("JANELA_AGUARDE_MENU.hide()");
		
		}
		
    

    public void save(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}    	
		Integer idUsuario = new Integer(usrDto.getIdEmpregado());
		
		PortalDTO portal = (PortalDTO) document.getBean();
    	PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);
    	
		portal.setIdUsuario(idUsuario);
		portal.setData(UtilDatas.getDataAtual());
		/*portal.setHora(UtilDatas.getDataHoraAtual());*/
		Collection<PortalDTO> col = portalService.findByCondition(idUsuario, portal.getIdItem());
		if (col == null || col.isEmpty()) 
		{
			if(portal.getIdItem() != null){
				
				if(portal.getIdItem()==2 || portal.getIdItem()==5)
					portal.setColuna(2);
				else if(portal.getIdItem()==1 || portal.getIdItem()==4)
					portal.setColuna(3);
				else if(portal.getIdItem()==3)
					portal.setColuna(4);
				
				portalService.create(portal);				
				load(document, request, response);		
			}
		} else 
		{
			portalService.update(portal);
			load(document, request, response);		
		}
		
    }
    public void saveService(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}   
    	//Meu Catalogo de Servicos
    	MeuCatalogoService meuCatalogoService = (MeuCatalogoService) ServiceLocator.getInstance().getService(MeuCatalogoService.class, null); 	
		Integer idUsuario = new Integer(usrDto.getIdEmpregado());
		PortalDTO portal = (PortalDTO) document.getBean();
		MeuCatalogoDTO catalogo = new MeuCatalogoDTO();
		catalogo.setIdServico(portal.getIdServico());   	
    	catalogo.setIdUsuario(idUsuario);
    	List<MeuCatalogoDTO> list = (List<MeuCatalogoDTO>) meuCatalogoService.findByIdServicoAndIdUsuario(portal.getIdServico(), idUsuario);
    	if(list == null || list.isEmpty())
    	{
    		meuCatalogoService.create(catalogo);
    	}
    	load(document, request, response);
    	document.executeScript("$('#camponomeServico').val('"+portal.getNomeServico()+"');");    	
    	if(!portal.getNomeServico().equalsIgnoreCase("")){
    		listNomeServico(document, request, response);
    	}
    	document.executeScript("JANELA_AGUARDE_MENU.hide();");
    }
    
    public void deleteService(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception 
    {
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}   
    	//Meu Catalogo de Servicos
    	MeuCatalogoService meuCatalogoService = (MeuCatalogoService) ServiceLocator.getInstance().getService(MeuCatalogoService.class, null); 	
		Integer idUsuario = new Integer(usrDto.getIdEmpregado());
		PortalDTO portal = (PortalDTO) document.getBean();
		MeuCatalogoDTO catalogo = new MeuCatalogoDTO();
		catalogo.setIdServico(portal.getIdServico());   	
    	catalogo.setIdUsuario(idUsuario);
    	meuCatalogoService.delete(catalogo);
    	load(document, request, response);
    	document.executeScript("JANELA_AGUARDE_MENU.hide();");
    }

    public void restore(DocumentHTML document, HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

		
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void listNomeServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    		
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	if(usrDto == null){
    		return;
    	}
    	//document.executeScript("document.getElementById('loading').style.display = 'block';");
		Integer idEmpregado = new Integer(usrDto.getIdEmpregado());
		
		HTMLForm form = document.getForm("formPortal");		
		//Recebendo o dados do Portal
		PortalDTO portal = (PortalDTO) document.getBean();
    	/*Classe de Serviço */
    	ServicoService servicoService = (ServicoService) ServiceLocator.getInstance().getService(ServicoService.class, null);
    	/*Classe d Servico de grupo*/
    	GrupoEmpregadoService grupoService = (GrupoEmpregadoService) ServiceLocator.getInstance().getService(GrupoEmpregadoService.class, null);
    	/*Classe d Servico de Contratos*/
    	ContratosGruposService contratosGruposService = (ContratosGruposService) ServiceLocator.getInstance().getService(ContratosGruposService.class, null);
    	/*Classe d Servico de Contratos*/
    	ServicoContratoService servicoContratoService = (ServicoContratoService) ServiceLocator.getInstance().getService(ServicoContratoService.class, null);
    	
    	Collection<ServicoDTO> listService 		= new ArrayList<ServicoDTO>();
    	Collection<ContratosGruposDTO> listContratosGrupos 	= new ArrayList<ContratosGruposDTO>();
    	Collection<ServicoContratoDTO> listContratosServico = new ArrayList<ServicoContratoDTO>();
    	Set listSet = new HashSet<ServicoDTO>();
    	
    	/*Controle unidades - Se houver mudança no parametro alterar metodo listNomeService*/
		String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");
		if (!UtilStrings.isNotVazio(controleAccUnidade)){
			controleAccUnidade = "N";
		}
				
		if (controleAccUnidade.trim().equalsIgnoreCase("S"))
		{
			// Busca Unidade do solicitante
			UnidadesAccServicosService unidadeAccService = (UnidadesAccServicosService) ServiceLocator.getInstance().getService(UnidadesAccServicosService.class, null);
			Integer idUnidade = getEmpregadoService().consultaUnidadeDoEmpregado(idEmpregado);
			Collection<UnidadesAccServicosDTO> col = unidadeAccService.findByIdUnidade(idUnidade);
			if (col!=null) {
				for (UnidadesAccServicosDTO unidadeAccServicoDTO : col) {
					List<ServicoDTO> temp = null;
					/*Realizando o filtro pelo nome do servico*/
					if(portal.getNomeServico() != null)
					{ 
						if(!portal.getNomeServico().isEmpty())
							temp = (List<ServicoDTO>) servicoService.findByServico(unidadeAccServicoDTO.getIdServico(), portal.getNomeServico());
						else
							temp = (List<ServicoDTO>) servicoService.findByServico(unidadeAccServicoDTO.getIdServico());
					}			
					if (temp != null)
						listSet.add(temp.get(0));
					
				}	
			}
		}
		/*Controle Grupos*/
		String controleAccGrupo = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.COLABORADORES_VINC_CONTRATOS, "N");		
		if (!UtilStrings.isNotVazio(controleAccGrupo.trim())){
			controleAccGrupo = "N";
		}
		if (controleAccGrupo.trim().equalsIgnoreCase("S"))
		{
			// Busca Grupo do solicitante
			Collection<GrupoEmpregadoDTO> grupo = grupoService.findByIdEmpregado(idEmpregado);	

			if (grupo!=null) {
				for (GrupoEmpregadoDTO grupoEmpregadoDTO : grupo) {
					List<ContratosGruposDTO> temp = null;
					temp = (List<ContratosGruposDTO>) contratosGruposService.findByIdGrupo(grupoEmpregadoDTO.getIdGrupo());
					if (temp != null)
					{
						for (ContratosGruposDTO contratosGruposDTO : temp) 
						{
							listContratosGrupos.add(contratosGruposDTO);
						}
					}
						
				}	
			}
			Set listSetServico = new HashSet<ServicoContratoDTO>();
			if (listContratosGrupos!=null) {
				for (ContratosGruposDTO contratosGruposDTO : listContratosGrupos) {
					List<ServicoContratoDTO> temp = null;
					temp = (List<ServicoContratoDTO>) servicoContratoService.findByIdContratoDistinct(contratosGruposDTO.getIdContrato());
					if (temp != null)
					{
						for (ServicoContratoDTO servicoContratoDTO : temp) 
						{
							listSetServico.add(servicoContratoDTO);	
						}
					}
				}	
			}
			listContratosServico = listSetServico;
			if(!listContratosServico.isEmpty())
			{
				for (ServicoContratoDTO servicoContratoDTO : listContratosServico) {
					List<ServicoDTO> temp = null;
					/*Realizando o filtro pelo nome do servico*/
					if(portal.getNomeServico() != null)
					{ 
						if(!portal.getNomeServico().isEmpty())
							temp = (List<ServicoDTO>) servicoService.findByServico(servicoContratoDTO.getIdServico(), portal.getNomeServico());
						else
							temp = (List<ServicoDTO>) servicoService.findByServico(servicoContratoDTO.getIdServico());
					}				
					
					if (temp != null)
						listSet.add(temp.get(0));
				}
			}
		}
		//List Service Recebendo o List Set
		listService = listSet;

		String categoria = "";
		StringBuffer html = new StringBuffer();
		html.append("<div id='table'><table id='' cellpadding='0' cellspacing='0' style='width:100%'>");
		html.append("<thead ><tr><th>Categoria</th><th>Tipo de Serviço</th><th>Serviço</th></tr></thead><tbody>");
		if(listService != null)
		{
			for (ServicoDTO servicoDTO : listService) {					
				if(categoria.equals("")){	
					categoria = servicoDTO.getNomeCategoriaServico();
					html.append("<tr><td>" + categoria + "</td>" +
							"<td  style='text-align: left;'>" + servicoDTO.getNomeTipoServico() + "</td>" +
							"<td  style='text-align: left;'>");
					html.append("<div id='servico-" + servicoDTO.getIdServico() + "' class='sort1'>"+ servicoDTO.getNomeServico()+"<span title='Adiciona o Serviço ao Meu Catálogo' class='btclick ui-icon ui-icon-circle-plus'></span></div>");
				}else if(!categoria.equalsIgnoreCase(servicoDTO.getNomeCategoriaServico())){
					categoria = servicoDTO.getNomeCategoriaServico();
					html.append("</td></tr><tr><td>" + categoria + "</td>" +
							"<td style='text-align: left;'>" + servicoDTO.getNomeTipoServico() + "</td>" +
									"<td  style='text-align: left;'>");
					html.append("<div id='servico-" + servicoDTO.getIdServico() + "' class='sort1'>"+ servicoDTO.getNomeServico()+"<span title='Adiciona o Serviço ao Meu Catálogo' class='btclick ui-icon ui-icon-circle-plus'></span></div>");	
				}else{
					html.append("<div id='servico-" + servicoDTO.getIdServico() + "' class='sort1'>"+ servicoDTO.getNomeServico()+"<span title='Adiciona o Serviço ao Meu Catálogo' class='btclick ui-icon ui-icon-circle-plus'></span></div>");
				}
			}
		}
		html.append("</tbody></table></div>");
		form.getDocument().getElementById("table").setInnerHTML(html + "");	
		document.executeScript("document.getElementById('loading').style.display = 'none';");
		document.executeScript("addRemove();");	
	
    }
    
    public void delete(DocumentHTML document, HttpServletRequest request,
    	    HttpServletResponse response) throws Exception {
    	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
    	PortalDTO portal = (PortalDTO) document.getBean();
    	if(usrDto == null){
    		//document.executeScript("window.load('"+CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() +"')");
    		return;
    	}
    	PortalService portalService = (PortalService) ServiceLocator.getInstance().getService(PortalService.class, null);
		//substituir o valor 1 para o usuario da sessão!!!!
		Integer idUsuario = new Integer(usrDto.getIdEmpregado());
		portal.setIdUsuario(idUsuario);
		portalService.delete(portal);
		load(document, request, response);

     }
    
    @SuppressWarnings("unchecked")
	public void posts(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
    	PostService postService = (PostService) ServiceLocator.getInstance().getService(PostService.class, null);
    	HTMLElement header = null;
    	List<PostDTO> posts = (List<PostDTO>) postService.listNotNull();
    	if(posts == null)
    	{
    		posts = new ArrayList<PostDTO>();
    	}
    	if(!posts.isEmpty()){
    		document.executeScript("$('.portletInicial').css('display','block');");
    		int i = 1;
    		header = document.getElementById("header-title-" + i);	 
    		header.setInnerHTML("<h2><span class='fn' >Notícias</span></h2>	");
	    	for (PostDTO postDTO : posts) {	     	
	    		 document.executeScript("createPosts("+ i + ")");
	        	 document.executeScript("$('#a-"+i+ "').attr('href', '" + CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath() + "/portal/portal.load?idPost=" + postDTO.getIdPost()+ "');");
	        	 document.executeScript("$('#a-"+i+ "').text('"+postDTO.getTitulo()+"')");
	        	 document.executeScript("$('#img-"+i+ "').attr('src', '" + ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.URL_Sistema, "") + "/tempUpload/" + postDTO.getImagem()+ "');");
	        	 document.executeScript("$('#div-"+i+ "').html('"+postDTO.getDescricao()+"')");
	        	 i ++;
			}
    	} else {
    		document.executeScript("$('.portletInicial').css('display','none');");
    	}
    	HTMLElement page = document.getElementById("page") ;
    	page.setInnerHTML("");
     }
    
    public void contentPost(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
 
    	PostService postService = (PostService) ServiceLocator.getInstance().getService(PostService.class, null);
		String id = request.getParameter("idPost");
		if(id != null && !"".equals(id))
		{
			try {
				PostDTO post = new PostDTO();				
				post.setIdPost(Integer.valueOf(id));
				post = (PostDTO) postService.restore(post);
				if(post != null) 
				{
					StringBuffer html = new StringBuffer();
					HTMLElement content = document.getElementById("page");	        	
					html.append("<h1 class='tit'>"+post.getTitulo()+"</h1>");
					html.append("<div class='cont'>"+post.getConteudo()+"<div>");
					
					content.setInnerHTML(html.toString());
				}
			}catch(Exception e){}
		}								
    }
	
    public void contentFAQ(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
/*  	PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);
		Collection<PastaDTO> listPastaPaiFAQ = pastaService.listPastaSuperiorFAQSemPai();
		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		StringBuffer sb = new StringBuffer();

		sb.append("<ul class=\"ulFaq\">");

		if (listPastaPaiFAQ != null) {

			for (PastaDTO pasta : listPastaPaiFAQ) {

				String nomePasta = pasta.getNome();

				sb.append("<li>");
				sb.append("<span>");
				sb.append(nomePasta);
				sb.append("</span>");
				sb.append("<ul> ");

				Collection<BaseConhecimentoDTO> listBaseconhecimentoFAQ = baseConhecimentoService.listarBaseConhecimentoFAQByPasta(pasta);

				if (listBaseconhecimentoFAQ != null && !listBaseconhecimentoFAQ.isEmpty()) {
					sb.append("<ul>");
					for (BaseConhecimentoDTO base : listBaseconhecimentoFAQ) {
						sb.append("<li>");
						sb.append("<span>");
						sb.append("<a  href='#' class='even'  id='even-" + base.getIdBaseConhecimento() + "' >");
						sb.append(" " + base.getTitulo() + " ");
						sb.append("</a>");
						sb.append("</span>");
						sb.append("<div class='sel' id='sel-" + base.getIdBaseConhecimento() + "'>");
						sb.append(base.getConteudo());
						sb.append("</div>");
						sb.append("</li>");
					}
					sb.append("</ul>");
				}

				Collection<PastaDTO> listSubPastaFAQ = pastaService.listSubPastasFAQ(pasta);
				if (listSubPastaFAQ != null) {
					this.contentFAQFaq(sb, listSubPastaFAQ, pasta, request);
				}
				sb.append("</li>");
				sb.append("</ul>");
				sb.append("</li>");
			}
		}
		sb.append("</ul>");

		HTMLElement divPrincipal = document.getElementById("faq");
		divPrincipal.setInnerHTML(sb.toString());*/
    	
	}

	@SuppressWarnings("unused")
	public void contentFAQFaq(StringBuffer sb, Collection<PastaDTO> listSubPastaFAQ, PastaDTO pasta, HttpServletRequest request) throws ServiceException, Exception {

		BaseConhecimentoService baseConhecimentoService = (BaseConhecimentoService) ServiceLocator.getInstance().getService(BaseConhecimentoService.class, null);
		PastaService pastaService = (PastaService) ServiceLocator.getInstance().getService(PastaService.class, null);

		UsuarioDTO usuarioDto = WebUtil.getUsuario(request);
		for (PastaDTO subPasta : listSubPastaFAQ) {

			BaseConhecimentoDTO baseConhecimento = new BaseConhecimentoDTO();
			Collection<PastaDTO> listSubSubPastaFAQ = pastaService.listSubPastasFAQ(subPasta);

			String nomeSubPasta = subPasta.getNome();
			sb.append("<li>");
			sb.append("<div>");
			sb.append("</div>");

			if (listSubSubPastaFAQ != null && !listSubSubPastaFAQ.isEmpty()) {
				sb.append("<span>");
				sb.append(subPasta.getNome());
				sb.append("</span >");

				Collection<BaseConhecimentoDTO> listBaseconhecimentoFAQDaSubpasta = baseConhecimentoService.listarBaseConhecimentoFAQByPasta(subPasta);

				if (listBaseconhecimentoFAQDaSubpasta != null && !listBaseconhecimentoFAQDaSubpasta.isEmpty()) {
					sb.append("<ul>");
					for (BaseConhecimentoDTO base : listBaseconhecimentoFAQDaSubpasta) {
						sb.append("<li>");
						sb.append("<span>");
						sb.append("<a  href='#' class='even'  id='even-" + base.getIdBaseConhecimento() + "' >");
						sb.append(" " + base.getTitulo() + " ");
						sb.append("</a>");
						sb.append("</span>");
						sb.append("<div class='sel' id='sel-" + base.getIdBaseConhecimento() + "'>");
						sb.append(base.getConteudo());
						sb.append("</div>");
						sb.append("</li>");
					}
					sb.append("</ul>");
				}
			} else {
				sb.append("<span>");
				sb.append(subPasta.getNome());
				sb.append("</span>");
				Collection<BaseConhecimentoDTO> listaBaseconhecimentoFAQ = baseConhecimentoService.listarBaseConhecimentoFAQByPasta(subPasta);
				if (listaBaseconhecimentoFAQ != null && !listaBaseconhecimentoFAQ.isEmpty()) {
					sb.append("<ul>");
					for (BaseConhecimentoDTO base : listaBaseconhecimentoFAQ) {
						sb.append("<li>");
						sb.append("<span class='span'>");
						sb.append("<a  href='#' class='even'  id='even-" + base.getIdBaseConhecimento() + "' >");
						sb.append(" " + base.getTitulo() + " ");
						sb.append("</a>");
						sb.append("</span>");
						sb.append("<div class='sel' id='sel-" + base.getIdBaseConhecimento() + "'>");
						sb.append(base.getConteudo());
						sb.append("</div>");
						sb.append("</li>");
					}
					sb.append("</ul>");
				}
			}
			if (listSubSubPastaFAQ != null && !listSubSubPastaFAQ.isEmpty()) {
				sb.append("<ul> ");
				this.contentFAQFaq(sb, listSubSubPastaFAQ, pasta, request);
				sb.append("</ul> ");
				sb.append("</li>");
			}

		}
	}
	
	@SuppressWarnings("unchecked")
	public void contentCatalogoServico(DocumentHTML document, HttpServletRequest request, HttpServletResponse response) throws Exception {
	/*	StringBuffer sb = new StringBuffer();
		CatalogoServicoService catalogoServicoService = (CatalogoServicoService) ServiceLocator.getInstance().getService(CatalogoServicoService.class, null);
		InfoCatalogoServicoService infoCatalogoServicoService = (InfoCatalogoServicoService) ServiceLocator.getInstance().getService(InfoCatalogoServicoService.class, null);

		Collection<CatalogoServicoDTO> listCatalogos = new ArrayList<CatalogoServicoDTO>();
		listCatalogos = catalogoServicoService.listAllCatalogos();
		sb.append("<ul>");
		for (CatalogoServicoDTO catalogoServicoDTO : listCatalogos) {
			sb.append("<li>");
			sb.append("<span >");
			sb.append(" " + catalogoServicoDTO.getTituloCatalogoServico() + " ");
			sb.append("</span>");
			Collection<InfoCatalogoServicoDTO> listInfoCatalogoServico = infoCatalogoServicoService.findByCatalogoServico(catalogoServicoDTO.getIdCatalogoServico());
			if (listInfoCatalogoServico != null && !listInfoCatalogoServico.isEmpty()) {
				sb.append("<ul>");
				for (InfoCatalogoServicoDTO info : listInfoCatalogoServico) {
					sb.append("<li>");
					sb.append("<span>");
					sb.append("<a  href='#' servico='"+catalogoServicoDTO.getTituloCatalogoServico()+"' class='catalogo'  id='catalogo-" + info.getIdInfoCatalogoServico() + "' >");
					sb.append(" " + info.getNomeInfoCatalogoServico() + " ");
					sb.append("</a>");
					sb.append("</span>");
					sb.append("</li>");
				}
				sb.append("</ul>");
			}
			sb.append("</li>");
		}
		sb.append("</ul>");
		
		HTMLElement divPrincipal = document.getElementById("tabCatalogoNegocio");
		divPrincipal.setInnerHTML(sb.toString());
		document.executeScript("eventCatalogo()");*/

	}
	
    public Class<PortalDTO> getBeanClass()
    {
    	return PortalDTO.class;
    }
    public EmpregadoService getEmpregadoService() throws ServiceException, Exception {
		return (EmpregadoService) ServiceLocator.getInstance().getService(EmpregadoService.class, null);
	}
}
