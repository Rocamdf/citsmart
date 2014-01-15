<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ImportanciaConhecimentoUsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ImportanciaConhecimentoGrupoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.BaseConhecimentoRelacionadoDTO"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.bean.EventoMonitConhecimentoDTO"%>


<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
    	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
		
		<%
					response.setHeader( "Cache-Control", "no-cache");
					response.setHeader( "Pragma", "no-cache");
					response.setDateHeader ( "Expires", -1);
					
					String iframe = "";
					iframe = request.getParameter("iframe");
					String idProblema = request.getParameter("idProblema");
					
					if(idProblema == null){
						idProblema = "";
					}
					
					String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
				%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.js" type="text/javascript" language="javascript"></script>
		<script src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.MetaData.js' type="text/javascript" language="javascript"></script>
		<script src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.rating.js' type="text/javascript" language="javascript"></script>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js?nocache=<%=new java.util.Date().toString()%>"></script>
		<link href='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.rating.css' type="text/css" rel="stylesheet"/>
		
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
			
			.outputext.text {
				box-shadow: 0 0 0 0 #DDDDDD inset;
			    font-weight: bold;
			    border: 0px;
			    background: none;
			}
			
			input.text:hover.outputext{
				box-shadow: none !important;
			}
			
			input.text:focus.outputext{
				box-shadow: none !important;
			}
			
			input.text.outputext{
				box-shadow: none !important;
			}
			
			span.publicabase{
				/* padding-left: 10px; */
			}
			
			.col_15 {
			    width: 13% !important;
			}
			
		</style>
		
		<script type="text/javascript">
		
			var contUsuario = 0;
			var contGrupo = 0;
			var contConhecimentoRelacionado = 0;
			
			var contUsuarioNotificacao = 0;
			var contGrupoNotificacao = 0;
			
			var contEvento = 0;
			
			var objTab = null;
			
			addEvent(window, "load", load, false);

			function load() {
				
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
				
				$("#POPUP_USUARIO").dialog({
					title: i18n_message("citcorpore.comum.pesquisarUsuario"),
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				
				$("#POPUP_EVENTOMONITORAMENTO").dialog({
					title: i18n_message("citcorpore.comum.pesquisarEvento"),
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$("#POPUP_GRUPO").dialog({
					title: i18n_message("citcorpore.comum.pesquisarGrupo"),
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
					
				});
				
				$("#POPUP_CONHECIMENTO").dialog({
					title: i18n_message("citcorpore.comum.pesquisarConhecimento"),
					autoOpen : false,
					width : 600,
					height : 750,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$(function() {
					$("#POPUP_NOTIFICACAO").dialog({
						title: i18n_message("baseconhecimento.notificacoes"),
						autoOpen : false,
						width : 1300,
						height : 600,
						modal : true,
						show: "fade",
						hide: "fade"
					});
				});
				
				$("#POPUP_GRAUDEIMPORTANCIA").dialog({
					title: i18n_message("baseconhecimento.graudeimportancia"),
					autoOpen : false,
					width : 1300,
					height : 600,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$("#POPUP_EVENTOMONITCONHECIMENTO").dialog({
					title: i18n_message('baseConhecimento.associarDocumentoEventos'),
					autoOpen : false,
					width : 800,
					height : 600,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$("#POPUP_CONHECIMENTORELACIONADO").dialog({
					title: i18n_message('baseConhecimento.conhecimentoRelacionado'),
					autoOpen : false,
					width : 800,
					height : 600,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$(function() {
					$("#POPUP_INCIDENTE").dialog({
						autoOpen : false,
						width : 1500,
						height : 1005,
						modal : true
					});
				});
				
				$(function() {
					$("#POPUP_MUDANCA").dialog({
						autoOpen : false,
						width : 1500,
						height : 1005,
						modal : true
					});
				});
				

				$("#POPUP_LOOKUPEVENTOMONITORAMENTO").dialog({
					title: i18n_message('baseConhecimento.pesquisarEventoMonitoramento'),
					autoOpen : false,
					width : 600,
					height : 750,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$('#POPUP_GRAUDEIMPORTANCIA').hide();
				$('#POPUP_EVENTOMONITCONHECIMENTO').hide();
				$('#POPUP_CONHECIMENTORELACIONADO').hide();
				ocultarBtnGravarImportancia();
				ocultarBtnGravarConhecimentoRelacionado();
				$('#idLabelTituloFAQ').hide();
				$('#idLabelConteudoFAQ').hide();
				ocultarBotaoArquivar();
				ocultarBotaoRestaurar();
				ocultarArquivado();
			}
			
			function fecharSolicitacao(){
				$("#POPUP_INCIDENTE").dialog('close');
			}
			
			function LOOKUP_BASECONHECIMENTO_select(idBase, desc) {
				document.getElementById("identificadorBase").value = idBase;
 				document.form.fireEvent("restore");
			}
			
			function chamarRestore(idBase){
				document.getElementById("identificadorBase").value = idBase
				docucment.form.restore();
			}
			
			function limpar(){
				document.form.fireEvent("limpar");
				document.getElementById("fraUpload_uploadAnexos").src = "";
				document.getElementById("file_uploadAnexos").value = "";
				document.getElementById("descUploadFile_uploadAnexos").value = "";
				document.form.clear();
				deleteAllRows();
		        var oEditor = FCKeditorAPI.GetInstance( 'conteudoBaseConhecimento' ) ;
		        oEditor.SetData('');		
		        document.getElementById('titulo').readOnly = false;
		        ocultarDivPublicacao();
		        habilitarComboPasta();
		        deleteAllRowsUsuario();
			 	deleteAllRowsGrupo();
			 	deleteAllRowsUsuarioNotificacao();
			 	deleteAllRowsGrupoNotificacao();
			 	deleteAllRowsConhecimento();
			 	ocultarBtnGravarImportancia();
			 	ocultarBtnGravarConhecimentoRelacionado();
			 	ocultarArquivado();
			 	ocultarBotaoArquivar();
			 	ocultarBotaoRestaurar();
			}
			
			function verificarPermissoesDeAcesso(){
				document.form.fireEvent("verificarPermissoesDeAcesso");
			}
			
			function marcaRadioButton(){
				var flag1 = document.getElementById("aprovaBaseConhecimentoTrue").checked;
				var flag2 = document.getElementById("aprovaBaseConhecimentoFalse").checked;
				
				if(flag1==false && flag2==false){
					document.getElementById("aprovaBaseConhecimentoFalse").checked = true;
				}
			}

			function excluir(){
				if(document.getElementById("identificadorBase").value != ""){
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("excluir");
					}
				}
			}

			$(function() {
				$('.datepicker').datepicker();
		  	});

	        var oFCKeditor = new FCKeditor( 'conteudoBaseConhecimento' ) ;
	        function onInitQuestionario(){
		        oFCKeditor.BasePath = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
		
		        oFCKeditor.ToolbarSet   = 'Default' ;
		        oFCKeditor.Width = '100%' ;
	            oFCKeditor.Height = '300' ;
		        oFCKeditor.ReplaceTextarea() ;   
	        }
	        HTMLUtils.addEvent(window, "load", onInitQuestionario, false);
	        
			function restoreRow() {
				var tabela = document.getElementById('tabelaComentarios');
				var lastRow = tabela.rows.length;
	
				var row = tabela.insertRow(lastRow);
				countComentario++;
	
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<img id="imgExcluiComentario' + countComentario	+ '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.excluirComentario")" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaComentarios\', this.parentNode.parentNode.rowIndex);">';
	
				coluna = row.insertCell(1);
				coluna.innerHTML = '<input type="hidden" id="idComentario' + countComentario + '" name="idComentario"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nome' + countComentario + '" name="nome"/>';
	
				coluna = row.insertCell(2);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="email' + countComentario + '" name="email"/>';
	
				coluna = row.insertCell(3);
				coluna.innerHTML = '<textarea style="width: 100%; border: 0 none;" readonly="readonly" id="comentario' + countComentario + '" name="comentario"/>';

				coluna = row.insertCell(4);
				coluna.innerHTML =  '<input class="star" type="radio" id="nota1' + countComentario + '" name="nota' + countComentario + '" value="1" disabled="disabled" /> 1' +
								    '<input class="star" type="radio" id="nota2' + countComentario + '" name="nota' + countComentario + '" value="2" disabled="disabled" /> 2' +
								   	'<input class="star" type="radio" id="nota3' + countComentario + '" name="nota' + countComentario + '" value="3" disabled="disabled" /> 3' +
								    '<input class="star" type="radio" id="nota4' + countComentario + '" name="nota' + countComentario + '" value="4" disabled="disabled" /> 4' +
								    '<input class="star" type="radio" id="nota5' + countComentario + '" name="nota' + countComentario + '" value="5" disabled="disabled" /> 5';
			}
	
			var seqSelecionada = '';
			function setRestoreComentario(idComentario, comentario, nome, email, nota, dataInicio) {
				if (seqSelecionada != '') {
					eval('document.form.idComentario' + seqSelecionada + '.value = "' + idComentario + '"');
					eval('document.form.comentario' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + comentario + '\')');
					eval('document.form.nome' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nome + '\')');
					eval('document.form.email' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + email + '\')');
					
					if (ObjectUtils.decodificaEnter(nota) == 1){
						document.getElementById('nota1' + seqSelecionada).checked = true;
					}
					if (ObjectUtils.decodificaEnter(nota) == 2){
						document.getElementById('nota2' + seqSelecionada).checked = true;
					}
					if (ObjectUtils.decodificaEnter(nota) == 3){
						document.getElementById('nota3' + seqSelecionada).checked = true;
					}
					if (ObjectUtils.decodificaEnter(nota) == 4){
						document.getElementById('nota4' + seqSelecionada).checked = true;
					}
					if (ObjectUtils.decodificaEnter(nota) == 5){
						document.getElementById('nota5' + seqSelecionada).checked = true;
						document.getElementById('nota5' + seqSelecionada).class = "star";
					}
				}
				exibeGrid();
			}
	
			function removeLinhaTabela(idTabela, rowIndex) {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
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
				ocultarGridComentario();
			}
			
			function gravar() {
				aguarde();
				serializaUsuario();
				serializaGrupo();
				serializaConhecimentoRelacionado();
				serializaUsuarioNotificacao();
				serializaGrupoNotificacao();
				serializaEventoMonitoramento();
				if ($("#comboPasta").val() == 0){
					alert(i18n_message("pasta.selecione"));
					fechar_aguarde();
				} else{
					var tituloNotificacao = $('#notificacaoTitulo').val()
					$('#tituloNotificacao').val(tituloNotificacao);
					var tipoNotificacao = $('#tipo').val();
					$('#tipoNotificacao').val(tipoNotificacao);
					var oEditor = FCKeditorAPI.GetInstance( 'conteudoBaseConhecimento' ) ;
					document.form.conteudoBaseConhecimento.value = oEditor.GetXHTML();
					fechar_aguarde();
					document.form.save();
				}
			}
			
			function bloquearTitulo(){
				document.getElementById('titulo').readOnly = true;
			}
			
			function liberarTitulo(){
				document.getElementById('titulo').readOnly = false;
			}
			
			function desabilitarComboPasta(){
				$('#comboPasta').attr('disabled', true);
			}
			
			function habilitarComboPasta(){
				$('#comboPasta').attr('disabled', false);
			}
			
			function exibeGrid() {
				document.getElementById('gridComentario').style.display = 'block';
			}
	
			function ocultarGridComentario() {
				document.getElementById('gridComentario').style.display = 'none';
			}
			
			function ocultarDivPublicacao(){
				$('#publicacao').hide();
			}
			
			function ocultarBotoes(){
				$('#btnGravar').hide();
				$('#btnExcluir').hide();
				$('#btnLimpar').hide();
				ocultarBotaoArquivar();
				ocultarBotaoRestaurar();
			}
			
			function ocultarBotaoArquivar(){
				$('#btnArquivar').hide();
			}
			
			function ocultarBotaoRestaurar(){
				$('#btnRestaurar').hide();
			}
			
			function exibirBotaoArquivar(){
				$('#btnArquivar').show();
			}
			
			function exibirBotaoRestaurar(){
				$('#btnRestaurar').show();
			}
			
			function exibirBotoes(){
				$('#btnExcluir').show();
				$('#btnGravar').show();
			}
			
			function desabilitaCamposFrame(){
				
				$(".col_20").children().attr("disabled","disabled");//desabilitando o campo tipo de documento(faq, erro conhecido, base conhecimento)
				$(".col_10").children().attr("disabled","disabled");//desabilitando Identificador, Notificações, Importância, Documento Relacionado, Req Serviço, Mudança, Evento Monitoramento
				$("#titulo").attr("disabled","disabled");//desabilitando título
				$("#fonteReferencia").attr("disabled","disabled");
				$("#comboPasta").attr("disabled","disabled");
				$("#comboOrigem").attr("disabled","disabled");
				$("#justificativaObservacao").attr("disabled","disabled");
				$("#comboPrivacidade").attr("disabled","disabled");
				$("#comboSituacao").attr("disabled","disabled");
				$("#dataExpiracao").attr("disabled","disabled");
				$(".col_33").children().attr("disabled","disabled");
				$("#file_uploadAnexos").attr("disabled","disabled");
				$("#btnAdduploadAnexos").attr("disabled","disabled");
				$("#descUploadFile_uploadAnexos").attr("disabled","disabled");
				$('#btnAdduploadAnexos').attr('style','display:none');
			
			}
			 
			function abrirPopupNotificacao(){
				$('#POPUP_NOTIFICACAO').dialog("open");
			}
			
		 	function adicionarUsuario(isNotificacao) {
		 		if (isNotificacao == true){
		 			$('#isNotificacao').val(true);	
		 		} else{
		 			if (isNotificacao == false){
		 				$('#isNotificacao').val(false);
		 			}
		 		}
		 		
				$("#POPUP_USUARIO").dialog("open");
			}
		 	
		 	function adicionarGrupo (isNotificacao) {
				if (isNotificacao == true){
		 			$('#isNotificacaoGrupo').val(true);	
		 		} else{
		 			if (isNotificacao == false){
		 				$('#isNotificacaoGrupo').val(false);
		 			}
		 		}
				$("#POPUP_GRUPO").dialog("open");
			}
		 	
		 	function adicionarConhecimentoRelacionado() {
				$("#POPUP_CONHECIMENTO").dialog("open");
			}
		 	
			function abrirPopupImportanciaConhecimento(){
				$('#POPUP_GRAUDEIMPORTANCIA').dialog("open");
			}
			
			function fecharPopupGrauDeImportancia(){
				$('#POPUP_GRAUDEIMPORTANCIA').dialog("close");
			}
			
			function abrirPopupEventoMonitConhecimento(){
				$('#POPUP_EVENTOMONITCONHECIMENTO').dialog("open");
			}
			
			function fecharPopupEventoMonitConhecimento(){
				$('#POPUP_EVENTOMONITCONHECIMENTO').dialog("close");
			}
			
			function abrirPopupConhecimentoRelacionado(){
				$('#POPUP_CONHECIMENTORELACIONADO').dialog("open");
			}
			
			function fecharPopupConhecimentoRelacionado(){
				$('#POPUP_CONHECIMENTORELACIONADO').dialog("close");
			}
		 	
		 	function LOOKUP_USUARIO_select(id, desc) {
		 		if ($('#isNotificacao').val() == "true"){
		 			addLinhaTabelaUsuarioNotificacao(id, desc, true);
		 		} else{
			 		addLinhaTabelaUsuario(id, desc, true);
		 		}
			}
		 	
		 	function LOOKUP_GRUPO_select(id, desc) {
		 		if($('#isNotificacaoGrupo').val()=="true"){
		 			addLinhaTabelaGrupoNotificacao(id, desc, true);
		 		}else{
		 			addLinhaTabelaGrupo(id, desc, true);
		 		}
			}
		 	
		 	function LOOKUP_CONHECIMENTO_RELACIONADO_select(id, desc) {
		 		
		 		if (id == $('#identificadorBase').val() ){
			 		alert(i18n_message("baseConhecimento.autorelacionamento"));
		 		} else{
			 		$('#idConhecimentoRelacionado').val(id);
			 		document.form.fireEvent("validarRelacionamentoConhecimento");
		 		}
			}
		 	
		 	function addLinhaTabelaUsuarioNotificacao(id, desc, valida){
				var tbl = document.getElementById('tabelaUsuarioNotificacao');
				$('#tabelaUsuarioNotificacao').show();
				$('#gridUsuarioNotificacao').show();
				var lastRow = tbl.rows.length;
				if (valida){
					if (!validaAddLinhaTabelaUsuarioNotificacao(lastRow, id)){
						return;
					}
				}
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contUsuarioNotificacao++;
				coluna.innerHTML = '<img id="imgDelUsuario' + contUsuarioNotificacao + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaUsuarioNotificacao(\'tabelaUsuarioNotificacao\', this.parentNode.parentNode.rowIndex);">';
				coluna = row.insertCell(1);
				coluna.innerHTML = desc + '<input type="hidden" id="idUsuarioNotificacao' + contUsuarioNotificacao + '" name="idUsuarioNotificacao" value="' + id + '" />';
				$("#POPUP_USUARIO").dialog("close");		
			}
		 	
		 	
			function validaAddLinhaTabelaUsuarioNotificacao(lastRow, id){
				if (lastRow > 2){
					var arrayIdUsuario = document.getElementsByName("idUsuarioNotificacao");
					for (var i = 0; i < arrayIdUsuario.length; i++){
						if (arrayIdUsuario[i].value == id){
							alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
							return false;
						}
					}
				} else if (lastRow == 2){
					var idUsuario = document.getElementsByName("idUsuarioNotificacao");
					if (idUsuario[0].value == id){
						alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
				return true;
			}
				 
			function removeLinhaTabelaUsuarioNotificacao(idTabela, rowIndex) {
				if (confirm(i18n_message("citcorpore.comum.deleta"))){
					HTMLUtils.deleteRow(idTabela, rowIndex);
					var tabela = document.getElementById(idTabela);
					if (tabela.rows.length == 1){
						if (idTabela == 'tabelaUsuarioNotificacao'){
							$('#gridUsuarioNotificacao').hide();
							return;
						}
						$('#tabelaUsuarioNotificacao').hide();
					}
				}
			}
		 	
			function addLinhaTabelaGrupoNotificacao(id, desc, valida){
				var tbl = document.getElementById('tabelaGrupoNotificacao');
				$('#tabelaGrupoNotificacao').show();
				$('#gridGrupoNotificacao').show();
				var lastRow = tbl.rows.length;
				if (valida){
					if (!validaAddLinhaTabelaGrupoNotificacao(lastRow, id)){
						return;
					}
				}
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contGrupoNotificacao++;
				coluna.innerHTML = '<img id="imgDelGrupo' + contGrupoNotificacao + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupoNotificacao\', this.parentNode.parentNode.rowIndex);">';
			    coluna = row.insertCell(1);
				coluna.innerHTML = desc
					+ '<input type="hidden" id="idGrupoNotificacao' + contGrupoNotificacao + '" name="idGrupoNotificacao" value="' + id + '" />';
					$("#POPUP_GRUPO").dialog("close");
			}
			
			function validaAddLinhaTabelaGrupoNotificacao(lastRow, id) {
				if (lastRow > 2) {
					var arrayIdGrupo = document.getElementsByName("idGrupoNotificacao");
					for ( var i = 0; i < arrayIdGrupo.length; i++) {
						if (arrayIdGrupo[i].value == id) {
							alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
							return false;
						}
					}
				} else if (lastRow == 2) {
					var idGrupo =  document.getElementsByName("idGrupoNotificacao");
					if (idGrupo[0].value == id) {
						alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
				return true;
			}
			
			function removeLinhaTabelaGrupoNotificacao(idTabela, rowIndex) {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
					var tabela = document.getElementById(idTabela);
					if (tabela.rows.length == 1) {
						if (idTabela == 'tabelaGrupoNotificacao') {
							$('#gridGrupoNotificacao').hide();
							return;
						}
						$('#tabelaGrupoNotificacao').hide();
					}
				}
			}
			
		 	function addLinhaTabelaUsuario(id, desc, valida){
				var tbl = document.getElementById('tabelaUsuario');
				$('#tabelaUsuario').show();
				$('#gridUsuario').show();

				var lastRow = tbl.rows.length;
				
				if (valida){
					if (!validaAddLinhaTabelaUsuario(lastRow, id)){
						return;
					}
				}
				
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contUsuario++;
				
				coluna.innerHTML = '<img id="imgDelUsuario' + contUsuario + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaUsuario(\'tabelaUsuario\', this.parentNode.parentNode.rowIndex);">';
				
				coluna = row.insertCell(1);
				
				coluna.innerHTML = desc + '<input type="hidden" id="idUsuario' + contUsuario + '" name="idUsuario" value="' + id + '" />';
				
				coluna = row.insertCell(2);
				
				coluna.innerHTML = '<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="baixoUsuario' + contUsuario + '" name="grauImportanciaUsuario' + contUsuario + '" value="1"/>'+i18n_message("requisitosla.baixo")+'</span>' +
									'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="medioUsuario' + contUsuario + '" name="grauImportanciaUsuario' + contUsuario + '" value="2" checked= checked;/>'+i18n_message("requisitosla.medio")+'</span>' +
									'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="altoUsuario' + contUsuario + '" name="grauImportanciaUsuario' + contUsuario + '" value="3"/>'+i18n_message("requisitosla.alto")+'</span>';
				
				$('#POPUP_USUARIO').dialog("close");		
					
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
				
				coluna.innerHTML = '<img id="imgDelGrupo' + contGrupo + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaGrupo(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">';
				
				coluna = row.insertCell(1);
				
				coluna.innerHTML = desc + '<input type="hidden" id="idGrupo' + contGrupo + '" name="idGrupo" value="' + id + '" />';
				
				coluna = row.insertCell(2);
				
				coluna.innerHTML = '<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="baixoGrupo' + contGrupo + '" name="grauImportanciaGrupo' + contGrupo + '" value="1"/>'+i18n_message("requisitosla.baixo")+'</span>' +
									'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="medioGrupo' + contGrupo + '" name="grauImportanciaGrupo' + contGrupo + '" value="2" checked= checked;/>'+i18n_message("requisitosla.medio")+'</span>' +
									'<span style="padding-right: 30px;"><input style="margin-right: 5px;" type="radio" id="altoGrupo' + contGrupo + '" name="grauImportanciaGrupo' + contGrupo + '" value="3"/>'+i18n_message("requisitosla.alto")+'</span>';
				
				$('#POPUP_GRUPO').dialog("close");		
			}
		 	
		 	function addLinhaTabelaConhecimentoRelacionado(id, desc, valida){
				var tbl = document.getElementById('tabelaConhecimentoRelacionado');
				$('#tabelaConhecimentoRelacionado').show();
				$('#gridConhecimentoRelacionado').show();

				var lastRow = tbl.rows.length;
				
				if (valida){
					if (!validaAddLinhaTabelaConhecimentoRelacionado(lastRow, id)){
						return;
					}
				}
				
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contConhecimentoRelacionado++;
				
				coluna.innerHTML = '<img id="imgDelConhecimento' + contConhecimentoRelacionado + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaConhecimentoRelacionado(\'tabelaConhecimentoRelacionado\', this.parentNode.parentNode.rowIndex);">';
				
				coluna = row.insertCell(1);
				
				coluna.innerHTML = desc + '<input type="hidden" id="idConhecimento' + contConhecimentoRelacionado + '" name="idConhecimento" value="' + id + '" />';
				
				$('#POPUP_CONHECIMENTO').dialog("close");		
			}
		 	
			 function validaAddLinhaTabelaUsuario(lastRow, id){
				if (lastRow > 2){
					var arrayIdUsuario = document.getElementsByName("idUsuario");
					for (var i = 0; i < arrayIdUsuario.length; i++){
						if (arrayIdUsuario[i].value == id){
							alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
							return false;
						}
					}
				} else if (lastRow == 2){
					var idUsuario = document.getElementsByName("idUsuario");
					if (idUsuario[0].value == id){
						alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
				return true;
			}
			 
			 function validaAddLinhaTabelaGrupo(lastRow, id){
				if (lastRow > 2){
					var arrayIdGrupo = document.getElementsByName("idGrupo");
					for (var i = 0; i < arrayIdGrupo.length; i++){
						if (arrayIdGrupo[i].value == id){
							alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
							return false;
						}
					}
				} else if (lastRow == 2){
					var idGrupo = document.getElementsByName("idGrupo");
					if (idGrupo[0].value == id){
						alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
				return true;
			}
			 
			 function validaAddLinhaTabelaConhecimentoRelacionado(lastRow, id){
					if (lastRow > 2){
						var arrayIdConhecimento = document.getElementsByName("idConhecimento");
						for (var i = 0; i < arrayIdConhecimento.length; i++){
							if (arrayIdConhecimento[i].value == id){
								alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
								return false;
							}
						}
					} else if (lastRow == 2){
						var idConhecimento = document.getElementsByName("idConhecimento");
						if (idConhecimento[0].value == id){
							alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
							return false;
						}
					}
					return true;
			}
			 
			function removeLinhaTabelaUsuario(idTabela, rowIndex) {
				if (confirm(i18n_message("citcorpore.comum.deleta"))){
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
			
			function removeLinhaTabelaGrupo(idTabela, rowIndex) {
				if (confirm(i18n_message("citcorpore.comum.deleta"))){
					HTMLUtils.deleteRow(idTabela, rowIndex);
					var tabela = document.getElementById(idTabela);
					if (tabela.rows.length == 1){
						if (idTabela == 'tabelaGrupo'){
							$('#gridGrupo').hide();
							return;
						}
						$('#tabelaGrupo').hide();
					}
				}
			}
			
			function removeLinhaTabelaConhecimentoRelacionado(idTabela, rowIndex) {
				if (confirm(i18n_message("citcorpore.comum.deleta"))){
					HTMLUtils.deleteRow(idTabela, rowIndex);
					var tabela = document.getElementById(idTabela);
					if (tabela.rows.length == 1){
						if (idTabela == 'tabelaConhecimentoRelacionado'){
							$('#gridConhecimentoRelacionado').hide();
							return;
						}
						$('#tabelaConhecimentoRelacionado').hide();
					}
				}
			}
			
			function ImportanciaConhecimentoUsuarioDTO(idBaseConhecimento, _id, grau){
				this.idBaseConhecimento = idBaseConhecimento;
		 		this.idUsuario = _id; 
		 		this.grauImportanciaUsuario = grau;
		 	}
			
			function ImportanciaConhecimentoGrupoDTO(idBaseConhecimento, _id, grau){
				this.idBaseConhecimento = idBaseConhecimento;
		 		this.idGrupo = _id; 
		 		this.grauImportanciaGrupo = grau;
		 	}
			
			function serializaUsuario() {
				var tabela = document.getElementById('tabelaUsuario');
				var count = contUsuario + 1;
				var listUsuario = [];
				
				var idBaseConhecimento = $('#identificadorBase').val();
				
				for ( var i = 1; i < count; i++) {
					if (document.getElementById('idUsuario' + i) != "" && document.getElementById('idUsuario' + i) != null) {
						var idUsuario = document.getElementById('idUsuario' + i).value;
						var grauImportancia;
						
						if ($('#baixoUsuario' + i).is(":checked")){
							grauImportancia = "1";
						} else{
							if ($('#medioUsuario' + i).is(":checked")){
								grauImportancia = "2";
							} else{
								if ($('#altoUsuario' + i).is(":checked")){
									grauImportancia = "3";
								}
							}
						}
						var usuario = new ImportanciaConhecimentoUsuarioDTO(idBaseConhecimento, idUsuario, grauImportancia);
						listUsuario.push(usuario);
					}
				}
				document.form.listImportanciaConhecimentoUsuarioSerializado.value = ObjectUtils.serializeObjects(listUsuario);
			}
			
			function serializaGrupo() {
				var tabela = document.getElementById('tabelaGrupo');
				var count = contGrupo + 1;
				var listGrupo = [];
				
				var idBaseConhecimento = $('#identificadorBase').val();
				
				for ( var i = 1; i < count; i++) {
					if (document.getElementById('idGrupo' + i) != "" && document.getElementById('idGrupo' + i) != null) {
						var idGrupo = document.getElementById('idGrupo' + i).value;
						var grauImportancia;
						
						if ($('#baixoGrupo' + i).is(":checked")){
							grauImportancia = "1";
						} else{
							if ($('#medioGrupo' + i).is(":checked")){
								grauImportancia = "2";
							} else{
								if ($('#altoGrupo' + i).is(":checked")){
									grauImportancia = "3";
								}
							}
						}
						var grupo = new ImportanciaConhecimentoGrupoDTO(idBaseConhecimento, idGrupo, grauImportancia);
						listGrupo.push(grupo);
					}
				}
				document.form.listImportanciaConhecimentoGrupoSerializado.value = ObjectUtils.serializeObjects(listGrupo);
			}
			
			function BaseConhecimentoRelacionadoDTO(idBaseConhecimento, _id){
				this.idBaseConhecimento = idBaseConhecimento;
		 		this.idBaseConhecimentoRelacionado = _id; 
		 	}
			
			function serializaConhecimentoRelacionado() {
				var tabela = document.getElementById('tabelaConhecimentoRelacionado');
				var count = contConhecimentoRelacionado + 1;
				var listConhecimentoRelacionado = [];
				
				var idBaseConhecimento = $('#identificadorBase').val();
				
				for ( var i = 1; i < count; i++) {
					if (document.getElementById('idConhecimento' + i) != "" && document.getElementById('idConhecimento' + i) != null) {
						var idConhecimentoRelacionado = document.getElementById('idConhecimento' + i).value;

						var baseConhecimentoRelacionado = new BaseConhecimentoRelacionadoDTO(idBaseConhecimento, idConhecimentoRelacionado);
						listConhecimentoRelacionado.push(baseConhecimentoRelacionado);
					}
				}
				document.form.listConhecimentoRelacionadoSerializado.value = ObjectUtils.serializeObjects(listConhecimentoRelacionado);
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
			
			function deleteAllRowsUsuarioNotificacao() {
				var tabela = document.getElementById('tabelaUsuarioNotificacao');
				var count = tabela.rows.length;
		
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				$('#gridUsuarioNotificacao').hide();
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
			
			function deleteAllRowsGrupoNotificacao() {
				var tabela = document.getElementById('tabelaGrupoNotificacao');
				var count = tabela.rows.length;
		
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				$('#gridGrupoNotificacao').hide();
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
			
			function deleteAllRowsConhecimento() {
				var tabela = document.getElementById('tabelaConhecimentoRelacionado');
				var count = tabela.rows.length;
		
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				$('#gridConhecimentoRelacionado').hide();
			}
			
			function atribuirCheckedUsuario(grauImportanciaUsuario){
				if (grauImportanciaUsuario == "1"){
					$('#baixoUsuario' + contUsuario).attr('checked', true);
				} else{
					if (grauImportanciaUsuario == "2"){
						$('#medioUsuario' + contUsuario).attr('checked', true);
					} else{
						$('#altoUsuario' + contUsuario).attr('checked', true);
					}
				}
			}
			
		 	function atribuirCheckedGrupo(grauImportanciaGrupo){
				if (grauImportanciaGrupo == "1"){
					$('#baixoGrupo' + contGrupo).attr('checked', true);
				} else{
					if (grauImportanciaGrupo == "2"){
						$('#medioGrupo' + contGrupo).attr('checked', true);
					} else{
						$('#altoGrupo' + contGrupo).attr('checked', true);
					}
				}
			}
		 	
		 	function gravarImportanciaConhecimento(){
		 		serializaUsuario();
				serializaGrupo();
				document.form.fireEvent("gravarImportanciaConhecimento");		 	
	 		}
		 	
		 	function gravarConhecimentoRelacionado(){
		 		serializaConhecimentoRelacionado();
				document.form.fireEvent("gravarConhecimentoRelacionado");		 	
	 		}
		 	
		 	function gravarEventoMonitConhecimento (){
		 		serializaEventoMonitoramento();
				document.form.fireEvent("gravarEventoMonitConhecimento");		 	
	 		}
		 	
		 	function ocultarBtnGravarImportancia(){
				$('#btnGravarImportancia').hide();
			}
		 	
		 	function exibirBtnGravarImportancia(){
		 		$('#btnGravarImportancia').show();
		 	}
		 	
		 	function ocultarBtnGravarConhecimentoRelacionado(){
				$('#btnGravarConhecimentoRelacionado').hide();
			}
		 	
		 	function exibirBtnGravarConhecimentoRelacionado(){
				$('#btnGravarConhecimentoRelacionado').show();
			}
		 	
		
			function NotificacaoUsuarioDTO(_id, i) {
				this.idUsuario = _id;
			}

			function serializaUsuarioNotificacao() {
				var tabela = document.getElementById('tabelaUsuarioNotificacao');
				var count = contUsuarioNotificacao + 1;
				var listaDeUsuario = [];
				for ( var i = 1; i < count; i++) {
					if (document.getElementById('idUsuarioNotificacao' + i) != ""
							&& document.getElementById('idUsuarioNotificacao' + i) != null) {
						var trObj = document.getElementById('idUsuarioNotificacao' + i).value;
						var usuario = new NotificacaoUsuarioDTO(trObj, i);
						listaDeUsuario.push(usuario);
					}
				}
				var serializaUsuario = ObjectUtils.serializeObjects(listaDeUsuario);
				document.form.listUsuariosNotificacaoSerializados.value = serializaUsuario;
			}

			function NotificacaoGrupoDTO(_id, i) {
				this.idGrupo = _id;
			}

			function serializaGrupoNotificacao() {
				var tabela = document.getElementById('tabelaGrupoNotificacao');
				var count = contGrupoNotificacao + 1;
				var listaDeGrupo = [];
				for ( var i = 1; i < count; i++) {
					if (document.getElementById('idGrupoNotificacao' + i) != ""
							&& document.getElementById('idGrupoNotificacao' + i) != null) {
						var trObj = document.getElementById('idGrupoNotificacao' + i).value;
						var grupo = new NotificacaoGrupoDTO(trObj, i);
						listaDeGrupo.push(grupo);
					}
				}
				var serializaGrupo = ObjectUtils.serializeObjects(listaDeGrupo);
				document.form.listGruposNotificacaoSerializados.value = serializaGrupo;
			}
			
			function setarValoresPopupNotificacao() {
				$('#notificacaoTitulo').val($('#tituloNotificacao').val());
				var tipoNotificacao = $('#tipoNotificacao').val();
				$('#tipo').val(tipoNotificacao);
			}
			
			function fecharNotificacao(){
				$("#POPUP_NOTIFICACAO").dialog("close");
			}
			
			function gravarNotificacao() {
				var tituloNotificacao = $('#notificacaoTitulo').val()
				$('#tituloNotificacao').val(tituloNotificacao);
				var tipoNotificacao = $('#tipo').val();
				$('#tipoNotificacao').val(tipoNotificacao);
				serializaUsuarioNotificacao();
				serializaGrupoNotificacao();
				document.form.fireEvent("gravarNotificacao");
			}
			
			function tamanhoCampo(obj, limit) {
		  		if (obj.value.length >= limit) {
		  			obj.value = obj.value.substring(0, limit-1);
		  		}
		  	}
			
			function habilitarPergunta(){
				if ($('#faq').is(":checked")){
					$('#idLabelTituloFAQ').show();
					$('#idLabelConteudoFAQ').show();
					$('#idLabelTitulo').hide();
					$('#idLabelConteudo').hide();
					ocultarAnexos();
				}else{
					$('#idLabelTituloFAQ').hide();
					$('#idLabelConteudoFAQ').hide();
					$('#idLabelTitulo').show();
					$('#idLabelConteudo').show();
					exibirAnexos();
				}
			}
			
			function ocultarAnexos(){
				$('#formularioDeAnexos').hide();
			}
			
			function exibirAnexos(){
				$('#formularioDeAnexos').show();
			}
			
			function ckeckarFaq(){
				$('#faq').attr('checked', true);
			}
			
			aguarde = function(){
				JANELA_AGUARDE_MENU.show();
			}
			
			fechar_aguarde = function(){
		    	JANELA_AGUARDE_MENU.hide();
			}
			
			function arquivar(){
				document.form.fireEvent('arquivarConhecimento');
			}
			
			function restaurar(){
				document.form.fireEvent('restaurarConhecimento');
			}
			
			function exibirArquivado(){
				$('#idArquivado').show();
			}
			
			function ocultarArquivado(){
				$('#idArquivado').hide();
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
			
			function gravarGestaoMudanca(idBaseConhecimento){
				if(idBaseConhecimento=="" ||idBaseConhecimento == null ){
					alert(i18n_message("baseConhecimento.validacao.selecionebase"));
					return;
				}
				
				var id =  parseInt(idBaseConhecimento);
				
				document.getElementById('iframeMudanca').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/requisicaoMudanca/requisicaoMudanca.load?idBaseConhecimento=' + id;
				
				$("#POPUP_MUDANCA").dialog("open");				
			}
			
			function fecharMudanca(){
				$("#POPUP_MUDANCA").dialog('close');
			}	
			
			function adicionarEvento() {
				$("#POPUP_LOOKUPEVENTOMONITORAMENTO").dialog("open");
			}
			
			function LOOKUP_EVENTO_MONITORAMENTO_select(id, desc) {
				addLinhaTabelaEvento(id,desc,true);
			}
			
			
			function addLinhaTabelaEvento(id, desc, valida){
				var tbl = document.getElementById('tabelaEvento');
				var descricao = desc.split("-");
				$('#tabelaEvento').show();
				$('#gridEvento').show();

				var lastRow = tbl.rows.length;
				
				if (valida){
					if (!validaAddLinhaTabelaEvento(lastRow, id)){
						return;
					}
				}
				
				var row = tbl.insertRow(lastRow);
				var coluna = row.insertCell(0);
				contEvento++;
				
				coluna.innerHTML = '<img id="imgDelEvento' + contEvento + '" style="cursor: pointer;" title="i18n_message("citcorpore.comum.cliquaParaExcluir")" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabelaEventoMonitoramento(\'tabelaEvento\', this.parentNode.parentNode.rowIndex);">';
				
				coluna = row.insertCell(1);
				
				coluna.innerHTML = descricao[0] + '<input type="hidden" id="idEventoMonitoramento' + contEvento + '" name="idEventoMonitoramento" value="' + id + '" />';
				
				coluna = row.insertCell(2);
				
				coluna.innerHTML = descricao[1] ;
				
				coluna = row.insertCell(3);
				
				coluna.innerHTML = descricao[4] + "/" + descricao[3] + "/" + descricao[2];
				
				$('#POPUP_LOOKUPEVENTOMONITORAMENTO').dialog("close");		
					
			}
		 	
		 	function validaAddLinhaTabelaEvento(lastRow, id) {
				if (lastRow > 2) {
					var arrayIdEvento = document.getElementsByName("idEventoMonitoramento");
					for ( var i = 0; i < arrayIdEvento.length; i++) {
						if (arrayIdEvento[i].value == id) {
							alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
							return false;
						}
					}
				} else if (lastRow == 2) {
					var idEventoMonitoramento =  document.getElementsByName("idEventoMonitoramento");
					if (idEventoMonitoramento[0].value == id) {
						alert(i18n_message("citcorpore.comum.registroJaCadastrado"));
						return false;
					}
				}
				return true;
			}

		 	function limpaTabelaEventoMonitoramento() {
				var tabela = document.getElementById('tabelaEvento');
				var count = tabela.rows.length;
	
				while (count > 1) {		
					tabela.deleteRow(count - 1);
					count--;
				}
		 	}
		 	
		 	function removeLinhaTabelaEventoMonitoramento(idTabela, rowIndex) {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
					var tabela = document.getElementById(idTabela);
					if (tabela.rows.length == 1) {
						if (idTabela == 'tabelaEvento') {
							$('#gridEvento').hide();
							return;
						}
						$('#tabelaEvento').hide();
					}
				}
			}
		 	
		 	function EventoMonitConhecimentoDTO(_id, i) {
				this.idEventoMonitoramento = _id;
			}

			function serializaEventoMonitoramento() {
				 var tabela = document.getElementById('tabelaEvento');
				var count = contEvento + 1;
				var listaDeEventoMonitoramento = [];
				for ( var i = 1; i < count; i++) {
					if (document.getElementById('idEventoMonitoramento' + i) != ""
							&& document.getElementById('idEventoMonitoramento' + i) != null) {
						var trObj = document.getElementById('idEventoMonitoramento' + i).value;
						var eventoMonitoramento = new EventoMonitConhecimentoDTO(trObj, i);
						listaDeEventoMonitoramento.push(eventoMonitoramento);
					}
				}
				var serializaEventoMonitoramento = ObjectUtils.serializeObjects(listaDeEventoMonitoramento);
				document.form.listEventoMonitoramentoSerializado.value = serializaEventoMonitoramento; 
			}
		 	
			function deleteAllRowsEventoMonitoramento() {
				var tabela = document.getElementById('tabelaEvento');
				var count = tabela.rows.length;
		
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				$('#gridEvento').hide();
			}
			
			function validaFaq(){
				$('#faq').attr('checked',false);
				$('#documento').attr('checked',false);
				$('#erroConhecido').attr('checked',true);
				habilitarPergunta();
			}
			
			function validaErroConhecido(){
				$('#erroConhecido').attr('checked',false);
				$('#documento').attr('checked',false);
				$('#faq').attr('checked',true);
			}
			
			function validaDocumento(){
				$('#faq').attr('checked',false);
				$('#erroConhecido').attr('checked',false);
				$('#documento').attr('checked',true);
				habilitarPergunta();
			}
			
			fechar = function(){
				var iframe = <%=iframe%>
				if(iframe != null){
					parent.fecharBaseConhecimento();
				}else{
					return;
				}
			}
			
		</script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
	<body>	
		<script type="text/javascript">
			function setDataEditor(){
				var oEditor = FCKeditorAPI.GetInstance( 'conteudoBaseConhecimento' ) ;
			    oEditor.SetData(document.form.conteudoBaseConhecimento.value);
			}    
		</script>
		<script type="text/javascript" src="../../cit/objects/ComentariosDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
					<%
						if (iframe == null) {
					%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="baseConhecimento.baseConhecimento"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="baseConhecimento.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="baseConhecimento.pesquisa"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">											
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimento/baseConhecimento' enctype="multipart/form-data">
									<div class="columns clearfix">
										<input type='hidden' id='versao' name='versao'/>
										<input type='hidden' name='idBaseConhecimentoPai'/>
										<input type='hidden' name='comentariosSerializados'/>
										<input type='hidden' name='comentariosDeserializados'/>
										<input type='hidden' id='comentarioSerializado' name='comentarioDeserializado'/>
										<input type='hidden' id='idListUsuariosGrauImportanciaSerializado' name='listImportanciaConhecimentoUsuarioSerializado'/>
										<input type='hidden' id='idListGrupoosGrauImportanciaSerializado' name='listImportanciaConhecimentoGrupoSerializado'/>
										<input type='hidden' id='idListConhecimentoRelacionadoSerializado' name='listConhecimentoRelacionadoSerializado'/>
										<input type='hidden' name='idUsuarioAutor'/>
										<input type='hidden' id='idConhecimentoRelacionado' name="idConhecimentoRelacionado"/>
										<input type='hidden' id="tituloNotificacao" name="tituloNotificacao"/>
										<input type='hidden' id='tipoNotificacao' name="tipoNotificacao"/>
										<input type="hidden" id="listUsuariosNotificacaoSerializados" name="listUsuariosNotificacaoSerializados"/>
										<input type="hidden" id="listGruposNotificacaoSerializados" name="listGruposNotificacaoSerializados" />
										<input type="hidden" id="idNotificacao" name="idNotificacao" />
										<input type="hidden" id="idHistoricoBaseConhecimento" name="idHistoricoBaseConhecimento" />
										<input type="hidden" id="arquivado" name="arquivado" />
										<input type="hidden" id="idListEventoMonitoramento" name="listEventoMonitoramentoSerializado" />
										<input type="hidden" id="idProblema" name="idProblema" />
										
										<span id="idArquivado"><label style="font-weight: bold; color: red; padding-left: 20px; padding-right: 20px; border: 1px solid red;"><i18n:message key="baseconhecimento.arquivado"/></label></span>
										<!-- Começo da div -->
										<div class="columns clearfix">
											<div class="col_20" >
												<fieldset id="idFaq" >
													<label ><i18n:message key="baseconhecimento.tipoconhecimento"/></label>
													<div >
													  	<span class="publicabase">
													  	<label style="display: block">
														  		<input  type="radio" id="documento" name="documento"  value="S" onclick="validaDocumento()"  /><i18n:message key="baseconhecimento.documento"/>
														  	</label>
														  	<label style="display: block">
														  		<input  type="radio" id="faq" name="faq" value="S" onclick="habilitarPergunta();validaErroConhecido()"/><i18n:message key="baseconhecimento.faq"/>
														  	</label>
														  	<label style="display: block">
														  		<input  type="radio" id="erroConhecido" name="erroConhecido"  value="S"  onclick="validaFaq()"/><i18n:message key="baseConhecimento.erroConhecido"/>
														  	</label>
													  	</span>
													</div>
												</fieldset>
											</div>
											<div class="col_10" >				
												<fieldset id="idIdentificadorBase" style="height: 80px;">
												  	<label><i18n:message key="citSmart.comum.identificador"/></label>
												  	<div >
												  		<input id="identificadorBase" type='text' class="outputext" name="idBaseConhecimento" readonly="readonly"/>
												  	</div>
												</fieldset>
											</div>
											
										
											<div class="col_10">
												<fieldset style="height: 80px; text-align: center;" >
												  	<label><i18n:message key="baseconhecimento.notificacoes"/></label>
												  	<div>
												  		<input type="image" class='' style="cursor: pointer;" title="<i18n:message key='pasta.notificacoes'/>" onclick="abrirPopupNotificacao();return false;"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/large/grey/mail_2.png"/>
												  	</div>
												</fieldset>
											</div>
											
											<div class="col_10">
												<fieldset style="height: 80px; text-align: center;">
												  	<label><i18n:message key="baseconhecimento.importancia"/></label>
												  	<div>
												  		<input type="image" class='' style="cursor: pointer;" title="<i18n:message key='baseConhecimento.definirImportancia'/>" onclick="abrirPopupImportanciaConhecimento();return false;"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/graudeimportancia.png" />
												  	</div>
												</fieldset>
											</div>
											
											<div class="col_10">
												<fieldset style="height: 80px; text-align: center;	">
												  	<label><i18n:message key="baseconhecimento.conhecimentorelacionado"/></label>
												  	<div>
												  		<input type="image" class='' style="cursor: pointer;" title="<i18n:message key='baseConhecimento.relacionarConhecimento'/>" onclick="abrirPopupConhecimentoRelacionado();return false;"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/conhecimentorelacionado.png"/>
												  	</div>
												</fieldset>
											</div>
											
											<div id="divSolicitacaoServico" class="col_10">
												<fieldset style="height: 80px; text-align: center;	">
												  	<label ><i18n:message key="solicitacaoServico.solicitacao"/></label>
												  	<div>
												  		<input type="image" class='' style="cursor: pointer;" title="<i18n:message key='rh.solicitacaoServico'/>" onclick="gravarIncidentesRequisicao(document.form.idBaseConhecimento.value);return false;"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/large/grey/cog.png"/>
												  	</div>
												</fieldset>
											</div>	
											
											<div id="divMudanca" class="col_10">
												<fieldset style="height: 80px; text-align: center;	">
												  	<label ><i18n:message key="requisicaMudanca.mudanca"/></label>
												  	<div>
												  		<input type="image" class='' style="cursor: pointer;" title="<i18n:message key='baseConhecimento.requisicaoMudanca'/>" onclick="gravarGestaoMudanca(document.form.idBaseConhecimento.value);return false;"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/large/grey/alert_2.png"/>
												  	</div>
												</fieldset>
											</div>	
											
											<%
												if(!br.com.citframework.util.Util.isVersionFree(request)){												
											%>
											<div id="divEvento" class="col_10" >
												<fieldset style="height: 80px; text-align: center;	">
												  	<label ><i18n:message key="eventoMonitoramento.Evento"/></label>
												  	<div>
												  		<input type="image" class='' style="cursor: pointer;" title="<i18n:message key='menu.nome.eventoMonitoramento'/>" onclick="abrirPopupEventoMonitConhecimento();return false;"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/large/grey/radio_signal.png"/>
												  	</div>
												</fieldset>
											</div>
											<%
												}
											%>
											
										</div>
										<!-- Fim da div -->
										<div class="columns clearfix">
											<div class="col_33">				
												<fieldset>
													<label class="campoObrigatorio" style="margin-top: 5px;" id="idLabelTitulo"><i18n:message key="baseConhecimento.titulo"/></label>
													<label class="campoObrigatorio" style="margin-top: 5px;" id="idLabelTituloFAQ"><i18n:message key="citcorpore.comum.pergunta"/></label>
													<div>
													  	<input type="text" id="titulo" name="titulo" maxlength="70" size="70" class="Valid[Required] Description[baseConhecimento.titulo]"/>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label  style="margin-top: 5px;"><i18n:message key="baseconhecimento.fontereferencia"/></label>
														<div>
														  	<input type='text' id="fonteReferencia" name="fonteReferencia" maxlength="70" size="70" />
														</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="pasta.pasta"/></label>
													<div>
													  	<select id="comboPasta" name="idPasta" style="margin-bottom: 3px;" onchange='verificarPermissoesDeAcesso();' class="Valid[Required] Description[pasta.pasta]"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_16">
												<fieldset >
													<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="citcorpore.comum.origem"/></label>
													<div>
													  	<select id="comboOrigem" name="origem" style="margin-bottom: 3px;" class="Valid[Required] Description[citcorpore.comum.origem]"></select>
													</div>
												</fieldset>
											</div>
										</div>
										
										<div class="columns clearfix">
											<div class="col_33">				
												<fieldset style="height: 100px;">
													<label style="margin-top: 5px;"><i18n:message key="baseconhecimento.justificativaobservacao"/></label>
													<div>
												  		<textarea id="justificativaObservacao" name="justificativaObservacao" rows="1" cols="1" style="display: block;" onKeyDown="tamanhoCampo(this, 500);" onKeyUp="tamanhoCampo(this, 500);"></textarea>
													</div>
												</fieldset>
											</div>
											<div class="col_15" style="width: 8% !important;">
												<fieldset class="tooltip_bottom " title="<i18n:message key='baseconhecimento.publicarTitulo'/>" id="publicacao" style="height: 100px;">
												
													<label class="campoObrigatorio" style="margin-top: 5px; padding-left: 12px;"><i18n:message key="baseconhecimento.publicar"/></label>
												  	<span class="publicabase">
													  	<label style="cursor: pointer; padding-right: 1px;">
													  		<input style="margin-right: 5px;" type="radio" id="aprovaBaseConhecimentoTrue" name="status" value="S"/>
													  		<i18n:message key="citcorpore.comum.sim"/>
													  	</label>
													  	<label style="cursor: pointer;">
													  		<input type="radio" id="aprovaBaseConhecimentoFalse" name="status" value="N" />
													  		<i18n:message key="citcorpore.comum.nao"/>
													  	</label>
												  	</span>
												
												</fieldset>
											</div>
											<div class="col_15" style="width: 10% !important;">
												<fieldset style="height: 100px;">
													<label style="margin-top: 5px; padding-left: 12px;"><i18n:message key="baseconhecimento.privacidade"/></label>
													<div>
													  	<select id="comboPrivacidade" id="privacidade" name="privacidade" style="margin-bottom: 3px;"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_15" style="width: 10% !important;">
												<fieldset style="height: 100px;">
													<label style="margin-top: 5px; padding-left: 12px;"><i18n:message key="citcorpore.comum.situacao"/></label>
													<div>
													  	<select id="comboSituacao" id="situacao" name="situacao" style="margin-bottom: 3px;"></select>
													</div>
												</fieldset>
											</div>
											<div class="col_16">
												<fieldset id="idautor" style="height: 100px;">
													<label style="margin-top: 5px; padding-left: 12px;"><i18n:message key="citcorpore.comum.autor"/></label>
												  	<input type='text' class="outputext" name="autor" readonly="readonly" style="padding-left: 9px;"/>
												</fieldset>
											</div>
											<div class="col_16">
												<fieldset id="idaprovador" style="height: 100px;">
												  	<label style="margin-top: 5px; padding-left: 12px;"><i18n:message key="citcorpore.comum.aprovador"/></label>
												  	<input type='text' class="outputext" name="aprovador" readonly="readonly" style="padding-left: 9px;"/>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix" style="margin-top: -1px;">
											<div class="col_16">
												<fieldset id="idDataInicio" style="height: 71px;">
												  	<label ><i18n:message key="citcorpore.comum.dataCriacao"/></label>
											  		<input style="margin-top: 5px; padding-left: 15px;" id=dataInicio type='text' class="outputext" name="dataInicio" readonly="readonly" />
												</fieldset>
											</div>
											<div class="col_16">
												<fieldset style="height: 71px;">
													<label class="campoObrigatorio" ><i18n:message key="baseConhecimento.dataExpiracao"/></label>
											  		<input type='text'  id="dataExpiracao" name="dataExpiracao" maxlength="10" readonly="readonly" size="8"  class="Valid[Required,Data] Description[baseConhecimento.dataExpiracao] Format[Data] datepicker" />
												</fieldset>
											</div>
											<div class="col_16">
												<fieldset id="idDataPublicacao" style="height: 71px;">
												  	<label><i18n:message key="citcorpore.comum.dataPublicacao"/></label>
											  		<input id=dataPublicacao type='text' class="outputext" name="dataPublicacao" readonly="readonly" />
												</fieldset>
											</div>
											<div class="col_16" >
												<fieldset>
													<div >
													<label style="display: block; padding-top: 15px;"><input  type="checkbox" checked="checked" name="gerenciamentoDisponibilidade" id="gerenciamentoDisponibilidade" value="S" /><i18n:message key="baseconhecimento.gerenciamentoDisponibilidade"/></label>
													</div>
												</fieldset>
											</div>
											<div class="col_16" >
												<fieldset>
													<div >
													<label style="display: block; padding-top: 15px;"><input  type="checkbox" checked="checked" name="direitoAutoral" id="direitoAutoral" value="S" /><i18n:message key="baseconhecimento.direitoAutoral"/></label>
													</div>
												</fieldset>
											</div>
											<div class="col_16" >
												<fieldset>
													<div >
													<label style="display: block; padding-top: 15px;"><input  type="checkbox" checked="checked" name="legislacao" id="legislacao" value="S" /><i18n:message key="baseconhecimento.legislacao"/></label>
													</div>
												</fieldset>
											</div>	
										</div>
										<div class="columns clearfix" >
																																										
										</div>
										
										<div class="columns clearfix" >
											<div class="col_50">				
												<fieldset>
													<div id="gridCont" class="columns clearfix">
														<label style="font-weight: bold" id="idLabelConteudo"><i18n:message key="baseConhecimento.conteudo"/></label>
														<label style="font-weight: bold" id="idLabelConteudoFAQ"><i18n:message key="citcorpore.comum.resposta"/></label>
													</div>
													<div>
													  	<textarea id="conteudoBaseConhecimento" name="conteudo" rows="3" cols="80" style="display: block;"></textarea>
													</div>
												</fieldset>
											</div>												
											<div class="col_50">
												<fieldset>
														<div id="gridComentario" class="columns clearfix" style="display: none;">
															<label style="font-weight: bold"><i18n:message key="baseConhecimento.comentarios"/></label>
			  												<table class="table" id="tabelaComentarios" style="width: 95%">
																<tr style="text-align: left;">
																	<th></th>
																	<th style="width: 15%;"><i18n:message key="citcorpore.comum.nome"/></th>
																	<th style="width: 25%;"><i18n:message key="citcorpore.comum.email"/></th>
																	<th style="width: 30%;"><i18n:message key="baseConhecimento.comentario"/></th>
																	<th style="width: 30%;"><i18n:message key="baseConhecimento.avaliacao"/></th>
																</tr>
															</table>
														</div>
												</fieldset>
											</div>
										</div>
										<br>
									</div>
									
									
									<div id="POPUP_GRAUDEIMPORTANCIA" class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<h2><i18n:message key="citcorpore.comum.usuario" /></h2>
												<label id="addUsuario" style="cursor: pointer;">
													<img title="Adicionar Usuário" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/adicionarUsuario.png" onclick="adicionarUsuario(false)">
												</label>
												<div  id="gridUsuario">
													<table class="table" id="tabelaUsuario"
														style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 50%;"><i18n:message key="citcorpore.comum.usuario"/></th>
															<th style="width: 49%;"><i18n:message key="baseconhecimento.graudeimportancia"/></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<h2><i18n:message key="grupo.grupo"/></h2>
												<label id="addGrupo" style="cursor: pointer;">
													<img title="Adicionar Grupo" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add2.png" onclick="adicionarGrupo(false)">
												</label>
												<div  id="gridGrupo">
													<table class="table" id="tabelaGrupo"  style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 50%;"><i18n:message key="grupo.grupo" /></th>
															<th style="width: 49%;"><i18n:message key="baseconhecimento.graudeimportancia"/></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
										
										<div class="col_100">
											<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
												<button id="btnGravarImportancia" type='button' name='btnGravarImportancia' class="light"  onclick='gravarImportanciaConhecimento();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
													<span><i18n:message key="citcorpore.comum.gravar"/></span>
												</button>
												
												<button id="btnFechar" type='button' name='btnFechar' class="light"  onclick='fecharPopupGrauDeImportancia();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
													<span><i18n:message key="citcorpore.comum.fechar"/></span>
												</button>
												
											</fieldset>
										</div>
									</div>
									
									
									<div id="POPUP_CONHECIMENTORELACIONADO" class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<h2><i18n:message key="baseConhecimento.conhecimento"/></h2>
												<label id="addConhecimento" style="cursor: pointer;">
													<img title="Adicionar Conhecimento" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/adicionarConhecimento.png" onclick="adicionarConhecimentoRelacionado()">
												</label>
												<div  id="gridConhecimentoRelacionado">
													<table class="table" id="tabelaConhecimentoRelacionado" style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 50%;"><i18n:message key="baseconhecimento.documento"/></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
												<button id="btnGravarConhecimentoRelacionado" type='button' name='btnGravarConhecimentoRelacionado' class="light"  onclick='gravarConhecimentoRelacionado();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
													<span><i18n:message key="citcorpore.comum.gravar"/></span>
												</button>
												
												<button id="btnFechar" type='button' name='btnFechar' class="light"  onclick='fecharPopupConhecimentoRelacionado();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
													<span><i18n:message key="citcorpore.comum.fechar"/></span>
												</button>
												
											</fieldset>
										</div>
									</div>
									
									<div id="POPUP_NOTIFICACAO" >
										<div class="columns clearfix">
											<div class="col_50">
												<fieldset >
													<label class="campoObrigatorio"><i18n:message key="notificacao.titulo" /></label>
													<div >
														<input type='text' id="notificacaoTitulo" name="notificacaoTitulo" maxlength="255"  />
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
													<label id="addUsuario" style="cursor: pointer;"><i18n:message key="citcorpore.comum.usuario" />
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/adicionarUsuario.png" onclick="adicionarUsuario(true)"></label>
													<div  id="gridUsuarioNotificacao">
														<table class="table" id="tabelaUsuarioNotificacao">
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
													<label id="addGrupo" style="cursor: pointer;"><i18n:message key="grupo.grupo" />
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add2.png" onclick="adicionarGrupo(true)" ></label>
													<div  id="gridGrupoNotificacao">
														<table class="table" id="tabelaGrupoNotificacao"  >
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
   							 		
   							 		<div id="POPUP_EVENTOMONITCONHECIMENTO" class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<h2><i18n:message key="eventoMonitoramento.Evento"/></h2>
												<label id="addEvento" style="cursor: pointer;">
													<img title="Adicionar Evento" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/adicionarEvento.png" onclick="adicionarEvento()">
												</label>
												<div  id="gridEvento">
													<table class="table" id="tabelaEvento"
														style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 50%;"><i18n:message key="citcorpore.comum.nome"/></th>
															<th style="width: 49%;"><i18n:message key="citcorpore.comum.criadopor"/></th>
															<th style="width: 49%;"><i18n:message key="citcorpore.comum.dataCriacao"/></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
										
										<div class="col_100">
											<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
												<button id="btnGravarEventoMonitConhecimento" type='button' name='btnGravarEventoMonitConhecimento' class="light"  onclick='gravarEventoMonitConhecimento();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
													<span><i18n:message key="citcorpore.comum.gravar"/></span>
												</button>
												
												<button id="btnFechar" type='button' name='btnFechar' class="light"  onclick='fecharPopupEventoMonitConhecimento();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
													<span><i18n:message key="citcorpore.comum.fechar"/></span>
												</button>
												
											</fieldset>
										</div>
									</div>
									
									
									<button id="btnGravar" type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button id="btnLimpar" type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>	
									<button id="btnExcluir" type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir"/></span>
									</button>
									<button id="btnArquivar" type='button' name='btnArquivar' class="light" onclick='arquivar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/folder.png">
										<span><i18n:message key="baseConhecimento.arquivar"/></span>
									</button>
									<button id="btnRestaurar" type='button' name='btnRestaurar' class="light" onclick='restaurar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/folder.png">
										<span><i18n:message key="itemConfiguracaoTree.restaurar"/></span>
									</button>
								</form>
								
								<form name="formUpload" method="post" enctype="multipart/form-data" id="formularioDeAnexos">
										<div class="columns clearfix">
											<div class="col_100">				
												<fieldset style="margin-top: 5px;">
													<label style="margin-top: 5px; margin-bottom: 5px;"><i18n:message key="baseConhecimento.anexo"/></label>
													<cit:uploadControl style="height:100px;width:50%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
												</fieldset>
											</div>
										</div>		
								</form>	
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_BASECONHECIMENTO' id='LOOKUP_BASECONHECIMENTO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
		<div class="box grid_16 tabs" style='width: 560px !important;'>
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaUsuario' style="width: 540px">
							<input type="hidden" id="isNotificacao" name="isNotificacao">
							<cit:findField formName='formPesquisaUsuario'  lockupName='LOOKUP_USUARIO_CONHECIMENTO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
	<div id="POPUP_GRUPO" title="<i18n:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs" style='width: 560px !important;'>
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaGrupo' style="width: 540px">
						<input type="hidden" id="isNotificacaoGrupo" name="isNotificacaoGrupo">
							<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="POPUP_CONHECIMENTO" title="<i18n:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaConhecimento' style="width: 540px">
							<cit:findField formName='formPesquisaConhecimento' lockupName='LOOKUP_BASECONHECIMENTO' id='LOOKUP_CONHECIMENTO_RELACIONADO' top='0' left='0' len='550' heigth='700' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="POPUP_LOOKUPEVENTOMONITORAMENTO" title="<i18n:message key="citcorpore.comum.pesquisar" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaEventoMonitoramento' style="width: 540px">
							<cit:findField formName='formPesquisaEventoMonitoramento' lockupName='LOOKUP_EVENTO_MONITORAMENTO' id='LOOKUP_EVENTO_MONITORAMENTO' top='0' left='0' len='550' heigth='700' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="POPUP_INCIDENTE" title="<i18n:message key='solicitacaoServico.solicitacao' /> " >
		<iframe id="iframeSolicitacaoServico"  name="iframeSolicitacaoServico" width="100%" height="100%"> </iframe>
	</div>
	
	<div id="POPUP_MUDANCA" title="<i18n:message key='requisicaMudanca.mudanca' /> " >
		<iframe id="iframeMudanca"  name="iframeMudanca" width="100%" height="100%"> </iframe>
	</div>
		
</html>