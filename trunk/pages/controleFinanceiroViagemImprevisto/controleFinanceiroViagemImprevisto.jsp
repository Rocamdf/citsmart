<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%-- <%@page import="br.com.centralit.citcorpore.bean.tipoMovimFinanceiraViagemDTO"%> --%>
<%-- <%@page import="br.com.citframework.ajax.tipoMovimFinanceiraViagem"%> --%>
<!doctype html public "âœ°">
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

    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
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
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ControleFinanceiroViagemDTO.js"></script>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ItemPrestacaoContasViagemDTO.js"></script>

<style type="text/css">
        .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
        }
        .table {
            border-left:1px solid #ddd;
            width: 100%;
        }
        
        .table th {
            border:0px solid #ddd;
            padding:52px 100px;
            border-left:none;
            background:#eee;
        }
        
        .table td {
            border:1px solid #ddd;
            padding:100px 100px;
            border-top:none;
            border-left:none;
        }
        
        .table1 {
        }
        
        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }
        
        .table1 td {
        }   
             
         div#main_container {
            margin: 0px 0px 0px 0px;
        } 
                
        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;
            
            letter-spacing: -4px;
        }
        
        .table tr > td:first-child {
			text-align: center;
		}
		
	/* Desenvolvedor: Pedro Lino - Data: 31/10/2013 - Horário: 14:17 - ID Citsmart: 120948 - 
	* Motivo/Comentário: Paginação com layout antigo / Layout com as cores padrao */

	/* Paginação */

	#itenPaginacaoGerenciamento ul li {
		padding:  0px!important;
	}
	
	#itenPaginacaoGerenciamento ul li font {
		padding:  6px!important;
		background-color: white!important;
		border: 2px  solid #ddd!important;
	}
	
	#itenPaginacaoGerenciamento ul li font:HOVER {
		border: 2px  solid #74AF3B!important;
	}
	
	#itenPaginacaoGerenciamento ul li font a{
		color: #74AF3B!important;
	}
		
}
		
</style>

