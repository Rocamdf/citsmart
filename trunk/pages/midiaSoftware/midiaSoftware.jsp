
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.MidiaSoftwareDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%
		response.setHeader( "Cache-Control", "no-cache");
		response.setHeader( "Pragma", "no-cache");
		response.setDateHeader ( "Expires", -1);
		
		String iframe = "";
	    iframe = request.getParameter("iframe");
	%>
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="../../cit/objects/MidiaSoftwareChaveDTO.js"></script>
	
<script>
    addEvent(window, "load", load, false);
    function load(){		
	
	document.form.afterRestore = function () {
		$('.tabs').tabs('select', 0);
	}
    }	
	function LOOKUP_MIDIASOFTWARE_select(id,desc){
		document.form.restore({idMidiaSoftware:id});
	}
	function excluir() {
		if (document.getElementById("idMidiaSoftware").value != "") {
			if (confirm( i18n_message("midiaSoftware.confirme.excluir"))) {
				document.form.fireEvent("update");
			}
		}
	}	
	function limpar(){
		document.form.clear();
		deleteAllRows();
	}	
	 function deleteAllRows() {
		var tabela = document.getElementById('tblMidiaSoftwareChave');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
		}
	}
	 
	function incrementaLicensas() {
		var tabela = document.getElementById('tblMidiaSoftwareChave');
		var count = tabela.rows.length;
		document.getElementById("licencas").value = count - 1;
	}
	 
	function addItemInfo() {
    	if(StringUtils.isBlank(document.form.chave.value) || document.form.chave.value == null){
    		document.form.chave.focus();
    		return;
    	}
    
        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
            var obj = new CIT_MidiaSoftwareChaveDTO();
             obj.chave = document.form.chave.value;
             obj.qtdPermissoes = document.form.qtdPermissoes.value;
             
            HTMLUtils.addRow('tblMidiaSoftwareChave', document.form, null, obj, ['','chave', 'qtdPermissoes'], null, '', [gerarButtonDelete], funcaoClickRow, null, false);
        } else {                
	        var obj = HTMLUtils.getObjectByTableIndex('tblMidiaSoftwareChave', document.getElementById('rowIndex').value);
	        obj.chave = document.form.chave.value;
	        obj.qtdPermissoes = document.form.qtdPermissoes.value;
	        HTMLUtils.updateRow('tblMidiaSoftwareChave', document.form, null, obj, ['','chave', 'qtdPermissoes'], null, '', [gerarButtonDelete], funcaoClickRow, null, document.getElementById('rowIndex').value, false);
        }  
        limpaDados();
        HTMLUtils.applyStyleClassInAllCells('tblMidiaSoftwareChave', 'celulaGrid');
        incrementaLicensas();
        
	}	 
	function funcaoClickRow(row, obj) {
    	if(row == null){
            document.getElementById('rowIndex').value = null;
            document.form.clear();
        }else{
        	document.getElementById('rowIndex').value = row.rowIndex;
        	document.form.chave.value = obj.chave;
        	document.form.qtdPermissoes.value = obj.qtdPermissoes;
        }
    }	 
	function gerarButtonDelete(row) {
		row.cells[0].innerHTML = '<img id="imgDelServ" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblMidiaSoftwareChave\', this.parentNode.parentNode.rowIndex);">'
	}
	function limpaDados(){
		document.form.chave.value = "";
		document.form.qtdPermissoes.value = 1;
	}
	function deleteLinha(table, index){		
		HTMLUtils.deleteRow(table, index);
		 incrementaLicensas();
		limpaDados();
	}
	function salvar() {
		var objs = HTMLUtils.getObjectsByTableId('tblMidiaSoftwareChave');
		document.form.midiaSoftwareChaveSerializada.value = ObjectUtils.serializeObjects(objs)
		incrementaLicensas();
		document.form.save();
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
	.chave {
		margin: 5px 5px 5px 0;
		width: 70%;
		float: left;
	}
	.qtdPer {
		margin: 5px 0 5px 5px;
		width: 25%;
		float: right;
		
	}
<%
    //se for chamado por iframe deixa apenas a parte de cadastro da página
    if (iframe != null) {
%>
	div#main_container {
		margin: 10px 10px 10px 10px;
	}
<%
    }
