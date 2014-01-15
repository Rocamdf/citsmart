<%@ taglib uri="/tags/cit" prefix="cit" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
%>
<html>

<head>
	<%@include file="/include/noCache/noCache.jsp" %>

	<%@include file="/include/titleComum/titleComum.jsp" %>
	
	<%@include file="/include/menu/menuConfig.jsp" %>

	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
	<%@include file="/include/cssComuns/cssComuns.jsp" %>
</head>

<!-- Definicoes Comuns -->
<cit:janelaAguarde id="JANELA_AGUARDE_MENU" title="Aguarde... Processando..." style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body>
	<%@include file="/include/menu/menu.jsp"%>

<script>
	var objTab = null;
	
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
			document.getElementById('tabTela').tabber.tabShow(0);
			verificaValor(document.form.tipo);
		}
	}
	
	function verificaValor(obj){
		if (obj.options[obj.selectedIndex].value == 'E'){
			$('spnValorSalario').innerHTML = 'Valor Salário CLT:';
		}else if (obj.options[obj.selectedIndex].value == 'S'){
			$('spnValorSalario').innerHTML = 'Valor Estágio:';
		}else if (obj.options[obj.selectedIndex].value == 'P'){
			$('spnValorSalario').innerHTML = 'Valor Contratado Mensal:';
		}else if (obj.options[obj.selectedIndex].value == 'X'){
			$('spnValorSalario').innerHTML = 'Valor do Prolabore:';
		}else{
			$('spnValorSalario').innerHTML = 'Valor Mensal:';
		}
	}
</script>

<!-- Area de JavaScripts -->
<script>
	function LOOKUP_EMPREGADO_select(id,desc){
		document.form.restore({idEmpregado:id});
	}
	function CriaItemSelecaoPerfil(row,obj){
		row.cells[0].innerHTML = '<input type="checkbox" name="idGrupo" value="' + obj.idGrupo + '"/>';
	}
</script>

