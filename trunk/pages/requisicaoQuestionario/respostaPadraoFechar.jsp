<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="br.com.citframework.util.Constantes"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	
%>


<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
	<%@include file="/include/noCache/noCache.jsp" %>

	<%@include file="/include/titleComum/titleComum.jsp" %>
	
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>	
	
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
		
	</head>
	
	<body>
		
	</body>
</html>