%>
div.col_100{
	margin: 10px 10px 10px 10px;
	
}
.menuoverlay{
	overflow: visible;
}
</style>
</head>
<body>	
<div id="wrapper">
	<%
	    if (iframe == null) {
	%>
		<%@include file="/include/menu_vertical.jsp"%>
	<%
	    }
	%>
	<div id="main_container" class="main_container container_16 clearfix">
	<%
	    if (iframe == null) {
	%>	
		<%@include file="/include/menu_horizontal.jsp"%>
	<%
	    }
	%>
					
	<div class="flat_area grid_16">
			<h2><i18n:message key="midiaSoftware.midiaDefinitiva" /></h2>						
	</div>
  	<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><i18n:message key="midiaSoftware.cadastro" /></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><i18n:message key="midiaSoftware.pesquisa" /></a>
				</li>
			</ul>				
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form' 
							action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/midiaSoftware/midiaSoftware'>
								<div class="columns clearfix">
									<input type='hidden' name='idMidiaSoftware' id='idMidiaSoftware' /> 
									<input type='hidden' name='dataInicio' id='dataInicio' />
									<input type="hidden" id="rowIndex" name="rowIndex"/>
									<input type="hidden" id="midiaSoftwareChaveSerializada" name="midiaSoftwareChaveSerializada"/>
									
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome" />
											</label>
											<div >
												<input id="nome" name="nome" type='text' maxlength="80"
													class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="midiaSoftware.endFisico" />
											</label>
											<div>
												<input id="endFisico" type="text" name="endFisico" maxlength="200" class="Valid[Required] Description[midiaSoftware.endFisico]" />
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset>
											<label><i18n:message key="midiaSoftware.endLogico" />
											</label>
											<div>
												<input id="endLogico" type="text" name="endLogico" maxlength="200" class="Description[midiaSoftware.endLogico]" />
											</div>
										</fieldset>
									</div>

									<div class="col_33">
										<fieldset   style="height: 52px">
											<label class="campoObrigatorio"><i18n:message key="midiaSoftware.tipoMidia" /> </label>
											<div>
											<select id="idMidia" name='idMidia' class="Valid[Required] Description[midiaSoftware.tipoMidia]"></select>
											</div>
										</fieldset>
										</div>
										
										<div class = "col_33">
										<fieldset>
											<label><i18n:message key="midiaSoftware.tipoSoftware" /></label>
											<div>
												<select id="idTipoSoftware" name='idTipoSoftware' class="Description[midiaSoftware.tipoSoftware]"></select>
											</div>
										</fieldset>
									</div>
									<div class="col_16">
										<fieldset   style="height: 52px">
											<label><i18n:message key="midiaSoftware.licencas" />
											</label>
											<div style = "width: 50px">
												<input id="licencas" type="text" readonly="readonly" name="licencas"  maxlength="6" class="Format[Numero] Description[midiaSoftware.licencas]" />
											</div>
										</fieldset>
									</div>
									
									<div class="col_16">
										<fieldset   style="height: 52px">
											<label> <i18n:message key="midiaSoftware.versao" />
											</label>
											<div style = "width: 50px">
												<input id="versao" type="text" name="versao"
													maxlength="20" 
													class="Description[midiaSoftware.versao] Format[Numero]" />
											</div>
										</fieldset>
									</div>
									</div>
									<div class="col_66">
										<fieldset>
											<div>
												<!-- 
													Adicionado label
													@autor flavio.santana
													28/10/2013 10:28
												 -->
												<div class="col_60 chave">
													<label><i18n:message key="midiaSoftware.chave" /></label>
													<div><input type='text' id="chave" name="chave" maxlength="200" /></div>
												</div>
												<div class="col_30 qtdPer">
													<label><i18n:message key="midiaSoftware.qtdPermissoes" /></label>
													<div><input type='text' id="qtdPermissoes" name="qtdPermissoes" maxlength="5" class="Format[Numero]" value="1" /></div>
												</div>
												<button id="buttonAddLimpar" type="button" class="light" onclick="limpaDados()" style="float: right !important;">
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
													<span>
														<i18n:message key="citcorpore.comum.limpar" />
													</span>
												</button>
												<button id="buttonAddChave" type="button" class="light" onclick="addItemInfo()" style="float: right !important;">
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/list.png">
													<span>
														<i18n:message key="citcorpore.comum.adicionar" />
													</span>
												</button>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<table class="tableLess" id="tblMidiaSoftwareChave" >
											<thead>
												<tr>
													<th></th>
													<th><i18n:message key="midiaSoftware.chave" /></th>
													<th><i18n:message key="midiaSoftware.qtdPermissoes" /></th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="col_100">
								<button type='button' name='btnGravar' class="light" onclick='salvar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='limpar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message	key="citcorpore.comum.excluir" />
									</span>
								</button>
							   </div>
								<div id="popupCadastroRapido">
			                           <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
		                        </div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_MIDIASOFTWARE' id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
