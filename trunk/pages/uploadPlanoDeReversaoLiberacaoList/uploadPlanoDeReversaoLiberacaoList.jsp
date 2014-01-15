<!-- Esta página realiza apenas a listagens dos uploads -->

<%@page import="br.com.centralit.citged.bean.ControleGEDDTO"%>
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
<html>
<head>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
	<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
	
	<style type="text/css">
	body {
	background-color: white;
	background-image: url("");
	}
	table tr
	{
		height: 15px;
	}
	</style>
	
</head>
<body>
<script>

	function obtemArquivoTemporario(path){
		window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/visualizarUploadTemp/visualizarUploadTemp.load?path=' + path;
	}
</script>
<table width="100%" class='ui-widget ui-state-default ui-corner-all' id="table">
		<tr>
          <th ><i18n:message key="citcorpore.comum.arquivo" /></th>
          <th><i18n:message key="citcorpore.comum.descricao" /></th>
          <th><i18n:message key="citcorpore.comum.situacao" /></th>
		</tr>  
        <%boolean branco = true;%>
      	<%String cor = "#ffffff";%>	
      	<%
		Collection<UploadDTO> colUploadsGED = (Collection<UploadDTO>)request.getSession(true).getAttribute("colUploadPlanoDeReversaoLiberacaoGED");

		if (colUploadsGED == null){
			colUploadsGED = new ArrayList();
		} 	
		%>
      	<%
      	if(!colUploadsGED.isEmpty())
      	{
	      	for(UploadDTO bean : colUploadsGED){ %>
	      		<%
	      		UploadDTO uploadDTO = (UploadDTO)bean;
	      		%>
			<tr>
				<%
				if(branco){ 
					cor ="#ffffff"; 
				}else{
					cor="#E5EBF6";
				}
				%>					
				<td bgcolor="<%=cor%>" style="font-size: 11px;" width="40%">
					<a href="#" onclick='obtemArquivoTemporario("<%=uploadDTO.getPath()%>")'><%=uploadDTO.getNameFile()%></a>
				</td>
				<td bgcolor="<%=cor%>" style="font-size: 11px;" width="40%">
					<%=uploadDTO.getDescricao()%>
				</td>		
				<td bgcolor="<%=cor%>" style="font-size: 11px;" width="15%" title='<i18n:message key="citcorpore.ged.situacao" />'>			
					<%=uploadDTO.getSituacao()%>
				</td>
				<%branco=!branco;%>		
	        </tr>
			<%}
			}else
			{
			%>
			<tr><td colspan="3"><i18n:message key="citcorpore.comum.resultado" /></td></tr>
			<%
			}
			%>
</table>
</body>

</html>