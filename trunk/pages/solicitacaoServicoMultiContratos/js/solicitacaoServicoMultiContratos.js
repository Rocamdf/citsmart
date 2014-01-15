

jQuery(function($){
	$("#telefonecontato").mask("(999) 9999-9999");
});

function fecharJanelaAguarde(){
	JANELA_AGUARDE_MENU.hide();	
}

function LOOKUP_SOLICITANTE_select(id, desc){
	document.form.solicitante.value = desc;
	document.form.idSolicitante.value = id;
	$('#modal_lookupSolicitante').modal('hide');
	document.form.fireEvent("restoreColaboradorSolicitante");
	calcularSLA();
}

function desabilitaSituacao(){
	var radios = document.getElementsByName('situacao');
	for (var i=0, iLen=radios.length; i<iLen; i++) {
	  radios[i].disabled = true;
	}
	$("div.radio").each(function() {
		$(this).addClass("disabled");
	});					 
}

function LOOKUP_PROBLEMA_select(id, desc){	
	document.form.idProblema.value = id;
	document.form.fireEvent('atualizaGridProblema');
}

function LOOKUP_MUDANCA_select(id, desc){
	document.form.idRequisicaoMudanca.value = id;
	document.form.fireEvent('atualizaGridMudanca');
}

function LOOKUP_ITEMCONFIGURACAO_select(id, desc) {
	document.form.idItemConfiguracao.value = id;
	document.form.fireEvent('atualizaGridItemConfiguracao');
}

function fecharModalProblema(){
	$("#modal_lookupProblema").modal('hide');
}

function fecharModalMudanca(){
	$("#modal_lookupMudanca").modal('hide');
}

function fecharModalItemConfiguracao(){
	$("#modal_lookupItemConfiguracao").modal('hide');
}

function fecharModalListaRelacionarIncidentes(){
	$('#modal_listaRelacionarIncidentes').modal('hide');
}

function abrirTodosCollapse(){
	$("#collapse1").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse1").attr('data-collapse-closed',false);
		$(this).css("height", "auto");
	});
	
	$("#collapse2").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse2").attr('data-collapse-closed',false);
		$(this).css("height", "auto");
	});
	
	$("#collapse3").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse3").attr('data-collapse-closed',false);
		$(this).css("height", "auto");
	});
	
	$("#collapse4").find(".widget-body").each(function() {
		$(this).addClass("in");
		$("#collapse4").attr('data-collapse-closed',false); 
		$(this).css("height", "auto");
	});
}

/**
 * Desmonta a tela de cadastro de solicitação mostrando apenas o collpase3
 * permitindo a reclassificação da solicitação
**/
function visualizaCollapse3(){
	//$("#menu").hide();
	//$("#content").css('cssText', 'margin: 0 !important');
	$(".menu-0, .menu-1").css('cssText', 'display: none;');
	$("#tab1-4").removeClass("active");
	$("#tab3-4").addClass("active");
	$("#divControleInformacaoComplementar2").addClass("inativo");
	/**
	 * Motivo: Comentado pois não permitia que quando se reclassificava um serviço o SLA fosse calculado e mostrado
	 * @author flavio.santana
	 * Data/Hora: 04/12/2013 16:16
	 */
	//$("#fieldDescricao").addClass("inativo");
	//$("#fieldSla").addClass("inativo");
	var count = 1;
	$('.nav-tabs').find('li').each( function() {
		if ($(this).is('li')) {
			if(count==3) {
				$(this).addClass('active');
				count++;
			}else {
				$(this).removeClass('active').addClass("disabled");
				count++;
			}
		}
	});
}
 
/**Autocomplete **/
var completeServico;
var completeSolicitante;
$(document).ready(function() {
	
	$('#servicoBusca').on('click', function(){
		montaParametrosAutocompleteServico();
	});
	
	/*Atribuindo slimScroll a todos o frames */
	//$('.container-fluid').slimScroll({ scrollTo: '0', height: '700px' });
	 
	completeSolicitante = $('#solicitante').autocomplete({ 
		serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
		noCache: true,
		onSelect: function(value, data){
			$('#idSolicitante').val(data);
			$('#solicitante').val(value);
			$('#nomecontato').val(value);
			document.form.fireEvent("restoreColaboradorSolicitante");
			document.form.fireEvent('renderizaHistoricoSolicitacoesEmAndamentoUsuario');

		}
	});
	
	completeServico = $('#servicoBusca').autocomplete({ 
		serviceUrl:'pages/autoCompleteServico/autoCompleteServico.load',
		noCache: true,
		onSelect: function(value, data){
			//document.form.clear();
			$('#idServico').val(data);
			$('#servicoBusca').val(value);
			document.form.fireEvent('verificaImpactoUrgencia');
			document.form.fireEvent('carregaBaseConhecimentoAssoc');
			calcularSLA();
			carregarInformacoesComplementares();
			/*document.form.fireEvent('verificaImpactoUrgencia');*/
		}
	});
	
});
var tipoDemanda;
var contrato;
var categoria;

