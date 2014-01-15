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
	response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
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
	
	String qtdeAnexo = UtilStrings.nullToVazio( (String) request.getAttribute("quantidadeAnexo") );
	
	String tarefaAssociada = (String) request
			.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null) {
		tarefaAssociada = "N";
	} 
	String iframe = "", parametroAdicionalAsterisk = "";
	iframe = request.getParameter("iframe");
	/**
	* Motivo: Recebendo o parametro modalAsterisk
	* Autor: flavio.santana
	* Data/Hora: 11/12/2013 10:15
	*/
	parametroAdicionalAsterisk = (request.getParameter("modalAsterisk") == null ? "false" : request.getParameter("modalAsterisk"));
	String URL_SISTEMA = "";
	URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';
	String mostraGravarBaseConhec = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOSTRAR_GRAVAR_BASE_CONHECIMENTO, "S");
%>

<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>

<link type="text/css" rel="stylesheet" href="css/solicitacaoServicoMultiContratos.min.css"/>
<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
<title>CITSMart</title>

	

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body onload="">
	
	<div class="container-fluid fluid menu-right">
		<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos'>
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
			<input type='hidden' name='colItensBaseConhecimento_Serialize' id='colItensBaseConhecimento_Serialize' />
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
			<input type="hidden" name="idBaseConhecimento" id="idBaseConhecimento"/> 

			<div class="wrapper personalizado" >
				<div id="menu" class="hidden-print" >
					<span class='profile inativo' id='tituloSolicitacao'></span>
					<!-- <div class="slimScrollDiv" style="position: relative; overflow: hidden; width: auto; height: 800px;">
						<div class="slim-scroll" data-scroll-height="800px" style="overflow: hidden; width: auto; height: 800px;"> -->
						<!-- Regular Size Menu -->
						<ul class="menu-0">
							<li class="hasSubmenu glyphicons warning_sign " id='divMenuSolicitacao' >
								<a  href="#modal_listaSolicitacoesMesmoSolicitante" data-toggle="modal" data-target="#modal_listaSolicitacoesMesmoSolicitante" class="collapsed" onclick="carregaSolicitacoesAbertasParaMesmoSolicitante()"><i></i><span><i18n:message key="gerenciaservico.solicitacoes" /></span></a>
								<ul class="collapse" id="menu_components" style="height: 0px;">
								</ul>
								<span class="count" id='countSolicitacoesAbertasSolicitante'>0</span>
							</li>
							<li class="hasSubmenu glyphicons list " id='divMenuScript'>
								<a  href="#modal_script" data-toggle="modal" data-target="#modal_script" class="collapsed" onclick="carregaScript();" ><i></i><span><i18n:message key="solicitacaoServico.script" /></span></a>
								<ul class="collapse" id="menu_components" style="height: 0px;">
								</ul>
								<span class="count" id='countScript'>0</span>
							</li>
							<li class="hasSubmenu">
								<a  class="glyphicons paperclip" href="#modal_anexo" data-toggle="modal" data-target="#modal_anexo" onclick="carregaFlagGerenciamento()"><i></i><span><i18n:message key="citcorpore.comum.anexos" />(s)</span></a>
								<%-- <a  class="glyphicons paperclip" href="#modal_anexo" data-toggle="modal" data-target="#modal_anexo"><i></i><span><i18n:message key="citcorpore.comum.anexos" />(s)</span></a> --%>
								<ul class="collapse" id="menu_examples">
								</ul>
								<span class="count" id='quantidadeAnexos'></span>
							</li>
							<li class="glyphicons calendar"><a href="#modal_agenda" onclick="abrirModalAgenda()"><i></i><span><i18n:message key="citcorpore.comum.agenta" /></span></a>
							
							<%if (!tarefaAssociada.equalsIgnoreCase("N")) {%>
								<li class="hasSubmenu">
									<a  class="glyphicons notes" onclick="abreModalOcorrencia()"><i></i><span><i18n:message key="solicitacaoServico.ocorrencia" /></span></a>
									<ul class="collapse" id="menu_examples">
									</ul>
									<!-- <span class="count"></span> -->
								</li>

							
							<li class="hasSubmenu" id='liIncidentesRelacionados'>
								<a  class="glyphicons bomb" href="#modal_incidentesRelacionados" data-toggle="modal" data-target="#modal_incidentesRelacionados" id='btIncidentesRelacionados' onclick='restaurarIncidentesRelacionados()'><i></i><span><i18n:message key="gerenciaservico.incidentesrelacionados" /></span></a>
								<ul class="collapse" id="menu_examples">
								</ul>
								<!-- <span class="count"></span> -->
							</li>
							
							
							<li class="hasSubmenu inativo" id='liNovasolicitacao'>
								<a  class="glyphicons circle_plus"   onclick="modalCadastroSolicitacaoServico()"><i></i><span><i18n:message key="gerenciaservico.novasolicitacao" /></span></a>
								<ul class="collapse" id="menu_examples">
								</ul>
								<!-- <span class="count"></span> -->
							</li>
						<%}%>
						</ul>
					<div class="clearfix"></div>
						
					<ul class="menu-1">
						<li class="hasSubmenu active">
							<a class="glyphicons " href="#menu-recent-stats" data-toggle=""><i></i><span><i18n:message key="solicitacaoServico.processos" /></span></a>
						<%-- 	<%if (br.com.citframework.util.Util.isVersionFree(request)) {%> --%>
							<ul class="collapse in" id="menu-recent-stats">
							   <li id="divProblema"><a class="glyphicons circle_exclamation_mark" href="#modal_problema" data-toggle="modal" data-backdrop="static" data-target="#modal_problema"><i></i><span><i18n:message key="itemConfiguracaoTree.problema" /></span></a></li>
								<li id="divMudanca"><a class="glyphicons  edit" href="#modal_mudanca" data-toggle="modal" data-target="#modal_mudanca" data-backdrop="static"><i></i><span><i18n:message key="requisicaMudanca.mudanca" /></span></a></li>
								<li id="divItemConfiguracao"><a class="glyphicons  display" href="#modal_itemConfiguracao" data-toggle="modal" data-backdrop="static" data-target="#modal_itemConfiguracao"><i></i><span><i18n:message key="itemConfiguracaoTree.itensConfiguracao" /></span></a></li>
								<li><a class="glyphicons  database_lock" onclick="abrirModalBaseConhecimento()"><i></i><span><i18n:message key="baseConhecimento.baseConhecimento" /></span></a></li>
							</ul>
						<%-- 	<%} else{%>
								<ul class="collapse in" id="menu-recent-stats">
									<%=Free.getMsgCampoIndisponivel(request)%>
								</ul>
							<% }%> --%>
						</li>
					</ul>
					<!--
				Adicionado menu_2 para adicionar itens da template.
				* 
				* @author Pedro Lino
				* @since 07/11/2013 15:00
				*-->
					<ul class="menu-1 inativo" id='menu_2'>
						<li class="hasSubmenu active">
							<a class="glyphicons " href="#menu-recent-stats" data-toggle=""><i></i><span><i18n:message key="construtorconsultas.template" /></span></a>
							<ul class="collapse in" id="menu_itens_template">
							 	
							</ul>
						</li>
					</ul>
					<div class="clearfix"></div>
					<div class="separator bottom"></div>
									
					<!-- </div> -->
					<div class="slimScrollBar ui-draggable" style="background-color: rgb(0, 0, 0); width: 7px; position: absolute; top: 0px; opacity: 0.4; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; z-index: 99; right: 1px; height: 2764px; background-position: initial initial; background-repeat: initial initial;"></div>
					<div class="slimScrollRail" style="width: 7px; height: 100%; position: absolute; top: 0px; display: none; border-top-left-radius: 7px; border-top-right-radius: 7px; border-bottom-right-radius: 7px; border-bottom-left-radius: 7px; background-color: rgb(51, 51, 51); opacity: 0.2; z-index: 90; right: 1px; background-position: initial initial; background-repeat: initial initial;"></div>
				</div>
							
							
				<!-- <div id="wrapper"> -->
				<div id="content">
				
				<div id="rootwizard" class="wizard tabbable tabs-left">
					<ul id="ulWizard" class="nav nav-tabs">
					  	<li><a href="#tab1-4" data-toggle="tab" id='tab1'><i18n:message key="start.instalacao.1passo" /></a></li>
						<li><a href="#tab2-4" data-toggle="tab" id='tab2'><i18n:message key="start.instalacao.2passo" /></a></li>
						<li><a href="#tab3-4" data-toggle="tab" id='tab3'><i18n:message key="start.instalacao.3passo" /></a></li>
						<li><a href="#tab4-4" data-toggle="tab" id='tab4'><i18n:message key="start.instalacao.4passo" /></a></li>
					</ul>
	
					<!-- <div id="rootwizarda" class="wizard personalizado"> -->
						<!-- <div class="widget widget-tabs widget-tabs-double widget-tabs-vertical row-fluid row-merge"> -->
						
							<!-- Widget heading -->
							<!-- <div class="widget-head span1"> -->
								<%-- <ul>
									<li class="active"><a href="#tab1-4" class="glyphicons cargo" data-toggle="tab"><i></i>
										<span class="strong"><i18n:message key="start.instalacao.1passo" /></span>
										<span><i18n:message	key="solicitacaoServico.informacaoContratoNovoLayout" /></span></a>
									</li>
									<li><a href="#tab2-4" class="glyphicons user" data-toggle="tab"><i></i>
										<span class="strong"><i18n:message key="start.instalacao.2passo" /></span>
										<span><i18n:message	key="solicitacaoServico.informacoesSolicitanteNovoLayout" /></span></a></li>
									<li><a href="#tab3-4" class="glyphicons circle_info" data-toggle="tab"><i></i>
										<span class="strong"><i18n:message	key="start.instalacao.3passo" /></span>
										<span><i18n:message	key="solicitacaoServico.informacoesSolicitanteNovoLayout" /></span></a>
									</li>
									<li><a href="#tab4-4" class="glyphicons circle_ok" data-toggle="tab"><i></i>
										<span class="strong"><i18n:message key="start.instalacao.4passo" /></span>
										<span><i18n:message key="solicitacaoServico.fechamentoNovoLayout" /></span></a>
									</li>
								</ul> --%>
							<!-- </div> -->
							<!-- // Widget heading END -->
							
							<!-- <div class="widget-body span11"> -->
								<div class="tab-content">
								
									<!-- Step 1 -->
									<div class="tab-pane active" id="tab1-4">
										<div class='row-fluid'>
											<div class='span12'>
												 <div class="widget dentro">
													<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.informacaoContratoNovoLayout" /></h4></div>
													<div class="widget-body" >
														<div class='row-fluid'>
															<label class="strong campoObrigatorio"><i18n:message key="solicitacaoServico.selecioneContrato" /></label>
																<select  class="span12" id="idContrato" name="idContrato" required="required" onchange="chamaFuncoesContrato();montaParametrosAutocompleteServico();"></select>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- // Step 1 END -->
									
									<!-- Step 2 -->
									<div class="tab-pane" id="tab2-4">									
										<div class='row-fluid'>
											<div class='span12'>
												<div class="widget dentro">
													<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.informacoesSolicitanteNovoLayout" /></h4></div>
													<div class="widget-body" >
														<label  class="strong"></label>
															<div class="input-append">
																<label  class="strong campoObrigatorio"><i18n:message key="citcorpore.comum.origemNovoLayout" /></label>
																<select  id="idOrigem" required="required" name="idOrigem" ></select>
																<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" id='btnCadastroOrigem'  onclick="chamaPopupCadastroOrigem()"><i></i> <i18n:message key="novaOrigemAtendimento.novaOrigemAtendimento" /></button>
															</div>	
																<label  class="strong campoObrigatorio"><i18n:message key="solicitacaoServico.nomeDoSolicitante" /></label>
																<div class=" input-append">
																  	 <input type="text" class="span6" name="solicitante" id="solicitante" onfocus="montaParametrosAutocompleteServico();" required="required"  placeholder="" value="">
																  	<span class="add-on"><i class="icon-search"></i></span> 
																	<button type='button' class="btn btn-mini btn-primary btn-icon glyphicons search"  href="#modal_lookupSolicitante" data-toggle="modal" data-target="#modal_lookupSolicitante" id='btnPesqAvancada'>
																		<i></i> <i18n:message key="citcorpore.comum.pesquisaAvancada" />
																	</button>
																	<button type='button' class="btn btn-mini btn-primary btn-icon glyphicons user_add"  href="#modal_novoUsuario" data-toggle="modal" data-target="#modal_novoUsuario" onclick="abreModalNovoColaborador()">
																		<i></i> <i18n:message key="citcorpore.comum.novoUsuario" />
																	</button>
																	<%
																		String mostraBotaoLDAP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_MOSTRA_BOTAO, "N");
																		if (mostraBotaoLDAP.equalsIgnoreCase("S")) {
																	%>
																	<span class="btn btn-mini btn-primary btn-icon glyphicons group"  data-target="#code-1" onclick="buscarAD();"><i></i> <i18n:message key="solicitacaoServico.sincronizarAD" /></span>
																	<%}%> 
																</div>
																
								<%-- 								<div class="input-prepend input-append">
							  	<label  class="strong"><i18n:message key="citcorpore.comum.busca" /></label>
							  	<input class="span12"  type="text" name="campoBuscaTblResumo2" id="campoBuscaTblResumo2" placeholder="" onkeydown="if ( event.keyCode == 13 ) pesquisaSolicitacoesAbertasParaMesmoSolicitante();">
							  	<span class="add-on"><i class="icon-search"></i></span>
							  	<span class="btn btn-mini btn-primary btn-icon glyphicons search" id='btnPesquisaSolUsuario' onclick="pesquisaSolicitacoesAbertasParaMesmoSolicitante()"><i></i> <i18n:message key="citcorpore.comum.pesquisar"/></span>
							</div> --%>
																<!-- <div class="alert ">
																	 <a class="close" data-dismiss="alert">&times;</a>
																	<p onclick="">ATENÇÃO: há solicitações abertas para o solicitante escolhido!</p>
																</div> -->
																<div class="row-fluid">
																	<div class="span7">
																		<label  class="strong  campoObrigatorio"><i18n:message key="citcorpore.comum.email" /></label>
																	  	<input placeholder="Digite o email" class="span12" id="emailcontato" required="required" type="text" name="emailcontato">
																	</div>
																	<div class="span3">
																	  	<label  class="strong"><i18n:message key="citcorpore.comum.telefone" /></label>
																	  	<input class="span12"  type="text" name="telefonecontato" id="telefonecontato" placeholder="">
																	
																	</div>
																	<div class="span2">
																	  	<label  class="strong"><i18n:message key="citcorpore.comum.ramal" /></label>
																	  	<input class="span12"  type="text" name="ramal" id="ramal" maxlength="5" placeholder="" onkeypress="return somenteNumero(event)">
																	</div>
																</div>
																<div class="row-fluid">
																	<div class="span6">
																		<label  class="strong  campoObrigatorio"><i18n:message key="unidade.unidade" /></label>
																		<select  class="span12" name="idUnidade" id="idUnidade" required="required" onchange="document.form.fireEvent('preencherComboLocalidade');"></select>
																	</div>
																	<div class="span6">
																		<label  class="strong"><i18n:message key="citcorpore.comum.localidadeFisica" /></label>
																		<select  class="span12" name="idLocalidade" id="idLocalidade"></select>
																	</div>
																</div>
																<div class="input-append">
																	<label  class="strong"><i18n:message key="solicitacaoServico.observacao" /></label>
																  	<div class="controls">
																		<textarea  class="wysihtml5 span12" rows="5" cols="100" name="observacao" id="observacao"></textarea>
																		<span id="contador_char"></span>
																	</div>
																</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									<!-- // Step 2 END -->
									
									<!-- Step 3 -->
									<div class="tab-pane" id="tab3-4">
										<div class='row-fluid'>
											<div class='span12'>
												<div class="widget dentro">
													<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.informacoesSolicitacaoNovoLayout" /></h4><span class=""></span></div>
													<div class="widget-body" >
													<div class="row-fluid">
														<div class="span6">
															<label  class="strong  campoObrigatorio"><i18n:message key="solicitacaoServico.tipo" /></label>
															<select  class=" span6" name="idTipoDemandaServico" id="idTipoDemandaServico" required="required"  onchange="montaParametrosAutocompleteServico();document.form.fireEvent('carregaServicosMulti');limparCampoServiceBusca();stopSLAPrevisto();"></select>
														</div>
														<div class="span6">
															<div class="uniformjs">
																<label class="checkbox" id="checkUtilizaCategoriaServico">
																	<input type="checkbox" name="utilizaCategoriaServico" id="utilizaCategoriaServico" value="S" onclick="carregaCategoriaServico();" style="opacity: 0;">
																	<i18n:message key="solicitacaoServico.utilizaCategoriaServico" />
																</label>
															</div>
															<div id="divCategoriaServico">
																<label  class="strong"><i18n:message key="servico.categoria" /></label>
																<select  class=" span6" id="idCategoriaServico" name="idCategoriaServico" onchange="montaParametrosAutocompleteServico();"></select>
															</div>
														</div>
													</div>
														<!-- 	<div class="separator"></div> -->
															<div class="row-fluid">
																<div class="span9">
																	<!-- <label class="strong">Nome do Serviço</label> -->
																	<!-- <div class="input-append" >
																	<input class="span6" id='servicoBusca' name='servicoBusca' type="text" onblur="camposObrigatoriosSolicitacao();" placeholder="Digite o nome do Solicitante">
																	<button class="btn btn-default" type="button"><i class="icon-search"></i></button>
																	</div> -->
																	<div class="input-append" id='divNomeDoServico'>
																		<label class="strong campoObrigatorio"><i18n:message key="servico.nome" /></label>
																	  	<input class="span6" type="text" name="servicoBusca" id="servicoBusca" required="required" onkeyup="eventoStartLoading(event);" placeholder="Digite o nome do Serviço" >
																	  	<span class="add-on"><i class="icon-search"></i></span> 
																	  	<button type="button" class="btn btn-small btn-primary" onclick='limparServico()' id='btnLimparServico'><i18n:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>&nbsp;
																		<button type="button" class="btn btn-small btn-primary" onclick="mostrarComboServico()" data-toggle="modal" id='modals-bootbox-confirm'><i18n:message key="portal.carrinho.listagem" /></button>
																	</div>																	
																</div>
															<%-- 	<div class='span1'>
																	<label class="strong ">&nbsp;</label>
																	<button class="btn btn-mini btn-primary" onclick='limparServico()' type="button">limpar</button>
																</div>
																<div class='span2'>
																	<label class="strong ">&nbsp;</label>
																	<a class="" href="" onclick="mostrarComboServico()" data-toggle="modal" id='modals-bootbox-confirm' >&nbsp;<i18n:message key="portal.carrinho.listagem" /></a>
																</div> --%>
																<%-- <div class='span3'>
																<label class="strong ">&nbsp;</label>
																	<div class="" >
																		<button type="button" class="btn btn-small btn-primary" onclick='limparServico()'><i18n:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>&nbsp;
																		<button type="button" class="btn btn-small btn-primary" onclick="mostrarComboServico()" data-toggle="modal" id='modals-bootbox-confirm'><i18n:message key="portal.carrinho.listagem" /></button>
																	</div>
																</div> --%>
																<div class="rFloat">
																	<div id='fieldSla'>
																		<label class="strong"><i18n:message key="controle.sla" /></label>
																		<div class='input-append'>
																			<span class='label large' id="tdResultadoSLAPrevisto"></span>
																			<span id="divMini_loading" style="display: none" ><img src='<%=URL_SISTEMA%>novoLayout/common/include/imagens/mini_loading.gif'></span>
																		</div>
																	</div>
																</div>
																</div>
																<div id='fieldDescricao'>
																	<div class="controls">
																	<label  class="strong campoObrigatorio"><i18n:message key="solicitacaoServico.descricao" /></label>
																		<div class="controls">
																			<textarea  class="wysihtml5 span12 Valid[Required] Description[solicitacaoServico.descricao]" rows="5" cols="100" id="descricao" name="descricao" maxlength="65000"></textarea>
																		</div>
																	</div>
																	<div class="row-fluid">
																		<div class="span12">
																			<button type='button' class="btn btn-mini btn-icon glyphicons search"
																			id='btnPesqSolucao' onclick='pesquisarSolucao()'><i></i><i18n:message key="solicitacaoServico.pesquisarSolucao" /></button>
																		</div>
																	</div>
																</div>
																<div class="row-fluid">
																	<div id="divUrgencia" class="span6">
																		<label  class="strong campoObrigatorio"><i18n:message key="solicitacaoServico.urgencia" /></label>
																			<select  class=" span6" id="urgencia" name="urgencia" required="required" onchange="calcularSLA();"></select>
																	</div>
																	<div id="divImpacto" class="span6">
																		<label  class="strong campoObrigatorio"><i18n:message key="solicitacaoServico.impacto" /></label>
																			<select  class=" span6" id="impacto" name="impacto" required="required" onchange="calcularSLA();"></select>
																	</div>
																</div>
																<div id="divGrupoAtual">
																	<label  class="strong"><i18n:message key="solicitacaoServico.grupo" /></label>
																			<select  class=" span6" name="idGrupoAtual" id="idGrupoAtual" onchange="marcarChecksEmail()"></select>
																</div>
																<div id="divNotificacaoEmail">
																	<div id="privacy-settings" class="tab-pane active">
																		<label  class="strong"><i18n:message key="solicitacaoServico.notificaoemail" /></label>
																		<div class="uniformjs">
																		
																			<label class="checkbox" id="uniform-undefined1">
																				<input type="checkbox" name="enviaEmailCriacao" id="enviaEmailCriacao" value="S" checked style="opacity: 0;">
																				<i18n:message key="solicitacaoServico.enviaEmailCriacao" />
																			</label>
																			<label class="checkbox" id="uniform-undefined2">
																				<input type="checkbox" name="enviaEmailFinalizacao" id="enviaEmailFinalizacao"  value="S" checked  style="opacity: 0;">
																				<i18n:message key="solicitacaoServico.enviaEmailFinalizacao" />
																			</label>
																			<label class="checkbox" id="uniform-undefined3">
																				<input type="checkbox" name="enviaEmailAcoes" id="enviaEmailAcoes" value="S" checked style="opacity: 0;" >
																				<i18n:message key="solicitacaoServico.enviaEmailAcoes" />
																			</label>
																																						
																		</div>
																	</div>
																</div>
																<div class="" id='divControleInformacaoComplementar1'>
																</div>
																<div class="" id='divControleInformacaoComplementar2'>
																</div>
																
																<div class="inativo" id='divInformacoesComplementares'>

																</div>
													</div>
												</div>
											</div>
										</div>											
									</div>
									<!-- // Step 3 END -->
									
									<!-- Step 4 -->
									<div class="tab-pane" id="tab4-4">
										<div class='row-fluid' id="col4">
											<div class='span12'>
												<div class="widget dentro">
													<div class="widget-head"><h4 class="heading"><i18n:message key="solicitacaoServico.fechamentoNovoLayout" /></h4><span class=""></span></div>
													<div class="widget-body" >																
														<div id="divSituacao">
															<label  class="strong"><i18n:message key="solicitacaoServico.situacao" /></label>
															<div class="tab-pane">
																<div class="uniformjs">
																	<label class="radio"><input class="radio" type="radio" checked="checked" value="EmAndamento" name="situacao"/> <i18n:message key="solicitacaoServico.situacaoRegistrada" /></label>
																	<label class="radio"><input class="radio" type="radio" value="Resolvida" name="situacao"/> <i18n:message key="solicitacaoServico.situacaoResolvida" /></label>
																	<label class="radio"><input class="radio" type="radio" value="Cancelada" name="situacao"/> <i18n:message key="solicitacaoServico.situacao.Cancelada" /></label>
																</div>
															</div>
														</div>
														<div id="solucao">
															<div class="row-fluid">
																<div class="span6">
																	<label  class="strong"><i18n:message key="solicitacaoServico.causa" /></label>
																		<select  class=" span10" id="idCausaIncidente" name="idCausaIncidente"></select>
																</div>
																<div class="span6">
																	<label  class="strong"><i18n:message key="solicitacaoServico.categoriaSolucao" /></label>
																		<select  class=" span10" id="idCategoriaSolucao" name="idCategoriaSolucao"></select>
																</div>
															</div>
															<div class="controls" >
															<button type='button'class="btn btn-mini  btn-icon glyphicons circle_plus" id='btnAdicionarRegistroExecucao' onclick="adicionarRegistroExecucao()"><i></i> <i18n:message key="solicitacaoServico.addregistroexecucao_mais" /></button>
														<!-- Foi necessário deixar o style na div, pois colocando no css nao renderizou corretamente -->
															<div id='controleRegistroExecucao' style="display: none" >
																<label  class="strong"><i18n:message key="solicitacaoServico.registroExecucao" /></label>
																<div class="controls">
																	<textarea  class="wysihtml5 span12" rows="5" name="registroexecucao" id="registroexecucao"></textarea>
																</div>
															</div>
															</div>
															<label  class="strong"><i18n:message key="solicitacaoServico.solucaoTemporaria" /></label>
															<div class="tab-pane">
																<div class="uniformjs">
																	<label class="radio"><input type="radio" value="S" name="solucaoTemporaria"/> <i18n:message key="citcorpore.comum.sim" /></label>
																	<label class="radio"><input type="radio" checked="checked" value="N" name="solucaoTemporaria"/> <i18n:message key="citcorpore.comum.nao" /></label>
																</div>
															</div>
															<div class="controls">
															<label  class="strong"><i18n:message key="solicitacaoServico.detalhamentocausa" /></label>
																<div class="controls">
																	<textarea  class="wysihtml5 span12" rows="5" id="detalhamentoCausa" name="detalhamentoCausa"></textarea>
																</div>
															</div>
															<div id="privacy-settings" class="tab-pane active">
																<div class="uniformjs">
																	<label class="checkbox" id="uniform-undefined">
																		<input type="checkbox" name="gravaSolucaoRespostaBaseConhecimento" id="gravaSolucaoRespostaBaseConhecimento" onclick="gravarSolucaoRespostaEmBaseConhecimento()"  style="opacity: 0;">
																		<i18n:message key='baseConhecimento.GravarSolucaoResposta'/>
																	</label>
																</div>
															</div>
															<div class="row-fluid inativo" id="divTituloSolucaoRespostaBaseConhecimento" >
																<div class="span6">
																	<label  class="strong campoObrigatorio"><i18n:message key="baseConhecimento.titulo"/></label>
																		<input type="text" class="span12" id="tituloBaseConhecimento" name="tituloBaseConhecimento"/>
																</div>
															</div>
															<div class="controls">
																<label  class="strong"><i18n:message key="solicitacaoservico.solucaoResposta"/></label>
																<div class="controls">
																		<textarea  class="wysihtml5 span12" rows="5" id="resposta" name="resposta"></textarea>
																</div>
															</div>
														</div>
															
													 	<div class="separator"></div> 
													</div>
												</div>
											</div>
										</div>											
									</div>
									<!-- // Step 4 END -->
									
									<% 
										if (request.getParameter("visualizarPasso") == null)  {
									%>
									<!-- Wizard pagination controls -->
									<div class="pagination margin-bottom-none pull-right">
										<ul>
											<li class="primary previous"><a href="javascript:;"><i18n:message key="citcorpore.comum.anterior"/></a></li>
											<li class="next primary"><a href="javascript:;"><i18n:message key="citcorpore.comum.proximo"/></a></li>
											<!-- <li class="next finish primary" style="display:none;"><a href="javascript:;">Finish</a></li> -->
										</ul>
									</div>
									<% 
										}
									%>
									
									<div class="clearfix"></div>
									<!-- // Wizard pagination controls END -->
									
								</div>
								
							<!-- </div> -->
						<!-- </div> -->
					</div>
					<div style="margin: 1;" id="divBotoes" class="navbar navbar-fixed-bottom ">
					<%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
						<%-- <button href="#" class="btn " id='modals-bootbox-confirm' onclick="cancelar()"><i18n:message key="citcorpore.comum.cancelar" /></button>  --%>
						 
						<button type="button"  data-dismiss="modal"  class="btn btn-primary" onclick='desabilitaBotaoGravar(); gravarSemEnter(event); ' id="btnGravar"><i18n:message key="citcorpore.comum.gravar" /></button>
						<button type="button"  class="btn " onclick="cancelar()" data-dismiss="modal"><i18n:message key="citcorpore.comum.cancelar" /></button>
					<%} else {%>
						
						<button type="button"  id="btnGravar" data-dismiss="modal" class="btn  btn-primary " onclick='desabilitaBotaoGravar(); gravarEContinuar();'><i18n:message key="citcorpore.comum.gravarEContinuar" /></button>
						<button type="button" id="btnGravarEContinuar"  data-dismiss="modal" class="btn  btn-primary " onclick='gravarEFinalizar(); '><i18n:message key="citcorpore.comum.gravarEFinalizar" /></button>
						<button type="button" class="btn " id='modals-bootbox-confirm' onclick="cancelar()"><i18n:message key="citcorpore.comum.cancelar" /></button> 
					<%}%>
				</div>
				</div>
				
			</div>
		</form>
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
						<h3><i18n:message key="citcorpore.comum.agenta" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoAgendaAtvPeriodicas">
							
						</div>		
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
						<h3><i18n:message key="menu.nome.colaborador" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoCadastroNovoColaborador">
							
						</div>	
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<!-- MODAL ANEXO ... -->
			<div class="modal hide fade in" id="modal_anexo" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true" onclick="fechaModalAnexo();">×</button>
						<h3><i18n:message key="citcorpore.comum.anexos" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form class="form-horizontal" name="formUpload" method="post" enctype="multipart/form-data">
								<cit:uploadControl id="uploadAnexos" title="Anexos" style="height: 100px; width: 100%; border: 1px solid black;" form="document.formUpload" action="/pages/upload/upload.load" disabled="false" />								
								<font id="msgGravarDados" style="display:none" color="red"><i18n:message key="barraferramenta.validacao.solicitacao" /></font><br />	
						</form>		
					</div>	
					<!-- // Modal heading END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a><button type="button" href="#" class="btn btn-primary" data-dismiss="modal" onclick="fechaModalAnexo();"><i18n:message key="citcorpore.comum.gravar" /></button></a> 
						<a href="#" class="btn " data-dismiss="modal" onclick="fechaModalAnexo();"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<!-- MODAL SCRIPT ... -->
			<div class="modal hide fade in" id="modal_script" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.script" /></h3>
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
									<button class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" id="addProblema" href="#modal_lookupProblema" data-target="#modal_lookupProblema" data-toggle="modal">
										<i></i>
										<i18n:message key="solicitacaoServico.adicionarProblema" />
									</button>
									
									<button class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" id="addMudanca" onclick="cadastrarProblema()">
										<i></i>
										<i18n:message key="categoriaProblema.cadastro" />
									</button>
								</div>
							</div>
							<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.problemaRelacionado" /></h4></div>
								<div class='widget-body'>
									<div id='divProblemaSolicitacao' >
										<table id='tblProblema' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
												
												<!-- <td width="10%"></td> -->
												<td width="60%" ><i18n:message key="requisicaMudanca.titulo" /></td>
												<td width="29%" ><i18n:message key="requisicaMudanca.status" /></td>
												<td style='text-align: center'  width='20px' height="15px"></td>
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
						<h3><i18n:message key="requisicaMudanca.mudanca" /></h3>
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
									<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons search"  id="pesquisaMudanca" name="pesquisaMudanca" class='span10'  href="#modal_lookupMudanca" data-toggle="modal" data-target="#modal_lookupMudanca"><i></i> <i18n:message key="gerenciarequisicao.pesquisaMudanca" /></button> 
									 <button type="button" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  id="addMudanca" name="addMudanca" class='span10'  onclick="cadastrarMudanca()"><i></i><i18n:message key="citcorpore.comum.cadastrarMudanca"/></button>
								</div>
							</div>
						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.mudancaRelacionada" /></h4></div>
								<div class='widget-body'>
									<div id='divMudancaSolicitacao' >
										<table id='tblMudanca' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
												<!-- <td width="10%"></td> -->
												<td width="60%" ><i18n:message key="requisicaMudanca.titulo" /></td>
												<td width="29%" ><i18n:message key="requisicaMudanca.status" /></td>
												<td style='text-align: center'  width='20px' height="15px">&nbsp;</td>
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
			
			<!-- MODAL SOLICITACAOFILHAS ... -->
			
			<div class="modal hide fade in" id="modal_solicitacaofilha" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoservico.solicitacaofilha" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
						<div class="modal-body">
							<div id="divSolicitacaoRelacionada" style="display: block">
							<div class="row-fluid">
								<div class="span10">
									 <button type="button" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  id="addSolicitacaoFilha" name="addSolicitacaoFilha" class='span10'  onclick="chamaPopupCadastroSolicitacaoServico()"><i></i><i18n:message key="solicitacaoServico.cadastrosolicitacao"/></button>
								</div>
							</div>
							
						<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.solicitacao" /></h4></div>
								<div class='widget-body'>
									<div id='solicitacaoRelacionada' >
									</div>
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
									<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons search"  
									id="addProblema" name="addProblema" class='span10' onclick="abrirModalPesquisaItemConfiguracao()"><i></i> <i18n:message key="solicitacaoServico.pesquisarItemConfiguracao" /></button> 
								</div>
							</div>
							<div class='widget'>
							<div class='widget-head'><h4 class='heading'><i18n:message key="solicitacaoServico.itemConfiguracaoAdcionado" /></h4></div>
								<div class='widget-body'>
									<div id='divMudancaSolicitacao' >
										<table id='tblIC' class='dynamicTable table table-striped table-bordered table-condensed dataTable'>
											<tr>
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
										<li class="active"><a href="#tab11-33" data-toggle="tab"><i18n:message key="baseConhecimento.vincularBaseConhecimento"/></a></li>
										<li class=""><a href="#tab22-33" data-toggle="tab" onclick="abrirModalBaseConhecimento()"><i18n:message key="baseConhecimento.pesquisabase"/> </a></li>
									</ul>
								</div>
								<!-- // Tabs Heading END -->
								<div class="tab-content">
										<div class='tab-pane active' id='tab11-33'>
											<div class="row-fluid">
												<div class="span10">
													<button type="button" class="btn btn-mini btn-primary btn-icon glyphicons search"  
													id="addProblema" name="addProblema" class='span10' 
													 href="#modal_lookupBaseConhecimento" data-toggle="modal" data-target="#modal_lookupBaseConhecimento"><i></i> <i18n:message key="baseConhecimento.pesquisabase" /></button> 
												</div>
											</div>
											<div class='widget'>
												<div class='widget-head'><h4 class='heading'><i18n:message key="baseConhecimento.pesquisabase"/></h4></div>
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
											<div id="conteudoframeBaseConhecimento">
							
											</div>	
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
				<div class="modal hide fade in" id="modal_ocorrencia" data-backdrop="static" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.ocorrencia" /></h3>
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
										<li class="active"><a href="#tab1-3" data-toggle="tab"><i18n:message key="solicitacaoServico.ocorrenciasRegistradas" /> </a></li>
										<li class="" id='tabCadastroOcorrencia'><a href="#tab2-3" data-toggle="tab"><i18n:message key="solicitacaoServico.cadastroOcorrencia" /> </a></li>
									</ul>
								</div>
								<!-- // Tabs Heading END -->
								<div class="tab-content">
										<div class='tab-pane active' id='tab1-3'>
											<div id="divRelacaoOcorrencias"></div>
										</div>
										<div class='tab-pane' id='tab2-3'>
											<div class="">
													<div class="input-append">
														<label  class="strong"><i18n:message key="citcorpore.comum.categoria" /></label>
													  	<input class="span5"  type="text" name="nomeCategoriaOcorrencia" id="nomeCategoriaOcorrencia" placeholder="" onclick="abreLookupCategoriaOcorrencia()">
													  	<button class="btn btn-default" type="button" onclick="abreLookupCategoriaOcorrencia()"><i class="icon-search"></i></button>
														<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  href="#modal_cadastroCategoriaOcorrencia" onclick="abrirModalCadastroCategoriaOcorrencia()"><i></i> <i18n:message key="citcorpore.comum.cadastroCategoria" /></span>
													</div>
													<div class="input-append">
														<label  class="strong"><i18n:message key="citcorpore.comum.origem" /></label>
													  	<input class="span5"  type="text" name="nomeOrigemOcorrencia" id="nomeOrigemOcorrencia" placeholder="" onclick="abreLookupOrigemOcorrencia()">
													  	<button class="btn btn-default" type="button" onclick="abreLookupOrigemOcorrencia()"><i class="icon-search"></i></button>
														<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  href="#modal_cadastroOrigemOcorrencia" onclick="abrirModalCadastroOrigemOcorrencia()"><i></i><i18n:message key="citcorpore.comum.cadastroOrigem" /></span> 
													</div>
											</div>
											<div class="row-fluid">
													<div class="span3">
														<label  class="strong  campoObrigatorio"><i18n:message key="citcorpore.comum.tempogasto" /></label>
														<input type="text" class="span3 Valid[Required] Description[ocorrenciaSolicitacao.tempoGasto]" onkeypress='return somenteNumero(event)' name="tempoGasto" id="tempoGasto" maxlength="6"></input>
														<span >Minutos</span>
													</div>
													<div class="span6">
															<label  class="strong campoObrigatorio"><i18n:message key="ocorrenciaLiberacao.registradopor" /></label>
															<input type="text"  class="span12 Valid[Required] Description[ocorrenciaSolicitacao.registradopor]" id="registradopor" name="registradopor"></input>
													</div>
											</div>
											<div class="controls">
												<label  class="strong campoObrigatorio"><i18n:message key="solicitacaoServico.descricao" /></label>
													<div class="controls">
														<textarea  class="span10 Valid[Required] Description[pesquisa.descricao]" rows="2" id="descricao1" name="descricao1" maxlength ="200" ></textarea>
													</div>
												</div>
											<div class="controls">
												<label  class="strong campoObrigatorio"><i18n:message key="citcorpore.comum.ocorrencia" /></label>
													<div class="controls">
														<textarea  class="span10 Valid[Required] Description[Ocorrência]" rows="4" id="ocorrencia" name="ocorrencia"></textarea>
											</div>
														</div>
											<div class="controls">
												<label  class="strong campoObrigatorio"><i18n:message key="solicitacaoServico.informacaoContato" /></label>
													<div class="controls">
														<textarea  class="span10 Valid[Required] Description[solicitacaoServico.informacaoContato]" rows="4" id="informacoesContato" name="informacoesContato"></textarea>
													</div>
											</div>
											
											<button id="" class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick="gravarOcorrencia();"><i></i><i18n:message key="citcorpore.comum.gravar" /> </button>
											<button id="" class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick="limparCamposOcorrencia();"><i></i><i18n:message key="dataManager.limpar" /></button>
										
											
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
						<div id="conteudoCadastroCategoriaOcorrencia">
							
						</div>		
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
						<div id="conteudoCadastroOrigemOcorrencia">
							
						</div>	
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
					<div id='divBtIncidentesRelacionados'>
						<div class="row-fluid innerB">
							<div class="span12">
								<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"  
								id="btIncidentesRelacionados"  class='span10' 
								 href="#modal_listaRelacionarIncidentes" onclick="listarSolicitacoesServicoEmAndamento()" data-toggle="modal" data-target="#modal_listaRelacionarIncidentes"  ><i></i> <i18n:message key="solicitacaoServico.adicionarIncidenteNaRelacao" /></span> 
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
						<h3><i18n:message key="requisicaoMudanca.relacionarIncidentes" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
					<div>
						<div class="row-fluid">
						</div>
					</div>
					<!-- Modal body -->
						<div class="" id="divSolicitacoesFilhas">				
								<form name="formIncidentesRelacionados" method="post" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/incidentesRelacionados/incidentesRelacionados">
									<input type="hidden" name="idSolicitacaoIncRel" value="" />
									<div id="divConteudoIncRel">
										<div class="row-fluid innerTB">
											<div class="span12">
												<i18n:message key="citcorpore.comum.aguardecarregando"/>
											</div>
										</div>
									</div>
								</form>
						</div>
						
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
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
			
