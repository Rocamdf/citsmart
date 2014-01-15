<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>

<!doctype html>
<html>
<head>
<%
	response.setHeader( "Cache-Control", "no-cache");
	response.setHeader( "Pragma", "no-cache");
	response.setDateHeader ( "Expires", -1);
	
	//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
	String iframe = "";
	iframe = request.getParameter("iframe");
	
%>
<%@include file="/include/security/security.jsp" %>
<title><i18n:message key="citcorpore.comum.title"/></title><style type="text/css">
.tab_header ul, .tabs-wrap ul {
	height: auto !important;
	border: 0 !important;
}
</style>
<%@include file="/include/header.jsp" %>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>

<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.core.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.widget.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.position.js"></script>										
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.autocomplete.js"></script>	
<script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/painel/jquery.ui.datepicker.js?nocache=<%=new java.util.Date().toString()%>"></script>	
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.ui.datepicker-pt-BR.js"></script>	
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/lookup/jquery.ui.lookup.js"></script>

<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/themes/gray/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/themes/icon.css">
<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery-easy.css">
<link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.datepicker.css">		
<link rel="stylesheet" type="text/css" href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/css/jquery-ui-1.8.21.custom.css" />			

<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/locale/easyui-lang-pt_BR.js"></script>
					
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/json2.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/formparams/jquery.formparams.js"></script>					
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.parser.js"></script>

<script>
	var objTab = null;
	addEvent(window, "load", load, false);
	function load(){
		document.form.afterRestore = function () {
		}
	}

	function gerarInformacoes(){
		if (document.form.validate()){
			document.getElementById('divInfo').innerHTML = '<b>Aguarde...</b>';
			document.form.fireEvent('avalia');
		}
	}
	
	function selecionaExternalConnection(){
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('selecionaExternalConnection');
	}
	
	function selecionaOrigemDestinoDados(){
		document.form.fireEvent('getOrigemDestinoDados');
	}
	
	function mostrarOrigem(idParm){
		var ed = $('#tt').datagrid('getEditor', {index:idParm,field:'idorigem'});
		$(ed.target).combobox({
			data:origem,
			valueField:'id',
			textField:'text'
		});
	}
	function mostrarDestino(idParm){
		var ed = $('#tt').datagrid('getEditor', {index:idParm,field:'iddestino'});
		$(ed.target).combobox({
			data:destino,
			valueField:'id',
			textField:'text'
		});
	}
	function carregarDados(){
		$('#tt').datagrid('acceptChanges');
		var rowsMatriz = $('#tt').datagrid('getRows');
		var dadosStrMatriz = '';
		var jsonAuxMatriz = '';
		for (var j = 0; j < rowsMatriz.length; j++){
			var json_data = JSON.stringify(rowsMatriz[j]);
			if (dadosStrMatriz != ''){
				dadosStrMatriz = dadosStrMatriz + ',';
			}
			dadosStrMatriz = dadosStrMatriz + json_data;					
		}
		if (dadosStrMatriz != ''){
			jsonAuxMatriz = jsonAuxMatriz + '{"MATRIZ": [' + dadosStrMatriz + ']}';
		}			
		document.form.jsonMatriz.value = jsonAuxMatriz;	
		JANELA_AGUARDE_MENU.show();
		document.form.fireEvent('carregarDados');
	}
	function gravar(){
		$('#tt').datagrid('acceptChanges');
		var rowsMatriz = $('#tt').datagrid('getRows');
		var dadosStrMatriz = '';
		var jsonAuxMatriz = '';
		for (var j = 0; j < rowsMatriz.length; j++){
			var json_data = JSON.stringify(rowsMatriz[j]);
			if (dadosStrMatriz != ''){
				dadosStrMatriz = dadosStrMatriz + ',';
			}
			dadosStrMatriz = dadosStrMatriz + json_data;					
		}
		if (dadosStrMatriz != ''){
			jsonAuxMatriz = jsonAuxMatriz + '{"MATRIZ": [' + dadosStrMatriz + ']}';
		}			
		document.form.jsonMatriz.value = jsonAuxMatriz;			
		document.form.fireEvent('gravar');
	}
	function LOOKUP_IMPORTCONFIG_select(id,desc){
		document.form.idImportConfig.value = id;
		JANELA_AGUARDE_MENU.show();
		limpaGrid();
		document.form.restore({idImportConfig:document.form.idImportConfig.value});
		$( '#tabs' ).tabs('select', 0);
	}
	function adicionaLinha(orig,dest,scri){
		try{
			$('#tt').datagrid('endEdit', lastIndex);
		}catch(e){}
		$('#tt').datagrid('appendRow',{
			idorigem:orig,
			iddestino:dest,
			script:scri
		});		
	}
	function limpaGrid(){
		origem = null;
		destino = null;		
		$('#tt').datagrid('rejectChanges');
		var rowsMatriz = $('#tt').datagrid('getRows');
		while(rowsMatriz.length > 0){
			for (var j = 0; j < rowsMatriz.length; j++){
				try{
					var index = $('#tt').datagrid('getRowIndex', rowsMatriz[j]);
					$('#tt').datagrid('deleteRow', index);
				}catch(e){
				}
			}
			$('#tt').datagrid('acceptChanges');
			var rowsMatriz = $('#tt').datagrid('getRows');
		}
		geraGrid();
	}
	function validaTipo(obj){
		if (obj.value != 'J'){
			alert('<i18n:message key="importmanager.implementacao.futura"/>');
			HTMLUtils.setValue('tipo', 'J');
		}
	}
