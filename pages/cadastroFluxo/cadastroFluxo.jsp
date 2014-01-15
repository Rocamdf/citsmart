<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

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
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>


<script type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
<script type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
<script>
	addEvent(window, "load", load, false);
	var popup;
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
			document.form.nomeFluxo.focus();
		}
	}
	function salvar(){
		document.form.save();
	}
	function excluir(){
		if (!confirm(i18n_message("cadastroFLuxo.confirma_exclusao"))) 
			return;
		document.form.fireEvent('delete');
	}
</script>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>
</head>
<body>
	<div id="">
		<!-- Conteudo -->
		<div id="main_container">
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="cadastroFLuxo.fluxo" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/cadastroFluxo/cadastroFluxo'>
								<input type="hidden" name="locale" id="locale" value="" />
								<input type="hidden" name="idTipoFluxo" id="idTipoFluxo" />
								<input type="hidden" name="idFluxo" id="idFluxo" />
								<input type="hidden" name="dataInicio" id="dataInicio" />
								<input type="hidden" name="dataFim" id="dataFim" />
								<input type="hidden" name="conteudoXML" id="conteudoXML" />
								<div class="columns clearfix">
	                                <div class="col_100">
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="citcorpore.comum.nome" />
												</label>
												<div>
													<input type='text' name="nomeFluxo" class="" />
												</div>
											</fieldset>
										</div>
	                                    <div class="col_66">
	                                        <fieldset>
	                                            <label class="campoObrigatorio">
	                                                <i18n:message key="citcorpore.comum.descricao" />
	                                            </label>
	                                            <div>
	                                                <input type='text' name="descricao" class="" />
	                                            </div>
	                                        </fieldset>
	                                    </div>
	                                </div>
                                    <div class="col_100">
										<div class="col_80">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="cadastroFluxo.nomeClasse" />
												</label>
												<div>
													<input type='text' name="nomeClasseFluxo" class="" />
												</div>
											</fieldset>
										</div>
										<div class="col_20">
											<fieldset>
												<label>
													<i18n:message key="login.versao" />
												</label>
												<div>
													<input type='text' readonly="readonly" name="versao" class="" />
												</div>
											</fieldset>
										</div>
                                    </div>
								</div>
								<div class="col_100">
									<fieldset>
										<label>
											<i18n:message key="cadastroFLuxo.variaveis" />
										</label>
										<div>
											<textarea name="variaveis" rows="2" cols="50"></textarea>
										</div>
									</fieldset>
								</div>
								<div class="col_100">
								<button type='button' name='btnGravar' class="light"
									onclick='salvar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" />
									</span>
								</button>
								</div>
							</form>
						</div>
					</div>
				<!-- ## FIM - AREA DA APLICACAO ## -->
			</div>
		</div>
	</div>

	<!-- Fim da Pagina de Conteudo -->
	</div>
</body>

</html>

