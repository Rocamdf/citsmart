<%@ taglib uri="/tags/cit" prefix="cit"%> <%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.HistoricoItemConfiguracaoDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %> <%@page import="br.com.centralit.citcorpore.bean.InformacaoItemConfiguracaoDTO"%>
<html>
<head>
<%@include file="/include/security/security.jsp"%>
<title><i18n:message key="citcorpore.comum.title" /></title> 

<%@include file="/include/noCache/noCache.jsp" %> <%@include file="/include/header.jsp" %> <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		
<% 
	request.setCharacterEncoding("UTF-8");
	response.setHeader("Content-Language","lang");
	
	String iframe = "";
	iframe = request.getParameter("iframe"); %> <link rel="stylesheet" type="text/css" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/IC.css">

<script  charset="ISO-8859-1" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
<script type="text/javascript" src="../../cit/objects/CaracteristicaDTO.js"></script>
<script type="text/javascript" src="../../cit/objects/HistoricoItemConfiguracaoDTO.js"></script>

<style type="text/css">	
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
	  cursor: pointer;
	}
	
	.tableLess th {
	  border: 1px solid #BBB  !important;
	  padding: 6px  !important;
	}
	
	.tableLess td{
	  border: 1px solid #BBB  !important;
	  padding: 6px 10px  !important;
	}
	
	.sel {
		display: none;
		background: none  !important;;
		cursor:auto;
		padding: 0;
		margin: 0;
	}
	.sel td {
		padding: 0;
		margin: 0;
	}
	
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

	.sel-s {
	 	padding-left:20px !important;	 	
		border: 0px !important;
		width: 50% !important;
	}
	.form{
		width: 100%;
		float: right;
	}
	
	.formHead{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;
	}	
	
	.formBody{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;	
	}
	
	.formRelacionamentos div{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;
	}
	
	.formFooter{
		float: left; 
		width: 99%; 
		border: 1px solid #ccc; 
		padding: 5px;	
	}
	
	.divEsquerda{
		float: left; 
		width: 47%; 
		border: 1px solid #ccc; 
		padding: 5px;	
	}
	
	.divDireita{
		float: right;
		width: 47%; 
		border: 1px solid #ccc;
		padding: 5px;
	}
	
	.ui-tabs .ui-tabs-nav li a{
		background-color: #fff !important;
	}	
		
	.ui-state-active{
		background-color: #aaa ;
	}		
	
	#tabs div{
		background-color: #fff;
	}

	
	.ui-state-hover{
		background-color: #ccc !important;	
	}
	#contentBaseline {
		padding: 0 !important;
		margin: 0 !important;
		border: 0 !important;
	}
	.padd10 {
		padding: 0 10px;
	}
	.lFloat{
		float: left;
	}
	#divGrupoItemConfiguracao {
		display: none;
	}
	/* Desenvolvedor: Pedro Lino - Data: 25/10/2013 - Horário: 15:25 - ID Citsmart: 120948 - 
 	* Motivo/Comentário: Tamanho dos popups fora do frame/ Popups no tamanho correto do frame */
	.popup{
		height: 98%!important;
	}
</style>

