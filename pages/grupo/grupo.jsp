<%@page import="br.com.centralit.bpm.dto.TipoFluxoDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>
<%@ page import="br.com.centralit.citcorpore.free.Free" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
    String iframe = "";
    iframe = request.getParameter("iframe");
    
    Collection tiposFluxos = (Collection)request.getAttribute("tiposFluxos");
    if (tiposFluxos == null){
		tiposFluxos = new ArrayList();
    }
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include  file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script charset="ISO-8859-1"  type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>

<script type="text/javascript" src="../../cit/objects/GrupoEmpregadoDTO.js"></script>

<style type="text/css">
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
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
	}
	
	.cancelar {
		color: #FF0000;
		content: "*";
	}
	
	
	
/* Desenvolvedor: Pedro Lino - Data: 31/10/2013 - Horário: 10:00 - ID Citsmart: 120948 - 
* Motivo/Comentário: Paginação com layout antigo / Layout com as cores padrao */

/* Paginação */
	#itenPaginacaoColaborador ul li {
		padding:  0px!important;
		
	}
	
	#itenPaginacaoColaborador ul li font {
		padding:  6px!important;
		background-color: white!important;
		border: 2px  solid #ddd!important;
		
	}
	#itenPaginacaoColaborador ul li font:HOVER {
		border: 2px  solid #74AF3B!important;
		
	}
	
	#itenPaginacaoColaborador ul li font a{
		color: #74AF3B!important;
	}
	
	
	
	
