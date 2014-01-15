<%@page import="br.com.citframework.util.Constantes"%>
<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>
<!doctype html public "">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
		%>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		<script type="text/javascript" src="../../js/parametroCorpore.js"></script>
		<script charset="ISO-8859-1" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
		<script type="text/javascript" src="../../cit/objects/BIConsultaColunasDTO.js"></script>
		
		<script>
			var popup;
		    addEvent(window, "load", load, false);
		    function load(){		
				popup = new PopupManager(850, 500, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		    }	
		    
			function LOOKUP_BICONSULTAS_select(id, desc) {
				document.form.restore({
					idConsulta : id
				});
			}
		    exibeColuna = function(serializeColuna) {
		        if (seqColuna != '') {
		            if (!StringUtils.isBlank(serializeColuna)) {
		                var colunaDto = new CIT_BIConsultaColunasDTO();
		                colunaDto = ObjectUtils.deserializeObject(serializeColuna);           
		                eval('document.form.nomeColuna' + seqColuna + '.value = "' + colunaDto.nomeColuna + '"');
		                eval('document.form.ordem' + seqColuna + '.value = "' + colunaDto.ordem + '"');
		            }        
		        }
		    }			
		    function tratarColunas(){
		    	document.form.colCriterios_Serialize.value = '';
		        try{
		            var count = GRID_COLUNAS.getMaxIndex();
		            var contadorAux = 0;
		            var objs = new Array();
		            for (var i = 1; i <= count; i++){
		                var trObj = document.getElementById('GRID_COLUNAS_TD_' + NumberUtil.zerosAEsquerda(i,5));
		                if (!trObj){
		                    continue;
		                }
		                var biConsultaColunasDTO = getConsulta(i);
	                    if (StringUtils.isBlank(biConsultaColunasDTO.nomeColuna)){
	                        alert("<i18n:message key='construtorconsultas.nomeColuna' />");
	                        eval('document.form.nomeColuna' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');                     
	                        return false;
	                    }
	                    if (StringUtils.isBlank(biConsultaColunasDTO.ordem)){
	                        alert("<i18n:message key='construtorconsultas.ordem' />");
	                        eval('document.form.ordem' + NumberUtil.zerosAEsquerda(i,5) + '.focus()');                     
	                        return false;
	                    }	                    
	                    objs[contadorAux] = biConsultaColunasDTO;
	                    contadorAux = contadorAux + 1;
		            }
		            document.form.colCriterios_Serialize.value = ObjectUtils.serializeObjects(objs); 
		            return true;
		        }catch(e){
		        	alert('Ocorreu um erro ao processar a solicitação de Gravação. Tente novamente! ' + e.message);
		        	return false;
		        }       
		    }  
		    getConsulta = function(seq) {
		        var biConsultaColunasDTO = new CIT_BIConsultaColunasDTO();
		        
		        var seqCriterio = NumberUtil.zerosAEsquerda(seq,5);
		        biConsultaColunasDTO.nomeColuna = eval('document.form.nomeColuna' + seqCriterio + '.value');
		        biConsultaColunasDTO.ordem = eval('document.form.ordem' + seqCriterio + '.value');
		        return biConsultaColunasDTO;
		    } 		    
		    
		    var seqColuna = '';
		    incluirColuna = function() {
		    	GRID_COLUNAS.addRow();
		        seqColuna = NumberUtil.zerosAEsquerda(GRID_COLUNAS.getMaxIndex(),5);
		        eval('document.form.nomeColuna' + seqColuna + '.focus()');
		    }	
		    
		    function gravar(){
		    	if (tratarColunas()){
		    		document.form.save();
		    	}
		    }
		    function exportar(){
		    	if (tratarColunas()){
			    	document.form.fireEvent("exportar");		    	
		    	}		    	
		    }
		    
		    importarReport = function(){
		        if (!confirm('<i18n:message key="construtorconsultas.importaraQuestionario" />')){
		            return;
		        } 

		        JANELA_AGUARDE_MENU.setTitle('Aguarde... importando...');
		        JANELA_AGUARDE_MENU.show();

			    document.formImportar.setAttribute("enctype","multipart/form-data"); 
			    document.formImportar.setAttribute("encoding","multipart/form-data");        
		        document.formImportar.submit();
		    }		    

		</script>
		
	</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>	
	<body>	
		<div id="wrapper" style="overflow: hidden;">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="construtorconsultas.construtorconsultas"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="construtorconsultas.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="construtorconsultas.pesquisa"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">											
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/construtorConsultas/construtorConsultas'>
									<input type='hidden' name='colCriterios_Serialize' />
									<div class="columns clearfix">
										<div class="columns clearfix">
											<div class="col_25">				
												<fieldset>
													<label class="campoObrigatorio">Id:</label>
														<div>
														  <input type='text' name="idConsulta" maxlength="11" size="11" readonly="readonly"/>
														</div>
												</fieldset>
											</div>										
											<div class="col_75">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="construtorconsultas.nomeconsulta"/></label>
														<div>
														  <input type='text' name="nomeConsulta" maxlength="255" size="70" class="Valid[Required] Description[construtorconsultas.nomeconsulta]" />
														</div>
												</fieldset>
											</div>
											<div class="col_50">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="construtorconsultas.identificacao"/></label>
														<div>
															<input type='text' name="identificacao" maxlength="70" size="70" class="Valid[Required] Description[construtorconsultas.identificacao]" />
														</div>
												</fieldset>
											</div>												
											<div class="col_50">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="construtorconsultas.tipoconsulta"/></label>
														<div>
															<select name='tipoConsulta' class="Valid[Required] Description[construtorconsultas.tipoconsulta]">
																<option value=""><i18n:message key="citcorpore.comum.selecione" /></option>
																<option value="C"><i18n:message key="construtorconsultas.informacoescruzadas" /></option>
																<!-- option value="L"><i18n:message key="construtorconsultas.listagem" /></option>  -->
																<option value="T"><i18n:message key="construtorconsultas.template" /></option>
															</select>
														</div>
												</fieldset>
											</div>	
											<div class="col_100">
												<div class="col_50">
													<fieldset>
														<label><i18n:message key="construtorconsultas.categoria"/></label>
															<div>
																<select name='idCategoria' class="Valid[Required] Description[construtorconsultas.categoria]">
																	<option value=""><i18n:message key="citcorpore.comum.selecione" /></option>
																</select>
															</div>
													</fieldset>
												</div>	
												<div class="col_50">
													<fieldset>
														<label><i18n:message key="construtorconsultas.naoAtualizBase"/></label>
															<div>
																<input type='checkbox' name='naoAtualizBase' id='naoAtualizBase' value='S'/>
															</div>
													</fieldset>
												</div>		
											</div>																													
											<div class="col_100">
												<fieldset>
													<label><i18n:message key="construtorconsultas.textoSQL"/></label>
														<div>
															<textarea rows="10" cols="70" name="textoSQL"></textarea>
														</div>
												</fieldset>
											</div>
											<div class="col_100">
					                             <label  style="cursor: pointer;" onclick='incluirColuna();'>
					                                 <img  src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" /><span><b><i18n:message key="construtorconsultas.novaColuna" /></b></span>
					                             </label>
					                        </div>											
											<div class="col_100">
												<cit:grid id="GRID_COLUNAS" columnHeaders="construtorconsultas.cabecalhoGridColunas" styleCells="linhaGrid">
					                               <cit:column idGrid="GRID_COLUNAS" number="001">
					                                   <input type='text' name='nomeColuna#SEQ#' id='nomeColuna#SEQ#' size='70' maxlength='90' style='border:none;'/>
					                               </cit:column>
					                               <cit:column idGrid="GRID_COLUNAS" number="002">
					                                   <input type='text' name='ordem#SEQ#' id='ordem#SEQ#' size='3' maxlength='2' style='border:none; text-align: right;' class='Format[Numero]'/>
					                               </cit:column>
					                           </cit:grid>
											</div>
											<div class="col_100">
												<fieldset>
													<label><i18n:message key="construtorconsultas.parametros"/></label>
														<div>
															<textarea rows="10" cols="70" name="parametros"></textarea>
														</div>
												</fieldset>
											</div>		
											<div class="col_100">
												<fieldset>
													<label><i18n:message key="construtorconsultas.scriptExec"/></label>
														<div>
															<textarea rows="10" cols="70" name="scriptExec"></textarea>
														</div>
												</fieldset>
											</div>
											<div class="col_100">
												<fieldset>
													<label><i18n:message key="construtorconsultas.template"/></label>
														<div>
															<textarea rows="10" cols="70" name="template"></textarea>
														</div>
												</fieldset>
											</div>																															
										</div>
										<br>
									</div>	
									<br><br>
									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='GRID_COLUNAS.deleteAllRows();document.form.clear();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnExportar' class="light" onclick='exportar();'>
										<span><i18n:message key="dataManager.export"/></span>
									</button>
									<button type='button' name='btnImportar' class="light" onclick='POPUP_IMPORTAR.show();'>
										<span><i18n:message key="dataManager.import"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_BICONSULTAS' id='LOOKUP_BICONSULTAS' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>		
			</div>
		</div>
		
<cit:janelaPopup modal="true" style="display:none;top:300px;width:600px;left:100px;height:160px;position:absolute;" title='<i18n:message key="dataManager.import" />' id="POPUP_IMPORTAR">
   <label style="font-size:15px;font-weight:bold;"><i18n:message key="citcorpore.comum.arquivo"/></label>
    <form name='formImportar' method='post' ENCTYPE="multipart/form-data" action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/construtorConsultas/construtorConsultas.load'>
		<input type='hidden' name='acao' id='acaoImportar' value='<i18n:message key="dataManager.import" />'/>
        <table width="100%">
            <tr>
                <td>
                
                  <i18n:message key="citcorpore.comum.arquivo" />:*
                </td>
                <td>
                   <input type='file' name='fileImportar' size="50" value='<i18n:message key="questionario.selecionarArquivo" />'/>
                </td>
                <td>
                    <input type='button' value='OK' onclick='importarReport()' />
                </td>
            </tr>
        </table>
    </form>
</cit:janelaPopup>
		
		<%@include file="/include/footer.jsp"%>
	</body>
</html>