<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.bean.CriterioAvaliacaoDTO"%>
<%@page import="java.util.Collection"%>
<!doctype html public "">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			
     Collection<CriterioAvaliacaoDTO> colCriterios = (Collection)request.getAttribute("colCriterios");  
%>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" />
</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
<script type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
    <script type="text/javascript" src="../../cit/objects/CriterioCotacaoCategoriaDTO.js"></script>

<style>
    
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
        #btAnexos {
  			color: #8ec657!important;
  			outline: 0 !important;
  			cursor: pointer;
  			font-weight: bold;
  			font-size: 13px;
		}
</style>

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
		$("#loading_overlay .loading_message").delay(200).fadeOut(function(){}); 
		$("#loading_overlay").delay(500).fadeOut();  
	}

	function LOOKUP_CATEGORIAPRODUTO_select(id, desc) {
		document.form.restore({
			idcategoria : id
		});
	}
	
	function restaurarCategoria(id) {
		document.form.idCategoria.value = id;
		document.form.fireEvent("restore");
		
		var index = $('#tabs a[href="#tabs-1"]').parent().index();
		$('#tabs').tabs('select', index);
	}
	
	function LOOKUP_CATEGORIAPRODUTOPAI_select(idTipo, desc) {
		document.form.idCategoriaPai.value = idTipo;
		
	    var valor = desc.split('-');
		var nomeConcatenado = "";
		for(var i = 0 ; i < valor.length; i++){
			if(i == 0){
				document.form.nomeCategoriaPai.value = valor[i];
			}
		}
		fecharPopup();
	}
	
	function removerCategoria() {
		var idCategoriaServico = document.getElementById("idCategoriaServico");

		if (idCategoriaServico != null && idCategoriaServico.value == 0) {
			alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
			return false;
		}
		if (confirm(i18n_message("citcorpore.comum.deleta")))
			document.form.fireEvent("remove");
	}
	
	$(function() {
		$("#POPUP_CATEGORIAPRODUTOPAI").dialog( {
			autoOpen : false,
			width : 705,
			height : 500,
			modal : true
		});
	});
	
	$(function() {
		$("#POPUP_MENUFOTOS").dialog( {
			autoOpen : false,
			width : 705,
			height : 400,
			modal : true,
			title: i18n_message("menu.imagem")
		});
		
		$("#btnEscolherFotos").click(function() {
			document.form.fireEvent("mostraImagem");
			$('#POPUP_MENUFOTOS').dialog('close');
		});
	});
	
	function consultarCategoriaServicoSuperior(){
		$("#POPUP_CATEGORIAPRODUTOPAI").dialog("open");
	}

	function fecharPopup(){
		$("#POPUP_CATEGORIAPRODUTOPAI").dialog("close");
	}

	function anexos(){
		$('#POPUP_MENUFOTOS').dialog('open');
		uploadAnexos.refresh();
	};
	
	function situacaoAtivo() {
		var radioSituacao = document.form.situacao;
		var radioLength = radioSituacao.length;
		for(var i = 0; i < radioLength; i++) {
			radioSituacao[i].checked = false;
			if(radioSituacao[i].value == "A") {
				radioSituacao[i].checked = true;
			}
		}
	}

    var seqCriterio = '';
    incluirCriterio = function() {
        GRID_CRITERIOS.addRow();
        seqCriterio = NumberUtil.zerosAEsquerda(GRID_CRITERIOS.getMaxIndex(),5);
        eval('document.form.idCriterio' + seqCriterio + '.focus()');
    }

    exibeCriterio = function(serializeCriterio) {
        if (seqCriterio != '') {
            if (!StringUtils.isBlank(serializeCriterio)) {
                var criterioDto = new CIT_CriterioCotacaoCategoriaDTO();
                criterioDto = ObjectUtils.deserializeObject(serializeCriterio);
                try{
                    eval('HTMLUtils.setValue("idCriterio' + seqCriterio + '",' + criterioDto.idCriterio + ')');
                }catch(e){
                }            
                eval('document.form.pesoCotacao' + seqCriterio + '.value = "' + criterioDto.pesoCotacao + '"');
            }        
        }
    }

    getCriterio = function(seq) {
        var criterioDto = new CIT_CriterioCotacaoCategoriaDTO();
        
        seqCriterio = NumberUtil.zerosAEsquerda(seq,5);
        criterioDto.sequencia = seq;
        criterioDto.idCriterio = parseInt(eval('document.form.idCriterio' + seqCriterio + '.value'));
        criterioDto.pesoCotacao = eval('document.form.pesoCotacao' + seqCriterio + '.value');
        return criterioDto;
    }    

    verificarCriterio = function(seq) {
        var idCriterio = eval('document.form.idCriterio' + seq + '.value');
        var count = GRID_CRITERIOS.getMaxIndex();
        for (var i = 1; i <= count; i++){
            if (parseInt(seq) != i) {
                 var trObj = document.getElementById('GRID_CRITERIOS_TD_' + NumberUtil.zerosAEsquerda(i,5));
                 if (!trObj){
                    continue;
                 }                
                 var idAux = eval('document.form.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.value');
                 if (idAux == idCriterio) {
                      alert("<i18n:message key='categoria.produto.criterio_selecionado' />");                      
                      eval('document.form.idCriterio' + seq + '.focus()');
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
                    if (StringUtils.isBlank(criterioDto.pesoCotacao)){
                        alert("<i18n:message key='categoriaProduto.informePeso' />");
                        eval('document.form.pesoCotacao' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');                     
                        return false;
                    }
                    objs[contadorAux] = criterioDto;
                    contadorAux = contadorAux + 1;
                }else{
                    alert("<i18n:message key='categoriaProduto.selecioneCriterio' />");
                    eval('document.form.idCriterio' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');                     
                    return false;
                }        
            }
            document.form.colCriterios_Serialize.value = ObjectUtils.serializeObjects(objs); 
            return true;
        }catch(e){
        	
        }       
    }    
    
    function gravar() {
        if (!tratarCriterios()){
            return;
        }

        document.form.save();
    }
    
    $(function() {
    	var criteriosPesosCotacao = [
			'pesoCotacaoPreco',
			'pesoCotacaoPrazoEntrega',
			'pesoCotacaoPrazoPagto',
			'pesoCotacaoPrazoGarantia',
			'pesoCotacaoTaxaJuros'
		];    	
    	
    	// Associa a função de validação aos critérios e pesos obrigatórios para a cotação (quando o campo perder o foco).
    	for (i in criteriosPesosCotacao) {
    		$('#' + criteriosPesosCotacao[i]).focusout(function() {    			
    			if ($(this).val() == '')
    				return;
    			
    			// Referencia o elemento que precede (anterior) ao elemento pai da caixa de texto que está sendo validada.
    			var nomeCampo = $(this).parent().prev().html();
    			
    			nomeCampo = nomeCampo.substr(0, nomeCampo.indexOf('&nbsp;') ).replace(/^\s+|\s+$/g, '').toLowerCase();
    			
    			var msg = null;
    			
    			// Verificando se o valor do critério ou peso de cotação é numérico e possui um ou dois algarismos.
    			if ($(this).val().match(/^\d{1,2}$/) ) {    				
    				if ($(this).val() < 0 || $(this).val() > 10) {
    					msg = i18n_message("categoriaProduto.valorInformado") + ' ' + nomeCampo + ' ' + i18n_message("categoriaProduto.invalidoforaIntervalo");
    					alert(msg);        				
        				$(this).val('');
        			}
    			} else {
    				msg = i18n_message("categoriaProduto.valorInformado") + ' ' + nomeCampo + ' ' + i18n_message("categoriaProduto.invalidoforaIntervalo");			
    				alert(msg);
    				$(this).val('');    				
    			}
    		});
    	}  	
    });
</script>
</head>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="categoriaProduto.categoria_produto" />
				</h2>
			</div>
			<div id="tabs" class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message	key="categoriaProduto.cadastro_categoria" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="categoriaProduto.pesquisa_categoria" />	</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form id='form' name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/categoriaProduto/categoriaProduto'>
								<div class="columns clearfix">
									<input type='hidden' name="idCategoria" id="idCategoria" /> 
									<input type='hidden' name='idCategoriaPai' />
                                    <input type='hidden' name='colCriterios_Serialize' />

									<div class="col_33">
										<fieldset style="height: 60px;">
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="nomeCategoria" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset  style="height: 60px;">
											<label><i18n:message key="categoriaProduto.categoria_superior" /></label>
											<div>
												<div>
													<input onclick="consultarCategoriaServicoSuperior()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeCategoriaPai" maxlength="70" size="70"  />
													<img onclick="consultarCategoriaServicoSuperior()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
												</div>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
									<fieldset style="height: 60px;">
										<label class="campoObrigatorio">
											<i18n:message key="categoriaProduto.categoria_situacao"/>
										</label>
										<div class="inline">
											<label>
												<input type='radio' id="situacao" name="situacao" value="A" class="Valid[Required] Description[categoriaProduto.categoria_situacao]" checked="checked" />
												<i18n:message key="categoriaProduto.categoria_ativo"/>
											</label>
											<label>
												<input type='radio' id="situacao" name="situacao" value="I" class="Valid[Required] Description[categoriaProduto.categoria_situacao]" />
												<i18n:message key="categoriaProduto.categoria_inativo"/>
											</label>						
										</div>
									</fieldset>
									<br><br>
								 </div>

					            <div class="col_100">
					                <div class="col_50">
					                    <h2 class="section">
					                         <i18n:message key="categoriaProduto.criteriosObrigatorios" />
					                       </h2>
					                    <div class="col_50">
					                        <fieldset>
					                            <label class="campoObrigatorio"><i18n:message key="coletaPreco.criterioPreco" />&nbsp;<i18n:message key="cotacao.valoresCriterio" />
					                            </label>
					                            <div>
			                                   		<input id="pesoCotacaoPreco" type='text' name="pesoCotacaoPreco" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPreco]" />     
					                            </div>
					                        </fieldset>
					                    </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><i18n:message key="coletaPreco.criterioPrazoEntrega" />&nbsp;<i18n:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoPrazoEntrega" type='text' name="pesoCotacaoPrazoEntrega" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPrazoEntrega]" />     
					                               </div>
					                           </fieldset>
					                       </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><i18n:message key="coletaPreco.criterioPrazoPagto" />&nbsp;<i18n:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoPrazoPagto" type='text' name="pesoCotacaoPrazoPagto" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPrazoPagto]" />     
					                               </div>
					                           </fieldset>
					                       </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><i18n:message key="coletaPreco.criterioPrazoGarantia" />&nbsp;<i18n:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoPrazoGarantia" type='text' name="pesoCotacaoPrazoGarantia" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioPrazoGarantia]" />     
					                               </div>
					                           </fieldset>
					                       </div>
					                       <div class="col_50">
					                           <fieldset>
					                               <label class="campoObrigatorio"><i18n:message key="coletaPreco.criterioTaxaJuros" />&nbsp;<i18n:message key="cotacao.valoresCriterio" />
					                               </label>
					                               <div>
					                                  <input id="pesoCotacaoTaxaJuros" type='text' name="pesoCotacaoTaxaJuros" maxlength="2" class="Valid[Required] Description[coletaPreco.criterioTaxaJuros]" />     
					                               </div>
					                           </fieldset>
					                       </div>
					                </div>

					                <div class="col_50">
					                    <h2 class="section">
					                         <i18n:message key="categoriaProduto.criteriosVariaveis" />
					                    </h2>
					                <!-- Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 17:00 - ID Citsmart: 120948 - 
									* Motivo/Comentário: Opção de adicionar muito longe da grid, Inserido proximo a citgrid e alterado para button-->
					                    <div id='divNovoCriterio' class="col_100">
					                      <!--   <div class="col_66">
					                             <label>&nbsp;</label>
					                        </div> -->
					                        <div class="col_33">
					                             <button type='button' onclick='incluirCriterio();' class='light img_icon has_text'>
					                               <i18n:message key="cotacao.novoCriterio" />
					                             </button>
					                        </div>
					                    </div>
					                     <div class="col_100">
					                         <fieldset style='height:200px'>
					                           <div style='width:330px;height:190px;overflow:auto;'>
					                           <cit:grid id="GRID_CRITERIOS" columnHeaders="categoriaProduto.cabecalhoGridCriterios" styleCells="linhaGrid">
					                               <cit:column idGrid="GRID_CRITERIOS" number="001">
					                                   <select name='idCriterio#SEQ#' id='idCriterio#SEQ#' style='border:none; width: 200px' onchange='verificarCriterio("#SEQ#");'>
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
					                                   <input type='text' name='pesoCotacao#SEQ#' id='pesoCotacao#SEQ#' size='3' maxlength='2' style='border:none; text-align: right;' class='Format[Numero]'/>
					                               </cit:column>
					                           </cit:grid>
					                           </div>
					                         </fieldset>
					                     </div>
					                </div>
                                 </div>
									<!-- Desenvolvedor: Pedro Lino - Data: 28/10/2013 - Horário: 17:00 - ID Citsmart: 120948 - 
									* Motivo/Comentário: Layout sem usabilidade, dificultando o entendimento-->
									<div class="col_99">
										<fieldset>
											<br /> <a id="btAnexos" onclick="return anexos();" class='' ><i18n:message key="citcorpore.comum.adicionarAlterarImagem" /></a>
											<div>
												<input type="hidden" name="imagem" />
												<label>&nbsp;</label>
												
												<div id='divImgFoto' style='border: 0px; width: 60%; height: 100px;'>
												</div>
											
												<%-- <input type="button" class="light img_icon has_text"
													id="btAnexos" onclick="return anexos();"
													value="<i18n:message key="citcorpore.comum.adicionarAlterarImagem" />..." />
												<br />
												<br /> --%>
											</div>
										</fieldset>
									</div>
								</div>
								<label>&nbsp;</label>
								<button type='button' name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();document.form.fireEvent("load");'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnExcluir' class="light"
									onclick='removerCategoria();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<h3>
								<i18n:message key="categoriaProduto.categoria_produto" />
							</h3>
							<div id="divCategoria" style="height: 290px;overflow:auto;">
							</div>
						</div>
						
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_CATEGORIAPRODUTOPAI" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name='formCategoriaProdutoSuperior'>
							<cit:findField formName='formCategoriaProdutoSuperior' 
							lockupName='LOOKUP_CATEGORIAPRODUTOPAI' 
							id='LOOKUP_CATEGORIAPRODUTOPAI' top='0' left='0' len='550' heigth='400' 
							javascriptCode='true' 
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<div id="POPUP_MENUFOTOS" style='display:none'>
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControl style="height:100px;width:98%;border:0px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>
			<button id="btnEscolherFotos" name="btnEscolherFotos" type="button">
				<i18n:message key="citcorpore.comum.gravar" />
			</button>
		</form>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>
