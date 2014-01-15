package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.bean.UsuarioDTO;
import br.com.centralit.citcorpore.util.Enumerados.ParametroSistema;
import br.com.centralit.citcorpore.util.ParametroUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.FKReferenceException;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.UtilStrings;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PersistenceEngine extends JdbcEngine {

	private PersistenceUtil persistenceUtil;
	private String tableName;
	private Class bean;
	private Collection fields;
	private int maxRows = 0;
	private static UsuarioDTO usuarioSessao;

	private static final HashMap tabelaIdentificador = new HashMap();

	public static void setUsuarioSessao(UsuarioDTO u) {
		usuarioSessao = u;
	}

	public int getMaxRows() {
		return maxRows;
	}

	public void setMaxRows(int maxRows) {
		this.maxRows = maxRows;
	}

	public PersistenceEngine(String alias, String tableName, Class bean, Collection fields, Usuario usuario) {
		super(alias, usuario);

		this.tableName = tableName;
		this.bean = bean;
		this.fields = fields;
		persistenceUtil = new PersistenceUtil(tableName, bean, fields);

	}

	public PersistenceEngine(TransactionControler tc, String tableName, Class bean, Collection fields, Usuario usuario) {

		super((TransactionControlerImpl) tc, usuario);

		this.tableName = tableName;
		this.bean = bean;
		this.fields = fields;
		persistenceUtil = new PersistenceUtil(tableName, bean, fields);

	}

	public Object create(Object obj) throws PersistenceException {

		boolean trans = false;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			validUniqueKey(obj);
			setNextKey(obj);
			SqlConfiguration conf = persistenceUtil.getConfigurationCreate(obj);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}

			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				new Thread(new RegistraLog(obj, "I", this.persistenceUtil, usuarioSessao, tableName)).start();
			}

			return obj;
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}
			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
				tc.close();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}

	}

	public Object createWithID(Object obj) throws PersistenceException {

		boolean trans = false;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			validUniqueKey(obj);
			SqlConfiguration conf = persistenceUtil.getConfigurationCreate(obj);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}

			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				new Thread(new RegistraLog(obj, "I", this.persistenceUtil, usuarioSessao, tableName)).start();
			}

			return obj;
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}
			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
				tc.close();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}

	}

	public void update(Object obj) throws PersistenceException {

		boolean trans = false;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			validUniqueKey(obj);
			SqlConfiguration conf = persistenceUtil.getConfigurationUpdateAll(obj);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}

			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				new Thread(new RegistraLog(obj, "A", this.persistenceUtil, usuarioSessao, tableName)).start();
			}
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}
			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}

	}

	public void updateNotNull(Object obj) throws PersistenceException {

		boolean trans = false;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationUpdateNotNull(obj);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}
			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				new Thread(new RegistraLog(obj, "A", this.persistenceUtil, usuarioSessao, tableName)).start();
			}
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}
			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}

	}

	public void delete(Object obj) throws PersistenceException {

		boolean trans = false;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationDelete(obj);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}
			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				new Thread(new RegistraLog(obj, "E", this.persistenceUtil, usuarioSessao, tableName)).start();
			}
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}

			if (e.getMessage().indexOf(".FK") > -1) {
				throw new FKReferenceException("Operação cancelada: Registro com referência em outra(s) tabela(s)");
			}
			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}

	}

	public Object restore(Object obj) throws PersistenceException {

		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationRestore(obj);
			Object[] params = conf.getParamentros();
			List lista = execSQL(conf.getSql(), params, getMaxRows());
			if (lista == null || lista.size() == 0) {
				return null;
			}
			List result = listConvertion(getBean(), lista, conf.getCamposRetorno());
			return result.get(0);
		} catch (PersistenceException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			/*
			 * if (!getTransactionControler().isStarted()) {
			 * 
			 * getTransactionControler().close(); }
			 */
		}
	}

	public Collection list(List ordenacao) throws PersistenceException {

		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationList(ordenacao);
			List lista = execSQL(conf.getSql(), null, maxRows);
			List result = listConvertion(getBean(), lista, conf.getCamposRetorno());
			return result;
		} catch (PersistenceException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			/*
			 * if (!getTransactionControler().isStarted()) {
			 * 
			 * getTransactionControler().close(); }
			 */
		}

	}

	private Integer getNextKey(String nomeTabela, String nomeCampo) throws Exception {
		// String sql = "select S_"+nomeTabela.toUpperCase()+".nextval from
		// dual";

		String sql = Constantes.getValue("FUNC_NEXT_KEY");
		sql = sql.replaceAll("<FIELD>", nomeCampo).replaceAll("<TABLE>", nomeTabela);
		/*
		 * if (DBConstantes.OWNER_SYSTEM.trim().length() > 0) sql = "SELECT NEXTVAL FOR " + DBConstantes.OWNER_SYSTEM + ".S_" + nomeTabela.toUpperCase() + " FROM " + nomeTabela; else sql =
		 * "SELECT NEXTVAL FOR S_" + nomeTabela.toUpperCase() + " FROM " + nomeTabela;
		 */
		try {
			List lista = execSQL(sql, null, maxRows);
			if (lista == null || lista.size() == 0)
				return 1;
			if (((Object[]) lista.get(0))[0] == null) {
				return 1;
			}
			Integer key = new Integer(((Object[]) lista.get(0))[0].toString());
			return key.intValue() + 1;
		} catch (Exception e) {

			throw new Exception("Erro ao recuperar Next Key : sql - " + sql, e);
		}

	}

	private synchronized void setNextKey(Object obj) throws Exception {

		if (persistenceUtil.getCampoSequencial() != null) {
			Iterator it = persistenceUtil.getCampoSequencial().iterator();
			while (it.hasNext()) {
				Field field = (Field) it.next();
				// System.out.println("persistenceUtil.getCampoSequencial() --> "
				// + field.getFieldDB());

				Integer keyOfTable = getNextKey(tableName.trim().toLowerCase(), field.getFieldDB());

				if (tabelaIdentificador.containsKey(tableName.trim().toLowerCase())) {

					if (this.existeKeyTable(keyOfTable)) {

						while (true) {
							keyOfTable++;

							if (this.existeKeyTable(keyOfTable)) {
								continue;
							} else {

								tabelaIdentificador.remove(tableName.trim().toLowerCase());

								tabelaIdentificador.put(tableName.trim().toLowerCase(), keyOfTable);

								break;
							}
						}

					} else {

						tabelaIdentificador.remove(tableName.trim().toLowerCase());

						tabelaIdentificador.put(tableName.trim().toLowerCase(), keyOfTable);

					}

				} else {

					tabelaIdentificador.put(tableName.trim().toLowerCase(), keyOfTable);
				}

				Reflexao.setPropertyValueAsString(obj, field.getFieldClass(), keyOfTable + "");
			}

		}

	}

	private boolean existeKeyTable(Integer keyOfTable) {

		Integer keyAtual = (Integer) tabelaIdentificador.get(tableName.trim().toLowerCase());

		if (keyOfTable <= keyAtual || tabelaIdentificador.get(tableName.trim().toLowerCase()).toString().equalsIgnoreCase(keyOfTable.toString())) {

			return true;
		}

		return false;
	}

	/**
	 * Faz a busca dos dados atraves dos campos que estiverem preenchidos no bean, ou seja que nao estejam nulos. O segundo parametro permite ordenacao.
	 * 
	 * ## IMPORTANTE: ## O segundo parametro eh uma lista de String com o nome dos campos na classe que devem ser ordenados. Exemplo: List lst = new ArrayList(); lst.add("nomeUf"); lst.add("siglaUf");
	 * 
	 * findNotNull(obj, lst);
	 * 
	 * @param obj
	 * @param ordenacao
	 * @return
	 * @throws PersistenceException
	 */
	public Collection findNotNull(Object obj, List ordenacao) throws PersistenceException {
		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationFindNotNull(obj, ordenacao);
			Object[] params = conf.getParamentros();
			List lista = execSQL(conf.getSql(), params, maxRows);
			if (lista == null || lista.size() == 0) {
				return null;
			}
			List result = listConvertion(getBean(), lista, conf.getCamposRetorno());
			return result;
		} catch (PersistenceException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			/*
			 * if (!getTransactionControler().isStarted()) { getTransactionControler().close(); }
			 */
		}
	}

	public Collection findByCondition(List condicao, List ordenacao) throws PersistenceException {

		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationFindByCondition(condicao, ordenacao);
			Object[] params = conf.getParamentros();
			List lista = execSQL(conf.getSql(), params, maxRows);
			if (lista == null || lista.size() == 0) {
				return null;
			}
			List result = listConvertion(getBean(), lista, conf.getCamposRetorno());
			return result;
		} catch (PersistenceException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			/*
			 * if (!getTransactionControler().isStarted()) {
			 * 
			 * getTransactionControler().close(); }
			 */
		}

	}

	public int updateByCondition(Object obj, List condicao) throws PersistenceException {
		boolean trans = false;
		int result = 0;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationUpdateAllByCondition(obj, condicao);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			result = execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}
			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				new Thread(new RegistraLog(obj, "A", this.persistenceUtil, usuarioSessao, tableName)).start();
			}
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}
			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}

		return result;

	}

	public Collection getResultByConfiguration(SqlConfiguration sqlconf) throws PersistenceException {

		try {
			SqlConfiguration conf = sqlconf;
			Object[] params = conf.getParamentros();
			List lista = execSQL(conf.getSql(), params, maxRows);
			if (lista == null || lista.size() == 0) {
				return null;
			}
			List result = listConvertion(getBean(), lista, conf.getCamposRetorno());
			return result;
		} catch (PersistenceException e) {
			throw e;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			/*
			 * if (!getTransactionControler().isStarted()) {
			 * 
			 * getTransactionControler().close(); }
			 */
		}
	}

	public int updateNotNullByCondition(Object obj, List condicao) throws PersistenceException {
		boolean trans = false;
		int result = 0;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationUpdateNotNullByCondition(obj, condicao);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			result = execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}
			if (UtilStrings.nullToVazio(ParametroUtil.getValorParametroCitSmartHashMap(ParametroSistema.USE_LOG, "false")).equalsIgnoreCase("true")) {
				new Thread(new RegistraLog(obj, "A", this.persistenceUtil, usuarioSessao, tableName)).start();
			}
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}
			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}
		return result;

	}

	public int deleteByCondition(List condicao) throws PersistenceException {
		boolean trans = false;
		int result = 0;
		TransactionControlerImpl tc = getTransactionControler();
		try {
			SqlConfiguration conf = persistenceUtil.getConfigurationDeleteByCondition(condicao);
			trans = tc.isStarted();
			if (!trans) {
				tc.start();
			}
			Object[] params = conf.getParamentros();
			result = execUpdate(conf.getSql(), params);
			if (!trans) {
				tc.commit();
			}
			// registraLog(obj, "E");
		} catch (PersistenceException e) {
			if (!trans) {
				tc.rollback();
			}

			if (e.getMessage().indexOf(".FK") > -1) {
				throw new FKReferenceException("Operação cancelada: Registro com referência em outra(s) tabela(s)");
			}

			throw e;
		} catch (Exception e) {
			if (!trans) {
				tc.rollback();
			}
			throw new PersistenceException(e);
		} finally {
			if (!trans) {
				try {
					tc.close();
				} catch (Exception e) {
				}
				tc = null;
			}
		}

		return result;

	}

	private void validUniqueKey(Object obj) throws Exception {
		if (persistenceUtil.getUniqueFields() != null && persistenceUtil.getUniqueFields().size() > 0) {
			List valoresChave = getValoresChave(obj);
			Iterator it = persistenceUtil.getUniqueFields().iterator();
			while (it.hasNext()) {
				Field cmp = (Field) it.next();
				validUniqueKey(persistenceUtil.getNomeTabela(), cmp.getFieldDB(), cmp.getMsgReturn(), Reflexao.getPropertyValue(obj, cmp.getFieldClass()), persistenceUtil.getCamposChave(),
						valoresChave);

			}
		}
	}

	private List getValoresChave(Object obj) throws Exception {

		List valores = new ArrayList();
		Iterator it = persistenceUtil.getCamposChave().iterator();
		while (it.hasNext()) {
			Field cmp = (Field) it.next();
			valores.add(Reflexao.getPropertyValue(obj, cmp.getFieldClass()));

		}
		return valores;

	}

	// persistencia
	public String getTableName() {
		return tableName;
	}

	public Collection getFields() {
		return fields;
	}

	public Class getBean() {
		return bean;

	}

	/**
	 * Funcao colocada so para garantir estabilidade do sistema, caso a conexao com o banco de dados permaneca ativa por algum motivo.
	 * 
	 * Este metodo sera chamado quando o Garbage Colector passar e descartar um objeto desta classe.
	 */
	protected void finalize() throws Throwable {
		/*
		 * RETIRADO POR EMAURI!!!!!!!!!!!!!!!!!!!!!!!!!! ESTAVA FECHANDO CONEXOES EM TRANSACOES CORRENTES! try { if (transactionControler != null) { transactionControler.close(); } } catch (Exception
		 * e) { //deixa para la o erro. Eh so pra garantir estabilidade do sistema. } transactionControler = null;
		 */
		super.finalize();
	}
}
