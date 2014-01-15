<%@page import="br.com.centralit.citajax.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp" %>
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript">	
	function validaNav()
	{
		if ($.browser.msie) 
			document.getElementById("ie").value = "true";
		else
			document.getElementById("ie").value = "false";		
	}
	
	var objTab = null;	
	addEvent(window, "load", load, false);
	function load()
	{	
		document.form.afterRestore = function ()
		{
			$('.tabs').tabs('select', 0);
		}
	}
</script>

</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>

<script>
	
	var tabberOptions = {
	  'manualStartup':true,
	  
	  /*---- Gera um evento click ----*/
	  'onClick': function(argsObj) {
	
	    var t = argsObj.tabber; /* Tabber object */
	    var id = t.id; /* ID of the main tabber DIV */
	    var i = argsObj.index; /* Which tab was clicked (0
	 					is the first tab) */
	    var e = argsObj.event; /* Event object */
		
	    if (t.tabs[i].headingText == 'Processamentos Agendados') {
	    	document.form.fireEvent("listaProcessamentosBatch");
	    }
	  },
	
	  'addLinkId': true
	};	
</script>

<!-- Area de JavaScripts -->
<script>
	function LOOKUP_PROCESSAMENTO_BATCH_select(id,desc){
		document.form.restore({idProcessamentoBatch:id});
	}
	function excluir(){
        if (!confirm(i18n_message("citcorpore.comum.deleta"))){
            return;
        }
        document.form.fireEvent("delete");
    }
	function ajudaCron(){
        AJUDA_CRON.show();
    }  
    function mostraExecucoes(){
        var id = document.form.idProcessamentoBatch.value;
        if (id == '' || id == '0'){
            alert(i18n_message("processamentoBatch.informeProcessamento"));
            return;
        }
        document.getElementById('frame').src = '<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/processamentoBatch/processamentoBatch.load?idProcessamentoBatch=' + id + '&mostrar=S';
    	POPUP_EXECUCOES.showInYPosition({top:50});
    }
    function executarJobService(){
        var nomeClasseJobService = document.formExecJob.nomeClasseJobService.value;
        if (nomeClasseJobService == null || nomeClasseJobService == ''){
            alert(i18n_message("processamentoBatch.informeNomeClasse"));
            return;
        }
        document.formExecJob.fireEvent("executaJobService");        
    }
</script>

<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>

	<div id="main_container" class="main_container container_16 clearfix">
	<%@include file="/include/menu_horizontal.jsp"%>
	
		<div class="flat_area grid_16">
			<h2><i18n:message key="processamentoBatch.processamentoBatch"/></h2>						
		</div>

			
			<div class="box grid_16 tabs">
			
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="processamentoBatch.cadastroBatch"/></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="processamentoBatch.pesquisaBatch"/></a></li>
				</ul>
				
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
						 	<form name='form' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/processamentoBatch/processamentoBatch'>
						 		<div class="columns clearfix">
							 		<input type='hidden' name='idProcessamentoBatch'/>
							 		<input id="ie" name="ie" type="hidden"></input>
							 		
							 		<div class="col_100">
							 			<fieldset>
							 				<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.tipo"/></label>
							 				<div>
								 				<input class="Valid[Required] Description[citcorpore.comum.tipo]" type="radio" name="tipo" id="tipo" value="S" checked="checked"><i18n:message key="processamentoBatch.sql"/>
												<input class="Valid[Required] Description[citcorpore.comum.tipo]" type="radio" name="tipo" id="tipo" value="C"><i18n:message key="processamentoBatch.classeJava"/>
							 				</div>
							 			</fieldset>
						 			</div>
								
									<div class="col_40">
							 			<fieldset style="height: 81px !important;">
							 				<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.descricao"/></label>
							 				<div>
								 				<input type="text" name="descricao" maxlength="70" size="75" class="Valid[Required] Description[citcorpore.comum.descricao]" />
							 				</div>
							 			</fieldset>
							 		</div>
							 		<div class="col_60">
								 		<div class="col_15" style="width: 14.2%; !important;">
									 		<fieldset>
												<label class="campoObrigatorio"><i18n:message key="processamentoBatch.segundos"/></label>
												<div>
													<select name='segundos' id='segundos' class="Valid[Required] Description[processamentoBatch.segundos]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="col_15" style="width: 14.2%; !important;">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="processamentoBatch.minutos"/></label>
												<div>
													<select name='minutos' id='minutos' class="Valid[Required] Description[processamentoBatch.minutos]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="col_15" style="width: 14.2%; !important;">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="processamentoBatch.horas"/></label>
												<div>
													<select name='horas' id='horas' class="Valid[Required] Description[processamentoBatch.horas]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="col_15" style="width: 14.2%; !important;">
											<fieldset>
												<label><i18n:message key="processamentoBatch.diaDoMes"/></label>
												<div>
													<select name='diaDoMes' id='diaDoMes' class="Valid[Required] Description[processamentoBatch.diaDoMes]"></select>
												</div>
											</fieldset>
								 		</div>
								 		<div class="col_15" style="width: 14.2%; !important;">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="processamentoBatch.mes"/></label>
												<div>
													<select name='mes' id='mes' class="Valid[Required] Description[processamentoBatch.mes]"></select>
												</div>
											</fieldset>
										</div>
								 		<div class="col_15" style="width: 14.2%; !important;">
											<fieldset>
												<label><i18n:message key="processamentoBatch.diaDaSemana"/></label>
												<div>
													<select name='diaDaSemana' id='diaDaSemana' class="Valid[Required] Description[processamentoBatch.diaDaSemana]"></select>
												</div>
											</fieldset>	
										</div>
								 		<div class="col_15" style="width: 14.2%; !important;">	
								 			<fieldset>
												<label><i18n:message key="processamentoBatch.ano"/></label>
												<div>
													<select name='ano' id='ano' class="Description[processamentoBatch.ano]"></select>
												</div>
											</fieldset>
								 		</div>
							 		</div>
							 		<div class="col_100">
							 			<fieldset>
							 				<label ><i18n:message key="citcorpore.comum.conteudo"/></label>
							 				<div>
								 				<textarea cols=96 rows=7 name="conteudo" style='border:1px solid black'></textarea>
							 				</div>
							 			</fieldset>
							 		</div>
							 		
						 		</div>
						 		
						 		<br><br>
						 		
								<button type='button' name='btnGravar' class="light"  onclick='save();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar"/></span>
								</button>
								<button type='button' name='btnExcluir' class="light" onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir"/></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar"/></span>
								</button>	
								<button type='button' name='btnExecucoes' class="light" onclick='mostraExecucoes();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/settings.png">
									<span><i18n:message key="processamentoBatch.mostrarExecucoes"/></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa"/>
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_PROCESSAMENTO_BATCH' id='LOOKUP_PROCESSAMENTO_BATCH' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
									</form>
						</div>
					</div>									
					<!-- ## FIM - AREA DA APLICACAO ## -->


				</div>

			</div>

		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
	
