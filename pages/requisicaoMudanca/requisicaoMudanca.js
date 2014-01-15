$.fx.speeds._default = 1000;
var myLayout;
var popupManager;
var LOOKUP_EMPREGADO_select;
var popupManagerSolicitacaoServico;

var slickGridColunas;
var slickGridOptions;
var slickGridTabela;

var tabelaRelacionamentoICs;
var tabelaRelacionamentoServicos;
var tabelaRelacionamentoLiberacao;
var tabelaProblema;
var tabelaRelacionamentoSolicitacaoServico;
var tabelaRisco;

var count = 0;
var popup2;

var idItemConfiguracao;
var descricaoItemConfiguracao;
var descricaoTratada;

$(document).ready(function() {
	$('#horaAgendamentoFinal').mask('99:99');
	$('#horaAgendamentoInicial').mask('99:99');
});

function zerarContadores() {
	count = 0;
}

/* Gravar baseline */
function gravarBaseline() {
	var tabela = document.getElementById('tblBaselines');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var baselines = new Array();

	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idHistoricoMudanca' + i);
		if (!trObj) {
			continue;
		}
		baselines[contadorAux] = getbaseline(i);
		contadorAux = contadorAux + 1;
	}
	serializaBaseline();
	document.form.fireEvent("saveBaseline");
	document.form.fireEvent("restoreColaboradorSolicitante");
}

var seqBaseline = '';
var aux = '';
serializaBaseline = function() {
	var tabela = document.getElementById('tblBaselines');
	var count = tabela.rows.length;
	var contadorAux = 0;
	var baselines = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idHistoricoMudanca' + i);
		if (!trObj) {
			continue;
		} else if (trObj.checked) {
			baselines[contadorAux] = getbaseline(i);
			contadorAux = contadorAux + 1;
			continue;
		}

	}
	var baselinesSerializadas = ObjectUtils.serializeObjects(baselines);
	document.form.baselinesSerializadas.value = baselinesSerializadas;
	return true;
}

getbaseline = function(seq) {
	var HistoricoMudancaDTO = new CIT_HistoricoMudancaDTO();
	HistoricoMudancaDTO.sequencia = seq;
	HistoricoMudancaDTO.idHistoricoMudanca = eval('document.form.idHistoricoMudanca' + seq + '.value');
	return HistoricoMudancaDTO;
}
function marcarCheckbox(elementos) {
	var arrayIds = new Array();
	arrayIds = elementos;
	for ( var i = 0; i <= arrayIds.length; i++) {
		var posicao = arrayIds[i];
		$("#posicao").attr("checked", true);
	}

}

function restaurarHistorico(id) {
	document.form.idHistoricoMudanca.value = id;
	if (confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
		document.form.fireEvent("restaurarBaseline");
}

function addResponsavel(id, nome, cargo, tel, email) {
	var obj = new CIT_RequisicaoMudancaResponsavelDTO();

	document.getElementById('responsavel#idResponsavel').value = id;
	document.getElementById('responsavel#nomeResponsavel').value = nome;
	document.getElementById('responsavel#nomeCargo').value = cargo;
	document.getElementById('responsavel#telResponsavel').value = tel;
	document.getElementById('responsavel#emailResponsavel').value = email;
	document.getElementById('responsavel#papelResponsavel').value = prompt(i18n_message("citcorpore.comum.papel"));

	HTMLUtils.addRow('tblResponsavel', document.form, 'responsavel', obj, [ "", "idResponsavel", "nomeResponsavel", "nomeCargo", "telResponsavel", "emailResponsavel", "papelResponsavel" ], [ "idResponsavel" ], null, [ gerarImgDelResponsavel ], null, null, false);
	$("#POPUP_RESPONSAVEL").dialog("close");
}

function adicionarResponsavel() {
	$("#POPUP_RESPONSAVEL").dialog("open");
}

function verificaIdSolicitacaoNaURL() {
	var idRequisicao = extrairVariavelDaUrl("idRequisicao");

	if (idRequisicao != null && idRequisicao != "") {
		restaurarRequisicao(idRequisicao)
	}
}

function mostraMensagemInsercao(msg) {
	document.getElementById('divMensagemInsercao').innerHTML = msg;
	$("#POPUP_INFO_INSERCAO").dialog("open");
}

$(function() {
	$("#addSolicitante").click(function() {
		if (document.form.idContrato.value == '') {
			alert(i18n_message("contrato.alerta.informe_contrato"));
			return;
		}
		var y = document.getElementsByName("btnLimparLOOKUP_SOL_CONTRATO");
		y[0].style.display = 'none';
		$("#POPUP_SOLICITANTE").dialog("open");
	});
});

function pesquisarAD() {
	JANELA_AGUARDE_MENU.show();
	document.form.filtroADPesq.value = $("#filtroADPesqAux").val();
	if ($("#filtroADPesqAux").val() == "") {
		alert(i18n_message("login.digite_login"));
		$("#filtroADPesqAux").focus();
		JANELA_AGUARDE_MENU.hide();
		return;
	}
	if ($("#idContrato").val() == "") {
		alert(i18n_message("ss.escolhaContrato"));
		$("#idContrato").focus();
		JANELA_AGUARDE_MENU.hide();
		return;
	}
	document.form.fireEvent("sincronizaAD");
}
function initPopups() {
	$(".POPUP_LOOKUP").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});

	$(".POPUP_LOOKUP_ITEMCONFIGURACAO").dialog({
		autoOpen : false,
		width : 400,
		height : 200,
		modal : true
	});

	$("#POPUP_INFO_INSERCAO").dialog({
		autoOpen : false,
		width : 400,
		height : 280,
		modal : true,
		close : function(event, ui) {
			fechar();
		}
	});
}

