<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
		%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
    	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>		
		<script type="text/javascript">
		
			var objTab = null;
			
			addEvent(window, "load", load, false);
	
			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}
		
			function LOOKUP_MODELOEMAIL_select(id, desc) {
				document.form.restore( {
					idModeloEmail:id
				});
			}
			
			var oFCKeditor = new FCKeditor( 'texto' ) ;
	        function onInitQuestionario(){
		        oFCKeditor.BasePath = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
		        //oFCKeditor.Config['ToolbarStartExpanded'] = false ;
		
		        oFCKeditor.ToolbarSet   = 'Default' ;
		        oFCKeditor.Width = '100%' ;
	            oFCKeditor.Height = '350' ;
		        oFCKeditor.ReplaceTextarea() ;      
	        }
	        HTMLUtils.addEvent(window, "load", onInitQuestionario, false);
			
			function setDataEditor(){
				var oEditor = FCKeditorAPI.GetInstance( 'texto' ) ;
			    oEditor.SetData(document.form.texto.value);
			}
			
			function limpar(){
				document.form.clear();
		        var oEditor = FCKeditorAPI.GetInstance( 'texto' ) ;
		        oEditor.SetData('');		
			}
			
			function salvar(){
				var oEditor = FCKeditorAPI.GetInstance( 'texto' ) ;
				document.form.texto.value = oEditor.GetXHTML();
				document.form.save();
			}
			
		</script>
		
	</head>
	<body>	
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="modeloemail.modeloemail"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="modeloemail.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="modeloemail.pesquisa"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">		
								<form name="form" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/modeloEmail/modeloEmail'>
									<div class="columns clearfix">
										<input type='hidden' name='idModeloEmail'/>
										
										<div class="columns clearfix">
											<div class="col_1000">		
												<div class="col_50">		
												<fieldset>
													<label class="campoObrigatorio" ><i18n:message key="baseConhecimento.titulo"/></label>
														<div>
														  	<input type='text' name="titulo" maxlength="60" size="65" class="Valid[Required] Description[baseConhecimento.titulo]" />
														</div>
												</fieldset>
												</div>
												<div class="col_50">
												<fieldset style="height: 61px; !important">
													<label class="campoObrigatorio"><i18n:message key="solicitacaoServico.situacao"/></label>
														<div>
														  	<input id="situacao" value="A" checked="checked" class="Valid[Required] Description[solicitacaoServico.situacao]" type="radio" name="situacao" ><i18n:message key="citcorpore.comum.ativo"/>
				                                    		<input id="situacao" value="I" class="Valid[Required] Description[solicitacaoServico.situacao]" type="radio" name="situacao" ><i18n:message key="citcorpore.comum.inativo"/>
														</div>
												</fieldset>
												</div>
											</div>
										</div>
										
										<div class="columns clearfix">
											<div class="col_50">				
												<fieldset>
													<label><i18n:message key="modeloemail.texto"/></label>
														<div>
														  	<textarea id="texto" name="texto" rows="3" cols="80" maxlength="2000" style="display: block;"></textarea>
														</div>
												</fieldset>
											</div>
											<div class="col_50">		
												<fieldset>
													<label class="campoObrigatorio" ><i18n:message key="citSmart.comum.identificador"/></label>
														<div>
														  	<input type='text' name="identificador" maxlength="20" size="20" class="Valid[Required] Description[citSmart.comum.identificador]" />
														</div>
												</fieldset>
											</div>
										</div>
									</div>
								  	
									<button type='button' name='btnGravar' class="light"  onclick='salvar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>							
								</form>
							</div>		
						</div>
						
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_MODELOEMAIL' id='LOOKUP_MODELOEMAIL'  top='0' left='0' len='550' heigth='400' 
													javascriptCode='true' htmlCode='true' />
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