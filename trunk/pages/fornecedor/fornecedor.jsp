<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<%@ page import="br.com.centralit.citcorpore.util.WebUtil" %>
<%@ page import="br.com.centralit.citcorpore.bean.FornecedorDTO" %>
<%@ page import="br.com.centralit.citcorpore.bean.EnderecoDTO" %>

<!doctype html public "">
<html>
	<head>
		<%			
    		response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);
			
			String iframe = "";
			iframe = request.getParameter("iframe");
		%>
		
		<%@ include file="/include/security/security.jsp" %>
		
		<title>
			<i18n:message key="citcorpore.comum.title" />
		</title>
		
		<%@ include file="/include/noCache/noCache.jsp" %>
		<%@ include file="/include/menu/menuConfig.jsp" %>
		<%@ include file="/include/header.jsp" %>
		<%@ include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		
		<script>
			var objTab = null;
			
			$(function() {
				document.form.afterRestore = function() {
					$('.tabs').tabs('select', 0);
					
					if ($('#comboTiposPessoa option:selected').val() == 'F' || $('#comboTiposPessoa option:selected').val() == 'J') {
						$('#cnpj').removeAttr('disabled');
					} else {
						$('#cnpj').attr('disabled', 'disabled');
					}
					
					$('#telefone').unmask();
					$('#telefone').mask('(99) 9999-9999').val($('#telefone').val() );
					
					$('#fax').unmask();
					$('#fax').mask('(99) 9999-9999').val($('#fax').val() );
					
					$('#cep').unmask();
					$('#cep').mask('99999-999').val($('#cep').val() );					
					
					$('#comboPaises option[value=' + $('#idPais').val() + ']').prop('selected', true);
					$('#comboUfs option[value=' + $('#idUf').val() + ']').prop('selected', true);
					$('#comboCidades option[value=' + $('#idCidade').val() + ']').prop('selected', true);
				};
				
				$('#cnpj').attr('disabled', 'disabled');
				
				/* CONFIGURA O CAMPO ESCONDIDO idPais DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE PAISES */			
				$('#comboPaises').change(function() {
		            $('#idPais').val($('#comboPaises option:selected').val() );
		            
		            if ($('#idPais').val() == '') {
		            	$('#idUf').val('');
		            	$('#idCidade').val('');
		            	document.form.fireEvent('preencherComboCidades');
		            }		            
		            document.form.fireEvent('preencherComboUfs');
		        });
				
				/* CONFIGURA O CAMPO ESCONDIDO idUf DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE UFs*/
				$('#comboUfs').change(function() {
					$('#idUf').val($('#comboUfs option:selected').val() );
					
					if ($('#idUf').val() == '') {
						$('#idCidade').val('');
					}
					document.form.fireEvent('preencherComboCidades');
				});
				
				/* CONFIGURA O CAMPO ESCONDIDO idCidade DE ACORDO COM A OPÇÃO SELECIONADA NA CAIXA DE SELEÇÃO DE CIDADES*/
				$('#comboCidades').change(function() {
					$('#idCidade').val($('#comboCidades option:selected').val() );					
				});
				
				/* 1 - CONFIGURANDO A MÁSCARA APROPRIADA PARA CADA CAMPO DO FORMULÁRIO */
				
				/* 1.1 - PARA O CAMPO CPF/CNPJ DE ACORDO COM O TIPO DE PESSOA SELECIONADO */
				
				$('#comboTiposPessoa').change(function() {
					// Recuperando o tipo de pessoa selecionado.
					var tipoPessoa = $('#comboTiposPessoa option:selected').val();
					
					$('#tipoPessoa').val(tipoPessoa);
					
					// Limpando o campo de CPF/CNPJ
					$('#cnpj').val('');
					
					// Retirando a máscara anterior					
					$('#cnpj').unmask();
					
					if (tipoPessoa != '') {
						// Definindo a máscara do campo de acordo com o tipo de pessoa.
						if('<%=request.getSession().getAttribute("locale")%>'=="en")
							var mascara = '99999999999';
						else
							var mascara = tipoPessoa == 'F' ? '999.999.999-99' : '99.999.999/9999-99';
						
						$('#cnpj').removeAttr('disabled');
						
						// Aplicando a nova máscara ao campo.
						$('#cnpj').mask(mascara);		
					} else {
						$('#cnpj').attr('disabled', 'disabled');
					}
				});
				
				/* 1.2 - PARA OS CAMPOS TELEFONE E FAX */
				$('#telefone').mask('(99) 9999-9999');
				$('#fax').mask('(99) 9999-9999');
				
				/* 1.3 - CEP */				
				$('#cep').mask('99999-999');
				
				$('#email').focusout(function() {
					var email = $('#email').val();
					
					if (email != '') {
						// Avaliando a expressão regular para validação do e-mail.
						if (!/\b[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}\b/.test(email) ) {
							alert(i18n_message("citcorpore.validacao.emailInvalido"));
						}
					}
				});
			});
			
			/* RECUPERA O FORNECEDOR A PARTIR DA ABA DE PESQUISA DE FORNECEDORES */
			function LOOKUP_FORNECEDOR_select(id, desc) {				
				document.form.restore({
					idFornecedor: id
				});
			}
			
			function excluir() {
				var idFornecedor = document.getElementById("idFornecedor");
				
				if (idFornecedor.value != "") {
					if (confirm(i18n_message("citcorpore.comum.deleta") ) ) {
						document.form.fireEvent("delete");				
					}					
				} else {
					alert(i18n_message("citcorpore.comum.necessarioSelecionarRegistro") );					
					return false;	
				}
			}
			
