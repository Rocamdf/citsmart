<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String permiteValorZeroAtv = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.OS_VALOR_ZERO, "N");
	if (permiteValorZeroAtv == null){
		permiteValorZeroAtv = "N";
	}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<style type="text/css">
		body {
			border: black solid 1px ;	
			background-color: white;
			vertical-align: middle;
		
		}
		
		#paginaTotal {
			background-color: white;
			background-image: url("");
		}
		
		.linhaGrid{
			text-align: center;
			border: 1px solid black;
			background-color: white;	
			vertical-align: middle;
		}
		
		#GRID_ITENS_tblItens {
			border: black solid 1px ; 
			margin-top: 3px;
			margin-left: 2px;
		}
	</style>
	 <%@include file="/include/header.jsp"%> 
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
	
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/themeroller/Aristo.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/text.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/grid.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/main.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/theme_base.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/buttons.css">
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/ie.css">
<!--  Desenvolvedor: Pedro Lino - Data: 30/10/2013 - Horário: 09:40 - ID Citsmart: 120948 - 
* Motivo/Comentário: Inserido atualiza antigo para layout entrar na cor padrão 	 -->
	<link href="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />
	
	<script type="text/javascript" src="../../cit/objects/DemandaDTO.js"></script> 

	<script type="text/javascript">
		var objTab = null;
		
		addEvent(window, "load", load, false);
		function load(){
			document.form.afterRestore = function () {
				document.getElementById('tabTela').tabber.tabShow(0);
			}
		}
		
		function LOOKUP_OS_select(id,desc){
			document.form.restore({idOS:id});
		}
		
		function gravarForm(){
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			if(!validaData(dataInicio, dataFim)){
				return;
			}
			var count = GRID_ITENS.getMaxIndex();
			var existeErro = false;
			var contadorAux = 0;
			var objs = new Array();
			for (var i = 1; i <= count; i++){
				var trObj = document.getElementById('GRID_ITENS_TD_' + NumberUtil.zerosAEsquerda(i,5));
				if (!trObj){
					continue;
				}
				var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
				var complexidadeObj = document.getElementById('complexidade' + NumberUtil.zerosAEsquerda(i,5));
				var demandaObj = document.getElementById('demanda' + NumberUtil.zerosAEsquerda(i,5));
				var objObj = document.getElementById('obs' + NumberUtil.zerosAEsquerda(i,5));
				var formulaObj = document.getElementById('formula' + NumberUtil.zerosAEsquerda(i,5));
				var idAtividadeServicoContrato = document.getElementById('idAtividadeServicoContrato' + NumberUtil.zerosAEsquerda(i,5));
				var contabilizarObj = document.getElementById('contabilizar' + NumberUtil.zerosAEsquerda(i,5));
				var idServicoContratoContabilObj = document.getElementById('idServicoContratoContabil' + NumberUtil.zerosAEsquerda(i,5));
				trObj.bgColor = 'white';
				complexidadeObj.style.backgroundColor = 'white';
				quantidadeObj.style.backgroundColor = 'white';
				demandaObj.style.backgroundColor = 'white';		
				objObj.style.backgroundColor = 'white';		
				if (complexidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe a complexidade! Linha: ' + i);
					return;
				}
				if (demandaObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe a demanda! Linha: ' + i);
					return;
				}
				if (quantidadeObj.value == ''){
					trObj.bgColor = 'orange';
					complexidadeObj.style.backgroundColor = 'orange';
					quantidadeObj.style.backgroundColor = 'orange';
					demandaObj.style.backgroundColor = 'orange';		
					objObj.style.backgroundColor = 'orange';				
					alert('Informe o custo! Linha: ' + i);
					return;
				}
				var objItem = new CIT_DemandaDTO();
				objItem.complexidade = complexidadeObj.value;
				objItem.custoAtividade = quantidadeObj.value;
				objItem.descricaoAtividade = demandaObj.value;
				objItem.obsAtividade = objObj.value;
				objItem.formula = formulaObj.value;
				objItem.idAtividadeServicoContrato = idAtividadeServicoContrato.value;
				objItem.contabilizar = contabilizarObj.value;
				objItem.idServicoContratoContabil = idServicoContratoContabilObj.value;
				objs[contadorAux] = objItem;
				contadorAux = contadorAux + 1;
				
				<%if (!permiteValorZeroAtv.equalsIgnoreCase("S")){%>
					if (quantidadeObj.value == '' || quantidadeObj.value == '0,00' || quantidadeObj.value == '0'){
						trObj.bgColor = 'orange';
						complexidadeObj.style.backgroundColor = 'orange';
						quantidadeObj.style.backgroundColor = 'orange';
						demandaObj.style.backgroundColor = 'orange';		
						objObj.style.backgroundColor = 'orange';
						alert('Falta definir custo da atividade ! Linha: ' + i);
						existeErro = true;
					}
				<%}%>
			}
			if(existeErro){
				return;
			}
			document.form.colItens_Serialize.value = ObjectUtils.serializeObjects(objs);
			document.form.save();
		}
		var seqSelecionada = '';
		function setaRestoreItem(complex, det, obs, custo,  formula, idAtividadeServicoContrato, contabilizar, idServicoContratoContabil){
			if (seqSelecionada != ''){
				eval('HTMLUtils.setValue(\'complexidade' + seqSelecionada + '\', \'' + complex + '\')');
				eval('document.form.demanda' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + det + '\')');
				eval('document.form.obs' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + obs + '\')');
				eval('document.form.formula' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + formula + '\')'); 
				eval('document.form.quantidade' + seqSelecionada + '.value = "' + custo + '"');
				eval('document.form.idAtividadeServicoContrato' + seqSelecionada + '.value = "' + idAtividadeServicoContrato + '"');
				eval('document.form.contabilizar' + seqSelecionada + '.value = "' + contabilizar + '"');
				eval('document.form.idServicoContratoContabil' + seqSelecionada + '.value = "' + idServicoContratoContabil + '"');
				document.getElementById("divDemanda"+seqSelecionada).innerHTML = ObjectUtils.decodificaEnter(det) + "<div style='font-weight:bold; padding-top: 6px;'>"+formula+"</div>";
			}
		}
		
		function confirmaCancelamento(){
			if(confirm(i18n_message("os.cancelarOS"))){
				document.form.fireEvent('cancelaOSeRAs');
			}else{
				return false;
			}
		}
		
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
		
		function selecionaServicoContrato(){
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			
			if(dataInicio == "" || dataFim == ""){
				alert(i18n_message("os.InformePeriodoSelecionarServico"));
				document.getElementById("idServicoContrato").value = "";
				return;
			}
			
			if (dataInicio != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataInicio.value)){
					alert(i18n_message("citcorpore.comum.datainvalida"));
					document.form.dataInicio.focus();
					return;
				}
			}
			if (dataFim != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataFim.value)){
					alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
					document.form.dataFim.focus();
					return;
				}
			}
						
		}
		
		function restoreInfoServios(){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent('restoreInfoServicoContrato');
		}
		
		function atualizaInfoServios(){
			if (!document.form.idServicoContrato.disabled){
				if (confirm('Atenção! Esta ação pode atualizar as atividades da OS, conforme registradas no serviço. Confirma ?')){
					restoreInfoServios();
					alert('Atenção! É necessário rever os RAs. Se houver RAs gerados para esta OS, verifique cada um!');
				}
			}else{
				alert('Não é possível atualizar a lista de tarefas. A OS está bloqueada para esta ação!');
			}
		}		
			
		function GRID_ITENS_onDeleteRowByImgRef(objImg){
			if(confirm(i18n_message('dinamicview.desejaexcluirlinha'))){
				var indice = objImg.parentNode.parentNode.rowIndex;
				HTMLUtils.deleteRow("GRID_ITENS_tblItens", indice);
				recalculacusto();
				preencheNumeracaoItens();
				return false;
			}else{
				return false;
			}
		}
		
		function calculaCusto(){
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			
			if (dataInicio != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataInicio.value)){
					alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
					alert(i18n_message("citcorpore.comum.datainvalida"));
					document.form.dataInicio.focus();
					return;
				}
			}
			if (dataFim != ""){
				if(!DateTimeUtil.isValidDate(document.form.dataFim.value)){
					alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
					alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
					document.form.dataFim.focus();
					return;
				}
			}
			document.form.fireEvent('restoreInfoServicoContrato');
		}
		
		/**
		* Recalcula custo das atividades ao excluir
		* @author rodrigo.oliveira
		*/
		function recalculacusto(){
			var count = GRID_ITENS.getMaxIndex();
			var totalquantidade = 0;
			flag = false;
			for (var i = 1; i <= count; i++){
				var quantidadeObj = document.getElementById('quantidade' + NumberUtil.zerosAEsquerda(i,5));
				if(!quantidadeObj){
					continue;
				}else{
					if(quantidadeObj.value != ""){
						numtemp = quantidadeObj.value.replace(/,/g, ".");
						totalquantidade = totalquantidade + parseFloat(numtemp);
					}
				}
			}
			document.getElementById("custoTotal").innerHTML = "<b>" +moeda(totalquantidade)+ "</b>";
		}
		
		/**
		* Preenche numeração
		* @author rodrigo.oliveira
		*/
		function preencheNumeracaoItens(){
			var count = GRID_ITENS.getMaxIndex();
			flag = false;
			for (var i = 1; i <= count; i++){
				if(!flag){
					var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i,5));
					if(!item){
						flag = true;
						var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
						if(!item){
							continue;
						}
					}
					item.innerHTML = i < 10 ? NumberUtil.zerosAEsquerda(i,2) : i;
				}else{
					var item = document.getElementById('item' + NumberUtil.zerosAEsquerda(i+1,5));
					if(!item){
						continue;
					}
					item.innerHTML = i < 10 ? NumberUtil.zerosAEsquerda(i,2) : i;
				}
			}
		}
		
		
		/**
		* Converte número em valor monetário
		* @author rodrigo.oliveira
		*/
		function moeda(num){
			  x = 0;
			  
			  if(num < 0) { num = Math.abs(num);   x = 1; }
			  
			  if(isNaN(num)) num = "0";
			  cents = Math.floor((num*100+0.5)%100);
			    num = Math.floor((num*100+0.5)/100).toString();
			 
			  if(cents < 10) cents = "0" + cents;
			    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			  num = num.substring(0,num.length-(4*i+3))+'.'+num.substring(num.length-(4*i+3));
			  
			  ret = num + ',' + cents; 
			  if (x == 1) 
			  ret = '-' + ret;
			  
			  return ret;
		}

		$(function() {
			$('.dtpicker').datepicker();
		});
		
	</script>
