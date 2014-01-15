package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.ControleFinanceiroViagemDTO;
import br.com.centralit.citcorpore.bean.EmpregadoDTO;
import br.com.centralit.citcorpore.bean.ItemControleFinanceiroViagemDTO;

public interface ControleFinanceiroViagemService extends ComplemInfSolicitacaoServicoService{
	
	public Collection<ItemControleFinanceiroViagemDTO> recuperaItensControleFinanceiroBySolicitacao(Integer idSolicitacao);
	public Collection<EmpregadoDTO> recuperaIntegrantesViagemBySolicitacao(Integer idSolicitacao);
	
	/**
	 * Retorna controle financeiro viagem de acordo com o idsolicitaçãoservico passado
	 * @param idSolicitacaoServico
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public ControleFinanceiroViagemDTO buscarControleFinanceiroViagemPorIdSolicitacao(Integer idSolicitacaoServico) throws Exception;

}