/* 			function verificaTipPessoa(){
				alert(document.form.comboTiposPessoa.value);
				if (StringUtils.isBlank(document.form.comboTiposPessoa.value)){
                    alert(i18n_message("fornecedor.tipoPessoa")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.form.comboTiposPessoa.focus();
                    return false;
                }
			} */

			function verificaCpfCnpj(cpfcnpj) {
				var maskCPF = "___.___.___-__";
				var maskCNPJ = "__.___.___/____-__";
				var maskCPFAmericano = "___________";
				var valor = cpfcnpj.value;
				
				if (StringUtils.isBlank(cpfcnpj.value) || (valor == maskCPF) || (valor == maskCNPJ) || (valor == maskCPFAmericano))
                    return true;
               /*  if (StringUtils.isBlank(document.form.comboTiposPessoa.value)){
                    alert(i18n_message("fornecedor.tipoPessoa")+" "+i18n_message("citcorpore.comum.naoInformado"));
                    document.form.comboTiposPessoa.focus();
                    return false;
                }	 */
				if (document.form.comboTiposPessoa.value == 'J') {
	               if (!ValidacaoUtils.validaCNPJ(cpfcnpj, 'CNPJ - ')){
						valor = "";
						return false;
	               }
				}else if (document.form.comboTiposPessoa.value == 'F'){
                   if (!ValidacaoUtils.validaCPF(cpfcnpj, 'CPF - ')){
                	   valor = "";
                       return false;
                   }
				}
				return true;
			}
		</script>
		
