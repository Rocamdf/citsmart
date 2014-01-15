<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>

<!doctype html public "">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" />
</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);

		}
	}
	function excluir() {
		var idJornadaTrabalho = document.getElementById("idJornada");
		if (idJornadaTrabalho != null && idJornadaTrabalho.value == 0) {
			alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro "));
			return false;
		}
		if (confirm(i18n_message("citcorpore.comum.deleta")))
			document.form.fireEvent("delete");
	}
	function LOOKUP_JORNADATRABALHO_select(id, desc) {
		document.form.restore({
			idJornada : id
		});
	}
	
	isValidTime = function(objeto){
		 var hora = objeto.value;		
	     if(hora == null || hora.length == 0){
	         return 1;
	     }
	     if(hora.length != 5){
	    	 alert(i18n_message("jornadaTrabalho.formatoHoraInvalido"));
	    	 objeto.focus();
	         return -1;
	     }
	     var h  = hora.substring(0,2);
	     var m  = hora.substring(3,5);
	     if(h > 23 || h < 0){
	    	 alert(i18n_message("jornadaTrabalho.horaInvalida"));
	    	 objeto.focus();
	    	 return -2;
	     }
	     if(m>59 || m<0){
	    	 alert(i18n_message("jornadaTrabalho.minutoInvalido"));
	    	 objeto.focus();
	    	 return -3;
	     }
	     return 1;
	}
	
	function gravar(){
		var ok =isValidTime(inicio1);
		if (ok>0){
			ok=isValidTime(termino1);
		}
		if (ok>0){
			ok=isValidTime(inicio2);
		}
		if (ok>0){
			ok=isValidTime(termino2);
		}
		if (ok>0){
			ok=isValidTime(inicio3);
		}
		if (ok>0){
			ok=isValidTime(termino3);
		}
		if (ok>0){
			ok=isValidTime(inicio4);
		}
		if (ok>0){
			ok=isValidTime(termino4);
		}
		if (ok>0){
			ok=isValidTime(inicio5);
		}
		if (ok>0){
			ok=isValidTime(termino5);
		}
		if (ok>0){
			document.form.save();
		}
	}
	
</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2><i18n:message key="jornadaTrabalho.jornadaTrabalho"/></h2>
			</div>
			<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="jornadaTrabalho.cadastroJornadaTrabalho"/>
					</a></li>
					<li><a href="#tabs-2" class="round_top" ><i18n:message key="jornadaTrabalho.pesquisaJornadaTrabalho"/> 
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/jornadaTrabalho/jornadaTrabalho'>
								<div class="columns clearfix">
									<input type='hidden' name='idJornada' /> 
									<div class="col_50">
										<fieldset style="margin: 0 0 0 0">
											<label class="campoObrigatorio"><i18n:message key="jornadaTrabalho.descricao" /></label>
											<div>
												<input type='text' name="descricao" id="descricao" maxlength="70" class="Valid[Required] Description[jornadaTrabalho.descricao]" />
											</div>
										</fieldset>
									</div>
									</div>
							<div class="columns clearfix">
								<div class="col_50">
									<br>
									<table>
										<tbody>
										<tr>
											<td></td>
											<th><i18n:message key="jornadaTrabalho.horaInicio"/></th>
											<td></td>
											<th><i18n:message key="jornadaTrabalho.horaFim"/></th>
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>1</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio1" name="inicio1"/></td> 
											<td WIDTH=100 align="center"><label><b><i18n:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino1" name="termino1"/></td>
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>2</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio2" name="inicio2"/></td> 
											<td WIDTH=100 align="center"><label><b><i18n:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino2" name="termino2"/></td>
											
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>3</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio3" name="inicio3"/></td> 
											<td WIDTH=100 align="center"><label><b><i18n:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino3" name="termino3"/></td>
											
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>4</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio4" name="inicio4"/></td> 
											<td WIDTH=100 align="center"><label><b><i18n:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino4" name="termino4"/></td>
											<td></td>
										</tr>
										<tr>
											<td WIDTH=100 align="center"><b>5</b></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="inicio5" name="inicio5"/></td> 
											<td WIDTH=100 align="center"><label><b><i18n:message key="jornadaTrabalho.as" /></b></label></td>
											<td><input size="10" maxlength="5" class="Format[Hora]" type="text" id="termino5" name="termino5"/></td>
										</tr>
										</tbody>
									</table>
									<br>
									<br>
									<div>
										<fieldset style="margin-left: 2%; margin-right: 71%;">
											<label><i18n:message key="jornadaTrabalho.cargaHoraria"/></label>
											<input size="10" class="Format[Hora]" type="text" id="cargaHoraria" name="cargaHoraria" readonly="readonly" onclick="document.getElementById('descricao').focus();alert(i18n_message('citcorpore.comum.cargaHorariaCalculadaSistema'));"  />
										</fieldset>
								    </div>
								</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
									onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_JORNADATRABALHO' id='LOOKUP_JORNADATRABALHO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
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
</body>
</html>