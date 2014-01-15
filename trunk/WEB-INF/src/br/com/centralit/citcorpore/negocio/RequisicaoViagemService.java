package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.IntegranteViagemDTO;


public interface RequisicaoViagemService extends ComplemInfSolicitacaoServicoService{
	public Collection<IntegranteViagemDTO> recuperaIntegrantesViagemBySolicitacao(Integer idSolicitacao) throws Exception;
	public Double calculaValorTotalViagem(Integer idSolicitacao) throws Exception;
}
