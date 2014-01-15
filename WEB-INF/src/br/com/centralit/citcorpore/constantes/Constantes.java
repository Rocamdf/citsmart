/*
 * Created on 04/06/2004
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package br.com.centralit.citcorpore.constantes;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import br.com.centralit.citcorpore.util.CITCorporeUtil;

/**
 * @author Central IT
 */ 
public class Constantes 
{
	public static boolean LOADED = false;
	//Definicoes para usuarios
	public static final String USUARIO_INDEFINIDO = "0";
	public static final String USUARIO_NORMAL     = "1";
	public static final String USUARIO_PUBLICO    = "2";
	
	public static final String FORM_NORMAL        = "0";
	public static final String MULT_FORM_DATA     = "1";
	
	public static final String VERSAO     = "CITSmart V.1.0 Beta";
	 	
	public static final String SIMBOLO_MOEDA      = "R$";
	public static final String SENHA_PADRAO       = "mudar";
	
	public static final boolean IMPRIMIR_ERROS    = true;

	//public static String RAIZ_SITE  = "http://200.101.97.124:8080/citsaude";
	public static String RAIZ_SITE  = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS") + "/citcorpore";
	
	public static String RAIZ_CITSAUDE  = "/citcorpore";
	//public static String RAIZ_CITSAUDE  = "";
	
	public static String RAIZ_CITFINANCEIRO  = RAIZ_CITSAUDE;
	public static String PROTOCOLO  = "http://";
	
	public static String ENDERECO_SITE = br.com.citframework.util.Constantes.getValue("SERVER_ADDRESS"); 
	//public static String ENDERECO_SITE = "200.101.97.124:8080";
	
	public static boolean IMPRIMIR_FORMULARIO_POSTADO = false;

	// Controle de erros - redirecionamentos
	public static String PAGINA_LOGIN      			= RAIZ_SITE + "/login.jsp";
	public static String PAGINA_LOGIN_CITSAUDE 	= "http://" + ENDERECO_SITE + RAIZ_CITSAUDE + "/login.jsp";
	public static String PAGINA_SEM_ACESSO 			= RAIZ_SITE + "/erro/semacesso.jsp";
	
	//Outras Constantes
	public static final String SPACE              = " ";
	public static final String DESC               = "desc";
	public static final String CARACTER_PESQUISA  = "%";
	public static final String CARACTER_SEPARADOR = "\5";
	
	public static final String CARACTER_SEPARADOR_PIPE = "|";
	
	public static final String INSERT            = "I";
	public static final String UPDATE            = "U";	
	
	private static String CFG_FILE = "config";	
	
	public static final String EMAIL_DE			 = "local@portal.com.br";
	
	public static final int INTERVALO_PADRAO	 = 15; //15 minutos
	
	public static final int CODIGO_CONVENIO_PARTICULAR = 1;
	
	/**
	 * Definição de campos utilizados no sistema.
	 */
	public static final int CAMPO_TEXTO			 = 1;
	public static final int CAMPO_NUMERO		 = 2;
	public static final int CAMPO_DATA			 = 3;
	public static final int CAMPO_LISTA			 = 4;
	public static final int CAMPO_TABELA		 = 5;
	
	public static final String ESTILO_LISTA      = "L";
	public static final String ESTILO_RADIO      = "R";
	public static final String ESTILO_CHECK      = "C";
	
	public static final int SEQUENCIA_OUTROS  	 = 1000;
	public static final String TEXTO_OUTROS  	 = "Outro";
	
	public static final int TEMPLATE_APLICACAO_ANAMNESE = 1;
	
	public static String CAMINHO_GERAL_FOTO 		= RAIZ_CITSAUDE + "/empresas";
	public static String CAMINHO_GERAL_FOTO_INDV	= "/empresas";
	public static String CAMINHO_GERAL_TMP_IMG 	= "/empresas";
	public static String CAMINHO_GERAL_IMG 		= RAIZ_CITSAUDE + "/empresas";
	public static String CAMINHO_GERAL_IMG_CLIENTE = RAIZ_CITSAUDE + "/imagensClientes";
	public static String CAMINHO_GERAL_IMG_CLIENTE2 = "/produtos/citsaude/imagensClientes";
	public static String CAMINHO_GERAL_BD_IMG 	= "/empresas";
	public static String CAMINHO_JASPER 	= "/citsmart/tempFiles/";
	
	public static final int CID_9  = 9;
	public static final int CID_10 = 10;
	
	public static final int CODIGO_CH = 9;
	
	public static final Integer OPER_AGENDAMENTO = 0x1;
	public static final Integer OPER_REMARCACAO = 0x2;
	public static final Integer OPER_DESMARCACAO = 0x3;
	
