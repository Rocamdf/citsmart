<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<!doctype html public "">
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
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<style type="text/css">
.table {
	border-left: 1px solid #ddd;
}

.table th {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-left: none;
	background: #eee;
}

.table td {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-top: none;
	border-left: none;
}
	div.main_container .box {
			margin-top: -0.3em!important;

		}
</style>

<script><!--
	var temporizador;
	addEvent(window, "load", load, false);

	
	function imprimirRelatorioOrdemServicoUst(){	
		 if(document.getElementById("ano").value != "" && document.getElementById("idContrato").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioOrdemServicoUst");
		}else {
			if(document.getElementById("ano").value == ""){
				alert(i18n_message("citcorpore.comum.selecioneAno"));
				return;
			}
			if(document.getElementById("idContrato").value ==""){
				alert(i18n_message("citcorpore.comum.selecioneContrato"));
				return;
			}
		}  
	}
	
	function imprimirRelatorioOrdemServicoUstXls(){	
		 if(document.getElementById("ano").value != "" && document.getElementById("idContrato").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("imprimirRelatorioOrdemServicoUstXls");
		}else {
			if(document.getElementById("ano").value == ""){
				alert(i18n_message("citcorpore.comum.selecioneAno"));
				return;
			}
			if(document.getElementById("idContrato").value ==""){
				alert(i18n_message("citcorpore.comum.selecioneContrato"));
				return;
			}
			
		}  
	}
	
	function limpar(){
		document.form.clear();
	}

--></script>
</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="relatorioUtilizacaoUSTs.relatorioUtilizacaoUSTs" /></a></li>
				</ul>
				<div class="toggle_container">
					<div class="block" >
						<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/relatorioOrdemServicoUst/relatorioOrdemServicoUst'>
								<div class="columns clearfix">
									<div class="col_25">
										<fieldset style="height: 53px">
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.ano" /></label>
											<div>
												<select class="Valid[Required] Description[ano]" id="ano"  name="ano"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset style="height: 53px">
											<label class="campoObrigatorio"><i18n:message key="contrato.contrato" /></label>
											<div>
												<select class="Valid[Required] Description[contrato]" id="idContrato"  name="idContrato"></select   >
											</div>
										</fieldset>
									</div>
								</div>
								
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioOrdemServicoUst()"
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioOrdemServicoUstXls()"
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png">
											<span><i18n:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limpar()' style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
										
									</fieldset>
								</div>
							</form>
							</div>
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