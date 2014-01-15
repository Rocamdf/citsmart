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
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	String iframe = "";
	iframe = request.getParameter("iframe");
	
	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));	
	String nomeTarefa = UtilStrings.nullToVazio((String)request.getAttribute("nomeTarefa"));
	
	
	%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<script type="text/javascript">
		gravar = function() {
			if (document.form.slaACombinar.value == 'N'){
				if (StringUtils.isBlank(document.form.prazoHH.value) && StringUtils.isBlank(document.form.prazoMM.value)){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}
				if (document.form.prazoHH.value == '0' && document.form.prazoMM.value == '0'){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}
				if (document.form.prazoHH.value == '0' && StringUtils.isBlank(document.form.prazoMM.value)){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}	
				if (document.form.idJustificativa.value == '') {
					alert(i18n_message("citcorpore.comum.justificativa") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
					document.form.idJustificativa.focus();
					return;
				}
				if (StringUtils.isBlank(document.form.prazoHH.value) && document.form.prazoMM.value == '0'){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.prazoHH.focus();
					return;
				}	
				if (StringUtils.isBlank(document.form.idCalendario.value)){
					alert(i18n_message("gerenciaservico.mudarsla.validacao.informeprazo"));
					document.form.idCalendario.focus();
					return;
				}										
			}
			if (!document.form.validate()){
				return;
			}
			if (document.form.idJustificativa.value == '') {
				alert(i18n_message("citcorpore.comum.justificativa") + ': ' + i18n_message("citcorpore.comum.campo_obrigatorio"));
				document.form.idJustificativa.focus();
				return;
			}
			if (confirm(i18n_message("gerenciaservico.mudarsla.confirm.mudanca"))) 
				document.form.save();
		}	
		
		function mudarTipoSLA(obj){
			if (obj.value == 'S'){
			 	document.getElementById('tempo').style.display = 'none';
				document.getElementById('calendario').style.display = 'none'; 
				/*$('#tempo').switchClass( "inativo", "ativo", null );
				$('#calendario').switchClass( "inativo", "ativo", null );*/
			}else{
				document.getElementById('tempo').style.display = 'block';
				document.getElementById('calendario').style.display = 'block'; 
				/*$('#tempo').switchClass( "ativo", "inativo", null );
				$('#calendario').switchClass( "ativo", "inativo", null );*/
			}
		}
		function verificaMudarTipoSLA(){
			mudarTipoSLA(document.form.slaACombinar);
		}

		function somenteNumero(e){
		    var tecla=(window.event)?event.keyCode:e.which;   
		    if((tecla>47 && tecla<58)) return true;
		    else{
		    	if (tecla==8 || tecla==0) return true;
			else  return false;
		    }
		}
		
		</script>

		 <style type="text/css">
		 	.campoObrigatorio:after {
				color: #FF0000;
				content: "*";
			}
		 	
		 </style>
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
					<h3><i18n:message key="solicitacaoServico.solicitacao"/>&nbsp;Nº&nbsp;<%=idSolicitacaoServico%>&nbsp;-&nbsp;<i18n:message key="tarefa.tarefa"/>&nbsp;<%=nomeTarefa%></h3>
					<div class="box-generic">
						<form class="form-horizontal" name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/mudarSLA/mudarSLA'>
						<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/> 
						<input type='hidden' name='idTarefa'/>
							<div class="row-fluid">
								<div class="span5">
									<label  class="strong"><i18n:message key="gerenciaservico.mudarsla.tiposla"/></label>
									  	<select placeholder="" class="span10" id="slaACombinar" required="required"  type="text" name="slaACombinar"  onchange='mudarTipoSLA(this)'></select>
								</div>
							</div>
							<div class="row-fluid" id="tempo">
								<div class="span7">
									<label  class="strong"><i18n:message key="gerenciaservico.mudarsla.tempo"/></label>
									  	<input type='text' class='span4' id='prazoHH' name='prazoHH' onkeypress='return somenteNumero(event)' size='3' maxlength="3" />
										hh
									  	<input type='text' class='span4' ID='prazoMM' name='prazoMM' onkeypress='return somenteNumero(event)' size='3' maxlength="2" />
									  	mm
								</div>
							<!-- </div>
							<div class="row-fluid" > -->
								<div class="span5" id="calendario">
									<label  class="strong"><i18n:message key="gerenciaservico.mudarsla.calendario"/></label>
										<select id="idCalendario" name="idCalendario"></select>
								</div>
							</div>
							<div class="row-fluid">
								<div class="span5">
									<label  class="strong campoObrigatorio"><i18n:message key="citcorpore.comum.justificativa"/></label>
									  	<select placeholder="" class="span10" id="idJustificativa" required="required"  type="text" name="idJustificativa"></select>
								</div>
							</div>
							<div class="control-group row-fluid">
							  	<div class="span12">
									<label  class="strong"><i18n:message key="gerenciaservico.mudarsla.complementojustificativa" /></label>
									<textarea id="mustHaveId" class=" span12" rows="5" name="complementoJustificativa" id="complementoJustificativa"></textarea>
								</div>
							</div>
							<div style="margin: 0;" class="form-actions">
								<button class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick='gravar();'><i></i><i18n:message key="citcorpore.comum.gravar" /></button>
								<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick='document.form.clear();'><i></i><i18n:message key="citcorpore.comum.limpar" /></button>
							</div>
						</form>
					</div>
				</div>
				<!--  Fim conteudo-->
				
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				
			</div>
		</div>
	</body>
</html>