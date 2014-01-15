package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.SoftwaresListaNegraEncontradosDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

/**
 * @author ronnie.lopes
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SoftwaresListaNegraEncontradosDao extends CrudDaoDefaultImpl {
	
	private static final long serialVersionUID = -2983316142102074344L;

	public SoftwaresListaNegraEncontradosDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Class getBean() {
		return SoftwaresListaNegraEncontradosDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDSOFTWARESLISTANEGRAENCONTRAD", "idsoftwareslistanegraencontrados", true, true, false, false));
		listFields.add(new Field("IDITEMCONFIGURACAO", "iditemconfiguracao", false, false, false, false));
		listFields.add(new Field("IDSOFTWARESLISTANEGRA", "idsoftwareslistanegra", false, false, false, false));
		listFields.add(new Field("SOFTWARELISTANEGRAENCONTRADO", "softwarelistanegraencontrado", false, false, false, false));
		listFields.add(new Field("DATA", "data", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return "SoftwaresListaNegraEncontrados";
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("idsoftwareslistanegraencontrad"));
		return super.list(list);
	}

}
