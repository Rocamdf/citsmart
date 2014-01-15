<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<!doctype html public "✰">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script >
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_PRIORIDADE_select(id, desc) {
		document.form.restore({
			idPrioridade : id
		});
	}

	$(function() {
		$('.datepicker').datepicker();
	});

	function alterarSituacao() {
		var idPrioridade = document.getElementById("idPrioridade");

		if (idPrioridade.value != '') {
			if (confirm(i18n_message("citcorpore.comum.deleta")))
				document.form.fireEvent("alterarSituacao");
		}

	}
</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="prioridade.prioridade" />
				</h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message
								key="prioridade.cadastroPrioridade" />
					</a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message
								key="prioridade.pesquisaPrioridade" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/prioridade/prioridade'>
								<input type='hidden' name='idPrioridade' /> 
								<input type='hidden'name='idEmpresa' />
								<input type="hidden" name="situacao" />
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="prioridade.nomePrioridade" /></label>
											<div>
												<input type='text' name="nomePrioridade" maxlength="100"
													class="Valid[Required] Description[prioridade.nomePrioridade]" />
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="prioridade.grupoPrioridade" /></label>
											<div>
												<select name="grupoPrioridade" id="grupoPrioridade"
													class="Valid[Required] Description[prioridade.grupoPrioridade]"></select>
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
								<button type='button' name='btnExcluir' class="light"
									onclick='alterarSituacao();'>
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
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_PRIORIDADE' id='LOOKUP_PRIORIDADE' top='0'
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
</body>
</html>
