<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ page import="br.com.centralit.citcorpore.free.Free" %>
<%
	String retorno = br.com.citframework.util.Constantes
			.getValue("SERVER_ADDRESS")
			+ br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")
			+ "/pages/index/index.load";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>

<script
	src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.js"
	type="text/javascript" language="javascript"></script>
<script
	src='<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.MetaData.js'
	type="text/javascript" language="javascript"></script>
	
	
<link
	href='<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.rating.css'
	type="text/css" rel="stylesheet" />
	
		
    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/layout-default-latest.css"/>

    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.all.css"/>

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

#comentario {
	display: block;
	box-shadow: 0 0 2px 0 #555555 inset;
	-moz-box-sizing: border-box;
	background: none repeat scroll 0 0 rgba(0, 0, 0, 0.05);
	border: 0 none;
	box-shadow: 0 0 2px 0 #DDDDDD inset;
	margin-top: 3px;
	width: 541px;
	height: 137px;
	width: 100% !important;
}

.tv {
	z-index: 10;
	overflow: auto;
	margin-left: 5px;
	margin-bottom: 5px;
	width: 15%;
	height: 51%;
	position: auto;
	display: block;
	-moz-background-clip: padding; /* Firefox 3.6 */
	-webkit-background-clip: padding; /* Safari 4? Chrome 6? */
	-moz-box-shadow: 3px 3px 5px 6px #ccc;
	-webkit-box-shadow: 3px 3px 5px 6px #ccc;
	top: 8px;
	left: 0px;
	float: left;
	bottom: 8px;
	border: 0px solid rgba(0, 0, 0, 0.05);
	box-shadow: 0 0 0px rgba(0, 0, 0, 0.3)
}

div.pastas {
	background: none repeat scroll 0 0 padding-box #F7F7F7;
	padding: 0px 10px 5px;
	z-index: 10;
	overflow: auto;
	margin-left: 2px;
	margin-bottom: 5px;
	width: 95%;
	height: 98%;
	position: auto;
	display: block;
	-moz-background-clip: padding; /* Firefox 3.6 */
	-webkit-background-clip: padding; /* Safari 4? Chrome 6? */
	-moz-box-shadow: 3px 3px 5px 6px #ccc;
	-webkit-box-shadow: 3px 3px 5px 6px #ccc;
	top: 8px;
	left: 0px;
	float: left;
	bottom: 8px;
	border: 0px solid rgba(0, 0, 0, 0.05);
	box-shadow: 0 0 20px rgba(0, 0, 0, 0.3)
}

div.baseconhecimento {
	background: none repeat scroll 0 0 padding-box #F7F7F7;
	border: 0px solid rgba(0, 0, 0, 0.05);
	box-shadow: 0 0 8px rgba(0, 0, 0, 0.3);
	display: block;
	/* margin: 10px 10px 10px 17%; */
	/* padding: 20px 10px 5px; */
	position: relative;
	width: auto;
	/* height: 98%!important; */
	min-height: 98%!important;
	margin-left: 3px!important;
	margin-right: 3px!important;

}

div.principal {
 	padding-bottom: 50px; 
	height: 180%;
 /* 	margin-top: 5%;  */
}

.container_16 {
	width: 92%;
	margin-left: 4%;
	margin-right: 4%;
	letter-spacing: -4px;
}

#tituloconhecimento.text {
	font-size: 20px;
	box-shadow: 0 0 0 0 #DDDDDD inset;
	font-weight: bold;
	font-family: sans-serif;
	border: 0px;
	background: none;
}

#conhecimento.section {
	padding-top: 0px;
}
#conteudo{
font-family: sans-serif;
font-size: small;
/* font-variant: inherit; */

}
.manipulaDiv{
  color: #777C86;
    cursor: pointer;
    display: block;
    font-size: 14px;
    font-weight: bold;
    margin-top: 15px;
    margin-left: 2px;
    

}
.manipulaDiv:HOVER{
  color: #A6CE39;
    cursor: pointer;
    display: block;
    font-size: 14px;
    font-weight: bold;
    margin-top: 15px;

}

/*  .ui-layout-east ,
   			 .ui-layout-east .ui-layout-content {
       			padding:        0;
       			overflow:       hidden;
    		}
    		 .ui-layout-center{
    			height: 100% !important;
    			width: 100% !important;
    		} 
    		.ui-layout-center ,
   			 .ui-layout-east ,
    		.ui-layout-east .ui-layout-content  {
		        padding:        0 ;
		        overflow:       auto ;
		       
    		} */

