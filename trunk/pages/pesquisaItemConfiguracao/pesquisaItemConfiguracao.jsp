<%@page import="br.com.citframework.tld.I18N"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.PesquisaItemConfiguracaoDTO"%>
<!doctype html public "✰">
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
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
<script>
	addEvent(window, "load", load, false);
	function load() {
		popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}
	
	function pesquisa() {
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;

		if (dataInicio != '' || dataFim != '') {
			if (DateTimeUtil.isValidDate(dataInicio) == false) {
				alert(i18n_message("citcorpore.comum.datainvalida"));
	 			document.getElementById("dataInicio").value = '';
				return false;
			}
			
			if (DateTimeUtil.isValidDate(dataFim) == false) {
				alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
		 		document.getElementById("dataFim").value = '';
				return false;
			}
			
			if (!validaData(dataInicio, dataFim) ) {
				return false;
			}
		}
		
		document.form.fireEvent("pesquisarItemConfiguracao");
	}
	
	function verificarExpiracao() {
		document.form.fireEvent('verificarExpiracao');
	}
				

	function LOOKUP_PESQUISAITEMCONFIGURACAO_select(id, desc) {
		var valor = desc.split(' - ');
		document.form.ip.value = valor[0];
		document.form.idItemConfiguracao.value = id;
		
		document.formPesquisaFilho.pesqLockupLOOKUP_ITENSCONFIGURACAORELACIONADOS_iditemconfiguracaopai.value = id;
		$("#POPUP_ITEMCONFIG").dialog("close");		
		
	}
	
	function LOOKUP_ITENSCONFIGURACAORELACIONADOS_select(id, desc){	
		document.form.idItemConfiguracaoFilho.value = id;
		document.form.identificacaoFilho.value = desc;
		$("#POPUP_ITEMCONFIGFILHO").dialog("close");
	}
	
	function LOOKUP_GRUPOITEMCONFIGURACAO_select(id, desc) {
		document.form.idGrupoItemConfiguracao.value = id;
		document.form.nomeGrupoItemConfiguracao.value = desc;
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");		
	}
	function popupAtivos(idItem){
		var iframe = <%=iframe%>
		if(iframe != null){
			parent.selectedItemConfiguracao(idItem);
		}else{
			document.getElementById('iframeAtivos').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
			$("#POPUP_ATIVOS").dialog("open");
		}
	}
	
	$(function() {
		$("#POPUP_ATIVOS").dialog({
			autoOpen : false,
			width : 1005,
			height : 565,
			modal : true
		});
	});
	
	
	$(function() {
		$('.datepicker').datepicker();
	});
// 	popup para pesquisar de ip
	$(function() {
		$("#POPUP_ITEMCONFIG").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	});
	
	$(function() {
		$("#POPUP_ITEMCONFIGFILHO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		//document.formPesquisaFilho.btnTodosLOOKUP_ITENSCONFIGURACAORELACIONADOS.style.display = 'none';
		//document.formPesquisaFilho.btnLimparLOOKUP_ITENSCONFIGURACAORELACIONADOS.style.display = 'none';
	});
	
	$(function() {
		$("#addip").click(function() {
			$("#POPUP_ITEMCONFIG").dialog("open");
		});
	});
	
	$(function() {
		$("#addipFilho").click(function() {
			if (document.form.id.value == ''
				|| document.form.idItemConfiguracao.value == "") {
				alert(i18n_message("pesquisaIC.selecioneIdentificacao"));
			valor.checked = false;
			return false;
		}
			$("#POPUP_ITEMCONFIGFILHO").dialog("open");
			
		});
		
		$("#identificacaoFilho").click(function() {
			$("#POPUP_ITEMCONFIGFILHO").dialog("open");
		});
		
		$("#ip").click(function() {
			$("#POPUP_ITEMCONFIG").dialog("open");
		});
	});
	
	
	
//	popup para pesquisar de grupo
	$(function() {
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
	});
	
	function consultarGrupoItemConfiguracao(){
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("open");
	}
	
	
 	/**
	* @author rodrigo.oliveira
	*/
	function validaData(dataInicio, dataFim) {		
		var dtInicio = new Date();
		var dtFim = new Date();
		
		dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/") ) ).setFullYear;
		dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/") ) ).setFullYear;
		
		if (dtInicio > dtFim) {
			alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
			return false;
		} else
			return true;
	}
 	

	function divItemFilho(valor) {
		if (valor.checked) {
			if (document.form.id.value == ''
					|| document.form.idItemConfiguracao.value == "") {
				alert(i18n_message("pesquisaIC.selecioneIdentificacao"));
				valor.checked = false;
				return false;
			}

			document.getElementById('divItemFilho').style.display = 'block';
		} else {
			document.getElementById('divItemFilho').style.display = 'none';
		}
	}
</script>

<%
    //se for chamado por iframe deixa apenas a parte de cadastro da página
    if (iframe != null) {
%>
<style>
	div#main_container {
		margin: 10px 10px 10px 10px;
	}
	#wrapper {
    padding-bottom: 0 !important;
}
</style>

