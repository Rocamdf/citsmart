package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioDTO;
import br.com.centralit.citquestionario.bean.RespostaItemQuestionarioOpcoesDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class RespostaItemQuestionarioOpcoesDao extends CrudDaoDefaultImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 353690809125234502L;

	public RespostaItemQuestionarioOpcoesDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idRespostaItemQuestionario", "idRespostaItemQuestionario", true, false, false, false));
		listFields.add(new Field("idOpcaoRespostaQuestionario", "idOpcaoRespostaQuestionario", true, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "RespostaItemQuestionarioOpcoes";
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return RespostaItemQuestionarioOpcoesDTO.class;
	}
	
	public Collection listByIdRespostaItemQuestionario(Integer idRespostaItemQuestionario) throws Exception {
		List list = new ArrayList();
		list.add(new Order("idOpcaoRespostaQuestionario"));		
		RespostaItemQuestionarioOpcoesDTO obj = new RespostaItemQuestionarioOpcoesDTO();
		obj.setIdRespostaItemQuestionario(idRespostaItemQuestionario);
		return super.find(obj, list);	
	}
	
    public Collection getRespostasOpcoesByIdRespostaItemQuestionario(Integer idRespostaItemQuestionario) throws Exception  {
        Object[] objs = new Object[] {idRespostaItemQuestionario};
        
        String sql = "SELECT OPCAORESPOSTAQUESTIONARIO.idOpcaoRespostaQuestionario, titulo, peso, valor, geraAlerta, exibeComplemento, idQuestaoComplemento " +
        		"FROM RESPOSTAITEMQUESTIONARIOOPCOES " +
        		"INNER JOIN OPCAORESPOSTAQUESTIONARIO ON OPCAORESPOSTAQUESTIONARIO.idOpcaoRespostaQuestionario = RESPOSTAITEMQUESTIONARIOOPCOES.idOpcaoRespostaQuestionario " +
        		"WHERE idRespostaItemQuestionario = ?";
        List lista = this.execSQL(sql, objs);
        
        List listRetorno = new ArrayList();        
        listRetorno.add("idOpcaoRespostaQuestionario");
        listRetorno.add("titulo");
        listRetorno.add("peso");
        listRetorno.add("valor");
        listRetorno.add("geraAlerta");
        listRetorno.add("exibeComplemento");
        listRetorno.add("idQuestaoComplemento");

        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        return result;
 
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