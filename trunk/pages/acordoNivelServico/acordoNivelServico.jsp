<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<!doctype html public "">
<html>
	<head>
	<%
	    response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", -1);
	    String iframe = "";
	    iframe = request.getParameter("iframe");
	%>
	
	<title><i18n:message key="citcorpore.comum.title" /></title>
	
	<%@include file="/include/security/security.jsp"%>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	
	<script charset="ISO-8859-1"  type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/SLA/RequisitoSLA.js"></script>
	<script charset="ISO-8859-1"  type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/SLA/RevisarSLA.js"></script>
	<script charset="ISO-8859-1"  type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/SLA/PrioridadeSLA.js"></script>
	
	<script charset="ISO-8859-1"  type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	
	<script type="text/javascript" src="../../cit/objects/PrioridadeAcordoNivelServicoDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/PrioridadeServicoUsuarioDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/SlaRequisitoSlaDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/RevisarSlaDTO.js"></script>
	
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
		
		.formRelacionamentos div{
			float: left; 
			width: 99%; 
			border: 1px solid #ccc; 
			padding: 5px;
		}
		
		.ui-tabs .ui-tabs-nav li a{
			background-color: #fff !important;
		}	
			
		.ui-state-active{
			background-color: #aaa ;
		}		
		
		#tabs div{
			background-color: #fff;
		}
		
		div#main_container {
			margin: 10px 10px 10px 10px;
		}
			
	</style>
	
	<script type="text/javascript">
		var popup;
		addEvent(window, "load", load, false);
		function load() {
			popup = new PopupManager(800, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
			$("#POPUP_UNIDADE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			$("#POPUP_USUARIO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			$("#POPUP_REQUISITOSLA").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
						
		}
		$(function () {
			$( "#tabs" ).tabs();
			
			$("#addUnidade").click(function() {
				$("#POPUP_UNIDADE").dialog("open");
			});
			
			$("#addUsuario").click(function() {
				$("#POPUP_USUARIO").dialog("open");
			});
			
			$("#addRequisitoSLA").click(function() {
				$("#POPUP_REQUISITOSLA").dialog("open");
			});
			
		});
		
		/**
		* @author rodrigo.oliveira
		*/
		function validaData(dataInicio, dataFim, avaliarEm) {
			
			var dtInicio = new Date();
			var dtFim = new Date();
			var avEm = new Date();
			
			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
			avEm.setTime(Date.parse(avaliarEm.split("/").reverse().join("/"))).setFullYear;
						
			if (dtInicio > avEm){
				alert(i18n_message("solicitacaoservico.validacao.dataavaliarincorreta"));
				return false;
			}else if (dtInicio > dtFim){
				alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
				return false;
			}else
				return true;
		}
		
		function abreFechaMaisMenos(obj,idObj){
			var n = obj.src.indexOf('<%=Constantes.getValue("SERVER_ADDRESS")%><%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/mais.jpg');
			if (n > -1){
				document.getElementById(idObj).style.display='block';
				document.getElementById('img_' + idObj).src = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/menos.jpg';
			}else{
				document.getElementById(idObj).style.display='none';
				document.getElementById('img_' + idObj).src = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/mais.jpg';
			}
		}	
		
		function LOOKUP_ACORDONIVELSERVICOGLOBAL_select(id, desc) {
			JANELA_AGUARDE_MENU.show();
			document.form.restore({idAcordoNivelServico:id});
		}
				
		function LOOKUP_UNIDADE_select(id, desc) {
			
			var table = document.getElementById('tabelaPrioridadeUnidade');
			var tableSize = table.rows.length;
			var contadorAux = 0;
			if(tableSize >= 2){
				for (var i = 1; i < tableSize; i++) {
					var trObj = document.getElementById('idUnidade' + i);
					if (!trObj) {
						continue;
					}					
					if(trObj.value == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return;
					}
				}
			}
			
			var valor = desc.split(' - ');
			
			document.form.idUnidadePrioridade.value = id;
			document.form.addUnidade.value = valor[0];
			
			$("#POPUP_UNIDADE").dialog("close");
			
		}
		
		function LOOKUP_SOLICITANTE_select(id, desc) {
			
			var table = document.getElementById('tabelaPrioridadeUsuario');
			var tableSize = table.rows.length;
			var contadorAux = 0;
			if(tableSize >= 2){
				for (var i = 1; i < tableSize; i++) {
					var trObj = document.getElementById('idUsuario' + i);
					if (!trObj) {
						continue;
					}					
					if(trObj.value == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return;
					}
				}
			}
			
			var valor = desc.split(' - ');
			
			document.form.idUsuarioPrioridade.value = id;
			document.form.addUsuario.value = valor[0];
			
			$("#POPUP_USUARIO").dialog("close");
			
		}
		
		function LOOKUP_REQUISITOSLA_select(id, desc) {
			
			var table = document.getElementById('tabelaRequisitoSLA');
			var tableSize = table.rows.length;
			var contadorAux = 0;
			if(tableSize >= 2){
				for (var i = 1; i < tableSize; i++) {
					var trObj = document.getElementById('idRequisitoSLA' + i);
					if (!trObj) {
						continue;
					}					
					if(trObj.value == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return;
					}
				}
			}
			
			var valor = desc.split(' - ');
			
			document.form.idRequisitoSLAVinc.value = id;
			document.form.addRequisitoSLA.value = valor[0];
			
			$("#POPUP_REQUISITOSLA").dialog("close");
			
		}
		
		function avaliaTipoSLA(){
			document.getElementById('divByDisponibilidade').style.display = 'none';
			document.getElementById('divByTempos').style.display = 'none';
			document.getElementById('divByDiversos').style.display = 'none';
			
			var value = document.getElementById("tipo").value;
			
			if (value == 'D'){
				document.getElementById('divByDisponibilidade').style.display = 'block';
			}
			if (value == 'T'){
				document.getElementById('divByTempos').style.display = 'block';
			}
			if (value == 'V'){
				document.getElementById('divByDiversos').style.display = 'block';
			}
			
		}
		
		function addUnidadeRow(){
			
			var idUnidade = document.getElementById("idUnidadePrioridade").value;
			var unidadeNome = document.getElementById("addUnidade").value;
			var prioridadeValor = document.getElementById("prioridadeUnidade").value;
			
			if(unidadeNome == '' || prioridadeValor == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRowUnidade(idUnidade, unidadeNome, prioridadeValor);
			}
			
		}
				
		function addUsuarioRow(){
			
			var idUsuario = document.getElementById("idUsuarioPrioridade").value;
			var usuarioNome = document.getElementById("addUsuario").value;
			var prioridadeValor = document.getElementById("prioridadeUsuario").value;
			
			if(usuarioNome == '' || prioridadeValor == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRowUsuario(idUsuario, usuarioNome, prioridadeValor);
			}
			
		}
		
		function addRequisitoSLARow(){
			
			var idRequisitoSLA = document.getElementById("idRequisitoSLAVinc").value;
			var assuntoRequisito = document.getElementById("addRequisitoSLA").value;
			var dataVinculacao = document.getElementById("dataVinculacaoSLA").value;
			
			if(assuntoRequisito == '' || dataVinculacao == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRowRequisitoSLA(idRequisitoSLA, assuntoRequisito, dataVinculacao);
			}
			
		}
		
		function addRevisarSLARow(){
			
			var dataRevisar = document.getElementById("dataRevisarSLA").value;
			var detalhes = document.getElementById("detalhesSLA").value;
			var observacao = document.getElementById("observacaoSLA").value;
			
			if(dataRevisar == '' || detalhes == ''){
				alert(i18n_message("acordoNivelServico.informarCampos"));
			}else{
				insereRevisarSLARow(dataRevisar, detalhes, observacao);
			}
			
		}
		
		function gravar(){
			
			var value = document.getElementById("tipo").value;
			if(value == ''){
				alert(i18n_message("acordoNivelServico.tipoAcordoSelecione"));
				return;
			}
			
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			var avaliarEm = document.getElementById("avaliarEm").value;
			
			if(!validaData(dataInicio, dataFim, avaliarEm)){
				return;
			}
			
			limpaCamposTelaNaoUsada();
			
			aguarde();
			serializaPrioridadeUnidade();
			serializaPrioridadeUsuario();
			serializaRequisitoSLA();
			serializaRevisarSLA();
			document.form.save();
			fechar_aguarde();
		}
		
		function excluir(){
			if(document.getElementById("idAcordoNivelServico").value != ""){
				if (confirm('<%=UtilI18N.internacionaliza(request, "dinamicview.confirmaexclusao")%>')){
					document.form.fireEvent('excluir');
				}
			}
		}
		
		function limpar(){
			document.form.fireEvent("limpar");
			document.form.clear();
			deleteAllRowsPrioridadeUnidade();
			deleteAllRowsPrioridadeUsuario();
			deleteAllRowsRequisitoSLA();
			deleteAllRowsRevisarSLA();
		}
		
		function limpaCamposTelaNaoUsada(){
			
			var tipo = document.getElementById("tipo").value;
			
			if(tipo == "D"){
				deleteAllRowsPrioridadeUnidade();
				deleteAllRowsPrioridadeUsuario();
				document.getElementById("IDPRIORIDADEAUTO1").value = "";
				document.getElementById("IDGRUPO1").value = "";
				document.getElementById("TEMPOAUTO").value = "";
				document.getElementById("valorLimite").value = "";
				document.getElementById("unidadeValorLimite").value = "";
				document.getElementById("detalheGlosa").value = "";
				document.getElementById("detalheLimiteGlosa").value = "";
				zeraValores();
			}
			
			if(tipo == "T"){
				document.getElementById("disponibilidade").value = "";
				document.getElementById("valorLimite").value = "";
				document.getElementById("unidadeValorLimite").value = "";
				document.getElementById("detalheGlosa").value = "";
				document.getElementById("detalheLimiteGlosa").value = "";
			}
			
			if(tipo == "V"){
				deleteAllRowsPrioridadeUnidade();
				deleteAllRowsPrioridadeUsuario();
				document.getElementById("disponibilidade").value = "";
				document.getElementById("IDPRIORIDADEAUTO1").value = "";
				document.getElementById("IDGRUPO1").value = "";
				document.getElementById("TEMPOAUTO").value = "";
				zeraValores();
			}
		}
		
		function zeraValores(){
			for(var i = 1; i <= 5; i++){
				document.getElementById("HH-1-" + i).value = "0";
				document.getElementById("HH-2-" + i).value = "0";
				document.getElementById("MM-1-" + i).value = "0";
				document.getElementById("MM-2-" + i).value = "0";
			}
		}
		
		function aguarde(){
			JANELA_AGUARDE_MENU.show();
		}
		
		function fechar_aguarde(){
	    	JANELA_AGUARDE_MENU.hide();
		}
		
	</script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<!-- Conteudo -->
		<div id="wrapper" class="wrapper">		 
			<div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<i18n:message key="acordoNivelServico.AcordoNivelServicoGeral" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="acordoNivelServico.cadastroAcordoNivelServicoGeral" /></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="acordoNivelServico.pesquisaAcordoNivelServicoGeral" /></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="block">
							<div class="section">
								<form name="form" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/acordoNivelServico/acordoNivelServico">
									<input type='hidden' name='idAcordoNivelServico' id='idAcordoNivelServico'/>
									<div class="columns clearfix">
										<div class="col_60">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.tituloAcordo" />
												</label>
												<div>
													<input type='text' name="tituloSLA" maxlength="500" class="Valid[Required] Description[visao.tituloAcordo]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.tipoAcordo" />
												</label>
												<div style="height: 35px;">
													<select id="tipo" onchange="avaliaTipoSLA()" name="tipo" class="noClearCITAjax">
														<option value=""><i18n:message key="citcorpore.comum.selecione"/></option>
														<option value="D"><i18n:message key="requisitosla.disponibilidade"/></option>
														<option value="T"><i18n:message key="requisitosla.tempo_fases"/></option>
														<option value="V"><i18n:message key="requisitosla.informacoes_outras_fontes"/></option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.impacto" />
												</label>
												<div style="height: 35px;">
													<select id="impacto" name="impacto" class="noClearCITAjax">
														<option value="A"><i18n:message key="requisitosla.alto"/></option>
														<option value="M"><i18n:message key="requisitosla.medio"/></option>
														<option value="B"><i18n:message key="requisitosla.baixo"/></option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.urgencia" />
												</label>
												<div style="height: 35px;">
													<select id="urgencia" name="urgencia" class="noClearCITAjax">
														<option value="A"><i18n:message key="requisitosla.alta"/></option>
														<option value="M"><i18n:message key="requisitosla.media"/></option>
														<option value="B"><i18n:message key="requisitosla.baixa"/></option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.permiteMudImpUrg" />
												</label>
												<div style="height: 35px;">
													<input type="radio" id="pertmiteMudarImpUrgN" name="permiteMudarImpUrg" checked="checked" value="N" style="margin-top: 10px" /><i18n:message key="requisitosla.nao" />
													<input type="radio" id="pertmiteMudarImpUrgS" name="permiteMudarImpUrg" value="S" style="margin-left: 30px"/><i18n:message key="requisitosla.sim" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.situacao" />
												</label>
												<div style="height: 35px;">
													<input type="radio" id="situacaoAtivo" name="situacao" checked="checked" value="A" style="margin-top: 10px" /><i18n:message key="requisitosla.ativo" />
													<input type="radio" id="situacaoInativo" name="situacao" value="I" style="margin-left: 30px"/><i18n:message key="requisitosla.inativo" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.descricaoAcordo" />
												</label>
												<div>
													<textarea id="descricaoSLA" name="descricaoSLA" maxlength="2000" cols="50" rows="5" class="Valid[Required] Description[visao.descricaoAcordo]"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="visao.escopoAcordo" />
												</label>
												<div>
													<textarea id="escopoSLA" name="escopoSLA" maxlength="2000" cols="50" rows="5" class="Description[visao.escopoAcordo]"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="visao.dataDeInicio"/>
												</label>
												<div>
													<input type='text' id="dataInicio" name="dataInicio" maxlength="10" size="10" class="Valid[Data, Required] Description[visao.dataDeInicio] Format[Data] datepicker" />						
												</div>				
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.comum.datafim"/>
												</label>
												<div>
													<input type='text' id="dataFim" name="dataFim" maxlength="10" size="10" class="Valid[Data] Description[citcorpore.comum.datafim] Format[Data] datepicker" />						
												</div>				
											</fieldset>
										</div>
										<div class="col_20">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="sla.avaliarem"/>
												</label>
												<div>
													<input type='text' id="avaliarEm" name="avaliarEm" maxlength="10" size="10" class="Valid[Data, Required] Description[sla.avaliarem] Format[Data] datepicker" />						
												</div>				
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="visao.contatos" />
												</label>
												<div>
													<textarea id="contatos" name="contatos" maxlength="2000" cols="50" rows="5" class="Description[visao.contatos]"></textarea>
												</div>
											</fieldset>
										</div>
									</div>
									
									<!-- Início da parte dinâmica -->
									<div class="col_100">
										<div id="divByDisponibilidade" style="display: none;" >
											<div class="col_100" style="padding-bottom: 10px;">
												<b><i18n:message key="sla.acordoservicodisponibilidade" /></b>
											</div>
											<div class="col_20">
												<fieldset>
													<label style="margin-bottom: 3px; margin-top: 5px;">
														<i18n:message key="sla.indicedisponibilidade" />
													</label>
													<div style="float: left; width: 65px; padding-right: 10px;">
														<input type="text" name="disponibilidade" size="5" maxlength="5" class="Format[Money]"/>
													</div>
													<div style="padding-top: 10px;">%</div>
												</fieldset>
											</div>
										</div>
										<div id="divByTempos" style="display: none; padding-bottom: 145px;">
											<br>
											<b><i18n:message key="sla.alvostempo"/></b><br>
											<table border="1" width="100%" style="margin-top: 5px;">
												<tr style='color: white;'>
													<td>
														&nbsp;
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 1 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 2 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 3 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 4 ---</b>
													</td>
													<td colspan='2' style='text-align: center; background-color: gray; border:1px solid black;'>
														<b>--- 5 ---</b>
													</td>												
												</tr>
												<tr>
													<td>
														<i18n:message key="sla.captura"/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-1-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-1-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>												
												</tr>
												<tr>
													<td>
														<i18n:message key="sla.resolucao"/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-1' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-2' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-3' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-4' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='HH-2-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>
													<td>
														<input type='text' value="0" name='MM-2-5' size='2' maxlength="2" class='Format[Numero] text'/>
													</td>												
												</tr>				
											</table>
											<br>
											<b><i18n:message key="sla.automacao"/></b><br>
											<fieldset style="padding-bottom: 10px; padding-top: 10px;">
												<table border="1" width="100%">
													<tr>
														<td style="width: 100px; padding-bottom: 25px;">
															<i18n:message key="sla.actiontime"/>
														</td>
														<td>
															<div id="divTempoAuto"></div>
														</td>			
													</tr>
													<tr>
														<td colspan="2">
																<label style="font-weight: bold;"><i18n:message key="sla.action"/></label>
																<table>
																	<tr>
																		<td>
																			<i18n:message key="sla.prioridade"/>
																		</td>
																		<td>
																			<select name="IDPRIORIDADEAUTO1" id="IDPRIORIDADEAUTO1"></select>
																		</td>
																		<td>
																			<i18n:message key="sla.grupo"/>
																		</td>
																		<td>
																			<select name="IDGRUPO1" id="IDGRUPO1"></select>
																		</td>
																		
																		<td>
																			<i18n:message key="acordoNivelServico.modeloEmail"/>
																		</td>
																		<td>
																			<select name="idEmail" id="idEmail"></select>
																		</td>												
																	</tr>
																</table>
														</td>			
													</tr>
												</table>
											</fieldset>
											<div id="gridPrioridadeUnidade" class="col_50">
												<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
													<b><i18n:message key="acordoNivelServico.prioridadeUnidade" /></b>
												</div>
												<div class="col_100">
													 <fieldset > 
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><i18n:message key="acordoNivelServico.buscaUnidade" />*</label>
														<div style="float: left; width: 330px; padding-left: 0px;">
															<input type='hidden' id="idUnidadePrioridade" name='idUnidadePrioridade'/>
															<input id="addUnidade" type='text' readonly="readonly" name="addUnidade" maxlength="80" />
														</div>
														<button type="button" style="margin-top: 1px!important;" onclick="$('#addUnidade').val('')" class='light icon_only text_only'><i18n:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>
														</fieldset>
														</div>
														<div class="col_100">
														 <fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;"> 
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><i18n:message key="prioridade.prioridade" />*</label>
														<div style="float: left; width: 50px; padding-left: 0px;">
															<div style="height: 15px;">
																<select id="prioridadeUnidade" name="prioridadeUnidade" class="noClearCITAjax">
																	<option value="">--</option>
																	<option value="1">1</option>
																	<option value="2">2</option>
																	<option value="3">3</option>
																	<option value="4">4</option>
																	<option value="5">5</option>
																</select>
															</div>
														</div>
														<img title="Adicionar Prioridade" src="/citsmart/imagens/add.png" onclick="addUnidadeRow();" border="0" style="cursor:pointer; padding-top: 10px;">
													 </fieldset>
												</div>
												<input type='hidden' id="prioridadeUnidadeSerializados" name='prioridadeUnidadeSerializados'/>
												<table class="table" id="tabelaPrioridadeUnidade" style="width: 500px; margin-left: 15px;">
													<tr>
														<th style="width: 16px !important;"></th>
														<th style="width: 70%;"><i18n:message key="citcorpore.comum.unidade" /></th>
														<th style="width: 20%;"><i18n:message key="prioridade.prioridade" /></th>
													</tr>
												</table>
											</div>
											<div id="gridPrioridadeUsuario" class="col_50">
												<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
													<b><i18n:message key="acordoNivelServico.prioridadeUsuario" /></b>
												</div>
												<div class="col_100" style="">
													<fieldset>
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><i18n:message key="acordoNivelServico.buscaUsuario" />*</label>
														<div style="float: left; width: 300px; padding-left: 0px;">
															<input type='hidden' id="idUsuarioPrioridade" name='idUsuarioPrioridade'/> 
															<input id="addUsuario" type='text' readonly="readonly" name="addUsuario" maxlength="80" />
														</div>
														<button type="button" style="margin-top: 1px!important;" onclick="$('#addUsuario').val('')" class='light icon_only text_only'><i18n:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>
														</fieldset>
														</div>
														<div class="col_100" style="">
														<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px; ">
														<label style="margin-bottom: 3px; margin-top: 10px; float: left;"><i18n:message key="prioridade.prioridade" />*</label>
														<div style="float: left; width: 50px; padding-left: 0px;">
															<div style="height: 15px;">
																<select id="prioridadeUsuario" name="prioridadeUsuario" class="noClearCITAjax">
																	<option value="">--</option>
																	<option value="1">1</option>
																	<option value="2">2</option>
																	<option value="3">3</option>
																	<option value="4">4</option>
																	<option value="5">5</option>
																</select>
															</div>
														</div>
														<img title="Adicionar Prioridade" src="/citsmart/imagens/add.png" onclick="addUsuarioRow();" border="0" style="cursor:pointer; padding-top: 10px;">
													</fieldset>
												</div>
												<input type='hidden' id="prioridadeUsuarioSerializados" name='prioridadeUsuarioSerializados'/>
												<table class="table" id="tabelaPrioridadeUsuario" style="width: 500px; margin-left: 15px;">
													<tr>
														<th style="width: 16px !important;"></th>
														<th style="width: 70%;"><i18n:message key="citcorpore.comum.usuario" /></th>
														<th style="width: 20%;"><i18n:message key="prioridade.prioridade" /></th>
													</tr>
												</table>
											</div>
										</div>
										
										<div id="divByDiversos" style="display: none;">
											<div class="col_100" style="padding-bottom: 10px;">
												<b><i18n:message key="sla.acordoservico" /></b>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<i18n:message key="visao.valorLimite" />
													</label>
													<div style="float: left; width: 150px;">
														<input type="text" name="valorLimite" size="15" maxlength="15" class="Format[Money]"/>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<i18n:message key="sla.unidade" />
													</label>
													<div style="float: left; width: 200px;">
														<input type="text" name="unidadeValorLimite" size="150" maxlength="150"/>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<i18n:message key="visao.glosa" />
													</label>
													<div>
														<textarea name="detalheGlosa" maxlength="2000" cols="50" rows="5" class="Description[visao.glosa]"></textarea>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label style="margin-bottom: 3px;">
														<i18n:message key="visao.limiteGlosa" />
													</label>
													<div>
														<textarea name="detalheLimiteGlosa" maxlength="2000" cols="50" rows="5" class="Description[visao.limiteGlosa]"></textarea>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<!-- Fim da parte dinâmica -->
									
									<div class="col_100" style="padding-top: 30px; padding-bottom: 30px;">
										<button type="button" name="btnGravar" class="light" onclick="gravar();">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
											<span><i18n:message key="citcorpore.comum.gravar"/></span>
										</button>
										<button type="button" name="btnLimpar" class="light" onclick="limpar();">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar"/></span>
										</button>
										<button type="button" name="btnUpDate" class="light" onclick="excluir();">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
											<span><i18n:message key="citcorpore.comum.excluir" />
											</span>
										</button>
									</div>
									
									<!-- Início das abas inferiores de Relacionamentos -->
									<div  id="abas" class="formRelacionamentos">
										<div id="tabs" class="block" style="padding-bottom: 50px;">
											<ul class="tab_header clearfix">
												<li>
													<a href="#relacionarRequisitoSla"><i18n:message key="slarequisitosla.titulo"/></a>
												</li>
												<li>
													<a href="#relacionarContratoVincCliente"><i18n:message key="sla.listacontratosvinculadoscliente"/></a>
												</li>
												<li>
													<a href="#relacionarContratoVincAno"><i18n:message key="sla.listacontratosvinculadosano"/></a>
												</li>
												<li>
													<a href="#relacionarContratoVincTerceiro"><i18n:message key="sla.listacontratosvinculadosterceiro"/></a>
												</li>
												<li>
													<a href="#relacionarHistoricoAuditoria"><i18n:message key="sla.historicoauditoria"/></a>
												</li>
												<li>
													<a href="#relacionarRevisarSla"><i18n:message key="sla.revisar.revisar"/></a>
												</li>
											</ul>
											<div id="relacionarRequisitoSla">
												<div id="contentrequisitoSla">
													<input type='hidden' id="requisitoSlaSerializados" name='requisitoSlaSerializados'/>
													<div class="col_20" style="border: none; width: 20%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="slarequisitosla.datavinculacao" />*
															</label> 
															<input type='text' id="dataVinculacaoSLA" name="dataVinculacaoSLA" maxlength="10" size="10" class="Valid[Data] Description[slarequisitosla.datavinculacao] Format[Data] datepicker" />
														</fieldset>
													</div>
													<div class="col_20" style="border: none; width: 20%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="slarequisitosla.reqslr" />*
															</label>
															<input type='hidden' id="idRequisitoSLAVinc" name='idRequisitoSLAVinc' /> <input id="addRequisitoSLA" type='text' readonly="readonly" name="addRequisitoSLA" maxlength="80" />
														</fieldset>
													</div>
													<div class="col_15" style="border: none; width: 5%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<div style="border: none; padding-top: 25px; padding-left: 5px;">
																<img title="Adicionar Requisito SLA" src="/citsmart/imagens/add.png" onclick="addRequisitoSLARow();" border="0" style="cursor: pointer">
															</div>
														</fieldset>
													</div>
													<div class="col_50" style="border: none; width: 50%; float: left; padding-left: 50px;">
														<fieldset style="border: none;">
															<table class="table" id="tabelaRequisitoSLA" style="width: 500px; margin-top: 5px;">
																<tr>
																	<th style="width: 16px !important;"></th>
																	<th style="width: 30%;"><i18n:message key="slarequisitosla.datavinculacao" /></th>
																	<th style="width: 60%;"><i18n:message key="slarequisitosla.reqslr" /></th>
																</tr>
															</table>
														</fieldset>
													</div>
												</div>				
												<label style="color: red;"><i18n:message key="acordoNivelServico.avisoSalvar"/></label>
											</div>				
											<div id="relacionarContratoVincCliente">
												<div style="border: none !important;" id="contratoVincCliente"></div>
											</div>
											<div id="relacionarContratoVincAno">
												<div style="border: none !important;" id="contratoVincAno"></div>
											</div>
											<div id="relacionarContratoVincTerceiro">
												<div style="border: none !important;" id="contratoVincTerceiro"></div>
											</div>
											<div id="relacionarHistoricoAuditoria">
												<div style="border: none !important;" id="historicoAuditoria"></div>
											</div>
											<div id="relacionarRevisarSla">
												<div id="contentrevisarSla">
													<input type='hidden' id="revisarSlaSerializados" name='revisarSlaSerializados'/>
													<div class="col_20" style="border: none; width: 13%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 0px; font-weight: bolder;">
																<i18n:message key="sla.revisar.data"/>*
															</label>
															<input type='text' id="dataRevisarSLA" name="dataRevisarSLA" maxlength="10" size="10" class="Valid[Data] Description[sla.revisar.data] Format[Data] datepicker" />
														</fieldset>
													</div>
													<div class="col_20" style="border: none; width: 15%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="sla.revisar.detalhes"/>*
															</label>
															<textarea id="detalhesSLA" name="detalhesSLA" maxlength="2000" cols="50" rows="3" class="Description[sla.revisar.detalhes]"></textarea>
														</fieldset>
													</div>
													<div class="col_20" style="border: none; width: 15%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="sla.revisar.observacao"/>
															</label>
															<textarea id="observacaoSLA" name="observacaoSLA" maxlength="200" cols="50" rows="3" class="Description[sla.revisar.observacao]"></textarea>
														</fieldset>
													</div>
													<div class="col_15" style="border: none; width: 3%; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<div style="border: none; padding-top: 30px; padding-left: 20px;">
																<img title="Adicionar Revisar SLA" src="/citsmart/imagens/add.png" onclick="addRevisarSLARow();" border="0" style="cursor:pointer">
															</div>
														</fieldset>
													</div>
													<div class="col_50" style="border: none; width: 45%; float: left; padding-left: 50px;">
														<fieldset style="border: none;">
															<table class="table" id="tabelaRevisarSLA" style="width: 750px; margin-top: 5px;">
																<tr>
																	<th style="width: 10px !important;"></th>
																	<th style="width: 15%;"><i18n:message key="sla.revisar.data" /></th>
																	<th style="width: 45%;"><i18n:message key="sla.revisar.detalhes" /></th>
																	<th style="width: 35%;"><i18n:message key="sla.revisar.observacao" /></th>
																</tr>
															</table>
														</fieldset>
													</div>
												</div>
												<label style="color: red;"><i18n:message key="acordoNivelServico.avisoSalvar"/></label>
											</div>				
										</div>
									</div>
									<!-- Fim das abas inferiores de Relacionamentos -->
								</form>
							</div>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName="formPesquisa" lockupName="LOOKUP_ACORDONIVELSERVICOGLOBAL" id="LOOKUP_ACORDONIVELSERVICOGLOBAL" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<div id="POPUP_UNIDADE" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 560px !important;height: 560px !important;" >
				<div class="toggle_container">
					<div class="block">
						<div class="section"  >
							<form name="formPesquisaUnidade" style="width: 530px !important;" >
								<cit:findField formName="formPesquisaUnidade" lockupName="LOOKUP_UNIDADE" id="LOOKUP_UNIDADE" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
				</div> 
			</div>
		</div>
		<div id="POPUP_USUARIO" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 560px !important;height: 560px !important;" >
				<div class="toggle_container">
					<div class="block">
						<div class="section">
							<form name="formPesquisaUsuario" style="width: 530px !important;" >
								<cit:findField formName="formPesquisaUsuario" lockupName="LOOKUP_SOLICITANTE" id="LOOKUP_SOLICITANTE" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
							</form>
						</div>
					</div>
				</div> 
			</div>
		</div>
		<div id="POPUP_REQUISITOSLA" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 560px !important;height: 560px !important;" >
				<div class="toggle_container">
					<div class="block">
						<div class="section">
							<form name="formRequisitoSLA" style="width: 530px !important;" >
								<cit:findField formName="formRequisitoSLA" lockupName="LOOKUP_REQUISITOSLA" id="LOOKUP_REQUISITOSLA" top="0" left="0" len="550" heigth="400" javascriptCode="true" htmlCode="true" />
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
</html>
