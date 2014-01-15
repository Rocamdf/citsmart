<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ taglib uri="/tags/menuPadrao" prefix="menu"%>
<%
	String iframe = "";
	iframe = request.getParameter("iframe");
%>
<!doctype html public "">
<html>
<head>
<%@include file="/novoLayout/common/include/titulo.jsp"%>
<%@include file="/novoLayout/common/include/libCabecalho.jsp"%>
<link type="text/css" rel="stylesheet"
	href="../../novoLayout/common/include/css/template.css" />
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title=""
	style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
<body>
	<div class="<%=(iframe == null) ? "container-fluid fixed" : ""%>">

		<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
		<div
			class="navbar <%=(iframe == null) ? "main" : "nomain"%> hidden-print">

			<%
				if (iframe == null) {
			%>
			<%@include file="/novoLayout/common/include/cabecalho.jsp"%>
			<%@include file="/novoLayout/common/include/menuPadrao.jsp"%>
			<%
				}
			%>

		</div>

		<div id="wrapper" class="<%=(iframe == null) ? "" : "nowrapper"%>">

			<!-- Inicio conteudo -->
			<div id="content">
				<div class="separator top"></div>
				<div class="row-fluid">
					<div class="innerLR">
						<div class="widget">
							<div class="widget-head">
								<h4 class="heading">
									<i18n:message key="relatorioKpi.titulo" />
								</h4>
							</div>
							<div class="widget-body collapse in">
								<form name="form" id="form"action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/relatorioKpiProdutividade/relatorioKpiProdutividade.load">
									<input type="hidden" id='formatoArquivoRelatorio'name='formatoArquivoRelatorio'>
									<input type="hidden" id='listaUsuarios'name='listaUsuarios'>
									
									<!--Datas-->
									<div class='row-fluid'>
											<div class='span4'>
												<label class="strong campoObrigatorio"><i18n:message key="citcorpore.comum.periodo" /></label>
												<input type="text" class=" span5 citdatepicker" id="dataInicio" name="dataInicio" maxlength="10" required="required" >
												&nbsp;<i18n:message key='citcorpore.comum.a' />&nbsp;
												<input type="text" class=" span5 citdatepicker" id="dataFim" name="dataFim" maxlength="10" required="required" >
											</div>
										</div>
									<!--Contrato-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="campoObrigatorio"><i18n:message key="contrato.contrato" /></label>
													<select id="idContrato" name="idContrato" class="span12 Valid[Required] Description[citcorpore.comum.nome]" onchange="preencherComboUsuariosContrato(this);">
													</select>
												</div>
											</div>
										</div>
									</div>
									<!--Grupo-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="campoObrigatorio"><i18n:message key="suspensaoReativacaoSolicitacao.Grupo" /></label>
													<select id="grupo" name="grupo" class="span12 Valid[Required] Description[citcorpore.comum.nome]" onchange="preencherComboUsuariosGrupo(this);" >
													</select>
												</div>
											</div>
										</div>
									</div>
									
									<!--MostrarFuncionario-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="campoObrigatorio"><i18n:message key="relatorioKpi.SelecionarColaborador" /></label>
													<div class="widget-body uniformjs">
														<label class="radio">
															<input type="radio" class="radio" value="S" name="selecionarColaborador" id="selecionarColaborador" onclick="mostrarFuncionario(this);"/>
															<i18n:message key="citcorpore.comum.sim" />
														</label><br/>
														<label class="radio">
															<input type="radio" class="radio" value="N" checked="checked" name="selecionarColaborador" id="selecionarColaborador" onclick="mostrarFuncionario(this);"/>
															<i18n:message key="citcorpore.comum.nao" />
														</label><br/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<!--Funcionario-->
									<div class='row-fluid' style="display: none;" id="funcionario">
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span2'>
													<label class="campoObrigatorio"><i18n:message key="relatorioKpi.SelecionarColaborador" /></label>
													<select size="8" id="primeiraLista" name="primeiraLista">
													</select>
												</div>
													
												<div class='span2'>
													<div class="separator top"></div>
													<span class="btn btn-block btn-primary" onclick="inserirNaLista();">
														Adicionar
													</span>
													<span class="btn btn-block btn-primary" onclick="RetirarDaLista();">
														Remover
													</span>
													<span class="btn btn-block btn-primary" onclick="inserirTodosDaLista();">
														AdicionarTodos
													</span>
													<span class="btn btn-block btn-primary" onclick="retirarTodosDaLista();">
														RemoverTodos
													</span>
												</div>
												<div class='span3'>
													<label class="campoObrigatorio"><i18n:message key="relatorioKpi.ColaboradoresSelecionados" /></label>
													<select size="8" id="segundaLista" name="segundaLista">
													</select>
												</div>
											</div>
										</div>
									</div>
									
									<!--MostrarSolicitações/Incidente-->
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<label class="campoObrigatorio"><i18n:message key="portal.carrinho.tipoSolicitacao" /></label>
													<div class="widget-body uniformjs">
														<label class="checkbox">
															<input type="checkbox" class="radio" name="checkMostrarIncidentes" value="S" id="checkMostrarIncidentes"/>
															<i18n:message key="requisitosla.incidente" />
														</label>
														<label class="checkbox">
															<input type="checkbox" class="radio" name="checkMostrarRequisicoes" value="S" checked="checked" id="checkMostrarRequisicoes"/>
															<i18n:message key="requisicaoProduto.requisicao" />
														</label><br/>
													</div>
												</div>
											</div>
										</div>
									</div>
									
									<div class="separator top"></div>
									<div class='row-fluid'>
										<div class='span12'>
											<div class='row-fluid'>
												<div class='span6'>
													<button class="btn btn-default btn-primary" type="button" onclick="imprimirRelatorio(this);" id="btnRelatorio" name='btnRelatorio' value="pdf">
														<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/file_pdf.png"style="padding-left: 8px;">
														<span>
															<i18n:message key="citcorpore.comum.gerarRelatorio" />
														</span>
													</button>
								
													<button class="btn btn-default btn-primary" type="button" onclick="imprimirRelatorio(this);" id="btnRelatorio" name='btnRelatorio' value="xls">
														<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png"style="padding-left: 8px;">
														<span>
															<i18n:message key="citcorpore.comum.gerarRelatorio" />
														</span>
													</button>
								
													<button class="btn btn-default btn-primary" type="button" id="btnRelatorio" name='btnRelatorio' onclick='limpar();'>
														<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png"style="padding-left: 8px;">
														<span>
															<i18n:message key="citcorpore.comum.limpar" />
														</span>
													</button>
												</div>
											</div>
										</div>
									</div>
								</form>
							</div>
						</div>
					</div>
				</div>
			</div>
			<!--  Fim conteudo-->
			<%@include file="/novoLayout/common/include/rodape.jsp"%>
		</div>
	</div>
	<script>
		/**
		* @author Bruno.Aquino
		*/
		function validaData(dataInicio, dataFim) {
			var dtInicio = new Date();
			var dtFim = new Date();
			
			dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/") ) ).setFullYear;
			dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/") ) ).setFullYear;
			
			if (dtInicio > dtFim) {
				alert(i18n_message("citcorpore.comum.dataInicioMenorFinal") );
				return false;
			} else
				return true;
		}
		
		function limpar() {
			document.form.clear();
		}
		
		function reportEmpty(){
			alert(i18n_message("citcorpore.comum.relatorioVazio"));
		}
		function mostrarFuncionario(valor){
			if(valor.value=="S"){
				$("#funcionario").show();
			}else{
				$("#funcionario").hide();
			}
		}
		function inserirNaLista(){
			var texto = $("#primeiraLista option:selected").text();
			var valor = $("#primeiraLista option:selected").val();
			if(texto!="" & valor!=""){
				$('#segundaLista').append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#primeiraLista option:selected').remove();
			}
		}
		
		function RetirarDaLista(){
			var texto = $("#segundaLista option:selected").text();
			var valor = $("#segundaLista option:selected").val();
			if(texto!="" & valor!=""){
				$('#primeiraLista').append("<option value='"+valor+"' selected='selected'>"+texto+"</option>");
				$('#segundaLista option:selected').remove();
			}
		}
		
		function inserirTodosDaLista(){
			$("#primeiraLista > option").each(function(i){
				$('#segundaLista').append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });			
		}
		
		function retirarTodosDaLista(){
			$("#segundaLista > option").each(function(i){
				$('#primeiraLista').append("<option value='"+$(this).val()+"'>"+$(this).text()+"</option>");
				$(this).remove();
		    });	
		}
		
		function preencherComboUsuariosContrato(opcao){
			if(opcao.value!=""){
				document.form.fireEvent("preencherComboUsuariosPorContrato");
			}
			
		}
		
		function preencherComboUsuariosGrupo(opcao){
			if(opcao.value!=""){
				document.form.fireEvent("preencherComboUsuariosPorGrupo");
			}
			
		}
		
		function imprimirRelatorio(formato) {
			var dataInicio = document.getElementById("dataInicio").value;
			var dataFim = document.getElementById("dataFim").value;
			var contrato = document.getElementById("idContrato").value;
			var incidente = document.getElementById("checkMostrarIncidentes");
			var requisisao = document.getElementById("checkMostrarRequisicoes");
			
			if(incidente.checked == false && requisisao.checked ==false){
				alert("Preencha Um dos campos Tipo de Solicitação");
				return false;
			}
			
			if (contrato == ""){
				alert(i18n_message("solicitacaoservico.validacao.contrato"));
				document.getElementById("contrato").value = '';
				return false;	
			}
			if (dataInicio == ""){
				alert(i18n_message("citcorpore.comum.validacao.datainicio"));
				document.getElementById("dataInicio").value = '';
				return false;	
			}
			if (dataFim == ""){
				alert(i18n_message("citcorpore.comum.validacao.datafim"));
				document.getElementById("dataFim").value = '';
				return false;					
			}
			if (validaData(dataInicio,dataFim) ) {
				JANELA_AGUARDE_MENU.show();
				var listaUsuarios = "";
				$("#segundaLista > option").each(function(i){
					listaUsuarios+=this.value+";"
			    });
				if(listaUsuarios!=""){
					document.form.listaUsuarios.value = listaUsuarios;
				}
				document.form.formatoArquivoRelatorio.value = formato.value;
				document.form.fireEvent("imprimirRelatorio");					
			}
		}
  	</script>
</body>
</html>