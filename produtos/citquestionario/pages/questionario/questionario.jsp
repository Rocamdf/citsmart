<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="cit" uri="/tags/cit" %>
<%@taglib uri="/tags/i18n" prefix="i18n"%>
<%@ taglib prefix="c"   uri="/tags/jstl-c"%>
<%@ taglib prefix="fmt" uri="/tags/jstl-fmt"%> 
<%@ taglib prefix="sql" uri="/tags/jstl-sql"%>
<%@ taglib prefix="fn"  uri="/tags/jstl-fn"%>
<%@ taglib prefix="x"   uri="/tags/jstl-x"%>

<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%
    response.setCharacterEncoding("ISO-8859-1");
    response.setHeader( "Cache-Control", "no-cache");
    response.setHeader( "Pragma", "no-cache");
    response.setDateHeader ( "Expires", -1);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>     

    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script> 

<%--     <%@include file="/produtos/citquestionario/include/noCache/noCache.jsp" %> --%>

<%--     <%@include file="/produtos/citquestionario/include/titleComum/titleComum.jsp" %> --%>
    
<%--     <%@include file="/produtos/citquestionario/include/javaScriptsComuns/javaScriptsComuns.jsp" %>   --%>
    
    <%@include file="/include/cssComuns/cssComuns.jsp" %>
    
<style type="text/css">

body {
	background-color: white;
	background-image: url("");
	}
	.table {
		border-left:1px solid #ddd;
	}
.linha{
    background-color: #F1F1F1;
    border-color: -moz-use-text-color;
    border-right: thin outset;
    border-style: outset;
    border-width: thin;
    color: #000000;
    font-family: Arial;
    font-size: 12px;
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
	
	.tableLess {
	  font-family: arial, helvetica !important;
	  font-size: 10pt !important;
	  cursor: default !important;
	  margin: 0 !important;
	  background: white !important;
	  border-spacing: 0  !important;
	  width: 100%  !important;
	}
	
	.tableLess tbody {
	  background: transparent  !important;
	}
	
	.tableLess * {
	  margin: 0 !important;
	  vertical-align: middle !important;
	  padding: 2px !important;
	}
	
	.tableLess thead th {
	  font-weight: bold  !important;
	  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
	  text-align: center  !important;
	}
	
	.tableLess tbody tr:ACTIVE {
	  background-color: #fff  !important;
	}
	
	.tableLess tbody tr:HOVER {
	  background-color: #e7e9f9!important ;
	  cursor: pointer;
	}
	
	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}
	
	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}
	a{
		font-size: 11px!important;
		
	} 
td{
font-size: 11px!important;

}

.frame{

	background-color: white!important;
	color: white;
	min-height: 99%!important;
}

/* Thiago Fernandes - 29/10/2013 09:06 - Sol. 121468 - Adicionar campo obrigatorio questionario . */
.campoObrigatorioQuestionario:after {
	color: #FF0000;
	content: "*";
}

.campoObrigatorioQuestionario {
    font-size		:10px;
    color			:black;
    font-family		:Tahoma, Arial, Helvetica, sans-serif;
    text-decoration	:none;
   /*  width			:250px; */
    padding			:3px 0 0 0;
}

.btnQuest{
    background-color: #9DCE6E !important;
    background-image: linear-gradient(to bottom, #A8D37D, #8EC657) !important;
    background-repeat: repeat-x !important;
    border: 1px solid #8EC657 !important;
    box-shadow: 0 1px 0 0 rgba(255, 255, 255, 0.3) inset, 0 0 0 1px #74AF3B !important;
    color: #FFFFFF !important;
    text-shadow: 0 1px 0 rgba(0, 0, 0, 0.5) !important;
    font-weight: 600 !important;
    margin-bottom: 1em !important;
    margin-right: 1em !important;
    border-radius: 3px 3px 3px 3px;
    padding: 0.4em;
}.btnQuest:hover {
	color:#FFF;
	background: #A8D37D;
}.btnQuest:link {
	color:#FFF;
}.btnQuest:active {
	color:#FFF;
	position:relative;
	top:1px;
}a:active{
	color: #DFDFDF;
}

#idCategoriaQuestionarioAux{
	background: none repeat scroll 0 0 #F2F2F2;
    border: 1px solid #DDDDDD;
    border-radius: 2px 2px 2px 2px;
    height: 32px;
    margin-bottom: 0;
    margin-right: 5px;
    margin-top: 0;
    padding: 5px;
    width: 100%;

}

</style>
     
<!-- FCKEditor -->
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/fckeditor.js"></script>
<script type="text/javascript">
	var oFCKeditorTituloQuestaoQuestionario       = new FCKeditor( 'tituloQuestaoQuestionario' ) ;
	var oFCKeditorTituloQuestaoQuestionarioAgrupada  = new FCKeditor( 'tituloQuestaoQuestionarioAgrupada' ) ;
	var oFCKeditorTituloQuestaoMatriz             = new FCKeditor( 'tituloQuestaoMatriz' ) ;
	var oFCKeditorTituloQuestaoTabela             = new FCKeditor( 'tituloQuestaoTabela' ) ;
	var oFCKeditorTextoFixoAgrupada               = new FCKeditor( 'textoFixoAgrupada' ) ;
	var oFCKeditorTextoFixo                       = new FCKeditor( 'textoFixo' ) ;
	var oFCKeditorTextoInicialQuestaoQuestionario = new FCKeditor( 'textoInicialQuestaoQuestionario' ) ;
	function geraConfig(){
		DEFINEALLPAGES_fCallBackLoadDirect('');
	}
	function onInitQuestionario(){
		oFCKeditorTituloQuestaoQuestionario.BasePath = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/';
		oFCKeditorTituloQuestaoQuestionario.Config['ToolbarStartExpanded'] = true ;
	    oFCKeditorTituloQuestaoQuestionario.ToolbarSet   = 'Basic' ;
	    oFCKeditorTituloQuestaoQuestionario.Width = '100%' ;
	    oFCKeditorTituloQuestaoQuestionario.Height = '100' ;
	    oFCKeditorTituloQuestaoQuestionario.ReplaceTextarea() ;    
	    //---  
		oFCKeditorTituloQuestaoQuestionarioAgrupada.BasePath = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/';
		oFCKeditorTituloQuestaoQuestionarioAgrupada.Config['ToolbarStartExpanded'] = true ;
		oFCKeditorTituloQuestaoQuestionarioAgrupada.ToolbarSet   = 'Basic' ;
		oFCKeditorTituloQuestaoQuestionarioAgrupada.Width = '100%' ;
		oFCKeditorTituloQuestaoQuestionarioAgrupada.Height = '200' ;
		oFCKeditorTituloQuestaoQuestionarioAgrupada.ReplaceTextarea() ;    
	    //---  
		oFCKeditorTituloQuestaoMatriz.BasePath = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/';
		oFCKeditorTituloQuestaoMatriz.Config['ToolbarStartExpanded'] = true ;
		oFCKeditorTituloQuestaoMatriz.ToolbarSet   = 'Basic' ;
		oFCKeditorTituloQuestaoMatriz.Width = '100%' ;
		oFCKeditorTituloQuestaoMatriz.Height = '200' ;
		oFCKeditorTituloQuestaoMatriz.ReplaceTextarea() ;      
	    //---  
		oFCKeditorTituloQuestaoTabela.BasePath = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/';
		oFCKeditorTituloQuestaoTabela.Config['ToolbarStartExpanded'] = true ;
		oFCKeditorTituloQuestaoTabela.ToolbarSet   = 'Basic' ;
		oFCKeditorTituloQuestaoTabela.Width = '100%' ;
		oFCKeditorTituloQuestaoTabela.Height = '200' ;
		oFCKeditorTituloQuestaoTabela.ReplaceTextarea() ;      
	    //---  
		oFCKeditorTextoFixo.BasePath = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/';
		oFCKeditorTextoFixo.Config['ToolbarStartExpanded'] = true ;
		oFCKeditorTextoFixo.ToolbarSet   = 'Default' ;
		oFCKeditorTextoFixo.Width = '100%' ;
		oFCKeditorTextoFixo.Height = '500' ;
		oFCKeditorTextoFixo.ReplaceTextarea() ;      
	    //---  
		oFCKeditorTextoFixoAgrupada.BasePath = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/';
		oFCKeditorTextoFixoAgrupada.Config['ToolbarStartExpanded'] = true ;
		oFCKeditorTextoFixoAgrupada.ToolbarSet   = 'Default' ;
		oFCKeditorTextoFixoAgrupada.Width = '100%' ;
		oFCKeditorTextoFixoAgrupada.Height = '500' ;
		oFCKeditorTextoFixoAgrupada.ReplaceTextarea() ;      
	    //---  
		oFCKeditorTextoInicialQuestaoQuestionario.BasePath = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fckeditor/';
		oFCKeditorTextoInicialQuestaoQuestionario.Config['ToolbarStartExpanded'] = true ;
		oFCKeditorTextoInicialQuestaoQuestionario.ToolbarSet   = 'Default' ;
		oFCKeditorTextoInicialQuestaoQuestionario.Width = '100%' ;
		oFCKeditorTextoInicialQuestaoQuestionario.Height = '350' ;
		oFCKeditorTextoInicialQuestaoQuestionario.ReplaceTextarea() ;   

	}
    function load() {
    	$("#POPUP_IMPORTAR").dialog({
			autoOpen : false,		
			width : 600,
			height : 160,
			modal : true
		});
    	$("#POPUP_GRUPO").dialog({
			autoOpen : false,		
			width : 800,
			height : 210,
			modal : true
		});	
    	$("#POPUP_LISTAGEM_QUESTIONARIOS").dialog({
			autoOpen : false,		
			width : 650,
			height : 450,
			modal : true
		});	
    	$("#POPUP_GRUPO_EXCLUIR").dialog({
			autoOpen : false,		
			width : 595,
			height : 485,
			modal : true
		});	
    	$("#POPUP_GRUPO_ORDENAR").dialog({
			autoOpen : false,		
			width : 595,
			height : 485,
			modal : true
		});	
    	$("#POPUP_QUESTAO").dialog({
			autoOpen : false,		
			width : 865,
			height : 945,
			modal : true
		});	
    	$("#POPUP_TEXTO").dialog({
			autoOpen : false,		
			width : 800,
			height : 550,
			modal : true
		});
    	$("#POPUP_GRUPO_COPIAR").dialog({
			autoOpen : false,		
			width : 800,
			height : 285,
			modal : true
		});
    	$("#POPUP_GRUPO_ORDENAR_GRUPO").dialog({
			autoOpen : false,		
			width : 850,
			height : 490,
			modal : true
		});
    	$("#POPUP_GRUPO_EXCLUIR_GRUPO").dialog({
			autoOpen : false,		
			width : 850,
			height : 490,
			modal : true
		});
    	$("#POPUP_QUESTAO_AGRUPADA").dialog({
			autoOpen : false,		
			width : 840,
			height : 900,
			modal : true
		});
    	$("#POPUP_COPIAR_QUESTAO").dialog({
			autoOpen : false,		
			width : 350,
			height : 160,
			modal : true
		});
	}
	HTMLUtils.addEvent(window, "load", onInitQuestionario, false);
	HTMLUtils.addEvent(window, "load", geraConfig, false);
	HTMLUtils.addEvent(window, "load", load, false);
</script>   
<!-- /FCKEditor -->

<%--     <%@include file="/produtos/citquestionario/include/menu/menuConfig.jsp" %> --%>
    
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/GrupoQuestionarioDTO.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/QuestaoQuestionarioDTO.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/OpcaoRespostaQuestionarioDTO.js"></script>
    
