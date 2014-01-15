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
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ControleFinanceiroViagemDTO.js"></script>

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
        
        $(function() {
            $("#POPUP_EMPREGADO").dialog({
                autoOpen : false,
                width : 600,
                height : 400,
                modal : true
            });
        }); 

        function adicionarEmpregado() {
            $("#POPUP_EMPREGADO").dialog("open");
        }  
        
		function LOOKUP_EMPREGADO_select(id, desc){
			var obj = new CIT_EmpregadoDTO();
            obj.idEmpregado = id;
            obj.nome = desc;

            HTMLUtils.addRow('tblControleFinaceiro', document.form, null, obj, ['idEmpregado','nome'], ['idEmpregado'] , '<i18n:message key="citcorpore.comum.registroJaAdicionado"/>', [gerarButtonDelete], null, null, false);
            
            $("#POPUP_EMPREGADO").dialog("close");
			
		}
		function gerarButtonDelete(row) {
			row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblControleFinaceiro\', this.parentNode.parentNode.rowIndex);">'
		}
		
		function deleteLinha(table, index){
			
			HTMLUtils.deleteRow(table, index);
		}
		
		
		   function getObjetoSerializado() {
	            var obj = new CIT_ControleFinanceiroViagemDTO();
	            HTMLUtils.setValuesObject(document.form, obj);
	            var itegranteViagem = HTMLUtils.getObjectsByTableId('tblControleFinaceiro');
	            obj.integranteViagemSerialize = ObjectUtils.serializeObjects(itegranteViagem);
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
		
		function addItemIntegrante(row, obj){
			var idEmpregado = obj.idEmpregado;
			var numeroSolicitacao = document.getElementById("idSolicitacaoServico").value;
			document.getElementById('iframeItemControleFinanceiro').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemControleFinanceiroViagem/itemControleFinanceiroViagem.load?iframe=true&idSolicitacaoServico="+ numeroSolicitacao +"&idEmpregado="+idEmpregado;
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("open");
		}
		
		function resize_iframe(){
			var height=window.innerWidth;/*Firefox*/
			if (document.body.clientHeight)
			{
				height=document.body.clientHeight;/*IE*/
			}
			document.getElementById("iframeItemControleFinanceiro").style.height=parseInt(height - document.getElementById("iframeItemControleFinanceiro").offsetTop-8)+"px";
		}
		$(function() {
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog({
				autoOpen : false,
				width : "98%",
				height : $(window).height()-20,
				modal : true
			});
		});
		
		function fecharFrameItemControleFinanceiro(){
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("close");
		}
		
		
		function gerarImg (row, obj){
            row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"/>';
        };
        
		if (window.matchMedia("screen and (-ms-high-contrast: active), (-ms-high-contrast: none)").matches) {
		    document.documentElement.className += "ie10";
		}
		
    </script>
</head>

<body>  
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/controleFinanceiroViagem/controleFinanceiroViagem'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='idControleFinanceiroViagem' id='idControleFinanceiroViagem' /> 
                                <input type='hidden' name='editar' id='editar' /> 
                                <input type='hidden' name='acao' id='acao'/> 
                                <input type='hidden' name='idCategoriaProduto' id='idCategoriaProduto'/> 
                                <input type='hidden' name='idProduto' id='idProdutoForm'/> 
                                <input type='hidden' name='tipoIdentificacaoItem' id='tipoIdentificacaoItem'/> 
                                <input type='hidden' name='identificacao' id='identificacaoItemForm'/>
                                <input type='hidden' name='nomeMarca' id='nomeMarca'/>
                                <input type='hidden' name='precoMercado' id='precoMercado'/>
                                <input type='hidden' name='idUnidadeMedida' id='idUnidadeMedida'/>
                                <input type='hidden' name='siglaUnidadeMedida' id='siglaUnidadeMedida'/>
                                <input type='hidden' name='detalhes' id='detalhes'/>
                                <input type='hidden' name='itemIndex'/>
                                <input type='hidden' name='integranteViagemSerialize'/>
                                <input type='hidden' name='estado'/>
                                <input type='hidden' name='idCidadeOrigem'/>
                                <input type='hidden' name='idCidadeDestino'/>
                                <input type='hidden' name='idResponsavel' id='idResponsavel'/>
                                
                                <div class="columns clearfix">
                                	<div>
                                		<h2><i18n:message key="requisicaoViagem.controleFinancerioViagem" /></h2>
                                	</div>
                                	<div class="col_100">
                                		<div  class="col_50">
										<fieldset>
											 <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="requisicaoViagem.cidadeOrigem" /></label>
										<div>
											<input id="nomeCidadeOrigem" name="nomeCidadeOrigem" size="10" maxlength="10" type="text" onclick="adicionarCidade(true)" />
										</div>
										</fieldset>
										</div>
										<div  class="col_50">
											<fieldset>
												 <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="requisicaoViagem.cidadeDestino" /></label>
											<div>
												<input id="nomeCidadeDestino" name="nomeCidadeDestino" size="10" maxlength="10" type="text" onclick="adicionarCidade(false)" />
											</div>
											</fieldset>
										</div>
                                	</div>
                                   <div class="col_100">
                                   	 <div  class="col_33">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="citcorpore.comum.datainicio"/></label>
										<div>
											<input id="dataInicioViagem" name="dataInicioViagem" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" onchange="calcularQuantidadeDias()" />
										</div>
										</fieldset>
									</div>
									<div  class="col_33">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="citcorpore.comum.datafim"/></label>
										<div>
											<input id="dataFimViagem" name="dataFimViagem" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" onchange="calcularQuantidadeDias()" />
										</div>
										</fieldset>
									</div>
									<div  class="col_25">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="requisicaoViagem.quantidadeDias"/></label>
										<div>
											<input id="qtdeDias" name="qtdeDias" size="10" maxlength="10" type="text" readonly="readonly"  />
										</div>
										</fieldset>
									</div>
                                   </div>
									</div>
									<div class="columns clearfix">
									<div class="col_33">
                                         <fieldset style="height: 70px;">
                                             <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="centroResultado.custo" /></label>
                                             <div>
                                                 <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
                                             </div>
                                         </fieldset>
                                    </div>  
                                    <div class="col_33">
                                         <fieldset style="height: 70px;">
                                             <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="requisicaoProduto.projeto" /></label>
                                             <div>
                                                 <select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_33">
                                         <fieldset style="height: 70px;">
                                             <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="requisicaoViagem.justificativa" /></label>
                                             <div>
                                                 <select name='idMotivoViagem' class="Valid[Required] Description[requisicaoViagem.justificativa]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
									<div class="col_100">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="requisicaoViagem.motivo"/>
											</label>
											<div class="col_100">
												<textarea name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='4' maxlength = "2000"></textarea>
											</div>
										</fieldset>
									</div> 
                                 </div>  
                                 <div class="col_100">
                                 <div class="col_33">
                                         <fieldset style="height: 120px;">
                                             <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="moeda.moeda" /></label>
                                             <div>
                                                 <select name='idMoeda' class="Valid[Required] Description[moeda.moeda]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                 <div class="col_66">
										<fieldset style="height: 120px;">
											<label><i18n:message key="avaliacaoFonecedor.observacao"/>
											</label>
											<div>
												<textarea name="observacoes" id="observacoes" cols='200' rows='4' maxlength = "2000"></textarea>
											</div>
										</fieldset>
									</div> 
                                 </div>
                                <div class="col_100">
                                    <div class="col_100">
                                        <h4 class="section"><i18n:message key="requisicaoViagem.integranteViagem" /></h4>
                                    </div>
                                    <div style="overflow: auto " class="col_100">
                                    <table id="tblControleFinaceiro" class="table">
                                        <tr>
                                            <th width="10%"><i18n:message key="requisicaoProduto.adicionarItem" /></th>
                                            <th width="50%"><i18n:message key="citcorpore.comum.nome" /></th>
                                        </tr>
                                    </table>
                                    </div>
                                </div>
                        </form>
                    </div>
            </div>  
        </div>
        <div id="POPUP_ITEMCONTROLEFINANCEIRO" name="POPUP_ITEMCONTROLEFINANCEIRO"  style="overflow: hidden;" title="<i18n:message key="itemControleFinanceiroViagem.itemControleFinanceiroViagem"/>">
			<iframe id='iframeItemControleFinanceiro' name="iframeItemControleFinanceiro" src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>		
		</div>

</body>

</html>
