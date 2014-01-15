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
        .table tr > td:nth-child(2) {
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
        var obj = new CIT_PrestacaoContasViagemDTO();
        HTMLUtils.setValuesObject(document.form, obj);
        var adiantamentoViagem = HTMLUtils.getObjectsByTableId('tabelaItemPrestacaoContasViagem');
        obj.itensPrestacaoContasViagemSerialize = ObjectUtils.serializeObjects(adiantamentoViagem);
        return ObjectUtils.serializeObject(obj);
    }
    
    /* Funções da Popup POPUP_ADICIONARITEM */
    
    $(function() {
		$("#POPUP_ADICIONARITEM").dialog({
			autoOpen : false,
			width : "90%",
			height : $(window).height()-100,
			modal : true
		});
	});
    
    function fecharPopupAdicionarItem(){
		limpaPopup();
		$("#POPUP_ADICIONARITEM").dialog("close");
	}
    
   $(function() {
		$("#addItemPrestacaoContasViagem").click(function() {
			$("#POPUP_ADICIONARITEM").dialog("open");
		});
	}); 
    
	/* Funções da Popup POPUP_FORNECEDOR */
    
    $(function() {
		$("#POPUP_FORNECEDOR").dialog({
			autoOpen : false,
			width : 620,
			height : 500,
			modal : true
		});
	});
	
    function pesquisaFornecedor(){
		$("#POPUP_FORNECEDOR").dialog("open");
	}
	
	function fecharPopupFornecedor(){
		$("#POPUP_FORNECEDOR").dialog("close");
	}
	
	
	function LOOKUP_FORNECEDOR_select(id, desc) {
		document.getElementById("idFornecedor").value = id;

		var valor = desc.split('-');
		    var nome = "";
			for(var i = 0 ; i < valor.length; i++){
				if(i < (valor.length - 1)){
					nome += valor[i];
				}
				if(i < (valor.length - 2) && valor.length > 2){
					nome += "-";
				}
			}
			
		document.getElementById("nomeFornecedor").value = nome;
		fecharPopupFornecedor();
	}
	
	/* Funções para salvar itens */
	
	function adicionarItem(){
				var idFornecedor = document.getElementById("idFornecedor").value;
				var nomeFornecedor = document.getElementById("nomeFornecedor").value;
				var numeroDocumento = document.getElementById("numeroDocumento").value;
				var data = document.getElementById("data").value;
				var valor = document.getElementById("valor").value;
				var descricao = document.getElementById("descricao").value;
				var idItemDespesaViagem = document.getElementById("idItemDespesaViagem").value;

				if (nomeFornecedor == "") {
					alert(i18n_message("itemPrestacaoContasViagem.nomeFornecedor") + ": " + i18n_message("itemPrestacaoContasViagem.campoObrigatorio"));
					return;
				}
				if (numeroDocumento == "") {
					alert(i18n_message("itemPrestacaoContasViagem.numeroDocumento") + ": " + i18n_message("itemPrestacaoContasViagem.campoObrigatorio"));
					return;
				}
				if (data == "") {
					alert(i18n_message("itemPrestacaoContasViagem.data") + ": " + i18n_message("itemPrestacaoContasViagem.campoObrigatorio"));
					return;
				}
				if (valor == null || valor == "") {
					alert(i18n_message("citcorpore.comum.valor") + ": " + i18n_message("itemPrestacaoContasViagem.campoObrigatorio"));
					return;
				}
				if (valor == "0,00") {
					alert(i18n_message("citcorpore.comum.valor") + ": " + i18n_message("itemPrestacaoContasViagem.invalido"));
					return;
				}
				addLinhaTabelaItem(idItemDespesaViagem, idFornecedor, nomeFornecedor, numeroDocumento, data, valor, descricao);
			}

			function addLinhaTabelaItem(idItemDespesaViagem, idFornecedor, nomeFornecedor, numeroDocumento, data, valor, descricao){
				var tbl = document.getElementById('tabelaItemPrestacaoContasViagem');
				tbl.style.display = 'block';
				var tamanhoTabela = tbl.rows.length;
				var obj = new CIT_ItemPrestacaoContasViagemDTO();
				
				obj.idItemDespesaViagem = idItemDespesaViagem;
		        obj.idFornecedor = idFornecedor;
	            obj.nomeFornecedor = nomeFornecedor;
	            obj.numeroDocumento = numeroDocumento;
	            obj.data = data;
	            obj.valor = valor;
	            obj.descricao = descricao;
	            

	            HTMLUtils.addRow('tabelaItemPrestacaoContasViagem', document.form, null, obj, ["","idItemDespesaViagem","nomeFornecedor","numeroDocumento","data","valor","descricao"], ["numeroDocumento"] , '<i18n:message key="citcorpore.comum.registroJaAdicionado"/>', [gerarButtonDelete], null, null, false);
				
                $("#POPUP_ADICIONARITEM").dialog("close");
				document.formItem.clear();
				
			}

			function gerarButtonDelete(row) {
				row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaItemPrestacaoContasViagem\', this.parentNode.parentNode.rowIndex);">'
			}
		
			function removeLinhaTabela(idTabela, rowIndex) {
				 if(idTabela == "tabelaItemPrestacaoContasViagem"){
						if (confirm(i18n_message("citcorpore.comum.deleta"))) {
							HTMLUtils.deleteRow(idTabela, rowIndex);
						} 
				 }
			}
			
			function addItemPrestacaoContas(row, obj){
				var idItemDespesa = obj.idItemControleFinanceiroViagem;
				valor = document.getElementById("idItemDespesaViagem").value = idItemDespesa;
				$("#POPUP_ADICIONARITEM").dialog("open");
			}
			
			function gerarImgTbl(row, obj){
				 row.cells[0].innerHTML = '<img style="cursor: pointer;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"/>';
			}
    
    </script>
    
