
/**AS ALTERAÇÃO FORAM COMENTADAS EM CASO DE REVERTER FUTURAMENTE. 
 * DATA: 25:04/2013
 * PEDRO LINO
 **/



$(window).resize(function() {
	//$("#right").css("width", $(window).width() - 215);
});

/**
 * Pedro Lino
 * 23/04/2011
 * Adiciona os serviços da tab lateral de catalogo de serviço em meu catalogo
 */
addServicoMeuCatalogo = function(idServico){
		document.formCatalogo.idServicoCatalogo.value = idServico
		document.formPortal.idServico.value = document.formCatalogo.idServicoCatalogo.value;
		JANELA_AGUARDE_MENU.show();
		document.formPortal.fireEvent('saveService');
}
/**
 * Pedro Lino
 * 23/04/2011
 * Deleta os serviços da tab lateral de catalogo de serviço em meu catalogo
 */
deleteServicoMeuCatalogo = function(idServico){
	document.formCatalogo.idServicoCatalogo.value = idServico
	document.formPortal.idServico.value = document.formCatalogo.idServicoCatalogo.value;
	JANELA_AGUARDE_MENU.show();
	document.formPortal.fireEvent('deleteService');
}

$(function() {
	
	
	
	/*Resizable Manual*/
	$( "#resizableManual" ).button();
	$( "#chkAtualiza" ).button();
	
		
	/*Animate de Catálogo de negocios*/
	$('.floatingTab').click(function(e) {
		if (parseInt($(this).parent().css('right')) > -563) {
			$(this).parent().animate({
				right : -752
			}, 250, 'easeOutExpo');
		} else {
			$(this).parent().animate({
				right : 0
			}, 700, 'easeOutExpo');
		}
	});	
	$('.floatingTabsButtons .cancelBtn').click(function(e) {
		$(this).parent().parent().animate({
			right : -752
		}, 250, 'easeOutExpo');
	});
	
	$(".botao").button();
	
	$("html").tooltip({
		close : function() {
			$(document).unbind("mousemove.tooltip-position");
		}
	});
	$(".texto").focus(function() {
		$(this).toggleClass("ui-state-focus");
	});
	$(".texto").blur(function() {
		$(this).toggleClass("ui-state-focus");
	});
	$(".texto").keyup(function() {
		if ($(this).val() != "") {
			$(this).removeClass("errosImput");
			$("#error" + $(this).attr("id")).detach();
		}
	});
	$("#tabs").tabs( "option", "active", 2);

	$(".dialog").dialog("destroy");
	$(".dialog").dialog({
		autoOpen : false,
		resizable : true,
		modal : true,
		position : "center"
	});
	$(".datepiker").datepicker();
	var altura = parseInt($(window).height());
	var largura = parseInt($("#body").width());
	var versao = parseInt($.browser.version, 10);
	altura = parseInt($(window).height() - 200);
	largura = parseInt($("#body").width() - 210);
	
	$("#body").find("#left").each(function() {
		$("#left").css("height", altura);
		$("#right").css("height", altura);
		$("#right").css("width", largura);
	});

	$("#popupCadastroRapido").dialog({
		title : '',
		width : 900,
		height : 500,
		modal : true,
		autoOpen : false,
		resizable : true,
		show : "fade",
		hide : "fade"
	});
	
	$("#POPUP_CONTEUDOCATALOGO").dialog({
		title : 'Catálogo de Negócio',
		width : 600,
		height : 400,
		modal : true,
		autoOpen : false,
		resizable : true,
		show : "fade",
		hide : "fade"
	});

	$("#popupCadastroRapido").dialog('close');

	$("#popupNovaSolicitacao").dialog({
		title : 'Nova Solicitação',
		width : 1000,
		height : 500,
		modal : true,
		autoOpen : false,
		resizable : false,
		show : "fade",
		hide : "fade"
	});

	$("#popupNovaSolicitacao").dialog('close');

	$("#right").find("#container").each(function() {

		$("#setaColumn").click(function() {
			if ($(this).is('.hide')) {
				$(this).removeClass('hide').addClass('show');
				$(this).css("top", -10);
				$(this).css("left", -30);
				$("#right").removeClass('nosidebar');
				$("#left").css('display', 'block');
			} else {
				$(this).removeClass('show').addClass('hide');
				$(this).css("top", '0');
				$(this).css("left", '-15px');
				$("#right").addClass('nosidebar');
				$("#left").hide();
			}

		});
	});
	
	//função rezible

/*	$("ul#column1,ul#column2,ul#column3,ul#column4")
			.find(".portlet")
			.each(
					function() {
						var selector = $(this).attr('id');

						$("#" + selector)
								.find(".sort1")
								.each(
										function() {
											var s = $(this).attr('id').split(
													'servico-').join('');
											$("#servico-" + s)
													.find(
															"span.ui-icon-circle-plus")
													.click(
															function() {
																$("#idServico")
																		.val(s);
																document.formPortal
																		.fireEvent('saveService');
																$(
																		".ui-tooltip ")
																		.remove();
															});
										});

						$("#" + selector)
								.find(".sort2")
								.each(
										function() {
											var s = $(this).attr('id').split(
													'meu-servico-').join('');
											$("#meu-servico-" + s)
													.find(
															"span.ui-icon-circle-minus")
													.click(
															function() {
																$("#idServico")
																		.val(s);
																document.formPortal
																		.fireEvent('deleteService');
																$(
																		".ui-tooltip ")
																		.remove();
															});
										});
					});*/

	var $tabs = $("#right").tabs();
	$tabs.tabs('select', 1);

	
	var $tab_items = $("ul:first li", $tabs).droppable({
		hoverClass : "ui-state-hover",
		drop : function(event, ui) {
			var $item = $(this);

			$tabs.tabs("select", $tab_items.index($item));
		}
	});

	/* Column 1/2 - portlet */
	$("ul#column0").sortable({
						connectWith : "",
						revert : true,
						cancel : '.portlet-content',
						stop : function(event, ui) {	
							$("ul#column1,ul#column2,ul#column2_1,ul#column3,ul#column3_1,ul#column4")
									.find(".panel")
									.each(
											function() {

												var selector = $(this).attr(
														'id');

												var s = selector.split(
														'portlet-0').join('');

												if (s == 1 || s == 4) {
													$tabs.tabs({
														selected : 1
													});
												} else if (s == 2 || s == 5) {
													$tabs.tabs({
														selected : 0
													});
												} else if (s == 3) {
													$tabs.tabs({
														selected : 2
													});
												}

												$("#" + selector)
														.removeClass("panel")
														.addClass(
																"portlet ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
														.find(".portlet-header")
														.addClass(
																"ui-widget-header ui-corner-all")
														.end()
														.find(
																".portlet-content");

												$("#" + selector).css("height",
														250);
												$("#" + selector).css("width",
														710);

												$(".portlet-header .ui-icon")
														.click(
																function() {
																	itens("#"
																			+ selector);
																	document.formPortal
																			.fireEvent('delete');
																});

												$(".portlet").find(
														".portlet-content")
														.show();

												// Setando valores aleatorios
												// para o portlet
												var top = rand(50, 250);
												var left = rand(100, 800);

												var iditem = $(this).attr('id')
														.split('portlet-')
														.join('');
												$("#idItem").val(iditem);
												$("#largura").val(
														parseInt($(this)
																.width()));
												$("#altura").val(
														parseInt($(this)
																.height()));
												$("#posicaoY").val(
														parseInt(left));
												$("#posicaoX").val(
														parseInt(top));
												$(this)
														.find(
																".portlet-content")
														.each(
																function() {
																	$(this)
																			.css(
																					"height",
																					parseInt($(
																							this)
																							.height() - 50));
																});
												if(document.getElementById('resizableManual').checked == true){

												$("#" + selector).resizable(
																{
																	maxHeight : $(
																			"#right")
																			.height(),
																	maxWidth : $(
																			"#right")
																			.width(),
																	minHeight : 150,
																	minWidth : 350,
																	containment: "#right",
																	resize : function(
																			event,
																			ui) {
																		itens("#"
																				+ selector);
																	}
																});
												}
												$("#" + selector)
														.draggable(
																{
																	connectToSortable : "#column0",
																	cancel : '.portlet-content',
																	stop : function(
																			event,
																			ui) {
																		itens(this);
																		document.formPortal
																				.save();
																	}
																});
												$("#" + selector)
														.draggable(
																{
																	connectToSortable : "#column0",
																	cancel : '.portlet-content',
																	drag : function(
																			event,
																			ui) {
																		itens(this);
																	}
																});

											});
							document.formPortal.save();
							

						}
					});

	/* Column 2/1 - portlet */
	$("").sortable({
		connectWith : "ul#column0",
		revert : false,
		cancel : '.portlet-content'
	});
	
	//função rezible

/*	$("#column0")
			.find(".panel")
			.each(
					function() {
						$(this).attr("id", function() {
							$(this).resizable("destroy");
						});
						 Classes 
						$(this)
								.addClass(
										"ui-widget ui-widget-content ui-helper-clearfix ui-corner-all");

					});
*/
	//$("#column0,#column1,#column2,ul#column2_1,#column3,ul#column3_1,ul#column4,ul#column5").disableSelection();

	var $tabs = $("#right").tabs( "option", "active", 3);

});

addRemove = function() {
	$("ul#column1,ul#column2,ul#column2_1,ul#column3,ul#column3_1,ul#column4,ul#column5")
			.find(".portlet")
			.each(
					function() {
						var selector = $(this).attr('id');

						$("#" + selector)
								.find(".sort1")
								.each(
										function() {
											var s = $(this).attr('id').split(
													'servico-').join('');
											$("#servico-" + s)
													.find(
															"span.ui-icon-circle-plus")
													.click(
															function() {
																$("#idServico")
																		.val(s);
																document.formPortal
																		.fireEvent('saveService');
																$(
																		".ui-tooltip ")
																		.remove();
															});
										});

						$("#" + selector)
								.find(".sort2")
								.each(
										function() {
											var s = $(this).attr('id').split(
													'meu-servico-').join('');
											$("#meu-servico-" + s)
													.find(
															"span.ui-icon-circle-minus")
													.click(
															function() {
																$("#idServico")
																		.val(s);
																document.formPortal
																		.fireEvent('deleteService');
																$(
																		".ui-tooltip ")
																		.remove();
															});
										});

					});

}
function red(id, largura, altura, x, y) {
	var selector = $("#" + id);
	$(selector).css("width", parseFloat(largura));
	$(selector).css("height", parseFloat(altura));
	$(selector).css("top", parseFloat(x));
	$(selector).css("left", parseFloat(y));
}
redimensionamento = function() {
	$("ul#column1,ul#column2,ul#column2_1,ul#column3,ul#column3_1,ul#column4,ul#column5")
			.find(".portlet")
			.each(
					function() {
						var selector = $(this).attr('id');

						$("#" + selector)
								.addClass(
										"ui-widget ui-widget-content ui-helper-clearfix ui-corner-all")
								.find(".portlet-header").addClass(
										"ui-widget-header ui-corner-all").end()
								.find(".portlet-content");
						$("#" + selector).find(".portlet-header .ui-icon")
								.click(function() {
									itens("#" + selector);
									document.formPortal.fireEvent('delete');
								});
						$("#" + selector).find(".portlet-content").show();
						if(document.getElementById('resizableManual').checked == true){
							$("#" + selector).resizable({
								maxHeight : $("#right").height(),
								maxWidth : $("#right").width(),
								minHeight : 150,
								minWidth : 350,
								containment : "#right",
								stop : function(event, ui) {
									itens(this);
									document.formPortal.save();
								}
							});
						}
						if(document.getElementById('resizableManual').checked == true){
							$("#" + selector)
								.resizable(
										{
											maxHeight : $("#right").height(),
											maxWidth : $("#right").width(),
											minHeight : 150,
											minWidth : 350,
											containment: "#right",
											resize : function(event, ui) {
												itens(this);
												var selector = $(this).attr(
														'id');
												$(this)
														.find(
																".portlet-content")
														.each(
																function() {
																	$(this)
																			.css(
																					"height",
																					parseFloat($(
																							"#"
																									+ selector)
																							.height() - 50));
																});
											}
										});
						}
						
						//DRAGGABLE
						
					/*	$("#" + selector).draggable({
							connectToSortable : "#column0",
							scroll : true,
							scrollSensitivity : 100,
							scrollSpeed : 100,
							containment : '#right',
							cancel : '.portlet-content',
							stop : function(event, ui) {
								itens(this);
								document.formPortal.save();
							}
						});
						$("#" + selector).draggable({
							connectToSortable : "#column0",
							scroll : true,
							scrollSensitivity : 100,
							scrollSpeed : 100,
							containment : '#right',
							cancel : '.portlet-content',
							drag : function(event, ui) {
								itens(this);
							}
						});*/
						$("#" + selector).find(".portlet-content").each(
								function() {
									$(this).css(
											"height",
											parseFloat($("#" + selector)
													.height() - 50));
								});

						$("#" + selector)
								.find(".sort1")
								.each(
										function() {
											var s = $(this).attr('id').split(
													'servico-').join('');
											$("#servico-" + s)
													.find(
															"span.ui-icon-circle-plus")
													.click(
															function() {
																$("#idServico")
																		.val(s);
																document.formPortal
																		.fireEvent('saveService');
																$(
																		".ui-tooltip ")
																		.remove();
															});
										});
						$("#" + selector)
								.find(".sort2")
								.each(
										function() {
											var s = $(this).attr('id').split(
													'meu-servico-').join('');
											$("#meu-servico-" + s)
													.find(
															"span.ui-icon-circle-minus")
													.click(
															function() {
																$("#idServico")
																		.val(s);
																document.formPortal
																		.fireEvent('deleteService');
																$(
																		".ui-tooltip ")
																		.remove();
															});
										});

						$("div").find('span.ui-icon').each(function() {
							cursor: 'pointer'
						});
						
						//função onkeyup, agora está na funcao filtroTableJs.
						
					/*	$('#camponomeServico').keyup(function(event) {
													listKeyUp(this);
												});*/
						

						$("#camponomeServico").focus();
						
						$("table.form").click(function(){
							$("#camponomeServico").focus();
						}); 
					});
	
}

function hideLeft() {
	$("#setaColumn").removeClass('show').addClass('hide');
	$("#setaColumn").css("top", '0');
	$("#setaColumn").css("left", '-15px');
	$("#right").addClass('nosidebar');
	$("#left").hide();
}
function showLeft() {
	$("#setaColumn").removeClass('hide').addClass('show');
	$("#setaColumn").css("top", -10);
	$("#setaColumn").css("left", -30);
	$("#right").removeClass('nosidebar');
	$("#left").css('display', 'block');
}
function itens(_this) {
	var iditem = $(_this).attr('id').split('portlet-').join('');
	$("#idItem").val(iditem);
	$("#largura").val(parseInt($(_this).width()));
	$("#altura").val(parseInt($(_this).height()));
	var p = $(_this).position();
	$("#posicaoY").val(parseInt(p.left));
	$("#posicaoX").val(parseInt(p.top));
	$(_this).find(".portlet-content").each(function() {
		$(this).css("height", parseInt($(_this).height() - 50));
	});
}

function listKeyUp(_this) {
	var value = $(_this).val();
	$('#nomeServico').val(value);
	
	document.formPortal.fireEvent('listNomeServico');
	document.getElementById('loading').style.display = 'block';
}
function rand(min, max) {
	var result = Math.floor(Math.random() * (max + 1));
	if (result < min) {
		return rand(min, max);
	} else {
		return result;
	}
}
function fecharPopup() {
	$("#popupCadastroRapido").dialog("close");
}

function load() {
	if (window.location != window.top.location) {
		window.top.location = window.location;
	}
	document.getElementById("user").focus();
	$("#user").focus();

	if ($.browser.msie) {
		var versao = parseInt($.browser.version, 10);

		if (versao < 9) {
			$("#mensagemNavegador").dialog({
				height : 155,
				modal : true,
				autoOpen : false,
				width : 350,
				resizable : false,
				show : "fade",
				hide : "fade",
				position : "center"
			});

			$(".ui-dialog").css("width", "auto");

			$("#mensagemNavegador").dialog('open');

			$("#wrapper").hide();
		} else {
			$("#mensagemNavegador").hide();
		}

	} else {
		$("#mensagemNavegador").hide();
	}

}
function submitEnter(event) {
	if ((event.keyCode ? event.keyCode : event.which) == 13) {
		validar();
	}
}

function createElementWithClassName(type, className) {
	var elm = document.createElement(type);
	elm.className = className;
	return elm;
}

function createPosts(id) {
	var node = createElementWithClassName('div', 'floatLeft');
	var inicial = createElementWithClassName('div', 'portletInicial');
	var inicialBorder = createElementWithClassName('div',
			'portletInicial-border');
	var content = createElementWithClassName('div', 'content');
	var header = createElementWithClassName('div', 'header');
	var headerTitle = createElementWithClassName('spam', 'fn');
	var contentEntry = createElementWithClassName('div', 'content-entry');
	var lines = createElementWithClassName('div', 'line');
	var imgs = createElementWithClassName('img', '');
	var a = createElementWithClassName('a', '');
	var desc = createElementWithClassName('div', '');

	contentEntry.id = 'entry';
	header.id = 'header-title-' + id;
	imgs.id = "img-" + id;
	a.id = "a-" + id;
	desc.id = "div-" + id;

	lines.appendChild(imgs);
	lines.appendChild(a);
	lines.appendChild(desc);
	$("#entry").append(lines);

}

function event() {
	$( ".even" ).click(function() {
		var s = $(this).attr('id').split('even-').join("");
		$( "#sel-" + s ).toggle();

	});
}

function eventMeuCatalogo() {
	$(".meuCatalogo").click(function() {
		var s = $(this).attr('servico');
		document.formCatalogo.nomeServico.value = s;
		popupNovaSolicitacaoCatalogoNegocio();			
	});
}

function meuCatalogo(str) {
	document.formCatalogo.nomeServico.value = str;
	popupNovaSolicitacaoCatalogoNegocio();	
}

function eventCatalogo(idInfoCatalogoServico) {
//	$( ".catalogo" ).click(function() {
		/*ID catalogo*/
//		var c = $(this).attr('id').split('catalogo-').join("");
//		document.formCatalogo.idInfoCatalogoServico.value = c;
		/*Servico*/
//		var s = $(this).attr('servico');
		document.formCatalogo.idInfoCatalogoServico.value = idInfoCatalogoServico;
	//	document.formCatalogo.nomeServico.value = s;
		document.formCatalogo.fireEvent('conteudoInfoCatalogoServico');
//	});
}

/** Autor: Pedro Lino
 * Data: 19/04/2013
 * Filtra todos os dados contidos na tabela(filtro feito por TR)
 * deve ser chamada no input via onkeyup
 * campoBusca: valor digitado no campo de filtro
 * table: Id da tabela onde será feito a busca
 **/
function filtroTableJs(campoBusca, table){
			// Recupera value do campo de busca
        var term=campoBusca.value.toLowerCase();
		if( term != "")
		{
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

