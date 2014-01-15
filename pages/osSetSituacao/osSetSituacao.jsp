<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String permiteValorZeroAtv = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.OS_VALOR_ZERO, "N");
	if (permiteValorZeroAtv == null){
		permiteValorZeroAtv = "N";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style type="text/css">
		body {
			border: black solid 1px ;	
			background-color: white;
			vertical-align: middle;
		
		}
		
		#paginaTotal {
			background-color: white;
			background-image: url("");
		}
		
		.linhaGrid{
			text-align: center;
			border: 1px solid black;
			background-color: white;	
			vertical-align: middle;
		}
		
		#GRID_ITENS_tblItens {
			border: black solid 1px ; 
			margin-top: 3px;
			margin-left: 2px;
		}
	
	</style>
	
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
	
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/themeroller/Aristo.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/text.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/grid.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/main.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/theme_base.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/buttons.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/ie.css">
	
	<script type="text/javascript" src="../../cit/objects/DemandaDTO.js"></script> 
	<script type="text/javascript" src="../../cit/objects/GlosaOSDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/VinculaOsIncidenteDTO.js"></script>
		
	<!-- Area de JavaScripts -->
	<script type="text/javascript">
		var objTab = null;
		
		addEvent(window, "load", load, false);
		function load(){
			document.form.afterRestore = function () {
				document.getElementById('tabTela').tabber.tabShow(0);
			}
		}
		
		function LOOKUP_OS_select(id,desc){
			document.form.restore({idOS:id});
		}
		
		$(function() {
			$('.datepicker').datepicker();
			
			$("#POPUP_LISTA_INCIDENTES").dialog({
				title: i18n_message('citcorpore.comum.associarIncidente'),
				autoOpen : false,
				height : 300,
				width : 800,
				modal : true
			});
			
		});
		
		function gravarForm(){
			var count = GRID_ITENS.getMaxIndex();
			var existeErro = false;
			var contadorAux = 0;
			var objs = new Array();
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var idAtividadesOSObj = document.getElementById('idAtividadesOS' + NumberUtil.zerosAEsquerda(i,5));
				var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
				var qtdeExecutadaObj = document.getElementById('qtdeExecutada' + NumberUtil.zerosAEsquerda(i,5));
				var glosaAtividadeObj = document.getElementById('glosa' + NumberUtil.zerosAEsquerda(i,5));
				var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
				var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
				var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
				trObj.bgColor = 'white';
				complexidadeObj.style.backgroundColor = 'white';
				quantidadeObj.style.backgroundColor = 'white';
				glosaAtividadeObj.style.backgroundColor = 'white';
				demandaObj.style.backgroundColor = 'white';		
				objObj.style.backgroundColor = 'white';		
				if (complexidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					glosaAtividadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe a complexidade! Linha: ' + i);
					return;
				}
				if (demandaObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					glosaAtividadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe a demanda! Linha: ' + i);
					return;
				}
				if (quantidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					glosaAtividadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe o custo! Linha: ' + i);
					return;
				}
				var objItem = new CIT_DemandaDTO();
				objItem.idAtividadesOS = idAtividadesOSObj.value;
				objItem.complexidade = complexidadeObj.value;
				objItem.custoAtividade = quantidadeObj.value;
				objItem.glosaAtividade = glosaAtividadeObj.value;
				objItem.descricaoAtividade = demandaObj.value;
				objItem.qtdeExecutada = qtdeExecutadaObj.value;
				objItem.obsAtividade = objObj.value;
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;
				
				<%if (!permiteValorZeroAtv.equalsIgnoreCase("S")){%>
					if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
						trObj.bgColor = 'orange';
						complexidadeObj.style.backgroundColor = 'orange';
						quantidadeObj.style.backgroundColor = 'orange';
						glosaAtividadeObj.style.backgroundColor = 'orange';
						demandaObj.style.backgroundColor = 'orange';		
						objObj.style.backgroundColor = 'orange';
						alert('Falta definir custo da atividade ! Linha: ' + i);
						existeErro = true;
					}
				<%}%>
			}
			if (existeErro){
				return;
			}
			document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
			
			count = GRID_GLOSAS.getMaxIndex();
			existeErro = false;
			contadorAux = 0;
			objs = new Array();		
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_GLOSAS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var descricaoGlosaObj = document.getElementById('descricaoGlosa' + NumberUtil.zerosAEsquerda(i,5));
				var numeroOcorrenciasObj = document.getElementById('numeroOcorrencias' + NumberUtil.zerosAEsquerda(i,5));
				var percAplicadoObj = document.getElementById('percAplicado' + NumberUtil.zerosAEsquerda(i,5));
				var custoGlosaObj = document.getElementById('custoGlosa' + NumberUtil.zerosAEsquerda(i,5));
				var ocorrenciasObj = document.getElementById('ocorrencias' + NumberUtil.zerosAEsquerda(i,5));
				var idGlosaOSObj = document.getElementById('idGlosaOS' + NumberUtil.zerosAEsquerda(i,5));
				trObj.bgColor = 'white';
				complexidadeObj.style.backgroundColor = 'white';
				quantidadeObj.style.backgroundColor = 'white';
				glosaAtividadeObj.style.backgroundColor = 'white';
				demandaObj.style.backgroundColor = 'white';		
				objObj.style.backgroundColor = 'white';		
				if (descricaoGlosaObj.value == ''){
					trObj.bgColor = 'orange';
					descricaoGlosaObj.style.backgroundColor = 'orange';		
					numeroOcorrenciasObj.style.backgroundColor = 'orange';
					percAplicadoObj.style.backgroundColor = 'orange';
					custoGlosaObj.style.backgroundColor = 'orange';
					ocorrenciasObj.style.backgroundColor = 'orange';				
					alert('Informe a descrição da Glosa! Linha: ' + i);
					return;
				}
				if (numeroOcorrenciasObj.value == ''){
					trObj.bgColor = 'orange';
					descricaoGlosaObj.style.backgroundColor = 'orange';		
					numeroOcorrenciasObj.style.backgroundColor = 'orange';
					percAplicadoObj.style.backgroundColor = 'orange';
					custoGlosaObj.style.backgroundColor = 'orange';
					ocorrenciasObj.style.backgroundColor = 'orange';				
					alert('Informe o número de ocorrências para Glosa! Linha: ' + i);
					return;
				}