/**Monta os parametros para a buscas do autocomplete**/
function montaParametrosAutocompleteServico(){
	tipoDemanda = $("#idTipoDemandaServico").val();
 	contrato =  $("#idContrato").val() ;
 	categoria = $("#idCategoriaServico").val();
 	if($("#utilizaCategoriaServico").is(":checked")){
 		completeServico.setOptions({params: {contrato: contrato, tipoDemanda: tipoDemanda, categoria: categoria } });
 	} else {
 		completeServico.setOptions({params: {contrato: contrato, tipoDemanda: tipoDemanda} });
 	}
 	completeSolicitante.setOptions({params: {contrato: contrato} });
}

function listarSolicitacoesServicoEmAndamento(){
	if(document.form.idSolicitacaoServico.value != ""){
		document.formIncidentesRelacionados.idSolicitacaoIncRel.value = document.form.idSolicitacaoServico.value; 
		document.formIncidentesRelacionados.fireEvent("listarSolicitacoesServicoEmAndamento");
	}
}

function restaurarIncidentesRelacionados(){
	if(document.form.idSolicitacaoServico.value != ""){
		document.formIncidentesRelacionados.idSolicitacaoIncRel.value = document.form.idSolicitacaoServico.value; 
		document.formIncidentesRelacionados.fireEvent("restore");
	}
}

function abreModalNovoColaborador(){
	contrato =  $("#idContrato").val();
	document.getElementById('frameCadastroNovoColaborador').src = URL_SISTEMA+'pages/empregado/empregado.load?iframe=true&idContrato='+contrato;
	$('#modal_novoColaborador').modal('show');
}

function startLoading() {
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'none'
		var servicoBusca = document.form.servicoBusca.value;
		if(servicoBusca!= ''){
			document.getElementById('divMini_loading').style.display = 'block';
		} else {
			document.getElementById('divMini_loading').style.display = 'none';
		}
}

function stopLoading() {
	document.getElementById('divMini_loading').style.display = 'none';
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'block'
}

function stopSLAPrevisto(){
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'none';
}

function calcularSLA() {
	/**
	 * Motivo: Adicionado validação de reclassificação pois não permitia que quando se alterava um serviço o SLA fosse calculado e mostrado
	 * @author flavio.santana
	 * Data/Hora: 04/12/2013 16:16
	 */
	if (document.form.reclassicarSolicitacao.value == 'S' || document.form.idSolicitacaoServico.value == null || document.form.idSolicitacaoServico.value == '' || document.form.idSolicitacaoServico.value == 0 ) {
		startLoading();
		var temp = 'var statusDisabledUrgencia = document.form.urgencia.disabled;';
		temp += 'var statusDisabledImpacto = document.form.impacto.disabled;';
		temp += 'document.form.urgencia.disabled = false;';
		temp += 'document.form.impacto.disabled = false;';
		temp += 'document.form.fireEvent("calculaSLA");';
		temp += 'document.form.urgencia.disabled = statusDisabledUrgencia;';
		temp += 'document.form.impacto.disabled = statusDisabledImpacto;';
		//temp += 'document.getElementById("img_carregando").style.display = "none";';
		temp += 'document.getElementById("tdResultadoSLAPrevisto").style.display = "";';
		setTimeout(temp, 1500);
	}
}

function limparCampoBusca(){
	document.form.servicoBusca.value = '';
	document.getElementById('tdResultadoSLAPrevisto').style.display = 'none';
}

function setaValorLookup(obj){
	document.form.idSolicitante.value = '';
	document.form.solicitante.value = '';
	document.form.emailcontato.value = '';
	document.form.telefonecontato.value = '';
	document.form.observacao.value = '';
	document.form.ramal.value = '';
	document.getElementById('idLocalidade').options.length = 0;
	document.form.servicoBusca.value = '';
	document.getElementById('idTipoDemandaServico').options[0].selected = 'selected';
}

function limparEmails(){
    $("#emails").val("");	        
}

function chamaFuncoesContrato(){
	setaValorLookup(this);
	document.form.fireEvent('verificaGrupoExecutor');
	document.form.fireEvent('verificaImpactoUrgencia'); 
	document.form.fireEvent('carregaServicosMulti');
	document.form.fireEvent('carregaUnidade');
	limparEmails();
	document.form.fireEvent('preencherComboLocalidade');
	adicionarIdContratoNaLookup(document.form.idContrato.value);
}

function adicionarIdContratoNaLookup(id){
	document.getElementById('pesqLockupLOOKUP_SOLICITANTE_IDCONTRATO').value = id;
}

function abreLookupCategoriaOcorrencia(){
	$('#modal_lookupCategoriaOcorrencia').modal('show');
}

function abreLookupOrigemOcorrencia(){
	$('#modal_lookupOrigemOcorrencia').modal('show');
}

function LOOKUP_CATEGORIA_OCORRENCIA_select(id, desc) {
	$('#idCategoriaOcorrencia').val(id);				
	$('#nomeCategoriaOcorrencia').val(desc);
	$('#modal_lookupCategoriaOcorrencia').modal('hide');
}

function LOOKUP_ORIGEM_OCORRENCIA_select(id, desc) {
	$('#idOrigemOcorrencia').val(id);				
	$('#nomeOrigemOcorrencia').val(desc);
	$('#modal_lookupOrigemOcorrencia').modal('hide');
}	

