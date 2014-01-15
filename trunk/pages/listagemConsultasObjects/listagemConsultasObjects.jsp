<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%
String json_retorno = (String)request.getAttribute("json_retorno");
out.print(json_retorno);
%>