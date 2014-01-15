<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO"%>
<%
    request.setCharacterEncoding("UTF-8");
    response.setHeader("Content-Language", "lang");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<%@include file="/include/security/security.jsp"%>
	<title><i18n:message key="citcorpore.comum.title" /></title>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<%@include file="/include/header.jsp"%>
			
	<script type="text/javascript">
	
	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
		}
	}
	
	function tree(id) {
		
		$(id).treeview();
		
	}
	
	function excluir() {
		if (document.getElementById("idPerfilAcesso").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))){
				document.form.acessoMenuSerializados.value = adicionaMenus();
				document.form.fireEvent("delete");
			}
		}
	}
	
	function LOOKUP_PERFILACESSO_select(id, desc) {
		JANELA_AGUARDE_MENU.show();
		document.form.restore({idPerfilAcesso : id});
	}
	
	function marcarTodosCheckbox(id) {
	
		var classe = $(id).attr("class");
		var x = classe.split(" ");
		if(x[1] != null){
			classe = x[x.length - 2];
		} 
	
		var valor = "";
		if(!$(id).is(':checked')){
			
			$("." + classe).each(function() {
				$(this).attr("checked", false);
			});		
		}else{
			$("." + classe).each(function() {
					$(this).attr("checked", true);
			});		
		}
	}
	
		function checkboxPesquisar(id) {
			var idPesquisa = "pesq_" + id;
			$("#" + idPesquisa).attr("checked", true);
		}
	
		function checkboxIncluir(id) {
			var idInclui = "inc_" + id;
			$("#" + idInclui).attr("checked", true);
		}
	
		function checkboxGravar(id) {
			var idGravar = "gravar_" + id;
			$("#" + idGravar).attr("checked", true);
		}
	
		function checkboxDeletar(id) {
			var idDeleta = "del_" + id;
			$("#" + idDeleta).attr("checked", true);
		}
	
		adicionaMenus = function() {
			var id = "";
			var check = "";
			var lista = "";
			var tipo = "";
			var i = 0;
			var x = 0;
			var array = new Array();
			$("input[name='menu']").each(function() {
				id = $(this).val();
				if ($(this).is(':checked')) {
					tipo = "S";
				} else {
					tipo = "N";
				}
				check += tipo + "-";
				if (i == 2) {
					lista += id + "@" + check + ";";
					i = -1;
					check = "";
				}
				i++;
				x++;
			});
			return lista;
		}
	
		function gravar() {
			document.form.acessoMenuSerializados.value = adicionaMenus();
			document.form.save();
		}
	</script>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%@include file="/include/menu_vertical.jsp"%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix">
			<%@include file="/include/menu_horizontal.jsp"%>
			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="perfil.perfil" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="perfil.cadastro" /></a></li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="perfil.pesquisa" /></a></li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/perfilAcesso/perfilAcesso'>
								<div class="columns clearfix">
									<input type='hidden' name='idPerfilAcesso' id='idPerfilAcesso' /> 
									<input type='hidden' name='dataFim' id='dataFim' /> 
									<input type='hidden' name='dataInicio' id='dataInicio' /> 
									<input type='hidden' name='acessoMenuSerializados' id="acessoMenuSerializados" value=""/>
									<div class="col_66">
										<fieldset>
											<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome" />
											</label>
											<div>
												<input id="nomePerfilAcesso" name="nomePerfilAcesso" type='text' maxlength="80" class="Valid[Required] Description[citcorpore.comum.nome]" onkeypress="return tratarEnter(this, event);"/>
											</div>
										</fieldset>
									</div>
								</div>
								<div>
								<div id="principalInf" style="margin-left: 20px; margin-bottom: 30px;"></div>
								</div>
								<div style="margin-bottom: 30px; margin-left: 20px;">
									<br><br>
									<table>
										<tr>
											<td>
												<b><i18n:message key="perfil.acessoPorSituacaoOS" /></b><br>
												<input type='checkbox' name='situacaoos' id='situacaoos1' value='1'/>&nbsp;<i18n:message key="perfil.criacao" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos2' value='2'/>&nbsp;<i18n:message key="perfil.solicitada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos3' value='3'/>&nbsp;<i18n:message key="perfil.autorizada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos4' value='4'/>&nbsp;<i18n:message key="perfil.aprovada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos5' value='5'/>&nbsp;<i18n:message key="perfil.execucao" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos6' value='6'/>&nbsp;<i18n:message key="perfil.executada" /><br>
												<input type='checkbox' name='situacaoos' id='situacaoos7' value='7'/>&nbsp;<i18n:message key="perfil.cancelada" /><br>
											</td>
											<td style="padding-left: 50px;">
												<b><i18n:message key="perfil.acessoPorSituacaoFatura" /></b><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura1' value='1'/>&nbsp;<i18n:message key="perfil.criacao" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura2' value='2'/>&nbsp;<i18n:message key="perfil.aguardandoAprovacao" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura3' value='3'/>&nbsp;<i18n:message key="perfil.aprovada" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura4' value='4'/>&nbsp;<i18n:message key="perfil.rejeitada" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura5' value='5'/>&nbsp;<i18n:message key="perfil.recebimento" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura6' value='6'/>&nbsp;<i18n:message key="perfil.recebida" /><br>
												<input type='checkbox' name='situacaoFatura' id='situacaoFatura7' value='7'/>&nbsp;<i18n:message key="perfil.cancelada" /><br>
											</td>											
										</tr>
									</table>
								</div>
								<div  style="margin-left: 20px; margin-bottom: 30px;">
									<button type='button' name='btnGravar' class="light" onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" /></span>
									</button>
									<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" /></span>
									</button>
									<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" /></span>
									</button>
								</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa" />
						<form name='formPesquisa'>
							<cit:findField formName='formPesquisa' lockupName='LOOKUP_PERFILACESSO' id='LOOKUP_PERFILACESSO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
		<!-- Fim POPUP ITEM DE CONFIGURǇÃO -->
</body>
</html>