<%--     <%@include file="/produtos/citquestionario/include/cssComuns/cssComuns.jsp" %> --%>
    
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>

    
<!-- Area de JavaScripts -->
<script>
    var idControleTabela = 0;        
    var colAtualQuestaoAgrupada = 0;
    var linAtualQuestaoAgrupada = 0;
    var objsQuestoesAgrupadas = new Array();
    var objsCabecalhosLinha = new Array();    
    var objsCabecalhosColuna = new Array();    
        
    restoreHistorico = function(id){
        document.form.restore({idHistoricoBancario:id});
    };
    
    atualizaListagem = function(){
        document.form.fireEvent('atualizaListagem');
    };
    
    geraRadio = function(row, obj){
        row.cells[0].innerHTML = '<input type="radio" name="idHistoricoListagem" value="' + obj.idHistoricoBancario + '" onclick="restoreHistorico(\'' + obj.idHistoricoBancario + '\')"/>';
    };  
    
    adicionarGrupo = function(){
        if (StringUtils.isBlank(document.formGrupo.nomeGrupoQuestionario.value)){
        	alert('<i18n:message key="questionario.informeGrupo" />');
            document.formGrupo.nomeGrupoQuestionario.focus();
            return;
        }
        
        addicionarGrupoFunction(document.formGrupo.nomeGrupoQuestionario.value, null);
        
        //POPUP_GRUPO.hide();
        $("#POPUP_GRUPO").dialog("close");
    };
    
    addicionarGrupoFunction = function(nomeGrupo, idGrupo){
        var grupoDto = new CIT_GrupoQuestionarioDTO();
        grupoDto.idGrupoQuestionario = idGrupo;
        grupoDto.nomeGrupoQuestionario = StringUtils.trim(nomeGrupo);
        grupoDto.infoGrupo = '<table id="tblItem_' + idControleTabela + '" class="tableLess" width="100%" border="1"><tr><td colspan="2"><b><font size="16">' + grupoDto.nomeGrupoQuestionario + '</font></b></td></tr>' +
                            '</table>';
        
        grupoDto.idControleQuestao = new String(idControleTabela);
        
        //HTMLUtils.addRow('tblConfigQuestionario', null, '', grupoDto, ['', 'infoGrupo'], null, '<i18n:message key="questionario.jaExisteGrupo" />', [execAddGrupo], editarGrupo, null, true);
        HTMLUtils.addRow('tblConfigQuestionario', null, '', grupoDto, ['', 'infoGrupo'], null, '<i18n:message key="questionario.jaExisteGrupo" />', [execAddGrupo], null, null, true);
        
        idControleTabela = idControleTabela + 1;
    };
    
    atualizarGrupo = function(){
        if (StringUtils.isBlank(document.formGrupo.nomeGrupoQuestionario.value)){
            alert('<i18n:message key="questionario.informeGrupo" />');
            document.formGrupo.nomeGrupoQuestionario.focus();
            return;
        }
        
        //atualizarGrupoFunction(document.formGrupo.nomeGrupoQuestionario.value);
        //POPUP_GRUPO.hide();
        $("#POPUP_GRUPO").dialog("close");
        JANELA_AGUARDE_MENU.show();
        document.formGrupo.fireEvent('atualizarNomeGrupo');
    };
    
    atualizarGrupoFunction = function(nomeGrupo){
        var index = document.getElementById('idIndexGrupo').value;
            
        var grupoDto = HTMLUtils.getObjectByTableIndex('tblConfigQuestionario', index);
        
        grupoDto.nomeGrupoQuestionario = StringUtils.trim(nomeGrupo);
        grupoDto.infoGrupo = '<table id="tblItem_' + grupoDto.idControleQuestao + '" width="100%" border="1"><tr><td colspan="2"><b><font size="16">' + grupoDto.nomeGrupoQuestionario + '</font></b></td></tr>' +
                            '</table>';
        
        HTMLUtils.updateRow('tblConfigQuestionario', null, '', grupoDto, ['', 'infoGrupo'], null, '<i18n:message key="questionario.jaExisteGrupo" />', [execAddGrupo], editarGrupo, null, index, true);
        
        idControleTabela = idControleTabela + 1;
    };  
    
    mostrarAdicionarNovoGrupo = function(){
        document.getElementById('idIndexGrupo').value = 0;
        
        document.getElementById('idGrupoQuestionario').value = '';
        document.getElementById('nomeGrupoQuestionario').value = '';
        
        document.getElementById('idControleQuestaoGrupo').value = '';
        
        document.getElementById('btnAdicionarGrupo').style.display = 'block';
        document.getElementById('btnAtualizarGrupo').style.display = 'none';      
        
        //POPUP_GRUPO.show(); 
        $("#POPUP_GRUPO").dialog("open");
    };
    
    editarGrupo = function(row, obj){
        document.getElementById('idIndexGrupo').value = row.rowIndex;
        
        document.getElementById('idGrupoQuestionario').value = obj.idGrupoQuestionario;
        document.getElementById('nomeGrupoQuestionario').value = obj.nomeGrupoQuestionario;
        
        document.getElementById('idControleQuestaoGrupo').value = obj.idControleQuestao;
        
        document.getElementById('btnAdicionarGrupo').style.display = 'none';
        document.getElementById('btnAtualizarGrupo').style.display = 'block';     
        
        //POPUP_GRUPO.show();
        $("#POPUP_GRUPO").dialog("open");
    };
    
    mostrarAdicionarQuestao = function(idControleTabela){
        HTMLUtils.clearForm(document.formQuestao);
        document.formQuestao.tipo[0].disabled = false;
        document.formQuestao.tipo[1].disabled = false;
        document.formQuestao.tipo[2].disabled = false;
        document.formQuestao.tipo[3].disabled = false;
        document.formQuestao.tipo[4].disabled = false;            
        
        document.getElementById('divQuestao').style.display = 'none';
        document.getElementById('divTextoFixo').style.display = 'none'; 
        document.getElementById('divHTML').style.display = 'none';         
        document.getElementById('divMatriz').style.display = 'none';
        document.getElementById('divTabela').style.display = 'none';
        document.getElementById('divTamanho').style.display = 'none';
        document.getElementById('divDecimais').style.display = 'none';
        document.getElementById('divFaixaNumeros').style.display = 'none';
        document.getElementById('divFaixaDecimais').style.display = 'none';
        document.getElementById('divCheckboxRadioCombo').style.display = 'none';
        document.getElementById('divPonderada').style.display = 'none';
        document.getElementById('divCalculada').style.display = 'none';
        document.getElementById('divEditavel').style.display = 'none';
        document.getElementById('divPeso').style.display = 'none';  
        document.getElementById('btnAtuOpcao').style.display = 'none';
        document.getElementById('btnAddOpcao').style.display = 'none';        
        document.getElementById('btnComplementoOpcao').style.display = 'none';
        document.getElementById('btnMoveOpcaoAcima').style.display = 'none';
        document.getElementById('btnMoveOpcaoAbaixo').style.display = 'none';
        document.getElementById('btnRemOpcao').style.display = 'none';   
        document.getElementById('divOpcaoQuestao').style.display = 'none';  
        document.getElementById('btnNovaOpcao').style.display = 'block';
        
        HTMLUtils.deleteAllRows('tblOpcoes');
        HTMLUtils.removeAllOptions('lstOpcoesDefault');
        document.getElementById('idControleQuestao').value = idControleTabela;

        FCKeditorAPI.GetInstance('tituloQuestaoQuestionario').SetData('');
        document.getElementById('btnAddQuestao').style.display = 'block';
        document.getElementById('btnAtuQuestao').style.display = 'none';

        FCKeditorAPI.GetInstance('tituloQuestaoMatriz').SetData('');
        document.getElementById('btnAddMatriz').style.display = 'block';
        document.getElementById('btnAtuMatriz').style.display = 'none';

        FCKeditorAPI.GetInstance('tituloQuestaoTabela').SetData('');
        document.getElementById('btnAddTabela').style.display = 'block';
        document.getElementById('btnAtuTabela').style.display = 'none';
        objsQuestoesAgrupadas = new Array();
        objsCabecalhosLinha = new Array();
        objsCabecalhosColuna = new Array();
        
        document.getElementById('btnAddHTML').style.display = 'block';
        document.getElementById('btnAtuHTML').style.display = 'none';  

        //POPUP_QUESTAO.showInYPosition({top:40});
        $("#POPUP_QUESTAO").dialog("open");
    };

    mostraQuestao = function(row, obj){
        HTMLUtils.clearForm(document.formQuestao);
        document.getElementById('divTextoInicialQuestao').innerHTML = '';
        
        HTMLUtils.setValuesForm(document.formQuestao, obj);
        if (obj.tipo == 'Q'){
            document.formQuestao.tipo[0].checked = true;
        }else if (obj.tipo == 'T'){
            document.formQuestao.tipo[1].checked = true;
        }else if (obj.tipo == 'M'){
            document.formQuestao.tipo[2].checked = true;
        }else if (obj.tipo == 'L'){
            document.formQuestao.tipo[3].checked = true;
        }else if (obj.tipo == 'C'){
        	document.formQuestao.tipo[4].checked = true;
        }                        
        document.formQuestao.tipo[0].disabled = true;
        document.formQuestao.tipo[1].disabled = true;
        document.formQuestao.tipo[2].disabled = true;
        document.formQuestao.tipo[3].disabled = true;
        document.formQuestao.tipo[4].disabled = true;         
        verificaTipo();
        verificaTipoQuestao();
        
        document.getElementById('idIndex').value = row.rowIndex;
        document.getElementById('idControleQuestao').value = obj.idControleQuestao;

        if (obj.tipo == 'Q'){
            document.formQuestao.tituloQuestaoQuestionario.value = obj.tituloQuestaoQuestionario;
            FCKeditorAPI.GetInstance('tituloQuestaoQuestionario').SetData(obj.tituloQuestaoQuestionario);
            document.getElementById('btnAddQuestao').style.display = 'none';
            document.getElementById('btnAtuQuestao').style.display = 'block';
            var objs = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(obj.serializeOpcoesResposta);  
            HTMLUtils.deleteAllRows('tblOpcoes');          
            HTMLUtils.removeAllOptions('lstOpcoesDefault');
            if (objs != undefined && objs != null){
                for(var i = 0; i < objs.length; i++){
                    var opcaoResposta = objs[i];
                    if (opcaoResposta.exibeComplemento == 'S') {
                    	opcaoResposta.questaoComplementoDto = ObjectUtils.deserializeObject(opcaoResposta.serializeQuestaoComplemento);
                    }else{
                    	opcaoResposta.questaoComplementoDto = null;
                    }		   
                	HTMLUtils.addRow('tblOpcoes', null, '', opcaoResposta, ['', 'titulo', 'valor', 'peso'], null, '', null, mostraOpcao, null, true);
                    HTMLUtils.addOption('lstOpcoesDefault', opcaoResposta.titulo, '' + i);
                }
            }
            document.getElementById('divTextoInicialQuestao').innerHTML = obj.textoInicial;
            document.getElementById('btnNovaOpcao').style.display = 'block';
        }else if (obj.tipo == 'T'){
            document.formQuestao.textoFixo.value = obj.tituloQuestaoQuestionario;
            FCKeditorAPI.GetInstance('textoFixo').SetData(obj.tituloQuestaoQuestionario);
        }else if (obj.tipo == 'M'){
            document.formQuestao.tituloQuestaoMatriz.value = obj.tituloQuestaoQuestionario;
            if (obj.imprime == 'S') {
                document.formQuestao.imprimeMatriz[0].checked = true;
            }else{
            	document.formQuestao.imprimeMatriz[1].checked = true;
            }        
            FCKeditorAPI.GetInstance('tituloQuestaoMatriz').SetData(obj.tituloQuestaoQuestionario);
            document.formQuestao.siglaMatriz.value = obj.sigla;

            if (obj.ultimoValor == 'S') {
                document.formQuestao.ultimoValorMatriz[0].checked = true;
            }else if (obj.ultimoValor == 'G') {
                document.formQuestao.ultimoValorMatriz[1].checked = true;               
            }               
            
            document.getElementById('btnAddMatriz').style.display = 'none';
            document.getElementById('btnAtuMatriz').style.display = 'block';
            montarMatriz();
            objsQuestoesAgrupadas = new Array();
            var objsQuestoesAux = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(obj.serializeQuestoesAgrupadas);
            var posicao = 0;
            var qtdeLinhas = document.formQuestao.qtdeLinhas.value;
            var qtdeColunas = document.formQuestao.qtdeColunas.value;
            for(var l = 1; l <= qtdeLinhas; l++){
                var objsColunasMatriz = new Array();
            	for(var c = 1; c <= qtdeColunas; c++){
            		objsColunasMatriz[c-1] = objsQuestoesAux[posicao];
                	posicao = posicao + 1;
            	}
            	objsQuestoesAgrupadas[l-1] = objsColunasMatriz;
            }    
            objsCabecalhosLinha =  ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(obj.serializeCabecalhosLinha); 
            objsCabecalhosColuna =  ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(obj.serializeCabecalhosColuna); 
            mostraCabecalhos();               
            configuraBotoesMatrizTabela();
        }else if (obj.tipo == 'L'){
        	document.formQuestao.qtdeColunasTabela.value = obj.qtdeColunas;
            document.formQuestao.tituloQuestaoTabela.value = obj.tituloQuestaoQuestionario;
            document.formQuestao.siglaTabela.value = obj.sigla;
            if (obj.imprime == 'S') {
                document.formQuestao.imprimeTabela[0].checked = true;
            }else{
                document.formQuestao.imprimeTabela[1].checked = true;
            }        

            if (obj.ultimoValor == 'S') {
                document.formQuestao.ultimoValorTabela[0].checked = true;
            }else if (obj.ultimoValor == 'G') {
                document.formQuestao.ultimoValorTabela[1].checked = true;               
            }               
            
            FCKeditorAPI.GetInstance('tituloQuestaoTabela').SetData(obj.tituloQuestaoQuestionario);
            document.getElementById('btnAddTabela').style.display = 'none';
            document.getElementById('btnAtuTabela').style.display = 'block';
            montarTabela(false);
            objsQuestoesAgrupadas = new Array();
            var objsColunas = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(obj.serializeQuestoesAgrupadas);
            objsQuestoesAgrupadas[0] = objsColunas;
            configuraBotoesMatrizTabela();                        
        }else if (obj.tipo == 'P'){    
            document.formQuestao.textoHTML.value = obj.valorDefault;
            document.getElementById('btnAddHTML').style.display = 'none';
            document.getElementById('btnAtuHTML').style.display = 'block';                 
        }  

        if (obj.tipoQuestao == '2' || obj.tipoQuestao == 'N' || obj.tipoQuestao == 'V' || obj.tipoQuestao == '%'){
            if (obj.valorPermitido1 != null && obj.valorPermitido1 != ''){
            	document.formQuestao.valPerm1.value = NumberUtil.format(NumberUtil.toDouble(obj.valorPermitido1), 3, ",", ".");
            }else{
            	document.formQuestao.valPerm1.value = '';
            }
            if (obj.valorPermitido2 != null && obj.valorPermitido2 != ''){
            	document.formQuestao.valPerm2.value = NumberUtil.format(NumberUtil.toDouble(obj.valorPermitido2), 3, ",", ".")
            }else{
            	document.formQuestao.valPerm2.value = '';
            }
        }                             
        
        //POPUP_QUESTAO.show();
        //POPUP_QUESTAO.showInYPosition({top:40});
        $("#POPUP_QUESTAO").dialog("open");
    };
    
    execAddGrupo = function(row, obj){
        row.cells[0].innerHTML = '<img id="grupo_' + row.rowIndex + '" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/updItens.gif" title="<i18n:message key="questionario.editarNomeGrupo" />" border="0"/> <img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/addItens.png" title="<i18n:message key="questionario.adicionarNovoElemento" />" border="0" onclick="mostrarAdicionarQuestao(' + idControleTabela + ')"/> <img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/ordenaItens.gif" title="<i18n:message key="questionario.ordenarElemento" />" border="0" onclick="ordenarItens(' + obj.idControleQuestao + ')"/> <img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/exclItens.png" title="<i18n:message key="questionario.excluirElemento" />" border="0" onclick="excluirItens(' + obj.idControleQuestao + ')"/>';
        document.getElementById('grupo_' + row.rowIndex).onclick = function(){editarGrupo(row, obj)};
    };
    
    ordenarItens = function(idControleQuestao){
        var tblGrupo = 'tblItem_' + idControleQuestao;
        
        document.getElementById('tabelaOrdenar').value = tblGrupo;
        
        var objsQuestoes = HTMLUtils.getObjectsByTableId(tblGrupo);
        
        HTMLUtils.deleteAllRows('tblOrdenar');
        for(var i = 0; i < objsQuestoes.length; i++){
            HTMLUtils.addRow('tblOrdenar', null, '', objsQuestoes[i], ['', 'tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', [execAddItemOrdenar], null, null, true);
        }

        if (!confirm('<i18n:message key="questionario.salvarAntesOrdenacao" />')){
            return;
        }
        
        //POPUP_GRUPO_ORDENAR.show();
        $("#POPUP_GRUPO_ORDENAR").dialog("open");
    };
    
    ordenarGrupos = function(){
        var tblGrupo = 'tblConfigQuestionario';
        
        var objsGrupos = HTMLUtils.getObjectsByTableId(tblGrupo);
        
        HTMLUtils.deleteAllRows('tblOrdenarGrupo');
        for(var i = 0; i < objsGrupos.length; i++){
            HTMLUtils.addRow('tblOrdenarGrupo', null, '', objsGrupos[i], ['', 'infoGrupo'], null, '<i18n:message key="questionario.jaExisteGrupo" />', [execAddItemOrdenarGrupo], null, null, true);
        }

        if (!confirm('<i18n:message key="questionario.salvarAntesOrdenacao" />')){
            return;
        }
        
        //POPUP_GRUPO_ORDENAR_GRUPO.show();
        $("#POPUP_GRUPO_ORDENAR_GRUPO").dialog("open");
    };
    
    execAddItemOrdenarGrupo = function(row, obj){
        row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/baixo.gif" title="Clique aqui para colocar o item para baixo" border="0" onclick="moveLinhaBaixoGrupo(this)"/>&nbsp;<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/alto.jpg" title="Clique aqui para colocar o item para cima" border="0" onclick="moveLinhaCimaGrupo(this)"/>';
    };
    execAddItemExcluirGrupo = function(row, obj){
        row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/btnExcluirRegistro.gif" title="Clique aqui para excluir o grupo" border="0" onclick="excluirGrupoItem(this)"/>';
    };
    
    execAddItemOrdenar = function(row, obj){
        row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/baixo.gif" title="Clique aqui para colocar o item para baixo" border="0" onclick="moveLinhaBaixo(this)"/>&nbsp;<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/alto.jpg" title="Clique aqui para colocar o item para cima" border="0" onclick="moveLinhaCima(this)"/>';
    };
    
    excluirItens = function(idControleQuestao){
        var tblGrupo = 'tblItem_' + idControleQuestao;
        
        document.getElementById('tabelaExcluir').value = tblGrupo;
        
        var objsQuestoes = HTMLUtils.getObjectsByTableId(tblGrupo);
        
        HTMLUtils.deleteAllRows('tblExcluir');
        for(var i = 0; i < objsQuestoes.length; i++){
            HTMLUtils.addRow('tblExcluir', null, '', objsQuestoes[i], ['', 'tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', [execAddItemExcluir], null, null, true);
        }

        if (!confirm('<i18n:message key="questionario.deveExcluirItens" />')){
            return;
        }
                
        //POPUP_GRUPO_EXCLUIR.show();
        $("#POPUP_GRUPO_EXCLUIR").dialog("open");
    };
    
    execAddItemExcluir = function(row, obj){
        row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/btnExcluirRegistro.gif" title="Clique aqui para excluir" border="0" onclick="excluirLinha(this)"/>';
    };
    
    moveRow = function(idTabela, rowIndexOld, rowIndexNew){
    	var tbl = document.getElementById(idTabela);
    	
    	var rowOld = tbl.rows[rowIndexOld];
    	var rowNew = tbl.rows[rowIndexNew];
    	
    	var idOld = null;
    	var idNew = null;
    	if (rowOld.id){
    		idOld = rowOld.id;
    		idNew = rowNew.id;
    	}
    	
    	var objs = new Array();
    	for (var i = 0; i < rowOld.cells.length; i++){
    		objs[i] = rowOld.cells[i].innerHTML;
    				
    		rowOld.cells[i].innerHTML = rowNew.cells[i].innerHTML;
    		rowNew.cells[i].innerHTML = objs[i];		
    	}
    	
    	if (rowOld.id){
    		rowNew.id = idOld;
    		rowOld.id = idNew;
    	}
    }
    
    moveLinhaCima = function(objImg){
        var indiceNew = objImg.parentNode.parentNode.rowIndex - 1;
        if (indiceNew == 0) return;
        
        var indice = objImg.parentNode.parentNode.rowIndex;
        
        moveRow('tblOrdenar', indice, indiceNew);
        moveRow(document.getElementById('tabelaOrdenar').value, indice, indiceNew);
    };
    moveLinhaBaixo = function(objImg){
        var indiceNew = objImg.parentNode.parentNode.rowIndex + 1;
        if (indiceNew == (document.getElementById('tblOrdenar').length - 1)) return;
        
        var indice = objImg.parentNode.parentNode.rowIndex;
                        
        moveRow('tblOrdenar', indice, indiceNew); 
        moveRow(document.getElementById('tabelaOrdenar').value, indice, indiceNew);
    };  
    moveLinhaCimaGrupo = function(objImg){
        var indiceNew = objImg.parentNode.parentNode.rowIndex - 1;
        if (indiceNew == 0) return;
        
        var indice = objImg.parentNode.parentNode.rowIndex;
        
        moveRow('tblOrdenarGrupo', indice, indiceNew);
        moveRow('tblConfigQuestionario', indice, indiceNew);
    };
    moveLinhaBaixoGrupo = function(objImg){
        var indiceNew = objImg.parentNode.parentNode.rowIndex + 1;
        if (indiceNew == (document.getElementById('tblOrdenarGrupo').length - 1)) return;
        
        var indice = objImg.parentNode.parentNode.rowIndex;
                        
        moveRow('tblOrdenarGrupo', indice, indiceNew); 
        moveRow('tblConfigQuestionario', indice, indiceNew);
    };  
    
    excluirLinha = function(objImg){
        if (!confirm('<i18n:message key="questionario.desejaExcluirQuestao" />')) return;
        var indice = objImg.parentNode.parentNode.rowIndex;
        
        HTMLUtils.deleteRow('tblExcluir', indice);
        HTMLUtils.deleteRow(document.getElementById('tabelaExcluir').value, indice);
    };

    excluirGrupoItem = function(objImg){
        if (!confirm('<i18n:message key="questionario.desejaExcluirGrupo" />')) return;
        
        var indice = objImg.parentNode.parentNode.rowIndex;
        
        HTMLUtils.deleteRow('tblExcluirGrupo', indice);
        HTMLUtils.deleteRow('tblConfigQuestionario', indice);
    };
    
    adicionarQuestao = function(){
        if (StringUtils.isBlank(FCKeditorAPI.GetInstance('tituloQuestaoQuestionario').GetXHTML())){
            alert('<i18n:message key="questionario.informeTituloQuestao" />');
            return;
        }
        if (StringUtils.isBlank(document.formQuestao.tipoQuestao.value)){
            alert('<i18n:message key="questionario.informeTipoQuestao" />');
            document.formQuestao.tipoQuestao.focus();
            return;
        }       

       	var questaoCalculada = 'N';
        if (document.formQuestao.tipoQuestao.value == 'T' || document.formQuestao.tipoQuestao.value == 'A' || 
            document.formQuestao.tipoQuestao.value == 'N' || document.formQuestao.tipoQuestao.value == 'V' || 
            document.formQuestao.tipoQuestao.value == 'D' || document.formQuestao.tipoQuestao.value == 'H' || 
            document.formQuestao.tipoQuestao.value == 'G' || document.formQuestao.tipoQuestao.value == 'L'){
        	if (document.formQuestao.calculada[0].checked){
            	questaoCalculada = 'S';
        	}       
	    }
    
        if (questaoCalculada == 'S' && document.formQuestao.sigla.value == ''){
            alert('<i18n:message key="questionario.informeSiglaQuestao" />');
            document.formQuestao.sigla.focus();
            return;
        }

        if (document.formQuestao.ultimoValor[2].checked && StringUtils.isBlank(document.formQuestao.sigla.value)){
            alert('<i18n:message key="questionario.informeSiglaQuestao" />');
            document.formQuestao.sigla.focus();
            return;
        }
        
        var obrQuestao = 'N';
        if (document.formQuestao.obrigatoria[0].checked){
            obrQuestao = 'S';
        }
                       
        var infoResposta = 'L';
        if (document.formQuestao.infoResposta[1].checked){
            infoResposta = 'B';
        }   
        
        var questaoPonderada = 'N';
        if (document.formQuestao.ponderada[0].checked){
            questaoPonderada = 'S';
        }       

        var listagem = '';
        if (document.formQuestao.tipoQuestao.value == '8'){
            listagem = document.formQuestao.nomeListagem.value;
        }
              
        var peso = '';
        var valor = '';
        var opcao = '';
        var objsOpcoesRespostas = new Array();
        var tipoQuestao = document.formQuestao.tipoQuestao.value;
        if (tipoQuestao == 'R' || tipoQuestao == 'C' || tipoQuestao == 'X'){
        	var tbl = document.getElementById('tblOpcoes');
            if (tbl.rows.length == 1){
                alert('<i18n:message key="questionario.informeNomeFormulario" />');
                return;
            }

            objsOpcoesRespostas = HTMLUtils.getObjectsByTableId('tblOpcoes');
        }
                        
         var objsSerializadosQ = ObjectUtils.serializeObjects(objsOpcoesRespostas);

         var ultimoValor = 'N';
         if (document.formQuestao.ultimoValor[1].checked){
        	 ultimoValor = 'S';
         }else if (document.formQuestao.ultimoValor[2].checked){ 
        	 ultimoValor = 'G';
         }

         var imprime = 'S';
         if (document.formQuestao.imprime[1].checked){
             imprime = 'N';
         }

         var editavel = 'S';
         if (document.formQuestao.editavel[1].checked){
        	 editavel = 'N';
         }

         adicionarQuestaoFunction('Q',null,
                                 FCKeditorAPI.GetInstance('tituloQuestaoQuestionario').GetXHTML(),
                                 document.formQuestao.tipoQuestao.value,
                                 document.formQuestao.unidade.value,
                                 document.formQuestao.valoresReferencia.value,
                                 obrQuestao,
                                 questaoPonderada,
                                 questaoCalculada,
                                 infoResposta,
                                 document.getElementById('divTextoInicialQuestao').innerHTML,
                                 document.formQuestao.tamanho.value,
                                 document.formQuestao.decimais.value,
                                 null,null,null,null,
                                 listagem,
                                 objsSerializadosQ,
                                 null, null, null, ultimoValor, null, null,
                                 document.formQuestao.sigla.value, 
                                 imprime, 
                                 editavel,
                                 document.formQuestao.valPerm1.value,
                                 document.formQuestao.valPerm2.value,
                                 document.formQuestao.idImagem.value,
                                 null);
     
         //POPUP_QUESTAO.hide();
         $("#POPUP_QUESTAO").dialog("close");
    };

    adicionarQuestaoFunction = function(tipo,
                                        idQuestaoOrigem,
                                        textoConteudo, 
                                        tipoQuestao, 
                                        unidadeQuestao, 
                                        valoresRefQuestao, 
                                        obrQuestao, 
                                        questaoPonderada, 
                                        questaoCalculada,
                                        infoResposta, 
                                        textoInicial,
                                        tamanhoQuestao,
                                        decimaisQuestao,
                                        qtdeLinhas,
                                        qtdeColunas,
                                        cabecalhoLinhas,
                                        cabecalhoColunas,
                                        nomeListagem,
                                        objsSerializadosQ,
                                        objsSerializadosQAgrup,
                                        objsSerializadosCabecalhosLinha,
                                        objsSerializadosCabecalhosColuna,
                                        ultimoValor,
                                        idSubQuestionario,
                                        abaResultSubForm,
                                        sigla,
                                        imprime,
                                        editavel,
                                        valorPermitido1,
                                        valorPermitido2,
                                        idImagem,
                                        valorDefault){
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.idQuestaoOrigem = idQuestaoOrigem;
//        alert(textoConteudo+' - '+idQuestaoOrigem);
        questaoDto.tipo = tipo;
        questaoDto.tituloQuestaoQuestionario = textoConteudo;
        questaoDto.tipoQuestao = tipoQuestao;
        
        questaoDto.idTabela = tblAdicionar;
        questaoDto.idControleQuestao = document.getElementById('idControleQuestao').value;
        
        questaoDto.unidade = unidadeQuestao;
        questaoDto.valoresReferencia = valoresRefQuestao;
        questaoDto.obrigatoria = obrQuestao;
        questaoDto.infoResposta = infoResposta;
        questaoDto.ponderada = questaoPonderada;
        questaoDto.calculada = questaoCalculada;
        
        questaoDto.ultimoValor = ultimoValor;
        
        questaoDto.serializeOpcoesResposta = objsSerializadosQ;
        questaoDto.serializeQuestoesAgrupadas = objsSerializadosQAgrup;
        questaoDto.serializeCabecalhosLinha = objsSerializadosCabecalhosLinha;
        questaoDto.serializeCabecalhosColuna = objsSerializadosCabecalhosColuna;
                
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
        if (tipoQuestao == 'T'){
            if (StringUtils.isBlank(tamanhoQuestao)){
                alert('<i18n:message key="questionario.respostaQuestao" />');
                document.formQuestao.tamanho.focus();
                return;
            }   
            questaoDto.tamanho = tamanhoQuestao;    
        }
        if (tipoQuestao == 'N'){
        	questaoDto.tamanho = tamanhoQuestao;
        }
        if (tipoQuestao == 'V' || tipoQuestao == '%'){
            if (StringUtils.isBlank(tamanhoQuestao)){
                alert('<i18n:message key="questionario.respostaQuestao" />');
                document.formQuestao.tamanho.focus();
                return;
            }
            if (StringUtils.isBlank(decimaisQuestao)){
                alert('<i18n:message key="questionario.numeroCasasDecimais" />');
                document.formQuestao.decimais.focus();
                return;
            }
            questaoDto.tamanho = tamanhoQuestao;
            questaoDto.decimais = decimaisQuestao;
        }   
            
        questaoDto.textoInicial = textoInicial;
        questaoDto.qtdeLinhas = qtdeLinhas;
        questaoDto.qtdeColunas = qtdeColunas;
        questaoDto.cabecalhoLinhas = cabecalhoLinhas;
        questaoDto.cabecalhoColunas = cabecalhoColunas;
        questaoDto.nomeListagem = nomeListagem;
        
        questaoDto.idSubQuestionario = idSubQuestionario;
        questaoDto.abaResultSubForm = abaResultSubForm;

        questaoDto.sigla = sigla;
        questaoDto.imprime = imprime;
        questaoDto.editavel = editavel;

        questaoDto.valorPermitido1 = valorPermitido1;
        questaoDto.valorPermitido2 = valorPermitido2;
        
        questaoDto.idImagem = idImagem;
        questaoDto.valorDefault = valorDefault;
        
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.addRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;      
    };
    
    atualizarQuestao = function(){
        if (StringUtils.isBlank(FCKeditorAPI.GetInstance('tituloQuestaoQuestionario').GetXHTML())){
            alert('<i18n:message key="questionario.informeTituloQuestao" />');
            return;
        }
        if (StringUtils.isBlank(document.formQuestao.tipoQuestao.value)){
            alert('<i18n:message key="questionario.informeTipoQuestao" />');
            document.formQuestao.tipoQuestao.focus();
            return;
        }       

        var questaoCalculada = 'N';
        if (document.formQuestao.tipoQuestao.value == 'T' || document.formQuestao.tipoQuestao.value == 'A' || 
            document.formQuestao.tipoQuestao.value == 'N' || document.formQuestao.tipoQuestao.value == 'V' || 
            document.formQuestao.tipoQuestao.value == 'D' || document.formQuestao.tipoQuestao.value == 'H' ||
            document.formQuestao.tipoQuestao.value == 'G' || document.formQuestao.tipoQuestao.value == 'L'){
            if (document.formQuestao.calculada[0].checked){
                questaoCalculada = 'S';
            }       
        }
    
        if (questaoCalculada == 'S' && document.formQuestao.sigla.value == ''){
            alert('<i18n:message key="questionario.informeSiglaQuestao" />');
            document.formQuestao.sigla.focus();
            return;
        }

        if (document.formQuestao.ultimoValor[2].checked && StringUtils.isBlank(document.formQuestao.sigla.value)){
            alert('<i18n:message key="questionario.informeSiglaQuestao" />');
            document.formQuestao.sigla.focus();
            return;
        }
        
        var obrQuestao = 'N';
        if (document.formQuestao.obrigatoria[0].checked){
            obrQuestao = 'S';
        }

        var tipoQuestao = document.formQuestao.tipoQuestao.value;
        
        var questaoPonderada = 'N';
        if (document.formQuestao.ponderada[0].checked){
            questaoPonderada = 'S';
        }       

        var infoResposta = 'L';
        if (document.formQuestao.infoResposta[1].checked){
            infoResposta = 'B';
        }   

        var listagem = '';
        if (document.formQuestao.tipoQuestao.value == '8'){
            listagem = document.formQuestao.nomeListagem.value;
        }
        
        var opcao = '';
        var peso = '';
        var objsOpcoesRespostas = new Array();
        if (tipoQuestao == 'R' || tipoQuestao == 'C' || tipoQuestao == 'X'){
        	var tbl = document.getElementById('tblOpcoes');
        	if (tbl.rows.length == 1){
                alert('<i18n:message key="questionario.informeNomeFormulario" />');
                return;
            }

            objsOpcoesRespostas = HTMLUtils.getObjectsByTableId('tblOpcoes');
        }           
        
        var objsSerializadosQ = ObjectUtils.serializeObjects(objsOpcoesRespostas);

        var ultimoValor = 'N';
        if (document.formQuestao.ultimoValor[1].checked){
       	 ultimoValor = 'S';
        }else if (document.formQuestao.ultimoValor[2].checked){
       	 ultimoValor = 'G';
        }        

        var imprime = 'S';
        if (document.formQuestao.imprime[1].checked){
            imprime = 'N';
        }

        var editavel = 'S';
        if (document.formQuestao.editavel[1].checked){
        	editavel = 'N';
        }
                
        atualizarQuestaoFunction(   FCKeditorAPI.GetInstance('tituloQuestaoQuestionario').GetXHTML(), 
                                    document.formQuestao.tipoQuestao.value,
                                    document.formQuestao.unidade.value,
                                    document.formQuestao.valoresReferencia.value,
                                    obrQuestao,
                                    questaoPonderada,
                                    questaoCalculada,
                                    infoResposta,
                                    document.getElementById('divTextoInicialQuestao').innerHTML,
                                    document.formQuestao.tamanho.value,
                                    document.formQuestao.decimais.value,
                                    listagem,
                                    objsSerializadosQ,
                                    ultimoValor,
                                    document.formQuestao.sigla.value,
                                    imprime,
                                    editavel,
                                    document.formQuestao.valPerm1.value,
                                    document.formQuestao.valPerm2.value,
                                    document.formQuestao.idImagem.value);
        
        //POPUP_QUESTAO.hide(); 
        $("#POPUP_QUESTAO").dialog("close");
    };

    atualizarQuestaoFunction = function(  textoConteudo,
                                          tipoQuestao, 
                                          unidadeQuestao, 
                                          valoresRefQuestao, 
                                          obrQuestao, 
                                          questaoPonderada,
                                          questaoCalculada,
                                          infoResposta, 
                                          textoInicial,
                                          tamanhoQuestao,
                                          decimaisQuestao,
                                          nomeListagem,
                                          objsSerializadosQ,
                                          ultimoValor,
                                          sigla,
                                          imprime,
                                          editavel,
                                          valorPermitido1,
                                          valorPermitido2,
                                          idImagem) {

    	var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
        
        var idIndex = 0;
        idIndex = document.getElementById('idIndex').value;
        
        var questaoDto = HTMLUtils.getObjectByTableIndex(tblAdicionar, idIndex);
        questaoDto.tituloQuestaoQuestionario = textoConteudo;
        questaoDto.tipoQuestao = tipoQuestao;
        
        questaoDto.unidade = unidadeQuestao;
        questaoDto.valoresReferencia = valoresRefQuestao;
        questaoDto.obrigatoria = obrQuestao;
        questaoDto.ponderada = questaoPonderada;
        questaoDto.calculada = questaoCalculada;
        questaoDto.ultimoValor = ultimoValor;
        questaoDto.infoResposta = infoResposta;
        
        questaoDto.serializeOpcoesResposta = objsSerializadosQ;
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
        if (tipoQuestao == 'T'){
            if (StringUtils.isBlank(tamanhoQuestao)){
                alert('<i18n:message key="questionario.respostaQuestao" />');
                document.formQuestao.tamanho.focus();
                return;
            }   
            questaoDto.tamanho = tamanhoQuestao;    
        }
        if (tipoQuestao == 'N'){
        	questaoDto.tamanho = tamanhoQuestao;
        }
        if (tipoQuestao == 'V' || tipoQuestao == '%'){
            if (StringUtils.isBlank(tamanhoQuestao)){
                alert('<i18n:message key="questionario.respostaQuestao" />');
                document.formQuestao.tamanho.focus();
                return;
            }
            if (StringUtils.isBlank(decimaisQuestao)){
                alert('<i18n:message key="questionario.numeroCasasDecimais" />');
                document.formQuestao.decimais.focus();
                return;
            }
            questaoDto.tamanho = tamanhoQuestao;
            questaoDto.decimais = decimaisQuestao;
        }       

        questaoDto.textoInicial = textoInicial;
        questaoDto.nomeListagem = nomeListagem;

        questaoDto.sigla = sigla;
        questaoDto.imprime = imprime;
        questaoDto.editavel = editavel;

        questaoDto.valorPermitido1 = valorPermitido1;
        questaoDto.valorPermitido2 = valorPermitido2;    

        questaoDto.idImagem = idImagem;    
        
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        
        HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;      
    };  

    atualizarSubFormulario = function(){
        if (document.getElementById('idSubQuestionario').value == ''){
            alert('Informe o Formulário!');
            return;
        }
        if (document.getElementById('abaResultSubForm').value == ''){
            alert('Informe a Aba para associar os resultados dos registros no Prontuário!');
            return;
        }
        
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;

        var idIndex = 0;
        idIndex = document.getElementById('idIndex').value;        
        
        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.tituloQuestaoQuestionario = 'SubFormulário';
        questaoDto.sigla = document.getElementById('siglaSubFormulario').value;
        questaoDto.tipo = 'F';
        questaoDto.tipoQuestao = 'F';
        questaoDto.idSubQuestionario = document.getElementById('idSubQuestionario').value;
        questaoDto.abaResultSubForm = document.getElementById('abaResultSubForm').value;
        
        questaoDto.serializeOpcoesResposta = '';
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
    
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;  
        
        //POPUP_QUESTAO.hide();
        $("#POPUP_QUESTAO").dialog("close");
    }; 

    atualizarInfoHistSessao = function(){
        if (document.getElementById('abaResultSubFormHS').value == ''){
            alert('Informe a(s) guia(s) do Prontuário!');
            return;
        }
        
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;

        var idIndex = 0;
        idIndex = document.getElementById('idIndex').value;        
        
        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.tituloQuestaoQuestionario = 'Histórico de Sessões';
        //questaoDto.sigla = document.getElementById('siglaSubFormulario').value;
        questaoDto.tipo = 'H';
        questaoDto.tipoQuestao = 'F';
        questaoDto.abaResultSubForm = document.getElementById('abaResultSubFormHS').value;
        
        questaoDto.serializeOpcoesResposta = '';
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
    
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;  
        
        //POPUP_QUESTAO.hide();
        $("#POPUP_QUESTAO").dialog("close");
    };     

    atualizarInformaHistoricas = function(){
        if (document.getElementById('abaResultSubFormIH').value == ''){
            alert('Informe a guia do Prontuário!');
            return;
        }
        
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;

        var idIndex = 0;
        idIndex = document.getElementById('idIndex').value;        
        
        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.tituloQuestaoQuestionario = 'Informações Históricas';
        //questaoDto.sigla = document.getElementById('siglaSubFormulario').value;
        questaoDto.tipo = 'I';
        questaoDto.tipoQuestao = 'F';
        questaoDto.abaResultSubForm = document.getElementById('abaResultSubFormIH').value;
        
        questaoDto.serializeOpcoesResposta = '';
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
    
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;  
        
        //POPUP_QUESTAO.hide();
        $("#POPUP_QUESTAO").dialog("close");
    };       
      
    atualizarFormDinamico = function(){
        if (document.getElementById('nomeFormDinamico').value == ''){
            alert('Informe o nome do formulario!');
            return;
        }
        
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;

        var idIndex = 0;
        idIndex = document.getElementById('idIndex').value;        
        
        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.tituloQuestaoQuestionario = 'Formulário Dinâmico';
        //questaoDto.sigla = document.getElementById('siglaSubFormulario').value;
        questaoDto.tipo = 'D';
        questaoDto.tipoQuestao = 'F';
        questaoDto.abaResultSubForm = document.getElementById('nomeFormDinamico').value;
        
        questaoDto.serializeOpcoesResposta = '';
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
    
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;  
        
        //POPUP_QUESTAO.hide();
        $("#POPUP_QUESTAO").dialog("close");
    };
    
    adicionarTextoFixo = function(){
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
        
        if (StringUtils.isBlank(FCKeditorAPI.GetInstance('textoFixo').GetXHTML())){
        	alert('<i18n:message key="questionario.informeOpcoes" />');
            return;
        }       
        
        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.tituloQuestaoQuestionario = FCKeditorAPI.GetInstance('textoFixo').GetXHTML();
        questaoDto.tipo = 'T';
        questaoDto.tipoQuestao = 'F';
        
        questaoDto.serializeOpcoesResposta = '';
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
    
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.addRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario', 'tipoQuestao'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;  
        
        //POPUP_QUESTAO.hide();
        $("#POPUP_QUESTAO").dialog("close");
    };

    adicionarHTML = function(){
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
        
        if (StringUtils.isBlank(document.formQuestao.textoHTML.value)){
            alert('Informe os comandos HTML/Java script!');
            return;
        }       
        
        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.idTabela = tblAdicionar;
        questaoDto.idControleQuestao = document.getElementById('idControleQuestao').value;
        questaoDto.valorDefault = document.formQuestao.textoHTML.value;
        questaoDto.tituloQuestaoQuestionario = 'HTML/JavaScript';
        questaoDto.tipo = 'P';
        questaoDto.tipoQuestao = 'S';
        
        questaoDto.serializeOpcoesResposta = '';
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
    
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.addRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;  
        
        //POPUP_QUESTAO.hide();
        $("#POPUP_QUESTAO").dialog("close");
    };

    atualizarHTML = function(){
        var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
        
        if (StringUtils.isBlank(document.formQuestao.textoHTML.value)){
            alert('Informe os comandos HTML/Java script!');
            return;
        }       

        var idIndex = 0;
        idIndex = document.getElementById('idIndex').value;   
        
        var questaoDto = HTMLUtils.getObjectByTableIndex(tblAdicionar, idIndex);
        questaoDto.valorDefault = document.formQuestao.textoHTML.value;
        questaoDto.tituloQuestaoQuestionario = 'HTML/JavaScript';
        questaoDto.tipo = 'P';
        questaoDto.tipoQuestao = 'S';
        
        questaoDto.serializeOpcoesResposta = '';
        
        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
    
        var corOnAux = HTMLUtil_colorOn;
        var corOffAux = HTMLUtil_colorOff;
        HTMLUtil_colorOn = 'yellow';
        HTMLUtil_colorOff = HTMLUtil_colorOn;
        HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
        HTMLUtil_colorOn = corOnAux;
        HTMLUtil_colorOff = corOffAux;  
        
        //POPUP_QUESTAO.hide();
        $("#POPUP_QUESTAO").dialog("close");
    };
    verificaTipoQuestao = function(){
        document.getElementById('divTamanho').style.display = 'none';
        document.getElementById('divDecimais').style.display = 'none';
        document.getElementById('divFaixaNumeros').style.display = 'none';
        document.getElementById('divFaixaDecimais').style.display = 'none';
        document.getElementById('divPonderada').style.display = 'none';
        document.getElementById('divCalculada').style.display = 'none';
        document.getElementById('divEditavel').style.display = 'none';
        document.getElementById('divPeso').style.display = 'none';
        document.getElementById('divListagem').style.display = 'none';
        document.getElementById('divDesenho').style.display = 'none';
        document.getElementById('divValoresReferenciaUN').style.display = 'block';
        
        document.getElementById('divCheckboxRadioCombo').style.display = 'none';
        if (document.formQuestao.tipoQuestao.value == 'T'){
            document.getElementById('divTamanho').style.display = 'block';
        }
        if (document.formQuestao.tipoQuestao.value == 'N'){
            document.getElementById('divTamanho').style.display = 'block';
        }
        if (document.formQuestao.tipoQuestao.value == 'V' || document.formQuestao.tipoQuestao.value == '%'){
            document.getElementById('divTamanho').style.display = 'block';
            document.getElementById('divDecimais').style.display = 'block';
        }
        if (document.formQuestao.tipoQuestao.value == 'R' || document.formQuestao.tipoQuestao.value == 'C' || document.formQuestao.tipoQuestao.value == 'X'){
            document.getElementById('divCheckboxRadioCombo').style.display = 'block';
            document.getElementById('divPonderada').style.display = 'block';
            if (document.formQuestao.ponderada[0].checked){
                document.getElementById('divPeso').style.display = 'block';
            }    
        }   
        if (document.formQuestao.tipoQuestao.value == 'T' || document.formQuestao.tipoQuestao.value == 'A' || 
            document.formQuestao.tipoQuestao.value == 'N' || document.formQuestao.tipoQuestao.value == 'V' || 
            document.formQuestao.tipoQuestao.value == 'D' || document.formQuestao.tipoQuestao.value == 'H' ||
            document.formQuestao.tipoQuestao.value == 'G' || document.formQuestao.tipoQuestao.value == 'L'){ 
            document.getElementById('divCalculada').style.display = 'block';
        }   
        if (document.formQuestao.calculada[0].checked) {
            document.getElementById('divValoresReferenciaUN').style.display = 'none';
            document.getElementById('divEditavel').style.display = 'block';
        }            
        if (document.formQuestao.tipoQuestao.value == '1'){
            document.getElementById('divFaixaNumeros').style.display = 'block';
        }
        if (document.formQuestao.tipoQuestao.value == '2' || document.formQuestao.tipoQuestao.value == 'N' ||
        	document.formQuestao.tipoQuestao.value == 'V' || document.formQuestao.tipoQuestao.value == '%'){
            document.getElementById('divFaixaDecimais').style.display = 'block';
        }
        if (document.formQuestao.tipoQuestao.value == '8'){
            document.getElementById('divListagem').style.display = 'block';
        }
        if (document.formQuestao.tipoQuestao.value == '9'){
            document.getElementById('divDesenho').style.display = 'block';
        }
    };

    verificaExibicaoComplementoOpcao = function(){
        document.getElementById('btnComplementoOpcao').style.display = 'none';
        
        if (document.formQuestao.exibeComplemento.checked) {
            document.getElementById('btnComplementoOpcao').style.display = 'block';
        }
    };

    var opcaoRespostaAtualDto = null;
    novaOpcaoQuestao = function(){
    	opcaoRespostaAtualDto = new CIT_OpcaoRespostaQuestionarioDTO();
    	opcaoRespostaAtualDto.questaoComplementoDto = null;
	    document.formQuestao.txtOpcao.value = '';
	    document.formQuestao.txtValor.value = '';
	    document.formQuestao.peso.value = ''; 
	    document.formQuestao.geraAlerta.checked = false;
	    document.formQuestao.exibeComplemento.checked = false; 
	    document.getElementById('divOpcaoQuestao').style.display = 'block';    
	    document.getElementById('btnAddOpcao').style.display = 'block';
	    document.getElementById('btnAtuOpcao').style.display = 'none';
	    document.getElementById('btnNovaOpcao').style.display = 'none';
        document.getElementById('btnComplementoOpcao').style.display = 'none';
        document.formQuestao.txtOpcao.focus();	    
    }

    verificaOpcaoQuestao = function() {
        if (opcaoRespostaAtualDto == null) {
            return false;
        }
        if (StringUtils.isBlank(document.formQuestao.txtOpcao.value)){
            alert('<i18n:message key="questionario.textoOpcao" />');
            document.formQuestao.txtOpcao.focus();
            return false;
        }   
        if (document.formQuestao.ponderada[0].checked){
            if (StringUtils.isBlank(document.formQuestao.peso.value)){
                alert('<i18n:message key="questionario.informePesoOpcoes" />');
                document.formQuestao.peso.focus();
                return false;
            }  
        }
        if (document.formQuestao.exibeComplemento.checked) {
            if (opcaoRespostaAtualDto.questaoComplementoDto == null) {
                alert('<i18n:message key="questionario.configureComplementoQuestao" />');
                document.formQuestao.btnComplementoOpcao.focus();
                return false;
            }

            opcaoRespostaAtualDto.exibeComplemento = 'S';
            var serializadosQ = ObjectUtils.serializeObject(opcaoRespostaAtualDto.questaoComplementoDto);
            opcaoRespostaAtualDto.serializeQuestaoComplemento = serializadosQ; 
        }else{
        	opcaoRespostaAtualDto.exibeComplemento = 'N';
        	opcaoRespostaAtualDto.serializeQuestaoComplemento = '';
        }        
        opcaoRespostaAtualDto.valor = document.formQuestao.txtValor.value;
        opcaoRespostaAtualDto.titulo = document.formQuestao.txtOpcao.value;
        opcaoRespostaAtualDto.peso = document.formQuestao.peso.value;
        if (document.formQuestao.geraAlerta.checked) {
        	opcaoRespostaAtualDto.geraAlerta = 'S';
        }else{
        	opcaoRespostaAtualDto.geraAlerta = 'N';
        }        
        return true;            
    }
        
    var idControleValorCombos = 0;
    adicionarOpcaoQuestao = function(){
        if (verificaOpcaoQuestao(opcaoRespostaAtualDto)) {
	        HTMLUtils.addRow('tblOpcoes', null, '', opcaoRespostaAtualDto, ['', 'titulo', 'valor', 'peso'], null, '', null, mostraOpcao, null, true);
	        HTMLUtils.addOption('lstOpcoesDefault', document.formQuestao.txtOpcao.value, '' + idControleValorCombos);
	        idControleValorCombos = idControleValorCombos + 1;
	        configuraBotoesOpcoes();    
	        novaOpcaoQuestao();
        }    	            
    };

    removeOpcao = function(){
        var tbl = document.getElementById('tblOpcoes');
        var iRowFimAux = tbl.rows.length;
        var b = false;
        for(var i = 1; i < iRowFimAux; i++){
            try{
                if (tbl.rows[i].cells[0].innerHTML != '' && tbl.rows[i].cells[0].innerHTML != "&nbsp;"){
                    HTMLUtils.deleteRow('tblOpcoes', i);
                    b = true;
                }
            }catch(ex){
            }
        }   
        if (!b){
            alert('<i18n:message key="questionario.selecioneUmItem" />');
        }
        if (document.getElementById('tblOpcoes').rows.length > 2) {
            document.getElementById('btnMoveOpcaoAcima').style.display = 'block';
        }else{
            document.getElementById('btnMoveOpcaoAcima').style.display = 'none';
        }       
        document.getElementById('btnMoveOpcaoAbaixo').style.display = 'none';
        document.getElementById('btnRemOpcao').style.display = 'block';
    };

    atualizarOpcaoQuestao = function(){
        if (verificaOpcaoQuestao(opcaoRespostaAtualDto)) {
            HTMLUtils.updateRow('tblOpcoes', null, '', opcaoRespostaAtualDto, ['', 'titulo', 'valor', 'peso'], null, '', null, mostraOpcao, null, linhaOpcaoAtual, true);
        	configuraBotoesOpcoes();
        	novaOpcaoQuestao();
        }    
    };
 
    var linhaOpcaoAtual = -1;
    mostraOpcao = function(row, obj){
    	if (row.cells[0].innerHTML != '' && row.cells[0].innerHTML != '&nbsp;'){
            HTMLUtils.setValueColumnTable('tblOpcoes', '&nbsp;', 1, null, 0);
            novaOpcaoQuestao();
        }else{
        	linhaOpcaoAtual = row.rowIndex;
        	opcaoRespostaAtualDto = obj;
            document.formQuestao.txtOpcao.value = obj.titulo;
            document.formQuestao.txtValor.value = obj.valor;
            document.formQuestao.peso.value = obj.peso;  
            document.formQuestao.geraAlerta.checked = obj.geraAlerta == 'S';
            document.formQuestao.exibeComplemento.checked = obj.exibeComplemento == 'S';
            verificaExibicaoComplementoOpcao(); 

            HTMLUtils.setValueColumnTable('tblOpcoes', '&nbsp;', 1, null, 0);
            row.cells[0].innerHTML = '<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/editar.gif" border="0"/>';

            document.getElementById('btnAddOpcao').style.display = 'none';
            document.getElementById('btnAtuOpcao').style.display = 'block';
            document.getElementById('btnRemOpcao').style.display = 'block';
            document.getElementById('btnNovaOpcao').style.display = 'none';
            if (obj.exibeComplemento == 'S') {
            	document.getElementById('btnComplementoOpcao').style.display = 'block';
            }    
            document.getElementById('divOpcaoQuestao').style.display = 'block';            
            configuraBotoesOpcoes();
        }    

    };

    configuraBotoesOpcoes = function() {
        document.getElementById('btnMoveOpcaoAcima').style.display = 'none';        
        document.getElementById('btnMoveOpcaoAbaixo').style.display = 'none';
        if (linhaOpcaoAtual > 1) {
            document.getElementById('btnMoveOpcaoAcima').style.display = 'block';
        }     
        if (linhaOpcaoAtual < (document.getElementById('tblOpcoes').rows.length -1)) {
            document.getElementById('btnMoveOpcaoAbaixo').style.display = 'block';
        }     
    };
        
    removeTodasOpcoes = function(){
    	HTMLUtils.deleteAllRows('tblOpcoes');
        document.getElementById('btnMoveOpcaoAcima').style.display = 'none';
        document.getElementById('btnMoveOpcaoAbaixo').style.display = 'none';
        document.getElementById('btnRemOpcao').style.display = 'none';
    };  

    moveOpcaoAcima = function(objImg){
        if (linhaOpcaoAtual == -1) return;
        var indiceNew = linhaOpcaoAtual - 1;
        if (indiceNew <= 0) return;
        
        var indice = linhaOpcaoAtual;
        linhaOpcaoAtual = indiceNew;
        
        moveRow('tblOpcoes', indice, indiceNew);
        configuraBotoesOpcoes();
    };

    moveOpcaoAbaixo = function(objImg){
    	if (linhaOpcaoAtual == -1) return;
        var indiceNew = linhaOpcaoAtual + 1;
        if (indiceNew == (document.getElementById('tblOpcoes').length - 1)) return;
        
        var indice = linhaOpcaoAtual;
        linhaOpcaoAtual = indiceNew;
                        
        moveRow('tblOpcoes', indice, indiceNew);
        configuraBotoesOpcoes();
    };  

    var editandoComplementoOpcao = 'N';
    configurarComplementoOpcao = function() {
        editandoComplementoOpcao = 'S';        
        if (opcaoRespostaAtualDto != null &&  opcaoRespostaAtualDto.questaoComplementoDto != null) {
            mostraQuestaoAgrupada();
        }else{
	        HTMLUtils.clearForm(document.formQuestaoAgrupada);
	        document.formQuestaoAgrupada.tipo[0].checked = true;
	        preparaInclusaoQuestaoAgrupada();
        }    
    };
        
    addTextoInicioQuestao = function(){
        var textoInicialQuestaoQuestionario = FCKeditorAPI.GetInstance('textoInicialQuestaoQuestionario').GetXHTML();
        document.getElementById('divTextoInicialQuestao').innerHTML = textoInicialQuestaoQuestionario;
        
        //POPUP_TEXTO.hide();
        $("#POPUP_TEXTO").dialog("close");
    };
    
    gravarQuestionario = function(){
        if (StringUtils.isBlank(document.formAuxiliar.nomeQuestionario.value)){
            alert('<i18n:message key="questionario.informeNomeQuestionario" />');
            document.formAuxiliar.nomeQuestionario.focus();
            return;
        }
        if (StringUtils.isBlank(document.form.idCategoriaQuestionario.value)){
            alert('<i18n:message key="questionario.informeCategoriaQuestionario" />');
            document.formAuxiliar.idCategoriaQuestionarioAux.focus();
            return;
        }
            
        var objs = HTMLUtils.getObjectsByTableId('tblConfigQuestionario');
        if (objs == null || objs == undefined){
            alert('<i18n:message key="questionario.informeGruposQuestoes" />');
            return false;
        }
        if (objs.length == 0){
            alert('<i18n:message key="questionario.informeGruposQuestoes" />');
            return false;       
        }
        
        JANELA_AGUARDE_MENU.setTitle('Aguarde... gravando...');
        JANELA_AGUARDE_MENU.show();
        
        document.form.nomeQuestionario.value = document.formAuxiliar.nomeQuestionario.value;
        document.form.javaScript.value = document.formAuxiliar.javaScript.value;
        
        for(var i = 0; i < objs.length; i++){
            var tblGrupo = 'tblItem_' + objs[i].idControleQuestao;
            
            var objsQuestoes = HTMLUtils.getObjectsByTableId(tblGrupo);
            var objsQuestSerializados = ObjectUtils.serializeObjects(objsQuestoes);

            objs[i].serializeQuestoesGrupo = objsQuestSerializados;
        }
        var objsSerializados = ObjectUtils.serializeObjects(objs); 
        
        document.form.colQuestionariosSerialize.value = objsSerializados;
        document.form.reload.value = 'true';
        document.form.fireEvent('gravar');
    };
    
    gravarGruposQuestionario = function(){
        if (StringUtils.isBlank(document.formAuxiliar.nomeQuestionario.value)){
            alert('<i18n:message key="questionario.informeNomeQuestionario" />');
            document.formAuxiliar.nomeQuestionario.focus();
            return;
        }
            
        var objs = HTMLUtils.getObjectsByTableId('tblConfigQuestionario');
        if (objs == null || objs == undefined){
            alert('<i18n:message key="questionario.informeGruposQuestoes" />');
            return false;
        }
        if (objs.length == 0){
            alert('<i18n:message key="questionario.informeGruposQuestoes" />');
            return false;       
        }
        
        JANELA_AGUARDE_MENU.setTitle('Aguarde... gravando...');
        JANELA_AGUARDE_MENU.show();
        
        document.form.nomeQuestionario.value = document.formAuxiliar.nomeQuestionario.value;
        
        var objsSerializados = ObjectUtils.serializeObjects(objs); 
        
        document.form.colQuestionariosSerialize.value = objsSerializados;
        document.form.reload.value = 'true';
        document.form.fireEvent('gravarOrdemGrupos');
    };
    
    listagemQuestionario = function(){
        HTMLUtils.removeAllOptions('lstQuestionarios');
        HTMLUtils.addOption('lstQuestionarios', '<i18n:message key="citcorpore.comum.aguardecarregando"/>', '0');
        
        document.form.fireEvent('listar');
        //POPUP_LISTAGEM_QUESTIONARIOS.show();
        $("#POPUP_LISTAGEM_QUESTIONARIOS").dialog("open");
    };
    
    abrirQuestionario = function(){
        if (document.getElementById('lstQuestionarios').selectedIndex == null || document.getElementById('lstQuestionarios').selectedIndex == undefined ||
            document.getElementById('lstQuestionarios').selectedIndex < 0){
            alert('Informe o questionário que deseja abrir!');
            return;
        }
        JANELA_AGUARDE_MENU.setTitle('Aguarde... abrindo o questionário...');
        JANELA_AGUARDE_MENU.show();
                
        document.form.restore();
    };
    
    verificaTipo = function(){
        document.getElementById('divQuestoesTabela').innerHTML = '';
        document.getElementById('divQuestoesMatriz').innerHTML = '';
        document.getElementById('divHTML').style.display = 'none';
        if (document.formQuestao.tipo[0].checked){              
            document.getElementById('divQuestao').style.display = 'block';
            document.getElementById('divTextoFixo').style.display = 'none';
            document.getElementById('divMatriz').style.display = 'none';
            document.getElementById('divTabela').style.display = 'none';
        }else if (document.formQuestao.tipo[1].checked){
            document.getElementById('divQuestao').style.display = 'none';
            document.getElementById('divTextoFixo').style.display = 'block'; 
            document.getElementById('divMatriz').style.display = 'none';    
            document.getElementById('divTabela').style.display = 'none'; 
        }else if (document.formQuestao.tipo[2].checked){
            document.getElementById('divQuestao').style.display = 'none';
            document.getElementById('divTextoFixo').style.display = 'none'; 
            document.getElementById('divMatriz').style.display = 'block';
            document.getElementById('divTabela').style.display = 'none';                 
            document.getElementById('divQuestoesMatriz').style.display = 'none';
        }else if (document.formQuestao.tipo[3].checked){
            document.getElementById('divQuestao').style.display = 'none';
            document.getElementById('divTextoFixo').style.display = 'none'; 
            document.getElementById('divMatriz').style.display = 'none';
            document.getElementById('divTabela').style.display = 'block';                 
            document.getElementById('divQuestoesTabela').style.display = 'none';
        }else if (document.formQuestao.tipo[4].checked){
            document.getElementById('divQuestao').style.display = 'none';
            document.getElementById('divTextoFixo').style.display = 'none'; 
            document.getElementById('divMatriz').style.display = 'none';
            document.getElementById('divTabela').style.display = 'none';                 
            document.getElementById('divQuestoesTabela').style.display = 'none';
            document.getElementById('divHTML').style.display = 'block';
        }            
    };

    visualizarQuestionario = function(){
        window.open('<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/visualizacaoQuestionario/visualizacaoQuestionario.load?modo=edicao&idQuestionario=' + document.getElementById('idQuestionario').value);
    };

    exportarQuestionario = function(){
        if (!confirm('<i18n:message key="questionario.exportaraQuestionario" />')){
            return;
        }    

        if (StringUtils.isBlank(document.formAuxiliar.nomeQuestionario.value)){
            alert('<i18n:message key="questionario.informeNomeQuestionario" />');
            document.formAuxiliar.nomeQuestionario.focus();
            return;
        }
        if (StringUtils.isBlank(document.form.idCategoriaQuestionario.value)){
            alert('<i18n:message key="questionario.informeCategoriaQuestionario" />');
            document.formAuxiliar.idCategoriaQuestionarioAux.focus();
            return;
        }
            
        var objs = HTMLUtils.getObjectsByTableId('tblConfigQuestionario');
        if (objs == null || objs == undefined){
            alert('<i18n:message key="questionario.informeGruposQuestoes" />');
            return false;
        }
        if (objs.length == 0){
            alert('<i18n:message key="questionario.informeGruposQuestoes" />');
            return false;       
        }
        
        JANELA_AGUARDE_MENU.setTitle('Aguarde... exportando...');
        JANELA_AGUARDE_MENU.show();
        
        document.form.nomeQuestionario.value = document.formAuxiliar.nomeQuestionario.value;
        document.form.javaScript.value = document.formAuxiliar.javaScript.value;
        
        for(var i = 0; i < objs.length; i++){
            var tblGrupo = 'tblItem_' + objs[i].idControleQuestao;
            
            var objsQuestoes = HTMLUtils.getObjectsByTableId(tblGrupo);
            var objsQuestSerializados = ObjectUtils.serializeObjects(objsQuestoes);
            
            objs[i].serializeQuestoesGrupo = objsQuestSerializados;
        }
        var objsSerializados = ObjectUtils.serializeObjects(objs); 
        
        document.form.colQuestionariosSerialize.value = objsSerializados;
                
        document.form.fireEvent("exportarQuestionario");
    };

    importarQuestionario = function(){
        if (!confirm('<i18n:message key="questionario.importaraQuestionario" />')){
            return;
        } 

        JANELA_AGUARDE_MENU.setTitle('Aguarde... importando...');
        JANELA_AGUARDE_MENU.show();

	    document.formImportar.setAttribute("enctype","multipart/form-data"); 
	    document.formImportar.setAttribute("encoding","multipart/form-data");        
        document.formImportar.submit();
    }    
    
    novoQuestionario = function(){
        document.formAuxiliar.nomeQuestionario.value = '';
        document.form.nomeQuestionario.value = '';
        document.formAuxiliar.javaScript.value = '';
        document.form.javaScript.value = '';
        
        document.form.idCategoriaQuestionario.value = '';
        
        document.form.idQuestionario.value = '';
        document.form.idQuestionarioOrigem.value = '';
        document.form.ativo.value = 'S';
        document.form.acao.value = '';
        document.getElementById('btnCapGrupoQuest').style.display = 'none';
        document.getElementById('btnOrdenarGrupos').style.display = 'none';
        document.getElementById('btnVisualiza').style.display = 'none';
        document.getElementById('btnExcluirGrupo').style.display = 'none'
        
        HTMLUtils.deleteAllRows('tblConfigQuestionario');
        HTMLUtils.setValue('idCategoriaQuestionarioAux', '');

        document.getElementById('spanTitulo').innerHTML = '';
    };

    <!-- Métodos para Matriz e Tabela -->   
    existeQuestaoAgrupada = function(linha, coluna){
        var result = false;
    	if (objsQuestoesAgrupadas[linha-1] != undefined && objsQuestoesAgrupadas[linha-1] != null){
    	    var objsColunas = objsQuestoesAgrupadas[linha-1];
    		result = (objsColunas[coluna-1] != undefined && objsColunas[coluna-1] != null);
    	}     
    	return result;
    }   

    retornaObjQuestaoAgrupada = function(linha, coluna){
        var result = null;
        if (objsQuestoesAgrupadas[linha-1] != undefined && objsQuestoesAgrupadas[linha-1] != null){
            var objsColunas = objsQuestoesAgrupadas[linha-1];
            result = objsColunas[coluna-1];
        }     
        return result;
    }   

    atualizaObjQuestaoAgrupada = function(obj, linha, coluna){
        if (objsQuestoesAgrupadas[linha-1] == undefined || objsQuestoesAgrupadas[linha-1] == null){
            objsQuestoesAgrupadas[linha-1] = new Array();
        }	
        var objsColunas = objsQuestoesAgrupadas[linha-1];
        objsColunas[coluna-1] = obj;
    }   
    
    configurarQuestaoAgrupada = function(linha, coluna){
        editandoComplementoOpcao = 'N';        
    	colAtualQuestaoAgrupada = coluna;
    	linAtualQuestaoAgrupada = linha;
        if (existeQuestaoAgrupada(linha, coluna)){
            mostraQuestaoAgrupada();
        }else{
            preparaInclusaoQuestaoAgrupada();
        }    
    }    
        
    preparaInclusaoQuestaoAgrupada = function(){
        HTMLUtils.clearForm(document.formQuestaoAgrupada);
        document.formQuestaoAgrupada.tipo[0].disabled = false;
        document.formQuestaoAgrupada.tipo[1].disabled = editandoComplementoOpcao == 'S';  
        
        /**
       		 Opção comentada, pois a opções de matriz e tabela foram retiradas do questionário.
        **/
     	//document.formQuestaoAgrupada.tipo[2].disabled = (document.formQuestao.tipo[3].checked || editandoComplementoOpcao == 'S');  // Se tabela, desabilita texto fixo
        document.getElementById('divQuestaoAgrupada').style.display = 'none';
        document.getElementById('divTextoFixoAgrupada').style.display = 'none';   
        document.getElementById('divTamanhoAgrupada').style.display = 'none';
        document.getElementById('divDecimaisAgrupada').style.display = 'none';
        document.getElementById('divFaixaNumerosAgrupada').style.display = 'none';
        document.getElementById('divFaixaDecimaisAgrupada').style.display = 'none';
        document.getElementById('divCheckboxRadioComboAgrupada').style.display = 'none';
        document.getElementById('divPonderadaAgrupada').style.display = 'none';
        document.getElementById('divListagemAgrupada').style.display = 'none';        
        document.getElementById('divPesoAgrupada').style.display = 'none'; 
                         

        HTMLUtils.removeAllOptions('lstOpcoesAgrupada');
        HTMLUtils.removeAllOptions('lstOpcoesDefaultAgrupada');
        FCKeditorAPI.GetInstance('tituloQuestaoQuestionarioAgrupada').SetData('');
        document.getElementById('btnAddQuestaoAgrupada').style.display = 'block';
        document.getElementById('btnAtuQuestaoAgrupada').style.display = 'none';
        
        //POPUP_QUESTAO_AGRUPADA.showInYPosition({top:40});
        $("#POPUP_QUESTAO_AGRUPADA").dialog("open");
    };

    mostraQuestaoAgrupada = function(){
        var obj = null;

        if (editandoComplementoOpcao == 'N') {
            obj = retornaObjQuestaoAgrupada(linAtualQuestaoAgrupada, colAtualQuestaoAgrupada);
        }else if (opcaoRespostaAtualDto != null &&  opcaoRespostaAtualDto.questaoComplementoDto != null) {
            obj = opcaoRespostaAtualDto.questaoComplementoDto;
        }else{
            return;
        }             
        
        HTMLUtils.clearForm(document.formQuestaoAgrupada);
        HTMLUtils.setValuesForm(document.formQuestaoAgrupada, obj);
        
        if (obj.tipo == 'Q'){
            document.formQuestaoAgrupada.tipo[0].checked = true;
        }else if (obj.tipo == 'C'){
            document.formQuestaoAgrupada.tipo[1].checked = true;
        }else{
            document.formQuestaoAgrupada.tipo[2].checked = true;
        }     
        document.formQuestaoAgrupada.tipo[1].disabled = editandoComplementoOpcao == 'S';    
        document.formQuestaoAgrupada.tipo[2].disabled = document.formQuestao.tipo[3].checked || editandoComplementoOpcao == 'S';  // Se tabela, desabilita texto fixo

        document.formQuestaoAgrupada.nomeListagemAgrupada.value = obj.nomeListagem;
        
        verificaTipoAgrupada();
        verificaTipoQuestaoAgrupada();

        if (obj.tipo == 'Q'){        
			document.formQuestaoAgrupada.tituloQuestaoQuestionarioAgrupada.value = obj.tituloQuestaoQuestionario;
    	    FCKeditorAPI.GetInstance('tituloQuestaoQuestionarioAgrupada').SetData(obj.tituloQuestaoQuestionario);

        	var objs = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(obj.serializeOpcoesResposta);
        
        	HTMLUtils.removeAllOptions('lstOpcoesAgrupada');
        	HTMLUtils.removeAllOptions('lstOpcoesDefaultAgrupada');
        	if (objs != undefined && objs != null){
            	for(var i = 0; i < objs.length; i++){
                	var opcao = objs[i];
                	var s = opcao.titulo;
                	if (obj.ponderada == 'S') {
                    	s = opcao.titulo + ' [Peso ' + opcao.peso + ']';
                	}   
                	HTMLUtils.addOption('lstOpcoesAgrupada', s, '' + i);
                	HTMLUtils.addOption('lstOpcoesDefaultAgrupada', opcao.titulo, '' + i);
            	}
        	}
        
       		document.getElementById('btnAddQuestaoAgrupada').style.display = 'none';
        	document.getElementById('btnAtuQuestaoAgrupada').style.display = 'block';
        }else if (obj.tipo == 'T'){
            document.formQuestaoAgrupada.textoFixoAgrupada.value = obj.tituloQuestaoQuestionario;
            FCKeditorAPI.GetInstance('textoFixoAgrupada').SetData(obj.tituloQuestaoQuestionario);
        }   

        if (obj.tipoQuestao == '2' || obj.tipoQuestao == 'N' || obj.tipoQuestao == 'V' || obj.tipoQuestao == '%'){
            if (obj.valorPermitido1 != null && obj.valorPermitido1 != ''){
            	document.formQuestaoAgrupada.valPerm1.value = NumberUtil.format(NumberUtil.toDouble(obj.valorPermitido1), 3, ",", ".");
            }else{
            	document.formQuestaoAgrupada.valPerm1.value = '';
            }
            if (obj.valorPermitido2 != null && obj.valorPermitido2 != ''){
            	document.formQuestaoAgrupada.valPerm2.value = NumberUtil.format(NumberUtil.toDouble(obj.valorPermitido2), 3, ",", ".")
            }else{
            	document.formQuestaoAgrupada.valPerm2.value = '';
            }
        }
                                    
        //POPUP_QUESTAO_AGRUPADA.showInYPosition({top:40});
        $("#POPUP_QUESTAO_AGRUPADA").dialog("open");
        
    };
    
    verificaTipoQuestaoAgrupada = function(){
        document.getElementById('divTamanhoAgrupada').style.display = 'none';
        document.getElementById('divDecimaisAgrupada').style.display = 'none';
        document.getElementById('divFaixaNumerosAgrupada').style.display = 'none';
        document.getElementById('divFaixaDecimaisAgrupada').style.display = 'none';
        document.getElementById('divPonderadaAgrupada').style.display = 'none';
        document.getElementById('divListagemAgrupada').style.display = 'none';        
        document.getElementById('divPesoAgrupada').style.display = 'none';
        
        document.getElementById('divCheckboxRadioComboAgrupada').style.display = 'none';
        if (document.formQuestaoAgrupada.tipoQuestao.value == 'T'){
            document.getElementById('divTamanhoAgrupada').style.display = 'block';
        }
        if (document.formQuestaoAgrupada.tipoQuestao.value == 'N'){
            document.getElementById('divTamanhoAgrupada').style.display = 'block';
        }
        if (document.formQuestaoAgrupada.tipoQuestao.value == 'V' || document.formQuestaoAgrupada.tipoQuestao.value == '%'){
            document.getElementById('divTamanhoAgrupada').style.display = 'block';
            document.getElementById('divDecimaisAgrupada').style.display = 'block';
        }
        if (document.formQuestaoAgrupada.tipoQuestao.value == 'R' || document.formQuestaoAgrupada.tipoQuestao.value == 'C' || document.formQuestaoAgrupada.tipoQuestao.value == 'X'){
            document.getElementById('divCheckboxRadioComboAgrupada').style.display = 'block';
            document.getElementById('divPonderadaAgrupada').style.display = 'block';
            if (document.formQuestaoAgrupada.ponderada[0].checked){
                document.getElementById('divPesoAgrupada').style.display = 'block';
            }    
        }   
        if (document.formQuestaoAgrupada.tipoQuestao.value == '1'){
            document.getElementById('divFaixaNumerosAgrupada').style.display = 'block';
        }
        if (document.formQuestaoAgrupada.tipoQuestao.value == '2'){
            document.getElementById('divFaixaDecimaisAgrupada').style.display = 'block';
        }
        if (document.formQuestaoAgrupada.tipoQuestao.value == '8'){
            document.getElementById('divListagemAgrupada').style.display = 'block';
        }
        if (document.formQuestaoAgrupada.tipoQuestao.value == '2' || document.formQuestaoAgrupada.tipoQuestao.value == 'N' ||
            	document.formQuestaoAgrupada.tipoQuestao.value == 'V' || document.formQuestaoAgrupada.tipoQuestao.value == '%'){
            document.getElementById('divFaixaDecimaisAgrupada').style.display = 'block';
        }        
    };
    
    var idControleValorCombosAgrupada = 0;
    adicionarOpcaoQuestaoAgrupada = function(){
        var s = '';
        if (StringUtils.isBlank(document.formQuestaoAgrupada.txtOpcao.value)){
            alert('<i18n:message key="questionario.textoOpcao" />');
            document.formQuestaoAgrupada.txtOpcao.focus();
            return;
        }   
        s = document.formQuestaoAgrupada.txtOpcao.value;
        if (document.formQuestaoAgrupada.ponderada[0].checked){
            if (StringUtils.isBlank(document.formQuestaoAgrupada.peso.value)){
                alert('<i18n:message key="questionario.informePesoOpcoes" />');
                document.formQuestaoAgrupada.peso.focus();
                return;
            }  
            /* - Nao sera verificado a duplicidade de pesos, pois isto pode ocorrer - EMAURI - 15/07/2009.
            for(var i = 0; i < document.formQuestaoAgrupada.lstOpcoesAgrupada.length; i++){
                opcao = document.formQuestaoAgrupada.lstOpcoesAgrupada.options[i].text;
                peso = opcao.substring(opcao.indexOf('[')+6,opcao.indexOf(']'));
                if (parseInt(peso) == parseInt(document.formQuestaoAgrupada.peso.value)) {
                	alert('Este peso já foi informado!');
                	document.formQuestaoAgrupada.peso.focus();
                	return;
                }
            }    	
            */

            s = s + ' [Peso '+document.formQuestaoAgrupada.peso.value+']';
        }
        HTMLUtils.addOption('lstOpcoesAgrupada', s, '' + idControleValorCombosAgrupada);
        HTMLUtils.addOption('lstOpcoesDefaultAgrupada', document.formQuestaoAgrupada.txtOpcao.value, '' + idControleValorCombosAgrupada);
        idControleValorCombosAgrupada = idControleValorCombosAgrupada + 1;
    };
    
    removeOpcaoAgrupada = function(){
        if (document.getElementById('lstOpcoesAgrupada').selectedIndex != undefined && document.getElementById('lstOpcoesAgrupada').selectedIndex != null &&
            document.getElementById('lstOpcoesAgrupada').selectedIndex != -1){
            HTMLUtils.removeOption('lstOpcoesAgrupada', document.getElementById('lstOpcoesAgrupada').selectedIndex);
        }
    };
    
    removeTodasOpcoesAgrupada = function(){
        if (document.getElementById('lstOpcoesAgrupada').length != undefined && document.getElementById('lstOpcoesAgrupada').length != null &&
            document.getElementById('lstOpcoesAgrupada').length > 0){ 
            for(var i = document.getElementById('lstOpcoesAgrupada').length; i >= 0; i--){
                HTMLUtils.removeOption('lstOpcoesAgrupada', i);
            }
        }
    };  
    
    verificaTipoAgrupada = function(){
        if (document.formQuestaoAgrupada.tipo[0].checked){
            document.getElementById('divQuestaoAgrupada').style.display = 'block';
            document.getElementById('divTextoFixoAgrupada').style.display = 'none';
        }else if (document.formQuestaoAgrupada.tipo[1].checked){
            document.getElementById('divQuestaoAgrupada').style.display = 'none';
            document.getElementById('divTextoFixoAgrupada').style.display = 'none';         
        }else{
            document.getElementById('divQuestaoAgrupada').style.display = 'none';
            document.getElementById('divTextoFixoAgrupada').style.display = 'block';        
        }    
    };

    atualizarQuestaoAgrupada = function(){
        var questaoDto = new CIT_QuestaoQuestionarioDTO();

        if ((document.formQuestao.tipo[0].checked || document.formQuestao.tipo[3].checked) && StringUtils.isBlank(FCKeditorAPI.GetInstance('tituloQuestaoQuestionarioAgrupada').GetXHTML())){
            alert('<i18n:message key="questionario.informeTituloQuestao" />');
            return;
        }
        if (StringUtils.isBlank(document.formQuestaoAgrupada.tipoQuestao.value)){
            alert('<i18n:message key="questionario.informeTipoQuestao" />');
            document.formQuestaoAgrupada.tipoQuestao.focus();
            return;
        }       
        questaoDto.tipoQuestao = document.formQuestaoAgrupada.tipoQuestao.value;

        questaoDto.tamanho = '0';
        questaoDto.decimais = '0';
        if (questaoDto.tipoQuestao == 'T'){
            if (StringUtils.isBlank(document.formQuestaoAgrupada.tamanho.value)){
                alert('<i18n:message key="questionario.respostaQuestao" />');
                document.formQuestaoAgrupada.tamanho.focus();
                return;
            }   
            questaoDto.tamanho = document.formQuestaoAgrupada.tamanho.value;    
        }
        if (questaoDto.tipoQuestao == 'V' || questaoDto.tipoQuestao == '%'){
            if (StringUtils.isBlank(document.formQuestaoAgrupada.tamanho.value)){
                alert('<i18n:message key="questionario.respostaQuestao" />');
                document.formQuestaoAgrupada.tamanho.focus();
                return;
            }
            if (StringUtils.isBlank(document.formQuestaoAgrupada.decimais.value)){
                alert('<i18n:message key="questionario.numeroCasasDecimais" />');
                document.formQuestaoAgrupada.decimais.focus();
                return;
            }
            questaoDto.tamanho = document.formQuestaoAgrupada.tamanho.value;
            questaoDto.decimais = document.formQuestaoAgrupada.decimais.value;
        }   

        if (document.formQuestao.ultimoValor[2].checked && StringUtils.isBlank(document.formQuestaoAgrupada.sigla.value)){
            alert('<i18n:message key="questionario.informeSiglaQuestao" />');
            document.formQuestaoAgrupada.decimais.focus();
            return;
        }
        
        var obrQuestao = 'N';
        if (document.formQuestaoAgrupada.obrigatoria[0].checked){
            obrQuestao = 'S';
        }
        
        var infoResposta = 'L';
        if (document.formQuestaoAgrupada.infoResposta[1].checked){
            infoResposta = 'B';
        }   
        var questaoPonderada = 'N';
        if (document.formQuestaoAgrupada.ponderada[0].checked){
            questaoPonderada = 'S';
        }       

        var listagem = '';
        if (document.formQuestaoAgrupada.tipoQuestao.value == '8'){
            listagem = document.formQuestaoAgrupada.nomeListagemAgrupada.value;
        }
        
        questaoDto.tituloQuestaoQuestionario = FCKeditorAPI.GetInstance('tituloQuestaoQuestionarioAgrupada').GetXHTML();
        questaoDto.tipo = 'Q';
        questaoDto.idQuestaoAgrupadora = document.getElementById('idControleQuestao').value;
        questaoDto.tipoQuestao = document.formQuestaoAgrupada.tipoQuestao.value;
        questaoDto.idControleQuestao = document.getElementById('idControleQuestao').value;
        questaoDto.unidade = document.formQuestaoAgrupada.unidade.value;
        questaoDto.valoresReferencia = document.formQuestaoAgrupada.valoresReferencia.value;
        questaoDto.obrigatoria = obrQuestao;
        questaoDto.infoResposta = infoResposta;
        questaoDto.ponderada = questaoPonderada;
        questaoDto.textoInicial = document.getElementById('divTextoInicialQuestao').innerHTML;
        questaoDto.nomeListagem = listagem;
        questaoDto.sigla = document.formQuestaoAgrupada.sigla.value;

        questaoDto.valorPermitido1 = document.formQuestaoAgrupada.valPerm1.value;
        questaoDto.valorPermitido2 = document.formQuestaoAgrupada.valPerm2.value;       

        var peso = '';
        var opcao = '';
        var objsOpcoesRespostas = new Array();
        var tipoQuestao = document.formQuestaoAgrupada.tipoQuestao.value;
        if (tipoQuestao == 'R' || tipoQuestao == 'C' || tipoQuestao == 'X'){
            if (document.formQuestaoAgrupada.lstOpcoesAgrupada.length == null || document.formQuestaoAgrupada.lstOpcoesAgrupada.length == undefined || document.formQuestaoAgrupada.lstOpcoesAgrupada.length == 0){
                alert('<i18n:message key="questionario.informeNomeFormulario" />');
                return;
            }
            for(var i = 0; i < document.formQuestaoAgrupada.lstOpcoesAgrupada.length; i++){
                var opcaoRespostaDTO = new CIT_OpcaoRespostaQuestionarioDTO();
                opcao = document.formQuestaoAgrupada.lstOpcoesAgrupada.options[i].text;
                if (questaoPonderada == 'S') {
                    peso = opcao.substring(opcao.indexOf('[')+6,opcao.indexOf(']'));
                    opcaoRespostaDTO.titulo = opcao.substring(0,opcao.indexOf('[')-1);
                    opcaoRespostaDTO.peso = peso;
                }else{  
                    opcaoRespostaDTO.titulo = opcao;
                    opcaoRespostaDTO.peso = 0;
                }
                opcaoRespostaDTO.idOpcaoRespostaQuestionario = document.formQuestaoAgrupada.lstOpcoesAgrupada.options[i].value;
                
                objsOpcoesRespostas[i] = opcaoRespostaDTO;
            }
        }                

        var objsSerializadosQ = ObjectUtils.serializeObjects(objsOpcoesRespostas);
        questaoDto.serializeOpcoesResposta = objsSerializadosQ;
        
        if (editandoComplementoOpcao != 'S') {
	        atualizaObjQuestaoAgrupada(questaoDto,linAtualQuestaoAgrupada,colAtualQuestaoAgrupada);
	        configuraBotoesMatrizTabela();
        }else if (opcaoRespostaAtualDto != null) {
        	opcaoRespostaAtualDto.questaoComplementoDto = questaoDto;
        }        
        //POPUP_QUESTAO_AGRUPADA.hide();
        $("#POPUP_QUESTAO_AGRUPADA").dialog("close");
    };

    atualizarTextoFixoAgrupada = function(){
        if (StringUtils.isBlank(FCKeditorAPI.GetInstance('textoFixoAgrupada').GetXHTML())){
            alert('<i18n:message key="questionario.informeTextoQuestao" />');
            return;
        }

        var questaoDto = new CIT_QuestaoQuestionarioDTO();
        questaoDto.tituloQuestaoQuestionario = FCKeditorAPI.GetInstance('textoFixoAgrupada').GetXHTML();
        questaoDto.tipo = 'T';
        questaoDto.tipoQuestao = 'F'; 

        atualizaObjQuestaoAgrupada(questaoDto,linAtualQuestaoAgrupada,colAtualQuestaoAgrupada);
        configuraBotoesMatrizTabela();
        //POPUP_QUESTAO_AGRUPADA.hide();
        $("#POPUP_QUESTAO_AGRUPADA").dialog("close");
    };
    
    validarCamposMatriz = function() {
        /*
        if (StringUtils.isBlank(FCKeditorAPI.GetInstance('tituloQuestaoMatriz').GetXHTML())){
            alert('<i18n:message key="questionario.informeTituloQuestao" />');
            return false;
        }
        */
        if (StringUtils.isBlank(document.formQuestao.qtdeLinhas.value)){
            alert('<i18n:message key="questionario.informeQunatidadeLinhas" />');
            document.formQuestao.qtdeLinhas.focus();
            return false;
            }
        if (StringUtils.isBlank(document.formQuestao.qtdeColunas.value)){
            alert('<i18n:message key="questionario.informeQuantidadeColunas" />');
            document.formQuestao.qtdeColunas.focus();
            return false;
        }
        if (document.formQuestao.cabecalhoLinhas[0].checked == false && document.formQuestao.cabecalhoLinhas[1].checked == false) {
             alert('<i18n:message key="questionario.informeExisteTituloLinhas" />');
            document.formQuestao.cabecalhoLinhas[0].focus();
            return false;
        }    
        if (document.formQuestao.cabecalhoColunas[0].checked == false && document.formQuestao.cabecalhoColunas[1].checked == false) {
            alert('<i18n:message key="questionario.informeExisteTituloColunas" />');
            document.formQuestao.cabecalhoColunas[0].focus();
            return false;
        }    
        if (document.formQuestao.ultimoValorMatriz[1].checked && StringUtils.isBlank(document.formQuestao.siglaMatriz.value)) {
            alert('<i18n:message key="questionario.informeSiglaMatriz" />');
            document.formQuestao.siglaMatriz.focus();
            return false;
        }    
        return true;
    }
    
    validarCamposTabela = function() {
        if (StringUtils.isBlank(FCKeditorAPI.GetInstance('tituloQuestaoTabela').GetXHTML())){
            alert('<i18n:message key="questionario.informeTituloQuestao" />');
            return false;
        }
        if (StringUtils.isBlank(document.formQuestao.qtdeColunasTabela.value)){
            alert('<i18n:message key="questionario.informeQuantidadeColunas" />');
            document.formQuestao.qtdeColunasTabela.focus();
            return false;
        }
        if (document.formQuestao.ultimoValorTabela[1].checked && StringUtils.isBlank(document.formQuestao.siglaTabela.value)) {
            alert('<i18n:message key="questionario.informeTipoQuestao" />');
            document.formQuestao.siglaTabela.focus();
            return false;
        }    
        return true;
    }

    montarMatriz = function() {
        if (validarCamposMatriz() == true) {
            var qtdeLinhas = document.formQuestao.qtdeLinhas.value;
            var qtdeColunas = document.formQuestao.qtdeColunas.value;
            var cabecalhoLinhas = false;
            var cabecalhoColunas = false;
            if (document.formQuestao.cabecalhoLinhas[0].checked) {
            	cabecalhoLinhas = true;
            }
            if (document.formQuestao.cabecalhoColunas[0].checked) {
            	cabecalhoColunas = true;   
            }	         
            var s = '<table id="tblQuestoesAgrupadas" cellpadding="0" cellspacing="0" width="100%" border = "1">';
            if (cabecalhoColunas) {
                s = s + '<tr>';  
                if (cabecalhoLinhas) {
                    s = s + '<td><input type="text" name="cabecalhoColuna_0" id="cabecalhoColuna_0" size="10" maxlength="50"/></td>';
                }                
	            for(var c = 1; c <= qtdeColunas; c++){            
	                s = s + '<td><input type="text" name="cabecalhoColuna_'+ c + '" id="cabecalhoColuna_'+ c + '" size="10" maxlength="50"/></td>';
	            }       
                s = s + '</tr>';  
            }    
	        for(var l = 1; l <= qtdeLinhas; l++){
                s = s + '<tr>';
                if (cabecalhoLinhas) { 
                    s = s + '<td><input type="text" name="cabecalhoLinha_'+ l + '" id="cabecalhoLinha_'+ l + '" size="10" maxlength="50"/></td>';
                }    
                for(var c = 1; c <= qtdeColunas; c++){ 
                    s = s + '<td><table><tr><td>('+l+'.'+c+') </td><td><img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/configurar.gif" title="Clique aqui para configurar a questão (linha ' + l + ', coluna ' + c + ')" border="0" onclick="configurarQuestaoAgrupada('+l+','+c+')"/></td>';
                    s = s + '<td><img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/copiar.gif" title="Clique aqui para copiar esta configuração para outras células" border="0" id="btnCopiarQuestao_' + l +'_' + c+'" onclick="copiarQuestaoAgrupada('+l+','+c+')" style="display:none"/></td></tr></table></td>';
                }
                s = s + '</tr>';
            }
            document.getElementById('divQuestoesMatriz').innerHTML = s;
            document.getElementById('divQuestoesMatriz').style.display = 'block';
            configuraBotoesMatrizTabela();
            mostraCabecalhos();
        }    
    };    

    configuraBotoesMatrizTabela = function() {
        if (document.formQuestao.tipo[2].checked){      // Matriz      
            var qtdeLinhas = document.formQuestao.qtdeLinhas.value;
            var qtdeColunas = document.formQuestao.qtdeColunas.value;
        }else{                                          // Tabela
            var qtdeLinhas = 1;
            var qtdeColunas = document.formQuestao.qtdeColunasTabela.value;
        }    
        for(var l = 1; l <= qtdeLinhas; l++){
            for(var c = 1; c <= qtdeColunas; c++){
                if (existeQuestaoAgrupada(l,c)){
                    document.getElementById('btnCopiarQuestao_' + l + '_' + c).style.display = 'block';
                }    
            }    
        }                
    }
    
    mostraCabecalhos = function() {
        var qtdeLinhas = document.formQuestao.qtdeLinhas.value;
        var qtdeColunas = document.formQuestao.qtdeColunas.value;
        if (document.formQuestao.cabecalhoLinhas[0].checked) {
            for(var l = 1; l <= qtdeLinhas; l++){         
                if (objsCabecalhosLinha[l-1] != undefined && objsCabecalhosLinha[l-1] != null){
                    var questaoDto = objsCabecalhosLinha[l-1];                
                    document.getElementById('cabecalhoLinha_' + l).value = questaoDto.tituloQuestaoQuestionario; 
                }       
            }       
        }
        if (document.formQuestao.cabecalhoColunas[0].checked) {
            var p = 0;
            if (document.formQuestao.cabecalhoLinhas[0].checked) {
            	if (objsCabecalhosColuna[0] != undefined && objsCabecalhosColuna[0] != null){   
            	    var questaoDto = objsCabecalhosColuna[0];                
            	    document.getElementById('cabecalhoColuna_0').value = questaoDto.tituloQuestaoQuestionario;
            	}    
                p = 1;                 
            }    
            for(var c = 1; c <= qtdeColunas; c++){      
                if (objsCabecalhosColuna[p] != undefined && objsCabecalhosColuna[p] != null){                      
	                var questaoDto = objsCabecalhosColuna[p];                
	                document.getElementById('cabecalhoColuna_' + c).value = questaoDto.tituloQuestaoQuestionario; 
                }	
                p = p + 1;                  
            }       
        }
    }
        
    montarTabela = function(comValidacao) {
        var b = true;
        if (comValidacao){
            b = validarCamposTabela();
        }
        if (b == true) {
            var qtdeColunas = document.formQuestao.qtdeColunasTabela.value;
            var s = '<table id="tblQuestoesAgrupadas" cellpadding="0" cellspacing="0" width="100%" border = "1"><tr>';
            for(var c = 1; c <= qtdeColunas; c++){            
                s = s + '<td><table><tr><td>('+c+') </td><td><img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/configurar.gif" title="Clique aqui para configurar a questão (coluna ' + c + ')" border="0" onclick="configurarQuestaoAgrupada(1,'+c+')"/></td>';
                s = s + '<td><img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/produtos/citquestionario/imagens/copiar.gif" title="Clique aqui para copiar esta configuração para outras colunas" border="0" id="btnCopiarQuestao_1_'+c+'" onclick="copiarQuestaoAgrupada(1,'+c+')" style="display:none"/></td></tr></table></td>';
            }
            s = s + '</tr>';
            document.getElementById('divQuestoesTabela').innerHTML = s;
            document.getElementById('divQuestoesTabela').style.display = 'block'; 
            configuraBotoesMatrizTabela();   
        }    
    };    
    
    validarQuestoesMatriz = function(){
        var qtdeLinhas = document.formQuestao.qtdeLinhas.value;
        var qtdeColunas = document.formQuestao.qtdeColunas.value;
        for(var l = 1; l <= qtdeLinhas; l++){
            for(var c = 1; c <= qtdeColunas; c++){            
                if (!existeQuestaoAgrupada(l,c)){
                    alert('Questão não definida (linha ' + l + ', coluna ' + c + ')!');
                    return false;
                }        
            }
        }
        return true;
    } 

    validarQuestoesTabela = function(){
        var qtdeColunas = document.formQuestao.qtdeColunasTabela.value;
        for(var c = 1; c <= qtdeColunas; c++){            
            if (!existeQuestaoAgrupada(1,c)){
                alert('Questão não definida (coluna ' + c + ')!');
                return false;
            }        
        }
        return true;
    }

    copiarQuestaoAgrupada = function (linha, coluna) {
        linAtualQuestaoAgrupada = linha;
        colAtualQuestaoAgrupada = coluna;
        if (document.formQuestao.tipo[3].checked){        
            copiarQuestaoTabela(coluna);            
        }else{
            var qtdeLinhas = document.formQuestao.qtdeLinhas.value;
            var qtdeColunas = document.formQuestao.qtdeColunas.value;
	        var s = '';
	        HTMLUtils.removeAllOptions('lstQuestoes');
	        for(var l = 1; l <= qtdeLinhas; l++){
	            for(var c = 1; c <= qtdeColunas; c++){
	                if (linha != l || coluna != c) {
		                s = 'Linha ' + l + ', Coluna ' + c;
		                if (existeQuestaoAgrupada(l,c)){ 
		                    s = s + ' (já configurada)';
		                }                    
		                HTMLUtils.addOption('lstQuestoes', s, l+'#'+c);
	                }
	            }
	        }
        }                    
    	//POPUP_COPIAR_QUESTAO.show();
    	$("#POPUP_COPIAR_QUESTAO").dialog("open");
    }

    copiarQuestaoTabela = function (coluna) {
        var qtdeColunas = document.formQuestao.qtdeColunasTabela.value;            
        var s = '';

        HTMLUtils.removeAllOptions('lstQuestoes');
        for(var c = 1; c <= qtdeColunas; c++){
            if (c != coluna) {
                s = 'Coluna ' + c; 
                if (existeQuestaoAgrupada(1,c)){
                    s = s + ' (já configurada)';
                }                    
                HTMLUtils.addOption('lstQuestoes', s, '1#'+c);
            }
        }
    }
    
    copiarQuestaoFunction = function() {
        var confirmou = false;
        var questaoOrigem = retornaObjQuestaoAgrupada(linAtualQuestaoAgrupada,colAtualQuestaoAgrupada);
        
        for(var i = 0; i < document.formCopiarQuestao.lstQuestoes.options.length; i++){
            if (document.formCopiarQuestao.lstQuestoes.options[i].selected){
                var s = document.formCopiarQuestao.lstQuestoes.options[i].value;  
                var linha = parseInt(s.substring(0,parseInt(s.indexOf('#'))));
                var coluna = parseInt(s.substring(s.indexOf('#')+1));
                if (!confirmou && existeQuestaoAgrupada(linha,coluna)) {
                    if (!confirm('Todas as questões já configuradas serão substituídas. Deseja prosseguir?')) {
                        //POPUP_COPIAR_QUESTAO.hide();
                        $("#POPUP_COPIAR_QUESTAO").dialog("close");
                        return;
                    }    
                }
                confirmou = true;    
                atualizaObjQuestaoAgrupada(questaoOrigem,linha,coluna);
            }
        }
        //POPUP_COPIAR_QUESTAO.hide();
        $("#POPUP_COPIAR_QUESTAO").dialog("close");
        configuraBotoesMatrizTabela();
    }    
              
    atualizarQuestaoMatriz = function(questaoDto, tblAdicionar) {
        questaoDto.tipo = 'M';
        questaoDto.tituloQuestaoQuestionario = FCKeditorAPI.GetInstance('tituloQuestaoMatriz').GetXHTML();
        questaoDto.tipoQuestao = '3';
        if (document.formQuestao.imprimeMatriz[0].checked) {
            questaoDto.imprime = 'S';
        }else{
        	questaoDto.imprime = 'N';
        }        
        questaoDto.sigla = document.formQuestao.siglaMatriz.value;
        questaoDto.qtdeLinhas = document.formQuestao.qtdeLinhas.value;
        questaoDto.qtdeColunas = document.formQuestao.qtdeColunas.value;
        if (document.formQuestao.cabecalhoLinhas[0].checked) {
            questaoDto.cabecalhoLinhas = 'S';
        }else{    
            questaoDto.cabecalhoLinhas = 'N';
        }    
        if (document.formQuestao.cabecalhoColunas[0].checked) {
            questaoDto.cabecalhoColunas = 'S';
        }else{    
            questaoDto.cabecalhoColunas = 'N';
        } 
        
        questaoDto.ultimoValor = 'N';
        if (document.formQuestao.ultimoValorMatriz[1].checked){
            questaoDto.ultimoValor = 'G';
        }   
        
        questaoDto.idTabela = tblAdicionar;
        questaoDto.idControleQuestao = document.getElementById('idControleQuestao').value;
        var objsQuestoesAux = new Array();
        var p = 0;
        for(var l = 0; l < questaoDto.qtdeLinhas; l++){
            var objsColunas = objsQuestoesAgrupadas[l];
            for(var c = 0; c < questaoDto.qtdeColunas; c++){
            	objsQuestoesAux[p] = objsColunas[c];
            	p = p + 1; 
            }    
        }    
        var objsSerializadosQ = ObjectUtils.serializeObjects(objsQuestoesAux);
        questaoDto.serializeQuestoesAgrupadas = objsSerializadosQ;
        atualizarCabecalhos(questaoDto);
    }

    atualizarCabecalhos = function(questaoDto) {
        var qtdeLinhas = document.formQuestao.qtdeLinhas.value;
        var qtdeColunas = document.formQuestao.qtdeColunas.value;
        var cabecalhoLinhas = false;
        var cabecalhoColunas = false;

        objsCabecalhosLinha = new Array();
        objsCabecalhosColuna = new Array();    
        if (document.formQuestao.cabecalhoLinhas[0].checked) {
            cabecalhoLinhas = true;
        }
        if (document.formQuestao.cabecalhoColunas[0].checked) {
            cabecalhoColunas = true;   
        } 
        if (cabecalhoLinhas) {
            for(var l = 1; l <= qtdeLinhas; l++){   
                var cabecalho = new CIT_QuestaoQuestionarioDTO();
                cabecalho.tipo = 'T';
                cabecalho.tituloQuestaoQuestionario = document.getElementById('cabecalhoLinha_' + l).value;
                cabecalho.tipoQuestao = '4';
                objsCabecalhosLinha[l-1] = cabecalho;
            }       
        }        
        if (cabecalhoColunas) {
            var p = 0;
        	if (document.formQuestao.cabecalhoLinhas[0].checked) {
                var cabecalho = new CIT_QuestaoQuestionarioDTO();
                cabecalho.tipo = 'T';
                cabecalho.tituloQuestaoQuestionario = document.getElementById('cabecalhoColuna_0').value;
                cabecalho.tipoQuestao = '5';
                objsCabecalhosColuna[0] = cabecalho;
                p = 1;           	
        	}	
            for(var c = 1; c <= qtdeColunas; c++){   
                var cabecalho = new CIT_QuestaoQuestionarioDTO();
                cabecalho.tipo = 'T';
                cabecalho.tituloQuestaoQuestionario = document.getElementById('cabecalhoColuna_' + c).value;
                cabecalho.tipoQuestao = '5';
                objsCabecalhosColuna[p] = cabecalho;
                p = p + 1;
            }       
        }
        var objsSerializadosQ = ObjectUtils.serializeObjects(objsCabecalhosLinha);
        questaoDto.serializeCabecalhosLinha = objsSerializadosQ;
        var objsSerializadosQ = ObjectUtils.serializeObjects(objsCabecalhosColuna);
        questaoDto.serializeCabecalhosColuna = objsSerializadosQ;
    }
        
    atualizarQuestaoTabela = function(questaoDto, tblAdicionar) {
        questaoDto.tipo = 'L';
        questaoDto.tituloQuestaoQuestionario = FCKeditorAPI.GetInstance('tituloQuestaoTabela').GetXHTML();
        questaoDto.tipoQuestao = '3';
        questaoDto.sigla = document.formQuestao.siglaTabela.value;
        if (document.formQuestao.imprimeTabela[0].checked) {
            questaoDto.imprime = 'S';
        }else{
            questaoDto.imprime = 'N';
        }        

        questaoDto.ultimoValor = 'N';
        if (document.formQuestao.ultimoValorTabela[1].checked){
            questaoDto.ultimoValor = 'G';
        }       
        
        questaoDto.qtdeColunas = document.formQuestao.qtdeColunasTabela.value;
        questaoDto.idTabela = tblAdicionar;
        questaoDto.idControleQuestao = document.getElementById('idControleQuestao').value;
        var objsColunas = new Array();
        var objsAux = objsQuestoesAgrupadas[0];
        for(var l = 0; l < questaoDto.qtdeColunas; l++){
        	objsColunas[l] = objsAux[l];
        }    
        var objsSerializadosQ = ObjectUtils.serializeObjects(objsColunas);
        questaoDto.serializeQuestoesAgrupadas = objsSerializadosQ;
    }
            
    adicionarMatriz = function(){
        if (validarCamposMatriz() == true && validarQuestoesMatriz() == true) { 
            var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
           
            var questaoDto = new CIT_QuestaoQuestionarioDTO();
            atualizarQuestaoMatriz(questaoDto, tblAdicionar);
                        
            var corOnAux = HTMLUtil_colorOn;
            var corOffAux = HTMLUtil_colorOff;
            HTMLUtil_colorOn = 'yellow';
            HTMLUtil_colorOff = HTMLUtil_colorOn;
            HTMLUtils.addRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, true);
            HTMLUtil_colorOn = corOnAux;
            HTMLUtil_colorOff = corOffAux;      
            //POPUP_QUESTAO.hide();
            $("#POPUP_QUESTAO").dialog("close");
        };
    }

    adicionarTabela = function(){
        if (validarCamposTabela() == true && validarQuestoesTabela() == true) { 
            var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
           
            var questaoDto = new CIT_QuestaoQuestionarioDTO();
            atualizarQuestaoTabela(questaoDto, tblAdicionar);
                        
            var corOnAux = HTMLUtil_colorOn;
            var corOffAux = HTMLUtil_colorOff;
            HTMLUtil_colorOn = 'yellow';
            HTMLUtil_colorOff = HTMLUtil_colorOn;
            HTMLUtils.addRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, true);
            HTMLUtil_colorOn = corOnAux;
            HTMLUtil_colorOff = corOffAux;      
//             POPUP_QUESTAO.hide();
            $("#POPUP_QUESTAO").dialog("close");
        };
    }
    
    atualizarMatriz = function(){
        if (validarCamposMatriz() == true && validarQuestoesMatriz() == true) {        
            var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
            var idIndex = 0;
            
            idIndex = document.getElementById('idIndex').value;
            var questaoDto = HTMLUtils.getObjectByTableIndex(tblAdicionar, idIndex);
            atualizarQuestaoMatriz(questaoDto, tblAdicionar);
                                    
            var corOnAux = HTMLUtil_colorOn;
            var corOffAux = HTMLUtil_colorOff;
            HTMLUtil_colorOn = 'yellow';
            HTMLUtil_colorOff = HTMLUtil_colorOn;
            HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
            HTMLUtil_colorOn = corOnAux;
            HTMLUtil_colorOff = corOffAux;      
//             POPUP_QUESTAO.hide();
            $("#POPUP_QUESTAO").dialog("close");
        }    
    };
    
    atualizarTabela = function(){
        if (validarCamposTabela() == true && validarQuestoesTabela() == true) {        
            var tblAdicionar = 'tblItem_' + document.getElementById('idControleQuestao').value;
            var idIndex = 0;
            
            idIndex = document.getElementById('idIndex').value;
            var questaoDto = HTMLUtils.getObjectByTableIndex(tblAdicionar, idIndex);
            atualizarQuestaoTabela(questaoDto, tblAdicionar);
                                    
            var corOnAux = HTMLUtil_colorOn;
            var corOffAux = HTMLUtil_colorOff;
            HTMLUtil_colorOn = 'yellow';
            HTMLUtil_colorOff = HTMLUtil_colorOn;
            HTMLUtils.updateRow(tblAdicionar, null, '', questaoDto, ['tituloQuestaoQuestionario'], null, '<i18n:message key="questionario.existeQuestaoNome" />', null, mostraQuestao, null, idIndex, true);
            HTMLUtil_colorOn = corOnAux;
            HTMLUtil_colorOff = corOffAux;      
//             POPUP_QUESTAO.hide();
            $("#POPUP_QUESTAO").dialog("close");
        }    
    };

    selecionaCategoriaQuest = function (obj){
        document.form.idCategoriaQuestionario.value = obj.value;
    }

    copiarGrupo = function(){
        if (StringUtils.isBlank(document.formCopiarGrupo.idGrupoQuestionario.value)){
            alert('<i18n:message key="questionario.informeGrupoCopiar" />');
            document.formCopiarGrupo.idGrupoQuestionario.focus();
            return false;
        }        
        if (StringUtils.isBlank(document.formCopiarGrupo.nomeGrupoQuestionario.value)){
              alert('<i18n:message key="questionario.informeGrupo"/>');
          
            document.formCopiarGrupo.nomeGrupoQuestionario.focus();
            return false;
        }        
    	document.formCopiarGrupo.fireEvent("copiarGrupoQuestionario");
    }

    excluirGrupo = function(){
        var tblGrupo = 'tblConfigQuestionario';
        
        var objsGrupos = HTMLUtils.getObjectsByTableId(tblGrupo);
        
        HTMLUtils.deleteAllRows('tblExcluirGrupo');
        for(var i = 0; i < objsGrupos.length; i++){
            HTMLUtils.addRow('tblExcluirGrupo', null, '', objsGrupos[i], ['', 'infoGrupo'], null, '<i18n:message key="questionario.jaExisteGrupo" />', [execAddItemExcluirGrupo], null, null, true);
        }

        if (!confirm('<i18n:message key="questionario.salvarAntesOrdenacao" />')){
            return;
        }
                
    	//POPUP_GRUPO_EXCLUIR_GRUPO.show();
    	$("#POPUP_GRUPO_EXCLUIR_GRUPO").dialog("open");
    }

    function mostrarTextoInicial(){
    	FCKeditorAPI.GetInstance('textoInicialQuestaoQuestionario').SetData(document.getElementById('divTextoInicialQuestao').innerHTML);
    	//POPUP_TEXTO.show();
    	$("#POPUP_TEXTO").dialog("open");
    }

	
	abrirPopUPImportar = function(){
 		$("#POPUP_IMPORTAR").dialog("open");
	}
	
	fecharPopUPListagemQuestionario = function(){
		$("#POPUP_LISTAGEM_QUESTIONARIOS").dialog("close");
	}
	
	fecharPopUPTexto = function(){
		$("#POPUP_TEXTO").dialog("close");
	}
	
	fecharPopUPGrupoCopiar = function(){
		$("#POPUP_GRUPO_COPIAR").dialog("close");
	}
	
	abriPopUpGrupoCopiar = function(){
		$("#POPUP_GRUPO_COPIAR").dialog("open");
	}
	 
