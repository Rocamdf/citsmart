<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.citframework.dto.Usuario"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%@page import="java.util.Collection"%>
<!doctype html public "âœ°">
<html>
<head>
<%
    response.setHeader("Cache-Control", "no-cache");
			response.setHeader("Pragma", "no-cache");
			response.setDateHeader("Expires", -1);

			//identifica se a página foi aberta a partir de um iframe (popup de cadastro rápido)
			String iframe = "";
			iframe = request.getParameter("iframe");			
%>
<%@include file="/include/security/security.jsp"%>
<!--<![endif]-->
<title><i18n:message key="citcorpore.comum.title" /></title>
<%@include file="/include/noCache/noCache.jsp"%>
<%@include file="/include/header.jsp"%>
<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/boxover.js"></script>

<script>
	var objTab = null;

	addEvent(window, "load", load, false);
	function load() {
		document.form.afterRestore = function() {
			$('.tabs').tabs('select', 0);
	        document.getElementById('divEncerramento').style.display = 'block';
			carregarFrames();   
		}
	}

	function selecionarAbaColetaPrecos() {
        JANELA_AGUARDE_MENU.hide();
		carregarFrames();  
        //$('tabsFrame').tabs('select', 3);
	}
	
	var contFrame = 0;
	function carregarFrames() {
		contFrame = 3;
        exibeItensCotacao();
        exibeFornecedoresCotacao();
        exibeColetasPrecos();
    }
    
	function LOOKUP_COTACAO_select(id, desc) {
		document.form.restore({
			idCotacao :id});
	}
	
	function LOOKUP_COTACAO_ENCERRADAS_select(id, desc) {
		document.form.restore({
			idCotacao :id});
	}
    
    function exibeColetasPrecos() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraColetaPreco').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/coletaPreco/coletaPreco.load?'+
            'idCotacao='+idCotacao;
        //window.frames["fraColetaPreco"].atualiza();
    }
    
    function atualizaColetaPrecos() {
    	JANELA_AGUARDE_MENU.show();
    	window.frames["fraColetaPreco"].atualiza();
    }

    function exibeItensCotacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraItemCotacao').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemCotacao/itemCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeFornecedoresCotacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraFornecedorCotacao').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/fornecedorCotacao/fornecedorCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeResultadoCotacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraResultadoCotacao').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/resultadoCotacao/resultadoCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeAprovacao() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraAprovacao').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/consultaAprovacaoCotacao/consultaAprovacaoCotacao.load?'+
            'idCotacao='+idCotacao;
    }

    function exibePedidos() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraPedido').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/pedidoCompra/pedidoCompra.load?'+
            'idCotacao='+idCotacao;
    }

    function exibeEntregas() {
        var idCotacao = document.form.idCotacao.value;
        JANELA_AGUARDE_MENU.show();
        document.getElementById('fraEntrega').src = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/entregaPedido/entregaPedido.load?'+
            'idCotacao='+idCotacao;
    }
    
    function escondeJanelaAguarde() {
        contFrame = contFrame - 1;
        if (contFrame <= 0)
            JANELA_AGUARDE_MENU.hide();
    }

    function limpar() {
        JANELA_AGUARDE_MENU.show();
    	document.form.clear();
    	document.getElementById('fraColetaPreco').src = 'about:blank';
    	document.getElementById('fraItemCotacao').src = 'about:blank';
    	document.getElementById('fraFornecedorCotacao').src = 'about:blank';
    	document.getElementById('fraResultadoCotacao').src = 'about:blank'; 
        document.getElementById('fraPedido').src = 'about:blank'; 
        document.getElementById('fraEntrega').src = 'about:blank'; 
        $('.tabs').tabs('select', 0);
        document.getElementById('divEncerramento').style.display = 'none';
        document.getElementById('retPesqLOOKUP_COTACAO').innerHTML = '';
        document.getElementById('retPesqLOOKUP_COTACAO_ENCERRADAS').innerHTML = '';
        JANELA_AGUARDE_MENU.hide();
    }

    function atualiza() {
    	JANELA_AGUARDE_MENU.hide();
        document.form.fireEvent('restore');
        carregarFrames(); 
    }

    function pesquisarRequisicoes() {
		var dataInicio = document.getElementById("dataInicio").value;
		var dataFim = document.getElementById("dataFim").value;
		if(DateTimeUtil.isValidDate(dataInicio) == false){
			alert(i18n_message("citcorpore.comum.validacao.datainicio"));
		 	document.getElementById("dataInicio").value = '';
			return false;	
		}
		if(DateTimeUtil.isValidDate(dataFim) == false){
			alert(i18n_message("citcorpore.comum.validacao.datafim"));
			document.getElementById("dataFim").value = '';
			return false;					
		}

		if(validaData(dataInicio,dataFim)){
        	document.formItensRequisicao.fireEvent('pesquisaItensParaCotacao');
		}
    }
    
	function validaData(dataInicio, dataFim) {
		
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

    function gerarSelecaoItemRequisicao(row, obj){
        obj.selecionado = 'N';
        var detalhes = "<table>";
        detalhes += "<tr><td style='padding:4px 4px;'><b><i18n:message key='calendario.descricao'/>:<b></td><td>"+obj.descricaoItem+"</td></tr>"; 
        if (obj.idProduto != '' && obj.codigoProduto != '')                                                                  
            detalhes += "<tr><td style='padding:4px 4px;'><b><i18n:message key='centroResultado.codigo'/>:<b></td><td>"+obj.codigoProduto+"</td></tr>";         
        if (obj.idProduto == '' && obj.marcaPreferencial != '')                                                                  
           detalhes += "<tr><td style='padding:4px 4px;'><b><i18n:message key='itemRequisicaoProduto.marcaPreferencial'/>:<b></td><td>"+obj.marcaPreferencial+"</td></tr>"; 
        if (obj.especificacoes != '')                                                                  
               detalhes += "<tr><td style='padding:4px 4px;'><b><i18n:message key='itemRequisicaoProduto.especificacoes'/>:<b></td><td>"+obj.especificacoes+"</td></tr>";            
        detalhes += "</table>";       
        row.cells[0].innerHTML = "<img style='cursor: pointer;' src='<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/viewCadastro.png' title=\"header=[<i18n:message key='cotacao.detalhesDoItem'/>] body=[" + detalhes + "]\" />";        
    }

    function encerrarCotacao() {
        if (confirm(i18n_message("cotacao.mensagemEncerramento"))) 
            document.form.fireEvent('encerra');
    }
</script>
<style>
    
    .linhaSubtituloGrid
    {
        color			:#000000;
	    background-color: #d3d3d3;
	    BORDER-RIGHT: thin outset;
	    BORDER-TOP: thin outset;
	    BORDER-LEFT: thin outset;
	    BORDER-BOTTOM: thin outset;	 
		FONT-WEIGHT: bold;
		padding: 5px 0px 5px 5px;
        
    }
    .linhaGrid{
        border: 1px solid black;
        background-color:  #F2F2F2;
        vertical-align: middle;
    }   
        .table {
            border-left:1px solid #ddd;
            width: 100%;
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
        
</style>
<%//se for chamado por iframe deixa apenas a parte de cadastro da página
			if (iframe != null) {%>
<style>
    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style>    
<%}%>
</head>
<cit:janelaAguarde id="JANELA_AGUARDE_MENU"  title="" style="display:none;top:325px;width:300px;left:500px;height:50px;position:absolute;">
</cit:janelaAguarde>
<body>
	<div id="wrapper">
		<%if (iframe == null) {%>
		<%@include file="/include/menu_vertical.jsp"%>
		<%}%>
		<!-- Conteudo -->
		<div id="main_container" class="main_container container_16 clearfix" style="height: auto !important;">
			<%if (iframe == null) {%>
			<%@include file="/include/menu_horizontal.jsp"%>
			<%}%>

			<div class="flat_area grid_16">
				<h2>
					<i18n:message key="cotacao" />
				</h2>
			</div>
			<div class="box grid_16 tabs">
				<ul class="tab_header clearfix">
					<li><a href="#tabs-1"><i18n:message key="cotacao.gerenciamento" />
					</a>
					</li>
					<li><a href="#tabs-2" class="round_top"><i18n:message key="cotacao.pesquisaAbertas" />
					</a>
					</li>
					<li><a href="#tabs-11" class="round_top"><i18n:message key="cotacao.pesquisaEncerradas" />
					</a>
					</li>					
                    <li><a href="#tabs-3" class="round_top"><i18n:message key="cotacao.pesquisaRequisicoes" />
                    </a>
                    </li>
				</ul>
				<a href="#" class="toggle">&nbsp;</a>
				<div class="toggle_container">
					<div id="tabs-1" class="block">
						<div class="section">
							<form name='form'
								action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/cotacao/cotacao'>
								<div class="columns clearfix">
                                    <input type='hidden' name='idItemCotacao' id='idItemCotacao'/>
                                    <input type='hidden' name='idEmpresa' id='idEmpresa'/>
                                    <input type='hidden' name='idResponsavel' id='idResponsavel'/>
                                    <input type='hidden' name='situacao' id='situacao'/>
                                    <input type='hidden' name='dataHoraCadastro' id='dataHoraCadastro'/>

                                    <div class="col_100">
                                         <div class="col_15">
                                            <fieldset>
                                                <label ><i18n:message key="cotacao.numero" />
                                                </label>
                                                   <div>
                                                       <input id="idCotacao" type='text' name="idCotacao" readonly="readonly" />     
                                                   </div>
                                            </fieldset>
                                         </div>
                                         <div class="col_60">
											<fieldset>
												<label class="campoObrigatorio"><i18n:message key="cotacao.identificacao" />
												</label>
		                                           <div>
		                                               <input id="identificacao" type='text' name="identificacao" maxlength="100" class="Valid[Required] Description[cotacao.identificacao]" />     
		                                           </div>
											</fieldset>
								         </div>
                                         <div class="col_25">
                                              <fieldset>
                                                  <label class="campoObrigatorio"><i18n:message key="cotacao.dataFinal" /></label>
                                                  <div>
                                                     <input type='text' name="dataFinalPrevista" id="dataFinalPrevista" maxlength="10" size="10" 
                                                            class="Valid[Required] Description[cotacao.dataFinal] Format[Data] datepicker" />
                                                  </div>
                                              </fieldset>
                                         </div>
                                    </div>
                                    <div class="col_50">
                                        <fieldset>
                                            <label><i18n:message key="cotacao.observacoes" />
                                            </label>
                                            <div>
                                                <textarea name="observacoes" id="observacoes" cols='200' rows='3' ></textarea>
                                            </div>
                                        </fieldset>
	                                </div>
                                    <div class="col_20">
                                       <fieldset>
                                           <label><i18n:message key="citcorpore.comum.situacao" />
                                           </label>
                                              <div>
                                                  <input id="situacaoStr" type='text' name="situacaoStr" readonly="readonly" />     
                                              </div>
                                       </fieldset>
                                    </div>
                                    <div class="col_20">
                                        <fieldset>
	                                        <div style="padding:20px !important;margin-top: 15px !important">
				                                <button type='button' name='btnGravar' class="light"
				                                    onclick='document.form.save();'>
				                                    <img
				                                        src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
				                                    <span><i18n:message key="citcorpore.comum.gravar" />
				                                    </span>
				                                </button>
				                                <button type='button' name='btnLimpar' class="light"
				                                    onclick='limpar();'>
				                                    <img
				                                        src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
				                                    <span><i18n:message key="citcorpore.comum.limpar" />
				                                    </span>
				                                </button>
	                                        </div>
                                        </fieldset>
                                    </div>
                                    <div id="divEncerramento" class="col_10" style='display:none'>
                                        <fieldset>
	                                        <div style="padding:20px !important;margin-top: 15px !important">
	                                            <button type='button' name='btnEncerrar' class="light"
	                                                onclick='encerrarCotacao();'>
	                                                <img
	                                                    src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/locked_2.png">
	                                                <span><i18n:message key="cotacao.encerrarCotacao" />
	                                                </span>
	                                            </button>
	                                        </div>
                                        </fieldset>
                                    </div>
                                    <div class="col_100">
							            <div class="tabs" style="width: 100%;">
							                <ul class="tab_header clearfix">
                                                <li><a href="#tabs-4" class="round_top" onclick=''><i18n:message key="cotacao.itens" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-5" class="round_top" onclick=''><i18n:message key="cotacao.fornecedores" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-6" class="round_top" onclick='exibeColetasPrecos(); '><i18n:message key="cotacao.coletasPrecos" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-7" class="round_top" onclick='exibeResultadoCotacao();'><i18n:message key="cotacao.resultados" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-8" class="round_top" onclick='exibeAprovacao();'><i18n:message key="cotacao.aprovacoes" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-9" class="round_top" onclick='exibePedidos();'><i18n:message key="cotacao.pedidos" />
                                                </a>
                                                </li>
                                                <li><a href="#tabs-10" class="round_top" onclick='exibeEntregas();'><i18n:message key="cotacao.entregas" />
                                                </a>
                                                </li>
							                </ul>						                
                                            <a href="#" class="toggle">&nbsp;</a>
							                <div class="toggle_container">
                                                <div id="tabs-4" class="block">
                                                    <div class="columns clearfix">
                                                        <div class="col_100" style='height:640px;'>
                                                            <iframe id='fraItemCotacao' name='fraItemCotacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                        </div>
                                                    </div> 
                                                </div> 
                                                <div id="tabs-5" class="block">
                                                    <div class="columns clearfix">
                                                        <div class="col_100" style='height:700px;'>
                                                            <iframe id='fraFornecedorCotacao' name='fraFornecedorCotacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                        </div>
                                                    </div> 
                                                </div> 

                                               <div id="tabs-6" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:1000px;'>
                                                           <iframe id='fraColetaPreco' name='fraColetaPreco' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div> 
                                               </div> 
                                               <div id="tabs-7" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:500px;'>
                                                           <iframe id='fraResultadoCotacao' name='fraResultadoCotacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div> 
                                                </div>
                                               <div id="tabs-8" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:500px;'>
                                                           <iframe id='fraAprovacao' name='fraAprovacao' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div> 
                                                </div>
                                                <div id="tabs-9" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:1000px;'>
                                                           <iframe id='fraPedido' name='fraPedido' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div> 
                                                </div> 
                                                <div id="tabs-10" class="block">
                                                   <div class="columns clearfix">
                                                       <div class="col_100" style='height:800px;'>
                                                           <iframe id='fraEntrega' name='fraEntrega' src='about:blank' style='width: 100%; height: 100%; border:none;'></iframe>
                                                       </div>
                                                   </div> 
                                                </div> 	                                        
                                            </div> 
                                        </div>                                  
                                    </div>
                                    <div style="clear: both"></div>
    							</div>
							</form>
						</div>
					</div>
					<div id="tabs-2" class="block">
						<div class="section">
							<form name='formPesquisa'>
								<cit:findField formName='formPesquisa'
									lockupName='LOOKUP_COTACAO_ABERTAS' id='LOOKUP_COTACAO' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
					<div id="tabs-11" class="block">
						<div class="section">
							<form name='formPesquisaEncerradas'>
								<cit:findField formName='formPesquisaEncerradas'
									lockupName='LOOKUP_COTACAO_ENCERRADAS' id='LOOKUP_COTACAO_ENCERRADAS' top='0'
									left='0' len='550' heigth='400' javascriptCode='true'
									htmlCode='true' />
							</form>
						</div>
					</div>
					<div id="tabs-3" class="block">
                        <div class="section">
						    <form name='formItensRequisicao'
						        action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemCotacao/itemCotacao'>
						
						          <div class="columns clearfix">
						              <h2 class="section">
						                  <i18n:message key="cotacao.itensRequisicao" />
						              </h2>
						              <div class="col_100">
						                  <div class="col_20">
						                       <fieldset >
						                           <label class="campoObrigatorio"><i18n:message key="cotacao.periodoRequisicao" /></label>
						                           <div class="col_100">
						                              <div class="col_33" style="margin-top:0px !important; text-align: center !important;vertical-align:middle !important">
						                                      <input type='text' name="dataInicio" id="dataInicio" maxlength="10" size="15" 
						                                             class="Format[Data] datepicker" />
						                                             </div>
						                                    <div class="col_15" style="margin-top:11px; text-align: center !important;vertical-align:middle !important"> 
						                                      &nbsp;<i18n:message key="citcorpore.comum.a" />&nbsp;
						                                </div>   
						                                <div class="col_33" style="text-align: center !important;vertical-align:middle !important">  
						                                      <input type='text' name="dataFim" id="dataFim" maxlength="10" size="15" 
						                                             class="Format[Data] datepicker" />
						                                    </div>
						                                    </div>
						                       </fieldset>
						                  </div>
						                  <div class="col_40">
						                       <fieldset>
						                           <label style="cursor: pointer;"><i18n:message key="centroResultado.custo" /></label>
						                           <div>
						                               <select name='idCentroCusto' class="Valid[Required] Description[centroResultado.custo]"></select>
						                           </div>
						                       </fieldset>
						                  </div>    
                                          <div class="col_40">
                                               <fieldset>
                                                   <label style="cursor: pointer;"><i18n:message key="requisicaoProduto.projeto" /></label>
                                                   <div>
                                                       <select name='idProjeto' class="Description[requisicaoProduto.projeto]"></select>
                                                   </div>
                                               </fieldset>
                                          </div>    
						              </div>                          
						              <div class="col_100">
		                                     <div class="col_50">
		                                         <fieldset >
		                                             <label style="cursor: pointer;"><i18n:message key="requisicaoProduto.enderecoEntrega" /></label>
		                                             <div>
		                                                 <select name='idEnderecoEntrega' class="Description[requisicaoProduto.enderecoEntrega]"></select>
		                                             </div>
		                                         </fieldset>
		                                     </div>
						                     <div class="col_25">
						                          <fieldset>
						                              <label ><i18n:message key="requisicaoProduto.numero" /></label>
						                                 <div>
						                                     <input type='text' name="idSolicitacaoServico" id="idSolicitacaoServico" maxlength="10" size="10" 
						                                            class="Format[Numero]" />
						                                 </div>
						                          </fieldset>
						                     </div>
						                     <div class="col_25">
						                          <fieldset>
						                               <label >&nbsp;</label>
						                               <div>
						                                <button type='button' name='btnPesquisarRequisicao' class="light"
						                                    onclick='pesquisarRequisicoes();'>
						                                    <img
						                                        src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
						                                    <span><i18n:message key="citcorpore.comum.pesquisar" />
						                                    </span>
						                                </button>
						                               </div>
						                          </fieldset>
						                     </div>
						               </div>
						               <div class="col_100">
						                   <h2 class="section">
						                       <i18n:message key="cotacao.itensRequisicao" />
						                   </h2>
						               </div>
						               <div class="col_100" style='height:300px;overflow:auto;'>
						                   <table id="tblItensRequisicao" class="table">
						                       <tr>
						                           <th width="20px">&nbsp;</th>
						                           <th width="4%"><i18n:message key="citcorpore.comum.numero" /></th>
						                           <th width="5%"><i18n:message key="citcorpore.comum.data" /></th>
						                           <th width="20%"><i18n:message key="centroResultado.custo" /></th>
						                           <th width="20%"><i18n:message key="requisicaoProduto.projeto" /></th>
						                           <th width="20%"><i18n:message key="requisicaoProduto.enderecoEntrega" /></th>
						                           <th colspan="2"><i18n:message key="itemRequisicaoProduto.produto" /></th>
						                           <th width="5%"><i18n:message key="itemRequisicaoProduto.quantidade" /></th>
						                       </tr>
						                   </table>
						               </div>
                                       <div id="divSelecionarItens"></div> 
						         </div>
						    </form>

                        </div>
                    </div>
					<!-- ## FIM - AREA DA APLICACAO ## -->
				</div>
			</div>
		</div>
		<!-- Fim da Pagina de Conteudo -->
	</div>

	<%@include file="/include/footer.jsp"%>
</body>

</html>