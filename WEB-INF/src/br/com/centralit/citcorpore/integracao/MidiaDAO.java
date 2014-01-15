package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.MidiaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class MidiaDAO extends CrudDaoDefaultImpl {

	public MidiaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -3898157047313665148L;

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}



	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("IDMIDIA", "idMidia", true, true, false, false));
		listFields.add(new Field("NOME", "nome", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "MIDIA";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("idMidia"));
		return super.list(list);
	    }


	@Override
	public Class getBean() {
		return MidiaDTO.class;
	}

}
