<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.centralit.citcorpore.bean.ServicoContratoDTO"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.util.UtilI18N"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<%@include file="/include/security/security.jsp"%>
	<title>CITSmart</title>
	<%@include file="/include/noCache/noCache.jsp"%>
	<%@include file="/include/header.jsp"%>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/titleComum/titleComum.jsp" %>
	<%@include file="/include/menu/menuConfig.jsp" %>
	<script type="text/javascript" src="../../cit/objects/AcordoServicoContratoDTO.js"></script>
	<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/js/default.js"></script>
	
	<style>
		section {
			padding-left: 18px !important;
			padding-right: 18px !important;
			margin-bottom: 24px !important;
			margin-top: 8px !important;
			/* max-width: 600px !important; */
		}
		section > h3 {
			margin-left: -18px !important;
		}
		h3 {
			color: black !important;
			font-size: 1.2em !important;
			margin-bottom: 0.8em !important;
		}
		.settings-row {
			display: block !important;
			margin: 0.65em 0 !important;
		}
		.tableLess {
		  font-family: arial, helvetica !important;
		  font-size: 10pt !important;
		  cursor: default !important;
		  margin: 0 !important;
		  background: white !important;
		  border-spacing: 0  !important;
		  width: 100%  !important;
		}
		
		.tableLess tbody {
		  background: transparent  !important;
		}
		
		.tableLess * {
		  margin: 0 !important;
		  vertical-align: middle !important;
		  padding: 2px !important;
		}
		
		.tableLess thead th {
		  font-weight: bold  !important;
		  background: #fff url(../../imagens/title-bg.gif) repeat-x left bottom  !important;
		  text-align: center  !important;
		}
		
		.tableLess tbody tr:ACTIVE {
		  background-color: #fff  !important;
		}
		
		.tableLess tbody tr:HOVER {
		  background-color: #e7e9f9 ;
		  /* cursor: pointer; */
		}
		
		.tableLess th {
		  border: 1px solid #BBB  !important;
		  padding: 6px  !important;
		}
		
		.tableLess td{
		  border: 1px solid #BBB  !important;
		  padding: 6px 10px  !important;
		}
		
		.center {
		  	text-align: center  !important;
		}
	</style>
	<script type="text/javascript">
			
		function lancarServicos() {
			checkboxServico = document.getElementsByName('idServicoContrato');
			var count = checkboxServico.length;
			var contadorAux = 0;
			var baselines = new Array();
	
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idServicoContrato' + i);	
				if (!trObj) {
					continue;
				}	
				baselines[contadorAux] = getServico(i);
				contadorAux = contadorAux + 1;
			}
			serializaServico();
		}
		
		serializaServico = function() {
			var checkboxServico = document.getElementsByName('idServicoContrato');
			var count = checkboxServico.length;
			var contadorAux = 0;
			var servicos = new Array();
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idServicoContrato' + i);
				if (!trObj) {
					continue;
				}else if(trObj.checked){
					servicos[contadorAux] = getServico(i);
					contadorAux = contadorAux + 1;
					continue;
				}	
			}
			var servicosSerializadas = ObjectUtils.serializeObjects(servicos);
			document.form.servicosSerializados.value = servicosSerializadas;
			return true;
		}
	
		getServico = function(seq) {
			var acordoServicoContratoDTO = new CIT_AcordoServicoContratoDTO();
			acordoServicoContratoDTO.sequencia = seq;
			acordoServicoContratoDTO.idServicoContrato = eval('document.form.idServicoContrato' + seq + '.value');
			return acordoServicoContratoDTO;
		}
		
		//@author ronnie.lopes
		//Checka o checkbox de acordo com o filtro
		function check(campoBusca) {
			var tabela = document.getElementById('tblServicoContrato');
			var count = tabela.rows.length;
			var campoBuscaFormatada = campoBusca.value.toLowerCase();
			
			//verifica se existe algo digitado no campoBusca(Filtro)
			if (campoBuscaFormatada == "" || campoBuscaFormatada == null) {
				if ($('#todos').is(":checked")) { //verifica se o checkbox 'todos' está marcado
					for ( var i = 1; i < count; i++) {
						$('#idServicoContrato' + i).attr('checked', true); //marca todos elementos
					}
				}else { //se o checkbox 'todos' estiver desmarcado, desmarca todos os elementos
					for ( var i = 1; i < count; i++) {
						$('#idServicoContrato' + i).attr('checked', false);
					}
				}
			}else {
				if ($('#todos').is(":checked")) {
					for ( var i = 1; i < count; i++) {
						var elemento = tabela.rows[i]; //recebe o elemento
						if(elemento.className == 'ativo') { //verifica se o elemento possui Class "ativo"
							$('#idServicoContrato' + i).attr('checked', true); //checka o checkbox do elemento
						}
					}
				}else {
					for ( var i = 1; i < count; i++) {
						var elemento = tabela.rows[i]; //recebe o elemento
						if(elemento.className == 'ativo') { //verifica se o elemento possui Class "ativo"
							$('#idServicoContrato' + i).attr('checked', false); //retira o check do checkbox do elemento
						}
					}
				}
			}
		}
		
		//@author ronnie.lopes
		//Filtra a Tabela pelo Nome do Serviço de acordo com o que digita no campo busca
		function filtroTableAcNivelServ(campoBusca, table) {
			// Recupera valor do campo de busca
			var term = campoBusca.value.toLowerCase();
			if (term != "") {
				// Mostra os TR's que contem o value digitado no campoBusca
				if (table != "") {
					$("#" + table + " tbody>tr").hide();
					$("#" + table + " td").filter(function() {
						return $(this).text().toLowerCase().indexOf(term) > -1
					}).parent("tr").show().addClass("ativo"); //adiciona Class "ativo" nos elementos filtrados
				}
			} else {
				// Quando não há nada digitado, mostra a tabela com todos os dados
				$("#" + table + " tbody>tr").show().removeClass("ativo"); //remove Class "ativo" de todos elementos quando não filtrados
			}
		}

		function fechar() {
			parent.fecharVisao();
		}

		function gravar() {
			JANELA_AGUARDE_MENU.show();
			lancarServicos();
			document.form.save();
		}

		function salvar() {
			if ($('#habilitado').is(":checked")) {
				if (confirm(i18n_message('sla.confirmaVinculo'))) {
					gravar();
				}
			} else {
				gravar();
			}
		}

		function listaServicosRelacionados() {
			$('#habilitado').attr("checked", false);
			document.form.fireEvent("listaServicosRelacionados");
			JANELA_AGUARDE_MENU.show();
			deleteAllRows();
		}

		$(function() {
			JANELA_AGUARDE_MENU.show();
		});

		function deleteAllRows() {
			var tabela = document.getElementById('tblServicoContrato');
			if (tabela != undefined) {
				var count = tabela.rows.length;
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
			}
		}

		function mostra() {
			$("#HAB").css("display", "block");
		}
		function oculta() {
			$("#HAB").css("display", "none");
		}
	</script>
