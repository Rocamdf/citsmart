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
    
    String iframe = "";
    iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
<script type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/painel/jquery.ui.datepicker.js"></script>
<script src="/citsmart/js/jquery.ui.datepicker-pt-BR.js"></script>

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
		border:1px solid #ddd !important;
		padding:4px 10px !important;
		border-top:none !important;
		border-left:none !important;
	}
<%if (iframe != null) {%>

	div#main_container {
		margin: 10px 10px 10px 10px;  
	
	}
<%}%>
	
	
</style>

<%-- <%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;  
	
}
</style>

<%}%>
 --%>


<script><!--
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_SOLICITANTE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
		
		$("#POPUP_RESPONSAVEL").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});		

		$("#POPUP_ITEMCONFIG").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_OCORRENCIAS").dialog({
			autoOpen : false,
			width : 800,
			height : 600,	
			modal : true
		});
		
		$("#POPUP_UNIDADE").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		$("#POPUP_menuAnexos").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		$("#POPUP_EMPREGADO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		
		
	}
	
	function LOOKUP_UNIDADE_SOLICITACAO_select(id, desc) {
		document.form.idUnidade.value = id;
		document.form.nomeUnidade.value = desc.split(" - ")[0];
		$("#POPUP_UNIDADE").dialog("close");		
	}
	
	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
		document.getElementById("idItemConfiguracao").value = id;
		document.getElementById("nomeItemConfiguracao").value = desc;
		$("#POPUP_ITEMCONFIG").dialog("close");		
	}

	function abrePopupIC(){
		$("#POPUP_ITEMCONFIG").dialog("open");
	}
	function inicializarTemporizador(){
		if(temporizador == null){
			temporizador = new Temporizador("imgAtivaTimer");
		} else {
			temporizador = null;
			try{
				temporizador.listaTimersAtivos = [];
			}catch(e){}
			try{
				temporizador.listaTimersAtivos.length = 0;
			}catch(e){}
			temporizador = new Temporizador("imgAtivaTimer");
		}
	}
			
	function pesquisaRequisicaoMudanca(){	
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var numero = document.getElementById("idRequisicaoMudanca").value;
		if(numero != "") {
			if (dataInicio != ""){
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
					return false;	
				}
			
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
			inicializarTemporizador();
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("pesquisaRequisicaoMudanca");
		}
	}
	
	function imprimirRelatorioRequisicaoMudanca(){	
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
			document.form.formatoArquivoRelatorio.value = 'pdf';
			document.form.fireEvent("imprimirRelatorioRequisicaoMudanca");
			
		}
	}
	
	function imprimirRelatorioRequisicaoMudancaXls(){	
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
			document.form.formatoArquivoRelatorio.value = 'xls';
			document.form.fireEvent("imprimirRelatorioRequisicaoMudanca");
			
		}
	}
	
	
	function limpar(){
		$('#divTblRequisicaoMudanca').hide();
		document.form.clear();
		
		
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
			alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
			return false;
		}else
			return true;
	}
	
	function pageLoad()
	{
		$(function() 
		{
			$('input.datepicker').datepicker();
		});
	}
	
//	popup para pesquisar de unidade
	
	 $(function() {
		$("#addUnidade").click(function() {
			$("#POPUP_UNIDADE").dialog("open");
		});
	}); 
	
	
	 /*
	 	 * Reaproveitamento da lookup EMPREGADO
	 	 */	 	 
		function selecionarSolicitante(){
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idSolicitante.value = id;
				document.form.nomeSolicitante.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}
					
			$("#POPUP_EMPREGADO").dialog("open");
		}	

		function selecionarProprietario(){
			limpar_LOOKUP_EMPREGADO();
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idProprietario.value = id;
				document.form.nomeProprietario.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}
			
			$("#POPUP_EMPREGADO").dialog("open");			
		}
		
	    function mostrarCategoria(){ 
	    	
	    	document.form.fireEvent('validacaoCategoriaMudanca');
	    	
        }  
	    


--></script>

