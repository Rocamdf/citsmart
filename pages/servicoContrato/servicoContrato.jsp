<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "âœ°">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
	div#main_container {
		margin: 10px 10px 10px 10px;
	}
	.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	}
	
	.tableLess tbody {
	  background: transparent  !important;
	}
	
	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}
	
	.tableLess thead .th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}
	
	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}
	
	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9 ;
	}
	
	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}
	
	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}
</style>
<%}%>
	<script type="text/javascript" src="../../cit/objects/ServicoContratoDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/FluxoServicoDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/ServicoDTO.js"></script>
	<script type="text/javascript">
	
		function tamanhoCampo(obj, limit) {
	  		if (obj.value.length >= limit) {
	  			obj.value = obj.value.substring(0, limit-1);
	  		}
	  	}
		
		var objTab = null;
		
		addEvent(window, "load", load, false);
		
		function load() {
			setTimeout(function(){
				if ('<%=request.getParameter("idServicoContrato").toString()%>' != '' && '<%=request.getParameter("idServicoContrato").toString()%>' != '') {
					document.form.fireEvent("recupera");
				}	
			}, 500);
			$("#POPUP_SERVICOCONTRATO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_FLUXOTRABALHO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
		}		
		
		function excluir() {
			if (document.getElementById("idServicoContrato").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}
		
		function closePopup(idServicoContrato) {
			parent.fecharVisaoPainel(idServicoContrato,"ServicoContrato");
		}
	
		function setaValorServicoContrato(){
			var servicoContrato = document.getElementById('idServico').value;
			document.getElementById('idServico').value = servicoContrato;
		}
	
		$(function() {
			$("#addServicoContrato").click(function() {
				$("#POPUP_SERVICOCONTRATO").dialog("open");
			});
		});	
		
		function LOOKUP_SERVICO_select(id, desc){
			document.form.idServico.value = id;
			document.form.fireEvent("restoreServicoContrato");
		}
		function fecharPopup(){
			$("#POPUP_SERVICOCONTRATO").dialog("close");
		}
		
		function gravar(){
			if(serializa()){
				document.form.save();
			}
		}
		
		function ComparaDatas() {
			var data1 = document.getElementById("dataInicio").value;
			var data2 = document.getElementById("dataFim").value;
			
			var nova_data1 = parseInt(data1.split("/")[2].toString() + data1.split("/")[1].toString() + data1.split("/")[0].toString());
			var nova_data2 = parseInt(data2.split("/")[2].toString() + data2.split("/")[1].toString() + data2.split("/")[0].toString());
			 
			 if (nova_data2 < nova_data1){
				 alert('<i18n:message key="contrato.dataFimMenorDataInicio"/>');
				 document.getElementById("dataFim").value = "";
			 }
		}
		
		var contTipoFluxo = 0;
		$(function() {
			$("#addFluxo").click(function() {
				$("#POPUP_FLUXOTRABALHO").dialog("open");
			});
		});	
	
		function gravarFluxo(){
			var fase = document.getElementById("idfase");
			var faseNome = fase.options[fase.selectedIndex].text;
			var idFase = document.getElementById("idfase").value;
			
			var tipofluxo = document.getElementById("idtipofluxo");
			var tipoFluxoNome = tipofluxo.options[tipofluxo.selectedIndex].text;
			var idTipofluxo = document.getElementById("idtipofluxo").value;
			
			var fluxoPrincipal = document.getElementById("fluxoPrincipal");
			var fluxoPrincipalNome = fluxoPrincipal.options[fluxoPrincipal.selectedIndex].text;
			var idFluxoPrincipal = document.getElementById("fluxoPrincipal").value;
			
			if (idFase == "") {
				 alert('<i18n:message key="contrato.faseCampoObrigatorio"/>');
				return;
			}
			else if (idTipofluxo == ""){
				 alert('<i18n:message key="contrato.fluxoCampoObrigatorio"/>');
				return;
			}
			else{
				addLinhaTabelaFluxoTrabalho(idFase,idTipofluxo,idFluxoPrincipal,faseNome,tipoFluxoNome,fluxoPrincipalNome);
			}
		}
		
		function addLinhaTabelaFluxoTrabalho(idFase,idTipofluxo,idFluxoPrincipal,faseNome,tipoFluxoNome,fluxoPrincipalNome){
			var tbl = document.getElementById('tabelaFluxo');
			var lastRow = tbl.rows.length;
			var row = tbl.insertRow(lastRow);
			var coluna = row.insertCell(0);
			contTipoFluxo++;
			coluna.innerHTML = '<img id="imgDelEmpregado' + contTipoFluxo + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaFluxo\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = tipoFluxoNome + '<input type="hidden" id="idTipoFluxo' + contTipoFluxo + '" name="' + idTipofluxo + '" value="' + tipoFluxoNome + '" />';
			coluna = row.insertCell(2);
			coluna.innerHTML = faseNome + '<input type="hidden" id="idFase' + contTipoFluxo + '" name="' + idFase + '" value="' + faseNome + '" />';
			coluna = row.insertCell(3);
			coluna.innerHTML = fluxoPrincipalNome + '<input type = "hidden" id = "principal' + contTipoFluxo + '" name = "' + idFluxoPrincipal + '" value ="' + fluxoPrincipalNome + '" />';
				
			$("#POPUP_FLUXOTRABALHO").dialog("close");
		}
		
		function removeLinhaTabela(idTabela, rowIndex) {
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.idTipoFluxoExclusao.value = eval('document.getElementById("idTipoFluxo' + rowIndex + '").name');
				document.form.idFaseExclusao.value = eval('document.getElementById("idFase' + rowIndex + '").name');
				document.form.principalExclusao.value = eval('document.getElementById("principal' + rowIndex + '").name');
				/* document.form.fireEvent("deleteFluxo"); */
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.emailsSerializados.value = eval('document.getElementById("idTipoFluxo' + rowIndex + '").name');
				contTipoFluxo = 0;
		 	}
		 }
	    
		 function serializa(){
			 var tabela = document.getElementById('tabelaFluxo');
			 var count = tabela.rows.length;
			 var contadorAux = 0;
			 var prioridadeUnidade = new Array();
			 for ( var i = 1; i <= count; i++) {
			 var trObj = document.getElementById('idTipoFluxo' + i);
			 if (!trObj) {
			 continue;
			 }
			 prioridadeUnidade[contadorAux] = getFluxo(i);
			 contadorAux = contadorAux + 1;
			 }
	
			 var prioridadeUnidadeSerializada = ObjectUtils.serializeObjects(prioridadeUnidade);
			 document.form.fluxosSerializados.value = prioridadeUnidadeSerializada;
	
			 return true;
	
			 }
		 
		 function getFluxo(seq) {
			 var fluxoServicoDTO = new CIT_FluxoServicoDTO();
			 fluxoServicoDTO.sequencia = seq;
			 fluxoServicoDTO.idTipoFluxo = document.getElementById('idTipoFluxo' + seq).name; 
			 fluxoServicoDTO.idFase = document.getElementById('idFase' + seq).name;
			 fluxoServicoDTO.principal = document.getElementById('principal' + seq).name;
			 return fluxoServicoDTO;
		 }
		 
		 function exibirGrid(){
			document.getElementById('gridFluxos').style.display = 'block';
		}
	</script>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1">
					<i18n:message key="visao.servicoContrato" /></a></li>
				</ul>
					<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/servicoContratoUnico/servicoContratoUnico'>
								<input type='hidden' name='idServicoContrato' value="<%=request.getParameter("idServicoContrato").toString()%>"/> 
								<input type='hidden' id='idServico' name='idServico'/>
								<input type="hidden" id="fluxosSerializados" name="fluxosSerializados" />
								<input type="hidden" id="idTipoFluxoExclusao" name="idTipoFluxoExclusao" />	
								<input type="hidden" id="idFaseExclusao" name="idFaseExclusao" />	
								<input type="hidden" id="principalExclusao" name="principalExclusao" />	
								<input type="hidden" id="idContrato" name="idContrato" value="<%=request.getParameter("idContrato").toString()%>" />
								
								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="contrato.servicos_contrato" /></label>
										<div> 
											<input id="addServicoContrato" type='text' readonly="readonly" name="nomeServico" maxlength="80" class="Valid[Required] Description[contrato.servicos_contrato]"/>
										</div>
									</fieldset>
								</div>
										
								<div class="col_100">
									<div class="col_33">
										<fieldset style="height: 55px;">
											<label class="campoObrigatorio"><i18n:message key="visao.condicaoOperacao" /></label>		
											<div>
												<select style="width: 90%;" id='idCondicaoOperacao' name='idCondicaoOperacao' class="Valid[Required] Description[visao.condicaoOperacao]"></select>
											</div>
										</fieldset>
									</div>
									
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="visao.dataDeInicio"/></label>
											<div>
											  	<input  type='text'  id="dataInicio" style="width: 90% !important;"  name="dataInicio" maxlength="10" readonly="readonly" size="10"  class="Valid[Required,Data] Description[visao.dataDeInicio] Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
									
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="citcorpore.comum.datafim"/></label>
											<div>
										  		<input  type='text'  id="dataFim" style="width: 90% !important;" onchange="ComparaDatas()" name="dataFim" maxlength="10" readonly="readonly" size="10"  class="Format[Data] datepicker" />
											</div>
										</fieldset>
									</div>
								</div>
										
								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.observacao"/> </label>
											<div>
												<textarea id="observacao" name="observacao" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 1000);" onKeyUp="tamanhoCampo(this, 1000);"></textarea>
											</div>
										</fieldset>
									</div>
									
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.restricoesPressuposto"/> </label>
											<div>
												<textarea id="restricoesPressup" name="restricoesPressup" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 1000);" onKeyUp="tamanhoCampo(this, 1000);"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
									
								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.objetivo"/> </label>
											<div>
												<textarea id="objetivo" name="objetivo" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 1000);" onKeyUp="tamanhoCampo(this, 1000);"></textarea>
											</div>
										</fieldset>
									</div>
									
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.descricaoProcesso"/> </label>
											<div>
												<textarea id="descricaoProcesso" name="descricaoProcesso" rows="2" cols="10" style="display: block;" onKeyDown="tamanhoCampo(this, 100);" onKeyUp="tamanhoCampo(this, 100);"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
									
								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.linkProcesso" /></label>
											<div> 
												<input id="linkProcesso" type='text' name="linkProcesso" maxlength="500" />
											</div>
										</fieldset>
									</div>
									
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.areaRequisitante" /></label>
											<div> 
												<input id="areaRequisitante" type='text' name="areaRequisitante" maxlength="500" />
											</div>
										</fieldset>
									</div>
								</div>
									
								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="visao.modEmailAberturaIncidente" /></label>		
											<div>
												<select style="width: 100%;" id='idModeloEmailCriacao' name='idModeloEmailCriacao' class="Valid[Required] Description[visao.modEmailAberturaIncidente]"></select>
											</div>
										</fieldset>
									</div>
									
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.modEmailFinalizacaoIncidente" /></label>		
											<div>
												<select style="width: 100%;" id='idModeloEmailFinalizacoes' name='idModeloEmailFinalizacao'></select>
											</div>
										</fieldset>
									</div>
								</div>
									
								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.modEmailDemaisAcoes" /></label>		
											<div>
												<select style="width: 100%;" id='idModeloEmailAcoes' name='idModeloEmailAcoes' ></select>
											</div>
										</fieldset>
									</div>
									
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.grupoEscalacaoNivel1" /></label>		
											<div>
												<select style="width: 100%;" id='idGrupoNivel1' name='idGrupoNivel1'></select>
											</div>
										</fieldset>
									</div>
								</div>
									
								<div class="col_100">
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.grupoExecutor" /></label>		
											<div>
												<select style="width: 100%;" id='idGrupoExecutor' name='idGrupoExecutor'></select>
											</div>
										</fieldset>
									</div>
									
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="visao.grupoAprovador" /></label>		
											<div>
												<select style="width: 100%;" id='idGrupoAprovador' name='idGrupoAprovador'></select>
											</div>
										</fieldset>
									</div>
								</div>
									
								<div class="col_50">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="visao.calendario" /></label>		
										<div>
											<select style="width: 100%;" id='idCalendario' name='idCalendario' class="Valid[Required] Description[visao.calendario]"></select>
										</div>
									</fieldset>
								</div>
									
								<div class="col_100">
									<br><br>
									<fieldset>
										<label id="addFluxo" style="cursor: pointer;"title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />"><i18n:message  key="visao.fluxoServico" /><img	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"></label>
										<div  id="gridFluxos">
											<table class="tableLess" id="tabelaFluxo" width="100%">
												<thead>
													<tr class="th">
														<th></th>
														<th><i18n:message  key="visao.fluxo" /></th>
														<th><i18n:message  key="visao.fase" /></th>
														<th><i18n:message  key="visao.fluxoPrincipal" />	</th>
													</tr>
												</thead>
												<tbody></tbody>
											</table>
										</div>
									</fieldset>
									<br>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir" class="light" onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
	<div id="POPUP_FLUXOTRABALHO" title="<i18n:message  key="visao.fluxoServico" />">
		<div class="section">
			<div class="col_100">
				<fieldset>
					<label  class="campoObrigatorio"><i18n:message key="visao.fluxo" /></label>		
					<div>
						<select id='idtipofluxo' name='idtipofluxo' class="Valid[Required] Description[visao.modEmailAberturaIncidente]"></select>
					</div>
				</fieldset>
			</div>
			<div class="col_100">
				<fieldset>
					<label class="campoObrigatorio"><i18n:message key="visao.fase" /></label>		
					<div>
						<select id='idfase' name='idfase' class="Valid[Required] Description[visao.modEmailAberturaIncidente]"></select>
					</div>
				</fieldset>
			</div>
			
			<div class="col_100">
				<fieldset>
					<label class="campoObrigatorio"><i18n:message key="visao.fluxoPrincipal"/></label>
					<div>
					<select name="fluxoPrincipal" id="fluxoPrincipal">
						<option value="N"><i18n:message key="citcorpore.comum.nao"/></option>
						<option value="S"><i18n:message key="citcorpore.comum.sim"/></option>
					</select>	
					</div>
				</fieldset>
			</div>
			
			<button type='button' name='btnGravar' class="light" onclick='gravarFluxo();'>
				<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span><i18n:message key="citcorpore.comum.gravar" />
				</span>
			</button>
		</div>
	</div>
	
	<div id="POPUP_SERVICOCONTRATO" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
		<div class="box grid_16 tabs" style='width: 560px !important;height: 560px !important;' >
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section"  >
						<form name='formPesquisaServicoAtivo' style='width: 530px !important; ' >
							<cit:findField formName='formPesquisaServicoAtivo' lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div> 
		</div>
	</div>
</html>
