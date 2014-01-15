package br.com.centralit.citcorpore.integracao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import br.com.centralit.citcorpore.bean.ExecucaoSolicitacaoDTO;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.citframework.dto.IDto;
import br.com.citframework.integracao.Condition;
import br.com.citframework.integracao.CrudDaoDefaultImpl;
import br.com.citframework.integracao.Field;
import br.com.citframework.integracao.Order;
import br.com.citframework.util.Constantes;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class ExecucaoSolicitacaoDao extends CrudDaoDefaultImpl {

	private static final long serialVersionUID = 3460552611698275811L;

	private static final String TABLE_NAME = "execucaosolicitacao";

	public ExecucaoSolicitacaoDao() {
		super(Constantes.getValue("DATABASE_ALIAS"), null);
	}

	public Collection getFields() {
		Collection listFields = new ArrayList();
		listFields.add(new Field("idExecucao", "idExecucao", true, true, false, false));
		listFields.add(new Field("idSolicitacaoServico", "idSolicitacaoServico", false, false, false, false));
		listFields.add(new Field("idFase", "idFase", false, false, false, false));
		listFields.add(new Field("idFluxo", "idFluxo", false, false, false, false));
		listFields.add(new Field("prazoHH", "prazoHH", false, false, false, false));
		listFields.add(new Field("prazoMM", "prazoMM", false, false, false, false));
		listFields.add(new Field("idInstanciaFluxo", "idInstanciaFluxo", false, false, false, false));
		listFields.add(new Field("seqReabertura", "seqReabertura", false, false, false, false));
		return listFields;
	}

	public String getTableName() {
		return TABLE_NAME;
	}

	public Collection list() throws Exception {
		return null;
	}

	public Class getBean() {
		return ExecucaoSolicitacaoDTO.class;
	}

	public Collection find(IDto arg0) throws Exception {
		return null;
	}

	public ExecucaoSolicitacaoDTO findByIdInstanciaFluxo(Integer idInstanciaFluxo) throws Exception {
		List condicao = new ArrayList();
		condicao.add(new Condition("idInstanciaFluxo", "=", idInstanciaFluxo));

		Collection col = super.findByCondition(condicao, null);
		if (col == null || col.size() == 0)
			return null;
		return (ExecucaoSolicitacaoDTO) ((List) col).get(0);
	}

	public Collection<ExecucaoSolicitacaoDTO> listByIdSolicitacao(Integer idSolicitacaoServico) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", idSolicitacaoServico));
		ordenacao.add(new Order("idExecucao", Order.DESC));
		return super.findByCondition(condicao, ordenacao);
	}

	public ExecucaoSolicitacaoDTO findBySolicitacaoServico(SolicitacaoServicoDTO solicitacaoServicoDto) throws Exception {
		List condicao = new ArrayList();
		List ordenacao = new ArrayList();
		condicao.add(new Condition("idSolicitacaoServico", "=", solicitacaoServicoDto.getIdSolicitacaoServico()));
		if (solicitacaoServicoDto.getSeqReabertura() != null && solicitacaoServicoDto.getSeqReabertura().intValue() > 0)
			condicao.add(new Condition("seqReabertura", "=", solicitacaoServicoDto.getSeqReabertura()));
		ordenacao.add(new Order("idExecucao", Order.DESC));
		List<ExecucaoSolicitacaoDTO> result = (List<ExecucaoSolicitacaoDTO>) super.findByCondition(condicao, ordenacao);
		if (result != null && result.size() > 0)
			return result.get(0);
		else
			return null;
	}
}
