package br.com.centralit.citgerencial.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import br.com.centralit.citgerencial.bean.ParametroDTO;
import br.com.centralit.citgerencial.integracao.ParametroDao;
import br.com.citframework.dto.Usuario;
import br.com.citframework.util.Constantes;


public class ParametrosConfig {
	private static final Logger LOGGER = Logger.getLogger(ParametrosConfig.class);
	
	private static ParametrosConfig	singleton;
	private Document doc = null;
	
	public static InputStream inputStreamSettedInLoad = null;
	
	private Collection lstParametros;
	private Collection lstGruposParametros;
	
	public static ParametrosConfig getInstance() throws Exception {
		if (singleton == null) {
			String fileNameConfig = "cfgParametros.xml";
			if (Constantes.getValue("PARAMETROS_CONFIG") != null && !Constantes.getValue("PARAMETROS_CONFIG").trim().equalsIgnoreCase("")){
				fileNameConfig = Constantes.getValue("PARAMETROS_CONFIG");
			}
			InputStream parametrosConfigFile = ParametrosConfig.class.getClassLoader().getResourceAsStream(fileNameConfig); 
			if (parametrosConfigFile == null){
				parametrosConfigFile = ClassLoader.getSystemResourceAsStream(fileNameConfig);
				if (parametrosConfigFile == null){
					parametrosConfigFile = ClassLoader.getSystemClassLoader().getResourceAsStream(fileNameConfig); 
					if (parametrosConfigFile == null){
						try{
							parametrosConfigFile = new FileInputStream(Constantes.getValue("CAMINHO_PARAMETROS_CONFIG") + fileNameConfig);
						}catch (Exception e) {
							e.printStackTrace();
							LOGGER.error(e);
							//Se der errado, tenta por ultimo pegar informacoes do carregamento do sistema, se houver.
							parametrosConfigFile = inputStreamSettedInLoad; 
						}
					}
				}
			}
			LOGGER.info("PARAMETROS_CONFIG: " + fileNameConfig);
			singleton = new ParametrosConfig(parametrosConfigFile, fileNameConfig);
		}
		return singleton;
	}
	public ParametrosConfig(InputStream ioos, String fileNameConfig){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            if (ioos == null){
            	throw new Exception("ARQUIVO (PARAMETROS_CONFIG): " + fileNameConfig + " NAO ENCONTRADO!!!!!!!!!");
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
		String name = "", description = "";
		lstParametros = new ArrayList();
		lstGruposParametros = new ArrayList();
		Node noRoot = doc.getChildNodes().item(0);
		for(int j = 0; j < noRoot.getChildNodes().getLength(); j++){
            Node noItem = noRoot.getChildNodes().item(j);
            if(noItem.getNodeName().equals("#text")) continue;
            
            if (noItem.getNodeName().equalsIgnoreCase("grupo")){
                NamedNodeMap map = noItem.getAttributes();
                name = map.getNamedItem("name").getNodeValue();
                description = map.getNamedItem("description").getNodeValue();
                
                GrupoConfiguracaoParametro grupoConfiguracaoParametro = new GrupoConfiguracaoParametro();
                grupoConfiguracaoParametro.setName(name);
                grupoConfiguracaoParametro.setDescription(description);
                
                lstGruposParametros.add(grupoConfiguracaoParametro);
                
                for (int i = 0; i < noItem.getChildNodes().getLength(); i++) {
                	Node noItemParm = noItem.getChildNodes().item(i);
                	if(noItemParm.getNodeName().equals("#text")) continue;
	                if (noItemParm.getNodeName().equalsIgnoreCase("parametro")){
	                	NamedNodeMap mapItem = noItemParm.getAttributes();
	                	
	                	ItemConfiguracaoParametro itemConfiguracaoParametro = new ItemConfiguracaoParametro();
	                	itemConfiguracaoParametro.setGrupoName(name);
	                	itemConfiguracaoParametro.setGrupoDescription(description);
	                	
	                	String modulo = mapItem.getNamedItem("modulo").getNodeValue();
	                	String nameParm = mapItem.getNamedItem("name").getNodeValue();
	                	String type = mapItem.getNamedItem("type").getNodeValue();
	                	String size = mapItem.getNamedItem("size").getNodeValue();
	                	String valorDefault = mapItem.getNamedItem("default").getNodeValue();
	                	String descriptionParm = mapItem.getNamedItem("description").getNodeValue();
	                	
	                	itemConfiguracaoParametro.setModulo(modulo);
	                	itemConfiguracaoParametro.setName(nameParm);
	                	itemConfiguracaoParametro.setType(type);
	                	itemConfiguracaoParametro.setSize(size);
	                	itemConfiguracaoParametro.setValorDefault(valorDefault);
	                	itemConfiguracaoParametro.setDescription(descriptionParm);
	                	
	                	lstParametros.add(itemConfiguracaoParametro);
	                	
	                	for (int z = 0; z < noItemParm.getChildNodes().getLength(); z++) {
	                    	Node noItemCombo = noItemParm.getChildNodes().item(z);
	                    	if(noItemCombo.getNodeName().equals("#text")) continue;
	    	                if (noItemCombo.getNodeName().equalsIgnoreCase("item")){
	    	                	NamedNodeMap mapItemCombo = noItemCombo.getAttributes();

	    	                	String value = mapItemCombo.getNamedItem("value").getNodeValue();
	    	                	String descriptionItem = mapItemCombo.getNamedItem("description").getNodeValue();
	    	                	
	    	                	ItemComboParametro item = new ItemComboParametro();
	    	                	item.setValue(value);
	    	                	item.setDescription(descriptionItem);
	    	                	
	    	                	itemConfiguracaoParametro.getColItens().add(item);
	    	                }	                		
	                	}
	                }
                }
            }
		}
	}
	public Collection getLstParametros() {
		return lstParametros;
	}
	public void setLstParametros(Collection lstParametros) {
		this.lstParametros = lstParametros;
	}
	public Collection getLstGruposParametros() {
		return lstGruposParametros;
	}
	public void setLstGruposParametros(Collection lstGruposParametros) {
		this.lstGruposParametros = lstGruposParametros;
	}	
	
	public String getValueStr(HttpServletRequest request, String moduloParm, String nomeParametroParm) throws Exception {
		Usuario usuario = (Usuario) request.getSession(true).getAttribute("USUARIO_SESSAO");
		Integer idEmpresaParm = null;
		try{
			idEmpresaParm = usuario.getIdEmpresa();
		}catch (Exception e) {
			idEmpresaParm = new Integer(1);
		}

		ParametroDao parametroDao = new ParametroDao();
		ParametroDTO parametroDTO = parametroDao.getValue(moduloParm, nomeParametroParm, idEmpresaParm);
		if (parametroDTO == null){
			return null;
		}
		return parametroDTO.getValor();
	}
	public String getValueStr(Integer idEmpresa, String moduloParm, String nomeParametroParm) throws Exception {
		ParametroDao parametroDao = new ParametroDao();
		ParametroDTO parametroDTO = parametroDao.getValue(moduloParm, nomeParametroParm, idEmpresa);
		if (parametroDTO == null){
			return null;
		}
		return parametroDTO.getValor();
	}	
	public String getDetalhamentoStr(HttpServletRequest request, String moduloParm, String nomeParametroParm) throws Exception {
		Usuario usuario = (Usuario) request.getSession(true).getAttribute("USUARIO_SESSAO");
		Integer idEmpresaParm = null;
		try{
			idEmpresaParm = usuario.getIdEmpresa();
		}catch (Exception e) {
			idEmpresaParm = new Integer(1);
		}
		ParametroDao parametroDao = new ParametroDao();
		ParametroDTO parametroDTO = parametroDao.getValue(moduloParm, nomeParametroParm, idEmpresaParm);
		if (parametroDTO == null){
			return null;
		}		

		return parametroDTO.getDetalhamento();
	}	
}
