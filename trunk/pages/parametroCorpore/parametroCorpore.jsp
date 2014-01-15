<%@taglib uri="/tags/cit" prefix="cit"%>
<%@taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.citframework.util.UtilDatas"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
<%@page import="br.com.centralit.citcorpore.bean.ParametroCorporeDTO"%>
<!doctype html public "">
<html>
	<head>
		<%
			/**
			*  ATENCAO! O CAMPO VALOR NAO EH OBRIGATORIO! A INFORMACAO VAZIA REPRESENTA MUITO PRA VARIAS COISAS! 
			*
			*      EMAURI - 16/08/2012
			*
			*
			*/
			response.setHeader( "Cache-Control", "no-cache");
			response.setHeader( "Pragma", "no-cache");
			response.setDateHeader ( "Expires", -1);
		%>
		<title><i18n:message key="citcorpore.comum.title"/></title>
		<%@include file="/include/security/security.jsp" %>
		<%@include file="/include/noCache/noCache.jsp" %>
		<%@include file="/include/header.jsp" %>
		<%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp"%>
		
		<script type="text/javascript" src="../../js/parametroCorpore.js"></script>
		<script charset="ISO-8859-1" type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
		
		<script>
			var popup;
		    addEvent(window, "load", load, false);
		    function load(){		
				popup = new PopupManager(850, 500, "<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/");
		    }	
		    
			exportar =  function(){
			document.form.fireEvent('exportarParametroCsv');
			JANELA_AGUARDE_MENU.show();
			}
			
			salvar =  function(){
				var pass = document.getElementById("valor");
				if(pass.type == 'password'){
					removeAllEventos()
					limpaElemntoValidacao(); 
					$('#valor').unmask(); 
					$('#valor').removeClass();
					removeAllEventos();
				}
				document.form.save();
			}
			
			function LOOKUP_PARAMETROCORPORE_select(id, desc) {
				document.form.restore({
					id : id
				});
			}
			
			function limpaCaracteristica(){
				removeAllEventos()
				limpaElemntoValidacao(); 
				$('#valor').unmask(); 
				$('#valor').removeClass();
				removeAllEventos();
				 $("#valor").attr('maxlength','200');
			}
			function MudarCampovalorParaTipoSenha(){
				removeAllEventos()
				limpaElemntoValidacao(); 
				$('#valor').unmask(); 
				$('#valor').removeClass();
				removeAllEventos();
				var pass = document.getElementById("valor");
				pass.type = 'password';
				//$('#valor').attr('type', 'password');
			}
			function MudarCampovalorParaTipoTexto(){
				removeAllEventos()
				limpaElemntoValidacao(); 
				$('#valor').unmask(); 
				$('#valor').removeClass();
				removeAllEventos();
				var pass = document.getElementById("valor");
				pass.type = 'text';
			}
			function limpaElemntoValidacao(){	
				var element = document.form.valor;
				var aux = element.validacao;
				if (aux == null || aux == undefined){
					element.validacao = '';
				}else{
					element.validacao = '';
				}
				
				var aux = element.descricao;
				if (aux == null || aux == undefined){
					element.descricao = '';
				}else{
					element.descricao = '';
				}
			}
			
		    function setaLingua(){
		    	document.getElementById("pesqLockupLOOKUP_PARAMETROCORPORE_d_idlingua").value = document.formPesquisa.idLingua.value;
		    }
			
			function mascara(tipo){
		
				if(tipo == "Date"){
				 	$("#valor").mask("99/99/9999"); 
				    $("#valor").attr('maxlength','10');
				
				} else if(tipo == "CPF"){
			 		$("#valor").mask("999.999.999-99");
					$("#valor").attr('maxlength','14');
					
				} else if(tipo == "Telefone"){
					$("#valor").mask("(99)9999-9999"); 
					$("#valor").attr('maxlength','13');
					
				} else if(tipo == "Hora"){
					 $("#valor").mask('99:99'); 
					$("#valor").attr('maxlength','5');
					
				} else if(tipo == "Numero"){
					$("#valor").mask("999999999999999999999999999999"); 
					$("#valor").attr('maxlength','30');
					
				} else if(tipo == "CNPJ"){
				 	$("#valor").mask("99.999.999/9999-99"); 
					$("#valor").attr('maxlength','17');
				} else if(tipo == "CEP"){
				 	$("#valor").mask("99.999-999"); 
					$("#valor").attr('maxlength','10');
				} 
			}
						
			function removeAllEventos(){
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataMoedaSaidaCampo, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataDecimalSaidaCampo, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataNumero, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataData, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataHora, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataCNPJ, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataCPF, false);
				document.getElementById('valor').removeEventListener('keydown',DEFINEALLPAGES_formataMoeda, false);

				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataMoedaSaidaCampo, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataDecimalSaidaCampo, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataNumero, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataData, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataHora, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataCNPJ, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataCPF, false);
				document.getElementById('valor').removeEventListener('blur',DEFINEALLPAGES_formataMoeda, false);
			}
			
		</script>
		
	</head>
	<body>	
		<!-- Rodrigo Pecci Acorse - 22/11/2013 14h40 - Removido o overflow:hidden pois quebrava o menu. -->
		<div id="wrapper">
			<%@include file="/include/menu_vertical.jsp"%>
			<div id="main_container" class="main_container container_16 clearfix">
				<%@include file="/include/menu_horizontal.jsp"%>
							
				<div class="flat_area grid_16">
					<h2><i18n:message key="parametroCorpore.parametroCorpore"/></h2>						
				</div>
				
				<div class="box grid_16 tabs">
					<ul class="tab_header clearfix">
						<li>
							<a href="#tabs-1"><i18n:message key="parametroCorpore.cadastro"/></a>
						</li>
						<li>
							<a href="#tabs-2" class="round_top" onclick="setaLingua();"><i18n:message key="parametroCorpore.pesquisa"/></a>
						</li>
					</ul>				
					<a href="#" class="toggle">&nbsp;</a>
					<div class="toggle_container">
						<div id="tabs-1" class="block">
							<div class="section">											
								<form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/parametroCorpore/parametroCorpore'>
									<div class="columns clearfix">
										<input type='hidden' name='id'/>
										<input type='hidden' name='idEmpresa'/>
										<input type='hidden' name='dataInicio'/>
										<input type='hidden' name='dataFim'/>
										<input type='hidden' name='tipoDado'/>
										<div class="columns clearfix">
											<div class="col_25">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="parametroCorpore.id"/>:</label>
														<div>
														  <input type='text' name="idAux" maxlength="11" size="20" readonly="readonly"/>
														</div>
												</fieldset>
											</div>										
											<div class="col_50">				
												<fieldset>
													<label class="campoObrigatorio"><i18n:message key="parametroCorpore.parametro"/></label>
														<div>
														  <input type='text' name="nome" maxlength="70" size="70" class="Valid[Required] Description[parametroCorpore.parametro]" readonly="readonly"/>
														</div>
												</fieldset>
											</div>
											<div class="col_50">
												<fieldset>
													<label><i18n:message key="citcorpore.comum.valor"/></label>
														<div>
														  <input type='text' name="valor" maxlength="200" size="70" class="" />
														</div>
												</fieldset>
											</div>
										</div>
										<br>
									</div>	
									<div id="popupCadastroRapido">
									     <iframe id="frameCadastroRapido" name="frameCadastroRapido" width="100%" height="98%"></iframe>
								    </div>	
									<br><br>
					<!-- 	Desenvolvedor: Pedro Lino - Data: 29/10/2013 - Horário: 15:00 - ID Citsmart: 120948 - 
					* Motivo/Comentário: Removido icone do botao exportar csv  -->
									<button type='button' name='btnGravar' class="light text_only has_text"  onclick='salvar();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/pencil.png">
										<span><i18n:message key="citcorpore.comum.gravar"/></span>
									</button>
									<button type='button' name='btnLimpar' class="light text_only has_text" onclick='document.form.clear();MudarCampovalorParaTipoTexto();'>
										<img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/grey/clear.png">
										<span><i18n:message key="citcorpore.comum.limpar"/></span>
									</button>
									<button type='button' name='btnImportar' class="light text_only has_text" onclick="popup.abrePopup('cargaParametroCorpore', ' ')">
										<span><i18n:message key="parametroCorpore.importarDados"/></span>
									</button>
									<button type='button' name='btnGerar' class="light text_only has_text" onclick = "exportar();">
										<%-- <img src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/template_new/images/icons/small/util/excel.png"  style="padding-left: 0px;"> --%>
										<span><i18n:message key="parametroCorpore.exportarCSV"/></span>
									</button>
								</form>
							</div>
						</div>
						<div id="tabs-2" class="block">
							<div class="section"><i18n:message key="citcorpore.comum.pesquisa"/>
								<form name='formPesquisa'>
									<cit:findField formName='formPesquisa' lockupName='LOOKUP_PARAMETROCORPORE' id='LOOKUP_PARAMETROCORPORE' top='0' left='0' len='550' heigth='400' javascriptCode='true' htmlCode='true' />
									<input type='hidden' name='idLingua'/>
								</form>
							</div>
						</div>
					</div>
				</div>		
			</div>
		</div>
		<%@include file="/include/footer.jsp"%>
	</body>
</html>