// 				if (percAplicadoObj.value == '' || percAplicadoObj.value == '0,00' || percAplicadoObj.value == '0'){
// 					trObj.bgColor = 'orange';
// 					descricaoGlosaObj.style.backgroundColor = 'orange';		
// 					numeroOcorrenciasObj.style.backgroundColor = 'orange';
// 					percAplicadoObj.style.backgroundColor = 'orange';
// 					custoGlosaObj.style.backgroundColor = 'orange';
// 					ocorrenciasObj.style.backgroundColor = 'orange';				
// 					alert('Informe o % aplicado de Glosa! Linha: ' + i);
// 					return;
// 				}
// 				if (custoGlosaObj.value == '' || custoGlosaObj.value == '0,00' || custoGlosaObj.value == '0'){
// 					trObj.bgColor = 'orange';
// 					descricaoGlosaObj.style.backgroundColor = 'orange';		
// 					numeroOcorrenciasObj.style.backgroundColor = 'orange';
// 					percAplicadoObj.style.backgroundColor = 'orange';
// 					custoGlosaObj.style.backgroundColor = 'orange';
// 					ocorrenciasObj.style.backgroundColor = 'orange';				
// 					alert('Informe o custo da Glosa! Linha: ' + i);
// 					return;
// 				}	
				var objItem = new CIT_GlosaOSDTO();
				objItem.idGlosaOS = idGlosaOSObj.value;
				objItem.descricaoGlosa = descricaoGlosaObj.value;
				objItem.ocorrencias = ocorrenciasObj.value;
				objItem.percAplicado = percAplicadoObj.value;
				objItem.custoGlosa = custoGlosaObj.value;
				objItem.numeroOcorrencias = numeroOcorrenciasObj.value;
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;
			}
			document.form.colGlosas_Serialize.value = ObjectUtils.serializeObjects(objs);	
			document.form.save();
		}	
		var seqSelecionada = '';
		function setaRestoreItem(complex, det, obs, formula, custo, glosa, qtdeExec, idAtvOS, exibirBotao, idServicoContratoContabil, divAssociacao){
			if (seqSelecionada != ''){
				eval('HTMLUtils.setValue(\'complexidade' + seqSelecionada + '\', \'' + complex + '\')');
				eval('document.form.demanda' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
				eval('document.form.obs' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
				eval('document.form.quantidade' + seqSelecionada + '.value = "' + custo + '"');
				eval('document.form.glosa' + seqSelecionada + '.value = "' + glosa + '"');
				eval('document.form.qtdeExecutada' + seqSelecionada + '.value = "' + qtdeExec + '"');
				eval('document.form.idAtividadesOS' + seqSelecionada + '.value = "' + idAtvOS + '"');
				eval('document.form.idServicoContratoContabil' + seqSelecionada + '.value = "' + idServicoContratoContabil + '"');
				document.getElementById("divDemanda"+seqSelecionada).innerHTML = ObjectUtils.decodificaEnter(det) + "<div style='font-weight:bold; padding-top: 6px;'>"+formula+"</div>";
				document.getElementById("divAssociacaoInc"+seqSelecionada).innerHTML = divAssociacao;
				if(exibirBotao == "true"){
					exibirBotaoVincInc(seqSelecionada);
				}
			}
		}
		var seqSelecionadaGlosa = '';
		function setaRestoreItemGlosa(idGlosa, det, obs, numOc, perc, glosa){
			if (seqSelecionadaGlosa != ''){
				eval('document.form.idGlosaOS' + seqSelecionadaGlosa + '.value = "' + idGlosa + '"');
				eval('document.form.descricaoGlosa' + seqSelecionadaGlosa + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
				eval('document.form.ocorrencias' + seqSelecionadaGlosa + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
				eval('document.form.numeroOcorrencias' + seqSelecionadaGlosa + '.value = "' + numOc + '"');
				eval('document.form.percAplicado' + seqSelecionadaGlosa + '.value = "' + perc + '"');
				eval('document.form.custoGlosa' + seqSelecionadaGlosa + '.value = "' + glosa + '"');
			}
		}
		function selecionaServicoContrato(){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent('restoreInfoServicoContrato');
		}
		function desabilitaObsFinal(){
			document.form.obsFinalizacao.disabled=false;
			document.form.obsFinalizacao.onkeydown = null;
		}
				
		function carregaGlosas(){
			document.form.fireEvent('preencheItensGlosa');
		}
		
		function setaGlosaItem(descricao){
			if (seqSelecionada != ''){		
				eval('document.form.descricaoGlosa' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
				eval('document.form.numeroOcorrencias' + seqSelecionada + '.value = 0');
			}
		}
		
		function exibirBotaoVincInc(seq){
			document.getElementById('divAssociacaoInc'+seq).style.display = 'block';
		}
		
		function mostrarIncidentesParaAssociar(idServicoContrato){
			document.form.idServicoContratoContabil.value = idServicoContrato;
			document.form.fireEvent('listaIncidentesParaVincular');
		}
		
		function associarIncidentes(){
			document.formAssociar.idOS.value = document.form.idOS.value;
			document.formAssociar.idServicoContratoContabil.value = document.form.idServicoContratoContabil.value;
			
			var tabela = document.getElementById('tableIncidentes');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var incidentes = new Array();
			
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idSolicitacaoServico' + i);	
				if (!trObj) {
					continue;
				}	
				incidentes[contadorAux] = getIncidentes(i);
				contadorAux = contadorAux + 1;
			}
			serializaIncidentes();
			//document.getElementById('divOsSelecao').innerHTML = 'Aguarde... carregando...';
			document.formAssociar.fireEvent("associarOSIncidente");
		}
		
		serializaIncidentes = function() {
			var tabela = document.getElementById('tableIncidentes');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var incidentes = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idSolicitacaoServico' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					incidentes[contadorAux] = getIncidentes(i);
					contadorAux = contadorAux + 1;
					continue;
				}	
				
			}
			var incidentesSerializadas = ObjectUtils.serializeObjects(incidentes);
			document.formAssociar.incidentesSerializadas.value = incidentesSerializadas;
			return true;
		}
		
		getIncidentes = function(seq) {
			var VinculaOsIncidenteDTO = new CIT_VinculaOsIncidenteDTO();
			VinculaOsIncidenteDTO.sequencia = seq;
			VinculaOsIncidenteDTO.idSolicitacaoServico = eval('document.formAssociar.idSolicitacaoServico' + seq + '.value');
			return VinculaOsIncidenteDTO;
		}
		
		function preencheNumeracaoItens(){
			var count = GRID_ITENS.getMaxIndex();
			flag = false;
			for (var i = 1; i <= count; i++){
				if(!flag){
					var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i,5));
					if(!item){
						flag = true;
						var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
						if(!item){
							continue;
						}
					}
					item.innerHTML = i < 10 ? NumberUtil.zerosAEsquerda(i,2) : i;
				}else{
					var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
					if(!item){
						continue;
					}
					item.innerHTML = i < 10 ? NumberUtil.zerosAEsquerda(i,2) : i;
				}
			}
		}
		
		function unlockGlosas(){
			for(var i = 0; i < document.form.length; i++){
				var elem = document.form.elements[i];
				var ok = false;
				if (elem.name.indexOf("idGlosaOS") > -1){
					ok = true;
				}			
				if (elem.name.indexOf("descricaoGlosa") > -1){
					ok = true;
					HTMLUtils.unlockField(elem);
				}
				if (elem.name.indexOf("numeroOcorrencias") > -1){
					ok = true;
				}	
				if (elem.name.indexOf("ocorrencias") > -1){
					ok = true;
					HTMLUtils.unlockField(elem);
				}
				if (elem.name.indexOf("percAplicado") > -1){
					ok = true;
				}
				if (elem.name.indexOf("custoGlosa") > -1){
					elem.readOnly = true;
					ok = true;
				}											
				if (ok){
					elem.disabled = false;
				}
			}	
		}
		function calculaFormulaANS(seq, objFieldName){
			JANELA_AGUARDE_MENU.show();
			document.form.seqANS.value = seq;
			document.form.fieldANS.value = objFieldName;
			document.form.fireEvent('calculaFormulaANS');
		}
		
		function alterarValorAtiviade(){
			var count = GRID_ITENS.getMaxIndex();
			var existeErro = false;
			var contadorAux = 0;
			var objs = new Array();
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var idAtividadesOSObj = document.getElementById('idAtividadesOS' + NumberUtil.zerosAEsquerda(i,5));
				var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
				var qtdeExecutadaObj = document.getElementById('qtdeExecutada' + NumberUtil.zerosAEsquerda(i,5));
				var glosaAtividadeObj = document.getElementById('glosa' + NumberUtil.zerosAEsquerda(i,5));
				var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
				var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
				var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
				trObj.bgColor = 'white';
				complexidadeObj.style.backgroundColor = 'white';
				quantidadeObj.style.backgroundColor = 'white';
				glosaAtividadeObj.style.backgroundColor = 'white';
				demandaObj.style.backgroundColor = 'white';		
				objObj.style.backgroundColor = 'white';		
				if (complexidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					glosaAtividadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe a complexidade! Linha: ' + i);
					return;
				}
				if (demandaObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					glosaAtividadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe a demanda! Linha: ' + i);
					return;
				}
				if (quantidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					glosaAtividadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe o custo! Linha: ' + i);
					return;
				}						
				var objItem = new CIT_DemandaDTO();
				objItem.idAtividadesOS = idAtividadesOSObj.value;
				objItem.complexidade = complexidadeObj.value;
				objItem.custoAtividade = quantidadeObj.value;
				objItem.glosaAtividade = glosaAtividadeObj.value;
				objItem.descricaoAtividade = demandaObj.value;
				objItem.qtdeExecutada = qtdeExecutadaObj.value;
				objItem.obsAtividade = objObj.value;
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;
				
				<%if (!permiteValorZeroAtv.equalsIgnoreCase("S")){%>
					if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
						trObj.bgColor = 'orange';
						complexidadeObj.style.backgroundColor = 'orange';
						quantidadeObj.style.backgroundColor = 'orange';
						glosaAtividadeObj.style.backgroundColor = 'orange';
						demandaObj.style.backgroundColor = 'orange';		
						objObj.style.backgroundColor = 'orange';
						alert('Falta definir custo da atividade ! Linha: ' + i);
						existeErro = true;
					}
				<%}%>
			}
			if (existeErro){
				return;
			}
			document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
			
			document.form.fireEvent('calculaValorTotalAtividade');
		}
		
		function serealizaCustoExecutado(){
			
			var count = GRID_ITENS.getMaxIndex();
			var contadorAux = 0;
			var objs = new Array();
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var qtdeExecutadaObj = document.getElementById('qtdeExecutada' + NumberUtil.zerosAEsquerda(i,5));
				var objItem = new CIT_DemandaDTO();
				objItem.qtdeExecutada = qtdeExecutadaObj.value;
				
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;
								
			}
				
			document.form.colQtdExec_Serialize.value = ObjectUtils.serializeObjects(objs);
			
			document.form.fireEvent('atualizaTotalExecutado');
		}
				
		function GRID_ITENS_onDeleteRowByImgRef(){
			alert('Não é permitido efetuar essa exclusão!');
			return false;
		}
		
	 	function GRID_GLOSAS_onDeleteRowByImgRef(objImg){
			alert('Não é permitido efetuar essa exclusão!');
			return false;
		}
		
	</script>
	<style>
		.linhaGrid {
			border: 1px solid black;
			background-color: white;
			vertical-align: middle;
		}
		
		body {
			background-color: white;
			background-image: url("");
		}
		
		#GRID_ITENS_tblItens {
			margin-top: 3px;
			margin-left: 2px;
		}
		#GRID_GLOSAS_tblItens{
		margin-top: 3px;
			margin-left: 2px;
		}
		.linhaSubtituloGrid {
			padding: 5px;
		}
	</style>
