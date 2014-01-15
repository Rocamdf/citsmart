$(function(){

	$('#listaDetalhada').click(function(){ 
		
		$('#tipoLista').val(1);
		
		$('.listaResumida').animate({opacity:0},function(){
			$(this).removeClass('ativo');
			$(this).css('display', 'none');
		});
		
		$('.listaDetalhada').animate({opacity:1},function(){
			$(this).addClass('ativo');
			$(this).css('display', 'block');
        	$(this).show();
        	removerBotaoAtivo('#listaResumida');
        	adicionarBotaoAtivo('#listaDetalhada');   
        });
    });

    $('#listaResumida').click(function(){ 
    	
    	$('#tipoLista').val(2);
    	
    	$('.listaDetalhada').animate({opacity:0},function(){
			$(this).removeClass('ativo');
			$(this).css('display', 'none');
		});
    	$('.listaResumida').animate({opacity:1},function(){
    		$(this).addClass('ativo');
    		$(this).css('display', 'block');
        	$(this).show();
        	removerBotaoAtivo('#listaDetalhada');
        	adicionarBotaoAtivo('#listaResumida');        
    	});
    });		
    
    $('#modal_novaSolicitacao').on('hide', function() {
		 document.getElementById('frameNovaSolicitacao').src = '';
	});
});

function adicionarBotaoAtivo(param){
	$(param).addClass('btn-primary').each(function() {
		$(this).find('i').addClass('icon-white');
	});
}

function removerBotaoAtivo(param){
	$(param).removeClass('btn-primary').each(function() {
		$(this).find('i').removeClass('icon-white');
	});
}

function load() {
	document.form.afterRestore = function() {
	}
}

function executaModal(id) {
	window.open(URL_SISTEMA + 'pages/baseConhecimentoView/modalBaseConhecimento.load?id='+id);
}
function executaModalFaq(id) {
	window.open('/pages/pesquisaFaq/pesquisaFaq.load?id='+id);
}

function deleteLinha(table, index){
	HTMLUtils.deleteRow(table, index);
	
}

function gerarButtonDescricao(row) {
	row.cells[1].innerHTML = '<input class="btn btn-small" id="imgDetalhes" title="<i18n:message key="citcorpore.comum.excluir" />" onclick="">';
}

function gerarButtonAdd(row) {
	row.cells[2].innerHTML = '<input class="btn btn-small" id="imgDetalhes" title="<i18n:message key="citcorpore.comum.excluir" />" onclick="">';
}

function carregarServicos(idCatalogoServico, idContratoUsuario){
	document.formListaServicos.idCatalogoServico.value = idCatalogoServico; 
	document.formListaServicos.idContratoUsuario.value = idContratoUsuario;
	document.getElementsByName("observacaoPortal").value = "";
	document.formListaServicos.fireEvent("contentServicos");
}

function marcarTodos(checked){	
	classe = 'perm';
	if (checked) {
		$("." + classe).each(function() {
			$(this).attr("checked", true);
		});					 
	}else {
		$("." + classe).each(function() {
			$(this).attr("checked", false);
		});
	}
}

function setarValoresTabela(param) {
	bootbox.dialog(param, [{
		"label" : i18n_message("portal.adicionarCarrinho"),
		"class" : "btn-primary",
		"callback": function() {
			adicionarCarrinho();
		}
	}, {
		"label" : i18n_message("citcorpore.comum.cancelar"),
		"class" : "btn",
		"callback":function() {
			$("#tblDescricao").hide();
		}
	}]);
}

function limparDadosServicoCatalogo(){
	var element1 = document.getElementsByName("idServicoCatalogo");
	for (index = element1.length - 1; index >= 0; index--) {
		element1[index].parentNode.removeChild(element1[index]);
	}	
	var element2 = document.getElementsByName("idContrato");
	for (index = element2.length - 1; index >= 0; index--) {
		element2[index].parentNode.removeChild(element2[index]);
	}
	var element3 = document.getElementsByName("descInfoCatalogoServico");
	for (index = element3.length - 1; index >= 0; index--) {
		element3[index].parentNode.removeChild(element3[index]);
	}
	var element4 = document.getElementsByName("observacaoPortal");
	for (index = element4.length - 1; index >= 0; index--) {
		element4[index].parentNode.removeChild(element4[index]);
	}	
	
}

