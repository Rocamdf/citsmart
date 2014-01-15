/*
 * Created on 21/Mar/2004
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import br.com.centralit.citcorpore.util.AdaptacaoBD;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

/**
 * @author Ney Pellegrini
 * 
 */
public class ConnectionProvider {

	public static String strSGBDPrincipal = null;
	public static final String POSTGRESQL = "POSTGRES";
	public static final String SQLSERVER = "SQLSERVER";

	private ConnectionProvider() {
	}

	public static final Connection getConnection(String pTipo) throws Exception {
		try {

			Context cnx = new InitialContext();

			// boolean isWebSphere = Constantes.getValue("IS_WEBSPHERE");//Constantes.TRUE.equalsIgnoreCase(SysConfig.getInstance().getValue(SysConfig.IS_WEBSPHERE));
			// if (isWebSphere)
			// else
			// ds = (DataSource) cnx.lookup("java:/" + pTipo);
			// Verifica de que maneira a Conexão será recuperada
			String recuperacao = Constantes.getValue("OBTECAO_CONEXAO");
			Connection con = null;
			String usuario = Constantes.getValue("USUARIO_CONEXAO");
			String senha = Constantes.getValue("SENHA_CONEXAO");
			String classe = Constantes.getValue("CLASSE_CONEXAO");
			String url = Constantes.getValue("URL_CONEXAO");
			String contexto = Constantes.getValue("CONTEXTO_CONEXAO");
			if (recuperacao != null && recuperacao.trim().equalsIgnoreCase("JDBC")) {

				if (usuario == null || usuario.trim().length() == 0) {
					throw new Exception("A constante USUARIO_CONEXAO deve ser preenchida corretamente");
				}
				if (senha == null || senha.trim().length() == 0) {
					throw new Exception("A constante SENHA_CONEXAO deve ser preenchida corretamente");
				}
				if (classe == null || classe.trim().length() == 0) {
					throw new Exception("A constante CLASSE_CONEXAO deve ser preenchida corretamente");
				}
				if (url == null || url.trim().length() == 0) {
					throw new Exception("A constante URL_CONEXAO deve ser preenchida corretamente");
				}
				Class.forName(classe);
				con = DriverManager.getConnection(url, usuario, senha);
			} else if (recuperacao != null && recuperacao.trim().equalsIgnoreCase("JNDI")) {

				if (contexto == null) {
					contexto = "";
				}

				if (pTipo == null || pTipo.trim().length() == 0) {
					throw new Exception("Nome jndi vazio ou nulo");
				} else {
					// System.out.println("[Connection Provider] - Capturando JNDI : "+contexto+pTipo);
				}
				DataSource ds;
				ds = (DataSource) cnx.lookup(contexto + pTipo);
				con = ds.getConnection();

				if (strSGBDPrincipal == null) {
					if (CITCorporeUtil.SGBD_PRINCIPAL == null || CITCorporeUtil.SGBD_PRINCIPAL.trim().equalsIgnoreCase("")) {
						AdaptacaoBD.getBancoUtilizado(con); // este metodo atualizada o valor de CITCorporeUtil.SGBD_PRINCIPAL, DEVE-SE PASSAR A CONEXAO COMO PARAMETRO! EMAURI 18/05/2013.
					}
					strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
					if (strSGBDPrincipal != null) {
						strSGBDPrincipal = strSGBDPrincipal.toUpperCase();
					}
					strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
				}

    			if (!strSGBDPrincipal.equalsIgnoreCase(POSTGRESQL)){
    				if (strSGBDPrincipal.equalsIgnoreCase(SQLSERVER)){
    					con.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
    				}else{
    					con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
    				}
    			}


			} else {
				try {
					if (contexto == null) {
						contexto = "";
					}
					DataSource ds;
					ds = (DataSource) cnx.lookup(contexto + pTipo);
					con = ds.getConnection();
				} catch (Exception e) {

					if (usuario == null || usuario.trim().length() == 0) {
						throw new Exception("A constante USUARIO_CONEXAO deve ser preenchida corretamente");
					}
					if (senha == null || senha.trim().length() == 0) {
						throw new Exception("A constante SENHA_CONEXAO deve ser preenchida corretamente");
					}
					if (classe == null || classe.trim().length() == 0) {
						throw new Exception("A constante CLASSE_CONEXAO deve ser preenchida corretamente");
					}
					if (url == null || url.trim().length() == 0) {
						throw new Exception("A constante URL_CONEXAO deve ser preenchida corretamente");
					}
					Class.forName(classe);
					con = DriverManager.getConnection(url, usuario, senha);
				}
			}
			con.setAutoCommit(false);
			return con;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;

		}

	}

}
