package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.InspecaoEntregaItemDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class InspecaoEntregaItemDao extends CrudDaoDefaultImpl {
	public InspecaoEntregaItemDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idEntrega" ,"idEntrega", true, false, false, false));
		listFields.add(new Field("idCriterio" ,"idCriterio", true, false, false, false));
		listFields.add(new Field("dataHoraInspecao" ,"dataHoraInspecao", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("avaliacao" ,"avaliacao", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "InspecaoEntregaItem";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return InspecaoEntregaItemDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdEntrega(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idEntrega", "=", parm)); 
		ordenacao.add(new Order("idCriterio"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdEntrega(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEntrega", "=", parm));
		super.deleteByCondition(condicao);
	}
}
