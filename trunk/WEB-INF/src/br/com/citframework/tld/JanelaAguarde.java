package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;


import br.com.centralit.citcorpore.util.UtilI18N;
import br.com.citframework.util.Constantes;

public class JanelaAguarde extends BodyTagSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6496792382410987252L;
	private String	id;
	private String style;
	private String title;
	
	public int doStartTag() throws JspException {
		try {
			String urlIframe = "../../include/vazio.jsp";
			
			pageContext.getOut().println("<script type=\"text/javascript\">\n");
			pageContext.getOut().println("var " + getId() + "_time_id = 0;");
			
			pageContext.getOut().println("function " + getId() + "_ajustaPosicao() {");
			pageContext.getOut().println("	document.getElementById('" + getId() + "').style.top = HTMLUtils.getYOffset() + 'px';");
			pageContext.getOut().println("}");
			
			pageContext.getOut().println("HTMLUtils.addEvent(window, 'scroll', " + getId() + "_ajustaPosicao, false);");
					
			pageContext.getOut().println("function " + getId() + "() { }");
			
			//Cria a funcao de Show
			pageContext.getOut().println("" + getId() + ".setTitle = function(titulo){\n");
			pageContext.getOut().println("	document.getElementById('" + getId() + "_titulo').innerHTML = '<b>' + titulo + '</b>';");
			pageContext.getOut().println("}\n\n");
			
			pageContext.getOut().println("" + getId() + ".show = function(){\n");
			pageContext.getOut().println("   " + getId() + "_Ajusta_JanelaAguarde();\n");
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='block';\n");
			pageContext.getOut().println("   document.getElementById('divBloqueiaTela_" + getId() + "').style.display='block';\n");
			
			pageContext.getOut().println("  F" + getId() + "_atualizaZIndexMaior('divBloqueiaTela_" + getId() + "');\n");
			
			pageContext.getOut().println("   var leftPos = (document.body.offsetWidth - document.getElementById('" + getId() + "').clientWidth) / 2;");
			pageContext.getOut().println("   var topPos  = (document.body.offsetHeight - document.getElementById('" + getId() + "').clientHeight) / 2;");

			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.left = leftPos + 'px';");
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.top = topPos + 'px';");
			
			pageContext.getOut().println("  F" + getId() + "_atualizaZIndexMaior('" + getId() + "');\n");
			
			pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.left = '0px';");
			pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.top = '0px';");
			pageContext.getOut().println("	document.getElementById('divBloqueiaTela_" + getId() + "').style.width = document.body.scrollWidth + 'px';");
			pageContext.getOut().println("	if (document.body.clientHeight + document.body.clientTop > document.body.scrollHeight){");
			pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = (document.body.clientHeight + document.body.clientTop) + 'px';");
			pageContext.getOut().println("	}else{");
			pageContext.getOut().println("		document.getElementById('divBloqueiaTela_" + getId() + "').style.height = (document.body.scrollHeight) + 'px';");
			pageContext.getOut().println("	}");
			
			//pageContext.getOut().println("	document.getElementById('divCorpoJanelaAguarde_" + getId() + "').innerHTML = '';");
			
			pageContext.getOut().println("	 if (" + getId() + "_time_id == 0){");
			pageContext.getOut().println("		" + getId() + "_time_id = setInterval(" + getId() + "_Tira_Foco, 20);");
			pageContext.getOut().println("	 }");
			
			pageContext.getOut().println("   " + getId() + "_ajustaPosicao();\n");
			
			pageContext.getOut().println("}\n\n");
			
			//Cria a funcao de Hide
			pageContext.getOut().println("" + getId() + ".hide = function(){\n");
			pageContext.getOut().println("   document.getElementById('" + getId() + "').style.display='none';\n");
			pageContext.getOut().println("   document.getElementById('divBloqueiaTela_" + getId() + "').style.display='none';\n");
			pageContext.getOut().println("	 if (" + getId() + "_time_id > 0){");
			pageContext.getOut().println("	 	clearInterval(" + getId() + "_time_id);");
			pageContext.getOut().println("		" + getId() + "_time_id = 0;");
			pageContext.getOut().println("	 }");
			pageContext.getOut().println("}\n\n");
			
			//Cria a funcao de Controle de Foco			
			pageContext.getOut().println("function " + getId() + "_Tira_Foco(){");
			pageContext.getOut().println("	 if (document.getElementById('" + getId() + "').style.display == 'none'){");
			pageContext.getOut().println("	 	if (" + getId() + "_time_id > 0){");
			pageContext.getOut().println("	 		clearInterval(" + getId() + "_time_id);");
			pageContext.getOut().println("	 	}");
			pageContext.getOut().println("   	document.getElementById('divBloqueiaTela_" + getId() + "').style.display='none';\n");
			pageContext.getOut().println("	 }else{");						
			//pageContext.getOut().println("		window.focus();");
			//pageContext.getOut().println("		document.getElementById('divCorpoJanelaAguarde_" + getId() + "').innerHTML = document.getElementById('divCorpoJanelaAguarde_" + getId() + "').innerHTML + '=';");
			//pageContext.getOut().println("		if (document.getElementById('divCorpoJanelaAguarde_" + getId() + "').offsetWidth > document.getElementById('" + getId() + "').offsetWidth){");
			//pageContext.getOut().println("			document.getElementById('divCorpoJanelaAguarde_" + getId() + "').innerHTML = '';");			
			//pageContext.getOut().println("		}");
			pageContext.getOut().println("	 }");
			pageContext.getOut().println("}");
				
			//Cria a funcao de Ajustar a Janela			
			pageContext.getOut().println("function " + getId() + "_Ajusta_JanelaAguarde(){\n");
			pageContext.getOut().println("   var dvInterna = document.getElementById('divIntJanelaAguarde_" + getId() + "');\n");			
			pageContext.getOut().println("   var dvCorpo = document.getElementById('divCorpoJanelaAguarde_" + getId() + "');\n");
			pageContext.getOut().println("   var dvLookup = document.getElementById('divJanelaAguarde_" + getId() + "');\n");
			pageContext.getOut().println("   var fraLookup = document.getElementById('fraJanelaAguarde_" + getId() + "');\n");
			//pageContext.getOut().println("   dvLookup.style.height = document.getElementById('" + getId() + "').style.height;\n");
			pageContext.getOut().println("   dvLookup.style.height = '100px';\n");
			//pageContext.getOut().println("   fraLookup.style.height = document.getElementById('" + getId() + "').style.height;\n");
			pageContext.getOut().println("   fraLookup.style.height = '100px';\n");
			pageContext.getOut().println("   dvLookup.style.width = document.getElementById('" + getId() + "').style.width;\n");
			pageContext.getOut().println("   fraLookup.style.width = document.getElementById('" + getId() + "').style.width;\n");
			//pageContext.getOut().println("   dvInterna.style.height = document.getElementById('" + getId() + "').style.height;\n");
			pageContext.getOut().println("   dvInterna.style.height = '135px';\n");
			pageContext.getOut().println("   dvInterna.style.width = document.getElementById('" + getId() + "').style.width;\n");
			
			pageContext.getOut().println("   dvInterna.style.top = '0px'");
			pageContext.getOut().println("   dvCorpo.style.top = '25px'");
			
			pageContext.getOut().println("}\n");			
			
			pageContext.getOut().println("</script>\n");
			
			pageContext.getOut().println("<div id='" + getId() + "' style='z-index:3001;" + getStyle() + "' class='dragme'>\n");
			pageContext.getOut().println("<div style='z-index:2;' id='divJanelaAguarde_" + getId() + "'><iframe id='fraJanelaAguarde_" + getId() + "' src='" + urlIframe + "'></iframe></div>\n");
			pageContext.getOut().println("<div style='position:absolute;background:white;border:1px solid black' id='divIntJanelaAguarde_" + getId() + "'>\n");
						
			pageContext.getOut().println("<table width='100%' bgcolor='#CCCCCC'>\n");
			pageContext.getOut().println("<tr>\n");
			pageContext.getOut().println("<td width='100%' id='" + getId() + "_titulo' style='text-align:center; backgroundcollor: #CCCCCC;'>\n");
			pageContext.getOut().println("<b>" + getTitle() + "</b>\n");
			pageContext.getOut().println("</td>\n");
			pageContext.getOut().println("</tr>\n");
			pageContext.getOut().println("</table>\n");
			
			pageContext.getOut().println("</div>");
			
			pageContext.getOut().println("<div id='divCorpoJanelaAguarde_" + getId() + "' style='position:absolute;width:100%'>");
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().println("&nbsp;<br/>");
			pageContext.getOut().println("<table width='100%'><tr><td style='text-align:center'>");
			HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
			pageContext.getOut().println("&nbsp;<img src='" + Constantes.getValue("SERVER_ADDRESS") + Constantes.getValue("CONTEXTO_APLICACAO") + "/novoLayout/common/include/imagens/ajax-loader.gif' border='0'> &nbsp;" + UtilI18N.internacionaliza(request, "citcorpore.comum.aguarde"));
			pageContext.getOut().println("</td></tr></table>");
			pageContext.getOut().println("</div>\n");
			pageContext.getOut().println("</div>\n");
			pageContext.getOut().println("</div>\n");
			
			
			pageContext.getOut().println("<script>");
			pageContext.getOut().println("F" + getId() + "_atualizaZIndexMaior = function(idDiv){");
			pageContext.getOut().println("	var divs = document.getElementsByTagName('div');");
			pageContext.getOut().println("	var maiorZIndex = 0;");
			pageContext.getOut().println("	if (divs == null || divs == undefined) return;");
			pageContext.getOut().println("	for(var i = 0; i < divs.length; i++){");
			pageContext.getOut().println("		if (divs[i].style.display != 'none'){");
			pageContext.getOut().println("			if (divs[i].style.zIndex != null && divs[i].style.zIndex != undefined){");
			pageContext.getOut().println("				if (maiorZIndex < divs[i].style.zIndex){");
			pageContext.getOut().println("					maiorZIndex = divs[i].style.zIndex;");
			pageContext.getOut().println("				}");
			pageContext.getOut().println("			}");
			pageContext.getOut().println("		}");
			pageContext.getOut().println("	}");
			pageContext.getOut().println("	document.getElementById(idDiv).style.zIndex = maiorZIndex + 10;");
			pageContext.getOut().println("};");
			pageContext.getOut().println("</script>");			
			
			//Gera a DIV de Bloqueio da Tela.
			pageContext.getOut().println("<div id='divBloqueiaTela_" + getId() + "' style='z-index:3000;position:absolute; CURSOR: wait; BACKGROUND-COLOR:gray; filter:alpha(opacity=20);-moz-opacity:.25;opacity:.25;'>");
			pageContext.getOut().println("</div>");
			
		} catch (IOException e) {
			throw new JspException(e);
		}
		return super.doEndTag();
	}

	public int doAfterBody() throws JspException {
		return super.doAfterBody();
	}	
	
	public BodyContent getBodyContent() {
		BodyContent b = super.getBodyContent();
		return b;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
