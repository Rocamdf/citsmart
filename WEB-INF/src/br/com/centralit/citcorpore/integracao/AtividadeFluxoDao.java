package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.AtividadeFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class AtividadeFluxoDao extends CrudDaoDefaultImpl {

	public AtividadeFluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2983316142102074344L;

	public Class getBean() {
		return AtividadeFluxoDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idAtividade", "idAtividade", false, false, false, false));
		listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "ATIVIDADESFLUXOS";
	}

	public Collection find(IDto obj) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("idFluxo"));		
		return super.find(obj, ordem);
	}

	public Collection list() throws Exception {
		return null;
	}


}
