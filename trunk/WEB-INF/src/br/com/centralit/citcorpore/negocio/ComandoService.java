package br.com.centralit.citcorpore.negocio;

import br.com.centralit.citcorpore.bean.ComandoDTO;
import br.com.citframework.service.CrudServiceEjb2;

/**
 * @author ygor.magalhaes
 *
 */
public interface ComandoService extends CrudServiceEjb2 {
	
	public ComandoDTO listItemCadastrado(String descricao) throws Exception;

}
