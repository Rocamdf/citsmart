<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="java.util.Collection"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        String id = request.getParameter("id");
        Collection<CriterioAvaliacaoDTO> colCriterios = (Collection)request.getAttribute("colCriterios");  
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
    <script type="text/javascript" src="../../cit/objects/InspecaoEntregaItemDTO.js"></script>
   <link href="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/css/atualiza-antigo.css" rel="stylesheet" />

    <style type="text/css">
        .linhaSubtituloGrid
    {
        color           :#000000;
        background-color: #d3d3d3;
        BORDER-RIGHT: thin outset;
        BORDER-TOP: thin outset;
        BORDER-LEFT: thin outset;
        BORDER-BOTTOM: thin outset;  
        FONT-WEIGHT: bold;
        padding: 5px 0px 5px 5px;
        
    }
    .linhaGrid{
        border: 1px solid black;
        background-color:  #F2F2F2;
        vertical-align: middle;
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
                height : 630,
                modal : true
            });
        }); 
                
        atualizarItem = function(){
            if (!tratarCriterios()){
                return;
            }

            if (!document.formItemRequisicao.validate())
                return;

            var indice = parseInt(document.getElementById('item#index').value);
            if (indice == 0)
                return;
            
            var obj = HTMLUtils.getObjectByTableIndex('tblItensRequisicao', indice);
            HTMLUtils.setValuesObjectByGroupName(document.formItemRequisicao, 'item', obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            obj.aprovado = '';
            if (aprovado[0].checked) {
                obj.aprovado = 'S';
                obj.descrSituacao = i18n_message("pedidoCompra.entregaAprovada");
            }else if (aprovado[1].checked) {
                obj.aprovado = 'N';
                obj.descrSituacao = i18n_message("pedidoCompra.entregaNaoAprovada");
            }
            configuraJustificativa(obj.aprovado);
            
            HTMLUtils.updateRow('tblItensRequisicao', document.formItemRequisicao, 'item', obj, 
                    ["","descricaoItem","nomeFornecedor","numeroPedido","quantidadeEntregue","dataEntrega","descrSituacao"], null,'', [gerarImg], editarItem, null, indice, true);
                        
            document.form.save();
        };

        function gerarImg (row, obj){
            row.cells[0].innerHTML = '<button type="button" class="light">'+i18n_message("dinamicview.editar")+'</button>';
        };
            
        function editarItem(row, obj) {
            document.formItemRequisicao.clear();
            HTMLUtils.setValues(document.formItemRequisicao,'item',obj);
            var aprovado = document.formItemRequisicao['item#aprovado'];
            if (obj.aprovado == 'S')
                aprovado[0].checked = true;
            else if (obj.aprovado == 'N')
                aprovado[1].checked = true;
            document.getElementById('item#index').value = row.rowIndex;
            configuraJustificativa(obj.aprovado)
            document.getElementById('item#dataEntrega').value = obj.dataEntrega;
            document.form.idEntrega.value = obj.idEntrega;
            document.form.fireEvent('exibeAvaliacao'); 
        } 
      
        function validar() {
            return document.form.validate();
        }

        function getObjetoSerializado() {
            var obj = new CIT_RequisicaoProdutoDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            var itensRequisicao = HTMLUtils.getObjectsByTableId('tblItensRequisicao');
            obj.itensEntrega_serialize = ObjectUtils.serializeObjects(itensRequisicao);
            return ObjectUtils.serializeObject(obj);
        }

        var seqCriterio = '';
        incluirCriterio = function() {
            GRID_CRITERIOS.addRow();
            seqCriterio = NumberUtil.zerosAEsquerda(GRID_CRITERIOS.getMaxIndex(),5);
            eval('document.formItemRequisicao.observacoes' + seqCriterio + '.value = ""');
            eval('document.formItemRequisicao.idCriterio' + seqCriterio + '.focus()');
        }

        exibeCriterio = function(serializeCriterio) {
            if (seqCriterio != '') {
                if (!StringUtils.isBlank(serializeCriterio)) {
                    var criterioDto = new CIT_InspecaoEntregaItemDTO();
                    criterioDto = ObjectUtils.deserializeObject(serializeCriterio);
                    try{
                        eval('HTMLUtils.setValue("idCriterio' + seqCriterio + '",' + criterioDto.idCriterio + ')');
                    }catch(e){
                    }            
                    if (criterioDto.tipoAvaliacao == 'S') {
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.sim'), i18n_message('criterioAvaliacao.sim'))");
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.nao'),i18n_message('criterioAvaliacao.nao'))");
                    }
                    if (criterioDto.tipoAvaliacao == 'A') {
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.aceito'),i18n_message('criterioAvaliacao.aceito'))");
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.naoAceito'),i18n_message('criterioAvaliacao.naoAceito'))");
                    }
                    if (criterioDto.tipoAvaliacao == 'C') {
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.conforme'),i18n_message('criterioAvaliacao.conforme'))");
                    	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "', i18n_message('criterioAvaliacao.naoConforme'),i18n_message('criterioAvaliacao.naoConforme'))");
                    }
                    if (criterioDto.tipoAvaliacao == 'P') {
                        for ( var i = 1; i < 11; i++) {
                        	eval("HTMLUtils.addOption('avaliacao" + seqCriterio + "',"+i+","+i+");");
						}
                    }    
                    eval('document.formItemRequisicao.avaliacao' + seqCriterio + '.value = "' + criterioDto.avaliacao + '"');
                    eval('document.formItemRequisicao.observacoes' + seqCriterio + '.value = "' + criterioDto.observacoes + '"');
                }        
            }
        }    

        getCriterio = function(seq) {
            var criterioDto = new CIT_InspecaoEntregaItemDTO();
            
            seqCriterio = NumberUtil.zerosAEsquerda(seq,5);
            criterioDto.sequencia = seq;
            criterioDto.idCriterio = parseInt(eval('document.formItemRequisicao.idCriterio' + seqCriterio + '.value'));
            criterioDto.avaliacao = eval('document.formItemRequisicao.avaliacao' + seqCriterio + '.value');
            criterioDto.observacoes = eval('document.formItemRequisicao.observacoes' + seqCriterio + '.value');
            return criterioDto;
        }    

        verificarCriterio = function(seq) {
            var idCriterio = eval('document.formItemRequisicao.idCriterio' + seq + '.value');
            var count = GRID_CRITERIOS.getMaxIndex();
            for (var i = 1; i <= count; i++){
                if (parseInt(seq) != i) {
                     var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                     if (!trObj){
                        continue;
                     }                
                     var idAux = eval('document.formItemRequisicao.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.value');
                     if (idAux == idCriterio) {
                          alert('Critério já selecionado!');
                          eval('document.formItemRequisicao.idCriterio' + seq + '.focus()');
                          return false;
                     }    
                }
            }       
            return true; 
        }   

        function tratarCriterios(){
            try{
                var count = GRID_CRITERIOS.getMaxIndex();
                var contadorAux = 0;
                var objs = new Array();
                for (var i = 1; i <= count; i++){
                    var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                    if (!trObj){
                        continue;
                    }
                 
                    var criterioDto = getCriterio(i);
                    if (parseInt(criterioDto.idCriterio) > 0) {
                        if  (!verificarCriterio(NumberUtil.zerosAEsquerda(i,5))) {
                            return false;
                        }    
                        if (StringUtils.isBlank(criterioDto.avaliacao)){
                            alert('Informe a avaliação!');
                            eval('document.formItemRequisicao.avaliacao' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');                     
                            return false;
                        }
                        objs[contadorAux] = criterioDto;
                        contadorAux = contadorAux + 1;
                    }else{
                        alert('Selecione a avaliacao!');
                        eval('document.formItemRequisicao.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');                     
                        return false;
                    }        
                }
                document.form.colCriterios_Serialize.value = ObjectUtils.serializeObjects(objs); 
                return true;
            }catch(e){
            }       
        }    
        
        function gravarCriterios() {
            if (!tratarCriterios()){
                return;
            }

            document.form.save();
        }

        function configuraJustificativa(aprovado) {
            document.getElementById('divJustificativa').style.display = 'none';
            if (aprovado == 'N') 
                document.getElementById('divJustificativa').style.display = 'block';  
        }
    </script>
</head>

<body>  
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/inspecaoEntregaItem/inspecaoEntregaItem'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='editar' id='editar' /> 
                                <input type='hidden' name='acao' id='acao'/> 
                                <input type='hidden' name='colCriterios_Serialize' id='colCriterios_Serialize'/> 
                                <input type='hidden' name='idEntrega' id='idEntrega'/> 
                                
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
                                            <th ><i18n:message key="coletaPreco.fornecedor" /></th>
                                            <th width="10%"><i18n:message key="pedidoCompra.numero" /></th>
                                            <th width="10%"><i18n:message key="itemRequisicaoProduto.quantidade" /></th>
                                            <th width="20%"><i18n:message key="pedidoCompra.dataEntrega" /></th>
                                            <th width="20%"><i18n:message key="citcorpore.comum.situacao" /></th>
                                        </tr>
                                    </table>
                                </div>
                        </form>
                    </div>
            </div>  
        </div>  

<div id="POPUP_ITEM_REQUISICAO" title="<i18n:message key="pedidoCompra.avaliacao" />"  style="overflow: hidden;">
    <form name='formItemRequisicao'>
        <input type='hidden' name='item#index'/>
        <div class="section">
            <div class="col_100">
                 <fieldset>
	                 <label><i18n:message key="coletaPreco.item" />
	                 </label>
                    <div>
                      <input id="item#descricaoItem"  type='text'  name="item#descricaoItem" readonly="readonly" />
                    </div>
                 </fieldset>
            </div>
            <div class="col_100">
                 <div class="col_20">
                     <fieldset>
                        <label><i18n:message key="fornecedor.cpfcnpj" />
                        </label>
                        <div>
                          <input id="item#cpfCnpjFornecedor"  type='text'  name="item#cpfCnpjFornecedor" readonly="readonly" />
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
                 <div class="col_20">
                    <fieldset>
                        <label style="cursor: pointer;"><i18n:message key="itemRequisicaoProduto.quantidade" /></label>
                        <div>
                            <input id="item#quantidadeEntregue" type="text"  name='item#quantidadeEntregue' class="Format[Moeda]" readonly="readonly" />
                        </div>
                    </fieldset>
                 </div>
                 <div class="col_40">
                     <fieldset>
                        <label><i18n:message key="pedidoCompra.numero" />
                        </label>
                        <div>
                          <input id="item#numeroPedido"  type='text'  name="item#numeroPedido" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
                 <div class="col_40">
                     <fieldset>
                        <label><i18n:message key="pedidoCompra.dataEntrega" />
                        </label>
                        <div>
                          <input id="item#dataEntrega"  type='text'  name="item#dataEntrega" class="Format[Data]" readonly="readonly" />
                        </div>
                     </fieldset>
                 </div>
            </div>       
	       <div style="display: block;" id="validacao">
	           <div class="col_100">
	               <fieldset>
		               <label  class="campoObrigatorio">
		                   <i18n:message key="itemRequisicaoProduto.aprovacao" /> 
		               </label>
		               <div>
	                       <input type='radio' name="item#aprovado" value="S" onclick='configuraJustificativa("S");' checked="checked"><i18n:message key="pedidoCompra.entregaAprovada"/>
	                       <input type='radio' name="item#aprovado" value="N" onclick='configuraJustificativa("N");' ><i18n:message key="pedidoCompra.entregaNaoAprovada"/>
	                   </div>
	               </fieldset>
			   </div>
               <div id="divJustificativa" class="col_100" style='display:none' >
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
            </div>
            <div class="col_100">
                   <h2 class="section">
                         <i18n:message key="pedidoCompra.avaliacao" />
                    </h2>
            </div>
            <div class="col_100" style='height:150px;overflow:auto;'>
	             <cit:grid id="GRID_CRITERIOS" columnHeaders="Critério;Avaliacao;Observações" styleCells="linhaGrid" deleteIcon="false">
	                 <cit:column idGrid="GRID_CRITERIOS" number="001">
	                     <select name='idCriterio#SEQ#' id='idCriterio#SEQ#' style='border:none;' disabled='disabled'>
	                         <option value=''><i18n:message key="citcorpore.comum.selecione" /></option>
	                         <%
	                         if (colCriterios != null){
	                             for (CriterioAvaliacaoDTO criterioDto : colCriterios) {
	                                 out.println("<option value='" + criterioDto.getIdCriterio() + "'>" +
	                                 criterioDto.getDescricao() + "</option>");
	                             }
	                         }
	                         %>
	                     </select>
	                 </cit:column>
	                 <cit:column idGrid="GRID_CRITERIOS" number="002">
	                     <select name='avaliacao#SEQ#' id='avaliacao#SEQ#' style='border:none; '>
	                         <option value=''><i18n:message key="citcorpore.comum.selecione" /></option>
	                     </select>
	                 </cit:column>
	                 <cit:column idGrid="GRID_CRITERIOS" number="003">
	                     <input type='text' name='observacoes#SEQ#' id='observacoes#SEQ#' size='180'/>
	                 </cit:column>
	             </cit:grid>
            </div>
            <div class="col_100" >
                    <fieldset>
                        <div>
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
    </form>
</div>

</body>

</html>
