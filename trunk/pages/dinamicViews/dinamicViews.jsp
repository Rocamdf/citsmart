<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="java.util.HashMap"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.HtmlCodeVisaoDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.BotaoAcaoVisaoDTO"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoRelacionadaDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.util.MetaUtil"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoCamposNegocioDTO"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.GrupoVisaoDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.metainfo.bean.VisaoDTO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);

	String scriptToLOad = "";
	boolean temAbaFilha = false;

	VisaoDTO visaoDto = (VisaoDTO) request.getAttribute("visao");
	Collection colBotoes = (Collection) request.getAttribute("botoes");
	String titulo = "";
	String idVisao = "";
	if (visaoDto != null) {
		titulo = visaoDto.getDescricao();
		idVisao = "" + visaoDto.getIdVisao();
	} else {
		visaoDto = new VisaoDTO();
		visaoDto.setMapHtmlCodes(new HashMap());
		visaoDto.setMapScripts(new HashMap());
		visaoDto.setDescricao("");
		visaoDto.setTipoVisao(VisaoDTO.EDIT);
	}
	String strArrayNamesTablesVinc = "";
	String strArrayTablesVincMatriz = "";
	String strArrayControleRenderVinc = "";

	String scriptsIniciais = "";
	// modoExibicao: "N=Normal, J=Janela (para execuçao de fluxos)"
	String modoExibicao = (String) request.getParameter("modoExibicao");
	if (modoExibicao == null || modoExibicao.trim().length() == 0)
		modoExibicao = "N";

	if (modoExibicao.equals("J")) {
		request.setAttribute("menustyle", "SHORT");
	}

	String idFluxo = UtilStrings.nullToVazio((String) request
			.getParameter("idFluxo"));
	String idTarefa = UtilStrings.nullToVazio((String) request
			.getParameter("idTarefa"));
	String acaoFluxo = UtilStrings.nullToVazio((String) request
			.getParameter("acaoFluxo"));

	String descrvisao = visaoDto.getDescricao();
	descrvisao = UtilStrings.nullToVazio(descrvisao).trim();
	if (descrvisao.startsWith("$")){
		descrvisao = UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, descrvisao));
	}
%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title><%=descrvisao%></title>
<style type="text/css">
.tab_header ul, .tabs-wrap ul {
	height: auto !important;
	border: 0 !important;
}
</style>
<%@include file="/include/header.jsp" %>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.core.js"></script>
<script type="text/javascript"	src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.position.js"></script>										
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.autocomplete.js"></script>	
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/painel/jquery.ui.datepicker.js?nocache=<%=new java.util.Date().toString()%>"></script>	
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.datepicker-pt-BR.js"></script>	
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/lookup/jquery.ui.lookup.js"></script>

<link rel="stylesheet" type="text/css"	href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/include/js/barra-rolagem/perfect-scrollbar.css">
<script type="text/javascript"	src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/include/js/barra-rolagem/jquery.mousewheel.js"></script>
<script type="text/javascript"	src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/include/js/barra-rolagem/perfect-scrollbar.js"></script>
<script>
$(document).ready(function() {
	$('li.dropdown').click(function() {
		if ($(this).is('.open')) {
		$(this).removeClass('open');
		} else {
		$(this).addClass('open');
		}
	}); 
	
	/** Luiz.borges 04/12/2013 #126174 - Adicionada linha  Corrige a sobreposição da dinamicView sobre o Menu. **/
	$('.layout-panel-south').css('z-index', '1');
}); 
      jQuery(document).ready(function ($) {
        "use strict";
        $('.panel-body, .layout-body').perfectScrollbar();
      });
</script>

<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/themes/icon.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/css/jquery-easy.css">
<link rel="stylesheet" type="text/css"
	href="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.datepicker.css">		
<link rel="stylesheet" type="text/css" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/css/jquery-ui-1.8.21.custom.css" />			

<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/locale/easyui-lang-pt_BR.js"></script>
					
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/formparams/jquery.formparams.js"></script>					

<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.parser.js"></script>
					

