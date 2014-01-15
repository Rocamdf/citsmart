<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.FiltroSegurancaCITSmart" %>
<%@include file="/include/header.jsp"%>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/isotope/jquery.isotope.min.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/fancybox/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/custom/gallery.js"></script>
<% if(!FiltroSegurancaCITSmart.getHaVersoesSemValidacao()){ %>     
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/dwr/engine.js"></script>
<%} %>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/dwr/util.js"></script>
	<script>
	
		function tratarEnter (field, event) {
			var keyCode = event.keyCode ? event.keyCode : event.which ? event.which : event.charCode;
			if (keyCode == 13) {
				var i;
				for (i = 0; i < field.form.elements.length; i++)
					if (field == field.form.elements[i])
						break;
				i = (i + 1) % field.form.elements.length;
				field.form.elements[i].focus();
				return false;
			} 
			else
			return true;
		}
		/*
		* Motivo: Corrigindo erros de scripts
		* Autor: flavio.santana
		* Data/Hora: 04/11/2013 16:19
		*/
		function resize_iframe(){}
		
		if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
			 document.documentElement.className += " " + "ie10";
		}
	</script>
		 
	<div id="loading_overlay">
		<div class="loading_message round_bottom">
			<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/loading.gif" alt="aguarde..." />
		</div>
	</div>
	
<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/bootstrap/js/bootstrap.min.js"></script>

<%@include file="/pages/ctrlAsterisk/ctrlAsterisk.jsp" %>
	