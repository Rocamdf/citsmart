<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");	
%>
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<script type="text/javascript" src="../js/alterarSenha2.js"></script>
<script>
    var popup;
    addEvent(window, "load", load, false);
    function load(){		
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }
	
	function validar(){
	var senha = document.getElementById("senha").value;
     var senha2 = document.getElementById("senhaNovamente").value;
		if (senha == senha2){
			document.form.fireEvent("alterarSenha");
		}
		else{
			alert(i18n_message("usuario.senhaDiferente"));
			document.getElementById("senha").value = "";
			document.getElementById("senha_novamente").value = "";
			document.getElementById("senha").focus();
		}
	}		
	
	

</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

<%}%>
</head>
<body>	
<div id="wrapper">
	<%if (iframe == null) {%>
	<%@include file="/include/menu_vertical.jsp"%>
	<%}%>

<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>
					
		<div class="flat_area grid_16">
				<h2><i18n:message key="alterarSenha.alterarSenha"/></h2>						
		</div>
  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><i18n:message key="alterarSenha.alterarSenha"/></a>
				</li>
			</ul>				
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' 
							action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/usuario/usuario'>
								<div class="columns clearfix">
									<input type='hidden' name='idUsuario' id='idUsuario'/> 
									<input type='hidden' name='idEmpresa' id='idEmpresa'  />
									<input type='hidden' name='idEmpregado' id='idEmpregado' />
									<input type='hidden' name='dataInicio' id='dataInicio' />
									<input type='hidden' name='dataFim' id='dataFim' />
									<input type='hidden' name='status' id='status'  />
									<div class="columns clearfix">
									<div class="col_25" >
										<fieldset>
											<label  class="campoObrigatorio"><i18n:message key="alterarSenha.novaSenha"/></label>
											<div>
												<input id="senha" type="password" name="senha"  maxlength="20" class="Valid[Required] Description[alterarSenha.novaSenha]" />
											</div>
										</fieldset>
										</div>
										<div class="col_25" >
										<fieldset>
											<label  class="campoObrigatorio"><i18n:message key="alterarSenha.senhaNovamente"/></label>
											<div>
												<input id="senhaNovamente" type="password" name="senha_novamente" maxlength="20" class="Valid[Required] Description[alterarSenha.senhaNovamente]" />
											</div>
										</fieldset>
									</div>	
									</div>
			                   </div>
			                   <div>
								<button type='button' name='btnGravar' class="light" onclick='validar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
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
</div>
		<%@include file="/include/footer.jsp"%>
</body>
<script>
    var popup;
    addEvent(window, "load", load, false);
    function load(){		
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }
	
	function validar(){
	var senha = document.getElementById("senha").value;
     var senha2 = document.getElementById("senhaNovamente").value;
		if (senha == senha2){
			document.form.fireEvent("alterarSenha");
		}
		else{
			alert(i18n_message("usuario.senhaDiferente"));
			document.getElementById("senha").value = "";
			document.getElementById("senha_novamente").value = "";
			document.getElementById("senha").focus();
		}
	}		
</script>
</html>
