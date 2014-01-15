<%@page import="br.com.centralit.citcorpore.comm.server.IPAddress"%>
<%@page import="java.io.File"%>
<%@page import="br.com.centralit.citcorpore.versao.Versao"%>
<%@page import="br.com.centralit.citcorpore.util.CITCorporeUtil"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "âœ°">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);

	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<!--<![endif]-->
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
</script>

<%//se for chamado por iframe deixa apenas a parte de cadastro da página
if (iframe != null) {%>
<style>
	div#main_container {
		margin: 10px 10px 10px 10px;
		width: 100%;
	}
</style>
<%}%>
<style type="text/css">
.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	  overflow: hidden;
	  margin: 10px 0 0 10px ;
	}
	
	.tableLess tbody {
	  background: transparent  !important;
	}
	
	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}
	
	.tableLess tr th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}
	
	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}
	
	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9 ;
	  /* cursor: pointer; */
	}
	
	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}
	
	.tableLess td{
		border: 1px solid #BBB  !important;
		padding: 6px 10px  !important;
		max-width: 250px;
		padding: 6px 10px !important;
		text-overflow: ellipsis;
	}
</style>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>
			<div class="flat_area grid_16">
				<h2><i18n:message key="citcorpore.comum.configuracoesDeAmbiente" /></h2>
			</div>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div class="col_50">
						<fieldset>
							<label>
								<i18n:message key="citcorpore.comum.versaoCitsmart" />
							</label>
							<div style="height: 20px;">
								<%=Versao.getDataAndVersao()%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								<i18n:message key="citcorpore.comum.versaoJava" />
							</label>
							<div style="height: 20px;">
								<%=System.getProperty("java.version")%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								<i18n:message key="citcorpore.comum.SGBD" />
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.SGBD_PRINCIPAL%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								<i18n:message key="citcorpore.comum.driverBanco" />
							</label>
							<div style="height: 20px;">
								<%out.println( request.getAttribute("versao_driver_jdbc"));%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_DISCOVERY
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_DISCOVERY%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_INVENTORY
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_INVENTORY%>
							</div>
						</fieldset>
					</div>	
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_ITSM
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_ITSM%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								START_MODE_RULES
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.START_MODE_RULES%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								JDBC_ALIAS_INVENTORY
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.JDBC_ALIAS_INVENTORY%>
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label>
								File Config
							</label>
							<div style="height: 20px;">
								<%=CITCorporeUtil.caminho_real_config_file%>
								<%
								File f = new File(CITCorporeUtil.caminho_real_config_file);
								if (f.exists()){
									out.println(" (<b>File Exists</b>)");
								}else{
									out.println(" (<b>File NOT Exists</b>)");
								}
								%>
							</div>
						</fieldset>
					</div>	
					<div class="col_50">
						<fieldset>
							<label>
								NATIVE_PING
							</label>
							<div style="height: 20px;">
								<%=IPAddress.NATIVE_PING%>
							</div>
						</fieldset>
					</div>																			
					<div class="col_50">
						<fieldset>
							<label>
								<i18n:message key="citcorpore.comum.sistemaOperacional" />
							</label>
							<div style="height: 20px;">
								<%=System.getProperty("os.name")%>
							</div>
						</fieldset>
					</div>
					<form name='formLogJboss' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/logJboss/logJboss.load'>
						<div class="col_50">
							<fieldset>
								<label>
									<i18n:message key="citcorpore.comum.logDoJboss" />
								</label>
								<div style="height: 20px;">
									<button type='button' class="light" onclick='document.formLogJboss.submit();'>
										<span><i18n:message key="downloadagente.download" /></span>
									</button>
								</div>
							</fieldset>
						</div>
					</form>
					<form name='formGetConfig' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/getFileConfig/getFileConfig.load'>
						<div class="col_50">
							<fieldset>
								<label>
									Load Config (Process File)
								</label>
								<div style="height: 20px;">
									<button type='button' class="light" onclick='document.formGetConfig.submit();'>
										<span>Process</span>
									</button>
								</div>
							</fieldset>
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