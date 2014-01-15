<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<html>
<head>
	<style type="text/css">

	</style>
	
	<script type="text/javascript">
		$(function() {
			$('.datepicker').datepicker();
		});
	</script>
	
</head>
<body>

	<table style="margin-top: 2px;">
		<tr>
			<td style="cursor: pointer; padding: 5px;">
				<%-- <img src='<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/cash-register-icon-add2.png' border='0' style='cursor:pointer' onclick='adicionaFatura();' title="<i18n:message key="contrato.criarFatura"/>"/> --%>
				<button type='button' name='btnPesquisar' class="light" onclick='adicionaFatura();' style="width: 100px;">
					<span><i18n:message key="contrato.criarFatura"/></span>
				</button>
			</td>
			<td class="" >
				<table>
					<tr>
						<td>
							<input type='text' name='dataInicioFatura' id='dataInicioFatura' size='10' maxlength="10" class='Format[Date] Valid[Date] text datepicker'/>
						</td>
						<td>&nbsp;
							<b><i18n:message key="citcorpore.comum.a"/></b>
						&nbsp;</td>
						<td>
							<input type='text' name='dataFimFatura' id='dataFimFatura' size='10' maxlength="10" class='Format[Date] Valid[Date] text datepicker'/>
						</td>
					</tr>
				</table>
			</td>		
			<%-- <td>
				&nbsp;
			</td>
			<td>
				&nbsp;
			</td>
			<td id='tdEmCriacao' class="tdOs" onclick='listarFatura("1", this)'>
				<i18n:message key="perfil.criacao"/>
			</td>
			<td>
				&nbsp;
			</td>		
			<td id='tdAguardandoAprovacao' class="tdOs" onclick='listarFatura("2", this)'>
				<i18n:message key="perfil.aguardandoAprovacao"/>
			</td>
			<td>
				&nbsp;
			</td>		
			<td id='tdAprovadas' class="tdOs" onclick='listarFatura("3", this)'>
				<i18n:message key="citcorpore.comum.aprovadas"/>
			</td>		
			<td>
				&nbsp;
			</td>		
			<td id='tdAguardHomologacao' class="tdOs" onclick='listarFatura("5", this)'>
				<i18n:message key="perfil.recebimento"/>
			</td>
			<td>
				&nbsp;
			</td>			
			<td id='tdHomologadas' class="tdOs" onclick='listarFatura("6", this)'>
				<i18n:message key="perfil.recebidas"/>
			</td>
			<td>
				&nbsp;
			</td>		
			<td id='tdRejeitadas' class="tdOs" onclick='listarFatura("4", this)'>
				<i18n:message key="citcorpore.comum.rejeitadas"/>
			</td>
			<td>
				&nbsp;
			</td>	
			<td id='tdCanceladas' class="tdOs" onclick='listarFatura("7", this)'>
				<i18n:message key="citcorpore.comum.canceladas"/>
			</td>
			<td>
				&nbsp;
			</td>		
			<td id='tdTodas' class="tdOs" onclick='listarFatura("TODAS", this)'>
				<i18n:message key="citcorpore.comum.todas"/>
			</td>
			<td>
				&nbsp;
			</td>	 --%>	
			<td>
				<div class="menubar">
					<ul>
						<li id='tdTodas'  onclick='listarFatura("0", this)'><a><i18n:message key="citcorpore.comum.todas"/></a></li>
						<li class="divider"></li>
						<li id='tdEmCriacao'  onclick='listarFatura("1", this)'><a><i18n:message key="perfil.criacao"/></a></li>
						<li class="divider"></li>
						
						<li id='tdAguardandoAprovacao'  onclick='listarFatura("2", this)'><a><i18n:message key="perfil.aguardandoAprovacao"/></a></li>
						<li class="divider"></li>
						
						<li  id='tdAprovadas'  onclick='listarFatura("3", this)'><a><i18n:message key="citcorpore.comum.aprovadas"/></a></li>
						<li class="divider"></li>
						
						<li id='tdAguardHomologacao'  onclick='listarFatura("5", this)'><a><i18n:message key="perfil.recebimento"/></a></li>
						<li class="divider"></li>
						
						<li id='tdHomologadas'  onclick='listarFatura("6", this)'><a><i18n:message key="perfil.recebidas"/></a></li>
						<li class="divider"></li>
						
						<li id='tdRejeitadas'  onclick='listarFatura("4", this)'><a><i18n:message key="citcorpore.comum.rejeitadas"/></a></li>
						<li class="divider"></li>
						
						<li  id='tdCanceladas'  onclick='listarFatura("7", this)'><a><i18n:message key="citcorpore.comum.canceladas"/></a></li>
						<li class="divider"></li>
						
					</ul>
				</div>
			</td>			
		</tr>
	</table>
	
	<div id='divListaFaturas'></div>
	
</body>
</html>