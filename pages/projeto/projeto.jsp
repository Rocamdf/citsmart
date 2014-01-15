<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
%>
<html>

<head>
	<%
		response.setHeader( "Cache-Control", "no-cache");
		response.setHeader( "Pragma", "no-cache");
		response.setDateHeader ( "Expires", -1);
	%>
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	
	<style type="text/css">
		#adicionarMudanca{
			width: 95%!important;
		}
		#adicionarLiberacao{
			width: 95%!important;
		}
	</style>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>


<script>
	var objTab = null;
	
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}
	$(function() {
		$("#POPUP_EMPREGADO").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
		$("#POPUP_EMPREGADO_ASSINATURA").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
/* Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 10:00 - ID Citsmart: 120948 - 
* Motivo/Comentário: Lookup com padrão de popup antigo/ criando popups modelo jqueryui */	
		$("#POPUP_MUDANCA").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
		$("#POPUP_LIBERACAO").dialog({
			autoOpen : false,
			width : 650,
			height : 500,
			modal : true,
			show: "fade",
			hide: "fade"
		});
		$("#POPUP_REG_AUT_MUDANCA").dialog({
			autoOpen : false,
			width : 650,
			height : 250,
			modal : true,
			show: "fade",
			hide: "fade"
		});		
		
		$("#addRecurso").click(function() {
			$("#POPUP_EMPREGADO").dialog("open");
		});		
		
		$("#addAssinaturasAprovacoes").click(function() {
			$("#POPUP_EMPREGADO_ASSINATURA").dialog("open");
		});
		$("#adicionarMudanca").click(function() {
			$("#POPUP_MUDANCA").dialog("open");
		});
		$("#adicionarLiberacao").click(function() {
			$("#POPUP_LIBERACAO").dialog("open");
		});		
	});	
</script>

