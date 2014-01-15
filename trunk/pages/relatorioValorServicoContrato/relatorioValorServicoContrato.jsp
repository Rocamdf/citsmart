<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
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
		
		
	}

	
	function imprimirRelatorioValorServicoContrato(){
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.datainvalida"));
		 	document.getElementById("dataInicio").value = '';
		 	return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
			 document.getElementById("dataFim").value = '';
			return false;					
		}
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioValorServicoContrato");
			
		}
	}
	
	function imprimirRelatorioValorServicoContratoXls(){	
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.datainvalida"));
		 	document.getElementById("dataInicio").value = '';
		 	return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
			 document.getElementById("dataFim").value = '';
			return false;					
		}
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioValorServicoContratoXls");
		}

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
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}
	
	function limpar(){
		
		document.form.clear();
		
		/*
		* É necessário, já que ao limpar o contrato muda
		* sendo necessário carregar os seus serviços
		*/
		document.form.fireEvent("preencherComboboxServico");
	}	
	
	$(function() {
		$("#idContrato").change(function() {
			document.form.fireEvent("preencherComboboxServico");
		});
	});	

--></script>
</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
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
					<li><a href="#tabs-1"><i18n:message key="relatorioValorServicoContrato.titulo" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" >
						<div id="parametros">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/relatorioValorServicoContrato/relatorioValorServicoContrato'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td><input type='text' name='dataInicio'
															id='dataInicio' size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
														<td><i18n:message key="citcorpore.comum.a" /></td>
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
												<select id="idContrato" name="idContrato"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 64px">
											<label><i18n:message key="servico.servico" /></label>
											<div>
												<select id="idServico" name="idServico"></select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<span class="campoObrigatorio" style="margin: 20px !important;"><i18n:message key="relatorioValorServicoContrato.info" />.</span>
								</div>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light" title='Download documento PDF' 
											onclick='imprimirRelatorioValorServicoContrato();'
											style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
											<span><i18n:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorio' class="light" title='Download documento XLS' 
											onclick='imprimirRelatorioValorServicoContratoXls();'
											style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png"  style="padding-left: 8px;">
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
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>