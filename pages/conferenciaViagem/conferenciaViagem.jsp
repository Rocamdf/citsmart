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
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/PrestacaoContasViagemDTO.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ItemPrestacaoContasViagemDTO.js"></script>
    
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
        .table tr > td:nth-child(10) {
			text-align: center;
		}
        .table tr > td:nth-child(4) {
			text-align: center;
		}
        .table tr > td:nth-child(5) {
			text-align: center;
		}
        .table tr > td:nth-child(16) {
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
        var obj = new CIT_PrestacaoContasViagemDTO();
        HTMLUtils.setValuesObject(document.form, obj);
/*         var adiantamentoViagem = HTMLUtils.getObjectsByTableId('tabelaItemPrestacaoContasViagem');
        obj.itensPrestacaoContasViagemSerialize = ObjectUtils.serializeObjects(adiantamentoViagem); */
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
	<div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px; width: 100%'>
		<div class="toggle_container">
			<div id="tabs-2" class="block" style="overflow: hidden;">
				<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/conferenciaViagem/conferenciaViagem'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
					<input type='hidden' name='idEmpregado' id='idEmpregado' /> 
					<input type='hidden' name='idPrestacaoContasViagem' id='idPrestacaoContasViagem' /> 
					<input type='hidden' name='itensPrestacaoContasViagemSerialize' id="itensPrestacaoContasViagemSerialize"/>
					<input type='hidden' name='idEmpregado' id="idEmpregado"/>
					<input type='hidden' name='situacao' id='situacao'/>
					<input type='hidden' name='listItens' id='listItens'/>
					<input type='hidden' name='idResponsavel' id='idResponsavel'/>
					<input type='hidden' name='idAprovacao' id='idAprovacao'/>
					
					
					<div class="columns clearfix" >
						<div>
							<h2><i18n:message key="requisicaoViagem.conferenciaViagem" /></h2>
                        </div> 
					</div>
					<div class="col_100">
						<div id="divNome"></div>
						<fieldset>
							<h2 class="section"><i18n:message key="requisicaoViagem.itensContasAdiantamento" /></h2>
							<table class="table" id="tblItensAdiantados">
								<tr>
									<th style="width: 10%"><i18n:message key="citcorpore.comum.numero" /></th>
									<th style="width: 20%"><i18n:message  key="itemControleFinanceiroViagem.tipoMovimentacaoFinanceira" /></th>
									<th style="width: 20%;"><i18n:message  key="itemPrestacaoContasViagem.nomeFornecedor" /></th>
									<th style="width: 15%"><i18n:message  key="citcorpore.comum.valor"/></th>
								</tr>
							</table>
							<div id="divTotal"></div>
						</fieldset>
						
					</div>

					<div class="columns clearfix">
						<div class="col_100">
						<h2 class="section"><i18n:message key="itemPrestacaoContasViagem.nome" /></h2>
							<fieldset>
								<table class="table" id="tabelaItemPrestacaoContasViagem" style="display: none;">
									<tr>
										<th style="width: 5%;"><i18n:message  key="requisicaoViagem.itemReferente" /></th>
										<th style="width: 20%;"><i18n:message  key="itemPrestacaoContasViagem.nomeFornecedor" /></th>
										<th style="width: 10%;"><i18n:message  key="itemPrestacaoContasViagem.numeroDocumento" /></th>
										<th style="width: 5%;"><i18n:message  key="itemPrestacaoContasViagem.data" /></th>
										<th style="width: 7%; text-align: center;"><i18n:message  key="citcorpore.comum.valor" /></th>
										<th style="width: 20%;"><i18n:message  key="citcorpore.comum.descricao" /></th>
									</tr>
								</table>
							</fieldset>
						</div>
					</div>
					<div style="display: block;" id="validacao">
			           <div class="col_100">
			               <h2 class="section">
			                  <i18n:message key="citcorpore.comum.aprovacao" />
			               </h2>
			           </div>    
			           <div class="col_100">
			               <div class="col_25" >
			                   <fieldset >
			                   	   <label class="campoObrigatorio"  ><i18n:message key="citcorpore.comum.aprovacao"/></label>
			                       <label style='cursor:pointer'><input type='radio' name="aprovado" value="S" onclick='configuraJustificativa("S");'  checked="checked"><i18n:message key="citcorpore.comum.aprovada"/></label><br>
			                       <label style='cursor:pointer'><input type='radio' name="aprovado" value="N"  onclick='configuraJustificativa("N");' ><i18n:message key="itemRequisicaoProduto.naoAprovado"/></label>
			                   </fieldset>
			               </div>
			               <div id="divJustificativa" class="col_75" >
			                   <div class="col_25">
			                        <fieldset>
			                            <label class="campoObrigatorio"><i18n:message key="itemRequisicaoProduto.justificativa" /></label>
			                            <div>
			                                <select id='idJustificativaAutorizacao'  name='idJustificativaAutorizacao'></select>
			                            </div>
			                        </fieldset>
			                   </div>
			                   <div class="col_75">
			                       <fieldset>
			                           <label class="campoObrigatorio"><i18n:message key="itemRequisicaoProduto.complementoJustificativa" /></label>
			                           <div>
			                                <textarea id="complemJustificativaAutorizacao" name="complemJustificativaAutorizacao" cols='60' rows='2'></textarea>                               
			                           </div>
			                       </fieldset>
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