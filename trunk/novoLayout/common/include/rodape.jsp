<%@page import="br.com.centralit.citcorpore.bean.EmpresaDTO"%>
<%@page import="br.com.centralit.citcorpore.versao.Versao" %>
<%@page import="br.com.citframework.util.Constantes" %>

<div class="clearfix"></div>
<!-- Inicio Rodape  -->
<% if(request.getParameter("iframe") == null) { %>
<div id="footer" class="hidden-print">
	
	<!--  Copyright Line -->
	<div class="copy">© 2012 - 2013 - <a target="_blank" href="http://www.citsmart.com.br"><i18n:message key="citcorpore.comum.title"/></a> - <i18n:message key="citcorpore.todosDireitosReservados"/>  - <i18n:message key="login.versao"/> <b><%=Versao.getDataAndVersao()%> </div>
	<!--  End Copyright Line -->

</div>
<% } %>


<%@include file="/novoLayout/common/include/libRodape.jsp" %>
<!-- Fim Rodape  -->

<script type="text/javascript">
	$(document).ready(function() {		
		<% if(request.getAttribute("Script") != null) { out.print(request.getAttribute("Script").toString()); }%>		
	});
	function buscaHistoricoPorVersao() {
		document.formSobre.fireEvent("buscaHistoricoPorVersao");
	}
</script>
		
<% if(request.getSession().getAttribute(Constantes.getValue("USUARIO_SESSAO") + "_CITCORPORE") != null){ %>	
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/dwr/engine.js"></script>
<%} %>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/dwr/util.js"></script>
<%@include file="/pages/ctrlAsterisk/ctrlAsterisk.jsp" %>