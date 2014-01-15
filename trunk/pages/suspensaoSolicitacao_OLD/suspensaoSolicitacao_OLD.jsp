<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN""http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));
	
%>
    <%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

</head>

<body>	

<script>
	
	fechar = function(){
		parent.fecharModalSuspensao();
	}
	
	gravar = function() {
		if (confirm(i18n_message("gerenciaservico.suspensaosolicitacao.confirm.suspensao"))) 
			document.form.save();
	}	
	
</script>

<div id="wrapper">
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
					
		<div class="flat_area grid_16">
				<h2><i18n:message key="solicitacaoServico.solicitacao"/>&nbsp;Nº&nbsp;<%=idSolicitacaoServico%></h2>						
		</div>
  <div class="box grid_16 tabs">

	 <div class="toggle_container">
						<div class="">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/suspensaoSolicitacao/suspensaoSolicitacao'>
								<div class="columns clearfix">
									<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/> 

									<div class="col_100">
										<fieldset>
											<label style="cursor: pointer;"><i18n:message key="citcorpore.comum.justificativa"/>:*</label>
											<div> 
												<select name='idJustificativa' class="Valid[Required] Description[Justificativa]">
												</select>					
											</div>
										</fieldset>
									</div>	
									<div class="col_100">
										<fieldset style="FONT-SIZE: xx-small;">
											<label style="cursor: pointer;"><i18n:message key="gerenciaservico.mudarsla.complementojustificativa"/></label>
											<div> 
												<textarea id="complementoJustificativa" name="complementoJustificativa" cols='70' rows='3'></textarea>												
											</div>
										</fieldset>										
									</div>	
								</div>	
									
								<div>
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name='btnCancelar' class="light" onclick='fechar();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/bended_arrow_left.png">
										<span><i18n:message key="citcorpore.comum.cancelar" />
										</span>
									</button>
							    </div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->							
	 </div>
  </div>				
 </div>
<!-- Fim da Pagina de Conteudo -->

</body>

</html>
