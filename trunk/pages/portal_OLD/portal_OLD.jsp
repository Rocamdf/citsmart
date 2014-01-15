<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%
	/* String PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL = ParametroUtil.getValue(Enumerados.ParametroSistema.PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL, "");
	if (PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL == null){
		PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL = "";
	}	
	
	PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL = PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL.trim();   
	if (PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL.trim().equalsIgnoreCase("")){ */
		String PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL = "/pages/solicitacaoServicoMultiContratosPortal/solicitacaoServicoMultiContratosPortal.load";
	/* } */
	
	String login = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null)
		login = usuario.getLogin();
%>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);

	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
	UsuarioDTO usrDto = (UsuarioDTO) WebUtil.getUsuario(request);
%>
<% if(usrDto != null){ %>
<% }else{ %>
<% } %>
<%@include file="/include/security/security.jsp"%>

		<!-- <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"> -->
		<title>CITSMart</title>
		<%@include file="/include/noCache/noCache.jsp"%>
			
		<link rel="stylesheet" type="text/css" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/css/jquery-ui-1.8.21.custom.css" />
		<link rel="stylesheet" type="text/css" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/css/index.css" />
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/js/jquery-1.7.2.js"></script>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/js/jquery-ui-1.8.js"></script>		
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/js/default.js"></script>
		<script type="text/javascript" src="../../cit/objects/ServicoDTO.js"></script>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<style type="text/css">
			.floatingTabsBox form {
				position: absolute;
				right: 0;
				top: 0;
				width: 700px;
				color: #3F4145;
				box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.3);
				border-radius: 0 0 0 6px;
				border: 3px solid #D6D6D6;
				border-right: none;
				padding: 25px 30px 0px;
				background: white;
			}
			.floatingTabsBox {
				position: fixed;
				right: 0;
				top: 160px;
				z-index: 1002;
			}
			.floatingTabsBox .floatingTab {
				height: 170px;
				width: 30px;
				line-height: 0;
				position: absolute;
				top: -3px;
				left: -35px;
				padding: 0;
				display: block;
				font-size: 14px;
				font-weight: normal;
				-moz-border-radius: 4px 0 0 4px;
				-webkit-border-radius: 4px 0 0 4px;
				border-radius: 4px 0 0 4px;
				border: 0;
				box-shadow: none;
				cursor: pointer;
			}
			.floatingTabsBox form legend {
				font-weight: 700;
				font-size: 21px;
				font-family: 'Open Sans', sans-serif;
				padding-bottom: 20px;
			}
			.floatingTabsBox .floatingTab div {
				-webkit-transform: rotate(-90deg);
				-moz-transform: rotate(-90deg);
				filter: progid:DXImageTransform.Microsoft.BasicImage(rotation=3);
				-o-transform: rotate(-90deg);
				transform: rotate(-90deg);
				color: white;
				height: 30px;
				width: 155px;
				text-align: center;
				position: absolute;
				top: 70px;
				top: 0	9;
				left: -65px;
				left: 0	9;
				line-height: 30px;
				text-shadow: 1px 0 0 rgba(0, 0, 0, 0.4);
				cursor: pointer;
			}
			.floatingTabsBox .floatingTab {
				line-height: 0;
				font-size: 14px;
				font-weight: normal;
				cursor: pointer;
			}
			.floatingTabsBox label {
				display: block;
				font-weight: bold;
				color: black;
				margin-bottom: 5px;
			}
			.helpForm p {
				margin-bottom: 25px;
			}
			#tabHelp {
				background: #7EB738;
			}
		
			#tabs-4 {
				overflow: auto;
			}
			#faq ul, #tabCatalogoNegocio ul {
				font-family: Verdana, Arial, Helvetica, sans-serif;
				font-size: 12px;
				font-style: normal;
				line-height: 2em;
				font-weight: normal;
				font-variant: normal;
				text-transform: none;
				color: #000;
				text-decoration: none;
				text-indent: 5px;
				list-style-position: outside;
				list-style-image: url(arrow.gif);
				list-style-type: square;
				padding: 6px;
				margin: 2px;
			}
			#faq span, #tabCatalogoNegocio span {
				font-weight: bold;
				font-size: small;
				
			}			
			#faq a, #tabCatalogoNegocio a {
				color: #245DC1;
				font-weight: normal;
				text-decoration: none;
				font-size: x-small;
			}
			#faq a:hover, #tabCatalogoNegocio a:hover {
				color: #7EB738;
				font-weight: normal;
				text-decoration: underline;
			}
			#tabCatalogoNegocio {
				height: 270px;
		    	overflow: auto;
		    	width: auto;
		    }    
			.sel {
				display: none;
				background: none  !important;;
				cursor:auto;
				margin: 15px 10px;
			}
			.span {
				display: block;
			}
			.btnAbrirSolicitacao {
				left: 10px;
				bottom: 30px;
				position: float;
			}
			h2 { 
				margin: 0.83em 0; 
			}
			p { 
				margin: 1em 0; 
			}
			.preFooter {
				height: 55px; 
				width: 100%; 
				position: relative;
			}
			.certificado {
				position: absolute;
				right: 10px;
				bottom: 3px;
			}
			#btnCancelar{
			
				margin-bottom: 5px;
				margin-top: 5px;
			}
			.catalogo:hover{
			
				color: #7EB738 !important;
				font-weight: normal !important;
				text-decoration: underline !important;
			}
			.infoLogin{
				font-family: sans-serif;
				padding: 15px
			}
			
		</style>
		
		<script type="text/javascript">
			addEvent(window, "load", load, false);
			
			function load() {
				$('#mensagemNavegadorFirefox').hide();
				$('#mensagemNavegadorIe').hide();
				
				if (window.location != window.top.location) {
			    	window.top.location = window.location;
				}
				
				validarNavegador();
				
				$("#user").focus();
			}
			
			function validarNavegador(){
				if ($.browser.msie) {
					var versao =  parseInt($.browser.version, 10);
					if (versao < 9) {
						$("#mensagemNavegadorIe").dialog({
							height: 155,
							modal: true,
							autoOpen: false,
							width: 350,
							resizable: false,
							show: "fade",
							hide: "fade",
							position: "center"
						});
						$(".ui-dialog").css("width", "auto");
						$("#mensagemNavegadorIe").dialog('open');
						$("#wrapper").hide();
						return false;
					} else { 
						$("#mensagemNavegadorIe").hide();
						return true;
					}				
				} else {
					$("#mensagemNavegadorIe").hide();
					if($.browser.mozilla){
						var versao =  parseInt($.browser.version, 10);
						if (versao < 9) {
							$("#mensagemNavegadorFirefox").dialog({
								height: 160,
								modal: true,
								autoOpen: false,
								width: "20%",
								resizable: false,
								show: "fade",
								hide: "fade",
								position: "center"
							});
							$(".ui-dialog").css("width", "auto");
							$("#mensagemNavegadorFirefox").dialog('open');
							$("#wrapper").hide();
							
							return false;
						} else { 
							$("#mensagemNavegadorFirefox").hide();
							return true;
						}
					} else{
						return true;
					}
				}
			}
			
			function abrirLogin(){
				if (validarNavegador()){
					$('#popupLogin').dialog('open');
				}
			}
	
			validar = function() {
				if($("#user").val() == ""){
					alert(i18n_message("login.digite_login"));
					$("#user").focus();
				}else if($("#senha").val() == ""){
					alert(i18n_message("login.digite_senha"));
					$("#senha").focus();
				}else{
					JANELA_AGUARDE_MENU.show();
					document.form.action = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/login/login.save?user='+$("#user").val()+'&senha='+document.getElementById('senha').value;
					document.form.save();
				}
	
				fechar_aguarde = function(){
			    	JANELA_AGUARDE_MENU.hide();
				}
			}
			
			function encaminhaAosErrosDeScript() {
				document.form.submit();
				window.location = '<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/scripts/scripts.load?upgrade=sim';
			}
			
			function logar(){
				document.form.submit()
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/portal/portal.load';
			}		
			
			function popupNovaSolicitacao()
			{
				document.getElementById('frameNovaSolicitacao').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL%>';
				$('#popupNovaSolicitacao').dialog('open');
			}
			
			function popupNovaSolicitacaoCatalogoNegocio()
			{
				document.getElementById('frameNovaSolicitacao').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO_SOLICITACAOSERVICO_PORTAL%>?nomeServico=' + document.formCatalogo.nomeServico.value;
				$('#popupNovaSolicitacao').dialog('open');
			}
			
			function logout() {
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/portal.load?logout=yes';
			}
			
			fecharSolicitacao = function(){
				$('#popupNovaSolicitacao').dialog('close');
				$('#POPUP_CONTEUDOCATALOGO').dialog('close');
				JANELA_AGUARDE_MENU.show();
				document.formPortal.fireEvent('load');
			};
			/**
			Autor: Pedro Lino
			Habilita e desabilita o resizable das portlets
			**/
			function habilitaDesabilitaResizable(){
				if(document.getElementById('resizableManual').checked == true){
					$( ".portlet" ).resizable( "option", {disabled: false, minHeight: 100, minWidth: 350, containment: "#right"});
					redimensionamento()
				}else{
					$( ".portlet" ).resizable( "option", {disabled: true} );
					$( ".portlet" ).removeClass( "ui-state-disabled" ); //Retira efeito modal ao remover o resizable;
				}
			}
	
			function atualizaPagina(){
	      		if ( document.getElementById('chkAtualiza').checked ) {
	      			document.formPortal.fireEvent('load');
	      		} 
	      	}
			
			window.setInterval(atualizaPagina, 30000);	
		function novaTelaOrdensServico(){
			window.open('pages/informacoesContrato/informacoesContrato.load?portal=true');
		}
		</script>	
	</head>
		<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="estructure">
			<form name="form" id="form" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/portal">
				<div id="popupLogin" class="dialog" title="Login">
					<label>Login:
						<input type="text" id="user" size="35" name="user" class="texto ui-widget ui-state-default" onkeypress="submitEnter(event)" />
					</label>
					<label>Senha:
						<input type="password" id="senha"  size="35" name="senha" class="texto ui-widget ui-state-default" onkeypress="submitEnter(event)" />
					</label>
					<hr />
					<label>
						<input type="button" id="botao" name="botao" title="Logar no Sistema" value="ENVIAR" class="botao ui-widget ui-state-default" onclick="validar();" />
					</label>
				</div>
			</form>				
			<div id="top">
				<div class="floatLeft logo paddingL10">
			       <a href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/portal/portal.load" ><img border="0" src="/citsmart/imagens/logo/logo.png"></a>
			    </div>
			    <div class="floatRight item">
