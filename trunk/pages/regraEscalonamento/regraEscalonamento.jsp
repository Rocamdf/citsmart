<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
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
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript"
	src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/EscalonamentoDTO.js"></script>

<style type="text/css">
.table {
	border-left: 1px solid #ddd;
}

.table th {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-left: none;
	background: #eee;
}

.table td {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-top: none;
	border-left: none;
}

.tableLess {
	font-family: arial, helvetica !important;
	font-size: 10pt !important;
	cursor: default !important;
	margin: 0 !important;
	background: white !important;
	border-spacing: 0 !important;
	width: 100% !important;
}

.tableLess tbody {
	background: transparent !important;
}

.tableLess * {
	margin: 0 !important;
	vertical-align: middle !important;
	padding: 2px !important;
}

.tableLess thead th {
	font-weight: bold !important;
	background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom
		!important;
	text-align: center !important;
}

.tableLess tbody tr:ACTIVE {
	background-color: #fff !important;
}

.tableLess tbody tr:HOVER {
	background-color: #e7e9f9;
	cursor: pointer;
}

.tableLess th {
	border: 1px solid #BBB !important;
	padding: 6px !important;
}

.tableLess td {
	border: 1px solid #BBB !important;
	padding: 6px 10px !important;
}

.sel {
	display: none;
	background: none !important;;
	cursor: auto;
	padding: 0;
	margin: 0;
}

.sel td {
	padding: 0;
	margin: 0;
}

.table {
	border-left: 1px solid #ddd;
}

.table th {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-left: none;
	background: #eee;
}

.table td {
	border: 1px solid #ddd;
	padding: 4px 10px;
	border-top: none;
	border-left: none;
	text-align: center;
}

.sel-s {
	padding-left: 20px !important;
	border: 0px !important;
	width: 50% !important;
}

.form {
	width: 100%;
	float: right;
}

.formHead {
	float: left;
	width: 99%;
	border: 1px solid #ccc;
	padding: 5px;
}

.formBody {
	float: left;
	width: 99%;
	border: none;
	padding: 5px;
}

.formRelacionamentos div {
	float: left;
	width: 99%;
	border: 1px solid #ccc;
	padding: 5px;
}

.formFooter {
	float: left;
	width: 99%;
	border: 1px solid #ccc;
	padding: 5px;
}

.divEsquerda {
	float: left;
	width: 47%;
	border: 1px solid #ccc;
	padding: 5px;
}

.divDireita {
	float: right;
	width: 47%;
	border: 1px solid #ccc;
	padding: 5px;
}

.ui-tabs .ui-tabs-nav li a {
	background-color: #fff !important;
}

.ui-state-active {
	background-color: #aaa;
}

#tabs div {
	background-color: #fff;
}

.ui-state-hover {
	background-color: #ccc !important;
}

#contentBaseline {
	padding: 0 !important;
	margin: 0 !important;
	border: 0 !important;
}

.padd10 {
	padding: 0 10px;
}

.lFloat {
	float: left;
}

#divGrupoItemConfiguracao {
	display: none;
}

.checkboxRegra {
	display: inline-block;
	padding: 4px 12px;
	margin-bottom: 0;
	text-align: center;
	vertical-align: middle;
	margin-top: 20px;
}
input.text, .textarea, .ui-multiselect, table input.text {
	margin-top: 0 !important;
	}

</style>

