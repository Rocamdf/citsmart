<%@page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodePartDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ExternalClassDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CITCorporeUtil"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ScriptEventDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ObjetoNegocioDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.ArrayList"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String templateTab = (String)request.getAttribute("templateTab");
	if (templateTab == null){
		templateTab = "";
	}
	Collection relacaoVisoes = (Collection)request.getAttribute("relacaoVisoes");
	if (relacaoVisoes == null){
		relacaoVisoes = new ArrayList();
	}
	Collection objetosNegocio = (Collection)request.getAttribute("objetosNegocio");
	if (objetosNegocio == null){
		objetosNegocio = new ArrayList();
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
	<%@include file="/include/security/security.jsp" %>
	<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
	<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
	<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
	<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
	<!--[if gt IE 8]><!--> <html lang="pt-br" class="no-js"> <!--<![endif]-->
	
	<title>CIT Corpore</title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/reset.css" />
	
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
	<script type="text/javascript" src="../../cit/objects/VisaoDTO.js"></script>
	
	<style type="text/css">
		#sortable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
		#sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 100%; }
		#sortable li span { position: absolute; margin-left: -1.3em; }
		
		#tabsAssociadas { margin-top: 1em !important; }
		#tabsAssociadas li .ui-icon-close { float: left !important; margin: 0.4em 0.2em 0 0; cursor: pointer; }
		
		.tabs li{
			float: none !important;
			display: block !important;
		}
		
		.tableLess {
		  font-family: arial, helvetica !important;
		  font-size: 10pt !important;
		  cursor: default !important;
		  margin: 0 !important;
		  background: white !important;
		  border-spacing: 0  !important;
		  width: 100%  !important;
		}
		
		.tableLess tbody {
		  background: transparent  !important;
		}
		
		.tableLess * {
		  margin: 0 !important;
		  vertical-align: middle !important;
		  padding: 2px !important;
		}
		
		.tableLess thead th {
		  font-weight: bold  !important;
		  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
		  text-align: center  !important;
		}
		
		.tableLess tbody tr:ACTIVE {
		  background-color: #fff  !important;
		}
		
		.tableLess tbody tr:HOVER {
		  background-color: #e7e9f9 ;
		  cursor: pointer;
		}
		
		.tableLess th {
		  border: 1px solid #BBB  !important;
		  padding: 6px  !important;
		}
		
		.tableLess td{
		  border: 1px solid #BBB  !important;
		  padding: 6px 10px  !important;
		}
		#listaTodasVisoesTb {
			margin: 30px 0;
		}
		.throbber {
			background: url('/citsmart/imagens/ajax-loader.gif');
			display: inline-block;
			height: 16px;
			width: 16px;
		}
		#Throbber {
			margin: 4px 10px;
			vertical-align: middle;
			visibility: hidden;
		}
		.barra {
			margin: 10px 0;
		}
	</style>
	
	<script type="text/javascript">
		var $tabsAssociadas;
		$(document).ready(function() {
			$( "#POPUP_OBJ" ).dialog({
				title: i18n_message("visaoAdm.campoVisao"),
				width: 800,
				height: 600,
				modal: true,
				autoOpen: false,
				resizable: false,
				show: "fade",
				hide: "fade"
				}); 
					
			$("#POPUP_OBJ").hide();
	
			$( "#POPUP_SCRIPT" ).dialog({
				title: 'Scripts',
				width: 800,
				height: 600,
				modal: true,
				autoOpen: false,
				resizable: false,
				show: "fade",
				hide: "fade"
				}); 
					
			$("#POPUP_SCRIPT").hide();
			
			$( "#POPUP_HTMLCODE" ).dialog({
				title: 'Html Code',
				width: 800,
				
				height: 600,
				modal: true,
				autoOpen: false,
				resizable: false,
				show: "fade",
				hide: "fade"
				}); 
					
			$("#POPUP_HTMLCODE").hide();		
			
			$( "#POPUP_BOTOES" ).dialog({
				title: i18n_message("visaoAdm.botoes"),
				width: 800,
				height: 600,
				modal: true,
				autoOpen: false,
				resizable: false,
				show: "fade",
				hide: "fade"
				}); 
			
			$("#POPUP_BOTOES").hide();	
			
			$( "#POPUP_IMPORTAR" ).dialog({
				title: i18n_message("visaoAdm.importarVisao"),
				width: 500,
				height: 300,
				modal: true,
				autoOpen: false,
				resizable: false,
				show: "fade",
				hide: "fade"
				}); 
			
			$("#POPUP_IMPORTAR").hide();			
			
			$("#POPUP_EXPORTARVISOES").dialog({
				title: i18n_message("visaoAdm.exportarVisoes"),
				autoOpen : false,
				width : 700,
				height : 500,
				modal : true,
				show: "fade",
				hide: "fade"
			});
			
			$("#btnFechaExportacoes").click(function(){
				$('#POPUP_EXPORTARVISOES').dialog('close');	 
			});
			
			$("#btnFechaTodasImportacoes").click(function(){
				$('#POPUP_IMPORTARTODASVISOES').dialog('close');	 
			});
			
			$("#POPUP_IMPORTARVISOES").dialog({
				title: i18n_message("visaoAdm.importarVisoes"),
				autoOpen : false,
				width : 700,
				height : 320,
				modal : true,
				show: "fade",
				hide: "fade"
			});
			
			$("#POPUP_IMPORTARTODASVISOES").dialog({
				title: i18n_message("visaoAdm.importarTodasVisoes"),
				autoOpen : false,
				width : 700,
				height : 320,
				modal : true,
				show: "fade",
				hide: "fade"
			});
			
			$("#btnFechaImportacoes").click(function(){
				$('#POPUP_IMPORTARVISOES').dialog('close');	 
			});
					
			$( "#sortable" ).sortable({
				cancel: ".ui-state-disabled"
			});
			$( "#sortable" ).disableSelection();	
			
			$tabsAssociadas = $( "#tabsAssociadas").tabs({
				tabTemplate: "<%=templateTab%>",
				add: function( event, ui ) {
					var tab_content = document.getElementById('divOcultaVisaoRelacionada').innerHTML;
					tab_content = StringUtils.replaceAll(tab_content, '#SEQ#', '' + tab_counter);
					$( ui.panel ).append( "<p>" + tab_content + "</p>" );
				}
			});	
			
			$( "#tabsAssociadas span.ui-icon-close" ).live( "click", function() {
				var index = $( "li", $tabsAssociadas ).index( $( this ).parent() );
				$tabsAssociadas.tabs( "remove", index );
			});
			
			var tab_content = document.getElementById('divOcultaVisaoRelacionada').innerHTML;
			tab_content = StringUtils.replaceAll(tab_content, '#SEQ#', '1');
			document.getElementById('tabsAssociadas-1').innerHTML = tab_content;			
		});
		
		var tab_counter = 2;
		
		function addTab() {
			var tab_title = i18n_message("visaoAdm.visaoRelacionada");
			$tabsAssociadas.tabs( "add", "#tabsAssociadas-" + tab_counter, tab_title );
			tab_counter++;
		}
		
		function geraSortable(id){
			$( "#" + id ).sortable();
		}
		
		function mostraAddObj(){
			var idObj = document.formItem.idObjetoNegocio.value;
			document.formItem.clear();
			document.formItem.idObjetoNegocio.value = idObj;
			document.formItem.numeroEdicao.value = '';
			$( "#POPUP_OBJ" ).dialog( 'open' );
		}

		function adicionaAtualizaItem(){
			if (document.formItem.validate()){
				selecionaTudo();
				if (document.formItem.numeroEdicao.value == ''){
					document.formItem.fireEvent('addItem');
				}else{
					document.formItem.fireEvent('atualizaItem');
				}
			}
		}
		function excluir(){
			if (document.formItem.numeroEdicao.value == ''){
				alert(i18n_message("visaoAdm.naoHaItemExcluir"));
			}else{
				document.formItem.fireEvent('deleteItem');
			}
		}
		function excluirVisao(){
			if (document.form.idVisao.value == ''){
				alert(i18n_message("visaoAdm.naoHaVisaoExcluir"));
			}else{
				if (confirm(i18n_message("visaoAdm.desejaRealmenteExcluirVisao"))){
					document.form.fireEvent('deleteVisao');
				}
			}		
		}
		
		function adicionaAtualizaScript(){
			if (document.formScript.validate()){
				document.formScript.fireEvent('atualizaScript');
			}	
		}
		function adicionaAtualizaHTMLCode(){
			if (document.formHtmlCode.validate()){
				document.formHtmlCode.fireEvent('atualizaHTMLCode');
			}	
		}	
		function adicionaAtualizaBotao(){
			if (document.formBotoes.validate()){
				document.formBotoes.fireEvent('atualizaBotao');
			}	
		}
		function excluirBotao(){
			if (confirm(i18n_message("visaoAdm.confirmaExclusaoBotao"))){
				document.formBotoes.fireEvent('excluirBotao');
			}
		}
		function limparBotao(){
			document.formBotoes.clear();
		}
		function selecionaAcaoBotao(obj){
			if (obj.value == '<%=BotaoAcaoVisaoDTO.ACAO_GRAVAR%>'){
				document.formBotoes.texto.value ='<i18n:message key="botaoacaovisao.gravar_dados"/>';
			}
			if (obj.value == '<%=BotaoAcaoVisaoDTO.ACAO_LIMPAR%>'){
				document.formBotoes.texto.value = '<i18n:message key="botaoacaovisao.limpar_dados"/>';
			}
			if (obj.value == '<%=BotaoAcaoVisaoDTO.ACAO_EXCLUIR%>'){
				document.formBotoes.texto.value = '<i18n:message key="botaoacaovisao.excluir"/>';
			}	
			if (obj.value == '<%=BotaoAcaoVisaoDTO.ACAO_SCRIPT%>'){
				document.formBotoes.texto.value = '';
			}
		}
		function limparItem(){
			var num = document.formItem.numeroEdicao.value;
			document.formItem.clear();
			document.formItem.numeroEdicao.value = num;
		}
		function editar(num){
			document.formItem.numeroEdicao.value = num;
			document.formItem.fireEvent('recuperaItem');
		}
		function escondeDivs(){
			document.getElementById("divCampoRadioSelect").style.display = 'none';
			document.getElementById("divCampoNumeroDecimais").style.display = 'none';
			document.getElementById("divCampoRelacao").style.display = 'none';
			document.getElementById("divCampoHTMLCode").style.display = 'none';
		}
		function selecionaObjNegocio(obj){
			document.formItem.fireEvent('listarCamposObjNegocio');
		}
		function selecionaObjNegocioLigacao(obj){
			document.formItem.fireEvent('listarCamposObjNegocioLigacao');
		}	
		function selecionaCampoObjNegocio(obj){
			escondeDivs();
			document.formItem.fireEvent('aplicaCfgCampoObjNegocioVisao');
		}
		function gravar(){
			var numTabs = '';
			for (var i = 0; i < tab_counter; i++){
				if (document.getElementById('tabsAssociadas-' + i)){
					//alert('tabsAssociadas-' + i);
					numTabs = numTabs + i + ",";
	
					var cbo = document.getElementById('vinculosVisaoPaiNN_' + i);
					if (cbo != null && cbo != undefined){
				    	for(var x = cbo.length -1; x >= 0; x--){
				        	cbo.options[x].selected = true;
					    }		
					}
					var cbo = document.getElementById('vinculosVisaoFilhoNN_' + i);
					if (cbo != null && cbo != undefined){
				    	for(var x = cbo.length -1; x >= 0; x--){
				    		cbo.options[x].selected = true;
					    }				
					}
				}
			}
			
			var result = $('#sortable').sortable('toArray');
			var ordemStr = '';
			for(var i = 0; i < result.length; i++){
				ordemStr = ordemStr + StringUtils.onlyNumbers(result[i]) + ',';
			}	
			document.form.ordemCampos.value = ordemStr;
			document.form.numTabs.value = numTabs;
			document.form.save();
		}
		function showScripts(){
			$( "#POPUP_SCRIPT" ).dialog( 'open' );
		}
		function showHTMLCode(){
			$( "#POPUP_HTMLCODE" ).dialog( 'open' );
		}	
		function showBotoes(){
			$( "#POPUP_BOTOES" ).dialog( 'open' );
		}
		function fechar(){
			$( "#POPUP_OBJ" ).dialog( 'close' );
		}
		function fecharScript(){
			$( "#POPUP_SCRIPT" ).dialog( 'close' );
		}
		function fecharBotao(){
			$( "#POPUP_BOTOES" ).dialog( 'close' );
		}	
		function fecharHTMLCode(){
			$( "#POPUP_HTMLCODE" ).dialog( 'close' );
		}	
		function LOOKUP_VISAO_select(id,desc){
			document.getElementById("sortable").innerHTML = '<i18n:message key="citcorpore.comum.aguardecarregando"/>';
			window.location = '<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/visaoAdm/visaoAdm.load?idVisao=' + id;
		}
		function limparTela(){
			document.getElementById("sortable").innerHTML ='<i18n:message key="citcorpore.comum.aguardecarregando"/>';
			window.location = '<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/visaoAdm/visaoAdm.load';
		}
		function addOpcao(){
			var v = document.formItem.valorOpcao.value;
			var d = document.formItem.descricaoOpcao.value;
			HTMLUtils.addOptionIfNotExists('valoresOpcoes', v + ' - ' + d, v + '#' + d);
		}
		function removeOpcao(){
			var cbo = document.getElementById('valoresOpcoes');
	    	for(var i = cbo.length -1; i >= 0; i--){
	        	if (cbo.options[i].selected){
			    	cbo.options[i] = null;
	        	}
		    }		
		}
		function selecionaTudo(){
			var cbo = document.getElementById('valoresOpcoes');
	    	for(var i = cbo.length -1; i >= 0; i--){
	        	cbo.options[i].selected = true;
		    }		
		}	
		function mudaTipoVinculo(obj, seq, fire){
			document.getElementById('divLabelVinculoVisao1_' + seq).style.display = 'none';
			document.getElementById('divLabelVinculoVisao2_' + seq).style.display = 'none';
			document.getElementById('divVinculoVisaoNN1_' + seq).style.display = 'none';
			document.getElementById('divVinculoVisaoNN2_' + seq).style.display = 'none';
			if (obj.value == '1'){
				document.getElementById('divVinculoVisaoNN1_' + seq).style.display = 'block';
				
				document.form.seq.value = seq;
				if (fire){
					document.form.idVisaoRel.value = HTMLUtils.getValue('divVisaoRelacionada_' + seq, document.form);
					document.form.fireEvent('listaCamposVisao');
					document.form.fireEvent('getCampos1ToN');
				}			
			}
			if (obj.value == '2'){
				document.getElementById('divLabelVinculoVisao1_' + seq).style.display = 'block';
				document.getElementById('divLabelVinculoVisao2_' + seq).style.display = 'block';
				document.getElementById('divVinculoVisaoNN1_' + seq).style.display = 'block';
				document.getElementById('divVinculoVisaoNN2_' + seq).style.display = 'block';
	
				document.form.seq.value = seq;
				if (fire){
					document.form.fireEvent('listaCamposVisao');
				}
			}
		}
		function mudaCampoObjNN(obj, seq){
			document.form.idObjetoNegocio.value = '';
			document.form.idObjetoNegocio.value = HTMLUtils.getValue('idObjetoNegocioNN_' + seq, document.form);
			document.form.nomeCombo.value = 'idCamposObjetoNegocioObjNN1_' + seq;
			document.form.fireEvent('listarCamposObjNegocioNN');
	
			document.form.nomeCombo.value = 'idCamposObjetoNegocioObjNN2_' + seq;
			document.form.fireEvent('listarCamposObjNegocioNN');		
		}
		function mudaCampoVisaoRel(obj, seq){
			document.form.idVisaoRel.value = HTMLUtils.getValue('divVisaoRelacionada_' + seq, document.form);
			document.form.seq.value = seq;
			document.form.fireEvent('getCamposFromVisaoRel');
		}
		function addVincObjNNPai(seq){
			var idCpNegPai = HTMLUtils.getValue('idCamposObjetoNegocioPai_' + seq, document.form);
			if (idCpNegPai == '' || idCpNegPai == 0){
				alert(i18n_message("visaoAdm.informeCampoNegocioVisaoPai"));
				return;
			}
			var idCpNegNN = HTMLUtils.getValue('idCamposObjetoNegocioObjNN1_' + seq, document.form);
			if (idCpNegNN == '' || idCpNegNN == 0){
				alert(i18n_message("visaoAdm.campoNegocioNN"));
				return;
			}
	
			var obj = document.getElementById('idCamposObjetoNegocioPai_' + seq);
			var t1 = obj.options[obj.selectedIndex].text;
			var obj = document.getElementById('idCamposObjetoNegocioObjNN1_' + seq);
			var t2 = obj.options[obj.selectedIndex].text;
			
			HTMLUtils.addOptionIfNotExists('vinculosVisaoPaiNN_' + seq, t1 + ' - ' + t2, idCpNegPai + '#' + idCpNegNN);		
		}
		function addVincObjNNFilho(seq){
			var idCpNegPai = HTMLUtils.getValue('idCamposObjetoNegocioFilho_' + seq, document.form);
			if (idCpNegPai == '' || idCpNegPai == 0){
				alert(i18n_message("visaoAdm.informeCampoNegocioVisaoPai"));
				return;
			}
			var idCpNegNN = HTMLUtils.getValue('idCamposObjetoNegocioObjNN2_' + seq, document.form);
			if (idCpNegNN == '' || idCpNegNN == 0){
				alert(i18n_message("visaoAdm.campoNegocioNN"));
				return;
			}
	
			var obj = document.getElementById('idCamposObjetoNegocioFilho_' + seq);
			var t1 = obj.options[obj.selectedIndex].text;
			var obj = document.getElementById('idCamposObjetoNegocioObjNN2_' + seq);
			var t2 = obj.options[obj.selectedIndex].text;
			
			HTMLUtils.addOptionIfNotExists('vinculosVisaoFilhoNN_' + seq, t1 + ' - ' + t2, idCpNegPai + '#' + idCpNegNN);		
		}
		function remVincObjNNPai(seq){
			var cbo = document.getElementById('vinculosVisaoPaiNN_' + seq);
	    	for(var i = cbo.length -1; i >= 0; i--){
	        	if (cbo.options[i].selected){
			    	cbo.options[i] = null;
	        	}
		    }		
		}
		function remVincObjNNFilho(seq){
			var cbo = document.getElementById('vinculosVisaoFilhoNN_' + seq);
	    	for(var i = cbo.length -1; i >= 0; i--){
	        	if (cbo.options[i].selected){
			    	cbo.options[i] = null;
	        	}
		    }		
		}
		function showMessageScript(msg){
			document.getElementById('divMensagemScript').innerHTML = msg;
			window.setTimeout('ocultaMessageScript()', 5000)
		}
		function ocultaMessageScript(){
			document.getElementById('divMensagemScript').innerHTML = '';
		}
		function showMessageHtmlCode(msg){
			document.getElementById('divMensagemHTMLCode').innerHTML = msg;
			window.setTimeout('ocultaMessageHtmlCode()', 5000)
		}
		function ocultaMessageHtmlCode(){
			document.getElementById('divMensagemHTMLCode').innerHTML = '';
		}	
		function showMessageBotao(msg){
			document.getElementById('divMensagemBotao').innerHTML = msg;
			window.setTimeout('ocultaMessageBotao()', 5000)
		}	
		function ocultaMessageBotao(){
			document.getElementById('divMensagemBotao').innerHTML = '';
		}	
		function recuperaScript(obj){
			document.getElementById('divMensagemScript').innerHTML = '<i18n:message key="visaoAdm.aguardeRecuperandoScript"/>';
			document.formScript.fireEvent('recuperaScript');
		}
		function recuperaHtmlCode(obj){
			document.getElementById('divMensagemHTMLCode').innerHTML = '<i18n:message key="visaoAdm.aguardeRecuperandoHTMLCode"/>';
			document.formHtmlCode.fireEvent('recuperaHtmlCode');
		}
		function recuperaBotao(obj){
			document.getElementById('divMensagemBotao').innerHTML = '<i18n:message key="visaoAdm.aguardeRecuperandoBotaoAcao"/>';
			document.formBotoes.texto.value = obj.value;
			document.formBotoes.fireEvent('recuperaBotao');	
		}
		var infoBotaoSel = -1;
		function guardaInfoBotao(){
			infoBotaoSel = document.formBotoes.botaoCadastrado.selectedIndex;
		}
		function setInfoBotao(){
			document.formBotoes.botaoCadastrado.selectedIndex = infoBotaoSel;
		}	
		function verificaTipoVisao(obj){
			if (obj.value == '<%=VisaoDTO.EXTERNALCLASS%>'){
				document.getElementById('sortable').style.display = 'none';
				document.getElementById('classeExterna').style.display = 'block';
			}else if (obj.value == '<%=VisaoDTO.MATRIZ%>'){
				document.getElementById('sortable').style.display = 'block';
				document.getElementById('matriz').style.display = 'block';
				document.getElementById('classeExterna').style.display = 'none';
			}else{
				document.getElementById('sortable').style.display = 'block';
				document.getElementById('classeExterna').style.display = 'none';
			}
		}
		function exportarXML(){
			var numTabs = '';
			for (var i = 0; i < tab_counter; i++){
				if (document.getElementById('tabsAssociadas-' + i)){
					//alert('tabsAssociadas-' + i);
					numTabs = numTabs + i + ",";
	
					var cbo = document.getElementById('vinculosVisaoPaiNN_' + i);
					if (cbo != null && cbo != undefined){
				    	for(var x = cbo.length -1; x >= 0; x--){
				        	cbo.options[x].selected = true;
					    }		
					}
					var cbo = document.getElementById('vinculosVisaoFilhoNN_' + i);
					if (cbo != null && cbo != undefined){
				    	for(var x = cbo.length -1; x >= 0; x--){
				    		cbo.options[x].selected = true;
					    }				
					}
				}
			}
			
			var result = $('#sortable').sortable('toArray');
			var ordemStr = '';
			for(var i = 0; i < result.length; i++){
				ordemStr = ordemStr + StringUtils.onlyNumbers(result[i]) + ',';
			}	
			document.form.ordemCampos.value = ordemStr;
			document.form.numTabs.value = numTabs;	
			document.form.fireEvent('exportXML');
		}
		function importarVisoesXML(){
			if (!confirm(i18n_message("visaoAdm.importarVisoesMensagem")+" "+i18n_message("visaoAdm.importarVisoesMensagemCont"))){
	            return;
	        }
			document.form.fireEvent('importarVisoesXML');
		}
		
		function importarTodasVisoesXML() {
			desabilita();
			document.form.fireEvent('importarTodasVisoesXML');
		}
				
		function mostrarImportarVisoesXML(){
			if (!confirm(i18n_message("visaoAdm.mostrarImportarVisoes"))){
				return;
			}
			$( "#POPUP_IMPORTARVISOES" ).dialog( 'open' );
			uploadAnexos.refresh();
		}
		
		function mostrarImportarTodasVisoesXML() {
			document.getElementById("listaTodasVisoesTb").innerHTML="";
			$( "#POPUP_IMPORTARTODASVISOES" ).dialog( 'open' );
		}
		
		function exportarVisoesXML(){
			if (!confirm(i18n_message("visaoAdm.desejaContinuarExportacaoArquivoSelecionado"))){
	            return;
	        }
			exportarSelecionados();
		}
		
		function mostrarExportarVisoesXML(){
			$( "#POPUP_EXPORTARVISOES" ).dialog( 'open' );
			document.form.fireEvent('listaVisoesTb');
		}
						
		function ordenaBaixo(){
			var obj = document.formBotoes.botaoCadastrado;
			var v = '';
			var t = '';
			var v2 = '';
			var t2 = '';
			if (obj.selectedIndex > 0){
				v = obj[obj.selectedIndex].value;
				t = obj[obj.selectedIndex].text;
				
				v2 = obj[obj.selectedIndex - 1].value;
				t2 = obj[obj.selectedIndex - 1].text;
				
				obj[obj.selectedIndex].value = v2;		
				obj[obj.selectedIndex].text = t2;
				
				obj[obj.selectedIndex - 1].value = v;
				obj[obj.selectedIndex - 1].text = t;
				
				obj[obj.selectedIndex - 1].selected = true;
			}
			var valores = '';
			for(var i = 0; i < obj.options.length; i++){
				if (valores != ''){
					valores =  valores + ',';
				}
				valores = valores + obj.options[i].value;
			}		
			document.formBotoes.ordemBotoes.value = valores;
			document.getElementById('divMensagemBotao').innerHTML = '<i18n:message key="visaoAdm.aguardeOrdenando"/>';
			document.formBotoes.fireEvent('setaOrdemBotoes');
		}
		function ordenaCima(){
			var obj = document.formBotoes.botaoCadastrado;
			var v = '';
			var t = '';
			var v2 = '';
			var t2 = '';
			if (obj.selectedIndex < obj.options.length){
				v = obj[obj.selectedIndex].value;
				t = obj[obj.selectedIndex].text;
				
				v2 = obj[obj.selectedIndex + 1].value;
				t2 = obj[obj.selectedIndex + 1].text;
				
				obj[obj.selectedIndex].value = v2;		
				obj[obj.selectedIndex].text = t2;
				
				obj[obj.selectedIndex + 1].value = v;
				obj[obj.selectedIndex + 1].text = t;
				
				obj[obj.selectedIndex + 1].selected = true;
			}
			var valores = '';
			for(var i = 0; i < obj.options.length; i++){
				if (valores != ''){
					valores =  valores + ',';
				}
				valores = valores + obj.options[i].value;
			}		
			document.formBotoes.ordemBotoes.value = valores;
			document.getElementById('divMensagemBotao').innerHTML = '<i18n:message key="visaoAdm.aguardeOrdenando"/>';
			document.formBotoes.fireEvent('setaOrdemBotoes');			
		}
		function verificaBrancos(obj){
			var str = obj.value;
			var retorno = '';
			for(var i = 0; i < str.length; i++){
				if (str.charAt(i) != ' '){
				    if (str.charAt(i) != '@' && str.charAt(i) != '#' && str.charAt(i) != '$' && str.charAt(i) != '%'
				    	&& str.charAt(i) != '*' && str.charAt(i) != '&' && str.charAt(i) != '!' && str.charAt(i) != '('
				    	&& str.charAt(i) != ')' && str.charAt(i) != '+' && str.charAt(i) != '-' && str.charAt(i) != '='
				    	&& str.charAt(i) != 'ç' && str.charAt(i) != 'ã' && str.charAt(i) != 'á' && str.charAt(i) != 'õ'
				    	&& str.charAt(i) != 'ó' && str.charAt(i) != 'ú' && str.charAt(i) != 'ê' && str.charAt(i) != 'é'){
				        retorno = retorno + str.charAt(i);
				    }else{
				    	retorno = retorno + '_';
				    }
				}else{
					retorno = retorno + '_';
				}
			}
			obj.value = retorno;
		}
		function selecionaObjetoNegocioMatriz(obj){
			document.form.fireEvent('listarCamposObjNegocioMatriz');
		}
		
		/*Exporta visões*/
		function exportarSelecionados() {
			var tabela = document.getElementById('tbVisoes');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var visoesList = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idVisao' + i);	
				if (!trObj) {
					continue;
				}	
				visoesList[contadorAux] = getVisoes(i);
				contadorAux = contadorAux + 1;
			}
			serializaVisoes();
			document.formExport.fireEvent("exportarVisoesXML");
		}
		
		var seqBaseline = '';
		var aux = '';
		serializaVisoes = function() {
			var tabela = document.getElementById('tbVisoes');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var visoesList = new Array();
			for ( var i = 0; i <= count; i++) {
				var trObj = document.getElementById('idVisaoCheck' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					visoesList[contadorAux] = getVisoes(i);
					contadorAux = contadorAux + 1;
					continue;
				}	
				
			}
			var visoesSerializadas = ObjectUtils.serializeObjects(visoesList);
			document.formExport.visoesSerializadas.value = visoesSerializadas;
			return true;
		}

		getVisoes = function(seq) {
			var VisaoDTO = new CIT_VisaoDTO();
			VisaoDTO.sequencia = seq;
			VisaoDTO.idVisao = eval('document.formExport.idVisao' + seq + '.value');
			return VisaoDTO;
		}
		
		function marcarTodosCheckbox() {
			var itens = document.formExport.idVisaoCheck;
			var marcar = document.formExport.marcarTodos;
			for ( var i = 0; i < itens.length; i++) {
				if (marcar.checked) {
					if (!itens[i].checked) {
						itens[i].checked = true;
					}
				} else {
					itens[i].checked = false;
				}
			}
		}
		
		function marcarCheck(count){
			var item = document.formExport.idVisaoCheck;
			if(item[count].checked){
				item[count].checked = false;	
			}else{
				item[count].checked = true;
			}
		}
		
		function desabilita() {
			$('#concluir').attr('disabled', true);
			$('#Throbber').css('visibility','visible');
		}
		function habilita() {
			$('#concluir').attr('disabled', false);
			$('#Throbber').css('visibility','hidden');
		}
		
	</script>
	