</style>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");
	if (iframe == null) {
		iframe = "false";
	}
	String fecharpesquisa = "";
	fecharpesquisa = request.getParameter("fecharpesquisa");
	if (fecharpesquisa == null) {
		fecharpesquisa = "false";
	}

	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil
	.getValorParametroCitSmartHashMap(
			Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
%>


<%@include file="/include/security/security.jsp"%>
<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>



 <!-- Imports do layout -->
		

		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js?nocache=<%=new java.util.Date().toString()%>"></script>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/dtree.js"></script>
		
		 <%-- 
		 	CAUSA CONFLITO COM O dtree.js
		 <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/jquery-latest.js"></script> --%>
   		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/jquery-ui-latest.js"></script>
    	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/jquery.layout-latest.js"></script>
    	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/debug.js"></script> 
<%if (iframe != null && iframe.equalsIgnoreCase("true")) {%> 	
	<style type="text/css">.ui-layout-center {top: 0px!important;}</style>
<%}else{ %>
	<style type="text/css">.ui-layout-center {top: 137px!important;}</style>
<%} %>
<script type="text/javascript">
		    var popup;
		    var popup4;
		    var popup5;
		    var popup6;
		    var controle = 0;
		    var contConhecimentoRelacionado = 0;
		    var countComentario = 0;
		    
		    addEvent(window, "load", load, false);
		    
		    function load(){	
		    	JANELA_AGUARDE_MENU.show()
		    	popup = new PopupManager(1800, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		    	popup4 = new PopupManager(1100, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		    	popup5 = new PopupManager(1100, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		    	popup6 = new PopupManager(1100, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		    	
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
				
				$("#POPUP_USUARIO").dialog({
					title: i18n_message("citcorpore.comum.pesquisarUsuario"),
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true,
					show: "fade",
					hide: "fade"
				});	
				
				$("#POPUP_HISTORICOVERSAO").dialog({
					title: i18n_message("pesquisaBaseConhecimento.historicoVersoes"),
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true,
					show: "fade",
					hide: "fade"
				});	
				
				$("#POPUP_ALTERACAO").dialog({
					title: i18n_message("pesquisaBaseConhecimento.historicoAlteracao"),
					autoOpen : false,
					width : 1000,
					height : 400,
					modal : true,
					show: "fade",
					hide: "fade"
				});	
				
				$("#POPUP_ATIVOS").dialog({
					autoOpen : false,
					width : 1005,
					height : 565,
					modal : true
				});
				
				<%if (fecharpesquisa != null
					&& fecharpesquisa.equalsIgnoreCase("true")) {%>
				document.getElementById('divpesquisa').style.display = 'none';
				<%}%>
			}
		    
			
			$(document).ready(
				function() {
					$('#divMostraUpload_uploadAnexos').closest(
							'#divMostraUpload_uploadAnexos').remove();
				}
			);

			function tituloBaseConhecimentoView(idItem) {
				
				document.form2.idBaseConhecimento.value = idItem;
				document.form2.fireEvent("restore");
				
				$('#conhecimento').show();
			}
			
			function verificarPermissaoDeAcesso(idPasta,idBaseConhecimento) {
				
				document.form2.idPasta.value = idPasta;
				document.form2.idBaseConhecimento.value = idBaseConhecimento;
				document.form2.fireEvent("verificarPermissaoDeAcesso");
				
			}
			

			function corTitulo(idItem) {
				var browser = document.getElementById("browser");
				var lista = browser.getElementsByTagName("a");
				for ( var i = 0; i < lista.length; i++) {
					if (lista[i] == null) {
						continue;
					}
					if (lista[i].id != "idTitulo" + idItem) {
						lista[i].style.backgroundColor = "#FFF";
					} else {
						lista[i].style.backgroundColor = "#B0C4DE";
						lista[i].style.color = "#000";
					}
				}

			}

			function executeJS() {
				if (document.getElementById('jsOnDemand')) {
					document.getElementsByTagName("head")[0]
							.removeChild(document.getElementById('jsOnDemand'));
				}
				
				var fileref = document.createElement('script');
				fileref.setAttribute("id", "jsOnDemand");
				fileref.setAttribute("type", "text/javascript");
				fileref.setAttribute("src",'<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.rating.js');	
				
				document.getElementsByTagName("head")[0].appendChild(fileref);
				
				try{
					$('input[type=radio].star').rating();
				}catch(e){}
			}
			
			function createDiv(nota){
				var str = '';
				if (nota == '1.0'){
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media1_' + controle + '" checked="checked" value="1.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media1_' + controle + '" value="1.0" disabled="disabled"/>';
				}
				if (nota == '2.0'){
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media2_' + controle + '" checked="checked" value="2.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media2_' + controle + '" value="2.0" disabled="disabled"/>';
				}
				if (nota == '3.0'){
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media3_' + controle + '" checked="checked" value="3.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media3_' + controle + '" value="3.0" disabled="disabled"/>';
				}
				if (nota == '4.0'){
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media4_' + controle + '" checked="checked" value="4.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media4_' + controle + '" value="4.0" disabled="disabled"/>';
				}
				if (nota == '5.0'){
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media5_' + controle + '" checked="checked" value="5.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media5_' + controle + '" value="5.0" disabled="disabled"/>';
				}

				document.getElementById('divMostraNota').innerHTML = str;				
			}

			function getStringStarsClean(){
				var str = '';
				str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media1_' + controle + '" value="1.0" disabled="disabled"/>';
				str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media2_' + controle + '" value="2.0" disabled="disabled"/>';
				str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media3_' + controle + '" value="3.0" disabled="disabled"/>';
				str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media4_' + controle + '" value="4.0" disabled="disabled"/>';
				str = str + '<input class="star" type="radio" name="media_' + controle + '" id="media5_' + controle + '" value="5.0" disabled="disabled"/>';
				return str;
			}
			
			function getStringStars(){
				var str = '';
				str = str + '<input id="nota" class="star required" type="radio" name="nota" value="1" title="'+i18n_message("citcorpore.comum.fraco")+'/>';
				str = str + '<input  class="star" type="radio" name="nota" value="2" title="'+i18n_message("citcorpore.comum.regular")+'"/>';
				str = str + '<input  class="star" type="radio" name="nota" value="3" title="'+i18n_message("citcorpore.comum.bom")+'"/>';
				str = str + '<input  class="star" type="radio" name="nota" value="4" title="'+i18n_message("citcorpore.comum.otimo")+'"/>';
				str = str + '<input  class="star" type="radio" name="nota" value="5" title="'+i18n_message("citcorpore.comum.excelente")+'"/>';
				return str;
			}
			
			function tree(id) {
				$(id).treeview();
			}
			
			function gravar() {
				if (document.form.titulo.value == '' ){
					alert(i18n_message("baseConhecimento.validacao.selecionebase"));
					return fecharPopup();}
				else
				document.formPopup.save();
			}
			
			$(function() {
				$("#POPUP_COMENTARIOS").dialog({
					autoOpen : false,
					height : 458,
					width : 560,
					modal : true
				});
			});
			
			$(function() {
				$("#addComentarios").click(function() {
					$("#POPUP_COMENTARIOS").dialog("open");
				});
			});
			
			function fecharPopup(){
					$("#POPUP_COMENTARIOS").dialog("close");
			}
	
			function limpar() {
				document.formPopup.clear();
				document.getElementById("notaEnviarComentario").innerHTML = getStringStarsClean();
				$('input[type=radio].star').rating(); 
				document.getElementById("notaEnviarComentario").innerHTML = getStringStars();
				$('input[type=radio].star').rating(); 
			}
			
			function restoreRow(comentario,nome,email,nota) {
				controle++;
				var tabela = document.getElementById('tabelaComentarios');
				var lastRow = tabela.rows.length;
	
				var row = tabela.insertRow(lastRow);
				countComentario++;
	
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="hidden" id="idComentario' + countComentario + '" name="idComentario"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nome' + countComentario + '" name="nome" value="'+nome+'"/>';
	
				coluna = row.insertCell(1);
				coluna.innerHTML ='<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="email' + countComentario + '" name="email" value="'+email+'"/>';
	
				coluna = row.insertCell(2);
				coluna.innerHTML = '<textarea style="width: 100%; border: 0 none;"  id="comentario' + countComentario + '" name="comentario" >'+comentario+'</textarea>';
				
				coluna = row.insertCell(3);
				var str = '';						    
				if (nota == '1'){
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota1_' + controle + '" checked="checked" value="1.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota1_' + controle + '" value="1.0" disabled="disabled"/>';
				}
				if (nota == '2'){
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota2_' + controle + '" checked="checked" value="2.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota2_' + controle + '" value="2.0" disabled="disabled"/>';
				}
				if (nota == '3'){
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota3_' + controle + '" checked="checked" value="3.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota3_' + controle + '" value="3.0" disabled="disabled"/>';
				}
				if (nota == '4'){
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota4_' + controle + '" checked="checked" value="4.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota4_' + controle + '" value="4.0" disabled="disabled"/>';
				}
				if (nota == '5'){
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota5_' + controle + '" checked="checked" value="5.0" disabled="disabled"/>';
				}else{
					str = str + '<input class="star" type="radio" name="nota_' + controle + '" id="nota5_' + controle + '" value="5.0" disabled="disabled"/>';
				}
				coluna.innerHTML = str;
				executeJS();
			}
	
			var seqSelecionada = '';
			function setRestoreComentario(idComentario, comentario, nome, email, nota, dataInicio) {
				if (seqSelecionada != '') {
					eval('document.form.idComentario' + seqSelecionada + '.value = "' + idComentario + '"');
					eval('document.form.comentario' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + comentario + '\')');
					eval('document.form.nome' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nome + '\')');
					eval('document.form.email' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + email + '\')');
					
					if (ObjectUtils.decodificaEnter(nota) == 1){
						document.getElementById('nota1' + controle + '_' + seqSelecionada).checked = true;
					}
					
					if (ObjectUtils.decodificaEnter(nota) == 2){
						document.getElementById('nota2' + controle + '_' + seqSelecionada).checked = true;
					}
					
					if (ObjectUtils.decodificaEnter(nota) == 3){
						document.getElementById('nota3' + controle + '_' + seqSelecionada).checked = true;
					}
					
					if (ObjectUtils.decodificaEnter(nota) == 4){
						document.getElementById('nota4' + controle + '_' + seqSelecionada).checked = true;
					}
					
					if (ObjectUtils.decodificaEnter(nota) == 5){
						document.getElementById('nota5' + controle + '_' + seqSelecionada).checked = true;
						document.getElementById('nota5' + controle + '_' + seqSelecionada).className = "star";
					}
					
				}
				exibeGrid();
			}

			function removeLinhaTabela(idTabela, rowIndex) {
				if (confirm(i18n_message("baseConhecimento.confirme.excluircomentario"))) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
	
					document.form.comentarioSerializado.value = eval('document.form.idComentario' + rowIndex + '.value');
	
					document.form.fireEvent("excluirAssociacaoComentarioBaseConhecimento");
				}
			}
	
			function deleteAllRows() {
				var tabela = document.getElementById('tabelaComentarios');
				var count = tabela.rows.length;
	
				while (count > 1) {		
					tabela.deleteRow(count - 1);
					count--;
				}
				ocultaGrid();
			}
			
			function exibeGrid() {
				document.getElementById('gridComentario').style.display = 'block';
			}
	
			function ocultaGrid() {
				document.getElementById('gridComentario').style.display = 'none';
			}
			
			function fecharPesquisa(){
				$("#resultPesquisaPai").dialog("close");		
			}
			
			function mostraPesquisaBaseConhecimento(){
				document.getElementById("resultPesquisaPai").style.display="block";
			}
			
			$(function() {
				
				$("#resultPesquisaPai").dialog({
					title : '<i18n:message key="citcorpore.comum.resultadopesquisa"/>',
					autoOpen : false,
					width : 700,
					height : 390,
					modal : true
				});
				
				$('#conhecimento').hide();
			});
			
			pesquisarBaseConhecimento = function(){
				if (document.formPesquisa.nomeUsuarioAutor.value == "") {
					document.formPesquisa.idUsuarioAutorPesquisa.value = "0";
				}
				if (document.formPesquisa.nomeUsuarioAprovador.value == "") {
					document.formPesquisa.idUsuarioAprovadorPesquisa.value = "0";
				}				
				
				$('#conhecimento').hide();
				document.formPesquisa.fireEvent('pesquisaBaseConhecimento');

				
				
/*
			var  auxiliar = document.formPesquisa.termoPesquisa.value;
				for(var i = 1; i <= document.formPesquisa.termoPesquisa.value.length; i++){
					auxiliar = auxiliar.replace(" ","");	
				}
				//document.getElementById("resultPesquisa").innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
				
				
			 	if(auxiliar == ""){	
					
					alert(i18n_message("baseConhecimento.validacao.informetermo"));
					
					//$('#conhecimento').show();
					
					return;
				}else {
					
					$("#resultPesquisaPai").dialog("open");
					document.getElementById("resultPesquisa").innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
					document.formPesquisa.fireEvent('pesquisaBaseConhecimento');			
				}
*/
			};
			
			function fecharBaseConhecimentoView(){
				parent.fechaPopupIframe();
			}
			
			function voltar(){
				verificarAbandonoSistema = false;
				window.location = '<%=retorno%>';
			}
			
			function contadorClicks(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('contadorDeClicks');	
			}
			
			function incidentesAbertosPorBaseConhecimnto(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('mostrarQuantidadeDeIncidentesAbertosPorBaseConhecimento');	
			}
			
			function problemasAbertosPorBaseConhecimnto(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('mostrarQuantidadeDeProblemasAbertosPorBaseConhecimento');	
			}
			function mudancasAbertasPorBaseConhecimnto(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('mostrarQuantidadeDeMudancasAbertasPorBaseConhecimento');	
			}
			function comentariosAbertosPorBaseConhecimnto(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('mostrarQuantidadeDeComentariosAbertosPorBaseConhecimento');	
			}
			function itensConfiguracoesAbertosPorBaseConhecimnto(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('mostrarQuantidadeDeItensConfiguracoesAbertosPorBaseConhecimento');	
			}
			
			
			function gravarIncidentesRequisicao(idBaseConhecimento){
				if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
					alert(i18n_message("baseConhecimento.validacao.selecionebase"));
					return;
				}
				
				var id =  parseInt(idBaseConhecimento);
				
				var pagina = '<%=PAGE_CADADTRO_SOLICITACAOSERVICO%>';
				
				if(pagina == null || pagina==" "){
					document.getElementById('iframeSolicitacaoServico').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServico/solicitacaoServico.load?id=' + id;
				}else{
					document.getElementById('iframeSolicitacaoServico').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?id=' + id;
				}
				
				$("#POPUP_INCIDENTE").dialog("open");
			}
			
			
			
			$(function() {
				$("#POPUP_INCIDENTE").dialog({
					autoOpen : false,
					width : 1500,
					height : 1005,
					modal : true
				});
			});
			
			function fecharSolicitacao(){
				$("#POPUP_INCIDENTE").dialog('close');
			}
			
			function gravarGestaoProblema(idBaseConhecimento){
				if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
					alert(i18n_message("baseConhecimento.validacao.selecionebase"));
					return;
				}
				
				var id =  parseInt(idBaseConhecimento);
				
				document.getElementById('iframeProblema').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/problema/problema.load?idBaseConhecimento=' + id;
				
				$("#POPUP_CADASTROPROBLEMA").dialog("open");				
			}
			
			$(function() {
				$("#POPUP_CADASTROPROBLEMA").dialog({
					autoOpen : false,
					width : 1500,
					height : 1005,
					modal : true
				});
			});
			
			function fecharProblemaAtualizaGrid() {
				$("#POPUP_PROBLEMA").dialog("close");
			}
			
			function gravarGestaoMudanca(idBaseConhecimento){
				if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
					alert(i18n_message("baseConhecimento.validacao.selecionebase"));
					return;
				}
				
				var id =  parseInt(idBaseConhecimento);
				
				document.getElementById('iframeMudanca').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/requisicaoMudanca/requisicaoMudanca.load?idBaseConhecimento=' + id;
				
				$("#POPUP_CADASTROMUDANCA").dialog("open");				
			}
			
			function gravarGestaoLiberacao(idBaseConhecimento){
				if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
					alert(i18n_message("baseConhecimento.validacao.selecionebase"));
					return;
				}
				
				var id =  parseInt(idBaseConhecimento);
				
				document.getElementById('iframeLiberacao').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/requisicaoLiberacao/requisicaoLiberacao.load?idBaseConhecimento=' + id;
				
				$("#POPUP_CADASTROLIBERACAO").dialog("open");				
			}
			
			$(function() {
				$("#POPUP_CADASTROMUDANCA").dialog({
					autoOpen : false,
					width : 1500,
					height : 1005,
					modal : true
				});
				
				$("#POPUP_CADASTROLIBERACAO").dialog({
					autoOpen : false,
					width : 1500,
					height : 1005,
					modal : true
				});
			});
			
			function fecharMudancaAtualizaGrid(){
				$("#POPUP_MUDANCA").dialog('close');
			}
			
			function fecharLiberacao(){
				$("#POPUP_CADASTROLIBERACAO").dialog('close');
			}			
			
			function gravarGestaoItemConfiguracao(idBaseConhecimento){
				if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
					alert(i18n_message("baseConhecimento.validacao.selecionebase"));
					return;
				}
				
				var id =  parseInt(idBaseConhecimento);
				
				document.getElementById('iframeItemConfiguracao').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load?iframe=true&idBaseConhecimento=' + id;
				
				$("#POPUP_ITEMCONFIGURACAO").dialog("open");				
			}
			
			$(function() {
				$("#POPUP_ITEMCONFIGURACAO").dialog({
					autoOpen : false,
					width : 1500,
					height : 1005,
					modal : true
				});
			});
			
			function fecharItemConfiguracao(){
				$("#POPUP_ITEMCONFIGURACAO").dialog('close');
			}
			
			function consultarSolicitacao(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('listarSolicitacaoServico');	
			}
			
			function consultarProblemas(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('listarProblema');	
			}
			
			function consultarMudancas(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('listarMudanca');	
			}
			
			function consultarComentarios(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('listarComentario');	
			}
			
			function consultarItensConfiguracoes(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('listarItemConfiguracao');	
			}
			
			$(function() {
				$("#POPUP_DADOSSOLICITCAO").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
			});
			
			$(function() {
				$("#POPUP_DADOSPROBLEMA").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
			});
			
			$(function() {
				$("#POPUP_DADOSMUDANCA").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
			});
			
			$(function() {
				$("#POPUP_DADOSCOMENTARIO").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
			});
			
			$(function() {
				$("#POPUP_DADOSITEMCONFIGURACAO").dialog({
					autoOpen : false,
					width : 1000,
					height : 200,
					modal : true
				});
			});
			
			
			function LOOKUP_USUARIO_select(id, desc) {
				
				if ($('#isNotificacao').val() == "true"){
					
					document.formPesquisa.idUsuarioAutorPesquisa.value = id;
					document.formPesquisa.nomeUsuarioAutor.value = desc;
		 			
		 		} else{
		 			
		 			document.formPesquisa.idUsuarioAprovadorPesquisa.value = id;
					document.formPesquisa.nomeUsuarioAprovador.value = desc;
		 			
		 		}
				
				$("#POPUP_USUARIO").dialog("close");		
			}
			
			function abrirPopupUsuario(isNotificacao){
				
				if (isNotificacao == true){
		 			
		 			$('#isNotificacao').val(true);	
		 			
		 		} else{
		 			if (isNotificacao == false){
		 				$('#isNotificacao').val(false);
		 			}
		 		}
				
				$("#POPUP_USUARIO").dialog("open");
			}
			
			 function addLinhaTabelaConhecimentoRelacionado(id, desc, valida, idPasta){
				var tbl = document.getElementById('tabelaConhecimentoRelacionado');
				$('#tabelaConhecimentoRelacionado').show();
				$('#gridConhecimentoRelacionado').show();

				var lastRow = tbl.rows.length;
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contConhecimentoRelacionado++;
				
				coluna.innerHTML = "";
				coluna = row.insertCell(1);
				coluna.innerHTML = "<a  onclick='verificarPermissaoDeAcesso("+idPasta+","+id+");incidentesAbertosPorBaseConhecimnto("+id+");'   id='idTitulo"+id+"' href='javascript:;' >" + desc +"</a>";
			} 
		 	
			 function verificarPermissaoDeAcessoIdBaseConhecimento(idBaseConhecimento) {
					
					var idBaseConhecimento =  parseInt(idBaseConhecimento);
					
					document.form2.idBaseConhecimento.value = idBaseConhecimento;
					
					document.form2.fireEvent("verificarPermissaoDeAcesso");
					
			 } 
			
			 function deleteAllRowsConhecimento() {
					var tabela = document.getElementById('tabelaConhecimentoRelacionado');
					var count = tabela.rows.length;
			
					while (count > 1) {
						tabela.deleteRow(count - 1);
						count--;
					}
					$('#gridConhecimentoRelacionado').hide();
				}
			 
			 
			 $(function() {
				  $("#exibirHistorioVersao").click(function() {
					  
						document.form2.fireEvent("exibirHistoricoVersoesBaseConhecimento");
					});
			   });
			  
			  $(function() {
				  $("#exibirHistorioAlteracao").click(function() {
					  
						document.form2.fireEvent("exibirHistoricoAlteracaoBaseConhecimento");
					});
			   });
			   
			  
			   function addLinhaTabelaHistorioVersoes(id, desc, valida, versao){
					var tbl = document.getElementById('tabelaHistoricoVersoes');
					$('#tabelaHistoricoVersoes').show();
					$('#gridHistoricoVersoes').show();

					var lastRow = tbl.rows.length;
					var row = tbl.insertRow(lastRow);
					var coluna = row.insertCell(0);
					
					coluna.innerHTML = desc ;
					
					coluna = row.insertCell(1);
					
					coluna.innerHTML = versao;
					
				} 
			   
			   function deleteAllRowsHistoricoVersoes() {
					var tabela = document.getElementById('tabelaHistoricoVersoes');
					var count = tabela.rows.length;
			
					while (count > 1) {
						tabela.deleteRow(count - 1);
						count--;
					}
					$('#gridHistoricoVersoes').hide();
			  }
			   
			  function deleteAllRowsHistoricoAlteracao() {
					var tabela = document.getElementById('tabelaHistoricoAlteracao');
					var count = tabela.rows.length;
			
					while (count > 1) {
						tabela.deleteRow(count - 1);
						count--;
					}
					$('#gridHistoricoAlteracao').hide();
			  }
			  
		  function addLinhaTabelaHistorioAlteracao(titulo, pasta, versao, origem, usuario, dataHoraAlteracao, status){
				var tbl = document.getElementById('tabelaHistoricoAlteracao');
				$('#tabelaHistoricoAlteracao').show();
				$('#gridHistoricoAlteracao').show();

				var lastRow = tbl.rows.length;
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				
				coluna.innerHTML = titulo ;
				
				coluna = row.insertCell(1);
				coluna.innerHTML = pasta;
				
				coluna = row.insertCell(2);
				coluna.innerHTML = versao;
				
				coluna = row.insertCell(3);
				coluna.innerHTML = origem;
				
				coluna = row.insertCell(4);
				coluna.innerHTML = status;
				
				coluna = row.insertCell(5);
				coluna.innerHTML = usuario;
				
				coluna = row.insertCell(6);
				coluna.innerHTML = dataHoraAlteracao;
				
			} 
			 
		  	function selectedItemConfiguracao(idItemCfg){
		 		document.formPesquisa.idItemConfiguracao.value = idItemCfg;
		 		document.formPesquisa.fireEvent("restoreItemConfiguracao");
		 	}
		  	
		  	function LOOKUP_PROBLEMA_select(id, desc){
				document.formPesquisa.idProblema.value = id;
				document.formPesquisa.fireEvent('atualizaGridProblema');
			}
			
			function LOOKUP_MUDANCA_select(id, desc){
				document.formPesquisa.idRequisicaoMudanca.value = id;
				document.formPesquisa.fireEvent('atualizaGridMudanca');
			}
			function LOOKUP_LIBERACAO_select(id, desc){
				document.formPesquisa.idRequisicaoLiberacao.value = id;
				document.formPesquisa.fireEvent('atualizaGridLiberacao');
			}
			
			function LOOKUP_SOLICITACAOSERVICO_select(id, desc){
				document.formPesquisa.idSolicitacaoServico.value = id;
				document.formPesquisa.fireEvent('atualizaGridSolicitacao');
			}
		  	
		  	function fecharPopupPesquisaItemCfg(){
				$("#popupCadastroRapido").dialog("close");
			}
		  	
		  	exibeIconesIC = function(row, obj){
				var id = obj.idItemConfiguracao;
				
		        obj.sequenciaIC = row.rowIndex; 
			    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiIC('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
			    
				if(obj.idItemConfiguracaoPai == ""){
					row.cells[3].innerHTML = '<img src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/graph.png" border="0" onclick="popupAtivos( '+ id + ')" style="cursor:pointer"/>';
				}
			}
		  	
		  	function gravarItensConfiguracaoConhecimento(){
		  		document.formPesquisa.idItemConfiguracao.value = "";
		  		
		  		var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
				document.formPesquisa.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
				
				document.formPesquisa.fireEvent("gravarItensConfiguracaoConhecimento");
		  	}
		  	
		  	$(function() {
		  		
		  		$("#POPUP_MUDANCA").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
		  		
		  		$("#POPUP_LIBERACAO").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
		  		
		  		$("#POPUP_PROBLEMA").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
		  		
		  		$("#POPUP_SOLICITACAO").dialog({
					autoOpen : false,
					width : 1000,
					height : 500,
					modal : true
				});
		  		
		  		$("#imagenMudanca").click(function() {
					$("#POPUP_MUDANCA").dialog("open");
				});
		  		
		  		$("#imagenProblema").click(function() {
					$("#POPUP_PROBLEMA").dialog("open");
				});
		  		
		  		$("#imagenSolicitacao").click(function() {
					$("#POPUP_SOLICITACAO").dialog("open");
				});
		  		$("#imagenLiberacao").click(function() {
					$("#POPUP_LIBERACAO").dialog("open");
				});
		  		
		   });
		  	
		  	function chamaPopupCadastroProblema(){
				var idSolicitacaoServico = 0;
					try{
						idSolicitacaoServico = document.form.idSolicitacaoServico.value;
					}catch(e){
				}
				popup4.abrePopupParms('problema', '', 'idSolicitacaoServico=' + idSolicitacaoServico);
			}
		  	
		  	function chamaPopupCadastroMudanca(){
				var idSolicitacaoServico = 0;
					try{
						idSolicitacaoServico = document.form.idSolicitacaoServico.value;
					}catch(e){
				}
				popup5.abrePopupParms('requisicaoMudanca', '', '');
			}
		  	
		  	function chamaPopupCadastroLiberacao(){
				var idSolicitacaoServico = 0;
					try{
						idSolicitacaoServico = document.form.idSolicitacaoServico.value;
					}catch(e){
				}
				popup6.abrePopupParms('requisicaoLiberacao', '', '');
			}
		  	
		  	function fecharProblema(){
				$("#POPUP_CADASTROPROBLEMA").dialog('close');
				$("#popupCadastroRapido").dialog('close');
			}
			
			function fecharMudanca(){
				$("#POPUP_CADASTROMUDANCA").dialog('close');
				$("#popupCadastroRapido").dialog('close');
			}
			function fecharLiberacao(){
				$("#POPUP_LIBERACAO").dialog("close");
			}
			
			function fecharSolicitacaoServico(){
				$("#POPUP_SOLICITACAO").dialog('close');
			}
			
			exibeIconesProblema = function(row, obj){
				var id = obj.idProblema;
		        obj.sequenciaOS = row.rowIndex; 
			    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiProblema('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
			    row.cells[1].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/viewCadastro.png" border="0" onclick="carregarProblema(row, obj)" style="cursor:pointer"/>';
			    
			}
			
			exibeIconesMudanca = function(row, obj){
				var id = obj.idRequisicaoMudanca;
		        obj.sequenciaOS = row.rowIndex; 
			    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiMudanca('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
			}
			exibeIconesLiberacao = function(row, obj){
				var id = obj.idRequisicaoLiberacao;
		        obj.sequenciaOS = row.rowIndex; 
			    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiLiberacao('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
			}
			
			exibeIconesSolicitacao = function(row, obj){
				var id = obj.idRequisicaoMudanca;
		        obj.sequenciaOS = row.rowIndex; 
			    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiSolicitacao('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
			}
			
			function gravarSolicitacoesConhecimento(){
				JANELA_AGUARDE_MENU.show();
				var objs = HTMLUtils.getObjectsByTableId('tblSolicitacao');
				document.formPesquisa.colItensINC_Serialize.value = ObjectUtils.serializeObjects(objs);	
				document.formPesquisa.fireEvent("gravarSolicitacoesConhecimento");
			}
			
			function gravarProblemasConhecimento(){
				JANELA_AGUARDE_MENU.show();
				var objs = HTMLUtils.getObjectsByTableId('tblProblema');
				document.formPesquisa.colItensProblema_Serialize.value = ObjectUtils.serializeObjects(objs);	
				document.formPesquisa.fireEvent("gravarProblemasConhecimento");
			}
			
			function gravarMudancaConhecimento(){	
				JANELA_AGUARDE_MENU.show();
				var objsMudanca = HTMLUtils.getObjectsByTableId('tblMudanca');
				document.formPesquisa.colItensMudanca_Serialize.value = ObjectUtils.serializeObjects(objsMudanca);		
				document.formPesquisa.fireEvent("gravarMudancaConhecimento");
			}
			function gravarLiberacaoConhecimento(){	
				JANELA_AGUARDE_MENU.show();
				var objsLiberacao = HTMLUtils.getObjectsByTableId('tblLiberacao');
				document.formPesquisa.colItensLiberacao_Serialize.value = ObjectUtils.serializeObjects(objsLiberacao);		
				document.formPesquisa.fireEvent("gravarLiberacaoConhecimento");
			}
			
			function gravarICConhecimento(){		
				JANELA_AGUARDE_MENU.show();
				var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
				document.formPesquisa.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);	
				document.formPesquisa.fireEvent("gravarICConhecimento");
			}			
			
			function popupAtivos(id){
				var idItem = id;
				document.getElementById('iframeAtivos').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
				$("#POPUP_ATIVOS").dialog("open");
			}

	excluiProblema = function(indice) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
			HTMLUtils.deleteRow('tblProblema', indice);
		}
	}

	excluiMudanca = function(indice) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
			HTMLUtils.deleteRow('tblMudanca', indice);
		}
	}
	excluiLiberacao = function(indice) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
			HTMLUtils.deleteRow('tblLiberacao', indice);
		}
	}

	excluiIC = function(indice) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
			HTMLUtils.deleteRow('tblIC', indice);
		}
	}

	excluiSolicitacao = function(indice) {
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao"))) {
			HTMLUtils.deleteRow('tblSolicitacao', indice);
		}
	}
	
	function getBotaoEditarProblema(row, obj){
		var botaoVisualizarProblemas = new Image();

		botaoVisualizarProblemas.src = '<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png';
		botaoVisualizarProblemas.setAttribute("style", "cursor: pointer;");
		botaoVisualizarProblemas.id = obj.idProblema;
		botaoVisualizarProblemas.addEventListener("click", function(evt){CarregarProblema(id)}, true);

		return botaoVisualizarProblemas;
	}
	
	function carregarProblema(row, obj){
		var idProblema = obj.idProblema;
		document.getElementById('iframeEditarProblema').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
		$("#POPUP_EDITARPROBLEMA").dialog("open");
	}
	$(function() {
		$("#POPUP_EDITARPROBLEMA").dialog({
			autoOpen : false,
			width : "98%",
			height : 1000,
			modal : true
		});
		
		jQuery.fn.toggleText = function(a,b) {
			return this.html(this.html().replace(new RegExp("("+a+"|"+b+")"),function(x){return(x==a)?b:a;}));
			} 
		

		 $('#divpesquisa').css('display', 'block');
		 
		 $('.manipulaDiv', '#tabs-1').click(function() {
			 $(this).next().slideToggle('slow') .siblings('#divpesquisa:visible').slideToggle('fast');
			 $(this).toggleText('<i18n:message key="baseConhecimento.esconder" />','<i18n:message key="baseConhecimento.mostrar" />').siblings('span').next('#divpesquisa:visible').prev()
			 .toggleText('<i18n:message key="baseConhecimento.esconder" />','<i18n:message key="baseConhecimento.mostrar" />');
		 });
		 
		
		
		 $('#modulos').css('display', 'block');
		 
		 $('span', '#modulosPai').click(function() {
			 $(this).next().slideToggle('slow').siblings('#modulos:visible').slideToggle('fast');
			 
			 $(this).toggleText('<i18n:message key="baseConhecimento.esconder" />','<i18n:message key="baseConhecimento.mostrar" />').siblings('span').next('#modulos:visible').prev()
			 .toggleText('<i18n:message key="baseConhecimento.esconder" />','<i18n:message key="baseConhecimento.mostrar" />')
		 });
		
	});
	
	function habilitaDivPesquisa(){
		 $('#divpesquisa').css('display', 'block');
		 $('#spanPesq').text('<i18n:message key="baseConhecimento.esconderPesquisa" />','<i18n:message key="baseConhecimento.mostrarPesquisa" />');
	}
	
	function desabilitaDivPesquisa(){
		  $('#divpesquisa').css('display', 'none'); 
		 $('#spanPesq').text('<i18n:message key="baseConhecimento.mostrarPesquisa" />','<i18n:message key="baseConhecimento.esconderPesquisa" />');
	}
	
	function fecharFrameProblema(){
		$("#POPUP_EDITARPROBLEMA").dialog("close");
		//document.form.fireEvent("atualizaGridProblema");
	}
	
	function buscaProblema(row, object){
		carregarProblema(row, object);
	}
	
	$(function() {
		var myLayout;
		myLayout = $('body').layout({
		  		north__resizable: false // OVERRIDE the pane-default of 'resizable=true'
	          , north__spacing_open: 0 // no resizer-bar when open (zero height)
	          , north__spacing_closed: 350 
	          , north__minSize: 110
	          , west: {
	        	  minSize: 310
	        	  ,onclose_end: function(){
	        		  $('#baseconhecimento').css('width', '98%');
	        	  }
				  ,onopen_end: function(){
					  $('#baseconhecimento').css('width', '98%');
				}
	          }   
        });
	});

	function pesquisarItensFiltro() {
		$("#POPUP_INCIDENTE").dialog("close");
	}

	function fecharVisao() {
		$("#POPUP_CADASTROLIBERACAO").dialog("close");
		$("#popupCadastroRapido").dialog('close');
	}
	
