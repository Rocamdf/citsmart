package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.AdiantamentoViagemDTO;

public interface AdiantamentoViagemService extends ComplemInfSolicitacaoServicoService{

	public Integer recuperaIdAdiantamentoSeExistir(AdiantamentoViagemDTO adiantamentoViagemDto)throws Exception;

}
