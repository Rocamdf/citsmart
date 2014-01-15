<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.LocalidadeUnidadeDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UnidadesAccServicosDTO"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "">
<html>
	<head>
	<%
		response.setHeader("Cache-Control", "no-cache");
		    response.setHeader("Pragma", "no-cache");
		    response.setDateHeader("Expires", -1);
		    String iframe = "";
		    iframe = request.getParameter("iframe");
		    
			String controleAccUnidade = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.CONTROLE_ACC_UNIDADE_INC_SOLIC, "N");
			
			if(!UtilStrings.isNotVazio(controleAccUnidade)){
		controleAccUnidade = "N";
			}
	%>
	
	<title><i18n:message key="citcorpore.comum.title" /></title>
	
	<%@include file="/include/security/security.jsp"%>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	
	<script charset="ISO-8859-1"  type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	
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
		var popup;
		addEvent(window, "load", load, false);
		var contLocalidade = 0;
		function load() {
				popup = new PopupManager(800, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			   document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
			 $('#cep').mask('99999999');
			
			
		}
		
		
		
		function LOOKUP_UNIDADE_select(id, desc) {
			document.form.restore({idUnidade : id
			});
		}
		
		function listaContrato(){
			if (document.getElementById('idUnidadePai').value != "")
				{
				document.getElementById("divListaContratos").setVisible(false);
				}
			else {
				document.getElementById("divListaContratos").setVisible(true);
			}
		}
		
		function LOOKUP_SERVICO_select(id, desc) {
			
			var tabela = document.getElementById('tabelaServico');
			var lastRow = tabela.rows.length;
			
			if (lastRow > 2) {
				var arrayIdServico = document.form.idServico;
				for ( var i = 0; i < arrayIdServico.length; i++) {
					if (arrayIdServico[i].value == id) {
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return;
					}
				}
			} else if (lastRow == 2) {
				var idServico = document.form.idServico;
				if (idServico.value == id) {
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return;
				}
			}
			
			insereRow(id, desc);
			
			$("#POPUP_SERVICO").dialog("close");
			exibeGrid();
		}
		
		
	
		function update() {
			if (document.getElementById('idUnidade').value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					serializaLocalidade();
					document.form.fireEvent("delete");
					deleteAllRows();
				}
			}
		}
		
		$(function() {
			$("#POPUP_SERVICO").dialog( {
				autoOpen : false,
				width : "90%" ,
				height : $(window).height()-100,
				modal : true
			});
		});
		
		$(function() {
			$("#addServico").click(function() {
				$("#POPUP_SERVICO").dialog("open");
			});
		});
		
		function limpar() {
			deleteAllRows();
			deleteAllRowsLocalidade();
			document.getElementById('gridServicos').style.display = 'none';
			ocultaGridLocalidade();
			$('#tabelaLocalidade').hide();
			document.form.clear();
		}
	
		function exibeGrid() {
			document.getElementById('gridServicos').style.display = 'block';
		}
		function exibeGridLocalidade() {
			$('#gridLocalidade').show();
		}
		function ocultaGridLocalidade() {
			$('#gridLocalidade').hide();
		}
		function ocultaGrid() {
			document.getElementById('gridServicos').style.display = 'none';
			
		}
		
		var countServico = 0;
		
		function insereRow(id, desc) {
			var tabela = document.getElementById('tabelaServico');
			var lastRow = tabela.rows.length;
	
			var row = tabela.insertRow(lastRow);
			countServico++;
	
			var valor = desc.split(' - ');
	
			var coluna = row.insertCell(0);
			coluna.innerHTML = '<img id="imgExcluiServico' + countServico + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir"/>" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaServico\', this.parentNode.parentNode.rowIndex);">';
	
			coluna = row.insertCell(1);
			coluna.innerHTML = valor[0] + '<input type="hidden" id="idServico' + countServico + '" name="idServico" value="' + id + '" />';
	
			coluna = row.insertCell(2);
			coluna.innerHTML = valor[1];
			
		}
		
		function restoreRow() {
			var tabela = document.getElementById('tabelaServico');
			var lastRow = tabela.rows.length;

			var row = tabela.insertRow(lastRow);
			countServico++;
			
			var coluna = row.insertCell(0);
			coluna.innerHTML = '<img id="imgExcluiServico' + countServico + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir"/>" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaServico\', this.parentNode.parentNode.rowIndex);">';
			
			coluna = row.insertCell(1);
			coluna.innerHTML = '<input type="hidden" id="idServico' + countServico + '" name="idServico"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="nomeServico' + countServico + '" name="nomeServico"/>';
			
			coluna = row.insertCell(2);
			coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="descricaoServico' + countServico + '" name="descricaoServico"/>';
		}
		
		var seqSelecionada = '';
		
		function setRestoreServico(idServico, nome, descricao) {
			if (seqSelecionada != '') {
				eval('document.form.idServico' + seqSelecionada + '.value = "' + idServico + '"');
				eval('document.form.nomeServico' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + nome + '\')');
				eval('document.form.descricaoServico' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
			}
			exibeGrid();
		}
		
		function removeLinhaTabela(idTabela, rowIndex) {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.servicoSerializado.value = eval('document.form.idServico' + rowIndex + '.value');
				document.form.fireEvent("excluirAssociacaoServico");
			}
		}

		function deleteAllRows() {
			var tabela = document.getElementById('tabelaServico');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
				
			}
			ocultaGrid();
		}
		
		function deleteAllRowsLocalidade() {
			var tabela2 = document.getElementById('tabelaLocalidade');
			var cont2 = tabela2.rows.length;

			if(cont2!=null){
				while (cont2 > 1) {
					tabela2.deleteRow(cont2 - 1);
					cont2--;
					
				}
				ocultaGridLocalidade();
			}
		}

		function gravar() {

			serializa();
			serializaLocalidade();
			document.form.save();
			
		}

		var seqSelecionada = '';
		var aux = '';
		
		serializa = function() {
			var tabela = document.getElementById('tabelaServico');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var servico = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idServico' + i);

				if (!trObj) {
					continue;
				}
				servico[contadorAux] = getServico(i);
				contadorAux = contadorAux + 1;
			}
			
			var servicosSerializados = ObjectUtils.serializeObjects(servico);
			
			document.form.servicosSerializados.value = servicosSerializados;
			
			return true;
		}

		getServico = function(seq) {
			var ServicoDTO = new CIT_ServicoDTO();
			ServicoDTO.sequencia = seq;
			ServicoDTO.idServico = eval('document.form.idServico' + seq + '.value');
			return ServicoDTO;
		}
		
		function LOOKUP_LOCALIDADE_select(id, desc){
			addLinhaTabelaLocalidade(id, desc, true);
		}
		
		$(function() {
			$("#addLocalidade").click(function() {
				$("#POPUP_LOCALIDADE").dialog("open");
			});
		});
		
		$(function() {
			$("#POPUP_LOCALIDADE").dialog( {
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
		});
		
		function addLinhaTabelaLocalidade(id, desc, valida){
			var tabelaLocalidade = document.getElementById('tabelaLocalidade');
			$('#tabelaLocalidade').show();
			exibeGridLocalidade();
			var lastRow = tabelaLocalidade.rows.length;
			if (valida){
				if (!validaAddLinhaTabelaLocalidade(lastRow, id)){
					return;
				}
			}
			var row = tabelaLocalidade.insertRow(lastRow);
			var coluna = row.insertCell(0);
			contLocalidade++;
			coluna.innerHTML = '<img id="imgDelLocalidade' + contLocalidade + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir"/>" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaLocalidade\', this.parentNode.parentNode.rowIndex);">';
			coluna = row.insertCell(1);
			coluna.innerHTML = desc + '<input type="hidden" id="idLocalidade' + contLocalidade + '" name="idLocalidade" value="' + id + '" />';
			$("#POPUP_LOCALIDADE").dialog("close");
			
		}
		function validaAddLinhaTabelaLocalidade(lastRow, id){
			if (lastRow > 2){
				var arrayIdLocalidade = document.form.idLocalidade;
				for (var i = 0; i < arrayIdLocalidade.length; i++){
					if (arrayIdLocalidade[i].value == id){
						alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
						return false;
					}
					
				}
			} else if (lastRow == 2){
				var idLocalidade = document.form.idLocalidade;
				if (idLocalidade.value == id){
					alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
					return false;
				}
			}
			return true;
		}
		function removeLinhaTabela(idTabela, rowIndex) {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				HTMLUtils.deleteRow(idTabela, rowIndex);
				var tabela = document.getElementById(idTabela);
				if (tabela.rows.length == 1){
					if (idTabela == 'tabelaLocalidade'){
						document.getElementById('dvItemConfig').style.display = 'none';
						return;
					}
					tabela.style.display = 'none';
				}
			}
		}
		
		function LocalidadeUnidadeDTO(_id, i){
	 		this.idLocalidade = _id; 
	 	}
		
		function serializaLocalidade(){
	 		var tabela = document.getElementById('tabelaLocalidade');
	 		var count = contLocalidade + 1;
	 		var listaDeLocalidades = [];
	 		for(var i = 1; i < count ; i++){
	 			if (document.getElementById('idLocalidade' + i) != "" && document.getElementById('idLocalidade' + i) != null){
	 			var trObj = document.getElementById('idLocalidade' + i).value;
	 			var localidade = new LocalidadeUnidadeDTO(trObj, i);
	 			listaDeLocalidades.push(localidade);
	 			}
	 		} 	
	 		var serializaLocalidade = ObjectUtils.serializeObjects(listaDeLocalidades);
			document.form.localidadesSerializadas.value = serializaLocalidade;
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
	</head>
	<body>
		<script type="text/javascript" src="../../cit/objects/ServicoDTO.js"></script>
		<div id="wrapper">
			<%
				if (iframe == null) {
			%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%
				}
			%>
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
					<h2>
						<i18n:message key="unidade.unidade" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><i18n:message
									key="unidade.cadastroUnidade" />
						</a></li>
						<li><a href="#tabs-2" class="round_top"><i18n:message
									key="unidade.pesquisaUnidade" />
						</a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/unidade/unidade'>
									<input type="hidden" name="locale" id="locale" value="" />
									<input type='hidden' name='dataInicio' /> 
									<input type='hidden' name='dataFim' />
									<input type='hidden' name='servicosSerializados'/>
									<input type='hidden' name='servicosDeserializados'/>
									<input type='hidden' id='servicoSerializado' name='servicoSerializado'/>
									<input type="hidden" name="localidadesSerializadas">
                                    <input type='hidden' name='idEndereco' />

									<div class="columns clearfix">
										<input type='hidden' name='idUnidade' /> 
										<input type='hidden' name='idEmpresa'/>
										<div class="col_100">
											<div class="col_50">
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome" />
													</label>
													<div>
														<input type='text' name="nome" maxlength="200" class="Valid[Required] Description[citcorpore.comum.nome]" />
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label > <i18n:message key="unidade.unidadePai" /></label>
													<div style="padding-top: 3px;">
														<select name='idUnidadePai' class="Description[unidade.unidadePai]" onchange="document.form.fireEvent('listaContrato')">
														</select>
													</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label > <i18n:message key="unidade.aceitaEntregaProduto" /></label>
													<div style="height: 35px">
														<input type="checkbox" id="aceitaEntregaProduto" name="aceitaEntregaProduto" value="S"/>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="col_100">
											<div class="col_50">
												<fieldset>
													<label><i18n:message key="unidade.email" /></label>
													<div>
														<input type='text' name="email" onchange="ValidacaoUtils.validaEmail(email, '');" class="Description[unidade.email]" maxlength="40"/>
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label><i18n:message key="citcorpore.comum.descricao" /></label>
													<div>
														<textarea name="descricao" maxlength="200" rows="1"></textarea>
													</div>
												</fieldset>
											</div>
										</div>
										<div class="columns clearfix">
										<h2 id="tituloEndereco" class="section"><i18n:message key="localidade.endereco"/></h2>
										<div class="col_33">
											<fieldset>
												<label><i18n:message key="unidade.pais" />
												</label>
												<div>
												<select name='idPais' id="idPais"  onchange="document.form.fireEvent('preencherComboUfs');" class="Description[unidade.pais]" ></select>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
										<fieldset>
											<label><i18n:message key="localidade.uf" />
											</label>
											<div>
											<select name='idUf' id="idUf" onchange="document.form.fireEvent('preencherComboCidade');" class="Description[uf]" ></select>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="localidade.cidade" />
											</label>
											<div>
												<select id="idCidade" name='idCidade'  class="Description[Cidade]"></select>
											</div>
										</fieldset>
									</div>
										<div class="col_50">
												<fieldset>
													<label><i18n:message key="unidade.logradouro" /></label>
													<div>
														<input type='text' name="logradouro"  class="Description[unidade.logradouro]" maxlength="200"/>
													</div>
												</fieldset>
										</div>
										<div class="col_50">
												<fieldset>
													<label><i18n:message key="localidade.complemento" /></label>
													<div>
														<input type='text' name="complemento"  class="Description[localidade.complemento]" maxlength="200"/>
													</div>
												</fieldset>
										</div>
										<div class="col_50">
												<fieldset>
													<label><i18n:message key="localidade.bairro" /></label>
													<div>
														<input type='text' name="bairro"  class="Description[localidade.bairro]" maxlength="200"/>
													</div>
												</fieldset>
										</div>
										<div class="col_25">
												<fieldset>
													<label><i18n:message key="localidade.numero" /></label>
													<div>
														<input type='text' name="numero"  class="Description[localidade.bairro]" maxlength="20"/>
													</div>
												</fieldset>
										</div>
										<div class="col_25">
												<fieldset>
													<label><i18n:message key="unidade.cep" /></label>
													<div>
														<input type='text' id="cep" name="cep"  class="Description[unidade.cep]" maxlength="8"/>
													</div>
												</fieldset>
										</div>
										
										</div>
										<div class="col_100" id='divListaContratos'>
											<fieldset id='fldListaContratos'>
											</fieldset>
										</div> 	
										<div class="columns clearfix">
											<%if(controleAccUnidade.trim().equalsIgnoreCase("S")){%>
											<div class="col_100" style="padding-left: 20px; padding-top: 10px;">
													<button id="addServico" type='button' name='addServico' class="light">
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/adcionar.png">
													<span><i18n:message key="servico.servico"/></span>
													</button>
											</div>
											<%}%>
										</div>
										<br>
										<div id="gridServicos" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaServico" style="width: 100%">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 50%;"><i18n:message key="servico.servico"/></th>
													<th style="width: 50%;"><i18n:message key="citcorpore.comum.descricao"/></th>
												</tr>
											</table>
										</div>
										
									</div>
									<div  class="col_100">
											<fieldset>
												<label style="cursor: pointer;" title="Clique para adicionar uma Localidade ao cadastro de unidade"><i18n:message key="localidadeFisica.localidadeFisica" />
												<img id="cadastroLocalidade" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.cadastrorapido" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/page_white_add.png" onclick="popup.abrePopup('localidade', '')">
												<img id="addLocalidade"  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png">
												</label>
												
												<div id="gridLocalidade">
													<table class="table" id="tabelaLocalidade" style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 99%;"><i18n:message key="localidadeFisica.nomeLocalidade"/></th>
														</tr>
													</table>
												</div>
											</fieldset>
									</div>
									<br><br>
									<div style="overflow-y:hidden !important" id="popupCadastroRapido">
										<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"> </iframe>
									</div>
									<div class="col_100">
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnUpDate' class="light"
										onclick='update();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" />
										</span>
									</button>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_UNIDADE' id='LOOKUP_UNIDADE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>	
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<div  id="POPUP_SERVICO" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style='width: 95% !important;'>
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px;">
							<form name='formPesquisaServico' style='width: 100% !important;'>
								<cit:findField formName='formPesquisaServico'
	 							lockupName='LOOKUP_SERVICO_ATIVOS'
	 							id='LOOKUP_SERVICO' top='0' left='0' len='550' heigth='400'
	 							javascriptCode='true'
	 							htmlCode='true' />
							</form>
						</div>
					</div>
				</div> 
			</div>
		</div>
		<div id="POPUP_LOCALIDADE" title="<i18n:message key="citcorpore.comum.pesquisa"/>">
			<div class="box grid_16 tabs" style='width: 560px !important;' >
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section"  >
							<form name='formPesquisaLocalidade' style='width: 530px !important;' >
								<cit:findField formName='formPesquisaLocalidade' lockupName='LOOKUP_NOVALOCALIDADE' id='LOOKUP_LOCALIDADE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
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
