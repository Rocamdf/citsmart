package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.bpm.dto.InstanciaFluxoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class InstanciaFluxoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 7825105268029019342L;

	private static final String TABLE_NAME = "bpm_instanciafluxo";

	public InstanciaFluxoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idInstancia", "idInstancia", true, true, false, false));
		listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
		listFields.add(new Field("dataHoraCriacao", "dataHoraCriacao", false, false, false, false));
		listFields.add(new Field("situacao", "situacao", false, false, false, false));
		listFields.add(new Field("dataHoraFinalizacao", "dataHoraFinalizacao", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return InstanciaFluxoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection findByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		ordenacao.add(new Order("idInstancia"));
		return super.findByCondition(condicao, ordenacao);
	}

	public void deleteByIdFluxo(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idFluxo", "=", parm));
		super.deleteByCondition(condicao);
	}
}
