package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import br.com.citframework.util.UtilStrings;

public class UtilImportData {
	public static List readXMLFile(String pathFile){
		Document doc = null;
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(pathFile));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }	
        Node node = null;
        Node nodeTable = null;
        NodeList nodes = null;
        if (doc != null){
        	nodes = doc.getChildNodes();
        }
        List lstRecords = new ArrayList();
        if (nodes != null){
        	for (int i = 0; i < nodes.getLength(); i++){
        		nodeTable = nodes.item(i);
        		if (nodeTable.getNodeName().equalsIgnoreCase("tables")){
	        		NamedNodeMap mapX = nodeTable.getAttributes();
	        		String origem = mapX.getNamedItem("origem").getNodeValue();        			
        			for(int x = 0; x < nodeTable.getChildNodes().getLength(); x++){
        				node = nodeTable.getChildNodes().item(x);
		        		if (node.getNodeName().equalsIgnoreCase("table")){
			        		NamedNodeMap map = node.getAttributes();
			        		String tableName = map.getNamedItem("name").getNodeValue();
			        		if (node.getChildNodes() != null){
				        		for(int j = 0; j < node.getChildNodes().getLength(); j++){
				        			Node nodeRecord = node.getChildNodes().item(j);
		        		            if (nodeRecord.getNodeName().equalsIgnoreCase("command") || nodeRecord.getNodeName().equalsIgnoreCase("commandDelete")){
		        		            	continue;
		        		            }		        			
				        			if (nodeRecord.getChildNodes() != null && nodeRecord.getChildNodes().getLength() > 0){
				        				ImportInfoRecord importInfoRecord = new ImportInfoRecord();
				        				importInfoRecord.setTableName(tableName);
				        				Collection colFields = new ArrayList();
				        				for(int z = 0; z < nodeRecord.getChildNodes().getLength(); z++){
				        					Node nodeField = nodeRecord.getChildNodes().item(z);
				        		            if (nodeField == null || nodeField.getNodeName() == null){
				        		            	continue;
				        		            }
				        		            if(nodeField.getNodeName().equals("#text")) continue;	
				        		            if (nodeField.getNodeName().equalsIgnoreCase("command") || nodeField.getNodeName().equalsIgnoreCase("commandDelete")){
				        		            	continue;
				        		            }
				        	        		NamedNodeMap mapField = nodeField.getAttributes();
				        	        		String key = mapField.getNamedItem("key").getNodeValue();
				        	        		String sequence = mapField.getNamedItem("sequence").getNodeValue();
				        	        		String type = mapField.getNamedItem("type").getNodeValue();
				        	        		String nameField = mapField.getNamedItem("name").getNodeValue();
				        					ImportInfoField importInfoField = new ImportInfoField();
				        					importInfoField.setNameField(nameField);
				        					importInfoField.setValueField(nodeField.getTextContent());
				        					if (UtilStrings.nullToVazio(key).equalsIgnoreCase("y")){
				        						importInfoField.setKey(true);
				        					}else{
				        						importInfoField.setKey(false);
				        					}
				        					if (UtilStrings.nullToVazio(sequence).equalsIgnoreCase("y")){
				        						importInfoField.setSequence(true);
				        					}else{
				        						importInfoField.setSequence(false);
				        					}				        					
				        					importInfoField.setType(type);
				        					colFields.add(importInfoField);
				        				}
				        				importInfoRecord.setColFields(colFields);
				        				importInfoRecord.setOrigem(origem);
				        				lstRecords.add(importInfoRecord);
				        			}
				        		}
			        		}
		        		}
        			}
        		}
        	}
        }
        return lstRecords;
	}
}