</script>
	<%if(request.getParameter("iframe")!=null && request.getParameter("iframe").equals(true)){%>
	<div id="divLogo" style="overflow: hidden!important;">
			<table cellpadding='0' cellspacing='0'>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<img border="0" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/logo.png" />
					</td>
				</tr>
				
			</table>	
			
		</div>
	<%}%>
<div>
    <form name='formAuxiliar'>
		<fieldset style="width: 100">
			<legend><b><i18n:message key="questionario.cadastroQuestionario" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</b></legend>
	        <table cellpadding="0" cellspacing="0" >
				<tr>
					<td style="width: 180px">
						<span id='spanTitulo'></span>&nbsp;
					</td>
					<td>
						<%
						String enderecoServidor = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS_CITSAUDE");
						if (enderecoServidor == null || enderecoServidor.trim().equalsIgnoreCase("")){
							enderecoServidor = "";
						}		
						if (enderecoServidor.equalsIgnoreCase("")){
							enderecoServidor = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS");
						}
						%>
						<div>
							&nbsp;
						</div>
						<a type='button' name='btnExportarQuest' class='btnQuest'  onclick='gravarQuestionario()'><i18n:message key="citcorpore.comum.gravar" /></a>&nbsp;
						<a type='button' name='btnExportarQuest' class='btnQuest'  onclick='listagemQuestionario()'><i18n:message key="questionario.abrir" /></a>&nbsp;
						<a type='button' name='btnExportarQuest' class='btnQuest'  onclick='novoQuestionario()'><i18n:message key="questionario.novo" /></a>&nbsp;