</head>

	<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
		title=""
		style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
	</cit:janelaAguarde>

	<body id="bodyInf">	
	
		<div class="tabs">
			<ul class="tab_header clearfix">
				<li><a href="#tabs-1" onclick="showRelacionamentos()"><i18n:message	key="itemConfiguracao.cadastro"  /></a></li>
				<li><a href="#tabs-2" onclick="verificaImpactos()" class="round_top"><i18n:message key="itemConfiguracaoTree.impactos" /></a></li>
				<li><a href="#tabs-3" class="round_top"><i18n:message key="inventario.invetario" /></a></li>
				<li><a href="#tabs-4" onclick="verificaHistoricoAlteracao()" class="round_top" ><i18n:message key="citcorpore.comum.auditoria"/></a></li>
				
			</ul>
			<!-- <a href="#" class="toggle">&nbsp;</a> -->
			<div class="toggle_container">
				<div id="tabs-1" class="block">
					<div class="section">
		<form name='form'  id='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemConfiguracaoTree/itemConfiguracaoTree'>
			<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/> 
			<input type='hidden' id="idTipoItemConfiguracao" name='idTipoItemConfiguracao'/>			
			<input type='hidden' id="idItemConfiguracaoPai" name='idItemConfiguracaoPai'/>
			<input type='hidden' id="idGrupoItemConfiguracao" name='idGrupoItemConfiguracao'/>
			<input type='hidden' name='dataInicio'/>
			<input type='hidden' name='dataFim'/>
			<input type='hidden' id="caracteristicasSerializadas" name='caracteristicasSerializadas'/>
			<input type='hidden' id='idIncidente' name='idIncidente'/>
			<input type='hidden' id='idProblema' name='idProblema'/>
			<input type='hidden' id='idMudanca' name='idMudanca'/>
			<input type='hidden' id='idProprietario' name='idProprietario'/>
			<input type='hidden' id='idMidiaSoftware' name='idMidiaSoftware'/>		
			<input type='hidden' id='idLiberacao' name='idLiberacao'/>
			<input type='hidden' id='idResponsavel' name='idResponsavel'/>	
			<input type='hidden' id="dataInicioHistorico" name="dataInicioHistorico" />
			<input type='hidden' id="dataFimHistorico" name="dataFimHistorico" />		
										
			<div id="principalInf" style="display: none;"></div>
				<div id="itemConfiguracaoCorpo">
				 	<table>
						<tr>
							<td>
								<h3 id="titleITem"></h3>
							</td>
							<td>
								&nbsp;
							</td>
						</tr>
					</table> 
					<!-- 
					* Alterado por luiz.borges dia 27/11/2013 às 10:30 hrs
					* Motivo: Colocar o botão no layout padrão.
					 -->
					<div id="itemPai" style="line-height: 20px;display: none;">
						<button type='button' name='addItemConfiguracaoPai' id="addItemConfiguracaoPai" class="light"  style="margin: 10px !important; text-align: right;float: left;" >
						<span>
							<i18n:message key="itemConfiguracaoTree.mudarItemRelacionado"/>
						</span>
						</button>
						<div style="padding: 14px;">
							<span>
								<h2 class="padd10 lFloat" id="nomeItemConfiguracaoPai"></h2>
							</span>
						</div>
					</div>
					<!-- Fim da alteração luiz.borges -->
				</div>
				<div class="columns clearfix">
				
				    <div class="col_66">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="contrato.contrato" /></label>
								<div>
								   <select id="idContrato" name="idContrato" class="Valid[Required] Description[<i18n:message key="contrato.contrato"/>]"></select>
								</div>
						</fieldset>
					</div>
					
					<div class="col_40">				
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="citcorpore.comum.identificacao"/></label>
								<div>
								  	<input type='text' name="identificacao" class="Valid[Required] Description[<i18n:message key="citcorpore.comum.identificacao"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">				
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.familia"/></label>
								<div>
								  	<input type='text' name="familia" class="Valid[Required] Description[<i18n:message key="itemConfiguracaoTree.familia"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.classe"/></label>
								<div>
								  	<input type='text' name="classe" class="Valid[Required] Description[<i18n:message key="itemConfiguracaoTree.classe"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.localidade"/></label>
								<div>
								  	<input type='text' name="localidade" class="Valid[Required] Description[<i18n:message key="itemConfiguracaoTree.localidade"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					
					<div class="col_20">
						<fieldset>
							<label><i18n:message key="itemConfiguracaoTree.dataExpiracao"/></label>
								<div>
								  	<input type='text' name="dataExpiracao" class="Description[<i18n:message key="itemConfiguracaoTree.dataExpiracao"/>] Format[Date] Valid[Date] datepicker" maxlength="30" size="30"/>
								</div>
						</fieldset>
					</div>	
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;" class="campoObrigatorio"><i18n:message key="colaborador.colaborador"/></label>
							<div> 
								<input id="nomeUsuario" type='text' readonly="readonly" name="nomeUsuario" onfocus='abrePopupUsuario();' style="width: 70% !important;" maxlength="80" class="Valid[Required] Description[<i18n:message key="colaborador.colaborador"/>]" />
								<img onclick="abrePopupUsuario()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<!-- /**
								* Botão de limpar Lookup	
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparColaborador()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.versao"/></label>
								<div>	<input type='text' name="versao" class="Valid[Required] Description[<i18n:message key="itemConfiguracaoTree.versao"/>]" maxlength="30" size="30"/>	</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.nSerie"/></label>
								<div>	<input type='text' name="numeroSerie" class="Valid[Required] Description[<i18n:message key="itemConfiguracaoTree.nSerie"/>]" maxlength="30" size="30"/>	</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.criticidadeDoServico"/></label>
								<div>	<select name="criticidade" class="Valid[Required] Description[<i18n:message key="itemConfiguracaoTree.criticidadeDoServico"/>]"></select>	</div>
						</fieldset>
					</div>
				</div>
				<div class="columns clearfix">
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.urgencia"/></label>
							<div> 
								<select id="urgencia" name="urgencia" class="Description[<i18n:message key="itemConfiguracaoTree.urgencia"/>]">
								</select>					
							</div>
						</fieldset>										
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.impacto"/></label>
							<div> 
								<select id="impacto" name="impacto" class="Description[<i18n:message key="itemConfiguracaoTree.impacto"/>]">
								</select>					
							</div>
						</fieldset>										
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="itemConfiguracaoTree.status"/></label>
								<div>
								  	<select  name="status" class="Valid[Required] Description[<i18n:message key="itemConfiguracaoTree.status"/>]"></select>
								</div>
						</fieldset>
					</div>		
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio"><i18n:message key="tipoItemConfiguracao.tipoItemConfiguracao"/></label>
							<div>
							  	<input class="Valid[Required] Description[<i18n:message key="tipoItemConfiguracao.tipoItemConfiguracao"/>]" onclick="consultarTipoItemConfiguracao()" readonly="readonly" style="width: 70% !important;" type='text' name="nomeTipoItemConfiguracao" maxlength="70" size="70"  />
								<img onclick="consultarTipoItemConfiguracao()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<!-- /**
								* Botão de limpar Lookup	
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparTipoItemConfiguracao()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>						
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><i18n:message key="itemConfiguracaoTree.midia"/></label>
							<div> 
								<input id="nomeMidia" type='text' readonly="readonly" name="nomeMidia" onfocus='abrePopupMidia();' style="width: 70% !important;" maxlength="80" class="Description[<i18n:message key="itemConfiguracaoTree.midia"/>]" />
								<img onclick="abrePopupMidia()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparMidiaSoftware()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>						
				</div>
				<div class="columns clearfix">
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><i18n:message key="itemConfiguracaoTree.incidenteRequisicao"/></label>
							<div> 
								<input id="numeroIncidente" type='text' readonly="readonly" name="numeroIncidente" onfocus='abrePopupIncidente();' style="width: 70% !important;" maxlength="80" class="Description[<i18n:message key="itemConfiguracaoTree.incidenteRequisicao"/>]" />
								<img onclick="abrePopupIncidente()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparSolicitacaoServico()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">							
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label style="cursor: pointer;"><i18n:message key="itemConfiguracaoTree.problema"/></label>
							<div> 
								<input id="numeroProblema" type='text' readonly="readonly" name="numeroProblema" onfocus='abrePopupProblema();' style="width: 70% !important;" maxlength="80" class="Description[<i18n:message key="itemConfiguracaoTree.problema"/>]" />
								<img onclick="abrePopupProblema()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparProblema()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label id="Mudancarequired" style="cursor: pointer;" ><i18n:message key="itemConfiguracaoTree.mudanca"/></label>
							<div> 
								<input id="numeroMudanca" type='text' readonly="readonly" name="numeroMudanca" onfocus='abrePopupMudanca();' style="width: 70% !important;" maxlength="80" class="Description[<i18n:message key="itemConfiguracaoTree.mudanca"/>]" />
								<img onclick="abrePopupMudanca()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparRequisicaoMudanca()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label id="liberacao" style="cursor: pointer;" ><i18n:message key="liberacao"/></label>
							<div> 
								<input id="tituloLiberacao" type='text' readonly="readonly" name="tituloLiberacao" onfocus='abrePopupLiberacao();' style="width: 70% !important;" maxlength="80" class="Description[<i18n:message key="liberacao"/>]" />
								<img onclick="abrePopupLiberacao()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparRequisicaoLiberacao()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					<div class="col_20" id="divGrupoItemConfiguracao">
						<fieldset>
							<label><i18n:message key="itemConfiguracaoTree.grupo"/></label>
								<div>
								  	<input onclick="abrePopupGrupoItemConfiguracao()" readonly="readonly" style="width: 70% !important;" type='text' name="nomeGrupoItemConfiguracao" maxlength="70" size="70"  />
									<img onclick="abrePopupGrupoItemConfiguracao()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
									<!-- /**
								* Botão de limpar Lookup	
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
									<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparGrupoItemConfiguracao()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
								</div>
						</fieldset>
					</div>
					<div class="col_20">				
						<fieldset>
							<label><i18n:message key="itemConfiguracaoTree.ativofixo"/></label>
								<div>
								  	<input type='text' name="ativoFixo" class="Description[<i18n:message key="itemConfiguracaoTree.ativofixo"/>]" maxlength="70" size="40"/>
								</div>
						</fieldset>
					</div>
					<div class="col_20">
						<fieldset>
							<label class="campoObrigatorio" style="cursor: pointer;"><i18n:message key="citcorpore.comum.responsavel"/></label>
							<div> 
								<input id="nomeResponsavel" type='text' readonly="readonly" name="nomeResponsavel" onfocus='abrePopupResponsavel();' style="width: 70% !important;" maxlength="80" class="Valid[Required] Description[<i18n:message key="citcorpore.comum.responsavel"/>]" />
								<img onclick="abrePopupResponsavel()" style=" vertical-align: middle;" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
								<!-- /**
								* Botão de limpar Lookup	
								* @autor flavio.santana
								* 25/10/2013 10:50
								*/ -->
								<img border="0" title="<i18n:message key="botaoacaovisao.limpar_dados"/>" onclick="limparResponsavel()" style="cursor:pointer" src="/citsmart/imagens/borracha.png">
							</div>
						</fieldset>
					</div>
					
				</div>				
				<br>
				<div id="gridCaracteristica" class="columns clearfix" style="display: none;">
					<h2><i18n:message key="itemConfiguracaoTree.caracteristicas"/></h2>
					<table class="table table-bordered table-striped" id="tabelaCaracteristica" style="width: 100%">
						<tr>
							<th style="width: 20%;"><i18n:message key="citcorpore.comum.caracteristica"/></th>
							<th style="width: 30%;"><i18n:message key="citcorpore.comum.descricao"/></th>
							<th style="width: 20%;"><i18n:message key="citcorpore.comum.valor"/></th>								
						</tr>
					</table>												
				</div>					
				<button type='button' name='btnGravar' class="light"  onclick='gravar();'>
					<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
					<span><i18n:message key="citcorpore.comum.gravar"/></span>
				</button>
				<button type="button" name="btnLimpar" class="light img_icon has_text" onclick="limpar();" id="btnLimpar">
					<img src="/citsmart/template_new/images/icons/small/grey/clear.png">
					<span><i18n:message key="botaoacaovisao.limpar_dados"/></span>
				</button>
				
		</form>
				<div class="formRelacionamentos" id="relacionamentos">
			<div id="tabs">
				<ul>
					<li><a href="#relacionaIcs"><i18n:message key="itemConfiguracaoTree.itensConfiguracao"/></a></li>
					<li><a href="#relacionarIncidentes"><i18n:message key="itemConfiguracaoTree.incidentesRequisicoes"/></a></li>
					<li><a href="#relacionarProblemas"><i18n:message key="itemConfiguracaoTree.problemas"/></a></li>
					<li><a href="#relacionarMudancas"><i18n:message key="itemConfiguracaoTree.mudancas"/></a></li>	
					<li><a href="#relacionarLiberacoes"><i18n:message key="itemConfiguracaoTree.liberacoes"/></a></li>	
					<li><a href="#relacionarBC"><i18n:message key="itemConfiguracaoTree.baseConhecimento"/></a></li>					
				</ul>
				<div id="relacionaIcs">
					<form name='formBaseline' id='formBaseline' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/historicoItemConfiguracao/historicoItemConfiguracao'>
						<input type='hidden' id='idHistoricoIC' name='idHistoricoIC'/>
						<input type='hidden' id='idItemConfiguracao' name='idItemConfiguracao'/> 
						<input type='hidden' id="baselinesSerializadas" name='baselinesSerializadas'/>					
						<div id="contentBaseline">				
							
						</div>		
						<fieldset>		
							<button onclick="gravarBaseline();" class="light img_icon has_text" name="btnGravarBaseLine" type="button" id="btnGravarBaseLine">
								<img src="/citsmart/template_new/images/icons/small/grey/pencil.png">
								<span><i18n:message key="itemConfiguracaoTree.gravarBaselines"/></span>
							</button>
						</fieldset>
					</form>
				</div>				
				<div id="relacionarIncidentes">
					
				</div>
				<div id="relacionarProblemas">
				
				</div>
				<div id="relacionarMudancas">
				
				</div>
				<div id="relacionarLiberacoes">
				
				</div>
				<div id="relacionarBC">
				
				</div>				
			</div>
		</div>	
		</div>
		</div>
			<div id="tabs-2" class="block" >
				<div class="section">
					<form><div id='divImpactos'  style='width: 600px'></div></form>					
				</div>
			</div>
				
			<div id="tabs-3" class="block" >
				<div class="section" id="divInventario">
					<iframe  width="100%" height="90%" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id=<%=request.getParameter("idItemConfiguracao")%>&mostraItensVinculados=false"></iframe>					
				</div>
			</div>
			
			<div id="tabs-4" class="block" >
				<div class="section">
					<form id="formHistoricoAlteracao" name="formHistoricoAlteracao">
						<div id='divHistoricoAlteracao'  style='width: 1000px'>
							<h2><i18n:message key="itemConfiguracaoTree.auditoriaItemConfiguracao"/></h2>
							<div id="gridPesquisaHistorio" class="columns clearfix" style="display: block;">
									<div class="col_33">
										<fieldset>
											<label style="cursor: pointer;"><i18n:message key="citcorpore.comum.datainicio"/></label>
											<div> 									
												<input type='text' id="dataInicioH" name="dataInicioH" class="Description[<i18n:message key="citcorpore.comum.datainicio"/>] Format[Date] Valid[Date] datepicker" maxlength="30" size="30"/>
											</div>
										</fieldset>
									</div>
									<div class="col_33">
										<fieldset>
											<label style="cursor: pointer;"><i18n:message key="citcorpore.comum.datafim"/></label>
											<div> 									
												<input type='text' id="dataFimH" name="dataFimH" class="Description[<i18n:message key="citcorpore.comum.datainicio"/>] Format[Date] Valid[Date] datepicker" maxlength="30" size="30"/>
											</div>
										</fieldset>
									</div>
									<div class="col_100">
										<fieldset>
											<label style="cursor: pointer;"></label>
												<button type='button' name='btnHistoricoItemConfiguracao' class="light"  onclick='pesquisiarHistoricoItemConfiguracao();'>
														<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
														<span><i18n:message key="citcorpore.comum.pesquisar"/></span>
												</button>
												<button type="button" name="btnLimpar" class="light img_icon has_text" onclick="document.formHistoricoAlteracao.clear();" id="btnLimpar">
													<img src="/citsmart/template_new/images/icons/small/grey/clear.png">
													<span><i18n:message key="botaoacaovisao.limpar_dados"/></span>
												</button>
										</fieldset>
								</div>
							</div>
							<div id="historicoAlteracaoItemConfiguracao">
							
							</div>
							
						</div>
					</form>					
				</div>
			</div>
				
		</div>
	</div>
		
	<div id="popupCadastroRapido">
		<iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="99%"></iframe>
	</div>
		<div id="POPUP_GRUPOITEMCONFIGURACAO" class='popup' title="<i18n:message key="itemConfiguracaoTree.consultaGrupoItemConfiguracao"/>">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style="padding: 33px;">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<i18n:message key="itemConfiguracaoTree.grupoItemConfiguracao"/>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('grupoItemConfiguracao')">
								</label>
							</div>
							<form name='formPesquisaGrupoItemConfiguracao'>
								<cit:findField formName='formPesquisaGrupoItemConfiguracao' 
								lockupName='LOOKUP_GRUPOITEMCONFIGURACAO' 
								id='LOOKUP_GRUPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='400' 
								javascriptCode='true' 
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="POPUP_ITEMCONFIGPAI" class='popup' title="<i18n:message key="citcorpore.comum.identificacao" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisaItemConfiguracaoPai' style="width: 540px">
								<cit:findField formName='formPesquisaItemConfiguracaoPai'
		 							lockupName='LOOKUP_PESQUISAITEMCONFIGURACAO' id='LOOKUP_PESQUISAITEMCONFIGURACAO' top='0' 
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="POPUP_TIPOITEMCONFIGURACAO" class='popup' title="<i18n:message key="itemConfiguracaoTree.consultaTipoItemConfiguracao"/>" style="display: none;">
			<div class="box grid_16 tabs">
					<div class="toggle_container">
						<div id="tabs-2" class="block">
							<div class="section">
								<div  align="right">
									<label  style="cursor: pointer; ">
										<i18n:message key="itemConfiguracaoTree.tipoItemConfiguracao"/>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('tipoItemConfiguracao')">	
									</label>
								</div>
								<form name='formPesquisaTipoItemConfiguracao'>
									<cit:findField formName='formPesquisaTipoItemConfiguracao' 
									lockupName='LOOKUP_TIPOITEMCONFIGURACAO' 
									id='LOOKUP_TIPOITEMCONFIGURACAO' top='0' left='0' len='550' heigth='600' 
									javascriptCode='true' 
									htmlCode='true' />
								</form>
							</div>
						</div>
					</div>
				</div>
				
		</div>
		
		<div id="POPUP_MIDIASOFTWARE" class='popup' title='<i18n:message key="itemConfiguracaoTree.pesquisaMidia"/>' style="display: none;">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<i18n:message key="itemConfiguracaoTree.midia"/>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('midiaSoftware')">	
								</label>
							</div>
							<form name='formPesquisaMidiaSoftware'>
								<cit:findField formName='formPesquisaMidiaSoftware' 
								lockupName='LOOKUP_MIDIASOFTWARE' 
								id='LOOKUP_MIDIASOFTWARE' top='0' left='0' len='550' heigth='400' 
								javascriptCode='true' 
								htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>				
		</div>
		
		<div id="POPUP_EMPREGADO" class='popup' title='<i18n:message key="itemConfiguracaoTree.pesquisaEmpregado"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<i18n:message key="colaborador.colaborador"/>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('empregado')">
								</label>
							</div>
							<form name='formPesquisaEmp' style="width: 540px">
								<cit:findField formName='formPesquisaEmp' 
									lockupName='LOOKUP_EMPREGADO' id='LOOKUP_EMPREGADO' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_RESPONSAVEL" class='popup' title='<i18n:message key="citcorpore.comum.responsavel"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<i18n:message key="colaborador.colaborador"/>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="popup.abrePopup('empregado')">
								</label>
							</div>
							<form name='formPesquisaRes' style="width: 540px">
								<cit:findField formName='formPesquisaRes' 
									lockupName='LOOKUP_RESPONSAVEL_ITEM' id='LOOKUP_RESPONSAVEL' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_INCIDENTE" class='popup' title="<i18n:message key="solicitacaoServico.solicitacao" />">
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section" style='overflow: auto'>
							<div  align="right">
								<label  style="cursor: pointer; ">
										<i18n:message key="solicitacaoServico.solicitacao" />
										<!--
										* Adicionado titulo a popup	
										* @autor flavio.santana
										* 25/10/2013 10:50
										-->
										<img id='botaoSolicitante' src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" onclick="abrePopupSolicitacaoServico();">
								</label>
							</div>
							<form name='formPesquisaSolicitacaoServico' style="width: 540px">
								<cit:findField formName='formPesquisaSolicitacaoServico'
									lockupName='LOOKUP_SOLICITACAOSERVICO' id='LOOKUP_SOLICITACAOSERVICO' top='0'
									left='0' len='550' heigth='280' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="POPUP_PROBLEMA" class='popup' title='<i18n:message key="itemConfiguracaoTree.pesquisaProblema"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<!--
									* Adicionado titulo a popup	
									* @autor flavio.santana
									* 25/10/2013 10:50
									-->
									<i18n:message key="itemConfiguracaoTree.problemas"/>
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" 
									onclick="popup.titulo='<i18n:message key="problema.registro_problema" />';popup.abrePopup('problema');">	
								</label>
							</div>
							<form name='formPesquisaProblema' style="width: 540px">
								<cit:findField formName='formPesquisaProblema' 
									lockupName='LOOKUP_PROBLEMA' id='LOOKUP_PROBLEMA' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_MUDANCA" class='popup' title='<i18n:message key="itemConfiguracaoTree.pesquisaMudanca"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<i18n:message key="itemConfiguracaoTree.mudancas"/>
									<!--
									* Adicionado titulo a popup	
									* @autor flavio.santana
									* 25/10/2013 10:50
									-->
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" 
										onclick="popup.titulo='<i18n:message key="requisicaoMudanca.requisicaoMudanca" />';popup.abrePopup('requisicaoMudanca');">	
								</label>
							</div>
							<form name='formPesquisaMudanca' style="width: 540px">
								<cit:findField formName='formPesquisaMudanca' 
									lockupName='LOOKUP_MUDANCA' id='LOOKUP_MUDANCA' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		<div id="POPUP_LIBERACAO" class='popup' title='<i18n:message key="gerenciarequisicao.pesquisaliberacao"/>'>
			<div class="box grid_16 tabs">
				<div class="toggle_container">
					<div id="tabs-2" class="block">
						<div class="section">
							<div  align="right">
								<label  style="cursor: pointer; ">
									<i18n:message key="liberacao"/>
									<!--
									* Adicionado titulo a popup	
									* @autor flavio.santana
									* 25/10/2013 10:50
									-->
									<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png" 
										onclick="popup.titulo='<i18n:message key="requisicaoLiberacao.requisicaoLiberacao" />';popup.abrePopup('requisicaoLiberacao');">	
								</label>
							</div>
							<form name='formPesquisaLiberacao' style="width: 540px">
								<cit:findField formName='formPesquisaLiberacao' 
									lockupName='LOOKUP_LIBERACAO' id='LOOKUP_LIBERACAO' top='0'
									left='0' len='1050' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
		
		<div id="POPUP_IMPACTO" class='popup' title="<i18n:message key="itemConfiguracaoTree.impactos"/>">
			<div id='divImpactos'  style='width: 100%'>
			</div>
		</div>
		
		<div id="POPUP_EDITARPROBLEMA" class='popup' style="overflow: hidden;" title="<i18n:message key="problema.problema"/>">
		<iframe id='iframeEditarProblema' src='about:blank' width="100%" height="99%" style='width: 100%; height: 100%; border:none;'></iframe>		
	</div>
	<!-- 
		Adicionado o script para carregamento no final da página
		@autor flavio.santana
		28/10/2013	
	 -->
	<script type="text/javascript">
		var objTab = null;
		
		function update() {
			if (document.getElementById("idItemConfiguracao").value != "") {
				if (confirm(i18n_message("citcorpore.comum.deleta"))) {
					document.form.fireEvent("delete");
				}
			}
		}
		
		function LOOKUP_PESQUISAITEMCONFIGURACAOTODOS_select(id, desc) {
			document.form.restore( {
				idItemConfiguracao: id
			});
		}
		
		function LOOKUP_GRUPOITEMCONFIGURACAO_select(id,desc){
			document.form.idGrupoItemConfiguracao.value = id;
			document.form.nomeGrupoItemConfiguracao.value = desc;
			$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
			document.form.fireEvent("restoreGrupoItemConfiguracao");
		}
		
		function LOOKUP_PESQUISAITEMCONFIGURACAO_select(idItemConfiguracaoPai, desc) {
			
			if(confirm(i18n_message("itemConfiguracaoTree.ICrelacionado"))) {
				document.form.idItemConfiguracaoPai.value = idItemConfiguracaoPai;	
				$("#nomeItemConfiguracaoPai").text(desc);
					$("#POPUP_ITEMCONFIGPAI").dialog("close");		
				gravar();
			}
			
		}
	
		function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {
			document.form.idTipoItemConfiguracao.value =	 idTipo;
			document.form.fireEvent("restoreTipoItemConfiguracao");
		}
		
		var countCaracteristica = 0;
		function insereRow(id, desc) {
			var tabela = document.getElementById('tabelaCaracteristica');
			var lastRow = tabela.rows.length;

			var row = tabela.insertRow(lastRow);
			countCaracteristica++;

			var valor = desc.split(' - ');

			var coluna = row.insertCell(0);
			coluna.innerHTML = valor[0] + '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica" value="' + id + '" />';

			coluna = row.insertCell(1);
			coluna.innerHTML = valor[1];

			coluna = row.insertCell(2);
			coluna.innerHTML = valor[2]
		}

		function restoreRow() {
			var tabela = document.getElementById('tabelaCaracteristica');
			var lastRow = tabela.rows.length;

			var row = tabela.insertRow(lastRow);
			countCaracteristica++;
			var coluna = row.insertCell(0);
			coluna.innerHTML = '<input type="hidden" id="idCaracteristica' + countCaracteristica + '" name="idCaracteristica"/>' + 
								'<div id="caracteristica'+ countCaracteristica +'"></div>';	
			coluna = row.insertCell(1);
			coluna.innerHTML = '<div id="descricao'+ countCaracteristica +'"></div>';
			coluna = row.insertCell(2);
			coluna.innerHTML = '<input style="width: 100%; " type="text" id="valorString' + countCaracteristica + '" name="valorString"/>';	
			
		}

		var seqSelecionada = '';
		function setRestoreCaracteristica(idCaracteristica, caracteristica, tag, valorString, descricao, idEmpresa, dataInicio, dataFim) {
			if (seqSelecionada != '') {
				/*Motido: Retirado por efeitos negativos em relação a seu uso
				* Autor: flavio.santana
				* Data/Hora: 02/11/2013 13:26
				*/
				//eval('document.form.idCaracteristica' + seqSelecionada + '.value = "' + idCaracteristica + '"');
				$('#idCaracteristica' + seqSelecionada).val(idCaracteristica);
				$('#caracteristica' + seqSelecionada).text(ObjectUtils.decodificaEnter(caracteristica));
				$('#descricao' + seqSelecionada).text(ObjectUtils.decodificaEnter(descricao));
				$('#valorString' + seqSelecionada).val(valorString);
				
				/*Motido: Retirado por efeitos negativos em relação a seu uso
				* Autor: flavio.santana
				* Data/Hora: 02/11/2013 13:26
				*/
				//eval('document.form.valorString' + seqSelecionada + '.value = "' + valorString + '"');					
			}
		}

		function deleteAllRows() {
			var tabela = document.getElementById('tabelaCaracteristica');
			var count = tabela.rows.length;

			while (count > 1) {
				tabela.deleteRow(count - 1);
				count--;
			}
			ocultaGrid();
		}
		/*
		* Limpar todas as linhas das tabelas
		* @autor flavio.santana
		* 28/10/2013
		*/
		function limparTabelas(listTabela) {
			var tabela, count; 
			for(var i=0; i<listTabela.length;i++){
				tabela = document.getElementById(listTabela[i]);
				if(tabela != undefined) {
					count = tabela.rows.length;
	
					while (count > 1) {
						tabela.deleteRow(count - 1);
						count--;
					}	
				}
			}
		}

		function gravar() {
			var tabela = document.getElementById('tabelaCaracteristica');
			var count = tabela.rows.length;
			var contadorAux = 0;
			var caracteristicas = new Array();
			document.getElementById('divInventario').style.display = 'block';
			
			for ( var i = 1; i <= count; i++) {
				var trObj = document.getElementById('idCaracteristica' + i);

				if (!trObj) {
					continue;
				}	
				caracteristicas[contadorAux] = getCaracteristica(i);
				contadorAux = contadorAux + 1;
			}				
			serializa();
			document.form.save();
		}
			

	function reload(idItem) {
				<%
	    //se for chamado por iframe deixa apenas a parte de cadastro da página
	    if (iframe == null) {
		%>
			parent.reloadItem(idItem);
		<%
	    }
		%>
	}

	
	
	var seqSelecionada = '';
	var aux = '';
	serializa = function() {
		var tabela = document.getElementById('tabelaCaracteristica');
		var count = tabela.rows.length;
		var contadorAux = 0;
		var caracteristicas = new Array();
		for ( var i = 1; i <= count; i++) {
			var trObj = document.getElementById('idCaracteristica' + i);

			if (!trObj) {
				continue;
			}

			caracteristicas[contadorAux] = getCaracteristica(i);
			contadorAux = contadorAux + 1;
		}
		var caracteristicasSerializadas = ObjectUtils.serializeObjects(caracteristicas);
		document.form.caracteristicasSerializadas.value = caracteristicasSerializadas;
		return true;
	}

	getCaracteristica = function(seq) {
		var CaracteristicaDTO = new CIT_CaracteristicaDTO();
		CaracteristicaDTO.sequencia = seq;
		
		/*Motido: Retirado por efeitos negativos em relação a seu uso
		* Autor: flavio.santana
		* Data/Hora: 02/11/2013 13:26
		*/
		//CaracteristicaDTO.idCaracteristica = eval('document.form.idCaracteristica' + seq + '.value');
		//CaracteristicaDTO.valorString = eval('document.form.valorString' + seq + '.value');
		
		CaracteristicaDTO.idCaracteristica = $('#idCaracteristica' + seq).val();
		CaracteristicaDTO.valorString = $('#valorString' + seq).val();
		return CaracteristicaDTO;
	}
	/*Gravar baseline*/
	function gravarBaseline() {
		var tabela = document.getElementById('tblBaselines');
		var count = tabela.rows.length;
		var contadorAux = 0;
		var baselines = new Array();

		for ( var i = 1; i <= count; i++) {
			var trObj = document.getElementById('idHistoricoIC' + i);	
			if (!trObj) {
				continue;
			}	
			baselines[contadorAux] = getbaseline(i);
			contadorAux = contadorAux + 1;
		}
		serializaBaseline();
/**
 * Mostra o janela aguarde
 * @author flavio.santana
 * 25/10/2013
 */
		JANELA_AGUARDE_MENU.show();
		document.formBaseline.fireEvent("saveBaseline");
	}
	
	var seqBaseline = '';
	var aux = '';
	serializaBaseline = function() {
		var tabela = document.getElementById('tblBaselines');
		var count = tabela.rows.length;
		var contadorAux = 0;
		var baselines = new Array();
		for ( var i = 1; i <= count; i++) {
			var trObj = document.getElementById('idHistoricoIC' + i);
			if (!trObj) {
				continue;
			}else if(trObj.checked){
				baselines[contadorAux] = getbaseline(i);
				contadorAux = contadorAux + 1;
				continue;
			}	
			
		}
		var baselinesSerializadas = ObjectUtils.serializeObjects(baselines);
		document.formBaseline.baselinesSerializadas.value = baselinesSerializadas;
		return true;
	}

	getbaseline = function(seq) {
		var HistoricoItemConfiguracaoDTO = new CIT_HistoricoItemConfiguracaoDTO();
		HistoricoItemConfiguracaoDTO.sequencia = seq;
		/*Motido: Retirado por efeitos negativos em relação a seu uso
		* Autor: flavio.santana
		* Data/Hora: 02/11/2013 13:26
		*/
		//HistoricoItemConfiguracaoDTO.idHistoricoIC = eval('document.formBaseline.idHistoricoIC' + seq + '.value');
		HistoricoItemConfiguracaoDTO.idHistoricoIC = $('#idHistoricoIC' + seq).val();
		return HistoricoItemConfiguracaoDTO;
	}
	
	function restaurar(id){
		document.formBaseline.idHistoricoIC.value = id;
		if(confirm(i18n_message("itemConfiguracaoTree.restaurarVersao")))
			document.formBaseline.fireEvent("restaurarBaseline");
	}


	function fecharPopup(){
		$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
	}
	
	function fecharPopupGrupo(){
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("close");
	}
	
	function limpar() {
		deleteAllRows();
		var arr = ["tblLiberacoes", "tblMudancas", "tblIC", "tblProblemas", "tbIncidentes", "tblBaselines"];
		limparTabelas(arr);
		document.getElementById('gridCaracteristica').style.display = 'none';
		document.getElementById('divInventario').style.display = 'none';
		document.form.clear();
	}

	function exibeGrid() {
		document.getElementById('gridCaracteristica').style.display = 'block';
	}

	function ocultaGrid() {
		<%
    //se for chamado por iframe deixa apenas a parte de cadastro da página
    if (iframe == null) {
	%>
		document.getElementById('gridCaracteristica').style.display = 'none';
	<%
    }
	%>
}
		