</head>
<body>
	<!-- Definicoes Comuns -->
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	<div id="paginaTotal">
		<div id="areautil">
			<div id="formularioIndex">
	       		<div id=conteudo>
					<table width="100%">
						<tr>
							<td width="100%">
									<h2><b>Gerar R.A.</b></h2>
									<!-- ## AREA DA APLICACAO ## -->
									 	<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/osSetSituacao/osSetSituacao'>
									 		<input type='hidden' name='idOS'/>
									 		<input type='hidden' name='idOSPai'/>
									 		<input type='hidden' name='idContrato'/>
									 		<input type='hidden' name='seqANS'/>
									 		<input type='hidden' name='fieldANS'/>
									 		<input type='hidden' name='colItens_Serialize'/>
									 		<input type='hidden' name='colQtdExec_Serialize'/>
									 		<input type='hidden' name='colGlosas_Serialize'/>
									 		<input type='hidden' name='flagGlosa'/>
									 		<input type='hidden' name='idServicoContratoContabil'/>
										  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
										         <tr>
										           <!--  <td class="campoEsquerda">Serviço*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<select name='idServicoContrato'  onchange="selecionaServicoContrato()">
										            	</select>
										            	</div>
										            </td>
										         </tr>	
										         <tr>
										           <!--  <td class="campoEsquerda">Número*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<input type='text' name='numero' size="20" maxlength="20" />
										            </div>
										            </td>
										         </tr> 
										         <tr>
										            <!-- <td class="campoEsquerda">Área requisitante*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<input type='text' name='nomeAreaRequisitante' size="80" maxlength="150" style="width: 500px !important;"/>
										           	</div>
										            </td>
										         </tr>	 								         
										         <tr>
										           <!--  <td class="campoEsquerda">Ano*:</td> -->
										            <td>
										            <div style="display: none" >
										            	<input type='text' name='ano' size="4" maxlength="4" style="width: 80px !important;" />
										            </div>
										            </td>
										         </tr>	 
										         <tr>
										            <td class="campoEsquerda">Data Início*:</td>
										            <td>
										            	<input type='text' name='dataInicio' id='dataInicio' size="10" maxlength="10" style="width: 100px !important;" class="Format[Date] Valid[Required,Date] Description[Data Início] text datepicker"/>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda">Data Fim*:</td>
										            <td>
										            	<input type='text' name='dataFim' id='dataFim' size="10" maxlength="10" style="width: 100px !important;" class="Format[Date] Valid[Required,Date] Description[Data Fim] text datepicker"/>
										            </td>
										         </tr>
	        									<tr>
										            <td class="campoEsquerda">Quantidade*:</td>
										            <td>
										            	<input type='text' name='quantidade' id='quantidade' size="10" maxlength="4" style="width: 40px !important;" onblur="alterarValorAtiviade();" class="Format[Numero] Valid[Required] Description[Quantidade]"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'>Tarefa/Demanda:</td>
										            <td>
										            	<textarea name="demanda" cols='120' rows='5' style="border: 1px solid black"></textarea>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'>Objetivo:</td>
										            <td>
										            	<textarea name="objetivo" cols='120' rows='5' style="border: 1px solid black"></textarea>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda">Situação:*</td>
										            <td>
										            	<select name='situacaoOS' id='situacaoOS' class="Valid[Required] Description[Situação]" ></select>
										            </td>
										         </tr>
										         <tr>
										         	<td></td>
										         	<td>
											         	
													</td>
										         </tr>
										         <tr>
										         	<td colspan="2">
										         		<cit:grid id="GRID_ITENS" columnHeaders="Item;Complexidade;Atividade;Custo Total;Qtde executada;Glosa" styleCells="linhaGrid">
										         			<cit:column idGrid="GRID_ITENS" number="001">
									         					<span id='item#SEQ#' style="border: none; backtext-align: center; font-weight: bold; background-color: white;"></span>
									         				</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="002">
										         				<input type='hidden' name='idAtividadesOS#SEQ#' id='idAtividadesOS#SEQ#' size='12' maxlength='14' />
										         				<select name='complexidade#SEQ#' id='complexidade#SEQ#'>
										         					<option value='B'>Baixa</option>
										         					<option value='I'>Intermediária</option>
										         					<option value='M'>Mediana</option>
										         					<option value='A'>Alta</option>
										         					<option value='E'>Especialista</option>
										         				</select>
										         			</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="003" >
										         				<div style="width: 100%; height: 234px;">
										         					<div style="border-bottom:1px solid black;">
											         					<div id="divDemanda#SEQ#" style="width: 600px; height: 74px; overflow: auto;" ></div>
																		<div id="divPopUpAssociacaoInc#SEQ#" style="display: none;"></div>
											         					<input type="hidden" name="demanda#SEQ#" />
											         					<input type="hidden" name="formula#SEQ#" />
											         				</div>
											         				<input type='hidden' name='idServicoContratoContabil#SEQ#' id='idServicoContratoContabil#SEQ#'/>
											         				<div id="divAssociacaoInc#SEQ#" style="border-bottom:1px solid black; display: none;"></div>
										         					<div style="overflow: auto;">
											         					<div style="width: 10px; border: 5px; padding: 5px; font-weight: bold;">
											         						 Observações: 
											         					</div>
											         						<textarea style="width: 600px; height:auto;border: none;" name="obs#SEQ#" cols='45' rows='5'></textarea> 
											         					</div>
										         					</div>
										         			</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="004">
										         				<input type='text' name='quantidade#SEQ#' style="border: none; text-align: center; font-weight: bold;" size='12' maxlength='14' class='Format[Moeda]'/>
										         			</cit:column>
										         			<cit:column idGrid="GRID_ITENS" number="005">
										         				<input type='text' name='qtdeExecutada#SEQ#' onchange="serealizaCustoExecutado();" style="border: none; text-align: center; font-weight: bold;" size='12' maxlength='14' class='Format[Moeda]'/>
										         			</cit:column>												         			
										         			<cit:column idGrid="GRID_ITENS" number="006">
										         				<input type='text' name='glosa#SEQ#' style="border: none; text-align: center; font-weight: bold;" size='12' maxlength='14' class='Format[Moeda]' value="0,00"/>
										         			</cit:column>												         			
										         		</cit:grid>
										         	</td>
										         </tr>
										         <tr>
											     	<td colspan="2" style='text-align: right; border:2px solid black; '>
											     		<table width="99%" >
											     			<tr>
											     				<th style='text-align: right; width: 90%; padding-bottom: 10px;'>Custo Total Previsto: </th>
											     				<td style='text-align: right;'>
											     					<span id='custoTotalPrevisto'><b>0,00</b></span>
											     				</td>
											     			</tr>
											     			<tr>
											     				<th style='text-align: right; width: 90%;'>Custo Total Executado: </th>
											     				<td style='text-align: right;'>
											     					<input style="color: black; text-align:right; font-weight: bold; background: none; border: none;" name="executadoOS" id="executadoOS" value="0,00"/>
											     				</td>
											     			</tr>
											     		</table> 
											     	</td>
											     </tr>
										         <tr>
										         	<td colspan="2" style='text-align: right;'>
										         		<div id='divBotaoGlosaOS' style='display:none; text-align: right; width: 100%'>
										         		<!-- 	<input type='button' name='btnAddGlosaOS' id='btnAddGlosaOS' value='Adicionar Glosa na O.S.' onclick="GRID_GLOSAS.addRow();" /> -->
										         		<button type='button' id="btnAddGlosaOS" name='btnAddGlosaOS' style="margin-top: 5px; margin-left: 3px; display: none;" class="light img_icon has_text"  onclick="carregaGlosas();">
															<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
															<span style="font-size: 12px !important;">Adicionar Glosa na O.S.</span>
														</button>
										         		</div>
										         	</td>
										         </tr>											         
										         <tr>
										         	<td colspan="2">
										         		<div id='divGlosas' style='display:none'>
											         		<cit:grid id="GRID_GLOSAS" columnHeaders="Descrição da Glosa aplicada na O.S.;Número de Ocorrências;Detalhamento da Ocorrência;% aplicado;Custo da Glosa" styleCells="linhaGrid">
											         			<cit:column idGrid="GRID_GLOSAS" number="001">
											         				<input type='hidden' name='idGlosaOS#SEQ#' id='idGlosaOS#SEQ#'/><textarea name="descricaoGlosa#SEQ#" id="descricaoGlosa#SEQ#" cols='45' rows='5'></textarea> 
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="002">
											         				<input type='text' name='numeroOcorrencias#SEQ#' id='numeroOcorrencias#SEQ#' size='12' maxlength='14' class='Format[Numero]'/>
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="003">
											         				<textarea name="ocorrencias#SEQ#" id="ocorrencias#SEQ#" cols='45' rows='5'></textarea> 
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="004">
											         				<input type='text' name='percAplicado#SEQ#' id='percAplicado#SEQ#' size='12' maxlength='14' class='Format[Moeda]' value="0,00" />
											         			</cit:column>
											         			<cit:column idGrid="GRID_GLOSAS" number="005">
											         				<input type='text' name='custoGlosa#SEQ#' id='custoGlosa#SEQ#' size='12' maxlength='14' class='Format[Moeda]' value="0,00" />
											         			</cit:column>
											         		</cit:grid>
											         		<span style='color: red'>&nbsp;Atenção: Os valores relativos ao custo da Glosa serão atualizados somente após a gravação da OS.</span>										         		
										         		</div>
										         	</td>
										         </tr>
										         <tr>
											     	<td colspan="2" style='text-align: right; border:2px solid black; '>
											     		<table width="99%" >
											     			<tr>
											     				<th style='text-align: right; width: 90%;'>
											     					Custo Total da Glosa:
											     				</th>
											     				<td style='text-align: right;'>
											     					<span id='custoTotalGlosa'><b>0,00</b></span>
											     				</td>
											     			</tr>
											     		</table> 
											     	</td>
											     </tr>
										         <tr>
										            <th style='vertical-align: middle;'>Observações finais (será apresentada no RA):</th>
										            <td>
										            	<textarea name="obsFinalizacao" id="obsFinalizacao" cols='120' rows='5' style="border: 1px solid black; margin-top: 10px;"></textarea>
										            </td>
										         </tr>											         
											 <tr>
									            <td colspan="2" class="campoObrigatorio"> Campos com preenchimento obrigat&oacute;rio</td>
									         </tr>
									         <tr>
									         	<td colspan='2'>
									         	<!-- 	<button type='button' name='btnGravar' id='btnGravar' onclick='gravarForm();'>Gravar</button> -->
									         	 	<button type='button' id="btnGravar" name='btnGravar' style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="gravarForm()">
														<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
														<span>Gravar Dados</span>
													</button>
								         		</td>
								         	</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>
	
	<div id='POPUP_LISTA_INCIDENTES' style='display:none;'>
		<form name='formAssociar' action='"+ Constantes.getValue("SERVER_ADDRESS") + request.getContextPath()+ "/pages/osSetSituacao/osSetSituacao'>
			<div id='conteudoPopUp'></div>
		</form>
	</div>
	
	<script type="text/javascript">
		document.form.onClear = function(){
			GRID_ITENS.deleteAllRows();
		};
	</script>
</body>
</html>
							