<%-- 						<a class='btnQuest' href='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load'><i18n:message key="questionario.voltar" /></a> --%>
					</td>
				</tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
	            <tr>
	                <td style='width: 180px' class="campoObrigatorioQuestionario">
	                <i18n:message key="questionario.nomeQuestionario" />:
	                </td>
	                <td>
	                    <input type='text' name='nomeQuestionario' id='nomeQuestionario' size="50" maxlength="50"/> 
	                </td>
	            </tr>
	            <tr>
                    <td class="campoObrigatorioQuestionario">
                        <i18n:message key="questionario.categoriaQuestionario" />:
                    </td>
                    <td>
                        <select name='idCategoriaQuestionarioAux' id='idCategoriaQuestionarioAux' onclick='selecionaCategoriaQuest(this)'>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>&nbsp;</td>
                </tr>
                <tr>
                    <td>
                    <i18n:message key="questionario.javascriptQuestionario" />:
                    </td>
                    <td colspan='3'>
                        <textarea rows="8" cols="80" name="javaScript" id="javaScriptAux"></textarea>
                    </td>
	            </tr>
	        </table>   
		</fieldset> 
    </form>
</div>

<table width="100%">
    <tr>
        <td width="100%"><br/>
            <!-- ## AREA DA APLICACAO ## -->
            <form name='form' id='form' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'>
                <input type='hidden' name='acao'/>
                <input type='hidden' name='nomeQuestionario'/>
                <input type='hidden' name='idQuestionario' id='idQuestionario'/>
                <input type='hidden' name='idCategoriaQuestionario' id='idCategoriaQuestionario' value=''/>
                <input type='hidden' name='idQuestionarioOrigem' id='idQuestionarioOrigem'/>
                <input type='hidden' name='ativo' id='ativo'/>
                <input type='hidden' name='colQuestionariosSerialize'/>
                <input type='hidden' name='reload' value='false'/>
                <input type='hidden' name='idControleQuestao' id='idControleQuestao' />
       			<input type='hidden' name='idIndex' id='idIndex' />
				<input type='hidden' name='idIndexGrupo' />
				<input type='hidden' name='idControleQuestaoGrupo' id='idControleQuestaoGrupo' />
                
                <input type='hidden' name='tabelaOrdenar'/>
                <input type='hidden' name='tabelaExcluir'/>
                <input type='hidden' name='javaScript'/>

                <div class='row-fluid'>                              
                            <button type='button' name='btnExportarQuest' class='btnQuest span2'  onclick='mostrarAdicionarNovoGrupo()'><i18n:message key="questionario.adicionarNovoGrupo" /></button>
                            <button type='button' id='btnCapGrupoQuest' name='btnExportarQuest' class='btnQuest span2'  onclick='document.formCopiarGrupo.fireEvent("listarQuestionarios"); document.formCopiarGrupo.idQuestionarioCopiar.value = document.form.idQuestionario.value; abriPopUpGrupoCopiar()'><i18n:message key="questionario.copiarOutroQuestionario" /></button>                   
                            <button type='button' id='btnOrdenarGrupos' name='btnExportarQuest' class='btnQuest span1'  onclick='ordenarGrupos()'><i18n:message key="questionario.ordenarGrupos" /></button>                      
                            <button type='button' id='btnExcluirGrupo' name='btnExportarQuest' class='btnQuest span1'  onclick='excluirGrupo()'><i18n:message key="questionario.excluirGrupos" /></button>
                            <button type='button' id='btnVisualiza' name='btnExportarQuest' class='btnQuest span2'  onclick='visualizarQuestionario()'><i18n:message key="questionario.visualizarQuestionario" /></button>
                            <button type='button' name='btnExportarQuest' class='btnQuest span2'  onclick='exportarQuestionario()'><i18n:message key="questionario.exportarQuestionario" /></button>
                            <button type='button' name='btnExportarQuest' class='btnQuest span2'  onclick='abrirPopUPImportar()'><i18n:message key="questionario.importarQuestionario" /></button>                                                          
                </div>
                <table id='tblConfigQuestionario' class="tableLess" cellpadding="0" cellspacing="0" width="100%" >
                     <tr>
                        <td class="linhaSubtituloGrid" width="15%">
                        <i18n:message key="questionario.acoes" />
                        </td>
                        <td class="linhaSubtituloGrid">
                        <i18n:message key="questionario.conteudoQuestionario" />
                        </td>                                           
                     </tr>
                </table>
                
            </form>
            <!-- ## FIM - AREA DA APLICACAO ## -->          
        </td>
    </tr>