function abreModalOcorrencia() {
	document.formOcorrenciaSolicitacao.clear();
	document.formOcorrenciaSolicitacao.idSolicitacaoOcorrencia.value = document.form.idSolicitacaoServico.value; 
	document.formOcorrenciaSolicitacao.fireEvent('load');
	document.getElementById('divRelacaoOcorrencias').innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
	document.formOcorrenciaSolicitacao.fireEvent('listOcorrenciasSituacao');
	//posteriormente trocar pelo serviço carregado
	$('#pesqLockupLOOKUP_OCORRENCIA_SOLICITACAO_IDSOLICITACAOSERVICO').val(1);
	$('#modal_ocorrencia').modal('show');

}

function gravarOcorrencia() {
	document.formOcorrenciaSolicitacao.descricao.value = document.formOcorrenciaSolicitacao.descricao1.value;
	if ($("#idOcorrencia").val() != null && !$("#idOcorrencia").val("") ) {
		alert(i18n_message("gerenciaservico.suspensaosolicitacao.validacao.alteraregistroocorrencia") );
	} else {
		document.formOcorrenciaSolicitacao.save();
		limparCamposOcorrencia();
	}	
}

function limparCampoServiceBusca() {
	document.form.servicoBusca.value = '';
}

function carregaScript(){
	document.form.idServico.disabled = false;
	document.getElementById("divScript").innerHTML = "<i18n:message key='citcorpore.comum.aguardecarregando' />";
	document.form.fireEvent('carregaBaseConhecimentoAssoc');
} 

function carregaSolicitacoesAbertasParaMesmoSolicitante(){
	/*inicializarTemporizador();*/
	document.form.fireEvent("renderizaHistoricoSolicitacoesEmAndamentoUsuario");
}

function carregaFlagGerenciamento(){
	document.form.fireEvent("carregaFlagGerenciamento");
}

function pesquisaSolicitacoesAbertasParaMesmoSolicitante(){
	/*inicializarTemporizador();*/
	document.form.situacaoFiltroSolicitante.value = document.getElementById('situacaoTblResumo2').value
	document.form.buscaFiltroSolicitante.value = document.getElementById('campoBuscaTblResumo2').value
	document.form.fireEvent('renderizaHistoricoSolicitacoesEmAndamentoUsuario')
}

function detalheSolicitacao(parametro){
	var dadosSolicitacao;
	var divDetalhe;
	
	dadosSolicitacao = parametro.split("#");

	divDetalhe = '<div class="span4" id="informacoesUsuario">';
	divDetalhe += '<div class="well margin-none">';
	divDetalhe += '<address class="margin-none">';
	divDetalhe += '<h2>'+dadosSolicitacao[1]+'</h2>';
	divDetalhe += '<abbr title="Work email">'+i18n_message("visao.contrato")+':</abbr> '+dadosSolicitacao[0]+'<br> ';
	divDetalhe += '<abbr title="Work email">Email:</abbr> <a href="mailto:'+dadosSolicitacao[2]+'"> '+dadosSolicitacao[2]+'</a><br> ';
	divDetalhe += '<abbr title="Work Phone">'+i18n_message("lookup.telefone")+':</abbr> '+dadosSolicitacao[3]+'<br>';
	if(dadosSolicitacao[4]=='Requisição'){	
	divDetalhe += '<abbr title="Work Fax">'+i18n_message("portal.carrinho.tipoSolicitacao")+':</abbr>'+i18n_message("requisicaoProduto.requisicao")+'<br>';
	} else if (dadosSolicitacao[4]=='Incidente'){
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("portal.carrinho.tipoSolicitacao")+':</abbr> '+i18n_message("requisitosla.incidente")+'<br>';		
	} else{
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("portal.carrinho.tipoSolicitacao")+':</abbr> '+dadosSolicitacao[4]+'<br>';	
	}
	divDetalhe += '<abbr title="Work Fax">'+i18n_message("problema.servico")+':</abbr> '+dadosSolicitacao[5]+'<br>';		
	if(dadosSolicitacao[6] == 'EmAndamento'){
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("projeto.situacao")+':</abbr> '+i18n_message("solicitacaoServico.situacao.EmAndamento")+'<br>';
	} else if (dadosSolicitacao[6]=='Fechada'){
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("projeto.situacao")+':</abbr> '+i18n_message("solicitacaoServico.situacao.Fechada")+'<br>';	
	} else {
		divDetalhe += '<abbr title="Work Fax">'+i18n_message("projeto.situacao")+':</abbr> '+dadosSolicitacao[6]+'<br>';	
	}
	divDetalhe += '</div>';
		
	document.getElementById('detalheSolicitacaoServico').innerHTML = divDetalhe;
	$("#modal_detalheSolicitacaoServico").modal("show");
}

