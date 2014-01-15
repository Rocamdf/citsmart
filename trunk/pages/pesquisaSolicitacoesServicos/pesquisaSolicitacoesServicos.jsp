<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<%@page import="br.com.centralit.citcorpore.free.Free" %>
<%@page import="java.util.Collection"%>

<!doctype html public "">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
    
    String iframe = "";
    iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
<script type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>


<style type="text/css">
/*  	.table {
		border-left:1px solid #ddd;
	} */
	div.main_container .box {
		border: 0px solid #D8D8D8 !important;
		border-top: none !important;
/*  		margin-top: 0!important;  */
		border-radius: 0 !important;
	}
	select {
		width: 98%!important;
	}
	
 	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
		word-wrap:break-word;
	}
	.labelOverflowTresPontinhos label {text-overflow: ellipsis;overflow: hidden;white-space: nowrap; font-weight: bold;}
	
	.inner{
		margin: 15px;
	}
	
/* 	.table td {
		border:1px solid #ddd !important;
		padding:4px 10px !important;
		border-top:none !important;
		border-left:none !important;
	}   */
<%if (iframe != null) {%>

	div#main_container {
		margin: 10px 10px 10px 10px;  
	
	}
<%}%>
	
	
</style>

<%-- <%//se for chamado por iframe deixa apenas a parte de cadastro da pÃ¡gina
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;  
	
}
</style>

<%}%>
 --%>


