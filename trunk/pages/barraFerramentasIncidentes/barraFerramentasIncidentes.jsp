<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.BarraFerramentasIncidentesDTO" %>

<!doctype html public "">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
	 		String id = request.getParameter("id");
		%>
		<%@ include file="/include/security/security.jsp" %>
		<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
		<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
		<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
		<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
		<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
				
		<title><i18n:message key="citcorpore.comum.title" /></title>
				
		<%@ include file="/include/noCache/noCache.jsp" %>
		<%@ include file="/include/header.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	
		<script type="text/javascript" src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/js/UploadUtils.js"></script>
		<script type="text/javascript"src="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/js/PopupManager.js"></script>	
		
		<style>
			#barraFerramentasIncidentes ul li.li_menu {
				float: left;
				width: 85px;
				height: 50px;
			}

			.menu_horizontal{
				left: 40;
			}
				
			.table {
				border-left: 1px solid #ddd;
			}
	
			.table th {
				border: 1px solid #ddd;
				padding: 4px 10px;
				border-left: none;
				background: #eee;
			}
	
			.table td {
				border: 1px solid #ddd;
				padding: 4px 10px;
				border-top: none;
				border-left: none;
			}
	
			div#main_container {
				margin: 10px 10px 10px 10px;
			}
		</style>
			
		<script type="text/javascript">
			var popup;
			var menusLiberados;
			var todosOsMenus;
			var objTab = null;

			function LOOKUP_OCORRENCIA_SOLICITACAO_select(id, desc) {
				document.formOcorrenciaSolicitacao.restore({
					idOcorrencia : id
				});
			}

			function salvar() {
				document.formOcorrenciaSolicitacao.descricao.value = document.formOcorrenciaSolicitacao.descricao1.value;
				if (document.getElementById("idOcorrencia").value != null && document.getElementById("idOcorrencia").value != "") {
					alert(i18n_message("gerenciaservico.suspensaosolicitacao.validacao.alteraregistroocorrencia") );
				} else {
					document.formOcorrenciaSolicitacao.save();
				}	
			}
			
			function fecharBaseConhecimentoView() {
				$('#popupCadastroRapido').dialog('close');
			}
		
			$(function() {
				popupBarraFerramenta = new PopupManager(1300, 600, "<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/");
				popupCadastroCategoriaOcorrencia = new PopupManager(1200, 450, "<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/");
				popupCadastroOrigemOcorrencia = new PopupManager(1200, 450, "<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/");
				
				document.formOcorrenciaSolicitacao.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				};
				
				$(".POPUP_barraFerramentasIncidentes").dialog({
					autoOpen : false,
					width : 1000,
					height : 580,
					modal : true
				});
				
				$('.POPUP_PESQUISA_CATEGORIA_OCORRENCIA').dialog({
					autoOpen: false,
					width: 600,
					height: 400,
					modal: true
				});
				
				$('.POPUP_PESQUISA_ORIGEM_OCORRENCIA').dialog({
					autoOpen: false,
					width: 600,
					height: 400,
					modal: true
				});
				
				menusLiberados = [];
				todosOsMenus = [];
				todosOsMenus.push("btAnexos");
				todosOsMenus.push("btOcorrencias");
				todosOsMenus.push("btIncidentesRelacionados");
				todosOsMenus.push("btBaseConhecimentoView");	
				menusLiberados.push("btBaseConhecimentoView");

				//adicionar um IF para verificar ID de Serviço quando já estier sendo feito o restore.
				liberaMenusDeConsulta();
				escondeMenusBloqueadosEMostraLiberados();
	
				/**
	 			* O nome do método já diz tudo.
	 			*/
				function escondeMenusBloqueadosEMostraLiberados() {
					for(var i = 0; i < todosOsMenus.length; i++) {
						if(!isLiberado(todosOsMenus[i]) ) {
							document.getElementById(todosOsMenus[i]).style.display = "none";
						} else {
							document.getElementById(todosOsMenus[i]).style.display = "block";
						}
					}
				}
	
				/**
				 * Só deve ser chamada quando houver restore do serviço
				 */
				function liberaMenusDeConsulta() {
					menusLiberados.push("btAnexos");
					menusLiberados.push("btOcorrencias");
					menusLiberados.push("btIncidentesRelacionados");
					menusLiberados.push("btBaseConhecimentoView");
				}

				/**
				 * Bloqueia os menus que não devem ser acessados caso seja 
				 * nova solicitação de serviço.
				 */
				function bloqueiaMenusDeConsulta() {
					deletaItemLista("btAnexos");
					deletaItemLista("btOcorrencias");
					deletaItemLista("btIncidentesRelacionados");
				}

				/**
				 * Deleta um item da lista e a reordena.
				 */
				function deletaItemLista(nomeItem) {
					var novaLista = [];
					for(var i = 0; i < menusLiberados.length; i++) {
						if(menusLiberados[i] == nomeItem) {
							menusLiberados[i] = null;
						} else {
							novaLista.push(menusLiberados[i]);
						}
					}
					menusLiberados = [];
					menusLiberados = null;
					menusLiberados = novaLista;
				}
		
				/**
				 * Verifica se um item está na lista de liberação.
				 */
				function isLiberado(nomeMenu) {
					for(var i = 0; i < menusLiberados.length; i++) {
						if(menusLiberados[i] == nomeMenu) {
							return true;
						}
					}
					return false;
				}

				//ações dos botões	
				$("#btAnexos").click(function() {
					var parametrosUploadValidos = ${parametrosUploadValidos};
					if (parametrosUploadValidos) {
						//a popup é aberta do lado do java.
						document.formUpload.fireEvent("valida");
						
						$('#POPUP_menuAnexos').dialog('open');
						uploadAnexos.refresh();
					} else {
						alert(i18n_message('citcorpore.comum.diretorioUploadEGedInvalidos'));
					}
				});

				$("#btOcorrencias").click(function() {
					document.formOcorrenciaSolicitacao.clear();
					document.formOcorrenciaSolicitacao.idSolicitacaoOcorrencia.value = document.form.idSolicitacaoServico.value; 
					document.formOcorrenciaSolicitacao.fireEvent('load');
					document.getElementById('divRelacaoOcorrencias').innerHTML = i18n_message("citcorpore.comum.aguardecarregando");
					document.formOcorrenciaSolicitacao.fireEvent('listOcorrenciasSituacao');
					$("#POPUP_menuOcorrencias").dialog("open");
					//posteriormente trocar pelo serviço carregado
					document.getElementById("pesqLockupLOOKUP_OCORRENCIA_SOLICITACAO_IDSOLICITACAOSERVICO").value = 1;
					document.getElementById("btnTodos").style.display = "none";
				});


				$("#btIncidentesRelacionados").click(function() {
					//popup aberta do lado java
					document.formIncidentesRelacionados.idSolicitacaoIncRel.value = document.form.idSolicitacaoServico.value; 
					inicializarTemporizadorRel1();
					document.formIncidentesRelacionados.fireEvent("restore");
				});

				$("#btRelacionarSolicitacao").click(function() {
					inicializarTemporizadorRel2();
					document.formIncidentesRelacionados.fireEvent("listarSolicitacoesServicoEmAndamento");
					$("#divSolicitacoesFilhas").dialog("open");
				});
	
				$("#btRelacionarSolicitacaoFechar").click(function() {
					$('#POPUP_menuIncidentesRelacionados').dialog('close');
				});

				$("#btBaseConhecimentoView").click(function() {
					//dialog tratado pelo PopupManager.js
					popupBarraFerramenta.titulo = "<i18n:message key='baseConhecimento.consultaABaseConhecimento' />";
					dimensionaPopupCadastroRapido("1300","700");
					popupBarraFerramenta.abrePopup('baseConhecimentoView', '');
				});
	
				$("#btnFecharTelaAnexos").click(function() {
					$('#POPUP_menuAnexos').dialog('close');
				});
				
				$('#nomeCategoriaOcorrencia').click(function() {
					$('#POPUP_PESQUISA_CATEGORIA_OCORRENCIA').dialog('open')
				});
				
				$('#nomeOrigemOcorrencia').click(function() {
					$('#POPUP_PESQUISA_ORIGEM_OCORRENCIA').dialog('open')
				});
			});
			
			function gravarAnexo() {
				document.form.idSolicitacaoServico.disabled = false;
				document.form.fireEvent("gravarAnexo");
			}
			
			function abrirPopupCadastroCategoriaOcorrencia() {
				dimensionaPopupCadastroRapido("900","500");
				popupCadastroCategoriaOcorrencia.abrePopupParms('categoriaOcorrencia', '');
			}	
			
			function LOOKUP_CATEGORIA_OCORRENCIA_select(id, desc) {
				$('#idCategoriaOcorrencia').val(id);				
				$('#nomeCategoriaOcorrencia').val(desc);
				$('.POPUP_PESQUISA_CATEGORIA_OCORRENCIA').dialog('close');
			}
			
			function LOOKUP_ORIGEM_OCORRENCIA_select(id, desc) {
				$('#idOrigemOcorrencia').val(id);				
				$('#nomeOrigemOcorrencia').val(desc);
				$('.POPUP_PESQUISA_ORIGEM_OCORRENCIA').dialog('close');
			}			
			
			function abrirPopupCadastroOrigemOcorrencia() {
				dimensionaPopupCadastroRapido("900","500");
				popupCadastroOrigemOcorrencia.abrePopupParms('origemOcorrencia', '');
			}

			function excluir() {				
				if (document.getElementById("id").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
						document.formPesquisaCategoriaOcorrencia.fireEvent("delete");
					}
				}
			}
		</script>		
	</head>	
	<body>
		<input type="hidden" id="idsolicitacaoservico" value="1" />	
		<div align="center" id="barraFerramentasIncidentes">
			<ul class="menu_horizontal">
				<li style="cursor: pointer;" class="li_menu tooltip_bottom" id="btAnexos" title=<i18n:message key="citcorpore.comum.anexardocumentoimagens" />>
					<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/paperclip.png" />
					<div class="name"><i18n:message key="citcorpore.comum.anexos" /></div>
				</li>
				<li style="cursor: pointer;" class="li_menu tooltip_bottom" id="btOcorrencias" title='<i18n:message key="gerenciaservico.visualizarregistarocorrencia" />'>
					<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/note_book.png" >
					<div class="name"><i18n:message key="citcorpore.comum.ocorrencia" /></div>
				</li>
				<li style="cursor: pointer;" class="li_menu tooltip_bottom" id="btIncidentesRelacionados" title='<i18n:message key="gerenciaservico.vincularincidentesrelacionados" />'>
					<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/strategy.png" >
					<div class="name"><i18n:message key="gerenciaservico.incidentesrelacionados" /></div>
				</li>
			<% if (id == null ) { %>
				<li style="cursor: pointer;" class="li_menu tooltip_bottom" id="btBaseConhecimentoView" title='<i18n:message key="gerenciaservico.acessarbaseconhecimentos" />'>
					<img  src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/user_comment.png" >
					<div class="name"><i18n:message key="baseConhecimento.baseConhecimento" /></div>
				</li>
			<% } %>
			</ul>
		</div>				
		<div class="POPUP_barraFerramentasIncidentes" id="POPUP_menuAnexos" style="display:none">		
			<form name="formUpload" method="post" enctype="multipart/form-data">
				<cit:uploadControl id="uploadAnexos"
					title="Anexos"
					style="height: 100px; width: 50%; border: 1px solid black;"
					form="document.formUpload" 
					action="/pages/upload/upload.load" 
					disabled="false" />								
				<font id="msgGravarDados" style="display:none" color="red"><i18n:message key="barraferramenta.validacao.solicitacao" /></font><br />				
				<button id="btnGravarTelaAnexos" name="btnGravarTelaAnexos" onclick="gravarAnexo();" type="button" style="display:none">
					<i18n:message key="citcorpore.comum.gravar" />
				</button>
				<button id="btnFecharTelaAnexos" name="btnFecharTelaAnexos" type="button">
					<i18n:message key="citcorpore.comum.fechar" />
				</button>
			</form>
		</div>	
		<div class="POPUP_barraFerramentasIncidentes" id="POPUP_menuOcorrencias">		
			<!-- Conteudo -->
			<!--	Não usar este CSS possuido:	main_container -->
			<div id="main_container" class="container_16 clearfix">
				<div class="flat_area grid_16">
					<h2><i18n:message key="citcorpore.comum.ocorrencia" /></h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="gerenciaservico.relacaoocorrenciaregistradas" /></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top" onclick="alert(document.getElementById('pesqLockupLOOKUP_OCORRENCIA_SOLICITACAO_idsolicitacaoservico').value);">
								<i18n:message key="gerenciaservico.cadastroocorrencia" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<form name="formOcorrenciaSolicitacao" method="post" 
							action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/ocorrenciaSolicitacao/ocorrenciaSolicitacao">
							<input type="hidden" id="idSolicitacaoOcorrencia" name="idSolicitacaoOcorrencia" />
							<input type="hidden" id="descricao" name="descricao" />
							<input type="hidden" id="idCategoriaOcorrencia" name="idCategoriaOcorrencia" />
							<input type="hidden" id="idOrigemOcorrencia" name="idOrigemOcorrencia" />

							<!-- Primeira aba -->
							<div id="tabs-1" class="block">
								<div id="divRelacaoOcorrencias" style="width: 100%; height: 100%; overflow: auto"></div>
							</div>
							<!-- Segunda aba -->
							<div id="tabs-2" class="block">
								<div class="section">
									<div class="columns clearfix">
										<input type="hidden" name="idOcorrencia" />
										<div class="col_50">
											<fieldset style="height: 70px;">
												<div>													
													<label class="campoObrigatorio" style="cursor: pointer; float: left; width: 100px">
														<i18n:message key="citcorpore.comum.categoria" />														
														<img id="btnCategoriaOcorrencia" src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/imagens/add.png" onclick="abrirPopupCadastroCategoriaOcorrencia();" />													    
													</label>															
													<div>
														<input id="nomeCategoriaOcorrencia" name="nomeCategoriaOcorrencia" type="text" readonly="readonly" maxlength="80" class="Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "citcorpore.comum.categoria") %>]" />
													</div>											
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio" style="cursor: pointer; float: left; width: 100px">
													<i18n:message key="citcorpore.comum.origem" />
													<img id="btnOrigemOcorrencia" src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/imagens/add.png" onclick="abrirPopupCadastroOrigemOcorrencia();" />													    
												</label>													
												<div>
													<input id="nomeOrigemOcorrencia" name="nomeOrigemOcorrencia" type="text" readonly="readonly" maxlength="80" class="Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "citcorpore.comum.origem") %>]" />
												</div>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix">
										<div class="col_20">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="ocorrenciaSolicitacao.tempoGasto" /></label>
												<div>
													<input type="text" name="tempoGasto" maxlength="4" size="4" class="Format[Numero] Valid[Required] Description[Tempo Gasto]" /> 
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="ocorrenciaSolicitacao.registradopor" /></label>
												<div>
													<input type="text" name="registradopor" id="registradopor" maxlength="30" size="30" readonly="readonly" 
														class="Valid[Required] Description[Registrado por]" /> 
												</div>
											</fieldset>
										</div>																		
									</div>								
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="citcorpore.comum.descricao" />
												</label>
												<div>
													<textarea cols="70" rows="5" name="descricao1" class="Valid[Required] Description[Descrição]" maxlength="200"></textarea>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.ocorrencia" /></label>
												<div>
													<textarea cols="70" rows="5" name="ocorrencia" class="Valid[Required] Description[Ocorrência]" maxlength="5000"></textarea>
												</div>
											</fieldset>
										</div>
									</div>
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="solicitacaoServico.informacaoContato" /></label>
												<div>
													<textarea cols="70" rows="2" name="informacoesContato" class="Valid[Required] Description[Informações de Contato]" maxlength="5000"></textarea>
												</div>
											</fieldset>
										</div>
									</div>
									<br /><br />								
									<button type="button" name="btnGravar" class="light" onclick="salvar();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/pencil.png">
										<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i18n:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type="button" name="btnLimpar" class="light" onclick="document.formOcorrenciaSolicitacao.clear();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/trashcan.png">
										<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<i18n:message key="citcorpore.comum.limpar" /></span>
									</button>
								</div>
							</div>
							<!-- ## FIM - AREA DA APLICACAO ## -->
						</form>
					</div>
				</div>
			</div>
		</div>	
		<div class="POPUP_barraFerramentasIncidentes" id="POPUP_menuIncidentesRelacionados">
			<div id="tabelaIncidentesRelacionados"></div>
			<div id="menuIncidentesRelacionados" class="col_50">
				<button type="button" name="btRelacionarSolicitacao" id="btRelacionarSolicitacao" class="light" onclick="">
					<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/pencil.png">
					<span>
						<i18n:message key="barraferramenta.relacionaroutroincidente" />
					</span>
				</button>
			</div>
			<div id="menuIncidentesRelacionados" class="col_50">
				<button type="button" name="btRelacionarSolicitacaoFechar" id="btRelacionarSolicitacaoFechar" class="light" onclick="">
					<span>
						<i18n:message key="citcorpore.comum.fechar" />
					</span>
				</button>
			</div>
			<div class="POPUP_barraFerramentasIncidentes" id="divSolicitacoesFilhas">				
				<form name="formIncidentesRelacionados" method="post" 
					action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/incidentesRelacionados/incidentesRelacionados">
					<input type="hidden" name="idSolicitacaoIncRel" value="" />
					<div id="divConteudoIncRel"></div>
				</form>
			</div>
		</div>
		<div class="POPUP_barraFerramentasIncidentes" id="POPUP_menuHistorico">
			<div id="divResultHistorico"></div>
		</div>	
		<div id="popupCadastroRapido">	
			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
		</div>
		<div id="POPUP_PESQUISA_CATEGORIA_OCORRENCIA" class="POPUP_PESQUISA_CATEGORIA_OCORRENCIA" title="<i18n:message key="citcorpore.comum.pesquisaCategoriaOcorrencia" />" >
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="left">
							<label style="cursor: pointer;">
								<i18n:message key="citcorpore.comum.pesquisa" />													
							</label>
							<form id="formPesquisaCategoriaOcorrencia" name="formPesquisaCategoriaOcorrencia" method="post" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/categoriaOcorrencia/categoriaOcorrencia">
								<cit:findField id="LOOKUP_CATEGORIA_OCORRENCIA" 
									formName="formPesquisaCategoriaOcorrencia" 
									lockupName="LOOKUP_CATEGORIA_OCORRENCIA" 
									top="0" 
									left="0" 
									len="550" 
									heigth="400" 
									javascriptCode="true" 
									htmlCode="true" />									
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_PESQUISA_ORIGEM_OCORRENCIA" class="POPUP_PESQUISA_ORIGEM_OCORRENCIA" title="<i18n:message key="citcorpore.comum.pesquisaOrigemOcorrencia" />" >
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section">
						<div align="right">
							<label style="cursor: pointer;">
								<i18n:message key="citcorpore.comum.pesquisa" />													
							</label>
							<form id="formPesquisaOrigemOcorrencia" name="formPesquisaOrigemOcorrencia" method="post" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/origemOcorrencia/origemOcorrencia">
								<cit:findField id="LOOKUP_ORIGEM_OCORRENCIA" 
									formName="formPesquisaOrigemOcorrencia" 
									lockupName="LOOKUP_ORIGEM_OCORRENCIA" 
									top="0" 
									left="0" 
									len="550" 
									heigth="400" 
									javascriptCode="true" 
									htmlCode="true" />									
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
