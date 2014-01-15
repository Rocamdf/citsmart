<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema" %>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA"%>
<%@ page import="br.com.centralit.citcorpore.util.ParametroUtil" %>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

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
	
	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));	
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));
	
	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil
			.getValorParametroCitSmartHashMap(br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO,"");

	String tarefaAssociada = (String) request
			.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null) {
		tarefaAssociada = "N";
	}
	String iframe = "";
	iframe = request.getParameter("iframe"); 
	
	String nomeUsuario = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null)
		nomeUsuario = usuario.getNomeUsuario();
	
	String URL_SISTEMA = "";
	URL_SISTEMA = CitCorporeConstantes.CAMINHO_SERVIDOR + request.getContextPath()+'/';
%>

<!DOCTYPE HTML>
<html>
	<head>	
		<meta name="viewport" content="width=device-width" />
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<title><i18n:message key="citcorpore.comum.title" /></title>
		<link type="text/css" rel="stylesheet" href="css/gerenciamentoServicos.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/jqueryautocomplete.css"/>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">	</cit:janelaAguarde>
	<body >
		<div class="container-fluid fixed ">
			<!--  Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 15:54 - ID Citsmart: 120948 - 
			* Motivo/Comentário: Verificação para abrir com iframe -->
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<% if(iframe == null) { %>
			<div class="navbar main hidden-print">
			
		
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>					
				
				
			</div>
	<% } %>
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">				
					<div class="separator top"></div>	
					<div class="row-fluid">
						<div class="innerLR">
							<div class="widget">
								<div class="widget-head">
									<h4 class="heading"><i18n:message key="gerenciaservico.solicitacoes"/></h4>
								</div>
								<div class="widget-body collapse in">		
									<div class="tabsbar">
										<ul>
											<li class="active"><a href="#tab1-3" data-toggle="tab"><i18n:message key="gerenciamentoservico.gerenciarSolicitacoes"/></a></li>
											<li class="" onclick="renderizarGraficos()"><a href="#tab2-3" data-toggle="tab" ><i18n:message key="citcorpore.comum.graficos"/></a></li>
											<li class="" onclick="renderizarResumoSolicitacoes()"><a href="#tab3-3" data-toggle="tab"><span><i18n:message key="gerenciamentoservico.resumoSolicitacoes"/></span></a></li>
										</ul>
									</div>
									<div class="tab-content">
										<div class="tab-pane active" id="tab1-3">
											<form id='formGerenciamento'  name='formGerenciamento' method='post' action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicosImpl/gerenciamentoServicosImpl">
												<cit:gerenciamentoField classeExecutora="br.com.centralit.citcorpore.ajaxForms.GerenciamentoServicosImpl" paginacao="true" tipoLista="1"></cit:gerenciamentoField>													
											</form>
											<form id='form' name='form' method='post' action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicos/gerenciamentoServicos">
												<input type="hidden" id='idTarefa' name='idTarefa'>
												<input type="hidden" id='idSolicitacaoSel' name='idSolicitacaoSel'>
												<input type="hidden" id='acaoFluxo' name='acaoFluxo'>
											</form>
											
										</div>
										<div class="tab-pane" id="tab2-3" >
											
											<span class='btn btn-primary btn-icon glyphicons refresh' id='btnAtualizarGraficos' ><i></i><i18n:message key="citcorpore.comum.atualizarGraficos"/></span>
											
											<!-- Widget -->
											<div class="widget">
											
												<!-- Widget heading -->
												<div class="widget-head">
													<h4 class="heading"><i18n:message key="citcorpore.comum.solicitacaoSituacao"/></h4>
												</div>
												<!-- // Widget heading END -->
												
												<div class="widget-body">
												
													<!-- Simple Chart -->
													<div style="width: 100%!important;" class='row-fluid'>
														<div id="tdAvisosSol" style="height: 350px; border: 1px solid #D8D8D8" class="span6"></div>
														<div id="divGrafico" style="height: 350px; border: 1px solid #D8D8D8" class="span6"></div>
													</div>
													<!-- <div id="divGrafico" style="height: 350px; width: 100%!important;"></div> -->
												</div>
											</div>
											<!-- // Widget END -->
											
											<!-- Widget -->
											<div class="widget" style="float:left; width:49%">
											
												<!-- Widget heading -->
												<div class="widget-head">
													<h4 class="heading"><i18n:message key="citcorpore.comum.solicitacaoPrioridade"/></h4>
												</div>
												<!-- // Widget heading END -->
												
												<div class="widget-body">
												
													<!-- Chart with lines and fill with no points -->
													<div class='row-fluid'>
														<div id="divGrafico2" style="height: 350px; border: 1px solid #D8D8D8" class="span12"></div>														
													</div>
												</div>
											</div>
											<!-- // Widget END -->
											
											<!-- Widget -->
											<div class="widget" style="float:right; width:49%;">
												
												<!-- Widget heading -->
												<div class="widget-head">
													<h4 class="heading"><i18n:message key="citcorpore.comum.solicitacaoGrupo"/></h4>
												</div>
												<!-- // Widget heading END -->
												
												<div class="widget-body">
												
												<div class='row-fluid'>
														<div id="divGrafico3" style="height: 350px; border: 1px solid #D8D8D8" class="span12"></div>
												</div>
												
													<!-- Ordered bars Chart -->
													
												</div>
											</div>
											<!-- // Widget END -->
											
											<!-- Widget -->
										<!-- 	<div class="widget">
											
												Widget heading
												<div class="widget-head">
													<h4 class="heading">Pie chart</h4>
												</div>
												// Widget heading END
												
												<div class="widget-body">
												
													Pie Chart
													<div id="chart_pie" style="height: 250px;"></div>
												</div>
											</div> -->
											<!-- // Widget END -->												
										</div>
										<div class="tab-pane" id="tab3-3">
											
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
				<!--  Fim conteudo-->
		
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
   				<script type="text/javascript" src="js/gerenciamentoServicos.js" ></script>
				<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
				<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/ObjectUtils.js"></script>
				<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
				<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>	
				<script>
		
				/* ============================================================
					ATENÇÃO
		
					NO JSP DEVE CONTER APENAS SCRIPTS QUE NECESSITAM DE SCRIPTLETS  
		
				================================================================*/
				
				jQuery(function($){
					$("#telefonecontato").mask('(999) 9999-9999');
				});
				
				capturarTarefa = function(responsavelAtual, idTarefa) {
					var msg = "";
					if (responsavelAtual == '')
						msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '<%=nomeUsuario%>'  ?";
					else 	
						msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +" '<%=nomeUsuario%>' "+ i18n_message("gerencia.confirm.atribuicaotarefa_3");
						
					bootbox.confirm(msg, function(result) {
						if(result == true){
							JANELA_AGUARDE_MENU.show();
							document.form.idTarefa.value = idTarefa;
							document.form.fireEvent('capturaTarefa');
						
							}
					});
				};
				
				function gravarEContinuar() {
					document.form.acaoFluxo.value = '<%=Enumerados.ACAO_INICIAR%>';
					gravarSolicitacao();
				}
		
				function gravarEFinalizar2() {
					document.form.acaoFluxo.value = '<%=Enumerados.ACAO_EXECUTAR%>';
					gravarSolicitacao();
				}
		
				function gravarSolicitacao22() {
					
					document.form.save(); 
					
				 	if(document.form.descricao.innerHTML == "<br />" || document.form.descricao.innerHTML == "&lt;br /&gt;"){
						alert('Informe a descrição!');
						return;
					}
					
					if (document.form.descricao.value == '' || document.form.descricao.value == '&nbsp;'
						|| document.form.descricao.value == '<p></p>'){
						alert('Informe a descrição!');
						return;
					}
					if (document.form.descricao.value == 'Resolvida'){
						if (document.form.resposta.value == '' || document.form.resposta.value == ' '){
							alert('Informe a resposta!');
							return;				
						}
					} 
		
				     if (!validarInformacoesComplementares())
				        return; 
				     
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
							
						var objsBaseConhecimento = HTMLUtils.getObjectsByTableId('tblBaseConhecimento');
						document.form.colConhecimentoSolicitacao_Serialize.value = ObjectUtils.serializeObjects(objsBaseConhecimento);
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
				
				var dadosGrafico;
				var dadosGrafico2;
				var dadosGrafico4;
				var dadosGerais;
				var scriptTemposSLA = '';
				var temporizador;
				exibirGraficos = function(json_tarefas) {
					var tarefas = [];
					//json_tarefas = '';
					//$("#ajaxX").text(json_tarefas);
					var qtdeAtrasadas = 0;
					var qtdeSuspensas = 0;
					var qtdeEmAndamento = 0;
					var qtdePri1 = 0;
					var qtdePri2 = 0;
					var qtdePri3 = 0;
					var qtdePri4 = 0;
					var qtdePri5 = 0;
					var qtdeItens = 0;
					var colGrupoSol = new HashMap();
					scriptTemposSLA = "";

					arrayTarefas = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(json_tarefas); 
				    for(var i = 0; i < arrayTarefas.length; i++){
			            var tarefaDto = arrayTarefas[i];
			            tarefaDto.solicitacaoDto = ObjectUtils.deserializeObject(tarefaDto.solicitacao_serialize);	     
			            tarefaDto.elementoFluxoDto = ObjectUtils.deserializeObject(tarefaDto.elementoFluxo_serialize);         
				    }

					var strTableTemposSLA = '';
					strTableTemposSLA += "<img width='20' height='20' ";
					strTableTemposSLA += "alt='"+  i18n_message('ativaotemporizador')+"' id='imgAtivaTimer' style='opacity:0.5;display:none' ";
					strTableTemposSLA += "title='"+ i18n_message('citcorpore.comum.ativadestemporizador') +"' ";
					strTableTemposSLA += "src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/cronometro.png'/>";	
					strTableTemposSLA += "<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\">";
					strTableTemposSLA = strTableTemposSLA + "<tr><td><b><i18n:message key='gerenciaservico.slasandamento' /></b></td></tr>";	
					inicializarTemporizador();
					for(var i = 0; i < arrayTarefas.length; i++){
						var idSolicitacaoServico = "";
						var contrato = "";
						var responsavel = "";
						var servico = "";
						var solicitante = "";
						var prioridade = "";
						var situacao = "";
						var sla = "";
						var dataHoraSolicitacao = "";
						var dataHoraLimite = "";
						var grupoAtual = "";
						var farolAux = "";
		
						var tarefaDto = arrayTarefas[i];
						var solicitacaoDto = tarefaDto.solicitacaoDto;
						if (solicitacaoDto != null) {
							if (solicitacaoDto.prioridade == '1'){
								qtdePri1++;
							}
							if (solicitacaoDto.prioridade == '2'){
								qtdePri2++;
							}
							if (solicitacaoDto.prioridade == '3'){
								qtdePri3++;
							}
							if (solicitacaoDto.prioridade == '4'){
								qtdePri4++;
							}
							if (solicitacaoDto.prioridade == '5'){
								qtdePri5++;
							}	
							var grupoNome = solicitacaoDto.grupoAtual;
							if (grupoNome == null){
								grupoNome = '-- '+ i18n_message("citcorpore.comum.aclassificar")+ '--';
							}
							var auxGrp = colGrupoSol.get(grupoNome);
							if (auxGrp != undefined){
								auxGrp.qtde++;
							}else{
								var grupoQtde = new GrupoQtde();
								grupoQtde.id = grupoNome; 
								grupoQtde.qtde = 1;
								colGrupoSol.set(grupoNome, grupoQtde);
							}								
							
							idSolicitacaoServico = ""+solicitacaoDto.idSolicitacaoServico;
							responsavel = ""+trocaBarra(solicitacaoDto.responsavel);
							contrato = ""+trocaBarra(solicitacaoDto.contrato);
							servico = ""+trocaBarra(solicitacaoDto.servico);
							solicitante = ""+trocaBarra(solicitacaoDto.solicitanteUnidade);
							if (solicitacaoDto.prazoHH < 10)
								sla = "0";
							sla += solicitacaoDto.prazoHH + ":";
							if (solicitacaoDto.prazoMM < 10)
								sla += "0";
							sla += solicitacaoDto.prazoMM;
							prioridade = ""+solicitacaoDto.prioridade;
							dataHoraSolicitacao = solicitacaoDto.dataHoraSolicitacaoStr;
							if (solicitacaoDto.situacaoSLA == "<%=SituacaoSLA.A%>") { 
								dataHoraLimite = solicitacaoDto.dataHoraLimiteStr;
							}
							grupoAtual = trocaBarra(solicitacaoDto.grupoAtual);
							
							if (parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>" && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Cancelada%>"){
								qtdeAtrasadas++;
							}else if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>"){
								qtdeSuspensas++;
							}else {
								qtdeEmAndamento++;
								if (qtdeItens < 15){
									if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S' && solicitacaoDto.situacaoSLA == 'A'){
										scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idSolicitacaoServico + "', " + "'barraProgresso" + solicitacaoDto.idSolicitacaoServico + "', "
										    + "'" + solicitacaoDto.dataHoraInicioSLAStr + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
										strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label class='crono' id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'></label>";
										strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idSolicitacaoServico + "'></div></td></tr>";
									}else if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar == 'S') {
										strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><i18n:message key='citcorpore.comum.acombinar' /> </font></label>";
									}else if (solicitacaoDto.situacaoSLA == 'N'){
			                            strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><i18n:message key='citcorpore.comum.naoIniciado' /> </font></label>";
									}else if (solicitacaoDto.situacaoSLA == 'S'){
			                            strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><i18n:message key='citcorpore.comum.suspenso' /> </font></label>";
									}
								}
								qtdeItens++;
							}
						} 
				        tarefas[i] = {
						        		 iniciar:			tarefaDto.executar
						        		,executar:			tarefaDto.executar
						        		,delegar:			tarefaDto.delegar
				        				,idSolicitacaoServico:		idSolicitacaoServico
				        			 	,contrato: 			contrato
				        			 	,responsavel: 		responsavel
				        			 	,servico: 			servico
				        			 	,solicitanteUnidade: 		solicitante
				        			 	,prioridade: 		prioridade
				        			 	,dataHoraSolicitacao: dataHoraSolicitacao
				        			 	,descricao: 		trocaBarra(tarefaDto.elementoFluxoDto.documentacao)
						        		,status:	 		""
							        	,atraso:			solicitacaoDto.atrasoSLA
						        		,sla:	 			sla
						        		,atrasoSLA:	 		""
				        			 	,dataHoraLimite: 	dataHoraLimite
				        			 	,responsavelAtual:  tarefaDto.responsavelAtual
				        			 	,compartilhamento:  tarefaDto.compartilhamento
				        			 	,grupoAtual:  grupoAtual
				        			}
					}
					strTableTemposSLA = strTableTemposSLA + '</table>';
					if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
						var info = '';
						if (qtdeAtrasadas > 0){
							info += ' <font color="red"><b>' + qtdeAtrasadas + '</b> <i18n:message key="solicitacaoServico.solicitacoes_incidentes_atrasado" /></font><br>';
						}
						if (qtdeSuspensas > 0){
							info += ' <b>' + qtdeSuspensas + '</b> <i18n:message key="solicitacaoServico.solicitacoes_incidentes_suspenso" />';
						}
						info = ' <i18n:message key="solicitacaoServico.existem" /><br>' + info + '<br><div id="divTemposSLA" style="height:280px; overflow:auto; border: 1px solid #999999">' + strTableTemposSLA + '</div>';
						/* if (document.getElementById('frameNovaSolicitacao').src == "about:blank"){ */
							info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:15px">&nbsp;</td><td style="vertical-align: top; width: 100%; height: 250px">' + info + '</td></tr></table>';
							 document.getElementById('tdAvisosSol').innerHTML = info;
							dadosGrafico = [{label: i18n_message('citcorpore.comum.normal'), data: qtdeEmAndamento}, {label: i18n_message('citcorpore.comum.suspensa'), data: qtdeSuspensas},{label:  i18n_message('citcorpore.comum.vencido'), data: qtdeAtrasadas}];
							dadosGrafico2 = [{label:" 1 ", data: qtdePri1},{label:" 2 ", data: qtdePri2},{label: " 3 ", data: qtdePri3},{label: " 4 ", data: qtdePri4},{label: " 5 ", data: qtdePri5}];
							window.setTimeout(atualizaGrafico, 1000);
							window.setTimeout(atualizaGrafico2, 1000);
							
							var colArray = colGrupoSol.toArray();
							dadosGerais = new Array();
							if (colArray){
								for (var iAux = 0; iAux < colArray.length; iAux++){
									dadosGerais[iAux] = {label: colArray[iAux].id, data: colArray[iAux].qtde};
								}
							}
							window.setTimeout(atualizaGrafico3, 1000);
							document.formGerenciamento.quantidadeAtrasadas.value = qtdeAtrasadas;
							document.formGerenciamento.quantidadeTotal.value = (qtdeEmAndamento + qtdeSuspensas + qtdeAtrasadas);
							/* window.setTimeout(atualizaGrafico4, 1000); */
						/* } */
					}

				  	JANELA_AGUARDE_MENU.hide()
				};
				
				</script>	
			</div>	
		</div>	
