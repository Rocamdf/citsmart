<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        String id = request.getParameter("id");
    %>
    <%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
    <%@include file="/include/security/security.jsp" %>
    <title><i18n:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/noCache/noCache.jsp" %>
    <%@include file="/include/menu/menuConfig.jsp" %>
    <%@include file="/include/header.jsp" %>
    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>   
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoPessoalDTO.js"></script>

    <style type="text/css">
        .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
        }
        .table {
            border-left:1px solid #ddd;
            width: 100%;
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
        
        .table1 {
        }
        
        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }
        
        .table1 td {
        }   
             
         div#main_container {
            margin: 0px 0px 0px 0px;
        } 
                
        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;
            
            letter-spacing: -4px;
        }
    </style>

    <script>
	    function limpar(){
	   		window.location = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/requisicaoPessoal/requisicaoPessoal.load';
	   	}    
	    function desabilitarTela() {
	        var f = document.form;
	        for(i=0;i<f.length;i++){
	            var el =  f.elements[i];
	            if (el.type != 'hidden') { 
	                if (el.disabled != null && el.disabled != undefined) {
	                    el.disabled = 'disabled';
	                }
	            }
	        }  
	    }    
	    addEvent(window, "load", load, false);
	    function load(){        
	        document.form.afterLoad = function () {
	            if (document.form.editar.value != '' && document.form.editar.value != 'S')
	                desabilitarTela();
	            dataAtual();
	            parent.escondeJanelaAguarde();                    
	        }    
	    }
	
	    function validar() {
	        return document.form.validate();
	    } 
	
	</script>
</head>

<body> 

<div class="flat_area grid_16">
	<h2><i18n:message key="Requisição de pessoal"/></h2>						
</div>
<div class="box grid_16 tabs">
	<ul class="tab_header clearfix">
		<li>
			<a href="#tabs-1"><i18n:message key="Dados gerais"/></a>
		</li>
	</ul>	    
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/jornadaDeTrabalho/jornadaDeTrabalho'>
						</form>
                    </div>
            </div>  
        </div>
       </div>
<div id="POPUP_SELECAO" title="Seleção" style="overflow: hidden">
   <form name='formSelecao' method="POST" action=''>
		<input type='hidden' name='id'/>
		<input type='hidden' name='sel'/>
		<input type='hidden' name='objeto' id='objetoSel'/>
		<div id='divSelecao' style='height:180px;width:340px;overflow: auto;'>
			<table id="tblSelecao" class="table">
				<tr>
					<th style="width: 1%;" class="linhaSubtituloGrid">&nbsp;</th>
					<th style="width: 50%;" class="linhaSubtituloGrid">Descrição</th>
					<th style="width: 49%;" class="linhaSubtituloGrid">Detalhes</th>
				</tr>
			</table>
		</div>
     	<table cellpadding="0" cellspacing="0">
          	<tr>
           		<td>
           		     &nbsp;
               		<input type='button' name='btnSelecionar' value='Selecionar' onclick='atribuirSelecao()'/>
               		<input type='button' name='btnFechar' value='Fechar' onclick='$("#POPUP_SELECAO").dialog("close");'/>
           		</td>
          	</tr>                                                                                      
      	</table>
   </form>
</div>

</body>

</html>
