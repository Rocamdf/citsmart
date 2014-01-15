<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!doctype html public "✰">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" />
</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
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
}

.tableLess {
font-family: arial, helvetica !important;
font-size: 10pt !important;
cursor: default !important;
margin: 0 !important;
background: white !important;
border-spacing: 0 !important;
width: 100% !important;
}

.tableLess tbody {
background: transparent !important;
}

.tableLess * {
margin: 0 !important;
vertical-align: middle !important;
padding: 2px !important;
}

.tableLess thead th {
font-weight: bold !important;
background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom !important;
text-align: center !important;
}

.tableLess tbody tr:ACTIVE {
background-color: #fff !important;
}

.tableLess tbody tr:HOVER {
background-color: #e7e9f9 ;
cursor: pointer;
}

.tableLess th {
border: 1px solid #BBB !important;
padding: 6px !important;
}

.tableLess td{
border: 1px solid #BBB !important;
padding: 6px 10px !important;
}
</style>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}

	}

	$(function() {
		$("#addGrupoExecucao").click(function() {	
			$("#POPUP_GRUPO_EXECUCAO").dialog("open");
		});
	});
	
	$(function() {
		$("#addGrupoRelatorio").click(function() {	
			$("#POPUP_GRUPO_RELATORIO").dialog("open");
		});
	});
	
	$(function() {
		$("#addGrupo").click(function() {	
			$("#POPUP_GRUPO").dialog("open");
		});
	});
	
	function load() {
		
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	
	
		$("#POPUP_GRUPO_EXECUCAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_GRUPO_RELATORIO").dialog({
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
	
	function LOOKUP_GRUPO_RENDIMENTO_EXECUCAO_select(id, desc) {
		document.form.idGrupoExecucao.value = id;
		document.form.fireEvent("restoreNomeGrupoExecucao");
		$("#POPUP_GRUPO_EXECUCAO").dialog("close");
	}
	
	function LOOKUP_GRUPO_RENDIMENTO_select(id, desc) {
		document.form.idGrupo.value = id;
		document.form.fireEvent("restoreNomeGrupo");
		$("#POPUP_GRUPO").dialog("close");
	}
	
	function LOOKUP_GRUPO_RELATORIO_select(id, desc) {
		document.form.idGrupoRelatorio.value = id;
		document.form.fireEvent("restoreNomeGrupoRelatorio");
		$("#POPUP_GRUPO_RELATORIO").dialog("close");
	}


	function filtrarExecucao() {
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var idGrupo = document.getElementById("idGrupoExecucao").value;
		var idPessoa = document.getElementById("comboPessoa").value;
		
		document.getElementById("idPessoa").value = idPessoa;

		if(idGrupo == ""){
			alert(i18n_message("controle.grupoObrigatorio"));
			return false;
		}
		
		if (dataInicio != "") {
			if (DateTimeUtil.isValidDate(dataInicio) == false) {
				alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				document.getElementById("dataInicio").value = '';
				return false;
			}
		} 
		
		if(dataInicio == ""){
			alert(i18n_message("citcorpore.comum.validacao.datainicio"));
			return false;
		}
		if (dataFim != "") {
			if (DateTimeUtil.isValidDate(dataFim) == false) {
				alert(i18n_message("citcorpore.comum.validacao.datafim"));
				document.getElementById("dataFim").value = '';
				return false;
			}
		} 
		
		if(dataFim == ""){
			alert(i18n_message("citcorpore.comum.validacao.datafim"));
			return false;
		}
		
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("carregaTabelasExecucao");
		
	}
	
	function filtrar() {
		var ano = document.getElementById("comboAno").value;
		var mes = document.getElementById("comboMes").value;
		var idGrupo = document.getElementById("idGrupo").value;
		
		document.form.variavelAuxiliarParaFecharMes.value = "true";
		document.form.ano.value = ano;
		document.form.mes.value = mes;
		
		if(idGrupo == ""){	
			alert(i18n_message("controle.grupoObrigatorio"));
			return false;
		}
		
		JANELA_AGUARDE_MENU.show();
 		document.form.fireEvent("carregaTabelas");
			
	}
	
	function fecharMes() {
		var ano = document.getElementById("comboAno").value;
		var mes = document.getElementById("comboMes").value;
		var idGrupo = document.getElementById("idGrupo").value;
		
		document.form.ano.value = ano;
		document.form.mes.value = mes;
		
		if(variavelAuxiliarParaFecharMes.value != "true"){
			alert(i18n_message("controle.pesquiseAntes"));
			return false;
		}
		
		if(idGrupo == ""){	
			alert(i18n_message("controle.grupoObrigatorio"));
			return false;
		}
		
 		document.form.fireEvent("fecharMes");
			
	}
	
	function imprimirRelatorioFuncionarioMaisEficiente(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;
		
		document.form.ano.value = ano;
		document.form.mes.value = mes;
		
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioFuncionarioMaisEficiente");
	}
	
	function imprimirRelatorioFuncionarioMenosEficiente(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;
		
		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioFuncionarioMenosEficiente");
	}
	
	function imprimirRelatorioMelhoresFuncionarios(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;
		
		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioMelhoresFuncionarios");
	}
	
	function imprimirRelatorioPorGrupo(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;
		
		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioPorGrupo");
	}
	
	function imprimirRelatorioPorPessoa(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;
		
		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioPorPessoa");
	}
	
	function imprimirRelatorioMediaAtraso(){
		var ano = document.getElementById("comboAnoRelatorio").value;
		var mes = document.getElementById("comboMesRelatorio").value;
		
		document.form.ano.value = ano;
		document.form.mes.value = mes;

		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("imprimirRelatorioMediaAtraso");
	}
</script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="controle.titulo" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message
								key="controle.titulo" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message
								key="controle.apurarMes" />
					</a></li>
					<li><a href="#tabs-3" class="round_top"><i18n:message
								key="controle.relatorios" />
					</a></li>
					
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/controleRendimentoExecucao/controleRendimentoExecucao.load'>
								<div class="columns clearfix">
									<input type='hidden' name='idGrupo' id='idGrupo' /> 
									<input type='hidden' name='idGrupoExecucao' id='idGrupoExecucao' />
									<input type='hidden' name='idGrupoRelatorio' id='idGrupoRelatorio' />      
									<input type='hidden' name='idPessoa' id='idPessoa' />  
									<input type='hidden' name='ano' id='ano' />  
									<input type='hidden' name='mes' id='mes' /> 
									<input type='hidden' name='qtdSolicitacoes' id='qtdSolicitacoes' />
									<input type='hidden' name='qtdTotalPontos' id='qtdTotalPontos' /> 
									<input type='hidden' name='qtdPontosPositivos' id='qtdPontosPositivos' />  
									<input type='hidden' name='qtdPontosNegativos' id='qtdPontosNegativos' />  
									<input type='hidden' name='mediaRelativa' id='mediaRelativa' />  
									<input type='hidden' name='variavelAuxiliarParaFecharMes' id='variavelAuxiliarParaFecharMes' />      
									
									<div class="col_15">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="controle.pesquisa"/></label>
											<div  >
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
														<td>
															<i18n:message key="citcorpore.comum.a" />
														</td>
														<td>
															<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
														</td>
													</tr>
												</table>												
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="controle.grupo" /></label>
											<div>
												<input type='text' name="nomeGrupoExecucao" id="addGrupoExecucao" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="controle.pessoa" /></label>
											<div>
												<select name="comboPessoa" id="comboPessoa"/></select>
											</div>
										</fieldset>
									</div>
									<br>
								</div>
								<br>
								<br>
								<button type='button' name='btnPesquisar' class="light"  onclick='filtrarExecucao();'>
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><i18n:message key="citcorpore.comum.pesquisar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<div class='col_100'>
										<fieldset>
										<legend><i18n:message key="controle.rendimentoGrupo" /></legend>
											<div class="col_100" id="divrendimentoGrupoExecucao" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoGrupoExecucao" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><i18n:message key="controle.sla" /></th>
														<th style="width: 10%;"><i18n:message key="controle.qtdSolicitacoes" /></th>
														<th style="width: 10%;"><i18n:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.valoresPositivos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.valoresNegativos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.media" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
									</div>
									<div class='col_100' id='divResultadoGrupo' style='font-family: Arial; font-weight: bold'>
									</div>
									<br>
									<br>
								<div class='col_100' id="divTotalRendimentoPessoa">
										<fieldset>
										<legend><i18n:message key="controle.rendimentoPessoa" /></legend>
											<div class="col_100" id="divrendimentoPessoaExecucao" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoPessoaExecucao" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><i18n:message key="citcorpore.comum.nome" /></th>
														<th style="width: 10%;"><i18n:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.aprovacao" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
								</div>							
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="controle.ano" /></label>
											<div>
												<select name="comboAno" id="comboAno"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="controle.mes" /></label>
											<div>
												<select name="comboMes" id="comboMes"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="controle.grupo" /></label>
											<div>
												<input type='text' name="nomeGrupo" id="addGrupo" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
						
						<div class="block">
							<button type='button' name='btnPesquisar' class="light"  onclick='filtrar();'>
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><i18n:message key="citcorpore.comum.pesquisar" /></span>
							</button>
						</div>
						<div class='col_100'>
										<fieldset>
										<legend><i18n:message key="controle.rendimentoGrupo" /></legend>
											<div class="col_100" id="divrendimentoGrupo" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoGrupo" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><i18n:message key="controle.sla" /></th>
														<th style="width: 10%;"><i18n:message key="controle.qtdSolicitacoes" /></th>
														<th style="width: 10%;"><i18n:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.valoresPositivos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.valoresNegativos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.media" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
						</div>
						<div class='col_100' id="divTotalRendimentoPessoa">
										<fieldset>
										<legend><i18n:message key="controle.rendimentoPessoa" /></legend>
											<div class="col_100" id="divrendimentoPessoa" style="height: 200px;width: 65%; overflow: auto">
											<table class="tableLess" id="tblrendimentoPessoa" style="width: 65%">
												<thead>
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 5%;"><i18n:message key="citcorpore.comum.nome" /></th>
														<th style="width: 10%;"><i18n:message key="controle.qtdPontos" /></th>
														<th style="width: 10%;"><i18n:message key="controle.aprovacao" /></th>
													</tr>
												</thead>
											</table>
											</div>
									</fieldset>
						</div>
						<div class="block">
							<button type='button' name='btnFecharMes' class="light"  onclick='fecharMes();'>
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/note_book.png">
											<span><i18n:message key="controle.fecharMes" /></span>
							</button>
						</div>
						</div>	
					</div>
					<div id="tabs-3" class="block">
						<div class="section">
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="controle.ano" /></label>
											<div>
												<select name="comboAnoRelatorio" id="comboAnoRelatorio"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="controle.mes" /></label>
											<div>
												<select name="comboMesRelatorio" id="comboMesRelatorio"/></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="controle.grupo" /></label>
											<div>
												<input type='text' name="nomeGrupoRelatorio" id="addGrupoRelatorio" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
						<div class="block">&nbsp;<h3 ><i18n:message key="controle.relatorios" /></h3></div>
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="controle.melhorFuncionario" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioFuncionarioMaisEficiente()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="controle.piorFuncionario" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioFuncionarioMenosEficiente()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="controle.melhoresFuncionarios" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioMelhoresFuncionarios()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="block"></div>
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="controle.rendimentoGrupo" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioPorGrupo()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="controle.rendimentoPessoa" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioPorPessoa()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="controle.mediaAtrasoEquipe" /></label>
								<button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioMediaAtraso()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
								</button >
							</fieldset>
						</div>
						</div>
						</div>	
						
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_GRUPO_EXECUCAO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
						<div class="section">
							<form name='formPesquisaGrupo1' style="width: 540px">
								<cit:findField formName='formPesquisaGrupo1' 
									lockupName='LOOKUP_GRUPO_RENDIMENTO_EXECUCAO' id='LOOKUP_GRUPO_RENDIMENTO_EXECUCAO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
				</div>
			</div>
		</div>
		<div id="POPUP_GRUPO_RELATORIO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
						<div class="section">
							<form name='formPesquisaGrupo3' style="width: 540px">
								<cit:findField formName='formPesquisaGrupo3' 
									lockupName='LOOKUP_GRUPO_RELATORIO' id='LOOKUP_GRUPO_RELATORIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />  
							</form>
						</div>
				</div>
			</div>
		</div>
		<div id="POPUP_GRUPO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
						<div class="section">
							<form name='formPesquisaGrupo2' style="width: 540px">
								<cit:findField formName='formPesquisaGrupo2' 
									lockupName='LOOKUP_GRUPO_RENDIMENTO' id='LOOKUP_GRUPO_RENDIMENTO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
				</div>
			</div>
		</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
