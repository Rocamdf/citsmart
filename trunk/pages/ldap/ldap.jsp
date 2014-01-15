<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.LdapDTO"%>


<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
		%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
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
				border:1px solid #ddd;
				padding:4px 10px;
				border-top:none;
				border-left:none;
			}
			
			.outputext.text {
				box-shadow: 0 0 0 0 #DDDDDD inset;
			    font-weight: bold;
			    border: 0px;
			    background: none;
			}
			
			input.text:hover.outputext{
				box-shadow: none !important;
			}
			
			input.text:focus.outputext{
				box-shadow: none !important;
			}
			
			input.text.outputext{
				box-shadow: none !important;
			}
			
		</style>
		
		<script type="text/javascript">
		
			var countAtributo = 0;
		
			function addLinhaTabelaAtributosLdap(id, atributoLdap, valorAtributoLdap){
				var tbl = document.getElementById('tabelaAtributosLdap');
				
				$('#tabelaAtributosLdap').show();
	
				var lastRow = tbl.rows.length;
				
				countAtributo++;

				var row = tbl.insertRow(lastRow);

				var coluna = row.insertCell(0);
				
				coluna.innerHTML = '<input type="hidden" readonly="readonly" class="outputext" id="idAtributo' + countAtributo + '" name="idAtributoLdap" value="' + id + '" />' + atributoLdap;
				
				coluna = row.insertCell(1);
				if(countAtributo == 5){
					coluna.innerHTML = '<input style="width: 100%;  border: 0 none;" type="password" onblur="validarQuantidade(' + countAtributo + ');" id="valorAtributoLdap' + countAtributo + '" name="valorAtributoLdap" value="' + valorAtributoLdap + '"/>'
					+'<input readonly="readonly" style="width: 100%;display: none;" id="quantidadeParametro' + countAtributo + '" name="quantidadeParametro" value="' + valorAtributoLdap.split(";").length + '" />';
				}else{
				coluna.innerHTML = '<input style="width: 100%;  border: 0 none;" type="text" onblur="validarQuantidade(' + countAtributo + ');" id="valorAtributoLdap' + countAtributo + '" name="valorAtributoLdap" value="' + valorAtributoLdap + '"/>'
				+'<input readonly="readonly" style="width: 100%;display: none;" id="quantidadeParametro' + countAtributo + '" name="quantidadeParametro" value="' + valorAtributoLdap.split(";").length + '" />';
				}
				}
			
			function deleteAllRowsTabelaAtributosLdap() {
				var tabela = document.getElementById('tabelaAtributosLdap');
				var count = tabela.rows.length;
		
				while (count > 1) {
					tabela.deleteRow(count - 1);
					count--;
				}
			}
			
			function serializaAtributosLdap() {
				var tabela = document.getElementById('tabelaAtributosLdap');
				var count = countAtributo + 1;
				var listAtributos = [];
				
				for ( var i = 1; i < count; i++) {
					if (document.getElementById('idAtributo' + i) != "" && document.getElementById('idAtributo' + i) != null) {
						var idAtributo = document.getElementById('idAtributo' + i).value;

						var valorAtributo = $('#valorAtributoLdap' + i).val()
						
						var ldapDto = new LdapDTO(idAtributo, valorAtributo);
						
						listAtributos.push(ldapDto);
					}
				}
				document.form.listAtributoLdapSerializado.value = ObjectUtils.serializeObjects(listAtributos);
			}
			
			function LdapDTO(idAtributo, valorAtributo){
				this.idAtributoLdap = idAtributo;
		 		this.valorAtributoLdap = valorAtributo;
		 	}
			
			function gravar(){
				 var campo1 = document.getElementById("valorAtributoLdap1").value.split(";").length;
				 var campo2 = document.getElementById("valorAtributoLdap2").value.split(";").length;
				 var campo3 = document.getElementById("valorAtributoLdap3").value.split(";").length;
				 var campo4 = document.getElementById("valorAtributoLdap4").value.split(";").length;
				 var campo5 = document.getElementById("valorAtributoLdap5").value.split(";").length;
				 var campo6 = document.getElementById("valorAtributoLdap6").value.split(";").length;
				 var campo7 = document.getElementById("valorAtributoLdap7").value.split(";").length;
				 var campo9 = document.getElementById("valorAtributoLdap9").value.split(";").length;
				 var campo10 = document.getElementById("valorAtributoLdap10").value.split(";").length;
				 var campo11 = document.getElementById("valorAtributoLdap11").value.split(";").length;

				if(campo1 == campo2 && campo1 == campo3 && campo1 == campo4 && campo1 == campo5 && campo1 == campo6 && campo1 == campo7  && campo1 == campo9  && campo1 == campo10  && campo1 == campo11){
				 //if(campo1 == campo2 && campo1 == campo3 && campo1 == campo4 && campo1 == campo5 ){
					 if((campo6 == 1 || campo1 == campo6) && (campo7 == 1 || campo1 == campo7) && (campo9 == 1 || campo1 == campo9) && (campo10 == 1 || campo1 == campo10) && (campo11 == 1 || campo1 == campo11)){
						 serializaAtributosLdap();
							document.form.save();
					 }
					 else{
						 alert("<i18n:message key='ldap.quantidadeparametros'/>");
					 }
				 }else{
					 alert("<i18n:message key='ldap.quantidadeparametros'/>");
				 }
			}
			
			function testarConexao() {
				JANELA_AGUARDE_MENU.show();
				document.form.fireEvent("testarConexao");
			}
			
			function atualizar(){
				document.form.fireEvent("load");
			}
			function sincronizaLDAP() {
				if(confirm(i18n_message("ldap.desejaSincronizarLDAP"))) {
					JANELA_AGUARDE_MENU.show();
					document.form.fireEvent("sincronizaLDAP");
				}
			}

			 function validarQuantidade(nomeCampo) {
				 var campoInicial = document.getElementById("valorAtributoLdap1").value.split(";");
				 var quantidade = document.getElementById("valorAtributoLdap"+nomeCampo).value.split(";");
				 
				 var primeiroValor = campoInicial.length;
				 var segundoValor = quantidade.length;
				 if(primeiroValor == segundoValor){
					 document.getElementById("quantidadeParametro"+nomeCampo).style.color="black";
					 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="black";
				 }else{
					 if(nomeCampo != 8  && nomeCampo != 12  && nomeCampo != 13){
						 document.getElementById("quantidadeParametro"+nomeCampo).style.color="red";
						 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="red";
					 }
					 else{
						 var valorCampo = document.getElementById("valorAtributoLdap"+nomeCampo).value.trim();
						 if(valorCampo != "S" && valorCampo != "s" && valorCampo != "N" && valorCampo != "n" && valorCampo != ""){
							 document.getElementById("quantidadeParametro"+nomeCampo).style.color="red";
							 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="red";
							 document.getElementById("quantidadeParametro"+nomeCampo).value="";
							 document.getElementById("valorAtributoLdap"+nomeCampo).value="";
							 alert("Parametro incorreto!");
						 }
						 document.getElementById("quantidadeParametro"+nomeCampo).style.color="black";
						 document.getElementById("valorAtributoLdap"+nomeCampo).style.color="black";
					 }
				 }
				 document.getElementById("quantidadeParametro"+nomeCampo).value = quantidade.length;
			}
		
		</script>
	</head>
	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;"></cit:janelaAguarde>
	<body>	
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="ldap.configuracao"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="ldap.parametrosldap"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">											
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/ldap/ldap' enctype="multipart/form-data">
									<input type="hidden" name="listAtributoLdapSerializado" id="idListAtributoLdapSerializado">
									<div class="columns clearfix">
										<div class="col_100">
											<fieldset>
												<h2><i18n:message key="ldap.atributos"/></h2>
													<table class="table" id="tabelaAtributosLdap" style="width: 100%">
														<tr>
															<th style="width: 40%;"><i18n:message key="ldap.atributo"/></th>
															<th style="width: 100%;"><i18n:message key="ldap.valor"/></th>
															<th style="display: none;">Quantidade de Parametros</th>
														</tr>
													</table>
											</fieldset>
										</div>
										<br>
									</div>
									
									<button id="btnGravar" type='button' name='btnGravar' class="light"  onclick='gravar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button id="btnTestarConexao" type='button' name='btnTestarConexao' class="light" onclick='testarConexao();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="ldap.testarconexao"/></span>
									</button>
									<button id="btnSincronizaLDAP" type='button' name='btnSincronizaLDAP' class="light" onclick='sincronizaLDAP();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="ldap.sincronizaLDAP"/></span>
									</button>	
								</form>
							</div>
						</div>
					</div>
				</div>		
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>