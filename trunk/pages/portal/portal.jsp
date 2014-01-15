<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String exibirBotaoOrdemServico = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.HABILITA_BOTAO_ORDEMSERVICO, "N");
%>
<!doctype html public "">
<html>
	<head>	
		<%@include file="/novoLayout/common/include/titulo.jsp" %>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../gerenciamentoServicos/css/gerenciamentoServicos.css"></link>
		<link type="text/css" rel="stylesheet" href="css/portal.css"/>
		<script type="text/javascript" src="../../cit/objects/InfoCatalogoServicoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/ServicoContratoDTO.js"></script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>

		<div class="container-fluid fixed ">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar main hidden-print">
			
				<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
				<input type="hidden" id="rowIndex" name="rowIndex"/>
			</div>
	
			<div id="wrapper">
				<div class="separator top"></div>
					<!-- Inicio conteudo -->
					<div id="content">
						<div class="row-fluid">
							<div class="innerLR">
								<div class="widget">
									<!-- inicio das tabs -->
										<!-- 
											Correção do layout da aba
											@autor flavio.santana
											@since 29/10/2013
										 -->
										<div class="tabsbar tabsbar tabsbar-2 active-fill">
											<ul>
												<li class="glyphicons no-js show_thumbnails_with_lines active"><a href="#tabgerenc" data-toggle="tab"><i></i><i18n:message key="portal.gerenciamentoServico"/></a></li>
												<li class="glyphicons no-js notes"><a href="#tabbase" data-toggle="tab"><i></i><i18n:message key="portal.baseConhecimento"/></a></li>
												<li class="glyphicons no-js circle_question_mark"><a href="#tabfaq" data-toggle="tab"><i></i><i18n:message key="portal.faq"/></a></li>
												<%if (exibirBotaoOrdemServico.equals("S")){ %>
												<li class="glyphicons no-js notes"><a href="../../pages/informacoesContrato/informacoesContrato.load?portal=true" target="_blank" id="tabOrdensDeServico"><i></i><i18n:message key="portal.ordemServico"/></a></li>
												<%} %>
											</ul>
										</div>
										<!-- fim das tabs -->
										<div class="tab-content">			
											<div class="separator top"></div>	
											<!-- inicio da tab gerenciamento de serviços -->
											<div class="tab-pane active" style="min-height:650px" id="tabgerenc">
												<div class="innerLR">
														<div class="widget" data-toggle="collapse-widget">
															<div class="widget-head">
																<h4 class="heading"><i18n:message key="portal.carrinho.servico" /></h4>
															</div>
															<div class="widget-body collapse in">								
																<div class="row-fluid" >									
																	<div class="span12">										
																		<div id="titulo" >
																			<div class="row-fluid inicio">
																				<div class="span2">
																					<!-- <a href="" class="widget-stats small">
																						<span class="glyphicons shopping_cart"><i></i></span>
																						<span class="count label label-primary">238</span>
																					</a> -->
																				</div>
																			</div>
																		</div>
																	</div>
																	<!-- Tabs -->
																	<div class="tabsbar tabsbar-2 active-fill">
																		<ul class="row-fluid row-merge">
																			<li class="span3 glyphicons cargo active"><a href="#tab1-4" data-toggle="tab"><i></i><i18n:message key="portal.carrinho.listagem"/></a></li>
																			<li class="span3 glyphicons cart_in"><a href="#tab2-4" data-toggle="tab"><i></i> <span><i18n:message key="portal.carrinho.nomeAmigavel.servico"/></span></a></li>
																		</ul>
																	</div>
																	<!-- Fim tabs -->
																	<div class="tab-content">
																		<div class="tab-pane active" id="tab1-4"> <!-- conteudo da tab Listagem de ServiÃ§os -->
																		
																			<div class="span12 filtro">		
																				<div class="row-fluid" >									
																					<div class="span6">				
																						<div class="input-append">
																						  	<input class="span11" id="stringDigitada" type="text" placeholder='<i18n:message key="citcorpore.comum.buscar"/>' onkeyup="filtroListaJs(this, 'listaServicos');">
																					  		<button class="btn btn-default" type="button" ><i class="icon-search"></i></button>
																					  		<!-- <button class="btn btn-default" type="button" onclick="filtroTableJs(document.getElementById('stringDigitada'), document.getElementById('listaServicos') )" ><i class="icon-search"></i></button> -->
																						</div>
																					</div>
																				</div>
																			</div>
																			
																			<form name='formListaServicos' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/portal'>
																				<input type="hidden" id="idCatalogoServico" name="idCatalogoServico"/>
																				<input type="hidden" id="idContratoUsuario" name="idContratoUsuario"/>
																				<input type='hidden' name='servicosEscolhidos' id='servicosEscolhidos'/>
																				<input type='hidden' name='filtroCatalogo' id='filtroCatalogo'/>
																				<input type='hidden' name='valorTotalServico' id='valorTotalServico'/>	
																				<input type='hidden' name='listaServicosLancados' id='listaServicosLancados'/>
																																	
																				<!-- Início listagem de itens -->
																				<div class="shop-client-products list" id="listaServicos"></div>
																				<!-- Fim listagem de itens -->
																			</form>
																		</div>
																		<div class="tab-pane" id="tab2-4">
																			<!-- Shopping cart -->
																			<div class="shop-client-products cart">
																				<form name='formCarrinho' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/portal'>
																					<input type='hidden' name='servicosLancados' id='servicosLancados'/>
																					<!-- Cart table -->
																					<table id="carrinho" class="table table-bordered table-primary table-striped table-vertical-center checkboxs js-table-sortable">
																						<thead>
																							<tr>
																								<th class="span1"></th>
																								<th class="span2" ><i18n:message key="portal.carrinho.nomeTecnico.servico"/></th>
																								<th class="span3"><i18n:message key="portal.carrinho.descricao"/></th>
																								<th class="span3"><i18n:message key="portal.carrinho.observacao"/></th>																								
																								<th class="span1"><i18n:message key="carrinho.categoria"/></th>
																								<th class="span1"><i18n:message key="portal.carrinho.preco"/></th>
																								<th class="span1"><i18n:message key="carrinho.excluir"/></th>
																							</tr>
																						</thead>
																						<tbody>
								
																						</tbody>
																					</table>
																					<!-- // Cart table END -->
																				
																					<div class="separator bottom"></div>
																					<!-- Row -->
																					<div class="row-fluid">
																					<!-- Column -->
																						<div class="span5">
																							<!-- <div class="box-generic center">
																								<strong>Discount Code:</strong>
																								<div class="separator bottom"></div>
																								<input type="text" value="723-WTX31" class="span12" />
																								<button class="btn btn-inverse">Apply code</button>
																							</div> -->
																						</div>
																						<!-- Column END -->
																						<!-- Column -->
																						<div class="span4 offset3">
																							<table class="table table-borderless table-condensed cart_total">
																								<tbody>
																									<tr>
																										<td colspan="2">
																											<div class="separator bottom"></div>
																											<span class="label center label-block large" id="imprimeTotal"><i18n:message key="carrinho.total"/></span>
																										</td>
																									</tr>
																									<tr>
																										<td colspan="2"><button type="button" onclick="finalizarCarrinho()" class="btn btn-block btn-primary btn-icon glyphicons right_arrow"><i></i><i18n:message key="carrinho.Concluir"/></button></td>
																									</tr>
																								</tbody>
																							</table>
																						</div>
																						<!-- // Column END -->
																					</div>
																					<!-- // Row END -->
																				</form>
																			</div>	<!-- // Shopping cart END -->
																		</div>
																	</div>
																</div>
																<div class="separator top"></div>
																<div class="row-fluid">
																	<div class="span12">										
																		<div id="titulo" >
																		
																		</div>
																	</div>
																</div>	
															</div>
													</div>
													 <div class="widget" data-toggle="collapse-widget">
														<div class="widget-head">
															<h4 class="heading"><i18n:message key="portal.carrinho.solicitacoes"/></h4>
														</div>
														<div class="widget-body collapse in">	
															<form id='formGerenciamento'  name='formGerenciamento' method='post' action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicosPortal/gerenciamentoServicosPortal">																
																<cit:gerenciamentoField classeExecutora="br.com.centralit.citcorpore.ajaxForms.GerenciamentoServicosPortal" paginacao="true" tipoLista="1"></cit:gerenciamentoField>
															</form>
														</div>
													</div>
												</div>	
											</div> <!-- fim da tab gerenciamento -->
											<!-- início da tab Base de Conhecimento -->
											<div class="tab-pane" style="min-height:650px" id="tabbase">
												<div class="innerLR">
													<div class="widget" data-toggle="collapse-widget">
														<div class="widget-head">
															<h4 class="heading"><i18n:message key="baseConhecimento.baseConhecimento"/></h4>
														</div>
														<div class="widget-body collapse in">
															<div class="span12 filtro">		
																<div class="row-fluid" >									
																	<div class="span6">				
																		<div class="input-append">
																	  		<input class="span12" id="stringDigitada" type="text" placeholder="<i18n:message key="citcorpore.comum.buscar"/>" onkeyup="filtroTableJs(this, 'tableBc');">
																			<button class="btn btn-default" type="button" ><i class="icon-search"></i></button>
																			<!-- <button class="btn btn-default" type="button" onclick="filtroTableJs(document.getElementById('stringDigitada'), document.getElementById('listaServicos') )" ><i class="icon-search"></i></button> -->
																		</div>
																	</div>
																</div>
															</div>
															<div class="widget-body collapse in">																	
																<ul id="column4" class="connectedSortable" style="margin:0px">	</ul>
															</div>
														</div>	
													</div>
												</div>			
											</div>
											<!-- fim da tab Base de Conhecimento -->
											<!-- início da tab FAQ -->
											<div class="tab-pane" style="min-height:650px" id="tabfaq">
												<div class="innerLR">
													<div class="widget" data-toggle="">
														<div class="widget-head">
															<h4 class="heading"><i18n:message key="baseconhecimento.faq"/></h4>
														</div>
														<div class="widget-body collapse in">
															<div class="span12 filtro">		
																<div class="row-fluid" >									
																	<div class="span6">				
																		<div class="input-append">
																	  		<input class="span11" id="stringDigitada" type="text" placeholder="<i18n:message key="citcorpore.comum.buscar"/>" onkeyup="filtroDivsJs(this, 'faqs');">
																			<button class="btn btn-default" type="button" ><i class="icon-search"></i></button>
																			<!-- <button class="btn btn-default" type="button" onclick="filtroTableJs(document.getElementById('stringDigitada'), document.getElementById('listaServicos') )" ><i class="icon-search"></i></button> -->
																		</div>
																	</div>
																</div>
															</div>
																<div class="widget-body"><!-- Inicio da div class="widget-body" -->
																	<div class="tab-content"><!-- Inicio da div class="tab-content" -->
																		<h3><i18n:message key="faq.faq"/></h3>
																		<div class="accordion accordion-2" id="tabAccountAccordion">
																		</div>
																			<div class="accordion accordion-2" id="accordion"><!-- Inicio da div id="accordion" -->
																				<div id="faqs" class="accordion-group"><!-- Inicio da div id="grupo" -->
																				</div><!-- Fim da div id="grupo" -->
																			</div><!-- Fim da div id="accordion" -->
																	</div><!-- Fim da div class="tab-content" -->		
																</div><!-- Fim da div class="widget-body" -->
														</div>
													</div>
											</div>
										</div>
										
										<!-- Ordens de Serviço -->

									<!-- fim da tab faq -->
									</div>
								</div>
							</div>
						</div>	
						<%@include file="/novoLayout/common/include/rodape.jsp" %>
						<script type="text/javascript" src="js/portal2.js"></script>
					</div>
				</div><!--  Fim conteudo-->
			</div>
	<!-- 	</div> -->	
		
		<!-- MODAL NOVA SOLICITACAO -->
		<div class="modal hide fade" id="modal_novaSolicitacao" tabindex="-1" data-backdrop="static" data-keyboard="false">
				<!-- Modal heading -->
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">×</button>
					<h3><i18n:message key="citcorpore.comum.solicitacao"/></h3>
				</div>
				<!-- // Modal heading END -->
				<!-- Modal body -->
				<div class="modal-body">
					<div class='slimScrollDiv'>
						<div class='slim-scroll' id='contentFrameNovaSolicitacao'>
							<iframe id='frameNovaSolicitacao' ></iframe>
						</div>
					</div>
				</div>
				<!-- // Modal body END -->
				<!-- Modal footer -->
				<!-- // Modal footer END -->
		</div>			
		<%-- <div id="chat-h" style="visibility: hidden;position: absolute;left: 0;top: 0;width: 100%;height: 100%;overflow: hidden;z-index: 3;">
			<div id="cit-chat" style="margin: 30px auto 30px;width: 94%;">
				<div style="float:right">
					<div id="chatf" style="width: 267px; height: 636px;position: relative;">
						<div style="position: fixed;bottom: 0;">
							<div style="width: 267px;/* height: 380px; */visibility: visible;box-shadow: 0 2px 6px rgba(0,0,0,0.2);">
								<div class="row-fluid">
									<div class="span12">	
										<!-- Chat -->
										<div class="widget margin-none" style="padding-bottom: 0px !important;" data-toggle="collapse-widget" data-collapse-closed="false">
										
											<div class="widget-head">
												<h4 class="heading">Chat</h4>
											</div>
											<div class="widget-body">
												<div class="tab-content">
													<!-- Filter Users Tab -->
													<div class="tab-pane active">
														<iframe src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/chatSmartListaContatos/chatSmartListaContatos.load" style="border: none !important" frameborder="0" scrolling="no" width="235" height="320"></iframe>
													</div>
												</div>											
											</div>
											
										</div>
										<!-- // Chat END -->
									
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div> --%>
		
	</body>
</html>