<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
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

<script  charset="ISO-8859-1" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>		
		
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
			#imagens {
				margin: 10px 0;
				display: inline-block;
			}
			#imagens  label {
				display: inline-block !important;
				float: left;
				display: inline-block; 
			}
			#imagens  label img, input {
				vertical-align: middle !important;
			}
			<%
			    //se for chamado por iframe deixa apenas a parte de cadastro da página
			    if (iframe != null) {
			%>
			div#main_container {
			margin: 10px 10px 10px 10px;
			}
			<%
			    }
			%>
		</style>

		<script type="text/javascript">
			var objTab = null;
	
			addEvent(window, "load", load, false);
	
			function load() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
				}
			}
	
			function update() {
				document.form.fireEvent("excluirTipoItemConfiguracao");
			}
	
			function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {
				document.form.restore( {
					id : idTipo
				});
			}
	
			function LOOKUP_CARACTERISTICA_select(id, desc) {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;
				if (lastRow > 2) {
					var arrayIdCaracteristica = document.form.idCaracteristica;
					for ( var i = 0; i < arrayIdCaracteristica.length; i++) {
						if (arrayIdCaracteristica[i].value == id) {
							alert('Característica já adicionada!');
							return;
						}
					}
				} else if (lastRow == 2) {
					var idCaracteristica = document.form.idCaracteristica;
					if (idCaracteristica.value == id) {
						alert('Caracteristica já adicionada!');
						return;
					}
				}
	
				insereRow(id, desc);
	
				$("#POPUP_CARACTERISTICA").dialog("close");
				exibeGrid();
			}
			
			
			var popupA;
		    addEvent(window, "load", load, false);
		    function load(){		
				popupA = new PopupManager(600, 450, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
				document.form.afterRestore = function () {
					$('.tabs').tabs('select', 0);
				}
		    }
			
	
			var countCaracteristica = 0;
			function insereRow(id, desc) {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;
	
				var row = tabela.insertRow(lastRow);
				countCaracteristica++;
	
				var valor = desc.split(' - ');
	
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<img id="imgExcluiCaracteristica' + countCaracteristica + '" style="cursor: pointer;" title="Excluir Característica!" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaCaracteristica\', this.parentNode.parentNode.rowIndex);">';
	
				coluna = row.insertCell(1);
				coluna.innerHTML = valor[0] + '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica" value="' + id + '" />';
	
				coluna = row.insertCell(2);
				coluna.innerHTML = valor[1];
	
				coluna = row.insertCell(3);
				coluna.innerHTML = valor[2]
			}
	
			function restoreRow() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var lastRow = tabela.rows.length;
	
				var row = tabela.insertRow(lastRow);
				countCaracteristica++;
	
				var coluna = row.insertCell(0);
				coluna.innerHTML = '<img id="imgExcluiCaracteristica' + countCaracteristica	+ '" style="cursor: pointer;" title="Excluir Característica!" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removeLinhaTabela(\'tabelaCaracteristica\', this.parentNode.parentNode.rowIndex);">';
	
				coluna = row.insertCell(1);
				coluna.innerHTML = '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica"/><input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="caracteristica' + countCaracteristica + '" name="caracteristica"/>';
	
				coluna = row.insertCell(2);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="tagCaracteristica' + countCaracteristica + '" name="tagCaracteristica"/>';
	
				coluna = row.insertCell(3);
				coluna.innerHTML = '<input style="width: 100%; border: 0 none;" readonly="readonly" type="text" id="descricao' + countCaracteristica + '" name="descricao"/>';
			}
	
			var seqSelecionada = '';
			function setRestoreCaracteristica(idCaracteristica, caracteristica, tag, valorString,
					descricao, idEmpresa, dataInicio, dataFim) {
				if (seqSelecionada != '') {
					eval('document.form.idCaracteristica' + seqSelecionada + '.value = "' + idCaracteristica + '"');
					eval('document.form.caracteristica' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + caracteristica + '\')');
					eval('document.form.tagCaracteristica' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + tag + '\')');
					eval('document.form.descricao' + seqSelecionada + '.value = ObjectUtils.decodificaEnter(\'' + descricao + '\')');
				}
				exibeGrid();
			}
	
			function removeLinhaTabela(idTabela, rowIndex) {
				if (confirm('Deseja realmente excluir característica?')) {
					HTMLUtils.deleteRow(idTabela, rowIndex);
	
					document.form.caracteristicaSerializada.value = eval('document.form.idCaracteristica' + rowIndex + '.value');
	
					document.form.fireEvent("excluirAssociacaoCaracteristicaTipoItemConfiguracao");
				}
			}
	
			function deleteAllRows() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var count = tabela.rows.length;
	
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
				ocultaGrid();
			}
	
			function gravar() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var caracteristicas = new Array();
	
				for ( var i = 1; i <= count; i++) {
					var trObj = document.getElementById('idCaracteristica' + i);
	
					if (!trObj) {
						continue;
					}
	
					caracteristicas[contadorAux] = getCaracteristica(i);
					contadorAux = contadorAux + 1;
				}
				serializa();
				document.form.save();
			}
	
			var seqSelecionada = '';
			var aux = '';
			serializa = function() {
				var tabela = document.getElementById('tabelaCaracteristica');
				var count = tabela.rows.length;
				var contadorAux = 0;
				var caracteristicas = new Array();
				for ( var i = 1; i <= count; i++) {
					var trObj = document.getElementById('idCaracteristica' + i);
	
					if (!trObj) {
						continue;
					}
	
					caracteristicas[contadorAux] = getCaracteristica(i);
					contadorAux = contadorAux + 1;
				}
				var caracteristicasSerializadas = ObjectUtils
						.serializeObjects(caracteristicas);
				document.form.caracteristicasSerializadas.value = caracteristicasSerializadas;
				return true;
			}
	
			getCaracteristica = function(seq) {
				var CaracteristicaDTO = new CIT_CaracteristicaDTO();
				CaracteristicaDTO.sequencia = seq;
				CaracteristicaDTO.idCaracteristica = eval('document.form.idCaracteristica' + seq + '.value');
				return CaracteristicaDTO;
			}
	
			$(function() {
				$("#POPUP_CARACTERISTICA").dialog( {
					autoOpen : false,
					width : 600,
					height : 400,
					modal : true
				});
			});
	
			$(function() {
				$("#addCaracteristica").click(function() {
					$("#POPUP_CARACTERISTICA").dialog("open");
				});
			});
	
			function limpar() {
				deleteAllRows();
				document.getElementById('gridCaracteristica').style.display = 'none';
				document.form.clear();
				bloquearTag(false);
			}
	
			function exibeGrid() {
				document.getElementById('gridCaracteristica').style.display = 'block';
			}
	
			function ocultaGrid() {
				document.getElementById('gridCaracteristica').style.display = 'none';
			}

			function bloquearTag(valor){
				document.getElementById('tag').readOnly  = valor;
			}
		</script>
	</head>
	<body>	
	
		<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
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
					<h2><i18n:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="tipoItemConfiguracao.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top"><i18n:message key="tipoItemConfiguracao.pesquisa"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">											
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/tipoItemConfiguracao/tipoItemConfiguracao'>
									<div class="columns clearfix">
										<input type='hidden' name='id'/>
										<input type='hidden' name='idEmpresa' value="<%=WebUtil.getIdEmpresa(request)%>"/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' name='caracteristicasSerializadas'/>
										<input type='hidden' name='caracteristicasDeserializadas'/>
										<input type='hidden' id='caracteristicaSerializada' name='caracteristicaSerializada'/>
										
										<div class="columns clearfix">
											<div class="col_50">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></label>
														<div>
														  <input type='text' name="nome" maxlength="70" size="70" class="Valid[Required] Description[tipoItemConfiguracao.tipoItemConfiguracao]" />
														</div>
												</fieldset>
											</div>
											<div class="col_25">
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.tag"/></label>
														<div>
														  <input type='text' name="tag" maxlength="50" size="70" class="Valid[Required] Description[citcorpore.comum.tag]" />
														</div>
												</fieldset>
											</div>
											<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="tipoItemConfiguracao.categoria"/></label>
													<div>
													  	<select  name="categoria" class="Valid[Required] Description[tipoItemConfiguracao.categoria]"></select>
													</div>
											</fieldset>
										</div>		
										</div>
										<div class="columns clearfix">
											<div class="col_100" style="padding-left: 20px; padding-top: 10px;">
												<button id="addCaracteristica" type='button' name='botaoCaracteristica' class="light">
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/adcionar.png">
													<span><i18n:message key="tipoItemConfiguracao.inserirCaracteristicas"/></span>
												</button>
												<button type='button' name='botaoCaracteristica' class="light" onclick="popupA.abrePopup('caracteristica', 'preencherComboUnidade')" >
													<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/adcionar.png">
													<span>
														<i18n:message key="tipoItemConfiguracao.criarCaracteristicas"/>
													</span>
												</button>
											</div>
										</div>
										<br>
										<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
											<table class="table" id="tabelaCaracteristica" style="width: 100%">
												<tr>
													<th style="width: 16px;"></th>
													<th style="width: 40%;"><i18n:message key="citcorpore.comum.caracteristica"/></th>
													<th style="width: 20%;"><i18n:message key="citcorpore.comum.tag"/></th>
													<th style="width: 40%;"><i18n:message key="citcorpore.comum.descricao"/></th>
												</tr>
											</table>
										</div>
									</div>	
									<div class="col_100">
										<fieldset>
											<label ><i18n:message key="tipoitemconfiguracao.imagem" /></label>
										</fieldset>
										<div id="imagens"></div>
									</div>
									
									<br><br>
									<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light" onclick='limpar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>	
									<button type='button' name='btnUpDate' class="light" onclick='update();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir"/></span>
									</button>					         
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' 
													lockupName='LOOKUP_TIPOITEMCONFIGURACAO' 
													id='LOOKUP_TIPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400' 
													javascriptCode='true' 
													htmlCode='true' />
								</form>
							</div>
						</div>
						
					</div>
				</div>		
			</div>
		</div>
		
		<div id="popupCadastroRapido">
			<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="100%"></iframe>
		</div>
		
		<%@include file="/include/footer.jsp"%>
	</body>
	<div id="POPUP_CARACTERISTICA" title="Pesquisa Característica">
		<div class="toggle_container">
			<div id="tabs-2" class="block">
				<div class="section" style="padding: 33px;">
					<form name='formPesquisaCaracteristica'>
						<cit:findField formName='formPesquisaCaracteristica' 
						lockupName='LOOKUP_CARACTERISTICA' 
						id='LOOKUP_CARACTERISTICA' top='0' left='0' len='550' heigth='400' 
						javascriptCode='true' 
						htmlCode='true' />
					</form>
				</div>
			</div>
			</div>
	</div>
</html>