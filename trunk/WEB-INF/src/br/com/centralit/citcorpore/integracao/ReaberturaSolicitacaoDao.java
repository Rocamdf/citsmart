package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ReaberturaSolicitacaoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

public class ReaberturaSolicitacaoDao extends CrudDaoDefaultImpl {
	public ReaberturaSolicitacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}
	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idsolicitacaoservico" ,"idSolicitacaoServico", true, false, false, false));
		listFields.add(new Field("seqreabertura" ,"seqReabertura", true, false, false, false));
		listFields.add(new Field("idresponsavel" ,"idResponsavel", false, false, false, false));
		listFields.add(new Field("datahora" ,"dataHora", false, false, false, false));
		listFields.add(new Field("observacoes" ,"observacoes", false, false, false, false));
		return listFields;
	}
	public String getTableName() {
		return this.getOwner() + "ReaberturaSolicitacao";
	}
	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ReaberturaSolicitacaoDTO.class;
	}
	public Collection find(IDto arg0) throws Exception {
		return null;
	}
	public Collection findByIdSolicitacaoServico(Integer parm) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList(); 
		condicao.add(new Condition("idSolicitacaoServico", "=", parm)); 
		ordenacao.add(new Order("seqReabertura"));
		return super.findByCondition(condicao, ordenacao);
	}
}
