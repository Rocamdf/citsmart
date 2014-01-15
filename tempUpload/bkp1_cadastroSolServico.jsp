<html>
<head>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.util.CitCorporeConstantes" %>
<%@ page import="br.com.centralit.citcorpore.negocio.ParametroCorporeService" %>
<%@ page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO" %>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String id = request.getParameter("id");
	String strRegistrosExecucao = (String) request
			.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}
	
	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil
			.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO,"");

	String tarefaAssociada = (String) request
			.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null) {
		tarefaAssociada = "N";
	} 
	String iframe = "";
	iframe = request.getParameter("iframe");
%>

<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>

<script src="../../novoLayout/common/include/js/internacionalizar.js"></script>
<script src="../../novoLayout/common/include/js/cadastroSolServico.js"></script>
<script src="../../novoLayout/common/include/js/jquery.limit-1.2.source.js"></script>
<script src="../../novoLayout/common/include/js/canvas.js"></script>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/cadastroSolServico2.css"/>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
<script type="text/javascript" src="../../novoLayout/common/include/js/jquery.slimscroll.min.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js?nocache=<%=new java.util.Date().toString()%>"></script>

<script type="text/javascript">

		/* ============================================================
		 			ATENÇÃO
		 
		 NO JSP DEVE CONTER APENAS SCRIPTS QUE NECESSITAM DE SCRIPTLETS  
		
		================================================================*/

function gravarEContinuar() {
	document.form.acaoFluxo.value = '<%=Enumerados.ACAO_INICIAR%>';
	gravar();
}

function gravarEFinalizar() {
	document.form.acaoFluxo.value = '<%=Enumerados.ACAO_EXECUTAR%>';
	gravar();
}

function gravarsddd() {
     
	if(document.form.enviaEmailCriacao.disabled==true)
	{
		document.form.enviaEmailCriacao.disabled=false;
	}
	if(document.form.enviaEmailFinalizacao.disabled==true)
	{
		document.form.enviaEmailFinalizacao.disabled=false;
	}
	if(document.form.enviaEmailAcoes.disabled==true)
	{
		document.form.enviaEmailAcoes.disabled=false;
	}

	var informacoesComplementares_serialize = '';
	try{
		informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
	}catch(e){}
	
	<%if (!br.com.citframework.util.Util.isVersionFree(request)) {%>
		var objs = HTMLUtils.getObjectsByTableId('tblProblema');
		if (objs != null) {
			document.form.colItensProblema_Serialize.value = ObjectUtils.serializeObjects(objs);
		}
		
		var objsMudanca = HTMLUtils.getObjectsByTableId('tblMudanca');
		if (objsMudanca != null) {
			document.form.colItensMudanca_Serialize.value = ObjectUtils.serializeObjects(objsMudanca);
		}
		
		var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
		if (objsIC != null) {
			document.form.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
			
		//var objsBaseConhecimento = HTMLUtils.getObjectsByTableId('tblBaseConhecimento');
		//document.form.colConhecimentoSolicitacao_Serialize.value = ObjectUtils.serializeObjects(objsBaseConhecimento);
		}
	<%}%>			
	
	/* JANELA_AGUARDE_MENU.show(); */
	document.form.urgencia.disabled = false;
	document.form.impacto.disabled = false;
	document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
	if(document.getElementById('flagGrupo').value == 0){
		document.form.save();
	}else{
	if (document.getElementById("idGrupoAtual").value == ''){
		//var e = document.getElementById("idGrupoAtual");
		//var grupoAtual = e.options[e.selectedIndex].text;
		if (confirm('<i18n:message key="solicitacaoServico.grupoAtualVazio" />')){
		document.form.save(); 
		}else{
			/* JANELA_AGUARDE_MENU.hide(); */
			return;	
		}
	}else{
		document.form.save(); 
	}
}
}

function gravar() {
	//testantoass(); 
	document.form.save(); 
}


</script>

<title>CITSMart</title>


