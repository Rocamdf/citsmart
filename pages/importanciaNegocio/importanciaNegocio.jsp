<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
%>

<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
<script>
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
	}
	function LOOKUP_IMPORTANCIANEGOCIO_select(id,desc){
		document.form.restore({idImportanciaNegocio:id});
	}
	function excluir(){
		if(document.getElementById("idImportanciaNegocio").value != ""){
			if(confirm(i18n_message("citcorpore.comum.deleta"))){
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
				<h2><i18n:message key="importanciaNegocio.importanciaNegocio"/></h2>						
		</div>
		
  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><i18n:message key="importanciaNegocio.cadastroImportanciaDeNegocio"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><i18n:message key="importanciaNegocio.pesquisaImportanciaDeNegocio"/></a>
				</li>
			</ul>				
	<a href="#" class="toggle">&nbsp;</a>
	 <div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/importanciaNegocio/importanciaNegocio'>
								<div class="columns clearfix">
									<input type='hidden' name='idEmpresa'/> 
									<input type='hidden' name='idImportanciaNegocio' /> 
									<input type='hidden' name='situacao' value="A" />

									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message
													key="importanciaNegocio.nomeImportanciaDeNegocio" />
											</label>
											<div>
												<input type='text' name="nomeImportanciaNegocio"
													maxlength="80"
													class="Valid[Required] Description[importanciaNegocio.nomeImportanciaDeNegocio]" />
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
									<span><i18n:message
											key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message
											key="citcorpore.comum.limpar" />
									</span>
								</button>
								<button type='button' name='btnUpDate' class="light"
									onclick='excluir();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message
											key="citcorpore.comum.excluir" />
									</span>
								</button>
							</form>

						</div>
					</div>
					<div id="tabs-2" class="block" >
							<div  class="section">
									<i18n:message key="citcorpore.comum.pesquisa"/>
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_IMPORTANCIANEGOCIO' id='LOOKUP_IMPORTANCIANEGOCIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true'   />
									</form>
						    </div>
	                </div>								
							<!-- ## FIM - AREA DA APLICACAO ## -->							
	 </div>
  </div>				
 </div>
<!-- Fim da Pagina de Conteudo -->
</div>
		<%@include file="/include/footer.jsp"%>
</body>
</html>