$(function() {
	
	$("#POPUP_ITEMCONFIGPAI").dialog({
		autoOpen : false,
		width : 700,
		height : 500,
		modal : true
	});
	
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
	
	$("#POPUP_GRUPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
					
	$("#POPUP_ITEMCONFIGPAI").dialog({
		autoOpen : false,
		width : 700,
		height : 500,
		modal : true
	});

	$("#addItemConfiguracaoPai").click(function() {
		$("#POPUP_ITEMCONFIGPAI").dialog("open");
	});
	
	$("#POPUP_TIPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
	
	$(".dialog").dialog({
		autoOpen : false,
		modal : true
	});
	
	$("#POPUP_EMPREGADO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	/**
	* Aumentando o dialog de incicente	
	* @autor flavio.santana
	* 25/10/2013 10:50
	*/
	$("#POPUP_INCIDENTE").dialog({
		autoOpen : false,
		width : 800,
		height : 600,
		modal : true
	});
	$("#POPUP_PROBLEMA").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_MUDANCA").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_LIBERACAO").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_MIDIASOFTWARE").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	$("#POPUP_GRUPOITEMCONFIGURACAO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
	
	$("#POPUP_IMPACTO").dialog( {
		autoOpen : false,
		width : 705,
		height : 500,
		modal : true
	});
	$("#POPUP_RESPONSAVEL").dialog({
		autoOpen : false,
		width : 600,
		height : 400,
		modal : true
	});
	
	
	$("#divAlterarSenha").hide();
					
	initPopups();
	
  		//para visualização rápida do mapaDesenhoServico
  		popupManager = new PopupManager(0 , 0, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
  		//solicitcaoservico
  		popupManagerSolicitacaoServico = new PopupManager(0 , 0, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");

	$( "#tabs" ).tabs();
	

});

/**
* Motivo: Conformar a Popup com o tamanho da tela
* Autor: luiz.borges
* Data/Hora: 22/11/2013 11:32
*/
function abrePopupSolicitacaoServico(){
	
	popupManagerSolicitacaoServico.titulo='<i18n:message key="solicitacaoServico.solicitacao" />';
	redimensionarTamanho("#popupCadastroRapido", "XG");
	popupManagerSolicitacaoServico.abrePopupParms('solicitacaoServicoMultiContratos', '', '&selecaoIc=true');
	
}


function redimensionarTamanho(identificador, tipo_variacao){
	var h;
	var w;
	switch(tipo_variacao)
	{
	case "PEQUENO":
		w = parseInt($(window).width() * 0.25);
		h = parseInt($(window).height() * 0.35);
	  break;
	case "MEDIO":
		w = parseInt($(window).width() * 0.5);
		h = parseInt($(window).height() * 0.6);
	  break;
	case "GRANDE":
		w = parseInt($(window).width() * 0.75);
		h = parseInt($(window).height() * 0.85);
	  break;
	case "XG":
		w = parseInt($(window).width() * 0.95);
		h = parseInt($(window).height() * 0.95);
	  break;
	default:
		w = parseInt($(window).width() * 0.5);
		h = parseInt($(window).height() * 0.6);
	}
	
	$(identificador).dialog("option","width", w)
	$(identificador).dialog("option","height", h)
}

function event() {
	$( ".even" ).click(function() {
		var s = $(this).attr('id').split('-');
		if(s[0]=="even")
			$( "#sel-" + s[1] ).toggle();
		else if(s[0]=="evenM")
			$( "#selM-" + s[1] ).toggle();
		else if(s[0]=="evenP")
			$( "#selP-" + s[1] ).toggle();
	});
}


	var popup;
    function load(){
    	/**
		* Aumentando o dialog geral de varios frames
		* @autor flavio.santana
		* 25/10/2013 10:50
		*/
		popup = new PopupManager(880, 550, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		document.form.afterRestore = function () {
			$('.tabs').tabs('select', 0);
		}
    }
    addEvent(window, "load", load, false);

/* 	function consultarTipoItemConfiguracao(){
		$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
	} */
	
	function consultarTipoItemConfiguracao(){
		$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
	}
	
	/*function consultarTipoItemConfiguracao(){
		$("#POPUP_TIPOITEMCONFIGURACAO").dialog("open");
	}*/
	
	function LOOKUP_TIPOITEMCONFIGURACAO_select(idTipo, desc) {
		var valor = desc.split('-');
		
		document.form.nomeTipoItemConfiguracao.value = valor[0];
		
		document.getElementById('idTipoItemConfiguracao').value = valor[2].replace(/\D/g, "");
		
		document.form.fireEvent("restoreTipoItemConfiguracao");
		
		$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");	
	}
	
	
	function fecharPopup(){
		$("#POPUP_TIPOITEMCONFIGURACAO").dialog("close");
	}
	
	function novoTipo(){
		var idItem = id.replace(/\D/g, "");
		
		document.getElementById('iframeOpcoes').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/tipoItemConfiguracaoTree/tipoItemConfiguracaoTree.load?idItemConfiguracao=';
		$("#FRAME_OPCOES").dialog("open");
	}
	
	function alterarSenha(){
		$("#divSenha").show();
	}
	
	abrePopupUsuario = function(){
		$("#POPUP_EMPREGADO").dialog("open");
	}
	
	abrePopupIncidente = function(){
		$("#POPUP_INCIDENTE").dialog("open");
	}
	
	abrePopupProblema = function(){
		$("#POPUP_PROBLEMA").dialog("open");
	}
	
	abrePopupMudanca = function(){
		$("#POPUP_MUDANCA").dialog("open");
	}
	abrePopupLiberacao = function(){
		$("#POPUP_LIBERACAO").dialog("open");
	}
	
	abrePopupMidia= function(){
		$("#POPUP_MIDIASOFTWARE").dialog("open");
	}
	abrePopupGrupoItemConfiguracao = function(){
		$("#POPUP_GRUPOITEMCONFIGURACAO").dialog("open");
	}
	abrePopupResponsavel = function(){
		$("#POPUP_RESPONSAVEL").dialog("open");
	}
	
	function LOOKUP_MIDIASOFTWARE_select(id, desc) {
		document.form.idMidiaSoftware.value = id;
		document.getElementById("nomeMidia").value = desc;

		$("#POPUP_MIDIASOFTWARE").dialog("close");
	}
		
	function LOOKUP_EMPREGADO_select(id, desc){
		document.form.idProprietario.value = id;
		document.getElementById("nomeUsuario").value = desc;

		$("#POPUP_EMPREGADO").dialog("close");
	}
	function LOOKUP_SOLICITACAOSERVICO_select(id, desc){
		document.form.idIncidente.value = id;
		document.getElementById("numeroIncidente").value = desc;
		$("#POPUP_INCIDENTE").dialog("close");
	}
	function LOOKUP_PROBLEMA_select(id, desc){
		document.form.idProblema.value = id;
		document.getElementById("numeroProblema").value = desc;
		$("#POPUP_PROBLEMA").dialog("close");
	}
	function LOOKUP_MUDANCA_select(id, desc){
		document.form.idMudanca.value = id;
		document.getElementById("numeroMudanca").value = desc;
		$("#POPUP_MUDANCA").dialog("close");
	}
	function LOOKUP_LIBERACAO_select(id, desc){
		document.form.idLiberacao.value = id;
		document.getElementById("tituloLiberacao").value = desc;
		$("#POPUP_LIBERACAO").dialog("close");
	}
	function LOOKUP_RESPONSAVEL_select(id, desc){
		document.form.idResponsavel.value = id;
		document.getElementById("nomeResponsavel").value = desc;

		$("#POPUP_RESPONSAVEL").dialog("close");
	}
	
	function abrePopupServicos(){
		$("#POPUP_SERVICO").dialog("open");
	}
	
	function adicionarServico(){
		abrePopupServicos();	
	}
	
	function abrePopupIcs(){
		$("#POPUPITEMCONFIGURACAO").dialog("open");
	}
	
	function adicionarIC(){			
		abrePopupIcs();
	}
	
	function initPopups(){
		$(".POPUP_LOOKUP").dialog({
			autoOpen : false,
			width : 600,
			height : 400,
			modal : true
		});			
	}
	
	function fecharAddSolicitante() {
		$("#popupCadastroRapido").dialog("close");
	}
	
	function limparMidiaSoftware(){
		document.getElementById("idMidiaSoftware").value = '';
		document.getElementById("nomeMidia").value = '';
	}
	function limparSolicitacaoServico(){
		document.getElementById("idIncidente").value = '';
		document.getElementById("numeroIncidente").value = '';
	}
	function limparProblema(){
		document.getElementById("idProblema").value = '';
		document.getElementById("numeroProblema").value = '';
	}
	function limparRequisicaoMudanca(){
		document.getElementById("idMudanca").value = '';
		document.getElementById("numeroMudanca").value = '';
	}
	function limparRequisicaoLiberacao(){
		document.getElementById("idLiberacao").value = '';
		document.getElementById("tituloLiberacao").value = '';
	}
	/**
	* Adicionando o item de limpar lookup
	* @autor flavio.santana
	* 25/10/2013 10:50
	*/
	function limparColaborador() {
		document.getElementById("idProprietario").value = '';
		document.getElementById("nomeUsuario").value = '';
	}
	function limparTipoItemConfiguracao() {
		document.getElementById("idTipoItemConfiguracao").value = '';
		document.getElementById("nomeTipoItemConfiguracao").value = '';
	}
	function limparGrupoItemConfiguracao(){
		document.getElementById("idGrupoItemConfiguracao").value = '';
		document.getElementById("nomeGrupoItemConfiguracao").value = '';
	}
	function limparResponsavel() {
		document.getElementById("idResponsavel").value = '';
		document.getElementById("nomeResponsavel").value = '';
	}
	function verificaImpactos(){
		JANELA_AGUARDE_MENU.show();
		$("#relacionamentos").hide();
		document.form.fireEvent('verificaImpactos');
	}
	
	function verificaHistoricoAlteracao(){
		JANELA_AGUARDE_MENU.show();
		$("#relacionamentos").hide();
		$("#divHistoricoAlteacao").show();
		JANELA_AGUARDE_MENU.hide()
	}
	
	function pesquisiarHistoricoItemConfiguracao(){
		
		document.form.dataInicioHistorico.value = document.getElementById("dataInicioH").value;
		document.form.dataFimHistorico.value = document.getElementById("dataFimH").value;
		
		var dataIncio = document.form.dataInicioHistorico.value;
		var dataFim = document.form.dataFimHistorico.value;
		
		if(DateTimeUtil.isValidDate(dataIncio) == false){
			alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		 	document.getElementById("dataInicioH").value = '';
		 	return false;
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			 alert(i18n_message("citcorpore.comum.validacao.datafim"));
			 document.getElementById("dataFimH").value = '';
			return false;					
		}
		
		if( !verificaData(dataIncio ,dataFim ))
			return false;
		
		JANELA_AGUARDE_MENU.show();
		
		document.form.fireEvent('verificaHistoricoAlteracao'); 
	}
	
	function verificaData(dataInicio, dataFim) {
		
		var dtInicio = new Date();
		var dtFim = new Date();
		
		dtInicio.setTime(Date.parse(dataInicio.split("/").reverse().join("/"))).setFullYear;
		dtFim.setTime(Date.parse(dataFim.split("/").reverse().join("/"))).setFullYear;
		
		if (dtInicio > dtFim){
			alert(i18n_message("citcorpore.comum.dataInicioMenorFinal"));
			return false;
		}else
			return true;
	}
	
	function showRelacionamentos(){
		$("#relacionamentos").show();
	}
	/**
	* Função para fechar modal de nova solicitação
	* @autor flavio.santana
	* 25/10/2013 10:50
	*/
	function fecharModalNovaSolicitacao() {
		$("#popupCadastroRapido").dialog('close');
	}
	
	function fecharModalFilha() {
		$("#popupCadastroRapido").dialog('close');
	}
	
	function fecharProblema() {
		$("#popupCadastroRapido").dialog('close');
	}
	
	function fecharMudanca() {
		$("#popupCadastroRapido").dialog('close');
	}
	
	function fecharVisao() {
		$("#popupCadastroRapido").dialog('close');
	}
	
	function getBotaoEditarProblema(id){
		var botaoVisualizarErrosConhecidos = new Image();

		botaoVisualizarErrosConhecidos.src = '<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png';
		botaoVisualizarErrosConhecidos.setAttribute("style", "cursor: pointer;");
		botaoVisualizarErrosConhecidos.id = id;
		botaoVisualizarErrosConhecidos.addEventListener("click", function(evt){CarregarProblema(id)}, true);

		return botaoVisualizarErrosConhecidos;
	}
	
	function CarregarProblema(idProblema){
		document.getElementById('iframeEditarProblema').src = "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/problema/problema.load?iframe=true&chamarTelaProblema=S&acaoFluxo=E&idProblema="+idProblema;
		$("#POPUP_EDITARPROBLEMA").dialog("open");
	}
	$(function() {
		$("#POPUP_EDITARPROBLEMA").dialog({
			autoOpen : false,
			width : "98%",
			height : 1000,
			modal : true
		});
	});
	
	function fecharFrameProblema(){
		$("#POPUP_EDITARPROBLEMA").dialog("close");
		//document.form.fireEvent("atualizaGridProblema");
	}
	/**
	* Funções essenciais para funcionamento do cadastro de nova solicitação	
	* @autor flavio.santana
	* 25/10/2013 10:50
	*/
	function fecharJanelaAguarde() {
		JANELA_AGUARDE_MENU.hide();
	}	
	function atualizarLista(){
		
	}
	
</script>
	
	</body>
</html>