<!-- Area de JavaScripts -->
<script>
	var contRecurso = 0;
	var contRecursosAssinatura = 0;
	function LOOKUP_PROJETO_select(id,desc){
		document.form.restore({idProjeto:id});
	}
	function LOOKUP_EMPREGADO_select(id, desc){
		addLinhaTabelaRecurso(id, desc, '', true);
	}	
	function LOOKUP_EMPREGADO_ASSINATURA_select(id, desc){
		addLinhaTabelaAssinaturaAprovacao(id, desc, '', '', true);
	}
	function LOOKUP_MUDANCA_select(id, desc){
		document.form.idRequisicaoMudanca.value = id;
		document.form.adicionarMudanca.value = desc;
		$("#POPUP_MUDANCA").dialog("close");
	}
	function LOOKUP_LIBERACAO_select(id, desc){
		document.form.idLiberacao.value = id;
		document.form.adicionarLiberacao.value = desc;
		$("#POPUP_LIBERACAO").dialog("close");
	}	
	function addLinhaTabelaRecurso(idEmpregado, nome, valor, valida){
		var tbl = document.getElementById('tabelaRecurso');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
	 	if (valida){
			if (!validaAddLinhatabelaRecurso(lastRow, idEmpregado)){
				return;
			}
		} 
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contRecurso++;
		coluna.innerHTML = '<img id="imgDelEmpregado' + contRecurso + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaRecurso\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = nome + '<input type="hidden" id="idEmpregado' + contRecurso + '" name="idEmpregado" value="' + idEmpregado + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" id="custoHora' + idEmpregado + '"  name="custoHora" value="' + valor + '"/>';

		addEvent(document.getElementById('custoHora' + idEmpregado), 
				"keydown", 
				DEFINEALLPAGES_formataMoeda, 
				false);
		addEvent(document.getElementById('custoHora' + idEmpregado), 
				"blur", 
				DEFINEALLPAGES_formataMoedaSaidaCampo, 
				false);	
		
		$("#POPUP_EMPREGADO").dialog("close");
	}	
	
	function addLinhaTabelaAssinaturaAprovacao(idEmpregado, nome, papel, ordem, valida){
		var tbl = document.getElementById('tabelaAssinaturasAprovacoes');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
	 	
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contRecursosAssinatura++;
		coluna.innerHTML = '<img id="imgDelEmpregadoAssinatura' + contRecursosAssinatura + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaAssinatura(\'tabelaAssinaturasAprovacoes\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = nome + '<input type="hidden" id="idEmpregadoAssinatura' + contRecursosAssinatura + '" name="idEmpregadoAssinatura" value="' + idEmpregado + "" + contRecursosAssinatura + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" id="papel' + idEmpregado + "" + contRecursosAssinatura + '"  name="papel" value="' + papel + '"/>';
		coluna = row.insertCell(3);
		coluna.innerHTML = '<input type="text" id="ordem' + idEmpregado + "" + contRecursosAssinatura + '" class="Format[Numero]" name="ordem" value="' + ordem + '" onkeypress="return somenteNumero(event);"/>';
		
		$("#POPUP_EMPREGADO_ASSINATURA").dialog("close");
	}	
	
	function trataKeyMoney(e){
		if (isMozilla){
			element = e.target;
		}else{
			element = e.srcElement;
		}    	  
	  	FormatUtils.formataCampo(element.form, element.name, '99/99/9999', e);		
	}
	function validaAddLinhatabelaRecurso(lastRow, idEmpregadoParm){
		if (lastRow > 2){
			var arrayIdEmp = document.form.idEmpregado;
			for (var i = 0; i < arrayIdEmp.length; i++){
				if (arrayIdEmp[i].value == idEmpregadoParm){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}
				
			}
		} else if (lastRow == 2){
			var idEmp = document.form.idEmpregado;
			if (idEmp.value == idEmpregadoParm){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}	
	
	function removeLinhaTabela(idTabela, rowIndex) {
		 if (idTabela == "tabelaRecurso"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
				} 
		 }
	}	
	function removeLinhaTabelaAssinatura(idTabela, rowIndex) {
		 if (idTabela == "tabelaAssinaturasAprovacoes"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
				} 
		 }
	}	
	function deleteAllRows() {
		var tabela = document.getElementById('tabelaRecurso');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		document.getElementById('tabelaRecurso').style.display = "none";
		contRecurso = 0;
		
		var tabelaAssinatura = document.getElementById('tabelaAssinaturasAprovacoes');
		var countAssinatura = tabelaAssinatura.rows.length;

		while (countAssinatura > 1) {
			tabelaAssinatura.deleteRow(countAssinatura - 1);
			countAssinatura--;
		}
		document.getElementById('tabelaAssinaturasAprovacoes').style.display = "none";
		contRecursosAssinatura = 0;
	}	
	
	function salvar(){
		document.form.colRecursosSerialize.value = '';
		document.form.colAssinaturasSerialize.value = '';
		//serialização de recursos
		var tbl = document.getElementById('tabelaRecurso');
		var lastRow = tbl.rows.length;
		var objs = new Array();
		var x = 0;
		if (lastRow > 2){
			var arrayIdEmp = document.form.idEmpregado;
			for (var i = 0; i < arrayIdEmp.length; i++){
				var custoObj = null;
				try{
					custoObj = document.getElementById('custoHora' + arrayIdEmp[i].value);
				}catch(e){}
				var custoValue = '0';
				if (custoObj != null){
					try{
						custoValue = custoObj.value;
					}catch(e){}
				}
				objs[x] = {idEmpregado:arrayIdEmp[i].value,custoHora:custoValue};
				x++;
			}
		} else if (lastRow == 2){
			var idEmp = document.form.idEmpregado;
			var custoObj = null;
			try{
				custoObj = document.getElementById('custoHora' + idEmp.value);
			}catch(e){}
			var custoValue = '0';
			if (custoObj != null){
				try{
					custoValue = custoObj.value;
				}catch(e){}
			}			
			objs[x] = {idEmpregado:idEmp.value,custoHora:custoValue};
			x++;			
		}		
      	var objsSerializados = ObjectUtils.serializeObjects(objs);		
		document.form.colRecursosSerialize.value = objsSerializados;	
		
		//serialização de assinaturas
		var tblAssinatura = document.getElementById('tabelaAssinaturasAprovacoes');
		var lastRowAssinatura = tblAssinatura.rows.length;
		var objsAssinatura = new Array();
		var y = 0;
		if (lastRowAssinatura > 2){
			var arrayIdEmpAssinatura = document.form.idEmpregadoAssinatura;
			for (var i = 0; i < arrayIdEmpAssinatura.length; i++){
				var papelObj = null;
				var ordemObj = null;
				try{
					papelObj = document.getElementById('papel' + arrayIdEmpAssinatura[i].value);
					ordemObj = document.getElementById('ordem' + arrayIdEmpAssinatura[i].value);
				}catch(e){}
				var papelValue = ' ';
				var ordemValue = ' ';
				if (papelObj != null){
					try{
						papelValue = papelObj.value;
					}catch(e){}
				}
				if (ordemObj != null){
					try{
						ordemValue = ordemObj.value;
						var ordemInt = parseInt(ordemObj.value);
						if(isNaN(ordemInt)){
							alert(i18n_message("projeto.ordemSomenteNumeros"));
							return;
						}
					}catch(e){}
				}
				objsAssinatura[y] = {idEmpregadoAssinatura:arrayIdEmpAssinatura[i].value,papel:papelValue,ordem:ordemValue};
				y++;
			}
		} else if (lastRowAssinatura == 2){
			var idEmpAssinatura = document.form.idEmpregadoAssinatura;
			var papelObj = null;
			var ordemObj = null
			try{
				papelObj = document.getElementById('papel' + idEmpAssinatura.value);
				ordemObj = document.getElementById('ordem' + idEmpAssinatura.value);
			}catch(e){}
			var papelValue = ' ';
			var ordemValue = ' ';
			if (papelObj != null){
				try{
					papelValue = papelObj.value;
				}catch(e){}
			}
			if (ordemObj != null){
				try{
					ordemValue = ordemObj.value;
					var ordemInt = parseInt(ordemObj.value);
					if(isNaN(ordemInt)){
						alert(i18n_message("projeto.ordemSomenteNumeros"));
						return;
					}
				}catch(e){}
			}
			objsAssinatura[y] = {idEmpregadoAssinatura:idEmpAssinatura.value,papel:papelValue,ordem:ordemValue};
			y++;			
		}		
      	var objsAssinaturaSerializados = ObjectUtils.serializeObjects(objsAssinatura);		
		document.form.colAssinaturasSerialize.value = objsAssinaturaSerializados;	
		
		
		document.form.save();		
	}
	function limpar(){
		deleteAllRows();
		document.form.clear();	
		document.getElementById('divOS').style.display = 'none';
		document.getElementById('divLinhasBase').innerHTML = '';
	}
	function verificaVinculoOS(obj){
		if (obj.checked){
			document.getElementById('divOS').style.display = 'block';
			document.form.fireEvent('carregaInfoOS');
		}else{
			document.getElementById('divOS').style.display = 'none';
			limparVinculacoesOS();
		}
	}
	function limparMudanca(){
		document.form.idRequisicaoMudanca.value = '';
		document.form.adicionarMudanca.value = '';
		/* LOOKUP_MUDANCA.setvalue('');
		LOOKUP_MUDANCA.settext(''); */
	}
	function limparLiberacao(){
		document.form.idLiberacao.value = '';
		document.form.adicionarLiberacao.value = '';
		/* LOOKUP_LIBERACAO.setvalue('');
		LOOKUP_LIBERACAO.settext(''); */
	}	
	function registrarAutorizacao(idLnBase){
		document.formAutorizacao.idLinhaBaseProjeto.value = idLnBase;
		document.formAutorizacao.idProjetoAutorizacao.value = document.form.idProjeto.value;
		document.formAutorizacao.justificativaMudanca.value = '';
		$("#POPUP_REG_AUT_MUDANCA").dialog("open");
	}
	function salvarAutMudanca(){
		document.formAutorizacao.fireEvent('gravarAutorizMudanca');
	}
	function excluir() {
		if (document.getElementById("idProjeto").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.fireEvent("delete");
			}
		}
	}
	
	function somenteNumero(e){
		 var tecla=(window.event)?event.keyCode:e.which;
		 if((tecla>47 && tecla<58)) 
			 return true;
		 else{
			 if (tecla==8 || tecla==0) return true;
			 	else  
			 		return false;
		 }
	}
	
	function limparVinculacoesOS(){
		$('#idServicoContrato').val("");
		$('#nomeAreaRequisitante').val("");
		$('#numero').val("");
		$('#dataEmissao').val("");
		$('#demanda').val("");
		$('#objetivo').val("");
	}
	