<%-- 			<div class="modal hide fade in" id="modal_novaSolicitacaoFilho" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="gerenciaservico.novasolicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<div class="modal-body">
						<div id="conteudoframeCadastroNovaSolicitacaoFilho">
							
						</div>
					<div >
					</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div> --%>
			
						<!-- MODAL ITEM DE CONFIGURACAO ... -->
			<div class="modal hide fade in" id="modal_pesquisaItemConfiguracao" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.pesquisarItemConfiguracao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoPesquisaItemConfiguracao">
							
						</div>		
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_informacaoItemConfiguracao" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.itemConfiguracao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoiframeInformacaoItemConfiguracao">
							
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_editarCadastrarProblema" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="problema.problema" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">						
						<div id="conteudoiframeEditarCadastrarProblema">
							
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_editarCadastrarMudanca" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="itemConfiguracaoTree.mudanca" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoiframeEditarCadastrarMudanca">
							
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			
			<div class="modal hide fade in" id="modal_editarCadastrarSolicitacaoFilha" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="solicitacaoServico.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id="conteudoframeCadastroNovaSolicitacaoFilho">
							
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
						<h3></h3>
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
						<h3></h3>
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
						<h3></h3>
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
						<h3></h3>
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
						<h3></h3>
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
			
			<!--
				Foi adicionado tratamento (data-backdrop="static" data-keyboard="false") no modal  para bloquear click externo.
				* 
				* @author maycon.fernandes
				* @since 25/10/2013 16:32
			*-->
			<div class="modal hide fade in" id="mensagem_insercao" aria-hidden="false" data-backdrop="static" data-keyboard="false">
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
						<a id="btFecharMensagem" href="#" class="btn " onclick="fecharModalNovaSolicitacao();" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
					</div>
			</div>

			<div class="modal hide fade in" id="modal_origem" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<!-- <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button> -->
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='slimScrollDiv'>
							<div class='slim-scroll' id='contentFrameOrigem'>
								<div id="conteudoframeExibirOrigem">
							
								</div>
							</div>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
							<a href="#" class="btn " onclick="preencherComboOrigem();" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
						</div>
					<!-- // Modal footer END -->
				</div>
			</div>
			
			<div class="modal hide fade in" id="modal_visualizaProblemaBaseConhecimento" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='slimScrollDiv'>
							<div class='slim-scroll' id='contentFrameOrigem'>
								<div id="conteudovisualizaProblemaBaseConhecimento">
							
								</div>
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
			
				<div class="modal hide fade in" id="modal_infoServicos" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="portal.carrinho.listagem" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div class='slimScrollDiv'>
							<div class='slim-scroll'>
								<div id="divInfoServicos">
								<div class="filter-bar">
									<div class='row-fluid'>
										<input type='text' class='span12' id='filtroTableServicos' placeholder="<i18n:message key="portal.carrinho.filtroBusca" />" onkeyup="filtroTableJs(this, 'tblListaServicos')">
									</div>
								</div>
									<table id='tblListaServicos' class='dynamicTable table  table-bordered table-condensed dataTable'>
											<tr>
												<th></th>
												<th ><i18n:message key="citcorpore.comum.servico" /></th>
											</tr>
									</table>
								</div>
							</div>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
							<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
						</div>
					<!-- // Modal footer END -->
				</div>
			</div>
			<%@include file="/novoLayout/common/include/libRodape.jsp" %>
			<script src="js/form_wizards.js"></script>
			<script src="js/solicitacaoServicoMultiContratos.js"></script>
			<script src="../../novoLayout/common/include/js/internacionalizar.js"></script>
			<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
			<script type="text/javascript" src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/UploadUtils.js'></script>
			<!-- Form Wizards Page Demo Script -->
			<script type="text/javascript">
				/* ============================================================
				 			ATENÇÃO
				 
				 NO JSP DEVE CONTER APENAS SCRIPTS QUE NECESSITAM DE SCRIPTLETS  
				
				================================================================*/

			/* 	ronnie.lopes
				Inicializa escondido Campo Título do Gravar Base Conhecimento no Load da Página */
				$(function() {
				<% if (mostraGravarBaseConhec.trim().equalsIgnoreCase("S")) { %>
						document.getElementById("divTituloSolucaoRespostaBaseConhecimento").style.display='none';
				<% } %>
				});
				var evento;
				
				<%if (!br.com.citframework.util.Util.isVersionFree(request)) {%>
						excluiProblema = function(indice) {
							if (indice > 0 && confirm('Confirma exclusão?')) {
								HTMLUtils.deleteRow('tblProblema', indice);
							}
						}
						
						excluiMudanca = function(indice) {
							if (indice > 0 && confirm('Confirma exclusão?')) {
								HTMLUtils.deleteRow('tblMudanca', indice);
							}
						}
						
						excluiIC = function(indice) {
							if (indice > 0 && confirm('Confirma exclusão?')) {
								HTMLUtils.deleteRow('tblIC', indice);
							}
						}
					<%}%>
				
				
				function gravar() {	
					if (evento == 13) {
						return;
					} 
					JANELA_AGUARDE_MENU.show();
					if(!validaForm()){
						JANELA_AGUARDE_MENU.hide();
						habilitaBotaoGravar();  
						return;
					}
					
					serializaMudanca();
					serializaProblema();
					serializaIC();
					serializaBaseConhecimento();
					
					/*  ronnie.lopes
					Campo Título Obrigatório caso Situação seje igual a Resolvida */
					if($("input[name='situacao']:checked").val() == "Resolvida") {
					<% if (mostraGravarBaseConhec.trim().equalsIgnoreCase("S")) { %>
						if($("#gravaSolucaoRespostaBaseConhecimento").is(":checked")){
							if($("#tituloBaseConhecimento").val() == "" || $("#tituloBaseConhecimento").val == null) {
								alert(i18n_message("solicitacaoServico.tituloObrigatorio"));
								habilitaBotaoGravar();
								JANELA_AGUARDE_MENU.hide();
								return;
							}
						}
					<% } %>
					}

					var todosPreenchidos = validarInformacoesComplementares();
					 if (!todosPreenchidos) {
						 habilitaBotaoGravar();
						 JANELA_AGUARDE_MENU.hide();
						 $('#ulWizard li:eq(2) a').tab('show');
						 scrolls();
				         return;
					 }  			
										
					document.form.urgencia.disabled = false;
					document.form.impacto.disabled = false;
					if(document.getElementById('flagGrupo').value == 0){
						informacoesComplementaresSerialize();
						document.form.idServico.disabled = false;
						document.form.idContrato.disabled = false;
						habilitaEmail();
						document.form.save();
					}else{
						if (document.getElementById("idGrupoAtual").value == ''){
							//var e = document.getElementById("idGrupoAtual");
							//var grupoAtual = e.options[e.selectedIndex].text;
							if (confirm('<i18n:message key="solicitacaoServico.grupoAtualVazio" />')){
							habilitaEmail();
							informacoesComplementaresSerialize();
							document.form.save(); 
							}else{
								habilitaBotaoGravar();
								JANELA_AGUARDE_MENU.hide();
								return;
							}
						}else{
							habilitaEmail();
							informacoesComplementaresSerialize();
							document.form.save(); 
						}
					}
					//habilitaBotaoGravar();
				}
				
				function desabilitaBotaoGravar(){
					var solicitacaoNova = false;
					<%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
						solicitacaoNova = true;
					<%}%>
					
					if(solicitacaoNova){
						document.getElementById('btnGravar').disabled = true;
					}else{
						document.getElementById('btnGravar').disabled = true;
						document.getElementById('btnGravarEContinuar').disabled = true;
					}
				}
				
				function habilitaBotaoGravar(){
					var solicitacaoNova = false;
					<%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
						solicitacaoNova = true;
					<%}%>
					
					if(solicitacaoNova){
							document.getElementById('btnGravar').disabled = false
					}else{
						document.getElementById('btnGravar').disabled = false;
						document.getElementById('btnGravarEContinuar').disabled = false;
					}
				}
				
				function gravarEContinuar() {
					desabilitaBotaoGravar(); 
					document.form.acaoFluxo.value = '<%=Enumerados.ACAO_INICIAR%>';
					gravar();
				}

				function gravarEFinalizar() {
					desabilitaBotaoGravar(); 
					document.form.acaoFluxo.value = '<%=Enumerados.ACAO_EXECUTAR%>';
					gravar();
				}
				
				function mostraMensagemInsercao(msg){
					document.getElementById('divInsercao').innerHTML = msg;
					$("#mensagem_insercao").modal("show");
					
					$('#mensagem_insercao').on('hide', function() { 
					    $('#tmensagem_insercao').modal('hide');
					    fecharSolicitacaoModal();
					});
					document.getElementById('mensagem_insercao').focus();
				}
				
				function fecharSolicitacaoModal(){
					var iframe = <%=iframe%>;
					/**
					* Motivo: Verifica se o parametro modalAsterisk é true e chama a função 'fecharModalSolicitacaoAsterisk'
					* Autor: flavio.santana
					* Data/Hora: 11/12/2013 10:15
					*/
					var modalAsterisk = <%=parametroAdicionalAsterisk%>
				    if(iframe){
				    	if(modalAsterisk)
				    		parent.fecharModalSolicitacaoAsterisk();
				    	else
							parent.fecharModalFilha();
				    }else{
				    	parent.pesquisarItensFiltro();
				    }
				}
				
				function fecharModalFilha(){
					$('#modal_editarCadastrarSolicitacaoFilha').modal('hide');
					document.form.fireEvent('abrirListaDeSubSolicitacoes');
				}
				
				function fecharModalSubSolicitacao(){
					var iframe = <%=iframe%>;
				    if(iframe){
						parent.fecharModalFilha();
				    }else{
				    	parent.fecharModalNovaSolicitacao();
				    }
				}
				
				function cancelar(){
					bootbox.confirm(i18n_message("solicitacaoServico.cancelarOperacao"), function(result) {
						if(result == true){
							fecharModalSubSolicitacao()
								$.gritter.add({
									title: 'CITSMART',
									text: i18n_message("MSG16"),
									class_name: 'gritter-primary',
									time: 1300
								});
						
							}
						});
				}				
				
				function serializaMudanca(){
			    	var mudancas = HTMLUtils.getObjectsByTableId('tblMudanca');
					document.form.colItensMudanca_Serialize.value =  ObjectUtils.serializeObjects(mudancas);
					}
				
				function serializaProblema(){
			    	var problemas = HTMLUtils.getObjectsByTableId('tblProblema');
					document.form.colItensProblema_Serialize.value =  ObjectUtils.serializeObjects(problemas);
					}
				
				function serializaIC(){
			    	var ics = HTMLUtils.getObjectsByTableId('tblIC');
					document.form.colItensIC_Serialize.value =  ObjectUtils.serializeObjects(ics);
					}
				
				function serializaBaseConhecimento(){
			    	var baseConhecimento = HTMLUtils.getObjectsByTableId('tblBaseConhecimento');
					document.form.colItensBaseConhecimento_Serialize.value =  ObjectUtils.serializeObjects(baseConhecimento);
					}
				
				/*
				Mário Júnior
				02/12/2013 - Sol. 123057
				- Criado para fechar popup quando der enter na mensagem de inserção
			 	*/
				$('#mensagem_insercao').on('show', function() {
					$(window).keydown(function(event){
					if(event.keyCode == 13){
						$("#mensagem_insercao").modal("hide");
						fecharSolicitacaoModal();
					}
					});
				});
				
				
				/*
					Pedro Lino
					23/10/2013 - Sol. 120948
					- Alterado height de iframeEditarCadastrarProblema e iframeEditarCadastrarMudanca para 550 para retirar barra de rolagem via iframe
				 */
				/*
				Rodrigo Pecci Acorse
				07/11/2013 - Sol. 123390
				- Os iframs que possuiam src definido aqui no load da página foram removidos e adicionados somente na ação do item. Os frames serão carregados somente quando necessário.
				*/
				$(window).load(function(){
					$('#divInformacoesComplementares').html('<iframe id="fraInformacoesComplementares" name="fraInformacoesComplementares" src="about:blank" class="inativo iframeSemBorda" width="100%" height="100%" style="width: 95%; height: 100%; border: none; overflow: auto"></iframe>');
					$('#conteudoCadastroNovoColaborador').html('<iframe id="frameCadastroNovoColaborador" src="about:blank" width="99%" height="545" class="iframeSemBorda"></iframe>');
					$('#conteudoframeBaseConhecimento').html('<iframe id="frameBaseConhecimento" src="about:blank" width="99%" height="460" class="iframeSemBorda"></iframe>');
					$('#conteudoframeExibirOrigem').html('<iframe id="frameExibirOrigem" src="about:blank" width="99%" height="400" class="iframeSemBorda"></iframe>');
					$('#conteudovisualizaProblemaBaseConhecimento').html('<iframe id="visualizaProblemaBaseConhecimento" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
					$('#conteudoPesquisaItemConfiguracao').html('<iframe id="framePesquisaItemConfiguracao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
					$('#conteudoAgendaAtvPeriodicas').html('<iframe id="frameAgendaAtvPeriodicas" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
					$('#conteudoCadastroCategoriaOcorrencia').html('<iframe id="frameCadastroCategoriaOcorrencia" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
					$('#conteudoCadastroOrigemOcorrencia').html('<iframe id="frameCadastroOrigemOcorrencia" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');
					$('#conteudoframeCadastroNovaSolicitacaoFilho').html('<iframe id="frameCadastroNovaSolicitacaoFilho" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>');					
					$('#conteudoiframeInformacaoItemConfiguracao').html('<iframe id="iframeInformacaoItemConfiguracao" src="about:blank" width="99%" height="530" class="iframeSemBorda"></iframe>'); 
					$('#conteudoiframeEditarCadastrarProblema').html('<iframe id="iframeEditarCadastrarProblema" src="about:blank" width="99%" height="550" class="iframeSemBorda"></iframe>'); 
					$('#conteudoiframeEditarCadastrarMudanca').html('<iframe id="iframeEditarCadastrarMudanca" src="about:blank" width="99%" height="550" class="iframeSemBorda"></iframe>'); 
				});
			</script>
		
</body>
</html>