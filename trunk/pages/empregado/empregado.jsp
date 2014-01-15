<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.EmpregadoDTO"%>
<!doctype html public "">
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	String iframe = "";
	String idContrato = "";
	iframe = request.getParameter("iframe");
	if (iframe == null){
	    iframe = "false";  
	}
%>
<%@include file="/include/security/security.jsp" %>
	<title><i18n:message key="citcorpore.comum.title"/></title>
	<%@include file="/include/noCache/noCache.jsp" %>
	<%@include file="/include/header.jsp" %>
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<script  charset="ISO-8859-1" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
	<script  charset="ISO-8859-1" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	
	<script>
	
	 var popup;
	 var popup2;
	 var popup3;
	    addEvent(window, "load", load, false);
	    function load(){		
			popup = new PopupManager(1000, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popup2 = new PopupManager(1000, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			popup3 = new PopupManager(1000, 600, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
			converteCpfParaPadraoIngles();
			document.form.afterRestore = function () {
				$('.tabs').tabs('select', 0);
			}
	    }	
	    
		function verificaValor(obj){
			if (obj.options[obj.selectedIndex].value == 'E'){
				$('valorSalario').innerHTML = 'Valor Sal&atilde;rio CLT:';
			}else if (obj.options[obj.selectedIndex].value == 'S'){
				$('valorSalario').innerHTML = 'Valor Est&atilde;gio:';
			}else if (obj.options[obj.selectedIndex].value == 'P'){
				$('valorSalario').innerHTML = 'Valor Contratado Mensal:';
			}else if (obj.options[obj.selectedIndex].value == 'X'){
				$('valorSalario').innerHTML = 'Valor do Prolabore:';
			}else{
				$('valorSalario').innerHTML = 'Valor Mensal:';
			}
		}
		/*Bruno.Aquino : Foi retirado o class de mascaras do campo cpf e inserido ao carregar a página a mascara do jquery para as linguas: Português e espanhol, para inglês não haverá mascara */
		function converteCpfParaPadraoIngles(){
			if('<%=request.getSession().getAttribute("locale")%>'=="en"){	
				$("#cpf").unmask();
			}else{
				$("#cpf").mask("999.999.999-99");
			}
	    }
		function LOOKUP_EMPREGADO_select(id,desc){
			document.form.restore({idEmpregado:id});
		}
		
		$(function() {
			   $('.datepicker').datepicker();
			   $('#telefone').mask('(999) 9999-9999');
		  });
		
		function ocultarDivGruposContrato(){
			$('#gruposContrato').hide();
		}
		
		function exibirDivGruposContrato(){
			$('#gruposContrato').show();
		}
	
	function excluir() {
		if (document.getElementById("idEmpregado").value != "") {
			if (confirm(i18n_message("citcorpore.comum.deleta"))) {
				document.form.fireEvent("delete");
			}
		}
	}
	
	function gravar(){
		if(!validaDatas()){
			return;
		}
		document.form.save();
			
	}

	function validaDatas(){
		if(!nullOrEmpty(gebi("dataNasc"))){
			if(!nullOrEmpty(gebi("dataEmissaoRg"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataEmissaoRg, i18n_message("citcorpore.comum.validacao.datargmenordatanasc"))){
					return false;
				} 
			} 	
			if(!nullOrEmpty(gebi("dataEm"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataEm, i18n_message("citcorpore.comum.validacao.emissaoctpsmenornasci"))){
					return false;
				}
			}
			if(!nullOrEmpty(gebi("dataAdmissao"))){
				if (!DateTimeUtil.comparaDatas(document.form.dataNasc, document.form.dataAdmissao, i18n_message("citcorpore.comum.validacao.admissaomenornascimento"))){
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * getElementById
	 */
	function gebi(id){
		return document.getElementById(id);
	}

	function nullOrEmpty(comp){
		return comp.value == null || comp.value == "" ? true : false;		
	}

	<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Criação de função para não digitar numeros, para retirar bug de não poder usar setas do teclado . -->
	function naoDigitarNumeros(e){
		 var tecla=(window.event)?event.keyCode:e.which;
		 if((tecla>47 && tecla<58)) 
			 return false;
		 else{
			 if (tecla==8 || tecla==0) return true;
			 	else  
			 		return true;
		 }
	}
	
</script>
<%//se for chamado por iframe deixa apenas a parte de cadastro da pagina
			if (iframe != null && iframe.equalsIgnoreCase("true")) {%>
<style>
	div#main_container {
		margin: 10px 10px 10px 10px;
	}

</style>
<%}%>
</head>
<body>	
	<div id="">
		<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
		<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
		<%}%>
				
				
				
		<div class="flat_area grid_16">
				<h2><i18n:message key="colaborador.colaborador"/></h2>						
		</div>
		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1"><i18n:message key="colaborador.cadastroColaborador"/></a>
				</li>
				<%if (!iframe.equalsIgnoreCase("true")){%>
				<li>
					<a href="#tabs-2" class="round_top"><i18n:message key="colaborador.pesquisacolaborador"/></a>
				</li>
				<%}%>
			</ul>				
	<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">											
							<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/empregado/empregado'>
							<div class="columns clearfix">
								<input type='hidden' id="idEmpregado" name='idEmpregado'/>	
								<input type='hidden' name='dataFim'/>
								<input type='hidden' name='iframe' value="<%=iframe%>"/>
								<input type='hidden' name='idContrato'/>	
								<div class="col_50">				
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.nome"/></label>
											<div>
												<!-- Thiago Fernandes - 23/10/2013 14:06 - Sol. 121468 - Criação de função para não digitar numeros, para retirar bug de não poder usar setas do teclado . -->
												<input type='text' onkeypress="return naoDigitarNumeros(event);" name="nome" maxlength="70" class="Valid[Required] Description[citcorpore.comum.nome]"  />
												
											</div>
									</fieldset>
								</div>
								<div  class="col_25">
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="colaborador.tipoColaborador"/></label>
											<div style="height: 35px;">
												<select name='tipo' id="tipo"  class="Valid[Required] Description[colaborador.tipoColaborador]" onchange='verificaValor(this)'>
												</select>
											</div>
									</fieldset>
								</div>
								<div  class="col_25">
								<fieldset>
									<label class="campoObrigatorio"><i18n:message key="colaborador.situacao"/></label>
										<div style="height: 35px;">
											<select name='idSituacaoFuncional' class="Valid[Required] Description[colaborador.situacao]"></select>						
										</div>
								</fieldset>
								</div>
							<div class="col_100">
								<div class="col_50">				
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.email"/></label>
											<div style="height: 39px;">
												<input maxlength="200" type='text' name="email" onblur="ValidacaoUtils.validaEmail(email, '');"  class="Valid[Required] Description[citcorpore.comum.email]" />
											</div>
									</fieldset>
								</div>
							
									<div class="col_25">				
									<fieldset>
										<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.telefone"/></label>
											<div style="height: 39px;">
												<input maxlength="11" type='text' id="telefone" name="telefone"  class="Valid[Required] Description[citcorpore.comum.telefone]" />
											</div>
									</fieldset>
									</div>
									<div class="col_25">
									<fieldset>
										<label><i18n:message key="citcorpore.comum.ramal"/></label>
											<div style="height: 39px;">
												<input maxlength="4" type='text' id="ramal" name="ramal" class="Format[Numero]" />
											</div>
									</fieldset>
									</div>
								</div>
								  <div class='col_50'>
								  <fieldset >
									<label class="tooltip_bottom campoObrigatorio" title="<i18n:message key="unidade.unidade"/>" >
										<i18n:message key="unidade.unidade"/>
										<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
											onclick="popup2.abrePopup('unidade', 'preencherComboUnidade')">	
											<%}%>
									 </label>
										<div>
											<select id='idUnidade' name='idUnidade' class="Valid[Required] Description[unidade.unidade]"></select>
										</div>
								    </fieldset>
								  </div>
								  <div class='col_50'>
									  <fieldset >
											<label class="tooltip_bottom campoObrigatorio" title="<i18n:message key="cargos.cargos"/>" >
												<i18n:message key="cargos.cargos"/>
												<%if (iframe == null || iframe.equalsIgnoreCase("false")) {%>
												<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"
													onclick="popup3.abrePopup('cargos', 'preencherComboCargos')">
													<%}%>
										 	</label>
											<div>
												<select id='idCargo' name='idCargo' class="Valid[Required] Description[cargos.cargos]"></select>
											</div>
									    </fieldset>
								  </div>
								  </div>  
								  <div id="gruposContrato" class="col_100">
										<div class='col_100'>
											<fieldset >
								  				<label title="Grupo" ><i18n:message key="grupo.grupo"/></label>
								  				<div>
													<select id='idGrupo' name='idGrupo'></select>
								  				</div>
											</fieldset>
										</div>
								  </div>
							
							<div id="divOutrasInformacoes" style="display: block;">     
							
							  	            	
						       <h2 id="tituloInformacaoPagamento" class="section"><i18n:message key="colaborador.informacaoPagamento"/></h2>
						       <div id="informacaoPagamento" class="columns clearfix">
						        <div class="col_33">  
						        <fieldset>
									<label ><i18n:message key="colaborador.valorSalario"/>(<i18n:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input id="valorSalario" type="text"  maxlength="15" name='valorSalario' class="Description[colaborador.valorSalario] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label ><i18n:message key="colaborador.valorProdutividadeMedia"/>(<i18n:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorProdutividadeMedia' class=" Format[Moeda] Description[colaborador.valorProdutividadeMedia]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
						        <fieldset>
									<label ><i18n:message key="colaborador.valorPlanoSaude"/>(<i18n:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorPlanoSaudeMedia' class=" Description[colaborador.valorPlanoSaude] Format[Moeda]" />
										</div>
								</fieldset>
								</div>	
								</div>
								<div class="columns clearfix">        
								<div class="col_33">
								<fieldset>
									<label ><i18n:message key="colaborador.valorValeTransporte"/>(<i18n:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorVTraMedia' class=" Description[colaborador.valorValeTransporte] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label ><i18n:message key="colaborador.valorValeRefeicaoAlim"/>(<i18n:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  maxlength="15" name='valorVRefMedia' class=" Description[colaborador.valorValeRefeicaoAlim] Format[Moeda]"/>
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label><i18n:message key="colaborador.custoHora"/>(<i18n:message key="citcorpore.comum.simboloMonetario"/>)</label>
										<div>
											<input type='text'  name="custoPorHora" maxlength="10" readonly="readonly" size="10" class="Format[Moeda]" />					
										</div>
								</fieldset>
								</div>
								</div>
								<div class="columns clearfix"> 
								<div class="col_33">
								<fieldset>
									<label><i18n:message key="colaborador.custoTotalMensal"/></label>
										<div>
											<input type='text'  name="custoTotalMes" maxlength="15" readonly="readonly" size="15" class="Format[Moeda]"/>					
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label><i18n:message key="colaborador.agencia"/></label>
										<div>
											<input type='text' name="agencia" maxlength="10" size="10"/>				
										</div>
								</fieldset>
								</div>
								<div class="col_33">
								<fieldset>
									<label><i18n:message key="colaborador.conta"/></label>
										<div>
											<input type='text' name="contaSalario" maxlength="20" size="20" />
										</div>
								</fieldset>
								</div>
								</div>
								<h2 class="section"><i18n:message key="colaborador.dadosPessoais"/></h2>         	
								 <div class="columns clearfix">          
								  <div class="col_33">
								  <fieldset>
										<label ><i18n:message key="colaborador.cpf"/></label>
											<div>
												<input type='text' id="cpf" name="cpf" maxlength="14" size="15" class=" Description[colaborador.cpf]" />
												
											</div>
									</fieldset>		  	
									</div>
									<div class="col_33">
										<fieldset>
										<label ><i18n:message key="colaborador.dataNascimento"/></label>
											<div>
												<input  type='text'  id="dataNasc"  name="dataNascimento" maxlength="10" size="10"  class="Valid[Data] Description[colaborador.dataNascimento] Format[Data] datepicker" />						
											</div>				
										</fieldset>				
									</div>
									<div class="col_33">
									<fieldset>
										<label ><i18n:message key="colaborador.sexo"/></label>
											<div class="inline clearfix">
												<label><input type='radio' id="sexoMasculino" name="sexo" value="M" class="  Description[colaborador.sexo]" />
													<i18n:message key="colaborador.masculino"/>
												</label>
												<label><input type='radio' id="sexoFeminino" name="sexo" value="F" class="  Description[colaborador.sexo]" />
													<i18n:message key="colaborador.feminino"/>
												</label>						
											</div>
									</fieldset>
								 </div>		 
								</div>
								<div class="columns clearfix">          
								  	<div class="col_33">
									<fieldset>
										<label ><i18n:message key="colaborador.rg"/></label>
											<div>
												<input type='text' name="rg" maxlength="15" size="15" />						
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label><i18n:message key="colaborador.dataEmissaoRg"/></label>
											<div>
												<input type='text' id="dataEmissaoRg" name="dataEmissaoRG" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataEmissaoRg] Format[Data] datepicker"/>						
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset>
										<label><i18n:message key="colaborador.orgaoExpedidor"/></label>
											<div>
												<input type='text' name="orgExpedidor" maxlength="10" size="10" />						
											</div>
									</fieldset>
									</div>
									<div class="col_16">
									<fieldset>
										<label><i18n:message key="colaborador.ufExpedidor"/></label>
											<div>
												<select name='idUFOrgExpedidor'></select>					
											</div>
									</fieldset>
									</div>
								</div> 
								<div class="columns clearfix">          
								  	<div class="col_33">
									<fieldset>
										<label><i18n:message key="colaborador.numeroCtps"/></label>
											<div>
												<input type='text' name="ctpsNumero" maxlength="15" size="15" class="Description[colaborador.numeroCtps]"/>						
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label><i18n:message key="colaborador.dataEmissaoCtps"/></label>
											<div>
												<input type='text' id="dataEm" name="ctpsDataEmissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataEmissaoCtps] Format[Data] datepicker"/>						
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset>
										<label><i18n:message key="colaborador.serieCtps"/></label>
											<div>
												<input type='text' id="ctpsSerie" name="ctpsSerie" maxlength="10" size="10"  class="Description[colaborador.serieCtps]"/>						
											</div>
									</fieldset>
									</div>
									<div class="col_16">
									<fieldset>
										<label><i18n:message key="colaborador.ufCtps"/></label>
											<div>
												<select name='ctpsIdUf' class="Description[colaborador.ufCtps]"></select>					
											</div>
									</fieldset>
									</div>
								</div>
								<div class="columns clearfix">          
								  	<div class="col_33">
									<fieldset>
										<label><i18n:message key="colaborador.nit"/></label>
											<div>
							       				<input type='text' name="nit" maxlength="20" size="20" class="Description[colaborador.nit]"/>												
											</div>
									</fieldset>
									</div>
									<div class="col_33">
									<fieldset>
										<label><i18n:message key="colaborador.dataAdmissao"/></label>
											<div>
							       				<input type='text' id="dataAdmissao" name="dataAdmissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataAdmissao] Format[Data] datepicker"/>											
											</div>
									</fieldset>
								 </div>
								 <div class="col_16">
									<fieldset>
										<label><i18n:message key="colaborador.dataDesligamento"/></label>
											<div>
												<input type='text' id="dataDemissao" name="dataDemissao" maxlength="10" size="10" class="Valid[Data] Description[colaborador.dataDesligamento] Format[Data] datepicker"/>				
											</div>				
									</fieldset>
									</div>
	
								</div>
								<div class="columns clearfix">          
								  <div class="col_66">
										<fieldset>
											<label><i18n:message key="colaborador.conjuge"/></label>
												<div>
								       				<input type='text' name="conjuge" maxlength="50" size="50"/>												
												</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label><i18n:message key="colaborador.estadoCivil"/></label>
												<div>
								       				<select name='estadoCivil'></select>												
												</div>
										</fieldset>
									</div>         	
								</div>
							    <div class="columns clearfix">          
								  <div class="col_50">
										<fieldset>
											<label><i18n:message key="colaborador.pai"/></label>
												<div>
								       				<input type='text' name="pai" maxlength="50" size="50"/>												
												</div>
										</fieldset>
									</div>
									<div class="col_50">
										<fieldset>
											<label><i18n:message key="colaborador.mae"/></label>
												<div>
								       				<input type='text' name="mae" maxlength="50" size="50"/>												
												</div>
										</fieldset>
									</div>         	
								</div>
								<div class="columns clearfix">          
								  <div class="col_50">
										<fieldset>
											<label><i18n:message key="colaborador.observacao"/></label>
												<div>
								       				<textarea name="observacoes" cols='70' maxlength ="2000" rows='5'></textarea>												
												</div>
										</fieldset>
									</div> 	
								</div>
<!-- 								<div class="columns clearfix" id='divListaContratos' style='display: none'>           -->
<!-- 								  <div class="col_100"> -->
<!-- 										<fieldset id='fldListaContratos'> -->
<!-- 										</fieldset> -->
<!-- 									</div> 	 -->
<!-- 								</div>								 -->
							</div>
							<div id="popupCadastroRapido">
							     <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
						    </div>	
							<br><br>
							<fieldset>
							<div>
								<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
									<span><i18n:message key="citcorpore.comum.gravar"/></span>
								</button>
								<%if (!iframe.equalsIgnoreCase("true")){%>
								<button type="button" name='btnLimpar' class="light" onclick='document.form.clear();'>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
									<span><i18n:message key="citcorpore.comum.limpar"/></span>
								</button>
								<button type='button' name='btnUpDate' class="light" onclick='excluir();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/trashcan.png">
										<span><i18n:message key="citcorpore.comum.excluir" /></span>
								</button>					         
								<%}%>
							</div>
							</fieldset>
						</form>
						</div>	
					</div>
				</div>
					<%if (!iframe.equalsIgnoreCase("true")){%>
					<div id="tabs-2" class="block">
						<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
							</form>
						</div>
					</div>		
					<%}%>						
				</div>
			</div>		
		</div>
	</div>
</body>
<%//se for chamado por iframe deixa apenas a parte de cadastro da pagina
	if (iframe != null && iframe.equalsIgnoreCase("true")) {%>
		<script>
			document.getElementById("divOutrasInformacoes").style.display = "none";
		</script>
<%}%>
<%@include file="/include/footer.jsp"%>
</html>
