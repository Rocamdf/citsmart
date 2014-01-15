package br.com.citframework.integracao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.Usuario;
import br.com.citframework.excecao.DuplicateUniqueException;
import br.com.citframework.excecao.InvalidParameterException;
import br.com.citframework.excecao.LogicException;
import br.com.citframework.excecao.MandatoryParameterNotFound;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.Mensagens;
import br.com.citframework.util.Reflexao;
import br.com.citframework.util.SQLConfig;
import br.com.citframework.util.Util;
import br.com.citframework.util.UtilStrings;

public class JdbcEngine {

	protected TransactionControlerImpl transactionControler = null;
	protected Usuario usuario;

	private String dataBaseAlias;

	public JdbcEngine(String alias, Usuario usuario) {
		super();
		this.usuario = usuario;
		dataBaseAlias = alias;
	}

	public JdbcEngine(TransactionControlerImpl tc, Usuario usuario) {
		super();
		this.usuario = usuario;
		this.transactionControler = tc;
	}

	public TransactionControlerImpl getTransactionControler() {
		if (transactionControler == null) {
			transactionControler = new TransactionControlerImpl(dataBaseAlias);
		}
		return transactionControler;
	}

	public void setTransactionControler(
			TransactionControlerImpl transactionControler) {
		this.transactionControler = transactionControler;
	}

	protected List execSQL(String sql, Object[] parametros)
			throws PersistenceException {
		return execSQL(sql, parametros, 0);
	}