</script>
<style type="text/css">
	.labelOverflowTresPontinhos label {
		text-overflow: ellipsis;
		overflow: hidden;
		white-space: nowrap;
	}
</style>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>

<body>
		<%
			if (iframe.equalsIgnoreCase("false")) {
		%>
	<div class="ui-layout-north">
		<div id="divLogo" style="overflow: hidden!important;">
			<table cellpadding='0' cellspacing='0'>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<img border="0" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/logo.png" />
					</td>
				</tr>
			</table>	
		</div>
		
		<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block; ">
				<table cellpadding='0' cellspacing='0' width="100" style="width: 100%;">
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td width="100" style="display: block; float: left;">
							<button  type="button" class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<i18n:message key="citcorpore.comum.voltarprincipal" />">
								<img border="0" title="<i18n:message key='citcorpore.comum.voltarprincipal' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/back.png" /><span style="padding-left: 0px !important;"><i18n:message key="citcorpore.comum.voltar" /></span>
							</button>	
						
						</td>
					</tr>
				</table>	
			</div>
		</div>
		<%
			}
		%>
	<!-- <div id="wrapper" class="principal"> -->
		<div class="ui-layout-west">
			<div class="pastas">
					<form name='form2'
						action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimentoView/baseConhecimentoView	'>
						<input type='hidden' id='idBaseConhecimento'
							name='idBaseConhecimento' /> <input type='hidden' id='idPasta'
							name='idPasta' />
		
						<div>
							<div style="" id="principalBaseConhecimento"></div>
						</div>
					</form>
				</div>
			</div>
	<div class="ui-layout-center">
		<div id="baseconhecimento" class="baseconhecimento container_16 clearfix" style="padding: 0px !important;">
			<div class="flat_area grid_16" style="padding-bottom: 1px !important; margin-top: -1px;"></div>
				<div class="box grid_16 tabs">
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<span  class='manipulaDiv' style='cursor: pointer'><span id='spanPesq' style='cursor: pointer'><i18n:message key="baseConhecimento.esconderPesquisa" /></span> &nbsp;<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/search.png" /></span>
						  <div id="divpesquisa" class="section">
							<form class="labelOverflowTresPontinhos" name='formPesquisa' id='formPesquisa'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimentoView/baseConhecimentoView	'>
								<%
									if (iframe.equalsIgnoreCase("false")) {
								%>
								<!-- <div style="float: right;">
									<img border="0" src="/citsmart/imagens/btnvoltar.gif"
										title="Retornar ao menu principal" alt="Voltar"
										onclick="voltar()" style="cursor: pointer;">
								</div> -->
								<%
									}
								%>
								<div class="flat_area grid_16">
									<h2>
										<i18n:message key="baseConhecimento.baseConhecimento" />
									</h2>
								</div>

								<input type='hidden' id='idBaseConhecimento'
									name='idBaseConhecimento' /> <input type="hidden"
									id="idUsuarioAutorPesquisa" name="idUsuarioAutorPesquisa" /> <input
									type="hidden" id="idUsuarioAprovadorPesquisa"
									name="idUsuarioAprovadorPesquisa" /> <input type="hidden"
									id="idItemConfiguracao" name="idItemConfiguracao" /> <input
									type="hidden" id="idProblema" name="idProblema" /> <input
									type="hidden" id="idSolicitacaoServico"
									name="idSolicitacaoServico" /> <input type="hidden"
									id="idRequisicaoMudanca" name="idRequisicaoMudanca" /> <input
									type='hidden' name='colItensProblema_Serialize'
									id='colItensProblema_Serialize' /> <input type='hidden'
									name='colItensMudanca_Serialize' id='colItensMudanca_Serialize' />
								<input type='hidden' name='colItensIC_Serialize'
									id='colItensIC_Serialize' /> <input type='hidden'
									name='colItensINC_Serialize' id='colItensINC_Serialize' />
									<input type="hidden"
									id="idRequisicaoLiberacao" name="idRequisicaoLiberacao" /> <input type='hidden'
									name='colItensLiberacao_Serialize' id='colItensLiberacao_Serialize' />
								<div class="columns clearfix">
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.autor" /></label>
											<div>
												<input  type="text"
													id="nomeUsuarioAutor" name="nomeUsuarioAutor"
													onclick="abrirPopupUsuario(true);" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.aprovador" /></label>
											<div>
												<input  type="text"
													id="nomeUsuarioAprovador" name="nomeUsuarioAprovador"
													onclick="abrirPopupUsuario(false);" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><i18n:message
													key="citcorpore.comum.dataCriacao" /></label>
											<div>
												<input  type='text'
													id="dataInicioPesquisa" name="dataInicioPesquisa"
													maxlength="10" size="10"
													class="Valid[Data] Description[colaborador.dataNascimento] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><i18n:message
													key="citcorpore.comum.dataPublicacao" /></label>
											<div>
												<input  type='text'
													id="dataPublicacaoPesquisa" name="dataPublicacaoPesquisa"
													maxlength="10" size="10"
													class="Valid[Data] Description[colaborador.dataNascimento] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><i18n:message key="relatorioBaseConhecimento.mediaAvaliacao" /></label>
											<div>
												<select id="termoPesquisaNota" name="termoPesquisaNota">
													<option value="">-- <i18n:message key="alcada.limite.todos" /> --</option>
													<option value="0.0">0.0</option>
													<option value="1.0">1.0</option>
													<option value="2.0">2.0</option>
													<option value="3.0">3.0</option>
													<option value="4.0">4.0</option>
													<option value="5.0">5.0</option>
													<option value="S"><i18n:message key="relatorioBaseConhecimento.semAvaliacao" /></option>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_15" >
										<fieldset style="border-bottom: 0px !important;">
											<label><i18n:message key="baseconhecimento.gerenciamentoDisponibilidade"/></label>
											<div>
												<input type='checkbox' name='amDoc' value='S'  />
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div id='divBotaoFechar'
										style='top: 0px; left: 220px; z-index: 10000;'>
										<fieldset>
											<div
												style="border-right: none; left: 0px; width: 400px; padding-top: 10px;"
												class="col_50">
												<label style="font: bold 13px arial, serif;"><i18n:message
														key="baseConhecimento.pesquisar" /></label>
												<div>
													<input
														type='text' name='termoPesquisa' size='40' maxlength="200"
														 />
												</div>
											</div>
											<div
												style="border-right: none; padding-top: 10px; left: 15px;"
												class="col_40">
												<button title='<i18n:message key="citcorpore.comum.pesquisar"/>' type='button' id="btnPesquisar"
													name='btnPesquisar'
													style="margin-top: 17px; margin-left: -30px;" class="light"
													onclick='pesquisarBaseConhecimento()'>
													<img
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
													<span><i18n:message key="baseConhecimento.pesquisar" /></span>
												</button>
												<button title='<i18n:message key="citcorpore.ui.botao.rotulo.Limpar"/>' type='button' id="btnPesquisar"
													name='btnPesquisar' style="margin-top: 17px;" class="light"
													onclick='document.formPesquisa.clear();'>
													<img
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
													<span><i18n:message key="citcorpore.comum.limpar" /></span>
												</button>
											</div>
										</fieldset>
										<table>
											<tr>
												<td>
													<div id='resultPesquisaPai'
														style='border: 1px solid black; background-color: white; height: 400px; width: 650px; overflow: auto'>
														<table>
															<tr>
																<td>
																	<div style="margin: 10px !important;"
																		id='resultPesquisa'></div>
																</td>
															</tr>
														</table>
													</div>
												</td>
											</tr>
										</table>
									</div>
								</div>
							</form>
						</div>

						<div class="section" id="conhecimento" style="overflow: auto;">
							<form id="form" name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimento/baseConhecimento'>

								<div class="flat_area grid_16">
									<input type='text' id="tituloconhecimento" name="titulo" />
								</div>

								<div id="modulosPai" class="columns clearfix">
									
									<input type='hidden' id='idBaseConhecimento'
										name='idBaseConhecimento' /> <input type='hidden'
										name='idBaseConhecimentoPai' />

									<div class="columns clearfix">
										<div class="col_25">
											<fieldset style="height: 58px">
												<label><i18n:message key="pasta.pasta" /></label>
												<div>
													<input type='text' id="nomePasta" name="nomePasta"
														readonly="readonly" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label><i18n:message
														key="baseConhecimento.avaliacao" /></label>
												<div id='divMostraNota'></div>
												<br>

												<div id='divMostraVotos'>
													<label><span id="votos"> </span>
													<i18n:message key="citcorpore.comum.votos" /></label>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<div id='divInfoAdicional'></div>
											</fieldset>
										</div>
									</div>

									<div class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<label><i18n:message
														key="baseConhecimento.conteudo" /></label>
												<div id="conteudo"></div>
											</fieldset>
											<div class="col_100">
												<fieldset>
													<label><i18n:message key="baseConhecimento.anexos" /></label>
													<cit:uploadControl
														style="height:10%;width:100%; border-bottom:1px solid #DDDDDD ; border-top:1px solid #DDDDDD "
														title="<i18n:message key='citcorpore.comum.anexos' />"
														id="uploadAnexos" form="document.form"
														action="/pages/upload/upload.load" disabled="false" />
												</fieldset>
											</div>
										</div>
										<div class="col_50">
											<div class="col_100">
												<fieldset
													style="margin-bottom: -1px; padding-bottom: 21px; height: 34px">
													<div style="padding-top: 20px">
														<a style="padding: 18px;" href="#" class="light"
															id="exibirHistorioVersao"
															title="<i18n:message key='baseConhecimentoView.consultarHistoricoVersoes'/>"> <img
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/search.png" />
															<span style="color: black !important; font-weight: bold"><i18n:message
																	key="pesquisaBaseConhecimento.historicoVersoes" /></span>
														</a>
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset
													style="margin-bottom: -1px; padding-bottom: 21px; height: 34px">
													<div style="padding-top: 20px">
														<a style="padding: 18px;" href="#" class="light"
															id="exibirHistorioAlteracao"
															title="<i18n:message key='baseConhecimentoView.consultarHistoricoAlteracao'/>"> <img
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/search.png" />
															<span style="color: black !important; font-weight: bold"><i18n:message
																	key="pesquisaBaseConhecimento.historicoAlteracao" /></span>
														</a>
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset
													style="margin-bottom: -1px; padding-bottom: 21px; height: 34px">
													<div style="padding-top: 20px">
														<a style="padding: 18px;" href="#" class="light"
															id="addComentarios"> <img
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
															<span style="color: black !important; font-weight: bold"><i18n:message
																	key="baseConhecimento.enviarComentario" /></span>
														</a>
													</div>
												</fieldset>
												<div id="quantidadeComentarioPorBaseConhecimento"></div>
											</div>
											
											<%
												if (iframe.equalsIgnoreCase("false")) {
											%>
											
											</div>
											<div>
												<fieldset>
													<div id="manipularDiv">
													
													</div>
												</fieldset>
											</div>
											<span  class='manipulaDiv' style='cursor: pointer'><i18n:message key="baseConhecimento.esconderConteudo" />&nbsp;<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/content2.png" /></span>
											<div class="col_100" id="modulos">
											
											<!-- DIV INCIDENTES -->
											<div class="col_50">
												<fieldset>
													<div class="col_100">
														<div id="quantidadeSolicitacaoPorBaseConhecimento"></div>
													</div>
													<div id="divSolicitacao" style="display: block" class="col_100">
														<h2 class="section">
															<i18n:message key="solicitacaoServico.solicitacao" />
														</h2>
														<fieldset>
															<div style="width: 90%;">
																<input type='hidden' name='sequenciaSolicitacao'
																	id='sequenciaSolicitacao' /> <input type="hidden"
																	name="idSolicitacaoServico" id="idSolicitacaoServico">
																<input readonly="readonly" style="width: 90% !important;"
																	type='text' id="addMudanca" name="addMudanca"
																	maxlength="70" size="70" /> <img id="imagenSolicitacao"
																	style="vertical-align: middle; cursor: pointer;"
																	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
																<img id='btnCadastroSolicitacao'
																	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
																	onclick="gravarIncidentesRequisicao(document.form.idBaseConhecimento.value);">
															</div>
		
															<div id='divSolicitacaoConhecimento'
																style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
																<table id='tblSolicitacao' cellpadding="0" cellspacing="0"
																	width='100%'>
																	<tr>
																		<td style='text-align: center'
																			class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																		<td width="5%"></td>
																		<td width="20%" class='linhaSubtituloGrid'><i18n:message
																				key="citcorpore.comum.numero" /></td>
																		<td width="75%" class='linhaSubtituloGrid'><i18n:message
																				key="servico.servico" /></td>
																	</tr>
																</table>
															</div>
		
															<button type='button'
																name='btnGravarSolicitacaoConhecimento' class="light"
																onclick="gravarSolicitacoesConhecimento();"
																style="margin: 12px 0 12px 22px;">
																<img
																	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																<span><i18n:message key="citcorpore.comum.gravar" /></span>
															</button>
														</fieldset>
													</div>
												</fieldset>
											</div>
														<!-- FIM DIV INCIDENTES -->
											<%
												}
											%>

											<%
												if(br.com.citframework.util.Util.isVersionFree(request)){
											%>
											<fieldset >
												<div style="width: 100%;">
													<%=Free.getMsgCampoIndisponivel(request)%>
												</div>
											</fieldset>
											<%
											} else 
												if (iframe.equalsIgnoreCase("false")) {
											%>
											
											<!-- DIV PROBLEMAS -->
											<div class="col_50">	
											<fieldset>									
											<div class="col_100">
												<div id="quantidadeProblemaPorBaseConhecimento"></div>
											</div>

											<div id="divProblema" style="display: block" class="col_100">
												<h2 class="section">
													<i18n:message key="problema.problema" />
												</h2>

												<fieldset>
													<div style="width: 90%;">
														<input type='hidden' name='sequenciaProblema'
															id='sequenciaProblema' /> <input type="hidden"
															name="idProblema" id="idProblema"> <input
															readonly="readonly" style="width: 90% !important;"
															type='text' id="addProblema" name="addProblema"
															maxlength="70" size="70" /> <img id="imagenProblema"
															style="vertical-align: middle; cursor: pointer;"
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
														<img id='btnCadastroProblema'
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
															onclick="gravarGestaoProblema(document.form.idBaseConhecimento.value);">
													</div>

													<div id='divProblemaConhecimento'
														style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
														<table id='tblProblema' cellpadding="0" cellspacing="0"
															width='100%'>
															<tr>
																<td style='text-align: center'
																	class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																<td width="10%"></td>
																<td width="60%" class='linhaSubtituloGrid'><i18n:message
																		key="requisicaMudanca.titulo" /></td>
																<td width="29%" class='linhaSubtituloGrid'><i18n:message
																		key="requisicaMudanca.status" /></td>
															</tr>
														</table>
													</div>

													<button type='button' name='btnGravar' class="light"
														onclick="gravarProblemasConhecimento();"
														style="margin: 12px 0 12px 22px;">
														<img
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
														<span><i18n:message key="citcorpore.comum.gravar" /></span>
													</button>
												</fieldset>
											</div>
											</fieldset>
											</div>
											<!-- FIM DIV PROBLEMAS -->
											
											<!-- DIV MUDANCA -->
											<DIV class="col_50">
											<fieldset>
											<div class="col_100">
												<div id="quantidadeMudancaPorBaseConhecimento"></div>
											</div>

											<div id="divMudanca" style="display: block" class="col_100">
												<h2 class="section">
													<i18n:message key="requisicaMudanca.mudanca" />
												</h2>
												<fieldset>
													<div style="width: 90%;">
														<input type='hidden' name='sequenciaMudanca'
															id='sequenciaMudanca' /> <input type="hidden"
															name="idRequisicaoMudanca" id="idRequisicaoMudanca">
														<input readonly="readonly" style="width: 90% !important;"
															type='text' id="addMudanca" name="addMudanca"
															maxlength="70" size="70" /> <img id="imagenMudanca"
															style="vertical-align: middle; cursor: pointer;"
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
														<img id='btnCadastroMudanca'
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
															onclick="gravarGestaoMudanca(document.form.idBaseConhecimento.value);">
													</div>

													<div id='divMudancaConhecimento'
														style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
														<table id='tblMudanca' cellpadding="0" cellspacing="0"
															width='100%'>
															<tr>
																<td style='text-align: center'
																	class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																<td width="10%"></td>
																<td width="60%" class='linhaSubtituloGrid'><i18n:message
																		key="requisicaMudanca.titulo" /></td>
																<td width="29%" class='linhaSubtituloGrid'><i18n:message
																		key="requisicaMudanca.status" /></td>
															</tr>
														</table>
													</div>

													<button type='button' name='btnGravar' class="light"
														onclick="gravarMudancaConhecimento();"
														style="margin: 12px 0 12px 22px;">
														<img
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
														<span><i18n:message key="citcorpore.comum.gravar" /></span>
													</button>
												</fieldset>
											</div>
											</fieldset>
											</div>
											<!-- DIV ITEM CONFIGURAÇÃO -->
											<DIV class="col_50">
												<fieldset>
													<div class="col_100">
														<div id="quantidadeItemConfiguracaoPorBaseConhecimento"></div>
													</div>
		
													<div id="divItemConfiguracao" class="col_100"
														style="display: block;">
														<h2 class="section"><i18n:message key="itemConfiguracao.itemConfiguracao"/></h2>
		
														<fieldset>
															<label> <img
																title="<i18n:message key="solicitacaoServico.informacaoItemConfiguracao" />"
																id="imagem" onclick="popupAtivos();"
																style="vertical-align: top; cursor: pointer; display: none;"
																src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/graph.png">
																<img id="btHistoricoIc"
																style="cursor: pointer; display: none;"
																src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png">
															</label>
		
															<div style="width: 90%;">
																<input
																	onclick="popup.abrePopup('pesquisaItemConfiguracao','()')"
																	readonly="readonly" style="width: 90% !important;"
																	type='text' id="itemConfiguracao" name="itemConfiguracao"
																	maxlength="70" size="70" /> <img id="imagenIC"
																	onclick="popup.abrePopup('pesquisaItemConfiguracao','()')"
																	style="vertical-align: middle; cursor: pointer;"
																	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
																<img id='btnCadastroItemConfiguracao'
																	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
																	onclick="gravarGestaoItemConfiguracao(document.form.idBaseConhecimento.value);">
															</div>
		
															<div id='divIC'
																style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
																<table id='tblIC' cellpadding="0" cellspacing="0"
																	width='100%'>
																	<tr>
																		<td style='text-align: center'
																			class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																		<td width="20%" class='linhaSubtituloGrid'><i18n:message
																				key="citcorpore.comum.numero" /></td>
																		<td width="62%" class='linhaSubtituloGrid'><i18n:message
																				key="citcorpore.comum.identificacao" /></td>
																		<td width="10%"><i18n:message key="solicitacaoServico.informacao"/></td>
																	</tr>
																</table>
															</div>
		
															<button type='button' name='btnGravar' class="light"
																onclick="gravarICConhecimento();"
																style="margin: 12px 0 12px 22px;">
																<img
																	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																<span><i18n:message key="citcorpore.comum.gravar" /></span>
															</button>
														</fieldset>
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<div id="quantidadeLiberacaoPorBaseConhecimento"></div>
											</div>

											<div id="divLiberacao" style="display: block" class="col_50">
												<h2 class="section">
													<i18n:message key="requisicaoLiberacao.liberacao" />
												</h2>
												<fieldset>
													<div style="width: 90%;">
														<input type='hidden' name='sequenciaLiberacao'
															id='sequenciaLiberacao' /> <input type="hidden"
															name="idRequisicaoLiberacao" id="idRequisicaoLiberacao">
														<input readonly="readonly" style="width: 90% !important;"
															type='text' id="addLiberacao" name="addLiberacao"
															maxlength="70" size="70" /> <img id="imagenLiberacao"
															style="vertical-align: middle; cursor: pointer;"
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
														<img id='btnCadastroLiberacao'
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
															onclick="gravarGestaoLiberacao(document.form.idBaseConhecimento.value);">
													</div>

													<div id='divLiberecaoConhecimento'
														style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
														<table id='tblLiberacao' cellpadding="0" cellspacing="0"
															width='100%'>
															<tr>
																<td style='text-align: center'
																	class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																<td width="10%"></td>
																<td width="60%" class='linhaSubtituloGrid'><i18n:message
																		key="requisicaMudanca.titulo" /></td>
																<td width="29%" class='linhaSubtituloGrid'><i18n:message
																		key="requisicaMudanca.status" /></td>
															</tr>
														</table>
													</div>

													<button type='button' name='btnGravar' class="light"
														onclick="gravarLiberacaoConhecimento();"
														style="margin: 12px 0 12px 22px;">
														<img
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
														<span><i18n:message key="citcorpore.comum.gravar" /></span>
													</button>
												</fieldset>
											</div>
											<%
											}
											%>

											<div id="POPUP_DADOSCOMENTARIO">
												<div class="col_50">
													<label
														style="font-weight: bold; color: #333333; display: block; font-size: 11px; font-weight: bold; margin-right: 10px; padding: 4px 20px;"><i18n:message
															key="baseConhecimento.comentarios" /></label>

													<div id="gridComentario" class="columns clearfix"
														style="display: none;">
														<table class="table" id="tabelaComentarios"
															style="width: 100%">
															<tr style="text-align: left;">
																<th style="width: 15%;"><i18n:message
																		key="citcorpore.comum.nome" /></th>
																<th style="width: 25%;"><i18n:message
																		key="citcorpore.comum.email" /></th>
																<th style="width: 30%;"><i18n:message
																		key="baseConhecimento.comentario" /></th>
																<th style="width: 30%;"><i18n:message
																		key="baseConhecimento.nota" /></th>
															</tr>
														</table>
													</div>
												</div>
											</div>

											<div class="col_100">
												<div id="gridConhecimentoRelacionado">
													<label style="font-weight: bold; color: #333333; display: block; font-size: 11px; font-weight: bold; margin-right: 10px; padding: 4px 20px;">
														<i18n:message key='baseConhecimentoView.conhecimentosRelacionados'/></label>
													<table class="table" id="tabelaConhecimentoRelacionado"
														style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 50%;"><i18n:message key='menu.relatorio.gerConhecimento'/></th>
														</tr>
													</table>
												</div>
											</div>
										</div>
									</div>

									<div id="popupCadastroRapido">
										<iframe id="frameCadastroRapido" name="frameCadastroRapido"
											width="97%" height="97%"></iframe>
									</div>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			</div>
		</div>
	<!-- </div> -->
