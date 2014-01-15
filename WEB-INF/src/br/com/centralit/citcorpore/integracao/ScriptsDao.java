package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ScriptsDTO;
import br.com.centralit.citcorpore.util.CITCorporeUtil;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;
import br.com.citframework.util.SQLConfig;

@SuppressWarnings({ "rawtypes", "serial", "unchecked" })
public class ScriptsDao extends CrudDaoDefaultImpl {

	public ScriptsDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public ScriptsDTO buscaScriptPorId(Integer id) throws Exception {
		ScriptsDTO scriptsDTO = new ScriptsDTO();
		scriptsDTO.setIdScript(id);
		List col = (List) super.find(scriptsDTO, null);
		if (col == null || col.size() == 0)
			return null;
		return (ScriptsDTO) col.get(0);
	}

	public List<ScriptsDTO> buscaScriptsPorIdVersao(Integer idVersao) throws Exception {
		ScriptsDTO scriptsDTO = new ScriptsDTO();
		scriptsDTO.setIdVersao(idVersao);
		return (List) super.find(scriptsDTO, null);
	}

	public ScriptsDTO consultarScript(String nomeScript) throws Exception {
		ScriptsDTO scriptsDTO = new ScriptsDTO();
		scriptsDTO.setNome(nomeScript);
		List col = (List) super.find(scriptsDTO, null);
		if (col != null && col.size() != 0) {
			return (ScriptsDTO) col.get(0);
		}
		return null;
	}

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@Override
	public Class getBean() {
		return ScriptsDTO.class;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDSCRIPT", "idScript", true, true, false, false));
		listFields.add(new Field("IDVERSAO", "idVersao", false, false, false, false));
		listFields.add(new Field("NOME", "nome", false, false, false, false));
		listFields.add(new Field("DESCRICAO", "descricao", false, false, false, false));
		listFields.add(new Field("SQLQUERY", "sqlQuery", false, false, false, false));
		listFields.add(new Field("HISTORICO", "historico", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("TIPO", "tipo", false, false, false, false));

		return listFields;
	}

	/**
	 * Obtêm o próximo valor a ser persistido do campo informado por parâmetro
	 * 
	 * @author Murilo Gabriel Rodrigues
	 */
	public Integer getNextKey(String nomeTabela, String nomeCampo) throws Exception {
		String sql = Constantes.getValue("FUNC_NEXT_KEY");
		sql = sql.replaceAll("<FIELD>", nomeCampo).replaceAll("<TABLE>", nomeTabela);

		try {
			List lista = execSQL(sql, null);
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

	@Override
	public String getTableName() {
		return "SCRIPTS";
	}

	public boolean haScriptDeVersaoComErro() throws Exception {
		String sql = "select * from " + getTableName() + " where idversao is not null and descricao like 'ERRO%'";
		List list = this.execSQL(sql, null);
		return (list != null && !list.isEmpty());
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("dataInicio"));
		return super.list(list);
	}

	public void marcaErrosScriptsComoCorrigidos() throws Exception {
		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER))
			execUpdate("update " + getTableName() + " set descricao = 'CORRIGIDO ' + convert(varchar,descricao) where idversao is not null and descricao like 'ERRO%' and idscript > 0 ", null);
		else
			execUpdate("update " + getTableName() + " set descricao = concat('CORRIGIDO ', descricao) where idversao is not null and descricao like 'ERRO%' and idscript > 0 ", null);
	}

	public boolean temScriptsAtivos(ScriptsDTO script) throws Exception {
		List parametro = new ArrayList();
		List list;

		String sql = "select idscript From " + getTableName() + " where ";
		if (script != null && script.getNome() != null && !script.getNome().isEmpty()) {
			sql += " nome = ? and";
			parametro.add(script.getNome());
		}
		sql += " dataFim is null ";
		if (script != null && script.getIdScript() != null) {
			sql += " and idscript <> " + script.getIdScript();
		}

		list = this.execSQL(sql, parametro.toArray());
		return (list != null && !list.isEmpty());
	}