<script>
	var objTab = null;
	var trObj = null;
	var NAME_REMOVE = 'Excluído';
	var tabsInf = new Array();
	var tabsVincLado = new Array();
	var limpo = true;
	
	var arrayNamesTablesVinc = null;
	var arrayTablesVincMatriz = null;
	var arrayControleRenderVinc = null;

	function prepareStringJSON(json_data_geral){
		var ret = json_data_geral.replace(/[\\]/g, '\\\\')
			    .replace(/[\"]/g, '\\\"')
			    .replace(/[\/]/g, '\\/')
			    .replace(/[\b]/g, '\\b')
			    .replace(/[\f]/g, '\\f')
			    .replace(/[\n]/g, '\\n')
			    .replace(/[\r]/g, '\\r')
			    .replace(/[\t]/g, '\\t');
		return ret;
	}
	function abreFechaMaisMenos(obj,idObj){
		var n = obj.src.indexOf('<%=Constantes.getValue("SERVER_ADDRESS")%><%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/mais.jpg');
		if (n > -1){
			document.getElementById(idObj).style.display='block';
			document.getElementById('img_' + idObj).src = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/menos.jpg';
		}else{
			document.getElementById(idObj).style.display='none';
			document.getElementById('img_' + idObj).src = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/mais.jpg';
		}
	}	
	function salvar(){
		var jsonAux = '';
		document.form.jsonMatriz.value = '';
		<%if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {%>
			$('#<%=VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoDto.getIdVisao()%>').datagrid('acceptChanges');
			$('#<%=VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoDto.getIdVisao()%>').datagrid('rejectChanges');		
			var rowsMatriz = $('#<%=VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA + visaoDto.getIdVisao()%>').datagrid('getRows');
			var dadosStrMatriz = '';
			var jsonAuxMatriz = '';
			for (var j = 0; j < rowsMatriz.length; j++){
				var json_data = JSON.stringify(rowsMatriz[j]);
				if (dadosStrMatriz != ''){
					dadosStrMatriz = dadosStrMatriz + ',';
				}
				dadosStrMatriz = dadosStrMatriz + json_data;					
			}
			if (dadosStrMatriz != ''){
				jsonAuxMatriz = jsonAuxMatriz + '{"MATRIZ": [' + dadosStrMatriz + ']}';
			}			
			document.form.jsonMatriz.value = jsonAuxMatriz;
		<%}%>
		document.form.dinamicViewsDadosAdicionais.value = '';			
		if (arrayNamesTablesVinc != null){
			for(var i = 1; i < arrayNamesTablesVinc.length; i++){
				$('#' + arrayNamesTablesVinc[i]).datagrid('acceptChanges');
				$('#' + arrayNamesTablesVinc[i]).datagrid('rejectChanges');
			}
			for(var i = 1; i < arrayNamesTablesVinc.length; i++){
				if (i == 1){
					jsonAux = jsonAux + '{';
				}
				var dadosStr = '';
				var rows = $('#' + arrayNamesTablesVinc[i]).datagrid('getRows');
				for (var j = 0; j < rows.length; j++){
					var json_data = JSON.stringify(rows[j]);
					if (dadosStr != ''){
						dadosStr = dadosStr + ',';
					}
					dadosStr = dadosStr + json_data;					
				}				
				if (dadosStr != ''){
					if (jsonAux != '' && jsonAux != '{'){
						jsonAux = jsonAux + ',';
					}				
					if (arrayNamesTablesVinc[i] != undefined){
						jsonAux = jsonAux + '"' + arrayNamesTablesVinc[i] + '": [' + dadosStr + ']';
					}
				}
			}
			if (jsonAux != ''){
				jsonAux = jsonAux + '}';
			}
		}
		document.form.dinamicViewsDadosAdicionais.value = jsonAux;
		var retValid = valid_scripts();
		if (retValid != undefined && !retValid){
			return;
		}
		if (!document.form.validate()){
			return;
		}
		JANELA_AGUARDE_MENU.show();
		document.form.save();
	}
	function excluir(){
		if (confirm('<%=UtilI18N.internacionaliza(request, "dinamicview.confirmaexclusao")%>')){
			/*
			 * Rodrigo Pecci Acorse - 29/11/2013 14h05 - #125019
			 * Exibe a janela de aguarde.
			 */
			JANELA_AGUARDE_MENU.show();
			
			document.form.fireEvent('delete');
		}
	}
	var acaoPesquisar = 'N';
	function TABLE_SEARCH_CLICK(idVisao, acao, obj, action){
		if (acaoPesquisar == 'N'){
			alert('<%=UtilI18N.internacionaliza(request, "dinamicview.naoehpossivelpesquisar")%>');
			return;
		}
		document.form.dinamicViewsIdVisaoPesquisaSelecionada.value = idVisao;
		document.form.dinamicViewsAcaoPesquisaSelecionada.value = acao;
		var json_data = JSON.stringify(obj);
		document.form.dinamicViewsJson_data.value = json_data;
		document.form.fireEvent('tableSearchClick');
	}
	function carregaVinculacoes(){
		try{
			if (arrayNamesTablesVinc != null){
				var jsonDataFormEdit = $('#' + document.form.name).formParams(false);
				for(var i = 0; i < arrayNamesTablesVinc.length; i++){
					if (arrayNamesTablesVinc[i] != ''){
						try{
							//alert(JSON.stringify(jsonDataFormEdit));						
							$('#' + arrayNamesTablesVinc[i]).datagrid('load',jsonDataFormEdit);
						}catch(e){alert(arrayNamesTablesVinc[i] + ':' + e.message);}
						limpo = false;
					}
				}
				try{
					$('#body').layout('expand', 'south');
				}catch(e){}
			}
			try{
				$('#body').layout();
			}catch(e){}
		}catch(e0){}
		callExternalClasses();	
	}
	function limpaVinculacoes(){
		try{
			$( '#tabsCompl' ).tabs('select', 0);
		}catch(e){}
		if (arrayNamesTablesVinc != null){
			for(var i = 0; i < arrayNamesTablesVinc.length; i++){
				if (arrayNamesTablesVinc[i] != ''){
					$('#' + arrayNamesTablesVinc[i]).datagrid('load',{});
				}
				arrayControleRenderVinc[i] = false;
			}
			arrayControleRenderVinc[0] = true;
			arrayControleRenderVinc[1] = true;
		}		
	}
	function limpar(){
		var idVisaoTmp = document.form.dinamicViewsIdVisao.value;
		var idFluxo = null;
		var idTarefa = null;
		var acaoFluxo = null;
		try{
			idFluxo = document.form.idFluxo.value;
			idTarefa = document.form.idTarefa.value;
			acaoFluxo = document.form.acaoFluxo.value;
		}catch(e){}
		var modoExibicao = document.form.modoExibicao.value;
		document.form.clear();	
		//Faz o tratamento de campos especificos que possuem metodo especifico de limpeza.
		try{
			for(var i = 0; i < document.form.length; i++){
				var elem = document.form.elements[i];
				if (elem.name == null) continue;
				try{
					eval(elem.name + '_limparField()');
				}catch(e){}
			}
		}catch(e){}
		document.form.dinamicViewsIdVisao.value = idVisaoTmp;
		document.form.idFluxo.value = idFluxo;
		document.form.idTarefa.value = idTarefa;
		document.form.acaoFluxo.value = acaoFluxo;
		document.form.modoExibicao.value = modoExibicao;
		limpo = true;
		limpaVinculacoes();
	}
	//A funcao abaixo pode ser utilizada em Scripts colocados na DinamicView (VisaoAdm)
	function executeTimeout(func,timeout){
		window.setTimeout(func,timeout);
	}
	function setDataTemp(key, data){
		document.form.dinamicViewsJson_tempData.value = data; 
		document.form.keyControl.value = key;
		document.form.fireEvent('setDadosTemporarios');
	}
	function retiraApostrofe(str){
		var myRegExp = new RegExp("'", "g");
		var myResult = str.replace(myRegExp, "-");
		return myResult;
	}
    function callExternalClasses(){
 		for(var i = 1; i < tabsInf.length; i++){
 			if (tabsInf[i] != ''){
 				enviaDados(tabsInf[i], 'tabInfs-' + i, document.form);
 			}
 		}    
    }	
    function enviaDados(urlParm, divParm, theForm){
		var dataToSend = $("#" + theForm.name).serialize();
		$.ajax({
		    url : urlParm,
		    type : 'post',
		    data : dataToSend,
		    dataType: 'html',
		    beforeSend: function(){
		    	try{
		    		document.getElementById(divParm).innerHTML = '<%=UtilI18N.internacionaliza(request, "citcorpore.comum.carregando")%>...';
		    	}catch(e){}
		    },
		    timeout: 3000,    
		    success: function(retorno){
		      $('#' + divParm).html(retorno);
		    },
		    error: function(erro){
		      $('#' + divParm).html(erro);
		    }       
		  });
    }    
	$.fn.datebox.defaults.formatter = function(date){
		var y = '' + date.getFullYear();
		var m = '' + (date.getMonth()+1);
		var d = '' + date.getDate();
		if (m.length < 2){
			m = '0' + m;
		}
		if (d.length < 2){
			d = '0' + d;
		}		
		return d+'/'+m+'/'+y;
	}
    $(document).ready(function () {
 		load_scripts();
 		try{
	 		$('#tabsCompl').tabs({
	 			onSelect: function(title,index){
	 				if (arrayControleRenderVinc != null){
						if (!arrayControleRenderVinc[index + 1]){ 				
			 				var jsonDataFormEdit = $('#' + document.form.name).formParams(false);
			 				try{
			 					$('#' + arrayNamesTablesVinc[index + 1]).datagrid('load',{});
			 				}catch(e){
			 				}
			 				if (!limpo){
				 				try{
				 					$('#' + arrayNamesTablesVinc[index + 1]).datagrid('load',jsonDataFormEdit);
				 				}catch(e){
				 				} 
			 				}
			 				arrayControleRenderVinc[index + 1] = true;
						}
	 				}
				}
	 		});
 		}catch(e){}
 		callExternalClasses();
    });	
</script>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="<%=UtilI18N.internacionaliza(request, "citcorpore.comum.aguarde")%>"
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body id='body' class="easyui-layout">

	<%if (!modoExibicao.equals("J")) {%>
	<div data-options="region:'north',split:false" style="height: 125px;" class="dinamic-menu">
		<%@include file="/include/menu_horizontal.jsp"%>
	</div>
	<%}%>
	<div data-options="region:'south',split:true" title="<%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.informacoescomplementares"))%>"
		style="height: 200px; padding: 10px; background: #efefef;">
		<%
			int i = 0;
			if (visaoDto.getColVisoesRelacionadas() != null && visaoDto.getColVisoesRelacionadas().size() > 0) {
				i = 1;
				for (Iterator it = visaoDto.getColVisoesRelacionadas().iterator(); it.hasNext();) {
					VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO) it.next();
					if (visaoRelacionadaDto.getTipoVinculacao() == null) {
						visaoRelacionadaDto.setTipoVinculacao("");
					}
					if (visaoRelacionadaDto.getTipoVinculacao().equalsIgnoreCase(VisaoRelacionadaDTO.VINC_ABA_FILHA)) {
		%>
		<script>tabsInf[<%=i%>] = '';</script>
		<%
			if (i == 1) {
		%>
		<div id="tabsCompl" class="easyui-tabs">
			<%
				}
			%>
			<%
			String tituloAba = visaoRelacionadaDto.getTitulo();
			tituloAba = UtilStrings.nullToVazio(tituloAba).trim();
			if (tituloAba.startsWith("$")){
				tituloAba = UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, tituloAba));
			}
			%>
			<div id="tabInfs-<%=i%>" data-options="tools:'#p-tools'"
				title="<%=tituloAba%>">
				<%
						temAbaFilha = true;
						if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EDIT)) {
								if (visaoRelacionadaDto.getAcaoEmSelecaoPesquisa().equalsIgnoreCase(VisaoRelacionadaDTO.ACAO_RECUPERAR_REGISTROS_VINCULADOS)) {
										MetaUtil.renderViewTableEditVinc_Easy(
												visaoRelacionadaDto.getVisaoFilhaDto(),
												out, visaoRelacionadaDto, request,
												response);

										if (!strArrayNamesTablesVinc.equalsIgnoreCase("")) {
											strArrayNamesTablesVinc += ",";
											strArrayTablesVincMatriz += ",";
											strArrayControleRenderVinc += ",";
										}
										strArrayNamesTablesVinc += "\""
												+ VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA
												+ visaoRelacionadaDto
														.getVisaoFilhaDto()
														.getIdVisao() + "\"";
										strArrayTablesVincMatriz += "false";
										strArrayControleRenderVinc += "false";
								} else {
										MetaUtil.renderViewEdit(
												visaoRelacionadaDto.getVisaoFilhaDto(),
												out, request, response);
								}
						} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
									String url = MetaUtil.renderExternaClass(visaoRelacionadaDto.getVisaoFilhaDto(), out);
									out.print("<script>tabsInf[" + i + "] = '" + url + "';</script>");
						} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
									MetaUtil.renderViewMatriz(visaoRelacionadaDto.getVisaoFilhaDto(), out, request, response, true);
									if (!strArrayNamesTablesVinc.equalsIgnoreCase("")) {
										strArrayNamesTablesVinc += ",";
										strArrayTablesVincMatriz += ",";
										strArrayControleRenderVinc += ",";
									}
									strArrayNamesTablesVinc += "\""
											+ VisaoRelacionadaDTO.PREFIXO_SISTEMA_TABELA_VINCULADA
											+ visaoRelacionadaDto
													.getVisaoFilhaDto()
													.getIdVisao() + "\"";		
									strArrayTablesVincMatriz += "true";
									strArrayControleRenderVinc += "false";
						} else {
									MetaUtil.renderViewTableSearch(visaoRelacionadaDto.getVisaoFilhaDto(), out, request);
						}
				%>
			</div>
			<%
						i++;
						}
					}
				}
				if (!temAbaFilha) {
					//SE NAO TEM ABA FILA, ENTAO ESCONDE!
					//scriptToLOad += "layout.hide('south');\n";
					scriptToLOad += "$('#body').layout('remove', 'south');\n";
				}
				if (i > 0) {
			%>
		</div>
		<%
			}
		%>
	</div>
	<div data-options="region:'east',iconCls:'icon-reload',split:true"
		title="<%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.botoesacao"))%>" style="width: 180px;">
		<%
			String htmlCodeInitButtons = (String) visaoDto.getMapHtmlCodes()
					.get(HtmlCodeVisaoDTO.HTMLCODE_INIT_BUTTONS.getName());
			if (htmlCodeInitButtons != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeInitButtons, request));
			}
		%>

		<%
			if (colBotoes == null) {
				colBotoes = new ArrayList();
			}
		%>
		<%
			for (Iterator it = colBotoes.iterator(); it.hasNext();) {
				BotaoAcaoVisaoDTO botaoAcaoVisaoDTO = (BotaoAcaoVisaoDTO) it.next();
				if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_SCRIPT)) {
					out.println("<script>");
					out.println("function script_button_" + botaoAcaoVisaoDTO.getIdBotaoAcaoVisao() + "(){");
					out.println(botaoAcaoVisaoDTO.getScript());
					out.println("}");
					out.println("</script>");
				}
				if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_GRAVAR)) {
		%>
		<button type='button' name='btnGravar' id='btnGravar' class="light"
			onclick='salvar();'>
			
			<span><%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.gravardados"))%></span>
		</button>
		<%
			}
				if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_EXCLUIR)) {
		%>
		<button type='button' id='btnExcluir' name='btnExcluir' class="light"
			onclick='excluir();'>
			
			<span><%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.excluirdados"))%></span>
		</button>
		<%
			}
				if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_LIMPAR)) {
		%>
		<button type='button' id='btnLimpar' name='btnLimpar' class="light"
			onclick='limpar();'>
			
			<span><%=UtilStrings.nullToVazio(UtilI18N.internacionaliza(request, "dinamicview.limpardados"))%></span>
		</button>
		<%
			}
				if (botaoAcaoVisaoDTO.getAcao().equalsIgnoreCase(BotaoAcaoVisaoDTO.ACAO_SCRIPT)) {
		%>
		<button type='button'
			name='btnScript<%=botaoAcaoVisaoDTO.getIdBotaoAcaoVisao()%>'
			class="light"
			onclick='<%="script_button_" + botaoAcaoVisaoDTO.getIdBotaoAcaoVisao() + "();"%>'>
			<span><%=UtilI18N.internacionalizaString(botaoAcaoVisaoDTO.getTexto(), request)%></span>
		</button>
		<%
			}
		%>
		<br>
		<%
			}
		%>
		<%
			if (modoExibicao.equals("J")) {
		%>
		<button type='button' name='btnCancelar' id="btnCancelar"
			class="light" onclick='cancelar();'>
			<img
				src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/bended_arrow_left.png">
			<span><%=UtilI18N.internacionaliza(request, "dinamicview.cancelar")%></span>
		</button>
		<%
			}
		%>

		<%
			String htmlCodeFinalButtons = (String) visaoDto.getMapHtmlCodes()
					.get(HtmlCodeVisaoDTO.HTMLCODE_END_BUTTONS.getName());
			if (htmlCodeFinalButtons != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeFinalButtons, request));
			}
		%>
	</div>

	<div data-options="region:'center'" title="<%=descrvisao%>">
		<%
			String htmlCodeInit = (String) visaoDto.getMapHtmlCodes().get(
					HtmlCodeVisaoDTO.HTMLCODE_INIT.getName());
			if (htmlCodeInit != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeInit, request));
			}
		%>
		<!-- Conteudo do Centro -->
		<div id="tabs" class="easyui-tabs" data-options="tools:'#tab-tools'">
			<div id="tabs-1" data-options="tools:'#p-tools'" style="width:100% !important;" title="<%=descrvisao%>">
				<!-- INICIO DO FORM PRINCIPAL -->
				<form name='form' id='form' onsubmit='javascript:return false;' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dinamicViews/dinamicViews'>
					<input type='hidden' name='dinamicViewsIdVisao'	value='<%=idVisao%>' /> 
					<input type='hidden' name='dinamicViewsIdVisaoPesquisaSelecionada' value='' /> 
					<input type='hidden' name='dinamicViewsAcaoPesquisaSelecionada' value='' />
					<input type='hidden' name='dinamicViewsJson_data' value='' /> 
					<input type='hidden' name='dinamicViewsJson_tempData' value='' />
					<input type='hidden' name='keyControl' value='' />
					<input type='hidden' name='dinamicViewsTablesVinc' value='' /> 
					<input type='hidden' name='dinamicViewsDadosAdicionais' value='' /> 
					<input type='hidden' name='modoExibicao' value='<%=modoExibicao%>' /> 
					<input type='hidden' name='idFluxo' value='<%=idFluxo%>' /> 
					<input type='hidden' name='idTarefa' value='<%=idTarefa%>' /> 
					<input type='hidden' name='acaoFluxo' value='<%=acaoFluxo%>' />
					<input type='hidden' name='JsonData' value='TRUE' /> 
					<input type='hidden' name='jsonMatriz' value='' /> 
					<%
						String htmlCodeInitForm = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_INIT_FORM.getName());
						if (htmlCodeInitForm != null) {
							out.println(UtilI18N.internacionalizaString(htmlCodeInitForm, request));
						}
					%>
					<div id='divMainContent' class="columns clearfix">
						<%
							if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.EDIT)) {
								MetaUtil.renderViewEdit(visaoDto, out, request, response);
							} else if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
								String url = MetaUtil.renderExternaClass(visaoDto, out);
								scriptsIniciais = "enviaDados('" + url	+ "', 'divMainContent', document.form);";
							} else if (visaoDto.getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
								MetaUtil.renderViewMatriz(visaoDto, out, request, response, false);								
							} else {
								MetaUtil.renderViewTableSearch(visaoDto, out, request);
							}
						%>
					</div>
					<%
						String htmlCodeFinalForm = (String) visaoDto.getMapHtmlCodes().get(
								HtmlCodeVisaoDTO.HTMLCODE_END_FORM.getName());
						if (htmlCodeFinalForm != null) {
							out.println(UtilI18N.internacionalizaString(htmlCodeFinalForm, request));
						}
					%>
					<br> <br>
				</form>
				<!-- FIM DO FORM PRINCIPAL -->
			</div>
			<%
				if (visaoDto.getColVisoesRelacionadas() != null) {
					i = 2;
					for (Iterator it = visaoDto.getColVisoesRelacionadas().iterator(); it.hasNext();) {
						VisaoRelacionadaDTO visaoRelacionadaDto = (VisaoRelacionadaDTO) it.next();
						if (visaoRelacionadaDto.getTipoVinculacao() == null) {
							visaoRelacionadaDto.setTipoVinculacao("");
						}
						if (visaoRelacionadaDto.getTipoVinculacao().equalsIgnoreCase(VisaoRelacionadaDTO.VINC_ABA_AO_LADO)) {
			%>
			<div id="tabs-<%=i%>" data-options="tools:'#p-tools'" title="<%=UtilI18N.internacionalizaString(visaoRelacionadaDto.getTitulo(),request)%>">
				<div id="tabsLado-<%=i%>">
					<%
						if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EDIT)) {
								MetaUtil.renderViewEdit(visaoRelacionadaDto.getVisaoFilhaDto(), out, request, response);
						} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.EXTERNALCLASS)) {
								String url = MetaUtil.renderExternaClass(visaoRelacionadaDto.getVisaoFilhaDto(), out);
								out.print("<script>tabsVincLado[" + i + "] = '"	+ url + "';</script>");
						} else if (visaoRelacionadaDto.getVisaoFilhaDto().getTipoVisao().equalsIgnoreCase(VisaoDTO.MATRIZ)) {
								MetaUtil.renderViewMatriz(visaoRelacionadaDto.getVisaoFilhaDto(), out, request, response, false);
						} else {
								MetaUtil.renderViewTableSearch(visaoRelacionadaDto.getVisaoFilhaDto(), out, request);
						}
						i++;
					%>
				</div>
			</div>
			<%
				}
					}
				}
			%>
			<!-- ## FIM - AREA DA APLICACAO ## -->
		</div>

		<!-- Fim Conteudo do Centro -->

		<%
			String htmlCodeFinal = (String) visaoDto.getMapHtmlCodes().get(HtmlCodeVisaoDTO.HTMLCODE_END.getName());
			if (htmlCodeFinal != null) {
				out.println(UtilI18N.internacionalizaString(htmlCodeFinal, request));
			}
		%>
	</div>
	
	<script>
	<%if (strArrayNamesTablesVinc != null && !strArrayNamesTablesVinc.equalsIgnoreCase("")) {%>
		arrayNamesTablesVinc = new Array('', <%=strArrayNamesTablesVinc%>);
		arrayTablesVincMatriz = new Array(false, <%=strArrayTablesVincMatriz%>);
		arrayControleRenderVinc = new Array(false, <%=strArrayControleRenderVinc%>);
	<%}%>
	document.form.dinamicViewsTablesVinc.value = '<%=strArrayNamesTablesVinc%>';
	</script>	
	
	<script>
	function cancelar() {
		try{
			parent.fecharVisao();
		}catch(e){
		}
	}
	function fecharSePOPUP() {
		try{
			parent.fecharVisao();
		}catch(e){
		}
	}	
	function load_scripts(){
		try{
	  	 	<%=scriptsIniciais%>
			<%String strScript = (String) visaoDto.getMapScripts().get(
			ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#"
					+ ScriptsVisaoDTO.SCRIPT_LOAD.getName());
			if (strScript != null) {
				//NAO RETIRE ISTO!!!!				
				//remove coisas velhas da dinamic view anterior que estava no fonte do BD. Mantem compatibilidade.				
				strScript = strScript.replaceAll("layout\\.sizePane\\(\\\"west\\\"\\, 5\\);", "");
				strScript = strScript.replaceAll("layout\\.close\\(\\\"west\\\"\\);", "");
				strScript = strScript.replaceAll("layout\\.toggle\\(\\\"south\\\"\\);", "");
				out.println(UtilI18N.internacionalizaString(strScript, request));
		}%>
		}catch(e){}
		<%=scriptToLOad%>
	}   
    function restore_scripts(){
    	<%strScript = (String) visaoDto.getMapScripts().get(
					ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#"
							+ ScriptsVisaoDTO.SCRIPT_ONRESTORE.getName());
			if (strScript != null) {
				out.println(UtilI18N.internacionalizaString(strScript, request));
			}%>
    } 
    function valid_scripts(){
    	<%strScript = (String) visaoDto.getMapScripts().get(
					ScriptsVisaoDTO.SCRIPT_EXECUTE_CLIENT + "#"
							+ ScriptsVisaoDTO.SCRIPT_VALIDADE.getName());
		if (strScript != null && !strScript.trim().equalsIgnoreCase("")) {
			out.println(UtilI18N.internacionalizaString(strScript, request));
		}else{
			out.println("return true;");
		}
		%>
    }    

	addEvent(window, "load", load, false);
	function load(){
		document.form.afterLoad = function () {
			if ('<%=idFluxo%>' != '' && '<%=idTarefa%>' != '') {
				document.form.fireEvent("recuperaVisaoFluxo");
			}		
		}
	}
	
	</script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/dwr/engine.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/dwr/util.js"></script>
	<script>
		/*
		* Motivo: Corrigindo erros de scripts
		* Autor: flavio.santana
		* Data/Hora: 04/11/2013 16:19
		*/
		function resize_iframe(){}
		
		if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
			 document.documentElement.className += " " + "ie10";
		}
	</script>
		 	
	<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/bootstrap/js/bootstrap.min.js"></script>

	<%@include file="/pages/ctrlAsterisk/ctrlAsterisk.jsp" %>
</body>

</html>