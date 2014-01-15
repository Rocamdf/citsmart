package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.CustoAdicionalProjetoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class CustoAdicionalProjetoDao extends CrudDaoDefaultImpl{
	public CustoAdicionalProjetoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2983316142102074344L;

	public Class getBean() {
		return CustoAdicionalProjetoDTO.class;
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		
		listFields.add(new Field("idCustoAdicional", "idCustoAdicional", true, true, false, false));
		listFields.add(new Field("idProjeto", "idProjeto", false, false, false, false));
		listFields.add(new Field("tipoCusto", "tipoCusto", false, false, false, false));
		listFields.add(new Field("valor", "valor", false, false, false, false));
		listFields.add(new Field("detalhamento", "detalhamento", false, false, false, false));
		listFields.add(new Field("dataCusto", "dataCusto", false, false, false, false));
		
		return listFields;
	}

	public String getTableName() {
		return "CUSTOSADICIONAISPROJETO";
	}

	public Collection find(IDto obj) throws Exception {
		return null;
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("idProjeto"));
		return super.list(list);
	}

}
