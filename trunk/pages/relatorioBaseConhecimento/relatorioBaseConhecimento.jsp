<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO"%>
<!doctype html public "">
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>

<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script><!--
	var temporizador;
	addEvent(window, "load", load, false);
	function load() {
		$("#POPUP_USUARIO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	}
	
	function LOOKUP_USUARIO_select(id, desc) {
		document.form.idUsuarioAcesso.value = id;
		document.form.nomeUsuarioAcesso.value = desc;
		$("#POPUP_USUARIO").dialog("close");		
	}
	
	function imprimirRelatorioBaseConhecimento(){
		
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var dataInicioPublicacao = document.getElementById("dataInicioPublicacao").value;
		var dataFimPublicacao = document.getElementById("dataFimPublicacao").value;
		var dataInicioExpiracao = document.getElementById("dataInicioExpiracao").value;
		var dataFimExpiracao = document.getElementById("dataFimExpiracao").value;
		var dataInicioAcesso = document.getElementById("dataInicioAcesso").value;
		var dataFimAcesso = document.getElementById("dataFimAcesso").value;
		
		if(dataInicio != "" || dataFim != "" ){
			if(DateTimeUtil.isValidDate(dataInicio) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioCriacao"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFim) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimCriacao"));
			 	document.getElementById("dataFim").value = '';
				return false;	
			}
		}
		
		if(dataInicioPublicacao != "" || dataFimPublicacao != "" ){
			if(DateTimeUtil.isValidDate(dataInicioPublicacao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioPublicacao"));
			 	document.getElementById("dataInicioPublicacao").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFimPublicacao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimPublicacao"));
			 	document.getElementById("dataFimPublicacao").value = '';
				return false;	
			}
		}
		
		if(dataInicioExpiracao != "" || dataFimExpiracao != "" ){
			if(DateTimeUtil.isValidDate(dataInicioExpiracao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioExpiracao"));
			 	document.getElementById("dataInicioExpiracao").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFimExpiracao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimExpiracao"));
			 	document.getElementById("dataFimExpiracao").value = '';
				return false;	
			}
		}

		if(dataInicioAcesso != "" || dataFimAcesso != "" ){
			if(DateTimeUtil.isValidDate(dataInicioAcesso) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioAcesso"));
			 	document.getElementById("dataInicioAcesso").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFimAcesso) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimAcesso"));
			 	document.getElementById("dataFimAcesso").value = '';
				return false;	
			}
		}
		
		if(dataInicioPublicacao != "" || dataFimPublicacao != ""){
			if(validaData(dataInicioPublicacao,dataFimPublicacao)){
				if(validaData(dataInicio,dataFim)){
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorioBaseConhecimento");
				}
			}
		}else{
			if(validaData(dataInicio,dataFim)){
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("imprimirRelatorioBaseConhecimento");
			}
		}
		
	}
	
	function imprimirRelatorioBaseConhecimentoXls(){	
		
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		var dataInicioPublicacao = document.getElementById("dataInicioPublicacao").value;
		var dataFimPublicacao = document.getElementById("dataFimPublicacao").value;
		var dataInicioExpiracao = document.getElementById("dataInicioExpiracao").value;
		var dataFimExpiracao = document.getElementById("dataFimExpiracao").value;
		var dataInicioAcesso = document.getElementById("dataInicioAcesso").value;
		var dataFimAcesso = document.getElementById("dataFimAcesso").value;
		
		if(dataInicio != "" || dataFim != "" ){
			if(DateTimeUtil.isValidDate(dataInicio) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioCriacao"));
			 	document.getElementById("dataInicio").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFim) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimCriacao"));
			 	document.getElementById("dataFim").value = '';
				return false;	
			}
		}
		
		if(dataInicioPublicacao != "" || dataFimPublicacao != "" ){
			if(DateTimeUtil.isValidDate(dataInicioPublicacao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioPublicacao"));
			 	document.getElementById("dataInicioPublicacao").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFimPublicacao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimPublicacao"));
			 	document.getElementById("dataFimPublicacao").value = '';
				return false;	
			}
		}
		
		if(dataInicioExpiracao != "" || dataFimExpiracao != "" ){
			if(DateTimeUtil.isValidDate(dataInicioExpiracao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioExpiracao"));
			 	document.getElementById("dataInicioExpiracao").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFimExpiracao) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimExpiracao"));
			 	document.getElementById("dataFimExpiracao").value = '';
				return false;	
			}
		}
		
		if(dataInicioAcesso != "" || dataFimAcesso != "" ){
			if(DateTimeUtil.isValidDate(dataInicioAcesso) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataInicioAcesso"));
			 	document.getElementById("dataInicioAcesso").value = '';
				return false;	
			}
			
			if(DateTimeUtil.isValidDate(dataFimAcesso) == false ){
				alert(i18n_message("relatorioBaseConhecimento.informeDataFimAcesso"));
			 	document.getElementById("dataFimAcesso").value = '';
				return false;	
			}
		}
		
		if(dataInicioPublicacao != "" || dataFimPublicacao != ""){
			if(validaData(dataInicioPublicacao,dataFimPublicacao)){
				if(validaData(dataInicio,dataFim)){
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("imprimirRelatorioBaseConhecimentoXls");
				}
			}
		}else{
			if(validaData(dataInicio,dataFim)){
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("imprimirRelatorioBaseConhecimentoXls");
			}
		}
		
	}
	
	function limpar(){
		document.getElementById('idBaseConhecimento').options.length = 0;
		document.form.clear();
	}
	
	function mudaDivPeriodo(tipoPeriodo){
		if (tipoPeriodo != null && tipoPeriodo != '') {
			// LIMPA CAMPOS DE PERÍODO
			document.getElementById('dataInicio').value = '';
			document.getElementById('dataFim').value = '';
			document.getElementById('dataInicioPublicacao').value = '';
			document.getElementById('dataFimPublicacao').value = '';
			document.getElementById('dataInicioExpiracao').value = '';
			document.getElementById('dataFimExpiracao').value = '';
			document.getElementById('dataInicioAcesso').value = '';
			document.getElementById('dataFimAcesso').value = '';
			
			document.getElementById('divPeriodoCriacao').style.display = 'none';
			document.getElementById('divPeriodoPublicacao').style.display = 'none';
			document.getElementById('divPeriodoExpiracao').style.display = 'none';
			document.getElementById('divPeriodoAcesso').style.display = 'none';
			
			// MOSTRA O PERIODO ESCOLHIDO
			if (tipoPeriodo == 'criacao') {
				document.getElementById('divPeriodoCriacao').style.display = '';
			} else if (tipoPeriodo == 'publicacao') {
				document.getElementById('divPeriodoPublicacao').style.display = '';
			} else if (tipoPeriodo == 'expiracao') {
				document.getElementById('divPeriodoExpiracao').style.display = '';
			} else if (tipoPeriodo == 'acesso') {
				document.getElementById('divPeriodoAcesso').style.display = '';
			}
		}
	}
	
	  $(function() {
			$("#addUsuario").click(function() {
				$("#POPUP_USUARIO").dialog("open");
			});
		});  
	  
	  /**
		* @author rodrigo.oliveira
		*/
		function validaData(dataInicio, dataFim) {
			
			var dtInicio = new Date();
			var dtFim = new Date();
			
			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
			
			if (dtInicio > dtFim){
				alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
				return false;
			}else
				return true;
		}
--></script>
<style type="text/css">
	div.main_container .box {
			margin-top: -0.3em!important;

		}
</style>
</head>
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>

	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="relatorioBaseConhecimento.relatorioBaseConhecimento"/></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block" >
						<div class="section" >
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/relatorioBaseConhecimento/relatorioBaseConhecimento'>
								<input type="hidden" id="idUsuarioAcesso" name="idUsuarioAcesso">
								<div class="columns clearfix">
								<div class="col_100">
									<div class="col_40">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.periodo"/></label>
											<div>
												<table>
													<tr>
														<td>
															<select onchange="mudaDivPeriodo(this.value);">
																<option value="criacao" selected="selected"><i18n:message key="relatorioBaseConhecimento.periodoCriacaoBaseConhecimento"/></option>
																<option value="publicacao"><i18n:message key="relatorioBaseConhecimento.periodoPublicacaoBaseConhecimento"/></option>
																<option value="expiracao"><i18n:message key="relatorioBaseConhecimento.periodoExpiracaoBaseConhecimento"/></option>
																<option value="acesso"><i18n:message key="relatorioBaseConhecimento.periodoAcessoBaseConhecimento"/></option>
															</select>
														</td>
														<td style="padding-left: 5px;">
															<div id='divPeriodoCriacao'>
																<table>
																	<tr>
																		<td>
																			<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																		<td>
																			<i18n:message key="citcorpore.comum.a" />
																		</td>
																		<td>
																			<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																	</tr>
																</table>												
															</div>
															<div id='divPeriodoPublicacao' style="display: none;">
																<table>
																	<tr>
																		<td>
																			<input type='text' name='dataInicioPublicacao' id='dataInicioPublicacao' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																		<td>
																			<i18n:message key="citcorpore.comum.a" />
																		</td>
																		<td>
																			<input type='text' name='dataFimPublicacao' id='dataFimPublicacao' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																	</tr>
																</table>
															</div>
															<div id='divPeriodoExpiracao' style="display: none;">
																<table>
																	<tr>
																		<td>
																			<input type='text' name='dataInicioExpiracao' id='dataInicioExpiracao' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																		<td>
																			<i18n:message key="citcorpore.comum.a" />
																		</td>
																		<td>
																			<input type='text' name='dataFimExpiracao' id='dataFimExpiracao' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																	</tr>
																</table>
															</div>
															<div id='divPeriodoAcesso' style="display: none;">
																<table>
																	<tr>
																		<td>
																			<input type='text' name='dataInicioAcesso' id='dataInicioAcesso' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																		<td>
																			<i18n:message key="citcorpore.comum.a" />
																		</td>
																		<td>
																			<input type='text' name='dataFimAcesso' id='dataFimAcesso' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset >
											<label><i18n:message key="pasta.pasta"/></label>
											<div>
												<select onchange="document.form.fireEvent('preencherComboBaseConhecimentoPorPasta')" name="idPasta"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_30">
										<fieldset >
												<label><i18n:message key="baseConhecimento.baseConhecimento"/></label>
											<div>
												<select name="idBaseConhecimento"></select>
											</div>
										</fieldset>
									</div>
								</div>
									<div class="col_25">
										<fieldset >
												<label><i18n:message key="citcorpore.comum.situacao"/></label>
											<div>
												<select name="status"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_25" >
										<fieldset >
											<label><i18n:message key="relatorioBaseConhecimento.mediaAvaliacao"/></label>
											<div>
											<select id="termoPesquisaNota" name="termoPesquisaNota">
												<option value=""><i18n:message key="citcorpore.comum.todos"/></option>
												<option value="0.0">0.0</option>
												<option value="1.0">1.0</option>
												<option value="2.0">2.0</option>
												<option value="3.0">3.0</option>
												<option value="4.0">4.0</option>
												<option value="5.0">5.0</option>
												<option value="S"><i18n:message key="citcorpore.comum.semAvaliacao"/></option>
											</select>
											</div>
											</fieldset>					
									</div>
									<div class="col_25" >
										<fieldset>
											<label><i18n:message key="citcorpore.comum.ordenacao"/>:</label>
											<div>
												<select id="acessado" name="acessado">
													<option value="A"><i18n:message key="relatorioBaseConhecimento.ordenarPorQuantidadeAcessos"/></option>
													<option value="M"><i18n:message key="relatorioBaseConhecimento.ordenarPorMediaAvaliacao"/></option>
													<option value="V"><i18n:message key="relatorioBaseConhecimento.ordenarPorVersao"/></option>
												</select>
											</div>
										</fieldset>					
									</div >	
									<div class="col_25">
										<fieldset >
											<label ><i18n:message key="relatorioBaseConhecimento.exibirUltimoAcesso"/></label>
											<div>
												<select name="ultimoAcesso" id="ultimoAcesso" >
												<option value="N"><i18n:message key="citcorpore.comum.nao"/></option>
												<option value="S"><i18n:message key="citcorpore.comum.sim"/></option>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25">
										<fieldset >
											<label ><i18n:message key="relatorioBaseConhecimento.ultimasVersoes"/></label>
											<div>
												<select name="ultimaVersao" id="ultimaVersao" >
												<option value="N"><i18n:message key="citcorpore.comum.nao"/></option>
												<option value="S"><i18n:message key="citcorpore.comum.sim"/></option>
												</select>
											</div>
										</fieldset>
									</div>
									<div class="col_25 ">
										<fieldset style="height: 52px">
											<label style="display: block; float: left;padding-top: 15px;"><input  type="checkbox" checked="checked" name="ocultarConteudo" id="ocultarConteudo" value="S" /><i18n:message key="relatorioBaseConhecimento.ocultarConteudo"/></label>
										</fieldset>
									</div>
								</div>
									<div class="col_100">
									<fieldset>
										<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioBaseConhecimento()"
											style="margin: 20px !important;">
											<img
								
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span ><i18n:message key="citcorpore.comum.gerarrelatorio"/></span>
										</button>
										<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioBaseConhecimentoXls()"
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png">
											<span ><i18n:message key="citcorpore.comum.gerarrelatorio"/></span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limpar()' style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar"/></span>
										</button>
									</fieldset>
								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
	<%-- <div id="POPUP_USUARIO" title="<i18n:message key="citcorpore.comum.pesquisaresponsavel" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaResponsavel' style="width: 540px">
							<cit:findField formName='formPesquisaResponsavel' 
								lockupName='LOOKUP_USUARIO_BASECONHECIMENTO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div> --%>
</html>