</head>	
<body>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<form name='form' id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/ansServicoContratoRelacionado/ansServicoContratoRelacionado'>	
		<input type="hidden" name="servicosSerializados" id="servicosSerializados"/>
		<input type="hidden" id="idContrato" name="idContrato" value="<%=request.getAttribute("idContrato")%>" />
		<section>
			<section>
			  <h3><i18n:message key="sla.avaliacao.acordo" /></h3>
				<div>
					<select id="idAcordoNivelServico" name="idAcordoNivelServico" onchange="listaServicosRelacionados();"></select>
				</div>
			</section>
			<section id="HAB" style='display:none;'>
			  <h4><input type="checkbox" id="habilitado" name="habilitado" value="S"/><i18n:message key="sla.avaliacao.habilitado" /></h4>
			</section>
			<section style="padding-bottom: 25px !important;">
				<button type='button' name='btnGravar' class="light" onclick='salvar();'> <span><i18n:message key="citcorpore.comum.gravar" /></span></button>
				<button type='button' name='btnFechar' class="light" onclick='fechar();'><span><i18n:message key="citcorpore.comum.fechar" /></span></button>
				<div id="buscaServico" style="float: right;"></div>
			</section>
			<section>
				<div id="relacionarServicos" style="clear: both;"></div>
			</section>
		</section>
	</form>
</body>
</html>