</table>

<script>
    document.form.afterRestore = function () {
    };
    
    document.form.onClear = function () {
    };
    
    abrirQuestionario = function(){
        if (document.formAbrirQuestionario.lstQuestionarios.selectedIndex == null || 
            document.formAbrirQuestionario.lstQuestionarios.selectedIndex == undefined ||
            document.formAbrirQuestionario.lstQuestionarios.selectedIndex == -1){
            alert('<i18n:message key="questionario.selecioneQuestionario" />');
            return;
        }
        
        var id = document.formAbrirQuestionario.lstQuestionarios.options[document.formAbrirQuestionario.lstQuestionarios.selectedIndex].value;
        abre(id);
    };

    abrirQuestPosOrder = function(){
        abre(document.form.idQuestionario.value);
    };    
    
    abre = function(id){
        window.location = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario.load?acao=restore&idQuestionario=' + id;
    };
    
//     document.getElementById('divTextoFixo').style.left = document.getElementById('divQuestao').style.left;
//     document.getElementById('divTextoFixo').style.top = document.getElementById('divQuestao').style.top;
</script>

<!-- SCRIPT DE CARGA DOS DADOS DE RESTORE, SE HOUVER -->
        <script>
        idControleTabela = 0;
        <c:forEach var="item" items="${grupos}">
            addicionarGrupoFunction('<c:out value="${item.nomeGrupoQuestionario}"/>', <c:out value="${item.idGrupoQuestionario}"/>);
            
            document.getElementById('idControleQuestao').value = '' + (idControleTabela - 1);
            
            <c:forEach var="itemQuestao" items="${item.colQuestoes}">                           
                adicionarQuestaoFunction('${itemQuestao.tipo}',
                                            '${itemQuestao.idQuestaoOrigem}',                                            
                                            '${itemQuestao.tituloQuestaoQuestionarioNoEnter}', 
                                            '${itemQuestao.tipoQuestao}',
                                            '${itemQuestao.unidade}',
                                            '${itemQuestao.valoresReferencia}',
                                            '${itemQuestao.obrigatoria}',
                                            '${itemQuestao.ponderada}',
                                            '${itemQuestao.calculada}',
                                            '${itemQuestao.infoResposta}',
                                            '${itemQuestao.textoInicialNoEnter}',
                                            '${itemQuestao.tamanho}',
                                            '${itemQuestao.decimais}',
                                            '${itemQuestao.qtdeLinhas}',
                                            '${itemQuestao.qtdeColunas}',
                                            '${itemQuestao.cabecalhoLinhas}',
                                            '${itemQuestao.cabecalhoColunas}',
                                            '${itemQuestao.nomeListagem}',
                                            '${itemQuestao.serializeOpcoesResposta}',
                                            '${itemQuestao.serializeQuestoesAgrupadas}',
                                            '${itemQuestao.serializeCabecalhosLinha}',
                                            '${itemQuestao.serializeCabecalhosColuna}',
                                            '${itemQuestao.ultimoValor}',
                                            '${itemQuestao.idSubQuestionario}',
                                            '${itemQuestao.abaResultSubForm}',
                                            '${itemQuestao.sigla}',
                                            '${itemQuestao.imprime}',
                                            '${itemQuestao.editavel}', 
                                            NumberUtil.format(NumberUtil.toDouble('${itemQuestao.valorPermitido1Str}'), 3, ",", "."),
                                            NumberUtil.format(NumberUtil.toDouble('${itemQuestao.valorPermitido2Str}'), 3, ",", "."),
                                            '${itemQuestao.idImagem}',
                                            '${itemQuestao.valorDefaultNoEnter}'
                                            );                                                                             
            </c:forEach>
        </c:forEach>
       </script>
