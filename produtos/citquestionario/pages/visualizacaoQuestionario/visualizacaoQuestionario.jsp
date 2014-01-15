<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="br.com.citframework.util.Constantes"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String tabela100 = request.getParameter("tabela100");
	if (tabela100 == null || tabela100.equalsIgnoreCase("")){
		tabela100 = "870px";
	}else{
		if (tabela100.equalsIgnoreCase("true")){
			tabela100 = "100%";
		}else{
			tabela100 = "800px";
		}
	} 
	String subForm = request.getParameter("subForm");
	if (subForm == null || subForm.equalsIgnoreCase("")){
		subForm = "N";
	}
	 
	String idPessoaVisualizacaoHistCampos = (String)request.getAttribute("idPessoaVisualizacaoHistCampos");
	if (idPessoaVisualizacaoHistCampos == null){
		idPessoaVisualizacaoHistCampos = "";
	}
	
	String bufferAposLoad = (String)request.getAttribute("bufferAposLoad");
	if (bufferAposLoad == null){
		bufferAposLoad = "";
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citquestionario.bean.LinhaSpoolQuestionario"%>
<%@page import="java.util.ArrayList"%>
<html xmlns="http://www.w3.org/1999/xhtml">
 
<head>   

<style type="text/css">
body{
	background-image: url("") !important;
	background-color: white !important;
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
	  border-bottom: 2px double #F8F8F8 !important;
	  padding: 6px 10px !important;
	}
	
	.ml2 { margin-left: 2px !important; } 
	.mr5 { margin-right: 5px !important; }
</style>

	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
	
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/boxover.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/CollectionUtils.js"></script>
	
	<script>
		var oldLink = null;
	</script>

	<script>
	function encode64_questionario(input) {
		// base64 strings are 4/3 larger than the original string
		if(window.btoa) return window.btoa(input);
		var _keyStr = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=";
		var output = new Array( Math.floor( (input.length + 2) / 3 ) * 4 );
		var chr1, chr2, chr3;
		var enc1, enc2, enc3, enc4;
		var i = 0, p = 0;
	
		do {
			chr1 = input.charCodeAt(i++);
			chr2 = input.charCodeAt(i++);
			chr3 = input.charCodeAt(i++);
	
			enc1 = chr1 >> 2;
			enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);
			enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);
			enc4 = chr3 & 63;
	
			if (isNaN(chr2)) {
				enc3 = enc4 = 64;
			} else if (isNaN(chr3)) {
				enc4 = 64;
			}
	
			output[p++] = _keyStr.charAt(enc1);
			output[p++] = _keyStr.charAt(enc2);
			output[p++] = _keyStr.charAt(enc3);
			output[p++] = _keyStr.charAt(enc4);
		} while (i < input.length);
	
		return output.join('');
	}	
	function fecharIFrame(frameNameParm){
		$(frameNameParm).style.display='none';
	}
	function executarAposCarregar(){
		<%=bufferAposLoad%>
	}
	HTMLUtils.addEvent(window, "load", executarAposCarregar, false);
	HTMLUtils.addEvent(window, "load", load_page, false);
    function load_page(){    
		try{
            parent.escondeJanelaAguarde();                    
        }catch(e){} 
    }	
    function validar(){
		if (!validacaoGeral()){
			return false;
		}
		if (!document.formQuestionario.validate()){
			return false;
		}
		try{
			mostraAguardeValidacaoQuestionario();
		}catch(e){
		}			
		document.formQuestionario.fireEvent('validate');  
		return true;
    }
    function getObjetoSerializado(){
<%--     	document.formQuestionario.action = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/visualizacaoQuestionario/visualizacaoQuestionario.load';    	 --%>
    	document.formQuestionario.action = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/solicitacaoServicoQuestionario/solicitacaoServicoQuestionario.load';    	
    	//alert(document.formQuestionario.action);
    	document.formQuestionario.submit();
    	return '';
    }
	</script>
	<%@include file="/produtos/citquestionario/include/includeHeadCITQuestionario.jsp"%>		
</head>
 
<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>


   
<body>   
	<%@include file="/produtos/citquestionario/include/includeTOPCITQuestionario.jsp"%> 
	
	<form name='formQuestionario' id='formQuestionario' method="post" action="<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/contratoQuestionarios/contratoQuestionarios">
		<input type='hidden' name='idContrato'/>
		<input type='hidden' name='idQuestionario'/> 
		<input type='hidden' name='aba'/>
		<input type='hidden' name='idItem'/>  
		<input type='hidden' name='idContratoQuestionario'/>
		<input type='hidden' name='dataQuestionario'/>
		<input type='hidden' name='dataAtual' value='<%=UtilDatas.dateToSTR(UtilDatas.getDataAtual())%>'/>
		<input type='hidden' name='situacao'/>

		<input type='hidden' name='subForm' value="<%=subForm%>"/>
  
		<input type='hidden' name='produtos'/>
			
        <select style='display:none' name='idQuestoesCalculadas' id='idQuestoesCalculadas' >
        </select>
	<div style="background-color: white !important; width:100%" >
		<table name="tblQuestoes" border="0" id="tblQuestoes" width="50%" style="background-color: white !important; margin: 0px 10px 0px 10px">
			<%
			Collection colLinhas = (Collection)request.getAttribute("linhasQuestionario");
			if (colLinhas != null){
				for(Iterator it = colLinhas.iterator(); it.hasNext();){
					LinhaSpoolQuestionario linhaQuestionario = (LinhaSpoolQuestionario)it.next();
					if (linhaQuestionario.isGenerateTR()){
						//out.println("<tr><td>");
					}
					
					out.println(linhaQuestionario.getLinha());
					
					if (linhaQuestionario.isGenerateTR()){
						//out.println("</td></tr>");
					}			
				}
			}
			%> 
		</table>
		</div>
	</form>
	
	<%    
	String modo = request.getParameter("modo");
	if ("somenteleitura".equalsIgnoreCase(modo)){  
	%>
	<script>
		HTMLUtils.lockForm(document.formQuestionario);
	</script>
	<%
	}
	%>	
<script>
</script>

<%@include file="/produtos/citquestionario/include/includeBottomCITQuestionario.jsp"%> 
</body>
</html>
	