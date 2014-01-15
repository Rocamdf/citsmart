<%@page import="br.com.centralit.citcorpore.util.Util"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.centralit.bpm.util.Enumerados"%>
<%@ page import="br.com.centralit.citcorpore.free.Free"%>
<!doctype html public "">
<html>
<head>
<script type="text/javascript"
	src="<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String id = request.getParameter("id");
	String strRegistrosExecucao = (String) request
			.getAttribute("strRegistrosExecucao");
	if (strRegistrosExecucao == null) {
		strRegistrosExecucao = "";
	}

	String tarefaAssociada = (String) request
			.getAttribute("tarefaAssociada");
	if (tarefaAssociada == null) {
		tarefaAssociada = "N";
	}

	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil
			.getValorParametroCitSmartHashMap(
					br.com.centralit.citcorpore.util.Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO,
					"");
	
	String mostraGravarBaseConhec = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.MOSTRAR_GRAVAR_BASE_CONHECIMENTO, "S");
	
	if (PAGE_CADADTRO_SOLICITACAOSERVICO == null) {
		PAGE_CADADTRO_SOLICITACAOSERVICO = "";
	}
	
	String frameInfoComplementar = "N";
	PAGE_CADADTRO_SOLICITACAOSERVICO = PAGE_CADADTRO_SOLICITACAOSERVICO
			.trim();

	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript"
	src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
<script type="text/javascript"
	src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js?nocache=<%=new java.util.Date().toString()%>"></script>

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

.linhaSubtituloGridOcorr {
	font-size: 12px;
	color: #000000;
	font-family: Arial;
	background-color: #d3d3d3;
	BORDER-RIGHT: thin outset;
	BORDER-TOP: thin outset;
	BORDER-LEFT: thin outset;
	BORDER-BOTTOM: thin outset;
	text-align: center;
	font-weight: bold;
	height: 15px;
	line-height: 15px;
}

tr.hover {
	background: none;
}

tr.hover:HOVER {
	background: #EDEDED;
	cursor: pointer;
}
</style>

<script>
/* 		document.form.onClear = function() {
			HTMLUtils.deleteAllRows('tblProblema');
			HTMLUtils.deleteAllRows('tblMudanca');
		}; */
	</script>