//INICIALIZA O TEMPLATE - INFORMAÇÕES COMPLEMENTARES
function carregarInformacoesComplementares() {
    try{
        /*document.getElementById('divInformacoesComplementares').style.display = 'block';*/
    	$('#divControleInformacaoComplementar1').switchClass( "inativo", "ativo", null );
    	$('#divControleInformacaoComplementar2').switchClass( "inativo", "ativo", null );
    	$('#divInformacoesComplementares').switchClass( "ativo", "inativo", null );
    	$('#fraInformacoesComplementares').switchClass( "ativo", "inativo", null );
    	
        window.frames["fraInformacoesComplementares"].document.write("");
        window.frames["fraInformacoesComplementares"].document.write("<font color='red'><b><i18n:message key='citcorpore.comum.aguardecarregando' /></b></font>");
    }catch (e) {
    }       
    document.form.fireEvent('carregaInformacoesComplementares');
}
function exibirInformacoesComplementares(url) {
    if (url != '') {
        JANELA_AGUARDE_MENU.show();
        /*document.getElementById('divInformacoesComplementares').style.display = 'block';*/
        $('#divControleInformacaoComplementar1').switchClass( "ativo", "inativo", null );
        $('#divControleInformacaoComplementar2').switchClass( "ativo", "inativo", null );
        $('#divInformacoesComplementares').switchClass( "inativo", "ativo", null );
    	$('#fraInformacoesComplementares').switchClass( "inativo", "ativo", null );
        document.getElementById('fraInformacoesComplementares').src = url;
    }else{
        try{
            window.frames["fraInformacoesComplementares"].document.write("");
        }catch (e) {
        }       
        document.getElementById('divInformacoesComplementares').style.display = 'none';
    } 
}   

function validarInformacoesComplementares() {
	if (window.frames["fraInformacoesComplementares"]){
		try{
    		return window.frames["fraInformacoesComplementares"].validar();
		}catch(e){
			return true;
		}
	}else{
		return true;
	}
}   

function escondeJanelaAguarde() {
    JANELA_AGUARDE_MENU.hide();
}

function destaqueScript(){
	/* $('#divMenuScript').effect("highlight", {}, 1000000);*/
	$('#divMenuScript').addClass('ui-state-highlight');
}

function destaqueSolicitacaoMesmoUsuario(){
	/* $('#divMenuSolicitacao').effect("highlight", {}, 1000000);*/
	$('#divMenuSolicitacao').addClass('ui-state-error');
}


function adicionarRegistroExecucao(){
	if(document.getElementById('controleRegistroExecucao').style.display == 'none'){
		$('#btnAdicionarRegistroExecucao').switchClass("circle_plus", "circle_minus", null);
		document.getElementById('controleRegistroExecucao').style.display = 'block'
	}else{
		$('#btnAdicionarRegistroExecucao').switchClass("circle_minus", "circle_plus", null);
		document.getElementById('controleRegistroExecucao').style.display = 'none'
	}
}

function executa_miniLoading(){
	document.getElementById('divMini_loading').style.display = 'block';
	
}
function finaliza_miniLoading(){
	document.getElementById("divMini_loading").style.display = 'none';

}

function fecharAddSolicitante(){
	$('#modal_novoColaborador').modal('hide');
}

function abrirModalBaseConhecimento(){
	document.getElementById('frameBaseConhecimento').src = URL_SISTEMA+'baseConhecimentoView/baseConhecimentoView.load?iframe=true';
	$('#modal_baseConhecimento').modal('show');
}

function abrirModalPesquisaItemConfiguracao(){
	document.getElementById('framePesquisaItemConfiguracao').src = URL_SISTEMA+'pages/pesquisaItemConfiguracao/pesquisaItemConfiguracao.load?iframe=true';
	$('#modal_pesquisaItemConfiguracao').modal('show');
}

function abrirModalAgenda(){
	document.getElementById('frameAgendaAtvPeriodicas').src = URL_SISTEMA+'pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar=true';
	$('#modal_agenda').modal('show');
}

function abrirModalCadastroCategoriaOcorrencia(){
	document.getElementById('frameCadastroCategoriaOcorrencia').src = URL_SISTEMA+'pages/categoriaOcorrencia/categoriaOcorrencia.load?iframe=true';
	$('#modal_cadastroCategoriaOcorrencia').modal('show');
}

function abrirModalCadastroOrigemOcorrencia(){
	document.getElementById('frameCadastroOrigemOcorrencia').src = URL_SISTEMA+'pages/origemOcorrencia/origemOcorrencia.load?iframe=true';
	$('#modal_cadastroOrigemOcorrencia').modal('show');
}

function abrirModalProblema(){
	$('#conteudoiframeEditarCadastrarProblema').html('<iframe src="about:blank" width="99%" id="iframeEditarCadastrarProblema" height="550" class="iframeSemBorda"></iframe>');
}

function abrirModalMudanca(){
	$('#conteudoiframeEditarCadastrarMudanca').html('<iframe src="about:blank" width="99%" id="iframeEditarCadastrarMudanca" height="550" class="iframeSemBorda"></iframe>');
}

function abrirModalItemConfiguracao(){
	 $('#conteudoiframeInformacaoItemConfiguracao').html('<iframe src="about:blank" width="99%" id="iframeInformacaoItemConfiguracao" height="530" class="iframeSemBorda"></iframe>'); 

}

function modalCadastroSolicitacaoServico(){
	document.form.fireEvent('abrirListaDeSubSolicitacoes');
	$('#modal_solicitacaofilha').modal('show');
}