</body>

<div id="POPUP_COMENTARIOS"
	title="<i18n:message key='comentarios.comentarios' />">
	<form id="formPopup" name='formPopup'
		action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/comentarios/comentarios'>
		<div class="columns clearfix" style="padding: 15px; width: 500px;">

			<input type='hidden' id='idBaseConhecimento'
				name='idBaseConhecimento'
				value="<%session.getAttribute("idBaseConhecimento");%>" /> <input
				type='hidden' id='idComentario' name='idComentario' /> <input
				type='hidden' name='dataInicio' /> <input type='hidden'
				name='dataFim' />

			<h2 style="padding: 0px;">
				<i18n:message key="comentarios.nota" />
			</h2>
			<span><i18n:message key="baseConhecimento.notaconhecimento" /></span>
			<div class="Clear" id="notaEnviarComentario">
				<input id="nota" class="star required" type="radio" name="nota"
					value="1" title="<i18n:message key='citcorpore.comum.fraco'/>" /> <input class="star" type="radio"
					name="nota" value="2" title="<i18n:message key='citcorpore.comum.regular'/>" /> <input class="star"
					type="radio" name="nota" value="3" title="<i18n:message key='citcorpore.comum.bom'/>" /> <input
					class="star" type="radio" name="nota" value="4" title="<i18n:message key='citcorpore.comum.otimo'/>" /> <input
					class="star" type="radio" name="nota" value="5" title="<i18n:message key='citcorpore.comum.excelente'/>" />
			</div>

			<br>

			<h2 class="campoObrigatorio" style="padding: 0px;">
				<i18n:message key="comentarios.nome" />
			</h2>
			<span><i18n:message key="baseConhecimento.nomecompleto" /></span>
			<div>
				<input type='text' name="nome"
					onkeydown="FormatUtils.noNumbers(this)" maxlength="70" size="70"
					class="Valid[Required] Description[comentarios.nome]" />
			</div>

		

			<h2 class="campoObrigatorio" style="padding: 0px;">
				<i18n:message key="comentarios.email" />
			</h2>
			<span><i18n:message key="baseConhecimento.informemail" /> </span>
			<div>
				<input type='text' name="email" maxlength="70" size="70"
					onchange="ValidacaoUtils.validaEmail(email,'');"
					class="Valid[Required] Description[comentarios.email]" />
			</div>

			

			<h2 class="campoObrigatorio" style="padding: 0px;">
				<i18n:message key="comentarios.comentario" />
			</h2>
			<%-- <span><i18n:message
					key="baseConhecimento.vantagensdesvantagens" /> </span> --%>
			<div>
				<textarea rows="" cols="" maxlength="200" id="comentario"
					name="comentario"></textarea>
			</div>

			<br>

			<button type='button' name='btnGravar' class="light"
				onclick='gravar();'>
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span><i18n:message key="citcorpore.comum.gravar" /></span>
			</button>
			<button type="button" name='btnLimpar' class="light"
				onclick='limpar();'>
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
				<span><i18n:message key="citcorpore.comum.limpar" /></span>
			</button>

		</div>
	</form>
