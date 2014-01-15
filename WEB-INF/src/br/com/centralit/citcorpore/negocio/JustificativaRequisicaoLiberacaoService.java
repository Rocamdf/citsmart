package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.centralit.citcorpore.bean.JustificativaRequisicaoLiberacaoDTO;
import br.com.citframework.service.CrudServiceEjb2;

public interface JustificativaRequisicaoLiberacaoService extends CrudServiceEjb2 {
	
	/**
	 * Retorna uma lista de justificativa requisição mudança ativas.
	 * @return
	 * @throws Exception
	 * @author thays.araujo
	 */
	
	public Collection<JustificativaRequisicaoLiberacaoDTO> listAtivasParaSuspensao() throws Exception;

}
