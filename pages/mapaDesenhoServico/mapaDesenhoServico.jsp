<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>
<%
    //identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
			
			String retorno = br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")
					+ br.com.citframework.util.Constantes
							.getValue("CONTEXTO_APLICACAO")
					+ "/pages/index/index.load";
		
			
%>
<!DOCTYPE html>
<html lang="pt-br">
<head>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en-us" class="no-js">
<!--<![endif]-->
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<meta charset="ISO-8859-1">
<title>Mapa de Serviços</title>
<script type="text/javascript" src="js/MapaDesenhoServico.js"></script>
<script type="text/javascript" src="js/canvas2image.js"></script>
<%-- <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script> --%>
<link rel="stylesheet" type="text/css"	href="css/estiloMapeamentoServico.css">
<script src="js/jquery.growl.js" type="text/javascript"></script>

<script>
	var mapa = null;
	addEvent(window, "load", load, false);	
	function load() {
		//$(".popup").css("overflow", "block");		
		$(".popup").dialog({
			width : "50%",
			height : window.screen.height/2,
			modal : true,
			autoOpen : false,
			resizable : false,
			show : "fade",
			hide : "fade",
			beforeClose : limpaFormItemConfiguracao
		});
		
		mapa = new MapaDesenhoServico(document.getElementById("mapaServicos"));
		mapa.configuraEventos();		
	};

	function extrairVariavelDaUrl(nome){
		var valor = null;
		var identificador = null;
		try{
			var strUrl = document.URL;
			var params = strUrl.split("?")[1];
			var variaveis = params.split("&");
	
			for(var i = 0; i < variaveis.length; i++){
				valor = variaveis[i].split("=")[1]; 			
				identificador = variaveis[i].split("=")[0];
				
				if(identificador == nome){
					return valor; 
				}
				
				valor = null;
			}
		}catch(e){}

		return null;
	}
	
	
	/**
	 * 
	 */
	function atualizaServicoMapa(serealizado) {
		mapa.resetaLista();
		//mapa = null;
		mapa = new MapaDesenhoServico(document.getElementById("mapaServicos"));
		mapa.configuraEventos();

		//Recupera o IC com apenas dois clicks. Atualmente utilizado apenas pelo gerenciamento de problema
		if(extrairVariavelDaUrl("selecaoIc") != null){
			mapa.addAfterDBClickItemEvent(function(item){
				parent.selecionaIcPeloMapa(item.idItemConfiguracao, item.identificacao + "-" + item.descricao);
				throw i18n_message("mapaDesenhoServico.itemRecuperadoPorPaginaPai");
			})
		}
		
		var lista = ObjectUtils.deserializeCollectionFromString(serealizado);
		var imgAux = null;
		var listaAux = [];
		for ( var i = 0; i < lista.length; i++) {
			imgAux = new ImagemItemConfiguracao(mapa.context, lista[i].caminhoImagem);
			imgAux.idImagemItemConfiguracao = lista[i].idImagemItemConfiguracao;
			imgAux.posx = parseInt(lista[i].posx);
			imgAux.posy = parseInt(lista[i].posy);
			imgAux.idServico = lista[i].idServico;
			imgAux.idItemConfiguracao = lista[i].idItemConfiguracao;
			imgAux.idImagemItemConfiguracaoPai = lista[i].idImagemItemConfiguracaoPai != "" ? lista[i].idImagemItemConfiguracaoPai : null;
			imgAux.descricao = lista[i].descricao;
			imgAux.identificacao = lista[i].identificacao;
			// 			alert("x: " + imgAux.posx + "\n - y: " + imgAux.posy + "\n - idSevico: " + imgAux.idServico + 
			// 			  														" \n identi: " + imgAux.identificacao);
			listaAux[i] = imgAux;
		}
		mapa.setListaItens(listaAux.slice());
		document.getElementById("listaItensConfiguracaoSerializada").value = "";
	};

	/**
	 * Quando o item começar a ser arrastado algumas informações
	 * serão salvas para serem recuperadas pelo Mapa.
	 * @param e
	 * Evento dragstart.
	 */
	function dragstart(e) {
		if (document.form.idServico.value != ""){
			e.dataTransfer.setData("text", e.target.getAttribute("id"));			
		}
		/* else{
			$.growl.notice({ message: i18n_message("citcorpore.comum.selecioneUmServico") });
			alert(i18n_message("citcorpore.comum.selecioneUmServico"));
			return;
		} */
	}

	/**
	 * Mostra e esconde uma popup.
	 */
	function togglePopup(id, openClose) {
		$("#" + id).dialog(openClose);
		limpaFormItemConfiguracao();
	};
	
	/**
	 * Mostra e esconde uma popup.
	 */
	function togglePopupFrame(id, link, openClose) {
		if(link > "")
			document.getElementById('popupitemc').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>' + link + '?iframe=true';
		else
			document.getElementById('popupitemc').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>' + link;
				
		$("#" + id).dialog(openClose);
		limpaFormItemConfiguracao();
	};

	/**
	 * Limpa campos específicos.
	 */
	function limpaFormItemConfiguracao() {
		document.form.idItemConfiguracao.value = "";
// 		document.form.identificacao.value = "";
// 		document.form.txtDescricao.value = "";
		document.getElementById("identificacao").value = "";
		document.getElementById("txtDescricao").value = "";
	};	
	/**
	 *
	 */
	function LOOKUP_SERVICO_DESENHO_select(id, desc) {
		document.getElementById("listaItensConfiguracaoSerializada").value = "";
		var camposLookupServico = desc.split("-");
		document.getElementById("nomeServico").innerHTML = desc;
// 		document.getElementById("descricaoServico").innerHTML = camposLookupServico[1];
		document.form.idServico.value = id;
		document.form.fireEvent("selecionarServico");
		togglePopup("POPUP_SERVICO", "close");
	};

	/**
	 *
	 */
	function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
		var camposLookupItem = desc.split("-");
		document.form.idItemConfiguracao.value = id;
		document.getElementById("identificacao").value = camposLookupItem[0];
		document.form.fireEvent("selecionarItemConfigurcao");
		togglePopupFrame("POPUPITEMCONFIGURACAO", "", "close");
	};
	
	/**
	 * Faz a seleção so item de configuração a partir da página de pesquisaItemConfiguracao
	 */
	function selectedItemConfiguracao(idItem) {
		document.form.idItemConfiguracao.value = idItem;
		document.form.fireEvent("selecionarItemConfigurcao");
		togglePopup("POPUPITEMCONFIGURACAO", "close");
	};
	
	function voltar(){
		verificarAbandonoSistema = false;
		window.location = '<%=retorno%>';
	}
	
	 /* function mapaServicos(){
		 if(document.form.idServico.value == null || document.form.idServico.value == ""){
			alert(i18n_message("citcorpore.comum.selecioneUmServico"))
		 }else{
			 window.print();
		 }
	}  */
	
	
	
