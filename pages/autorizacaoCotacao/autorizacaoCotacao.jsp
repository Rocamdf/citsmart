<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        String id = request.getParameter("id");
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
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoProdutoDTO.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ItemRequisicaoProdutoDTO.js"></script>
   <link href="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

    <style type="text/css">
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
            	parent.escondeJanelaAguarde(); 
            }    
        }
        function LOOKUP_PRODUTO_select(id, desc){
        	document.getElementById('item#idProduto').value = id;
            document.getElementById('codigoProduto').value = desc.split("-")[0];
        	document.getElementById('nomeProduto').value = desc.split("-")[1];
            $("#POPUP_PRODUTO").dialog("close");
        }        
        $(function() {
            $("#POPUP_ITEM_REQUISICAO").dialog({
                autoOpen : false,
                width : 580,
                height : 670,
                modal : true
            });
        }); 
                
        atualizarItem = function(){
            if (!document.formItemRequisicao.validate())
                return;

            var indice = parseInt(document.getElementById('item#index').value);
            if (indice == 0)
                return;
            
            var idUnidade = document.getElementById('item#idUnidadeMedida');
            document.getElementById('item#siglaUnidadeMedida').value = idUnidade[idUnidade.selectedIndex].text;
            var obj = HTMLUtils.getObjectByTableIndex('tblItensRequisicao', indice);
            HTMLUtils.setValuesObjectByGroupName(document.formItemRequisicao, 'item', obj);
            var aprovado = document.formItemRequisicao['item#autorizado'];
            if (aprovado[0].checked) {
                obj.autorizado = 'S';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.autorizado");
            }else{
                obj.autorizado = 'N';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.naoAutorizado");
            }
            var aprovaCotacao = document.formItemRequisicao['item#aprovaCotacao'];
            if (aprovaCotacao.checked)
                obj.aprovaCotacao = 'S';
            else
                obj.aprovaCotacao = 'N';
            
            HTMLUtils.updateRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj, 
                    ["","descricaoItem","quantidade","siglaUnidadeMedida","descrSituacao","codigoProduto","nomeProduto"], null,'', [gerarImg], editarItem, null, indice, true);
            $("#POPUP_ITEM_REQUISICAO").dialog("close");
        };

        function gerarImg (row, obj){
            row.cells[0].innerHTML = '<button type="button" class="light">'+i18n_message("dinamicview.editar")+'</button>';
        };

        function posicionarCategoria(id) {
        	HTMLUtils.setValue('item#idCategoriaProduto', id);		
        	document.form.idCategoriaProduto.value = id;
        }
            
        function editarItem(row, obj) {
            if (document.form.editar.value == 'N')
                return;
            
            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            var aprovado = document.formItemRequisicao['item#autorizado'];
            if (obj.autorizado == 'S')
            	aprovado[0].checked = true;
            else if (obj.autorizado == 'N')
                aprovado[1].checked = true;
            var aprovaCotacao = document.formItemRequisicao['item#aprovaCotacao'];
            aprovaCotacao.checked = obj.aprovaCotacao == 'S';
            document.getElementById('item#index').value = row.rowIndex;
            configuraJustificativa(obj.autorizado);

            posicionarCategoria(obj.idCategoriaProduto);
            document.form.idCategoriaProduto.value = obj.idCategoriaProduto; 
            document.form.idProduto.value = obj.idProduto; 
            document.form.tipoIdentificacaoItem.value = obj.tipoIdentificacao; 
            document.form.fireEvent('preparaTelaItemRequisicao'); 
        } 

        function prepararTelaDigitacaoProduto() {
            document.getElementById('divProduto').style.display = 'none';
            document.getElementById('divDigitacao').style.display = 'block';
            document.form.tipoIdentificacaoItem.value = 'D';
        }
                
        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensRequisicao_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        function configuraJustificativa(aprovado) {
            var tipoAtend = document.formItemRequisicao['item#tipoAtendimento'].value;
        	document.getElementById('divJustificativa').style.display = 'none';
        	document.getElementById('divAprovacao').style.display = 'none';   
            if (aprovado == 'N') 
            	document.getElementById('divJustificativa').style.display = 'block';    	
            else if (aprovado == 'S' && tipoAtend == 'C')
                document.getElementById('divAprovacao').style.display = 'block';  
        }
        
    </script>
</head>