</div>

<div id="POPUP_INCIDENTE"
	title="<i18n:message key='solicitacaoServico.solicitacao' /> ">
	<iframe id="iframeSolicitacaoServico" name="iframeSolicitacaoServico"
		width="95%" height="95%"> </iframe>
</div>

<div id="POPUP_CADASTROPROBLEMA"
	title="<i18n:message key='problema.problema' /> ">
	<iframe id="iframeProblema" name="iframeProblema" 
		width="97%" height="97%"> </iframe>
</div>

<div id="POPUP_CADASTROMUDANCA"
	title="<i18n:message key='requisicaMudanca.mudanca' /> ">
	<iframe id="iframeMudanca" name="iframeMudanca" 
		width="97%" height="97%"> </iframe>
</div>
<div id="POPUP_CADASTROLIBERACAO"
	title="<i18n:message key='requisicaoLiberacao.liberacao' /> ">
	<iframe id="iframeLiberacao" name="iframeLiberacao" 
		width="97%" height="97%"> </iframe>
</div>

<div id="POPUP_MUDANCA"
	title="<i18n:message key="requisicaMudanca.mudanca" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="right">
						<!-- <label style="cursor: pointer;"> <i18n:message
								key="requisicaMudanca.mudanca" /> <img id='botaoMudanca'
							src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
							onclick="chamaPopupCadastroMudanca()">
						</label> -->
					</div>
					<form name='formPesquisaMudanca' style="width: 540px">
						<cit:findField formName='formPesquisaMudanca'
							lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0' left='0'
							len='550' heigth='400' javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_LIBERACAO"
	title="<i18n:message key="requisicaoLiberacao.liberacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="right">
						<!-- <label style="cursor: pointer;"> <i18n:message
								key="requisicaoLiberacao.liberacao" /> <img id='botaoLiberacao'
							src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
							onclick="chamaPopupCadastroLiberacao()">
						</label> -->
					</div>
					<form name='formPesquisaLiberacao' style="width: 540px">
						<cit:findField formName='formPesquisaLiberacao'
							lockupName='LOOKUP_LIBERACAO' id='LOOKUP_LIBERACAO' top='0' left='0'
							len='550' heigth='400' javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_PROBLEMA"
	title="<i18n:message key="problema.problema" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="right">
						<!-- <label style="cursor: pointer;"> <i18n:message
								key="problema.problema" /> <img id='botaoProblema'
							src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
							onclick="chamaPopupCadastroProblema()">
						</label> -->
					</div>
					<form name='formPesquisaProblema' style="width: 540px">
						<cit:findField formName='formPesquisaProblema'
							lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_SOLICITACAO"
	title="<i18n:message key="gerenciaservico.solicitacaoincidente" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="right">
						<!-- <label style="cursor: pointer;"> <i18n:message
								key="gerenciaservico.solicitacaoincidente" /> <img
							id='botaoSolicitacao'
							src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
							onclick="chamaPopupCadastroSolicitacao()">
						</label> -->
					</div>
					<form name='formPesquisaSolicitacao' style="width: 540px">
						<cit:findField formName='formPesquisaSolicitacao'
							lockupName='LOOKUP_SOLICITACAOSERVICO'
							id='LOOKUP_SOLICITACAOSERVICO' top='0' left='0' len='550'
							heigth='400' javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_ITEMCONFIGURACAO"
	title="<i18n:message key='itemConfiguracao.itemConfiguracao' /> ">
	<iframe id="iframeItemConfiguracao" name="iframeItemConfiguracao"
		width="99%" height="90%"> </iframe>
