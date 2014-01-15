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
		<script src="../../novoLayout/common/include/js/entrevistaRequisicaoPessoal2.js"></script>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/entrevistaRequisicaoPessoal2.css"/>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>
		
		<style type="text/css">
		
		</style>
		<script type="text/javascript">
		  
		  function gerarImgExclusaoCurriculo(row, obj) {
/* 		   		row.cells[2].innerHTML = "<a class='close' data-dismiss='alert'></a><p>Candidato está na Lista Negra</p>"; */

			     row.cells[3].innerHTML = '<a class="btn-action glyphicons edit btn-success" onclick="abrePopupEntrevista('+obj.idCurriculo+','+obj.idTriagem+',\''+obj.tipoEntrevista+'\')"><i></i></a> | ' 
		         row.cells[3].innerHTML += '<a class="btn-action glyphicons nameplate btn-success" onclick="abrePopupCurriculo('+obj.idCurriculo+')"><i></i></a> | ' ;
		         row.cells[3].innerHTML += '<a href="#" class="btn-action glyphicons history btn-success" onclick="abrePopupHistoricoCandidato('+obj.idCurriculo+')"><i></i></a>';
				
		       if (obj.caminhoFoto != "")
		       	row.cells[0].innerHTML = '<img src="' + obj.caminhoFoto + '" border=0 width="128px" />';

		       else
		    	row.cells[0].innerHTML = '<div class="col_100"><img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/novoLayout/common/theme/images/avatar.jpg" border=0  /></div>';
		  }

		  function abrePopupCurriculo(idCurriculo){
			  	document.getElementById('frameCurriculo').src = URL_SISTEMA+"pages/templateCurriculo/templateCurriculo.load?iframe=true&idCurriculo=" + idCurriculo ;
				$("#modal_curriculo").modal("show");
			}

		  function abrePopupEntrevista(idCurriculo, idTriagem, tipoEntrevista) {
				var idSolicitacao = document.form.idSolicitacaoServico.value;
				document.getElementById('frameFicha').src =URL_SISTEMA+"pages/entrevistaCandidato2/entrevistaCandidato2.load?idCurriculo=" + idCurriculo + '&idTriagem=' + idTriagem + '&tipoEntrevista=' + tipoEntrevista + '&idSolicitacaoServico=' + idSolicitacao;
				$("#modal_ficha").modal("show");
			}

		  function abrePopupHistoricoCandidato(idCurriculo) { 
	    	  /* document.formSugestaoCurriculos.idSolicitacaoServico.value = document.form.idSolicitacaoServico.value; */
	    	  var URL_SISTEMA = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/';
	    	  document.getElementById("frameHistoricoCandidato").src =  URL_SISTEMA+'pages/historicoCandidato/historicoCandidato.load?iframe=true&idCurriculo=' + idCurriculo;
	    	  $("#modal_historicoCandidato").modal("show"); 
	      }
		</script>
	
	</head>
	<body>
		<div class="container-fluid fixed ">
	
			<div id="wrapper">
					
				<!-- Inicio conteudo -->
				
					<div class="box-generic">
						<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/historicoCandidato/historicoCandidato'>
						<div >
							<div>
							<h2><i18n:message key="rh.historicoCandidato" /></h2> 
							</div>
								<table class="table  table-condensed" id="tblHistoricoCandidato" >
							
									<!-- Table heading -->
										<tr >
											<th style="width: 20%;"><i18n:message key="rh.solicitacaoServico" /></th>
											<th><i18n:message key="rh.solicitacaoServico" /></th>
											<th><i18n:message key="rh.funcao" /></th>
											<th><i18n:message key="entrevista.resultado" /></th>
										</tr>
									<!-- // Table heading END -->
									
									<!-- Table body -->
									
									<!-- // Table body END -->
							</table>
						</div>
					</form>
				  </div>
				 
			
				</div>
				<!--  Fim conteudo-->
				
				 <%@include file="/novoLayout/common/include/libRodape.jsp" %> 
				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
    			<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoPessoalDTO.js"></script>
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/TriagemRequisicaoPessoalDTO.js"></script>
   				<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/CurriculoDTO.js"></script>
				
			</div>
	
	</body>
</html>