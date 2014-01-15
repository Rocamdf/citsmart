<%@page import="br.com.citframework.util.UtilStrings"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "âœ°">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);

			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");
			
	String divInfo = (String)request.getAttribute("divInfo");
	divInfo = UtilStrings.nullToVazio(divInfo);
%>
<%@include file="/include/security/security.jsp"%>
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
	}

	function submeter(){
		document.getElementById('divInfo').innerHTML = i18n_message("citcorpore.comum.aguarde");
		//if (document.form.idGrupoItemConfiguracao.value == ''){
		//	document.getElementById('divInfo').innerHTML = '';
		//	alert('Selecione o Grupo!');
		//	return;
		//}
		//document.form.fireEvent('mostraInfo');
		document.form.action = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/treeViewItemCfgAval/treeViewItemCfgAval.load';
		document.form.submit();
	}
	function visualizaSofts(id){
		document.form.idItemConfiguracao.value = id;
		$('#POPUP_SOFTS').dialog('open');
		document.getElementById('divInfoSoftware').innerHTML = i18n_message("citcorpore.comum.aguarde");
		document.form.fireEvent('viewSoftwares');
	}
	function visualizaNet(id){
		document.form.idItemConfiguracao.value = id;
		$('#POPUP_SOFTS').dialog('open');
		document.getElementById('divInfoSoftware').innerHTML = i18n_message("citcorpore.comum.aguarde");
		document.form.fireEvent('viewNetwork');		
	}
	$(function() {
		$("#POPUP_SOFTS").dialog({
			autoOpen : false,
			width : 1000,
			height : 700,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});	
	
	function imprimir(){
		$('#divInfo').printElement({
			printMode : 'popup',
			leaveOpen : true,
			 pageTitle : 'CITSMART Report',
			iframeElementOptions:{classNameToAdd : 'ui-corner-all resBusca'}
			});    			
	}	
	
	function setaProcessar(v) {
		$('input[name="processar"]').attr('value', v);
	}
</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
	width: 100%;
}
</style>
<%}%>
</head>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="treeViewItemCfgAval.titulo" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="treeViewItemCfgAval.titulo" />
					</a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' method="post"
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/treeViewItemCfgAval/treeViewItemCfgAval'>
								<div class="columns clearfix">
									<input type='hidden' name='idItemConfiguracao' />
									<input type='hidden' name='processar' value="S"/>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.estrutura"/></label>
												<div>										
													<select name='idGrupoItemConfiguracao' id='idGrupoItemConfiguracao'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.status" /></label>
												<div>										
													<select name='status' id='status'>
													</select>
												</div>
											</fieldset>
										</div>										
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.sistemaOperacional"/>
												<div>										
													<select name='sistemaOperacional' id='sistemaOperacional'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.grupoTrabalho"/></label>
												<div>										
													<select name='grupoTrabalho' id='grupoTrabalho'>
													</select>
												</div>
											</fieldset>
										</div>										
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.tipoMebroDominio"/></label>
												<div>										
													<select name='tipoMembroDominio' id='tipoMembroDominio'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.usuario"/></label>
												<div>										
													<select name='usuario' id='usuario'>
													</select>
												</div>
											</fieldset>
										</div>										
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.processador"/></label>
												<div>										
													<select name='processador' id='processador'>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.criticidade" /></label>
												<div>										
													<select name='criticidade' id='criticidade'>
													</select>
												</div>
											</fieldset>
										</div>										
									</div>
									<div class="col_100">
										<div class="col_75">
											<fieldset>
												<label><i18n:message key="treeViewItemCfgAval.softwareInstalado"/></label>
												<div>										
													<textarea rows="3" cols="150" name='softwares' id='softwares'></textarea>
												</div>
											</fieldset>
										</div>				
										<div class="col_25">
											<table>
												<tr>
													<td>
														<button type='button' class="light" onclick='submeter()'><span><i18n:message key="citcorpore.comum.pesquisar"/></span></button>
														<!-- /*
														Rodrigo Pecci Acorse - 03/12/2013 17h30 - #126233
														Adicionado a função setaProcessar para após o clear não limpar o valor do input hidden processa.
														*/ -->
														<button type="button" class="light" name="btnLimpar" id="btnLimpar" onclick="document.form.clear();setaProcessar('S');"><span><i18n:message key="botaoacaovisao.limpar_dados" /></span></button>
													</td>
												</tr>
											</table>
										</div>					
									</div>																											
								</div>
								<div id='divInfo'>
									<%=divInfo %>
								</div>								
								<br> <br>
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
	
	<div id="POPUP_SOFTS" title="Itens">
		<div id='divInfoSoftware'></div>
	</div>
</body>
</html>