<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ProdutoDTO"%>
<%@page import="java.util.Collection"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
    %>
    <%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
    <%@include file="/include/security/security.jsp" %>
    <title><i18n:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/noCache/noCache.jsp" %>
    <%@include file="/include/menu/menuConfig.jsp" %>
    <%@include file="/include/header.jsp" %>
    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>   
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/EmpregadoDTO.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoViagemDTO.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/AdiantamentoViagemDTO.js"></script>

    <style type="text/css">
        .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
        }
        .table {
            border-left:1px solid #ddd;
            width: 100%;
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
        
        .table1 {
        }
        
        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }
        
        .table1 td {
        }   
             
         div#main_container {
            margin: 0px 0px 0px 0px;
        } 
                
        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;
            
            letter-spacing: -4px;
        }
        
        .table tr > td:first-child {
			text-align: center;  
		}
		
		.tdPontilhada{
			border:2px;
		    BORDER-RIGHT-style: solid;
		    BORDER-RIGHT-width: 1px;
		    BORDER-RIGHT-COLOR: black;
		    BORDER-TOP-style: solid;
		    BORDER-TOP-width: 1px;
		    BORDER-TOP-COLOR: black;
		    BORDER-LEFT-style: solid;
		    BORDER-LEFT-width: 1px;
		    BORDER-LEFT-COLOR: black;
		    BORDER-BOTTOM-style: solid;
		    BORDER-BOTTOM-width: 1px;
		    BORDER-BOTTOM-COLOR: black;
		}
		
    </style>

    <script>
      
        addEvent(window, "load", load, false);
        function load(){        
            document.form.afterLoad = function () {
                if (document.form.editar.value != '' && document.form.editar.value != 'S')
                    desabilitarTela();
                document.form.fireEvent('montaHierarquiaCategoria');
                parent.escondeJanelaAguarde();                    
            }    
        }
        
		function gerarButtonDelete(row) {
			row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblControleFinaceiro\', this.parentNode.parentNode.rowIndex);">'
		}
		
		function deleteLinha(table, index){
			HTMLUtils.deleteRow(table, index);
		}
		
		
	   function getObjetoSerializado() {
            var obj = new CIT_AdiantamentoViagemDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var adiantamentoViagem = HTMLUtils.getObjectsByTableId('tblControleFinaceiro');
            obj.adiantamentoViagemSerialize = ObjectUtils.serializeObjects(adiantamentoViagem);
            return ObjectUtils.serializeObject(obj);
        }
		   
		function calcularQuantidadeDias(){
			
			var dataInicio = document.getElementById("dataInicioViagem").value;
			var dataFim = document.getElementById("dataFimViagem").value;
			
			var dtInicio = new Date();
			var dtFim = new Date();
			
			if(dataInicio != "" & dataFim != ""){
				
				if(validaData(dataInicio,dataFim)){
					
					dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
					dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
					
					var timeDifference = dtFim.getTime() - dtInicio.getTime();
					var seconds = timeDifference / 1000;
					var minutes = seconds / 60;
					var hours = minutes / 60;
					var days = hours / 24;
					document.form.qtdeDias.value = days + 1;
				}
				
				
			}
			
		}
		
		/**
		* @author rodrigo.oliveira
		*/
		function validaData(dataInicio, dataFim) {
			
			var dtInicio = new Date();
			var dtFim = new Date();
			
			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
			
			if (dtInicio > dtFim){
				alert(i18n_message("solicitacaoservico.validacao.datainiciomenorfinal"));
				return false;
			}else
				return true;
		}
		
		function resize_iframe(){
			var height=window.innerWidth;//Firefox
			if (document.body.clientHeight)
			{
				height=document.body.clientHeight;//IE
			}
			document.getElementById("iframeItemControleFinanceiro").style.height=parseInt(height - document.getElementById("iframeItemControleFinanceiro").offsetTop-8)+"px";
		}
		$(function() {
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog({
				autoOpen : false,
				width : "90%",
				height : $(window).height()-100,
				modal : true
			});
		});
		
		function fecharFrameItemControleFinanceiro(){
			limpaPopup();
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("close");
		}
		
		
		function gerarImg (row, obj){
			row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png"/>';
        };
        
        function carregaPopupAdiantamento(row, obj){
			var idEmpregado = obj.idEmpregado;
			var idSolicitacao = obj.idSolicitacaoServico;
			document.form.idEmpregado.value = idEmpregado;
			document.form.idSolicitacaoServico.value = idSolicitacao;
			document.form.fireEvent('atualizaGridItensControle');
			document.form.fireEvent('restorePorIntegrante');
		}
        
        function gravaAdiantamento(){
        	var obs = document.getElementById("observacoesPopup").value;
        	document.form.observacoes.value = obs;
        	document.form.fireEvent("save");
        	//fecharFrameItemControleFinanceiro();
        }
        
        function limpaPopup(){
        	document.getElementById("observacoesPopup").value = "";
        }
		
    </script>
