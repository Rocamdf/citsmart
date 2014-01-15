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
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}

		function LOOKUP_ORIGEMATENDIMENTO_select(id, desc) {
			document.form.restore({
				idOrigem : id
			});
		}
		
		function excluir() {
			if (document.getElementById("idOrigem").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					chamaFuncaoPreencherCombo();
					document.form.fireEvent("delete");
				}
			}
		}
		
		var objTab = null;
		addEvent(window, "load", load, false);
		
		function load() {
			document.form.afterRestore = function() {
				$('.tabsbar li:eq(0) a').tab('show')
			}
		}
		
		function excluir() {
			if (document.getElementById("idOrigem").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
					document.form.fireEvent("delete");
				}
			}
		}
		function LOOKUP_ORIGEMATENDIMENTO_select(id, desc) {
			document.form.restore({
				idOrigem: id
			});
		}
		
		function chamaFuncaoPreencherCombo() {
			parent.preencherComboOrigem();
		}
		
		function savarDados() {
			document.form.save();
		}

		</script>
		
		 <style type="text/css">
		 	.campoObrigatorio:after {
				color: #FF0000;
				content: "*";
			}
		 	
		 </style>
		 
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
				<h3><i18n:message key="origemAtendimento.origem" /></h3>
				<div class="box-generic">
					
						<!-- Tabs Heading -->
						<div class="tabsbar">
							<ul>
								<li class="active"><a href="#tab1" data-toggle="tab"><i18n:message key="origemAtendimento.cadastroOrigem" /></a></li>
								<li  class=""><a href="#tab2" data-toggle="tab"><i18n:message key="origemAtendimento.pesquisaOrigem" /></a></li>
							</ul>
						</div>
						<!-- // Tabs Heading END -->
						<form name="form" id="form" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR %><%=request.getContextPath() %>/pages/origemAtendimento/origemAtendimento">
						<div class="tab-content">
								
							<!-- Tab content -->
							<div class="tab-pane active" id="tab1">
								
									<input type='hidden' name='idOrigem' id='idOrigem' /> 
									<input type='hidden' name='dataInicio' id="dataInicio" />
									<input type='hidden' name='dataFim' id="dataFim" />
									<div class="row-fluid">
										<div class="span5">
											<label  class="strong campoObrigatorio"><i18n:message key="citcorpore.comum.nome"/></label>
											  	<input placeholder="" class="span10" id="descricao" required  type="text" name="descricao">
										</div>
									</div>
									<div style="margin: 0;" class="form-actions">
										<button class="btn  btn-primary " type="button" onclick='savarDados();'><i></i><i18n:message key="citcorpore.comum.gravar" /></button>
										<button class="btn  btn-primary " type="button" onclick='document.form.clear();;'><i></i><i18n:message key="citcorpore.comum.limpar" /></button>
										<button class="btn  btn-primary " type="button" onclick='excluir();;'><i></i><i18n:message key="citcorpore.comum.excluir" /></button>
									</div>
								
							
							</div>
							<!-- // Tab content END -->
							
							<!-- Tab content -->
							<div class="tab-pane" id="tab2">
								<cit:findField id="LOOKUP_ORIGEMATENDIMENTO" 
										formName="form" 
										lockupName="LOOKUP_ORIGEMATENDIMENTO" 
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