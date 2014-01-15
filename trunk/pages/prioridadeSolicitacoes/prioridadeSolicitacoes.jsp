<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>

<!doctype html public "">
<html>
	<head>
	<%
	    response.setHeader("Cache-Control", "no-cache");
	    response.setHeader("Pragma", "no-cache");
	    response.setDateHeader("Expires", -1);
	    String iframe = "";
	    iframe = request.getParameter("iframe");
	%>
	
	<title><i18n:message key="citcorpore.comum.title" /></title>
	
	<%@include file="/include/security/security.jsp"%>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	
	<script charset="ISO-8859-1"  type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	
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
				
	</style>
	
	<script type="text/javascript">
		var popup;
		addEvent(window, "load", load, false);
		function load() {
			popup = new PopupManager(800, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}
					
		function update() {
			if (document.getElementById('idUnidade').value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
					deleteAllRows();
				}
			}
		}
				
		function matrizPrioridade(){
			document.form.fireEvent("cadastrarMatriz");
		}
				
		function exibeCadastroMatriz(){
			document.getElementById('divAdicionarMatriz').style.display = 'block';
		}
		
		function limpar() {
			deleteAllRows();
			document.getElementById('gridMatrizPrioridade').style.display = 'none';
			document.form.clear();
		}
	
		function exibeGrid() {
			document.getElementById('gridMatrizPrioridade').style.display = 'block';
		}
		function ocultaGrid() {
			document.getElementById('gridMatrizPrioridade').style.display = 'none';
		}
		
		var countMatriz = 0;
		
		function insereRow(siglaImpacto, nivelImpacto, siglaUrgencia, nivelUrgencia, valor) {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var lastRow = tabela.rows.length;
			
			var row = tabela.insertRow(lastRow);
			countMatriz++;
			
			var coluna = row.insertCell(0);
			coluna.innerHTML = '<input type="hidden" id="idMatrizPrioridade' + countMatriz + '" name="idMatrizPrioridade"/><img id="imgExcluiMatrizPrioridade' + countMatriz + '" style="cursor: pointer;" title="<i18n:message key="citcorpore.comum.excluir"/>" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaMatrizPrioridade\', this.parentNode.parentNode.rowIndex);">';
			
			coluna = row.insertCell(1);
			coluna.innerHTML = '<input type="hidden" id="siglaImpacto'+countMatriz+'" value="'+siglaImpacto+'" style="display: none;"><input type="text" id="nivelImpacto' + countMatriz + '" name="nivelImpacto" value="' + nivelImpacto + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
			
			coluna = row.insertCell(2);
			coluna.innerHTML = '<input type="hidden" id="siglaUrgencia'+countMatriz+'" value="'+siglaUrgencia+'" style="display: none;"><input type="text" id="nivelUrgencia' + countMatriz + '" name="nivelUrgencia" value="' + nivelUrgencia + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
			
			coluna = row.insertCell(3);
			coluna.innerHTML = '<input type="text" id="valorPrioridade' + countMatriz + '" name="valorPrioridade" value="' + valor + '" style="width: 100%; border: 0 none;" readonly="readonly" />';
			
		}
						
		function removeLinhaTabela(idTabela, rowIndex) {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
				document.form.fireEvent("excluirMatrizPrioridade");
			}
		}

		function deleteAllRows() {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
				
			}
			ocultaGrid();
		}
		
		var seqSelecionada = '';
		var aux = '';
		
		/* Início do código de Níveis de Impacto e Urgência */
		
		var cont = 0;
	
		function addImpacto() {
			
			var tabela = document.getElementById('tabelaImpacto');
			var lastRow = tabela.rows.length;
			
			var row = tabela.insertRow(lastRow);
			cont++;
			
			var coluna = row.insertCell(0);
			coluna.innerHTML = '<span><i18n:message key="prioridade.nivelImpacto" />  </span>';
			
			coluna = row.insertCell(1);
			coluna.innerHTML = '<input type="TEXT" class="text" name="NIVELIMPACTO" size="50" maxlength="50"/>';
			
			coluna = row.insertCell(2);
			coluna.innerHTML = '<span style="padding-left: 5px; padding-right: 15px;"><i18n:message key="prioridade.sigla" />  </span>';
			
			coluna = row.insertCell(3);
			coluna.innerHTML = '<input type="TEXT" class="text" name="SIGLAIMPACTO" size="2" maxlength="2"/>';
						
			coluna = row.insertCell(4);
			coluna.innerHTML = '<img title="Remover Impacto" src="/citsmart/imagens/delete.png" onclick="removeNivel(\'tabelaImpacto\', this.parentNode.parentNode.rowIndex);" border="0" style="cursor:pointer">';
			
			coluna = row.insertCell(5);
			coluna.innerHTML = '<img title="Adicionar nível do Impacto" src="/citsmart/imagens/add.png" onclick="addImpacto();" border="0" style="cursor:pointer">';
			
		}
		
		var cont = 0;
		
		function addUrgencia() {
			
			var tabela = document.getElementById('tabelaUrgencia');
			var lastRow = tabela.rows.length;
			
			var row = tabela.insertRow(lastRow);
			cont++;
			
			var coluna = row.insertCell(0);
			coluna.innerHTML = '<span><i18n:message key="prioridade.nivelUrgencia" />  </span>';
			
			coluna = row.insertCell(1);
			coluna.innerHTML = '<input type="TEXT" class="text" name="NIVELURGENCIA" size="50" maxlength="50"/>';
			
			coluna = row.insertCell(2);
			coluna.innerHTML = '<span style="padding-left: 5px; padding-right: 15px;"><i18n:message key="prioridade.sigla" />  </span>';
			
			coluna = row.insertCell(3);
			coluna.innerHTML = '<input type="TEXT" class="text" name="SIGLAURGENCIA" size="2" maxlength="2"/>';
			
			coluna = row.insertCell(4);
			coluna.innerHTML = '<img title="Remover quantiade" src="/citsmart/imagens/delete.png" onclick="removeNivel(\'tabelaUrgencia\', this.parentNode.parentNode.rowIndex);" border="0" style="cursor:pointer">';
			
			coluna = row.insertCell(5);
			coluna.innerHTML = '<img title="Adicionar nível da Urgência" src="/citsmart/imagens/add.png" onclick="addUrgencia();" border="0" style="cursor:pointer">';
			
		}
		
		function removeNivel(idTabela, rowIndex){
			
			if(idTabela == "tabelaImpacto"){
				var nivelImpacto = document.form.NIVELIMPACTO[rowIndex].value
				var resp = percorreTabela(idTabela, nivelImpacto);
				if(!resp){
					alert(i18n_message("prioridade.matrizprioridade.remover"));
					return false;
				}
			}else {
				var nivelUrgencia = document.form.NIVELURGENCIA[rowIndex].value
				var resp = percorreTabela(idTabela, nivelUrgencia);
				if(!resp){
					alert(i18n_message("prioridade.matrizprioridade.remover"));
					return false;
				}
			}
			HTMLUtils.deleteRow(idTabela, rowIndex);
		}
		function removeLinha(idTabela, rowIndex){
			HTMLUtils.deleteRow(idTabela, rowIndex);
		}
		
		function percorreTabela(idTabela, novo){
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;
			var contadorAux = 0;
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idMatrizPrioridade' + i);
				if (!trObj) {
					continue;
				}
				if(idTabela == "tabelaImpacto"){
					var resp = document.getElementById('nivelImpacto' + i).value;
					if(novo == resp){
						return false;
					}
				}else {
					var resp = document.getElementById('nivelUrgencia' + i).value;
					if(novo == resp){
						return false;
					}
				}
			}
			return true;
		}
		
		/* Código da Matriz de Prioridade */
		
		function addLinhaMatriz(){
			var idImpacto = document.getElementById('IDIMPACTOSELECT').value;
			var nivelImpacto = document.getElementById('IDIMPACTOSELECT').options[document.getElementById('IDIMPACTOSELECT').selectedIndex].textContent;
			
			var idUrgencia = document.getElementById('IDURGENCIASELECT').value;
			var nivelUrgencia = document.getElementById('IDURGENCIASELECT').options[document.getElementById('IDURGENCIASELECT').selectedIndex].textContent;
			
			var valor = document.getElementById('VALORPRIORIDADE').value;
			
			if(!verificaRegistrosMatriz(idImpacto, idUrgencia, valor)){
				window.alert(i18n_message("prioridadesolicitacao.registrosiguais"));
				return;
			}
			
			//geber.costa
			//validação para ser inserido na matriz apenas as prioridades com impacto, urgencia e valor
			if(idImpacto <=0 || idUrgencia <= 0 || valor <=0){
				window.alert(i18n_message("prioridade.matrizprioridade.info"));
				return;
			}
				
			exibeGrid();
			
			limpaFormMatriz();
			
			insereRow(idImpacto, nivelImpacto, idUrgencia, nivelUrgencia, valor);
		}
		
		function limpaFormMatriz(){
			document.getElementById('IDIMPACTOSELECT').selectedIndex = 0;
			document.getElementById('IDURGENCIASELECT').selectedIndex = 0;
			document.getElementById('VALORPRIORIDADE').value = "";
		}
		
		function gravarMatriz() {
			serializaMatriz();
			document.form.fireEvent("saveMatrizPrioridade");
		}
		
		function serializaMatriz() {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var matrizPrioridade = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idMatrizPrioridade' + i);
				if (!trObj) {
					continue;
				}
				matrizPrioridade[contadorAux] = getMatrizPrioridade(i);
				contadorAux = contadorAux + 1;
			}
			
			var matrizSerelializada = ObjectUtils.serializeObjects(matrizPrioridade);
			document.form.MATRIZPRIORIDADESERELIALIZADO.value = matrizSerelializada;
			
			return true;
		}
		
		// Método que verifica se ja existe um registro com os mesmos valores
		
		function verificaRegistrosMatriz(imp, urg , prio) {
			var tabela = document.getElementById('tabelaMatrizPrioridade');
			var count = tabela.rows.length;
			// 
			var impacto;
			var urgencia;
			var prioridade;
			var verificacao = true;
			//
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idMatrizPrioridade' + i);
				if (!trObj) {
					continue;
				}
				
				impacto = document.getElementById('siglaImpacto' + i).value;
				urgencia = document.getElementById('siglaUrgencia' + i).value;
				prioridade = document.getElementById('valorPrioridade' + i).value;
				
				if(impacto == imp && urgencia == urg && prioridade == prio){
					verificacao = false;
					break;
				}
				
			}
			
			return verificacao;
		}
		

		function getMatrizPrioridade(seq) {
			var MatrizPrioridadeDTO = new CIT_MatrizPrioridadeDTO();
			MatrizPrioridadeDTO.sequencia = seq;
			MatrizPrioridadeDTO.siglaImpacto = document.getElementById('siglaImpacto' + seq).value;
			MatrizPrioridadeDTO.siglaUrgencia = document.getElementById('siglaUrgencia' + seq).value;
			MatrizPrioridadeDTO.valorPrioridade = document.getElementById('valorPrioridade' + seq).value;
			return MatrizPrioridadeDTO;
		}
		
				
		/* Fim do código da Matriz de Prioridade */
		
		/* Carrega Valores de Impacto e Urgência */
		
		var flag = false;
	
		function carregaImpacto(){
			var objres = document.form.NIVELIMPACTOSERELIALIZADO.value;
			var objResultado = new Array();
			objResultado = ObjectUtils.deserializeCollectionFromString(objres);
			if(objResultado != null && objResultado.length != 0 && flag == false){
				var size = objResultado.length;
				if(size>1){
					for(var i=0;i<size-1;i++){
						addImpacto();
					}
					for(var i=0; i<size;i++){
						var ImpactoDTO = new CIT_ImpactoDTO();
						ImpactoDTO = objResultado[i];
						document.form.NIVELIMPACTO[i].value = ImpactoDTO.nivelImpacto;
						document.form.SIGLAIMPACTO[i].value = ImpactoDTO.siglaImpacto;
					}
					 flag = true;
				}else{
					var ImpactoDTO = new CIT_ImpactoDTO();
					ImpactoDTO = objResultado[0];
					document.form.NIVELIMPACTO.value = ImpactoDTO.nivelImpacto;
					document.form.SIGLAIMPACTO.value = ImpactoDTO.siglaImpacto;
				}
			}
		}
		
		var flag2 = false;
		
		function carregaUrgencia(){
			var objres = document.form.NIVELURGENCIASERELIALIZADO.value;
			var objResultado = new Array();
			objResultado = ObjectUtils.deserializeCollectionFromString(objres);
			if(objResultado != null && objResultado.length != 0 && flag2 == false){
				var size = objResultado.length;
				if(size>1){
					for(var i=0;i<size-1;i++){
						addUrgencia();
					}
					for(var i=0; i<size;i++){
						var UrgenciaDTO = new CIT_UrgenciaDTO();
						UrgenciaDTO = objResultado[i];
						document.form.NIVELURGENCIA[i].value = UrgenciaDTO.nivelUrgencia;
						document.form.SIGLAURGENCIA[i].value = UrgenciaDTO.siglaUrgencia;
					}
					flag2 = true;
				}else{
					var UrgenciaDTO = new CIT_UrgenciaDTO();
					UrgenciaDTO = objResultado[0];
					document.form.NIVELURGENCIA.value = UrgenciaDTO.nivelUrgencia;
					document.form.SIGLAURGENCIA.value = UrgenciaDTO.siglaUrgencia;
				}
			}
		}
		
		/**
		 * Gravando informações sobre de Níveis de Impacto e Urgência
		 */
		 
		 /* Impacto */
		 
		function gravarImpacto() {
			serializaImpacto();
			document.form.fireEvent("saveImpacto");
		}
						
		function serializaImpacto() {
			var contadorAux = 0;
			var objNivelImpacto = new Array();
			if(document.form.NIVELIMPACTO.length == undefined){
				objNivelImpacto[contadorAux] = getNivelImpactoUnicaLinha();
				contadorAux = contadorAux + 1;
			}else{
				var count = document.form.NIVELIMPACTO.length;
				for ( var i = 0; i < count; i++) {
					objNivelImpacto[contadorAux] = getNivelImpacto(i);
					contadorAux = contadorAux + 1;
				}
			}
			var nivelImpactoSerializado = ObjectUtils.serializeObjects(objNivelImpacto);
			document.form.NIVELIMPACTOSERELIALIZADO.value = nivelImpactoSerializado;
			return true;
		}
		
		function getNivelImpactoUnicaLinha(){
			var ImpactoDTO = new CIT_ImpactoDTO();
			ImpactoDTO.nivelImpacto = document.form.NIVELIMPACTO.value;
			ImpactoDTO.siglaImpacto = document.form.SIGLAIMPACTO.value;
	 		return ImpactoDTO;
		}
		
		function getNivelImpacto(seq) {
			var ImpactoDTO = new CIT_ImpactoDTO();
			ImpactoDTO.sequence = seq;
			ImpactoDTO.nivelImpacto = document.form.NIVELIMPACTO[seq].value;
			ImpactoDTO.siglaImpacto = document.form.SIGLAIMPACTO[seq].value;
			return ImpactoDTO;
		}
		
		/* Urgência */
		
		function gravarUrgencia() {
			serializaUrgencia();
			document.form.fireEvent("saveUrgencia");
		}
		
		function serializaUrgencia() {
			var contadorAux = 0;
			var objNivelUrgencia = new Array();
			if(document.form.NIVELURGENCIA.length == undefined){
				objNivelUrgencia[contadorAux] = getNivelUrgenciaUnicaLinha();
				contadorAux = contadorAux + 1;
			}else{
				var count = document.form.NIVELURGENCIA.length;
				for ( var i = 0; i < count; i++) {
					objNivelUrgencia[contadorAux] = getNivelUrgencia(i);
					contadorAux = contadorAux + 1;
				}
			}
			var nivelUrgenciaSerializado = ObjectUtils.serializeObjects(objNivelUrgencia);
			document.form.NIVELURGENCIASERELIALIZADO.value = nivelUrgenciaSerializado;
			return true;
		}
		
		function getNivelUrgenciaUnicaLinha(){
			var UrgenciaDTO = new CIT_UrgenciaDTO();
			UrgenciaDTO.nivelUrgencia = document.form.NIVELURGENCIA.value;
			UrgenciaDTO.siglaUrgencia = document.form.SIGLAURGENCIA.value;
	 		return UrgenciaDTO;
		}
		
		function getNivelUrgencia(seq) {
			var UrgenciaDTO = new CIT_UrgenciaDTO();
			UrgenciaDTO.sequence = seq;
			UrgenciaDTO.nivelUrgencia = document.form.NIVELURGENCIA[seq].value;
			UrgenciaDTO.siglaUrgencia = document.form.SIGLAURGENCIA[seq].value;
			return UrgenciaDTO;
		}
		
		/* Fim do código de Níveis de Impacto e Urgência */
		
	</script>
	</head>
	<body>
		<script type="text/javascript" src="../../cit/objects/ImpactoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/UrgenciaDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/MatrizPrioridadeDTO.js"></script>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<i18n:message key="prioridade.titulo" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><i18n:message key="prioridade.cadastro" /></a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div class="block">
							<div class="parametros">
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/prioridadeSolicitacoes/prioridadeSolicitacoes'>
									<div class="columns clearfix">
										<div>
											<div class="col_50">
												<fieldset style="padding-top: 15px; border: none !important;">
													<label><i18n:message key="prioridade.impacto" /></label>
													<div>
														<br>
														<div id="divNivelImpacto" style="padding-top:10px">
															<input type='hidden' id='NIVELIMPACTOSERELIALIZADO' name='NIVELIMPACTOSERELIALIZADO'/>
															<table id="tabelaImpacto" >
																<tr>
																	<td>
																		<span style=" padding-right: 15px;"><i18n:message key="prioridade.nivelImpacto" />  </span>
																	</td>
																	<td>
																		<input type='TEXT' name='NIVELIMPACTO' size='50' maxlength='50'/>
																	</td>
																	<td>
																		<span style="padding-left: 5px; padding-right: 15px;"><i18n:message key="prioridade.sigla" />  </span>
																	</td>
																	<td>
																		<input type='TEXT' name='SIGLAIMPACTO' size='2' maxlength='2'/>
																	</td>
																	<td>
																		<img title="Adicionar nível do Impacto" src="/citsmart/imagens/add.png" onclick="addImpacto();" border="0" style="cursor:pointer">
																	</td>
																</tr>
															</table>
															<button type='button' name='btnGravar' class="light" onclick='gravarImpacto();'>
																<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																<span><i18n:message key="prioridade.gravarImpacto"/></span>
															</button>
														</div>
													</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset style="padding-top: 15px; border: none !important;">
													<label><i18n:message key="prioridade.urgencia" /></label>
													<div>
														<br>
														<div id="divNivelUrgencia" style="padding-top:10px">
															<input type='hidden' id='NIVELURGENCIASERELIALIZADO' name='NIVELURGENCIASERELIALIZADO'/>
															<table id="tabelaUrgencia" >
																<tr>
																	<td>
																		<span style=" padding-right: 15px;"><i18n:message key="prioridade.nivelUrgencia" />  </span>
																	</td>
																	<td>
																		<input type='TEXT' name='NIVELURGENCIA' size='50' maxlength='50'/>
																	</td>
																	<td>
																		<span style="padding-left: 5px; padding-right: 15px;"><i18n:message key="prioridade.sigla" />  </span>
																	</td>
																	<td>
																		<input type='TEXT' name='SIGLAURGENCIA' size='2' maxlength='2'/>
																	</td>
																	<td>
																		<img title="Adicionar nível da Urgência" src="/citsmart/imagens/add.png" onclick="addUrgencia();" border="0" style="cursor:pointer">
																	</td>
																</tr>
															</table>
															<button type='button' name='btnGravar' class="light" onclick='gravarUrgencia();'>
																<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																<span><i18n:message key="prioridade.gravarUrgencia"/></span>
															</button>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
									<div class="col_100">
										<div class="columns clearfix">
											<div class="col_100" style="padding-left: 20px; padding-top: 10px;">
													<button id="addMatrizPrioridade" type="button" name="addMatrizPrioridade" class="light" onclick="matrizPrioridade()">
														<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/adcionar.png">
														<span><i18n:message key="prioridade.matrizprioridade.cadastro"/></span>
													</button>
											</div>
										</div>
									</div>
									<div id="divAdicionarMatriz" style="left: 10px; top: 10px; padding: 7px; display: none;">
										<div class="columns clearfix">
											<div>
												<fieldset style="padding-top: 15px; border: none !important;">
													<label><i18n:message key="prioridade.matrizprioridade.info" /></label>
													<div>
														<br>
														<div id="divNivelUrgencia" style="padding-top:10px">
															<input type='hidden' id='MATRIZPRIORIDADESERELIALIZADO' name='MATRIZPRIORIDADESERELIALIZADO'/>
															<table id="tabelaCadastroMatriz" >
																<tr>
																	<td>
																		<span style="padding-right: 15px;"><i18n:message key="prioridade.nivelImpacto" />  </span>
																	</td>
																	<td>
																		<div><select id="IDIMPACTOSELECT" name="IDIMPACTOSELECT"></select></div>
																	</td>
																	<td>
																		<span style="padding-right: 15px; padding-left: 15px;"><i18n:message key="prioridade.nivelUrgencia" />  </span>
																	</td>
																	<td>
																		<div><select id="IDURGENCIASELECT" name="IDURGENCIASELECT"></select></div>
																	</td>
																	<td>
																		<span style="padding-right: 15px; padding-left: 15px;"><i18n:message key="prioridade.matrizpriopridade.valor" />  </span>
																	</td>
																	<td>
																		<input type='TEXT' name='VALORPRIORIDADE' size='5' maxlength='5' class="Format[Numero]"/>
																	</td>
																	<td>
																		<img title="Adicionar situação na Matriz de Prioridade" src="/citsmart/imagens/add.png" onclick="addLinhaMatriz();" border="0" style="cursor:pointer;">
																	</td>
																</tr>
															</table>
															<div id="gridMatrizPrioridade" style="display: none;">
																<table class="table" id="tabelaMatrizPrioridade" style="width: 50%">
																	<tr>
																		<th style="width: 16px !important;"></th>
																		<th style="width: 30%;"><i18n:message key="prioridade.nivelImpacto"/></th>
																		<th style="width: 30%;"><i18n:message key="prioridade.nivelUrgencia"/></th>
																		<th style="width: 30%;"><i18n:message key="prioridade.matrizpriopridade.valor"/></th>
																	</tr>
																</table>
															</div>
															<button type='button' name='btnGravar' class="light" onclick='gravarMatriz()'>
																<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
																<span><i18n:message key="prioridade.matrizprioridade.gravar"/></span>
															</button>
														</div>
													</div>
												</fieldset>
											</div>
										</div>
									</div>
									
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