<%
    }
%>
</head>
<body>	
<div id="wrapper">
	<%
	    if (iframe == null) {
	%>
	<%@include file="/include/menu_vertical.jsp"%>
	<%
	    }
	%>
		<form name='form2' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServico/solicitacaoServico'>
			<input type="hidden" id="idItemConfiguracao" name="idItemConfiguracao">
		</form>
		<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
		<%
		    if (iframe == null) {
		%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%
		    }
		%>
			<div class="flat_area grid_16">
				<h2><i18n:message key="pesquisa.pesquisa" /></h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="citcorpore.comum.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div style="height: 400px; overflow-y: auto;" class="block">
						<div  class="">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao'>
								<div class="columns clearfixs">
									<div class="columns clearfix">
									<input type='hidden' name='idPesquisaItemConfiguracao' id='idPesquisaItemConfiguracao'/> 
									<input type='hidden' name='idItemConfiguracao' id='idItemConfiguracao'/> 
									<input type='hidden' name='idGrupoItemConfiguracao' id ="idGrupoItemConfiguracao" />
									<input type="hidden" name="idItemConfiguracaoFilho" id="idItemConfiguracaoFilho"/>
									
										<div class="col_60">
											<fieldset>
												<label><i18n:message key="itemConfiguracao.itemConfiguracao" /></label>
												<div>
													<input style="width: 90% !important;" type='text' id="ip" name="ip" readonly="readonly"
														maxlength="70" size="70" class="Valid[Required] Description[itemConfiguracao.itemConfiguracao]" />
													<img  id="addip"  style=" cursor: pointer; vertical-align: middle;"
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
												</div>
											</fieldset>
										</div>
									  	<% if (iframe != null) {%>							
											<div class="col_100">
												<div class="col_40">
													<fieldset>	
														<div style="height:50px" class="inline clearfix" >
															<label>
																<input type="checkbox" id="itemRelacionado" onclick="divItemFilho(this)" name="itemRelacionado" value="S" /><i18n:message key="pesquisa.pesquisarItemRelacionado" />
															</label> 
														</div>								
													
													</fieldset>
												</div> 
												<div id="divItemFilho" style="display: none;" class="col_60">
													<div>
														<label>
															<i18n:message key="pesquisa.identificacaoItemRelacionado" />
														</label>
														<input style="width: 90% !important;" type='text' id="identificacaoFilho" name="identificacaoFilho" readonly="readonly" 
															maxlength="70" size="70" class="Valid[Required] Description[itemConfiguracao.itemConfiguracao]" />
														<img id="addipFilho"  style=" cursor: pointer; vertical-align: middle;"
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
													</div>
												</div> 
											</div>
										<% } %>
										<div class="col_33">
											<fieldset>
												<label>
													<i18n:message key="pesquisa.grupo" />:
												</label>
												<div>
													<input onclick="consultarGrupoItemConfiguracao()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeGrupoItemConfiguracao" maxlength="70" size="70"  />
													<img onclick="consultarGrupoItemConfiguracao()" style=" vertical-align: middle;" 
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
												</div>
											</fieldset>
										</div>
										<div  class="col_33">
											<fieldset>
												<label>
													<i18n:message key="pesquisa.tipoExecucao" />
												</label>
												<div style="height:35px" class="inline clearfix">
													<label>
														<input type="checkbox" id="instalacao" name="instalacao" value="I" /><i18n:message key="pesquisa.instalacao" />
													</label> 
													<label>
														<input type="checkbox" id="desinstalacao" name=desinstalacao value="D" /><i18n:message key="pesquisa.desinstalacao" />
													</label>
													<label>
														<input type="checkbox" checked="checked" id="inventario" name=inventario value="inventario" /><i18n:message key="pesquisa.inventario" />
													</label>
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<label>
													<i18n:message key="pesquisa.datainicio" />
												</label>
												<div>
													<input id="dataInicio" type="text" name="dataInicio" maxlength="10" 
														class="Valid[Data] Description[pesquisa.datainicio] Format[Data] datepicker" />
												</div>
											</fieldset>
										</div>
										<div class="col_16">
											<fieldset>
												<label>
													<i18n:message key="pesquisa.datafim" />
												</label>
												<div>
													<input id="dataFim" type="text" name="dataFim" maxlength="10" 
														class="Valid[Data] Description[pesquisa.datafim] Format[Data] datepicker" />
												</div>
											</fieldset>
										</div>
									</div>
									
									<div class="col_100">
										<div  style="display: block; float: right; margin-top: 7px;">
											<button type="button" name="btnlimpar" class="light" onclick="document.form.clear();">
												<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png" />
												<span><i18n:message key="citcorpore.comum.limpar" /></span>
											</button>
										</div>									
										<div style="display: block; float: right; margin-top: 7px;">
											<button type="button" name="btnpesquisa" class="light" onclick="pesquisa();">
												<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
												<span><i18n:message key="citcorpore.comum.pesquisa" /></span>
											</button>
											<button type="button" name="btnverificarExpiracao" class="light" onclick="verificarExpiracao();">
												<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
												<span><i18n:message key="pesquisa.verificarExpiracao" /></span>
											</button>
										</div>
									</div>
									<div id="divPesquisaItemConfiguracao"></div>
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
<div id="POPUP_ITEMCONFIG" title="<i18n:message key="citcorpore.comum.identificacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisa' style="width: 540px">
						<cit:findField formName='formPesquisa'
 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0' 
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>


<div id="POPUP_ITEMCONFIGFILHO" title="<i18n:message key="citcorpore.comum.identificacao" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaFilho' style="width: 540px">
						<cit:findField formName='formPesquisaFilho'
 							lockupName='LOOKUP_ITENSCONFIGURACAORELACIONADOS' id='LOOKUP_ITENSCONFIGURACAORELACIONADOS' top='0' 
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>



<div id="POPUP_GRUPOITEMCONFIGURACAO" title="<i18n:message key="pesquisa.grupo" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaGrupo' style="width: 540px">
						<cit:findField formName='formPesquisaGrupo'
							lockupName='LOOKUP_GRUPOITEMCONFIGURACAO' id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_ATIVOS" title="<i18n:message key="pesquisa.listaAtivosDaMaquina" />" style="overflow: hidden;">
	<div class="box grid_16 tabs" >
		<div class="toggle_container" >
			<div id="tabs-2" class="block" style="overflow: hidden;">
				<div class="section" style="overflow: hidden;">
					<iframe id="iframeAtivos" style="display: block; margin-left: -20px;" name="iframeAtivos" width="970" height="480" >
					</iframe>
				</div>
			</div>
		</div>
	</div>
</div>
</html>