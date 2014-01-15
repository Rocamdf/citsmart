<%@page import="br.com.citframework.util.UtilStrings"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<%
	response.setCharacterEncoding("ISO-8859-1");
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	String noVoltar = request.getParameter("noVoltar");
	if (noVoltar == null){
		noVoltar = "false";
	}
	noVoltar = UtilStrings.nullToVazio(noVoltar);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>

	<%@include file="/include/noCache/noCache.jsp" %>

	<%@include file="/include/titleComum/titleComum.jsp" %>
	
	<%@include file="/include/header.jsp" %>
<%-- 	<%@include file="/include/cssComuns/cssComuns.jsp" %> --%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	
<style type='text/css'>
            .emExecucao{
                background-color: blue;
                color: white;
                font-size: 6px;
                font-weight: normal;                
            }
            .suspenso{
                background-color: gray;
                font-size: 6px;
                font-weight: normal;                
            }   
            .executado{
                background-color: green !important;
                color: white !important;
                font-size: 6px;
                font-weight: normal;
                
            }                      
            .agendado{
                background-color: #8EC657;
                color: black;
                font-size: 6px;
                font-weight: normal;                
            }            
            .atrasado{
                background-color: #DD4133;
                color: #ffff00;
                font-size: 6px;
                font-weight: normal;                
            }
           .fc-event{
           height: 35px !important;
           }
           body {
			background-color: white;
			background-image: url("");
		}
		div{
		border: 0px!important;
		}
		.ui-dialog .ui-dialog-content {
			padding: 1 !important;
		}
		td{
			vertical-align: top !important;
			}
</style>	
	

<%-- <script type='text/javascript' src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fullcalendar/jquery/jquery-1.5.2.min.js'></script>
<script type='text/javascript' src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fullcalendar/jquery/jquery-ui-1.8.11.custom.min.js'></script> --%>
<script type='text/javascript' src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fullcalendar/fullcalendar.min.js'></script>
<script type='text/javascript' src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fullcalendar/fullcalendar.js'></script>
<script type="text/javascript" src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/UploadUtils.js'></script>

<script type='text/javascript'>

