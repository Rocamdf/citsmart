/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.JustificativaSolicitacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author breno.guimaraes
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class JustificativaSolicitacaoDao extends CrudDaoDefaultImpl {

    /**
     * 
     */
    private static final long serialVersionUID = -6089982747737399561L;

    public JustificativaSolicitacaoDao() {
	super(Constantes.getValue("DATABASE_ALIAS"), null);
    }

    @Override
    public Collection find(IDto arg0) throws Exception {
	return null;
    }

    @Override
    public Collection getFields() {
	Collection listFields = new ArrayList();
	listFields.add(new Field("idjustificativa" ,"idJustificativa", true, true, false, false));
	listFields.add(new Field("descricaojustificativa" ,"descricaoJustificativa", false, false, false, false));
	listFields.add(new Field("suspensao" ,"suspensao", false, false, false, false));
	listFields.add(new Field("aprovacao" ,"aprovacao", false, false, false, false));
	listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
	listFields.add(new Field("viagem" ,"viagem", false, false, false, false));
	listFields.add(new Field("deleted" ,"deleted", false, false, false, false));
	return listFields;
    }
    
    public Collection listAtivasParaSuspensao() throws Exception{
	List condicao = new ArrayList();
	List ordenacao = new ArrayList();
	
	condicao.add(new Condition("suspensao", "=", "S"));
	condicao.add(new Condition("situacao", "=", "A"));
	condicao.add(new Condition("deleted", "<>", "Y"));
	condicao.add(new Condition(Condition.OR, "deleted", "is", null));
	
	ordenacao.add(new Order("descricaoJustificativa"));
	return super.findByCondition(condicao, ordenacao);
    }
    
    

    public Collection listAtivasParaAprovacao() throws Exception{
        List condicao = new ArrayList();
        List ordenacao = new ArrayList();
        
        condicao.add(new Condition("aprovacao", "=", "S"));
        condicao.add(new Condition("situacao", "=", "A"));
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }
    
    @Override
    public String getTableName() {
	return "justificativasolicitacao";
    }

    @Override
    public Collection list() throws Exception {
    	List condicao = new ArrayList();
    	List ordenacao = new ArrayList();
    	
    	ordenacao.add(new Order("descricaoJustificativa"));
    	return super.findByCondition(condicao, ordenacao);
    }
    
   
	/**
	 * Retorna uma lista de justificativas ativas para requisicao viagem
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection listAtivasParaViagem() throws Exception{
    	List condicao = new ArrayList();
    	List listaRetorno = new ArrayList();
    	
    	StringBuffer sql = new StringBuffer();
    	
    	sql.append("select idjustificativa,descricaoJustificativa  from justificativasolicitacao where suspensao = ? and viagem =  ? and situacao = ? and (deleted <> ? or deleted is null) ");
    	condicao.add("N");
    	condicao.add("S");
    	condicao.add("A");
    	condicao.add("Y");
    	
    	List lista = this.execSQL(sql.toString(), condicao.toArray());
    	
    	listaRetorno.add("idJustificativa");
    	listaRetorno.add("descricaoJustificativa");
    	
    	if (lista != null && !lista.isEmpty()) {
			return this.listConvertion(getBean(), lista, listaRetorno);
		} else {
			return null;
		}
    
    }

    @Override
    public Class getBean() {
	return JustificativaSolicitacaoDTO.class;
    }

}