	public List execSQL(String sql, Object[] parametros, int maxRows)
			throws PersistenceException {
		if (sql == null || sql.length() == 0) {
			throw new MandatoryParameterNotFound("SQL IS MANDATORY");
		}
		String sqlExecutar = sql; // SQLConfig.traduzSQL(sql);

		TransactionControlerImpl tc = getTransactionControler();

		Connection con = (Connection) tc.getTransactionObject();

		PreparedStatement ps = null;
		try {
			ps = getPreparedStatement(con, sqlExecutar, parametros);
		} catch (Exception e) {
			String params = "";
			if (parametros != null) {
				for (int i = 0; i < parametros.length; i++) {
					params += parametros[i] + ",";
				}
			}
			try {
				if(ps != null){
					ps.close();
				}
			} catch (Exception eX) {
			}
			try {
				con.rollback();
			} catch (Exception eX) {
			}
			try {
				con.close();
			} catch (Exception eX) {
			}
			try {
				tc.close();
			} catch (Exception eX) {
			}
			ps = null;
			con = null;
			tc = null;
			throw new PersistenceException("Select Error: "
					+ SQLConfig.traduzSQL(sql) + "\n\t parameters: " + params
					+ " \n\t*** ERROR: " + e.getMessage(), e);
		}

		ResultSet rs = null;
		try {
			if (maxRows != 0) {
				ps.setMaxRows(maxRows);
			}
			if (con == null || con.isClosed()) {
				con = (Connection) tc.getTransactionObject();
			}
			if (ps == null) {
				ps = getPreparedStatement(con, sqlExecutar, parametros);
			}
			rs = ps.executeQuery();
		} catch (SQLException e) {
			boolean bErroContinua = true;
			/*
			 * try{ if (e.getMessage().indexOf("Socket") > -1 ||
			 * e.getMessage().indexOf("closed") > -1){ if (con == null ||
			 * con.isClosed()){ con = (Connection) tc.getTransactionObject(); }
			 * try{ ps = getPreparedStatement(con, sqlExecutar, parametros); rs
			 * = ps.executeQuery(); bErroContinua = false; }catch (Exception eX)
			 * { } } }catch (Exception eX2) { }
			 */
			if (bErroContinua) {
				String params = "";
				if (parametros != null) {
					for (int i = 0; i < parametros.length; i++) {
						params += parametros[i] + ",";
					}
				}

				try {
					ps.close();
				} catch (Exception eX) {
				}
				try {
					con.rollback();
				} catch (Exception eX) {
				}
				try {
					con.close();
				} catch (Exception eX) {
				}
				try {
					tc.close();
				} catch (Exception eX) {
				}
				rs = null;
				ps = null;
				con = null;
				tc = null;
				throw new PersistenceException("Select Error: "
						+ SQLConfig.traduzSQL(sql) + "\n\t parameters: "
						+ params + " \n\t*** ERROR: " + e.getMessage(), e);
			}
		}
		try {
			int colunas = 0;
			if(rs != null){
				colunas = rs.getMetaData().getColumnCount();
			}
			List result = new ArrayList();
			
			if(rs != null){
				while (rs.next()) {
					Object o = null;
					Object[] row = new Object[colunas];
					for (int i = 0; i < colunas; i++) {
						if (rs.getMetaData().getColumnType(i + 1) != Types.TIMESTAMP)
							o = rs.getObject(i + 1);
						else{
							try{
								o = rs.getTimestamp(i + 1);
							}catch(Exception e){
								e.printStackTrace();
								try{
									o = rs.getObject(i + 1);
								}catch(Exception e1){
									e1.printStackTrace();
									o = null;
								}
							}
						}
						if (o != null
								&& ("com.ibm.db2.jcc.c.bs".equalsIgnoreCase(o
										.getClass().getName())
										|| o.getClass().getName().toUpperCase()
												.indexOf("CLOB") != -1 || o
										.getClass().getName().toUpperCase()
										.indexOf("VB") != -1)) {
							row[i] = rs.getString(i + 1);
						} else if (o != null
								&& ("com.ibm.db2.jcc.am.ie".equalsIgnoreCase(o
										.getClass().getName())
										|| o.getClass().getName().toUpperCase()
												.indexOf("CLOB") != -1 || o
										.getClass().getName().toUpperCase()
										.indexOf("VB") != -1)) {
							row[i] = rs.getString(i + 1);
						} else if (o != null
								&& ("com.ibm.db2.jcc.b.cc".equalsIgnoreCase(o
										.getClass().getName())
										|| o.getClass().getName().toUpperCase()
												.indexOf("CLOB") != -1 || o
										.getClass().getName().toUpperCase()
										.indexOf("VB") != -1)) {
							row[i] = rs.getString(i + 1);
						} else
							row[i] = o;
						/**
						 * Retirado e substituido pelas instrucoes acima. EMAURI.
						 * 26/11/2008. if (o != null &&
						 * (o.getClass().getName().toUpperCase().indexOf("CLOB") >
						 * -1 || o.getClass().getName().toUpperCase().indexOf("VB")
						 * > -1)) { row[i] = rs.getString(i + 1); } else row[i] = o;
						 */
					}
					result.add(row);
				}
			}
			try {
				if(rs != null){
					rs.close();
				}
			} catch (Exception eX) {
			}
			try {
				ps.close();
			} catch (Exception eX) {
			}
			rs = null;
			ps = null;
			
			// Forma sugerida pelo Emauri
			if (!tc.isStarted()) {
				tc.start();
				try {
					tc.commit();
				} catch (Exception eX) {
				}
			
				try {
					if(con != null)
						con.close();
				} catch (Exception e) {
				}
				con = null;
				try {
					tc.close();
				} catch (Exception eX) {
				}
				tc = null;
			} 

			return result;
		} catch (Exception e) {
			try {
				if(rs != null){
					rs.close();
				}
			} catch (Exception eX) {
			}
			try {
				if(ps != null){
					ps.close();
				}
			} catch (Exception eX) {
			}
			try {
				tc.rollback();
			} catch (Exception eX) {
			}
			try {
				con.close();
			} catch (Exception eX) {
			}
			try {
				tc.close();
			} catch (Exception eX) {
			}
			rs = null;
			ps = null;
			con = null;
			tc = null;
			throw new PersistenceException(e);
		}

	}

