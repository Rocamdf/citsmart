package br.com.centralit.citcorpore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import br.com.centralit.citajax.html.DocumentHTML;
import br.com.centralit.citajax.html.HTMLForm;
import br.com.centralit.citcorpore.batch.MonitoraAtivosDiscovery;
import br.com.centralit.citcorpore.batch.ThreadValidaFaixaIP;
import br.com.centralit.citcorpore.comm.server.IPAddress;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

public class CITCorporeUtil {
    public static boolean DEBUG = false;


	public static List lstExternalClasses = null;
	public static HashMap hsmExternalClasses = null;

    public static String caminho_real_app = "";
    public static String caminho_real_config_file = "";
    public static String IDENTIFICACAO_CLIENTE = "GERAL";
    
    public static boolean START_MODE_DISCOVERY = true;
    public static boolean START_MODE_INVENTORY = true;
    public static boolean START_MODE_RULES = true;
    public static boolean START_MODE_ITSM = true;
    
    public static String IP_RANGE_DISCOVERY = "";
    public static String JDBC_ALIAS_INVENTORY = "";
    public static String JDBC_ALIAS_REPORTS = "";

    public static String SGBD_PRINCIPAL = "";
    public static String CAMINHO_SCRIPTS = "/citsmart/scripts_deploy/";

    public static String IDENTIFICACAO_STF = "STF";
    public static String IDENTIFICACAO_TRF = "TRF1";
    public static String IDENTIFICACAO_SESC = "SESC";
    public static String IDENTIFICACAO_TRT12 = "TRT12";
    public static String IDENTIFICACAO_CASEMBRAPA = "CASEMBRAPA";

    public static final String CAMINHO_SERVIDOR = Constantes.getValue("SERVER_ADDRESS");
	static{
	    System.out.println(":::::: CITCorporeUtil.CAMINHO_SERVIDOR ---> " + CITCorporeUtil.CAMINHO_SERVIDOR);
	}
    // public static final String CAMINHO_SERVIDOR =
    // "http://citcorpore.centralit.com.br";
	
	/*
	 * Inserido por Emauri - 23/11/2013
	 */	
	public static void fazLeituraArquivoConfiguracao(){
		if (CITCorporeUtil.caminho_real_config_file == null){
			CITCorporeUtil.caminho_real_config_file = "";
		}
		System.out.println("CITSMART - CAMINHO DO CONFIG: " + CITCorporeUtil.caminho_real_config_file);
		File fConf = new File(CITCorporeUtil.caminho_real_config_file);
		String pathConfigStartMode = CITCorporeUtil.caminho_real_config_file;
		if (fConf.exists()){
			System.out.println("CITSMART - CAMINHO DA CONFIG: " + pathConfigStartMode + " - ARQUIVO EXISTE!!! AVALIANDO CONFIGURACOES!!!");
			Properties propsCfg = new Properties();
			try {
				propsCfg.load(new FileInputStream(fConf));
				if(propsCfg != null){
					//--- Verificando START_MODE_INVENTORY
					String strItemProp = propsCfg.getProperty("START_MODE_INVENTORY");
					if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp.trim().equalsIgnoreCase("S"))){
						CITCorporeUtil.START_MODE_INVENTORY = true;
					}else if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp.trim().equalsIgnoreCase("N"))){
						CITCorporeUtil.START_MODE_INVENTORY = false;
					}
					System.out.println("CITSMART - START_MODE_INVENTORY: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_INVENTORY + ")");
					
