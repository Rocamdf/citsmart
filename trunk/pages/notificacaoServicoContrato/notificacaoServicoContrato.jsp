<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoServicoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoGrupoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoUsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "âœ°">
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	
	String idContrato = request.getParameter("idContrato");
   	if (idContrato == null)
   	idContrato = "";
%>

<%@include file="/include/security/security.jsp"%>

<title><i18n:message key="citcorpore.comum.title"/></title>

<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="../../cit/objects/NotificacaoServicoDTO.js"></script>
	<style type="text/css">
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
		
		div#main_container {
			margin: 10px 10px 10px 10px;
		}
	</style>

	<script>
		var objTab = null;
		var contUsuario = 0;
		var contGrupo = 0;
		addEvent(window, "load", load, false);
		
		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
			
			$("#POPUP_USUARIO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_GRUPO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
		}
		
		function LOOKUP_NOTIFICACAO_select(id, desc) {
			document.form.restore({
				idNotificacao : id
			});
		}
		
		function excluir() {
			if (document.getElementById("idNotificacao").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}
		
	 	$(function() {
			$("#addUsuario").click(function() {
				$("#POPUP_USUARIO").dialog("open");
			});
		});
		 
	 	$(function() {
			$("#addGrupo").click(function() {
				$("#POPUP_GRUPO").dialog("open");
			});
		}); 
		 
	 	function LOOKUP_USUARIO_select(id, desc) {
		 addLinhaTabelaUsuario(id, desc, true);
			
		}
		 	
		function exibirTabelaUsuario (){
			document.getElementById('tabelaUsuario').style.display = 'block';
		}
		
		function exibirTabelaGrupo (){
			document.getElementById('tabelaGrupo').style.display = 'block';
		} 
	
		function addLinhaTabelaUsuario(id, desc, valida){
			var tbl = document.getElementById('tabelaUsuario');
			$('#tabelaUsuario').show();
			$('#gridUsuario').show();
			//tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaUsuario(lastRow, id)){
					return;
				}
			}
			var row = tbl.insertRow(lastRow);
			var coluna = row.insertCell(0);
			contUsuario++;
			coluna.innerHTML = '<img id="imgDelUsuario' + contUsuario + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.cliquaParaExcluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaUsuario(\'tabelaUsuario\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = desc + '<input type="hidden" id="idUsuario' + contUsuario + '" name="idUsuario" value="' + id + '" />';
			$("#POPUP_USUARIO").dialog("close");		
				
		}
		 
		 function validaAddLinhaTabelaUsuario(lastRow, id){
			if (lastRow > 2){
				var arrayIdUsuario = document.form.idUsuario;
				for (var i = 0; i < arrayIdUsuario.length; i++){
					if (arrayIdUsuario[i].value == id){
						alert('Regristro já adicionado!');
						return false;
					}
					
				}
			} else if (lastRow == 2){
				var idUsuario = document.form.idUsuario;
				if (idUsuario.value == id){
					alert('Regristro já adicionado!');
					return false;
				}
			}
			return true;
		}
		 
		function removeLinhaTabelaUsuario(idTabela, rowIndex) {
			if (confirm('<i18n:message key="citcorpore.comum.deleta" />')){
				HTMLUtils.deleteRow(idTabela, rowIndex);
				var tabela = document.getElementById(idTabela);
				if (tabela.rows.length == 1){
					if (idTabela == 'tabelaUsuario'){
						$('#gridUsuario').hide();
						return;
					}
					$('#tabelaUsuario').hide();
				}
			}
		}
			
		function LOOKUP_GRUPO_select(id, desc) {
			addLinhaTabelaGrupo(id, desc, true);
	
		}
		
		 function addLinhaTabelaGrupo(id, desc, valida){
				var tbl = document.getElementById('tabelaGrupo');
				$('#tabelaGrupo').show();
				$('#gridGrupo').show();
				var lastRow = tbl.rows.length;
				if (valida){
					if (!validaAddLinhaTabelaGrupo(lastRow, id)){
						return;
					}
				}
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contGrupo++;
				coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.cliquaParaExcluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = desc
					+ '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';
			$("#POPUP_GRUPO").dialog("close");
		}
	
		function validaAddLinhaTabelaGrupo(lastRow, id) {
			if (lastRow > 2) {
				var arrayIdGrupo = document.form.idGrupo;
				for ( var i = 0; i < arrayIdGrupo.length; i++) {
					if (arrayIdGrupo[i].value == id) {
						alert('Regristro já adicionado!');
						return false;
					}
	
				}
			} else if (lastRow == 2) {
				var idGrupo = document.form.idGrupo;
				if (idGrupo.value == id) {
					alert('Regristro já adicionado!');
					return false;
				}
			}
			return true;
		}
	
		function removeLinhaTabelaGrupo(idTabela, rowIndex) {
			if (confirm('<i18n:message key="citcorpore.comum.deleta" />')) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
				var tabela = document.getElementById(idTabela);
				if (tabela.rows.length == 1) {
					if (idTabela == 'tabelaGrupo') {
						$('#gridGrupo').hide();
						return;
					}
					$('#tabelaGrupo').hide();
				}
			}
		}
		
		function NotificacaoUsuarioDTO(_id, i){
	 		this.idUsuario = _id; 
	 	}
		
		function serializaUsuario() {
			var tabela = document.getElementById('tabelaUsuario');
			var count = contUsuario + 1;
			var listaDeUsuario = [];
			for ( var i = 1; i < count; i++) {
				if (document.getElementById('idUsuario' + i) != ""
						&& document.getElementById('idUsuario' + i) != null) {
					var trObj = document.getElementById('idUsuario' + i).value;
					var usuario = new NotificacaoUsuarioDTO(trObj, i);
					listaDeUsuario.push(usuario);
				}
			}
			var serializaUsuario = ObjectUtils.serializeObjects(listaDeUsuario);
			document.form.usuariosSerializados.value = serializaUsuario;
		}
		
		function NotificacaoGrupoDTO(_id, i){
	 		this.idGrupo = _id; 
	 	}
		
		function serializaGrupo() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = contGrupo + 1;
			var listaDeGrupo = [];
			for ( var i = 1; i < count; i++) {
				if (document.getElementById('idGrupo' + i) != ""
						&& document.getElementById('idGrupo' + i) != null) {
					var trObj = document.getElementById('idGrupo' + i).value;
					var grupo = new NotificacaoGrupoDTO(trObj, i);
					listaDeGrupo.push(grupo);
				}
			}
			var serializaGrupo = ObjectUtils.serializeObjects(listaDeGrupo);
			document.form.gruposSerializados.value = serializaGrupo;
		}
		
		function lancarServicos() {
			checkboxServico = document.getElementsByName('idServico');
			var count = checkboxServico.length;
			var contadorAux = 0;
			var baselines = new Array();

			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idServico' + i);	
				if (!trObj) {
					continue;
				}	
				baselines[contadorAux] = getServico(i);
				contadorAux = contadorAux + 1;
			}
			serializaServico();
		}
		
		serializaServico = function() {
			var checkboxServico = document.getElementsByName('idServico');
			var count = checkboxServico.length;
			var contadorAux = 0;
			var servicos = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idServico' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					servicos[contadorAux] = getServico(i);
					contadorAux = contadorAux + 1;
					continue;
				}	
			}
			var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
			document.form.servicosLancados.value = servicosSerializadas;
			return true;
		}

		getServico = function(seq) {
			var NotificacaoServicoDTO = new CIT_NotificacaoServicoDTO();
			NotificacaoServicoDTO.sequencia = seq;
			NotificacaoServicoDTO.idServico = eval('document.form.idServico' + seq + '.value');
			return NotificacaoServicoDTO;
		}
		
		function gravar(){
			/*
			 * Rodrigo Pecci Acorse - 27/11/2013 17h30 - #125019
			 * Adicionado a validação para garantir que ao menos um usuário ou grupo foi adicionado. 
			 */
			if ($('input[type="hidden"][name="idUsuario"]').length == 0 && $('input[type="hidden"][name="idGrupo"]').length == 0) {
				alert(i18n_message("gerenciaservico.delegartarefa.validacao.informeprazo"));
				return false;
			}

			serializaUsuario();
			serializaGrupo();
			document.getElementById('origemNotificacao').value = 'S';
			lancarServicos();
			document.form.save();		
		}
		
		function limpar(){
			 deleteAllRowsUsuario();
			  deleteAllRowsGrupo();
			document.form.clear();
			
		}
		
		function deleteAllRowsUsuario() {
			var tabela = document.getElementById('tabelaUsuario');
			var count = tabela.rows.length;
	
			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
				
			}
			$('#gridUsuario').hide();
		}
		
		function deleteAllRowsGrupo() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = tabela.rows.length;
	
			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
				
			}
			$('#gridGrupo').hide();
		}
		
		function check() {
			var tabela = document.getElementById('tbServicos');
			var count = tabela.rows.length;
			if ($('#todos').is(":checked")) {
				for ( var i = 1; i < count; i++) {
					$('#idServico' + i).attr('checked', true);
				}
			} else {
				for ( var i = 1; i < count; i++) {
					$('#idServico' + i).attr('checked', false);
				}
			}
		}
		function fecharPopup() {
			parent.fecharPopupNotificacoesServicos();
		}
		
	</script>
