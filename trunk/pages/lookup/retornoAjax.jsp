<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="/WEB-INF/cit.tld" prefix="cit"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.Iterator"%>
<%@page import="br.com.citframework.util.Campo"%>
<%@page import="br.com.citframework.util.LookupFieldUtil"%>
<%@page import="br.com.citframework.util.UtilHTML"%>
<%@page import="br.com.centralit.citcorpore.util.UtilI18N"%>
<%@ taglib uri="/tags/i18n" prefix="i18n"%>
<%
	Collection col = (Collection) request.getAttribute("retorno");
	String nomeLookup = request.getParameter("nomeLookup");
	String checkbox = request.getParameter("checkbox");//verificar se a lookup é do tipo radio ou seja retorna um unico valor ou se é checkbox que retorna array com valores 
	Integer totalPag = 0; 
	Integer pagAtual = 1;
	Integer totalItens = 0;
	if(request.getSession(true).getAttribute("totalPag_" + nomeLookup) != null){
		totalPag = new Integer( request.getSession(true).getAttribute("totalPag_" + nomeLookup).toString() );	
		pagAtual = new Integer( request.getSession(true).getAttribute("pagAtualAux_" + nomeLookup).toString() );
		totalItens = new Integer( request.getSession(true).getAttribute("totalItens_" + nomeLookup).toString() );		
	}
	String nomeLookupExec = nomeLookup;
	String id = request.getParameter("id");
	if (nomeLookup == null)
		nomeLookup = "";
	if (id != null) {
		if (!id.equalsIgnoreCase("")) {
			nomeLookup = id;
		}
	}
