<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n"%>
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
		<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
		<%@include file="/include/security/security.jsp"%>
		<%@include file="/include/noCache/noCache.jsp"%>
		<%@include file="/include/header.jsp"%>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

		<title><i18n:message key="citcorpore.comum.title" /></title>
		
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
		<script type="text/javascript" src="../../cit/objects/ControleContratoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/ControleContratoPagamentoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/ControleContratoTreinamentoDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/ControleContratoOcorrenciaDTO.js"></script>
		<script type="text/javascript" src="../../cit/objects/ControleContratoVersaoDTO.js"></script>
				
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
			
			label{
				margin-top: 3px !important;
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
			 //somente numeros
		      jQuery.fn.numbersOnly = function(){
		        var $teclas = {8:'backspace',9:'tab',13:'enter',48:0,49:1,50:2,51:3,52:4,53:5,54:6,55:7,56:8,57:9};    
		        $(this).keypress(function(e){
		          var keyCode = e.keyCode?e.keyCode:e.which?e.which:e.charCode;
		          if(keyCode in $teclas){
		            return true;
		          }else{
		            return false;
		          }
		        });
		        return $(this);
		      }
			 
		      $(document).ready(function () {
				$('.datepicker').datepicker();
				/* $('#parcelaText').numbersOnly();
				$('#telefone1').numbersOnly();
				$('#telefone2').numbersOnly(); */
		  	});
			
			
			
			function LOOKUP_CONTROLECONTRATO_select(id, desc) {
				JANELA_AGUARDE_MENU.show();
				document.form.restore({idControleContrato : id});
				
			}
			
			function LOOKUP_SOLICITANTE_select(id, desc) {
				document.form.idEmpregadoTreinamento.value = id;
				document.form.nomeInstrutorTreinamentoText.value = desc;
				$("#POPUP_INSTRUTOR").dialog("close");
			}
			
			function LOOKUP_SOLICITANTEOCORRENCIA_select(id, desc) {
				document.form.idEmpregadoOcorrencia.value = id;
				document.form.usuarioOcorrenciaText.value = desc;
				$("#POPUP_USUARIOOCORRENCIA").dialog("close");
			}
			
			function LOOKUP_CATALOGOSERVICOCONTRATO_select(id, desc) {
				document.form.idServicoContrato.value = id;
				document.form.fireEvent('adicionaGridServico');
				$("#POPUP_SERVICOCONTRATO").dialog("close");
				
			}
			function LOOKUP_CONTRATOS_select(id, desc) {
				document.form.nomeContrato.value = desc;
				document.form.idContrato.value = id;
				//document.formServicoContrato.pesqLockupLOOKUP_CATALOGOSERVICOCONTRATO_IDCONTRATO.value = id;
				$("#POPUP_CONTRATO").dialog("close");
				//deleteAllRowsNovoContrato()
			}
			
			function abrePopupContrato(){
				$("#POPUP_CONTRATO").dialog("open");
				/* document.getElementsByName('btnLimparLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none' 
				document.getElementsByName('btnTodosLOOKUP_CATALOGOSERVICOCONTRATO')[0].style.display = 'none' */
			}
			function abrePopupInstrutor(){
				$("#POPUP_INSTRUTOR").dialog("open");
	
			}
			function abrePopupUsuarioOcorrencia(){
				$("#POPUP_USUARIOOCORRENCIA").dialog("open");
	
			}
			function abrePopupVersao(){
				$("#POPUP_VERSAO").dialog("open");
	
			}
			function abrePopupServico(){
					if(StringUtils.isBlank(document.form.colaboradorInstrutor.value)){
						alert(i18n_message("controlecontrato.colaboradorinstrutor"));
						document.form.colaboradorInstrutor.focus();
						return;
					}else{
						$("#POPUP_INSTRUTOR").dialog("open");
					}
			}
			$(function() {
				$("#POPUP_INSTRUTOR").dialog({
					autoOpen : false,
					width : 750,
					height : 600,
					modal : true,
					show: "fade",
					hide: "fade"
				});
				
				$("#POPUP_USUARIOOCORRENCIA").dialog({
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
			});
			
			function setDataEditor(){
				var oEditor = FCKeditorAPI.GetInstance("descOcorrencia") ;
			    oEditor.SetData(document.form.descInfoCatalogoServico.value);
			}
			var oFCKeditor = new FCKeditor("") ;
		    function onInitQuestionario(){
		       <%--  oFCKeditor.BasePath = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
		       	oFCKeditor.Config['ToolbarStartExpanded'] = false ;
		       	oFCKeditor.ToolbarSet   = 'Basic' ;
		        oFCKeditor.Width = '50%' ;
		        oFCKeditor.Height = '150' ;
		      oFCKeditor.ReplaceTextarea() ;  --%> 
		    }
		    HTMLUtils.addEvent(window, "load", onInitQuestionario, false);
		    
		    function gravar(){
		    	
		    	 if(StringUtils.isBlank(document.form.cliente.value)){
		    		alert(i18n_message("citcorpore.controleContrato.cliente"));
		    		document.form.cliente.focus();
		    		return;
		    	}else{ 
		    		
		    	var objVersao = HTMLUtils.getObjectsByTableId('tblVersao');
				document.form.versaoSerialize.value = ObjectUtils.serializeObjects(objVersao)
		    	
				var objPag = HTMLUtils.getObjectsByTableId('tblPagamento');
				document.form.pagamentoSerialize.value = ObjectUtils.serializeObjects(objPag)
				
				var objTreina = HTMLUtils.getObjectsByTableId('tblTreinamento');
				document.form.treinamentoSerialize.value = ObjectUtils.serializeObjects(objTreina)
				
				var objOcorrencia = HTMLUtils.getObjectsByTableId('tblOcorrencia');
				document.form.ocorrenciaSerialize.value = ObjectUtils.serializeObjects(objOcorrencia)
				
				var checkModulo = document.form.moduloAtivo;
				var itensCheck = "";
				for (i = 1; i < (checkModulo.length); i++) {
					if (checkModulo[i].checked == true) {
						itensCheck += checkModulo[i].value;
						itensCheck += ";";
						
					}
				}
				document.form.lstModulos.value = itensCheck;
		  
		    	document.form.save();
		    	 } 
		    }
		    function limpar(){
		    	document.form.clear();
				document.form.nomeContrato.disabled = false;
		    	deleteAllRows();
		    	var oEditor = FCKeditorAPI.GetInstance("descCatalogoServico");
			    oEditor.SetData('');
	
		     }
		    
		    function novoInfoServico(){
		    	document.form.idInfoCatalogoServico.value = "";
		 		document.form.nomeInfoCatalogoServico.value = "";
		 		document.form.rowIndex.value = "";
		    }
		    
		    //ADDROW/UPDATEROW GRID PAGAMENTO	
		    addGridPagamento = function() {
		    	if(StringUtils.isBlank(document.form.dataPagamentoText.value) || document.form.dataPagamentoText.value == null){
		    		alert(i18n_message("citcorpore.comum.data"));
		    		document.form.dataPagamentoText.focus();
		    		return;
		    	}
		    	if(StringUtils.isBlank(document.form.parcelaText.value) || document.form.parcelaText.value == null){
		    		alert(i18n_message("citcorpore.controleContrato.parcela"));
		    		document.form.parcelaText.focus();
		    		return;
		    	}
		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoPagamentoDTO();
		             obj.dataCcPagamento = document.form.dataPagamentoText.value;
		             obj.parcelaCcPagamento = document.form.parcelaText.value;
		             obj.dataAtrasoCcPagamento = document.form.dataAtrasoText.value;
		    
		            HTMLUtils.addRow('tblPagamento', document.form, null, obj, ['','dataCcPagamento', 'parcelaCcPagamento', 'dataAtrasoCcPagamento'], null, '', [gerarButtonDeletePagamento], funcaoClickRowPagamento, null, false);
		            document.form.dataPagamentoText.value = "";
			           document.form.parcelaText.value = "";
			           document.form.dataAtrasoText.value = "";
			           novoItem();
		        } else {                
			        var obj = HTMLUtils.getObjectByTableIndex('tblPagamento', document.getElementById('rowIndex').value);
			        obj.dataCcPagamento = document.form.dataPagamentoText.value;
		            obj.parcelaCcPagamento = document.form.parcelaText.value;
		            obj.dataAtrasoCcPagamento = document.form.dataAtrasoText.value;
		             
			        HTMLUtils.updateRow('tblPagamento', document.form, null, obj, ['','dataCcPagamento', 'parcelaCcPagamento','dataAtrasoCcPagamento'], null, '', [gerarButtonDeletePagamento], funcaoClickRowPagamento, null, document.getElementById('rowIndex').value, false);
			       document.form.dataPagamentoText.value = "";
		           document.form.parcelaText.value = "";
		           document.form.dataAtrasoText.value = "";
		           novoItem();
		        }  
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblPagamento', 'celulaGrid');
			}
		    
		    //ADDROW/UPDATEROW GRID TREINAMENTO
		    addGridTreinamento = function() {
		    	if(StringUtils.isBlank(document.form.dataTreinamentoText.value) || document.form.dataTreinamentoText.value == null){
		    		alert(i18n_message("citcorpore.comum.data"));
		    		document.form.dataPagamento.focus();
		    		return;
		    	}
		    	if(StringUtils.isBlank(document.form.nomeTreinamentoText.value) || document.form.nomeTreinamentoText.value == null){
		    		alert(i18n_message("citcorpore.controleContrato.treinamento"));
		    		document.form.parcela.focus();
		    		return;
		    	}
		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoTreinamentoDTO();
		             obj.dataCcTreinamento = document.form.dataTreinamentoText.value;
		             obj.nomeCcTreinamento = document.form.nomeTreinamentoText.value;
		             obj.idEmpregadoTreinamento = document.form.idEmpregadoTreinamento.value;
		             obj.nomeInstrutorCcTreinamento = document.form.nomeInstrutorTreinamentoText.value;
		    
		            HTMLUtils.addRow('tblTreinamento', document.form, null, obj, ['','dataCcTreinamento', 'nomeCcTreinamento','nomeInstrutorCcTreinamento'], null, '', [gerarButtonDeleteTreinamento], funcaoClickRowTreinamento, null, false);
		            document.form.dataTreinamentoText.value = "";
		        	document.form.nomeTreinamentoText.value = "";
		        	document.form.nomeInstrutorTreinamentoText.value = "";
		        	document.form.idEmpregadoTreinamento.value = "";
		        	novoItem()
		        } else {                
			        var obj = HTMLUtils.getObjectByTableIndex('tblTreinamento', document.getElementById('rowIndex').value);
			        
			        obj.dataCcTreinamento = document.form.dataTreinamentoText.value;
		            obj.nomeCcTreinamento = document.form.nomeTreinamentoText.value;
		            obj.nomeInstrutorCcTreinamento = document.form.nomeInstrutorTreinamentoText.value;
		            obj.idEmpregadoTreinamento = document.form.idEmpregadoTreinamento.value;
		             
			        HTMLUtils.updateRow('tblTreinamento', document.form, null, obj, ['','dataCcTreinamento', 'nomeCcTreinamento','nomeInstrutorCcTreinamento'], null, '', [gerarButtonDeleteTreinamento], funcaoClickRowTreinamento, null, document.getElementById('rowIndex').value, false);
			        document.form.dataTreinamentoText.value = "";
		        	document.form.nomeTreinamentoText.value = "";
		        	document.form.nomeInstrutorTreinamentoText.value = "";
		        	document.form.idEmpregadoTreinamento.value = "";
		        	novoItem();
		        
		        }  
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblTreinamento', 'celulaGrid');
			}
		    
		    //ADDROW/UPDATEROW GRID OCORRENCIA
		    addGridOcorrencia = function() {
		    	 if(StringUtils.isBlank(document.form.dataOcorrencia.value) || document.form.dataOcorrencia.value == null){
		    		alert(i18n_message("controlecontrato.dataOcorrencia"));
		    		document.form.dataOcorrencia.focus();
		    		return;
		    	}
		    	if(StringUtils.isBlank(document.form.assuntoOcorrencia.value) || document.form.assuntoOcorrencia.value == null){
		    		alert(i18n_message("controlecontrato.assuntoOcorrencia"));
		    		document.form.assuntoOcorrencia.focus();
		    		return;
		    	} 
		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoOcorrenciaDTO();
		             obj.dataCcOcorrencia = document.form.dataOcorrenciaText.value;
		             obj.assuntoCcOcorrencia = document.form.assuntoOcorrenciaText.value;
		             obj.empregadoCcOcorrencia = document.form.usuarioOcorrenciaText.value;
		             obj.idEmpregadoOcorrencia = document.form.idEmpregadoOcorrencia.value;
		    
		            HTMLUtils.addRow('tblOcorrencia', document.form, null, obj, ['','dataCcOcorrencia', 'assuntoCcOcorrencia', 'empregadoCcOcorrencia'], null, '', [gerarButtonDeleteOcorrencia], funcaoClickRowOcorrencia, null, false);
		            document.form.dataOcorrenciaText.value = "";
		            document.form.assuntoOcorrenciaText.value = "";
		            document.form.usuarioOcorrenciaText.value = "";
		            document.form.idEmpregadoOcorrencia.value = "";
		            novoItem();
		        } else {                
			        var obj = HTMLUtils.getObjectByTableIndex('tblOcorrencia', document.getElementById('rowIndex').value);
			        obj.dataCcOcorrencia = document.form.dataOcorrenciaText.value;
		            obj.assuntoCcOcorrencia = document.form.assuntoOcorrenciaText.value;
		            obj.empregadoCcOcorrencia = document.form.usuarioOcorrenciaText.value;
		            obj.idEmpregadoOcorrencia = document.form.idEmpregadoOcorrencia.value;
	
		             
			        HTMLUtils.updateRow('tblOcorrencia', document.form, null, obj, ['','dataCcOcorrencia', 'assuntoCcOcorrencia', 'empregadoCcOcorrencia'], null, '', [gerarButtonDeleteOcorrencia], funcaoClickRowOcorrencia, null, document.getElementById('rowIndex').value, false);
			        document.form.dataOcorrenciaText.value = "";
		            document.form.assuntoOcorrenciaText.value = "";
		            document.form.usuarioOcorrenciaText.value = "";
		            document.form.idEmpregadoOcorrencia.value = "";
		            novoItem();
		        }  
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblOcorrencia', 'celulaGrid');
			}
		    
		    addGridVersao = function() {
		    	 if(StringUtils.isBlank(document.form.nomeVersaoText.value) || document.form.nomeVersaoText.value == null){
		    		alert(i18n_message("citcorpore.controleContrato.versao"));
		    		document.form.nomeVersao.focus();
		    		return;
		    	}
		   
		        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
		            var obj = new CIT_ControleContratoVersaoDTO();
		            obj.nomeCcVersao = document.form.nomeVersaoText.value;
		    
		            HTMLUtils.addRow('tblVersao', document.form, null, obj, ['','nomeCcVersao'], null, '', [gerarButtonDeleteVersao], funcaoClickRowVersao, null, false);
		            document.form.nomeVersaoText.value = "";
		            novoItem();
		        } else {                
			        var obj = HTMLUtils.getObjectByTableIndex('tblVersao', document.getElementById('rowIndex').value);
			        obj.nomeCcVersao = document.form.nomeVersaoText.value;
	
		             
			        HTMLUtils.updateRow('tblVersao', document.form, null, obj, ['','nomeCcVersao'], null, '', [gerarButtonDeleteVersao], funcaoClickRowVersao, null, document.getElementById('rowIndex').value, false);
			        document.form.nomeVersaoText.value = "";
		            novoItem();
		        }  
		     //  limpaDadosTableInfo();
		        HTMLUtils.applyStyleClassInAllCells('tblVersao', 'celulaGrid');
			}
		    
		    function funcaoClickRowPagamento(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.dataPagamentoText.value = obj.dataCcPagamento;
		        	document.form.parcelaText.value = obj.parcelaCcPagamento;
		        	document.form.dataAtrasoText.value = obj.dataAtrasoCcPagamento;
		        	
		        }
		    }
		    
		    function funcaoClickRowPagamentoRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.dataPagamento.value = obj.dataCcPagamento;
		        	document.form.parcela.value = obj.parcelaCcPagamento;
		        	document.form.dataAtraso.value = obj.dataAtrasoCcPagamento;
		        	
		        }
		    }
		    function funcaoClickRowTreinamento(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.dataTreinamentoText.value = obj.dataCcTreinamento;
		        	document.form.nomeTreinamentoText.value = obj.nomeCcTreinamento;
		        	document.form.nomeInstrutorTreinamentoText.value = obj.nomeInstrutorCcTreinamento;
		        	document.form.idEmpregadoTreinamento.value = obj.idEmpregadoTreinamento;
		        	
		        }
		    }
		    function funcaoClickRowTreinamentoRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.dataTreinamentoText.value = obj.dataCcTreinamento;
		        	document.form.nomeTreinamentoText.value = obj.nomeCcTreinamento;
		        	document.form.nomeInstrutorTreinamentoText.value = obj.nomeInstrutorCcTreinamento;
		        	document.form.idEmpregadoTreinamento.value = obj.idEmpregadoTreinamento;
		        	
		        }
		    }
		    
		    function funcaoClickRowOcorrencia(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.dataOcorrenciaText.value = obj.dataCcOcorrencia;
		        	document.form.assuntoOcorrenciaText.value = obj.assuntoCcOcorrencia;
		        	document.form.usuarioOcorrenciaText.value = obj.empregadoCcOcorrencia;
		        	document.form.idEmpregadoOcorrencia.value = obj.idEmpregadoOcorrencia;
		        	
		        	
		        }
		    }
		    function funcaoClickRowOcorrenciaRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.dataOcorrenciaText.value = obj.dataCcOcorrencia;
		        	document.form.assuntoOcorrenciaText.value = obj.assuntoCcOcorrencia;
		        	document.form.usuarioOcorrenciaText.value = obj.empregadoCcOcorrencia;
		        	
		        	
		        }
		    }
		    function funcaoClickRowVersaoRes(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.nomeVersaoText.value = obj.nomeCcVersao;
		        }
		    }
		    function funcaoClickRowVersao(row, obj){
		    	if(row == null){
		            document.getElementById('rowIndex').value = null;
		            document.form.clear();
		        }else{
		        	document.getElementById('rowIndex').value = row.rowIndex;
		        	
		        	document.form.nomeVersaoText.value = obj.nomeCcVersao;
		        }
		    }
		    function deleteAllRows() {
				var tabela = document.getElementById('tblPagamento');
				var tabela1 = document.getElementById('tblTreinamento');
				var tabela2 = document.getElementById('tblOcorrencia');
				var tabela3 = document.getElementById('tblVersao');
				var count = tabela.rows.length;
				var count1 = tabela1.rows.length;
				var count2 = tabela2.rows.length;
				var count3 = tabela3.rows.length;
				
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				while (count1 > 1) {
					tabela1.deleteRow(count1 - 1);
					count1--;
				} 
				while (count2 > 1) {
					tabela2.deleteRow(count2 - 1);
					count2--;
				}
				while (count3 > 1) {
					tabela3.deleteRow(count3 - 1);
					count3--;
				}
			}
			
		    function novoItem(){
		 		document.form.rowIndex.value = "";
		    }
				
			function gerarButtonDeletePagamento(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblPagamento\', this.parentNode.parentNode.rowIndex);">'
			}
			function gerarButtonDeleteTreinamento(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblTreinamento\', this.parentNode.parentNode.rowIndex);">'
			}
			function gerarButtonDeleteOcorrencia(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblOcorrencia\', this.parentNode.parentNode.rowIndex);">'
			}
			function gerarButtonDeleteVersao(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblVersao\', this.parentNode.parentNode.rowIndex);">'
			}
			function deleteLinha(table, index){
				HTMLUtils.deleteRow(table, index);
			//	limpaDadosTableInfo();
			}
			geraCheckButt = function(row, obj){
				row.cells[0].innerHTML = '<input type="checkbox" name="moduloAtivo" id="moduloAtivo'+obj.idModuloSistema+'" onclick="selecionaModuloAtivo('+ obj.idModuloSistema +')" value="'+ obj.idModuloSistema +'" />';
			}
			function excluir() {
				if (document.getElementById("idControleContrato").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
			}
			
			jQuery(function($){ 
				$('#telefone1').mask('(999) 9999-9999');
				$('#telefone2').mask('(999) 9999-9999');
		    });
			
		</script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
				<div class="flat_area grid_16">
					<h2>
						<i18n:message key="citcorpore.controleContrato.controleContrato" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li><a href="#tabs-1"><i18n:message	key="citcorpore.controleContrato.cadastroControleContrato" /></a></li>
						<li><a href="#tabs-2" class="round_top"><i18n:message key="citcorpore.controleContrato.pesquisaControleContrato" /></a></li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/controleContrato/controleContrato">
									<input type="hidden" id="idControleContrato" name="idControleContrato"/>
									<input type="hidden" id="idContrato" name="idContrato"/>
									<input type="hidden" id="pagamentoSerialize" name="pagamentoSerialize"/>
									<input type="hidden" id="treinamentoServicoSerialize" name="treinamentoSerialize"/>
									<input type="hidden" id="ocorrenciaSerialize" name="ocorrenciaSerialize"/>
									<input type="hidden" id="versaoSerialize" name="versaoSerialize"/>
									<input type="hidden" id="lstModulos" name="lstModulos"/>
									<input type="hidden" id="rowIndex" name="rowIndex"/>
									
									<div class="columns clearfix">
										<!-- Início dados Gerais -->
										<div class="col_60">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="contrato.contrato" />
												</label>
												<div>
													<input readonly="readonly" style="width: 90% !important;" type='text' name="nomeContrato" id="nomeContrato" onclick="abrePopupContrato()" maxlength="50" size="50" class="Valid[Required] Description[contrato.contrato]" />
												    <img style="vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.numeroSubscricao" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="numeroSubscricao"  maxlength="100" class="Valid[Required] Description[citcorpore.comum.numeroSubscricao]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.comum.nome" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="cliente"  maxlength="200" class="Valid[Required] Description[citcorpore.comum.nome]" />
												</div>
											</fieldset>
										</div>
										<div class="col_60">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.endereco" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="endereco" maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_30">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.contato" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="contato" maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.email" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="email"  maxlength="200"  />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.telefone1" />
												</label>
												<div style="height: 35px;">
													<input id="telefone1" type="text" name="telefone1"  maxlength="13" class="" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.telefone2" />
												</label>
												<div style="height: 35px;">
													<input id="telefone2" type="text" name="telefone2"  maxlength="13" class="" />
												</div>
											</fieldset>
										</div>
										<div class="col_30">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.tipoSubscricao" />
												</label>
												<div style="height: 35px;">
													<select id="tipoSubscricao" name="tipoSubscricao"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.url" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="url"  maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.login" />
												</label>
												<div style="height: 35px;">
													<input type='text' name="login"  maxlength="200" />
												</div>
											</fieldset>
										</div>
										<div class="col_15">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="citcorpore.controleContrato.senha" />
												</label>
												<div style="height: 35px;">
													<input type='password' name="senha"  maxlength="200" />
												</div>
											</fieldset>
										</div>
										
										<!-- VERSAO -->
										<div class="col_50">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><i18n:message key="citcorpore.controleContrato.versao" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<label  class="campoObrigatorio" style="margin-bottom: 3px; margin-top: 10px; float: left;">
														<i18n:message key="citcorpore.controleContrato.versao" />
													</label>
													<div style="float: left; width: 300px; padding-left: 0px;">
														<input type="hidden" id="idVersao" name="idVersao"/>
														<input type='text' id="nomeVersaoText" name="nomeVersaoText" maxlength="50" />
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridVersao();" border="0" style="cursor:pointer; padding-top: 10px;">
												</fieldset>
											</div>
											<table class="table" id="tblVersao" style="width: 500px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 90%;"><i18n:message key="citcorpore.controleContrato.versao" /></th>
												</tr>
											</table>
										</div>
										
										<!-- PAGAMENTOS -->
										<div class="col_50">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><i18n:message key="citcorpore.controleContrato.pagamento" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<div class="col_15" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.comum.data" />
															</label> 
															<input type='text' id="dataPagamentoText" name="dataPagamentoText"  maxlength="200" class="datepicker" />
														</fieldset>
													</div>
													<div class="col_40" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.controleContrato.parcela" />
															</label> 
															<input type='text' class="Format[Numero]" id="parcelaText" name="parcelaText" maxlength="200"  />
														</fieldset>
													</div>
													<div class="col_25" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.controleContrato.atraso" />
															</label> 
															<input type='text' name="dataAtrasoText"  maxlength="200"  class="datepicker" />
														</fieldset>
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridPagamento();" border="0" style="cursor:pointer; padding-top: 30px;">
												</fieldset>
											</div>
											<table class="table" id="tblPagamento" style="width: 850px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 20%;"><i18n:message key="citcorpore.comum.data" /></th>
													<th style="width: 35%;"><i18n:message key="citcorpore.controleContrato.parcela" /></th>
													<th style="width: 35%;"><i18n:message key="citcorpore.controleContrato.atraso" /></th>
												</tr>
											</table>
										</div>
										
										<!-- MODULOS ATIVOS -->
										<div class="col_100" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><i18n:message key="citcorpore.controleContrato.modulosAtivos" /></b>
											</div>
											<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
												<div id="divModulosAtivos">
													<table class="table" id="tblModulosAtivos" style="width: 850px; margin-left: 15px;">
														<tr>
															<th style="width: 16px !important;"></th>
															<th style="width: 90%;"><i18n:message key="citcorpore.controleContrato.modulos" /></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
										
										<!-- TREINAMENTO -->
										<div class="col_50" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><i18n:message key="citcorpore.controleContrato.treinamento" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<div class="col_15" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.comum.data" />
															</label> 
															<input type='text' name="dataTreinamentoText"  maxlength="200" class=" datepicker" />
														</fieldset>
													</div>
													<div class="col_40" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.controleContrato.treinamento" />
															</label> 
															<input type='text' name="nomeTreinamentoText" maxlength="200" />
														</fieldset>
													</div>
													<div class="col_30" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.controleContrato.instrutor" />
															</label> 
															<input type='hidden' name="idEmpregadoTreinamento" id="idEmpregadoTreinamento"  maxlength="200"  />
															<input type='text' name="nomeInstrutorTreinamentoText" onclick="abrePopupInstrutor()"  maxlength="200" style="width: 90% !important;" />
															<img style="vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
														</fieldset>
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridTreinamento();" border="0" style="cursor:pointer; padding-top: 30px;">
												</fieldset>
											</div>
											<table class="table" id="tblTreinamento" style="width: 850px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 20%;"><i18n:message key="citcorpore.comum.data" /></th>
													<th style="width: 35%;"><i18n:message key="citcorpore.controleContrato.treinamento" /></th>
													<th style="width: 35%;"><i18n:message key="citcorpore.controleContrato.instrutor" /></th>
												</tr>
											</table>
										</div>
										
										<!-- OCORRENCIAS -->
										<div class="col_50" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><i18n:message key="citcorpore.controleContrato.ocorrencia" /></b>
											</div>
											<div class="col_100">
												<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
													<div class="col_15" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.comum.data" />
															</label> 
															<input type='text' id="dataOcorrenciaText" name="dataOcorrencia"  maxlength="200" class="datepicker" />
														</fieldset>
													</div>
													<div class="col_40" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.controleContrato.assunto" />
															</label> 
															<input type='text' id="assuntoOcorrenciaText" name="assuntoOcorrencia"  maxlength="200"  />
														</fieldset>
													</div>
													<div class="col_30" style="border: none; float: left;">
														<fieldset style="border: none; padding-top: 5px;">
															<label class="campoObrigatorio" style="margin-bottom: 3px; font-weight: bolder;">
																<i18n:message key="citcorpore.comum.usuario" />
															</label> 
															<input type='hidden' id="idEmpregadoOcorrencia" name="idEmpregadoOcorrencia" maxlength="200" />
															<input type='text' id="usuarioOcorrenciaText" name="usuarioOcorrenciaText" onclick="abrePopupUsuarioOcorrencia()" maxlength="200" style="width: 90% !important;" />
															<img style="vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
														</fieldset>
													</div>
													<img title="Adicionar" src="/citsmart/imagens/add.png" onclick="addGridOcorrencia();" border="0" style="cursor:pointer; padding-top: 30px;">
												</fieldset>
											</div>
											<table class="table" id="tblOcorrencia" style="width: 850px; margin-left: 15px;">
												<tr>
													<th style="width: 16px !important;"></th>
													<th style="width: 20%;"><i18n:message key="citcorpore.comum.data" /></th>
													<th style="width: 35%;"><i18n:message key="citcorpore.controleContrato.assunto" /></th>
													<th style="width: 35%;"><i18n:message key="citcorpore.comum.usuario" /></th>
												</tr>
											</table>
										</div>
										<!--  Trecho de código comentado pois não estava claro a sua utilização na tela em questão 
										<div class="col_100" style="border-top: 1px solid #ddd">
											<div style="margin-bottom: 5px; margin-top: 5px; border-bottom: none;">
												<b><i18n:message key="citcorpore.controleContrato.incidentes" /></b>
											</div>
											<fieldset>
												<div class="col_100" id="divIncidentesContrato" style="height: 200px;width: 65%; overflow: auto; padding-top: 10px;">
													<table class="table" id="tblIncidente" style="width: 65%">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 5%;"><i18n:message key="citcorpore.comum.solicitacao" /></th>
															<th style="width: 10%;"><i18n:message key="citcorpore.comum.servico" /></th>
															<th style="width: 5%;"><i18n:message key="contrato.contrato" /></th>
															<th style="width: 5%;"><i18n:message key="citcorpore.comum.usuario" /></th>
															<th style="width: 5%;"><i18n:message key="citcorpore.comum.responsavel" /></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>	-->
										<div class="col_100" style="padding-top: 30px; padding-bottom: 30px;">
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
										</div>
									</div>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section" style="padding-bottom: 10px;">
								<i18n:message key="citcorpore.comum.pesquisa" />
								<form name='formPesquisa'> 
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_CONTROLECONTRATO' id='LOOKUP_CONTROLECONTRATO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		
	<%-- 	 <div id="POPUP_SERVICOCONTRATO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="box grid_16 tabs" style="width: auto !important">
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
			</div>
		</div> --%>
		<div id="POPUP_CONTRATO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div style="width: auto !important">
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
		<div id="POPUP_INSTRUTOR" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="tabs" style="width: auto !important">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px; ">
							<form name='formTreinamento'style="width: 100% !important;">
								<cit:findField formName='formTreinamento' 
								lockupName='LOOKUP_SOLICITANTE' 
								id='LOOKUP_SOLICITANTE' top='0' left='0' len='550' heigth='400' 
								javascriptCode='true' 
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_USUARIOOCORRENCIA" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="tabs" style="width: auto !important">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px; ">
							<form name='formUsuarioOcorrencia'style="width: 100% !important;">
								<cit:findField formName='formUsuarioOcorrencia' 
								lockupName='LOOKUP_SOLICITANTE' 
								id='LOOKUP_SOLICITANTEOCORRENCIA' top='0' left='0' len='550' heigth='400' 
								javascriptCode='true' 
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
		</div>
	</body>
</html>
