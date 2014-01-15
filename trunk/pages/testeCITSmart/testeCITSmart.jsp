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
	
	String PAGE_CADADTRO_SOLICITACAOSERVICO = ParametroUtil.getValorParametroCitSmartHashMap(Enumerados.ParametroSistema.PAGE_CADADTRO_SOLICITACAOSERVICO, "");
	
	if (PAGE_CADADTRO_SOLICITACAOSERVICO == null){
	    PAGE_CADADTRO_SOLICITACAOSERVICO = "";
	}	    
	PAGE_CADADTRO_SOLICITACAOSERVICO = PAGE_CADADTRO_SOLICITACAOSERVICO.trim();   
	if (PAGE_CADADTRO_SOLICITACAOSERVICO.trim().equalsIgnoreCase("")){
	    PAGE_CADADTRO_SOLICITACAOSERVICO = "/pages/solicitacaoServico/solicitacaoServico.load";
	}
%>
    <%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	<script>
	
	$(function() {
		$("#POPUP_VISUALIZARSOLICITACAO").dialog({
			autoOpen : false,
			width : 1205,
			height : 1000,
			modal : true
		});
	});
	  
	testCadUsuario = function(){
		document.form.fireEvent("testCadUsuario");
	}
	testCadEmpregado = function(){
		document.form.fireEvent("testCadEmpregado");
	}
	testCadIncidente = function(){
		document.form.fireEvent("testCadIncidente");
	}	
	testCadOcorrencia = function(){
		document.form.fireEvent("testCadOcorrencia");
	}
	testCadContato = function(){
		document.form.fireEvent("testCadContato");
	}
	testCadBaseConhecimento = function(){
		document.form.fireEvent("testCadBaseConhecimento");
	}
	testCadContrato = function(){
		document.form.fireEvent("testCadContrato");
	}
	testCadMultiContratos = function(){
		document.form.fireEvent("testCadMultiContratos");
	}
	testAtividadePeriodica = function (){
		document.form.fireEvent("testAtividadePeriodica");
	}
	testCapturarTarefa = function(){
		document.form.fireEvent("testCapturarTarefa");
	}
	testDelegacaoTarefa = function(){
		document.form.fireEvent("testDelegacaoTarefa");
	}
	testExecutarTarefa = function(){
		document.form.fireEvent("testExecutarTarefa");
	}
	testIniciaTarefa = function(){
		document.form.fireEvent("testIniciaTarefa");
	}
	testMudancaSLA = function(){
		document.form.fireEvent("testMudancaSLA");
	}
	testSuspenderSolicitacao = function(){
		document.form.fireEvent("testSuspenderSolicitacao");
	}
	testReativaSolicitacao = function(){
		document.form.fireEvent("testReativaSolicitacao");
	}
	testReclassificaSolicitacao = function(){
		document.form.fireEvent("testReclassificaSolicitacao");
	}
	testAnexo = function(){
		document.form.fireEvent("testAnexo");
	}
	testAtribuidaCompartilhada = function(){
		document.form.fireEvent("testAtribuidaCompartilhada");
	}
	testRegistroExecucao = function(){
		document.form.fireEvent("testRegistroExecucao");
	}
	testBusca = function(){
		document.form.fireEvent("testBusca");
	}
	testProblemas = function(){
		document.form.fireEvent("testProblemas");
	}
	testSubSolicitacoes = function(){
		document.form.fireEvent("testSubSolicitacoes");
	}
	testMudancas = function(){
		document.form.fireEvent("testMudancas");
	}
	testVerificacaoEmail  = function(){
		document.form.fireEvent("testVerificacaoEmail");
	}
	testCadCliente = function(){
		document.form.fireEvent("testCadCliente");
	}
	testCadFornecedor = function(){
		document.form.fireEvent("testCadFornecedor");
	}
	testCadMenu = function(){
		document.form.fireEvent("testCadMenu");
	}
	testCadMarca = function(){
		document.form.fireEvent("testCadMarca");
	}
	testCadProduto = function(){
		document.form.fireEvent("testCadProduto");
	}
	testCadEmpresa = function(){
		document.form.fireEvent("testCadEmpresa");
	}
	testCadGrupo = function(){
		document.form.fireEvent("testCadGrupo");
	}
	testCadCargos = function(){
		document.form.fireEvent("testCadCargos");
	}
	testCadUnidade = function(){
		document.form.fireEvent("testCadUnidade");
	}
	testCadJornadaTrabalho = function(){
		document.form.fireEvent("testCadJornadaTrabalho");
	}
	testCadLocalidade = function(){
		document.form.fireEvent("testCadLocalidade");
	}
	testCadPerfilAcesso = function(){
		document.form.fireEvent("testCadPerfilAcesso");
	}
	testCadCalendario = function(){
		document.form.fireEvent("testCadCalendario");
	}
	testCadTipoUnidade = function(){
		document.form.fireEvent("testCadTipoUnidade");
	}
	testCadComando = function(){
		document.form.fireEvent("testCadComando");
	}
	testCadSistemaOperacional = function(){
		document.form.fireEvent("testCadSistemaOperacional");
	}
	testCadComandoSistemaOperacional = function(){
		document.form.fireEvent("testCadComandoSistemaOperacional");
	}
	testCadTipoEventoServico = function(){
		document.form.fireEvent("testCadTipoEventoServico");
	}
	testCadJustificativaFalhas = function(){
		document.form.fireEvent("testCadJustificativaFalhas");
	}
	testCadImportanciaNegocio = function(){
		document.form.fireEvent("testCadImportanciaNegocio");
	}
	testCadPrioridade = function(){
		document.form.fireEvent("testCadPrioridade");
	}
	testCadCausaIncidente = function(){
		document.form.fireEvent("testCadCausaIncidente");
	}
	testCadGrupoAtvPeriodica = function(){
		document.form.fireEvent("testCadGrupoAtvPeriodica");
	}
	testCadModeloEmail = function(){
		document.form.fireEvent("testCadModeloEmail");
	}
	testCadCategoriaSolucao = function(){
		document.form.fireEvent("testCadCategoriaSolucao");
	}
	testCadCategoriaPost = function(){
		document.form.fireEvent("testCadCategoriaPost");
	}
	testCadAlcadaCentroResultado = function(){
		document.form.fireEvent("testCadAlcadaCentroResultado");
	}
	testCadCategoriaProduto = function(){
		document.form.fireEvent("testCadCategoriaProduto");
	}
	testCadAlcadaCentroResultado = function(){
		document.form.fireEvent("testCadAlcadaCentroResultado");
	}
	testCadCondicaoOperacao = function(){
		document.form.fireEvent("testCadCondicaoOperacao");
	}
	testCadJustificativaParecer = function(){
		document.form.fireEvent("testCadJustificativaParecer");
	}
	testCadUnidadeMedida = function(){
		document.form.fireEvent("testCadUnidadeMedida");
	}
	testCadRequisicaoProduto = function(){
		document.form.fireEvent("testCadRequisicaoProduto");
	}
	testCadCentroResultado = function(){
		document.form.fireEvent("testCadCentroResultado");
	}
	testCadPasta = function(){
		document.form.fireEvent("testCadPasta");
	}
	testCadPalavraGemea = function(){
		document.form.fireEvent("testCadPalavraGemea");
	}
	testCadCatalogoServico = function(){
		document.form.fireEvent("testCadCatalogoServico");
	}
	testCadTipoServico = function(){
		document.form.fireEvent("testCadTipoServico");
	}
	testCadCategoriaServico = function(){
		document.form.fireEvent("testCadCategoriaServico");
	}
	testCadItemConfiguracao = function(){
		document.form.fireEvent("testCadItemConfiguracao");
	}
	testCadTipoItemConfiguracao = function(){
		document.form.fireEvent("testCadTipoItemConfiguracao");
	}
	testCadCaracteristica = function(){
		document.form.fireEvent("testCadCaracteristica");
	}
	testCadSoftwareInsDes = function(){
		document.form.fireEvent("testCadSoftwareInsDes");
	}
	testCadEvento = function(){
		document.form.fireEvent("testCadEvento");
	}
	testPesquisaSolicitacao = function(){
		document.form.fireEvent("testPesquisaSolicitacao");
	}

	
	testVisualizar = function(idSolicitacao) {
		document.getElementById('fraSolicitacaoServico').src = "about:blank";
		document.getElementById('fraSolicitacaoServico').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%><%=PAGE_CADADTRO_SOLICITACAOSERVICO%>?idSolicitacaoServico="+idSolicitacao+"&escalar=N&alterarSituacao=N&editar=N";
		$("#POPUP_VISUALIZARSOLICITACAO").dialog("open");
	};
	
	function resize_iframe()
	{
		var height=window.innerWidth;//Firefox
		if (document.body.clientHeight)
		{
			height=document.body.clientHeight;//IE
		}
		document.getElementById("fraSolicitacaoServico").style.height=parseInt(height - document.getElementById("fraSolicitacaoServico").offsetTop-8)+"px";
	}
	
	
	</script>
	