function adicionarCarrinho() {
	escolherServicos();
	limparDadosServicoCatalogo();
	document.formListaServicos.fireEvent("adicionaItensCarrinhoDeCompra");
}

function finalizarCarrinho(){
	if (confirm(i18n_message("portal.confirmaFinalizacaoCompra"))){
		lancarServicos();
		document.formCarrinho.fireEvent("finalizarCarrinho");
	}
}

function escolherServicos() {
	checkboxServico = document.getElementsByName('idServicoCatalogo');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var baselines = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoCatalogo' + i);	
		if (!trObj) {
			continue;
		}	
		baselines[contadorAux] = getServicoCatalogo(i);
		contadorAux = contadorAux + 1;
	}
	serializarServicoEscolhido();
}

function lancarServicos() {
	checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for ( var i = 0; i < count; i++) {
		var attr = checkboxServico[i].id.split('idServicoContrato').join('');
		var trObj = document.getElementById('idServicoContrato' + attr);
		if (!trObj) {
			continue;
		}
		servicos[contadorAux] = getServicoContrato(attr);
		contadorAux = contadorAux + 1;
	}
	serializarServicoLancados();
}

informacaoContrato = function(url){
	
}

serializarServicoEscolhido = function() {
//	var tabela = document.getElementById('tblDescricao');
//	var count = tabela.rows.length;
	
	var checkboxServico = document.getElementsByName('idServicoCatalogo');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for ( var i = 1; i <= count; i++) {
		var trObj = document.getElementById('idServicoCatalogo' + i);
		if (!trObj) {
			continue;
		}else if(trObj.checked){
			servicos[contadorAux] = getServicoCatalogo(i);
			contadorAux = contadorAux + 1;
			continue;
		}	
	}
	var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
	document.formListaServicos.servicosEscolhidos.value = servicosSerializadas;
	document.formListaServicos.listaServicosLancados.value = $("#servicosLancados").val();
	return true;
}

serializarServicoLancados = function() {
	var checkboxServico = document.getElementsByName('idServicoContrato');
	var count = checkboxServico.length;
	var contadorAux = 0;
	var servicos = new Array();
	for ( var i = 0; i < count; i++) {
		var attr = checkboxServico[i].id.split('idServicoContrato').join('');
		var trObj = document.getElementById('idServicoContrato' + attr);
		if (!trObj) {
			continue;
		}
		servicos[contadorAux] = getServicoContrato(attr);
		contadorAux = contadorAux + 1;
	}
	var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
	document.formCarrinho.servicosLancados.value = servicosSerializadas;
	return true;
}

getServicoCatalogo = function(seq) {
	var infoCatalogoServicoDTO = new CIT_InfoCatalogoServicoDTO();
	infoCatalogoServicoDTO.sequencia = seq;
	infoCatalogoServicoDTO.idServicoCatalogo = $('#idServicoCatalogo' + seq).val();
	infoCatalogoServicoDTO.idContrato = $('#idContrato' + seq).val();
	infoCatalogoServicoDTO.descInfoCatalogoServico =  $('#descInfoCatalogoServico' + seq).val();
	infoCatalogoServicoDTO.observacaoPortal = $('#observacaoPortal' + seq).val();
	return infoCatalogoServicoDTO;
}

getServicoContrato = function(seq) {
	var servicoContratoDTO = new CIT_ServicoContratoDTO();
	servicoContratoDTO.sequencia = seq;
	servicoContratoDTO.idServicoContrato =  $('#idServicoContrato' + seq).val();
	servicoContratoDTO.descricao = $('#descricao' + seq).val();
	servicoContratoDTO.valorServico = $('#valorServico' + seq).val();
	servicoContratoDTO.observacaoPortal =  $('#observacaoPortal' + seq).val();
	return servicoContratoDTO;
}

function filtrarCatalogo(str){
	document.formListaServicos.filtroCatalogo.value = str;
	document.formListaServicos.fireEvent("filtrarCatalogoServico");
}

/** Autor: Thiago Matias
 * Data: 16/08/2013
 * Filtra todos os dados contidos na lista
 * deve ser chamada no input via onkeyup
 * campoBusca: valor digitado no campo de filtro
 * lista: Id da div onde será feito a busca
 **/