<div id="paginaTotal">
	<div id="areautil">
		<div id="formularioIndex">
       		<div id=conteudo>
				<table width="100%">
					<tr>
						<td width="100%">
							<div id='areaUtilAplicacao'>   
								<!-- ## AREA DA APLICACAO ## -->
							  	<div class="tabber" id="tabTela">
							    	<div class="tabbertab" id="tabCadastro">
										<h2>Cadastro</h2>  	
										 	<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/empregado/empregado'>
										 		<input type='hidden' name='idEmpregado'/>
											  	<table id="tabFormulario" cellpadding="0" cellspacing="0">
											         <tr>
											            <td class="campoEsquerda">Nome:*</td>
											            <td>
											            	<input type='text' name="nome" maxlength="80" size="70" class="Valid[Required] Description[Nome]" />
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Login na rede:</td>
											            <td>
											            	<input type='text' name="login" maxlength="30" size="30"/>
											            </td>
											         </tr>											  												         
											         <tr>
											            <td class="campoEsquerda">Tipo de Colaborador:</td>
											            <td>
											            	<select name='tipo' class="Valid[Required] Description[Tipo de Colaborador]" onchange='verificaValor(this)'>
											            	</select>
											            </td>
											         </tr>
											         <tr>
											         	<td>&nbsp;
											         		
											         	</td>
											         	<td>
											            	<fieldset>
											            		<legend><b>Informa&ccedil;&otilde;es de Pagamento</b></legend>
											            		<table>
											            			<tr>
											            				<td class="campoEsquerda">
											            					<span id='spnValorSalario'>Valor Salário:</span>
											            				</td>
											            				<td>
											            					<input type='text' size='15' maxlength="15" name='valorSalario' class="Valid[Required] Description[Valor Salário]"/>
											            				</td>
											            			</tr>
											            			<tr>
											            				<td class="campoEsquerda">
											            					Valor Produtividade Média:
											            				</td>
											            				<td>
											            					<input type='text' size='15' maxlength="15" name='valorProdutividadeMedia' class="Valid[Required] Description[Valor Produtividade Média (Informe 0 se não houver)]"/>
											            				</td>
											            			</tr>
											            			<tr>
											            				<td class="campoEsquerda">
											            					Valor Plano de Saúde (Valor pago pela empresa):
											            				</td>
											            				<td>
											            					<input type='text' size='15' maxlength="15" name='valorPlanoSaudeMedia' class="Valid[Required] Description[Valor Plano de Saúde]"/>
											            				</td>
											            			</tr>	
											            			<tr>
											            				<td class="campoEsquerda">
											            					Valor Vale Transporte (Média):
											            				</td>
											            				<td>
											            					<input type='text' size='15' maxlength="15" name='valorVTraMedia' class="Valid[Required] Description[Valor Vale Transporte (Média)]"/>
											            				</td>
											            			</tr>
											            			<tr>
											            				<td class="campoEsquerda">
											            					Valor Vale Refeição/Alim. (Média):
											            				</td>
											            				<td>
											            					<input type='text' size='15' maxlength="15" name='valorVRefMedia' class="Valid[Required] Description[Valor Vale Refeição/Alim. (Média)]"/>
											            				</td>
											            			</tr>	
															         <tr>
															            <td class="campoEsquerda">Custo por Hora (Calculado pelo sistema):</td>
															            <td>
															            	<input type='text' name="custoPorHora" maxlength="10" readonly="readonly" size="10"/>
															            </td>
															         </tr>											  											         												            													            														            														            													            			
															         <tr>
															            <td class="campoEsquerda">Custo Total Mensal (Calculado pelo sistema):</td>
															            <td>
															            	<input type='text' name="custoTotalMes" maxlength="15" readonly="readonly" size="15"/>
															            </td>
															         </tr>	
															         <tr>
															            <td class="campoEsquerda">Agência:</td>
															            <td>
															            	<input type='text' name="agencia" maxlength="10" size="10"/>
															            </td>
															         </tr>											  											         												            													            														            														            													            			
															         <tr>
															            <td class="campoEsquerda">Conta:</td>
															            <td>
															            	<input type='text' name="contaSalario" maxlength="20" size="20"/>
															            </td>
															         </tr>															         															         														         
											            		</table>
											            	</fieldset>
											            </td>
											         </tr>	         
											         <tr>
											            <td class="campoEsquerda">Data Nascimento:*</td>
											            <td>
											            	<input type='text' name="dataNascimento" maxlength="10" size="10" class="Valid[Required,Data] Description[Data de Nascimento] Format[Data]" />
											            </td>
											         </tr>	
											         <tr>
											            <td class="campoEsquerda">Sexo:*</td>
											            <td>
											            	<input type='radio' name="sexo" value="M" class="Valid[Required] Description[Sexo]" />Masculino
											            	<input type='radio' name="sexo" value="F" class="Valid[Required] Description[Sexo]" />Feminino
											            </td>
											         </tr>	
											         <tr>
											            <td class="campoEsquerda">CPF:</td>
											            <td>
											            	<input type='text' name="cpf" maxlength="14" size="15" class="Valid[CPF] Description[CPF] Format[CPF]" />
											            </td>
											         </tr>	
											         <tr>
											            <td class="campoEsquerda">RG:</td>
											            <td>
											            	<input type='text' name="rg" maxlength="15" size="15" />
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Data Emissão RG:</td>
											            <td>
											            	<input type='text' name="dataEmissaoRG" maxlength="10" size="10" class="Valid[Data] Description[Data Emissão RG] Format[Data]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Órgão Expedidor RG:</td>
											            <td>
											            	<input type='text' name="orgExpedidor" maxlength="10" size="10" />
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">UF Órgão Expedidor RG:</td>
											            <td>
											            	<select name='idUFOrgExpedidor'>
											            	</select>
											            </td>
											         </tr>	
											         <tr>
											            <td class="campoEsquerda">Email:</td>
											            <td>
											            	<input type='text' name="email" size="70" />
											            </td>
											         </tr>											         
											         <tr>
											            <td class="campoEsquerda">CTPS Número:</td>
											            <td>
											            	<input type='text' name="ctpsNumero" maxlength="15" size="15" class="Description[CTPS Número]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">CTPS Série:</td>
											            <td>
											            	<input type='text' name="ctpsSerie" maxlength="15" size="15" class="Description[CTPS Série]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">CTPS UF:</td>
											            <td>
											            	<select name='ctpsIdUf' class="Description[CTPS UF]">
											            	</select>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">CTPS Data Emissão:</td>
											            <td>
											            	<input type='text' name="ctpsDataEmissao" maxlength="10" size="10" class="Valid[Data] Description[CTPS Data Emissão] Format[Data]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">NIT (PIS ou PASEP):</td>
											            <td>
											            	<input type='text' name="nit" maxlength="20" size="20" class="Description[NIT (PIS ou PASEP)]"/>
											            </td>
											         </tr>	
											         <tr>
											            <td class="campoEsquerda">Data de Admissão*:</td>
											            <td>
											            	<input type='text' name="dataAdmissao" maxlength="10" size="10" class="Valid[Data] Description[Data de Admissão] Format[Data]"/>
											            </td>
											         </tr>
											         <tr>
											            <td class="campoEsquerda">Situação*:</td>
											            <td>
											            	<select name='idSituacaoFuncional' class="Valid[Required] Description[Situação]">
											            	</select>
											            </td>
											         </tr>	         
											         <tr>
											            <td class="campoEsquerda">Data Desligamento:</td>
											            <td>
										            	<input type='text' name="dataDemissao" maxlength="10" size="10" class="Valid[Data] Description[Data de Desligamento] Format[Data]"/>
										            	</td>
										         	</tr>
										         <tr>
										            <td class="campoEsquerda">Pai:</td>
										            <td>
										            	<input type='text' name="pai" maxlength="50" size="50"/>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda">Mãe:</td>
										            <td>
										            	<input type='text' name="mae" maxlength="50" size="50"/>
										            </td>
										         </tr>
										         <tr>
										            <td class="campoEsquerda">Estado Cívil:</td>
										            <td>
										            	<select name='estadoCivil'>
										            	</select>
										            </td>
										         </tr>		         
										         <tr>
										            <td class="campoEsquerda">Conjuge:</td>
										            <td>
										            	<input type='text' name="conjuge" maxlength="50" size="50"/>
										            </td>
										         </tr>	         
										         <tr>
										            <td class="campoEsquerda">Observações:</td>
										            <td>
										            	<textarea name="observacoes" cols='70' rows='5'></textarea>
										            </td>
										         </tr>	
										         <tr>
										            <td class="campoEsquerda">&nbsp;</td>
										            <td>
										            	<fieldset>
										            		<legend><b>Perfil</b></legend>
										            		<div id='divPerfil' style='overflow:auto; height: 120px'>
										            			<table id='tblPerfil' width="100%">
										            				<tr>
										            					<td>
										            						<b>Selecione</b>
										            					</td>
										            					<td>
										            						<b>Grupo</b>
										            					</td>										            					
										            				</tr>
										            			</table>
										            		</div>
										            	</fieldset>
										            </td>
										         </tr>	 										                  	         	                                    		                                            
												 <tr>
										            <td colspan="2" class="campoObrigatorio">* Campos com preenchimento obrigat&oacute;rio</td>
										         </tr>
										         <tr>
										         	<td colspan='2'>
									         		<input type='button' name='btnGravar' value='Gravar' onclick='document.form.save();'/>
									         		<input type='button' name='btnLimpar' value='Limpar' onclick='document.form.idEmpregado.value="";document.form.clear();'/>
									         	</td>
									         </tr>
										</table>
									</form>
								</div>	
						    	<div class="tabbertab" id="tabPesquisa">
									<h2>Pesquisa</h2>
									<form name='formPesquisa'>
										<cit:findField formName='formPesquisa' lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
									</form>
								</div>
							</div>	
							<!-- ## FIM - AREA DA APLICACAO ## -->			
							</div>
						</td>
					</tr>
				</table>
			</div>	
		</div>
	</div>
	<%@include file="../../include/rodape.jsp"%>
</div>

</body>
</html>
							