$(function() {
	var offset = $("#menu").offset();
	var topPadding = 15;
	$(window).scroll(function() {
		if ($(window).scrollTop() > offset.top) {
			$("#menu").stop().animate({
				marginTop: $(window).scrollTop() - offset.top + topPadding
			});
		} else {
			$("#menu").stop().animate({
				marginTop: 0
			});
		};
	});
});

	// propose username by combining first- and lastname
	$("#idContrato").focus(function() {
		var idContrato = $("#idContrato").val();
		var idServico = $("#idServico").val();
		if(idContrato && idServico && !this.value) {
			this.value = idContrato + "." + idServico;
		}
	});

	//code to hide topic selection, disable for demo
	var newsletter = $("#newsletter");
	// newsletter topics are optional, hide at first
	var inital = newsletter.is(":checked");
	var topics = $("#newsletter_topics")[inital ? "removeClass" : "addClass"]("gray");
	var topicInputs = topics.find("input").attr("disabled", !inital);
	// show when newsletter is checked
	newsletter.click(function() {
		topics[this.checked ? "removeClass" : "addClass"]("gray");
		topicInputs.attr("disabled", !this.checked);
	});

exibeIconesProblema = function(row, obj){
	var id = obj.idProblema;
    obj.sequenciaOS = row.rowIndex; 
    row.cells[2].innerHTML = '<a class="btn-action glyphicons pencil btn-success" onclick="carregarProblema('+ row.rowIndex + ', '+id+')"><i></i></a>  '
    row.cells[2].innerHTML += '<a class="btn-action glyphicons remove_2 btn-danger" onclick="excluiProblema(this.parentNode.parentNode.rowIndex,this)"><i></i></a>'
}

exibeIconesMudanca = function(row, obj){
	var id = obj.idRequisicaoMudanca;
    obj.sequenciaOS = row.rowIndex; 
      row.cells[2].innerHTML = '<a class="btn-action glyphicons pencil btn-success" onclick="carregarMudanca('+ row.rowIndex + ', '+id+')"><i></i></a>  '
      row.cells[2].innerHTML += '<a class="btn-action glyphicons remove_2 btn-danger" onclick="excluiMudanca(this.parentNode.parentNode.rowIndex,this)"><i></i></a>';
}

exibeIconesBaseConhecimento = function(row, obj){
	var id = obj.idBaseConhecimento;
    obj.sequenciaOS = row.rowIndex; 
    row.cells[2].innerHTML = '<a  class="btn-action glyphicons remove_2 btn-danger" onclick="excluiBaseConhecimento(this.parentNode.parentNode.rowIndex,this)"><i></i></a>';
}

exibeIconesIC = function(row, obj){
	var id = obj.idItemConfiguracao;
    obj.sequenciaIC = row.rowIndex; 
    row.cells[3].innerHTML = '<a  class="btn-action glyphicons remove_2 btn-danger" onclick="excluiIC(this.parentNode.parentNode.rowIndex,this)"><i></i></a>';
    
	if(obj.idItemConfiguracaoPai == ""){
		/*row.cells[2].innerHTML = '<img src="../template_new/images/icons/small/grey/graph.png" border="0" onclick="popupAtivos( '+ id + ')" style="cursor:pointer"/>';*/
		row.cells[2].innerHTML = '<a  class="btn-action glyphicons circle_info btn-default" onclick="popupAtivos( '+ id + ')"><i></i></a>'
		
	}
}

excluiBaseConhecimento = function(indice) {
	if (indice > 0 && confirm('Confirma exclusão?')) {
		HTMLUtils.deleteRow('tblBaseConhecimento', indice);
	}
}
excluiProblema = function(indice) {
	if (indice > 0 && confirm('Confirma exclusão?')) {
		HTMLUtils.deleteRow('tblProblema', indice);
	}
}

excluiMudanca = function(indice) {
	if (indice > 0 && confirm('Confirma exclusão?')) {
		HTMLUtils.deleteRow('tblMudanca', indice);
	}
}
excluiLiberacao = function(indice) {
	if (indice > 0 && confirm('Confirma exclusão?')) {
		HTMLUtils.deleteRow('tblLiberacao', indice);
	}
}

excluiIC = function(indice) {
	if (indice > 0 && confirm('Confirma exclusão?')) {
		HTMLUtils.deleteRow('tblIC', indice);
	}
}

excluiSolicitacao = function(indice) {
	if (indice > 0 && confirm('Confirma exclusão?')) {
		HTMLUtils.deleteRow('tblSolicitacao', indice);
	}
}
function buscaProblema(row, object){
	carregarProblema(row, object);
}
function buscaMudanca(row, object){
	var obj = object.idRequisicaoMudanca;
	carregarMudanca(row, obj);
}
function carregarProblema(row, obj){
	var idProblema = obj;
	document.getElementById('iframeEditarCadastrarProblema').src = URL_SISTEMA+"pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
	$("#modal_editarCadastrarProblema").modal("show");
}


/**
Funcao que faz referencia ao botão fechar da tela de problema após fechar um problema ira fechar modal em solicitacao servico.
* 
* @author maycon.fernandes
* @since 30/10/2013 15:35
*/
function fecharProblema(){
	$("#modal_editarCadastrarProblema").modal("hide");
}

function fecharFrameProblema(){
	$("#modal_editarCadastrarProblema").modal("hide");
}

/**
Funcao que faz referencia ao botão fechar da tela de mudanca após fechar um mudanca ira fechar modal em solicitacao servico.
* 
* @author maycon.fernandes
* @since 30/10/2013 15:35
*/
function fecharMudanca(){
	$("#modal_editarCadastrarMudanca").modal("hide");
}

