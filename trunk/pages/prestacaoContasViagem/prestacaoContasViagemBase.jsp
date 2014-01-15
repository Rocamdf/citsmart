<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ItemPrestacaoContasViagemDTO"%>
<%@page import="java.util.Collection"%>
<!doctype html public "">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
			
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ItemPrestacaoContasViagemDTO.js"></script>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js?nocache=<%=new java.util.Date().toString()%>"></script>
			
			<style type="text/css">
				.table {
				border-left:1px solid #ddd;
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
			</style>
		
		<script type="text/javascript">

			var objTab = null;
			var popup;

			addEvent(window, "load", load, false);
			
			function load() {
				popup = new PopupManager(900, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
				
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 1);
				}
				
				$("#POPUP_FORNECEDOR").dialog({
					autoOpen : false,
					width : 620,
					height : 400,
					modal : true
				});
				
				$("#POPUP_ADICIONARITEM").dialog({
					autoOpen : false,
					width : 1050,
					height : 220,
					modal : true
				});
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

			function chamaPopupCadastroForn(){
				dimensionaPopupCadastroRapido("1300","600");
				popup.abrePopupParms('fornecedor', '', '');
			}

			function dimensionaPopupCadastroRapido(w, h) {
				$("#popupCadastroRapido").dialog("option","width", w)
				$("#popupCadastroRapido").dialog("option","height", h)
			}

			function pesquisaFornecedor(){
				$("#POPUP_FORNECEDOR").dialog("open");
			}
			
			function fecharPopupFornecedor(){
				$("#POPUP_FORNECEDOR").dialog("close");
			}
			
			$(function() {
				$("#addItemPrestacaoContasViagem").click(function() {
					$("#POPUP_ADICIONARITEM").dialog("open");
				});
			});
			
			function adicionarItem(){
				var idFornecedor = document.getElementById("idFornecedor").value;
				var nomeFornecedor = document.getElementById("nomeFornecedor").value;
				var numeroDocumento = document.getElementById("numeroDocumento").value;
				var data = document.getElementById("data").value;
				var valor = document.getElementById("valor").value;
				var descricao = document.getElementById("descricao").value;

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
				addLinhaTabelaItem(idFornecedor, nomeFornecedor, numeroDocumento, data, valor, descricao);
			}

			function addLinhaTabelaItem(idFornecedor, nomeFornecedor, numeroDocumento, data, valor, descricao){
				var tbl = document.getElementById('tabelaItemPrestacaoContasViagem');
				tbl.style.display = 'block';
				var tamanhoTabela = tbl.rows.length;
				var obj = new CIT_ItemPrestacaoContasViagemDTO();

		        obj.idFornecedor = idFornecedor;
	            obj.nomeFornecedor = nomeFornecedor;
	            obj.numeroDocumento = numeroDocumento;
	            obj.data = data;
	            obj.valor = valor;
	            obj.descricao = descricao;

	            HTMLUtils.addRow('tabelaItemPrestacaoContasViagem', document.form, null, obj, ['idFornecedor','nomeFornecedor','numeroDocumento','data','valor','descricao'], ['nomeFornecedor','numeroDocumento', 'data', 'valor'] , '<i18n:message key="citcorpore.comum.registroJaAdicionado"/>', [gerarButtonDelete], null, null, false);
				
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
			
			function ItemPrestacaoContasViagemDTO(idItemPrestContasViagem, nomeFornecedor, numeroDocumento, data, valor, descricao){
				this.idItemPrestContasViagem = idItemPrestContasViagem;
		 		this.nomeFornecedor = nomeFornecedor; 
		 		this.numeroDocumento = numeroDocumento;
		 		this.data = data;
		 		this.valor = valor;
                this.descricao = descricao;
		 	}
			
			function serializaItemPrestacaoContasViagem() {
				var tabela = document.getElementById('tabelaItemPrestacaoContasViagem');
				var count = tabela.rows.length;
				var listaItens = [];
				
				for ( var i = 1; i < contadorGlobal; i++) {
					if (document.getElementById('idItemPrestContasViagem' + i) != "" && document.getElementById('idItemPrestContasViagem' + i) != null) {
						var idItemPrestContasViagem = document.getElementById('idItemPrestContasViagem' + i).value;
						var nomeFornecedor = document.getElementById('nomeFornecedor' + i).value;
						var numeroDocumento = document.getElementById('numeroDocumento' + i).value;
						var data = document.getElementById('data' + i).value;
						var valor = document.getElementById('valor' + i).value;
						var descricao = document.getElementById('descricao' + i).value;
						var item = new ItemPrestacaoContasViagemDTO(idItemPrestContasViagem, nomeFornecedor, numeroDocumento, data, valor, descricao);
						listaItens.push(item);
					}
				}
				document.form.listaItens.value = ObjectUtils.serializeObjects(listaItens);
				document.form.save();
			}
				
			function setItemPrestacaoContasViagem() {
				document.getElementById("idFornecedor").disabled = 0;
                document.getElementById("idNumeroDocumento").disabled = 0;
				document.getElementById("idData").disabled = 0;
				document.getElementById("idValor").disabled = 0;
                document.getElementById("idDescricao").disabled = 0;
			}
			
			function deleteAllRows() {
				var tabela = document.getElementById('tabelaItemPrestacaoContasViagem');
				var count = tabela.rows.length;

				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				document.getElementById('tabelaItemPrestacaoContasViagem').style.display = "none";
			}

			function gravar(){
				var obj = new CIT_ItemPrestacaoContasViagemDTO();
		        HTMLUtils.setValuesObject(document.form, obj);
		        var itemPrestacaoContaViagem = HTMLUtils.getObjectsByTableId('tabelaItemPrestacaoContasViagem');
		        document.form.listItens.value = ObjectUtils.serializeObjects(itemPrestacaoContaViagem);
		        document.form.save();
			}

			function excluir() {
				if (document.getElementById("idPrestacaoContasViagem").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
			}
			
		</script>		
				
	</head>
	<body>
	<div id="wrapper" style="overflow: hidden;">
		<% 
			if (iframe == null) { 
		%>
			<%@ include file="/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file="/include/menu_horizontal.jsp" %>
			<%
				}
			%>				
				<div class="flat_area grid_16">
					<h2><i18n:message key="prestacaoContasViagem"/></h2>
				</div>
				<div class="box grid_16 tabs">
				   <ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="prestacaoContasViagem.cadastro"/></a>
						</li>
						<%-- <li>
							<a href="#tabs-2" class="round_top"><i18n:message key="prestacaoContasViagem.pesquisa"/></a>
						</li> --%>
					</ul> 
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/prestacaoContasViagem/prestacaoContasViagem'>
									<div class="columns clearfix">
										<input type='hidden' name='idPrestacaoContasViagem'/>
										<input type='hidden' name='idResponsavel'/>
										<input type='hidden' name='idAprovacao'/>
										<input type='hidden' name='idSolicitacaoServico'/>
										<input type='hidden' name='idEmpregado'/>
										<input type='hidden' name='listItens' />
										<div class="columns clearfix">
											<div>
                                				<h2>
													<i18n:message key="prestacaoContasViagem.prestacaoContasViagem" />
												</h2>
                                			</div>
											<div class="col_40">
												<fieldset>
													<label><i18n:message key="prestacaoContasViagem.solicitante"/></label>
														<div>
														  <input type='text' name="idEmpregado" id="idEmpregado" value="OBS.: Setado de outra tela" maxlength="70" size="70" disabled="disabled" class="Description[prestacaoContasViagem.solicitante]"/>
														</div>
												</fieldset>
											</div>
											<div class="col_15">
												<fieldset>
													<label><i18n:message key="prestacaoContasViagem.numeroSolicitacao"/></label>
														<div>
														  <input type='text' name="idSolicitacaoServico" id="idSolicitacaoServico" value="OBS.: Setado de outra tela" maxlength="70" size="70" disabled="disabled" class="Description[prestacaoContasViagem.numero_solicitacao]"/>
														</div>
												</fieldset>
											</div>
											<%-- <div class="col_15">				
												<fieldset style="height: 55px">
													<label><i18n:message key="citcorpore.comum.data"/></label>
													<div>
														<input type="text" class="Format[Date] Valid[Date] datepicker" maxlength="0" id="dataHora" name="dataHora">
													</div>
												</fieldset>
                							</div> --%>
											<div class="col_15">
												<fieldset style="height: 55px !important;">
													<label class="campoObrigatorio campoEsquerda"><i18n:message key="citcorpore.comum.situacao"/></label>
													<input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacao" id="situacao" value="A" checked="checked"/><i18n:message key="citcorpore.comum.aprovada"/>
													<input class="Valid[Required] Description[citcorpore.comum.situacao]" type="radio" name="situacao" id="situacao" value="NA"/><i18n:message key="citcorpore.comum.naoAprovada"/>
												</fieldset>
											</div>
										</div>
										<br>
									</div>
									
									<h2 class="section">
										<i18n:message key="itemPrestacaoContasViagem" />
									</h2>
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<label id="addItemPrestacaoContasViagem" style="cursor: pointer;" title="<i18n:message key="itemPrestacaoContasViagem.cliqueAdicionar" />">
													<i18n:message key="itemPrestacaoContasViagem.adicionarItem" />
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png">
												</label>
												<div id="divItens">
													<table class="table" id="tabelaItemPrestacaoContasViagem" style="display: none;">
														<tr>
															<th style="width: 1%;"></th>
															<th style="width: 30%;"><i18n:message  key="itemPrestacaoContasViagem.nomeFornecedor" /></th>
															<th style="width: 10%;"><i18n:message  key="itemPrestacaoContasViagem.numeroDocumento" /></th>
                                                        	<th style="width: 10%;"><i18n:message  key="itemPrestacaoContasViagem.data" /></th>
                                                        	<th style="width: 10%;"><i18n:message  key="citcorpore.comum.valor" /></th>
                                                       		<th style="width: 30%;"><i18n:message  key="citcorpore.comum.descricao" /></th>
														</tr>
													</table>
												</div>
											</fieldset>
										</div>
									</div>
									
									<br><br>
								 	<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();documento.formItem.clear();deleteAllRows();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" /></span>
									</button>
									<button type='button' name='btnExcluir' class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" /></span>
									</button> 
								</form>
							</div>
						 </div>
						<%-- <div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_ALCADA' id='LOOKUP_ALCADA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
								</form>
							</div>
						</div>  --%>
					</div>
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
								<input type='hidden' name='idItemPrestContasViagem'/>
								<input type='hidden' name='idItemDespesaViagem'/>
								<input type='hidden' name='idFornecedor'/>
								<div class="col_55">				
								<fieldset style="height: 55px">
									<label class="campoObrigatorio">
										<i18n:message key="itemPrestacaoContasViagem.nomeFornecedor"/>
									</label>
									<div>
										<input onclick="pesquisaFornecedor()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeFornecedor" id="nomeFornecedor" maxlength="100" size="100"  />
										<img onclick="pesquisaFornecedor()" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
										<input type="hidden" name="idItemPrestContasViagem" id="idItemPrestContasViagem" />
									</div>
								</fieldset>
								</div>
								<div class="col_15">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="itemPrestacaoContasViagem.numeroDocumento" /></label>
											<div>
												<input type="text" name="numeroDocumento" id="numeroDocumento" maxlength="50" class="Valid[Required] Description[itemPrestacaoContasViagem.numeroDocumento]" />
											</div>
									</fieldset>
								</div>
								<div class="col_15">				
									<fieldset style="height: 55px">
										<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.data"/></label>
											<div>
												<input type="text" class="Valid[Required] Format[Date] Valid[Date] datepicker" maxlength="0" id="data" name="data">
											</div>
									</fieldset>
                				</div>
			                	<div class="col_15">
		                    		<fieldset>
		                        		<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.valor"/></label>
		                            	<div>
		                              		<input type="text" name="valor" id="valor" maxlength="6" class="Valid[Required] Description[citcorpore.comum.valor] Format[Moeda]"/>
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
		
		<!-- TELA DE PESQUISA DOS FORNECEDORES -->
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
		
		<div id="popupCadastroRapido">
			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
		</div>
									
		<%@include file="/include/footer.jsp"%>
	</body>
</html>