</head>
<body>
	<!-- Definicoes Comuns -->
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="<i18n:message key='citcorpore.comum.aguardecarregando' />" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;"></cit:janelaAguarde>
	
	<div id="paginaTotal">
		<div id="areautil">
			<div id="formularioIndex">
	       		<div id="conteudo" style="margin-left:  10px; margin-bottom: 10px;" >
					<table width="100%">
						<tr>
							<td width="100%">
									<h2><b><i18n:message key="requisitosla.ordem_servico" /></b></h2>
									<!-- ## AREA DA APLICACAO ## -->
									 	<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/os/os'>
									 		<input type='hidden' name='idOS'/>
									 		<input type='hidden' name='idOSPai'/>
									 		<input type='hidden' name='idContrato'/>
									 		<input type='hidden' name='quantidadeGlosasAnterior'/>
									 		<input type='hidden' name='colItens_Serialize'/>
										  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
										  		<tr>
										            <td class="campoEsquerda"><i18n:message key="pesquisa.datainicio" />*:</td>
										            <td>
										            	<input type='text' id="dataInicio" name='dataInicio' size="10" maxlength="10" onchange="calculaCusto()" style="width: 100px !important;" class="Valid[Required,Date] Description[Data Início] Format[Date] dtpicker"/>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda"><i18n:message key="pesquisa.datafim" />*:</td>
										            <td>
										            	<input type='text' id="dataFim" name='dataFim' size="10" maxlength="10" onchange="calculaCusto()" style="width: 100px !important;" class="Valid[Required,Date] Description[Data Fim] Format[Date] dtpicker"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><i18n:message key="problema.servico" />*:</td>
										            <td>
										            	<select id="idServicoContrato" name='idServicoContrato' class="Valid[Required] Description[Serviço]" onclick="selecionaServicoContrato()" onchange="restoreInfoServios()"></select>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style="visibility:hidden; display: none;"><i18n:message key="citcorpore.comum.ano" />*:</td>
										            <td>
										            	<input type='text' style="visibility:hidden; display: none;" name='ano' size="4" maxlength="4" style="width: 80px !important;" class="Format[Numero] Valid[Required] Description[Ano] text"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><i18n:message key="citcorpore.comum.numero" />*:</td>
										            <td>
										            	<input type='text' name='numero' size="20" maxlength="20" style="width: 250px !important;" class="Valid[Required] Description[Número] text"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda"><i18n:message key="citcorpore.comum.areaRequisitante" />*:</td>
										            <td>
										            	<input type='text' id='nomeAreaRequisitante' name='nomeAreaRequisitante' size="80" maxlength="80" style="width: 500px !important;" class="Valid[Required] Description[Área requisitante] text"/>
										            </td>
										         </tr>											         
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'><i18n:message key="citcorpore.ui.tabela.coluna.Tarefa_Demanda" />:</td>
										            <td>
										            	<textarea name="demanda" cols='120' rows='5' style="border: 1px solid black;"></textarea>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda" style='vertical-align: middle;'><i18n:message key="planoMelhoria.objetivo" />:</td>
										            <td>
										            	<textarea name="objetivo" cols='120' rows='5' style="border: 1px solid black;"></textarea>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda"><i18n:message key="visao.situacao" />*:</td>
										            <td>
										            	<select name='situacaoOS' id='situacaoOS' class="Valid[Required] Description[visao.situacao]"></select>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda">&nbsp;</td>
										            <td>
										            	<input type='button' name='btnAtuLista' id='btnAtuLista' value='<i18n:message key="citcorpore.comum.atualizalistaatv" />' onclick="atualizaInfoServios();" />
										            </td>
										         </tr>										         	
										         <tr>
										         	<td colspan="2" style='text-align: right; display:none'>
										         		<input type='button' name='btnAddInteg' id='btnAddInteg' value='Adicionar Item' onclick="GRID_ITENS.addRow();" />
										         	</td>
										         </tr>											         		
										         <tr>
										         	<td colspan="2">
											         		<cit:grid id="GRID_ITENS" columnHeaders="citcorpore.comum.cabecalhoItens" styleCells="linhaGrid">
											         			<cit:column idGrid="GRID_ITENS" number="001">
											         				<span id='item#SEQ#' style="border: none; backtext-align: center; font-weight: bold; background-color: white;"></span>
											         			</cit:column>
											         			<cit:column idGrid="GRID_ITENS" number="002">
											         			<input type="hidden" id='idAtividadeServicoContrato#SEQ#' name='idAtividadeServicoContrato#SEQ#' size='12' maxlength='14' />
											         				<select id='complexidade#SEQ#' name='complexidade#SEQ#'>
											         					<option value='B'><i18n:message key="citcorpore.comum.complexidadeBaixa" /></option>
											         					<option value='I'><i18n:message key="citcorpore.comum.complexidadeIntermediaria" /></option>
											         					<option value='M'><i18n:message key="citcorpore.comum.complexidadeMediana" /></option>
											         					<option value='A'><i18n:message key="citcorpore.comum.complexidadeAlta" /></option>
											         					<option value='E'><i18n:message key="citcorpore.comum.complexidadeEspecialista" /></option>
											         				</select>
											         			</cit:column>
											         			<cit:column idGrid="GRID_ITENS" number="003" >
											         				<div style="width: 100%; height: 234px;">
											         					<div style="border-bottom:1px solid black;">
												         					<div id="divDemanda#SEQ#" style="width: 705px; height: 107px;" ></div>
												         					<input type="hidden" name="demanda#SEQ#" />
												         					<input type="hidden" name="formula#SEQ#" />
												         				</div>
												         				<input type="hidden" name="contabilizar#SEQ#" />
												         				<input type="hidden" name="idServicoContratoContabil#SEQ#" />
											         					<div style="width: 10px; border: 5px; padding: 5px; font-weight: bold;">
											         						 <i18n:message key="citcorpore.comum.observacoes" />: 
											         					</div>
											         						<textarea style="width: 705px; height: 100px ;border: none;" name="obs#SEQ#" cols='45' rows='5'></textarea> 
											         					</div>
											         			</cit:column>
											         			<cit:column idGrid="GRID_ITENS" number="004">
											         				<input type='text' name='quantidade#SEQ#' style="border: none; text-align: center; font-weight: bold;" size='12' maxlength='14' class='Format[Moeda]'/>
											         			</cit:column>
											         		</cit:grid>
										         	</td>
										         </tr>	
										     <tr>
										     	<td colspan="2" style='text-align: right; border:2px solid black; '>
										     		<table width="99%" >
										     			<tr>
										     				<th style='text-align: right; width: 90%;'>
										     					<i18n:message key="requisitosla.custo_total" />:
										     				</th>
										     				<td style='text-align: right;'>
										     					<span id='custoTotal'><b>0,00</b></span>
										     				</td>
										     			</tr>
										     		</table> 
										     	</td>
										     </tr>										         									         	         	         	                                    		                                            
											 <tr>
									            <td colspan="2" class="campoObrigatorio"><i18n:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio" /></td>
									         </tr>
									         <tr>
									         	<td colspan='2'>
									         		<!-- <button type='button' name='btnGravar' onclick='gravarForm();'>Gravar</button> -->
									         		<button type='button' id="btnGravar" name='btnGravar' style="margin-top: 5px; margin-left: 3px" class="light img_icon has_text" onclick="gravarForm()">
															<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
														<span><i18n:message key="botaoacaovisao.gravar_dados" /></span>
													</button>
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
	<script type="text/javascript">
		document.form.onClear = function(){
			GRID_ITENS.deleteAllRows();
		};
	</script>
</body>
</html>
							