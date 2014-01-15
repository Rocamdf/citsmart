package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExecucaoBatchDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ExecucaoBatchDao extends CrudDaoDefaultImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ExecucaoBatchDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	
	public Collection findByIdProcBatch(Integer idProcBatch) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		
		condicao.add(new Condition("idProcessamentoBatch", "=", idProcBatch));
		ordenacao.add(new Order("dataHora", Order.DESC));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idExecucaoBatch", "idExecucaoBatch", true, true, false, false));
		listFields.add(new Field("idProcessamentoBatch", "idProcessamentoBatch", false, false, false, false));
		listFields.add(new Field("conteudo", "conteudo", false, false, false, false));
		listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "ExecucaoBatch";
	}

	public Collection list() throws Exception {
        return null;
    }

	public Class getBean() {
		return ExecucaoBatchDTO.class;
	}
}