<script><!--
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_SOLICITANTE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
		
		$("#POPUP_RESPONSAVEL").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});		

		$("#POPUP_ITEMCONFIG").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_OCORRENCIAS").dialog({
			autoOpen : false,
			width : 800,
			height : 600,	
			modal : true
		});
		
		$("#POPUP_UNIDADE").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_LOCALIDADE").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_menuAnexos").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_SERVICO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	}
	
	function LOOKUP_UNIDADE_SOLICITACAO_select(id, desc) {
		document.form.idUnidade.value = id;
		document.form.nomeUnidade.value = desc.split(" - ")[0];
		$("#POPUP_UNIDADE").dialog("close");		
	}
	function LOOKUP_LOCALIDADE_SOLICITACAO_select(id, desc) {
		document.form.idLocalidade.value = id;
		document.form.nomeLocalidade.value = desc.split(" - ")[0];
		$("#POPUP_LOCALIDADE").dialog("close");		
	}
	function LOOKUP_SOLICITANTE_select(id, desc){
		document.getElementById("idSolicitante").value = id;
		document.getElementById("nomeSolicitante").value = desc;
		$("#POPUP_SOLICITANTE").dialog("close");
	}
	function LOOKUP_SERVICO_select(id, desc){
		document.getElementById("idServico").value = id;
		document.getElementById("servico").value = desc;
		$("#POPUP_SERVICO").dialog("close");
	}
	
	function LOOKUP_RESPONSAVEL_select(id, desc){
		document.getElementById("idResponsavel").value = id;
		document.getElementById("nomeResponsavel").value = desc;
		$("#POPUP_RESPONSAVEL").dialog("close");
	}
	
	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
		document.getElementById("idItemConfiguracao").value = id;
		document.getElementById("nomeItemConfiguracao").value = desc;
		$("#POPUP_ITEMCONFIG").dialog("close");		
	}

	function abrePopupResponsavel(){
		$("#POPUP_RESPONSAVEL").dialog("open");
	}
	
	function abrePopupUsuario(){
		$("#POPUP_SOLICITANTE").dialog("open");
	}
	function abrePopupIC(){
		$("#POPUP_ITEMCONFIG").dialog("open");
	}

	function abrePopupServico(){
		$("#POPUP_SERVICO").dialog("open");
	}
	function inicializarTemporizador(){
		if(temporizador == null){
			temporizador = new Temporizador("imgAtivaTimer");
		} else {
			temporizador = null;
			try{
				temporizador.listaTimersAtivos = [];
			}catch(e){}
			try{
				temporizador.listaTimersAtivos.length = 0;
			}catch(e){}
			temporizador = new Temporizador("imgAtivaTimer");
		}
	}
	
	function paginacao(paginacao){
		
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
		var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
		var dataFimFechamento = document.getElementById("dataFimFechamento").value;
		
		document.form.flag.value = "comPag";
		document.form.paginacao.value = paginacao;
		
		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
			
			}
			
			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					 document.getElementById("dataFim").value = '';
					return false;					
				}
			}
		}
		else {
				if(dataInicioFechamento != "" || dataFimFechamento != ""){
					if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
					 	document.getElementById("dataInicioFechamento").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
						document.getElementById("dataFimFechamento").value = '';
						return false;					
					}
					if (dataInicio != ""){
						if(DateTimeUtil.isValidDate(dataFim) == true){
						if(DateTimeUtil.isValidDate(dataInicio) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 	document.getElementById("dataInicio").value = '';
							return false;	
						}
						}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;	
						}
					}
					
					if (dataFim != ""){
						if(DateTimeUtil.isValidDate(dataInicio) == true){
						if(DateTimeUtil.isValidDate(dataFim) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;					
						}
					}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
							 document.getElementById("dataInicio").value = '';
							return false;	
						}
					}
					
					
					
				}
				else{
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						document.getElementById("dataFim").value = '';
						return false;					
					}
					
				}
		}
			
		if(dataInicioFechamento != "" || dataFimFechamento != ""){
			if(validaData(dataInicioFechamento,dataFimFechamento)){
				if(validaData(dataInicio,dataFim)){
					inicializarTemporizador();
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("preencheSolicitacoesRelacionadas");
				}
			}
		}
		else{
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("preencheSolicitacoesRelacionadas");
			}
			
		}
		
	}
			
	function filtrar(){	
		
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = $.trim(document.getElementById("idSolicitacaoServicoPesquisa").value);
		document.getElementById("idSolicitacaoServicoPesquisa").value = numero;
		var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
		var dataFimFechamento = document.getElementById("dataFimFechamento").value;
		
		document.form.flag.value = "semPag";
		
		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
			
			}
			
			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					 document.getElementById("dataFim").value = '';
					return false;					
				}
			}
		}
		else {
				if(dataInicioFechamento != "" || dataFimFechamento != ""){
					if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
					 	document.getElementById("dataInicioFechamento").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
						document.getElementById("dataFimFechamento").value = '';
						return false;					
					}
					if (dataInicio != ""){
						if(DateTimeUtil.isValidDate(dataFim) == true){
						if(DateTimeUtil.isValidDate(dataInicio) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 	document.getElementById("dataInicio").value = '';
							return false;	
						}
						}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;	
						}
					}
					
					if (dataFim != ""){
						if(DateTimeUtil.isValidDate(dataInicio) == true){
						if(DateTimeUtil.isValidDate(dataFim) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;					
						}
					}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
							 document.getElementById("dataInicio").value = '';
							return false;	
						}
					}
					
					
					
				}
				else{
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						document.getElementById("dataFim").value = '';
						return false;					
					}
					
				}
		}
			
		if(dataInicioFechamento != "" || dataFimFechamento != ""){
			if(validaData(dataInicioFechamento,dataFimFechamento)){
				if(validaData(dataInicio,dataFim)){
					inicializarTemporizador();
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("preencheSolicitacoesRelacionadas");
				}
			}
		}
		else{
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("preencheSolicitacoesRelacionadas");
			}
			
		}
		
	}
	
	function imprimirRelatorio(){	
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
		var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
		var dataFimFechamento = document.getElementById("dataFimFechamento").value;
		
		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
			
			}
			
			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					 document.getElementById("dataFim").value = '';
					return false;					
				}
			}
		}
		else {
				if(dataInicioFechamento != "" || dataFimFechamento != ""){
					if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
					 	document.getElementById("dataInicioFechamento").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
						document.getElementById("dataFimFechamento").value = '';
						return false;					
					}
					if (dataInicio != ""){
						if(DateTimeUtil.isValidDate(dataFim) == true){
						if(DateTimeUtil.isValidDate(dataInicio) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 	document.getElementById("dataInicio").value = '';
							return false;	
						}
						}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;	
						}
					}
					
					if (dataFim != ""){
						if(DateTimeUtil.isValidDate(dataInicio) == true){
						if(DateTimeUtil.isValidDate(dataFim) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;					
						}
					}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
							 document.getElementById("dataInicio").value = '';
							return false;	
						}
					}
					
					
					
				}
				else{
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						document.getElementById("dataFim").value = '';
						return false;					
					}
					
				}
		}
			
		if(dataInicioFechamento != "" || dataFimFechamento != ""){
			if(validaData(dataInicioFechamento,dataFimFechamento)){
				if(validaData(dataInicio,dataFim)){
					inicializarTemporizador();
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorio");
				}
			}
		}
		else{
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("imprimirRelatorio");
			}
			
		}
	}
	
	function imprimirRelatorioXls(){	
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idSolicitacaoServicoPesquisa").value;
		var dataInicioFechamento = document.getElementById("dataInicioFechamento").value;
		var dataFimFechamento = document.getElementById("dataFimFechamento").value;
		
		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
			
			}
			
			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
					 document.getElementById("dataFim").value = '';
					return false;					
				}
			}
		}
		else {
				if(dataInicioFechamento != "" || dataFimFechamento != ""){
					if(DateTimeUtil.isValidDate(dataInicioFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioEncerramento"));
					 	document.getElementById("dataInicioFechamento").value = '';
						return false;
					}
					if(DateTimeUtil.isValidDate(dataFimFechamento) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimEncerramento"));
						document.getElementById("dataFimFechamento").value = '';
						return false;					
					}
					if (dataInicio != ""){
						if(DateTimeUtil.isValidDate(dataFim) == true){
						if(DateTimeUtil.isValidDate(dataInicio) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
						 	document.getElementById("dataInicio").value = '';
							return false;	
						}
						}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;	
						}
					}
					
					if (dataFim != ""){
						if(DateTimeUtil.isValidDate(dataInicio) == true){
						if(DateTimeUtil.isValidDate(dataFim) == false){
							alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
							 document.getElementById("dataFim").value = '';
							return false;					
						}
					}
						else{
							alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
							 document.getElementById("dataInicio").value = '';
							return false;	
						}
					}
					
					
					
				}
				else{
					if(DateTimeUtil.isValidDate(dataInicio) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataInicioAbertura"));
					 	document.getElementById("dataInicio").value = '';
						return false;	
					}
					if(DateTimeUtil.isValidDate(dataFim) == false){
						alert(i18n_message("pesquisasolicitacao.informeDataFimAbertura"));
						document.getElementById("dataFim").value = '';
						return false;					
					}
					
				}
		}
			
		if(dataInicioFechamento != "" || dataFimFechamento != ""){
			if(validaData(dataInicioFechamento,dataFimFechamento)){
				if(validaData(dataInicio,dataFim)){
					inicializarTemporizador();
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorioXls");
				}
			}
		}
		else{
			if(validaData(dataInicio,dataFim)){
				inicializarTemporizador();
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("imprimirRelatorioXls");
			}
			
		}
	}
	
	 function anexos(idSolicitacao){

		 document.form.idSolicitacaoServico.value = idSolicitacao; 
		 document.form.fireEvent("restoreUpload");
	 }
	function reabrir(id){
		if (!confirm(i18n_message("solicitacaoservico.confirme.reabertura"))){
			return;
		}
		document.form.idSolicitacaoServico.value = id; 
		document.form.fireEvent("reabre");
	}
	
	function consultarOcorrencias(idSolicitacao){
		document.formOcorrencias.idSolicitacaoOcorrencia.value = idSolicitacao;
		document.formOcorrencias.fireEvent("listOcorrenciasSituacao");
		$("#POPUP_OCORRENCIAS").dialog("open");
	}
	
	function limpar(){
		document.form.clear();
		$('#situacao').attr('disabled', false)
		
		document.getElementById("tblResumo").innerHTML="";
	}
	
	/**
	* @author rodrigo.oliveira
	*/
	function validaData(dataInicio, dataFim) {
		
		var dtInicio = new Date();
		var dtFim = new Date();
		
		dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
		dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
		
		if (dtInicio > dtFim){
			alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
			return false;
		}else
			return true;
	}
	
