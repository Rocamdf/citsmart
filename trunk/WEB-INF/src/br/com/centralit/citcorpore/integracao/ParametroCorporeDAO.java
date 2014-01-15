/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.ParametroCorporeDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;

/**
 * @author valdoilo.damasceno
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ParametroCorporeDAO extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 7531091932858241783L;

	private static final String SQL_GET_PARAMETRO_CITCORPORE = "SELECT IDPARAMETROCORPORE, NOMEPARAMETROCORPORE, VALOR, IDEMPRESA, DATAINICIO, DATAFIM FROM parametrocorpore ";

	public ParametroCorporeDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDPARAMETROCORPORE", "id", true, false, false, true));
		listFields.add(new Field("NOMEPARAMETROCORPORE", "nome", false, false, false, true, "Nome Parâmetro!"));
		listFields.add(new Field("VALOR", "valor", false, false, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false, false));
		listFields.add(new Field("DATAINICIO", "dataInicio", false, false, false, false));
		listFields.add(new Field("DATAFIM", "dataFim", false, false, false, false));
		listFields.add(new Field("tipodado", "tipoDado", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "PARAMETROCORPORE";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("id"));
		return super.list(list);
	}

	/**
	 * @param id
	 *            Integer IdParamentro do Sistema
	 * @param NomeParametro
	 *            String Nome Paramentro do Sistema
	 * @return Lista
	 * @throws Exception
	 * @author Maycon.Fernandes
	 */
	public ParametroCorporeDTO getParamentroAtivo(Integer id) throws Exception {
		List objs = new ArrayList();
		objs.add(id);

		String sql = SQL_GET_PARAMETRO_CITCORPORE;

		sql += " WHERE ";
		sql += " (IDPARAMETROCORPORE = ?) AND (DATAFIM IS NULL) ";

		/*
		 * if (NomeParametro.length() > 0) { sql += " AND  UPPER(NOMEPARAMETROCORPORE) = ? "; objs.add(NomeParametro.toUpperCase()); }
		 */

		sql += " ORDER BY NOMEPARAMETROCORPORE, DATAINICIO DESC, DATAFIM";
		List lista = this.execSQL(sql, objs.toArray());

		List listRetorno = this.prepararListaDeRetorno();

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return (ParametroCorporeDTO) result.get(0);
	}

	public List pesquisarParamentro(Integer id, String NomeParametro) throws Exception {
		List objs = new ArrayList();
		objs.add(id);

		String sql = SQL_GET_PARAMETRO_CITCORPORE;

		sql += " WHERE ";
		sql += " (IDPARAMETROCORPORE = ?) ";

		/*
		 * if (NomeParametro.length() > 0) { sql += " AND  UPPER(NOMEPARAMETROCORPORE) = ? "; objs.add(NomeParametro.toUpperCase()); }
		 */

		sql += " ORDER BY NOMEPARAMETROCORPORE";
		List lista = this.execSQL(sql, objs.toArray());

		List listRetorno = this.prepararListaDeRetorno();

		List result = this.engine.listConvertion(getBean(), lista, listRetorno);
		if (result == null || result.size() == 0)
			return null;
		return result;
	}

	@Override
	public Class getBean() {
		return ParametroCorporeDTO.class;
	}

	private List prepararListaDeRetorno() {
		List listRetorno = new ArrayList();
		listRetorno.add("id");
		listRetorno.add("nome");
		listRetorno.add("valor");
		listRetorno.add("idEmpresa");
		listRetorno.add("dataInicio");
		listRetorno.add("dataFim");
		return listRetorno;
	}

	public Collection findByID(ParametroCorporeDTO parametroCorporeDTO) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();

		condicao.add(new Condition("id", "=", parametroCorporeDTO.getId()));
		ordenacao.add(new Order("id"));
		condicao.add(new Condition(Condition.AND, "dataFim", "is", null));
		return super.findByCondition(condicao, ordenacao);
	}

	@Override
	public void updateNotNull(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}

}
