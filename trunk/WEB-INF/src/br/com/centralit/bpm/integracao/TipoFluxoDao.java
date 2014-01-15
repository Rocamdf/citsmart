package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.TipoFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class TipoFluxoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 7360913307987027459L;

	private static final String TABLE_NAME = "bpm_tipofluxo";

	public TipoFluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idTipoFluxo", "idTipoFluxo", true, true, false, false));
		listFields.add(new Field("nomeFluxo", "nomeFluxo", false, false, false, false));
		listFields.add(new Field("descricao", "descricao", false, false, false, false));
		listFields.add(new Field("nomeClasseFluxo", "nomeClasseFluxo", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		List list = new ArrayList();
				list.add(new Order("nomeFluxo"));
		return super.list(list);
	}

	public Class getBean() {
		return TipoFluxoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public TipoFluxoDTO findByNome(String nome) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("nomeFluxo", "=", nome));
		ordenacao.add(new Order("idTipoFluxo"));
		Collection col = super.findByCondition(condicao, ordenacao);
		if (col != null && !col.isEmpty())
			return (TipoFluxoDTO) ((List) col).get(0);
		else
			return null;
	}
	
	@Override
	public void updateNotNull(IDto obj) throws Exception {
		// TODO Auto-generated method stub
		super.updateNotNull(obj);
	}
}
