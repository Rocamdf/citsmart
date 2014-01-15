<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script>
var intervalo;
function abrirPopup(id, text){
	if (id != null){
		var init = id.charAt(0);
		var idConsulta = id.substring(1,id.length);
		if (init == 'C'){ //Cruzado
			parent.document.getElementById('direita').src = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/geraInfoPivotTable/geraInfoPivotTable.load?idConsulta=' + idConsulta;
		}
		if (init == 'T'){ //Template
			parent.document.getElementById('direita').src = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/geraTemplateReport/geraTemplateReport.load?idConsulta=' + idConsulta;
		}
	}
}
</script>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
		
		<script type="text/javascript"	src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.easyui.min.js"></script>
		
		<link rel="stylesheet" type="text/css" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/css/jquery-ui-1.8.21.custom.css" />			
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/main.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/theme_base.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/buttons.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/ie.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/themes/gray/easyui.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/themes/icon.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery-easy.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.datepicker.css">		
		
		<style>
			#ajxiupload2, #limpar{
				margin:30px 20px; padding:15px;
				font-weight:bold; font-size:1.3em;
				font-family:Arial, Helvetica, sans-serif;
				text-align:center;
				background:#f2f2f2;
				color:#3366cc;
				border:1px solid #ccc;
				width:90px;
				cursor:pointer !important;
				-moz-border-radius:5px; -webkit-border-radius:5px;
			}
			.darkbg{
				background:#ddd !important;
			}
			#status{
				font-family:Arial; padding:5px;
			}
			ul#files{ list-style:none; padding:0; margin:0; }
			ul#files li{ padding:10px; margin-bottom:2px; width:200px; float:left; margin-right:10px;}
			ul#files li img{ max-width:180px; max-height:150px; }
			.success{ background:#99f099; border:1px solid #339933; }
			.error{ background:#f0c6c3; border:1px solid #cc6622; }
			.table {
				border-left:1px solid #ddd;
			}
			.table th {
				border:1px solid #ddd;
				padding:4px 10px;
				border-left:none;
				background:#eee;
			}
			.table td {
				border:1px solid #ddd !important;
				padding:4px 10px !important;
				border-top:none !important;
				border-left:none !important;
			}
			div#main_container {
				margin: 10px 10px 10px 10px;
			}
		</style>		
</head>

<body>
	<body>
		<div id="wrapper">
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix" style="height: 100% !important;">
				<div style='width: 100%'>
					<div class="flat_area grid_16">
						<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/listagemConsultas/listagemConsultas'>
							<input type='hidden' name='idObjetoNegocio' /> 
							<div>
								<div id="infoDiv" style="font-weight: bold;">Consultas/Relatórios</div>
								<br>
								<ul id="tt" class="easyui-tree" data-options="url:'<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/listagemConsultasObjects/listagemConsultasObjects.load',animate: true, onClick: function(node){
											abrirPopup(node.id, node.text);
										}">
						        </ul>
							</div>
						</form>
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</body>

</html>
