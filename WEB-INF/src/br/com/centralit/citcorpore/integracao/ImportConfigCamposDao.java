package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.ImportConfigCamposDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class ImportConfigCamposDao extends CrudDaoDefaultImpl {
	public ImportConfigCamposDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idImportConfigCampo" ,"idImportConfigCampo", true, true, false, false));
		listFields.add(new Field("idImportConfig" ,"idImportConfig", false, false, false, false));
		listFields.add(new Field("origem" ,"origem", false, false, false, false));
		listFields.add(new Field("destino" ,"destino", false, false, false, false));
		listFields.add(new Field("script" ,"script", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ImportConfigCampos";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ImportConfigCamposDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdImportConfig(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idImportConfig", "=", parm)); 
		ordenacao.add(new Order("idImportConfigCampo"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdImportConfig(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idImportConfig", "=", parm));
		super.deleteByCondition(condicao);
	}
}
