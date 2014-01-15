<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!doctype html public "">
<html>
<head>

<%@include file="/include/security/security.jsp" %>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="en-us" class="no-js"> <!--<![endif]-->
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" 
			src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
<script>
	
	addEvent(window, "load", load, false);
	
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		};
	}
	
	function LOOKUP_COMANDOSISTEMAOPEARCIONAL_select(idSO, desc) {
		document.form.restore({id: idSO});
	}
	
	function gravar() {
		document.form.save();		
	}
	
	//funcoes de tratamento da popup de cadastro rápido
	
	/*
	*configurações da popup
	*layout: <div id="popupCadastroRapido">
	*			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%">
	*			</iframe>
	*		</div>
	*/
	$(document).ready(function() {
		$( "#popupCadastroRapido" ).dialog({
			title: 'Cadastro Rápido',
			width: 900,
			height: 500,
			modal: true,
			autoOpen: false,
			resizable: true,
			show: "fade",
			hide: "fade"
			}); 
		
		$("#popupCadastroRapido").dialog('close');
				
	});
	
	
	
	/*
	*funcao chamada no onclick para abrir a popup passando como parâmetro 
	*a página que deseja abrir e a fireEvent que será executada
	*ao fechar a popup (que poder ser uma função do action para
	*recarregar a combo). Exemplo: abrePopup('unidade', 'preencheLista');
	*/
	function abrePopup(pagina, callbackBeforeClose) {
		document.getElementById('frameCadastroRapido').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/' + pagina + '/' + pagina + '.load?iframe=true';	
		
		$("#popupCadastroRapido").dialog('open');
		
		//quando fechar a popup ele executa um evento
		$("#popupCadastroRapido").dialog({
			beforeClose: function(event, ui) {
	   			//aqui o evento disparado ao fechar
	   			document.form.fireEvent(callbackBeforeClose);		   
	   		}
		});
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
					<h2><i18n:message key="comandoso.comandoso"/></h2>						
			</div>
			
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li>
						<a href="#tabs-1"><i18n:message key="comandoso.cadastro"/></a>
					</li>
					<li>
						<a href="#tabs-2" class="round_top"><i18n:message key="comandoso.pesquisa"/></a>
					</li>
				</ul>				
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="section">							
						<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/comandoSistemaOperacional/comandoSistemaOperacional'>
							<input type='hidden' name='id' />
							<div class="columns clearfix">	
								<div  class="col_66">			
									<fieldset style="height: 65px">
										<label class="campoObrigatorio" onclick="alerta();"><i18n:message key="comandoso.comandoso"/></label>
											<div>
												<input type="text" id="comando" name="comando" maxlength="80" class="Valid[Required] Description[comandoso.comandoso]" />
											</div>
									</fieldset>
								</div>
								<div class="col_33">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="comandoso.sistema"/>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
													onclick="abrePopup('sistemaOperacional', 'load')">
										</label>
											<div>
												<select name='idSistemaOperacional' id='idSistemaOperacional' class="Valid[Required] Description[comandoso.sistema]"></select>					
											</div>
									</fieldset>
								</div>
							</div>	
							<div class="columns clearfix">
								<div class="col_100">				
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="comandoso.funcao"/>
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrePopup('comando', 'load')">
										</label>
											<div>
												<select name='idComando' id='idComando' class="Valid[Required] Description[comandoso.funcao]"></select>	
											</div>
									</fieldset>
								</div>			
							</div>
					
							<div id="popupCadastroRapido">
								<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%">
								</iframe>
							</div>
							<br><br>
					
									            
							<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
							<span><i18n:message key="citcorpore.comum.gravar"/></span>
							</button>
							
							
							<button type='button' name='btnLimpar' class="light" onclick='document.form.clear();'>
							<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
							<span><i18n:message key="citcorpore.comum.limpar"/></span>
							</button>						         
						</form>
				</div>
				</div>
				
				<div id="tabs-2" class="block">
					<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa"/>
							<!-- EDITAR QUANDO COLOCAR FUNCIONALIDADE -->
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_COMANDOSISTEMAOPEARCIONAL' id='LOOKUP_COMANDOSISTEMAOPEARCIONAL' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