%>
<style>
table {
  max-width: 100%;
  background-color: transparent;
  border-collapse: collapse;
  border-spacing: 0;
}
.table {
  width: 100%;
  margin-bottom: 20px;
}
.table th,
.table td {
  padding: 8px;
  line-height: 20px;
  text-align: left;
  vertical-align: top;
  border-top: 1px solid #dddddd;
}
.table th {
  font-weight: bold;
}
.table thead th {
  vertical-align: bottom;
}
.table caption + thead tr:first-child th,
.table caption + thead tr:first-child td,
.table colgroup + thead tr:first-child th,
.table colgroup + thead tr:first-child td,
.table thead:first-child tr:first-child th,
.table thead:first-child tr:first-child td {
  border-top: 0;
}
.table tbody + tbody {
  border-top: 2px solid #dddddd;
}
.table .table {
  background-color: #ffffff;
}
.table-condensed th,
.table-condensed td {
  padding: 4px 5px;
}
.table-bordered {
  border: 1px solid #dddddd;
  border-collapse: separate;
  *border-collapse: collapse;
  border-left: 0;
  -webkit-border-radius: 4px;
  -moz-border-radius: 4px;
  border-radius: 4px;
}
.table-bordered th,
.table-bordered td {
  border-left: 1px solid #dddddd;
}
.table-bordered caption + thead tr:first-child th,
.table-bordered caption + tbody tr:first-child th,
.table-bordered caption + tbody tr:first-child td,
.table-bordered colgroup + thead tr:first-child th,
.table-bordered colgroup + tbody tr:first-child th,
.table-bordered colgroup + tbody tr:first-child td,
.table-bordered thead:first-child tr:first-child th,
.table-bordered tbody:first-child tr:first-child th,
.table-bordered tbody:first-child tr:first-child td {
  border-top: 0;
}
.table-bordered thead:first-child tr:first-child > th:first-child,
.table-bordered tbody:first-child tr:first-child > td:first-child,
.table-bordered tbody:first-child tr:first-child > th:first-child {
  -webkit-border-top-left-radius: 4px;
  -moz-border-radius-topleft: 4px;
  border-top-left-radius: 4px;
}
.table-bordered thead:first-child tr:first-child > th:last-child,
.table-bordered tbody:first-child tr:first-child > td:last-child,
.table-bordered tbody:first-child tr:first-child > th:last-child {
  -webkit-border-top-right-radius: 4px;
  -moz-border-radius-topright: 4px;
  border-top-right-radius: 4px;
}
.table-bordered thead:last-child tr:last-child > th:first-child,
.table-bordered tbody:last-child tr:last-child > td:first-child,
.table-bordered tbody:last-child tr:last-child > th:first-child,
.table-bordered tfoot:last-child tr:last-child > td:first-child,
.table-bordered tfoot:last-child tr:last-child > th:first-child {
  -webkit-border-bottom-left-radius: 4px;
  -moz-border-radius-bottomleft: 4px;
  border-bottom-left-radius: 4px;
}
.table-bordered thead:last-child tr:last-child > th:last-child,
.table-bordered tbody:last-child tr:last-child > td:last-child,
.table-bordered tbody:last-child tr:last-child > th:last-child,
.table-bordered tfoot:last-child tr:last-child > td:last-child,
.table-bordered tfoot:last-child tr:last-child > th:last-child {
  -webkit-border-bottom-right-radius: 4px;
  -moz-border-radius-bottomright: 4px;
  border-bottom-right-radius: 4px;
}
.table-bordered tfoot + tbody:last-child tr:last-child td:first-child {
  -webkit-border-bottom-left-radius: 0;
  -moz-border-radius-bottomleft: 0;
  border-bottom-left-radius: 0;
}
.table-bordered tfoot + tbody:last-child tr:last-child td:last-child {
  -webkit-border-bottom-right-radius: 0;
  -moz-border-radius-bottomright: 0;
  border-bottom-right-radius: 0;
}
.table-bordered caption + thead tr:first-child th:first-child,
.table-bordered caption + tbody tr:first-child td:first-child,
.table-bordered colgroup + thead tr:first-child th:first-child,
.table-bordered colgroup + tbody tr:first-child td:first-child {
  -webkit-border-top-left-radius: 4px;
  -moz-border-radius-topleft: 4px;
  border-top-left-radius: 4px;
}
.table-bordered caption + thead tr:first-child th:last-child,
.table-bordered caption + tbody tr:first-child td:last-child,
.table-bordered colgroup + thead tr:first-child th:last-child,
.table-bordered colgroup + tbody tr:first-child td:last-child {
  -webkit-border-top-right-radius: 4px;
  -moz-border-radius-topright: 4px;
  border-top-right-radius: 4px;
}
.table-striped tbody > tr:nth-child(odd) > td,
.table-striped tbody > tr:nth-child(odd) > th {
  background-color: #f9f9f9;
}
.table-hover tbody tr:hover > td,
.table-hover tbody tr:hover > th {
  background-color: #f5f5f5;
}
table td[class*="span"],
table th[class*="span"],
.row-fluid table td[class*="span"],
.row-fluid table th[class*="span"] {
  display: table-cell;
  float: none;
  margin-left: 0;
}
.table td.span1,
.table th.span1 {
  float: none;
  width: 44px;
  margin-left: 0;
}
.table td.span2,
.table th.span2 {
  float: none;
  width: 119px;
  margin-left: 0;
}
.table td.span3,
.table th.span3 {
  float: none;
  width: 194px;
  margin-left: 0;
}
.table td.span4,
.table th.span4 {
  float: none;
  width: 269px;
  margin-left: 0;
}
.table td.span5,
.table th.span5 {
  float: none;
  width: 344px;
  margin-left: 0;
}
.table td.span6,
.table th.span6 {
  float: none;
  width: 419px;
  margin-left: 0;
}
.table td.span7,
.table th.span7 {
  float: none;
  width: 494px;
  margin-left: 0;
}
.table td.span8,
.table th.span8 {
  float: none;
  width: 569px;
  margin-left: 0;
}
.table td.span9,
.table th.span9 {
  float: none;
  width: 644px;
  margin-left: 0;
}
.table td.span10,
.table th.span10 {
  float: none;
  width: 719px;
  margin-left: 0;
}
.table td.span11,
.table th.span11 {
  float: none;
  width: 794px;
  margin-left: 0;
}
.table td.span12,
.table th.span12 {
  float: none;
  width: 869px;
  margin-left: 0;
}
.table tbody tr.success > td {
  background-color: #dff0d8;
}
.table tbody tr.error > td {
  background-color: #f2dede;
}
.table tbody tr.warning > td {
  background-color: #fcf8e3;
}
.table tbody tr.info > td {
  background-color: #d9edf7;
}
.table-hover tbody tr.success:hover > td {
  background-color: #d0e9c6;
}
.table-hover tbody tr.error:hover > td {
  background-color: #ebcccc;
}
.table-hover tbody tr.warning:hover > td {
  background-color: #faf2cc;
}
.table-hover tbody tr.info:hover > td {
  background-color: #c4e3f3;
}
.table {
  margin: 0;
  position: relative;
  -webkit-border-radius: 0 0 0 0;
  -moz-border-radius: 0 0 0 0;
  border-radius: 0 0 0 0;
}
.table.table-white {
  background: #fff;
}
.table th,
.table td {
  border-top-color: #ebebeb;
}
.table td.center,
.table th.center {
  text-align: center;
}
.table td.right,
.table th.right {
  text-align: right;
}
.table-condensed {
  font-size: 10pt;
}
.table-condensed th,
.table-condensed td {
  padding: 4px 10px;
}
.table-borderless th,
.table-borderless td {
  border: none;
}
.table-striped tbody tr:nth-child(odd) td,
.table-striped tbody tr:nth-child(odd) th {
  background-color: #fafafa;
}
.table-bordered {
  border-color: #d8d8d8;
  box-shadow: 0 1px 0 0 #f7f7f7, 0 5px 4px -4px #d8d8d8;
  -moz-box-shadow: 0 1px 0 0 #f7f7f7, 0 5px 4px -4px #d8d8d8;
  -webkit-box-shadow: 0 1px 0 0 #f7f7f7, 0 5px 4px -4px #d8d8d8;
}
.table-bordered th,
.table-bordered td {
  border-color: #d8d8d8;
}
.table-bordered thead:first-child tr:first-child > th:first-child,
.table-bordered tbody:first-child tr:first-child > td:first-child,
.table-bordered tbody:first-child tr:first-child > th:first-child,
.table-bordered thead:first-child tr:first-child > th:last-child,
.table-bordered tbody:first-child tr:first-child > td:last-child,
.table-bordered tbody:first-child tr:first-child > th:last-child,
.table-bordered thead:last-child tr:last-child > th:first-child,
.table-bordered tbody:last-child tr:last-child > td:first-child,
.table-bordered tbody:last-child tr:last-child > th:first-child,
.table-bordered tfoot:last-child tr:last-child > td:first-child,
.table-bordered tfoot:last-child tr:last-child > th:first-child {
  -webkit-border-radius: 0 0 0 0;
  -moz-border-radius: 0 0 0 0;
  border-radius: 0 0 0 0;
}
.table-fill td {
  background: #F8F8F8;
}
.table .progress:last-child,
.table .alert:last-child {
  margin: 0;
}
.table .shortRight {
  width: 25%;
  text-align: right;
  direction: rtl;
  text-indent: 10px;
}
.table-large-spacing td {
  padding: 20px 15px;
}
.table .thead td {
  padding: 8px;
  font-weight: bold;
}
.table-vertical-center td,
.table-vertical-center th {
  vertical-align: middle;
}
.table-thead-simple thead th {
  background: none;
  border-left: none;
  border-right: none;
  border-top: none;
  border-bottom: 1px solid #ebebeb;
  color: #7c7c7c;
  -webkit-border-radius: 0 0 0 0;
  -moz-border-radius: 0 0 0 0;
  border-radius: 0 0 0 0;
  text-shadow: none;
  text-transform: none;
  box-shadow: inset 1px 1px 1px rgba(255, 255, 255, 0), inset -1px -1px 1px rgba(0, 0, 0, 0);
  -moz-box-shadow: inset 1px 1px 1px rgba(255, 255, 255, 0), inset -1px -1px 1px rgba(0, 0, 0, 0);
  -webkit-box-shadow: inset 1px 1px 1px rgba(255, 255, 255, 0), inset -1px -1px 1px rgba(0, 0, 0, 0);
}
.table-thead-simple.table-thead-border-none {
  border-top: none;
  -webkit-border-radius: 0 0 0 0;
  -moz-border-radius: 0 0 0 0;
  border-radius: 0 0 0 0;
}
.table-thead-simple.table-thead-border-none thead th {
  border-bottom: none;
}
.table-thead-simple.table-thead-border-none thead:first-child tr:first-child > th:last-child,
.table-thead-simple.table-thead-border-none tbody:first-child tr:first-child > td:last-child,
.table-thead-simple.table-thead-border-none tbody:first-child tr:first-child > th:last-child {
  -webkit-border-radius: 0 0 0 0;
  -moz-border-radius: 0 0 0 0;
  border-radius: 0 0 0 0;
}
.table-primary {
  border-color: #d8d8d8;
  border-top: none;
}
.table-primary thead th {
  border-color: #8ec657;
  background-color: #8ec657;
  color: #fff;
  font-size: 14px;
}
.table-primary tbody td {
  color: #7c7c7c;
  background: #fafafa;
  border-width: 0px;
}
.table-primary tbody td.important {
  color: #8ec657;
  font-weight: 600;
}
.table-primary tbody td.actions {
  padding-right: 1px;
}
.table-primary.table-bordered tbody td {
  border-color: #d8d8d8;
  border-width: 1px;
  /*
			box-shadow: 0 0 0 1px #fff inset;
			-webkit-box-shadow: 0 0 0 1px #fff inset;
			-moz-box-shadow: 0 0 0 1px #fff inset;
			*/

}
.table-primary tbody tr:nth-child(odd) td,
.table-primary tbody tr:nth-child(odd) th {
  background: #ffffff;
}
.table-primary tbody tr.selectable td {
  cursor: pointer;
}
.table-primary tbody tr.selected td,
.table-primary tbody tr.selectable:hover td {
  background: #cbcbcb;
  box-shadow: 0 0 0 1px #a5a5a5 inset;
  -webkit-box-shadow: 0 0 0 1px #a5a5a5 inset;
  -moz-box-shadow: 0 0 0 1px #a5a5a5 inset;
}
</style>
<table id='topoRetorno' class='dynamicTable table table-striped table-bordered table-condensed dataTable' width="100%">
<%
		boolean b;
		Campo cp;
		LookupFieldUtil lookUpField = new LookupFieldUtil();
		Collection colCamposRet = lookUpField
				.getCamposRetorno(nomeLookupExec);
		String strSeparaCampos = lookUpField
				.getSeparaCampos(nomeLookupExec);
		if (colCamposRet != null) {
			Iterator itRet = colCamposRet.iterator();
			out.print("<tr>");
			b = false;
			while (itRet.hasNext()) {
				cp = (Campo) itRet.next();
				if (b) {
					if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())) {
						out.print("<td>");
						out.print("<b style='line-height: 25px;'>" + UtilHTML.encodeHTML(cp.getDescricao()) + "</b>");
						out.print("</td>");
					}
				} else {
					if (checkbox != null && checkbox.equalsIgnoreCase("true")){
						out.print("<td ><input  style='width: 20px !important; align: center;' type='checkbox' name='selTodosPagina' value='' onclick=\""+nomeLookup.toUpperCase()+"_marcarTodosCheckbox(this)\";></td>");
					}else {
						out.print("<td >&nbsp;</td>");
					}
				}
				b = true;
			}
			out.print("</tr>");
		}