</div>

<div id="POPUP_COMENTARIO"
	title="<i18n:message key='baseConhecimento.comentario' /> ">
	<iframe id="iframeComentario" name="iframeComentario" 
		width="97%" height="97%"> </iframe>
</div>

<div id="POPUP_DADOSSOLICITCAO"
	title="<i18n:message key='baseConhecimento.dadosolicacao' />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formOcorrencias' method="post" action=''>
						<div id='dadosSolicitacao/Incidetes'
							style='width: 100%; height: 100%;'></div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_DADOSPROBLEMA"
	title="<i18n:message key='baseConhecimento.dadosproblema' />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formOcorrencias' method="post" action=''>
						<div id='dadosProblema' style='width: 100%; height: 100%;'>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_DADOSMUDANCA"
	title="<i18n:message key='baseConhecimento.dadosmudanca' />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formOcorrencias' method="post" action=''>
						<div id='dadosMudanca' style='width: 100%; height: 100%;'></div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_DADOSITEMCONFIGURACAO"
	title="<i18n:message key='baseConhecimento.dadositemconfiguracao' />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formOcorrencias' method="post" action=''>
						<div id='dadosItemConfiguracao' style='width: 100%; height: 100%;'>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_USUARIO"
	title="<i18n:message key="citcorpore.comum.pesquisar" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaUsuario' style="width: 540px">
						<input type="hidden" id="isNotificacao" name="isNotificacao">
						<cit:findField formName='formPesquisaUsuario'
							lockupName='LOOKUP_USUARIO_CONHECIMENTO' id='LOOKUP_USUARIO'
							top='0' left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_HISTORICOVERSAO">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaHistoricoVersao' style="width: 540px">
						<div id="gridHistoricoVersoes">
							<table style="width: 100%" class="table"
								id="tabelaHistoricoVersoes">
								<tr>
									<th style="width: 90%;"><i18n:message key="planoMelhoria.tituloplano"/></th>
									<th style="width: 10%;"><i18n:message key="midiaSoftware.versao"/></th>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_ALTERACAO">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaHistoricoAlteracao' style="width: 930px">
						<div id="gridHistoricoAlteracao">
							<table style="width: 100%;" class="table"
								id="tabelaHistoricoAlteracao">
								<tr>
									<th style="width: 30%;"><i18n:message key="planoMelhoria.tituloplano"/></th>
									<th style="width: 30%;"><i18n:message key="menu.nome.pasta"/></th>
									<th style="width: 2%;"><i18n:message key="midiaSoftware.versao"/></th>
									<th style="width: 2%;"><i18n:message key="lookup.origem"/></th>
									<th style="width: 2%;"><i18n:message key="pagamentoProjeto.status"/></th>
									<th style="width: 50%;"><i18n:message key="pagamentoProjeto.usuario"/></th>
									<th style="width: 25%;"><i18n:message key="baseConhecimentoView.dataHoraAlteracao"/></th>
								</tr>
							</table>
						</div>
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_ATIVOS"
	title="<i18n:message key="pesquisa.listaAtivosDaMaquina" />"
	style="overflow: hidden;">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block" style="overflow: hidden;">
				<div class="section" style="overflow: hidden;">
					<iframe id="iframeAtivos"
						style="display: block; margin-left: -20px;" name="iframeAtivos"
						width="97%" height="97%"> </iframe>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_EDITARPROBLEMA"  style="overflow: hidden;" title="<i18n:message key="problema.problema"/>">
		<iframe id='iframeEditarProblema' src='about:blank' width="97%" height="97%" style='border:none;' onload="resize_iframe()"></iframe>		
</div>
	<script type="text/javascript">
		/*
		* Motivo: Corrigindo erros de scripts
		* Autor: flavio.santana
		* Data/Hora: 04/11/2013 16:19
		*/
		function resize_iframe(){}
		
		if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
		    document.documentElement.className += "ie10";
		}
	</script>
</html>