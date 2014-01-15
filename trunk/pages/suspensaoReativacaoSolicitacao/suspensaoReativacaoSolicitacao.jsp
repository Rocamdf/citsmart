<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ taglib uri="/tags/menuPadrao" prefix="menu"%>

<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");

	String idSolicitacaoServico = UtilStrings.nullToVazio((String) request.getParameter("idSolicitacaoServico"));
	String nomeTarefa = UtilStrings.nullToVazio((String) request.getAttribute("nomeTarefa"));
%>
<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
<link type="text/css" rel="stylesheet"
	href="../../novoLayout/common/include/css/template.css" />
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
</head>
<body>
	<div class="<%=(iframe == null) ? "container-fluid fixed" : ""%>">

		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div
			class="navbar <%=(iframe == null) ? "main" : "nomain"%> hidden-print">

			<%
				if (iframe == null) {
			%>
			<%@include file="/novoLayout/common/include/cabecalho.jsp"%>
			<%@include file="/novoLayout/common/include/menuPadrao.jsp"%>
			<%
				}
			%>

		</div>

		<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper"%>">

			<!-- Inicio conteudo -->
			<div id="content">
				<div class="box-generic">
					<div>
							<select id="idSelectTipo">
								<option value="Suspender"><i18n:message key="suspensaoReativacaoSolicitacao.SuspenderSolicitacao" /></option>
								<option value="Reativar"><i18n:message key="suspensaoReativacaoSolicitacao.ReativarSolicitacao" /></option>
							</select>
						</div>
					<form class="form-horizontal" name='form' id='form'	action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/suspensaoReativacaoSolicitacao/suspensaoReativacaoSolicitacao.load'>
					
						<input type="hidden" id="tipoAcao" name="tipoAcao">
						<div>
							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><i18n:message key="suspensaoReativacaoSolicitacao.Contrato" /></label>
									<select id="idContrato" name="idContrato" class="span10 Valid[Required]" required="required">
								</select>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><i18n:message key="suspensaoReativacaoSolicitacao.Solicitante" /></label>
									<div class=" input-append">
										<input type="text" class="span6 Valid[Required]" name="solicitante" id="solicitante" onfocus="montaParametrosAutocompleteServico();" required="required" placeholder="" value="" disabled="disabled">
										<span class="add-on">
											<i class="icon-search"></i>
										</span>
									</div>
								</div>
							</div>
							
							<div class="row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><i18n:message key="suspensaoReativacaoSolicitacao.Grupo" /></label>
									<select id="idGrupo" name="idGrupo" class="span10 Valid[Required]" required="required"></select>
								</div>
							</div>
							
						</div>
						
						<div id="idSuspensao">
							<div class="control-group row-fluid">
								<div class="span12">
									<label class="strong campoObrigatorio"><i18n:message key="citcorpore.comum.justificativa" /></label> 
									<select placeholder="" class="span10 Valid[Required]" id="idJustificativa" required="required" type="text" name="idJustificativa"></select>
								</div>
								<div class="span12">
									<label  class="strong campoObrigatorio"><i18n:message key="gerenciaservico.mudarsla.complementojustificativa"/></label>
									<textarea rows="5" class="span10 Valid[Required]" required="required" type="text" name="justificativa" id="justificativa"></textarea>
								</div>
							</div>
							<br>
							<span class="btn btn-icon btn-primary" onclick="suspenderSolicitacoes();"><i></i><i18n:message key="suspensaoReativacaoSolicitacao.Suspender" /></span>
							<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick="document.form.clear();" id=""><i></i><i18n:message key="suspensaoReativacaoSolicitacao.limparDados" /></button>
						</div>
						
						<div id="idReativacao" style="display: none;">
						<br>
							<span class="btn btn-icon btn-primary" onclick="reativarSolicitacoes();"><i></i><i18n:message key="suspensaoReativacaoSolicitacao.Reativar" /></span>
							<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick="document.form.clear();" id=""><i></i><i18n:message key="suspensaoReativacaoSolicitacao.limparDados" /></button>
						</div>
					</form>
				</div>
			</div>
			<!--  Fim conteudo-->
			<%@include file="/novoLayout/common/include/rodape.jsp"%>
			<script type="text/javascript">
		
		function suspenderSolicitacoes(){
			document.getElementById("tipoAcao").value = "suspender";
			if(validarCampos(document.getElementById("tipoAcao").value)!=true){
				var confimacao = confirm(i18n_message("suspensaoReativacaoSolicitacao.MensagemConfirmacaoSuspensao"));
				if(confimacao==true){
					document.form.fireEvent("processarSolicitacoes");
					document.form.clear();
				}
			}
				
		}	
				
		function reativarSolicitacoes(){
			document.getElementById("tipoAcao").value = "reativar";
			if(validarCampos(document.getElementById("tipoAcao").value)!=true){
				var confimacao = confirm(i18n_message("suspensaoReativacaoSolicitacao.MensagemConfirmacaoReativacao"));
				if(confimacao==true){
					document.form.fireEvent("processarSolicitacoes");
					document.form.clear();
				}
					
			}
		}	
		
		function validarCampos(tipoAcao){
			
			if(document.getElementById("idContrato").value==""){
				alert('i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioContrato")');
				return true;
			}
			if(document.getElementById("solicitante").value==""){
				alert('i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioSolicitante")');
				return true;
			}
			if(document.getElementById("idGrupo").value==""){
				alert('i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioidGrupo")');
				return true;
			} 
			if(tipoAcao=='suspender'){
				if(document.getElementById("justificativa").value==""){
					alert('i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVaziojustificativa")');
					return true;
				}
				if(document.getElementById("idJustificativa").value==""){
					alert('i18n_message("suspensaoReativacaoSolicitacao.alertaCampoVazioidJustificativa")');
					return true;
				}
					
			}
			return false;
			
		}
			
		var completeSolicitante;
		$(function(){
			$('#idSelectTipo').on('change', function() {
				  if(this.value=='Suspender'){
					  $("#idReativacao").hide();
					  $("#idSuspensao").show();
					  $("#solicitante").attr("disabled", "disabled");
				  }
				  else{
					  $("#idSuspensao").hide();
					  $("#idReativacao").show();
					  $("#solicitante").attr("disabled", "disabled");
				  }	
				  document.form.reset();
			});
			
			$('#idContrato').on('change', function() {
				if(this.value!="")
					$("#solicitante").removeAttr("disabled");
				else
					$("#solicitante").attr("disabled", "disabled");
				$("#solicitante").val("");
			});		
			
			completeSolicitante = $('#solicitante').autocomplete({ 
				serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
				noCache: true,
				onSelect: function(value, data){
					$('#idSolicitante').val(data);
					$('#solicitante').val(value);
					$('#nomecontato').val(value);
					document.form.fireEvent("restoreColaboradorSolicitante");
					document.form.fireEvent('renderizaHistoricoSolicitacoesEmAndamentoUsuario');
	
				}
			});
		});
		
		
		/**Monta os parametros para a buscas do autocomplete**/
		function montaParametrosAutocompleteServico(){
		 	contrato = $("#idContrato").val();
		 	completeSolicitante.setOptions({params: {contrato: contrato} });
		}
		</script>
		</div>
	</div>
</body>

</html>