</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body onload="">
<!-- <a class="password" href="#modal_novaSolicitacao" data-toggle="modal" id='modals-bootbox-confirm'>Nova solicitacao</a> -->
<div class="wrapper" >
		<!-- Modal heading -->
		<div class="widget-body">
			<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
					<!-- <h3>Solicitação</h3> -->
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
			<!-- <div class="modal-body"> -->
				<div class="container-fluid fixed menu-right">
					<!-- <div id="wrapper"> -->
					<div id="content" >
					
					<div id="menu" class="hidden-print">
						<div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 800px;">
							<div class="slim-scroll" data-scroll-height="800px" style="overflow: hidden; width: auto; height: 800px;">
							<!-- Regular Size Menu -->
							<ul class="menu-0">
								<li class="hasSubmenu glyphicons warning_sign " id='divMenuSolicitacao' >
									<a  href="#modal_listaSolicitacoesMesmoSolicitante" data-toggle="modal" data-target="#modal_listaSolicitacoesMesmoSolicitante" class="collapsed" onclick="carregaSolicitacoesAbertasParaMesmoSolicitante()"><i></i><span><i18n:message key="gerenciaservico.solicitacoes" /></span></a>
									<ul class="collapse" id="menu_components" style="height: 0px;">
									</ul>
									<span class="count" id='countSolicitacoesAbertasSolicitante'></span>
								</li>
								<li class="hasSubmenu glyphicons list " id='divMenuScript'>
									<a  href="#modal_script" data-toggle="modal" data-target="#modal_script" class="collapsed" onclick="carregaScript()"><i></i><span><i18n:message key="solicitacaoServico.script" /></span></a>
									<ul class="collapse" id="menu_components" style="height: 0px;">
									</ul>
									<span class="count" id='countScript'></span>
								</li>
								<li class="hasSubmenu">
									<a  class="glyphicons paperclip" onclick="abrirModalAnexo();" data-toggle="modal"><i></i><span><i18n:message key="citcorpore.comum.anexos" />(s)</span></a>
									<ul class="collapse" id="menu_examples">
									</ul>
									<span class="count" id='countAnexo'>2</span>
								</li>
								<li class="glyphicons calendar"><a href="#modal_agenda" data-toggle="modal" data-target="#modal_agenda"><i></i><span><i18n:message key="citcorpore.comum.agenta" /></span></a></li>
								<li class="hasSubmenu">
									<a  class="glyphicons notes" onclick="abreModalOcorrencia()"><i></i><span><i18n:message key="solicitacaoServico.ocorrencia" /></span></a>
									<ul class="collapse" id="menu_examples">
									</ul>
									<!-- <span class="count"></span> -->
								</li>
								<li class="hasSubmenu">
									<a  class="glyphicons bomb" href="#modal_incidentesRelacionados" data-toggle="modal" data-target="#modal_incidentesRelacionados" id='btIncidentesRelacionados'><i></i><span><i18n:message key="gerenciaservico.incidentesrelacionados" /></span></a>
									<ul class="collapse" id="menu_examples">
									</ul>
									<!-- <span class="count"></span> -->
								</li>
								<li class="hasSubmenu">
									<a  class="glyphicons circle_plus"  onclick="modalCadastroSolicitacaoServico()"><i></i><span><i18n:message key="gerenciaservico.novasolicitacao" /></span></a>
									<ul class="collapse" id="menu_examples">
									</ul>
									<!-- <span class="count"></span> -->
								</li>
							</ul>
							<div class="clearfix"></div>
							
							<ul class="menu-1">
								<li class="hasSubmenu active">
									<a class="glyphicons " href="#menu-recent-stats" data-toggle="collapse"><i></i><span><i18n:message key="solicitacaoServico.processos" /></span></a>
								<%-- 	<%if (br.com.citframework.util.Util.isVersionFree(request)) {%> --%>
									<ul class="collapse in" id="menu-recent-stats">
										<li><a class="glyphicons circle_exclamation_mark" href="#modal_problema" data-toggle="modal" data-target="#modal_problema"><i></i><span><i18n:message key="itemConfiguracaoTree.problema" /></span></a></li>
										<li><a class="glyphicons  edit" href="#modal_mudanca" data-toggle="modal" data-target="#modal_mudanca"><i></i><span><i18n:message key="requisicaMudanca.mudanca" /></span></a></li>
										<li><a class="glyphicons  display" href="#modal_itemConfiguracao" data-toggle="modal" data-target="#modal_itemConfiguracao"><i></i><span><i18n:message key="itemConfiguracaoTree.itensConfiguracao" /></span></a></li>
										<li><a class="glyphicons  database_lock" href="#modal_baseConhecimento" data-toggle="modal" data-target="#modal_baseConhecimento"><i></i><span><i18n:message key="baseConhecimento.baseConhecimento" /></span></a></li>
									</ul>
								<%-- 	<%} else{%>
										<ul class="collapse in" id="menu-recent-stats">
											<%=Free.getMsgCampoIndisponivel(request)%>
										</ul>
									<% }%> --%>
								</li>
							</ul>
							<div class="clearfix"></div>
							<div class="separator bottom"></div>
										
							</div>
							<div class="slimScrollBar ui-draggable" style="background-color: rgb(0, 0, 0); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; height: 2764px; background-position: initial initial; background-repeat: initial initial;"></div>
							<div class="slimScrollRail" style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div></div>
					</div>
				
				
						<!-- <h3>Cadastro de Solicitação</h3> -->
					<div class="innerLR">
						<div class="widget-body span9">
							<div class="span4">
									<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/cadastroSolServico/cadastroSolServico'>
										<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
										<input type='hidden' name='idSolicitante' id='idSolicitante' /> 
										<input type='hidden' name='messageId' id='messageId' /> 
										<input type='hidden' name='idItemConfiguracao' id='idItemConfiguracao' />
										<input type='hidden' name='reclassificar' id='reclassificar' />
										<input type='hidden' name='escalar' id='escalar' /> 
										<input type='hidden' name='alterarSituacao' id='alterarSituacao' />
										<input type='hidden' name='idTarefa' id='idTarefa' />
										<input type='hidden' name='acaoFluxo' id='acaoFluxo' />
										<input type='hidden' name='filtroADPesq' id='filtroADPesq' />
										<input type='hidden' name='colItensProblema_Serialize' id='colItensProblema_Serialize' />
										<input type='hidden' name='colItensMudanca_Serialize' id='colItensMudanca_Serialize' />
										<input type='hidden' name='colItensIC_Serialize' id='colItensIC_Serialize' />
										<input type='hidden'  name='idSolicitacaoPai' id='idSolicitacaoPai' />
										<input type='hidden' name='idSolicitacaoRelacionada' id='idSolicitacaoRelacionada' /> 
										<input type='hidden' name='informacoesComplementares_serialize' id='informacoesComplementares_serialize' />
										<input type='hidden' name='sequenciaProblema' id='sequenciaProblema' />
										<input type='hidden' name='idProblema' id='idProblema' />
										<input type='hidden' name='idRequisicaoMudanca' id='idRequisicaoMudanca' />
										<input type='hidden' name='colConhecimentoSolicitacao_Serialize' id='colConhecimentoSolicitacao_Serialize' />
										<input type='hidden' name='situacaoFiltroSolicitante' id='situacaoFiltroSolicitante' />
										<input type='hidden' name='buscaFiltroSolicitante' id='buscaFiltroSolicitante' />
										<input type='hidden' name='validaBaseConhecimento' id='validaBaseConhecimento' />
										<input type='hidden' name='flagGrupo' id='flagGrupo' />
										<input type='hidden' name='idServico' id='idServico' />
										<input type='hidden' name='nomecontato' id='nomecontato'  />
										<input type='hidden' name='reclassicarSolicitacao' id='reclassicarSolicitacao'  />
										<input type='hidden' name='sequenciaBaseConhecimento' id='sequenciaBaseConhecimento' /> 
										<input type="hidden" name="idItemBaseConhecimento" id="idItemBaseConhecimento"> 
										
									
									 <div class="widget" data-toggle="collapse-widget" data-collapse-closed="true" id="collapse1">
										<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.informacaoContratoNovoLayout" /></h4><span class=""></span></div>
										<div class="widget-body collapse" >
											<div class='row-fluid'>
												<label class="strong"><i18n:message key="solicitacaoServico.selecioneContrato" /></label>
													<select  class=" span12" id="idContrato" name="idContrato" required="required" onchange="desativarBotaoAvancar2();avancar1();chamaFuncoesContrato();montaParametrosAutocompleteServico();"></select>
											</div>
												<div class="separator"></div>
												
												<!-- Botão avançar -->
												<div class="pagination margin-bottom-none pull-right">
													<ul>
														<li class="next primary" id="avancar1"><a href="javascript:avancar1();"><i18n:message key="citcorpore.comum.avancar" /></a></li>
													</ul>
												</div>
										</div>
									</div>
									<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true" id="collapse2">
										<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.informacoesSolicitanteNovoLayout" /></h4><span class=""></span></div>
										<div class="widget-body collapse" >
											<label  class="strong">
										
											</label>
											<div class="input-append">
											<label  class="strong"><i18n:message key="citcorpore.comum.origemNovoLayout" /></label>
												<select  class=" span6" id="idOrigem" required="required" onchange="camposObrigatoriosSolicitante();" name="idOrigem" ></select>
												<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" data-toggle="modal" data-target="#modal_novoUsuario" onclick="chamaPopupCadastroOrigem()"><i></i> <i18n:message key="novaOrigemAtendimento.novaOrigemAtendimento" /></span>
											</div>	
												<label  class="strong"><i18n:message key="solicitacaoServico.nomeDoSolicitante" /></label>
													<div class="input-append">
													  	<input class="span5"  type="text" name="solicitante" id="solicitante" required="required"  placeholder="">
													  	<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
														<span class="btn btn-mini btn-primary btn-icon glyphicons search"  href="#modal_lookupSolicitante" data-toggle="modal" data-target="#modal_lookupSolicitante"><i></i> <i18n:message key="solicitacaoServico.nomeDoSolicitante" /></span>
														<span class="btn btn-mini btn-primary btn-icon glyphicons user_add"  href="#modal_novoUsuario" data-toggle="modal" data-target="#modal_novoUsuario" onclick="abreModalNovoColaborador()"><i></i> <i18n:message key="citcorpore.comum.novoUsuario" /></span>
														<%
															String mostraBotaoLDAP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_MOSTRA_BOTAO, "N");
															if (mostraBotaoLDAP.equalsIgnoreCase("S")) {
														%>
														<span class="btn btn-mini btn-primary btn-icon glyphicons group"  data-target="#code-1" onclick="buscarAD();"><i></i> <i18n:message key="solicitacaoServico.sincronizarAD" /></span>
														<%}%>
													</div>
													<!-- <div class="alert ">
														 <a class="close" data-dismiss="alert">&times;</a>
														<p onclick="">ATENÇÃO: há solicitações abertas para o solicitante escolhido!</p>
													</div> -->
													<div class="row-fluid">
														<div class="span5">
															<label  class="strong"><i18n:message key="citcorpore.comum.email" /></label>
															  	<input placeholder="Text input" class="span10" id="emailcontato" required="required"  onchange="camposObrigatoriosSolicitante();"  type="text" name="emailcontato">
														</div>
														<div class="span3">
															  	<label  class="strong"><i18n:message key="citcorpore.comum.telefone" /></label>
															  	<input class="span8 "  type="text" name="telefonecontato" placeholder="">
														
														</div>
														<div class="span2">
															
															  	<label  class="strong"><i18n:message key="citcorpore.comum.ramal" /></label>
															  	<input class="span5"  type="text" name="ramal" id="ramal"  placeholder="">
														</div>
													</div>
													<div class="row-fluid">
														<div class="span5">
															<label  class="strong"><i18n:message key="unidade.unidade" /></label>
																<select  class=" span6" name="idUnidade" id="idUnidade" required="required" onchange="camposObrigatoriosSolicitante();document.form.fireEvent('preencherComboLocalidade');" onclick="verificaContrato()"></select>
														</div>
														<div class="span5">
															<label  class="strong"><i18n:message key="citcorpore.comum.localidadeFisica" /></label>
																<select  class=" span6" name="idLocalidade" id="idLocalidade" onclick="verificaContrato()"></select>
														</div>
													</div>
													<div class="input-append">
														<label  class="strong"><i18n:message key="solicitacaoServico.observacao" /></label>
													  	<div class="controls">
															<textarea id="mustHaveId" class="wysihtml5 span12" rows="5" name="observacao" id="observacao"></textarea>
															<span id="contador_char"></span>
														</div>
													</div>
													 	<div class="separator"></div> 
													 	
												<!-- Botão avançar -->
												<div class="pagination margin-bottom-none pull-right">
													<ul>
														<li class="next primary" id="avancar2"><a href="javascript:avancar2();"><i18n:message key="citcorpore.comum.avancar" /></a></li>
													</ul>
												</div>
										</div>
									</div>
						
									<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true" id="collapse3">
										<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.informacoesSolicitacaoNovoLayout" /></h4><span class=""></span></div>
										<div class="widget-body collapse" >
										<div class="row-fluid">
											<div class="span5">
												<label  class="strong"><i18n:message key="solicitacaoServico.tipo" /></label>
												<select  class=" span6" name="idTipoDemandaServico" id="idTipoDemandaServico" required="required" onclick="limparCampoServiceBusca()" onchange="montaParametrosAutocompleteServico();document.form.fireEvent('carregaServicosMulti');camposObrigatoriosSolicitacao();"></select>
												</div>
												<div class="span5">
												<label  class="strong"><i18n:message key="servico.categoria" /></label>
												<select  class=" span6" id="idCategoriaServico" name="idCategoriaServico"></select>
												</div>
										</div>
											<!-- 	<div class="separator"></div> -->
												<div class="row-fluid">
													<div class="span7">
													<!-- <label  class="strong">Nome do Serviço</label> -->
														<!-- <div class="input-append" >
														  	<input class="span6" id='servicoBusca'  name='servicoBusca' type="text" onblur="camposObrigatoriosSolicitacao();"  placeholder="Digite o nome do Solicitante">
														  	<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
														</div> -->
														<div class="input-prepend input-append" id='divNomeDoServico'>
														  	<label  class="strong"><i18n:message key="servico.nome" /></label>
														  	<input class="span12"  type="text" name="servicoBusca" id="servicoBusca" required="required" ondblclick="limparCampoBusca()" onkeyup="startLoading()" onblur="document.form.fireEvent('verificaImpactoUrgencia');carregaScript(this);document.form.fireEvent('verificaGrupoExecutor');carregarInformacoesComplementares();calcularSLA();"
																placeholder="Digite o nome do Serviço" >
														  	<span class="add-on"><i class="icon-search"></i></span>
														</div>
														</div>
														<div class="span5">
														<label  class="strong"><i18n:message key="controle.sla" /></label>
														<div class='input-append'>
															 <span class='label large' id="tdResultadoSLAPrevisto"></span>
															<span  id="divMini_loading" style="display: none" ><img src="../../novoLayout/common/include/imagens/mini_loading.gif"></span>
														
														</div>
														</div>
												</div>
													<div class="controls">
													<label  class="strong"><i18n:message key="solicitacaoServico.descricao" /></label>
														<div class="controls">
															<textarea class='span10'  rows="500" id="descricao" name="descricao" onChange="camposObrigatoriosSolicitacao();" ></textarea>
														</div>
													</div>
													<div class="row-fluid">
														<div class="span3">
															<span class="btn btn-block btn-mini btn-icon glyphicons search"
															id='btnPesqSolucao' onclick='pesquisarSolucao()'><i></i><i18n:message key="solicitacaoServico.pesquisarSolucao" /></span>
														</div>
													</div>
													<div class="" id='divControleInformacaoComplementar'>
														<label  class="strong"><i18n:message key="solicitacaoServico.grupo" /></label>
															<select  class=" span6" name="idGrupoAtual" id="idGrupoAtual"></select>
															<div class="row-fluid">
																<div class="span5">
																	<label  class="strong"><i18n:message key="solicitacaoServico.urgencia" /></label>
																		<select  class=" span6" id="urgencia" name="urgencia" required="required" onchange="camposObrigatoriosSolicitacao();calcularSLA();"></select>
																</div>
																<div class="span5">
																	<label  class="strong"><i18n:message key="solicitacaoServico.impacto" /></label>
																		<select  class=" span6" id="impacto" name="impacto" required="required" onchange="camposObrigatoriosSolicitacao();calcularSLA();"></select>
																</div>
															</div>
															<div id="privacy-settings" class="tab-pane active">
																<label  class="strong"><i18n:message key="solicitacaoServico.notificaoemail" /></label>
																<div class="uniformjs">
																	<label class="checkbox"><div class="checker" id="uniform-undefined"><span class=""><input type="checkbox" name="enviaEmailCriacao" id="enviaEmailCriacao" value="S" checked="checked" style="opacity: 0;"></span></div> <i18n:message key="solicitacaoServico.enviaEmailCriacao" /></label>
																	<label class="checkbox"><div class="checker" id="uniform-undefined"><span class=""><input type="checkbox" name="enviaEmailFinalizacao" id="enviaEmailFinalizacao"  value="S"  style="opacity: 0;"></span></div> <i18n:message key="solicitacaoServico.enviaEmailFinalizacao" /></label>
																	<label class="checkbox"><div class="checker" id="uniform-undefined"><span class=""><input type="checkbox" name="enviaEmailAcoes" id="enviaEmailAcoes" value="S" style="opacity: 0;"></span></div> <i18n:message key="solicitacaoServico.enviaEmailAcoes" /></label>
																	
																</div>
															</div>
													</div>
													<div class="inativo" id='divInformacoesComplementares'>
														<iframe id='fraInformacoesComplementares' name='fraInformacoesComplementares' class="inativo" src='about:blank' width="100%" height="100%" style='width: 95%; height: 100%; border: none;'></iframe>
													</div>
													 	<div class="separator"></div> 
												<!-- Botão avançar -->
												<div class="pagination margin-bottom-none pull-right">
													<ul>
														<li class="next primary" id="avancar3"><a href="javascript:avancar3();"><i18n:message key="citcorpore.comum.avancar" /></a></li>
													</ul>
												</div>
										</div>
									</div>
									<div class="widget" data-toggle="collapse-widget" data-collapse-closed="true" id="collapse4">
										<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.fechamentoNovoLayout" /></h4><span class=""></span></div>
										<div class="widget-body collapse" >
													<div class="controls" >
													<span class="btn btn-mini  btn-icon glyphicons circle_plus" id='btnAdicionarRegistroExecucao' onclick="adicionarRegistroExecucao()"><i></i> <i18n:message key="solicitacaoServico.fechamentoNovoLayout" /></span>
													<!-- Foi necessário deixar o style na div, pois colocando no css nao renderizou corretamente -->
														<div id='controleRegistroExecucao' style="display: none" >
															<label  class="strong"><i18n:message key="solicitacaoServico.registroExecucao" /></label>
															<div class="controls">
																<textarea  class="wysihtml5 span12" rows="5" name="registroexecucao" id="registroexecucao"></textarea>
															</div>
														</div>
													</div>
													<label  class="strong"><i18n:message key="solicitacaoServico.situacao" /></label>
													<div class="tab-pane">
														<div class="uniformjs">
															<label class="radio"><input type="radio" checked="checked" value="EmAndamento" name="situacao"/> <i18n:message key="solicitacaoServico.situacaoRegistrada" /></label>
															<label class="radio"><input type="radio" value="Resolvida" name="situacao"/> <i18n:message key="solicitacaoServico.situacaoResolvida" /></label>
															<label class="radio"><input type="radio" value="Cancelada" name="situacao"/> <i18n:message key="solicitacaoServico.situacao.Cancelada" /></label>
														</div>
													</div>
														<div class="row-fluid">
														<div class="span5">
															<label  class="strong"><i18n:message key="solicitacaoServico.causa" /></label>
																<select  class=" span6" id="idCausaIncidente" name="idCausaIncidente"></select>
														</div>
														</div>
														<div class="row-fluid">
															<div class="span5">
																<label  class="strong"><i18n:message key="solicitacaoServico.categoriaSolucao" /></label>
																	<select  class=" span6" id="idCategoriaSolucao" name="idCategoriaSolucao"></select>
															</div>
														</div>
														<label  class="strong"><i18n:message key="solicitacaoServico.solucaoTemporaria" /></label>
															<div class="tab-pane">
															<div class="uniformjs">
																<label class="radio"><input type="radio" value="S" name="solucaoTemporaria"/> <i18n:message key="citcorpore.comum.sim" /></label>
																<label class="radio"><input type="radio" checked="checked" value="N" name="situacsolucaoTemporaria"/> <i18n:message key="citcorpore.comum.nao" /></label>
															</div>
													</div>
													<div class="controls">
													<label  class="strong"><i18n:message key="solicitacaoServico.detalhamentocausa" /></label>
														<div class="controls">
															<textarea  class="wysihtml5 span12" rows="5" id="detalhamentoCausa" name="detalhamentoCausa"></textarea>
														</div>
													</div>
														
													 	<div class="separator"></div> 
												<!-- Botão avançar -->
												<!-- <div class="pagination margin-bottom-none pull-right">
													<ul>
														<li class="next primary"><a href="javascript:;">Avançar</a></li>
													</ul>
												</div> -->
										</div>
									</div>
									<div style="margin: 1;" id="divBotoes" class="navbar navbar-fixed-bottom">
							<%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
							<%-- <button href="#" class="btn " id='modals-bootbox-confirm' onclick="cancelar()"><i18n:message key="citcorpore.comum.cancelar" /></button>  --%>
							<button type="button"  class="btn " onclick="cancelar()" data-dismiss="modal"><i18n:message key="citcorpore.comum.cancelar" /></button> 
							<!-- <input type="button"  class="btn  btn-primary submit" onclick='gravar();' id="btGravar" value='<i18n:message key="citcorpore.comum.gravar" />'/>  -->
							<button class="btn  btn-primary submit" class='submit' type="submit"><i18n:message key="citcorpore.comum.gravar"/></button>
						<%} else {%>
							<button type="button" class="btn " id='modals-bootbox-confirm' onclick="cancelar()"><i18n:message key="citcorpore.comum.cancelar" /></button> 
							<button type="button"   class="btn  btn-primary " onclick='gravarEContinuar();'><i18n:message key="citcorpore.comum.gravarEContinuar" /></button>
							<button type="button"   class="btn  btn-primary " onclick='gravarEFinalizar();'><i18n:message key="citcorpore.comum.gravarEFinalizar" /></button>
							<%}%>
						</div>									
								</form>
							</div>
						</div>						
					</div>					
				</div>
						
			</div>
