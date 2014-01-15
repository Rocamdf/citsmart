package br.com.centralit.citcorpore.negocio;

import java.util.List;

import br.com.centralit.citcorpore.bean.TipoMovimFinanceiraViagemDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author ronnie.lopes
 *
 */
public interface TipoMovimFinanceiraViagemService extends CrudServiceEjb2 {
	public List<TipoMovimFinanceiraViagemDTO> listByClassificacao(String classificacao) throws Exception;
	public List<TipoMovimFinanceiraViagemDTO> findByMovimentacao(Long idtipoMovimFinanceiraViagem) throws Exception;
	public List<TipoMovimFinanceiraViagemDTO> recuperaTipoAtivos() throws Exception;
}
