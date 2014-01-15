<%@page import="br.com.citframework.util.Constantes"%>
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
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
	<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
	<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
	<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
	<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
	<title>CITSMart</title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/menu_vertical.jsp"%>
	<%@include file="/include/footer.jsp"%>
	<%@include file="/include/menu_horizontal.jsp"%>
	<%@include file="/include/welcome.jsp"%>
	<style type="text/css">
		#result li ul {
			margin:10px; 
		}
	</style>
</head>
<body>	
	<div id="wrapper">
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<div id="result" style="letter-spacing: 0px;"></div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
</body>
</html>
