package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.metainfo.bean.VisaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.excecao.PersistenceException;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class VisaoDao extends CrudDaoDefaultImpl {
	public VisaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idVisao" ,"idVisao", true, true, false, false));
		listFields.add(new Field("descricao" ,"descricao", false, false, false, false));
		listFields.add(new Field("tipoVisao" ,"tipoVisao", false, false, false, false));
		listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
		listFields.add(new Field("classeName" ,"classeName", false, false, false, false));
		listFields.add(new Field("identificador" ,"identificador", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Visao";
	}
	public Collection list() throws Exception {
		return null;
	}
	public Collection listAtivos() throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("situacao", "=", "A"));
		ordenacao.add(new Order("descricao"));
		
		return super.findByCondition(condicao, ordenacao);
	}	

	public VisaoDTO findByIdentificador(String identificador) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("identificador", "=", identificador));
		ordenacao.add(new Order("descricao"));
		
		List result = (List) super.findByCondition(condicao, ordenacao);
		if (result != null)
			return (VisaoDTO) result.get(0);
		else
			return null;
	}	

	public Class getBean() {
		return VisaoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	@Override
	public List execSQL(String sql, Object[] parametros)
			throws PersistenceException {
		return super.execSQL(sql, parametros);
	}
	@Override
	public int execUpdate(String sql, Object[] parametros)
			throws PersistenceException {
		return super.execUpdate(sql, parametros);
	}
	
}