function LOOKUP_SOL_CONTRATO_select(id, desc) {
	document.form.idSolicitante.value = id;
	document.form.fireEvent("restoreColaboradorSolicitante");
}

function setaValorLookup(obj) {
	document.form.idSolicitante.value = '';
	document.form.nomeSolicitante.value = '';
	document.form.emailSolicitante.value = '';
	document.form.nomeContato.value = '';
	document.form.telefoneContato.value = '';
	document.form.observacao.value = '';
	document.form.ramal.value = '';
	document.getElementById('idLocalidade').options.length = 0;
	document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = '';
	document.getElementById('pesqLockupLOOKUP_SOL_CONTRATO_IDCONTRATO').value = obj.value;
	// document.form.servicoBusca.value = '';
	// document.getElementById('tipo').options[0].selected = 'selected';
}

/*
 * Funções de apoio
 */

function extrairVariavelDaUrl(nome) {
	var valor = null;
	var identificador = null;
	try {
		var strUrl = document.URL;
		var params = strUrl.split("?")[1];
		var variaveis = params.split("&");
		for ( var i = 0; i < variaveis.length; i++) {
			valor = variaveis[i].split("=")[1];
			identificador = variaveis[i].split("=")[0];

			if (identificador == nome) {
				return valor;
			}

			valor = null;
		}
	} catch (e) {
	}

	return null;
}

function serializaResponsavel() {
	var responsavel = HTMLUtils.getObjectsByTableId('tblResponsavel');
	document.form.responsavel_serialize.value = ObjectUtils.serializeObjects(responsavel);
}

function limparFCKEditores() {
	var fckEditorAux;
	var textAreaList = document.getElementsByTagName("textarea");

	for ( var i = 0; i < textAreaList.length; i++) {
		if (textAreaList[i].id != null) {

			fckEditorAux = FCKeditorAPI.GetInstance(textAreaList[i].id);

			if (fckEditorAux != null) {
				try {
					fckEditorAux.SetData("");
				} catch (e) {
					// alert("Problemas com FCKEditor. \n" + e.message)
				}
			}
		}
	}
}

/** INFLUENCIA PRIORIDADE */
function atualizaPrioridade() {

	var impacto = document.getElementById('nivelImpacto').value;
	var urgencia = document.getElementById('nivelUrgencia').value;

	if (urgencia == "B") {
		if (impacto == "B") {
			document.form.prioridade.value = 5;
		} else if (impacto == "M") {
			document.form.prioridade.value = 4;
		} else if (impacto == "A") {
			document.form.prioridade.value = 3;
		}
	}

	if (urgencia == "M") {
		if (impacto == "B") {
			document.form.prioridade.value = 4;
		} else if (impacto == "M") {
			document.form.prioridade.value = 3;
		} else if (impacto == "A") {
			document.form.prioridade.value = 2;
		}
	}

	if (urgencia == "A") {
		if (impacto == "B") {
			document.form.prioridade.value = 3;
		} else if (impacto == "M") {
			document.form.prioridade.value = 2;
		} else if (impacto == "A") {
			document.form.prioridade.value = 1;
		}
	}
}

/*
 * Funções auxílio CRUD
 */

function limpar(form) {
	try {
		form.clear();
	} catch (e) {
	}

	limparFCKEditores();
	limpaListasRelacionamentos();
}