/**
Alterado para apenas visualizar, a rotina anterior estava permitindo alterar com isso ele nao estava andando junto com o fluxo.
* 
* @author maycon.fernandes
* @since 30/10/2013 15:35
*/
function carregarMudanca(row, obj){
	var idMudanca = obj;
	document.getElementById('iframeEditarCadastrarMudanca').src = URL_SISTEMA+"pages/requisicaoMudanca/requisicaoMudanca.load?iframe=true&idRequisicaoMudanca="+idMudanca+"&escalar=N&alterarSituacao=N&editar=N";
	$("#modal_editarCadastrarMudanca").modal("show");
}

function chamaPopupCadastroSolicitacaoServico(){
	var idItem = document.getElementById("idSolicitacaoServico").value;
	var idContrato = document.getElementById("idContrato").value;	
	document.getElementById('frameCadastroNovaSolicitacaoFilho').src = URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?iframe=true&idSolicitacaoRelacionada='+idItem+'&idContrato='+idContrato;
	$("#modal_editarCadastrarSolicitacaoFilha").modal("show");
}

function cadastrarProblema(){
	document.getElementById('iframeEditarCadastrarProblema').src = URL_SISTEMA+"pages/problema/problema.load?iframe=true";
	$("#modal_editarCadastrarProblema").modal("show");
}
function cadastrarMudanca(){
	document.getElementById('iframeEditarCadastrarMudanca').src = URL_SISTEMA+"pages/requisicaoMudanca/requisicaoMudanca.load?iframe=true";
	$("#modal_editarCadastrarMudanca").modal("show");
}
/*function abreMudanca(row, object){
	popupAbreMudanca.abrePopupParms('requisicaoMudanca', '', 'idRequisicaoMudanca=' + object.idRequisicaoMudanca);
}*/

function popupAtivos(id){
	var idItem = id;
		document.getElementById('iframeInformacaoItemConfiguracao').src = URL_SISTEMA+'pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=' + idItem;
		$("#modal_informacaoItemConfiguracao").modal("show");
		calcularSLA();
}

function selectedItemConfiguracao(idItemCfg){
	document.form.idItemConfiguracao.value = idItemCfg;
	serializaTabelaIcParaImpactoUrgencia();
	document.form.fireEvent("restoreItemConfiguracao");
}

function serializaTabelaIcParaImpactoUrgencia(){
	var objsIC = HTMLUtils.getObjectsByTableId('tblIC');
	if (objsIC != null) {
		document.form.colItensIC_Serialize.value = ObjectUtils.serializeObjects(objsIC);
	}
}

function gravarSolucaoRespostaEmBaseConhecimento(){
	if($("#gravaSolucaoRespostaBaseConhecimento").is(":checked")){
    		$('#divTituloSolucaoRespostaBaseConhecimento').switchClass( "inativo", "ativo", null );
    		$("#tituloSolucaoRespostaBaseConhecimento").attr('required',true);
		}
	else{
			$('#divTituloSolucaoRespostaBaseConhecimento').switchClass( "ativo", "inativo", null );
			$("#tituloSolucaoRespostaBaseConhecimento").attr('required',false); 
			$("#tituloSolucaoRespostaBaseConhecimento").val("");
		 }
}

function pesquisarSolucao(){
	document.form.fireEvent('pesquisaBaseConhecimento');
}

function LOOKUP_BASECONHECIMENTO_select(id, desc){
	document.form.idItemBaseConhecimento.value = id;
	document.form.fireEvent('atualizaGridBaseConhecimento'); 
	$('#modal_lookupBaseConhecimento').modal('hide')
}

function chamaPopupCadastroOrigem(){
	if (document.form.idContrato.value == ''){
		alert(i18n_message("solicitacaoservico.validacao.contrato"));
		return;
	}
	var idContrato = 0;
	try{
		idContrato = document.form.idContrato.value;
	}catch(e){
	}
	document.getElementById('frameExibirOrigem').src = URL_SISTEMA+'pages/origemAtendimento/origemAtendimento.load?iframe=true&idContrato='+idContrato;
	$('#modal_origem').modal('show');
}

function setValorTextArea(id, texto) {
	//comando para atribuir valores das textareas
	$(id).data("wysihtml5").editor.setValue(texto);
}

function desabilitaTextAreaWysi(id) {
	//comando para tornar textarea desabilitado	
	$(id).data("wysihtml5").editor.composer.element.setAttribute('contenteditable', false);
	//comando para remover botões de formatação do textarea
	$(id +"-wysihtml5-toolbar").remove();
}

//Mário Júnior -  23/10/2013 -  16:27 - Inseri tarefa na grid
function informaNumeroSolicitacao(numero, responsavel, tarefa){
	document.getElementById('tituloSolicitacao').innerHTML = 
		'<label class="strong">Nº</label><p>&nbsp;' + numero + '</p><label  class="strong">'+i18n_message("solicitacaoServico.responsavelatual.desc")+'</label><p><i18n:message key="solicitacaoServico.solicitacaonumero" />&nbsp;' + responsavel + '</p><label  class="strong">'+i18n_message("solicitacaoServico.tarefaatual.desc")+'</label><p><i18n:message key="solicitacaoServico.tarefaatual.desc" />&nbsp;' + tarefa + '</p>';   
}

