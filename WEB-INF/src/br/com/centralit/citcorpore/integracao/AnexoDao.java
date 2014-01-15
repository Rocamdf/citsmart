package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AnexoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AnexoDao extends CrudDaoDefaultImpl {

	public AnexoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private static final long serialVersionUID = -2983316142102074344L;

	public Class getBean() {
		return AnexoDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();	
	    
		listFields.add(new Field("idAnexo", "idAnexo", true, true, false, false));
		listFields.add(new Field("nome", "nome", false, false, false, false));	
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("extensao", "extensao", false, false, false, false));
		listFields.add(new Field("link", "link", false, false, false, false));	
        listFields.add(new Field("path", "path", false, false, false, false));  
		listFields.add(new Field("idExecucaoAtividade", "idExecucaoAtividade", false, false, false, false));	
		return listFields;
	}

	public String getTableName() {
		return "ANEXO";
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nome"));
		return super.list(list);
	}
	
	public void deleteByIdExecucao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idExecucaoAtividade", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection findByIdExecucao(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idExecucaoAtividade", "=", parm));
		ordenacao.add(new Order("nome"));
		return super.findByCondition(condicao, ordenacao);
	}

}