</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
	
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
	
	
		<%-- <%@include file="/include/menu_vertical.jsp"%> --%>
		<!-- Conteudo -->
		<!-- <div id="main_container" class="main_container container_16 clearfix"> -->
		<%-- 	<%@include file="/include/menu_horizontal.jsp"%> --%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="pesquisaRequisicaoMudanca.pesquisaRequisicaoMudanca"/></a></li>
				</ul> 
				<a href="#" class="toggle">&nbsp;</a>
				<div  class="toggle_container">
					<div  class="block">
						<div id="parametros">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaRequisicaoMudanca/pesquisaRequisicaoMudanca'>
								<input type="hidden" id='idSolicitante' name='idSolicitante'>
								<input type="hidden" id='idProprietario' name='idProprietario'>
								<input type="hidden" id='idItemConfiguracao' name='idItemConfiguracao'>
								<input type="hidden" id='idUnidade' name='idUnidade'>
								<input type="hidden" id='formatoArquivoRelatorio' name='formatoArquivoRelatorio'>
								
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 71px">
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.periodo" /></label>
											<div>
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
										<fieldset style="height: 71px">
											<label><i18n:message key="citcorpore.comum.numero" /></label>
											<div>
												<input type="text" id="idRequisicaoMudanca" name="idRequisicaoMudanca" size="9" maxlength="9" class='Format[Numero]'/>
											</div>
										</fieldset>																				
									</div>
									<%-- <div class="col_25">
										<fieldset>
											<label><i18n:message key="contrato.contrato" /></label>
											<div>
												<select name='idContrato'>
												</select>
											</div>
										</fieldset>
									</div>	 --%>
									<div class="col_25">
										<fieldset style="height: 71px">
											<label><i18n:message key="citcorpore.comum.ordenacao" /></label>
											<div>
												<div>
												<select name='ordenacao' id="ordenacao">
													<option selected="selected" value='requisicaomudanca.idrequisicaomudanca'><i18n:message key="citcorpore.comum.numero" /></option>
													<option value="requisicaomudanca.datahorainicio"><i18n:message key="citcorpore.comum.data" /></option>
										            <OPTION value='ltrim(solicitante.nome)'><i18n:message key="solicitacaoServico.solicitante" /></OPTION>
										             <OPTION value='ltrim(proprietario.nome)'><i18n:message key="requisicaoMudanca.proprietario" /></OPTION>
										            <OPTION value='requisicaomudanca.status'><i18n:message key="requisicaMudanca.status" /></OPTION>
										            <OPTION value='requisicaomudanca.idgrupoatual'><i18n:message key="pesquisasolicitacao.gruposolucionador" /></OPTION>
												</select>
											</div>
											</div>
										</fieldset>																				
									</div>
								</div>
								
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset >
											<label><i18n:message key="solicitacaoServico.itemConfiguracao" /></label>
											<div>
												<input type="text" onfocus='abrePopupIC();' id="nomeItemConfiguracao" name="nomeItemConfiguracao" />
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset >
											<label><i18n:message key="solicitacaoServico.solicitante" /></label>
											<div>
												<input type="text" onclick="selecionarSolicitante();" id="nomeSolicitante" name="nomeSolicitante" />
											</div>
										</fieldset>
									</div>	
									<div class="col_25">
										<fieldset >
											<label><i18n:message key="requisicaoMudanca.proprietario" /></label>
											<div>
											<input class="Valid[Required] Description[requisicaoMudanca.proprietario]" id="nomeProprietario" name="nomeProprietario" type="text" readonly="readonly" onclick="selecionarProprietario()"/>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 55px" >
											<label><i18n:message key="requisicaMudanca.status" /></label>
											<div>
												<select name='status'>
													<option value=''>--<i18n:message key="citcorpore.comum.todos" /> --</option>
													<OPTION value='Registrada'><i18n:message key="citcorpore.comum.registrada" /></OPTION>
													<OPTION value='Proposta'><i18n:message key="citcorpore.comum.proposta" /></OPTION>
										            <OPTION value='Aprovada'><i18n:message key="citcorpore.comum.aprovada" /></OPTION>
										            <OPTION value='Planejada'><i18n:message key="citcorpore.comum.planejada" /></OPTION>
										            <OPTION value='EmExecucao'><i18n:message key="citcorpore.comum.emExecucao" /></OPTION>
										            <OPTION value="Concluida" ><i18n:message key="citcorpore.comum.concluida" /></OPTION>
										            <OPTION value="Rejeitada" ><i18n:message key="citcorpore.comum.rejeitada" /></OPTION>
										            <OPTION value="Cancelada" ><i18n:message key="citcorpore.comum.cancelada" /></OPTION>
												</select>
											</div>
										</fieldset>
									</div>	
								</div>		
														
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="pesquisasolicitacao.gruposolucionador" /></label>
											<div>
												<select name='idGrupoAtual'>
												</select>
											</div>
										</fieldset>
									</div>
								<%-- 	<div class="col_25">
										<fieldset>
											<label><i18n:message key="pesquisasolicitacao.fase" /> </label>
											<div>
												<select name='idFaseAtual'>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.origem" /></label>
											<div>
												<select name='idOrigem'>
												</select>
											</div>
										</fieldset>
									</div>		 --%>
									
									<div class="col_25">
										<div>
											<fieldset>
												<label><i18n:message key="citcorpore.comum.tipo" /></label>
												<div>
													<select id="comboTipo" name='idTipoMudanca' onchange="mostrarCategoria();">
													</select>
												</div>
											</fieldset>										
										</div>
									</div>		
									<div class="col_25">
										<div id="div_categoria" style="display:none">
											<fieldset>
												<label><i18n:message key="pesquisaRequisicaoMudanca.categoriaMudanca" /></label>
												<div>
													<select  name='nomeCategoriaMudanca'>
													<option value=''>--<i18n:message key="citcorpore.comum.todos" /> --</option>
													<option value="Importante"><i18n:message key="pesquisaRequisicaoMudanca.categoriaMudancaImportante" /></option>
													<option value="Significativa"><i18n:message key="pesquisaRequisicaoMudanca.categoriaMudancaSignificativa" /></option>
													<option value="Pequena"><i18n:message key="pesquisaRequisicaoMudanca.categoriaMudancaPequena" /></option>
													</select>
												</div>
											</fieldset>										
										</div>
									</div>	
									<div class="col_25">
										<fieldset style="height: 52px">
											<label style="display: block; float: left;padding-top: 15px;"><input  type="checkbox" name="exibirCampoDescricao" id="exibirCampoDescricao" value="S" /><i18n:message key="pesquisasolicitacao.exibirCampoDescricaoRelatorios"/> </label>
										</fieldset>
									</div>	
								</div>		
								<%-- <div class="columns clearfix">
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.unidade" /></label>
											<div>
												<input type="text"  id="addUnidade" name="nomeUnidade" />
											</div>
										</fieldset>
									</div>
								</div> --%>
								
								<div class="col_100">
									<fieldset>
										<button type='button' name='btnPesquisar' class="light"  onclick='pesquisaRequisicaoMudanca();' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><i18n:message key="citcorpore.comum.pesquisar" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"  onclick='limpar()' style="margin: 20px !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
									 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioRequisicaoMudanca()' style="margin: 20px !important;">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
										<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button> 
										 <button type='button' name='btnRelatorio' class="light"  onclick='imprimirRelatorioRequisicaoMudancaXls()' style="margin: 20px !important;">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png">
										<span><i18n:message key="citcorpore.comum.gerarrelatorio" /></span>
										</button> 
									</fieldset>
								</div>
								
							</form>												
						</div>
						
						
						<div class="col_100"  align="center" id="divTblRequisicaoMudanca" style='display: block; border:0px solid gray'>
						
						<table id='tblRequisicaoMudanca' class="table" cellpadding="0" cellspacing="0" width='100%'>
							<tr>
								<th width="10%" ><i18n:message key="requisicaoMudanca.NumeroMudanca" /></th>
								<th width="10%" ><i18n:message key="citcorpore.comum.tipo" /></th>
								<th width="10%" ><i18n:message key="requisicaMudanca.status" /></th>
								<th width="10%" ><i18n:message key="requisicaMudanca.titulo" /></th>
								<th width="10%" ><i18n:message key="citcorpore.comum.motivo" /></th>
								<th width="10%" ><i18n:message key="citcorpore.comum.descricao" /></th>
								<th width="10%"><i18n:message key="requisicaoMudanca.proprietario" /></th>
								<th width="10%" ><i18n:message key="solicitacaoServico.solicitante" /></th>
								<th width="10%" ><i18n:message key="citcorpore.comum.categoria" /></th>
								<th width="10%" ><i18n:message key="citcorpore.comum.dataHoraInicio" /></th>
								<th width="10%" ><i18n:message key="grupo.grupo" /></th>
								
							</tr>
						</table>
						
						</div> 
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	
		<%
		    if (iframe == null) {
		%>
		<%@include file="/include/footer.jsp"%>
		<%} %>