</script>

	<script>
		var origem = null;
		var destino = null;
		var products = [
		    {productid:'FI-SW-01',name:'Koi'},
		    {productid:'K9-DL-01',name:'Dalmation'},
		    {productid:'RP-SN-01',name:'Rattlesnake'},
		    {productid:'RP-LI-02',name:'Iguana'},
		    {productid:'FL-DSH-01',name:'Manx'},
		    {productid:'FL-DLH-02',name:'Persian'},
		    {productid:'AV-CB-01',name:'Amazon Parrot'}
		];
		function productFormatter(value){
			for(var i=0; i<products.length; i++){
				if (products[i].productid == value) return products[i].name;
			}
			return value;
		}
		$(function(){
			geraGrid();
		});
		var lastIndex;
		function geraGrid(){
			$('#tt').datagrid({
				toolbar:[{
					text:'append',
					iconCls:'icon-add',
					handler:function(){
						$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('appendRow',{
							idorigem:'',
							iddestino:'',
							script:''
						});
						lastIndex = $('#tt').datagrid('getRows').length-1;
						$('#tt').datagrid('selectRow', lastIndex);
						$('#tt').datagrid('beginEdit', lastIndex);
						mostrarOrigem(lastIndex);
						mostrarDestino(lastIndex);
					}
				},'-',{
					text:'delete',
					iconCls:'icon-remove',
					handler:function(){
						var row = $('#tt').datagrid('getSelected');
						if (row){
							var index = $('#tt').datagrid('getRowIndex', row);
							$('#tt').datagrid('deleteRow', index);
						}
					}
				},'-',{
					text:'accept',
					iconCls:'icon-save',
					handler:function(){
						$('#tt').datagrid('acceptChanges');
					}
				},'-',{
					text:'reject',
					iconCls:'icon-undo',
					handler:function(){
						$('#tt').datagrid('rejectChanges');
					}
				},'-',{
					text:'GetChanges',
					iconCls:'icon-search',
					handler:function(){
						var rows = $('#tt').datagrid('getChanges');
						alert('changed rows: ' + rows.length + ' lines');
					}
				}],
				onBeforeLoad:function(){
					$(this).datagrid('rejectChanges');
				},
				onClickRow:function(rowIndex){
					if (lastIndex != rowIndex){
						$('#tt').datagrid('endEdit', lastIndex);
						$('#tt').datagrid('selectRow', rowIndex);
						$('#tt').datagrid('beginEdit', rowIndex);
						mostrarOrigem(rowIndex);
						mostrarDestino(rowIndex);						
					}
					lastIndex = rowIndex;
				}
			});
		}
	</script>


<%
//se for chamado por iframe deixa apenas a parte de cadastro da página
if(iframe != null){%>
<style>
div#main_container {
	margin: 10px 10px 10px 10px;
	width: 100%;
}
</style>
<%}%>

</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title="Aguarde... Processando..."
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

