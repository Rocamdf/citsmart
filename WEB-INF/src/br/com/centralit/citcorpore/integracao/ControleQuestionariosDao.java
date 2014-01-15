package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleQuestionariosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

public class ControleQuestionariosDao extends CrudDaoDefaultImpl {

	public ControleQuestionariosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idControleQuestionario", "idControleQuestionario", true, true, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "ControleQuestionarios";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ControleQuestionariosDTO.class;
	}

}
