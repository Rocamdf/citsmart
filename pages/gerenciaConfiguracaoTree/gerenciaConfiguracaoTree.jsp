<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.CaracteristicaDTO"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="javax.servlet.http.HttpSession" %>

<!doctype html public "">

<html>

	<head>
	 
		<%
		    response.setHeader("Cache-Control", "no-cache");
		    response.setHeader("Pragma", "no-cache");
			String id = request.getParameter("idBaseConhecimento");
		    response.setDateHeader("Expires", -1);
		    String iframeProblema = "";
		    iframeProblema = request.getParameter("iframeProblema");
		    if (iframeProblema == null) {
		    	iframeProblema = "false";
			}
		%>
		<%@include file="/include/security/security.jsp" %>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		<link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/layout-default-latest.css"/>
		 
		 <!-- Imports do layout -->
		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-latest.js"></script>
   		<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery-ui-latest.js"></script>
    	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/jquery.layout-latest.js"></script>
    	<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/js/debug.js"></script>
		<!-- Imports da jsTree -->
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jstree/_lib/jquery.cookie.js"  type="text/javascript"></script>
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jstree/_lib/jquery.hotkeys.js"  type="text/javascript"></script>
		<script src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/js/jstree/jquery.jstree.js"  type="text/javascript"></script>
		
    	
    
		<!-- Fim Imports da jsTree -->
		
    	<%--<link type="text/css" rel="stylesheet" href="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/css/jquery.ui.all.css"/> --%>
		<style type="text/css">
		#btnVoltar{	
			position: absolute;
			right: 10px;
		}
		
		 #jsTreeIC{
			display: block;
			width: 99%; 
			padding: 7px; 
			background: none repeat scroll 0 0 padding-box #F7F7F7;
		    border: 0 solid rgba(0, 0, 0, 0.05);
		    box-shadow: 0 0 20px rgba(0, 0, 0, 0.3);
		    overflow: auto;
		    position: absolute;
		    top: 0; 
		} 
		
		#treeTop{
			position: relative;
			height: 100%;
		}
		
		
		#FRAME_OPCOES{
			display: block;
			float: left;	
			margin-top: 5px;
			float: left;
			margin-left: 10px;
			padding: 7px;
			background: none repeat scroll 0 0 padding-box #F7F7F7;
		    border: 0 solid rgba(0, 0, 0, 0.05);
		    box-shadow: 0 0 8px rgba(0, 0, 0, 0.3);
		    width: 98%!important;
		    height: 98%!important;
		    min-height: 98%!important;
		
		}
		
		#principalInf{
			display: block;
			float: left;	
			height: 80px;
			width: 99.9%;
			background: #F1F1F1;	
			float: left;
		}
		
		#cabecalhoInf label{
			display: block;
			float: left;
			margin-left: 7px;
		}
		#cabecalhoInf b{
			margin: 0px 5px;
		}
		
		#cabecalhoInf label input{
			display: block;
			padding: 5px;
			background: #CCCCCC;
			width: 99.8%;
		}
		
		#itemConfiguracaoCorpo{
			display: block;
			float: left;
			margin-top: 5px;	
		}
		
		#itemConfiguracaoCorpo{
			display: block;
			width: 99.9%;	
			float: left;
		}
		
		#cabecalhoInf{
			display: block;
			float: left;
			width: 1220px;
		}
		.win {
			background-image: url("../../imagens/windows.png") !important;
		}
		.linux {
			background-image: url("../../imagens/linux.png") !important;
		}
		
		a.l .jstree-icon {
			background-image: url("../../imagens/linux.png") !important;
		}
		a.w .jstree-icon {
			background-image: url("../../imagens/windows.png") !important;
		}
		
		#legenda {
			position: absolute;
			top: 5px;
			right: 5px;
			cursor: pointer;
		}
				
		.jstree ul li ul li a { /* max-width:200px !important ; */ overflow:hidden !important; /* text-overflow: ellipsis !important; */ }
		.jstree li[rel="grupo"] > a { color:blue; font-weight: bold; }
		.jstree li[rel="grupoRelacionado"] > a { color: slategray; font-weight: 400; }
		.jstree li[rel="item"] > a { color: black; }
		.jstree li[rel="itemRelacionado"] > a { color: black; }
			 
		.hidden {
      		display: none;
   		} 
   		
   		<%  HttpSession sessao = ((HttpServletRequest) request).getSession();
			if(sessao.getAttribute("css") != null) {
				out.print(sessao.getAttribute("css").toString());
			}
		%>
		</style>
		
		<script type="text/javascript">
		
		addEvent(window, "load", load, false);		
		
		$(function() {
			
			/** POPUPS **/
			$(".dialog").dialog({
				autoOpen : false,
				resizable : false,
				modal : true
			});
			
			$("#POPUP_PESQUISA").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_GRUPO").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#POPUP_LEGENDA").dialog({
				autoOpen : false,
				width : 600,
				height : 400,
				modal : true
			});
			
			$("#FRAME_ATIVOS").dialog({
				autoOpen : false,
				width : 1000,
				height : 600,
				modal : true
			});
			
			
			$("#btnTodos").css('display','none');
			$("#btnLimpar").css('display','none');
			 			
		});
		
		function load() {
			/* carregarCombosFiltro = function(){
				document.formTree.fireEvent('carregaCombosFiltro');
			} */
	
			/* CRIANDO UMA INSTANCIA DO JSTREE */
			/* Seleciona a arvore container usando JQuery */
			$("#jsTreeIC").jstree({

					/* a lista 'plugins' permite configurar os plugins ativos nesta instancia */
					"plugins" : ["themes","html_data","ui","crrm","hotkeys","dnd","types","contextmenu", "search"],
					
					/* tratamento dos eventos de drag 'n drop. O que pode ser movido e para onde pode ser movido. */
					"crrm" : {
				        "move" : {
				          "check_move" : function (m) {
				        	  /*Receptor*/				        	  
				        	  receptor = m.r.attr("id");
				        	  receptorRel = m.r.attr("rel");
				        	  receptorPai = m.np.attr("id");
				        	  
				        	  splitReceptor = receptor.split("_");
				        	  nomeReceptor = splitReceptor[0];				        	  

				        	  splitReceptorPai = receptorPai.split("_");
				        	  nomeReceptorPai = splitReceptorPai[0];
				        	 
				        	  /*Emissor*/
				        	  emissorRel = m.o.attr("rel");
				        	  emissor = m.o[0].id;
				        	  splitEmissor = emissor.split("_");
				        	  nomeEmissor = splitEmissor[0];
				        	  
				        	  if(nomeEmissor == 'grupo' && emissorRel == 'grupo')
				        		  return false;
				        	  
				        	  else if(nomeEmissor == 'item' && emissorRel == 'itemRelacionado')
				        		  return false;

				        	  else if(nomeEmissor == 'item' && receptorRel == 'grupoRelacionado' ||  receptorRel == 'grupo')
				        		  return true;
				        	  
				        	  else if(nomeEmissor == 'grupo' && emissorRel == 'grupoRelacionado' && receptorRel == 'grupo')
				        		  return true;
				        	  
				        	  else if(nomeEmissor == 'grupo' && emissorRel == 'grupoRelacionado' && receptorRel == 'grupoRelacionado')
				        		  return true;
				        	  
				        	  else if(receptorPai=='jsTreeIC')
				        		  return false;

				        	  else 
				        		  return false;
				        	  
							/* 
				        	  if(nomeEmissor == 'grupo'){
				        		  return false;
				        	  } else {
								  if(nomeReceptor == 'grupo'){
									  if(nomeReceptorPai == 'grupo'){
										  return true;
									  } else {
										  return false;
									  }
					        	  } else {
									return false;
					        	  }
				        	  } */
				          }
				        }
				      },
				      
				      /* Altera o contextMenu dependendo do tipo de node da arvore. */
				      "contextmenu" : {
				    	    "items" : function ($node) {
						        if ($node.attr('rel') == 'grupoRelacionado'){
					    	        return {
					    	            "gNovoGrupo" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.criarNovoGrupo"),
					    	                "action" : function (obj) {
								    	        			if (acessoGravar == 'N'){
								    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
								    	        				return;
								    	        			}					    	                	
															this.create(obj,"first", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoGrupo")}, "attr" : {"rel" : "grupoRelacionado", "id" : "grupo_novo"} } );
														},
					    	                "icon" : "images/group_menu_new.png"
					    	            },
					    	            "gRenomearGrupo" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.renomearGrupo"),
					    	                "action" : function (obj) {
						    	        			if (acessoGravar == 'N'){
						    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
						    	        				return;
						    	        			}						    	                	
					    	                		this.rename(obj); 
					    	                		},
					    	            	"icon" : "images/group_menu_edit.png"
					    	            },
					    	            "gApagarGrupo" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.apagarGrupo"),
					    	                "action" : function (obj) {
								    	        			if (acessoGravar == 'N'){
								    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
								    	        				return;
								    	        			}
								    	        			if (acessoDeletar == 'N'){
								    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoRemocao"));
								    	        				return;
								    	        			}									    	        			
								    	                	if (confirm(i18n_message("gerenciaConfiguracaoTree.desejaExcluir") + " " + trim(obj.children("a").text()) + "?")) {
								    	                		this.remove(obj);
								    						}
					    	                			},
					    	            	"icon" : "images/group_menu_remove.png"
					    	            },
					    	            "gNovoItem" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.criarNovoItem"),
					    	                "action" : function (obj) {
					    	        			if (acessoGravar == 'N'){
					    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
					    	        				return;
					    	        			}						    	                	
					    	                	this.create(obj, "first", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoItem")}, "attr" : {"rel" : "item", "id" : "item_novo"} } ); },
					    	                "icon" : "images/item_menu_new.png"
					    	            }
					    	        };
								} else if ($node.attr('rel') == 'item' || $node.attr('rel') == 'itemRelacionado') {
									return {
					    	           /*  "iListarAtivos" : {
					    	                "label" : "Listar ativos",
					    	                "action" : function (obj) { abrirInventario(obj.attr('id')); },
					    	                "icon" : "images/item_menu_assets.png"
					    	            }, */
					    	            "iVisualizarJanela" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.visualizarJanela"),
					    	                "action" : function (obj) { visualizarJanela(obj.attr('id')); },
					    	                "icon" : "images/viewCadastro.png"
					    	            },
					    	            "iRelacionados" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.verItensRelacionados"),
					    	                "action" : function (obj) { listarRelacionados(obj.attr('id')); },
					    	                "icon" : "images/item_menu_relation.png"
					    	            },
					    	            "iPesquisaRelacionados" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.pesquisarRelacionados"),
					    	                "action" : function (obj) { pesquisarRelacionados(obj.attr('id')); },
					    	                "icon" : "images/item_menu_relation.png"
					    	            },
					    	            "iCriarRelacionado" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.criarItemRelacionado"),
					    	                "action" : function (obj) {
					    	        			if (acessoGravar == 'N'){
					    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
					    	        				return;
					    	        			}						    	                	
					    	                	this.create(obj, "first", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoItemRelacionado")}, "attr" : {"rel" : "itemRelacionado", "id" : "itemRel_novo"} } ); },
					    	                "icon" : "images/item_menu_new.png"
					    	            }, 					    	            
					    	            /* "iApagarItem" : {
					    	                "label" : "Apagar item",
					    	                "action" : function (obj) {
					    	                	if (confirm("Deseja excluir " + trim(obj.children("a").text()) + "?")) {
					    	                		this.remove(obj);
					    						}
		    	                			},
					    	                "icon" : "images/item_menu_remove.png"
					    	            } */
					    	        };
								} else if ($node.attr('rel') == 'grupo' || $node.attr('rel') == 'database') {
									return {
										"nNovoGrupo" : {
												"label" : i18n_message("gerenciaConfiguracaoTree.criarNovoGrupo"),
						    	                "action" : function (obj) {
								    	        			if (acessoGravar == 'N'){
								    	        				alert(i18n_message("gerenciaConfiguracaoTree.semPermissaoAlteracao"));
								    	        				return;
								    	        			}							    	                	
																this.create(obj,"last", {"data" : {"title" : i18n_message("gerenciaConfiguracaoTree.novoGrupo")}, "attr" : {"rel" : "grupoRelacionado", "id" : "grupo_novo"} } );
															},
						    	                "icon" : "images/group_menu_new.png"
										},
										/*
										"nNovoItem" : {
						    	                "label" : "Criar novo item",
						    	                "action" : function (obj) {this.create(obj, "first", {"data" : {"title" : "Novo item"}, "attr" : {"rel" : "item", "id" : "item_novo"} } ); },
						    	                "icon" : "images/item_menu_new.png"
										},*/
										"nExportCMDB" : {
					    	                "label" : i18n_message("gerenciaConfiguracaoTree.exportCMDB"),
					    	                "action" : function (obj) {exportarCMDB(obj.attr('id'));},
					    	                "icon" : "images/database-upload-icon.png"
										} 
									};
								} /* else if ($node.attr('rel') == 'itemRelacionado') {
									return {
										"nNovoGrupo" : {
												"label" : "Criar novo grupo",
						    	                "action" : function (obj) {
																this.create(null,"after", {"data" : {"title" : "Novo grupo"}, "attr" : {"rel" : "grupoRelacionado", "id" : "grupo_novo"} } );
															},
						    	                "icon" : "images/group_menu_new.png"
										},
										"nNovoItem" : {
						    	                "label" : "Criar novo item",
						    	                "action" : function (obj) {this.create(obj, "first", {"data" : {"title" : "Novo item"}, "attr" : {"rel" : "item", "id" : ""} } ); },
						    	                "icon" : "images/item_menu_new.png"
										},
										"nProcurarItem" : {
					    	                "label" : "Procurar itens na rede",
					    	                "action" : function (obj) {this.create(obj, "first", {"data" : {"title" : "Novo item"}, "attr" : {"rel" : "item"} } ); },
					    	                "icon" : "images/item_menu_search.png"
										}
									};
								} */ else {
									return false;
								}
				    	    }
				    	},
				    	
				      /* Altera o padrao de imagens dos icones. */
				      'types': {
				          'types' : {
				        	  /* este e' o mesmo nome que esta' no atributo 'rel' das tags <li> */
				              'grupo' : {
				            	  'text' : 'red',
				                  'icon' : {
				                      'image' : 'images/group_default.png'
				                  },
				                  'valid_children' : 'default'
				              },
				              'database' : {
				            	  'text' : 'red',
				                  'icon' : {
				                      'image' : 'images/database-icon.png'
				                  },
				                  'valid_children' : 'default'
				              },				              
				        	  'item' : {
				                  'icon' : {
				                      'image' : 'images/item_default.png'
				                  }
				              },
				              'grupoRelacionado' : {
				                  'icon' : {
				                      'image' : 'images/group_default.png'
				                  },
				                  'valid_children' : 'default'
				              },
				              'itemRelacionado' : {
				                  'icon' : {
				                      'image' : 'images/item_relation.png'
				                  },
				                  'valid_children' : 'default'
				              }
				          }

				      },
				      
					/* Cada plugin incluido pode ter seus proprios objetos de configuracao. */
					"core" : { }

				})
		    				
				/* Tratamento dos eventos de movimento dos nodes. */
				.bind("move_node.jstree", function (event, data){
					idGrupo = data.rslt.np.attr("id");
					data.rslt.o.each(function () {
						 var i = $(this).attr("id");
						 iReceptor = i.split("_");
			        	 if(iReceptor[0]=='grupo' || iReceptor[0]=='database')
							mudarGrupoDeGrupo($(this).attr("id"), idGrupo);
			        	 else if(iReceptor[0]=='item')
			        		mudarItemDeGrupo ($(this).attr("id"), idGrupo);
			        	 else if(idGrupo=='jsTreeIC')
			        		 return false;
                    });
					 
				})
				
				
				/* Tratamento dos eventos de renomear nodes da arvore. */
				/* Tambem trata os nodes novos pois ao criar um node ele ja renomeia. */
				/* Padrao de IDs para novos: grupo_novo, item_novo, onde x e' o ID do grupo. */
				.bind('rename_node.jstree', function (event, data){
					var id = data.rslt.obj.attr("id");
					var idNumerico = id.replace(/\D/g, "");
					
					var nome = data.rslt.name;
					var nomeTratado = $.trim(nome)
					if (nomeTratado == ''){
						alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoGrupo"));
						$.jstree.rollback(data.rlbk);
					}
																	
					/* Array do ID. Ex.: grupo_15, item_novo, grupo_0, etc. */
					listId = id.split("_");
					
					/* Se for um item ele devera' ter um grupo. */
					if ( listId[0] == 'item' || listId[0] == 'itemRel' || listId[0] == 'grupo' || listId[0] == 'database') {
						var idPai = data.inst._get_parent(data.rslt.obj).attr("id");
						var idNumPai = idPai.replace(/\D/g, "");
					}
					
					
					/* Verifica se e' grupo, item, recem criado ou renomeado. */
					if(listId[1] == 'novo') {
						if ( listId[0] == 'grupo' || listId[0] == 'database') {
							if (nomeTratado.length > 100) {
								alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
								$("#jsTreeIC").jstree("remove","#grupo_novo");
							} else if (nomeTratado == '' || nomeTratado == i18n_message("gerenciaConfiguracaoTree.novoGrupo")) {
								alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoGrupo"));
								$("#jsTreeIC").jstree("remove","#grupo_novo");
							} else {
								atualizarDadosRenomear(idNumerico, nomeTratado, idNumPai);
								criarGrupo(idNumPai, nomeTratado);
								return true;
							}
						} else if (listId[0] == 'item') {
							if (nomeTratado.length > 100) {
								alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
								$("#jsTreeIC").jstree("remove","#item_novo");
							} else if (nomeTratado == '' || nomeTratado == i18n_message("gerenciaConfiguracaoTree.novoItem")) {
								alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoItem"));
								$("#jsTreeIC").jstree("remove","#item_novo");
							} else {
								if(nomeTratado.toLowerCase().indexOf("win") !=-1){	
									$("#item_novo").find("a > ins").removeClass("jstree-icon").addClass("wind");
								}else if(nomeTratado.toLowerCase().indexOf("linux") !=-1){
									$("#item_novo").find("a > ins").removeClass("jstree-icon").addClass("linux");
								}
								criarItem(nomeTratado, idNumPai);
								return true;
							}
						} else if (listId[0] == 'itemRel') {
							if (nomeTratado.length > 100) {
								alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
								$("#jsTreeIC").jstree("remove","#itemRel_novo");								
							} else if (nomeTratado == '' || nomeTratado == i18n_message("gerenciaConfiguracaoTree.novoItemRelacionado")) {
								alert(i18n_message("gerenciaConfiguracaoTree.nomeInvalidoItemRelacionado"));
								$("#jsTreeIC").jstree("remove","#itemRel_novo");								
							} else {
								criarItemRelacionado(nomeTratado, idNumPai);
								return true;
							}
						}
						return false;
					} else if ( listId[0] == 'grupo' || listId[0] == 'database') {
						if (nomeTratado.length > 100) {
							alert(i18n_message("gerenciaConfiguracaoTree.nomeMaior100"));
							$.jstree.rollback(data.rlbk);
						} else {
							atualizarDadosRenomear(idNumerico, nomeTratado, idNumPai);
							document.formTree.fireEvent('renomearGrupo');
						}
					} else if ( listId[0] == 'item' ) {
						/* Ao implementar retirar o 'return false;'. */
						return false;
					}
					
				})
				
				
				/* Tratamento dos eventos de remover nodes da arvore. */
				 .bind("remove.jstree", function (event, data){
					var id = data.rslt.obj.attr("id");
					var idNumerico = id.replace(/\D/g, "");
					
					var nome = data.rslt.obj.children("a").text();
					var nomeTratado = trim(nome);
					
					/* Array do ID. Ex.: grupo_15, item_novo, grupo_0, etc. */
					listId = id.split("_");
					
					/* Se a remocao for de um item novo nao precisa ser tratado pois nao esta salvo em base de dados. */
					if ( listId[1] == 'novo' ) {
						return true;
					}
					
					if ( listId[0] == 'grupo' ) {
						var quantidadeFilhos = 0;
						
						$(data.rslt.obj).find("li").each( function( idx, listItem ) {
							quantidadeFilhos ++;
	                    })
						
	                    if(quantidadeFilhos > 0){
	                    	alert(i18n_message("gerenciaConfiguracaoTree.GrupoNaoPodeSerApagado"));
	                    	$.jstree.rollback(data.rlbk);
	                    } else {
	                    	$("#idGrupoItemConfiguracao").val(idNumerico);
	                    	$("#nomeGrupoItemConfiguracao").val(nomeTratado);
	                    	document.formTree.fireEvent('apagarGrupo');
	                    }
					} else if ( listId[0] == 'item' ) {
						$("#idItemConfiguracao").val(idNumerico);
						
						/* document.formTree.fireEvent('apagarItem'); */
					}					
				})
				
				
				/* Tratamento dos eventos de duplo clique. */
				/* Se for 'grupos' o duplo clique deve expandi-lo. */
				.bind("dblclick.jstree", function (event) {
					var node = $(event.target).closest("li");
					
					if(node.attr('rel') == 'grupo' || node.attr('rel') == 'database' || node.attr('rel') == 'grupoRelacionado'){
						$("#jsTreeIC").jstree("toggle_node", node);
					} else if ( node.attr('rel') == 'item' ) {
						var id = node.attr('id');
						id = Number(id.slice('item-'.length));
						abrirEditarItem(id);
					}
				})
				
				
				/* Tratamento dos eventos de selecao dos nodes. */
				.bind("select_node.jstree", function (event, data) { 
					id = data.rslt.obj.attr("id");
					
					listId = id.split("_");
										
					if(listId[0] == 'grupo' || listId[0] == 'database') {
						$("#jsTreeIC").jstree("deselect_all");
						$("#jsTreeIC").jstree("toggle_node", "#"+id);
						$("#jsTreeIC").jstree("hover_node", "#"+id);
					} else if ( listId[0] == 'item' ) {
						/**
						* Motivo: Definir o valor do idGrupoItemConfiguracao
						* Logo apos selecionar o ic o codigo abaixo verifica o primeiro grupo acima do mesmo e seta o seu valor dentro do input idGrupoItemConfiguracao
						* Autor: flavio.santana
						* Data/Hora: 20/11/2013
						*/
						$('#idGrupoItemConfiguracao').val($($('#'+id).parents("li[rel=grupoRelacionado]:first")).attr("id").slice('grupo_'.length));
						abrirEditarItem(id);
					}
					
				})
				  .bind("loaded.jstree", function (event, data) {
		            $(this).jstree("open_all");
		        })   
				
				var altura = ($(window).height() - 50);
				var largura = ($(window).width() - ($("#jsTreeIC").width() + 55));				
				//$("#jsTreeIC").css("height", altura);
				$("#FRAME_OPCOES").css("height", altura);
				$("#FRAME_OPCOES").css("width", largura);
				
				$(window).resize(function () {
					$("#FRAME_OPCOES").css("width", $(window).width() - ($("#jsTreeIC").width() + 55));	
				});
				
				
				
		}
		function legenda() {
			$("#POPUP_LEGENDA").dialog("open");
		}
		
		/* Funcao TRIM. Remove espacos no inicio e no fim de uma string. */
		function trim(str) {
			return str.replace(/^\s+|\s+$/g,"");
		}
		
		/* Funcao que cria grupos de itens de configuracao. */
		function criarGrupo(id, nome){
			$("#idGrupoItemConfiguracaoPai").val(id);
			$("#nomeGrupoItemConfiguracao").val(nome);
			
			document.formTree.fireEvent('CriarGrupo');
		}
		
		/* Funcao que cria itens de configuracao. */
		function criarItem(nome, idGrupo){
			$("#identificacao").val(nome);
			$("#idGrupoItemConfiguracao").val(idGrupo);
			
			document.formTree.fireEvent('CriarItem');
		}

		/* Funcao que cria itens de configuracao. */
		function criarItemRelacionado(nome, idConfiguracaoPai){
			$("#identificacao").val(nome);
			$("#idItemConfiguracaoPai").val(idConfiguracaoPai);		
			document.formTree.fireEvent('CriarItemRelacionado');
		}
		
		/* Funcao que abre o iframe de edicao de itens. */
		function abrirEditarItem(id){
			/**
			 * Chama o load da página
			 * @author flavio.santana
			 * 25/10/2013 12:00
			 */
			JANELA_AGUARDE_MENU.show();
			var idItem = id.replace(/\D/g, "");	
			if(idItem>"") {
				document.getElementById('iframeOpcoes').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemConfiguracaoTree/itemConfiguracaoTree.load?idItemConfiguracao='+ idItem;
				$("#FRAME_OPCOES").dialog("open");
			}
		}
		
		/* Funcao que lista os itens relacionados ao item escolhido. */
		function listarRelacionados(id){
			/* Bruno.Aquino: Se estiver sendo usado o mozzila como navegador,
			será setado no hidden correspondente, se não será setado a String 'outro'
			para ser feito a validação no action */
			if($.browser.mozilla){
				var browser = "Mozzila";
			} else
				var browser = "outro";
			$("#idBrowserName").val(browser);
			
			JANELA_AGUARDE_MENU.show();
			var idItem = id.replace(/\D/g, "");			
			$("#idItemConfiguracao").val(idItem);			
			document.formTree.fireEvent('listarRelacionados');
		}
		
		/* Funcao que lista os itens relacionados ao item escolhido. */
		function pesquisarRelacionados(id){
			var idItem = id.replace(/\D/g, "");			
			document.getElementById("pesqLockupLOOKUP_ITENSCONFIGURACAORELACIONADOS_iditemconfiguracaopai").value = idItem;
			$('#POPUP_PESQUISA').dialog('open');
		}
		
		function visualizarJanela(id){
			id = id.split('item_').join('');
			document.getElementById('iframeAtivos').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/informacaoItemConfiguracao/informacaoItemConfiguracao.load?id='+ id;
			$("#FRAME_ATIVOS").dialog("open");
		}
		
		function LOOKUP_ITENSCONFIGURACAORELACIONADOS_select(id, desc) {
			document.getElementById('iframeOpcoes').src ='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/itemConfiguracaoTree/itemConfiguracaoTree.load?idItemConfiguracao='+ id;
			limpar_LOOKUP_ITENSCONFIGURACAORELACIONADOS();
			$('#POPUP_PESQUISA').dialog('close');
			$("#FRAME_OPCOES").dialog("open");
		}
		
		/* Funcao que adiciona os itens relacionados 'a arvore. */
		function adicionarRelacionados(idPai, idItemRelacionado, nomeItemRelacionado){
			$("#jsTreeIC").jstree("remove","#"+idItemRelacionado);
			$("#jsTreeIC").jstree("create", $("#"+idPai), "inside",  { "data" : {"title" : nomeItemRelacionado}, "attr" : {"rel" : "itemRelacionado", "id" : idItemRelacionado} }, function() { }, true);
		}
		
		/* Funcao que adiciona os itens relacionados 'a arvore. */
		function adicionarRelacionadosImagem(idPai, idItemRelacionado, nomeItemRelacionado, imagem){
			$("#jsTreeIC").jstree("remove","#"+idItemRelacionado);
			$("#jsTreeIC").jstree("create", $("#"+idPai), "inside",  { "data" : {"title" : nomeItemRelacionado , "icon" : imagem }, "attr" : {"rel" : "itemRelacionado", "id" : idItemRelacionado} }, function() { }, true);
		}
		
		/* Verifica itens criticos. */
		function criticos(id){
			if($("#" + id).attr('rel')=="item") {
				var s = id.split('item_').join('');
				$("#idItemConfiguracao").val(s);		
				document.formTree.fireEvent('verificaCriticidade');
			} 
			$("#" + id).find(".jstree-leaf").each(function() {
				if($(this).attr('rel')=="itemRelacionado") {
					var s = this.id.split('item_').join('');
					$("#idItemConfiguracao").val(s);		
					document.formTree.fireEvent('verificaCriticidade');
				}
			});
		}
		/*Add o icone de item critico*/
		function addCritico(id) {
			$("#"+id).append(($("#"+id).has("img").length ? "" : "<img src='../../imagens/b.gif' />"));
		}
		
		/*Add o icone de item critico para o Pai */
		function addCriticoPai(id) {
			$("#"+id).find("a.r").append(($("#"+id).has("img").length ? "" : "<img src='../../imagens/b.gif' />"));
		}
		
		/* @Deprecated
		/* Funcao que adiciona os itens relacionados em uma popup. 
		*/
		function adicionarRelacionadosPesquisa(idPai, idItemRelacionado, nomeItemRelacionado){
			var nodeLi = createElementWithClassName('div', 'list');
			var nodeL = createElementWithClassName('div', 'listitem');
			
			var nodeA = createElementWithClassName('div', 'url title');
			
			nodeLi.id = idItemRelacionado;
			nodeLi.addEventListener('click', event);
			nodeA.innerHTML=nomeItemRelacionado;			
			nodeL.appendChild(nodeA);
			nodeLi.appendChild(nodeL);
			
			$("#entry").append(nodeLi);	
		}

		/* Tratamento dos eventos de selecao dos nodes a partir do popup. */
		function event(e) {
		  	var box = e.currentTarget;
		  	var id = box.id;
			abrirEditarItem(id);	
			setTimeout(function () { $('#POPUP_PESQUISA').dialog('close'); },1000);
		}
		
		/* Cria um elemento com classe */
		function createElementWithClassName(type, className) {
			var elm = document.createElement(type);
			elm.className = className;
			return elm;
		}

		/* Funcao para mudar um item de grupo. */
		function mudarItemDeGrupo(idItem, idGrupo){
			if (acessoGravar == 'N'){
				alert(i18n_message("baseconhecimento.permissao.sempermissao"));
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load';
				return;
			}			
			var idItemNumero = idItem.replace(/\D/g, "");
			var idGrupoNumero = idGrupo.replace(/\D/g, "");
			$("#idItemConfiguracao").val(idItemNumero);
			$("#idGrupoItemConfiguracao").val(idGrupoNumero);
			document.formTree.fireEvent('mudarItemDeGrupo');
		}
		
		/* Funcao para mudar um item de grupo. */
		function mudarGrupoDeGrupo(idItem, idGrupo){
			if (acessoGravar == 'N'){
				alert(i18n_message("baseconhecimento.permissao.sempermissao"));
				window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load';
				return;
			}
			var idItemNumero = idItem.replace(/\D/g, "");
			var idGrupoNumero = idGrupo.replace(/\D/g, "");
			$("#idGrupoItemConfiguracao").val(idItemNumero);
			$("#idGrupoItemConfiguracaoPai").val(idGrupoNumero);
			document.formTree.fireEvent('mudarGrupoDeGrupo');
		}
		
		function recarregar(){
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree.load';
		}
		
		
		
		/* Funcao para voltar ao menu principal do sistema. */
		function voltar(){
			window.location = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load';
		}
				
		/* Funcao para atualizar o ID de novos nodes de acordo com a base de dados. */
		function atualizarId(idAtual, idNovo){
			var node = document.getElementById(idAtual);
			node.id = idNovo;
		}
		/* Funcao para reload do node atualizado de acordo com a base de dados. */
		function reloadItem(idItem) {
			$("#idItemConfiguracao").val(idItem);			
			document.formTree.fireEvent('listarRelacionados');
		}
		
		/* Renomeia o item de configuração e move de grupo caso necessário. */
		function renomearMoverItemConfiguracao(idItemConfiguracao, identificacao, idGrupo ) {
			if($('#idGrupoItemConfiguracao').val()!="" && $('#idGrupoItemConfiguracao').val()!=idGrupo)
				$('#jsTreeIC').jstree('move_node','#item_'+idItemConfiguracao,'#grupo_'+idGrupo);

			$('#jsTreeIC').jstree('rename_node','#item_'+idItemConfiguracao,identificacao +' - ');
			
		}
		
		function exportarCMDB(idItem){
			$("#idItemConfiguracaoExport").val(StringUtils.onlyNumbers(idItem));
			JANELA_AGUARDE_MENU.show();
			document.formTree.fireEvent('exportarCMDB');
		}
		
		function getFile(pathFile, fileName){
			window.location.href = '<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/baixar.getFile?file=' + pathFile + '&fileName=' + fileName;
		}
		
		function filtar(){
			document.formTree.criticidade.value = $("#cboCriticidade").val();
			document.formTree.status.value = $("#comboStatus").val();
			document.formTree.identificacao.value = $("#identificador").val();
			JANELA_AGUARDE_MENU.show();
			document.formTree.fireEvent("load");
		}
		$(function() {
			
					var myLayout;
					myLayout = $('body').layout({
			          	north__resizable: false // OVERRIDE the pane-default of 'resizable=true'
			          , north__spacing_open: 0 // no resizer-bar when open (zero height)
			          , north__spacing_closed: 350 
			          , north__minSize: 110
			          , west: {
			        	   minSize: 310 
			        	  ,onclose_end: function(){
			        		  $('#FRAME_OPCOES').css('width', '98%');
			        	  }
						  ,onopen_end: function(){
							  $('#FRAME_OPCOES').css('width', '98%');
						}
			          }
		    		});
					
					jQuery.fn.toggleText = function(a,b) {
						return this.html(this.html().replace(new RegExp("("+a+"|"+b+")"),function(x){return(x==a)?b:a;}));
						}; 
					
					/*  $('#divpesquisa').before("<span id='spanPesq' class='manipulaDiv' style='cursor: pointer'>Mostrar Pesquisa</span>"); */
					
					 $('#filtro').css('display', 'none')
					// $('#jsTreeIC').css('height',  $('#west').height()-240);
					 
					 $('#spanPesq').click(function() {
						 document.formTree.fireEvent('carregaCombosFiltro');
						 $('#filtro').toggle(function(){
							 /**
							 * Adiciona o scroll na treeView
							 * @autor flavio.santana
							 */
							 $('#jsTreeIC').animate({ height: $(window).height() - $("#jsTreeIC").offset().top - 20});
						 });
					 });
					 /**
					 * Adiciona o scroll na treeView
					 * @autor flavio.santana
					 */
					 $('#jsTreeIC').animate(
					    { height: $(window).height() - $("#jsTreeIC").offset().top - 20},
					    {
					        complete : function(){
					            //alert('this alert will popup twice');
					        }
					    }
					 );
				});
		
		var acessoGravar = 'N';
		var acessoDeletar = 'N';

		atualizarDadosRenomear = function(idNumerico, nomeTratado, idNumPai){
			$('#idGrupoItemConfiguracao').val(idNumerico);
			$('#nomeGrupoItemConfiguracao').val(nomeTratado);
			$('#idGrupoItemConfiguracaoPai').val(idNumPai);
		}
		
		function fecharJanelaAguarde() {
			JANELA_AGUARDE_MENU.hide();
		}	

		function esconderBotaoVoltar() {
			document.getElementById('divControleLayout').style.display = 'none';
		}	
		</script>
		<style type="text/css">
		
			 
    		/*  .ui-layout-east ,
   			 .ui-layout-east .ui-layout-content {
       			padding:        0;
       			overflow:       hidden;
    		}
    		 .ui-layout-center{
    			height: 100% !important;
    			width: 100% !important;
    		} */ 
    		
    		
    		 .hidden {
        		display:        none;
    		}
			.pageList {
				margin-bottom: 20px;
				min-height: 64px;
			}
			.list:HOVER {
				background-color: #e7e9f9 ;
	  			cursor: pointer;
				background-image: none;
			}
			.list > * {
				border: none;
				border-radius: 0;
				box-sizing: border-box;
				display: -webkit-box;
				height: 32px;
				margin: 0;
			}
			.list > :first-child {
				-webkit-box-align: center;
				-webkit-box-flex: 1;
				-moz-box-align: center;
				-moz-box-flex: 1;
				display: -webkit-box;
				display: -moz-box;
			}
			.pageList .title {
				width: 40%;
			}
			.url {
				-webkit-margin-start: 7px;
				-moz-margin-start: 7px;
				-ms-margin-start: 7px;
				display: block;
				overflow: hidden;
				text-overflow: ellipsis;
				white-space: nowrap;
			}
			/*Legedas*/
			.leg td {
			 	padding: 4px 0 !important;
			 	line-height: 4px;
			 	font-weight: bold;
			}
			.prod {
				background: blue;
			}
			.grup {
				background: green;
			}
			.ic {
				background: red;
			}			
			.ic_rel {
				background: black;
				width: 20px;
				height: 20px;
			}
			.wh20 {
				width: 20px;
				height: 20px;
			}
			.ic_crit {
				background-image: url("../../imagens/b.gif");
				background-repeat: no-repeat;
				width: 6px;
				height: 20px;
			}
			.wind {
				background-image: url("../../imagens/windows.png");
				background-repeat: no-repeat;
				width: 16px;
				height: 16px;
			}
			.linux {
				background-image: url("../../imagens/linux.png");
				background-repeat: no-repeat;
				width: 16px;
				height: 16px;
			}
			/*Legenda TreeView color*/
			.lAtivado { color: #4D90FE !important;}
			.lDesativado { color: #D14836 !important;}
			.lEmManutencao { color: #FF7F00 !important;}
			.lImplantacao { color: #15C !important;}
			.lHomologacao {	color: #1E68BF !important;}
			.lArquivado { color: #5A5A5A !important;}
			.lEmDesenvolvimento { color: #7BB600 !important;}
			.lvalidar { color: #990000 !important;}
			
			/*Legenda Background*/
			.bAtivado { background: #4D90FE !important;}
			.bDesativado { background: #D14836 !important;}
			.bEmManutencao { background: #FF7F00 !important;}
			.bImplantacao { background: #15C !important;}
			.bHomologacao {	background: #1E68BF !important;}
			.bArquivado { background: #5A5A5A !important;}
			.bEmDesenvolvimento { background: #7BB600 !important;}
	
			.manipulaDiv{
			  color: #6A6A6A;
			    cursor: pointer;
			    display: block;
			    font-size: 12px;
			    font-weight: bold;
			    margin-top: 15px;
			    margin-left: 2px;
			
			}
			.manipulaDiv:HOVER{
			  color: #A6CE39;
			    cursor: pointer;
			    display: block;
			    font-size: 12px;
			    font-weight: bold;
			    margin-top: 15px;
			
			}
	
		</style>
	</head>

<cit:janelaAguarde id="JANELA_AGUARDE_MENU"
	title=""
	style="display:none;top:100px;width:300px;left:200px;height:50px;position:absolute;">
</cit:janelaAguarde>

	<body>
	
	<div class="ui-layout-north">
		<div id="divLogo">
			<table cellpadding='0' cellspacing='0'>
				<tr><td>&nbsp;</td></tr>
				<tr>
					<td>
						<img border="0" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/logo/CMDB2.png" />
					</td>
				</tr>
			</table>	
		</div>
	
		<div id="divControleLayout" style="position: fixed;top:1%;right: 2%;z-index: 100000;float: right;display: block;">
				<table cellpadding='0' cellspacing='0'>
					<tr><td>&nbsp;</td></tr>
					<tr>
						<td style='vertical-align: top;'>
							<%-- <label style="margin-left: 20%; font-size: large;"><b><i18n:message key="gerenciaConfiguracaoTree.CMDBExplorer" /></b></label> --%>
							<%
							if (iframeProblema.equalsIgnoreCase("false")){
							%>
							<button  type="button" class="light img_icon has_text" style='text-align: right; margin-left: 99%; float: right; display: block;' onclick="voltar()" title="<i18n:message key="citcorpore.comum.voltarprincipal" />">
								<img border="0" title="<i18n:message key='citcorpore.comum.voltarprincipal' />" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/imagens/back.png" /><span style="padding-left: 0px !important;"><i18n:message key="citcorpore.comum.voltar" /></span>
							</button>	
							<%
							}
							%>
						</td>
					</tr>
				</table>	
			</div>
		</div>
		<div class="ui-layout-west " id="west" style="overflow: hidden;">
			<!-- 
			adicionado div superior
			@autor flavio.santana
			25/10/2013 10:50
			 -->
			<div id="calcTamanho">
				<form action="javascript:;" onsubmit="filtar()">
					<div class="row-fluid">
						<div class="span12">
							<label class="content-row">
								<div><i18n:message key="citcorpore.comum.identificacao" /></div> 
								<span><input name="identificador" id="identificador" type="text" class="text"/></span>
							</label>
						</div>
					</div>
					<div id="filtro">
						<div class="row-fluid">
							<div class="span12">
								<label class="content-row">
									<div><i18n:message key="citcorpore.comum.criticidade" /></div> 
									<span><select id="cboCriticidade" name="cboCriticidade"></select></span>
								</label>
							</div>
						</div>
						<div class="row-fluid">
							<div class="span12">
								<label class="content-row">
									<div><i18n:message key="itemConfiguracaoTree.status" /></div> 
									<span><select id="comboStatus" name="comboStatus"></select></span>
								</label>
							</div>
						</div>
					</div>
					<div class="row-fluid">
						<div class="span12 right">
							<div class="content-row">
								<div class="row-fluid">
									<div class="span6">
										<span id='spanPesq' class='manipulaDiv' style='cursor: pointer'><i18n:message key="gerenciaConfiguracaoTree.outrosFiltros" /> &nbsp;<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/imagens/search.png" /></span>
									</div>
									<div class="span6">
										<button id="buttonFiltro" type="button" title="" class="light" onclick="filtar()" style="float: right !important;">
											<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/magnifying_glass.png">
											<span style="padding-left: 0px!important;"><i18n:message key="gantt.filtrar" /></span>
										</button>
									</div>
								</div>
							</div>
						</div>
					</div>

				</form>
			</div>
			<div id="treeTop">
				<div id="jsTreeIC"></div>
			</div>
		</div>
				
		<div class="ui-layout-center " style="overflow: hidden;" >
			<div id="FRAME_OPCOES" style="overflow: hidden;" >
				<iframe id="iframeOpcoes" name="iframeOpcoes" width="100%" height="100%"></iframe>
			</div>
		</div>
				
		<!-- <div class="ui-layout-east hidden"></div> -->
				
		<!-- <div class="ui-layout-south hidden"></div> -->
		

		<form name="formTree" action="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/gerenciaConfiguracaoTree/gerenciaConfiguracaoTree">
			<input type='hidden' id="idItemConfiguracaoExport" name='idItemConfiguracaoExport'/>
			<input type='hidden' id="idItemConfiguracao" name='idItemConfiguracao'/>							
			
			<%if(id!=null){ %>
				<input type='hidden' name='idBaseConhecimento' id='idBaseConhecimento' value='<%=id %>' /> 
			<%} else {%>
				<input type='hidden' name='idBaseConhecimento' id='idBaseConhecimento' value='' /> 
			<%}%>	
			
			<input type='hidden' id="idItemConfiguracaoPai" name='idItemConfiguracaoPai'/>
			<input type='hidden' id="idBrowserName" name='idBrowserName'/> 
			<input type='hidden' id="idGrupoItemConfiguracao" name='idGrupoItemConfiguracao'/>
			<input type='hidden' id="idGrupoItemConfiguracaoPai" name='idGrupoItemConfiguracaoPai'/>
			<input type='hidden' id="nomeGrupoItemConfiguracao" name='nomeGrupoItemConfiguracao'/>
			<input type='hidden' id="identificacao" name='identificacao'/>
			<input type='hidden' id="criticidade" name='criticidade'/>
			<input type='hidden' id="status" name='status'/>
			<input type='hidden' name='cboCriticidade'/>
			<input type='hidden'  name='comboStatus'/>

		</form>
		
		<div id="POPUP_PESQUISA" title='<i18n:message key="gerenciaConfiguracaoTree.consultaItemRelacionado"/>' style="display: none;">
			<form name='formPesquisaItemConfiguracaoRelacionado'>
				
				<cit:findField formName='formPesquisaItemConfiguracaoRelacionado' 
				lockupName='LOOKUP_ITENSCONFIGURACAORELACIONADOS' 
				id='LOOKUP_ITENSCONFIGURACAORELACIONADOS' top='0' left='0' len='550' heigth='400' 
				javascriptCode='true' 
				htmlCode='true' />
			</form>
		</div>
		
		<div id="FRAME_ATIVOS" style="overflow: hidden;" >
			<iframe id="iframeAtivos" name="iframeAtivos" width="100%" height="100%"></iframe>
		</div>
		
		<div id="POPUP_LEGENDA" title='<i18n:message key="gerenciaConfiguracaoTree.legenda.titulo"/>' style="display: none;">
			<table class="leg">
				<tr>
					<td width="10%"><div class='bAtivado wh20'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.ativado"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='bDesativado wh20'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.desativado"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='bEmManutencao wh20'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.emManutencao"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='bImplantacao wh20'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.implantacao"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='bHomologacao wh20'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.homologacao"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='bEmDesenvolvimento wh20'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.emDesenvolvimento"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='bArquivado wh20'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.arquivado"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='wh20'></div></td>
					<td width="25%"></td>
				</tr>
				<tr>
					<td width="10%"><div class='wind'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.windows"/></td>
					<td width="10%"></td>
					<td width="10%"><div class='linux'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.linux"/></td>
				</tr>
				<tr>
					<td width="10%"><div class='ic_crit'></div></td>
					<td width="25%"><i18n:message key="gerenciaConfiguracaoTree.legenda.icsCriticos"/></td>
					<td width="25%"></td>
					<td width="10%"></td>
					<td width="10%"></td>
					
				</tr>
			</table>
		</div>
		
		
		<%-- <div id="POPUP_GRUPO" title="Grupo Item Configuração" style="display: none;">
			<form name='formPesquisaGrupoItemConfiguracao'>
				
				<cit:findField formName='formPesquisaGrupoItemConfiguracao' 
				lockupName='LOOKUP_GRUPOITENSCONFIGURACAO' 
				id='LOOKUP_GRUPOITENSCONFIGURACAO' top='0' left='0' len='550' heigth='400' 
				javascriptCode='true' 
				htmlCode='true' />
			</form>
		</div> --%>
		
	</body>

</html>