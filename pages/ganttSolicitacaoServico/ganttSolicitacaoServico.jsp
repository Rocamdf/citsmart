<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<!doctype html public "">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
		%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		<script type="text/javascript">
		
			var objTab = null;
			
			addEvent(window, "load", load, false);
	
			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}
			
			function filtrar(){
				var dataInicio = document.getElementById("dataInicio").value;
				var dataFim = document.getElementById("dataFim").value;
				
				if(DateTimeUtil.isValidDate(dataInicio) == false){
					alert(i18n_message("citcorpore.comum.datainvalida"));
				 	document.getElementById("dataInicio").value = '';
				 	return false;
				}
				if(DateTimeUtil.isValidDate(dataFim) == false){
					alert(i18n_message("citcorpore.comum.dataFinalInvalida"));
					 document.getElementById("dataFim").value = '';
					return false;					
				}
				
				if(validaData(dataInicio,dataFim)){
					setGantt(i18n_message("citcorpore.comum.aguardecarregando"));
					document.form.fireEvent("filtrarGantt");
				}
			}
			
			function setGantt(txt) {
				$('.gantt').html(txt);
			}
			
			function ocultarInformacao(){
				$('#informacao').hide();
			}
			
			/**
			* @author rodrigo.oliveira
			*/
			function validaData(dataInicio, dataFim) {
				
				var dtInicio = new Date();
				var dtFim = new Date();
				
				dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
				dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
				
				if (dtInicio > dtFim){
					alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
					return false;
				}else
					return true;
			}
			
		</script>
		
	</head>
	<body>	
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="gantt.gantt"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<form name="form" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/ganttSolicitacaoServico/ganttSolicitacaoServico'>
						
						<div class="columns clearfix">
							<div class="col_25">
								<fieldset>
									<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="citcorpore.comum.periodo"/></label>
									<div>
										<table>
											<tr>
												<td>
													<input type='text' name='dataInicio' id='dataInicio' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
												</td>
												<td>
													<i18n:message key="citcorpore.comum.a"/>
												</td>
												<td>
													<input type='text' name='dataFim' id='dataFim' size='10' maxlength="10" class='Format[Date] Valid[Date] datepicker'/>
												</td>
											</tr>
										</table>												
									</div>
								</fieldset>
							</div>
						</div>
						
						<div class="columns clearfix">
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><i18n:message key="solicitacaoServico.tipo"/></label>
										<div>
										  	<select id="comboTipoDemanda" name="tipoDemandaServico" style="margin-bottom: 3px;"></select>
										</div>
								</fieldset>
							</div>
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><i18n:message key="grupo.grupo"/></label>
										<div>
										  	<select id="comboGruposSeguranca" name="idGruposSeguranca" style="margin-bottom: 3px;"></select>
										</div>
								</fieldset>
							</div>
							<div class="col_25">
								<fieldset>
									<label style="margin-top: 5px;"><i18n:message key="citcorpore.comum.situacao"/></label>
									<div>
										<select name='situacao' style="margin-bottom: 3px;">
											<option value=''><i18n:message key="citcorpore.comum.todos"/></option>
											<OPTION value='Cancelada'><i18n:message key="citcorpore.comum.cancelada"/></OPTION>
								            <OPTION value='EmAndamento'><i18n:message key="citcorpore.comum.emandamento"/></OPTION>
								            <OPTION value='Fechada'><i18n:message key="citcorpore.comum.fechada"/></OPTION>
								            <OPTION value='Reaberta'><i18n:message key="citcorpore.comum.reaberta"/></OPTION>
								            <OPTION value='ReClassificada'><i18n:message key="citcorpore.comum.reclassificada"/></OPTION>
								            <OPTION value='Resolvida'><i18n:message key="citcorpore.comum.resolvida"/></OPTION>
								            <OPTION value='Suspensa'><i18n:message key="citcorpore.comum.suspensa"/></OPTION>
										</select>
									</div>
								</fieldset>
							</div>							
						</div>
						<div class="columns clearfix">
							<div class="col_100">			
								<fieldset>
									<button style="margin-top: 10px; margin-left: 22px; margin-bottom: 10px; width: 90px;" type='button' name='btnFiltrar' class="light"  onclick='filtrar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/download.png">
										<span><i18n:message key="gantt.filtrar"/></span>
									</button>
								</fieldset>									
							</div>								
						</div>
						<div class="gantt" style="width: 74%; float: left; margin-left: 20px;"></div>
					</form>
				</div>		
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>