	static {
		Constantes.load();
	}
	
	public static void load(){
	    System.out.println(">>>>>>>>>>>>>>>>> INICIO CARGA - DEFINICOES DO SISTEMA <<<<<<<<<<<<<<<<<");		
		try{
			ResourceBundle config = ResourceBundle.getBundle(CFG_FILE);
			
			try{
				Constantes.ENDERECO_SITE = config.getString("ENDERECO_SITE");
			}catch(MissingResourceException e){
			    Constantes.ENDERECO_SITE = null;
			}
			if (Constantes.ENDERECO_SITE == null) Constantes.ENDERECO_SITE = "";
			//
			try{
				Constantes.RAIZ_CITSAUDE = config.getString("RAIZ_CITSAUDE");
			}catch(MissingResourceException e){
			    Constantes.RAIZ_CITSAUDE = null;
			}
			if (Constantes.RAIZ_CITSAUDE == null) Constantes.RAIZ_CITSAUDE = "/citsaude";
			Constantes.RAIZ_CITFINANCEIRO = Constantes.RAIZ_CITSAUDE;
			Constantes.CAMINHO_GERAL_FOTO 		= RAIZ_CITSAUDE + "/empresas";
			Constantes.CAMINHO_GERAL_FOTO_INDV	= "/empresas";
			Constantes.CAMINHO_GERAL_TMP_IMG 	= "/empresas";
			Constantes.CAMINHO_GERAL_IMG 		= RAIZ_CITSAUDE + "/empresas";
			Constantes.CAMINHO_GERAL_IMG_CLIENTE = RAIZ_CITSAUDE + "/imagensClientes";
			Constantes.CAMINHO_GERAL_IMG_CLIENTE2 = "/imagensClientes";
			Constantes.CAMINHO_GERAL_BD_IMG 	= "/empresas";
			//
			try{
				Constantes.RAIZ_SITE = config.getString("RAIZ_SITE");
			}catch(MissingResourceException e){
			    Constantes.RAIZ_SITE = null;
			}
			if (Constantes.RAIZ_SITE == null) Constantes.RAIZ_SITE = "";
			Constantes.PAGINA_LOGIN      			= Constantes.RAIZ_SITE + "/index.jsp";
			Constantes.PAGINA_LOGIN_CITSAUDE 	= "http://" + Constantes.ENDERECO_SITE + Constantes.RAIZ_CITSAUDE + "/index.jsp";
			Constantes.PAGINA_SEM_ACESSO 			= Constantes.RAIZ_SITE + "/erro/semacesso.jsp";
			//
			try{
				Constantes.PROTOCOLO = config.getString("PROTOCOLO");
			}catch(MissingResourceException e){
			    Constantes.PROTOCOLO = null;
			}
			if (Constantes.PROTOCOLO == null) Constantes.PROTOCOLO = "http://";
			//
			/*
			try{
			    String auxFormPost = config.getString("IMPRIMIR_FORMULARIO_POSTADO");
			    auxFormPost = Util.nullPorString(auxFormPost);
			    if (auxFormPost.equalsIgnoreCase("true")){
			        Constantes.IMPRIMIR_FORMULARIO_POSTADO = true;
			    }else{
			        Constantes.IMPRIMIR_FORMULARIO_POSTADO = false;
			    }
			}catch(MissingResourceException e){
			    Constantes.IMPRIMIR_FORMULARIO_POSTADO = false;
			}
			*/
			Constantes.IMPRIMIR_FORMULARIO_POSTADO = CITCorporeUtil.DEBUG;
			LOADED = true;
			//------------ Imprime os valores lidos do Properties ----------------//
			System.out.println("RAIZ_SITE = " + Constantes.RAIZ_SITE);
			System.out.println("RAIZ_CITSAUDE = " + Constantes.RAIZ_CITSAUDE);
			System.out.println("RAIZ_CITFINANCEIRO = " + Constantes.RAIZ_CITFINANCEIRO);
			System.out.println("PROTOCOLO = " + Constantes.PROTOCOLO);
			
			System.out.println("IMPRIMIR_FORMULARIO_POSTADO = " + CITCorporeUtil.DEBUG);
			
		}catch(Exception e){
			System.out.println(">>>>>>>>>>>>>>>>> PROBLEMAS NA CARGA DAS DEFINICOES DO SISTEMA <<<<<<<<<<<<<<<<<");
			e.printStackTrace();
			System.out.println("===============================================================================");
			return;
		}
		System.out.println(">>>>>>>>>>>>>>>>> DEFINICOES DO SISTEMA CARREGADAS COM SUCESSO!!!!! <<<<<<<<<<<<<<<<<");
	}	
}