<body>  
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/autorizacaoCotacao/autorizacaoCotacao'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='editar' id='editar' /> 
                                <input type='hidden' name='acao' id='acao'/> 
                                <input type='hidden' name='idCategoriaProduto' id='idCategoriaProduto'/> 
                                <input type='hidden' name='idProduto' id='idProduto'/> 
                                <input type='hidden' name='tipoIdentificacaoItem' id='tipoIdentificacaoItem'/> 
                                
                                <div class="col_100">
                                    <div class="col_50">
                                        <fieldset >
                                            <label><i18n:message key="requisicaoProduto.finalidade" /></label>
                                            <div>
                                                <select name='finalidade' id='finalidade' disabled="disabled"></select>
                                            </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_50">
                                         <fieldset >
                                             <label ><i18n:message key="centroResultado.custo" /></label>
                                             <div>
                                                 <select name='idCentroCusto' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>  
								</div>                                    
                                <div class="col_100">
                                    <div class="col_40">
                                         <fieldset >
                                             <label ><i18n:message key="requisicaoProduto.projeto" /></label>
                                             <div>
                                                 <select name='idProjeto' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_60">
                                         <fieldset >
                                             <label ><i18n:message key="requisicaoProduto.enderecoEntrega" /></label>
                                             <div>
                                                 <select name='idEnderecoEntrega' disabled="disabled"></select>
                                             </div>
                                         </fieldset>
                                    </div>
								</div>                                    
                                <div class="col_100">
                                    <div class="col_50">
                                         <fieldset >
                                             <label ><i18n:message key="requisicaoProduto.aprovadores" /></label>
                                             <div>
                                                 <input id="loginAprovadores" type="text" name='loginAprovadores' readonly="readonly" />
                                             </div>
                                         </fieldset>
                                    </div>
                                    <div class="col_50" >
                                             <div style="margin-top: 30px!important">
                                                <label style='cursor:pointer'><input type='checkbox' value='S' name='rejeitada'/><b><i18n:message key="requisicaoProduto.rejeitarValidacao"/></b></label>
                                             </div>
                                    </div>                                    
                                </div>

                                <div class="col_100">
                                    <div class="col_50">
                                        <h2 class="section">
                                            <i18n:message key="requisicaoProduto.itens" />
                                        </h2>
                                    </div>
                                </div>
                                <div class="col_100" style="overflow:auto; height:300px">
                                    <table id="tblItensRequisicao" class="table">
                                        <tr>
                                            <th width="1%">&nbsp;</th>
                                            <th width="30%"><i18n:message key="itemRequisicaoProduto.descricao" /></th>
                                            <th width="10%"><i18n:message key="itemRequisicaoProduto.quantidade" /></th>
                                            <th width="10%"><i18n:message key="itemRequisicaoProduto.unidadeMedida" /></th>
                                            <th width="15%"><i18n:message key="citcorpore.comum.situacao" /></th>
                                            <th colspan='2'><i18n:message key="itemRequisicaoProduto.produto" /></th>
                                        </tr>
                                    </table>
                                </div>
                        </form>
                    </div>
            </div>  
        </div>