function bloqueiaBotoesVisualizacao(){
	$('.btn').attr('disabled', 'disabled').addClass('disabled');
	$('#btnPesquisaSolUsuario').removeAttr( "disabled" ).removeClass('disabled');
	$('#tabCadastroOcorrencia').addClass('inativo');
	document.getElementById('divBtIncidentesRelacionados').style.display = 'none';
	document.getElementById('btnAdduploadAnexos').style.display = 'none';
	//retira disabled dos botoes fechar das Modais
	$('.modal-footer').find('a').each( function() {
		$('a').removeAttr( "disabled" ).removeClass('disabled');
	});

}

function preencherComboOrigem() {
	document.form.fireEvent('chamaComboOrigem');
}

function abreVISBASECONHECIMENTO(id){
	JANELA_AGUARDE_MENU.show();
	document.getElementById('visualizaProblemaBaseConhecimento').src = URL_SISTEMA+'baseConhecimentoView/baseConhecimentoView.load?iframe=true&idBaseConhecimento='+id;
	$('#modal_visualizaProblemaBaseConhecimento').modal('show');
	
}

function contadorClicks(idBaseConhecimento){
	document.form.idBaseConhecimento.value = idBaseConhecimento;
	document.form.fireEvent('contadorDeClicks');	
}	

function validaCampoExecutanteNullparaVazio(){
	$('#solicitante').val("");
}

function mostrarPassoQuatroExecucaoTarefa(){
	$('.wizard').each(function()
	{
		if ($(this).is('#rootwizard'))
			$(this).bootstrapWizard('show', 2);
	});
}

function mostrarComboServico(){
	var idTipoDemandaServico = $("#idTipoDemandaServico").val();
	var idCategoriaServico = $("#idCategoriaServico").val();
	if (idTipoDemandaServico != '') {
		JANELA_AGUARDE_MENU.show();
		if($("#utilizaCategoriaServico").is(":checked")){
			document.form.idCategoriaServico.value = idCategoriaServico;
		} else{
			document.form.idCategoriaServico.value = '';
		}
		$('#filtroTableServicos').val("");
		$('#filtroTableServicos').focus();
		document.form.fireEvent('listarServicosPorContratoDemandaCategoria');
		$('#modal_infoServicos').modal('show');
	}else{
		alert(i18n_message("citcorpore.comum.informeTipoSolicitacao"));
		$('#ulWizard li:eq(2) a').tab('show');
		document.form.idTipoDemandaServico.focus();
	}
}

function marcarChecksEmail(){
	document.form.fireEvent('marcarChecksEmail')
}