</script>

</head>
<body>
<div id="carregando" class="carregando" style="display: none;">
	<label>Carregando...</label>
</div>
<div id="divLogo" style="overflow: hidden!important;">
			<table cellpadding='0' cellspacing='0'>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<img border="0" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/logo.png" />
					</td>
				</tr>
			</table>	
		</div>
<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block;">
	<table cellpadding='0' cellspacing='0'>
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td>
				<button  type="button" id='btnVoltar' class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<i18n:message key="citcorpore.comum.voltarprincipal" />">
					<img border="0" title="<i18n:message key='citcorpore.comum.voltarprincipal' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/back.png" /><span style="padding-left: 0px !important;"><i18n:message key="citcorpore.comum.voltar" /></span>
				</button>	
			
			</td>
		</tr>
	</table>	
</div>
<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/mapaDesenhoServico/mapaDesenhoServico'>
	<input type="hidden" name="idServico"  />
	<input type="hidden" name="idItemConfiguracao" />
	<input type="hidden" name="idImagemItemConfiguracao" />
	<input type="hidden" name="listaItensConfiguracaoSerializada" />
		<div id="menu">
			<menu id="itens">
<!-- 			Os ids devem ser diferentes.		 -->
				<li>
					<label class="tooltip_bottom" style="height: 40px;"
						   title="<i18n:message key="mapaDesenhoServico.software"/>">
					<img src="imagens/software.png" id="1" draggable="true" 
						 class="itemInMenu icone" 
						 ondragstart="dragstart(event)" ></img>
					 </label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;"
						   title="<i18n:message key="mapaDesenhoServico.servidor"/>">
					<img src="imagens/servidor.png" id="2" draggable="true" 
						 class="itemInMenu icone" 
						 ondragstart="dragstart(event)" ></img>
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;"
						   title="<i18n:message key="mapaDesenhoServico.desktop"/>">
						<img src="imagens/desktop.png" id="3" draggable="true" 
							 class="itemInMenu icone" 
							 ondragstart="dragstart(event)"  /></img>
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;"
						   title="<i18n:message key="mapaDesenhoServico.linux"/>">
					<img src="imagens/linux.png" id="4" draggable="true" 
						 class="itemInMenu icone"  
						 ondragstart="dragstart(event)" ></img>
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;"
						   title="<i18n:message key="mapaDesenhoServico.windows"/>">
						<img src="imagens/windows.png" id="5" draggable="true" 
							 class="itemInMenu icone" 
							 ondragstart="dragstart(event)" ></img>
					</label>
				</li>
				<li>
					<label class="tooltip_bottom" style="height: 40px;"
						   title="<i18n:message key="mapaDesenhoServico.switch"/>">
						<img src="imagens/switch.png" id="6" draggable="true" 
							 class="itemInMenu icone" 
							 ondragstart="dragstart(event)" ></img>
					</label>
				</li>
			</menu>
			<ul id="opcoes">
				<%-- <li>
				<div style='background-color: "#ffffff"'>
					<a href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load">
					<label class="tooltip_bottom" style="height: 18px;" 
							   title="<i18n:message key='mapaDesenhoServico.voltarParaPaginaInicial'/>">
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/home_black2.png" 
								 class="icone"></img>
					</label>
					</a>
				</div>
				</li> --%>
				<li>
