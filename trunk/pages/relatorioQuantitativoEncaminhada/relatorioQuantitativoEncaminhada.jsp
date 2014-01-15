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
	addEvent(window, "load", load, false);
	
	function load() {
		
		$("#POPUP_RESPONSAVEL").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});		
		
		
	}
	
	function LOOKUP_RESPONSAVEL_select(id, desc){
		document.getElementById("idResponsavel").value = id;
		document.getElementById("nomeResponsavelAtual").value = desc;
		$("#POPUP_RESPONSAVEL").dialog("close");
	}
	
	function abrePopupResponsavel(){
		$("#POPUP_RESPONSAVEL").dialog("open");
	}
	function imprimirRelatorioQuantitativo(formatoRelatorio){	
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var contrato = document.getElementById("idContrato").value;
		//var grupo = document.getElementById("idGrupoAtual").value;
		/*if(contrato == ""){
			alert(i18n_message("solicitacaoservico.validacao.contrato"));
			return false;	
		}*/
/*		if(grupo == ""){
			alert(i18n_message("grupo.informeGrupo"));
			return false;	
		}*/
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
			}
			
			if (dataFim != ""){
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.validacao.datafim"));
					 document.getElementById("dataFim").value = '';
					return false;					
				}
			}
		}
		else {
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
		}
		if(validaData(dataInicio,dataFim)){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirCargaQuantitativoEncaminhado" + formatoRelatorio);
			
		}
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

	function preencherComboGrupo(){
		document.form.fireEvent("preencherComboGrupo");
	}
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
					<li><a href="#tabs-1"><i18n:message key="relatorioExecucaoSolicitacao.titulo.execucaoSolicitacao" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div class="block" >
						<div id="parametros">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/relatorioQuantitativoEncaminhada/relatorioQuantitativoEncaminhada'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idResponsavel' name='idResponsavel'>
							<div class="columns clearfix">
									<div class="col_20">
										<fieldset style="height: 65px">
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tr>
														<td><input type='text' name='dataInicio'
															id='dataInicio' size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
														<td><i18n:message key="citcorpore.comum.a"/></td>
														<td><input type='text' name='dataFim' id='dataFim'
															size='10' maxlength="10"
															class='Format[Date] Valid[Date] datepicker' /></td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset style="height: 65px">
											<label><i18n:message key="citcorpore.comum.numero"/></label>
											<div>
												<input type="text" id="idSolicitacaoServico" name="idSolicitacaoServico" size="9" maxlength="9" class='Format[Numero]'/>
											</div>
										</fieldset>																				
									</div>
									<div class="col_20">
										<fieldset  style="height: 65px">
											<label><i18n:message key="relatorioSolicitacaoPorExecutante.executadoPor" /> </label>
											<div>
												<input type="text" onfocus='abrePopupResponsavel();' id="nomeResponsavelAtual" name="nomeResponsavelAtual" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset style="height: 65px">
											<label><i18n:message key="contrato.contrato" /></label>
											<div>
												<select name="idContrato" onchange="preencherComboGrupo();"></select>
											</div>
										</fieldset>
									</div>		
									<div class="col_20">
										<fieldset style="height: 64px">
											<label><i18n:message key="citcorpore.comum.grupoaprovador" /></label>
											<div>
												<select name="idGrupoAtual"></select>
											</div>
										</fieldset>
									</div>	
								</div>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick='imprimirRelatorioQuantitativo("PDF");'
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png" style="padding-left: 8px;">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button>
									<button type='button' name='btnRelatorioXls' class="light"
											onclick='imprimirRelatorioQuantitativo("XLS");'
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png" style="padding-left: 8px;">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
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
	<div id="POPUP_RESPONSAVEL" title="<i18n:message key="citcorpore.comum.pesquisaresponsavel" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaResponsavel' style="width: 540px">
							<cit:findField formName='formPesquisaResponsavel' 
								lockupName='LOOKUP_USUARIO' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>