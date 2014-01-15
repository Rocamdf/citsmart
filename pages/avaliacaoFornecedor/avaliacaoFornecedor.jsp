<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.citframework.util.UtilDatas"%>

<!doctype html public "âœ°">

<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");
	
	UsuarioDTO usuario = WebUtil.getUsuario(request);
%>
<%@include file="/include/security/security.jsp"%>
<html lang="en-us" class="no-js">
<head>
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
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
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	    document.form.afterLoad = function() {
	        document.form.dataAvaliacao.value = "<%=UtilDatas.dateToSTR(UtilDatas.getDataAtual())%>";
	        document.form.idResponsavel.value = '<%=usuario.getIdEmpregado()%>';
	        document.form.nomeResponsavel.value = '<%=usuario.getNomeUsuario()%>';
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
</style>
<%
	}
%>

<script type="text/javascript">
	$(function() {
		
		<%-- popup = new PopupManager(1100, 800, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/"); --%>
		
		$("#POPUP_FORNECEDOR").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_CRITERIOAVALIACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_CRITERIO").dialog({
			autoOpen : false,
			width : 600,
			height : 350,
			modal : true
		});
		
		$("#POPUP_EMPREGADOS").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});
		
		$("#POPUP_APROVACAO").dialog({
			autoOpen : false,
			width : 600,
			height : 300,
			modal : true
		});
		
		$("#POPUP_RESPONSAVEL").dialog({
			title: 'Pesquisar Empregados',
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true,
			show: "fade",
			hide: "fade"
		});
		
		
		$("#btnAddAprovacao").click(function() {
			document.formAprovacao.clear();
			$("#POPUP_APROVACAO").dialog("open");
		});
		
		$("#razaoSocial").click(function() {
			$("#POPUP_FORNECEDOR").dialog("open");
		});
	});
	
	function LOOKUP_AVALIACAOFORNECEDOR_select(id, desc) {
		document.form.restore({
			idAvaliacaoFornecedor : id
		});
	}
	
	
	
	function LOOKUP_FORNECEDOR_select(id, desc){
		document.form.idFornecedor.value = id;
		document.form.razaoSocial.value  = desc;
		document.form.fireEvent("preencheFornecedor");
	}
	
	function LOOKUP_EMPREGADO_select(id, desc){
		document.formAprovacao.idEmpregado.value = id;
		document.formAprovacao.fireEvent("preencheEmpregado");
	}
	
	function LOOKUP_CRITERIOAVALIACAO_select(id, desc){
		document.formCriterio.idCriterio.value = id;
		document.formCriterio.descricao.value  = desc;
		$("#POPUP_CRITERIOAVALIACAO").dialog("close");
	}
	
	function adicionarCriterio(){
		document.formCriterio.clear();
		$("#POPUP_CRITERIO").dialog("open");
	}
	
	function adicionarNome(){
		$("#POPUP_EMPREGADOS").dialog("open");
	}
	
	function adicionarDescricao(){
		$("#POPUP_CRITERIOAVALIACAO").dialog("open");
	}
	
	function fechaCriterio(){
		$("#POPUP_CRITERIO").dialog("close");
	}
	
	
	exibeIconesCriterio = function(row, obj) {
		obj.sequencia = row.rowIndex; 
	
	    	row.cells[0].innerHTML = '<img src="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/imagens/edit.png" border="0" onclick="exibeCriterio(' 
	    			+ row.rowIndex + ')" style="cursor:pointer" />';
	    	row.cells[1].innerHTML = '<img src="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/imagens/excluirPeq.gif" border="0" onclick="excluiCriterio(' 
	    			+ row.rowIndex + ', this)" style="cursor:pointer" />';
	
	};
	
	exibeCriterio = function(indice) {
		document.formCriterio.clear();
		var obj = HTMLUtils.getObjectByTableIndex('tblCriterio', indice);
		var idCriterio = obj.idCriterio;
		HTMLUtils.setForm(document.formCriterio);
		HTMLUtils.setValues(document.formCriterio, null, obj);
		HTMLUtils.setForm(document.formCriterio);
		$("#POPUP_CRITERIO").dialog("open");
	};
	
	excluiCriterio = function(indice) {				
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao") ) ) {				
			HTMLUtils.deleteRow('tblCriterio', indice);
		}
	};
	
	function fechaAprovacao(){
		$("#POPUP_APROVACAO").dialog("close");
	}
	
	function fechaEmpregado(){
		$("#POPUP_EMPREGADOS").dialog("close");
	}
	
	exibeIconesAprovacao = function(row, obj) {
		obj.sequencia = row.rowIndex; 
	
	    	row.cells[0].innerHTML = '<img src="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/imagens/edit.png" border="0" onclick="exibeAprovacao(' 
	    			+ row.rowIndex + ')" style="cursor:pointer" />';
	    	row.cells[1].innerHTML = '<img src="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/imagens/excluirPeq.gif" border="0" onclick="excluiAprovacao(' 
	    			+ row.rowIndex + ', this)" style="cursor:pointer" />';
	
	};
	
	exibeAprovacao = function(indice) {
		document.formAprovacao.clear();
		var obj = HTMLUtils.getObjectByTableIndex('tblAprovacao', indice);
		var idEmpregado = obj.idEmpregado;
		HTMLUtils.setForm(document.formAprovacao);
		HTMLUtils.setValues(document.formAprovacao, null, obj);
		HTMLUtils.setForm(document.formAprovacao);
		document.formAprovacao.idEmpregado.value = idEmpregado;
		$("#POPUP_APROVACAO").dialog("open");
	};
	
	excluiAprovacao = function(indice) {				
		if (indice > 0 && confirm(i18n_message("citcorpore.ui.confirmacao.mensagem.Confirma_exclusao") ) ) {				
			HTMLUtils.deleteRow('tblAprovacao', indice);
		}
	};
	
	function gravar(){
		
		var dataAvaliacao = document.getElementById("dataAvaliacao").value;
		if(validaData){
			var objsCriterios = HTMLUtils.getObjectsByTableId('tblCriterio');
			document.form.listCriteriosQualidadeSerializado.value = ObjectUtils.serializeObjects(objsCriterios);
			
			var objsAprovacao = HTMLUtils.getObjectsByTableId('tblAprovacao');
			document.form.listAprovacaoReferenciaSerializado.value = ObjectUtils.serializeObjects(objsAprovacao);
			
			document.form.save(); 
		}
		
		
		
	}
	
	function adicionarResponsavel() {
		$("#POPUP_RESPONSAVEL").dialog("open");
	}
	
	function LOOKUP_RESPONSAVEL_select(id, desc) {
		document.getElementById("idResponsavel").value = id;
		document.getElementById("nomeResponsavel").value = desc;
		$("#POPUP_RESPONSAVEL").dialog("close");
	}
	
	function excluir() {
		if (document.getElementById("idAvaliacaoFornecedor").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}
	
	function limpar(){
		document.form.fireEvent("limpar");
		deleteAllRowsEscopo();
		document.form.clear();
        document.form.dataAvaliacao.value = "<%=UtilDatas.dateToSTR(UtilDatas.getDataAtual())%>";
        document.form.idResponsavel.value = '<%=usuario.getIdEmpregado()%>';
        document.form.nomeResponsavel.value = '<%=usuario.getNomeUsuario()%>';
	}
	
	function deleteAllRowsEscopo() {
		var tabela = document.getElementById('tblEscopo');
		try {
			var count = tabela.rows.length;
	
			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
		}catch(e){}
		
	}
	
	
	/**
	* @author rodrigo.oliveira
	*/
	function validaData(dataAvaliacao) {
		
		var dtInicio = new Date();
		var dtFim = new Date();
		
		dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
		dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
		
		if (dtInicio > dtFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}

</script>

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
					<i18n:message key="avaliacaoFornecedor.avaliacaoFornecedor" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message
								key="avaliacaoFornecedor.cadastro" /> </a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message
								key="avaliacaoFornecedor.pesquisa" /> </a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/avaliacaoFornecedor/avaliacaoFornecedor'>
								<input type='hidden' name='idAvaliacaoFornecedor' /> 
								<input type='hidden' name='idFornecedor' id="idFornecedor" /> 
								<input type='hidden' name='idResponsavel' id="idResponsavel" /> 
								<input type="hidden" name="listCriteriosQualidadeSerializado" id="listCriteriosQualidadeSerializado"/> 
								<input type="hidden" name="listAprovacaoReferenciaSerializado" id="listAprovacaoReferenciaSerializado"/> 
                                <input type="hidden" name="decisaoQualificacao" id="decisaoQualificacao"/> 

								<div class="col_100">
									<h2 class="section">
										<i18n:message key="avaliacaoFonecedor.dadosFornecedor" />
									</h2>
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="avaliacaoFonecedor.razaoSocial" /></label>
											<div>
												<input  type='text' id="razaoSocial" name="razaoSocial" maxlength="256" class="Valid[Required] Description[avaliacaoFonecedor.razaoSocial]" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label><i18n:message key="avaliacaoFonecedor.endereco" /></label>
											<div>
												<input readonly="readonly" type='text' id="enderecoStr" name="enderecoStr" maxlength="256"  />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<div class="col_40">
											<fieldset>
												<label><i18n:message key="avaliacaoFonecedor.cidade" /></label>
												<div>
													<input readonly="readonly" type='text' id="nomeCidade" name="nomeCidade" maxlength="256"  />
												</div>
											</fieldset>
										</div>
										
										<div class="col_10">
											<fieldset>
												<label><i18n:message key="avaliacaoFonecedor.uf" /></label>
												<div>
													<input readonly="readonly" type='text' id="siglaUf" name="siglaUf" maxlength="256"  />
												</div>
											</fieldset>
										</div>
										
										<div class="col_25">
											<fieldset>
												<label><i18n:message key="avaliacaoFonecedor.cep" /></label>
												<div>
													<input readonly="readonly" type='text' id="cep" name="cep" maxlength="256"  />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label><i18n:message key="avaliacaoFonecedor.telefone" /></label>
												<div>
													<input readonly="readonly" type='text' id="telefone" name="telefone" maxlength="256"  />
												</div>
											</fieldset>
										</div>
									</div>
									
									<div class="col_100">
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="avaliacaoFonecedor.cnpj" /></label>
												<div>
													<input readonly="readonly" type='text' id="cnpj" name="cnpj" maxlength="256" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label><i18n:message key="avaliacaoFonecedor.inscricaoEstadual" /></label>
												<div>
													<input readonly="readonly" type='text' id="inscricaoEstadual" name="inscricaoEstadual" maxlength="256" />
												</div>
											</fieldset>
										</div>
									</div>
									<div class="col_100">
											<fieldset>
												<label><i18n:message key="avaliacaoFonecedor.email" /></label>
												<div>
													<input readonly="readonly" type='text' id="email" name="email" maxlength="256"  />
												</div>
											</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label><i18n:message key="avaliacaoFonecedor.nomeDoscontatos" /></label>
											<div id="divContatos">
												<input type="text" name="contato" id="contato" maxlength="245">
											</div>
										</fieldset>
									</div>
								    <div class="col_100">
										<fieldset>
											<label><i18n:message key="avaliacaoFonecedor.escopo" /></label>
											<div id="divEscopo">
												
											</div>
										</fieldset>
									</div>   
								</div>
								<div class="col_100">
									<h2 class="section">
										<i18n:message key="avaliacaoFonecedor.criterioQualidade" />
									</h2>
									<div class="col_100">
										<button type="button" 
													id="btnAddCriterio" 
													name="btnAddCriterio" 
													style="margin-top: 5px; margin-left: 3px; float: left;" 
													class="light img_icon has_text" onclick="adicionarCriterio()">
													<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/pencil.png">													
													<span style="font-size: 12px !important;"><i18n:message key='avaliacaoFornecedor.adicionarCriterio'/></span>													
										</button>
									</div>
									<div class="col_100">
										<fieldset>
											<div id="divCriterio">
												<div id="divOS"	style="height: 120px; width: 100%; overflow: auto; border: 1px solid black;">
													<table id="tblCriterio" cellpadding="0" cellspacing="0" width="100%" class="table table-bordered table-striped">
														<tr>
															<th style="text-align: center" class="linhaSubtituloGrid" width="16px" >&nbsp;</th>
															<th></th>															
													 		<th class="linhaSubtituloGrid"><i18n:message key='citcorpore.comum.requisito'/></th>
															<th class="linhaSubtituloGrid"><i18n:message key='colaborador.observacao'/></th>
														</tr>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<h2 class="section">
										<i18n:message key="avaliacaoFonecedor.aprovacaoReferencia" />
									</h2>
									<div class="col_100">
										<button type="button" 
													id="btnAddAprovacao" 
													name="btnAddAprovacao" 
													style="margin-top: 5px; margin-left: 3px; float: left;" 
													class="light img_icon has_text">
													<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/pencil.png">													
													<span style="font-size: 12px !important;"><i18n:message key='avaliacaoFornecedor.adicionarAprovacaoReferencia'/></span>													
												</button>
										
									</div>
									<div class="col_100">
										<fieldset>
											<div id="divAprovacaoReferencia">
											<div id="divOS"	style="height: 120px; width: 100%; overflow: auto; border: 1px solid black;">
													<table id="tblAprovacao" cellpadding="0" cellspacing="0" width="100%" class="table table-bordered table-striped">
														<tr>
															<th style="text-align: center" class="linhaSubtituloGrid" width="16px">&nbsp;</th>
															<th></th>															
													 		<th class="linhaSubtituloGrid"><i18n:message key='cronograma.nome'/></th>
															<th class="linhaSubtituloGrid"><i18n:message key='fornecedor.telefone'/></th>
															<th class="linhaSubtituloGrid"><i18n:message key='colaborador.observacao'/></th>
														</tr>
													</table>
												</div>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<h2 class="section">
										<i18n:message key="avaliacaoFornecedor.conclusao" />
									</h2>
									<div class="col_100">
											<fieldset>
												<label ><i18n:message key="avaliacaoFonecedor.observacao" /></label>
												<div class="inline">
													<textarea name="observacoesAvaliacaoFornecedor" id="obsAvaliacaoFornecedor" ></textarea>
												</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
								<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="avaliacaoFonecedor.responsavel" /></label>
												<div class="inline">
													<input onclick="adicionarResponsavel();" type="text" name="nomeResponsavel" id="nomeResponsavel" class="Valid[Required] Description[avaliacaoFonecedor.responsavel]" />
												</div>
										</fieldset>
									</div>
									<div class="col_16">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="avaliacaoFonecedor.dataAvaliacao"/></label>
												<div class="inline">
												<input  type='text'  id="dataAvaliacao"  name="dataAvaliacao" maxlength="10" size="10"  class="Valid[Data,Required] Format[Data] datepicker Description[avaliacaoFonecedor.dataAvaliacao]" />		
												</div>
										</fieldset>
									</div>
								</div>
								<br> <br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /> </span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /> </span>
								</button>
								<button type='button' name='btnExcluir' id="btnExcluir"
									class="light" onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" /> </span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_AVALIACAOFORNECEDOR' id='LOOKUP_AVALIACAOFORNECEDOR' top='0' left='0'
									len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
					<div id="POPUP_CRITERIO" title=<i18n:message key="citcorpore.ui.janela.popup.titulo.Adicionar_OS" />>
						<form name="formCriterio" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/criterioAvaliacao/criterioAvaliacao">
							<input type="hidden" name="idCriterio" id="idCriterio" />
							<input type="hidden" name="sequencia" id="sequencia" />
								<div class="col_100">
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="avaliacaoFornecedor.criterio"/></label>
											<div>
												<input readonly="readonly" type='text' onclick="adicionarDescricao();" id="descricao" name="descricao" maxlength="256" class="Valid[Required] Description[avaliacaoFornecedor.criterio]" />
											</div>
										</fieldset>
									</div>
									<div class="col_100">
											<fieldset>
												<label><i18n:message key="citcorpore.comum.observacoes"/></label>
												<div>
													<textarea  id="obsCriterio" name="obs"  rows="4" cols="2"></textarea>
												</div>
											</fieldset>
									</div>
									<br> <br>
									<button type='button' name='btnGravar' class="light"
										onclick='document.formCriterio.fireEvent("atualizaGridCriterio");'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /> </span>
									</button>
									<button type='button' name='btnLimpar' class="light"
										onclick='document.formCriterio.clear();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" /> </span>
									</button>
									
									<button type='button' name='btnLimpar' class="light"
										onclick='fechaCriterio();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/alert.png">
										<span><i18n:message key="citcorpore.comum.fechar" /> </span>
									</button>
							</div>
						</form>
					</div>
					<div id="POPUP_APROVACAO" title=<i18n:message key="citcorpore.ui.janela.popup.titulo.Adicionar_OS" />>
						<form name="formAprovacao" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/avaliacaoReferenciaFornecedor/avaliacaoReferenciaFornecedor">
							<input type="hidden" name="idEmpregado" id="idEmpregado" />
							<input type="hidden" name="sequencia" id="sequencia" />
							<div class="col_100">
								<div class="col_100">
									<fieldset>
										<label class="campoObrigatorio" ><i18n:message key="citcorpore.comum.nome"/></label>
										<div>
											<input readonly="readonly" type='text' onclick="adicionarNome();" id="nome" name="nome" maxlength="256" class="Valid[Required] Description[citcorpore.comum.nome]"  />
										</div>
									</fieldset>
								</div>
								
								<div class="col_100">
									 <div class="col_40">
										<fieldset style='height:100px'>
											<label><i18n:message key="citcorpore.comum.telefone"/></label>
											<div>
												<input type='text' id="telefone" name="telefone" maxlength="256" class="Valid[Required] Description[citcorpore.comum.telefone]" />
											</div>
										</fieldset>
									</div>
									<div class="col_60">
											<fieldset style='height:100px'>
												<label><i18n:message key="colaborador.observacao" /></label>
												<div class="inline">
													<textarea id="obsAvaliacaoReferencia" name="observacoes"></textarea>
												</div>
											</fieldset>
									</div>
								</div>
									<button type='button' name='btnGravar' class="light"
										onclick='document.formAprovacao.fireEvent("atualizaGridAvaliacao");'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /> </span>
									</button>
									<button type='button' name='btnLimpar' class="light"
										onclick='document.formAprovacao.clear();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" /> </span>
									</button>
									<button type='button' name='btnLimpar' class="light"
										onclick='fechaAprovacao();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/alert.png">
										<span><i18n:message key="citcorpore.comum.fechar" /> </span>
									</button>
							</div>
						</form>
					</div>
					</div>
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	<%@include file="/include/footer.jsp"%>
	
	<div id="POPUP_FORNECEDOR" title="<i18n:message key="avaliacaoFornecedor.fornecedor" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaFornecedor' style="width: 540px">
							<cit:findField formName='formPesquisaFornecedor'
								lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div> 
	
	<div id="POPUP_CRITERIOAVALIACAO" title="<i18n:message key="criterioAvaliacao.criterio_avaliacao"/>">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<form name='formPesquisaCriterio' style="width: 540px">
							<cit:findField formName='formPesquisaCriterio'
								lockupName='LOOKUP_CRITERIOAVALIACAO_FORNECEDOR' id='LOOKUP_CRITERIOAVALIACAO' top='0'
								left='0' len='550' heigth='400' javascriptCode='true'
								htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_EMPREGADOS" title="Cliente ou Gestor">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaAprovacao' style="width: 540px">
								<cit:findField formName='formPesquisaAprovacao'
									lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div> 
		<div id="POPUP_RESPONSAVEL" title="<i18n:message key="citcorpore.comum.pesquisar" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaUsuario' style="width: 540px">
								<input type="hidden" id="isNotificacao" name="isNotificacao">
								<cit:findField formName='formPesquisaUsuario'  lockupName='LOOKUP_EMPREGADO' id='LOOKUP_RESPONSAVEL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div> 
</body>
</html>
