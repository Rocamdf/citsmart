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
<html lang="en-us" class="no-js">
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>	
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
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ItemControleFinanceiroViagemDTO.js"></script>

<script>
	
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}

	function gerarImgAlterar(row, obj){
        row.cells[0].innerHTML = '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/btnAlterarRegistro.gif" style="cursor: pointer;"/>';
    };
    
    function editarItem(row, obj) {
    	var dtCotacao = obj.dataCotacao;
    	var hrCotacao = obj.horaCotacao; 
    	HTMLUtils.setValues(document.form, null, obj);
    	document.form.dataCotacao.value = dtCotacao;
    	if(hrCotacao != null){
    		document.form.horaCotacao.value = hrCotacao;
    	}
    	document.form.fireEvent("habilitaTipo");
    }; 
    
	function gerarImgDeletar(row, obj){
        row.cells[4].innerHTML = '<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" style="cursor: pointer;" onclick="deleteLinha(\'tblItemControleFinaceiro\', this.parentNode.parentNode.rowIndex);"/>';
	};
	
	function deleteLinha(table, index){
		var obj = HTMLUtils.getObjectsByTableId(table);
		if (confirm(i18n_message("citcorpore.comum.confirmaExclusao"))) {
			teste = 'true';
			HTMLUtils.deleteRow(table, index);
			<%-- Recupera apenas o idItemControleFinanceiroViagem do objeto e passa o valor dele para o hidden, em seguida executa 
			método setarDataFimItem que tratará a parte do banco --%>
 			document.getElementById("idItemSelecionado").value =  idItemSelecionado = obj[0].idItemControleFinanceiroViagem;
 			document.form.fireEvent('setarDataFimItem');
			return;
		}
		teste = '';
	}
	
    function limparTela(){
    	
    	if(document.getElementById("idItemControleFinanceiroViagem")!=null){
    		document.getElementById("idItemControleFinanceiroViagem").value = "0";
    	}
    	if(document.getElementById("valorAdiantamento")!=null){
    		document.getElementById("valorAdiantamento").value = "0";
    	}
    	
    	if(document.getElementById("quantidade")!=null){
    		document.getElementById("quantidade").value = "";
    	}
    	
    	if(document.getElementById("valorUnitario")!=null){
    		document.getElementById("valorUnitario").value = "";
    	}
    	
    	if(document.getElementById("assento")!=null){
    		document.getElementById("assento").value = "";
    	}
    	
    	if(document.getElementById("localizador")!=null){
    		document.getElementById("localizador").value = "";
    	}
    	if(document.getElementById("dataCotacao")!=null){
    		document.getElementById("dataCotacao").value = "";
    	}
    	if(document.getElementById("horaCotacao")!=null){
    		document.getElementById("horaCotacao").value = "";
    	}
    	
    	if(document.getElementById("complementoJustificativa")!=null){
    		document.getElementById("complementoJustificativa").value = "";
    	}
    	
    	if(document.getElementById("situacao")!=null){
    		document.getElementById("situacao").value = "";
    	}
    	
    	if(document.getElementById("classificacao")!=null){
    		document.form.fireEvent('comboClassificacao');
    	}
    	
    	if(document.getElementById("idTipoMovimFinanceiraViagem")!=null){
    		document.form.fireEvent('comboTipoMovimentacaoFinanceira');
    	}
    	if(document.getElementById("idFormaPagamento")!=null){
    		document.form.fireEvent('comboFormaPagamento');
    	}
    	if(document.getElementById("idJustificativa")!=null){
    		document.form.fireEvent('comboJustificativaSolicitacao');
    	}
    	if(document.getElementById("idFornecedor")!=null){
    		document.form.fireEvent('comboFornecedor');
    	}
    }
    
    function fecharPopup(){
		parent.fecharFrameItemControleFinanceiro();
	}
    
    function validarDatas(){
		var inputs = document.getElementsByClassName("datepicker");
		var input = null;
		var errorMsg = i18n_message("citcorpore.comum.nenhumaDataDeveSerInferiorHoje") ;
		
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
    
    function gravar(){
    	validarDatas();
    	document.form.save();
    }
    
    function validaData(obj) {

		var dataInicioViagem = document.form.dataInicioViagem.value;
		var dataInicioCotacao = obj.value;
		var dtInicioViagem = new Date();
		var dtInicioCotacao = new Date();
		
		dtInicioViagem.setTime(Date.parse(dataInicioViagem.split("/").reverse().join("/"))).setFullYear;
		dtInicioCotacao.setTime(Date.parse(dataInicioCotacao.split("/").reverse().join("/"))).setFullYear;
		
		if (dtInicioViagem < dtInicioCotacao){
			alert("Prazo de Cotação Nao deve ser superior ao inicio da viagem");
			$("#dataInicioViagem").val("");
			$("#dataFimViagem").val("");
			return false;
		}else
			return true;
	}

    $(document).ready(function(){
		$('#horaCotacao').mask('99:99');
	});
    
    function validaHoras(obj){
    	var valor = obj.value;
    	var hh = valor.substring(0,2);
    	var mm = valor.substring(3,5);
    	var hora = parseInt(hh);
    	var min = parseInt(mm);
    	if(hora < 0 || hora > 23){
    		alert(i18n_message("jornadaTrabalho.horaInvalida"));
    		$('#horaCotacao').val("");
    		return false;
    	}
    	if(min < 0 || min > 59){
    		alert(i18n_message("jornadaTrabalho.minutoInvalido"));
    		$('#horaCotacao').val("");
    		return false;
    	}
  	}
    
    $('#valorUnitario').on('click', function(){
    	$('#valorUnitario').attr('value','');
    });
    
</script>

<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style title="" type="text/css">

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
		text-align: center;
	}
	
	.table td {
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
		height: 10px;
	}
	
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
		background-color: #fff;
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
	
	.nAdiantamento {
		visibility: hidden;
	}
	
	.adiantamento{
		visibility: visible;
	}
	
	.table tr > td:nth-child(4) {
		text-align: center;
	}
	
    </style>