<script>

	addEvent(window, "load", load, false);
	
	function load(){ 
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	} 
	
	function pesquisarRequisicoes() {
		
		var dataI = document.getElementById("dataInicio").value;
		var dataF = document.getElementById("dataFim").value;
		
		if((dataI != null && dataI != "") || (dataF != null && dataF != "")){

			if((dataI == null || dataI == "") || (dataF == null || dataF == "")){
				alert(i18n_message("citcorpore.comum.validacao.intervaloInvalidoDaDataDoPeriodo"));				
				return;
			}

			if (!DateTimeUtil.comparaDatas(document.form.dataInicio, document.form.dataFim, i18n_message("citcorpore.comum.dataInicioMenorFinal"))){
				return false;
			} 
			
		}
		
		
    	document.form.fireEvent("pesquisaRequisicoesViagem");
    	
	}


	function calcularQuantidadeDias(){
	
		var dataInicio = document.getElementById("dataInicioViagem").value;
		var dataFim = document.getElementById("dataFimViagem").value;
	
		var dtInicio = new Date();
		var dtFim = new Date();
	
		if(dataInicio != "" & dataFim != ""){
		
			if(validaData(dataInicio,dataFim, 1)){
			
				dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
				dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
			
				var timeDifference = dtFim.getTime() - dtInicio.getTime();
				var seconds = timeDifference / 1000;
				var minutes = seconds / 60;
				var hours = minutes / 60;
				var days = hours / 24;
				document.form.qtdeDias.value = days + 1;
			}
		}
	}


	function resize_iframe(){
		
		var height=window.innerWidth;//Firefox
		if (document.body.clientHeight) {
			height=document.body.clientHeight;//IE
		}
		
		document.getElementById("iframeItemControleFinanceiro").style.height=parseInt(height - document.getElementById("iframeItemControleFinanceiro").offsetTop-8)+"px";
		
	}
	
	$(function() {
		$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog({
			autoOpen : false,
			width : "80%",
			height : $(window).height()-200,
			modal : true
		});
		
	}); 

	function fecharFrameItemControleFinanceiro(){
		limparTabelaDeItensCadastradosDaPopup('tblItemControleFinaceiro');
		$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("close");
	}

	function gerarImg (row, obj){
   		row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"/>';
	};

	function gerarImgTblRequisicao(row, obj){
	    row.cells[0].innerHTML = "<img style='cursor: pointer;' src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/viewCadastro.png' />";        
	}
	
	function gerarTblIntegrantes(row, obj){
		var idSolicitacao = obj.idSolicitacaoServico;
		document.form.idSolicitacaoServico.value = idSolicitacao;
		document.form.fireEvent("pesquisaIntegrantesViagem");
	}
	
	
	//Variavel controla se ja foi adicionado algum item na tabela = tblIntegrantesViagemAux
	var contemItensAdd = false;
	
	function addItens(row, obj){
		
		HTMLUtils.clearForm(document.formItem);
		
		document.getElementById("idSolicitacaoServico").value = obj.idSolicitacaoServico;
		document.getElementById("idEmpregado").value = obj.idEmpregado;
		document.form.fireEvent('recuperaInformacoesIntegrante');
		
		var tabela = document.getElementById("tblIntegrantesViagemAux");
		var count = tabela.rows.length;
		
		if(count > 1)
			contemItensAdd = true;
		else 
			contemItensAdd = false;
		
		$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("open");
	
	}
	
	
	function tratarTipoMovimentacaoFinanceira(obj){
		setaDados();
		document.form.fireEvent('tratarTipoMovimentacaoFinanceira'); 
		
	}
	function tratarValores(){
		setaDados();
		document.form.fireEvent('tratarValores');
	}
	
	function setaDados(){
		
		document.getElementById("classificacao").value = document.getElementById("classificacaoAux").value;
		document.getElementById("idTipoMovimFinanceiraViagem").value = document.getElementById("idTipoMovimFinanceiraViagemAux").value;
		document.getElementById("idTipoMovimenta").value = document.getElementById("idTipoMovimFinanceiraViagemAux").value;
		document.getElementById("idFormaPagamento").value = document.getElementById("idFormaPagamentoAux").value;
		document.getElementById("idJustificativa").value = document.getElementById("idJustificativaAux").value;
		document.getElementById("idFornecedor").value = document.getElementById("idFornecedorAux").value;
		document.getElementById("valorUnitario").value = document.getElementById("valorUnitarioAux").value;
		document.getElementById("complementoJustificativa").value = document.getElementById("complementoJustificativaAux").value;
		document.getElementById("quantidade").value = document.getElementById("quantidadeAux").value;
		document.getElementById("valorAdiantamento").value = document.getElementById("valorAdiantamentoAux").value;
		document.getElementById("prazoCotacao").value = document.getElementById("prazoCotacaoAux").value;
		document.getElementById("horaCotacao").value = document.getElementById("horaCotacaoAux").value;
		document.getElementById("observacao").value = document.getElementById("observacaoAux").value;
		
		if(document.getElementById("div_assento").visible == true)
			document.getElementById("assento").value = document.getElementById("assentoAux").value;
		else
			document.getElementById("assento").value = null;
		
		if(document.getElementById("div_tipoPassagem").visible == true)
			document.getElementById("tipoPassagem").value = document.getElementById("tipoPassagemAux").value;
		else
			document.getElementById("tipoPassagem").value = null;
		
		if(document.getElementById("div_localizador").visible == true)
			document.getElementById("localizador").value = document.getElementById("localizadorAux").value;
		else
			document.getElementById("localizador").value = null;
		
		
	}
	
	function paginarItens(paginaSelecionada) {
		document.form.paginaSelecionada.value = paginaSelecionada;
		document.form.fireEvent("pesquisaRequisicoesViagem");
	}
	
	
	var objs = new Array();
	var idTemporario = 1;
	function adicionarItem(){
		
		setaDados();

		if(!camposObrigatoriosPreenchidos())
			return;
		
		var dataFim = document.getElementById("prazoCotacaoAux").value;
		
		var hoje = new Date();
		var dia = hoje.getDate()
		var mes = hoje.getMonth()
		var ano = hoje.getFullYear()
		
		if (dia < 10)
			dia = "0" + dia
		
		if (ano < 2000)
			ano = "19" + ano

		if(!validaData((dia+"/"+(mes+1)+"/"+ano), dataFim, 2))
			return;
		
		var obj = new CIT_ItemPrestacaoContasViagemDTO();
		
		obj.idItemControleFinanceiroViagem = document.getElementById("idItemControleFinanceiroViagem").value;
		obj.idControleFinanceiroViagem = document.getElementById("idControleFinanceiroViagem").value;
		obj.idFormaPagamento = document.getElementById("idFormaPagamento").value;
		obj.idSolicitacaoServico  = document.getElementById("idSolicitacaoServico").value;
		obj.idEmpregado  = document.getElementById("idEmpregado").value;
		obj.idTipoMovimFinanceiraViagem  = document.getElementById("idTipoMovimFinanceiraViagem").value;
		obj.idJustificativa  = document.getElementById("idJustificativa").value;
		obj.idFornecedor  = document.getElementById("idFornecedor").value;
		obj.observacao  = document.getElementById("observacao").value;
		obj.complementoJustificativa  = document.getElementById("complementoJustificativa").value;
		obj.quantidade  = document.getElementById("quantidade").value;
		obj.valorUnitario  = document.getElementById("valorUnitario").value;
		obj.valorAdiantamento  = document.getElementById("valorAdiantamento").value;
		obj.tipoPassagem  = document.getElementById("tipoPassagem").value;
		obj.localizador = document.getElementById("localizador").value;
		obj.assento  = document.getElementById("assento").value;
		obj.dataFim  = document.getElementById("dataFim").value;
		obj.nomeFornecedor  = document.getElementById("nomeFornecedor").value;
		obj.nomeTipoMovimFinanceira  = document.getElementById("nomeTipoMovimFinanceira").value;	
		obj.prazoCotacao = document.getElementById("prazoCotacaoAux").value;
		obj.horaCotacao = document.getElementById("horaCotacaoAux").value;
		obj.auxiliarIdentificador = idTemporario++;

		objs.push(obj);
		
		HTMLUtils.addRow('tblItemControleFinaceiro', document.form, null, obj, ["","nomeTipoMovimFinanceira", "nomeFornecedor", "valorAdiantamento"], null , null, [gerarButtonDelete], null, null, null);
		//HTMLUtils.addRow('tblIntegrantesViagemAux', document.form, null, obj, [""], null , null, null, null, null, null);
		
		limparTela();
		
	}  
	
	//Valida se os campos obrigatorios da Popup item estão preenchidos (o Valid[Required] não funciona nesse caso)
	//False + mensagem caso algum campo obrigatorio não esteja preenchido
	//True caso todos os campos obrigatorios estejam preenchidos
	function camposObrigatoriosPreenchidos(){
		
		if(document.getElementById("complementoJustificativaAux").value == ""){
			alert(i18n_message("itemControleFinanceiroViagem.complementoJustificativa") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}
		
		if(document.getElementById("classificacaoAux").value == ""){
			alert(i18n_message("citcorpore.comum.classificacao") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}
		
		if(document.getElementById("idTipoMovimFinanceiraViagemAux").value == ""){
			alert(i18n_message("itemControleFinanceiroViagem.tipoMovimentacao") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}
		
		if(document.getElementById("idJustificativaAux").value == ""){
			alert(i18n_message("itemControleFinanceiroViagem.justificativaSolicitacao") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}
		
		if(document.getElementById("idFormaPagamentoAux").value == ""){
			alert(i18n_message("itemControleFinanceiroViagem.formaPagamento") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}
		
		if(document.getElementById("idFornecedorAux").value == ""){
			alert(i18n_message("itemControleFinanceiroViagem.fornecedor") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}
		
		if(document.getElementById("prazoCotacaoAux").value == ""){
			alert(i18n_message("itemControleFinanceiroViagem.prazoCotacao") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}
		
		if(document.getElementById("horaCotacaoAux").value == ""){
			alert(i18n_message("requisicaoViagem.prazoCotacaoHora") + ": " + i18n_message("citcorpore.comum.campo_obrigatorio"));
			return false;
		}

		
		return true;
	}
	
	
	function gerarButtonDelete(row) {
		row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tblItemControleFinaceiro\', this.parentNode.parentNode.rowIndex);">'
	}

	//Remove a linha da tabela na popup
	function removeLinhaTabela(idTabela, rowIndex) {
		
		 if(idTabela == "tblItemControleFinaceiro"){
			 
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {

					var obj = HTMLUtils.getObjectByTableIndex(idTabela, rowIndex);
					
					HTMLUtils.deleteRow(idTabela, rowIndex);
					
					if(obj != null){
						
						var pos = 0;
						var objAux;
						var indexItemDel;
						
						for(i=0; i < objs.length; i++){
							
							objAux = objs[i];
							
							if(objAux.auxiliarIdentificador == obj.auxiliarIdentificador)
								indexItemDel = i;
							
						}
						
						if(indexItemDel)
							objs.splice(indexItemDel, 1);
						
					}
					
				}
		 }
	}
	
	function recuperaNomeFornecedor(){
		setaDados();
		document.form.fireEvent('recuperaNomeFornecedor');
	}
	
	function limparTela(){ 	
		HTMLUtils.clearForm(document.formItem);
		document.form.fireEvent('recuperaInformacoesIntegrante');
    }
	
	function validarAdiantamento(){
		setaDadosAtualizados();
	}
	
	function serializaItensControle(){
    	//var itens = HTMLUtils.getObjectsByTableId('tblIntegrantesViagemAux');
		//document.form.itemSerialiados.value =  ObjectUtils.serializeObjects(itens);
    	document.form.itemSerialiados.value =  ObjectUtils.serializeObjects(objs);
    }
	
	function gravar(){
		
		//Busca a tabela de itens já cadastradas
		var tabela = document.getElementById('tblIntegrantesViagemAux');
		//var count = tabela.rows.length;
		var count = objs.length;
		
		//Valida se existe algum item cadastrado
		if(count > 1){
			serializaItensControle();
			setaDados();
			document.form.fireEvent("save");
		} else {
			alert(i18n_message("citcorpore.comum.nenhumItemCadastrado"));
		}

	}
	
	function gerarImg (row, obj){
        row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"/>';
    };
    
	function setaDadosAtualizados(){
		
		document.getElementById("classificacao").value = document.getElementById("classificacaoAux").value;
		var valorUnitario = document.getElementById("valorUnitarioAux").value;
		var classificacao = document.getElementById("classificacaoAux").value;
		var quantidade = document.getElementById("quantidadeAux").value;
		var adiantamento;
		
		if(valorUnitario == "" || quantidade == ""){
			return;
		}

		var validar = false;
		
		if(classificacao =='Diária'){
			validar = true;
		}else{
			validar = false;
		}
		
		valorUnitario = document.getElementById("valorUnitarioAux").value;
		valorUnitario = valorUnitario.replace(",",".");
		
		document.getElementById("valorUnitario").value = valorUnitario;
		document.getElementById("idTipoMovimFinanceiraViagem").value = document.getElementById("idTipoMovimFinanceiraViagemAux").value;
		document.getElementById("idTipoMovimenta").value = document.getElementById("idTipoMovimFinanceiraViagemAux").value;
		document.getElementById("idFormaPagamento").value = document.getElementById("idFormaPagamentoAux").value;
		document.getElementById("idJustificativa").value = document.getElementById("idJustificativaAux").value;
		document.getElementById("idFornecedor").value = document.getElementById("idFornecedorAux").value;
		document.getElementById("complementoJustificativa").value = document.getElementById("complementoJustificativaAux").value;
		document.getElementById("observacao").value = document.getElementById("observacaoAux").value;
		document.getElementById("quantidade").value = document.getElementById("quantidadeAux").value;
		
		if(quantidade==null){
			quantidade = 1;
		}
		quantidade = parseFloat(quantidade);
		
		if(validar==true){
			adiantamento = (parseInt(quantidade) +1) * parseFloat(valorUnitario);
			document.getElementById("valorAdiantamentoAux").value = "";
			document.getElementById("valorAdiantamentoAux").value = adiantamento;
		}else{
			adiantamento = (parseInt(quantidade) * parseFloat(valorUnitario));
// 			adiantamento = adiantamento.replace(".",",");
			document.getElementById("valorAdiantamentoAux").value = "";
			document.getElementById("valorAdiantamentoAux").value = adiantamento;
		}
		
		document.getElementById("valorAdiantamento").value = document.getElementById("valorAdiantamentoAux").value;
		document.getElementById("prazoCotacao").value = document.getElementById("prazoCotacaoAux").value;
		document.getElementById("horaCotacao").value = document.getElementById("horaCotacaoAux").value;
// 		document.getElementById("situacao").value = document.getElementById("situacaoAux").value;
		document.getElementById("assento").value = document.getElementById("assentoAux").value;
		document.getElementById("tipoPassagem").value = document.getElementById("tipoPassagemAux").value;
		document.getElementById("localizador").value = document.getElementById("localizadorAux").value;
	}
	
	
	//Deleta os itens da tabela passada como parametro 
	function limparTabelaDeItensCadastradosDaPopup(nomeDaTabela){
		
		var tabela = document.getElementById(nomeDaTabela);
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		
	}
	

	//Ação do botao limpar do formulario principal
	function limparFormulario(){
		
		//Busca a tabela de itens já cadastradas
		var tabela = document.getElementById('tblIntegrantesViagemAux');
		var count = tabela.rows.length;
		
		//Valida se existe algum item cadastrado
		if(count > 1){
			if (confirm(i18n_message("citcorpore.comum.limparDadosComItem")))
				limparFormularioPrincipal();
			
		} else {
			limparFormularioPrincipal();			
		}
		
	}
	
	
	//Limpar os campos do formulario principal
	function limparFormularioPrincipal(){
		
		HTMLUtils.clearForm(document.form);
		limparTabelaDeItensCadastradosDaPopup('tblRequisicoesViagem');
		limparTabelaDeItensCadastradosDaPopup('tblIntegrantesViagemAux');
		limparTabelaDeItensCadastradosDaPopup('tblItemControleFinaceiro');
		limparCamposDaSegundaGridDoFormularioPrincipal();
		
		objs = new Array();
		
	}
	
	//Limpar os campos da segunda grid (Numero/Nome) do formulario principal
	function limparCamposDaSegundaGridDoFormularioPrincipal(){
		
		limparTabelaDeItensCadastradosDaPopup('tblIntegrantesViagem');
		$('#div_integrantes').hide();
		
	}
	
	$(document).ready(function(){
		$('#horaCotacaoAux').mask('99:99');
	});
    
    function validaHoras(obj){
    	var valor = obj.value;
    	var hh = valor.substring(0,2);
    	var mm = valor.substring(3,5);
    	var hora = parseInt(hh);
    	var min = parseInt(mm);
    	if(hora < 0 || hora > 23){
    		alert(i18n_message("jornadaTrabalho.horaInvalida"));
    		$('#horaCotacaoAux').val("");
    		return false;
    	}
    	if(min < 0 || min > 59){
    		alert(i18n_message("jornadaTrabalho.minutoInvalido"));
    		$('#horaCotacaoAux').val("");
    		return false;
    	}
  	}
	 
    
	//Valida se a dataFim é maior que a dataInicio
	//Parametro opcao serve para alterar a mensagem que será exibida para o usuario
	function validaData(dataInicio, dataFim, opcao) {
		
		var dtInicio = new Date();
		var dtFim = new Date();

		dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
		dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
	
		if (dtInicio > dtFim){
			
			if(opcao == 1)
				alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
			else
				alert(i18n_message("controleFinanceiroViagemImprevisto.prazoCotacaoMenorQueDataAtual"));
			
			return false;
			
		}else
			return true;
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
					<i18n:message key="controleFinanceiroViagemImprevisto.controleFinanceiroViagemImprevisto" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="controleFinanceiroViagemImprevisto.cadastroControleFinanceiroViagemImprevisto" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/controleFinanceiroViagemImprevisto/controleFinanceiroViagemImprevisto'>
                                <input type='hidden' name='itemSerialiados' id='itemSerialiados' />
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='idControleFinanceiroViagem' id='idControleFinanceiroViagem' /> 
                                <input type='hidden' name='integranteViagemSerialize'/>
                                <input type='hidden' name='idResponsavel' id='idResponsavel'/>
								<input type='hidden' name='idItemControleFinanceiroViagem' id='idItemControleFinanceiroViagem' /> 
								<input type='hidden' name='idEmpregado' id='idEmpregado' />
								<input type='hidden' name='movimenta' id="movimenta" /> 
								<input type='hidden' name='classifica' id='classifica'/>
								<input type='hidden' name='qtDias' id='qtDias'/>
								<input type='hidden' name='idItemSelecionado' id='idItemSelecionado'/>                                
								<input type='hidden' name='classificacao' id='classificacao'/>                                                               
								<input type='hidden' name='idFormaPagamento' id='idFormaPagamento'/>                                
								<input type='hidden' name='idJustificativa' id='idJustificativa'/>                                
								<input type='hidden' name='idFornecedor' id='idFornecedor'/>                                
								<input type='hidden' name='valorUnitario' id='valorUnitario'/> 
								<input type='hidden' name='complementoJustificativa' id='complementoJustificativa'/> 
								<input type='hidden' name='observacao' id='observacao'/>
								<input type='hidden' name='quantidade' id='quantidade'/> 
								<input type='hidden' name='valorAdiantamento' id='valorAdiantamento'/> 
								<input type='hidden' name='prazoCotacao' id='prazoCotacao'/> 
								<input type='hidden' name='horaCotacao' id='horaCotacao'/>
								<input type='hidden' name='situacao' id='situacao'/> 
								<input type='hidden' name='assento' id='assento'/> 
								<input type='hidden' name='tipoPassagem' id='tipoPassagem'/> 
								<input type='hidden' name='localizador' id='localizador'/>
								<input type='hidden' name='idTipoMovimFinanceiraViagem' id='idTipoMovimFinanceiraViagem'/>
								<input type='hidden' name='idTipoMovimenta' id='idTipoMovimenta'/>
								<input type='hidden' name='nomeTipoMovimFinanceira' id='nomeTipoMovimFinanceira'/>
								<input type='hidden' name='nomeFornecedor' id='nomeFornecedor'/>
								<input type='hidden' name='paginaSelecionada' id='paginaSelecionada'/>
								                               
                                <div class="columns clearfix">
						             	<div class="col_100">
						             	
							                <div class="col_20">
						                       <fieldset style="height: 60px">
						                           <label><i18n:message key="citcorpore.comum.periodo" /></label>
						                              <div><table>
						                                 <tr>
						                                     <td>
						                                      	<input type='text' name="dataInicio" id="dataInicio" size="10" maxlength="10" class="Format[Data] datepicker" />
						                                     </td> 
						                                     <td>
						                                      	&nbsp;<i18n:message key="citcorpore.comum.a" />&nbsp;
						                                     </td> 
						                                     <td> 
						                                      	<input type='text' name="dataFim" id="dataFim" size="10" maxlength="10" class="Format[Data] datepicker" />
						                                     </td>
						                                 </tr>
						                              </table></div>
						                       </fieldset>
							                 </div>
							                 
						                     <div class="col_15">
						                          <fieldset style="height: 60px">
						                              <label><i18n:message key="controleFinanceiroViagemImprevisto.numeroSolicitacaoViagem" /></label>
						                                 <div>
						                                     <input type='text' name="idSolicitacao" id="idSolicitacao" size="10" maxlength="10" class="Format[Numero]" />
						                                 </div>
						                          </fieldset>
						                     </div>
						                     
						                     <div class="col_25">
						                          <fieldset style="height: 60px">
						                               <label>&nbsp;</label>
						                               <div>
						                                <button type='button' name='btnPesquisarRequisicao' class="light" onclick='pesquisarRequisicoes();'>
						                                    <img
						                                        src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
						                                    <span><i18n:message key="citcorpore.comum.pesquisar" />
						                                    </span>
						                                </button>
						                               </div>
						                          </fieldset>
						                     </div>
						                     
						               </div>
						               
						               <div class="col_50">
						                   <h2 class="section">
						                       <i18n:message key="controleFinanceiroViagemImprevisto.requisicoesViagem" />
						                   </h2>
						               </div >
						               <div class="col_100" style='height:175px;overflow:auto;'>
						                   <table id="tblRequisicoesViagem" class="table">
						                       <tr>
						                           <th width="4%">&nbsp;</th>
						                           <th width="5%"><i18n:message key="citcorpore.comum.numero" /></th>
						                           <th width="10%"><i18n:message key="citcorpore.comum.datainicio" /></th>
						                           <th width="10%"><i18n:message key="citcorpore.comum.datafim" /></th>
						                           <th width="30%"><i18n:message key="controleFinanceiroViagemImprevisto.motivoViagem" /></th>
						                       </tr>
						                   </table>
						               </div>
						               <div class="col_100">
						               		<div class="col_40"><br></div>
						               		<div id="paginas" class="col_50" align="center" style="height: 50px;" ></div>
						               </div>
						               <div class="col_100">
										<div id="div_integrantes" class="col_100" style='height: 200px; overflow: auto;'>
											<table id="tblIntegrantesViagem" class="table">
												<tr>
													<th width="4%">&nbsp;</th>
													 <th width="5%"><i18n:message key="citcorpore.comum.numero" /></th>
													 <th width="20%"><i18n:message key="citcorpore.comum.nome" /></th>
												</tr>
											</table>
										</div>
									</div>
									<div style="display: none;" class="col_100">
										<div class="col_66" style='height: 200px; overflow: auto;'>
											<table id="tblIntegrantesViagemAux" class="table">
												<tr>
													<th width="4%">&nbsp;</th>
													 <th width="5%"><i18n:message key="citcorpore.comum.numero" /></th>
													 <th width="20%"><i18n:message key="citcorpore.comum.nome" /></th>
												</tr>
											</table>
										</div>
									</div>
						 	      </div>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='limparFormulario();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>						
							</form>
							<!-- Inicio Popup -->
							<div id="POPUP_ITEMCONTROLEFINANCEIRO" style="overflow: hidden;"
									title="<i18n:message key="itemControleFinanceiroViagem.itemControleFinanceiroViagem"/>">
									<form name='formItem' id='formItem' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/controleFinanceiroViagemImprevisto/controleFinanceiroViagemImprevisto'>
									<div>
										<div class="columns clearfix">
											<div class="col_100">
												<fieldset>
													<label><i18n:message
															key="itemControleFinanceiroViagem.nome" /></label>
													<div>
														<input type='text' name="nomeIntegrante" id="nomeIntegrante" maxlength="120" readonly="readonly" class="Description[itemControleFinanceiroViagem.nomeSolicitante]" />
													</div>
												</fieldset>
											</div>
											<div class="col_100">
												<div class="col_100">
													<fieldset>
														<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.complementoJustificativa" /></label>
														<div>
															<textarea id="complementoJustificativaAux" name="complementoJustificativaAux" style="height: 100px; float: left;" ></textarea>
														</div>
													</fieldset>
												</div>

												<div class="col_33">
													<fieldset style="height: 60px">
														<label class="campoObrigatorio"><i18n:message
																key="itemControleFinanceiroViagem.classificacao" /></label>
														<div>
															<select name='classificacaoAux' id='classificacaoAux' onchange="tratarTipoMovimentacaoFinanceira(this);" >
															</select>
	
														</div>
													</fieldset>
												</div>
												
												<div class="col_33">
													<fieldset style="height: 60px">
														<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.tipoMovimentacao" /></label>
														<div>
															<select name='idTipoMovimFinanceiraViagemAux' id='idTipoMovimFinanceiraViagemAux' onchange="tratarValores();" ></select>
														</div>
													</fieldset>
												</div>
												
												<div class="col_33">
													<fieldset style="height: 60px">
														<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.justificativaSolicitacao" /></label>
														<div>
															<select name='idJustificativaAux' id='idJustificativaAux' ></select>
														</div>
													</fieldset>
												</div>
												
											</div>
													
											<div class="col_100">
												<div class="col_33">
													<fieldset style="height: 60px">
														<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.formaPagamento" /></label>
														<div>
															<select name='idFormaPagamentoAux' id='idFormaPagamentoAux' >
															</select>
														</div>
													</fieldset>
												</div>
												<div class="col_33">
													<fieldset style="height: 60px">
														<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.fornecedor" /></label>
														<div>
															<select onchange="recuperaNomeFornecedor();" id="idFornecedorAux" name="idFornecedorAux" ></select>
														</div>
													</fieldset>
												</div>
												
												<div class="col_33">
													<div class="col_50">
														<fieldset style="height: 60px">
															<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.prazoCotacao" /></label>
															<div>
																<input id="prazoCotacaoAux" name="prazoCotacaoAux" size="10" maxlength="10" type="text" class="Format[Data] text datepicker " />
															</div>
														</fieldset>
													</div>
												
													<div class="col_50">
														<fieldset style="height: 60px">
															<label class="campoObrigatorio"><i18n:message key="requisicaoViagem.prazoCotacaoHora" /></label>
															<div>
																<input id="horaCotacaoAux" name="horaCotacaoAux" size="10" maxlength="10" type="text" onblur="validaHoras(this)"/>
															</div>
														</fieldset>
													</div>
												</div>	
														
											</div>
													
											<div class="col_100">	
												<div class="col_33">
													<fieldset style="height: 60px">
														<label><i18n:message key="itemControleFinanceiroViagem.quantidade" /></label>
														<div>
															<input type='text' id="quantidadeAux" name="quantidadeAux" class="Format[Numero]" maxlength="8" onblur="validarAdiantamento();" />
														</div>
													</fieldset>
												</div>
												<div class="col_33">
													<fieldset style="height: 60px">
														<label><i18n:message
																key="itemControleFinanceiroViagem.valorUnitario" /></label>
														<div>
															<input type='text' id="valorUnitarioAux" name="valorUnitarioAux" maxlength="8" onblur="validarAdiantamento();" />
														</div>
													</fieldset>
												</div>
												<div class="col_33">
													<fieldset style="height: 60px">
														<label id="nomeAdiantamento" style="display: none"><i18n:message key="itemControleFinanceiroViagem.adiantamento" /></label>
														<label id="nomeValorTotal"><i18n:message key="coletaPreco.preco" /></label>
														<div>
															<input type='text' id="valorAdiantamentoAux" name="valorAdiantamentoAux" class="Format[Numero]" maxlength="20" readonly="readonly" onblur="validarAdiantamento()"/>
														</div>
													</fieldset>
												</div>
											</div>
											
											<div class="col_100">
												<div class="col_33" id="div_assento">
													<fieldset style="height: 60px">
														<label><i18n:message key="itemControleFinanceiroViagem.assento" /></label>
														<div>
															<input type='text' id="assentoAux" name="assentoAux" maxlength="20" />
														</div>
													</fieldset>
												</div>
			
												<div class="col_33" id="div_tipoPassagem">
													<fieldset style="height: 60px">
														<label><i18n:message key="itemControleFinanceiroViagem.tipoPassagem" /></label>
														<div>
															<select name='tipoPassagemAux' id='tipoPassagemAux' disabled="disabled">
																<option><i18n:message key="itemControleFinanceiroViagem.ida" /></option>
																<option><i18n:message key="itemControleFinanceiroViagem.idaevolta" /></option>
																<option><i18n:message key="itemControleFinanceiroViagem.remarcacao" /></option>
																<option><i18n:message key="itemControleFinanceiroViagem.volta" /></option>
															</select>
														</div>
													</fieldset>
												</div>
												<div class="col_33" id="div_localizador">
													<fieldset style="height: 60px">
														<label><i18n:message key="itemControleFinanceiroViagem.localizador" /></label>
														<div>
															<input type='text' id="localizadorAux" name="localizadorAux" maxlength="50" />
														</div>
													</fieldset>
												</div>
											</div>
											
											<div class="col_100">
												<fieldset>
													<label><i18n:message key="avaliacaoFonecedor.observacao" /></label>
													<div>
														<textarea id="observacaoAux" name="observacaoAux" style="height: 100px; float: left;"></textarea>
													</div>
												</fieldset>
											</div>
													
										         <!-- 	Desenvolvedor: Pedro Lino - Data: 29/10/2013 - Horário: 11:39 - ID Citsmart: 120948 - 
														* Motivo/Comentário: Tabela sem alinhamento  -->

											<br>
											<div class="col_100">
												<fieldset>
												
													<div class="col_50">
														<h4> <i18n:message key="itemControleFinanceiroViagem.itensCadastrados" /></h4>
													</div>
												</fieldset>
												
												<fieldset>
													<div class="col_90">
														<table id="tblItemControleFinaceiro" class="table table-bordered table-striped">
															<tr>
																<th width="10%"></th>
																<th width="30%"><i18n:message key="itemControleFinanceiroViagem.tipoMovimentacaoFinanceira" /></th>
																<th width="50%"><i18n:message key="fornecedor" /></th>
																<th width="10%"><i18n:message key="coletaPreco.preco" /></th>
															</tr>
														</table>
													</div>
													
												</fieldset>
											</div>
										</div>
										<br>
										<fieldset>
											<div>
												<button type='button' name='btnAdicionarItem' class="light" onclick='adicionarItem();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
													<span><i18n:message key="citcorpore.comum.gravar" /></span>
												</button>
												<button type='button' name='btnLimpar' class="light" onclick='limparTela();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
													<span><i18n:message key="citcorpore.comum.limpar" /></span>
												</button>
												<button type='button' name='btnFechar' class="light" onclick='fecharFrameItemControleFinanceiro();'>
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/alert.png">
													<span><i18n:message key="citcorpore.comum.fechar" /></span>
												</button>
											</div>
										</fieldset>
									</div>
								</form>
							</div>
							<!--Fim Popup  -->
					    </div>
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>