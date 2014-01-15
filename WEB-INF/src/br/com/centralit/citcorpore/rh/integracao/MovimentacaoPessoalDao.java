package br.com.centralit.citcorpore.rh.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.rh.bean.MovimentacaoPessoalDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class MovimentacaoPessoalDao extends CrudDaoDefaultImpl {
	public MovimentacaoPessoalDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idMovimentacaoPessoal" ,"idMovimentacaoPessoal", true, true, false, false));
		listFields.add(new Field("idEmpregado" ,"idEmpregado", false, false, false, false));
		listFields.add(new Field("data" ,"data", false, false, false, false));
		listFields.add(new Field("tipoMovimentacao" ,"tipoMovimentacao", false, false, false, false));
		listFields.add(new Field("idResponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("idUnidade" ,"idUnidade", false, false, false, false));
		listFields.add(new Field("idCargo" ,"idCargo", false, false, false, false));
		listFields.add(new Field("idCentroResultado" ,"idCentroResultado", false, false, false, false));
		listFields.add(new Field("idProjeto" ,"idProjeto", false, false, false, false));
		listFields.add(new Field("matricula" ,"matricula", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "RH_MovimentacaoPessoal";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return MovimentacaoPessoalDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdEmpregado(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idEmpregado", "=", parm)); 
		ordenacao.add(new Order("idEmpregado"));
		return super.findByCondition(condicao, ordenacao);
	}
	public void deleteByIdEmpregado(Integer parm) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idEmpregado", "=", parm));
		super.deleteByCondition(condicao);
	}
}