%>
<%
	if (col != null) {
	Iterator it = col.iterator();
	Iterator it2;
	Collection colAux;
	if (!it.hasNext()) {
		out.print("<tr>");
		out.print("<td>&nbsp;</td><td>");
		out.print("<B><font color='RED'>"+UtilI18N.internacionaliza(request, "MSG04")+"</font></B>");
		out.print("</td>");		
		out.print("</tr>");
	}
	int i = 0;
	String cor = "";				
	while (it.hasNext()) {
			colAux = (Collection) it.next();
			it2 = colAux.iterator();
			if ((i % 2) == 0) {
				cor = "";
			} else {
				cor = "";
			}
			i++;
			String ret = "";
			b = false;

			while (it2.hasNext()) {

				cp = (Campo) it2.next();
				String strVal = "";
				if (cp.getObjValue() != null) {
					strVal = cp.getObjValue().toString();
				} else {
					cp.setObjValue("");
				}
				if (strVal.indexOf('\n') >= 0
						|| strVal.indexOf('\r') >= 0) {
					strVal = "";
				}
				if (!b) {
					b = true;
				} else {
					if (strSeparaCampos.equalsIgnoreCase("S")) {
						if (!ret.equalsIgnoreCase("")) {
							ret = ret + ", ";
						}
						ret = ret + "'" + strVal + "'";
					} else {
						if (!ret.equalsIgnoreCase("")) {
							ret = ret + " - ";
						}
						ret = ret + strVal;
					}
				}
			}
			if (!strSeparaCampos.equalsIgnoreCase("S")) {
				ret = "'" + ret + "'";
			}
			//
			it2 = colAux.iterator();
			out.print("<tr style='padding: 2px 0 3px 0; height: 20px;' class='"
					+ cor + "'>");
			b = false;
			if (checkbox == null || checkbox.equalsIgnoreCase("false")){
				while (it2.hasNext()) {
					cp = (Campo) it2.next();
					if (!b) {
						out.print("<td width='2' colspan='1'>");
						out.print("<input  style='width: 20px !important; align: center;' type='radio' name='sel' value='"+cp.getObjValue()+"'  onclick=\"setRetorno_"+nomeLookup.toUpperCase()+"('"+cp.getObjValue()+"',"+ret.replace("\\", "\\\\")+");\">");
						out.print("</td>");		
						b=true;
					} else {
						if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())) {
							out.print("<td >");
							if (cp.getObjValue().equals("")) {
								out.print("&nbsp;");
							} else {
								out.print(cp.getObjValue());
							}
							out.print("</td>");
						}
					}
				}
			}else {
				while (it2.hasNext()) {
					cp = (Campo) it2.next();
					if (!b) {
						out.print("<td width='2' colspan='1'>");
						out.print("<input  class='check' style='width: 20px !important; align: center;' type='checkbox' name='sel' value='"+cp.getObjValue()+"' onclick=concatenarValoresCheckados_"+nomeLookup+"(this);>");
						out.print("</td>");		
						b=true;
						} else {
						if (!"IGNORE".equalsIgnoreCase(cp.getDescricao())) {
							out.print("<td >");
							if (cp.getObjValue().equals("")) {
								out.print("&nbsp;");
							} else {
								out.print(cp.getObjValue());
							}
							out.print("</td>");
						}
					}
				}
			}
			out.print("</tr>");
		}
	} else {
		out.print("<tr >");
		out.print("<td  colspan='11'>");
		out.print("<B><font size='2' color='red'>"+UtilI18N.internacionaliza(request, "MSG04")+"</font></B>");
		out.print("</td>");
		out.print("</tr>");
	}
