package br.com.centralit.citquestionario.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import br.com.centralit.citquestionario.bean.QuestionarioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class QuestionarioDao extends CrudDaoDefaultImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1006840294191898276L;
	
    private static final String SQL_EXISTE_RESPOSTA =  "SELECT COUNT(*) QTDE "+
                                                       "  FROM RESPOSTAITEMQUESTIONARIO R, QUESTAOQUESTIONARIO Q, GRUPOQUESTIONARIO G "+
                                                       " WHERE R.IDQUESTAOQUESTIONARIO = Q.IDQUESTAOQUESTIONARIO "+
                                                       "   AND Q.IDGRUPOQUESTIONARIO = G.IDGRUPOQUESTIONARIO "+
                                                       "   AND G.IDQUESTIONARIO = ?"; 
    
    private static final String SQL_EXISTE_REFERENCIA_COMPARTILHADA =   "SELECT COUNT(*) QTDE "+
                                                        "  FROM QUESTAOQUESTIONARIO Q, GRUPOQUESTIONARIO G "+
                                                        " WHERE Q.IDGRUPOQUESTIONARIO = G.IDGRUPOQUESTIONARIO "+ 
                                                        "   AND G.IDQUESTIONARIO <> ? "+
                                                        "   AND Q.IDQUESTAOCOMPARTILHADA IN " +
                                                        "   (SELECT QQ.IDQUESTAOQUESTIONARIO "+
                                                        "      FROM QUESTAOQUESTIONARIO QQ, GRUPOQUESTIONARIO G  "+
                                                        "     WHERE QQ.IDGRUPOQUESTIONARIO = G.IDGRUPOQUESTIONARIO  "+
                                                        "       AND G.IDQUESTIONARIO = ?)"; 
    
    private static final String SQL_LIST_QUESTIONARIO = "SELECT IDQUESTIONARIO, IDQUESTIONARIOORIGEM, IDCATEGORIAQUESTIONARIO, "+
                                                        "       NOMEQUESTIONARIO, IDEMPRESA, ATIVO, javaScript "+
                                                        "  FROM QUESTIONARIO "+ 
                                                        " WHERE ATIVO = 'S' "+
                                                        " ORDER BY NOMEQUESTIONARIO";

    private static final String SQL_LIST_POR_APLICACAO = "SELECT DISTINCT Q.IDQUESTIONARIO, IDQUESTIONARIOORIGEM, Q.IDCATEGORIAQUESTIONARIO, "+
                                                         "       NOMEQUESTIONARIO, Q.IDEMPRESA, ATIVO, javaScript "+
                                                         "  FROM QUESTIONARIO Q, APLICACAOQUESTIONARIO A "+ 
                                                         " WHERE Q.IDQUESTIONARIO = Q.IDQUESTIONARIOORIGEM "+
                                                         "   AND Q.IDQUESTIONARIO = A.IDQUESTIONARIO "+
                                                         "   AND A.SITUACAO = 'A' "+
                                                         "   AND (A.APLICACAO = 'T' OR A.APLICACAO = ?) "+
                                                         "   AND Q.idEmpresa = ? "+
                                                         " ORDER BY NOMEQUESTIONARIO";
    
	public QuestionarioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idQuestionario", "idQuestionario", true, true, false, false));
		listFields.add(new Field("idQuestionarioOrigem", "idQuestionarioOrigem", false, false, false, false));
		listFields.add(new Field("idCategoriaQuestionario", "idCategoriaQuestionario", false, false, false, false));
		listFields.add(new Field("nomeQuestionario", "nomeQuestionario", false, false, false, false));
		listFields.add(new Field("idEmpresa", "idEmpresa", false, false, false, false));
        listFields.add(new Field("ativo", "ativo", false, false, false, false));	
        listFields.add(new Field("javaScript", "javaScript", false, false, false, false));    
		
		return listFields;
	}

	public String getTableName() {
		return "Questionario";
	}

	public Collection list() throws Exception {
        Object[] objs = new Object[] {};
        List lista = this.execSQL(SQL_LIST_QUESTIONARIO, objs);
        
        List listRetorno = new ArrayList();
        listRetorno.add("idQuestionario");
        listRetorno.add("idQuestionarioOrigem");
        listRetorno.add("idCategoriaQuestionario");
        listRetorno.add("nomeQuestionario");
        listRetorno.add("idEmpresa");
        listRetorno.add("ativo");
        listRetorno.add("javaScript");
        
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        return result;
	}

    public Collection listByIdEmpresaAndAplicacao(Integer idEmpresa, String aplicacao) throws Exception {
        Object[] objs = new Object[] {aplicacao, idEmpresa};
        List lista = this.execSQL(SQL_LIST_POR_APLICACAO, objs);
        
        List listRetorno = new ArrayList();
        listRetorno.add("idQuestionario");
        listRetorno.add("idQuestionarioOrigem");
        listRetorno.add("idCategoriaQuestionario");
        listRetorno.add("nomeQuestionario");
        listRetorno.add("idEmpresa");
        listRetorno.add("ativo");
        listRetorno.add("javaScript");
        
        List result = this.engine.listConvertion(getBean(), lista, listRetorno);
        return result;
    }
	
	public Class getBean() {
		return QuestionarioDTO.class;
	}
	
	public Collection listByIdEmpresa(Integer idEmpresa) throws Exception {
        List lstCond = new ArrayList();
        List lstOrder = new ArrayList();
        lstCond.add(new Condition("idEmpresa", "=", idEmpresa));
        lstCond.add(new Condition("ativo", "=", "S"));
        lstOrder.add(new Order("nomeQuestionario"));
        return super.findByCondition(lstCond, lstOrder);  
	}

    public QuestionarioDTO restoreByIdOrigem(Integer idQuestionarioOrigem) throws Exception {
        List lstCond = new ArrayList();
        lstCond.add(new Condition("idQuestionarioOrigem", "=", idQuestionarioOrigem));
        lstCond.add(new Condition("ativo", "=", "S"));
        Collection col = super.findByCondition(lstCond, null); 
        if (col == null){
        	return null;
        }
        Iterator it = col.iterator();
        if (it.hasNext()){
            return (QuestionarioDTO) it.next();
        }else{
            return null;
        }
    }

	public boolean existeResposta(Integer idQuestionario) throws Exception {
        Object[] objs = new Object[] {idQuestionario};
        List lista = this.execSQL(SQL_EXISTE_RESPOSTA, objs);

        Object[] row = (Object[]) lista.get(0);
        return Integer.parseInt(row[0].toString()) > 0;
    }  
	
    public boolean existeReferenciaQuestaoCmpartilhada(Integer idQuestionario) throws Exception {
        Object[] objs = new Object[] {idQuestionario, idQuestionario};
        List lista = this.execSQL(SQL_EXISTE_REFERENCIA_COMPARTILHADA, objs);

        Object[] row = (Object[]) lista.get(0);
        return Integer.parseInt(row[0].toString()) > 0;
    } 	
    
    @Override
    public void updateNotNull(IDto obj) throws Exception {
        // TODO Auto-generated method stub
        super.updateNotNull(obj);
    }
}