function filtroListaJs(campoBusca, lista){
		// Recupera value do campo de busca
    var term=campoBusca.value.toLowerCase();
	if( term != "")
	{
		 var searchText = term;

	        $('#' + lista + ' ul > li ').each(function(){

	            var currentLiText = $(this).text(),
	                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) !== -1;

	            $(this).toggle(showCurrentLi);

	        });  
	}else{
		// Quando não há nada digitado, mostra a tabela com todos os dados
		 $('#' + lista + ' ul > li').each(function(){

	            var currentLiText = $(this).text(),
	                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) == -1;

	            $(this).toggle(showCurrentLi);

	        }); 
	}
}

function filtroDivsJs(campoBusca, lista){
	// Recupera value do campo de busca
var term=campoBusca.value.toLowerCase();
if( term != "")
{
	 var searchText = term;

        $('#' + lista + ' div').each(function(){

            var currentLiText = $(this).text(),
                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) !== -1;

            $(this).toggle(showCurrentLi);

        });  
}else{
	// Quando não há nada digitado, mostra a tabela com todos os dados
	 $('#' + lista + ' div').each(function(){

            var currentLiText = $(this).text(),
                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) == -1;

            $(this).toggle(showCurrentLi);

        }); 
}
}

function filtroTableJs(campoBusca, table){
	// Recupera value do campo de busca
    var term=campoBusca.value.toLowerCase();
	if( term != ""){
		// Mostra os TR's que contem o value digitado no campoBusca
		if(table != ""){
			$("#"+table+" tbody>tr").hide();
            $("#"+table+" td").filter(function(){
                   return $(this).text().toLowerCase().indexOf(term ) >-1
            }).parent("tr").show();
		}
	}else{
		// Quando não há nada digitado, mostra a tabela com todos os dados
		$("#"+table+" tbody>tr").show();
	}
}

function calcularTotal(){
	var checkboxServico = document.getElementsByName('valorServico');
	var count = checkboxServico.length;
	var total = 0.0;
	for ( var i = 0; i < count; i++) {
		var trObj = checkboxServico[i];
		if (!trObj) {
			continue;
		}
		total += parseFloat((trObj.value == '' ? 0.0 : trObj.value));
	}
	$('#valorTotalServico').val(total);
	$('#imprimeTotal').html(number_format(total, 2, ',', '.'));
}

function removeLinhaTabela(rowIndex) {
	HTMLUtils.deleteRow('carrinho', rowIndex);
	calcularTotal();
}


function adicionarColecaoItensItens(list) {
	arrayServicoContrato = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(list); 
	var string = "";
	var total = parseFloat(($('#valorTotalServico').val() == '' ? 0.0 : parseFloat($('#valorTotalServico').val())));
	var j = (document.getElementsByName('idServicoContrato').length == 0 ? 1 : (document.getElementsByName('idServicoContrato').length + 1));
    for(var i = 0; i < arrayServicoContrato.length; i++){
    	if(!validaItem(arrayServicoContrato[i].idServicoContrato)) {
    		string += "<tr>";
    		string +="	<td class='span1'>";
    		string +=	"	<input type='hidden' name='idServicoContrato' id='idServicoContrato"+j+"' value='"+arrayServicoContrato[i].idServicoContrato+"' />";
    		string +=	"	<input type='hidden' name='descricao' id='descricao"+j+"' value='"+arrayServicoContrato[i].descricao+"' />";
    		string +=	"	<input type='hidden' name='valorServico' id='valorServico"+j+"' value='"+ (arrayServicoContrato[i].valorServico == '' ? 0.0 : arrayServicoContrato[i].valorServico) +"' />";
    		string +=	"" + arrayServicoContrato[i].idServicoContrato + "";
    		string +=	"</td>";
    		string +=	"<td class='span2'>";
    		string +=	"" + arrayServicoContrato[i].nomeServico + "";
    		string +=	"</td>";
    		string +=	"<td class='span3'>" + arrayServicoContrato[i].descricao + "</td>";
    		string +=	"<td class='span3'>" + arrayServicoContrato[i].observacaoPortal + "</td>";
    		string +=	"<td class='span1'>" + arrayServicoContrato[i].nomeCategoriaServico + "</td>";
    		string +=	"<td class='span1'>" + (arrayServicoContrato[i].valorServico == '' ? 0.0 : arrayServicoContrato[i].valorServico) + "</td>";
    		string +=	"<td class='center' class='span1'>";
    		string +=	"	<a href='javascript:;' onclick='removeLinhaTabela(this.parentNode.parentNode.rowIndex);' class='btn-action glyphicons remove_2 btn-danger'><i></i></a>";
    		string +=	"</td>";
    		string +="	</tr>";
    		total += (arrayServicoContrato[i].valorServico == '' ? 0.0 : parseFloat(arrayServicoContrato[i].valorServico));	
    		j++;
    	}
    }
    $('#carrinho').append(string);
	$('#valorTotalServico').val(total);
	$('#imprimeTotal').html(number_format(total, 2, ',', '.'));
}
function validaItem(idServico) {
	var arrServicos = document.getElementsByName('idServicoContrato');
	var flag = false;
	for ( var i = 0; i < arrServicos.length; i++) {
		if(arrServicos[i].value == idServico) {
			flag = true;
		}
	}
	return flag;
}

