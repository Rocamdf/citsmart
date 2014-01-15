package br.com.centralit.citgerencial.integracao;

import java.util.Collection;

import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.util.Constantes;

public class GerencialGenerateDao extends CrudDaoDefaultImpl  {	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2146645404182739180L;

	public GerencialGenerateDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		return null;
	}

	public String getTableName() {
		return null;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return null;
	}
	
	public Collection executaSQL(String sql, Object[] list) throws Exception{
		Collection lista = this.execSQL(sql, list);
		return lista;
	}


}
