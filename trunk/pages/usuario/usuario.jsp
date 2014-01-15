<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<!doctype html public "âœ°">
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
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	
	<script type="text/javascript" src="../../cit/objects/GrupoDTO.js"></script>
	
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
		border:1px solid #ddd;
		padding:4px 10px;
		border-top:none;
		border-left:none;
	}
	
	.cancelar {
		color: #FF0000;
		content: "*";
	}
	
	
</style>
	<script>
	    var contGrupo = 0;
	    var popup;
	    addEvent(window, "load", load, false);
	    function load(){		
		popup = new PopupManager(1000, 900, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
			}
	    }
		
		function LOOKUP_USUARIO_select(id,desc){
			document.form.restore({idUsuario:id});
			
			$("#divSenha").hide();
			$("#divAlterarSenha").show();
			$("#divGrupo").show();
		}
		function LOOKUP_EMPREGADO_USUARIO_select(id, desc){
			document.form.idEmpregado.value = id;
			document.form.fireEvent("restoreEmpregado");
		}
		
		function fecharPopup(){
			$('.tabs').tabs('select', 0);
			
		}
		
		function validar(){
		
			document.form.colGrupoSerialize.value = '';
			//serialização do grupo
			
			var objOcorrencia = HTMLUtils.getObjectsByTableId('tabelaGrupo');
			document.form.colGrupoSerialize.value = ObjectUtils.serializeObjects(objOcorrencia);
			
	    	var senha = document.getElementById("senha").value;
	    	var senha2 = document.getElementById("senhaNovamente").value;
			
	    	if (senha == senha2){
				document.form.save();
			}
			else{
				alert(i18n_message("usuario.senhaDiferente"));
				document.getElementById("senha").value = "";
				document.getElementById("senhaNovamente").value = "";
				document.getElementById("senha").focus(); 
			}
		}		
		
		function excluir() {
			if (document.getElementById("idUsuario").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}
		
		$(function() {
			$("#POPUP_EMPREGADO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_GRUPO").dialog({
				autoOpen : false,
				width : 650,
				height : 400,
				modal : true,
			});	
			
			$("#divAlterarSenha").hide();
			$("#divGrupo").hide();
		});
		
		function alterarSenha(){
			$("#divSenha").show();
		}
		
		$(function() {
			$("#addEmpregado").click(function() {
				$("#POPUP_EMPREGADO").dialog("open");
			});
		});
		
	 	function fecharPopup(){
			$("#POPUP_EMPREGADO").dialog("close");
		}
	 	
	 	function limpar(){
	 		deleteAllRows();
			$("#divSenha").show();
			$("#divAlterarSenha").hide();
			$("#divGrupo").hide();
	 		document.form.clear();
	 	}
	 	
	 	function fecharAddSolicitante(){
			$("#popupCadastroRapido").dialog('close');
		}
	 	
	 	var nomeGrupo;
	 	function LOOKUP_GRUPO_EVENTO_select(id, desc){
	 		$("#divGrupo").show();
	 		document.form.idGrupo.value = id;
	 		nomeGrupo = desc;
	 		addGridGrupo();
	 		$("#POPUP_GRUPO").dialog("close");
		}
	 	
	 	
	    addGridGrupo = function() {
	    	var tbl = document.getElementById('tabelaGrupo');
			tbl.style.display = 'block';
			var lastRow = tbl.rows.length;
	   
	        if( document.getElementById('rowIndex').value == null ||  document.getElementById('rowIndex').value == undefined || document.getElementById('rowIndex').value < 1){
	            var obj = new CIT_GrupoDTO();
	            obj.idGrupo = document.form.idGrupo.value;
	            obj.nome = nomeGrupo;
	    
	            HTMLUtils.addRow('tabelaGrupo', document.form, null, obj, ['','idGrupo','nome'], ['idGrupo'], '<i18n:message key="citcorpore.comum.registroJaAdicionado"/>', [gerarButtonDeleteGrupo], null, null, false);
	            nomeGrupo = "";
	            document.form.idGrupo.value = "";
	            novoItem();
	        } else {                
		        var obj = HTMLUtils.getObjectByTableIndex('tabelaGrupo', document.getElementById('rowIndex').value);
		        obj.idGrupo = document.form.idGrupo.value;
	            obj.nome = nomeGrupo;

		        HTMLUtils.updateRow('tabelaGrupo', document.form, null, obj, ['','idGrupo','nome'], null, '', [gerarButtonDeleteGrupo], null, null, document.getElementById('rowIndex').value, false);
		        nomeGrupo = "";
		        document.form.idGrupo.value = "";
	            novoItem();
	        }  
	     //  limpaDadosTableInfo();
	        HTMLUtils.applyStyleClassInAllCells('tabelaGrupo', 'celulaGrid');
		}
	 	
	 	$(function() {
			$("#addGrupo").click(function() {
				$("#POPUP_GRUPO").dialog("open");
			});
		});
	 	
	 	function deleteAllRows() {
			var tabela = document.getElementById('tabelaGrupo');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
			//document.getElementById('tabelaGrupo').style.display = "none";
			contGrupo = 0;
	 	}
	 	
	    function gerarButtonDeleteGrupo(row) {
			row.cells[0].innerHTML = '<img id="imgDelInfo" style="cursor: pointer;"  title="<i18n:message key="citcorpore.comum.excluir" />" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="deleteLinha(\'tabelaGrupo\', this.parentNode.parentNode.rowIndex);">'
		}
	    function novoItem(){
	 		document.form.rowIndex.value = "";
	    }
	    function deleteLinha(table, index){
	    	if (table == "tabelaGrupo"){
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
			HTMLUtils.deleteRow(table, index);
				}
	    	}
		}
	 	
	</script>
	
