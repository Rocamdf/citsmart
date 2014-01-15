package br.com.centralit.citcorpore.seguranca;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.centralit.citcorpore.exception.SystemException;
import br.com.citframework.util.Constantes;

/**
 *
 * @author  CentralIT
 * @version 
 */
public class ConexaoBD implements java.io.Serializable{

  private static String   userBD = "",
                          passwordBD = "",
                          urlBD = "",
                          driverBD = "";
                            
  private static String 
     CONNECTION_FILE    = 
                "Conexao";

  private static DataSource ds = null;

  private Connection conn;
  private long instanteCriacao;
  private long instanteUltimoAcesso;

  public ConexaoBD(boolean b) throws SystemException {
	  this.conn = null;
  }
  public ConexaoBD() throws SystemException {
	if (urlBD.equals("")){
		try
		{
		  ResourceBundle conexao = ResourceBundle.getBundle(CONNECTION_FILE);
		  urlBD        = conexao.getString("urlBD");
		  driverBD     = conexao.getString("driverBD");
		  userBD       = conexao.getString("userBD");
		  passwordBD   = conexao.getString("passwordBD");
		}
		catch(Exception exc)
		{
		  exc.printStackTrace();
		  throw new SystemException("sis9998", "ERRO AO LER O ARQUIVO DE PROPERTIES - " + exc.getMessage());
		}
	}
  	
    //if (ds == null){
        Context initCtx = null;
        try {
            initCtx = new InitialContext();
        } catch (NamingException e1) {
            e1.printStackTrace();
        }
        Context envCtx = null;
        try {
            //envCtx = (Context) initCtx.lookup("java:comp/env");
        	if(initCtx != null){
        		envCtx = (Context) initCtx.lookup(Constantes.getValue("CONTEXTO_CONEXAO"));
        	}
        } catch (NamingException e2) {
            e2.printStackTrace();
        }
        try {
            //ds = (DataSource) envCtx.lookup("jdbc/citsaude");
        	if(envCtx != null){
        		ds = (DataSource) envCtx.lookup(Constantes.getValue("CONEXAO_DEFAULT"));
        	}
        } catch (NamingException e3) {
            e3.printStackTrace();
        }
    //}
    try {
        if (ds == null){
            System.out.println("DATA SOURCE NULO");
        }
        if(ds != null){
        	this.conn = ds.getConnection();
        }
    } catch (SQLException e) {
        System.out.println("PROBLEMAS COM DATASOURCE...");
        e.printStackTrace();
        if(this.conn != null)
			try {
				this.conn.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        this.conn = null;
    }
  }
  
  public void fecharConexao(){
  	try{
  		if (this.conn != null){
  			if (!this.conn.isClosed()){
		  		this.conn.close();
  			}
  		}
  		this.conn = null;
  	}
	catch(SQLException exc){
		exc.printStackTrace(); //Não problema nenhum caso aconteça este erro.
	}  	
  }
  
  public DataSource getDataSource()
  {
      return ds;
  }
  
  public java.sql.Connection getConn() {
    return conn;
  }  
  
  public long getInstanteCriacao() {
    return instanteCriacao;
  }
  
  public long getInstanteUltimoAcesso() {
    return instanteUltimoAcesso;
  }

  public void setConn(java.sql.Connection conn) {
    this.conn = conn;
  }  

  public void setInstanteCriacao(long instanteCriacao) {
    this.instanteCriacao = instanteCriacao;
  }
   
  public void setInstanteUltimoAcesso(long instanteUltimoAcesso) {
    this.instanteUltimoAcesso = instanteUltimoAcesso;
  }
 
  public boolean isConexaoValida(){
    return true;
  }
  
  protected void finalize() throws Throwable {
	  try {
		  fecharConexao();
	  } finally {
		  super.finalize();
	  }
  }
}
