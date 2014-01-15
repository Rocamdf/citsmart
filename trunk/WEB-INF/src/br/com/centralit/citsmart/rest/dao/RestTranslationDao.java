package br.com.centralit.citsmart.rest.dao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citsmart.rest.bean.RestTranslationDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class RestTranslationDao extends CrudDaoDefaultImpl {
	public RestTranslationDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idRestTranslation" ,"idRestTranslation", true, true, false, false));
		listFields.add(new Field("idRestOperation" ,"idRestOperation", false, false, false, false));
		listFields.add(new Field("idBusinessObject" ,"idBusinessObject", false, false, false, false));
		listFields.add(new Field("fromValue" ,"fromValue", false, false, false, false));
		listFields.add(new Field("toValue" ,"toValue", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "Rest_Translation";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RestTranslationDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
}