<%//se for chamado por iframe deixa apenas a parte de cadastro da pÃ¡gina
			if (iframe != null) {%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
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
				<h2><i18n:message key="usuario.usuario"/></h2>						
		</div>
  <div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><i18n:message key="usuario.cadastroUsuario"/></a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top"><i18n:message key="usuario.pesquisaUsuario"/></a>
				</li>
			</ul>				
	<a href="#" class="toggle">&nbsp;</a>
	 			<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/usuario/usuario'>
									
									<div class="columns clearfix">
										<input type='hidden' name='idUsuario' id='idUsuario'/> 
										<input type='hidden' name='idEmpresa' id='idEmpresa'  />
										<input type='hidden' name='idEmpregado' id='idEmpregado' />
										<input type='hidden' name='dataInicio' id='dataInicio' />
										<input type='hidden' name='dataFim' id='dataFim' />
										<input type='hidden' name='status' id='status'  />
										<input type='hidden' name='colGrupoSerialize'/>
										<input type="hidden" id="rowIndex" name="rowIndex"/>
										<input type='hidden' name='idGrupo' id='idGrupo'/>
										
										
										<div class="col_66">
											<fieldset>
												<label  class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="colaborador.colaborador"/></label>
												<div> 
													<input id="addEmpregado" id="nomeUsuario" type='text' readonly="readonly" name="nomeUsuario" maxlength="80" class="Valid[Required] Description[colaborador.colaborador]" />
												</div>
											</fieldset>
										</div>
										
										<div class="col_66">
											<div class="col_50" >
												<fieldset style="height: 61px">
													<label  class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="usuario.login"/></label>
													<div>
														<input id="login" type='text' name="login" maxlength="70" class="Valid[Required] Description[usuario.login]" />
													</div>
												</fieldset>
											</div>
											
											<div class="col_50">
												<fieldset style="height: 61px">
													<label class="campoObrigatorio" style="margin-top: 5px;"><i18n:message key="usuario.perfilAcesso"/>
													</label>
													<div>
														<select name="idPerfilAcessoUsuario" id ="idPerfilAcessoUsuario"  class="Valid[Required] Description[usuario.perfilAcesso]">
														</select>
													</div>
												</fieldset>
											</div>
										</div>
										
										<div class="col_66" id="divAlterarSenha">
											<fieldset>
												<label onclick="alterarSenha()" style="cursor: pointer; margin-top: 5px; margin-bottom: 5px;"><img alt="" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/alterarsenha.png"><i18n:message key="usuario.alterarSenha"/></label>
											</fieldset>
										</div>
									
										<div class="col_66" id="divSenha">
											<div class="col_50" >
												<fieldset>
													<label  style="margin-top: 5px;"><i18n:message key="usuario.senha"/></label>
													<div>
														<input id="senha" type="password" name="senha"  maxlength="20"  />
													</div>
												</fieldset>
											</div>
											<div class="col_50" >
												<fieldset>
													<label  style="margin-top: 5px;"><i18n:message key="usuario.senhaNovamente"/></label>
													<div>
														<input id="senhaNovamente" type="password" name="senhaNovamente" maxlength="20" />
													</div>
												</fieldset>
											</div>	
										</div>
																
										<div class="col_66">
										<div class = "columns clearfix">
										<div>
											<fieldset style="border-bottom: none; padding-bottom: 5px; padding-top: 5px;">
												<label id="addGrupo" style="cursor: pointer;"
														title="<i18n:message  key="citcorpore.comum.cliqueParaAdicinar" />"><i18n:message key="controle.grupo" /><img	src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"></label>														
											</fieldset>
											</div>
											<div id ="divGrupo">
														<table class="table" id="tabelaGrupo" style="width: 850px; margin-left: 15px;">
															<tr>
															   <th style="width: 1%;"></th>
																<th style="width: 35%;"><i18n:message  key="grupo.idgrupo" /></th>
																<th style="width: 85%;"><i18n:message  key="controle.grupo" /></th>
															</tr>
														</table>
														</div>											
											</div>
										</div>										         	
										         	
										
			                     </div>
			                     
			                    <br><br>
									<button type='button' name='btnGravar' class="light text_only has_text" onclick='validar();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name='btnLimpar' class="light text_only has_text" onclick='limpar();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar" />
										</span>
									</button>
									<button type='button' name='btnUpDate' class="light text_only has_text"
										onclick='excluir();'>
										<img
											src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" />
										</span>
									</button>
									<button type='button' name='btnImportar' class="light text_only has_text" onclick="popup.abrePopup('cargaUsuarioAd', ' ')">
										<span><i18n:message key="parametroCorpore.importarDados"/></span>
									</button>
							   
								<div id="popupCadastroRapido">
			                           <!-- ## Desenvolvedor: Euler Data: 28/10/2013 Horário: 09h45min ID Citsmart: 120393 Motivo/Comentário: Eliminar multiplas scrolls ## -->
			                           <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
		                        </div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa" />
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_USUARIO' id='LOOKUP_USUARIO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
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
<div id="POPUP_EMPREGADO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
	<div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaEmp' style="width: 540px !important;">
						<cit:findField formName='formPesquisaEmp' 
							lockupName='LOOKUP_EMPREGADO_USUARIO' id='LOOKUP_EMPREGADO_USUARIO' top='0'
							left='0' len='1050' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
<div id="POPUP_GRUPO" title="<i18n:message key="citcorpore.comum.pesquisa" />">
	<div class="box grid_16 tabs"  style="width: 570px !important;">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section">
					<form name='formPesquisaGrupo' style="width: 540px !important;">
						<cit:findField formName='formPesquisaGrupo' 
							lockupName='LOOKUP_GRUPO_EVENTO' id='LOOKUP_GRUPO_EVENTO' top='0'
							left='0' len='1050' heigth='400' javascriptCode='true'
							htmlCode='true' />
					</form>
				</div>
			</div>
		</div>
	</div>
</div>
</html>
