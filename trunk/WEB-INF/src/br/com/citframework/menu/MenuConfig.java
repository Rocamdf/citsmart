package br.com.citframework.menu;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.citframework.util.Constantes;

public class MenuConfig {
	
	private static final Logger LOGGER = Logger.getLogger(MenuConfig.class);
	private static MenuConfig	singleton;
	private Document doc = null;
	private Collection menuItens;
	public static MenuConfig getInstance(ServletContext servletContext) throws Exception {
		if (singleton == null) {
			LOGGER.info("ServletContext: " + servletContext);
			InputStream menuFile = servletContext.getResourceAsStream(Constantes.getValue("MENU_FILE_CFG")); 
			if (menuFile == null){
				menuFile = servletContext.getResourceAsStream("/WEB-INF/menu-config.xml"); 
			}
			//InputStream menuFile = new FileInputStream(Constantes.getValue("MENU_FILE_CFG"));
			LOGGER.info("MENU_FILE_CFG: " + menuFile);
			singleton = new MenuConfig(menuFile);
		}
		return singleton;
	}
	public MenuConfig(InputStream ioos){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null){
            	throw new Exception("ARQUIVO: " + Constantes.getValue("MENU_FILE_CFG") + " NAO ENCONTRADO!!!!!!!!!");
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
		String description = "", path = "";
		menuItens = new ArrayList();
		MenuItem m;
		Node noRoot = doc.getChildNodes().item(0);
		for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
            Node noMenu = noRoot.getChildNodes().item(j);
            if(noMenu.getNodeName().equals("#text")) continue;

            NamedNodeMap map = noMenu.getAttributes();
            description = map.getNamedItem("description").getNodeValue();
            path = map.getNamedItem("path").getNodeValue();
            
            m = new MenuItem();
            m.setDescription(description);
            m.setPath(path);
            m.setRootLevel(true);
            m = getSubTree(m, noMenu);
            menuItens.add(m);
		}
	}
	public MenuItem getSubTree(MenuItem m, Node noMenu){
		if (noMenu == null) return m;
		String description = "", path = "";
		MenuItem menuTemp;
		for (int i = 0; i < noMenu.getChildNodes().getLength(); i++){
    		Node noMenuItem = noMenu.getChildNodes().item(i);
    		if(noMenuItem.getNodeName().equals("#text")) continue;
    		if (noMenuItem.getNodeName().equalsIgnoreCase("menu-itens")){
    			m = getSubTree(m, noMenuItem);
    		}else if (noMenuItem.getNodeName().equalsIgnoreCase("item-menu")){
                NamedNodeMap map = noMenuItem.getAttributes();
                description = map.getNamedItem("description").getNodeValue();
                path = map.getNamedItem("path").getNodeValue();
                
                menuTemp = new MenuItem();
                menuTemp.setDescription(description);
                menuTemp.setPath(path);
                if (m.getMenuItens()==null){
                	m.setMenuItens(new ArrayList());
                }
                m.getMenuItens().add(menuTemp);
                menuTemp = getSubTree(menuTemp, noMenuItem);
    		}
		}
		return m;
	}
	public Collection getMenuItens() {
		return menuItens;
	}
	public void setMenuItens(Collection menuItens) {
		this.menuItens = menuItens;
	}
}
