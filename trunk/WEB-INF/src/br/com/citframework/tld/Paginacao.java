package br.com.citframework.tld;

import java.io.IOException;
import java.util.Collection;
import java.util.StringTokenizer;

import javax.servlet.jsp.JspException;

import org.apache.struts.taglib.logic.IterateTag;

import br.com.citframework.excecao.LogicException;
import br.com.citframework.util.Constantes;

public class Paginacao extends IterateTag {

	/**
	 * 
	 */
	private static final long serialVersionUID = 678136298587564530L;
	private String	form;
	private String	parametros;
	private String	onclick;
	private String	url;

	public int doEndTag() throws JspException {
		
		int result = super.doEndTag();

		try {
			int tamanho = getTamanho();

			if (tamanho > Integer.parseInt("0" + Constantes.getValue("LIMITE_CONSULTA"))) {
				setLength("0");
				setOffset("0");
				return result;

			}
			// String pth = renderizaURL();
			pageContext.getOut().print("<tr><td class='valor' colspan='6'>P&aacute;gina: ");
			int pagina = 1;
			//System.out.println("------Tamanho:" + tamanho);

			for (int i = 0; i < tamanho; i += Integer.parseInt(getLength())) {
				// pageContext.getOut().print("<a
				// href='"+pth+"&offset="+i+"'>"+pagina+"</a>|");
				pageContext.getOut().println("<a href='#' onclick=\"" + renderizaClick(i) + "\">" + pagina + "</a>|");

				pagina++;
			}

			pageContext.getOut().print("</td></tr>");
		} catch (IOException e) {

			throw new JspException(e);
		} catch (Exception e) {

			setLength("0");
			setOffset("0");
			return result;
		}
		return result;
	}

	public int doStartTag() throws JspException {

		try {
			if (getTamanho() > Integer.parseInt("0" + Constantes.getValue("LIMITE_CONSULTA"))) {
				setLength("0");
				setOffset("0");
				try {
					pageContext.getOut().print("<script>alert('" + Constantes.getValue("MT001") + "');</script>");
					return super.doStartTag();
				} catch (IOException e) {

					throw new JspException(e);
				}

			}

		} catch (Exception e) {

			try {
				e.printStackTrace();
				pageContext.getOut().print("<script>alert('" + e.getMessage() + "');</script>");
				return super.doStartTag();
			} catch (IOException e1) {

				throw new JspException(e1);
			}

		}

		if (pageContext.getRequest().getParameter("offset") == null || pageContext.getRequest().getParameter("offset").trim().length() == 0) {
			setOffset("0");
		} else {
			setOffset(pageContext.getRequest().getParameter("offset").trim());
		}

		
		return super.doStartTag();
	}

	public String getParametros() {
		return parametros;
	}

	public void setParametros(String parametros) {
		this.parametros = parametros;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String url) {
		this.form = url;
	}

	/*
	 * private String renderizaURL()throws LogicException{ String resultUrl =
	 * getForm();
	 * 
	 * String params ="";
	 * 
	 * if(getParametros().indexOf(";")==-1){
	 * if(pageContext.getRequest().getParameter(getParametros())!=null &&
	 * pageContext.getRequest().getParameter(getParametros()).trim().length()>0){
	 * 
	 * String tmp = pageContext.getRequest().getParameter(getParametros());
	 * if(tmp.indexOf("%")>-1){ tmp = tmp.replaceAll("%","|_|-|"); }
	 * params+=getParametros()+"="+tmp; } }else{ StringTokenizer tok = new
	 * StringTokenizer(getParametros(),";"); while(tok.hasMoreTokens()){ String
	 * tmp = tok.nextToken();
	 * if(pageContext.getRequest().getParameter(tmp)!=null &&
	 * pageContext.getRequest().getParameter(tmp).trim().length()>0){
	 * 
	 * String tmp2 = pageContext.getRequest().getParameter(getParametros());
	 * 
	 * if(tmp2.indexOf("%")>-1){ tmp2 = tmp2.replaceAll("%",Constantes.CORINGA); }
	 * 
	 * params+=getParametros()+"="+tmp; if(params.length()>0){ params+="&"; }
	 * params+=tmp+"="+tmp2; }
	 *  }
	 *  } if(params.length()>0) return resultUrl+"?"+params; else throw new
	 * LogicException(Constantes.MT001); }
	 */

	public int doAfterBody() throws JspException {
		
		int tamanho = 0;
		try {
			tamanho = getTamanho();
		} catch (Exception e) {

			tamanho = 0;
		}

		if (tamanho > Integer.parseInt("0" + Constantes.getValue("LIMITE_CONSULTA"))) {
			setLength("0");
			setOffset("0");
			return SKIP_BODY;

		}
		return super.doAfterBody();
	}

	private int getTamanho() throws Exception {

		renderizaClick(1);

		int tamanho = 0;

		Object obj = pageContext.getRequest().getAttribute(getName());
		if (obj == null) {
			obj = pageContext.getSession().getAttribute(getName());
		}

		Collection col = null;
		if (obj != null) {
			col = (Collection) obj;
			tamanho = col.size();
		}
		return tamanho;

	}

	private String renderizaClick(int offset) throws Exception {

		if (getUrl() != null && getUrl().trim().length() > 0) {
			if (getUrl().indexOf("?") == -1)
				return "window.location='" + getUrl() + "?offset=" + offset + "';";
			else
				return "window.location='" + getUrl() + "&offset=" + offset + "';";
		}

		//String resultUrl = getForm();

		String params = "";

		if (getParametros().indexOf(";") == -1) {
			if (pageContext.getRequest().getParameter(getParametros()) != null && pageContext.getRequest().getParameter(getParametros()).trim().length() > 0) {

				String tmp = pageContext.getRequest().getParameter(getParametros());
				/*
				 * if(tmp.indexOf("%")>-1){ tmp = tmp.replaceAll("%","|_|-|"); }
				 */
				params += "document." + getForm() + "." + getParametros() + ".value='" + tmp + "';";
			}
		} else {
			StringTokenizer tok = new StringTokenizer(getParametros(), ";");
			while (tok.hasMoreTokens()) {
				String tmp = tok.nextToken();
				if (pageContext.getRequest().getParameter(tmp) != null && pageContext.getRequest().getParameter(tmp).trim().length() > 0) {

					String tmp2 = pageContext.getRequest().getParameter(tmp);

					params += "document." + getForm() + "." + tmp + ".value='" + tmp2 + "';";
				}

			}

		}
		if (params.length() > 0) {
			if (getOnclick() == null || getOnclick().trim().length() == 0) {
				return params + "document." + getForm() + ".action = document." + getForm() + ".action+'?offset=" + offset + "';" + "document." + getForm() + ".submit();";
			} else {
				return params + "document." + getForm() + ".action = document." + getForm() + ".action+'?offset=" + offset + "';" + onclick + ";";
			}

		}

		else
			throw new LogicException(Constantes.getValue("MSG98"));
	}

	public String getOnclick() {
		return onclick;
	}

	public void setOnclick(String onclick) {
		this.onclick = onclick;
	}

	/**
	 * @return Returns the url.
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url
	 *            The url to set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}
}
