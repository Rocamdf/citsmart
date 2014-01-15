package br.com.centralit.citcorpore.metainfo.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.metainfo.bean.ScriptsVisaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class ScriptsVisaoDao extends CrudDaoDefaultImpl {
	public ScriptsVisaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idScriptsVisao" ,"idScriptsVisao", true, true, false, false));
		listFields.add(new Field("idVisao" ,"idVisao", false, false, false, false));
		listFields.add(new Field("typeExecute" ,"typeExecute", false, false, false, false));
		listFields.add(new Field("scryptType" ,"scryptType", false, false, false, false));
		listFields.add(new Field("script" ,"script", false, false, false, false));
		listFields.add(new Field("scriptLanguage" ,"scriptLanguage", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ScriptsVisao";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ScriptsVisaoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdVisao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idVisao", "=", parm)); 
		ordenacao.add(new Order("typeExecute"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdVisao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idVisao", "=", parm));
		super.deleteByCondition(condicao);
	}
}
