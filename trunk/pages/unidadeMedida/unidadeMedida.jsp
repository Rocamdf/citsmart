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
			String iframe = "";
			iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<html lang="en-us" class="no-js">
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {

		
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_UNIDADEMEDIDA_select(id, desc) {
		document.form.restore({
			idUnidadeMedida : id
		});
	}
	
	function excluir() {
		if (document.getElementById("idUnidadeMedida").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	

	
	
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
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="unidadeMedida" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="unidadeMedida.cadastro" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="unidadeMedida.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/unidadeMedida/unidadeMedida'>
								<input type='hidden' name='idUnidadeMedida' id = "idUnidadeMedida" />
								<div class="columns clearfix">
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="unidadeMedida.nome" /></label>
											<div>
												<input type='text' name="nomeUnidadeMedida" id = "nomeUnidadeMedida" maxlength="100"
													class="Valid[Required] Description[unidadeMedida.nome]" />
											</div>
										</fieldset>
									</div>
								<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="unidadeMedida.sigla" /></label>
											<div>
												<input type='text' name="siglaUnidadeMedida" id = "sigla" maxlength="10"
													class="Valid[Required] Description[unidadeMedida.sigla]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 55px">
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.situacao" /></label>
											<div  class="inline clearfix">
											<label><i18n:message key="citcorpore.comum.ativo" /><input type="radio"  name="situacao" value="A" checked="checked" /></label>
											<label><i18n:message key="citcorpore.comum.inativo" /><input type="radio"  name="situacao" value="I" /></label>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='document.form.save();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir"
									class="light" onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_UNIDADEMEDIDA' id='LOOKUP_UNIDADEMEDIDA' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
