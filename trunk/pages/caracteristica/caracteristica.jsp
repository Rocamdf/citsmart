<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

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
		
		<%
		    //se for chamado por iframe deixa apenas a parte de cadastro da página
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

		<script type="text/javascript">
			var objTab = null;
	
			addEvent(window, "load", load, false);
	
			function load(){
				document.form.afterRestore = function () {
					$('.tabs').tabs('select', 0);
				}
			}
	
			function excluir(){
				if (document.getElementById("idCaracteristica").value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta"))) {
						document.form.fireEvent("delete");
					}
				}
				
			}
	
			function LOOKUP_CARACTERISTICA_select(id,desc){
				document.form.restore({idCaracteristica:id});
			}

			function bloquearTag(valor){
				document.getElementById('tag').readOnly  = valor;
			}
			
			function limpar() {
				document.form.clear();
				bloquearTag(false);
			}
		</script>
	</head>
	<body>	
		<div id="wrapper">
			<%
			    if (iframe == null) {
			%>
			<%@include file="/include/menu_vertical.jsp"%>
			<%
			    }
			%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%
				    if (iframe == null) {
				%>
				<%@include file="/include/menu_horizontal.jsp"%>
				<%
				    }
				%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="citcorpore.comum.caracteristica"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="caracteristica.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="caracteristica.pesquisa"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">											
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/caracteristica/caracteristica'>
									<div class="columns clearfix">
										<input type='hidden' id="idCaracteristica" name='idCaracteristica'/>
										<input type='hidden' name='idEmpresa'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' name='tipo'/>
										
										<div class="columns clearfix">
											<div class="col_40">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.caracteristica"/></label>
														<div>
														  	<input type='text' name="nome" maxlength="70" size="70" class="Valid[Required] Description[citcorpore.comum.caracteristica]" />
														</div>
												</fieldset>
											</div>
											<div class="col_20">
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.tag"/></label>
														<div>
														  	<input type='text' name="tag" maxlength="15" size="15" class="Valid[Required] Description[Tag]" />
														</div>
												</fieldset>
											</div>
											<div class="col_40">
												<fieldset>
													<label><i18n:message key="citcorpore.comum.descricao"/></label>
														<div>
														  	<input type='text' name="descricao" maxlength="70" size="40"/>
														</div>
												</fieldset>
											</div>
										</div>
										<br>
									</div>	
									<br><br>
									<button type='button' name='btnGravar' class="light"  onclick='save();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>	
									<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir"/></span>
									</button>					         
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_CARACTERISTICA' id='LOOKUP_CARACTERISTICA' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
