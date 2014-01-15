<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!doctype html public "âœ°">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", -1);
%>
<%@include file="/include/security/security.jsp" %>
<title>CIT Corpore</title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<script>
		var objTab = null;
		
		addEvent(window, "load", load, false);
		function load(){
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
			}
		}
		
		function LOOKUP_SITUACAOSERVICO_select(id,desc){
			document.form.restore({idSituacaoServico:id});
		}
		
		function deleteObj() {
			
			var idSituacaoServico = document.getElementById("idSituacaoServico");
			if (idSituacaoServico.value != '') {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");		
				}
			}
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
				<h2><i18n:message key="situacao.situacao"/></h2>
			</div>

			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="situacao.cadastro"/></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="situacao.pesquisa"/></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/situacaoServico/situacaoServico'>
								<div class="columns clearfix">
									<input type='hidden' name='idSituacaoServico' />
									<input type='hidden' name='dataInicio' />
									<input type='hidden' name='dataFim' />
									<input type='hidden' name='idEmpresa' />
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="situacao.nome"/></label>
											<div>
												<input type='text' name="nomeSituacaoServico" maxlength="80"
													class="Valid[Required] Description[Nome da Situação do Serviço]" />
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
									<span><i18n:message key="citcorpore.comum.gravar"/></span>
								</button>
								<button type='button' name='btnLimpar' class="light"
									onclick='document.form.clear();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar"/></span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='deleteObj();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir"/></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_SITUACAOSERVICO' id='LOOKUP_SITUACAOSERVICO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>	
				</div>
			</div>
		</div>
	</div>
	<%@include file="/include/footer.jsp"%>
</body>
</html>