<!-- 								===========================================================
									================= AREA DE MODAL ===========================	
									===========================================================																																																									-->
			
			<!-- MODAL NOVA SOLICITACAO -->
			<div class="modal hide fade in" id="modal_novaSolicitacao" tabindex="-1" data-backdrop="static" data-keyboard="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentFrameNovaSolicitacao'>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<!-- // Modal footer END -->
			</div>
			<!-- MODAL RECLASSIFICAR -->
			
			<div class="modal hide fade in" id="modal_alterarSLA" aria-hidden="false" data-width="850">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentFrameAlterarSLA'>
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
			
				<!-- MODAL RECLASSIFICAR -->
			
			<div class="modal hide fade in" id="modal_reclassificar2" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentReclassificarSolicitacao2'>
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
			
			<div class="modal hide fade in" id="modal_agendarAtividade" aria-hidden="false" data-width="850">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentFrameAgendarAtividade'>
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
			
			<div class="modal hide fade in" id="modal_agenda" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.agenta" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentFrameagendaAtvPeriodicas'>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
							<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
						</div> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_SuspenderReativarSolicitacao" aria-hidden="false" data-width="700">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="suspensaoReativacaoSolicitacao.tituloPopUp" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
							
					<div class="modal-body">
						<div id='contentFrameSuspenderReativarSolicitacao'>
						</div>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
							<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
						</div> 
					</div>
					<!-- // Modal footer END -->
			</div>
			
			
			
			<div class="modal hide fade in" id="modal_exibirDelegacaoTarefa" aria-hidden="false" data-width="850">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentFrameExibirDelegacaoTarefa'>
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
			
			<div class="modal hide fade in" id="modal_suspender" aria-hidden="false" data-width="750">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacao"/></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentFrameSuspender'>
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
			
			<div class="modal hide fade in" id="modal_pesquisaGeralSolicitacao" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentPesquisaGeralSolicitacao'>
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
			
			<div class="modal hide fade in" id="modal_reclassificarSolicitacao" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.reclassificaosolicitacao"/></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<div id='contentframeReclassificarSolicitacao'>
						</div>
					</div>
					
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<!-- <div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
							<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.cancelar" /></a> 
							<button type="button" data-dismiss="modal" class="btn  btn-primary " onclick='gravarSolicitacao();' id="btGravar2"><i18n:message key="citcorpore.comum.gravar" /></button>
						</div>
					</div> -->
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_criarSubSolicitacao" aria-hidden="false" data-width="700">
			
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="gerenciaservico.duplicarSolicitacao"/></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name="formInformacoesContato" id="formInformacoesContato" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos' >
							<input type='hidden' name='idSolicitante' id='idSolicitante' />
							<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
							<input type='hidden' name='idContrato' id='idContrato' />
							<input type="hidden" name="idTipoDemandaServico" id="idTipoDemandaServico" />
							<input type='hidden' name='situacaoFiltroSolicitante' id='situacaoFiltroSolicitante' />
							<input type='hidden' name='buscaFiltroSolicitante' id='buscaFiltroSolicitante' />
							<input type='hidden' name='nomecontato' id='nomecontato' />
							
							<input type='hidden' name='situacao' id='situacao' />
							<input type='hidden' name='idGrupoAtual' id='idGrupoAtual' />
							
										<div class='row-fluid'>
											<div class="span12">
												<label class="strong campoObrigatorio"><i18n:message key="origemAtendimento.origem" /></label>
													<select name='idOrigem' id='idOrigem' ></select>
											</div>
										</div>
										
										<div class='row-fluid'>
											<div class="input-append">
												<label class="strong campoObrigatorio" >
													<i18n:message key="solicitacaoServico.solicitante" />
												</label>
											  	<input class="span12"  type="text" name="nomeSolicitante" id="nomeSolicitante" required="required"  placeholder="Digite o nome do solicitante">
											  	<span class="add-on"><i class="icon-search"></i></span>
												<span class="btn btn-mini btn-primary btn-icon glyphicons search modal_lookupSolicitante" href="#modal_lookupSolicitante" data-toggle="modal" data-target="#modal_lookupSolicitante"><i></i> <i18n:message key="citcorpore.comum.pesquisaAvancada" /></span>
											</div>
										</div>
										<div class='row-fluid'>
											<div class="span6">
													<label class="strong campoObrigatorio" ><i18n:message
															key="solicitacaoServico.emailContato" /></label>
													<input id="emailcontato" type='text'  name="emailcontato" maxlength="120" class="span12" />
											</div>
											<div class="span6">
													<label class="strong campoObrigatorio" ><i18n:message key="solicitacaoServico.telefoneDoContato" /></label>
											
														<input id="telefonecontato" type='text' name="telefonecontato" maxlength="13"  />
											
											</div>
										</div>
										<div class='row-fluid'>
											<div class="span7">
													<label class="strong campoObrigatorio" title="<i18n:message key="colaborador.cadastroUnidade"/>" >
														<i18n:message key="unidade.unidade"/>
													 </label>
												
														<select name="idUnidade" id="idUnidade"  onchange = "document.form.fireEvent('preencherComboLocalidade');"></select>
												
											</div>
										</div>
										<div class='row-fluid'>
											<div class="span6">
													<label class="strong"  title="<i18n:message key="colaborador.cadastroUnidade"/>" >
														<i18n:message key="solicitacaoServico.localidadeFisica"/>
													 </label>
													<select name="idLocalidade" id='idLocalidade'></select>
											</div>
										</div>
										<div class='row-fluid'>
											<div class="controls">
													<label  class="strong"><i18n:message key="solicitacaoServico.observacao" /></label>
													<div class="controls">
														<textarea  class="span12" rows="5" cols="100" id="observacao" name="observacao"></textarea>
													</div>
											</div>
										</div>
						
							
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					 <div class="modal-footer">
						<div class="form-actions">							 
							<a href="#" class="btn btn-primary"  onclick="duplicarSolicitacao();"><i18n:message key="citcorpore.comum.gravar" /></a> 
							<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
						</div>
					</div> 
					<!-- // Modal footer END -->
			</div>
			
			<div class="modal hide fade in" id="modal_exibirSubSolicitaces" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="citcorpore.comum.solicitacaoSubSolicitacao" /></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form id="formIncidentesRelacionados" name='formIncidentesRelacionados' method="post" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/incidentesRelacionados/incidentesRelacionados'>
							<input type='hidden' name='idSolicitacaoIncRel' value=''/>
							<div id="tabelaIncidentesRelacionados"></div>
					</form>
						
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div class="form-actions">
							<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
						</div>
					<!-- // Modal footer END -->
					</div>
			</div>
			
			<div class="modal hide fade in" id="modal_lookupSolicitante" aria-hidden="false" data-width="600">
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
						<div class="form-actions">
							<a href="#" class="btn btn-primary" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
						</div> 
					</div>
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
		</div>  --%>
			
		<script type="text/javascript">
		
		/*
			Rodrigo Pecci Acorse
			07/11/2013 - Sol. 123390
			- Os iframs que possuiam src definido aqui no load da página foram removidos e adicionados somente na ação do item. Os frames serão carregados somente quando necessário.
			- A altura do iframe foi removida pois estava causando 2 barras de rolagem na página de nova solicitação.
	 	*/
		$(window).load(function(){
			$('#contentFrameNovaSolicitacao').html('<iframe src="about:blank" id="frameNovaSolicitacao" width="100%" class="iframeSemBorda"></iframe>');
			$('#contentFrameAlterarSLA').html('<iframe id="frameAlterarSLA" src="about:blank" width="100%" height="520" class="iframeSemBorda"></iframe>');
			$('#contentReclassificarSolicitacao2').html('<iframe id="frameReclassificarSolicitacao2" src="about:blank" width="100%" height="600" class="iframeSemBorda"></iframe>');
			$('#contentFrameAgendarAtividade').html('<iframe id="frameAgendarAtividade" src="about:blank" width="100%" height="460" class="iframeSemBorda"></iframe>');
			$('#contentFrameagendaAtvPeriodicas').html('<iframe id="frameAgendaAtvPeriodicas" src="about:blank" width="100%" height="530" class="iframeSemBorda"></iframe>');
			$('#contentFrameSuspenderReativarSolicitacao').html('<iframe id="frameSuspenderReativarSolicitacao" src="about:blank" width="100%" height="520" class="iframeSemBorda"></iframe>');
			$('#contentFrameExibirDelegacaoTarefa').html('<iframe id="frameExibirDelegacaoTarefa" src="about:blank" width="100%" height="400" class="iframeSemBorda"></iframe>');
			$('#contentFrameSuspender').html('<iframe id="frameExibirSuspender" src="about:blank" width="100%" height="400" class="iframeSemBorda"></iframe>');
			$('#contentPesquisaGeralSolicitacao').html('<iframe id="framePesquisaGeralSolicitacao" src="about:blank" width="100%" height="600" class="iframeSemBorda"></iframe>');
			$('#contentframeReclassificarSolicitacao').html('<iframe id="frameReclassificarSolicitacao" src="about:blank" width="100%" height="700" class="iframeSemBorda"></iframe>');
		});
		</script>
	</body>
</html>