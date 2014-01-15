addEvent(window, "load", load, false);

function renderizarGraficos() {
	JANELA_AGUARDE_MENU.show()
	document.formGerenciamento.fireEvent('renderizarGraficos');
}

function renderizarResumoSolicitacoes() {
	JANELA_AGUARDE_MENU.show()
	document.form.fireEvent('exibirResumoSolicitacoes');
}   

function GrupoQtde(){
      		this.id = '';
      		this.qtde = 0;
      }
   
   function trocaBarra(txt){
		var x = new String(txt);
		x = x.replace(/{{BARRA}}/g,'\\');
		return x;
	}

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
    
   	var attendeeUrl =  URL_SISTEMA+'pages/autoCompleteSolicitante/autoCompleteSolicitante.load';

	$('#idSolicitante').select2(
	{
	    //Does the user have to enter any data before sending the ajax request
	    minimumInputLength: 0,            
	    allowClear: true,
	    placeholder: i18n_message("citcorpore.comum.selecione"),
	    ajax: {
	        //How long the user has to pause their typing before sending the next request
	        quietMillis: 150,
	        //The url of the json service
	        url: attendeeUrl,
	        dataType: 'json',
	        //Our search term and what page we are on
	        data: function (term) {
	        	var idContratoAutoComSolicitante = $("#idContrato").val();
	        	if (idContratoAutoComSolicitante == -1) {
	        		alert(i18n_message("citcorpore.comum.selecioneContrato"));
	        		$("#idSolicitante").select2("close");
	        		$("#idContrato").focus();
	        		return;
	        	}
	            return {
	            	q : term,
	                query: term, 
	                contrato : $("#idContrato").val(),
	                colection : true
	            };
	        },
	        results: function (data, page) {
	            //Used to determine whether or not there are more results available,
	            //and if requests for more data should be sent in the infinite scrolling
	            return { results: data };
	        }
	    },
        formatResult: function(exercise) {
        	return exercise.nome;
    	},
    	formatSelection: function(exercise) {
    		 return exercise.nome;
    	},
    	id: function(exercise) {
        	return exercise.idEmpregado;
    	} 
	});
	
	$('#idResponsavelAtual').select2(
	{
		//Does the user have to enter any data before sending the ajax request
	    minimumInputLength: 0,            
	    allowClear: true,
	    placeholder: i18n_message("citcorpore.comum.selecione"),
	    ajax: {
	        //How long the user has to pause their typing before sending the next request
	        quietMillis: 150,
	        //The url of the json service
	        url: attendeeUrl,
	        dataType: 'json',
	        //Our search term and what page we are on
	        data: function (term) {
	        	var idContratoAutoComResponsavel = $("#idContrato").val();
	        	if (idContratoAutoComResponsavel == -1) {
	        		alert(i18n_message("citcorpore.comum.selecioneContrato"));
	        		$("#idResponsavelAtual").select2("close");
	        		$("#idContrato").focus();
	        		return;
	        	}
	            return {
	            	q : term,
	                query: term, 
	                contrato : $("#idContrato").val(),
	                colection : true
	            };
	        },
	        results: function (data, page) {
	            //Used to determine whether or not there are more results available,
	            //and if requests for more data should be sent in the infinite scrolling
	            return { results: data };
	        }
	    },
        formatResult: function(exercise) {
        	return exercise.nome;
    	},
    	formatSelection: function(exercise) {
    		 return exercise.nome;
    	},
    	id: function(exercise) {
        	return exercise.idEmpregado;
    	} 
	});
	
	$('#tarefaAtual').select2(
	{
	    //Does the user have to enter any data before sending the ajax request
	    minimumInputLength: 0,            
	    allowClear: true,
	    placeholder: i18n_message("citcorpore.comum.selecione"),
	    ajax: {
	        //How long the user has to pause their typing before sending the next request
	        quietMillis: 150,
	        //The url of the json service
	        url: "../pages/autoCompleteTarefaAtual/autoCompleteTarefaAtual.load",
	        dataType: 'json',
	        //Our search term and what page we are on
	        data: function (term) {
	            return {
	                query: term, 
	            };
	        },
	        results: function (data, page) {
	            //Used to determine whether or not there are more results available,
	            //and if requests for more data should be sent in the infinite scrolling
	            return { results: data };
	        }
	    },
        formatResult: function(exercise) {
        	return exercise.documentacao;
    	},
    	formatSelection: function(exercise) {
    		 return exercise.documentacao;
    	},
    	id: function(exercise) {
        	return exercise.documentacao;
    	} 
	});
	
	$(document).on('change', '#idContrato', function() {
			$('#idSolicitante').empty()
			$('#idSolicitante').select2('data', [{}], false);
			$('#idResponsavelAtual').empty()
			$('#idResponsavelAtual').select2('data', [{}], false);
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
	document.formGerenciamento.afterRestore = function() {
		
	}
}

/**
 * Motivo: Criando flag de atualização 
 * Autor: flavio.santana
 * Data/Hora: 13/11/2013 15:56
 */
var flagModalAtualizacao = false;

$(function(){
	
	$('.modal').on('shown', function() {
		 flagModalAtualizacao = true;
	});
	
	$('.modal').on('hidden', function () {
		 flagModalAtualizacao = false;
	});

});

function janelaAguarde(){
	JANELA_AGUARDE_MENU.show();
}
function modalNovaSolicitacaoServico(){
	janelaAguarde();
	document.getElementById('frameNovaSolicitacao').src =  URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load';
	$('#modal_novaSolicitacao').modal('show');
}

//Mário Júnior -  23/10/2013 -  16:27 - Inseri idTarefa como parametro.
function visualizarSolicitacao(idSolicitacaoServico, idTarefa) {
	document.getElementById('frameNovaSolicitacao').src =  URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico='+idSolicitacaoServico+'&idTarefa='+idTarefa+'&escalar=N&alterarSituacao=N&editar=N&acaoFluxo=V';
	//window.frames['frameNovaSolicitacao'].bloquearForm();
	$('#modal_novaSolicitacao').modal('show');
}

function prepararMudancaSLA(idTarefa, idSolicitacao) {
	document.getElementById('frameAlterarSLA').src =  URL_SISTEMA+'pages/mudarSLA/mudarSLA.load?iframe=true&idSolicitacaoServico='+idSolicitacao;
	$('#modal_alterarSLA').modal('show');
}

function agendaAtividade(idSolicitacao) {
	document.getElementById('frameAgendarAtividade').src =  URL_SISTEMA+'pages/agendarAtividade/agendarAtividade.load?iframe=true&idSolicitacaoServico='+idSolicitacao;
	$('#modal_agendarAtividade').modal('show');
}

function exibirDelegacaoTarefa(idTarefa, idSolicitacao, nomeTarefa) {
	document.getElementById('frameExibirDelegacaoTarefa').src =  URL_SISTEMA+'pages/delegacaoTarefa/delegacaoTarefa.load?iframe=true&idSolicitacaoServico='+idSolicitacao+'&idTarefa='+idTarefa+'&nomeTarefa='+nomeTarefa;
	$('#modal_exibirDelegacaoTarefa').modal('show');
}

function prepararSuspensao(idSolicitacao) {
	document.getElementById('frameExibirSuspender').src =  URL_SISTEMA+'pages/suspensaoSolicitacao/suspensaoSolicitacao.load?iframe=true&idSolicitacaoServico='+idSolicitacao;
	$('#modal_suspender').modal('show');
}

function reativarSolicitacao(idSolicitacao) {
	if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao"))) 
		return;
	document.form.idSolicitacaoSel.value = idSolicitacao;
	document.form.fireEvent('reativaSolicitacao'); 
}

function prepararExecucaoTarefa(idTarefa,idSolicitacao,acao) {
	janelaAguarde();
	document.form.idSolicitacaoSel.value = idSolicitacao;
	document.form.idTarefa.value = idTarefa;
	document.form.acaoFluxo.value = acao;
	document.form.fireEvent('preparaExecucaoTarefa');
}

exibirVisao = function(titulo,idVisao,idFluxo,idTarefa,acao){
	document.getElementById('tdAvisosSol').innerHTML = '';
	myLayout.close("south");
	document.getElementById('fraSolicitacaoServico').src = "about:blank";
	document.getElementById('fraSolicitacaoServico').src = URL_SISTEMA + "pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
};

fecharVisao = function(){
	$( "#POPUP_VISAO" ).dialog( 'close' );
	document.getElementById('fraSolicitacaoServico').src = "about:blank";
	document.form.fireEvent('exibeTarefas');
	myLayout.open("south");		
	//myLayout.open("west");		
};
	

function exibirUrl(titulo, url) {
	document.getElementById('frameNovaSolicitacao').src = '../../'+url;
	$('#modal_novaSolicitacao').modal('show');
}
function reclassificarSolicitacao(idSolicitacao, idTarefa) {
	JANELA_AGUARDE_MENU.show();
	document.getElementById('frameReclassificarSolicitacao').src = URL_SISTEMA+'pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load?idSolicitacaoServico='+idSolicitacao+'&idTarefa='+idTarefa+'&reclassificar=S&visualizarPasso=C';
	$('#modal_reclassificarSolicitacao').modal('show');

};

function inicializaPopover(){
	$('.maisInfo').popover({ placement: 'top', animation: true, trigger: 'click', html: true}); 
	$('.informacoesSolicitante').popover({placement: 'top', animation: true, trigger: 'click', html: true});
}

var completeServico;
$(document).ready(function() {
	
	$('body').on('click', function (e) {
	    $('.maisInfo').each(function () {
	        //the 'is' for buttons that triggers popups
	        //the 'has' for icons within a button that triggers a popup
	        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
	            $(this).popover('hide');
	        }
	    });
	});
	
	$('body').on('click', function (e) {
	    $('.informacoesSolicitante').each(function () {
	        //the 'is' for buttons that triggers popups
	        //the 'has' for icons within a button that triggers a popup
	        if (!$(this).is(e.target) && $(this).has(e.target).length === 0 && $('.popover').has(e.target).length === 0) {
	            $(this).popover('hide');
	        }
	    });
	});

	completeServico = $('#servicoBusca').autocomplete({ 
		serviceUrl:'pages/autoCompleteServico/autoCompleteServico.load',
		noCache: true,
		onSelect: function(value, data){
			//document.form.clear();
			$('#idServico').val(data);
			$('#servicoBusca').val(value);
			/*document.form.fireEvent('verificaImpactoUrgencia');	*/	
			document.form.fireEvent('carregaBaseConhecimentoAssoc');
			carregarInformacoesComplementares();
			calcularSLA();
			startLoading();

		} 
	});
	inicializaPopover();
	$('#btnAtualizarGraficos').click(function(){ 
		renderizarGraficos();
	})
});