<!-- 					<a href="#" style="color: #CCCCCC;" onclick="togglePopup("POPUP_SERVICO")">Selecionar Seviço</a> -->
						<label id="hoverServico" class="tooltip_bottom" style="height: 18px;" 
							   title="<i18n:message key='mapaDesenhoServico.selecionarServico'/>">
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/large/grey/magnifying_glass.png"
								 class="icone"  id=""
							 	 onclick="togglePopup('POPUP_SERVICO', 'open');"></img>
						 </label>
				</li>
				<li> 
				<label class="tooltip_bottom" style="height: 18px;"
					   title="<i18n:message key="citcorpore.comum.gravar"/>">
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/large/grey/cassette.png"
						 class="icone"  id="disquete"
						 onclick="mapa.salvarServico();"></img>
				</label>
<!-- 					<a href="#" style="color: #CCCCCC;" onclick="salvarServico();">Salvar Seviço</a> -->
				</li>
				<li>
				<label class="tooltip_bottom" style="height: 18px;"
					   title="<i18n:message key="citcorpore.comum.gerarImpressao"/>">
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/large/grey/printer.png"  class="icone"  id="imprimir" onclick="mapa.imprimirDesenhoMapaServicos()"></img>
				</label>
				</li>
				<!-- <li>
					<label class="msg_erro" id="growlDock" class='growl'></label>
				</li>
				<li>
					<label class="msg" id="msg"></label>	
				</li> -->
			</ul>
		</div>
		<div id="divNomeServico">
			<h2 id="nomeServico"></h2>
		</div>
		<div id="editarItemConfiguracao" style="width: 95%;" class="popup">
			<div id="formItemConfiguracao">
				<table border="1">
				<tr>
					<td>
						<h3 align="left"><i18n:message key='itemConfiguracao.itemConfiguracao'/></h3>
					</td>
				</tr>
					<tr>
						<td>
							<i18n:message key='itemConfiguracao.itemConfiguracao'/>:
						</td>
					</tr>
					<tr>						
						<td>
							<input type="text" id="identificacao" name="identificacao" disabled="disabled" size="100%">
						</td>
						<td>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" 
								 onclick="togglePopupFrame('POPUPITEMCONFIGURACAO', '/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load', 'open')"></img>
						</td>						
					</tr>
					<tr>
						<td><i18n:message key='citcorpore.comum.descricao'/>:</td>
					</tr>
					<tr>
						<td>
							<textarea rows="3" cols="103" id="txtDescricao" name="txtDescricao" maxlength="250">
							</textarea>
						</td>
					</tr>
					<tr>
						<td><label class="msg_erro" id="msg_erro_form_item"></label></td>
					</tr>
					<tr>
						<td colspan="3">						
<!-- 							<a href="#" id="btSalvar" >Salvar</a>&nbsp;&nbsp;&nbsp; -->
<!-- 							<a href="#" onclick="limpaFormItemConfiguracao();" >Limpar</a>&nbsp;&nbsp;&nbsp; -->
							<button type='button' id="btSalvar" name="btSalvar" class="light">
								<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
								<span>
									<i18n:message key="citcorpore.comum.gravar" />
								</span>
							</button>
							<button type='button' onclick="limpaFormItemConfiguracao();" class="light">
								<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
								<span>
									<i18n:message key="citcorpore.comum.limpar" />
								</span>
							</button>
							<button type='button' id="btExcluir" name='btExcluir' class="light" style="display: none">
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
								<span>
									<i18n:message key="citcorpore.comum.excluir" />
								</span>
							</button>
<!-- 							<a href="#" id="btExcluir" style="display: none">Excluir</a> -->
						</td>
					</tr>				
				</table>
			</div>				
		</div>
	</form>
		
 	<div id="POPUP_SERVICO" style="" title="<i18n:message key="citcorpore.comum.pesquisa" />" class="popup">

 			<table >
				<tr>
					<td>
						<h3 align="center"><i18n:message key="servico.servico" /></h3>
					</td>
				</tr>
			</table>
				
			<form name='formPesquisa' style="width: 95%;">
				<cit:findField formName='formPesquisa' 
							   lockupName='LOOKUP_SERVICO_DESENHO' 
							   id='LOOKUP_SERVICO_DESENHO' 
							   top='0' left='0' len='550' 
							   heigth='300' 
							   javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		
		<div id="POPUPITEMCONFIGURACAO" style=""  title="<i18n:message key="citcorpore.comum.pesquisa" />" class="popup section">
				<iframe id="popupitemc" name="popupitemc" width="100%" height="98%"></iframe>
		</div>	
		<div id="imprimir">
		<canvas id="mapaServicos" class=''>
		</canvas>
		</div>
	</body>
</html>