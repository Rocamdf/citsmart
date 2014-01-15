<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
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
%>

<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" />
</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" charset="ISO-8859-1"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
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
	
		function TabelaExcecoes(_idTabelaExcecoes){
			var idTabelaExcecoes = _idTabelaExcecoes;
			var excecoes = [];
			var tabela = null;
	
			this.getExcecoes = function(){
				return excecoes;
			}
	
			this.setExcecoes = function(novasExcecoes){
				excecoes = novasExcecoes;
				montaTabela();
			}
			
			this.adicionarExcecao = function(excecao){
				excecoes.push(excecao);
				montaTabela();
			}
	
			this.limpaLista = function(){
				excecoes.length = 0;
				excecoes = null;
				excecoes = [];
				limpaTabela();
			}
			
			var limpaTabela = function(){
				while (getTabela().rows.length > 1){
					getTabela().deleteRow(1); 
				} 
			}
			
			var montaTabela = function(){
				limpaTabela();
				var excecao;
				for(var i = 0; i < excecoes.length; i++){
					excecao = excecoes[i];
					var linha = getTabela().insertRow(1);
					
					var celTipo = linha.insertCell(0);
					var celDataInicio = linha.insertCell(1);
					var celDataTermino = linha.insertCell(2);
					var celJornada = linha.insertCell(3);
					var celExcluir = linha.insertCell(4);
		
					celTipo.innerHTML = excecao.getTipo();
					celDataInicio.innerHTML = excecao.getDataInicio();
					celDataTermino.innerHTML = excecao.getDataTermino();
					celJornada.innerHTML = excecao.getDescJornada();
	
					var botaoExcluir = getBotao();
					botaoExcluir.setAttribute("id", i);
					celExcluir.appendChild(botaoExcluir);
					botaoExcluir.setAttribute("onclick", "tabExcecoes.removeExcecao(" + i + ")");
				}
			}
	
			this.removeExcecao = function(indice){
				removeExcecaoDaLista(indice);
				montaTabela();
			}
		
			/**
			 * Remove item e organiza lista
			 */
			var removeExcecaoDaLista = function(indice){
				excecoes[indice] = null;
				var novaLista = [];
				for(var i = 0 ; i < excecoes.length; i++){
					if(excecoes[i] != null){
						novaLista.push(excecoes[i]);
					}
				}
				excecoes = novaLista;
			}
	
			var getBotao = function(){
				var botao = new Image();
				botao.setAttribute("style", "cursor: pointer;");
				botao.src = "<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png";
				return botao;
			}
	
			var getTabela = function(){
				if(tabela == null){
					tabela = document.getElementById(idTabelaExcecoes);
				}
				return tabela;
			}
		}
	
		function Excecao(_tipo, _dataInicio, _dataFim, _descJornada, _idJornada){
			this.tipo = _tipo;
			this.dataInicio = _dataInicio;
			this.dataTermino = _dataFim;
			this.idJornada = _idJornada;
			this.descJornada = _descJornada;
			this.idExcecaoCalendario;
			
			this.getTipo = function(){
				return this.tipo;
			}
	
			this.getDataInicio = function(){
				return this.dataInicio;
			}
	
			this.getDataTermino = function(){
				return this.dataTermino;
			}
	
			this.getDescJornada = function(){
				return this.descJornada;
			}
	
			this.getIdJornada = function(){
				return this.idJornada;
			}		
		}
	
		var tabExcecoes;
		addEvent(window, "load", load, false);
		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
			
			tabExcecoes = new TabelaExcecoes("tabelaExcecoes");
			$("#divAdicionarExcecao").dialog({
				//title : "Adicionar Exceção",
				title : i18n_message("calendario.title"),
				width : 500,
				height : 300,
				modal : true,
				autoOpen : false,
				resizable : true,
				show : "fade",
				hide : "fade"
			});
			
		}
	<!--			$('.tabs').tabs('select', 0);-->
			//configuraçães iniciais da popup
			function salvar(){
				//salvar excecoes
				document.form.listaExecoesSerializada.value = ObjectUtils.serializeObjects(tabExcecoes.getExcecoes());
				document.form.save();
			}	
	
			function restaurarTabelaExcecoes(serializado){
				var lista = ObjectUtils.deserializeCollectionFromString(serializado);
				if(lista == null || lista == ""){
					return;
				}
				tabExcecoes.limpaLista();
				var novaLista = [];
				for(var i = 0 ; i < lista.length; i++){
					var opcoes = document.getElementById("idjornadaexcecao").options;
					var descricaoCalendario = "";
					for (var j = 0; j < opcoes.length; j++) {
						if (opcoes[j].value == lista[i].idJornada) {
							descricaoCalendario = opcoes[j].text;
							if (descricaoCalendario == "undefined"){
								descricaoCalendario = "";
							}
							break;
						}
					}
					var excecao = new Excecao(lista[i].tipo, lista[i].dataInicio, lista[i].dataTermino, descricaoCalendario, lista[i].idJornada);
					excecao.idExcecaoCalendario = lista[i].idExcecaoCalendario;
					novaLista[i] = excecao; 
				}
				tabExcecoes.setExcecoes(novaLista);
			}
	
			function LOOKUP_CALENDARIO_select(id, desc) {
				document.form.restore({idCalendario : id});
			}
	
			function validaData(dataInicial, dataFinal){
				if(dataInicial.value == "" || dataFinal.value == "" ){
					alert(i18n_message("calendario.preenchaDatas"));
					return false;
				}
				
				if(comparaComDataAtual(dataInicial) < 0 || comparaComDataAtual(dataFinal) < 0){
					alert(i18n_message("calendario.dataInferiorDataHoje"));
					return false;
				}
	
				if(!verificaData(dataInicial.value, dataFinal.value)){
					return false;
				} 
				return true;						
			}
			
			/**
			* @author rodrigo.oliveira
			*/
			function verificaData(dataInicio, dataFim) {
				
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
			
			function adicionarExcecao(){
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataTermino").value;
				
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				 	document.getElementById("dataInicio").value = '';
				 	return false;
				}
				
				if(DateTimeUtil.isValidDate(dataFim) == false){
					 alert(i18n_message("citcorpore.comum.validacao.datafim"));
					 document.getElementById("dataTermino").value = '';
					return false;					
				}
				
				if(validaData(document.getElementById("dataInicio"), document.getElementById("dataTermino"))){
					var select = document.getElementById("idjornadaexcecao");
					
					var tipoExcecao = "F"
					var jornadaText = select.options[select.selectedIndex].text;
					var jornadaValue = select.options[select.selectedIndex].value;
					
					if ($("#tipoExcecaoTrabalho").is(':checked')){
						tipoExcecao = "T";
					} else{
						jornadaText = "";
						jornadaValue = null;
					}
					var excecao = new Excecao(tipoExcecao, document.getElementById("dataInicio").value, document.getElementById("dataTermino").value, jornadaText, jornadaValue);
					
					tabExcecoes.adicionarExcecao(excecao);
					
					document.formAdicionarExcecao.reset();
					
					$("#divAdicionarExcecao").dialog("close");
				}
			}
	
			function clearForm(){
				tabExcecoes.limpaLista();
				document.form.clear();
				setConsideraFeriados();
			}
	
			document.form.onClear = function(){
				window.setTimeout(setConsideraFeriados, 500);
			}
			function setConsideraFeriados(){
				document.form.consideraFeriados[0].checked = true;
			}
			
			function abrirAdicionarExcecao(){
				$("#divJornada").hide();
				$('#divAdicionarExcecao').dialog('open');
			}
			
			function hideExcecao(){
				$("#divJornada").hide();
			}
			
			function showExcecao(){
				$("#divJornada").show();
			}
			
			function excluir() {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("excluir");
				}
			}

	</script>