function MostrarMaisInformacoes(descricao){
	$('.maisInfo').attr('data-content', descricao);
}


/**
Adicionado para fechar o moldal  apos carregar a grid de solicitaçoes.grid de solicitação.
* 
* @author maycon.fernandes
* @since 25/10/2013 14:35
*/
function fecharModal(){
	$('#modal_novaSolicitacao').modal('hide');
	$('#modal_reclassificarSolicitacao').modal('hide');
	$('#modal_criarSubSolicitacao').modal('hide');
	$('#modal_exibirDelegacaoTarefa').modal('hide');
	$('#modal_suspender').modal('hide');
	$('#modal_alterarSLA').modal('hide');
}

function fecharModalNovaSolicitacao(){
	$('#modal_novaSolicitacao').modal('hide');
	$('#modal_reclassificarSolicitacao').modal('hide');
	carregaListaServico();
}

function carregaListaServico(){
	pesquisarItensFiltro();
}

  function fecharModalReclassificacao() {
  	$('#modal_reclassificar2').modal('hide');
	atualizarLista();
  }

/**
 * Realiza o evento de dropdown do filtro de pesquisa
 * Ao clicar dentro da area de conteudo do filtro o mesmo não oculta, mas se o 
 * mesmo clicar fora da area de conteudo o mesmo fica oculto
 */