</body>
<div id="POPUP_IMPORTAR" title='<i18n:message key="questionario.selecionarArquivo" />' >
	<div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formImportar' method='post' ENCTYPE="multipart/form-data"  action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'/>
						<input type='hidden' name='acao' id='acaoImportar' value='<i18n:message key="dataManager.import" />'/>
				        <table width="100%">
				            <tr>
				                <td>
				                
				                  <i18n:message key="questionario.arquivoImportar" />:*
				                </td>
				                <td>
				                   <input type='file' name='fileImportar' size="50" value='<i18n:message key="questionario.selecionarArquivo" />'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' value='OK' onclick='importarQuestionario()' />
				                </td>
				            </tr>
				        </table>
				    </form>
    			</div>
			</div>
		</div>
	</div>
</div>

<div  id="POPUP_GRUPO" title='<i18n:message key="questionario.configuracaoQuestionario" />' >
    <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formGrupo' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'/>
				        <input type='hidden' name='idGrupoQuestionario' />
				        <table>
				            <tr>
				                <td class="campoObrigatorioQuestionario">
				                <i18n:message key="questionario.nomeGrupoQuestionario" />
				                </td>
				                <td>
				                    <input type='text' name='nomeGrupoQuestionario' size="80" maxlength="80"/>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <table>
				                        <tr>
				                            <td>
				                                <input type='button' name='btnAdicionarGrupo' class='ui-button'  value='<i18n:message key="questionario.adicionar" />' onclick="adicionarGrupo()"/>
				                            </td>
				                            <td>
				                                <input type='button' name='btnAtualizarGrupo'  class='ui-button' value='<i18n:message key="questionario.atualizar" />' onclick="atualizarGrupo()"/>
				                            </td>
				                        </tr>
				                    </table>
				                </td>
				            </tr>           
				        </table>
				    </form>
	    		</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_LISTAGEM_QUESTIONARIOS" title='<i18n:message key="questionario.listagemQuestionario" />' >
     <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section"> 
				    <form name='formAbrirQuestionario'>
				        <table>
				            <tr>
				                <td>
				                    <select name="lstQuestionarios" id="lstQuestionarios" style='width: 645px; height: 350px' size="18">
				                    </select>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnAbrir' value='<i18n:message key="questionario.abrirQuestionario" />' onclick='abrirQuestionario()'/> 
				                    <input type='button' class='ui-button' name='btnFecharListagem' value='<i18n:message key="questionario.fechar" />' onclick='fecharPopUPListagemQuestionario()'/>
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO_EXCLUIR" title='<i18n:message key="questionario.excluirQuestoesGrupo" />' >
   <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">  
				    <form name='formExcluirQuestaoGrupo' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><i18n:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><i18n:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                   <i18n:message key="questionario.questoes" />:
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesExcluir' style='width:540px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblExcluir' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesExcluirAtualizar' style='display:none'>
				                        <i18n:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>           
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarExcl'   value='<i18n:message key="questionario.salvar" />' onclick='gravarQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarExcl'   value='<i18n:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>           
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_GRUPO_ORDENAR" title='<i18n:message key="questionario.ordenarQuestionario" />'>
   <div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">    
				    <form name='formOrdenarGrupo' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><i18n:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><i18n:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                    <i18n:message key="questionario.questoes" />:
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesOrdenar' style='width:540px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblOrdenar' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesOrdenarAtualizar' style='display:none'>
				                        <i18n:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>           
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarOrd'  value='<i18n:message key="questionario.salvar" />' onclick='gravarQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarOrd'  value='<i18n:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>           
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_QUESTAO" title='<i18n:message key="questionario.configurarElemento" />'>
   <div class="box grid_16 tabs"  style="width: 840px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">     
				    <form name='formQuestao' id='formQuestao'>
				        <input type='hidden' name='idQuestaoQuestionario' />
				        <div>
				            <table>
				                <tr>
				                    <td>
				                    <i18n:message key="questionario.informeElemento" />:
				                    </td>
				                    <td>
				                        <input type='radio' name='tipo' value='Q' onclick='verificaTipo()'/><i18n:message key="questionario.questoes" /> 
				                        <input type='radio' name='tipo' value='T' onclick='verificaTipo()'/><i18n:message key="questionario.textoFixo" />   
				                        <input type='hidden' name='tipo' value='M' onclick='verificaTipo()'/><!-- Matriz  comentado por nao estar funcionando a corrigir depois  --> 
				                        <input type='hidden' name='tipo' value='B' onclick='verificaTipo()'/><!-- Tabela  comentado por nao estar funcionando a corrigir depois  -->
				                        <input type='radio' name='tipo' value='P' onclick='verificaTipo()'/>HTML/Java Script
				                        
				                    </td>
				                </tr>
				            </table>
				            <div id='divQuestao' style='display: none'>
				                <table width='100%' border="0">
				                    <tr>
				                        <td colspan="2">
				                            <i18n:message key="questionario.textoIncial" /> :    <a href='#' onclick="mostrarTextoInicial()"><i18n:message key="questionario.cliqueEditarTexto" /> </a>
				                        </td>
				                    </tr>   
				                    <tr>                        
				                        <td colspan="2">
				                            <div id='divTextoInicialQuestao' style='height: 50px; overflow: auto; border: 1px solid black;'></div>
				                        </td>
				                    </tr>       
				                    <tr>
				                        <td style="5%" class='campoObrigatorioQuestionario campoEsquerda'>
				                        <i18n:message key="questionario.tituloQuestao" />:
				                        </td>
				                        <td>
				                            <textarea rows="5" cols="90" name="tituloQuestaoQuestionario" id="tituloQuestaoQuestionario" style='width: 100%'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td class="campoObrigatorioQuestionario">
				                           <i18n:message key="questionario.tipoQuestao" />:
				                        </td>
				                        <td>
				                            <table cellpadding="0" cellspacing="0">
				                                <tr>
				                                    <td>
				                                        <select name='tipoQuestao' onchange='verificaTipoQuestao()'>
				                                            <option value=''><i18n:message key="citcorpore.comum.selecione" /></option>
				                                            <option value='T'><i18n:message key="questionario.textoCurto" /></option>
				                                            <option value='A'><i18n:message key="questionario.textoLongoObs" /></option>
				                                            <option value='L'><i18n:message key="questionario.textoLongoEditor" /></option>
				                                            <option value='R'><i18n:message key="questionario.radioTxt" /></option>
				                                            <option value='C'><i18n:message key="questionario.checkBoxTxt" /></option>
				                                            <option value='X'><i18n:message key="questionario.comboBoxTxt" /></option>
				                                            <option value='N'><i18n:message key="questionario.numeroTxt" /></option>
				                                            <option value='V'><i18n:message key="questionario.valorDecimal" /></option>
				                                            <option value='%'><i18n:message key="questionario.percentualTxt" /></option>
				                                            <option value='*'><i18n:message key="questionario.percentualValorTxt" /></option>
				                                            <option value='1'><i18n:message key="questionario.faixaValoresNumeros" /></option>
				                                            <option value='2'><i18n:message key="questionario.faixaValoresDecimais" /></option>
				                                            <option value='D'><i18n:message key="citcorpore.comum.data" /></option>
				                                            <option value='H'><i18n:message key="citcorpore.texto.tempo.hora" /></option>
				                                            <option value='G'><i18n:message key="questionario.mesAno" /></option>                                           
				                                            <option value='M'><i18n:message key="questionario.galeriaMultimidia" /></option>
				                                        </select>
				                                    </td>
				                                    <td>
				                                        &nbsp;
				                                    </td>
				                                    <td>
				                                        <div id='divTamanho' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <i18n:message key="questionario.tamanho" />:
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='tamanho' size="10" maxlength="10" class='Format[Numero]'/>
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divDecimais' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                       <i18n:message key="questionario.decimais" />:
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='decimais' size="3" maxlength="3" class='Format[Numero]'/>
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>   
				                                    <td>
				                                        <div id='divPonderada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                       <i18n:message key="questionario.ponderada" />:
				                                                    </td>
				                                                    <td>
				                                                        <input type='radio' name='ponderada' value='S' onclick='verificaTipoQuestao()'/> <i18n:message key="citcorpore.comum.sim" /> 
				                                                        <input type='radio' name='ponderada' value='N' onclick='verificaTipoQuestao()' checked="checked"/> <i18n:message key="citcorpore.comum.nao" />
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>                                                   
				                                    <td>
				                                        <div id='divListagem' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                    <i18n:message key="questionario.tipoListagem" />:
				                                                    </td>
				                                                    <td>
							                                            <select name='nomeListagem'>
							                                            </select>
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>                                                   
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>   
				                    <tr>
				                        <td colspan="2">
				                            <table border = "0">
				                                <tr>
						                             <td>
						                              <i18n:message key="questionario.ondeAparecer" />
						                                 
						                             </td>
						                             <td>
						                                 <input type='radio' value='L' name='infoResposta' checked="checked"/> <i18n:message key="questionario.ladoPergunto" /> 
						                             </td>
						                             <td>
						                                 <input type='radio' value='B' name='infoResposta'/><i18n:message key="questionario.baixoPergunta" /> 
						                             </td>
				                                </tr>                                   
				                            </table>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">
				                            <fieldset>
				                                <legend><B><i18n:message key="questionario.informacoesEspecificasQuestao" /></B></legend>
				                                <div style='height: 420px; width: 770px; overflow: auto'>
				                                    <div id='divCheckboxRadioCombo' style='display:none'>
				                                        <fieldset><legend><b><i18n:message key="questionario.opcoes" /></b></legend>
				                                        <table width='100%' border="0">
				                                            <tr>
				                                                <td>
				                                                    <div id='divOpcaoQuestao' style='display:none'>
					                                                    <table width='100%' >
					                                                        <tr>
								                                                <td>
								                                                    <i18n:message key="questionario.opcao" />:
								                                                </td>
								                                                <td>
								                                                    <input type='text' name='txtOpcao' size="45"/>
								                                                </td>
								                                                <td>
								                                                    <i18n:message key="questionario.valor" />:
								                                                </td>
								                                                <td>
								                                                    <input type='text' name='txtValor' size="6"/>
								                                                </td>
								                                                <td>
								                                                    <div id='divPeso' style='display:none'>
								                                                        <table cellpadding="0" cellspacing="0">
								                                                            <tr>
								                                                                <td>
								                                                                    <i18n:message key="questionario.peso" />:
								                                                                </td>
								                                                                <td>
								                                                                    <input type='text' name='peso' size="3" maxlength="3" class='Format[Numero]'/>
								                                                                </td>                                       
								                                                            </tr>
								                                                        </table>
								                                                    </div> 
								                                                </td>
								                                                <td>
								                                                   <input type='checkbox' name='geraAlerta' value='S'/><span><i18n:message key="questionario.alerta" /></span>
								                                                </td>
								                                                <td>
								                                                   <input type='checkbox' name='exibeComplemento' value='S' onchange="verificaExibicaoComplementoOpcao()" /><span><i18n:message key="questionario.exibeComplemento" /></span>
								                                                </td>
					                                                        </tr>
					                                                    </table>
				                                                    </div>	                                                        
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <table>
				                                                        <tr>
				                                                            <td><input type='button' class ='ui-button' name='btnComplementoOpcao' style='display:none' value='<i18n:message key="questionario.configuraComplemento" />' onclick="configurarComplementoOpcao()"/></td>
						                                                    <td><input type='button' class ='ui-button' name='btnNovaOpcao' style='display:block' value='<i18n:message key="questionario.novaOpcao" />' onclick="novaOpcaoQuestao()"/></td>
						                                                    <td><input type='button' class ='ui-button' name='btnAddOpcao' style='display:none' value='<i18n:message key="questionario.adicionar" />' onclick="adicionarOpcaoQuestao()"/></td>
						                                                    <td><input type='button' class ='ui-button' name='btnAtuOpcao' style='display:none' value='<i18n:message key="questionario.atualizar" />' onclick="atualizarOpcaoQuestao()"/></td>
				                                                        </tr>
				                                                    </table>
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <fieldset>
				                                                    <table width="100%"><tr>
				                                                    <td>
					                                                    <div style='height: 70px; width: 600px; overflow: auto; border: 1px solid black'>
						                                                    <table id='tblOpcoes' cellpadding="0" cellspacing="0" width="100%" border="0">
						                                                        <tr>
						                                                            <td class="linhaSubtituloGrid" width="5%">
						                                                                &nbsp;
						                                                            </td>
						                                                            <td class="linhaSubtituloGrid" width="60%">
						                                                                <i18n:message key="questionario.opcao" />
						                                                            </td>
					                                                                <td class="linhaSubtituloGrid" width="25%">
					                                                                    <i18n:message key="questionario.valor" />
					                                                                </td>
						                                                            <td class="linhaSubtituloGrid" width="10%">
						                                                                <i18n:message key="questionario.peso" />
						                                                            </td>
						                                                        </tr>
						                                                    </table>
					                                                    </div>
					                                                </td>
					                                                <td>
				                                                        <table cellpadding="0" cellspacing="0" >
				                                                            <tr>
				                                                                <td><input type='button' class ='ui-button' style="width: 120px" name='btnRemTodasOpcoes' value='<i18n:message key="questionario.removerTodos" />' onclick='removeTodasOpcoes()'/></td>
				                                                            </tr>
				                                                            <tr>
				        	                                                    <td><input type='button' class ='ui-button' style="width: 100px; display: none" name='btnRemOpcao' value='<i18n:message key="questionario.remover" />' onclick='removeOpcao()'/></td>
				                                                            </tr>
				                                                            <tr>
				                                                                <td><input type='button' class ='ui-button' style="width: 100px; display: none" name='btnMoveOpcaoAcima' value='<i18n:message key="questionario.moverAcima" />' onclick='moveOpcaoAcima(this)'/></td>
				                                                            </tr>
				                                                            <tr>
				                                                                <td><input type='button' class ='ui-button' style="width: 100px; display: none" name='btnMoveOpcaoAbaixo' value='<i18n:message key="questionario.moverAbaixo" />' onclick='moveOpcaoAbaixo(this)'/></td>
				                                                            </tr>
				                                                        </table>
					                                                </td>
				                                                </tr></table>
				                                                </fieldset>   
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <table width="100%">
				                                                        <tr>
				                                                            <td>
							                                                    <i18n:message key="questionario.opcaoPadrao" />:
							                                                </td>
							                                                <td>
							                                                    <select name='lstOpcoesDefault' id='lstOpcoesDefault' style='width: 500px'></select>
							                                                </td>  
				                                                        </tr>
				                                                    </table>
				                                                </td>                                     
				                                            </tr>                                   
				                                        </table>
				                                    </fieldset>
				                                    </div>
				                                    <div id='divFaixaNumeros' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                   <i18n:message key="questionario.faixaPermitida" />:
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='val1' size="15" class='Format[Numero]'/>
				                                                </td>
				                                                <td width="5%">
				                                                   <i18n:message key="questionario.ate" />
				                                                </td>                                       
				                                                <td>
				                                                    <input type='text' name='val2' size="15" class='Format[Numero]'/>
				                                                </td>                                                                               
				                                            </tr>
				<!-- 
				                                            <tr>
				                                                <td>
				                                                    Texto:
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>                                                                               
				                                            </tr>           
				-->
				                                        </table>
				                                    </div>
				                                    <div id='divFaixaDecimais' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                    <i18n:message key="questionario.faixaPermitida" />:
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='valPerm1' size="15" class='Format[Moeda]'/>
				                                                </td>
				                                                <td width="5%">
				                                                     <i18n:message key="questionario.ate" />
				                                                </td>                                       
				                                                <td>
				                                                    <input type='text' name='valPerm2' size="15" class='Format[Moeda]'/>
				                                                </td>  
																<td>
				                                                    <font color='red'><i18n:message key="questionario.faixaValores" /></font>
				                                                </td> 
				                                            </tr>
				<!-- 
				                                            <tr>
				                                                <td>
				                                                    Texto:
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>                                                                               
				                                            </tr>   
				 -->                                                                            
				                                        </table>
				                                    </div>                          
				                                    <div id='divValoresReferenciaUN'>
				                                        <table width='100%'>
				                                            <tr>
				                                                <td width='180px'>
				                                                <i18n:message key="questionario.preenchimentoObrigatorio" />
				                                                </td>
				                                                <td>
				                                                    <input type='radio' name='obrigatoria' value='S'/><i18n:message key="citcorpore.comum.sim" />
				                                                    <input type='radio' name='obrigatoria' value='N' checked="checked"/><i18n:message key="citcorpore.comum.nao" />
				                                                </td>
				                                            </tr>                                       
				                                            <tr>
				                                                <td>
				                                                     <i18n:message key="unidade.unidade" />: 
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="unidade" id="unidade" size="50" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <i18n:message key="questionario.valorReferencia" />:
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="valoresReferencia" id="valoresReferencia" size="85" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                     <i18n:message key="cronograma.imprimir" />: 
				                                                </td>
				                                                <td>
				                                                    <input type='radio' name='imprime' value='S' checked="checked"/><i18n:message key="citcorpore.comum.sim" /> <input type='radio' name='imprime' value='N'/><i18n:message key="citcorpore.comum.nao" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <font color='red'><i18n:message key="questionario.recuperarValor" /></font> 
				                                                </td>
				                                                <td>
				                                                     <input type='radio' name='ultimoValor' value='N' checked="checked"/><i18n:message key="citcorpore.comum.nao" /> <input type='radio' name='ultimoValor' value='S'/><i18n:message key="questionario.atravesId" /> <input type='radio' name='ultimoValor' value='G'/><i18n:message key="questionario.atravesSigla" />
				                                                </td>
				                                            </tr> 
				                                        </table>
				                                    </div>
				                                    <div id='divCalculada' style='display:none'>
				                                        <table width='100%'>
				                                           <tr>
				                                               <td width='180px'>
				                                                   <i18n:message key="questionario.calculadaSistema" />
				                                               </td>
				                                               <td>
				                                                   <input type='radio' name='calculada' value='S' onclick='verificaTipoQuestao()'/><i18n:message key="citcorpore.comum.sim" /> <input type='radio' name='calculada' value='N' onclick='verificaTipoQuestao()' checked="checked"/><i18n:message key="citcorpore.comum.nao" />
				                                               </td>                                       
				                                           </tr>
				                                       </table>
				                                    </div>
				                                    <div id='divDesenho' style='display:none'>
				                                        <table width='100%'>
				                                           <tr>
				                                               <td width='180px'>
				                                                   <i18n:message key="questionario.utilizarDesenho" />:
				                                               </td>
				                                               <td>
																	<select name='idImagem' id='idImagem'>
																	</select>
				                                               </td>                                       
				                                           </tr>
				                                       </table>
				                                    </div>
				                                    <div id='divEditavel' style='display:none'>
				                                        <table width='100%'>
				                                           <tr>
				                                               <td width='180px'>
				                                                <i18n:message key="questionario.alteradoUsuario" />
				                                                   
				                                               </td>
				                                               <td>
				                                                   <input type='radio' name='editavel' value='S' /><i18n:message key="citcorpore.comum.sim" /> <input type='radio' name='editavel' value='N' checked="checked"/><i18n:message key="citcorpore.comum.nao" />
				                                               </td>                                       
				                                           </tr>
				                                       </table>
				                                    </div>
				                                    <div id='divSigla'>
				                                        <table width='100%'>
				                                            <tr>
				                                                <td width='180px'>
				                                                    <i18n:message key="questionario.sigla" />: 
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="sigla" id="sigla" size="60" />
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>                                  
				                                </div>
				                            </fieldset>
				                        </td>
				                    </tr>                               
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAddQuestao'  value='<i18n:message key="questionario.adicionar" />' onclick='adicionarQuestao()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAtuQuestao'  value='<i18n:message key="questionario.atualizar" />' onclick='atualizarQuestao()' style='display:none'/>
				                                    </td>                                   
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>           
				                </table>
				            </div>
				            <div id='divTextoFixo' style='display: none;'>
				                <table width="795px">
				                    <tr>
				                        <td>
				                            <textarea rows="20" class='ui-button' cols="100" name="textoFixo" id="textoFixo" style='width: 790px; height: 400px'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <input type='button' class='ui-button' name='btnAtuTextoFixo'  value='<i18n:message key="questionario.adicionar" />' onclick='adicionarTextoFixo()'/>
				                        </td>
				                    </tr>                   
				                </table>
				            </div>
				            <div id='divHTML' style='display: none;'>
				                <table width="795px">
				                    <tr>
				                        <td>
				                            <b>HTML/Java Script</b>
				                            <textarea rows="40" cols="100" name="textoHTML" id="textoHTML" style='width: 790px; height: 350px'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <input type='button' class='ui-button' name='btnAddHTML'  value='<i18n:message key="questionario.adicionar" />' onclick='adicionarHTML()'/>
				                            <input type='button' class='ui-button' name='btnAtuHTML'  value='<i18n:message key="questionario.atualizar" />' onclick='atualizarHTML()'/>
				                        </td>
				                    </tr>                   
				                </table>
				            </div>
				            </div>
				            <div id='divMatriz' style='display: none;'>
				                <fieldset>
					                <table border = "0" width="770px">
					                    <tr>
					                        <td style="5%">
					                            <i18n:message key="questionario.titulo" />:*
					                        </td>
					                        <td colspan="5">
					                            <textarea rows="5" cols="90" name="tituloQuestaoMatriz" id="tituloQuestaoMatriz"></textarea>
					                        </td>
					                    </tr>
					                </table>
				                </fieldset>
				                <fieldset>
				                    <table border = "0" cellpadding="0" cellspacing="0" border = '0'>
				                        <tr>
				                            <td>
				                                <table>
				                                    <tr>
							                            <td>
							                                <i18n:message key="questionario.imprime" />: 
							                            </td>
							                            <td>
							                                <input type='radio' name='imprimeMatriz' value='S' checked="checked"/><i18n:message key="citcorpore.comum.sim" /> <input type='radio' name='imprimeMatriz' value='N'/><i18n:message key="citcorpore.comum.nao" />
							                            </td>
							                            <td>
							                                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							                            </td>
				                                    </tr>
				                                </table>
				                            </td>
				                        </tr>
				                        <tr>
				                            <td>
				                                <table>
				                                    <tr>
							                            <td>
							                               <i18n:message key="questionario.recuperarValor" />
							                                
							                            </td>
							                            <td>
							                                <input type='radio' name='ultimoValorMatriz' value='N' checked="checked"/><i18n:message key="citcorpore.comum.nao" /> <input type='radio' name='ultimoValorMatriz' value='G'/><i18n:message key="citcorpore.comum.sim" />
							                            </td>
				                                    </tr>
				                                </table>
				                            </td>
				                        </tr> 
				                        <tr>
				                            <td>
				                                <table>
								                    <tr>
								                        <td >
								                        <i18n:message key="questionario.qtdLinhas" />:
								                        </td>
								                        <td>
								                            <input type='text' name="qtdeLinhas" id="qtdeLinhas" size="3" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
								                        <td >
								                     <i18n:message key="questionario.qtdColunas" />:
				:
								                        </td>
								                        <td>
								                            <input type='text' name="qtdeColunas" id="qtdeColunas" size="3" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
							                            <td>
							                                <i18n:message key="questionario.sigla" />: 
							                            </td>
							                            <td>
							                                <input type='text' name="siglaMatriz" id="siglaMatriz" size="50" />
							                            </td>
								                    </tr>
				                                </table>
				                            </td>
				                        </tr>
				                        <tr>
				                            <td>
				                                <table>
				                                    <tr>
								                        <td>
								                        <i18n:message key="questionario.cabecaolhoLinhas" />: 
								                        </td>
								                        <td>
								                            <input type='radio' name='cabecalhoLinhas' value='S' checked="checked"/><i18n:message key="citcorpore.comum.sim" /> <input type='radio' name='cabecalhoLinhas' value='N'/><i18n:message key="citcorpore.comum.nao" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
								                        <td>
								                            <i18n:message key="questionario.cabecaolhoColunas" />: 
								                        </td>
								                        <td>
								                            <input type='radio' name='cabecalhoColunas' value='S' checked="checked"/><i18n:message key="citcorpore.comum.sim" /> <input type='radio' name='cabecalhoColunas' value='N'/><i18n:message key="citcorpore.comum.nao" />
								                        </td>
				                                        <td>
				                                            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                                        </td>
				                                        <td>
								                            <input type='button' class='ui-button' name='btnMontaMatriz'  value='Montar linhas e colunas' onclick='montarMatriz()'/>
								                        </td>
				                                    </tr>        
				                                </table>
				                            </td>
				                        </tr> 
				                    </table>
				                </fieldset>
				                <fieldset>
				                    <legend><B><i18n:message key="questionario.questaoMatriz"/></B></legend>
				                    <div id='divQuestoesMatriz' style='width: 770px; height: 340px; overflow: auto;'>
				                    </div>
				                </fieldset>
				                <table>
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAddMatriz' value='<i18n:message key="questionario.adicionar" />' onclick='adicionarMatriz()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAtuMatriz'  value='<i18n:message key="questionario.atualizar" />' onclick='atualizarMatriz()' style='display:none'/>
				                                    </td>                                   
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                </table>           
				            </div>
				            <div id='divTabela' style='display: none; position: absolute;'>
				                <fieldset>
					                <table border = "0" width="780px">
					                    <tr>
					                        <td style="5%">
					                            <i18n:message key="questionario.titulo" />:*
					                        </td>
					                        <td colspan="5">
					                            <textarea rows="5" cols="90" name="tituloQuestaoTabela" id="tituloQuestaoTabela"></textarea>
					                        </td>
					                    </tr>
				                    </table>
				                </fieldset>
				                <fieldset>
				                    <table border = "0">
				                        <tr>
				                            <td>
				                                <i18n:message key="questionario.imprime" />: 
				                            </td>
				                            <td>
				                                <input type='radio' name='imprimeTabela' value='S' checked="checked"/><i18n:message key="citcorpore.comum.sim" /> <input type='radio' name='imprimeTabela' value='N'/><i18n:message key="citcorpore.comum.nao" />
				                            </td>
				                        </tr>
				                        <tr>
				                            <td colspan="5" cellpadding="0" cellspacing="0" border = '0'>
				                                <table>
				                                    <tr>
				                                        <td>
				                                           <i18n:message key="questionario.recuperarValor" />
				                                        </td>
				                                        <td>
				                                            <input type='radio' name='ultimoValorTabela' value='N' checked="checked"/><i18n:message key="citcorpore.comum.nao" /> <input type='radio' name='ultimoValorTabela' value='G'/><i18n:message key="citcorpore.comum.sim" />
				                                        </td>
				                                    </tr>
				                                </table>
				                            </td>
				                        </tr> 
				                    </table>
				                    <table width="100%" border = "0">
				                        <tr>
					                        <td style='text-align: right;' width="20%" >
					                            <i18n:message key="questionario.qtdColunas" />:
					                        </td>
					                        <td width="10%">
					                            <input type='text' name="qtdeColunasTabela" id="qtdeColunasTabela" size="3" />
					                        </td>
				                            <td>
				                                <i18n:message key="questionario.sigla" />: 
				                            </td>
				                            <td>
				                                <input type='text' name="siglaTabela" id="siglaTabela" size="60" />
				                            </td>
					                        <td>
					                            <input type='button' class='ui-button' name='btnMontaTabela'  value='<i18n:message key="questionario.montarColunas" />' onclick='montarTabela(true)'/>
					                        </td>
				                        </tr>        
				                    </table>
				                </fieldset>
				                <fieldset>
				                    <legend><B><i18n:message key="questionario.questoesTabela" /></B></legend>
				                    <div id='divQuestoesTabela' style='width: 750px; height: 340px; overflow: auto;'>
				                    </div>
				                </fieldset>
				                <table>
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAddTabela' value='<i18n:message key="questionario.adicionar" />' onclick='adicionarTabela()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class='ui-button' name='btnAtuTabela' value='<i18n:message key="questionario.adicionar" />' onclick='atualizarTabela()' style='display:none'/>
				                                    </td>                                   
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>
				                </table>           
				            </div>
				    </form>
				</div>
			</div>
		</div>
	</div>
