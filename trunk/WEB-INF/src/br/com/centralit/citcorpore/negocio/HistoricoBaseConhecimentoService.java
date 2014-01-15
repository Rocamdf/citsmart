/**
 * 
 */
package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.HistoricoBaseConhecimentoDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author Vadoilo Damasceno
 * 
 */
public interface HistoricoBaseConhecimentoService extends CrudServiceEjb2 {

	/**
	 * Retorna uma lista de historico de alteração de uma base de conhecimento informada
	 * 
	 * @param historicoBaseConhecimentoDto
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	public Collection<HistoricoBaseConhecimentoDTO> obterHistoricoDeAlteracao(HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDto) throws Exception;
	
	public Collection<HistoricoBaseConhecimentoDTO> obterHistoricoDeAlteracaoPorPeriodo(HistoricoBaseConhecimentoDTO historicoBaseConhecimentoDTO) throws Exception;
	
}
