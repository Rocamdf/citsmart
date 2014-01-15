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
    </style>

    <script>
    
        addEvent(window, "load", load, false);
        function load(){        
            document.form.afterLoad = function () {
                parent.escondeJanelaAguarde();                    
            }    
        }
        
        $(function() {
            $("#POPUP_EMPREGADO").dialog({
                autoOpen : false,
                width : 450,
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

            HTMLUtils.addRow('tblIntegranteViagem', document.form, null, obj, ['idEmpregado','nome'], ['idEmpregado'] , '<i18n:message key="citcorpore.comum.registroJaAdicionado"/>', [gerarButtonDelete], null, null, false);
            
            $("#POPUP_EMPREGADO").dialog("close");
			
		}
		
		
		
		function gerarButtonDelete(row) {
			row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tblIntegranteViagem\', this.parentNode.parentNode.rowIndex);">'
		}
		
		function deleteLinha(table, index){
			HTMLUtils.deleteRow(table, index);
		}
		
		
		   function getObjetoSerializado() {
	            var obj = new CIT_RequisicaoViagemDTO();
	            HTMLUtils.setValuesObject(document.form, obj);
	            var itegranteViagem = HTMLUtils.getObjectsByTableId('tblIntegranteViagem');
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
					var dias = DateTimeUtil.diferencaEmDias(dtInicio,dtFim);
					
					document.form.qtdeDias.value = dias + 1;
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
				$("#dataInicioViagem").val("");
				$("#dataFimViagem").val("");
				return false;
			}else
				return true;
		}
		
		 $(function() {
	            $("#POPUP_CIDADE").dialog({
	                autoOpen : false,
	                width : 450,
	                height : 400,
	                modal : true
	            });
	        }); 

		
		function LOOKUP_CIDADE_select(id, desc) {
			
			if ($('#isCidade').val() == "true"){
				document.form.idCidadeOrigem.value = id;
				document.form.nomeCidadeOrigem.value= desc;
	 		} else{
	 			document.form.idCidadeDestino.value = id;
				document.form.nomeCidadeDestino.value= desc;
	 		}
			$("#POPUP_CIDADE").dialog("close");
			
		}
		
		function adicionarCidade(isCidade) {
	 		if (isCidade == true){
	 			$('#isCidade').val(true);	
	 		} else{
	 			if (isCidade == false){
	 				$('#isCidade').val(false);
	 			}
	 		}
	 		
			$("#POPUP_CIDADE").dialog("open");
		}
		
    </script>
</head>

<body>  
    <div class="box grid_16 tabs" style='margin: 0px 0px 0px 0px;'>
        <div class="toggle_container">
            <div id="tabs-2" class="block" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/requisicaoViagem/requisicaoViagem'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
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
                                <div class="columns clearfix">
                                	<div>
                                		<h2>
											<i18n:message key="requisicaoViagem.informacoesRequisicaoViagem" />
										</h2>
                                	</div>
                                	<div  class="col_25">
										<fieldset>
											 <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="requisicaoViagem.cidadeOrigem" /></label>
										<div>
											<input id="nomeCidadeOrigem" name="nomeCidadeOrigem" size="10" maxlength="10" type="text" onclick="adicionarCidade(true)" />
										</div>
										</fieldset>
									</div>
									<div  class="col_25">
										<fieldset>
											 <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="requisicaoViagem.cidadeDestino" /></label>
										<div>
											<input id="nomeCidadeDestino" name="nomeCidadeDestino" size="10" maxlength="10" type="text" onclick="adicionarCidade(false)" />
										</div>
										</fieldset>
									</div>
                                    <div  class="col_16">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="citcorpore.comum.datainicio"/></label>
										<div>
											<input id="dataInicioViagem" name="dataInicioViagem" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" onchange="calcularQuantidadeDias()" />
										</div>
										</fieldset>
									</div>
									<div  class="col_16">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="citcorpore.comum.datafim"/></label>
										<div>
											<input id="dataFimViagem" name="dataFimViagem" size="10" maxlength="10" type="text" class="Format[Date] Valid[Date] text datepicker" onchange="calcularQuantidadeDias()" />
										</div>
										</fieldset>
									</div>
									<div  class="col_16">
										<fieldset>
											<label class="campoObrigatorio" ><i18n:message key="requisicaoViagem.quantidadeDias"/></label>
										<div>
											<input id="qtdeDias" name="qtdeDias" size="10" maxlength="10" type="text" readonly="readonly"  />
										</div>
										</fieldset>
									</div>
									</div>
									<div class="columns clearfix">
									<div class="col_100">
										<div class="col_33">
	                                         <fieldset>
	                                             <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="centroResultado.custo" /></label>
	                                             <div>
	                                                 <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
	                                             </div>
	                                         </fieldset>
	                                    </div>  
	                                    <div class="col_33">
	                                         <fieldset>
	                                             <label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="requisicaoProduto.projeto" /></label>
	                                             <div>
	                                                 <select name='idProjeto' class="Valid[Required] Description[requisicaoProduto.projeto]"></select>
	                                             </div>
	                                         </fieldset>
	                                    </div>
	                                    <div class="col_33">
	                                         <fieldset>
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
												<div>
													<textarea name="descricaoMotivo" id="descricaoMotivo" cols='200' rows='5' maxlength = "2000"></textarea>
												</div>
											</fieldset>
										</div> 
	                                 </div>  
                                 </div>  
                                <div class="col_100">
                                    <div  class="col_100">
                                        <label  style="cursor: pointer;" onclick='adicionarEmpregado();' class="campoObrigatorio">
                                            <img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" /><span><b><i18n:message key="requisicaoViagem.adicionarIntegranteViagem" /></b></span>
                                        </label>
                                    </div>
                                </div>
                                <div class="col_100">
                                    <div class="col_100">
                                        <h2 class="section">
                                            <i18n:message key="requisicaoViagem.integranteViagem" />
                                        </h2>
                                    </div>
                                    <div class="col_66">
                                    <table id="tblIntegranteViagem" class="table ">
                                        <tr>
                                            <th width="1%">&nbsp;</th>
                                            <th width="20%"><i18n:message key="citcorpore.comum.nome" /></th>
                                        </tr>
                                    </table>
                                    </div>
                                </div>
                        </form>
                    </div>
            </div>  
        </div>
<div id="POPUP_EMPREGADO" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaEmp' style="width: 400px">
				<cit:findField formName='formPesquisaEmp' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>
	
	<div id="POPUP_CIDADE" title="<i18n:message  key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs">
		<div class="toggle_container">
		<div id="tabs-2" class="block">
		<div class="section">
			<form name='formPesquisaCidade' style="width: 400px">
			<input type="hidden" id="isCidade" name="isCidade">
				<cit:findField formName='formPesquisaCidade' lockupName='LOOKUP_CIDADE' id='LOOKUP_CIDADE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
			</form>
		</div>
		</div>
		</div>
		</div>
	</div>

</body>

</html>
