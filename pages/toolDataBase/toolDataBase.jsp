<%@page import="java.util.HashMap"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%
	String sgbd = "";
	String banco = "";
	String schema = "";
	HashMap<Integer, String> dadosSGBD = new HashMap<Integer, String>();
	dadosSGBD = (HashMap)request.getSession().getAttribute("dadosSGBD");
	if(dadosSGBD != null){
		sgbd = dadosSGBD.get(1);
		banco = dadosSGBD.get(2);
		schema = dadosSGBD.get(3);
	}

%>
<!doctype html public "✰">
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
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<link rel="stylesheet" type="text/css" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/toolDataBase/toolDataBase.css" />	
	<script>
	    var popup;
	    addEvent(window, "load", load, false);
	    function load(){		
		popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");

	    }
	    $(function() {
			$("#POPUP_ACAO").dialog({
				autoOpen : false,
				width : 300,
				height : 200,
				modal : true
			});			
		});	 
		var tabela = "";
		acao = function(valor){
			$("#POPUP_ACAO").dialog("open");
			tabela = valor;
		} 
		acaoTabela = function(tipo){
			document.form.tipoAcao.value = tipo;
			document.form.tabela.value = tabela;
			document.form.fireEvent("executaMontaSQL");
		} 
		executaScript = function(){
			document.form.tipoAcao.value = "";
			document.form.fireEvent("executaSQL");
		}
		$(function() {
			$(".ui-widget-overlay").click(function() {
				$("#POPUP_ACAO").dialog("close");
			});
		});

		acaoDrop = function(){
			if(confirm(i18n_message("tooldatabase.alerta.ExclusaoTabela")))
				acaoTabela('drop');
			else
				$("#POPUP_ACAO").dialog("close");				
		}

		createTable = function(){
			document.form.tipoAcao.value = "createTable";
			document.form.tabela.value = "";
			document.form.fireEvent("executaMontaSQL");
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
	<body >
		<div id="wrapper" >
			<%if (iframe == null) {%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%}%>
	
			<!-- Conteudo -->
			<div id="main_container" class="main_container" >
				<%if (iframe == null) {%>
				<%@include file="/include/menu_horizontal.jsp"%>
				<%}%>
				<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/toolDataBase/toolDataBase'>
					<input type="hidden" name="tabela" id="tabela">
					<input type="hidden" name="tipoAcao" id="tipoAcao">
					<div id="corpoPrincipal" >
						<label class="infoBanco"><b><i18n:message key="tooldatabase.sgbd"/>: <%=sgbd%></b></label>
						<label class="infoBanco"><b><i18n:message key="tooldatabase.banco"/>: <%=banco%></b></label>
						<label class="infoBanco"><b><i18n:message key="tooldatabase.url"/>: <%=schema%></b></label>						
						<div id="estTabelas"  >
							
						</div>
						<div id="corpoExec" >
							<div id="inputSQL" >
								<label><b><i18n:message key="tooldatabase.consoleScriptsSQL"/></b></label>
								<textarea id="strExec" name="strExec" >
								
								</textarea>
								<br />
								<label><input type="button" name="executar" id="executar" onclick="executaScript()" value="<i18n:message key="tooldatabase.Executar"/>" /></label>
								<!-- <label><input type="button" name="commit" id="commit" value="COMMIT" /></label> -->
							</div>							
							<div id="outputSQL" >
								<label><b><i18n:message key="tooldatabase.resultadosScriptSQL"/></b></label><label><b><i18n:message key="tooldatabase.quantRows"/>: <input type="text" id="quantRows" name="quantRows" value="1000" /> </b></label>
							</div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
					<div id="POPUP_ACAO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
						<div class="box grid_16 tabs" >
							<div class="toggle_container" >
								<br />
								<a class="linkAcao" href="#" onclick="acaoDrop()" ><i18n:message key="tooldatabase.deletarTabela"/></a>
								<br />																																
								<a class="linkAcao" href="#" onclick="acaoTabela('addColumn');" ><i18n:message key="tooldatabase.adicionarCampos"/></a>
								<br />							
								<a class="linkAcao" href="#" onclick="acaoTabela('list');" ><i18n:message key="tooldatabase.listarCampos"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('insert');" ><i18n:message key="tooldatabase.inserirDados"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('update');" ><i18n:message key="tooldatabase.atualizarDados"/></a>
								<br />
								<a class="linkAcao" href="#" onclick="acaoTabela('del');" ><i18n:message key="tooldatabase.deletarDados"/></a>
							</div>
						</div>
					</div>	
				</form>				
			</div>
		</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>   