</head>
<body>
	<div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px; width: 100%'>
		<div class="toggle_container">
			<div id="tabs-2" class="block" style="overflow: hidden;">
				<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/prestacaoContasViagem/prestacaoContasViagem'>
					<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
					<input type='hidden' name='idPrestacaoContasViagem' id='idPrestacaoContasViagem' /> 
					<input type='hidden' name='itensPrestacaoContasViagemSerialize' id="itensPrestacaoContasViagemSerialize"/>
					<input type='hidden' name='idEmpregado' id="idEmpregado"/>
					<input type='hidden' name='situacao' id='situacao'/>
					<input type='hidden' name='listItens' id='listItens'/>
					<input type='hidden' name='idResponsavel' id='idResponsavel'/>
					<input type='hidden' name='idAprovacao' id='idAprovacao'/>
					
					<div class="columns clearfix" >
						<div>
							<h2><i18n:message key="prestacaoContasViagem.prestacaoContasViagem" /></h2>
                        </div> 
					</div>
					<div id="divNome"></div>
					<div id="divCorrecao" class="col_100">
						<fieldset>
							<textarea id="corrigir" name="corrigir" rows="4" cols="200"></textarea>
						</fieldset>
					</div>
					<div class="col_100">
						<fieldset>
							<h2 class="section"><i18n:message key="requisicaoViagem.itensContasAdiantamento" /></h2>
							<table class="table" id="tblItensAdiantados">
								<tr>
									<th style="width: 3%"></th>
									<th style="width: 10%"><i18n:message key="citcorpore.comum.numero" /></th>
									<th style="width: 40%"><i18n:message  key="itemControleFinanceiroViagem.tipoMovimentacaoFinanceira" /></th>
									<th style="width: 15%"><i18n:message  key="citcorpore.comum.valor"/></th>
								</tr>
							</table>
							<div id="divTotal"></div>
						</fieldset>
						
					</div>
		
					<div class="columns clearfix">
						<div class="col_100">
							<h2 class="section" style="margin-top: 10px!important;"><i18n:message key="itemPrestacaoContasViagem.nome" /></h2>
							<fieldset>
								<table class="table" id="tabelaItemPrestacaoContasViagem" style="display: none;">
									<tr>
										<th style="width: 1%;"></th>
										<th style="width: 5%;"><i18n:message  key="requisicaoViagem.itemReferente" /></th>
										<th style="width: 20%;"><i18n:message  key="itemPrestacaoContasViagem.nomeFornecedor" /></th>
										<th style="width: 5%;"><i18n:message  key="itemPrestacaoContasViagem.numeroDocumento" /></th>
										<th style="width: 5%;"><i18n:message  key="itemPrestacaoContasViagem.data" /></th>
										<th style="width: 5%;"><i18n:message  key="citcorpore.comum.valor" /></th>
										<th style="width: 20%;"><i18n:message  key="citcorpore.comum.descricao" /></th>
									</tr>
								</table>
							</fieldset>
						</div>
					</div>
				</form>
			</div>
		</div>	
	</div>
	
	<!-- TELA DE CADASTRO DE ITEM PRESTAÇÃO CONTAS VIAGEM -->
		<div id="POPUP_ADICIONARITEM" title="<i18n:message key="itemPrestacaoContasViagem.addItemPrestacaoContasViagem" />">
			<div class="box grid_16 ">
				<div class="toggle_container">
					<div class="section">
						<form name='formItem' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/prestacaoContasViagem/prestacaoContasViagem'>
							<div class="col_100" >
								<input type='hidden' name='idItemPrestContasViagem' id="idItemPrestContasViagem"/>
								<input type='hidden' name='idItemDespesaViagem' id="idItemDespesaViagem"/>
								<input type='hidden' name='idFornecedor' id="idFornecedor"/>
								<input type='hidden' name='idItemDespesaViagem' id="idItemDespesaViagem"/>
								<div class="col_40">				
								<fieldset style="height: 55px">
									<label class="campoObrigatorio">
										<i18n:message key="itemPrestacaoContasViagem.nomeFornecedor"/>
									</label>
									<div>
										<input style="width: 85% !important;" type='text' name="nomeFornecedor" id="nomeFornecedor" maxlength="100" size="100"  />
										<img onclick="pesquisaFornecedor()" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
									</div>
								</fieldset>
								</div>
								<div class="col_20">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="itemPrestacaoContasViagem.numeroDocumento" /></label>
											<div>
												<input type="text" name="numeroDocumento" id="numeroDocumento" maxlength="50" class="Valid[Required] Description[itemPrestacaoContasViagem.numeroDocumento]" />
											</div>
									</fieldset>
								</div>
								<div class="col_20">				
									<fieldset style="height: 55px">
										<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.data"/></label>
											<div>
												<input type="text" class="Valid[Required] Format[Date] Valid[Date] datepicker" maxlength="0" id="data" name="data">
											</div>
									</fieldset>
                				</div>
			                	<div class="col_20">
		                    		<fieldset>
		                        		<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.valor"/></label>
		                            	<div>
		                              		<input  type="text" name="valor" id="valor" maxlength="6" class="Valid[Required] Description[citcorpore.comum.valor] Format[Moeda]"/>
		                            	</div>
		                    		</fieldset>
                        		</div>
								<div class="col_100">
									<fieldset>
										<label><i18n:message key="citcorpore.comum.descricao" /></label>
										<div>
											<input type='text' name="descricao" id="descricao" maxlength="200" class="Description[citcorpore.comum.descricao]" />
										</div>
									</fieldset>
								</div>
							</div>
							<button type='button' name='btnGravar' class="light" onclick='adicionarItem();'>
								<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/adcionar.png">
							<span><i18n:message key="citcorpore.comum.adicionar" /></span>
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
		
		
		<div id="POPUP_FORNECEDOR" title="<i18n:message key="citcorpore.comum.pesquisa" />">

			<div class="box grid_16 tabs" style="width: 570px;">		
				<div class="toggle_container">
					<%-- <div style="text-align: right;">
						<i18n:message key="fornecedor.cadastro"/>
						<img id="botaoCadastrarFornecedor" style="vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="chamaPopupCadastroForn()" />
					</div> --%>
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaFornecedor' style="width: 540px">
								<cit:findField formName='formPesquisaFornecedor' lockupName='LOOKUP_FORNECEDOR' id='LOOKUP_FORNECEDOR' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	
</body>
</html>