$('html').off('click.dropdown.data-api');
$('html').on('click.dropdown.data-api', function(e) {
  if($(e.target).is('.search') || !$(e.target).parents('li').is('.dropdownFiltroPesquisa')) {
    $('.dropdown').removeClass('open');
    resetarPluginSelect2AutoComplete();
  }else {
	  e.stopPropagation();
  }
});

function fechaWindow() {
	$('#acoes').removeClass('open');
}


/** Autor: Pedro Lino
 * Data: 27/08/2013
 * Filtra todos os dados contidos na lista em div.content-area.ativo
 * deve ser chamada no input via onkeyup
 * campoBusca: valor digitado no campo de filtro
 * lista: Id da div onde será feito a busca
 **/
function filtroListaDivJs(campoBusca, lista){
			// Recupera value do campo de busca
        var term=campoBusca.value.toLowerCase();
		if( term != "")
		{
			 var searchText = term;

		        $('#' + lista + ' div.content-area.ativo ').each(function(){

		            var currentLiText = $(this).text(),
		                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) !== -1;

		            $(this).toggle(showCurrentLi);

		        });  
		}else{
			// Quando não há nada digitado, mostra a tabela com todos os dados
			 $('#' + lista + ' div.content-area.ativo').each(function(){

		            var currentLiText = $(this).text(),
		                showCurrentLi = currentLiText.toLowerCase().indexOf(searchText) == -1;

		            $(this).toggle(showCurrentLi);

		        }); 
		}
	}

