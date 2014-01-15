<%@page import="br.com.citframework.util.Constantes"%>
<%@ taglib uri="/tags/cit" prefix="cit"%>
<%@ taglib uri="/tags/i18n" prefix="i18n" %>
<%@page import="br.com.centralit.citcorpore.util.WebUtil"%>
<%@page import="br.com.centralit.citcorpore.bean.UsuarioDTO"%>
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
    <%@include file="/include/internacionalizacao/internacionalizacao.jsp"%>
    <%@include file="/include/security/security.jsp" %>
    <title><i18n:message key="citcorpore.comum.title"/></title>
    <%@include file="/include/noCache/noCache.jsp" %>
    <%@include file="/include/menu/menuConfig.jsp" %>
    <%@include file="/include/header.jsp" %>
    <%@include file="/include/javaScriptsComuns/javaScriptsComuns.jsp" %>   
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/ValidacaoUtils.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/js/PopupManager.js"></script>
    <script type="text/javascript" src="<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/cit/objects/ValidacaoRequisicaoProblemaDTO.js"></script>

    <script>
        addEvent(window, "load", load, false);
        function load(){        
            document.form.afterLoad = function () {
            	parent.escondeJanelaAguarde(); 
            }    
        }        
        function validar() {
            return document.form.validate();
        }
        
        function getObjetoSerializado() {
            var obj = new CIT_ValidacaoRequisicaoProblemaDTO();
            HTMLUtils.setValuesObject(document.form, obj);
            return ObjectUtils.serializeObject(obj);
        }

        
    </script>
<style>
    div#main_container {
        margin: 10px 10px 10px 10px;
        width: 100%;
    }
</style> 
</head>

<body>  
<div >
     <form name='form' action='<%=CitCorporeConstantes.CAMINHO_SERVIDOR%><%=request.getContextPath()%>/pages/validacaoRequisicaoProblema/validacaoRequisicaoProblema'>
           <input type='hidden' name='idValidacaoRequisicaoProblema' id='idValidacaoRequisicaoProblema' /> 
            <input type='hidden' name='dataInicio' id='dataInicio' /> 
            <input type='hidden' name='idProblema' id='idProblema'/> 
	           <div class="columns clearfix">
				<div class="col_66">
					<fieldset>
						<label><i18n:message key="problema.observacaoProblema" /></label>
						<div>
							 <textarea cols='100' rows='5' name="observacaoProblema" maxlength="500"></textarea>  
						</div>
					</fieldset>
				</div>
			</div>
        </form>
</div>

</body>

</html>
