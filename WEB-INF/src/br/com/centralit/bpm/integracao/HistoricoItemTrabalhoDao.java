package br.com.centralit.bpm.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class HistoricoItemTrabalhoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 4357989677212221656L;

	private final static String TABLE_NAME = "bpm_historicoitemtrabalho";

	public HistoricoItemTrabalhoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idHistoricoItemTrabalho", "idHistoricoItemTrabalho", true, true, false, false));
		listFields.add(new Field("idItemTrabalho", "idItemTrabalho", false, false, false, false));
		listFields.add(new Field("idResponsavel", "idResponsavel", false, false, false, false));
		listFields.add(new Field("idUsuario", "idUsuario", false, false, false, false));
		listFields.add(new Field("idGrupo", "idGrupo", false, false, false, false));
		listFields.add(new Field("dataHora", "dataHora", false, false, false, false));
		listFields.add(new Field("acao", "acao", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return br.com.centralit.bpm.dto.HistoricoItemTrabalhoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public Collection findByIdItemTrabalho(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idItemTrabalho", "=", parm));
		ordenacao.add(new Order("dataHora"));
		return super.findByCondition(condicao, ordenacao);
	}

	public Collection findByIdUsuario(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idUsuario", "=", parm));
		ordenacao.add(new Order("dataHora"));
		return super.findByCondition(condicao, ordenacao);
	}
}
