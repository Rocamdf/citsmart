/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.PesquisaSatisfacaoDTO;
import br.com.citframework.excecao.ServiceException;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author valdoilo
 * 
 */
public interface PesquisaSatisfacaoService extends CrudServiceEjb2 {
	Collection<PesquisaSatisfacaoDTO> getPesquisaByIdSolicitacao(int idServico);
	
	Collection<PesquisaSatisfacaoDTO> relatorioPesquisaSatisfacao(PesquisaSatisfacaoDTO pesquisaSatisfacaoDTO) throws ServiceException, Exception;
	
}