//	popup para pesquisar de unidade
	
	 $(function() {
		$("#addUnidade").click(function() {
			$("#POPUP_UNIDADE").dialog("open");
		});
	}); 
	
//		popup para pesquisar de localidade
		
	 $(function() {
		$("#addLocalidade").click(function() {
			$("#POPUP_LOCALIDADE").dialog("open");
		});
	}); 
	
	function validarNumero() {
		 if (  isNaN(document.getElementById("idSolicitacaoServicoPesquisa").value)   ) {  
		       document.getElementById("idSolicitacaoServicoPesquisa").value = "";
		} else {
		     return true;
		}	
	}
	
--></script>

</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<i18n:message key='citcorpore.comum.aguardeProcessando'/>" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
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
				<h2><i18n:message key="pesquisasolicitacao.pesquisasolicitacao"/></h2>
			</div>
			<div class="box grid_16 tabs ">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="pesquisasolicitacao.pesquisasolicitacao"/></a></li>
				</ul> 
				<a href="#" class="toggle">&nbsp;</a>
				<div  class="toggle_container">
					<div  class="block"  style="overflow: hidden;">
						<div id="parametros">
						  <div class='inner'>
							<form class='labelOverflowTresPontinhos' name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaSolicitacoesServicos/pesquisaSolicitacoesServicos'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idResponsavel' name='idResponsavel'>
								<input type="hidden" id='idItemConfiguracao' name='idItemConfiguracao'>
								<input type="hidden" id='idSolicitacaoServico' name='idSolicitacaoServico'>
								<input type="hidden" id='idUnidade' name='idUnidade'>
								<input type="hidden" id='idLocalidade' name='idLocalidade'>
								<input type="hidden" id='idServico' name='idServico'>
								<input type="hidden" id='paginacao' name='paginacao'>
								<input type='hidden' id='totalItens' name='totalItens' value =''>
								<input type='hidden' id='totalPagina' name='totalPagina' value =''>
								<input type='hidden' id='pagAtual' name='pagAtual' value =''>
								<input type='hidden' id='pagAtualAux' name='pagAtualAux' value =''>
								<input type='hidden' id='flag' name='flag'>
								
								<!-- 
									Motivo: Otimizando o html para o novo padrão de layout
									Autor: flavio.santana
									Data: 30/10/2013 10:50									
								 -->
								<div class="row-fluid" >									
									<div class="span12">										
										<div class="row-fluid">
											<div class="span3">
												<label class="campoObrigatorio strong"><i18n:message key="pesquisasolicitacao.periodoAbertura" /></label>
												<div class="row-fluid" >									
													<div class="span5">
													  	<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
													</div>
													<div class="span2"><i18n:message key="citcorpore.comum.a" /></div>
													<div class="span5">
											  			<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
													</div>
												</div>	
											</div>
										  	<div class="span3">
												<label ><i18n:message key="pesquisasolicitacao.periodoEncerramento"/></label>
												<div class="row-fluid" >									
													<div class="span5">
													  	<input  type='text' name='dataInicioFechamento' id='dataInicioFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
													</div>
													<div class="span2"><i18n:message key="citcorpore.comum.a" /></div>
													<div class="span5">
											  			<input type='text' name='dataFimFechamento' id='dataFimFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
													</div>
												</div>	
											</div>
											<div class="span2">
												<label><i18n:message key="citcorpore.comum.numero" /></label>
												<input type="text" id="idSolicitacaoServicoPesquisa" name="idSolicitacaoServicoPesquisa" size="9" maxlength="9" onkeydown="validarNumero();"/>
											</div>
											<div class="span2">
												<label><i18n:message key="contrato.contrato" /></label>
												<select name='idContrato'></select>
											</div>
											<div class="span2">
												<label><i18n:message key="citcorpore.comum.ordenacao" /></label>
												<select name='ordenacao' id="ordenacao">
													<option selected="selected" value='solicitacaoservico.idSolicitacaoServico'><i18n:message key="citcorpore.comum.numero" /></option>
													<OPTION value='solicitacaoservico.iditemconfiguracao'><i18n:message key="itemConfiguracao.itemConfiguracao" /></OPTION>
													<option value="solicitacaoservico.datahorasolicitacao"><i18n:message key="citcorpore.comum.data" /></option>
										            <OPTION value='ltrim(empregado.nome)'><i18n:message key="solicitacaoServico.solicitante" /></OPTION>
										            <OPTION value='solicitacaoservico.situacao'><i18n:message key="solicitacaoServico.situacao" /></OPTION>
										            <OPTION value='solicitacaoservico.idPrioridade'><i18n:message key="solicitacaoServico.prioridade" /></OPTION>
										            <OPTION value='ltrim(grupo.sigla)'><i18n:message key="pesquisasolicitacao.gruposolucionador" /></OPTION>
										            <OPTION value='solicitacaoservico.idFaseAtual'><i18n:message key="pesquisasolicitacao.fase" /></OPTION>
										            <OPTION value='solicitacaoservico.idOrigem'><i18n:message key="citcorpore.comum.origem" /></OPTION>
										            <OPTION value='tipodemandaservico.nometipodemandaservico'><i18n:message key="citcorpore.comum.tipo" /></OPTION>
												</select>
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid" >									
									<div class="span12">										
										<div class="row-fluid">
											<div class="span3">
												<label><i18n:message key="solicitacaoServico.itemConfiguracao" /></label>
												<%
													if(br.com.citframework.util.Util.isVersionFree(request)){												
												%>
													<%=Free.getMsgCampoIndisponivel(request)%>
												<%
													} else {
												%>
													<input type="text" onfocus='abrePopupIC();' id="nomeItemConfiguracao" name="nomeItemConfiguracao" />
												<%
													}
												%>
											</div>
											<div class="span3">
												<label><i18n:message key="solicitacaoServico.solicitante" /></label>
												<input type="text" onfocus='abrePopupUsuario();' id="nomeSolicitante" name="nomeSolicitante" />
											</div>
											<div class="span3">
												<label><i18n:message key="solicitacaoServico.situacao" /></label>
												<select id="situacao" name='situacao'>
													<option value=''>--<i18n:message key="citcorpore.comum.todos" /> --</option>
													<OPTION value='Cancelada'><i18n:message key="citcorpore.comum.cancelada" /> </OPTION>
										            <OPTION value='EmAndamento'><i18n:message key="citcorpore.comum.emandamento" /> </OPTION>
										            <OPTION value='Fechada'><i18n:message key="citcorpore.comum.fechada" /> </OPTION>
										            <OPTION value='Reaberta'><i18n:message key="citcorpore.comum.reaberta" /> </OPTION>
										            <OPTION value='Resolvida'><i18n:message key="citcorpore.comum.resolvida" /></OPTION>
										            <OPTION value='Suspensa'><i18n:message key="citcorpore.comum.suspensa" /> </OPTION>
												</select>
											</div>
											<div class="span3">
												<label><i18n:message key="solicitacaoServico.prioridade" /></label>
												<select name='idPrioridade'></select>
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid" >	
									<div class="span12">										
										<div class="row-fluid">
											<div class="span3">
												<label><i18n:message key="pesquisasolicitacao.gruposolucionador" /></label>
												<select name='idGrupoAtual'></select>
											</div>
											<div class="span3">
												<label><i18n:message key="pesquisasolicitacao.fase" /> </label>
													<select name='idFaseAtual'></select>
											</div>
											<div class="span3">
												<label><i18n:message key="citcorpore.comum.origem" /></label>
													<select name='idOrigem'></select>
											</div>
											<div class="span3">
												<label><i18n:message key="citcorpore.comum.tipo" /></label>
												<select name='idTipoDemandaServico'></select>
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid" >	
									<div class="span12">										
										<div class="row-fluid">
											<div class="span3">
												<label><i18n:message key="gerenciaservico.criado_por" /> </label>
												<input type="text" onfocus='abrePopupResponsavel();' id="nomeResponsavel" name="nomeResponsavel" />
											</div>
											<div class="span3">
												<label><i18n:message key="servico.servico" /> </label>
												<input type="text" onfocus='abrePopupServico();' id="servico" name="servico" />
											</div>
											<div class="span2">
												<label><i18n:message key="citcorpore.comum.unidade" /></label>
												<input type="text"  id="addUnidade" name="nomeUnidade" />
											</div>
											<div class="span2">
												<label><i18n:message key="itemConfiguracaoTree.localidade" /></label>
												<input type="text"  id="addLocalidade" name="nomeLocalidade" />
											</div>
											<div class="span2">
												<label><i18n:message key="pesquisa.palavraChave"/> </label>
												<input type="text"  id="palavraChave" name="palavraChave" />
											</div>
										</div>
									</div>
								</div>
								<div class="row-fluid" >	
									<div class="span12">	
										<label>
											<input  type="checkbox" name="exibirCampoDescricao" id="exibirCampoDescricao" value="S" /> 
											<i18n:message key="pesquisasolicitacao.exibirCampoDescricaoRelatorios"/>
										</label>
									</div>
								</div>
								<div class="row-fluid" >	
									<div class="span12">	
										<button type='button' name='btnPesquisar' class="light"  onclick='filtrar();' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><i18n:message key="citcorpore.comum.pesquisar" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"   onclick='limpar()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
									 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorio()' style="margin: 20px !important;">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
										<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button > 
										 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioXls()' style="margin: 20px !important;">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png">
										<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button> 
									</div>
								</div>
								<div class="row-fluid" >	
									<div class="span12">
										<div  id="tblResumo" align="center" style='display: block; border:0px solid gray; overflow-x: auto'></div>
									</div>	
								</div>
								
