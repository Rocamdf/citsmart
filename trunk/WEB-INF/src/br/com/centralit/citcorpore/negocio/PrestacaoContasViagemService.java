package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.PrestacaoContasViagemDTO;
import br.com.centralit.citcorpore.bean.RequisicaoViagemDTO;
import br.com.citframework.excecao.ServiceException;


/**
 * @author ronnie.lopes
 *
 */
public interface PrestacaoContasViagemService extends ComplemInfSolicitacaoServicoService {

	Integer recuperaIdPrestacaoSeExistir(Integer idSolicitacaoServico, Integer idEmpregado) throws ServiceException, Exception;
	public PrestacaoContasViagemDTO recuperaCorrecao(RequisicaoViagemDTO requisicaoViagemDto) throws Exception;
	
}
