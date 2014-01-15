<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.GrupoDTO"%>
<!doctype html public>
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
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/informacao.css">
<style type="text/css">
.table {
	border-left: 1px solid #ddd;
	width: 100%;
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

textarea#txtJustificacaoFalha {
	border: 1px solid #d0d0d0;
	background-color: white;
}

thead {
	font-size: large;
	font-style: normal;
	font-weight: bold;
}

table#tabelaCabecalhoJustificacaoFalhas {
	border: black solid 1px;
}

table#tabelaCabecalhoJustificacaoFalhas tr td {
	padding: 10px;
	margin: 10px;
	border: black solid 1px;
}

table#tabelaJustificacao {
	border: black solid 1px;
	width: 100%;
}

table#tabelaJustificacao tr td {
	padding: 10px;
	margin: 10px;
	border: black solid 1px;
	width: 25%;
}

table#tabelaFalhas tr td {
	padding: 10px;
	margin: 10px;
	background: #ccc;
}

table#tabelaFalhas thead {
	font-size: large;
	font-family: fantasy;
}

/* table#tabelaFalhas tr.dif td { */
/* 	background: #eee; /* Linhas com fundo cinza */
*
/
/* } */
</style>

<script>
	var listaFalhas = [];
	function JustificacaoEventoHistorico(_ip, _idItemConfiguracao, _idBaseItemConfiguracao,
										 _identificacaoItemConfiguracao, _descricaoTentativa, _idEvento,
										 _nomeGrupo, _nomeUnidade, _idEmpregado, _idHistoricoTentativa){
		this.ip = _ip;
		this.idItemConfiguracao = _idItemConfiguracao;
		this.idBaseItemConfiguracao = _idBaseItemConfiguracao;
		this.identificacaoItemConfiguracao = _identificacaoItemConfiguracao;
		this.descricaoTentativa = _descricaoTentativa;
		this.idEvento = _idEvento;
		this.nomeGrupo = _nomeGrupo;
		this.nomeUnidade = _nomeUnidade;
		this.descricao;
		this.idEmpregado = _idEmpregado;
		this.idHistoricoTentativa = _idHistoricoTentativa;
	}
   
	addEvent(window, "load", load, false);
	function load() {
		$(".popup").dialog({
			width : document.body.offsetWidth/1.3,
			height : window.screen.height/1.3,
			modal : true,
			autoOpen : false,
			resizable : false,
			show : "fade",
			hide : "fade",
// 			beforeClose : limpaFormItemConfiguracao
		});
		
		$("#loading_overlay").hide();
	}
	
	function alimentaListaFalhas(){
		document.getElementById("qtdTotal").innerHTML = "";
		listaFalhas = [];
		var lista = ObjectUtils.deserializeCollectionFromString(document.getElementById("listaItensSerializado").value);
		var table = document.getElementById('tabelaFalhas'); 
		if(lista == ""){
			table.innerHTML = '<i18n:message key="justificacaoFalhas.nenhumResultadoEncontrado"/>';
			return;
		}
		table.innerHTML = "";
		var listaIpsJaPercorrido = [];
		
		var contador = 0;
		for(var i = 0 ; i < lista.length; i++){
// 			alert("ip: " + lista[i].ip + "\n id item config: " + lista[i].idItemConfiguracao + "\n id base item: " + lista[i].idBaseItemConfiguracao
// 					+ "\n identifi: " + lista[i].identificacaoItemConfiguracao + "\n descricao tentativa: " + lista[i].descricaoTentativa
// 					+ "\n id evento: " + lista[i].idEvento + "\n nome grupo: " + lista[i].nomeGrupo + "\n nome unidade: " + lista[i].nomeUnidade);
			
			listaFalhas.push(new JustificacaoEventoHistorico(lista[i].ip, lista[i].idItemConfiguracao, lista[i].idBaseItemConfiguracao,
															 lista[i].identificacaoItemConfiguracao, lista[i].descricaoTentativa, lista[i].idEvento,
															 lista[i].nomeGrupo, lista[i].nomeUnidade, lista[i].idEmpregado, lista[i].idHistoricoTentativa));
// 			alert("empregado: " + lista[i].idEmpregado);
			if(!estaNaLista(listaIpsJaPercorrido, lista[i].ip)){			
					listaIpsJaPercorrido.push(lista[i].ip);
					
					var linkIp = document.createElement('a');
					linkIp.setAttribute('href', '#');
					linkIp.innerText = lista[i].ip + " (" + getQuantidadeItensIp(lista[i].ip, lista) + ")";
					linkIp.setAttribute("id", lista[i].ip);
					linkIp.addEventListener("click", abrePopupJustificacao, false);
					table.appendChild(linkIp);
					table.appendChild(document.createElement("br"));
			}
		}
// 		alert("erros: " + listaFalhas.length);
		document.getElementById("qtdTotal").innerHTML = "Total: " + listaFalhas.length; 
	}
	
	function getQuantidadeItensIp(ip, listaFalhasAux){
		var contador = 0;
		for(var i = 0; i < listaFalhasAux.length; i++){
			if(listaFalhasAux[i].ip == ip){
				contador++;
			}
		}
		return contador;
	}
	
	function estaNaLista(lista, valor){
		for(var i = 0 ; i < lista.length; i++){
			if(lista[i] == valor){
				return true;
			}
		}
		return false;
	}
	
	function abrePopupJustificacao(ev){
		var id = ev.target.getAttribute("id");
// 		var fieldset = document.getElementById('fsJustificacao');		
// 		fieldset.innerHTML = "";
		
		//cabecalho
		for(var i = 0 ; i < listaFalhas.length; i++){
			if(listaFalhas[i].ip == id){
				document.getElementById("lbNomeGrupo").innerText = listaFalhas[i].nomeGrupo;
				document.getElementById("lbNomeUnidade").innerText = listaFalhas[i].nomeUnidade;
				document.getElementById("lbIP").innerText = listaFalhas[i].ip;
				break;
			}
		}
		
		var tabela = document.getElementById("descricaoFalhas");
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}

		for(var i = 0 ; i < listaFalhas.length; i++){
			if(listaFalhas[i].ip == id){
// 				alert("comparação: " + listaFalhas[i].identificacaoItemConfiguracao + " == " + id + " ?" + listaFalhas[i].identificacaoItemConfiguracao == id);
				
				var linha = tabela.insertRow(1);
				var coluna01 = linha.insertCell(0);
				var coluna02 = linha.insertCell(1);
				var coluna03 = linha.insertCell(2);
				var coluna04 = linha.insertCell(3);
				
				var chk = document.createElement("input");

				//descricao falha
				coluna01.innerHTML = listaFalhas[i].descricaoTentativa;
				
				//descricao item
				coluna02.innerHTML = listaFalhas[i].identificacaoItemConfiguracao;

				//descricao tipo
				coluna03.innerHTML = listaFalhas[i].idItemConfiguracao == "" ? '<i18n:message key="justificacaoFalhas.instalacao"/>' : '<i18n:message key="justificacaoFalhas.desinstalacao"/>';

				// checkbox
				chk.setAttribute("type", "checkbox");
				chk.setAttribute("id", "check-" + i);
				coluna04.appendChild(chk);
				

			}
		}
		
		$("#popupJustificacao").dialog("open");
	}
	
	function LOOKUP_EVENTOS_select(id, desc) {
		$("#POPUP_EVENTOS").dialog("close");
		document.form.idEvento.value = id;
		document.form.nomeEvento.value = desc.split("-")[0];		
	}
	
	function LOOKUP_GRUPO_select(id, desc) {
		$("#POPUP_GRUPO_EMPREGADOS").dialog("close");
		document.form.idGrupo.value = id;
		document.form.nomeGrupo.value = desc.split("-")[0];		
	}
	
	function LOOKUP_UNIDADE_select(id, desc) {
		$("#POPUP_UNIDADE_EMPREGADO").dialog("close");
		document.form.idUnidade.value = id;
		document.form.nomeUnidade.value = desc.split("-")[0];		
	}
	
	function pesquisar(){
		document.form.fireEvent("pesquisar");
		$("#loading_overlay").show();
	}
	
	
	function checkAll(){
		var chkAll = document.getElementById("chkMarcarTodos");
		for(var i = 0 ; i < listaFalhas.length; i++){
			chk = document.getElementById("check-" + i);
			if(chk != null) {
				chk.checked = chkAll.checked;
			}
		}
	}
	
	function justificar(){
		var justificacao = document.getElementById("txtJustificacaoFalha").value;
		var chk;
		var listaAux = [];
		for(var i = 0 ; i < listaFalhas.length; i++){
			chk = document.getElementById("check-" + i);
			if(chk != null && chk.checked == 1){
				listaFalhas[i].descricao = justificacao;
				listaAux.push(listaFalhas[i]);
			}
		}
		var listaSerializada = ObjectUtils.serializeObjects(listaAux);
		document.getElementById("listaItensSerializado").value = listaSerializada;
// 		alert(listaSerializada);
		document.form.fireEvent("salvarJustificativa");
		document.getElementById('tabelaFalhas').innerHTML = ""
		$("#loading_overlay").show();
		$("#popupJustificacao").dialog("close");
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
					<i18n:message key="justificacaoFalhas.JustificacaoFalhas" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><i18n:message key="justificacaoFalhas.consulta" /></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/justificacaoFalhas/justificacaoFalhas'>
								<input type="hidden" id="listaItensSerializado" name="listaItensSerializado"/> 
									
									<%
										if(!br.com.citframework.util.Util.isVersionFree(request)){												
									%>
									<div class="columns clearfix">
										<div class="col_66">
											<fieldset>											
											<label><i18n:message key="justificacaoFalhas.evento" /></label>
											<div>
												<input type="hidden" id="idEvento" name="idEvento" />
												<input type="text" readonly="readonly" id="nomeEvento" size="100%"
													   name="nomeEvento" onclick="$('#POPUP_EVENTOS').dialog('open')"/>
											</div>
											</fieldset>
										</div>
									</div>
									<%
										}
									%>
									
									<div class="columns clearfix">
										<div class="col_33">
											<fieldset>											
												<label><i18n:message key="justificacaoFalhas.grupoEmpregados" /></label>
												<div>
													<input type="hidden" id="idGrupo" name="idGrupo" />
													<input type="text" readonly="readonly" id="nomeGrupo"
														   name="nomeGrupo" onclick="$('#POPUP_GRUPO_EMPREGADOS').dialog('open')"/>
												</div>
											</fieldset>	   
										</div>
										<div class="col_33">
											<fieldset>
											<label><i18n:message key="justificacaoFalhas.unidadeEvento" /></label>
											<div>
												<input type="hidden" id="idUnidade" name="idUnidade" />
												<input type="text" readonly="readonly" id="nomeUnidade" size="45"
													   name="nomeUnidade" onclick="$('#POPUP_UNIDADE_EMPREGADO').dialog('open')"/>
											</div>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix">	
										<div class="col_33">
											<fieldset>
												<label><i18n:message key="pesquisa.datainicio" /></label>
												<div>
													<input type="text" class="datepicker" id="dataInicial" name="dataInicial">
												</div>										
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label><i18n:message key="pesquisa.datafim" /></label>
												<div>
													<input type="text" class="datepicker" id="dataFinal" name="dataFinal">
												</div>
											</fieldset>	
										</div>				
									</div>
								<div class="col_66">
									<button type='button' name='btnPesquisar' class="light" onclick="pesquisar();">
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
										<span><i18n:message key="citcorpore.comum.pesquisa"/></span>
									</button>
								</div>
								<div class="col_33" id="qtdTotal" align="center">
									
								</div>
								<div class="col_100" style="height: 300px">
									<input type="hidden" name="listaSerializada" id="listaSerializada">
									<div style="border: 1px solid  #DDDDDD; text-align: center;" id="tabelaFalhas">
										
									</div>
								</div>
							</form>
					</div>
				</div>
			</div>
		</div>		
		</div>
<!-- 		JUSTIFICACAO 	   -->
		<div id="popupJustificacao" class="popup">
			<div class="columns clearfix">
				<div class="col_33">
					<fieldset>
						<label><i18n:message key="pesquisa.grupo" />:</label>
						<div>
							<label id="lbNomeGrupo"></label>
						</div>
					</fieldset>
				</div>
				<div class="col_33">
					<fieldset>
						<label><i18n:message key="pesquisa.unidade" />:</label>
						<div>
							<label id="lbNomeUnidade"></label>
						</div>
					</fieldset>
				</div>
				<div class="col_33">
					<fieldset>
						<label><i18n:message key="inventario.ip" />:</label>
						<div>
							<label id="lbIP"></label>
						</div>
					</fieldset>
				</div>
			</div>
			<div class="columns clearfix">
				<table id="descricaoFalhas" class="table">
					<tr>
						<th><i18n:message key="justificacaoFalhas.falha" /></th>
						<th><i18n:message key="justificacaoFalhas.item" /></th>
						<th><i18n:message key="justificacaoFalhas.tipo" /></th>
						<th><input type="checkbox" onclick="checkAll();" id="chkMarcarTodos" />
									<i18n:message key="justificacaoFalhas.marcarTodos" />
						</th>
					</tr>
				</table>
			</div>
			<div class="col_100" style="background-color: #cccccc">
				<fieldset>
					<label><i18n:message key="justificacaoFalhas.justifique" /></label>
					<div>
						<textarea rows="3" cols="100" id="txtJustificacaoFalha"></textarea>
						<button type='button' id="btSalvar" name="btSalvar" class="light"
							onclick="justificar();">
							<img
								src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
							<span> <i18n:message key="citcorpore.comum.gravar" /> </span>
						</button>
					</div>
				</fieldset>
			</div>
		</div>
	</div>	
		<!-- 		EVENTOS		 -->
		<div id="POPUP_EVENTOS" title="<i18n:message key="citcorpore.comum.pesquisa"/> " class="popup">
 			<table style="width: 100%">
				<tr>
					<td>
						<h3 align="center"><i18n:message key="justificacaoFalhas.evento" /></h3>
					</td>
				</tr>
			</table>				
			<form name='formPesquisaEvento' style="width: 90%;" >
				<cit:findField formName='formPesquisaEvento' 
							   lockupName='LOOKUP_EVENTOS' 
							   id='LOOKUP_EVENTOS' 
							   top='0' left='0' len='550' 
							   heigth='400' 
							   javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		<!-- 		GRUPO EVENTOS		 -->
		<div id="POPUP_GRUPO_EMPREGADOS" title="<i18n:message key="citcorpore.comum.pesquisa"/>" class="popup">
 			<table style="width: 100%">
				<tr>
					<td>
						<h3 align="center"><i18n:message key="justificacaoFalhas.grupoEmpregados" /></h3>
					</td>
				</tr>
			</table>				
			<form name='formPesquisaGrupo' style="width: 85%;">
				<cit:findField formName='formPesquisaGrupo' 
							   lockupName='LOOKUP_GRUPO' 
							   id='LOOKUP_GRUPO' 
							   top='0' left='0' len='550' 
							   heigth='400' 
							   javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		<!-- 		UNIDADE EVENTOS		 -->
		<div id="POPUP_UNIDADE_EMPREGADO" title="<i18n:message key="citcorpore.comum.pesquisa"/>" class="popup">
 			<table style="width: 100%">
				<tr>
					<td>
						<h3 align="center"><i18n:message key="justificacaoFalhas.unidadeEvento" /></h3>
					</td>
				</tr>
			</table>				
			<form name='formPesquisaUnidade' style="width: 85%;">
				<cit:findField formName='formPesquisaUnidade' 
							   lockupName='LOOKUP_UNIDADE' 
							   id='LOOKUP_UNIDADE' 
							   top='0' left='0' len='550' 
							   heigth='400' 
							   javascriptCode='true' htmlCode='true' />
			</form>
		</div>		
	<%@include file="/include/footer.jsp"%>
</body>
</html>