var tabberOptions = {
        
        'manualStartup':true,
        
        /*---- Gera um evento click ----*/
        'onClick': function(argsObj) {
      
          var t = argsObj.tabber; /* Tabber object */
          var id = t.id; /* ID of the main tabber DIV */
          var i = argsObj.index; /* Which tab was clicked (0
                          is the first tab) */
          var e = argsObj.event; /* Event object */
          
          
        },
      
        'addLinkId': true
    };
    
 $(document).ready(function() {

	$("#POPUP_REGISTRO1").dialog({
		autoOpen : false,
		width : 900,
		height : 500,
		modal : true
	});	

	$("#POPUP_NOVOMOTIVOSUSPENSAOATIVIDADE").dialog({
		autoOpen : false,
		width : 1000,
		height : 450,
		modal : true,
		close: function() {
			document.form.fireEvent('carregarComboMotivo');
		}
	});		

	$("#POPUP_ORIENTACAO").dialog({
		title: i18n_message("scripts.orientacaoTecnica"),
		autoOpen : false,
		width : 700,
		height : 450,
		modal : true
	});	
}); 

	$(document).ready(function() {
	
		var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		$('#calendar').fullCalendar({
			header: {
				left: 'prev,next today',
				center: 'title',
				right: 'month,agendaWeek,agendaDay'
			},
			selectable: false,

			selectHelper: false,

			select: function(start, end, allDay) {
				var title = prompt('Evento:');
				if (title) {
					calendar.fullCalendar('renderEvent',
						{
							title: title,
							start: start,
							end: end,
							allDay: allDay
						},
						false // make the event "stick"
					);
				}
				calendar.fullCalendar('unselect');
			},			
			editable: false,

			events: "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/eventos/eventos.load",

			eventDrop: function(event, delta) {
			},
			
			loading: function(bool) {
				if (bool) $('#loading').show();
				else $('#loading').hide();
			}
		});

		$("#POPUP_REGISTRO1").dialog('close')
	});

	function validaEvento(idExecucaoAtividadePeriodicaParm, idAtv, idProg, titulo, data,numeroOS, descricaoAtividadeOS, hora){
        document.form.clear();
        document.formUpload.clear();
        uploadAnexos.clear();
        uploadAnexos.refresh();
		if (idExecucaoAtividadePeriodicaParm == '0'){
			document.form.idExecucaoAtividadePeriodica.value = '';			
		}else{
			document.form.idExecucaoAtividadePeriodica.value = idExecucaoAtividadePeriodicaParm;
		}
		
		
		document.form.idAtividadePeriodica.value = idAtv;
		document.form.idProgramacaoAtividade.value = idProg;
		document.form.dataProgramada.value = data;
		document.form.horaProgramada.value = hora;
		document.form.titulo.value = titulo;
		if(descricaoAtividadeOS!= "null"){
			document.form.descricaoAtividadeOS.value = descricaoAtividadeOS;
			document.form.numeroOS.value = numeroOS;
			document.getElementById('atividadeOS').style.display = 'block';
		}else{
			document.getElementById('atividadeOS').style.display = 'none';
		}
		document.form.restore({idExecucaoAtividadePeriodica:idExecucaoAtividadePeriodicaParm});
		$("#POPUP_REGISTRO1").dialog('open')
/* 		$("#POPUP_REGISTRO").toggle();
		$("#POPUP_REGISTRO").animate({height: "toggle"}, { duration: 500 }); */
		//POPUP_REGISTRO.showInYPosition({top:100});
	}

	function gravarForm(){
		document.form.save();
	}

	function refresh(){
		//$('#calendar').fullCalendar( 'rerenderEvents' );
		window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendaAtvPeriodicas/agendaAtvPeriodicas.load?noVoltar=<%=noVoltar%>';
	}

	function refreshEvents(){
		$('#calendar').fullCalendar("refetchEvents");
		$('#calendar').fullCalendar( 'rerenderEvents' );
		$("#POPUP_REGISTRO1").dialog('close')
	}	

	function voltar(){
		//$('#calendar').fullCalendar( 'rerenderEvents' );
		window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
	}	

	function mudaGrupo(idGrp){
		document.formParm.fireEvent('mudaGrupo');
	}
	
	function mudaGrupoPesquisa(idGrp){
		if (document.getElementById('idGrupoAtvPeriodica').value != '') {
			document.formParm.fireEvent('mudaGrupo');
		}else{
			alert('Informe o Grupo de Atividades!');
		}
	}
	
	function mudaPesquisa(idGrp){
		document.formParm.fireEvent('mudaPesquisa');
	}

	function visualizarOrientacoes(){
		document.form.fireEvent('visualizarOrientacoes');
	}

	function configuraMotivoSuspensao(motivo) {
		if (motivo == 'S') 
		   document.getElementById('divMotivoSuspensao').style.display = 'block';
		else   
		   document.getElementById('divMotivoSuspensao').style.display = 'none';
	}
	
	 function setSelectGrupo(elem) {
        var setSelectGrupoPes = document.getElementById("idGrupoPesquisa");
        setSelectGrupoPes.value = elem;   
    }

	function abrirPopupMotivoSuspensaoAtividade() {
		document.getElementById('iframeNovoMotivoSuspensaoAtividade').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/motivoSuspensaoAtividade/motivoSuspensaoAtividade.load?iframe=true";
		$("#POPUP_NOVOMOTIVOSUSPENSAOATIVIDADE").dialog("open");
	}

</script>

<style type="text/css">
/* 
Pedro Lino
23/10/2013 - Sol. 120948
*Para sobrepor o padding do atualiza-antigo.css que está 0%
*Estava sem margem 
*/
.ui-dialog .ui-dialog-content {
	padding: 1% !important;
}

</style>
<link rel='stylesheet' type='text/css' href='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fullcalendar/fullcalendar.css' />
<link rel='stylesheet' type='text/css' href='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/fullcalendar/fullcalendar.print.css' media='print' />
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>

<script>
	var objTab = null;
</script>

<!-- Area de JavaScripts -->

