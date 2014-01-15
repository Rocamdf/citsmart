<%@page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.util.MetaUtil"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoDTO"%>
<%@taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@taglib uri="/tags/i18n" prefix="i18n" %>

<!doctype html public "✰">
<html>
<head>
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
<html lang="en-us" class="no-js">
<!--<![endif]-->

<title>CIT Corpore</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/DataTables/css/demo_table.css">
		
<style>
	table.display tr.even.row_selected td {
		background-color: #B0BED9;
	}
	
	table.display tr.odd.row_selected td {
		background-color: #9FAFD1;
	}
</style>

<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/lookup/jquery.ui.lookup.js"></script>
<script type="text/javascript" language="javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/DataTables/jquery.dataTables.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/layout/jquery.layout-latest.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/formparams/jquery.formparams.js"></script>

<script>

    $(document).ready(function () {
     	layout = $('body').layout({ applyDefaultStyles: true, north: { resizable: false, slidable: false }, west: { resizable: false, slidable: false, closable: false }});

    	layout.sizePane('north', 50); 

    	layout.sizePane('west', 40); 

    	layout.sizePane('south', 200);
    	
    	$( "#tabInfs" ).tabs({
 		   select: function(event, ui) {
 		 	}
 		});    	
    });
    
	function limpar(){
		document.form.clear();	
		document.getElementById('divRetorno').innerHTML = '';	
	}
	
	function carregar(){
		document.getElementById('divRetorno').innerHTML = 'Aguarde... carregando...';
		var flag = document.getElementById("carregarTodos");
		if(flag.checked){
			if(confirm(i18n_message("dataBaseMetaDados.carregaTabelas"))){
				document.form.fireEvent('carregaTodosMetaDados');
			}else{
				document.getElementById('divRetorno').innerHTML = "";
				return false;
			}
		}else{
			document.form.fireEvent('carregaMetaDados');
		}
		
	}
	
	function validaCheck(){
		var flag = document.getElementById("carregarTodos");
		if(flag.checked){
			document.getElementById("nomeTabela").value = "";
			document.getElementById("nomeTabela").disabled = true;
		}else{
			document.getElementById("nomeTabela").disabled = false;
		}
	}
	
	voltar = function(){
		window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
	};
	
</script>
</head>

<body>

<div id='menu'>  
	<%
	request.setAttribute("menustyle", "SHORT");
	%>
	<%@include file="/include/menu_vertical.jsp"%>
</div>

<div class="ui-layout-north"></div>

<div class="ui-layout-east">
	<button type='button' name='btnGravar' class="light"
		onclick='carregar();'><img
		src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
	<span><i18n:message key="citcorpore.comum.carregar"/></span></button>
	<button type='button' name='btnLimpar' class="light"
		onclick='limpar();'><img
		src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
	<span><i18n:message key="dinamicview.limpardados"/></span></button>
	<button type="button" class="light img_icon has_text " style='text-align: right;' onclick="voltar()" title="<i18n:message key="citcorpore.comum.voltar" />">
		<img border="0" title="<i18n:message key="citcorpore.comum.voltar" />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/back.png" /><span ><i18n:message key="citcorpore.comum.voltar" /></span>
	</button>
</div>

<div class="ui-layout-west"></div>

<div class="ui-layout-center">
	<!-- Conteudo do Centro -->
	<div id='tabs' class="box grid_16 tabs" style='width: 97%'>
		<ul class="tab_header clearfix">
			<li>
				<a href="#tabs-1">
					<i18n:message key="carregar.meta_dados"/>
				</a>
			</li>
		</ul>
		<a href="#" class="toggle">&nbsp;</a>
		<div class="toggle_container">
			<div id="tabs-1" class="block">
				<div class="section">
					
					<!-- INICIO DO FORM PRINCIPAL -->
					<form name='form' id='form' onsubmit='javascript:return false;' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dataBaseMetaDados/dataBaseMetaDados'>
						<label><i18n:message key="carregar.meta_dados.nomeTabela"/></label>
						<input type='text' name='nomeTabela' size='40' maxlength="40"/><br>
						<div style="padding-top: 10px;"><label style="padding-top: 10px;"><input type='checkbox' name='carregarTodos' id='carregarTodos' onchange="validaCheck();"/><i18n:message key="carregar.meta_dados.todos"/><br></label></div>
						<div id='divRetorno'></div>
					</form>
					<!-- FIM DO FORM PRINCIPAL -->
					
				</div>
			</div>
		</div>
	</div>
	<!-- Fim Conteudo do Centro -->
</div>

<div class="ui-layout-south"></div>

<!-- Fim da Pagina de Conteudo -->

</body>
</html>
