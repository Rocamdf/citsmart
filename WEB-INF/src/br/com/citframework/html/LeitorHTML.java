package br.com.citframework.html;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.html.parser.ParserDelegator;

public class LeitorHTML {
	public static void main(String args[]) throws Exception {
		StringBuffer strBuff = getTextoFromArquivoHTML("C:\\CentralIT\\Documentacao da Empresa\\Fabrica\\Documentacao de Projeto\\CASEMBRAPA\\CITSaude\\Integração\\Embrapa - CAT\\CatMpact\\CAT2004762330601.HTM", 
				true);
		
		Collection colBuff = getCollectionFromArquivoHTML("C:\\CentralIT\\Documentacao da Empresa\\Fabrica\\Documentacao de Projeto\\CASEMBRAPA\\CITSaude\\Integração\\Embrapa - CAT\\CatMpact\\CAT2004762330601.HTM", 
				true);
	}
	
	protected static HTMLParseLister process(String nomeArquivo, boolean debugParser)
			throws IOException{
		FileInputStream fis = new FileInputStream(nomeArquivo);
		InputStreamReader isr = new InputStreamReader(fis);
		BufferedReader br = new BufferedReader(isr);

		HTMLEditorKit.Parser parser;
		parser = new ParserDelegator();
		HTMLParseLister h = new HTMLParseLister(debugParser);
		parser.parse(br, h, true);
		br.close();
		return h;
	}
	/**
	 * Obtem o texto nao formatado de um arquivo HTML (sem as tags).
	 * @param nomeArquivo
	 * @param debugParser
	 * @return
	 * @throws IOException
	 */
	public static StringBuffer getTextoFromArquivoHTML(String nomeArquivo, boolean debugParser)
			throws IOException {
		HTMLParseLister h = process(nomeArquivo, debugParser);
		
		if (debugParser){
			System.out.println(">>>> LeitorHTML:: TEXTO EXTRAÍDO: "
					+ h.getStrBuffTextData().toString());
		}
		return h.getStrBuffTextData();
	}
	/**
	 * Obtem uma coleção de elementos string - nao formatado de um arquivo HTML (sem as tags).
	 * @param nomeArquivo
	 * @param debugParser
	 * @return
	 * @throws IOException
	 */	
	public static Collection getCollectionFromArquivoHTML(String nomeArquivo, boolean debugParser)
	throws IOException {
		HTMLParseLister h = process(nomeArquivo, debugParser);
		
		if (debugParser){
			if (h.getColBuffTextData() != null){
				int i = 0;
				for(Iterator it = h.getColBuffTextData().iterator(); it.hasNext();){
					String element = (String)it.next();
					System.out.println(">>>> LeitorHTML:: TEXTO EXTRAÍDO (" + i + "): " + element);				
					i++;
				}
			}
		}
		return h.getColBuffTextData();
	}
}

class HTMLParseLister extends HTMLEditorKit.ParserCallback {
	int indentSize = 0;
	boolean debug = false;
	StringBuffer strBuffTextData = null;
	Collection colBuffTextData = null;

	public HTMLParseLister() {
		debug = false;
	}

	public HTMLParseLister(boolean debugParm) {
		debug = debugParm;
	}

	protected void indent() {
		indentSize += 3;
	}

	protected void unIndent() {
		indentSize -= 3;
		if (indentSize < 0)
			indentSize = 0;
	}

	protected void pIndent() {
		if (debug) {
			for (int i = 0; i < indentSize; i++)
				System.out.print(" ");
		}
	}

	public void handleText(char[] data, int pos) {
		pIndent();
		// System.out.println("Text(" + data.length + " chars)");
		if (debug) {
			System.out.println(data);
		}
		if (strBuffTextData == null) {
			strBuffTextData = new StringBuffer();
		}
		if (colBuffTextData == null){
			colBuffTextData = new ArrayList();
		}
		strBuffTextData.append(data);
		strBuffTextData.append("\n");
		
		colBuffTextData.add("" + new String(data));
	}

	public void handleComment(char[] data, int pos) {
		pIndent();
		if (debug) {
			System.out.println("Comment(" + data.length + " chars)");
		}
	}

	public void handleStartTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		pIndent();
		if (debug) {
			System.out.println("Tag start(<" + t.toString() + ">, "
					+ a.getAttributeCount() + " attrs)");
		}
		indent();
	}

	public void handleEndTag(HTML.Tag t, int pos) {
		unIndent();
		pIndent();
		if (debug) {
			System.out.println("Tag end<" + t.toString() + ">");
		}
	}

	public void handleSimpleTag(HTML.Tag t, MutableAttributeSet a, int pos) {
		pIndent();
		if (debug) {
			System.out.println("Tag(<" + t.toString() + ">, "
					+ a.getAttributeCount() + " attrs)");
		}
	}

	public void handleError(String errorMsg, int pos) {
		if (debug) {
			System.out.println("Parsing error: " + errorMsg + " at " + pos);
		}
	}

	public StringBuffer getStrBuffTextData() {
		return strBuffTextData;
	}

	public void setStrBuffTextData(StringBuffer strBuffTextData) {
		this.strBuffTextData = strBuffTextData;
	}

	public Collection getColBuffTextData() {
		return colBuffTextData;
	}

	public void setColBuffTextData(Collection colBuffTextData) {
		this.colBuffTextData = colBuffTextData;
	}
}
