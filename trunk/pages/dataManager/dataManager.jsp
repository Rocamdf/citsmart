<%@taglib uri="/tags/cit" prefix="cit"%>	
<%@taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
		    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
		%>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
		
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
		<script type="text/javascript">
			
			function abrirPopup(id, text){
				document.getElementById('descObjetoNegocio').innerHTML = text;
				document.form.idObjetoNegocio.value = id;
				document.form2.idObjetoNegocio.value = id;
				document.getElementById('divExport').innerHTML = '';
				$("#POPUP_EXPORTAR").dialog({
					autoOpen : false,
					width : 800,
					height : 400,
					modal : true,
		            buttons: {
		                "<i18n:message key="dataManager.processsar"/>": function() {
		                	JANELA_AGUARDE_MENU.show();
		                	document.form2.fireEvent('exportar');
		                },
		                "<i18n:message key="dataManager.cancelar"/>": function() {
		                    $( this ).dialog( "close" );
		                }
		            }
				});
				$('#POPUP_EXPORTAR').dialog('open');
				mostraExport();
			}
			function exportarTudo(){
				if (!confirm('<i18n:message key="dataManager.exportTudoConfirm"/>')){
					return;
				}
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent('exportarTudo');
			}
			function mostraExport(){
				document.getElementById('divExport').innerHTML = '<i18n:message key="citcorpore.comum.carregando"/>...';
				document.form2.fireEvent('trataExport');
			}
			
			function importar(){
				
				document.form.fireEvent('importar');
			}
			
			function mostraImport(){
				$("#POPUP_IMPORTAR").dialog({
					title: 'Importar',
					autoOpen : false,
					width : 700,
					height : 320,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$("#btnFechaImportacoes").click(function(){
					$('#POPUP_IMPORTAR').dialog('close');	 
				});
				
				$( "#POPUP_IMPORTAR" ).dialog( 'open' );
				uploadAnexos.refresh();
			}
			function getFile(pathFile, fileName){
				window.location.href = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/baixar.getFile?file=' + pathFile + '&fileName=' + fileName;
			}
			function preparaUpload(){
				var btnUpload=$('#ajxiupload2');
				var status=$('#status');
				
				new AjaxUpload(btnUpload, {
					action: '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/uploadAjax/uploadAjax.load',
					name: 'file_uploadAnexos',
					onSubmit: function(file, ext){
						status.text('Uploading...');
					},
					onComplete: function(file, response){
						//On completion clear the status
						status.text('');
						//Add uploaded file to list
						//if(response==="success"){
							$('<li></li>').appendTo('#files').html('<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/documents.png" alt="" /><br />' + file).addClass('success');
						//} else{
						//	$('<li></li>').appendTo('#files').text(file).addClass('error');
						//}
					}
				});
			}
			function limpar_upload(){
				document.getElementById('divShowFiles').innerHTML = '';
				document.getElementById('divShowFiles').innerHTML = '<ul id="files" ></ul>';
				document.form.fireEvent('limparUpload');
			}
			function validaExcl(obj){
				if (obj.checked){
					var msg = '<i18n:message key="dataManager.exclusaoregs"/>';
					alert(msg);
				}
			}
			function exportarTudoSql(){
				if (!confirm('<i18n:message key="dataManager.exportTudoConfirm"/>')){
					return;
				}
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent('exportarTudoSql');
			}
			
			function carregaMetaDados(){
				if (!confirm('<i18n:message key="dataBaseMetaDados.carregaTabelas"/>')){
					return;
				}
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent('carregaMetaDados');
			}
			
		</script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix" style="height: 100% !important;">
				<div style='width: 100%'>
					<%@include file="/include/menu_horizontal.jsp"%>
					<div class="flat_area grid_16">
						<h2>
							<i18n:message key="dataManager.titulo" />
						</h2>
						<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dataManager/dataManager'>
							<input type='hidden' name='idObjetoNegocio' /> 
							<div>
								<table>
									<tr>
										<td>
											<button onclick='mostraImport()' type='button'><i18n:message key="dataManager.import"/></button>
										</td>
										<td>
											<button onclick='exportarTudo()' type='button'><i18n:message key="dataManager.exportTudo"/></button>
										</td>
										<td>
											<button onclick='exportarTudoSql()' type='button'><i18n:message key="dataManager.exportTudoSql"/></button>
										</td>
										<td>
											<button onclick='carregaMetaDados()' type='button'><i18n:message key="carregar.meta_dados"/></button>
										</td>										
									</tr>
								</table>
								<div id="infoDiv" style="font-weight: bold;"><i18n:message key="dataManager.info"/></div>
								<br>
								<ul id="tt" class="easyui-tree" data-options="url:'<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dataManagerObjects/dataManagerObjects.load',animate: true, onDblClick: function(node){
											abrirPopup(node.id, node.text);
										}">
						        </ul>
							</div>
						</form>
					</div>
				</div>
				<!-- Pop de Exportação -->
				<div id="POPUP_EXPORTAR" style='display:none;' title="<i18n:message key="dataManager.objetoNegocio"/>">
					<form name='form2' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dataManager/dataManager'>
						<input type='hidden' name='idObjetoNegocio' id='idObjetoNegocio2' /> 			
						<div id='descObjetoNegocio'></div>
						<div id='divExport'></div>
					</form>
				</div>
				<!-- Pop de Importação -->
				<div id="POPUP_IMPORTAR" style='display:none'>		
					<form name="formUpload" method="post" enctype="multipart/form-data">
						<cit:uploadControl style="height:100px;width:100%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>			
		               	<button name="btnImportarOK" type='button' onclick='importar()'>Importar Arquivo(s)</button>
						<button id='btnFechaImportacoes' name='btnFechaImportacoes' type="button">Fechar</button>
					</form>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>
