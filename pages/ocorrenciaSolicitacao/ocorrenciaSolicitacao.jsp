<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp"%>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="pt-br" class="no-js">
<!--<![endif]-->

<title>CIT Corpore</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<style>
		div#main_container {
			margin: 10px 10px 10px 10px;
		}
</style>

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.formOcorrenciaSolicitacao.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function LOOKUP_OCORRENCIA_SOLICITACAO_select(id, desc) {
		alert(desc);
		document.formOcorrenciaSolicitacao.restore({
			idOcorrencia : id
		});
	}
</script>
</head>
<body>
	<div id="wrapper">
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">

			<div class="flat_area grid_16">
				<h2><i18n:message key="citcorpore.comum.ocorrencia" /></h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="citcorpore.comum.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="citcorpore.comum.pesquisa" /></a></li>
				</ul>
				
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='formOcorrenciaSolicitacao' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao'>

								<div class="columns clearfix">
									<input type='hidden' name='idOcorrencia' />
									<div class="col_50">
										<fieldset>
										
											<label><i18n:message key="citcorpore.comum.categoria"/></label>
											<div>
												<select name='categoria' class="Valid[Required] Description[Categoria]">
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.origem"/></label>
											<div>
												<select name='origem' class="Valid[Required] Description[Origem]">
												</select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_20">
										<fieldset>
											<label><i18n:message key="ocorrenciaSolicitacao.tempoGasto"/></label>
											<div>
												<input type='text' name="tempoGasto" maxlength="4" size="4"
													   class="Valid[Required] Description[Tempo Gasto]" /> 
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.descricao"/></label>
											<div>
												<input type="text" name="descricao" cols='70' rows='5'
													class="Valid[Required] Description[Descrição]"></textarea>
											</div>
										</fieldset>
									</div>
								</div>

								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.ocorrencia" /></label>
											<div>
												<textarea cols='70' rows='5' name='ocorrencia'
													class="Valid[Required] Description[Ocorrência]">
												</textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.informacaoContato" /></label>
											<div>
												<textarea cols='70' rows='2' name='informacoesContato'
													class="Valid[Required] Description[Informações de Contato]">
												</textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='document.formOcorrenciaSolicitacao.save();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Gravar Dados</span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.formOcorrenciaSolicitacao.save();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Limpar Dados</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_OCORRENCIA_SOLICITACAO' id='LOOKUP_OCORRENCIA_SOLICITACAO' top='0'
									left='0' len='550' heigth='600' javascriptCode='true'
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
