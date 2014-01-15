/**
 * 
 */
package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import br.com.centralit.citajax.util.Constantes;
import br.com.centralit.citcorpore.bean.PalavraGemeaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PalavraGemeaDAO extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = -6672763846038471421L;

	public PalavraGemeaDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	@Override
	public Collection getFields() {

		Collection listFields = new ArrayList();

		listFields.add(new Field("idpalavragemea", "idPalavraGemea", true, true, false, false));
		listFields.add(new Field("palavra", "palavra", false, false, false, false));
		listFields.add(new Field("palavracorrespondente", "palavraCorrespondente", false, false, false, false));

		return listFields;
	}

	@Override
	public String getTableName() {
		return "palavragemea";
	}

	@Override
	public Collection list() throws Exception {
		return super.list("palavra");
	}

	@Override
	public Class getBean() {
		return PalavraGemeaDTO.class;
	}

}
