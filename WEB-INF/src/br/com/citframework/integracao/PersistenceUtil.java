package br.com.citframework.integracao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.citframework.util.Reflexao;

public class PersistenceUtil {

	private String nomeTabela;
	private Class nomeClasse;
	private Collection campos;
	private List camposCreate;
	private List camposChave;
	private List camposUpdate;
	private List camposSequencial;
	private List uniqueFields;

	public PersistenceUtil(String nomeTabela, Class nomeClasse, Collection campos) {
		super();
		this.nomeTabela = nomeTabela;
		this.nomeClasse = nomeClasse;
		this.campos = campos;
		configuraCampos();
	}

	protected List getUniqueFields() {
		return uniqueFields;
	}

	protected void setUniqueFields(List uniqueFields) {
		this.uniqueFields = uniqueFields;
	}

	protected List getCamposChave() {
		return camposChave;
	}

	protected void setCamposChave(List camposChave) {
		this.camposChave = camposChave;
	}

	protected List getCamposCreate() {
		return camposCreate;
	}

	protected void setCamposCreate(List camposCreate) {
		this.camposCreate = camposCreate;
	}

	protected List getCampoSequencial() {
		return camposSequencial;
	}

	protected void setCampoSequencial(List campoSequencial) {
		this.camposSequencial = campoSequencial;
	}

	protected List getCamposUpdate() {
		return camposUpdate;
	}

	protected void setCamposUpdate(List camposUpdate) {
		this.camposUpdate = camposUpdate;
	}

	protected Collection getCampos() {
		return campos;
	}

	protected void setCampos(Collection campos) {
		this.campos = campos;
	}

	protected Class getNomeClasse() {
		return nomeClasse;
	}

	protected void setNomeClasse(Class nomeClasse) {
		this.nomeClasse = nomeClasse;
	}

	protected String getNomeTabela() {
		return nomeTabela;
	}

