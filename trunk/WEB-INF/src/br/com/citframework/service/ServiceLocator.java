package br.com.citframework.service;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.ejb.EJBHome;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.rmi.PortableRemoteObject;

import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.BadServiceConfigurationException;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.excecao.ServiceNotFoundException;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.Util;



public class ServiceLocator {
	
	private Map						cache;
	private static String			URL_EJB								= null;											// "jnp://127.0.0.1:1099";
	private static String			CONTEXT_EJB							= null;											// "org.jnp.interfaces.NamingContextFactory";
	private static ServiceLocator	singleton;
	
	private static String IS_WEBSPHERE = null;
	private static String URL_FIXA = "FALSE";

	// Singleton
	private ServiceLocator() throws Exception {
		super();
		this.cache = new HashMap();
		URL_EJB	 =  Constantes.getValue("URL_EJB"); 
		CONTEXT_EJB	 = Constantes.getValue("CONTEXT_EJB");
		
		IS_WEBSPHERE = Constantes.getValue("IS_WEBSPHERE");
		if (IS_WEBSPHERE == null){
			IS_WEBSPHERE = "false";
		}	
		URL_FIXA = Constantes.getValue("URL_FIXA");
		if (URL_FIXA == null) URL_FIXA = "FALSE";
		if (IS_WEBSPHERE.equalsIgnoreCase("true")){
			System.out.println("CITFRAMEWORK: - EH WEBSPHERE!");
			URL_FIXA = "FALSE"; //FORCA PARA ENTRAR NO IF ABAIXO.
		}	
		System.out.println("CITFRAMEWORK: INICIANDO ServiceLocator... " + 
				Constantes.getValue("TIPO_SERVICE") +
				" - IS_WEBSPHERE: " + Constantes.getValue("IS_WEBSPHERE"));		
		if(Constantes.getValue("TIPO_SERVICE").equalsIgnoreCase("EJB")){
			if (URL_FIXA.equalsIgnoreCase("FALSE")){
				if (URL_EJB.indexOf("jnp") > -1){ //EH JBOSS
					InetAddress localAdrr = null;
					try {
						localAdrr = InetAddress.getLocalHost();
						URL_EJB = "jnp://" + localAdrr.getHostAddress() + ":1099";
						System.out.println("CITFRAMEWORK: - URL de Localizacao de Objetos (JBOSS): " + URL_EJB);
					} catch (UnknownHostException e) {
						System.out.println(e);
					}			
				}else{
					if (URL_EJB.indexOf("corbaloc") > -1){ //EH WEBSPHERE
						InetAddress localAdrr = null;
						try {
							localAdrr = InetAddress.getLocalHost();
							URL_EJB = "corbaloc:iiop:" + localAdrr.getHostAddress() + ":2809";
							System.out.println("CITFRAMEWORK: - URL de Localizacao de Objetos (WEBSPHERE): " + URL_EJB);
						} catch (UnknownHostException e) {
							System.out.println(e);
						}				
					}
				}
			}else{
				System.out.println("CITFRAMEWORK: - URL de Localizacao de Objetos: " + URL_EJB);
			}
		}
	}

	public static ServiceLocator getInstance() throws Exception {
		if (singleton == null) {
			singleton = new ServiceLocator();
		}
		return singleton;
	}
	
	
	protected EJBHome getEjbHome(String jndiName, Class homeClass) throws Exception {

		Map map = Collections.synchronizedMap(cache);
		if (map.containsKey(jndiName) && !IS_WEBSPHERE.equalsIgnoreCase("true")) {
			return (EJBHome) map.get(jndiName);
		} else {
			Hashtable ht = null;
			if ((CONTEXT_EJB.length() > 0) || (URL_EJB.length() > 0)) {
				ht = new Hashtable();
				if (CONTEXT_EJB.length() > 0){
					ht.put(Context.INITIAL_CONTEXT_FACTORY, CONTEXT_EJB);
				}
				if (URL_EJB.length() > 0) {
					ht.put(Context.PROVIDER_URL, URL_EJB);
				}
			}
			InitialContext ctx = null;
			if (ht == null) {
				ctx = new InitialContext();
			} else {
				ctx = new InitialContext(ht);
			}
			Object ref = ctx.lookup(jndiName);
			EJBHome home = (EJBHome) PortableRemoteObject.narrow(ref, homeClass);
			cache.put(jndiName, home);
			return home;
		}

	}
	
	public Object getService(Class iservice,Usuario usr) throws ServiceException {
		String nome = iservice.getName();
		
		if(Constantes.getValue("TIPO_SERVICE")!=null){
			
			if(Constantes.getValue("TIPO_SERVICE").equalsIgnoreCase("POJO")){
				try{
					Object obj = Class.forName(nome+Constantes.getValue("SUFIXO_SERVICE")).newInstance();
					((IService)obj).setUsuario(usr);
					return obj;
				}catch (Exception e) {
					try{
						//Se nao conseguiu com o sufixo indicado nos parametros, faz com Bean pra ter certeza de que nao existe.
						Object obj = Class.forName(nome+"Bean").newInstance();
						((IService)obj).setUsuario(usr);
						return obj;						
					}catch (Exception e2) {
						e2.printStackTrace();
						throw new ServiceNotFoundException("Classe "+nome+Constantes.getValue("SUFIXO_SERVICE")+" não existe");
					}
				}				
			}else if(Constantes.getValue("TIPO_SERVICE").equalsIgnoreCase("EJB")){
				String nomeTmp = Util.getNomeClasseSemPacote(iservice);
				Class classeHome;
				try {
					classeHome = Class.forName(nome+Constantes.getValue("SUFIXO_HOME"));
				} catch (ClassNotFoundException e) {
					throw new ServiceNotFoundException("Interface "+nome+Constantes.getValue("SUFIXO_HOME")+" não existe");
				}
				String jndi = Constantes.getValue("PREFIXO_EJB")+nomeTmp;
				
				EJBHome home;
				try {
					home = getEjbHome(jndi,classeHome);
				} catch (Exception e) {
					throw new ServiceNotFoundException("Servico não encontrado: jndi:"+jndi+" Home:"+classeHome.getName());
				}
				Method met = Reflexao.findMethod("create", home);
				
				try {
					Object obj = met.invoke(home, null);
					((IService)obj).setUsuario(usr);
					return obj;
				} catch (Exception e) {
					throw new ServiceException(e);
				}
				
			}else{
				throw new BadServiceConfigurationException("O TIPO_SERVICE do arquivo Constantes.properties só pode ser POJO ou EJB (Encontrado: " + Constantes.getValue("TIPO_SERVICE") + ")");
			}

		}else{
			throw new BadServiceConfigurationException("TIPO_SERVICE não configurado no arquivo Constantes.properties");
			
		}
	}

}
