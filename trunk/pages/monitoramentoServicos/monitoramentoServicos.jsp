<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho"%>

<%
    response.setCharacterEncoding("ISO-8859-1");
    response.setHeader( "Cache-Control", "no-cache");
    response.setHeader( "Pragma", "no-cache");
    response.setDateHeader ( "Expires", -1);
    
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
        
    <title>CITSMart</title>
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
	<!-- Easy-pie Plugin -->
<link href="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/scripts/plugins/charts/easy-pie/jquery.easy-pie-chart.css" rel="stylesheet" />
	
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

/* input#idRequisicaoSel{
	border: 1px solid #CCCCCC !important;
    padding: 0.4em !important;
    } */
iframe{
border: none !important;
}
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
	.T-I-ax7{
		margin-top:10px;
		}
		#popupCadastroRapido{
		width: 80% !important;
		left:10% !important;
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
	
	input[type="text"] {
 		 padding: 4px 6px !important;
   		 height: 26px !important;
   }

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
	
    <!-- SlickGrid and its dependancies (not sure what they're for?) --->
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.rule-1.0.1.1-min.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.event.drag.custom.js"></script>
    	
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.core.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.editors.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.grid.js"></script>
    
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
    <!--  Flot Charts Plugin -->
<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.js"></script>
<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.pie.min.js"></script>
<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.tooltip.min.js"></script>
<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.selection.min.js"></script>
<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.resize.min.js"></script>
<script src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/scripts/plugins/charts/flot/jquery.flot.orderBars.js"></script>
    
    

    <script type="text/javascript">
    
    //Autor: Tiago Cartibani
    //Data: 23/10/2013
    //Função para funcionar botões de idioma e logout
    
    $(document).ready(function() {
    	$('li.dropdown').click(function() {
    		if ($(this).is('.open')) {
    		$(this).removeClass('open');
    		} else {
    		$(this).addClass('open');
    		}
    	}); 

    }); 
    // Fim da Função para funcionar botões de idioma e logout

    popup2 = new PopupManager(1084, 1084, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	popup2.titulo = i18n_message("citcorpore.comum.pesquisarapida");
    
      function GrupoQtde(){
      		this.id = '';
      		this.qtde = 0;
      }
      
      //Autor: Emauri
      //Data: 05/11/2013
      //Trocado o icone de executar pelo botao executar      
		  AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null && solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	  		return value;

		var str = "";
	      
         return str + value;	  
	  };

	  AddLinkSolicitacao = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null) {
	  	  var str = "";
	  	  str += "&nbsp;&nbsp;"+solicitacaoDto.idSolicitacaoServico;
          return str;
	    }else
	      return "";	  
	  };

	  AddSituacao = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null) {
	  	  var str = solicitacaoDto.descrSituacao;
	  	  if (parseFloat(solicitacaoDto.atrasoSLA) > 0.0 && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	      	 str += " <img src='" + URL_INITIAL + "imagens/exclamation02.gif' height='15px' title='<i18n:message key='gerenciaservico.atrasada'  />' id='imgAtrasada' >"; 	  	  
          return str;	  	  	 
	    }else
	      return "";	  
	  };

	  AddAtraso = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	var result = "";
	  	if (solicitacaoDto != null && parseFloat(solicitacaoDto.atrasoSLA) > 0 && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	      	result = '<font color="red">' + solicitacaoDto.atrasoSLAStr + '</font>';
        return result;	  
	  };

	  AddSelTarefa = function(row, cell, value, columnDef, dataContext) {
        return "<input type='radio' name='selTarefa' value='S'/>";
	  };

	  AddBotaoMudancaSLA = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null && solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	  		return "";

	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	var aux = value;
	  	if (solicitacaoDto.slaACombinar == 'S'){
	  		aux = 'A comb.';
	  	}
        return aux;
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
	  
	  AddImgSolicitante = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if ((solicitacaoDto.emailcontato != '' && solicitacaoDto.emailcontato != undefined && solicitacaoDto.emailcontato != null) 
	  	||  (solicitacaoDto.telefonecontato != '' && solicitacaoDto.telefonecontato != undefined && solicitacaoDto.telefonecontato != null)
	  	||  (solicitacaoDto.localizacaofisica != '' && solicitacaoDto.localizacaofisica != undefined && solicitacaoDto.localizacaofisica != null)){
	  		var strAux = '';
	  		strAux += '' + solicitacaoDto.solicitanteUnidade;
	  		if (solicitacaoDto.telefonecontato != '' && solicitacaoDto.telefonecontato != undefined && solicitacaoDto.telefonecontato != null){
	  			strAux += '\n<i18n:message key="citcorpore.comum.telefone" />: ' + solicitacaoDto.telefonecontato;
	  		}
	  		if (solicitacaoDto.emailcontato != '' && solicitacaoDto.emailcontato != undefined && solicitacaoDto.emailcontato != null){
	  			strAux += '\n<i18n:message key="citcorpore.comum.email" />: ' + solicitacaoDto.emailcontato;
	  		}
	  		if (solicitacaoDto.localizacaofisica != '' && solicitacaoDto.localizacaofisica != undefined && solicitacaoDto.localizacaofisica != null){
	  			strAux += '\n<i18n:message key="citcorpore.comum.localizacao" />: ' + solicitacaoDto.localizacaofisica;
	  		}	  		
	    	return " <img src='" + URL_INITIAL + "imagens/cracha.png' style='cursor:pointer;' title='" + strAux + "'/> " + value;
	  	}else{
	  		return value;
	  	}
	  };

		if (isIE){
			 var	arrayTarefas   = []
			    ,   gridTarefa     = {}
			    ,   tarefas		   = []
			    ,   colunasTarefa = [
			           	{ id: "idSolicitacao"      	, name: "<i18n:message key='citcorpore.comum.numero' />"		, field: "idSolicitacao"       	, width: 80,	formatter: AddLinkSolicitacao, resizable:true	   }
			        ,   { id: "contrato"			, name: "<i18n:message key='contrato.contrato' />"		, field: "contrato"	       		, width: 100    }
			        ,   { id: "origem"				, name: "<i18n:message key='origemAtendimento.origem' />"		, field: "origem"	       		, width: 80    }
			        ,   { id: "servico"				, name: "<i18n:message key='servico.servico' />"		, field: "servico"	       		, width: 150    }
			        ,   { id: "solicitanteUnidade"	, name: "<i18n:message key='solicitacaoServico.solicitante' />"	, field: "solicitanteUnidade"	       	, width: 180, width: 180,    formatter: AddImgSolicitante, resizable:true    }
			        ,   { id: "dataHoraSolicitacao"	, name: "<i18n:message key='solicitacaoServico.dataHoraCriacao' />"		, field: "dataHoraSolicitacao"	, width: 110   }
			        ,   { id: "prioridade"			, name: "<i18n:message key='gerenciaservico.pri' />"			, field: "prioridade"	       	, width: 30,    formatter: AddImgPrioridade, resizable:true    }
			        ,   { id: "sla"					, name: "<i18n:message key='gerenciaservico.sla' />"			, field: "sla"					, width: 70,    formatter: AddBotaoMudancaSLA, resizable:true   }
			        ,   { id: "dataHoraLimite"		, name: "<i18n:message key='solicitacaoServico.prazoLimite' />"	, field: "dataHoraLimite"		, width: 110   }
			        ,   { id: "atrasoSLA"       	, name: "<i18n:message key='tarefa.atraso' />" 		, field: "atrasoSLA"           	, width: 50,   	formatter: AddAtraso, resizable:false 		}
			        ,   { id: "situacao"       		, name: "<i18n:message key='solicitacaoServico.situacao' />"     	, field: "situacao"           	, width: 150,	formatter: AddSituacao, resizable:false    	}
			        ,   { id: "descricao"			, name: "<i18n:message key='tarefa.tarefa_atual' />"	, field: "descricao"    	 	, width: 250,   formatter: AddBotoesTarefa}
			        ,   { id: "grupoAtual"			, name: "<i18n:message key='citcorpore.comum.grupoExecutor' />", field: "grupoAtual"     		, width: 80    }
			        ,   { id: "responsavelAtual"	, name: "<i18n:message key='tarefa.responsavelatual' />", field: "responsavelAtual"  , width: 80    }
			        ]
			    ,   gridOptions = {
			            editable:               false
			        ,   asyncEditorLoading:     false
			        ,   enableAddRow:           false
			        ,   enableCellNavigation:   true
			        ,   enableColumnReorder:    true
			        }
			    ;	
		}else{
		    var	arrayTarefas   = []
		    ,   gridTarefa     = {}
		    ,   tarefas		   = []
		    ,   colunasTarefa = [
		           	{ id: "idSolicitacao"      	, name: "<i18n:message key='citcorpore.comum.numero' />"		, field: "idSolicitacao"       	, width: 80,	formatter: AddLinkSolicitacao, resizable:true	   }
		        ,   { id: "contrato"			, name: "<i18n:message key='contrato.contrato' />"		, field: "contrato"	       		, width: 100    }
		        ,   { id: "origem"				, name: "<i18n:message key='origemAtendimento.origem' />"		, field: "origem"	       		, width: 80    }
		        ,   { id: "servico"				, name: "<i18n:message key='servico.servico' />"		, field: "servico"	       		, width: 150    }
		        ,   { id: "solicitanteUnidade"	, name: "<i18n:message key='solicitacaoServico.solicitante' />"	, field: "solicitanteUnidade"	       	, width: 180,    formatter: AddImgSolicitante, resizable:true    }
		        ,   { id: "dataHoraSolicitacao"	, name: "<i18n:message key='solicitacaoServico.dataHoraCriacao' />"		, field: "dataHoraSolicitacao"	, width: 110   }
		        ,   { id: "prioridade"			, name: "<i18n:message key='gerenciaservico.pri' />"			, field: "prioridade"	       	, width: 30,    formatter: AddImgPrioridade, resizable:true    }
		        ,   { id: "sla"					, name: "<i18n:message key='gerenciaservico.sla' />"			, field: "sla"					, width: 80,    formatter: AddBotaoMudancaSLA, resizable:true   }
		        ,   { id: "dataHoraLimite"		, name: "<i18n:message key='solicitacaoServico.prazoLimite' />"	, field: "dataHoraLimite"		, width: 110   }
		        ,   { id: "atrasoSLA"       	, name: "<i18n:message key='tarefa.atraso' />" 		, field: "atrasoSLA"           	, width: 50,   	formatter: AddAtraso, resizable:false 		}
		        ,   { id: "situacao"       		, name: "<i18n:message key='solicitacaoServico.situacao' />"     	, field: "situacao"           	, width: 150,	formatter: AddSituacao, resizable:false    	}
		        ,   { id: "descricao"			, name: "<i18n:message key='tarefa.tarefa_atual' />"	, field: "descricao"    	 	, width: 250,   formatter: AddBotoesTarefa}
		        ,   { id: "grupoAtual"			, name: "<i18n:message key='citcorpore.comum.grupoExecutor' />", field: "grupoAtual"     		, width: 120    }
		        ,   { id: "responsavelAtual"	, name: "<i18n:message key='tarefa.responsavelatual' />", field: "responsavelAtual"  , width: 120    }
		        ]
		    ,   gridOptions = {
		            editable:               false
		        ,   asyncEditorLoading:     false
		        ,   enableAddRow:           false
		        ,   enableCellNavigation:   true
		        ,   enableColumnReorder:    true
		        }
		    ;
		}

		var dadosGrafico;
		var dadosGrafico2;
		var dadosGerais;
		var scriptTemposSLA = '';
		var temporizador;
		exibirTarefas = function(json_tarefas) {
			var tarefas = [];
			//json_tarefas = '';
			//$("#ajaxX").text(json_tarefas);
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
			inicializarTemporizador();
			for(var i = 0; i < arrayTarefas.length; i++){
				var idSolicitacao = "";
				var contrato = "";
				var origem = "";
				var servico = "";
				var solicitante = "";
				var prioridade = "";
				var situacao = "";
				var sla = "";
				var dataHoraSolicitacao = "";
				var dataHoraLimite = "";
				var grupoAtual = "";

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
					var grupoNome = solicitacaoDto.grupoAtual;
					if (grupoNome == null){
						grupoNome = ' -- '+ i18n_message("citcorpore.comum.aclassificar")+ '--';
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
					
					idSolicitacao = ""+solicitacaoDto.idSolicitacaoServico;
					origem = ""+solicitacaoDto.origem;
					contrato = ""+solicitacaoDto.contrato;
					servico = ""+solicitacaoDto.servico;
					solicitante = ""+solicitacaoDto.solicitanteUnidade;
					if (solicitacaoDto.prazoHH < 10)
						sla = "0";
					sla += solicitacaoDto.prazoHH + ":";
					if (solicitacaoDto.prazoMM < 10)
						sla += "0";
					sla += solicitacaoDto.prazoMM;
					prioridade = ""+solicitacaoDto.prioridade;
					dataHoraSolicitacao = solicitacaoDto.dataHoraSolicitacaoStr;
					if (solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>") { 
						dataHoraLimite = solicitacaoDto.dataHoraLimiteStr;
					}
					grupoAtual = solicitacaoDto.grupoAtual;
					
					if (parseFloat(solicitacaoDto.atrasoSLA) > 0.0 && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>"){
						qtdeAtrasadas++;
					}else if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>" && tarefaDto.reativar == 'S'){
						qtdeSuspensas++;
					}else {
						qtdeEmAndamento++;
						if (qtdeItens < 100){
							if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S'){
								scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idSolicitacaoServico + "', " + "'barraProgresso" + solicitacaoDto.idSolicitacaoServico + "', "
									+ "'" + solicitacaoDto.dataHoraSolicitacaoToString + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
							}
							if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S'){
								strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'></label>";
								strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idSolicitacaoServico + "'></div></td></tr>";
							}else{
								strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><i18n:message key='citcorpore.comum.acombinar'/></font></label>";
							}
						}
						qtdeItens++;
					}
				} 
		        tarefas[i] = {
				        		 iniciar:			tarefaDto.executar
				        		,executar:			tarefaDto.executar
				        		,delegar:			tarefaDto.delegar
		        				,idSolicitacao:		idSolicitacao
		        			 	,contrato: 			contrato
		        			 	,origem: 			origem
		        			 	,servico: 			servico
		        			 	,solicitanteUnidade: 		solicitante
		        			 	,prioridade: 		prioridade
		        			 	,dataHoraSolicitacao: dataHoraSolicitacao
		        			 	,descricao: 		tarefaDto.elementoFluxoDto.documentacao
				        		,status:	 		""
				        		,sla:	 			sla
				        		,atrasoSLA:	 		""
		        			 	,dataHoraLimite: 	dataHoraLimite
		        			 	,responsavelAtual:  tarefaDto.responsavelAtual
		        			 	,grupoAtual:  grupoAtual
		        			}
			}
			strTableTemposSLA = strTableTemposSLA + '</table>';
			if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
				var info = '';
				if (qtdeAtrasadas > 0){
					info += ' <font color="red"><b>' + qtdeAtrasadas + '</b><i18n:message key="solicitacaoServico.solicitacoes_incidentes_atrasado" /></font><br>';
				}
				if (qtdeSuspensas > 0){
					info += ' <b>' + qtdeSuspensas + '</b> <i18n:message key="solicitacaoServico.solicitacoes_incidentes_suspenso" />';
				}
				info = '<i18n:message key="solicitacaoServico.existem" /> ' + info + '<br><div id="divTemposSLA" style="height:330px; overflow:auto; border:1px solid black">' + strTableTemposSLA + '</div>';

				info = '<table cellpadding="0" cellspacing="0" style="width: 100%!important"><tr><td style="width:10px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" style="height: 380px; width: 350px;"></div></td><td><div id="divGrafico2" style="height: 380px; width: 350px;"></div></td><td><div id="divGrafico3" style="height: 380px; width: 350px;"></div></td></tr></table>';
				//info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:15px">&nbsp;</td><td style="vertical-align: top; width: 100%; height: 250px">' + info + '</td></tr></table>';
				document.getElementById('tdAvisosSol').innerHTML = info;
				/* dadosGrafico = [['<i18n:message key="gerenciaservico.emandamento" />',qtdeEmAndamento],['<i18n:message key="gerenciaservico.suspensas" />',qtdeSuspensas],['<i18n:message key="gerenciaservico.atrasadas" />',qtdeAtrasadas]]; */
				dadosGrafico = [{label: i18n_message('citcorpore.comum.normal'), data: qtdeEmAndamento}, {label: i18n_message('citcorpore.comum.suspensa'), data: qtdeSuspensas},{label:  i18n_message('citcorpore.comum.vencido'), data: qtdeAtrasadas}];
				/* dadosGrafico2 = [[' 1 ',qtdePri1],[' 2 ',qtdePri2],[' 3 ',qtdePri3],[' 4 ',qtdePri4],[' 5 ',qtdePri5]]; */
				dadosGrafico2 = [{label:" 1 ", data: qtdePri1},{label:" 2 ", data: qtdePri2},{label: " 3 ", data: qtdePri3},{label: " 4 ", data: qtdePri4},{label: " 5 ", data: qtdePri5}];
				//window.setTimeout(atualizaGrafico, 1000);
				atualizaGrafico();
				//window.setTimeout(atualizaGrafico2, 1000);
				atualizaGrafico2();
				
				var colArray = colGrupoSol.toArray();
				dadosGerais = new Array();
				if (colArray){
					for (var iAux = 0; iAux < colArray.length; iAux++){
						/* dadosGerais[iAux] = [colArray[iAux].id, colArray[iAux].qtde]; */
						dadosGerais[iAux] = {label: colArray[iAux].id, data: colArray[iAux].qtde};
					}
				}
				//window.setTimeout(atualizaGrafico3, 1000);
				atualizaGrafico3();
				//window.setTimeout(atualizaPagina, 30000);
				atualizaPagina();
			}
	        //gridTarefa = new Slick.Grid( myLayout.contents.south,  tarefas,  colunasTarefa, gridOptions );
	        document.getElementById("divConteudoLista").innerHTML = "<div id=\"divConteudoListaInterior\" style=\"height: 100%; width: 100%\"></div>";
	        gridTarefa = new Slick.Grid($("#divConteudoListaInterior"), tarefas,  colunasTarefa, gridOptions );
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
	
	capturarTarefa = function(responsavelAtual, idTarefa, idRequisicao) {
		var msg = "";
		if (responsavelAtual == '')
			msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '<%=login%>'  ?";
		else 	
			msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +" '<%=login%>' "+ i18n_message("gerencia.confirm.atribuicaotarefa_3");
			
		if (!confirm(msg)) 
			return;
		document.form.idTarefa.value = idTarefa;
		document.form.idRequisicao.value = idRequisicao;
		document.form.fireEvent('capturaTarefa');
	};

    var myLayout;
    var popup;
	popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");

    $(document).ready(function () {
		$( "#POPUP_VISAO" ).dialog({
			title: i18n_message("citcorpore.comum.visao"),
			width: 900,
			height: 500,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			}); 
			
		$("#POPUP_VISAO").hide();
		
		$( "#POPUP_REUNIAO" ).dialog({
			title: i18n_message("citcorpore.comum.visao"),
			width: 900,
			height: 600,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			}); 
		
		$("#POPUP_REUNIAO").hide();
		
		$( "#POPUP_BUSCA" ).dialog({
			title: i18n_message("citcorpore.comum.buscarapida"),
			width: 250,
			height: 300,
			modal: false,
			autoOpen: false,
			resizable: false
			}); 
		
		$( "#popupCadastroRapido" ).dialog({
			title: i18n_message("citcorpore.comum.cadastrorapido"),
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
       /*  ,	south__size:        420 */
       	,	center__minHeight:	200
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
					// Removido para melhor performance da aplicação
        			//document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
                   // document.form.fireEvent('exibeTarefas');     		
        		},
        		onresize_end: function(){
        			//document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
                    //document.form.fireEvent('exibeTarefas');        		
        		}     		
        	}
        });

        $('body > h2.loading').hide(); // hide Loading msg
        <%	if(request.getParameter("idRequisicao") == null)	{		%>
       		document.getElementById('fraRequisicaoMudanca').src = "about:blank";
		<%
		} else	{	%>	
			visualizarSolicitacao(<%= request.getParameter("idRequisicao") %>);
		<%}%>
		
	    myLayout.hide('north');
	    myLayout.hide('west');
    });


	voltar = function(){
		window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
	};
	
	editarSolicitacao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServico/solicitacaoServico.load?idSolicitacaoServico="+idSolicitacao+"&escalar=S&alterarSituacao=N";
	};

	reclassificarSolicitacao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServico/solicitacaoServico.load?idSolicitacaoServico="+idSolicitacao+"&reclassificar=S";
	};

	prepararSuspensao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/suspensaoSolicitacao/suspensaoSolicitacao.load?idSolicitacaoServico="+idSolicitacao;
		$( "#POPUP_VISAO" ).dialog({ height: 500 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.suspendersolicitacao" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};
	
	reativarSolicitacao = function(idSolicitacao) {
		if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao"))) 
			return;
		document.form.idSolicitacao.value = idSolicitacao;
		document.form.fireEvent('reativaSolicitacao');
	};
	
	agendaAtividade = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendarAtividade/agendarAtividade.load?idSolicitacaoServico="+idSolicitacao;
		$( "#POPUP_VISAO" ).dialog({ height: 600 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.agendaratividade" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	prepararExecucaoTarefa = function(idTarefa,idSolicitacao,acao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.form.idTarefa.value = idTarefa;
		document.form.acaoFluxo.value = acao;
		document.form.fireEvent('preparaExecucaoTarefa');
	};
	
	prepararMudancaSLA = function(idTarefa,idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/mudarSLA/mudarSLA.load?idSolicitacaoServico="+idSolicitacao;
		$( "#POPUP_VISAO" ).dialog({ height: 550 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.mudarsla" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	exibirDelegacaoTarefa = function(idTarefa,idSolicitacao,nomeTarefa) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/delegacaoTarefa/delegacaoTarefa.load?idSolicitacaoServico="+idSolicitacao+"&idTarefa="+idTarefa+"&nomeTarefa="+nomeTarefa;
		$( "#POPUP_VISAO" ).dialog({ height: 400 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.delegarcompartilhartarefa" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	exibirVisao = function(titulo,idVisao,idFluxo,idTarefa,acao){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
		//Versao anterior - codigo comentado abaixo.
		//document.getElementById('fraVisao').src = "about:blank";
		//document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dinamicViews/dinamicViews.load?modoExibicao=J&idVisao="+idVisao+"&idFluxo="+idFluxo+"&idTarefa="+idTarefa+"&acaoFluxo="+acao;
		//$( "#POPUP_VISAO" ).dialog({ height: 600 });
		//$( "#POPUP_VISAO" ).dialog({ title: titulo });
		//$( "#POPUP_VISAO" ).dialog( 'open' );
	};
	
	fecharVisao = function(){
		$( "#POPUP_VISAO" ).dialog( 'close' );
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.form.fireEvent('exibeTarefas');
		myLayout.open("south");		
		//myLayout.open("west");		
	};
				
	abrirSolicitacao = function(){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		//myLayout.close("west");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServico/solicitacaoServico.load";
	};

	exibirUrl = function(titulo, url){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/"+url;
		//Versao anterior - codigo comentado abaixo.
		//document.getElementById('fraVisao').src = "about:blank";
		//document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/"+url;
		//$( "#POPUP_VISAO" ).dialog({ height: 600 });
		//$( "#POPUP_VISAO" ).dialog({ title: titulo });
		//$( "#POPUP_VISAO" ).dialog( 'open' );
	};

	fecharSolicitacao = function(){
		myLayout.open("south");
		//myLayout.open("west");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.form.fireEvent('exibeTarefas');			
	};
	
	atualizarListaTarefas = function() {
		myLayout.open("south");
		document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
		document.form.numeroContratoSel.value = document.formPesquisa.numeroContratoSel.value;
		document.form.idSolicitacaoSel.value = document.formPesquisa.idSolicitacaoSel.value;
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
		document.getElementById("fraSolicitacaoServico").style.height=parseInt(height - document.getElementById("fraSolicitacaoServico").offsetTop-8)+"px";
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
			  },
			 legend: {
			        show: false
			    }
			});
		}
		
	 abrePopupPesquisa = function( ) {
	  		
	  		$( "#popupCadastroRapido" ).dialog({
	  			title: i18n_message("citcorpore.comum.cadastrorapido"),
	  			width: 1240,
	  			height: 600,
	  			modal: true,
	  			autoOpen: false,
	  			resizable: false,
	  			show: "fade",
	  			hide: "fade"
	  			
	  			}); 
	  		
	  		
	  		document.getElementById('popupCadastroRapido').style.overflow = "hidden";
	  		document.getElementById('tdAvisosSol').innerHTML = '';
	  		
	  		
	  		popup2.abrePopup('pesquisaRequisicaoMudanca','onClosePopUp');
	  	}
	 
	    var cont = 0;
	     function atualizaPagina(){
	      	if (document.getElementById('chkAtualiza').checked && cont == 3){
	      		window.location.reload();
	      		document.form.fireEvent('exibeTarefas');
	      	}
	      	if (cont > 3) {
	      		cont = 0;
			}
	      	window.setInterval(atualizaPagina, 30000);
	      	cont++;
	      }

	
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
	
	<!-- <div class="ui-layout-west hidden">
	    <div class="ui-layout-content"></div>
	</div> -->

	<div class="ui-layout-center hidden ">
	    <table width='100%' height='100%' style='width: 100%; height: 100%; '>
	         <tr>
	         	<td width='100%' style='width: 100%; vertical-align: top !important; '>
					<iframe id='fraRequisicaoMudanca' src='about:blank' width="100%" style='width: 100%; border:none;' onload="resize_iframe()"></iframe>
				</td>
	         	<!-- <td width='50%'>
					<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
				</td> -->
			</tr>
		</table>		
	</div>	

	<!-- <div class="ui-layout-east hidden"></div> -->
	<div  class="ui-layout-south hidden ">
	
	<div class="box grid_16 tabs">

	
	
	<ul class="tab_header clearfix">
		<li>
			<a href="#tabs-1"><i18n:message key="painel.monitoramentoIncidente" /></a>
		</li>
		<li>
			<a href="#tabs-2" class="round_top"><i18n:message key='citcorpore.comum.graficos'/></a>
		</li>
	</ul>	
	

	<div class="toggle_container">					
<!---------- Início Gerenciamento de Mudança ---------->					
					
		<div id="tabs-1" class="block">
	    	<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" style="min-height: 75px; ">
	    	<form name='formPesquisa' id='formPesquisa' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoMudancas/gerenciamentoMudancas'>
	    	 <div class="space" >
				<table width="100%"  cellspacing="3" >
					<tr>
						<td style='vertical-align: top;'>
								<span class="btn btn-default btn-primary" type="button" onclick="window.location.reload();" id=""><i class="icon-white icon-refresh"></i>&nbsp;<i18n:message key="citcorpore.comum.atualizar" /></span>
					
						<div class="T-I J-J5-Ji ar7 nf L3 T-I-JO ">
							<label>
								<input type='checkbox' id='chkAtualiza' name='chkAtualiza' value='X' checked="checked"/>
								<i18n:message key="citcorpore.comum.atualizar" />&nbsp;<i18n:message key="citcorpore.comum.automaticamente" />
							</label>
						</div>
						</td>
						<td>
							&nbsp;
						</td>
					<%-- 	<td>
							<button type='button' title="<i18n:message key='gerenciarequisicao.pesquisaMudanca'/>" class="light img_icon has_text" onclick="abrePopupPesquisa()">
								<div class="asa">
									<span class=""><i18n:message key="gerenciarequisicao.pesquisaMudanca"/></span>
									<div class="G-asx2 T-I-J3 J-J5-Ji"></div>
								</div>
							</button>
						</td>						
						<td style='vertical-align: top;' width="20%">							
							<button  type="button" class="light img_icon has_text" onclick="abrirSolicitacao()" style='width: 150px'>
								<img border="0" title="<i18n:message key="requisicaoMudanca.cadastrarMudanca" />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/add-icon-medium.png" />
								<span ><i18n:message key="requisicaoMudanca.novaMudanca" /></span>
							</button>
						</td>
						<td style='vertical-align: top; text-align: right;float: right;'>
							<button style="" type="button" class="light img_icon has_text" onclick="atualizarListaTarefas()" >
								<img title="<i18n:message key="citcorpore.comum.atualizar" />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/refresh.png" /><span><i18n:message key="citcorpore.comum.atualizar" /></span>
							</button>								
						</td> --%>
					<td>
					<!--<span class="btn btn-small btn-primary" title="<i18n:message key='requisicaoMudanca.cadastrarMudanca'/>"  onclick="abrirSolicitacao()">
						<i18n:message key="requisicaoMudanca.cadastrarMudanca"/>
					</span>
					
					<span  title="<i18n:message key='gerenciarequisicao.pesquisaMudanca'/>"  class="btn btn-small btn-primary" onclick="abrePopupPesquisa()">
						<i18n:message key="gerenciarequisicao.pesquisaMudanca"/>
					</span>-->
					
					<%-- <button type='button' title="<i18n:message key="citcorpore.comum.atualizar" />" class="T-I J-J5-Ji nu T-I-ax7 L3 T-I-JO T-I-hvr" onclick="atualizarListaTarefas()">
						<div class="asa">
							<span class="J-J5-Ji ask">&nbsp;</span>
							<div class="asf T-I-J3 J-J5-Ji"></div>
						</div>
					</button> --%>
			</td>
					</tr>
				</table>
				</div>
			</form>				
	    	</div>	
	  
		
		<div id='divConteudoLista' class="ui-layout-content" style="height: 500px;">
	    	<div id='divConteudoListaInterior' style='height: 100%; width: 100%'></div>
	    </div>
	</div>
<!---------- Fim Gerenciamento de Mundança ---------->	
	
	
<!---------- Início Gráficos ---------->	
			<div id="tabs-2" class="block">	
				<div  style='vertical-align: top; '>
					<div class='col_100' id='tdAvisosSol'>
					</div>
				
				</div>				
			</div>
<!---------- Fim Gráficos ---------->		
	
	
	
	<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicos/gerenciamentoServicos'>
				<input type='hidden' name='idFluxo'/>
				<input type='hidden' name='idVisao'/>
				<input type='hidden' name='idTarefa'/>
				<input type='hidden' name='acaoFluxo'/>
				<input type='hidden' name='idUsuarioDestino'/>
				<input type='hidden' name='numeroContratoSel'/>
				<input type='hidden' name='idProblemaSel'/>
				<input type='hidden' name='atribuidaCompartilhada'/>
				<input type='hidden' name='idProblema' id='idProblemaForm'/>
	</form>
	
	<div id="POPUP_VISAO">
		<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
	</div>
				
	<div id="popupCadastroRapido">
          <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>
</div></div></div>
</body>
</html>