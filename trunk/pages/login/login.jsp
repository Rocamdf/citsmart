<!DOCTYPE html>
<html>
<head>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
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
<%@page import="br.com.citframework.util.UtilStrings"%>
<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>

<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
<link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/login/css/login.css"/>


<title>CITSMart</title>
</head>

<body class="login">

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<i18n:message key='citcorpore.comum.aguardeProcessando'/>" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<!-- Wrapper -->
<div id="login">

	<div class="container">
	
		<div class="wrapper">
		
			<h1 class="glyphicons lock"><img alt="CITSMart" id="logo" src="/citsmart/imagens/logo/logo.png"/><i></i></h1>
		
			<!-- Box -->
			<div class="widget">
				
				<div class="widget-head">
					<h3 class="heading"><i18n:message key="login.area"/></h3>
				</div>
				<div class="widget-body">
				
					<% if (request.getSession().getAttribute("passoInstalacao") == null) {%>
				
					<form name='formInternacionaliza' id='formInternacionaliza' class="marginless" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/internacionalizar/internacionalizar'>
						<input type="hidden" name="locale" id="locale"/>
							<div class="navbar main hidden-print">
							<ul class="topnav pull-right">
								<!-- Language menu -->
								<li class="hidden-phone dropdown dd-1 dd-flags" id="lang_nav">
								
								<% if (locale.equalsIgnoreCase("pt")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    							 <%} else {
    								if (locale.equalsIgnoreCase("en")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/en.png" alt="br"></a>
    							  <%} else {
    								  if (locale.equalsIgnoreCase("es")) {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/es.png" alt="br"></a>
    							    <%} else {%>
    									<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    								<%}
    							    }
    							  }%>
								
							    	<ul class="dropdown-menu pull-left">
							    		<li class="active" onclick="internacionalizar('')"><a href="" title="Portugues" ><img onclick="internacionalizar('')" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
							      		<li onclick="internacionalizar('en')"><a href="" title="English"><img onclick="internacionalizar('en')" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/en.png" alt="English"> English</a></li>
							      		<li onclick="internacionalizar('es')"><a href="" title="Español"><img onclick="internacionalizar('es')" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
							    	</ul>
								</li>
							</ul>
						</div>
					</form>
					
					<% } %>
					
					<!-- Form -->
					<form name="form" onkeydown="if ( event.keyCode == 13 ) validar();" id="formlogin" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/login/login">
					<!-- <input type="hidden" name="locale" id="locale" /> -->
						<label><i18n:message key="login.nomeusuario"/></label>
						<input type="text" class="input-block-level" id="user" name="user" placeholder="<i18n:message key='login.placeholderUsuario'/>"/> 
						<label><i18n:message key="login.senha"/></label>
						<input type="password" class="input-block-level margin-none" id="senha" name="senha" placeholder="<i18n:message key='login.placeholderSenha'/>" />
						<div class="separator bottom"></div> 
						<div class="row-fluid">
							<div class="span8">
								
							<% UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);
										
											boolean usuarioIsAd = usuarioService.usuarioIsAD(WebUtil.getUsuario(request) );	
											
											String metodoAutenticacaoProprio = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.METODO_AUTENTICACAO_Pasta, "1");	
											
											if (metodoAutenticacaoProprio != null && metodoAutenticacaoProprio.trim().equalsIgnoreCase("1") ) {
												
												if (!usuarioIsAd) {
							%>	
								<a class="" href="#modal_alteraSenha" data-toggle="modal" id='modals-bootbox-confirm'><i18n:message key="recuperacaoSenha.esqueceuSuaSenha" /></a>
							<%
								}		
							} 
							%>	
						
							</div>
							<div class="span4 center">
								<button class="btn btn-block btn-primary" onclick='validar();' type="button"><i18n:message key="login.entrar"/></button>
							</div>
						</div>
					</form>
					<!-- // Form END -->
							
				</div>
				<div class="widget-footer">
					<p class="glyphicons restart"><i></i><i18n:message key="login.usuarioSenha"/></p>
					
				</div>
			</div>
			<!-- // Box END -->
			
			<!-- INICIO MODAL REDEFINIR SENHA -->
			<div class="modal hide fade in" id="modal_alteraSenha"  aria-hidden="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h3><i18n:message key="login.esqueceu" /></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<form name="form1" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/login/login" method="post">
						<label for="login"><i18n:message key="recuperacaoSenha.loginOuEmail" /></label>
						<div class='row-fluid'>
							<div class='span12'>
								<input type="text" class="" id="" name="login" placeholder="<i18n:message key="recuperacaoSenha.dica.loginOuEmail" />" />
							</div>
						</div>
					</form>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
				<div class="modal-footer">
					<a href="#" class="btn btn-default" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					<a href="#" data-dismiss="modal" class="btn btn-primary" onclick="document.form1.fireEvent('redefinirSenha');"><i18n:message key="citcorpore.comum.gravar" /></a>
				</div>
				<!-- // Modal footer END -->
			</div>
			
		</div>
		<div class="innerAll center">
		<p><i></i><i18n:message key="login.problema"/></p>
			<span class="glyphicons phone" data-toggle="notyfy" data-layout="topRight" data-type="primary"><i></i><b><i18n:message key="citcorpore.comum.suporte"/></b><span class=""> +55 (61) 3966 - 4349</span></span>&nbsp; &nbsp;
			<a href="mailto:suporte.citsmart@citsmart.com.br?Subject=[<i18n:message key="citcorpore.comum.suporte"/>]" target="top" data-toggle="" class="glyphicons envelope"><i></i>suporte.citsmart@citsmart.com.br <span class=""></span></a>
				
		</div> 
		<div class="innerAll center">
			<p><i></i><i18n:message key="login.certificado"/></p>
				<a href="http://www.pinkelephant.com/PinkVERIFY/PinkVERIFY_2011_Toolsets.htm" target="_blank" class="">
					<img alt="" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/PinkVERIFY9.png" >
					<i></i>
					<span></span>
					<span class="strong"></span>
				</a>
		
		</div> 
		
	</div>
	
