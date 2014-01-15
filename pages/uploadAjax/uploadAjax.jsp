<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%
response.setHeader( "Cache-Control", "no-cache");
response.setHeader( "Pragma", "no-cache");
response.setDateHeader ( "Expires", -1);
%>