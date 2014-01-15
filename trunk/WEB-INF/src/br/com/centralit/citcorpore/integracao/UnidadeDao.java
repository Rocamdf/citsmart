package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.UnidadeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class UnidadeDao extends CrudDaoDefaultImpl {

	public UnidadeDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private static final String SQL_NOMEUNIDADE = "select idunidade, nome " + "from unidade where idunidade not in(select idunidade from usuario)";

	private static final String SQL_NOMEEMPREGADO = "select idunidade, nome " + "from unidade where idunidade not in(select idunidade from empregados)";

	private static final long serialVersionUID = -2983316142102074344L;

	public Class getBean() {
		return UnidadeDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("idUnidade", "idUnidade", true, true, false, false));
		// listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));
		listFields.add(new Field("idUnidadePai", "idUnidadePai", false, false, false, false));
		listFields.add(new Field("idTipoUnidade", "idTipoUnidade", false, false, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));
		listFields.add(new Field("dataInicio", "dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim", "dataFim", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("email", "email", false, false, false, false));
        listFields.add(new Field("idEndereco", "idEndereco", false, false, false, false));
        listFields.add(new Field("aceitaEntregaProduto", "aceitaEntregaProduto", false, false, false, false));

		return listFields;
	}

	public Collection findSemPai() throws Exception {
		String sql = "SELECT idUnidade, idUnidadePai, nome, descricao, dataInicio FROM Unidade WHERE idUnidadePai IS NULL AND dataFim IS NULL ORDER BY nome ";
		List colDados = this.execSQL(sql, null);
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idUnidade");
			fields.add("idUnidadePai");
			fields.add("nome");
			fields.add("descricao");
			fields.add("dataInicio");
			return this.listConvertion(UnidadeDTO.class, colDados, fields);
		}
		return null;
	}
	
	public Collection findSemPaiMultContrato(Integer idContrato) throws Exception {
		String sql = "SELECT idUnidade, idUnidadePai, nome, descricao, dataInicio FROM Unidade WHERE idUnidadePai IS NULL AND dataFim IS NULL and idUnidade in "
				+ "(select idUnidade from ContratosUnidades where idContrato = ?) ORDER BY nome ";
		List colDados = this.execSQL(sql, new Object[] { idContrato });
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idUnidade");
			fields.add("idUnidadePai");
			fields.add("nome");
			fields.add("descricao");
			fields.add("dataInicio");
			return this.listConvertion(UnidadeDTO.class, colDados, fields);
		}
		return null;
	}
	
	

	public Collection findByIdPai(Integer idCausaIncidentePaiParm) throws Exception {
		String sql = "SELECT idUnidade, idUnidadePai, nome, descricao, dataInicio FROM Unidade " + "WHERE idUnidadePai = ? AND dataFim IS NULL ORDER BY nome ";
		List colDados = this.execSQL(sql, new Object[] { idCausaIncidentePaiParm });
		if (colDados != null) {
			List fields = new ArrayList();
			fields.add("idUnidade");
			fields.add("idUnidadePai");
			fields.add("nome");
			fields.add("descricao");
			fields.add("dataInicio");
			return this.listConvertion(UnidadeDTO.class, colDados, fields);
		}
		return null;
	}

	public String getTableName() {
		return "Unidade";
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}

	/**
	 * @return
	 * @throws Exception
	 *             Lista idunidade e nome de unidade que não estejam cadastrado na tabela USUARIO
	 */
	public Collection findByIdUnidade() throws Exception {
		String sql = SQL_NOMEUNIDADE;
		List lista = new ArrayList();
		lista = this.execSQL(sql, null);
		List listRetorno = new ArrayList();
		listRetorno.add("idUnidade");
		listRetorno.add("nome");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * @return
	 * @throws Exception
	 *             Lista idunidade e nome de unidade que não estejam cadastrado na tabela EMPREGADOS
	 */
	public Collection findByIdEmpregado() throws Exception {
		String sql = SQL_NOMEEMPREGADO;
		List lista = new ArrayList();
		lista = this.execSQL(sql, null);
		List listRetorno = new ArrayList();
		listRetorno.add("idUnidade");
		listRetorno.add("nome");
		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		return result;
	}

	/**
	 * Verifica se idUnidade informado possui filho.
	 * 
	 * @param idUnidade
	 * @return true - possui; false - não possui
	 * @throws PersistenceException
	 */
	public boolean verificarSeUnidadePossuiFilho(Integer idUnidade) throws PersistenceException {
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		sql.append("SELECT idunidade FROM " + getTableName() + " where idunidadepai = ? and datafim is null ");
		parametro.add(idUnidade);
		List list = execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Verifica se Unidade informada existe.
	 * 
	 * @param unidadeDTO
	 * @return true - existe; false - não existe;
	 * @throws PersistenceException
	 */
	public boolean verificarSeUnidadeExiste(UnidadeDTO unidadeDTO) throws PersistenceException {
		
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT idunidade FROM " + getTableName() + "  WHERE  nome = ? AND datafim IS NULL AND idunidade <> ? ");
		parametro.add(unidadeDTO.getNome());
		parametro.add(unidadeDTO.getIdUnidade() == null ? 0 : unidadeDTO.getIdUnidade());
		
		list = this.execSQL(sql.toString(), parametro.toArray());
		
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
		
	}

	public Collection findByNomeUnidade(UnidadeDTO unidadeDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nome", "=", unidadeDTO.getNome())); 
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		ordenacao.add(new Order("nome"));
		return super.findByCondition(condicao, ordenacao);
	}
	
	/**
	 * Filtra unidade pelo id
	 * 
	 * @param idUnidade
	 * @return Collection
	 * @throws Exception 
	 */
	public Collection findById(Integer idUnidade) throws Exception {
		StringBuffer sql = new StringBuffer();
		List parametro = new ArrayList();
		sql.append("SELECT idunidade,nome FROM " + getTableName() + " where idUnidade = ? and datafim is null ");
		parametro.add(idUnidade);
		List list = execSQL(sql.toString(), parametro.toArray());
		List listRetorno = new ArrayList();
		listRetorno.add("idUnidade");
		listRetorno.add("nome");
		List result = this.engine.listConvertion(getBean(), list, listRetorno);
		return result;
	}
	
}
