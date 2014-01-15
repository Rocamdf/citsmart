package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citquestionario.bean.AplicacaoQuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AplicacaoQuestionarioDao extends CrudDaoDefaultImpl {

	/**
	 * 
	 */
    private static final String SQL_LIST_POR_QUESTIONARIO_APLICACAO = "SELECT IDAPLICACAOQUESTIONARIO, Q.IDQUESTIONARIO, IDTIPOPRODUTO, A.SITUACAO, APLICACAO "+
                                        "  FROM QUESTIONARIO Q, APLICACAOQUESTIONARIO A "+ 
                                        " WHERE A.IDQUESTIONARIO = Q.IDQUESTIONARIOORIGEM "+
                                        "   AND A.SITUACAO = 'A' "+
                                        "   AND Q.IDQUESTIONARIO = ? "+                                        
                                        "   AND (A.APLICACAO = 'T' OR A.APLICACAO = ?) ";
    
	private static final long serialVersionUID = 5768104603640127577L;

	public AplicacaoQuestionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idAplicacaoQuestionario", "idAplicacaoQuestionario", true, true, false, false));
		listFields.add(new Field("idQuestionario", "idQuestionario", false, false, false, false));
		listFields.add(new Field("idTipoProduto", "idTipoProduto", false, false, false, false));
	    listFields.add(new Field("situacao", "situacao", false, false, false, false));
	    listFields.add(new Field("aplicacao", "aplicacao", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "AplicacaoQuestionario";
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("idAplicacaoQuestionario"));
		return super.list(list);
	}

	public Class getBean() {
		return AplicacaoQuestionarioDTO.class;
	}
	
    public Collection listByIdQuestionarioAndAplicacao(Integer idQuestionario, String aplicacao) throws Exception {
        Object[] objs = new Object[] {idQuestionario, aplicacao};
        List lista = this.execSQL(SQL_LIST_POR_QUESTIONARIO_APLICACAO, objs);
        
        List listRetorno = new ArrayList();
        listRetorno.add("idAplicacaoQuestionario");
        listRetorno.add("idQuestionario");
        listRetorno.add("idTipoProduto");
        listRetorno.add("situacao");
        listRetorno.add("aplicacao");
        
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        return result;
    }
	
}
