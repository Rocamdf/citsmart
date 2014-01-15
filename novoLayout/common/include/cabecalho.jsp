
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.util.ParametroUtil"%>
<%@page import="br.com.centralit.citcorpore.util.Enumerados.ParametroSistema"%>
<head>
<%@page import="br.com.citframework.util.UtilStrings"%>
<%@page import="br.com.centralit.citcorpore.util.CitCorporeConstantes"%>
<script src="../../novoLayout/common/include/js/internacionalizar.js"></script>
<!-- Compatibilidade para simular telas do chrome no IE -->
<meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
<%String locale = UtilStrings.nullToVazio((String)request.getSession().getAttribute("locale")); %>
<%	String nameUser = null;
	String emailUser = null;
	StringBuffer finalNameUser = new StringBuffer();
	if (WebUtil.getUsuario(request)!= null){
		nameUser = WebUtil.getUsuario(request).getNomeUsuario();
		emailUser = WebUtil.getColaborador(request).getEmail();
		
		/**
		* Procedimento para Abreviar o Nome do Usuário
		* @autor luiz.borges
		* 26/11/2013 09:47
		*/
		if(nameUser != null){
			String[] array = new String[15];
			int index;
			
			if(nameUser.contains(" ")){
				int cont = 0;
			
				nameUser = nameUser.trim();
				array = nameUser.split(" ");
				index = array.length;
				
				for(String nome : array){
					if(cont == 0){
						finalNameUser.append(nome.toUpperCase() + " ");
						cont++;
					}else{
						if(cont == index-1){
							finalNameUser.append(" " + nome.toUpperCase());
						}else{
							if(nome.length() > 3){
								finalNameUser.append(nome.substring(0, 1).toUpperCase() + ". ");
							}
							cont++;
						}
					}
				}					
			}else{
				finalNameUser.append(nameUser.toUpperCase());
			}
		}
		
	}
	
	String urlAtual = request.getServletPath();
%>
<% String asteriskAtivaBotao = ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.SERVASTERISKATIVAR, "N"); %>

