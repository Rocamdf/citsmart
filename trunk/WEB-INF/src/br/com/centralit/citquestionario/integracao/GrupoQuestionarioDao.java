package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.GrupoQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class GrupoQuestionarioDao extends CrudDaoDefaultImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8426680865854581008L;

	public GrupoQuestionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idGrupoQuestionario", "idGrupoQuestionario", true, true, false, false));
		listFields.add(new Field("idQuestionario", "idQuestionario", false, false, false, false));
		listFields.add(new Field("nomeGrupoQuestionario", "nomeGrupoQuestionario", false, false, false, false));
		listFields.add(new Field("ordem", "ordem", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "GrupoQuestionario";
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomeGrupoQuestionario"));
		return super.list(list);
	}

	public Class getBean() {
		return GrupoQuestionarioDTO.class;
	}
	
	public Collection listByIdQuestionario(Integer idQuestionario) throws Exception {
		List list = new ArrayList();
		list.add(new Order("ordem"));		
		list.add(new Order("idGrupoQuestionario"));		
		GrupoQuestionarioDTO obj = new GrupoQuestionarioDTO();
		obj.setIdQuestionario(idQuestionario);
		return super.find(obj, list);	
	}
	
	public void updateOrdem(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}
	public void updateNome(IDto obj) throws Exception {
		super.updateNotNull(obj);
	}
}
