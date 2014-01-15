package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;

import br.com.centralit.citcorpore.bean.PedidoPortalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class PedidoPortalDAO extends CrudDaoDefaultImpl{

	public PedidoPortalDAO() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	private static final long serialVersionUID = 1L;

	@Override
	public Collection find(IDto obj) throws Exception {
		return null;
	}

	
	@Override
	public Collection getFields() {
Collection listFields = new ArrayList();
		
		listFields.add(new Field("idPedidoPortal", "idPedidoPortal", true, true, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("dataPedido", "dataPedido", false, false, false, false));
		listFields.add(new Field("precoTotal", "precoTotal", false, false, false, false));
		listFields.add(new Field("status", "status", false, false, false, false));
		
		return listFields;
		
	}

	@Override
	public String getTableName() {
		return "PedidoPortal";
	}

	@Override
	public Collection list() throws Exception {
		return null;
	}

	@Override
	public Class getBean() {
		return PedidoPortalDTO.class;
	}

}
