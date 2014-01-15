<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>

<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.versao.Versao" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>
<%@ page import="br.com.citframework.service.ServiceLocator" %>
<%@ page import="br.com.centralit.citcorpore.negocio.ParametroCorporeService" %>
<%@ page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@ page import="br.com.centralit.citcorpore.negocio.UsuarioService" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados" %>

<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
%>

<%
	String parametroCITSmart = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LOGIN_PORTAL, "N");
 
	if (parametroCITSmart.trim().equalsIgnoreCase("S") ) {		
		response.sendRedirect("./portal/portal.load");		
	}
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>		
		<title><i18n:message key="citcorpore.comum.title"/></title>
		
		<%@ include file="/include/noCache/noCache.jsp" %>
		<%@ include file="/include/header.jsp" %>
		
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/defines.js"></script>	
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/tabber.js"></script>	
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ObjectUtils.js"></script>	
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/AjaxUtils.js"></script>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/HTMLUtils.js"></script>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
		
		<script>		
			addEvent(window, "load", load, false);
			
			popupAlteracaoSenha = new PopupManager(1200, 300, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			
			function load() {
				$('#mensagemNavegadorFirefox').hide();
				$('#mensagemNavegadorIe').hide();
				
				if (window.location != window.top.location) {
			    	window.top.location = window.location;
				}
				
				validarNavegador();
				document.form.user.focus();				
				document.getElementById("user").focus();				
				
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
			
			function abrirPopupAlteracaoSenha() {
				popupAlteracaoSenha.abrePopupParms("alteracaoSenha", "");
			}
			
			validar = function() {
				if (validarNavegador()){
					JANELA_AGUARDE_MENU.show();
					document.form.save();
					$("#user").focus();
				}
			}
		
			aguarde = function() {
				JANELA_AGUARDE_MENU.show();
			}
			
			fechar_aguarde = function() {
		    	JANELA_AGUARDE_MENU.hide();
			}
		
			$(document).ready(function() {
				
				$('.abalinguas').click(function() {
					if ($("#lang").is('.hide')) {
						$("#lang").removeClass('hide').addClass('show');
					} else {
						$("#lang").removeClass('show').addClass('hide');
					}
				});
				
				$('#gbg4').click(function() {
					if ($("#gbd4").is('.visibilityFalse')) {						
						$('#gbd4').removeClass('visibilityFalse').addClass('visibilityTrue');
					} else { 
						$('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse');
					}
				});
				
				function hidden(){
					$('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse')
				}
				
				$('body').click(function(e){
				   if(!$(e.target).hasClass('TRUE')) {
					   $('#gbd4').removeClass('visibilityTrue').addClass('visibilityFalse');
				   }
				});
			
				var altura = $(window).height() - 140;
				$("#main_container").css("height", altura);
				
			});
		
			function internacionalizar(parametro) {
		  		document.getElementById('locale').value = parametro;
			  	document.formInternacionaliza.fireEvent('internacionaliza');
		 	}	
			
			function logar() {
				document.form.submit();
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
			}
			
			function encaminhaAosErrosDeScript() {
				document.form.submit();
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/scripts/scripts.load?upgrade=sim';
			}
			
			function alterarSenha() {
				document.form.submit();
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/alterarSenha/alterarSenha.load';
			}
			
			function alterarSenha() {
				document.form.submit();
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/alterarSenha/alterarSenha.load';
			}
			
			$(function() {
				$('#popupAlteracaoSenha').dialog({
					autoOpen: false,
					width: 705,
					height: 350,
					modal: true
				});
			});
			
			function abrirPopupAlteracaoSenha() {
				$('#popupAlteracaoSenha').dialog('open');
			}
			
			$(".validate_form").validate();
			
			$("#user").focus();
		</script>
		
		<style type="text/css">
			body {
				font-size: 11px;
				background: #EEEEEE;
				margin: 0;
				padding: 0;
			}
			.topo {
				display: block;
				width: 100%;
				margin-bottom: 7px;
				background: -moz-linear-gradient(center top , #eee, #fff) repeat scroll 0 0 #F5F5F5;
				border: 2px solid #FFFFFF;
				box-shadow: 0 0 4px rgba(0, 0, 0, 0.3);
				height: 79px;
			}
			.conteudo {
				padding: 5px 0 200px 0;
				background: #FFFFFF;
				position: relative;
			}
			.conteudo .main{
				margin: 0 auto;
				width: 600px;
			}
			.main h1 {
				margin: 0 0 .3em !important;
				white-space: nowrap !important;
				text-align: center;
			}
			h1 {
				color: #000 !important;
				font-size: 3.2em !important;
				font-weight: 300 !important;
				line-height: 1.15 !important;
				margin: 0 0 .7em !important;
			}
			fieldset {
				border: 0 !important;
				
			}
			fieldset > label {
				margin: 1em 0 0.5em !important;
				padding: 1px 20px !important;
				font-size: 15px !important;
			}
			.no_label {
				padding: 1px 15px !important;
			}
			input[type=text],input[type=password],textarea{
				border:1px solid #B3B3B3 !important;
				background-color: #FFF !important;
			}
			.boxLogin {
				background: #F1F1F1;
				border: 1px solid #E5E5E5;
				padding: 10px 30px;
			}
			.footer {
				background: url("../../../template_new/images/backgrounds/top-bg.png") repeat scroll 0 0 #262829;
				bottom: 0;
				/* position: absolute; */
				box-shadow: 0 -1px 2px #404040;
				font-size: 13px;
				padding-bottom: 5px;
				text-shadow: 0 1px 1px #000000;
				width: 100%;
				z-index: 1000;
				height: 25px;
				font-family: arial;
			}
			.copy > div {
				padding: 4px 0;
				color: #DDDDDD;
			}
			.alignCenter {
				text-align: center;
			}
			.centered {
				margin: auto;
				width: 980px;
			}
			.f11 {
				font-size: 11px;
			}
			.lineHeight30 {
				line-height: 30px;
			}
			.certificado {
				position: absolute;
				right: 10px;
				bottom: 10px;
			}
			
			.esqueceuSenha:hover {
				text-decoration: underline;
			}
		</style>
	</head>
	<body onload="$('#user').focus();">	
	
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
		
	<div id="linguas" style="margin-top: 30px;">
		<div class="abalinguas"><img title="Selecione o Idioma" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/white/globe_2.png"></div>
		<div id="lang" class="menulinguas hide"> 
	  	 <form name='formInternacionaliza' id='formInternacionaliza' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/internacionalizar/internacionalizar'>
  			<input type="hidden" name="locale" id="locale"/>
			<img title="Português" onclick="internacionalizar('')" class="tooltip_top" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/brazil_flag.png">
			<img title="English" onclick="internacionalizar('en')" class="tooltip_top" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/united_states_flag.png">
			<img title="Español" onclick="internacionalizar('es')" class="tooltip_top" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/spain_flag.png">
		 </form>
	  	</div>
	</div>
	<div class="topo">
		<div class="floatLeft logo paddingL10">
	       <a href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/login/login.load" ><img border="0" src="/citsmart/imagens/logo/logo.png"></a>
	    </div>
	</div>
	<div class="conteudo">
		<div class="main">
			<h1><i18n:message key="login.bemvindo"/></h1>
			<div class="boxLogin">
				<form name="form" onkeydown="if ( event.keyCode == 13 ) validar();" id="formlogin" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/login/login">
					<input type="hidden" name="locale" id="locale" />
					<fieldset>
						<label for="user"><i18n:message key="login.login"/></label>
						<div>
							<input type="text" id="user" name="user" class="required" >
						</div>
					</fieldset>						
					<fieldset>
						<label for="senha"><i18n:message key="login.senha"/></label>
						<div>
							<input type="password" id="senha" name="senha" class="required">
						</div>
					</fieldset>
					<fieldset class="no_label" style="text-align: right;">																							
						<button type='button' name='btnGravar' style="display: block; float: right; margin-top: 5px; width: 150px; text-align: center;" class="light" onclick='validar();'>							
							<span style="font-weight: bold;"><i18n:message key="login.login"/></span>
						</button>
						<%
							UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
										
											boolean usuarioIsAd = usuarioService.usuarioIsAD(WebUtil.getUsuario(request) );	
											
											String metodoAutenticacaoProprio = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.METODO_AUTENTICACAO_Pasta, "1");	
											
											if (metodoAutenticacaoProprio != null && metodoAutenticacaoProprio.trim().equalsIgnoreCase("1") ) {
												
												if (!usuarioIsAd) {
						%>				
							<span style="font-size: 14px; float: left; margin-left: 185px; margin-top: 10px;"><a id="lnkEsqueceuSenha" href="#" class="esqueceuSenha" onclick="abrirPopupAlteracaoSenha();"><i18n:message key="recuperacaoSenha.esqueceuSuaSenha" /></a></span>								
						<%
								}		
							} 
						%>	
					</fieldset>
					<fieldset class="no_label" style="text-align: right;">					
						<br>
						<i18n:message key="login.versao"/> <b><%=Versao.getDataAndVersao()%></b>
						<%
							if(br.com.citframework.util.Util.isVersionFree(request).booleanValue()){												
						%>		
							<font color="red" style="margin-left: 10px;"><b>Versão Community</b></font>
						<%} 
						%>				
					</fieldset>
				</form>	
			</div>
		</div>
		 <div class="certificado">
			<a href="http://www.pinkelephant.com/PinkVERIFY/PinkVERIFY_2011_Toolsets.htm" target="_blank">
				<img alt="" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/PinkVERIFY_9.PNG" >
			</a>
		</div>
	</div>
	<div class="footer">
		<div class="copy">
           <div class="centered alignCenter">	
               <div class="lineHeight30 f11"> Todos os direitos reservados</div>	
           </div>
   		</div>
	</div>
	<div id="loading_overlay">
		<div class="loading_message round_bottom">
			<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/template_new/images/loading.gif" alt="loading" />
		</div>
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
	
	<div id="popupAlteracaoSenha" style="overflow: hidden;">
		<div class="conteudo">
			<div class="main">
				<h1><i18n:message key="login.esqueceu" /></h1>
				<h2 style="padding-left: 0;"><i18n:message key="recuperacaoSenha.informeDados" /></h2>
				<div class="boxLogin">
					<form name="form1" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/login/login" method="post">
						<fieldset>
							<label for="login"><i18n:message key="recuperacaoSenha.loginOuEmail" />:</label>
							<div>
								<input type="text" id="login" name="login" class="Valid[Required] Description[recuperacaoSenha.loginOuEmail]" title='<i18n:message key="recuperacaoSenha.dica.loginOuEmail" />' >
							</div>
						</fieldset>
						<fieldset class="no_label" style="text-align: right;">																							
							<button type="button" name="btnEnviar" style="display: block; float: right; margin-top: 5px; width: 150px; text-align: center;" class="light" onclick="document.form1.fireEvent('redefinirSenha');" title='<i18n:message key="recuperacaoSenha.dica.BotaoEnviar" />' >							
								<span style="font-weight: bold;"><i18n:message key="citSmart.comum.enviar" /></span>
							</button>
						</fieldset>
					</form>
				</div>					
			</div>
		</div>
	</div>	
	</body>
</html>