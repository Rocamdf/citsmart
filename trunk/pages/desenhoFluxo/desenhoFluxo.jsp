<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<!DOCTYPE html>
<html lang="pt-br">
<head>

<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/menu/menuConfig.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<meta charset="ISO-8859-1">
<title><i18n:message key="desenhoFluxo.titulo"/></title>
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/Teclas.js"></script>

    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/layout-default-latest.css"/>
    <link type="text/css" rel="stylesheet" href="css/desenho-fluxo.css"/>

    <!-- theme is last so will override defaults --->
    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.all.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jqueryTreeview/jquery.treeview.css"/>

    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.layout-latest.js"></script>
    
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
    
	<script type="text/javascript" src="js/desenho-fluxo.js"></script>
    
    <script type="text/javascript">
        
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterLoad = function() {
			if (document.form.idFluxo.value != '') {
				exibeElementosFluxo(document.form.linhaAtual.value, document.form.idFluxo.value);
				controlarExibicaoBotoes('block');
			}else{
				if (document.form.acao.value == 'I'){
					document.getElementById('btnImportarFluxo').style.display = 'none';
					document.getElementById('btnExportarFluxo').style.display = 'none';
					document.getElementById('btnExcluirFluxo').style.display = 'none';
					$("#POPUP_FLUXO").dialog('open');
				}
				controlarExibicaoBotoes('none');
			}
		}
	}
	        
	dragstart = function(e) {
		e.dataTransfer.setData("text", e.target.getAttribute("id"));
	}
	      
    var myLayout;
	exibirElementos = function(json_elementos) {
		elementos     = [];
		arrayElementos = JSON.parse(json_elementos);
		var str = "<table>"
		for(var i = 0; i < arrayElementos.length; i++){
			var elementoDto = arrayElementos[i];
			str += "<tr><td class='tdIcone' id='tdIcone"+i+"' style='display:none'><img src='imagens/" + elementoDto.icone + "' draggable='true' class='icone' title='"+ i18n_message(elementoDto.label) +"' ondragstart='dragstart(event)' id='" + i + "'></img></td></tr>";
		}
		str += "<tr><td class='tdIcone'>&nbsp;</td></tr>";
		str += "<tr><td class='tdIcone' style='display:none' id='tdGravarFluxo'><img src='imagens/gravar.png' title='"+i18n_message("desenhoFluxo.msg.atualizarDesenho")+"' onclick='gravar()' class='icone'></img></td></tr>";
		str += "</table>";
		document.getElementById('divElementos').innerHTML = str;
		desenhoFluxo.setTiposDeElemento(arrayElementos);
	};
	

	controlarExibicaoBotoes = function(controle) {
		document.getElementById('imgAlterarFluxo').style.display = controle;
		document.getElementById('imgExportarFluxo').style.display = controle;
		document.getElementById('tdGravarFluxo').style.display = controle;		
		for(var i = 0; i < arrayElementos.length; i++){
			document.getElementById('tdIcone'+i).style.display = controle;	
		}
	}


    $(document).ready(function () {
    
    
        // create the layout - with data-table wrapper as the layout-content element
        myLayout = $('body').layout({
        	west__size:		 .2
        ,	center__onresize:     function (pane, $pane, state, options) {
                                    var $content    = $pane.children('.ui-layout-content')
                                    ,   gridHdrH    = $content.children('.slick-header').outerHeight()
                                    ,   paneHdrH    = $pane.children(':first').outerHeight()
                                    ,   paneFtrH    = $pane.children(':last').outerHeight()
                                    ,   $gridList   = $content.children('.slick-viewport') ;
                                    $gridList.height( state.innerHeight - paneHdrH - paneFtrH - gridHdrH );
                                }
        ,	 south__size:		 0
        ,    south__initClosed:   true
        ,    east__initClosed:   true
        ,	 east__size:		 0
        ,    north__initClosed:   true
        ,	 north__size:		 0
        , center: {
        		onresize_end: function(){
        			redimensionaAreaDesenho();
        		}     		
        	}
        , west: {
        		onresize_end: function(){
        			redimensionaAreaDesenho();
        		}     		
        	}
                
        });

        $('body > h2.loading').hide(); // hide Loading msg
		desenhoFluxo = new DesenhoFluxo(document.getElementById("canvas"));
		desenhoFluxo.configuraEventos(); 
       	redimensionaAreaDesenho();
    });

    var bCarregou = false;
	var altCanvasInicial = 0;
	var largCanvasInicial = 0;
	redimensionaAreaDesenho = function() {
		canvasParent = document.getElementById('divCanvas');
		
		var l = parseInt(document.getElementById('divCenter').style.width);
		var a = parseInt(document.getElementById('divCenter').style.height);
		var t = parseInt(document.getElementById('divCenter').style.top);
		
		document.getElementById('divElementos').style.top = '0px';
		document.getElementById('divElementos').style.left = '0px';
		document.getElementById('divElementos').style.height = a+'px';
		document.getElementById('divElementos').style.width = '28px';
		
		document.getElementById('divCanvas').style.top = '0px';
		document.getElementById('divCanvas').style.left = '28px';
		document.getElementById('divCanvas').style.height = (a - 23) + 'px';
		document.getElementById('divCanvas').style.width = (l - 28)+'px';
		
		if (!bCarregou) {
			var altCanvas = a - 50;
			var largCanvas = l - 50;
			altCanvasInicial = altCanvas;
			largCanvasInicial = largCanvas;
			document.getElementById('canvas').height = altCanvas;
			document.getElementById('canvas').width = largCanvas;
		}

		document.getElementById('imgPosicao').style.top = '0px';
		document.getElementById('divPosicao').style.top = (a - 23) + 'px';
		document.getElementById('divPosicao').style.left = '28px';	
		document.getElementById('divPosicao').style.height = '23px';
		document.getElementById('divPosicao').style.width = (l-28)+'px';
		
		bCarregou = true;
		desenhoFluxo.atualiza();
	}
	
	voltar = function(){
		window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
	};
	
	var trAnterior = null;
	exibeElementosFluxo = function(linha,idFluxo) {
        if (desenhoFluxo.isAlterado() && !confirm((i18n_message("desenhoFluxo.msg.confirmaSelecao")).replace("\\n\\r",""))){
            return;
        } 

		if (trAnterior != null)
			trAnterior.style.background	= '';
			
		var tr = document.getElementById('tr'+linha);
		if (tr)
			tr.style.background	= '#FFCC99';
		
		trAnterior = tr;		
		document.form.idFluxo.value = idFluxo;
		document.form.linhaAtual.value = linha;
		
		document.getElementById('canvas').height = altCanvasInicial;
		document.getElementById('canvas').width = largCanvasInicial;
 
		controlarExibicaoBotoes('none');
    	JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('exibeElementosFluxo');
	}

	gravar = function(idFluxo) {
		if (document.form.idFluxo.value == '') {
			alert('Nenhum fluxo selecionado');
			return;
		}
		JANELA_AGUARDE_MENU.show();
		desenhoFluxo.serializa();
		document.form.fireEvent('atualizaDiagrama');
	}

	atualizar = function(idFluxo) {
        JANELA_AGUARDE_MENU.show();

	    document.form.action = document.form.action + ".load";     
        document.form.submit();
	}
	
    $(function() {
        $("#POPUP_PROPRIEDADES").dialog({
            autoOpen : false,
            width : 700,
            height : 400,
            modal : true
        });
        $("#POPUP_FLUXO").dialog({
            autoOpen : false,
            width : 700,
            height : 380,
            modal : true
        });  
        $("#POPUP_IMPORTACAO").dialog({
            autoOpen : false,
            width : 700,
            height : 200,
            modal : true
        }); 
    });
    
	function gravarCadastroFluxo(){
        if (StringUtils.isBlank(document.form.nomeFluxo.value)){
            alert(i18n_message("citcorpore.comum.nome")+" "+i18n_message("citcorpore.comum.naoInformado"));
            document.form.nomeFluxo.focus();
            return;
        }
        if (StringUtils.isBlank(document.form.descricao.value)){
            alert(i18n_message("citcorpore.comum.descricao")+" "+i18n_message("citcorpore.comum.naoInformado"));
            document.form.descricao.focus();
            return;
        }
        if (StringUtils.isBlank(document.form.nomeClasseFluxo.value)){
            alert(i18n_message("cadastroFluxo.nomeClasse")+" "+i18n_message("citcorpore.comum.naoInformado"));
            document.form.nomeClasseFluxo.focus();
            return;
        }
    	JANELA_AGUARDE_MENU.show();
    	document.form.fireEvent('gravaCadastroFluxo');
	}
	function excluirFluxo(){
		if (!confirm(i18n_message("cadastroFLuxo.confirma_exclusao"))) 
			return;
    	JANELA_AGUARDE_MENU.show();
    	document.form.fireEvent('excluiCadastroFluxo');
	}

	function exibirCadastroFluxo() {
		if (document.form.idFluxo.value == '') 
			return;
		var id = document.form.idFluxo.value;
		document.form.clear();
		document.form.idFluxo.value = id;
		document.form.fireEvent('recuperaCadastroFluxo');
		document.getElementById('btnExcluirFluxo').style.display = 'block';
		document.getElementById('btnImportarFluxo').style.display = 'none';
		document.getElementById('btnExportarFluxo').style.display = 'block';
		document.getElementById('imgExportarFluxo').style.display = 'block';
		$("#POPUP_FLUXO").dialog('open');
	}
	
	function exibirNovoFluxo() {
		document.form.clear();	
		document.getElementById('btnExcluirFluxo').style.display = 'none';
		document.getElementById('btnImportarFluxo').style.display = 'block';
		document.getElementById('btnExportarFluxo').style.display = 'none';
		document.getElementById('imgExportarFluxo').style.display = 'none';
		$("#POPUP_FLUXO").dialog('open');
	}

	function exportarFluxo() {
		if (document.form.idFluxo.value == '') 
			return;
		document.form.idFluxo.value = document.form.idFluxo.value;
    	JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('exportaFluxo');
	}
	
	exibeImportacaoFluxo = function(){
        if (!confirm(i18n_message("desenhoFluxo.msg.confirmaNovoFluxo"))){
            return;
        } 
		$("#POPUP_IMPORTACAO").dialog("open");
	}
	importarFluxo = function(){
        JANELA_AGUARDE_MENU.show();

	    document.formImportar.setAttribute("enctype","multipart/form-data"); 
	    document.formImportar.setAttribute("encoding","multipart/form-data");        
        document.formImportar.submit();
    }  
    </script>