<!-- 					<div class="redes_sociais">
						<a target="_blank" href="http://www.facebook.com/pages/Central-IT-Tecnologia-da-InformaÃ§Ã£o/"  style="margin:4px;"><img  border="0" title="Siga-nos Facebook" alt="Facebook" src="/citsmart/template_new/images/icons/large/grey/facebook.png "></a>
						<a target="_blank" href="http://twitter.com/Central_IT" rel="nofollow" style="margin:4px;"><img title="Siga-nos Twitter" alt="Twitter"  border="0" src="/citsmart/template_new/images/icons/large/grey/twitter.png "></a>
						<a target="_blank" href="https://picasaweb.google.com/106509295606978450336" rel="nofollow" style="margin:4px;"><img title="Siga-nos Picasa"  border="0" alt="Picasa" src="/citsmart/template_new/images/icons/large/grey/picasa.png "></a>
						<a target="_blank" href="http://www.linkedin.com/company/central-it" rel="nofollow" style="margin:4px;"><img title="Siga-nos Linkedin"  border="0" alt="Linkedin" src="/citsmart/template_new/images/icons/large/grey/linkedin.png "></a>
					</div> -->
		    		<div class="login">
			    		<label class="btnLogin" for="btnLogin">
			    		<% if(usrDto != null){ %>
			    			<input type="button"  class="botao ui-widget ui-state-default lFloat" id="btnLogin" name="btnLogin" onclick="window.open('<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load','_blank')" value="CITSmart" />
			    			<a class="botao ui-widget ui-state-default lFloat" id="btnLogout" name="btnLogout" href="javascript:;" onclick="logout();" >Sair</a>
						<% }else{ %>
							<input type="button" id="btnLogin" name="btnLogin" title="Clique aqui para logar" onclick="abrirLogin()" value="LOGIN" class="botao ui-widget ui-state-default" />
						<% } %>
						</label>
					</div>
					<div style=" height: 80%; width: 50%">
						<div class='infoLogin' id="usuario"></div>
						<div class='infoLogin' id="unidade"></div>
					</div>
			    </div>		     			    	
			</div>
			<div id="body">
					<form id="formPortal" name="formPortal" method="post" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/portal" >
						<input type='hidden' name="largura" id="largura"  />
						<input type='hidden' name="altura" id="altura"  />
						<input type='hidden' name="posicaoY" id="posicaoY"  />
						<input type='hidden' name="posicaoX" id="posicaoX"  />
						<input type='hidden' name="id" id="id"  />
						<input type='hidden' name="idItem" id="idItem"  />				
						<input type='hidden' name="idServico" id="idServico"  />
						<input type='hidden' name="coluna" id="coluna"  />
						<input type='hidden' name="nomeServico" id="nomeServico"  />
						<input type='hidden' name="idPost" id="idPost"  />
					</form>
									
				<% if(usrDto != null){ %>
					<script>	    				  
					    function executaModal(id)
					    {
					    	window.open('<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimentoView/modalBaseConhecimento.load?id='+id);
					    
					    }
					    function executaModalFaq(id)
					    {
					    	window.open('<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaFaq/pesquisaFaq.load?id='+id);
					    
					    }
					    function executaModalIncidentes(idSolicitacao)
					    {
					    	window.open('<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicos/gerenciamentoServicos.load?idSolicitacao='+idSolicitacao);
					    }
					    function executaModalPesquisa(idSolicitacao,hash)
					    {
					    	document.getElementById('frameCadastroRapido').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaSatisfacao/pesquisaSatisfacao.load?idSolicitacaoServico='+idSolicitacao+'&hash='+hash+'&frame=sim';
					    	$("#popupCadastroRapido").dialog('option', 'title', 'Pesquisa de SatisfaÃ§Ã£o do Usuário');
					    	$("#popupCadastroRapido").dialog('open');
					    }

						function executaModalOpiniao(idSolicitacao){
							document.getElementById('frameCadastroRapido').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/opiniao/opiniao.load?idSolicitacao='+idSolicitacao;
							$("#popupCadastroRapido").dialog('option', 'title', 'Registre sua OpiniÃ£o');
							$("#popupCadastroRapido").dialog('open');
					    }								
						
				    </script>																																																																																																																																																																												

					<!-- 	<div id="left" class=''>
					
							<ul id="column0">
											
							</ul>																																									
						</div> -->
						<div id="right">
							<!-- Seta que habilita div para antiga funcionalidade dos portlets usando sortable -->
							 <!-- <div id="container"><div id="setaColumn" class="show"></div></div>  -->
							<ul>
								<!-- <li><a href="#tabs-0" >Meu Portal</a></li> -->
								<li><a href="#tabs-1" >Catálogos de Negócio</a></li>
								<li><a href="#tabs-2" >Solicitações / Incidentes</a></li>
								<li><a href="#tabs-3" >Base de Conhecimento</a></li>
								<li><a href="#tabs-4" >FAQ</a></li>
								<li><a href="#tabs-5" id="ordensServico" onclick="novaTelaOrdensServico()"> Ordens de Serviço</a></li>
								<li style="float: right;">
								
									<input type='checkbox' id='chkAtualiza' checked="checked" name='chkAtualiza' value='X'/>
								<label for="chkAtualiza">	<i18n:message key="citcorpore.comum.atualizar" />&nbsp;<i18n:message key="citcorpore.comum.automaticamente" />
									&nbsp;<i18n:message key="citcorpore.comum.aTela" />
								</label>
									<input type="checkbox" id="resizableManual"  name="resizableManual" onclick="habilitaDesabilitaResizable()" />
										<!-- <label for="resizableManual"> Redimensionar tabelas</label>  -->
										<label for="resizableManual"><i18n:message key="portal.redimensionarTabela" /></label>
								</li>										
							</ul>	
							<div id="tabs-1" style="height: 90%" class="ui-helper-reset">
							
								<div id='col2'><ul id="column2" class="connectedSortable"></ul></div>
								<div id='col2_1' style="height: 230px"><ul id="column2_1" class="connectedSortable"></ul></div>
										
								
								<div class="floatingTabsBox">
									<form id="formCatalogo" name="formCatalogo" class="helpForm open" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/catalogoServico/catalogoServico" style="right: -752px;">
										 <input id='idInfoCatalogoServico' name='idInfoCatalogoServico' type='hidden'/>
										 <input id='nomeServico' name='nomeServico' type='hidden'/> 
										  <input id='idServicoCatalogo' name='idServicoCatalogo' type='hidden'/>  
										 
										<div id="tabHelp" class="floatingTab">
											<div>Catálogo de Serviços</div>
										</div>
										<legend>Catálogo de Serviços</legend>
										<div id="tabCatalogoNegocio">
											<ul id="ulTabCatalogoNegocio">
											
											</ul>
										</div>
										<div class="floatingTabsButtons cf">
											<button type="button" id="btnCancelar" class="btn secondary cancelBtn botao ui-widget ui-state-default lFloat">Cancelar</button> 
										</div>
										
									<div id="POPUP_CONTEUDOCATALOGO">
											<h2 id="tituloCatalogo"></h2>
											<p id="conteudoCatalogo"></p>
											<div class="btnAbrirSolicitacao">
												<input type="button" onclick="popupNovaSolicitacaoCatalogoNegocio()" class="botao ui-widget ui-state-default lFloat" value="Abrir Solicitação">
											</div>				
										</div>
									</form>
								</div>
							</div>	
							<div id="tabs-2" style="height: 90%; " class="ui-helper-reset">
								<div id='col3'><ul id="column3" class="connectedSortable"></ul></div>
								<div id='col3_1'><ul id="column3_1" class="connectedSortable"></ul></div>
										
									
							</div>
							<div id="tabs-3" style="height: 90%" class="ui-helper-reset">
								<ul id="column4" class="connectedSortable">
										
								</ul>	
							</div>
							<div id="tabs-4" style="height: 90%" class="ui-helper-reset">
								<ul id="column5" class="connectedSortable">
							</ul>
								<!-- <div id="faq"></div> -->
							</div>
							<div id="popupNovaSolicitacao" class="dialog" style='width: 1000px;'>
								<iframe id="frameNovaSolicitacao"  style='border:0px' name="frameNovaSolicitacao" width="100%" height="100%" src="" ></iframe>
							</div>
							
						</div>								
				<% }else{ %>																																																																																																																																																						
					<div id="right" class='nosidebar ui-dialog-content ui-widget-content ui-corner-all'>
				<%	if(request.getParameter("idPost") == null)	{		%>
							
							<div style='margin: 10px 0;'>
								<h1 class="h1">
									<i18n:message key="portal.bemVindoPortal"/>									
								</h1>
								<%-- <iframe src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/home.html" style='width:100%; overflow: hidden' height="430" frameborder="0" ></iframe> --%>	
							</div>
							
							<div class="floatLeft">
								<div class="portletInicial">
									<div class="portletInicial-border">
										<div class="content">
											<div class="header" id="header-title-1">
												<h2>
													<span class="fn"></span>
												</h2>
											</div>
											<div class="content-entry" id="entry">
												
											</div>
										</div>
									</div>
								</div>
							</div>
									
							<div id="page" style="display: none"></div>		
						<%	} else {	%>
							<div class="cab"><button onclick="javascript:location.href='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/portal.load'">HOME</button></div>
							<div id="page" class="page lFloat"></div>							
						<% } %>
						<div class="clear"></div>
					</div>		
				<% } %>	
				
				<div class="clear"></div>			
				
			</div>
			<div class="clear"></div>
			<div class="preFooter">
				<div class="certificado">
					<a href="http://www.pinkelephant.com/PinkVERIFY/PinkVERIFY_2011_Toolsets.htm" target="_blank">
						<img alt="" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/PinkVERIFY_9.PNG" >
					</a>
				</div>
			</div>
			
			<div id="footer">
				<div class="copy">
		            <div class="centered alignCenter">	
		                <div class="lineHeight30 f11"> Todos os direitos reservados </div>	
		            </div>
        		</div>
			</div>						
		</div>
		
		<div id="popupCadastroRapido">
			<iframe id="frameCadastroRapido"  style='border:0px' name="frameCadastroRapido" width="100%" height="100%"></iframe>
		</div>
		
		<div id="mensagemNavegadorIe" title="Atualize seu navegador"  >
			<form name='formIe'>
				<fieldset style="border: 2px; height: 80% !important; width: 100%; padding: 0px !important;">
					<label style="font-size: 14px !important; line-height: 24px;">
					 <i18n:message key="login.navegadorIEIncompativel"/>
					 <span  style="font-weight: bold; font-size: 14px !important; "><br><a href="http://windows.microsoft.com/pt-BR/internet-explorer/products/ie/home" style="color: grey !important;">Atualize aqui!</a></span></label>
				</fieldset>
			</form>
		</div>	
		
		<div id="mensagemNavegadorFirefox" title="Atualize seu navegador"  >
			<form name='formFirefox'>
				<fieldset style="border: 2px; height: 80% !important; width: 100%; padding: 0px !important;">
					<label style="font-size: 14px !important; line-height: 24px;">
					 <i18n:message key="login.navegadorFFIncompativel"/>
					 <span  style="font-weight: bold; font-size: 14px !important; "><br><a href="https://www.mozilla.org/pt-BR/firefox/new/" style="color: grey !important;"><i18n:message key="login.atualizeNavegador"/></a></span></label>
				</fieldset>
			</form>
		</div>
	</body>
</html>
