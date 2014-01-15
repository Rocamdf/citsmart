<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>

<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
%>
<%@include file="/include/security/security.jsp" %>
<!--[if lt IE 7]> <html lang="en-us" class="no-js ie6"> <![endif]-->
<!--[if IE 7]>    <html lang="en-us" class="no-js ie7"> <![endif]-->
<!--[if IE 8]>    <html lang="en-us" class="no-js ie8"> <![endif]-->
<!--[if IE 9]>    <html lang="en-us" class="no-js ie9"> <![endif]-->
<!--[if gt IE 8]><!--> <html lang="pt-br" class="no-js"> <!--<![endif]-->

	<title>CIT Corpore</title>
	<%@include file="/include/noCache/noCache.jsp" %>

	<%@include file="/include/titleComum/titleComum.jsp" %>
	
	<%@include file="/include/header.jsp" %>
	
	<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>
		
<script>
	$(document).ready(function() {
		$( "#POPUP_OBJ" ).dialog({
			title: 'Item de Workflow',
			width: 800,
			height: 400,
			modal: true,
			autoOpen: false,
			resizable: false,
			show: "fade",
			hide: "fade"
			}); 
				
		$("#POPUP_OBJ").hide();

		$( "#sortable" ).sortable({
			cancel: ".ui-state-disabled"
		});
		$( "#sortable" ).disableSelection();	
	});

	function geraSortable(id){
		$( "#" + id ).sortable();
	}

	function mostraAddObj(){
		$( "#POPUP_OBJ" ).dialog( 'open' );
	}

	function adicionaItem(){
		document.formItem.fireEvent('addItem');
	}

	function selecionaItemWorkflow(obj){
		if (obj.value == '1'){
			document.getElementById('divDecisao').style.display = 'none';
		}
		if (obj.value == '2'){
			document.getElementById('divDecisao').style.display = 'block';
		}
	}
</script>

<style>
	#sortable { list-style-type: none; margin: 0; padding: 0; width: 100%; }
	#sortable li { margin: 0 3px 3px 3px; padding: 0.4em; padding-left: 1.5em; font-size: 1.4em; height: 100%; }
	#sortable li span { position: absolute; margin-left: -1.3em; }
</style>

</head>
<body>	
<div id="wrapper">
	<%@include file="/include/menu_vertical.jsp"%>
<!-- Conteudo -->
	<div id="main_container" class="main_container container_16 clearfix">
					
		<div class="flat_area grid_16">
				<h2>Workflow</h2>						
		</div>
		
	<form name='form' method="POST" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/designerWorkflow/designerWorkflow'>
		<div class="box grid_16 tabs">
			<ul class="tab_header clearfix">
				<li>
					<a href="#tabs-1">Cadastro de Workflow</a>
				</li>
				<li>
					<a href="#tabs-2" class="round_top">Pesquisa Workflow</a>
				</li>
			</ul>				
			<a href="#" class="toggle">&nbsp;</a>
			<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="section">											
						<div id='init' style='text-align: center;'>
							<div style='text-align: center;'>
								<table style='text-align: center;' width="100%">
									<tr>
										<td style='text-align: center;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/circle_green.png' border='0'/>
										</td>
										<td style='text-align: right;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/plus.png' border='0' style='cursor: pointer' onclick='mostraAddObj();'/>
										</td>
										<td style='text-align: right;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/vazio.png' border='0'/>
										</td>										
									</tr>
								</table>
							</div>
							<div style='text-align: center;'>
								<table style='text-align: center;' width="100%">
									<tr>
										<td style='text-align: center;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/arrow_down_2.png' border='0'/>
										</td>
										<td style='text-align: right;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/vazio.png' border='0'/>
										</td>										
										<td style='text-align: right;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/vazio.png' border='0'/>
										</td>
									</tr>
								</table>								
							</div>						
						</div>
						<div id='sortable' style='text-align: center;'>
						
						</div>
						<div id='setaFim' style='text-align: center; display: none'>
							<div style='text-align: center;'>
								<table style='text-align: center;' width="100%">
									<tr>
										<td style='text-align: center;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/arrow_down_2.png' border='0'/>
										</td>
										<td style='text-align: right;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/vazio.png' border='0'/>
										</td>										
										<td style='text-align: right;'>
											<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/vazio.png' border='0'/>
										</td>
									</tr>
								</table>								
							</div>						
						</div>						
						<div id='end' style='text-align: center;'>
							<table style='text-align: center;' width="100%">
								<tr>
									<td style='text-align: center;'>
										<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/circle_red.png' border='0'/>
									</td>
									<td style='text-align: right;'>
										<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/vazio.png' border='0'/>
									</td>
									<td style='text-align: right;'>
										<img src='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/vazio.png' border='0'/>
									</td>									
								</tr>
							</table>							
						</div>								
					</div>
				</div>
				<div id="tabs-2" class="block">
					<div class="section">
											
					</div>
				</div>								
			</div>
		</div>		
	</form>
	</div>
	
<div id="POPUP_OBJ" style='width: 600px; height: 400px' >
	<form name='formItem' method="POST" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/designerWorkflow/designerWorkflow'>
	<table>
		<tr>
			<td>
				Item de Workflow:
			</td>
			<td>
				<select name='type' onchange='selecionaItemWorkflow(this)'>
					<option value='1'>Passo</option>
					<option value='2'>Decisão</option>
				</select>
			</td>
		</tr>
		<tr>
			<td>
				Descrição:
			</td>
			<td>
				<input type='text' name='nome'/>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<div id='divDecisao'>
					
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="2">
				<button name="btnSalvar" type='button' onclick='adicionaItem()'>OK</button>
			</td>
		</tr>
	</table>
	</form>
</div>

<!-- Fim da Pagina de Conteudo -->
</div>

<%@include file="/include/footer.jsp"%>


</body>
</html>
