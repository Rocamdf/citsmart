<%@page import="br.com.centralit.citcorpore.util.UtilI18N"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.bean.NotificacaoDTO"%>
<%@page import="br.com.citframework.util.UtilFormatacao"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%
Collection listaNotificacoes = (Collection)request.getAttribute("listaNotificacoes");
%>
<style type="text/css">
	
	td{
	border: 1px solid #f5f5f5;
	cursor: pointer;
	padding: 0.5em;
 	font-weight: bold;
 	font-family: arial;
 	font-size: 12px;
 	background:#f2f2f2; 
 	
	}

	
	.linhaSubtituloGrid{
	padding: 0px;
	font-size:13px;
    box-shadow: 0 0 2px 0 #DDDDDD inset;
    margin-top: 3px;
    background-color: #F3F3F3;
    border: 1px solid #B3B3B3;

	}

	.tituloDiv{
		text-align: center; 
		font-size: 20px; 
		font-weight: bold;
		color: #7C7C7C;
	}
	</style>
<div>
	<button type='button' name='btnGravar' class="light"  onclick='abrirPopupNotificacoesServicos();'>
		<img src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/plane_suitcase.png'>
		<span><i18n:message key="notificacao.adicionarNotificacao"/></span>
	</button>
</div>
<div class='tituloDiv'><i18n:message key="contrato.notificacoesContrato"/></div>

<table cellpadding="0" cellspacing="0" width="100%" class="table table-bordered table-striped">
	<tr >
		<th width="8%">
			&nbsp;
		</th>
		<th width="60%">
			<i18n:message key="notificacao.notificacao"/>
		</th>
		<th width="32%">
			<i18n:message key="notificacao.tipoNotificacao"/>
		</th>		
	</tr>

	
	<%
	String corLinha = "";
	if (listaNotificacoes != null && listaNotificacoes.size() > 0){
		for(Iterator it = listaNotificacoes.iterator(); it.hasNext();){
			if (!corLinha.trim().equalsIgnoreCase("#f5f5f5")){
				corLinha = "#f5f5f5";
			}else{
				corLinha = "white";
			}
			NotificacaoDTO servicoNotificacoesDto = (NotificacaoDTO)it.next();
			
			
			out.print("<tr>");
					out.print("<td >");
						out.print("<table >");
							out.print("<tr style='text-align: center;'>");
							out.print("<td style='text-align: center; border: 0px' align='center'>");
							out.print("<img src='" + br.com.citframework.util.Constantes
									.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
									.getValue("CONTEXTO_APLICACAO") + "/imagens/write.png' border='0' style='cursor:pointer' title='" 
									+ UtilI18N.internacionaliza(request, "contrato.editarNotificacao") + "' onclick='editarNotificaoServico(" + servicoNotificacoesDto.getIdNotificacao() + ")'/>");					
							out.print("</td>");
							out.print("<td style='text-align: center; border: 0px'>&nbsp;&nbsp;&nbsp;</td>");
							out.print("<td style='text-align: center; border: 0px' align='center'>");
							out.print("<img src='" + br.com.citframework.util.Constantes
									.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes
									.getValue("CONTEXTO_APLICACAO") + "/imagens/button_cancel.png' border='0' style='cursor:pointer' title='" 
									+ UtilI18N.internacionaliza(request, "contrato.excluirNotificacao") + "' onclick='excluiNotificaoServico(" + servicoNotificacoesDto.getIdNotificacao() + ")'/>");					
							out.print("</td>");
							out.print("</tr>");
						out.print("</table>");
					out.print("</td>");
					out.print("<td>");
						out.print(servicoNotificacoesDto.getTitulo());
					out.print("</td>");
					out.print("<td>");
						out.print(UtilStrings.nullToVazio(servicoNotificacoesDto.getNomeTipoNotificacao()));
					out.print("</td>");				
					
				out.print("</tr>");
			}
		}

	%>
</table>