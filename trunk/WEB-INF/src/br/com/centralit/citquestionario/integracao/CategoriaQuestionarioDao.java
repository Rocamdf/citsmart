package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.CategoriaQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CategoriaQuestionarioDao extends CrudDaoDefaultImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5768104603640127577L;

	public CategoriaQuestionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idCategoriaQuestionario", "idCategoriaQuestionario", true, true, false, false));
		listFields.add(new Field("nomeCategoriaQuestionario", "nomeCategoriaQuestionario", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "CategoriaQuestionario";
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomeCategoriaQuestionario"));
		return super.list(list);
	}

	public Class getBean() {
		return CategoriaQuestionarioDTO.class;
	}
}
