<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
    	<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.min.js" type="text/javascript"></script>
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
    	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
		%>
    	
		
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
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

    </style>	
		
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery-ui.min.js" type="text/javascript"></script>
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/touchPunch/jquery.ui.touch-punch.min.js" type="text/javascript"></script>
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/uitotop/js/jquery.ui.totop.js" type="text/javascript"></script>
		
		
		<script src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.MetaData.js' type="text/javascript" language="javascript"></script>
		<script src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.rating.js' type="text/javascript" language="javascript"></script>
		<link href='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/star-rating/jquery.rating.css' type="text/css" rel="stylesheet"/>
		
		<style type="text/css">
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
		</style>
	    <script>
	    
	    $(function(){
			$("#pesquisaBaseConhecimento").dialog({
				title: 'Pesquisa Base de Conhecimento',
				width: 1300,
				height: 650,
				modal: true,
				autoOpen: true,
				resizable: false,
				show: "fade",
				hide: "fade",
				beforeClose: function(){
					fechaPopupIframe();
				}
					
			});
    	});
	    function fechaPopupIframe(){
	    	<%	if(request.getParameter("id") == null)	{		%>
	    			window.location = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load";
	    	<%
	    	} else	{	%>	
	    		window.close();
			<%}%>
	    }
	    </script>
    
	    
	</head>

	<body>
		<div class="clearfix ui-tabs-nav ui-helper-reset ui-helper-clearfix ui-widget-header ui-corner-all" style="height: 40px; *height: 40px; ">
		
		<form name='formPesquisa' id='formPesquisa'>	
				
			<div id="pesquisaBaseConhecimento">
				<%	if(request.getParameter("id") == null)	{		%>
	    			<iframe id="frameCadastroRapido" name="frameCadastroRapido"
	          		  src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimentoView/baseConhecimentoView.load" width="100%" height="100%"></iframe>
		    	<%
		    	} else	{	%>	
					<iframe id="frameCadastroRapido" name="frameCadastroRapido"	
					src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/baseConhecimentoView/baseConhecimentoView.load?id=<%=request.getParameter("id")%>&origem=portal" width="100%" height="100%"></iframe>				<%}%>
				
			</div>
		</form>
		</div>
	</body>

</html>