function selecionarServico(row, obj){
	JANELA_AGUARDE_MENU.show()
	$('#idServico').val(obj.idServico);
	$('#servicoBusca').val(obj.nomeServico);
	document.form.fireEvent('verificaImpactoUrgencia');
	document.form.fireEvent('carregaBaseConhecimentoAssoc');
	calcularSLA();
	carregarInformacoesComplementares();
	JANELA_AGUARDE_MENU.hide()
	$('#modal_infoServicos').modal('hide');
	
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

function somenteNumero(e){
    var tecla=(window.event)?event.keyCode:e.which;   
    if((tecla>47 && tecla<58)) return true;
    else{
    	if (tecla==8 || tecla==0) return true;
	else  return false;
    }
}

/*function limparServico(){
	$('#servicoBusca').val('');
}
*/
function limparServico(){
	$('#servicoBusca').val('');
	calcularSLA();
	$( "#idServico" ).val( '' );
}

/* Desenvolvedor: Riubbe Oliveira - Data: 23/10/2013 - Horário: 10:46 - ID Citsmart: 121539 
 * 
 * Motivo/Comentário: Função para ocultar divInformacoesComplementares caso seja um questionario 
 * isso se faz necessário porque ao salvar o questionário, a função getObjetoSeriarizado
 * da um submit e um reload dentro da div mostrando a pagina inicial do citsmart   
 * */
function ocultaInfoComplSeQuestionario(link){
	var str = new String(link);
	var res = str.search("visualizacaoQuestionario.load");
	if(res != -1){
		$('#divInformacoesComplementares').css('cssText','display:none !important');
	}
}

function incluiInfoComplSeQuestionario(link){
	var str = new String(link);
	var res = str.search("visualizacaoQuestionario.load");
	if(res != -1){
		$('#divInformacoesComplementares').css('cssText','display:block !important');
	}
}

/* Desenvolvedor: Mário Júnior - Data: 28/10/2013 - Horário: 15:10
 * 
 * Motivo/Comentário: Limpar campos de ocorrencia, estava limpando o form inteiro, e não pode, pois some também o idSolicitaçãoServico
 * */
function limparCamposOcorrencia(){
	$('#nomeCategoriaOcorrencia').val('');
	$("#nomeOrigemOcorrencia").val('');
	$("#tempoGasto").val('');
	$("#registradopor").val( '' );
	$("#descricao1").val( '' );
	$("#ocorrencia").val( '' );
	$("#informacoesContato").val( '' );
}

function validaForm(){
	var idContrato = $("#idContrato").val();
	if (idContrato == '') {
		alert(i18n_message("contrato.contrato") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard a:first').tab('show');
		document.form.idContrato.focus();
		return;
	}
	var idOrigem = $("#idOrigem").val();
	if (idOrigem == '') {
		alert(i18n_message("citcorpore.comum.origem") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.idOrigem.focus();
		return;
	}
	var servicoBusca = $("#solicitante").val();
	if (servicoBusca == '') {
		alert(i18n_message("solicitacaoServico.solicitante") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.solicitante.focus();
		return;
	}
	var emailcontato = $("#emailcontato").val();
	if (emailcontato == '') {
		alert(i18n_message("citcorpore.comum.email") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.emailcontato.focus();
		return;
	}
	if (emailcontato != ''){ 
		if (!/\b[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}\b/.test(emailcontato) ) {
			alert(i18n_message("citcorpore.validacao.emailInvalido"));
			$('#ulWizard li:eq(1) a').tab('show');
			document.form.emailcontato.focus();
			return;
		}
	}
	var idUnidade = $("#idUnidade").val();
	if (idUnidade == '') {
		alert(i18n_message("unidade.unidade") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(1) a').tab('show');
		document.form.idUnidade.focus();
		return;
	}
	var idTipoDemandaServico = $("#idTipoDemandaServico").val();
	if (idTipoDemandaServico == '') {
		alert(i18n_message("solicitacaoServico.tipo") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(2) a').tab('show');
		document.form.idTipoDemandaServico.focus();
		return;
	}
	var servicoBusca = $("#servicoBusca").val();
	if (servicoBusca == '') {
		alert(i18n_message("citcorpore.comum.servico") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(2) a').tab('show');
		document.form.servicoBusca.focus();
		return;
	}
	var descricao = $("#descricao").val();
	if (descricao == '') {
		alert(i18n_message("solicitacaoServico.descricao") + ": "
				+ i18n_message("citcorpore.comum.campo_obrigatorio"));
		$('#ulWizard li:eq(2) a').tab('show');
		$("#descricao").data("wysihtml5").editor.focus();
		return;
	}
	if($("#divUrgencia").is(":visible")){
		var urgencia = $("#urgencia").val();
		if (urgencia == '') {
			alert(i18n_message("solicitacaoServico.urgencia") + ": "
					+ i18n_message("citcorpore.comum.campo_obrigatorio"));
			$('#ulWizard li:eq(2) a').tab('show');
			document.form.urgencia.focus();
			return;
		}
	}
	if($("#divImpacto").is(":visible")){
		var impacto = $("#impacto").val();
		if (impacto == '') {
			alert(i18n_message("solicitacaoServico.impacto") + ": "
					+ i18n_message("citcorpore.comum.campo_obrigatorio"));
			$('#ulWizard li:eq(2) a').tab('show');
			document.form.impacto.focus();
			return;
		}			
	}
	
	return true;
}

function gravarSemEnter(evt) {
	var key_code = evt.keyCode  ? evt.keyCode  : evt.charCode ? evt.charCode : evt.which ? evt.which : void 0;
	if (key_code == 13) {
		evento = key_code;
		fecharModalNovaSolicitacao();
		return;
	}else{
		gravar();
	}
}
function scrolls() {	
	var alturaDiv = $('#divInformacoesComplementares').height();
	var altura = alturaDiv / 2;
	$('html,body').animate({scrollTop: altura},'slow');
}



function atualizarLista(){
	parent.atualizarLista();
}
/* Desenvolvedor: Riubbe Oliveira - Data: 23/10/2013 - Horário: 11:59 - ID Citsmart: 121539 
 * 
 * Motivo/Comentário: Função que serializa as informações dos templates e questionarios.
 * foi incluido a chamada ao metodo ocultaInfoComplSeQuestionario(link) para verificar
 * e tratar a div quando for um questionário.
 * */
function informacoesComplementaresSerialize(){
	var informacoesComplementares_serialize = '';
	var link = $('#fraInformacoesComplementares').attr('src');
		try {
			informacoesComplementares_serialize = window.frames["fraInformacoesComplementares"].getObjetoSerializado();
		} catch (e) {
	}
	document.form.informacoesComplementares_serialize.value = informacoesComplementares_serialize;
	ocultaInfoComplSeQuestionario(link);	
}

function habilitaEmail(){
	if (document.form.enviaEmailCriacao.disabled == true) {
		document.form.enviaEmailCriacao.disabled = false;
	}
	if (document.form.enviaEmailFinalizacao.disabled == true) {
		document.form.enviaEmailFinalizacao.disabled = false;
	}
	if (document.form.enviaEmailAcoes.disabled == true) {
		document.form.enviaEmailAcoes.disabled = false;
	}
}

function gravarfg() {
	seTodosCamposObrigatorioPreenchidos();
}
function fechaModalAnexo(){
	document.form.fireEvent("flagGerenciamentoClose");
}

function carregaCategoriaServico(){	
	if($("#utilizaCategoriaServico").is(":checked")){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent("criarComboCategoriaServico");
	} else {
		document.getElementById("divCategoriaServico").style.display = 'none';
	}
}

function fecharComEnter(evt){
	var key_code = evt.keyCode  ? evt.keyCode  : evt.charCode ? evt.charCode : evt.which ? evt.which : void 0;
	if (key_code == 13) {
		evento = key_code;
		fecharModalNovaSolicitacao();
		return;
	}
}

/*Criado para quando usar as setas na busca de seviço, não alterar o load do SLA*/
function eventoStartLoading(evt){
	if( evt.keyCode != 37 && evt.keyCode != 38 && evt.keyCode != 39 && evt.keyCode != 40){
		startLoading();
	}
}
