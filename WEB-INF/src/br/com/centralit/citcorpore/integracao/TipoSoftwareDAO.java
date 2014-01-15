package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.TipoSoftwareDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TipoSoftwareDAO extends CrudDaoDefaultImpl {

	public TipoSoftwareDAO() {
		 super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 6616035672697692662L;

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("IDTIPOSOFTWARE", "idTipoSoftware", true, true, false, false));
		listFields.add(new Field("NOME", "nome", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "TIPOSOFTWARE";
	}

	@Override
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("idTipoSoftware"));
		return super.list(list);
	    }

	@Override
	public Class getBean() {
		return TipoSoftwareDTO.class;
	}

}
