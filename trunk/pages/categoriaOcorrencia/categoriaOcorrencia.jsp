<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ taglib uri="/tags/menuPadrao" prefix="menu" %>

<html>
	<head>	
		<%
			response.setHeader("Cache-Control", "no-cache"); 
	    	response.setHeader("Pragma", "no-cache");
	    	response.setDateHeader("Expires", -1);
	    	String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		<script type="text/javascript">
		var objTab = null;
		addEvent(window, "load", load, false);
		
		function load() {
			document.formCategoriaOcorrencia.afterRestore = function() {
				$('.tabsbar li:eq(0) a').tab('show')
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
	</head>
	<body>
		<div class="container-fluid fixed ">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<%if (iframe == null) {%>
			<div class="navbar main hidden-print">
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
			</div>
			<%}%>
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">
				<%if (iframe == null) {%>
					<h3><i18n:message key="citcorpore.comum.categoriaOcorrencia" /></h3>
				<%}%>
				<div class="box-generic">
					
						<!-- Tabs Heading -->
						<div class="tabsbar">
							<ul>
								<li class="active"><a href="#tab1" data-toggle="tab"><i18n:message key="citcorpore.comum.cadastroCategoriaOcorrencia" /></a></li>
								<li  class=""><a href="#tab2" data-toggle="tab"><i18n:message key="citcorpore.comum.pesquisaCategoriaOcorrencia" /></a></li>
							</ul>
						</div>
						<!-- // Tabs Heading END -->
						<form name="formCategoriaOcorrencia" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR %><%=request.getContextPath() %>/pages/categoriaOcorrencia/categoriaOcorrencia">
						<div class="tab-content">
								
							<!-- Tab content -->
							<div class="tab-pane active" id="tab1">
								
									<input type="hidden" id="idCategoriaOcorrencia" name="idCategoriaOcorrencia" /> 
									<input type="hidden" id="dataInicio" name="dataInicio" />
									<input type="hidden" id="dataFim" name="dataFim" />
									<div class="row-fluid">
										<div class="span5">
											<label  class="strong"><i18n:message key="citcorpore.comum.nome"/></label>
											  	<input placeholder="" class="span10 Valid[Required] Description[Nome]" id="nome" required="required"  type="text" name="nome" maxlength="255" />
										</div>
									</div>
									<div style="margin: 0;" class="form-actions">
										<button class="btn btn-icon btn-primary glyphicons circle_ok" type="button" onclick='document.formCategoriaOcorrencia.save();'><i></i><i18n:message key="citcorpore.comum.gravar" /></button>
										<button class="btn btn-icon btn-default glyphicons cleaning" type="button" onclick='document.formCategoriaOcorrencia.clear();'><i></i><i18n:message key="citcorpore.comum.limpar" /></button>
										<button class="btn btn-icon btn-default glyphicons remove_2" type="button" onclick='excluir();'><i></i><i18n:message key="citcorpore.comum.excluir" /></button>
									</div>
								
							
							</div>
							<!-- // Tab content END -->
							
							<!-- Tab content -->
							<div class="tab-pane" id="tab2">
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
							<!-- // Tab content END -->
				
							
						</div>
							</form>
				</div>
			
				</div>
				<!--  Fim conteudo-->
				
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				
			</div>
		</div>
	</body>
</html>