<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>
<!doctype html public "âœ°">
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
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
<script>
	var popup;
	var popup1;
	
	addEvent(window, "load", load, false);
	function load() {
		popup = new PopupManager(800, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		popup1 = new PopupManager(800, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_LOCALIDADE_select(id,desc){
		document.form.restore( {
			idLocalidade : id
		});
	}
	
	
	function preecherUfs() {
		if (document.getElementById("idRegioes").value != "") {
				document.form.fireEvent("preencherComboUfs");
		}
	}
	
	function preecherCidades() {
		if (document.getElementById("idUf").value != "") {
				document.form.fireEvent("preencherComboCidades");
		}
	}
	
	function excluir() {
		if (document.getElementById("idLocalidade").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}
	
	function LOOKUP_LOCALIDADEITEMCONFIGURACAO_select(id, desc) {
		document.form.itemConfiguracao.value = desc
		document.form.idItemConfiguracao.value = id;	
		$("#POPUP_ITEMCONFIGURACAO").dialog("close");		
		
	}
	
	$(function() {
		$("#POPUP_ITEMCONFIGURACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	});
	
	$(function() {
		$("#addItemConfiguracao").click(function() {
			$("#POPUP_ITEMCONFIGURACAO").dialog("open");
		});
	});
		

</script>

<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
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
					<i18n:message key="localidade.localidade" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="localidade.cadastroLocalidade" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="localidade.pesquisaLocalidade" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/localidadeItemConfiguracao/localidadeItemConfiguracao'>
								<div class="columns clearfix">
									<input type='hidden' name='idLocalidade'  id="idLocalidade"/> 
									<input type='hidden' id="idItemConfiguracao" name='idItemConfiguracao' /> 
									<input type="hidden" id="dataInicio" name="dataInicio" /> 
									<input type="hidden" id="dataFim" name="dataFim" />
									<div  class="col_33">
										<fieldset style="height: 64px">
											<label><i18n:message key="localidade.itemConfiguracao" />
											</label>
											<div>
												<input type='text' id="addItemConfiguracao" name="itemConfiguracao" maxlength="80" class="Valid[Required] Description[Item de Configuracao]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset >
											<label><i18n:message key="grupo.grupo" />
											<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('grupo','preencherComboGrupo')">
											</label>
											<div>
												<select id="departamento" name='departamento'  class="Valid[Required] Description[Departamento]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="unidade.unidade" />
											<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup1.abrePopup('unidade','preencherComboUnidade')">
											</label>
											<div>
												<select id="idUnidade" name='idUnidade'  class="Valid[Required] Description[Unidade]" ></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="localidade.regiao" />
											</label>
											<div>
												<select id="idRegioes" name='idRegioes'  onclick="preecherUfs()" class="Valid[Required] Description[Regiao]" ></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="localidade.uf" />
											</label>
											<div>
											<select name='idUf' id="idUf" onclick="preecherCidades()" class="Valid[Required] Description[uf]" ></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="localidade.cidade" />
											</label>
											<div>
												<select id="cidade" name='cidade'  class="Valid[Required] Description[Cidade]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<label><i18n:message key="localidade.endereco" />
											</label>
											<div>
												<input type='text' name="endereco" maxlength="80" class="Valid[Required] Description[Endereço]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="localidade.complemento" />
											</label>
											<div>
												<input type='text' name="complemento" maxlength="80" class="Valid[Required] Description[Complemento]" />
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<label><i18n:message key="localidade.bairro" />
											</label>
											<div>
												<input type='text' name="bairro" maxlength="80" class="Valid[Required] Description[Bairro]" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.numero" />
											</label>
											<div>
												<input type='text' name="numero" maxlength="80" class="Valid[Required] Description[Numero" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="localidade.edificio" />
											</label>
											<div>
												<input type='text' name="edificio" maxlength="80" class="Valid[Required] Description[Edificio]" />
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset>
											<label><i18n:message key="localidade.sala" />
											</label>
											<div>
												<input type='text' name="sala" maxlength="80" class="Valid[Required] Description[Sala]" />
											</div>
										</fieldset>
									</div>
								</div>
								<h2 class="section">
									<i18n:message key="localidade.organizacao" />
								</h2>
								<div class="columns clearfix">
								<div class="col_50">
									<fieldset style="height: 61px">
										<label> <i18n:message key="localidade.organizacao.divisao" />
										</label>
										<div>
											<input type='text' name="divisao" maxlength="80" class="Valid[Required] Description[Divisão]" />
										</div>
									</fieldset>
								</div>
								<div class="col_50">
									<fieldset>
										<label><i18n:message key="localidade.organizacao.subDivisao" />
										</label>
										<div>
											<input type='text' name="subdivisao" maxlength="80" class="Valid[Required] Description[Subdivisão]" />
										</div>
									</fieldset>
								</div>
								<div class="col_50">
										<fieldset>
											<label><i18n:message key="localidade.organizacao.secao" />
											</label>
											<div>
												<input type='text' name="secao" maxlength="80" class="Valid[Required] Description[Secão]" />
											</div>
										</fieldset>
								</div>
								</div>
								<div id="popupCadastroRapido">
									<iframe id="frameCadastroRapido" name="frameCadastroRapido"
										width="100%" height="100%"></iframe>
								</div>
								<button type='button' name='btnGravar' class="light" onclick='document.form.save();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisa' style="width: 540px">
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_LOCALIDADE' id='LOOKUP_LOCALIDADE' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
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
		<!-- Fim POPUP ITEM DE CONFIGURAÃ‡ÃƒO -->
</body>
<div id="POPUP_ITEMCONFIGURACAO" title="Pesquisa">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formItemConfiguracao' style="width: 540px">
						<cit:findField formName='formItemConfiguracao'
							lockupName='LOOKUP_LOCALIDADEITEMCONFIGURACAO' id='LOOKUP_LOCALIDADEITEMCONFIGURACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>