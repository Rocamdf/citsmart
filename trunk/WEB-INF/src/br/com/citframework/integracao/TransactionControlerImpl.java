package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.excecao.ConnectionException;
import br.com.citframework.excecao.InvalidTransactionObjectException;
import br.com.citframework.excecao.TransactionOperationException;
import br.com.citframework.integracao.ConnectionProvider;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.UtilStrings;

public class TransactionControlerImpl implements TransactionControler {

	private Connection	connection;
	private boolean		started;
	private String		dataBaseAlias;

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
	}


	public String getDataBaseAlias() {
		return dataBaseAlias;
	}

	public void setDataBaseAlias(String dataBaseAlias) {
		this.dataBaseAlias = dataBaseAlias;
	}

	public TransactionControlerImpl(String alias) {
		super();		
		dataBaseAlias = alias;
	}

	public TransactionControlerImpl(Connection con) {
		super();
		this.connection = con;
	}
	
	/**
	 * Retorna a conexão relacionada ao Transaction Controler
	 */
	public Object getTransactionObject()throws ConnectionException  {
		
		if (connection == null) {
			try {
				//Caso conexão esteja nula, recupera uma conexão do provedor
				connection = ConnectionProvider.getConnection(getDataBaseAlias());
			} catch (Exception e) {
				connection = null;
				throw new ConnectionException(e);
			}
		} else {
			//Caso conexão exists
			if (isStarted()) {
				//se o TC estiver estartado, retornar conexão
				return connection;
			} else {
				//TC não estartado
				try {
					if (!connection.isClosed()) {
						//Fecha conexao caso esteja aberta
						try{
							connection.close();
						}catch (Exception e) {
						}
					}
					connection = null;
					//pega uma conexao do provedor
					connection = ConnectionProvider.getConnection(getDataBaseAlias());
				} catch (Exception e) {
					connection = null; 
					throw new ConnectionException(e);
				}
			}
		}

		return connection;
	}


	public void setTransactionObject(Object transactionObject) throws InvalidTransactionObjectException {
		
		if (transactionObject instanceof Connection) {
			connection = (Connection) transactionObject;
		} else {
			throw new InvalidTransactionObjectException("Transaction object Invalido");
		}

	}


	public void start() throws TransactionOperationException, ConnectionException {
		
		try {
			//Abre transação
			if (connection == null){
				((Connection) getTransactionObject()).setAutoCommit(false);
			}
			//((Connection) getTransactionObject()).setAutoCommit(false);
			String strSGBDPrincipal = null;
			strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;			
			strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
			if (connection != null){
			    connection.setAutoCommit(false);
			    if (strSGBDPrincipal.equalsIgnoreCase("MYSQL")){
        			    String sqlSetAutocommit = "SET autocommit = 0;";
        			    PreparedStatement ps = connection.prepareStatement(sqlSetAutocommit);
        			    ps.execute();
        			    ps.close();
			    }
			}
			setStarted(true);
		} catch (SQLException e) {
			connection = null;
			throw new TransactionOperationException("Start operation Failed",e);
		}

	}

	public void commit() throws TransactionOperationException, ConnectionException  {
		
		try {
			//Commita transação
			//((Connection) getTransactionObject()).commit();
		    if (connection != null){
		        connection.commit();
		    }
			setStarted(false);
		} catch (SQLException e) {
			connection = null;
			throw new TransactionOperationException("Commit operation Failed",e);
		}

	}

	public void rollback() throws TransactionOperationException, ConnectionException  {
		
		try {
			//Aborta Transação
			//((Connection) getTransactionObject()).rollback();
		    if (connection != null){
		        connection.rollback();
		    }
			setStarted(false);
			//((Connection) getTransactionObject()).setAutoCommit(false);
			if (connection != null){
			    connection.setAutoCommit(false);
			}
		} catch (SQLException e) {
			connection = null;
			System.out.println("CITFramework -> Rollback operation Failed ---> Forced..." + e);
			//throw new TransactionOperationException("Rollback operation Failed",e);
			setStarted(false);
		}
	}

	public void close() throws TransactionOperationException, ConnectionException  {
		
		try {
			//fecha conexão caso ja não esteja fechada
			if (connection == null) {
				if (isStarted()) {
					//((Connection) getTransactionObject()).close();
					setStarted(false);
				}				
			}else{
				if (!connection.isClosed()){ // && (!isStarted())) {
					try{
						//((Connection) getTransactionObject()).commit();
						connection.commit();
					}catch (Exception e) {
						//Deixa passar o erro.
					}
					try{
						//((Connection) getTransactionObject()).close();
						connection.close();
					}catch (Exception e) {
						//Deixa passar o erro.
					}
					setStarted(false);
				}
			}
			setStarted(false);
			connection = null;
		} catch (SQLException e) {
			connection = null;	
			System.out.println("CITFramework -> Close operation Failed");
			setStarted(false);
			//throw new TransactionOperationException("Close operation Failed",e);
		}

	}
	/**
	 * Funcao colocada so para garantir estabilidade do sistema, caso a conexao com o banco de dados permaneca ativa por algum motivo.
	 * 
	 * Este metodo sera chamado quando o Garbage Colector passar e descartar um objeto desta classe.
	 */
	protected void finalize() throws Throwable {
		try {
			if (connection != null) {
				if (!connection.isClosed()) {
					//((Connection) getTransactionObject()).close();
					connection.close();
					setStarted(false);
				}
			}
		} catch (Exception e) {
			//deixa para la o erro. Eh so pra garantir estabilidade do sistema.
		}	
		connection = null;
		super.finalize();
	}
}
