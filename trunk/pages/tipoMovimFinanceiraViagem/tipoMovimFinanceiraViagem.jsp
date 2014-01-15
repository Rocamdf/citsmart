<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%-- <%@page import="br.com.centralit.citcorpore.bean.tipoMovimFinanceiraViagemDTO"%> --%>
<%-- <%@page import="br.com.citframework.ajax.tipoMovimFinanceiraViagem"%> --%>
<!doctype html public "✰">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
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

	function LOOKUP_TIPOMOVIMFINANCEIRAVIAGEM_select(id, desc) {
		document.form.restore({
			idtipoMovimFinanceiraViagem : id
		});
	}

	/* function excluir() {
		if (document.getElementById("idtipoMovimFinanceiraViagem").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	} */
</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="tipoMovimFinanceiraViagem.TipoMovimFinanceiraViagem" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="tipoMovimFinanceiraViagem.cadastroTipoMovimFinanceiraViagem" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="tipoMovimFinanceiraViagem.pesquisaTipoMovimFinanceiraViagem" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/tipoMovimFinanceiraViagem/tipoMovimFinanceiraViagem'>
								<div class="columns clearfix">
									<input type='hidden' name='idtipoMovimFinanceiraViagem' /> 
									<div class="col_40">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="nome" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="citcorpore.comum.classificacao"/></label>
											<div>
												<select name="classificacao" id ="classificacao" class="Valid[Required] Description[citcorpore.comum.classificacao]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="citcorpore.comum.tipo"/></label>
											<div>
												<select name="tipo" id ="tipo" class="Valid[Required] Description[citcorpore.comum.tipo]"></select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="tipoMovimFinanceiraViagem.exigePrestacaoContas"/></label>
											<div class="inline clearfix">
												<label><input type="radio" name="exigePrestacaoConta" value="S" checked="checked" class="Valid[Required] Description[tipoMovimFinanceiraViagem.exigePrestacaoContas]"/><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" name="exigePrestacaoConta" value="N" class="Valid[Required] Description[tipoMovimFinanceiraViagem.exigePrestacaoContas]"/><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="tipoMovimFinanceiraViagem.exigeJustificativa"/></label>
											<div class="inline clearfix">
												<label><input type="radio" name="exigeJustificativa" value="S" checked="checked" class="Valid[Required] Description[tipoMovimFinanceiraViagem.exigeJustificativa]"/><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" name="exigeJustificativa" value="N" class="Valid[Required] Description[tipoMovimFinanceiraViagem.exigeJustificativa]"/><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="tipoMovimFinanceiraViagem.permiteAdiantamento"/></label>
											<div class="inline clearfix">
												<label><input type="radio" name="permiteAdiantamento" value="S" checked="checked" class="Valid[Required] Description[tipoMovimFinanceiraViagem.permiteAdiantamento]"/><i18n:message key="citcorpore.comum.sim" /></label>
												<label><input type="radio" name="permiteAdiantamento" value="N" class="Valid[Required] Description[tipoMovimFinanceiraViagem.permiteAdiantamento]"/><i18n:message key="citcorpore.comum.nao" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_15">
										<fieldset style="height: 61px">
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="citcorpore.comum.situacao" /></label>
											<div  class="inline clearfix">
											<label><input type="radio" name="situacao" value="A" checked="checked" class="Valid[Required] Description[citcorpore.comum.situacao]"/><i18n:message key="citcorpore.comum.ativo" /></label>
											<label><input type="radio" name="situacao" value="I" class="Valid[Required] Description[citcorpore.comum.situacao]"/><i18n:message key="citcorpore.comum.inativo" /></label>
											</div>
										</fieldset>
									</div>
									<div class="col_20">
										<fieldset>
											<label style="margin-top: 5px;"><i18n:message key="tipoMovimFinanceiraViagem.valorPadrao" /></label>
											<div>
												<input type='text' name="valorPadrao" maxlength="6" style="width: 235px !important;" class="Format[Moeda] " />
											</div>
										</fieldset>
									</div>	
								</div>
								<div class="columns clearfix">
									<div class="col_80">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.descricao" />
											</label>
											<div>
												<textarea name="descricao" cols='200' rows='5' maxlength="2000" ></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='document.form.save();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>
								<%-- <button type='button' name='btnExcluir' class="light" onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" /></span>
								</button> --%>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_TIPOMOVIMFINANCEIRAVIAGEM' id='LOOKUP_TIPOMOVIMFINANCEIRAVIAGEM' top='0'
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
