<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ taglib uri="/tags/menuPadrao" prefix="menu" %>

<html>
	<head>	
	<%
			response.setHeader("Cache-Control", "no-cache"); 
	    	response.setHeader("Pragma", "no-cache");
	    	response.setDateHeader("Expires", -1);
	    	String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		
		<style title="" type="text/css">
		.campoObrigatorio:after {
			color: #FF0000;
			content: "*";
		}
		</style>
		
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<%@include file="/novoLayout/common/include/rodape.jsp" %>
		
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/css/theme_base.css">
		<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jqueryTreeview/jquery.treeview.css">	
		
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.min.js" type="text/javascript"></script>	
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery-ui.min.js" type="text/javascript"></script>
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/validation/jquery.validate.min.js" type="text/javascript"></script>
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
		
		<script type="text/javascript">
		
		jQuery(function($){
			   $("#horaInicio").mask("99:99");
			   $('.datepicker').datepicker();
			});
		
		gravar = function() {


			hrs = (document.form.horaInicio.value.substring(0,2)); 
			min = (document.form.horaInicio.value.substring(3,5)); 
			estado = ""; 
			if ((hrs < 00 ) || (hrs > 23) || ( min < 00) ||( min > 59)){ 
				estado = "errada"; 
			} 

			if (document.form.horaInicio.value == "") { 
				estado = "errada"; 
			}
			if (StringUtils.isBlank(document.form.idGrupoAtvPeriodica.value)){
				alert(i18n_message("gerenciaservico.agendaratividade.valida.grupo"));
				document.form.idGrupoAtvPeriodica.focus();
				return;
			}
			if (StringUtils.isBlank(document.form.dataInicio.value)){
				alert(i18n_message("gerenciaservico.agendaratividade.valida.dataagendamento"));
				document.form.dataInicio.focus();
				return;
			} 
			if (estado == "errada") { 
				alert(i18n_message("citcorpore.validacao.horaInvalida")); 
				document.form.horaInicio.focus(); 
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
			if (confirm(i18n_message("gerenciaservico.agendaratividade.confirm.agendamento"))) 
				document.form.save();
			}


		function somenteNumero(e){
		    var tecla=(window.event)?event.keyCode:e.which;   
		    if((tecla>47 && tecla<58)) return true;
		    else{
		    	if (tecla==8 || tecla==0) return true;
			else  return false;
		    }
		}
		
		function limpar(){
			document.form.idGrupoAtvPeriodica.value = '';
			document.form.dataInicio.value = '';
			document.form.horaInicio.value = '';
			document.form.duracaoEstimada.value = '';	
			//Limpar TextArea HTML5
			$("#orientacaoTecnica").data("wysihtml5").editor.setValue('');
		}
		

		</script>
	</head>
	<body>
		<div class="<%=(iframe == null) ? "container-fluid fixed" : "" %>">
					
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<div class="navbar <%=(iframe == null) ? "main" : "nomain" %> hidden-print">
				<% if(iframe == null) { %>
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>					
				<% } %>
				
			</div>
	
			<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper" %>">
					
				<!-- Inicio conteudo -->
				<div id="content">
				<h3><i18n:message key="gerenciaservico.agendaratividade" /></h3>
				<div class="box-generic">
					
						<!-- Tabs Heading -->
						<div class="tabsbar">
							<ul>
								<li class="active"><a href="#tab1" data-toggle="tab"><i18n:message key='gerenciaservico.agendaratividade.historicoagendamentos' /></a></li>
								<li  class=""><a href="#tab2" data-toggle="tab"><i18n:message key='gerenciaservico.agendaratividade.criaragendamento' /></a></li>
							</ul>
						</div>
						<!-- // Tabs Heading END -->
						<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendarAtividade/agendarAtividade'>
							<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/> 
							<input type='hidden' name='idTarefa'/>
								<div class="tab-content">
										
									<!-- Tab content -->
									<div class="tab-pane active" id="tab1">
										<div id='divAgendamentos' ></div>
									</div>
									<div class="tab-pane" id="tab2">
										<div class='row-fluid'>
											<label class="strong campoObrigatorio"><i18n:message key="gerenciaservico.agendaratividade.crupoatividades" /></label>
											<select  class=" span12" id="idGrupoAtvPeriodica" name="idGrupoAtvPeriodica" required="required" ></select>
										</div>
										<div class="input-append">
											<label  class="strong"><i18n:message key="gerenciaservico.agendaratividade.orientacaotecnica" /></label>
										  	<div class="controls">
												<textarea class="wysihtml5 span12" rows="5" name="orientacaoTecnica" id="orientacaoTecnica"></textarea>
											</div>
										</div>
										<div class='row-fluid'>
											<div class='span6'>
												<label class="strong campoObrigatorio"><i18n:message key="gerenciaservico.agendaratividade.agendarpara" /></label>
												<input type="text" class=" span5 datepicker" id="dataInicio" name="dataInicio" maxlength="10" required="required" >
												&nbsp;<i18n:message key='citcorpore.comum.as' />&nbsp;
												<input type="text" class=" span5 " id="horaInicio" name="horaInicio"  required="required" >
											</div>
										</div>
										<div class="separator"></div> 
										<div class='row-fluid'>
											<label class="strong campoObrigatorio"><i18n:message key='gerenciaservico.agendaratividade.duracaoestimada' /></label>
											<input type="text" class=" span1" id="duracaoEstimada" maxlength="8" name="duracaoEstimada" onkeypress='return somenteNumero(event)' required="required" >&nbsp;<i18n:message key='gerenciaservico.agendaratividade.minuto' />
										</div>
										<div style="margin: 0;" class="form-actions">
												<button class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick='gravar();'><i></i><i18n:message key="citcorpore.comum.gravar" /></button>
												<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick='limpar();'><i></i><i18n:message key="citcorpore.comum.limpar" /></button>
											</div>
										
			
									</div>
									<!-- // Tab content END -->
						
									
								</div>
						</form>
				</div>
			
				</div>
				<!--  Fim conteudo-->
				
			</div>
		</div>
	</body>
</html>