</div>
<div class="modal hide fade in" id="mensagem_insercao" aria-hidden="false" data-backdrop="static" data-keyboard="false">
	<!-- Modal heading -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<i18n:message key="instalacao.mensagemInsucessoAvisoImportante"/>
	</div>
	<!-- // Modal heading END -->
	<!-- Modal body -->
	<div class="modal-body" >
		<div id="divInsercao">
			
		</div>

	</div>
	<!-- // Modal body END -->
	<!-- Modal footer -->
	<div class="modal-footer">
		<a id="btFechar" href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
	</div>
</div>
<!-- // Wrapper END -->	

<!-- Themer -->
<!-- <div id="themer" class="collapse">
	<div class="wrapper">
		<span class="close2">&times; close</span>
		<h4>Themer <span>color options</span></h4>
		<ul>
			<li>Theme: <select id="themer-theme" class="pull-right"></select><div class="clearfix"></div></li>
			<li>Primary Color: <input type="text" data-type="minicolors" data-default="#ffffff" data-slider="hue" data-textfield="false" data-position="left" id="themer-primary-cp" /><div class="clearfix"></div></li>
			<li>
				<span class="link" id="themer-custom-reset">reset theme</span>
				<span class="pull-right"><label>advanced <input type="checkbox" value="1" id="themer-advanced-toggle" /></label></span>
			</li>
		</ul>
		<div id="themer-getcode" class="hide">
			<hr class="separator" />
			<button class="btn btn-primary btn-small pull-right btn-icon glyphicons download" id="themer-getcode-less"><i></i>Get LESS</button>
			<button class="btn btn-inverse btn-small pull-right btn-icon glyphicons download" id="themer-getcode-css"><i></i>Get CSS</button>
			<div class="clearfix"></div>
		</div>
	</div>
</div> -->
<!-- // Themer END -->
<%@include file="/novoLayout/common/include/libRodape.jsp" %>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/login/js/login.js"></script>
<script src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/include/js/internacionalizar.js"></script>
</body>
</html>