<%-- 							<div class="columns clearfix">
									<div class="col_20">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="pesquisasolicitacao.periodoAbertura" /></label>
											<div>
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
														<td>
															<i18n:message key="citcorpore.comum.a" />
														</td>
														<td>
															<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
													</tr>
												</table>												
											</div>
										</fieldset>
									</div>
									<div class="col_20" >
										<fieldset>
											<label ><i18n:message key="pesquisasolicitacao.periodoEncerramento"/></label>
											<div  >
												<table>
													<tr>
														<td>
															<input  type='text' name='dataInicioFechamento' id='dataInicioFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
														<td>
															<i18n:message key="citcorpore.comum.a" />
														</td>
														<td>
															<input type='text' name='dataFimFechamento' id='dataFimFechamento' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
													</tr>
												</table>												
											</div>
										</fieldset>
									</div>
									<div class="col_20" >
										<fieldset style="height: 65px">
											<label><i18n:message key="citcorpore.comum.numero" /></label>
											<div >
												<input type="text" id="idSolicitacaoServicoPesquisa" name="idSolicitacaoServicoPesquisa" size="9" maxlength="9" onkeydown="validarNumero();"/>
											</div>
										</fieldset>																				
									</div>
									<div class="col_20">
										<fieldset style="height: 65px">
											<label><i18n:message key="contrato.contrato" /></label>
											<div>
												<select name='idContrato'>
												</select>
											</div>
										</fieldset>
									</div>	
									<div class="col_20">
										<fieldset style="height: 65px" >
											<label><i18n:message key="citcorpore.comum.ordenacao" /></label>
											<div>
												<select name='ordenacao' id="ordenacao">
													<option selected="selected" value='solicitacaoservico.idSolicitacaoServico'><i18n:message key="citcorpore.comum.numero" /></option>
													<OPTION value='solicitacaoservico.iditemconfiguracao'><i18n:message key="itemConfiguracao.itemConfiguracao" /></OPTION>
													<option value="solicitacaoservico.datahorasolicitacao"><i18n:message key="citcorpore.comum.data" /></option>
										            <OPTION value='ltrim(empregado.nome)'><i18n:message key="solicitacaoServico.solicitante" /></OPTION>
										            <OPTION value='solicitacaoservico.situacao'><i18n:message key="solicitacaoServico.situacao" /></OPTION>
										            <OPTION value='solicitacaoservico.idPrioridade'><i18n:message key="solicitacaoServico.prioridade" /></OPTION>
										            <OPTION value='ltrim(grupo.sigla)'><i18n:message key="pesquisasolicitacao.gruposolucionador" /></OPTION>
										            <OPTION value='solicitacaoservico.idFaseAtual'><i18n:message key="pesquisasolicitacao.fase" /></OPTION>
										            <OPTION value='solicitacaoservico.idOrigem'><i18n:message key="citcorpore.comum.origem" /></OPTION>
										            <OPTION value='tipodemandaservico.nometipodemandaservico'><i18n:message key="citcorpore.comum.tipo" /></OPTION>
												</select>
											</div>
										</fieldset>																				
									</div>
								</div>
								
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="solicitacaoServico.itemConfiguracao" /></label>
											<%
												if(br.com.citframework.util.Util.isVersionFree(request)){												
											%>
											<div>
												<%=Free.getMsgCampoIndisponivel(request)%>
											</div>
											<%
												} else {
											%>
											<div>
												<input type="text" onfocus='abrePopupIC();' id="nomeItemConfiguracao" name="nomeItemConfiguracao" />
											</div>
											<%
												}
											%>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="solicitacaoServico.solicitante" /></label>
											<div>
												<input type="text" onfocus='abrePopupUsuario();' id="nomeSolicitante" name="nomeSolicitante" />
											</div>
										</fieldset>
									</div>	
									<div class="col_25">
										<fieldset  style="height: 61px !important;">
											<label><i18n:message key="solicitacaoServico.situacao" /></label>
											<div>
												<select id="situacao" name='situacao'>
													<option value=''>--<i18n:message key="citcorpore.comum.todos" /> --</option>
													<OPTION value='Cancelada'><i18n:message key="citcorpore.comum.cancelada" /> </OPTION>
										            <OPTION value='EmAndamento'><i18n:message key="citcorpore.comum.emandamento" /> </OPTION>
										            <OPTION value='Fechada'><i18n:message key="citcorpore.comum.fechada" /> </OPTION>
										            <OPTION value='Reaberta'><i18n:message key="citcorpore.comum.reaberta" /> </OPTION>
										            <OPTION value='ReClassificada'><i18n:message key="citcorpore.comum.reclassificada" /> </OPTION>
										            <OPTION value='Resolvida'><i18n:message key="citcorpore.comum.resolvida" /></OPTION>
										            <OPTION value='Suspensa'><i18n:message key="citcorpore.comum.suspensa" /> </OPTION>
												</select>
											</div>
										</fieldset>
									</div>	
									<div class="col_25">
										<fieldset style="height: 61px !important;">
											<label><i18n:message key="solicitacaoServico.prioridade" /></label>
											<div>
												<select name='idPrioridade'>
												</select>
											</div>
										</fieldset>
									</div>		
								</div>		
														
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="pesquisasolicitacao.gruposolucionador" /></label>
											<div>
												<select name='idGrupoAtual'>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="pesquisasolicitacao.fase" /> </label>
											<div>
												<select name='idFaseAtual'>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.origem" /></label>
											<div>
												<select name='idOrigem'>
												</select>
											</div>
										</fieldset>
									</div>		
									
									<div class="col_25">
										<div>
											<fieldset>
												<label><i18n:message key="citcorpore.comum.tipo" /></label>
												<div>
													<select name='idTipoDemandaServico'>
													</select>
												</div>
											</fieldset>										
										</div>
									</div>		
								</div>		
								
								<div class="columns clearfix">
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="gerenciaservico.criado_por" /> </label>
											<div>
												<input type="text" onfocus='abrePopupResponsavel();' id="nomeResponsavel" name="nomeResponsavel" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="servico.servico" /> </label>
											<div>
												<input type="text" onfocus='abrePopupServico();' id="servico" name="servico" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.unidade" /></label>
											<div>
												<input type="text"  id="addUnidade" name="nomeUnidade" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="itemConfiguracaoTree.localidade" /></label>
											<div>
												<input type="text"  id="addLocalidade" name="nomeLocalidade" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="pesquisa.palavraChave"/> </label>
											<div>
												<input type="text"  id="palavraChave" name="palavraChave" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset style="height: 55px">
											<label style="display: block; float: left;padding-top: 15px;"><input  type="checkbox" name="exibirCampoDescricao" id="exibirCampoDescricao" value="S" /> <i18n:message key="pesquisasolicitacao.exibirCampoDescricaoRelatorios"/></label>
										</fieldset>
									</div>
								</div> 
								<div class="col_100">
									<fieldset>
										<button type='button' name='btnPesquisar' class="light"  onclick='filtrar();' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><i18n:message key="citcorpore.comum.pesquisar" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"   onclick='limpar()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
									 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorio()' style="margin: 20px !important;">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
										<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button > 
										 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioXls()' style="margin: 20px !important;">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png">
										<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button> 
									</fieldset>
								</div>
								
								<div class="col_100" id="tblResumo" align="center" style='display: block; border:0px solid gray; overflow-x: auto'></div>
								--%>
							</form>		
						  </div>									
						</div>
						
						<!-- <div class="col_100" id="tblResumo" align="center" style='display: block; border:0px solid gray'>
						</div>  -->
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	
		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/footer.jsp"%>
		<%} %>

