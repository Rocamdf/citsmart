<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<%@page import="br.com.citframework.util.UtilStrings"%>
<!doctype html public "âœ°">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String idSolicitacaoServico = UtilStrings.nullToVazio((String)request.getParameter("idSolicitacaoServico"));	
	String dataHoraSolicitacao = UtilStrings.nullToVazio((String)request.getAttribute("dataHoraSolicitacao"));	
	
%>
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script>
	
	fechar = function(){
		parent.fecharSolicitacao();
	}
</script>

<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

</head>

<body>	
<div id="wrapper">
<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
					
		<div class="flat_area grid_16">
				<h2><i18n:message key="solicitacaoServico.solicitacao"/>&nbsp;Nº&nbsp;<%=idSolicitacaoServico%>&nbsp;-&nbsp;<i18n:message key="solicitacaoServico.dataHoraCriacao"/>&nbsp;<%=dataHoraSolicitacao%></h2>						
		</div>
  <div class="box grid_16 tabs">

	 <div class="toggle_container">
						<div class="">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/consultaSolicitacaoServico/consultaSolicitacaoServico'>
								<div class="columns clearfix">
									<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/> 

									<div class="col_66">
										<div class="col_50" >
											<fieldset>
												<label style="cursor: pointer;"><i18n:message key="origemAtendimento.origem"/></label>
												<div> 
													<input type='text' name="origem" readonly="readonly"/>					
												</div>
											</fieldset>
										</div>	
										<div class="col_50" >
											<fieldset>
												<label style="cursor: pointer;"><i18n:message key="solicitacaoServico.solicitante"/></label>
												<div> 
													<input type='text' name="solicitante"  readonly="readonly"/>					
												</div>
											</fieldset>
										</div>	
									</div>

									<div class="col_66">
										<div class="col_50" >
											<fieldset>
												<label style="cursor: pointer;"><i18n:message key="solicitacaoServico.tipo"/></label>
												<div> 
													<input type='text' name="demanda" readonly="readonly"/>					
												</div>
												<label style="cursor: pointer;"><i18n:message key="servico.servico"/></label>
												<div> 
													<input type='text' name="servico"  readonly="readonly"/>					
												</div>
											</fieldset>
										</div>
										<div class="col_50" >
											<fieldset>
												<label><i18n:message key="solicitacaoServico.descricao"/></label>
													<div>
									       				<textarea name="descricao" cols='70' rows='5' readonly="readonly"></textarea>												
													</div>
											</fieldset>
										</div>	
									</div>	
									<div class="col_66">
										<div class="col_50" >
											<fieldset>
												<label><i18n:message key="contrato.contrato" /></label>
													<div>
														<input type='text' name="contrato" size='50' readonly="readonly"/>					
													</div>
												<label><i18n:message key="solicitacaoServico.situacao" /></label>
													<div>
														<input type='text' name="situacao" size='50' readonly="readonly"/>					
													</div>
											</fieldset>
										</div>
										<div class="col_50" >
											<fieldset>
												<label><i18n:message key="solicitacaoServico.prioridade" /></label>
													<div>
														<input type='text' name="prioridade" size='10' readonly="readonly"/>					
													</div>
												<label><i18n:message key="solicitacaoServico.prazoLimite" /></label>
													<div>
														<input type='text' name="dataHoraLimiteStr" size='30' readonly="readonly"/>					
													</div>
											</fieldset>
										</div>	
									</div>	
								</div>
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->							
	 </div>
  </div>				
 </div>
<!-- Fim da Pagina de Conteudo -->

</body>

</html>
