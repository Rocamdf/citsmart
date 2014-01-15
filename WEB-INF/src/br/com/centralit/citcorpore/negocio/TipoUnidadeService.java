package br.com.centralit.citcorpore.negocio;

import java.util.Collection;

import br.com.citframework.service.CrudServiceEjb2;

public interface TipoUnidadeService extends CrudServiceEjb2 {
	boolean jaExisteUnidadeComMesmoNome(String nome); 
}
