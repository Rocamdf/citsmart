<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%String retorno = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/index/index.load";%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	    <link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/layout-default-latest.css"/>
   		<link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.all.css"/>
	
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
		
		
		
		<style type="text/css">
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
			
			#comentario {
				display: block;
				box-shadow: 0 0 2px 0 #555555 inset;
				-moz-box-sizing: border-box;
				background: none repeat scroll 0 0 rgba(0, 0, 0, 0.05);
				border: 0 none;
				box-shadow: 0 0 2px 0 #DDDDDD inset;
				margin-top: 3px;
				width: 541px;
				height: 137px;
				width: 100% !important;
			}
			
			.tv {
				z-index: 10;
				overflow: auto;
				margin-left: 5px;
				margin-bottom: 5px;
				width: 15%;
				height: 51%;
				position: auto;
				display: block;
				-moz-background-clip: padding; /* Firefox 3.6 */
				-webkit-background-clip: padding; /* Safari 4? Chrome 6? */
				-moz-box-shadow: 3px 3px 5px 6px #ccc;
				-webkit-box-shadow: 3px 3px 5px 6px #ccc;
				top: 8px;
				left: 0px;
				float: left;
				bottom: 8px;
				border: 0px solid rgba(0, 0, 0, 0.05);
				box-shadow: 0 0 0px rgba(0, 0, 0, 0.3)
				
				
			}
			
			div.pastas{
			
				background: none repeat scroll 0 0 padding-box #F7F7F7;
				padding: 0px 10px 5px;
				z-index: 10;
				overflow: auto;
				margin-left: 2px;
				margin-bottom: 5px;
				width: 95%;
				height: 100%;
				position: auto;
				display: block;
				-moz-background-clip: padding; /* Firefox 3.6 */
				-webkit-background-clip: padding; /* Safari 4? Chrome 6? */
				-moz-box-shadow: 3px 3px 5px 6px #ccc;
				-webkit-box-shadow: 3px 3px 5px 6px #ccc;
				top: 8px;
				left: 0px;
				float: left;
				bottom: 8px;
				border: 0px solid rgba(0, 0, 0, 0.05);
				box-shadow: 0 0 20px rgba(0, 0, 0, 0.3)
			
			}
			
			div.baseconhecimento{
				
				background: none repeat scroll 0 0 padding-box #F7F7F7;
				border: 0px solid rgba(0, 0, 0, 0.05);
				box-shadow: 0 0 8px rgba(0, 0, 0, 0.3);
				display: block;
				/* margin: 10px 10px 10px 17%; */
				/* padding: 20px 10px 5px; */
				position: relative;
				width: auto;
				height: 98%!important;
				min-height: 98%!important;
				margin-left: 3px!important;
				margin-right: 3px!important;

			
			}
			
			div.principal{
				
				padding-bottom: 50px;
				height: 180%;
			
			}
			
			.container_16{
			
				width: 92%;
				margin-left: 4%;
				margin-right: 4%;
				
				letter-spacing: -4px;
			}
			
			#tituloconhecimento.text{
				font-size: 20px;
				box-shadow: 0 0 0 0 #DDDDDD inset;
				font-style: italic;
			    font-weight: bold;
			    font-family: Lucida Grande;
			    border: 0px;
			    background: none;
				
			}
			
			#conhecimento.section{
				
				padding-top: 0px;
				
			}
			
			.iconsFAQ{
				 background: url("/citsmart/imagens/question-balloon.png") no-repeat scroll 0 0 transparent !important;
   				 font-weight: bold !important;
			}
			
			.manipulaDiv{
			  color: #777C86;
			    cursor: pointer;
			    display: block;
			    font-size: 14px;
			    font-weight: bold;
			    margin-top: 15px;
			    margin-left: 2px;
			    
			
			}
			.manipulaDiv:HOVER{
			  color: #A6CE39;
			    cursor: pointer;
			    display: block;
			    font-size: 14px;
			    font-weight: bold;
			    margin-top: 15px;
			   
			
			}
			
			
		</style>
		
		<%
					response.setHeader("Cache-Control", "no-cache");
					response.setHeader("Pragma", "no-cache");
					response.setDateHeader("Expires", -1);
					String iframe = "";
					iframe = request.getParameter("iframe");
					if (iframe == null) {
						iframe = "false";
					}
					
					String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
				%>
		
		
		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/dtree.js"></script>
		
		<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/jquery-ui-latest.js"></script>
    	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/jquery.layout-latest.js"></script>
    	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/debug.js"></script> 
		
		<script type="text/javascript">
		    var popup;
		    var controle = 0;
		    addEvent(window, "load", load, false);
		    function load(){		
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}

			function tituloBaseConhecimentoView(idItem) {
				
				document.form2.idBaseConhecimento.value = idItem;
				document.form2.fireEvent("restore");
				
				$('#conhecimento').show();
			}
			
			function verificarPermissaoDeAcesso(idPasta,idBaseConhecimento) {
				
				document.form2.idPasta.value = idPasta;
				document.form2.idBaseConhecimento.value = idBaseConhecimento;
				document.form2.fireEvent("verificarPermissaoDeAcesso");
				
			}

			function corTitulo(idItem) {
				var browser = document.getElementById("browser");
				var lista = browser.getElementsByTagName("a");
				for ( var i = 0; i < lista.length; i++) {
					if (lista[i] == null) {
						continue;
					}
					if (lista[i].id != "idTitulo" + idItem) {
						lista[i].style.backgroundColor = "#FFF";
					} else {
						lista[i].style.backgroundColor = "#B0C4DE";
						lista[i].style.color = "#000";
					}
				}

			}
			
			function tree(id) {
				$(id).treeview();
			}
			
			
			function limpar() {
				document.formPesquisa.clear();
			}
			
			var seqSelecionada = '';
			
			function fecharPesquisa(){
				$("#resultPesquisaPai").dialog("close");		
			}
			
			function mostraPesquisaBaseConhecimento(){
				document.getElementById("resultPesquisaPai").style.display="block";
			}
			
			$(function() {
				
				$("#resultPesquisaPai").dialog({
					title : '<i18n:message key="citcorpore.comum.resultadopesquisa"/>',
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
				
				$('#conhecimento').hide();
				
			
				 
			});
			
		
			pesquisarErroConhecido = function() {
				var auxiliar = document.formPesquisa.termoPesquisa.value;

				for ( var i = 1; i <= document.formPesquisa.termoPesquisa.value.length; i++) {
					auxiliar = auxiliar.replace(" ", "");
				}

				$('#conhecimento').hide();
				document.formPesquisa.fireEvent('pesquisaBaseConhecimentoTipoErroConhecimento');

			};

			function fecharBaseConhecimentoView() {
				parent.fechaPopupIframe();
			}

			function voltar() {
				verificarAbandonoSistema = false;
				window.location = '<%=retorno%>';
			}
			
			function contadorClicks(idBaseConhecimento){
				document.formPesquisa.idBaseConhecimento.value = idBaseConhecimento;
				document.formPesquisa.fireEvent('contadorDeClicks');	
			}
			$(function() {
				var myLayout;
				myLayout = $('body').layout({
				  		north__resizable: false // OVERRIDE the pane-default of 'resizable=true'
			          , north__spacing_open: 0 // no resizer-bar when open (zero height)
			          , north__spacing_closed: 350 
			          , north__minSize: 110
			          , west: {
			        	  minSize: 310
			        	  ,onclose_end: function(){
			        		  $('#baseconhecimento').css('width', '98%');
			        	  }
						  ,onopen_end: function(){
							  $('#baseconhecimento').css('width', '98%');
						}
			          }   
		        });
				
				jQuery.fn.toggleText = function(a,b) {
					return this.html(this.html().replace(new RegExp("("+a+"|"+b+")"),function(x){return(x==a)?b:a;}));
					} 
				
			
				 $('#divpesquisa').css('display', 'block');
				 
				 $('.manipulaDiv', '#tabs-1').click(function() {
					 $(this).next().slideToggle('slow') .siblings('#divpesquisa:visible').slideToggle('fast');
					 $(this).toggleText('<i18n:message key="baseConhecimento.esconder" />','<i18n:message key="baseConhecimento.mostrar" />').siblings('span').next('#divpesquisa:visible').prev()
					 .toggleText('<i18n:message key="baseConhecimento.esconder" />','<i18n:message key="baseConhecimento.mostrar" />');
				 });
			});
			function habilitaDivPesquisa(){
				 $('#divpesquisa').css('display', 'block');
				 $('#spanPesq').text('<i18n:message key="baseConhecimento.esconderPesquisa" />','<i18n:message key="baseConhecimento.mostrarPesquisa" />');
			}
			
			function desabilitaDivPesquisa(){
				  $('#divpesquisa').css('display', 'none'); 
				 $('#spanPesq').text('<i18n:message key="baseConhecimento.mostrarPesquisa" />','<i18n:message key="baseConhecimento.esconderPesquisa" />');
			}
			
		</script>
	</head>
	
	<body>	
		
		<%
			if (iframe.equalsIgnoreCase("false")) {
		%>
	<div class="ui-layout-north">
		<div id="divLogo" style="overflow: hidden!important;">
			<table cellpadding='0' cellspacing='0'>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<img border="0" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/logo.png" />
					</td>
				</tr>
			</table>	
		</div>
		
		<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block; ">
				<table cellpadding='0' cellspacing='0' width="100" style="width: 100%;">
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td width="100" style="display: block; float: left;">
							<button  type="button" class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<i18n:message key="citcorpore.comum.voltarprincipal" />">
								<img border="0" title="<i18n:message key='citcorpore.comum.voltarprincipal' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/back.png" /><span style="padding-left: 0px !important;"><i18n:message key="citcorpore.comum.voltar" /></span>
							</button>	
						
						</td>
					</tr>
				</table>	
			</div>
		</div>
		<%
			}
		%>
		
		<!-- <div id="wrapper" class="principal"> -->
		<div class="ui-layout-west">
		   <div  class="pastas">
				
				<form name='form2' onsubmit='javascript:return false;' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaErroConhecido/pesquisaErroConhecido'>
					<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento'/>
					<input type='hidden' id='idPasta' name='idPasta'/>
					
					<div>
						<div style="" id="principalBaseConhecimento"></div>
					</div>
					
				</form>
			
			</div>
		</div>
		<div class="ui-layout-center">
			<div id="baseconhecimento" class="baseconhecimento container_16 clearfix" style=" padding: 0px !important; /* height: 500px; */">
				
				<div class="flat_area grid_16" style="padding-bottom: 1px !important;margin-top: -1px; ">
				</div>
				
				<div class="box grid_16 tabs">
					<div class="toggle_container" >
						
						<div id="tabs-1" class="block">
						  <span  class='manipulaDiv' style='cursor: pointer'><span id='spanPesq' style='cursor: pointer'><i18n:message key="baseConhecimento.esconderPesquisa" /></span> &nbsp;<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/search.png" /></span>
							<div id="divpesquisa" class="section">
								<form name='formPesquisa' id='formPesquisa' onsubmit='javascript:return false;' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaErroConhecido/pesquisaErroConhecido'>
									<div class="flat_area grid_16"><h2><i18n:message key="baseConhecimento.erroConhecido" /></h2></div>
										<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento'/>
										
										<div class="columns clearfix">
											<div id='divBotaoFechar' style='top: 0px; left: 220px; z-index: 10000;'>
												<fieldset>
													<div class="col_66">
														<label style="font:bold 13px arial,serif;"><i18n:message key="baseConhecimento.pesquisar" /></label>
														<div>
															<input onkeydown="if ( event.keyCode == 13 || event.which == 13 ) pesquisarErroConhecido();" type='text' name='termoPesquisa' size='40' maxlength="200" />
														</div>
													</div>
													<div class="col_15">
														<button title='Pesquisa' type='button' id="btnPesquisar" name='btnPesquisar' style=" margin-top: 17px;margin-left: -30px;" class="light" onclick='pesquisarErroConhecido()'>
														<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
														<span><i18n:message key="baseConhecimento.pesquisar"/></span>
														</button>
														<%-- <%if(iframe.equalsIgnoreCase("false")){ %>	
														<div style="float: right;"><img border="0" src="/citsmart/imagens/btnvoltar.gif" title="Retornar ao menu principal" alt="Voltar" onclick="voltar()" style=" cursor:pointer; "></div>
														<%} %> --%>
													</div>
													
												</fieldset>
												<table>
													<tr>
														<td>
															<div id='resultPesquisaPai'
																style='display: none; border: 1px solid black; background-color: white; height: 400px; width: 650px; overflow: auto'>
																<table>
																	<tr>
																		<td>
																			<div style="margin: 10px !important;" id='resultPesquisa'></div>
																		</td>
																	</tr>
																</table>
															</div>
														</td>
													</tr>
												</table>
											</div>
										</div>
									</form>
								</div>
				
								<div class="section" id="conhecimento">
									<form id="form" name='form' onsubmit='javascript:return false;' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pesquisaErroConhecido/pesquisaErroConhecido'>
										
										<div class="flat_area grid_16">
											<input type='text' id="tituloconhecimento" name="titulo"/>
										</div>
										
										<div class="columns clearfix">
											<input type='hidden' id='idBaseConhecimento' name='idBaseConhecimento'/>
											<input type='hidden' name='idBaseConhecimentoPai'/>
											<input type='hidden' name='dataInicio'/>
											
											<div class="columns clearfix">
												<div class="col_25">
													<fieldset style="height: 58px">
														<label><i18n:message key="pasta.pasta"/></label>
															<div>
															<input type='text' id="nomePasta" name="nomePasta"/>
															</div>
													</fieldset>
												</div>
											</div>
											<div class="columns clearfix">
												<div  class="col_50">			
													<fieldset >
													<label ><i18n:message key="baseConhecimento.conteudo"/></label>	
														<div  id="conteudo">
														</div>
													</fieldset>
												</div>
											</div>
	                          			 </div>
									</form>
								</div>
							</div>
						</div>
					</div>		
			</div>
		</div>
		<!-- </div> -->
	
	</body>
</html>