<cit:janelaPopup style="display:none;top:1350px;width:850px;left:50px;height:475px;position:absolute; overflow: auto;" modal="true" title="<i18n:message key='processamento.ultimasExecucoes'/>" id="POPUP_EXECUCOES">
	<iframe name="frame" id="frame" src='about:blank' frameborder="1" style="height:470px;width:855px"></iframe>
</cit:janelaPopup>
</body>
</html>


<cit:janelaPopup id="AJUDA_CRON" title="<i18n:message key='processamentoBatch.ajudaCRON'/>"
	modal="true"
	style="display:none;top:200px;width:700px;left:200px;height:300px;position:absolute;">
	<table style="margin-left:5px" border="2">
		<br />
		<p><i18n:message key="processamentoBatch.esteCampoDeveSerPreenchidoConformeExemplosAbaixo"/></p>
		<tr>
			<th><i18n:message key="processamentoBatch.nomedoCampo"/></th>
			<th><i18n:message key="processamentoBatch.aCadaDuasHoras"/></th>
			<th><i18n:message key="processamentoBatch.todoDiaas1145PM"/></th>
			<th><i18n:message key="processamentoBatch.todoDomingoAs100AM"/></th>
			<th><i18n:message key="processamentoBatch.todoUltimoDiaMesAs1000AME1000PM"/></th>
		</tr>
		<tr>
			<td><i18n:message key="processamentoBatch.segundos"/></td>
			<td>0</td>
			<td>0</td>
			<td>0</td>
			<td>0</td>
		</tr>
		<tr>
			<td><i18n:message key="processamentoBatch.minutos"/></td>
			<td>30</td>
			<td>45</td>
			<td>0</td>
			<td>0</td>
		</tr>
		<tr>
			<td><i18n:message key="processamentoBatch.horas"/></td>
			<td>0/2</td>
			<td>23</td>
			<td>1</td>
			<td>10,22</td>
		</tr>
		<tr>
			<td><i18n:message key="processamentoBatch.diaDoMes"/></td>
			<td>*</td>
			<td>*</td>
			<td>&nbsp;?</td>
			<td>L</td>
		</tr>
		<tr>
			<td><i18n:message key="processamentoBatch.mes"/></td>
			<td>*</td>
			<td>*</td>
			<td>*</td>
			<td>*</td>
		</tr>
		<tr>
			<td><i18n:message key="processamentoBatch.diaDaSemana"/></td>
			<td>&nbsp;?</td>
			<td>&nbsp;?</td>
			<td>0</td>
			<td>&nbsp;?</td>
		</tr>
		<tr>
			<td><i18n:message key="processamentoBatch.ano(Opcional)"/></td>
			<td>*</td>
			<td>*</td>
			<td>*</td>
			<td>*</td>
		</tr>
	</table>
	<p><i18n:message key="processamentoBatch.seCasoFor"/><b><i><i18n:message key="processamentoBatch.tododiaAs1145PM"/></i></b><i18n:message key="processamentoBatch.porExemploDevePreencher"/></p>
	<p><i18n:message key="processamentoBatch.paraVerSignificadoDosCaracteresEspeciaisConsulteAqui"/></a>.</p>
			
</cit:janelaPopup>