</body>
	<div class="POPUP_barraFerramentasIncidentes" id="POPUP_menuAnexos" style='display:none'>		
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControlList style="height:100px;width:50%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/uploadList/uploadList.load" disabled="false"/>
		</form>
	</div>	
	
	<div id="POPUP_RESPONSAVEL" title="<i18n:message key="citcorpore.comum.pesquisaresponsavel" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaResponsavel' style="width: 540px">
							<cit:findField formName='formPesquisaResponsavel' 
								lockupName='LOOKUP_USUARIO_EMPREGADO' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_UNIDADE" title="<i18n:message key="citcorpore.comum.unidade" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaUnidade' style="width: 540px">
						<cit:findField formName='formPesquisaUnidade'
							lockupName='LOOKUP_UNIDADE' id='LOOKUP_UNIDADE_SOLICITACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
	<div id="POPUP_LOCALIDADE" title="<i18n:message key="itemConfiguracaoTree.localidade" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaLocalidade' style="width: 540px">
							<cit:findField formName='formPesquisaLocalidade'
								lockupName='LOOKUP_LOCALIDADE_PESQUISA' id='LOOKUP_LOCALIDADE_SOLICITACAO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_SOLICITANTE" title="<i18n:message key="citcorpore.comum.pesquisacolaborador" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario'
								lockupName='LOOKUP_SOLICITANTE' id='LOOKUP_SOLICITANTE' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

		<div id="POPUP_SERVICO" title="<i18n:message key="servico.servico" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formServico' style="width: 540px">
							<cit:findField formName='formServico'
								lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="POPUP_ITEMCONFIG" title="<i18n:message key="citcorpore.comum.identificacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisa' style="width: 540px">
						<cit:findField formName='formPesquisa'
 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0' 
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_OCORRENCIAS" title="<i18n:message key="citcorpore.comum.ocorrenciasolicitacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao'>
							<input type='hidden' name='idSolicitacaoOcorrencia' />
							<div id='divRelacaoOcorrencias' style='width: 100%; height: 100%;'>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
