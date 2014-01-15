<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<!doctype html public "✰">
<html>
	<head>
		<%@include file="/include/security/security.jsp"%>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<%@include file="/include/noCache/noCache.jsp"%>
		<%@include file="/include/header.jsp"%>
		<title><i18n:message key="citcorpore.comum.title" /></title>
		<style>
			.table[hasElementFocus]>list>[lead] {
				border-color: hsl(214, 91%, 65%);
				z-index: 2;
			}
			
			.table[hasElementFocus]>list>[selected] {
				background-color: hsl(214, 91%, 89%);
				border-color: hsl(214, 91%, 65%);
			}
			
			.table[hasElementFocus]>list>[lead][selected],list>[selected]:hover {
				background-color: hsl(214, 91%, 87%);
				border-color: hsl(214, 91%, 65%);
			}
			
			.list>* {
				border-left: none;
				border-right: none;
				padding: 0;
			}
			
			.table:focus {
				border: 1px solid;
			}
			
			.table {
				border: 1px solid;
				outline: none;
				overflow: hidden;
				padding: 2px;
			}
			
			tr {
				background: rgba(243, 243, 243, .85) !important;
				color: #222 !important;
				border: 1px #E5E5E5 solid !important;
			}
			
			td,th {
				padding: 5px 3px !important;
				overflow: hidden;
				text-overflow: ellipsis;
			}
			
			th {
				text-align: left;
			}
			
			#parametros {
				border: 1px solid #ccc;
				margin: 5px 0;
			}
		</style>
		<script type="text/javascript">
			addEvent(window, "load", load, false);
			function load() {
				$("#POPUP_SOLICITANTE").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
			}
			function LOOKUP_SOLICITANTE_select(id, desc) {
				document.getElementById("idUsuario").value = id;
				document.getElementById("nomeUsuario").value = desc;
				$("#POPUP_SOLICITANTE").dialog("close");
			}
			function abrePopupUsuario() {
				$("#POPUP_SOLICITANTE").dialog("open");
			}
			function imprimirRelatorio() {
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;
				
				if (dataInicio != "") {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
				}
				if (dataFim != "") {
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				} else {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				}
				if (validaData(dataInicio, dataFim)) {
					document.form.fireEvent("imprimirRelatorio");
		
				}
			}
			
			function filtrar() {
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;
		
				if (dataInicio != "") {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
				}
				if (dataFim != "") {
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				} else {
					if (DateTimeUtil.isValidDate(dataInicio) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datainicio"));
						document.getElementById("dataInicio").value = '';
						return false;
					}
					if (DateTimeUtil.isValidDate(dataFim) == false) {
						alert(i18n_message("citcorpore.comum.validacao.datafim"));
						document.getElementById("dataFim").value = '';
						return false;
					}
				}
				if (validaData(dataInicio, dataFim)) {
					document.form.fireEvent("filtrar");
				}
			}
			
			function validaData(dataInicio, dataFim) {
				var dtInicio = new Date();
				var dtFim = new Date();
				dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
				dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
				if (dtInicio > dtFim) {
					alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
					return false;
				} else
					return true;
			}
			
			function limpar() {
				document.form.clear();
			}
		</script>
	</head>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix" style="letter-spacing: 0; height: 100% !important;">
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<i18n:message key="logs.logs" />
					</h2>
				</div>
				<div style="opacity: 1;" class="block">
					<div id="parametros">
						<div class="columns clearfix">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/logController/logController'>
								<input type="hidden" id='idUsuario' name='idUsuario'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset id="">
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.periodo" /></label>
											<div>
												<table>
													<tbody>
														<tr>
															<td><input type="text"
																class="Format[Date] Valid[Date] datepicker"
																maxlength="10" size="10" id="dataInicio"
																name="dataInicio"></td>
															<td><i18n:message key="citcorpore.comum.a" /></td>
															<td><input type="text"
																class="Format[Date] Valid[Date] datepicker"
																maxlength="10" size="10" id="dataFim" name="dataFim">
															</td>
														</tr>
													</tbody>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="logs.nomeTabela" /></label>
											<div style="height: 47px; padding-top: 10px;">
												<select name="nomeTabela"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset>
											<label><i18n:message key="logs.nomeUsuario" /></label>
											<div style="height: 47px; padding-top: 10px;">
												<input type="text" name="nomeUsuario" id="nomeUsuario" onfocus="abrePopupUsuario();" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
										<button style="margin: 20px !important;" onclick="filtrar();"
											class="light img_icon has_text" name="btnPesquisar"
											type="button" id="btnPesquisar">
											<img src="/citsmart/template_new/images/icons/small/grey/magnifying_glass.png">
											<span><i18n:message key="citcorpore.comum.pesquisar" />
											</span>
										</button>
										<button type='button' name='btnRelatorio' class="light"
											title='Download documento PDF' onclick='imprimirRelatorio();'
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png"
												style="padding-left: 8px;"> <span><i18n:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light" onclick='limpar()' style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<div style="clear: both;"></div>
					<div id="page" align="center" style="display: block; overflow:auto; height:500px; border:0px solid gray"></div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
		<div id="POPUP_SOLICITANTE" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaUsuario'>
								<cit:findField formName='formPesquisaUsuario'
									lockupName='LOOKUP_USUARIO' id='LOOKUP_SOLICITANTE' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>
