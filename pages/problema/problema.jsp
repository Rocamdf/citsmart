<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	String id = request.getParameter("idBaseConhecimento");
	
	// Limpando o cache do navegador.
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache", "no-cache");
	response.setHeader("Cache-Control", "no-cache, must-revalidate");	
	response.setDateHeader("Expires", -1);
	
	 

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/security/security.jsp" %>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@page import="br.com.centralit.bpm.util.Enumerados"%>
    <%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

    <script>var URL_INITIAL = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/';</script>
    <%
    String tarefaAssociada = (String)request.getAttribute("tarefaAssociada");
    if (tarefaAssociada == null){
    	tarefaAssociada = "N";
    }
    %>
    <title>CITSMart</title>
	<%@include file="/include/noCache/noCache.jsp" %>
	
    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/layout-default-latest.css"/>

    
    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.all.css"/>
                    


	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/themeroller/Aristo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jqueryTreeview/jquery.treeview.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jQueryGantt/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/slick.grid.css"/>	
	<link type="text/css" rel="stylesheet" class="include" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/jquery.jqplot.min.css" />
	
    <style title="" type="text/css">
    
    select:focus {
	    border-color: rgba(82, 168, 236, 0.8) !important;
	    outline: 0px none !important;
	    box-shadow: 0px 1px 1px rgba(0, 0, 0, 0.075) inset, 0px 0px 8px rgba(82, 168, 236, 0.6) !important;
	}

    .ui-layout-center ,
    .ui-layout-east ,
    .ui-layout-east .ui-layout-content {
        padding:        0;
        overflow:       hidden;
    }
    .hidden {
        display:        none;
    }
    h4 {
        margin:0 0 0 0;
    }
    .cell-title {      
        font-weight: bold;    
    }    
    .cell-effort-driven {      
        text-align: center;    
    } 
    
	.toggler-west-closed		{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-off.gif) no-repeat center; }
	.toggler-west-closed:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-on.gif)  no-repeat center; }
	.toggler-east-closed		{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-off.gif) no-repeat center; }
	.toggler-east-closed:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-on.gif)  no-repeat center; }    
	
	span.button-close-west			{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-off.gif) no-repeat center; }
	span.button-close-west:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-on.gif)  no-repeat center; }
	span.button-close-east			{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-off.gif) no-repeat center; }
	span.button-close-east:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-on.gif)  no-repeat center; }

	/* .table {
		border-left:1px solid #ddd;
		width: 100%;
		text-align: center;
	}
	
	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}
	
	.table td {
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
		height: 10px;
	} */
	
	.col_98 {
		
			width: 98%;
			
		}
	
	.tabFormulario tr{
		width: 50%;
	}
	
	.tabFormulario td{
		width: 70%;
	}

	.tabFormulario th{
		width: 30%;
	}
	
	.form{
		width: 100%;
		float: right;
	}
	
	.formHead{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;
	}	
	
	.formBody{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;	
	}
	
	.formRelacionamentos div{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;
	}
	
	.formFooter{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;	
	}
	
	.divEsquerda{
		float: left; 
		width: 47%; 
		border: 1px solid #ccc; 
		padding: 5px;	
	}
	
	.divDireita{
		float: right;
		width: 47%; 
		border: 1px solid #ccc;
		padding: 5px;
	}
	
	.ui-tabs .ui-tabs-nav li a{
		background-color: #fff !important;
	}	
		
	.ui-state-active{
		background-color: #aaa ;
	}		
	
	#tabs div{
		/* background-color: #fff; */
	}

	
	.ui-state-hover{
		background-color: #ccc !important;	
	}
	
	#divTituloSolicitacao {
		
			text-align: center;
			
	}
		
	fieldset 
	{
    	line-height: 1;
    	margin-top: 0;
    	margin-bottom: 0;
    	margin-right: 0;
    	margin-left: 0;
    	background: none repeat scroll 0 0 transparent;
    	outline: 0 none;
    	padding: 0;
    	vertical-align: baseline;	
    	letter-spacing: 0;
    	border-right: none;
    	border-left: none;
    		
	}
	/* Desenvolvedor: Pedro Lino - Data: 25/10/2013 - Horário: 16:58 - ID Citsmart: 120948 - 
	* Motivo/Comentário: box(botão) base de conhecimento pequeno/ Alterado altura do box */
	#barraFerramentasProblemas ul li.li_menu {
		height: 65px!important;
	}
	

    </style>

    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-ui-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/debug.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/uniform/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/autogrow/jquery.autogrowtextarea.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/multiselect/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/selectbox/jquery.selectBox.min.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/timepicker/jquery.timepicker.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/colorpicker/js/colorpicker.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/validation/jquery.validate.min.js"></script>		
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/uitotop/js/jquery.ui.totop.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/custom/ui.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/custom/forms.js"></script>	
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<script class="include" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/jquery.jqplot.min.js"></script>
	<script class="include" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>	
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
	
    <!-- SlickGrid and its dependancies (not sure what they're for?) -->
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.rule-1.0.1.1-min.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.event.drag.custom.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.core.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.editors.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.grid.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CITTable.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.maskedinput.js"></script>
    
    <script type="text/javascript">
		
	    $.fx.speeds._default = 1000;
		var myLayout;
		var popupManager;
		var popupManagerSolicitacaoServico;
		var LOOKUP_EMPREGADO_select;
		var atualizarListaRegistros;

		var slickGridColunas;
		var slickGridOptions;
		var slickGridTabela;

		var tabelaRelacionamentoICs;
		var tabelaRelacionamentoSolicitacaoServico;
		
		var popup2;
		 
		$(document).ready(function () {
	    		//initTextEditors();
	    		initPopups();
	    		initTabelasRelacionamentos();

	    		//para visualização rápida do mapaDesenhoServico
	    		popupManager = new PopupManager("98%" , $(window).height()-100, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	    		popupManagerSolicitacaoServico = new PopupManager("98%" , $(window).height()-100, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	    		popupManagerRequisicaoMudanca = new PopupManager("98%" , $(window).height()-100, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	    		popup2 = new PopupManager("98%", 520 , "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	    		popupCategoriaProblema = new PopupManager("98%", $(window).height()-100, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	 
	    		$( "#tabs" ).tabs();

// 	    		$('.datepicker').mask('99/99/9999');
	    		
	    		$("#POPUP_SOLICITANTE").dialog({
					autoOpen : false,
					width : 600,
					height : 550,
					modal : true
					
				});
	    		
	    		$("#POPUP_INFO_INSERCAO").dialog({
	    			autoOpen : false,
	    			width : 400,
	    			height : 280,
	    			modal : true,
	    			close: function(event, ui) {
	    				fechar();
	    			}
	    		});	
	    		
	    		/*  geber.costa
	    			Inserido no começo para ocultar a aba de Revisão, por padrão a prioridade é 5, e a aba de Revisão de problema grave deve ficar inativa ,
	    			somente mudará quando a prioridade for 1 ou 2.
	    		*/
	  
	    		atualizaPrioridade();
	    		
	    });	    

		/*
		 * Funções de inicialização
		 */
		function initTabelasRelacionamentos(){
			//ICs
			tabelaRelacionamentoICs = new CITTable("tblICs",["idItemConfiguracao", "nomeItemConfiguracao","descricaoProblema"],[]);
			tabelaRelacionamentoICs.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			//Incidentes
			tabelaRelacionamentoSolicitacaoServico = new CITTable("tblSolicitacaoServico",["idSolicitacaoServico", "nomeServico"],[]);
			tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			//Mudancas
			tabelaRelacionamentoMudancas = new CITTable("tblRDM",["idRequisicaoMudanca", "titulo", "status"],[]);
			tabelaRelacionamentoMudancas.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
		}
		
	    function initTextEditor(editor){
	    	editor.BasePath = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
	      	editor.Config['ToolbarStartExpanded'] = false ;
	      	editor.Width = '100%' ;
	      	editor.ReplaceTextarea() ;
	    }

// 	    function initTextEditors(){
// 			var textAreaList = document.getElementsByTagName("textarea"); 
// 			if(textAreaList != null){
// 				for(var i = 0; i < textAreaList.length; i++){
// 					if(textAreaList[i].id != null){
// 						initTextEditor(new FCKeditor(textAreaList[i].id));				
// 					}
// 				}
// 			}
// 		}

	    /**
	     * Popups à partir de lookups devem ter suas classes setadas como POPUP_LOOKUP.
	     * Assim, serão todas inicializadas aqui.
	     */
		function initPopups(){
			$(".POPUP_LOOKUP").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$(".popup").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});			
		}
	    
		/*
		 * Funções de Solicitacao Servico
		 */	
		 
		 
		$(function() {
			$("#POPUP_SOLICITACAOSERVICO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#addSolicitacaoServico").click(function() {
				$("#POPUP_SOLICITACAOSERVICO").dialog("open");
			});
			
			$("#addImgSolicitacaoServico").click(function() {
				$("#POPUP_SOLICITACAOSERVICO").dialog("open");
			});

			$("#POPUP_PESQUISAITEMCONFIGURACAO").dialog({
				autoOpen : false,
				width : 1200,
				height : 650,
				modal : true
			});
			
		});
		
		function fecharProblema(){
			$("#POPUP_SOLICITACAOSERVICO").dialog("close");
		}
		
		function LOOKUP_SOLICITACAOSERVICO_select(id, desc){
			
			addLinhaTabelaSolicitacaoServico(id, desc, true);
			
		}
		
		function addLinhaTabelaSolicitacaoServico(id, desc, valida){
			var tbl = document.getElementById('tblSolicitacaoServico');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaSolicitacaoServico(lastRow, id)){
					return;
				}
			}
			
			tabelaRelacionamentoSolicitacaoServico.addObject([id, desc]);
			
			document.form.solicitacaoServicoSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoSolicitacaoServico.getTableObjects());
			
			$("#POPUP_SOLICITACAOSERVICO").dialog("close");
			
		}
		
		function validaAddLinhaTabelaSolicitacaoServico(lastRow, id){
			var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaSolicitacaoServico.length; i++){
					if (listaSolicitacaoServico[i].idSolicitacaoServico == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;
		}
		
		/*
		 * Funções de apoio
		 */				 	
		 
		function mostrarFormulario(){;
			limpar(document.form);
		}

		/**
		 * Textarea com FCKEditores devem ter ID.
		 */
		function limparFCKEditores(){
			var fckEditorAux = null;
			var textAreaList = document.getElementsByTagName("textarea");

			for(var i = 0; i < textAreaList.length; i++){
				if(textAreaList[i].id != null){
					
					fckEditorAux = FCKeditorAPI.GetInstance( textAreaList[i].id );
					
					if(fckEditorAux != null){
						try{
							fckEditorAux.SetData("");
						}catch(e){
						}
					} 					
				}	
			}	
		}


// 		/**
// 		 * Todos os selects com a classe "influenciaPrioridade" devem ser
// 		 * ordenados crescentemente de acordo com seu peso sobre a prioridade.
// 		 * Exemplo: baixo - médio - alto
// 		 *    peso:   0      1       2
// 		 */
// 		function atualizaPrioridade(){
// 			var selects = document.getElementsByClassName("influenciaPrioridade");
// 			var select = null;
// 			var valorPrioridade = 0;
// 			var totalItens = 0;
			
// 			for(var i = 0; i < selects.length; i++){
// 				select = selects[i];

// 				if(select == null){
// 					continue;
// 				}

// 				valorPrioridade += select.selectedIndex;
// 				totalItens += select.length - 1;			
// 			}

// 			//média de peso de acordo com a quantidade de itens influentes na prioridade
// 			document.form.prioridade.value = parseInt(valorPrioridade * (5 / totalItens));
// 		}

		
		function atualizaPrioridade(){
			
			var impacto = document.getElementById('impacto').value;
			var urgencia = document.getElementById('urgencia').value;
			
			/*
			geber.costa
			a aba de Revisão de Problema Grave depende da prioridade
			, sempre que muda a prioridade irá mudar ou não a disponibilidade dos campos da aba
			*/
			
			if (urgencia == "B"){
				if (impacto == "B"){
					document.form.prioridade.value = 5;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'none'
				}else if (impacto == "M"){
					document.form.prioridade.value = 4;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'none'
				}else if (impacto == "A"){
					document.form.prioridade.value = 3;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'none'
				}
			}	
			
			if (urgencia == "M"){
				if (impacto == "B"){
					document.form.prioridade.value = 4;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'none'
				}else if (impacto == "M"){
					document.form.prioridade.value = 3;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'none'
				}else if (impacto == "A"){
					document.form.prioridade.value = 2;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'block';
				}
			}
			
			if (urgencia == "A"){
				if (impacto == "B"){
					document.form.prioridade.value = 3;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'none'
				}else if (impacto == "M"){

					document.form.prioridade.value = 2;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'block';
				}else if (impacto == "A"){
					document.form.prioridade.value = 1;
					//document.getElementById('relacionarRevisaoProblemaGrave').style.display = 'block';
					
				}
			}
		}
		
		/*
		 * Funções auxílio CRUD
		 */
		
		function limpar(form){
			try{
				form.clear();
			}catch(e){}
			//limparFCKEditores();
		}

		function validarDatas(){
			var inputs = document.getElementsByClassName("datepicker");
			var input = null;
			var errorMsg = i18n_message("citcorpore.comum.nenhumaDataDeveSerInferiorHoje");
			
			for(var i = 0; i < inputs.length; i++){
				input = inputs[i];

				if(input == null){
					continue;
				}
				
				if(comparaComDataAtual(input) < 0){
					alert(errorMsg);
					input.focus();
					throw errorMsg;
				}				
			}
		}
		
		function fecharMudanca(){
			popupManagerRequisicaoMudanca.fecharPopup();
		}
		
		function gravar(form){
		
			document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());
			document.form.solicitacaoServicoSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoSolicitacaoServico.getTableObjects());
			document.form.requisicaoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoMudancas.getTableObjects());
			<%if(id !=null){ %>
			document.form.idBaseConhecimento.value = <%=id%>;
			<% } %>
			
			if (document.form.idContrato.value == '' || document.form.idContrato.value == ' ' || document.form.idContrato.value == undefined || document.form.idContrato.value == null){
				alert('<i18n:message key="problema.contrato" />: <i18n:message key="citcorpore.comum.campo_obrigatorio" />');
				return;
			}
			
			var informacoesComplementares_serialize = '';
			try{
				informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
			}catch(e){}
			
			
			document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
			
			
			validarDatas();
			document.form.save();	
		}
		
		function validafkeditor(){
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
			document.form.descricao.value = oEditor.GetXHTML();
			document.form.descricao.innerHTML = oEditor.GetXHTML();
			
			if(document.form.descricao.innerHTML == "<br />" || document.form.descricao.innerHTML == "&lt;br /&gt;"){
				alert(i18n_message("problema.informe_descricao"));
				return false;
			}
			
			if (document.form.descricao.value == '' || document.form.descricao.value == '&nbsp;'
				|| document.form.descricao.value == '<p></p>'){
				alert(i18n_message("problema.informe_descricao"));
				return false;
			}
			
			return true;
		}
		
		
		
		function restaurar(){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
			var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);
			var listaRequisicaoMudanca = ObjectUtils.deserializeCollectionFromString(document.form.requisicaoMudancaSerializado.value);
			limpaListasRelacionamentos();

			if(listaICs.length > 0){
				for(var i = 0; i < listaICs.length; i++){
					tabelaRelacionamentoICs.addObject([
					                   				   listaICs[i].idItemConfiguracao, 
					                   				   listaICs[i].nomeItemConfiguracao,
					                   				   listaICs[i].descricaoProblema,
					                   				   getBotaoMostrarServicosRelacionados(listaICs[i].idItemConfiguracao)
					                   				  ]);
	 				  
					   
				}
			}
			if(listaSolicitacaoServico.length > 0){
				for(var i = 0; i < listaSolicitacaoServico.length; i++){
					tabelaRelacionamentoSolicitacaoServico.addObject([
					                   				    listaSolicitacaoServico[i].idSolicitacaoServico, 
						                   				listaSolicitacaoServico[i].nomeServico,
					                   				  ]);
	 				  
					   
				}
			}
			
			if(listaRequisicaoMudanca.length > 0){
				for(var i = 0; i < listaRequisicaoMudanca.length; i++){
					tabelaRelacionamentoMudancas.addObject([
					                   				    listaRequisicaoMudanca[i].idRequisicaoMudanca, 
					                   				 	listaRequisicaoMudanca[i].titulo,
					                   				 	listaRequisicaoMudanca[i].status,
					                   				  ]);
	 				  
					   
				}
			}
			/*
			geber.costa

			Na hora de restaurar o problema será verificado a prioridade 
			para validar a aba de Revisão de problema de Grave
			*/
			atualizaPrioridade();
			
			
		}

		function getBotaoMostrarServicosRelacionados(idItemConfiguracao){	
			var botao = new Image();

			botao.src = '<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png';
			botao.setAttribute("style", "cursor: pointer;");
			botao.id = idItemConfiguracao;
			botao.addEventListener("click", function(evt){
				$("#popupServicosRelacionados").dialog("open");
				//fireevent
			}, true);

			return botao;
		}	     

		function mostrarServicosRelacionados(idItemConfiguracao){
			alert(idItemConfiguracao);
		}
		
		function deletar(){
			if(confirm(i18n_message("problema.deseja_deletar"))){
				document.form.fireEvent("delete");
			}
		}

		/**
		 * Ajusta dados dos textareas com fckeditor ao restaurar.
		 */
		function restauraFckEditores(){
			var textAreaList = document.getElementsByTagName("textarea");

			for(var i = 0; i < textAreaList.length; i++){
				if(textAreaList[i].id != null){
					
					fckEditorAux = FCKeditorAPI.GetInstance( textAreaList[i].id );
					
					if(fckEditorAux != null){
						try{
							fckEditorAux.SetData(document.getElementById( textAreaList[i].id ).value);
						}catch(e){}
					} 					
				}	
			}
		}

	 	/*
	 	 * Reaproveitamento da lookup EMPREGADO
	 	 */	 	 

		function selecionarCriador(){
			limpar_LOOKUP_EMPREGADO();
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idCriador.value = id;
				document.form.nomeCriador.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}
					
			$("#POPUP_EMPREGADO").dialog("open");
		}	

		function selecionarProprietario(){
			limpar_LOOKUP_EMPREGADO();
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idProprietario.value = id;
				document.form.nomeProprietario.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}
			
			$("#POPUP_EMPREGADO").dialog("open");			
		}

		
		function restaurarRegistro(idRegistro){
			mostrarFormulario();
			//document.form.idRequisicaoMudanca.value = idRegistro;
			document.form.fireEvent("restore");
		}

		/*
		 * Funções de relacionamento
		 */

		//adicionar ics
		function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
			
			addLinhaTabelaItemConfiguracao(id, desc, true);
			
		}
		
		function addLinhaTabelaItemConfiguracao(id, desc, valida){
			var tbl = document.getElementById('tblICs');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaItemConfiguracao(lastRow, id)){
					return;
				}
			}
			
			tabelaRelacionamentoICs.addObject([id, desc, prompt(i18n_message("problema.descrevaResumidamenteProblema"),""), getBotaoMostrarServicosRelacionados(id)]);
			
			document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());
			
			$("#POPUPITEMCONFIGURACAO").dialog("close");
			
		}
		
		function validaAddLinhaTabelaItemConfiguracao(lastRow, id){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaICs.length; i++){
					if (listaICs[i].idItemConfiguracao == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;
		}

		function selecionaIcPeloMapa(id, desc){
			
			tabelaRelacionamentoICs.addObject([id, desc, prompt(i18n_message("problema.descrevaResumidamenteProblema"),""), getBotaoMostrarServicosRelacionados(id)]);
			
			$("#popupCadastroRapido").dialog("close");
		}
		
		
		function LOOKUP_MUDANCA_select(id, desc) {
			
			addLinhaTabelaMudanca(id, desc, true);
			
		}
		
		
		function addLinhaTabelaMudanca(id, desc, valida){
			var tbl = document.getElementById('tblRDM');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaMudanca(lastRow, id)){
					return;
				}
			}
			
			var camposLookupItem = desc.split("-");
			tabelaRelacionamentoMudancas.addObject([id, camposLookupItem[1], camposLookupItem[2]]);
			
			document.form.requisicaoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoMudancas.getTableObjects());
			
			$("#POPUP_REQUISICAOMUDANCA").dialog("close");
			
		}
		
		function validaAddLinhaTabelaMudanca(lastRow, id){
			var listaRequisicaoMudanca = ObjectUtils.deserializeCollectionFromString(document.form.requisicaoMudancaSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaRequisicaoMudanca.length; i++){
					if (listaRequisicaoMudanca[i].idRequisicaoMudanca == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;
		}
		
		function abrePopupIcs(){
			$("#POPUPITEMCONFIGURACAO").dialog("open");
		}
		
		function adicionarIC(){			
			abrePopupIcs();
		}
		
		function pesquisarRequisicaoMudanca(){			
			abrePopupRequisicaoMudanca();
		}
		
		function abrePopupRequisicaoMudanca(){
			$("#POPUP_REQUISICAOMUDANCA").dialog("open");
		}

		function alternaVisibilidadePropsBase(){
			var check = document.getElementById("adicionarBDCE");
			var propsBaseConhecimento = null;
			
			if(check.checked){
				propsBaseConhecimento = document.getElementById("propsBaseConhecimento");
				$("#propsBaseConhecimento").show("clip");
			} else {
				$("#propsBaseConhecimento").hide("clip");
			}
		}
		
		fechar = function(){
			parent.fecharProblema();
		}
		
		function getServicos(){
			document.form.fireEvent('carregaServicosMulti');
		}
		
		 $(function() {
				$("#addSolicitante").click(function() {
					if (document.form.idContrato.value == ''){
						alert('Informe o contrato!');
						document.form.idContrato.focus();
						return;
					}
					/* var v = document.getElementsByName("btnTodosLOOKUP_SOL_CONTRATO");
					v[0].style.display = 'none'; */
					var y = document.getElementsByName("btnLimparLOOKUP_SOL_CONTRATO");
					y[0].style.display = 'none'; 	
					$("#POPUP_SOLICITANTE").dialog("open");
				});
			});
		 
		 function setaValorLookup(obj){
			document.form.idSolicitante.value = '';
			document.form.solicitante.value = '';
			document.form.emailContato.value = '';
	 		document.form.nomeContato.value = '';
			document.form.telefoneContato.value = '';
			document.form.observacao.value = '';
			document.form.ramal.value = '';
			document.getElementById('idLocalidade').options.length = 0;
			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = '';
			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = obj.value; 
// 			document.form.fireEvent('preencherComboLocalidade');
//	 		document.form.servicoBusca.value = '';
//	 		document.getElementById('tipo').options[0].selected = 'selected';
		}
		 
		function LOOKUP_SOL_CONTRATO_select(id, desc){
				document.form.idSolicitante.value = id;
				document.form.fireEvent("restoreColaboradorSolicitante");
		}
		
		function selecionarSolicitante(){
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idSolicitante.value = id;
				document.form.solicitante.value = desc.split("-")[0];
				$("#POPUP_EMPREGADO").dialog("close");
			}
						
			$("#POPUP_EMPREGADO").dialog("open");
		}	
		 


		function mostrarEscondeRegExec(){
			if (document.getElementById('divMostraRegistroExecucao').style.display == 'none'){
				document.getElementById('divMostraRegistroExecucao').style.display = 'block';
				document.getElementById('lblMsgregistroexecucao').style.display = 'block';
				document.getElementById('btnAddRegExec').innerHTML = '<i18n:message key="solicitacaoServico.addregistroexecucao_menos" />';
			}else{
				document.getElementById('divMostraRegistroExecucao').style.display = 'none';
				document.getElementById('lblMsgregistroexecucao').style.display = 'none';
				document.getElementById('btnAddRegExec').innerHTML = '<i18n:message key="solicitacaoServico.addregistroexecucao_mais" />';
			}
		}
		
		
		function gravarErroConhecido(){
			var idProblema = document.form.idProblema.value;
			document.getElementById('iframeBaseConhecimento').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimento/baseConhecimento.load?iframe=true&erroConhecido=S&idProblema="+idProblema;
			$("#POPUP_BASECONHECIMENTO").dialog("open");
		}
		$(function() {
			$("#POPUP_BASECONHECIMENTO").dialog({
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true
			});
		});
		
		function resize_iframe(){
			var height=window.innerWidth;//Firefox
			if (document.body.clientHeight)
			{
				height=document.body.clientHeight;//IE
			}
			document.getElementById("iframeBaseConhecimento").style.height=parseInt(height - document.getElementById("iframeBaseConhecimento").offsetTop-8)+"px";
		}
		
		function fecharBaseConhecimento(){
			$("#POPUP_BASECONHECIMENTO").dialog("close");
			document.form.fireEvent("atualizaGridProblema");
		}
		
		exibeIconesEditarBaseConhecimento = function(row, obj){
			var id = obj.idBaseConhecimento;
	        obj.sequenciaOS = row.rowIndex; 
	        row.cells[0].innerHTML = '<img src="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/imagens/edit.png" border="0" onclick="editarBaseConhecimento(' 
	        		+ id + ')" style="cursor:pointer" />';
		}
		
			/*geber.costa
		validador do checkbox acompanhamento
		*/
		function validarAcompanhamento(){
			
			if (document.getElementById('acompanhamento').checked == "N"){
				window.alert("sem acompanhamento!");
			}else if (document.getElementById('acompanhamento').checked == "S"){
				window.alert("com acompanhamento!");
			}
		}
				
		function editarBaseConhecimento(idBaseConhecimento){
			var idProblema = document.form.idProblema.value;
			document.getElementById('iframeBaseConhecimento').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimento/baseConhecimento.load?iframe=true&erroConhecido=S&idBaseConhecimento=" + idBaseConhecimento+"&idProblema="+idProblema;
			$("#POPUP_BASECONHECIMENTO").dialog("open");
			
		}
		
		function limpaListasRelacionamentos(){
			tabelaRelacionamentoICs.limpaLista();
			tabelaRelacionamentoSolicitacaoServico.limpaLista();
			tabelaRelacionamentoMudancas.limpaLista();
			
		}
		
		this.limpaLista = function(){
			tableObjects.length = 0;
			tableObjects = null;
			tableObjects = [];
			limpaTabela();
		}
		
		var limpaTabela = function(){
			while (getTabela().rows.length > 1){
				getTabela().deleteRow(1); 
			}
		}
		
		function gravarEContinuar() {
			document.form.acaoFluxo.value = '<%=Enumerados.ACAO_INICIAR%>';
			gravar();
	    }
		
	    
	    function gravarEFinalizar() {
			document.form.acaoFluxo.value = '<%=Enumerados.ACAO_EXECUTAR%>';
			document.form.fecharItensRelacionados.value = "N";
			
			if($("input[name='status']:checked").val() == 'Resolução'){
				verificarItensRelacionados(true)
			}else{
				document.form.fireEvent('validacaoAvancaFluxo');		
			}
	    }

	 	function verificarItensRelacionados(validarItem){
	 		if(validarItem){
				document.form.fireEvent("verificarItensRelacionados");
				document.form.fecharItensRelacionados.value = "N";
		   }else{
			   if(confirm(i18n_message("citcorpore.comum.fecharItemRelacionados"))){
					document.form.fecharItensRelacionados.value = "S";
					document.form.fireEvent('validacaoAvancaFluxo');		
				}else{
					document.form.fecharItensRelacionados.value = "N";
					document.form.fireEvent('validacaoAvancaFluxo');		
				}
		    }
		}
	    
		$(function() {
			
			// Manipulador de evento para o radio button de confirmação de solução de contorno.
			// Se S (sim), torna obrigatório o preenchimento do campo solução de contorno na aba Diagnóstico. 
			$('input[type=radio][name=precisaSolucaoContorno][value="S"]').click(function() {
				
				$('#rotuloSolucaoContorno').addClass('campoObrigatorio');
				
				//$('#solucaoContorno').addClass('Valid[Required]');
				
			});
			
			$('input[type=radio][name=precisaSolucaoContorno][value="N"]').click(function() {
				
				$('#rotuloSolucaoContorno').removeClass('campoObrigatorio');
				
				//$('#solucaoContorno').removeClass('Valid[Required]');
				
			});
			
			$('input[type=radio][name=precisaMudanca][value="S"]').click(function() {
				
				$('#abaMudancas').show();
				
				$('#relacionarMudancas').show();
				
			});
			
			$('input[type=radio][name=precisaMudanca][value="N"]').click(function() {
				
				$('#tabs').tabs('select', '#relacionaIcs');
				
				$('#abaMudancas').hide();
				
				$('#relacionarMudancas').hide();
				
			});
			
			$('input[type=radio][name=grave][value="S"]').click(function() {
				
				$('#abaRevisaoProblemaGrave').show();
				
				$('#relacionarRevisaoProblemaGrave').show();				
				
			});
			
			$('input[type=radio][name=grave][value="N"]').click(function() {
				
				$('#tabs').tabs('select', '#relacionaIcs');
				
				$('#abaRevisaoProblemaGrave').hide();
				
				$('#relacionarRevisaoProblemaGrave').hide();
				
				
			});
			
		});
		function informaNumeroSolicitacao(numero){
			document.getElementById('divTituloProblema').innerHTML = 
				'<h2><i18n:message key="problema.numero"/>&nbsp;' + numero + '</h2>';   
		}
		
		function chamaPopupCadastroSol(){
			if (document.form.idContrato.value == ''){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				return;
			}		
			var idContrato = 0;
			try{
				idContrato = document.form.idContrato.value;
			}catch(e){
			}
			dimensionaPopupCadastroRapido(1000, 530);
			popup2.abrePopupParms('empregado', '', 'idContrato=' + idContrato);
		}	
		
		function fecharAddSolicitante(){
			$("#popupCadastroRapido").dialog('close');
		}
		
		function abreCadastroCategoriaProblema(){
			dimensionaPopupCadastroRapido(900, 500);
			popupCategoriaProblema.abrePopup('categoriaProblema', 'alimentaComboCategoriaProblema');
		}
		
		function gravarGestaoItemConfiguracao(idProblema){
			
			var id =  parseInt(idProblema);
			
			document.getElementById('iframeItemConfiguracao').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load?iframeProblema=true&idProblema='+id;
			
			$("#POPUP_ITEMCONFIGURACAO").dialog("open");				
		}
		$(function() {
			$("#POPUP_ITEMCONFIGURACAO").dialog({
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true
			});
		});
		
		$(function() {
			$("#POPUP_SOLUCAO_CONTORNO").dialog({
				title: i18n_message("problema.solucao_contorno"),
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true,
				show: "fade",
				hide: "fade"
			});

			<!-- Thiago Fernandes - 01/11/2013 08:30 - Sol. 121468 - Popup para cadastro nova solicitação serviço.. -->
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog({
				autoOpen : false,
				width : 1520,
				height : 800,
				modal : true
			});
			
		});

		function abrirPopupSolContorno(){
			$('#POPUP_SOLUCAO_CONTORNO').dialog("open");
		}

		function fecharSolContorno(){
			$("#POPUP_SOLUCAO_CONTORNO").dialog("close");
		}

		function gravarSolContorno() {
			var tituloSolucaoContorno = $('#tituloSolCon').val()
			$('#tituloSolucaoContorno').val(tituloSolucaoContorno);
			var solucaoContorno = $('#descSolCon').val();
			$('#solucaoContorno').val(solucaoContorno);
			document.form.fireEvent("gravarSolContorno");
		}
		
		$(function() {
			$("#POPUP_SOLUCAO_DEFINITIVA").dialog({
				title: i18n_message("problema.solucao_definitiva"),
				autoOpen : false,
				width : "98%",
				height : $(window).height()-100,
				modal : true,
				show: "fade",
				hide: "fade"
			});
		});

		function abrirPopupSolDefinitiva(){
			$('#POPUP_SOLUCAO_DEFINITIVA').dialog("open");
		}

		function fecharSolDefinitiva(){
			$("#POPUP_SOLUCAO_DEFINITIVA").dialog("close");
		}

		function gravarSolDefinitiva() {
			var tituloSolucaoDefinitiva = $('#tituloSolDefinitiva').val()
			$('#tituloSolucaoDefinitiva').val(tituloSolucaoDefinitiva);
			var solucaoDefinitiva = $('#descSolDefinitiva').val();
			$('#solucaoDefinitiva').val(solucaoDefinitiva);
			document.form.fireEvent("gravarSolDefinitiva");
		}
		
		function fecharFrameProblema(){
			limpar(document.form);
			parent.fecharFrameProblema();
		}
		
		function carregarInformacoesComplementares() {
            document.form.fireEvent('carregaInformacoesComplementares');
        }   
		
		function exibirInformacoesComplementares(url) {
            if (url != '') {
                JANELA_AGUARDE_MENU.show();
                document.getElementById('divInformacoesComplementares').style.display = 'block';
                document.getElementById('fraInformacoesComplementares').src = url;
            }else{
                try{
                	escondeJanelaAguarde();
                }catch (e) {
                }       
                document.getElementById('divInformacoesComplementares').style.display = 'none';
            } 
        }
		
		function exibirInformacoesAprovacao(url) {
            if (url != '') {
            	var urlAtual = window.location;
    			urlAtual = urlAtual.toString();
    			var n = urlAtual.indexOf("pages/");
    			n = n + 6;
    			var urlFinal = urlAtual.substring(0, n);
    			url = urlFinal + url;
                JANELA_AGUARDE_MENU.show();
                document.getElementById('divInformacoesComplementares').style.display = 'block';
                document.getElementById('fraInformacoesComplementares').src = url;
            }else{
                try{
                	escondeJanelaAguarde();
                }catch (e) {
                }       
                document.getElementById('divInformacoesComplementares').style.display = 'none';
            } 
        }

		function escondeJanelaAguarde() {
            JANELA_AGUARDE_MENU.hide();
        }
		
		function mostraMensagemInsercao(msg){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}
		function verificaContrato() {
			if (document.form.idContrato.value == ''){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				return;
			}
		}
		function restoreImpactoUrgenciaPorTipoProblema(){
    	   document.form.fireEvent('restoreImpactoUrgenciaPorCategoriaProblema');
       	}
		
		$(function() {
		   $('#telefoneContato').mask('(999) 9999-9999');
		});
		
		function dimensionaPopupCadastroRapido(w, h) {
			$("#popupCadastroRapido").dialog("option","width", w)
			$("#popupCadastroRapido").dialog("option","height", h)
		}

		<!-- Thiago Fernandes - 01/11/2013 08:30 - Sol. 121468 - Popup para cadastro nova solicitação serviço.. -->
		function fecharModalNovaSolicitacao() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function pesquisarItensFiltro() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function abrirPopupNovaSolicitacaoServico(){
			document.getElementById('iframeNovaSolicitacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?iframe=true";
			redimencionarTamhanho();
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("open");
		}
		/**
		* Motivo: Redimenciona a popup em tamanho compativel com o tamanho da tela
		* Autor: flavio.santana
		* Data/Hora: 02/11/2013 15:35
		*/
		function redimencionarTamhanho(){
			var altura = parseInt($(window).height() * 0.75);
			var largura = parseInt($(window).width() * 0.75);
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("option","width", largura)
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("option","height", altura)
		}

		function abrirModalPesquisaItemConfiguracao(){

			var h;
			var w;
			w = parseInt($(window).width() * 0.75);
			h = parseInt($(window).height() * 0.85);
			
			document.getElementById('framePesquisaItemConfiguracao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load?iframe=true";
		
			$("#POPUP_PESQUISAITEMCONFIGURACAO").dialog("open");
		}

		function selectedItemConfiguracao(id){
			document.getElementById('hiddenIdItemConfiguracao').value = id;
			document.form.fireEvent("restaurarItemConfiguracao");
		}

		function atualizarTabelaICs(id, desc) {
			addLinhaTabelaItemConfiguracao(id, desc, true);
		}

	</script>

</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>	
	 <div id="divBarraFerramentas" style='position:absolute; top: 0px; left: 500px; z-index: 1000'>
		<jsp:include page="/pages/barraFerramentasProblemas/barraFerramentasProblemas.jsp"></jsp:include>
	</div>
	<div id="wrapper" class="wrapper">
	<div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
	<div id='divTituloProblema'  class="flat_area grid_16" style="text-align:right;">
		<h2><i18n:message key="problema.registro_problema"/></h2>
	</div>
	<div class="box grid_16 tabs">			
	<form  name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/problema/problema'>
		<input type="hidden" id="itensConfiguracaoRelacionadosSerializado" name="itensConfiguracaoRelacionadosSerializado" />
		<input type="hidden" id="servicosRelacionadosSerializado" name="servicosRelacionadosSerializado" />
		<input type="hidden" id="solicitacaoServicoSerializado" name="solicitacaoServicoSerializado" />
		<%if(id!=null){ %>
		<input type="hidden" id="idBaseConhecimento" name="idBaseConhecimento">
		<%} %>
		<input type="hidden" id="idProblema" name="idProblema" />
		<input type="hidden" id="idProprietario" name="idProprietario" />
		<input type="hidden" id="idSolicitante" name="idSolicitante" />
		<input type="hidden" id="idCriador" name="idCriador" />	
		<input type="hidden" id="dataHoraInicio" name="dataHoraInicio" />
		<input type="hidden" id="dataHoraCaptura" name="dataHoraCaptura" />
		<input type="hidden" id="idFaseAtual" name="idFaseAtual" />
		<input type="hidden" id="requisicaoMudancaSerializado" name="RequisicaoMudancaSerializado" />
		<input type='hidden' name='escalar' id='escalar' />
		<input type='hidden' name='alterarSituacao' id='alterarSituacao' />
		<input type='hidden' name='idTarefa' id='idTarefa' />
		<input type='hidden' name='acaoFluxo' id='acaoFluxo' />			
		<input type='hidden' name='fase' id='fase' />
		<input type='hidden' name='tituloSolucaoContorno' id='tituloSolucaoContorno' />
		<input type='hidden' name='solucaoContorno' id='solucaoContorno' maxlength="700"/>
		<input type='hidden' name='idSolucaoContorno' id='idSolucaoContorno' />
		<input type='hidden' name='tituloSolucaoDefinitiva' id='tituloSolucaoDefinitiva' />
		<input type='hidden' name='solucaoDefinitiva' id='solucaoDefinitiva' />
		<input type='hidden' name='idSolucaoDefinitiva' id='idSolucaoDefinitiva' />
		<input type='hidden' name='chamarTelaProblema' id='chamarTelaProblema' />
		<input type='hidden' name='informacoesComplementares_serialize' id='informacoesComplementares_serialize' />
		<input type='hidden' id='fecharItensRelacionados' name="fecharItensRelacionados">
		<input type='hidden' id='hiddenIdItemConfiguracao' name="hiddenIdItemConfiguracao">
		<div class="col_100">
					<fieldset>
						<label >&nbsp;</label>
						<div>
							&nbsp;
						</div>
					</fieldset>
				</div>
		
		<div class="col_100">
					<fieldset>
						<label class="campoObrigatorio" style="font-family: Arial; font-weight: bold;"><i18n:message key="contrato.contrato" /></label>
						<div>
							<select  id="idContrato" name='idContrato' class=" Valid[Required] Description[<i18n:message key='contrato.contrato' />]" 
								onchange="setaValorLookup(this);" onclick= "document.form.fireEvent('carregaUnidade');"> <!--SETAR DPOIS carregarInformacoesComplementares(); -->
								 
							</select>
						</div>
					</fieldset>
				</div>
					<div class="col_100">
						<div class="col_50">	
						<fieldset style="height: 60px;">
							<label class="campoObrigatorio" ><i18n:message key="solicitacaoServico.solicitante" /></label>
							<div>
								<input class="Valid[Required] Description[solicitacaoServico.solicitante]" id="addSolicitante" name="solicitante" type="text" readonly="readonly"/>
							</div>
						</fieldset>
						</div>
						<div class="col_25">
							<fieldset style="height: 60px;">
								<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="origemAtendimento.origem" /></label>
								<div>
									<select id="idOrigemAtendimento" name='idOrigemAtendimento' class="Valid[Required] Description[origemAtendimento.origem]"></select>
								</div>
							</fieldset>
						</div>
						<div class="col_25">
						<fieldset style="height: 60px;">
							<label><i18n:message key="citcorpore.comum.grupoExecutor"/></label>
							<div>
								<select name='idGrupo' id='idGrupo'  >								
								</select>			
							</div>
						</fieldset>
					</div>
				</div>
				<!-- infContato -->
				<div class="col_100">
					<div>
						<h2 class="section"><i18n:message key="solicitacaoServico.informacaoContato" /></h2>
					</div>
				
				<div class="col_100">
					<div class="col_50">
						<div class="col_50">
							<fieldset>
								<label class="campoObrigatorio">
									<i18n:message key="solicitacaoServico.nomeDoContato" /></label>
									<div>
										<input id="contato" type='text' name="nomeContato" maxlength="70" 
										class="Valid[Required] Description[<i18n:message key='solicitacaoServico.nomeDoContato' />]" />
									</div>
							</fieldset>
						</div>
							
						<div class="col_50">
							<fieldset>
								<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.email" /></label>
									<div>
										<input id="emailContato" type='text'  name="emailContato" maxlength="120" class="Valid[Required, Email] Description[citcorpore.comum.email]" />
									</div>
								</fieldset>
						</div>
								
						<div class="col_50">
							<fieldset >
								<label><i18n:message key="citcorpore.comum.telefone" /></label>
								<div>
									<input id="telefoneContato"  type='text'  name="telefoneContato" maxlength="20" class="" />
								</div>
							</fieldset>
						</div>
									
						<div class="col_50">
							<fieldset >
								<label><i18n:message key="citcorpore.comum.ramal" /></label>
								<div>
									<input id="ramal"  type='text'  name="ramal" maxlength="5" class="Format[Numero]" />
								</div>
							</fieldset>
						</div>
								
						<div class="col_50">
							<fieldset style="height: 55px">
								<label class="tooltip_bottom campoObrigatorio" title="<i18n:message key="colaborador.cadastroUnidade"/>" >
									<i18n:message key="unidade.unidade"/></label>
								<div>
									<select name='idUnidade' id = 'idUnidade' onchange="document.form.fireEvent('preencherComboLocalidade')" class="Valid[Required] Description[colaborador.cadastroUnidade]" ></select>
								</div>
						    </fieldset>
						</div>
								
						<div class="col_50">
							<fieldset style="height: 55px" >
								<label class="tooltip_bottom " title="<i18n:message key="colaborador.cadastroUnidade"/>" >
									<i18n:message key="solicitacaoServico.localidadeFisica"/>
								 </label>
								 <div>
									<select name='idLocalidade' id = 'idLocalidade'></select>
								</div>
						    </fieldset>
						</div>
					</div>
									
						<div class="col_50">
							<fieldset style="height: 112px">
								<label><i18n:message key="colaborador.observacao"/></label>
								<div>
									<textarea id="observacao" class="col_98" name="observacao" maxlength="700" rows="4" style="height: 90px; float: right;"></textarea>
								</div>
							</fieldset>
						</div>
				</div>
				</div> <!-- FIM_divInfContato -->
				
				<div class="col_100">
					
				</div>
		
		<div class="col_100">
					<div>
						<h2 class="section"><i18n:message key="problema.informacao" /></h2>
					</div>
						
					<div class="col_50"> <!-- Lado Esquerdo da Tela -->			
						<div class="col_100">
							<fieldset>
								<label class="campoObrigatorio"><i18n:message key="problema.titulo" /></label>
									<div>
										<input maxlength="255" class="Valid[Required] Description[problema.titulo]" id="titulo" name="titulo" type="text"/>
									</div>
							</fieldset>
						</div>
					
						<div style="width: 100%; float: left;">
							<fieldset>
								<label class="campoObrigatorio"><i18n:message key="problema.descricao" /></label>
									<div>
										<textarea class="Valid[Required] Description[problema.descricao]" id="descricao" class="col_100" name="descricao" rows="4" maxlength="65000" style="height: 250px;"></textarea>
									</div>
							</fieldset>
						</div>
				
					</div> 
			
					<div class="col_50"><!-- Lado Direito da Tela -->
						<div class="col_33">
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><i18n:message key="problema.severidade" /></label>
								<div>
									<select class="influenciaPrioridade" onchange="atualizaPrioridade()" id="severidade" name="severidade">
										<option><i18n:message key="citcorpore.comum.baixa"/></option>
										<option><i18n:message key="citcorpore.comum.media"/></option>
										<option><i18n:message key="citcorpore.comum.alta"/></option>
									</select>
								</div>
							</fieldset>
						</div>
						<div class="col_33" >
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><i18n:message key="problema.impacto"/></label>
								<div>
									<select class="influenciaPrioridade" onchange="atualizaPrioridade()" id="impacto" name="impacto">
										<option value="B"><i18n:message key="citcorpore.comum.baixo"/></option>
										<option value="M"><i18n:message key="citcorpore.comum.medio"/></option>
										<option value="A"><i18n:message key="citcorpore.comum.alto"/></option>
									</select>
								</div>
							</fieldset>
						</div>
						<div class="col_33" >
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><i18n:message key="problema.urgencia"/></label>
								<div>
									<select class="influenciaPrioridade" onchange="atualizaPrioridade()" id="urgencia" name="urgencia">
										<option value="B"><i18n:message key="citcorpore.comum.baixo"/></option>
										<option value="M"><i18n:message key="citcorpore.comum.medio"/></option>
										<option value="A"><i18n:message key="citcorpore.comum.alto"/></option>
									</select>
								</div>
							</fieldset>
						</div>					
						<div class="col_100">
						<%-- <div class="col_50" >
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><i18n:message key="tipoMudanca.nomeCalendario" /></label>
								<div>
									<select id="idCalendario" name="idCalendario"></select>
								</div>
							</fieldset>
						</div>		 --%>
						<div  class="col_50">
							<fieldset style="height: 60px">
								<label><i18n:message key="problema.solucionar_contornar"/></label>
								<div>
									<input id="dataHoraLimiteSolucionar" name="dataHoraLimiteSolucionar" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
								</div>
							</fieldset>
						</div>
						<div class="col_20">
							<fieldset style="height: 60px" >
								<label class="campoObrigatorio"><i18n:message key="prioridade.prioridade" /></label>
								<div>
									<input id="prioridade" name="prioridade" type="text" readonly="readonly" value="5"/>
								</div>
							</fieldset>
						</div>		
						<div  class="col_30">
							<fieldset style="height: 60px">
								<label><i18n:message key="problema.gerenciamentoProblema"/></label>
								<div>
									<input type="radio" id="proativoReativo" name="proativoReativo" value="Proativa" checked="checked" /><i18n:message key="problema.proativo"/>
									<input type="radio" id="proativoReativo" name="proativoReativo" value="Reativo" /><i18n:message key="problema.reativo"/>
								</div>
							</fieldset>
						</div>	
						</div>
						
						
						
						<div id="divNotificacaoEmail" class="col_100" >
								<fieldset class="Col_50">
									<label><i18n:message key="problema.notificacao_email"/></label>								
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailCriacao' checked="checked"/><i18n:message key="problema.registrar_novo_problema"/></label><br>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailFinalizacao' checked="checked"/><i18n:message key="problema.finalizar_problema"/></label><br>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailAcoes'/><i18n:message key="problema.acoes_relacionadas_problema"/></label>
									<%-- <label style="cursor:pointer"><input type="checkbox" value="S" name="enviaEmailPrazoSolucionarExpirou" /><i18n:message key="problema.enviaemailprazosolucaoexpirado" /></label>	 --%>				
								</fieldset>
						</div>
					</div>
				</div>	
				<div style="display: block;" id="categoria">
									<div class="col_33">
										<fieldset style="height: 60px">
											<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="categoriaProblema.categoriaProblema"/>
											<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abreCadastroCategoriaProblema()">
											</label>
											<div>
												<select name='idCategoriaProblema' onchange="carregarInformacoesComplementares();restoreImpactoUrgenciaPorTipoProblema();" onclick="verificaContrato()" class="Valid[Required] Description[categoriaProblema.categoriaProblema]" ></select>
											</div>
										</fieldset >
									</div>
									<div class="col_33">
										<fieldset style="height: 60px">
											<label style="cursor: pointer;"><i18n:message key="problema.causa"/></label>
											<div>
												<select name='idCausa' ></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33"> 
										<fieldset style="FONT-SIZE: xx-small; height: 60px;">
											<label style="cursor: pointer;"><i18n:message key="citcorpore.comum.categoriaSolucao"/></label>
											<div>
												<select name='idCategoriaSolucao' ></select>
											</div>
										</fieldset>
									</div>
									<%-- <div id="divPrecisaSolucaoContorno" class="col_100">
										<fieldset style="FONT-SIZE: xx-small;">
											<label id="rotuloPrecisaSolucaoContorno" style="cursor: pointer;">
												<i18n:message key="problema.precisaSolucaoContorno" />
											</label>
											<div>
												<label>
													<input type="radio" name="precisaSolucaoContorno"  value="S" class="Description[problema.precisaSolucaoContorno]" />
													<i18n:message key="citcorpore.comum.sim" />
												</label>	
												<label>
													<input type="radio" name="precisaSolucaoContorno" value="N"  class="Description[problema.precisaSolucaoContorno]" checked="checked"  />
													<i18n:message key="citcorpore.comum.nao" />
												</label>
											</div>
										</fieldset>
									</div> --%>
							<%-- 		<div id="divPrecisaMudanca" class="col_100">
										<fieldset style="FONT-SIZE: xx-small;">
											<label style="cursor: pointer;">
												<i18n:message key="problema.precisaMudanca" />
											</label>
											<div>
												<label>
													<input type="radio" name="precisaMudanca"  value="S" class="Description[problema.precisaMudanca]" />
													<i18n:message key="citcorpore.comum.sim" /></label>	
												<label>
													<input type="radio" name="precisaMudanca" value="N" class="Description[problema.precisaMudanca]"  checked="checked" />
													<i18n:message key="citcorpore.comum.nao" />
												</label>
											</div>
										</fieldset>									
									</div> --%>
							<%-- 		<div id="divProblemaGrave" class="col_100">
										<fieldset style="FONT-SIZE: xx-small;">
											<label id="rotuloProblemaGrave" style="cursor: pointer;">
												<i18n:message key="problema.grave" />
											</label>
											<div>
												<label>
													<input type="radio" name="grave" id="simGrave" value="S" class="Description[problema.grave]" />
													<i18n:message key="citcorpore.comum.sim" />
													</label>
												<label>
													<input type="radio" name="grave" value="N" id="naoGrave" class="Description[problema.grave]" checked="checked"  />
													<i18n:message key="citcorpore.comum.nao" />
												</label>
											</div>
										</fieldset>									
									</div> --%>
								<%-- 	<div id="divResolvido" class="col_100">
										<fieldset style="FONT-SIZE: xx-small;">
											<label id="rotuloResolvido" style="cursor: pointer;">
												<i18n:message key="problema.resolvido"/> ?
											</label>
											<div>
												<label>
													<input type="radio" name="resolvido" value="S" id="simResolvido" class="Description[problema.resolvido]" />
													<i18n:message key="citcorpore.comum.sim" />
													</label>
												<label>
													<input type="radio" name="resolvido" value="N" id="naoResolvido" class="Description[problema.resolvido]" checked="checked" />
													<i18n:message key="citcorpore.comum.nao" />
												</label>
											</div>
										</fieldset>									
									</div> --%>
								</div>
								
							<div class="col_100" id='divInformacoesComplementares' style='display:none;'>
                                <iframe id='fraInformacoesComplementares' name='fraInformacoesComplementares' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'></iframe>
                            </div>
								
				<div id="statusProblema" class="col_100">
				<fieldset>
					<label>
						<i18n:message key="problema.status"/>
					</label>
					
						<label id="statusSetado" style="cursor: pointer;">
							
						</label >
					<label id="statusCancelada" style="cursor: pointer;">
						<input type="radio"  name="status" value="Cancelada" />
						<i18n:message key="problema.cancelado"/>
					</label>
					</fieldset>
				</div>
				<div class="col_100">
						<fieldset>
							<label><i18n:message key="citcorpore.comum.fechamento" /></label>
							<div>
								<textarea id="fechamento" name="fechamento" maxlength="500" cols='70' rows='5' class="Description[Resposta]"></textarea>
							</div>
						</fieldset>									
				</div>
				
				
						<div class="col_100">
							<fieldset style="height: 60px">
								<label><i18n:message key="ocorrenciaProblema.dataHoraUltimaAtualizacao"/></label>
								<div id='dataHoraUltimaAtualizacao'></div>
							</fieldset>
						</div>	
						
				<div class="col_100" id='divBotaoAddRegExecucao' >
						<fieldset style="FONT-SIZE: xx-small;">
							<button type='button' name='btnAddRegExec' id='btnAddRegExec' onclick='mostrarEscondeRegExec()'><i18n:message key="solicitacaoServico.addregistroexecucao_mais" /></button>
						</fieldset>																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																																		
					</div>	
					<div class="col_100">
						<fieldset style="FONT-SIZE: xx-small">
							<label id='lblMsgregistroexecucao' style='display:none'><font color='red'><b><i18n:message key="solicitacaoServico.msgregistroexecucao" /></b></font></label>
								<div id='divMostraRegistroExecucao' style='display:none'>
									
									<textarea id="registroexecucao" name="registroexecucao" cols='70' rows='5' class="Description[citcorpore.comum.resposta]" maxlength="700"></textarea>
									
								</div>
						</fieldset>									
					</div>			
					<div class="col_100" style="overflow : auto;max-height: 400px">
						<fieldset>
							<div  id="tblOcorrencias" ></div>
						</fieldset>									
					</div>	
		
		<div id="abas" class="formRelacionamentos">
			<div id="tabs" class="block">
				<ul class="tab_header clearfix">
					<li><a href="#relacionaIcs"><i18n:message key="problema.relacionar_ics"/></a></li>
					<li><a href="#relacionarMudancas" id="abaMudancas"><i18n:message key="requisicaoMudanca.mudancas"/>
					</a></li>
					<li><a href="#relacionarIncidentes"><i18n:message key="problema.incidentes"/>
					</a></li>
					<li><a href="#relacionarRevisaoProblemaGrave" id="abaRevisaoProblemaGrave"><i18n:message key="problema.revisao_problema_grave"/>
					</a></li>
					<li><a href="#relacionarDiagnostico"><i18n:message key="problema.avaliacaoDiagnostico"/>
					</a></li>
					<li><a href="#relacionarSolucao"><i18n:message key="citcorpore.comum.solucao"/>
					</a></li>
					<%if(id==null){ %>
					<li><a href="#relacionarErrosConhecidos"><i18n:message key="problema.erros_conhecidos"/></a></li>
					<%} %>
				</ul>
				<div id="relacionaIcs">
					<div class="formHead">
						<div style="width: 15%; float: left; border: 0px!important;" align="center" >
						<fieldset>
							<label  >
								<i18n:message key="itemConfiguracao.itemConfiguracao"/>
								<img id="imagenIC" onclick="abrirModalPesquisaItemConfiguracao();" style="vertical-align: middle; cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<img id='btnCadastroItemConfiguracao' style="vertical-align: middle; cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="gravarGestaoItemConfiguracao(document.form.idBaseConhecimento.value);">
							</label>
						</fieldset>
						</div>
						<!-- <div style="width: 99%; height : 30px; float: left;"></div> -->
						<div class="formBody" style="overflow: auto;">
							<table id="tblICs" class="table table-bordered table-striped">
								<tr>
									<th width="13%">
										<i18n:message key="parametroCorpore.id"/>
									</th>
									<th width="35%">
										<i18n:message key="citcorpore.comum.nome"/>
									</th>
									<th width="50%">
										<i18n:message key="citcorpore.comum.descricao"/>
									</th>
									<th width="2%">
										
									</th>
								</tr>
							</table>
						</div>
					</div>
				</div>
				<div id="relacionarMudancas">
					<div class="formHead">
						<div style="width: 15%; float: left;border: 0px!important;" align="center">
						<fieldset>
							<label  >
								<i18n:message key="requisicaMudanca.mudanca"/>
								<img onclick='pesquisarRequisicaoMudanca();' style="vertical-align: middle; cursor: pointer;"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
								<img  onclick="popupManagerRequisicaoMudanca.abrePopup('requisicaoMudanca', '');" style="vertical-align: middle; cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
							</label>
						</fieldset>
						</div>
						<!-- <div style="width: 99%; height : 30px; float: left;"></div> -->
					<div class="formBody">
						<table id="tblRDM" class="table table-bordered table-striped">
								<tr>
									<th width="13%"><i18n:message key="parametroCorpore.id"/></th>
									<th width="35%"><i18n:message key="lookup.titulo"/></th>
									<th width="50%"><i18n:message key="lookup.status"/></th>
									<th width="2%"></th>
								</tr>
							</table>
						</div>
					</div>
				</div>			
				<div id="relacionarIncidentes">
							<div class="formHead">
								<div style="width: 20%; float: left;border: 0px!important;" align="center" >
								<fieldset>
									<label  >
										<i18n:message key="solicitacaoServico.solicitacao"/>
										<img id="addSolicitacaoServico" style="vertical-align: middle; cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
										<img  onclick="abrirPopupNovaSolicitacaoServico();" style="vertical-align: middle; cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
									</label>
								</fieldset>	
								</div>
								<div ></div>
								<div class="formBody">
									<table id="tblSolicitacaoServico" class="table table-bordered table-striped">
										<tr>
											<th width="35%">
												<i18n:message key="problema.numero_solicitacao"/>
											</th>
											<th width="63%">
												<i18n:message key="citcorpore.comum.descricao"/>
											</th>
											<th width="2%"></th>
										</tr>
									</table>
								</div>
							</div>
					</div>
						<div id="relacionarRevisaoProblemaGrave">
							<!-- REVISÃO DE PROBLEMA GRAVE
							geber.costa
							
							Ações corretas, 
							ações incorretas, 
							possíveis melhorias futuras
							prevenção de recorrência do problema,
							se ações de acompanhamento são necessárias
							e responsabilidade de terceiros     -->
							
							<fieldset>
								<label ><i18n:message key="problema.acoes_corretas"/></label>
								<textarea id="acoesCorretas" name="acoesCorretas" maxlength="500" style="height: 100px; width: 99%;"></textarea>
							
								<label><i18n:message key="problema.acoes_erradas"/></label>
								<textarea id="acoesIncorretas" name="acoesIncorretas" maxlength="500" style="height: 100px; width: 99%;"></textarea>
							
								<label><i18n:message key="problema.possiveis_melhorias_futuras"/></label>
								<textarea id="melhoriasFuturas" name="melhoriasFuturas" maxlength="500" style="height: 100px; width: 99%;"></textarea>
							
								<label><i18n:message key="problema.prevencao_de_recorrencia"/></label>
								<textarea id="recorrenciaProblema" name="recorrenciaProblema" maxlength="500" style="height: 100px; width: 99%;"></textarea>
							
								<label style="cursor: pointer;">
									<input type="checkbox" id="acompanhamento" name="acompanhamento"  value = "S" onchange="validarAcompanhamento()"/>
									<i18n:message key="problema.necessario_acompanhamento"/>
								</label>
							
								<label ><i18n:message key="problema.responsabilidade_de_terceiros"/></label>
								<textarea id="responsabilidadeTerceiros" name="responsabilidadeTerceiros" maxlength="500" style="height: 100px; width: 99%;"></textarea>
							
							</fieldset>
					</div><!-- FIM_relacionarRevisaoProblemaGrave -->
					
						<div id="relacionarDiagnostico">
							<!-- ANALISES DE IMPACTO, RISCOS E ROLLBACK -->
							<fieldset>
							
							<label id="rotuloCausaRaiz"  class="campoObrigatorio" ><i18n:message key="problema.causa_raiz"/></label>
							<textarea id="causaRaiz" name="causaRaiz" maxlength="700" style="height: 100px; width: 99%;"></textarea>
							
							<label ><i18n:message key="problema.mensagem_erro_associada"/></label>
							<textarea id="msgErroAssociada" name="msgErroAssociada" maxlength="500" style="height: 100px; width: 99%;"></textarea>
							
							<label><i18n:message key="problema.diagnostico"/></label>
							<textarea id="diagnostico" name="diagnostico" maxlength="500" style="height: 100px; width: 99%;"></textarea>
								
							</fieldset>
					</div>
						<div id="relacionarSolucao">
							<div class="col_100">
								<div style="width: 20%; float: left;border: 0px!important;" align="center" >
								<fieldset>
									<label class="campoObrigatorio" id="rotuloSolucaoContorno"  style="cursor: pointer;" onclick='abrirPopupSolContorno();'>
									<i18n:message key="problema.adicionarEditarSolucaoContorno"/>
									<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
									</label>
									
								</fieldset>
								</div>
							<div class="formBody" id="divTblSolContorno" style='display: block; overflow: auto;'>
								<table id="tblSolContorno" class="table table-bordered table-striped">
									<tr>
										<th width="20%">
											<i18n:message key="problema.titulo"/>
										</th>
										<th width="10%">
											<i18n:message key="requisitosla.criadoem"/>
										</th>
										<th width="70%">
											<i18n:message key="citcorpore.comum.descricao"/>
										</th>
									</tr>
								</table>
							</div>
							</div>
							<div class="col_100">
								<fieldset>
									<div style="width: 15%; float: left;border: 0px!important;" align="center" >
									<label  style="cursor: pointer;" onclick='abrirPopupSolDefinitiva();'>
									<i18n:message key="problema.adicionarEditarSolucaoDefinitiva"/>
									<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
									</label>
									</div>
								</fieldset>
							<div class="formBody" id="divTblSolDefinitiva" style='display: block;overflow:auto; '>
								<table id="tblSolDefinitiva" class="table table-bordered table-striped">
									<tr>
										<th width="20%">
											<i18n:message key="problema.titulo"/>
										</th>
										<th width="10%">
											<i18n:message key="requisitosla.criadoem"/>
										</th>
										<th width="70%">
											<i18n:message key="citcorpore.comum.descricao"/>
										</th>
									</tr>
								</table>
							</div>
							</div>
						</div>
						<%if(id==null){ %>
						<div id="relacionarErrosConhecidos">
							<div class="col_100">
								<input type="hidden" id="idBaseConhecimento" name="idBaseConhecimento">
									 <div id="divBaseConhecimento" style="width: 15%; float: left; border: 0px!important;" align="center">
										<fieldset >
											<label ><i18n:message key='baseConhecimento.baseConhecimento'/>
											<img onclick='gravarErroConhecido();' style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png">
											</label>
										</fieldset>	
									</div> 
									<div id='divProblemaConhecimento'	style='height: 120px; width: 99%; overflow: auto;'>
										<table id='tblErrosConhecidos' class="table table-bordered table-striped">
											<tr>
											
											<th width="10%"></th> 
											<th width="60%" ><i18n:message key="requisicaMudanca.titulo" /></th>
											<th width="29%" ><i18n:message key="requisicaMudanca.status" /></th>
											<th >&nbsp;</th>
											</tr>
										</table>
									</div>
								</div>
						</div>
					<%} %>
					</div>		
				</div>
				
				<div id="divBotoes" class="formFooter">
				<!-- antes do q copiei -->
				

			
				
				<!-- dpois do que copiei -->
					<%if (tarefaAssociada.equalsIgnoreCase("N")) {%>
						<button type='button' name='btnGravar' class="light" onclick='gravar()'>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
							<span><i18n:message key="citcorpore.comum.gravar" /></span>
						</button>
					<%}else{%>
						<button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
							<span><i18n:message key="citcorpore.comum.gravarEContinuar" /></span>
						</button>
						<button type='button' name='btnGravarEFinalizar' class="light" onclick='gravarEFinalizar();'>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/cog_2.png">
							<span><i18n:message key="citcorpore.comum.gravarEFinalizar" /></span>
						</button>
					<%}%>
					
					<button type='button' name='btnLimpar' class="light" onclick='limpar(document.form);'>
						<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
						<span>
							<i18n:message key="citcorpore.comum.limpar" />
						</span>
					</button>					<%-- <button type='button' name='btnExcluir' class="light" onclick='deletar();'>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
						<span>
						<i18n:message key="citcorpore.comum.excluir" />
					</span>
					</button> --%>
				</div><!-- FIM_formFooter -->
				<div id="divBotaoFecharFrame" class="formFooter">
					<button type='button' name='btnGravarFecharDef' class="light"
					onclick='fecharFrameProblema();'>
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span><i18n:message key="citcorpore.comum.fechar" />
					</span>
				</button>
				</div>
				<!-- Solução de Contorno -->
				<div id="POPUP_SOLUCAO_CONTORNO" >
					<div class="columns clearfix">
						<div class="col_100">
							<fieldset >
								<label class="campoObrigatorio"><i18n:message key="problema.titulo" /></label>
								<div >
									<input type='text' id="tituloSolCon" name="tituloSolCon" maxlength="120"  />
								</div>
							</fieldset>
						</div>
					<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio" id="rotuloSolucaoContornoN"><i18n:message key="citcorpore.comum.descricao"/></label>
							<div>
							<textarea id="descSolCon" name="descSolCon" maxlength="700" style="height: 150px; width: 99%;" class="Description[citcorpore.comum.descricao]"></textarea>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="col_100">
					<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
					 <button type='button' name='btnGravarNotificacao' class="light"
						onclick='gravarSolContorno();'>
						<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
						<span><i18n:message key="citcorpore.comum.gravar" />
						</span>
					</button>
					<button type='button' name='btnGravarFechar' class="light"
						onclick='fecharSolContorno();'>
						<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
						<span><i18n:message key="citcorpore.comum.fechar" />
						</span>
					</button>
				</fieldset>
				</div>
			</div>
			<!-- Solução de Definitiva -->
			<div id="POPUP_SOLUCAO_DEFINITIVA" >
					<div class="columns clearfix">
						<div class="col_100">
							<fieldset >
								<label class="campoObrigatorio"><i18n:message key="problema.titulo" /></label>
								<div >
									<input type='text' id="tituloSolDefinitiva" name="tituloSolDefinitiva" maxlength="120"  />
								</div>
							</fieldset>
						</div>
					<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio" id="rotuloSolucaoDefinitiva"><i18n:message key="citcorpore.comum.descricao"/></label>
							<div>
							<textarea id="descSolDefinitiva" name="descSolDefinitiva" maxlength="700" style="height: 150px; width: 99%;" class="Description[citcorpore.comum.descricao]"></textarea>
							</div>
						</fieldset>
					</div>
				</div>
				<div class="col_100">
					<fieldset style="padding-top: 10px; padding-bottom: 10px; padding-left: 10px;">
					 <button type='button' name='btnGravarSolDefinitiva' class="light"
						onclick='gravarSolDefinitiva();'>
						<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
						<span><i18n:message key="citcorpore.comum.gravar" />
						</span>
					</button>
					<button type='button' name='btnGravarFecharDef' class="light"
						onclick='fecharSolDefinitiva();'>
						<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
						<span><i18n:message key="citcorpore.comum.fechar" />
						</span>
					</button>
				</fieldset>
				</div>
			</div>		
	
		</form>
	</div>		
	</div>
	</div>
	<!-- POPUPS -->
	<div id="popupServicosRelacionados" class="popup">
	
	</div>
	
	<!-- MAPA DESENHO SERVIÇO -->
	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>
	
	<!-- LOOKUPS -->
	
	
	<div id="POPUP_SOLICITACAOSERVICO" title="<i18n:message key="solicitacaoServico.solicitacao" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
					<div  align="right">
							<label  style="cursor: pointer; ">
									<i18n:message key="solicitacaoServico.solicitacao" />
									<img id='botaoSolicitante' src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrirPopupNovaSolicitacaoServico();">
							</label>
						</div>
						<form name='formPesquisaSolicitacaoServico' style="width: 540px">
							<cit:findField formName='formPesquisaSolicitacaoServico'
								lockupName='LOOKUP_SOLICITACAOSERVICO' id='LOOKUP_SOLICITACAOSERVICO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	
	<div id="POPUPITEMCONFIGURACAO" style=""  title="Pesquisa Itens Configuração" class="POPUP_LOOKUP">
 		<table >
			<tr>
				<td>
					<h3><i18n:message key="problema.itens_configuracao"/></h3>
				</td>
			</tr>
		</table>				
		<form name='formPesquisaItem' style="width: 95%;">
			<cit:findField formName='formPesquisaItem' 
						   lockupName='LOOKUP_ITEMCONFIGURACAO_ATIVO' 
						   id='LOOKUP_ITEMCONFIGURACAO' 
						   top='0' left='0' len='550' 
						   heigth='400' 	
						   javascriptCode='true' htmlCode='true' />
		</form>
	</div>
	
	<div id="POPUP_REQUISICAOMUDANCA" style=""  title=<i18n:message key="requisicaMudanca.mudanca"/> class="POPUP_LOOKUP">
 		<table >
			<tr>
				<td>
					<h3><i18n:message key="requisicaMudanca.mudanca"/></h3>
				</td>
			</tr>
		</table>				
		<form name='formPesquisaRequisicaoMudanca' style="width: 95%;">
			<cit:findField formName='formPesquisaRequisicaoMudanca' 
						   lockupName='LOOKUP_MUDANCA' 
						   id='LOOKUP_MUDANCA' 
						   top='0' left='0' len='550' 
						   heigth='400' 	
						   javascriptCode='true' htmlCode='true' />
		</form>
	</div>	
	<div id="POPUP_SOLICITANTE" title="<i18n:message key="citcorpore.comum.pesquisacolaborador" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
					<div  align="right">
							<label  style="cursor: pointer; ">
									<i18n:message key="solicitacaoServico.solicitante" />
									<img id='botaoSolicitante' src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="chamaPopupCadastroSol()">
									<img id="btHistoricoSolicitante" style="cursor: pointer; display: none;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png">	
							</label>
						</div>
						<form name='formPesquisaColaborador' style="width: 540px">
							<cit:findField formName='formPesquisaColaborador'
								lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOL_CONTRATO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_EMPREGADO" title="Pesquisa Colaborador" class="POPUP_LOOKUP">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaEmpregado' style="width: 540px">
							<cit:findField formName='formPesquisaEmpregado'
								lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="POPUP_SERVICO" style="" title="Pesquisa Serviço" class="POPUP_LOOKUP">
 			<table >
				<tr>
					<td>
						<h3 align="center">
							<i18n:message key="problema.servico" />
						</h3>
					</td>
				</tr>
			</table>
				
			<form name='formPesquisa' style="width: 95%;">
				<cit:findField formName='formPesquisa' 
							   lockupName='LOOKUP_SERVICO' 
							   id='LOOKUP_SERVICO' 
							   top='0' left='0' len='550' 
							   heigth='400' 
							   javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		
		<div id="POPUP_INFO_INSERCAO" title="" style="overflow: hidden;">
			<div class="toggle_container">
				<div id='divMensagemInsercao' class="section" style="overflow: hidden; font-size: 24px;">
					
				</div>
				<button type="button" onclick='$("#POPUP_INFO_INSERCAO").dialog("close")'>
					<i18n:message key="citcorpore.comum.fechar" />
				</button>
			</div>
		</div>
		
		<div id="POPUP_BASECONHECIMENTO"  style="overflow: hidden;" title="<i18n:message key="baseConhecimento.baseConhecimento"/>">
		<iframe id='iframeBaseConhecimento' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>
		</div>
		
		<div  id="POPUP_ITEMCONFIGURACAO" title="<i18n:message key='itemConfiguracao.itemConfiguracao' /> ">
		<iframe  id="iframeItemConfiguracao" name="iframeItemConfiguracao" width="100%" height="100%"> </iframe>
		</div>
		
		<!-- Thiago Fernandes - 01/11/2013 08:30 - Sol. 121468 - Popup para cadastro nova solicitação serviço.. -->
		<div id="POPUP_NOVASOLICITACAOSERVICO"  style="overflow: hidden;" title="<i18n:message key="solicitacaoServico.solicitacao"/>">
			<iframe id='iframeNovaSolicitacao' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
			</iframe>		
		</div>
		
		<div id="POPUP_PESQUISAITEMCONFIGURACAO" style="overflow: hidden;" title="<i18n:message key="itemConfiguracao.pesquisa" />">
			<iframe id='framePesquisaItemConfiguracao' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
			</iframe>
		</div>
</body>
</html>

