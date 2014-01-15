<%@page import="br.com.centralit.citcorpore.bean.RequisicaoMudancaDTO"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@taglib uri="/tags/cit" prefix="cit" %>
<%@taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<%@page import="org.apache.commons.lang.StringEscapeUtils"%>
<%@page import="br.com.centralit.citcorpore.bean.HistoricoMudancaDTO"%>
<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Mudar a maneira de chamar a poupup de cadastro para novo colaborador e poder fechala pelo o seu id. Da linha 2047 á 2065 ,2969, e da linha 3118 á 3122. -->
<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Setar mascar no campo telefoneContato, linha 424. -->

<%
	/*response.setCharacterEncoding("ISO-8859-1");*/
	String id = request.getParameter("idBaseConhecimento");
	response.setHeader("Pragma", "no-cache");
	response.setHeader("Cache-Control", "no-cache");
	response.setDateHeader("Expires", -1);
	int idRequisicaoMudanca = 0;
	if (request.getParameter("idRequisicaoMudanca") != null) {
		idRequisicaoMudanca = (Integer) request
				.getAttribute("idRequisicaoMudanca");
	}

	String strRegistrosExecucao = (String) request
			.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}
	/*Só vai aparecer se for feito a pesquisa, se já esta aprovado ou não, caso ao contrario não aparecera nenhum dos dois.*/

	String faseMudancaRequisicao = "";
	if (request.getAttribute("faseMudancaRequisicao") != null) {
		faseMudancaRequisicao = (String) request
				.getAttribute("faseMudancaRequisicao");
	}
%>

<% 
/**
* Motivo: Verifica se a tela é de visualização
* Autor: luiz.borges	
* Data/Hora: 10/12/2013 15:41
*/
	String display = "";
	String editar = "false";
	if(request.getParameter("editar") != null && request.getParameter("editar").toString().equalsIgnoreCase("N")){ 
		display = "display:none";
		editar = "true";
		request.getSession().setAttribute("disable", "true");
	}else
	{
		request.getSession().setAttribute("disable", "false");
	}
%>