function validarDatas() {
	var inputs = document.getElementsByClassName("datepicker");
	var input = null;
	var errorMsg = i18n_message("citcorpore.comum.nenhumaDataDeveSerInferiorHoje");

	for ( var i = 0; i < inputs.length; i++) {
		input = inputs[i];

		if (input == null) {
			continue;
		}

		if (comparaComDataAtual(input) < 0) {
			alert(errorMsg);
			input.focus();
			throw errorMsg;
		}
	}
}

function gerarRelatorioPDF() {
	document.form.fireEvent("imprimirRelatorioReqMudanca");
}

function restaurar() {
	var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);
	var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);
	var listaServicos = ObjectUtils.deserializeCollectionFromString(document.form.servicosRelacionadosSerializado.value);
	var listaProblema = ObjectUtils.deserializeCollectionFromString(document.form.problemaSerializado.value);
	var listaLiberacoes = ObjectUtils.deserializeCollectionFromString(document.form.liberacoesRelacionadosSerializado.value);
	var listaRisco = ObjectUtils.deserializeCollectionFromString(document.form.riscoSerializado.value);
	limpaListasRelacionamentos();

	for ( var i = 0; i < listaICs.length; i++) {
		tabelaRelacionamentoICs.addObject([ listaICs[i].idItemConfiguracao, listaICs[i].nomeItemConfiguracao, listaICs[i].descricao ]);
	}

	for ( var i = 0; i < listaServicos.length; i++) {
		tabelaRelacionamentoServicos.addObject([ listaServicos[i].idServico, listaServicos[i].nome, listaServicos[i].descricao, getBotaoVisualizarMapa(listaServicos[i].idServico) ]);
	}

	for ( var i = 0; i < listaLiberacoes.length; i++) {
		tabelaRelacionamentoLiberacao.addObject([ listaLiberacoes[i].idLiberacao, listaLiberacoes[i].titulo, listaLiberacoes[i].descricao, listaLiberacoes[i].status ]);
	}

	if (listaSolicitacaoServico.length > 0) {
		for ( var i = 0; i < listaSolicitacaoServico.length; i++) {
			tabelaRelacionamentoSolicitacaoServico.addObject([ listaSolicitacaoServico[i].idSolicitacaoServico, listaSolicitacaoServico[i].nomeServico ]);
		}
	}

	if (listaProblema.length > 0) {
		for ( var i = 0; i < listaProblema.length; i++) {
			tabelaProblema.addObject([ listaProblema[i].idProblema, listaProblema[i].titulo, listaProblema[i].status, getBotaoEditarProblema(listaProblema[i].idProblema) ]);
		}
	}

	if (listaRisco.length > 0) {
		for ( var i = 0; i < listaRisco.length; i++) {
			tabelaRisco.addObject([ listaRisco[i].idRisco, listaRisco[i].nomeRisco, listaRisco[i].detalhamento ]);
		}
	}
}

function deletar() {
	document.form.fireEvent("delete");
}

/** Ajusta dados dos textareas com fckeditor ao restaurar. */
function restauraFckEditores() {
	var textAreaList = document.getElementsByTagName("textarea");

	for ( var i = 0; i < textAreaList.length; i++) {
		if (textAreaList[i].id != null) {

			fckEditorAux = FCKeditorAPI.GetInstance(textAreaList[i].id);

			if (fckEditorAux != null) {
				try {
					fckEditorAux.SetData(document.getElementById(textAreaList[i].id).value);
				} catch (e) {
				}
			}
		}
	}
}

/*
 * Reaproveitamento da lookup EMPREGADO
 */
function selecionarSolicitante() {
	LOOKUP_EMPREGADO_select = function(id, desc) {
		document.form.idSolicitante.value = id;
		document.form.nomeSolicitante.value = desc.split("-")[0];
		$("#POPUP_EMPREGADO").dialog("close");
	}

	$("#POPUP_EMPREGADO").dialog("open");
}

function selecionarProprietario() {
	limpar_LOOKUP_EMPREGADO();
	LOOKUP_EMPREGADO_select = function(id, desc) {
		document.form.idProprietario.value = id;
		document.form.nomeProprietario.value = desc.split("-")[0];
		$("#POPUP_EMPREGADO").dialog("close");
	}

	$("#POPUP_EMPREGADO").dialog("open");
}
/* ------------- */

function restaurarRequisicao(idRequisicao) {
	document.form.idRequisicaoMudanca.value = idRequisicao;
	document.form.fireEvent("restore");
}

/*
 * Funções de relacionamento
 */

/*
 * Adicionado por David, para validar a aba de item de configuração
 */
