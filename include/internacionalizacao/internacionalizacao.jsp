<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="br.com.centralit.citcorpore.util.UtilI18N"%>
<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
%>


<script type="text/javascript">

    var resourceBundle = { <%=UtilI18N.getJsonMensagens(request)%>}

	function i18n_message(label){
		var labelIntenacionalizada = resourceBundle['key'][label];
		
		if(labelIntenacionalizada == undefined){
			return null;
		}else{
			return labelIntenacionalizada;
		}
		return labelIntenacionalizada;
	}

</script>