<%}%>
</head>
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
				<h2>
					<i18n:message key="itemControleFinanceiroViagem.itemControleFinanceiroViagem" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				
<%-- 				<a href="#tabs-1"><i18n:message key="itemControleFinanceiroViagem.cadastro" /></a> --%>
				<h4><i18n:message key="itemControleFinanceiroViagem.cadastro" /></h4>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container ui-layout-content">
					<div class="block ui-layout-content">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemControleFinanceiroViagem/itemControleFinanceiroViagem'>
								<input type='hidden' name='idControleFinanceiroViagem' id='idControleFinanceiroViagem' />
								<input type='hidden' name='idItemControleFinanceiroViagem' id='idItemControleFinanceiroViagem' /> 
								<input type='hidden' name='idEmpregado' id='idEmpregado' />
								<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
								<input type='hidden' name='movimenta' id="movimenta" /> 
								<input type='hidden' name='classifica' id='classifica'/>
								<input type='hidden' name='qtDias' id='qtDias'/>
								<input type='hidden' name='idItemSelecionado' id='idItemSelecionado'/>
								<input type='hidden' name='dataInicioViagem' id='dataInicioViagem'/>
								<input type='hidden' name='origemCompras' id='origemCompras'/>

								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label><i18n:message key="itemControleFinanceiroViagem.nome" /></label>
											<div class="col_100">
												<input type='text' name="nomeIntegrante" id="nomeIntegrante" maxlength="120" readonly="readonly"
													class= "Description[itemControleFinanceiroViagem.nomeSolicitante] " />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
									<div class="col_100">
									<div class="col_100">
										<div class="col_50">
											<fieldset style="height: 60px">
												<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.classificacao" /></label>
												<div>
													<select name='classificacao' id = 'classificacao' onchange="document.form.fireEvent('tratarTipoMovimentacaoFinanceira')" class="Valid[Required] Description[itemControleFinanceiroViagem.classificacao]">
													</select>
									
												</div>
											</fieldset>
										</div>
										
										<div class="col_50">
											<fieldset style="height: 60px">
												<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.tipoMovimentacao" /></label>
												<div>
													<select name='idTipoMovimFinanceiraViagem' id = 'idTipoMovimFinanceiraViagem' onchange="document.form.fireEvent('tratarValores');"class="Valid[Required] Description[itemControleFinanceiroViagem.idTipoMovimFinanceiraViagem]">
													</select>
												</div>
											</fieldset>
										</div>										
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset style="height: 60px">
												<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.justificativaSolicitacao" /></label>
												<div>
													<select name='idJustificativa' id = 'idJustificativa'  class="Valid[Required] Description[itemControleFinanceiroViagem.justificativaSolicitacao]">
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset style="height: 60px">
												<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.fornecedor" /></label>
												<div>
													<select id="idFornecedor" name="idFornecedor" class="Valid[Required] Description[itemControleFinanceiroViagem.fornecedor]" ></select>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
										<div class="col_50">
											<fieldset style="height: 60px">
												<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.formaPagamento" /></label>
												<div>
													<select name='idFormaPagamento' id = 'idFormaPagamento' class="Valid[Required] Description[itemControleFinanceiroViagem.formaPagamento]" >
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<div class="col_50">
												<fieldset style="height: 60px">
													<label class="campoObrigatorio"><i18n:message key="requisicaoViagem.prazoCotacaoData" /></label>
													<div>
														<input id="dataCotacao" name="dataCotacao" size="10" maxlength="10" type="text" 
														class="Format[Date] Valid[Required] text datepicker  Description[requisicaoViagem.prazoCotacaoData]" />
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset style="height: 60px">
													<label class="campoObrigatorio"><i18n:message key="requisicaoViagem.prazoCotacaoHora" /></label>
													<div>
														<input id="horaCotacao" name="horaCotacao" size="10" maxlength="10" type="text" onblur="validaHoras(this)"/>
													</div>
												</fieldset>
											</div>
										</div>		
									</div>	
									<div class="col_100">
										<div class="col_33">
											<fieldset style="height: 60px">
												<label><i18n:message key="itemControleFinanceiroViagem.valorUnitario" /></label>
												<div>
													<input type='text' id="valorUnitario" name="valorUnitario" class="Format[Moeda]" maxlength="8" onblur="document.form.fireEvent('validarAdiantamento');"/>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset style="height: 60px">
												<label><i18n:message key="itemControleFinanceiroViagem.quantidade" /></label>
												<div >
													<input type='text' id="quantidade" name="quantidade" class="Format[Moeda]" maxlength="8" onblur="document.form.fireEvent('validarAdiantamento');"/>
												</div>
											</fieldset>
										</div>									
										<div class="col_33">
											<fieldset style="height: 60px">
												<label id="nomeAdiantamento" style="display:none" ><i18n:message key="itemControleFinanceiroViagem.adiantamento" /></label>
												<label id="nomeValorTotal"><i18n:message key="coletaPreco.preco" /></label>
												<div>
													<input type='text' id="valorAdiantamento" name="valorAdiantamento" class="Format[Moeda]"  maxlength="20" readonly="readonly" />
												</div>
											</fieldset>
										</div>
									</div>
									<div>
										<div class="col_33" id="div_assento">
											<fieldset style="height: 60px">
												<label><i18n:message key="itemControleFinanceiroViagem.assento" /></label>
												<div>
													<input type='text' id="assento" name="assento" maxlength="20"  />
												</div>
											</fieldset>
										</div>	
										<div class="col_33" id="div_tipoPassagem">
											<fieldset style="height: 60px">
												<label><i18n:message key="itemControleFinanceiroViagem.tipoPassagem" /></label>
												<div>
													<select name='tipoPassagem' id = 'tipoPassagem' >
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
													<input type='text' id="localizador" name="localizador" maxlength="50"  />
												</div>
											</fieldset>
										</div>	
									</div>
									</div>
									<div class="col_100">
										<div class="col_100">
											<fieldset  style="height: 90px">
												<label class="campoObrigatorio"><i18n:message key="itemControleFinanceiroViagem.complementoJustificativa" /></label>
												<div >
													<textarea id="complementoJustificativa" name="complementoJustificativa" maxlength="1000" style="height: 90px; float: left;"
													class="Valid[Required] Description[itemControleFinanceiroViagem.complementoJustificativa]"></textarea>
												</div>
											</fieldset>
										</div>
										<div class="col_100">
											<fieldset style="height: 90px">
												<label><i18n:message key="avaliacaoFonecedor.observacao" /></label>
												<div >
													<textarea id="observacao" name="observacao" maxlength="1000" style="height: 90px; float: left;"></textarea>
												</div>
											</fieldset>
										</div>
									</div>
									</div>

									<div class="col_100" id="divTabela">
									<br>
										<div class="col_100">
                                           <h5><i18n:message key="itemControleFinanceiroViagem.itensCadastrados" /></h5>
                                    	</div>
                                    	<div class="col_100">
                                    	<table id="tblItemControleFinaceiro" class="table">
                                        	<tr>
                                           		<th width="5%">&nbsp;</th>
                                            	<th width="35%"><i18n:message key="itemControleFinanceiroViagem.tipoMovimentacao" /></th>
                                            	<th width="40%"><i18n:message key="fornecedor" /></th>
                                            	<th width="15%" style="text-align: center;"><i18n:message key="coletaPreco.preco" /></th>
                                            	<th width="5%">&nbsp;</th>
                                        	</tr>
                                    	</table>
                                    </div>
									</div>					
								</div>
								<div id="divBtnPrincipais" style="padding-top: 15px;">						
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
<%-- 										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png"> --%>
										<span><i18n:message key="citcorpore.comum.gravar" /> </span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limparTela()'>
<%-- 										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png"> --%>
										<span><i18n:message key="citcorpore.comum.limpar" /> </span>
									</button>
									<button type='button' name='btnFecha' id="btnFecha" class="light" onclick='fecharPopup();'>
										<span><i18n:message key="Concluir" /> </span>
									</button>
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
</html>
