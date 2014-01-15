<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "✰">
<html>
<head>
	<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
			
			String iframe = "";
		    iframe = request.getParameter("iframe");
		%>
	<%@include file="/include/security/security.jsp"%>
	<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
	<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
	<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
	<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
	<!--[if gt IE 8]><!-->
	<!--<![endif]-->
	<title>CIT Corpore</title>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	
	<style type="text/css">
	.table {
		border-left: 1px solid #ddd;
	}
	
	.table th {
		border: 1px solid #ddd;
		padding: 4px 10px;
		border-left: none;
		background: #eee;
	}
	
	.table td {
		border: 1px solid #ddd;
		padding: 4px 10px;
		border-top: none;
		border-left: none;
		
	}
	#tblip{
		width: 90% !important;
		margin-left: -20px;
	}
	#tblip .marcaTodos{
		display: block;
		float: left;
		width: 150px;
	}
	#tblip .pesquisarNetMap{
		display: block;
		float: right;
		width: 150px;
	}
	#tblip .quantIP{
		display: block;
		float: left;
		width: 350px;
	}	
	#totalIP{
		width: 59% !important;
		margin-left: -20px;	
	}
	input.btn{
		background-color: #A5D279 !important;
    	background-image: linear-gradient(to bottom, #B4D990, #8EC657) !important;
    	background-repeat: repeat-x !important;
    	color: #FFFFFF !important;
    	}
		
	</style>
	<script>

	function netMapManual(){
		JANELA_AGUARDE_MENU.show();
		$('#loading_overlay').show();
		document.form.fireEvent("netMapManual");

	}
	
	function selecionaItemConfiguracao(){
		JANELA_AGUARDE_MENU.show();
		$('#loading_overlay').show();
		document.form.fireEvent("selecionaItemConfiguracao");

	}

</script>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper">
		<%
	   	 	if (iframe == null) {
		%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%
		    }
		%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
		<%
	   	 	if (iframe == null) {
		%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%
		    }
		%>
		
		<div class="flat_area grid_16">
		<h2><i18n:message key="inventario.invetario" /></h2>
		</div>
		
		<div class="box grid_16 tabs">
		<ul class="tab_header clearfix">
			<li><a href="#tabs-1"><i18n:message key="inventario.cadastro" />
			</a></li>
		
		</ul>
		<a href="#" class="toggle">&nbsp;</a>
		<div class="toggle_container">
			<div id="tabs-1" class="block">
				<div class="section" style="width: ">
					<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/inventarioNew/inventarioNew'>
						<div class="columns clearfix">
							<h2><i18n:message key="inventario.itensConfiguracao" /></h2>
							<div class="columns clearfix">
								<div>
									<input  type='button' id='pesquisarNetMap' class='btn btn-primary' name='pesquisarNetMap' value='<i18n:message key="inventario.pesquisarItensConfiguracao" />' onclick='netMapManual();'>
									<div id="ipMac" ></div>
							   </div>
							</div>
						</div>					
					</form>
				</div>
			</div>
		<!-- ## FIM - AREA DA APLICACAO ## --></div>
		</div>
		</div>
		<!-- Fim da Pagina de Conteudo --></div>
		<%@include file="/include/footer.jsp"%>
	</body>
	
</html>