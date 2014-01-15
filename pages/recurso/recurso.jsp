<%@page import="br.com.centralit.citcorpore.bean.RecursoDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%
    response.setCharacterEncoding("ISO-8859-1");
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<html>

<head>
<%@include file="/include/security/security.jsp"%>
<!--<![endif]-->
<title>CITSmart</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript"
	src="../../cit/objects/FaixaValoresRecursoDTO.js"></script>
	<!-- Area de JavaScripts -->
	<script type="text/javascript">
	var controleFaixas_Inclusao = true;
	var controleFaixas_Index = 0;
	
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}	

	LOOKUP_RECURSO_select = function(id,desc){
		JANELA_AGUARDE_MENU.show();
		document.form.restore({idRecurso:id});
	};	
	
	atualizaListagem = function(){
		document.form.fireEvent('atualizaListagem');
	};
	
	geraRadio = function(row, obj){
		row.cells[0].innerHTML = '<input type="radio" name="idObjetivoEstrategico" value="' + obj.idPlanoEstrategico + '" onclick="restoreObj(\'' + obj.idObjetivoEstrategico + '\')"/>';
	};
	
	setaCor = function(cor){
		try{
			document.getElementById('divCor').style.backgroundColor = cor;
		}catch(e){
		}
		
		document.formFaixaControle.cor.value = cor;
		POPUP_CORES.hide();
	};
	
	showAdicionarFaixaControle = function(){
		controleFaixas_Inclusao = true;
		controleFaixas_Index = 0;
	
		document.getElementById('divCor').style.backgroundColor = 'white';
		document.formFaixaControle.clear();
		POPUP_FAIXA_CONTROLE.show();
	};
	
	validaFaixaControle = function(){
		if (document.formFaixaControle.validate()){
			var obj = new CIT_FaixaValoresRecursoDTO();
			
			obj.corInner = '<div style="width:100px; height:18px; background-color: ' + document.formFaixaControle.cor.value + '">&nbsp;</div>';
			
			if (controleFaixas_Inclusao){
				HTMLUtils.addRow('tblFaixaControle', document.formFaixaControle, '', obj, ['valorInicio', 'valorFim', 'corInner', ''], null, null, [geraBtnExcluir], null, null, false);
			}else{
				HTMLUtils.updateRow('tblFaixaControle', document.formFaixaControle, '', obj, ['valorInicio', 'valorFim', 'corInner', ''], null, null, [geraBtnExcluir], null, null, controleFaixas_Index, false);
			}
			
			POPUP_FAIXA_CONTROLE.hide();
		}
	};
	
	geraBtnExcluir = function(row, obj){
		row.cells[0].onclick = function(){funcaoClick(row, obj)};
		row.cells[1].onclick = function(){funcaoClick(row, obj)};
		row.cells[2].onclick = function(){funcaoClick(row, obj)};
	
		row.cells[3].innerHTML = '<input type="button" name="btnExcluirFaixa" value="Excluir" onclick="excluirFaixa(' + row.rowIndex + ')"/>';
	};	
	
	funcaoClick = function(row, obj){
		controleFaixas_Inclusao = false;
		
		controleFaixas_Index = row.rowIndex;
		
		HTMLUtils.setValuesForm(document.formFaixaControle, obj);
		
		document.getElementById('divCor').style.backgroundColor = obj.cor;
		POPUP_FAIXA_CONTROLE.show();
	};
	
	excluirFaixa = function(index){
		if (!confirm(i18n_message('recurso.desejaExcluirFaixa'))){
			return;
		}
		HTMLUtils.deleteRow('tblFaixaControle', index);
	};
	
	function LOOKUP_SOLICITANTE_select(id, desc){
		document.form.idSolicitante.value = id;
		document.form.nomeSolicitante.value = desc;
	}
	
	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc){
		document.form.idItemConfiguracaoPai.value = id;
		document.form.nomeItemConfiguracaoPai.value = desc;
		listaInfoRelacionadaIC();
	}
	
	function listaInfoRelacionadaContrato(){
		document.form.fireEvent('listaServicos');
	}
	
	function listaInfoRelacionadaIC(){
		document.form.fireEvent('listaICSVinc');
	}
	
	function limparIC(){
		document.form.nomeItemConfiguracaoPai.value = '';
		document.form.idItemConfiguracaoPai.value = '';
		HTMLUtils.removeAllOptions('idItemConfiguracao');
	}
		
