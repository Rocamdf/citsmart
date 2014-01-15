<%@ page isErrorPage="true" %>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ page import="br.com.centralit.citcorpore.free.Free" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>CITSmart - ITSM</title>
<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/main.css">
<link rel="stylesheet"
	href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/start/css/default.css" />
<script
	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.min.js"
	type="text/javascript"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/start/js/default.js"></script>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
</head>
<body>
	<!-- INICIO HEADER -->
	<div class="compact" id="header">
		<div class="g-section g-tpl-160 g-split">
			<div class="g-unit g-first" id="header-logo">
				<a
					href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/index/index.load">
					<img alt="CITSmart" src="/citsmart/imagens/logo/logo.png" />
				</a>
			</div>
		</div>
	</div>
	<!-- FIM HEADER -->

	<!-- INICIO CONTENT -->
	<div class="browser-features" id="main">
		<div
			class="compact marquee-side marquee-divider g-section g-tpl-nest g-split"
			id="marquee">
			<div class="g-unit g-first marquee-copy g-col-8">
				<div class="g-content" id="step">

					<!-- STEP 1 -->
					<div class="g-content-inner">
						<h1 class="wrap"><i18n:message key="start.instalacao.bemVindo" /></h1>
						<section>
							<h3><i18n:message key="start.instalacao.termosServico" /></h3>
							<div>
								<iframe class="termos"
									src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/start/termos.html"></iframe>
							</div>
						</section>
						<label><input type="checkbox" id="termos" name="termos" /> <i18n:message key="start.instalacao.aceiteTermos" /></label>
						 <input class="button content first" rel="step-2" type="button" value='<i18n:message key="start.instalacao.aceitarInstalar"/>' />
					</div>
					
					<!-- STEP 2 -->
					<div class="g-content-inner">
						<h1 class="wrap"><i18n:message key="start.instalacao.1passo"/></h1>
						<p><i18n:message key="start.instalacao.dadosConexao"/></p>
						<section>
							<h3><i18n:message key="start.instalacao.informacoesGerais"/></h3>
							<form id="frmConexao" name="frmConexao" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start" method="post">
								<input id="current" name="current" type="hidden" />
								<div class="row">
									<label> 
										<span class="w20"><i18n:message key="start.instalacao.driverConexao"/></span> 
										<select name="driverConexao">
											<!-- <option value="MYSQL"><i18n:message key="start.instalacao.mysql"/></option> -->
											<option value="PostgreSQL"><i18n:message key="start.instalacao.postgresql"/></option>
											<option value="Oracle"><i18n:message key="start.instalacao.oracle"/></option>
											<option value="Microsoft SQL Server"><i18n:message key="start.instalacao.sqlserver"/></option>
										</select>
									</label>
								</div>
								<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-1">
								<input type="button" value="<i18n:message key="start.instalacao.proximo"/>"  id="install" class="button content fire" rel="step-3" onclick="gerarCargaInicial(this);">
								<div id="Throbber" class="throbber"></div>
							</form>
						</section>
					</div>
					
					<!-- STEP 3 -->
					<div class="g-content-inner">
						<h1 class="wrap"><i18n:message key="start.instalacao.2passo"/></h1>
						<p><i18n:message key="start.instalacao.dadosEmpresa"/> </p>
						<section>
							<h3><i18n:message key="start.instalacao.informacoesGerais"/></h3>
							<form id="frmEmpresa" name="frmEmpresa" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start" method="post" onsubmit="javascript:return false;">
								<input id="current" name="current" type="hidden" />
								<div class="row">
									<label> 
										<span class="w20 campoObrigatorio"><i18n:message key="empresa.empresa"/>:</span> 
										<input id="nomeEmpresa" name="nomeEmpresa" type="text" size="60" maxlength="150"> 
									</label>
								</div>
								<div class="row">
									<label> 
										<span class="w20"><i18n:message key="empresa.detalhamento"/>:</span> 
										<textarea id="detalhamento" name="detalhamento" maxlength="2000" style="height: 116px; width: 391px;"></textarea>
									</label>
								</div>
								<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-2">
								<input type="button" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire" rel="step-4" onclick="cadastraEmpresa(this)">
							</form>
						</section>
					</div>

					<!-- STEP 4 -->
					<div class="g-content-inner">
						<h1 class="wrap"><i18n:message key="start.instalacao.3passo"/></h1>
						<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
						<section>
							<h3><i18n:message key="start.instalacao.autenticacao"/></h3>
							<form id="frmLDAP" name="frmLDAP" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start" method="post">
								<input id="current" name="current" type="hidden" />
								<input type="hidden" name="listAtributoLdapSerializado" id="idListAtributoLdapSerializado">
								<div>
									<div class="row">
										<label> 
											<span class="w20"><i18n:message key="start.instalacao.metodoAutenticacao"/></span> 
											<select name="metodoAutenticacao" id="metodoAutenticacao">
												<option value="1"><i18n:message key="start.instalacao.proprio"/></option>
												<option value="2"><i18n:message key="start.instalacao.ldap"/></option>
											</select>
										</label>
									</div>
								</div>
								<div id="LDAP">
									<table class="tableLess" id="tabelaAtributosLdap">
										<thead>
										<tr>
											<th><i18n:message key="start.instalacao.atributo"/></th>
											<th><i18n:message key="start.instalacao.valor"/></th>
										</tr>
										</thead>
										<tbody></tbody>
									</table>
								</div>	
							</form>
						</section>
						<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-3">
						<input type="button" value="<i18n:message key="start.instalacao.testarConexao"/>" class="button" id="testarLDAP" onclick="testaLDAP()">
						<input type="button" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire" rel="step-5" onclick="configuraLDAP(this)">
					</div>
					
					<!-- STEP 5 -->
					<div class="g-content-inner">
						<h1 class="wrap"><i18n:message key="start.instalacao.4passo"/></h1>
						<p><i18n:message key="start.instalacao.autenticacaoEmail"/></p>
						<section>
							<h3><i18n:message key="start.instalacao.informacoesGerais"/></h3>
							<form id="frmEmail" name="frmEmail" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start" method="post">
								<input id="current" name="current" type="hidden" />
								<input type="hidden" name="listAtributoEmailSerializado" id="idAtributoEmailSerializado">
								<table class="tableLess m10" id="tabelaAtributosEmail">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
									
								<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-4">
								<input type="button" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire" rel="step-6" onclick="configuraEmail(this)">
							</form>
						</section>
					</div>
					
					<!-- STEP 6 -->
					<div class="g-content-inner">
						<form id="frmLog" name="frmLog" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start.load" method="post"  rel="step-7" onsubmit="configuraLog(this); return false;">
							<h1 class="wrap"><i18n:message key="start.instalacao.5passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								<h3><i18n:message key="start.instalacao.log"/></h3>
									<input id="current" name="current" type="hidden" />
									<input type="hidden" name="listAtributoLogSerializado" id="idAtributoLogSerializado">
								<table class="tableLess m10" id="tabelaAtributosLog">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-5">
							<input type="submit" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire">
						</form>
					</div>
					
					<!-- STEP 7 -->
					<div class="g-content-inner">
						<form id="frmGed"  rel="step-8" name="frmGed" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start.load" method="post" onsubmit="configuraGed(this); return false;">
							<h1 class="wrap"><i18n:message key="start.instalacao.6passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								<h3><i18n:message key="start.instalacao.ged"/></h3>
									<input id="current" name="current" type="hidden" />
									<input type="hidden" name="listAtributoGedSerializado" id="idAtributoGedSerializado">
									<input id="diretorio" name="diretorio" type="hidden" />
									<input id="campoDiretorio" name="campoDiretorio" type="hidden" />
								<table class="tableLess m10" id="tabelaAtributosGed">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-6">
							<input type="submit" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire">
						</form>
					</div>
					
					<!-- STEP 8 -->
					<div class="g-content-inner">
						<form id="frmSMTP" name="frmSMTP" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start" method="post">
							<h1 class="wrap"><i18n:message key="start.instalacao.7passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								<h3><i18n:message key="start.instalacao.smtp"/></h3>
									<input id="current" name="current" type="hidden" />
									<input type="hidden" name="listAtributoSMTPSerializado" id="idAtributoSMTPSerializado">
								<table class="tableLess m10" id="tabelaAtributosSMTP">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-7">
							<input type="button" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire" rel="step-9" onclick="configuraSMTP(this)">
						</form>
					</div>
					<%
						if(!br.com.citframework.util.Util.isVersionFree(request)) {	
					%>
					<!-- STEP 8 -->
					<div class="g-content-inner">
						<form id="frmIC" name="frmIC" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start.load" method="post" onsubmit="configuraParametrosIC(this); return false;"  rel="step-10">
							<h1 class="wrap"><i18n:message key="start.instalacao.8passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								<h3><i18n:message key="start.instalacao.itemConfiguracao"/></h3>
									<input id="current" name="current" type="hidden" />
									<input type="hidden" name="listAtributoICSerializado" id="idAtributoICSerializado">
								<table class="tableLess m10" id="tabelaAtributosIC">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-8">
							<input type="submit" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire">
						</form>
					</div>
					<!-- STEP 9 -->
					<div class="g-content-inner">
						<form id="frmBase" name="frmBase" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start.load" method="post"  rel="step-11" onsubmit="configuraParametrosBase(this); return false;">
							<h1 class="wrap"><i18n:message key="start.instalacao.9passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								<h3><i18n:message key="start.instalacao.baseConhecimento"/></h3>
									<input id="current" name="current" type="hidden" />
									<input type="hidden" name="listAtributoBaseSerializado" id="idAtributoBaseSerializado">
								<table class="tableLess m10" id="tabelaAtributosBase">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-9">
							<input type="submit" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire">
						</form>
					</div>
					
					<!-- STEP 10 -->
					<div class="g-content-inner">
						<form id="frmGeral" name="frmGeral" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start" method="post">
							<input id="current" name="current" type="hidden" />
							<input type="hidden" name="listAtributoGeraisSerializado" id="idAtributoGeraisSerializado">
							<h1 class="wrap"><i18n:message key="start.instalacao.10passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								
								<h3><i18n:message key="start.instalacao.gerais"/></h3>								
								<table class="tableLess m10" id="tabelaAtributosGerais">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-10">
							<input type="button" id="concluir" value='<i18n:message key="start.instalacao.concluir"/>' class="button content fire" onclick="configuraAplicacao(this)">
							<div id="Throbber_" class="throbber"></div>
						</form>
					</div>
					<%
						} else {	
					%>
					<!-- STEP 9 -->
					<div class="g-content-inner">
						<form id="frmBase" name="frmBase" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start.load" method="post"  rel="step-10" onsubmit="configuraParametrosBase(this); return false;">
							<h1 class="wrap"><i18n:message key="start.instalacao.8passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								<h3><i18n:message key="start.instalacao.baseConhecimento"/></h3>
									<input id="current" name="current" type="hidden" />
									<input type="hidden" name="listAtributoBaseSerializado" id="idAtributoBaseSerializado">
								<table class="tableLess m10" id="tabelaAtributosBase">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-8">
							<input type="submit" value="<i18n:message key="start.instalacao.proximo"/>" class="button content fire">
						</form>
					</div>
					
					<!-- STEP 10 -->
					<div class="g-content-inner">
						<form id="frmGeral" name="frmGeral" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/start/start" method="post">
							<input id="current" name="current" type="hidden" />
							<input type="hidden" name="listAtributoGeraisSerializado" id="idAtributoGeraisSerializado">
							<h1 class="wrap"><i18n:message key="start.instalacao.9passo"/></h1>
							<p><i18n:message key="start.instalacao.configuracoesParametrizacoes"/></p>
							<section>
								
								<h3><i18n:message key="start.instalacao.gerais"/></h3>								
								<table class="tableLess m10" id="tabelaAtributosGerais">
									<thead>
									<tr>
										<th><i18n:message key="start.instalacao.atributo"/></th>
										<th><i18n:message key="start.instalacao.valor"/></th>
									</tr>
									</thead>
									<tbody></tbody>
								</table>
							</section>
							<input type="button" value="<i18n:message key="start.instalacao.anterior"/>" class="button content" rel="step-9">
							<input type="button" id="concluir" value='<i18n:message key="start.instalacao.concluir"/>' class="button content fire" onclick="configuraAplicacao(this)">
							<div id="Throbber_" class="throbber"></div>
						</form>
					</div>
					<%
						}
					%>

				</div>
			</div>
			<div class="g-unit marquee-image g-col-4">
			</div>
		</div>
	</div>
	<!-- FIM CONTENT -->

</body>
</html>