<%
		//se for chamado por iframe deixa apenas a parte de cadastro da pÃ¡gina
		if (iframe != null) {
	%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
}
</style>

<%
	}
%>
</head>
<body>	
<div id="wrapper">
	<%
		if (iframe == null) {
	%>
	<%@include file="/include/menu_vertical.jsp"%>
	<%
		}
	%>

<!-- Conteudo -->
 <div id="main_container" class="main_container container_16 clearfix">
		<%
			if (iframe == null) {
		%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>
					
		<div class="flat_area grid_16">
				<h2>Teste CITSmart</h2>						
		</div>
		<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/testeCITSmart/testeCITSmart'>
		
			<button type='button' name='btnGravar' class="light" onclick="testCadUsuario();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro de Usuário</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadEmpregado();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro de Empregado</span>
			</button>
			
						<button type='button' name='btnGravar' class="light" onclick="testCadContato();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro de Contato</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadIncidente();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro de Incidente</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadOcorrencia();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro de Ocorrência</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadBaseConhecimento();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro Base de Conhecimento</span>
			</button>

			<button type='button' name='btnGravar' class="light" onclick="testCadContrato();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro de Contrato</span>
			</button>
			<button type='button' name='btnGravar' class="light" onclick="testCadMultiContratos();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Cadastro de MultiContratos</span>
			</button>
				
			<button type='button' name='btnGravar' class="light" onclick="testAtividadePeriodica();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Agendamento de Atividades</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCapturarTarefa();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Captura de Tarefa</span>	
			</button>	
				
			<button type='button' name='btnGravar' class="light" onclick="testDelegacaoTarefa();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Delegação de Tarefas</span>
			</button>	
				
			<button type='button' name='btnGravar' class="light" onclick="testMudancaSLA();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Mudança de SLA</span>
			</button>	
				
			<button type='button' name='btnGravar' class="light" onclick="testExecutarTarefa();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Execução de Tarefa</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testIniciaTarefa();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Inicialização de Tarefa</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testSuspenderSolicitacao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste Suspensão de Solicitação</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testReativaSolicitacao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste Reativação de Solicitação</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testBusca();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste Busca Contrato/Solicitação</span>
			</button>
				
			<button type='button' name='btnGravar' class="light" onclick="testReclassificaSolicitacao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Reclassificação</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testRegistroExecucao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Registro de Execução</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testAtribuidaCompartilhada();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste Atribuida/Compartilhada</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testAnexo();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Anexo</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testProblemas();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste Criação de Problemas</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testSubSolicitacoes();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste Criação de Sub-Solicitações</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testMudancas();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste Criação de Requisição de Mudança</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testVerificacaoEmail();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Verificação de Email</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCliente();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Clientes</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadFornecedor();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Fornecedores</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadMenu();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Menus</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadMarca();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Marcas</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadProduto();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Produtos</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadEmpresa();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Empresas</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadGrupo();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Grupos</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCargos();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Cargos</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadUnidade();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Unidades</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadJornadaTrabalho();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Jornada de Trabalho</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadLocalidade();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Localidades</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadPerfilAcesso();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Perfil de Acesso</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCalendario();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Calendario</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadTipoUnidade();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Tipo de Unidade</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadComando();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Comandos</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadSistemaOperacional();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Sistemas Operacionais</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadComandoSistemaOperacional();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Comandos de Sistema Operacional</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadPrioridade();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Prioridadess</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadTipoEventoServico();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Tipo de Evento de Serviço</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadJustificativaFalhas();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Justificativa de Falhas</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadImportanciaNegocio();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Importancia Negocio</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCausaIncidente();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Causa Incidente</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadGrupoAtvPeriodica();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Grupo Atividade Periodica</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadModeloEmail();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Modelo de Email</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCategoriaSolucao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Categoria Solução</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCategoriaPost();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Categoria de Postagem</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadAlcadaCentroResultado();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Alcada Centro de Resultado</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCentroResultado();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Centro Resultado</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadUnidadeMedida();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Unidade de Medida</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadJustificativaParecer();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Justificativa Parecer</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCondicaoOperacao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Condição Operação</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCategoriaProduto();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Categoria de Produto</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadRequisicaoProduto();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Requisição Produto</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadPasta();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Pastas</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadPalavraGemea();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Palavra Gêmea</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCatalogoServico();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Catalogo Serviço</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadTipoServico();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Tipo Serviço</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCategoriaServico();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Categoria Serviço</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadCaracteristica();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Caracteristica</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadItemConfiguracao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Item Configuração</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadTipoItemConfiguracao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Tipo Item Configuração</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadSoftwareInsDes();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Software Inst/Des</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testCadEvento();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Criação de Evento</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testVisualizar(9);">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Visualização</span>
			</button>
			
			<button type='button' name='btnGravar' class="light" onclick="testPesquisaSolicitacao();">
				<img
					src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				<span>Efetuar Teste de Pesquisa de Solicitação</span>
			</button>
			
			
		
		<div id="POPUP_VISUALIZARSOLICITACAO"  style="overflow: hidden;">
			<div class="box grid_16 tabs" >
				<div class="toggle_container" >
					<div id="tabs-2" class="block" style="overflow: hidden;">
						<div class="section" style="overflow: hidden;">
							<iframe id="fraSolicitacaoServico" style="display: block; margin-left: -20px;" name="fraSolicitacaoServico" width="1000" height="900" >
							</iframe>
						</div>
					</div>
				</div>
			</div>
		</div>
									
		</form>
 </div>
<!-- Fim da Pagina de Conteudo -->
</div>
		<%@include file="/include/footer.jsp"%>
</body>
</html>
