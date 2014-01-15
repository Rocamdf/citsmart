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
        .table tr > td:nth-child(4) {
			text-align: center;
		}
        .table tr > td:nth-child(5) {
			text-align: center;
		}		
        .table tr > td:nth-child(6) {
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

		   function getObjetoSerializado() {
	            var obj = new CIT_ControleFinanceiroViagemDTO();
	            HTMLUtils.setValuesObject(document.form, obj);
	            var itegranteViagem = HTMLUtils.getObjectsByTableId('tblControleFinaceiro');
	            obj.integranteViagemSerialize = ObjectUtils.serializeObjects(itegranteViagem);
	            return ObjectUtils.serializeObject(obj);
	        }
		
		function editItem(row, obj){
			var idEmpregado = obj.idEmpregado;
			var numeroSolicitacao = document.getElementById("idSolicitacaoServico").value;
			var idItemControleFinanceiroViagem = obj.idItemControleFinanceiroViagem;
			document.getElementById('iframeItemControleFinanceiro').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemControleFinanceiroViagem/itemControleFinanceiroViagem.load?iframe=true&origemCompras=S&idSolicitacaoServico="+ numeroSolicitacao +"&idEmpregado="+idEmpregado+"&idItemControleFinanceiroViagem="+idItemControleFinanceiroViagem;
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("open");
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
				width : "98%",
				height : $(window).height()-100,
				modal : true
			});
		});
		
		function fecharFrameItemControleFinanceiro(){
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("close");
		}
		
		
		function gerarImg (row, obj){
            row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png"/>';
        }; 
        
		function gerarImgEdit (row, obj){
            row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png"/>';
        }; 
        
        function carregaItens(row, obj){
        	document.form.idIntegrante.value = obj.idEmpregado;
        	document.form.fireEvent("carregaTblItens");
        }
        
        function confirmaExecucao(){
        	if($('#confirmaExec').is(':checked')){
        		document.form.confirma.value = "S";
        	}else{
        		document.form.confirma.value = "N";
        	}
        }
		
    </script>
</head>

<body>  
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/compraViagem/compraViagem'>
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
                                <input type='hidden' name='idIntegrante' id='idIntegrante'/>
                                <input type='hidden' name='confirma' id='confirma'/>
                                
                                <div class="columns clearfix">
                                	<div>
                                		<h2>
											<i18n:message key="requisicaoViagem.execucaoCompras" />
										</h2>
                                	</div>
                                   <div class="col_100">
                                	<div  class="col_40">
										<fieldset>
											 <label  style="cursor: pointer;"><i18n:message key="requisicaoViagem.cidadeOrigem" /></label>
										<div>
											<input id="nomeCidadeOrigem" name="nomeCidadeOrigem" size="10" maxlength="10" type="text" onclick="adicionarCidade(true)" />
										</div>
										</fieldset>
									</div>
									<div  class="col_40">
										<fieldset>
											 <label  style="cursor: pointer;"><i18n:message key="requisicaoViagem.cidadeDestino" /></label>
										<div>
											<input id="nomeCidadeDestino" name="nomeCidadeDestino" size="10" maxlength="10" type="text" onclick="adicionarCidade(false)" />
										</div>
										</fieldset>
									</div>
                                	</div>
                                	<div class="col_100">
                                		 <div  class="col_25">
											<fieldset>
												<label  ><i18n:message key="citcorpore.comum.datainicio"/></label>
											<div>
												<input id="dataInicioViagem" name="dataInicioViagem" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" onchange="calcularQuantidadeDias()" />
											</div>
											</fieldset>
										</div>
										<div  class="col_25">
											<fieldset>
												<label  ><i18n:message key="citcorpore.comum.datafim"/></label>
											<div>
												<input id="dataFimViagem" name="dataFimViagem" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" onchange="calcularQuantidadeDias()" />
											</div>
											</fieldset>
										</div>
										<div  class="col_25">
											<fieldset>
												<label  ><i18n:message key="requisicaoViagem.quantidadeDias"/></label>
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
									<div id="divMotivo" class="col_100">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="requisicaoViagem.motivo"/>
											</label>
											<div>
												<textarea name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='5' maxlength = "2000"></textarea>
											</div>
										</fieldset>
									</div> 
                                 </div>  
                                 <div class="col_100">
                                 <div class="col_33">
                                         <fieldset style="height: 120px;">
                                             <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="moeda.moeda" /></label>
                                             <div>
                                                 <select id='idMoeda' name='idMoeda' class="Valid[Required] Description[moeda.moeda]"></select>
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
                                        <h2 class="section">
                                            <i18n:message key="requisicaoViagem.integranteViagem" />
                                        </h2>
                                    </div>
                                    <div class="col_50">
	                                    <table id="tblControleFinaceiro" class="table" style="margin-left: 5px;width: 99%">
	                                        <tr>
	                                            <th width="1%"></th>
	                                            <th width="50%"><i18n:message key="citcorpore.comum.nome" /></th>
	                                        </tr>
	                                    </table>
                                    </div>
                                    <div class="col_100">
                                    	<fieldset>
                                    		<label style='cursor:pointer; margin-top: 1%; padding-left: 20px;'><i18n:message key="requisicaoViagem.confimarCompra" />&nbsp
                                    		<input type='checkbox' id='confirmaExec' name='confirmaExec' onclick="confirmaExecucao();"/><i18n:message key="citcorpore.comum.sim" /></label><br>
                                    	</fieldset>
                                    </div>
                                </div>
                                <br>
                                <br>
                                <div id="divItens" class="col_100"> 	
                                	<br>         	
                                	<br>
                                	<h2 style="padding-left: 20px"><i18n:message key="requisicaoViagem.itensAguardandoCompra" /></h2><hr>
                                    <div id="divNome" class="col_50">
                                    </div>
                                    <hr>   
                                    <div class="col_100">
                                    <table id="tblItens" class="table" style="margin-left: 5px;width: 99%">
                                        <tr>
                                            <th width="1%"></th>
                                            <th width="20%"><i18n:message key="itemControleFinanceiroViagem.tipoMovimentacaoFinanceira" /></th>
                                            <th width="20%"><i18n:message key="fornecedor" /></th>
                                            <th width="10%"><i18n:message key="itemControleFinanceiroViagem.quantidade" /></th>
                                            <th width="10%"><i18n:message key="itemControleFinanceiroViagem.valorUnitario" /></th>
                                            <th width="10%"><i18n:message key="coletaPreco.preco" /></th>
                                        </tr>
                                    </table>
                                    </div>
                                </div>
                        </form>
                    </div>
            </div>  
        </div>
        <div id="POPUP_ITEMCONTROLEFINANCEIRO"  style="overflow: hidden;" title="<i18n:message key="itemControleFinanceiroViagem.itemControleFinanceiroViagem"/>">
			<iframe id='iframeItemControleFinanceiro' src='about:blank' width="100%" height="100%" style='width: 100%; height: 100%; border:none;' onload="resize_iframe()"></iframe>		
		</div>

</body>

</html>