/** AREA DE GRAFICOS**/


function atualizaGrafico(){
	plotaGrafico(dadosGrafico, "divGrafico");
	eval(scriptTemposSLA);
	temporizador.init();
	temporizador.ativarDesativarTimer();
}
function atualizaGrafico2(){
	plotaGrafico(dadosGrafico2, "divGrafico2");
}
function atualizaGrafico3(){
	plotaGrafico(dadosGerais, "divGrafico3");
}

function plotaGrafico(dados, idDiv){
	var div = '#'+idDiv;
	$.plot(div, dados, {
	    series: {
	    	 pie: {
	    		 innerRadius: 0.0,
	             show: true,
	             highlight: {
						opacity: 0.1
					},
					radius: 1,
					stroke: {
						color: '#fff',
						width: 8
					},
					startAngle: 2,
				    combine: {
	                    color: '#EEE',
	                    threshold: 0.05
	                },
	             label: {
	                    show: true,
	                    radius: 1,
	                    formatter: function(label, series){
	                        return '<div class="label label-inverse">'+label+'&nbsp;'+Math.round(series.percent)+'%</div>';
	                    }
	         }	
	    },
	    grow: {	active: true},
	    legend: {
	        show: false
	    },
	    grid: {
            hoverable: true,
            clickable: true
           
        },
        colors: [],
	    tooltip: true,
	    tooltipOpts: {
			content: "%s : %y.1"+"%",
			shifts: {
				x: -30,
				y: -50
			},
			defaultTheme: true
		}
	  }
	});
}

function inicializarTemporizador(){
	if(temporizador == null){
		temporizador = new Temporizador("imgAtivaTimer");
	} else {
		temporizador = null;
		try{
			temporizador.listaTimersAtivos = [];
		}catch(e){}
		try{
			temporizador.listaTimersAtivos.length = 0;
		}catch(e){}
		temporizador = new Temporizador("imgAtivaTimer");
	}
}

