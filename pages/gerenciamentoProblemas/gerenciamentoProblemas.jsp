<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoProblema"%>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho"%>
<%
    response.setCharacterEncoding("ISO-8859-1");
    response.setHeader( "Cache-Control", "no-cache");
    response.setHeader( "Pragma", "no-cache");
    response.setDateHeader ( "Expires", -1);
    
    String PAGE_CADASTRO = "/pages/problema/problema.load";
    
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
.slick-viewport, #divConteudoLista{
height:auto !important;
}
.grid-canvas{
	min-height: 300px;
	max-height:570px;
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
    
    popupPesquisaProblema = new PopupManager(1084, 1084, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
    popupPesquisaProblema.titulo = i18n_message("citcorpore.comum.pesquisarapida");
    
      function GrupoQtde(){
      		this.id = '';
      		this.qtde = 0;
      }

      prepararSuspensao = function(idProblema) {
  		document.getElementById('tdAvisosSol').innerHTML = '';
  		document.getElementById('fraVisao').src = "about:blank";
  		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/suspensaoProblema/suspensaoProblema.load?idProblema="+idProblema;
  		$( "#POPUP_VISAO" ).dialog({ height: 500 });
  		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="problema.suspenderProblema" />' });
  		$( "#POPUP_VISAO" ).dialog( 'open' );
  	  };
  	
  	  reativarProblema = function(idProblema) {
		if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao"))) 
			return;
		document.form.idProblema.value = idProblema;
		document.form.fireEvent('reativaProblema');
  	  };
	
  	  agendaAtividade = function(idProblema) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendarAtividadeProblema/agendarAtividadeProblema.load?idProblema="+idProblema;
		$( "#POPUP_VISAO" ).dialog({ height: $(window).height()-100 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.agendaratividade" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
  	  };
	
  	  capturarTarefa = function(responsavel, idTarefa, idProblema) {
		var msg = "";
		if (responsavel == '')
			msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '<%=login%>'  ?";
		else 	
			msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavel + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +" '<%=login%>' "+ i18n_message("gerencia.confirm.atribuicaotarefa_3");
			
		if (!confirm(msg)) 
			return;
		document.form.idTarefa.value = idTarefa;
		document.form.idProblema.value = idProblema;
		document.form.fireEvent('capturaTarefa');
  	  };
	
	   prepararExecucaoTarefa = function(idTarefa,idProblema,acao) {
			document.getElementById('tdAvisosSol').innerHTML = '';
			document.getElementById('fraRequisicaoProblema').src = "about:blank";
			document.form.idTarefa.value = idTarefa;
			document.form.acaoFluxo.value = acao;
			document.form.idProblema.value = idProblema;
			document.form.fireEvent('preparaExecucaoTarefa');
	   };

	   exibirUrl = function(titulo, url){
			document.getElementById('tdAvisosSol').innerHTML = '';
			myLayout.close("south");
			document.getElementById('fraRequisicaoProblema').src = "about:blank";
			document.getElementById('fraRequisicaoProblema').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/"+url;
		};
		
	   AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var problemaDto = tarefaDto.problemaDto;
		  	if (problemaDto != null && problemaDto.status == "<%=SituacaoProblema.Suspensa%>") 
		  		return value;
	
		  		var str = "<table cellpadding='0' cellspacing='0'><tr>";
		  		if (tarefaDto.executar == 'S') {
		  			if(problemaDto.responsavel != '<%=login%>'){
		  				str += '<td>';
		  					/*  str += "<img src='" + URL_INITIAL + "imagens/pegar.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.capturartarefa' />' onclick='capturarTarefa(\""+problemaDto.responsavel+"\","+tarefaDto.idItemTrabalho+","+problemaDto.idProblema+")'>&nbsp;"; */
		  					str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='<i18n:message key='gerenciaservico.capturartarefa' />' onclick='capturarTarefa(\""+problemaDto.responsavel+"\","+tarefaDto.idItemTrabalho+","+problemaDto.idProblema+")'><i18n:message key='citcorpore.comum.capturar' /></span>";
		  				str += '</td>';
			  			}
		     		 /* str += "<img src='" + URL_INITIAL + "imagens/executarTarefa.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.iniciarexecutartarefa' />' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+problemaDto.idProblema+",\"E\")'>&nbsp;"; */
		  			str += '<td>';
			  			str += "<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title='<i18n:message key='gerenciaservico.iniciarexecutartarefa' />' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+problemaDto.idProblema+",\"E\")'><i18n:message key='citcorpore.comum.executar' /></span>";
			  		str += '</td>';
					}
		  		
		  		//if (tarefaDto.delegar == 'S') 
		     	/*  str += "<img src='" + URL_INITIAL + "imagens/share.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.delegarcompartilhartarefa' />' onclick='exibirDelegacaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idRequisicaoMudanca+",\""+tarefaDto.elementoFluxoDto.documentacao+"\")'>&nbsp;"; */
		  	  	str += '<td>' + value + '</td>';
			 	str += '</tr></table>';
			      
		        return str ;
	   }

	   AddLinkProblema = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var problemaDto = tarefaDto.problemaDto; 
		  	if (problemaDto != null) {
		  	  var str = "";
		  	/*   str += "<img src='" + URL_INITIAL + "imagens/viewCadastro.png' style='cursor:pointer;' title='<i18n:message key='problema.vizualizarCadastroProblema' />' onclick='visualizarSolicitacao("+problemaDto.idProblema+")'>"; */
		  	str += "<span style='cursor:pointer;margin-top: 5px!important;' class='btn btn-mini btn-primary titulo' title='<i18n:message key='problema.vizualizarCadastroProblema' />' onclick='visualizarSolicitacao("+problemaDto.idProblema+")'><i18n:message key='citcorpore.comum.visualizar' /></span>";
		  	  str += "&nbsp;&nbsp;"+problemaDto.idProblema;
	          return str;
		    }else
		      return "";	  
	   };

	   AddStatus = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var problemaDto = tarefaDto.problemaDto;
		  	if (problemaDto != null) {
		  	  var str = problemaDto.status;
		  	  
		  	  if (problemaDto.atrasoSLA > 0.0 && problemaDto.status != "<%=SituacaoProblema.Suspensa%>") 
		      	 str += " <img src='" + URL_INITIAL + "imagens/exclamation02.gif' height='15px' title='<i18n:message key='gerenciaservico.atrasada' />'>"; 
		  	  if (problemaDto.status != "<%=SituacaoProblema.Suspensa%>" && tarefaDto.suspender == 'S') 
			  	/*  str = "<img src='" + URL_INITIAL + "imagens/stop.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.suspenderProblema' /> ' onclick='prepararSuspensao("+problemaDto.idProblema+")'>&nbsp;" + str; */
			  	 str = "<a href='#' class='btn-action glyphicons pause btn-default titulo' title='<i18n:message key='gerenciaservico.suspenderProblema' />' onclick='prepararSuspensao("+problemaDto.idProblema+")'><i></i></a> " + str;
		  	  if (problemaDto.status == "<%=SituacaoProblema.Suspensa%>" && tarefaDto.reativar == 'S') 
		  	  	 /* str = "<img src='" + URL_INITIAL + "imagens/play.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.reativarProblema' /> ' onclick='reativarProblema("+problemaDto.idProblema+")'>&nbsp;" + str;
		  	  	 str = " <img src='" + URL_INITIAL + "imagens/agenda.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.agendaratividade' />  ' onclick='agendaAtividade("+problemaDto.idProblema+")'>" + str; */
		  	 	str = "<a href='#' class='btn-action glyphicons play btn-default titulo' title='<i18n:message key='gerenciaservico.reativarProblema' />' onclick='reativarProblema("+problemaDto.idProblema+")'><i></i></a> " + str;
		  	  	str = "<a href='#' class='btn-action glyphicons book btn-default titulo' title='<i18n:message key='gerenciaservico.agendaratividade' />' onclick='agendaAtividade("+problemaDto.idProblema+")'><i></i></a> " + str;
	          return str;	  	  	 
		    }else
		      return "";	  
	   };
	   
	   AddImgPrioridade = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var problemaDto = tarefaDto.problemaDto;
		  	if (problemaDto.prioridade == '1'){
		    	return value + " <img src='" + URL_INITIAL + "imagens/b.gif' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.prioridadealta' />'/> ";
		  	}else{
		  		return value;
		  	}
		  };
		  
			
	 var	arrayTarefas   = []
	    ,   gridTarefa     = {}
	    ,   tarefas		   = []
	    ,   colunasTarefa = [
	           	{ id: "idProblema"     	, name: "<i18n:message key='citcorpore.comum.numero'/>"			, field: "idProblema" 			, width: 160 ,	formatter: AddLinkProblema, resizable:true }
	        ,   { id: "titulo"			, name: "<i18n:message key='citcorpore.comum.titulo'/>"			, field: "titulo"	    		, width: 230   }
	        ,   { id: "contrato"		, name: "<i18n:message key='contrato.contrato'/>"			, field: "contrato"	    		, width: 150   }
	        ,   { id: "dataCriacao"		, name: "<i18n:message key='solicitacaoServico.dataHoraCriacao'/>"		, field: "dataHoraCapturaStr"	, width: 120   }
	        ,   { id: "prioridade"		, name: "<i18n:message key='gerenciaservico.pri' />"		, field: "prioridade"  			, width: 30  ,  formatter: AddImgPrioridade  }
	      /*   ,   { id: "prazo"			, name: "Prazo"				, field: "prazo"				, width: 80 } */
	        ,   { id: "dataHoraLimite"	, name: "<i18n:message key='solicitacaoServico.prazoLimite'/>"		, field: "dataHoraLimiteStr"  	, width: 120   }
	        ,   { id: "solicitante"		, name:  "<i18n:message key='solicitacaoServico.solicitante'/>"		, field: "solicitante"			, width: 250   }
	        ,   { id: "status"			, name: "<i18n:message key='solicitacaoServico.situacao'/>" 			, field: "status"				, width: 150 ,	formatter: AddStatus, resizable:true }
	        ,   { id: "descricao"		, name: "<i18n:message key='tarefa.tarefa_atual'/>"		, field: "descricao" 			, width: 250 ,  formatter: AddBotoesTarefa }
	        ,   { id: "grupoAtual"		, name: "<i18n:message key='citcorpore.comum.grupoExecutor'/>"			, field: "nomeGrupoAtual"  		, width: 170   }
	        ,   { id: "responsavel"		, name: "<i18n:message key='tarefa.responsavelatual'/>"		, field: "responsavel"			, width: 120   }
	        ],   gridOptions = {
	            editable:               false
	        ,   asyncEditorLoading:     false
	        ,   enableAddRow:           false
	        ,   enableCellNavigation:   true
	        ,   enableColumnReorder:    true
	        ,   rowHeight: 40
	        };	

	//INICIO grid/graficos -> David
	
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
			
			//arrayTarefas = JSON.parse(json_tarefas);
			
			arrayTarefas = ObjectUtils.deserializeCollectionFromStringSemQuebraEnter(json_tarefas); 
		    for(var i = 0; i < arrayTarefas.length; i++){
	            var tarefaDto = arrayTarefas[i];
	            tarefaDto.problemaDto = ObjectUtils.deserializeObject(tarefaDto.problema_serialize);	     
	            tarefaDto.elementoFluxoDto = ObjectUtils.deserializeObject(tarefaDto.elementoFluxo_serialize);         
		    }

			var strTableTemposSLA = '';
			strTableTemposSLA += "<img width='20' height='20' ";
			strTableTemposSLA += "alt='"+  i18n_message('ativaotemporizador')+"' id='imgAtivaTimer' style='opacity:0.5;display:none' ";
			strTableTemposSLA += "title='"+ i18n_message('citcorpore.comum.ativadestemporizador') +"' ";
			strTableTemposSLA += "src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/cronometro.png'/>";	
			strTableTemposSLA += "<table class=\"table\" cellpadding=\"0\" cellspacing=\"0\">";
			strTableTemposSLA = strTableTemposSLA + "<tr><td><b><i18n:message key='gerenciaservico.slasandamento' /></b></td></tr>";	
			
		
		for(var i = 0; i < arrayTarefas.length; i++){
			var idProblema = "";
			var solicitante = "";
			var titulo = "";
			var contrato = "";
			var dataHoraCaptura = "";
			var nomeCriador = "";
			var nomeProprietario = "";
			var status = "";
			var prioridade = "";
			var dataHoraLimite = "";
			var nomeGrupoAtual = "";
			/* var prazo = ""; */
			
			var tarefaDto = arrayTarefas[i];
			var problemaDto = tarefaDto.problemaDto;
			if (problemaDto != null) {
	
				if (problemaDto.prioridade == '1'){
					qtdePri1++;
				}
				if (problemaDto.prioridade == '2'){
					qtdePri2++;
				}
				if (problemaDto.prioridade == '3'){
					qtdePri3++;
				}
				if (problemaDto.prioridade == '4'){
					qtdePri4++;
				}
				if (problemaDto.prioridade == '5'){
					qtdePri5++;
				}
			
				var grupoNome = problemaDto.nomeGrupoAtual;
				if (grupoNome == null){
					grupoNome = ' --'+ i18n_message("citcorpore.comum.aclassificar")+ '--';
				}
				
				idProblema = ""+problemaDto.idProblema;
				solicitante = ""+problemaDto.solicitante;
				
				/* if (problemaDto.prazoHH < 10)
					prazo = "0";
				prazo += problemaDto.prazoHH + ":";
				
				if (problemaDto.prazoMM < 10)
					prazo += "0";
				prazo += problemaDto.prazoMM; */
				
				prioridade = ""+problemaDto.prioridade;
				dataHoraCaptura = problemaDto.dataHoraCapturaStr;
				
				if (problemaDto.status != "<%=SituacaoProblema.Suspensa%>") { 
					dataHoraLimite = problemaDto.dataHoraLimiteStr;
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
				
				nomeGrupoAtual = problemaDto.nomeGrupoAtual;
			
				if (problemaDto.atraso > 0.0 && problemaDto.status != "<%=SituacaoProblema.Suspensa%>"){
					qtdeAtrasadas++;
				}else if (problemaDto.status == "<%=SituacaoProblema.Suspensa%>" && tarefaDto.reativar == 'S'){
					qtdeSuspensas++;
				}else {
					qtdeEmAndamento++;
					if (qtdeItens < 15){
						scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + problemaDto.idProblema + "', " + "'barraProgresso" + problemaDto.idProblema + "', "
							+ "'" + problemaDto.dataHoraCapturaStr + "', '" + problemaDto.dataHoraLimiteStr + "'));";
						strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + problemaDto.idProblema + "</b>: <label id='tempoRestante" + problemaDto.idProblema + "'></label>";
						strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + problemaDto.idProblema + "'></div></td></tr>";
					}
					qtdeItens++;
				}
			}
			 
			tarefas[i] = {
					iniciar:				tarefaDto.executar
					,executar:				tarefaDto.executar
					//,delegar:				tarefaDto.delegar
					
					,idProblema:			problemaDto.idProblema
					,titulo:				problemaDto.titulo
					,idContrato:			problemaDto.idContrato
					,contrato:				problemaDto.contrato
					,dataHoraCapturaStr:	problemaDto.dataHoraCapturaStr
					,solicitante:			problemaDto.solicitante
					,responsavel:			problemaDto.responsavel
					,status: 				problemaDto.status
					/* ,prazo:	 				prazo */
					,prioridade:  			problemaDto.prioridade 
					,descricao: 			tarefaDto.elementoFluxoDto.documentacao
					,dataHoraLimiteStr: 	problemaDto.dataHoraLimiteStr
					,nomeGrupoAtual: 		problemaDto.nomeGrupoAtual 
			}
		}
		
		strTableTemposSLA = strTableTemposSLA + '</table>';
			if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
				var info = '';
				
				if (qtdeAtrasadas > 0){
					info += ' <font color="red"><b>' + qtdeAtrasadas + ' </b>  <i18n:message key="problema.problemas_atrasado" /></font><br>';
				}

				if (qtdeSuspensas > 0){
					info += ' <b>' + qtdeSuspensas + ' </b><i18n:message key="problema.problemas_suspenso" />';
				}

				/* info = 'Existem: ' + info + '<br><div id="divTemposSLA" style="height:115px; overflow:auto">' + strTableTemposSLA + '</div>'; */
				
				if (document.getElementById('fraRequisicaoProblema').src == "about:blank"){
					info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:20px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" style="height: 250px; min-width: 340px;"></div></td><td><div id="divGrafico2" style="height: 250px; min-width: 250px;"></div></td><td><div id="divGrafico3" style="height: 250px; min-width: 270px;overflow:auto;"></div></td></tr></table>';
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
			alert(i18n_message("gerenciamentoMudanca.ErroRenderizarGrid"));
			document.form.erroGrid.value = e;
			document.fireEvent("imprimeErroGrid");
		}
	};
	
	//FIM grid/graficos -> David
	
