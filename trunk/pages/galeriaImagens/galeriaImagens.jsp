<%@page import="br.com.centralit.citajax.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<!doctype html public "âœ°">

<html>
<head>
<%
	String retorno = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") + "/pages/index/index.load";

	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<%@include file="/include/security/security.jsp" %>
<title><i18n:message key="citcorpore.comum.title"/></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
	
}
.innerAll {
  padding: 15px;
  position: relative;
}
.box-generic {
  border: 1px solid #d8d8d8;
  padding: 15px;
  position: relative;
  background: #ffffff;
  box-shadow: 0 1px 0 0 #f7f7f7, 0 5px 4px -4px #d8d8d8;
  -moz-box-shadow: 0 1px 0 0 #f7f7f7, 0 5px 4px -4px #d8d8d8;
  -webkit-box-shadow: 0 1px 0 0 #f7f7f7, 0 5px 4px -4px #d8d8d8;
}
</style>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="" style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>

<script>
	var objTab = null;
	
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			document.getElementById('tabTela').tabber.tabShow(0);
		}
	}

	function selecionaCategoriaGaleriaImagem(){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('listaImagens');
	}
	function voltar(){
		verificarAbandonoSistema = false;
		window.location = '<%=retorno%>';
	}
	function adicionarImagem(){
		if(document.form.idCategoriaGaleriaImagem.value != ""){
			document.getElementById("arquivo").value = "";
			$('#POPUP_ADD_IMAGEM').dialog('open');
		}else{
			alert(i18n_message("galeriaImagens.selecioneUmaCategoria"));
		}
	}
	
	function excluirImagem(id){
		document.form.idImagem.value = id;
		if(confirm(i18n_message("citcorpore.comum.deleta"))){
			JANELA_AGUARDE_MENU.show();
			document.form.fireEvent("excluirImagem");
		}
	}
	
	function fecharPopUpAdicionarImagem(){
		$('#POPUP_ADD_IMAGEM').dialog('close');
	}
	
	$(function() {
		$("#POPUP_ADD_IMAGEM").dialog({
			autoOpen : false,
			width : 650,
			height : 350,
			modal : true,
			show: "fade",
			hide: "fade"
		});
	});

    carregouIFrameAnexo = function() {
    	$('#POPUP_ADD_IMAGEM').dialog('close');
    	selecionaCategoriaGaleriaImagem();
    	alert(i18n_message("MSG05"));
		HTMLUtils.removeEvent(document.getElementById("frameUpload"),"load", carregouIFrameAnexo);
    };
    	
	submitFormAnexo = function(){
		JANELA_AGUARDE_MENU.show();
		HTMLUtils.addEvent(document.getElementById("frameUpload"),"load", carregouIFrameAnexo, true);
		
	    document.formAddImagem.setAttribute("target","frameUpload");
	    document.formAddImagem.setAttribute("method","post"); 
	    document.formAddImagem.setAttribute("enctype","multipart/form-data");
	    document.formAddImagem.setAttribute("encoding","multipart/form-data");
	    
	    //submetendo 
	    document.formAddImagem.idCategoriaGaleriaImagem.value = document.form.idCategoriaGaleriaImagem.value;
	    document.formAddImagem.submit();
	};    
</script>
<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>
		<div class="flat_area grid_16">
				<h2>
					<i18n:message key="menu.nome.galeriaImagens" />
				</h2>
			</div>
<!-- 	Desenvolvedor: Pedro Lino - Data: 289/10/2013 - Horário: 17:00 - ID Citsmart: 120948 - 
		* Motivo/Comentário: Alterado todo layout para div, no novo padrão
		* Adicionado div Janela aguarde ao carregar as imagens -->			
<div class="box grid_16 tabs">
	<div id="areautil">
		<div id="formularioIndex">
       		<div id=conteudo>
				<table width="100%">
					<tr>
						<td width="100%">
							<div id='areaUtilAplicacao'>   
								<!-- ## AREA DA APLICACAO ## -->
							 	<form name='form' action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/galeriaImagens/galeriaImagens'>
							 	<input type='hidden' name='idImagem' id='idImagem' />
									<div class='row-fluid'>
										<div class="span6">
											<label ><strong><i18n:message key="citcorpore.comum.categoria" /></strong></label>
										  	<select name='idCategoriaGaleriaImagem' onchange='selecionaCategoriaGaleriaImagem()' ></select>
										</div>
										<div class="span6">
											<label >&nbsp;</label>
											<button type='button' name='btnAddImagem' onclick='adicionarImagem()'><i18n:message key="menu.nome.adicionarImagem" /></button>
										</div>
									
									</div>
									<div class='row-fluid'>
										<div class='box-generic'>
											<div id='divImagens'></div>
										</div>
									
									</div>
								</form>
							</div>
						</td>
					</tr>
				</table>
			</div>	
		</div>
	</div>
	<script>
/* function POPUP_ADD_IMAGEM_onhide(){
	selecionaCategoriaGaleriaImagem();
} */

</script>

<div id="POPUP_ADD_IMAGEM" title="" >
		<iframe name='frameUpload' id='frameUpload' src='about:blank' height="0" width="0" style='display:none'/></iframe>
	<form name='formAddImagem' method="post" ENCTYPE="multipart/form-data" action='<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/galeriaImagens/galeriaImagens.load'>
		<input type='hidden' name='idCategoriaGaleriaImagem' id='idCategoriaGaleriaImagemAdd'/>
		<div class='innerAll'>
			<div class='row-fluid'>
				
					<div class="span6">
							<label ><strong><i18n:message key="menu.nome.arquivoImagem" /></strong></label>
						  	<input type='file' name='arquivo' size="60"/>
						</div>
						<div class="span6">
							<label ><strong><i18n:message key="menu.nome.descricaoImagem" /></strong></label>
							<input type="text" name='descricaoImagem' size="70" maxlength="70">
						</div>
				</div>	
				<div class='row-fluid'>
					<div class="span12">
						<label ><strong><i18n:message key="citcorpore.ui.tabela.coluna.Detalhamento" /></strong></label>
						<textarea rows="5" cols="60" name="detalhamento" style='border:1px solid black'></textarea>
					</div>
				</div>
				<div class='row-fluid'>
					<div class="span12">
						<button type='button' name='btnEnviarImagem' value="" onclick='submitFormAnexo();'><i18n:message key="menu.nome.enviarImagem" /></button>
						<button type='button' name='btnFecharEnviarImagem' value="" onclick="fecharPopUpAdicionarImagem();"><i18n:message key="menu.nome.fecharImagem" /></button>
					</div>
			</div>
		</div>
	</form>
</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	<%@include file="/include/footer.jsp"%>
	
</div>
</body>
</html>