package br.com.centralit.citcorpore.rh.negocio;

import br.com.centralit.bpm.negocio.ItemTrabalho;
import br.com.centralit.citcorpore.bean.SolicitacaoServicoDTO;
import br.com.centralit.citcorpore.negocio.ComplemInfSolicitacaoServicoService;


public interface RequisicaoPessoalService extends ComplemInfSolicitacaoServicoService {
	public void preparaSolicitacaoParaAprovacao(SolicitacaoServicoDTO solicitacaoDto, ItemTrabalho itemTrabalho, String aprovacao, Integer idJustificativa, String observacoes) throws Exception;
}