</div>
							<!-- 
							===================================
							===================================
							INICIO DA AREA DE JANELAS (MODAL) 
							===================================
							===================================
							-->
<!-- MODAL AGENDA ... -->
			<div class="modal hide fade in" id="modal_agenda" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Agenda</h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<iframe width="99%" height="770" src="../../pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar=true"></iframe>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_novoColaborador" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Novo Colaborador</h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<iframe width="99%" height="830" id='frameCadastroNovoColaborador' src=""></iframe>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<!-- MODAL ANEXO ... -->
										
			 <div class="modal hide fade in" id="modal_anexo" aria-hidden="false">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Anexo</h3>
					</div>
					<div>
						<form name="formUpload" method="post" id='formularioDeAnexos'>
							<cit:uploadControl id="uploadAnexos" title="Anexos"	style="height: 80px; width: 100%; border: 1px solid black;" form="document.formUpload" action="/pages/upload/upload.load" disabled="false" />								
							<font id="msgGravarDados" style="display:none" color="red"><i18n:message key="barraferramenta.validacao.solicitacao" /></font><br />				
							<button id="btnGravarTelaAnexos" name="btnGravarTelaAnexos" onclick="gravarAnexo();" type="button" style="display:none">
								<i18n:message key="citcorpore.comum.gravar" />
							</button>
						</form>
					</div>
					<div class="modal-footer">
						<a href="#" class="btn btn-primary" data-dismiss="modal" onclick="gravarAnexo();"><i18n:message key="citcorpore.comum.gravar" /></a> 
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
			</div>
			
			<!--<div class="POPUP_barraFerramentasProblemas" id="modal_anexo" style="display:none">		
				<form name="formUpload" method="post" enctype="multipart/form-data" id='formularioDeAnexos'>
					<cit:uploadControl id="uploadAnexos" title="Anexos"	style="height: 10px; width: 50%; border: 1px solid black;" form="document.formUpload" action="/pages/upload/upload.load" disabled="false" />								
					<font id="msgGravarDados" style="display:none" color="red"><i18n:message key="barraferramenta.validacao.solicitacao" /></font><br />				
					<button id="btnGravarTelaAnexos" name="btnGravarTelaAnexos" onclick="gravarAnexoMudanca();" type="button" style="display:none">
						<i18n:message key="citcorpore.comum.gravar" />
					</button>
					<button id="btnFecharTelaAnexos" name="btnFecharTelaAnexos" type="button">
						<i18n:message key="citcorpore.comum.fechar" />
					</button>
				</form>
			</div>-->
			
			<!-- MODAL SCRIPT ... -->
			<div class="modal hide fade in" id="modal_script" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Script</h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.scriptApoio" /></h4></div>
								<div class='widget-body'>
									<div id='divScript' >
										<i18n:message key="citcorpore.comum.selecionescript" />
									</div>
								</div>
							</div>
							
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<!-- MODAL PROBLEMA ... -->
			<div class="modal hide fade in" id="modal_problema" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="divProblema" style="display: block" class="col_50">
							<%if (br.com.citframework.util.Util.isVersionFree(request)) {%>
								<div style="width: 90%;">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</div>
							<%} else {%>
							<div class="row-fluid">
								<div class="span10">
									<span class="btn btn-mini btn-primary btn-icon glyphicons search"  
									id="addProblema" name="addProblema" class='span10' 
									 href="#modal_lookupProblema" data-toggle="modal" data-target="#modal_lookupProblema"  ><i></i> <i18n:message key="solicitacaoServico.adicionarProblema" /></span> 
								</div>
							</div>
							<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.problemaRelacionado" /></h4></div>
								<div class='widget-body'>
									<div id='divProblemaSolicitacao' >
										<table id='tblProblema' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
												<td style='text-align: center'  width='20px' height="15px">&nbsp;</td>
												<td width="10%"></td>
												<td width="60%" ><i18n:message key="requisicaMudanca.titulo" /></td>
												<td width="29%" ><i18n:message key="requisicaMudanca.status" /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<%}%>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
				<!-- MODAL MUDANÇA ... -->
			<div class="modal hide fade in" id="modal_mudanca" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Mudança</h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
					<div id="divMudanca" style="display: block" class="col_50">
							<%if (br.com.citframework.util.Util.isVersionFree(request)) {%>
								<div style="width: 90%;">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</div>
							<%} else {%>
							<div class="row-fluid">
								<div class="span10">
									<span class="btn btn-mini btn-primary btn-icon glyphicons search"  
									id="addMudanca" name="addMudanca" class='span10' 
									 href="#modal_lookupMudanca" data-toggle="modal" data-target="#modal_lookupMudanca"  ><i></i> <i18n:message key="solicitacaoServico.adicionarMudanca" /></span> 
								</div>
							</div>
							<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.mudancaRelacionada" /></h4></div>
								<div class='widget-body'>
									<div id='divMudancaSolicitacao' >
										<table id='tblMudanca' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
												<td style='text-align: center'  width='20px' height="15px">&nbsp;</td>
												<td width="10%"></td>
												<td width="60%" ><i18n:message key="requisicaMudanca.titulo" /></td>
												<td width="29%" ><i18n:message key="requisicaMudanca.status" /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<%}%>
						</div>
							
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
				<!-- MODAL ITENS DE CONFIGURAÇÃO ... -->
			<div class="modal hide fade in" id="modal_itemConfiguracao" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.itemConfiguracao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
							<div id="divMudanca" style="display: block" class="col_50">
							<%if (br.com.citframework.util.Util.isVersionFree(request)) {%>
								<div style="width: 90%;">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</div>
							<%} else {%>
							<div class="row-fluid">
								<div class="span10">
									<span class="btn btn-mini btn-primary btn-icon glyphicons search"  
									id="addProblema" name="addProblema" class='span10' 
									 href="#modal_lookupMudanca" data-toggle="modal" data-target="#modal_lookupMudanca"  ><i></i> <i18n:message key="problema.adicionar_item_configuracao" /></span> 
								</div>
							</div>
							<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.itemConfiguracaoAdcionado" /></h4></div>
								<div class='widget-body'>
									<div id='divMudancaSolicitacao' >
										<table id='tblIC' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
											<td style='text-align: center' class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
											<td width="20%" class='linhaSubtituloGrid'><i18n:message key="citcorpore.comum.numero" /></td>
											<td width="62%" class='linhaSubtituloGrid'><i18n:message key="citcorpore.comum.identificacao" /></td>
											<td width="10%"><i18n:message key="solicitacaoServico.informacao" /></td>
											</tr>
										</table>
									</div>
								</div>
							</div>
							<%}%>
						</div>
					
							
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
				<!-- MODAL BASE DE CONHECIMENTO ... -->
			<div class="modal hide fade in" id="modal_baseConhecimento" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="baseConhecimento.baseConhecimento" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
							<div class="box-generic">
		
								<!-- Tabs Heading -->
								<div class="tabsbar">
									<ul>
										<li class="active"><a href="#tab11-33" data-toggle="tab"><i18n:message key="baseConhecimento.vincularBaseConhecimento" /></a></li>
										<li class=""><a href="#tab22-33" data-toggle="tab" onmouseover="abrirModalBaseConhecimento()"><i18n:message key="baseConhecimento.pesquisabase" /> </a></li>
									</ul>
								</div>
								<!-- // Tabs Heading END -->
								<div class="tab-content">
										<div class='tab-pane active' id='tab11-33'>
											<div class="row-fluid">
												<div class="span10">
													<span class="btn btn-mini btn-primary btn-icon glyphicons search"  
													id="addProblema" name="addProblema" class='span10' 
													 href="#modal_lookupBaseConhecimento" data-toggle="modal" data-target="#modal_lookupBaseConhecimento"><i></i><i18n:message key="baseConhecimento.pesquisabase" /></span> 
												</div>
											</div>
											<div class='widget'>
												<div class='widget-head'><h4 class='heading'>Base de Conhecimento Relacionado(s)</h4></div>
													<div class='widget-body'>
														<div id='divConhecimentoSolicitacao' >
															<table id='tblBaseConhecimento' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
																<tr>
																	<td width="20%" style='font-size: 14px;' class='linhaSubtituloGrid'><i18n:message key="baseConhecimento.idBaseConhecimento" /></td>
																	<td width="75%" style='font-size: 14px;' class='linhaSubtituloGrid'><i18n:message key="baseConhecimento.titulo" /></td>
																	<td style='text-align: center' style='font-size: 14px;'  class='linhaSubtituloGrid' width='20px' height="15px;">&nbsp;</td>
																</tr>
															</table>
														</div>
													</div>
											</div>
										</div>
										<div class='tab-pane' id='tab22-33'>
											<iframe width="99%" height="520" id='frameBaseConhecimento' ></iframe>		
										</div>
								</div>
	
							</div>
											
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
				<!-- MODAL OCORRENCIA -->
				<div class="modal hide fade in" id="modal_ocorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Ocorrencia</h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
					<form name="formOcorrenciaSolicitacao" method="post" 
							action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao">
							<input type="hidden" id="idSolicitacaoOcorrencia" name="idSolicitacaoOcorrencia" />
							<input type="hidden" id="descricao" name="descricao" />
							<input type="hidden" id="idCategoriaOcorrencia" name="idCategoriaOcorrencia" />
							<input type="hidden" id="idOrigemOcorrencia" name="idOrigemOcorrencia" />
							<input type="hidden" name="idOcorrencia" />
						<div class="box-generic">
	
							<!-- Tabs Heading -->
							<div class="tabsbar">
								<ul>
									<li class=""><a href="#tab1-3" data-toggle="tab"><i18n:message key="solicitacaoServico.ocorrenciasRegistradas" /> </a></li>
									<li class=""><a href="#tab2-3" data-toggle="tab"><i18n:message key="solicitacaoServico.cadastroOcorrencia" /> </a></li>
								</ul>
							</div>
							<!-- // Tabs Heading END -->
							<div class="tab-content">
									<div class='tab-pane' id='tab1-3'>
										<div id="divRelacaoOcorrencias"></div>
									</div>
									<div class='tab-pane' id='tab2-3'>
										<div class="">
												<div class="input-append">
													<label  class="strong"><i18n:message key="solicitacaoServico.categoria" /></label>
												  	<input class="span5"  type="text" name="nomeCategoriaOcorrencia" id="nomeCategoriaOcorrencia" placeholder="" onclick="abreLookupCategoriaOcorrencia()">
												  	<button class="btn btn-default" type="button" onclick="abreLookupCategoriaOcorrencia()"><i class="icon-search"></i></button>
													<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  href="#modal_cadastroCategoriaOcorrencia" data-toggle="modal" data-target="#modal_cadastroCategoriaOcorrencia"><i></i> <i18n:message key="citcorpore.comum.cadastroCategoria" /></span>
												</div>
												<div class="input-append">
													<label  class="strong"><i18n:message key="citcorpore.comum.origem" /></label>
												  	<input class="span5"  type="text" name="nomeOrigemOcorrencia" id="nomeOrigemOcorrencia" placeholder="" onclick="abreLookupOrigemOcorrencia()">
												  	<button class="btn btn-default" type="button" onclick="abreLookupOrigemOcorrencia()"><i class="icon-search"></i></button>
													<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  href="#modal_cadastroOrigemOcorrencia" data-toggle="modal" data-target="#modal_cadastroOrigemOcorrencia"><i></i> <i18n:message key="citcorpore.comum.cadastroOrigem" /></span>
												</div>
										</div>
										<div class="row-fluid">
												<div class="span3">
													<label  class="strong"><i18n:message key="citcorpore.comum.tempogasto" /></label>
													<input type="text" class="span3" name="tempoGasto" id="tempoGasto" ></input>
													<span >Minutos</span>
												</div>
												<div class="span6">
														<label  class="strong"><i18n:message key="ocorrenciaLiberacao.registradopor" /></label>
														<input type="text"  class="span6" id="registradopor" name="registradopor"></input>
												</div>
										</div>
										<div class="controls">
											<label  class="strong"><i18n:message key="solicitacaoServico.descricao" /></label>
												<div class="controls">
													<textarea  class="wysihtml5 span12" rows="5" id="descricao1" name="descricao1" onblur="camposObrigatoriosSolicitacao();"></textarea>
												</div>
											</div>
										<div class="controls">
											<label  class="strong"><i18n:message key="citcorpore.comum.ocorrencia" /></label>
												<div class="controls">
													<textarea  class="wysihtml5 span12" rows="5" id="ocorrencia" name="ocorrencia"></textarea>
										</div>
													</div>
										<div class="controls">
											<label  class="strong"><i18n:message key="solicitacaoServico.informacaoContato" /></label>
												<div class="controls">
													<textarea  class="wysihtml5 span12" rows="5" id="informacoesContato" name="informacoesContato"></textarea>
												</div>
										</div>
										
										<button id="" class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick="gravarOcorrencia();"><i></i><i18n:message key="citcorpore.comum.gravar" /> </button>
										<button id="" class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick="document.formOcorrenciaSolicitacao.clear();"><i></i><i18n:message key="dataManager.limpar" /></button>
									
										
									</div>
							</div>

						</div>
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
					<!-- MODAL CADASTRO DE CATEGORIA DE OCORRENCIA ... -->
			<div class="modal hide fade in" id="modal_cadastroCategoriaOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.cadastroCategoriaOcorrencia" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<iframe width="99%" height="830" id='frameCadastroCategoriaOcorrencia' src="../../categoriaOcorrencia/categoriaOcorrencia.load?iframe=true"></iframe>							
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
					<!-- MODAL CADASTRO DE ORIGEM DE OCORRENCIA ... -->
			<div class="modal hide fade in" id="modal_cadastroOrigemOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.cadastroOrigemOcorrencia" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<iframe width="99%" height="830" id='frameCadastroOrigemOcorrencia' src="../../origemOcorrencia/origemOcorrencia.load?iframe=true"></iframe>							
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
					<!-- MODAL LISTA INCIDENTES RELACIONADOS ... -->
			<div class="modal hide fade in" id="modal_incidentesRelacionados" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="gerenciaservico.incidentesrelacionados" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div>
						<div class="row-fluid">
							<div class="span10">
								<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  
								id="addProblema" name="addProblema" class='span10' 
								 href="#modal_listaRelacionarIncidentes" data-toggle="modal" data-target="#modal_listaRelacionarIncidentes"  ><i></i> <i18n:message key="solicitacaoServico.adicionarIncidenteNaRelacao" /></span> 
							</div>
						</div>
					</div>
					<!-- Modal body -->
					
					
						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.listaIncidenteRealcionado" /></h4></div>
								<div class='widget-body'>
									<div id="tabelaIncidentesRelacionados"></div>	
								</div>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<!-- MODAL SELECIONAR INCIDENTES RELACIONADOS ... -->
			<div class="modal hide fade in" id="modal_listaRelacionarIncidentes" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Relacionar Incidentes</h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div>
						<div class="row-fluid">
							<div class="span10">
								<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  
								id="addProblema" name="addProblema" class='span10' 
								 href="" data-toggle="" data-target=""  ><i></i> <i18n:message key="solicitacaoServico.adicionarSelecionados" /></span> 
							</div>
						</div>
					</div>
					<!-- Modal body -->
						<div class="" id="divSolicitacoesFilhas">				
								<form name="formIncidentesRelacionados" method="post" 
									action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/incidentesRelacionados/incidentesRelacionados">
									<input type="hidden" name="idSolicitacaoIncRel" value="" />
										<div class='widget'>
											<div class='widget-head'><h4 class='heading'><i18n:message key="requisicaoMudanca.relacionarIncidentes" /></h4></div>
												<div class='widget-body'>
													<div id="divConteudoIncRel"></div>
											</div>
										</div>
								</form>
						</div>
						
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
				<!-- MODAL SOLICITAÇÕES PARA O SOLICITANTE SELECIONADO ... -->
			<div class="modal hide fade in" id="modal_listaSolicitacoesMesmoSolicitante" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.solicitacaoPorUsuario" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div>
						<div class="row-fluid">
							<div class="span3">
								<label  class="strong"><i18n:message key="solicitacaoServico.situacao" /></label>
								<select name='situacaoTblResumo2' id='situacaoTblResumo2'>
									<option value='EmAndamento'><i18n:message key="citcorpore.comum.emandamento"/></option>
									<option value='Fechada'><i18n:message key="citcorpore.comum.fechada"/></option>
								</select>
							</div>
							<div class="input-prepend input-append">
							  	<label  class="strong"><i18n:message key="citcorpore.comum.busca" /></label>
							  	<input class="span12"  type="text" name="campoBuscaTblResumo2" id="campoBuscaTblResumo2" placeholder="" onkeydown="if ( event.keyCode == 13 ) pesquisaSolicitacoesAbertasParaMesmoSolicitante();">
							  	<span class="add-on"><i class="icon-search"></i></span>
							  	<span class="btn btn-mini btn-primary btn-icon glyphicons search" id='btnPesquisaSolUsuario' onclick="pesquisaSolicitacoesAbertasParaMesmoSolicitante()"><i></i> <i18n:message key="citcorpore.comum.pesquisar"/></span>
							</div>
						</div>
					</div>
					<!-- Modal body -->
						<div class="" id="tblResumo2"></div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_detalheSolicitacaoServico" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.detalhamento" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div id='detalheSolicitacaoServico'>
					</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_pesquisaSolucaoBaseConhecimento" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<!-- <h3>Detalhamento da Solicitação</h3> -->
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
						<div id='resultPesquisa'>
					</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_novaSolicitacaoFilho" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="gerenciaservico.novasolicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
						<iframe width="99%" height="830" id='frameCadastroNovaSolicitacaoFilho'></iframe>
					<div >
					</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
					<!-- MODAL LOOKUP SOLICITANTE... -->
			<div class="modal hide fade in" id="modal_lookupSolicitante" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="colaborador.pesquisacolaborador" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaColaborador' style="width: 540px">
							<cit:findField formName='formPesquisaColaborador'
							lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOLICITANTE'
							top='0' left='0' len='550' heigth='200' javascriptCode='true'
							htmlCode='true' />
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			<!-- MODAL LOOKUP PROBLEMA... -->
			<div class="modal hide fade in" id="modal_lookupProblema" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaProblema' style="width: 540px">
							<cit:findField formName='formPesquisaProblema'
								lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
								left='0' len='550' heigth='200' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_lookupMudanca" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaMudanca' style="width: 540px">
						<cit:findField formName='formPesquisaMudanca'
							lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
							htmlCode='true' />
					</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_lookupCategoriaOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaCategoriaOcorrencia' style="width: 540px">
						<cit:findField formName='formPesquisaCategoriaOcorrencia'
							lockupName='LOOKUP_CATEGORIA_OCORRENCIA' id='LOOKUP_CATEGORIA_OCORRENCIA' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
							htmlCode='true' />
					</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_lookupOrigemOcorrencia" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaOrigemOcorrencia' style="width: 540px">
						<cit:findField formName='formPesquisaOrigemOcorrencia'
							lockupName='LOOKUP_ORIGEM_OCORRENCIA' id='LOOKUP_ORIGEM_OCORRENCIA' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
							htmlCode='true' />
					</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_lookupBaseConhecimento" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name='formPesquisaBaseConhecimento' style="width: 540px">
						<cit:findField formName='formPesquisaBaseConhecimento'
							lockupName='LOOKUP_BASECONHECIMENTO' id='LOOKUP_BASECONHECIMENTO' top='0'
							left='0' len='550' heigth='200' javascriptCode='true'
							htmlCode='true' />
					</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="mensagem_insercao" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body" >
						<div id="divInsercao">
						
						</div>

					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a class="btn " onclick="fecharModalNovaSolicitacao();" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_origem" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3>Solicitação</h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='slimScrollDiv'>
							<div class='slim-scroll' id='contentFrameOrigem'>
								<iframe id='frameExibirOrigem' src='about:blank' width="100%" height="320"></iframe>
							</div>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
					
					</div>
					<!-- // Modal footer END -->
				</div>
			</div>
			
			<%@include file="/novoLayout/common/include/libRodape.jsp" %>
</body>
</html>