<h2 style="background-color: white !important;"><i18n:message key="agendaAtividade.agendaAtividade" /> </h2>  	
<div id='loading' style='display:none; background-color: red; color: white'><b><i18n:message key="citcorpore.comum.aguardecarregando" /></b></div>
	<form name='formParm' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/agendaAtvPeriodicas/agendaAtvPeriodicas'>
	<div style="background-color: white !important;">
		<table>
	         <tr>
	            <td class="campoEsquerdaSemTamanho"><i18n:message key="gerenciaservico.agendaratividade.grupo" />*</td>
	            <td>
	            	<select name='idGrupoAtvPeriodica' id='idGrupoAtvPeriodica' onchange='mudaGrupo(this.value)'>
	            	</select>
	      
	            </td>
	          	<td>
	            	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	            </td>
	            <td align="center"><i18n:message key="gerenciaservico.grupoPesquisa" /></td>
	            <td>
	            	<select size="1" name="idGrupoPesquisa" id="idGrupoPesquisa" onchange='mudaGrupoPesquisa(this.value)'>
	            		<option selected value="0">			<i18n:message key="citcorpore.comum.todos"/></option>
	            		<option 		 value="1">		   	<i18n:message key="gerenciaIncidente"/></option>
	            		<option 		 value="2">		   	<i18n:message key="requisicaoMudanca"/></option>
	            		<option 		 value="3">		   	<i18n:message key="requisicaoLiberacao"/></option>
	            		<option 		 value="4">		   	<i18n:message key="menu.nome.gerenciaProblema"/></option>
	            	</select>
	            </td>
	            <td>
	            	&nbsp;
	            </td>
	            <td>
	            	<img src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/refresh.png" border="0" style='cursor:pointer' onclick='refreshEvents()' title="refresh" />
	            </td>
	            <%if (!noVoltar.equalsIgnoreCase("true")){%>
                <td width='700px' style='text-align:right'>
                   <img border="0" style=" cursor:pointer; " onclick="voltar()" alt="Voltar" title="Retornar ao menu principal" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/btnvoltar.gif">&nbsp;
                </td>
                <%}%>
	         </tr>
         </table>
	</div>
	</form>
	<div id='calendar' style='background-color: white'></div>

	<%@include file="../../include/rodape.jsp"%>
	

