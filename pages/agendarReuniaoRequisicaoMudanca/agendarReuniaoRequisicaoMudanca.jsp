<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String idRequisicaoMudanca = UtilStrings.nullToVazio((String)request.getParameter("idRequisicaoMudanca"));	
	String idReuniaoRequisicaoMudanca = UtilStrings.nullToVazio((String)request.getParameter("idReuniaoRequisicaoMudanca"));	
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));	
	
%>
	<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>

<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

</head>

<body>	

<script>
	
	fechar = function(){
		parent.fecharReuniao();
	}
	
	gravar = function() {
		if (StringUtils.isBlank(document.form.localReuniao.value)){
			alert(i18n_message("gerenciaservico.agendaratividade.valida.local"));
			document.form.localReuniao.focus();
			return;
		}
		if (StringUtils.isBlank(document.form.dataInicio.value)){
			alert(i18n_message("gerenciaservico.agendaratividade.valida.dataagendamento"));
			document.form.dataInicio.focus();
			return;
		}
		if (StringUtils.isBlank(document.form.horaInicio.value)){
			alert(i18n_message("gerenciaservico.agendaratividade.valida.hora"));
			document.form.horaInicio.focus();
			return;
		}
		if (StringUtils.isBlank(document.form.duracaoEstimada.value)){
			alert(i18n_message("gerenciaservico.agendaratividade.valida.duracao"));
			document.form.duracaoEstimada.focus();
			return;
		}
		if (document.form.duracaoEstimada.value == '0' || document.form.duracaoEstimada.value == '00' || document.form.duracaoEstimada.value == '000'){
			alert(i18n_message("gerenciaservico.agendaratividade.valida.duracao"));
			document.form.duracaoEstimada.focus();
			return;
		}	
		if (!document.form.validate()){
			return;
		}				
		validarDatas();
		
		if (confirm(i18n_message("gerenciaservico.agendaratividade.confirm.agendamento"))) 
			
			document.form.fireEvent("validaHorarioESalva");
	}	
	
	function excluiReuniao(idReuniao){
		if (confirm(i18n_message("gerenciaservico.agendaratividade.cancela.agendamento"))) {
			document.getElementById('idReuniaoRequisicaoMudanca').value = idReuniao;
			document.form.fireEvent("delete");
		}
	}
	
	function validarDatas(){
		var inputs = document.getElementsByClassName("datepicker");
		var input = null;
		var errorMsg = i18n_message("citcorpore.comum.nenhumaDataDeveSerInferiorHoje") ;
		
		for(var i = 0; i < inputs.length; i++){
			input = inputs[i];

			if(input == null){
				continue;
			}
			
			if(comparaComDataAtual(input) < 0){
				alert(errorMsg);
				input.focus();
				throw errorMsg;
			}				
		}
	}

	/*Thiago Fernandes - 29/10/2013 - 17:33 - Sol. 121468 - Assim que for adicionado um agendamento a popup não deve ser fechada, ela deve ser apenas recarregada. Função para limpar campos ao cadastrar um novo agendamento*/
	function limparCampos() {
		document.getElementById('localReuniao').value = '';
		document.getElementById('descricao').value = '';
		document.getElementById('dataInicio').value = '';
		document.getElementById('horaInicio').value = '';
		document.getElementById('duracaoEstimada').value = '';
	}
	
	
</script>

<div id="wrapper">
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
					
		<div class="flat_area grid_16">
				<h2><i18n:message key="reuniao.agendaReuniaoMudanca"/>&nbsp;Nº&nbsp;<%=idRequisicaoMudanca%></h2>						
		</div>
  <div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key='gerenciaservico.agendaratividade.historicoagendamentos' /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key='gerenciaservico.agendaratividade.criaragendamento' /></a></li>
				</ul>
	 <div class="toggle_container">
	 <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendarReuniaoRequisicaoMudanca/agendarReuniaoRequisicaoMudanca'>
	 					<div id="tabs-1" class="block">
	 						<div id='divAgendamentos' style='overflow: auto'>
	 						</div>
	 					</div>
						<div id="tabs-2" class="block">
							
								<div class="columns clearfix">
									<input type='hidden' name='idRequisicaoMudanca' id='idRequisicaoMudanca' value='<%=idRequisicaoMudanca%>'/> 
									<input type='hidden' name='idTarefa'/>
									<input type='hidden' name='idReuniaoRequisicaoMudanca' id='idReuniaoRequisicaoMudanca'/>

									<div class="col_100">
										<fieldset>
											<label style="cursor: pointer;"><i18n:message key='reuniao.localReuniao' /></label>
											<div>
											<table>
											<tr>
											<td>
												<input type='text' name='localReuniao' id='localReuniao' size="180" maxlength="180" class=''/>						
											</td>
											</tr>
											</table> 
											</div>
										</fieldset>
										<fieldset id='fldOrientacao'>
											<label style="cursor: pointer;"><i18n:message key='reuniao.descricaoReuniao' /></label>
											<div> 
												<textarea name="descricao" cols='90' rows='5'></textarea>
											</div>
										</fieldset>										
										<fieldset id='fldIniciarEm'>
											<label style="cursor: pointer;"><i18n:message key='gerenciaservico.agendaratividade.agendarpara' /></label>
											<div> 
												<table>
													<tr>
														<td>
															<input type='text' name='dataInicio' id='dataInicio' size="10" maxlength="10" class="Format[Date] Valid[Date] Description[gerenciaservico.agendaratividade.agendarpara] datepicker text"/>
												        </td>
												        <td>
										                    &nbsp;<b><i18n:message key='citcorpore.comum.as' /></b>&nbsp;
												        </td>
												        <td>
												        	<input type='text' name='horaInicio' id='horaInicio' size="5" maxlength="5" class='Format[Hora] Valid[Hora] Description[gerenciaservico.agendaratividade.agendarpara] text'/>
												        </td>
												    </tr>											
												</table>
											</div>
										</fieldset>
										<fieldset id='fldDuracaoEstimada'>
											<label style="cursor: pointer;"><i18n:message key='gerenciaservico.agendaratividade.duracaoestimada' /> </label>
											<div> 
												<table>
													<tr>
														<td>
															<input type='text' name='duracaoEstimada' id='duracaoEstimada' size="5" maxlength="5" class='Format[Numero] text'/>&nbsp;
														</td>
														<td>
															<b>&nbsp;<i18n:message key='gerenciaservico.agendaratividade.minuto' /></b>
														</td>
													</tr>
												</table>
											</div>
										</fieldset>											
									</div>	
								</div>	
									
								<div>
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name='btnCancelar' class="light" onclick='fechar();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/bended_arrow_left.png">
										<span><i18n:message key="citcorpore.comum.cancelar" />
										</span>
									</button>
							    </div>
							
						</div>
						</form>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->							
	 </div>
  </div>				
 </div>
<!-- Fim da Pagina de Conteudo -->

</body>

</html>
