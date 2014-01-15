package br.com.centralit.citcorpore.negocio;

import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author ygor.magalhaes
 *
 */
public interface CondicaoOperacaoService extends CrudServiceEjb2 {
	public boolean jaExisteCondicaoComMesmoNome(String nome);
}
