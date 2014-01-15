<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
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
<script charset="ISO-8859-1"  type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>

<style type="text/css">
	.table {
		border-left:1px solid #ddd;
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
	}
</style>

<script>
	var contRelacionamento = 0;
	var objTab = null;
	var contFornecedor = 0;
	var contMarca = 0;

	addEvent(window, "load", load, false);
	function load() {

		
		$("#POPUP_CATEGORIAPRODUTO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_TIPOPRODUTORELACIONADO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_UNIDADEMEDIDA").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_FORNECEDOR").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_MARCA").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}

	};
	
	function gravar(){
		serializa();
		serializa2();
		document.form.save();
		contRelacionamento = 0;
	}
	
	
 	function serializa(){
 		var tabela = document.getElementById('tabelaTipoProdutoRelacionado');
 		var count = contRelacionamento + 1;
 		var listaDeRelacionamentos = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idTipoProdutoRelacionado' + i) != "" && document.getElementById('idTipoProdutoRelacionado' + i) != null){
 			var trObj = document.getElementById('idTipoProdutoRelacionado' + i).value;
 			var relacionamentoProduto = new RelacionamentoProdutoDTO(trObj, i);
 				listaDeRelacionamentos.push(relacionamentoProduto);
 			}
 		} 	
 		var ser = ObjectUtils.serializeObjects(listaDeRelacionamentos);
		document.form.relacionamentosSerializados.value = ser;
 	}
 	
 	function serializa2(){
 		var tabela = document.getElementById('tabelaFornecedor');
 		var count = contFornecedor + 1;
 		var listaDeFornecedores = [];
 		for(var i = 1; i < count ; i++){
 			if (document.getElementById('idFornecedor' + i) != "" && document.getElementById('idFornecedor' + i) != null){
 			var trObj = document.getElementById('idFornecedor' + i).value;
 			var fornecedorProduto = new fornecedorProdutoDTO(trObj, i);
 				listaDeFornecedores.push(fornecedorProduto);
 			}
 		} 	
 		var ser = ObjectUtils.serializeObjects(listaDeFornecedores);
		document.form.fornecedoresSerializados.value = ser;
 	}
	
 	
	function addLinhaTabelaTipoProdutoRelacionado(id, desc, valida){
		var tbl = document.getElementById('tabelaTipoProdutoRelacionado');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaTipoProdutoRelacionado(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contRelacionamento++;
		coluna.innerHTML = '<img id="imgDelTipoProdutoRelacionado' + contRelacionamento + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaTipoProdutoRelacionado\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc + '<input type="hidden" id="idTipoProdutoRelacionado' + contRelacionamento + '" name="idTipoProdutoRelacionado" value="' + id + '" />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input  type="radio" id="tipoRelacionamentoA' + contRelacionamento + '"  name="tipoRelacionamento' + contRelacionamento + '" value= "A" />'
		coluna = row.insertCell(3);
		coluna.innerHTML = '<input  type="radio" id="tipoRelacionamentoS' + contRelacionamento + '"  name="tipoRelacionamento' + contRelacionamento + '" value= "S" />';
			$("#POPUP_TIPOPRODUTORELACIONADO").dialog("close");
		
	}
	
	function addLinhaTabelaFornecedor(id, desc, valida){
		var tbl = document.getElementById('tabelaFornecedor');
		tbl.style.display = 'block';
		var lastRow = tbl.rows.length;
		if (valida){
			if (!validaAddLinhaTabelaFornecedor(lastRow, id)){
				return;
			}
		}
		var row = tbl.insertRow(lastRow);
		var coluna = row.insertCell(0);
		contFornecedor++;
		coluna.innerHTML = '<img id="imgDelFornecedor' + contFornecedor + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaFornecedor\', this.parentNode.parentNode.rowIndex);">';
		coluna = row.insertCell(1);
		coluna.innerHTML = desc + '<input type="hidden" id="idFornecedor' + contFornecedor + '" name="idFornecedor" value="' + id + '" />' + '<input type="hidden" name="idFornecedorProduto' + contFornecedor + '" id="idFornecedorProduto' + contFornecedor + '" readonly="readonly"  />';
		coluna = row.insertCell(2);
		coluna.innerHTML = '<input type="text" name="marca' + contFornecedor + '" id="marca' + contFornecedor + '" onclick="abrePopupMarca('+ contFornecedor +');" maxlength="50" size="50" />' + '<input type="hidden" name="idMarca' + contFornecedor + '" id="idMarca' + contFornecedor + '" readonly="readonly"  />'
		$("#POPUP_FORNECEDOR").dialog("close");
		
	}
	
 	function RelacionamentoProdutoDTO(_id, i){
 		if ($('#tipoRelacionamentoS' + i).is(":checked")){
 			this.tipoRelacionamento = 'S';
 		}
 		else {
 			this.tipoRelacionamento = 'A';
 		}
 		this.idTipoProdutoRelacionado = _id; 
	}
 	
 	function fornecedorProdutoDTO(_id, i){
 		this.idMarca = document.getElementById('idMarca' + i).value;
 		this.idFornecedor = _id; 
	}
	
	
	function validaAddLinhaTabelaTipoProdutoRelacionado(lastRow, id){
		if (lastRow > 2){
			var arrayIdTipoProdutoRelacionado = document.form.idTipoProdutoRelacionado;
			for (var i = 0; i < arrayIdTipoProdutoRelacionado.length; i++){
				if (arrayIdTipoProdutoRelacionado[i].value == id || arrayIdTipoProdutoRelacionado[i].value == document.form.idTipoProduto){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}
				
			}
		} else if (lastRow == 2){
			var idTipoProdutoRelacionado = document.form.idTipoProdutoRelacionado.value;
			if (idTipoProdutoRelacionado == id || idTipoProdutoRelacionado == document.form.idTipoProduto.value){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		 else if (lastRow == 1){
				if (document.form.idTipoProduto.value == id ){
					alert(i18n_message("tipoProduto.naoRelacionar"));
					return false;
					}
			 }	
		return true;
	}
	
	function validaAddLinhaTabelaFornecedor(lastRow, id){
		if (lastRow > 2){
			var arrayIdFornecedor = document.form.idFornecedor;
			for (var i = 0; i < arrayIdFornecedor.length; i++){
				if (arrayIdFornecedor[i].value == id){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}
				
			}
		} else if (lastRow == 2){
			var idFornecedor = document.form.idFornecedor.value;
			if (idFornecedor == id){
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
		return true;
	}
	
	function LOOKUP_TIPOPRODUTO_select(id, desc) {
		document.form.restore({
			idTipoProduto : id
		});
	}
	
	function atribuirChecked(valor, seq){
			if (valor == "S"){
				$('#tipoRelacionamentoS' + seq).attr('checked', true)
			}else{
				$('#tipoRelacionamentoA' + seq).attr('checked', true)
			}
		}
	
	function removeLinhaTabela(idTabela, rowIndex) {
		 if(idTabela == "tabelaTipoProdutoRelacionado"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.relacionamentoProduto.value = eval('document.form.idTipoProdutoRelacionado' + rowIndex + '.value');
					document.form.fireEvent("deleteTipoProdutoRelacionado");
					HTMLUtils.deleteRow(idTabela, rowIndex);
					document.form.relacionamentosSerializados.value = eval('document.form.idTipoProdutoRelacionado' + rowIndex + '.value');
				} 
		 }
		  else{ 
			 if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fornecedor.value = eval('document.getElementById("idFornecedor' + rowIndex + '").value');
				document.form.fornecedorProduto.value = eval('document.getElementById("idFornecedorProduto' + rowIndex + '").value');
				document.form.fireEvent("deleteFornecedor"); 
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.fornecedoresSerializados.value = eval('document.getElementById("idFornecedor' + rowIndex + '").value');
				}
		 } 
	}

	
	
	function deleteAllRows() {
		var tabela = document.getElementById('tabelaTipoProdutoRelacionado');
		var count = tabela.rows.length;
	 	var tabela1 = document.getElementById('tabelaFornecedor');
		var count1 = tabela1.rows.length
 
		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
		 	while (count1 > 1) {
				tabela1.deleteRow(count1 - 1);
				count1--;
		} 
		contRelacionamento = 0;
		contFornecedor = 0;
	}
	
	function LOOKUP_CATEGORIAPRODUTO_select(id,desc){
		document.getElementById("idCategoria").value = id;
		document.getElementById("categoriaProduto").value = desc;
		$("#POPUP_CATEGORIAPRODUTO").dialog("close");
	};
	
	function LOOKUP_UNIDADEMEDIDA_select(id,desc){
		document.getElementById("idUnidadeMedida").value = id;
		document.getElementById("unidadeMedida").value = desc;
		$("#POPUP_UNIDADEMEDIDA").dialog("close");
	};
	
	function LOOKUP_MARCA_select(id,desc){
		document.getElementById("idMarca"+contMarca).value = id;
		document.getElementById("marca"+contMarca).value = desc;
		$("#POPUP_MARCA").dialog("close");
	};
	
	function setarMarca(id, desc, cont){
		document.getElementById("idMarca"+cont).value = id;
		document.getElementById("marca"+cont).value = desc;
	}
	
	function setarIdFornecedorProduto(id, cont){
		document.getElementById("idFornecedorProduto"+cont).value = id;
	}
	
	function abrePopupCategoriaProduto(){
		$("#POPUP_CATEGORIAPRODUTO").dialog("open");
	};
	
	function abrePopupMarca(cont){
		$("#POPUP_MARCA").dialog("open");
		contMarca = cont;
	};
	
	function abrePopupUnidadeMedida(){
		$("#POPUP_UNIDADEMEDIDA").dialog("open");
	};
	
	function excluir() {
		if (document.getElementById("idTipoProduto").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}
	
	function LOOKUP_TIPOPRODUTORELACIONADO_select(id, desc){
		addLinhaTabelaTipoProdutoRelacionado(id, desc, true);
		atribuirChecked('A', contRelacionamento);
	}
	
	function LOOKUP_FORNECEDOR_select(id, desc){
		addLinhaTabelaFornecedor(id, desc, true);
	}
	
	$(function() {
		$("#addTipoProdutoRelacionado").click(function() {
			$("#POPUP_TIPOPRODUTORELACIONADO").dialog("open");
		});
	});
	
	$(function() {
		$("#addFornecedor").click(function() {
			$("#POPUP_FORNECEDOR").dialog("open");
		});
	});

</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
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
					<i18n:message key="tipoProduto" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="tipoProduto.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="tipoProduto.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/TipoProduto/TipoProduto'>
								<input type="hidden" id="idTipoProduto" name="idTipoProduto" />
									<input type="hidden" id="relacionamentosSerializados" name="relacionamentosSerializados" />
									<input type="hidden" id="relacionamentoProduto" name="relacionamentoProduto" />
									<input type="hidden" id="fornecedor" name="fornecedor" />
									<input type="hidden" id="fornecedorProduto" name="fornecedorProduto" />
								<input type="hidden" id="idCategoria" name="idCategoria" />
								<input type="hidden" id="idUnidadeMedida" name="idUnidadeMedida" />
								<input type="hidden" id="fornecedoresSerializados" name="fornecedoresSerializados" />
								
								<div class="columns clearfix">	
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="tipoProduto.nome" /></label>
										<div>
											<input type='text' name="nomeProduto" maxlength="70" class="Valid[Required] Description[tipoProduto.nome]" />
										</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="tipoProduto.Categoria" /></label>
										<div>
										<input type='text' name="nomeCategoria" id="categoriaProduto" onclick="abrePopupCategoriaProduto();" maxlength="50" size="50" readonly="readonly" class="Valid[Required] Description[tipoProduto.Categoria]" />
										</div>
									</fieldset>
									</div>
									<div class="col_33">	
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="tipoProduto.unidadeMedida" /></label>
										<div>
										<input type='text' name="nomeUnidadeMedida" id="unidadeMedida" onclick="abrePopupUnidadeMedida();" maxlength="50" size="50" readonly="readonly" class="Valid[Required] Description[tipoProduto.unidadeMedida]"  />
										</div>
										</fieldset>
									</div>
									</div>
									<div class="columns clearfix">
									<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="tipoProduto.aceitaRequisicao" /></label>
										<div  class="inline clearfix">
										<label><input type="radio" id="aceitaRequisicao" name="aceitaRequisicao" value="S" checked="checked" /><i18n:message key="citcorpore.comum.sim" /></label>
										<label><input type="radio" id="aceitaRequisicao" name="aceitaRequisicao" value="N" /><i18n:message key="citcorpore.comum.nao" /></label>
										</div>
										</fieldset>
									</div>
									<div class="col_33">	
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="tipoProduto.situacao" /></label>	
										<div  class="inline clearfix">
										<label>
												<input type="radio" id="situacao" name="situacao" value="A" checked="checked" /><i18n:message key="citcorpore.comum.ativo" />
										</label>
										<label>
											<input type="radio" id="situacao" name="situacao" value="I" /><i18n:message key="citcorpore.comum.inativo" />
										</label>
										</div>
										</fieldset>
									</div>
								</div>
									<div  class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label id="addFornecedor" style="cursor: pointer;"
												title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />"><i18n:message key="cotacao.fornecedores" /><img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"></label>
											<div  id="gridFornecedores">
												<table class="table" id="tabelaFornecedor"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 50%;"><i18n:message key="cotacao.fornecedores" /></th>
														<th style="width: 49%;"><i18n:message  key="marca" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div  class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label id="addTipoProdutoRelacionado" style="cursor: pointer;"
												title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />"><i18n:message key="tipoProduto.relacionamento" /><img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"></label>
											<div  id="gridTiposProdutosRelacionados">
												<table class="table" id="tabelaTipoProdutoRelacionado"
													style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 97%;"><i18n:message key="tipoProduto" /></th>
														<th style="width: 1%;"><i18n:message  key="tipoProduto.acessorio" /></th>
														<th style="width: 1%;"><i18n:message  key="tipoProduto.prodSemelhante" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>	
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear(); deleteAllRows();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
									onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>

					</div>
					<div id="tabs-2" class="block">
												<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'lockupName='LOOKUP_TIPOPRODUTO' id='LOOKUP_TIPOPRODUTO' top='0'left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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

<div id="POPUP_CATEGORIAPRODUTO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formCategoriaProduto' style="width: 540px">
							<cit:findField formName='formCategoriaProduto' 
								lockupName='LOOKUP_CATEGORIAPRODUTO' id='LOOKUP_CATEGORIAPRODUTO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<div id="POPUP_UNIDADEMEDIDA" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formUnidadeMedida' style="width: 540px">
							<cit:findField formName='formUnidadeMedida' 
								lockupName='LOOKUP_UNIDADEMEDIDA' id='LOOKUP_UNIDADEMEDIDA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<div id="POPUP_TIPOPRODUTORELACIONADO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formTipoProduto' style="width: 540px">
							<cit:findField formName='formTipoProduto' 
								lockupName='LOOKUP_TIPOPRODUTO' id='LOOKUP_TIPOPRODUTORELACIONADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<div id="POPUP_FORNECEDOR" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaFabricante' style="width: 540px">
							<cit:findField formName='formPesquisaFabricante' 
								lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
<div id="POPUP_MARCA" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formMarca' style="width: 540px">
							<cit:findField formName='formMarca' 
								lockupName='LOOKUP_MARCA' id='LOOKUP_MARCA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
</html>
