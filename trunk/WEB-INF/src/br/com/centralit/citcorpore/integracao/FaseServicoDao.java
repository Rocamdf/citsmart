package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.FaseServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class FaseServicoDao extends CrudDaoDefaultImpl {
	public FaseServicoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idFase" ,"idFase", true, true, false, false));
		listFields.add(new Field("nomeFase" ,"nomeFase", false, false, false, false));
		listFields.add(new Field("faseCaptura" ,"faseCaptura", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "FaseServico";
	}
	public Collection list() throws Exception {
		List list = new ArrayList();
		list.add(new Order("nomeFase"));
		return super.list(list);	    
	}

	public Class getBean() {
		return FaseServicoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
}
