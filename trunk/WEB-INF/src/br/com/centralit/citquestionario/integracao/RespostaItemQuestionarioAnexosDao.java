package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioAnexosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RespostaItemQuestionarioAnexosDao extends CrudDaoDefaultImpl {
	
	public RespostaItemQuestionarioAnexosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idRespostaItmQuestionarioAnexo", "idRespostaItmQuestionarioAnexo", true, true, false, false));
		listFields.add(new Field("idRespostaItemQuestionario", "idRespostaItemQuestionario", false, false, false, false));
		listFields.add(new Field("caminhoAnexo", "caminhoAnexo", false, false, false, false));
		listFields.add(new Field("observacao", "observacao", false, false, false, false));
		
		return listFields;
	}

	@Override
	public String getTableName() {
		return "RespostaItemQuestionarioAnexos";
	}

	@Override
	public Collection list() throws Exception {
		return null;
	}

	public Collection listByIdRespostaItemQuestionario(Integer idRespostaItemQuestionario) throws Exception {
		List list = new ArrayList();
		list.add(new Order("idRespostaItemQuestionario"));		
		RespostaItemQuestionarioAnexosDTO obj = new RespostaItemQuestionarioAnexosDTO();
		obj.setIdRespostaItemQuestionario(idRespostaItemQuestionario);
		return super.find(obj, list);	
	}	
	
	@Override
	public Class getBean() {
		return RespostaItemQuestionarioAnexosDTO.class;
	}
	
	public void deleteByIdRespostaItemQuestionario(
	        final Integer idRespostaItemQuestionario) throws Exception
	{
	    Condition where = new Condition("idRespostaItemQuestionario", "=", 
	            idRespostaItemQuestionario);
        List lstCond = new ArrayList();
        lstCond.add(where);
        super.deleteByCondition(lstCond);
	}
}