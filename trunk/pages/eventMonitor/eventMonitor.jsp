<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<html>
<head>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script>
var contador = 60;
addEvent(window, "load", load, false);
function load() {
	window.setTimeout('geraInformacoesMonitoramento()',1000);
}
function mostraHostsGrupo(id, text){
	document.form.idGrupoRecurso.value = id;
	document.form.nomeGrupoRecurso.value = text; 
	document.getElementById('divDetalhamento').innerHTML = '<%=UtilI18N.internacionaliza(request, "citcorpore.comum.aguardecarregando")%>';
	document.form.fireEvent('mostraHostsGrupo');
}
function geraInformacoesMonitoramento(){
	if (contador < 0){
		contador = 61;
		document.form.fireEvent('geraInformacoesMonitoramento');
		document.getElementById('divDetalhamento').innerHTML = '<%=UtilI18N.internacionaliza(request, "citcorpore.comum.aguardecarregando")%>';
		document.form.fireEvent('mostraHostsGrupo');
	}else{
		document.getElementById('timeRefresh').innerHTML = contador;
	}
	contador--;
	window.setTimeout('geraInformacoesMonitoramento()',1000);
}
</script>
</head>
<body>
<form name='form' id='form' action="<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/eventMonitor/eventMonitor" method="post">
	<input type='hidden' name='idGrupoRecurso'/>
	<input type='hidden' name='nomeGrupoRecurso'/>
	<table width='100%'>
		<tr>
			<td>
				<img src='<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/logo/logo.png' border='0'/>
			</td>
			<td style='border:1px solid black; background-color: lightgray'>
				<div id='timeRefresh'>
				</div>
			</td>
			<!-- 
			<td width="25%">
				<table class="Resume_light_table" border='1'>
					<tbody><tr class="Resume_light_header" style="white-space:nowrap;">
						<td>Hosts</td>
						<td>Up</td>
						<td>Down</td>
						<td>Unreachable</td>
						<td>Pending</td>
					</tr>
					<tr>
						<td><div id="hosts" style="background:white;text-align:center"><a href="main.php?p=20102&amp;o=h&amp;search=">0</a></div></td>
						<td style="background:#19EE11"><div id="host_up" style="background:white;text-align:center"><a href="main.php?p=20102&amp;o=h_up&amp;search=">0</a></div></td>
						<td style="background:#F91E05"><div id="host_down" style="background:white;text-align:center"><a href="main.php?p=20102&amp;o=h_down&amp;search=">0</a></div></td>
						<td style="background:#82CFD8"><div id="host_unreachable" style="background:white;text-align:center"><a href="main.php?p=20102&amp;o=h_unreachable&amp;search=">0</a></div></td>
						<td style="background:#2AD1D4"><div id="host_pending" style="background:white;text-align:center"><a href="main.php?p=20102&amp;o=h_pending&amp;search=">0</a></div></td>
					</tr>
					</tbody>
				</table>
			</td>
			<td width="25%">
				<table class="Resume_light_table" border='1'>
					<tbody><tr class="Resume_light_header" style="white-space:nowrap;">
						<td>Services</td>
						<td>Ok</td>
						<td>Warning</td>
						<td>Critical</td>
						<td>Unknown</td>		
						<td>Pending</td>
					</tr>
					<tr>
						<td><div id="service_total" style="background:white;text-align:center"><a href="main.php?p=20201&amp;o=svc&amp;search=">0</a></div></td>
						<td style="background:#13EB3A"><div id="service_ok" style="background:white;text-align:center"><a href="main.php?p=20201&amp;o=svc_ok&amp;search=">0</a></div></td>
						<td style="background:#F8C706"><div id="service_warning" style="background:white;text-align:center"><a href="main.php?p=20201&amp;o=svc_warning&amp;search=">0/0</a></div></td>
						<td style="background:#F91D05"><div id="service_critical" style="background:white;text-align:center"><a href="main.php?p=20201&amp;o=svc_critical&amp;search=">0/0</a></div></td>
						<td style="background:#DCDADA"><div id="service_unknown" style="background:white;text-align:center"><a href="main.php?p=20201&amp;o=svc_unknown&amp;search=">0/0</a></div></td>
						<td style="background:#2AD1D4"><div id="service_pending" style="background:white;text-align:center"><a href="main.php?p=20202&amp;o=svcpb&amp;search=">0</a></div></td>
					</tr>
					</tbody>
				</table>			
			</td>
			 -->
			<td>
				<div id='divServicesCritical' style='width: 100%;border:1px solid black; height: 60px; overflow: auto'>
				</div>
			</td>
		</tr>
	</table>
	<table width='100%'>
		<tr>
			<td>
				<div id='divGrupos' style='width: 100%;'>
					
				</div>
			</td>
		</tr>		
		<tr>
			<td>
				<div id='divDetalhamento' style='width: 100%;'>
					
				</div>
			</td>
		</tr>		
	</table>
</form>
</body>
</html>