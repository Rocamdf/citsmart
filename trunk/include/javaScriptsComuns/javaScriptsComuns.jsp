<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
<%@taglib uri="/tags/i18n" prefix="i18n" %>
<script>var URL_INITIAL = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/';</script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/Temporizador.js?nocache=<%=new java.util.Date().toString()%>"></script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/defines.js?nocache=<%=new java.util.Date().toString()%>"></script>	
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/tabber.js"></script>	
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/LookupFind.js"></script>	
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/ObjectUtils.js"></script>	
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/DateTimeUtil.js"></script>
<script charset="ISO-8859-1"  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/ValidacaoUtils.js"></script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/StringUtils.js"></script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/NumberUtil.js"></script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/AjaxUtils.js?nocache=<%=new java.util.Date().toString()%>"></script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/HTMLUtils.js"></script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/FormatUtils.js"></script>
<script  type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/Thread.js"></script>
<script>
	
	/* Desenvolvedor: Euler.Ramos  Data: 23/10/2013 - Horário: 16h04min  ID Citsmart: 120393  Motivo/Comentário: Para evitar o erro: "...has no method fecharTelaAguarde" na tela de pesquisa da base de conhecimento. */
	function fecharJanelaAguarde(){
		JANELA_AGUARDE_MENU.hide();	
	}
	
	//imprime no console qualquer erro de javascript no sistema
	onerror=handleErr;
	function handleErr(msg,url,l)
	{
		var txt;
		txt+="Erro: " + msg + " - ";
		txt+="URL: " + url + " - ";
		txt+="Linha: " + l;
		console.log(txt);
		return true;
	}
	
</script>