function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
	idItemConfiguracao = id;
	descricaoItemConfiguracao = desc;
	addLinhaTabelaItemConfiguracao(id, desc, true);

}

function addLinhaTabelaItemConfiguracao(id, desc, valida) {
	var tbl = document.getElementById('tblICs');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	if (valida) {
		if (!validaAddLinhaTabelaItemConfiguracao(lastRow, id)) {
			return;
		}
	}

	abrePopupDescricaoIcs(id, desc);

}

function validaAddLinhaTabelaItemConfiguracao(lastRow, id) {
	var listaICs = ObjectUtils.deserializeCollectionFromString(document.form.itensConfiguracaoRelacionadosSerializado.value);

	if (lastRow > 1) {
		for ( var i = 0; i < listaICs.length; i++) {
			if (listaICs[i].idItemConfiguracao == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
	}
	return true;
}

function abrePopupIcs() {
	redimensionarTamhanho("#POPUPITEMCONFIGURACAO", "PEQUENO");
	$("#POPUPITEMCONFIGURACAO").dialog("open");
}

function abrePopupDescricaoIcs() {
	redimensionarTamhanho("#POPUPDESCRICAOITEMCONFIGURACAO", "PEQUENO");
	$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("open");
}

function adicionarIC() {
	abrePopupIcs();
}

function LOOKUP_SERVICO_select(id, desc) {
	addLinhaTabelaServicos(id, desc, true);

};

function LOOKUP_RESPONSAVEL_select(id, desc) {
	var str = desc.split('-');
	addResponsavel(id, str[0], str[1], str[2] + "-" + str[3], str[4]);
}

function addLinhaTabelaServicos(id, desc, valida) {
	var tbl = document.getElementById('tblServicos');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	if (valida) {
		if (!validaAddLinhaTabelaServicos(lastRow, id)) {
			return;
		}
	}

	var camposLookupServico = desc.split("-");
	tabelaRelacionamentoServicos.addObject([ id, camposLookupServico[0], camposLookupServico[1], getBotaoVisualizarMapa(id) ]);

	document.form.servicosRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoServicos.getTableObjects());

	$("#POPUP_SERVICO").dialog("close");

}

function validaAddLinhaTabelaServicos(lastRow, id) {
	var listaServicos = ObjectUtils.deserializeCollectionFromString(document.form.servicosRelacionadosSerializado.value);

	if (lastRow > 1) {
		for ( var i = 0; i < listaServicos.length; i++) {
			if (listaServicos[i].idServico == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
	}
	return true;
}

// adicionar problema

function LOOKUP_LIBERACAO_MUDANCA_select(id, desc) {
	document.getElementById('liberacao#idRequisicaoLiberacao').value = id;
	document.form.fireEvent("inserirRequisicaoLiberacao");

};

// Adiciona a linha da liberação
function adicionaLiberacaoMudanca(idLiberacao, titulo, descricao, status) {

	// Faz a validação para verificar pelo id que o registro já está adicionado
	var tbl = document.getElementById('tblLiberacao');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	var valida = true;
	if (valida) {
		if (!validaAddLinhaTabelaLiberacao(lastRow, idLiberacao)) {
			return;
		}
	}

	tabelaRelacionamentoLiberacao.addObject([ idLiberacao, titulo, descricao, status ], [ getBotaoVisualizarMapa(idLiberacao) ]);

	document.form.liberacoesRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoLiberacao.getTableObjects());

	$("#POPUP_LIBERACAO").dialog("close");
}

function validaAddLinhaTabelaLiberacao(lastRow, id) {
	var listaLiberacoes = ObjectUtils.deserializeCollectionFromString(document.form.liberacoesRelacionadosSerializado.value);

	if (lastRow > 1) {
		for ( var i = 0; i < listaLiberacoes.length; i++) {
			if (listaLiberacoes[i].idLiberacao == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
	}
	return true;
}

// adicionar problema
function LOOKUP_PROBLEMA_select(id, desc) {
	addLinhaTabelaProblema(id, desc, true);

};

function addLinhaTabelaProblema(id, desc, valida) {
	var tbl = document.getElementById('tblProblema');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	if (valida) {
		if (!validaAddLinhaTabelaProblema(lastRow, id)) {
			return;
		}
	}

	desc = desc.replace(/['"]*/g, '');

	var camposLookupProblema = desc.split("-");
	tabelaProblema.addObject([ id, camposLookupProblema[0], camposLookupProblema[1], getBotaoEditarProblema(id) ]);

	document.form.problemaSerializado.value = ObjectUtils.serializeObjects(tabelaProblema.getTableObjects());

	$("#POPUP_PROBLEMA").dialog("close");

}

function validaAddLinhaTabelaProblema(lastRow, id) {
	var listaProblema = ObjectUtils.deserializeCollectionFromString(document.form.problemaSerializado.value);

	if (lastRow > 1) {
		for ( var i = 0; i < listaProblema.length; i++) {
			if (listaProblema[i].idProblema == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
	}
	return true;
}

// adicionar Risco
function LOOKUP_RISCO_select(id, desc) {
	addLinhaTabelaRisco(id, desc, true);

};

function addLinhaTabelaRisco(id, desc, valida) {
	var tbl = document.getElementById('tblRisco');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	if (valida) {
		if (!validaAddLinhaTabelaRisco(lastRow, id)) {
			return;
		}
	}

	var camposLookupRisco = desc.split("-");
	tabelaRisco.addObject([ id, camposLookupRisco[0], camposLookupRisco[1] ]);

	document.form.riscoSerializado.value = ObjectUtils.serializeObjects(tabelaRisco.getTableObjects());

	$("#POPUP_RISCO").dialog("close");

}

function validaAddLinhaTabelaRisco(lastRow, id) {
	var listaRisco = ObjectUtils.deserializeCollectionFromString(document.form.riscoSerializado.value);

	if (lastRow > 1) {
		for ( var i = 0; i < listaRisco.length; i++) {
			if (listaRisco[i].idRisco == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
	}
	return true;
}

function abrePopupServicos() {
	$("#POPUP_SERVICO").dialog("open");
}

function abrePopupLiberacao() {
	$("#POPUP_LIBERACAO").dialog("open");
}

function abrePopupRisco() {
	$("#POPUP_RISCO").dialog("open");
}

function abrePopupProblema() {
	$("#POPUP_PROBLEMA").dialog("open");
}

function adicionarRisco() {
	abrePopupRisco();
}

function adicionarServico() {
	abrePopupServicos();
}

function adicionarProblema() {
	abrePopupProblema();
}

function adicionarLiberacao() {
	abrePopupLiberacao();
}

/*
 * Funções alimentação tabelas de relacionamento
 */

/**
 * Renderiza tabela a partir de lista.
 * 
 * @param _idCITTable
 *            id da tabela a ser tratada
 * @param _fields
 *            Lista de campos correspondentes ao banco de dados
 * @param _tableObjects
 *            Lista de itens. Deve corresponder aos campos de _fields
 */
function CITTable(_idCITTable, _fields, _tableObjects) {
	var self = this;
	var idCITTable = _idCITTable;
	var fields = _fields;
	var tableObjects = _tableObjects;
	var tabela = null;

	var insereBtExcluir = true;
	var imgBotaoExcluir;

	this.onDeleteRow = function(deletedItem) {
	};

	this.getTableList = function() {
		return tableObjects;
	}

	/**
	 * Transforma a lista da tabela em uma lista de objetos de acordo com o
	 * 'fields' passado.
	 */
	this.getTableObjects = function() {
		var objects = [];
		var object = {};

		for ( var j = 0; j < tableObjects.length; j++) {
			for ( var i = 0; i < fields.length; i++) {
				eval("object." + fields[i] + " = '" + tableObjects[j][i] + "'");
			}
			objects.push(object);
			object = {};
		}

		return objects;
	}

	this.setTableObjects = function(objects) {
		tableObjects = objects;
		this.montaTabela();
	}

	this.addObject = function(object) {
		tableObjects.push(object);
		this.montaTabela();
	}

	this.limpaLista = function() {
		tableObjects.length = 0;
		tableObjects = null;
		tableObjects = [];
		limpaTabela();
	}

	var limpaTabela = function() {
		while (getTabela().rows.length > 1) {
			getTabela().deleteRow(1);
		}
	}

	this.montaTabela = function() {
		var linha;
		var celula;

		limpaTabela();

		for ( var i = tableObjects.length - 1; i >= 0; i--) {

			var j = 0;
			linha = getTabela().insertRow(1);

			for (j = 0; j < fields.length; j++) {
				celula = linha.insertCell(j);

				// tratamento caso seja um componente ao invés de texto
				try {
					celula.appendChild(tableObjects[i][j]);
				} catch (e) {
					celula.innerHTML = tableObjects[i][j];
				}
			}

			if (insereBtExcluir) {
				var btAux = getCopiaBotaoExcluir();
				var celExcluir = linha.insertCell(j);

				btAux.setAttribute("id", i);
				btAux.addEventListener("click", function(evt) {
					// ao disparar o evento, considerará o id do botão
					self.removeObject(this.id);
					this.onDeleteRow(this);

				}, false);
				celExcluir.appendChild(btAux);
			}
		}
	}

	this.removeObject = function(indice) {
		removeObjectDaLista(indice);
		this.montaTabela();
	}

	/**
	 * Remove item e organiza lista
	 */
	var removeObjectDaLista = function(indice) {
		tableObjects[indice] = null;
		var novaLista = [];
		for ( var i = 0; i < tableObjects.length; i++) {
			if (tableObjects[i] != null) {
				novaLista.push(tableObjects[i]);
			}
		}
		tableObjects = novaLista;
	}

	var getCopiaBotaoExcluir = function() {
		var novoBotao = new Image();
		novoBotao.setAttribute("style", "cursor: pointer;");
		novoBotao.src = imgBotaoExcluir;
		return novoBotao;
	}

	var setImgPathBotaoExcluir = function(src) {
		imgBotaoExcluir = src;
	}

	var getTabela = function() {
		if (tabela == null) {
			tabela = document.getElementById(idCITTable);
		}
		return tabela;
	}

	this.setInsereBotaoExcluir = function(bool, imgSrc) {
		insereBtExcluir = bool;
		setImgPathBotaoExcluir(imgSrc);
	}
}

fechar = function() {
	parent.fecharMudanca();
}

/*
 * Funções de Solicitacao Servico
 */

$(function() {
	$("#POPUP_SOLICITACAOSERVICO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});

	$("#addSolicitacaoServico").click(function() {
		$("#POPUP_SOLICITACAOSERVICO").dialog("open");
	});

	$("#addImgSolicitacaoServico").click(function() {
		$("#POPUP_SOLICITACAOSERVICO").dialog("open");
	});

});

function fecharProblema() {
	$("#POPUP_SOLICITACAOSERVICO").dialog("close");
}

function LOOKUP_SOLICITACAOSERVICO_select(id, desc) {
	addLinhaTabelaSolicitacaoServico(id, desc, true);

}

function addLinhaTabelaSolicitacaoServico(id, desc, valida) {
	var tbl = document.getElementById('tblSolicitacaoServico');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	if (valida) {
		if (!validaAddLinhaTabelaSolicitacaoServico(lastRow, id)) {
			return;
		}
	}

	var camposLookupItem = desc.split("-");
	tabelaRelacionamentoSolicitacaoServico.addObject([ id, camposLookupItem[1], camposLookupItem[2] ]);

	document.form.solicitacaoServicoSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoSolicitacaoServico.getTableObjects());

	$("#POPUP_SOLICITACAOSERVICO").dialog("close");

}

function validaAddLinhaTabelaSolicitacaoServico(lastRow, id) {
	var listaSolicitacaoServico = ObjectUtils.deserializeCollectionFromString(document.form.solicitacaoServicoSerializado.value);

	if (lastRow > 1) {
		for ( var i = 0; i < listaSolicitacaoServico.length; i++) {
			if (listaSolicitacaoServico[i].idSolicitacaoServico == id) {
				alert(i18n_message("citcorpore.comum.registroJaAdicionado"));
				return false;
			}
		}
	}
	return true;
}

function AprovacaoMudancaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario, i) {
	this.idEmpregado = idEmpregado;
	this.nomeEmpregado = nomeEmpregado;
	this.dataHoraVotacao = dataHoraVotacao
	this.voto = voto;
	this.comentario = comentario;
}

function AprovacaoPropostaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario, i) {
	this.idEmpregado = idEmpregado;
	this.nomeEmpregado = nomeEmpregado;
	this.dataHoraVotacao = dataHoraVotacao
	this.voto = voto;
	this.comentario = comentario;
}

function serializaAprovacoesProposta() {
	var tabela = document.getElementById('tabelaAprovacoesProposta');
	var count = tabela.rows.length;
	var listaDeAprovacoes = [];
	for ( var i = 1; i < count; i++) {
		var voto = '';
		if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null) {
			var idEmpregado = document.getElementById('idEmpregado' + i).value;
			var nomeEmpregado = document.getElementById('nomeEmpregado' + i).value;
			var dataHoraVotacao = document.getElementById('dataHoraVotacao' + i).value;
			if ($('#votoAProposta' + i).is(":checked")) {
				voto = "A";
			} else {
				if ($('#votoRProposta' + i).is(":checked")) {
					voto = "R";
				}
			}

			var comentario = document.getElementById('comentarioProposta' + i).value;
			var aprovacaoProposta = new AprovacaoPropostaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario, i);
			listaDeAprovacoes.push(aprovacaoProposta);
		}
	}
	var serializa = ObjectUtils.serializeObjects(listaDeAprovacoes);
	document.form.aprovacaoPropostaServicoSerializado.value = serializa;
}

function serializaAprovacoesMudanca() {
	var tabela = document.getElementById('tabelaAprovacoesMudanca');
	var count = tabela.rows.length;
	var listaDeAprovacoes = [];
	for ( var i = 1; i < count; i++) {
		var voto = '';
		if (document.getElementById('idEmpregado' + i) != "" && document.getElementById('idEmpregado' + i) != null) {
			var idEmpregado = document.getElementById('idEmpregado' + i).value;
			var nomeEmpregado = document.getElementById('nomeEmpregado' + i).value;
			var dataHoraVotacao = document.getElementById('dataHoraVotacao' + i).value;
			if ($('#votoAMudanca' + i).is(":checked")) {
				voto = "A";
			} else {
				if ($('#votoRMudanca' + i).is(":checked")) {
					voto = "R";
				}
			}

			var comentario = document.getElementById('comentarioMudanca' + i).value;
			var aprovacaoMudanca = new AprovacaoMudancaDTO(idEmpregado, nomeEmpregado, dataHoraVotacao, voto, comentario, i);
			listaDeAprovacoes.push(aprovacaoMudanca);
		}
	}
	var serializa = ObjectUtils.serializeObjects(listaDeAprovacoes);
	document.form.aprovacaoMudancaServicoSerializado.value = serializa;
}

function addLinhaTabelaAprovacaoProposta(idEmpregado, nomeEmpregado, comentario, dataHoraVotacao, validacao, valida) {
	var tbl = document.getElementById('tabelaAprovacoesProposta');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	var row = tbl.insertRow(lastRow);
	var disabled = '';
	if (validacao == 'true') {
		disabled = 'disabled = "true"';
	}

	count++;
	coluna = row.insertCell(0);
	coluna.innerHTML = '<input id="idEmpregado' + count + '" type="hidden" name="idEmpregado" value="' + idEmpregado + '"/><input  value = "' + nomeEmpregado + '"  type="hidden" id="nomeEmpregado' + count + '" />';
	coluna = row.insertCell(1);
	coluna.innerHTML = nomeEmpregado;
	coluna = row.insertCell(2);
	coluna.innerHTML = '<span  style="padding-right: 30px;"><input ' + disabled + '  style="margin-right: 5px;" type="radio" id="votoAProposta' + count + '" name="voto' + count + '" value="A"  />Aprovada</span>' + '<span style="padding-right: 30px;"><input ' + disabled
			+ ' style="margin-right: 5px;" type="radio" id="votoRProposta' + count + '" name="voto' + count + '" value="R" />Rejeitada</span>';
	coluna = row.insertCell(3);
	coluna.innerHTML = '<input  ' + disabled + '  value="' + comentario + '" name="comentario' + count + '" id="comentarioProposta' + count + '" size="100"  type="text" maxlength="200" />'
	coluna = row.insertCell(4);
	var input = '<input  ' + disabled + '  value="' + dataHoraVotacao + '" name="dataHoraVotacao' + count + '" id="dataHoraVotacao' + count + '" size="100"  type="hidden" maxlength="200" />';
	coluna.innerHTML = dataHoraVotacao + input;

}

function addLinhaTabelaAprovacaoMudanca(idEmpregado, nomeEmpregado, comentario, dataHoraVotacao, validacao, valida) {
	var tbl = document.getElementById('tabelaAprovacoesMudanca');
	tbl.style.display = 'block';
	var lastRow = tbl.rows.length;
	var row = tbl.insertRow(lastRow);
	var disabled = '';
	if (validacao == 'true') {
		disabled = 'disabled = "true"';
	}

	count++;
	coluna = row.insertCell(0);
	coluna.innerHTML = '<input id="idEmpregado' + count + '" type="hidden" name="idEmpregado" value="' + idEmpregado + '"/><input  value = "' + nomeEmpregado + '"  type="hidden" id="nomeEmpregado' + count + '" />';
	coluna = row.insertCell(1);
	coluna.innerHTML = nomeEmpregado;
	coluna = row.insertCell(2);
	coluna.innerHTML = '<span  style="padding-right: 30px;"><input ' + disabled + '  style="margin-right: 5px;" type="radio" id="votoAMudanca' + count + '" name="voto' + count + '" value="A"  />Aprovada</span>' + '<span style="padding-right: 30px;"><input ' + disabled
			+ ' style="margin-right: 5px;" type="radio" id="votoRMudanca' + count + '" name="voto' + count + '" value="R" />Rejeitada</span>';
	coluna = row.insertCell(3);
	coluna.innerHTML = '<input  ' + disabled + '  value="' + comentario + '" name="comentario' + count + '" id="comentarioMudanca' + count + '" size="100"  type="text" maxlength="200" />'
	coluna = row.insertCell(4);
	var input = '<input  ' + disabled + '  value="' + dataHoraVotacao + '" name="dataHoraVotacao' + count + '" id="dataHoraVotacao' + count + '" size="100"  type="hidden" maxlength="200" />';
	coluna.innerHTML = dataHoraVotacao + input;

}

function mostrarEscondeRegExec() {
	if (document.getElementById('divMostraRegistroExecucao').style.display == 'none') {
		document.getElementById('divMostraRegistroExecucao').style.display = 'block';
		document.getElementById('lblMsgregistroexecucao').style.display = 'block';
		document.getElementById('btnAddRegExec').innerHTML = '<i18n:message key="solicitacaoServico.addregistroexecucao_menos" />';
	} else {
		document.getElementById('divMostraRegistroExecucao').style.display = 'none';
		document.getElementById('lblMsgregistroexecucao').style.display = 'none';
		document.getElementById('btnAddRegExec').innerHTML = '<i18n:message key="solicitacaoServico.addregistroexecucao_mais" />';
	}
}

function deleteAllRowsProposta() {
	var tabela = document.getElementById('tabelaAprovacoesProposta');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
}

function deleteAllRowsMudanca() {
	var tabela = document.getElementById('tabelaAprovacoesMudanca');
	var count = tabela.rows.length;

	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
}

function mostrarCategoria() {

	document.form.fireEvent('validacaoCategoriaMudanca');

}

function atribuirCheckedVotoProposta(voto) {
	if (voto == "A" && voto != null) {
		$('#votoAProposta' + count).attr('checked', true);
	} else {
		if (voto == "R" && voto != null) {
			$('#votoRProposta' + count).attr('checked', true);
		}
	}
}

function atribuirCheckedVotoMudanca(voto) {
	if (voto == "A" && voto != null) {
		$('#votoAMudanca' + count).attr('checked', true);
	} else {
		if (voto == "R" && voto != null) {
			$('#votoRMudanca' + count).attr('checked', true);
		}
	}
}

function chamaPopupCadastroSol() {
	if (document.form.idContrato.value == '') {
		alert(i18n_message("solicitacaoservico.validacao.contrato"));
		return;
	}
	var idContrato = 0;
	try {
		idContrato = document.form.idContrato.value;
	} catch (e) {
	}
	popup2.abrePopupParms('empregado', '', 'idContrato=' + idContrato);
}

// ações dos botões
function abrirPopupAnexo() {
	$('#POPUP_menuAnexos').dialog('open');
	uploadAnexos.refresh();
}

function fecharFrameProblema() {
	$("#POPUP_EDITARPROBLEMA").dialog("close");
	// document.form.fireEvent("atualizaGridProblema");
}

function fecharItemRequisicao() {
	$("#POPUPITEMCONFIGURACAO").dialog("close");
}

function fecharPopupDescricaoItemConf() {
	document.getElementById('hiddenDescricaoItemConfiguracao').value = document.getElementById('descricaoItemConfiguracao').value;
	teste();
}

function teste() {
	document.form.fireEvent('tratarCaracterItemConfiguracao');
}

function atualizarTabela(itemTratado) {

	tabelaRelacionamentoICs.addObject([ idItemConfiguracao, descricaoItemConfiguracao, itemTratado ]);
	document.form.itensConfiguracaoRelacionadosSerializado.value = ObjectUtils.serializeObjects(tabelaRelacionamentoICs.getTableObjects());

	$("#POPUPDESCRICAOITEMCONFIGURACAO").dialog("close");
	fecharItemRequisicao();

	document.getElementById('descricaoItemConfiguracao').value = "";
	idItemConfiguracao = "";
	descricaoItemConfiguracao = "";

}

function descricaoTratadaJava(descricaoTratadaJava) {
	// alert(descricaoTratadaJava);
	descricaoTratada = descricaoTratadaJava;
}
function escondeJanelaAguarde() {
    JANELA_AGUARDE_MENU.hide();
}

function restoreImpactoUrgenciaPorTipoMudanca(){
	   document.form.fireEvent('restoreImpactoUrgenciaPorTipoMudanca');
}