%>
</table>
<script>
$(function() {
	$(".botao").button();
});
</script>
<div id="pag" style="text-align: center; display: block; width: 100%; margin-bottom: 5px;">
		<input id="btfirst" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(0);" value='<i18n:message key="citcorpore.comum.primeiro" />' title='<i18n:message key="citcorpore.comum.primeiro" />' style=" cursor: pointer" name="btfirst">
		<input id="btprevius" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(-1);" value='<i18n:message key="citcorpore.comum.anterior" />' title='<i18n:message key="citcorpore.comum.anterior" />' style=" cursor: pointer" name="btprevius">
		<output><i18n:message key="citcorpore.comum.mostrandoPagina" /></output> <%=pagAtual%> <output><i18n:message key="citcorpore.comum.mostrandoPaginaDe" /></output> <%=totalPag%> 
		<input id="btnext" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(1);" value='<i18n:message key="citcorpore.comum.proximo" />' title='<i18n:message key="citcorpore.comum.proximo" />' style=" cursor: pointer" name="btnext">
		<input id="btlast" class="ui-widget ui-state-default botao corner-all" type="button" onclick="paginacao<%=nomeLookup%>(<%=totalItens%>);" value='<i18n:message key="citcorpore.comum.ultimo" />' title='<i18n:message key="citcorpore.comum.ultimo" />' style=" cursor: pointer" name="btlast">
</div>
