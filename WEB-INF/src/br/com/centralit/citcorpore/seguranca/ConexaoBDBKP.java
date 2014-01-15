package br.com.centralit.citcorpore.seguranca;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

import br.com.centralit.citcorpore.exception.SystemException;

/**
 *
 * @author  Tellus SA
 * @version 
 */
public class ConexaoBDBKP implements java.io.Serializable{

  private static String   userBD = "",
                          passwordBD = "",
                          urlBD = "",
                          driverBD = "";
                            
  private static String 
     CONNECTION_FILE    = 
                "Conexao";

  private Connection conn;
  private long instanteCriacao;
  private long instanteUltimoAcesso;

  public ConexaoBDBKP() throws SystemException {
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
  	
	try {
	  Class.forName(driverBD).newInstance();
	  this.conn = DriverManager.getConnection(urlBD, userBD, passwordBD);
	  this.instanteCriacao = System.currentTimeMillis();
	  this.instanteUltimoAcesso = System.currentTimeMillis();
	}
	catch(Exception exc){
	    exc.printStackTrace();
	    throw new SystemException("sis9998", exc.getMessage());
	}
  }
  
  public void fecharConexao(){
  	try{
  		if (this.conn != null){
  			if (!this.conn.isClosed()){
		  		this.conn.close();
		  		this.conn = null;
  			}
  		}
  	}
	catch(SQLException exc){
		exc.printStackTrace(); //Não problema nenhum caso aconteça este erro.
	}  	
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
