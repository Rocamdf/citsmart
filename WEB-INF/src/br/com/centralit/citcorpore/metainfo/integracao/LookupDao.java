package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.metainfo.bean.LookupDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.util.Constantes;;

@SuppressWarnings("serial")
public class LookupDao extends CrudDaoDefaultImpl {
	public LookupDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	@SuppressWarnings("rawtypes")
	public Collection getFields() {
		return null;
	}
	public String getTableName() {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Collection list() throws Exception {
		return null;
	}
	@SuppressWarnings("rawtypes")
	public Class getBean() {
		return LookupDTO.class;
	}
	@SuppressWarnings("rawtypes")
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	@SuppressWarnings("rawtypes")
	@Override
	public List execSQL(String sql, Object[] parametros)
			throws PersistenceException {
		if (sql == null || sql.trim().equalsIgnoreCase("")){
			return null;
		}
		return super.execSQL(sql, parametros);
	}
}