<style type="text/css">
#emailUsuario{
font-size: 11px!important;
 text-overflow: ellipsis!important;
}
#sobre-container { display: -webkit-box;-webkit-box-orient: horizontal;display: -moz-box;-moz-box-orient: horizontal;display: box;box-orient: horizontal;margin-top: 10px;}
#sobre-container h2, #versao-container h2 {font-size: 1.3em;margin-bottom: 0.4em;}
#versao-container {margin-top: 30px;}
#produto-descricao{margin-left: 10px;}
#produto-container {line-height: 1.8em;margin-top: 100px;}
#historico{display: none}
.navbar.main .topnav > li.open .dropdown-menu{background:#363432;border:none;box-shadow:none;-webkit-box-shadow:none;-moz-box-shadow:none;right:0;width:283px;}
</style>
</head>
<!-- Wrapper -->
<div class="wrapper">
<!-- //Desenvolvedor: Thiago Matias - Data: 06/11/2013 - Horário: 10:50 - ID Citsmart: 123357 - 
	 //*Motivo/Comentário: quando estiver no portal ao clicar na logo redicionará para o portal, quando estiver no sistema ao clicar na logo redicionará para o sistema   -->
	<% if (urlAtual.equalsIgnoreCase("/pages/portal/portal.jsp")){ %>
	<div class="g-first" id="header-logo">
  		<a href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/portal/portal.load">
  			<img alt="CITSMart" id="logo" src="/citsmart/imagens/logo/logo.png"/>
		</a>
	</div>
	<%} else { %>
	<div class="g-first" id="header-logo">
  		<a href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load">
  			<img alt="CITSMart" id="logo" src="/citsmart/imagens/logo/logo.png"/>
		</a>
	</div>
	
	<%}%>
		
	<!-- Topo Right -->
	<form name='formInternacionaliza' id='formInternacionaliza' class="marginless" action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/internacionalizar/internacionalizar'>
	<input type="hidden" name="locale" id="locale"/>
	<ul class="navbar topnav pull-right">
		<!-- Language menu -->
		<li class="hidden-phone dropdown dd-1 dd-flags" id="lang_nav">
	    <% if (locale.equalsIgnoreCase("pt")) {%>
    		<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    	<%} else {
    		if (locale.equalsIgnoreCase("en")) {%>
    			<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/en.png" alt="en"></a>
    	  <%} else {
    		    if (locale.equalsIgnoreCase("es")) {%>
    				<a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/es.png" alt="es"></a>
    		  <%} else {%>
    			    <a href="#" data-toggle="dropdown"><img id='linguagemAtiva' src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/novoLayout/common/theme/images/lang/br.png" alt="br"></a>
    		  <%}
    		}
    	}%>
    		<ul class="dropdown-menu pull-left">
	    		<li class="active" onclick="internacionalizar('')"><a href="" title="Portugues" ><img onclick="internacionalizar('')" src="../../novoLayout/common/theme/images/lang/br.png" alt="Portugues"> Português BR</a></li>
	      		<li onclick="internacionalizar('en')"><a href="" title="English"><img onclick="internacionalizar('en')" src="../../novoLayout/common/theme/images/lang/en.png" alt="English"> English</a></li>
	      		<li onclick="internacionalizar('es')"><a href="" title="Español"><img onclick="internacionalizar('es')" src="../../novoLayout/common/theme/images/lang/es.png" alt="Espanhol"> Español</a></li>
	    	</ul>
		</li>
		<!-- Language menu END -->
	
		<!-- Dropdown -->
		<li class="dropdown dd-1 visible-desktop">
			<a href="#" data-toggle="dropdown" class="glyphicons shield"><i></i><i18n:message key="citcorpore.comum.ajuda"/> <span class="caret"></span></a>
			<ul class="dropdown-menu pull-right">
				
				<li class="dropdown ">
                  		<a href="/cithelp/index.html" target="blank" class="dropdown-toggle glyphicons adress_book" data-toggle=""><i></i><i18n:message key="citcorpore.comum.guiaUsuario"/></a>
                </li>
                <li><a href="#modal_sobreCitsmart" data-toggle="modal" data-target="#modal_sobreCitsmart" class="glyphicons circle_info"><i18n:message key="citcorpore.comum.sobre"/><i></i></a></li>
			</ul>
		</li>
		<!-- // Dropdown END -->
		
		<!-- Profile / Logout menu -->
		<li class="account dropdown dd-1">
				<a data-toggle="dropdown" href="#" class="glyphicons lock"><span class="hidden-phone"><%=finalNameUser%></span><i></i></a>
			<ul class="dropdown-menu pull-right">
				<li><a href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/empregado/empregado.load" class="glyphicons cogwheel"><i18n:message key="citcorpore.comum.configuracoes"/><i></i></a></li>
				<!-- <li><a href="my_account.html?lang=en&amp;layout_type=fixed&amp;menu_position=menu-left&amp;style=style-light" class="glyphicons camera">My Photos<i></i></a></li> -->
				<li class="profile">
					<span>
						<span class="heading"><i18n:message key="citcorpore.comum.perfil"/> <a href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/alterarSenha/alterarSenha.load" class="pull-right"><i18n:message key="usuario.alterarSenha"/></a></span>
						<span class="img"></span>
						<span class="details">
							<p href=""><%=UtilStrings.nullToVazio(nameUser)%></p>
							<a href="mailto:<%=UtilStrings.nullToVazio(emailUser)%>" id='emailUsuario'><%=UtilStrings.nullToVazio(emailUser)%></a>
						</span>
						<span class="clearfix"></span>
					</span>
				</li>
				<li>
					<span>
						<a class="btn btn-default btn-mini pull-right" href="/citsmart/pages/login/login.load?logout=yes"><i18n:message key="citcorpore.comum.sair"/></a>
					</span>
				</li>
			</ul>
		</li>
		<!-- // Profile / Logout menu END -->
		
	</ul>
	</form>
	<!-- // Top Menu Right END -->
	<ul class="topnav pull-right rimless" >
	<!-- //Desenvolvedor: Thiago Matias - Data: 06/11/2013 - Horário: 10:50 - ID Citsmart: 123357 - 
		 //*Motivo/Comentário: quando estiver no portal será exibidido um botao escrito Acessar o Sistema que redirecionará para o sistema, quando estiver no sistema o botão muda de icone e label  -->
		<% if (urlAtual.equalsIgnoreCase("/pages/portal/portal.jsp")){ %>
		<li class="rimless" >
			<a href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load" data-toggle="" class="glyphicons book_open" ><i></i><b><i18n:message key="portal.acessarSistema"/></b><span class=""> </span></a>
		</li>
		<%} else {%>
		<li class="rimless" >
			<a href="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/index/index.load" data-toggle="" class="glyphicons home"><i></i><b><i18n:message key="cronograma.inicio"/></b><span class=""> </span></a>
		</li>
		<%}%>
		<!-- verificando se o parâmetro está habilitado ou não, caso esteja exibe botão atendimento -->
		<% if (asteriskAtivaBotao.equals("S")){%>
		<!-- botão atendimento -->
		<li class="rimless" style="display: block;">
			<a href="javascript:abreRamalTelefone();" data-toggle="" class="glyphicons headset"><i></i><b><i18n:message key="asterisk.atendimento"/></b><span class=""> </span></a>
		</li>
		<% } %>
		
		<!-- 
		* Procedimento para corrigir problema de quebra do cabeçalho
		* @autor luiz.borges
		* 26/11/2013 16:54
		 -->
		<li class="dropdown dd-1 visible-desktop">
			<a href="#" data-toggle="dropdown" class="glyphicons beach_umbrella"><i></i><i18n:message key="citcorpore.comum.suporte"/> <span class="caret"></span></a>
			<ul class="dropdown-menu pull-right">
				
				<li class="profile">
                  		<span>
                  			<a href="javascript:;" data-toggle="" class="glyphicons phone"><i></i><span class=""> +55 (61) 3966 - 4349 (Brasília)</span></a>
                  		</span>
                </li>
                <li class="profile">
                  		<span>
                  			<a href="javascript:;" data-toggle="" class="glyphicons phone"><i></i><span class=""> 0800 500 3030 (Demais Localidades)</span></a>
                  		</span>
                </li>
                <li class="profile">
                	<span>
                		<a href="mailto:suporte.citsmart@citsmart.com.br?Subject=[<i18n:message key="citcorpore.comum.suporte"/>]" target="top" data-toggle="" class="glyphicons envelope"><i></i> <span class=""> suporte.citsmart@citsmart.com.br </span></a>
                	</span>
                </li>
			</ul>
		</li>
		<!-- // Dropdown END -->
	</ul>	
					
	<div class="clearfix"></div>
</div>			
<!-- // Wrapper END -->

<!-- Modal sobre Citsmart -->
<div class="modal hide fade in" id="modal_sobreCitsmart" data-width="800">
	<!-- Modal heading -->
	<div class="modal-header">
		 <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
		<h3><i18n:message key="citcorpore.comum.sobre"/></h3>
	</div>
	<!-- // Modal heading END -->
	<!-- Modal body -->
	<div class="modal-body">
		<cit:sobreCitsmart></cit:sobreCitsmart> 
	</div>
	<!-- // Modal body END -->
	<!-- Modal footer -->
	<div class="modal-footer">
		<div style="margin: 0;" class="form-actions">
			<a href="#" class="btn " onclick="preencherComboOrigem();" data-dismiss="modal"><i18n:message key="citcorpore.comum.fechar" /></a> 
		</div>
	<!-- // Modal footer END -->
	</div>
</div>