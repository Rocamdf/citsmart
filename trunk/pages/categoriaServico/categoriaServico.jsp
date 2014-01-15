<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/security/security.jsp"%>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<title><i18n:message key="citcorpore.comum.title" /></title>
	<script type="text/javascript">
		var objTab = null;
	
		addEvent(window, "load", load, false);
		function load() {
			document.form.afterRestore = function() {
				$('.tabs').tabs('select', 0);
			}
		}
	
		function LOOKUP_CATEGORIASERVICO_select(id, desc) {
			document.form.restore({
				idcategoriaServico : id
			});
		}
		
		
		function LOOKUP_CATEGORIASERVICO_SUPERIOR_select(idTipo, desc) {
			document.form.idCategoriaServicoPai.value = idTipo;
			
		    var valor = desc.split('-');
			var nomeConcatenado = "";
			for(var i = 0 ; i < valor.length; i++){
				if(i == 0){
					document.form.nomeCategoriaServicoPai.value = valor[i];
				}
			}
			document.form.fireEvent("verificaHierarquia");
			fecharPopup();
			/* document.form.fireEvent("restoreTipoItemConfiguracao"); */
		}
		
		function atualizaData() {
			var idCategoriaServico = document.getElementById("idCategoriaServico");
	
			if (idCategoriaServico != null && idCategoriaServico.value == 0) {
				alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro"));
				return false;
			}
			if (confirm(i18n_message("citcorpore.comum.deleta")))
				document.form.fireEvent("atualizaData");
		}
		
		$(function() {
			$("#POPUP_CATEGORIASERVICO_SUPERIOR").dialog( {
				autoOpen : false,
				width : 705,
				height : 500,
				modal : true
			});
		});
		
		function consultarCategoriaServicoSuperior(){
			$("#POPUP_CATEGORIASERVICO_SUPERIOR").dialog("open");
		}
	
		function fecharPopup(){
			$("#POPUP_CATEGORIASERVICO_SUPERIOR").dialog("close");
		}
			
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
					<i18n:message key="categoriaServico.categoriaServico" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message	key="categoriaServico.cadastroCategoriaServico" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="categoriaServico.pesquisaCategoriaServico" />	</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/categoriaServico/categoriaServico'>
								<div class="columns clearfix">
									<input type='hidden' name='idCategoriaServico' /> 
									<input type='hidden' name='idEmpresa' id="idEmpresa" /> 
										<input type='hidden' name='dataInicio'  id="dataInicio"/> 
										<input type='hidden' name='dataFim' />
										<input type='hidden' name='idCategoriaServicoPai' /> <input
										type='hidden' name='idEmpresa' /> 
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome" /></label>
											<div>
												<input type='text' name="nomeCategoriaServico" maxlength="100" class="Valid[Required] Description[citcorpore.comum.nome]" />
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset style="height: 61px">
											<label><i18n:message key="categoriaServico.categoriaServicoPai" /></label>
											<div>
												<!-- <select name="idCategoriaServicoPai"></select> -->
											<div>
												<input onclick="consultarCategoriaServicoSuperior()" readonly="readonly" style="width: 90% !important;" type='text' name="nomeCategoriaServicoPai" maxlength="70" size="70"  />
												<img onclick="consultarCategoriaServicoSuperior()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											</div>
												
												
											</div>
										</fieldset>
									</div>
									<div class="col_66">
										<fieldset>
											<label ><i18n:message key="categoriaServico.hierarquia" /></label>
											<div>
												<input type='text' readonly="readonly" name="nomeCategoriaServicoConcatenado" maxlength="100" />
											</div>
										</fieldset>
									</div>
									
								</div>

								<br>
								<br>
								<button type='button' name='btnGravar' class="light"
									onclick='document.form.save();'>
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
									onclick='atualizaData();'>
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
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_CATEGORIASERVICO'
									id='LOOKUP_CATEGORIASERVICO' top='0' left='0' len='550'
									heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<div id="POPUP_CATEGORIASERVICO_SUPERIOR" title="<i18n:message key="citcorpore.comum.pesquisa" />">
		<div class="box grid_16 tabs" style="width: auto !important">
			<div class="toggle_container">
				<div id="tabs-2" class="block">
					<div class="section" style="padding: 33px; width: 100% !important;">
						<form name='formCategoriaServicoSuperior'>
							<cit:findField formName='formCategoriaServicoSuperior' 
							lockupName='LOOKUP_CATEGORIASERVICO_SUPERIOR' 
							id='LOOKUP_CATEGORIASERVICO_SUPERIOR' top='0' left='0' len='550' heigth='400' 
							javascriptCode='true' 
							htmlCode='true' />
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>
