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
%>
<%@include file="/include/security/security.jsp"%>
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script>
	var objTab = null;
	var descricaoOuQueryAlterada = false;
	
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
		
		$("#POPUP_RESULTADO_CONSULTA").dialog({
			closeOnEscape: false,
			title: i18n_message('scripts.resultadoConsulta'),
			autoOpen : false,
			width : 900,
			height : 600,
			modal : true
		});
		
		$("#POPUP_MENSAGEM_FALTA_PERMISSAO").dialog({
			title: i18n_message('mensagem'),
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
	}

	function LOOKUP_SCRIPTS_select(id, desc) {
		document.form.restore({
			idScript :id});
	}
	
	function excluir() {
		if (document.getElementById("idScript").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}
	
	function validaAtualizacao() {
		if(confirm("Confirma a validação da atualização do CITSMart?")){
			document.form.fireEvent("validaAtualizacao");
		}
	}
	
	function encaminhaParaIndex() {
		document.form.submit();
		window.location = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load?mensagem=" + i18n_message("citcorpore.comum.validacaoSucesso");
	}
	
	function orientacaoTecnicaPopUp() {
		$('#POPUP_ORIENTACAO_TECNICA').dialog('open');
	}
	
	function executar() {
		if (document.getElementById("tipo").value != "consulta" 
				&& (document.getElementById("idScript").value == "" 
					|| descricaoOuQueryAlterada)) {
			alert(i18n_message("scripts.favorGravarScript"));
		} else if (document.getElementById("tipo").value == "consulta" 
				|| document.getElementById("historico").value == "" 
				|| confirm(i18n_message("scripts.scriptJaFoiExecutado"))) {
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("executar");
		}
	}
	
	function downloadDocumento(sel) {
		var value = sel.options[sel.selectedIndex].value;
		window.open(value, '_blank')
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
<style type="text/css">
.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	  overflow: hidden;
	  margin: 10px 0 0 10px ;
	}
	
	.tableLess tbody {
	  background: transparent  !important;
	}
	
	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}
	
	.tableLess tr th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}
	
	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}
	
	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9 ;
	  /* cursor: pointer; */
	}
	
	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}
	
	.tableLess td{
		border: 1px solid #BBB  !important;
		padding: 6px 10px  !important;
		max-width: 250px;
		padding: 6px 10px !important;
		text-overflow: ellipsis;
	}
	#historico{
		display: block!important;
	}
	
</style>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

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
					<i18n:message key="scripts.scripts" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="scripts.cadastroScripts" />
					</a>
					</li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="scripts.pesquisaScripts" />
					</a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/scripts/scripts'>
								<input type='hidden' name='idScript' id='idScript' />
								<input type='hidden' name='dataInicio' />
								<input type='hidden' name='dataFim' />
								<div class="columns clearfix">
									<div class="col_50">
										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="scripts.nome" /></label>
													<div>
													  <input type='text' name="nome" id="nome" maxlength="70" size="70" class="Valid[Required] Description[scripts.nome]" onchange="descricaoOuQueryAlterada=true;" />
													</div>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label><i18n:message key="scripts.descricao" /></label>
													<div>
													  <textarea name="descricao" cols='70' rows='2' onchange="descricaoOuQueryAlterada=true;"></textarea>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix">
											<div  class="col_100">
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.tipo"/></label>
													<div >
														<select name='tipo' id="tipo" class="Valid[Required] Description[citcorpore.comum.tipo]" onchange="descricaoOuQueryAlterada=true;"></select>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="scripts.historico" /></label>
											<div>
											  <textarea name='historico' id='historico' cols='70' rows='8' readonly="readonly"></textarea>
											</div>
										</fieldset>
									</div>
								</div>				
								<div class="columns clearfix">          
								  <div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="scripts.sqlQuery" /></label>
										<div>
										  <textarea name="sqlQuery" cols='70' rows='5' class="Valid[Required] Description[scripts.sqlQuery]" onchange="descricaoOuQueryAlterada=true;"></textarea>
										</div>
									</fieldset>
									</div>
								</div>
								<br> <br>
								<button type='button' name='btnGravar' class="light" onclick='document.form.save();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir" class="light" onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" /></span>
								</button>
								<button type='button' name='btnExecutar' id="btnExecutar" class="light" onclick='executar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/cog_2.png">
									<span><i18n:message key="scripts.executar" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField
									formName='formPesquisa'
									lockupName='LOOKUP_SCRIPTS'
									id='LOOKUP_SCRIPTS'
									top='0'
									left='0'
									len='550'
									heigth='400'
									javascriptCode='true'
									htmlCode='true'
								/>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_RESULTADO_CONSULTA" title="<i18n:message key='scripts.resultadoConsulta' />">
		<div id='headerResultadoConsulta'></div>
		<div id='contentResultadoConsulta'></div>
		
	</div>
	<div id="POPUP_MENSAGEM_FALTA_PERMISSAO" title="<i18n:message key='citcorpore.comum.seguintesItensNaoTratados' />">
		<div id='divPopupVerificacaoPermissoes'></div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>