function mostrarMensagemSolicitacoes(param) {
	bootbox.alert(param, function(result) {
		deleteTodasLinhasCarrinho();
	});
}
/**
 *	   nota 1: Para 1000.55 retorna com precisão 1 no FF/Opera é 1,000.5, mas no IE é 1,000.6
 *     exemplo 1: number_format(1234.56);
 *     retorno 1: '1,235'
 *     exemplo 2: number_format(1234.56, 2, ',', ' ');
 *     retorno 2: '1 234,56'
 *     exemplo 3: number_format(1234.5678, 2, '.', '');
 *     retorno 3: '1234.57'
 *     exemplo 4: number_format(67, 2, ',', '.');
 *     retorno 4: '67,00'
 *     exemplo 5: number_format(1000);
 *     retorno 5: '1,000'
 *     exemplo 6: number_format(67.311, 2);
 *     retorno 6: '67.31'
 * @param number
 * @param decimals
 * @param dec_point
 * @param thousands_sep
 * @returns
 */
function number_format( number, decimals, dec_point, thousands_sep ) {
    var n = number, prec = decimals;
    n = !isFinite(+n) ? 0 : +n;
    prec = !isFinite(+prec) ? 0 : Math.abs(prec);
    var sep = (typeof thousands_sep == "undefined") ? ',' : thousands_sep;
    var dec = (typeof dec_point == "undefined") ? '.' : dec_point;
 
    var s = (prec > 0) ? n.toFixed(prec) : Math.round(n).toFixed(prec); //fix for IE parseFloat(0.55).toFixed(0) = 0;
 
    var abs = Math.abs(n).toFixed(prec);
    var _, i;
 
    if (abs >= 1000) {
        _ = abs.split(/\D/);
        i = _[0].length % 3 || 3;
 
        _[0] = s.slice(0,i + (n < 0)) +
              _[0].slice(i).replace(/(\d{3})/g, sep+'$1');
 
        s = _.join(dec);
    } else {
        s = s.replace('.', dec);
    }
 
    return s;
}

function deleteTodasLinhasCarrinho() {
	var tabela = document.getElementById('carrinho');
	var count = tabela.rows.length;
	while (count > 1) {
		tabela.deleteRow(count - 1);
		count--;
	}
	calcularTotal();
}

function fecharModalNovaSolicitacao() {
	$('#modal_novaSolicitacao').modal('hide');
	JANELA_AGUARDE_MENU.hide();		 
}

function abrirModalNovaSolicitacao() {
	JANELA_AGUARDE_MENU.hide();
	$('#modal_novaSolicitacao').modal('show');
}

$('#tabOrdensDeServico').click(function(){ 
	
	executaTelaOrdensDeServico(3);
});

function executaTelaOrdensDeServico(id) {
	document.getElementById("iframeInformacoesContrato").src ="pages/informacoesContrato/informacoesContrato.load?iframe=true";
	
	//painel principal
//	$(".panel-header").hide();
	
	//menu principal
//	$("#tst").hide();
	
	//botão de voltar
//	$(".voltar").hide();
//	document.getElementById("iframeInformacoesContrato").style.width ="100%";
//	document.getElementById("iframeInformacoesContrato").style.height ="100%";
	
	

}
//function para limitar campos
function limita(campo){  
	var texto = $('#'+campo).val();
    var tamanho = $('#'+campo).val().length; 
    
    if (tamanho>3000) {
    	document.getElementById(campo).value = texto.substr(0,3000);
    	alert(i18n_message("portal.tamanhoMaximoCampo"));
    }  
    return true;  
}