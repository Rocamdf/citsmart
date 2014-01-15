
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<html>
	<head></head>
<body>
	<table style="margin-top: 2px">
		<tr>
			<td  style="padding: 2px; cursor: pointer">
				<%-- <img src='<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/order_add2.png' border='0' style='cursor:pointer' onclick='adicionaOS();' title="<i18n:message key="citcorpore.comum.criarOS"/>"/> --%>
				<button type='button' name='btnPesquisar' class="light" onclick='adicionaOS();' >
					<span><i18n:message key="citcorpore.comum.criarOS"/></span>
				</button>
			</td>
			<td>
			 <td  class="">
				<table>
					<tr>
						<td>
							<input type='text' name='dataInicioOS' id='dataInicioOS' size='10' maxlength="10" class='Format[Date] Valid[Date] text datepicker'/>
						</td>
						<td>&nbsp;
							<b><i18n:message key="citcorpore.comum.a"/></b>
						&nbsp;</td>
						<td>
							<input type='text' name='dataFimOS' id='dataFimOS' size='10' maxlength="10" class='Format[Date] Valid[Date] text datepicker'/>
						</td>
					</tr>
				</table>
			</td>	
			<td>	
		<%--	<td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
			<td id='tdTodas' class="tdOs" onclick='listarOS("0", this)'>
				<i18n:message key="citcorpore.comum.todas"/>
			</td>
			<td>
				&nbsp;
			</td>
			<td id='tdEmCriacao' class="tdOs" onclick='listarOS("1", this)'>
				<i18n:message key="perfil.criacao"/>
			</td>
			<td>
				&nbsp;
			</td>		
			<td id='tdSolicitada' class="tdOs" onclick='listarOS("2", this)'>
				<i18n:message key="perfil.solicitada"/>
			</td>
			<td>
				&nbsp;
			</td>		
			<td id='tdAutorizada' class="tdOs" onclick='listarOS("3", this)'>
				<i18n:message key="perfil.autorizada"/>
			</td>		
			<td>
				&nbsp;
			</td>		
			<td id='tdAprovada' class="tdOs" onclick='listarOS("4", this)'>
				<i18n:message key="citcorpore.comum.aprovadas"/>
			</td>
			<td>
				&nbsp;
			</td>	
			<td id='tdEmExecucao' class="tdOs" onclick='listarOS("5", this)'>
				<i18n:message key="citcorpore.comum.emExecucao"/>
			</td>
			<td>
				&nbsp;
			</td>
			<td id='tdExecutada' class="tdOs" onclick='listarOS("6", this)'>
				<i18n:message key="perfil.executada"/>
			</td>
			<td>
				&nbsp;
			</td>
			<td id='tdCancelada' class="tdOs" onclick='listarOS("7", this)'>
				<i18n:message key="citcorpore.comum.canceladas"/>
			</td>
			<td>
				&nbsp;
			</td>	 --%>	
				<div class="menubar">
					<ul>
						<li id='tdTodas'  onclick='listarOS("0", this)'><a><i18n:message key="citcorpore.comum.todas"/></a></li>
						<li class="divider"></li>
						
						<li id='tdEmCriacao'  onclick='listarOS("1", this)'><a><i18n:message key="perfil.criacao"/></a></li>
						<li class="divider"></li>
						
						<li  id='tdSolicitada'  onclick='listarOS("2", this)'><a><i18n:message key="perfil.solicitada"/></a></li>
						<li class="divider"></li>
						
						<li id='tdAutorizada'  onclick='listarOS("3", this)'><a><i18n:message key="perfil.autorizada"/></a></li>
						<li class="divider"></li>
						
						<li id='tdAprovada'  onclick='listarOS("4", this)'><a><i18n:message key="citcorpore.comum.aprovadas"/></a></li>
						<li class="divider"></li>
						
						<li id='tdEmExecucao'  onclick='listarOS("5", this)'><a><i18n:message key="citcorpore.comum.emExecucao"/></a></li>
						<li class="divider"></li>
						
						<li  id='tdExecutada'  onclick='listarOS("6", this)'><a><i18n:message key="perfil.executada"/></a></li>
						<li class="divider"></li>
						
						<li id='tdCancelada'  onclick='listarOS("7", this)'><a><i18n:message key="citcorpore.comum.canceladas"/></a></li>
						
					</ul>
			</div>
		  </td>
		</tr>
	</table>
	<div id='divListaOS'>
		<table cellpadding="0" cellspacing="0" width="100%" style='width: 98%' class="table table-bordered table-striped">
			<tr>
				<td  >
					&nbsp;
				</td>
				<td >
					<i18n:message key="citcorpore.comum.servico"/>
				</td>
				<td >
					<i18n:message key="citcorpore.comum.datainicio"/>
				</td>
				<td >
					<i18n:message key="citcorpore.comum.datafim"/>
				</td>				
			</tr>
		</table>
	</div>
</body>
</html>