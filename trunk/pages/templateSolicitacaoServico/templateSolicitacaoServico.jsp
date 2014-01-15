<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html lang="en-us" class="no-js">
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			String iframe = "";
			iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
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
	};
	
	function LOOKUP_TEMPLATESOLICITACAOSERVICO_select(id, desc) {
		document.form.restore({
			idTemplate : id
		});
	}
	
	function excluir() {
		if (document.getElementById("idTemplate").value != "") {
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
					<i18n:message key="templateSolicitacaoServico.Template" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="templateSolicitacaoServico.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="templateSolicitacaoServico.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/templateSolicitacaoServico/templateSolicitacaoServico'>
								<input type='hidden' id='idTemplate' name='idTemplate'/>
								
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.identificacao" /></label>
											<div>
												<input type='text' name="identificacao" maxlength="40" class="Valid[Required] Description[templateSolicitacaoServico.identificacao]" />
											</div>
										</fieldset>
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.nomeTemplate" /></label>
											<div>
												<input type='text' name="nomeTemplate" maxlength="200" class="Valid[Required] Description[templateSolicitacaoServico.nomeTemplate]" />
											</div>
										</fieldset>
										<fieldset>
											<label ><i18n:message key="templateSolicitacaoServico.nomeClasseDto" /></label>
											<div>
												<input type='text' name="nomeClasseDto" maxlength="255" />
											</div>
										</fieldset>
										<fieldset>
											<label ><i18n:message key="templateSolicitacaoServico.nomeClasseAction" /></label>
											<div>
												<input type='text' name="nomeClasseAction" maxlength="255"  />
											</div>
										</fieldset>
										<fieldset>
											<label ><i18n:message key="templateSolicitacaoServico.nomeClasseServico" /></label>
											<div>
												<input type='text' name="nomeClasseServico" maxlength="255" />
											</div>
										</fieldset>
										<fieldset>
											<label><i18n:message key="templateSolicitacaoServico.urlRecuperacao" /></label>
											<div>
												<input type='text' name="urlRecuperacao" maxlength="255"  />
											</div>
										</fieldset>
                                        <fieldset>
                                            <label style="cursor: pointer;"><i18n:message key="menu.nome.questionario" /></label>
                                            <div>
                                                <select name='idQuestionario' id='idQuestionario'></select>
                                            </div>
                                        </fieldset>
										<fieldset>
											<label><i18n:message key="templateSolicitacaoServico.scriptAposRecuperacao" /></label>
											<div>
												<textarea name="scriptAposRecuperacao"></textarea>
											</div>
										</fieldset>
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.alturaDiv" /></label>
											<div>
												<input type='text' name="alturaDiv" maxlength="5" class="Valid[Required] Description[templateSolicitacaoServico.alturaDiv] Format[Numero]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaDirecionamento" /></label>
											<div  class="inline clearfix">
												<label><input type="radio" id="radioHabilitaDirecionamento" name="habilitaDirecionamento" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" id="radioHabilitaDirecionamento" name="habilitaDirecionamento" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaSituacao" /></label>
											<div  class="inline clearfix">
												<label><input type="radio" id="radioHabilitaSituacao" name="habilitaSituacao" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" id="radioHabilitaSituacao" name="habilitaSituacao" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaSolucao" /></label>
											<div  class="inline clearfix">
												<label><input type="radio" id="radioHabilitaSolucao" name="habilitaSolucao" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" id="radioHabilitaSolucao" name="habilitaSolucao" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaUrgenciaImpacto" /></label>
											<div  class="inline clearfix">
												<label><input type="radio" id="radioHabilitaUrgenciaImpacto" name="habilitaUrgenciaImpacto" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" id="radioHabilitaUrgenciaImpacto" name="habilitaUrgenciaImpacto" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaNotificacaoEmail" /></label>
											<div  class="inline clearfix">
												<label><input type="radio" id="radioHabilitaNotificacaoEmail" name="habilitaNotificacaoEmail" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" id="radioHabilitaNotificacaoEmail" name="habilitaNotificacaoEmail" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
                                        <fieldset>
                                            <label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaProblema" /></label>
                                            <div  class="inline clearfix">
                                                <label><input type="radio" id="radioHabilitaProblema" name="habilitaProblema" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
                                                <label><input type="radio" id="radioHabilitaProblema" name="habilitaProblema" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaMudanca" /></label>
                                            <div  class="inline clearfix">
                                                <label><input type="radio" id="radioMudanca" name="habilitaMudanca" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
                                                <label><input type="radio" id="radioMudanca" name="habilitaMudanca" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaItemConfiguracao" /></label>
                                            <div  class="inline clearfix">
                                                <label><input type="radio" id="radioItemConfiguracao" name="habilitaItemConfiguracao" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
                                                <label><input type="radio" id="radioItemConfiguracao" name="habilitaItemConfiguracao" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaSolicitacaoRelacionada" /></label>
                                            <div  class="inline clearfix">
                                                <label><input type="radio" id="radioSolicitacaoRelacionada" name="habilitaSolicitacaoRelacionada" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
                                                <label><input type="radio" id="radioSolicitacaoRelacionada" name="habilitaSolicitacaoRelacionada" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
                                            </div>
                                        </fieldset>
                                        <fieldset>
                                            <label class="campoObrigatorio"><i18n:message key="templateSolicitacaoServico.habilitaGravarEContinuar" /></label>
                                            <div  class="inline clearfix">
                                                <label><input type="radio" id="radioGravarEContinuar" name="habilitaGravarEContinuar" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
                                                <label><input type="radio" id="radioGravarEContinuar" name="habilitaGravarEContinuar" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
                                            </div>
                                        </fieldset>
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.aprovacao" /></label>
											<div  class="inline clearfix">
												<label><input type="radio" id="aprovacao" name="aprovacao" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" id="aprovacao" name="aprovacao" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
									</div>
								</div>
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
									onclick='excluir();'>
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
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_TEMPLATESOLICITACAOSERVICO' id='LOOKUP_TEMPLATESOLICITACAOSERVICO' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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