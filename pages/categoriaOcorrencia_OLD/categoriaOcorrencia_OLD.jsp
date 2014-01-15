<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.CategoriaOcorrenciaDTO" %>

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
		<%@ include file="/include/internacionalizacao/internacionalizacao.jsp" %>
    	<%@ include file="/include/security/security.jsp" %>		
		<title>
			<i18n:message key="citcorpore.comum.title" />
		</title>		
		<%@ include file="/include/noCache/noCache.jsp" %>
		<%@ include file="/include/menu/menuConfig.jsp" %>
		<%@ include file="/include/header.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>		
		<script type="text/javascript">
			var objTab = null;
			addEvent(window, "load", load, false);
			
			function load() {
				document.formCategoriaOcorrencia.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}
			
			function excluir() {
				if (document.getElementById("idCategoriaOcorrencia").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
						document.formCategoriaOcorrencia.fireEvent("delete");
					}
				}
			}
			
			function LOOKUP_CATEGORIA_OCORRENCIA_select(id, desc) {
				document.formCategoriaOcorrencia.restore({
					idCategoriaOcorrencia: id
				});
			}
		</script>
	<%
		// Se for chamado por iframe deixa apenas a parte de cadastro da p�gina
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
						<i18n:message key="citcorpore.comum.categoriaOcorrencia" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<i18n:message key="citcorpore.comum.cadastroCategoriaOcorrencia" />
							</a>
						</li>
						<li>
							<a href="#tabs-2">
								<i18n:message key="citcorpore.comum.pesquisaCategoriaOcorrencia" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<form name="formCategoriaOcorrencia" 
							action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/categoriaOcorrencia/categoriaOcorrencia">
							<input type="hidden" id="idCategoriaOcorrencia" name="idCategoriaOcorrencia" /> 
							<input type="hidden" id="dataInicio" name="dataInicio" />
							<input type="hidden" id="dataFim" name="dataFim" />
							<!-- Cadastro de categoria de ocorr�ncia -->
							<div id="tabs-1" class="block">						
								<div class="section">																					
									<div class="columns clearfix">
										<div class="col_66">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="citcorpore.comum.nome" />
												</label>												
												<div>
													<input type="text" name="nome" maxlength="20" class="Valid[Required] Description[<%= UtilI18N.internacionaliza(request, "citcorpore.comum.nome") %>]" />
												</div>
											</fieldset>
										</div>
									</div>
									<br />
									<br />
									<button type="button" name="btnGravar" class="light" onclick="document.formCategoriaOcorrencia.save();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/pencil.png">
										<span>
											<i18n:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name="btnLimpar" class="light" onclick="document.formCategoriaOcorrencia.clear();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/clear.png">
										<span>
											<i18n:message key="citcorpore.comum.limpar" />
										</span>
									</button>
									<button type="button" name="btnExcluir" id="btnExcluir" class="light" onclick="excluir();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png" />
										<span><i18n:message key="citcorpore.comum.excluir" /></span>
									</button>				
								</div>
							</div>
							<!-- Pesquisa de categoria de ocorr�ncia -->
							<div id="tabs-2" class="block">
								<div class="section">
									<label style="cursor: pointer;">
										<i18n:message key="citcorpore.comum.pesquisa" />													
									</label>																		
									<cit:findField id="LOOKUP_CATEGORIA_OCORRENCIA" 
										formName="formCategoriaOcorrencia" 
										lockupName="LOOKUP_CATEGORIA_OCORRENCIA" 
										top="0" 
										left="0" 
										len="550" 
										heigth="400" 
										javascriptCode="true" 
										htmlCode="true" />																
								</div>
							</div>
						</div>
					</form>
				</div> <!-- FIM - �REA DA APLICACAO -->
			</div>						
		</div> <!-- FIM DA �REA DE CONTE�DO -->
		<%@ include file="/include/footer.jsp" %>
	</body>
</html>