</head>

<body>
	<div id="wrapper">
		<div id="main_container" class="main_container container_16 clearfix">
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="notificacao.cadastronotificacao" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/notificacaoServicoContrato/notificacaoServicoContrato'>
								<input type='hidden' name='idBaseConhecimento' id='idBaseConhecimento'/> 
								<input type='hidden' name='idNotificacao' />
								<input type='hidden' name='idContrato' value="<%=idContrato%>" />
								<input type='hidden' name='dataInicio' id="dataInicio" />
								<input type='hidden' name='dataFim' id="dataFim" />
								<input type="hidden" name="usuariosSerializados">
								<input type="hidden" name="gruposSerializados">
								<input type='hidden' name='origemNotificacao' id='origemNotificacao'/>
								<input type='hidden' name='servicosLancados' id='servicosLancados'/>
								
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="notificacao.titulo" /></label>
											<div>
												<input type='text' name="titulo" maxlength="256" class="Valid[Required] Description[notificacao.titulo]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="notificacao.tipoNotificacao" /></label>
											<div>
												<select id="tipoNotificacao" name="tipoNotificacao" class='Valid[Required] Description[notificacao.tipoNotificacao]'></select>
											</div>
										</fieldset>
									</div>
								</div>
								<div  class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label id="addUsuario" style="cursor: pointer;">
												<i18n:message key="citcorpore.comum.usuario" />
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add2.png">
											</label>
											<div id="gridUsuario">
												<table class="table" id="tabelaUsuario"	style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 98%;"><i18n:message key="citcorpore.comum.usuario" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label id="addGrupo" style="cursor: pointer;">
												<i18n:message key="grupo.grupo" />
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add2.png">
											</label>
											<div id="gridGrupo">
												<table class="table" id="tabelaGrupo"  style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 98%;"><i18n:message key="grupo.grupo" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<div id='divServicos' style='height: 300px; overflow: auto;'></div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

	<div id="POPUP_USUARIO" title="<i18n:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario' lockupName='LOOKUP_USUARIO_NOTIFICACAO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div> 
	<div id="POPUP_GRUPO" title="<i18n:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
							<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
</html>
