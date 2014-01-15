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
	
</script>

<%
//se for chamado por iframe deixa apenas a parte de cadastro da página
if(iframe != null){%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
	width: 100%;
}
</style>
<%}%>

</head>

<body>
	<script>
	function gerarInformacoes(){
		if (document.form.validate()){
			document.form.fireEvent('geraInformacoes');
		}
	}
	</script>
	<div id="wrapper">
	<%if(iframe == null){%>
		<%@include file="/include/menu_vertical.jsp"%>
	<%}%>
<!-- Conteudo -->
	<div id="main_container">
	<%if(iframe == null){%>
		<%@include file="/include/menu_horizontal.jsp"%>
	<%}%>

			<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/recursoAvaliacao/recursoAvaliacao'>
				<div>
					<fieldset>
						<legend><i18n:message key='citcorpore.comum.filtros'/></legend>
						<table>
							<tr>
								<td>
									<label  class="campoObrigatorio"><i18n:message key="citcorpore.comum.periodo" /></label>
								</td>
								<td>
									<input type='text' name='dataInicio' size="10" maxlength="10" class="Valid[Required,Date] Description[visao.dataDeInicio] Format[Date] datepicker" />									
								</td>
								<td>
									<input type='text' name='dataFim' size="10" maxlength="10" class="Valid[Required,Date] Description[avaliacao.fornecedor.dataFim] Format[Date] datepicker" />									
								</td>
								<td>
									&nbsp;
								</td>
							</tr>
							<tr>
								<td style='vertical-align: top;'>
									<label><i18n:message key="controle.grupo"/>:</label>
								</td>
								<td colspan="2" style='vertical-align: top;'>
									<select name='idGrupoRecurso' class="Description[Grupo]">
									</select>									
								</td>
								<td style='vertical-align: top;'>
									<button type="button" onclick='gerarInformacoes()'>
										<i18n:message key="citcorpore.comum.gerarInformacoes"/>
									</button>
								</td>								
							</tr>
						</table>
					</fieldset>
				</div>
				<div id='divInfo'>
				</div>
			</form>

		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>