</div>

<div id="POPUP_TEXTO" title='<i18n:message key="questionario.textoInicialQuestao" />'>
   <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
				    <form name='formTextoInicial'>
				        <table width="750px">
				            <tr>
				                <td>
				                
				                   <i18n:message key="questionario.textoInicialQuestao" />:
				                </td>
				            </tr>
				            <tr>
				                <td>  
				                    <textarea rows="20" cols="100" name="textoInicialQuestaoQuestionario" id="textoInicialQuestaoQuestionario" style='width: 600px; height: 300px'></textarea>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                    <table>
				                        <tr>
				                            <td>
				                                <input type='button' class='ui-button' name='btnOKTexto' value='OK' onclick='addTextoInicioQuestao()'/>
				                            </td>
				                            <td>
				                                <input type='button' class='ui-button' name='btnFecharTexto' value=' <i18n:message key="questionario.fechar" />' onclick='fecharPopUPTexto()'/>
				                            </td>                           
				                        </tr>
				                    </table>
				                </td>
				            </tr>                       
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div>    
</div>

<div  id="POPUP_GRUPO_COPIAR" title='<i18n:message key="questionario.copiarGrupoQuestionario" />'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">    
				    <form name='formCopiarGrupo' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'/>
				        <input type='hidden' name='idQuestionarioCopiar'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><i18n:message key="questionario.atencaoQuestionario" />:</font>
				                </td>
				                <td>
				                    <font color='red'><i18n:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>       
				            <tr>
				                <td>
				                <i18n:message key="questionario.questionario" />:
				                </td>
				                <td>
				                    <select name='idQuestionario' id='idQuestionarioCopiarGrupo' onchange='document.formCopiarGrupo.fireEvent("listarGruposQuestionarios")'>
				                    </select>
				                </td>
				            </tr>       
				            <tr>
				                <td>
				                 <i18n:message key="questionario.grupo" />:
				                </td>
				                <td>
				                    <select name='idGrupoQuestionario' id='idGrupoQuestionarioCopiarGrupo'>
				                    </select>
				                </td>
				            </tr>
				            <tr>
				                <td>
				                <i18n:message key="questionario.nomeNovoGrupo" />:
				                  
				                </td>
				                <td>
				                    <input type='text' name='nomeGrupoQuestionario' size="80" maxlength="80"/>
				                </td>
				            </tr>           
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnCopiarGrupo'  value='<i18n:message key="questionario.copiar" />' onclick='copiarGrupo()'/>
				                     <input type='button' class='ui-button' name='btnFecharGrupo'  value='<i18n:message key="questionario.fechar" />' onclick='fecharPopUPGrupoCopiar()'/>
				                </td>
				            </tr>           
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div> 				    
</div>

