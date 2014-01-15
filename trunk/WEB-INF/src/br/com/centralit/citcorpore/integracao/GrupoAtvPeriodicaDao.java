package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import br.com.centralit.citcorpore.bean.GrupoAtvPeriodicaDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class GrupoAtvPeriodicaDao extends CrudDaoDefaultImpl {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1758986727299261895L;

	public GrupoAtvPeriodicaDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idGrupoAtvPeriodica" ,"idGrupoAtvPeriodica", true, false, false, false));
		listFields.add(new Field("nomeGrupoAtvPeriodica" ,"nomeGrupoAtvPeriodica", false, false, false, false));
		listFields.add(new Field("descGrupoAtvPeriodica" ,"descGrupoAtvPeriodica", false, false, false, false));
		listFields.add(new Field("deleted" ,"deleted", false, false, false, false));
		listFields.add(new Field("dataInicio" ,"dataInicio", false, false, false, false));
		listFields.add(new Field("dataFim" ,"dataFim", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "GrupoAtvPeriodica";
	}
	public Collection list() throws Exception {
		return super.list("nomeGrupoAtvPeriodica");
	}

	public Class getBean() {
		return GrupoAtvPeriodicaDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByDescGrupoAtvPeriodica(String parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("descGrupoAtvPeriodica", "=", parm)); 
		ordenacao.add(new Order(""));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByDescGrupoAtvPeriodica(String parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("descGrupoAtvPeriodica", "=", parm));
		super.deleteByCondition(condicao);
	}
	
	public Collection listGrupoAtividadePeriodicaAtiva() throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("deleted", "<>", "Y"));
		condicao.add(new Condition(Condition.OR, "deleted", "is", null));
		ordenacao.add(new Order("nomeGrupoAtvPeriodica"));
		return super.findByCondition(condicao, ordenacao);
	}
	
}