</style>
<script>
	var contEmpregado = 0;
	var contEmail = 0;
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}
	
	function validaEmail(mail) {
		var prim = mail.value.indexOf("@");
		if(prim < 2) {
			return false;
		}
		if(mail.value.indexOf("@",prim + 1) != -1) {
			return false;
		}
		if(mail.value.indexOf(".") < 1) {
			return false;
		}
		if(mail.value.indexOf(" ") != -1) {
			return false;
		}
		if(mail.value.indexOf("zipmeil.com") > 0) {
			return false;
		}
		if(mail.value.indexOf("hotmeil.com") > 0) {
			return false;
		}
		if(mail.value.indexOf(".@") > 0) {
			return false;
		}
		if(mail.value.indexOf("@.") > 0) {
			return false;
		}
		if(mail.value.indexOf(".com.br.") > 0) {
			return false;
		}
		if(mail.value.indexOf("/") > 0) {
			return false;
		}
		if(mail.value.indexOf("[") > 0) {
			return false;
		}
		if(mail.value.indexOf("]") > 0) {
			return false;
		}
		if(mail.value.indexOf("(") > 0) {
			return false;
		}
		if(mail.value.indexOf(")") > 0) {
			return false;
		}
		if(mail.value.indexOf("..") > 0) {
			return false;
		}
	
		return true;
}
	
	function LOOKUP_EMPREGADO_select(id, desc){
		document.form.iddEmpregado.value = id;
		document.form.descEmpregado.value = desc;
		document.form.fireEvent("manipulaEmpregado");
	}
	
	function LOOKUP_EMPREGADO_EMAIL_select(id, desc) {

		document.formEmail.idEmpregado.value = id;
		document.formEmail.fireEvent("restoreEmpregadoEmail");
	}
	
	function complementoRestore(){
		var nome = document.formEmail.nomeEmail.value
		var email = document.formEmail.emailExtra.value
		if(!validaEmail(document.formEmail.emailExtra)){
			alert (i18n_message("grupo.emailInvalidoColaborador"));
			document.formEmail.clear();
		return;
		} 
		else{
		var id = document.formEmail.idEmpregado.value
		addLinhaTabelaEmail(id,nome,email, true );
		}
		document.formEmail.clear();
	}
	
	function LOOKUP_GRUPO_select(id, desc) {
		JANELA_AGUARDE_MENU.show();
		contColuna=1;
		document.form.restore({idGrupo : id});
		document.getElementById('paginas').style.display = "block";
	}
	function excluir() {
		if (document.getElementById("idGrupo").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}
	
	function gravar(){	
		document.form.nome.value = document.form.nome.value.replace(/'/g,"");
		/* if (document.getElementById("serviceDesk").value == "-- Selecione --"){
			alert("Por favor, selecione se o grupo é do Service Desk");
		} */
		if (document.getElementById("serviceDesk").value == i18n_message("citcorpore.comum.selecione") ){
			alert(i18n_message("grupo.serviceDeskObrigatorio"));
		}
		else if (document.getElementById("idPerfilAcessoGrupo").value != ""){
			JANELA_AGUARDE_MENU.show();
			//so serealiza a tabela empregado quando o idGrupo não existe, por causa da paginação
			if(document.getElementById("idGrupo").value ==""){
			    serializa();
			}else{
				//Trata os elementos que estao na pagina;
				serializaAux();
				document.form.fireEvent("gravarEmail");
			}
			serializa2();
			
			document.form.save();
			contEmpregado = 0;
			contEmail = 0;
		}else{
			alert(i18n_message("grupo.perfilAcessoObrigatorio"))
		} 			
	}
	
	function gravarEmail(){
			var nome = document.formEmail.nomeEmail.value
			var email = document.formEmail.emailExtra.value
			if (nome == "") {
				alert (i18n_message("grupo.nomeCampoObrigatorio"));
				return;
			}
			else if (email == ""){
				alert (i18n_message("grupo.emailCampoObrigatorio"));
				return;
			}
			else if(!validaEmail(document.formEmail.emailExtra)){
				alert (i18n_message("grupo.emailInvalido"));
			document.formEmail.emailExtra.value = "";
			return;
			} 
			else{
				var id = document.formEmail.idEmpregado.value
				addLinhaTabelaEmail(id,nome,email, true );
			 document.formEmail.clear();
			}

	}
	
	var contColuna = 1;
	function addLinhaTabelaEmpregado(id, desc, valida){
		var tbl = document.getElementById('tabelaEmpregado');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaEmpregado(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contEmpregado++;
		coluna.innerHTML = '<img id="imgDelEmpregado' + contColuna + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaEmpregado\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc + '<input type="hidden" id="idEmpregado' + contColuna + '" name="idEmpregado" value="' + id + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input  type="checkbox" id="enviaEmail' + contColuna + '"  name="enviaEmail" value= "S" />';
		contColuna++;

		$("#POPUP_EMPREGADO").dialog("close");
		
	}
	
	function addLinhaTabelaEmail(id, descNome, descEmail, valida){
		var tbl = document.getElementById('tabelaEmail');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
	 	if (valida){
			if (!validaAddLinhaTabelaEmail(lastRow, descEmail)){
				return;
			}
		} 
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contEmail++;
		coluna.innerHTML = '<img id="imgDelEmpregado' + contEmail + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaEmail\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = descNome + '<input type="hidden" id="idNomeEmail' + contEmail + '" name="idNomeEmail" value="' + descNome + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = descEmail + '<input type="hidden" id="idEmail' + contEmail + '"  name="idEmail" value="' + descEmail + '" />';
		coluna = row.insertCell(3);
		if (id != "" || id != 0){
			coluna.innerHTML = "<i18n:message key="grupo.colaborador" />" + '<input type = "hidden" id = "idEmpregadoEmail' + contEmail + '" name = "idEmpregadoEmail" value ="' + id + '" />';
		}
		else{
			coluna.innerHTML = "<i18n:message key="grupo.externo" />" + '<input type = "hidden" id = "idEmpregadoEmail' + contEmail + '" name = "idEmpregadoEmail" value ="' + id + '" />';
		}
			

		$("#POPUP_EMAIL").dialog("close");
	}
	
	function validaAddLinhaTabelaEmpregado(lastRow, id){
		if (lastRow > 2){
			var arrayIdEmpregado = document.form.idEmpregado;
			for (var i = 0; i < arrayIdEmpregado.length; i++){
				if (arrayIdEmpregado[i].value == id){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}
				
			}
		} else if (lastRow == 2){
			var idEmpregado = document.form.idEmpregado;
			if (idEmpregado.value == id){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}
	
	function validaAddLinhaTabelaEmail(lastRow, email){
		if (lastRow > 2){
			var arrayEmail = document.form.idEmail;
			for (var i = 0; i < arrayEmail.length; i++){
				if (arrayEmail[i].value == email){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}
				
			}
		} else if (lastRow == 2){
			var idEmail = document.form.idEmail;
			if (idEmail.value == email){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}
 	
 	
 	function GrupoEmpregadoDTO(_id, i){
 		if  ($('#enviaEmail' + i).is(":checked")){
			this.enviaEmail = "S";
		}else{
			this.enviaEmail = "N";
		}
 		this.idEmpregado = _id; 
 	}
 	
 	function GrupoEmailDTO(_id,i){
			this.email = _id;
			this.nome = document.getElementById('idNomeEmail' + i).value;	
			this.idEmpregado = document.getElementById('idEmpregadoEmail' + i).value;
 	}
 	
 	function serializa(){
 		var tabela = document.getElementById('tabelaEmpregado');
 		var count = contEmpregado + 1;
 		var listaDeEmpregados = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null){
 			var trObj = document.getElementById('idEmpregado' + i).value;
 			var empregado = new GrupoEmpregadoDTO(trObj, i);
 			listaDeEmpregados.push(empregado);
 			}
 		} 	
 		var ser = ObjectUtils.serializeObjects(listaDeEmpregados);
		document.form.empregadosSerializados.value = ser;
 	}
 	
 	function serializaAux(){
 		var tabela = document.getElementById('tabelaEmpregado');
 		var count = contEmpregado + 1;
 		var listaDeEmpregados = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null){
 			var trObj = document.getElementById('idEmpregado' + i).value;
 			var empregado = new GrupoEmpregadoDTO(trObj, i);
 			listaDeEmpregados.push(empregado);
 			}
 		} 	
 		var ser = ObjectUtils.serializeObjects(listaDeEmpregados);
		document.form.empregadosSerializadosAux.value = ser;
 	}
 
 	 function serializa2(){
		var tabela = document.getElementById('tabelaEmail');
		var count = contEmail + 1;
		var listaDeEmails = [];
		for(var i = 1; i < count ; i++){
			try{
				if (document.getElementById('idEmail' + i) != "" && document.getElementById('idEmail' + i) != null){
				
					var trObj = document.getElementById('idEmail' + i).value;
					var email = new GrupoEmailDTO(trObj, i);
					listaDeEmails.push(email);
				}
			}catch(e){}
		} 	
		var ser = ObjectUtils.serializeObjects(listaDeEmails);
		document.form.emailsSerializados.value = ser;
	}

 	
	function removeLinhaTabela(idTabela, rowIndex) {
		 if(idTabela == "tabelaEmpregado"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.empregadoGrupos.value = eval('document.form.idEmpregado' + rowIndex + '.value');
					contColuna = 1;
					document.form.fireEvent("deleteEmpregado");
					HTMLUtils.deleteRow(idTabela, rowIndex);				
					document.form.empregadoGrupos.value = '';
					//document.form.empregadosSerializadosAux.value = eval('document.form.idEmpregado' + rowIndex + '.value');				
				} 
		 }
		 else{ 
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
 				document.form.emailGrupos.value = eval('document.getElementById("idEmail' + rowIndex + '").value');
				document.form.fireEvent("deleteEmail"); 
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.emailsSerializados.value = eval('document.getElementById("idEmail' + rowIndex + '").value');
				contEmail = 0;
		 	}
		 }
	}

	function deleteAllRows() {
		var tabela = document.getElementById('tabelaEmpregado');
		var tabela1 = document.getElementById('tabelaEmail');
		var count = tabela.rows.length;
		var count1 = tabela1.rows.length

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
			while (count1 > 1) {
				tabela1.deleteRow(count1 - 1);
				count1--;
		}
		document.getElementById('tabelaEmpregado').style.display = "none";
		document.getElementById('tabelaEmail').style.display = "none";
	}
	
	function visualizarPermissoes() {
		document.getElementById('permissoes').style.display = 'block';
		document.getElementById('tipoPermissao').style.display = 'block';
	}
	
	function ocultarPermissoes() {
		document.getElementById('permissoes').style.display = 'none';
		document.getElementById('tipoPermissao').style.display = 'none';
	}
	
	$(function() {
		$("#POPUP_EMPREGADO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});
	
	$(function() {
		$("#POPUP_EMAIL").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});
	
	$(function() {
		$("#addEmpregado").click(function() {
			$("#POPUP_EMPREGADO").dialog("open");
		});
	});
	
	$(function() {
		$("#addEmail").click(function() {
			$("#POPUP_EMAIL").dialog("open");
		});
	});
	
	function limpar() {
		deleteAllRows();
		document.form.clear();
		document.getElementById('paginas').style.display = "none";
		contColuna = 1;
	}
	
	function limparEmail(){
		document.formEmail.clear();
	}
	
	function exibirGrid(){
		document.getElementById('gridEmpregados').style.display = 'block';
	}
	
	function exibirGrid1(){
		document.getElementById('gridEmails').style.display = 'block';
	}
	
	function addEmpregado(){
			$("#POPUP_EMPREGADO").dialog("open");		
	}
	function check() {
		var tabela = document.getElementById('tabelaEmpregado');
		var count = tabela.rows.length;
		if ($('#emailTodos').is(":checked")) {
			for ( var i = 1; i < count; i++) {
				$('#enviaEmail' + i).attr('checked', true);
			}
		} else {
			for ( var i = 1; i < count; i++) {
				$('#enviaEmail' + i).attr('checked', false);
			}
		}
	}
	
	function marcarTodos(){
		classe = 'perm';
		if ($("#checkboxCheckAll").is(':checked')) {
			$("." + classe).each(function() {
				$(this).attr("checked", true);
			});					 
		}else {
			$("." + classe).each(function() {
					$(this).attr("checked", false);
			});
		}
	}
	
	ValidacaoUtils.validaRequired = function(field, label){
		var bTexto = false;
		
		if (HTMLUtils._isHTMLElement(field, "input")){
			if (field.type == "text"){
				bTexto = true;
			}
		}
		
		if (bTexto){
			if(StringUtils.isBlank(field.value)){
				alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
				try{
					field.focus();
					JANELA_AGUARDE_MENU.hide();
				}catch(e){
				}
			    return false;		
			}
		}else{
			if(StringUtils.isBlank(HTMLUtils.getValue(field.id, field.form))){
				alert(label + i18n_message("citcorpore.comum.campo_obrigatorio"));
				JANELA_AGUARDE_MENU.hide();
				try{
					field.focus();
				}catch(e){
				}
			    return false;
			}
		}
		return true;
	}
	
	//Mudança na paginação
	function paginarItens(paginaSelecionadaColaborador) {
		//Página selecionada
		document.form.paginaSelecionadaColaborador.value = paginaSelecionadaColaborador;
		//Tratando a página que mudou
		serializaAux();
		document.form.fireEvent("gravarEmail");
		//Preenche a tabela com a próxima página
		document.form.fireEvent("preencheTabelaColaborador");
		
		contColuna = 1;
		contEmpregado = 0;
	}
	
	function deleteEmpregadoRows() {
		var tabela = document.getElementById('tabelaEmpregado');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		document.getElementById('tabelaEmpregado').style.display = "block";
	}
</script>

<%
    //se for chamado por iframe deixa apenas a parte de cadastro da pÃ¡gina
    if (iframe != null) {
%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

<%
    }
%>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<!-- 	<script type="text/javascript" src="../../cit/objects/EmpregadoDTO.js"></script> -->
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
				<h2>
					<i18n:message key="grupo.grupo" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="grupo.cadastroDeGrupo" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="grupo.pesquisaGrupo" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/grupo/grupo'>
								<div class="columns clearfix">
									<input type='hidden' name='idGrupo' /> 
									<input type='hidden' name='idEmpresa'/> 
									<input type="hidden" id="dataInicio" name="dataInicio" /> 
									<input type="hidden" id="dataFim" name="dataFim" />
									<input type="hidden" id="empregadosSerializados" name="empregadosSerializados" />
									<input type="hidden" id="empregadosSerializadosAux" name="empregadosSerializadosAux" />
									<input type="hidden" id="emailsSerializados" name="emailsSerializados" />									
									<input type="hidden" id="empregadoGrupos" name="empregadoGrupos" />
									<input type="hidden" id="emailGrupos" name="emailGrupos" />
									<input type='hidden' name='paginaSelecionadaColaborador' id='paginaSelecionadaColaborador'/>
									<input type="hidden" id="iddEmpregado" name="iddEmpregado" />
									<input type="hidden" id="descEmpregado" name="descEmpregado" />
									
									
									<div class="col_100">
									<div class="col_25">
										<fieldset>
											<label  class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome"/>
											</label>
											<div>
												<input type='text' name="nome" maxlength="50" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio"> <i18n:message key="grupo.perfilAcesso" /> </label>
											<div>
												<select id='idPerfilAcessoGrupo' name='idPerfilAcessoGrupo' class=" Description[grupo.perfilAcesso]">
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio"><i18n:message key="grupo.serviceDesk" /></label>
											<div>
												<select id='serviceDesk' name='serviceDesk' class=" Valid[Required] Description[grupo.serviceDesk]">
													<option><i18n:message key="citcorpore.comum.selecione" /></option>
													<option value='N'><i18n:message key="citcorpore.comum.nao" /></option>
													<option value='S'><i18n:message key="citcorpore.comum.sim" /></option>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 61px">
											<label><i18n:message key="grupo.comiteConsultivoMudanca" /></label>
											<div>
												<%
													if(br.com.citframework.util.Util.isVersionFree(request)){												
												%>
													<%=Free.getMsgCampoIndisponivel(request)%>
												<%
													} else {
												%>
													<select id='comiteConsultivoMudanca' name='comiteConsultivoMudanca'>
														<option value=""><i18n:message key="citcorpore.comum.selecione" /></option>
														<option value='N'><i18n:message key="citcorpore.comum.nao" /></option>
														<option value='S'><i18n:message key="citcorpore.comum.sim" /></option>
													</select>
												<%
													}
												%>
											</div>
										</fieldset>
									</div>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="grupo.sigla" />
											</label>
											<div>
												<input type='text' name="sigla" id="siglagrupo" maxlength="20" class="Valid[Required] Description[grupo.sigla]" />
											</div>
										</fieldset>
									</div>									
									<div class="col_66">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.descricao" />
											</label>
											<div>
												<textarea name="descricao" cols='200' rows='5' maxlength = "2000"></textarea>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<div>
												<label><b><i18n:message key="grupo.suspensaoReativacaodeMultiplasSolicitacoes" /></b></label>
												<i18n:message key="citcorpore.comum.sim" /><input type="radio" name="permiteSuspensaoReativacao" id="permiteSuspensaoReativacao" value="S">
												<i18n:message key="citcorpore.comum.nao" /><input type="radio" name="permiteSuspensaoReativacao" id="permiteSuspensaoReativacao" value="N" checked="checked">
											</div>
											
											<label><i18n:message key="grupo.permissoesfluxos" />
											</label>
											<br />
											<input  style="float:left; margin-left:20px" onclick='marcarTodos();' type='checkbox' name='checkboxCheckAll' id='checkboxCheckAll'  />
											<div>
											<label for="checkboxCheckAll" ><i18n:message key="citcorpore.comum.MarcarTodos" />
											</label>
											</div>
											<div>
												<table id="marcarTodos" style="line-height: 0.3!important;">		
													<tr align='center'>
													    <td></td>
													    <td style='width: 40px;'><i18n:message key="citcorpore.comum.criar" /></td>
													    <td style='width: 60px;'><i18n:message key="citcorpore.comum.executar" /></td>
													    <td style='width: 60px;'><i18n:message key="citcorpore.comum.delegar" /></td>
													    <td style='width: 72px;'><i18n:message key="citcorpore.comum.suspender" /></td>
													    <td style='width: 60px;'><i18n:message key="citcorpore.comum.reativar" /></td>
													    <td style='width: 74px;'><i18n:message key="citcorpore.comum.alterarSLA" /></td>
													    <td style='width: 50px;'><i18n:message key="citcorpore.comum.reabrir" /></td>
													    <td style='width: 50px;'><i18n:message key="citcorpore.comum.cancelar" />*</td>
												    </tr>
												<%
												for (Iterator it = tiposFluxos.iterator(); it.hasNext();){
												    TipoFluxoDTO  tipoFluxoDto = (TipoFluxoDTO)it.next();
												    out.print("<tr>");
												    out.print("<td style='vertical-align: middle;'>" + tipoFluxoDto.getNomeFluxo() + ": </td>");
												    
												    if (br.com.citframework.util.Util.isVersionFree(request)
												    		&& (tipoFluxoDto.getNomeFluxo().equals("RequisicaodeMudancaNormal")
												    			|| tipoFluxoDto.getNomeFluxo().equals("RequisicaoMudancaEmergencial")
												    			|| tipoFluxoDto.getNomeFluxo().equals("RequisicaoMudancaPadrao"))) {
													    out.print("<td colspan='7'>" + Free.getMsgCampoIndisponivel(request) + "</td>");
												    } else {
												    	
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='C_" + tipoFluxoDto.getIdTipoFluxo() + "' id='C_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='E_" + tipoFluxoDto.getIdTipoFluxo() + "' id='E_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='D_" + tipoFluxoDto.getIdTipoFluxo() + "' id='D_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='S_" + tipoFluxoDto.getIdTipoFluxo() + "' id='S_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='R_" + tipoFluxoDto.getIdTipoFluxo() + "' id='R_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='A_" + tipoFluxoDto.getIdTipoFluxo() + "' id='A_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='X_" + tipoFluxoDto.getIdTipoFluxo() + "' id='X_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
													    out.print("<td align='center'><input class='perm' value='S' type='checkbox' name='N_" + tipoFluxoDto.getIdTipoFluxo() + "' id='N_" + tipoFluxoDto.getIdTipoFluxo() + "'/></td>");
												    }
												    out.print("</tr>");
												}
												%>
											
												</table>
												<h6 class="cancelar">*<i18n:message key="grupo.observacaoCancelar" /></h6>
											</div>
										</fieldset>
									</div>
									
									<div class="col_66">
										<fieldset>
											<label><i18n:message key="grupo.NotificacoesDeE-mail" /></label>
											<div>
												<table>
													<tr><td><input value='S' type='checkbox' name='abertura' id='Abertura'/></td><td><i18n:message key="grupo.abertura" /></td></tr>
													<tr><td><input value='S' type='checkbox' name='andamento' id='Andamento'/></td><td><i18n:message key="grupo.andamento" /></td></tr>
													<tr><td><input value='S' type='checkbox' name='encerramento' id='Encerramento'/></td><td><i18n:message key="grupo.encerramento" /></td></tr>
												</table>
											</div>
										</fieldset>
									</div>
									
										<div class="col_100" id='divListaContratos'>
											<fieldset id='fldListaContratos'>
											</fieldset>
										</div> 	
																		
								</div>
								<!-- 	Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 19:45 - ID Citsmart: 120948 - 
								* Motivo/Comentário: Alterado largura das colunas das tables e inserido class no padrao novo  -->
								<div  class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label id="addEmpregado" style="cursor: pointer;"
												title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />"><i18n:message key="colaborador.colaborador" /><img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"></label>
											<div  id="gridEmpregados">
												<table class="table table-bordered table-striped" id="tabelaEmpregado"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 90%;"><i18n:message key="colaborador.colaborador" /></th>
														<th style="width: 9%; text-align: left;"><i18n:message  key="citcorpore.comum.email" /><input value='S' type='checkbox' name='email' id='emailTodos' onclick="check()"/></th>
													</tr>
												</table>
											</div>
											<div class="col_100">
						               		   <div class="col_40"><br></div>
						               		   <div id="paginas" class="col_50" align="center" style="height: 50px;" ></div>
						                    </div>
										</fieldset>

									</div>
								</div>
									<div  class="columns clearfix">
									<div class="col_100">
												<fieldset>
											<label id="addEmail" style="cursor: pointer;"
												title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />"><i18n:message  key="citcorpore.comum.email" /><img	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"></label>
											<div  id="gridEmails">
												<table class="table table-bordered table-striped" id="tabelaEmail"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 49,5%;"><i18n:message  key="citcorpore.comum.nome" /></th>
														<th style="width: 49,5%;"><i18n:message  key="citcorpore.comum.email" /></th>
														<th style="width: 0%;"> <i18n:message  key="citcorpore.comum.tipo" />	</th>
													</tr>
												</table>
											</div>
										</fieldset>
								</div>
								</div>
								<br>
								<br>
<!-- 								onclick='gravar();' -->
								<fieldset>
									<label>
										<button type='button' name='btnGravar' class="light" onclick="gravar();" >
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
											<span><i18n:message key="citcorpore.comum.gravar" /></span>
										</button>
										<button type="button" name='btnLimpar' class="light" onclick="limpar();">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
										<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
											<span><i18n:message key="citcorpore.comum.excluir" /></span>
										</button>
									</label>
								</fieldset>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
	<!-- POPUP EMPREGADO -->
	<div id="POPUP_EMPREGADO" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaEmp' style="width: 540px">
				<cit:findField formName='formPesquisaEmp' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP EMPREGADO -->
		<!-- POPUP EMAIL -->
	<div id="POPUP_EMAIL" title="<i18n:message  key="grupo.pesquisaEmail" />">
		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
			<li><a href="#tabs-1"><i18n:message  key="email.cadastroEmail" /></a></li>
			<li><a href="#tabs-2" class="round_top"><i18n:message  key="email.pesquisaColaborador" />
			</a></li>
			</ul>
			<a href="#" class="toggle">&nbsp;</a>
		<div class="toggle_container">

		<div id="tabs-1" class="block">
		<div class="section">
		<form id="formEmail" name="formEmail" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/grupoEmail/grupoEmail">
		<input type = "hidden" name = "idEmpregado" /> 
		<input type = "hidden" name = "idGrupo" /> 
		<label><i18n:message  key="citcorpore.comum.nome" /></label>
		<div>
			<input type="text" id="nomeEmail" name="nome" maxlength = "80" />
		</div>
		
		<label><i18n:message  key="citcorpore.comum.email" /></label>
			<div>
				<input type="text" id="emailExtra" name="email" size="300" maxlength="200"/>
			</div>
			<br>
			<br>
			<button type='button' name='btnGravar' class="light" onclick="gravarEmail();" >
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span><i18n:message key="citcorpore.comum.gravar" /></span>
			</button>
			<button type="button" name='btnLimpar' class="light" onclick="limparEmail();">
				<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
					<span><i18n:message key="citcorpore.comum.limpar" /></span>
			</button>	
			</form>	
		</div>
		</div>
		<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisaEmail' style="width: 540px">
								<cit:findField formName='formPesquisaEmail'  lockupName='LOOKUP_EMPREGADO_EMAIL' id='LOOKUP_EMPREGADO_EMAIL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
		</div>
		</div>
	</div>
	<!-- Fim POPUP EMAIL -->
	
</html>