	protected void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	// Metodos utilitario
	public SqlConfiguration getConfigurationCreate(Object obj) throws Exception {
		String sql = "INSERT INTO " + getNomeTabela() + " ";
		String campos = "";
		String valores = "";
		// Object[] parametros = new Object[camposCreate.size()];
		List listaParametros = new ArrayList();
		for (int i = 0; i < camposCreate.size(); i++) {
			Field cmp = (Field) camposCreate.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());

			if (i > 0) {
				campos += ",";
				valores += ",";
			}
			campos += cmp.getFieldDB();
			if (valor != null) {
				valores += "?";
				listaParametros.add(valor);
			} else {

				valores += "NULL";
			}

		}
		sql += "(" + campos + ") VALUES ";
		sql += "(" + valores + ")";
		return new SqlConfiguration(sql, listaParametros.toArray());
	}

	public SqlConfiguration getConfigurationUpdateAll(Object obj) throws Exception {
		String sql = "update " + getNomeTabela() + " set ";
		String campos = "";
		String chaves = "";
		int qtCampos = camposUpdate.size();
		qtCampos += camposChave.size();
		// Object[] parametros = new Object[qtCampos];
		List listaParametros = new ArrayList();
		for (int i = 0; i < camposUpdate.size(); i++) {
			Field cmp = (Field) camposUpdate.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (i > 0) {
				campos += ",";

			}
			if (valor != null) {
				campos += cmp.getFieldDB() + " = ? ";
				listaParametros.add(valor);
			} else {
				campos += cmp.getFieldDB() + " = NULL ";
			}

		}

		for (int i = 0; i < camposChave.size(); i++) {
			Field cmp = (Field) camposChave.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (valor == null) {
				throw new Exception("PK value is mandatory");
			}
			if (i > 0) {
				chaves += " and ";
			}
			chaves += cmp.getFieldDB() + " = ? ";
			listaParametros.add(valor);
		}
		sql += campos + " where " + chaves;
		return new SqlConfiguration(sql, listaParametros.toArray());
	}

	public SqlConfiguration getConfigurationUpdateNotNull(Object obj) throws Exception {
		String sql = "update " + getNomeTabela() + " set ";
		String campos = "";
		String chaves = "";
		int qtCampos = camposUpdate.size();
		qtCampos += camposChave.size();
		// Object[] parametros = new Object[qtCampos];
		List listaParametros = new ArrayList();
		for (int i = 0; i < camposUpdate.size(); i++) {
			Field cmp = (Field) camposUpdate.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());

			if (valor != null) {
				if (listaParametros.size() > 0) {
					campos += ",";
				}
				campos += cmp.getFieldDB() + " = ? ";
				listaParametros.add(valor);
			}

		}

		for (int i = 0; i < camposChave.size(); i++) {
			Field cmp = (Field) camposChave.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (valor == null) {
				throw new Exception("PK value is mandatory");
			}
			if (i > 0) {
				chaves += " and ";
			}
			chaves += cmp.getFieldDB() + " = ? ";
			listaParametros.add(valor);
		}
		sql += campos + " where " + chaves;
		return new SqlConfiguration(sql, listaParametros.toArray());
	}

	public SqlConfiguration getConfigurationDelete(Object obj) throws Exception {
		String sql = "delete from " + getNomeTabela() + " where ";
		String chaves = "";
		Object[] parametros = new Object[camposChave.size()];
		for (int i = 0; i < camposChave.size(); i++) {

			Field cmp = (Field) camposChave.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (valor == null) {
				throw new Exception("PK value is mandatory");
			}
			if (i > 0) {
				chaves += " and ";

			}

			chaves += cmp.getFieldDB() + " = ? ";
			parametros[i] = valor;
		}
		sql += chaves;
		return new SqlConfiguration(sql, parametros);
	}

	public SqlConfiguration getConfigurationRestore(Object obj) throws Exception {
		String sql = " select ";
		String camposSql = "";
		String chaves = "";
		List camposRetorno = new ArrayList();

		Iterator it = campos.iterator();

		while (it.hasNext()) {
			Field cmp = (Field) it.next();

			camposRetorno.add(cmp.getFieldClass());
			if (camposSql.length() > 0) {
				camposSql += ",";
			}
			camposSql += cmp.getFieldDB();

		}
		Object[] parametros = new Object[camposChave.size()];

		for (int i = 0; i < camposChave.size(); i++) {

			Field cmp = (Field) camposChave.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (valor == null) {
				throw new Exception("PK value is mandatory");
			}
			if (i > 0) {
				chaves += " and ";

			}

			chaves += cmp.getFieldDB() + " = ? ";
			parametros[i] = valor;
		}
		sql += camposSql + " from " + getNomeTabela() + " where " + chaves;
		return new SqlConfiguration(sql, parametros, camposRetorno);
	}

	public SqlConfiguration getConfigurationList(List ordenacao) throws Exception {
		String sql = " select ";
		String camposSql = "";
		// String camposOrder = "";
		List camposRetorno = new ArrayList();

		Iterator it = campos.iterator();

		while (it.hasNext()) {
			Field cmp = (Field) it.next();

			camposRetorno.add(cmp.getFieldClass());
			if (camposSql.length() > 0) {
				camposSql += ",";
			}
			camposSql += cmp.getFieldDB();

		}
		sql += camposSql + " from " + getNomeTabela();
		if (ordenacao != null) {

			sql += getOrdenacao(ordenacao);
			/*
			 * List camposAux = (List)campos; //Varre a lista de ordenacao e
			 * encontra os campos corretos. for(int i = 0; i < ordenacao.size();
			 * i++){ Order ord = (Order)ordenacao.get(i); String cmpOrder =
			 * ord.getField(); for(int j=0;i<camposAux.size();i++){ Field cmp =
			 * (Field)camposAux.get(j); if
			 * (cmpOrder.equalsIgnoreCase(cmp.getFieldClass())){
			 * if(camposOrder.length()>0){ camposOrder+="
			 * "+ord.getTypeOrder()+","; } camposOrder+=cmp.getFieldDB(); } } }
			 */
		}

		// sql += " " + camposOrder;

		return new SqlConfiguration(sql, null, camposRetorno);
	}

	public SqlConfiguration getConfigurationFindByCondition(List condicao, List ordenacao) throws Exception {
		SqlConfiguration sqlConf = getConfigurationList(null);
		montaCondicaoOrdenacao(condicao, ordenacao, sqlConf);
		return sqlConf;

	}

	public SqlConfiguration getConfigurationUpdateAllByCondition(Object obj, List condicao) throws Exception {
		String sql = "update " + getNomeTabela() + " set ";
		String campos = "";
		List listaParametros = new ArrayList();
		for (int i = 0; i < camposUpdate.size(); i++) {
			Field cmp = (Field) camposUpdate.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (listaParametros.size() > 0) {
				campos += ",";

			}
			if (valor == null) {
				campos += cmp.getFieldDB() + " = NULL ";
			} else {
				campos += cmp.getFieldDB() + " = ? ";
				listaParametros.add(valor);
			}

		}
		SqlConfiguration result = new SqlConfiguration(sql + campos, listaParametros.toArray());
		montaCondicaoOrdenacao(condicao, null, result);
		return result;

	}

	public SqlConfiguration getConfigurationUpdateNotNullByCondition(Object obj, List condicao) throws Exception {
		String sql = "update " + getNomeTabela() + " set ";
		String campos = "";
		List listaParametros = new ArrayList();
		for (int i = 0; i < camposUpdate.size(); i++) {
			Field cmp = (Field) camposUpdate.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (valor != null) {
				if (listaParametros.size() > 0) {
					campos += ",";
				}
				campos += cmp.getFieldDB() + " = ? ";
				listaParametros.add(valor);

			}

		}
		SqlConfiguration result = new SqlConfiguration(sql + campos, listaParametros.toArray());
		montaCondicaoOrdenacao(condicao, null, result);
		return result;

	}

	/**
	 * Pega a configuracao de find. O segundo parametro eh uma lista de String
	 * com o nome dos campos na classe que devem ser ordenados. Exemplo: List
	 * lst = new ArrayList(); lst.add("nomeUf"); lst.add("siglaUf");
	 * 
	 * getConfigurationFindNotNull(obj, lst);
	 * 
	 * @param obj
	 * @param ordenacao
	 * @return
	 * @throws Exception
	 */
	public SqlConfiguration getConfigurationFindNotNull(Object obj, List ordenacao) throws Exception {
		String sql = " select ";
		String camposSql = "";
		String chaves = "";
		List camposRetorno = new ArrayList();

		Iterator it = campos.iterator();

		while (it.hasNext()) {
			Field cmp = (Field) it.next();

			camposRetorno.add(cmp.getFieldClass());
			if (camposSql.length() > 0) {
				camposSql += ",";
			}
			camposSql += cmp.getFieldDB();

		}
		Object[] parametros = null;
		List parms = new ArrayList();
		List camposAux = (List) campos;
		int qtdeParms = 0;
		for (int i = 0; i < camposAux.size(); i++) {

			Field cmp = (Field) camposAux.get(i);
			Object valor = Reflexao.getPropertyValue(obj, cmp.getFieldClass());
			if (valor != null) {
				if (qtdeParms > 0) {
					chaves += " and ";
				}
				qtdeParms++;
				chaves += cmp.getFieldDB() + " = ? ";
				parms.add(valor);
			}
		}
		if (qtdeParms > 0) {
			parametros = new Object[parms.size()];
			for (int i = 0; i < parms.size(); i++) {
				parametros[i] = (Object) parms.get(i);
			}
		}
		sql += camposSql + " from " + getNomeTabela() + " where " + chaves;

		if (ordenacao != null) {
			sql += getOrdenacao(ordenacao);
			// Varre a lista de ordenacao e encontra os campos corretos.
			/*
			 * for(int i = 0; i < ordenacao.size(); i++){ Order ord =
			 * (Order)ordenacao.get(i); String cmpOrder = ord.getField();
			 * for(int j=0;i<camposAux.size();i++){ Field cmp =
			 * (Field)camposAux.get(j); if
			 * (cmpOrder.equalsIgnoreCase(cmp.getFieldClass())){
			 * if(camposOrder.length()>0){ camposOrder+="
			 * "+ord.getTypeOrder()+","; } camposOrder+=cmp.getFieldDB(); } } }
			 */
		}

		// sql += " " + camposOrder;

		return new SqlConfiguration(sql, parametros, camposRetorno);
	}

	public SqlConfiguration getConfigurationDeleteByCondition(List condicao) throws Exception {
		SqlConfiguration sqlConf = new SqlConfiguration("delete from " + getNomeTabela(), null);
		montaCondicaoOrdenacao(condicao, null, sqlConf);
		return sqlConf;

	}

	private void montaCondicaoOrdenacao(List condicao, List ordenacao, SqlConfiguration sqlConf) throws Exception {

		if (condicao != null) {
			String result = "";
			List parametros = new ArrayList();
			if (sqlConf.getParamentros() != null) {

				parametros.addAll(Arrays.asList(sqlConf.getParamentros()));
			}
			for (int i = 0; i < condicao.size(); i++) {
				Condition cond = (Condition) condicao.get(i);
				if (i == 0) {
					result += " where  ";
				} else {
					if (cond.getOperator() == Condition.OR) {
						result += " or ";
					} else {
						result += " and ";
					}
				}

				result += getCampoDB(cond.getFiledClass()) + " " + cond.getComparator() + " ";

				if (cond.getValue() instanceof Collection) {
					Iterator it = ((Collection) cond.getValue()).iterator();
					String valores = "";
					while (it.hasNext()) {
						if (valores.length() > 0) {
							valores += ",";
						}
						valores += "?";
						parametros.add(it.next());
					}
					result += "(" + valores + ") ";
				} else {
				    	if (cond.getValue() == null){
        					result += " NULL ";
				    	}else{
        					result += "? ";
        					parametros.add(cond.getValue());
				    	}
				}

			}

			sqlConf.setParamentros(parametros.toArray());
			sqlConf.setSql(sqlConf.getSql() + result);
		}

		if (ordenacao != null) {

			sqlConf.setSql(sqlConf.getSql() + getOrdenacao(ordenacao));
		}

	}

	private String getOrdenacao(List ordenacao) throws Exception {
		if (ordenacao == null || ordenacao.size() == 0)
			return "";
		String result = " order by ";
		for (int i = 0; i < ordenacao.size(); i++) {
			if (i > 0) result += ",";
			Order order = (Order) ordenacao.get(i);
			result += getCampoDB(order.getField()) + " " + order.getTypeOrder();
		}
		return result;
	}

	public String getCampoDB(String campoClasse) throws Exception {

		Iterator it = campos.iterator();
		while (it.hasNext()) {
			Field cmp = (Field) it.next();

			if (cmp.getFieldClass().equals(campoClasse)) {
				return cmp.getFieldDB();
			}
		}
		throw new Exception("Field " + campoClasse + " não configurado!");

	}

	private void configuraCampos() {
		camposCreate = new ArrayList();
		camposChave = new ArrayList();
		camposUpdate = new ArrayList();
		uniqueFields = new ArrayList();
		camposSequencial = new ArrayList();

		if (getCampos() != null) {

			Iterator it = getCampos().iterator();
			while (it.hasNext()) {

				Field cmp = (Field) it.next();
				if (!cmp.isAuto()) {
					camposCreate.add(cmp);
				}
				if (cmp.isPk()) {
					camposChave.add(cmp);
				} else {
					camposUpdate.add(cmp);
				}

				if (cmp.isSequence()) {
					camposSequencial.add(cmp);
				}
				if (cmp.isUnique()) {
					uniqueFields.add(cmp);
				}
			}

		}

	}

}