	public int execUpdate(String sql, Object[] parametros)
			throws PersistenceException {
		
		if (sql == null || sql.length() == 0) {
			throw new MandatoryParameterNotFound("SQL IS MANDATORY");
		}
		String sqlExecutar = SQLConfig.traduzSQL(sql);

		TransactionControlerImpl tc = getTransactionControler();

		Connection con = (Connection) tc.getTransactionObject();
		String strSGBDPrincipal = null;
		if (strSGBDPrincipal == null){
			strSGBDPrincipal = CITCorporeUtil.SGBD_PRINCIPAL;
			strSGBDPrincipal = UtilStrings.nullToVazio(strSGBDPrincipal).trim();
		}		
		PreparedStatement ps = null;
		try {
/*			if (strSGBDPrincipal.equalsIgnoreCase("MYSQL")){
				ps = setCommit(con);
				ps.execute();
			}*/			
			ps = getPreparedStatement(con, sqlExecutar, parametros);
		} catch (Exception e) {
			String params = "";
			if (parametros != null) {
				for (int i = 0; i < parametros.length; i++) {
					params += parametros[i] + ",";
				}
			}
			try {
				if(ps != null){
					ps.close();
				}
			} catch (Exception eX) {
			}
			ps = null;
			try {
				con.rollback();
			} catch (Exception eX) {
			}
			try {
				con.close();
			} catch (Exception eX) {
			}
			try {
				tc.rollback();
			} catch (Exception eX) {
			}
			try {
				tc.close();
			} catch (Exception eX) {
			}
			con = null;
			// tc = null;
			throw new PersistenceException("Select UPDATE Error " + sqlExecutar
					+ "\n\t parameters: " + params + " \n\t*** ERROR: "
					+ e.getMessage(), e);
		}

		int result;
		try {
			result = ps.executeUpdate();
		} catch (Exception e) {
			String params = "";
			if (parametros != null) {
				for (int i = 0; i < parametros.length; i++) {
					params += parametros[i] + ",";
				}
			}	
			try {
				ps.cancel();
			} catch (Exception eX) {
			}
			try {
				ps.close();
			} catch (Exception eX) {
			}
			ps = null;
			try {
				con.rollback();
			} catch (Exception eX) {
			}
			try {
				con.close();
			} catch (Exception eX) {
			}
			try {
				tc.rollback();
			} catch (Exception eX) {
			}
			try {
				tc.close();
			} catch (Exception eX) {
			}
			con = null;
			// tc = null;
			throw new PersistenceException("Excecution Error " + sqlExecutar
					+ "\n\t parameters: " + params + " \n\t*** ERROR: "
					+ e.getMessage(), e);
		}
		try {
			ps.close();
		} catch (Exception e) {
			// throw new PersistenceException(e);
		}
		ps = null;

		if (!tc.isStarted()) {
			try{
				tc.commit();
			} catch (Exception e) {
			}				
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			con = null;
			try{
				tc.close();		
			} catch (Exception e) {
			}
			// tc = null;
		}

		return result;
	}

	public PreparedStatement getPreparedStatement(Connection con, String sql,
			Object[] parametros) throws PersistenceException {
		PreparedStatement ps = null;

		String sqlExecutar = SQLConfig.traduzSQL(sql);
		try {
			if(con != null && !con.isClosed()){
				ps = con.prepareStatement(sqlExecutar);
			}
		} catch (SQLException e1) {
			try {
				con.rollback();
			} catch (Exception eX) {
			}
			try {
				con.close();
			} catch (Exception eX) {
			}
			con = null;
			throw new PersistenceException(e1);
		}

		String parametro = "";
		String sAux;
		// ParameterMetaData pmd = ps.getParameterMetaData();
		if (parametros != null) {
			for (int i = 0; i < parametros.length; i++) {

				Object valor = parametros[i];
				if (i > 0) {
					parametro += ",";
				}
				parametro += valor;
				// ### NAO HABILITEM ESTA LINHA - SE PRECISAR DESCOMEMTE
				// LOCALMENTE EM SUA MAQUINA!!!! EMAURI.
				// System.out.println("[DATA BASE DAO IMPL] - Valor parametro["
				// + i + "]: " + valor);
				try {
					if (valor == null) {
						// int type = pmd.getParameterType(i + 1);
						// ps.setNull(i + 1, type);
						ps.setObject(i + 1, null);
						// ps.setString(i + 1, null);
					} else {
						if (valor instanceof Integer) {
							ps.setInt(i + 1, ((Integer) valor).intValue());
						} else if (valor instanceof String) {
							sAux = (String) valor;
							// sAux = sAux.toUpperCase();
							ps.setString(i + 1, sAux);
						} else {
							ps.setObject(i + 1, valor);
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						con.rollback();
					} catch (Exception eX) {
					}
					try {
						con.close();
					} catch (Exception eX) {
					}
					con = null;
					throw new InvalidParameterException("Wrong Parameter "
							+ (i + 1) + ". SQL: " + sql + " " + e.getMessage());
				}
			}
		}
		if (Constantes.getValue("DEBUG_PERSISTENCE") != null
				&& Constantes.getValue("DEBUG_PERSISTENCE").trim()
						.equalsIgnoreCase("true")) {

			System.out.println("[DEBUG PERSISTENCE] SQL: " + sql);
			System.out.println("[DEBUG PERSISTENCE] PARAMS: " + parametro);

		}
		return ps;

	}

	public List listConvertion(Class classe, List dados, List fields)
			throws Exception {
		if (dados == null || dados.size() == 0)
			return new ArrayList(0);

		List result = new ArrayList(dados.size());
		Iterator it = dados.iterator();
		while (it.hasNext()) {
			Object obj = classe.newInstance();
			Object[] row = (Object[]) it.next();
			for (int i = 0; i < fields.size(); i++) {
				// System.out.println(">>> [DataBaseDAOImpl] Setando: " +
				// fields.get(i).toString() + " -> " + row[i]);
				String atributoClasse = "";
				if (fields.get(i) instanceof Field) {
					atributoClasse = ((Field) fields.get(i)).getFieldClass();

				} else {
					atributoClasse = fields.get(i).toString();
				}

				Reflexao.setPropertyValue(obj, atributoClasse, row[i]);

			}
			result.add(obj);
		}
		return result;
	}

	protected void validRelationship(String nomeTabela, String[] campos,
			Object[] valores, String aliasTabela) throws Exception,
			LogicException {

		String sql = "select * from " + nomeTabela;

		for (int i = 0; i < campos.length; i++) {

			if (i == 0) {
				sql += " where ";
			} else {
				sql += " and ";
			}

			sql += campos[i] + " = ? ";

		}
		TransactionControlerImpl tc = getTransactionControler();

		Connection con = (Connection) tc.getTransactionObject();
		PreparedStatement ps = getPreparedStatement(con, sql, valores);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			rs.close();
			ps.close();
			if (!tc.isStarted())
				tc.close();

			tc = null;
			throw new LogicException(Mensagens.getValue("MSG08") + " "
					+ aliasTabela);
		}
		rs.close();
		ps.close();
		rs = null;
		ps = null;
		if (!tc.isStarted())
			tc.close();

		// tc = null;
	}

