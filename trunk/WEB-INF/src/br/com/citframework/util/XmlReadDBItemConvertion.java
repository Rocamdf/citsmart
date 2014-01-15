package br.com.citframework.util;
 
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class XmlReadDBItemConvertion {

	private static final String ERROR_BUILDER_PARSE = "Erro ao construir documento XML a partir do arquivo de CONVERSOES DE BANCO DE DADOS!";
	private static XmlReadDBItemConvertion xmlReaderItemConvertion = null;
	private static final Logger LOGGER = Logger.getLogger(XmlReadDBItemConvertion.class);
	private Document doc = null;
	private static Collection colItens = null;

	public static XmlReadDBItemConvertion getInstance(String dbName) {
		if (xmlReaderItemConvertion == null){
			String strNameDb = dbName.toLowerCase();
			xmlReaderItemConvertion = new XmlReadDBItemConvertion((XmlReadDBItemConvertion.class.getResourceAsStream("/" + strNameDb + "-convert.xml")));
		}
		return xmlReaderItemConvertion;
	}

	public XmlReadDBItemConvertion(InputStream ioos) {
		if (ioos == null){
			System.out.println("XmlReadDBItemConvertion :: InputStream ioos IS NULL!!!");
			return;
		}
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(ioos);
		} catch (Exception e) {
			LOGGER.error(ERROR_BUILDER_PARSE, e);
		}
	}

	public XmlReadDBItemConvertion(String file) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(file));
		} catch (Exception e) {
			LOGGER.error(ERROR_BUILDER_PARSE, e);
		}
	}

	public Collection getItens() {
		if (colItens != null){
			return colItens;
		}
		colItens = new ArrayList();
		
		if (doc == null){
			return null;
		}
		
		Node noRoot = doc.getChildNodes().item(0);
		String nameToBeConverted = StringUtils.EMPTY;
		String nameAfterConversion = StringUtils.EMPTY;
		for (int j = 0; j < noRoot.getChildNodes().getLength(); j++) {
			Node noLookup = noRoot.getChildNodes().item(j);
			if (noLookup.getNodeName().equals("#text") || noLookup.getNodeName().equals("#comment"))
				continue;
			NamedNodeMap map = noLookup.getAttributes();
			nameToBeConverted = map.getNamedItem("nameToBeConverted").getNodeValue();
			nameAfterConversion = map.getNamedItem("nameAfterConversion").getNodeValue();
			
			DBItemConvertion dbItemConvertion = new DBItemConvertion();
			dbItemConvertion.setNameToBeConverted(nameToBeConverted);
			dbItemConvertion.setNameAfterConversion(nameAfterConversion);
			
			if (nameAfterConversion != null && !nameAfterConversion.equalsIgnoreCase("")){
				colItens.add(dbItemConvertion);
			}
		}
		return colItens;
	}
}