					//--- Verificando START_MODE_DISCOVERY
					strItemProp = propsCfg.getProperty("START_MODE_DISCOVERY");
					if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp.trim().equalsIgnoreCase("S"))){
						CITCorporeUtil.START_MODE_DISCOVERY = true;
					}else if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp.trim().equalsIgnoreCase("N"))){
						CITCorporeUtil.START_MODE_DISCOVERY = false;
					}
					System.out.println("CITSMART - START_MODE_DISCOVERY: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_DISCOVERY + ")");	
					
					//--- Verificando START_MODE_RULES
					strItemProp = propsCfg.getProperty("START_MODE_RULES");
					if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp.trim().equalsIgnoreCase("S"))){
						CITCorporeUtil.START_MODE_RULES = true;
					}else if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp.trim().equalsIgnoreCase("N"))){
						CITCorporeUtil.START_MODE_RULES = false;
					}
					System.out.println("CITSMART - START_MODE_RULES: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_RULES + ")");		
					
					//--- Verificando START_MODE_ITSM
					strItemProp = propsCfg.getProperty("START_MODE_ITSM");
					if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp.trim().equalsIgnoreCase("S"))){
						CITCorporeUtil.START_MODE_ITSM = true;
					}else if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp.trim().equalsIgnoreCase("N"))){
						CITCorporeUtil.START_MODE_ITSM = false;
					}
					System.out.println("CITSMART - START_MODE_ITSM: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_ITSM + ")");
					
					//--- Verificando NATIVE_PING
					strItemProp = propsCfg.getProperty("NATIVE_PING");
					if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("TRUE") || strItemProp.trim().equalsIgnoreCase("T") || strItemProp.trim().equalsIgnoreCase("OK") || strItemProp.trim().equalsIgnoreCase("S"))){
						IPAddress.NATIVE_PING = true;
					}else if (strItemProp != null && (strItemProp.trim().equalsIgnoreCase("FALSE") || strItemProp.trim().equalsIgnoreCase("F") || strItemProp.trim().equalsIgnoreCase("NOK") || strItemProp.trim().equalsIgnoreCase("N"))){
						IPAddress.NATIVE_PING = false;
					}
					System.out.println("CITSMART - NATIVE_PING: " + strItemProp + " - (" + IPAddress.NATIVE_PING + ")");					
					
					//--- Verificando PING_TIMEOUT
					strItemProp = propsCfg.getProperty("PING_TIMEOUT");
					if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")){
						try{
							int pingTimeOut = IPAddress.PING_TIMEOUT;
							pingTimeOut = Integer.parseInt(strItemProp);
							IPAddress.PING_TIMEOUT = pingTimeOut;
						}catch(Exception e){}
					}
					System.out.println("CITSMART - PING_TIMEOUT: " + strItemProp + " - (" + IPAddress.PING_TIMEOUT + ")");	
					
					//--- Verificando NUM_THREADS_DISCOVERY
					strItemProp = propsCfg.getProperty("NUM_THREADS_DISCOVERY");
					if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")){
						try{
							int numThreads = ThreadValidaFaixaIP.NUMERO_THREADS;
							numThreads = Integer.parseInt(strItemProp);
							ThreadValidaFaixaIP.NUMERO_THREADS = numThreads;
						}catch(Exception e){}
					}
					System.out.println("CITSMART - NUM_THREADS_DISCOVERY: " + strItemProp + " - (" + ThreadValidaFaixaIP.NUMERO_THREADS + ")");	
					
					//--- Verificando NUM_THREADS_INVENTORY
					strItemProp = propsCfg.getProperty("NUM_THREADS_INVENTORY");
					if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")){
						try{
							int numThreads = MonitoraAtivosDiscovery.NUMERO_THREADS;
							numThreads = Integer.parseInt(strItemProp);
							MonitoraAtivosDiscovery.NUMERO_THREADS = numThreads;
						}catch(Exception e){}
					}
					System.out.println("CITSMART - NUM_THREADS_INVENTORY: " + strItemProp + " - (" + MonitoraAtivosDiscovery.NUMERO_THREADS + ")");	
					
					//--- Verificando JDBC_ALIAS_INVENTORY
					strItemProp = propsCfg.getProperty("JDBC_ALIAS_INVENTORY");
					if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")){
						try{
							CITCorporeUtil.JDBC_ALIAS_INVENTORY = strItemProp.trim();
						}catch(Exception e){}
					}
					System.out.println("CITSMART - JDBC_ALIAS_INVENTORY: " + strItemProp + " - (" + CITCorporeUtil.JDBC_ALIAS_INVENTORY + ")");	
					
					//--- Verificando JDBC_ALIAS_REPORTS
					strItemProp = propsCfg.getProperty("JDBC_ALIAS_REPORTS");
					if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")){
						try{
							CITCorporeUtil.JDBC_ALIAS_REPORTS = strItemProp.trim();
						}catch(Exception e){}
					}
					System.out.println("CITSMART - JDBC_ALIAS_REPORTS: " + strItemProp + " - (" + CITCorporeUtil.JDBC_ALIAS_REPORTS + ")");						
					
					//--- Verificando IP_RANGE_DISCOVERY
					strItemProp = propsCfg.getProperty("IP_RANGE_DISCOVERY");
					if (strItemProp != null && !strItemProp.trim().equalsIgnoreCase("") && !strItemProp.trim().equalsIgnoreCase("0")){
						try{
							CITCorporeUtil.IP_RANGE_DISCOVERY = strItemProp.trim();
						}catch(Exception e){}
					}
					System.out.println("CITSMART - IP_RANGE_DISCOVERY: " + strItemProp + " - (" + CITCorporeUtil.IP_RANGE_DISCOVERY + ")");					
				}else{
					System.out.println("CITSMART - NAO FOI POSSIVEL LER AS PROPRIEDADES DO ARQUIVO DE CONFIGURACOES!!!");
				}
			} catch (FileNotFoundException e) {
				System.out.println("CITSMART - PROBLEMAS AO LER AS PROPRIEDADES DO ARQUIVO DE CONFIGURACOES!!!");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("CITSMART - PROBLEMAS AO LER AS PROPRIEDADES DO ARQUIVO DE CONFIGURACOES!!!");
				e.printStackTrace();
			}
		}else{
			System.out.println("CITSMART - CAMINHO DA CONFIG: " + pathConfigStartMode + " - ARQUIVO NAO EXISTE!!!!!!!!!!!!!!!!!!!! ASSUMINDO CONFIGURACOES PADRAO...");
			String strItemProp = null;
			System.out.println("CITSMART - START_MODE_INVENTORY: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_INVENTORY + ")");
			System.out.println("CITSMART - START_MODE_DISCOVERY: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_DISCOVERY + ")");	
			System.out.println("CITSMART - START_MODE_RULES: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_RULES + ")");
			System.out.println("CITSMART - START_MODE_ITSM: " + strItemProp + " - (" + CITCorporeUtil.START_MODE_ITSM + ")");
			System.out.println("CITSMART - NATIVE_PING: " + strItemProp + " - (" + IPAddress.NATIVE_PING + ")");
			System.out.println("CITSMART - PING_TIMEOUT: " + strItemProp + " - (" + IPAddress.PING_TIMEOUT + ")");
			System.out.println("CITSMART - NUM_THREADS_DISCOVERY: " + strItemProp + " - (" + ThreadValidaFaixaIP.NUMERO_THREADS + ")");
			System.out.println("CITSMART - NUM_THREADS_INVENTORY: " + strItemProp + " - (" + MonitoraAtivosDiscovery.NUMERO_THREADS + ")");
			System.out.println("CITSMART - JDBC_ALIAS_INVENTORY: " + strItemProp + " - (" + CITCorporeUtil.JDBC_ALIAS_INVENTORY + ")");
			System.out.println("CITSMART - JDBC_ALIAS_REPORTS: " + strItemProp + " - (" + CITCorporeUtil.JDBC_ALIAS_REPORTS + ")");
			System.out.println("CITSMART - IP_RANGE_DISCOVERY: " + strItemProp + " - (" + CITCorporeUtil.IP_RANGE_DISCOVERY + ")");	
		}
	}
	/*
	 * Fim - Inserido por Emauri - 23/11/2013
	 */
	
    public static String getNameFile(String fullPathFile) {
	int tam = fullPathFile.length() - 1;
	String nomeFile = "";
	for (int i = tam; i >= 0; i--) {
	    if (fullPathFile.charAt(i) == '\\' || fullPathFile.charAt(i) == '/')
		break;
	    else
		nomeFile = fullPathFile.charAt(i) + nomeFile;
	}
	nomeFile = UtilStrings.removeCaracteresEspeciais(nomeFile);
	nomeFile = UtilStrings.retiraEspacoPorUnderline(nomeFile);
	return nomeFile;
    }

    public static String getExtensao(String nome) {
	if (nome == null)
	    return "";
	String resultado = "";
	int i = nome.length();
	while (i >= 1) {
	    if (nome.charAt(i - 1) == '.') {
		resultado = nome.substring(i, nome.length());
		i = -1;
	    } else {
		i = i - 1;
	    }
	}
	return resultado;
    }

    /**
     * Limpa formulário.
     * 
     * @param document
     * @return HTMLForm
     * @author valdoilo.damasceno
     */
    public static HTMLForm limparFormulario(DocumentHTML document) {
	HTMLForm form = document.getForm("form");
	form.clear();
	return form;
    }
}