</head>
<body>	
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
					<h2><i18n:message key='citcorpore.comum.visao'/></h2>						
			</div>
			
			<div id="tabs" class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li style="float: left !important;">
						<a href="#tabs-1"><i18n:message key='visaoAdm.cadastroVisao'/></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top"><i18n:message key='visaoAdm.pesquisaVisao'/></a>
					</li>
				</ul>				
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<form name='form' method="POST" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/visaoAdm/visaoAdm'>
							<input type='hidden' name='idVisao' class='Description[Identificação da Visão]'/>
							<input type='hidden' name='idVisaoRel' class='Description[Identificação da Visão]'/>
							<input type='hidden' name='ordemCampos' class='Description[Ordem dos campos]'/>	
							<input type='hidden' name='numTabs' class='Description[Numero de Tabs]'/>
							<input type='hidden' name='nomeCombo' />
							<input type='hidden' name='seq' />
							<input type='hidden' name='idObjetoNegocio' id='idObjetoNegocioAux'/>
							<div class="section">
								<div id='init' style='text-align: center;'>
									<table>
										<tr>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='gravar();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/Save-icon.png' border='0'/><span><i18n:message key='projeto.salvar'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='showScripts();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/forms/script_lightning.png' border='0'/><span>Scripts</span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='showBotoes();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/forms/button_lightning.png' border='0'/><span><i18n:message key='dinamicview.botoesacao'/></span>
												</button>
											</td>	
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='showHTMLCode();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/forms/stock_form_image_html.png' border='0'/><span><i18n:message key='visaoAdm.HTMLCodeAdicional'/></span>
												</button>
											</td>																	
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='limparTela();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/template_new/images/icons/small/grey/clear.png' border='0'/><span><i18n:message key='citcorpore.ui.botao.rotulo.Limpar'/></span>
												</button>
											</td>	
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='excluirVisao();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/template_new/images/icons/small/grey/trashcan.png' border='0'/><span><i18n:message key='itemRequisicaoProduto.excluir'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>	
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>									
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='mostrarExportarVisoesXML();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/forms/exportar.png' border='0'/><span><i18n:message key='visaoAdm.exportarVisoes'/> XML</span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>	
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='mostrarImportarVisoesXML();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/forms/importar.png' border='0'/><span><i18n:message key='visaoAdm.importarVisoes'/> XML</span>
												</button>
											</td>
											<td style='vertical-align: middle;'>
												<button type='button' class="light" onclick='mostrarImportarTodasVisoesXML();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/xml.png' border='0'/><span><i18n:message key='visaoAdm.importarTodasVisoes'/> XML</span>
												</button>
											</td>
										</tr>
									</table>
									<table>
										<tr>
											<td>
												<label class="campoObrigatorio"><i18n:message key='calendario.descricao'/>: </label>
											</td>
											<td>
												<input type='text' name='descricao' size="80" maxlength="120" class='Valid[Required] Description[calendario.descricao]'/>
											</td>
										</tr>
										<tr>
											<td>
												<label class="campoObrigatorio"><i18n:message key='citSmart.comum.identificador'/>: </label>
											</td>
											<td style='text-align: left;'>
												<input type='text' name='identificador' size="50" maxlength="50" class='Valid[Required] Description[citSmart.comum.identificador]' onblur='verificaBrancos(this)'/>
											</td>
										</tr>
										<tr>
											<td>
												<label class="campoObrigatorio"><i18n:message key='visaoAdm.tipoVisao'/>: </label>
											</td>
											<td>
												<select name='tipoVisao' class='Valid[Required] Description[visaoAdm.tipoVisao]' onchange='verificaTipoVisao(this)'>
													<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
													<option value='<%=VisaoDTO.EXTERNALCLASS%>'><i18n:message key='visaoAdm.classeExterna'/></option>
													<option value='<%=VisaoDTO.EDIT%>'><i18n:message key='visaoAdm.edicao'/></option>
													<option value='<%=VisaoDTO.TABLEEDIT%>'><i18n:message key='visaoAdm.listagemTabelaPermitindoEdicao'/></option>
													<option value='<%=VisaoDTO.TABLESEARCH%>'><i18n:message key='visaoAdm.tabelaBusca'/></option>
													<option value='<%=VisaoDTO.MATRIZ%>'><i18n:message key='visaoAdm.matriz'/></option>
												</select>
											</td>
										</tr>	
										<tr>
											<td>
												<label class="campoObrigatorio"><i18n:message key='lookup.situacaoVisao'/>: </label>
											</td>
											<td>
												<input type='radio' name='situacaoVisao' value='A' class='Valid[Required] Description[lookup.situacaoVisao]'/><i18n:message key='planoMelhoria.situacao.ativo'/>
												<input type='radio' name='situacaoVisao' value='I' class='Valid[Required] Description[lookup.situacaoVisao]'/><i18n:message key='requisitosla.inativo'/>
											</td>
										</tr>															
									</table>
									<table width="100%">
										<tr>
											<td>
												<button type='button' class="light" onclick='mostraAddObj();'>
													<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/Button-Add-icon.png' border='0'/><span><i18n:message key='citcorpore.comum.adicionarCampo'/></span>
												</button>
											</td>
											<td>
												&nbsp;
											</td>
											<td align="right" style="text-align: right;">
												<span style='color: red; font-size: 8'><i18n:message key='visaoAdm.atencaoEditarCampoExistenteBastaDuploClique'/></span>
											</td>
										</tr>
									</table>
								</div>
								<div id='matriz' style='text-align: center;display:none'>
									<table>
										<tr>
											<td>
												<i18n:message key='visaoAdm.objetoNegocioBaseMatriz'/>
											</td>
											<td>
												<select name='idObjetoNegocioMatriz' onchange='selecionaObjetoNegocioMatriz(this)'>
													<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
													<%
													for (Iterator it = objetosNegocio.iterator(); it.hasNext();){
														ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
														out.println("<option value='" + objetoNegocioDTO.getIdObjetoNegocio() + "'>" + objetoNegocioDTO.getNomeObjetoNegocio() + "</option>");
													}
													%>
											</select>
										</td>
									</tr>
									<tr>
										<td>
											<i18n:message key='visaoAdm.campoChaveObjetoNegocioBaseMatriz'/>:
										</td>
										<td>
											<select name='idCamposObjetoNegocio1'>
												<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
											</select>
										</td>
										<td>
											<i18n:message key='calendario.descricao'/>:
										</td>
										<td>
											<input type='text' name='descricaoCampo1' size='70' maxlength="120"/>
										</td>
									</tr>
									<tr>
										<td>
											<i18n:message key='visaoAdm.campoApresentacaoUmObjetoNegocioBaseMatriz'/>:
										</td>
										<td>
											<select name='idCamposObjetoNegocio2'>
												<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
											</select>
										</td>
										<td>
											<i18n:message key='calendario.descricao'/>:
										</td>
										<td>
											<input type='text' name='descricaoCampo2' size='70' maxlength="120"/>
										</td>									
									</tr>
									<tr>
										<td>
											<i18n:message key='visaoAdm.campoApresentacaoDoisObjetoNegocioBaseMatriz'/>:
										</td>
										<td>
											<select name='idCamposObjetoNegocio3'>
												<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
											</select>
										</td>
										<td>
											<i18n:message key='calendario.descricao'/>:
										</td>
										<td>
											<input type='text' name='descricaoCampo3' size='70' maxlength="120"/>
										</td>									
									</tr>																								
								</table>							
							</div>
							<div id='sortable' style='text-align: center;'>
							
							</div>
							<div id='classeExterna' style='text-align: center;display:none'>
								<select name='classeName' id='classeName'>
									<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
									<%
									if (CITCorporeUtil.lstExternalClasses != null){
										for (Iterator it = CITCorporeUtil.lstExternalClasses.iterator(); it.hasNext();){
											ExternalClassDTO externalClassDTO = (ExternalClassDTO)it.next();
											out.print("<option value='" + externalClassDTO.getNameClass() + "'>" + externalClassDTO.getNameClass() + " (" + externalClassDTO.getNameJarOriginal() + ")" + "</option>");
										}
									}
									%>
								</select>
							</div>						
							<div id='ctrlTabs' style='text-align: center;'>
								<button name="btnSalvar" type='button' onclick='addTab()'>
									<i18n:message key='visaoAdm.adicionarVisaoVinculada'/>
								</button>							
							</div>
							<br><br>
							<div id='tabsAssociadas' style='text-align: center;'>
								<ul>
									<li><a href="#tabsAssociadas-1"><i18n:message key='visaoAdm.visaoRelacionada'/></a> <span class="ui-icon ui-icon-close">Remove Tab</span></li>
								</ul>
								<div id="tabsAssociadas-1">
								</div>							
							</div>
						</div>
						</form>
					</div>
					<div id="tabs-2" class="block">
						<form name='formPesquisa' method="POST">
						<div class="section">
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_VISAO' id='LOOKUP_VISAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</div>
						</form>											
					</div>								
				</div>
			</div>		
		</div>
		
		<div id='divOcultaVisaoRelacionada' style='display:none'>
			<table>
				<tr>
					<td>
						<i18n:message key='visaoAdm.visaoRelacionada'/>:
					</td>
					<td>
						<select name='divVisaoRelacionada_#SEQ#' onchange='mudaCampoVisaoRel(this, #SEQ#)'>
							<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
							<%
							for (Iterator it = relacaoVisoes.iterator(); it.hasNext();){
								VisaoDTO visaoDTO = (VisaoDTO)it.next();
								out.println("<option value='" + visaoDTO.getIdVisao() + "'>" + visaoDTO.getDescricao() + "</option>");
							}
							%>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<i18n:message key='notificacao.titulo'/>:
					</td>
					<td style='text-align: left;'>
						<input type='text' name='titulo_#SEQ#' size="80" maxlength="500" class='Description[notificacao.titulo]'/>
					</td>
				</tr>
				<tr>
					<td>
						<i18n:message key='visaoAdm.tipoVinculacao'/>:
					</td>
					<td>
						<select name='tipoVinculacao_#SEQ#'>
							<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
							<option value='1'><i18n:message key='visaoAdm.abaAoLado'/></option>
							<option value='2'><i18n:message key='visaoAdm.abaFilhaEmBaixo'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<i18n:message key='visaoAdm.acaoCasoVisaoPesquisa'/>:
					</td>
					<td>
						<select name='acaoEmSelecaoPesquisa_#SEQ#'>
							<option value=''><i18n:message key='citcorpore.comum.nenhuma'/></option>
							<option value='<%=VisaoRelacionadaDTO.ACAO_RECUPERAR_PRINCIPAL%>'><i18n:message key='visaoAdm.recuperarRegistroPrincipal'/></option>
							<option value='<%=VisaoRelacionadaDTO.ACAO_RECUPERAR_REGISTROS_VINCULADOS%>'><i18n:message key='visaoAdm.recuperarRegistroVinculado'/></option>
						</select>
					</td>
				</tr>	
				<tr>
					<td>
						<i18n:message key='visaoAdm.situacaoVinculacao'/>:
					</td>
					<td style='text-align: left;'>
						<input type='radio' name='situacaoVisaoVinculada_#SEQ#' value='A' class='Description[lookup.situacaoVisao]'/> <i18n:message key='categoriaProduto.categoria_ativo'/>
						<input type='radio' name='situacaoVisaoVinculada_#SEQ#' value='I' class='Description[lookup.situacaoVisao]'/> <i18n:message key='categoriaProduto.categoria_inativo'/>
					</td>
				</tr>
				<tr>
					<td>
						<i18n:message key='visaoAdm.vinculoEntreVisoes'/>:
					</td>
					<td>
						<select name='tipoVinculo_#SEQ#' id='tipoVinculo_#SEQ#' onchange="mudaTipoVinculo(this, '#SEQ#', true)">
							<option value=''><i18n:message key='citcorpore.comum.nenhum'/></option>
							<option value='1'><i18n:message key='visaoAdm.relacionamentoUmParaN'/></option>
							<option value='2'><i18n:message key='visaoAdm.relacionamentoNParaN'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<div id='divLabelVinculoVisao1_#SEQ#' style='display:none'>
							<i18n:message key='visaoAdm.objetoNegocioRelacao'/>:
						</div>
					</td>
					<td>
						<div id='divLabelVinculoVisao2_#SEQ#' style='display:none'>
							<select name='idObjetoNegocioNN_#SEQ#' onchange="mudaCampoObjNN(this, '#SEQ#')">
								<option value=''><i18n:message key='citcorpore.comum.nenhum'/></option>
								<%
								for (Iterator it = objetosNegocio.iterator(); it.hasNext();){
									ObjetoNegocioDTO objetoNegocioDTO = (ObjetoNegocioDTO)it.next();
									out.println("<option value='" + objetoNegocioDTO.getIdObjetoNegocio() + "'>" + objetoNegocioDTO.getNomeObjetoNegocio() + "</option>");
								}
								%>							
							</select>						
						</div>
					</td>
				</tr>	
				<tr>
					<td colspan="2">
						<div id='divVinculoVisaoNN1_#SEQ#' style='display:none'>
						<br><br>
						<fieldset>
							<legend><b><i18n:message key='visaoAdm.relacaoObjetoFilhoVisaoPai'/></b></legend>
						<table>
							<tr>
								<td>
									<i18n:message key='visaoAdm.campoNegocioVisaoPai'/>
								</td>
								<td>
									<i18n:message key='visaoAdm.campoNegocioFilho'/>
								</td>							
							</tr>					
							<tr>
								<td>
									<select name='idCamposObjetoNegocioPai_#SEQ#' id='idCamposObjetoNegocioPai_#SEQ#' style='width: 350px'>
										<option value=''><i18n:message key='citcorpore.comum.nenhum'/></option>
									</select>
								</td>
								<td>
									<select name='idCamposObjetoNegocioObjNN1_#SEQ#' id='idCamposObjetoNegocioObjNN1_#SEQ#' style='width: 350px'>
									</select>
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<input type="button" name='btnAddItemVinc' value='<i18n:message key="portal.carrinho.adicionar"/>' onclick='addVincObjNNPai(#SEQ#)'/>
								</td>							
							</tr>
							<tr>
								<td colspan="3">
									<select name='vinculosVisaoPaiNN_#SEQ#' id='vinculosVisaoPaiNN_#SEQ#' multiple="multiple" size="5" style='width: 700px; height: 100px'></select>
								</td>
								<td>
									<input type="button" name='btnRemItemVinc' value='<i18n:message key="dinamicview.remover"/>' onclick='remVincObjNNPai(#SEQ#)'/>
								</td>
							</tr>
						</table>
						</fieldset>
						</div>
					</td>
				</tr>					
				<tr>
					<td colspan="2">
						<div id='divVinculoVisaoNN2_#SEQ#' style='display:none'>
						<br><br>
						<fieldset>
							<legend><b><i18n:message key='visaoAdm.relacaoObjetoComVisaoFilha'/></b></legend>
						<table>
							<tr>
								<td>
									<i18n:message key='visaoAdm.campoNegocioVisaoFilha'/>
								</td>
								<td>
									<i18n:message key='visaoAdm.campoNegocio'/>
								</td>							
							</tr>					
							<tr>
								<td>
									<select name='idCamposObjetoNegocioFilho_#SEQ#' id='idCamposObjetoNegocioFilho_#SEQ#' style='width: 350px'>
										<option value=''><i18n:message key='citcorpore.comum.nenhum'/></option>
									</select>
								</td>
								<td>
									<select name='idCamposObjetoNegocioObjNN2_#SEQ#' id='idCamposObjetoNegocioObjNN2_#SEQ#' style='width: 350px'>
									</select>
								</td>
								<td>
									&nbsp;
								</td>
								<td>
									<input type="button" name='btnAddItemVinc' value='<i18n:message key="portal.carrinho.adicionar"/>' onclick='addVincObjNNFilho(#SEQ#)'/>
								</td>							
							</tr>
							<tr>
								<td colspan="3">
									<select name='vinculosVisaoFilhoNN_#SEQ#' id='vinculosVisaoFilhoNN_#SEQ#' multiple="multiple" size="5" style='width: 700px; height: 100px'></select>
								</td>
								<td>
									<input type="button" name='btnRemItemVinc' value='Remover' onclick='remVincObjNNFilho(#SEQ#)'/>
								</td>							
							</tr>
						</table>
						</fieldset>
						</div>
					</td>
				</tr>
			</table>
		</div>
		
		<div id="POPUP_OBJ" style='width: 600px; height: 600px' >
			<form name='formItem' method="POST" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/visaoAdm/visaoAdm'>
			<input type='hidden' name='numeroEdicao' class='Description[Número]'/>
			<table>
				<tr>
					<td>
						<i18n:message key='visaoAdm.objetoNegocio'/>:
					</td>
					<td>
						<select name='idObjetoNegocio' onchange='selecionaObjNegocio(this)' class='Valid[Required] Description[Objeto de Negócio]'>
						</select>
					</td>
				</tr>	
				<tr>
					<td>
						<i18n:message key='visaoAdm.tipoCampo'/>:
					</td>
					<td>
						<select name='tipoNegocio' onchange='selecionaCampoObjNegocio(this)' class='Valid[Required] Description[Tipo de Campo]'>
							<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
							<option value='SELECT'><i18n:message key='visaoAdm.caixaSelecao'/></option>
							<option value='HIDDEN'><i18n:message key='visaoAdm.controleEscondido'/></option>
							<option value='DATE'><i18n:message key='avaliacaoFonecedor.dataAvaliacao'/></option>
							<option value='HTML'>HTML Code</option>
							<option value='DECIMAL'><i18n:message key='visaoAdm.moedaDecimal'/></option>
							<option value='NUMBER'><i18n:message key='citcorpore.comum.numero'/></option>
							<option value='RADIO'><i18n:message key='visaoAdm.radio'/></option>
							<option value='RELATION'><i18n:message key='visaoAdm.relacionamentoOutroObjetoNegocio'/></option>
							<option value='CLASS'><i18n:message key='visaoAdm.retornoExecucaoClasseMetodo'/></option>
							<option value='TEXT'><i18n:message key='modeloemail.texto'/></option>
							<option value='TEXTAREA'><i18n:message key='visaoAdm.textoLongo'/></option>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<i18n:message key='visaoAdm.campoObjetoNegocio'/>:
					</td>
					<td>
						<select name='idCamposObjetoNegocio' onchange='selecionaCampoObjNegocio(this)' class='Valid[Required] Description[visaoAdm.campoObjetoNegocio]'>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<i18n:message key='solicitacaoServico.descricao'/>:
					</td>
					<td>
						<input type='text' name='descricaoNegocio' size="80" maxlength="500" class='Valid[Required] Description[solicitacaoServico.descricao]'/>
					</td>
				</tr>
				<tr>
					<td>
						<i18n:message key='questionario.tamanho'/>:
					</td>
					<td>
						<input type='text' name='tamanho' size="4" maxlength="4"/>
					</td>
				</tr>				
				<tr>
					<td>
						<i18n:message key='citcorpore.comum.obrigatorio'/>:
					</td>
					<td>
						<input type='radio' name='obrigatorio' value='S' class='Valid[Required] Description[Obrigatório]'/> <i18n:message key='citcorpore.comum.sim'/>
						<input type='radio' name='obrigatorio' value='N' class='Valid[Required] Description[Obrigatório]'/> <i18n:message key='citcorpore.comum.nao'/>
					</td>
				</tr>		
				<tr>
					<td colspan="2">
						<div id='divCampoRelacao' style='display: none; border:1px solid black;'>
							<table>
								<tr>
									<td>
										<i18n:message key='visaoAdm.descricaoParaRelacionamentoto'/>:
									</td>
									<td>
										<input type='text' name='descricaoRelacionamento' size="70" maxlength="70"/>
									</td>
								</tr>					
								<tr>
									<td>
										<i18n:message key='visaoAdm.relacionamentoObjetoNegocio'/>:
									</td>
									<td>
										<select name='idObjetoNegocioLigacao' onchange='selecionaObjNegocioLigacao(this)' class='Description[visaoAdm.relacionamentoObjetoNegocio]'>
										</select>							
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key='visaoAdm.campoObjetoNegocioApresentacaoRelacionamento'/>:
									</td>
									<td>
										<select name='idCamposObjetoNegocioLigacao' class='Description[visaoAdm.campoObjetoNegocioApresentacaoRelacionamento]'>
										</select>							
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key='visaoAdm.campoObjetoNegocioLigacaoRelacionamento'/>:
									</td>
									<td>
										<select name='idCamposObjetoNegocioLigacaoVinc' class='Description[Campo do Objeto de Negócio de Ligação]'>
										</select>							
									</td>
								</tr>
								<tr>
									<td>
											<i18n:message key='visaoAdm.campoObjetoNegocioOrdemRelacionamento'/>:
									</td>
									<td>
										<select name='idCamposObjetoNegocioLigacaoOrder' class='Description[visaoAdm.campoObjetoNegocioOrdemRelacionamento]'>
										</select>							
									</td>
								</tr>	
								<tr>
									<td>
										<i18n:message key='visaoAdm.tipoCampoAprensentar'/>:
									</td>
									<td>
										<select name='tipoLigacao' class='Description[visaoAdm.tipoCampoAprensentar]'>
											<option value='N'><i18n:message key='citcorpore.comum.padrao'/></option>
											<option value='C'>COMBO</option>
											<option value='S'>LOOKUP</option>
										</select>							
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key='visaoAdm.filtroAdicional'/>:
									</td>		
									<td>
										<textarea rows="3" cols="80" name="filtro" id="filtro" style='border:1px solid black;'></textarea>
									</td>					
								</tr>																								
							</table>
						</div>
						<div id='divCampoNumeroDecimais' style='display: none; border:1px solid black;'>
							<table>
								<tr>
									<td>
										<i18n:message key='questionario.decimais'/>:
									</td>
									<td>
										<input type='text' name='decimais' size="4" maxlength="4"/>
									</td>
								</tr>						
							</table>
						</div>
						<div id='divCampoRadioSelect' style='display: none; border:1px solid black;'>
							<table>
								<tr>
									<td>
										<i18n:message key='visaoAdm.valorOpcao'/>:
									</td>
									<td>
										<input type='text' name='valorOpcao' size="70" maxlength="100"/>
									</td>
									<td>
										&nbsp;
									</td>
								</tr>
								<tr>
									<td>
										<i18n:message key='visaoAdm.descricaoOpcao'/>:
									</td>
									<td>
										<input type='text' name='descricaoOpcao' size="70" maxlength="100"/>
									</td>
									<td>
										<input type='button' name='btnAddOpt' value='<i18n:message key="visaoAdm.adicionarOpcao"/>' onclick='addOpcao()'/>
									</td>
								</tr>	
								<tr>
									<td colspan="2">
										<select size='10' style='width: 400px; height: 100px' name='valoresOpcoes' multiple="multiple"></select>
									</td>
									<td>
										<input type='button' name='btnRemOpt' value='<i18n:message key="visaoAdm.removerOpcao"/>' onclick='removeOpcao()'/>
									</td>
								</tr>					
							</table>
						</div>
						<div id='divCampoHTMLCode' style='display: none; border:1px solid black;'>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td style="vertical-align: middle;">
										HTML Code:
									</td>
									<td>
										<textarea name="htmlCode" id="htmlCode" rows="5" cols="90" class='Description[HTML Code]' style='border:1px solid black;'></textarea>
									</td>
								</tr>						
							</table>
						</div>
						<div id='divCampoClass' style='display: none; border:1px solid black;'>
							<i18n:message key="visaoAdm.atencaoMetodoDeveRetornarColecao"/>
							<table cellpadding="0" cellspacing="0">
								<tr>
									<td style="vertical-align: middle;">
										<i18n:message key="visaoAdm.acrescenteInformacoesAbaixoCampoFormula"/>:<br>
										<b><i18n:message key='visaoAdm.classe'/></b>, <b><i18n:message key='visaoAdm.metodoExecutar'/></b>, <b><i18n:message key="visaoAdm.identificadorCombo"/></b>, <b><i18n:message key="visaoAdm.atributoDescricaoCombo"/></b> (<i18n:message key="visaoAdm.devemSeperadosVirgulo"/>):
									</td>
								</tr>
							</table>
						</div>								
					</td>
				</tr>
				<tr>
					<td style="vertical-align: middle;">
						<i18n:message key='requisitosla.formula'/>:
					</td>
					<td>
						<textarea name="formula" rows="5" cols="90" class='Description[Fórmula]' style='border:1px solid black;'></textarea>
					</td>
				</tr>				
				<tr>
					<td>
						<i18n:message key='projeto.situacao'/>:
					</td>
					<td>
						<input type='radio' name='situacao' value='A' class='Valid[Required] Description[Situação]'/> <i18n:message key='citcorpore.comum.ativo'/>
						<input type='radio' name='situacao' value='I' class='Valid[Required] Description[Situação]'/> <i18n:message key='citcorpore.comum.inativo'/>
					</td>
				</tr>	
				<tr>
					<td colspan="2">
						&nbsp;
					</td>
				</tr>			
				<tr>
					<td colspan="2">
						<table>
							<tr>
								<td>
									<button name="btnSalvar" type='button' onclick='adicionaAtualizaItem()'>OK</button>
								</td>
								<td>
									<button name="btnExcluir" type='button' onclick='excluir()'><i18n:message key='visaoAdm.excluirCampo'/></button>
								</td>						
								<td>
									<button name="btnFechar" type='button' onclick='fechar()'><i18n:message key='citSmart.comum.fechar'/></button>
								</td>						
							</tr>
						</table>
					</td>
				</tr>
			</table>
			</form>
		</div>
		
		<div id="POPUP_SCRIPT" style='width: 750px; height: 600px' >
			<form name='formScript' method="POST" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/visaoAdm/visaoAdm'>
			<table>
				<tr>
					<td>
						<select name='scryptType' onchange='recuperaScript(this)' class='Valid[Required] Description[Tipo de Script]' size="20" style='height: 500px'>
							<optgroup label='<i18n:message key="citcorpore.controleContrato.cliente"/>' style='background-color: yellow; color: red'></optgroup>
							<%
							for (ScriptEventDTO scriptEvt : ScriptsVisaoDTO.colScriptEvents){
								if (!scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_ONSQLSEARCH.getName())
										&& !scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_ONSQLWHERESEARCH.getName())
										&& !scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_AFTERCREATE.getName())
										&& !scriptEvt.getName().equalsIgnoreCase(ScriptsVisaoDTO.SCRIPT_AFTERUPDATE.getName())){
									out.println("<option value='" + ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#" + scriptEvt.getName() + "'>"+UtilI18N.internacionaliza(request, scriptEvt.getDescription())+"</option>");
								}
							}
							%>
							<optgroup label='<i18n:message key="mapaDesenhoServico.servidor"/>' style='background-color: yellow; color: red'></optgroup>
							<%
							for (ScriptEventDTO scriptEvt : ScriptsVisaoDTO.colScriptEvents){
								out.println("<option value='" + ScriptsVisaoDTO.SCRIPT_EXECUTE_SERVER + "#" + scriptEvt.getName() + "'>"+UtilI18N.internacionaliza(request, scriptEvt.getDescription())+"</option>");
							}					
							%>
						</select>
					</td>
					<td style='vertical-align: top;'>
						<textarea rows="27" cols="80" name="script" id="script" style='border: 1px solid black; width: 100%; font-family: "Courier New"'></textarea>
					</td>
				</tr>	
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table>
							<tr>
								<td>
									<button name="btnSalvarScript" type='button' onclick='adicionaAtualizaScript()'><i18n:message key='visaoAdm.atualizaScript'/></button>
								</td>
								<td>
									<button name="btnFecharScript" type='button' onclick='fecharScript()'><i18n:message key='citSmart.comum.fechar'/></button>
								</td>	
								<td>
									&nbsp;&nbsp;
								</td>			
								<td>
									<div id='divMensagemScript' style='background-color: yellow; color: red'>
									</div>
								</td>		
							</tr>
						</table>
					</td>
				</tr>		
			</table>
			</form>
		</div>
		
		<div id="POPUP_HTMLCODE" style='width: 750px; height: 600px' >
			<form name='formHtmlCode' method="POST" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/visaoAdm/visaoAdm'>
			<table>
				<tr>
					<td>
						<select name='htmlCodeType' onchange='recuperaHtmlCode(this)' class='Valid[Required] Description[Tipo de HTML Code]' size="20" style='height: 500px'>
							<%
							for (HtmlCodePartDTO htmlCodePart : HtmlCodeVisaoDTO.colHtmlCodeParts){
								out.println("<option value='" + htmlCodePart.getName() + "'>" +UtilI18N.internacionaliza(request,htmlCodePart.getDescription()) + "</option>");
							}					
							%>
						</select>
					</td>
					<td style='vertical-align: top;'>
						<textarea rows="27" cols="84" name="htmlCode" id="htmlCodePopupHtmlCode" style='border: 1px solid black; width: 100%; font-family: "Courier New"'></textarea>
					</td>
				</tr>	
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table>
							<tr>
								<td>
									<button name="btnSalvarHTMLCode" type='button' onclick='adicionaAtualizaHTMLCode()'><i18n:message key='visaoAdm.atualizaHTMLCode'/></button>
								</td>
								<td>
									<button name="btnFecharHTMLCode" type='button' onclick='fecharHTMLCode()'><i18n:message key='citSmart.comum.fechar'/></button>
								</td>	
								<td>
									&nbsp;&nbsp;
								</td>			
								<td>
									<div id='divMensagemHTMLCode' style='background-color: yellow; color: red'>
									</div>
								</td>		
							</tr>
						</table>
					</td>
				</tr>		
			</table>
			</form>
		</div>
	
		<div id="POPUP_BOTOES" style='width: 800px; height: 600px' >
			<form name='formBotoes' method="POST" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/visaoAdm/visaoAdm'>
			<input type='hidden' name='ordemBotoes' value=''/>
			<table>
				<tr>
					<td>
						<table border="0" cellpadding="0" cellspacing="0">
							<tr>
								<td>
									<select name='botaoCadastrado' onchange='recuperaBotao(this)' size="2" style='height: 250px; width: 120px'>
									</select>
								</td>
							</tr>
							<tr>
								<td style='text-align: center'>
									<table>
										<tr>
											<td style='border:1px solid black; cursor: pointer' onclick='ordenaCima();'>
												<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/forms/ordena.gif' border='0'/>
											</td>
											<td>
												&nbsp;
											</td>
											<td>
												&nbsp;
											</td>
											<td style='border:1px solid black; cursor: pointer' onclick='ordenaBaixo();'>
												<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/forms/ordena2.gif' border='0'/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</td>
					<td style='vertical-align: top; border: 1px solid black' valign="top">
						<table>
							<tr>
								<td>
									<i18n:message key='solicitacaoServico.acao'/>:
								</td>
								<td>
									<select name='acao' onchange='selecionaAcaoBotao(this)' class='Valid[Required] Description[solicitacaoServico.acao]'>
										<option value=''><i18n:message key='citcorpore.comum.selecione'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_GRAVAR%>'><i18n:message key='portal.gravarDados'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_LIMPAR%>'><i18n:message key='portal.limparDados'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_EXCLUIR%>'><i18n:message key='itemRequisicaoProduto.excluir'/></option>
										<option value='<%=BotaoAcaoVisaoDTO.ACAO_SCRIPT%>'><i18n:message key='visaoAdm.executarScript'/></option>
									</select>
								</td>
							</tr>				
							<tr>
								<td>
									<i18n:message key='modeloemail.texto'/>:
								</td>
								<td>
									<input type='text' name='texto' size="80" maxlength="100" class='Valid[Required] Description[modeloemail.texto]'/>
								</td>
							</tr>
							<tr>
								<td style='vertical-align: middle;' valign="middle">
									Script:
								</td>
								<td>
									<textarea rows="10" cols="70" name="script" id="scriptBotao" style='border: 1px solid black; font-family: "Courier New"'></textarea>
								</td>
							</tr>
							<tr>
								<td style='vertical-align: middle;' valign="middle">
									<i18n:message key='visaoAdm.mensagemMovimentar'/><br><i18n:message key='visaoAdm.mouseSobreBotão'/>:
								</td>
								<td>
									<input type='text' name='hint' size="80" maxlength="100"/>
								</td>
							</tr>										
						</table>
					</td>
				</tr>	
				<tr>
					<td>
						&nbsp;
					</td>
					<td>
						<table>
							<tr>
								<td>
									<button name="btnSalvarScript" type='button' onclick='adicionaAtualizaBotao()'>OK</button>
								</td>
								<td>
									&nbsp;&nbsp;
								</td>
								<td>
									<button name="btnExcluirScript" type='button' onclick='excluirBotao()'><i18n:message key='itemRequisicaoProduto.excluir'/></button>
								</td>	
								<td>
									&nbsp;&nbsp;
								</td>																	
								<td>
									<button name="btnSalvarScript" type='button' onclick='limparBotao()'><i18n:message key='citcorpore.ui.botao.rotulo.Limpar'/></button>
								</td>						
								<td>
									<button name="btnFecharScript" type='button' onclick='fecharBotao()'><i18n:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
								</td>	
								<td>
									&nbsp;&nbsp;
								</td>			
								<td>
									<div id='divMensagemBotao' style='background-color: yellow; color: red'>
									</div>
								</td>		
							</tr>
						</table>
					</td>
				</tr>		
			</table>
			</form>
		</div>
		<!-- Pop de Importação Antiga
		<div id="POPUP_IMPORTAR" style='width: 800px; height: 600px' >
		    <form name='formImportar' method='post' ENCTYPE="multipart/form-data" action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/visaoAdm/visaoAdm.load'>
				<input type='hidden' name='acao' id='acaoImportar' value='importar'/>
		        <table width="100%">
		            <tr>
		                <td>
		                   Selecione o arquivo a importar:*
		                </td>
		                <td>
		                   <input type='file' name='fileImportar' size="50"/>
		                </td>
		            </tr>
		            <tr>
		                <td colspan="2">
		                	&nbsp;
		                </td>
		            </tr>
		            <tr>
		                <td colspan="2">
		                	<button name="btnOKImportar" type='button' onclick='importarXML()'>OK</button>
		                </td>
		            </tr>
		        </table>
		    </form>
		</div> -->
		
		<!-- Pop de Exportação Nova -->
		<div id="POPUP_EXPORTARVISOES" style='display:none'>		
			<form name="formExport" method='post' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/visaoAdm/visaoAdm.load'>
               	<button name="btnExportarOK" type='button' onclick='exportarVisoesXML()'><i18n:message key='citcorpore.comum.exportarArquivo'/></button>
				<button id='btnFechaExportacoes' name='btnFechaExportacoes' type="button"><i18n:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
				<input type='hidden' id='visoesSerializadas' name='visoesSerializadas'/>
				<div>
					<div id="listaVisoesTb"></div>
				</div>
			</form>
		</div>
		<!-- Pop de Importação Nova -->
		<div id="POPUP_IMPORTARVISOES" style='display:none'>		
			<form name="formUpload" method="post" enctype="multipart/form-data">
				<cit:uploadControl style="height:100px;width:100%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>			
               	<button name="btnImportarOK" type='button' onclick='importarVisoesXML()'><i18n:message key='citcorpore.comum.importarArquivo'/></button>
				<button id='btnFechaImportacoes' name='btnFechaImportacoes' type="button"><i18n:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
				<input type='hidden' id='visoesSerializadas' name='visoesSerializadas'/>
			</form>
		</div>
		<!-- Pop de Importação de Todas as Visoes Novas -->
		<div id="POPUP_IMPORTARTODASVISOES" style='display:none'>
			<i18n:message key='visaoAdm.mensagemPopUp'/><i18n:message key='visaoAdm.mensagemPopUpCont'/>
            <div class="barra">
	            <button id="concluir" name="btnImportarTodosOK" type='button' onclick='importarTodasVisoesXML()'><i18n:message key='citcorpore.comum.importarTudo'/></button>
				<button id='btnFechaTodasImportacoes' name='btnFechaTodasImportacoes' type="button"><i18n:message key='citcorpore.ui.botao.rotulo.Fechar'/></button>
				<div id="Throbber" class="throbber"></div>
			</div>			
			<br/>
			<div id="listaTodasVisoesTb"></div>
		</div>
		
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
