<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoRequisicaoLiberacao"%>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho"%>
<%
    response.setCharacterEncoding("ISO-8859-1");
    response.setHeader( "Cache-Control", "no-cache");
    response.setHeader( "Pragma", "no-cache");
    response.setDateHeader ( "Expires", -1);

    String PAGE_CADASTRO = "/pages/requisicaoLiberacao/requisicaoLiberacao.load";
    String login = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null)
		login = usuario.getLogin();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
	<%@include file="/include/security/security.jsp" %>
    <%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
<link href="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

    <script>var URL_INITIAL = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/';</script>
      
    <title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>	
	
    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/layout-default-latest.css"/>
    <!-- theme is last so will override defaults --->
    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.all.css"/>                  
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/themeroller/Aristo.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/main.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/theme_base.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/buttons.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/ie.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jqueryTreeview/jquery.treeview.css"/>
	<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jQueryGantt/css/style.css" />
    <link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/slick.grid.css"/>	
	<link type="text/css" rel="stylesheet" class="include" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/jquery.jqplot.min.css" />
    <style title="" type="text/css">
     .wrapper, #nav , .ui-layout-center{
    border-left: 1px solid #D8D8D8 !important;
    border-right: 1px solid #D8D8D8 !important;
    margin: 0 auto !important;
    width: 94% !important;
	}
	.ui-layout-south {
	border-top: none !important;
	width: 94% !important;
	margin-left: auto !important;
	margin-right: auto !important;
	padding: 0 !important;
	border-color: #D8D8D8 !important;
	}
	.navbar.main{
	border-bottom: none !important;
	}
	
    .ui-layout-center ,
    .ui-layout-east ,
    .ui-layout-east .ui-layout-content {
        padding:        0;
        overflow:       hidden;
    }
    .hidden {
        display:        none;
    }
    h2.loading {
        border:         0;
        font-size:      24px;
        font-weight:    normal;
        margin:         30% 0 0 40%;
    }
    .cell-title {      
        font-weight: bold;    
    }    
    .cell-effort-driven {      
        text-align: center;    
    } 
    .box{
    float: none;
    }
    
    .ui-layout-resizer{
    top: 118px !important;
    }
    .ui-widget-header{
    background-color: #F9F9F9 !important;
    background-image: linear-gradient(to bottom, #FDFDFD, #F4F4F4) !important;
    background-repeat: repeat-x !important;
    border: 1px solid #D8D8D8 !important;
    box-shadow: 0 1px 0 0 #F7F7F7, 0 5px 4px -4px #D8D8D8 !important;
    margin: 15px !important;
    overflow: hidden !important;
    position: relative !important;
    margin-bottom:0 !important;
    }
    .ui-state-default, .ui-widget-content .ui-state-default, .ui-widget-header .ui-state-default{
    background-color: #F9F9F9 !important;
    background-image: linear-gradient(to bottom, #FDFDFD, #F4F4F4) !important;
    background-repeat: repeat-x !important;
    }
    .ui-layout-toggler{
    background: transparent !important;
    border:none;
    }
.ui-layout-center{
	top:118px !important;
}
.ui-layout-resizer{
	background: transparent !important;
	height: 5px!important;
	}
.ui-layout-south {
	top:125px !important;
	height:auto !important;
}

input#idRequisicaoSel{
	border: 1px solid #CCCCCC !important;
    padding: 0.4em !important;
    } 
iframe{
border: none !important;
}
#divIntLOOKUP_ITEMCONFIGURACAO {
	margin-left: 1em !important;
	}
