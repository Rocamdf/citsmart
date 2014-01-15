package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.OpcaoRespostaQuestionarioDTO;
import br.com.centralit.citquestionario.bean.QuestaoQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class OpcaoRespostaQuestionarioDao extends CrudDaoDefaultImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1632007685703882032L;

	public OpcaoRespostaQuestionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idOpcaoRespostaQuestionario", "idOpcaoRespostaQuestionario", true, true, false, false));
		listFields.add(new Field("idQuestaoQuestionario", "idQuestaoQuestionario", false, false, false, false));
		listFields.add(new Field("titulo", "titulo", false, false, false, false));
        listFields.add(new Field("peso", "peso", false, false, false, false));  
		listFields.add(new Field("valor", "valor", false, false, false, false));    
		listFields.add(new Field("geraAlerta", "geraAlerta", false, false, false, false));    
		listFields.add(new Field("exibeComplemento", "exibeComplemento", false, false, false, false));
		listFields.add(new Field("idQuestaoComplemento", "idQuestaoComplemento", false, false, false, false));      
        
		return listFields;
	}

	public String getTableName() {
		return "OpcaoRespostaQuestionario";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return OpcaoRespostaQuestionarioDTO.class;
	}

	public Collection listByIdQuestaoQuestionario(Integer idQuestaoQuestionario) throws Exception {
		List list = new ArrayList();
		list.add(new Order("idOpcaoRespostaQuestionario"));		
		OpcaoRespostaQuestionarioDTO obj = new OpcaoRespostaQuestionarioDTO();
		obj.setIdQuestaoQuestionario(idQuestaoQuestionario);
		return super.find(obj, list);	
	}	
	
    public int deleteByIdQuestaoQuestionario(Integer idQuestaoQuestionario) throws Exception {
        Condition cond = new Condition("idQuestaoQuestionario", "=", idQuestaoQuestionario);
        List lstCond = new ArrayList();
        
        lstCond.add(cond);
        return super.deleteByCondition(lstCond);
    }
	    
}