<style type="text/css">
.ui-layout-center {top: 0px!important;}
/* .ui-layout-south {top: 0px!important;} */
</style>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
</head>
<body>

	<h2 class="loading">
		<i18n:message key="citcorpore.comum.carregandoCarregando" />...
	</h2>
	
	<div class="ui-layout-west hidden">
		<h2 class="section">
			CITSmart Designer
		</h2>	
		<div id="divAcoes" style='height:30px'>
			<table>
				<tr >
					<td class='tdIcone'><img src='imagens/voltar.gif' title=<i18n:message key="desenhoFluxo.msg.voltar"/> onclick='voltar()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
					<td >&nbsp;</td>
					<td class='tdIcone' ><img id='imgCriarFluxo' src='imagens/add.png' title=<i18n:message key="desenhoFluxo.msg.novoFluxo"/> onclick='exibirNovoFluxo()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
					<td class='tdIcone' ><img id='imgAlterarFluxo' style='display:none'src='imagens/edit.png' title=<i18n:message key="desenhoFluxo.msg.alterarFluxo"/> onclick='exibirCadastroFluxo()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
					<td class='tdIcone' ><img id='imgExportarFluxo' style='display:none'src='imagens/export.png' title=<i18n:message key="desenhoFluxo.msg.exportarFluxo"/> onclick='exportarFluxo()' class='icone' style='border: 1px solid #DDDDDD'></img></td>
				</tr>
			</table>		
		</div>	
		<div id="divFluxos" >
		</div>	
	</div>

	<div id='divCenter' class="ui-layout-center hidden">
		<div id="divElementos" style="position: absolute; margin: 0px;">
		</div>	    
		<div id="divCanvas" style="position: absolute; margin: 0px; overflow:auto;padding: 0px 0px 0px 0px !important">
			<canvas id="canvas"  style='border: 2px solid #DDDDDD; '>
			</canvas>	    
		</div>
		<div id="divPosicao" style="position: absolute; margin: 0px;padding: 0px 0px 0px 0px !important">
			<table style='width:100%'>
				<tr>
					<td >&nbsp;</td>
					<td style='width:24px;align:right;text-align:right;padding: 0px 0px 0px 0px !important' >
						<img id='imgDestacado' src='imagens/destacado.png'></img>
					</td>
					<td style='width:80px;align:right;font-size:9px;padding: 0px 0px 0px 0px !important'>
						&nbsp;&nbsp;<span id='spanSelecionado'></span>&nbsp;&nbsp;
					</td>
					<td  style='width:24px;align:right;text-align:right;padding: 0px 0px 0px 0px !important' >
						<img id='imgCentro' src='imagens/centro.png'></img>
					</td>
					<td style='width:80px;align:right;font-size:9px;padding: 0px 0px 0px 0px !important'>
						&nbsp;&nbsp;<span id='spanCentro'></span>&nbsp;&nbsp;
					</td>
					<td  style='width:24px;align:right;text-align:right;padding: 0px 0px 0px 0px !important' >
						<img id='imgPosicao' src='imagens/posicao.png'></img>
					</td>
					<td style='width:80px;align:right;font-size:9px;padding: 0px 0px 0px 0px !important'>
						&nbsp;&nbsp;<span id='spanPosicao'></span>&nbsp;&nbsp;
					</td>
				</tr>
			</table>
		</div>	    
	</div>	

	<div id="POPUP_PROPRIEDADES" style="overflow: auto;">
		<form name='formPropriedades' id='formPropriedades' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/desenhoFluxo/desenhoFluxo'>
			<input type='hidden' name='sequencia'/>
			<input type='hidden' name='elemento_serializado'/>
			<input type='hidden' name='idElemento' id='idElementoProp'/>
			<div id='divPropriedades' class=""></div>
		</form>
	</div>
	
	<div id="POPUP_FLUXO" title=<i18n:message key="desenhoFluxo.titulo.informacoesFluxo"/> style="overflow: auto;">
		<form name='form' id='form' method='post' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/desenhoFluxo/desenhoFluxo'>
			<input type="hidden" name="locale" id="locale" value="" />
			<input type="hidden" name="idTipoFluxo" id="idTipoFluxoCad" />
			<input type="hidden" name="dataInicio" id="dataInicioCad" />
			<input type="hidden" name="dataFim" id="dataFimCad" />
			<input type="hidden" name="acao" id="acaoCad" />
			<input type='hidden' name='idFluxo'/>
	  	    <input type='hidden' name='elementos_serializados'/>
	  	    <input type='hidden' name='sequencias_serializadas'/>
	  	    <input type='hidden' name='linhaAtual'/>
			
			<div class="columns clearfix">
				<div class="col_100">
					<div class="col_33">
						<fieldset>
							<label class="campoObrigatorio">
								<i18n:message key="citcorpore.comum.nome" />
							</label>
							<div>
								<input type='text' name="nomeFluxo" maxlength="70" class="Valid[Required] Description[citcorpore.comum.nome]" />
							</div>
						</fieldset>
					</div>
					<div class="col_66">
                         <fieldset>
                             <label class="campoObrigatorio">
                                 <i18n:message key="citcorpore.comum.descricao" />
                             </label>
                             <div>
                                 <input type='text' name="descricao" class="Valid[Required] Description[citcorpore.comum.descricao]" />
                             </div>
                         </fieldset>
					</div>
				</div>
				<div class="col_100">
					<div class="col_80">
						<fieldset>
							<label class="campoObrigatorio">
								<i18n:message key="cadastroFluxo.nomeClasse" />
							</label>
							<div>
								<input type='text' name="nomeClasseFluxo" maxlength="255" class="Valid[Required] Description[cadastroFluxo.nomeClasse]" />
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label>
								<i18n:message key="login.versao" />
							</label>
							<div>
								<input type='text' readonly="readonly" name="versao" class="" />
							</div>
						</fieldset>
					</div>
			</div>
			</div>
			<div class="col_100">
				<fieldset>
					<label>
						<i18n:message key="cadastroFLuxo.variaveis" />
					</label>
					<div>
						<textarea name="variaveis" rows="4" cols="50"></textarea>
					</div>
				</fieldset>
			</div>
			<div class="col_100">
			<button type='button' name='btnGravar' class="light"
				onclick='gravarCadastroFluxo();'>
				<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span><i18n:message key="citcorpore.comum.gravar" /></span>
			</button>
			<button type='button' name='btnImportarFluxo' class="light"
				onclick='exibeImportacaoFluxo()'>
				<img src="imagens/import.png">
					<span><i18n:message key="desenhoFluxo.btn.importarFluxo" />
				</span>
			</button>
			<button type='button' name='btnExportarFluxo' class="light"
				onclick='exportarFluxo()'>
				<img src="imagens/export.png">
					<span><i18n:message key="desenhoFluxo.btn.exportarFluxo" />
				</span>
			</button>
			<button type='button' name='btnExcluirFluxo' class="light"
				onclick='excluirFluxo();'>
				<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
					<span><i18n:message key="citcorpore.comum.excluir" />
				</span>
			</button>
			</div>
		</form>
	</div>	
	
	<div id="POPUP_IMPORTACAO" title=<i18n:message key="desenhoFluxo.titulo.selecionarArquivo"/> style="overflow: auto;">
	    <form name='formImportar' method='post' ENCTYPE="multipart/form-data" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/desenhoFluxo/desenhoFluxo.load'>
			<input type='hidden' name='acao' id='acaoImportar' value='I'/>
	
			<div class="columns clearfix">
				<div class="col_100">
						<fieldset>
							<label class="campoObrigatorio">
								<i18n:message key="desenhoFluxo.label.selecioneArquivo" />
							</label>
							<div>
								<input type='file' name='fileImportar' size="50" value=''/>
							</div>
						</fieldset>
				</div>
			</div>
            <button type='button'class="light"
				onclick='importarFluxo();'>
				<span><i18n:message key="citcorpore.comum.confirmar" />
				</span>
			</button>
	    </form>
	</div>
</body>
</html>

