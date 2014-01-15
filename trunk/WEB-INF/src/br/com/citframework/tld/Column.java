package br.com.citframework.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

public class Column extends BodyTagSupport {
	private String idGrid;
	private String number;
	public int doStartTag() throws JspException {
		try {
			pageContext.getOut().println("<div id='" + this.getIdGrid() + "_Coluna_" + this.getNumber() + "' class='CLASS_CONTROL_COLUMN_TAG' style='display:none'>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return EVAL_BODY_INCLUDE;
	}
	public int doEndTag() throws JspException {
		try {
			pageContext.getOut().println("</div>\n");
		} catch (IOException e) {
			e.printStackTrace();
		}		
		return super.doEndTag();
	}
	
	public String getIdGrid() {
		return idGrid;
	}
	public void setIdGrid(String idGrid) {
		this.idGrid = idGrid;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
}
