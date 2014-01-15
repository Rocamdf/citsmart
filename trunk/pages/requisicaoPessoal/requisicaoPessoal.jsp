<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<!doctype html public "">
<html>
<head>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>
    <%
        response.setHeader("Cache-Control", "no-cache"); 
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", -1);
        String id = request.getParameter("id");
    %>
		<%@include file="/novoLayout/common/include/libCabecalho.jsp" %>
		<link type="text/css" rel="stylesheet" href="../../novoLayout/common/include/css/template.css"/>  
	    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
	    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
	    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/RequisicaoPessoalDTO.js"></script>
	    	 <%@include file="/novoLayout/common/include/libRodape.jsp" %> 

    <style type="text/css">
	    .campoObrigatorio:before {
			color: #FF0000;
			content: "*";
		}
      /*   .destacado {
            border:1px solid #ddd;
            padding:4px 10px;
            border-left:none;
            background:#eee;
            font-size: 14px;
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
        
        .table1 {
        }
        
        .table1 th {
            border:1px solid #ddd;
            padding:4px 10px;
            background:#eee;
        }
        
        .table1 td {
        }   
             
         div#main_container {
            margin: 0px 0px 0px 0px;
        } 
                
        .container_16
        {
            width: 100%;
            margin: 0px 0px 0px 0px;
            
            letter-spacing: -4px;
        } */
    </style>

    <script>
	    function limpar(){
	   		window.location = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/requisicaoPessoal/requisicaoPessoal.load';
	   	} 
		    
	    var contSelecao = 0;
		function exibirSelecao(objeto) {
			contSelecao = 0;
			HTMLUtils.deleteAllRows('tblSelecao');
			var nome = objeto.toUpperCase().substring(0,1) + objeto.substring(1,objeto.length);
			document.formSelecao.objeto.value = nome;
			document.formSelecao.action = '<%=Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/' + objeto + '/' + objeto;
	 		document.formSelecao.fireEvent('exibeSelecao');
			$("#modal_selecao").modal("show");
		}
		
		function incluirOpcaoSelecao(id, desc, detalhe) {
			contSelecao ++;
			var tbl = document.getElementById('tblSelecao');
			var ultimaLinha = tbl.rows.length;
			var linha = tbl.insertRow(ultimaLinha);
			
			var coluna = linha.insertCell(0);
			coluna.className = "celulaEsquerda";
		    coluna.innerHTML = '<input style="cursor: pointer" type="checkbox" name="id" id="id' + contSelecao + '" value="'+id+'">';
		    
			coluna = linha.insertCell(1);
			coluna.className = "celulaEsquerda";
		    coluna.innerHTML = '<b>' +desc + '</b><input type="hidden" id="desc' + contSelecao + '" name="desc" value="' + desc + '" />';
		    
		    coluna = linha.insertCell(2);
			coluna.className = "celulaEsquerda";
			coluna.innerHTML = detalhe.substring(0,11) +'<span style="cursor: pointer;" title = "'+ detalhe +'"> ... </span> <input type="hidden" id="detalhe' + contSelecao + '" name="detalhe" value="' + detalhe + '" />';
		}
		
		function atribuirSelecao() {
			if (document.formSelecao.id.length) {
				var tbl = document.getElementById('tblSelecao');
				var ultimaLinha = tbl.rows.length;
	            for(var i = 1; i < document.formSelecao.id.length; i++){
	            	if (document.formSelecao.id[i].checked) {
	            		var desc = document.getElementById('desc'+i).value;
						if (!validarLinhaSelecionada(document.formSelecao.objeto.value, ultimaLinha, document.formSelecao.id[i].value, desc))
							return;
					}
				}
	            for(var i = 1; i < document.formSelecao.id.length; i++){
	            	if (document.formSelecao.id[i].checked) {
	            		var desc = document.getElementById('desc'+i).value;
	            		var detalhe = document.getElementById('detalhe'+i).value;
	            		adicionarLinhaSelecionada(document.formSelecao.objeto.value, document.formSelecao.id[i].value, desc, "N", detalhe);
					}
				}
			}
			$("#modal_selecao").modal("hide");
		}
		
		function inicializaContLinha() {
			document.getElementById('contFormacaoAcademica').value = '0';	
			document.getElementById('contCertificacao').value = '0';	
			document.getElementById('contCurso').value = '0';	
			document.getElementById('contExperienciaInformatica').value = '0';	
			document.getElementById('contIdioma').value = '0';	
			document.getElementById('contExperienciaAnterior').value = '0';
			document.getElementById('contConhecimento').value = '0';	
			document.getElementById('contHabilidade').value = '0';
			document.getElementById('contAtitudeIndividual').value = '0';
		}
		
		function adicionarLinhaSelecionada(objeto, id, desc, obrigatorio, detalhe){
			var contLinha = parseInt(document.getElementById('cont'+objeto).value);
			var checked = "";
			if (obrigatorio == "S")
				checked = "checked='true'";	
			contLinha ++;
			eval("document.getElementById('cont"+objeto+"').value = '"+contLinha+"'");
			var nomeTabela = 'tbl'+objeto;
			var tbl = document.getElementById(nomeTabela);
			tbl.style.display = 'block';
			var ultimaLinha = tbl.rows.length;
			var linha = tbl.insertRow(ultimaLinha);
			var coluna = linha.insertCell(0);
			/* coluna.className = "celulaEsquerda"; */
			if(obrigatorio != "S") {
			  coluna.innerHTML = '<img id="imgDel' + contLinha + '" style="cursor: pointer;" title="Clique para excluir" src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/delete.png" onclick="removerLinhaTabela(\''+nomeTabela+'\', this.parentNode.parentNode.rowIndex);">';
			} else {
				coluna.innerHTML = "&nbsp;";
			}
			coluna = linha.insertCell(1);
			/* coluna.className = "celulaEsquerda"; */
			coluna.innerHTML = desc + '<input type="hidden" id="id' + objeto + contLinha + '" name="id'+objeto+'" value="' + id + '" />';
			coluna = linha.insertCell(2);
			/* coluna.className = "celulaEsquerda"; */
			coluna.innerHTML = detalhe.substring(0,11) +'<span style="cursor: pointer;" title = "'+ detalhe +'"> ... </span>';
			coluna = linha.insertCell(3);
			/* coluna.className = "celulaCentralizada"; */
			coluna.innerHTML = '<input style="cursor: pointer" type="checkbox" id="obrig' + objeto + contLinha + '" name="obrig'+objeto+'" value="S" ' + checked + ' >';
		}
		
		function validarLinhaSelecionada(objeto, ultimaLinha, id, desc){
			if (ultimaLinha > 1){
				var arrayId = eval('document.form.id'+objeto);
				for (var i = 1; i < arrayId.length; i++){
					if (arrayId[i].value == id){
						alert(i18n_message("menu.nome.formacaoAcademica") + desc + i18n_message("rh.jaAdicionada"));
						return false;
					}
				}
			}
			return true;
		}
		
		function removerLinhaTabela(idTabela, rowIndex) {
			if (confirm('Deseja realmente excluir?')) {
				HTMLUtils.deleteRow(idTabela, rowIndex);
			}
		}

		
		/**
		 * Desenvolvedor: David Rodrigues - Data: 28/11/2013 - Horário: 15:30 - ID Citsmart: 0 
		 * 
		 * Motivo/Comentário: Tratamento de indice não existente (objeto excluido)
		 *
		 **/
		
	 	function serializaObjetos(objeto, tipo){
	 		var tabela = document.getElementById('tbl'+objeto);
	 		var lista = [];
			var arrayId = eval('document.form.id'+objeto);
			for (var i = 1; i < arrayId.length; i++){
	 			var id = arrayId[i].value;
	 			var chk = document.getElementById('obrig' + objeto + i);
	 			var obr = 'N';
	 			if (chk) {
					if (chk.checked)
						obr = 'S';
				} 				
	 			var obj = eval('new '+tipo+'(' + id + ',"' + obr + '")');
	 			lista.push(obj);
	 		} 	
	 		var ser = ObjectUtils.serializeObjects(lista);
			document.getElementById('serialize'+objeto).value = ser;
	 	}	
	
	 	function RequisicaoFormacaoAcademicaDTO(_id, _obrigatorio){
	 		this.idFormacaoAcademica = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	
	 	function RequisicaoCertificacaoDTO(_id, _obrigatorio){
	 		this.idCertificacao = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	
	 	function RequisicaoCursoDTO(_id, _obrigatorio){
	 		this.idCurso = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	
	 	function RequisicaoExperienciaInformaticaDTO(_id, _obrigatorio){
	 		this.idExperienciaInformatica = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	function RequisicaoIdiomaDTO(_id, _obrigatorio){
	 		this.idIdioma = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	
	 	function RequisicaoExperienciaAnteriorDTO(_id, _obrigatorio){
	 		this.idConhecimento = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	
	 	function RequisicaoConhecimentoDTO(_id, _obrigatorio){
	 		this.idConhecimento = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	
	 	function RequisicaoHabilidadeDTO(_id, _obrigatorio){
	 		this.idHabilidade = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	 	function RequisicaoAtitudeIndividualDTO(_id, _obrigatorio){
	 		this.idAtitudeIndividual = _id; 		
	 		this.obrigatorio = _obrigatorio;
	 	}
	        
		   function configuraPainelAcesso(){
		       if (document.formDescricao.acessoAAlteracoes[0].checked) {
		           $('divPainelAcesso').style.display='block';
		       }else{
		           $('divPainelAcesso').style.display='none';
		       }       
		}
		
		function configuraPerfilCargo(perfil){
		       if (perfil == 1) {
		          /*  document.getElementById('divPerfilCargo').style.display = 'block';
		           document.getElementById('divDown').style.display = 'none';
		           document.getElementById('divUp').style.display = 'block'; */
		    		$("#collapse1").find(".widget-body").each(function() {
		    			$(this).addClass("in");
		    			$("#collapse1").attr('data-collapse-closed',false);
		    			$(this).css("height", "auto");
		    		});
		       }else{
		    	   if (perfil == 2) {
			           /* document.getElementById('divPerfilCargo').style.display = 'none';
			           document.getElementById('divDown').style.display = 'block';
			           document.getElementById('divUp').style.display = 'none'; */
		    		   $("#collapse1").find(".widget-body").each(function() {
		    				$(this).removeClass("in");
		    				$(this).css("height", 0);
		    				$("#collapse1").attr('data-collapse-closed',true) 
		    			});
		    	   }
		    	   else {
		    		   /* document.getElementById('divPerfilCargo').style.display = 'none';
			           document.getElementById('divDown').style.display = 'none';
			           document.getElementById('divUp').style.display = 'none'; */
		    		   $("#collapse1").find(".widget-body").each(function() {
		    				$(this).removeClass("in");
		    				$(this).css("height", 0);
		    				$("#collapse1").attr('data-collapse-closed',true) 
		    			});
		    	   }
		       }       
		}
		   
	   function configuraPainelSalario(chk){
	       if (chk.checked) 
	           document.getElementById('divPainelSalario').style.display = 'none';
	       else
	       	document.getElementById('divPainelSalario').style.display = 'block';
		}
		       
	    function desabilitarTela() {
	        var f = document.form;
	        for(i=0;i<f.length;i++){
	            var el =  f.elements[i];
	            if (el.type != 'hidden') { 
	                if (el.disabled != null && el.disabled != undefined) {
	                    el.disabled = 'disabled';
	                }
	            }
	        }  
	    }    
	    addEvent(window, "load", load, false);
	    function load(){        
	        document.form.afterLoad = function () {
	        	configuraPerfilCargo(0);
	        	if (document.form.idSolicitacaoServico != '') {
	        		configuraPainelSalario(document.form.salarioACombinar);
	        		configuraPerfilCargo(2);
	        	}
	            if (document.form.editar.value != '' && document.form.editar.value != 'S')
	                desabilitarTela();
	            parent.escondeJanelaAguarde();                    
	        }    
	    }
	
	    function validar() {
	        return document.form.validate();
	    }
	
		function restoreCargo() {
		   document.form.fireEvent("restoreCargo");
		}
		      
		function getObjetoSerializado() {
	        	serializaObjetos('FormacaoAcademica','RequisicaoFormacaoAcademicaDTO');
				serializaObjetos('Certificacao','RequisicaoCertificacaoDTO');
				serializaObjetos('Curso','RequisicaoCursoDTO');
				serializaObjetos('ExperienciaInformatica','RequisicaoExperienciaInformaticaDTO');
				serializaObjetos('Idioma','RequisicaoIdiomaDTO');
				serializaObjetos('Conhecimento','RequisicaoConhecimentoDTO');
				serializaObjetos('ExperienciaAnterior','RequisicaoExperienciaAnteriorDTO');
				serializaObjetos('Habilidade','RequisicaoHabilidadeDTO');
				serializaObjetos('AtitudeIndividual','RequisicaoAtitudeIndividualDTO');
			
	            var obj = new CIT_RequisicaoPessoalDTO();
	            HTMLUtils.setValuesObject(document.form, obj);
	    		obj.serializeFormacaoAcademica = document.form.serializeFormacaoAcademica.value;
				obj.serializeCertificacao = document.form.serializeCertificacao.value;
				obj.serializeCurso = document.form.serializeCurso.value;
				obj.serializeExperienciaInformatica = document.form.serializeExperienciaInformatica.value;
				obj.serializeIdioma = document.form.serializeIdioma.value;
				obj.serializeExperienciaAnterior = document.form.serializeExperienciaAnterior.value;
				obj.serializeConhecimento = document.form.serializeConhecimento.value;
				obj.serializeHabilidade = document.form.serializeHabilidade.value;
				obj.serializeAtitudeIndividual = document.form.serializeAtitudeIndividual.value;
	            return ObjectUtils.serializeObject(obj);
	     }   

		function abilitarCampoSalario() {
	    	if (document.getElementById('salarioACombinar').checked == true) {
	    		document.getElementById('salario').value = '';
	    		document.getElementById('salario').disabled = true;
	    	}else{
	    		document.getElementById('salario').disabled = false;
			}
	    }
	
	</script>
</head>

<body> 


	<div class="container-fluid fixed" '>
        <div class="wrapper">
            <div id="tabs-2" class="box-generic" style="overflow: hidden;">
                        <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/requisicaoPessoal/requisicaoPessoal'>
                                <input type='hidden' name='idSolicitacaoServico' id='idSolicitacaoServico' /> 
                                <input type='hidden' name='editar' id='editar' /> 
                                <input type='hidden' name='acao' id='acao'/> 
									
                                <input type='hidden' name='idFormacaoAcademica'/>
                                <input type='hidden' name='idCertificacao'/>
                                <input type='hidden' name='idCurso'/>
                                <input type='hidden' name='idExperienciaInformatica'/>
                                <input type='hidden' name='idExperienciaAnterior'/>
                                <input type='hidden' name='idIdioma'/>
                                <input type='hidden' name='idConhecimento'/>
                                <input type='hidden' name='idHabilidade'/>
                                <input type='hidden' name='idAtitudeIndividual'/>
                                
                                <input type='hidden' name='contFormacaoAcademica' id='contFormacaoAcademica' value='0'/>
                                <input type='hidden' name='contCertificacao' id='contCertificacao' value='0'/>
                                <input type='hidden' name='contCurso' id='contCurso' value='0'/>
                                <input type='hidden' name='contExperienciaInformatica' id='contExperienciaInformatica' value='0'/>
                                <input type='hidden' name='contExperienciaAnterior' id='contExperienciaAnterior' value='0'/>
                                <input type='hidden' name='contIdioma' id='contIdioma' value='0'/>
                                <input type='hidden' name='contConhecimento' id='contConhecimento' value='0'/>
                                <input type='hidden' name='contHabilidade' id='contHabilidade' value='0'/>
                                <input type='hidden' name='contAtitudeIndividual' id='contAtitudeIndividual' value='0'/>					                                

                                <input type='hidden' name='serializeFormacaoAcademica' id='serializeFormacaoAcademica'/>
                                <input type='hidden' name='serializeCertificacao' id='serializeCertificacao'/>
                                <input type='hidden' name='serializeCurso' id='serializeCurso'/>
                                <input type='hidden' name='serializeExperienciaInformatica' id='serializeExperienciaInformatica'/>
                                <input type='hidden' name='serializeIdioma' id='serializeIdioma'/>
                                <input type='hidden' name='serializeExperienciaAnterior' id='serializeExperienciaAnterior'/>
                                <input type='hidden' name='serializeConhecimento' id='serializeConhecimento'/>
                                <input type='hidden' name='serializeHabilidade' id='serializeHabilidade'/>
                                <input type='hidden' name='serializeAtitudeIndividual' id='serializeAtitudeIndividual'/>									

							<div class="row-fluid">
								<div class='span4'>
									<label for='idCargo' class='strong campoObrigatorio'><i18n:message key="cargo.cargo" /></label>
									<select name='idCargo' id='idCargo' class="Valid[Required] Description[Cargo] span10"  onchange="document.form.fireEvent('restoreCargo');"></select>
								</div>
								<div class='span4'>
									<label for='vagas' class='strong campoObrigatorio'><i18n:message key="requisicaoPessoal.numeroVagas" /></label>
									<input type='text' name='vagas' class="Valid[Required] Description[Número de Vagas] Format[Numero] span10"   maxlength="9"  class='span6'/>
								</div>
								<div class='span4'>
									<label for='confidencial' class='strong campoObrigatorio'><i18n:message key="requisicaoPessoal.vagaConfidencial" /></label>
									<select name='confidencial' class="Valid[Required] Description[Vaga confidencial]" >
	                                   <option value=" "> --- Selecione --- </option>
	                                   <option value="S">Sim</option>
	                                   <option value="N">Não</option>
	                                </select>
								</div>
							</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='tipoContratacao' class='strong campoObrigatorio'><i18n:message key="requisicaoPessoal.tipoContratacao" /></label>
								<select name='tipoContratacao' class="Valid[Required] Description[Tipo de contratação]" ></select>
							</div>
							<div class='span4'>
								<label for='motivoContratacao' class='strong campoObrigatorio'><i18n:message key="requisicaoPessoal.motivoContratacao" /></label>
								<select name='motivoContratacao' class="Valid[Required] Description[Motivo da contratação]" >
                               	      <option value=''> --- Selecione ---</option>
                               	      <option value='N'> Novo cargo </option>
                               	      <option value='D'> Demissão de pessoal </option>
                               	      <option value='A'> Aumento da demanda </option>
                               	      <option value='R'> Requisição do cliente </option>
                                </select>
							</div>
							<div class='span4'>
							<label class='strong'><i18n:message key="requisicaoPessoal.salario" /></label>
								<div class='row-fluid'>
									<div class='span6'>
										<input type="checkbox" onclick="abilitarCampoSalario();" name="salarioACombinar" value="S" >
		                                 <i18n:message key="requisicaoPessoal.salarioACombinar" />
	                                 </div>
	                                 <div class='span6'>
		                                 <div id='divPainelSalario' style='display:block'>
		                                      <div class="input-prepend input-append">
													<span id="spanSlario" class="add-on">R$</span>
													<input class="Format[Moeda] span10" id="salario" name='salario' size='10' maxlength="100"  > 
											  </div>
			                         	</div>
		                         	</div>
	                         	</div>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='idPais' class='strong'><i18n:message key="unidade.pais" /></label>
								<select name='idPais' id="idPais"  onchange="document.form.fireEvent('preencherComboUfs');" class="Description[unidade.pais]"  ></select>
							</div>
							<div class='span4'>
								<label for='idUf' class='strong'><i18n:message key="localidade.uf" /></label>
								<select name='idUf' id="idUf" onchange="document.form.fireEvent('preencherComboCidade');" class="Description[uf]"  ></select>
							</div>
							<div class='span4'>
								<label for='idCidade' class='strong'><i18n:message key="localidade.cidade" /></label>
								<select id="idCidade" name='idCidade'  class="Description[Cidade]" ></select>
							</div>
						</div>
						
						<div class="row-fluid">
							<div class='span4'>
								<label for='idUnidade' class='strong campoObrigatorio'><i18n:message key="citcorporeRelatorio.comum.lotacao" /></label>
								<select name='idUnidade' class="Valid[Required] Description[Lotação]" ></select>
							</div>
							<div class='span4'>
								<label for='idJornada' class='strong'><i18n:message key="requisicaoPessoal.horarios" /></label>
								<select name='idJornada' id='idJornada'  ></select>
							</div>
							<div class='span4'>
								<label for='beneficios' class='strong'><i18n:message key="requisicaoPessoal.beneficios" /></label>
								<textarea name='beneficios' rows="4"  class='span8' maxlength="100"></textarea>
							</div>
						</div>
						<div class="row-fluid">
							<div class='span4'>
								<label for='idCentroCusto' class='strong campoObrigatorio'><i18n:message key="requisicaoPessoal.centroCusto" /></label>
								<select name='idCentroCusto' class="Valid[Required] Description[Centro de custo]" ></select>
							</div>
							<div class='span4'>
								<label for='idProjeto' class='strong campoObrigatorio'><i18n:message key="requisicaoPessoal.projeto" /></label>
								<select name='idProjeto' class="Valid[Required] Description[Projeto]" ></select>
							</div>
							<div class='span4'>
								<label for='folgas' class='strong'><i18n:message key="requisicaoPessoal.folgas" /></label>
								<input type='text' name='folgas' size='10' maxlength="100" />
							</div>
						</div>
                      
                                <div class=''>
                                <div class="widget row-fluid" data-toggle="collapse-widget" id='collapse1'>
	
									<!-- Widget heading -->
									<div class="widget-head">
										<h4 class="heading"><i18n:message key="requisicaoPessoal.perfilCargo"/></h4>
										<!-- <span class="collapse-toggle"></span> -->
									</div>
									<!-- // Widget heading END -->
									
									<div class="widget-body collapse in">
                                <div id='divPerfilCargo' style='display:block!important' class='span12'>
                                <div class="span12">				
										<label for='atividades' class="campoObrigatorio"><i18n:message key="solicitacaoCargo.atividades"/></label>
										<div>
											<textarea rows="5" cols="122" class='span12' name='atividades'></textarea>
										</div>
								</div>
								
								<div class="span12">
								<h4 id="perfilProfissional" class=""><i18n:message key="solicitacaoCargo.perfilProfissional"/></h4>
								</div>
								<div class="row-fluid">
									
													
											<div class="span6">
											<label class="campoObrigatorio strong"><i18n:message key="solicitacaoCargo.formacaoAcademica"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("formacaoAcademica")' style="cursor: pointer;" title="Clique para adicionar uma formação acadêmica"></label>
											
												<div  id="gridFormacaoAcademica">
													<table id="tblFormacaoAcademica" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none">
														<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>
											
										</div>
										
										<div class="span6">
										
											<label class=' strong'><i18n:message key="solicitacaoCargo.certificacoes"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("certificacao")' style="cursor: pointer;" title="Clique para adicionar uma certificação"></label>
											<div  id="gridCertificacao">
												<table id="tblCertificacao" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>
										
										</div>
								</div>
								<div class="row-fluid">
									 <div class='span6'>
									  
										<label class=' strong'><i18n:message key="solicitacaoCargo.cursos"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("curso")' style="cursor: pointer;" title="Clique para adicionar um curso"></label>
											<div  id="gridCurso">
												<table id="tblCurso" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>
									    
									  </div>
									  <div class='span6'>
										  
												<label class=' strong'><i18n:message key="solicitacaoCargo.experienciaInformatica"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("experienciaInformatica")' style="cursor: pointer;" title="Clique para adicionar uma experência em informática"></label>
												<div  id="gridExperienciaInformatica">
													<table id="tblExperienciaInformatica" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
														<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>
										    
									  </div>
								</div>
								<div class="row-fluid">								  
									  <div class='span6'>
									  
										<label class=' strong'><i18n:message key="solicitacaoCargo.idiomas"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("idioma")' style="cursor: pointer;" title="Clique para adicionar um idioma"></label>
											<div  id="gridIdioma">
												<table id="tblIdioma" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>
									    
									  </div>
									  <div class='span6'>
										  
												<label class=' strong'><i18n:message key="solicitacaoCargo.experienciaAnterior"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("experienciaAnterior")' style="cursor: pointer;" title="Clique para adicionar uma experiência anterior"></label>
												<div  id="gridExperienciaAnterior">
													<table id="tblExperienciaAnterior" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
														<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>
										    
									  </div>
								</div>
								<div class="span12">
								  <h4 id="perfilCompetencia" class="section"><i18n:message key="solicitacaoCargo.perfilCompetencia"/></h4>
								  </div>
								  <div class='row-fluid'>
									  <div class='span6'>
										  
												<label class="campoObrigatorio strong"><i18n:message key="solicitacaoCargo.conhecimentos"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("conhecimento")' style="cursor: pointer;" title="Clique para adicionar um conhecimento"></label>
												<div  id="gridConhecimento">
													<table id="tblConhecimento" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
														<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
													</table>
												</div>
										    
									  </div>
									  <div class='span6'>
									  
										<label class="campoObrigatorio strong"><i18n:message key="solicitacaoCargo.habilidades"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("habilidade")' style="cursor: pointer;" title="Clique para adicionar uma habilidade"></label>
											<div  id="gridHabilidade">
												<table id="tblHabilidade" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
															<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
															<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
															<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
															<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
														</tr>
												</table>
											</div>
									    
									  </div>
									</div>
								<div class="row-fluid">								  
								  <div class='span12'>
									  
											<label class="campoObrigatorio strong"><i18n:message key="solicitacaoCargo.atitudes"/><img src="<%=Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/add.png"  onclick='exibirSelecao("atitudeIndividual")' style="cursor: pointer;" title="Clique para adicionar uma atitude individual"></label>
											<div  id="gridAtitudeIndividual">
												<table id="tblAtitudeIndividual" class="dynamicTable table table-striped table-bordered table-condensed dataTable" style="display: none;">
													<tr>
														<th style="width: 2%;font-size:10px; " ><i18n:message key="rh.acao"/></th>
														<th style="font-size:10px;" ><i18n:message key="rh.descricao"/></th>
														<th style="width: 40%;font-size:10px;" ><i18n:message key="rh.detalhes"/></th>
														<th style="width: 10%;font-size:10px;" ><i18n:message key="rh.obrigatoria"/></th>
													</tr>
												</table>
											</div>
									    
								  	</div>
								</div>
							</div>
						</div>
					</div> 
					</div> 
							<div class="row-fluid">	
								  <div class="span12">
								  <label for='observacoes' id="informacoesComplementares" class="section"><i18n:message key="solicitacaoCargo.complementos"/></label>
								  </div>
							</div>
							<div class="row-fluid">	
								  <div class="span12">				
									
											<div>
												<textarea rows="5" cols="122" name='observacoes' class='span12'></textarea>
											</div>
									
								</div>
							</div>
								</form>
							</div>
							</div>
					
                    </div>
            </div>  
        </div>
       </div>
			<div class="modal hide fade in" id="modal_selecao" aria-hidden="false">
					<!-- Modal heading -->
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
						<h3></h3>
					</div>
					<!-- // Modal heading END -->
					<!-- Modal body -->
					<div class="modal-body">
					 <form name='formSelecao' method="POST" action=''>
						<input type='hidden' name='id'/>
						<input type='hidden' name='sel'/>
						<input type='hidden' name='objeto' id='objetoSel'/>
						<div id='divSelecao' style='height:180px;overflow: auto;'>
							<table id="tblSelecao" class="table">
								<tr>
									<th style="width: 1%;" ><i18n:message key="rh.acao"/></th>
									<th ><i18n:message key="rh.descricao"/></th>
									<th style="width: 40%;" ><i18n:message key="rh.detalhes"/></th>
								</tr>
							</table>
						</div>
		  			 </form>
					</div>
					<!-- // Modal body END -->
					<!-- Modal footer -->
					<div class="modal-footer">
						<div style="margin: 0;" class="form-actions">
							<input type='button' class='btn btn-primary' name='btnSelecionar' value='<i18n:message key="rh.selecionar" />' onclick='atribuirSelecao()'/>
				            <a href="#" class="btn "  data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a>
						</div>
					<!-- // Modal footer END -->
				</div>
			</div> 

</body>

</html>
