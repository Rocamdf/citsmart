<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!doctype html public "">
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
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		
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
				text-align: center;
			}
			
			fieldset #divHerdarPermissao{
				padding: 15px 13px 15px !important;
			}
		</style>

		<script type="text/javascript">
			var objTab = null;
			var contUsuario = 0;
			var contGrupo = 0;
			addEvent(window, "load", load, false);

			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
				
				$(function() {
					$("#POPUP_NOTIFICACAO").dialog({
						title: 'Notificações',
						autoOpen : false,
						width : 1300,
						height : 600,
						modal : true,
						show: "fade",
						hide: "fade"
					});
				});
				
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
			
			function gravar() {

				$('#titulo').val($('#tituloNotificacao').val());
				var tipoNotificacao = $('#tipo').val();
				$('#tipoNotificacao').val(tipoNotificacao);
				serializa();
				serializaUsuario();
				serializaGrupo();
				document.form.save();
			}

			function LOOKUP_PASTA_select(idPasta, desc) {
				document.form.restore( {
					id : idPasta
				});
			}
			
			function restoreRow() {
									
				var tabela = document.getElementById('tabelaPerfilAcesso');
				var lastRow = tabela.rows.length;
	
				var row = tabela.insertRow(lastRow);
				count++;
	
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<input type="checkbox" id="checkPerfilAcesso' + count + '" onclick="document.getElementById(\'aprovaBaseConhecimentoFalse' + count + '\' ).checked = true, document.getElementById(\'permiteLeitura' + count + '\' ).checked = true" />';

				coluna = row.insertCell(1);
				coluna.innerHTML = '<input type="hidden" id="idPerfilAcesso'
						+ count + '" name="idPerfilAcesso"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nomePerfilAcesso'
						+ count + '" name="nomePerfilAcesso"/>';
						
				coluna = row.insertCell(2);
				coluna.innerHTML = 	'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="permiteLeitura' + count + '" name="permiteLeitura' + count + '" value="l" checked: true; /><i18n:message key="pasta.leitura"/></span>' +
									'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="permiteLeituraGravacao' + count + '" name="permiteLeitura' + count + '" value="g"/><i18n:message key="pasta.leituraGravacao"/></span>';
									
				coluna = row.insertCell(3);
				coluna.innerHTML = '<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="aprovaBaseConhecimentoTrue' + count + '" name="aprovaBaseConhecimento' + count + '" value="S"/><i18n:message key="citcorpore.comum.sim"/></span>' + 
									'<span><input type="radio" id="aprovaBaseConhecimentoFalse' + count + '" name="aprovaBaseConhecimento' + count + '" value="N"/><i18n:message key="citcorpore.comum.nao"/></span>';
									
			}

			var seqSelecionada = '';
			function setRestorePerfilAcesso(idPerfilAcesso, nomePerfilAcesso, aprovaBaseConhecimento) {
				
				if (seqSelecionada != '') {
					
					eval('document.form.idPerfilAcesso' + seqSelecionada + '.value = "' + idPerfilAcesso + '"');
					
					eval('document.form.nomePerfilAcesso' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nomePerfilAcesso + '\')');
				}
				
				exibirGridPerfilAcesso();
			}

			function atribuirChecked(idPerfilAcesso, aprovaBaseConhecimento, permiteLeitura, permiteLeituraGravacao){
				
				if ($('#idPerfilAcesso' + seqSelecionada).val() == idPerfilAcesso){
					
					$('#checkPerfilAcesso' + seqSelecionada).attr('checked', true);
					
					if (aprovaBaseConhecimento == "N"){
						
						$('#aprovaBaseConhecimentoFalse' + seqSelecionada).attr('checked', true);
						
					}else{
						
						$('#aprovaBaseConhecimentoTrue' + seqSelecionada).attr('checked', true);
					}
						
					if (permiteLeitura == "S"){
						
						$('#permiteLeitura' + seqSelecionada).attr('checked', true);
						
					} else{
						
						if (permiteLeituraGravacao == "S"){
							
							$('#permiteLeituraGravacao' + seqSelecionada).attr('checked', true);
							
						}
						
					}
				}
			}

			var seqSelecionada = '';
			var aux = '';
			serializa = function() {
				var tabela = document.getElementById('tabelaPerfilAcesso');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var perfis = new Array();
				
				for ( var i = 1; i <= count; i++) {
					
					var trObj = document.getElementById('idPerfilAcesso' + i);
	
					if (!trObj) {
						continue;
					}
					
					if ($('#checkPerfilAcesso' + i).is(":checked")){
						
						perfis[contadorAux] = getPerfil(i);
						contadorAux = contadorAux + 1;
					}
				}
				
				var perfisSerializados = ObjectUtils.serializeObjects(perfis);
				
				document.form.perfisSerializados.value = perfisSerializados;
				return true;
			}

			getPerfil = function(seq) {
				var PerfilAcessoPastaDTO = new CIT_PerfilAcessoPastaDTO();
				
				PerfilAcessoPastaDTO.sequencia = seq;
				
				PerfilAcessoPastaDTO.idPerfilAcesso = eval('document.form.idPerfilAcesso' + seq + '.value');
				
				if ($('#aprovaBaseConhecimentoTrue' + seq).is(":checked")){
					PerfilAcessoPastaDTO.aprovaBaseConhecimento = "S";
				}else{
					PerfilAcessoPastaDTO.aprovaBaseConhecimento = "N";
				}
				
				if ($('#permiteLeitura' + seq).is(":checked")){
					PerfilAcessoPastaDTO.permiteLeitura = "S";
					PerfilAcessoPastaDTO.permiteLeituraGravacao = "N";
				} else{
					if ($('#permiteLeituraGravacao' + seq).is(":checked")){
						PerfilAcessoPastaDTO.permiteLeitura = "N";
						PerfilAcessoPastaDTO.permiteLeituraGravacao = "S";
					}
				}
				return PerfilAcessoPastaDTO;
			}

			function deleteAllRows() {
				var tabela = document.getElementById('tabelaPerfilAcesso');
				var count = tabela.rows.length;
	
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				ocultarGridPerfilAcesso();
			}

			function limpar() {
				deleteAllRows();
				deleteAllRowsUsuario();
				deleteAllRowsGrupo();
				document.form.clear();
				desativarCheckHerdarPermissoes();
				document.form.fireEvent("load");
			}

			function excluir(){
				if (document.getElementById("id").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("excluirPasta");
						limpar();
					}
				}
			}
			
			function ativarHerdarPemissoes(){
				document.form.fireEvent('ativarHerdarPemissoes');
			}
			
			function desativarCheckHerdarPermissoes(){
				$('#herdarPermissao').attr('checked', false);
				$('#mainHerdarPermissao').hide();
				exibirGridPerfilAcesso();
			}
			
			function ativarCheckHerdarPermissoes(){
				exibirHerdaPermissao();
				$('#herdarPermissao').attr('checked', true);
				ocultarGridPerfilAcesso();
			}
			
			function exibirHerdaPermissao(){
				$('#mainHerdarPermissao').show();
				
				ocultarGridPerfilAcesso();
			}
			
			function ocultarHerdaPermissao(){
				$('#mainHerdarPermissao').hide();
				
				exibirGridPerfilAcesso();
			}
			
			
			function exibirGridPerfilAcesso() {
				$('#gridPerfilAcesso').show();
			}
	
			function ocultarGridPerfilAcesso() {
				$('#gridPerfilAcesso').hide();
			}
			
			function exibirOcultarGridPerfilAcesso() {
				if ($('#herdarPermissao').is(':checked')){
					$('#gridPerfilAcesso').hide();
				} else{
					exibirGridPerfilAcesso();
				}
			}
			
			function abrirPopupNotificacao(){
				$('#POPUP_NOTIFICACAO').dialog("open");
			}
			
			
			
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
				coluna.innerHTML = '<img id="imgDelUsuario' + contUsuario + '" style="cursor: pointer;" title="Clique para excluir" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaUsuario(\'tabelaUsuario\', this.parentNode.parentNode.rowIndex);">';
				coluna = row.insertCell(1);
				coluna.innerHTML = desc + '<input type="hidden" id="idUsuario' + contUsuario + '" name="idUsuario" value="' + id + '" />';
				$("#POPUP_USUARIO").dialog("close");		
						
			}
				 
			function validaAddLinhaTabelaUsuario(lastRow, id){
				if (lastRow > 2){
					var arrayIdUsuario = document.getElementsByName("idUsuario");
					for (var i = 0; i < arrayIdUsuario.length; i++){
						if (arrayIdUsuario[i].value == id){
							alert('Regristro já adicionado!');
							return false;
						}
							
					}
				} else if (lastRow == 2){
					var idUsuario = document.getElementsByName("idUsuario");
					if (idUsuario[0].value == id){
						alert('Regristro já adicionado!');
						return false;
					}
				}
				return true;
			}
				 
			function removeLinhaTabelaUsuario(idTabela, rowIndex) {
				if (confirm('Deseja realmente excluir?')){
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
				coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="Clique para excluir" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">';
			    coluna = row.insertCell(1);
				coluna.innerHTML = desc
					+ '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';
					$("#POPUP_GRUPO").dialog("close");
			}
			
			function validaAddLinhaTabelaGrupo(lastRow, id) {
				if (lastRow > 2) {
					var arrayIdGrupo = document.getElementsByName("idGrupo");
					for ( var i = 0; i < arrayIdGrupo.length; i++) {
						if (arrayIdGrupo[i].value == id) {
							alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
							return false;
						}
			
					}
				} else if (lastRow == 2) {
					var idGrupo =  document.getElementsByName("idGrupo");
					if (idGrupo[0].value == id) {
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
				return true;
			}
			
			function removeLinhaTabelaGrupo(idTabela, rowIndex) {
					if (confirm(i18n_message("citcorpore.comum.deleta"))){
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
				
			
			function gravarNotificacao() {

				$('#titulo').val($('#tituloNotificacao').val());
				var tipoNotificacao = $('#tipo').val();
				$('#tipoNotificacao').val(tipoNotificacao);
				serializaUsuario();
				serializaGrupo();
				document.form.fireEvent("gravarNotificacao");
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

			function setarValoresPopupNotificacao() {
				$('#tituloNotificacao').val($('#titulo').val());
				var tipoNotificacao = $('#tipoNotificacao').val();
				$('#tipo').val(tipoNotificacao);
			}
			
			function fecharNotificacao(){
				$("#POPUP_NOTIFICACAO").dialog("close");
			}
			aguarde = function(){
				JANELA_AGUARDE_MENU.show();
			}
			
			fechar_aguarde = function(){
		    	JANELA_AGUARDE_MENU.hide();
			}
		</script>
	</head>	
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
	<body>	
		<script type="text/javascript" src="../../cit/objects/PerfilAcessoPastaDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="pasta.pasta"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="pasta.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="pasta.pesquisa"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">											
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pasta/pasta'>
									
									<div class="columns clearfix">
										<input type='hidden' name='id'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='perfisSerializados'/>
										<input type='hidden' name='idNotificacao' /> 
										<input type='hidden' name='dataInicio' id="dataInicio" />
										<input type='hidden' name='dataFim' id="dataFim" />
										<input type="hidden" name="usuariosSerializados">
										<input type="hidden" name="gruposSerializados">
										<input type="hidden" id="titulo" name="titulo">
										<input type="hidden" id="tipoNotificacao" name="tipoNotificacao">
										
										<div class="columns clearfix">
											
											<div class="col_33">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="pasta.pasta"/></label>
														<div>
														  	<input type='text' name="nome" maxlength="70" size="70" class="Valid[Required] Description[Nome da Pasta]" />
														</div>
												</fieldset>
											</div>
											<div class="col_33">
												<fieldset>
													<label ><i18n:message key="pasta.superior"/></label>
													<div>
													  	<select id="comboPastaPai" name="idPastaPai" style="margin-bottom: 3px;" onchange="ativarHerdarPemissoes()"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_15">
												<fieldset style="height: 55px; text-align: center;	">
												  	<label ><i18n:message key="baseconhecimento.notificacoes"/></label>
												  	<div>
												  		<img style="cursor: pointer; " title=<i18n:message key="pasta.notificacoes" /> onclick="abrirPopupNotificacao()"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/notificacao.png">
												  	</div>
												</fieldset>
											</div>
										</div>
										<br>
										<h3><i18n:message key="pasta.controleAcesso"/></h3>
										<div id="mainHerdarPermissao" class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<div id="divHerdarPermissao">
													  	<span>
													  		<input type="checkbox" id="herdarPermissao" name="herdaPermissoes" style="margin-right: 7px !important;" value="S" onclick="exibirOcultarGridPerfilAcesso()"/>
													  		<label style="font-weight: bold;"><i18n:message key="pasta.herdaPermissoes" /></label>
													  	</span>
													</div>
												</fieldset>
											</div>
										</div>
										
										<div id="gridPerfilAcesso" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaPerfilAcesso" style="width: 100%">
												<tr>
													<th style="width: 1%;"></th>
													<th style="width: 40%;"><i18n:message key="citcorpore.comum.perfilAcesso"/></th>
													<th style="width: 30%;" align="center"><i18n:message key="pasta.permissoes"/></th>
													<th style="width: 20%;"><i18n:message key="citcorpore.comum.aprovaBaseConhecimento"/></th>
												</tr>
											</table>
										</div>
									</div>	
									
									<div id="POPUP_NOTIFICACAO" >
										<div class="columns clearfix">
											<div class="col_50">
												<fieldset >
													<label class="campoObrigatorio"><i18n:message key="notificacao.titulo" /></label>
													<div >
														<input type='text' id="tituloNotificacao" name="tituloNotificacao" maxlength="256" class="Valid[Required] Description[notificacao.titulo]" />
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset style="height: 55px">
													<label class="campoObrigatorio"><i18n:message key="notificacao.tipoNotificacao" /></label>
													<div>
														<select id="tipo" name="tipo"></select>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="col_100">
											<div class="col_50">
												<fieldset>
													<label id="addUsuario" style="cursor: pointer;"><i18n:message key="citcorpore.comum.usuario" /><img
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/adicionarUsuario.png"></label>
													<div  id="gridUsuario">
														<table class="table" id="tabelaUsuario"
															style="display: none;">
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
													<label id="addGrupo" style="cursor: pointer;"><i18n:message key="grupo.grupo" /><img
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add2.png"></label>
													<div  id="gridGrupo">
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
										<div class="col_100">
											<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
											 <button type='button' name='btnGravarNotificacao' class="light"
												onclick='gravarNotificacao();'>
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
												<span><i18n:message key="citcorpore.comum.gravar" />
												</span>
											</button>
											<button type='button' name='btnGravarFechar' class="light"
												onclick='fecharNotificacao();'>
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
												<span><i18n:message key="citcorpore.comum.fechar" />
												</span>
											</button>
										</fieldset>
										</div>
   							 		</div>	
   							 	
									<br>
									<br>
									
									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>	
									<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir"/></span>
									</button>

								</form>
								
							</div>
						</div>
						
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_PASTA' id='LOOKUP_PASTA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
						
					</div>
				</div>		
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>

    
    <div id="POPUP_USUARIO" title="<i18n:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<cit:findField formName='formPesquisaUsuario' 
								lockupName='LOOKUP_USUARIO_CONHECIMENTO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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