function gravarSolicitacao() {
	document.form.save();
	if (document.getElementById('form') != null) {
		alert(document.getElementById('form'));
	}
}

function chamarPesquisaSolicitacoes(){
	document.getElementById('framePesquisaGeralSolicitacao').src = URL_SISTEMA+'pages/pesquisaSolicitacoesServicos/pesquisaSolicitacoesServicos.load?iframe=true';
	$('#modal_pesquisaGeralSolicitacao').modal('show');
}

function chamarAgendaGrid(){
	document.getElementById('frameAgendaAtvPeriodicas').src = URL_SISTEMA+'pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar=true';
	$('#modal_agenda').modal('show');
}

function chamarSuspenderReativarSolicitacaoGrid(){
	document.getElementById('frameSuspenderReativarSolicitacao').src = URL_SISTEMA+'pages/suspensaoReativacaoSolicitacao/suspensaoReativacaoSolicitacao.load?noVoltar=true&iframe=true';
	$('#modal_SuspenderReativarSolicitacao').modal('show');
}

geraPopoverInformacoesSolicitante = function(telefoneContato, emailContato) {
	
  	if ((emailContato != '' && emailContato != undefined && emailContato != null) 
  	||  (telefoneContato != '' && telefoneContato != undefined && telefoneContato != null)){
  		var strAux1 = '';
  		if (telefoneContato != '' && telefoneContato != undefined && telefoneContato != null){
  			strAux1 += i18n_message('citcorpore.comum.telefone')+":"  + telefoneContato +", \r" ;
  		}
  		var strAux2 = '';
  		if (emailContato != '' && emailContato != undefined && emailContato != null){
  			/*
  			 * Rodrigo Pecci Acorse - 20/11/2013 16h40 - #125019
  			 * Foi adicionada a classe nowrap para evitar a quebra de linha do e-mail 
  			 */
  			strAux2 += "<span class='nowrap'>"+i18n_message('citcorpore.comum.email')+"</span>: " + emailContato;
  		}

  		$('.informacoesSolicitante').attr('data-content', '<p>'+'<label>'+strAux1+'</label>'+'<label>'+strAux2+'</label>'+'</p>');

  	}
  };
  
  var completeSolicitante;
  $(document).ready(function() {
	  completeSolicitante = $('#nomeSolicitante').autocomplete({ 
  		serviceUrl:'pages/autoCompleteSolicitante/autoCompleteSolicitante.load',
  		noCache: true,
  		onSelect: function(value, data){
  			document.formInformacoesContato.idSolicitante.value = data;
  			document.formInformacoesContato.nomeSolicitante.value = value;
  			document.formInformacoesContato.nomecontato.value = data;
  			document.formInformacoesContato.fireEvent("restauraSolicitante");
  		}
  	});
  });
  
  function montaParametrosAutocompleteSol(idContrato){
	  completeSolicitante.setOptions({params: {contrato: idContrato} });
  }

  function carregarModalDuplicarSolicitacao(idSolicitacao) {	  
	    document.formInformacoesContato.clear();
		document.formInformacoesContato.idSolicitacaoServico.value = idSolicitacao;
		
		/*
		 * Rodrigo Pecci Acorse - 03/12/2013 14h40 - #126139
		 * Seta os valores da situação e grupo executor que foram selecionados no filtro para o form de criar subsolicitação.
		 */
		var situacao = $('select[name="situacao"]').find(':selected').attr('value');
		if (situacao == "" || situacao == "undefined") situacao = "";
		
		var idGrupoAtual = $('select[name="idGrupoAtual"]').find(':selected').attr('value');
		if (idGrupoAtual == "" || idGrupoAtual == "undefined") idGrupoAtual = "-1";
		
	  	document.formInformacoesContato.situacao.value = situacao;
	  	document.formInformacoesContato.idGrupoAtual.value = idGrupoAtual;
		
		document.formInformacoesContato.fireEvent('carregarModalDuplicarSolicitacao');
		$('#modal_criarSubSolicitacao').modal('show');
  }
  
  function fecharModalAgendarAtividade() {
	  $('#modal_agendarAtividade').modal('hide');
  }
  
