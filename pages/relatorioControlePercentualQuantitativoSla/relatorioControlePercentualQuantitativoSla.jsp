<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<!doctype html public "">
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
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
div.main_container .box {
	margin-top: -0.3em!important;

}
</style>

<script><!--
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_SERVICOCONTRATO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});		
		
	}
	
	$(function() {
		$("#addServicoContrato").click(function() {
			if(verificaContrato()){
				var idContrato = document.form.idContrato.value;
				document.formPesquisaLocalidade.pesqLockupLOOKUP_SERVICOCONTRATO_IDCONTRATO.value = idContrato;
				$("#POPUP_SERVICOCONTRATO").dialog("open");
			}
		});
	});	
	
	function LOOKUP_SERVICOCONTRATO_select(id, desc){
		document.form.idServico.value = id;
		document.form.addServicoContrato.value = desc;
		$("#POPUP_SERVICOCONTRATO").dialog("close");
	}
	
	function fecharPopup(){
		$("#POPUP_SERVICOCONTRATO").dialog("close");
	}
	
	function imprimirRelatorioQuantitativo(){	
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					document.getElementById("dataFim").value = '';
					return false;					
				}

		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioControleSla");
			
		}
	}
	
	
	function imprimirRelatorioQuantitativoXls(){	
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;

				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					document.getElementById("dataFim").value = '';
					return false;					
				}
		
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioControleSlaXls");

	}

	
	function validaData(dataInicio, dataFim) {
		
		var dtInicio = new Date();
		var dtFim = new Date();
		
		dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
		dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
		
		if (dtInicio > dtFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}
	
	function limpar(){
		document.form.clear();
	}
	
	function verificaContrato() {
		if (document.form.idContrato.value == '' ||  document.form.idContrato.value == '-- Todos --'){
			alert(i18n_message("solicitacaoservico.validacao.contrato"));
			return false;
		}
		return true;
	}
	
	function limparServico(){
		document.form.idServico.value = "";
		document.form.addServicoContrato.value = "";
	}


--></script>
</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title=""
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="relatorioSlaPercentualQuantitativo.titulo" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" >
						<div id="parametros">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/relatorioControlePercentualQuantitativoSla/relatorioControlePercentualQuantitativoSla'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idServico' name='idServico'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 64px">
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td><input type='text' name='dataInicio'
															id='dataInicio' size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
														<td>&nbsp;-&nbsp;</td>
														<td><input type='text' name='dataFim' id='dataFim'
															size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 64px">
											<label><i18n:message key="contrato.contrato" /></label>
											<div>
												<select name="idContrato"  onchange=" document.form.fireEvent('carregaUnidade'); limparServico();"></select>
											</div>
										</fieldset>																	
									</div>
								
								<div class="col_25">
										<fieldset style="height: 64px">
											<label><i18n:message key="prioridade.prioridade" /></label>
											<div>
												<select name="idPrioridade"></select>
											</div>
										</fieldset>
									</div>
								
								
								
									<div class="col_25">
										<fieldset style="height: 64px">
											<label><i18n:message key="pesquisasolicitacao.gruposolucionador" /></label>
											<div>
												<select name="idGrupoAtual"></select>
											</div>
										</fieldset>
									</div>
									
									<div class = "col_25">
										<fieldset style="height: 64px">
										<label><i18n:message key="citcorpore.comum.unidade" /></label>
											<div>
												<select name='idUnidade' id='idUnidade'
													class="Valid[Required] Description[<i18n:message key='unidade.unidade'/>]"
													onclick="verificaContrato()"></select>

											</div>
										</fieldset>
									</div>	
									
									<div class = "col_25">
										<fieldset style="height: 64px">
										<label><i18n:message key="servico.servico" /></label>
											<div>
												<input type="text" id="addServicoContrato" name="nomeServico" readonly="readonly" />
											</div>
										</fieldset>
									</div>	
									
									<div class="col_25">
										<fieldset  style="height: 64px !important;">
											<label><i18n:message key="tipoServico.tipoServico" /></label>
											<div>
												<select name="idTipoServico"></select>
											</div>
										</fieldset>
									</div>
								<div class = "col_25">
										<fieldset style="height: 64px">
										<label><i18n:message key="citcorpore.comum.situacao" /></label>
											<div>
												<select name="situacao"></select>
											</div>
										</fieldset>
									</div>	
									<div class="col_50">
									 <div class = "col_50">
										<fieldset style="height: 64px">
										<label><i18n:message key="citcorpore.comum.classificacao" /></label>
											<div>
												<select name='idTipoDemandaServico'></select>
											</div>
										</fieldset>
										</div>
										<div class = "col_50">
										<fieldset style="height: 64px">
										<label><i18n:message key="citcorpore.comum.origem" /></label>
											<div>
												<select name='idOrigem' class="Valid[Required] Description[<i18n:message key='origemAtendimento.origem' />]"></select>
											</div>
										</fieldset>
										</div>
									</div>	
									
									</div>
	
								</div>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick='imprimirRelatorioQuantitativo();'
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorioXls' class="light"
											onclick="imprimirRelatorioQuantitativoXls()"
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png">
											<span><i18n:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limpar()' style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
										
									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
		<div id="POPUP_SERVICOCONTRATO" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style='width: 560px !important;height: 560px !important;' >
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section"  >
							<form name='formPesquisaLocalidade' style='width: 530px !important; ' >
								<cit:findField formName='formPesquisaLocalidade' lockupName='LOOKUP_SERVICOCONTRATO' id='LOOKUP_SERVICOCONTRATO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div> 
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>


</html>