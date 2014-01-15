<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>


<html>
	<head>	
		<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	String id = request.getParameter("id");

	%>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<script type="text/javascript" src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/UploadUtils.js'></script>
		<script src="../../novoLayout/common/include/js/entrevistaCandidato2.js"></script>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/entrevistaCandidato2.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
	
	</head>
	<body>
		<div class="container-fluid fixed ">
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/entrevistaCandidato/entrevistaCandidato'>
		      	    	<input type='hidden' name='idEntrevista' id='idEntrevista'/>
				       	<input type='hidden' name='idCurriculo' id='idCurriculo'/>
				       	<input type='hidden' name='idTriagem' id='idTriagem'/>
				       	<input type='hidden' name='tipoEntrevista' id='tipoEntrevista'/>
				       	<input type='hidden' name='idEntrevistador' id='idEntrevistador'/>
						<input type='hidden' name='serializeAtitudes' id='serializeAtitudes'/>
						<input type='hidden' name='atitude#idAtitudeOrganizacional' id='atitude#idAtitudeOrganizacional'/>
						<input type='hidden' name='atitude#avaliacao' id='atitude#avaliacao'/>
						<input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico'/>
						<input type='hidden' name='qtdCandidatosAprovados' id='qtdCandidatosAprovados' />
					
					<div class="widget-body span10">
					
						<div class="tab-pane active" id="account-settings">
						
							<!-- Row -->
							<div class="row-fluid">
							
								<!-- Column -->
								<div class="span3">
									<strong><i18n:message key='entrevistaCandidato.informacoesPessoais'/></strong>
									<p class="muted"><i18n:message key='entrevistaCandidato.informacoesPessoaisCandidato'/></p>
								</div>
								<!-- // Column END -->
								
								<!-- Column -->
								<div class="span9">
								 	<div class='span8'>
										<label><i18n:message key="citcorpore.comum.nome" /></label>
										<input type='text' name='candidato' class='span12' size='20' maxlength="100" readonly="readonly"/>
									</div>
									<div class='span4'>	
										<label ><i18n:message key="Idade" /></label>
										<input type='text' name='idade' class='span12' size='3' maxlength="4" readonly="readonly"/>
									</div>
									<div class="row-fluid">
									    <div class='span12'>
											<label ><i18n:message key="entrevista.cargoPretendido" /></label>
											<input type='text' name='cargoPretendido'  class='span12' maxlength="100" readonly="readonly"/>
										</div>
									</div>
								</div>
							</div>
							<!-- // Column END -->
						</div>
						<!-- // Row END -->
							
						<div class="separator line bottom"></div>
							
							<!-- Row -->
						<div class="row-fluid">
						
							<!-- Column -->
							<div class="span3">
								<strong><i18n:message key="coletaPreco.avaliacao"/></strong>
								<p class="muted"><i18n:message key="entrevistaCandidato.informacoesAvaliacaoCandidato"/></p>
							</div>
							<!-- // Column END -->
							
							<!-- Column -->
							<div class="span9">
								<div class="row-fluid">
									<div class='span12'>
										<label for="planoCarreira" class="campoObrigatorio"><i18n:message key="entrevista.planoCarreira" /></label>
										<textarea rows="3" cols="80"  class='span12' name='planoCarreira' maxlength="100"></textarea>
									</div>
								</div>
								<div class="row-fluid">
									<div class='span12'>
										<label for="caracteristicas" class="campoObrigatorio"><i18n:message key="entrevista.caracteristicas" /></label>
										<textarea rows="3" cols="80"  class='span12' name='caracteristicas'></textarea>
									</div>
								</div>
								<div class="row-fluid">
									<div class='span12'>
										 <label for="trabalhoEmEquipe" class="campoObrigatorio"><i18n:message key="entrevista.trabalhoEquipe" /></label>
										 <textarea rows="3" cols="80" class='span12' name='trabalhoEmEquipe'></textarea>
									</div>
								</div>
								<div class="row-fluid">
									<div class='span6'>
										<label class="campoObrigatorio"><i18n:message key="entrevista.possuiOutraAtividade" /></label>
										<div>
											<input type='radio' style="cursor: pointer" name='possuiOutraAtividade' value='S' onclick='configuraAtividade()'/> <i18n:message key="citcorpore.comum.sim"/>
			                                <input type='radio' style="cursor: pointer" name='possuiOutraAtividade' value='N' onclick='configuraAtividade()'/> <i18n:message key="citcorpore.comum.nao"/>
										</div>
									</div>
									<div class='span6' id='divAtividade'>
										<label for="outraAtividade" class="campoObrigatorio"><i18n:message key="entrevista.qual"/></label>
										<div>
					               			<input type='text' name='outraAtividade' class='span12' size="40" maxlength="40"/>
					               		</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class='span12'>
										<label><i18n:message key="entrevista.concordaClausulaExclusividade" /></label>
										<div>
											<input type='radio' style="cursor: pointer" name='concordaExclusividade' value='S' /> <i18n:message key="citcorpore.comum.sim"/>
				                            <input type='radio' style="cursor: pointer" name='concordaExclusividade' value='N' /> <i18n:message key="citcorpore.comum.nao"/>
										</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class='span12'>
										<label for="metodosAdicionais"><i18n:message key="entrevista.metodosAdicionais" />
										<a href="#modal_anexo" data-toggle="modal" class="glyphicons single paperclip" style="float: right;"><i></i><i18n:message key='entrevistaCandidato.cliqueAquiIncluirAnexo'/></a></label>
										
										<textarea rows="3" cols="80" class='span12' id='metodosAdicionais' name='metodosAdicionais'></textarea>
									</div>
								</div>
							</div>
							<!-- Column -->
						</div>
						
						<div class="row-fluid">
							<div class="span3">
								<strong><i18n:message key="entrevistaCandidato.salarioDisponibilidade"/></strong>
								<p class="muted"><i18n:message key="entrevistaCandidato.informacoesPretensaoSalarial"/></p>
							</div>
							<div class="span9">
								<div class="row-fluid">
									<div class='span4'>
										<label for="salarioAtual" class="campoObrigatorio"><i18n:message key="entrevista.salarioAtualUltimo" /></label>
										<div>
              		  							<input type='text' name='salarioAtual' size="40" maxlength="40" class="span12 Format[Moeda]"/>
                							</div>
									</div>
									<div class='span4'>
										<label for="pretensaoSalarial" class="campoObrigatorio"><i18n:message key="entrevista.pretensaoSalarial" /></label>
		               					<div>
		               		 				 <input type='text' name='pretensaoSalarial' size="40" maxlength="40" class="span12 Format[Moeda]"/>
		                 				</div>
									</div>
									<div class='span4'>
										<label for="dataDisponibilidade" class="campoObrigatorio"><i18n:message key="entrevista.disponibilidadeComecar" /></label>
		                    		 	<div>
		                         			<input type='text' name="dataDisponibilidade" id="dataDisponibilidade" maxlength="10" size="10" class="span12 citdatepicker" />
		                     			</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span3">
								<strong><i18n:message key="entrevistaCandidato.informacoesAdicionais"/></strong>
								<p class="muted"><i18n:message key="avaliacaoFonecedor.observacao"/></p>
							</div>
							<div class="span9">
								<div class="row-fluid">
									<div class='span12'>
										<label for="competencias" class="campoObrigatorio"><i18n:message key="entrevista.competenciasObservadas" /></label>
		             					<div>
		             						<textarea rows="3" cols="80" class='span12' name='competencias'></textarea>
		             					</div>
									</div>
								</div>
								<div class="row-fluid">
									<div class='span12'>
										<label for="observacoes"><i18n:message key="entrevista.observacoes" /></label>
		             					<div>
		             						<textarea rows="3" cols="80" class='span12' name='observacoes'></textarea>
		             					</div>
		             				</div>
	             				</div>
								<div class="row-fluid">
		             				<div class='span12'>
										<label class="campoObrigatorio"><i18n:message key="solicitacaoCargo.atitudes" /></label>
										<div style='overflow:auto;max-height: 155px;'>
											<table id="tblAtitudes" class='span6'>
												<tr>
												</tr>
				                   			</table>
				                   		</div>
									</div>
								</div>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class="span3">
								<strong><i18n:message key="entrevistaCandidato.resultadoFinal"/></strong>
								<p class="muted"><i18n:message key="entrevistaCandidato.notaResultanteEntrevista"/></p>
							</div>
							<div class="span9">
								<div class="row-fluid">
									<div class='span6'>
										<label class="campoObrigatorio"><i18n:message key="entrevista.resultado" /></label>
											<label><input type='radio' style="cursor: pointer" name='resultado' value='A'/><i18n:message key="itemRequisicaoProduto.aprovado"/></label>
		                            		<label><input type='radio' style="cursor: pointer" name='resultado' value='R' /><i18n:message key="entrevistaCandidato.reprovado"/></label>
											<label><input type='radio' style="cursor: pointer" name='resultado' value='S' /><i18n:message key="entrevistaCandidato.segundaOportunidade"/></label>
											<label><input type='radio' style="cursor: pointer" name='resultado' value='D' /><i18n:message key="entrevistaCandidato.descarte"/></label>				
									</div>
									<div class='span6'>
										<label for="notaAvaliacao"><i18n:message key="entrevista.notaAvaliacao" /></label>
		                    		 	<div>
		                         			<input type='text' name="notaAvaliacao" id="notaAvaliacao" maxlength="10" size="10" class="span12 Format[Moeda]" />
		                     			</div>
									</div>
								</div>
							</div>
						</div>
							<!-- // Row END -->
							
						<!-- Form actions -->
						<div class="form-actions" style="margin: 0;">
							<button type="button" data-dismiss="modal" class="btn  btn-primary " onclick='gravar();' id="btGravar"><i18n:message key="citcorpore.comum.gravar" /></button>
						</div>
						<!-- // Form actions END -->
						
					</div>
				</form>
			
				<div class="modal hide fade in" id="modal_anexo" aria-hidden="false">

					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3><i18n:message key="rh.anexo"/></h3>
					</div>
					<!-- // Modal heading END -->
					
					<!-- Modal body -->
					<div class="modal-body">
						<form name="formUpload" method="post" enctype="multipart/form-data">
							<cit:uploadControl id="uploadAnexos"
								title="Anexos"
								style="height: 100px; width: 100%; border: 0px solid black!important;"
								form="document.formUpload" 
								action="/pages/upload/upload.load" 
								disabled="false" />								
							<font id="msgGravarDados" style="display:none" color="red"><i18n:message key="barraferramenta.validacao.solicitacao" /></font><br />				
							<button id="btnGravarTelaAnexos" name="btnGravarTelaAnexos" onclick="gravarAnexo();" type="button" style="display:none">
								<i18n:message key="citcorpore.comum.gravar" />
							</button>
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
				<!--  Fim conteudo-->
				
				<%@include file="/novoLayout/common/include/libRodape.jsp" %> 
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
    			<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoPessoalDTO.js"></script>
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/CurriculoDTO.js"></script>
			</div>
		</div>
	</body>
</html>