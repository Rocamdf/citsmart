
<!-- Inicio Panel-Header | Topo principal -->
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<%@page import="br.com.citframework.service.ServiceLocator"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.negocio.ParametroCorporeService"%>
<%@page import="br.com.centralit.citcorpore.negocio.UsuarioService"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>

<%@ taglib uri="/tags/menu" prefix="m"%>

<div class="panel-header" style="letter-spacing: 0;">
	<div class="logo">
		<a href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/index/index.load" ><img src="/citsmart/imagens/logo/logo.png"></a>
	</div>
	
	<div class="menuCenter">
		<m:menu rapido="S" />
	</div>
	<%
	UsuarioDTO usuarioAux = WebUtil.getUsuario(request);
	String nomeUsuarioTopoX = "";
	if (usuarioAux != null){
		nomeUsuarioTopoX = usuarioAux.getNomeUsuario();
	}
	%>
	<div class="account">
		<ol class="gbtc">
			<li class="gbt">
				<a class="gbgt" >
					<span	id="gbi4t" style="max-width: 781px; text-align: left;"><%= nomeUsuarioTopoX %>  </span>
				</a>
			</li>
			<li class="gbt gbto TRUE">
				<a class="gbgt gbg4p gbes TRUE" id="gbg4" href="javascript:;">
					<span id="gbgs4" class="TRUE">
						<img id="gbi4i" class="TRUE" width="27" height="27"	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/user.png">
						<span class="gbmai TRUE"></span>
					</span>
				</a>
				<div class="gbm gbes TRUE visibilityFalse" id="gbd4" style="right: 5px; left: auto;">
					<div class="gbmc">
						<table id="gbmpal" style="width: 270px !important;" >
							<tbody>
								<tr>									
									<%
										//UsuarioService usuarioService = (UsuarioService) ServiceLocator.getInstance().getService(UsuarioService.class, null);	
										boolean usuarioIsAd = false;
										
										if (usuarioAux != null){
											
											usuarioIsAd = (usuarioAux.getLdap() != null && usuarioAux.getLdap().equalsIgnoreCase("s")) ? true : false;
										
										}
																									
										//boolean usuarioIsAd = usuarioService.usuarioIsAD(WebUtil.getUsuario(request));		
										
										String parametroCITSmart = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.METODO_AUTENTICACAO_Pasta, "1");
										
										if(parametroCITSmart != null && parametroCITSmart.trim().equalsIgnoreCase("1")){		
											if (!usuarioIsAd){
									%>		
												<td class="gbmpala">		
													<a 	href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/alterarSenha/alterarSenha.load" class="gbqfbb"><i18n:message key="alterarSenha.alterarSenha"/></a>
												</td>				
												<%				
											}
											
										} 
									%>									
									<td class="gbmpalb"><a id="gbg7" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/login/login.load?logout=yes" class="gbqfbb"><i18n:message key="citcorpore.comum.sair"/></a></td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</li>
		</ol>
	</div>
	
	<div class="clear"></div>
</div>
<!-- Fim Panel-Header | Topo principal -->