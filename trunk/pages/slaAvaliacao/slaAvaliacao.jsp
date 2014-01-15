<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!doctype html>
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
	
%>
<%@include file="/include/security/security.jsp" %>
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
<script type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>


<script>

var objTab = null;
addEvent(window, "load", load, false);
function load(){
	document.form.afterRestore = function () {
		$('.tabs').tabs('select', 0);
	}
}

function LOOKUP_SISTEMAOPERACIONAL_select(id,desc){
	document.form.restore({id:id});
}
	
	function gerarInformacoes(){
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.datainvalida"));
		 	document.getElementById("dataInicio").value = '';
		 	return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
			 document.getElementById("dataFim").value = '';
			return false;					
		}
		if(validaData(dataInicio,dataFim)){
			document.getElementById('divInfo').innerHTML = '<b>'+i18n_message("citcorpore.comum.aguarde")+'</b>';
			document.form.fireEvent('avalia');
		}
	}
	
	function validaData(dataInicio, dataFim) {
		
		var dtInicio = new Date();
		var dtFim = new Date();
		
		dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
		dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
		
		if (dtInicio > dtFim){
			alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
			return false;
		}else
			return true;
	}
</script>

<%
//se for chamado por iframe deixa apenas a parte de cadastro da página
if(iframe != null){%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
	width: 100%;
	height: 0px!important;
	top: 5%!important;
}
#divInfo{
	overflow: auto!important;
}
</style>
<%}%>

</head>

<body>
	<div id="wrapper">
	<%if(iframe == null){%>
		<%@include file="/include/menu_vertical.jsp"%>
	<%}%>
<!-- Conteudo -->
	<div id="main_container">
	<%if(iframe == null){%>
		<%@include file="/include/menu_horizontal.jsp"%>
	<%}%>

		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix" style='height: 0px!important;top: 5%!important;'>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="menu.nome.avaliarSLA" /></a></li>
				</ul>
				<div class="toggle_container">
					<div class="block" >
						<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/slaAvaliacao/slaAvaliacao'>
								<fieldset>
									<legend><i18n:message key="citcorpore.comum.filtros" /></legend>
									<table>
										<tr>
											<td>
												<i18n:message key="avaliacaocontrato.periodo" /><font color="red">*</font>
											</td>
											<td>
												<input type='text' name='dataInicio' id ='dataInicio'  size="10" maxlength="10" class="Valid[Required,Date] Description[avaliacao.fornecedor.dataInicio] Format[Date] datepicker" />									
											</td>
											<td>
												<input type='text' name='dataFim' id = 'dataFim' size="10" maxlength="10" class="Valid[Required,Date] Description[avaliacao.fornecedor.dataFim] Format[Date] datepicker" />									
											</td>
											<td>
												&nbsp;
											</td>
											<td style='vertical-align: top;'>
												<button type="button" onclick='gerarInformacoes()'>
													<i18n:message key="citcorpore.comum.gerarInformacoes" />
												</button>
											</td>							
										</tr>
									</table>
								</fieldset>			
								<div id='divInfo' style="overflow: auto!important;">
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
		</div>
		<!-- Fim da Pagina de Conteudo -->
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>