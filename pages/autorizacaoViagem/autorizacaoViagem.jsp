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
            padding: 30%;
            
        }
        
        .table th {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
        }
        
        .table td  {
            border:1px solid #ddd;
            padding:4px 10px;
            border-top:none;
            border-left:none;
            text-align: center;
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
        
        function gerarImg (row, obj){
            row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/localizarLookup.gif"/>';
        };
		
		function addItemIntegrante(row, obj){
			var idEmpregado = obj.idEmpregado;
			document.form.idEmpregado.value = idEmpregado;
			document.form.fireEvent('atualizaGridItensControle');
		}
		
		$(function() {
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog({
				autoOpen : false,
				width : "98%",
				height : $(window).height()-350,
				modal : true
			});
		});
		
		function fecharFrameItemControleFinanceiro(){
			$("#POPUP_ITEMCONTROLEFINANCEIRO").dialog("close");
		}
		
		function getObjetoSerializado() { 
            var obj = new CIT_RequisicaoViagemDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itegranteViagem = HTMLUtils.getObjectsByTableId('tblControleFinaceiro');
            obj.integranteViagemSerialize = ObjectUtils.serializeObjects(itegranteViagem);
            return ObjectUtils.serializeObject(obj);
        }
		
		  function configuraJustificativa(aprovado) {
	            if (aprovado == 'N') {
	            	$('#divJustificativa').show(); 
	            	document.form.fireEvent('preencherComboJustificativaAutorizacao');
	            }else{
	            	$('#divJustificativa').hide();  
	            }
	            	
	        }
		
    </script>
</head>

<body>  
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/autorizacaoViagem/autorizacaoViagem'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='idcontroleFinanceiroViagem' id='idcontroleFinanceiroViagem' /> 
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
                                 <input type='hidden' name='idEmpregado'/>
                                <div class="columns clearfix">
                                	<div>
                                		<h2>
											<i18n:message key="requisicaoViagem.informacoesRequisicaoViagem" />
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
                                         <fieldset >
                                             <label  style="cursor: pointer;"><i18n:message key="centroResultado.custo" /></label>
                                             <div>
                                                 <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
                                             </div>
                                         </fieldset>
                                    </div>  
                                    <div class="col_33">
                                         <fieldset >
                                             <label  style="cursor: pointer;"><i18n:message key="requisicaoProduto.projeto" /></label>
                                             <div>
                                                 <select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_33">
                                         <fieldset >
                                             <label  style="cursor: pointer;"><i18n:message key="requisicaoViagem.justificativa" /></label>
                                             <div>
                                                 <select name='idMotivoViagem' class="Valid[Required] Description[requisicaoViagem.justificativa]"></select>
                                             </div>
                                         </fieldset>
                                    </div>
									<div class="col_100">
										<fieldset>
											<label  ><i18n:message key="requisicaoViagem.motivo"/>
											</label>
											<div>
												<textarea name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='5' maxlength = "2000"></textarea>
											</div>
										</fieldset>
									</div> 
                                 </div>  
                                 <div class="col_100">
                                 <div>
                                		<h2>
											<i18n:message key="requisicaoViagem.controleFinancerioViagem" />
										</h2>
                                	</div>
                                	
                                 <div class="col_25">
                                         <fieldset >
                                             <label  style="cursor: pointer;"><i18n:message key="moeda.moeda" /></label>
                                             <div>
                                             	<input id="nomeMoeda" name="nomeMoeda" size="10" maxlength="10" type="text" readonly="readonly"  />
                                             </div>
                                         </fieldset>
                                    </div>
                                 <div class="col_75">
										<fieldset >
											<label  ><i18n:message key="avaliacaoFonecedor.observacao"/>
											</label>
											<div>
												<textarea name="observacoes" id="observacoes" cols='60' rows='2' maxlength = "2000" readonly="readonly" ></textarea>
											</div>
										</fieldset>
									</div> 
                                 </div>
                                 <div id="divValor" class="col_100"></div>
					           <div style="display: block;" id="validacao">
						           <div class="col_100">
						               <h2 class="section">
						                  <i18n:message key="itemRequisicaoProduto.autorizacao" />
						               </h2>
						           </div>    
						           <div class="col_100">
						           <div class="col_25">
						            	<div class="col_100" >
						                   <fieldset >
						                   	   <label class="campoObrigatorio"  ><i18n:message key="itemRequisicaoProduto.autorizacao"/></label>
						                       <label style='cursor:pointer'><input type='radio' name="autorizado" value="S" onclick='configuraJustificativa("S");'  checked="checked"><i18n:message key="itemRequisicaoProduto.autorizado"/></label><br>
						                       <label style='cursor:pointer'><input type='radio' name="autorizado" value="N"  onclick='configuraJustificativa("N");' ><i18n:message key="itemRequisicaoProduto.naoAutorizado"/></label>
						                   </fieldset>
						              	 </div>
						              </div>
						               <div id="divJustificativa" class="col_75" >
						                   <div class="col_50">
						                        <fieldset >
						                            <label class="campoObrigatorio"><i18n:message key="itemRequisicaoProduto.justificativa" /></label>
						                            <div>
						                                <select id='idJustificativaAutorizacao'  name='idJustificativaAutorizacao'></select>
						                            </div>
						                        </fieldset>
						                   </div>
						                   <div class="col_50">
						                       <fieldset >
						                           <label class="campoObrigatorio"><i18n:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
						                           <div>
						                                <textarea id="complemJustificativaAutorizacao" name="complemJustificativaAutorizacao" cols='60' rows='2'></textarea>                               
						                           </div>
						                       </fieldset>
						                   </div>
						               </div>
							           </div> 
						           </div>
						           <div class="col_100">
                                    <div class="col_50">
                                        <h2 class="section">
                                            <i18n:message key="itemControleFinanceiroViagem.itensCadastrados" />
                                        </h2>
                                    </div>
                                    <div class="col_66">
                                    <table id="tblControleFinaceiro" class="table">
                                        <tr>
                                            <th width="10%">&nbsp;</th>
                                            <th width="10%"><i18n:message key="citcorpore.comum.numero" /></th>
                                            <th width="50%"><i18n:message key="citcorpore.comum.nome" /></th>
                             			
                                        </tr>
                                    </table>
                                    </div>
                                </div>
                        </form>
                    </div>
            </div>  
        </div>
        <div id="POPUP_ITEMCONTROLEFINANCEIRO"  style="overflow: hidden;" title="<i18n:message key="itemControleFinanceiroViagem.itemControleFinanceiroViagem"/>">
			<div id="divNome"></div>
			<div ><hr></div>
			<div id="divTblItens" class="col_100">
             </div>
             <div class="col_15">
				<button type='button' name='btnGravar' class="light" onclick='fecharFrameItemControleFinanceiro();'>
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/alert.png">
					<span><i18n:message key="citcorpore.comum.fechar" /></span>
				</button>
			</div>
		</div>
</body>

</html>
