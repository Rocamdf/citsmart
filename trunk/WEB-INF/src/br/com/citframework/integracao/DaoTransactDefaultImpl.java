package br.com.citframework.integracao;

import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.InvalidTransactionControler;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes" })
public abstract class DaoTransactDefaultImpl implements IDaoTransact {

	private static final long serialVersionUID = -4661598348894649790L;

	protected PersistenceEngine engine;
	protected String aliasDB;
	protected Usuario usuario;

	public DaoTransactDefaultImpl(String aliasDB, Usuario usuario) {
		this.aliasDB = aliasDB;
		engine = new PersistenceEngine(aliasDB, getTableName(), getBean(), getFields(), usuario);
		this.usuario = usuario;
	}

	public DaoTransactDefaultImpl(TransactionControler tc, Usuario usuario) throws InvalidTransactionControler {
		if (!(tc instanceof TransactionControlerImpl)) {
			throw new InvalidTransactionControler("Invalid Transaction Controler. Expecting TransactionControlerImpl");
		}
		this.aliasDB = tc.getDataBaseAlias();
		engine = new PersistenceEngine(tc, getTableName(), getBean(), getFields(), usuario);
		this.usuario = usuario;
	}

	/**
	 * Obtém o DataSource baseado no JNDI configurado.
	 * 
	 * @return o DataSource de conexão vinculado ao JNDI configurado.
	 * @throws Exception
	 */
	protected DataSource getDataSource() {
		try {
			return (DataSource) new InitialContext().lookup(Constantes.getValue("CONTEXTO_CONEXAO") + getAliasDB());
		} catch (NamingException ne) {
			System.out.println("\tNão foi possível obter DataSource: " + getAliasDB());
			ne.printStackTrace();
		}
		return null;
	}

	protected List execSQL(String sql, Object[] parametros) throws PersistenceException {
		return engine.execSQL(sql, parametros);
	}

	protected int execUpdate(String sql, Object[] parametros) throws PersistenceException {
		return engine.execUpdate(sql, parametros);
	}

	protected List listConvertion(Class classe, List dados, List fields) throws Exception {
		return engine.listConvertion(classe, dados, fields);

	}

	public String getAliasDB() {
		return aliasDB;
	}

	public TransactionControler getTransactionControler() throws Exception {
		return engine.getTransactionControler();
	}

	public void setTransactionControler(TransactionControler tc) throws Exception {
		if (!(tc instanceof TransactionControlerImpl)) {
			throw new InvalidTransactionControler("Invalid Transaction Controler. Expecting TransactionControlerImpl");
		}
		engine.setTransactionControler((TransactionControlerImpl) tc);
	}

	public abstract Class getBean();

	public Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Funcao colocada so para garantir estabilidade do sistema, caso a conexao com o banco de dados permaneca ativa por algum motivo.
	 * 
	 * Este metodo sera chamado quando o Garbage Colector passar e descartar um objeto desta classe.
	 */
	protected void finalize() throws Throwable {
		/*
		 * RETIRADO POR EMAURI!!!!!!!!!!!!!!!!!!!!!!!!!! ESTAVA FECHANDO CONEXOES EM TRANSACOES CORRENTES! try { if (engine.transactionControler != null) { engine.transactionControler.close(); } }
		 * catch (Exception e) { //deixa para la o erro. Eh so pra garantir estabilidade do sistema. } try{ engine.transactionControler = null; }catch (Exception e) { }
		 */
		super.finalize();
	}
}