package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AlcadaDTO;
import br.com.centralit.citcorpore.bean.ProcessamentoBatchDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ProcessamentoBatchDao extends CrudDaoDefaultImpl {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4639535278978154379L;

	public ProcessamentoBatchDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idProcessamentoBatch", "idProcessamentoBatch", true, true, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("expressaoCRON", "expressaoCRON", false, false, false, false));
		listFields.add(new Field("conteudo", "conteudo", false, false, false, false));
		listFields.add(new Field("tipo", "tipo", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "ProcessamentoBatch";
	}

	public Collection list() throws Exception {
            List list = new ArrayList();
            list.add(new Order("descricao"));
            return super.list(list);
	}

	public Class getBean() {
		return ProcessamentoBatchDTO.class;
	}
	public Collection getAtivos() throws Exception {
		List lstOrder = new ArrayList();
		List lstCondicao = new ArrayList();
		
		lstCondicao.add(new Condition("situacao", "=", "A"));
		lstOrder.add(new Order("descricao"));
		
		return super.findByCondition(lstCondicao, lstOrder);
	}
	
	/**
	 * Metodo que verifica se existe um registro com os mesmos dados na base de dados.
	 * 
	 * @param processamentoBatch
	 * @return
	 * @throws Exception
	 */
	public boolean existeDuplicidade(ProcessamentoBatchDTO processamentoBatch) throws Exception {
    	List condicao = new ArrayList();
    	List ordenacao = new ArrayList();
    	condicao.add(new Condition("descricao", "=", processamentoBatch.getDescricao()));
    	ordenacao.add(new Order("descricao"));
    	
    	List result = (List) super.findByCondition(condicao, ordenacao);
    	
    	if (result != null && !result.isEmpty()) 
            return true;
        else
            return false;
    }
	public boolean existeDuplicidadeClasse(ProcessamentoBatchDTO processamentoBatch) throws Exception {
    	List condicao = new ArrayList();
    	List ordenacao = new ArrayList();
    	condicao.add(new Condition("conteudo", "like", processamentoBatch.getConteudo()));
    	ordenacao.add(new Order("descricao"));
    	
    	List result = (List) super.findByCondition(condicao, ordenacao);
    	
    	if (result != null && !result.isEmpty()) 
            return true;
        else
            return false;
    }
}