</script>

<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>
					
		<div class="flat_area grid_16">
				<h2><i18n:message key="projeto.cadastroProjeto"/></h2>						
		</div>
  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><i18n:message key="projeto.cadastroProjeto"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><i18n:message key="citcorpore.comum.pesquisa"/></a>
				</li>
			</ul>				
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class=""> 	
									<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/projeto/projeto'>
										 	<input type='hidden' name='idProjeto'/>
										 	<input type='hidden' name='idOs'/>
										 	<input type='hidden' name='idRequisicaoMudanca'/>
										 	<input type='hidden' name='idLiberacao'/>
										 	<input type='hidden' name='colRecursosSerialize'/>
										 	<input type='hidden' name='colAssinaturasSerialize'/>
										 	<input type='hidden' name='empregadoGrupos'/>
										  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
										         <tr>
										            <td class="campoEsquerda" ><label class="campoObrigatorio"><i18n:message key="projeto.nomeProjeto" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name="nomeProjeto" maxlength="70" size="70" class="Valid[Required] Description[projeto.nomeProjeto]" />
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class=""><i18n:message key="projeto.siglaProjeto" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name="sigla" maxlength="50" size="50" class="Description[projeto.siglaProjeto]" />
										            </td>
										         </tr>										         
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="projeto.contrato" /></label></td>
										            <td colspan='2'>
										            	<select name='idContrato' class="Valid[Required] Description[projeto.contrato]">
										            	</select>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="projeto.emergencial" /></label></td>
										            <td colspan='2'>
										            	<select name='emergencial' class="Valid[Required] Description[projeto.emergencial]">
										            		<option value='N'><i18n:message key="citcorpore.comum.nao" /></option>
										            		<option value='S'><i18n:message key="citcorpore.comum.sim" /></option>
										            	</select>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="projeto.severidade" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name="severidade" maxlength="1" size="1" class="Valid[Required] Description[projeto.severidade]" />
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="projeto.nomeGestor" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name="nomeGestor" maxlength="50" size="50" class="Valid[Required] Description[projeto.nomeGestor]" />
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda"><label class=""><i18n:message key="projeto.mudanca" /></label></td>
										            <td>
										            	<%-- <cit:lookupField lockupName="LOOKUP_MUDANCA" heigth="400" left="100" len="700" htmlCode="true" top="50" javascriptCode="true" formName="form"></cit:lookupField> --%>
										            	<input type='text' id="adicionarMudanca" name="adicionarMudanca" maxlength="50" size="50" class="text" />
										            	<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/borracha.png' border='0' onclick='limparMudanca()'/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><label class=""><i18n:message key="projeto.liberacao" /></label></td>
										            <td>
										            	<%-- <cit:lookupField lockupName="LOOKUP_LIBERACAO" heigth="400" left="120" len="700" htmlCode="true" top="50" javascriptCode="true" formName="form"></cit:lookupField> --%>
										            	<input type='text' id="adicionarLiberacao" name="adicionarLiberacao" maxlength="50" size="50" class="text" />
										            	<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/borracha.png' border='0' onclick='limparLiberacao()'/>
										            </td>										            
										         </tr>										         											         									         										         										                  
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="projeto.situacao" /></label></td>
										            <td colspan='2'>
										            	<select name='situacao' class="Valid[Required] Description[projeto.situacao]">
										            	</select>
										            </td>
										         </tr>	 
										         <tr>
										            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="projeto.valorExecucaoProjeto" /></label></td>
										            <td colspan='2'>
										            	<input type='text' name='valorEstimado' size="15" maxlength="15" class="Format[Moeda] Valid[Required] Description[projeto.valorExecucaoProjeto]"/>
										            </td>
										         </tr>											                 
										         <tr>
										            <td class="campoEsquerda"><label class=""><i18n:message key="projeto.detalhesComplementar" /></label></td>
										            <td colspan='2'>
										            	<textarea name="detalhamento" cols='90' rows='5'></textarea>
										            </td>
										         </tr>	
												<!-- 	Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 10:07 - ID Citsmart: 120948 - 
														* Motivo/Comentário: Icone de adicionar dados na tabela fora do padrão/ inserido botão  -->
										         <tr>
										         	<td colspan='3'>
														<div class="col_100">
															<fieldset> 
																<button type='button' id="addRecurso" style="cursor: pointer;" title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />">
																	<i18n:message key="citcorpore.comum.adicionar" /> <i18n:message key="projeto.recursos" /><%-- <img	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"> --%>
																</button>
																<div  id="gridRecursos">
																	<table class="table table-bordered table-striped" id="tabelaRecurso" style="display: none;">
																		<tr>
																			<th style="width: 10%;"></th>
																			<th style="width: 45%;"><i18n:message  key="citcorpore.comum.nome" /></th>
																			<th style="width: 40%;"><i18n:message  key="projeto.custo" /></th>
																		</tr>
																	</table>
																</div>
															</fieldset>
														</div>										         	
										         	</td>
										         </tr> 
										         <!-- 	Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 10:07 - ID Citsmart: 120948 - 
														* Motivo/Comentário: Icone de adicionar dados na tabela fora do padrão/ inserido botão  -->
										         <tr>
										         	<td colspan='3'>
														<div class="col_100">
															<fieldset>
																<button type='button' id="addAssinaturasAprovacoes" style="cursor: pointer;" title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />">
																	<i18n:message key="citcorpore.comum.adicionar" /> <i18n:message key="projeto.assinaturasAprovacoes" /> <%-- <img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"> --%>
																</button>
																<div  id="gridAssinaturasAprovacoes">
																	<table class="table table-bordered table-striped" id="tabelaAssinaturasAprovacoes" style="display: none;">
																		<tr>
																			<th style="width: 5%;"></th>
																			<th style="width: 25%;"><i18n:message  key="citcorpore.comum.nome" /></th>
																			<th style="width: 20%;"><i18n:message  key="citcorpore.comum.papel" /></th>
																			<th style="width: 20%;" class='campoObrigatorio'><i18n:message  key="projeto.ordem" /></th>
																		</tr>
																	</table>
																</div>
															</fieldset>
														</div>										         	
										         	</td>
										         </tr> 
										         <tr>
										            <td colspan='3'>
										            	<input type='checkbox' id='vinculoOS' name='vinculoOS' value="S" onclick='verificaVinculoOS(this)'/><b><i18n:message  key="projeto.vincularOrdemServico" /></b>
										            </td>
										         </tr>										            
										         <tr>
										         	<td colspan='3'>
														<div class="col_100" id='divOS' style='display: none'>
															<table id="tabFormulario" cellpadding="0" cellspacing="0">
														         <tr>
														            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="problema.servico" /></label></td>
														            <td>
														            	<select id="idServicoContrato" name='idServicoContrato' class="Description[problema.servico]"></select>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda" style="visibility:hidden; display: none;"><i18n:message key="citcorpore.comum.ano" />*:</td>
														            <td>
														            	<input type='text' style="visibility:hidden; display: none;" name='ano' size="4" maxlength="4" style="width: 80px !important;" class="Format[Numero] Description[citcorpore.comum.ano] text"/>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="citcorpore.comum.numero" /></label></td>
														            <td>
														            	<input type='text' name='numero' size="20" maxlength="20" style="width: 250px !important;" class="Description[citcorpore.comum.numero] text"/>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda"><i18n:message key="projeto.dataEmissao" /></td>
														            <td>
														            	<input type='text' name='dataEmissao' size="10" maxlength="10" style="width: 250px !important;" class="Format[Date] datepicker Valid[Date] Description[projeto.dataEmissao] text"/>
														            </td>
														         </tr>														         
														         <tr>
														            <td class="campoEsquerda"><label class="campoObrigatorio"><i18n:message key="citcorpore.comum.areaRequisitante" /></label></td>
														            <td>
														            	<input type='text' id='nomeAreaRequisitante' name='nomeAreaRequisitante' size="80" maxlength="80" style="width: 500px !important;" class="Description[citcorpore.comum.areaRequisitante] text"/>
														            </td>
														         </tr>											         
														         <tr>
														            <td class="campoEsquerda" style='vertical-align: middle;'><i18n:message key="citcorpore.ui.tabela.coluna.Tarefa_Demanda" />:</td>
														            <td>
														            	<textarea name="demanda" cols='120' rows='5' style="border: 1px solid black;"></textarea>
														            </td>
														         </tr>
														         <tr>
														            <td class="campoEsquerda" style='vertical-align: middle;'><i18n:message key="planoMelhoria.objetivo" />:</td>
														            <td>
														            	<textarea name="objetivo" cols='120' rows='5' style="border: 1px solid black;"></textarea>
														            </td>
														         </tr>															
															</table>															
														</div>								              	         	                                    		                                            														</div>										         	
										         	</td>
										         </tr>
										         <tr>
										         	<td colspan='3'>
										         		<h4><i18n:message key="projeto.linhadebase" /></h4><br>
														<div class="col_100" id='divLinhasBase'>
														</div>
													</td>
												</tr>										         
										         <tr>
										         	<td colspan='3'>
														<button type='button' name='btnGravar' class="light" onclick='salvar();'>
															<img
																src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
															<span><i18n:message key="citcorpore.comum.gravar" />
															</span>
														</button>
														<button type="button" name='btnLimpar' class="light" onclick='limpar();'>
															<img
																src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
															<span><i18n:message key="citcorpore.comum.limpar" />
															</span>
														</button>
														<button type="button" name="btnExcluir" class="light" onclick="excluir();">
										                   <img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/trashcan.png" />
										                   <span><i18n:message key="citcorpore.comum.excluir" />
										                   </span>
									                    </button>	
									         		</td>
									         	</tr>
											</table>
									</form>
								</div>	
							</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_PROJETO' id='LOOKUP_PROJETO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>								
					<!-- ## FIM - AREA DA APLICACAO ## -->							
	 </div>
  </div>				
 </div>