<div id="pag" style="text-align: center; display: block; width: 100%; margin-bottom: 5px;">
		<input id="btfirst" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(0)" value='<i18n:message key="citcorpore.comum.primeiro" />' title='<i18n:message key="citcorpore.comum.primeiro" />' style=" cursor: pointer" name="btfirst">
		<input id="btprevius" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(-1)" value='<i18n:message key="citcorpore.comum.anterior" />' title='<i18n:message key="citcorpore.comum.anterior" />' style=" cursor: pointer" name="btprevius">
		<label><i18n:message key="citcorpore.comum.mostrandoPagina" /></label> <label id='paginaAtual' style='width: 1%!important;'></label> <label><i18n:message key="citcorpore.comum.mostrandoPaginaDe" /></label> <label id='paginaTotal' style='width: 1%!important;'> </label>
	<input id="btnext" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(1)" value='<i18n:message key="citcorpore.comum.proximo" />' title='<i18n:message key="citcorpore.comum.proximo" />' style=" cursor: pointer" name="btnext">
		<input id="btlast" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao(document.form.totalItens.value);" value='<i18n:message key="citcorpore.comum.ultimo" />' title='<i18n:message key="citcorpore.comum.ultimo" />' style=" cursor: pointer" name="btlast">
</div>
</html>