.T-I-ax7{
		margin-top:10px;
		}  
		.toggler-west-closed		{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-off.gif) no-repeat center; }
		.toggler-west-closed:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-on.gif)  no-repeat center; }
		.toggler-east-closed		{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-off.gif) no-repeat center; }
		.toggler-east-closed:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-on.gif)  no-repeat center; }    
		
		span.button-close-west			{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-off.gif) no-repeat center; }
		span.button-close-west:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-lt-on.gif)  no-repeat center; }
		span.button-close-east			{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-off.gif) no-repeat center; }
		span.button-close-east:hover	{ background: url(<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/go-rt-on.gif)  no-repeat center; }
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
		
		/* CSS Barra de pesquisa e botões */
	.T-I {
		-webkit-border-radius: 3px;
		-moz-border-radius: 3px;
		border-radius: 3px;
		cursor: default;
		font-size: 11px;
		font-weight: bold;
		text-align: center;
		white-space: nowrap;
		/* margin-right: 16px; */
		height: 27px;
		line-height: 27px;
		min-width: 54px;
		outline: 0;
		padding: 0 8px !important;
		text-shadow: none !important;
	}
	.J-J5-Ji { 	position: relative;	display: inline-block;}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 {
		background-color: transparent;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -webkit-gradient(linear, left top, left bottom, from(whiteSmoke),	to(#F1F1F1) );
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
	}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 { 
		background-color: transparent;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1); 
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -webkit-gradient(linear, left top, left bottom, from(whiteSmoke),	to(#F1F1F1) );
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
	}
	.TI .T-I-ax7:focus,.z0 .T-I-ax7:focus,.G-atb .T-I-ax7:focus { border: 1px solid #4D90FE !important; }
	.asa { display: inline-block; }
	.ask { width: 1px; margin-right: -1px; }
	.J-J5-Ji { position: relative;	display: inline-block;	}
	.T-I-ax7 .T-I-J3 {	opacity: .55; }
	.T-I .T-I-J3 { margin-top: -3px; vertical-align: middle; }
	.asf {	width: 21px; height: 21px;	}
	#botao_voltar a{
	background: url('/citsmart/imagens/btn-voltar.png') no-repeat;
	height:15px;
	top: 115px;
    left: 50%;
    margin: auto;
    width:70px;
    position: fixed;
    z-index: 601;
    color:#fff;
    padding: 0 0.5em 0.5em 1.8em;
	font-weight: bold;
	}
	.T-I-ax7 {
		background-color: whiteSmoke !important;
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1)!important;
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1) !important;
		color: #000;
		box-shadow: 0 !important;
		border: 1px solid gainsboro !important;
		border: 1px solid rgba(0, 0, 0, 0.1) !important;
	}
	.asf { background: url(../../imagens/sprite_black2.png) -63px -21px no-repeat; }
	.asb { background-image: url('../../imagens/k1_a31af7ac.png');	background-size: 294px 45px; }
	.gbqfi { background-position: -33px 0;	display: inline-block !important; height: 13px; margin: 7px 19px !important; width: 14px; }
	.gbqfb { background-color: hsl(217, 99%, 65%) !important;
		background-image: -webkit-gradient(linear, left top, left bottom, from(hsl(217, 99%, 65%)), to(hsl(217, 82%, 60%) ) ) !important;
		background-image: -webkit-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		background-image: -moz-linear-gradient(top, hsl(217, 99%, 65%), hsl(217, 82%, 60%) ) !important;
		background-image: -ms-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		background-image: -o-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		background-image: linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 82%, 60%) ) !important;
		filter: progid:DXImageTransform.Microsoft.gradient(startColorStr='#4d90fe', EndColorStr='#4787ed' ) !important;
		border: 1px solid hsl(217, 84%, 56%) !important;
		color: white !important;
		margin: 0 0 !important;
	}
	
	.gbqfb:hover {
		background-color: hsl(217, 80%, 56%) !important;
		background-image: -webkit-gradient(linear, left top, left bottom, from(hsl(217, 99%, 65%)), to(hsl(217, 80%, 56%) ) ) !important;
		background-image: -webkit-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: -moz-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: -ms-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: -o-linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
		background-image: linear-gradient(top, hsl(217, 99%, 65%),	hsl(217, 80%, 56%) ) !important;
	}
	.gbqfb:focus { border-color: hsl(221, 59%, 45%) !important; }
	.asf,.ar8,.asl,.ar9,.ase { 	width: 21px; height: 21px; }
	.T-I-ax7:focus,.z0 .T-I-ax7:focus,.G-atb .T-I-ax7:focus { border: 1px solid #4D90FE; }
	.T-I-hvr:hover { background-color: #F8F8F8 !important;
		background-image: -webkit-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: -moz-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: -ms-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: -o-linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		background-image: linear-gradient(top, #F8F8F8, #F1F1F1) !important;
		border: 1px solid #C6C6C6 !important;
		color: #333 !important;
	}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 {
		background-color: transparent;
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -webkit-gradient(linear, left top, left bottom, from(whiteSmoke), to(#F1F1F1) );
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
	}
	.TI .T-I-ax7,.z0 .T-I-ax7,.G-atb .T-I-ax7 { border: 1px solid rgba(0, 0, 0, 0.1); color: #000 !important; }
	.T-I-ax7.T-I-Zf-aw2 { border: 1px solid gainsboro; }
	.T-I-ax7:focus { border: 1px solid #4D90FE !important; color: #000 !important; }
	.T-I-ax7 { background-color: whiteSmoke;
		background-image: -webkit-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -moz-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -ms-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: -o-linear-gradient(top, whiteSmoke, #F1F1F1);
		background-image: linear-gradient(top, whiteSmoke, #F1F1F1);
		color: #000;
		border: 1px solid gainsboro;
		border: 1px solid rgba(0, 0, 0, 0.1);
	} 
	.J-J5-Ji { position: relative; display: inline-block; }
	.J-J5-Ji { position: relative; display: -moz-inline-box; display: inline-block; }
	.G-asx { background: url(../../imagens/sprite2.png) no-repeat -160px -80px; width: 12px; height: 12px; margin-left: 3px; }
	.G-asx2 { background: url(../../imagens/sprite3.png) no-repeat -84px 50%; width: 7px; height: 10px; }
	.space { padding: 5px 5px; } 
		
    </style>

    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-ui-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/debug.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/uniform/jquery.uniform.min.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/autogrow/jquery.autogrowtextarea.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/multiselect/js/ui.multiselect.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/selectbox/jquery.selectBox.min.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/timepicker/jquery.timepicker.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/colorpicker/js/colorpicker.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/tiptip/jquery.tipTip.minified.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/validation/jquery.validate.min.js"></script>		
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/uitotop/js/jquery.ui.totop.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/custom/ui.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/custom/forms.js"></script>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<script class="include" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/jquery.jqplot.min.js"></script>
	<script class="include" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>	
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.rule-1.0.1.1-min.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.event.drag.custom.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.core.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.editors.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.grid.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
    
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/highcharts.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/exporting.js"></script>
    
    
    <script type="text/javascript">
    $(document).ready(function() 
    		{
    	$('li.dropdown').click(function() {
    		if ($(this).is('.open')) {
    		$(this).removeClass('open');
    		} else {
    		$(this).addClass('open');
    		}
    	}); 
    }); 
    // Fim da Função para funcionar botões de idioma e logout
    var popup;
	popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	popup2 = new PopupManager(1084, 1084, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	popup2.titulo = "<i18n:message key='citcorpore.comum.pesquisarapida' />";
	
      function GrupoQtde(){
      		this.id = '';
      		this.qtde = 0;
      }
      
      abrePopupPesquisa = function( ) {
  		
  		$( "#popupCadastroRapido" ).dialog({
  			title: '<i18n:message key="citcorpore.comum.cadastrorapido" />',
  			width: 1230,
  			height: 600,
  			modal: true,
  			autoOpen: false,
  			resizable: false,
  			show: "fade",
  			hide: "fade"
  			
  			}); 
  		
  		
  		document.getElementById('popupCadastroRapido').style.overflow = "hidden";
  		document.getElementById('tdAvisosSol').innerHTML = '';
  		
  		
  		popup2.abrePopup('pesquisaRequisicaoLiberacao','onClosePopUp');
  	}
            
	  AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null && solicitacaoDto.status == "<%=SituacaoRequisicaoLiberacao.Suspensa%>") 
	  		return value;

		var str = "<table cellpadding='0' cellspacing='0'><tr>";
	  	if (tarefaDto.executar == 'S') { 
			var idResponsavel = Number(tarefaDto.idResponsavelAtual);
			var validaResp =<%=usuario.getIdUsuario()%>;
		  	  if (idResponsavel != validaResp) {
		  		str += '<td>';
		  		/*  str += "<img src='" + URL_INITIAL + "imagens/pegar.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.capturartarefa' />' onclick='capturarTarefa(\""+tarefaDto.responsavelAtual+"\","+tarefaDto.idItemTrabalho+"\,"+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+")'>&nbsp;"; */
		  			str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='<i18n:message key='gerenciaservico.capturartarefa' />' onclick='capturarTarefa(\""+tarefaDto.responsavelAtual+"\","+tarefaDto.idItemTrabalho+"\,"+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+")'><i18n:message key='citcorpore.comum.capturar' /></span>";
		  		str += '</td>';
				 }
		     
		      /* str += "<img src='" + URL_INITIAL + "imagens/executarTarefa.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.iniciarexecutartarefa' />' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+",\"E\")'>&nbsp;"; */
		  	str += '<td>';
		  		str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='<i18n:message key='gerenciaservico.iniciarexecutartarefa' />' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+",\"E\")'><i18n:message key='citcorpore.comum.executar' /></span>";
		  	str += '</td>';
				}
	  	str += '<td>' + value + '</td>';
	 	str += '</tr></table>';
	      
        	return str ;	  
	  };

	  AddLinkRequisicao = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null) {
	  	  var str = "";
	  	/*   str += "<img src='" + URL_INITIAL + "imagens/viewCadastro.png' style='cursor:pointer;' title='<i18n:message key='requisicaoLiberacao.visualizarCadastroLiberacao' />' onclick='visualizarSolicitacao("+solicitacaoDto.idRequisicaoLiberacao+")'>"; */
	  	str += "<span style='cursor:pointer;margin-top: 5px!important;' class='btn btn-mini btn-primary titulo' title='<i18n:message key='requisicaoMudanca.visualizarCadastroMudanca' />' onclick='visualizarSolicitacao("+solicitacaoDto.idRequisicaoLiberacao+")'><i18n:message key='citcorpore.comum.visualizar' /></span>";
	  	  str += "&nbsp;&nbsp;"+solicitacaoDto.idRequisicaoLiberacao;
          return str;
	    }else
	      return "";	  
	  };
	  
	  addLinkAprovarLiberacao = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  		var dataHora = "";
	  		datahora = i18n_message("gerenciaservico.delegarcompartilhartarefa");
	  		
	  		
	  		if (tarefaDto.delegar == 'S') { 
	  		//if(solicitacaoDto.autorizadoLiberar != null && solicitacaoDto.autorizadoLiberar != "" && solicitacaoDto.autorizadoLiberar == "S"){
			  	if (solicitacaoDto != null) {
			  		/* return "<img src='" + URL_INITIAL + "imagens/share.png' style='cursor:pointer;' title='<i18n:message key='requisicaoLiberacao.encaminhar' />' onclick='exibirDelegacaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+",\""+tarefaDto.elementoFluxoDto.documentacao+"\")'>&nbsp"+datahora; */
			  		return "<a href='#' class='btn-action glyphicons share btn-default titulo' title='<i18n:message key='requisicaoLiberacao.encaminhar' />' onclick='exibirDelegacaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoLiberacao+",\""+tarefaDto.elementoFluxoDto.documentacao+"\")'><i></i></a>&nbsp;"+datahora;
				  	}else
				      return "";
		    }else
		      return "";	  
		};
		  
	 function aprovarLiberacao(idRequisicao){
		 if (!confirm(i18n_message("requisicaoLiberacao.confirmaAprovacao"))) 
				return;
		 	JANELA_AGUARDE_MENU.show();
			document.form.idRequisicao.value = idRequisicao;
			document.form.fireEvent('aprovarLiberacao');
	 }
		 
		

	  AddSituacao = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	if (solicitacaoDto != null) {
			  	  var str = solicitacaoDto.descrSituacao;

			      if (solicitacaoDto.status != "<%=SituacaoRequisicaoLiberacao.Suspensa%>" && tarefaDto.suspender == 'S') 
				  	 	/* str = "<img src='" + URL_INITIAL + "imagens/stop.png' style='cursor:pointer;' title='<i18n:message key='requisicaoLiberacao.suspenderLiberacao' />' onclick='prepararSuspensao("+solicitacaoDto.idRequisicaoLiberacao+")'>&nbsp;" + str; */
			  	 		  str = "<a href='#' class='btn-action glyphicons pause btn-default titulo' title='<i18n:message key='requisicaoMudanca.suspenderMudanca' />' onclick='prepararSuspensao("+solicitacaoDto.idRequisicaoLiberacao+")'><i></i></a> " + str;
				  if (solicitacaoDto.status == "<%=SituacaoRequisicaoLiberacao.Suspensa%>" && tarefaDto.reativar == 'S') 
			  	  		 /* str = "<img src='" + URL_INITIAL + "imagens/play.png' style='cursor:pointer;' title='<i18n:message key='requisicaoLiberacao.reativarLiberacao' />' onclick='reativarSolicitacao("+solicitacaoDto.idRequisicaoLiberacao+")'>&nbsp;" + str; */
			  	 		str = "<a href='#' class='btn-action glyphicons play btn-default titulo' title='<i18n:message key='requisicaoMudanca.reativarMudanca' />' onclick='reativarSolicitacao("+solicitacaoDto.idRequisicaoLiberacao+")'><i></i></a> " + str;
			  	  		/*  str = " <img src='" + URL_INITIAL + "imagens/agenda.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.agendaratividade' />' onclick='agendaAtividade("+solicitacaoDto.idRequisicaoLiberacao+")'>" + str; */
			  	  		 str = "<a href='#' class='btn-action glyphicons book btn-default titulo' title='<i18n:message key='gerenciamentoMudanca.agendarMudanca' />' onclick='agendaAtividade("+solicitacaoDto.idRequisicaoLiberacao+")'><i></i></a> " + str;
		          return str;	  	  	 
		    }else
		      	return "";	  
	  };
 
	  AddAtraso = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	var result = "";
		  	if (solicitacaoDto != null && parseFloat(solicitacaoDto.atraso) > parseFloat("0,00") && solicitacaoDto.status != "<%=SituacaoRequisicaoLiberacao.Suspensa%>") 
		      		result = '<font color="red">' + solicitacaoDto.atrasoStr + '</font>';
	        return result;	  
	  };

	  AddSelTarefa = function(row, cell, value, columnDef, dataContext) {
      	  return "<input type='radio' name='selTarefa' value='S'/>";
	  };

	  AddImgPrioridade = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;
		  	if (solicitacaoDto.prioridade == '1'){
		    		return value + " <img src='" + URL_INITIAL + "imagens/b.gif' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.prioridadealta' />'/> ";
		  	}else{
		  			return value;
		  	}
	  };

	 	var	arrayTarefas   = []
	    ,   gridTarefa     = {}
	    ,   tarefas		   = []
	    ,   colunasTarefa  = [
					           	{ id: "idRequisicao"      	, name: "<i18n:message key='citcorpore.comum.numero' />"			, field: "idRequisicao"       		, width: 160,	formatter: AddLinkRequisicao, resizable:true	}
					        ,   { id: "solicitante"			, name: "<i18n:message key='solicitacaoServico.solicitante' />"		, field: "solicitante"	    		, width: 250, 	resizable:true   								}
					        ,   { id: "dataHoraSolicitacao"	, name: "<i18n:message key='solicitacaoServico.dataHoraCriacao' />"	, field: "dataHoraSolicitacao"		, width: 110  													}
					        ,   { id: "prioridade"			, name: "<i18n:message key='gerenciaservico.pri' />"				, field: "prioridade"	       		, width: 24,    formatter: AddImgPrioridade, resizable:true    	}
					        ,   { id: "dataHoraLimite"		, name: "<i18n:message key='solicitacaoServico.prazoLimite' />"		, field: "dataHoraLimite"			, width: 110   													}
					        ,   { id: "atraso"       		, name: "<i18n:message key='tarefa.atraso' />" 						, field: "atraso"           		, width: 60,   	formatter: AddAtraso, resizable:false 			} 
					        ,   { id: "status"       		, name: "<i18n:message key='solicitacaoServico.situacao' />"     	, field: "status"           		, width: 150,	formatter: AddSituacao, resizable:false    		}
					        ,   { id: "descricao"			, name: "<i18n:message key='tarefa.tarefa_atual' />"				, field: "descricao"    	 		, width: 260,   formatter: AddBotoesTarefa						}
					        ,   { id: "assinatura"			, name: "<i18n:message key='gerenciaservico.delegarcompartilhartarefa' />"	,field: "liberacao"    	 		    , width: 155,   formatter: addLinkAprovarLiberacao						}
					        ,   { id: "nomeGrupoAtual"		, name: "<i18n:message key='citcorpore.comum.grupoExecutor' />"		, field: "nomeGrupoAtual"     		, width: 150    												}
					        ,   { id: "responsavelAtual"	, name: "<i18n:message key='tarefa.responsavelatual' />"			, field: "responsavelAtual" 		, width: 200   													}
					        ,   { id: "compartilhamento"	, name: "<i18n:message key='tarefa.compartilhadacom' />", field: "compartilhamento"  , width: 120    }
	        ]
	    ,   gridOptions = {
				            editable:               false
				        ,   asyncEditorLoading:     false
				        ,   enableAddRow:           false
				        ,   enableCellNavigation:   true
				        ,   enableColumnReorder:    true
				        ,   rowHeight: 40
	        };	

	var dadosGrafico;
	var dadosGrafico2;
	var dadosGerais;
	var scriptTemposSLA = '';
	var temporizador;
	exibirTarefas = function(json_tarefas) {
		try{
			var tarefas = [];
			var qtdeAtrasadas = 0;
			var qtdeSuspensas = 0;
			var qtdeEmAndamento = 0;
			var qtdePri1 = 0;
			var qtdePri2 = 0;
			var qtdePri3 = 0;
			var qtdePri4 = 0;
			var qtdePri5 = 0;
			var qtdeItens = 0;
			var colGrupoSol = new HashMap();
			scriptTemposSLA = "";

			arrayTarefas = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(json_tarefas); 
		    for(var i = 0; i < arrayTarefas.length; i++){
	            var tarefaDto = arrayTarefas[i];
	            tarefaDto.solicitacaoDto = ObjectUtils.deserializeObject(tarefaDto.solicitacao_serialize);	     
	            tarefaDto.elementoFluxoDto = ObjectUtils.deserializeObject(tarefaDto.elementoFluxo_serialize);         
		    }
			
			var strTableTemposSLA = '';
			strTableTemposSLA += "<img width='20' height='20' ";
			strTableTemposSLA += "alt='"+  i18n_message('ativaotemporizador')+"' id='imgAtivaTimer' style='opacity:0.5;display:none' ";
			strTableTemposSLA += "title='"+ i18n_message('citcorpore.comum.ativadestemporizador') +"' ";
			strTableTemposSLA += "src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/cronometro.png'/>";	
			strTableTemposSLA += "<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\">";
			strTableTemposSLA = strTableTemposSLA + "<tr><td><b><i18n:message key='gerenciaservico.slasandamento' /></b></td></tr>";	
			//inicializarTemporizador();
			for(var i = 0; i < arrayTarefas.length; i++){
				var idRequisicao = "";
				var solicitante = "";
				var prioridade = "";
				var situacao = "";
				var prazo = "";
				var dataHoraSolicitacao = "";
				var dataHoraLimite = "";
				var nomeGrupoAtual = "";

				var tarefaDto = arrayTarefas[i];
				var solicitacaoDto = tarefaDto.solicitacaoDto;
				if (solicitacaoDto != null) {
					if (solicitacaoDto.prioridade == '1'){
						qtdePri1++;
					}
					if (solicitacaoDto.prioridade == '2'){
						qtdePri2++;
					}
					if (solicitacaoDto.prioridade == '3'){
						qtdePri3++;
					}
					if (solicitacaoDto.prioridade == '4'){
						qtdePri4++;
					}
					if (solicitacaoDto.prioridade == '5'){
						qtdePri5++;
					}	
					var grupoNome = solicitacaoDto.nomeGrupoAtual;
					if (grupoNome == null){
						grupoNome = ' --'+ i18n_message("citcorpore.comum.aclassificar")+ '--';
					}
					var auxGrp = colGrupoSol.get(grupoNome);
					if (auxGrp != undefined){
						auxGrp.qtde++;
					}else{
						var grupoQtde = new GrupoQtde();
						grupoQtde.id = grupoNome; 
						grupoQtde.qtde = 1;
						colGrupoSol.set(grupoNome, grupoQtde);
					}								
					
					idRequisicao = ""+solicitacaoDto.idRequisicaoLiberacao;
					solicitante = ""+solicitacaoDto.nomeSolicitante;
					if (solicitacaoDto.prazoHH < 10)
						prazo = "0";
					prazo += solicitacaoDto.prazoHH + ":";
					if (solicitacaoDto.prazoMM < 10)
						prazo += "0";
					prazo += solicitacaoDto.prazoMM;
					prioridade = ""+solicitacaoDto.prioridade;
					dataHoraSolicitacao = solicitacaoDto.dataHoraInicioStr;
					if (solicitacaoDto.status != "<%=SituacaoRequisicaoLiberacao.Suspensa%>") { 
						dataHoraLimite = solicitacaoDto.dataHoraTerminoStr;
					}
					nomeGrupoAtual = solicitacaoDto.nomeGrupoAtual;
					
					if (solicitacaoDto.atraso > 0.0 && solicitacaoDto.status != "<%=SituacaoRequisicaoLiberacao.Suspensa%>"){
						qtdeAtrasadas++;
					}else if (solicitacaoDto.status == "<%=SituacaoRequisicaoLiberacao.Suspensa%>" && tarefaDto.reativar == 'S'){
						qtdeSuspensas++;
					}else {
						qtdeEmAndamento++;
						if (qtdeItens < 15){
							scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idRequisicaoLiberacao + "', " + "'barraProgresso" + solicitacaoDto.idRequisicaoLiberacao + "', "
								+ "'" + solicitacaoDto.dataHoraSolicitacaoToString + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
							strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idRequisicaoLiberacao + "</b>: <label id='tempoRestante" + solicitacaoDto.idRequisicaoLiberacao + "'></label>";
							strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idRequisicaoLiberacao + "'></div></td></tr>";
						}
						qtdeItens++;
					}
				} 
				
		        tarefas[i] = {
							 iniciar:			tarefaDto.executar
							,executar:			tarefaDto.executar
							,delegar:			tarefaDto.delegar
							,idRequisicao:		idRequisicao
							,solicitante: 		solicitante
							,prioridade: 		prioridade
							,dataHoraSolicitacao: dataHoraSolicitacao
							,descricao: 		tarefaDto.elementoFluxoDto.documentacao
							,situacao:	 		""
							,atraso:	 		""
							,dataHoraLimite: 	dataHoraLimite
							,responsavelAtual:  tarefaDto.responsavelAtual
							,nomeGrupoAtual:  nomeGrupoAtual
	        			 	,compartilhamento:  tarefaDto.compartilhamento
       			}
			}
			strTableTemposSLA = strTableTemposSLA + '</table>';
			if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){reativarSolicitacao
				var info = '';
				if (qtdeAtrasadas > 0){
					info += ' <font color="red"><b>' + qtdeAtrasadas + ' </b>  <i18n:message key="requisicaoLiberacao.requisicoes_liberacao_atrasado" /></font><br>';
				}
				if (qtdeSuspensas > 0){
					info += ' <b>' + qtdeSuspensas + ' </b><i18n:message key="requisicaoLiberacao.requisicoes_liberacao_suspenso" />';
				}
				/* info = 'Existem: ' + info + '<br><div id="divTemposSLA" style="height:115px; overflow:auto">' + strTableTemposSLA + '</div>'; */
				if (document.getElementById('fraRequisicaoLiberacao').src == "about:blank"){
					info = '<table cellpadding="0" cellspacing="0" style="overflow: auto"><tr><td style="width:20px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" style="height: 250px; min-width: 340px;"></div></td><td><div id="divGrafico2" style="height: 250px; min-width: 250px;"></div></td><td><div id="divGrafico3" style="height: 250px; min-width: 270px;overflow:auto;"></div></td></tr></table>';
					document.getElementById('tdAvisosSol').innerHTML = info;
					dadosGrafico = [['<i18n:message key="gerenciaservico.emandamento" />',qtdeEmAndamento],['<i18n:message key="gerenciaservico.suspensas" />',qtdeSuspensas],['<i18n:message key="gerenciaservico.atrasadas" />',qtdeAtrasadas]];
					dadosGrafico2 = [[' 1 ',qtdePri1],[' 2 ',qtdePri2],[' 3 ',qtdePri3],[' 4 ',qtdePri4],[' 5 ',qtdePri5]];
					window.setTimeout(atualizaGrafico, 1000);
					window.setTimeout(atualizaGrafico2, 1000);
					
					var colArray = colGrupoSol.toArray();
					dadosGerais = new Array();
					if (colArray){
						for (var iAux = 0; iAux < colArray.length; iAux++){
							dadosGerais[iAux] = [colArray[iAux].id, colArray[iAux].qtde];
						}
					}
					window.setTimeout(atualizaGrafico3, 1000);
				}
			}

	        document.getElementById("divConteudoLista").innerHTML = "<div id=\"divConteudoListaInterior\" style=\"height: 100%; width: 100%\"></div>";
	        gridTarefa = new Slick.Grid($("#divConteudoListaInterior"), tarefas,  colunasTarefa, gridOptions );
		}catch(e){
			alert(i18n_message("gerenciamentoLiberacao.ErroRenderizarGrid"));
			document.form.erroGrid.value = e;
			document.fireEvent("imprimeErroGrid");
		}
	};
	
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
	
	capturarTarefa = function(responsavelAtual, idTarefa, idRequisicao) {
		var msg = "";
		if (responsavelAtual == '')
			msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '<%=login%>'  ?";
		else 	
			msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +" '<%=login%>' "+ i18n_message("gerencia.confirm.atribuicaotarefa_3");
			
		if (!confirm(msg)) 
			return;
		JANELA_AGUARDE_MENU.show();
		document.form.idTarefa.value = idTarefa;
		document.form.idRequisicao.value = idRequisicao;	
		document.form.fireEvent('capturaTarefa');
	};
	
	function atualizaGrafico(){
		//plotaGrafico(dadosGrafico, "divGrafico");
		plotaGraficoHCharts(dadosGrafico, "divGrafico", i18n_message("requisicaoMudanca.situacaoAtividades")) 
		//eval(scriptTemposSLA);
		//temporizador.init();
		//temporizador.ativarDesativarTimer();
	}
	function atualizaGrafico2(){
		//plotaGrafico(dadosGrafico2, "divGrafico2");
		plotaGraficoHCharts(dadosGrafico2, "divGrafico2", i18n_message("requisicaoMudanca.resumoPrioridade"));
	}
	function atualizaGrafico3(){
		//plotaGrafico(dadosGerais, "divGrafico3");
		plotaGraficoHCharts(dadosGerais, "divGrafico3", i18n_message("requisicaoMudanca.resumoGrupo"));
	}	

    var myLayout;
    var popup;
	popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");

    $(document).ready(function () {
		$( "#POPUP_VISAO" ).dialog({
			title: '<i18n:message key="citcorpore.comum.visao" />',
			width: 900,
			height: 600,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			}); 
			
		$("#POPUP_VISAO").hide();
		
		$( "#POPUP_BUSCA" ).dialog({
			title: '<i18n:message key="citcorpore.comum.buscarapida" />',
			width: 250,
			height: 300,
			modal: false,
			autoOpen: false,
			resizable: false
			}); 
		
		$( "#popupCadastroRapido" ).dialog({
			title: '<i18n:message key="citcorpore.comum.cadastrorapido" />',
			width: 900,
			height: 600,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			}); 

        // create the layout - with data-table wrapper as the layout-content element
        myLayout = $('body').layout({
        	west__size:         20
        ,	south__size:        420
        ,   center__minHeight: 	350 //limite maximo que o panel pode subir.
        ,   west__onresize:     function (pane, $pane, state, options) {
                                    var $content    = $pane.children('.ui-layout-content')
                                    ,   gridHdrH    = $content.children('.slick-header').outerHeight()
                                    ,   paneHdrH    = $pane.children(':first').outerHeight()
                                    ,   paneFtrH    = $pane.children(':last').outerHeight()
                                    ,   $gridList   = $content.children('.slick-viewport') ;
                                    $gridList.height( state.innerHeight - paneHdrH - paneFtrH - gridHdrH );
                                }
        ,   south__onresize:   function (pane, $pane, state, options) {
                                    var gridHdrH    = $pane.children('.slick-header').outerHeight()
                                    ,   $gridList   = $pane.children('.slick-viewport') ;
                                    $gridList.height( state.innerHeight - gridHdrH );
                                    document.form.fireEvent('exibeTarefas');
                                }
        ,    east__initClosed:   true
        ,	 east__size:		 0
        , west: {
        		onclose_end: function(){
					myLayout.close("west");
					document.getElementById("tdLeft").style.backgroundColor = 'white';        		
        		},
        		onopen_end: function(){
					myLayout.open("west");
					document.getElementById("tdLeft").style.backgroundColor = 'lightgray';        		
        		}        		
        	}
        , south: {
        		onclose_end: function(){
					myLayout.close("south");
					document.getElementById("tdDown").style.backgroundColor = 'white';        		
        		},
        		onopen_end: function(){
					myLayout.open("south");
					document.getElementById("tdDown").style.backgroundColor = 'lightgray';  
        			document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
                    document.form.fireEvent('exibeTarefas');     		
        		},
        		onresize_end: function(){
        			document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
                    document.form.fireEvent('exibeTarefas');        		
        		}     		
        	}
        });

        $('body > h2.loading').hide(); // hide Loading msg
        <%	if(request.getParameter("idRequisicao") == null)	{		%>
       		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		<%
		} else	{	%>	
			visualizarSolicitacao(<%= request.getParameter("idRequisicao") %>);
		<%}%>
		
	    myLayout.hide('north');
	    myLayout.hide('west');
    });


	voltar = function(){
		if (document.getElementById('fraRequisicaoLiberacao').src == "about:blank"){
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
		}else{
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoLiberacao/gerenciamentoLiberacao.load';
		}
	};
	
	editarSolicitacao = function(idRequisicao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.getElementById('fraRequisicaoLiberacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO%>?idRequisicaoLiberacao="+idRequisicao+"&escalar=S&alterarSituacao=N";
	};
	
	visualizarSolicitacao = function(idRequisicao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		JANELA_AGUARDE_MENU.show();
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.getElementById('fraRequisicaoLiberacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO%>?idRequisicaoLiberacao="+idRequisicao+"&escalar=N&alterarSituacao=N&editar=N";
	};

	reclassificarSolicitacao = function(idRequisicao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.getElementById('fraRequisicaoLiberacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO%>?idRequisicaoLiberacao="+idRequisicao+"&reclassificar=S";
	};

	prepararSuspensao = function(idRequisicao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/suspensaoLiberacao/suspensaoLiberacao.load?idRequisicaoLiberacao="+idRequisicao;
		$( "#POPUP_VISAO" ).dialog({ height: 500 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciamentoLiberacao.suspenderLiberacao" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};
	
	prepararSuspensao = function(idRequisicao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/suspensaoLiberacao/suspensaoLiberacao.load?idRequisicaoLiberacao="+idRequisicao;
		$( "#POPUP_VISAO" ).dialog({ height: 500 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciamentoLiberacao.suspenderLiberacao" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};


	reativarSolicitacao = function(idRequisicao) {
		if (!confirm(i18n_message("requisicaoLiberacao.confirmaReativacaoLiberacao"))) 
			return;
		JANELA_AGUARDE_MENU.show();
		document.form.idRequisicao.value = idRequisicao;
		document.form.fireEvent('reativaRequisicao');
	};
	
	agendaAtividade = function(idRequisicao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendarAtividadeRequisicaoLiberacao/agendarAtividadeRequisicaoLiberacao.load?idRequisicaoLiberacao="+idRequisicao;
		$( "#POPUP_VISAO" ).dialog({ height: 600 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.agendaratividade" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	
	prepararExecucaoTarefa = function(idTarefa,idRequisicao,acao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.form.idTarefa.value = idTarefa;
		document.form.acaoFluxo.value = acao;
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('preparaExecucaoTarefa');
	};
	
	prepararLiberacaoSLA = function(idTarefa,idRequisicao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/mudarSLA/mudarSLA.load?idRequisicaoLiberacao="+idRequisicao;
		$( "#POPUP_VISAO" ).dialog({ height: 550 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.mudarsla" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	exibirDelegacaoTarefa = function(idTarefa,idRequisicao,nomeTarefa) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/delegacaoLiberacao/delegacaoLiberacao.load?idRequisicaoLiberacao="+idRequisicao+"&idTarefa="+idTarefa+"&nomeTarefa="+nomeTarefa;
		$( "#POPUP_VISAO" ).dialog({ height: 400 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.delegarcompartilhartarefa" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	exibirVisao = function(titulo,idVisao,idFluxo,idTarefa,acao){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.getElementById('fraRequisicaoLiberacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
		//Versao anterior - codigo comentado abaixo.
		//document.getElementById('fraVisao').src = "about:blank";
		//document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
		//$( "#POPUP_VISAO" ).dialog({ height: 600 });
		//$( "#POPUP_VISAO" ).dialog({ title: titulo });
		//$( "#POPUP_VISAO" ).dialog( 'open' );
	};
	
	fecharVisao = function(){
		$( "#POPUP_VISAO" ).dialog( 'close' );
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.form.fireEvent('exibeTarefas');
		myLayout.open("south");
		JANELA_AGUARDE_MENU.hide();
		
		//myLayout.open("west");		
	};
				
	abrirSolicitacao = function(){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south"); 
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.getElementById('fraRequisicaoLiberacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO%>";
	};

	exibirUrl = function(titulo, url){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.getElementById('fraRequisicaoLiberacao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/"+url;
		//Versao anterior - codigo comentado abaixo.
		//document.getElementById('fraVisao').src = "about:blank";
		//document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/"+url;
		//$( "#POPUP_VISAO" ).dialog({ height: 600 });
		//$( "#POPUP_VISAO" ).dialog({ title: titulo });
		//$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	fecharLiberacao = function(){
		myLayout.open("south");
		//myLayout.open("west");
		document.getElementById('fraRequisicaoLiberacao').src = "about:blank";
		document.form.fireEvent('exibeTarefas');			
	};
	
	atualizarListaTarefas = function() {
		myLayout.open("south");
		document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
		//document.form.numeroContratoSel.value = document.formPesquisa.numeroContratoSel.value;
		document.form.idRequisicaoSel.value = document.formPesquisa.idRequisicaoSel.value;
		document.form.atribuidaCompartilhada.value = document.formPesquisa.atribuidaCompartilhada.value;
		document.form.fireEvent('exibeTarefas')
	}
	
	abrePopup = function(obj,func) {
		popup.abrePopup('usuario','()');
	}

	function resize_iframe()
	{
		var height=window.innerWidth;//Firefox
		if (document.body.clientHeight)
		{
			height=document.body.clientHeight;//IE
		}
		document.getElementById("fraRequisicaoLiberacao").style.height=parseInt(height - document.getElementById("fraRequisicaoLiberacao").offsetTop-135)+"px";
	}
	function controleArea(tdName, areaName){
		if (document.getElementById(tdName).style.backgroundColor == 'white'){
			myLayout.open(areaName);
			document.getElementById(tdName).style.backgroundColor = 'lightgray';		
		}else{
			myLayout.close(areaName);
			document.getElementById(tdName).style.backgroundColor = 'white';
		}
	}
	
	function plotaGrafico(dados, componente) {		
		var plot1 = jQuery.jqplot(componente, [ dados ], {
			seriesDefaults : {
				// Make this a pie chart.
				renderer : jQuery.jqplot.PieRenderer,
				rendererOptions : {
					// Put data labels on the pie slices.
					// By default, labels show the percentage of the slice.
					showDataLabels : true
				}
			},
			legend : {
				show : true,
				location : 'e'
			}
		});
	}
	
	function plotaGraficoHCharts(dados, componente, title){
		
		   $("#"+componente).highcharts({
		        chart: {
		            plotBackgroundColor: null,
		            plotBorderWidth: null,
		            plotShadow: true
		        },
		        title: {
		            text: title
		        },
		        credits: {
		        	enable: false
		        	},
		        	
		        tooltip: {
		    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
		        },
		        plotOptions: {
		            pie: {
		                allowPointSelect: true,
		                cursor: 'pointer',
		                dataLabels: {
		                    enabled: true,
		                    color: '#000000',
		                    connectorColor: '#000000',
		                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
		                },
		        	showInLegend: true
		            }
		        },
		        series: [{
		            type: 'pie',
		            name: i18n_message("questionario.percentualTxt"),
		            data: dados
		            
		        }]
		    });
	
	}
	
	
	function janelaAguarde(){
		JANELA_AGUARDE_MENU.show();
	}
	
	function fechaJanelaAguarde(){
		JANELA_AGUARDE_MENU.hide();
	}
	
	
	
	function disableEnterKey(e){
	     var key;

	     if(window.event)
	          key = window.event.keyCode;     //IE
	     else
	          key = e.which;     //firefox

	     if(key == 13)
	          return false;
	     else
	          return true;
	}

	 function atualizaPagina(){
	   		if ( document.getElementById('chkAtualiza').checked ) {
	   			atualizarListaTarefas();
	   		} 
				
	   	}
		 
		 /* Agendador de atualizacao. */
			window.setInterval(atualizaPagina, 30000);
	
    </script>

</head>
<body>

<%@include file="/include/menu_horizontal_gerenciamento.jsp"%>

	<h2 class="loading"><i18n:message key="citcorpore.comum.aguardecarregando"/></h2>
	<!-- <div class="ui-layout-north hidden"></div> -->
	
	<div id="botao_voltar">
	<a href="#" onclick="voltar()">
						<i18n:message key="citcorpore.comum.voltar" />
					</a>
	</div>
			
	
	
<!-- 	<div class="ui-layout-west hidden">
	    <div class="ui-layout-content"></div>
	</div> -->
	
	

	<div class="ui-layout-center hidden">
	    <table width='100%' height='100%' style='width: 100%; height: 100%'>
	         <tr>
	         	<td width='100%' style='width: 100%; height: 100%; vertical-align: top !important;'>
					<iframe id='fraRequisicaoLiberacao' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>
				</td>
	         	<!-- <td width='50%'>
					<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
				</td> -->
			</tr>
		</table>		
	</div>	

	<!-- <div class="ui-layout-east hidden"></div> -->
	<div  class="ui-layout-south hidden">
	
	<div class="flat_area grid_16">
					<h2><i18n:message key="gerenciamentoLiberacao.gerenciamentoLiberacao" /></h2>						
				</div>
	
	<div class="box grid_16 tabs">

	
	
	<ul class="tab_header clearfix">
		<li>
			<a href="#tabs-1"><i18n:message key="gerenciamentoLiberacao.gerenciamentoLiberacao" /></a>
		</li>
		<li>
			<a href="#tabs-2" class="round_top"><i18n:message key="gerenciamentoLiberacao.gerenciamentoLiberacaoGraficos" /></a>
		</li>
	</ul>	


	<div class="toggle_container">					
<!---------- Início Gerenciamento de Mudança ---------->					
					
		<div id="tabs-1" class="block">

	    	<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" style="min-height: 75px;">
	    	<form name='formPesquisa' id='formPesquisa' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoLiberacao/gerenciamentoLiberacao'>
	    	
				<table width="100%">
					<tr>
						
						<td style='vertical-align: top;'>
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td style='vertical-align: middle;'>
											<i18n:message key="requisicaoLiberacao.numeroLiberacao" />:
										</td>
										<td style='vertical-align: top;'>
											<input onkeypress="return disableEnterKey(event)" class='Format[Numero]' type='text' name="idRequisicaoSel" id="idRequisicaoSel" size="10" style="border:1px solid #B3B3B3;height:24px; width: 77px !important" />
										</td>
										<td style='vertical-align: middle;'>		 
											<%-- <img border="0" title="<i18n:message key="citcorpore.comum.pesquisar"/>" style="cursor:pointer" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/search.png" onclick="atualizarListaTarefas()"/>&nbsp; --%>
												<a href='#' class='btn-action glyphicons search btn-default titulo'  title="<i18n:message key="citcorpore.comum.pesquisar"/>" onclick="atualizarListaTarefas()"><i></i></a> 
										</td>
										<td style='vertical-align: middle;'>
											<i18n:message key="gerenciaservico.atribuidacompartilhada"/>
										</td>
										
										<td style='vertical-align: top;'>
											<select name='atribuidaCompartilhada' id='atribuidaCompartilhada' onchange="atualizarListaTarefas()"></select>
										</td>	
																													
										<td style='vertical-align: middle;'>
											<%-- <img border="0" title="<i18n:message key="citcorpore.comum.limpar" />" 	  style="cursor:pointer" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/clear.png" onclick="document.formPesquisa.clear();atualizarListaTarefas()"/> --%>
										<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title="<i18n:message key="citcorpore.comum.limpar" />"  onclick="document.formPesquisa.clear();atualizarListaTarefas()"><i18n:message key='citcorpore.ui.botao.rotulo.Limpar' /></span>
										</td>
									</tr>
								</table>
						</td>
						<td>
							&nbsp;
						</td>
						<td>	
							<%-- <button type='button' title="<i18n:message key='requisicaoLiberacao.cadastrarLiberacao'/>" class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr" onclick="abrirSolicitacao()">
								<div class="asa">
									<span class=""><i18n:message key="requisicaoLiberacao.cadastrarLiberacao"/></span>
									<div class="G-asx T-I-J3 J-J5-Ji"></div>
								</div>
							</button>	 --%>	
							<span class="btn btn-small btn-primary" title="<i18n:message key='requisicaoLiberacao.cadastrarLiberacao'/>"  onclick="abrirSolicitacao()">
								<i18n:message key="requisicaoLiberacao.cadastrarLiberacao"/>
							</span>				
							<%-- <button type='button' title="<i18n:message key='gerenciarequisicao.pesquisaliberacao'/>" class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr" onclick="abrePopupPesquisa()">
								<div class="asa">
									<span class=""><i18n:message key="gerenciarequisicao.pesquisaliberacao"/></span>
									<div class="G-asx2 T-I-J3 J-J5-Ji"></div>
								</div>
							</button> --%>
							<span  title="<i18n:message key='gerenciarequisicao.pesquisaliberacao'/>"  class="btn btn-small btn-primary" onclick="abrePopupPesquisa()">
								<i18n:message key="gerenciarequisicao.pesquisaliberacao"/>
							</span>
							<%-- <button type='button' title="<i18n:message key="citcorpore.comum.atualizar" />" class="T-I J-J5-Ji nu T-I-ax7 L3 T-I-JO T-I-hvr" onclick="atualizarListaTarefas()">
								<div class="asa">
									<span class="J-J5-Ji ask">&nbsp;</span>
									<div class="asf T-I-J3 J-J5-Ji"></div>
								</div>
							</button> --%>
							<span class="btn btn-default btn-primary" type="button" onclick="atualizarListaTarefas();" id=""><i class="icon-white icon-refresh"></i></span>
					
							<div class="T-I J-J5-Ji ar7 nf L3 T-I-JO ">
								<label>
									<input type='checkbox' id='chkAtualiza' name='chkAtualiza' value='X'/>
									<i18n:message key="citcorpore.comum.atualizar" />&nbsp;<i18n:message key="citcorpore.comum.automaticamente" />
								</label>
							</div>
						</td>
					</tr>
				</table>	
			</form>				
	    	</div>	
	    
	  
	  <!-- Fim Gerenciamento de Liberação -->
	  
		
		<div id='divConteudoLista' class="ui-layout-content" style="height: 380px;">
	    	<div id='divConteudoListaInterior' style='height: 100%; width: 100%'></div>
	    </div>
	</div>	
	</div>
	
<!---------- Início Gráficos ---------->	
	<div id="tabs-2" class="block">	
	<table cellpadding='0' cellspacing='0'>
		
			<tr>
				
				<td id='tdAvisosSol' style='vertical-align: top; '>
				</td>				
			</tr>
		</table>
		
			</div>
<!---------- Fim Gráficos ---------->	

	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
	
	<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoLiberacao/gerenciamentoLiberacao'>
		<input type='hidden' name='idFluxo'/>
		<input type='hidden' name='idVisao'/>
		<input type='hidden' name='idTarefa'/>
		<input type='hidden' name='acaoFluxo'/>
		<input type='hidden' name='idUsuarioDestino'/>
		<input type='hidden' name='numeroContratoSel'/>
		<input type='hidden' name='idRequisicaoSel'/>
		<input type='hidden' name='atribuidaCompartilhada'/>
		<input type='hidden' name='idRequisicao' id='idRequisicaoForm'/>		
		<input type='hidden' name='erroGrid' id='erroGrid'/>	
	</form>
	
	<div id="POPUP_VISAO">
		<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
	</div>
		
	<div id="popupCadastroRapido">
          <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div></div></div>
</body>
</html>

