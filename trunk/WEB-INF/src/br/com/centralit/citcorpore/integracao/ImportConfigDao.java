package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.ImportConfigDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

public class ImportConfigDao extends CrudDaoDefaultImpl {
	public ImportConfigDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idImportConfig" ,"idImportConfig", true, true, false, false));
		listFields.add(new Field("nome" ,"nome", false, false, false, false));
		listFields.add(new Field("tipo" ,"tipo", false, false, false, false));
		listFields.add(new Field("idExternalConnection" ,"idExternalConnection", false, false, false, false));
		listFields.add(new Field("tabelaOrigem" ,"tabelaOrigem", false, false, false, false));
		listFields.add(new Field("tabelaDestino" ,"tabelaDestino", false, false, false, false));
		listFields.add(new Field("filtroOrigem" ,"filtroOrigem", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ImportConfig";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ImportConfigDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
}