<div id="POPUP_GRUPO_ORDENAR_GRUPO" title='<i18n:message key="questionario.ordenarGrupos" />'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">     
				    <form name='formOrdenar' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><i18n:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><i18n:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                <i18n:message key="questionario.grupos" />:
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesOrdenarGrupo' style='width:790px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblOrdenarGrupo' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesOrdenarAtualizarGrupo' style='display:none'>
				                        <i18n:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>           
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarOrdGrupo'  value='<i18n:message key="questionario.salvar" />' onclick='gravarGruposQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarOrdGrupo' value='<i18n:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>           
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div> 				    
</div>

<div id="POPUP_GRUPO_EXCLUIR_GRUPO" title='<i18n:message key="questionario.excluirGrupos" />'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">    
				    <form name='formExcluirGrupo' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/questionario/questionario'/>
				        <table>
				            <tr>
				                <td>
				                    <font color='red'><b><i18n:message key="questionario.atencaoQuestionario" />:</b></font>
				                </td>
				                <td>
				                    <font color='red'><i18n:message key="questionario.salveAntesIniciar" /></font>
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                   <i18n:message key="questionario.grupos" />:
				                </td>
				            </tr>       
				            <tr>
				                <td colspan="2">
				                    <div id='divQuestoesExcluirGrupo' style='width:790px;height:300px; border:1px solid black; background-color: white; overflow: auto'>
				                        <table id='tblExcluirGrupo' width="100%">
				                            <tr>
				                                <td>
				                                </td>
				                                <td>
				                                </td>
				                            </tr>
				                        </table>
				                    </div>
				                    <div id='divQuestoesExcluirAtualizarGrupo' style='display:none'>
				                        <i18n:message key="citcorpore.comum.aguardecarregando"/>
				                    </div>
				                </td>
				            </tr>           
				            <tr>
				                <td>
				                    <input type='button' class='ui-button' name='btnSalvarOrdGrupo'   value='<i18n:message key="questionario.salvar" />' onclick='gravarQuestionario()'/>
				                </td>
				                <td>
				                    <input type='button' class='ui-button' name='btnCancelarOrdGrupo'  value='<i18n:message key="questionario.cancelar" />' onclick='abrirQuestPosOrder()'/>
				                </td>
				            </tr>           
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div> 				    
</div>

<div id="POPUP_QUESTAO_AGRUPADA" title='<i18n:message key="questionario.configuracaoMatrizTabela"/>'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">     
				    <form name='formQuestaoAgrupada'>
				        <input type='hidden' name='idQuestaoQuestionario' />
				        <div>
				            <table>
				                <tr>
				                    <td>
				                         <label><i18n:message key="questionario.informeElemento" /></label>
				                    </td>
				                    <td>
				                        <input type='radio' name='tipo' value='Q' onclick='verificaTipoAgrupada()'/><i18n:message key="questionario.questoes" /><input type='radio' name='tipo' value='T' onclick='verificaTipoAgrupada()'/><span id='lblTextoFixo' name='lblTextoFixo'>Texto Fixo</span>  
				                    </td>
				                </tr>
				            </table>
				            <div id='divQuestaoAgrupada' style='display: none'>
				                <table border="0" width="780px">  
				                    <tr>                        
				                        <td colspan="2">
				                            <div id='divTextoInicialQuestaoAgrupada' style='displauy:none'></div>
				                        </td>
				                    </tr>       
				                    <tr>
				                        <td style="5%">
				                            <label><i18n:message key="questionario.tituloQuestao" /></label>
				                        </td>
				                        <td>
				                            <textarea rows="5" cols="90" name="tituloQuestaoQuestionarioAgrupada" id="tituloQuestaoQuestionarioAgrupada" style='width: 100%'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <label><i18n:message key="questionario.tipoQuestao" /></label>
				                        </td>
				                        <td>
				                            <table cellpadding="0" cellspacing="0">
				                                <tr>
				                                    <td>
				                                        <select name='tipoQuestao' onchange='verificaTipoQuestaoAgrupada()'>
				                                            <option value=''><i18n:message key="citcorpore.comum.selecione" /></option>
				                                            <option value='T'><i18n:message key="questionario.textoCurto" /></option>
				                                            <option value='A'><i18n:message key="questionario.textoLongoObs" /></option>
				                                            <option value='L'><i18n:message key="questionario.textoLongoEditor" /></option>
				                                            <option value='R'><i18n:message key="questionario.radioTxt" /></option>
				                                            <option value='C'><i18n:message key="questionario.checkBoxTxt" /></option>
				                                            <option value='X'><i18n:message key="questionario.comboBoxTxt" /></option>
				                                            <option value='N'><i18n:message key="questionario.numeroTxt" /></option>
				                                            <option value='V'><i18n:message key="questionario.valorDecimal" /></option>
				                                            <option value='%'><i18n:message key="questionario.percentualTxt" /></option>
				                                            <option value='*'><i18n:message key="questionario.percentualValorTxt" /></option>
				                                            <option value='1'><i18n:message key="questionario.faixaValoresNumeros" /></option>
				                                            <option value='2'><i18n:message key="questionario.faixaValoresDecimais" /></option>
				                                            <option value='D'><i18n:message key="citcorpore.comum.data" /></option>
				                                            <option value='H'><i18n:message key="citcorpore.texto.tempo.hora" /></option>
				                                            <option value='G'><i18n:message key="questionario.mesAno" /></option>                                           
				                                            <option value='M'><i18n:message key="questionario.galeriaMultimidia" /></option>
				                                            <option value='8'><i18n:message key="visao.listagem" /></option>
				                                        </select>
				                                    </td>
				                                    <td>
				                                        &nbsp;
				                                    </td>
				                                    <td>
				                                        <div id='divTamanhoAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><i18n:message key="questionario.tamanho" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='tamanho' size="10" maxlength="10"/>
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divDecimaisAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><i18n:message key="questionario.decimais" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <input type='text' name='decimais' size="3" maxlength="3"/>
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>   
				                                    <td>
				                                        <div id='divPonderadaAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><i18n:message key="questionario.ponderada" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <input type='radio' name='ponderada' value='S' onclick='verificaTipoQuestaoAgrupada()'/><label><i18n:message key="citcorpore.comum.sim" /></label>
				                                                        <input type='radio' name='ponderada' value='N' onclick='verificaTipoQuestaoAgrupada()' checked="checked"/><label><i18n:message key="citcorpore.comum.nao" /></label>
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>
				                                    <td>
				                                        <div id='divListagemAgrupada' style='display:none'>
				                                            <table cellpadding="0" cellspacing="0">
				                                                <tr>
				                                                    <td>
				                                                        <label><i18n:message key="questionario.tipoDeListagem" /></label>
				                                                    </td>
				                                                    <td>
				                                                        <select name='nomeListagemAgrupada'></select>
				                                                    </td>                                       
				                                                </tr>
				                                            </table>
				                                        </div>
				                                    </td>                                                   
				                                </tr>
				                            </table>
				                        </td>
				                    </tr> 
				                    <tr>
				                        <td colspan="2">
				                            <table border = "0">
				                                <tr>
				                                     <td>
				                                         <label><i18n:message key="questionario.ondeAparecer" /></label>
				                                     </td>
				                                     <td>
				                                         <input type='radio' value='L' name='infoResposta' checked="checked"/><label><i18n:message key="questionario.ladoPergunto" /></label>
				                                     </td>
				                                     <td>
				                                         <input type='radio' value='B' name='infoResposta'/><label><i18n:message key="questionario.baixoPergunta" /></label>
				                                     </td>
				                                </tr>                                   
				                            </table>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td colspan="2">
				                            <fieldset>
				                                <legend><b><i18n:message key="questionario.informacoesEspecificasQuestao" /></b></legend>
				                                <div style='height: 320px; overflow: auto'>
				                                    <div id='divCheckboxRadioComboAgrupada' style='display:none'>
				                                        <table border="0">
				                                            <tr>
				                                                <td>
				                                                    <label><i18n:message key="questionario.opcao" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name='txtOpcao' size="70"/>
				                                                </td>
				                                                <td>
					                                                <div id='divPesoAgrupada' style='display:none'>
					                                                    <table cellpadding="0" cellspacing="0">
					                                                        <tr>
					                                                            <td>
					                                                                <label><i18n:message key="questionario.peso" /></label>
					                                                            </td>
					                                                            <td>
					                                                                <input type='text' name='peso' size="2" maxlength="3"/>
					                                                            </td>                                       
					                                                        </tr>
					                                                    </table>
					                                                </div>
				                                                </td>                         
				                                                <td>
				                                                    <input type='button' class='ui-button' name='btnAddOpcao' id='btnAddOpcaoAgrupada' value='<i18n:message key="citcorpore.comum.adicionar" />' onclick="adicionarOpcaoQuestaoAgrupada()"/>
				                                                </td>                                                       
				                                            </tr>
				                                            <tr>
				                                                <td colspan="2">
				                                                    <select name='lstOpcoesAgrupada' id='lstOpcoesAgrupada' size="7" style='width: 600px'></select>
				                                                </td>
				                                                <td colspan="3">
				                                                    <input type='button' class='ui-button' name='btnRemOpcao' value='<i18n:message key="questionario.remover" />' onclick='removeOpcaoAgrupada()'/>
				                                                    <br/>
				                                                    <input type='button' class='ui-button' name='btnRemOpcao' value='<i18n:message key="questionario.rmTodos" />' onclick='removeTodasOpcoesAgrupada()'/>
				                                                </td>                                       
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <label><i18n:message key="questionario.opcaoPadrao" /></label>
				                                                </td>
				                                                <td colspan="4">
				                                                    <select name='lstOpcoesDefaultAgrupada' id='lstOpcoesDefaultAgrupada' style='width: 500px'></select>
				                                                </td>                                       
				                                            </tr>                                   
				                                        </table>
				                                    </div>
				                                    <div id='divFaixaNumerosAgrupada' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                    <label><i18n:message key="questionario.faixaPermitida" /></label>
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='val1' size="15"/>
				                                                </td>
				                                                <td width="5%">
				                                                    <label><i18n:message key="citcorpore.comum.a" /></label>
				                                                </td>                                       
				                                                <td>
				                                                    <input type='text' name='val2' size="15"/>
				                                                </td>                                                                               
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <label><i18n:message key="questionario.texto" /></label>
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>                                                                               
				                                            </tr>                                                                       
				                                        </table>
				                                    </div>
				                                    <div id='divFaixaDecimaisAgrupada' style='display:none'>
				                                        <table>
				                                            <tr>
				                                                <td width="20%">
				                                                    <label><i18n:message key="questionario.faixaPermitida" /></label>
				                                                </td>
				                                                <td width="17%">
				                                                    <input type='text' name='valPerm1' size="15" class='Format[Moeda]'/>
				                                                </td>
				                                                <td width="5%">
				                                                    <label><i18n:message key="citcorpore.comum.a" /></label>
				                                                </td>                                       
				                                                <td>
				                                                    <input type='text' name='valPerm2' size="15" class='Format[Moeda]'/>
				                                                </td>  
																<td>
				                                                    <font color='red'><i18n:message key="questionario.avisoNaoExisteFaixaValores" /></font>
				                                                </td> 
				                                            </tr>
				<!-- 
				                                            <tr>
				                                                <td>
				                                                    Texto:
				                                                </td>
				                                                <td colspan="4">
				                                                    <div style='width: 600px; height: 100px; border: 1px solid black; background-color: white'></div>
				                                                </td>                                                                               
				                                            </tr>   
				-->                                                                            
				                                        </table>
				                                    </div>                          
				                                    <div id='divValoresReferenciaUNAgrupada'>
				                                        <table>
				                                            <tr>
				                                                <td>
				                                                    <label><i18n:message key="questionario.preenchimentoObrigatorio" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='radio' name='obrigatoria' value='S'/><label><i18n:message key="citcorpore.comum.sim" /></label>
				                                                    <input type='radio' name='obrigatoria' value='N' checked="checked"/><label><i18n:message key="citcorpore.comum.nao" /></label>
				                                                </td>
				                                            </tr>                                       
				                                            <tr>
				                                                <td>
				                                                    <label><i18n:message key="unidade.unidade" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="unidade" id="unidade" size="50" />
				                                                </td>
				                                            </tr>
				                                            <tr>
				                                                <td>
				                                                    <label><i18n:message key="questionario.valorReferencia" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="valoresReferencia" id="valoresReferencia" size="90" />
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>    
				                                    <div id='divSiglaAgrupada'>
				                                        <table width='100%'>
				                                            <tr>
				                                                <td width='180px'>
				                                                	<label><i18n:message key="questionario.sigla" /></label>
				                                                </td>
				                                                <td>
				                                                    <input type='text' name="sigla" id="sigla" size="60" />
				                                                </td>
				                                            </tr>
				                                        </table>
				                                    </div>                                  
				                                </div>
				                            </fieldset>
				                        </td>
				                    </tr>                               
				                    <tr>
				                        <td>
				                            <table>
				                                <tr>
				                                    <td>
				                                        <input type='button' class ='ui-button' name='btnAddQuestaoAgrupada'  value='<i18n:message key="citcorpore.comum.adicionar" />' onclick='atualizarQuestaoAgrupada()'/>
				                                    </td>
				                                    <td>
				                                        <input type='button' class ='ui-button' name='btnAtuQuestaoAgrupada'  value='<i18n:message key="citcorpore.comum.atualizar" />' onclick='atualizarQuestaoAgrupada()' style='display:none'/>
				                                    </td>                                   
				                                </tr>
				                            </table>
				                        </td>
				                    </tr>           
				                </table>
				            </div>
				            <div id='divTextoFixoAgrupada' style='display: none; width: 780px'>
				                <table width="100%">
				                    <tr>
				                        <td>
				                            <textarea rows="20" cols="100" name="textoFixoAgrupada" id="textoFixoAgrupada" style='width: 770px; height: 400px'></textarea>
				                        </td>
				                    </tr>
				                    <tr>
				                        <td>
				                            <input type='button' class ='ui-button' name='btnAtuTextoFixoAgrupada'  value='<i18n:message key="citcorpore.comum.adicionar" />' onclick='atualizarTextoFixoAgrupada()'/>
				                        </td>
				                    </tr>                   
				                </table>
				            </div>
				        </div>
				    </form>
				</div>
			</div>
		</div>
	</div> 				    
</div>

<div id="POPUP_COPIAR_QUESTAO" title='<i18n:message key="questionario.selecioneConfigurar"/>'>
     <div class="box grid_16 tabs">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">     
				    <form name='formCopiarQuestao'>
				        <table width="295">
				            <tr>
				                <td>
				                   <select name='lstQuestoes' id='lstQuestoes' multiple="multiple" size="7" style='width: 250px'></select>
				                </td>
				                <td>
				                    <input type='button' class ='ui-button' value='OK' onclick='copiarQuestaoFunction()' />
				                </td>
				            </tr>
				        </table>
				    </form>
				</div>
			</div>
		</div>
	</div> 					    
</div>
</html>
                            