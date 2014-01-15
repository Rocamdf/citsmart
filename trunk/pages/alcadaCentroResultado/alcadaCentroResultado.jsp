<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.AlcadaCentroResultadoDTO" %>

<!doctype html public "">
<html>
	<head>
		<%			
    		response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>		
		
		<%@ include file="/include/security/security.jsp" %>		
		
		<title>
			<i18n:message key="citcorpore.comum.title" />
		</title>			
		
		<%@ include file="/include/noCache/noCache.jsp" %>
		<%@ include file="/include/header.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
				
		<script type="text/javascript" src="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/js/PopupManager.js"></script>		
		
		<script type="text/javascript">			
			
	        $(function() {
	        	// As duas popup a seguir são variáveis globais pois não foram
	        	// declaradas com o uso da keyword var.
	        	popupCadastroCentroResultado = new PopupManager(1200, 700, "<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/");
		        popupCadastroAlcada = new PopupManager(1200, 700, "<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/"); 
	        	
	        	document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				};
						
// 				$('#LOOKUP_ALCADACENTRORESULTADO').dialog({
// 					autoOpen: false,
// 					width: 705,
// 					height: 500,
// 					modal: true
// 				});
				
				$('#POPUP_PESQUISA_COLABORADOR').dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});
				
				$('#POPUP_PESQUISA_CENTRORESULTADO').dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});
				
				$('#POPUP_PESQUISA_ALCADA').dialog({
					autoOpen: false,
					width: 705,
					height: 500,
					modal: true
				});
	        });
	        
	        // FUNÇÕES PARA ABERTURA DAS JANELAS DE PESQUISA
	        
	        function pesquisarColaborador() {
				$('#POPUP_PESQUISA_COLABORADOR').dialog('open');
			}			
			
			function pesquisarCentroResultado() {
				$('#POPUP_PESQUISA_CENTRORESULTADO').dialog('open');
			}
			
			function pesquisarAlcada() {
				$('#POPUP_PESQUISA_ALCADA').dialog('open');
			}
			
			// CONFIGURANDO O COMPORTAMENTO DAS JANELAS DE PESQUISA APÓS A SELEÇÃO DE UM DOS RESULTADOS
			
			function LOOKUP_PESQUISA_COLABORADOR_select(id, desc) {
				// Configurar o atributo idEmpregado do DTO (AlcadaCentroResultado) associado a esta página jsp.
				// com o valor do id do colaborador selecionada na janela de pesquisa de colaboradores.
				$('#idEmpregado').val(id);				
				// Configura uma caixa de texto com o nome do colaborador.
				$('#nomeEmpregado').val(desc);
				// Fecha a janela de pesquisa
				$('#POPUP_PESQUISA_COLABORADOR').dialog('close');				
			}
			
			function LOOKUP_PESQUISA_CENTRORESULTADO_select(id, desc) {				
				$('#idCentroResultado').val(id);
				
				desc = desc.split('-');
				desc = desc[0].replace(' ', '');
				
				$('#nomeCentroResultado').val(desc);				
				$('#POPUP_PESQUISA_CENTRORESULTADO').dialog('close');				
			}
			
			function LOOKUP_PESQUISA_ALCADA_select(id, desc) {				
				$('#idAlcada').val(id);				
				$('#nomeAlcada').val(desc);				
				$('#POPUP_PESQUISA_ALCADA').dialog('close');
			}			
			
			// LOOKUP_ALCADACENTRORESULTADO
			function LOOKUP_ALCADACENTRORESULTADO_select(id, desc) {				
				document.form.restore({
					idAlcadaCentroResultado: id
				});
			}
			
			// FUNÇÕES PARA ABERTURA DA PÁGINA DE CADASTRO DE OUTRAS ENTIDADES
			
			function abrirPopupCadastroCentroResultado() {				
				popupCadastroCentroResultado.abrePopupParms("centroResultado", "");
			}
			
			function abrirPopupCadastroAlcada() {
				popupCadastroAlcada.abrePopupParms("alcada", "");
			}
			
			// FUNÇÃO DE EXCLUSÃO DE ALÇADA CENTRO RESULTADO

			function excluir() {
				var idAlcadaCentroResultado = $('#idAlcadaCentroResultado').val();
				
				if (idAlcadaCentroResultado != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
						document.form.fireEvent("delete");				
					}					
				} else {
					alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro") );					
					return false;	
				}
			}
		</script>
		
	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da página
		if (iframe != null) {
	%>
		<style>
			div#main_container {
				margin: 10px 10px 10px 10px;
			}
		</style>
	<%
		}
	%>
	</head>
	<body>
		<div id="wrapper">
		<% 
			if (iframe == null) { 
		%>
			<%@ include file="/include/menu_vertical.jsp" %>
		<%
			}
		%>
			<!-- Conteudo -->
			<div id="main_container" class="main_container container_16 clearfix">
			<%
				if (iframe == null) {
			%>
				<%@ include file="/include/menu_horizontal.jsp" %>
			<%
				}
			%>
				<div class="flat_area grid_16">
					<h2>
						<i18n:message key="alcadaCentroResultado" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<i18n:message key="alcadaCentroResultado.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<i18n:message key="alcadaCentroResultado.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form name="form" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/alcadaCentroResultado/alcadaCentroResultado">
									<div class="columns clearfix">
										<input type="hidden" id="idAlcadaCentroResultado" name="idAlcadaCentroResultado" /> 
										<input type="hidden" id="idCentroResultado" name="idCentroResultado" />
										<input type="hidden" id="idEmpregado" name="idEmpregado" />
										<input type="hidden" id="idAlcada" name="idAlcada" />										
										
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="colaborador.colaborador" />													
												</label>
												<div>
													<div>
														<input type="text" id="nomeEmpregado" name="nomeEmpregado" readonly="readonly" style="width: 90% !important;" 
															class="Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "alcadaCentroResultado.nomeEmpregado") %>]" 
															maxlength="70" size="70" onclick="pesquisarColaborador();" />
														<img style="vertical-align: middle;" 
															src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/magnifying_glass.png"
															onclick="pesquisarColaborador();" />
													</div>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="centroResultado" />
													<img style="vertical-align: middle;" src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/imagens/add.png" 
														onclick="abrirPopupCadastroCentroResultado();" />
												</label>
												<div>
													<div>
														<input type="text" id="nomeCentroResultado" name="nomeCentroResultado" readonly="readonly" 
															class="Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "alcadaCentroResultado.nomeCentroResultado") %>]" 
															style="width: 90% !important;" maxlength="70" size="70" onclick="pesquisarCentroResultado();" />
														<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/magnifying_glass.png" 
															style="vertical-align: middle;" onclick="pesquisarCentroResultado();" />
													</div>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="alcada" />
													<img style="vertical-align: middle;" src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/imagens/add.png" 
														onclick="abrirPopupCadastroAlcada();" />
												</label>
												<div>
													<div>
														<input type="text" id="nomeAlcada" name="nomeAlcada" onclick="pesquisarAlcada();" readonly="readonly" 
															class="Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "alcadaCentroResultado.nomeAlcada") %>]" 
															style="width: 90% !important;" maxlength="70" size="70" />
														<img onclick="pesquisarAlcada();" style="vertical-align: middle;" 
															src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/magnifying_glass.png" />
													</div>
												</div>
											</fieldset>
										</div>
										<div class="col_33">
											<fieldset>
												<label class="campoObrigatorio" style="height: 30px;">
													<i18n:message key="centroResultado.situacao" />
												</label>
												<div>
													<input type="radio" id="situacaoAtivo" name="situacao" value="A" checked="checked" /><i18n:message key="citcorpore.comum.ativo" />
													<input type="radio" id="situacaoInativo" name="situacao" value="I" /><i18n:message key="citcorpore.comum.inativo" />
												</div>
											</fieldset>
										</div>		
									</div>
									<br />
									<br />
									<button type="button" name="btnGravar" class="light" onclick="document.form.save();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/pencil.png" />
										<span>
											<i18n:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name="btnLimpar" class="light" onclick='document.form.clear();document.form.fireEvent("load");'>
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/clear.png" />
										<span>
											<i18n:message key="citcorpore.comum.limpar" />
										</span>
									</button>
									<button type="button" name="btnExcluir" class="light" onclick="excluir();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/trashcan.png" />
										<span>
											<i18n:message key="citcorpore.comum.excluir" />
										</span>
									</button>									
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<i18n:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_ALCADACENTRORESULTADO"
										id="LOOKUP_ALCADACENTRORESULTADO" 
										top="0" 
										left="0" 
										len="550"
										heigth="400" 
										javascriptCode="true" 
										htmlCode="true" />
								</form>
							</div>
						</div>
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- Fim da Pagina de Conteudo -->
		</div>		
		<!-- JANELAS DE PESQUISA -->
		<div id="POPUP_PESQUISA_COLABORADOR" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name="formPesquisaColaborador">
							<cit:findField formName="formPesquisaColaborador" 
							lockupName="LOOKUP_EMPREGADO" 
							id="LOOKUP_PESQUISA_COLABORADOR" 
							top="0" left="0" len="550" heigth="400" 
							javascriptCode="true" 
							htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_PESQUISA_CENTRORESULTADO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name="formPesquisaCentroResultado">
							<cit:findField formName="formPesquisaCentroResultado" 
							lockupName="LOOKUP_CENTRORESULTADO" 
							id="LOOKUP_PESQUISA_CENTRORESULTADO" 
							top="0" left="0" len="550" heigth="400" 
							javascriptCode="true" 
							htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_PESQUISA_ALCADA" title="<i18n:message key="citcorpore.comum.pesquisa" />">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px;">
						<form name="formPesquisaAlcada">
							<cit:findField formName="formPesquisaAlcada" 
								lockupName="LOOKUP_ALCADA" 
								id="LOOKUP_PESQUISA_ALCADA" 
								top="0" left="0" len="550" heigth="400" 
								javascriptCode="true" 
								htmlCode="true" />
						</form>
					</div>
				</div>
			</div>
		</div>
		<div id="popupCadastroRapido">	
			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>