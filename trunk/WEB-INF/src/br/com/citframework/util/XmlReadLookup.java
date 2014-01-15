package br.com.citframework.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import br.com.citframework.dto.ItemValorDescricaoDTO;

public class XmlReadLookup {
    private static XmlReadLookup xmlReaderLookup = null;
    private Document doc = null;
    private HttpServletRequest request;
    private HashMap mapElementos = null;
    private static Properties props = null;
	private static String fileName = "";
    
    public static XmlReadLookup getInstance(){
    	if (xmlReaderLookup == null){
    		xmlReaderLookup = new XmlReadLookup((XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG"))));
    	}
    	return xmlReaderLookup;
    }
    
    public static XmlReadLookup getInstance(Locale locale){
    	xmlReaderLookup = new XmlReadLookup(locale);
    	return xmlReaderLookup;
    }
    
    public XmlReadLookup(InputStream ioos){
    	mapElementos = new HashMap();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(ioos);
            carregaLookups();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    public XmlReadLookup(Locale locale){
    	mapElementos = new HashMap();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse((XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG"))));
            carregaLookups(locale);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }    
    
    
    public XmlReadLookup(String file){
    	mapElementos = new HashMap();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(new File(file));
            carregaLookups();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public LookupInfo getLookup(String nome){
    	if (mapElementos != null){
	    	if (mapElementos.containsKey(nome)){
	    		LookupInfo lookupInfoAux = (LookupInfo)mapElementos.get(nome);
	    		if (lookupInfoAux != null){
	    			return lookupInfoAux;
	    		}
	    	}
    	}else{
    		mapElementos = new HashMap();
    	}
    	mapElementos = new HashMap();
    	LookupInfo lookupInfo = new LookupInfo();
        Node noRoot = null;
        try{
        	noRoot = doc.getChildNodes().item(0);
        }catch (Exception e) {
        	xmlReaderLookup = new XmlReadLookup((XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG"))));
        	try{
        		noRoot = doc.getChildNodes().item(0);
        	}catch (Exception e2) {
        		e2.printStackTrace();
			}
		}
        String nomeAux = "";
        String tabelaAux = "";
        String daoAux = "";
        String whereAux = "";
        String scriptRefAux = "";
        String scriptAux = "";
        String separaCamposAux = "";
        boolean tudoEmBranco = true;
        try{
        	if(noRoot != null){
		        for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
		            Node noLookup = noRoot.getChildNodes().item(j);
		            if (noLookup == null || noLookup.getNodeName() == null){
		            	continue;
		            }
		            if(noLookup.getNodeName().equals("#text")) continue;
		            tudoEmBranco = false;
		            
		            NamedNodeMap map = noLookup.getAttributes();
		            nomeAux = map.getNamedItem("name").getNodeValue();
		            tabelaAux = map.getNamedItem("tabela").getNodeValue();
		            daoAux = map.getNamedItem("daoProcessor").getNodeValue();
		            whereAux = map.getNamedItem("where").getNodeValue();
		            if (map.getNamedItem("separaCampos") == null){
		            	separaCamposAux = "N";
		            }else{
		            	separaCamposAux = map.getNamedItem("separaCampos").getNodeValue();
		            }
		            scriptRefAux = "";
		            if (map.getNamedItem("scriptRef") != null){
		            	scriptRefAux = map.getNamedItem("scriptRef").getNodeValue();
		            }
		            scriptAux = "";
		            if (map.getNamedItem("script") != null){
		            	scriptAux = map.getNamedItem("script").getNodeValue();
		            }            
		            if (nomeAux == null) nomeAux = "";
		            if (tabelaAux == null) tabelaAux = "";
		            if (daoAux == null) daoAux = "";
		            if (whereAux == null) whereAux = "";
		            if (nome.equalsIgnoreCase(nomeAux)){
		                lookupInfo.setNome(nomeAux);
		                lookupInfo.setTabela(tabelaAux);
		                lookupInfo.setDaoProcessor(daoAux);
		                lookupInfo.setWhere(whereAux);
		                lookupInfo.setScriptRef(scriptRefAux);
		                lookupInfo.setScript(scriptAux);
		                lookupInfo.setSeparaCampos(separaCamposAux);
		            	for (int i = 0; i < noLookup.getChildNodes().getLength(); i++){
		            		Node noLookupItem = noLookup.getChildNodes().item(i);
		            		if(noLookupItem.getNodeName().equals("#text")) continue;
		            		if (noLookupItem.getNodeName().equalsIgnoreCase("camposPesquisa")){
		            			lookupInfo.setColCamposPesquisa(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposRetorno")){
		            			lookupInfo.setColCamposRetorno(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposOrdenacao")){
		            			lookupInfo.setColCamposOrdenacao(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposChave")){
		            			lookupInfo.setColCamposChave(getCampos(noLookupItem));
		            		}
		            	}
		            	mapElementos.put(nome, lookupInfo);
		            }
		        }
        	}
        }catch (Exception e) {
        	tudoEmBranco = true;
        	System.out.println(">>>> CITSMART -> PROBLEMAS NO PROCESSAMENTO DO XML DO LOOKUP!!!!");
			e.printStackTrace();
		}
        if (tudoEmBranco){
        	xmlReaderLookup = new XmlReadLookup((XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG"))));
        }
        return lookupInfo;
    }
    
    public synchronized void carregaLookups(){
    	getProperties(null);
        Node noRoot = null;
        try{
        	noRoot = doc.getChildNodes().item(0);
        }catch (Exception e) {
        	xmlReaderLookup = new XmlReadLookup((XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG"))));
        	try{
        		noRoot = doc.getChildNodes().item(0);
        	}catch (Exception e2) {
        		e2.printStackTrace();
			}
		}
        String nomeAux = "";
        String tabelaAux = "";
        String daoAux = "";
        String whereAux = "";
        String scriptRefAux = "";
        String scriptAux = "";
        String separaCamposAux = "";
        try{
        	if(noRoot != null){
		        for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
		        	try{
			        	LookupInfo lookupInfo = new LookupInfo();
			            Node noLookup = noRoot.getChildNodes().item(j);
			            if (noLookup == null || noLookup.getNodeName() == null){
			            	continue;
			            }
			            if(noLookup.getNodeName().equals("#text")) continue;
			            
			            NamedNodeMap map = noLookup.getAttributes();
			            nomeAux = map.getNamedItem("name").getNodeValue();
			            tabelaAux = map.getNamedItem("tabela").getNodeValue();
			            daoAux = map.getNamedItem("daoProcessor").getNodeValue();
			            whereAux = map.getNamedItem("where").getNodeValue();
			            if (map.getNamedItem("separaCampos") == null){
			            	separaCamposAux = "N";
			            }else{
			            	separaCamposAux = map.getNamedItem("separaCampos").getNodeValue();
			            }
			            scriptRefAux = "";
			            if (map.getNamedItem("scriptRef") != null){
			            	scriptRefAux = map.getNamedItem("scriptRef").getNodeValue();
			            }
			            scriptAux = "";
			            if (map.getNamedItem("script") != null){
			            	scriptAux = map.getNamedItem("script").getNodeValue();
			            }            
			            if (nomeAux == null) nomeAux = "";
			            if (tabelaAux == null) tabelaAux = "";
			            if (daoAux == null) daoAux = "";
			            if (whereAux == null) whereAux = "";
			            
		                lookupInfo.setNome(nomeAux);
		                lookupInfo.setTabela(tabelaAux);
		                lookupInfo.setDaoProcessor(daoAux);
		                lookupInfo.setWhere(whereAux);
		                lookupInfo.setScriptRef(scriptRefAux);
		                lookupInfo.setScript(scriptAux);
		                lookupInfo.setSeparaCampos(separaCamposAux);
		            	for (int i = 0; i < noLookup.getChildNodes().getLength(); i++){
		            		Node noLookupItem = noLookup.getChildNodes().item(i);
		            		if(noLookupItem.getNodeName().equals("#text")) continue;
		            		if (noLookupItem.getNodeName().equalsIgnoreCase("camposPesquisa")){
		            			lookupInfo.setColCamposPesquisa(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposRetorno")){
		            			lookupInfo.setColCamposRetorno(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposOrdenacao")){
		            			lookupInfo.setColCamposOrdenacao(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposChave")){
		            			lookupInfo.setColCamposChave(getCampos(noLookupItem));
		            		}
		            	}
		            	System.out.println(">>>> CITSMART -> CARREGANDO LOOKUP: " + nomeAux + " em memoria!");
		            	mapElementos.put(nomeAux, lookupInfo);
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }
        	}
        }catch (Exception e) {
        	System.out.println(">>>> CITSMART -> PROBLEMAS NO PROCESSAMENTO DO XML DO LOOKUP!!!!");
			e.printStackTrace();
		}
    }
    
    public synchronized void carregaLookups(Locale locale){
    	getProperties(locale);
        Node noRoot = null;
        try{
        	noRoot = doc.getChildNodes().item(0);
        }catch (Exception e) {
        	xmlReaderLookup = new XmlReadLookup((XmlReadLookup.class.getResourceAsStream(Constantes.getValue("LOOKUP_FILE_CFG"))));
        	try{
        		noRoot = doc.getChildNodes().item(0);
        	}catch (Exception e2) {
        		e2.printStackTrace();
			}
		}
        String nomeAux = "";
        String tabelaAux = "";
        String daoAux = "";
        String whereAux = "";
        String scriptRefAux = "";
        String scriptAux = "";
        String separaCamposAux = "";
        try{
        	if(noRoot != null){
		        for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
		        	try{
			        	LookupInfo lookupInfo = new LookupInfo();
			            Node noLookup = noRoot.getChildNodes().item(j);
			            if (noLookup == null || noLookup.getNodeName() == null){
			            	continue;
			            }
			            if(noLookup.getNodeName().equals("#text")) continue;
			            
			            NamedNodeMap map = noLookup.getAttributes();
			            nomeAux = map.getNamedItem("name").getNodeValue();
			            tabelaAux = map.getNamedItem("tabela").getNodeValue();
			            daoAux = map.getNamedItem("daoProcessor").getNodeValue();
			            whereAux = map.getNamedItem("where").getNodeValue();
			            if (map.getNamedItem("separaCampos") == null){
			            	separaCamposAux = "N";
			            }else{
			            	separaCamposAux = map.getNamedItem("separaCampos").getNodeValue();
			            }
			            scriptRefAux = "";
			            if (map.getNamedItem("scriptRef") != null){
			            	scriptRefAux = map.getNamedItem("scriptRef").getNodeValue();
			            }
			            scriptAux = "";
			            if (map.getNamedItem("script") != null){
			            	scriptAux = map.getNamedItem("script").getNodeValue();
			            }            
			            if (nomeAux == null) nomeAux = "";
			            if (tabelaAux == null) tabelaAux = "";
			            if (daoAux == null) daoAux = "";
			            if (whereAux == null) whereAux = "";
			            
		                lookupInfo.setNome(nomeAux);
		                lookupInfo.setTabela(tabelaAux);
		                lookupInfo.setDaoProcessor(daoAux);
		                lookupInfo.setWhere(whereAux);
		                lookupInfo.setScriptRef(scriptRefAux);
		                lookupInfo.setScript(scriptAux);
		                lookupInfo.setSeparaCampos(separaCamposAux);
		            	for (int i = 0; i < noLookup.getChildNodes().getLength(); i++){
		            		Node noLookupItem = noLookup.getChildNodes().item(i);
		            		if(noLookupItem.getNodeName().equals("#text")) continue;
		            		if (noLookupItem.getNodeName().equalsIgnoreCase("camposPesquisa")){
		            			lookupInfo.setColCamposPesquisa(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposRetorno")){
		            			lookupInfo.setColCamposRetorno(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposOrdenacao")){
		            			lookupInfo.setColCamposOrdenacao(getCampos(noLookupItem));
		            		}else if (noLookupItem.getNodeName().equalsIgnoreCase("camposChave")){
		            			lookupInfo.setColCamposChave(getCampos(noLookupItem));
		            		}
		            	}
		            	System.out.println(">>>> CITSMART -> CARREGANDO LOOKUP: " + nomeAux + " em memoria!");
		            	mapElementos.put(nomeAux, lookupInfo);
		        	}catch(Exception e){
		        		e.printStackTrace();
		        	}
		        }
        	}
        }catch (Exception e) {
        	System.out.println(">>>> CITSMART -> PROBLEMAS NO PROCESSAMENTO DO XML DO LOOKUP!!!!");
			e.printStackTrace();
		}
    }
    
    private Collection getCampos(Node noLookupItem){
    	Collection colRetorno = new ArrayList();
    	for (int i = 0; i < noLookupItem.getChildNodes().getLength(); i++){
    		Node noCampo = noLookupItem.getChildNodes().item(i);
    		if(noCampo.getNodeName().equals("#text")) continue;
    		Campo campo = getCampo(noCampo);
    		colRetorno.add(campo);
    	}
    	return colRetorno;
    }
    private Campo getCampo(Node noCampo){
    	NamedNodeMap map = noCampo.getAttributes();
    	try{
	    	Campo campo = new Campo();
	    	campo.setNomeFisico(map.getNamedItem("nome").getNodeValue());
	    	
	    	/*Faz a leitura da chave*/
	    	if(props.containsKey(map.getNamedItem("descricao").getNodeValue()))
	    		campo.setDescricao(props.getProperty(map.getNamedItem("descricao").getNodeValue()));
	    	else
	    		campo.setDescricao(map.getNamedItem("descricao").getNodeValue());
	    	
	    	campo.setType(map.getNamedItem("tipo").getNodeValue());
	    	Node somenteBuscaNamedItem = map.getNamedItem("somenteBusca");
	    	if (somenteBuscaNamedItem != null) {
				String somenteBusca = somenteBuscaNamedItem.getNodeValue();
		    	campo.setSomenteBusca("true".equalsIgnoreCase(somenteBusca));
	    	}
	    	Node values = map.getNamedItem("values");
	    	Collection colValores = null;
	    	if (values != null) {
	    		String valuesStr = values.getNodeValue();
	    		if (valuesStr != null){
	    			String[] str = valuesStr.split(";");
	    			if (str != null){
	    				for(int x = 0; x < str.length; x++){
	    					String aux = str[x];
	    					if (aux != null){
	    						aux = aux.replaceAll("\\{", "");
	    						aux = aux.replaceAll("\\}", "");
	    						String[] str2 = aux.split("=");
	    						if (str2 != null && str2.length > 1){
	    							ItemValorDescricaoDTO item = new ItemValorDescricaoDTO();
	    							item.setValor(str2[0]);
	    							item.setDescricao(str2[1]);
	    							if (colValores == null){
	    								colValores = new ArrayList();
	    							}
	    							colValores.add(item);
	    						}
	    					}
	    				}
	    			}
	    		}
	    	}
	    	campo.setColValores(colValores);
	    	
	    	String auxStr = map.getNamedItem("tamanho").getNodeValue();
	    	if (auxStr == null) auxStr = "";
	    	int tam = Integer.parseInt("0" + auxStr);
	    	campo.setTamanho(tam);
	    	//
	    	auxStr = map.getNamedItem("obrigatorio").getNodeValue();
	    	if (auxStr == null) auxStr = "N";
	    	if (auxStr.equalsIgnoreCase("")) auxStr = "N";
	    	if (auxStr.substring(0,1).equalsIgnoreCase("S")){
	    		campo.setObrigatorio(true);
	    	}else{
	    		campo.setObrigatorio(false);
	    	}
	    	auxStr = "";
	    	if (map.getNamedItem("scriptLostFocus") != null){
	    		auxStr = map.getNamedItem("scriptLostFocus").getNodeValue();
	    	}
	    	campo.setScriptLostFocus(auxStr);
	    	auxStr = null;
	    	if (map.getNamedItem("mesmalinha") != null){
	    		auxStr = map.getNamedItem("mesmalinha").getNodeValue();
	    	}
	   		campo.setMesmalinha(auxStr);
	    	/*
	    	Node noScript;
	    	for (int i = 0; i < noCampo.getChildNodes().getLength(); i++){
	    		noScript = noCampo.getChildNodes().item(i);
	    		if(noScript.getNodeName().equals("#text")) continue;  
	    		System.out.println("------> Nome do No: " + noScript.getNodeName());
	    		System.out.println("------> To string do No: " + noScript.toString());
	    		System.out.println("------> To string do No: " + noScript.getNodeValue());
	    		System.out.println("------> To string Owner document: " + noScript.getOwnerDocument().toString());
	    		System.out.println("------> To string Owner document: " + noScript.getOwnerDocument().getNodeValue());
	    	}
	    	*/
	    	//
	    	return campo;
    	}catch (Exception e) {
    		e.printStackTrace();
    		return null;
		}
    }
    
    public static void main(String[] args){
    	XmlReadLookup xmlread = new XmlReadLookup("E:\\citsaude-bb\\ProjetoEjb\\lookup.xml");
    	xmlread.getLookup("LOOKUP_PESSOAFISICA");
    }

	/**
	 * @return the request
	 */
	public HttpServletRequest getRequest() {
		return request;
	}

	/**
	 * @param request the request to set
	 */
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private Properties getProperties(Locale locale) {
		try {
			if (locale != null && !locale.toString().equals("")) 
				fileName = "Mensagens_" + locale.toString() + ".properties";
			 else 
				fileName = "Mensagens.properties";

			props = new Properties();
			ClassLoader load = Mensagens.class.getClassLoader();
			InputStream is = load.getResourceAsStream(fileName);
			if (is == null) 
				is = ClassLoader.getSystemResourceAsStream(fileName);
			if (is == null) 
				is = ClassLoader.getSystemClassLoader().getResourceAsStream(fileName);
			
			try {
				if (is != null) {
					props.load(is);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SecurityException e1) {
			e1.printStackTrace();
		}
		return props;
	}
}