</head>

<body>
	<div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px; width: 100%'>
		<div class="toggle_container">
			<div id="tabs-2" class="block" style="overflow: hidden;">
				<form name='form' action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/adiantamentoViagem/adiantamentoViagem">
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
					<input type='hidden' name='idAdiantamentoViagem' id='idAdiantamentoViagem' /> 
					<input type='hidden' name='integranteViagemSerialize' id="integranteViagemSerialize"/>
					<input type='hidden' name='idEmpregado' id="idEmpregado"/>
					<input type='hidden' name='situacao' id='situacao'/>
					<input type='hidden' name='valorTotalAdiantado' id='valorTotalAdiantado'/>
					<input type='hidden' name='ColItens' id='ColItens'/>
					<input type='hidden' name='idResponsavel' id='idResponsavel'/>
					<input type='hidden' name='observacoes' id='observacoes'/>
					
					<div class="columns clearfix" >
						<div>
                        	<h2><i18n:message key="requisicaoViagem.adiantamentoViagem" /></h2>
                       	</div>
                       	<div >
                       		<div >
                       			<h3 class="section" style="padding: 0;padding-left: 20px"><i18n:message key="requisicaoViagem.integranteViagem" /></h3>
                       			<hr>
                       		</div>
                       		<div >
                       			<fieldset class="col_66">
                       				<table id="tblControleFinaceiro" class="table" >
                       					<tr>
                       						<th width="5%">&nbsp;</th>
                                            <th width="5%"><i18n:message key="citcorpore.comum.numero" /></th>
                                            <th width="60%"><i18n:message key="citcorpore.comum.nome" /></th>
                       					</tr>
                       				</table>
                       			</fieldset>
                       		</div>
                       	</div> 	
					</div>
					<div id="POPUP_ITEMCONTROLEFINANCEIRO"  style="overflow: hidden;" title="<i18n:message key="requisicaoViagem.adiantamentoViagem"/>">
						<div id="divNome"></div>
						<div id="divTblItens"></div>
						<div  class="col_100">
							<div class="col_100">
								<fieldset style="height: 110px;">
									<label><i18n:message key="avaliacaoFonecedor.observacao"/></label>
									<div>
										<textarea name="observacoesPopup" id="observacoesPopup" style="padding-bottom: " cols='200' rows='4' maxlength = "2000"></textarea>
									</div>
								</fieldset>
							</div>
							<div class="col_100">
							<br>
								<fieldset class="col_100" style="height: 7px;border-bottom: none;"></fieldset>
								<div id="divBtnGravar" class="col_25">
									<button type='button'  name='btnGravar' class="light" onclick='gravaAdiantamento();'>
<%-- 										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/checkmark.png"> --%>
										<span><i18n:message key="requisicaoViagem.confimarAdiantamento" /></span>
									</button>
								</div>
								<div class="col_15">
									<button type='button' name='btnGravar' class="light" onclick='fecharFrameItemControleFinanceiro();'>
<%-- 										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/alert.png"> --%>
										<span><i18n:message key="citcorpore.comum.fechar" /></span>
									</button>
								</div>
							</div>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>
