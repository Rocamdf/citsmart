<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.AlcadaDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.LimiteAlcadaDTO"%>
<!doctype html public "">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
			
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
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
		
		<script type="text/javascript">
			var objTab = null;
			var contadorGlobal = 1;
			
			addEvent(window, "load", load, false);
			
			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
				
				$("#POPUP_GRUPO").dialog({
					autoOpen : false,
					width : 620,
					height : 400,
					modal : true
				});
				
				$("#POPUP_ADICIONARLIMITE").dialog({
					autoOpen : false,
					width : 1050,
					height : 310,
					modal : true
				});
			}
			
			function LOOKUP_ALCADA_select(id, desc) {
				document.form.restore({
					idAlcada : id
				});
			}
			
			function LOOKUP_GRUPO_select(id, desc) {
				document.getElementById("idGrupoLimite").value = id;
				
			    var valor = desc.split('-');
			    var nome = "";
				for(var i = 0 ; i < valor.length; i++){
					if(i < (valor.length - 1)){
						nome += valor[i];
					}
					if(i < (valor.length - 2) && valor.length > 2){
						nome += "-";
					}
				}
				document.getElementById("grupoLimite").value = nome;
				fecharPopupGrupo();
			}
			
			function excluir() {
				if (document.getElementById("idAlcada").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
			}
			
			$(function() {
				$("#addGrupoItemConfig").click(function() {
					$("#POPUP_ADICIONARLIMITE").dialog("open");
				});
			});
			
			function pesquisaGrupos(){
				$("#POPUP_GRUPO").dialog("open");
			}
			
			function fecharPopupGrupo(){
				$("#POPUP_GRUPO").dialog("close");
			}
			
			function adicionarLimite(){
				var idGrupoLimite = document.getElementById("idGrupoLimite").value;
				var nomeGrupoLimite = document.getElementById("grupoLimite").value;
				var tipoLimite = document.getElementById("tipoLimite").value;
				var abrangenciaCentroCustoLimite = document.getElementById("abrangenciaCentroCustoLimite").value;
				var limiteItemUsoInterno = document.getElementById("limiteItemUsoInterno").value;
				var limiteMensalUsoInterno = document.getElementById("limiteMensalUsoInterno").value;
                var limiteItemAtendCliente = document.getElementById("limiteItemAtendCliente").value;
                var limiteMensalAtendCliente = document.getElementById("limiteMensalAtendCliente").value;
				var situacaoLimite = "";
				
				var radioSituacao = document.formLimite.situacaoLimite;
				var radioLength = radioSituacao.length;
				for(var i = 0; i < radioLength; i++) {
					if(radioSituacao[i].checked) {
						situacaoLimite = radioSituacao[i].value;
					}
				}
				
				if ( idGrupoLimite < 1 ) {
					alert(i18n_message("grupo.grupo") + ": " + i18n_message("alcada.limite.campo_obrigatorio"));
					return;
				}
				
				if ( situacaoLimite == "" ) {
					alert(i18n_message("citcorpore.comum.situacao") + ": " + i18n_message("alcada.limite.campo_obrigatorio"));
					return;
				}
				addLinhaTabelaLimite(idGrupoLimite, nomeGrupoLimite, tipoLimite, abrangenciaCentroCustoLimite, limiteItemUsoInterno, limiteMensalUsoInterno, limiteItemAtendCliente, limiteMensalAtendCliente, situacaoLimite);
			}
			
			function addLinhaTabelaLimite(idGrupoLimite, nomeGrupoLimite, tipoLimite, abrangenciaCentroCustoLimite, limiteItemUsoInterno, limiteMensalUsoInterno, limiteItemAtendCliente, limiteMensalAtendCliente, situacaoLimite){
				var tbl = document.getElementById('tabelaLimites');
				tbl.style.display = 'block';
				var tamanhoTabela = tbl.rows.length;
				var nomeTipoLimite = "";
				var nomeAbrangenciaCentroCustoLimite = "";
				var nomeSituacaoLimite = "";
				
				if (document.getElementById('idGrupoLimite_'+idGrupoLimite) != null) {
					alert(i18n_message("alcada.limite.existe_limite_grupo"));
					return;
				}
				
				if (tipoLimite == "F") {
					nomeTipoLimite = i18n_message("alcada.limite.faixaValores");
				} else if (tipoLimite == "Q") {
					nomeTipoLimite = i18n_message("alcada.limite.qualquerValor");
				}
				
				if (abrangenciaCentroCustoLimite == "T") {
					nomeAbrangenciaCentroCustoLimite = i18n_message("alcada.limite.todos");
				} else if (abrangenciaCentroCustoLimite == "R") {
					nomeAbrangenciaCentroCustoLimite = i18n_message("alcada.limite.somenteResponsal");
				}
				
				if (situacaoLimite == "A") {
					nomeSituacaoLimite = i18n_message("citcorpore.comum.ativo");
				} else if (situacaoLimite == "I") {
					nomeSituacaoLimite = i18n_message("citcorpore.comum.inativo");
				}
				
				var row = tbl.insertRow(tamanhoTabela);
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<img id="imgDelLimite' + contadorGlobal + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaLimites\', this.parentNode.parentNode.rowIndex);">';
				coluna = row.insertCell(1);
				coluna.innerHTML = nomeGrupoLimite + '<input type="hidden" id="idGrupoLimite' + contadorGlobal + '" name="idGrupoLimite" value="' + idGrupoLimite + '" />' + '<input type="hidden" id="idGrupoLimite_' + idGrupoLimite + '" name="idGrupoLimite" value="' + idGrupoLimite + '" />';
				coluna = row.insertCell(2);
				coluna.innerHTML = nomeTipoLimite + '<input type="hidden" id="idTipoLimite' + contadorGlobal + '" name="idTipoLimite" value="' + tipoLimite + '" />';
				coluna = row.insertCell(3);
				coluna.innerHTML = nomeAbrangenciaCentroCustoLimite + '<input type="hidden" id="idAbrangenciaCentroCustoLimite' + contadorGlobal + '" name="idAbrangenciaCentroCustoLimite" value="' + abrangenciaCentroCustoLimite + '" />';

				coluna = row.insertCell(4);
				coluna.innerHTML = nomeSituacaoLimite + '<input type="hidden" id="idSituacaoLimite' + contadorGlobal + '" name="SituacaoLimite" value="' + situacaoLimite + '" />';

                coluna = row.insertCell(5);
                var limiteStr = ""+limiteItemUsoInterno;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteItemUsoInterno' + contadorGlobal + '" name="idLimiteItemUsoInterno" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteItemUsoInterno' + contadorGlobal + '" name="idLimiteItemUsoInterno" value="' + limiteMensalUsoInterno + '" />';
				
                coluna = row.insertCell(6);
                limiteStr = ""+limiteMensalUsoInterno;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteMensalUsoInterno' + contadorGlobal + '" name="idLimiteMensalUsoInterno" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteMensalUsoInterno' + contadorGlobal + '" name="idLimiteMensalUsoInterno" value="' + limiteMensalUsoInterno + '" />';

                coluna = row.insertCell(7);
                var limiteStr = ""+limiteItemAtendCliente;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteItemAtendCliente' + contadorGlobal + '" name="idLimiteItemAtendCliente" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteItemAtendCliente' + contadorGlobal + '" name="idLimiteItemAtendCliente" value="' + limiteMensalUsoInterno + '" />';
                
                coluna = row.insertCell(8);
                limiteStr = ""+limiteMensalAtendCliente;
                if (limiteStr == 'null')
                    limiteStr = "&nbsp;";
                coluna.innerHTML = limiteStr + '<input type="hidden" id="idLimiteMensalAtendCliente' + contadorGlobal + '" name="idLimiteMensalAtendCliente" value="' + limiteStr + '" />' + '<input type="hidden" id="idLimiteMensalAtendCliente' + contadorGlobal + '" name="idLimiteMensalAtendCliente" value="' + limiteMensalUsoInterno + '" />';

                $("#POPUP_ADICIONARLIMITE").dialog("close");
				document.formLimite.clear();
				setSituacaoLimite();
				contadorGlobal++;
			}
			
			function desabilitaValores(){
				var tipo = document.getElementById('tipoLimite').options[document.getElementById('tipoLimite').selectedIndex].value;
				if(tipo == "Q"){
					document.getElementById("limiteItemUsoInterno").value = "";
					document.getElementById("limiteItemUsoInterno").disabled = -1;
                    document.getElementById("limiteItemAtendCliente").value = "";
                    document.getElementById("limiteItemAtendCliente").disabled = -1;
					document.getElementById("divValorMensal").style.display = "none";
					document.getElementById("limiteMensalUsoInterno").value = "";
					document.getElementById("limiteMensalUsoInterno").disabled = -1;
                    document.getElementById("limiteMensalAtendCliente").value = "";
                    document.getElementById("limiteMensalAtendCliente").disabled = -1;
					document.getElementById("divValorItem").style.display = "none";
				}else{
					document.getElementById("limiteItemUsoInterno").disabled = 0;
                    document.getElementById("limiteItemAtendCliente").disabled = 0;
					document.getElementById("divValorItem").style.display = "block";
					document.getElementById("limiteMensalUsoInterno").disabled = 0;
                    document.getElementById("limiteMensalAtendCliente").disabled = 0;
					document.getElementById("divValorMensal").style.display = "block";
				}
			}
			
			function removeLinhaTabela(idTabela, rowIndex) {
				 if(idTabela == "tabelaLimites"){
						if (confirm(i18n_message("citcorpore.comum.deleta"))) {
							HTMLUtils.deleteRow(idTabela, rowIndex);
						} 
				 }
			}
			
			function LimiteAlcadaDTO(idGrupo, tipoLimite, abrangenciaCentroCusto, limiteItemUsoInterno, limiteMensalUsoInterno, limiteItemAtendCliente, limiteMensalAtendCliente, situacao){
				this.idGrupo = idGrupo;
		 		this.tipoLimite = tipoLimite; 
		 		this.abrangenciaCentroCusto = abrangenciaCentroCusto;
		 		this.limiteItemUsoInterno = limiteItemUsoInterno;
		 		this.limiteItemAtendCliente = limiteItemAtendCliente;
                this.limiteMensalUsoInterno = limiteMensalUsoInterno;
                this.limiteMensalAtendCliente = limiteMensalAtendCliente;
		 		this.situacao = situacao;
		 	}
			
			function serializaLimite() {
				var tabela = document.getElementById('tabelaLimites');
				var count = tabela.rows.length;
				var listaLimites = [];
				
				for ( var i = 1; i < contadorGlobal; i++) {
					if (document.getElementById('idGrupoLimite' + i) != "" && document.getElementById('idGrupoLimite' + i) != null) {
						var idGrupoLimite = document.getElementById('idGrupoLimite' + i).value;
						var idTipoLimite = document.getElementById('idTipoLimite' + i).value;
						var idAbrangenciaCentroCustoLimite = document.getElementById('idAbrangenciaCentroCustoLimite' + i).value;
						var idLimiteItemUsoInterno = document.getElementById('idLimiteItemUsoInterno' + i).value;
						var idLimiteMensalUsoInterno = document.getElementById('idLimiteMensalUsoInterno' + i).value;
				        var idLimiteItemAtendCliente = document.getElementById('idLimiteItemAtendCliente' + i).value;
	                    var idLimiteMensalAtendCliente = document.getElementById('idLimiteMensalAtendCliente' + i).value;
	            		var idSituacaoLimite = document.getElementById('idSituacaoLimite' + i).value;
						var limite = new LimiteAlcadaDTO(idGrupoLimite, idTipoLimite, idAbrangenciaCentroCustoLimite, idLimiteItemUsoInterno, idLimiteMensalUsoInterno, idLimiteItemAtendCliente, idLimiteMensalAtendCliente, idSituacaoLimite);
						listaLimites.push(limite);
					}
				}
				document.form.listLimites.value = ObjectUtils.serializeObjects(listaLimites);
				document.form.save();/* 
				document.getElementById('tabelaLimites').style.display = "none"; */
			}
			
			function setSituacao() {
				var radioSituacao = document.form.situacao;
				var radioLength = radioSituacao.length;
				for(var i = 0; i < radioLength; i++) {
					radioSituacao[i].checked = false;
					if(radioSituacao[i].value == "A") {
						radioSituacao[i].checked = true;
					}
				}
			}
			
			function setSituacaoLimite() {
				var radioSituacao = document.formLimite.situacaoLimite;
				var radioLength = radioSituacao.length;
				for(var i = 0; i < radioLength; i++) {
					radioSituacao[i].checked = false;
					if(radioSituacao[i].value == "A") {
						radioSituacao[i].checked = true;
					}
				}
				
				document.getElementById("limiteItemUsoInterno").disabled = 0;
                document.getElementById("limiteItemAtendCliente").disabled = 0;
				document.getElementById("divValorItem").style.display = "block";
				document.getElementById("limiteMensalUsoInterno").disabled = 0;
                document.getElementById("limiteMensalAtendCliente").disabled = 0;
				document.getElementById("divValorMensal").style.display = "block";
			}
			
			

			function deleteAllRows() {
				var tabela = document.getElementById('tabelaLimites');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				contadorGlobal = 1;
				
				document.getElementById('tabelaLimites').style.display = "none";
			}
			
		</script>		
				
	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
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
	</head>
	<body>
		<div id="wrapper" style="overflow: hidden;">
		<% 
			if (iframe == null) { 
		%>
			<%@ include file="/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file="/include/menu_horizontal.jsp" %>
			<%
				}
			%>				
				<div class="flat_area grid_16">
					<h2><i18n:message key="alcada"/></h2>
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="alcada.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="alcada.pesquisa"/></a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/alcada/alcada'>
									<div class="columns clearfix">
										<input type='hidden' name='idAlcada'/>
										<input type='hidden' name='listLimites'/>
										<div class="columns clearfix">
											<div class="col_40">
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="alcada.nome"/></label>
														<div>
														  <input type='text' name="nomeAlcada" maxlength="70" size="70" class="Valid[Required] Description[alcada.nome]"/>
														</div>
												</fieldset>
											</div>
											<div class="col_30">
												<fieldset style="height: 55px !important;">
													<label class="campoObrigatorio"><i18n:message key="alcada.tipo"/></label>
														<div>
														  <select name='tipoAlcada' id='tipoAlcada' class="Valid[Required] Description[alcada.tipo]"></select>
														</div>
												</fieldset>
											</div>
											<div class="col_30">
												<fieldset style="height: 55px !important;">
													<label class="campoObrigatorio campoEsquerda"><i18n:message key="citcorpore.comum.situacao"/></label>
													<input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacao" id="situacao" value="A" checked="checked"/><i18n:message key="citcorpore.comum.ativo"/>
													<input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacao" id="situacao" value="I"/><i18n:message key="citcorpore.comum.inativo"/>
												</fieldset>
											</div>
										</div>
										<br>
									</div>
									
									<h2 class="section">
										<i18n:message key="alcada.limite" />
									</h2>
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<label id="addGrupoItemConfig" style="cursor: pointer;" title="<i18n:message key="alcada.limite.clique_adicionar" />">
													<i18n:message key="alcada.limite.adicionar_limite" />
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png">
												</label>
												<div  id="divLimites">
												<table class="table" id="tabelaLimites" style="display: none;">
													<tr>
														<th style="width: 1%;"></th>
														<th ><i18n:message key="grupo.grupo" /></th>
														<th style="width: 15%;"><i18n:message  key="alcada.limite.tipoLimite" /></th>
														<th style="width: 15%;"><i18n:message  key="alcada.limite.abrangencia" /></th>
                                                        <th style="width: 5%;"><i18n:message  key="citcorpore.comum.situacao" /></th>
                                                        <th style="width: 10%;"><i18n:message  key="alcada.limite.limiteItemUsoInterno" /></th>
                                                        <th style="width: 10%;"><i18n:message  key="alcada.limite.limiteMensalUsoInterno" /></th>
                                                        <th style="width: 10%;"><i18n:message  key="alcada.limite.limiteItemAtendCliente" /></th>
                                                        <th style="width: 10%;"><i18n:message  key="alcada.limite.limiteMensalAtendCliente" /></th>
													</tr>
												</table>
											</div>
											</fieldset>
										</div>
									</div>
									
									<br><br>
									<button type='button' name='btnGravar' class="light" onclick='serializaLimite();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();document.formLimite.clear();deleteAllRows();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" /></span>
									</button>
									<button type='button' name='btnExcluir' class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" /></span>
									</button>
									
									
									
									
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_ALCADA' id='LOOKUP_ALCADA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<!-- TELA DE CADASTRO DE LIMITES -->
		<div id="POPUP_ADICIONARLIMITE" title="<i18n:message key="alcada.limite.adicionar_limite" />">
			<div class="box grid_16 ">
				<div class="toggle_container">
					<div class="section">
					<form name='formLimite' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/alcada/alcada'>
						<div class="col_100">
								<div class="col_40">				
									<fieldset style="height: 55px">
										<label class="campoObrigatorio">
											<i18n:message key="grupo.grupo"/>
										</label>
										<div>
											<input onclick="pesquisaGrupos()" readonly="readonly" style="width: 90% !important;" type='text' name="grupoLimite" id="grupoLimite" maxlength="70" size="70"  />
											<img onclick="pesquisaGrupos()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											<input type="hidden" name="idGrupoLimite" id="idGrupoLimite" />
										</div>
									</fieldset>
								</div>
								<div class="col_20">				
									<fieldset style="height: 55px">
										<label><i18n:message key="alcada.limite.tipoLimite"/></label>
											<div>
											  <select name='tipoLimite' id='tipoLimite' onchange="desabilitaValores(this);" class="Valid[Required] Description[tipoLimite]">
											  	<option value="F"><i18n:message key="alcada.limite.faixaValores"/></option>
											  	<option value="Q"><i18n:message key="alcada.limite.qualquerValor"/></option>
											  </select>
											</div>
									</fieldset>
                				</div>
								<div class="col_25">
									<fieldset style="height: 55px">
										<label class="campoEsquerda"><i18n:message key="alcada.limite.abrangencia"/></label>
											<div>
											  <select name='abrangenciaCentroCustoLimite' id='abrangenciaCentroCustoLimite' class="Valid[Required] Description[alcada.limite.abrangencia]">
											  	<option value="T"><i18n:message key="alcada.limite.todos"/></option>
											  	<option value="R"><i18n:message key="alcada.limite.somenteResponsal"/></option>
											  </select>
											</div>
									</fieldset>
								</div>
				                <div class="col_15">
				                    <fieldset style="height: 55px !important;">
				                        <label class="campoObrigatorio"><i18n:message key="citcorpore.comum.situacao"/></label>
				                        <input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacaoLimite" id="situacaoLimite" value="A" checked="checked"/><i18n:message key="citcorpore.comum.ativo"/>
				                        <input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacaoLimite" id="situacaoLimite" value="I"/><i18n:message key="citcorpore.comum.inativo"/>
				                    </fieldset>
				                </div>
                    </div>
					<div class="col_100" id="divValorItem">
                        <div class="col_50">
							<fieldset>
								<label><i18n:message key="alcada.limite.limiteItemUsoInterno"/></label>
									<div>
									  <input type='text' name='limiteItemUsoInterno' id="limiteItemUsoInterno" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteItemUsoInterno] Format[Moeda]"/>
									</div>
							</fieldset>
                        </div>
                        <div class="col_50">
		                    <fieldset>
		                        <label><i18n:message key="alcada.limite.limiteItemAtendCliente"/></label>
		                            <div>
		                              <input type='text' name='limiteItemAtendCliente' id="limiteItemAtendCliente" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteItemAtendCliente] Format[Moeda]"/>
		                            </div>
		                    </fieldset>
                        </div>
					</div>
					<div class="col_100" id="divValorMensal">
                        <div class="col_50">
							<fieldset>
								<label><i18n:message key="alcada.limite.limiteMensalUsoInterno"/></label>
									<div>
									  <input type='text' name='limiteMensalUsoInterno' id="limiteMensalUsoInterno" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteMensalUsoInterno] Format[Moeda]"/>
									</div>
							</fieldset>
                        </div>
                        <div class="col_50">
		                    <fieldset>
		                        <label><i18n:message key="alcada.limite.limiteMensalAtendCliente"/></label>
		                            <div>
		                              <input type='text' name='limiteMensalAtendCliente' id="limiteMensalAtendCliente" size="20" maxlength="8" style="width: 250px !important;" class="Description[alcada.limite.limiteMensalAtendCliente] Format[Moeda]"/>
		                            </div>
		                    </fieldset>
                        </div>
					</div>
		</div>
		<br /><br />
		<button type='button' name='btnGravar' class="light" onclick='adicionarLimite();'>
				<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/adcionar.png">
				<span><i18n:message key="citcorpore.comum.adicionar" /></span>
			</button>
		</form>
						</div>
				</div>
			</div>
		</div>
		<!-- FIM TELA DE CADASTRO DE LIMITES -->
		
		<div id="POPUP_GRUPO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: 570px;">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaFabricante' style="width: 540px">
								<cit:findField formName='formPesquisaFabricante' 
									lockupName='LOOKUP_GRUPO' id='LOOKUP_GRUPO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>