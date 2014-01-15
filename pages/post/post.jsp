<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Collection"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.UploadDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>

<!doctype html public "âœ°">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
	response.setHeader("Pragma", "no-cache");
	response.setDateHeader("Expires", -1);

	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
	<%@include file="/include/security/security.jsp"%>
	<!--<![endif]-->
	<title><i18n:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/UploadUtils.js"></script>
	<script type="text/javascript"src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
<script>
	var objTab = null;

	addEvent(window, "load", load, false);

	function load() {
				
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
		
		$("#POPUP_menuAnexos").dialog({
			autoOpen : false,
			width : 700,
			height : 400,
			modal : true,
			close: function(event, ui) {
				document.form.fireEvent("trocaImagem");
			}
		});
		
		$("#btnFecharTelaAnexos").click(function(){
			$('#POPUP_menuAnexos').dialog('close');	 
			document.form.fireEvent("trocaImagem");
		});
		
	}
	
	function LOOKUP_POST_select(id, desc) {
		document.form.restore({
			idPost :id});
	}
	function anexos(){
		$('#POPUP_menuAnexos').dialog('open');
		uploadAnexos.refresh();
	};
	
	function atualizaData() {
		var idPost = document.getElementById("idPost");

		if (idPost != null && idPost.value == 0) {
			alert((i18n_message('citcorpore.comum.necessarioSelecionarRegistro')));
			return false;
		}
		if (confirm(i18n_message('citcorpore.comum.deleta')))
			document.form.fireEvent("delete");
	}
	
	var oFCKeditor = new FCKeditor( 'conteudo' ) ;
    function onInitQuestionario(){
        oFCKeditor.BasePath = '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
        //oFCKeditor.Config['ToolbarStartExpanded'] = false ;

        oFCKeditor.ToolbarSet   = 'Default' ;
        oFCKeditor.Width = '100%' ;
        oFCKeditor.Height = '300' ;
        oFCKeditor.ReplaceTextarea() ;   
    }
    HTMLUtils.addEvent(window, "load", onInitQuestionario, false);
	
	function limpar() {
		document.form.fireEvent("limpar");
        var oEditor = FCKeditorAPI.GetInstance( 'conteudo' ) ;
        oEditor.SetData('');
	}
    
    function gravar() {

		var oEditor = FCKeditorAPI.GetInstance( 'conteudo' ) ;
		document.form.conteudo.value = oEditor.GetXHTML();
		
		if(document.form.conteudo.value == "<br />"){
			alert((i18n_message('post.validacao.informarConteudo')));
			return;
		}

		
		document.form.save();

	}
    
	function setDataEditor(){
		var oEditor = FCKeditorAPI.GetInstance( 'conteudo' ) ;
	    oEditor.SetData(document.form.conteudo.value);
	}    
		
</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
	width: 100%;
}
</style>
<%}%>
</head>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="post" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="post.cadastroPostagem" /></a>
					</li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="post.pesquisaPostagem" /></a>
					</li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' id="form" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/post/post'>
								<input type="hidden" name="idPost" />
								<input type="hidden" name="dataInicio" />
								<input type="hidden" name="dataFim" />
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="post.titulo" /></label>
											<div>
												<input type='text' name="titulo" maxlength="80" class="Valid[Required] Description[post.titulo]" />
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"> <i18n:message key="post.descricao" /> </label>
											<div>
												<textarea class="Valid[Required] Description[post.descricao]" name="descricao" cols='200' rows='5' maxlength="400"></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"> <i18n:message key="post.conteudo" /> </label>
											<div>
												<textarea class="Valid[Required]" name="conteudo" cols='200' rows='10'></textarea>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="post.categoria" /></label>
											<div>
											  <select name='idCategoriaPost' class="Valid[Required] Description[post.categoria]">	</select>
											</div>
										</fieldset>
									</div>
								</div>
								<div class="columns clearfix">
									<div class="col_66">
										<fieldset>
											<label><i18n:message key="post.imagem" /></label>
											<div>
												<input type="hidden" name="imagem" />
										      	<img id="img" name="img" style="max-height: 200px; max-width: 60%;" src="" />	
											      	
												<input type="button" class="light img_icon has_text" id="btAnexos" onclick="return anexos();" value="Adicionar..." />										
											</div>
										</fieldset>
									</div>
								</div>
								<br> <br>
								<button type='button' name='btnGravar' class="light" onclick='gravar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar" /></span>
								</button>
								<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar" /></span>
								</button>
								<button type='button' name='btnExcluir' class="light" onclick='atualizaData();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
									<span><i18n:message key="citcorpore.comum.excluir" /></span>
								</button>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_POST' id='LOOKUP_POST' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
	
	<div id="POPUP_menuAnexos" style='display:none'>		
		<form name="formUpload" method="post" enctype="multipart/form-data">
			<cit:uploadControl style="height:100px;width:100%;border:1px solid black"  title="Anexos" id="uploadAnexos" form="document.formUpload" action="/pages/upload/upload.load" disabled="false"/>			
			<button id='btnFecharTelaAnexos' name='btnFecharTelaAnexos' type="button">
				Fechar
			</button>
		</form>
	</div>
	
	<%@include file="/include/footer.jsp"%>
</body>
</html>