function duplicarSolicitacao(){
		if (document.formInformacoesContato.idOrigem.value == '') {
			alert(i18n_message("citcorpore.comum.origem") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.idOrigem.focus();
			return;
		}
		if (document.formInformacoesContato.nomeSolicitante.value == '') {
			alert(i18n_message("solicitacaoServico.nomeDoSolicitante") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.nomeSolicitante.focus();
			return;
		}
		if (document.formInformacoesContato.emailcontato.value == '') {
			alert(i18n_message("solicitacaoServico.emailContato") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.emailcontato.focus();
			return;
		}
		if (document.formInformacoesContato.idUnidade.value == '') {
			alert(i18n_message("unidade.unidade") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
			document.formInformacoesContato.idUnidade.focus();
			return;
		}
		if (document.formInformacoesContato.telefonecontato.value == '') {
				alert(i18n_message("solicitacaoServico.telefoneDoContato") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
				document.formInformacoesContato.telefonecontato.focus();
				return;
		}
		if(ValidaEmail()) {
			JANELA_AGUARDE_MENU.show();
			document.formInformacoesContato.fireEvent("duplicarSolicitacao");
		}
		
}
exibirSubSolicitacoes = function(idSolicitacaoServico){
	document.formIncidentesRelacionados.idSolicitacaoIncRel.value = idSolicitacaoServico; 
	inicializarTemporizadorRel1();
	document.formIncidentesRelacionados.fireEvent("abrirListaDeSubSolicitacoes");
	$('#modal_exibirSubSolicitaces').modal('show');
}

function LOOKUP_SOLICITANTE_select(id, desc){
	document.formInformacoesContato.idSolicitante.value = id;
	document.formInformacoesContato.fireEvent("restoreSolicitante");
	$('#modal_lookupSolicitante').modal('hide');
}
var temporizadorRel1;

function inicializarTemporizador(){
	if(temporizador == null){
		temporizador = new Temporizador("imgAtivaTimer");
	} else {
		temporizador = null;
		try{
			temporizador.listaTimersAtivos = [];
		}catch(e){}
		try{
			temporizador.listaTimersAtivos.length = 0;
		}catch(e){}
		temporizador = new Temporizador("imgAtivaTimer");
	}
}

function inicializarTemporizadorRel1(){
	if(temporizadorRel1 == null){
		temporizadorRel1 = new Temporizador("imgAtivaTimerRel1");
	} else {
		temporizadorRel1 = null;
		try{
			temporizadorRel1.listaTimersAtivos = [];
		}catch(e){}
		try{
			temporizadorRel1.listaTimersAtivos.length = 0;
		}catch(e){}
		temporizadorRel1 = new Temporizador("imgAtivaTimerRel1");
	}
}

function ValidaEmail() {
    var email = $("#emailcontato").val();
    var emailValido=/^.+@.+\..{2,}$/;

    if(!emailValido.test(email))
    {
    	alert(i18n_message("solicitacaoServico.emailContato") + ': ' + i18n_message("citcorpore.validacao.emailInvalido"));
    	document.formInformacoesContato.emailcontato.focus();
    	return false;
    }
    return true;
}

function adicionarIdContratoNaLookup(id){
	 document.getElementById('pesqLockupLOOKUP_SOLICITANTE_IDCONTRATO').value = id;
}
/**
 * Motivo: Corrigir um bug nos navegadores que não fecham o select2 no dropdown de filtro
 * Autor: flavio.santana
 * Data/Hora: 13/11/2013 
 */
function resetarPluginSelect2AutoComplete() {
	$("#idResponsavelAtual").select2("close");
    $("#idSolicitante").select2("close");
    $("#tarefaAtual").select2("close");
}