<!doctype html public "">
<html>
<head>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/security/security.jsp" %>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
    <%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoMudancaResponsavelDTO.js"></script>
	<script type="text/javascript" src="../../cit/objects/HistoricoMudancaDTO.js"></script>

    <script>var URL_INITIAL = '<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/';</script>
    <%
    	String tarefaAssociada = (String) request
    			.getAttribute("tarefaAssociada");
    	if (tarefaAssociada == null) {
    		tarefaAssociada = "N";
    	}
    %>
    <link type="text/css" rel="stylesheet" class="include" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/jquery.jqplot.min.css" />
    
    
    <title><i18n:message key="citcorpore.comum.title"/></title>
	
	 <style type="text/css">
	 
	 	#btOk  {
			position: absolute; top: 50%; right: 6px;+
		}
	 
	    .hidden {
	        display: none !important;
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
	
	.tableLess tbody {
	  background: transparent  !important;
	}
	
	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}
	
	.tableLess thead th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}
	
	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}
	
	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9 ;
	  cursor: pointer;
	}
	
	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}
	
	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}
	
	.sel {
		display: none;
		background: none  !important;;
		cursor:auto;
		padding: 0;
		margin: 0;
	}
	.sel td {
		padding: 0;
		margin: 0;
	}
	
		.table {
		
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
		}
		
		.tabFormulario tr{
		
			width: 50%;
			
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
		
		
		.col_98 {
		
			width: 98%;
			
		}
		
		.col_17 {
			width: 17%;
				position: relative;
				
				margin-right: -1px;
				display: block;
				float: left;
		}
		
		.col_13 {
			width: 14%;
				position: relative;
				
				margin-right: -1px;
				display: block;
				float: left;
		}
		
		.col_36 {
			width: 36%;
				position: relative;				
				margin-right: -1px;
				display: block;
				float: left;
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
			padding: 5px;			
		}
		
		.formFooter{
		
			float: left; 
			width: 99%; 
			 
			padding: 18px;
				
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
		
			background-color: #fff;
			
		}
	
		
		.ui-state-hover{
		
			background-color: #ccc !important;
				
		}
		
		#divTituloSolicitacao {
		
			text-align: center;
			
		}
		
		#contentBaseline {
			padding: 0 !important;
			margin: 0 !important;
			border: 0 !important;
		}

    </style>
	
	<!-- SlickGrid and its dependancies (not sure what they're for?) --->
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.rule-1.0.1.1-min.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.event.drag.custom.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/slick.core.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/slick.editors.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/slick.grid.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery-ui-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/debug.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/uniform/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/autogrow/jquery.autogrowtextarea.js"></script> 
	 
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/multiselect/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/selectbox/jquery.selectBox.min.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/timepicker/jquery.timepicker.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/colorpicker/js/colorpicker.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/validation/jquery.validate.min.js"></script>		
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/uitotop/js/jquery.ui.totop.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/custom/ui.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/js/custom/forms.js"></script>	
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script> 
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script> 
	<script class="include" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/jquery.jqplot.min.js"></script>
	<script class="include" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>	
	
    <script type="text/javascript">
   	    
	    $.fx.speeds._default = 1000;
		var myLayout;
		var popupManager;
		var LOOKUP_EMPREGADO_select;
		var popupManagerSolicitacaoServico;
		var cadastroRisco;
		
		var slickGridColunas;
		var slickGridOptions;
		var slickGridTabela;

		var tabelaRelacionamentoICs;
		var tabelaRelacionamentoServicos;
		var tabelaRelacionamentoLiberacao;
		var tabelaProblema;
		var tabelaRelacionamentoSolicitacaoServico;
		var tabelaRisco;
		var tabelaGrupo;
		
		var count = 0;
		var popup2;
		
		var idItemConfiguracao;
		var descricaoItemConfiguracao;
		var descricaoTratada;
		
	
		$(document).ready(function(){
		  $('#horaAgendamentoFinal').mask('99:99');
		  $('#horaAgendamentoInicial').mask('99:99');
		  $('#telefoneContato').mask('(999) 9999-9999');
		});
		
		function zerarContadores(){
			 count = 0 ;
		}
		
		fecharInformacoesItemConfiguracao = function(){
			$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog( 'close' );	
		};
		
		/*Gravar baseline*/
		function gravarBaseline() {
			var tabela = document.getElementById('tblBaselines');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var baselines = new Array();

			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idHistoricoMudanca' + i);	
				if (!trObj) {
					continue;
				}	
				baselines[contadorAux] = getbaseline(i);
				contadorAux = contadorAux + 1;
			}
			serializaBaseline();
			document.form.fireEvent("saveBaseline");
			document.form.fireEvent("restoreColaboradorSolicitante");
		}
		
		var seqBaseline = '';
		var aux = '';
		serializaBaseline = function() {
			var tabela = document.getElementById('tblBaselines');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var baselines = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idHistoricoMudanca' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					baselines[contadorAux] = getbaseline(i);
					contadorAux = contadorAux + 1;
					continue;
				}	
				
			}
			var baselinesSerializadas = ObjectUtils.serializeObjects(baselines);
			document.form.baselinesSerializadas.value = baselinesSerializadas;
			return true;
		}

		getbaseline = function(seq) {
			var HistoricoMudancaDTO = new CIT_HistoricoMudancaDTO();
			HistoricoMudancaDTO.sequencia = seq;
			HistoricoMudancaDTO.idHistoricoMudanca = eval('document.form.idHistoricoMudanca' + seq + '.value');
			return HistoricoMudancaDTO;
		}
		function marcarCheckbox(elementos){
			var arrayIds = new Array();
			arrayIds = elementos;
			for ( var i = 0; i <= arrayIds.length; i++) {
				var posicao = arrayIds[i];
				$("#posicao").attr("checked",true);
			}
			
		}

		function restaurarHistorico(id){
			document.form.idHistoricoMudanca.value = id;
			if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
				document.form.fireEvent("restaurarBaseline");
		}
		 
		$(document).ready(function () {
	    		initPopups();
	    		initTabelasRelacionamentos();
	    		
	    		/* para visualização rápida do mapaDesenhoServico */
	    		popupManager = new PopupManager(window.screen.width - 100 , window.screen.height - 100, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	    		/* solicitcaoservico */
	    		popupManagerSolicitacaoServico = new PopupManager(window.screen.width - 100 , window.screen.height - 100, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	    		/* popup do solicitante */
	    		popup2 = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");

	    		cadastroRisco = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");

	    		
	    		document.form.afterRestore = function() {
	    			$('.tabs').tabs('select', 0);
	    		}

	    		<!-- Murilo Almeida Pacheco - 25/10/2013 09:55 - Sol. 122294 - Popup abrindo com barra de rolagem sem necessidade alterando a altura da popup. -->	    		
	    		$("#POPUP_SOLICITANTE").dialog({
					autoOpen : false,
					width : 570,
					height : 730,
					modal : true
				});
	    		
	    		$("#POPUP_RESPONSAVEL").dialog({
	    			autoOpen : false,
	    			width : 875,
	    			height : 680,
	    			modal : true,
	    			show: "fade",
	    			hide: "fade"
	    		});	
	    		
	    		$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog({
	    			title: '<i18n:message key="citcorpore.comum.visao" />',
	    			width: 900,
	    			height: 600,
	    			modal: true,
	    			autoOpen: false,
	    			resizable: false,
	    			show: "fade",
	    			hide: "fade"
	    			}); 
	    		
	    		$("#POPUP_INFORMACOESITEMCONFIGURACAO").hide();
	    		
	    });

		/* add responsavel */
	    function addResponsavel(id, nome, cargo, tel, email ){
	        var obj = new CIT_RequisicaoMudancaResponsavelDTO();

			<!-- Murilo Almeida Pacheco - 25/10/2013 11:05 - Sol. 122294 - Ao adicionar o responsável em papeis e responsabilidades estava vindo undefined fiz o tratamento para não aparecer mais assim. -->
	        if((cargo == "") || (cargo == undefined)){
	    		cargo = "N/A"
	    	}
	    	
	        if((tel == "") || (tel == "undefined-undefined")){
	        	tel = "N/A"
	    	}
	    	
	        if((email == "") || (email == undefined)){
	        	email = "N/A"
	    	}
	    	
	        
	        document.getElementById('responsavel#idResponsavel').value = id;
	        document.getElementById('responsavel#nomeResponsavel').value = nome;
	        document.getElementById('responsavel#nomeCargo').value = cargo;  
	        document.getElementById('responsavel#telResponsavel').value = tel;
	        document.getElementById('responsavel#emailResponsavel').value = email;
	        document.getElementById('responsavel#papelResponsavel').value = prompt(i18n_message("citcorpore.comum.papel"),"");
	        
	        HTMLUtils.addRow('tblResponsavel', document.form, 'responsavel', obj,
	                ["","idResponsavel","nomeResponsavel", "nomeCargo", "telResponsavel", "emailResponsavel" , "papelResponsavel" ], ["idResponsavel"], null, [gerarImgDelResponsavel], null, null, false);
	    	$("#POPUP_RESPONSAVEL").dialog("close");
		}
		
	    function adicionarResponsavel(){
			$("#POPUP_RESPONSAVEL").dialog("open");		
		}
		
	   function verificaIdSolicitacaoNaURL(){
	    	var idRequisicao = extrairVariavelDaUrl("idRequisicao");
			
   		 	if(idRequisicao != null && idRequisicao != ""){
	    		restaurarRequisicao(idRequisicao)
   		 	}
	   } 
	   
	   function mostraMensagemInsercao(msg){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}
	   
	   $(function() {
			$("#addSolicitante").click(function() {
				if (document.form.idContrato.value == ''){
					alert(i18n_message("contrato.alerta.informe_contrato"));
					return;
				}
				/* var v = document.getElementsByName("btnTodosLOOKUP_SOL_CONTRATO");
				v[0].style.display = 'none'; */
				var y = document.getElementsByName("btnLimparLOOKUP_SOL_CONTRATO");
				y[0].style.display = 'none'; 	
				$("#POPUP_SOLICITANTE").dialog("open");
			});
		});

	   function pesquisarAD(){
			JANELA_AGUARDE_MENU.show();
			document.form.filtroADPesq.value = $("#filtroADPesqAux").val();
			if($("#filtroADPesqAux").val() == ""){
				alert(i18n_message("login.digite_login"));
				$("#filtroADPesqAux").focus();
				JANELA_AGUARDE_MENU.hide();
				return;
			}
			if($("#idContrato").val() == ""){
				alert(i18n_message("ss.escolhaContrato"));
				$("#idContrato").focus();
				JANELA_AGUARDE_MENU.hide();
				return;
			}
			document.form.fireEvent("sincronizaAD");
		}
		function initTabelasRelacionamentos(){
			var display = "";
			display = '<%=display%>';
			/* ICs */
			tabelaRelacionamentoICs = new CITTable("tblICs",["idItemConfiguracao", "nomeItemConfiguracao", "descricao"],[]);
			tabelaRelacionamentoICs.setInsereBotaoInformacoes(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/order.png");
			if(display == ""){
				tabelaRelacionamentoICs.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}else{
				tabelaRelacionamentoICs.setInsereBotaoExcluir(false, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}
			
			/* Solicitacaoservico */
			tabelaRelacionamentoSolicitacaoServico = new CITTable("tblSolicitacaoServico",["idSolicitacaoServico", "nomeServico"],[]);
			if(display == ""){
				tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}else{
				tabelaRelacionamentoSolicitacaoServico.setInsereBotaoExcluir(false, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}
			
			/* servicos */
			tabelaRelacionamentoServicos = new CITTable("tblServicos",["idServico", "Nome", "Mapa", "Descrição"],[]);
			if(display == ""){
				tabelaRelacionamentoServicos.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}else{
				tabelaRelacionamentoServicos.setInsereBotaoExcluir(false, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}
			
			/* problemas */
			tabelaProblema = new CITTable("tblProblema",["idProblema", "titulo", "status","Editar"],[]);
			if(display == ""){
				tabelaProblema.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}else{
				tabelaProblema.setInsereBotaoExcluir(false, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}
			
			/* Liberação */
			tabelaRelacionamentoLiberacao = new CITTable("tblLiberacao",["idLiberacao", "titulo", "descricao","status"],[]);
			if(display == ""){
				tabelaRelacionamentoLiberacao.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}else{
				tabelaRelacionamentoLiberacao.setInsereBotaoExcluir(false, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}
			
			/* Risco */
			tabelaRisco = new CITTable("tblRisco",["idRisco", "nomeRisco", "detalhamento"],[]);
			if(display == ""){
				tabelaRisco.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}else{
				tabelaRisco.setInsereBotaoExcluir(false, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}
			
		    /* Grupo */
			tabelaGrupo = new CITTable("tblGrupo",["idGrupo", "nomeGrupo"],[]);
			if(display == ""){
				tabelaGrupo.setInsereBotaoExcluir(true, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}else{
				tabelaGrupo.setInsereBotaoExcluir(false, "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png");
			}
		}
		
	    function initTextEditor(editor){
	    	editor.BasePath = '<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
	      	editor.Config['ToolbarStartExpanded'] = false;
	      	editor.Width = '100%';
	      	editor.ReplaceTextarea();
	    }
	    
	    /**
		* Motivo: Redimenciona a popup em tamanho compativel com o tamanho da tela
		* Autor: flavio.santana
		* Data/Hora: 02/11/2013 15:35
		*/
		function redimensionarTamhanho(identificador, tipo_variacao){
			var h;
			var w;
			switch(tipo_variacao)
			{
			case "PEQUENO":
				w = parseInt($(window).width() * 0.25);
				h = parseInt($(window).height() * 0.35);
			  break;
			case "MEDIO":
				w = parseInt($(window).width() * 0.5);
				h = parseInt($(window).height() * 0.6);
			  break;
			case "GRANDE":
				w = parseInt($(window).width() * 0.75);
				h = parseInt($(window).height() * 0.85);
			  break;
			default:
				w = parseInt($(window).width() * 0.5);
				h = parseInt($(window).height() * 0.6);
			}
			
			$(identificador).dialog("option","width", w)
			$(identificador).dialog("option","height", h)
		}
	    
		function initPopups(){
			$(".POPUP_LOOKUP").dialog({
				autoOpen : false,
				width : 750,
				height : 700,
				modal : true
			});	
			
			$(".POPUP_LOOKUP_ITEMCONFIGURACAO").dialog({
				autoOpen : false,
				width : 400,
				height : 200,
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
			$("#POPUP_INFO_BASELINE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true,
				close: function(event, ui) {
				}
			});
			$(".POPUP_LOOKUP_SERVICO").dialog({
				autoOpen : false,
				width : 1000,
				height : 700,
				modal : true
			});
			$(".POPUP_LOOKUP_ICS").dialog({
				autoOpen : false,
				width : 850,
				height : 700,
				modal : true
			});
			$(".POPUP_LOOKUP_RISCO").dialog({
				autoOpen : false,
				width : 770,
				height : 650,
				modal : true
			});
			$(".POPUP_LOOKUP_LIBERACAO").dialog({
				autoOpen : false,
				width : 910,
				height : 650,
				modal : true
			});	
		}
		
		function LOOKUP_SOL_CONTRATO_select(id, desc){
			document.form.idSolicitante.value = id;
			document.form.fireEvent("restoreColaboradorSolicitante");
		}
		
		function setaValorLookup(obj){
			document.form.idSolicitante.value = '';
			document.form.nomeSolicitante.value = '';
			document.form.emailSolicitante.value = '';
 			document.form.nomeContato.value = '';
			document.form.telefoneContato.value = '';
			document.form.observacao.value = '';
			document.form.ramal.value = '';
			document.getElementById('idLocalidade').options.length = 0;
			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = '';
			document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = obj.value; 
		}
		

		/*
		 * Funções de apoio
		 */		

		 function extrairVariavelDaUrl(nome){
			var valor = null;
			var identificador = null;
			try{
				var strUrl = document.URL;
				var params = strUrl.split("?")[1];
				var variaveis = params.split("&");
								for(var i = 0; i < variaveis.length; i++){
					valor = variaveis[i].split("=")[1]; 			
					identificador = variaveis[i].split("=")[0];
					
					if(identificador == nome){
						return valor; 
					}
					
					valor = null;
				}
			}catch(e){}

			return null;
		}

		function gerarImgDelResponsavel(row, obj){
		        row.cells[0].innerHTML = '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" style="cursor: pointer;" onclick="deleteLinha(\'tblResponsavel\', this.parentNode.parentNode.rowIndex);"/>';
		};
		
		function deleteLinha(table, index){
			if (confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
				teste = 'true';
				HTMLUtils.deleteRow(table, index);
				return;
			}
			teste = '';
		}
		    
		function serializaResponsavel(){
	    	var responsavel = HTMLUtils.getObjectsByTableId('tblResponsavel');
			document.form.responsavel_serialize.value =  ObjectUtils.serializeObjects(responsavel);
			}
		    
		function serializaProblema(){
	   		/* Serializando para pode trabalhar com quantidade atual de linhas na grid */
			document.form.problemaSerializado.value = ObjectUtils.serializeObjects(tabelaProblema.getTableObjects()); 
		}
		
		function limparFCKEditores(){
			var fckEditorAux;
			var textAreaList = document.getElementsByTagName("textarea");

			for(var i = 0; i < textAreaList.length; i++){
				if(textAreaList[i].id != null){
					
					fckEditorAux = FCKeditorAPI.GetInstance( textAreaList[i].id );
					
					if(fckEditorAux != null){
						try{
							fckEditorAux.SetData("");
						}catch(e){
							/* alert("Problemas com FCKEditor. " + e.message) */
						}
					} 					
				}	
			}	
		}
		
		function restaurarHistorico(id){
			document.form.idHistoricoMudanca.value = id;
			if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
				document.form.fireEvent("restaurarBaseline");
		}
	 	function gravarEContinuar() {
			document.form.acaoFluxo.value = '<%=Enumerados.ACAO_INICIAR%>';
			gravar();

	    }
	    
	    function gravarEFinalizar() {
			document.form.acaoFluxo.value = '<%=Enumerados.ACAO_EXECUTAR%>';
			document.form.fecharItensRelacionados.value = "N";
			if(document.form.fase.value == 'Avaliacao'){
				verificarItensRelacionados(true)
			}else{
				gravar();
			}
	    }

	 	function verificarItensRelacionados(validarItem){
			if(validarItem){
				document.form.fireEvent("verificarItensRelacionados");
				document.form.fecharItensRelacionados.value = "N";
		   }else{
			   if(confirm(i18n_message("citcorpore.comum.fecharItemRelacionados"))){
					document.form.fecharItensRelacionados.value = "S";
					gravar();
				}else{
					document.form.fecharItensRelacionados.value = "N";
					gravar();
				}
		    }
		}

		function limpaListasRelacionamentos(){
			tabelaRelacionamentoICs.limpaLista();
			tabelaRelacionamentoServicos.limpaLista();
			tabelaProblema.limpaLista();
			tabelaRelacionamentoSolicitacaoServico.limpaLista();
			tabelaRelacionamentoLiberacao.limpaLista();
			tabelaRisco.limpaLista();
			tabelaGrupo.limpaLista();
			
		}

		/** INFLUENCIA PRIORIDADE */
		function atualizaPrioridade(){
			
			var impacto = document.getElementById('nivelImpacto').value;
			var urgencia = document.getElementById('nivelUrgencia').value;
			
			if (urgencia == "B"){
				if (impacto == "B"){
					document.form.prioridade.value = 5;
				}else if (impacto == "M"){
					document.form.prioridade.value = 4;
				}else if (impacto == "A"){
					document.form.prioridade.value = 3;
				}
			}
			
			
			if (urgencia == "M"){
				if (impacto == "B"){
					document.form.prioridade.value = 4;
				}else if (impacto == "M"){
					document.form.prioridade.value = 3;
				}else if (impacto == "A"){
					document.form.prioridade.value = 2;
				}
			}
			
			if (urgencia == "A"){
				if (impacto == "B"){
					document.form.prioridade.value = 3;
				}else if (impacto == "M"){
					document.form.prioridade.value = 2;
				}else if (impacto == "A"){
					document.form.prioridade.value = 1;
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
			
			limparFCKEditores();
			limpaListasRelacionamentos();			
		}

		function validarDatas(){
			var inputs = document.getElementsByClassName("datepicker");
			var input = null;
			var errorMsg = i18n_message("citcorpore.comum.dataNaoDeveSerInferiorAtual") ;
			
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
		
		function gerarRelatorioPDF() {
			document.form.fireEvent("imprimirRelatorioReqMudanca");
		}
		
		/**
		 * Ajusta dados dos textareas com fckeditor antes de gravar.
		 */
		function gravar(){
			var statusAtual;
			
			for (var i = 0; i < document.getElementsByName('status').length; i++)
		    {
		    	if (document.getElementsByName('status')[i].checked)
		    	{
		    		statusAtual = document.getElementsByName('status')[i].value;
		    	}
		    }
			
			if (statusAtual == "Concluida"){
				if (document.form.fechamento.value == '' || document.form.fechamento.value == ' '){
					alert(i18n_message("citcorpore.comum.informeFechamento"));
					return;				
				}
			}
			
			if (document.getElementById('dataHoraInicioAgendada').value != '' && document.getElementById('horaAgendamentoInicial').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoHoraInicialAgendada"));
				document.getElementById('horaAgendamentoInicial').focus();
				return;
			}
			
			if (document.getElementById('dataHoraInicioAgendada').value == '' && document.getElementById('horaAgendamentoInicial').value != '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataInicioAgendada"));
				document.getElementById('dataHoraInicioAgendada').focus();
				return;
			}
			
			if (document.getElementById('dataHoraTerminoAgendada').value != '' && document.getElementById('horaAgendamentoFinal').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoHoraFinalAgendada"));
				document.getElementById('horaAgendamentoFinal').focus();
				return;
			}
			
			if (document.getElementById('dataHoraTerminoAgendada').value == '' && document.getElementById('horaAgendamentoFinal').value != '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataTerminoAgendada"));
				document.getElementById('dataHoraTerminoAgendada').focus();
				return;
			}
			
			if (document.getElementById('dataHoraInicioAgendada').value != '' && document.getElementById('dataHoraTerminoAgendada').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataTerminoAgendada"));
				document.getElementById('dataHoraTerminoAgendada').focus();
				return;
			}
			
			if (document.getElementById('dataHoraTerminoAgendada').value != '' && document.getElementById('dataHoraInicioAgendada').value == '') {
				alert(i18n_message("requisicaoMudanca.informacaoDataInicioAgendada"));
				document.getElementById('dataHoraInicioAgendada').focus();
				return;
			}
			
			<%if (id != null) {%>
			document.form.idBaseConhecimento.value = <%=id%>;
			<%}%>
			document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());
			document.form.servicosRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoServicos.getTableObjects());
			document.form.problemaSerializado.value = ObjectUtils.serializeObjects(tabelaProblema.getTableObjects());
			document.form.solicitacaoServicoSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoSolicitacaoServico.getTableObjects());
			document.form.liberacoesRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoLiberacao.getTableObjects());
			document.form.riscoSerializado.value = ObjectUtils.serializeObjects(tabelaRisco.getTableObjects());
			document.form.grupoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaGrupo.getTableObjects());
			
			serializaResponsavel();
			serializaAprovacoesProposta();
			serializaAprovacoesMudanca();

			var informacoesComplementares_serialize = '';
			try{
				informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
			}catch(e){}
			if (document.form.informacoesComplementares_serialize != undefined) {
				document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
			}
			
			$("#contato").prop("disabled", false);
			$("#idGrupoComite").prop("disabled", false);
			$("#idGrupoAtual").prop("disabled", false);
			$("#idTipoMudanca").prop("disabled", false);
			$("#addSolicitante").prop("disabled", false);
			$("#idContrato").prop("disabled", false);
			
			validarDatas();
			
			document.form.save();
		}
		
		
		function restaurar(){
			var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
			var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);
			var listaServicos = ObjectUtils.deserializeCollectionFromString(document.form.servicosRelacionadosSerializado.value);
			var listaProblema = ObjectUtils.deserializeCollectionFromString(document.form.problemaSerializado.value);
			var listaLiberacoes = ObjectUtils.deserializeCollectionFromString(document.form.liberacoesRelacionadosSerializado.value);
			var listaRisco = ObjectUtils.deserializeCollectionFromString(document.form.riscoSerializado.value);
			var listaGrupo = ObjectUtils.deserializeCollectionFromString(document.form.grupoMudancaSerializado.value);
			limpaListasRelacionamentos();
			
			for(var i = 0; i < listaICs.length; i++){
				tabelaRelacionamentoICs.addObject([listaICs[i].idItemConfiguracao, listaICs[i].nomeItemConfiguracao , listaICs[i].descricao]);
			}

			for(var i = 0; i < listaServicos.length; i++){
				tabelaRelacionamentoServicos.addObject([listaServicos[i].idServico, listaServicos[i].nome , listaServicos[i].descricao, getBotaoVisualizarMapa(listaServicos[i].idServico)]);
			}
			
			for(var i = 0; i < listaLiberacoes.length; i++){
				tabelaRelacionamentoLiberacao.addObject([listaLiberacoes[i].idLiberacao, listaLiberacoes[i].titulo , listaLiberacoes[i].descricao,listaLiberacoes[i].status]);
			}
			
			 if(listaSolicitacaoServico.length > 0){
				for(var i = 0; i < listaSolicitacaoServico.length; i++){
					tabelaRelacionamentoSolicitacaoServico.addObject([listaSolicitacaoServico[i].idSolicitacaoServico, listaSolicitacaoServico[i].nomeServico]);
				}
			} 
			 
			 if(listaProblema.length > 0){
				for(var i = 0; i < listaProblema.length; i++){
					tabelaProblema.addObject([listaProblema[i].idProblema, listaProblema[i].titulo, listaProblema[i].status, getBotaoEditarProblema(listaProblema[i].idProblema)]);
				}
			} 	
			 
			 if(listaRisco.length > 0){
				for(var i = 0; i < listaRisco.length; i++){
					tabelaRisco.addObject([listaRisco[i].idRisco, listaRisco[i].nomeRisco, listaRisco[i].detalhamento]);
				}
			}
			 if(listaGrupo.length > 0){
					for(var i = 0; i < listaGrupo.length; i++){
						tabelaGrupo.addObject([listaGrupo[i].idGrupo, listaGrupo[i].nomeGrupo]);
					}
				}
		}
		
			
		function deletar(){
			document.form.fireEvent("delete");
		}

		/** Ajusta dados dos textareas com fckeditor ao restaurar. */
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
		function selecionarSolicitante(){
			LOOKUP_EMPREGADO_select =  function (id, desc){
				document.form.idSolicitante.value = id;
				document.form.nomeSolicitante.value = desc.split("-")[0];
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
		/*      -------------      */
		
		function restaurarRequisicao(idRequisicao){
			document.form.idRequisicaoMudanca.value = idRequisicao;
			document.form.fireEvent("restore");
		}
		
		/*
		 * Funções de relacionamento
		 */

		 /*
		 * Adicionado por David, para validar a aba de item de configuração
		 */
		function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
			idItemConfiguracao = id;
			descricaoItemConfiguracao = desc;
			addLinhaTabelaItemConfiguracao(id, desc, true);

		}
		
		function addLinhaTabelaItemConfiguracao(id, desc, valida){
			var resultado = true;;
			var tbl = document.getElementById('tblICs');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				resultado = validaAddLinhaTabelaItemConfiguracao(lastRow, id);
				if (!resultado){
					return resultado;
				}
			}
			return resultado;
			
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

		
		function abrePopupIcs(){
			redimensionarTamhanho("#POPUPITEMCONFIGURACAO", "MEDIO");
			$("#POPUPITEMCONFIGURACAO").dialog("open");
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
			idItemConfiguracao = id;
			document.getElementById('hiddenIdItemConfiguracao').value = id;
			var tbl = document.getElementById('tblICs');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			var resultado = true;
			resultado = validaAddLinhaTabelaItemConfiguracao(lastRow, id);
			
			if (resultado == true){
				abrePopupDescricaoIcs();
			}else{
				return;
			}
		}
		
		function abrePopupDescricaoIcs(){
			redimensionarTamhanho("#POPUPDESCRICAOITEMCONFIGURACAO", "PEQUENO");
			$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("open");
		}
		
		function adicionarIC(){			
			abrePopupIcs();
		}

		//adicionar servicos
		function LOOKUP_SERVICO_select(id, desc) {
			addLinhaTabelaServicos(id, desc, true);

		};
		
		function LOOKUP_RESPONSAVEL_select(id, desc){
	        var str = desc.split('-');
	        addResponsavel(id, str[0], str[1], str[2]+"-"+str[3], str[4]);
	    }
		
		function addLinhaTabelaServicos(id, desc, valida){
			var tbl = document.getElementById('tblServicos');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaServicos(lastRow, id)){
					return;
				}
			}
			
			var camposLookupServico = desc.split("-");
			tabelaRelacionamentoServicos.addObject([id, camposLookupServico[0], camposLookupServico[1], getBotaoVisualizarMapa(id)]);	
			
			document.form.servicosRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoServicos.getTableObjects());
			
			$("#POPUP_SERVICO").dialog("close");
			
		}
		
		function validaAddLinhaTabelaServicos(lastRow, id){
			var listaServicos = ObjectUtils.deserializeCollectionFromString(document.form.servicosRelacionadosSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaServicos.length; i++){
					if (listaServicos[i].idServico == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;		}
		
		//adicionar problema
		
		function LOOKUP_LIBERACAO_MUDANCA_select(id, desc) {
 			document.getElementById('liberacao#idRequisicaoLiberacao').value = id;
			document.form.fireEvent("inserirRequisicaoLiberacao");

		};
		
		//Adiciona a linha da liberação
		function adicionaLiberacaoMudanca(idLiberacao,titulo,descricao,status){
			
			//Faz a validação para verificar pelo id que o registro já está adicionado
			var tbl = document.getElementById('tblLiberacao');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			var valida = true;
			if (valida){
				if (!validaAddLinhaTabelaLiberacao(lastRow, idLiberacao)){
					return;
				}
			}
			
			tabelaRelacionamentoLiberacao.addObject([idLiberacao, titulo, descricao,status], [getBotaoVisualizarMapa(idLiberacao)]);	
			
			document.form.liberacoesRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoLiberacao.getTableObjects());
			
			$("#POPUP_LIBERACAO").dialog("close");
		}
		
// 		function LOOKUP_LIBERACAO_MUDANCA_select(id, desc) {
// 			addLinhaTabelaLiberacao(id, desc, true);

// 		};
		
// 		function addLinhaTabelaLiberacao(id, desc, valida){
// 			var tbl = document.getElementById('tblLiberacao');
// 			tbl.style.display = 'block';
// 			var lastRow = tbl.rows.length;
// 			if (valida){
// 				if (!validaAddLinhaTabelaLiberacao(lastRow, id)){
// 					return;
// 				}
// 			}
			
// 			var camposLookupLiberacao = desc.split("-");
// 			tabelaRelacionamentoLiberacao.addObject([id, camposLookupLiberacao[2], camposLookupLiberacao[3],camposLookupLiberacao[4],camposLookupLiberacao[5], getBotaoVisualizarMapa(id)]);	
			
// 			document.form.liberacoesRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoLiberacao.getTableObjects());
			
// 			$("#POPUP_LIBERACAO").dialog("close");
			
// 		}
		
		function validaAddLinhaTabelaLiberacao(lastRow, id){
			var listaLiberacoes = ObjectUtils.deserializeCollectionFromString(document.form.liberacoesRelacionadosSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaLiberacoes.length; i++){
					if (listaLiberacoes[i].idLiberacao == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;
		}
		
		//adicionar problema
		function LOOKUP_PROBLEMA_select(id, desc) {
			addLinhaTabelaProblema(id, desc, true);

		};
		
		function LOOKUP_GRUPO_select(id, desc) {
			addLinhaTabelaGrupo(id, desc, true);

		};
		
		function addLinhaTabelaProblema(id, desc, valida){
			var tbl = document.getElementById('tblProblema');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaProblema(lastRow, id)){
					return;
				}
			}
			
			desc = desc.replace(/['"]*/g, '');
			
			var camposLookupProblema = desc.split("-");
			tabelaProblema.addObject([id, camposLookupProblema[0], camposLookupProblema[1], getBotaoEditarProblema(id)]);
			
			document.form.problemaSerializado.value = ObjectUtils.serializeObjects(tabelaProblema.getTableObjects());
			
			$("#POPUP_PROBLEMA").dialog("close");
			
		}
		function validaAddLinhaTabelaProblema(lastRow, id){
			var listaProblema = ObjectUtils.deserializeCollectionFromString(document.form.problemaSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaProblema.length; i++){
					if (listaProblema[i].idProblema == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;
		}
		
		
		function addLinhaTabelaGrupo(id, desc, valida){
 			var tbl = document.getElementById('tblGrupo');
			tbl.style.display = 'block';
 			var lastRow = tbl.rows.length;
 			if (valida){
 				if (!validaAddLinhaTabelaGrupo(lastRow, id)){
 					return;
 				}
			}
			
 			desc = desc.replace(/['"]*/g, '');
			
 			var camposLookupGrupo = desc.split("-");
 			tabelaGrupo.addObject([id, camposLookupGrupo[0]]);
			
 			document.form.grupoMudancaSerializado.value = ObjectUtils.serializeObjects(tabelaGrupo.getTableObjects());
			
			$("#POPUP_GRUPO").dialog("close");
			
		}
		
		function validaAddLinhaTabelaGrupo(lastRow, id){
			var listaGrupo = ObjectUtils.deserializeCollectionFromString(document.form.grupoMudancaSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaGrupo.length; i++){
					if (listaGrupo[i].idGrupo == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;
		}
				

		function getBotaoVisualizarMapa(id){
			var botaoVisualizarMapa = new Image();

			botaoVisualizarMapa.src = '<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png';
			botaoVisualizarMapa.setAttribute("style", "cursor: pointer;");
			botaoVisualizarMapa.id = id;
			botaoVisualizarMapa.addEventListener("click", function(evt){
				if(popupManager == null){
					alert(i18n_message("requisicaoMudanca.popupNaoConfigurada"));
				} else {
					popupManager.abrePopupParms('mapaDesenhoServico', '', '&idServico=' + this.id);
				}
			}, true);

			return botaoVisualizarMapa;
		}
	
		//adicionar Risco
		function LOOKUP_RISCO_select(id, desc) {
			addLinhaTabelaRisco(id, desc, true);

		};
		
		function addLinhaTabelaRisco(id, desc, valida){
			var tbl = document.getElementById('tblRisco');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaRisco(lastRow, id)){
					return;
				}
			}
			
			var camposLookupRisco = desc.split("-");
			tabelaRisco.addObject([id, camposLookupRisco[0], camposLookupRisco[1]]);
			
			document.form.riscoSerializado.value = ObjectUtils.serializeObjects(tabelaRisco.getTableObjects());
			
			$("#POPUP_RISCO").dialog("close");
			
		}
		
		function validaAddLinhaTabelaRisco(lastRow, id){
			var listaRisco = ObjectUtils.deserializeCollectionFromString(document.form.riscoSerializado.value);
			
			if (lastRow > 1){
				for(var i = 0; i < listaRisco.length; i++){
					if (listaRisco[i].idRisco == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
				}
			} 
			return true;
		}
		
		function abrePopupServicos(){
			$("#POPUP_SERVICO").dialog("open");
		}
		
		function abrePopupLiberacao(){
			$("#POPUP_LIBERACAO").dialog("open");
		}
		
		function abrePopupGrupo(){
			$("#POPUP_GRUPO").dialog("open");
		}
		
		function abrePopupRisco(){
			$("#POPUP_RISCO").dialog("open");
		}
		
		function abrePopupProblema(){
			limpar_LOOKUP_PROBLEMA();
			$("#POPUP_PROBLEMA").dialog("open");
		}
				
		function adicionarRisco(){
			abrePopupRisco();
		}
		
		function adicionarServico(){
			abrePopupServicos();	
		}
		
		function adicionarProblema(){
			abrePopupProblema();
		/*
		 * é necessário serializar para que requisicaoMudanca.java possa trabalhar com
		 * quantidade atualizada de linhas na grid.
		 */
			serializaProblema();	
		}
		
		function adicionarLiberacao() {
			abrePopupLiberacao();
		}
		function adicionarGrupo() {
			abrePopupGrupo();
		}

		/*
		 * Funções alimentação tabelas de relacionamento
		 */

		 /**
		  * Renderiza tabela a partir de lista.
		  * @param _idCITTable id da tabela a ser tratada
		  * @param _fields Lista de campos correspondentes ao banco de dados
		  * @param _tableObjects Lista de itens. Deve corresponder aos campos de _fields
		  */
		 var contador = 0;
		 function CITTable(_idCITTable, _fields, _tableObjects){
				var self = this;
				var idCITTable = _idCITTable;
				var fields = _fields;
				var tableObjects = _tableObjects;
				var tabela = null;
				
				var insereBtExcluir = true;
				var insereBtInformacoes  = true;
				var imgBotaoExcluir;
				var imgBotaoInformacoes;

				this.onDeleteRow = function(deletedItem){};

				this.getTableList = function(){
					return tableObjects;
				}
				
				

				/**
				 * Transforma a lista da tabela em uma lista de objetos
				 * de acordo com o 'fields' passado.
				 */
				this.getTableObjects = function(){
					var objects = [];
					var object = {};

					for(var j = 0; j < tableObjects.length; j++){
						for(var i = 0 ; i < fields.length; i++){
							eval("object." + fields[i] + " = '" + tableObjects[j][i] + "'");
						}
						objects.push(object);
						object = {};
					}
					
					return objects;
				}

				this.setTableObjects = function(objects){
					tableObjects = objects;
					this.montaTabela();
				}
				
				this.addObject = function(object){
					tableObjects.push(object);
					this.montaTabela();
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
				
				this.montaTabela = function(){
					var linha;
					var celula;
					
					limpaTabela();

					var idItemConfiguracao;
					
					for(var i = tableObjects.length - 1; i >= 0; i--){
						
						
						var j = 0;					
						linha = getTabela().insertRow(1);

						for(j = 0; j < fields.length; j++){
							celula = linha.insertCell(j);
				
							//tratamento caso seja um componente ao invés de texto
							try{
								celula.appendChild(tableObjects[i][j]);
							}catch(e){
								celula.innerHTML = tableObjects[i][j];
							}							
						}					

						if(insereBtExcluir){
							var btAux = getCopiaBotaoExcluir();
							var celExcluir = linha.insertCell(j);
							
							btAux.setAttribute("id", i);
							btAux.addEventListener("click", function(evt){
								//ao disparar o evento, considerará o id do botão
								self.removeObject(this.id);
								this.onDeleteRow(this);
								
							}, false);							
							celExcluir.appendChild(btAux);
						}
						
						
						if(idCITTable == "tblICs"){
							
							if(insereBtInformacoes){
								var btAux = getCopiaBotaoInformacoes();
								var celInformacoes = linha.insertCell(j);
								btAux.setAttribute("id", i);
								
								btAux.addEventListener("click", function(evt){
									//alert(this.id);
									carregaPaginaIC(this.id);
									//contador++;
									
								}, false);							
								celInformacoes.appendChild(btAux);
								
							}	
							//contador++;
 						}
					}
				}
				
				this.removeObject = function(indice){
					removeObjectDaLista(indice);
					this.montaTabela();
				}
			
				/**
				 * Remove item e organiza lista
				 */
				var removeObjectDaLista = function(indice){
					tableObjects[indice] = null;
					var novaLista = [];
					for(var i = 0 ; i < tableObjects.length; i++){
						if(tableObjects[i] != null){
							novaLista.push(tableObjects[i]);
						}
					}
					tableObjects = novaLista;
				}

				var getCopiaBotaoExcluir = function(){
					var novoBotao = new Image();
					novoBotao.setAttribute("style", "cursor: pointer;");
					novoBotao.src = imgBotaoExcluir;
					return novoBotao;
				}
				
				var getCopiaBotaoInformacoes = function(){
					var novoBotao = new Image();
					novoBotao.setAttribute("style", "cursor: pointer;");
					novoBotao.src = imgBotaoInformacoes;
					return novoBotao;
				}

				var setImgPathBotaoExcluir = function(src){
					imgBotaoExcluir = src;
				}
				
				var setImgPathBotaoInformacoes = function(src){
					imgBotaoInformacoes= src;
				}

				var getTabela = function(){
					if(tabela == null){
						tabela = document.getElementById(idCITTable);
					}
					return tabela;
				}

				this.setInsereBotaoExcluir = function(bool, imgSrc){
					insereBtExcluir = bool;
					setImgPathBotaoExcluir(imgSrc);
				}
				
				this.setInsereBotaoInformacoes = function(bool, imgSrc){
					insereBtInformacoes = bool;
					setImgPathBotaoInformacoes(imgSrc);
				}
			}
			
		 carregaPaginaIC = function(idLinha){
			 	var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value); 	
				var idItemConfiguracao = listaICs[idLinha].idItemConfiguracao;
				
				document.getElementById('fraInfosItemConfig').src = "about:blank";
		    	document.getElementById('fraInfosItemConfig').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/informacoesItemConfiguracaoMudanca/informacoesItemConfiguracaoMudanca.load?idItemConfiguracao="+idItemConfiguracao; 
				$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog({ height: 600 });
				$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog({ title: '<i18n:message key="tipoItemConfiguracao.informacoesItemConfiguracao" />' });
				$( "#POPUP_INFORMACOESITEMCONFIGURACAO" ).dialog( 'open' );
			}
		
			 fechar = function(){
			parent.fecharMudanca();
			}	 
		 
		 	/*
			 * Funções de Solicitacao Servico
			 */	
		    
			$(function() {
				$("#POPUP_SOLICITACAOSERVICO").dialog({
					autoOpen : false,
					width : 863,
					height : 700,
					modal : true
				});

		<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Jquery popup cadastrar nova solicitação serviço. -->
				$("#POPUP_NOVASOLICITACAOSERVICO").dialog({
					autoOpen : false,
					width : 1260,
					height : 630,
					modal : true
				});

		<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
				$("#POPUP_NOVOTIPOREQUISICAOMUDANCA").dialog({
					autoOpen : false,
					width : 1260,
					height : 490,
					modal : true,
					close: function() {
						document.form.fireEvent('preencherComboTipoMudanca');
					}
				});

				$("#POPUP_NOVAUNIDADE").dialog({
					autoOpen : false,
					width : 1320,
					height : 780,
					modal : true,
					close: function() {
						document.form.fireEvent('carregaUnidade');
					}
				});

				$("#POPUP_NOVALOCALIDADE").dialog({
					autoOpen : false,
					width : 1000,
					height : 400,
					modal : true,
					close: function() {
						document.form.fireEvent('preencherComboLocalidade');
					}
				});

				$("#POPUP_NOVOGRUPOEXECUTOR").dialog({
					autoOpen : false,
					width : 1400,
					height : 780,
					modal : true,
					close: function() {
						document.form.fireEvent('preencherComboGrupoExecutor');
						document.form.fireEvent('preencherComboComite');
					}
				});

			<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Retirar barra de rolagem desnecessaria da popup serviço . -->
				$("#POPUP_SERVICO").dialog({
					autoOpen : false,
					width : 1100,
					height : 740,
					modal : true
				});
				
				$("#addSolicitacaoServico").click(function() {
					$("#POPUP_SOLICITACAOSERVICO").dialog("open");
				});
				
				$("#addImgSolicitacaoServico").click(function() {
					$("#POPUP_SOLICITACAOSERVICO").dialog("open");
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
				
				var camposLookupItem = desc.split("-");
				tabelaRelacionamentoSolicitacaoServico.addObject([id, camposLookupItem[1], camposLookupItem[2]]);
				
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
			
			
			function AprovacaoMudancaDTO(idEmpregado,nomeEmpregado,dataHoraVotacao,voto,comentario,i){
		 		this.idEmpregado = idEmpregado; 
		 		this.nomeEmpregado = nomeEmpregado; 
		 		this.dataHoraVotacao = dataHoraVotacao
		 		this.voto = voto; 
		 		this.comentario = comentario; 
		 	}
			
			function AprovacaoPropostaDTO(idEmpregado,nomeEmpregado,dataHoraVotacao,voto,comentario,i){
		 		this.idEmpregado = idEmpregado; 
		 		this.nomeEmpregado = nomeEmpregado; 
		 		this.dataHoraVotacao = dataHoraVotacao
		 		this.voto = voto; 
		 		this.comentario = comentario; 
		 	}
			
			function serializaAprovacoesProposta(){
		 		var tabela = document.getElementById('tabelaAprovacoesProposta');
		 		var count = tabela.rows.length;
		 		var listaDeAprovacoes = [];
		 		for(var i = 1; i < count ; i++){
		 			var voto =  '';
		 			if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null){
		 			var idEmpregado = document.getElementById('idEmpregado' + i).value;
		 			var nomeEmpregado = document.getElementById('nomeEmpregado' + i).value;
		 			var dataHoraVotacao = document.getElementById('dataHoraVotacao' + i).value;
		 			if ($('#votoAProposta' + i).is(":checked")){
		 				voto = "A";
					} else{
						if ($('#votoRProposta' + i).is(":checked")){
							voto = "R";
						} 
					}
		 			
		 			
		 			var comentario = document.getElementById('comentarioProposta' + i).value;
		 			var aprovacaoProposta = new AprovacaoPropostaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario,i);
		 			listaDeAprovacoes.push(aprovacaoProposta);
		 			}
		 		} 	
		 		var serializa = ObjectUtils.serializeObjects(listaDeAprovacoes);
				document.form.aprovacaoPropostaServicoSerializado.value = serializa;
		 	}
			
			function serializaAprovacoesMudanca(){
		 		var tabela = document.getElementById('tabelaAprovacoesMudanca');
		 		var count = tabela.rows.length;
		 		var listaDeAprovacoes = [];
		 		for(var i = 1; i < count ; i++){
		 			var voto =  '';
		 			if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null){
		 			var idEmpregado = document.getElementById('idEmpregado' + i).value;
		 			var nomeEmpregado = document.getElementById('nomeEmpregado' + i).value;
		 			var dataHoraVotacao = document.getElementById('dataHoraVotacao' + i).value;
		 			if ($('#votoAMudanca' + i).is(":checked")){
		 				voto = "A";
					} else{
						if ($('#votoRMudanca' + i).is(":checked")){
							voto = "R";
						} 
					}
		 			
		 			
		 			var comentario = document.getElementById('comentarioMudanca' + i).value;
		 			var aprovacaoMudanca = new AprovacaoMudancaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario,i);
		 			listaDeAprovacoes.push(aprovacaoMudanca);
		 			}
		 		} 	
		 		var serializa = ObjectUtils.serializeObjects(listaDeAprovacoes);
				document.form.aprovacaoMudancaServicoSerializado.value = serializa;
		 	}
			
			
			function addLinhaTabelaAprovacaoProposta(idEmpregado,nomeEmpregado,comentario,dataHoraVotacao,validacao,valida){
				var tbl = document.getElementById('tabelaAprovacoesProposta');
				tbl.style.display = 'block';
				var lastRow = tbl.rows.length;
				var row = tbl.insertRow(lastRow);
				var  disabled = '';
				if(validacao == 'true' ){
					disabled =  'disabled = "true"';
				}
				
				count++;
				coluna = row.insertCell(0);
				coluna.innerHTML ='<input id="idEmpregado' + count + '" type="hidden" name="idEmpregado" value="' + idEmpregado + '"/><input  value = "'+nomeEmpregado+'"  type="hidden" id="nomeEmpregado' + count + '" />';
 				coluna = row.insertCell(1);
 				coluna.innerHTML = nomeEmpregado ;
 				coluna = row.insertCell(2);
 				coluna.innerHTML = '<span  style="padding-right: 30px;"><input '+disabled+'  style="margin-right: 5px;" type="radio" id="votoAProposta' + count + '" name="voto' + count + '" value="A"  /><i18n:message key="citcorpore.comum.aprovada" /></span>' +
 				'<span style="padding-right: 30px;"><input '+disabled+' style="margin-right: 5px;" type="radio" id="votoRProposta' + count + '" name="voto' + count + '" value="R" /><i18n:message key="citcorpore.comum.rejeitada" /></span>';
 				coluna = row.insertCell(3);
 				coluna.innerHTML =  '<input  '+disabled+'  value="'+comentario+'" name="comentario' + count + '" id="comentarioProposta' + count + '" size="100"  type="text" maxlength="200" />'
 				coluna = row.insertCell(4);
 				var input ='<input  '+disabled+'  value="'+dataHoraVotacao+'" name="dataHoraVotacao' + count + '" id="dataHoraVotacao' + count + '" size="100"  type="hidden" maxlength="200" />';
				coluna.innerHTML = dataHoraVotacao + input;

			}
			
			function addLinhaTabelaAprovacaoMudanca(idEmpregado,nomeEmpregado,comentario,dataHoraVotacao,validacao,valida){
				var tbl = document.getElementById('tabelaAprovacoesMudanca');
				tbl.style.display = 'block';
				var lastRow = tbl.rows.length;
				var row = tbl.insertRow(lastRow);
				var  disabled = '';
				if(validacao == 'true' ){
					disabled =  'disabled = "true"';
				}
				
				count++;
				coluna = row.insertCell(0);
				coluna.innerHTML ='<input id="idEmpregado' + count + '" type="hidden" name="idEmpregado" value="' + idEmpregado + '"/><input  value = "'+nomeEmpregado+'"  type="hidden" id="nomeEmpregado' + count + '" />';
 				coluna = row.insertCell(1);
 				coluna.innerHTML = nomeEmpregado ;
 				coluna = row.insertCell(2);
 				coluna.innerHTML = '<span  style="padding-right: 30px;"><input '+disabled+'  style="margin-right: 5px;" type="radio" id="votoAMudanca' + count + '" name="voto' + count + '" value="A"  /><i18n:message key="citcorpore.comum.aprovada" /></span>' +
 				'<span style="padding-right: 30px;"><input '+disabled+' style="margin-right: 5px;" type="radio" id="votoRMudanca' + count + '" name="voto' + count + '" value="R" /><i18n:message key="citcorpore.comum.rejeitada" /></span>';
 				coluna = row.insertCell(3);
 				coluna.innerHTML =  '<input  '+disabled+'  value="'+comentario+'" name="comentario' + count + '" id="comentarioMudanca' + count + '" size="100"  type="text" maxlength="200" />'
 				coluna = row.insertCell(4);
 				var input ='<input  '+disabled+'  value="'+dataHoraVotacao+'" name="dataHoraVotacao' + count + '" id="dataHoraVotacao' + count + '" size="100"  type="hidden" maxlength="200" />';
				coluna.innerHTML = dataHoraVotacao + input;

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
			
			
			function deleteAllRowsProposta() {
				var tabela = document.getElementById('tabelaAprovacoesProposta');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
			}
			
			function deleteAllRowsMudanca() {
				var tabela = document.getElementById('tabelaAprovacoesMudanca');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
			}
			
			function mostrarCategoria(){ 
		    	
		    	document.form.fireEvent('validacaoCategoriaMudanca');
		    	
	        }
			
			function atribuirCheckedVotoProposta(voto){
				if (voto == "A" && voto != null){
					$('#votoAProposta' + count).attr('checked', true);
				} else{
					if (voto == "R" && voto != null){
						$('#votoRProposta' + count).attr('checked', true);
					} 
				}
			}
			
			function atribuirCheckedVotoMudanca(voto){
				if (voto == "A" && voto != null){
					$('#votoAMudanca' + count).attr('checked', true);
				} else{
					if (voto == "R" && voto != null){
						$('#votoRMudanca' + count).attr('checked', true);
					} 
				}
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
				popup2.abrePopupParms('empregado', '', 'idContrato=' + idContrato);
			}	
			
			//ações dos botões
			function abrirPopupAnexo() {
				$('#POPUP_menuAnexos').dialog('open');
				uploadAnexos.refresh();
			}
			
			function getBotaoEditarProblema(id){
				var botaoVisualizarProblemas = new Image();

				botaoVisualizarProblemas.src = '<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/imagens/viewCadastro.png';
				botaoVisualizarProblemas.setAttribute("style", "cursor: pointer;");
				botaoVisualizarProblemas.id = id;
				botaoVisualizarProblemas.addEventListener("click", function(evt){CarregarProblema(id)}, true);

				return botaoVisualizarProblemas;
			}
			
			function CarregarProblema(idProblema){
				document.getElementById('iframeEditarProblema').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
				$("#POPUP_EDITARPROBLEMA").dialog("open");
			}
			$(function() {
				$("#POPUP_EDITARPROBLEMA").dialog({
					autoOpen : false,
					width : "98%",
					height : 1000,
					modal : true
				});
			});
			
			function fecharFrameProblema(){
				$("#POPUP_EDITARPROBLEMA").dialog("close");
				//document.form.fireEvent("atualizaGridProblema");
			}
			
			function fecharItemRequisicao() {
				$("#POPUPITEMCONFIGURACAO").dialog("close");
			}
			
			

			
			function fecharPopupDescricaoItemConf(){
				document.getElementById('hiddenDescricaoItemConfiguracao').value = document.getElementById('descricaoItemConfiguracao').value;
				document.form.fireEvent('tratarCaracterItemConfiguracao');
				$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("close");
			}
			
			function teste() {
				document.form.fireEvent('tratarCaracterItemConfiguracao');
			}
			
			function atualizarTabela(itemTratado, desc){
				var descricao = document.getElementById('hiddenDescricaoItemConfiguracao').value;
				descricaoItemConfiguracao = desc;
				var registroJaEssisteTabela = addLinhaTabelaItemConfiguracao(idItemConfiguracao, desc, true);
				if (registroJaEssisteTabela == false) {
					return;
				}
				tabelaRelacionamentoICs.addObject([idItemConfiguracao, descricaoItemConfiguracao, descricao]);
				document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());
				
				//$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("close");
				fecharItemRequisicao();
				
				document.getElementById('descricaoItemConfiguracao').value = "";
				idItemConfiguracao = ""; 
				descricaoItemConfiguracao = "";
				
			}
			
			function descricaoTratadaJava(descricaoTratadaJava) {
				//alert(descricaoTratadaJava);
				descricaoTratada = descricaoTratadaJava;
			}
			
			    /**
		    	INFORMAÇÕES COMPLEMENTARES (TEMPLATE/QUESTIONARIO)
		    **/
		 function exibirInformacoesComplementares(url) {
	           if (url != '') {
	               JANELA_AGUARDE_MENU.show();
	               //document.getElementById('divInformacoesComplementares').style.display = 'block';
	               document.getElementById('fraInformacoesComplementares').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>'+url;
	           }else{
	               try{
	               	escondeJanelaAguarde();
	               }catch (e) {
	               }       
	               document.getElementById('divInformacoesComplementares').style.display = 'none';
	           } 
	       }
			    
	       function exibeJanelaAguarde() {
	           JANELA_AGUARDE_MENU.show();
	       }
			    
	       function escondeJanelaAguarde() {
	           JANELA_AGUARDE_MENU.hide();
	       }
	       
	       function restoreImpactoUrgenciaPorTipoMudanca(){
	    	   document.form.fireEvent('restoreImpactoUrgenciaPorTipoMudanca');
	       }
	   	function chamaPopupCadastroRisco(){
	   		dimensionaPopupCadastroRapido("1300","600");
			cadastroRisco.abrePopupParms('risco', '', '');
		}
		function dimensionaPopupCadastroRapido(w, h) {
			$("#popupCadastroRapido").dialog("option","width", w)
			$("#popupCadastroRapido").dialog("option","height", h)
		}

		function addSolicitante(){
			var idContrato = document.form.idContrato.value;
			document.getElementById('iframeNovoColaborador').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/empregado/empregado.load?iframe=true&idContrato="+idContrato;
			
			$("#POPUP_NOVOCOLABORADOR").dialog("open");
		}

		$(function() {
			$("#POPUP_NOVOCOLABORADOR").dialog({
				autoOpen : false,
				width : "70%",
				height : 500,
				modal : true
			});
		});

		function fecharAddSolicitante(){
			$('#POPUP_NOVOCOLABORADOR').dialog( 'close' );
		}
		<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Funções retirar bug para cadastrar nova solicitação serviço. -->
		function atualizarLista() {
			$("#POPUP_SOLICITACAOSERVICO").dialog("close");
		}

		function fecharModalNovaSolicitacao() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function fecharModalFilha() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function pesquisarItensFiltro() {
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("close");
		}

		function abrirPopupNovaSolicitacaoServico(){
			document.getElementById('iframeNovaSolicitacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?iframe=true";
			$("#POPUP_NOVASOLICITACAOSERVICO").dialog("open");
		}
		
		<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
		function abrirPopupNovoTipoRequisicaoMudanca(){
			document.getElementById('iframeNovoTipoRequisicaoMudanca').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/tipoMudanca/tipoMudanca.load?iframe=true";
			$("#POPUP_NOVOTIPOREQUISICAOMUDANCA").dialog("open");
		}

		function abrirPopupNovaUnidade(){
			document.getElementById('iframeNovaUnidade').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/unidade/unidade.load?iframe=true";
			$("#POPUP_NOVAUNIDADE").dialog("open");
		}

		function abrirPopupNovaLocalidade(){
			document.getElementById('iframeNovaLocalidade').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/localidade/localidade.load?iframe=true";
			$("#POPUP_NOVALOCALIDADE").dialog("open");
		}

		function abrirPopupNovoGrupoExecutor(){
			document.getElementById('iframeNovoGrupoExecutor').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/grupo/grupo.load?iframe=true";
			$("#POPUP_NOVOGRUPOEXECUTOR").dialog("open");
		}

		function abrirAbaPlanoDeReversao() {
			alert(i18n_message("informeAnexoReversao"));
			JANELA_AGUARDE_MENU.hide();
			$('.tabs').tabs('select', 11);
		}
		
	</script>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
<div id="divBarraFerramentas" style='position:absolute; top: 0px; left: 500px; z-index: 1000'>
		<jsp:include page="/pages/barraFerramentasMudancas/barraFerramentasMudancas.jsp"></jsp:include>
	</div>
	<div 	 id="wrapper" class="wrapper">		 
	<div id="main_container" class="main_container container_16 clearfix" style='margin: 10px 10px 10px 10px'>
	<div id='divTituloSolicitacao' class="flat_area grid_16" style="text-align:right;">
		<%
			if (idRequisicaoMudanca > 0) {
		%>
			<h2><i18n:message key="requisicaoMudanca.requisicaoMudancaNumero" /> <%=idRequisicaoMudanca%></h2>
		<%
			} else {
		%>
			<h2><i18n:message key="requisicaoMudanca.requisicaoMudanca" /></h2>
		<%
			}
		%>
	</div>
	<%
		if (faseMudancaRequisicao != null && faseMudancaRequisicao != ""){
	%>
		<div id='divStatusRequisicao' class="flat_area grid_16" style="text-align:right;">
					<h3><i18n:message key="<%=faseMudancaRequisicao%>" /></h3>
		</div>
		<%} %>

	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div class="section">
		<form id="form"  name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/requisicaoMudanca/requisicaoMudanca'>
			
			<input type="hidden" id="serializados" name="serializados" />
			<input type="hidden" id="idBaseConhecimento" name="idBaseConhecimento" />
			<input type="hidden" id="itensConfiguracaoRelacionadosSerializado" name="itensConfiguracaoRelacionadosSerializado" />
			<input type="hidden" id="servicosRelacionadosSerializado" name="servicosRelacionadosSerializado" />
			<input type="hidden" id="problemaSerializado" name="problemaSerializado" />
			<input type="hidden" id="riscoSerializado" name="riscoSerializado" />
			<input type="hidden" id="idRequisicaoMudanca" name="idRequisicaoMudanca" />
			<input type="hidden" id="idSolicitante" name="idSolicitante" />
			<input type="hidden" id="grupoMudancaSerializado" name="grupoMudancaSerializado" />
			<input type="hidden" id="idProprietario" name="idProprietario" />
			<input type="hidden"  id="solicitacaoServicoSerializado" name="solicitacaoServicoSerializado" />
			<input type='hidden' name='escalar' id='escalar' />
			<input type='hidden' name='alterarSituacao' id='alterarSituacao' />
			<input type='hidden' name='idTarefa' id='idTarefa' />
			<input type='hidden' name='acaoFluxo' id='acaoFluxo' />			
			<input type='hidden' name='fase' id='fase' />
			<input type="hidden" id="liberacoesRelacionadosSerializado" name="liberacoesRelacionadosSerializado" />
			<input type='hidden' name='faseAtual' id='faseAtual' />
			<input type='hidden' id='fecharItensRelacionados' name="fecharItensRelacionados">
			
			
			<input type="hidden" id="aprovacaoMudancaServicoSerializado" name="aprovacaoMudancaServicoSerializado" />
			<input type="hidden" id="aprovacaoPropostaServicoSerializado" name="aprovacaoPropostaServicoSerializado" />
			<input type="hidden" id="hiddenDescricaoItemConfiguracao" name="hiddenDescricaoItemConfiguracao" />
			<input type="hidden" id="hiddenIdItemConfiguracao" name="hiddenIdItemConfiguracao" />
			<input type="hidden" id="situacaoLiberacao" name="situacaoLiberacao" />
			<input type="hidden" id="liberacao#idRequisicaoLiberacao" name="liberacao#idRequisicaoLiberacao" />
			
			<input type='hidden' name='responsavel#idResponsavel' id='responsavel#idResponsavel' />
            <input type='hidden' name='responsavel#nomeResponsavel' id='responsavel#nomeResponsavel' />
            <input type='hidden' name='responsavel#papelResponsavel' id='responsavel#papelResponsavel' />
            <input type='hidden' name='responsavel#telResponsavel' id='responsavel#telResponsavel' />
            <input type='hidden' name='responsavel#nomeCargo' id='responsavel#nomeCargo' />
			<input type='hidden' name='responsavel#emailResponsavel' id='responsavel#emailResponsavel' />
			<input type="hidden" name="responsavel_serialize" id="responsavel_serialize" />
			
			<input type="hidden" name="idTipoRequisicao" id="idTipoRequisicao" />
			<input type="hidden" id="colAllLOOKUP_PROBLEMA" name="colAllLOOKUP_PROBLEMA"/>
			
			
			<div class="col_100">
					<fieldset>
						<label class="" style="font-family: Arial; font-weight: bold;">&nbsp;</label>
					
					</fieldset>
				</div>
				
				<div class="col_100">
					<fieldset>
						<label class="campoObrigatorio" style="font-family: Arial; font-weight: bold;"><i18n:message key="contrato.contrato" /></label>
						<div>
							<select  id="idContrato" name='idContrato' class=" Valid[Required] Description[<i18n:message key='contrato.contrato' />]" 
								onchange="setaValorLookup(this);" onclick= "document.form.fireEvent('carregaUnidade');"> <!--SETAR DPOIS carregarInformacoesComplementares(); 
								document.form.fireEvent('preencherComboLocalidade'); -->
							</select>
						</div>
					</fieldset>
				</div>
				
				<div class="col_100">
					<div class="col_50">	
						<fieldset>
							<label class="campoObrigatorio" ><i18n:message key="solicitacaoServico.solicitante" /></label>
							<div>
								<input class="Valid[Required] Description[solicitacaoServico.solicitante]" id="addSolicitante" name="nomeSolicitante" type="text" readonly="readonly"/>
							</div>
						</fieldset>
					</div>
				
					<div class= "col_25">
							<fieldset style="height: 55px">
								<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.tipo"/>
									<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrirPopupNovoTipoRequisicaoMudanca()">
								</label>
								<div>
								<select class="Valid[Required] Description[citcorpore.comum.tipo]" id="idTipoMudanca" name='idTipoMudanca' onchange="mostrarCategoria();restoreImpactoUrgenciaPorTipoMudanca();"></select>
								</div>
							</fieldset>
						</div>
					<div class="col_25" id="div_categoria" name="div_categoria" style="display:none" >
							<fieldset style="height: 55px" >
								<label><i18n:message key="citcorpore.comum.categoria"/></label>
								<div>
									<select id="nomeCategoriaMudanca" name="nomeCategoriaMudanca">
										<option value=''>--<i18n:message key="citcorpore.comum.selecione" />--</option>
										<option value="Importante"><i18n:message key="pesquisaRequisicaoMudanca.categoriaMudancaImportante" /></option>
										<option value="Significativa"><i18n:message key="pesquisaRequisicaoMudanca.categoriaMudancaSignificativa" /></option>
										<option value="Pequena"><i18n:message key="pesquisaRequisicaoMudanca.categoriaMudancaPequena" /></option>
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
					<div class="col_100">
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
								<label class="campoObrigatorio"><i18n:message key="requisicaoMudanca.email" /></label>
									<div>
										<input id="emailSolicitante" type='text'  name="emailSolicitante" maxlength="120" class="Valid[Required, Email] Description[requisicaoMudanca.email]" />
									</div>
								</fieldset>
						</div>
								
						<div class="col_25">
							<fieldset >
								<label><i18n:message key="requisicaoMudanca.telefone" /></label>
								<div>
									<input id="telefoneContato"  type='text'  name="telefoneContato" maxlength="13" class="" />
								</div>
							</fieldset>
						</div>
									
						<div class="col_25">
							<fieldset >
								<label><i18n:message key="citcorpore.comum.ramal" /></label>
								<div>
									<input id="ramal"  type='text'  name="ramal" maxlength="4" class="Format[Numero]" />
								</div>
							</fieldset>
						</div>
								
						<div class="col_25">
							<fieldset style="height: 55px">
								<label class="tooltip_bottom campoObrigatorio" title="<i18n:message key="colaborador.cadastroUnidade"/>" ><i18n:message key="unidade.unidade"/>
									<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
									<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrirPopupNovaUnidade()">
								</label>
								<div>
									<select name='idUnidade' id = 'idUnidade' onchange="document.form.fireEvent('preencherComboLocalidade')" class="Valid[Required] Description[colaborador.cadastroUnidade]" ></select>
								</div>
						    </fieldset>
						</div>
								
						<div class="col_25">
							<fieldset style="height: 55px" >
								<label class="tooltip_bottom " title="<i18n:message key="colaborador.cadastroUnidade"/>" >
									<i18n:message key="solicitacaoServico.localidadeFisica"/>
									<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
									<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrirPopupNovaLocalidade()">
								 </label>
								 <div>
									<select name='idLocalidade' id = 'idLocalidade'></select>
								</div>
						    </fieldset>
						</div>
					</div>
									
						<div class="col_50">
							<fieldset style="height: 112px">
								<label><i18n:message key="requisicaoMudanca.observacao"/></label>
								<div>
									<textarea id="observacao" class="col_98" name="observacao" maxlength="2000" style="height: 90px; float: right;"></textarea>
								</div>
							</fieldset>
						</div>
				</div>
				</div> <!-- FIM_divInfContato -->
			
			
				<div class="col_100">
					<div>
						<h2 class="section"><i18n:message key="requisicaoMudanca.informacaoRequisicao" /></h2>
					</div>
						
					<div class="col_50"> <!-- Lado Esquerdo da Tela -->			
						<div class="col_100" id="div_ehProposta">
							<fieldset>
								<label style='cursor:pointer' id="labelEhProposta"><input type='checkbox' value='S' name='ehPropostaAux'/><i18n:message key="solicitacaoServico.ehProposta"/></label>
							</fieldset>
						</div>
						<div class="col_100">
							<fieldset>
								<label class="campoObrigatorio"><i18n:message key="baseConhecimento.titulo" /></label>
									<div>
										<input class="Valid[Required] Description[baseConhecimento.titulo]" id="titulo" name="titulo" maxlength="100" type="text"/>
									</div>
							</fieldset>
						</div>
					
						<div style="width: 100%; float: left;">
							<fieldset>
								<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.descricao" /></label>
									<div>
										<textarea class="Valid[Required] Description[citcorpore.comum.descricao]" id="descricao" class="col_100" name="descricao" rows="4" maxlength="255" style="height: 250px;"></textarea>
									</div>
							</fieldset>
						</div>
						<div id="divNotificacaoEmail" class="col_50" >
								<fieldset>
									<label><i18n:message key="requisicaoMudanca.notificacaoporEmail"/></label>								
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailCriacao' checked="checked"/><i18n:message key="requisicaoMudanca.enviaEmailCriacao"/></label><br>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailFinalizacao' checked="checked"/><i18n:message key="requisicaoMudanca.enviaEmailFinalizacao"/></label><br>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailGrupoComite' checked="checked"/><i18n:message key="requisicaoMudanca.enviarEmailComiteConsultivoMudanca"/></label><br>
									<label style='cursor:pointer'><input type='checkbox' value='S' name='enviaEmailAcoes'/><i18n:message key="requisicaoMudanca.enviaEmailAcoes"/></label>
									
								</fieldset>
						</div>
						<div class="col_50"> 
							<fieldset style="FONT-SIZE: xx-small;">
								<label style="cursor: pointer;"><i18n:message key="citcorpore.comum.categoriaSolucao"/></label>
								<div>
									<select name='idCategoriaSolucao' ></select>
								</div>
							</fieldset>
						</div>
				
					</div> 
			
					<div class="col_50"><!-- Lado Direito da Tela -->
					<div class="col_100">
						<div class="col_50">
						<fieldset>
							<label class='campoObrigatorio'> <i18n:message key="grupo.execucaoRequisicaoMudanca" />
								<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
								<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrirPopupNovoGrupoExecutor()">
							</label>
							<div>
								<select name='idGrupoAtual' id='idGrupoAtual'  >								
								</select>			
							</div>
						</fieldset>
					</div>
					<div class="col_50">
						<fieldset>
							<label><i18n:message key="grupo.comiteConsultivoMudanca" />
								<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
								<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrirPopupNovoGrupoExecutor()">
							</label>
							<div>
								<select name='idGrupoComite' id='idGrupoComite'  >								
								</select>			
							</div>
						</fieldset>
					</div>
					</div>	
					<div class="col_100">
						<div class="col_33">
							<fieldset>
								<label class="campoObrigatorio"><i18n:message key="solicitacaoServico.impacto" /></label>
								<div>
									<select onchange="atualizaPrioridade()" id="nivelImpacto" name="nivelImpacto">
										<option value="B"><i18n:message key="citcorpore.comum.baixa"/></option>
										<option value="M"><i18n:message key="citcorpore.comum.media"/></option>
										<option value="A"><i18n:message key="citcorpore.comum.alta"/></option>
									</select>
								</div>
							</fieldset>
						</div>
						
						<div class="col_33" >
							<fieldset>
								<label class="campoObrigatorio"><i18n:message key="solicitacaoServico.urgencia"/></label>
								<div>
									<select onchange="atualizaPrioridade()" id="nivelUrgencia" name="nivelUrgencia">
										<option value="B"><i18n:message key="citcorpore.comum.baixa"/></option>
										<option value="M"><i18n:message key="citcorpore.comum.media"/></option>
										<option value="A"><i18n:message key="citcorpore.comum.alta"/></option>
									</select>
								</div>
							</fieldset>
						</div>
						
						<div class="col_33">
							<fieldset>
								<label><i18n:message key="prioridade.prioridade" /></label>
								<div>
									<input id="prioridade" name="prioridade" type="text" readonly="readonly" value="5" />
								</div>
							</fieldset>
						</div>
						</div>
						
						<div  class="col_100">
						<div  class="col_25">
							<fieldset>
								<label><i18n:message key="requisicaoMudanca.inicioAgendada"/></label>
								<div>
									<input id="dataHoraInicioAgendada" name="dataHoraInicioAgendada" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
								</div>
							</fieldset>
						</div>
						
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="requisicaoMudanca.horaAgendamentoInicial"/></label>
								<div>
									<input type='text' name="horaAgendamentoInicial" id="horaAgendamentoInicial" maxlength="5" size="5" maxlength="5"  class="Valid[Hora] Format[Hora]" />
								</div>
							</fieldset>
						</div>
						
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="requisicaoMudanca.terminoAgendada"/></label>
								<div>
									<input id="dataHoraTerminoAgendada" name="dataHoraTerminoAgendada" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
								</div>
							</fieldset>
						</div>
						
						<div class="col_25">
							<fieldset>
								<label><i18n:message key="requisicaoMudanca.horaAgendamentoFinal"/></label>
								<div>
									<input type='text' name="horaAgendamentoFinal" id="horaAgendamentoFinal" maxlength="5" size="5" maxlength="5"  class="Valid[Hora] Format[Hora]" />
								</div>
							</fieldset>
						</div>
						</div>
						
						<div  class="col_100">						
						
						<div class="col_33">
							<fieldset>
								<label><i18n:message key="requisicaoMudanca.aceitacao"/></label>
								<div>
									<input id="dataAceitacao" name="dataAceitacao" type="text" size="10" maxlength="10" class="Format[Date] Valid[Date] text datepicker" />
								</div>
							</fieldset>
						</div>
						
						<div class="col_33">
							<fieldset>
								<label><i18n:message key="requisicaoMudanca.votacao"/></label>
								<div>
									<input id="dataVotacao" name="dataVotacao" type="text" size="10" maxlength="10" class="Format[Date] Valid[Date] text datepicker" />
								</div>
							</fieldset>
						</div>
						
						<div class="col_33">
							<fieldset>
								<label><i18n:message key="requisicaoMudanca.conclusao"/></label>
								<div>
									<input id="dataHoraConclusao" name="dataHoraConclusao" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" />
								</div>
							</fieldset>
						</div>
						</div>
						<div class="col_50">
							<fieldset>
								<label style="cursor: pointer;"><i18n:message key='gerenciaservico.agendaratividade.crupoatividades' /></label>
								<div> 
									<select name='idGrupoAtvPeriodica' id='idGrupoAtvPeriodica'>
									</select>					
								</div>
							</fieldset>
						</div>
					</div>
				</div>
				<div id="requisicaMudancaStatus" class="col_100">
						<hr/>
						
						<%
													if (idRequisicaoMudanca > 0) {
												%>
						<label style="font-family: Arial; font-weight: bold; font-size: 13px;">
							<i18n:message key="requisicaMudanca.status" />
						</label>
						<label id="statusSetado" style="cursor: pointer;">	
						</label >
						
						<label id="statusCancelado" style="cursor: pointer;">
							<input type="radio" id="status" name="status" value="Cancelada" /><i18n:message key="citcorpore.comum.cancelada" />
						</label>
						<%
							}
						%>
						
						<%-- <label style="cursor: pointer;">
							<input type="radio" id="status" name="status" value="Rejeitada" /><i18n:message key="citcorpore.comum.rejeitada" />
						</label>
						
						<label style="cursor: pointer;">
							<input type="radio" id="status" name="status" value="Cancelada" /><i18n:message key="citcorpore.comum.cancelada" />
						</label>
						
						<label style="cursor: pointer;">
							<input type="radio" id="status" name="status" value="Concluida" /><i18n:message key="citcorpore.comum.concluida" />
						</label> --%>
						
					</div>
					<div class="col_100">
						<fieldset>
							<label><i18n:message key="solicitacaoServico.fechamento" /></label>
							<div>
								<textarea id="fechamento" name="fechamento" cols='70' rows='3' class="Description[Resposta]"></textarea>
							</div>
						</fieldset>									
					</div>
					<div>
					<div class="col_100" id='divBotaoAddRegExecucao'>
						<fieldset style="FONT-SIZE: xx-small;">
							<button type='button' name='btnAddRegExec' id='btnAddRegExec' onclick='mostrarEscondeRegExec()'><i18n:message key="solicitacaoServico.addregistroexecucao_mais" /></button>
						</fieldset>									
					</div>	
					<div class="col_100">
						<fieldset style="FONT-SIZE: xx-small;">
							<label id='lblMsgregistroexecucao' style='display:none'><font color='red'><b><i18n:message key="solicitacaoServico.msgregistroexecucao" /></b></font></label>
								<div id='divMostraRegistroExecucao' style='display:none'>
									<textarea id="registroexecucao" name="registroexecucao" cols='70' rows='5' maxlength="4000" class="Description[citcorpore.comum.resposta]"></textarea>
								</div>
						</fieldset>									
					</div>			
					<div class="col_100" style="overflow : auto;max-height: 400px">
						<fieldset>
							<div  id="tblOcorrencias" ></div>
						</fieldset>									
					</div>													
				</div>
				<div id="btRelatorio">
						<!-- botao plano de reversão -->
<!-- 						<button type='button' id="btAnexos" name='btnPlanoRevisao' onclick="abrirPopupAnexo();" class="light" style="margin: 10px !important; text-align: right;float: right;"> -->
<%-- 							<img src="<%=br.com.citframework.util.Constantes --%>
<%-- 					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/paperclip.png" /> --%>
<%-- 							<span ><i18n:message key="citcorpore.comum.anexosPlanoRevisao"/></span> --%>
<!-- 						</button> -->
						<%
							if (idRequisicaoMudanca > 0) {
						%>
							<button type='button' name='btnRelatorio' class="light" onclick="gerarRelatorioPDF();" style="margin: 10px !important; text-align: right;float: right;">
								<img src="<%=br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
								<span ><i18n:message key="citcorpore.comum.gerarRelatorioRegistroExecucao"/></span>
							</button>
						<%
							}
						%>
				</div>	
			
			<div  id="abas" class="formRelacionamentos">
				<div id="tabs" class="block">
					<ul class="tab_header">
						<li id="abaRelacionarAprovacoesProposta"><a href="#relacionarAprovacoesProposta"><i18n:message key="requisicaoMudanca.relacionarAprovacoesProposta"/></a></li>
						<li id="abaRelacionarAprovacoesMudanca"><a href="#relacionarAprovacoesMudanca"><i18n:message key="requisicaoMudanca.relacionarAprovacoesMudanca"/></a></li>
						<li><a href="#relacionaIcs"><i18n:message key="requisicaoMudanca.relacionarICs"/></a></li>
						<li><a href="#relacionaServicos"><i18n:message key="requisicaoMudanca.relacionarServicos"/></a></li>
						<li><a href="#relacionarProblemas"><i18n:message key="requisicaoMudanca.relacionarProblemas"/></a></li>
						<%-- <li><a href="#relacionarRecursos"><i18n:message key="requisicaoMudanca.relacionarRecursos"/></a></li> --%>
						<li><a href="#relacionarIncidentes"><i18n:message key="requisicaoMudanca.relacionarIncidentes"/></a></li>
						<li><a href="#relacionarRisco"><i18n:message key="requisicaoMudanca.relacionarRiscos"/></a></li>
						<li><a href="#relacionarLiberacaoMudanca"><i18n:message key="requisicaoMudanca.relacionarLiberacaoMudanca"/></a></li>
						<li><a href="#responsavels"><i18n:message key="requisicaoLiberacao.papeisResponsabilidades"/></a></li>
						<li><a href="#checklist"><i18n:message key="requisicaMudanca.checklist"/></a></li>
						<li><a href="#relacionarHistoricoMudanca"><i18n:message key="requisicaoMudanca.historicoMudanca"/></a></li>
						<li><a href="#adicionarPlanoDeReversao"><i18n:message key="requisicaoMudanca.adicionarPlanoDeReversao"/></a></li>
						<li><a href="#relacionarGrupo"><i18n:message key="requisicaoMudanca.relacionarGrupo"/></a></li>
					</ul>
					
					<div id="relacionaIcs">
						<div  style="width: 15%; float: left; <%=display%>" align="center" >
							<label style="cursor: pointer;" onclick='abrirModalPesquisaItemConfiguracao();'>
							<i18n:message key="requisicaoMudanca.adicionarItemConfiguracao"/>
								<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
							</label>
						</div>
						
						<div style="width: 99%; height : 30px; float: left;"></div>
						
						<div class="formBody">
							<table id="tblICs" class="table table-bordered table-striped">
								<tr>
									<th width="15%"><i18n:message key="parametroCorpore.id"/></th>
									<th width="35%"><i18n:message key="citcorpore.comum.nome"/></th>
									<th width="50%"><i18n:message key="citcorpore.comum.descricao"/></th>
									<th width="50%"><i18n:message key="start.instalacao.informacoesGerais"/></th>
									<th width="50%" style="<%=display%>"><i18n:message key="botaoacaovisao.excluir"/></th>
								</tr>
							</table>
						</div>
					</div>
						
					<div id="relacionaServicos" >
						<div style="width: 15%; float: left; <%=display%>" align="center"  >							
							<label  style="cursor: pointer;" onclick='adicionarServico();'>
								<i18n:message key="requisicaoMudanca.adicionarServico"/>
								<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
							</label>
						</div>	
						<div style="width: 99%; height : 30px; float: left;"></div>						
						<div class="formBody">
							<table id="tblServicos" class="table table-bordered table-striped">
								<tr>
									<th height="10px" width="15%"><i18n:message key="parametroCorpore.id"/></th>
									<th height="10px" width="35%"><i18n:message key="citcorpore.comum.nome"/></th>
									<th height="10px"width="50%"><i18n:message key="citcorpore.comum.descricao"/></th>
									<th height="10px"width="50%"><i18n:message key="citcorpore.comum.mapa"/></th>
								</tr>
							</table>
						</div>
					</div>
						
					<div id="relacionarProblemas">
						<div style="width: 15%; float: left; <%=display%>" align="center" >
							<label  style="cursor: pointer;" onclick='adicionarProblema();'>
								<i18n:message key="requisicaoMudanca.adicionarProblema"/>
								<img  src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
							</label>
						</div>						
						<div style="width: 99%; height : 30px; float: left;"></div>						
						<div class="formBody">
							<table id="tblProblema" class="table table-bordered table-striped">
								<tr>
									<th height="10px" width="15%"><i18n:message key="parametroCorpore.id"/></th>
									<th height="10px" width="35%"><i18n:message key="problema.titulo"/></th>
									<th height="10px"width="50%"><i18n:message key="problema.status"/></th>
									<th height="10px"width="50%"></th>
									<th height="10px"width="50%"></th>
								</tr>
							</table>
						</div>				
					</div>
						
<!-- 					<div id="relacionarRecursos">
					</div> -->
					
					<div id="relacionarIncidentes">
						<%-- <i18n:message key="requisicaMudanca.informacoes_relevantes_incidentes"/> --%>
						<div style="width: 15%; float: left; <%=display%>" align="center" >
							<label id="addSolicitacaoServico" style="cursor: pointer;" >
								<i18n:message key="requisicaMudanca.adicionar_incidente"/>
								<img id="addImgSolicitacaoServico" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
							</label>
						</div>											
						<div style="width: 15%; float: left; height: 27px;<%=display%>" align="center"  >
							<label  style="cursor: pointer;" onclick="abrirPopupNovaSolicitacaoServico();">
								<i18n:message key="requisicaMudanca.criar_novo_incidente"/>
								<img  src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
							</label>
						</div>										
						
						<div style="width: 99%; height : 30px; float: left;"></div>						
						<div class="formBody">
							<table id="tblSolicitacaoServico" class="table table-bordered table-striped">
								<tr>
									<th height="10px" width="20%"><i18n:message key="requisicaMudanca.numero_solicitacao"/></th>
									<th height="10px" width="850%"><i18n:message key="citcorpore.comum.descricao"/></th>
									<th height="10px" width="15%"></th>
								</tr>
							</table>
						</div>
						
						
					</div><!-- FIM_relacionarIncidentes -->
					
					<div id="relacionarRisco">
							<!-- ANALISES DE IMPACTO, RISCOS E ROLLBACK -->
						<div style="width: 15%; float: left; <%=display%>" align="center" >
							<label  style="cursor: pointer;" onclick='adicionarRisco();'>
								<i18n:message key="requisicaoMudanca.adicionarRisco"/>
								<img  src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
							</label>
						</div>	
						<div style="width: 15%; float: left; height: 27px; <%=display%>" align="center"  >
							<label  style="cursor: pointer;" onclick="chamaPopupCadastroRisco();">
								<i18n:message key="requisicaMudanca.criar_novo_risco"/>
								<img  src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
							</label>
						</div>					
						<div style="width: 99%; height : 30px; float: left;"></div>						
							<div class="formBody">
								<table id="tblRisco" class="table table-bordered table-striped">
									<tr>
										<th height="10px" width="15%"><i18n:message key="parametroCorpore.id"/></th>
										<th height="10px" width="35%"><i18n:message key="risco.risco"/></th>
										<th height="10px"width="50%"><i18n:message key="risco.detalhamento"/></th>
								</tr>
							</table>
							</div>
							<fieldset>
							<label><i18n:message key="requisicaoMudanca.razaoMudanca"/></label>
							<textarea id="razaoMudanca" name="razaoMudanca" maxlength="200" style="height: 100px; width: 99%;"></textarea> 
							
							<label ><i18n:message key="requisicaoMudanca.analiseImpacto"/></label>
							<textarea id="analiseImpacto" name="analiseImpacto" maxlength="255" style="height: 100px; width: 99%;"></textarea>
							
							<label><i18n:message key="requisicaoMudanca.analiseRiscos"/></label>
							<textarea id="risco" name="risco" maxlength="255" style="height: 100px; width: 99%;"></textarea>
							</fieldset>
							
					</div><!-- FIM_relacionarRisco-->
					
					<div id="relacionarAprovacoesProposta">
						<div id="gridAprovacoesProposta">
							<table class="table table-bordered table-striped" id="tabelaAprovacoesProposta">
							 <tr>
								<th style="width: 1%;"></th>
								<th style="width: 20%;"><i18n:message  key="citcorpore.comum.nome" /></th>
								<th style="width: 50%;"><i18n:message  key="requisicaoMudanca.votacao" /></th>
								<th style="width: 49,5%;"><i18n:message  key="comentarios.comentarios" /></th>
								<th style="width: 20%;"><i18n:message  key="citcorpore.comum.datahora" /></th>
							</tr>
							</table>
							</div>
						<div class="col_50"  id="quantidadePorVotoAprovadaProposta"></div>
						<div class="col_50"  id="quantidadePorVotoRejeitadaProposta"></div>
					</div>
					<div id="relacionarAprovacoesMudanca">
						<div id="gridAprovacoesMudanca">
							<table class="table table-bordered table-striped" id="tabelaAprovacoesMudanca">
							 <tr>
								<th style="width: 1%;"></th>
								<th style="width: 20%;"><i18n:message  key="citcorpore.comum.nome" /></th>
								<th style="width: 50%;"><i18n:message  key="requisicaoMudanca.votacao" /></th>
								<th style="width: 49,5%;"><i18n:message  key="comentarios.comentarios" /></th>
								<th style="width: 20%;"><i18n:message  key="citcorpore.comum.datahora" /></th>
							</tr>
							</table>
							</div>
						<div class="col_50"  id="quantidadePorVotoAprovadaMudanca"></div>
						<div class="col_50"  id="quantidadePorVotoRejeitadaMudanca"></div>
					</div>
					<div id="relacionarLiberacaoMudanca">
						<div style="width: 15%; float: left; <%=display%>" align="center" >
							<label  style="cursor: pointer;" onclick='adicionarLiberacao();'>
								<i18n:message key="requisicaoMudanca.adicionarliberacao"/>
								<img  src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
							</label>
						</div>						
						<div style="width: 99%; height : 30px; float: left;"></div>						
						<div class="formBody">
							<table id="tblLiberacao" class="table table-bordered table-striped">
								<tr>
									<th height="10px" width="15%"><i18n:message key="parametroCorpore.id"/></th>
									<th height="10px" width="30%"><i18n:message key="citcorpore.comum.titulo"/></th>
									<th height="10px"width="20%"><i18n:message key="citcorpore.comum.descricao"/></th>
									<th height="10px"width="20%"><i18n:message key="situacaoLiberacaoMudanca.status"/></th>
								</tr>
							</table>
						</div>
					</div>
					
					<div id="relacionarGrupo">
						<div style="width: 15%; float: left; <%=display%>" align="center" >
							<label  style="cursor: pointer;" onclick='adicionarGrupo();'>
								<i18n:message key="requisicaoMudanca.adicionarGrupo"/>
								<img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />							
							</label>
						</div>						
						<div style="width: 99%; height : 30px; float: left;"></div>						
						<div class="formBody">
							<table id="tblGrupo" class="table table-bordered table-striped">
								<tr>
								    <th height="10px" width="15%"><i18n:message key="parametroCorpore.id"/></th>
									<th height="10px" width="85%"><i18n:message key="pesquisa.grupo"/></th>
								</tr>
							</table>
						</div>
					</div>
<!-- 					<div id="checklist"> -->
<!-- 						<div class="col_100" id='divInformacoesComplementares' style='display:block;'> -->
<!--                         	<iframe id='fraInformacoesComplementares' name='fraInformacoesComplementares' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'></iframe> -->
<!--                        	</div> -->
<!-- 					</div> -->


							<div id="responsavels">
								<div style="width: 20%; float: left; <%=display%>" align="center">
									<label style="cursor: pointer;"
										onclick='adicionarResponsavel();'> <i18n:message
											key="liberacao.adicionarPapeisResponsabilidades" /> <img
										src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
									</label>
								</div>
								<div style="width: 99%; height: 30px; float: left;"></div>
								<div class="formBody">
									<table id="tblResponsavel" class="table table-bordered table-striped">
										<tr>
											<th height="10px" width="1%"></th>
											<th height="10px" width="5%"><i18n:message
													key="parametroCorpore.id" /></th>
											<th height="10px" width="25%"><i18n:message
													key="citcorpore.comum.nome" /></th>
											<th height="10px" width="15%"><i18n:message
													key="citcorpore.comum.cargo" /></th>
											<th height="10px" width="20%"><i18n:message
													key="citcorpore.comum.telefone" /></th>
											<th height="10px" width="20%"><i18n:message
													key="citcorpore.comum.email" /></th>
											<th height="10px" ><i18n:message
													key="citcorpore.comum.papel"/></th>
										</tr>
									</table>
								</div>
								</div>
								<div id="relacionarHistoricoMudanca">
											<div class="formBody">
												<input type='hidden' id='idHistoricoMudanca' name='idHistoricoMudanca'/>
												<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/> 
												<input type='hidden' id="baselinesSerializadas" name='baselinesSerializadas'/>					
												<div id="contentBaseline">				
													
												</div>				
												<!-- <button onclick="gravarBaseline();" class="light img_icon has_text" name="btnGravarBaseLine" type="button" id="btnGravarBaseLine">
													<img src="/citsmart/template_new/images/icons/small/grey/pencil.png">
													<span><i18n:message key="itemConfiguracaoTree.gravarBaselines"/></span>
												</button> -->
											</div>				
								</div>
								<div id="adicionarPlanoDeReversao">
									<cit:uploadPlanoDeReversaoControl id="uploadPlanoDeReversao" title="Anexos"	style="height: 100px; width: 98%; border: 1px solid black;" form="form" action="/pages/uploadPlanoDeReversao/uploadPlanoDeReversao.load" disabled="<%=editar%>" />
								</div>

								<!--  INICIO CHECKLIST -->
								<div id="checklist">
									<div class="col_100" id='divInformacoesComplementares'
										style='display: block; height: 1024px'>
										<iframe id='fraInformacoesComplementares'
											name='fraInformacoesComplementares' src='about:blank'
											width="100%" height="100%"
											style='width: 100%; height: 100%; border: none;'></iframe>
									</div>
								</div>
				</div><!-- FIM_tabs -->
			
							
					 </div>
							<!-- FIM_tabs -->

							<div id="divBotoes" class="formFooter">
			<!-- antes do q copiei -->
			<%
				if (tarefaAssociada.equalsIgnoreCase("N")) {
			%>
				<button type='button' name='btnGravar' class="light" onclick='gravar()'>
					<img src="<%=br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span><i18n:message key="citcorpore.comum.gravar" /></span>
				</button>
			<%
				} else {
			%>
				<button type='button' name='btnGravarEContinuar' class="light" onclick='gravarEContinuar();'>
					<img src="<%=br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span><i18n:message key="citcorpore.comum.gravarEContinuar" /></span>
				</button>
				<button type='button' name='btnGravarEFinalizar' class="light" onclick='gravarEFinalizar();'>
					<img src="<%=br.com.citframework.util.Constantes
						.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/cog_2.png">
					<span><i18n:message key="citcorpore.comum.gravarEFinalizar" /></span>
				</button>
			<%
				}
			%>
			<!-- dpois do que copiei -->
				
				<button type='button' name='btnLimpar' class="light" onclick='limpar(document.form);'>
					<img src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
					<span>
						<i18n:message key="citcorpore.comum.limpar" />
					</span>
				</button>
				
				<%-- <button type='button' name='btnExcluir' class="light" onclick='deletar();'>
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
					<span>
						<i18n:message key="citcorpore.comum.excluir" />
					</span>
				</button> --%>
			</div><!-- FIM_formFooter -->
		</form><!-- FIM_form -->	
		</div>
		</div>
		</div>
	</div><!-- FIM_classForm -->
	</div>	
			<!-- PESQUISAR AD -->
<!-- 	<div > -->
<!-- 		<button type="button" onclick='pesquisarAD()'> -->
<%-- 			<i18n:message key="citcorpore.comum.pesquisa" /> --%>
<!-- 		</button>			 -->
<!-- 		<button type="button" onclick='fecharAD();document.formAD.clear();'> -->
<%-- 			<i18n:message key="citcorpore.comum.fechar" /> --%>
<!-- 		</button> -->
<!-- 	</div> -->
	
	<!-- MAPA DESENHO SERVIÇO -->
	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>
	
	<!-- LOOKUPS -->
	
	<div id="POPUPITEMCONFIGURACAO" style=""  title="<i18n:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_ICS">
 		<table >
			<tr>
				<td>
					<h3><i18n:message key="eventoItemConfiguracao.itensConfiguracao" /></h3>
				</td>
			</tr>
		</table>				
		<form name='formPesquisaItem' style="width: 100%;">

			<cit:findField formName='formPesquisaItem' 
						   lockupName='LOOKUP_ITEMCONFIGURACAO_ATIVO' 
						   id='LOOKUP_ITEMCONFIGURACAO' 
						   top='0' left='0' len='550' 
						   heigth='480' 	
						   javascriptCode='true' htmlCode='true' />
		</form>
	</div>	
	
	<div id="POPUPDESCRICAOITEMCONFIGURACAO" style=""  title="<i18n:message key="requisicaoMudanca.oQueSeraMudadoNesteItem" />" class="POPUP_LOOKUP_ITEMCONFIGURACAO">
		<div class="row-fluid">
			<div class="span12 innerAll">
				<div class="row-fluid">
					<div class="span12">
						<input type="text" id="descricaoItemConfiguracao" maxlength="100" name="descricaoItemConfiguracao" />			
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<button type='button' name="btOk" class="light img_icon has_text rFloat" onclick="fecharPopupDescricaoItemConf();">
				    		<span><i18n:message key="requisicaMudanca.botaoOk" /></span>
			    		</button>
					</div>
				</div>
			</div>
		</div>
			
	</div>
	
	<div id="POPUP_SOLICITANTE" title="<i18n:message key="citcorpore.comum.pesquisacolaborador" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
					<div  align="right">
							<label  style="cursor: pointer; ">
									<i18n:message key="solicitacaoServico.solicitante" />
									<img id='botaoSolicitante' src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="addSolicitante()">
									<img id="btHistoricoSolicitante" style="cursor: pointer; display: none;" src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png">	
							</label>
						</div>
						<form name='formPesquisaColaborador' style="width: 500px">
							<cit:findField formName='formPesquisaColaborador'
								lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOL_CONTRATO' top='0'
								left='0' len='550' heigth='370' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_SERVICO" style="" title="<i18n:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_SERVICO">
		<table >
			<tr>
				<td>
					<h3 align="center"><i18n:message key="servico.servico" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisa' style="width: 100%;">
			<cit:findField formName='formPesquisa' lockupName='LOOKUP_SERVICO' id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='490' javascriptCode='true' htmlCode='true' />
		</form>
	</div>
	<div id="POPUP_RISCO" style="" title="<i18n:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_RISCO">
		<table >
			<tr>
				<td>
					<h3 align="center"><i18n:message key="risco.risco" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaRisco' style="width: 100%;">
			<cit:findField formName='formPesquisaRisco' lockupName='LOOKUP_RISCO' id='LOOKUP_RISCO' top='0' left='0' len='550' heigth='430' javascriptCode='true' htmlCode='true' />
		</form>
	</div>
	
	<div id="POPUP_LIBERACAO" style="" title="<i18n:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP_LIBERACAO">
		<table >
			<tr>
				<td>
					<h3 align="center"><i18n:message key="liberacao" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaLiberacao' style="width: 100%;">
			<cit:findField formName='formPesquisaLiberacao' lockupName='LOOKUP_LIBERACAO_MUDANCA' id='LOOKUP_LIBERACAO_MUDANCA' top='0' left='0' len='550' heigth='440' javascriptCode='true' htmlCode='true' />
		</form>
	</div>
	
	<div id="POPUP_GRUPO" style="" title="<i18n:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP">
		<table >
			<tr>
				<td>
					<h3 align="center"><i18n:message key="grupo.grupo" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaGrupo' style="width: 95%;">
			<cit:findField formName='formPesquisaGrupo' lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
		</form>
	</div>
	
	<div id="POPUP_PROBLEMA" style="" title="<i18n:message key="citcorpore.comum.pesquisa" />" class="POPUP_LOOKUP">
		<table >
			<tr>
				<td>
					<h3 align="center"><i18n:message key="problema.problema" /></h3>
				</td>
			</tr>
		</table>
		<form name='formPesquisaProblema' style="width: 100%;">
			<cit:findField formName='formPesquisaProblema' lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true'/>
		</form>
	</div>
	
	<div id="POPUP_INFO_INSERCAO"
		title=""
		style="overflow: hidden;">
		<div class="toggle_container">
			<div id='divMensagemInsercao' class="section" style="overflow: hidden; font-size: 24px;">
				
			</div>
			<button type="button" onclick='$("#POPUP_INFO_INSERCAO").dialog("close");'>
				<i18n:message key="citcorpore.comum.fechar" />
			</button>
		</div>
	</div>	
	 <div id="POPUP_INFO_BASELINE"
		title=""
		style="overflow: hidden;">
		<div class="toggle_container">
			<div id='divMensagemInsercaoBaseline' class="section" style="overflow: hidden; font-size: 24px;">
				
			</div>
			<button type="button" onclick='$("#POPUP_INFO_BASELINE").dialog("close");'>
				<i18n:message key="citcorpore.comum.fechar" />
			</button>
		</div>
	</div>	
		
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
					<form name='formPesquisaSolicitacaoServico' style="width: 790px">
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
	
	<div id="POPUP_EDITARPROBLEMA"  style="overflow: hidden;" title="<i18n:message key="problema.problema"/>">
		<iframe id='iframeEditarProblema' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()">
			
		</iframe>		
	</div>
	<div id="POPUP_RESPONSAVEL" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
        <div class="box grid_16 tabs">
        <div class="toggle_container">
        <div id="tabs-2" class="block">
       <div class="section">
            <form name='formPesquisaResponsavel' style="width: 790px">
                <cit:findField formName='formPesquisaResponsavel' lockupName='LOOKUP_RESPONSAVEL' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='800' heigth='410' javascriptCode='true' htmlCode='true' />
            </form>
        </div>
        </div>
        </div>
        </div>
    </div> 
    <div id="POPUP_INFORMACOESITEMCONFIGURACAO">
		<iframe id='fraInfosItemConfig' src='about:blank' width="100%" height="99%"></iframe>
	</div>
	<div id="POPUP_NOVOCOLABORADOR"  style="overflow: hidden;" title="<i18n:message key="colaborador.colaborador"/>">
		<iframe id='iframeNovoColaborador' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()">
			
		</iframe>		
	</div>
	<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Div popup cadastro nova solicitação serviço. -->
	<div id="POPUP_NOVASOLICITACAOSERVICO"  style="overflow: hidden;" title="<i18n:message key="solicitacaoServico.solicitacao"/>">
		<iframe id='iframeNovaSolicitacao' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
		</iframe>		
	</div>
	
	<!-- Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar novas opções ao comboboxes tipo requisição mudança, unidade, localidade fisica, grupo executor e grupo comitê consutivo de mudança . -->
	<div id="POPUP_NOVOTIPOREQUISICAOMUDANCA"  style="overflow: hidden;" title="<i18n:message key="tipoMudanca.tipoDeMudanca"/>">
		<iframe id='iframeNovoTipoRequisicaoMudanca' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
		</iframe>		
	</div>
	
	<div id="POPUP_NOVAUNIDADE"  style="overflow: hidden;" title="<i18n:message key="unidade.unidade"/>">
		<iframe id='iframeNovaUnidade' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
		</iframe>		
	</div>
	
	<div id="POPUP_NOVALOCALIDADE"  style="overflow: hidden;" title="<i18n:message key="localidadeFisica.localidadeFisica"/>">
		<iframe id='iframeNovaLocalidade' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
		</iframe>		
	</div>
	
	<div id="POPUP_NOVOGRUPOEXECUTOR"  style="overflow: hidden;" title="<i18n:message key="grupo.grupo"/>">
		<iframe id='iframeNovoGrupoExecutor' src='about:blank' width="100%" height="100%" onload="resize_iframe()">
		</iframe>	
	</div>
	
	<div id="POPUP_PESQUISAITEMCONFIGURACAO" style="" title="<i18n:message key="itemConfiguracao.pesquisa" />" class="POPUP_LOOKUP_SERVICO">
		<iframe id='framePesquisaItemConfiguracao' src='about:blank' width="100%" height="99%" onload="resize_iframe()">
		</iframe>
	</div>
</body>
</html>