<%
	// Se for chamado por iframe deixa apenas a parte de cadastro da página
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
			<%@ include file="/include/menu_vertical.jsp" %>
	<%
		}
	%>
			<!-- CONTEUDO -->
			<div id="main_container" class="main_container container_16 clearfix">
		<%
			if (iframe == null) {
		%>
				<%@ include file="/include/menu_horizontal.jsp" %>
		<%
			}
		%>
				<div class="flat_area grid_16">
					<h2>
						<i18n:message key="fornecedor" />
					</h2>
				</div>
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1">
								<i18n:message key="fornecedor.cadastro" />
							</a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top">
								<i18n:message key="fornecedor.pesquisa" />
							</a>
						</li>
					</ul>
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">
								<form id="form" name="form" action="<%= CitCorporeConstantes.CAMINHO_SERVIDOR %><%= request.getContextPath() %>/pages/fornecedor/fornecedor">
									<input type="hidden" id="idFornecedor" name="idFornecedor" />
									<input type="hidden" id="idEndereco" name="idEndereco" />
									<input type="hidden" id="idPais" name="idPais" />
									<input type="hidden" id="idUf" name="idUf" />
									<input type="hidden" id="idCidade" name="idCidade" />
									<input type="hidden" id="tipoPessoa" name="tipoPessoa" />
									<div class="columns clearfix">
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="fornecedor.nomeRazaoSocial" />
												</label>
												<div>
													<input type="text" id="razaoSocial" name="razaoSocial" maxlength="100" class="Valid[Required] Description[fornecedor.nomeRazaoSocial]" />
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label class="campoObrigatorio">
													<i18n:message key="fornecedor.nomeFantasia" />
												</label>
												<div>
													<input type="text" id="nomeFantasia" name="nomeFantasia" maxlength="70" class="Valid[Required] Description[fornecedor.nomeFantasia]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label style="margin-bottom: 3px;">
													<i18n:message key="fornecedor.tipoPessoa" />
												</label>
												<div style="height: 32px;">
													<select id="comboTiposPessoa" name="comboTiposPessoa">
                                                        <option value="">
                                                            <i18n:message key="citcorpore.comum.selecione" />
                                                        </option>
														<option value="J">
															<i18n:message key="fornecedor.juridica" />
														</option>
                                                        <option value="F">
                                                            <i18n:message key="fornecedor.fisica" />
                                                        </option>
													</select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="fornecedor.cpfcnpj" />
												</label>
												<div>												
													<input type="text" id="cnpj" name="cnpj" value="<i18n:message key="fornecedor.selecione_tipo_pessoa" />" maxlength="18" class="Description[fornecedor.cpfcnpj]" onblur="verificaCpfCnpj(this);"/>
													
													
												</div>
											</fieldset>
										</div>										
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="avaliacaoFonecedor.inscricaoEstadual" />
												</label>
												<div>
													<input type="text" id="inscricaoEstadual" name="inscricaoEstadual" maxlength="25" class="Description[avaliacaoFonecedor.inscricaoEstadual]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="avaliacaoFornecedor.inscricaoMunicipal" />
												</label>
												<div>
													<input type="text" id="inscricaoMunicipal" name="inscricaoMunicipal" maxlength="25" class="Description[avaliacaoFonecedor.inscricaoMunicipal]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="fornecedor.telefone" />
												</label>
												<div>
													<input type="text" id="telefone" name="telefone" maxlength="14" class="Description[fornecedor.telefone]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="fornecedor.fax" />
												</label>
												<div>
													<input type="text" id="fax" name="fax" maxlength="14" class="Description[fornecedor.fax]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="fornecedor.email" />
												</label>
												<div>
													<input type="text" id="email" name="email" maxlength="100" class="Description[fornecedor.email]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="fornecedor.nomeContato" />
												</label>
												<div>
													<input type="text" id="nomeContato" name="nomeContato" maxlength="100" class="Description[fornecedor.email]" />
												</div>
											</fieldset>
										</div>
										<div class="col_40">
											<fieldset>
												<label>
													<i18n:message key="unidade.logradouro" />
												</label>
												<div>
													<input type="text" id="logradouro" name="logradouro" maxlength="100" class="Description[unidade.logradouro]" />
												</div>
											</fieldset>
										</div>
										<div class="col_10">
											<fieldset>
												<label>
													<i18n:message key="localidade.numero" />
												</label>
												<div>
													<input type="text" id="numero" name="numero" maxlength="20" class="Description[localidade.numero]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="localidade.complemento" />
												</label>
												<div>
													<input type="text" id="complemento" name="complemento" maxlength="100" class="Description[localidade.complemento]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="localidade.bairro" />
												</label>
												<div>
													<input type="text" id="bairro" name="bairro" maxlength="100" class="Description[localidade.bairro]" />
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label>
													<i18n:message key="avaliacaoFonecedor.cep" />
												</label>
												<div>
													<input type="text" id="cep" name="cep" maxlength="8" class="Description[avaliacaoFonecedor.cep]" />
												</div>
											</fieldset>
										</div>																				
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="unidade.pais" />
												</label>
												<div style="height: 32px;">
													<select id="comboPaises" name="comboPaises" class="Valid[Required] Description[unidade.pais]"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="avaliacaoFonecedor.uf" />
												</label>
												<div style="height: 32px;">
													<select id="comboUfs" name="comboUfs" class="Valid[Required] Description[avaliacaoFonecedor.uf]"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_25">
											<fieldset>
												<label class="campoObrigatorio" style="margin-bottom: 3px;">
													<i18n:message key="avaliacaoFonecedor.cidade" />
												</label>
												<div style="height: 32px;">
													<select id="comboCidades" name="comboCidades" class="Valid[Required] Description[avaliacaoFonecedor.cidade]"></select>
												</div>
											</fieldset>
										</div>
										<div class="col_50">
											<fieldset>
												<label>
													<i18n:message key="fornecedor.observacao" />
												</label>
												<div>
													<textarea id="observacao" name="observacao" maxlength="2000" cols="50" rows="3" class="Description[fornecedor.observacao]"></textarea>
												</div>
											</fieldset>
										</div>																																														
									</div>
									<br />
									<br />								
									<button type="button" name="btnGravar" class="light" onclick="document.form.save();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/pencil.png" />
										<span>
											<i18n:message key="citcorpore.comum.gravar" />
										</span>
									</button>
									<button type="button" name="btnLimpar" class="light" onclick='document.form.clear();document.form.fireEvent("load");'>
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/clear.png" />
										<span>
											<i18n:message key="citcorpore.comum.limpar" />
										</span>
									</button>
									<button type="button" name="btnExcluir" class="light" onclick="excluir();">
										<img src="<%= br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO") %>/template_new/images/icons/small/grey/trashcan.png" />
										<span>
											<i18n:message key="citcorpore.comum.excluir" />
										</span>
									</button>									
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section">
								<i18n:message key="citcorpore.comum.pesquisa" />
								<form name="formPesquisa">
									<cit:findField formName="formPesquisa"
										lockupName="LOOKUP_FORNECEDOR"
										id="LOOKUP_FORNECEDOR" 
										top="0" 
										left="0" 
										len="550"
										heigth="400" 
										javascriptCode="true" 
										htmlCode="true" />
								</form>
							</div>
						</div>			
						<!-- ## FIM - AREA DA APLICACAO ## -->
					</div>
				</div>
			</div>
			<!-- FIM DA PÁGINA DE CONTEÚDO -->
		</div>					
		<%@include file="/include/footer.jsp"%>	
<script>
document.form.onValidate = function() {
    return verificaCpfCnpj(document.form.cnpj);
};
</script>
	</body>
</html>
	