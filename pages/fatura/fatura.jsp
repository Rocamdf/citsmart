<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
<%@include file="/include/header.jsp"%>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/titleComum/titleComum.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<%-- <%@include file="/include/cssComuns/cssComuns.jsp" %>
	 --%>
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/themeroller/Aristo.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/css/text.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/css/grid.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/css/main.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/css/theme_base.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/css/buttons.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/css/ie.css">
<style type="text/css">
.linhaGrid {
	border: 1px solid black;
	background-color: white;
	vertical-align: middle;
}

body {
	background-color: white;
}

#GRID_ITENS_tblItens {
	margin-top: 3px;
	margin-left: 2px;
}

.linhaSubtituloGrid {
	padding: 5px;
}

#valorTotal {
	margin-left: 2px;
}

#paginaTotal {
	background-color: white;
	background-image: url("");
}
/* Hack */
.modal-body 
{
   max-height:80%;
}
.modal
{
   width:80% !important;
   margin-left: -40% !important; 
}
</style>
<script type="text/javascript"
	src="../../cit/objects/FaturaApuracaoANSDTO.js"></script>

<!-- Area de JavaScripts -->
<script type="text/javascript">
	
		var objTab = null;
		
		addEvent(window, "load", load, false);
		function load(){
			document.form.afterRestore = function () {
				document.getElementById('tabTela').tabber.tabShow(0);
			}
		}
		
	/* JQUERY  */
	
		$(function() {
			$('.datepicker').datepicker();
		});
		
	/* JQUERY */
		
		function LOOKUP_OS_select(id,desc){
			document.form.restore({idOS:id});
		}
		function gravarSituacao(){
			document.form.fireEvent('updateSituacao');
		}
				
		function gravarForm(){
			var count = GRID_ITENS.getMaxIndex();
			var existeErro = false;
			var contadorAux = 0;
			var objs = new Array();
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var idAcordoObj = document.getElementById('idAcordoNivelServicoContrato' + NumberUtil.zerosAEsquerda(i,5));
				var descricaoAcordoObj = document.getElementById('descricaoAcordo' + NumberUtil.zerosAEsquerda(i,5));
				var detalhamentoObj = document.getElementById('detalhamento' + NumberUtil.zerosAEsquerda(i,5));
				var valorApuradoObj = document.getElementById('valorApurado' + NumberUtil.zerosAEsquerda(i,5));
				var percentualGlosaObj = document.getElementById('percentualGlosa' + NumberUtil.zerosAEsquerda(i,5));
				var valorGlosaObj = document.getElementById('valorGlosa' + NumberUtil.zerosAEsquerda(i,5));
				trObj.bgColor = 'white';
	// 			descricaoAcordoObj.style.backgroundColor = 'white';
				valorApuradoObj.style.backgroundColor = 'white';
	// 			detalhamentoObj.style.backgroundColor = 'white';		
	// 			percentualGlosaObj.style.backgroundColor = 'white';
	// 			valorGlosaObj.style.backgroundColor = 'white';
	// 			if (detalhamentoObj.value == ''){
	// 				trObj.bgColor = 'orange';
	// 				descricaoAcordoObj.style.backgroundColor = 'orange';
	// 				valorApuradoObj.style.backgroundColor = 'orange';
	// 				detalhamentoObj.style.backgroundColor = 'orange';		
	// 				percentualGlosaObj.style.backgroundColor = 'orange';	
	// 				valorGlosaObj.style.backgroundColor = 'orange';
	// 				alert('Informe o detalhamento da apuração! Linha: ' + i);
	// 				return;
	// 			}
				if (valorApuradoObj.value == ''){
					trObj.bgColor = 'orange';
					descricaoAcordoObj.style.backgroundColor = 'orange';
					valorApuradoObj.style.backgroundColor = 'orange';
					detalhamentoObj.style.backgroundColor = 'orange';		
					percentualGlosaObj.style.backgroundColor = 'orange';
					valorGlosaObj.style.backgroundColor = 'orange';				
					alert('Informe o valor apurado! Linha: ' + i);
					return;
				}
				var objItem = new CIT_FaturaApuracaoANSDTO();
				objItem.idAcordoNivelServicoContrato = idAcordoObj.value;
				objItem.valorApurado = valorApuradoObj.value;
				objItem.detalhamento = detalhamentoObj.value;
				objItem.percentualGlosa = percentualGlosaObj.value;
				objItem.valorGlosa = valorGlosaObj.value;
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;
			}
			if (existeErro){
				return;
			}
			document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
			document.form.save();
		}	
		var seqSelecionada = '';
		function setaRestoreItem(desc, det, valorApur, percGlosa, valorGlosa, compl, detAcordo, idAcordoNivel){
			if (seqSelecionada != ''){
				eval('document.form.idAcordoNivelServicoContrato' + seqSelecionada + '.value = \'' + idAcordoNivel + '\'');
				eval('document.form.descricaoAcordo' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + desc + '\')');
				eval('document.form.detalhamento' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
				eval('document.form.valorApurado' + seqSelecionada + '.value = "' + valorApur + '"');
				eval('document.form.percentualGlosa' + seqSelecionada + '.value = "' + percGlosa + '"');
				eval('document.form.valorGlosa' + seqSelecionada + '.value = "' + valorGlosa + '"');
				eval('document.form.complemento' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + compl + '\')');
				eval('document.getElementById("inform' + seqSelecionada + '").innerHTML = ObjectUtils.decodificaEnter(\'' + detAcordo + '\')');
			}
		}	
		function setaRestoreDesc(det, compl, detAcordo, idAcordoNivel){
			if (seqSelecionada != ''){
				eval('document.form.idAcordoNivelServicoContrato' + seqSelecionada + '.value = \'' + idAcordoNivel + '\'');
				eval('document.form.descricaoAcordo' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
				eval('document.form.complemento' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + compl + '\')');
				eval('document.getElementById("inform' + seqSelecionada + '").innerHTML = ObjectUtils.decodificaEnter(\'' + detAcordo + '\')');
			}
		}
		function selecionaServicoContrato(){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent('restoreInfoServicoContrato');
		}
		function GRID_ITENS_onDeleteRowByImgRef(objImg){
			alert(i18n_message("citcorpore.comum.naoPermitidoExclusaoItens"));
			return false;
		}
		function mostrarOSParaFaturamento(){
			document.getElementById('divOsSelecao').innerHTML = 'Aguarde... carregando...';
			$('#POPUP_LISTA_OS_FATURAMENTO').modal('show');
			document.form.fireEvent('listOSParaFaturamento');
		}
		function chamaAssociarOS(){
			window.setTimeout('associarOS()', 1000);
		}
		function associarOS(){
			$('#POPUP_LISTA_OS_FATURAMENTO').modal('hide');
			document.getElementById('divOsSelecionadas').innerHTML = 'Aguarde... carregando...';
			document.formAssociar.idOSExcluir.value = '';
			document.formAssociar.idFatura.value = document.form.idFatura.value;
			document.formAssociar.fireEvent('associarOSParaFaturamento');
		}
		function retiraOSDaFatura(idOsParm){
			document.getElementById('divOsSelecionadas').innerHTML = 'Aguarde... carregando...';
			document.formAssociar.idOSExcluir.value = idOsParm;
			document.formAssociar.idFatura.value = document.form.idFatura.value;
			document.formAssociar.fireEvent('associarOSParaFaturamento');
		}
		function calculaFormulaANS(seq, objFieldName){
			JANELA_AGUARDE_MENU.show();
			document.form.seqANS.value = seq;
			document.form.fieldANS.value = objFieldName;
			eval('document.form.idANS.value = document.form.idAcordoNivelServicoContrato' + NumberUtil.zerosAEsquerda(seq,5) + '.value');
			document.form.fireEvent('calculaFormulaANS');
		}
		if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
		    document.documentElement.className += " " + "ie10";
		}
	</script>

</head>

<body>
	<!-- Definicoes Comuns -->
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
		title="Aguarde... Processando..."
		style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
	</cit:janelaAguarde>

	<div id="paginaTotal">
		<div id="areautil">
			<div id="formularioIndex">
				<div id=conteudo>
					<table width="100%">
						<tr>
							<td width="100%">
								<h2>
									<b><i18n:message key="contrato.fatura" /></b>
								</h2> <!-- ## AREA DA APLICACAO ## -->
								<form name='form'
									action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/fatura/fatura'>
									<input type='hidden' name='idFatura' /> <input type='hidden'
										name='idContrato' /> <input type='hidden' name='idANS' /> <input
										type='hidden' name='seqANS' /> <input type='hidden'
										name='fieldANS' /> <input type='hidden'
										name='colItens_Serialize' />
									<table id="tabFormulario" cellpadding="0" cellspacing="0">
										<tr>
											<td class="campoEsquerda"><i18n:message
													key="citcorpore.comum.descricao" />*:</td>
											<td><input type='text' name='descricaoFatura' size="100"
												maxlength="150"
												class="Valid[Required] Description[Descrição da fatura] text" />
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda"><i18n:message
													key="citcorpore.comum.datainicio" />*:</td>
											<td><input type='text' name='dataInicial' size="10"
												maxlength="10" style="width: 100px !important;"
												class="Format[Date] Valid[Required,Date] Description[Data Início] text datepicker" />
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda"><i18n:message
													key="citcorpore.comum.datafim" />*:</td>
											<td><input type='text' name='dataFinal' size="10"
												maxlength="10" style="width: 100px !important;"
												class="Format[Date] Valid[Required,Date] Description[Data Fim] text datepicker" />
											</td>
										</tr>
										<tr>
											<td class="campoEsquerda" style="vertical-align: middle;"><i18n:message
													key="visao.observacao" />:</td>
											<td><textarea name="observacao" cols='120' rows='5'
													maxlength="1024" style="border: 1px solid black;"
													class="text"></textarea></td>
										</tr>
										<tr>
											<td class="campoEsquerda"><i18n:message
													key="citcorpore.comum.situacao" />*:</td>
											<td><select name='situacaoFatura' id='situacaoFatura'
												class="Valid[Required] Description[Situação]"></select></td>
										</tr>
										<tr>
											<td></td>
											<td colspan="2">
												<button type='button' id="btnAddListaOSFaturamento"
													name='btnAddListaOSFaturamento' style="margin-top: 5px;"
													class="light img_icon has_text"
													onclick="mostrarOSParaFaturamento()">
													<img
														src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/document.png">
													<span><i18n:message
															key="citcorpore.ui.janela.popup.titulo.Adicionar_OS" /></span>
												</button>
										</tr>
										<tr>
											<td colspan="2">
												<div id='divOsSelecionadas'></div>
											</td>
										</tr>
										<tr>
											<td colspan="2"><cit:grid id="GRID_ITENS"
													columnHeaders='citcorpore.comum.cabecalhoGridItens'
													styleCells="linhaGrid">
													<cit:column idGrid="GRID_ITENS" number="001">
														<input type='hidden'
															name='idAcordoNivelServicoContrato#SEQ#' value='' />
														<input type='text' name="descricaoAcordo#SEQ#" size='40'
															maxlength="200" readonly="readonly" />
														<br>
														<input type='text' name="complemento#SEQ#" size='40'
															maxlength="200" readonly="readonly" />
														<div id='inform#SEQ#'></div>
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="002">
														<textarea name="detalhamento#SEQ#" id="detalhamento#SEQ#"
															maxlength="300" cols='35' rows='5'></textarea>
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="003">
														<input type='text' name='valorApurado#SEQ#'
															id='valorApurado#SEQ#' maxlength="15" size='12'
															maxlength='14' class='Format[Money]' />
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="004">
														<input type='text' name='percentualGlosa#SEQ#'
															id='percentualGlosa#SEQ#' maxlength="15" size='5'
															maxlength='5' class='Format[Money]'
															onblur='calculaFormulaANS(#SEQ#, "percentualGlosa")'
															value="0,00" />
													</cit:column>
													<cit:column idGrid="GRID_ITENS" number="005">
														<input type='text' name='valorGlosa#SEQ#'
															id='valorGlosa#SEQ#' maxlength="15" size='12'
															maxlength='14' class='Format[Money]'
															onblur='calculaFormulaANS(#SEQ#, "valorGlosa")'
															value="0,00" />
													</cit:column>
												</cit:grid></td>
										</tr>
										<tr>
											<td colspan="2">
												<div id="valorTotal"
													style='border: 1px solid black; background-color: white'>
													<table>
														<tr>
															<td colspan="5">&nbsp;</td>
														</tr>
														<tr>
															<td colspan="5"><span style='color: red'>&nbsp;<i18n:message
																		key="citcorpore.comum.atencaoValoresAtualizadosAposGravacao" /></span></td>
														</tr>
														<tr>
															<td class="campoEsquerdaSemTamanho"><b><i18n:message
																		key="citcorpore.comum.valorTotalFatura" />:</b></td>
															<td><input type='text' class="text"
																name='valorPrevistoSomaOS' size="15" maxlength="15"
																readonly="readonly" /></td>
															<td class="campoEsquerdaSemTamanho"><b><i18n:message
																		key="contrato.valorExecutado" />:</b></td>
															<td><input type='text' class="text"
																name='valorExecutadoSomaOS' size="15" maxlength="15"
																readonly="readonly" /></td>
															<td class="campoEsquerdaSemTamanho"><b><i18n:message
																		key="citcorpore.comum.valorTotalGlosasFatura" />: </b></td>
															<td><input type='text' class="text"
																name='valorSomaGlosasOS' size="15" maxlength="15"
																readonly="readonly" /></td>
															<td class="campoEsquerdaSemTamanho"><b><i18n:message
																		key="citcorpore.comum.valorReceber" />:</b></td>
															<td><input type='text' class="text"
																name='valorReceberOS' size="15" maxlength="15"
																readonly="readonly" /></td>
														</tr>
														<tr>
															<td colspan="5">&nbsp;</td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2">
												<div id='pareceres' style='display: none'>
													<table>
														<tr>
															<th><i18n:message
																	key="citcorpore.comum.parecerAprovacaoGestor" />:</th>
														</tr>
														<tr>
															<td><textarea name="aprovacaoGestor" cols='120'
																	rows='5' style="border: 1px solid black"></textarea></td>
														</tr>
														<tr>
															<th><i18n:message
																	key="citcorpore.comum.parecerAprovacaoFiscal" />:</th>
														</tr>
														<tr>
															<td><textarea name="aprovacaoFiscal" cols='120'
																	rows='5' style="border: 1px solid black"></textarea></td>
														</tr>
													</table>
												</div>
											</td>
										</tr>
										<tr>
											<td colspan="2" class="campoObrigatorio"><i18n:message
													key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" /></td>
										</tr>
										<tr>
											<td colspan='2'>
												<table>
													<tr>
														<td>
															<div id='divBotaoGravar' style='display: block'>
																<!-- <button type='button' name='btnGravar' onclick='gravarForm();'>Gravar</button> -->
																<button type='button' id="btnAdicionar"
																	name='btnAdicionar'
																	style="margin-top: 5px; margin-left: 3px"
																	class="light img_icon has_text" onclick="gravarForm()">
																	<img
																		src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																	<span><i18n:message
																			key="dinamicview.gravardados" /></span>
																</button>
															</div>
														</td>
														<td>
															<div id='divBotaoGravarSituacao'
																style='display: none; margin-left: 2px'>
																<!-- <button type='button' name='btnGravarSituacao' onclick='gravarSituacao();'>Atualizar Situação da Fatura</button> -->
																<button type='button' id="btnGravarSituacao"
																	name='btnGravarSituacao' style="margin-top: 5px;"
																	class="light img_icon has_text"
																	onclick="gravarSituacao()">
																	<img
																		src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																	<span><i18n:message
																			key="citcorpore.comum.atualizarSituacaoFatura" /></span>
																</button>
															</div>
														</td>
													</tr>
												</table>
											</td>
										</tr>
									</table>
								</form>
							</td>
						</tr>
					</table>
				</div>
			</div>
		</div>
	</div>

	<div class="modal hide fade in" id="POPUP_LISTA_OS_FATURAMENTO"
		aria-hidden="false" data-width='1000px'>
		<!-- Modal heading -->
		<div class="modal-header">
			<button type="button" class="close" data-dismiss="modal"
				aria-hidden="true">×</button>
			<h3><i18n:message key='citcorpore.comum.listagemOSFinalizadasNaoAssociadasFatura'/></h3>
		</div>
		<!-- // Modal heading END -->
		<!-- Modal body -->
		<div class="modal-body">
			<form name='formAssociar'
				action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/fatura/fatura'>
				<input type='hidden' name='idOSExcluir' /> <input type='hidden'
					name='idFatura' id='idFaturaAssociar' />
				<div id='divOsSelecao'></div>
				<table>
					<tr>
						<td><button type="button" name='btnAssociarOS' onclick='associarOS()'><i18n:message key='fatura.associar'/></button></td>
					</tr>
				</table>
			</form>

		</div>

		<!-- // Modal body END -->
		<!-- Modal footer -->
		<div class="modal-footer">
			<a href="#" class="btn " data-dismiss="modal"><i18n:message
					key="citcorpore.comum.fechar" /></a>
		</div>
		<!-- // Modal footer END -->
	</div>
	<script type="text/javascript">
		document.form.onClear = function(){
			GRID_ITENS.deleteAllRows();
		};
	</script>
<%@include file="/include/footer.jsp"%>
</body>
</html>