<script type="text/javascript">
		
				var objTab = null;
		
				addEvent(window, "load", load, false);
		
				function load() {
					document.form.afterRestore = function() {
						$('.tabs').tabs('select', 0);
					}
					
					$("#POPUP_SERVICO").dialog({
						autoOpen : false,
						width : 600,
						height : 400,
						modal : true
					});
					
					$("#POPUP_GRUPO").dialog({
						autoOpen : false,
						width : 600,
						height : 400,
						modal : true
					});
					
					$("#POPUP_SOLICITANTE").dialog({
						autoOpen : false,
						width : 600,
						height : 400,
						modal : true
					});
					
				}
		
				function excluir() {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("excluir");
					}
				}
		
				function LOOKUP_REGRAESCALONAMENTO_select(idParam, desc) {
					document.form.restore({
						idRegraEscalonamento : idParam
					});
				}
				
				function LOOKUP_SERVICO_select(id, desc){
					document.getElementById("idServico").value = id;
					document.getElementById("servico").value = desc;
					$("#POPUP_SERVICO").dialog("close");
				}

				function LOOKUP_GRUPO_select(id, desc){
					document.getElementById("idGrupo").value = id;
					document.getElementById("grupo").value = desc;
					$("#POPUP_GRUPO").dialog("close");
				}
				
				function LOOKUP_SOLICITANTE_select(id, desc){
					document.getElementById("idSolicitante").value = id;
					document.getElementById("nomeSolicitante").value = desc;
					$("#POPUP_SOLICITANTE").dialog("close");
				}
				
				function abrePopupServico(){
					$("#POPUP_SERVICO").dialog("open");
				}

				function abrePopupGrupo(){
					$("#POPUP_GRUPO").dialog("open");
				}
				
				function abrePopupUsuario(){
					$("#POPUP_SOLICITANTE").dialog("open");
				}
				
				function addGrupoExecutor(){
					if(document.getElementById('idGrupoAtual').value == ""){
						alert(i18n_message("regraEscalonamento.alerta.informeGrupoExecutor"));
						return;
					}else if(document.getElementById('prazoExecucao').value == ""){
						alert(i18n_message("regraEscalonamento.alerta.informePrazoExecucao"));
						return;						
					}
			        var obj = new CIT_EscalonamentoDTO();
			        obj.idGrupoExecutor = document.getElementById('idGrupoAtual').value;
			        obj.descricao = document.getElementById('idGrupoAtual').options[document.getElementById('idGrupoAtual').selectedIndex].text;
					obj.prazoExecucao = document.getElementById('prazoExecucao').value;
					obj.idPrioridade = document.getElementById('idPrioridade').value;
					obj.descrPrioridade = document.getElementById('idPrioridade').options[document.getElementById('idPrioridade').selectedIndex].text;
					
					HTMLUtils.addRow('tblGrupoExecutor', document.form, '', obj, 
							['', 'idGrupoExecutor', 'descricao', 'prazoExecucao', 'idPrioridade', 'descrPrioridade'], ["idGrupoExecutor"], i18n_message("regraEscalonamento.alerta.grupoJaAdicionado"), [gerarImgDelGrupoExecutor], null, null, false);
			        
				}
				
				function deleteLinha(table, index){
					if (index > 0 && confirm(i18n_message("regraEscalonamento.alerta.exclusaoEscalonamento"))) {
						HTMLUtils.deleteRow(table, index);	
					}
				}				
				
				function gerarImgDelGrupoExecutor(row, obj){
				        row.cells[0].innerHTML = '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" style="cursor: pointer;" onclick="deleteLinha(\'tblGrupoExecutor\', this.parentNode.parentNode.rowIndex);"/>';
	}

	function serializaTabelaGrupos() {
		var itens = HTMLUtils.getObjectsByTableId('tblGrupoExecutor');
		document.form.grupos_serialize.value = ObjectUtils
				.serializeObjects(itens);
	}

	limpar = function() {
		document.form.clear();
		HTMLUtils.deleteAllRows("tblGrupoExecutor");
	}

	salvar = function() {
		serializaTabelaGrupos();

		if (document.getElementById("tempoExecucao").value == ""
				&& document.getElementById("idTipoGerenciamento").value == 1) {
			alert(i18n_message("regraEscalonamento.alerta.informePrazoRestanteExecucao"));
			document.getElementById("tempoExecucao").focus();
			return;
		}
		if (document.getElementById("enviarEmail").value == "S"
				&& document.getElementById("intervaloNotificacao").value == "") {
			alert(i18n_message("regraEscalonamento.alerta.informeIntervaloNotificacao"));
			document.getElementById("enviarEmail").focus();
			return;
		}
		document.form.save();
	}

	function LimparCampoServico() {
		document.getElementById("servico").value = "";
		document.getElementById("idServico").value = "";
	}
	function LimparCampoGrupo() {
		document.getElementById("grupo").value = "";
		document.getElementById("idGrupo").value = "";
	}
	function LimparCampoNomeSolicitante() {
		document.getElementById("nomeSolicitante").value = "";
		document.getElementById("idSolicitante").value = "";
	}
