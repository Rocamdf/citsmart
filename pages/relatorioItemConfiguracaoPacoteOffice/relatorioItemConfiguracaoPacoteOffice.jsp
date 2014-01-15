<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.UsuarioDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Relatorio Item Configuracao PacoteOffice</title>
<%
	response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
	<%@ include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title" /></title>
	<%@ include file="/include/noCache/noCache.jsp" %>
	<%@ include file="/include/menu/menuConfig.jsp" %>
	<%@ include file="/include/header.jsp" %>

	<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="../../cit/objects/MidiaSoftwareChaveDTO.js"></script>
	<script type="text/javascript">
		$(function() {
			$("#POPUP_MIDIASOFTWARE").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$('#duplicado').change(function() {
			  if (!$(this).is(':checked')) {
				  $('#contentChaves').hide();
				  $('#softwaresL').attr('disabled', false);
				  document.form.fireEvent("listaChavesMidiaSoftware");
			  }else{
				  $('#contentChaves').show();
				  $('#softwaresL').attr('disabled', true);
			  }
			});
			
		});
		
		function LOOKUP_MIDIASOFTWARE_select(id, desc) {
			document.form.idMidiaSoftware.value = id;
			document.getElementById("nomeMidia").value = desc;
			document.form.fireEvent("listaChavesMidiaSoftware");
			 $('#duplicados').show();
			 $('#softwares').show();
			$("#POPUP_MIDIASOFTWARE").dialog("close");
		}
	
		function abrePopupMidia(){
			$("#POPUP_MIDIASOFTWARE").dialog("open");
		}
		
		function imprimirRelatorioPacoteOffice(){				
			serializa();
			if(document.form.midiaSoftwareChavesSerealizadas.value == '' && confirm(i18n_message("relatorio.office.desejaListarTodos"))){
				document.form.fireEvent("imprimirRelatorioPacoteOffice");
			}else {
				document.form.fireEvent("imprimirRelatorioPacoteOffice");
			}				
		}
		
		function imprimirRelatorioPacoteOfficeXls(){				
			serializa();
			if(document.form.midiaSoftwareChavesSerealizadas.value == '' && confirm(i18n_message("relatorio.office.desejaListarTodos"))){
				document.form.fireEvent("imprimirRelatorioPacoteOfficeXls");
			}else {
				document.form.fireEvent("imprimirRelatorioPacoteOfficeXls");
			}				
		}
		
		serializa = function() {
			try {
				var tabela = document.getElementById('tblMidiaSoftwareChave');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var baselines = new Array();
				for ( var i = 0; i < count; i++) {
					var trObj = document.getElementById('idMidiaSoftwareChave' + i);
					if (!trObj)
						continue;
					else if(trObj.checked){
						baselines[contadorAux] = get(i);
						contadorAux = contadorAux + 1;
						continue;
					}	
				}
				var objs = ObjectUtils.serializeObjects(baselines);
				document.form.midiaSoftwareChavesSerealizadas.value = objs;
				return true;
			}catch(e){
				
			}
		}
		
		get = function(seq) {
			var midiaSoftwareChaveDTO = new CIT_MidiaSoftwareChaveDTO();
			midiaSoftwareChaveDTO.sequencia = seq;
			midiaSoftwareChaveDTO.idMidiaSoftwareChave = eval('document.form.idMidiaSoftwareChave' + seq + '.value');
			midiaSoftwareChaveDTO.chave = eval('document.form.chave' + seq + '.value');
			return midiaSoftwareChaveDTO;
		}
		
		function limpar() {
			$("#contentChaves").text("");
			$("#nomeMidia").val("");
			$("#midiaSoftwareChavesSerealizadas").val("");
			$('#duplicados').hide();
			$('#softwares').hide();
		}

	</script>
	<style type="text/css">
		.tableLess {
		  font-family: arial, helvetica !important;
		  font-size: 10pt !important;
		  cursor: default !important;
		  margin: 0 !important;
		  background: white !important;
		  border-spacing: 0  !important;
		  width: 100%  !important;
		}
		
		.tableLess tbody {
		  background: transparent  !important;
		}
		
		.tableLess * {
		  margin: 0 !important;
		  vertical-align: middle !important;
		  padding: 2px !important;
		}
		
		.tableLess thead th {
		  font-weight: bold  !important;
		  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
		  text-align: center  !important;
		}
		
		.tableLess tbody tr:ACTIVE {
		  background-color: #fff  !important;
		}
		
		.tableLess tbody tr:HOVER {
		  background-color: #e7e9f9 ;
		  cursor: pointer;
		}
		
		.tableLess th {
		  border: 1px solid #BBB  !important;
		  padding: 6px  !important;
		}
		
		.tableLess td{
		  border: 1px solid #BBB  !important;
		  padding: 6px 10px  !important;
		}
		div.main_container .box {
			margin-top: -0.3em!important;

		}
	</style>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="RelatorioItemConfiguracaoPacoteOffice.RelatorioItemConfiguracaoPacoteOffice" /></a></li>
				</ul>
				<div class="toggle_container">
					<div class="block" >
						<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/relatorioItemConfiguracaoPacoteOffice/relatorioItemConfiguracaoPacoteOffice'>
								<input  type="hidden" name="idMidiaSoftware" id="idMidiaSoftware" />
								<input  type="hidden" name="midiaSoftwareChavesSerealizadas" id="midiaSoftwareChavesSerealizadas" />
								<div class="columns clearfix col_66">
									<div class="col_50">
										<fieldset>
											<label style="cursor: pointer;"><i18n:message key="itemConfiguracaoTree.midia"/></label>
											<div> 
												<input id="nomeMidia" name="nomeMidia" type="text" readonly="readonly" onclick="abrePopupMidia()"  />												
											</div>
										</fieldset>
									</div>			
									<div class="col_25" id="softwares" style="display: none;">
										<fieldset id="softwaresL">
											<label style="cursor: pointer;">Softwares</label>
											<div>
												<label><input checked="checked" type="radio" value="S" name="contem" id="contem">Licenciados</label>	
												<label><input type="radio" value="N" name="contem" id="contem">Não licenciados</label>
											</div>
										</fieldset>									
									</div>
									<div class="col_25" id="duplicados" style="display: none;">
										<fieldset>
											<label style="cursor: pointer;"> 
												<input id="duplicado" name="duplicado" type="checkbox" value="S"  />	
												Procurar chaves duplicadas											
											</label>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<div id="contentChaves" style="display: none">
												
											</div>
										</fieldset>
									</div>
								</div>
								<div class="col_100">
									<fieldset>
									<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioPacoteOffice()"
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png">
											<span><i18n:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnRelatorio' class="light"
											onclick="imprimirRelatorioPacoteOfficeXls()"
											style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png">
											<span><i18n:message key="citcorpore.comum.gerarRelatorio" /></span>
										</button>
										<button type='button' name='btnLimpar' class="light"
											onclick='limpar()' style="margin: 20px !important;">
											<img
												src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
											<span><i18n:message key="citcorpore.comum.limpar" /></span>
										</button>
										
									</fieldset>
								</div>
								
							</form>
							</div>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
	<div id="POPUP_MIDIASOFTWARE" title='<i18n:message key="itemConfiguracaoTree.pesquisaMidia"/>' style="display: none;">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaMidiaSoftware'>
								<cit:findField formName='formPesquisaMidiaSoftware' 
								lockupName='LOOKUP_MIDIASOFTWARE' 
								id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400' 
								javascriptCode='true' 
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>				
		</div>
</body>
</html>