	public String testaPermissaoCriacaoTabela() {
		String retorno = "sucesso";
		try {
			StringBuilder sql = new StringBuilder();

			if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.MYSQL)) {
				sql.append("CREATE TABLE tabelatestepermissao ( ");
				sql.append("     idtabelatestepermissao INT(11) NOT NULL PRIMARY KEY, ");
				sql.append("     colunatexto            VARCHAR(256) ");
				sql.append(") ");
				sql.append("engine = innodb");
			} else if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.POSTGRESQL)) {
				sql.append("CREATE TABLE tabelatestepermissao ( ");
				sql.append("     idtabelatestepermissao INTEGER NOT NULL, ");
				sql.append("     colunatexto            VARCHAR(256) NULL, ");
				sql.append("     PRIMARY KEY (idtabelatestepermissao) ");
				sql.append(") ");
			} else if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.ORACLE)) {
				sql.append("CREATE TABLE tabelatestepermissao ( ");
				sql.append("	 idtabelatestepermissao NUMBER(10, 0) NOT NULL, ");
				sql.append("	 colunatexto            VARCHAR2(256) NULL ");
				sql.append(") ");
			} else if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
				sql.append("CREATE TABLE tabelatestepermissao ( ");
				sql.append("     idtabelatestepermissao INTEGER NOT NULL PRIMARY KEY, ");
				sql.append("     colunatexto            VARCHAR(256) ");
				sql.append(")");
			}

			execUpdate(sql.toString(), null);
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public String testaPermissaoInsercaoRegistroTabela() {
		String retorno = "sucesso";
		try {
			execUpdate("INSERT INTO tabelatestepermissao (idtabelatestepermissao, colunatexto) VALUES (1, 'conteudo')", null);
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public String testaPermissaoConsultaTabela() {
		String retorno = "sucesso";
		try {
			execSQL("SELECT * FROM parametrocorpore", null);
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public String testaPermissaoExclusaoRegistroTabela() {
		String retorno = "sucesso";
		try {
			execUpdate("DELETE FROM tabelatestepermissao WHERE idtabelatestepermissao = 1", null);
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public String testaPermissaoCriacaoColuna() {
		String retorno = "sucesso";
		try {
			if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.MYSQL) || CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.POSTGRESQL)) {
				execUpdate("ALTER TABLE tabelatestepermissao ADD COLUMN colunaadicionada VARCHAR(256) NULL", null);
			} else if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.ORACLE)) {
				execUpdate("ALTER TABLE tabelatestepermissao ADD (colunaadicionada VARCHAR2(256) NULL)", null);
			} else if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
				execUpdate("ALTER TABLE tabelatestepermissao ADD colunaadicionada VARCHAR(256) NULL", null);
			}
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public String testaPermissaoAlteracaoColuna() {
		String retorno = "sucesso";
		try {
			if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.MYSQL)) {
				execUpdate("ALTER TABLE tabelatestepermissao CHANGE COLUMN colunaadicionada colunaadicionadaalterada VARCHAR(256) NULL", null);
			} else if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.POSTGRESQL) || CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.ORACLE)
					|| CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
				execUpdate("ALTER TABLE tabelatestepermissao RENAME COLUMN colunaadicionada TO colunaadicionadaalterada", null);
			}
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public String testaPermissaoExclusaoColuna() {
		String retorno = "sucesso";
		try {
			execUpdate("ALTER TABLE tabelatestepermissao DROP COLUMN colunatexto", null);
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public String testaPermissaoExclusaoTabela() {
		String retorno = "sucesso";
		try {
			execUpdate("DROP TABLE tabelatestepermissao", null);
		} catch (PersistenceException e) {
			retorno = e.getMessage();
		}
		return retorno;
	}

	public List<ScriptsDTO> pesquisaScriptsComFaltaPermissao() throws Exception {
		String descricao = null;
		if (CITCorporeUtil.SGBD_PRINCIPAL.equalsIgnoreCase(SQLConfig.SQLSERVER)) {
			// PARA O SQLSERVER, O QUE DEFINE O CASE INSENSITIVE DA COLUNA É O COLLATE SQL_Latin1_General_CP1_CI_AS
			descricao = "descricao COLLATE SQL_Latin1_General_CP1_CI_AS";
		} else {
			descricao = "UPPER(descricao)";
		}

		StringBuilder sql = new StringBuilder();
		sql.append("SELECT descricao, ");
		sql.append("       sqlquery ");
		sql.append("FROM   scripts ");
		sql.append("WHERE  idversao IS NOT NULL ");
		sql.append("       AND ").append(descricao).append(" LIKE 'ERRO%' ");
		sql.append("       AND ( ").append(descricao).append(" LIKE '%COMMAND DENIED%' ");
		sql.append("              OR ").append(descricao).append(" LIKE '%PERMISSION DENIED%' ");
		sql.append("              OR ").append(descricao).append(" LIKE '%PERMISSÃO NEGADA%' ");
		sql.append("              OR ").append(descricao).append(" LIKE '%MUST BE OWNER%' ");
		sql.append("              OR ").append(descricao).append(" LIKE '%DEVE SER O DONO%' ");
		sql.append("              OR ").append(descricao).append(" LIKE '%INSUFFICIENT PRIVILEGES%' ");
		sql.append("              OR ").append(descricao).append(" LIKE '%NO PRIVILEGES%' )");

		List list = this.execSQL(sql.toString(), null);

		List fields = new ArrayList();
		fields.add("descricao");
		fields.add("sqlQuery");

		List<ScriptsDTO> listaRetorno = new ArrayList<ScriptsDTO>();

		if (list != null && !list.isEmpty()) {
			listaRetorno = this.listConvertion(getBean(), list, fields);
		}

		return listaRetorno;
	}

	public boolean verificaExistenciaTabela(String tabela) {
		boolean tabelaExiste = false;
		try {
			execSQL("SELECT * FROM " + tabela, null);
			tabelaExiste = true;
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return tabelaExiste;
	}

	public boolean verificaExistenciaColuna(String tabela, String coluna) {
		boolean colunaExiste = false;
		try {
			execSQL("SELECT " + coluna + " FROM " + tabela, null);
			colunaExiste = true;
		} catch (PersistenceException e) {
			e.printStackTrace();
		}
		return colunaExiste;
	}

}
