<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
	<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
	<%@include file="/include/security/security.jsp"%>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<script type="text/javascript"
	src="../../cit/objects/InfoCatalogoServicoDTO.js"></script>

	
	<title><i18n:message key="citcorpore.comum.title" /></title>
	<style type="text/css">
	.table {
		border-left:1px solid #ddd;
	}
	
	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}
	
	.table td {
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
	}
	
	</style>
	<script type="text/javascript">
		var objTab = null;
		var contTable = 0;
		var contInfo = 0;
		addEvent(window, "load", load, false);
		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}
		
		function LOOKUP_CATALOGOSERVICO_select(id, desc) {
			document.form.restore({idcatalogoServico : id});
			document.form.nomeContrato.disabled = true;
		}
	
		function LOOKUP_CATALOGOSERVICOCONTRATO_select(id, desc) {
			document.form.idServicoContrato.value = id;
			document.form.nomeServicoContrato.value = desc;
			//document.form.fireEvent('adicionaGridServico');
			$("#POPUP_DETALHES").dialog("close");
			deleteAllRowsNovoContrato()
			
		}
		function LOOKUP_CONTRATOS_select(id, desc) {
			//função para limpar lookup dos serviços
			//deve ser chamado aqui antes de chamar a função abrePopupServico() para que funcione
			limpar_LOOKUP_CATALOGOSERVICOCONTRATO();
			document.form.nomeContrato.value = desc;
			document.form.idContrato.value = id;
			document.formDetalhe.pesqLockupLOOKUP_CATALOGOSERVICOCONTRATO_IDCONTRATO.value = id;
			$("#POPUP_CONTRATO").dialog("close");
			deleteAllRowsNovoContrato();
		}
		
		function abrePopupContrato(){
			$("#POPUP_CONTRATO").dialog("open");
			document.getElementsByName('btnLimparLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none' 
			document.getElementsByName('btnTodosLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none'
		}
		function abrePopupServico(){
			if(StringUtils.isBlank(document.form.nomeContrato.value)){
				alert(i18n_message("contrato.contrato"));
				document.form.nomeContrato.focus();
				return;
			}else{
				document.form.fireEvent('verificarContratoServico');
				document.getElementsByName('btnLimparLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none' 
				document.getElementsByName('btnTodosLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none'
		
			}
		}		
		
		function validarContratoServico(){
			alert("<i18n:message key="condicao.contratoServico" />");
		}
		
		$(function() {
			$("#POPUP_SERVICOCONTRATO").dialog({
				autoOpen : false,
				width : 750,
				height : 600,
				modal : true,
				show: "fade",
				hide: "fade"
			});
			$("#POPUP_CONTRATO").dialog({
				autoOpen : false,
				width : 750,
				height : 600,
				modal : true,
				show: "fade",
				hide: "fade"
			});
			$("#POPUP_DETALHES").dialog({
				autoOpen : false,
				width : 750,
				height : 600,
				modal : true,
				show: "fade",
				hide: "fade"
			});
		});
		
		function setDataEditor(){
		}
	    
	    function gravar(){
	    	
			var objs = HTMLUtils.getObjectsByTableId('tblInfoCatalogoServico');
			document.form.infoCatalogoServicoSerialize.value = ObjectUtils.serializeObjects(objs)
	    	
	    	document.form.save();
	    	
	    }
	    function limpar(){
	    	document.form.clear();
			document.form.nomeContrato.disabled = false;
	    	deleteAllRows();
	     }
	    
	    function novoInfoServico(){
	    	document.form.idInfoCatalogoServico.value = "";
	    	document.form.nomeServicoContrato.value = "";
	 		document.form.nomeInfoCatalogoServico.value = "";
	 		document.form.rowIndex.value = "";
	    }
	    
	    addItemInfo = function() {
	    	if(StringUtils.isBlank(document.form.nomeCatalogoServico.value) || document.form.nomeCatalogoServico.value == null){
	    		alert(i18n_message("catalogoServico.nomeItemInformacaoCatalogo"));
	    		document.form.nomeCatalogoServico.focus();
	    		return;
	    	}
	    	if(StringUtils.isBlank(document.form.nomeServicoContrato.value) || document.form.nomeServicoContrato.value == null){
	    		alert(i18n_message("catalogoServico.informeServico"));
	    		document.form.nomeServicoContrato.focus();
	    		return;
	    	}
	    
	        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
	            var obj = new CIT_InfoCatalogoServicoDTO();
	             obj.nomeInfoCatalogoServico = document.form.nomeCatalogoServico.value;
	             obj.nomeServicoContrato = document.form.nomeServicoContrato.value;
	             obj.idServicoCatalogo = document.form.idServicoContrato.value;
	             
	             obj.descInfoCatalogoServico = document.form.descCatalogoServico.value;
	    
	            HTMLUtils.addRow('tblInfoCatalogoServico', document.form, null, obj, ['','idServicoCatalogo', 'nomeServicoContrato', 'nomeInfoCatalogoServico', 'descInfoCatalogoServico'],["idServicoCatalogo"], "Serviço já adicionado!", [gerarButtonDelete2], funcaoClickRow, null, false);

	        } else {                
		        var obj = HTMLUtils.getObjectByTableIndex('tblInfoCatalogoServico', document.getElementById('rowIndex').value);
		        obj.nomeInfoCatalogoServico = document.form.nomeCatalogoServico.value;
		        obj.nomeServicoContrato = document.form.nomeServicoContrato.value;
	            obj.idServicoCatalogo = document.form.idServicoContrato.value;
	            obj.descInfoCatalogoServico = document.form.descCatalogoServico.value;
	                   
		        HTMLUtils.updateRow('tblInfoCatalogoServico', document.form, null, obj, ['','idServicoCatalogo', 'nomeServicoContrato', 'nomeInfoCatalogoServico', 'descInfoCatalogoServico'],null, '', [gerarButtonDelete2], funcaoClickRow, null, document.getElementById('rowIndex').value, false);
	        }  
	        limpaDadosTableInfo();
	        HTMLUtils.applyStyleClassInAllCells('tblInfoCatalogoServico', 'celulaGrid');
	        
		}
	    
	    function funcaoClickRow(row, obj){
	    	if(row == null){
	            document.getElementById('rowIndex').value = null;
	            document.form.clear();
	        }else{
	        	document.getElementById('rowIndex').value = row.rowIndex;
	        	
	        	document.form.nomeServicoContrato.value = obj.nomeServicoContrato;
	        	document.form.idServicoContrato.value = obj.idServicoCatalogo; 
	        	document.form.nomeCatalogoServico.value = obj.nomeInfoCatalogoServico;
	        	document.form.descCatalogoServico.value = obj.descInfoCatalogoServico;
	        	
	        }
	    }
	    function deleteAllRows() {
		/* 	var tabela = document.getElementById('tblServicoContrato'); */
			var tabela1 = document.getElementById('tblInfoCatalogoServico');
			//var count = tabela.rows.length;
			var count1 = tabela1.rows.length;

			/* while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			} */
			while (count1 > 1) {
				tabela1.deleteRow(count1 - 1);
				count1--;
			} 
		}
	    
	    function deleteAllRowsNovoContrato(){
	    /* 	var tabela = document.getElementById('tblServicoContrato'); */
	    	var count = tabela.rows.length;
	    	while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
	    }

	function limpaDadosTableInfo(){
		document.form.idServicoContrato.value = ""; 
		document.form.nomeServicoContrato.value = "";
		document.form.nomeCatalogoServico.value = "";
		document.form.descCatalogoServico.value = "";
	}

	<%-- function gerarButtonDelete(row) {
		row.cells[0].innerHTML = '<img id="imgDelServ" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblServicoContrato\', this.parentNode.parentNode.rowIndex);">'
	} --%>
	function gerarButtonDelete2(row) {
		row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblInfoCatalogoServico\', this.parentNode.parentNode.rowIndex);">'
	}
	function deleteLinha(table, index){
		
		HTMLUtils.deleteRow(table, index);
		limpaDadosTableInfo();
	}
	function excluir() {
		if (document.getElementById("idCatalogoServico").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
				document.form.nomeContrato.disabled = false;
			}
		}
	}
	function contCaracteres(valor) {
	    quant = 255;
	    total = valor.length;
	    if(total <= quant)
	    {
	        resto = quant - total;
	        document.getElementById('cont').innerHTML = resto;
	    }
	    else
	    {
	        document.getElementById('descCatalogoServico').value = valor.substr(0,quant);
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
				<h2>
					<i18n:message key="catalogoServico.catalogoNegocio" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message	key="catalogoServico.cadastroCatalogoNegocio" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="catalogoServico.pesquisaCatalogoNegocio" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/catalogoServico/catalogoServico'>
								<div class="columns clearfix">
									<input type="hidden" id="idCatalogoServico" name="idCatalogoServico"/>
									<input type="hidden" id="idServicoContrato" name="idServicoContrato"/>
									<input type="hidden" id="idContrato" name="idContrato"/>
									<input type="hidden" id="servicoSerialize" name="servicoSerialize"/>
									<input type="hidden" id="infoCatalogoServicoSerialize" name="infoCatalogoServicoSerialize"/>
									<input type="hidden" id="rowIndex" name="rowIndex"/>
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="tituloCatalogoServico"  maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
											<label class="campoObrigatorio"><i18n:message key="contrato.contrato" /></label>
											<div>
												<div>
													<input  readonly="readonly" style="width: 90% !important;" 
														type='text' name="nomeContrato" id="nomeContrato" onclick="abrePopupContrato()" maxlength="50" size="50" class="Valid[Required] Description[contrato.contrato]" />
													<img  style=" vertical-align: middle;" 
														src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
												</div>
											</div>
										</fieldset>
									</div>
									<div class="col_66">
									<br>
										<fieldset>
											<legend>
												<i18n:message key="catalogoServico.informacoes_servico" />
											</legend>
											<label><i18n:message key="servico.servico" /></label>
											<div>
												<input  readonly="readonly" style="width: 90% !important;" 
													type='text' name="nomeServicoContrato" id="nomeServicoContrato" onclick="abrePopupServico()" maxlength="50" size="50" class="" />
												<img  style=" vertical-align: middle;" 
													src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											</div>
											<label>
												<i18n:message key="citcorpore.comum.nome" />
											</label>
											<div>
												<input type='text' id="nomeCatalogoServico" name="nomeCatalogoServico" maxlength="100" />
												<button id="buttonAddLimpar" type="button" title="Limpa os dados de informações de serviço" class="light" onclick="limpaDadosTableInfo()" style="float: right !important;">
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
													<span>
														<i18n:message key="citcorpore.comum.limpar" />
													</span>
												</button>
												<button id="buttonAddInfoServico" type="button" class="light" onclick="addItemInfo()" style="float: right !important;">
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/list.png">
													<span>
														<i18n:message key="citcorpore.comum.adicionar" />
													</span>
												</button>
											</div>
											<label id="infoComplementar"><i18n:message key="citcorpore.comum.descricao" /></label>
											<div>
												<div>
													<!-- <textarea id="descCatalogoServico" name="descCatalogoServico" onkeyDown="contCaracteres(this.form.descCatalogoServico,this.form.contDin,255);" onKeyUp="textCounter(this.form.descCatalogoServico,this.form.contDin,255);"  rows="10" cols="100"></textarea> -->
												<textarea id="descCatalogoServico" name="descCatalogoServico" onkeyup="contCaracteres(this.value)" onkeydown="contCaracteres(this.value)" onFocus="contCaracteres(this.value)" rows="10" cols="100"></textarea>
												</div>
 												(<i18n:message key="citcorpore.comum.caracteresrestantes"/>: <span id="cont">255</span>)<br>
												 
											</div>
										</fieldset>
									</div>
									
									<div class="col_66">
										<fieldset>
											<div>
												<table class="table" id="tblInfoCatalogoServico" >
													<tr>
														<th style="width: 1%;"></th>
														<th style="width: 1%;"><i18n:message key="#" /></th>
														<th style="width: 30%;"><i18n:message key="servico.servico" /></th>
														<th style="width: 20%;"><i18n:message key="citcorpore.comum.nome" /></th>
														<th style="width: 47%;"><i18n:message key="citcorpore.comum.descricao" /></th>
													</tr>
												</table>
											</div>
										</fieldset>
									</div>
								</div>
								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();document.form.fireEvent("load");'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
									onclick='excluir();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
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
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_CATALOGOSERVICO' id='LOOKUP_CATALOGOSERVICO' 
										top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_SERVICOCONTRATO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<%-- <div class="box grid_16 tabs" style="width: auto !important">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px; width: 100% !important;">
						<form name='formServicoContrato'>
							<cit:findField formName='formServicoContrato' 
							lockupName='LOOKUP_CATALOGOSERVICOCONTRATO' 
							id='LOOKUP_CATALOGOSERVICOCONTRATO' top='0' left='0' len='550' heigth='400' 
							javascriptCode='true' 
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div> --%>
	</div>
	<div id="POPUP_CONTRATO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs" style="width: auto !important">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px; ">
						<form name='formContrato'style="width: 100% !important;">
							<cit:findField formName='formContrato' 
							lockupName='LOOKUP_CONTRATOS' 
							id='LOOKUP_CONTRATOS' top='0' left='0' len='550' heigth='400' 
							javascriptCode='true' 
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div id="POPUP_DETALHES" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs" style="width: auto !important">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px; width: 100% !important;">
						<form name='formDetalhe'>
							<cit:findField formName='formDetalhe' 
							lockupName='LOOKUP_CATALOGOSERVICOCONTRATO' 
							id='LOOKUP_CATALOGOSERVICOCONTRATO' top='0' left='0' len='550' heigth='400' 
							javascriptCode='true' 
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>

</html>
