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
    
        $(function() {
            $("#POPUP_ITEM_REQUISICAO").dialog({
                autoOpen : false,
                width : 580,
                height : 640,
                modal : true
            });
        }); 
                
        atualizarItem = function(){
            if (!document.formItemRequisicao.validate())
                return;

            var indice = parseInt(document.getElementById('item#index').value);
            if (indice == 0)
                return;
            
            var obj = HTMLUtils.getObjectByTableIndex('tblItensRequisicao', indice);
            HTMLUtils.setValuesObjectByGroupName(document.formItemRequisicao, 'item', obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            if (aprovado[0].checked) {
                obj.aprovado = 'S';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.aprovado");
            }else{
                obj.aprovado = 'N';
                obj.descrSituacao = i18n_message("itemRequisicaoProduto.naoAprovado");
            }

            HTMLUtils.updateRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj, 
                    ["","descricaoItem","nomeFornecedor","quantidade","descrSituacao"], null,'', [gerarImg], editarItem, null, indice, true);
            $("#POPUP_ITEM_REQUISICAO").dialog("close");
        };

        function gerarImg (row, obj){
            row.cells[0].innerHTML = '<button type="button" class="light">'+i18n_message("dinamicview.editar")+'</button>';
        };
            
        function editarItem(row, obj) {
            if (document.form.editar.value == 'N')
                return;
            
            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            if (obj.aprovado == 'S')
            	aprovado[0].checked = true;
            else if (obj.aprovado == 'N')
                aprovado[1].checked = true;
            document.getElementById('item#index').value = row.rowIndex;
            configuraJustificativa(obj.aprovado);
            $('#POPUP_ITEM_REQUISICAO').dialog('open'); 
        } 
      
        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensCotacao_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        function configuraJustificativa(aprovado) {
        	document.getElementById('divJustificativa').style.display = 'none';
            document.getElementById('divAprovacao').style.display = 'none';   
            if (aprovado == 'N') 
            	document.getElementById('divJustificativa').style.display = 'block';  
            else if (aprovado == 'S')
                document.getElementById('divAprovacao').style.display = 'block';  
        }
        
    </script>
</head>

<body>  
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/aprovacaoCotacao/aprovacaoCotacao'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='editar' id='editar' /> 
                                <input type='hidden' name='acao' id='acao'/> 
                                <input type='hidden' name='itensCotacao_serialize' id='itensCotacao_serialize'/> 
                                
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
                                        <h2 class="section">
                                            <i18n:message key="requisicaoProduto.itens" />
                                        </h2>
                                    </div>
                                </div>
                                <div class="col_100" style="overflow:auto; height:300px">
                                    <table id="tblItensRequisicao" class="table">
                                        <tr>
                                            <th width="1%">&nbsp;</th>
                                            <th ><i18n:message key="itemRequisicaoProduto.descricao" /></th>
                                            <th width="30%"><i18n:message key="coletaPreco.fornecedor" /></th>
                                            <th width="10%"><i18n:message key="itemRequisicaoProduto.quantidade" /></th>
                                            <th width="20%"><i18n:message key="citcorpore.comum.situacao" /></th>
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

        <div class="col_100">
            <div class="col_100">
                 <div class="col_80">
                     <fieldset>
	                    <label><i18n:message key="coletaPreco.item" />
	                    </label>
                        <div>
                          <input id="item#descricaoItem"  type='text'  name="item#descricaoItem" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_20">
                    <fieldset>
                        <label style="cursor: pointer;"><i18n:message key="itemRequisicaoProduto.quantidade" /></label>
                        <div>
                            <input id="item#quantidade" type="text"  name='item#quantidade' class="Format[Moeda]" readonly="readonly" />
                        </div>
                    </fieldset>
                 </div>
            </div>
            <div class="col_100">
                 <div class="col_20">
                     <fieldset>
                        <label><i18n:message key="fornecedor.cpfcnpj" />
                        </label>
                        <div>
                          <input id="item#cpfCnpjFornecedor"  type='text'  name="cpfCnpjFornecedor" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_80">
                     <fieldset>
                        <label><i18n:message key="fornecedor" />
                        </label>
                        <div>
                          <input id="item#nomeFornecedor"  type='text'  name="item#nomeFornecedor" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
            </div>                        
            <div class="col_100">
                <fieldset>
                    <label><i18n:message key="coletaPreco.especificacoes" />
                    </label>
                    <div>
                        <textarea name="item#especificacoes" id="item#especificacoes" cols='200' rows='3' ></textarea>
                    </div>
                </fieldset>
            </div>
            <div class="col_100">
                <div class="col_33">
                    <fieldset>
                        <label ><i18n:message key="itemRequisicaoProduto.preco" />
                        </label>
                        <div>
                           <input id="item#preco" type='text' name="item#preco" class="Format[Moeda]" readonly="readonly"/>     
                        </div>
                    </fieldset>
                </div>
                <div class="col_33">
                    <fieldset>
                        <label ><i18n:message key="coletaPreco.preco" />
                        </label>
                        <div>
                           <input id="item#valorTotal" type='text' name="item#valorTotal" class="Format[Moeda]"  readonly="readonly"/>         
                        </div>
                    </fieldset>
                </div>
                <div class="col_33">
                    <fieldset>
                        <label><i18n:message key="coletaPreco.prazoEntrega" />
                        </label>
                        <div>
                           <input id="item#prazoEntrega" type='text' name="item#prazoEntrega" class="Format[Numero]"  readonly="readonly"/>             
                        </div>
                    </fieldset>
                </div>
            </div>
        </div>            
       <div style="display: block;" id="validacao">
           <div class="col_100">
               <fieldset>
	               <label  class="campoObrigatorio">
	                   <i18n:message key="itemRequisicaoProduto.aprovacao" /> 
	               </label>
	               <div>
                       <input type='radio' name="item#aprovado" value="S" onclick='configuraJustificativa("S");' checked="checked"><i18n:message key="itemRequisicaoProduto.aprovado"/>
                       <input type='radio' name="item#aprovado" value="N" onclick='configuraJustificativa("N");' ><i18n:message key="itemRequisicaoProduto.naoAprovado"/>
                   </div>
               </fieldset>
		   </div>
		          
              <div id="divJustificativa" class="col_100"  style='display:none'>
                  <div class="col_50">
                       <fieldset>
                           <label><i18n:message key="itemRequisicaoProduto.justificativa" /></label>
                           <div>
                               <select id='item#idJustificativa'  name='item#idJustificativa'></select>
                           </div>
                       </fieldset>
                  </div>
                  <div class="col_50">
                      <fieldset>
                          <label><i18n:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
                          <div>
                               <textarea id="item#complementoJustificativa" name="item#complementoJustificativa" cols='60' rows='2'></textarea>                               
                          </div>
                      </fieldset>
                  </div>
              </div> 
            <div id="divAprovacao" class="col_30" style='display:none'>
                 <fieldset>
                     <label><i18n:message key="itemRequisicaoProduto.percVariacaoPreco" /></label>
                     <div>
                          <input id="item#percVariacaoPreco" type="text"  maxlength="15" name='item#percVariacaoPreco' class="Valid[Required] Description[itemRequisicaoProduto.percVariacaoPreco] Format[Moeda]"/>                             
                     </div>
                 </fieldset>
            </div> 
            <div class="col_100" >
               <div class="col_50">
                    <fieldset>
                         <label>
                             &nbsp; 
                         </label>
                    </fieldset>
               </div>   
               <div class="col_50">
                    <fieldset>
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
