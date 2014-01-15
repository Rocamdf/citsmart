<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<!doctype html public "">
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
		<html lang="en-us" class="no-js">
		<!--<![endif]-->
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
			</style>
		<%}%>
		<script type="text/javascript" src="../../cit/objects/ServicoContratoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/ServicoDTO.js"></script>
		<script type="text/javascript">
			function tamanhoCampo(obj, limit) {
		  		if (obj.value.length >= limit) {
		  			obj.value = obj.value.substring(0, limit-1);
		  		}
		  	}
			
			function limpar(){
				document.getElementById('divByCustoTotal').style.display = 'block';
				//document.getElementById('divByCustoTotal2').style.display = 'block';
				document.getElementById('divByCustoFormula').style.display = 'none';
				document.getElementById('divByCustoFormula2').style.display = 'none';
				document.getElementById('divByCustoFormula3').style.display = 'none';
				document.getElementById('formulaResult').style.display = 'none';
				document.getElementById('CONTABILIZAR').value = "N";
				document.getElementById('tipoCusto').value = "C";
				document.getElementById('divComboServicoContrato').style.display = 'none';
				document.getElementById('addServicoContrato').value = "";
				document.getElementById('idServicoContratoContabil').value = "";
			}
			
			var objTab = null;
			
			addEvent(window, "load", load, false);
			function load() {
				setTimeout(function(){
					if ('<%=request.getParameter("idServicoContrato").toString()%>' != '' && '<%=request.getParameter("idServicoContrato").toString()%>' != '') {
						document.formInterno.fireEvent("recupera");
					}	
				}, 1000);
				$("#POPUP_SERVICOCONTRATO").dialog({
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
					
			}	$("#POPUP_SERVICOCONTRATO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});	
			function excluir() {
				if (document.getElementById("idAtividadeServicoContrato").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.formInterno.fireEvent("delete");
					}
				}
			}
			
			function closePopup(idServicoContrato) {
				parent.fecharVisaoPainel(idServicoContrato,"AtividadesServico");
			}
			
			function gravar(){
				
				var descricaoAtividade = document.getElementById('descricaoAtividade').value;
				
				if(descricaoAtividade == ""){
					alert(i18n_message("atividadeServico.faltaDescricaoAtividade"));
					return;
				}
				
				var tipoFormulaValidar = document.getElementById('tipoCusto').value;
				
				if(tipoFormulaValidar == "C"){
					validaCustoAtividade();
					return;
				}
				if(tipoFormulaValidar == "F"){
					validaCamposFormula();
					return;
				}
			}
		
			function validaCamposFormula(){
				var hora = document.getElementById('hora').value;
				var complexidade = document.getElementById('complexidade').value;
				var quantidade = document.getElementById('quantidade').value;
				var periodo = document.getElementById('periodo').value;
				if(hora == "" || complexidade == "" || periodo == "" || ((periodo != 4 && quantidade == "") && (periodo != 5 && quantidade == ""))){
					alert(i18n_message("atividadeServico.formulaNaoCompleta"));
					return false;
				}else{
					document.formInterno.fireEvent("save");
					return true;
				}
			}
			
			function validaCustoAtividade(){
				var custo = document.getElementById('custoAtividade').value;
				if(custo == ""){
					alert(i18n_message("atividadeServico.custoNaoInformado"));
					return false;
				}else{
					document.formInterno.fireEvent("save");
					return true;
				}
			}
			
			function avaliaTipoCusto(){
				document.getElementById('obs1').style.display = 'none';
				document.getElementById('obs2').style.display = 'none';
				document.getElementById('divByCustoTotal').style.display = 'none';
				//document.getElementById('divByCustoTotal2').style.display = 'none';
				document.getElementById('divByCustoFormula').style.display = 'none';
				document.getElementById('divByCustoFormula2').style.display = 'none';
				document.getElementById('divByCustoFormula3').style.display = 'none';
				if (document.formInterno.tipoCusto.value == 'C'){
					document.getElementById('divByCustoTotal').style.display = 'block';
					//document.getElementById('divByCustoTotal2').style.display = 'block';
					limparFormula();
				}
				if (document.formInterno.tipoCusto.value == 'F'){
					document.getElementById('divByCustoFormula').style.display = 'block';
					//document.getElementById('divByCustoFormula2').style.display = 'block';
					limparCusto();
				}
			}
			
			function avaliaContabil(){
				var contabilizar = document.formInterno.CONTABILIZAR.value;
				if(contabilizar == 'S'){
					document.getElementById('divComboServicoContrato').style.display = 'block';
				}else{
					document.getElementById('idServicoContratoContabil').value = "";
					document.getElementById('addServicoContrato').value = '';
					document.getElementById('divComboServicoContrato').style.display = 'none';
				}
			}
			
			function limparFormula(){
				document.getElementById('hora').value = "";
				document.getElementById('complexidade').value = "";
				document.getElementById('quantidade').value = "";
				document.getElementById('periodo').value = "";
				document.getElementById('formula').value = "";
			}
			
			function limparCusto(){
				document.getElementById('custoAtividade').value = "";
			}
			
			function setaValorServicoContrato(){
				var servicoContrato = document.getElementById('idServico').value;
				document.getElementById('idServicoContratoContabil').value = servicoContrato;
			}
			
			function geraFormula(){
				
				var hora = document.getElementById('hora').value;
				var complexidade = document.getElementById('complexidade').value;
				var quantidade = "";
				var periodo = document.getElementById('periodo').value;
				
				quantidade = document.getElementById('quantidade').value;
				
				document.getElementById('quantidade').disabled = 0;
				
				if(hora != "" && complexidade != "" && periodo != ""){
								
					var periodoText = document.getElementById('periodo').options[document.getElementById('periodo').selectedIndex].textContent;
					
					if(quantidade == "" && (periodo != 4 && periodo != 5)){
						document.getElementById('obs1').style.display = 'none';
						document.getElementById('obs2').style.display = 'none';
						document.getElementById('formulaResult').style.display = 'none';
						return;
					}
					avaliaTipoCusto();
					
					var result = hora +" x "+ complexidade +" x "+ quantidade +" "+ periodoText;
					
					document.getElementById('formulaResult').innerHTML = result;
					document.getElementById('divByCustoFormula3').style.display = 'block';
					document.getElementById('formulaResult').style.display = 'block';
					document.getElementById('formula').value = result;
					
					if(periodo == 4){
						document.getElementById('obs1').style.display = 'block';
						document.getElementById('obs2').style.display = 'none';
					}
					if(periodo == 5){
						document.getElementById('obs1').style.display = 'none';
						document.getElementById('obs2').style.display = 'block';
					}
					if(periodo != 5 && periodo != 4){
						document.getElementById('obs1').style.display = 'none';
						document.getElementById('obs2').style.display = 'none';
					}
					
				}else{
					document.getElementById('obs1').style.display = 'none';
					document.getElementById('obs2').style.display = 'none';
					document.getElementById('formulaResult').style.display = 'none';
				}
			}
			
			$(function() {
				$("#addServicoContrato").click(function() {
					var idContrato = document.formInterno.idContrato.value;
					document.formPesquisaLocalidade.pesqLockupLOOKUP_SERVICOCONTRATO_IDCONTRATO.value = idContrato;
					$("#POPUP_SERVICOCONTRATO").dialog("open");
				});
			});	
			
			function LOOKUP_SERVICOCONTRATO_select(id, desc){
				document.formInterno.idServicoContratoContabil.value = id;
				document.formInterno.fireEvent("restoreAtividadeServico");
			}
			
			function fecharPopup(){
				$("#POPUP_SERVICOCONTRATO").dialog("close");
			}
		</script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper" style="padding: 0px !important;" >
			<%if (iframe == null) {%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%}%>
			<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
				<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>
				<div class="flat_area grid_16"></div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="grupovisao.atividades_servico_conforme" /></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='formInterno' id="formInterno" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/atividadesServicoContrato/atividadesServicoContrato'>
									<input type='hidden' name='idServicoContrato' value="<%=request.getParameter("idServicoContrato").toString()%>"/> 
									<input type='hidden' name='idContrato' value="<%=request.getParameter("idContrato").toString()%>"/>
									<input type='hidden' name='idAtividadeServicoContrato' value="<%=request.getParameter("idAtividadeServicoContrato").toString()%>" />
										<div class="col_100">
											<fieldset>
												<label class="campoObrigatorio" ><i18n:message key="visao.descricaoAtividade"/> </label>
												<div>
													<textarea id="descricaoAtividade" name="descricaoAtividade" rows="2" cols="10" style="display: block;" class="Valid[Required] Description[visao.descricaoAtividade]" ></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset>
												<label><i18n:message key="visao.observacao"/> </label>
												<div>
													<textarea id="obsAtividade" name="obsAtividade" rows="2" cols="10" style="display: block;"></textarea>
												</div>
											</fieldset>
										</div>
									<div class="col_100">
										<fieldset style="padding-top: 5px; padding-bottom: 10px;">
											<label><i18n:message key="visao.contabilizar"/></label>
											<select name="CONTABILIZAR" id="CONTABILIZAR" class=" Description[visao.contabilizar]" style="left-padding: 15px; width: 150px;margin-left:20px;"  onchange="avaliaContabil();">
												<option value="N"><i18n:message key="citcorpore.comum.nao"/></option>
												<option value="S"><i18n:message key="citcorpore.comum.sim"/></option>
											</select>	
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset style="padding-bottom: 10px;">
											<br/>
											<label class="campoObrigatorio"><i18n:message key="visao.tipoCusto"/></label>
												<input type='hidden' id='idServicoContratoContabil' name='idServicoContratoContabil'/>
											<select id="tipoCusto" name="tipoCusto" style="width: 150px;margin-left:20px;" class=" Valid[Required] Description[grupo.serviceDesk]" onchange="avaliaTipoCusto()">
												<option value='C'><i18n:message key="requisitosla.custo_total" /></option>
												<option value='F'><i18n:message key="requisitosla.formula" /></option>
											</select>
										</fieldset>
										</div>
										<br/>
										
										<div id='divByCustoTotal' style='display: block; margin-left:20px;width: 150px;'>
											<fieldset>
												<label class="campoObrigatorio" style="padding-left: 0px; padding-top: 10px;"><i18n:message key="requisitosla.custo_total"/>:</label>
												<input type='TEXT' id="custoAtividade"	name='custoAtividade' size='9' maxlength='9' class='Format[Money]' />
											</fieldset>
										</div>
										<div id='divByCustoFormula' style='display: none'>
											<fieldset>
												<br>
												<div style="float: left; padding-right: 10px; padding-top: 10px;"><i18n:message key="eventoItemConfiguracao.hora" />:<span style="color: red">*</span></div>
													<div style="width: 50px; float: left; padding-right: 10px;">
														<input id="hora" name='HORA' onchange="geraFormula()" style="width: 130px; float: left; padding-right: 10px;"
															type='TEXT' size='5' maxlength='5' class='Format[Money]' />
													</div>
													<div style="float: left; padding-right: 10px; padding-top: 10px;"><i18n:message key="matrizvisao.complexidade" />:<span style="color: red">*</span></div>
													<div style="width: 130px; float: left; padding-right: 10px;">
														<select id="complexidade" name="COMPLEXIDADE"
															onchange="geraFormula()" class="noClearCITAjax">
															<option value=""><i18n:message key="requisitosla.selecione" /></option>
															<option value="B"><i18n:message key="citcorpore.comum.complexidadeBaixa" /></option>
															<option value="I"><i18n:message key="citcorpore.comum.complexidadeIntermediaria" /></option>
															<option value="M"><i18n:message key="citcorpore.comum.complexidadeMediana" /></option>
															<option value="A"><i18n:message key="citcorpore.comum.complexidadeAlta" /></option>
															<option value="E"><i18n:message key="citcorpore.comum.complexidadeEspecialista" /></option>
														</select>
													</div>
													<div style="float: left; padding-right: 10px; padding-top: 10px;"><i18n:message key="citcorpore.comum.quantidade" />:<span style="color: red">*</span></div>
													<div style="width: 50px; float: left; padding-right: 10px;">
														<input id="quantidade" name='QUANTIDADE' style="width: 130px; float: left; padding-right: 10px;"
															type='TEXT' onchange="geraFormula()" size='5' maxlength='5' class='Format[Numero]' />
													</div>
													<div style="float: left; padding-right: 10px; padding-top: 10px;"><i18n:message key="citcorpore.comum.periodo" />:<span style="color: red">*</span></div>
													<div style="width: 130px; float: left; padding-right: 10px;">
														<select id="periodo" name="PERIODO" onchange="geraFormula()" class="noClearCITAjax">
															<option value=""><i18n:message key="requisitosla.selecione" /></option>
															<option value="1"><i18n:message key="citcorpore.texto.periodo.mensal" /></option>
															<option value="2"><i18n:message key="citcorpore.texto.periodo.semanal" /></option>
															<option value="3"><i18n:message key="citcorpore.texto.periodo.diario" /></option>
															<option value="4"><i18n:message key="citcorpore.comum.diasUteis" /></option>
															<option value="5"><i18n:message key="citcorpore.comum.diasCorridos" /></option>
														</select>
													</div>
											</fieldset>
										</div>
										<div id='divByCustoFormula2' style='display: none'>
										<br>
										</div>
										<div id='divByCustoFormula3' style='display: none'>
											<div><i18n:message key="formula.formula" />:</div>
											<br>
											<div id="formulaResult" style="font-weight: bold; font-size: 10pt;display: none;"></div>
											<input type='hidden' id="formula" name="FORMULA"></input>
											<div id="obs1" style="color: red; font-style: italic;"><i18n:message key="citcorpore.comum.obsSelecionarPeriodoDiasUteis" /></div>
											<div id="obs2" style="color: red; font-style: italic;"><i18n:message key="citcorpore.comum.obsSelecionarPeriodoDiasCorridos" /></div>
										</div>
										<div id="divComboServicoContrato" style="display: none;">
											<br>
											<fieldset>
												<label><i18n:message key="contrato.servicos_contrato" /></label>
												<div>
													<input id="addServicoContrato" type='text' readonly="readonly" name="nomeServico" maxlength="80" />
												</div>
											</fieldset>
										</div>
									<br>
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type='reset' name='btnLimpar' class="light" onclick="limpar();">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" /></span>
									</button>
									<button type='button' name='btnExcluir' id="btnExcluir"	class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" /></span>
									</button>
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
	<div id="POPUP_SERVICOCONTRATO" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
		<div class="box grid_16 tabs" style='width: 560px !important;height: 560px !important;' >
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section"  >
						<form name='formPesquisaLocalidade' style='width: 530px !important; ' >
							<cit:findField formName='formPesquisaLocalidade' lockupName='LOOKUP_SERVICOCONTRATO' id='LOOKUP_SERVICOCONTRATO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div> 
		</div>
	</div>
</html>