<div id="POPUP_REGISTRO1" title=<i18n:message key='citcorpore.atividadePeriodica.registroAtividade'/>>
	<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/execucaoAtividadePeriodica/execucaoAtividadePeriodica'>
		<input type='hidden' name='idExecucaoAtividadePeriodica'/>
		<input type='hidden' name='idAtividadePeriodica'/>
        <input type='hidden' name='idProgramacaoAtividade'/>
        <div class="tabber" id='tabTela'>
             <div id="tabRegistro"> 
       			<h4><i18n:message key="agenda.registroExecucaoAtividade" /></h4> 
       			<div class="columns clearfix">
       			  <div id="atividadeOS" style="display: none;">
  					<div class="col_66">
  						<div class='col_50'>
							<label  class="" style="cursor: pointer;"><i18n:message key="agenda.numeroOS" /></label>
							<div> 
								<input type='text' name='numeroOS' size="90" readonly="readonly" class="text"/>
							</div>
						</div>
						<div class='col_50'>
							<label  class="" style="cursor: pointer;"><i18n:message key="agenda.descricaoAtividadeOS" /></label>
							<div> 
								<input type='text' name='descricaoAtividadeOS' size="90" readonly="readonly" class="text"/>
							</div>
						</div>
					</div>
				  </div>
				  <div class="col_100">
				  	<label  class="" style="cursor: pointer;"><i18n:message key="agenda.atividade" /></label>
						<div> 
							<input type='text' name='titulo' size="90" readonly="readonly" class="text"/>
						</div>
				  </div>
				  <div class="col_33" >
				  	<label  class="" style="cursor: pointer;"><i18n:message key="agenda.dataProgramada" /></label>
						<div> 
                         <!-- Maycon 30/10/2013 - 09:55 -  retirada o class datepicker para ter somente visualização -->
							 <input type='text' name='dataProgramada' style="width: 120px !important;"  size="10" maxlength="10" class="Format[Date]" readonly='readonly' />
						</div>
				  </div>
				    <div class="col_33" >
				    	<label  class="" style="cursor: pointer;"> <i18n:message key="agenda.horaProgramada" /></label>
						<div> 
							 <input type='text' name='horaProgramada' style="width: 100px !important;"  size="5" maxlength="5" class='Format[Hora] text' readonly='readonly'/>&nbsp;&nbsp;
						</div>
				  
				  </div>
				    <div class="col_33" >
				    <label  >&nbsp;</label>
					    <div>
					    	<button type='button' id="btnVerDetTec" name='btnVerDetTec' class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick="visualizarOrientacoes()">
								<%-- <img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png"> --%>
								<span style="font-size: 12px !important; font-color: white;"><i18n:message key="scripts.orientacaoTecnica" /></span>
							</button>
						</div>
				  
				  </div>
				  	<div class='col_33'>
						<label  class="" style="cursor: pointer;"><i18n:message key="agenda.situacao" /></label>
						<div> 
							<label><input type='radio' name='situacao' value="E" onclick="configuraMotivoSuspensao('E')"/> <i18n:message key="citcorpore.comum.emExecucao" /> </label>
							<label> <input type='radio' name='situacao' value="S" onclick="configuraMotivoSuspensao('S')"/> <i18n:message key="citcorpore.comum.suspenso" /> </label>
							<label>  <input type='radio' name='situacao' value="F" onclick="configuraMotivoSuspensao('F')"/> <i18n:message key="cronograma.executado" /></label>
						</div>
					</div>
					<div class='col_50'>
					<label  class="" style="cursor: pointer;"><i18n:message key="agenda.detalhamento" /></label>
						<div> 
							<textarea rows="3" name="detalhamento" cols="70" style="border: 1px solid black"></textarea>
						</div>
					
					</div>
					<!-- Desenvolvedor: Pedro Lino - Data: 23/10/2013 - Horário: 17:38 - ID Citsmart: 120948 - 
						* Motivo/Comentário: Campo motivo com layout antigo, retirado style -->
					 <div id='divMotivoSuspensao' style='display:none'>
                         <div class='col_50'>
                            <label style="cursor: pointer;" onclick="abrirPopupMotivoSuspensaoAtividade();">
								<i18n:message key="agenda.motivo"/>
								<img  src="<%=br.com.citframework.util.Constantes
								.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" />
							</label>
                                      
                                <div> 
                                	<select name="idMotivoSuspensao" id="idMotivoSuspensao" size="1px"></select>
                                </div>
                         </div>
                         <div class='col_50'>
                             <label class="campoEsquerdaSemTamanho"><i18n:message key="agenda.complemento" /> </label>
                             <div>
                                  <textarea rows="3" name="complementoMotivoSuspensao" cols="70" style="border: 1px solid black"></textarea>
                             </div>
                         </div>
			                  
		           </div>
		             <div class="col_25" >
				  	<label  class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="agenda.dataExecucao" /></label>
						<div> 
							 <input type='text' name='dataExecucao' style="width: 100px !important;" size="10" maxlength="10" class="Format[Date] Valid[Required,Date] Description[citcorpore.comum.dataRealExecucao] text datepicker"/>
						</div>
				  </div>
				    <div class="col_25" >
				    	<label  class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="agenda.horaExecucao" /></label>
						<div> 
							 <input type='text' name='horaExecucao' style="width: 100px !important;" size="5" maxlength="5" class='Format[Hora] Valid[Required,Hora] Description[citcorpore.comum.horaRealExecucao] text'/>
						</div>
				  
				  </div>
       			</div>
				 <table>
		
                </table> 
            </div>
        </div>
     
	</form>
	<form name='formUpload' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/execucaoAtividadePeriodica/execucaoAtividadePeriodica' enctype="multipart/form-data">	
            <div id="tabAnexos" style="border: 1px solid black !important;"> 
            <%-- 	<table>
            		<tr>
            			<td>
            				<img src='<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/file.png' width="16" height="16" border="0"/>
            			</td>
            			<td>
                			<b><i18n:message key="agenda.anexos" /></b> 
            			</td>
            		</tr>
            	</table> --%>
                <div id="anexos">
                    <div id="anexoGeral">
                        <cit:uploadControl style="height:80px;width:670px;border:12px solid white"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
                    </div>
                </div>
            </div>	
	</form> 
	   <table cellpadding='0' cellspacing='0' width='100%'>
           <tr>
               <td style='text-align:right'>
                   <!--  <input type='button' name='btnGravar' value='Gravar' onclick='gravarForm();'/>
                    <input type='button' name='btnFechar' value='Fechar' onclick='$("#POPUP_REGISTRO").hide();'/> -->
                    
                    <button type='button' id="btnGravar" name='btnGravar' style="margin-top: 5px; margin-left: 3px;" class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick="gravarForm()">
				<%-- 	<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png"> --%>
						<span style="font-size: 12px !important; "><i18n:message key="citcorpore.comum.gravar" /></span>
					</button>
							
				<button type='button' id="btnVerDetTec" name='btnVerDetTec' style="margin-top: 5px; margin-left: 3px; " class="ui-button ui-widget ui-state-default ui-corner-all ui-button-text-only" onclick='$("#POPUP_REGISTRO1").dialog("close");'">
				  <%-- <img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png"> --%>
					<span style="font-size: 12px !important; "><i18n:message key="citcorpore.ui.botao.rotulo.Fechar" /></span>
				</button>
               </td>
           </tr>
        </table>           
</div>

<div id="POPUP_ORIENTACAO" >
	<div id='divOrientacao' style='width: 100%; height: 390px; overflow: auto'>
	</div>
</div>

<div id="POPUP_NOVOMOTIVOSUSPENSAOATIVIDADE"  style="overflow: hidden;" title="<i18n:message key="motivoSuspensaoAtividade.motivoSuspensaoAtividade"/>">
	<iframe id='iframeNovoMotivoSuspensaoAtividade' src='about:blank' width="100%" height="100%">
	</iframe>	
</div>

</body>
</html>
							