	protected void validUniqueKey(String nomeTabela, String campo,
			String msgRetorno, Object valor, List camposChave, List valoresChave)
			throws Exception {

		if (valor == null) {

			return;

		}
		String sql = "select * from " + nomeTabela + " where ";

		List params = new ArrayList();

		if ((valor instanceof String)) {

			sql += Util.comparacaoSQLString(campo, "=", valor.toString(),
					params);

		} else {

			sql += campo + " = ? ";
			params.add(valor);

		}

		if (camposChave != null && valoresChave != null) {
			for (int i = 0; i < camposChave.size(); i++) {
				Field cmp = (Field) camposChave.get(i);
				if (valoresChave.get(i) != null) {
					sql += " and " + cmp.getFieldDB() + " <> ? ";
					params.add(valoresChave.get(i));
				}
			}
		}

		TransactionControlerImpl tc = getTransactionControler();

		Connection con = (Connection) tc.getTransactionObject();
		PreparedStatement ps = getPreparedStatement(con, sql, params.toArray());
		ResultSet rs = null;
		try {
			rs = ps.executeQuery();
			if (rs.next()) {
				if (msgRetorno != null) {
					throw new DuplicateUniqueException(	Mensagens.getValue("MSG12") + " " + msgRetorno);
				} else {
					throw new DuplicateUniqueException(	Mensagens.getValue("MSG12") + " " + campo);
				}
			}

		} catch (DuplicateUniqueException e) {
			throw e;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("SQL ERROR: " + sql);
		} finally {
			if (rs != null)
				rs.close();

			if (ps != null)
				ps.close();

			if (!tc.isStarted()) {
				tc.close();
				con.close();
				con = null;
			}
			// tc = null;
		}

	}

	protected String getDataBaseAlias() {
		return dataBaseAlias;
	}

	protected void setDataBaseAlias(String dataBaseAlias) {
		this.dataBaseAlias = dataBaseAlias;
	}

	/**
	 * Funcao colocada so para garantir estabilidade do sistema, caso a conexao
	 * com o banco de dados permaneca ativa por algum motivo.
	 * 
	 * Este metodo sera chamado quando o Garbage Colector passar e descartar um
	 * objeto desta classe.
	 */
	protected void finalize() throws Throwable {
		/*
		 * RETIRADO POR EMAURI!!!!!!!!!!!!!!!!!!!!!!!!!! ESTAVA FECHANDO
		 * CONEXOES EM TRANSACOES CORRENTES! try { if (transactionControler !=
		 * null) { transactionControler.close(); } } catch (Exception e) {
		 * //deixa para la o erro. Eh so pra garantir estabilidade do sistema. }
		 * transactionControler = null;
		 */
		super.finalize();
	}

}