</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="regraEscalonamento.regraEscalonamentoTitulo" />
				</h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message
								key="regraEscalonamento.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message
								key="regraEscalonamento.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/regraEscalonamento/regraEscalonamento'>
								<div class="columns clearfix">
									<input type='hidden' id='idRegraEscalonamento'
										name='idRegraEscalonamento' /> <input type="hidden"
										id='idServico' name='idServico'> <input type="hidden"
										id='idGrupo' name='idGrupo'> <input type="hidden"
										id='idSolicitante' name='idSolicitante'>
									<div
										style='padding-top: 1px; padding-bottom: 15px; padding-right: 15px; padding-left: 15px;'>
										<fieldset>
											<strong><legend>
													<i18n:message key="citcorpore.comum.filtros" />
												</legend></strong>
											<div class="col_15">
												<label class="tooltip_bottom campoObrigatorio"
													style="cursor: pointer; width: 150px !important;"
													title="<i18n:message key="regraEscalonamento.tipoGerenciamentotool"/>">
													<i18n:message key="regraEscalonamento.tipoGerenciamento" />
												</label>
												<div>
													<select name='idTipoGerenciamento' id='idTipoGerenciamento'
														
														class="Valid[Required] Description[<i18n:message key='regraEscalonamento.tipoGerenciamento'/>]"></select>
												</div>
											</div>

											<div class="col_25">
												<label class=""><i18n:message
														key="contrato.contrato" /></label>
												<div>
													<select name='idContrato' style='width: 250px;'
														class="Description[<i18n:message key='contrato.contrato' />]">
													</select>
												</div>
											</div>

											<div class="col_30">
												<label class=""><i18n:message key="servico.servico" /></label>
												<div>
													<table width="100%">
														<tr>
															<td style="width: 98%;padding-top: 0 !important;vertical-align: top !important;"><input type="text"
																onfocus='abrePopupServico();' id="servico"
																name="servico" /></td>
															<td style="width: 2%;"><img
																src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/borracha.png'
																border='0' style="cursor: pointer"
																onclick='LimparCampoServico();' /></td>
														</tr>
													</table>
												</div>
											</div>

											<div class="col_20">
												<label><i18n:message
														key="tipoLiberacao.nomeGrupoExecutor" /></label>
												<div>
													<table width="100%">
														<tr>
															<td style="width: 98%;padding-top: 0 !important;vertical-align: top !important;"><input type="text"
																onfocus='abrePopupGrupo();' id="grupo" name="grupo" /></td>
															<td style="width: 2%;"><img
																src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/borracha.png'
																border='0' style='cursor: pointer'
																onclick='LimparCampoGrupo();' /></td>
														</tr>
													</table>
												</div>
											</div>

											<div class="col_20">
												<label><i18n:message
														key="usuario.usuarioSolicitante" /></label>
												<div>
													<table width="100%">
														<tr>
															<td style='width: 98%;padding-top: 0 !important;vertical-align: top !important;'><input type="text"
																onfocus='abrePopupUsuario();' id="nomeSolicitante"
																name="nomeSolicitante" /></td>
															<td style='width: 2%;'><img
																src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/borracha.png'
																border='0' style='cursor: pointer'
																onclick='LimparCampoNomeSolicitante();' /></td>
														</tr>
													</table>
												</div>
											</div>

											<div class="col_15">
												<label><i18n:message
														key="tipoDemandaServico.tipoSolicitacao" /></label>
												<div>
													<select name='idTipoDemandaServico'
														class="Description[<i18n:message key='tipoDemandaServico.tipoSolicitacao' />]"
														>
													</select>
												</div>
											</div>

											
												<div id="divUrgencia" class="col_10">
													<label class=""><i18n:message
															key="solicitacaoServico.urgencia" /></label>
													<div>
														<select name='urgencia' id='urgencia'></select>
													</div>
												</div>

												<div id="divImpacto" class="col_10">
													<label class=""><i18n:message
															key="solicitacaoServico.impacto" /></label>
													<div>
														<select name='impacto' id='impacto'></select>
													</div>
												</div>
											
										</fieldset>
									</div>
									<div
										style='padding-top: 15px; padding-bottom: 15px; padding-right: 15px; padding-left: 15px;'>
										<fieldset>
											<strong><legend>
													<i18n:message
														key="regraEscalonamento.regraClassificacaoNotificacao" />
												</legend></strong>
											<div class="col_15">
												<label> <i18n:message
														key="regraEscalonamento.classificacaoEvento" />
												</label>
												<div>
													<input type='text'
														value='<i18n:message key="solicitacaoservico.vencendo"/>'
														readonly ></input>
												</div>
											</div>
											<div class="col_20">
												<label class="campoObrigatorio"> <i18n:message
														key="regraEscalonamento.prazoRestanteExecucao" />
												</label>
												<div>
													<input type='text' name='tempoExecucao' id='tempoExecucao'
														value='' class="Format[Numero]"
														></input>
												</div>
											</div>

											<div class="col_20">
												<label> <i18n:message
														key="regraEscalonamento.intervaloNotificacao" />
												</label>
												<div>
													<select name='intervaloNotificacao'
														id='intervaloNotificacao' ></select>
												</div>
											</div>
											<div class="">
												<div class="checkboxRegra">
													<input type="checkbox" name='enviarEmail' id='enviarEmail'
														value="S">
													<i18n:message key="regraEscalonamento.enviarEmail" />
												</div>
											</div>
											<div class="">
												<div class="checkboxRegra">
													<input type="checkbox" name="criaProblema"
														id="criaProblema" value="S" />
													<i18n:message key="tipoDemandaServico.criarProblema" />
												</div>
											</div>
											<div class="col_20">
												<label> <i18n:message
														key="regraEscalonamento.prazoCriarProblema" />
												</label>
												<div>
													<select name='prazoCriarProblema' id='prazoCriarProblema'
														></select>
												</div>
											</div>
											<div class="col_20">
												<label> <i18n:message
														key="regraEscalonamento.campoDataEscalonamento" />
												</label>
												<div>
													<select name='tipoDataEscalonamento'
														id='tipoDataEscalonamento'
														style='width: 150px !important;'></select>
												</div>
											</div>
										</fieldset>
									</div>
									<div
										style='padding-top: 15px; padding-bottom: 15px; padding-right: 15px; padding-left: 15px;'>
										<fieldset
											style='padding-top: 5px; padding-bottom: 5px; padding-right: 5px; padding-left: 5px;'>
											<strong><legend>
													<i18n:message key="regraEscalonamento.regraEscalonamento" />
												</legend></strong>
											<div class="col_25">
												<label class="tooltip_bottom campoObrigatorio"
													style="cursor: pointer;"
													title="<i18n:message key="regraEscalonamento.tipoGerenciamentotool"/>">
													<i18n:message key="citcorpore.comum.grupoExecutor" />
												</label>
												<div>
													<select name='idGrupoAtual' id='idGrupoAtual'
														></select>
												</div>
											</div>
											<div class="col_15">
												<label class="campoObrigatorio"><i18n:message
														key="citcorpore.comum.prazoExecucao" /></label>
												<div>
													<select name='prazoExecucao' id='prazoExecucao'
														></select>
												</div>
											</div>
											<div class="col_10">
												<label> <i18n:message
														key="regraEscalonamento.prioridade" />
												</label>
												<div>
													<select name='idPrioridade' id='idPrioridade'
														></select>
												</div>
											</div>
											<div class="col_25">
												<div style="margin-top: 26px">
													<button type='button' name='btnAddGrupo' class="light"
														onclick='addGrupoExecutor();'>
														<img
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
														<span><i18n:message
																key="citcorpore.comum.adicionar" /></span>
													</button>
												</div>
											</div>

											<!-- hiddens tabela de grupos  -->
											<input type="hidden" name="grupos_serialize"
												id="grupos_serialize" />

											
												<div class="formBody">
													<table id="tblGrupoExecutor" name="tblGrupoExecutor"
														class="table  table-bordered">
														<tr>
															<th height="10px" width="3%"></th>
															<th height="10px" width="13%"><i18n:message
																	key="grupo.idgrupo" /></th>
															<th height="10px" width="39%"><i18n:message
																	key="citcorpore.comum.grupoExecutor" /></th>
															<th height="10px" width="15%"><i18n:message
																	key="citcorpore.comum.prazoExecucao" /></th>
															<th height="10px" width="15%"><i18n:message
																	key="regraEscalonamento.idPrioridade" /></th>
															<th height="10px" width="15%"><i18n:message
																	key="regraEscalonamento.prioridade" /></th>
														</tr>
													</table>
												</div>
											
										</fieldset>
									</div>
									<br>
								</div>
								<br> <br>
								<button type='button' name='btnGravar' class="light"
									onclick='salvar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_REGRAESCALONAMENTO'
									id='LOOKUP_REGRAESCALONAMENTO' top='0' left='0' len='550'
									heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>

<div id="POPUP_SERVICO" title="<i18n:message key="servico.servico" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formServico' style="width: 540px">
						<cit:findField formName='formServico' lockupName='LOOKUP_SERVICO'
							id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='400'
							javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO" title="<i18n:message key="grupo.grupo" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formGrupo' style="width: 540px">
						<cit:findField formName='formGrupo' lockupName='LOOKUP_GRUPO'
							id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400'
							javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_SOLICITANTE"
	title="<i18n:message key="citcorpore.comum.pesquisacolaborador" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaUsuario' style="width: 540px">
						<cit:findField formName='formPesquisaUsuario'
							lockupName='LOOKUP_SOLICITANTE' id='LOOKUP_SOLICITANTE' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

</html>