</head>
<body>
	<div id="wrapper">

		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="calendario.calendario" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><i18n:message key="calendario.cadastro" /></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top"><i18n:message key="calendario.pesquisa" /></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/calendario/calendario'>
								<input type="hidden" id="listaExecoesSerializada" name="listaExecoesSerializada" />
								<input type="hidden" id="idCalendario" name="idCalendario" />
								<div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="calendario.descricao" /></label>
											<div>
												<input type='text' name="descricao" maxlength="70"
													   class="Valid[Required] Description[calendario.descricao]" />
											</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset style="height: 60px;">
											<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="calendario.consideraFeriados" />:</label>
											<div style="margin-top: 5px;">
												<input type="radio" checked="checked" name="consideraFeriados" class="Description[calendario.consideraFeriados]" value="S"/><i18n:message key="citcorpore.comum.sim" />
												<input type="radio" name="consideraFeriados" class="Description[calendario.consideraFeriados]" value="N"/><i18n:message key="citcorpore.comum.nao" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<div>
												<table class="table" width="50%">
													<tr>
														<th colspan="2"><i18n:message key="calendario.jornadas" /></th>
													</tr>
													<tr>
														<td><i18n:message key="calendario.segunda" /></td>
														<td>  <select name="idJornadaSeg"></select>  </td>
													</tr>
													<tr>
														<td><i18n:message key="calendario.terca" /></td>
														<td>  <select name="idJornadaTer"></select>  </td>
													</tr>
													<tr>
														<td><i18n:message key="calendario.quarta" /></td>
														<td>  <select name="idJornadaQua"></select>  </td>
													</tr>
													<tr>
														<td><i18n:message key="calendario.quinta" /></td>
														<td>  <select name="idJornadaQui"></select>  </td>
													</tr>
													<tr>
														<td><i18n:message key="calendario.sexta" /></td>
														<td>  <select name="idJornadaSex"></select>  </td>
													</tr>
													<tr>
														<td><i18n:message key="calendario.sabado" /></td>
														<td>  <select name="idJornadaSab"></select>  </td>
													</tr>
													<tr>
														<td><i18n:message key="calendario.domingo" /></td>
														<td>  <select name="idJornadaDom"></select>  </td>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								<div class="columns clearfix">
									<div class="col_100">
										<fieldset>
											<label>
												<i18n:message key="calendario.excecoes" />
												<img alt="" onclick="abrirAdicionarExcecao()" 
													 src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
											</label>
											<div>
												<table class="table" width="50%" id="tabelaExcecoes" style="margin-top: 5px;">
													<tr>
														<th><i18n:message key="citcorpore.comum.tipo" /></th>
														<th><i18n:message key="citcorpore.comum.datainicio" /></th>
														<th><i18n:message key="citcorpore.comum.datafim" /></th>
														<th><i18n:message key="calendario.jornada" /></th>
														<th><i18n:message key="calendario.excluir" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
							</div>
							<div style="margin-top: 10px;">
								<button type='button' name='btnGravar' class="light"
										onclick='salvar()'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='clearForm();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" /></span>
								</button>	
							</div>
						</div>
					</form>			
					</div>
				</div> <!--  end tab -->
				<div id="tabs-2" class="block">
					<div class="section">
						<i18n:message key="citcorpore.comum.pesquisa"/>
						<form name='formPesquisa'>
							<cit:findField formName='formPesquisa' lockupName='LOOKUP_CALENDARIO' id='LOOKUP_CALENDARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>	
				<!-- ## FIM - AREA DA APLICACAO ## -->
			</div>
		</div>
	</div>
	<!-- Fim da Pagina de Conteudo -->

		<div id="divAdicionarExcecao" style="left: 10px; top: 10px; padding: 7px;">
			<form name="formAdicionarExcecao" action="">
				<div class="columns clearfix">
					<div class="col_100">
						<input type="radio" checked="checked" name="tipoExcecao" id="tipoExcecaoFolga" class="Description[Considera Feriados]" value="F" onclick="hideExcecao()"/><i18n:message key="calendario.folga"/> 
						<input type="radio" name="tipoExcecao" id="tipoExcecaoTrabalho" class="Description[Considera Feriados]" value="T" onclick="showExcecao()"/><i18n:message key="calendario.trabalho"/> 
					</div>
				</div>
				<div class="columns clearfix">
					<div class="" style="display: block; float: left; width: 230px; margin-right: 10px;">
						<label><i18n:message key="citcorpore.comum.datainicio"/>:</label>
						<div>
							<input type='text' id="dataInicio" name="dataInicio" maxlength="10" size="10"
								class="Valid[Required,Date] Description[citcorpore.comum.datainicio] Format[Date] datepicker" />
						</div>
					</div>
					<div class=""  style="display: block; float: left; width: 230px;">
						<label><i18n:message key="citcorpore.comum.datafim"/>:</label>
						<div>
							<input type='text' id="dataTermino" name="dataTermino" maxlength="10" size="10"
								class="Valid[Required,Date] Description[citcorpore.comum.datafim] Format[Date] datepicker" />
						</div>
					</div>
					<div class="c"  style="display: block; float: left; width: 470px; margin-bottom: 10px;" id="divJornada">
						<label><i18n:message key="calendario.jornada"/>:</label>
						<div>
							<select id="idjornadaexcecao"></select>
						</div>
					</div>
				</div>
				
				<button type='button' name='btnGravar' class="light" onclick='adicionarExcecao()'>
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span>
						<i18n:message key="citcorpore.comum.gravar"/>
					</span>
				</button>
			</form>
		</div>
	</div>
		
	<%@include file="/include/footer.jsp"%>
</body>
</html>
