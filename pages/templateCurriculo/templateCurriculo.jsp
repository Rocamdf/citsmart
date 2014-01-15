<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@ taglib uri="/tags/menuPadrao" prefix="menu" %>
<!doctype html public "">
<html>
	<head>	
	<%	String iframe = "";
	iframe = request.getParameter("iframe"); %>
		<%@page import="br.com.citframework.util.UtilStrings"%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/include/css/template.css"/>
		<link type="text/css" rel="stylesheet" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/include/css/templateCurriculo.css"/>
	</head>
	<body>
		<div class="container-fluid fixed ">
			
			<!-- Top navbar (note: add class "navbar-hidden" to close the navbar by default) -->
			<%if (iframe == null) {%>
				<div class="navbar main hidden-print">
					<%@include file="/novoLayout/common/include/cabecalho.jsp" %>
					<%@include file="/novoLayout/common/include/menuPadrao.jsp" %>
				</div>
			<%}%>
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<div id="content">
				<form name='formPesquisaCurriculo' id='formPesquisaCurriculo' action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/templateCurriculo/templateCurriculo">
					<input type="hidden" id="numeroPagina" name="numeroPagina" />
					<input type="hidden" id="idPais" name="idPais" />
					<input type="hidden" id="hiddenIdUf" name="hiddenIdUf" />
					<input type="hidden" id="idCidade" name="idCidade" />
					<input type="hidden" id="idFormacaoAcademica" name="idFormacaoAcademica" />
					<input type='hidden' name='colTelefones_Serialize' id='colTelefones_Serialize'/>
					<input type='hidden' name='colEnderecos_Serialize' id='colEnderecos_Serialize'/>
					<input type='hidden' name='colEmail_Serialize' id='colEmail_Serialize'/>
					<input type='hidden' name='colFormacao_Serialize' id='colFormacao_Serialize'/>
					<input type='hidden' name='colExperienciaProfissional_Serialize' id='colExperienciaProfissional_Serialize'/>
					<input type='hidden' name='colCertificacao_Serialize' id='colCertificacao_Serialize'/>
					<input type='hidden' name='colIdioma_Serialize' id='colIdioma_Serialize'/>
					<input type='hidden' name='colCompetencias_Serialize' id='colCompetencias_Serialize'/>
					<input type='hidden' name='idCurriculo' id='idCurriculo'/>
					<input type='hidden' name='idSolicitacaoServicoCurriculo' id='idSolicitacaoServicoCurriculo'/>
				
					<div class="widget widget-tabs widget-tabs-double">
					
						<!-- Widget heading -->
						<div class="widget-head" id="wizardCurriculo">
							<ul id="ulWizard">
								<li class="active" id="li1" onclick="paginacao1();"><a class="glyphicons user" href="#tab1"><i></i><span class="strong"><i18n:message key='rh.etapaUm'/></span><span><i18n:message key='colaborador.dadosPessoais'/></span></a></li>
								<li id="li2" onclick="paginacao2()"><a class="glyphicons pen" href="#tab2"><i></i><span class="strong"><i18n:message key='rh.etapaDois'/></span><span><i18n:message key='rh.formacaoIdioma'/></span></a></li>
								<li id="li3" onclick="paginacao3()"><a class="glyphicons nameplate" href="#tab3"><i></i><span class="strong"><i18n:message key='rh.etapaTres'/></span><span><i18n:message key='rh.experiencia'/></span></a></li>
								<li id="li4" onclick="paginacao4()"><a class="glyphicons certificate" href="#tab4"><i></i><span class="strong"><i18n:message key='rh.etapaQuatro'/></span><span><i18n:message key='rh.competenciasCertificacoes'/></span></a></li>
								<li id="li5" onclick="paginacao5()"><a class="glyphicons paperclip" href="#tab5"><i></i><span class="strong"><i18n:message key='rh.etapaCinco'/></span><span><i18n:message key='agenda.anexos'/></span></a></li>
							</ul>
						</div>
						<!-- // Widget heading END -->
						
						<div class="widget-body">
							<div class="tab-content">
							
							<!-- Etapa 1 -->
								<div id="tab1" class="tab-pane active">
									<div>
										<strong><i18n:message key='rh.dadosPessoais'/></strong>
										<p class="muted"><i18n:message key='rh.entreDadosPessoais'/></p>
									</div>
									<div class="row-fluid">
										
										<!-- Column -->
										<div class="span5">
										
											<!-- Nome -->
											<div class="control-group">
												<label class="strong campoObrigatorio"><i18n:message key='citcorpore.comum.nome'/></label>
												<div class="controls">
													<input type="text" class="span10" value="" id="nome" name="nome" maxlength="120">
													<span data-original-title="First name is mandatory" data-placement="top" data-toggle="tooltip" class="btn-action single glyphicons circle_question_mark" style="margin: 0;"><i></i></span>
												</div>
											</div>
											<!-- // Fim nome -->
											
											<!-- CPF e Data de nascimento-->
											<div class="row-fluid">
											
												<div class="span6">
													<label class="strong campoObrigatorio"><i18n:message key='lookup.cpf'/></label>
														<div class="controls">
															<input type="text" class="span9" value="" id="cpf" name="cpf" maxlength="14">
														</div>
												</div>
												<div class="span5">
													<label class="strong campoObrigatorio"><i18n:message key='citcorpore.comum.dataNascimento'/></label>
														<div id="datetimepicker2" class="input-append date">
															<input type="text" class="span9 Format[data] datepicker" id="dataNascimento" name="dataNascimento" maxlength="10" />
															<span class="add-on glyphicons calendar"><i></i></span>
														</div>
												</div>
											</div>
											<!-- // Fim CPF e Data de nascimento -->
											
											<!-- Naturalidade e cidade natal -->
											<div class="row-fluid">
											<div class="span6" >
												<label class="strong campoObrigatorio"><i18n:message key='rh.nacionalidade'/></label>
													<div class="controls">
														<select class="span6 " name="idNaturalidade" id="idNaturalidade">
														</select>
													</div>
											</div>
											<div class="span6">
													<label class="strong campoObrigatorio"><i18n:message key='rh.cidadeNatal'/></label>
														<div class="controls">
															<input type="text" class="span9" value="" id="cidadeNatal" name="cidadeNatal" maxlength="80">
														</div>
												</div>
											</div>
										<!-- 	<div class="row-fluid">
												<div class="span11">
													<label class="strong campoObrigatorio">Cidade Natal</label>
														<div class="controls">
															<input type="text" class="span9" value="" id="cidadeNatal" name="cidadeNatal" maxlength="80">
														</div>
												</div>
											</div> -->
											<!-- // Fim Naturalidade e cidade natal -->
											
											<!-- E-mail -->
											<div class="row-fluid">
												<label class="strong campoObrigatorio"><i18n:message key='citcorpore.comum.email'/></label>
												<div class="input-append">
													<input type="text" class="span18" value="" id="email#descricaoEmail" name="email#descricaoEmail" maxlength="80" data-original-title=<i18n:message key='rh.digiteEmailContato'/> data-placement="top" data-toggle="tooltip">
													<span onclick="addEmail()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarEmail'/></span>
												</div>
												<div id="divPrincipal" class="tab-pane active">
														<div class="uniformjs">
															<label class="checkbox"><div class="checker" id="uniform-undefined"><span class=""><input type="checkbox" name="email#principal" id="email#principal" value="S" style="opacity: 0;"></span></div><i18n:message key='rh.emailPrincipal'/> </label>
														</div>
												</div>
											</div>
										
											<div class="row-fluid">
											<div class="widget">
												<div class="widget-head" style="height: height: 30px!important">
													<h4 class="heading"><i18n:message key='citcorpore.comum.emails'/></h4>
												</div>
												<div class="widget-body">
												
											<!-- Table  -->
													<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
															<div class="row-fluid">
															</div>
															<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblEmail" aria-describedby="DataTables_Table_0_info">
															
																<!-- Table heading -->
																<thead>
																	<tr role="row">
																		<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																			style="width: 10%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='rh.principal'/></th>
																		<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																			style="width: 10%;" aria-label="Platform(s): activate to sort column ascending"><i18n:message key='citcorpore.comum.email'/></th>
																		<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																			style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
																	</tr>
																</thead>
																<!-- // Table heading END -->
																
																<!-- Table body -->
																
																<!-- // Table body END -->
																
															<tbody role="alert" aria-live="polite" aria-relevant="all">
															</tbody>
														</table>
													</div>
													<!-- // Table END -->
												</div>
											</div>
											<!-- // Fim Email  -->
										</div>
											
											<div>
												<strong><i18n:message key='rh.endereco'/></strong>
												<p class="muted"><i18n:message key='rh.entreDados'/></p>
											</div>
											
											<!-- Tipo de Endereço -->
											<div class="row-fluid">
												<div class="span6">
												<label class="strong campoObrigatorio"><i18n:message key='rh.tipoEndereco'/></label>
													<div class="controls">
														<select class="span6 " id="endereco#idTipoEndereco" name="endereco#idTipoEndereco">
															<option value=""><i18n:message key='citcorpore.comum.selecione'/></option> 
													        <option  value="1"><i18n:message key='residencial'/></option>
															<option  value="2"><i18n:message key='comercial'/></option>	
														</select>
													</div>
												</div>
												<div class="span6">
												<label class="strong campoObrigatorio"><i18n:message key='avaliacaoFonecedor.cep'/></label>
													<div class="controls">
														<input type="text" class="span5" value="" id="cep" name="cep" maxlength="9">
													</div>
												</div>
											</div>
											<!-- // Fim tipo de endereço -->
											
											<!-- Endereço -->
											<div class="row-fluid">
												<label class="strong campoObrigatorio"><i18n:message key='unidade.logradouro'/></label>
												<div class="controls">
														<input type="text" class="span12" value="" id="endereco#logradouro" name="endereco#logradouro" maxlength="100" data-toggle="tooltip" data-original-title="<i18n:message key='templateCurriculo.digiteEnderecoCompleto'/>">
												</div>
											</div>
											<!-- // Fim Endereço -->
										
											<!-- Uf e cidade -->
											<div class="row-fluid">
												<div class="span6">
												<label class="strong campoObrigatorio"><i18n:message key='avaliacaoFonecedor.uf'/></label>
													<div class="controls">
														<select class="span9 " id="enderecoIdUF" name="enderecoIdUF">
														</select>
													</div>
												</div>
												<div class="span6">
												<label class="strong campoObrigatorio"><i18n:message key='avaliacaoFonecedor.cidade'/></label>
													<div class="controls">
														<select class="span9 " id="enderecoNomeCidade" name="enderecoNomeCidade">
														</select>
													</div>
												</div>
												
												<div class="uniformjs">
													<label class="checkbox"><div class="checker" id="enderecoCorrespondencia"><span class=""><input type="checkbox" name="endereco#correspondencia" id="endereco#correspondencia" value="S" style="opacity: 0;"></span></div><i18n:message key='rh.enderecoPrincipal'/></label>
												</div>
												<div class="input-append">
													<span onclick="addEndereco()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarEndereco'/></span>
												</div>
											</div>
											<!-- // Fim Uf e cidade -->
										
										</div>
										<!-- // Column END -->
										
										<!-- Column -->
										<div class="span5">
										
											<!-- SExo -->
											<div class="row-fluid">
											
												<div class="span5">
												<label class="strong campoObrigatorio"><i18n:message key='citcorpore.comum.sexo'/></label>
													<div class="controls">
														<select class="span9 " name="sexo" id="sexo">
														<option value='M'><i18n:message key='colaborador.masculino'/></option>
														<option value='F'><i18n:message key='colaborador.feminino'/></option>
													</select>
													</div>
												</div>
												<div class="span7">
												<label class="strong campoObrigatorio"><i18n:message key='citcorpore.comum.estadoCivil'/></label>
													<div class="controls">
														<select class="span5 " name='estadoCivil' id="estadoCivil">
														    <option value="0" ><i18n:message key='citcorpore.comum.selecione'/></option>
															<option value="1" ><i18n:message key='rh.solteiro'/></option>
															<option value="2" ><i18n:message key='rh.casado'/></option>
							                                <option value="3" ><i18n:message key='rh.Companheiro'/></option>
							                                <option value="4" ><i18n:message key='rh.uniaoEstavel'/></option>
															<option value="5" ><i18n:message key='rh.separado'/></option>
							                                <option value="6" ><i18n:message key='rh.divorciado'/></option>
															<option value="7" ><i18n:message key='rh.viuvo'/></option>
														</select>
													</div>
												</div>
											</div>
											<!-- // Fim sexo -->
											
											
											<!-- Possui Filhos -->
											<div class="row-fluid">
												<label class="strong campoObrigatorio"><i18n:message key='rh.possuiFilhos'/></label>
												<div class="widget-body uniformjs collapse in">
													<label class="radio">
														<div class="radio" id="filhos"><span class=""><input type="radio" value="1" name="filhos" class="radio" style="opacity: 0;" onclick="manipulaDivQtdFilhos()"></span></div>
														 <i18n:message key='rh.sim'/>
													</label>
													<label class="radio">
														<div class="radio" id="filhos"><span class="checked"><input type="radio" checked="checked" value="0" name="filhos" class="radio" style="opacity: 0;" onclick="manipulaDivQtdFilhos()"></span></div>
														 <i18n:message key='rh.nao'/>
													</label>
												</div>
											</div>
												<div class="row-fluid">
												<label class="strong campoObrigatorio"><i18n:message key='rh.portadorNecessidadeEspeciais'/></label>
												<div class="widget-body uniformjs collapse in">
													<label class="radio">
														<div class="radio" id="portadorNecessidadeEspecial"><span class=""><input type="radio" value="N" name="portadorNecessidadeEspecial" class="radio" style="opacity: 0;"  onclick="manipulaDivDeficiencia()"></span></div>
														<i18n:message key='rh.sim'/>
													</label>
													<label class="radio">
														<div class="radio" id="portadorNecessidadeEspecial2"><span class="checked"><input type="radio" checked="checked" value="S" name="portadorNecessidadeEspecial" class="radio" style="opacity: 0;" onclick="manipulaDivDeficiencia()"></span></div>
														 <i18n:message key='rh.nao'/>
													</label>
												</div>
											</div>
											
											
											<!-- Qual deficiencia -->
											<div class="row-fluid" id="divQualDeficiencia" style="display: none">
												<label class="strong campoObrigatorio"><i18n:message key='rh.qual'/></label>
												<div class="controls">
													 <select class="span4 " name="idItemListaTipoDeficiencia">
								                             <option value=""><i18n:message key='citcorpore.comum.selecione'/> </option>
								                             <option value="1"><i18n:message key='rh.auditiva'/></option>
								                             <option value="2"><i18n:message key='rh.fisica'/></option>
								                             <option value="3"><i18n:message key='rh.mental'/></option>
								                             <option value="4"><i18n:message key='rh.multipla'/></option>
								                             <option value="5"><i18n:message key='rh.visual'/></option>
							                            </select>
												</div>
											</div>
											<!-- // Fim Qual deficiencia -->
											
											
											
											<!-- // Fim Possui filhos -->
											
											<!-- Quantidade de filhos -->
											<div class="row-fluid" id="divQtdFilhos" style="display: none">
												<label class="strong campoObrigatorio"><i18n:message key='rh.quantidadeFilhos'/></label>
												<div class="controls">
													<select class="span3 " name="qtdeFilhos">
								                         <option value=""><i18n:message key='citcorpore.comum.selecione'/></option>
								                         <option value="1">1</option>
								                         <option value="2">2</option>
								                         <option value="3">3</option>
								                         <option value="4">4</option>
								                         <option value="5"><i18n:message key='rh.cincoOuMais'/></option>
							                        </select>
												</div>
											</div>
											<div class="separator"></div>
											<div class="separator"></div>
											<div class="separator"></div>
											<!-- // Fim Quantidade de filhos -->
											<!-- DDD e telefone -->
											<div class="row-fluid">
											<div class="span3">
											<label class="strong campoObrigatorio"><i18n:message key='rh.tipoTelefone'/></label>
												<select class="span10 " id="telefone#idTipoTelefone" name="telefone#idTipoTelefone">
													<option value=""><i18n:message key='citcorpore.comum.selecione'/></option>
													<option  value="1"><i18n:message key='rh.residencial'/></option>
													<option  value="2"><i18n:message key='rh.celular'/></option>
												</select>
											</div>
												<div class="span2">
												<label class="strong campoObrigatorio">DDD</label>
													<div class="controls">
														<input type="text" class="span5" value="" id="telefone#ddd" name="telefone#ddd" onkeypress="return somenteNumero(event);" maxlength="3">
													</div>
												</div>
												<div class="span6">
												<label class="strong campoObrigatorio"><i18n:message key='citcorpore.comum.telefone'/></label>
													<div class="input-append">
														<input type="text" class="span10" value="" id="telefone" name="telefone" maxlength="10">
														<span onclick="addTelefone()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarTelefone'/></span>
													</div>
												</div>
											</div>
											<div class="widget">
												<div class="widget-head" style="height: height: 30px!important">
													<h4 class="heading"><i18n:message key='rh.telefones'/></h4>
												</div>
												<div class="widget-body">
											<!-- Table  -->
													<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
														<div class="row-fluid">
														</div>
															<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblTelefones" aria-describedby="DataTables_Table_0_info">
															
																	<!-- Table heading -->
																	<thead>
																		<tr role="row">
																			<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='citcorpore.comum.tipo'/></th>
																			<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-label="Platform(s): activate to sort column ascending">DDD</th>
																			<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 70%;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='citcorpore.comum.telefone'/></th>
																			<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																				style="width: 10%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
																		</tr>
																	</thead>
																	<!-- // Table heading END -->
																	
																	<!-- Table body -->
																	
																	<!-- // Table body END -->
																	
																<tbody role="alert" aria-live="polite" aria-relevant="all">
																</tbody>
															</table>
														</div>
													<!-- // Table END -->
													</div>
												</div>
											<!-- // Fim Telefone  -->
											
											<br>
											<br>
											
											<!-- Complemento -->
											<div class="row-fluid">
												<label class="strong campoObrigatorio"><i18n:message key='localidade.complemento'/></label>
												<div class="controls">
													<input type="text" class="span10" value="" id="endereco#complemento" name="endereco#complemento" maxlength="100">
													<span data-original-title="First name is mandatory" data-placement="top" data-toggle="tooltip" class="btn-action single glyphicons circle_question_mark" style="margin: 0;"><i></i></span>
												</div>
											</div>
											<!-- // Fim complemento -->
											
											<!-- Bairro -->
											<div class="row-fluid">
												<label class="strong campoObrigatorio"><i18n:message key='localidade.bairro'/></label>
												<div class="controls">
													<input type="text" class="span10" value="" id="endereco#nomeBairro" name="endereco#nomeBairro" maxlength="80">
													<span data-original-title="First name is mandatory" data-placement="top" data-toggle="tooltip" class="btn-action single glyphicons circle_question_mark" style="margin: 0;"><i></i></span>
												</div>
											</div>
											<!-- // Fim Bairro -->
											
										</div>
										<!-- // Column END -->
										
										<!-- Column -->
										<div class="span2 center">
										
											<!-- Foto -->
											<div class="innerR">
												<!-- Profile Photo -->
												<label class="strong"><i18n:message key='rh.foto'/></label>
													<div id='divImgFoto' style=' width: 165px; height: 170px;'>
								     				 </div>
												<div class="separator bottom"></div>
												<!-- // Profile Photo END -->
												<div class="input-append">
													<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" href="#modal_foto" data-toggle="modal" data-target="#modal_foto"><i></i><i18n:message key='rh.adicionarFoto'/></span>
													<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" onclick="delImgFoto()"><i></i><i18n:message key='rh.removerFoto'/></span>
												</div>
											</div>
											<!-- // Fim Foto -->
											
											
										</div>
										
										
										<!-- // Column END -->
										
									</div>
						
							<div class="widget">
								<div class="widget-head" style="height: height: 30px!important">
									<h4 class="heading"><i18n:message key='grupovisao.enderecos'/></h4>
								</div>
								<div class="widget-body">
			
									<!-- Tabela Endereços -->
									<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
										<div class="row-fluid"> 
										</div>
											<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblEnderecos" aria-describedby="DataTables_Table_0_info">
										
												<!-- Table heading -->
												<thead>
													<tr role="row">
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 8%; word-wrap: break-word;" aria-label="Browser: activate to sort column ascending"><i18n:message key='visao.correspondencia'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 10%; word-wrap: break-word;" aria-label="Platform(s): activate to sort column ascending"><i18n:message key='requisicaoMudanca.tipo'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 20%; word-wrap: break-word;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='unidade.logradouro'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 20%; word-wrap: break-word;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='visao.complemento'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 10%; word-wrap: break-word;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='localidade.bairro'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 10%; word-wrap: break-word;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='lookup.cidade'/></th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 10%; word-wrap: break-word;" aria-label="Eng. vers.: activate to sort column ascending">UF</th>
													<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 10%; word-wrap: break-word;" aria-label="Eng. vers.: activate to sort column ascending">CEP</th>
													<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
														style="width: 5%; word-wrap: break-word;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
													</tr>
												</thead>
												<!-- // Table heading END -->
												
												<!-- Table body -->
												
												<!-- // Table body END -->
												
											<tbody role="alert" aria-live="polite" aria-relevant="all">
											</tbody>
										</table>
									</div>
									<!-- // Table END -->
				
								</div>
							</div>
						</div>
					<!-- // FIM ETAPA 1 -->
								
					<!-- ETAPA 2 -->
								<div id="tab2" class="tab-pane">
										<div>
											<strong><i18n:message key='rh.formacaoIdiomas'/></strong>
											<p class="muted"><i18n:message key='rh.entreOsDados'/></p>
										</div>
								<div class="row-fluid">
									<!-- Column 1 -->
										<!-- Formação -->
											<div class="span6">
												<div class="row-fluid">
													<label class="strong campoObrigatorio"><i18n:message key='rh.formacao'/></label>
													<div class="controls">
														<select class="span6 " name='formacao#idTipoFormacao' id="formacao#idTipoFormacao">
															<option  value="" ><i18n:message key='citcorpore.comum.selecione'/></option> 
													        <option  value="1" ><i18n:message key='rh.ensinoFundamental'/></option>
															<option  value="2" ><i18n:message key='rh.ensinoMedio'/></option>
															<option  value="3" ><i18n:message key='rh.graduacao'/></option>
															<option  value="4" ><i18n:message key='rh.posGraduacao'/></option>
															<option  value="5" ><i18n:message key='rh.mestrado'/></option>
															<option  value="6" ><i18n:message key='rh.doutorado'/></option>
						  								</select>
													</div>
												</div>
											<!-- // Fim Formação -->
												
											<!-- situação Formação -->
												<div class="row-fluid">
													<label class="strong campoObrigatorio"><i18n:message key='categoriaProduto.categoria_situacao'/></label>
													<div class="controls">
														<select class="span6 " name='formacao#idSituacao'>
													        <option value=""><i18n:message key='citcorpore.comum.selecione'/></option> 
													        <option  value="1"><i18n:message key='rh.cursou'/></option>
															<option  value="2"><i18n:message key='rh.cursando'/></option>
															<option  value="3"><i18n:message key='rh.trancadoInterrompido'/></option>														  								
						 								</select>
													</div>
												</div>
											<!-- // Fim situação Formação -->
											
											<!-- Instituição de ensino -->
												<div class="row-fluid">
													<label class="strong campoObrigatorio"><i18n:message key='rh.instituicaoEnsino'/></label>
													<div class="controls">
														<input type="text" class="span6" value="" id="formacao#instituicao" name="formacao#instituicao" maxlength="80">
													</div>
												</div>
											<!-- // Fim Instituição de ensino -->
											
											<!-- Descrição formacao -->
												<div class="row-fluid" id="divGraduacao">
													<label class="strong campoObrigatorio"><i18n:message key='calendario.descricao'/></label>
													<div class="controls">
														<input type="text" class="span6" value="" id="formacaoDescricao" name="formacaoDescricao" maxlength="80">
													</div>
												</div>
												<div class="input-append">
													<span onclick="addFormacao()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarFormacao'/></span>
												</div>
											<!-- // Fim Descrição formacao -->
											<div class="widget">
												<div class="widget-head" style="height: height: 30px!important">
													<h4 class="heading"><i18n:message key='rh.formacao'/></h4>
												</div>
												<div class="widget-body">
											<!-- Table  -->
												<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
													<div class="row-fluid"> 
													</div>
														<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblFormacao" aria-describedby="DataTables_Table_0_info">
													
															<!-- Table heading -->
															<thead>
																<tr role="row">
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 15%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='rh.formacao'/></th>
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 15%;" aria-label="Platform(s): activate to sort column ascending"><i18n:message key='rh.instituicao'/></th>
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 15%;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='categoriaProduto.categoria_situacao'/></th>
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 50%;" aria-label="CSS grade: activate to sort column ascending"><i18n:message key='calendario.descricao'/></th>
																<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th></tr>
															</thead>
															<!-- // Table heading END -->
															
															<!-- Table body -->
															
															<!-- // Table body END -->
															
														<tbody role="alert" aria-live="polite" aria-relevant="all">
														</tbody>
													</table>
												</div>
											<!-- // Table END -->
												</div>
											</div>
										</div>
									<!-- Fim column 1 -->
									<!-- Column 2 -->
									<!-- idiomas  -->
									<div class="span6">
										<div class="row-fluid">
											<label class="strong"><i18n:message key='lingua.lingua'/></label>
											<select name='idioma#idIdioma' id="idioma#idIdioma" class="span6 ">
				  								</select>
							  			</div>
										<!-- Fim idiomas -->
										
										<!-- Escrita do idioma -->
										<div class="row-fluid">
											<label class="strong"><i18n:message key='rh.escrita'/></label>
											<select name='idioma#idNivelEscrita' id="idioma#idNivelEscrita" class="span6 ">
										        <option  value=""><i18n:message key='citcorpore.comum.selecione'/></option> 
										        <option  value="1"><i18n:message key='rh.naoTem'/></option>
												<option  value="2"><i18n:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
												<option  value="3"><i18n:message key='rh.boa'/></option>
												<option  value="4"><i18n:message key='rh.avancada'/></option>													  								
			  								</select>
							  			</div>
										<!-- Fim escrita idiomas -->
										
										<!-- Leitura de idiomas -->
										<div class="row-fluid">
											<label class="strong"><i18n:message key='rh.leitura'/></label>
											<select name='idioma#idNivelLeitura' id="idioma#idNivelLeitura" class="span6 ">
										        <option  value=""><i18n:message key='citcorpore.comum.selecione'/></option> 
										        <option  value="1"><i18n:message key='rh.naoTem'/></option>
												<option  value="2"><i18n:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
												<option  value="3"><i18n:message key='rh.boa'/></option>
												<option  value="4"><i18n:message key='rh.avancada'/></option>													  								
			  								</select>
							  			</div>
										<!--  fim leitura de idiomas -->
										
										<!-- Conversa -->
										<div class="row-fluid">
											<label class="strong"><i18n:message key='rh.conversacao'/></label>
											 <select name='idioma#idNivelConversa' id="idioma#idNivelConversa" class="span6 ">
										        <option  value=""><i18n:message key='citcorpore.comum.selecione'/></option> 
										        <option  value="1"><i18n:message key='rh.naoTem'/></option>
												<option  value="2"><i18n:message key='citcorpore.comum.complexidadeIntermediaria'/></option>
												<option  value="3"><i18n:message key='rh.boa'/></option>
												<option  value="4"><i18n:message key='rh.avancada'/></option>													  								
			  								</select>
							  			</div>
							  			<div class="input-append">
											<span onclick="addIdioma()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarIdioma'/></span>
										</div>
										<!-- Fim conversa -->
										<div class="widget">
												<div class="widget-head" style="height: height: 30px!important">
													<h4 class="heading"><i18n:message key='rh.formacao'/></h4>
												</div>
												<div class="widget-body">
											<!-- Table  -->
												<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
													<div class="row-fluid"> 
													</div>
														<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblIdioma" aria-describedby="DataTables_Table_0_info">
													
															<!-- Table heading -->
															<thead>
																<tr role="row">
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 21%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='menu.nome.idioma'/></th>
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 21%;" aria-label="Platform(s): activate to sort column ascending"><i18n:message key='rh.escrita'/></th>
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 21%;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='rh.leitura'/></th>
																<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 22%;" aria-label="CSS grade: activate to sort column ascending"><i18n:message key='rh.conversacao'/></th>
																<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																	style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th></tr>
															</thead>
															<!-- // Table heading END -->
															
															<!-- Table body -->
															
															<!-- // Table body END -->
															
														<tbody role="alert" aria-live="polite" aria-relevant="all">
														</tbody>
													</table>
												</div>
											<!-- // Table END -->
												</div>
											</div>
									</div>
									
									</div>
								</div>
								<!-- // FIM ETAPA 2 -->
								
								<!-- Etapa 3 -->
								<div id="tab3" class="tab-pane">
									<div>
										<strong><i18n:message key='rh.experiencia'/></strong>
										<p class="muted"><i18n:message key='rh.entreOsDados'/></p>
									</div>
										
									<!-- nome da empresa -->
									<div class="row-fluid">
										<label class="strong campoObrigatorio"><i18n:message key='lookup.nomeEmpresa'/></label>
										<div class="controls">
											<input type="text" class="span6" value="" id="experiencia#empresa" name="experiencia#empresa" maxlength="80">
										</div>
									</div>
									<!-- // Fim Formação -->
										
									<!-- Função -->
									<div class="row-fluid">
										<label class="strong campoObrigatorio"><i18n:message key='rh.funcao'/></label>
										<div class="controls">
											<input type="text" class="span6" value="" id="experiencia#funcao" name="experiencia#funcao" maxlength="80">
										</div>
									</div>
									<!-- // Fim Função -->
									
									<!-- Localidade -->
									<div class="row-fluid">
										<label class="strong"><i18n:message key='menu.nome.localidade'/></label>
										<div class="controls">
											<input type="text" class="span6" value="" id="experiencia#localidade" name="experiencia#localidade" maxlength="80">
										</div>
									</div>
									<!-- // Fim Localidade -->
									
									<!-- Periodo -->
									<div class="row-fluid">
										<label class="strong campoObrigatorio"><i18n:message key='citcorpore.texto.periodo'/></label>
									</div>
									
									<div class="row-fluid">
									
										<div class="span3">
										
											<label for="experiencia#idMesInicio" class="strong campoObrigatorio"><i18n:message key='citcorpore.texto.tempo.Mes'/></label>
											<select name='experiencia#idMesInicio'>
												<option value=""><i18n:message key='citcorpore.comum.selecione'/></option> 
												<option value="1"><i18n:message key='citcorpore.texto.mes.janeiro'/></option>
												<option value="2"><i18n:message key='citcorpore.texto.mes.fevereiro'/></option>
												<option value="3"><i18n:message key='citcorpore.texto.mes.marco'/></option>
												<option value="4"><i18n:message key='citcorpore.texto.mes.abril'/></option>
												<option value="5"><i18n:message key='citcorpore.texto.mes.maio'/></option>
												<option value="6"><i18n:message key='citcorpore.texto.mes.junho'/></option>
												<option value="7"><i18n:message key='citcorpore.texto.mes.julho'/></option>
												<option value="8"><i18n:message key='citcorpore.texto.mes.agosto'/></option>
												<option value="9"><i18n:message key='citcorpore.texto.mes.setembro'/></option>
												<option value="10"><i18n:message key='citcorpore.texto.mes.outubro'/></option>
												<option value="11"><i18n:message key='citcorpore.texto.mes.novembro'/></option>
												<option value="12"><i18n:message key='citcorpore.texto.mes.dezembro'/></option>
											</select>
										</div>
										
										<div class="span2">
											<label for="experiencia#idMesFim" class="strong campoObrigatorio"><i18n:message key='citcorpore.texto.tempo.Ano'/></label>
											<input type="text" value="" onkeypress="return somenteNumero(event);" id="experiencia#anoInicio" name="experiencia#anoInicio" maxlength="4">
										</div>
										
										<div class="span3">
										
											<label class="strong campoObrigatorio"><i18n:message key='citcorpore.texto.tempo.Mes'/></label>
											<select name='experiencia#idMesFim'>
												<option value=""><i18n:message key='citcorpore.comum.selecione'/></option> 
												<option value="1"><i18n:message key='citcorpore.texto.mes.janeiro'/></option>
												<option value="2"><i18n:message key='citcorpore.texto.mes.fevereiro'/></option>
												<option value="3"><i18n:message key='citcorpore.texto.mes.marco'/></option>
												<option value="4"><i18n:message key='citcorpore.texto.mes.abril'/></option>
												<option value="5"><i18n:message key='citcorpore.texto.mes.maio'/></option>
												<option value="6"><i18n:message key='citcorpore.texto.mes.junho'/></option>
												<option value="7"><i18n:message key='citcorpore.texto.mes.julho'/></option>
												<option value="8"><i18n:message key='citcorpore.texto.mes.agosto'/></option>
												<option value="9"><i18n:message key='citcorpore.texto.mes.setembro'/></option>
												<option value="10"><i18n:message key='citcorpore.texto.mes.outubro'/></option>
												<option value="11"><i18n:message key='citcorpore.texto.mes.novembro'/></option>
												<option value="12"><i18n:message key='citcorpore.texto.mes.dezembro'/></option>
											</select>
										</div>
										
										<div class="span2">
										
											<label for="experiencia#anoFim" class="strong campoObrigatorio"><i18n:message key='citcorpore.texto.tempo.Ano'/></label>
											<input type="text" value="" id="experiencia#anoFim" onkeypress="return somenteNumero(event);" name="experiencia#anoFim" maxlength="4">
										
										</div>

									</div>
									
									<div class="input-append">
											<span onclick="addExperiencia()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarExperiencia'/></span>
										</div>
									<!-- // Fim Periodo -->
									
									<div class="widget">
										<div class="widget-head" style="height: height: 30px!important">
											<h4 class="heading"><i18n:message key='rh.experiencias'/></h4>
										</div>
										<div class="widget-body">
									<!-- Tabela Experiencias -->
										<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
											<div class="row-fluid"> 
											</div>
												<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblExperiencias" aria-describedby="DataTables_Table_0_info">
											
													<!-- Table heading -->
													<thead>
														<tr role="row">
														<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 25%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='menu.nome.empresa'/></th>
														<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 25%;" aria-label="Platform(s): activate to sort column ascending"><i18n:message key='rh.funcao'/></th>
														<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 20%;" aria-label="Eng. vers.: activate to sort column ascending"><i18n:message key='localidadeFisica.localidadeFisica'/></th>
														<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 25%;" aria-label="CSS grade: activate to sort column ascending"><i18n:message key='citcorpore.texto.periodo'/></th>
														<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th></tr>
													</thead>
													<!-- // Table heading END -->
													
													<!-- Table body -->
													
													<!-- // Table body END -->
													
												<tbody role="alert" aria-live="polite" aria-relevant="all">
												</tbody>
											</table>
										</div>
									<!-- // Table END -->
										</div>
									</div>
								</div>
								<!-- // Fim Etapa 3 -->
								
								<!-- Etapa 4 -->
								<div id="tab4" class="tab-pane">
										<div>
											<strong><i18n:message key='rh.competenciasEspecialidades'/></strong>
											<p class="muted"><i18n:message key='rh.entreOsDados'/></p>
										</div>
										
										<!-- competencias -->
										<div class="row-fluid">
											<label class="strong"><i18n:message key='rh.competencias'/>*</label>
											<div class="input-append">
												<input type="text" class="span6" value="" id="competencia#descricao" name="competencia#descricao" maxlength="80">
												<span onclick="addCompetencia()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarCompetencias'/></span>
											</div>
										</div>
										<!-- // Fim competencias -->
										<div class="widget">
											<div class="widget-head" style="height: height: 30px!important">
												<h4 class="heading"><i18n:message key='rh.competencias'/></h4>
											</div>
											<div class="widget-body">
									
										<!-- Table  -->
											<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
												<div class="row-fluid"> 
												</div>
													<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblCompetencia" aria-describedby="DataTables_Table_0_info">
												
														<!-- Table heading -->
														<thead>
															<tr role="row">
															<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																style="width: 25%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='rh.competencias'/></th>
															<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
																style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
														</thead>
														<!-- // Table heading END -->
														
														<!-- Table body -->
														
														<!-- // Table body END -->
														
													<tbody role="alert" aria-live="polite" aria-relevant="all">
													</tbody>
												</table>
										<!-- End Table  -->
										</div>
										</div>
									</div>
									<div>
										<strong><i18n:message key='rh.certificacoes'/></strong>
										<p class="muted"><i18n:message key='rh.entreOsDados'/></p>
									</div>
										
									<!-- descrição da certificacao -->
									<div class="row-fluid">
										<label class="strong campoObrigatorio"><i18n:message key='rh.descricaoCertificacao'/></label>
										<div class="controls">
											<input type="text" class="span6" value="" id="certificacao#descricao" name="certificacao#descricao" maxlength="80">
										</div>
									</div>
									<!-- // Fim descrição da certificacao -->
									
									<!-- versao Certificacao -->
									<div class="row-fluid">
										<label class="strong campoObrigatorio"><i18n:message key='release.versao'/></label>
										<div class="controls">
											<input type="text" class="span6" value="" id="certificacao#versao" name="certificacao#versao" maxlength="80">
										</div>
									</div>
									<!-- // Fim versao Certificacao -->
									
									<!-- ano validade -->
									<div class="row-fluid">
										<label class="strong campoObrigatorio"><i18n:message key='rh.anoValidade'/></label>
										<div class="controls">
											<input type="text" class="span6" value="" onkeypress="return somenteNumero(event);" id="certificacao#validade" name="certificacao#validade" maxlength="4">
										</div>
									</div>
									<div class="input-append">
										<span onclick="addCertificacao()" class="btn btn-mini btn-primary btn-icon glyphicons circle_plus"><i></i><i18n:message key='rh.adicionarCertificacao'/></span>
									</div>
									<!-- // Fim versao Certificacao -->
									<div class="widget">
										<div class="widget-head" style="height: height: 30px!important">
											<h4 class="heading"><i18n:message key='rh.certificacoesm'/> :</h4>
										</div>
										<div class="widget-body">
									<!-- Table  -->
										<div role="grid" class="dataTables_wrapper form-inline" id="DataTables_Table_0_wrapper">
											<div class="row-fluid"> 
											</div>
												<table class="dynamicTable table table-striped table-bordered table-condensed dataTable" id="tblCertificacao" aria-describedby="DataTables_Table_0_info">
											
													<!-- Table heading -->
													<thead>
														<tr role="row">
														<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 45%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='calendario.descricao'/></th>
														<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 25%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='release.versao'/></th>
														<th class="sorting" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 25%;" aria-label="Browser: activate to sort column ascending"><i18n:message key='rh.anoValidade'/></th>
														<th class="sorting_asc" role="columnheader" tabindex="0" aria-controls="DataTables_Table_0" rowspan="1" colspan="1" 
															style="width: 5%;" aria-sort="ascending" aria-label="Rendering eng.: activate to sort column descending"></th>
													</thead>
													<!-- // Table heading END -->
													
													<!-- Table body -->
													
													<!-- // Table body END -->
													
												<tbody role="alert" aria-live="polite" aria-relevant="all">
												</tbody>
											</table>
										</div>
										</div>
								</div>
							</div>
							<!-- // Fim Etapa 4 -->
							
							<!-- ETAPA 5 -->
								<div id="tab5" class="tab-pane">
									<div class="widget">	
										<!-- Widget heading -->
										<div class="widget-head" style="height: height: 30px!important">
											<h4 class="heading glyphicons file_import"><i></i><i18n:message key='dataManager.upload'/></h4>
										</div>
									</div>
									
									<div class="widget-body">
										<div class="input-append">
											<span class="btn btn-mini btn-primary btn-icon glyphicons circle_plus" href="#modal_anexo" data-toggle="modal" data-target="#modal_anexo"><i></i><i18n:message key='rh.adicionarAnexos'/></span>
										</div>
									</div>
								</div>
								
								<!-- // FIM ETAPA 5 -->
							
							<!-- Wizard pagination controls -->
							<div class="pagination margin-bottom-none">
								<ul >
									<li id="liPaginacao1" class="primary previous first disabled"><a href="javascript:primeiro();"><i18n:message key='citcorpore.texto.numeral.ordinal.Primeiro'/></a></li>
									<li id="liPaginacao2" class="primary previous disabled"><a href="javascript:retroceder();"><i18n:message key='citcorpore.comum.anterior'/></a></li>
									<li id="liPaginacao3" class="last primary"><a href="javascript:avancarUltimo();"><i18n:message key='citcorpore.texto.adjetivo.Ultimo'/></a></li>
								  	<li id="liPaginacao4" class="next primary"><a href="javascript:avancar();"><i18n:message key='citcorpore.comum.proximo'/></a></li>
									<li id="liPaginacao5" style="display: none;" class="next finish primary"><a href="javascript:gravar();"><i18n:message key='rh.finalizar'/></a></li>
								</ul>
							</div>
							<div class="clearfix"></div>
							<!-- // Wizard pagination controls END -->
							
						</div>
					</div>
				</div>
			</form>
			</div>
			<!-- MODAL ANEXO ... -->
			<div class="modal hide fade in" id="modal_anexo" aria-hidden="false">
				<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key='rh.anexo'/></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
						<form name="formUpload" method="post" enctype="multipart/form-data">
								<cit:uploadControl id="uploadAnexos" title="<i18n:message key='rh.anexo'/>" style="height: 100px; width: 50%; border: 1px solid black;" form="document.formUpload" action="/pages/upload/upload.load" disabled="false" />								
								<font id="msgGravarDados" style="display:none" color="red"><i18n:message key="barraferramenta.validacao.solicitacao" /></font><br />				
						</form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
					<%-- 	<a href="#" class="btn btn-primary" data-dismiss="modal" onclick="gravarAnexo();"><i18n:message key="citcorpore.comum.gravar" /></a>  --%>
						<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
					</div>
					<!-- // Modal footer END -->
				</div>
					
				<!-- MODAL FOTO ... -->
						<div class="modal hide fade in" id="modal_foto" aria-hidden="false">
							<!-- Modal heading -->
								<div class="modal-header">
									<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
									<h3><i18n:message key='rh.adicionarFoto'/></h3>
								</div>
								<!-- // Modal heading END -->
								<!-- Modal body -->
								<div class="modal-body">
							    	<iframe name='frameUploadFoto' id='frameUploadFoto' src='about:blank' height="0" width="0"/></iframe>
									<form name='formFoto' method="post" ENCTYPE="multipart/form-data" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/uploadFile/uploadFile.load'>
										<table>
											<tr>
												<td>
													<i18n:message key='rh.arquivoFoto'/>:
												</td>
												<td>
													<input type='file' name='arquivo'/>
												</td>
											</tr>
											<tr>
												<td>
													<input type='button' name='btnEnviarImagem' value='<i18n:message key='citSmart.comum.enviar'/>' onclick='submitFormFoto()'/>
												</td>
											</tr>
										</table>
									</form>
						  </div>
						<!-- // Modal body END -->
						<!-- Modal footer -->
						<div class="modal-footer">
							<%-- <a href="#" class="btn btn-primary" data-dismiss="modal" onclick="gravarAnexo();"><i18n:message key="citcorpore.comum.gravar" /></a> --%> 
							<a href="#" class="btn " data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
						</div>
						<!-- // Modal footer END -->
					</div>
			</div>
				<!--  Fim conteudo-->
			
				<%@include file="/novoLayout/common/include/rodape.jsp" %>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/TelefoneCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/EmailCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/EnderecoCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/FormacaoCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ExperienciaCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ExperienciaProfissionalCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/CompetenciaCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/CertificacaoCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/IdiomaCurriculoDTO.js"></script>
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/CompetenciaCurriculo.js"></script>
				<script type="text/javascript" src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/UploadUtils.js'></script>
				<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jquery/jquery.maskedinput.js" type="text/javascript"></script>
				<script src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/include/js/templateCurriculo.js"></script>
				<script type="text/javascript">
				/*bruno.aquino: foi retirado o trecho de código abaixo de templateCurriculo.js pois não era possivel capturar o locale, este é incarregado de retirar a mascara do cpf quando a linguagem for inglês */
					addEvent(window, "load", load, false);
					function load(){	
						$("#cep").mask("99.999-999");
						if('<%=request.getSession().getAttribute("locale")%>'=="en"){
							$('#cpf').unmask();
						}
						else{
							$("#cpf").mask("999.999.999-99");
						}
						$("#telefone").mask("9999-9999");
						$("#dataNascimento").mask("99/99/9999");
						
					/* Desenvolvedor: Gilberto Nery - Data: 25/11/2013 - Horário: 15:00 - ID Citsmart: 0
					 * Alterado por luiz.borges - 09/12/2013 - 09:30 hrs - Internacionalização do datePicker
					 * Motivo/Comentário: Formato o campo Data de Nascimento para o padrão brasileiro (dd/mm/yyyy)
					 * */
						$('#dataNascimento').datepicker({
							dateFormat: 'dd/mm/yy',
							language: 'pt-BR',
							dayNamesMin: [i18n_message("citcorpore.texto.abreviado.diaSemana.domingo"),
							              i18n_message("citcorpore.texto.abreviado.diaSemana.segundaFeira"),
							              i18n_message("citcorpore.texto.abreviado.diaSemana.tercaFeira"),
							              i18n_message("citcorpore.texto.abreviado.diaSemana.quartaFeira"),
							              i18n_message("citcorpore.texto.abreviado.diaSemana.quintaFeira"),
							              i18n_message("citcorpore.texto.abreviado.diaSemana.sextaFeira"),
							              i18n_message("citcorpore.texto.abreviado.diaSemana.sabado")],
							monthNames: [ i18n_message("citcorpore.texto.mes.janeiro"), 
							              i18n_message("citcorpore.texto.mes.fevereiro"),
							              i18n_message("citcorpore.texto.mes.marco"),
							              i18n_message("citcorpore.texto.mes.abril"),
							              i18n_message("citcorpore.texto.mes.maio"),
							              i18n_message("citcorpore.texto.mes.junho"),
							              i18n_message("citcorpore.texto.mes.julho"),
							              i18n_message("citcorpore.texto.mes.agosto"),
							              i18n_message("citcorpore.texto.mes.setembro"),
							              i18n_message("citcorpore.texto.mes.outubro"),
							              i18n_message("citcorpore.texto.mes.novembro"),
							              i18n_message("citcorpore.texto.mes.dezembro") ]
						});
		    	}
			</script>
			</div>
		</div>
	</body>
</html>