</script>
	
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title= "<i18n:message key='citcorpore.comum.aguardeProcessando'/>"
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>

	
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="recurso.recurso" />
				</h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message
								key="recurso.cadastro" /> </a>
					</li>
					<li><a href="#tabs-2" class="round_top"><i18n:message
								key="recurso.pesquisa" /> </a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/recurso/recurso'>
								<input type='hidden' name='idRecurso' /> <input type='hidden'
									name='colFaixasSerialize' />
								<table id="tabFormulario" cellpadding="0" cellspacing="0">
									<tr>
										<td class="campoEsquerda"><label  class="campoObrigatorio"><i18n:message key="recurso.nomeRecurso" />:</label></td>
										<td><input type='text' name='nomeRecurso' size="70"
											maxlength="100"
											class="Valid[Required] Description[recurso.nomeRecurso]" />
										</td>
									</tr>
									<tr>
										<td class="campoEsquerda"><label  class="campoObrigatorio"><i18n:message key="controle.grupo" />:</label></td>
										<td><select name='idGrupoRecurso'
											class="Valid[Required] Description[controle.grupo]">
										</select>
										</td>
									</tr>
									<tr>
										<td class="campoEsquerda"><label><i18n:message key="recurso.recursoPai"/>:</label></td>
										<td><select name='idRecursoPai'
											class="Description[recurso.recursoPai]">
										</select>
										</td>
									</tr>
									<tr>
										<td class="campoEsquerda"><i18n:message key="recurso.tipoConexao"/>:</td>
										<td><select name='tipoAtualizacao'
											class="Description[recurso.tipoConexao]">
											<option value='M'><i18n:message key="recurso.manual"/></option>
											<option value='<%=RecursoDTO.NAGIOS_NATIVE%>'>Nagios NDOUtils</option>
											<option value='<%=RecursoDTO.NAGIOS_CENTREON%>'>Nagios/Centreon</option>
										</select>
										</td>
									</tr>									
									<tr>
										<td class="campoEsquerda"><i18n:message key="recurso.conexaoNagios"/>:</td>
										<td><select name='idNagiosConexao'
											class="Description[recurso.conexaoNagios]">
										</select>
										</td>
									</tr>									
									<tr>
										<td class="campoEsquerda"><i18n:message key="recurso.nomeHost"/>:</td>
										<td><input type='text' name='hostName' size="70"
											maxlength="255"
											class="Description[recurso.conexaoNagios]" />
										</td>
									</tr>	
									<tr>
										<td class="campoEsquerda"><i18n:message key="servico.nome"/>:</td>
										<td><input type='text' name='serviceName' size="70"
											maxlength="255"
											class="Description[servico.nome]" />
										</td>
									</tr>	
									<tr>
										<td colspan="2">
											<table>
												<tr>
													<td class="campoEsquerda"><label  class="campoObrigatorio"><i18n:message key="recurso.periodoMaiorAtividade"/>:</label></td>
													<td><input type='text' name='horaInicioFunc' size="5"
														maxlength="5"
														class="Valid[Required,Hora] Format[Hora] Description[carteiraTrabalho.horaInicio]" />
													</td>
													<td>
													a
													</td>
													<td><input type='text' name='horaFimFunc' size="5"
														maxlength="5"
														class="Valid[Required,Hora] Format[Hora] Description[recurso.horaFim]" />
													</td>
												</tr>
											</table>
										</td>
									</tr>	
									<tr>
										<td class="campoEsquerda"><label  class="campoObrigatorio"><i18n:message key="calendario.calendario"/>:</label></td>
										<td><select name='idCalendario'
											class="Valid[Required] Description[calendario.calendario]">
										</select>
										</td>
									</tr>																																	
									<tr>
										<td class="campoEsquerda"><label  class="campoObrigatorio"><i18n:message key="citcorporeRelatorio.comum.dataInicio"/>:</label></td>
										<td><input type='text' name='dataInicio' size="10"
											maxlength="10"
											class="Valid[Required,Date] Description[citcorporeRelatorio.comum.dataInicio] Format[Date] datepicker" />
										</td>
									</tr>
									<tr>
										<td class="campoEsquerda"><i18n:message key="contrato.datafimcontrato"/>:</td>
										<td><input type='text' name='dataFim' size="10"
											maxlength="10"
											class="Valid[Date] Description[contrato.datafimcontrato] Format[Date] datepicker" />
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<fieldset>
												<legend><b><i18n:message key="recurso.aberturaAutomaticaIncidente"/></b></legend>
												<table>
													<tr>
														<td class="campoEsquerda"><i18n:message key="recurso.statusParaAberturaIncidente"/>:</td>
														<td colspan="2">
															<input type='text' name='statusAberturaInc' size="100"	maxlength="255" />
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="colaborador.solicitante"/>:</td>
														<td colspan="2">
															<input type='hidden' name='idSolicitante' />
															<input type='text' name='nomeSolicitante' size="100" maxlength="255" readonly="readonly" onclick='LOOKUP_SOLICITANTE.show()' onfocus='LOOKUP_SOLICITANTE.show()'/>
															<cit:lookupField formName='form'
																lockupName='LOOKUP_SOLICITANTE' id='LOOKUP_SOLICITANTE' top='0'
																left='0' len='550' heigth='400' javascriptCode='true'
																htmlCode='true' hide='true'/>															
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="recurso.emailAberturaIncidente"/>:</td>
														<td colspan="2">
															<input type='text' name='emailAberturaInc' size="100"	maxlength="255" />
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="recurso.descricaoTexto"/>:</td>
														<td colspan="2">
															<textarea name='descricaoAbertInc' rows="5" cols="100"></textarea>
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="itemConfiguracaoTree.impacto"/>:</td>
														<td colspan="2">
															<select name='impacto' id='impacto'>
															</select>														
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="itemConfiguracaoTree.urgencia"/>:</td>
														<td colspan="2">
															<select name='urgencia' id='urgencia'>
															</select>
														</td>														
													</tr>	
													<tr>
														<td class="campoEsquerda"><i18n:message key="lookup.origem"/>:</td>
														<td colspan="2">
															<select name='idOrigem' id='idOrigem'>
															</select>
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="menu.relatorio.gerContrato"/>:</td>
														<td colspan="2">
															<select name='idContrato' id='idContrato' onchange='listaInfoRelacionadaContrato()'>
															</select>
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="portal.carrinho.servico"/>:</td>
														<td colspan="2">
															<select name='idServico' id='idServico'>
															</select>
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="solicitacaoServico.grupo"/>:</td>
														<td colspan="2">
															<select name='idGrupo' id='idGrupo'>
															</select>
														</td>														
													</tr>													
													<tr>
														<td class="campoEsquerda"><i18n:message key="recurso.eventoSerGerado"/>:</td>
														<td colspan="2">
															<select name='idEventoMonitoramento' id='idEventoMonitoramento'>
															</select>
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda" style='vertical-align: middle;'><i18n:message key="itemConfiguracaoTree.itemConfiguracao"/>:</td>
														<td style='vertical-align: top'>
															<input type='hidden' name='idItemConfiguracaoPai' />
															<input type='text' name='nomeItemConfiguracaoPai' size="100" maxlength="255" readonly="readonly" onclick='LOOKUP_PESQUISAITEMCONFIGURACAO.show()' onfocus='LOOKUP_PESQUISAITEMCONFIGURACAO.show()'/>
															<cit:lookupField formName='form'
																lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0'
																left='0' len='550' heigth='400' javascriptCode='true'
																htmlCode='true' hide='true'/>															
														</td>
														<td style='vertical-align: top'>
															<button type="button" onclick='limparIC()'><i18n:message key="citcorpore.ui.botao.rotulo.Limpar" /></button>
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="itemConfiguracao.itemConfiguracaoFilho"/>:</td>
														<td colspan="2">
															<select name='idItemConfiguracao' id='idItemConfiguracao'>
															</select>															
														</td>														
													</tr>																										
												</table>
											</fieldset>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<fieldset>
												<legend><b><i18n:message key="contrato.alertas"/></b></legend>
												<table>
													<tr>
														<td class="campoEsquerda"><i18n:message key="recurso.statusParaAviso"/>:</td>
														<td>
															<input type='text' name='statusAlerta' size="100"	maxlength="255" />
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="recurso.emailAlerta"/>:</td>
														<td>
															<textarea name='emailsAlerta' rows="5" cols="100"></textarea>
														</td>														
													</tr>
													<tr>
														<td class="campoEsquerda"><i18n:message key="recurso.descricaoTexto"/>:</td>
														<td>
															<textarea name='descricaoAlerta' rows="5" cols="100"></textarea>
														</td>														
													</tr>																										
												</table>
											</fieldset>
										</td>
									</tr>																		
									<tr>
										<td colspan="2">
											<table width="100%">
												<tr>
													<td><input type='button' name='btnAddItem'
														value=<i18n:message key="recurso.adicionarFaixaControle"/>
														onclick='showAdicionarFaixaControle()' />
													</td>
												</tr>
											</table>
											<table id='tblFaixaControle' width="100%">
												<tr>
													<td class="linhaSubtituloGrid"><i18n:message key="recurso.valorInicio"/></td>
													<td class="linhaSubtituloGrid"><i18n:message key="recurso.valorFinal"/></td>
													<td class="linhaSubtituloGrid"><i18n:message key="recurso.cor"/></td>
													<td class="linhaSubtituloGrid">&nbsp;</td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td colspan="2">&nbsp;</td>
									</tr>
									<tr>
										<td colspan="2" class="campoObrigatorio">*
										<i18n:message key="citcorpore.ui.mensagem.Campos_com_preenchimento_obrigatorio"/></td>
									</tr>
									<tr>
										<td colspan='2'><button type='button' name='btnGravar'
											 onclick='document.form.save();'><i18n:message key="citcorpore.comum.gravar" /></button> 
											<button type='button' name='btnLimpar'
											onclick='document.form.clear();' ><i18n:message key="citcorpore.ui.botao.rotulo.Limpar"/></button>
										</td>
									</tr>
								</table>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_RECURSO' id='LOOKUP_RECURSO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>

	<cit:janelaPopup modal="true"
		style="display:none;top:100px;width:425px;left:50px;height:225px;position:absolute;"
		title="<i18n:message key='recurso.definicaoFaixaControle'/>" id="POPUP_FAIXA_CONTROLE">
		<form name='formFaixaControle'>
			<table>
				<tr>
					<td class='campoObrigatorio'><i18n:message key='criterioAvaliacao.criterio_descricao'/>:</td>
					<td colspan="2"><input type='text' name='descricao' size="20"
						maxlength="50" class='Valid[Required] Description[Descrição]' />
					</td>
				</tr>
				<tr>
					<td class="campoObrigatorio"><i18n:message key='recurso.valorInicio'/>:</td>
					<td colspan="2"><input type='text' name='valorInicio'
						size="15" maxlength="15"
						class='Format[Moeda] Valid[Required] Description[recurso.valorInicio]' />
					</td>
				</tr>
				<tr>
					<td class="campoObrigatorio"><i18n:message key='recurso.valorFinal'/>:</td>
					<td colspan="2"><input type='text' name='valorFim' size="15"
						maxlength="15"
						class='Format[Moeda] Valid[Required] Description[recurso.valorFinal]' />
					</td>
				</tr>
				<tr>
					<td class="campoObrigatorio"><i18n:message key='recurso.cor'/>:</td>
					<input type='hidden' name='cor'
						class='Valid[Required] Description[recurso.cor]' />
					<td>
						<div id='divCor'
							style='width: 100px; border: 1px solid black; height: 18px;'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</div>
					</td>
					<td><input type='button' name='btnAddFaixaValorCor'
						value=<i18n:message key='recurso.definirCor'/>
						onclick='POPUP_CORES.show()' style='width: 210px' />
					</td>
				</tr>
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="3">
						<table>
							<tr>
								<td><input type='button' name='btnConfirmarFaixaControle'
									value='&nbsp;OK&nbsp;' onclick='validaFaixaControle()' />
								</td>
								<td>&nbsp;</td>
								<td><input type='button' name='btnFecharFaixaControle'
									value=<i18n:message key='citcorpore.comum.fechar'/> onclick='POPUP_FAIXA_CONTROLE.hide()' />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</form>
	</cit:janelaPopup>

	<cit:janelaPopup modal="true"
		style="display:none;top:100px;width:430px;left:50px;height:320px;position:absolute;"
		title="<i18n:message key='recurso.selecaoCor'/>" id="POPUP_CORES">
		<div id='divCores'
			style='width: 420px; height: 280px; overflow: auto;'>
			<table style='width: 20px'>
				<script language="javascript"> 
			var r = new Array("00","33","66","99","CC","FF"); 
			var g = new Array("00","33","66","99","CC","FF"); 
			var b = new Array("00","33","66","99","CC","FF"); 
			
			for (i=0;i<r.length;i++){ 
			    for (j=0;j<g.length;j++) { 
			       document.write("<tr>"); 
			       for (k=0;k<b.length;k++) { 
			          var novoc = "#" + r[i] + g[j] + b[k]; 
			          document.write("<td style=\"background-color:" + novoc + "\" bgcolor=\"" + novoc + "\" align=center onclick=setaCor('" + novoc + "') style='cursor:pointer'>"); 
			          document.write(novoc); 
			          document.write("</td>"); 
			       } 
			       document.write("</tr>"); 
			    } 
			} 
			</script>
			</table>
		</div>
	</cit:janelaPopup>

	<script>	
	document.form.onClear = function () {
		HTMLUtils.deleteAllRows('tblFaixaControle');
		limparIC();
	};	
	
	/* document.form.afterRestore = function () {
		document.getElementById('tabTela').tabber.tabShow(0);
	};	 */	
	
	document.form.onValidate = function () {
		document.form.colFaixasSerialize.value = '';
		
		var objs = HTMLUtils.getObjectsByTableId('tblFaixaControle');
      	
      	var objsSerializados = ObjectUtils.serializeObjects(objs);		
      	
		document.form.colFaixasSerialize.value = objsSerializados;	
		
		return true;	
	};
	
	/** -- Esta chamada eh necessaria para gerar o objeto TAB na tela -- **/
	//tabberAutomatic(tabberOptions);	
</script>

	<%@include file="/include/footer.jsp"%>
</body>
</html>

