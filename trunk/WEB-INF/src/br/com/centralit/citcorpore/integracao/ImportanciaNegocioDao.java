package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ImportanciaNegocioDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ImportanciaNegocioDao extends CrudDaoDefaultImpl {

	public ImportanciaNegocioDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2983316142102074344L;

	public Class getBean() {
		return ImportanciaNegocioDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();

		listFields.add(new Field("IDIMPORTANCIANEGOCIO","idImportanciaNegocio", true, true, false, false));
		listFields.add(new Field("IDEMPRESA", "idEmpresa", false, false, false,false));
		listFields.add(new Field("NOMEIMPORTANCIANEGOCIO","nomeImportanciaNegocio", false, false, false, false));
		listFields.add(new Field("SITUACAO", "situacao", false, false, false,false));

		return listFields;
	}

	public String getTableName() {
		return "IMPORTANCIANEGOCIO";
	}

	public Collection find(IDto obj) throws Exception {
		List ordem = new ArrayList();
		ordem.add(new Order("nomeImportanciaNegocio"));
		return super.find(obj, ordem);
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomeImportanciaNegocio"));
		return super.list(list);
	}

}
