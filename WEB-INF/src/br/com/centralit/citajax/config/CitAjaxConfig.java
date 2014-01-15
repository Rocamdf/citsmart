package br.com.centralit.citajax.config;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.centralit.citajax.util.CitAjaxUtil;
import br.com.citframework.util.Constantes;

public class CitAjaxConfig {
	private static final Logger LOGGER = Logger.getLogger(CitAjaxConfig.class);
	private static CitAjaxConfig	singleton;
	private Document doc = null;
	
	public static InputStream inputStreamSettedInLoad = null;
	
	private Collection redirectItens;
	
	public static CitAjaxConfig getInstance() throws Exception {
		if (singleton == null) {
			String fileNameConfig = "citAjax.xml";
			if (Constantes.getValue("CITAJAX_CONFIG") != null && !Constantes.getValue("CITAJAX_CONFIG").trim().equalsIgnoreCase("")){
				fileNameConfig = Constantes.getValue("CITAJAX_CONFIG");
			}
			InputStream citAjaxConfigFile = null; //CitAjaxConfig.class.getClassLoader().getResourceAsStream(fileNameConfig); 
			
			if (citAjaxConfigFile == null){
				System.out.println("CITAJAX -> Configuração de CitAjaxUtil.caminho_real_app: " + CitAjaxUtil.caminho_real_app);
				try{
					citAjaxConfigFile = new FileInputStream(CitAjaxUtil.caminho_real_app + "/WEB-INF/citAjax.xml");  
				}catch (Exception e) {
				}
				if (citAjaxConfigFile != null){
					System.out.println("CITAJAX -> Carregado 1.o Passo!");
				}
			}
			if (citAjaxConfigFile == null){
				citAjaxConfigFile = CitAjaxConfig.class.getClassLoader().getResourceAsStream(fileNameConfig); 
				if (citAjaxConfigFile != null){
					System.out.println("CITAJAX -> Carregado 2.o Passo!");
				}				
			}
			if (citAjaxConfigFile == null){
				citAjaxConfigFile = ClassLoader.getSystemResourceAsStream(fileNameConfig);
				if (citAjaxConfigFile == null){
					citAjaxConfigFile = ClassLoader.getSystemClassLoader().getResourceAsStream(fileNameConfig); 
					if (citAjaxConfigFile == null){
						try{
							citAjaxConfigFile = new FileInputStream(Constantes.getValue("CAMINHO_CITAJAX_CONFIG") + fileNameConfig);
						}catch (Exception e) {
							e.printStackTrace();
							LOGGER.error(e);
							//Se der errado, tenta por ultimo pegar informacoes do carregamento do sistema, se houver.
							citAjaxConfigFile = inputStreamSettedInLoad; 
						}
					}
				}
				if (citAjaxConfigFile != null){
					System.out.println("CITAJAX -> Carregado 3.o Passo!");
				}				
			}
			LOGGER.info("CITAJAX_CONFIG: " + fileNameConfig);
			singleton = new CitAjaxConfig(citAjaxConfigFile, fileNameConfig);
		}
		return singleton;
	}
	public CitAjaxConfig(InputStream ioos, String fileNameConfig){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null){
            	throw new Exception("ARQUIVO (CITAJAX_CONFIG): " + fileNameConfig + " NAO ENCONTRADO!!!!!!!!!");
            }
            doc = builder.parse(ioos);
            load();
        } catch (Exception e) {
            e.printStackTrace();
            doc = null;
        }		
	}
	public void load(){
		if (doc == null) return;
		String pathIn = "", pathOut = "";
		redirectItens = new ArrayList();
		RedirectItem item;
		Node noRoot = doc.getChildNodes().item(0);
		for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
            Node noItem = noRoot.getChildNodes().item(j);
            if(noItem.getNodeName().equals("#text")) continue;

            NamedNodeMap map = noItem.getAttributes();
            pathIn = map.getNamedItem("pathIn").getNodeValue();
            pathOut = map.getNamedItem("pathOut").getNodeValue();
            
            System.out.println(">>>> CITAJAX.xml [Entrada]: " + pathIn + "  ->  " + pathOut);
            
            item = new RedirectItem();
            item.setPathIn(pathIn);
            item.setPathOut(pathOut);
            redirectItens.add(item);
		}
	}
	public RedirectItem getPathInConfig(String path, HttpServletRequest request){
		if (path == null) return null;
		
		int iniStr = path.indexOf(request.getContextPath());
		if (iniStr > -1){
			path = path.substring(iniStr + request.getContextPath().length());
		}
		if (this.getRedirectItens() != null){
			for(Iterator it = this.getRedirectItens().iterator(); it.hasNext();){
				RedirectItem r = (RedirectItem)it.next();
				if (r != null){
					if (r.getPathIn().trim().equalsIgnoreCase(path.trim())){
						return r;
					}
				}
			}
		}
		return null;
	}
	public Collection getRedirectItens() {
		return redirectItens;
	}
	public void setRedirectItens(Collection redirectItens) {
		this.redirectItens = redirectItens;
	}
	
}
