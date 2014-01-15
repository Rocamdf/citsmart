<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.citframework.util.UtilFormatacao"%>

<!doctype html>
<html>
<head>
	<%
		Collection listaServicos = (Collection) request.getAttribute("listaServicos");
	
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
	
	<style type="text/css">
	
		td{
			border: 1px solid #f5f5f5;
			cursor: pointer;
			padding: 0.5em;
		 	font-weight: bold;
		 	font-family: arial;
		 	font-size: 12px;
		 	background:#f2f2f2; 
		}
		
		.linhaSubtituloGrid{
			padding: 0px;
			font-size:13px;
		    box-shadow: 0 0 2px 0 #DDDDDD inset;
		    margin-top: 3px;
		    background-color: #F3F3F3;
		    border: 1px solid #B3B3B3;
		}
	
	</style>
	
	<script type="text/javascript">
		aguarde = function(){
			JANELA_AGUARDE_MENU.show();
		}
		
		fechar_aguarde = function(){
	    	JANELA_AGUARDE_MENU.hide();
		}
				
		function pesquisa(){
			aguarde();
			document.form.fireEvent("montaGraficoGeraDesempenho");
		}
		
	</script>
	<%
	//se for chamado por iframe deixa apenas a parte de cadastro da página
	if(iframe != null){%>
		<style type="text/css">
			div#main_container {
				margin: 10px 10px 10px 10px;
			}
		</style>
	<%}%>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if(iframe == null){%>
			<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
		<%if(iframe == null){%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>
		<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/avaliacaoPorFornecedor/avaliacaoPorFornecedor'>
			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="avaliacao.fornecedor.servico"/>
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<fieldset>
					<div class="col_30" style="width: 30% !important; float: left;">
						<label><i18n:message key="avaliacao.fornecedor.pesquisa"/></label>
						<select name="comboFornecedor" id="comboFornecedor"></select>
					</div>
					<div class="col_30" style="width: 30%; float: left; margin-top: 20px;">
						<button type='button' name='btnpesquisa' class="light" onclick='pesquisa();'>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
							<span><i18n:message key="citcorpore.comum.pesquisa" /></span>
						</button>
					</div>
				</fieldset>
				<div id="tableGrafico"></div>
				<div id="tableResult"></div>
			</div>
		</form>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>