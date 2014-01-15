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
		/* Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 11:00 - ID Citsmart: 120948 - 
		* Motivo/Comentário: Lookup pequena/ Alterado width e height */	
		$("#POPUP_FORNECEDOR").dialog({
			autoOpen : false,
			width : 720,
			height : 600,
			modal : true
		});
		
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_MARCA_select(id, desc) {
		document.form.restore({
			idMarca : id
		});
	}
	
	function excluir() {
		if (document.getElementById("idMarca").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}

	function abrePopupFornecedor(){
		$("#POPUP_FORNECEDOR").dialog("open");
	}
	
	function LOOKUP_FORNECEDOR_select(id, desc){
		document.getElementById("idFabricante").value = id;
		document.getElementById("nomeFabricante").value = desc;
		$("#POPUP_FORNECEDOR").dialog("close");
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
					<i18n:message key="marca" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="marca.cadastro" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="marca.pesquisa" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/marca/marca'>
								<input type='hidden' name='idMarca' id = "idMarca" />
								<input type='hidden' name='idFabricante' id = "idFabricante" /> 
								<div class="columns clearfix">
								<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="marca.nomeMarca" /></label>
											<div>
												<input type='text' name="nomeMarca" id = "nomeMarca" maxlength="100"
													class="Valid[Required] Description[marca.nomeMarca]" />
											</div>
										</fieldset>
								</div>
								<div class="col_33">
										<fieldset>
											<label ><i18n:message key="marca.fabricante" /></label>
											<div>
												<input type='text' onfocus='abrePopupFornecedor();' name="nomeFabricante" id = "nomeFabricante" maxlength="100" />
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
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_MARCA' id='LOOKUP_MARCA' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
<!-- 	 Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 11:10 - ID Citsmart: 120948 - 
		* Motivo/Comentário: form pequeno/ Alterado width para 660px  -->
<div id="POPUP_FORNECEDOR" title="<i18n:message key="marca.pesquisaFabricante" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaFabricante' style="width: 660px">
							<cit:findField formName='formPesquisaFabricante' 
								lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>
