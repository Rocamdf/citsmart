<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSolicitacaoServico"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.SituacaoSLA"%>
<%@page import="br.com.centralit.bpm.util.Enumerados.SituacaoItemTrabalho"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
    response.setHeader( "Cache-Control", "no-cache");
    response.setHeader( "Pragma", "no-cache");
    response.setDateHeader ( "Expires", -1);
	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
	if (PAGE_CADADTRO_SOLICITACAOSERVICO == null){
	    PAGE_CADADTRO_SOLICITACAOSERVICO = "";
	}	    
	PAGE_CADADTRO_SOLICITACAOSERVICO = PAGE_CADADTRO_SOLICITACAOSERVICO.trim();   
	if (PAGE_CADADTRO_SOLICITACAOSERVICO.trim().equalsIgnoreCase("")){
	    PAGE_CADADTRO_SOLICITACAOSERVICO = "	/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos.load";
	}
	
	String nomeUsuario = "";
	UsuarioDTO usuario = WebUtil.getUsuario(request);
	if (usuario != null)
		nomeUsuario = usuario.getNomeUsuario();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head >
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">

	<meta name="viewport" content="width=device-width, initial-scale=1.0">

	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="Cache-Control" content="no-cache">
	<meta http-equiv="Expires" content="0">

	<%@include file="/include/security/security.jsp" %>
    <%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>

    <script>var URL_INITIAL = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/';</script>
        
    <title>CITSMart</title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>	
	
    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/layout-default-latest.css"/>

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
    
  /*   funciona apenas para IE10 e opera */
	@viewport {
		width: device-width;
		zoom: 1;
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
	
	.campoOrdenavel{
		cursor: pointer;
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


/* Resolução para PC */
	#divTemposSLA {width: 250px;}
	#divTemposSLA > table {width: 100%;}
	#divGrafico{
		min-height: 200px; 
		width: 300px;
		height: auto;
	}
		#divGrafico2{
		min-height: 200px; 
		width: 280px;
		height: auto;
	}
	#divGrafico3{
		 min-height:200px;
		 width: 300px;
		 height: auto;
	}
	#divGrafico4{
		/*  min-height:200px; */
		 /* width: 300px; */
		 height: auto;
		 border: 2px solid #999999;
		 margin-left: 1.5%;
	}
	/* Resolução para 1280 (Ex: Galaxy tab2 10.1 ) */
 	@media screen and (max-width:1280px) {
		
		#divControleLayout{
			height: auto;
			width: device-width;
		}
		#divTemposSLA{
			width: 250px;
		}
		 #divGrafico{
			min-height: 200px; 
			width: 240px;
			height: auto;
		}
		#divGrafico2{
			min-height: 200px; 
			width: 240px; 
			height: auto;
		}
		#divGrafico3{
			 min-height:200px;
			 width: 240px;
			 height: auto;
		}
		#divGrafico4{
		  min-height:183px;
		  width: 185px; 
		 height: auto;
		 border: 2px solid #999999;
	}
	}
	/* Resolução para 1024 (Ex: Galaxy tab2 7.0 )*/
 	@media screen and (max-width: 1024px) {
	
	#divTemposSLA {width: 220px;}
	#divTemposSLA > table {width: 100%;}
	#divGrafico{
		min-height: 200px; 
		width: 245px;
		height: auto;
	}
		#divGrafico2{
		min-height: 200px; 
		width: 230px;
		height: auto;
	}
	#divGrafico3{
		 min-height:200px;
		 width: 245px;
		 height: auto;
	}
	#divGrafico4{
		 min-height:200px;
		 width: 245px;
		 height: auto;
		 border: 2px solid #999999;
	}
	
} 

	
</style>

    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-ui-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.layout-latest.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.layout.slideOffscreen-1.1.js"></script>
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
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.maskedinput.js"></script>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/jquery.jqplot.min.js"></script>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/graficos/plugins/jqplot.pieRenderer.min.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.rule-1.0.1.1-min.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.event.drag.custom.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.core.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.editors.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/slick.grid.js"></script>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
    <script type="text/javascript">
      function GrupoQtde(){
      		this.id = '';
      		this.qtde = 0;
      }
      
    //somente numeros
      jQuery.fn.numbersOnly = function(){
        var $teclas = {8:'backspace',9:'tab',13:'enter',48:0,49:1,50:2,51:3,52:4,53:5,54:6,55:7,56:8,57:9};    
        $(this).keypress(function(e){
          var keyCode = e.keyCode?e.keyCode:e.which?e.which:e.charCode;
          if(keyCode in $teclas){
            return true;
          }else{
            return false;
          }
        });
        return $(this);
      }
      
      

	  AddBotoesTarefa = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null && solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	  		return value;

		var str = "";
	  	if (tarefaDto.executar == 'S') { 
	  	  if (tarefaDto.responsavelAtual != '<%=nomeUsuario%>') {
	      	str += "<img src='" + URL_INITIAL + "imagens/pegar.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.capturartarefa' />' onclick='capturarTarefa(\""+tarefaDto.responsavelAtual+"\","+tarefaDto.idItemTrabalho+")'>&nbsp;";
	  	  }
	      //str += "<img src='" + URL_INITIAL + "imagens/pegar.png' style='cursor:pointer;' title='Capturar e editar tarefa' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idSolicitacaoServico+",\"I\")'>&nbsp;";
	      str += "<img src='" + URL_INITIAL + "imagens/executarTarefa.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.iniciarexecutartarefa' />' onclick='prepararExecucaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idSolicitacaoServico+",\"E\")'>&nbsp;";
		}
	  	if (tarefaDto.delegar == 'S') 
	      str += "<img src='" + URL_INITIAL + "imagens/share.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.delegarcompartilhartarefa' />' onclick='exibirDelegacaoTarefa("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idSolicitacaoServico+",\""+tarefaDto.elementoFluxoDto.documentacao+"\")'>&nbsp;";
	      
         return str + value;	  
	  };

	  AddLinkSolicitacao = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null) {
	  	  var str = "";
	  	  //if (solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>") {
  	  	      str += "<img src='" + URL_INITIAL + "imagens/viewCadastro.png' style='cursor:pointer;' title='"+ solicitacaoDto.descricaoForTitle +"' \n onclick='visualizarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>";
		  	  if (tarefaDto.executar == 'S') {
		  	  	//str += "&nbsp;<img src='" + URL_INITIAL + "imagens/grupo.gif' style='cursor:pointer;' title='Direcionar atendimento' onclick='editarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>";
		  	  	str += "&nbsp;<img src='" + URL_INITIAL + "imagens/reclassificar.gif' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.reclassificarsolicitacao' /> ' onclick='reclassificarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>";
		  	  }
		  	  
		  	  if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.EmAndamento%>"){
		  		  
		  		str += "&nbsp;<img src='" + URL_INITIAL + "imagens/duplica_solicitacao.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.duplicarSolicitacao' /> ' onclick='carregarModalDuplicarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>";
		  		  
		  	  }
		  	  
		  	  if (solicitacaoDto.possuiFilho == "true"){
		  		  
		  		str += "&nbsp;<img src='" + URL_INITIAL + "imagens/exibirsubsolicitacoes.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.exibirsubsolicitacoes' /> ' onclick='exibirSubSolicitacoes("+solicitacaoDto.idSolicitacaoServico+")'>";
		  		  
		  	  }

		  	 if (solicitacaoDto.possuiAnexo == "S"){
		  		  
		  		str += "&nbsp;<img src='" + URL_INITIAL + "imagens/file.png' style='cursor:pointer;' title='<i18n:message key='solicitacaoServico.possuiAnexo' /> '>";
		  		  
		  	  }

	  	  str += "&nbsp;&nbsp;" + value;
          return str;
	    }else
	      return "";	  
	  };
	  
	  AddSituacao = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto != null) {
	  	  var str = solicitacaoDto.descrSituacao;
	  	  //var str = value;
	  	  
	  	  if (parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	      	 str += " <img src='" + URL_INITIAL + "imagens/exclamation02.gif' height='15px' title='<i18n:message key='gerenciaservico.atrasada' />'>"; 
	  	  if (solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>" && tarefaDto.suspender == 'S') 
		  	 str = "<img src='" + URL_INITIAL + "imagens/stop.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.suspendersolicitacao' /> ' onclick='prepararSuspensao("+solicitacaoDto.idSolicitacaoServico+")'>&nbsp;" + str;
	  	  if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>" && tarefaDto.reativar == 'S') 
	  	  	 str = "<img src='" + URL_INITIAL + "imagens/play.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.reativarsolicitacao' /> ' onclick='reativarSolicitacao("+solicitacaoDto.idSolicitacaoServico+")'>&nbsp;" + str;
	  	  str = " <img src='" + URL_INITIAL + "imagens/agenda.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.agendaratividade' />  ' onclick='agendaAtividade("+solicitacaoDto.idSolicitacaoServico+")'>" + str;
          return str;	  	  	 
	    }else
	      return "";	  
	  };

	  AddAtraso = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	var result = "";
	  	if (solicitacaoDto != null && parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	      	result = '<font color="red">' + solicitacaoDto.atrasoSLAStr + '</font>';
        return result;	  
	  };

	  AddSelTarefa = function(row, cell, value, columnDef, dataContext) {
        return "<input type='radio' name='selTarefa' value='S'/>";
	  };
	  
	  AddFarol = function(row, cell, value, columnDef, dataContext) {
		  	var tarefaDto = arrayTarefas[row];
		  	var solicitacaoDto = tarefaDto.solicitacaoDto;	
 		  
		  	if(solicitacaoDto.vencendo == ''){
		  	
		  		if (solicitacaoDto.atrasada == 'true' && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>"){
					return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolavermelha.png' title='<i18n:message key='gerenciaservico.atrasada' />'/>";
				}else{
					if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>"){
						return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolacinza2.png' title='<i18n:message key='citcorpore.comum.suspensa'/>'/>";
					}
					if (solicitacaoDto.falta1Hora == 'true'){				
						return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolaamarela.png' title='<i18n:message key='solicitacaoServico.menos1hora.desc'/>'/>";
					}else{
						return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolaverde.png'/>";
					}
				}
		  		//trata de acordo com regras de escalonamento definidas
		  	} else {
		  		if (solicitacaoDto.atrasada == 'true' && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>"){
					return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolavermelha.png' title='<i18n:message key='gerenciaservico.atrasada' />'/>";
				}else{
					if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>"){
						return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolacinza2.png' title='<i18n:message key='citcorpore.comum.suspensa'/>'/>";
					}
					if(solicitacaoDto.vencendo == 'S'){
						return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolaamarela.png' title='<i18n:message key='solicitacaoServico.vencendo'/>'/>";
					} else {
						return "<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/bolaverde.png'/>";
					}
				}
		  	}
	  };

	  AddBotaoMudancaSLA = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
        if (solicitacaoDto != null && solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>") 
	  		return "";

        if (solicitacaoDto != null && solicitacaoDto.situacaoSLA == "<%=SituacaoSLA.N%>") 
            return "Não iniciado";
        
        if (solicitacaoDto != null && solicitacaoDto.situacaoSLA == "<%=SituacaoSLA.S%>") 
            return "Suspenso";

        var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	var aux = value;
	  	if (solicitacaoDto.slaACombinar == 'S'){
	  		aux = 'A comb.';
	  	}
		if (tarefaDto.alterarSLA == 'S') {
	    	return aux + " <img src='" + URL_INITIAL + "imagens/mudancasla.png' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.mudarsla' />' onclick='prepararMudancaSLA("+tarefaDto.idItemTrabalho+","+tarefaDto.solicitacaoDto.idSolicitacaoServico+")'>";
	    }else{
	        return aux;
	    }	
	  }; 
	  
	  AddImgPrioridade = function(row, cell, value, columnDef, dataContext) {
	  	var tarefaDto = arrayTarefas[row];
	  	var solicitacaoDto = tarefaDto.solicitacaoDto;
	  	if (solicitacaoDto.prioridade == '1'){
	    	return solicitacaoDto.prioridade + " <img src='" + URL_INITIAL + "imagens/b.gif' style='cursor:pointer;' title='<i18n:message key='gerenciaservico.prioridadealta' />'/> ";
	  	}else{
	  		return solicitacaoDto.prioridade;
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
	  
    var	arrayTarefas   = []
    ,   gridTarefa     = {}
    ,   tarefas		   = []
    ,   colunasTarefa = [
           	{ id: "idSolicitacaoServico", name: "<i18n:message key='citcorpore.comum.numero' />", field: "idSolicitacaoServico"       	, width: 120,	formatter: AddLinkSolicitacao, resizable:true, sortable : true,  headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />"	   }
        ,   { id: "contrato"			, name: "<i18n:message key='contrato.contrato' />"		, field: "contrato"	       		, width: 100  , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />" }
	    ,   { id: "responsavel"			, name: "<i18n:message key='gerenciaservico.criado_por' />", field: "responsavel"	       	, width: 100 , sortable : true  , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />"	  }
        ,   { id: "servico"				, name: "<i18n:message key='servico.servico' />"		, field: "servico"	       		, width: 150  , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />"	  }
        ,   { id: "solicitanteUnidade"	, name: "<i18n:message key='solicitacaoServico.solicitante' />", field: "solicitanteUnidade"	       	, width: 180,    formatter: AddImgSolicitante, resizable:true , sortable : true  , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />"   }
        ,   { id: "dataHoraSolicitacao"	, name: "<i18n:message key='solicitacaoServico.dataHoraCriacao' />", field: "dataHoraSolicitacao"	, width: 110  , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />" }
        ,   { id: "prioridade"			, name: "<i18n:message key='gerenciaservico.pri' />", field: "prioridade"	       	, width: 50,    formatter: AddImgPrioridade, resizable:true , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />"   }
        ,   { id: "sla"					, name: "<i18n:message key='gerenciaservico.sla' />", field: "sla"					, width: 85,    formatter: AddBotaoMudancaSLA, resizable:true   }
        ,   { id: "farol"			    , name: " "		, field: "farol"	       		, width: 20  , formatter: AddFarol }
        ,   { id: "dataHoraLimite"		, name: "<i18n:message key='solicitacaoServico.prazoLimite' />"	, field: "dataHoraLimite"		, width: 110 , sortable : true  , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />"  }
        ,   { id: "atrasoSLA"       	, name: "<i18n:message key='tarefa.atraso' />", field: "atrasoSLA"           	, width: 70,   	formatter: AddAtraso, resizable:false 	, sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />" 	}
        ,   { id: "situacao"       		, name: "<i18n:message key='solicitacaoServico.situacao' />", field: "situacao"           	, width: 150,	formatter: AddSituacao, resizable:false , sortable : true , headerCssClass : "campoOrdenavel", toolTip:"<i18n:message key='citcorpore.comum.ordenar' />" 	}
        ,   { id: "descricao"			, name: "<i18n:message key='tarefa.tarefa_atual' />", field: "descricao"    	 	, width: 250,   formatter: AddBotoesTarefa }
        ,   { id: "grupoAtual"			, name: "<i18n:message key='citcorpore.comum.grupoExecutor' />", field: "grupoAtual"     		, width: 120, sortable : true, headerCssClass : "campoOrdenavel"   }
        ,   { id: "responsavelAtual"	, name: "<i18n:message key='tarefa.responsavelatual' />", field: "responsavelAtual"  , width: 120    }
        ,   { id: "compartilhamento"	, name: "<i18n:message key='tarefa.compartilhadacom' />", field: "compartilhamento"  , width: 120    }
        ]
    ,   gridOptions = {
    		editable:               false,
	        asyncEditorLoading:     false,
	        enableAddRow:           false,
	        enableCellNavigation: true,
		    enableColumnReorder: true,
		    multiColumnSort: true
        }
    ;

	var dadosGrafico;
	var dadosGrafico2;
	var dadosGrafico4;
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

		//arrayTarefas = JSON.parse(json_tarefas);
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
			var idSolicitacaoServico = "";
			var contrato = "";
			var responsavel = "";
			var servico = "";
			var solicitante = "";
			var prioridade = "";
			var situacao = "";
			var sla = "";
			var dataHoraSolicitacao = "";
			var dataHoraLimite = "";
			var grupoAtual = "";
			var farolAux = "";

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
					grupoNome = '-- '+ i18n_message("citcorpore.comum.aclassificar")+ '--';
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
				
				idSolicitacaoServico = "<a href=\"javascript:exibirDescricao("+solicitacaoDto.idSolicitacaoServico+");\" title='<i18n:message key='gerenciaservico.visualizarDescricao' />'>"+solicitacaoDto.idSolicitacaoServico+"</a> ";
				responsavel = ""+trocaBarra(solicitacaoDto.responsavel);
				contrato = ""+trocaBarra(solicitacaoDto.contrato);
				servico = ""+trocaBarra(solicitacaoDto.servico);
				solicitante = ""+trocaBarra(solicitacaoDto.solicitanteUnidade);
				if (solicitacaoDto.prazoHH < 10)
					sla = "0";
				sla += solicitacaoDto.prazoHH + ":";
				if (solicitacaoDto.prazoMM < 10)
					sla += "0";
				sla += solicitacaoDto.prazoMM;
				prioridade = ""+solicitacaoDto.prioridade;
				dataHoraSolicitacao = solicitacaoDto.dataHoraSolicitacaoStr;
				if (solicitacaoDto.situacaoSLA == "<%=SituacaoSLA.A%>") { 
					dataHoraLimite = solicitacaoDto.dataHoraLimiteStr;
				}
				grupoAtual = trocaBarra(solicitacaoDto.grupoAtual);
				
				if (parseFloat(solicitacaoDto.atrasoSLA) > parseFloat("0,00") && solicitacaoDto.situacao != "<%=SituacaoSolicitacaoServico.Suspensa%>"){
					qtdeAtrasadas++;
				}else if (solicitacaoDto.situacao == "<%=SituacaoSolicitacaoServico.Suspensa%>" && tarefaDto.reativar == 'S'){
					qtdeSuspensas++;
				}else {
					qtdeEmAndamento++;
					if (qtdeItens < 15){
						if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar != 'S' && solicitacaoDto.situacaoSLA == 'A'){
							scriptTemposSLA += "temporizador.addOuvinte(new Solicitacao('tempoRestante" + solicitacaoDto.idSolicitacaoServico + "', " + "'barraProgresso" + solicitacaoDto.idSolicitacaoServico + "', "
							    + "'" + solicitacaoDto.dataHoraInicioSLAStr + "', '" + solicitacaoDto.dataHoraLimiteToString + "'));";
							strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'></label>";
							strTableTemposSLA = strTableTemposSLA + "<div id='barraProgresso" + solicitacaoDto.idSolicitacaoServico + "'></div></td></tr>";
						}else if (solicitacaoDto.slaACombinar && solicitacaoDto.slaACombinar == 'S') {
							strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><i18n:message key='citcorpore.comum.acombinar' /> </font></label>";
						}else if (solicitacaoDto.situacaoSLA == 'N'){
                            strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><i18n:message key='citcorpore.comum.naoIniciado' /> </font></label>";
						}else if (solicitacaoDto.situacaoSLA == 'S'){
                            strTableTemposSLA = strTableTemposSLA + "<tr><td>N.o <b>" + solicitacaoDto.idSolicitacaoServico + "</b>: <label id='tempoRestante" + solicitacaoDto.idSolicitacaoServico + "'><font color='red'><i18n:message key='citcorpore.comum.suspenso' /> </font></label>";
						}
					}
					qtdeItens++;
				}
			} 
	        tarefas[i] = {
			        		 iniciar:			tarefaDto.executar
			        		,executar:			tarefaDto.executar
			        		,delegar:			tarefaDto.delegar
	        				,idSolicitacaoServico:		idSolicitacaoServico
	        			 	,contrato: 			contrato
	        			 	,responsavel: 		responsavel
	        			 	,servico: 			servico
	        			 	,solicitanteUnidade: 		solicitante
	        			 	,prioridade: 		prioridade
	        			 	,dataHoraSolicitacao: dataHoraSolicitacao
	        			 	,descricao: 		trocaBarra(tarefaDto.elementoFluxoDto.documentacao)
			        		,status:	 		""
				        	,atraso:			solicitacaoDto.atrasoSLA
			        		,sla:	 			sla
			        		,atrasoSLA:	 		""
	        			 	,dataHoraLimite: 	dataHoraLimite
	        			 	,responsavelAtual:  tarefaDto.responsavelAtual
	        			 	,compartilhamento:  tarefaDto.compartilhamento
	        			 	,grupoAtual:  grupoAtual
	        			}
		}
		strTableTemposSLA = strTableTemposSLA + '</table>';
		if (qtdeAtrasadas > 0 || qtdeSuspensas > 0 || qtdeEmAndamento > 0){
			var info = '';
			if (qtdeAtrasadas > 0){
				info += ' <font color="red"><b>' + qtdeAtrasadas + '</b> <i18n:message key="solicitacaoServico.solicitacoes_incidentes_atrasado" /></font><br>';
			}
			if (qtdeSuspensas > 0){
				info += ' <b>' + qtdeSuspensas + '</b> <i18n:message key="solicitacaoServico.solicitacoes_incidentes_suspenso" />';
			}
			info = ' <i18n:message key="solicitacaoServico.existem" /><br>' + info + '<br><div id="divTemposSLA" style="height:130px; overflow:auto; border: 2px solid #999999">' + strTableTemposSLA + '</div>';
			if (document.getElementById('fraSolicitacaoServico').src == "about:blank"){
				info = '<table cellpadding="0" cellspacing="0"><tr><td style="width:15px">&nbsp;</td><td style="vertical-align: top;">' + info + '</td><td><div id="divGrafico" ></div></td><td><div id="divGrafico2" ></div></td><td><div id="divGrafico3" ></div></td><td><div id="divGrafico4" ></div></td></tr></table>';
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
				document.formPesquisa.quantidadeAtrasadas.value = qtdeAtrasadas;
				document.formPesquisa.quantidadeTotal.value = (qtdeEmAndamento + qtdeSuspensas + qtdeAtrasadas);
				window.setTimeout(atualizaGrafico4, 1000);
			}
		}
        //gridTarefa = new Slick.Grid( myLayout.contents.south,  tarefas,  colunasTarefa, gridOptions );
        document.getElementById("divConteudoLista").innerHTML = "<div id=\"divConteudoListaInterior\" style=\"height: 100%; width: 100%\"></div>";
        gridTarefa = new Slick.Grid($("#divConteudoListaInterior"), tarefas,  colunasTarefa, gridOptions );

        gridTarefa.onSort.subscribe(function (e, args) {
			var cols = args.sortCols;
			var asc = cols[0].sortAsc;
			if (document.formPesquisa.ordenacaoAsc.value != ''){
				asc = document.formPesquisa.ordenacaoAsc.value;
			}
			document.formPesquisa.nomeCampoOrdenacao.value = cols[0].sortCol.id;
			document.formPesquisa.ordenacaoAsc.value = "" + asc;
			
			/*
			* Codigo Comentado pois compromete a performance do sistema  - RETORNADO POR EMAURI - 18/05*/
			document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>"
			document.formPesquisa.fireEvent("exibeTarefas");
			//
			
	  	 });
	};

	function trocaBarra(txt){
		var x = new String(txt);
		x = x.replace(/{{BARRA}}/g,'\\');
		return x;
	}

	function setinha(){
		/*var headers = document.getElementsByClassName("slick-header");
		for(var i = 0; i < headers.length; i++){
			$("#" + headers[i].id).addClass(eval(document.formPesquisa.ordenacaoAsc.value) ? "slick-sort-indicator-desc" : "slick-sort-indicator-asc");
		}*/
		window.setTimeout(orgColunsSort, 500);
	}
	function orgColunsSort(){
		try{
			gridTarefa.setSortColumn(document.formPesquisa.nomeCampoOrdenacao.value, eval(document.formPesquisa.ordenacaoAsc.value));
		}catch(e){}		
	}

	/*
	 * Funções de ordenação
	 */	

	function encontraNovaPosicao(id, lista, indiceInicial){
		for(var i = indiceInicial; i < lista.length; i++){
			if(lista[i].idSolicitacaoServico == id){
				return i;
			}
		}
		return null;
	}

	function alternaPosicaoArray(indiceOrigem, indiceDestino, lista){
		var aux = lista[indiceDestino];
		
		lista[indiceDestino] = lista[indiceOrigem];
		lista[indiceOrigem] = aux;
	}
	
	//////////////
	
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
	function atualizaGrafico4(){
		document.formPesquisa.fireEvent('exibeGraficoVeloc');
	}

    var myLayout;
    var popup;
	popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	popup2 = new PopupManager(1084, 1084, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	popup2.titulo = "<i18n:message key='citcorpore.comum.pesquisarapida' />";
	
    $(document).ready(function () {
	$("#idSolicitacaoSel").numbersOnly();
	
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
			height: 400,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			}); 
		
		/** 
			
			Algumas alterações estão diretamente no js do plugin (jquery.layout-latest.js).
			Versão do plugin foi atualizada de 30.4 para 30.79 pois no tablet o pane não subia.
			Velocidade do pane alterada(menor) para funcionar corretamente no tablet.
			Pedro.lino
		**/
		
        // create the layout - with data-table wrapper as the layout-content element
        myLayout = $('body').layout({
        	west__size:         0
       // ,	south__size:        390 Nova versão, essa option nao existe mais. Fica dentro de south{}
        ,   center__minHeight: 	200 //limite maximo que o panel pode subir.
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
                                  /*   document.form.fireEvent('exibeTarefas'); */
                                }
        ,	 center__autoResize: true
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
        //	size: 390	
        	 onclose_end: function(){
        		 		myLayout.close("south");
						document.getElementById("tdDown").style.backgroundColor = 'white';
        		},
        		onopen_end: function(){
        				myLayout.open("south");
						document.getElementById("tdDown").style.backgroundColor = 'lightgray';  
					/* 
					* Removido para melhor performance da aplicação
					document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
                    document.form.fireEvent('exibeTarefas');   
                    */   	 
                    	
        		},
        		onresize_end: function(){
        			 document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
                     document.form.fireEvent('exibeTarefas');
                     // AS LINHAS ACIMA ESTAVÃO COMENTADAS.
        		}     		
        	}
        
        });

        $('body > h2.loading').hide(); // hide Loading msg
        <%	if(request.getParameter("idSolicitacao") == null)	{		%>
       		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		<%
		} else	{	%>	
			visualizarSolicitacao(<%= request.getParameter("idSolicitacao") %>);
		<%}%>
		
	   /*  myLayout.hide('north'); */
	    myLayout.hide('west');
	    
	    $("#formInformacoesContato").hide();
    });


	voltar = function(){
		if (document.getElementById('fraSolicitacaoServico').src == "about:blank"){
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
		}else{
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicos/gerenciamentoServicos.load';
		}
	};

	voltarPrincipal = function(){
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
	};
	
	editarSolicitacao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADADTRO_SOLICITACAOSERVICO%>?idSolicitacaoServico="+idSolicitacao+"&escalar=S&alterarSituacao=N";
	};
	
	visualizarSolicitacao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADADTRO_SOLICITACAOSERVICO%>?idSolicitacaoServico="+idSolicitacao+"&escalar=N&alterarSituacao=N&editar=N";
	};

	reclassificarSolicitacao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		myLayout.close("south");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADADTRO_SOLICITACAOSERVICO%>?idSolicitacaoServico="+idSolicitacao+"&reclassificar=S";
	};

	prepararSuspensao = function(idSolicitacao) {
		document.getElementById('tdAvisosSol').innerHTML = '';
		document.getElementById('fraVisao').src = "about:blank";
		document.getElementById('fraVisao').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/suspensaoSolicitacao/suspensaoSolicitacao.load?idSolicitacaoServico="+idSolicitacao;
		$( "#POPUP_VISAO" ).dialog({ height: 500 });
		$( "#POPUP_VISAO" ).dialog({ title: '<i18n:message key="gerenciaservico.suspendersolicitacao" />' });
		$( "#POPUP_VISAO" ).dialog( 'open' );
	};
	
	prepararCalendario = function(idSolicitacao) {
		document.getElementById('fraCalendario').src = "about:blank";
		document.getElementById('fraCalendario').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar=true";
		$( "#POPUP_CALENDARIO" ).dialog({ height: 600, width: 1000 });
		$( "#POPUP_CALENDARIO" ).dialog({ title: '<i18n:message key="agendaAtividade.agendaAtividade" />' });
		$( "#POPUP_CALENDARIO" ).dialog( 'open' );
	};	

	reativarSolicitacao = function(idSolicitacao) {
		if (!confirm(i18n_message("gerencia.confirm.reativacaoSolicitacao"))) 
			return;
		document.form.idSolicitacao.value = idSolicitacao;
		document.form.fireEvent('reativaSolicitacao');
	};
	
	capturarTarefa = function(responsavelAtual, idTarefa) {
		var msg = "";
		if (responsavelAtual == '')
			msg = i18n_message("gerencia.confirm.atribuicaotarefa") + " '<%=nomeUsuario%>'  ?";
		else 	
			msg = i18n_message("gerencia.confirm.atribuicaotarefa_1") +" " + responsavelAtual + " " + i18n_message("gerencia.confirm.atribuicaotarefa_2")  +" '<%=nomeUsuario%>' "+ i18n_message("gerencia.confirm.atribuicaotarefa_3");
			
		if (!confirm(msg)) 
			return;
		document.form.idTarefa.value = idTarefa;
		document.form.fireEvent('capturaTarefa');
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

	exibirDescricao = function(idSolicitacao) {
		$( "#POPUP_DESCRICAO" ).dialog({ title: '<i18n:message key="visao.idSolicitacao" />: '+idSolicitacao });
		document.form.idSolicitacaoServicoDescricao.value = idSolicitacao;
		document.form.fireEvent('restoreSolicitacaoServico');
		$( "#POPUP_DESCRICAO" ).dialog( 'open' );
		document.form.idSolicitacaoServicoDescricao.value = "";
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
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADADTRO_SOLICITACAOSERVICO%>";
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
		myLayout.close("south");
		//myLayout.open("south");
		//myLayout.open("west");
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.form.fireEvent('exibeTarefas');
	};
	
	atualizarListaTarefas = function() {
		if($('#fraSolicitacaoServico').src == 'about:blank')
			myLayout.open("south");

		document.getElementById('divConteudoLista').innerHTML = "<div id='divConteudoListaInterior' style='height: 100%; width: 100%'><i18n:message key='citcorpore.comum.aguardecarregando' /></div>";
		document.form.numeroContratoSel.value = document.formPesquisa.numeroContratoSel.value;
		document.form.idSolicitacaoSel.value = document.formPesquisa.idSolicitacaoSel.value;
		document.form.descricaoSolicitacao.value = document.formPesquisa.descricaoSolicitacao.value;
		document.form.responsavelAtual.value = document.formPesquisa.responsavelAtual.value;
		document.form.idTipoDemandaServico.value = document.formPesquisa.idTipoDemandaServico.value;
		document.form.nomeCampoOrdenacao.value = document.formPesquisa.nomeCampoOrdenacao.value;
		document.form.ordenacaoAsc.value = document.formPesquisa.ordenacaoAsc.value == "false" ? "true" : "false";
		document.form.grupoAtual.value = document.formPesquisa.grupoAtual.value;
		document.form.solicitanteUnidade.value = document.formPesquisa.solicitanteUnidade.value;
		document.form.fireEvent('exibeTarefas')
	}
	
	abrePopup = function(obj,func) {
		popup.abrePopup('usuario','()');
	}
	
	abrePopupPesquisa = function( ) {
		
		$( "#popupCadastroRapido" ).dialog({
			title: '<i18n:message key="citcorpore.comum.cadastrorapido" />',
			width: 1250,
			height: 570,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
	
			}); 
		document.getElementById('popupCadastroRapido').style.overflow = "hidden";
		
		popup2.abrePopup('pesquisaSolicitacoesServicos','()');
		
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
		}else{
			myLayout.close(areaName);
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
					showDataLabels : true,
					 startAngle: -90
					
				}
			},
			legend : {
				show : true,
				location : 'e',
				xoffset: 15,        // pixel offset of the legend box from the x (or x2) axis.
			    yoffset: 12 
			}
		
		});
	}	
	
		carregarModalDuplicarSolicitacao = function(idSolicitacaoServico){
			
			document.formInformacoesContato.idSolicitante.value = '';
			document.formInformacoesContato.idOrigem.value = '';
			document.formInformacoesContato.solicitante.value = '';
			document.formInformacoesContato.idSolicitacaoServico.value = '';
			document.formInformacoesContato.idSolicitante.value = '';
			document.formInformacoesContato.nomecontato.value = '';
			document.formInformacoesContato.emailcontato.value = '';
			document.formInformacoesContato.telefonecontato.value = '';
			document.formInformacoesContato.idUnidade.value = '';
			document.formInformacoesContato.idLocalidade.value = '';
			document.formInformacoesContato.observacao.value = '';
			document.formInformacoesContato.idTipoDemandaServico.value = '';
			
			document.formInformacoesContato.idSolicitacaoServico.value = idSolicitacaoServico;
			
			document.formInformacoesContato.fireEvent('carregarModalDuplicarSolicitacao');
			
			$("#formInformacoesContato" ).dialog({
				title: '<i18n:message key="solicitacaoServico.informacoesSolicitante" />',
				width: 950,
				height: 450,
				modal: true,
				autoOpen: false,
				resizable: true,
				show: "fade",
				hide: "fade"
				/* buttons: {
					 "<i18n:message key='citcorpore.comum.fechar' />": function() {
						 $( this ).dialog( "close" );
					}
				} */
			}); 
			
			$("#formInformacoesContato").dialog('open');
			
		}
		
		function LOOKUP_SOLICITANTE_select(id, desc){
			document.formInformacoesContato.idSolicitante.value = id;
			document.formInformacoesContato.fireEvent("restauraSolicitante");
		}
		
		$(function() {
			$("#addSolicitante").click(function() {
				if (document.formInformacoesContato.idContrato.value == ''){
					alert('Informe o contrato!');
					return;
				}
				
				$("#POPUP_SOLICITANTE").dialog("open");
			});
			
			$("#POPUP_SOLICITANTE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_DESCRICAO").dialog({
				autoOpen : false,
				width : 500,
				height : 400,
				modal : true
			});
			
			$("#formIncidentesRelacionados").dialog({
				autoOpen : false,
				width : 1000,
				height : 580,
				modal : true
			});
			
			$("#telefonecontato").mask("(999) 9999-9999");
		});
		
	 	function fecharPopup(popup){
			$(popup).dialog('close');
		}
	 	
		exibirSubSolicitacoes = function(idSolicitacaoServico){
			document.formIncidentesRelacionados.idSolicitacaoIncRel.value = idSolicitacaoServico; 
			inicializarTemporizadorRel1();
			document.formIncidentesRelacionados.fireEvent("abrirListaDeSubSolicitacoes");
		}
		
		fecharPopuListaSubSolicitacoes = function(){
			$('#formIncidentesRelacionados').dialog('close');
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
		
		duplicarSolicitacao = function(){
			
			if (DEFINEALLPAGES_validarForm(document.formInformacoesContato)){
				document.formInformacoesContato.fireEvent("duplicarSolicitacao");
			}
		}
		
		/* Atualizacao de pagina automatica. */
		function atualizaPagina(){
      		if ( document.getElementById('chkAtualiza').checked ) {
      			atualizarListaTarefas();
      		} 
			
      	}
		
		function abreEstatistica(){
			document.getElementById('divInterna_divDemaisEstat').innerHTML = '<i18n:message key='citcorpore.comum.aguarde' />';
			document.formPesquisa.fireEvent('mostraEstatAdicional');			
			 /* document.getElementById('divDemaisEstat').style.display='block';  */
			  $("#divDemaisEstat").hide();  
			 $('#divDemaisEstat').toggleClass("active").slideToggle("slow"); 
       			return ; //Prevent the browser jump to the link anchor 
			
			
		}
		
		/* Agendador de atualizacao. */
		window.setInterval(atualizaPagina, 30000);
	
    </script>
<style type="text/css">
#slide{
padding-top: 0px;
padding-left: 50px;
padding-right: 50px;
padding-bottom: 0px;
border-bottom: 2px solid #DDDDDD;
border-left: 2px solid #DDDDDD;
border-right: 2px solid #DDDDDD;
cursor: pointer;
margin-left: 10%;
position: fixed;
display: block;
margin-top: -7px!important;
border-radius: 0px 0px 10px 10px; /* para todos os browsers recentes (e decentes) */
-moz-border-radius: 0px 0px 10px 10px; /* para versões antigas do Mozilla */
-webkit-border-radius: 0px 0px 10px 10px; /* para versões antigas do Safari */
background-color: #F2F2F2;
box-shadow: 0 0 8px rgba(0, 0, 0, 0.3)
}
#slide:hover{
padding-top: 0px;
padding-left: 50px;
padding-right: 50px;
padding-bottom: 0px;
border-bottom: 2px solid #DDDDDD;
border-left: 2px solid #DDDDDD;
border-right: 2px solid #DDDDDD;
cursor: pointer;
box-shadow: 0 0 8px #A6CE39;

}
</style>
</head>
<body>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">	</cit:janelaAguarde>
	<h2 class="loading">Carregando...</h2>
	<!-- <div class="ui-layout-north hidden"></div> -->
	
	<div id="divDemaisEstat" style="position: absolute;top:0px;left:0px;z-index: 100000; height:500px;width:900px; border:2px solid #DDDDDD; background-color: white; display:none;box-shadow: 0 0 20px rgba(0, 0, 0, 0.3) ">
		<div><button  style="margin-top: 52%; left: 92%" type='button' class="light img_icon has_text" onclick='$("#divDemaisEstat").hide(); '>Fechar</button></div>
		<div id='divInterna_divDemaisEstat' style='overflow: auto; width: 91%; height: 91%'>
		</div>
	</div>
	
	<div id="divControleLayout" style="position: absolute;top:0px;left:0px;z-index: 1;display: none ">
		<table cellpadding='0' cellspacing='0'>
			<tr>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<%-- <td style='text-align: center;'><img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/setaAbreAux.png" border='0' style='cursor:pointer' onclick='abreEstatistica()'/></td> --%>
				<td>
					<div id="slide" onclick='abreEstatistica()'>
						<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/template_new/images/icons/small/grey/list.png" border='0' style='cursor:pointer; display: block;'/>				
					</div>
				</td>
			</tr>
			<tr>
				<td style='vertical-align: top;'>
				<button  type="button" class="light img_icon has_text" style='text-align: right; margin-left: 2px' onclick="voltar()" title="<i18n:message key="citcorpore.comum.voltar" />">
						<img border="0" title="<i18n:message key='citcorpore.comum.voltarprincipal' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/back.png" /><span style="padding-left: 0px !important;"><i18n:message key="citcorpore.comum.voltar" /></span>
					</button>	
					
					<button  type="button" class="light img_icon has_text" style='text-align: right; margin-left: 2px' onclick="voltarPrincipal()" title="<i18n:message key="mapaDesenhoServico.voltarParaPaginaInicial" />">
						<img border="0" title="<i18n:message key='mapaDesenhoServico.voltarParaPaginaInicial' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/home2.png"  style="padding-left:3px;"/><span style="padding-left: 0px !important;"></span>
					</button>

					<table>
						<tr>
							<td style='height: 15px; width: 30px; border:1px solid black; background-color: white;'>
								
							</td>
						</tr>
						<tr>
							<td id='tdDown' onclick='controleArea("tdDown", "south")' style='height: 25px; width: 30px; border:1px solid black; background-color: lightgray; cursor: pointer' title='<i18n:message key="citcorpore.comum.controlalayout" />'>
								
							</td>			
						</tr>
					</table>
					<table>	
						<tr>
							<td>
								<img border="0" title="<i18n:message key='agendaAtividade.agendaAtividade' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/template_new/images/icons/large/grey/day_calendar.png" onclick="prepararCalendario()" style="cursor:pointer" />
							</td>
						</tr>					
					</table>
				</td>
				<td>
					&nbsp;
				</td>
				<%-- <td style='vertical-align: top;'>
					<button  type="button" class="light img_icon has_text" style='text-align: right;' onclick="voltar()" title="<i18n:message key="citcorpore.comum.voltarprincipal" />">
						<img border="0" title="<i18n:message key='citcorpore.comum.voltarprincipal' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/back.png" /><span ><i18n:message key="citcorpore.comum.voltar" /></span>
					</button>				
				</td> --%>
				<td id='tdAvisosSol' style='padding-top: 1.5%!important;'>
				</td>				
			</tr>
		</table>	
	</div>
	
	<!-- <div class="ui-layout-west hidden">
	    <div class="ui-layout-content"></div>
	</div> -->

	<div class="ui-layout-center hidden">
	    <table width='100%' height='100%' style='width: 100%; height: 100%'>
	         <tr>
	         	<td width='100%' style='width: 100%; height: 100%'>
					<iframe id='fraSolicitacaoServico' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>
				</td>
	         	<!-- <td width='50%'>
					<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
				</td> -->
			</tr>
		</table>		
	</div>	

	<!-- <div class="ui-layout-east hidden"></div> -->
	<div style="z-index: 999 !important" class="ui-layout-south hidden">
	    <div >	
	    	<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" >
	    	<form name='formPesquisa' id='formPesquisa' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicos/gerenciamentoServicos'>
	    		<input type="hidden" name="nomeCampoOrdenacao" id="nomeCampoOrdenacao" />
	    		<input type="hidden" name="ordenacaoAsc" id="ordenacaoAsc" />
	    		
	    		<input type="hidden" name="quantidadeAtrasadas" id="quantidadeAtrasadas" />
	    		<input type="hidden" name="quantidadeTotal" id="quantidadeTotal" />
	    		
				<div class='space' style="float: left;  width: 75%;">
					<table cellpadding="0" cellspacing="3" style="float: left;">
						<tr>
							<td style='vertical-align: middle;'>
								<i18n:message key="contrato.contrato"/>
							</td>
							<td style='vertical-align: middle;'>
								<i18n:message key="gerenciaservico.numerosolicitacao"/>
							</td>
							<td style='vertical-align: middle;'>
								<i18n:message key="solicitacaoServico.descricao"/>
							</td>
							<td style='vertical-align: middle;'>
								 <i18n:message key="citcorpore.comum.tipo"/> 
							</td>
							<td style='vertical-align: middle;'>
								<i18n:message key="citcorpore.comum.responsavel"/>
							</td>
							<td style='vertical-align: middle;'>
								<i18n:message key="citcorpore.comum.grupoExecutor"/>
							</td>
							<td style='vertical-align: middle;'>
								<i18n:message key="solicitacaoServico.solicitante"/>
							</td>
							<td>
								&nbsp;
							</td>
							<td style='vertical-align: middle;'>
							</td>
						</tr>
						<tr>
							<td style='vertical-align: top;'>
								<input type='text' name="numeroContratoSel" id="numeroContratoSel" size="5" style='border:1px solid #B3B3B3; height:24px;' onkeydown="if ( event.keyCode == 13 ) atualizarListaTarefas();"/>
							</td>

							<td style='vertical-align: top;'>
								<input type='text' name="idSolicitacaoSel" id="idSolicitacaoSel" size="12" style="border:1px solid #B3B3B3;height:24px;" onkeydown="if ( event.keyCode == 13 ) atualizarListaTarefas();"/>
							</td>
							<td style='vertical-align: top;'>
								<input type='text' name="descricaoSolicitacao" id="descricaoSolicitacao" size="50" style="border:1px solid #B3B3B3;height:24px; /* width: 400px !important */" onkeydown="if ( event.keyCode == 13 ) atualizarListaTarefas();"/>
							</td>
							<td style='vertical-align: top;'>
								<select name='idTipoDemandaServico' id='idTipoDemandaServico'></select>
							</td>
							<td style='vertical-align: top;'>
								<select name='responsavelAtual' id='responsavelAtual'></select>
							</td>
							<td style='vertical-align: top;'>
								<select name='grupoAtual' id='grupoAtual'></select>
							</td>
							<td style='vertical-align: top;'>
								<select name='solicitanteUnidade' id='solicitanteUnidade'></select>
							</td>
							<td style='vertical-align: middle;'>		 
								<button type="button" title="<i18n:message key="citcorpore.comum.pesquisar"/>" class="T-I J-J5-Ji T-I-Js-Gs ar7 mw T-I-ax7 L3 T-I-JO gbqfb"  
									onclick="atualizarListaTarefas()">
									<div class="asa">
										<span class="J-J5-Ji ask">&nbsp;</span>
										<div class="asb gbqfi T-I-J3 J-J5-Ji"></div>
									</div>
								</button>
							</td>
							<td style='vertical-align: top;'>
								
							</td>																		
							<td style='vertical-align: middle;'>
								<img border="0" title="<i18n:message key='citcorpore.comum.limpar'/>" style="cursor:pointer" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/clear.png" onclick="document.formPesquisa.clear();atualizarListaTarefas()"/>
							</td>
						</tr>
					</table>
				</div>
	    		<div class='space' style="float: right;  width: 20%;">
					<button type='button' title="<i18n:message key='solicitacaoServico.cadastrosolicitacao'/>" class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr" onclick="abrirSolicitacao()">
						<div class="asa">
							<span class=""><i18n:message key="gerenciaservico.novasolicitacao"/></span>
							<div class="G-asx T-I-J3 J-J5-Ji"></div>
						</div>
					</button>
					
					<button type='button' title="<i18n:message key='gerenciaservico.pesquisasol'/>" class="T-I J-J5-Ji ar7 nf T-I-ax7 L3 T-I-JO T-I-hvr" onclick="abrePopupPesquisa()">
						<div class="asa">
							<span class=""><i18n:message key="gerenciaservico.pesquisasol"/></span>
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
				</div>
			</form>				
	    	</div>	
	    </div>
		<div id='divConteudoLista' class="ui-layout-content" style="height: 350px; border: 2px solid #E6ECEF">
	    	<div id='divConteudoListaInterior' style='height: 100%; width: 100%'></div>
	    </div>
	</div>

	
	<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciamentoServicos/gerenciamentoServicos'>
		<input type='hidden' name='idFluxo'/>
		<input type='hidden' name='idVisao'/>
		<input type='hidden' name='idTarefa'/>
		<input type='hidden' name='acaoFluxo'/>
		<input type='hidden' name='idUsuarioDestino'/>
		<input type='hidden' name='numeroContratoSel'/>
		<input type='hidden' name='idSolicitacaoSel'/>
		<input type='hidden' name='idTipoDemandaServico'/>
		<input type='hidden' name='responsavelAtual'/>
		<input type='hidden' name='idSolicitacao' id='idSolicitacaoForm'/>	
	    <input type="hidden" name="nomeCampoOrdenacao" id="nomeCampoOrdenacao2" />
	    <input type="hidden" name="ordenacaoAsc" id="ordenacaoAsc2" />
	    <input type="hidden" name="descricaoSolicitacao" id="descricaoSolicitacao" />
		<input type='hidden' name='grupoAtual'/>
		<input type='hidden' name='solicitanteUnidade'/>
		<input type='hidden' name='idSolicitacaoServicoDescricao'/>
		
	</form>
	
	<div id="POPUP_VISAO" style="overflow: hidden">
		<iframe id='fraVisao' src='about:blank' width="100%" height="100%"></iframe>
	</div>
	
	<div id="POPUP_CALENDARIO" style="overflow: hidden">
		<iframe id='fraCalendario' src='about:blank' width="100%" height="100%"></iframe>
	</div>	
		
	<div id="popupCadastroRapido">
          <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
	</div>
	
	<form name="formInformacoesContato" id="formInformacoesContato" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/solicitacaoServicoMultiContratos/solicitacaoServicoMultiContratos'>
		<input type='hidden' name='idSolicitante' id='idSolicitante' />
		<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' />
		<input type='hidden' name='idContrato' id='idContrato' />	
		<input type='hidden' name='idTipoDemandaServico' id='idTipoDemandaServico' />
	
			<div class="col_50">
				<fieldset style="height: 70px;">
				<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="origemAtendimento.origem" /></label>
					<div>
						<select name='idOrigem' class="Valid[Required] Description[Origem]"></select>
					</div>
				</fieldset>
			</div>
			<div class="col_50">
				<fieldset style="height: 70px;">
					<label class="campoObrigatorio" style="cursor: pointer; float: left; width: 100px">
						<i18n:message key="solicitacaoServico.solicitante" />
						<img id="btHistoricoSolicitante" style="cursor: pointer; display: none;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png" />	    
					</label>
					<div>
						<input id="addSolicitante" id="solicitante" type='text' readonly="readonly" name='solicitante' maxlength="80" class="Valid[Required] Description[Solicitante]" />
					</div>
				</fieldset>
			</div>
			<div class="col_50">
				<fieldset>
					<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="solicitacaoServico.nomeDoContato" /></label>
					<div>
						<input id="nomecontato" type='text' name='nomecontato' maxlength="70" class="Valid[Required] Description[Nome do Contato]" />
					</div>
				</fieldset>
			</div>
			<div class="col_50">
				<fieldset>
					<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message
							key="solicitacaoServico.emailContato" /></label>
					<div>
						<input id="emailcontato" type='text'  name="emailcontato" maxlength="120" class="Valid[Email] Description[Email do Contato]" />
					</div>
				</fieldset>
			</div>
			<div class="col_33">
				<fieldset>
					<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="solicitacaoServico.telefoneDoContato" /></label>
					<div>
						<input id="telefonecontato" type='text' name="telefonecontato" maxlength="13" class="Valid[Required] Description[Telefone do Contato]" />
					</div>
				</fieldset>
			</div>
			<div class="col_33">
				<fieldset style="height: 55px" >
					<label class="tooltip_bottom campoObrigatorio" style="cursor: pointer;" title="<i18n:message key="colaborador.cadastroUnidade"/>" >
						<i18n:message key="unidade.unidade"/>
					 </label>
					<div>
						<select name="idUnidade" id="idUnidade" class="Valid[Required] Description[Unidade]" onchange = "document.form.fireEvent('preencherComboLocalidade');"></select>
					</div>
			    </fieldset>
			</div>
			<div class="col_33">
				<fieldset style="height: 55px" >
					<label class="tooltip_bottom " style="cursor: pointer;" title="<i18n:message key="colaborador.cadastroUnidade"/>" >
						<i18n:message key="solicitacaoServico.localidadeFisica"/>
					 </label>
						<div>
							<select name="idLocalidade" id='idLocalidade'></select>
						</div>
			    </fieldset>
			</div>
			<div class="col_50" >
				<fieldset>
					<label style="cursor: pointer;"><i18n:message key="solicitacaoServico.observacao" /></label>
					<div style="height: 75px;">
						<textarea id="observacao" name="observacao" rows="4" cols="70"></textarea>
					</div>
				</fieldset>
			</div>
			<div class="col_100" style="padding-top: 5px;">
				<button type='button' name='btnGravar' class="light"  onclick="duplicarSolicitacao();">
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span><i18n:message key="citcorpore.comum.gravar"/></span>
				</button>
			</div>
	</form>
	
	<div id="POPUP_SOLICITANTE" title="<i18n:message key="citcorpore.comum.pesquisacolaborador" />">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div  align="right">
							<label  style="cursor: pointer; ">
									<i18n:message key="solicitacaoServico.solicitante" />
									<img id='botaoSolicitante' src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="chamaPopupCadastroSol()">
									<img id="btHistoricoSolicitante" style="cursor: pointer; display: none;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/month_calendar.png">	
							</label>
						</div>
						<form name='formPesquisaColaborador' style="width: 540px">
							<cit:findField formName='formPesquisaColaborador' lockupName='LOOKUP_SOLICITANTE_CONTRATO' id='LOOKUP_SOLICITANTE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
		<div id="POPUP_DESCRICAO">
		<div class="box grid_16 tabs">
			<div class="toggle_container">
					<div class="section">
						<div  align="left" >
						    <label style="cursor: pointer;font-size: 20px;"><i18n:message key="citcorpore.comum.descricao" />:
							<!-- <textarea id="descricaoSolicitacaoVisualizar" name="descricaoSolicitacaoVisualizar" rows="20" readonly="readonly" cols="90" ></textarea> -->
							</label>
							<div style="padding-top: 10px;" id="descricaoSolicitacaoVisualizar"></div>
						</div>
					</div>
			</div>
		</div>
	</div>
	
	
	<form id="formIncidentesRelacionados" name='formIncidentesRelacionados' method="post" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/incidentesRelacionados/incidentesRelacionados'>
		<input type='hidden' name='idSolicitacaoIncRel' value=''/>
		
		<div id="tabelaIncidentesRelacionados">
			
		</div>
		
		<div class="col_100" style="padding-top: 20px;">
			<button type='button' name='btnGravar' class="light" id="btRelacionarSolicitacaoFechar" onclick="fecharPopuListaSubSolicitacoes()">
				<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span><i18n:message key="citcorpore.comum.fechar"/></span>
			</button>
		</div>
	</form>
	
	<div id="POPUP_Telefonia" style="overflow: hidden;">

	</div>
		
</body>
</html>