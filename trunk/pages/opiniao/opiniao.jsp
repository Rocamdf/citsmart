<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html public "">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
%>
<%@include file="/include/security/security.jsp" %>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
	<title><i18n:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script type="text/javascript">
function fecharPopup(){
	window.parent.$('#popupCadastroRapido').dialog('close');
}
</script>
</head>
<body>	

	<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/opiniao/opiniao'>
										 		
		<div class="columns clearfix">
		 <%	if(request.getParameter("idSolicitacao") != null)	{		%>
		 <input type='hidden' name='idSolicitacao' id='idSolicitacao' value='<%= request.getParameter("idSolicitacao") %>'/>	
		 <% } %>

			
			<div class="col_50">				
				<fieldset>
					<label><i18n:message key="citcorpore.comum.tipo" /></label>
						<div>
						  	<input type="radio" value="Elogio" class="Valid[Required] Description[Tipo]" id="tipo" name="tipo"><i18n:message key="portal.elogio" />
						  	<input type="radio" value="Queixa" class="Valid[Required] Description[Tipo]" id="tipo" name="tipo"><i18n:message key="portal.queixa"/> 
						</div>
				</fieldset>
			</div>
		</div>	
		<div class="columns clearfix">          
		  <div class="col_100">
				<fieldset>
					<label><span class="campoEsquerda"><i18n:message key="citcorpore.comum.observacoes" /></span></label>
						<div>
						  <textarea name="observacoes" id="observacoes" class="Valid[Required] Description[Observações]" maxlength="200" cols='70' rows='5'></textarea>
						</div>
				</fieldset>
			</div>		      	
		</div>
		         
		<br><br>
						            
	<button type='button' name='btnGravar' class="light"  onclick='document.form.save();'>
		<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
		<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i18n:message key="portal.gravarDados"/></span>
	</button>
	<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
		<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
			<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i18n:message key="portal.limparDados"/></span>
		</button>						         
	</form>

</body>
</html>