<!-- Fim da Pagina de Conteudo -->
</div>

	<div id="POPUP_EMPREGADO" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaEmp'>
				<cit:findField formName='formPesquisaEmp' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	
	<div id="POPUP_EMPREGADO_ASSINATURA" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaAssinaturaEmpregado'>
				<cit:findField formName='formPesquisaAssinaturaEmpregado' lockupName='LOOKUP_EMPREGADO_ASSINATURA' id='LOOKUP_EMPREGADO_ASSINATURA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
		<div id="POPUP_MUDANCA" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaMud'>
				<cit:findField formName='formPesquisaMud' lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
		<div id="POPUP_LIBERACAO" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaLib'>
				<cit:findField formName='formPesquisaLib' lockupName='LOOKUP_lIBERACAO' id='LOOKUP_LIBERACAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	
	<div id="POPUP_REG_AUT_MUDANCA" title="Registrar Autorização de mudança">
		<form name='formAutorizacao' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/projeto/projeto'>
			<input type='hidden' name='idProjetoAutorizacao' id='idProjetoAutorizacao'/>
			<input type='hidden' name='idLinhaBaseProjeto' id='idLinhaBaseProjeto'/>
			<table id="tabFormulario" cellpadding="0" cellspacing="0">
		         <tr>
		            <td class="campoEsquerda"><i18n:message key="projeto.justificativaMudanca" /></td>
		            <td>
		            	<textarea rows="4" cols="70" name="justificativaMudanca" id="justificativaMudanca"></textarea>
		            </td>
		         </tr>
		         <tr>
		         	<td colspan='2'>
						<button type='button' name='btnGravar' class="light" onclick='salvarAutMudanca();'>
							<img
								src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
							<span><i18n:message key="citcorpore.comum.gravar" />
							</span>
						</button>
	         		</td>
	         	</tr>		         
		    </table>			 	
		</form>		
	</div>
	
		<%@include file="/include/footer.jsp"%>
</body>
</html>
							