<script>
		function buscarAD(){
			document.formAD.clear();
			document.getElementById('POPUP_SINCRONIZACAO_DETALHE').innerHTML = '';
			$("#POPUP_SINCRONIZACAO").dialog("open");
			$("#filtroADPesqAux").val("");
		}
		function fecharAD(){
			$("#POPUP_SINCRONIZACAO").dialog("close");
		}
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
		
		fechar_aguarde = function(){
	    	JANELA_AGUARDE_MENU.hide();
		}
		
		function LOOKUP_SOLICITANTE_select(id, desc){
		
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
			document.form.descricao.value = oEditor.GetXHTML();
			document.form.descricao.innerHTML = oEditor.GetXHTML();
			
			document.form.idSolicitante.value = id;
			document.form.fireEvent("restoreColaboradorSolicitante");
			calcularSLA();
		}
		
		function LOOKUP_PROBLEMA_select(id, desc){
			document.form.idProblema.value = id;
			document.form.fireEvent('atualizaGridProblema');
		}
		
		var popupAbreProblema = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		function abreProblema(row, object){
			popupAbreProblema.abrePopupParms('problema', '', 'idProblema=' + object.idProblema);
		}
		
		var popupAbreMudanca = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		function abreMudanca(row, object){
			popupAbreMudanca.abrePopupParms('requisicaoMudanca', '', 'idRequisicaoMudanca=' + object.idRequisicaoMudanca);
		}
		
		function LOOKUP_MUDANCA_select(id, desc){
			document.form.idRequisicaoMudanca.value = id;
			document.form.fireEvent('atualizaGridMudanca');
		}
		
		function fecharAddSolicitante(){
			$("#popupCadastroRapido").dialog('close');
		}
		
		jQuery(function($){ 
	        $('#telefonecontato').mask('(999) 9999-9999');
	    });
		
	    var popup;
	    var popup2;
	    var popup3;
	    var popup4;
	    var popup5;
	    var popup6;
	    var popupPesquisaSolucao;
	    var popupCategoriasOcorrencia;
	    var popupSolicitacaoRelacionada;
	    var popupVISBASECONHECIMENTO;

	    var escondeMostraDiv;
	    var temporizador;
	    var temporizadorRel1;
	    var temporizadorRel2;
	    addEvent(window, "load", load, false);
	    function load(){		
			popup = new PopupManager(1200, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popup2 = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popup3 = new PopupManager(1100, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popup4 = new PopupManager(1100, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popup5 = new PopupManager(1100, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popup6 = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popupCategoriasOcorrencia = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popupSolicitacaoRelacionada = new PopupManager("100%", 1005, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popupPesquisaSolucao = new PopupManager(1100, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			/* idSolicitacaoServico */
			popupVISBASECONHECIMENTO = new PopupManager(1300, 700, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			escondeMostraDiv = function(div, display) {
				document.getElementById(div).style.display = display;
			}
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
		
		$(function() { 
			$("#btHistoricoItem").click(function() {
				inicializarTemporizador();
				/* alert("oi"); */
			});
		});
		
		
		$(function() {
			$("#POPUP_SOLICITANTE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_PROBLEMA").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_MUDANCA").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});

			$("#tblResumo").dialog({
				autoOpen : false,
				width : 1000,
				height : 400,
				modal : true
			});
			
			$("#tblResumo2").dialog({
				autoOpen : false,
				width : 1000,
				height : 400,
				modal : true
			});
			$("#tblResumo3").dialog({
				autoOpen : false,
				width : 1000,
				height : 400,
				modal : true
			});
						
			$("#popupEmail").dialog({
				autoOpen : false,
				height : 570,
				width : 560,
				modal : true
			});
		});
		
		$(function() {
			$("#POPUP_PROBLEMA").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#addProblema").click(function() {
				$("#POPUP_PROBLEMA").dialog("open");
			});
			
			$("#imagenProblema").click(function() {
				$("#POPUP_PROBLEMA").dialog("open");
			});
			
			$("#addMudanca").click(function() {
				$("#POPUP_MUDANCA").dialog("open");
			});
			
			$("#imagenMudanca").click(function() {
				$("#POPUP_MUDANCA").dialog("open");
			});
			
		});
	
		$(function() {
			$("#addSolicitante").click(function() {
				if (document.form.idContrato.value == ''){
					alert('Informe o contrato!');
					return;
				}
				/* var v = document.getElementsByName("btnTodosLOOKUP_SOLICITANTE");
				v[0].style.display = 'none'; */
				var y = document.getElementsByName("btnLimparLOOKUP_SOLICITANTE");
				y[0].style.display = 'none'; 	
				$("#POPUP_SOLICITANTE").dialog("open");
			});
		});		
	
		$(function() { 
			$("#email").click(function() {
				$("#popupEmail").dialog("open");
			});
		});

		/**
		 * @author breno.guimaraes 
		 */
		$(function() {
			document.getElementById("divBarraFerramentas").style.display = "block";
			$("#btHistoricoSolicitante").click(function() {
				inicializarTemporizador();
				document.form.fireEvent("renderizaHistoricoSolicitacoesUsuario");
			});
			
			$("#btHistoricoSolicitanteEmAndamento").click(function() {
				inicializarTemporizador();
				document.form.fireEvent("renderizaHistoricoSolicitacoesEmAndamentoUsuario");
			});
			
			$("#btHistoricoIc").click(function() {
				inicializarTemporizador();
				document.form.fireEvent("renderizaHistoricoSolicitacoesIC");
			});
		});
		
		$(function() {
			
			$("#POPUP_PESQUISASOLUCAO").dialog({
				title : '<i18n:message key="citcorpore.comum.resultadopesquisa"/>',
				autoOpen : false,
				width : 700,
				height : 600,
				modal : true
			});
			
		});
		
		
		$(function() {
			
			$("#POPUP_VISBASECONHECIMENTO").dialog({
				autoOpen : false,
				width : 1300,
				height : 700,
				modal : true
			});
			
		});
		
		//@author ronnie.lopes
		//Inicializa escondido Campo Título do Gravar Base Conhecimento no Load da Página
		$(function() {
		<% if (mostraGravarBaseConhec.trim().equalsIgnoreCase("S")) { %>
				document.getElementById("ID_Div_Gravar_BaseConhecimento").style.display='none';
		<% } %>
		});
		
		//@author ronnie.lopes
		//Verifica se deve mostrar o campo título da base de conhecimento para gravá-lo. Só irá mostrar se a situação se encontrar como "Resolvida".
		function verificaMostraGravarBaseConhecimento(situacao) {
		<% if (mostraGravarBaseConhec.trim().equalsIgnoreCase("S")) { %>
				if (situacao == "Resolvida") {
					document.getElementById("ID_Div_Gravar_BaseConhecimento").style.display='block';
				}else {
					document.getElementById("ID_Div_Gravar_BaseConhecimento").style.display='none';
				}
		<% } %>

			if (situacao != "EmAndamento" && document.getElementById("novaSolicitacao").value == "true") {
				radiobtn = document.getElementById("situacaoRegistrada");
				radiobtn.checked = true;
			}
		}
		
		function mostraPesquisaSolucao(){
			document.getElementById("POPUP_PESQUISASOLUCAO").style.display="block";
		}
		
		function fecharPesquisaSolucao(){
			$("#POPUP_PESQUISASOLUCAO").dialog("close");		
		}
		
		function abreVISBASECONHECIMENTO(id){
			popupVISBASECONHECIMENTO.abrePopupParms('baseConhecimento', '', 'idBaseConhecimento=' + id);
		}
		
		function fecharVISBASECONHECIMENTO(){
			$("#POPUP_VISBASECONHECIMENTO").dialog("close");
		}
		
		function contadorClicks(idBaseConhecimento){
			document.form.idBaseConhecimento.value = idBaseConhecimento;
			document.form.fireEvent('contadorDeClicks');	
		}		
		
		function fecharProblema(){
			$("#POPUP_PROBLEMA").dialog("close");
		}
		
		function fecharMudanca(){
			$("#POPUP_MUDANCA").dialog("close");
		}
		exibeIconesProblema = function(row, obj){
			var id = obj.idProblema;
			/* var idos = obj.idOS; */
	        obj.sequenciaOS = row.rowIndex; 
		    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiProblema('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
		    row.cells[1].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/viewCadastro.png" border="0" onclick="carregarProblema(row, obj)" style="cursor:pointer"/>';
		}
		
		exibeIconesMudanca = function(row, obj){
			var id = obj.idRequisicaoMudanca;
			/* var idos = obj.idOS; */
	        obj.sequenciaOS = row.rowIndex; 
		    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiMudanca('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
			
		}

		exibeIconesBaseConhecimento = function(row, obj){
			var id = obj.idBaseConhecimento;
	        obj.sequenciaOS = row.rowIndex; 
		    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiBaseConhecimento('+ row.rowIndex + ',this)" style="cursor:pointer"/>';
		}
		//Bruno.Aquino
		exibeIconesICComAlerta = function(row, obj){
			var id = obj.idItemConfiguracao;
			/* var idos = obj.idOS; */
			
	        obj.sequenciaIC = row.rowIndex; 
		    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiIC('+ row.rowIndex + ',this)" style="cursor:pointer"/>';

			if(obj.idItemConfiguracaoPai == ""){
				row.cells[3].innerHTML =     '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/graph.png" border="0" onclick="popupAtivos( '+ id + ')" style="cursor:pointer"/>'+
											 '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/exclamation02.gif" border="0" height="18" style="cursor:pointer" id="btHistoricoItem" title="Há solicitações abertas para o Item de Configuracao escolhido. Clique para mais detalhes" onclick = "AbrirPopupItemConfiguracao();"/>';
					
				}
		}
		
		function AbrirPopupItemConfiguracao(){
			inicializarTemporizador();
			document.form.fireEvent("renderizaHistoricoItemConfiguracao");
		}
		
		exibeIconesIC = function(row, obj){
			var id = obj.idItemConfiguracao;
			/* var idos = obj.idOS; */
			
	        obj.sequenciaIC = row.rowIndex; 
		    row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/delete.png" border="0" onclick="excluiIC('+ row.rowIndex + ',this)" style="cursor:pointer"/>';

			if(obj.idItemConfiguracaoPai == ""){			
					row.cells[3].innerHTML =     '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/graph.png" border="0" onclick="popupAtivos( '+ id + ')" style="cursor:pointer"/>';
				}
  
		}
		
		excluiBaseConhecimento = function(indice) {
			if (indice > 0 && confirm('Confirma exclusão?')) {
				HTMLUtils.deleteRow('tblBaseConhecimento', indice);
			}
		}
		
		function LOOKUP_BASECONHECIMENTO_select(id, desc){
			document.form.idItemBaseConhecimento.value = id;
			document.form.fireEvent('atualizaGridBaseConhecimento');
		}
		
		function fecharBaseConhecimento(){
			$("#POPUP_BASECONHECIMENTO").dialog('close');
		}
		
		function inicializarTemporizador(){
			if(temporizador == null){
				temporizador = new Temporizador("imgAtivaTimer");
			} else {
				temporizador = null;
				try{
					temporizador.listaTimersAtivos = [];
				}catch(e){}
				try{
					temporizador.listaTimersAtivos.length = 0;
				}catch(e){}
				temporizador = new Temporizador("imgAtivaTimer");
			}
		}
		function inicializarTemporizadorRel1(){
			if(temporizadorRel1 == null){
				temporizadorRel1 = new Temporizador("imgAtivaTimerRel1");
			} else {
				temporizadorRel1 = null;
				try{
					temporizadorRel1.listaTimersAtivos = [];
				}catch(e){}
				try{
					temporizadorRel1.listaTimersAtivos.length = 0;
				}catch(e){}
				temporizadorRel1 = new Temporizador("imgAtivaTimerRel1");
			}
		}
		function inicializarTemporizadorRel2(){
			if(temporizadorRel2 == null){
				temporizadorRel2 = new Temporizador("imgAtivaTimerRel2");
			} else {
				temporizadorRel2 = null;
				try{
					temporizadorRel2.listaTimersAtivos = [];
				}catch(e){}
				try{
					temporizadorRel2.listaTimersAtivos.length = 0;
				}catch(e){}
				temporizadorRel2 = new Temporizador("imgAtivaTimerRel2");
			}
		}				
		
	 	function fecharPopup(popup){
			$(popup).dialog('close');
		}
	 	
	 	function fecharPopupPesquisaItemCfg(){
			$("#popupCadastroRapido").dialog("close");
		}
	 	
	 	function selectedItemConfiguracao(idItemCfg){
	 		document.form.idItemConfiguracao.value = idItemCfg;
	 		serializaTabelaIcParaImpactoUrgencia();
	 		document.form.fireEvent("restoreItemConfiguracao");
	 	}
	 	
	 	function serializaTabelaIcParaImpactoUrgencia(){
	 		var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
	 		if (objsIC != null) {
	 			document.form.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
	 		}
	 	}
	 	
		exibirSubSolicitacoes = function(idSolicitacaoServico){
			/* document.formIncidentesRelacionados.idSolicitacaoIncRel.value = idSolicitacaoServico;  */
			/* inicializarTemporizadorRel1(); */
			document.form.fireEvent("abrirListaDeSubSolicitacoes");
		}
	 	
		fechar = function(){

			var iframe = <%=iframe%>
			if(iframe != null){
				parent.fecharPopupRelacionada();
			}else{
				parent.fecharSolicitacao();
				parent.myLayout.open("south");
				
			}	
		}
	
	 	function fecharPopupRelacionada(){
			$("#popupCadastroRapido").dialog("close");
			document.form.fireEvent("abrirListaDeSubSolicitacoes");
		}
	
		
		function toggleDiv(id){
			var div = document.getElementById(id);
			div.style.display = div.style.display == "none" ? "block" : "none";
			div.style.background = "#FAFAFA";
		}
		
		function setConteudoEmail(id, messageId){
			var conteudo = document.getElementById(id).innerHTML;
			document.form.messageId.value = messageId;
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
			
	        oEditor.SetData(conteudo);
			
		}
		function setEmail(){
			if (document.form.idContrato.value == ''){
				alert('Informe o contrato!');
				return;
			}
			document.formEmail.idContrato.value = document.form.idContrato.value;
			document.getElementById('emails').innerHTML = '<i18n:message key="citcorpore.comum.aguardepesquisando" />';
			document.formEmail.fireEvent('carregarEmails');
			//$('#loading_overlay').show();
		}
	
		var oFCKeditor = new FCKeditor("descricao") ;
	    function onInitQuestionario(){
	        oFCKeditor.BasePath = '<%=br.com.citframework.util.Constantes
					.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
	        //oFCKeditor.Config['ToolbarStartExpanded'] = false ;
	
	        oFCKeditor.ToolbarSet   = 'Basic' ;
	        oFCKeditor.Width = '100%' ;
	        oFCKeditor.Height = '300' ;
	        oFCKeditor.ReplaceTextarea() ;  
	    }
	    HTMLUtils.addEvent(window, "load", onInitQuestionario, false);
		
	    function gravarEContinuar() {
			document.form.acaoFluxo.value = '<%=Enumerados.ACAO_INICIAR%>';
			gravar();
	    }
	    
	    function gravarEFinalizar() {
			document.form.acaoFluxo.value = '<%=Enumerados.ACAO_EXECUTAR%>';
			gravar();
	    }
	    
	    function calcularSLA() {
	    	if (document.form.idSolicitacaoServico.value == null || document.form.idSolicitacaoServico.value == '' || document.form.idSolicitacaoServico.value == 0) {
	    		document.getElementById('img_carregando').style.display = '';
	    		document.getElementById('tdResultadoSLAPrevisto').style.display = 'none';
	    		
	    		var temp = 'var statusDisabledUrgencia = document.form.urgencia.disabled;';
	    		temp += 'var statusDisabledImpacto = document.form.impacto.disabled;';
	    		temp += 'document.form.urgencia.disabled = false;';
	    		temp += 'document.form.impacto.disabled = false;';
	    		temp += 'document.form.fireEvent("calculaSLA");';
	    		temp += 'document.form.urgencia.disabled = statusDisabledUrgencia;';
	    		temp += 'document.form.impacto.disabled = statusDisabledImpacto;';
	    		temp += 'document.getElementById("img_carregando").style.display = "none";';
	    		temp += 'document.getElementById("tdResultadoSLAPrevisto").style.display = "";';
	    		setTimeout(temp, 1500);
	    	}
	    }
	    
	    function gravar() {
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
			document.form.descricao.value = oEditor.GetXHTML();
			document.form.descricao.innerHTML = oEditor.GetXHTML();
// 			document.form.idItemConfiguracao.value = "";
			
			//@author ronnie.lopes
			//Campo Título Obrigatório caso Situação seje igual a 'Resolvida'
			if($("input[name='situacao']:checked").val() == "Resolvida") {
			<% if (mostraGravarBaseConhec.trim().equalsIgnoreCase("S")) { %>
				if($("#Inpt_Title_BaseConhecimento").val() == "" || $("#Inpt_Title_BaseConhecimento").val == null) {
					alert(i18n_message("solicitacaoServico.tituloObrigatorio"));
					return;
				}else{}
			<% } %>
			}else{}
		
			
			if (!document.form.validate()){
				return;
			}
			
			if(document.form.descricao.innerHTML == "<br />" || document.form.descricao.innerHTML == "&lt;br /&gt;"){
				alert('Informe a descrição!');
				return;
			}
			
			if (document.form.descricao.value == '' || document.form.descricao.value == '&nbsp;'
				|| document.form.descricao.value == '<p></p>'){
				alert('Informe a descrição!');
				return;
			}
			if (document.form.descricao.value == 'Resolvida'){
				if (document.form.resposta.value == '' || document.form.resposta.value == ' '){
					alert('Informe a resposta!');
					return;				
				}
			}

            if (!validarInformacoesComplementares())
                return;
             
			if(document.form.enviaEmailCriacao.disabled==true)
			{
				document.form.enviaEmailCriacao.disabled=false;
			}
			if(document.form.enviaEmailFinalizacao.disabled==true)
			{
				document.form.enviaEmailFinalizacao.disabled=false;
			}
			if(document.form.enviaEmailAcoes.disabled==true)
			{
				document.form.enviaEmailAcoes.disabled=false;
			}

			var informacoesComplementares_serialize = '';
			try{
				informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
			}catch(e){}
			
			<%if (!br.com.citframework.util.Util.isVersionFree(request)) {%>
				var objs = HTMLUtils.getObjectsByTableId('tblProblema');
				if (objs != null) {
					document.form.colItensProblema_Serialize.value = ObjectUtils.serializeObjects(objs);
				}
				
				var objsMudanca = HTMLUtils.getObjectsByTableId('tblMudanca');
				if (objsMudanca != null) {
					document.form.colItensMudanca_Serialize.value = ObjectUtils.serializeObjects(objsMudanca);
				}
				
				var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
				if (objsIC != null) {
					document.form.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
					
				var objsBaseConhecimento = HTMLUtils.getObjectsByTableId('tblBaseConhecimento');
				document.form.colConhecimentoSolicitacao_Serialize.value = ObjectUtils.serializeObjects(objsBaseConhecimento);
				}
			<%}%>			
			
			JANELA_AGUARDE_MENU.show();
			document.form.urgencia.disabled = false;
			document.form.impacto.disabled = false;
			document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
			if(document.getElementById('flagGrupo').value == 0){
				document.form.save();
			}else{
			if (document.getElementById("idGrupoAtual").value == ''){
				//var e = document.getElementById("idGrupoAtual");
				//var grupoAtual = e.options[e.selectedIndex].text;
				if (confirm('<i18n:message key="solicitacaoServico.grupoAtualVazio" />')){
				document.form.save(); 
				}else{
					JANELA_AGUARDE_MENU.hide();
					return;	
				}
			}else{
				document.form.save(); 
			}
		}
	}

<%-- 		function popupAtivos(){
			var idItem = document.getElementById("idItemConfiguracao").value;
				document.getElementById('iframeAtivos').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
				$("#POPUP_ATIVOS").dialog("open");
		}
		 --%>
		 
			function popupAtivos(id){
				var idItem = id;
					document.getElementById('iframeAtivos').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
					$("#POPUP_ATIVOS").dialog("open");
					calcularSLA();
			}
			
		
			function chamaPopupCadastroSolicitacaoServico(){
				dimensionaPopupCadastroRapido("1300","700");
				var idItem = document.getElementById("idSolicitacaoServico").value;
				var idContrato = document.getElementById("idContrato").value;
				<%if (PAGE_CADADTRO_SOLICITACAOSERVICO.trim().equalsIgnoreCase("")) {%>
					popupSolicitacaoRelacionada.abrePopupParms('solicitacaoServico', '', 'idSolicitacaoRelacionada=' + idItem);
				<%} else {%>
					popupSolicitacaoRelacionada.abrePopupParms('solicitacaoServicoMultiContratos', '', 'idSolicitacaoRelacionada=' + idItem+"&idContrato="+idContrato );
				<%}%>
			}
		
		function dimensionaPopupCadastroRapido(w, h) {
			$("#popupCadastroRapido").dialog("option","width", w)
			$("#popupCadastroRapido").dialog("option","height", h)
		}
			
		function limpar() {
			document.form.clear();
			document.form.fireEvent("limpar");
	        var oEditor = FCKeditorAPI.GetInstance("descricao") ;
	        oEditor.SetData('');		
		}
		
		function limparEmails(){
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
	        oEditor.SetData('');	
	        $("#emails").html("");	        
		}
		
		function exibeCampos() {
			document.getElementById('imagem').style.display = 'inline';
			document.getElementById('caracteristicaPai').style.display = 'inline';
		}
		
		function verificaContrato() {
			if (document.form.idContrato.value == ''){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				return;
			}
		}
		
		
		 /* function mudarCorLabel() {
			document.getElementById('caracteristica').style.background-color = '#F2F2F2';
		}  */
		
		$(function() {
			$("#POPUP_ATIVOS").dialog({
				autoOpen : false,
				width : 1005,
				height : 565,
				modal : true
			});
			
			
			
			$("#POPUP_SOLICITACAOSERVICO").dialog({
				autoOpen : false,
				width : 1500,
				height : 1005,
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

			$("#POPUP_DETALHE_SOLICITACAO_SERVICO").dialog({
				autoOpen : false,
				width : 600,
				height : 322,
				modal : true
			});

			$("#POPUP_SINCRONIZACAO").dialog({
				autoOpen : false,
				width : 600,
				height : 322,
				modal : true
			});			

			$("#POPUP_INFO_SERVICOS").dialog({
				autoOpen : false,
				width : 700,
				height : 420,
				modal : true,
				buttons: [
				    {
				        text: "Fechar",
				        click: function() { $(this).dialog("close"); }
				    }
				],				
				close: function(event, ui) {
				}
			});			
			
	  		$("#POPUP_BASECONHECIMENTO").dialog({
				autoOpen : false,
				width : 600,
				height : 600,
				modal : true
			});
	  		
	  		$("#imagenBaseConhecimento").click(function() {
				$("#POPUP_BASECONHECIMENTO").dialog("open");
			});
	  		
	  		$("#addConhecimento").click(function() {
				$("#POPUP_BASECONHECIMENTO").dialog("open");
			});
	  		
		});
		
		function mostraMensagemInsercao(msg){
			document.getElementById('divMensagemInsercao').innerHTML = msg;
			$("#POPUP_INFO_INSERCAO").dialog("open");
		}
		
		addCorCombo = function(){
			$("#idItemConfiguracaoPai option:contains('Id:')").addClass("corRed");
		}
		
		function geraAutoCompleteServico(){
			var availableTags = new Array();
			var strTable = '<table cellpadding="2" cellspacing="2">';
			var iAux = 0;
			for (var i = 0; i < document.form.idServico.length; i++){
				if (document.form.idServico.options[i].value != ''){
					strTable += '<tr><td style="cursor:pointer" onclick="selecionaServicoTable(\'' + document.form.idServico.options[i].value + '\', \'' + document.form.idServico.options[i].text + '\')">';
					strTable += "<img src='<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/imagens/seta_azul.gif' border='0'/>" + document.form.idServico.options[i].text;
					availableTags[iAux] = {value: document.form.idServico.options[i].value, label: document.form.idServico.options[i].text};
					strTable += '</td></tr>';
					iAux++;
				}
			} 
			var accentMap = {
					"Ã": "A",
					"Á": "A",
					"Â": "A",
					"Ä": "A",
					"Ê": "E",
					"É": "E",
					"Ê": "E",
					"È": "E",
					"Í": "I",
					"Î": "I",
					"Ï": "I",
					"Ç": "C"
			};
			var normalize = function( term ) {
				var ret = "";
				for ( var i = 0; i < term.length; i++ ) {
				ret += accentMap[ term.charAt(i) ] || term.charAt(i);
				}
				return ret;
				};
				$( "#servicoBusca" ).autocomplete({
				source: function( request, response ) {
					var matcher = new RegExp( $.ui.autocomplete.escapeRegex( request.term ), "i" );
					response( $.grep( availableTags, function( value ) {
					value = value.label || value.value || value;
					return matcher.test( value ) || matcher.test( normalize( value ) );
					}));
				},
				select: function(event, ui) {
					$( "#idServico" ).val( ui.item.value );
					document.form.servicoBusca.value = ui.item.label;
					document.form.fireEvent('verificaImpactoUrgencia');		
					document.form.fireEvent('carregaBaseConhecimentoAssoc');
					calcularSLA();
					carregarInformacoesComplementares();
					return false;
				}
				});
			strTable += '</table>';
			document.getElementById('divInfoServicos').innerHTML = strTable;				
		}
		
		function selecionaServicoTable(id, text){
			$( "#idServico" ).val( id );
			document.form.servicoBusca.value = text;
			document.form.fireEvent('verificaImpactoUrgencia');	
			document.form.fireEvent('carregaBaseConhecimentoAssoc');
			calcularSLA();
			carregarInformacoesComplementares();
			$("#POPUP_INFO_SERVICOS").dialog('close');
		}
		
		function copiaEmail(id){
			document.form.emailcontato.value = document.getElementById('idSenderEmail' + id).value;
			document.form.descricao.value = document.getElementById('idGetBody' + id).innerHTML;
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
	        oEditor.SetData(document.form.descricao.value);
			document.form.fireEvent('preenchePorEmail');
		}

	      function carregarInformacoesComplementares() {
	            try{
	                document.getElementById('divInformacoesComplementares').style.display = 'block';
	                window.frames["fraInformacoesComplementares"].document.write("");
	                window.frames["fraInformacoesComplementares"].document.write("<font color='red'><b><i18n:message key='citcorpore.comum.aguardecarregando' /></b></font>");
	            }catch (e) {
	            }       
	            document.form.fireEvent('carregaInformacoesComplementares');
	        }   

	        function exibirInformacoesComplementares(url) {
	            if (url != '') {
	              
	                JANELA_AGUARDE_MENU.show();
	                document.getElementById('divInformacoesComplementares').style.display = 'block';
	                document.getElementById('fraInformacoesComplementares').src = url;
	            }else{
	                try{
	                    window.frames["fraInformacoesComplementares"].document.write("");
	                }catch (e) {
	                }       
	                document.getElementById('divInformacoesComplementares').style.display = 'none';
	            } 
	        }   

	        function validarInformacoesComplementares() {
	        	if (window.frames["fraInformacoesComplementares"]){
	        		try{
	            		return window.frames["fraInformacoesComplementares"].validar();
	        		}catch(e){
	        			return true;
	        		}
	        	}else{
	        		return true;
	        	}
	        }   

	        function escondeJanelaAguarde() {
	            JANELA_AGUARDE_MENU.hide();
	        }
	</script>

<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}

select#idItemConfiguracaoPai .corRed {
	background-color: #999999;
}
</style>
</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
	<script type="text/javascript">
		function setDataEditor(){
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
		    oEditor.SetData(document.form.descricao.value);
		}
		function setValueToDataEditor(){
			window.setTimeout(function() {
        			setDataEditor();
    			},1000);
		} 
		function ajustaBarraFerramentas() {
		    if(typeof(window.pageYOffset)=='number') {
		        pageY=window.pageYOffset;
		     }
		     else {
		        pageY=document.documentElement.scrollTop;
		     }			
			
			document.getElementById('divBarraFerramentas').style.top = pageY + 'px';
			try{
				//document.getElementById('divBarraScript').style.top = (pageY + 105) + 'px';
				document.getElementById('divBarraScript').style.top = pageY + 'px';
			}catch(e){
			}
		}
		function informaNumeroSolicitacao(numero){
			document.getElementById('divTituloSolicitacao').innerHTML = 
				'<h2><i18n:message key="solicitacaoServico.solicitacaonumero" />&nbsp;' + numero + '</h2>';   
		}
		function informaNomeTarefa(nome){
            document.getElementById('divNomeTarefa').innerHTML = 
                '<h2>' + nome + '</h2>';   
        }
		HTMLUtils.addEvent(window, "scroll", ajustaBarraFerramentas, false);	
		function mostraEscondeScript(){
			if (document.getElementById('divScript').style.display == 'block'){
				document.getElementById('divScript').style.display = 'none';
			}else{
				document.getElementById('divScript').style.display = 'block';
				voltaDestaqueScript();
			}
		}	
		function carregaScript(obj){
			document.getElementById("divScript").innerHTML = "<i18n:message key='citcorpore.comum.aguardecarregando' />";
			document.form.fireEvent('carregaBaseConhecimentoAssoc');
			if(obj.options[obj.selectedIndex].value != ""){
				document.form.servicoBusca.value = obj.options[obj.selectedIndex].text;
			}
			
		} 
		function mostrarComboServico(){
			$("#POPUP_INFO_SERVICOS").dialog("open");					
		}
		function limparServico(){
			document.form.servicoBusca.value = '';
			calcularSLA();
			$( "#idServico" ).val( '' );
		}
		var countControleDestaque = 0; 
		function destaqueScript(){
			countControleDestaque = 0;
			indicaDestaqueScript();
		}
		function indicaDestaqueScript(){
			if (document.getElementById('divScript').style.display == 'none'){
				document.getElementById('divBarraScript').style.backgroundColor = 'red';
				window.setTimeout('voltaDestaqueScript()',1000);
			}else{
				document.getElementById('divBarraScript').style.backgroundColor = 'lightyellow';
			}
		}  
		function voltaDestaqueScript(){
			document.getElementById('divBarraScript').style.backgroundColor = 'lightyellow';
			if (countControleDestaque < 6){
				if (document.getElementById('divScript').style.display == 'none'){
					window.setTimeout('indicaDestaqueScript()',1000);
				}
			}
			countControleDestaque++;
		} 		
		function setaValorLookup(obj){
			limpar_LOOKUP_SOLICITANTE();
			document.form.idSolicitante.value = '';
			document.form.solicitante.value = '';
			document.form.emailcontato.value = '';
			document.form.nomecontato.value = '';
			document.form.telefonecontato.value = '';
			document.form.observacao.value = '';
			document.form.ramal.value = '';
			document.getElementById('idLocalidade').options.length = 0;
			document.getElementById('pesqLockupLOOKUP_SOLICITANTE_IDCONTRATO').value = '';
			document.getElementById('pesqLockupLOOKUP_SOLICITANTE_IDCONTRATO').value = obj.value; 
			document.form.servicoBusca.value = '';
			document.getElementById('idTipoDemandaServico').options[0].selected = 'selected';
		}
		
		
		
		function adicionaValoLookup(id){
			document.getElementById('pesqLockupLOOKUP_SOLICITANTE_IDCONTRATO').value = id;
		}

		function detalheSolicitacao(parametro){
			var dadosSolicitacao;
			var divDetalhe;
			
			dadosSolicitacao = parametro.split("#");

		
	   divDetalhe ='<h2 class="section"><i18n:message key="solicitacaoServico.detalhamento" /></h2>';
			
	   divDetalhe +='<div class="col_100" >';
	   
	    
		 	
 		divDetalhe +='<div class="col_33" >';
 			divDetalhe +='<fieldset style="font-size: 12px; font-weight: bold; height: 20px; padding-left: 5px; padding-top: 6px;" ><i18n:message key="contrato.contrato" /></fieldset>';	
		divDetalhe +='</div>';
		divDetalhe +='<div class="col_66">';
			divDetalhe +='<fieldset style="font-size: 12px; height: 20px; padding-left: 5px; padding-top: 6px;"><div> '+dadosSolicitacao[0]+'</div></fieldset>';
		divDetalhe +='</div>';
		
 		divDetalhe +='<div class="col_33" >';
			divDetalhe +='<fieldset style="font-size: 12px; font-weight: bold; height: 20px; padding-left: 5px; padding-top: 6px;" ><i18n:message key="solicitacaoServico.nomeDoContato" /></fieldset>';	
		divDetalhe +='</div>';
		divDetalhe +='<div class="col_66">';
			divDetalhe +='<fieldset style="font-size: 12px; height: 20px; padding-left: 5px; padding-top: 6px;"><div> '+dadosSolicitacao[1]+'</div></fieldset>';
		divDetalhe +='</div>';
		
 		divDetalhe +='<div class="col_33" >';
			divDetalhe +='<fieldset style="font-size: 12px; font-weight: bold; height: 20px; padding-left: 5px; padding-top: 6px;" ><i18n:message key="solicitacaoServico.emailContato" /></fieldset>';	
		divDetalhe +='</div>';
		divDetalhe +='<div class="col_66">';
			divDetalhe +='<fieldset style="font-size: 12px; height: 20px; padding-left: 5px; padding-top: 6px;"><div> '+dadosSolicitacao[2]+'</div></fieldset>';
		divDetalhe +='</div>';
		
 		divDetalhe +='<div class="col_33" >';
			divDetalhe +='<fieldset style="font-size: 12px; font-weight: bold; height: 20px; padding-left: 5px; padding-top: 6px;" ><i18n:message key="solicitacaoServico.telefoneDoContato" /></fieldset>';	
		divDetalhe +='</div>';
		divDetalhe +='<div class="col_66">';
			divDetalhe +='<fieldset style="font-size: 12px; height: 20px; padding-left: 5px; padding-top: 6px;"><div> '+dadosSolicitacao[3]+'</div></fieldset>';
		divDetalhe +='</div>';
		
 		divDetalhe +='<div class="col_33" >';
			divDetalhe +='<fieldset style="font-size: 12px; font-weight: bold; height: 20px; padding-left: 5px; padding-top: 6px;" ><i18n:message key="solicitacaoServico.tipo" /></fieldset>';	
		divDetalhe +='</div>';
		divDetalhe +='<div class="col_66">';
			divDetalhe +='<fieldset style="font-size: 12px; height: 20px; padding-left: 5px; padding-top: 6px;"><div> '+dadosSolicitacao[4]+'</div></fieldset>';
		divDetalhe +='</div>';
		
 		divDetalhe +='<div class="col_33" >';
			divDetalhe +='<fieldset style="font-size: 12px; font-weight: bold; height: 20px; padding-left: 5px; padding-top: 6px;" ><i18n:message key="servico.servico" /></fieldset>';	
		divDetalhe +='</div>';
		divDetalhe +='<div class="col_66">';
			divDetalhe +='<fieldset style="font-size: 12px; height: 20px; padding-left: 5px; padding-top: 6px;"><div> '+dadosSolicitacao[5]+'</div></fieldset>';
		divDetalhe +='</div>';
		
 		divDetalhe +='<div class="col_33" >';
			divDetalhe +='<fieldset style="font-size: 12px; font-weight: bold; height: 20px; padding-left: 5px; padding-top: 6px;" ><i18n:message key="solicitacaoServico.situacao" /></fieldset>';	
		divDetalhe +='</div>';
		divDetalhe +='<div class="col_66">';
			divDetalhe +='<fieldset style="font-size: 12px; height: 20px; padding-left: 5px; padding-top: 6px;"><div> '+dadosSolicitacao[6]+'</div></fieldset>';
		divDetalhe +='</div>';
		
				
			document.getElementById('detalheSolicitacaoServico').innerHTML = divDetalhe;
			$("#POPUP_DETALHE_SOLICITACAO_SERVICO").dialog("open");
		}
		
		function chamaPopupCadastroSol(){
			dimensionaPopupCadastroRapido("1300","600");
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
		
		

		function chamaPopupItemConfiguracao() {
			dimensionaPopupCadastroRapido("1300","600");
			popup.abrePopup('pesquisaItemConfiguracao','');
		}
		
		
		function chamaPopupCadastroOrigem(){
			dimensionaPopupCadastroRapido("1300","600");
			if (document.form.idContrato.value == ''){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				return;
			}		
			var idContrato = 0;
			try{
				idContrato = document.form.idContrato.value;
			}catch(e){
			}
			popup6.abrePopupParms('origemAtendimento', 'preencherComboOrigem', 'idContrato=' + idContrato);
		}	
		function chamaPopupCadastroProblema(){
			var idSolicitacaoServico = 0;
				try{
					idSolicitacaoServico = document.form.idSolicitacaoServico.value;
				}catch(e){
			}
			popup4.abrePopupParms('problema', '', 'idSolicitacaoServico=' + idSolicitacaoServico);
		}
		
		function chamaPopupCadastroMudanca(){
			var idSolicitacaoServico = 0;
				try{
					idSolicitacaoServico = document.form.idSolicitacaoServico.value;
				}catch(e){
			}
			popup5.abrePopupParms('requisicaoMudanca', '', '');
		}
		
		function pesquisarSolucao(){
			var oEditor = FCKeditorAPI.GetInstance("descricao") ;
			document.form.descricao.value = oEditor.GetXHTML();
			document.form.descricao.innerHTML = oEditor.GetXHTML();
			document.form.fireEvent('pesquisaBaseConhecimento');
		}
		
		function limparCampoServiceBusca(){
			document.form.servicoBusca.value = '';
			calcularSLA();
		}
		
		<%if (!br.com.citframework.util.Util.isVersionFree(request)) {%>
			excluiProblema = function(indice) {
				if (indice > 0 && confirm('Confirma exclusão?')) {
					HTMLUtils.deleteRow('tblProblema', indice);
				}
			}
			
			excluiMudanca = function(indice) {
				if (indice > 0 && confirm('Confirma exclusão?')) {
					HTMLUtils.deleteRow('tblMudanca', indice);
				}
			}
			
			excluiIC = function(indice) {
				if (indice > 0 && confirm('Confirma exclusão?')) {
					HTMLUtils.deleteRow('tblIC', indice);
				}
			}
		<%}%>
		
		function divMudanca(parametro){
			document.getElementById('divMudanca').style.display = parametro;
		}
		
		function divProblema(parametro){
			document.getElementById('divProblema').style.display = parametro;
		}
		
		function abreConhecimento(urlToOpen, parms){
			$("#popupCadastroRapido").dialog({
				height : 600,
				modal : true,
				autoOpen : false,
				width : 900,
				title : "Consulta à Base de Conhecimento",
				resizable : true,
				show : "fade",
				hide : "fade"
			});			
			
			var params = '';
			if (parms != undefined && parms != ''){
				params = '&' + parms;
			}
			
			// seto para o iframe a pÃ¡gina que deverÃ¡ ser aberta
			document.getElementById('frameCadastroRapido').src = urlToOpen + 'baseConhecimentoView/baseConhecimentoView.load?iframe=true&fecharpesquisa=true' + params;
			
			// abre a popup
			$("#popupCadastroRapido").dialog('open');
		}
		
		function getBotaoEditarProblema(row, obj){
			var botaoVisualizarProblemas = new Image();

			botaoVisualizarProblemas.src = '<%=br.com.citframework.util.Constantes
					.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png';
			botaoVisualizarProblemas.setAttribute("style", "cursor: pointer;");
			botaoVisualizarProblemas.id = obj.idProblema;
			botaoVisualizarProblemas.addEventListener("click", function(evt){CarregarProblema(id)}, true);

			return botaoVisualizarProblemas;
		}
		
		function carregarProblema(row, obj){
			var idProblema = obj.idProblema;
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
		
		function buscaProblema(row, object){
			carregarProblema(row, object);
		}

		function continuarTela(){
			if (confirm("<i18n:message key='solicitacaoServico.continuarTela' />")) { 
			parent.prepararExecucaoTarefa(idTarefa.value,idSolicitacaoServico.value,"E");
			}else{
				fechar();
				parent.myLayout.open("south");
			}
		}
		
		
	</script>
	<div id="divBarraFerramentas"
		style='position: absolute; top: 0px; left: 500px; z-index: 1000'>
		<jsp:include
			page="/pages/barraFerramentasIncidentes/barraFerramentasIncidentes.jsp"></jsp:include>
	</div>
	<div id='divBarraScript'
		style='position: absolute; top: 1px; left: 900px; background-color: lightyellow; z-index: 100000; border: 1px solid black'>
		<table>
			<tr>
				<td>
					<div id='divScript'
						style='display: none; width: 400px; height: 400px; border: 1px solid black; overflow: auto'>
						<i18n:message key="citcorpore.comum.selecionescript" />
					</div>
				</td>
				<td style='vertical-align: middle;'><img
					src='<%=Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/script.png'
					title='<i18n:message key="solicitacaoServico.abrefechascript" />'
					border='0' onclick='mostraEscondeScript()' style='cursor: pointer;' />
				</td>
			</tr>
		</table>
	</div>
	<div id="wrapper">
		<div id="main_container" class="main_container container_16 clearfix"
			style='margin: 10px 10px 10px 10px'>
			<div id='divTituloSolicitacao' class="flat_area grid_16"
				style="text-align: right;">
				<h2>
					<i18n:message key="solicitacaoServico.solicitacao" />
				</h2>
			</div>
			<div id='divNomeTarefa' class="flat_area grid_16"
				style="text-align: right;"></div>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div class="">
						<form name='form'
							action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos'>
							<div class="columns clearfix">

								<%
									if (id != null) {
								%>
								<input type='hidden' name='idBaseConhecimento'
									id='idBaseConhecimento' value='<%=id%>' />
								<%
									} else {
								%>
								<input type='hidden' name='idBaseConhecimento'
									id='idBaseConhecimento' value='' />
								<%
									}
								%>

								<input type='hidden' name='idSolicitacaoServico'
									id='idSolicitacaoServico' /> <input type='hidden'
									name='idSolicitante' id='idSolicitante' /> <input
									type='hidden' name='messageId' id='messageId' /> <input
									type='hidden' name='idItemConfiguracao' id='idItemConfiguracao' />
									<input
									type='hidden' name='idItemConfiguracao2' id='idItemConfiguracao2' />
								<input type='hidden' name='reclassificar' id='reclassificar' />
								<input type='hidden' name='escalar' id='escalar' /> <input
									type='hidden' name='alterarSituacao' id='alterarSituacao' /> <input
									type='hidden' name='idTarefa' id='idTarefa' /> <input
									type='hidden' name='acaoFluxo' id='acaoFluxo' /> <input
									type='hidden' name='filtroADPesq' id='filtroADPesq' /> <input
									type='hidden' name='colItensProblema_Serialize'
									id='colItensProblema_Serialize' /> <input type='hidden'
									name='colItensMudanca_Serialize' id='colItensMudanca_Serialize' />
								<input type='hidden' name='colItensIC_Serialize'
									id='colItensIC_Serialize' /> <input type='hidden'
									name='idSolicitacaoPai' id='idSolicitacaoPai' /> <input
									type='hidden' name='idSolicitacaoRelacionada'
									id='idSolicitacaoRelacionada' /> <input type='hidden'
									name='informacoesComplementares_serialize'
									id='informacoesComplementares_serialize' /> <input
									type='hidden' name='sequenciaProblema' id='sequenciaProblema' />
								<input type='hidden' name='idProblema' id='idProblema' /> <input
									type='hidden' name='colConhecimentoSolicitacao_Serialize'
									id='colConhecimentoSolicitacao_Serialize' /> <input
									type='hidden' name='situacaoFiltroSolicitante'
									id='situacaoFiltroSolicitante' /> <input type='hidden'
									name='buscaFiltroSolicitante' id='buscaFiltroSolicitante' /> <input
									type='hidden' name='validaBaseConhecimento'
									id='validaBaseConhecimento' /> <input type='hidden'
									name='flagGrupo' id='flagGrupo' /> <input type='hidden'
									name='novaSolicitacao' id='novaSolicitacao' />
									


								<div id='divMessage'
									style='display: none; width: 98%; text-align: right;'></div>

								<%-- <div id="POPUP_SINCRONIZACAO"	title="" style="overflow: hidden;">
									<div class="box grid_16 tabs">
										<div class="toggle_container">	
											<div id='POPUP_SINCRONIZACAO_CABECALHO' style="width: 99.8%; ">
												<label class="campoObrigatorio"><i18n:message key="solicitacaoServico.sincronizarFiltro" />:(<i18n:message key="solicitacaoServico.sincronizarDigite" />)
													<input type="text" name="filtroADPesqAux" id="filtroADPesqAux" size="100" />
												</label>			
											</div>					
											<div id='POPUP_SINCRONIZACAO_DETALHE' style="width: 99.8%; margin-bottom: 20px; font-size: 16px; font-weight: bold; margin-top: 7px;">
																
											</div>					
											<div >
												<button type="button" onclick='pesquisarAD()'>
													<i18n:message key="citcorpore.comum.pesquisa" />
												</button>			
												<button type="button" onclick='fecharAD();'>
													<i18n:message key="citcorpore.comum.fechar" />
												</button>
											</div>
										</div>
									</div>
								</div>				 --%>
								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message
												key="contrato.contrato" /></label>
										<div>
											<select name='idContrato'
												class=" Valid[Required] Description[<i18n:message key='contrato.contrato' />]"
												onchange="setaValorLookup(this);document.form.fireEvent('verificaGrupoExecutor');document.form.fireEvent('verificaImpactoUrgencia'); document.form.fireEvent('carregaServicosMulti');document.form.fireEvent('carregaUnidade');limparEmails();carregarInformacoesComplementares();document.form.fireEvent('preencherComboLocalidade');calcularSLA();">
											</select>
										</div>
									</fieldset>
								</div>
								<div class="col_100">
									<div class="col_50">
										<fieldset style="height: 70px;">
											<label class="campoObrigatorio"
												style="cursor: pointer; float: left; width: 100px">
												<i18n:message key="origemAtendimento.origem" /> <img
												id='botaoSolicitante'
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
												onclick="chamaPopupCadastroOrigem()" />
											</label>
											<%
												String mostraBotaoLDAP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_MOSTRA_BOTAO, "N");
												if (mostraBotaoLDAP.equalsIgnoreCase("S")) {
											%>
											<input type="button" id="btnSincronizarAD" class=""
												style="cursor: pointer; margin-left: 10px"
												value="<i18n:message key="solicitacaoServico.sincronizarAD" />"
												onclick="buscarAD();" />
											<%
												}
											%>
											<div>
												<select name='idOrigem'
													class="Valid[Required] Description[<i18n:message key='origemAtendimento.origem' />]"></select>
											</div>
											<div></div>
										</fieldset>


									</div>
									<div class="col_50">
										<fieldset style="height: 70px;">
											<label class="campoObrigatorio"
												style="cursor: pointer; float: left; width: 200px;">
												<i18n:message key="solicitacaoServico.solicitante" /> <img
												id="botaoSolicitante" style="vertical-align: middle;"
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
												onclick="chamaPopupCadastroSol()" /> <img
												id="btHistoricoSolicitante"
												style="cursor: pointer; display: none; vertical-align: middle;"
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png" />
												<img id="btHistoricoSolicitanteEmAndamento"
												style="cursor: pointer; display: none; vertical-align: middle;"
												title="<i18n:message key='citcorpore.comum.haSolicitacoesAbertasSolicitante' />"
												height="18"
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/exclamation02.gif" />
											</label>
											<%
												mostraBotaoLDAP = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.LDAP_MOSTRA_BOTAO, "N");
												if (mostraBotaoLDAP.equalsIgnoreCase("S")) {
											%>
											<input type="button" id="btnSincronizarAD" class=""
												style="cursor: pointer; margin-left: 10px"
												value="<i18n:message key="solicitacaoServico.sincronizarAD" />"
												onclick="buscarAD();" />
											<%
												}
											%>
											<div>
												<input id="addSolicitante" type='text' readonly="readonly"
													name="solicitante" maxlength="80"
													class="Valid[Required] Description[<i18n:message key='solicitacaoServico.solicitante' />]"
													onchange="calcularSLA();" />
											</div>
											<div></div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<h2 class="section">
										<i18n:message key="solicitacaoServico.informacaoContato" />
										<div id='divEvtMonitoramento' style='display: none'></div>
									</h2>
									<div class="col_100">
										<div class="col_50">
											<div class="col_100">
												<div class="col_50">
													<fieldset>
														<label class="campoObrigatorio" style="cursor: pointer;">
															<i18n:message key="solicitacaoServico.nomeDoContato" />
														</label>
														<div>
															<input id="nomecontato" type='text' name="nomecontato"
																maxlength="70"
																class="Valid[Required] Description[<i18n:message key='solicitacaoServico.nomeDoContato' />]" />
														</div>
													</fieldset>
												</div>
												<div class="col_50">
													<fieldset>
														<label class="campoObrigatorio" style="cursor: pointer;">
															<i18n:message key="solicitacaoServico.emailContato" />
														</label>
														<div>
															<input id="emailcontato" type='text' name="emailcontato"
																maxlength="120"
																class="Valid[Required, Email] Description[solicitacaoServico.emailContato]" />
														</div>
													</fieldset>
												</div>

											</div>

											<div class="col_100">
												<div class="col_20">
													<fieldset>
														<label class="" style="cursor: pointer;"> <i18n:message
																key="solicitacaoServico.telefoneDoContato" />
														</label>
														<div>
															<input id="telefonecontato" type='text'
																name="telefonecontato" maxlength="13" class="" />
														</div>
													</fieldset>
												</div>
												<div class="col_15">
													<fieldset>
														<label style="cursor: pointer;"><i18n:message
																key="citcorpore.comum.ramal" /></label>
														<div>
															<input id="ramal" type='text' name="ramal" maxlength="4"
																class="Format[Numero]" />
														</div>
													</fieldset>
												</div>

												<div class="col_40">
													<fieldset style="height: 55px">
														<label class="tooltip_bottom campoObrigatorio"
															style="cursor: pointer;"
															title="<i18n:message key="colaborador.cadastroUnidade"/>">
															<i18n:message key="unidade.unidade" /> <%-- <img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
																onclick="popup2.abrePopup('unidade', 'preencherComboUnidade')">	 --%>
														</label>
														<div>
															<select name='idUnidade' id='idUnidade'
																class="Valid[Required] Description[<i18n:message key='unidade.unidade'/>]"
																onclick="verificaContrato()"
																onchange="document.form.fireEvent('preencherComboLocalidade');"></select>
														</div>
													</fieldset>
												</div>
												<div class="col_25">
													<fieldset style="height: 55px">
														<label class="tooltip_bottom " style="cursor: pointer;"
															title="<i18n:message key="colaborador.cadastroUnidade"/>">
															<i18n:message key="solicitacaoServico.localidadeFisica" />
														</label>
														<div>
															<select name='idLocalidade' id='idLocalidade'
																onclick="verificaContrato()"></select>
														</div>
													</fieldset>
												</div>

											</div>
										</div>
										<div class="col_50">
											<fieldset>
												<label style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.observacao" /></label>
												<div style="height: 98px;">
													<textarea id="observacao" name="observacao" rows="4"
														maxlength="2000" cols="70"></textarea>
												</div>
											</fieldset>
										</div>
									</div>

									<div class="col_100">
										<h2 class="section">
											<i18n:message key="solicitacaoServico.informacoesSolicitacao" />
										</h2>
										<div id="divTipoSolicitacaoServico" class="col_30">
											<fieldset>
												<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.tipo" /></label>
												<div>
													<select name='idTipoDemandaServico'
														onclick="limparCampoServiceBusca()"
														onchange="document.form.fireEvent('carregaServicosMulti')"></select>
												</div>
											</fieldset>
										</div>
										<div id="divSLAPrevisto" class="col_20"
											style="position: relative; border-top: 1px solid rgb(221, 221, 221);">
											<label
												style="cursor: pointer; font-size: 11px; font-weight: bold; padding: 1px 15px; margin-right: 10px; display: block; color: rgb(51, 51, 51);"><i18n:message
													key="citcorpore.comum.slaPrevisto" /></label>
											<div
												style="padding: 0px 20px 0px; font-size: 100%; vertical-align: baseline; background: none repeat scroll 0% 0% transparent; height: 100%;">
												<table>
													<tr>
														<td style="font-size: 25px;" id="tdResultadoSLAPrevisto"></td>
														<img id="img_carregando"
															src="/citsmart/imagens/ajax-loader.gif"
															style="display: none;">
													</tr>
												</table>
											</div>
										</div>
										<div class="col_50" id="divCategServico">
											<fieldset style="FONT-SIZE: xx-small;">
												<label style="cursor: pointer;"><i18n:message
														key="categoriaServico.categoriaServico" /></label>
												<div>
													<select name='idCategoriaServico'
														onchange="document.form.fireEvent('carregaServicosMulti')"></select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset style="height: 70px;">
												<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message
														key="servico.servico" /></label>
												<div>
													<table width="100%">
														<tr>
															<td><select name='idServico'
																onchange="document.form.fireEvent('verificaImpactoUrgencia');carregaScript(this);document.form.fireEvent('verificaGrupoExecutor');carregarInformacoesComplementares();calcularSLA();"
																class=" Valid[Required] Description[<i18n:message key='servico.servico' />]"
																style='width: 30px'></select></td>
															<td><input type='text' name='servicoBusca'
																id='servicoBusca' size='90' maxlength='120' /></td>
															<td id='tdListServicos'><img
																src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/list.png'
																border='0' style='cursor: pointer'
																onclick='mostrarComboServico()'
																title='<i18n:message key="solicitacaoServico.mostralistaservico" />' />
															</td>
															<td>&nbsp;</td>
															<td id='tdLimparServicos'><img
																src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/borracha.png'
																border='0' style='cursor: pointer'
																onclick='limparServico()'
																title='<i18n:message key="citcorpore.comum.limpar" />' />
															</td>
														</tr>
													</table>

												</div>
											</fieldset>
											<div id="divSolicitacaoRelacionada" style="display: none">
												<div class="col_100">
													<fieldset
														style="padding-top: 10px; padding-left: 10px; padding-bottom: 10px">
														<button type='button'
															title="<i18n:message key='solicitacaoServico.cadastrosolicitacao'/>"
															class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr"
															onclick="chamaPopupCadastroSolicitacaoServico()">
															<div class="asa">
																<span class=""><i18n:message
																		key="gerenciaservico.novasolicitacao" /></span>
																<div class="G-asx T-I-J3 J-J5-Ji"></div>
															</div>
														</button>
													</fieldset>
												</div>
												<fieldset>
													<label><i18n:message
															key="solicitacaoServico.solicitacao" /></label>
													<div class="col_100" style="height: 180px;">
														<div id="solicitacaoRelacionada"
															style='height: 150px; width: 90%; overflow: auto; border: 1px solid black;'>

														</div>
													</div>
												</fieldset>
											</div>
											<div id='divItemConfiguracao' class="col_100"
												style="display: block;">
												<%
													if (br.com.citframework.util.Util.isVersionFree(request)) {
												%>
												<fieldset>
													<label> <i18n:message
															key="solicitacaoServico.itemConfiguracao" />
													</label>
													<div style="width: 100%;">
														<%=Free.getMsgCampoIndisponivel(request)%>
													</div>

												</fieldset>
												<%
													} else {
												%>
												<fieldset>
													<label> <i18n:message
															key="solicitacaoServico.itemConfiguracao" /> <img
														title="<i18n:message key="solicitacaoServico.informacaoItemConfiguracao" />"
														id="imagem" onclick="popupAtivos();"
														style="vertical-align: top; cursor: pointer; display: none;"
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/graph.png">
														<img id="btHistoricoIc"
														style="cursor: pointer; display: none;"
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png">

													</label>
													<div style="width: 100%;">
														<input onclick="chamaPopupItemConfiguracao()"
															readonly="readonly" style="width: 90% !important;"
															type='text' id="itemConfiguracao" name="itemConfiguracao"
															maxlength="70" size="70" /> <img id="imagenIC"
															onclick="chamaPopupItemConfiguracao()" 
															style="vertical-align: middle; cursor: pointer;"
															src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
													</div>

													<div id='divIC'
														style='height: 120px; width: 86%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
														<table id='tblIC' cellpadding="0" cellspacing="0"
															width='100%'>
															<tr>
																<td style='text-align: center'
																	class='linhaSubtituloGrid' width='20px' height="15px">&nbsp;</td>
																<td width="20%" class='linhaSubtituloGrid'><i18n:message
																		key="citcorpore.comum.numero" /></td>
																<td width="62%" class='linhaSubtituloGrid'><i18n:message
																		key="citcorpore.comum.identificacao" /></td>
																<td width="10%">Informações</td>
															</tr>
														</table>
													</div>


												</fieldset>
												<%
													}
												%>
											</div>

											<div style="display: none;" id="caracteristicaPai"
												class="col_100">
												<div class="col_100">
													<fieldset>
														<label><i18n:message
																key="solicitacaoServico.categoria" /></label>
														<div class="inline clearfix">
															<label> <input type="radio" id="caracteristica"
																name="caracteristica" value="caracteristica"
																onclick="document.form.fireEvent('preecherComboSoftware');" />
																<i18n:message key="solicitacaoServico.software" /></label> <label>
																<input type="radio" id="caracteristica"
																name="caracteristica"
																onclick="document.form.fireEvent('preecherComboHardware');" />
																<i18n:message key="solicitacaoServico.hardware" />
															</label>
														</div>
													</fieldset>
													<fieldset>
														<div style="height: 50px">
															<select style="margin-top: 9px;"
																name='idItemConfiguracaoFilho'
																id="idItemConfiguracaoPai"></select>
														</div>
													</fieldset>
												</div>
											</div>
										</div>
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio"> <i18n:message
														key="solicitacaoServico.descricao" />
												</label>
												<div>
													<textarea id="descricao" name="descricao" cols='70'
														rows='5'
														class="Valid[Required] Description[<i18n:message key='solicitacaoServico.descricao' />]"></textarea>
												</div>


												<div class="col_100" id='divBotaoPesqSolucao'>
													<fieldset style="FONT-SIZE: xx-small;">
														<button type='button' name='btnPesqSolucao'
															id='btnPesqSolucao' onclick='pesquisarSolucao()'>
															<i18n:message key="solicitacaoServico.pesquisarSolucao" />
														</button>
													</fieldset>
												</div>


											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div id="divUrgencia" class="col_25">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message
														key="solicitacaoServico.urgencia" /></label>
												<div>
													<select name='urgencia' id='urgencia'
														onchange='calcularSLA();'></select>
												</div>
											</fieldset>
										</div>
										<div id="divImpacto" class="col_25">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message
														key="solicitacaoServico.impacto" /></label>
												<div>
													<select name='impacto' id='impacto'
														onchange='calcularSLA();'></select>
												</div>
											</fieldset>
										</div>
										<div id="divNotificacaoEmail" class="col_50">
											<fieldset>
												<label><i18n:message
														key="solicitacaoServico.notificaoemail" /></label> <label
													style='cursor: pointer'><input type='checkbox'
													value='S' name='enviaEmailCriacao' checked="checked" /> <i18n:message
														key="solicitacaoServico.enviaEmailCriacao" /></label><br> <label
													style='cursor: pointer'><input type='checkbox'
													value='S' name='enviaEmailFinalizacao' checked="checked" />
													<i18n:message
														key="solicitacaoServico.enviaEmailFinalizacao" /></label><br>
												<label style='cursor: pointer'><input
													type='checkbox' value='S' name='enviaEmailAcoes' checked="checked" /> <i18n:message
														key="solicitacaoServico.enviaEmailAcoes" /></label>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div id="divSituacao" class="col_50">
											<fieldset>
												<label><i18n:message key="solicitacaoServico.situacao" /></label>
												<div>
													<input type='radio' id='situacaoRegistrada' name="situacao" value="EmAndamento" onclick="verificaMostraGravarBaseConhecimento('EmAndamento')"
														class=" Description[<i18n:message key='solicitacaoServico.situacao'/>]"
														checked="checked" />
													<i18n:message key="solicitacaoServico.situacaoRegistrada" />
													<br> <br> <input type='radio' id='situacaoResolvida' name="situacao"
														value="Resolvida" onclick="verificaMostraGravarBaseConhecimento('Resolvida')"
														class=" Description[<i18n:message key='solicitacaoServico.situacao'/>]" />
													<i18n:message key="solicitacaoServico.situacaoResolvida" />
													<br> <br> <input type='radio' id='situacaoCancelada' name="situacao"
														value="Cancelada" onclick="verificaMostraGravarBaseConhecimento('Cancelada')"
														class=" Description[<i18n:message key='solicitacaoServico.situacao'/>]" />
													<i18n:message
														key="solicitacaoServico.solicitacaoServico.situacaoCancelada" />
													<br> <br>
												</div>
											</fieldset>
										</div>
										<div id="divGrupoAtual" class="col_50">
											<fieldset>
												<label class="tooltip_bottom" style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.grupo" /></label>
												<div>

													<select name='idGrupoAtual' id='idGrupoAtual'></select>

												</div>
											</fieldset>
										</div>
									</div>
								</div>

								<div class="col_100" id='divInformacoesComplementares'
									style='display: none; height: 350px'>
									<iframe id='fraInformacoesComplementares'
										name='fraInformacoesComplementares' src='about:blank'
										width="100%" height="100%"
										style='width: 95%; height: 100%; border: none;'></iframe>
								</div>

								<div id="divProblema" style="display: block" class="col_50">
									<h2 class="section">
										<i18n:message key="problema.problema" />
									</h2>
									<%
										if (br.com.citframework.util.Util.isVersionFree(request)) {
									%>
									<fieldset>
										<div style="width: 90%;">
											<%=Free.getMsgCampoIndisponivel(request)%>
										</div>
									</fieldset>
									<%
										} else {
									%>
									<fieldset>
										<div style="width: 90%;">
											<input readonly="readonly" style="width: 90% !important;"
												type='text' id="addProblema" name="addProblema"
												maxlength="70" size="70" /> <img id="imagenProblema"
												style="vertical-align: middle; cursor: pointer;"
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
											<img id="botaoSolicitante"
												style="vertical-align: middle; cursor: pointer;"
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
												onclick="chamaPopupCadastroProblema()" />
										</div>

										<div id='divProblemaSolicitacao'
											style='height: 120px; width: 86%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
											<table id='tblProblema' cellpadding="0" cellspacing="0"
												width='100%'>
												<tr>
													<td style='text-align: center' class='linhaSubtituloGrid'
														width='20px' height="15px">&nbsp;</td>
													<td width="10%"></td>
													<td width="60%" class='linhaSubtituloGrid'><i18n:message
															key="requisicaMudanca.titulo" /></td>
													<td width="29%" class='linhaSubtituloGrid'><i18n:message
															key="requisicaMudanca.status" /></td>
												</tr>
											</table>
										</div>
									</fieldset>
									<%
										}
									%>
								</div>
								<div id="divMudanca" style="display: block" class="col_50">
									<h2 class="section">
										<i18n:message key="requisicaMudanca.mudanca" />
									</h2>
									<%
										if (br.com.citframework.util.Util.isVersionFree(request)) {
									%>
									<fieldset>
										<div style="width: 90%;">
											<%=Free.getMsgCampoIndisponivel(request)%>
										</div>
									</fieldset>
									<%
										} else {
									%>
									<fieldset>
										<div style="width: 90%;">
											<input type='hidden' name='sequenciaMudanca'
												id='sequenciaMudanca' /> <input type="hidden"
												name="idRequisicaoMudanca" id="idRequisicaoMudanca">
											<input readonly="readonly" style="width: 90% !important;"
												type='text' id="addMudanca" name="addMudanca" maxlength="70"
												size="70" /> <img id="imagenMudanca"
												style="vertical-align: middle; cursor: pointer;"
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
											<img id="botaoMudanca"
												style="vertical-align: middle; cursor: pointer;"
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
												onclick="chamaPopupCadastroMudanca()" />
										</div>

										<div id='divMudancaSolicitacao'
											style='height: 120px; width: 86%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
											<table id='tblMudanca' cellpadding="0" cellspacing="0"
												width='100%'>
												<tr>
													<td style='text-align: center' class='linhaSubtituloGrid'
														width='20px' height="15px">&nbsp;</td>
													<td width="10%"></td>
													<td width="60%" class='linhaSubtituloGrid'><i18n:message
															key="requisicaMudanca.titulo" /></td>
													<td width="29%" class='linhaSubtituloGrid'><i18n:message
															key="requisicaMudanca.status" /></td>
												</tr>
											</table>
										</div>
									</fieldset>
									<%
										}
									%>
								</div>

								<div style="display: block;" id="solucao">
									<div class="col_100">
										<h2 class="section">
											<i18n:message key="solicitacaoServico.solucao" />
										</h2>
										<div class="col_50">
											<fieldset>
												<label style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.causa" /></label>
												<div>
													<select name='idCausaIncidente'></select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset style="FONT-SIZE: xx-small;">
												<label style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.categoriaSolucao" /></label>
												<div>
													<select name='idCategoriaSolucao'></select>
												</div>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset style="FONT-SIZE: xx-small;">
												<label style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.detalhamentocausa" /></label>
												<div>
													<textarea id="detalhamentoCausa" name="detalhamentoCausa"
														cols='70' rows='3'></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset style="FONT-SIZE: xx-small;">
												<label style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.solucaoTemporaria" /></label>
												<div>
													<label><input type='radio' name="solucaoTemporaria"
														value="S" class="Description[Solução Temporária]" /> <i18n:message
															key="citcorpore.comum.sim" /></label> <label><input
														type='radio' name="solucaoTemporaria" value="N"
														class="Description[Solução Temporária]" checked="checked" />
														<i18n:message key="citcorpore.comum.nao" /></label>
												</div>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset>
													<label style='cursor: pointer'>
														<input type='checkbox' value='S' id='criouProblemaAutomatico' name='criouProblemaAutomatico' />
														<i18n:message key="solicitacaoServico.criarProblema" />
													</label>
											</fieldset>
										</div>
										<div class="col_100" id='divBotaoAddRegExecucao'>
											<fieldset style="FONT-SIZE: xx-small;">
												<button type='button' name='btnAddRegExec'
													id='btnAddRegExec' onclick='mostrarEscondeRegExec()'>
													<i18n:message
														key="solicitacaoServico.addregistroexecucao_mais" />
												</button>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset style="FONT-SIZE: xx-small;">
												<label id='lblMsgregistroexecucao' style='display: none'><font
													color='red'><b><i18n:message
																key="solicitacaoServico.msgregistroexecucao" /></b></font></label>
												<div id='divMostraRegistroExecucao' style='display: none'>
													<textarea id="registroexecucao" name="registroexecucao"
														cols='70' rows='5' class="Description[Resposta]"></textarea>
												</div>
											</fieldset>
										</div>
										<%
											if (!strRegistrosExecucao.trim().equalsIgnoreCase("")) {
										%>
										<div class="col_100">
											<fieldset>
												<div>
													<%=strRegistrosExecucao%>
												</div>
											</fieldset>
										</div>
										<%
											}
										%>
										<div class="col_100">
											<fieldset style="FONT-SIZE: xx-small;">
												<label style="cursor: pointer;"><i18n:message
														key="solicitacaoServico.solucaoResposta" /></label>
												<div>
													<textarea id="resposta" name="resposta" cols='70' rows='5'
														class="Description[Resposta]"></textarea>
													<div id="divBaseConhecimento" style="display: block"
														class="col_100">
														<h2 class="section">
															<i18n:message key="baseConhecimento.baseConhecimento" />
														</h2>
														<fieldset>
															<div style="width: 90%;">
																<input type='hidden' name='sequenciaBaseConhecimento'
																	id='sequenciaBaseConhecimento' /> <input type="hidden"
																	name="idItemBaseConhecimento"
																	id="idItemBaseConhecimento"> <input
																	readonly="readonly" style="width: 50% !important;"
																	type='text' id="addConhecimento" name="addConhecimento"
																	maxlength="70" size="70" /> <img
																	id="imagenBaseConhecimento"
																	style="vertical-align: middle; cursor: pointer;"
																	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png" />
															</div>
															<div id='divConhecimentoSolicitacao'
																style='font-size: 14px;'
																style='height: 120px; width: 90%; margin-left: 21px; overflow: auto; border: 1px solid black;'>
																<table id='tblBaseConhecimento' style='font-size: 14px;'
																	cellpadding="0" cellspacing="0" width='100%'>
																	<tr>
																		<td style='text-align: center'
																			style='font-size: 14px;' class='linhaSubtituloGrid'
																			width='20px' height="15px;">&nbsp;</td>
																		<td width="5%"></td>
																		<td width="20%" style='font-size: 14px;'
																			class='linhaSubtituloGrid'><i18n:message
																				key="baseConhecimento.idBaseConhecimento" /></td>
																		<td width="75%" style='font-size: 14px;'
																			class='linhaSubtituloGrid'><i18n:message
																				key="baseConhecimento.titulo" /></td>
																	</tr>
																</table>
															</div>
														</fieldset>
													</div>
												</div>
											</fieldset>
										</div>

									</div>
								</div>

							</div>
				
							<%
								if (mostraGravarBaseConhec.trim().equalsIgnoreCase("S")) {
							%>
							<div id="ID_Div_Gravar_BaseConhecimento" class="col_100">
								<div class="col_50">
									<fieldset style="FONT-SIZE: xx-small;">
										<label><b><i18n:message
													key='baseConhecimento.GravarSolucaoResposta' /></b></label>
									</fieldset>
									<label class="campoObrigatorio" id="id_titulo"><i18n:message
											key='baseConhecimento.titulo' /> <input type="text"
										name="Inpt_Title_BaseConhecimento"
										id="Inpt_Title_BaseConhecimento" maxlength="50" size="50"
										style="width: 50% !important;" >
									</label>
								</div>
							</div>
							<% } %>
							
							<br/>
							<br/>
							<br/>
				
							<div class="box grid_16 tabs" id='divBotoes'>
								<%
									if (tarefaAssociada.equalsIgnoreCase("N")) {
								%>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type="button" name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>
								<%
									} else {
								%>
								<button type='button' id='btnGravarEContinuar'
									name='btnGravarEContinuar' class="light"
									onclick='gravarEContinuar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message
											key="citcorpore.comum.gravarEContinuar" /></span>
								</button>
								<button type='button' id='btnGravarEFinalizar'
									name='btnGravarEFinalizar' class="light"
									onclick='gravarEFinalizar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/cog_2.png">
									<span><i18n:message
											key="citcorpore.comum.gravarEFinalizar" /></span>
								</button>
								<%
									}
								%>
							</div>
							<div id="popupCadastroRapido">
								<iframe id="frameCadastroRapido" name="frameCadastroRapido"
									width="100%" height="100%"></iframe>
							</div>
						</form>
					</div>
				</div>
			</div>

			<div id='divMails' class="box grid_16 tabs">
				<div class="flat_area grid_16">
					<h2>
						<i18n:message key="citcorpore.comum.emails" />
					</h2>
				</div>
				<form id="formEmail" name='formEmail'
					action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/clienteEmailCentralServico/clienteEmailCentralServico'>
					<input type="hidden" id="messageid" name="messageid" /> <input
						type="hidden" id="idContrato" name="idContrato" />
					<div class="box grid_16 tabs">
						<button type="button" class="light" onclick="setEmail()">
							<img
								src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/archive.png">
							<span><i18n:message key="citcorpore.comum.verificarEmails" /></span>
						</button>
						<table>

						</table>
					</div>
					<div class="box grid_16 tabs" id="emails"></div>
				</form>
				<div id="loading_overlay">
					<div class="loading_message round_bottom">
						<img
							src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/loading.gif"
							alt="aguarde..." />
					</div>
				</div>
			</div>
		</div>
	</div>

	<div id="tblResumo" align="center" style="overflow: auto;">
		<div>
			<img width="20" height="20" alt="Ativa o temporizador"
				id="imgAtivaTimer" style="opacity: 0.5"
				title="<i18n:message key="citcorpore.comum.ativadestemporizador" />"
				src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/cronometro.png" />
		</div>
	</div>
	<div id="tblResumo2" align="center" style="overflow: auto;">
		<div>
			<img width="20" height="20" alt="Ativa o temporizador"
				id="imgAtivaTimer" style="opacity: 0.5"
				title="<i18n:message key="citcorpore.comum.ativadestemporizador" />"
				src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/cronometro.png" />
		</div>
	</div>
	<div id="tblResumo3" align="center" style="overflow: auto;">
		<div>
			<img width="20" height="20" alt="Ativa o temporizador"
				id="imgAtivaTimer" style="opacity: 0.5"
				title="<i18n:message key="citcorpore.comum.ativadestemporizador" />"
				src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/cronometro.png" />
		</div>
	</div>
</body>
<div id="POPUP_SINCRONIZACAO" title="" style="overflow: hidden;">
	<div class="box grid_16 tabs">
		<form id="formAD" name="formAD" action="">
			<div class="toggle_container">
				<div id='POPUP_SINCRONIZACAO_CABECALHO' style="width: 99.8%;">
					<label class="campoObrigatorio"><i18n:message
							key="solicitacaoServico.sincronizarFiltro" />:(<i18n:message
							key="solicitacaoServico.sincronizarDigite" />) <input
						type="text" name="filtroADPesqAux" id="filtroADPesqAux" size="100" />
					</label>
				</div>
				<div id='POPUP_SINCRONIZACAO_DETALHE'
					style="width: 99.8%; margin-bottom: 20px; font-size: 16px; font-weight: bold; margin-top: 7px;">

				</div>
				<div>
					<button type="button" onclick='pesquisarAD()'>
						<i18n:message key="citcorpore.comum.pesquisa" />
					</button>
					<button type="button" onclick='fecharAD();document.formAD.clear();'>
						<i18n:message key="citcorpore.comum.fechar" />
					</button>
				</div>
			</div>
		</form>
	</div>
</div>

<div id="POPUP_SOLICITANTE"
	title="<i18n:message key="citcorpore.comum.pesquisacolaborador" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="right">
						<label style="cursor: pointer;"> <i18n:message
								key="solicitacaoServico.solicitante" /> <img
							id='botaoSolicitante'
							src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
							onclick="chamaPopupCadastroSol()"> <img
							id="btHistoricoSolicitante"
							style="cursor: pointer; display: none;"
							src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png">
						</label>
					</div>
					<form name='formPesquisaColaborador' style="width: 540px">
						<cit:findField formName='formPesquisaColaborador'
							lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOLICITANTE'
							top='0' left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_ATIVOS"
	title="<i18n:message key="pesquisa.listaAtivosDaMaquina" />"
	style="overflow: hidden;">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block" style="overflow: hidden;">
				<div class="section" style="overflow: hidden;">
					<iframe id="iframeAtivos"
						style="display: block; margin-left: -20px;" name="iframeAtivos"
						width="970" height="480"> </iframe>
				</div>
			</div>
		</div>
	</div>
</div>


<div id="POPUP_INFO_INSERCAO" title="" style="overflow: hidden;">
	<div class="toggle_container">
		<div id='divMensagemInsercao' class="section"
			style="overflow: hidden; font-size: 24px;"></div>
		<button type="button"
			onclick='$("#POPUP_INFO_INSERCAO").dialog("close")'>
			<i18n:message key="citcorpore.comum.fechar" />
		</button>
	</div>
</div>

<div id="POPUP_DETALHE_SOLICITACAO_SERVICO" title=""
	style="overflow: hidden;">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block" style="overflow: hidden;">
				<div id='detalheSolicitacaoServico'></div>
				<div>
					<button type="button"
						onclick='$("#POPUP_DETALHE_SOLICITACAO_SERVICO").dialog("close")'>
						<i18n:message key="citcorpore.comum.fechar" />
					</button>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_PROBLEMA"
	title="<i18n:message key="problema.problema" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaProblema' style="width: 540px">
						<cit:findField formName='formPesquisaProblema'
							lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
							left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_MUDANCA"
	title="<i18n:message key="requisicaMudanca.mudanca" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaMudanca' style="width: 540px">
						<cit:findField formName='formPesquisaMudanca'
							lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0' left='0'
							len='550' heigth='400' javascriptCode='true' htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_BASECONHECIMENTO"
	title="<i18n:message key="baseConhecimento.baseConhecimento" />">
	<div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<div align="right">
						<label style="cursor: pointer;"> <i18n:message
								key="baseConhecimento.baseConhecimento" />
						</label>
					</div>
					<form name='formPesquisaBaseConhecimento' style="width: 540px">
						<cit:findField formName='formPesquisaBaseConhecimento'
							lockupName='LOOKUP_BASECONHECIMENTO' id='LOOKUP_BASECONHECIMENTO'
							top='0' left='0' len='550' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_INFO_SERVICOS" title="Serviços" style="overflow: hidden;">
	<div id='divInfoServicos'
		style="overflow: auto; font-size: 14px; width: 100%; height: 100%">
		<i18n:message key="solicitacaoServico.filtroselecionado" />
	</div>
</div>

<div id="POPUP_EDITARPROBLEMA" style="overflow: hidden;"
	title="<i18n:message key="problema.problema"/>">
	<iframe id='iframeEditarProblema' src='about:blank' width="100%"
		height="100%" style='width: 100%; height: 100%; border: none;'
		onload="resize_iframe()"> </iframe>
</div>

<div id='POPUP_PESQUISASOLUCAO'
	style='border: 1px solid black; background-color: white; height: 400px; width: 650px; overflow: auto'>
	<table>
		<tr>
			<td>
				<div style="margin: 10px !important;" id='resultPesquisa'></div>
			</td>
		</tr>
	</table>
</div>

<div id='POPUP_VISBASECONHECIMENTO' style="overflow: hidden;"
	title="<i18n:message key="baseConhecimento.baseConhecimento"/>">
	<iframe id='iframeVisualizarBaseConhecimento' src='about:blank'
		width="100%" height="100%"
		style='width: 100%; height: 100%; border: none;'
		onload="resize_iframe()"> </iframe>
</div>

<div id="POPUP_Telefonia" style="overflow: hidden;"></div>

</html>