/* 	function atualizaGrafico(){
		plotaGrafico(dadosGrafico, "divGrafico");
		//eval(scriptTemposSLA);
		//temporizador.init();
		//temporizador.ativarDesativarTimer();
	}
	function atualizaGrafico2(){
		plotaGrafico(dadosGrafico2, "divGrafico2");
	}
	function atualizaGrafico3(){
		plotaGrafico(dadosGerais, "divGrafico3");
	} */
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

    var myLayout;
    var popup;
	popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");

    $(document).ready(function () {
		$( "#POPUP_VISAO" ).dialog({
			title: 'Visão',
			width: "80%",
			height: $(window).height()-100,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			}); 
			
		$("#POPUP_VISAO").hide();
		
		$( "#POPUP_BUSCA" ).dialog({
			title: 'Busca Rápida',
			width: 250,
			height: 300,
			modal: false,
			autoOpen: false,
			resizable: false
			}); 
		
		$( "#popupCadastroRapido" ).dialog({
			title: 'Cadastro Rápido',
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
        			document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>"+i18n_message("citcorpore.comum.aguardecarregando")+"</div>";
                    document.form.fireEvent('exibeTarefas');     		
        		},
        		onresize_end: function(){
        			document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>"+i18n_message("citcorpore.comum.aguardecarregando")+"</div>";
                    document.form.fireEvent('exibeTarefas');        		
        		}     		
        	}
        });

        $('body > h2.loading').hide(); // hide Loading msg
        <%	if(request.getParameter("idProblema") == null)	{		%>
       		document.getElementById('fraRequisicaoProblema').src = "about:blank";
		<%
		} else	{	%>	
			visualizarSolicitacao(<%= request.getParameter("idProblema") %>);
		<%}%>
		
	    myLayout.hide('north');
	    myLayout.hide('west');
    });
    
    
	atualizarListaTarefas = function() {
		myLayout.open("south");
		document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'>"+i18n_message("citcorpore.comum.aguardecarregando")+"</div>";
		document.form.idProblemaSel.value = document.formPesquisa.idProblemaSel.value;
		document.form.fireEvent('exibeTarefas')
	}
	
	visualizarSolicitacao = function(idProblema) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraRequisicaoProblema').src = "about:blank";
		document.getElementById('fraRequisicaoProblema').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO%>?idProblema="+idProblema+"&escalar=N&alterarSituacao=N&editar=N";
	};


	voltar = function(){
		if (document.getElementById('fraRequisicaoProblema').src == "about:blank"){
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
		}else{
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoProblemas/gerenciamentoProblemas.load';
		}
	};
	
	editarSolicitacao = function(idProblema) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraRequisicaoProblema').src = "about:blank";
		document.getElementById('fraRequisicaoProblema').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO%>?idProblema="+idProblema+"&escalar=S&alterarSituacao=N";
	};
	
	fecharProblema = function(){
		myLayout.open("south");
		document.getElementById('fraRequisicaoProblema').src = "about:blank";
		document.form.fireEvent('exibeTarefas');			
	};
	
	fecharVisao = function(){
		$( "#POPUP_VISAO" ).dialog( 'close' );
		document.getElementById('fraRequisicaoProblema').src = "about:blank";
		document.form.fireEvent('exibeTarefas');
		myLayout.open("south");		
	};
	
	abrirSolicitacao = function(){
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraRequisicaoProblema').src = "about:blank";
		document.getElementById('fraRequisicaoProblema').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADASTRO%>";
	};
	
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
	
	function resize_iframe()
	{
		var height=window.innerWidth;//Firefox
		if (document.body.clientHeight)
		{
			height=document.body.clientHeight;//IE
		}
		document.getElementById("fraRequisicaoProblema").style.height=parseInt(height - document.getElementById("fraRequisicaoProblema").offsetTop-135)+"px";
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
  		
  		
  		popupPesquisaProblema.abrePopup('pesquisaProblema','onClosePopUp');
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
	<h2 class="loading">Carregando...</h2>
	<div class="ui-layout-north hidden"></div>
	

	<div id="botao_voltar">
	<a href="#" onclick="voltar()">
						<i18n:message key="citcorpore.comum.voltar" />
					</a>
	</div>			

	
	<!-- <div class="ui-layout-west hidden">
	    <div class="ui-layout-content"></div>
	</div> -->



	<div class="ui-layout-center hidden">
	    <table width='100%' height='100%' style='width: 100%; height: 100%;  vertical-align: top !important;'>
	         <tr>
	         	<td width='100%' style='width: 100%;  vertical-align: top !important;'>
					<iframe id='fraRequisicaoProblema' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;'onload="resize_iframe()"></iframe>
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
					<h2><i18n:message key="gerenciamentoProblemas.gerenciamentoProblemas"/></h2>						
				</div>
	
	<div class="box grid_16 tabs">

	
	
	<ul class="tab_header clearfix">
		<li>
			<a href="#tabs-1"><i18n:message key="gerenciamentoProblemas.gerenciamentoProblemas" /></a>
		</li>
		<li>
			<a href="#tabs-2" class="round_top"><i18n:message key="gerenciamentoProblemas.gerenciamentoProblemasGraficos" /></a>
		</li>
	</ul>	
	

	<div class="toggle_container">					
<!---------- Início Gerenciamento de Mudança ---------->					
					
		<div id="tabs-1" class="block">	    
	    	<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" >
	    	<form name='formPesquisa' id='formPesquisa' onsubmit='javascript:return false;'  
	    	action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicos/gerenciamentoServicos'>
				<div class="space" >
				<table width="100%">
					<tr>
						<td style='vertical-align: top;'>
								<table cellpadding="0" cellspacing="0">
									<tr>
										<td style='vertical-align: middle;'>
											N° <i18n:message key='itemConfiguracaoTree.problema'/>:
										</td>
										<td style='vertical-align: top;'>
											<input onkeydown="if ( event.keyCode == 13 || event.which == 13 ) atualizarListaTarefas();" type='text' name="idProblemaSel" id="idProblemaSel" class="Format[Numero]" size="10" style="border:1px solid #B3B3B3;height:24px; width: 77px !important" />
										</td>
										<td style='vertical-align: middle;'>		 
											<%-- <img border="0" title="<i18n:message key="citcorpore.comum.pesquisar"/>" style="cursor:pointer" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/search.png" onclick="atualizarListaTarefas()"/>&nbsp; --%>
											<a href='#' class='btn-action glyphicons search btn-default titulo'  title="<i18n:message key="citcorpore.comum.pesquisar"/>" onclick="atualizarListaTarefas()"><i></i></a> 
										</td>
										
										<td style='vertical-align: middle;'>
											<%-- <img border="0" title="<i18n:message key="citcorpore.ui.botao.rotulo.Limpar"/>"  style="cursor:pointer" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/clear.png" onclick="document.formPesquisa.clear();atualizarListaTarefas()"/> --%>
											<span style='cursor:pointer;' class='btn btn-mini btn-primary titulo' title="<i18n:message key="citcorpore.comum.limpar" />"  onclick="document.formPesquisa.clear();atualizarListaTarefas()"><i18n:message key='citcorpore.ui.botao.rotulo.Limpar' /></span>
										</td>
									</tr>
								</table>
						</td>
						<td>
							&nbsp;
						</td>						
				<%-- 		<td >	
							<button type='button' title="<i18n:message key='problema.novoproblema'/>" class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr" onclick="abrirSolicitacao()">
								<div class="asa">
									<span class=""><i18n:message key="problema.novoproblema"/></span>
									<div class="G-asx T-I-J3 J-J5-Ji"></div>
								</div>
							</button>
							<button type='button' title="<i18n:message key='pesquisaProblema.pesquisaProblema'/>" class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr" onclick="abrePopupPesquisa()">
								<div class="asa">
									<span class=""><i18n:message key="pesquisaProblema.pesquisaProblema"/></span>
									<div class="G-asx2 T-I-J3 J-J5-Ji"></div>
								</div>
							</button>						
							<button type='button' title="<i18n:message key="citcorpore.comum.atualizar" />" class="T-I J-J5-Ji nu T-I-ax7 L3 T-I-JO T-I-hvr" onclick="atualizarListaTarefas()">
								<div class="asa">
									<span class="J-J5-Ji ask">&nbsp;</span>
									<div class="asf T-I-J3 J-J5-Ji"></div>
								</div>
							</button>
							<div class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr">
								<label>
									<input type='checkbox' id='chkAtualiza' name='chkAtualiza' value='X'/>
									<i18n:message key="citcorpore.comum.atualizar" />&nbsp;<i18n:message key="citcorpore.comum.automaticamente" />
								</label>
							</div>
						<button  type="button" class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr" onclick="abrirSolicitacao()" style='width: 150px height: 50px'>
								<img border="0" title="<i18n:message key='problema.cadastrarNovoProblema' />" 
								src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/add-icon-medium.png" /><span><i18n:message key="problema.novoproblema" /></span>
						</button>
							<button style="" type="button" class="light img_icon has_text" onclick="atualizarListaTarefas()" >
								<img title="Atualizar lista de tarefas" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/refresh.png" /><span>Atualizar</span>
							</button>								
						</td> --%>
							<td>	
								<span class="btn btn-small btn-primary" title="<i18n:message key='problema.novoproblema'/>"  onclick="abrirSolicitacao()">
									<i18n:message key='problema.novoproblema'/>
								</span>				
								<span  title="<i18n:message key='pesquisaProblema.pesquisaProblema'/>"  class="btn btn-small btn-primary" onclick="abrePopupPesquisa()">
									<i18n:message key='pesquisaProblema.pesquisaProblema'/>
								</span>
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
				</div>	
			</form>				
	    	</div>	
	   
		
		<div id='divConteudoLista' class="ui-layout-content" style="height: 500px;">
	    	<div id='divConteudoListaInterior' style='height: 100%; width: 100%'></div>
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
	
	
	<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoProblemas/gerenciamentoProblemas'>
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

