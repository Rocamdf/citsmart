<%@page import="br.com.centralit.citcorpore.comm.server.IPAddress"%>
<%@page import="br.com.centralit.citcorpore.batch.ThreadValidaFaixaIP"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.batch.MonitoraAtivosDiscovery"%>
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
		window.setTimeout('processsa()',1000);
	}
	function processsa() {
		document.form.fireEvent('mostraInfo');
		window.setTimeout('processsa()',5000);
	}

	function LOOKUP_COMANDO_select(id, desc) {
		document.form.restore({
			id :id});
	}
	function submeteIP(){
		document.form.fireEvent('submeteIP');
	}
	function forcarLacoInv(){
		document.form.fireEvent('forcarLacoInv');
	}	
	function inventarioAgora(ip){
		document.form.ip.value = ip;
		document.form.fireEvent('inventarioAgora');
	}
	function refreshIPs(){
		document.getElementById('divInfo').innerHTML = 'Aguarde...';
		document.form.fireEvent('refreshIPs');
	}
	function gerarFaixaIPs(){
		$("#faixaIps").modal("show");		
	}
	function submeteGerarFaixaIPs(){
		document.getElementById('divResultado').innerHTML = 'Aguarde...';
		document.formGerarFaixa.fireEvent('gerarFaixaIPs');
	}
	function adicionarIPSAtivosLista(){
		document.form.ip.value = document.formGerarFaixa.txtIPSAtivos.value;
		document.form.fireEvent('submeteIP');
	}
	function adicionarTodosIPSLista(){
		document.form.ip.value = document.formGerarFaixa.txtIPSTodos.value;
		document.form.fireEvent('submeteIP');
	}
	function fazPing(ip){
		document.form.ip.value = ip;
		document.form.fireEvent('fazPing');
	}
	function refresh(){
		window.location = '<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/mostraStatusInventario/mostraStatusInventario.load';
	}
	function alterarThreadInv(){
		document.formAlteraValor.tipoDado.value = 'INV';
		$("#alteraValor").modal("show");
	}
	function alterarThreadDisc(){
		document.formAlteraValor.tipoDado.value = 'DIS';
		$("#alteraValor").modal("show");
	}
	function alterarPingTimeout(){
		document.formAlteraValor.tipoDado.value = 'PING';
		$("#alteraValor").modal("show");		
	}
	function submeteAlteracaoValor(){
		document.formAlteraValor.fireEvent('alteraValor');
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
</head>
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
				<h2>
					<i18n:message key="mostrarStatusInventario.titulo"/>
				</h2>
				<form name='form' method="post" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/mostraStatusInventario/mostraStatusInventario'>
					<table width="100%">
						<%if (!CITCorporeUtil.START_MODE_DISCOVERY){%>
						<tr>
							<td colspan="4">
								<font color='red'><i18n:message key="mostrarStatusInventario.atencaOProcesso"/><u><i18n:message key="mostrarStatusInventario.discovery"/></u><i18n:message key="mostrarStatusInventario.estaDesativado"/></font>
							</td>
						</tr>						
						<%}%>
						<%if (!CITCorporeUtil.START_MODE_INVENTORY){%>
						<tr>
							<td colspan="4">
								<font color='red'><i18n:message key="mostrarStatusInventario.atencaOProcesso"/><u><i18n:message key="mostrarStatusInventario.inventario"/></u><i18n:message key="mostrarStatusInventario.estaDesativado"/></font>
							</td>
						</tr>						
						<%}%>						
						<tr>
							<td colspan="4">
								<i18n:message key="mostrarStatusInventario.informeOsIpsAbaixo"/>
							</td>
						</tr>					
						<tr>
							<td>
								<textarea rows="5" cols="50" name='ip'></textarea>
							</td>
							<td>
								<div>
									<button onclick='submeteIP()' type='button'><i18n:message key="mostrarStatusInventario.realizarInvetarioDosIps"/></button><br>
									<button onclick='forcarLacoInv()' type='button'><i18n:message key="mostrarStatusInventario.forcarNovaRodadaDoInventario"/></button><br>
								</div>
							</td>
							<td>
								<div>
									<button onclick='refreshIPs()' type='button'><i18n:message key="mostrarStatusInventario.refreshDaListDeIps"/></button><br>
									<button onclick='gerarFaixaIPs()' type='button'><i18n:message key="mostrarStatusInventario.GerarFaixaDeIps"/></button><br>
								</div>
							</td>							
							<td>
								<div id="divInfo2">
								</div>							
							</td>						
						</tr>
						<tr>
							<td colspan="4">
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td>
											<i18n:message key="mostrarStatusInventario.numeroThreadsParaProcessoInvetario"/><b><%=MonitoraAtivosDiscovery.NUMERO_THREADS%></b>
										</td>
										<td>
											<button type='button' onclick='alterarThreadInv()'><i18n:message key="mostrarStatusInventario.alterar"/></button>
										</td>
										<td>							
											<i18n:message key="mostrarStatusInventario.numeroThreadsDiscovery"/><b><%=ThreadValidaFaixaIP.NUMERO_THREADS%></b>
										</td>
										<td>
											<button type='button' onclick='alterarThreadDisc()'><i18n:message key="mostrarStatusInventario.alterar"/></button>
										</td>
										<td>							
											<i18n:message key="mostrarStatusInventario.icmpTimeout"/><b><%=IPAddress.PING_TIMEOUT%></b>
										</td>
										<td>
											<button type='button' onclick='alterarPingTimeout()'><i18n:message key="mostrarStatusInventario.alterar"/></button>
										</td>																				
									</tr>
								</table>
							</td>
						</tr>					
						<tr>
							<td colspan="4">
								<div id="divInfo">
									<%=MonitoraAtivosDiscovery.MENSAGEM_PROCESSAMENTO_COMPL%>
								</div>
							</td>
						</tr>
					</table>
				</form>				
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	
<div class="modal hide fade in" id="faixaIps" aria-hidden="false" data-backdrop="static" data-keyboard="false">
	<!-- Modal heading -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
	</div>
	<!-- // Modal heading END -->
	<!-- Modal body -->
	<div class="modal-body" >
		<form name='formGerarFaixa' method="post" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/mostraStatusInventario/mostraStatusInventario'>
		<div id="divParametros">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td colspan="4">
						<i18n:message key="mostrarStatusInventario.informeAsFaixasAbaixo"/>
					</td>
				</tr>					
				<tr>
					<td colspan="4">
						<textarea rows="2" cols="50" name='ipFaixaGerar'></textarea>
					</td>
				</tr>
				<tr>
					<td>
						<input name='validarIP' type='checkbox' value='S'/><i18n:message key="mostrarStatusInventario.realizarChecknoIp"/>
					</td>
					<td>
						<input name='nativePing' type='checkbox' value='S'/><i18n:message key="mostrarStatusInventario.realizarPingNativo"/>
					</td>					
					<td>
						<i18n:message key="mostrarStatusInventario.threads"/>
					</td>	
					<td>
						<input name='numThreads' type='text' value='10'/>
					</td>													
					<td>
						<button type='button' onclick='submeteGerarFaixaIPs()'><i18n:message key="mostrarStatusInventario.gerar"/></button>
					</td>
				</tr>				
			</table>			
		</div>
		<div id="divResultado" style='height: 150px;'>
			
		</div>
		</form>
	</div>
	<!-- // Modal body END -->
	<!-- Modal footer -->
	<div class="modal-footer">
		<a id="btFechar" href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
	</div>
</div>

<div class="modal hide fade in" id="alteraValor" aria-hidden="false" data-backdrop="static" data-keyboard="false">
	<!-- Modal heading -->
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true">X</button>
	</div>
	<!-- // Modal heading END -->
	<!-- Modal body -->
	<div class="modal-body" >
		<form name='formAlteraValor' method="post" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/mostraStatusInventario/mostraStatusInventario'>
		<input type='hidden' name='tipoDado'/>
		<div id="divParametros">
			<table width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td>
						<i18n:message key="mostrarStatusInventario.valor"/>
					</td>
				</tr>					
				<tr>
					<td>
						<input type='text' name='valor' maxlength="5" size="5"/>
					</td>
				</tr>
				<tr>
					<td colspan="2">
						<button type='button' onclick='submeteAlteracaoValor()'><i18n:message key="mostrarStatusInventario.alterar"/></button>
					</td>
				</tr>				
			</table>			
		</div>
		</form>
	</div>
	<!-- // Modal body END -->
	<!-- Modal footer -->
	<div class="modal-footer">
		<a id="btFechar" href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
	</div>
</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>