<div id="POPUP_ITEM_REQUISICAO" title="<i18n:message key="requisicaoProduto.itens" />"  style="overflow: hidden;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#index'/>
        <input type='hidden' name='item#siglaUnidadeMedida'/>
        <input type='hidden' name='item#idProduto'/>
        <input type='hidden' name='item#tipoIdentificacao'/>
        <input type='hidden' name='item#tipoAtendimento'/>

		<div class="columns clearfix">
	        <div class="col_100">
				<fieldset>
                	<label class="campoObrigatorio"><i18n:message key="itemRequisicaoProduto.categoria" /></label>
                    <div>
                    	<select id='item#idCategoriaProduto' name='item#idCategoriaProduto' disabled="disabled" ></select>
 					</div>
				</fieldset>
	        </div>
	        <div class="col_100">	        
	            <div class="col_80">
	                 <fieldset>
	                      <label><i18n:message key="itemRequisicaoProduto.produto" /></label>
	                     <div>
							<input type='text' name='item#descricaoItem' id='descricaoItem' readonly="readonly"/>
	                      </div>
	                  </fieldset>
				</div>
	         
	            <div class="col_20">
	                <fieldset>
	                    <label><i18n:message key="itemRequisicaoProduto.quantidade" /></label>
	                    <div>
	                        <input id="item#quantidade" type="text"  maxlength="15" name='item#quantidade' class="Format[Moeda]" readonly="readonly"/>
	                    </div>
	                </fieldset>
	            </div>
			</div>
            <div id='divProduto' style='display:none' class="col_100">
                <div class="col_25">
                    <fieldset>
                        <div id='divImagemProduto' style='padding:10px 10px;'>
                        </div>
                    </fieldset>
                </div>                        
                <div class="col_50">
                    <fieldset>
                        <div id='divDetalhesProduto' style='padding:10px 10px;'>
                        </div>
                    </fieldset>
                </div>                        
                 <div class="col_25">
                        <fieldset>
                            <div id='divAcessorios' style='padding:10px 10px;'>
                            </div>
                        </fieldset>
                 </div>
            </div>
            <div id='divDigitacao' class="col_100">
                  <div class="col_100">
                      <fieldset>
                          <label><i18n:message key="itemRequisicaoProduto.especificacoes" /></label>
                          <div>
                               <textarea id="item#especificacoes" name="item#especificacoes" cols='60'  readonly="readonly"></textarea>                               
                          </div>
                      </fieldset>
                   </div>
                   <div class="col_100">
                       <div class="col_40">
                           <fieldset>
                               <label><i18n:message key="itemRequisicaoProduto.marcaPreferencial" /></label>
                               <div>
                                   <input id="item#marcaPreferencial"  type='text'  name="item#marcaPreferencial" maxlength="100" readonly="readonly"/>
                               </div>
                           </fieldset>
                       </div>
                       <div class="col_20">
                           <fieldset>
                               <label class="campoObrigatorio"><i18n:message key="itemRequisicaoProduto.unidadeMedida" /></label>
                               <div>
                                   <select id='item#idUnidadeMedida'  name='item#idUnidadeMedida'  disabled="disabled"></select>
                               </div>
                           </fieldset>
                       </div>
                       <div class="col_40">
                           <fieldset>
                               <label><i18n:message key="itemRequisicaoProduto.precoAproximado" /></label>
                               <div>
                                   <input id="item#precoAproximado" type="text"  maxlength="15" name='item#precoAproximado' class="Format[Moeda]" readonly="readonly"/>
                               </div>
                           </fieldset>
                       </div>
                   </div>
                    <div class="col_100">
                        <div class="col_25" >
                            <fieldset>
                             <label>
                                 <i18n:message key="itemRequisicaoProduto.codigoProduto" /> 
                             </label>
                             <div>
                                   <input id="codigoProduto" type='text' name="item#codigoProduto" readonly="readonly"/>
                               </div>
                             </fieldset>
                        </div>   
                        <div class="col_75" >
                              <fieldset>
                                  <label>
                                      <i18n:message key="itemRequisicaoProduto.descrProduto" /> 
                                  </label>
                                  <div>
                                      <input id="nomeProduto" type='text' name="item#nomeProduto" readonly="readonly"/>
                                  </div>
                             </fieldset>
                        </div>   
					</div>
            </div> 
        </div>

       <div style="display: block;" id="validacao">
           <div class="col_100">
               <fieldset>
	               <label  class="campoObrigatorio">
	                   <i18n:message key="itemRequisicaoProduto.validacao" /> 
	               </label>
	               <div>
                       <input type='radio' name="item#autorizado" value="S" onclick='configuraJustificativa("S");' checked="checked"><i18n:message key="itemRequisicaoProduto.autorizado"/>
                       <input type='radio' name="item#autorizado" value="N" onclick='configuraJustificativa("N");' ><i18n:message key="itemRequisicaoProduto.naoAutorizado"/>
                   </div>
               </fieldset>
		   </div>
       
           <div id="divJustificativa" class="col_100" >
               <div class="col_50">
                    <fieldset >
                        <label><i18n:message key="itemRequisicaoProduto.justificativa" /></label>
                        <div>
                            <select id='item#idJustificativaAutorizacao'  name='item#idJustificativaAutorizacao'></select>
                        </div>
                    </fieldset>
               </div>
               <div class="col_50">
                   <fieldset >
                       <label><i18n:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
                       <div>
                            <textarea id="item#complemJustificativaAutorizacao" name="item#complemJustificativaAutorizacao" cols='60' rows='2'></textarea>                               
                       </div>
                   </fieldset>
               </div>
           </div>
               
           <div id="divAprovacao" class="col_100" style='display:none'>
                  <div class="col_50">
                      <fieldset >
                          <label><i18n:message key="itemRequisicaoProduto.qtdeAprovada" /></label>
                          <div>
                               <input id="item#qtdeAprovada" type="text"  maxlength="15" name='item#qtdeAprovada' class="Valid[Required] Description[itemRequisicaoProduto.qtdeAprovada] Format[Moeda]"/>                             
                          </div>
                      </fieldset>
                  </div>  
                     <div class="col_50" >
                         <fieldset >
                              <div style="margin-top: 30px!important">
                                 <label style='cursor:pointer'><input type='checkbox' value='S' name='item#aprovaCotacao'/><b><i18n:message key="itemRequisicaoProduto.aprovaCotacao"/></b></label>
                              </div>
                         </fieldset>
                     </div>                              
              </div>

            <div class="col_100" >
               <div class="col_50">
                    <fieldset >
                         <label>
                             &nbsp; 
                         </label>
                    </fieldset>
               </div>   
               <div class="col_50">
                    <fieldset >
                        <div style="padding: 10px 0px 0px 10px">
                            <button type="button" onclick='$("#POPUP_ITEM_REQUISICAO").dialog("close")'>
                                <i18n:message key="citcorpore.comum.fechar" />
                            </button>
                            <button type="button" onclick='atualizarItem()'>
                                <i18n:message key="citcorpore.comum.confirmar" />
                            </button>
                        </div>
                    </fieldset>
               </div>   
            </div>
       </div>
    </form>
</div>

</body>

</html>
