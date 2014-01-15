<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "âœ°">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			String iframe = "";
			iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp"%>
<html lang="en-us" class="no-js">
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>



	<style type="text/css">
	.table {
		border-left:1px solid #ddd;
	}
	
	.table th {
		border:1px solid #ddd;
		padding:4px 10px;
		border-left:none;
		background:#eee;
	}
	
	.table td {
		border:1px solid #ddd !important;
		padding:4px 10px !important;
		border-top:none !important;
		border-left:none !important;
	}
	</style>
	
	<script  charset="ISO-8859-1" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
<script>
	var objTab = null;
	var popup;
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
		
		popup = new PopupManager(850, 500, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
	}

	function criarMensagensNovas(){
		if(document.getElementById("idLingua").value != ""){
			
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("criarMensagensNovos");
		}else{
			alert(i18n_message("dicionario.selecioneUmIdioma"))
		}
		
	}
	
	
	function DicionarioDTO(idDicionario,idLingua,nome,valor, i){
 		this.idDicionario = idDicionario; 
 		this.idLingua = idLingua; 
 		this.nome = nome; 
 		this.valor = valor; 
 	}
	
	function serializa(){
 		var tabela = document.getElementById('tabelaRetorno');
 		var count = tabela.rows.length;
 		var listaDeDicionario = [];
 		for(var i = 0; i < count ; i++){
 			if (document.getElementById('idDicionario' + i) != "" && document.getElementById('idDicionario' + i) != null){
 			var idDicionario = document.getElementById('idDicionario' + i).value;
 			var idLingua = document.getElementById('idLingua' + i).value;
 			var nome = document.getElementById('nome' + i).value;
 			var valor = document.getElementById('valor' + i).value;
 			var dicionario = new DicionarioDTO(idDicionario,idLingua,nome,valor, i);
 			listaDeDicionario.push(dicionario);
 			}
 		} 	
 		var serializa = ObjectUtils.serializeObjects(listaDeDicionario);
		document.form.dicionarioSerializados.value = serializa;
 	}
	
	function gravar(){
		JANELA_AGUARDE_MENU.show();
		serializa();
		document.form.save();
	}
	
	function deleteAllRows() {
		var tabela = document.getElementById('tabelaRetorno');
		var count = tabela.rows.length;

		while (count > 1) {
			tabela.deleteRow(count - 1);
			count--;
			
		}
		$('#tabelaRetorno').hide();
	}
	
	function limpar(){
		deleteAllRows();
		document.form.clear();
	}
	
	function exportarDicionarioXml(){
		if(document.getElementById("idLingua").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("exportarDicionarioXml");
		}else{
			alert(i18n_message("dicionario.selecioneUmIdioma"))
		}
		
	}
	
	function importarDicionarioXml(){
		if(document.getElementById("idLingua").value != ""){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("importarDicionarioXml");
		}else{
			alert(i18n_message("dicionario.selecioneUmIdioma"))
		}
		
	}
	

</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>
<%}%>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div  class="flat_area grid_16">
				<h2>
					<i18n:message key="dicionario.dicionario" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="dicionario.dicionario" />
					</a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/dicionario/dicionario'>
								<input type="hidden"  id="dicionarioSerializados" name= 'dicionarioSerializados'/>
								 <div class="columns clearfix">
									<div class="col_50">
										<fieldset>
											<label > <i18n:message key="lingua.lingua" /></label>
												<div style="padding-top: 3px;">
													<select name='idLingua' class="Description[unidade.unidadePai]" onchange="document.form.fireEvent('listaContrato')">
														</select>
												</div>
										</fieldset>
									</div>
								</div>
								 <div class="columns clearfix">
									 <div  style="overflow-x:hidden;">
										 <div style=" height: 300px;"  class="col_100" id="tabelaDicionario" >
										 </div> 
									 </div>
								</div> 
								
								<div id="popupCadastroRapido">
							     <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
						    </div>	
								
								<br>
								<br>
								<button  type='button' id="btnGravar" name='btnGravar' class="light"
									onclick='gravar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" />
									</span>
								</button>
								<button type='button' name='btnGerarMensagens' id="btnGerarMensagens"
									class="light" onclick='criarMensagensNovas();'>
									<img style="height: 25px" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/white_list_pencil.png">
									<span><i18n:message key="dicionario.geraMensagens" /></span>
								</button>
								<button type='button' name='btnExportar' class="light"
									onclick='exportarDicionarioXml();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/xml.png">
									<span><i18n:message key="dicionario.exportarXml"/>
									</span>
								</button>
								<button type='button' name='btnImportar' class="light" onclick="popup.abrePopup('cargaMensagens', ' ')">
								<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/xml.png">
										<span><i18n:message key="parametroCorpore.importarDados"/></span>
							    </button>
								<button type='button' name='btnLimpar' class="light"
									onclick='limpar();'>
									<img
										src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" />
									</span>
								</button>
								
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