</body>
	<div class="POPUP_barraFerramentasMudancas" id="POPUP_menuAnexos" style='display:none'>		
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControlList style="height:100px;width:50%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/uploadList/uploadList.load" disabled="false"/>
		</form>
	</div>	
	
	<div id="POPUP_UNIDADE" title="<i18n:message key="citcorpore.comum.unidade" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaUnidade' style="width: 540px">
						<cit:findField formName='formPesquisaUnidade'
							lockupName='LOOKUP_UNIDADE' id='LOOKUP_UNIDADE_SOLICITACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

	<div id="POPUP_EMPREGADO" title="<i18n:message key="citcorpore.comum.pesquisa" />" >
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaColaborador' style="width: 540px">
							<cit:findField formName='formPesquisaColaborador'
								lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	
	<div id="POPUP_ITEMCONFIG" title="<i18n:message key="citcorpore.comum.identificacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisa' style="width: 540px">
						<cit:findField formName='formPesquisa'
 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0' 
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>

	<div id="POPUP_OCORRENCIAS" title="<i18n:message key="citcorpore.comum.ocorrenciasolicitacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formOcorrencias' method="post" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao'>
							<input type='hidden' name='idSolicitacaoOcorrencia' />
							<div id='divRelacaoOcorrencias' style='width: 100%; height: 100%;'>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

</div>
</html>