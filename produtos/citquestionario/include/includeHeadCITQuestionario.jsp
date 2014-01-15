<!-- FCK Editor -->
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashMap"%>
<%@page import="br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO"%>
    <script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/fckeditor.js"></script>

<script>    
    var mapQuestoesComSigla = new HashMap(); 

	function onInitQuestionario(){
        <%
        Collection<QuestaoQuestionarioDTO> colQuestoesComSigla = (Collection)request.getAttribute("colQuestoesComSigla");
        if (colQuestoesComSigla != null) {
            for (QuestaoQuestionarioDTO questaoDto: colQuestoesComSigla) {
                %>
                mapQuestoesComSigla.set('<%=questaoDto.getSigla()%>', '<%=questaoDto.getIdQuestaoQuestionario()%>');
                <%
            }
         }
		Collection lst = (Collection) request.getAttribute("LST_CAMPOS_EDITOR");
		if (lst == null)
			lst = new ArrayList();
		
		for(Iterator it = lst.iterator(); it.hasNext();){
			String fieldName = (String)it.next();
		%>

		var oFCKeditor = new FCKeditor( '<%=fieldName%>' ) ;

		oFCKeditor.BasePath	= '<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/fckeditor/';
		oFCKeditor.Config['ToolbarStartExpanded'] = false ;

		oFCKeditor.ToolbarSet	= 'Default' ;
		oFCKeditor.ReplaceTextarea() ;		
		<%
			}
		%>
	}
	HTMLUtils.addEvent(window, "load", onInitQuestionario, false);
</script>	
<!-- FCK Editor -->

<link rel="stylesheet" type="text/css" id='cssCalendario' media="all" href="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/calendario/calendar-win2k-cold-1.css" title="win2k-cold-1"/>

<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/calendario/calendar.js"></script>
<script type="text/javascript" src="<%=br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS")%><%=br.com.citframework.util.Constantes.getValue("CONTEXTO_APLICACAO")%>/calendario/lang/calendar-pt.js"></script>