<body id='body' class="easyui-layout" style='background-color: white;'>
	<div data-options="region:'north',split:false" style="height: 125px;" class="dinamic-menu">
		<%@include file="/include/menu_horizontal.jsp"%>
	</div>
	<div data-options="region:'center'" title="Import Manager">
<!-- Conteudo -->
	<div id="tabs" class="easyui-tabs" data-options="tools:'#tab-tools'">
		<div id="tabs-1" data-options="tools:'#p-tools'" style="width:100% !important;" title="Import Manager">
			<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/importManager/importManager'>
				<input type='hidden' name='jsonMatriz'/>
				<input type='hidden' name='idImportConfig'/>
				<input type='hidden' name='auxData'/>
				<table>
					<tr>
						<td colspan="2">
							<table>
								<tr>
									<td>
										<i18n:message key="importmanager.tipo"/>:*
									</td>
									<td>
										<select name='tipo' id='tipo' onchange='validaTipo(this)'>
											<option value='J'><i18n:message key="importmanager.tipo.jdbc"/></option>
											<option value='C'><i18n:message key="importmanager.tipo.csvfile"/></option>
											<option value='T'><i18n:message key="importmanager.tipo.txtfile"/></option>
										</select>
									</td>							
								</tr>
							</table>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<div id='divJdbc'>
								<table>
									<tr>
										<td colspan="2">
											<b><i18n:message key="importmanager.origem"/></b>
										</td>									
									</tr>
									<tr>
										<td>
											<i18n:message key="importmanager.conexao"/>:*
										</td>
										<td>
											<select name='idExternalConnection' id='idExternalConnection' onchange='selecionaExternalConnection()'>
											</select>
										</td>
									</tr>
									<tr>
										<td>
											<i18n:message key="importmanager.tablename"/>:*
										</td>
										<td>
											<select name='tabelaOrigem' id='tabelaOrigem' onchange='selecionaOrigemDestinoDados()'>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<b><i18n:message key="importmanager.destino"/></b>
										</td>									
									</tr>
									<tr>
										<td>
											<i18n:message key="importmanager.tablename"/>:*
										</td>
										<td>
											<select name='tabelaDestino' id='tabelaDestino' onchange='selecionaOrigemDestinoDados()'>
											</select>
										</td>
									</tr>
									<tr>
										<td colspan="2">
											<b><i18n:message key="importmanager.nome"/></b>
										</td>									
									</tr>	
									<tr>
										<td>
											<i18n:message key="importmanager.nome"/>:*
										</td>
										<td>
											<input type='text' name='nome' size="100" maxlength="100"/>
										</td>
									</tr>																																												
								</table>
								<div>
									<table id="tt" style="width:800px;height:400px"
											data-options="iconCls:'icon-edit',singleSelect:true,idField:'idorigem',url:'datagrid_data2.json'"
											title="<i18n:message key="importmanager.dadosimportacao"/>">
										<thead>
											<tr>
												<th data-options="field:'idorigem',width:200,
														editor:{
															type:'combobox',
															options:{
																valueField:'id',
																textField:'text',
																data:origem,
																required:true
															}
														}"><i18n:message key="importmanager.origem"/></th>
												<th data-options="field:'iddestino',width:200,
														editor:{
															type:'combobox',
															options:{
																valueField:'id',
																textField:'text',
																data:destino,
																required:true
															}
														}"><i18n:message key="importmanager.destino"/></th>														
												<th data-options="field:'script',width:400,editor:'textarea'"><i18n:message key="importmanager.script"/></th>
											</tr>
										</thead>
									</table>								
								</div>
								<div>
									<table>
										<tr>
											<td>
												<button type="button" onclick='gravar()'><i18n:message key="citcorpore.comum.gravar"/></button>
											</td>
											<td>
												<button type="button" onclick='carregarDados()'><i18n:message key="importmanager.executar"/></button>
											</td>
										</tr>
									</table>
								</div>
							</div>
						</td>
					</tr>
				</table>
			</form>
			</div>
			<div id="tabs-2" data-options="tools:'#p-tools'" style="width:100% !important;" title="<i18n:message key="citcorpore.comum.pesquisa" />">
						<div class="section">
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_IMPORTCONFIG' id='LOOKUP_IMPORTCONFIG' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>			
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>
</body>
</html>