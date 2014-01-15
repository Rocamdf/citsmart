/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.JustificativaParecerDTO;
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
@SuppressWarnings({"unchecked","rawtypes"})
public class JustificativaParecerDao extends CrudDaoDefaultImpl {

    /**
     * 
     */
    private static final long serialVersionUID = -6089982747737399561L;

    public JustificativaParecerDao() {
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
    listFields.add(new Field("aplicavelRequisicao" ,"aplicavelRequisicao", false, false, false, false));
    listFields.add(new Field("aplicavelCotacao" ,"aplicavelCotacao", false, false, false, false));
    listFields.add(new Field("aplicavelInspecao" ,"aplicavelInspecao", false, false, false, false));    
	listFields.add(new Field("situacao" ,"situacao", false, false, false, false));
	return listFields;
    }
 
    @Override
    public String getTableName() {
	return "justificativaparecer";
    }

    @Override
    public Collection list() throws Exception {
    	List condicao = new ArrayList();
    	List ordenacao = new ArrayList();
    	
    	ordenacao.add(new Order("descricaoJustificativa"));
    	return super.findByCondition(condicao, ordenacao);
    }

    @Override
    public Class getBean() {
	return JustificativaParecerDTO.class;
    }

    public Collection listAplicaveisCotacao() throws Exception {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("aplicavelCotacao", "=", "S")); 
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }
    
    public Collection listAplicaveisRequisicao() throws Exception {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("aplicavelRequisicao", "=", "S")); 
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }

    public Collection listAplicaveisInspecao() throws Exception {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("aplicavelInspecao", "=", "S")); 
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }
    
	public boolean consultarJustificativaAtiva(JustificativaParecerDTO justificativaParecerDto) throws Exception {
		List parametro = new ArrayList();
		List list = new ArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select idjustificativa from " + getTableName() + "  where  descricaojustificativa = ? and situacao = 'A'");

		parametro.add(justificativaParecerDto.getDescricaoJustificativa());

		if (justificativaParecerDto.getIdJustificativa() != null) {
			sql.append("and idjustificativa <> ?");
			parametro.add(justificativaParecerDto.getIdJustificativa());
		}

		list = this.execSQL(sql.toString(), parametro.toArray());
		if (list != null && !list.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}
	
    public Collection listAplicaveisRequisicaoViagem() throws Exception {
        List condicao = new ArrayList();
        List ordenacao = new ArrayList(); 
        condicao.add(new Condition("aplicavelRequisicao", "=", "S")); 
        condicao.add(new Condition("viagem", "=", "S")); 
        ordenacao.add(new Order("descricaoJustificativa"));
        return super.findByCondition(condicao, ordenacao);
    }
    
}
